/*
 *  Copyright (c) 1995-2013, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 23/01/2001
 *
 *  $Id: XgappUpgradeSelector.java 1 2018-05-04 14:36:21Z ian_roberts $
 *
 */
package gate.gui.persistence;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gate.gui.AlternatingTableCellEditor;
import gate.gui.MainFrame;
import gate.persist.PersistenceException;
import gate.resources.img.svg.GreenBallIcon;
import gate.resources.img.svg.InvalidIcon;
import gate.resources.img.svg.OpenFileIcon;
import gate.resources.img.svg.RedBallIcon;
import gate.resources.img.svg.SaveIcon;
import gate.resources.img.svg.YellowBallIcon;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.ExtensionFileFilter;
import gate.util.persistence.PersistenceManager;
import gate.util.persistence.UpgradeXGAPP;

public class XgappUpgradeSelector implements ActionListener {

  private static final Logger log = LoggerFactory.getLogger(XgappUpgradeSelector.class);

  private List<UpgradeXGAPP.UpgradePath> upgrades;

  private URI gappUri;

  private EnumMap<UpgradeXGAPP.UpgradePath.UpgradeStrategy, Icon> strategyIcons;

  private Icon disabledStrategyIcon;

  private JLabel statusLabel;

  private JPanel mainPanel;

  private Icon warningIcon = new InvalidIcon(16, 16);

  private UpgradePathTableModel model;


