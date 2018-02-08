/*
 *  Main.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 1/Nov/00
 *
 *  $Id: Main.java 20194 2017-02-23 10:27:13Z markagreenwood $
 */

package gate;

import gate.creole.Plugin;
import gate.gui.MainFrame;
import gate.gui.OptionsDialog;
import gate.gui.creole.manager.PluginUpdateManager;
import gate.util.BomStrippingInputStreamReader;
import gate.util.Err;
import gate.util.Files;
import gate.util.GateException;
import gate.util.OptionsMap;
import gate.util.Out;
import gate.util.Strings;
import gate.util.ThreadWarningSystem;
import gate.util.persistence.PersistenceManager;
import gnu.getopt.Getopt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.management.ThreadInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/** Top-level entry point for the GATE command-line and GUI interfaces.
  * <P>
  */
public class Main {

  /** Debug flag */
  protected static final boolean DEBUG = false;

  /** Status flag for normal exit. */
  protected static final int STATUS_NORMAL = 0;

  /** Status flag for error exit. */
  protected static final int STATUS_ERROR = 1;

  /** Main routine for GATE.
    * Command-line arguments:
    * <UL>
    * <LI>
    * <B>-h</B> display a short help message
    * <LI>
    * <B>-d URL</B> define URL to be a location for CREOLE resoures
    * <LI>
    * <B>-i file</B> additional initialisation file (probably called
    *   <TT>gate.xml</TT>). Used for site-wide initialisation by the
    *   start-up scripts
    * </UL>
    */
  public static void main(String[] args) throws GateException {
    
    // check we have a useable JDK
    if(
      System.getProperty("java.version").compareTo(Gate.getMinJdkVersion())
      < 0
    ) {
      throw new GateException(
        "GATE requires JDK " + Gate.getMinJdkVersion() + " or newer"
      );
    }
    
    ThreadWarningSystem tws = new ThreadWarningSystem();
    tws.addListener(new ThreadWarningSystem.Listener() {
      
      final PrintStream out = System.out;
      
      @Override
      public void deadlockDetected(ThreadInfo inf) {
        out.println("Deadlocked Thread:");
        out.println("------------------");
        out.println(inf);
        for (StackTraceElement ste : inf.getStackTrace()) {
          out.println("\t" + ste);
        }
      }
      @Override
      public void thresholdExceeded(ThreadInfo[] threads) { }
    });

    // process command-line options
    processArgs(args);
    
    runGui();
  } // main

  /** Register any CREOLE URLs that we got on the command line */
  protected static void registerCreoleUrls() {
    CreoleRegister reg = Gate.getCreoleRegister();
    Iterator<URL> iter = pendingCreoleUrls.iterator();
    while(iter.hasNext()) {
      URL u = iter.next();
      try {
        reg.registerPlugin(new Plugin.Directory(u));
      } catch(GateException e) {
        Err.prln("Couldn't register CREOLE directory: " + u);
        Err.prln(e);
        System.exit(STATUS_ERROR);
      }
    }
  } // registerCreoleUrls()

  /** Main Frame of the GUI; null when no GUI running */
  protected static MainFrame frame;

  /**
   * Get the main frame of the GUI. If the GUI isn't running, it
   * is started.
   */
  public static MainFrame getMainFrame() throws GateException {
    if(frame == null)
      runGui();
    return frame;
  } // getMainFrame()

