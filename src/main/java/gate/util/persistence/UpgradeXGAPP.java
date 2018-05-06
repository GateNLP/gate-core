package gate.util.persistence;

import static gate.util.maven.Utils.getRepositoryList;
import static gate.util.maven.Utils.getRepositorySession;
import static gate.util.maven.Utils.getRepositorySystem;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import gate.gui.persistence.XgappUpgradeSelector;
import org.apache.log4j.Logger;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.util.version.GenericVersionScheme;
import org.eclipse.aether.version.InvalidVersionSpecificationException;
import org.eclipse.aether.version.Version;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import gate.Gate;
import gate.Main;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.gui.MainFrame;
import gate.resources.img.svg.ApplicationIcon;
import gate.swing.XJFileChooser;
import gate.util.ExtensionFileFilter;

@CreoleResource(tool = true, isPrivate = true, autoinstances = @AutoInstance, name = "Upgrade XGapp", comment = "Upgrades an XGapp to use new style GATE plugins")
public class UpgradeXGAPP {

  private static final Logger log = Logger.getLogger(UpgradeXGAPP.class);

  private static XMLOutputter outputter =
      new XMLOutputter(Format.getPrettyFormat());

  private static GenericVersionScheme versionScheme =
      new GenericVersionScheme();

  private static Version getDefaultSelection(List<Version> versions) {
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
          String newName = oldName.toLowerCase().replaceAll("[\\s_]+", "-");

          versions = getPluginVersions("uk.ac.gate.plugins", newName);

          if(versions != null) {

            upgrades
                .add(new UpgradePath(plugin, urlString, "uk.ac.gate.plugins",
                    newName, versions, null, getDefaultSelection(versions.getVersions())));
          }
          break;

        case "gate.creole.Plugin-Maven":

          String group = plugin.getChild("group").getValue();
          String artifact = plugin.getChild("artifact").getValue();
          String version = plugin.getChild("version").getValue();

          String oldCreoleURI =
              "creole://" + group + ";" + artifact + ";" + version + "/";

          versions = getPluginVersions(group, artifact);

          if(versions != null) {
            Version currentVersion;
            try {
              currentVersion = versionScheme.parseVersion(version);
              upgrades
                  .add(new UpgradePath(plugin, oldCreoleURI, group, artifact,
                      versions, currentVersion, getDefaultSelection(versions.getVersions())));
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

  @SuppressWarnings("unchecked")
  public static void upgrade(Document doc, List<UpgradePath> upgrades)
      throws JDOMException {
    Element root = doc.getRootElement();

    Element pluginList = root.getChild("urlList").getChild("localList");

    for(UpgradePath upgrade : upgrades) {
      if(upgrade.isShouldUpgrade()) {
        int pluginIndex = pluginList.indexOf(upgrade.getOldElement());

        pluginList.setContent(pluginIndex, upgrade.getNewElement());
      }
    }

    XPath jarXPath = XPath.newInstance(
        "//gate.util.persistence.PersistenceManager-URLHolder/urlString | //gate.util.persistence.PersistenceManager-RRPersistence/uriString");
    for(Element element : (List<Element>)jarXPath.selectNodes(doc)) {

      String urlString = element.getValue();

      for(UpgradePath upgrade : upgrades) {
        if(upgrade.isShouldUpgrade() && urlString.startsWith(upgrade.getOldPath())) {

          String urlSuffix = urlString.substring(upgrade.getOldPath().length());

          urlString = upgrade.getNewPath()
              + (urlSuffix.startsWith("resources/") ? "" : "resources/")
              + urlSuffix;

          Element rr = new Element(
              "gate.util.persistence.PersistenceManager-RRPersistence");
          Element uriString = new Element("uriString");
          uriString.setText(urlString);

          rr.addContent(uriString);

          Element parent = element.getParentElement().getParentElement();
          parent.removeContent(element.getParentElement());

          parent.addContent(rr);

          break;
        }
      }
    }

  }

  private static VersionRangeResult getPluginVersions(String group,
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

          artifactObj =
              new DefaultArtifact(group, artifact, "jar", version.toString());

          ArtifactRequest artifactRequest =
              new ArtifactRequest(artifactObj, repos, null);

          ArtifactResult artifactResult =
              repoSystem.resolveArtifact(repoSession, artifactRequest);

          // TODO check if it is compatible with this version of GATE

          URL artifactURL = new URL("jar:"
              + artifactResult.getArtifact().getFile().toURI().toURL() + "!/");

          // check it has a creole.xml at the root
          URL directoryXmlFileUrl = new URL(artifactURL, "creole.xml");

          try (InputStream creoleStream = directoryXmlFileUrl.openStream()) {
            // no op
          }
        } catch(ArtifactResolutionException | IOException e) {
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
    private Element oldEntry;

    private String groupID, artifactID;

    private Version selected, current;

    private VersionRangeResult versions;

    private String oldPath;

    private boolean shouldUpgrade = true;

    protected UpgradePath(Element oldEntry, String oldPath, String groupID,
        String artifactID, VersionRangeResult versions, Version current,
        Version selected) {
      this.oldEntry = oldEntry;
      this.oldPath = oldPath.endsWith("/") ? oldPath : oldPath + "/";
      this.groupID = groupID;
      this.artifactID = artifactID;
      this.versions = versions;
      this.selected = selected;
      this.current = current;
    }

    public void setSelectedVersion(Version version) {
      if(!versions.getVersions().contains(version))
        throw new IllegalArgumentException("Supplied version isn't valid");

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
          + selected.toString() + "/";
    }

    public String getOldPath() {
      return oldPath;
    }

    public String getGroupID() {
      return groupID;
    }

    public String getArtifactID() {
      return artifactID;
    }

    public List<Version> getVersions() {
      return versions.getVersions();
    }

    public boolean isShouldUpgrade() {
      return shouldUpgrade;
    }

    public void setShouldUpgrade(boolean shouldUpgrade) {
      this.shouldUpgrade = shouldUpgrade;
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
      fileChooser.addChoosableFileFilter(filter);
      fileChooser.setFileFilter(filter);
      fileChooser.setDialogTitle("Select an XGapp to Upgrade");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      fileChooser.setResource("lastapplication");

      if(fileChooser.showOpenDialog(
          MainFrame.getInstance()) != XJFileChooser.APPROVE_OPTION)
        return;

      File originalXGapp = fileChooser.getSelectedFile();

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
                if(!upgrade.isShouldUpgrade() || upgrade.getSelectedVersion().equals(upgrade.getCurrentVersion())) {
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
    }

  }

  public static void main(String args[]) {
    try {
      SAXBuilder builder = new SAXBuilder(false);
      Document doc = builder.build(new File("/home/mark/twitie-en.xgapp"));
      List<UpgradePath> upgrades = suggest(doc);
      Iterator<UpgradePath> it = upgrades.iterator();
      while(it.hasNext()) {
        UpgradePath upgrade = it.next();
        if(!upgrade.isShouldUpgrade() || upgrade.getSelectedVersion().equals(upgrade.getCurrentVersion())) {
          it.remove();
        } else {
          System.out.println(upgrade);
        }
      }

      if(upgrades.isEmpty()) {
        System.out.println("Nothing to do :(");
        return;
      }

      upgrade(doc, upgrades);

      outputter.output(doc, System.out);

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
