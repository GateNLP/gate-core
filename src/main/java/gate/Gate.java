/*
 *  Gate.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 31/07/98
 *
 *  $Id: Gate.java 20194 2017-02-23 10:27:13Z markagreenwood $
 */

package gate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import gate.config.ConfigDataProcessor;
import gate.creole.CreoleRegisterImpl;
import gate.creole.Plugin;
import gate.creole.ResourceData;
import gate.event.CreoleListener;
import gate.gui.creole.manager.PluginUpdateManager;
import gate.util.Benchmark;
import gate.util.Files;
import gate.util.GateClassLoader;
import gate.util.GateException;
import gate.util.GateRuntimeException;
import gate.util.OptionsMap;
import gate.util.Strings;

/**
 * The class is responsible for initialising the GATE libraries, and providing
 * access to singleton utility objects, such as the GATE class loader, CREOLE
 * register and so on.
 */
public class Gate implements GateConstants {

  /** A logger to use instead of sending messages to Out or Err **/
  protected static final Logger log = Logger.getLogger(Gate.class);

  /**
   * The default StringBuffer size, it seems that we need longer string than the
   * StringBuffer class default because of the high number of buffer expansions
   */
  public static final int STRINGBUFFER_SIZE = 1024;

  /**
   * The default size to be used for Hashtable, HashMap and HashSet. The defualt
   * is 11 and it leads to big memory usage. Having a default load factor of
   * 0.75, table of size 4 can take 3 elements before being re-hashed - a values
   * that seems to be optimal for most of the cases.
   */
  public static final int HASH_STH_SIZE = 4;

  

  /** The GATE URI used to interpret custom GATE tags */
  public static final String URI = "http://www.gate.ac.uk";

  /** Minimum version of JDK we support */
  protected static final String MIN_JDK_VERSION = "1.8";

  /**
   * Feature name that should be used to set if the benchmarking logging should
   * be enabled or disabled.
   */
  protected static final String ENABLE_BENCHMARKING_FEATURE_NAME =
    "gate.enable.benchmark";

  /** Is true if GATE is to be run in a sandbox **/
  private static boolean sandboxed = true;

  /**
   * Find out if GATE is to be run in a sandbox or not. If true then
   * GATE will not attempt to load any local configuration information during
   * Initialisation making it possible to use GATE from within unsigned
   * applets and web start applications.
   * @return true if GATE is to be run in a sandbox, false otherwise
   */
  public static boolean isSandboxed() {
    return sandboxed;
  }

  /**
   * Method to tell GATE if it is being run inside a JVM sandbox. If true then
   * GATE will not attempt to load any local configuration information during
   * Initialisation making it possible to use GATE from within unsigned
   * applets and web start applications.
   * @param sandboxed true if GATE is to be run in a sandbox, false otherwise
   */
  public static void runInSandbox(boolean sandboxed) {
    if (initFinished)
      throw new IllegalStateException("Sandbox status cannot be changed after GATE has been initialised!");

    Gate.sandboxed = sandboxed;
  }

  /** Get the minimum supported version of the JDK */
  public static String getMinJdkVersion() {
    return MIN_JDK_VERSION;
  }

  /**
   * Initialisation - must be called by all clients before using any other parts
   * of the library. Also initialises the CREOLE register and reads config data (<TT>gate.xml</TT>
   * files).
   *
   * @see #initCreoleRegister
   */
  public static void init() throws GateException {
    // init local paths
    if (!sandboxed) initLocalPaths();

    // check if benchmarking should be enabled or disabled
    if(System.getProperty(ENABLE_BENCHMARKING_FEATURE_NAME) != null
      && System.getProperty(ENABLE_BENCHMARKING_FEATURE_NAME).equalsIgnoreCase(
        "true")) {
      Benchmark.setBenchmarkingEnabled(true);
    }

    // builtin creole dir
    if(builtinCreoleDir == null) {
      String builtinCreoleDirPropertyValue =
        System.getProperty(BUILTIN_CREOLE_DIR_PROPERTY_NAME);
      if(builtinCreoleDirPropertyValue == null) {
        // default to /gate/resources/creole in gate.jar
        builtinCreoleDir = Files.getGateResource("/creole/");
      }
      else {
        String builtinCreoleDirPath = builtinCreoleDirPropertyValue;
        // add a slash onto the end of the path if it doesn't have one already -
        // a creole directory URL should always end with a forward slash
        if(!builtinCreoleDirPath.endsWith("/")) {
          builtinCreoleDirPath += "/";
        }
        try {
          builtinCreoleDir = new URL(builtinCreoleDirPath);
        }
        catch(MalformedURLException mue) {
          // couldn't parse as a File either, so throw an exception
          throw new GateRuntimeException(BUILTIN_CREOLE_DIR_PROPERTY_NAME
            + " value \"" + builtinCreoleDirPropertyValue + "\" could"
            + " not be parsed as either a URL or a file path.");
        }
        log.info("Using " + builtinCreoleDir + " as built-in CREOLE"
          + " directory URL");
      }
    }

    // initialise the symbols generator
    lastSym = 0;

    // create class loader and creole register if they're null
    if(classLoader == null)
      classLoader = new GateClassLoader("Top-Level GATE ClassLoader", Gate.class.getClassLoader());
    if(creoleRegister == null) creoleRegister = new CreoleRegisterImpl();
    if(knownPlugins == null) knownPlugins = new HashSet<Plugin>();
    if(autoloadPlugins == null) autoloadPlugins = new ArrayList<Plugin>();
    //if(pluginData == null) pluginData = new HashSet<Plugin>();
    // init the creole register
    initCreoleRegister();
    // init the data store register
    initDataStoreRegister();

    // read gate.xml files; this must come before creole register
    // initialisation in order for the CREOLE-DIR elements to have and effect
    if (!sandboxed) initConfigData();

    if (!sandboxed) initCreoleRepositories();
    // the creoleRegister acts as a proxy for datastore related events
    dataStoreRegister.addCreoleListener(creoleRegister);

    // some of the events are actually fired by the {@link gate.Factory}
    Factory.addCreoleListener(creoleRegister);

    // check we have a useable JDK
    if(System.getProperty("java.version").compareTo(MIN_JDK_VERSION) < 0) { throw new GateException(
      "GATE requires JDK " + MIN_JDK_VERSION + " or newer"); }

    initFinished = true;
  } // init()

