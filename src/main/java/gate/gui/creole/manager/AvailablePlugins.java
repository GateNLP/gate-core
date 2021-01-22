/*
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * Copyright (c) 2009, Ontotext AD.
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * PluginManagerUI.java
 * 
 * Valentin Tablan, 21-Jul-2004
 * 
 * $Id: PluginManagerUI.java 13565 2011-03-26 23:03:34Z johann_p $
 */

package gate.gui.creole.manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import gate.Gate;
import gate.Gate.ResourceInfo;
import gate.creole.CreoleRegisterImpl;
import gate.creole.Plugin;
import gate.gui.MainFrame;
import gate.resources.img.svg.AddIcon;
import gate.resources.img.svg.AvailableIcon;
import gate.resources.img.svg.DeleteIcon;
import gate.resources.img.svg.HelpIcon;
import gate.resources.img.svg.InvalidIcon;
import gate.resources.img.svg.MavenIcon;
import gate.resources.img.svg.OpenFileIcon;
import gate.resources.img.svg.RemoveIcon;
import gate.resources.img.svg.SaveIcon;
import gate.swing.CheckBoxTableCellRenderer;
import gate.swing.IconTableCellRenderer;
import gate.swing.SpringUtilities;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.GateRuntimeException;

@SuppressWarnings("serial")
public class AvailablePlugins extends JPanel {

  private XJTable mainTable;

  /**
   * Contains the URLs from Gate.getKnownPlugins() that satisfy the filter
   * filterTextField for the plugin URL and the plugin resources names
   */
  private List<Plugin> visibleRows = new ArrayList<Plugin>();

  private JSplitPane mainSplit;

  private MainTableModel mainTableModel;

  private ResourcesListModel resourcesListModel;

  private JList<ResourceInfo> resourcesList;
  
  private JLabel lblPluginDetails;
  
  //buttons on the plugin toolbar, should also be ones for homepage/help etc.
  private JButton btnResources, btnResourceHelp;
  private ExtractResourcesActionListener extractResourcesListener = new ExtractResourcesActionListener();
  private ShowResourceHelpActionListener showResourceHelpListener = new ShowResourceHelpActionListener();

  private JTextField filterTextField;

  /**
   * Map from URL to Boolean. Stores temporary values for the loadNow options.
   */
  private Map<Plugin, Boolean> loadNowByURL = new HashMap<Plugin, Boolean>();

  /**
   * Map from URL to Boolean. Stores temporary values for the loadAlways
   * options.
   */
  private Map<Plugin, Boolean> loadAlwaysByURL = new HashMap<Plugin, Boolean>();

  private static final int ICON_COLUMN = 0;

  private static final int NAME_COLUMN = 3;

  private static final int LOAD_NOW_COLUMN = 1;

  private static final int LOAD_ALWAYS_COLUMN = 2;

