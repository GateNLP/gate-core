/*
 *  Launcher.java
 *
 *  Copyright (c) 1995-2010, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 12 January 2012
 *
 *  $Id: Launcher.java 19983 2017-01-25 13:03:14Z markagreenwood $
 */
package gate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.java.ayatana.ApplicationMenu;
import org.java.ayatana.AyatanaDesktop;


/**
 * A simple launcher for GATE. It builds the correct classpath and starts GATE. 
 */
public class Launcher {
  
  public static final String GATE_HOME_PROPERTY_NAME = "gate.home";
  
  protected static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{(.*?)\\}");
  
  protected static final FilenameFilter JAR_FILTER = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
      return name.toLowerCase().endsWith(".jar");
    }
  };

  protected File gateHome;
  
  protected URLClassLoader classLoader;

  public void startGate(String[] args) throws ClassNotFoundException, 
      SecurityException, NoSuchMethodException, IllegalArgumentException, 
      IllegalAccessException, InvocationTargetException, URISyntaxException, 
      IOException {
    findGateHome();
    readSystemProperties();
    buildClassPath();
    Thread.currentThread().setContextClassLoader(classLoader);
    Class.forName("gate.Main", true, classLoader).getDeclaredMethod(
      "main", new Class[]{String[].class}).invoke(null, new Object[] {args});
    // try to register with Unity
    if (AyatanaDesktop.isSupported()){
      // the previous call will create a gate.gui.MainFrame in the swing thread
      // we queue an action to be called once that completes.
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          // get the MainFrame class
          try {
            JFrame mainFrame = (JFrame) Class.forName("gate.gui.MainFrame", 
              true, classLoader).getDeclaredMethod("getInstance").invoke(
              null, (Object[])null);
            if(mainFrame != null){
              ApplicationMenu.tryInstall(mainFrame);
            }
          } catch(Exception e) {
            // could not do registration... 
            // ignore
          }
        }
      });      
    }
  }
  
  protected void findGateHome() throws URISyntaxException {
    String gateHomeStr = System.getProperty(GATE_HOME_PROPERTY_NAME);
    if(gateHomeStr != null && gateHomeStr.length() > 0) {
      gateHome = new File(gateHomeStr);
    }
    if(gateHome == null || !gateHome.exists()) {
      URL gateURL = Launcher.class.getClassLoader().getResource(
          "gate/Launcher.class");
      if(gateURL.getProtocol().equals("jar")) {
        // running from gateLauncher.jar
        String gateURLStr = gateURL.getFile();
        File gateJarFile = new File(new URI(
            gateURLStr.substring(0, gateURLStr.indexOf('!'))));
        gateHome = gateJarFile.getParentFile().getParentFile();
      } else  {
        throw new RuntimeException("Could not find gateLauncher.jar! " +
        		"How has this launcher been started?");
      }
    }
  }
  
  protected void addUrlsForFile(File file, List<URL> urls) throws
      MalformedURLException {
    if(file != null) {
      if("*".equals(file.getName())) {
        // Java 6 style "/path/to/directory/*" entry
        for(File f : file.getParentFile().listFiles(JAR_FILTER)) {
          addUrlsForFile(f, urls);
        }
      } else {
        urls.add(file.toURI().toURL());
      }
    }
  }

  protected void buildClassPath() throws MalformedURLException {
    List<URL> urls = new LinkedList<URL>();
    // start with any externally-specified entries - gate.class.path system
    // property wins if it is specified, otherwise GATE_CLASSPATH environment
    // variable.
    String extraClassPath = System.getProperty("gate.class.path",
          System.getenv("GATE_CLASSPATH"));
    if(extraClassPath != null) {
      String[] extraEntries = extraClassPath.split(
          Pattern.quote(File.pathSeparator));
      for(String entry : extraEntries) {
        if(!"".equals(entry)) addUrlsForFile(new File(entry), urls);
      }
    }
    File binDir = new File(gateHome, "bin");
    // gate/bin (for log4j.properties)
    addUrlsForFile(binDir, urls);
    // bin/gate.jar
    File targetDir = new File(gateHome, "target");
    addUrlsForFile(new File(targetDir, "*"), urls);
    // and lib/*.jar
    File libDir = new File(gateHome, "lib");
    addUrlsForFile(new File(libDir, "*"), urls);

    classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]),
      Launcher.class.getClassLoader());
  }
  
  protected void readSystemProperties() throws IOException {
    File buildPropertiesFile = new File(gateHome, "build.properties");
    if(buildPropertiesFile.canRead()) {
      Properties buildProperties = new Properties();
      InputStream in = new FileInputStream(buildPropertiesFile);
      try {
        buildProperties.load(in);
      } finally {
        in.close();
      }
      
      
      String osPrefix = "os." + System.getProperty("os.name") + ".";
      for(String key : buildProperties.stringPropertyNames()) {
        if(key.startsWith("run.") && 
            System.getProperty(key.substring(4)) == null) {
          System.setProperty(key.substring(4), getPropertyValue(key,
            new HashSet<String>(), buildProperties, System.getProperties()));
        } else if(key.startsWith(osPrefix) && 
              System.getProperty(key.substring(osPrefix.length())) == null ) {
          System.setProperty(key.substring(osPrefix.length()), getPropertyValue(key,
            new HashSet<String>(), buildProperties, System.getProperties()));          
        }
      }
    }
    String gateHomeStr = System.getProperty(GATE_HOME_PROPERTY_NAME);
    if(gateHomeStr != null && gateHomeStr.length() > 0) {
      gateHome = new File(gateHomeStr);
    }
  }
  
  protected String getPropertyValue(String key, Set<String> seen, Properties... context) {
    String value = null;
    for(Properties p : context) {
      value = p.getProperty(key);
      if(value != null) break;
    }
    if(value != null) {
      Matcher m = PLACEHOLDER.matcher(value);
      if(m.find()) {
        m.reset();
        StringBuffer newValue = new StringBuffer();
        while(m.find()) {
          String varName = m.group(1);
          if(seen.contains(varName)) {
            throw new RuntimeException("Property " + varName +
              " is circularly defined.");
          }
          seen.add(varName);
          try {
            String varValue = getPropertyValue(varName, seen, context);
            if(varValue == null) {
              varValue = m.group();
            }
            m.appendReplacement(newValue, Matcher.quoteReplacement(varValue));
          } finally {
            seen.remove(varName);
          }
        }
        m.appendTail(newValue);
        value = newValue.toString();
      }
    }
    return value;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    Launcher launcher = new Launcher();
    try {
      launcher.startGate(args);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