  /** Have we successfully run {@link #init()} before? */
  public static boolean isInitialised() { return initFinished; }

  /** Records initialisation status. */
  protected static boolean initFinished = false;

  /**
   * Initialises the paths to local files of interest like the GATE home, the
   * installed plugins home and site and user configuration files.
   */
  protected static void initLocalPaths() {
    // GATE Home
	  //TODO check if we need any of this now we don't have a GATE home
    /*if(gateHome == null) {
      String gateHomeStr = System.getProperty(GATE_HOME_PROPERTY_NAME);
      if(gateHomeStr != null && gateHomeStr.length() > 0) {
        gateHome = new File(gateHomeStr);
      }
      // if failed, try to guess
      if(gateHome == null || !gateHome.exists()) {
        log.warn("GATE home system property (\"" + GATE_HOME_PROPERTY_NAME
          + "\") not set.\nAttempting to guess...");
        URL gateURL =
          Thread.currentThread().getContextClassLoader().getResource(
            "gate/Gate.class");
        try {
          if(gateURL.getProtocol().equals("jar")) {
            // running from gate.jar
            String gateURLStr = gateURL.getFile();
            File gateJarFile =
              new File(
                new URI(gateURLStr.substring(0, gateURLStr.indexOf('!'))));
            gateHome = gateJarFile.getParentFile().getParentFile();
          }
          else if(gateURL.getProtocol().equals("file")) {
            // running from classes directory
            File gateClassFile = Files.fileFromURL(gateURL);
            gateHome =
              gateClassFile.getParentFile().getParentFile().getParentFile();
          }
          log.warn("Using \"" + gateHome.getCanonicalPath()
            + "\" as GATE Home.\nIf this is not correct please set it manually"
            + " using the -D" + GATE_HOME_PROPERTY_NAME
            + " option in your start-up script");
        }
        catch(Throwable thr) {
          throw new GateRuntimeException(
            "Cannot guess GATE Home. Pease set it manually!", thr);
        }
      }
    }
    log.info("Using " + gateHome.toString() + " as GATE home");

    // Plugins home
    if(pluginsHome == null) {
      String pluginsHomeStr = System.getProperty(PLUGINS_HOME_PROPERTY_NAME);
      if(pluginsHomeStr != null && pluginsHomeStr.length() > 0) {
        File homeFile = new File(pluginsHomeStr);
        if(homeFile.exists() && homeFile.isDirectory()) {
          pluginsHome = homeFile;
        }
      }
      // if not set, use the GATE Home as a base directory
      if(pluginsHome == null) {
        File homeFile = new File(gateHome, PLUGINS);
        if(homeFile.exists() && homeFile.isDirectory()) {
          pluginsHome = homeFile;
        }
      }
      // if still not set, throw exception
      if(pluginsHome == null) { throw new GateRuntimeException(
        "Could not infer installed plug-ins home!\n"
          + "Please set it manually using the -D" + PLUGINS_HOME_PROPERTY_NAME
          + " option in your start-up script."); }
    }
    log.info("Using " + pluginsHome.toString()
      + " as installed plug-ins directory.");

    // site config
    if(siteConfigFile == null) {
      String siteConfigStr = System.getProperty(SITE_CONFIG_PROPERTY_NAME);
      if(siteConfigStr != null && siteConfigStr.length() > 0) {
        File configFile = new File(siteConfigStr);
        if(configFile.exists()) siteConfigFile = configFile;
      }
      // if not set, use GATE home as base directory
      if(siteConfigFile == null) {
        File configFile = new File(gateHome, GATE_DOT_XML);
        if(configFile.exists()) siteConfigFile = configFile;
      }
      // if still not set, throw exception
      if(siteConfigFile == null) { throw new GateRuntimeException(
        "Could not locate the site configuration file!\n"
          + "Please create it at "
          + new File(gateHome, GATE_DOT_XML).toString()
          + " or point to an existing one using the -D"
          + SITE_CONFIG_PROPERTY_NAME + " option in your start-up script!"); }
    }
    log.info("Using " + siteConfigFile.toString()
      + " as site configuration file.");*/

    // user config
    if(userConfigFile == null) {
      String userConfigStr = System.getProperty(USER_CONFIG_PROPERTY_NAME);
      if(userConfigStr != null && userConfigStr.length() > 0) {
        userConfigFile = new File(userConfigStr);
      } else {
        userConfigFile = new File(getDefaultUserConfigFileName());
      }
    }
    log.info("Using " + userConfigFile + " as user configuration file");

    // user session
    if(userSessionFile == null) {
      String userSessionStr =
        System.getProperty(GATE_USER_SESSION_PROPERTY_NAME);
      if(userSessionStr != null && userSessionStr.length() > 0) {
        userSessionFile = new File(userSessionStr);
      }
      else {
        userSessionFile = new File(getDefaultUserSessionFileName());
      }
    }// if(userSessionFile == null)
    log.info("Using " + userSessionFile + " as user session file");
  }

