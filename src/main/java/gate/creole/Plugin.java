/*
 * Plugin.java
 *
 * Copyright (c) 2016, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 3, June 2007
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Mark A. Greenwood, 3rd April 2016
 */

package gate.creole;

import gate.Gate;
import gate.Gate.ResourceInfo;
import gate.Resource;
import gate.Utils;
import gate.creole.metadata.CreoleResource;
import gate.util.asm.*;
import gate.util.asm.commons.EmptyVisitor;
import gate.util.maven.SimpleMavenCache;
import gate.util.maven.SimpleModelResolver;
import gate.util.persistence.PersistenceManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferListener;
import org.eclipse.aether.util.artifact.SubArtifact;
import org.eclipse.aether.util.version.GenericVersionScheme;
import org.eclipse.aether.version.InvalidVersionSpecificationException;
import org.eclipse.aether.version.Version;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import static gate.util.maven.Utils.*;

// import gate.util.maven.LoggingTransferListener;

public abstract class Plugin {

  public static interface DownloadListener {
    public void downloadStarted(String name);

    public void downloadProgressed(String name, long totalBytes,
        long transferredBytes);

    public void downloadSucceeded(String name);

    public void downloadFailed(String name, Throwable cause);
  }

  protected Vector<DownloadListener> downloadListeners = null;

  public void addDownloadListener(DownloadListener listener) {
    @SuppressWarnings("unchecked")
    Vector<DownloadListener> v = downloadListeners == null
        ? new Vector<DownloadListener>(2)
        : (Vector<DownloadListener>)downloadListeners.clone();
    if(!v.contains(listener)) {
      v.addElement(listener);
      downloadListeners = v;
    }
  }

  public void removeDownloadListener(DownloadListener listener) {
    if(downloadListeners != null && downloadListeners.contains(listener)) {
      @SuppressWarnings("unchecked")
      Vector<DownloadListener> v =
          (Vector<DownloadListener>)downloadListeners.clone();
      v.removeElement(listener);
      downloadListeners = v;
    }
  }

  protected void fireDownloadStarted(String name) {
    if(downloadListeners != null) {
      for(DownloadListener listener : downloadListeners) {
        listener.downloadStarted(name);
      }
    }
  }

  protected void fireDownloadSucceeded(String name) {
    if(downloadListeners != null) {
      for(DownloadListener listener : downloadListeners) {
        listener.downloadSucceeded(name);
      }
    }
  }

  protected void fireDownloadFailed(String name, Throwable cause) {
    if(downloadListeners != null) {
      for(DownloadListener listener : downloadListeners) {
        listener.downloadFailed(name, cause);
      }
    }
  }

  protected void fireDownloadProgressed(String name, long totalBytes,
      long transferredBytes) {
    if(downloadListeners != null) {
      for(DownloadListener listener : downloadListeners) {
        listener.downloadProgressed(name, totalBytes, transferredBytes);
      }
    }
  }

  protected static final Logger log = Logger.getLogger(Plugin.class);
  
  private static GenericVersionScheme versionScheme =
      new GenericVersionScheme();

  /**
   * Is the plugin valid (i.e. is the location reachable and the creole.xml file
   * parsable).
   */
  protected transient boolean valid = true;

  /**
   * This is the URL against which all relative URLs in the CREOLE metadata are
   * resolved
   */
  protected transient URL baseURL;

  protected transient String name;

  protected transient String description;
  
  protected transient String version;
  
  protected transient String minGateVersion;

  /**
   * The list of {@link gate.Gate.ResourceInfo} objects within this plugin
   */
  protected transient List<ResourceInfo> resourceInfoList = null;

  /**
   * The set of other plugins that must be loaded prior to the loading of this
   * plugin
   */
  protected transient Set<Plugin> requiredPlugins = null;

  /**
   * Returns a fully expanded creole.xml file that can be used for loading the
   * plugin into GATE
   * 
   * @return a fully expanded creole.xml file that can be used for loading the
   *         plugin into GATE
   */
  public abstract org.jdom.Document getCreoleXML() throws Exception;