  public XgappUpgradeSelector(URI gappUri, List<UpgradeXGAPP.UpgradePath> upgrades) {
    this.gappUri = gappUri;
    this.upgrades = upgrades;
    model = new UpgradePathTableModel(upgrades);

    strategyIcons = new EnumMap<UpgradeXGAPP.UpgradePath.UpgradeStrategy, Icon>(UpgradeXGAPP.UpgradePath.UpgradeStrategy.class);
    strategyIcons.put(UpgradeXGAPP.UpgradePath.UpgradeStrategy.UPGRADE, new GreenBallIcon(16, 16));
    strategyIcons.put(UpgradeXGAPP.UpgradePath.UpgradeStrategy.PLUGIN_ONLY, new YellowBallIcon(16, 16));
    strategyIcons.put(UpgradeXGAPP.UpgradePath.UpgradeStrategy.SKIP, new RedBallIcon(16, 16));
    disabledStrategyIcon = new RedBallIcon(16, 16, true);

    XJTable pluginTable = new XJTable(model);
    pluginTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    JScrollPane scroller = new JScrollPane(pluginTable);
    statusLabel = new JLabel("Select plugin versions to upgrade to");
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(statusLabel, BorderLayout.CENTER);

    Box buttonBox = Box.createHorizontalBox();
    JButton loadButton = new JButton();
    loadButton.setIcon(new OpenFileIcon(24, 24));
    loadButton.setToolTipText("Load a saved upgrade script");
    loadButton.setActionCommand("LOADSCRIPT");
    loadButton.addActionListener(this);
    buttonBox.add(loadButton);
    buttonBox.add(Box.createHorizontalStrut(5));

    JButton saveButton = new JButton();
    saveButton.setIcon(new SaveIcon(24, 24));
    saveButton.setToolTipText("Save selected upgrades as a script");
    saveButton.setActionCommand("SAVESCRIPT");
    saveButton.addActionListener(this);
    buttonBox.add(saveButton);
    topPanel.add(buttonBox, BorderLayout.SOUTH);

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(scroller, BorderLayout.CENTER);

    DefaultTableCellRenderer newPluginRenderer = new DefaultTableCellRenderer();
    newPluginRenderer.setToolTipText("Double-click or press the space key to change");
    pluginTable.getColumnModel().getColumn(1).setCellRenderer(newPluginRenderer);
    pluginTable.getColumnModel().getColumn(2).setCellRenderer(new UpgradeStrategyRenderer());
    pluginTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setEnabled(((UpgradeXGAPP.UpgradePath.UpgradeStrategy)table.getValueAt(row, 2)).upgradePlugin);
        return label;
      }
    });
    pluginTable.getColumnModel().getColumn(1).setCellEditor(new PluginCoordinatesEditor());
    // Alternate between two different cell editor components to avoid combo box rendering weirdness
    pluginTable.getColumnModel().getColumn(2).setCellEditor(
            new AlternatingTableCellEditor(new UpgradeStrategyEditor(), new UpgradeStrategyEditor()));
    pluginTable.getColumnModel().getColumn(3).setCellEditor(
            new AlternatingTableCellEditor(new UpgradeVersionEditor(), new UpgradeVersionEditor()));

    // initial sort
    model.fireTableDataChanged();
  }

  public boolean showDialog(Window parent) {
    JOptionPane optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    JDialog dialog = optionPane.createDialog(parent, "Select plugin versions");
    dialog.setResizable(true);
    dialog.setVisible(true);
    return (((Integer)optionPane.getValue()).intValue() == JOptionPane.OK_OPTION);
  }

  public void actionPerformed(ActionEvent event) {
    XJFileChooser fileChooser = MainFrame.getFileChooser();
    fileChooser.setResource("xgappUpgradeScript");
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    ExtensionFileFilter filter = new ExtensionFileFilter("Tab-separated values", ".tsv");
    fileChooser.resetChoosableFileFilters();
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.setFileFilter(filter);
    if("SAVESCRIPT".equals(event.getActionCommand())) {
      fileChooser.setDialogTitle("Save upgrade script");
      int result = fileChooser.showSaveDialog(mainPanel);
      if(result == JFileChooser.APPROVE_OPTION) {
        try {
          UpgradeXGAPP.saveUpgradePaths(model.upgrades, gappUri, fileChooser.getSelectedFile());
        } catch(IOException e) {
          log.error("Error writing upgrade script", e);
          statusLabel.setIcon(warningIcon);
          statusLabel.setText("Couldn't save upgrade script, see messages pane for details");
        }
      }
    } else if("LOADSCRIPT".equals(event.getActionCommand())) {
      fileChooser.setDialogTitle("Select upgrade script to load");
      int result = fileChooser.showOpenDialog(mainPanel);
      if(result == JFileChooser.APPROVE_OPTION) {
        try {
          UpgradeXGAPP.loadUpgradePaths(model.upgrades, gappUri, fileChooser.getSelectedFile());
          model.fireTableDataChanged();
        } catch(IOException e) {
          log.error("Error reading upgrade script", e);
          statusLabel.setIcon(warningIcon);
          statusLabel.setText("Couldn't load upgrade script, see messages pane for details");
        }
      }
    }
  }

  protected class UpgradePathTableModel extends AbstractTableModel {
    private List<UpgradeXGAPP.UpgradePath> upgrades;

    private int[] namedepths;

    UpgradePathTableModel(List<UpgradeXGAPP.UpgradePath> upgrades) {
      this.upgrades = upgrades;
      this.namedepths = new int[upgrades.size()];
      Arrays.fill(this.namedepths, 0);
      refreshDepths();
    }

    private void refreshDepths() {
      boolean finished = false;
      while(!finished) {
        finished = true;
        Map<String, List<Integer>> grouped = IntStream.range(0, namedepths.length).boxed()
                .collect(Collectors.groupingBy((i) -> (String)getValueAt(i, 0)));
        for(Map.Entry<String, List<Integer>> e : grouped.entrySet()) {
          if(e.getValue().size() > 1) {
            // two or more rows have the same name in the first column - increment all their
            // namedepth values unless they're already as detailed as can be
            for(Integer i : e.getValue()) {
              if(namedepths[i] < 2) {
                namedepths[i]++;
                finished = false;
              }
            }
          }
        }
        // by the time we get to here either (a) all the rows have distinct names or
        // (b) those that don't are already showing as much detail as we know and we
        // simply can't disambiguate them fully.
      }
    }

    @Override
    public int getRowCount() {
      return upgrades.size();
    }

    @Override
    public int getColumnCount() {
      return 4;
    }

    @Override
    public String getColumnName(int column) {
      switch(column) {
        case 0:
          return "Old plugin";
        case 1:
          return "New plugin";
        case 2:
          return "Upgrade?";
        case 3:
          return "Target version";
        default:
          return "UNKNOWN";
      }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch(columnIndex) {
        case 0:
          return String.class;
        case 1:
          return PluginCoordinates.class;
        case 2:
          return UpgradeXGAPP.UpgradePath.UpgradeStrategy.class;
        case 3:
          return Version.class;
        default:
          return null;
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      UpgradeXGAPP.UpgradePath path = upgrades.get(rowIndex);
      return (columnIndex == 1) ||
              (columnIndex == 2 && path.getGroupID() != null) ||
              (columnIndex == 3 && path.getUpgradeStrategy().upgradePlugin);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      UpgradeXGAPP.UpgradePath path = upgrades.get(rowIndex);
      if(columnIndex == 1) {
        PluginCoordinates coords = (PluginCoordinates)aValue;
        VersionRangeResult vrr = UpgradeXGAPP.getPluginVersions(coords.groupId, coords.artifactId);
        List<Version> versions = (vrr == null ? null : vrr.getVersions());
        if(versions != null && !versions.isEmpty()) {
          path.setGroupID(coords.groupId);
          path.setArtifactID(coords.artifactId);
          path.setVersionRangeResult(vrr);
          path.setUpgradeStrategy(UpgradeXGAPP.UpgradePath.UpgradeStrategy.UPGRADE);
          fireTableCellUpdated(rowIndex, 2);
          if(!versions.contains(path.getSelectedVersion())) {
            path.setSelectedVersion(UpgradeXGAPP.getDefaultSelection(vrr));
            fireTableCellUpdated(rowIndex, 3);
          }
        } else {
          statusLabel.setIcon(warningIcon);
          statusLabel.setText(coords + " is not a valid GATE plugin");
        }
      } else if(columnIndex == 2) {
        path.setUpgradeStrategy((UpgradeXGAPP.UpgradePath.UpgradeStrategy) aValue);
        // may need to re-render the version column
        fireTableCellUpdated(rowIndex, 3);
      } else if(columnIndex == 3) {
        path.setSelectedVersion((Version) aValue);
      }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      UpgradeXGAPP.UpgradePath path = upgrades.get(rowIndex);
      if(columnIndex == 0) {
        if(path.getOldPath().startsWith("$gate")) {
          // pre-8.5 GATE_HOME/plugins/Something
          int startOffset = 18; // for $gatehome$plugins/Foo/
          if(path.getOldPath().startsWith("$gateplugins$")) {
            startOffset = 13;
          }
          return "Pre-8.5 " + path.getOldPath().substring(startOffset, path.getOldPath().length() - 1) + " (built-in)";
        } else if(path.getOldPath().startsWith("creole:")) {
          // an existing Maven plugin
          String[] gav = path.getOldPath().substring(9).split(";", 3);
          if(namedepths[rowIndex] == 0) {
            return gav[1]; // just artifact ID
          } else if(namedepths[rowIndex] == 1) {
            return gav[1] + ":" + gav[2]; // artifact:version
          } else {
            return gav[0] + ":" + gav[1] + ":" + gav[2]; // group:artifact:version
          }
        } else {
          // a path to something that isn't gatehome
          try {
            URI oldURI = PersistenceManager.URLHolder.unpackPersistentRepresentation(gappUri, path.getOldPath());
            String[] parts = oldURI.toString().split("/");
            return "<html><body>Directory plugin \"" + parts[parts.length - 1]
                    + "\"<br><span style='font-size:75%'>" + oldURI + "</span></body></html>";
          } catch(PersistenceException e) {
            return path.getOldPath();
          }
        }
      } else if(columnIndex == 1) {
        return new PluginCoordinates(path.getGroupID(), path.getArtifactID());
      } else if(columnIndex == 2) {
        return path.getUpgradeStrategy();
      } else if(columnIndex == 3) {
        // column 3
        return path.getSelectedVersion();
      } else {
        // if we ask for an unknown column (including a negative number)
        // return the upgrade path itself.  This is used as a way to
        // get the UpgradePath for a row by visible row number unpacking
        // the XJTable sorting mechanism.
        return path;
      }
    }
  }

  protected class UpgradeVersionEditor extends DefaultCellEditor {

    @SuppressWarnings("unchecked")
    protected UpgradeVersionEditor() {
      super(new JComboBox<Version>());
      JComboBox<Version> combo = (JComboBox<Version>)getComponent();
      combo.setModel(new DefaultComboBoxModel<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      JComboBox<Version> combo = (JComboBox<Version>)super.getTableCellEditorComponent(table, value, isSelected, row, column);
      DefaultComboBoxModel<Version> model = (DefaultComboBoxModel<Version>)combo.getModel();
      model.removeAllElements();
      UpgradeXGAPP.UpgradePath path = (UpgradeXGAPP.UpgradePath)table.getValueAt(row, -1);
      for(Version v : path.getVersions()) {
        model.addElement(v);
      }
      combo.setSelectedItem(value); // which must be one of the available ones
      return combo;
    }
  }

  protected class UpgradeStrategyRenderer extends DefaultTableCellRenderer {
    private JLabel prototype = new DefaultTableCellRenderer();

    {
      prototype.setText(UpgradeXGAPP.UpgradePath.UpgradeStrategy.PLUGIN_ONLY.label);
      prototype.setIcon(strategyIcons.get(UpgradeXGAPP.UpgradePath.UpgradeStrategy.PLUGIN_ONLY));
    }

    @Override
    public Dimension getMinimumSize() {
      return prototype.getMinimumSize();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      JLabel renderer = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      renderer.setText(((UpgradeXGAPP.UpgradePath.UpgradeStrategy)value).label);
      renderer.setToolTipText(((UpgradeXGAPP.UpgradePath.UpgradeStrategy)value).tooltip);
      renderer.setIcon(strategyIcons.get(value));
      renderer.setDisabledIcon(disabledStrategyIcon);
      renderer.setEnabled(((PluginCoordinates)table.getValueAt(row, 1)).groupId != null);
      return renderer;
    }
  }

  protected class UpgradeStrategyEditor extends DefaultCellEditor {

    @SuppressWarnings("unchecked")
    protected UpgradeStrategyEditor() {
      super(new JComboBox<UpgradeXGAPP.UpgradePath.UpgradeStrategy>());
      JComboBox<UpgradeXGAPP.UpgradePath.UpgradeStrategy> combo = (JComboBox)getComponent();
      combo.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
          JLabel renderer = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
          renderer.setText(((UpgradeXGAPP.UpgradePath.UpgradeStrategy)value).label);
          renderer.setToolTipText(((UpgradeXGAPP.UpgradePath.UpgradeStrategy)value).tooltip);
          renderer.setIcon(strategyIcons.get(value));
          renderer.setDisabledIcon(disabledStrategyIcon);
          return renderer;
        }
      });
      combo.setModel(new DefaultComboBoxModel<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      JComboBox<UpgradeXGAPP.UpgradePath.UpgradeStrategy> combo = (JComboBox)super.getTableCellEditorComponent(table, value, isSelected, row, column);
      DefaultComboBoxModel<UpgradeXGAPP.UpgradePath.UpgradeStrategy> model = (DefaultComboBoxModel)combo.getModel();
      model.removeAllElements();
      if(((String)table.getValueAt(row, 0)).startsWith("<html>")) {
        // directory plugin
        model.addElement(UpgradeXGAPP.UpgradePath.UpgradeStrategy.UPGRADE);
        model.addElement(UpgradeXGAPP.UpgradePath.UpgradeStrategy.PLUGIN_ONLY);
        model.addElement(UpgradeXGAPP.UpgradePath.UpgradeStrategy.SKIP);
      } else {
        // old $gatehome$ or existing Maven
        model.addElement(UpgradeXGAPP.UpgradePath.UpgradeStrategy.UPGRADE);
        model.addElement(UpgradeXGAPP.UpgradePath.UpgradeStrategy.SKIP);
      }
      combo.setSelectedItem(value); // which must be one of the available ones
      return combo;
    }
  }

  protected class PluginCoordinatesEditor extends AbstractCellEditor implements TableCellEditor {
    TableCellRenderer renderer = new DefaultTableCellRenderer();

    JPanel mainPanel;
    JTextField groupIdField;
    JTextField artifactIdField;

    PluginCoordinatesEditor() {
      GridBagLayout layout = new GridBagLayout();
      mainPanel = new JPanel(layout);
      GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(5, 5, 5, 5);

      JLabel label = new JLabel("Please enter Maven co-ordinates of new plugin");
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.CENTER;
      c.weightx = 0;
      c.gridwidth = 2;
      mainPanel.add(label);
      layout.setConstraints(label, c);

      c.anchor = GridBagConstraints.LINE_START;
      c.gridx = 0;
      c.gridy = 1;
      c.gridwidth = 1;
      label = new JLabel("Group ID");
      mainPanel.add(label);
      layout.setConstraints(label, c);

      c.gridx = 1;
      c.weightx = 1;
      groupIdField = new JTextField(30);
      mainPanel.add(groupIdField);
      layout.setConstraints(groupIdField, c);

      c.gridx = 0;
      c.gridy = 2;
      c.weightx = 0;
      label = new JLabel("Artifact ID");
      mainPanel.add(label);
      layout.setConstraints(label, c);

      c.gridx = 1;
      c.weightx = 1;
      artifactIdField = new JTextField(30);
      mainPanel.add(artifactIdField);
      layout.setConstraints(artifactIdField, c);
    }

    PluginCoordinates selected = null;
    @Override
    public Object getCellEditorValue() {
      return selected;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
      if (anEvent instanceof MouseEvent) {
        return ((MouseEvent)anEvent).getClickCount() >= 2;
      } else {
        return true;
      }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      PluginCoordinates coords = (PluginCoordinates)value;
      if(coords.groupId != null) {
        groupIdField.setText(coords.groupId);
      } else {
        groupIdField.setText("");
      }
      artifactIdField.setText(coords.artifactId);
      SwingUtilities.invokeLater(() -> {
        int retVal = JOptionPane.showConfirmDialog(table, mainPanel, "Select new plugin",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(retVal == JOptionPane.CANCEL_OPTION) {
          selected = null;
          fireEditingCanceled();
        } else {
          selected = new PluginCoordinates(groupIdField.getText(), artifactIdField.getText());
          fireEditingStopped();
        }
      });
      return renderer.getTableCellRendererComponent(table,
              value, isSelected, true, row, column);
    }
  }

  protected static class PluginCoordinates {
    String groupId;
    String artifactId;

    public PluginCoordinates(String groupId, String artifactId) {
      this.groupId = groupId;
      this.artifactId = artifactId;
    }

    public String toString() {
      if(groupId == null) {
        return "<html><body><em>&lt;unknown&gt;</em></body></html>";
      } else if("uk.ac.gate.plugins".equals(groupId)) {
        return artifactId;
      } else {
        return groupId + ":" + artifactId;
      }
    }
  }
}
