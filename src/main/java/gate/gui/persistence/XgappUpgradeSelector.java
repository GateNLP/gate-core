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

import gate.persist.PersistenceException;
import gate.swing.XJTable;
import gate.util.persistence.PersistenceManager;
import gate.util.persistence.UpgradeXGAPP;
import org.eclipse.aether.version.Version;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class XgappUpgradeSelector {

  private XJTable pluginTable;

  private JScrollPane scroller;

  private List<UpgradeXGAPP.UpgradePath> upgrades;

  private UpgradePathTableModel model;

  private URI gappUri;

  public XgappUpgradeSelector(URI gappUri, List<UpgradeXGAPP.UpgradePath> upgrades) {
    this.gappUri = gappUri;
    this.upgrades = upgrades;
    this.model = new UpgradePathTableModel(upgrades);

    pluginTable = new XJTable(model);
    pluginTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    scroller = new JScrollPane(pluginTable);

    UpgradeVersionEditor cellEditor = new UpgradeVersionEditor();
    pluginTable.getColumnModel().getColumn(3).setCellEditor(cellEditor);
  }

  public boolean showDialog(Window parent) {
    JOptionPane optionPane = new JOptionPane(scroller, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    JDialog dialog = optionPane.createDialog(parent, "Select plugin versions");
    dialog.setResizable(true);
    dialog.setVisible(true);
    return (((Integer)optionPane.getValue()).intValue() == JOptionPane.OK_OPTION);
  }

  protected class UpgradePathTableModel extends AbstractTableModel {
    private List<UpgradeXGAPP.UpgradePath> upgrades;

    UpgradePathTableModel(List<UpgradeXGAPP.UpgradePath> upgrades) {
      this.upgrades = upgrades;
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
          return String.class;
        case 2:
          return Boolean.class;
        case 3:
          return Version.class;
        default:
          return null;
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return columnIndex == 2 || (columnIndex == 3 && upgrades.get(rowIndex).isShouldUpgrade());
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      if(columnIndex == 2) {
        upgrades.get(rowIndex).setShouldUpgrade((Boolean)aValue);
      } else if(columnIndex == 3) {
        UpgradeXGAPP.UpgradePath path = upgrades.get(rowIndex);
        path.setSelectedVersion((Version) aValue);
      }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      UpgradeXGAPP.UpgradePath path = upgrades.get(rowIndex);
      if(columnIndex == 0) {
        if(path.getOldPath().startsWith("$gatehome$plugins/")) {
          // pre-8.5 GATE_HOME/plugins/Something
          return "Pre-8.5 " + path.getOldPath().substring(18, path.getOldPath().length() - 1) + " (built-in)";
        } else if(path.getOldPath().startsWith("creole:")) {
          // an existing Maven plugin
          String[] gav = path.getOldPath().substring(9).split(";", 3);
          if("uk.ac.gate.plugins".equals(gav[0])) {
            return gav[1];
          } else {
            return gav[0] + ":" + gav[1];
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
        if("uk.ac.gate.plugins".equals(path.getGroupID())) {
          return path.getArtifactID();
        } else {
          return path.getGroupID() + ":" + path.getArtifactID();
        }
      } else if(columnIndex == 2) {
        return path.isShouldUpgrade();
      } else {
        // column 3
        return path.getSelectedVersion();
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
      for(Version v : upgrades.get(row).getVersions()) {
        model.addElement(v);
      }
      combo.setSelectedItem(value); // which must be one of the available ones
      return combo;
    }
  }
}
