package gate.util.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.repository.AuthenticationBuilder;
import org.eclipse.aether.util.repository.DefaultProxySelector;

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
  
  private static List<File> extraCacheDirectories = new CopyOnWriteArrayList<>();
  
  /**
   * A list of extra workspace cache directories that should be
   * used when resolving Maven plugins.
   * 
   * @return an unmodifiable list of the current registered caches
   */
  public static List<File> getExtraCacheDirectories() {
    return Collections.unmodifiableList(extraCacheDirectories);
  }
  
  /**
   * Add an extra cache directory to be used when resolving Maven plugins.
   * Caches registered in this way will be searched after the default
   * caches alongside saved GATE applications.
   * @param dir the cache directory to add
   */
  public static void addCacheDirectory(File dir) {
    extraCacheDirectories.add(0, dir);
  }
  
  /**
   * Remove a directory from the list of extra caches to be used when
   * resolving Maven plugins.
   * @param dir the cache directory to remove
   * @return <code>true</code> if the directory was removed,
   * <code>false</code> if not (which may mean it wasn't in the
   * list to start with)
   */
  public static boolean removeCacheDirectory(File dir) {
    return extraCacheDirectories.remove(dir);
  }
  
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
    
    // Without this we wouldn't be able to find SNAPSHOT builds of plugins we
    // haven't built and installed locally ourselves
    RemoteRepository gateRepo = new RemoteRepository.Builder("gate", "default",
        "http://repo.gate.ac.uk/content/groups/public/").build();

    // Add all repos from settings.xml
    // http://stackoverflow.com/questions/27818659/loading-mavens-settings-xml-for-jcabi-aether-to-use
    Settings effectiveSettings = loadMavenSettings();
    
    org.apache.maven.settings.Proxy proxy = effectiveSettings.getActiveProxy();
    DefaultProxySelector selector = null;
    if(proxy != null) {
      selector = new DefaultProxySelector();
      selector.add(
          new Proxy(proxy.getProtocol(), proxy.getHost(), proxy.getPort(),
              new AuthenticationBuilder().addUsername(proxy.getUsername())
                  .addPassword(proxy.getPassword()).build()),
          proxy.getNonProxyHosts());
    }
        
    Map<String, Profile> profilesMap = effectiveSettings.getProfilesAsMap();
    for(String profileName : effectiveSettings.getActiveProfiles()) {
      Profile profile = profilesMap.get(profileName);
      List<Repository> repositories = profile.getRepositories();
      for(Repository repo : repositories) {
        RemoteRepository remoteRepo =
                new RemoteRepository.Builder(repo.getId(), "default",
                        repo.getUrl()).build();
        
        if(selector != null) {
          remoteRepo = new RemoteRepository.Builder(remoteRepo)
              .setProxy(selector.getProxy(remoteRepo)).build();
        }
        
        repos.add(remoteRepo);
      }
    }
    
    repos.add(central);    
    repos.add(gateRepo);
    
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

  public static DefaultRepositorySystemSession getRepositorySession(RepositorySystem repoSystem, WorkspaceReader workspace) {
    
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
