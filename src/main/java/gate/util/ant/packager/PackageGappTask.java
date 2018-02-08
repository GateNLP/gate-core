/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 10/02/2009
 *
 *  $Id: PackageGappTask.java 20061 2017-02-02 12:59:12Z markagreenwood $
 */
package gate.util.ant.packager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PatternSet.NameEntry;
import org.apache.tools.ant.util.FileUtils;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import gate.util.Files;
import gate.util.maven.SimpleMavenCache;
import gate.util.persistence.PersistenceManager;

/**
 * Ant task to copy a gapp file, rewriting any relative paths it
 * contains to point within the same directory as the target file
 * location and copy the referenced files into the right locations. The
 * resulting structure is self-contained and can be packaged up (e.g. in
 * a zip file) to send to a third party.
 * 
 * @author Ian Roberts
 */
public class PackageGappTask extends Task {
  
  /**
   * Comparator to compare URLs by lexicographic ordering of their
   * getPath() values.  <code>null</code> compares less-than anything
   * not <code>null</code>.
   */
  public static final Comparator<URL> PATH_COMPARATOR = new Comparator<URL>() {
    @Override
    public int compare(URL a, URL b) {
      if(a == null) {
        return (b == null) ? 0 : -1;
      }
      if(b == null) {
        return 1;
      }
      return a.getPath().compareTo(b.getPath());
    }
  };

  /**
   * The file into which the modified gapp will be written.
   */
  private File destFile;

  /**
   * The original file containing the gapp to package.
   */
  private File src;

  /**
   * The location of the GATE home directory.  Only required if the GAPP file
   * to be packaged contains URLs relative to $gatehome$.
   */
  private File gateHome;

  /**
   * The location of the resources home directory.  Only required if the GAPP file
   * to be packaged contains URLs relative to $resourceshome$.
   */
  private File resourcesHome;
  
  /**
   * If set, create a local cache of any Maven plugins referenced by the application,
   * along with their transitive dependencies.  Typically this would be set to
   * the directory "maven-cache.gate" as a sibling of the <code>destFile</code>,
   * which will then be picked up automatically and used as a local cache by
   * the PersistenceManager when loading the packaged application, but it is
   * possible to set it to another location (for example if you want to package
   * a set of related applications and have them all share the same Maven cache).
   */
  private File mavenCache;

  /**
   * Should we copy the complete contents of referenced plugin
   * directories into the right place relative to the destFile? If not,
   * only the creole.xmls, any JARs they directly include, and directly
   * referenced resource files will be copied. Anything else needs to be
   * declared in an &lt;extrafiles&gt; sub-element.
   */
  private boolean copyPlugins = true;

  /**
   * Should we copy the complete contents of the parent directories of
   * any referenced resource files? If true, whenever the gapp
   * references a resource file <code>f</code> we will also include
   * the whole contents of <code>f.getParentFile()</code>.
   */
  private boolean copyResourceDirs = false;
  
  /**
   * Path-like structure listing extra resources that should be packaged
   * with the gapp, as if they had been referenced by relpaths from
   * within the gapp file. Their target locations are determined by the
   * plugins and mapping hints in the usual way. Typically this would be
   * used for other resource files that are not referenced directly by
   * the gapp file but are referenced indirectly by the PRs in the
   * application (e.g. the .lst files corresponding to a gazetteer
   * .def).
   */
  private List<Path> extraResourcesPaths = new ArrayList<Path>();

  /**
   * Enumeration of the actions to take when there are unresolved
   * resources. Options are to fail the build, to make the paths
   * absolute in the new gapp, or to recover by gathering the unresolved
   * files into an "application-resources" directory.
   */
  public static enum UnresolvedAction {
    fail, absolute, recover
  }

  /**
   * The action to take when there are unresolved resources. By default,
   * unresolved resources will fail the build.
   */
  private UnresolvedAction onUnresolved = UnresolvedAction.fail;