  /**
   * Loads the CREOLE repositories (aka plugins) that the user has selected for
   * automatic loading. Loads the information about known plugins in memory.
   */
  protected static void initCreoleRepositories() {
    // the logic is:
    // get the list of know plugins from gate.xml
    // add all the installed plugins
    // get the list of loadable plugins
    // load loadable plugins

    //TODO reinstate this bit
    
    // process the known plugins list
    /*String knownPluginsPath =
      (String)getUserConfig().get(KNOWN_PLUGIN_PATH_KEY);
    if(knownPluginsPath != null && knownPluginsPath.length() > 0) {
      StringTokenizer strTok =
        new StringTokenizer(knownPluginsPath, ";", false);
      while(strTok.hasMoreTokens()) {
        String aKnownPluginPath = strTok.nextToken();
        try {
          URL aPluginURL = new URL(aKnownPluginPath);
          addKnownPlugin(aPluginURL);
        }
        catch(MalformedURLException mue) {
          log.error("Plugin error: " + aKnownPluginPath + " is an invalid URL!");
        }
      }
    }
    // add all the installed plugins
    // pluginsHome is now set by initLocalPaths
    // File pluginsHome = new File(System.getProperty(GATE_HOME_PROPERTY_NAME),
    // "plugins");
    File[] dirs = pluginsHome.listFiles();
    for(int i = 0; i < dirs.length; i++) {
      File creoleFile = new File(dirs[i], "creole.xml");
      if(creoleFile.exists()) {
        try {
          URL pluginURL = dirs[i].toURI().toURL();
          addKnownPlugin(new Plugin.Directory(pluginURL));
        }
        catch(MalformedURLException mue) {
          // this should never happen
          throw new GateRuntimeException(mue);
        }
      }
    }*/
    
    // register plugins installed in the user plugin directory
    File userPluginsHome = PluginUpdateManager.getUserPluginsHome();
    if (userPluginsHome != null && userPluginsHome.isDirectory()) {
      for (File dir : userPluginsHome.listFiles()) {
        File creoleFile = new File(dir, "creole.xml");
        if(creoleFile.exists()) {
          try {
            URL pluginURL = dir.toURI().toURL();
            addKnownPlugin(new Plugin.Directory(pluginURL));
          }
          catch(MalformedURLException mue) {
            // this should never happen
            throw new GateRuntimeException(mue);
          }
        }
      }
    }

    // process the autoload plugins
    String pluginPath = getUserConfig().getString(AUTOLOAD_PLUGIN_PATH_KEY);
    // can be overridden by system property
    String prop = System.getProperty(AUTOLOAD_PLUGIN_PATH_PROPERTY_NAME);
    if(prop != null && prop.length() > 0) pluginPath = prop;

    if(pluginPath == null || pluginPath.length() == 0) {
      // no plugin to load stop here
      return;
    }

    // load all loadable plugins
    StringTokenizer strTok = new StringTokenizer(pluginPath, ";", false);
    while(strTok.hasMoreTokens()) {
      String aDir = strTok.nextToken();
      try {
        URL aPluginURL = new URL(aDir);
        Plugin plugin = new Plugin.Directory(aPluginURL);
        addAutoloadPlugin(plugin);
      }
      catch(MalformedURLException mue) {
        log.error("Cannot load " + aDir + " CREOLE repository.",mue);
      }
      try {
        Iterator<Plugin> loadPluginsIter = getAutoloadPlugins().iterator();
        while(loadPluginsIter.hasNext()) {
          getCreoleRegister().registerPlugin(loadPluginsIter.next());
        }
      }
      catch(GateException ge) {
        log.error("Cannot load " + aDir + " CREOLE repository.",ge);
      }
    }
  }

  /** Initialise the CREOLE register. */
  public static void initCreoleRegister() throws GateException {

    /*
     * We'll have to think about this. Right now it points to the creole inside
     * the jar/classpath so it's the same as registerBuiltins
     */
    creoleRegister.registerBuiltins();
  }

  /** Initialise the DataStore register. */
  public static void initDataStoreRegister() {
    dataStoreRegister = new DataStoreRegister();
  } // initDataStoreRegister()