  public AvailablePlugins() {
    JToolBar tbPluginDirs = new JToolBar(JToolBar.HORIZONTAL);
    tbPluginDirs.setFloatable(false);
    tbPluginDirs.setLayout(new BoxLayout(tbPluginDirs, BoxLayout.X_AXIS));
    tbPluginDirs.add(new JButton(new AddCreoleRepositoryAction()));
    tbPluginDirs.add(new JButton(new DeleteCreoleRepositoryAction()));
    tbPluginDirs.add(Box.createHorizontalStrut(5));
    JLabel titleLabel = new JLabel("CREOLE Plugin");
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
    tbPluginDirs.add(titleLabel);
    tbPluginDirs.add(Box.createHorizontalGlue());
    tbPluginDirs.add(new JLabel("Filter:"));
    filterTextField = new JTextField();
    filterTextField.setToolTipText("Type some text to filter the table rows.");
    tbPluginDirs.add(filterTextField);
    JButton clearFilterButton =
            new JButton(new AbstractAction(null, new DeleteIcon(MainFrame.ICON_DIMENSION)) {
              {
                this.putValue(MNEMONIC_KEY, KeyEvent.VK_BACK_SPACE);
                this.putValue(SHORT_DESCRIPTION, "Clear text field");
              }

              @Override
              public void actionPerformed(ActionEvent e) {
                filterTextField.setText("");
                filterTextField.requestFocusInWindow();
              }
            });

    tbPluginDirs.add(clearFilterButton);

    mainTableModel = new MainTableModel();
    mainTable = new XJTable(mainTableModel);
    mainTable.setTabSkipUneditableCell(true);
    mainTable.setSortedColumn(NAME_COLUMN);

    Collator collator = Collator.getInstance(Locale.ENGLISH);
    collator.setStrength(Collator.TERTIARY);
    mainTable.setComparator(NAME_COLUMN, collator);
    mainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    mainTable.getColumnModel().getColumn(ICON_COLUMN)
            .setCellRenderer(new IconTableCellRenderer());
    CheckBoxTableCellRenderer cbCellRenderer = new CheckBoxTableCellRenderer();
    mainTable.getColumnModel().getColumn(LOAD_ALWAYS_COLUMN)
            .setCellRenderer(cbCellRenderer);
    mainTable.getColumnModel().getColumn(LOAD_NOW_COLUMN)
            .setCellRenderer(cbCellRenderer);

    resourcesListModel = new ResourcesListModel();
    resourcesList = new JList<ResourceInfo>(resourcesListModel);
    resourcesList.setCellRenderer(new ResourcesListCellRenderer());

    // this is needed because otherwise the list gets really narrow most of the
    // time. Strangely if we don't use a custom cell renderer it works fine so
    // that must be where the actual bug is
    ResourceInfo prototype =
            new ResourceInfo("A rather silly long resource name",
                    "java.lang.String", "this is a comment");
    resourcesList.setPrototypeCellValue(prototype);
    resourcesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // enable tooltips
    ToolTipManager.sharedInstance().registerComponent(resourcesList);

    mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
    mainSplit.setResizeWeight(0.80);
    mainSplit.setContinuousLayout(true);
    JScrollPane scroller = new JScrollPane(mainTable);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    mainSplit.setLeftComponent(scroller);

    scroller = new JScrollPane(resourcesList);
    //scroller.setBorder(BorderFactory.createTitledBorder(scroller.getBorder(),
    //        "Resources in Plugin", TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
    
    lblPluginDetails = new JLabel();
    
    JToolBar pluginToolbar = new JToolBar(JToolBar.HORIZONTAL);
    pluginToolbar.setFloatable(false);
    
    btnResources = new JButton(new SaveIcon(32,32));
    btnResources.setDisabledIcon(new SaveIcon(32,32,true));
    btnResources.setToolTipText("Extract Plugin Resources");
    btnResources.setEnabled(false);
    btnResources.addActionListener(extractResourcesListener);

    pluginToolbar.add(btnResources);

    btnResourceHelp = new JButton(new HelpIcon(32,32));
    btnResourceHelp.setDisabledIcon(new HelpIcon(32,32,true));
    btnResourceHelp.setToolTipText("Show Help for Selected Resource");
    btnResourceHelp.setEnabled(false);
    btnResourceHelp.addActionListener(showResourceHelpListener);

    pluginToolbar.add(btnResourceHelp);

    JPanel pluginDisplay = new JPanel(new BorderLayout());
    pluginDisplay.add(lblPluginDetails, BorderLayout.NORTH);
    pluginDisplay.add(scroller,BorderLayout.CENTER);
    pluginDisplay.add(pluginToolbar,BorderLayout.SOUTH);
    
    mainSplit.setRightComponent(pluginDisplay);
    
    setLayout(new BorderLayout());

    add(tbPluginDirs, BorderLayout.NORTH);
    add(mainSplit, BorderLayout.CENTER);

    mainTable.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
              @Override
              public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                  resourcesListModel.dataChanged();
                }
              }
            });

    resourcesList.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
              ResourceInfo selected = resourcesList.getSelectedValue();
              showResourceHelpListener.setResource(selected);

              if (selected == null)
                btnResourceHelp.setEnabled(false);
              else
                btnResourceHelp.setEnabled(resourcesList.getSelectedValue().getHelpURL() != null);
            }
          }
    });

    // when typing a character in the table, use it for filtering
    mainTable.addKeyListener(new KeyAdapter() {

      private Action a = new DeleteCreoleRepositoryAction();

      @Override
      public void keyTyped(KeyEvent e) {
        // if you are doing something other than Shift+ then you probably don't
        // want to use it for filtering
        if(e.getModifiers() > 1) return;

        // if the user presses delete then act as if they pressed the delete
        // button on the toolbar
        if(e.getKeyChar() == KeyEvent.VK_DELETE) {
          a.actionPerformed(null);
          return;
        }

        // these are used for table navigation and not filtering
        if(e.getKeyChar() == KeyEvent.VK_TAB
            || e.getKeyChar() == KeyEvent.VK_SPACE) return;

        // we want to filter so move the character to the filter text field
        filterTextField.requestFocusInWindow();
        filterTextField.setText(String.valueOf(e.getKeyChar()));

      }
    });

    // show only the rows containing the text from filterTextField
    filterTextField.getDocument().addDocumentListener(new DocumentListener() {
      private Timer timer = new Timer("Plugin manager table rows filter", true);

      private TimerTask timerTask;

      @Override
      public void changedUpdate(DocumentEvent e) {
        /* do nothing */
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        update();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        update();
      }

      private void update() {
        if(timerTask != null) {
          timerTask.cancel();
        }
        Date timeToRun = new Date(System.currentTimeMillis() + 300);
        timerTask = new TimerTask() {
          @Override
          public void run() {
            filterRows(filterTextField.getText());
          }
        };
        // add a delay
        timer.schedule(timerTask, timeToRun);
      }
    });

    // Up/Down key events in filterTextField are transferred to the table
    filterTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_PAGE_UP
                || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
          mainTable.dispatchEvent(e);
        }
      }
    });

    // disable Enter key in the table so this key will confirm the dialog
    InputMap inputMap =
            mainTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    inputMap.put(enter, "none");

    reInit();
  }

  protected void reInit() {
    loadNowByURL.clear();
    loadAlwaysByURL.clear();
    visibleRows.clear();
    visibleRows.addAll(Gate.getKnownPlugins());
    if(mainTable.getRowCount() > 0) mainTable.setRowSelectionInterval(0, 0);
    filterRows("");
  }

  private void filterRows(String rowFilter) {
    final String filter = rowFilter.trim().toLowerCase();
    final String previousURL =
            mainTable.getSelectedRow() == -1 ? "" : (String)mainTable
                    .getValueAt(mainTable.getSelectedRow(),
                            mainTable.convertColumnIndexToView(NAME_COLUMN));
    if(filter.length() < 2) {
      // one character or less, don't filter rows
      visibleRows.clear();
      visibleRows.addAll(Gate.getKnownPlugins());
    } else {
      // filter rows case insensitively on each plugin URL and its resources
      visibleRows.clear();
      //for(int i = 0; i < Gate.getKnownPlugins().size(); i++) {
      for (Plugin plugin : Gate.getKnownPlugins()) {
        //Gate.DirectoryInfo dInfo =
         //       Gate.getDirectoryInfo(Gate.getKnownPlugins().get(i));
        String name = plugin.getName();//dInfo.getUrl().toString();
        String resources = "";
        for(int j = 0; j < plugin.getResourceInfoList().size(); j++) {
          resources +=
                  plugin.getResourceInfoList().get(j).getResourceName() + " ";
        }
        if((name != null && name.toLowerCase().contains(filter))
                || resources.toLowerCase().contains(filter)) {
          visibleRows.add(plugin);
        }
      }
    }

    mainTableModel.fireTableDataChanged();

    if(mainTable.getRowCount() > 0) {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          mainTable.setRowSelectionInterval(0, 0);
          if(filter.length() < 2 && previousURL != null
                  && !previousURL.equals("")) {
            // reselect the last selected row based on its name and url values
            for(int row = 0; row < mainTable.getRowCount(); row++) {
              String url =
                      (String)mainTable.getValueAt(row,
                              mainTable.convertColumnIndexToView(NAME_COLUMN));
              if(url.contains(previousURL)) {
                mainTable.setRowSelectionInterval(row, row);
                mainTable.scrollRectToVisible(mainTable.getCellRect(row, 0,
                        true));
                break;
              }
            }
          }
        }
      });
    }
  }

  private Boolean getLoadNow(Plugin plugin) {
    Boolean res = loadNowByURL.get(plugin);
    if(res == null) {
      res = Gate.getCreoleRegister().getPlugins().contains(plugin);
      loadNowByURL.put(plugin, res);
    }
    return res;
  }

  private Boolean getLoadAlways(Plugin plugin) {
    Boolean res = loadAlwaysByURL.get(plugin);
    if(res == null) {
      res = Gate.getAutoloadPlugins().contains(plugin);
      loadAlwaysByURL.put(plugin, res);
    }
    return res;
  }

  private class MainTableModel extends AbstractTableModel {

    private Icon otherIcon, invalidIcon, mavenIcon;

    public MainTableModel() {
      otherIcon = new OpenFileIcon(32, 32);
      invalidIcon = new InvalidIcon(32, 32);
      mavenIcon = new MavenIcon(32, 32);
    }

    @Override
    public int getRowCount() {
      return visibleRows.size();
    }

    @Override
    public int getColumnCount() {
      return 4;
    }

    @Override
    public String getColumnName(int column) {
      switch(column){
        case NAME_COLUMN:
          return "<html><body style='padding: 2px; text-align: center;'>Plugin Name</body></html>";
        case ICON_COLUMN:
          return null;
        case LOAD_NOW_COLUMN:
          return "<html><body style='padding: 2px; text-align: center;'>Load<br>Now</body></html>";
        case LOAD_ALWAYS_COLUMN:
          return "<html><body style='padding: 2px; text-align: center;'>Load<br>Always</body></html>";
        default:
          return "?";
      }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch(columnIndex){
        case NAME_COLUMN:
          return String.class;
        case ICON_COLUMN:
          return Icon.class;
        case LOAD_NOW_COLUMN:
        case LOAD_ALWAYS_COLUMN:
          return Boolean.class;
        default:
          return Object.class;
      }
    }

    @Override
    public Object getValueAt(int row, int column) {
      Plugin dInfo = visibleRows.get(row);
      if(dInfo == null) { return null; }
      switch(column){
        case NAME_COLUMN:
          String name = dInfo.getName();
          String version = dInfo.getVersion();
          return name + (version != null && !version.trim().isEmpty() ? " ("+version.trim()+")" : "");
          //return dInfo.toHTMLString();
        case ICON_COLUMN:
          if(!dInfo.isValid()) return invalidIcon;
          if (dInfo instanceof Plugin.Maven) return mavenIcon;
          //if(dInfo.isRemotePlugin()) return remoteIcon;
          //if(dInfo.isCorePlugin()) return coreIcon;
          //if(dInfo.isUserPlugin()) return userIcon;
          return otherIcon;
        case LOAD_NOW_COLUMN:
          return getLoadNow(dInfo);
        case LOAD_ALWAYS_COLUMN:
          return getLoadAlways(dInfo);
        default:
          return null;
      }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      Plugin plugin = visibleRows.get(row);
      return plugin.isValid()
              && (column == LOAD_NOW_COLUMN || column == LOAD_ALWAYS_COLUMN);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      Boolean valueBoolean = (Boolean)aValue;
      Plugin plugin = visibleRows.get(rowIndex);
            
      if(plugin == null) { return; }

      switch(columnIndex){
        case LOAD_NOW_COLUMN:
          loadNowByURL.put(plugin, valueBoolean);
          handleDependencies(plugin, valueBoolean, loadNowByURL);
          // for some reason the focus is sometime lost after editing
          // however it is needed for Enter key to execute OkAction
          mainTable.requestFocusInWindow();
          break;
        case LOAD_ALWAYS_COLUMN:
          loadAlwaysByURL.put(plugin, valueBoolean);
          handleDependencies(plugin, valueBoolean, loadAlwaysByURL);
          mainTable.requestFocusInWindow();
          break;
      }
    }
    
    private void handleDependencies(Plugin plugin, Boolean load, Map<Plugin,Boolean> allPlugins) {
      if (load) {
        Set<Plugin> dependencies = plugin.getRequiredPlugins();
        for (Plugin dependency : dependencies) {
          if (allPlugins.containsKey(dependency)) {
            allPlugins.put(dependency, true);
            handleDependencies(dependency, load, allPlugins);
          }
        }
      }
      else {
        Set<Plugin> toUnload = new HashSet<Plugin>();
        for (Map.Entry<Plugin, Boolean> entry : allPlugins.entrySet()) {
          if (entry.getKey().getRequiredPlugins().contains(plugin)) {
            entry.setValue(false);
            toUnload.add(entry.getKey());
          }
        }
        
        for (Plugin other : toUnload) {
          handleDependencies(other, false, allPlugins);
        }
      }
    }
  }

  private class ResourcesListModel extends AbstractListModel<ResourceInfo> {

    @Override
    public ResourceInfo getElementAt(int index) {
      int row = mainTable.getSelectedRow();
      if(row == -1) return null;
      row = mainTable.rowViewToModel(row);
      Plugin plugin = visibleRows.get(row);
      return plugin.getResourceInfoList().get(index);
    }

    @Override
    public int getSize() {
      int row = mainTable.getSelectedRow();
      if(row == -1) return 0;
      row = mainTable.rowViewToModel(row);
      Plugin plugin = visibleRows.get(row);
      if(plugin == null) { return 0; }
      return plugin.getResourceInfoList().size();
    }

    public void dataChanged() {
      fireContentsChanged(this, 0, getSize() - 1);
      lblPluginDetails.setText("");
      btnResources.setEnabled(false);
      btnResourceHelp.setEnabled(false);

      resourcesList.clearSelection();

      int row = mainTable.getSelectedRow();
      if(row == -1) return;
      row = mainTable.rowViewToModel(row);
      Plugin plugin = visibleRows.get(row);
      
      StringBuffer details = new StringBuffer("<html><body><h1>").append(plugin.getName()).append("</h1>");
      
      if (plugin.getDescription() != null)
        details.append("<p>").append(plugin.getDescription()).append("</p>");
      //details.append("<p>Has Resources:" ).append(plugin.hasResources()).append("</p>");
      btnResources.setEnabled(plugin.hasResources());
      extractResourcesListener.setPlugin(plugin);
      
      details.append("<p>This plugin contains the following CREOLE resources:</p></body></html>");
      
      lblPluginDetails.setText(details.toString());
    }
  }

  private class ShowResourceHelpActionListener implements ActionListener {
    ResourceInfo resInfo = null;

    public void setResource(ResourceInfo resInfo) {
      this.resInfo = resInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (resInfo == null) return;
      if (resInfo.getHelpURL() == null) return;

      MainFrame.getInstance().showHelpFrame(
        resInfo.getHelpURL(),
        resInfo.getResourceName());
    }
  }

  private class ExtractResourcesActionListener implements ActionListener {

    Plugin plugin = null;
    
    public void setPlugin(Plugin plugin) {
      this.plugin = plugin;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (plugin == null) return;
      if (!plugin.hasResources()) return;
      
      XJFileChooser fileChooser = MainFrame.getFileChooser();
      fileChooser.setDialogTitle("Select Folder to Hold Resources");
      fileChooser.setMultiSelectionEnabled(false);
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());
      fileChooser.setResource("gate.creole.Plugin");
      int result = fileChooser.showOpenDialog(AvailablePlugins.this);
      if(result != JFileChooser.APPROVE_OPTION) return;
      
      try {
        plugin.copyResources(fileChooser.getSelectedFile());
      } catch(IOException | URISyntaxException ex) {
        throw new GateRuntimeException("Unable to extract plugin resources to folder specified", ex);
      }
    }
  }

  private class ResourcesListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
      Gate.ResourceInfo rInfo = (Gate.ResourceInfo)value;
      // prepare the renderer
      String filter = filterTextField.getText().trim().toLowerCase();
      if(filter.length() > 1
              && rInfo.getResourceName().toLowerCase().contains(filter)) {
        isSelected = true; // select resource if matching table row filter
      }
      super.getListCellRendererComponent(list, rInfo.getResourceName(), index,
              isSelected, cellHasFocus);
      // add tooltip text
      setToolTipText(rInfo.getResourceComment());
      return this;
    }
  }

  protected boolean unsavedChanges() {

    Set<Plugin> creoleDirectories = Gate.getCreoleRegister().getPlugins();

    Iterator<Plugin> pluginIter = loadNowByURL.keySet().iterator();
    while(pluginIter.hasNext()) {
      Plugin aPluginURL = pluginIter.next();
      boolean load = loadNowByURL.get(aPluginURL);
      boolean loaded = creoleDirectories.contains(aPluginURL);
      if(load && !loaded) { return true; }
      if(!load && loaded) { return true; }
    }

    pluginIter = loadAlwaysByURL.keySet().iterator();
    while(pluginIter.hasNext()) {
      Plugin aPluginURL = pluginIter.next();
      boolean load = loadAlwaysByURL.get(aPluginURL);
      boolean loaded = Gate.getAutoloadPlugins().contains(aPluginURL);
      if(load && !loaded) { return true; }
      if(!load && loaded) { return true; }
    }

    return false;
  }

  protected Set<Plugin> updateAvailablePlugins(Plugin.DownloadListener progressPanel) {

    Set<Plugin> creoleDirectories = Gate.getCreoleRegister().getPlugins();

    // update the data structures to reflect the user's choices
    Iterator<Plugin> pluginIter = loadNowByURL.keySet().iterator();
    
    Set<Plugin> toLoad = new HashSet<Plugin>();
    while(pluginIter.hasNext()) {
      Plugin aPluginURL = pluginIter.next();
      boolean load = loadNowByURL.get(aPluginURL);
      boolean loaded = creoleDirectories.contains(aPluginURL);
      if(load && !loaded) {
        // remember that we need to load this plugin
        toLoad.add(aPluginURL);
      }
      if(!load && loaded) {
        // remove the directory
        Gate.getCreoleRegister().unregisterPlugin(aPluginURL);
      }
    }

    pluginIter = loadAlwaysByURL.keySet().iterator();
    while(pluginIter.hasNext()) {
      Plugin aPluginURL = pluginIter.next();
      boolean load = loadAlwaysByURL.get(aPluginURL);
      boolean loaded = Gate.getAutoloadPlugins().contains(aPluginURL);
      if(load && !loaded) {
        // set autoload to true
        Gate.addAutoloadPlugin(aPluginURL);
      }
      if(!load && loaded) {
        // set autoload to false
        Gate.removeAutoloadPlugin(aPluginURL);
      }
    }
    
    while(!toLoad.isEmpty()) {
      //lets finally try loading all the plugings
      int numToLoad = toLoad.size();
      List<Throwable> errors = new ArrayList<Throwable>();
      
      pluginIter = toLoad.iterator();
      while(pluginIter.hasNext()) {
        Plugin aPluginURL = pluginIter.next();
        
        // load the directory
        try {
          aPluginURL.addDownloadListener(progressPanel);
          ((CreoleRegisterImpl)Gate.getCreoleRegister()).registerPlugin(aPluginURL);
          pluginIter.remove();
        } catch(Throwable ge) {
          //TODO suppress the errors unless we are going to break out of the loop
          //ge.printStackTrace();
          errors.add(ge);
        }
        finally {
          aPluginURL.removeDownloadListener(progressPanel);
        }
      }
      
      if (numToLoad == toLoad.size()) {
        //we tried loading all the plugins and yet
        //we didn't actually achieve anything
        for (Throwable t : errors) {
          t.printStackTrace();
        }        
        
        break;
      }
    }
    
    loadNowByURL.clear();
    loadAlwaysByURL.clear();
    
    return toLoad;
  }

  private class DeleteCreoleRepositoryAction extends AbstractAction {
    public DeleteCreoleRepositoryAction() {
      super(null, new RemoveIcon(MainFrame.ICON_DIMENSION));
      putValue(SHORT_DESCRIPTION, "Unregister selected CREOLE plugin");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      int[] rows = mainTable.getSelectedRows();

      for(int row : rows) {
        int rowModel = mainTable.rowViewToModel(row);

        Plugin toDelete = visibleRows.get(rowModel);
        
        Gate.removeKnownPlugin(toDelete);
        loadAlwaysByURL.remove(toDelete);
        loadNowByURL.remove(toDelete);
      }

      // redisplay the table with the current filter
      filterRows(filterTextField.getText());
    }
  }

  private class AddCreoleRepositoryAction extends AbstractAction {
    public AddCreoleRepositoryAction() {
      super(null, new AddIcon(MainFrame.ICON_DIMENSION));
      putValue(SHORT_DESCRIPTION, "Register a new CREOLE plugin");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JTextField urlTextField = new JTextField(20);

      class URLfromFileAction extends AbstractAction {
        URLfromFileAction(JTextField textField) {
          super(null, new OpenFileIcon(MainFrame.ICON_DIMENSION));
          putValue(SHORT_DESCRIPTION, "Click to select a directory");
          this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          XJFileChooser fileChooser = MainFrame.getFileChooser();
          fileChooser.setMultiSelectionEnabled(false);
          fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());
          fileChooser.setResource("gate.CreoleRegister");
          int result = fileChooser.showOpenDialog(AvailablePlugins.this);
          if(result == JFileChooser.APPROVE_OPTION) {
            try {
              textField.setText(fileChooser.getSelectedFile().toURI().toURL()
                      .toExternalForm());
            } catch(MalformedURLException mue) {
              throw new GateRuntimeException(mue.toString());
            }
          }
        }

        JTextField textField;
      }

      JButton fileBtn = new JButton(new URLfromFileAction(urlTextField));

      JPanel urlPanel = new JPanel();
      GroupLayout urlPanelLayout = new GroupLayout(urlPanel);
      urlPanel.setLayout(urlPanelLayout);

      urlPanelLayout.setAutoCreateContainerGaps(true);
      urlPanelLayout.setAutoCreateGaps(true);

      JLabel lblURL = new JLabel("Type a URL");
      JLabel lblDir = new JLabel("Select a Directory");
      JLabel lblOR = new JLabel("or");

      urlPanelLayout
              .setHorizontalGroup(urlPanelLayout
                      .createSequentialGroup()
                      .addGroup(
                              urlPanelLayout.createParallelGroup()
                                      .addComponent(lblURL)
                                      .addComponent(urlTextField))
                      .addComponent(lblOR)
                      .addGroup(
                              urlPanelLayout
                                      .createParallelGroup(
                                              GroupLayout.Alignment.CENTER)
                                      .addComponent(lblDir)
                                      .addComponent(fileBtn)));

      urlPanelLayout
              .setVerticalGroup(urlPanelLayout
                      .createSequentialGroup()
                      .addGroup(
                              urlPanelLayout.createParallelGroup()
                                      .addComponent(lblURL)
                                      .addComponent(lblDir))

                      .addGroup(
                              urlPanelLayout
                                      .createParallelGroup(
                                              GroupLayout.Alignment.CENTER)
                                      .addComponent(urlTextField)
                                      .addComponent(lblOR)
                                      .addComponent(fileBtn)));
      
      JPanel mavenPanel = new JPanel(new SpringLayout());
      JTextField txtGroup = new JTextField("uk.ac.gate.plugins",20);
      JTextField txtArtifact = new JTextField(20);
      JTextField txtVersion = new JTextField(gate.Main.version,20);
      
      mavenPanel.add(new JLabel("Group:"));
      mavenPanel.add(txtGroup);
      mavenPanel.add(new JLabel("Artifact:"));
      mavenPanel.add(txtArtifact);
      mavenPanel.add(new JLabel("Version:"));
      mavenPanel.add(txtVersion);
      
      SpringUtilities.makeCompactGrid(mavenPanel,
                               3, 2, //rows, cols
                               5, 5, //initialX, initialY
                               5, 5);//xPad, yPad
      
      
      JTabbedPane tabsPluginType = new JTabbedPane();
      tabsPluginType.add("Maven", mavenPanel);
      tabsPluginType.add("Directory URL", urlPanel);

      if(JOptionPane.showConfirmDialog(AvailablePlugins.this, tabsPluginType,
              "Register a new CREOLE plugin", JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, new AvailableIcon(48, 48)) != JOptionPane.OK_OPTION)
        return;

      try {
        final Plugin plugin;
        if (tabsPluginType.getSelectedIndex() == 0) {
          plugin = new Plugin.Maven(txtGroup.getText().trim(), txtArtifact.getText().trim(), txtVersion.getText().trim());
          //Gate.addKnownPlugin()(plugin);
        }
        else {
          plugin = new Plugin.Directory(new URL(urlTextField.getText()));
          //Gate.addKnownPlugin(new Plugin.Directory(creoleURL));
        }
        Gate.addKnownPlugin(plugin);
        
        mainTable.clearSelection();
        // redisplay the table without filtering
        filterRows("");
        // clear the filter text field
        filterTextField.setText("");
        // select the new plugin row
        SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
            for(int row = 0; row < mainTable.getRowCount(); row++) {
              String url =
                      (String)mainTable.getValueAt(row,
                              mainTable.convertColumnIndexToView(NAME_COLUMN));
              if(url.equals(plugin.getName())) {
                mainTable.setRowSelectionInterval(row, row);
                mainTable.scrollRectToVisible(mainTable.getCellRect(row, 0,
                        true));
                break;
              }
            }
          }
        });
        mainTable.requestFocusInWindow();
      } catch(Exception ex) {

        JOptionPane
                .showMessageDialog(
                        AvailablePlugins.this,
                        "<html><body style='width: 350px;'><b>Unable to register CREOLE plugin!</b><br><br>"
                                + "The details you specified are not valid. Please check the details and try again.</body></html>",
                        "CREOLE Plugin Manager", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
