package gate.util.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.settings.Profile;
import org.apache.maven.settings.Repository;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilder;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingResult;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;

public class Utils {
  
  private static final Logger log = Logger.getLogger(Utils.class);
  
  public static final String userHome = System.getProperty("user.home");

  public static final File userMavenConfigurationHome =
          new File(userHome, ".m2");

  public static final String envM2Home = System.getenv("M2_HOME");

  public static final File DEFAULT_USER_SETTINGS_FILE =
          new File(userMavenConfigurationHome, "settings.xml");

  public static final String settingsXml = System.getProperty(
          "M2_SETTINGS_XML", DEFAULT_USER_SETTINGS_FILE.getPath());

  public static final File DEFAULT_GLOBAL_SETTINGS_FILE =
          new File(
                  System.getProperty("maven.home",
                          envM2Home != null ? envM2Home : ""),
                  "conf/settings.xml");
  
  public static Settings loadMavenSettings()
      throws SettingsBuildingException {
    // http://stackoverflow.com/questions/27818659/loading-mavens-settings-xml-for-jcabi-aether-to-use
    SettingsBuildingRequest settingsBuildingRequest =
        new DefaultSettingsBuildingRequest();
    settingsBuildingRequest.setSystemProperties(System.getProperties());
    settingsBuildingRequest.setUserSettingsFile(new File(settingsXml));
    settingsBuildingRequest.setGlobalSettingsFile(DEFAULT_GLOBAL_SETTINGS_FILE);

    SettingsBuildingResult settingsBuildingResult;
    DefaultSettingsBuilderFactory mvnSettingBuilderFactory =
        new DefaultSettingsBuilderFactory();
    DefaultSettingsBuilder settingsBuilder =
        mvnSettingBuilderFactory.newInstance();
    settingsBuildingResult = settingsBuilder.build(settingsBuildingRequest);

    Settings effectiveSettings = settingsBuildingResult.getEffectiveSettings();
    return effectiveSettings;
  }
  
  public static List<RemoteRepository> getRepositoryList() throws SettingsBuildingException {
    
    List<RemoteRepository> repos = new ArrayList<RemoteRepository>();
    
    RemoteRepository central =
        new RemoteRepository.Builder("central", "default",
                "http://repo1.maven.org/maven2/").build();

    repos.add(central);
    
    // Add all repos from settings.xml
    // http://stackoverflow.com/questions/27818659/loading-mavens-settings-xml-for-jcabi-aether-to-use
    Settings effectiveSettings = loadMavenSettings();
    Map<String, Profile> profilesMap = effectiveSettings.getProfilesAsMap();
    for(String profileName : effectiveSettings.getActiveProfiles()) {
      Profile profile = profilesMap.get(profileName);
      List<Repository> repositories = profile.getRepositories();
      for(Repository repo : repositories) {
        RemoteRepository remoteRepo =
                new RemoteRepository.Builder(repo.getId(), "default",
                        repo.getUrl()).build();
      repos.add(remoteRepo);
      }
    }
    
    return repos;
  }
  
  public Artifact getArtifact() {
    return null;
  }
  
  public static RepositorySystem getRepositorySystem() {

    DefaultServiceLocator locator =
            MavenRepositorySystemUtils.newServiceLocator();
    locator.addService(RepositoryConnectorFactory.class,
            BasicRepositoryConnectorFactory.class);
    locator.addService(TransporterFactory.class, FileTransporterFactory.class);
    locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

    return locator.getService(RepositorySystem.class);
  }

  public static RepositorySystemSession getRepositorySession(RepositorySystem repoSystem, WorkspaceReader workspace) {
    
    DefaultRepositorySystemSession repoSystemSession = MavenRepositorySystemUtils.newSession();
    
    

    String repoLocation = System.getProperty("user.home") + File.separator
            + ".m2" + File.separator + "repository/";
    try {
      Settings effectiveSettings = loadMavenSettings();
      if(effectiveSettings.getLocalRepository() != null) {
        repoLocation = effectiveSettings.getLocalRepository();
      }
    } catch(Exception e) {
      log.warn(
              "Unable to load Maven settings, using default repository location",
              e);
    }

    LocalRepository localRepo = new LocalRepository(repoLocation);
    log.debug("Using local repository at: " + repoLocation);
    repoSystemSession.setLocalRepositoryManager(repoSystem
            .newLocalRepositoryManager(repoSystemSession, localRepo));
    
    //repoSystemSession.setWorkspaceReader(new SimpleMavenCache(new File("repo")));      
    if (workspace != null) repoSystemSession.setWorkspaceReader(workspace);

    return repoSystemSession;
  }
}