  /** Run the user interface. */
  protected static void runGui() throws GateException {

    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

    // initialise the library and load user CREOLE directories
    try{
      Gate.runInSandbox(false);
      Gate.init();
    }catch(Throwable t){
      log.error("Problem while initialising GATE", t);
      int selection = JOptionPane.showOptionDialog(
        null,
        "Error during initialisation:\n" + t.toString() +
        "\nDo you still want to start GATE?",
        "GATE", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
        null, new String[]{"Cancel", "Start anyway"},
        "Cancel");
      if(selection != 1){
        System.exit(1);
      }
    }

    //create the main frame, show it
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        GraphicsConfiguration gc = GraphicsEnvironment.
        getLocalGraphicsEnvironment().getDefaultScreenDevice().
        getDefaultConfiguration();

        //this needs to run before any GUI component is constructed.
        applyUserPreferences();

        //all the defaults tables have been updated; build the GUI
        frame = MainFrame.getInstance(gc);
        if(DEBUG) Out.prln("constructing GUI");
        
        // run the GUI
        frame.setTitleChangable(true);
        frame.setTitle(name + " " + version + " build " + build);
        
        // Set title from Java properties
        String title =
          System.getProperty(GateConstants.TITLE_JAVA_PROPERTY_NAME);
        if(title != null) {
          frame.setTitle(title);
        } // if
        frame.setTitleChangable(false);

        // Set icon from Java properties
        // iconName could be absolute or "gate:/img/..."
        String iconName =
          System.getProperty(GateConstants.APP_ICON_JAVA_PROPERTY_NAME);
        if(iconName != null) {
          try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
                  new URL(iconName)));
          } catch(MalformedURLException mue){
            log.warn("Could not load application icon.", mue);
          }
        } // if

        // Validate frames that have preset sizes
        frame.validate();

        // Center the window
        Rectangle screenBounds = gc.getBounds();
        Dimension screenSize = screenBounds.getSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);

        frame.setVisible(true);

        //load session if required and available;
        //do everything from a new thread.
        Runnable runnable = new Runnable(){
          @Override
          public void run(){
            try{
              File sessionFile = Gate.getUserSessionFile();
              if(sessionFile != null && sessionFile.exists()){
                MainFrame.lockGUI("Loading saved session...");
                PersistenceManager.loadObjectFromFile(sessionFile);
              }
            }catch(Exception e){
              log.warn("Failed to load session data", e);
            }finally{
              MainFrame.unlockGUI();
            }
          }
        };
        Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                                   runnable, "Session loader");
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
      }
    });
    registerCreoleUrls();
    
    new Thread() {
      @Override
      public void run() {
        // kick this off here so that it does any resolving of creole metadata
        // jars now rather than the first time you try and open the plugin
        // manager, which is still a bit slow 
        PluginUpdateManager.loadDefaultPlugins();
      }
    }.start();
  } // runGui()

  /**
   * Reads the user config data and applies the required settings.
   * This must be called <b>after</b> {@link Gate#init()} but <b>before</b>
   * any GUI components are created.
   */
  public static void applyUserPreferences(){
    //look and feel
    String lnfClassName;
    if(System.getProperty("swing.defaultlaf") != null) {
      lnfClassName = System.getProperty("swing.defaultlaf");
    } else {
      lnfClassName = Gate.getUserConfig().
                            getString(GateConstants.LOOK_AND_FEEL);
    }
    if(lnfClassName == null){
      //if running on Linux, default to Metal rather than GTK because GTK LnF
      //doesn't play nicely with most Gnome themes
      if(System.getProperty("os.name").toLowerCase().indexOf("linux") != -1){
        //running on Linux
        lnfClassName = UIManager.getCrossPlatformLookAndFeelClassName();
      }else{
        lnfClassName = UIManager.getSystemLookAndFeelClassName();
      }      
    }
    try {
      UIManager.setLookAndFeel(lnfClassName);
    } catch(Exception e) {
      System.err.print("Could not set your preferred Look and Feel. The error was:\n" +
              e.toString() + "\nReverting to using Java Look and Feel");
      try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      }catch(Exception e1) {
        //we just can't catch a break here. Let's forget about look and feel.
        System.err.print(
                "Could not set the cross-platform Look and Feel either. The error was:\n" +
                e1.toString() + "\nGiving up on Look and Feel.");
      }
    }
    Gate.getUserConfig().put(GateConstants.LOOK_AND_FEEL, lnfClassName);
    
    //read the user config data
    OptionsMap userConfig = Gate.getUserConfig();

    //text font
    Font font = userConfig.getFont(GateConstants.TEXT_COMPONENTS_FONT);
    if(font == null){
      font = UIManager.getFont("TextPane.font");
    }

    if(font != null){
      OptionsDialog.setTextComponentsFont(font);
    }

    //menus font
    font = userConfig.getFont(GateConstants.MENUS_FONT);
    if(font == null){
      font = UIManager.getFont("Menu.font");
    }

    if(font != null){
      OptionsDialog.setMenuComponentsFont(font);
    }

    //other gui font
    font = userConfig.getFont(GateConstants.OTHER_COMPONENTS_FONT);
    if(font == null){
      font = UIManager.getFont("Button.font");
    }

    if(font != null){
      OptionsDialog.setComponentsFont(font);
    }

  }



  // find out the version and build numbers
  static {
    
    // we can't have the possibility of assigning to a final variable twice so
    // we put the details into a temp variable and then once we are happy assign
    // it to the static final variable
    String temp;
    
    BufferedReader reader = null;
    
    // find out the version number    
    try {
      InputStream ver = Files.getGateResourceAsStream("version.txt");
      if (ver==null) {
        throw new IOException();
      }
      reader = new BomStrippingInputStreamReader(ver, "UTF-8");
      temp = reader.readLine();
    } catch(IOException ioe) {
      temp = "VERSION UNKNOWN";
    } finally {
      IOUtils.closeQuietly(reader);
    }
    
    version = temp;

    // find out the build number
    try{
      InputStream build = Files.getGateResourceAsStream("build.txt");
      if (build==null) {
        throw new IOException();
      }
      reader = new BomStrippingInputStreamReader(build, "UTF-8");
      temp = reader.readLine();
    } catch(IOException ioe) {
      temp = "0000";
    } finally {
      IOUtils.closeQuietly(reader);
    }
    
    build = temp;
  } // static initializer finding build and version

  public static final String name = "GATE Developer";
  public static final String version;
  public static final String build;
  
  private static final Logger log = Logger.getLogger(Main.class);

  /** Process arguments and set up member fields appropriately.
    * Will shut down the process (via System.exit) if there are
    * incorrect arguments, or if the arguments ask for something
    * simple like printing the help message.
    */
  public static void processArgs(String[] args) {

    Getopt g = new Getopt("GATE main", args, "hd:ei:");
    int c;
    while( (c = g.getopt()) != -1 )
      switch(c) {
        // -h
        case 'h':
          help();
          usage();
          System.exit(STATUS_NORMAL);
          break;
        // -d creole-dir
        case 'd':
          String urlString = g.getOptarg();
          URL u = null;
          try {
            u = new URL(urlString);
          } catch(MalformedURLException e) {
            Err.prln("Bad URL: " + urlString);
            Err.prln(e);
            System.exit(STATUS_ERROR);
          }
          pendingCreoleUrls.add(u);
          Out.prln(
            "CREOLE Directory " + urlString + " queued for registration"
          );
          break;
        // -i gate.xml site-wide init file
        case 'i':
          String optionString = g.getOptarg();
          @SuppressWarnings("unused")
          URL u2 = null;
          File f = new File(optionString);
          try {
            u2 = f.toURI().toURL();
          } catch(MalformedURLException e) {
            Err.prln("Bad initialisation file: " + optionString);
            Err.prln(e);
            System.exit(STATUS_ERROR);
          }
          Gate.setSiteConfigFile(f);
          if(DEBUG)
            Out.prln(
              "Initialisation file " + optionString +
              " recorded for initialisation"
            );
          break;
        case '?':
          // leave the warning to getopt
          System.exit(STATUS_ERROR);
          break;

        default:
          // shouldn't happen!
          Err.prln("getopt() returned " + c + "\n");
          System.exit(STATUS_ERROR);
          break;
      } // getopt switch

  } // processArgs()

  /** Display a usage message */
  public static void usage() {
    Out.prln(
      "Usage: java gate.Main\n" +
      "-h -- show this help file\n" +
      "-d CREOLE-URL -- a creole directory to load\n" +
      "-i SITE_CONFIG_FILE -- the site config file to use"
    );
  } // usage()

  /** Display a help message */
  public static void help() {
    String nl = Strings.getNl();
    Out.prln(
      "For help on command-line options and other information " + nl +
      "see the user manual in your GATE distribution or at " + nl +
      "http://gate.ac.uk/userguide/"
    );
  } // help()

  /** The list of pending URLs to add to the CREOLE register */
  protected static final List<URL> pendingCreoleUrls = new ArrayList<URL>();

} // class Main