  /**
   * Reads config data (<TT>gate.xml</TT> files). There are three sorts of
   * these files:
   * <UL>
   * <LI> The builtin file from GATE's resources - this is read first.
   * <LI> A site-wide init file given as a command-line argument or as a
   * <TT>gate.config</TT> property - this is read second.
   * <LI> The user's file from their home directory - this is read last.
   * </UL>
   * Settings from files read after some settings have already been made will
   * simply overwrite the previous settings.
   */
  public static void initConfigData() throws GateException {
    ConfigDataProcessor configProcessor = new ConfigDataProcessor();
    URL configURL;

    // parse the site configuration file    
    if (siteConfigFile != null && siteConfigFile.exists()) {
    	//TODO should we set this to something sensible when we init paths now there is no gate home?
	    
	    try {
	      configURL = siteConfigFile.toURI().toURL();
	    }
	    catch(MalformedURLException mue) {
	      // this should never happen
	      throw new GateRuntimeException(mue);
	    }
	    try {
	      InputStream configStream = new FileInputStream(siteConfigFile);
	      configProcessor.parseConfigFile(configStream, configURL);
	    }
	    catch(IOException e) {
	      throw new GateException("Couldn't open site configuration file: "
	        + configURL + " " + e);
	    }
    }

    // parse the user configuration data if present
    if(userConfigFile != null && userConfigFile.exists()) {
      try {
        configURL = userConfigFile.toURI().toURL();
      }
      catch(MalformedURLException mue) {
        // this should never happen
        throw new GateRuntimeException(mue);
      }
      try {
        InputStream configStream = new FileInputStream(userConfigFile);
        configProcessor.parseConfigFile(configStream, configURL);
      }
      catch(IOException e) {
        throw new GateException("Couldn't open user configuration file: "
          + configURL + " " + e);
      }
    }

    // remember the init-time config options
    originalUserConfig.putAll(userConfig);
  } // initConfigData()

  /**
   * Flag controlling whether we should try to access the net, e.g. when setting
   * up a base URL.
   */
  private static boolean netConnected = false;

  private static int lastSym;

  /**
   * A list of names of classes that implement {@link gate.creole.ir.IREngine}
   * that will be used as information retrieval engines.
   */
  private static Set<String> registeredIREngines = new HashSet<String>();

  /**
   * Registers a new IR engine. The class named should implement
   * {@link gate.creole.ir.IREngine} and be accessible via the GATE class
   * loader.
   *
   * @param className
   *          the fully qualified name of the class to be registered
   * @throws GateException
   *           if the class does not implement the
   *           {@link gate.creole.ir.IREngine} interface.
   * @throws ClassNotFoundException
   *           if the named class cannot be found.
   */
  public static void registerIREngine(String className) throws GateException,
    ClassNotFoundException {
    Class<?> aClass = Class.forName(className, true, Gate.getClassLoader());
    if(gate.creole.ir.IREngine.class.isAssignableFrom(aClass)) {
      registeredIREngines.add(className);
    }
    else {
      throw new GateException(className + " does not implement the "
        + gate.creole.ir.IREngine.class.getName() + " interface!");
    }
  }

  /**
   * Unregisters a previously registered IR engine.
   *
   * @param className
   *          the name of the class to be removed from the list of registered IR
   *          engines.
   * @return true if the class was found and removed.
   */
  public static boolean unregisterIREngine(String className) {
    return registeredIREngines.remove(className);
  }

  /**
   * Gets the set of registered IR engines.
   *
   * @return an unmodifiable {@link java.util.Set} value.
   */
  public static Set<String> getRegisteredIREngines() {
    return Collections.unmodifiableSet(registeredIREngines);
  }

  /**
   * Gets the GATE home location.
   *
   * @return a File value.
   */
  public static File getGateHome() {
    return gateHome;
  }

  /**
   * Checks whether a particular class is a Gate defined type
   */
  public static boolean isGateType(String classname) {
    boolean res = getCreoleRegister().containsKey(classname);
    if(!res) {
      try {
        Class<?> aClass = Class.forName(classname, true, Gate.getClassLoader());
        res =
          Resource.class.isAssignableFrom(aClass);
      }
      catch(ClassNotFoundException cnfe) {
        return false;
      }
    }
    return res;
  }

  /** Returns the value for the HIDDEN attribute of a feature map */
  static public boolean getHiddenAttribute(FeatureMap fm) {
    if(fm == null) return false;
    Object value = fm.get(HIDDEN_FEATURE_KEY);
    return value != null && value instanceof String
      && ((String)value).equals("true");
  }

  /** Sets the value for the HIDDEN attribute of a feature map */
  static public void setHiddenAttribute(FeatureMap fm, boolean hidden) {
    if(hidden) {
      fm.put(HIDDEN_FEATURE_KEY, "true");
    }
    else {
      fm.remove(HIDDEN_FEATURE_KEY);
    }
  }

  /**
   * Registers a {@link gate.event.CreoleListener} with the Gate system
   */
  public static synchronized void addCreoleListener(CreoleListener l) {
    creoleRegister.addCreoleListener(l);
  } // addCreoleListener

  /**
   * Class loader used e.g. for loading CREOLE modules, of compiling JAPE rule
   * RHSs.
   */
  private static GateClassLoader classLoader = null;