  /**
   * Returns a creole.xml file which is only guaranteed to contain information
   * about the resources within the plugin and not dependency requirements.
   * 
   * @return a creole.xml file which is only guaranteed to contain information
   *         about the resources within the plugin and not dependency
   *         requirements.
   */
  public org.jdom.Document getMetadataXML() throws Exception {
    return getCreoleXML();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
  
  /**
   * Get the version of this plugin.
   */
  public String getVersion() {
    return version == null ? "" : version;
  }
  
  public String getMinimumGateVersion() {
    return minGateVersion;
  }

  public Set<Plugin> getRequiredPlugins() {
    if(requiredPlugins == null) parseCreole();

    return requiredPlugins;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseURL == null) ? 0 : baseURL.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    Plugin other = (Plugin)obj;
    if(baseURL == null) {
      if(other.baseURL != null) return false;
    } else if(!baseURL.equals(other.baseURL)) return false;
    return true;
  }

  public List<ResourceInfo> getResourceInfoList() {
    if(resourceInfoList == null) parseCreole();

    return resourceInfoList;
  }

  public URL getBaseURL() {
    if(baseURL == null) {
      try {
        getCreoleXML();
      } catch(Exception e) {
        throw new RuntimeException("Unable to determine base URL",e);
      }
    }
    return baseURL;
  }

  public URI getBaseURI() throws URISyntaxException {
    return getBaseURL().toURI();
  }

  public boolean isValid() {
    // we need to ensure parseCreole() has been called before we can know if the
    // valid flag is .... valid
    getResourceInfoList();
    
    //Main.version
    if (Gate.VERSION == null || minGateVersion == null || minGateVersion.isEmpty()) {
      log.debug("unable to check min GATE version");
      return valid;
    }
    
    try {
      Version pluginVersion = versionScheme.parseVersion(minGateVersion);
      
      valid = valid && Gate.VERSION.compareTo(pluginVersion) >= 0;
    } catch(InvalidVersionSpecificationException e) {
      // TODO Auto-generated catch block
      log.warn("unable to parse min GATE version: "+minGateVersion);
    }
    
    return valid;
  }

  public void copyResources(File dir) throws IOException, URISyntaxException {
    throw new UnsupportedOperationException(
        "This plugin does not contain any resources that can be copied");
  }

  public boolean hasResources() {
    return false;
  }

  /**
   * Given a class return the Plugin instance from which it was loaded.
   * 
   * @return the Plugin instance from which the specified class was loaded
   */
  public static Plugin getPlugin(Class<?> clazz) throws IOException {
    URL creoleURL = clazz.getResource("/creole.xml");

    if(creoleURL == null) return null;

    URL baseURL = new URL(creoleURL, ".");

    for(Plugin plugin : Gate.getCreoleRegister().getPlugins()) {
      if(plugin.getBaseURL().equals(baseURL)) return plugin;
    }

    return null;
  }