  /**
   * List of mapping hint sub-elements.
   */
  private List<MappingHint> hintTasks = new ArrayList<MappingHint>();

  /**
   * Map of mapping hints.  This is an insertion-ordered LinkedHashMap, so
   * where two hints could apply to the same path, the one specified first in
   * the configuration wins.
   */
  private Map<URL, String> mappingHints = new LinkedHashMap<URL, String>();

  /**
   * Get the destination file to which the modified gapp will be
   * written.
   */
  public File getDestFile() {
    return destFile;
  }

  /**
   * Set the destination file to which the modified gapp will be
   * written.
   */
  public void setDestFile(File destFile) {
    this.destFile = destFile;
  }

  /**
   * Get the original gapp file that is to be modified.
   */
  public File getSrc() {
    return src;
  }

  /**
   * Set the location of the original gapp file which is to be modified.
   */
  public void setSrc(File src) {
    this.src = src;
  }

  /**
   * Get the location of the GATE home directory, used to resolve $gatehome$
   * relative paths in the GAPP file.
   */
  public File getGateHome() {
    return gateHome;
  }

  /**
   * Set the location of the GATE home directory, used to resolve $gatehome$
   * relative paths in the GAPP file.
   */
  public void setGateHome(File gateHome) {
    this.gateHome = gateHome;
  }

  /**
   * Get the location of the resources home directory, used to resolve $resourceshome$
   * relative paths in the GAPP file.
   */
  public File getResourcesHome() {
    return resourcesHome;
  }

  /**
   * Set the location of the resources home directory, used to resolve $resourceshome$
   * relative paths in the GAPP file.
   */
  public void setResourcesHome(File resourcesHome) {
    this.resourcesHome = resourcesHome;
  }

  /**
   * Location where the task should create a local cache of any referenced Maven plugins
   * and their dependencies.  May be <code>null</code>, in which case no cache will be
   * created.
   */
  public File getMavenCache() {
	return mavenCache;
  }

  /**
   * Set the location where the task should create a local cache of any referenced
   * Maven plugins and their dependencies.  May be <code>null</code>, in which
   * case no cache will be created.
   */
  public void setMavenCache(File mavenCache) {
	this.mavenCache = mavenCache;
  }

  /**
   * Will the task copy the complete contents of referenced plugins into
   * the target location?
   */
  public boolean isCopyPlugins() {
    return copyPlugins;
  }

  /**
   * Will the task copy the complete contents of referenced plugins into
   * the target location? If false, only the bare minimum will be copied
   * (the creole.xml files, any JARs referenced therein, and any
   * directly referenced resource files). Anything extra must be copied
   * in separately, typically with extra &lt;copy&gt; tasks after the
   * &lt;packagegapp&gt; one.
   */
  public void setCopyPlugins(boolean copyPlugins) {
    this.copyPlugins = copyPlugins;
  }

  /**
   * Will the task copy the complete contents of directories containing
   * referenced resources into the target location or just the
   * referenced resources themselves?
   */
  public boolean isCopyResourceDirs() {
    return copyResourceDirs;
  }

  /**
   * Will the task copy the complete contents of directories containing
   * referenced resources into the target location? By default it does
   * not do this, but only includes the directly-referenced resource
   * files - for example, if the gapp refers to a <code>.def</code>
   * file defining gazetteer lists, the lists themselves will not be
   * included. If copyResourceDirs is false, the additional resources
   * will need to be included using an appropriate
   * &lt;extraresourcespath&gt;.
   */
  public void setCopyResourceDirs(boolean copyResourceDirs) {
    this.copyResourceDirs = copyResourceDirs;
  }

  /**
   * Get the action performed when there are unresolved resources.
   */
  public UnresolvedAction getOnUnresolved() {
    return onUnresolved;
  }

  /**
   * What should we do if there are unresolved relpaths within the gapp
   * file? By default the build will fail, but instead you can opt to
   * have the relative paths replaced by absolute paths to the same URL,
   * or to have the task recover by putting the files into an
   * "application-resources" directory.
   */
  public void setOnUnresolved(UnresolvedAction onUnresolved) {
    this.onUnresolved = onUnresolved;
  }

