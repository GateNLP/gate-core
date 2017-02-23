/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 23/01/2001
 *
 *  $Id: BooleanRenderer.java 17615 2014-03-10 11:10:08Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A {@link javax.swing.table.TableCellRenderer} used for Booleans
 */
@SuppressWarnings("serial")
public class BooleanRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
    JLabel component = (JLabel)super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row,
            column);
    if(value instanceof Boolean && value != null
            && ((Boolean)value).booleanValue()) {
      component.setIcon(MainFrame.getIcon("tick"));
      // setIcon(MainFrame.getIcon((isSelected) ? "tick_white" :
      // "tick"));
    } else {
      component.setIcon(null);
    }

    return component;
  }// public Component getTableCellRendererComponent
}// class BooleanRenderer extends DefaultTableCellRenderer