  protected void parseCreole() {

    valid = true;

    resourceInfoList = new ArrayList<ResourceInfo>();
    requiredPlugins = new LinkedHashSet<Plugin>();

    String relativePathMarker = "$relpath$";
    String gatehomePathMarker = "$gatehome$";
    String gatepluginsPathMarker = "$gateplugins$";

    try {
      org.jdom.Document creoleDoc = getMetadataXML();
      
      minGateVersion = creoleDoc.getRootElement().getAttributeValue("GATE-MIN","");
      version = creoleDoc.getRootElement().getAttributeValue("VERSION","");
      description = creoleDoc.getRootElement().getAttributeValue("DESCRIPTION");
      name = creoleDoc.getRootElement().getAttributeValue("NAME");

      final Map<String, ResourceInfo> resInfos =
          new LinkedHashMap<String, ResourceInfo>();
      List<Element> jobsList = new ArrayList<Element>();
      List<String> jarsToScan = new ArrayList<String>();
      List<String> allJars = new ArrayList<String>();
      jobsList.add(creoleDoc.getRootElement());
      while(!jobsList.isEmpty()) {
        Element currentElem = jobsList.remove(0);
        if(currentElem.getName().equalsIgnoreCase("JAR")) {
          @SuppressWarnings("unchecked")
          List<Attribute> attrs = currentElem.getAttributes();
          Iterator<Attribute> attrsIt = attrs.iterator();
          while(attrsIt.hasNext()) {
            Attribute attr = attrsIt.next();
            if(attr.getName().equalsIgnoreCase("SCAN")
                && attr.getBooleanValue()) {
              jarsToScan.add(currentElem.getTextTrim());
              break;
            }
          }
          allJars.add(currentElem.getTextTrim());
        } else if(currentElem.getName().equalsIgnoreCase("RESOURCE")) {
          // we don't go deeper than resources so no recursion here
          String resName = currentElem.getChildTextTrim("NAME");
          String resClass = currentElem.getChildTextTrim("CLASS");
          String resComment = currentElem.getChildTextTrim("COMMENT");
          if(!resInfos.containsKey(resClass)) {
            // create the handler
            ResourceInfo rHandler =
                new ResourceInfo(resName, resClass, resComment);
            resInfos.put(resClass, rHandler);
          }
        } else if(currentElem.getName().equalsIgnoreCase("REQUIRES")) {

          if(currentElem.getAttribute("GROUP") != null) {
            // TODO probably need more error checking here
            requiredPlugins
                .add(new Plugin.Maven(currentElem.getAttributeValue("GROUP"),
                    currentElem.getAttributeValue("ARTIFACT"),
                    currentElem.getAttributeValue("VERSION")));
          } else {
            URL url = null;
            String urlString = currentElem.getTextTrim();
            if(urlString.startsWith(relativePathMarker)) {
              url = new URL(getBaseURL(),
                  urlString.substring(relativePathMarker.length()));
            } else if(urlString.startsWith(gatehomePathMarker)) {
              throw new IOException("$gatehome$ variable no longer supported in REQUIRES element");
            } else if(urlString.startsWith(gatepluginsPathMarker)) {
              throw new IOException("$gateplugins$ variable no longer supported in REQUIRES element");
            } else {
              url = new URL(getBaseURL(), urlString);
            }

            requiredPlugins.add(new Plugin.Directory(url));

            Utils.logOnce(log, Level.WARN,
                "Dependencies on other plugins via URL is deprecated ("
                    + getName() + " plugin)");
          }
        } else {
          // this is some higher level element -> simulate recursion
          // we want Depth-first-search so we need to add at the beginning
          @SuppressWarnings("unchecked")
          List<Element> newJobsList =
              new ArrayList<Element>(currentElem.getChildren());
          newJobsList.addAll(jobsList);
          jobsList = newJobsList;
        }
      }

      // now process the jar files with SCAN="true", looking for any extra
      // CreoleResource annotated classes.
      for(String jarFile : jarsToScan) {
        URL jarUrl = new URL(getBaseURL(), jarFile);
        scanJar(jarUrl, resInfos);
      }

      // see whether any of the ResourceInfo objects are still incomplete
      // (don't have a name)
      List<ResourceInfo> incompleteResInfos = new ArrayList<ResourceInfo>();
      for(ResourceInfo ri : resInfos.values()) {
        if(ri.getResourceName() == null) {
          incompleteResInfos.add(ri);
        }
      }

      if(!incompleteResInfos.isEmpty()) {
        fillInResInfos(incompleteResInfos, allJars);
      }

      // if any of the resource infos still don't have a name, take it from
      // the class name.
      for(ResourceInfo ri : incompleteResInfos) {
        if(ri.getResourceName() == null) {
          ri.setResourceName(ri.getResourceClassName()
              .substring(ri.getResourceClassName().lastIndexOf('.') + 1));
        }
      }

      // finally, we have the complete list of ResourceInfos
      resourceInfoList.addAll(resInfos.values());
    } catch(IOException ioe) {
      valid = false;
      log.error("Problem while parsing plugin " + toString() + "!\n"
          + ioe.toString() + "\nPlugin not available!");
    } catch(JDOMException jde) {
      valid = false;
      log.error("Problem while parsing plugin " + toString() + "!\n"
          + jde.toString() + "\nPlugin not available!");
    } catch(Exception e) {
      valid = false;
      log.error("Problem while parsing plugin " + toString() + "!\n"
          + e.toString() + "\nPlugin not available!");
    }
  }