  /**
   * Create and add the representation for a nested &lt;hint from="X"
   * to="Y" /&gt; element.
   */
  public MappingHint createHint() {
    MappingHint hint = new MappingHint();
    hint.setProject(this.getProject());
    hint.setTaskName(this.getTaskName());
    hint.setLocation(this.getLocation());
    hint.init();
    hintTasks.add(hint);
    return hint;
  }

  /**
   * Add a path containing extra resources that should be treated as if
   * they had been referenced by relpaths within the gapp file. The
   * locations to which these extra resources will be copied are
   * determined by the plugins and mapping hints in the usual way.
   */
  public void addExtraResourcesPath(Path path) {
    extraResourcesPaths.add(path);
  }

  @Override
  public void execute() throws BuildException {
    // process the hints
    for(MappingHint h : hintTasks) {
      h.perform();
    }

    // map to store the necessary file copy operations
    Map<URL, URL> fileCopyMap = new HashMap<URL, URL>();
    Map<URL, URL> dirCopyMap = new HashMap<URL, URL>();
    TreeMap<URL, URL> pluginCopyMap = new TreeMap<URL, URL>(PATH_COMPARATOR);

    log("Packaging gapp file " + src);
    // do the work
    GappModel gappModel = null;
    URL newFileURL = null;
    try {
      URL gateHomeURL = null;
      // convert gateHome to a URL, if it was provided
      if(gateHome != null) {
        gateHomeURL = gateHome.toURI().toURL();
      }
      URL resourcesHomeURL = null;
      // convert resourcesHome to a URL, if it was provided
      if(resourcesHome != null) {
        resourcesHomeURL = resourcesHome.toURI().toURL();
      }
      gappModel = new GappModel(src.toURI().toURL(), gateHomeURL, resourcesHomeURL);
      newFileURL = destFile.toURI().toURL();
      gappModel.setGappFileURL(newFileURL);
    }
    catch(MalformedURLException e) {
      throw new BuildException("Couldn't convert src or dest file to URL", e,
              getLocation());
    }

    // we use TreeSet for these sets so we will process the paths
    // higher up the directory tree before paths pointing to their
    // subdirectories.
    Set<URL> plugins = new TreeSet<URL>(PATH_COMPARATOR);
    plugins.addAll(gappModel.getPluginURLs());
    Set<URL> resources = new TreeSet<URL>(PATH_COMPARATOR);
    resources.addAll(gappModel.getResourceURLs());

    // process the extraresourcespath elements (if any)
    processExtraResourcesPaths(resources);

    // first look at the explicit mapping hints
    if(mappingHints != null && !mappingHints.isEmpty()) {
      Iterator<URL> resourcesIt = resources.iterator();
      while(resourcesIt.hasNext()) {
        URL resource = resourcesIt.next();
        for(URL hint : mappingHints.keySet()) {
          String hintString = hint.toExternalForm();
          if(resource.equals(hint)
                  || (hintString.endsWith("/") && resource.toExternalForm()
                          .startsWith(hintString))) {
            // found this resource under this hint
            log("Found resource " + resource + " under mapping hint URL "
                    + hint, Project.MSG_VERBOSE);
            String hintTarget = mappingHints.get(hint);
            URL newResourceURL = null;
            if(hintTarget == null) {
              // hint asks to map to an absolute URL
              log("  Converting to absolute URL", Project.MSG_VERBOSE);
              newResourceURL = resource;
            }
            else {
              // relativize the URL against the hint source and
              // construct the new URL relative to the hint target
              try {
                URL mappedHint = new URL(newFileURL, hintTarget);
                String resourceRelpath =
                        PersistenceManager.getRelativePath(hint, resource);
                newResourceURL = new URL(mappedHint, resourceRelpath);
                fileCopyMap.put(resource, newResourceURL);
                if(copyResourceDirs) {
                  dirCopyMap.put(new URL(resource, "."), new URL(
                          newResourceURL, "."));
                }
              }
              catch(MalformedURLException e) {
                throw new BuildException("Couldn't construct URL relative to "
                        + hintTarget + " for " + resource, e, getLocation());
              }
              log("  Relocating to " + newResourceURL, Project.MSG_VERBOSE);
            }
            // do the relocation
            gappModel.updatePathForURL(resource, newResourceURL,
                    hintTarget != null);
            // we've now dealt with this resource, so don't need to
            // handle it later
            resourcesIt.remove();
            break;
          }
        }
      }
    }

    // Any resources that aren't covered by the hints, try and
    // resolve them relative to the plugins referenced by the
    // application.
    Iterator<URL> pluginsIt = plugins.iterator();
    while(pluginsIt.hasNext()) {
      URL pluginURL = pluginsIt.next();
      pluginsIt.remove();
      URL newPluginURL = null;
      
      String pluginName = pluginURL.getFile();
      log("Processing plugin " + pluginName, Project.MSG_VERBOSE);

      // first check whether this plugin is a subdirectory of another plugin
      // we have already processed
      SortedMap<URL, URL> possibleAncestors = pluginCopyMap.headMap(pluginURL);
      URL ancestorPlugin = null;
      if(!possibleAncestors.isEmpty()) ancestorPlugin = possibleAncestors.lastKey();
      if(ancestorPlugin != null && pluginURL.toExternalForm().startsWith(
              ancestorPlugin.toExternalForm())) {
        // this plugin is under one we have already dealt with
        log("  Plugin is located under another plugin " + ancestorPlugin, Project.MSG_VERBOSE);
        String relPath = PersistenceManager.getRelativePath(ancestorPlugin, pluginURL);
        try {
          newPluginURL = new URL(pluginCopyMap.get(ancestorPlugin), relPath);
        }
        catch(MalformedURLException e) {
          throw new BuildException("Couldn't construct URL relative to plugins/"
                  + " for " + pluginURL, e, getLocation());
        }
      }
      else {
        // normal case, this plugin is not a subdir of another plugin
        boolean addSlash = false;
        // we will map the plugin whose directory name is X to plugins/X
        if(pluginName.endsWith("/")) {
          addSlash = true;
          pluginName = pluginName.substring(
                  pluginName.lastIndexOf('/', pluginName.length() - 2) + 1,
                  pluginName.length() - 1);
        }
        else {
          pluginName = pluginName.substring(pluginName.lastIndexOf('/') + 1);
        }
        log("  Plugin name is " + pluginName, Project.MSG_VERBOSE);
        try {
          newPluginURL = new URL(newFileURL, "plugins/" + pluginName + (addSlash ? "/" : ""));
          // a gapp may refer to two or more plugins with the same name.
          // If plugins/{pluginName} is already taken, try
          // plugins/{pluginName}-2, plugins/{pluginName}-3, etc.,
          // until we find one that is available.
          if(pluginCopyMap.containsValue(newPluginURL)) {
            int index = 2;
            do {
              newPluginURL =
                      new URL(newFileURL, "plugins/" + pluginName + "-"
                              + (index++) + (addSlash ? "/" : ""));
            } while(pluginCopyMap.containsValue(newPluginURL));
          }
        }
        catch(MalformedURLException e) {
          throw new BuildException("Couldn't construct URL relative to plugins/"
                  + " for " + pluginURL, e, getLocation());
        }
      }
      log("  Relocating to " + newPluginURL, Project.MSG_VERBOSE);

      // deal with the plugin URL itself (in the urlList)
      gappModel.updatePathForURL(pluginURL, newPluginURL, true);
      pluginCopyMap.put(pluginURL, newPluginURL);

      // now look for resources located under that plugin
      String pluginUri = pluginURL.toExternalForm();
      if(!pluginUri.endsWith("/")) {
        pluginUri += "/";
      }
      Iterator<URL> resourcesIt = resources.iterator();
      while(resourcesIt.hasNext()) {
        URL resourceURL = resourcesIt.next();
        try {
          if(resourceURL.toExternalForm().startsWith(
                  pluginUri)) {
            // found a resource under this plugin, so relocate it to be
            // under the re-located plugin dir
            resourcesIt.remove();
            String resourceRelpath =
                    PersistenceManager.getRelativePath(pluginURL, resourceURL);
            log("    Found resource " + resourceURL, Project.MSG_VERBOSE);
            URL newResourceURL = null;
            newResourceURL = new URL(newPluginURL, resourceRelpath);
            log("    Relocating to " + newResourceURL, Project.MSG_VERBOSE);
            gappModel.updatePathForURL(resourceURL, newResourceURL, true);
            fileCopyMap.put(resourceURL, newResourceURL);
            if(copyResourceDirs) {
              dirCopyMap.put(new URL(resourceURL, "."), new URL(newResourceURL,
                      "."));
            }
          }
        }
        catch(MalformedURLException e) {
          throw new BuildException("Couldn't construct URL relative to "
                  + newPluginURL + " for " + resourceURL, e, getLocation());
        }
      }
    }

    // anything left over, handle according to onUnresolved
    if(!resources.isEmpty()) {
      switch(onUnresolved) {
        case fail:
          // easy case - fail the build
          log("There were unresolved resources:", Project.MSG_ERR);
          for(URL res : resources) {
            log(res.toExternalForm(), Project.MSG_ERR);
          }
          log("Either set onUnresolved=\"absolute|recover\" or add the "
                  + "relevant mapping hints", Project.MSG_ERR);
          throw new BuildException("There were unresolved resources",
                  getLocation());

        case absolute:
          // convert all unresolved resources to absolute URLs
          log("There were unresolved resources, which have been made absolute",
                  Project.MSG_WARN);
          for(URL res : resources) {
            gappModel.updatePathForURL(res, res, false);
            log(res.toExternalForm(), Project.MSG_VERBOSE);
          }
          break;

        case recover:
          // the clever case - recover by putting all the unresolved
          // resources into subdirectories of an "application-resources"
          // directory under the output dir
          URL unresolvedResourcesDir = null;
          try {
            unresolvedResourcesDir =
                    new URL(newFileURL, "application-resources/");
          }
          catch(MalformedURLException e) {
            throw new BuildException("Can't construct URL relative to "
                    + newFileURL + " for application-resources", e,
                    getLocation());
          }
          // map to track where under application-resources we should map
          // each directory that contains unresolved resources
          TreeMap<URL, URL> unresolvedResourcesSubDirs = new TreeMap<URL, URL>(PATH_COMPARATOR);
          log("There were unresolved resources, which have been gathered into "
                  + unresolvedResourcesDir, Project.MSG_INFO);
          for(URL res : resources) {
            URL resourceDir = null;
            try {
              resourceDir = new URL(res, ".");
            }
            catch(MalformedURLException e) {
              throw new BuildException(
                      "Can't construct URL to parent directory of " + res, e,
                      getLocation());
            }
            URL targetDir =
                    getUnresolvedResourcesTarget(unresolvedResourcesSubDirs,
                            unresolvedResourcesDir, resourceDir);
            String resName = res.getFile();
            resName = resName.substring(resName.lastIndexOf('/') + 1);
            URL newResourceURL = null;
            try {
              newResourceURL = new URL(targetDir, resName);
            }
            catch(MalformedURLException e) {
              throw new BuildException("Can't construct URL relative to "
                      + unresolvedResourcesDir + " for " + resName, e,
                      getLocation());
            }
            gappModel.updatePathForURL(res, newResourceURL, true);
            fileCopyMap.put(res, newResourceURL);
            if(copyResourceDirs) {
              dirCopyMap.put(resourceDir, targetDir);
            }
          }
          break;

        default:
          throw new BuildException("Unrecognised UnresolvedAction",
                  getLocation());
      }
    }

    // write out the fixed GAPP file
    try {
      log("Writing modified gapp to " + destFile);
      gappModel.write();
    }
    catch(IOException e) {
      throw new BuildException("Error writing out modified GAPP file", e,
              getLocation());
    }

    // now copy the files that it references
    if(fileCopyMap.size() > 0) {
      log("Copying " + fileCopyMap.size() + " resources");
    }
    for(Map.Entry<URL, URL> resEntry : fileCopyMap.entrySet()) {
      File source = Files.fileFromURL(resEntry.getKey());
      File dest = Files.fileFromURL(resEntry.getValue());
      if(source.isDirectory()) {
        // source URL points to a directory, so create a corresponding
        // directory dest
        dest.mkdirs();
      }
      else {
        // source URL doesn't point to a directory, so
        // ensure parent directory exists
        dest.getParentFile().mkdirs();
        if(source.isFile()) {
          // source URL points to an existing file, copy it
          try {
            log("Copying " + source + " to " + dest, Project.MSG_VERBOSE);
            FileUtils.getFileUtils().copyFile(source, dest);
          }
          catch(IOException e) {
            throw new BuildException(
                    "Error copying file " + source + " to " + dest, e,
                    getLocation());
          }
        }
      }
    }

    // handle the plugins
    if(pluginCopyMap.size() > 0) {
      log("Copying " + pluginCopyMap.size() + " plugins");
      if(copyPlugins) {
        log("Also copying complete plugin contents", Project.MSG_VERBOSE);
      }
      copyDirectories(pluginCopyMap, !copyPlugins);
    }
    
    // handle extra directories
    if(dirCopyMap.size() > 0) {
      log("Copying " + dirCopyMap.size() + " resource directories");      
      copyDirectories(dirCopyMap, false);
    }
    
    if(mavenCache != null) {
      List<GappModel.MavenPlugin> mavenPlugins = gappModel.getMavenPlugins();
      if(!mavenPlugins.isEmpty()) {
        log("Creating Maven cache at " + mavenCache.getAbsolutePath());
        SimpleMavenCache smc = new SimpleMavenCache(mavenCache);
        for(GappModel.MavenPlugin plugin : mavenPlugins) {
          log("  Cacheing " + plugin.group + ":" + plugin.artifact + ":" + plugin.version, Project.MSG_VERBOSE);
          Artifact pluginArtifact = new DefaultArtifact(plugin.group, plugin.artifact, "jar", plugin.version);
          try {
            smc.cacheArtifact(pluginArtifact);
          } catch(DependencyCollectionException | DependencyResolutionException
              | ArtifactResolutionException | IOException
              | SettingsBuildingException | ModelBuildingException e) {
            throw new BuildException("Error cacheing plugin " + plugin.group + ":"
              + plugin.artifact + ":" + plugin.version, e);
          }
        }
      }
    }
    
  }

