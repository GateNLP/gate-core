/*
 * CheckBoxTableCellRenderer.java
 * 
 * Copyright (c) 2011, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Mark A. Greenwood, 27/11/2011
 */

package gate.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * A TableCellRenderer for JCheckBox that disables the checkbox when the cell
 * isn't editable to make it clear that you can't click on it
 * 
 * @author Mark A. Greenwood
 */
@SuppressWarnings("serial")
public class CheckBoxTableCellRenderer extends JCheckBox implements
                                                        TableCellRenderer {

  /**
   * An empty border we use when the cell doesn't have focus
   */
  private static final Border NO_FOCUS = BorderFactory.createEmptyBorder(1, 1,
          1, 1);

  public CheckBoxTableCellRenderer() {
    super();

    // centre the checkbox within the cell
    setHorizontalAlignment(JCheckBox.CENTER);

    // make sure we always paint the cell border
    setBorderPainted(true);

    // make sure we always paint the background
    setOpaque(true);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {

    // this is needed for Nimbus which has alternative rows in different colours
    // hopefully other L&Fs that also do this use the same UIManager key
    Color alternate = UIManager.getColor("Table.alternateRowColor");

    // strangely the background color from Nimbus doesn't render properly unless
    // we convert it in this way. I'm guessing the problem is to do with the
    // DerivedColor class that nimbus uses
    Color normal = new Color(table.getBackground().getRGB());

    if(isSelected) {
      // if the cell is selected then set it's colors from the table
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      // if the cell isn't selected set it's colors taking into account the
      // possibility of alternating colors that Nimbus throws into the mix
      setForeground(table.getForeground());
      setBackground(alternate != null && row % 2 == 0 ? alternate : normal);
    }

    // if the cell isn't editable then disable the checkbox to give some visual
    // feedback to the user
    setEnabled(table.isCellEditable(row, column));

    // make the checkbox reflect the underlying boolean data
    setSelected(value != null && (Boolean)value);

    if(hasFocus) {
      // if the cell has focus then draw the border set by the L&F
      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
    } else {
      // if the cell doesn't have the focus then draw the empty border
      setBorder(NO_FOCUS);
    }

    // now return the checkbox for rendering into the table
    return this;
  }
}