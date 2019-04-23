package gate.util.persistence;

import gate.Gate;
import gate.Main;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.gui.MainFrame;
import gate.gui.persistence.XgappUpgradeSelector;
import gate.persist.PersistenceException;
import gate.resources.img.svg.ApplicationIcon;
import gate.swing.XJFileChooser;
import gate.util.ExtensionFileFilter;
import gate.util.maven.SimpleModelResolver;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.*;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.util.artifact.SubArtifact;
import org.eclipse.aether.util.version.GenericVersionScheme;
import org.eclipse.aether.version.InvalidVersionSpecificationException;
import org.eclipse.aether.version.Version;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import javax.swing.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gate.util.maven.Utils.*;

@CreoleResource(tool = true, isPrivate = true, autoinstances = @AutoInstance, name = "Upgrade XGapp", comment = "Upgrades an XGapp to use new style GATE plugins")
public class UpgradeXGAPP {

  private static final Logger log = Logger.getLogger(UpgradeXGAPP.class);

  private static final Pattern CREOLE_URI_PATTERN =
          Pattern.compile("^creole://(?<group>[^;/]+);(?<artifact>[^;/]+);(?<version>[^/]+)/$");

  /**
   * XML outputter. Use LF for line endings rather than CRLF (the JDOM default) to match
   * XStream.
   */
  private static XMLOutputter outputter = new XMLOutputter(Format.getRawFormat().setLineSeparator("\n"));

  private static GenericVersionScheme versionScheme =
      new GenericVersionScheme();

  public static Version getDefaultSelection(VersionRangeResult vrr) {
    if(vrr == null) {
      return null;
    }
    List<Version> versions = vrr.getVersions();
    // is the version of GATE we are running a SNAPSHOT?
    boolean isSnapshot = Main.version.toUpperCase().endsWith("-SNAPSHOT");

    int i = versions.size() - 1;

    while(i >= 0 && !isSnapshot) {
      // if GATE isn't a SNAPSHOT then work back through the versions until...
      Version v = versions.get(i);
      if(!v.toString().toUpperCase().endsWith("-SNAPSHOT")) {
        // we find one that isn't a SNAPSHOT
        return v;
      }

      --i;
    }

    // either GATE is a SNAPSHOT release or all the versions are SNAPSHOTS and
    // in either case we just return the latest
    return versions.get(versions.size() - 1);
  }

  @SuppressWarnings("unchecked")
  public static List<UpgradePath> suggest(Document doc)
      throws IOException, JDOMException {

    List<UpgradePath> upgrades = new ArrayList<UpgradePath>();

    Element root = doc.getRootElement();

    Element pluginList = root.getChild("urlList").getChild("localList");

    List<Element> plugins = pluginList.getChildren();

    Iterator<Element> it = plugins.iterator();
    while(it.hasNext()) {
      Element plugin = it.next();

      VersionRangeResult versions;

      switch(plugin.getName()){
        case "gate.util.persistence.PersistenceManager-URLHolder":
          String urlString = plugin.getChild("urlString").getValue();
          String[] parts = urlString.split("/");

          String oldName = parts[parts.length - 1];
          String newName = mapDirectoryNameToPlugin(oldName);

          versions = getPluginVersions("uk.ac.gate.plugins", newName);

          upgrades
              .add(new UpgradePath(plugin, urlString, (versions == null ? null : "uk.ac.gate.plugins"),
                  newName, versions, null, getDefaultSelection(versions)));
          break;

        case "gate.creole.Plugin-Maven":

          String group = plugin.getChild("group").getValue();
          String artifact = plugin.getChild("artifact").getValue();
          String version = plugin.getChild("version").getValue();

          String oldCreoleURI =
              "creole://" + group + ";" + artifact + ";" + version + "/";

          versions = getPluginVersions(group, artifact);

          if(versions != null && versions.getVersions() != null && !versions.getVersions().isEmpty()) {
            Version currentVersion;
            try {
              currentVersion = versionScheme.parseVersion(version);
              upgrades
                  .add(new UpgradePath(plugin, oldCreoleURI, group, artifact,
                      versions, currentVersion, getDefaultSelection(versions)));
            } catch(InvalidVersionSpecificationException e) {
              // this should be impossible as the version string comes from an
              // xgapp generated having successfully loaded a plugin
            }
          }

          break;
        default:
          // some unknown plugin type
          break;
      }
    }

    return upgrades;
  }