  /**
   * Process any extraresourcespath elements provided to this task and
   * include the resources they refer to in the given set.
   */
  private void processExtraResourcesPaths(Set<URL> resources) {
    for(Path p : extraResourcesPaths) {
      for(String resource : p.list()) {
        File resourceFile = new File(resource);
        try {
          resources.add(resourceFile.toURI().toURL());
        }
        catch(MalformedURLException e) {
          throw new BuildException("Couldn't construct URL for extra resource "
                  + resourceFile, e, getLocation());
        }
      }
    }
  }

  /**
   * Copy directories as specified by the given map.
   * 
   * @param copyMap map specifying the directories to copy and the
   *          target locations to which they should be copied.
   * @param minimalPlugin if true, treat the directory as a GATE plugin
   *          and copy just the minimal files needed for the plugin to
   *          work (creole.xml and any referenced jars).
   */
  private void copyDirectories(Map<URL, URL> copyMap, boolean minimalPlugin) {
    for(Map.Entry<URL, URL> copyEntry : copyMap.entrySet()) {
      File source = Files.fileFromURL(copyEntry.getKey());
      if(!source.exists()) { return; }
      File dest = Files.fileFromURL(copyEntry.getValue());
      // set up a copy task to do the copying
      Copy copyTask = new Copy();
      copyTask.setProject(getProject());
      copyTask.setLocation(getLocation());
      copyTask.setTaskName(getTaskName());
      copyTask.setTodir(dest);
      // ensure the target directory exists
      dest.mkdirs();
      FileSet fileSet = new FileSet();
      copyTask.addFileset(fileSet);
      fileSet.setDir(source);
      if(minimalPlugin) {
        // just copy creole.xml and JARs
        NameEntry include = fileSet.createInclude();
        include.setName("creole.xml");
        URL creoleXml;
        try {
          creoleXml =
              new URL(copyEntry.getKey().toExternalForm() + "/creole.xml");
        } catch(MalformedURLException e) {
          throw new BuildException(
              "Error creating URL for creole.xml in plugin "
                  + copyEntry.getKey());
        }

        for(String jarString : getJars(creoleXml)) {
          NameEntry jarInclude = fileSet.createInclude();
          jarInclude.setName(jarString);
        }
      }

      // do the copying
      copyTask.init();
      copyTask.perform();
    }
  }