  protected void scanJar(URL jarUrl, Map<String, ResourceInfo> resInfos)
      throws IOException {
    JarInputStream jarInput = new JarInputStream(jarUrl.openStream(), false);
    JarEntry entry = null;
    while((entry = jarInput.getNextJarEntry()) != null) {
      String entryName = entry.getName();
      if(entryName != null && entryName.endsWith(".class")) {
        final String className =
            entryName.substring(0, entryName.length() - 6).replace('/', '.');
        if(!resInfos.containsKey(className)) {
          ClassReader classReader = new ClassReader(jarInput);
          ResourceInfo resInfo = new ResourceInfo(null, className, null);
          ResourceInfoVisitor visitor = new ResourceInfoVisitor(resInfo);

          classReader.accept(visitor, ClassReader.SKIP_CODE
              | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
          if(visitor.isCreoleResource()) {
            resInfos.put(className, resInfo);
          }
        }
      }
    }

    jarInput.close();
  }

  protected void fillInResInfos(List<ResourceInfo> incompleteResInfos,
      List<String> allJars) throws IOException {
    // now create a temporary class loader with all the JARs (scanned or
    // not), so we can look up all the referenced classes in the normal
    // way and read their CreoleResource annotations (if any).
    URL[] jarUrls = new URL[allJars.size()];
    for(int i = 0; i < jarUrls.length; i++) {
      jarUrls[i] = new URL(getBaseURL(), allJars.get(i));
    }

    // TODO shouldn't we use a proper temp gate class loader which we
    // can then throw away?
    try (URLClassLoader tempClassLoader =
        new URLClassLoader(jarUrls, Gate.class.getClassLoader());) {
      for(ResourceInfo ri : incompleteResInfos) {
        String classFile =
            ri.getResourceClassName().replace('.', '/') + ".class";
        InputStream classStream =
            tempClassLoader.getResourceAsStream(classFile);
        if(classStream != null) {
          ClassReader classReader = new ClassReader(classStream);
          ClassVisitor visitor = new ResourceInfoVisitor(ri);
          classReader.accept(visitor, ClassReader.SKIP_CODE
              | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
          classStream.close();
        }
      }
    }
  }

  public static class Directory extends Plugin {

    public Directory(URL directoryURL) {
      baseURL = Gate.normaliseCreoleUrl(directoryURL);
    }

    @Override
    public Document getCreoleXML() throws Exception {
      SAXBuilder builder = new SAXBuilder(false);
      URL creoleFileURL = new URL(getBaseURL(), "creole.xml");
      return builder.build(creoleFileURL);
    }

    @Override
    public String getName() {
      if(name != null) return name;

      // url.getPath() works for jar URLs; url.toURI().getPath() doesn't
      // because jars aren't considered "hierarchical"
      name = getBaseURL().getPath();
      if(name.endsWith("/")) {
        name = name.substring(0, name.length() - 1);
      }
      int lastSlash = name.lastIndexOf("/");
      if(lastSlash != -1) {
        name = name.substring(lastSlash + 1);
      }
      try {
        // convert to (relative) URI and extract path. This will
        // decode any %20 escapes in the name.
        name = new URI(name).getPath();
      } catch(URISyntaxException ex) {
        // ignore, this should have been checked when adding the URL!
      }
      return name;
    }
  }

  public static class Maven extends Plugin
                            implements Serializable, TransferListener {

    private static final long serialVersionUID = -6944695755723023537L;

    private String group, artifact, version;

    private transient URL artifactURL, metadataArtifactURL;

    public Maven(String group, String artifact, String version) {
      this.group = group;
      this.artifact = artifact;
      this.version = version;

      name = artifact;// group+":"+artifact+":"+version;
    }

    @Override
    public String getName() {
      if(name == null) {
        try {
          getMetadataXML();
        } catch(Exception e) {
          // ignore this for now
        }
        
        if (name == null) {
          name = artifact;
        }
      }

      return name;
    }

    @Override
    public URI getBaseURI() throws URISyntaxException {
      return new URI(
          "creole://" + group + ";" + artifact + ";" + version + "/");
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((artifact == null) ? 0 : artifact.hashCode());
      result = prime * result + ((group == null) ? 0 : group.hashCode());
      result = prime * result + ((version == null) ? 0 : version.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {

      if(this == obj) return true;
      if(obj == null) return false;

      if(getClass() != obj.getClass()) return false;
      Maven other = (Maven)obj;
      if(artifact == null) {
        if(other.artifact != null) return false;
      } else if(!artifact.equals(other.artifact)) return false;
      if(group == null) {
        if(other.group != null) return false;
      } else if(!group.equals(other.group)) return false;
      if(version == null) {
        if(other.version != null) return false;
      } else if(!version.equals(other.version)) return false;
      return true;
    }

    @Override
    public void copyResources(File dir) throws URISyntaxException, IOException {

      if(!hasResources())
        throw new UnsupportedOperationException(
            "this plugin doesn't have any resources you can copy as you would know had you called hasResources first :P");
      
      Path target = Paths.get(dir.toURI());
      try (FileSystem zipFs =
          FileSystems.newFileSystem(getArtifactURL().toURI(), new HashMap<>());) {

        Path pathInZip = zipFs.getPath("/resources");

        Files.walkFileTree(pathInZip, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path filePath,
              BasicFileAttributes attrs) throws IOException {
            // Make sure that we conserve the hierachy of files and folders
            // inside the zip
            Path relativePathInZip = pathInZip.relativize(filePath);
            Path targetPath = target.resolve(relativePathInZip.toString());
            Files.createDirectories(targetPath.getParent());

            // And extract the file
            Files.copy(filePath, targetPath,
                StandardCopyOption.REPLACE_EXISTING);

            return FileVisitResult.CONTINUE;
          }
        });
      } catch(Exception e) {
        // TODO Auto-generated catch block
        throw new IOException(e);
      }
    }

    public void walkResources(FileVisitor<? super Path> visitor) throws URISyntaxException, IOException {
      try (FileSystem zipFs =
                   FileSystems.newFileSystem(artifactURL.toURI(), new HashMap<>())) {
        Path resourcesPath = zipFs.getPath("/resources");
        if(Files.isDirectory(resourcesPath)) {
          Files.walkFileTree(resourcesPath, visitor);
        }
      }
    }

    @Override
    public boolean hasResources() {
      try (FileSystem zipFs = FileSystems
          .newFileSystem(getMetadataArtifactURL().toURI(), new HashMap<>());) {
        Path pathInZip = zipFs.getPath("/resources");
        return Files.isDirectory(pathInZip);
      } catch(Exception e) {
        return false;
      }
    }

    protected URL getMetadataArtifactURL() throws Exception {
      if(metadataArtifactURL != null) return metadataArtifactURL;

      if(artifactURL != null) return artifactURL;

      getMetadataXML();

      return metadataArtifactURL;
    }

    protected URL getArtifactURL() throws Exception {
      if(artifactURL == null) {
        getCreoleXML();
      }

      return artifactURL;
    }

    @Override
    public Document getMetadataXML() throws Exception {
      Artifact artifactObj =
          new DefaultArtifact(group, artifact, "creole", "jar", version);

      List<RemoteRepository> repos = getRepositoryList();

      ArtifactRequest artifactRequest =
          new ArtifactRequest(artifactObj, repos, null);

      RepositorySystem repoSystem = getRepositorySystem();

      WorkspaceReader workspace = null;

      List<ResourceReference> persistenceURLStack =
          PersistenceManager.currentPersistenceURLStack();

      List<File> workspaces = new ArrayList<File>();

      if(persistenceURLStack != null && !persistenceURLStack.isEmpty()) {
        for(ResourceReference rr : persistenceURLStack) {
          try {
            File file = gate.util.Files.fileFromURL(rr.toURL());
            File cache = new File(file.getParentFile(), "maven-cache.gate");
            if(cache.exists() && cache.isDirectory()) {
              workspaces.add(cache);
            }
          } catch(IllegalArgumentException e) {
            // ignore this for now
          }
        }
      }
      workspaces.addAll(gate.util.maven.Utils.getExtraCacheDirectories());

      if(!workspaces.isEmpty()) {
        workspace = new SimpleMavenCache(
            workspaces.toArray(new File[workspaces.size()]));
      }

      RepositorySystemSession repoSession =
          getRepositorySession(repoSystem, workspace);

      try {
        ArtifactResult artifactResult =
            repoSystem.resolveArtifact(repoSession, artifactRequest);

        metadataArtifactURL = new URL("jar:"
            + artifactResult.getArtifact().getFile().toURI().toURL() + "!/");

        // check it has a creole.xml at the root
        URL expandedCreoleUrl =
            new URL(metadataArtifactURL, "META-INF/gate/creole.xml");

        try (InputStream creoleStream = expandedCreoleUrl.openStream()) {
          // no op just to check the file exists
        } catch(IOException ioe) {
          throw new IOException(expandedCreoleUrl.toExternalForm()
              + " does not exist so this artifact is not a GATE plugin");
        }

        // get the creole.xml out of the jar and add jar elements for this
        // jar (marked for scanning) and the dependencies
        SAXBuilder builder = new SAXBuilder(false);
        Document creoleDoc = builder.build(expandedCreoleUrl);
        
        if(creoleDoc.getRootElement().getAttributeValue("NAME") == null
            || creoleDoc.getRootElement()
                .getAttributeValue("VERSION") == null) {
          // if the root element doesn't specifiy a name and version then either
          // this is an old plugin from early in 8.5 or a file produced in
          // some other way than DumpCreoleToXML. Either way it's not valid so
          // fall back to using the full creole.xml expansion in getCreoleXML()
          return getCreoleXML();
        }
        
        return creoleDoc;

      } catch(IOException | ArtifactResolutionException e) {
        e.printStackTrace();
        return getCreoleXML();
      }
    }

    @Override
    public Document getCreoleXML() throws Exception {
      Artifact artifactObj =
          new DefaultArtifact(group, artifact, "jar", version);

      Dependency dependency = new Dependency(artifactObj, "runtime");

      List<RemoteRepository> repos = getRepositoryList();

      ArtifactRequest artifactRequest =
          new ArtifactRequest(artifactObj, repos, null);

      RepositorySystem repoSystem = getRepositorySystem();

      WorkspaceReader workspace = null;

      List<ResourceReference> persistenceURLStack =
          PersistenceManager.currentPersistenceURLStack();

      List<File> workspaces = new ArrayList<File>();

      if(persistenceURLStack != null && !persistenceURLStack.isEmpty()) {
        for(ResourceReference rr : persistenceURLStack) {
          try {
            File file = gate.util.Files.fileFromURL(rr.toURL());
            File cache = new File(file.getParentFile(), "maven-cache.gate");
            if(cache.exists() && cache.isDirectory()) {
              workspaces.add(cache);
            }
          } catch(IllegalArgumentException e) {
            // ignore this for now
          }
        }
      }
      workspaces.addAll(gate.util.maven.Utils.getExtraCacheDirectories());

      if(!workspaces.isEmpty()) {
        workspace = new SimpleMavenCache(
            workspaces.toArray(new File[workspaces.size()]));
      }

      DefaultRepositorySystemSession repoSession =
          getRepositorySession(repoSystem, workspace);
      repoSession.setTransferListener(this);

      ArtifactResult artifactResult =
          repoSystem.resolveArtifact(repoSession, artifactRequest);

      artifactURL = new URL("jar:"
          + artifactResult.getArtifact().getFile().toURI().toURL() + "!/");

      metadataArtifactURL = artifactURL;

      baseURL = artifactURL;

      // check it has a creole.xml at the root
      URL directoryXmlFileUrl = new URL(artifactURL, "creole.xml");

      InputStream creoleStream = null;

      try {
        creoleStream = directoryXmlFileUrl.openStream();
      } catch(IOException ioe) {
        throw new IOException(directoryXmlFileUrl.toExternalForm()
            + " does not exist so this artifact is not a GATE plugin");
      }

      CollectRequest collectRequest = new CollectRequest(dependency, repos);

      DependencyNode node =
          repoSystem.collectDependencies(repoSession, collectRequest).getRoot();

      DependencyRequest dependencyRequest = new DependencyRequest();
      dependencyRequest.setRoot(node);

      DependencyResult result =
          repoSystem.resolveDependencies(repoSession, dependencyRequest);

      // get the creole.xml out of the jar and add jar elements for this
      // jar (marked for scanning) and the dependencies
      SAXBuilder builder = new SAXBuilder(false);
      Document jdomDoc =
          builder.build(creoleStream, getBaseURL().toExternalForm());

      Element creoleRoot = jdomDoc.getRootElement();

      for(ArtifactResult ar : result.getArtifactResults()) {

        Element jarElement = new Element("JAR");
        jarElement.setText(
            ar.getArtifact().getFile().toURI().toURL().toExternalForm());

        if(ar.getArtifact().equals(artifactResult.getArtifact())) {
          jarElement.setAttribute("SCAN", "true");
        }

        creoleRoot.addContent(jarElement);
      }

      artifactObj = new SubArtifact(artifactObj, "", "pom");

      artifactRequest.setArtifact(artifactObj);
      artifactResult = repoSystem.resolveArtifact(repoSession, artifactRequest);

      ModelBuildingRequest req = new DefaultModelBuildingRequest();
      req.setProcessPlugins(false);
      req.setPomFile(artifactResult.getArtifact().getFile());
      req.setSystemProperties(System.getProperties());
      req.setModelResolver(new SimpleModelResolver(repoSystem, repoSession,
          repos));
      req.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

      ModelBuilder modelBuilder =
          new DefaultModelBuilderFactory().newInstance();
      Model model = modelBuilder.build(req).getEffectiveModel();

      if(model.getDescription() != null
          && model.getDescription().trim().equals(""))
        jdomDoc.getRootElement().setAttribute("DESCRIPTION", model.getDescription());
      
      jdomDoc.getRootElement().setAttribute("VERSION", version);
      
      if(model.getName() != null && !model.getName().trim().equals(""))
        jdomDoc.getRootElement().setAttribute("NAME",model.getName());
      else
        jdomDoc.getRootElement().setAttribute("NAME",model.getArtifactId());
      
      String creoleMinGate =
          jdomDoc.getRootElement().getAttributeValue("GATE-MIN");
      if(creoleMinGate == null) {
        for(org.apache.maven.model.Dependency effectiveDependency : model
            .getDependencies()) {
          if(effectiveDependency.getGroupId().equals("uk.ac.gate")
              && effectiveDependency.getArtifactId().equals("gate-core")) {
            jdomDoc.getRootElement().setAttribute("GATE-MIN",
                effectiveDependency.getVersion());
            break;
          }
        }
      }

      /*
       * System.out.println(model.getOrganization().getName()); for
       * (org.apache.maven.model.Repository r : model.getRepositories()) {
       * System.out.println(r.getName()); } for (License l :
       * model.getLicenses()) { System.out.println(l.getName()); }
       */

      return jdomDoc;
    }

    // accessors for the Maven coordinates

    /**
     * Get the Maven group ID of this plugin.
     */
    public String getGroup() {
      return group;
    }

    /**
     * Get the Maven artifact ID of this plugin.
     */
    public String getArtifact() {
      return artifact;
    }

    @Override
    public void transferInitiated(TransferEvent event)
        throws TransferCancelledException {
      // ignore this one
    }

    @Override
    public void transferStarted(TransferEvent event)
        throws TransferCancelledException {
      fireDownloadStarted(event.getResource().getFile().getName());

    }

    @Override
    public void transferProgressed(TransferEvent event)
        throws TransferCancelledException {
      fireDownloadProgressed(event.getResource().getFile().getName(),
          event.getResource().getContentLength(), event.getTransferredBytes());

    }

    @Override
    public void transferCorrupted(TransferEvent event)
        throws TransferCancelledException {
      fireDownloadFailed(event.getResource().getFile().getName(),
          event.getException());
    }

    @Override
    public void transferSucceeded(TransferEvent event) {
      fireDownloadSucceeded(event.getResource().getFile().getName());
    }

    @Override
    public void transferFailed(TransferEvent event) {
      fireDownloadFailed(event.getResource().getFile().getName(),
          event.getException());
    }
  }

  /**
   * ClassVisitor that uses information from a CreoleResource annotation on the
   * visited class (if such exists) to fill in the name and comment in the
   * corresponding ResourceInfo.
   */
  protected static class ResourceInfoVisitor extends EmptyVisitor {
    private ResourceInfo resInfo;

    private boolean foundCreoleResource = false;

    private boolean isAbstract = false;

    public ResourceInfoVisitor(ResourceInfo resInfo) {
      this.resInfo = resInfo;
    }

    public boolean isCreoleResource() {
      return foundCreoleResource && !isAbstract;
    }

    /**
     * Type descriptor for the CreoleResource annotation type.
     */
    private static final String CREOLE_RESOURCE_DESC =
        Type.getDescriptor(CreoleResource.class);

    /**
     * Visit the class header, checking whether this is an abstract class or
     * interface and setting the isAbstract flag appropriately.
     */
    @Override
    public void visit(int version, int access, String name, String signature,
        String superName, String[] interfaces) {
      isAbstract =
          ((access & (Opcodes.ACC_INTERFACE | Opcodes.ACC_ABSTRACT)) != 0);
    }

    /**
     * Visit an annotation on the class.
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      // we've found a CreoleResource annotation on this class
      if(desc.equals(CREOLE_RESOURCE_DESC)) {
        foundCreoleResource = true;
        return new AnnotationVisitor(Opcodes.ASM5) {
          @Override
          public void visit(String name, Object value) {
            if(name.equals("name") && resInfo.getResourceName() == null) {
              resInfo.setResourceName((String)value);
            } else if(name.equals("comment")
                && resInfo.getResourceComment() == null) {
              resInfo.setResourceComment((String)value);
            }
          }

          @Override
          public AnnotationVisitor visitAnnotation(String name, String desc) {
            // don't want to recurse into AutoInstance annotations
            return this;
          }
        };
      } else {
        return super.visitAnnotation(desc, visible);
      }
    }
  }

  public static class Component extends Plugin {

    private Class<? extends Resource> resourceClass;

    public Component(Class<? extends Resource> resourceClass)
        throws MalformedURLException {
      this.resourceClass = resourceClass;
      baseURL = (new URL(
          resourceClass.getResource("/gate/creole/CreoleRegisterImpl.class"),
          "."));
    }

    public String getName() {
      return resourceClass.getName();
    }

    @Override
    public Document getCreoleXML() throws Exception, JDOMException {
      Document doc = new Document();
      Element element;
      doc.addContent(element = new Element("CREOLE-DIRECTORY"));
      element.addContent(element = new Element("CREOLE"));
      element.addContent(element = new Element("RESOURCE"));
      Element classElement = new Element("CLASS");
      classElement.setText(resourceClass.getName());
      element.addContent(classElement);

      return doc;
    }
  }
}