  /** Get the GATE class loader. */
  public static GateClassLoader getClassLoader() {
    return classLoader;
  }

  /** The CREOLE register. */
  private static CreoleRegister creoleRegister = null;

  /** Get the CREOLE register. */
  public static CreoleRegister getCreoleRegister() {
    return creoleRegister;
  }

  /** The DataStore register */
  private static DataStoreRegister dataStoreRegister = null;

  /**
   * The current executable under execution.
   */
  private static gate.Executable currentExecutable;

  /** Get the DataStore register. */
  public static DataStoreRegister getDataStoreRegister() {
    return dataStoreRegister;
  } // getDataStoreRegister

  /**
   * Sets the {@link Executable} currently under execution. At a given time
   * there can be only one executable set. After the executable has finished its
   * execution this value should be set back to null. An attempt to set the
   * executable while this value is not null will result in the method call
   * waiting until the old executable is set to null.
   */
  public synchronized static void setExecutable(gate.Executable executable) {
    if(executable == null)
      currentExecutable = executable;
    else {
      while(getExecutable() != null) {
        try {
          Thread.sleep(200);
        }
        catch(InterruptedException ie) {
          throw new GateRuntimeException(ie.toString());
        }
      }
      currentExecutable = executable;
    }
  } // setExecutable

  /**
   * Returns the curently set executable.
   *
   * @see #setExecutable(gate.Executable)
   */
  public synchronized static gate.Executable getExecutable() {
    return currentExecutable;
  } // getExecutable

  /**
   * Returns a new unique string
   */
  public synchronized static String genSym() {
    StringBuffer buff =
      new StringBuffer(Integer.toHexString(lastSym++).toUpperCase());
    for(int i = buff.length(); i <= 4; i++)
      buff.insert(0, '0');
    return buff.toString();
  } // genSym

  /** GATE development environment configuration data (stored in gate.xml). */
  private static OptionsMap userConfig = new OptionsMap();

  /**
   * This map stores the init-time config data in case we need it later. GATE
   * development environment configuration data (stored in gate.xml).
   */
  private static OptionsMap originalUserConfig = new OptionsMap();

  /** Name of the XML element for GATE development environment config data. */
  private static String userConfigElement = "GATECONFIG";

  /**
   * Gate the name of the XML element for GATE development environment config
   * data.
   */
  public static String getUserConfigElement() {
    return userConfigElement;
  }

  /**
   * Get the site config file (generally set during command-line processing or
   * as a <TT>gate.config</TT> property). If the config is null, this method
   * checks the <TT>gate.config</TT> property and uses it if non-null.
   */
  public static File getSiteConfigFile() {
    if(siteConfigFile == null) {
      String gateConfigProperty = System.getProperty(GATE_CONFIG_PROPERTY);
      if(gateConfigProperty != null)
        siteConfigFile = new File(gateConfigProperty);
    }
    return siteConfigFile;
  } // getSiteConfigFile

  /** Set the site config file (e.g. during command-line processing). */
  public static void setSiteConfigFile(File siteConfigFile) {
    Gate.siteConfigFile = siteConfigFile;
  } // setSiteConfigFile

  /** Shorthand for local newline */
  private static String nl = Strings.getNl();

  /** An empty config data file. */
  private static String emptyConfigFile =
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + nl + "<!-- " + GATE_DOT_XML
      + ": GATE configuration data -->" + nl + "<GATE>" + nl + "" + nl
      + "<!-- NOTE: the next element may be overwritten by the GUI!!! -->" + nl
      + "<" + userConfigElement + "/>" + nl + "" + nl + "</GATE>" + nl;

  /**
   * Get an empty config file. <B>NOTE:</B> this method is intended only for
   * use by the test suite.
   */
  public static String getEmptyConfigFile() {
    return emptyConfigFile;
  }

  /**
   * Get the GATE development environment configuration data (initialised from
   * <TT>gate.xml</TT>).
   */
  public static OptionsMap getUserConfig() {
    return userConfig;
  }

  /**
   * Get the original, initialisation-time, GATE development environment
   * configuration data (initialised from <TT>gate.xml</TT>).
   */
  public static OptionsMap getOriginalUserConfig() {
    return originalUserConfig;
  } // getOriginalUserConfig