  /**
   * Extract the text values from any &lt;JAR&gt; elements contained in the
   * referenced creole.xml file.
   * 
   * @return a set with one element for each unique &lt;JAR&gt; entry in the
   *         given creole.xml.
   */
  private Set<String> getJars(URL creoleXml) {
    try {
      Set<String> jars = new HashSet<String>();
      // the XPath is a bit ugly, but needed to match the element name
      // case-insensitively.
      XPath jarXPath =
          XPath
              .newInstance("//*[translate(local-name(), 'jar', 'JAR') = 'JAR']");
      SAXBuilder builder = new SAXBuilder();
      Document creoleDoc = builder.build(creoleXml);
      // technically unsafe, but we know that the above XPath expression
      // can only match elements.
      @SuppressWarnings("unchecked")
      List<Element> jarElts = jarXPath.selectNodes(creoleDoc);
      for(Element e : jarElts) {
        jars.add(e.getTextTrim());
      }

      return jars;
    } catch(JDOMException e) {
      throw new BuildException("Error extracting JAR elements from "
          + creoleXml, e, getLocation());
    } catch(IOException e) {
      throw new BuildException("Error loading " + creoleXml
          + " to extract JARs", e, getLocation());
    }
  }
  
  /**
   * Get a URL for a directory to which the given (unresolved) resource
   * directory should be mapped.
   * 
   * @param unresolvedResourcesSubDirs a map from URLs of directories
   *          containing unresolved resources to the URLs under the
   *          target unresolved-resources directory that they will be
   *          mapped to. This map is updated by this method.
   * @param unresolvedResourcesDir the top-level application-resources
   *          directory in the target location.
   * @param resourceDir a directory containing an unresolved resource.
   * @return the URL under application-resources to which this directory
   *         should be mapped. For a resourceDir of the form .../foo,
   *         the returned URL would typically be
   *         &lt;applicationResourcesDir&gt;/foo, but if a different
   *         directory with the same name has already been mapped then
   *         we will return the first ..../foo-2, foo-3, etc. that is
   *         not already in use.  If one of the directory's ancestors
   *         has already been mapped then we return a URL pointing
   *         to the same relative path inside that ancestor's mapping,
   *         e.g. if .../foo has already been mapped to a-r/foo-2 then
   *         .../foo/bar/baz will map to a-r/foo-2/bar/baz.
   */
  private URL getUnresolvedResourcesTarget(
          TreeMap<URL, URL> unresolvedResourcesSubDirs, URL unresolvedResourcesDir,
          URL resourceDir) throws BuildException {
    URL targetDir = unresolvedResourcesSubDirs.get(resourceDir);
    try {
      if(targetDir == null) {
        // no exact match, try an ancestor match
        SortedMap<URL, URL> possibleAncestors = unresolvedResourcesSubDirs.headMap(resourceDir);
        URL nearestAncestor = null;
        if(!possibleAncestors.isEmpty()) nearestAncestor = possibleAncestors.lastKey();
        if(nearestAncestor != null && resourceDir.toExternalForm().startsWith(
                nearestAncestor.toExternalForm())) {
          // found an ancestor mapping, so take the relative path
          // from the ancestor to this dir and map it to the same
          // path under the ancestor's mapping.
          String relPath = PersistenceManager.getRelativePath(nearestAncestor, resourceDir);
          targetDir = new URL(unresolvedResourcesSubDirs.get(nearestAncestor), relPath);
        }
        else {
          // no ancestors currently mapped, so start a new sub-dir of
          // unresolvedResourcesDir whose name is the last path
          // component of the source URL
          String resourcePath = resourceDir.getFile();
          if(resourcePath.endsWith("/")) {
            resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
          }
          String targetDirName =
                  resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
          if(targetDirName.length() == 0) {
            // edge case, if the source URL points to the root directory "/"
            targetDirName = "resources";
          }
          // try application-resources/{targetDirName} as the target
          targetDir = new URL(unresolvedResourcesDir, targetDirName + "/");
          // if this is already taken, try
          // application-resources/{targetDirName}-2,
          // application-resources/{targetDirName}-3, etc., until we find
          // one that is available.
          if(unresolvedResourcesSubDirs.containsValue(targetDir)) {
            int index = 2;
            do {
              targetDir =
                      new URL(unresolvedResourcesDir, targetDirName + "-"
                              + (index++) + "/");
            } while(unresolvedResourcesSubDirs.containsValue(targetDir));
          }
        }
        
        // store the mapping for future use
        unresolvedResourcesSubDirs.put(resourceDir, targetDir);
      }
    }
    catch(MalformedURLException e) {
      throw new BuildException("Can't construct target URL for directory "
              + resourceDir, e, getLocation());
    }

    return targetDir;
  }

