/*
 * PluginUpdateManager.java
 *
 * Copyright (c) 2011, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Mark A. Greenwood, 29/10/2011
 */

package gate.gui.creole.manager;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Expand;

import gate.Gate;
import gate.creole.Plugin;
import gate.gui.MainFrame;
import gate.resources.img.svg.AddIcon;
import gate.resources.img.svg.AdvancedIcon;
import gate.resources.img.svg.AvailableIcon;
import gate.resources.img.svg.DownloadIcon;
import gate.resources.img.svg.EditIcon;
import gate.resources.img.svg.GATEUpdateSiteIcon;
import gate.resources.img.svg.InvalidIcon;
import gate.resources.img.svg.OpenFileIcon;
import gate.resources.img.svg.RefreshIcon;
import gate.resources.img.svg.RemoveIcon;
import gate.resources.img.svg.UpdateSiteIcon;
import gate.resources.img.svg.UpdatesIcon;
import gate.swing.CheckBoxTableCellRenderer;
import gate.swing.IconTableCellRenderer;
import gate.swing.SpringUtilities;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.Files;
import gate.util.OptionsMap;
import gate.util.Strings;
import gate.util.VersionComparator;

/**
 * The CREOLE plugin manager which includes the ability to download and
 * install/update plugins from remote update sites.
 *
 * @author Mark A. Greenwood
 */
@SuppressWarnings("serial")
public class PluginUpdateManager extends JDialog {

  private PluginTableModel availableModel = new PluginTableModel(3);

  private PluginTableModel updatesModel = new PluginTableModel(4);

  private UpdateSiteModel sitesModel = new UpdateSiteModel();

  private AvailablePlugins installed = new AvailablePlugins();

  private ProgressPanel progressPanel = new ProgressPanel();

  private JPanel panel = new JPanel(new BorderLayout());

  private JTabbedPane tabs = new JTabbedPane();

  private static File userPluginDir;

  private JFrame owner;

  private List<RemoteUpdateSite> updateSites =
      new ArrayList<RemoteUpdateSite>();

  private static final String GATE_USER_PLUGINS = "gate.user.plugins";

  private static final String GATE_UPDATE_SITES = "gate.update.sites";

  private static final String SUPPRESS_UPDATE_INSTALLED =
      "suppress.update.install";

  private static final String[] defaultUpdateSites = new String[]{
      "Additional Plugins from the GATE Team",
      "https://gate.ac.uk/gate/build/deploy/plugins/gate-8.1.xml",
      "Semantic Software Lab",
      "http://creole.semanticsoftware.info/gate-update-site.xml",
      //"City University Centre for Health Informatics",
      //"http://vega.soi.city.ac.uk/~abdy181/software/GATE/gate-update-site.xml",
      "Moonlytics",
      "http://word-correction-gate-plugin.googlecode.com/svn/trunk/site.xml",
      "SAGA",
      "http://demos.gsi.dit.upm.es/SAGA/gate-update-site.xml",
      "Austrian Research Institute for AI (OFAI)",
      "http://www.ofai.at/~johann.petrak/GATE/gate-update-site.xml",
      "BGLangTools",
      "http://www.grigoriliev.com/bglangtools/plugins/gate/gate-update-site.xml"};

public static File getUserPluginsHome() {
    
    if(userPluginDir == null) {
      String upd = System.getProperty(GATE_USER_PLUGINS, Gate.getUserConfig().getString(GATE_USER_PLUGINS));
      if(upd != null) {
        userPluginDir = new File(upd);
        
        if(!userPluginDir.exists() || !userPluginDir.isDirectory() || !userPluginDir.canWrite()) {
          userPluginDir = null;
          Gate.getUserConfig().remove(GATE_USER_PLUGINS);
        }
      }
    }
    
    if (userPluginDir == null) {
      String filePrefix = "";
      if(Gate.runningOnUnix()) filePrefix = ".";

      String userPluginName =
        System.getProperty("user.home") + Strings.getFileSep() + filePrefix
          + "gate-plugins";
      
      userPluginDir = new File(userPluginName);
      userPluginDir.mkdirs();
    }
    
    return userPluginDir;
  }

  /**
   * Responsible for pushing some of the config date for the plugin manager into
   * the main user config. Note that this doesn't actually persist the data,
   * that is only done on a clean exit of the GUI by code hidden somewhere else.
   */
  private void saveConfig() {
    Map<String, String> sites = new HashMap<String, String>();
    for(RemoteUpdateSite rus : updateSites) {
      sites.put(rus.uri.toString(), (rus.enabled ? "1" : "0") + rus.name);
    }
    OptionsMap userConfig = Gate.getUserConfig();
    userConfig.put(GATE_UPDATE_SITES, sites);
    if(userPluginDir != null)
      userConfig.put(GATE_USER_PLUGINS, userPluginDir.getAbsolutePath());
  }