  /**
   * Update the GATE development environment configuration data in the user's
   * <TT>gate.xml</TT> file (create one if it doesn't exist).
   */
  public static void writeUserConfig() throws GateException {

    //if we are running in a sandbox then don't try and write anything
    if (sandboxed) return;

    /*String pluginsHomeStr;
    try {
      pluginsHomeStr = pluginsHome.getCanonicalPath();
    }
    catch(IOException ioe) {
      throw new GateRuntimeException(
        "Problem while locating the plug-ins home!", ioe);
    }*/
        
    String userPluginHomeStr;
    try {
      File userPluginHome = PluginUpdateManager.getUserPluginsHome();
      userPluginHomeStr = (userPluginHome != null ? userPluginHome.getCanonicalPath() : null);
    }
    catch (IOException ioe) {
      throw new GateRuntimeException("Unable to access user plugin directory!", ioe);
    }
    
    //TODO need to reinstate this
    
    // update the values for knownPluginPath
    /*String knownPluginPath = "";
    Iterator<URL> pluginIter = getKnownPlugins().iterator();
    while(pluginIter.hasNext()) {
      URL aPluginURL = pluginIter.next();
      // do not save installed plug-ins - they get loaded automatically
      if(aPluginURL.getProtocol().equals("file")) {
        File pluginDirectory = Files.fileFromURL(aPluginURL);
        try {
          if(pluginDirectory.getCanonicalPath().startsWith(pluginsHomeStr))
            continue;
          
          if (userPluginHomeStr != null && pluginDirectory.getCanonicalPath().startsWith(userPluginHomeStr))
            continue;
        }
        catch(IOException ioe) {
          throw new GateRuntimeException("Problem while locating the plug-in"
            + aPluginURL.toString(), ioe);
        }
      }
      if(knownPluginPath.length() > 0) knownPluginPath += ";";
      knownPluginPath += aPluginURL.toExternalForm();
    }
    getUserConfig().put(KNOWN_PLUGIN_PATH_KEY, knownPluginPath);*/

    // update the autoload plugin list
    /*String loadPluginPath = "";
    pluginIter = getAutoloadPlugins().iterator();
    while(pluginIter.hasNext()) {
      URL aPluginURL = pluginIter.next();
      if(loadPluginPath.length() > 0) loadPluginPath += ";";
      loadPluginPath += aPluginURL.toExternalForm();
    }
    getUserConfig().put(AUTOLOAD_PLUGIN_PATH_KEY, loadPluginPath);*/

    // the user's config file
    // String configFileName = getUserConfigFileName();
    // File configFile = new File(configFileName);
    File configFile = getUserConfigFile();

    // create if not there, then update
    try {
      // if the file doesn't exist, create one with an empty GATECONFIG
      if(!configFile.exists()) {
        FileOutputStream fos = new FileOutputStream(configFile);
        OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
        writer.write(emptyConfigFile);
        writer.close();
      }

      // update the config element of the file
      Files.updateXmlElement(configFile, userConfigElement, userConfig.getStringMap());

    }
    catch(IOException e) {
      throw new GateException("problem writing user " + GATE_DOT_XML + ": "
        + nl + e.toString());
    }
  } // writeUserConfig

  /**
   * Get the default path to the user's config file, which is used unless an
   * alternative name has been specified via system properties or
   * {@link #setUserConfigFile}.
   *
   * @return the default user config file path.
   */
  public static String getDefaultUserConfigFileName() {
    String filePrefix = "";
    if(runningOnUnix()) filePrefix = ".";

    String userConfigName =
      System.getProperty("user.home") + Strings.getFileSep() + filePrefix
        + GATE_DOT_XML;
    return userConfigName;
  } // getDefaultUserConfigFileName

  /**
   * Get the default path to the user's session file, which is used unless an
   * alternative name has been specified via system properties or
   * {@link #setUserSessionFile(File)}
   *
   * @return the default user session file path.
   */
  public static String getDefaultUserSessionFileName() {
    String filePrefix = "";
    if(runningOnUnix()) filePrefix = ".";

    String userSessionName =
      System.getProperty("user.home") + Strings.getFileSep() + filePrefix
        + GATE_DOT_SER;

    return userSessionName;
  } // getUserSessionFileName

  /**
   * This method tries to guess if we are on a UNIX system. It does this by
   * checking the value of <TT>System.getProperty("file.separator")</TT>; if
   * this is "/" it concludes we are on UNIX. <B>This is obviously not a very
   * good idea in the general case, so nothing much should be made to depend on
   * this method (e.g. just naming of config file <TT>.gate.xml</TT> as
   * opposed to <TT>gate.xml</TT>)</B>.
   */
  public static boolean runningOnUnix() {
    return Strings.getFileSep().equals("/");
  } // runningOnUnix

  /**
   * This method tries to guess if we are on a Mac OS X system.  It does this
   * by checking the value of <TT>System.getProperty("os.name")</TT>.  Note
   * that if this method returns true, {@link #runningOnUnix()} will also
   * return true (i.e. Mac is considered a Unix platform) but the reverse is
   * not necessarily the case.
   */
  public static boolean runningOnMac() {
    return System.getProperty("os.name").toLowerCase().startsWith("mac os x");
  }

  /**
   * Returns the list of CREOLE directories the system knows about (either
   * pre-installed plugins in the plugins directory or CREOLE directories that
   * have previously been loaded manually).
   *
   * @return a {@link List} of {@link URL}s.
   */
  public static Set<Plugin> getKnownPlugins() {
    return knownPlugins;
  }

  /**
   * Adds the plugin to the list of known plugins.
   *
   * @param plugin
   *          the plugin to add to the list of known plugins.
   */
  public static void addKnownPlugin(Plugin plugin) {
    if(knownPlugins.contains(plugin)) return;
    knownPlugins.add(plugin);
  }