  /**
   * Class to represent a nested <code>hint</code> element. Typically
   * this will be a simple <code>&lt;hint from="X" to="Y" /&gt;</code>
   * but the MappingHint class actually extends the property task, so it
   * can read hints in Properties-file format using
   * <code>&lt;hint file="hints.properties" /&gt;</code>.
   */
  public class MappingHint extends Property {
    private boolean absolute = false;

    public void setFrom(File from) {
      super.setName(from.getAbsolutePath());
    }

    public void setTo(String to) {
      super.setValue(to);
    }

    /**
     * Should files matching this hint be made absolute? If true, the
     * "to" value is ignored.
     */
    public void setAbsolute(boolean absolute) {
      if(absolute) {
        super.setValue("dummy");
      }
      this.absolute = absolute;
    }

    /**
     * This was the pre-ant 1.8 version of addProperty, Ant 1.8 uses the
     * version that takes an <code>Object</code> as value rather than a
     * <code>String</code>.
     */
    @Override
    protected void addProperty(String n, String v) {
      addProperty(n, (Object)v);
    }

    /**
     * Rather than adding properties to the project, add mapping hints
     * to the task.
     */
    @Override
    protected void addProperty(String n, Object vObj) {
      String v = (vObj == null) ? null : vObj.toString();
      try {
        // resolve relative paths against project basedir
        File source = getProject().resolveFile(n);
        // add a trailing slash to the hint target if necessary
        if(source.isDirectory() && v != null && !v.endsWith("/")) {
          v += "/";
        }
        mappingHints.put(source.toURI().toURL(), absolute ? null : v);
      }
      catch(MalformedURLException e) {
        PackageGappTask.this.log("Couldn't interpret \"" + n
                + "\" as a file path, ignored", Project.MSG_WARN);
      }
    }

  }
}