  /**
   * Attempts to map an old-style name of an 8.4 plugin under the GATE
   * plugins folder into the corresponding Maven artifact ID under
   * uk.ac.gate.plugins.  In most cases this is a simple syntactic
   * transformation but there are a couple of special cases.
   * @param oldName the old plugin directory name
   * @return the Maven artifact ID that corresponds to the old plugin
   * in GATE 8.5 and later.
   */
  public static String mapDirectoryNameToPlugin(String oldName) {
    // special cases first
    if("Lang_French".equals(oldName)) {
      return "lang-french-compat";
    } else if("Lang_German".equals(oldName)) {
      return "lang-german-compat";
    } else {
      return oldName.toLowerCase().replaceAll("[\\s_]+", "-");
    }
  }

  @SuppressWarnings("unchecked")
  public static void upgrade(Document doc, List<UpgradePath> upgrades)
      throws JDOMException {
    Element root = doc.getRootElement();

    Element pluginList = root.getChild("urlList").getChild("localList");

    for(UpgradePath upgrade : upgrades) {
      if(upgrade.getUpgradeStrategy().upgradePlugin) {
        int pluginIndex = pluginList.indexOf(upgrade.getOldElement());
        Element newElement = upgrade.getNewElement();
        prettyPrint(newElement, 3);
        pluginList.setContent(pluginIndex, newElement);
      }
    }

    XPath jarXPath = XPath.newInstance(
        "//gate.util.persistence.PersistenceManager-URLHolder/urlString | //gate.util.persistence.PersistenceManager-RRPersistence/uriString");
    for(Element element : (List<Element>)jarXPath.selectNodes(doc)) {

      String urlString = element.getValue();

      for(UpgradePath upgrade : upgrades) {
        if(upgrade.getUpgradeStrategy().upgradeResources && urlString.startsWith(upgrade.getOldPath())) {

          String urlSuffix = urlString.substring(upgrade.getOldPath().length());

          urlString = upgrade.newPathFor(urlSuffix);

          // construct a new RRPersistence with the mapped path
          Element rr = new Element(
              "gate.util.persistence.PersistenceManager-RRPersistence");
          Element uriString = new Element("uriString");
          uriString.setText(urlString);

          rr.addContent(uriString);
          prettyPrint(rr, countAncestors(element) - 1);

          // replace the original URLHolder or RRPersistence with the new one
          // at the same point in the tree
          Element parent = element.getParentElement();
          Element grandparent = parent.getParentElement();

          int index = grandparent.indexOf(parent);
          grandparent.removeContent(parent);
          grandparent.addContent(index, rr);

          break;
        }
      }
    }

  }

  /**
   * Count the number of ancestor elements of the given element in the
   * tree (i.e. the "depth" of this element in the tree).  The element
   * must be attached to a document for this to be accurate.
   * @param e the element
   * @return the number of ancestor elements, including the root element
   * (but not the document root node, which is not an element).
   */
  private static int countAncestors(Element e) {
    int ancestors = 0;
    while((e = e.getParentElement()) != null) {
      ancestors++;
    }
    return ancestors;
  }

  /**
   * If this element has any child <em>elements</em>, add whitespace
   * text nodes inside this element to indent its children to the
   * appropriate level, and then do the same recursively for the children.
   * This doesn't work for elements with mixed content - content must be
   * either other elements or text, not both.
   * @param e the element to pretty print
   * @param numAncestors the number of ancestor elements this element
   *                     will have once it is inserted into the tree
   *                     (i.e. the "depth" of this element).
   */
  @SuppressWarnings("unchecked")
  private static void prettyPrint(Element e, int numAncestors) {
    if(!e.getChildren().isEmpty()) {
      int numChildren = e.getContentSize();
      // add indent before the closing tag
      e.addContent(numChildren, new Text("\n" + StringUtils.repeat(" ", 2*numAncestors)));
      // add a bit more indent before each child - we have to work backwards
      // as the act of adding text nodes changes the content indexes for
      // subsequent children
      String indentStr = "\n" + StringUtils.repeat(" ", 2*(numAncestors+1));
      for(int i = numChildren - 1; i >= 0; i--) {
        e.addContent(i, new Text(indentStr));
      }

      // now recursively prettify the children
      for(Element child : (List<Element>)e.getChildren()) {
        prettyPrint(child, numAncestors + 1);
      }
    }
  }