  /**
   * Makes sure the provided URL ends with "/" (CREOLE URLs always point to
   * directories so thry should always end with a slash.
   *
   * @param url
   *          the URL to be normalised
   * @return the (maybe) corrected URL.
   */
  public static URL normaliseCreoleUrl(URL url) {
    // CREOLE URLs are directory URLs so they should end with "/"
    String urlName = url.toExternalForm();
    String separator = "/";
    if(urlName.endsWith(separator)) {
      return url;
    }
    else {
      urlName += separator;
      try {
        return new URL(urlName);
      }
      catch(MalformedURLException mue) {
        throw new GateRuntimeException(mue);
      }
    }
  }

  /**
   * Returns the list of CREOLE directories the system loads automatically at
   * start-up.
   *
   * @return a {@link List} of {@link URL}s.
   */
  public static List<Plugin> getAutoloadPlugins() {
    return autoloadPlugins;
  }

  /**
   * Adds a new directory to the list of plugins that are loaded automatically
   * at start-up.
   *
   * @param plugin
   *          the plugin to add to the list of autoload plugins.
   */
  public static void addAutoloadPlugin(Plugin plugin) {
    
    if(autoloadPlugins.contains(plugin)) return;
    // make sure it's known
    addKnownPlugin(plugin);
    // add it to autoload list
    autoloadPlugins.add(plugin);
  }

  /**
   * Gets the information about a known directory.
   *
   * @param directory
   *          the URL for the directory in question.
   * @return a {@link DirectoryInfo} value.
   */
  /*public static DirectoryInfo getDirectoryInfo(URL directory) {
    directory = normaliseCreoleUrl(directory);
    if(!knownPlugins.contains(directory)) return null;
    DirectoryInfo dInfo = pluginData.get(directory);
    if(dInfo == null) {
      dInfo = new DirectoryInfo(directory);
      pluginData.put(directory, dInfo);
    }
    return dInfo;
  }*/
  
  /*public static DirectoryInfo getDirectoryInfo(URL directory, org.jdom.Document creoleDoc) {
    directory = normaliseCreoleUrl(directory);
    if(!knownPlugins.contains(directory)) return null;
    DirectoryInfo dInfo = pluginData.get(directory);
    if(dInfo == null) {
      dInfo = new DirectoryInfo(directory, creoleDoc);
      pluginData.put(directory, dInfo);
    }
    return dInfo;
  }*/
  
  /**
   * Returns information about plugin directories which provide the requested
   * resource
   * 
   * @param resourceClassName
   *          the class name of the resource you are interested in
   * @return information about the directories which provide an implementation
   *         of the requested resource
   */
  public static Set<Plugin> getPlugins(String resourceClassName) {
    Set<Plugin> dirs = new HashSet<Plugin>();
    
    for (Plugin plugin: knownPlugins) {
      
      
      for (ResourceInfo rInfo : plugin.getResourceInfoList()) {
        if (rInfo.resourceClassName.equals(resourceClassName)) {
          dirs.add(plugin);
        }
      }
    }
    
    return dirs;
  }

  /**
   * Tells the system to &quot;forget&quot; about one previously known
   * directory. If the specified directory was loaded, it will be unloaded as
   * well - i.e. all the metadata relating to resources defined by this
   * directory will be removed from memory.
   *
   * @param plugin the the plugin to remove from the list of known plugins
   **/
  public static void removeKnownPlugin(Plugin plugin) {
    //pluginURL = normaliseCreoleUrl(pluginURL);
    knownPlugins.remove(plugin);
    autoloadPlugins.remove(plugin);
    
    creoleRegister.unregisterPlugin(plugin);    
  }

  /**
   * Tells the system to remove a plugin URL from the list of plugins that are
   * loaded automatically at system start-up. This will be reflected in the
   * user's configuration data file.
   *
   * @param plugin
   *          the plugin to remove from the list of autoload plugins
   */
  public static void removeAutoloadPlugin(Plugin plugin) {
    autoloadPlugins.remove(plugin);
  }
  
  /**
   * Stores information about a resource defined by a CREOLE directory. The
   * resource might not have been loaded in the system so not all information
   * normally provided by the {@link ResourceData} class is available. This is
   * what makes this class different from {@link ResourceData}.
   */
  public static class ResourceInfo {
    public ResourceInfo(String name, String className, String comment) {
      this.resourceClassName = className;
      this.resourceName = name;
      this.resourceComment = comment;
    }
    
    public String toString() {
      return resourceName+" ("+resourceClassName+")";
    }

    /**
     * @return Returns the resourceClassName.
     */
    public String getResourceClassName() {
      return resourceClassName;
    }

    /**
     * @return Returns the resourceComment.
     */
    public String getResourceComment() {
      return resourceComment;
    }
    
    public void setResourceComment(String resourceComment) {
    	this.resourceComment = resourceComment;
    }

    /**
     * @return Returns the resourceName.
     */
    public String getResourceName() {
      return resourceName;
    }
    
    public void setResourceName(String resourceName) {
    	this.resourceName = resourceName;
    }

    /**
     * The class for the resource.
     */
    protected String resourceClassName;

    /**
     * The resource name.
     */
    protected String resourceName;

    /**
     * The comment for the resource.
     */
    protected String resourceComment;
  }

  /**
   * The top level directory of the GATE installation.
   */
  protected static File gateHome;