  /**
   * Load all the data about available plugins/updates from the remote update
   * sites as well as checking what is installed in the user plugin directory
   */
  private void loadData() {
    // display the progress panel to stop user input and show progress
    progressPanel.messageChanged("Loading CREOLE Plugin Information...");
    progressPanel.rangeChanged(0, 0);

    if(getUserPluginsHome() == null) {
      // if the user plugin directory is not set then there is no point trying
      // to load any of the data, just disable the update/install tabs
      SwingUtilities.invokeLater(new Runnable() {

        @Override
        public void run() {
          tabs.setEnabledAt(1, false);
          tabs.setEnabledAt(2, false);
          showProgressPanel(false);
        }
        
      });
      
      return;
    }

    // the assumption is that this code is run from the EDT so we need to run
    // the time consuming stuff in a different thread to stop things locking up
    new Thread() {
      @Override
      public void run() {
        // reset the info ready for a reload
        availableModel.data.clear();
        updatesModel.data.clear();

        // go through all the known update sites and get all the plugins they
        // are making available, skipping those sites which are marked as
        // invalid for some reason
        for(RemoteUpdateSite rus : updateSites) {
          if(rus.enabled && (rus.valid == null || rus.valid)) {

            for(CreolePlugin p : rus.getCreolePlugins()) {
              if(p != null) {
                int index = availableModel.data.indexOf(p);
                if(index == -1) {
                  availableModel.data.add(p);
                } else {
                  // if the plugin was already known then replace it if this
                  // instance is a newer version
                  CreolePlugin pp = availableModel.data.get(index);

                  if(VersionComparator.compareVersions(p.version, pp.version) > 0) {
                    availableModel.data.remove(pp);
                    availableModel.data.add(p);
                  }
                }
              }
            }
          }
        }

        // now work through the folders in the user plugin directory to see if
        // there are updates for any of the installed plugins
        if(userPluginDir.exists() && userPluginDir.isDirectory()) {
          File[] plugins = userPluginDir.listFiles();
          for(File f : plugins) {
            if(f.isDirectory()) {
              File pluginInfo = new File(f, "creole.xml");
              if(pluginInfo.exists()) {
                try {
                  CreolePlugin plugin =
                      CreolePlugin.load(pluginInfo.toURI().toURL());
                  if(plugin != null) {
                    int index = availableModel.data.indexOf(plugin);
                    if(index != -1) {
                      CreolePlugin ap = availableModel.data.remove(index);
                      if(VersionComparator.compareVersions(ap.version,
                          plugin.version) > 0) {
                        ap.installed = plugin.version;
                        ap.dir = f;
                        updatesModel.data.add(ap);
                      }
                    }
                  }

                  // add the plugin. most will already be known but this will
                  // catch any that have just been installed
                  Gate.addKnownPlugin(new Plugin.Directory(f.toURI().toURL()));
                } catch(Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }

        SwingUtilities.invokeLater(new Thread() {
          @Override
          public void run() {
            // update all the tables
            installed.reInit();
            updatesModel.dataChanged();
            availableModel.dataChanged();
            sitesModel.dataChanged();

            // enable the update tab if there are any
            //tabs.setEnabledAt(1, updatesModel.data.size() > 0);
            //tabs.setEnabledAt(2, true);

            // remove the progress panel
            showProgressPanel(false);
          }
        });
      }
    }.start();
  }

  private void showProgressPanel(final boolean visible) {
    if(visible == getRootPane().getGlassPane().isVisible()) return;
    if(visible) {
      remove(panel);
      add(progressPanel, BorderLayout.CENTER);
    } else {
      remove(progressPanel);
      add(panel, BorderLayout.CENTER);
    }
    getRootPane().getGlassPane().setVisible(visible);
    validate();
  }

  private void applyChanges() {
    progressPanel.messageChanged("Updating CREOLE Plugin Configuration...");
    progressPanel.rangeChanged(0, updatesModel.data.size()
        + availableModel.data.size());
    showProgressPanel(true);

    // the assumption is that this code is run from the EDT so we need to run
    // the time consuming stuff in a different thread to stop things locking up
    new Thread() {
      @Override
      public void run() {
        if(getUserPluginsHome() != null) {

          // set up ANT ready to do the unzipping
          Expander expander = new Expander();
          expander.setOverwrite(true);
          expander.setDest(getUserPluginsHome());

          // store the list of failed plugins
          List<CreolePlugin> failed = new ArrayList<CreolePlugin>();

          // has the user been warned about installing updates (or have the
          // suppressed the warning)
          boolean hasBeenWarned =
              Gate.getUserConfig().getBoolean(SUPPRESS_UPDATE_INSTALLED);

          // lets start by going through the updates that are available
          Iterator<CreolePlugin> it = updatesModel.data.iterator();
          while(it.hasNext()) {
            CreolePlugin p = it.next();
            if(p.install) {
              // if the user wants the update...
              if(!hasBeenWarned) {
                // warn them about the dangers of updating plugins if we haven't
                // done so yet
                if(JOptionPane
                    .showConfirmDialog(
                        PluginUpdateManager.this,
                        "<html><body style='width: 350px;'><b>UPDATE WARNING!</b><br><br>"
                            + "Updating installed plugins will remove any customizations you may have made. "
                            + "Are you sure you wish to continue?</body></html>",
                        "CREOLE Plugin Manager", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, new DownloadIcon(48, 48)) == JOptionPane.OK_OPTION) {
                  hasBeenWarned = true;
                } else {
                  // if they want to stop then remove the progress panel
                  SwingUtilities.invokeLater(new Thread() {
                    @Override
                    public void run() {
                      showProgressPanel(false);
                    }
                  });
                  return;
                }
              }

              // report on which plugin we are updating
              progressPanel
                  .messageChanged("Updating CREOLE Plugin Configuration...<br>Currently Updating: "
                      + p.getName());
              try {

                // download the new version
                File downloaded = File.createTempFile("gate-plugin", ".zip");
                downloadFile(p.getName(), p.downloadURL, downloaded);

                // try to rename the existing plugin folder
                File renamed =
                    new File(getUserPluginsHome(), "renamed-"
                        + System.currentTimeMillis());

                if(!p.dir.renameTo(renamed)) {
                  // if we can't rename then just remember that we haven't
                  // updated this plugin
                  failed.add(p);
                } else {
                  // if we could rename then trash the old version
                  Files.rmdir(renamed);

                  // unzip the downloaded file
                  expander.setSrc(downloaded);
                  expander.execute();

                  // and delete the download
                  if(!downloaded.delete()) downloaded.deleteOnExit();
                }
              } catch(IOException ex) {
                // something went wrong so log the failed plugin
                ex.printStackTrace();
                failed.add(p);
              }
            }

            // move on to the next plugin
            progressPanel.valueIncrement();
          }

          // now lets work through the available plugins
          it = availableModel.data.iterator();
          while(it.hasNext()) {
            CreolePlugin p = it.next();
            if(p.install) {
              // if plugin is marked for install then...

              // update the progress panel
              progressPanel
                  .messageChanged("Updating CREOLE Plugin Configuration...<br>Currently Installing: "
                      + p.getName());
              try {
                // download the zip file
                File downloaded = File.createTempFile("gate-plugin", ".zip");
                downloadFile(p.getName(), p.downloadURL, downloaded);

                // unpack it into the right place
                expander.setSrc(downloaded);
                expander.execute();

                // delete the download
                if(!downloaded.delete()) downloaded.deleteOnExit();
              } catch(IOException ex) {
                // something went wrong so log the failed plugin
                ex.printStackTrace();
                failed.add(p);
              }

              // move on to the next plugin
              progressPanel.valueIncrement();
            }
          }

          // explain that some plugins failed to install
          if(failed.size() > 0)
            JOptionPane
                .showMessageDialog(
                    PluginUpdateManager.this,
                    "<html><body style='width: 350px;'><b>Installation of "
                        + failed.size()
                        + " plugins failed!</b><br><br>"
                        + "Try unloading all plugins and then restarting GATE before trying to install or update plugins.</body></html>",
                    PluginUpdateManager.this.getTitle(),
                    JOptionPane.ERROR_MESSAGE);
        }

        // (un)load already installed plugins
        progressPanel.messageChanged("Updating CREOLE Plugin Configuration...");
        
        Set<Plugin> failedPlugins = installed.updateAvailablePlugins();
        if (!failedPlugins.isEmpty()) {
          JOptionPane
          .showMessageDialog(
              PluginUpdateManager.this,
              "<html><body style='width: 350px;'><b>Loading of "
                  + failedPlugins.size()
                  + " plugins failed!</b><br><br>"
                  + "See the message pane for more details.</body></html>",
              PluginUpdateManager.this.getTitle(),
              JOptionPane.ERROR_MESSAGE);
        }

        // refresh the tables to reflect what we have just done
        loadData();
      }
    }.start();
  }

  @Override
  public void dispose() {
    MainFrame.getGuiRoots().remove(this);
    super.dispose();
  }

  public PluginUpdateManager(JFrame owner) {
    super(owner, true);
    this.owner = owner;

    // get the list of remote update sites so we can fill in the GUI
    Map<String, String> sites = Gate.getUserConfig().getMap(GATE_UPDATE_SITES);
    for(Map.Entry<String, String> site : sites.entrySet()) {
      try {
        updateSites.add(new RemoteUpdateSite(site.getValue().substring(1),
            new URI(site.getKey()), site.getValue().charAt(0) == '1'));
      } catch(URISyntaxException e) {
        e.printStackTrace();
      }
    }

    if(defaultUpdateSites.length % 2 == 0) {
      // TODO the problem here is that we want to make sure new sites show up in
      // the list, but this means that if a user deletes a site it will respawn
      // next time they start GATE, not sure if there is a better solution.

      for(int i = 0; i < defaultUpdateSites.length; ++i) {
        try {
          RemoteUpdateSite rus =
              new RemoteUpdateSite(defaultUpdateSites[i], new URI(
                  defaultUpdateSites[++i]), false);

          if(!updateSites.contains(rus)) updateSites.add(rus);
        } catch(URISyntaxException e) {
          // this can never happen!
          e.printStackTrace();
        }
      }
    }

    // set up the main window
    setTitle("CREOLE Plugin Manager");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    //setIconImages(Arrays.asList(new Image[]{new GATEIcon(64, 64).getImage(),
    //    new GATEIcon(48, 48).getImage(), new GATEIcon(32, 32).getImage(),
    //    new GATEIcon(22, 22).getImage(), new GATEIcon(16, 16).getImage()}));

    // set up the panel that displays the main GUI elements
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    panel.add(tabs, BorderLayout.CENTER);

    // initialize all the different tabs
    tabs.addTab("Installed Plugins", new AvailableIcon(MainFrame.ICON_DIMENSION), installed);
    /*tabs.addTab("Available Updates", new UpdatesIcon(MainFrame.ICON_DIMENSION), buildUpdates());
    tabs.addTab("Available to Install", new DownloadIcon(MainFrame.ICON_DIMENSION),
        buildAvailable());
    tabs.addTab("Configuration", new AdvancedIcon(MainFrame.ICON_DIMENSION), buildConfig());
    tabs.setDisabledIconAt(
        1,
        new UpdatesIcon(MainFrame.ICON_DIMENSION,true));
    tabs.setDisabledIconAt(
        2,
        new DownloadIcon(MainFrame.ICON_DIMENSION,true));
    tabs.setEnabledAt(1, false);
    tabs.setEnabledAt(2, false);*/

    // setup the row of buttons at the bottom of the screen...
    JPanel pnlButtons = new JPanel();
    pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
    pnlButtons.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

    // ... the apply button
    JButton btnApply = new JButton("Apply All");
    getRootPane().setDefaultButton(btnApply);
    btnApply.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        PluginUpdateManager.this.applyChanges();
      }
    });

    // ... the close button
    Action cancelAction = new AbstractAction("Close") {
      @Override
      public void actionPerformed(ActionEvent e) {

        boolean changes = false;

        for(CreolePlugin p : availableModel.data) {
          changes = changes || p.install;
          if(changes) break;
        }

        if(!changes) {
          for(CreolePlugin p : updatesModel.data) {
            changes = changes || p.install;
            if(changes) break;
          }
        }

        if(!changes) changes = installed.unsavedChanges();

        if(changes
            && JOptionPane
                .showConfirmDialog(
                    PluginUpdateManager.this,
                    "<html><body style='width: 350px;'><b>Changes Have Not Yet Been Applied!</b><br><br>"
                        + "Would you like to apply your changes now?</body></html>",
                    "CREOLE Plugin Manager", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
          applyChanges();
        }

        PluginUpdateManager.this.setVisible(false);
      }
    };
    JButton btnCancel = new JButton(cancelAction);

    // ... and the help button
    Action helpAction = new AbstractAction("Help") {
      @Override
      public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().showHelpFrame("sec:howto:plugins",
            "gate.gui.creole.PluginUpdateManager");
      }
    };
    JButton btnHelp = new JButton(helpAction);

    // add the buttons to the panel
    pnlButtons.add(btnHelp);
    pnlButtons.add(Box.createHorizontalGlue());
    pnlButtons.add(btnApply);
    pnlButtons.add(Box.createHorizontalStrut(5));
    pnlButtons.add(btnCancel);

    // and the panel to the main GUI
    panel.add(pnlButtons, BorderLayout.SOUTH);

    // make the main GUI the currently visisble dialog item
    add(panel, BorderLayout.CENTER);

    // define keystrokes action bindings at the level of the main window
    getRootPane().registerKeyboardAction(cancelAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
    getRootPane().registerKeyboardAction(helpAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
        JComponent.WHEN_IN_FOCUSED_WINDOW);

    // make sure the dialog is a reasonable size
    pack();
    Dimension screenSize = getGraphicsConfiguration().getBounds().getSize();
    Dimension dialogSize = getPreferredSize();
    int width =
        dialogSize.width > screenSize.width
            ? screenSize.width * 3 / 4
            : dialogSize.width;
    int height =
        dialogSize.height > screenSize.height
            ? screenSize.height * 2 / 3
            : dialogSize.height;
    setSize(width, height);
    validate();

    // place the dialog somewhere sensible
    setLocationRelativeTo(owner);
  }

  private Component buildUpdates() {
    XJTable tblUpdates = new XJTable(updatesModel);
    tblUpdates.getColumnModel().getColumn(0)
        .setCellRenderer(new CheckBoxTableCellRenderer());
    tblUpdates.setSortable(false);
    tblUpdates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblUpdates.getColumnModel().getColumn(0).setMaxWidth(100);
    tblUpdates.getColumnModel().getColumn(2).setMaxWidth(100);
    tblUpdates.getColumnModel().getColumn(3).setMaxWidth(100);

    tblUpdates.getColumnModel().getColumn(1)
        .setCellEditor(new JTextPaneTableCellRenderer());

    tblUpdates.setSortable(true);
    tblUpdates.setSortedColumn(1);
    Collator collator = Collator.getInstance(Locale.ENGLISH);
    collator.setStrength(Collator.TERTIARY);
    tblUpdates.setComparator(1, collator);

    JScrollPane scroller = new JScrollPane(tblUpdates);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    return scroller;
  }

  private Component buildAvailable() {
    final XJTable tblAvailable = new XJTable();
    tblAvailable.setModel(availableModel);

    tblAvailable.getColumnModel().getColumn(0)
        .setCellRenderer(new CheckBoxTableCellRenderer());
    tblAvailable.setSortable(false);
    tblAvailable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblAvailable.getColumnModel().getColumn(0).setMaxWidth(100);
    tblAvailable.getColumnModel().getColumn(2).setMaxWidth(100);

    tblAvailable.getColumnModel().getColumn(1)
        .setCellEditor(new JTextPaneTableCellRenderer());

    tblAvailable.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        process(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        process(e);
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        process(e);
      }

      private void process(final MouseEvent e) {
        //final int row = tblAvailable.rowAtPoint(e.getPoint());
        final int column = tblAvailable.columnAtPoint(e.getPoint());
        if (column == 1) {
          SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
              try {
                Robot robot = new Robot();
                if (e.getID() == MouseEvent.MOUSE_PRESSED || e.getID() == MouseEvent.MOUSE_CLICKED) robot.mousePress(InputEvent.BUTTON1_MASK);
                if (e.getID() == MouseEvent.MOUSE_RELEASED || e.getID() == MouseEvent.MOUSE_CLICKED) robot.mouseRelease(InputEvent.BUTTON1_MASK);
              } catch(AWTException e) {
                e.printStackTrace();
              }
            }
          });
        }
      }
    });

    tblAvailable.setSortable(true);
    tblAvailable.setSortedColumn(1);
    Collator collator = Collator.getInstance(Locale.ENGLISH);
    collator.setStrength(Collator.TERTIARY);
    tblAvailable.setComparator(1, collator);

    JScrollPane scrollerAvailable = new JScrollPane(tblAvailable);
    scrollerAvailable
        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    return scrollerAvailable;
  }

  private Component buildConfig() {

    // the main update site area
    JPanel pnlUpdateSites = new JPanel(new BorderLayout());
    pnlUpdateSites.setBorder(BorderFactory
        .createTitledBorder("Plugin Repositories:"));
    final XJTable tblSites = new XJTable(sitesModel);
    tblSites.getColumnModel().getColumn(0)
        .setCellRenderer(new IconTableCellRenderer());
    tblSites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane scroller = new JScrollPane(tblSites);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    pnlUpdateSites.add(scroller, BorderLayout.CENTER);
    final JPanel pnlEdit = new JPanel(new SpringLayout());
    final JTextField txtName = new JTextField(20);
    final JTextField txtURL = new JTextField(20);
    pnlEdit.add(new JLabel("Name: "));
    pnlEdit.add(txtName);
    pnlEdit.add(new JLabel("URL: "));
    pnlEdit.add(txtURL);
    SpringUtilities.makeCompactGrid(pnlEdit, 2, 2, 6, 6, 6, 6);
    JButton btnAdd = new JButton(new AddIcon(MainFrame.ICON_DIMENSION));
    btnAdd.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        txtName.setText("");
        txtURL.setText("");

        final JOptionPane options =
            new JOptionPane(pnlEdit, JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION, new UpdateSiteIcon(48, 48));
        final JDialog dialog =
            new JDialog(PluginUpdateManager.this, "Plugin Repository Info",
                true);
        options.addPropertyChangeListener(new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent e) {
            if (options.getValue().equals(JOptionPane.UNINITIALIZED_VALUE)) return;
            String prop = e.getPropertyName();
            if(dialog.isVisible() && (e.getSource() == options)
                && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
              if(((Integer)options.getValue()).intValue() == JOptionPane.OK_OPTION) {
                if(txtName.getText().trim().equals("")) {
                  txtName.requestFocusInWindow();
                  options.setValue(JOptionPane.UNINITIALIZED_VALUE);
                  return;
                }
                if(txtURL.getText().trim().equals("")) {
                  txtURL.requestFocusInWindow();
                  options.setValue(JOptionPane.UNINITIALIZED_VALUE);
                  return;
                }
              }
              dialog.setVisible(false);
            }
          }
        });

        dialog.setContentPane(options);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(PluginUpdateManager.this);
        dialog.setVisible(true);

        if(((Integer)options.getValue()).intValue() != JOptionPane.OK_OPTION)
          return;
        if(txtName.getText().trim().equals("")) return;
        if(txtURL.getText().trim().equals("")) return;

        dialog.dispose();

        try {
          updateSites.add(new RemoteUpdateSite(txtName.getText().trim(),
              new URI(txtURL.getText().trim()), true));
          showProgressPanel(true);
          saveConfig();
          loadData();
        } catch(Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    final JButton btnRemove = new JButton(new RemoveIcon(MainFrame.ICON_DIMENSION));
    btnRemove.setEnabled(false);
    btnRemove.setDisabledIcon(new RemoveIcon(MainFrame.ICON_DIMENSION, true));
    btnRemove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showProgressPanel(true);
        int row = tblSites.getSelectedRow();
        if(row == -1) return;
        row = tblSites.rowViewToModel(row);
        updateSites.remove(row);
        saveConfig();
        loadData();
      }
    });

    final JButton btnEdit = new JButton(new EditIcon(MainFrame.ICON_DIMENSION));
    btnEdit.setDisabledIcon(new EditIcon(MainFrame.ICON_DIMENSION,true));
    btnEdit.setEnabled(false);
    btnEdit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = tblSites.getSelectedRow();
        if(row == -1) return;
        row = tblSites.rowViewToModel(row);
        RemoteUpdateSite site = updateSites.get(row);
        txtName.setText(site.name);
        txtURL.setText(site.uri.toString());
        if(JOptionPane.showConfirmDialog(PluginUpdateManager.this, pnlEdit,
            "Update Site Info", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, new UpdateSiteIcon(48, 48)) != JOptionPane.OK_OPTION)
          return;
        if(txtName.getText().trim().equals("")) return;
        if(txtURL.getText().trim().equals("")) return;
        try {
          URI url = new URI(txtURL.getText().trim());
          if(!url.equals(site.uri)) {
            site.uri = url;
            site.plugins = null;
          }
          site.name = txtName.getText().trim();
          site.valid = null;
          showProgressPanel(true);
          saveConfig();
          loadData();

        } catch(Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    final JButton btnRefresh = new JButton(new RefreshIcon(MainFrame.ICON_DIMENSION));
    btnRefresh.setDisabledIcon(new RefreshIcon(MainFrame.ICON_DIMENSION,true));
    btnRefresh.setEnabled(false);
    btnRefresh.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        int row = tblSites.getSelectedRow();
        if(row == -1) return;
        row = tblSites.rowViewToModel(row);
        RemoteUpdateSite site = updateSites.get(row);
        site.plugins = null;
        showProgressPanel(true);
        saveConfig();
        loadData();
      }
    });

    tblSites.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting()) return;

            boolean enable = (tblSites.getSelectedRow() != -1);
            btnRemove.setEnabled(enable);
            btnEdit.setEnabled(enable);
            btnRefresh.setEnabled(enable);
          }
        });

    JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
    toolbar.setFloatable(false);
    toolbar.add(btnAdd);
    toolbar.add(btnRemove);
    toolbar.add(btnRefresh);
    toolbar.add(btnEdit);
    pnlUpdateSites.add(toolbar, BorderLayout.EAST);

    // the user plugin dir area
    JToolBar pnlUserPlugins = new JToolBar(JToolBar.HORIZONTAL);
    pnlUserPlugins.setOpaque(false);
    pnlUserPlugins.setFloatable(false);
    pnlUserPlugins.setLayout(new BoxLayout(pnlUserPlugins, BoxLayout.X_AXIS));
    pnlUserPlugins.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

    //String userPluginsDir = (String)Gate.getUserConfig().get(GATE_USER_PLUGINS);
    getUserPluginsHome();
    final JTextField txtUserPlugins =
        new JTextField(userPluginDir == null ? "" : userPluginDir.getAbsolutePath());
    txtUserPlugins.setEditable(false);

    JButton btnUserPlugins = new JButton(new OpenFileIcon(MainFrame.ICON_DIMENSION));
    btnUserPlugins.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        XJFileChooser fileChooser = MainFrame.getFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setResource(GATE_USER_PLUGINS);

        if(fileChooser.showOpenDialog(PluginUpdateManager.this) == JFileChooser.APPROVE_OPTION) {
          userPluginDir = fileChooser.getSelectedFile();

          if(!userPluginDir.exists()) {
            JOptionPane
                .showMessageDialog(
                    owner,
                    "<html><body style='width: 350px;'><b>Selected Folder Doesn't Exist!</b><br><br>"
                        + "In order to install new CREOLE plugins you must choose a user plugins folder, "
                        + "which exists and is writable.",
                    "CREOLE Plugin Manager", JOptionPane.ERROR_MESSAGE);
            return;
          }

          if(!userPluginDir.isDirectory()) {
            JOptionPane
                .showMessageDialog(
                    owner,
                    "<html><body style='width: 350px;'><b>You Selected A File Instead Of A Folder!</b><br><br>"
                        + "In order to install new CREOLE plugins you must choose a user plugins folder, "
                        + "which exists and is writable.",
                    "CREOLE Plugin Manager", JOptionPane.ERROR_MESSAGE);
            return;
          }

          if(!userPluginDir.canWrite()) {
            JOptionPane
                .showMessageDialog(
                    owner,
                    "<html><body style='width: 350px;'><b>Selected Folder Is Read Only!</b><br><br>"
                        + "In order to install new CREOLE plugins you must choose a user plugins folder, "
                        + "which exists and is writable.",
                    "CREOLE Plugin Manager", JOptionPane.ERROR_MESSAGE);
            return;
          }

          txtUserPlugins.setText(userPluginDir.getAbsolutePath());
          saveConfig();
          loadData();
        }
      }
    });
    pnlUserPlugins.setBorder(BorderFactory
        .createTitledBorder("User Plugin Directory: "));
    pnlUserPlugins.add(txtUserPlugins);
    pnlUserPlugins.add(btnUserPlugins);

    // the suppress warnings area
    JPanel pnlSuppress = new JPanel();
    pnlSuppress.setLayout(new BoxLayout(pnlSuppress, BoxLayout.X_AXIS));
    pnlSuppress.setBorder(BorderFactory
        .createTitledBorder("Suppress Warning Messages:"));
    //final JCheckBox chkUserPlugins =
    //    new JCheckBox("User Plugin Directory Not Set", Gate.getUserConfig()
    //        .getBoolean(SUPPRESS_USER_PLUGINS));
    //pnlSuppress.add(chkUserPlugins);
    //pnlSuppress.add(Box.createHorizontalStrut(10));
    final JCheckBox chkUpdateInsatlled =
        new JCheckBox("Update Of Installed Plugin", Gate.getUserConfig()
            .getBoolean(SUPPRESS_UPDATE_INSTALLED));
    pnlSuppress.add(chkUpdateInsatlled);
    ActionListener chkListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        //Gate.getUserConfig().put(SUPPRESS_USER_PLUGINS,
        //    chkUserPlugins.isSelected());
        Gate.getUserConfig().put(SUPPRESS_UPDATE_INSTALLED,
            chkUpdateInsatlled.isSelected());
      }
    };
    chkUpdateInsatlled.addActionListener(chkListener);
    //chkUserPlugins.addActionListener(chkListener);

    // assemble the full panel and return it
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(pnlUpdateSites, BorderLayout.CENTER);
    panel.add(pnlUserPlugins, BorderLayout.NORTH);
    panel.add(pnlSuppress, BorderLayout.SOUTH);
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    return panel;
  }

  /**
   * Download a file from a URL into a local file while updating the progress
   * panel so the user knows how far through we are
   *
   * @param name
   *          the name of the plugin for the progress feedback
   * @param url
   *          the URL to download from
   * @param file
   *          the file to save into
   * @throws IOException
   *           if any IO related problems occur
   */
  private void downloadFile(String name, URL url, File file) throws IOException {
    InputStream in = null;
    FileOutputStream out = null;

    try {
      // get a connection to the URL
      URLConnection conn = url.openConnection();
      conn.setConnectTimeout(10000);
      conn.setReadTimeout(10000);

      // use this to configure the progress info
      int expectedSize = conn.getContentLength();
      progressPanel.downloadStarting(name, expectedSize == -1);
      int downloaded = 0;

      // create a 1KB buffer to speed up downloaded
      byte[] buf = new byte[1024];

      // records how much of the buffer was filled
      int length;

      // open the input and output streams
      in = conn.getInputStream();
      out = new FileOutputStream(file);

      // keep filling the buffer and then writing it out to the file until there
      // is no more data, all the time keep updating the progress bar
      while((in != null) && ((length = in.read(buf)) != -1)) {
        downloaded += length;
        out.write(buf, 0, length);
        if(expectedSize != -1)
          progressPanel.downloadProgress((downloaded * 100) / expectedSize);
      }

      // flush the output file to ensure everything is written to disk
      out.flush();
    } finally {
      // once we have finished close all the streams and report that we are done
      progressPanel.downloadFinished();
      if(out != null) out.close();
      if(in != null) in.close();
    }
  }

  @Override
  public void setVisible(boolean visible) {
    if(visible) {
      MainFrame.getGuiRoots().add(this);
      // if the window is about to be made visible then do some quick setup
      tabs.setSelectedIndex(0);
      installed.reInit();
      loadData();

      // warn the user if their plugni dir isn't set
      /*if(userPluginDir == null
          && !Gate.getUserConfig().getBoolean(SUPPRESS_USER_PLUGINS)) {
        JOptionPane
            .showMessageDialog(
                owner,
                "<html><body style='width: 350px;'><b>The user plugin folder has not yet been configured!</b><br><br>"
                    + "In order to install new CREOLE plugins you must choose a user plugins folder. "
                    + "This can be achieved from the Configuration tab of the CREOLE Plugin Manager.",
                "CREOLE Plugin Manager", JOptionPane.INFORMATION_MESSAGE,
                new UserPluginIcon(48, 48));
      }*/
    }

    // now actually show/hide the window
    super.setVisible(visible);

    dispose();
  }

  private static class PluginTableModel extends AbstractTableModel {
    private int columns;

    private List<CreolePlugin> data = new ArrayList<CreolePlugin>();

    public PluginTableModel(int columns) {
      this.columns = columns;
    }

    @Override
    public int getColumnCount() {
      return columns;
    }

    @Override
    public int getRowCount() {
      return data.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
      CreolePlugin plugin = data.get(row);
      switch(column){
        case 0:
          return plugin.install;
        case 1:
          return "<html><body>"
              + (plugin.getHelpURL() != null
                  ? "<a href=\"" + plugin.getHelpURL() + "\">"
                      + plugin.getName() + "</a>"
                  : plugin.getName())
              + plugin.compatabilityInfo()
              + (plugin.description != null
                  ? "<br><span style='font-size: 80%;'>" + plugin.description
                      + "</span>"
                  : "") + "</body></html>";
        case 2:
          return plugin.version;
        case 3:
          return plugin.installed;
        default:
          return null;
      }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      if(column > 1) return false;
      return data.get(row).compatible;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
      if(column != 0) return;
      CreolePlugin plugin = data.get(row);
      plugin.install = (Boolean)value;
    }

    @Override
    public String getColumnName(int column) {
      switch(column){
        case 0:
          return "<html><body style='padding: 2px; text-align: center;'>Install</body></html>";
        case 1:
          return "<html><body style='padding: 2px; text-align: center;'>Plugin Name</body></html>";
        case 2:
          // TODO it would be nice to use "Version<br>Available" but for some
          // reason the header isn't expanding
          return "<html><body style='padding: 2px; text-align: center;'>Available</body></html>";
        case 3:
          return "<html><body style='padding: 2px; text-align: center;'>Installed</body></html>";
        default:
          return null;
      }
    }

    @Override
    public Class<?> getColumnClass(int column) {
      switch(column){
        case 0:
          return Boolean.class;
        case 1:
          return String.class;
        case 2:
          return String.class;
        case 3:
          return String.class;
        default:
          return null;
      }
    }

    public void dataChanged() {
      fireTableDataChanged();
    }
  }

  private static class Expander extends Expand {
    public Expander() {
      setProject(new Project());
      getProject().init();
      setTaskType("unzip");
      setTaskName("unzip");
      setOwningTarget(new Target());
    }
  }

  private class UpdateSiteModel extends AbstractTableModel {
    private transient Icon icoSite = new UpdateSiteIcon(32, 32);

    private transient Icon icoInvalid = new InvalidIcon(32, 32);

    private transient Icon icoGATE = new GATEUpdateSiteIcon(32, 32);

    @Override
    public String getColumnName(int column) {
      switch(column){
        case 0:
          return "";
        case 1:
          return "<html><body style='padding: 2px; text-align: center;'>Enabled</body></html>";
        case 2:
          return "<html><body style='padding: 2px; text-align: center;'>Repository Info</body></html>";
        default:
          return null;
      }
    }

    @Override
    public Class<?> getColumnClass(int column) {
      switch(column){
        case 0:
          return Icon.class;
        case 1:
          return Boolean.class;
        case 2:
          return String.class;
        default:
          return null;
      }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return column == 1;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
      RemoteUpdateSite site = updateSites.get(row);
      site.enabled = (Boolean)value;
      saveConfig();

      if(site.enabled) {
        // TODO can we do this without a complete reload?
        showProgressPanel(true);
        loadData();
      } else {
        availableModel.data.removeAll(site.getCreolePlugins());
        updatesModel.data.removeAll(site.getCreolePlugins());
        availableModel.dataChanged();
        updatesModel.dataChanged();
        tabs.setEnabledAt(1, updatesModel.getRowCount() > 0);
      }
    }

    public void dataChanged() {
      fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
      return 3;
    }

    @Override
    public int getRowCount() {
      return updateSites.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
      RemoteUpdateSite site = updateSites.get(row);
      switch(column){
        case 0:
          if(site.valid != null && !site.valid) return icoInvalid;
          if(site.uri.toString().startsWith("https://gate.ac.uk"))
            return icoGATE;
          return icoSite;
        case 1:
          return site.enabled;
        case 2:
          return "<html><body>" + site.name
              + "<br><span style='font-size: 80%;'>" + site.uri
              + "</span></body></html>";
        default:
          return null;
      }
    }
  }
}