  private static XMLInputFactory inputFactory = XMLInputFactory.newFactory();

  public static VersionRangeResult getPluginVersions(String group,
      String artifact) {
    try {
      Artifact artifactObj =
          new DefaultArtifact(group, artifact, "jar", "[0,)");

      List<RemoteRepository> repos = getRepositoryList();

      RepositorySystem repoSystem = getRepositorySystem();

      RepositorySystemSession repoSession =
          getRepositorySession(repoSystem, null);

      VersionRangeRequest request =
          new VersionRangeRequest(artifactObj, repos, null);

      VersionRangeResult versionResult =
          repoSystem.resolveVersionRange(repoSession, request);

      if(versionResult.getVersions().isEmpty()) return null;

      List<Version> versions = versionResult.getVersions();

      Iterator<Version> it = versions.iterator();
      while(it.hasNext()) {

        Version version = it.next();
        try {
          URL creoleUrl = null;
          ArtifactResult artifactResult = null;
          // try creole.jar first
          try {
            artifactObj = new DefaultArtifact(group, artifact, "creole",
                    "jar", version.toString());
            ArtifactRequest artifactRequest =
                    new ArtifactRequest(artifactObj, repos, null);

            artifactResult =
                    repoSystem.resolveArtifact(repoSession, artifactRequest);

            URL tryCreoleUrl = new URL("jar:"
                    + artifactResult.getArtifact().getFile().toURI().toURL()
                    + "!/META-INF/gate/creole.xml");

            try(InputStream creoleStream = tryCreoleUrl.openStream()) {
              // no op
            }
            // if we get to here we know we have a valid creole.xml
            creoleUrl = tryCreoleUrl;
          } catch(ArtifactResolutionException e) {
            // no -creole.jar, try the normal jar
            artifactObj =
                    new DefaultArtifact(group, artifact, "jar", version.toString());

            ArtifactRequest artifactRequest =
                    new ArtifactRequest(artifactObj, repos, null);

            artifactResult =
                    repoSystem.resolveArtifact(repoSession, artifactRequest);

            URL artifactURL = new URL("jar:"
                    + artifactResult.getArtifact().getFile().toURI().toURL() + "!/");

            // look for the expanded creole.xml first
            URL tryCreoleUrl = new URL("jar:"
                    + artifactResult.getArtifact().getFile().toURI().toURL()
                    + "!/META-INF/gate/creole.xml");

            try(InputStream creoleStream = tryCreoleUrl.openStream()) {
              creoleUrl = tryCreoleUrl;
            } catch(IOException ex) {
              // expanded creole not found, try the regular top-level one
              URL directoryXmlFileUrl = new URL(artifactURL, "creole.xml");

              try(InputStream creoleStream = directoryXmlFileUrl.openStream()) {
                creoleUrl = directoryXmlFileUrl;
              }
            }
          }

          // look for a GATE-MIN attribute on the root of the creole.xml
          String minGateVersion = null;
          try(InputStream creoleStream = creoleUrl.openStream()) {
            XMLStreamReader xsr = inputFactory.createXMLStreamReader(creoleStream);
            try {
              // skip to root element
              xsr.nextTag();
              minGateVersion = xsr.getAttributeValue("", "GATE-MIN");
            } finally {
              xsr.close();
            }
          }
          if(minGateVersion == null) {
            // need to look at the POM :-(
            artifactObj = new SubArtifact(artifactObj, "", "pom");

            ArtifactRequest artifactRequest =
                    new ArtifactRequest(artifactObj, repos, null);
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

            for (org.apache.maven.model.Dependency effectiveDependency : model.getDependencies()) {
              if(effectiveDependency.getGroupId().equals("uk.ac.gate") &&
                      effectiveDependency.getArtifactId().equals("gate-core")) {
                minGateVersion = effectiveDependency.getVersion();
              }
            }
          }
          if(minGateVersion != null) {
            Version pluginMinGate = versionScheme.parseVersion(minGateVersion);
            if(Gate.VERSION.compareTo(pluginMinGate) < 0) {
              it.remove();
            }
          }
        } catch(ArtifactResolutionException | IOException | XMLStreamException | ModelBuildingException | InvalidVersionSpecificationException e) {
          e.printStackTrace();
          it.remove();
        }
      }

      versionResult.setVersions(versions);

      return versionResult;

    } catch(SettingsBuildingException | VersionRangeResolutionException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static class UpgradePath {

    public enum UpgradeStrategy {
      UPGRADE("Upgrade", "Replace all references to the old plugin with the new one", true, true),
      PLUGIN_ONLY("Plugin only", "Load the new plugin, but use resources from " +
              "the old location (for example if you have copied a core GATE plugin and " +
              "modified its files in-place)", true, false),
      SKIP("Skip", "Do nothing with this plugin", false, false);

      public final String label;

      public final String tooltip;

      public final boolean upgradePlugin;

      public final boolean upgradeResources;

      UpgradeStrategy(String label, String tooltip, boolean upgradePlugin, boolean upgradeResources) {
        this.label = label;
        this.tooltip = tooltip;
        this.upgradePlugin = upgradePlugin;
        this.upgradeResources = upgradeResources;
      }

      public String toString() {
        return label;
      }
    }
    private Element oldEntry;

    private String groupID, artifactID;

    private Version selected, current;

    private VersionRangeResult versions;

    private String oldPath;

    private UpgradeStrategy upgradeStrategy;

    public UpgradePath(Element oldEntry, String oldPath, String groupID,
        String artifactID, VersionRangeResult versions, Version current,
        Version selected) {
      this.oldEntry = oldEntry;
      this.oldPath = oldPath.endsWith("/") ? oldPath : oldPath + "/";
      this.groupID = groupID;
      this.artifactID = artifactID;
      this.versions = versions;
      this.selected = selected;
      this.current = current;
      this.upgradeStrategy = (versions == null ? UpgradeStrategy.SKIP : UpgradeStrategy.UPGRADE);
    }

    public void setSelectedVersion(Version version) {
      if(versions == null) {
        if(version == null) {
          this.selected = null;
        } else {
          throw new IllegalArgumentException("Supplied version isn't valid");
        }
      } else if(!versions.getVersions().contains(version)) {
        throw new IllegalArgumentException("Supplied version isn't valid");
      }
      this.selected = version;
    }

    public Version getSelectedVersion() {
      return selected;
    }

    public Version getCurrentVersion() {
      return current;
    }

    public String toString() {
      return oldPath + " can be upgraded to one of the following versions of "
          + groupID + ":" + artifactID + " " + versions;
    }

    public String getNewPath() {
      return "creole://" + groupID + ";" + artifactID + ";"
          + Objects.toString(selected) + "/";
    }

    public String newPathFor(String urlSuffix) {
      return getNewPath()
              + (urlSuffix.startsWith("resources/") ? "" : "resources/")
              + urlSuffix;
    }

    public String getOldPath() {
      return oldPath;
    }

    public String getGroupID() {
      return groupID;
    }

    public void setGroupID(String groupID) {
      this.groupID = groupID;
    }

    public String getArtifactID() {
      return artifactID;
    }

    public void setArtifactID(String artifactID) {
      this.artifactID = artifactID;
    }

    public List<Version> getVersions() {
      return versions.getVersions();
    }

    public void setVersionRangeResult(VersionRangeResult result) {
      this.versions = result;
    }

    public UpgradeStrategy getUpgradeStrategy() {
      return upgradeStrategy;
    }

    public void setUpgradeStrategy(UpgradeStrategy upgradeStrategy) {
      this.upgradeStrategy = upgradeStrategy;
    }

    protected Element getNewElement() {
      Element mavenPlugin = new Element("gate.creole.Plugin-Maven");

      Element groupElement = new Element("group");
      groupElement.setText(groupID);

      Element artifactElement = new Element("artifact");
      artifactElement.setText(artifactID);

      Element versionElement = new Element("version");
      versionElement.setText(selected.toString());

      mavenPlugin.addContent(groupElement);
      mavenPlugin.addContent(artifactElement);
      mavenPlugin.addContent(versionElement);

      return mavenPlugin;
    }

    protected Element getOldElement() {
      return oldEntry;
    }
  }

  /**
   * <p>Save details of the given set of upgrade paths to a TSV file that can be re-used
   * later when upgrading other apps.  Each line has either two or five fields:</p>
   * <ul>
   *   <li>The <code>oldPath</code>, with <code>$relpath$</code>,
   *   <code>$sysprop$</code> and <code>$resourceshome</code> expanded to absolute URIs</li>
   *   <li>The upgrade strategy (<code>SKIP</code>, <code>PLUGIN_ONLY</code> or
   *   <code>UPGRADE</code>).</li>
   *   <li>If the strategy is not SKIP then there are three more columns
   *   for the selected groupId, artifactId and version.</li>
   * </ul>
   *
   * @param paths the list of upgrade paths to persist
   * @param xgappUri the location of the xgapp file we are upgrading, against which
   *                 <code>$relpath$</code> locations will be resolved.
   * @param outputFile the file in which to save the output.
   * @throws IOException if an error occurs writing the file.
   */
  public static void saveUpgradePaths(List<UpgradePath> paths, URI xgappUri, File outputFile) throws IOException {
    try(PrintWriter w = new PrintWriter(Files.newBufferedWriter(outputFile.toPath(), StandardCharsets.UTF_8))) {
      for(UpgradePath path : paths) {
        StringBuilder line = new StringBuilder();
        String oldPath = path.getOldPath();
        if(oldPath.startsWith("$") && !oldPath.startsWith("$gate")) {
          // $relpath, $sysprop or $resourceshome
          try {
            oldPath = PersistenceManager.URLHolder.unpackPersistentRepresentation(xgappUri, oldPath).toString();
          } catch(PersistenceException e) {
            throw new FileNotFoundException("Couldn't translate " + oldPath + " to a URI"
                    + (oldPath.startsWith("$res") ? " - please set resources home" : ""));
          }
        }
        line.append(oldPath);
        line.append('\t');
        line.append(path.getUpgradeStrategy().name());
        if(path.getUpgradeStrategy().upgradePlugin) {
          line.append('\t');
          line.append(path.getGroupID());
          line.append('\t');
          line.append(path.getArtifactID());
          line.append('\t');
          line.append(path.getSelectedVersion());
        }

        w.println(line);
      }
    }
  }

  /**
   * Load a saved upgrade definition TSV file (such as would be written by
   * {@link #saveUpgradePaths}) and apply its instructions to the given list of
   * upgrade paths extracted from another xgapp.
   * @param paths the list of upgrade paths to modify according to the saved
   *              instructions
   * @param xgappUri the location of the xgapp from which <code>paths</code>
   *                 were extracted, used to resolve any <code>$relpath$</code>
   *                 locations
   * @param inputFile the TSV file of upgrade instructions
   * @throws IOException if an error occurs while reading the file.
   */
  public static void loadUpgradePaths(List<UpgradePath> paths, URI xgappUri, File inputFile) throws IOException {
    Map<String, List<UpgradePath>> pathsByOldPath = new HashMap<>();
    for(UpgradePath path : paths) {
      String oldPath = path.getOldPath();
      if(oldPath.startsWith("$") && !oldPath.startsWith("$gate")) {
        // $relpath, $sysprop or $resourceshome
        try {
          oldPath = PersistenceManager.URLHolder.unpackPersistentRepresentation(xgappUri, oldPath).toString();
        } catch(PersistenceException e) {
          throw new FileNotFoundException("Couldn't translate " + oldPath + " to a URI"
                  + (oldPath.startsWith("$res") ? " - please set resources home" : ""));
        }
      }
      pathsByOldPath.computeIfAbsent(oldPath, (k) -> new ArrayList<>()).add(path);
      // special case - if the oldPath is a creole URI (a Maven plugin) then also
      // generalise to the special "any version" oldPath creole://group;artifact;*/
      // to allow upgrade spec files that will do "any version of foo to vX.Y"
      Matcher m = CREOLE_URI_PATTERN.matcher(oldPath);
      if(m.matches()) {
        pathsByOldPath.computeIfAbsent(
                "creole://" + m.group("group") + ";" + m.group("artifact") + ";*/",
                (k) -> new ArrayList<>()).add(path);
      }
    }
    try(BufferedReader r = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8)) {
      String line;
      int lineNo = 0;
      while((line = r.readLine()) != null) {
        lineNo++;
        line = line.trim();
        if(line.isEmpty() || line.startsWith("#")) {
          // ignore comments and blank lines
          continue;
        }
        String[] fields = line.split("\\s+", 5);
        if(fields.length < 2) {
          throw new IllegalArgumentException("Malformed upgrade file at line " + lineNo + ", expected either " +
                  "\"oldPlugin  SKIP\" or \"oldPlugin  UPGRADE/PLUGIN_ONLY  group  artifact  version\" " +
                  "but found \"" + line + "\"");
        }
        String oldPath = fields[0];
        List<UpgradePath> thesePaths = pathsByOldPath.get(oldPath);
        if(thesePaths != null) {
          UpgradePath.UpgradeStrategy strategy = UpgradePath.UpgradeStrategy.valueOf(fields[1]);
          String groupId = null;
          String artifactId = null;
          String version = null;
          if(fields.length >= 5) {
            groupId = fields[2];
            artifactId = fields[3];
            version = fields[4];
          }

          VersionRangeResult vrr = (groupId == null ? null : getPluginVersions(groupId, artifactId));
          List<Version> versions = (vrr == null ? null : vrr.getVersions());

          for(UpgradePath path : thesePaths) {
            path.setUpgradeStrategy(strategy);
            if(groupId != null) {
              if(versions != null && !versions.isEmpty()) {
                path.setGroupID(groupId);
                path.setArtifactID(artifactId);
                path.setVersionRangeResult(vrr);
                try {
                  path.setSelectedVersion(version == null ? null : versionScheme.parseVersion(version));
                } catch(InvalidVersionSpecificationException | IllegalArgumentException e) {
                  path.setSelectedVersion(getDefaultSelection(vrr));
                  log.warn("Version " + version + " not valid for plugin " + groupId + ":" + artifactId
                          + ", using " + path.getSelectedVersion() + " instead");
                }
              }
            }
          }
        }
      }
    }
  }

  public static class UpgradeAction extends AbstractAction {

    private static final long serialVersionUID = 5104380211427809600L;

    public UpgradeAction() {
      super("Upgrade XGapp", new ApplicationIcon(24, 24));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

      XJFileChooser fileChooser = MainFrame.getFileChooser();

      ExtensionFileFilter filter = new ExtensionFileFilter(
          "GATE Application files (.gapp, .xgapp)", ".gapp", ".xgapp");
      fileChooser.resetChoosableFileFilters();
      fileChooser.addChoosableFileFilter(filter);
      fileChooser.setFileFilter(filter);
      fileChooser.setDialogTitle("Select an XGapp to Upgrade");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      fileChooser.setResource("lastapplication");

      if(fileChooser.showOpenDialog(
          MainFrame.getInstance()) != XJFileChooser.APPROVE_OPTION)
        return;

      try {
        File originalXGapp = fileChooser.getSelectedFile().getCanonicalFile();

        MainFrame.lockGUI("Gathering details of plugins");
        new Thread(() -> {
          final List<UpgradePath> upgrades;
          final Document doc;
          try {
            try {
              SAXBuilder builder = new SAXBuilder(false);
              doc = builder.build(originalXGapp);
              upgrades = suggest(doc);
            } catch(Exception e) {
              log.error("Problem parsing GAPP file", e);
              return;
            }
          } finally {
            MainFrame.unlockGUI();
          }

          SwingUtilities.invokeLater(() -> {
            if(upgrades.isEmpty()) {
              JOptionPane.showMessageDialog(MainFrame.getInstance(), "No upgradeable plugins found!", "Upgrade XGapp", JOptionPane.WARNING_MESSAGE);
            } else {
              if(new XgappUpgradeSelector(originalXGapp.toURI(), upgrades).showDialog(MainFrame.getInstance())) {
                Iterator<UpgradePath> it = upgrades.iterator();
                while(it.hasNext()) {
                  UpgradePath upgrade = it.next();
                  if(!upgrade.getUpgradeStrategy().upgradePlugin || upgrade.getSelectedVersion().equals(upgrade.getCurrentVersion())) {
                    it.remove();
                  } else {
                    System.out.println("Upgrading " + upgrade.getOldPath() + " to " + upgrade.getNewPath());
                  }
                }

                if(upgrades.isEmpty()) {
                  JOptionPane.showMessageDialog(MainFrame.getInstance(), "Nothing to do!", "Upgrade XGapp", JOptionPane.INFORMATION_MESSAGE);
                  return;
                }

                new Thread(() -> {
                  MainFrame.lockGUI("Upgrading application");
                  try {
                    upgrade(doc, upgrades);

                    if(!originalXGapp
                            .renameTo(new File(originalXGapp.getAbsolutePath() + ".bak"))) {
                      System.err.println("unable to back up existing xgapp");
                      return;
                    }

                    try(FileOutputStream out = new FileOutputStream(originalXGapp)) {
                      outputter.output(doc, out);
                    }
                  } catch(Exception e) {
                    log.error("Error upgrading application", e);
                  } finally {
                    MainFrame.unlockGUI();
                  }
                }).start();
              }
            }
          });
        }).start();
      } catch(IOException e) {
        log.error("Can't canonicalise file " + fileChooser.getSelectedFile(), e);
      }
    }

  }

  public static void main(String args[]) throws Exception {
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.ERROR);
    Logger.getLogger("gate").setLevel(Level.INFO);

    if(args.length == 0) {
      System.err.println("Usage:");
      System.err.println();
      System.err.println("  java [java_options] gate.util.persistence.UpgradeXGAPP <xgapp> [<script> [-d]]");
      System.err.println();
      System.err.println(" <xgapp>  - the xgapp file to upgrade");
      System.err.println(" <script> - (optional) a TSV file specifying any specific actions to take");
      System.err.println("            for certain plugins.  Typically this file would have been created");
      System.err.println("            by saving settings while upgrading an xgapp in the GUI.");
      System.err.println(" -d       - also apply the default upgrade paths in addition to any specified");
      System.err.println("            in the script.  The default upgrade paths bring every plugin");
      System.err.println("            referenced by the app up to the newest version that is compatible");
      System.err.println("            with this version of GATE.  Normally these defaults are only used");
      System.err.println("            if a script is not specified.");
      System.err.println();
      System.err.println("The upgraded xgapp will replace the original one, with the original saved");
      System.err.println("with an extra .bak extension.");
      System.err.println();
      System.err.println("Note: if the xgapp references any plugins using $resourceshome$ or");
      System.err.println("$sysprop:...$ style URLs then you must pass the appropriate -D arguments to");
      System.err.println("java to set the relevant properties (-Dgate.user.resourceshome=... in the");
      System.err.println("case of $resourceshome$).");
      System.exit(1);
    }

    SAXBuilder builder = new SAXBuilder(false);
    File gappFile = new File(args[0]).getCanonicalFile();
    System.out.println("Upgrading xgapp file " + gappFile);
    Document doc = builder.build(gappFile);
    List<UpgradePath> upgrades = suggest(doc);
    if(args.length > 1) {
      if(!(args.length > 2 && "-d".equals(args[2]))) {
        for(UpgradePath path : upgrades) {
          path.setUpgradeStrategy(UpgradePath.UpgradeStrategy.SKIP);
        }
      }
      System.out.println("Loading upgrade script from " + args[1]);
      loadUpgradePaths(upgrades, gappFile.toURI(), new File(args[1]));
    }
    Iterator<UpgradePath> it = upgrades.iterator();
    while(it.hasNext()) {
      UpgradePath upgrade = it.next();
      if(!upgrade.getUpgradeStrategy().upgradePlugin || upgrade.getSelectedVersion().equals(upgrade.getCurrentVersion())) {
        it.remove();
      } else {
        System.out.println("Upgrading " + upgrade.getOldPath() + " to " + upgrade.getNewPath()
                + (upgrade.getUpgradeStrategy().upgradeResources ? " (including resource references)" : " (plugin only)"));
      }
    }

    if(upgrades.isEmpty()) {
      System.out.println("Nothing to do :(");
      return;
    }

    upgrade(doc, upgrades);

    if(!gappFile
            .renameTo(new File(gappFile.getPath() + ".bak"))) {
      System.err.println("unable to back up existing xgapp");
      return;
    }

    try(FileOutputStream out = new FileOutputStream(gappFile)) {
      outputter.output(doc, out);
    }

    System.out.println("Done!");

  }

}