  /** Site config file */
  private static File siteConfigFile;

  /** User config file */
  private static File userConfigFile;

  /**
   * The top level directory for GATE installed plugins.
   */
  protected static File pluginsHome;

  /**
   * The "builtin" creole directory URL, where the creole.xml that defines
   * things like DocumentImpl can be found.
   */
  protected static URL builtinCreoleDir;

  /**
   * The user session file to use.
   */
  protected static File userSessionFile;

  /**
   * Set the location of the GATE home directory.
   *
   * @throws IllegalStateException
   *           if the value has already been set.
   */
  public static void setGateHome(File gateHome) {
    if(Gate.gateHome != null) { throw new IllegalStateException(
      "gateHome has already been set"); }
    Gate.gateHome = gateHome;
  }

  /**
   * Set the location of the plugins directory.
   *
   * @throws IllegalStateException
   *           if the value has already been set.
   */
  public static void setPluginsHome(File pluginsHome) {
    if(Gate.pluginsHome != null) { throw new IllegalStateException(
      "pluginsHome has already been set"); }
    Gate.pluginsHome = pluginsHome;
  }

  /**
   * Get the location of the plugins directory.
   *
   * @return the plugins drectory, or null if this has not yet been set (i.e.
   *         <code>Gate.init()</code> has not yet been called).
   */
  public static File getPluginsHome() {
    return pluginsHome;
  }

  /**
   * Set the location of the user's config file.
   *
   * @throws IllegalStateException
   *           if the value has already been set.
   */
  public static void setUserConfigFile(File userConfigFile) {
    if(Gate.userConfigFile != null) { throw new IllegalStateException(
      "userConfigFile has already been set"); }
    Gate.userConfigFile = userConfigFile;
  }

  /**
   * Get the location of the user's config file.
   *
   * @return the user config file, or null if this has not yet been set (i.e.
   *         <code>Gate.init()</code> has not yet been called).
   */
  public static File getUserConfigFile() {
    return userConfigFile;
  }

  /**
   * Set the URL to the "builtin" creole directory. The URL must point to a
   * directory, and must end with a forward slash.
   *
   * @throws IllegalStateException
   *           if the value has already been set.
   */
  public static void setBuiltinCreoleDir(URL builtinCreoleDir) {
    if(Gate.builtinCreoleDir != null) { throw new IllegalStateException(
      "builtinCreoleDir has already been set"); }
    Gate.builtinCreoleDir = builtinCreoleDir;
  }

  /**
   * Get the URL to the "builtin" creole directory, i.e. the directory that
   * contains the creole.xml file that defines things like DocumentImpl, the
   * Controllers, etc.
   */
  public static URL getBuiltinCreoleDir() {
    return builtinCreoleDir;
  }

  /**
   * Set the user session file. This can only done prior to calling Gate.init()
   * which will set the file to either the OS-specific default or whatever has
   * been set by the property gate.user.session
   *
   * @throws IllegalStateException
   *           if the value has already been set.
   */
  public static void setUserSessionFile(File newUserSessionFile) {
    if(Gate.userSessionFile != null) { throw new IllegalStateException(
      "userSessionFile has already been set"); }
    Gate.userSessionFile = newUserSessionFile;
  }

  /**
   * Get the user session file.
   *
   * @return the file corresponding to the user session file or null, if not yet
   *         set.
   */
  public static File getUserSessionFile() {
    return userSessionFile;
  }

  /**
   * The list of plugins (aka CREOLE directories) the system knows about. This
   * list contains URL objects.
   */
  private static Set<Plugin> knownPlugins;

  /**
   * The list of plugins (aka CREOLE directories) the system loads automatically
   * at start-up. This list contains URL objects.
   */
  protected static List<Plugin> autoloadPlugins;

  /**
   * Flag for whether to use native serialization or xml serialization when
   * saving applications.
   */
  private static boolean useXMLSerialization = true;

  /**
   * Tell GATE whether to use XML serialization for applications.
   */
  public static void setUseXMLSerialization(boolean useXMLSerialization) {
    Gate.useXMLSerialization = useXMLSerialization;
  }

  /**
   * Should we use XML serialization for applications.
   */
  public static boolean getUseXMLSerialization() {
    return useXMLSerialization;
  }

  /**
   * Returns the listeners map, a map that holds all the listeners that
   * are singletons (e.g. the status listener that updates the status
   * bar on the main frame or the progress listener that updates the
   * progress bar on the main frame). The keys used are the class names
   * of the listener interface and the values are the actual listeners
   * (e.g "gate.event.StatusListener" -> this). The returned map is the
   * actual data member used to store the listeners so any changes in
   * this map will be visible to everyone.
   * @return the listeners map
   */
  public static java.util.Map<String, EventListener> getListeners() {
    return listeners;
  }

  /**
   * A Map which holds listeners that are singletons (e.g. the status
   * listener that updates the status bar on the main frame or the
   * progress listener that updates the progress bar on the main frame).
   * The keys used are the class names of the listener interface and the
   * values are the actual listeners (e.g "gate.event.StatusListener" ->
   * this).
   */
  private static final java.util.Map<String, EventListener> listeners =
    new HashMap<String, EventListener>();

} // class Gate
