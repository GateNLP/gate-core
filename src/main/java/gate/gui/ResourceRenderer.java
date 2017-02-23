/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 02/10/2001
 *
 *  $Id: ResourceRenderer.java 19641 2016-10-06 07:24:25Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import gate.*;
import gate.creole.ResourceData;

/**
 * Renders a {@link Resource} for tables, trees and lists. It will use
 * the icon info from the creole register, the name of the resource as
 * the rendered string and the type of the resource as the tooltip.
 */
@SuppressWarnings({"serial", "rawtypes"})
public class ResourceRenderer extends JLabel implements ListCellRenderer,
                                            TableCellRenderer, TreeCellRenderer {

  public ResourceRenderer() {
    setOpaque(true);
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
    prepareRendererList(list, value, isSelected, hasFocus());
    return this;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
    prepareRendererTable(table, value, isSelected, hasFocus, row, column);
    return this;
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value,
          boolean selected, boolean expanded, boolean leaf, int row,
          boolean hasFocus) {
    prepareRendererTree(tree, value, selected, hasFocus);
    return this;
  }

  private void prepareRendererTable(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
    setName("Table.cellRenderer");
    if(isSelected) {
      super.setForeground(table.getSelectionForeground());
      super.setBackground(table.getSelectionBackground());
    }
    else {
      Color background = table.getBackground();
      if(background == null
              || background instanceof javax.swing.plaf.UIResource) {
//        Color alternateColor = DefaultLookup.getColor(this, ui,
//                "Table.alternateRowColor");
        Color alternateColor = UIManager.getColor("Table.alternateRowColor");
        
        if(alternateColor != null && row % 2 == 0) background = alternateColor;
      }
      super.setForeground(table.getForeground());
      super.setBackground(background);
    }

    if(hasFocus) {
      Border border = null;
      if(isSelected) {
        border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
      }
      if(border == null) {
        border = UIManager.getBorder("Table.focusCellHighlightBorder");
      }
      setBorder(border);
    }
    else {
      setBorder(UIManager.getBorder("Table.cellNoFocusBorder"));
    }

    prepareRendererCommon(table, value, isSelected, hasFocus);
  }

  private void prepareRendererList(JList list, Object value,
          boolean isSelected, boolean hasFocus) {
    if(isSelected) {
      setForeground(list.getSelectionForeground());
      setBackground(list.getSelectionBackground());
    }
    else {
      setForeground(list.getForeground());
      setBackground(list.getBackground());
    }
    prepareRendererCommon(list, value, isSelected, hasFocus);
  }

  private void prepareRendererTree(JTree tree, Object value,
          boolean isSelected, boolean hasFocus) {
    if(isSelected) {
      setForeground(UIManager.getColor("Tree.selectionForeground"));
      setBackground(UIManager.getColor("Tree.selectionBackground"));
    }
    else {
      setForeground(tree.getForeground());
      setBackground(tree.getBackground());
    }
    prepareRendererCommon(tree, value, isSelected, hasFocus);
  }

  private void prepareRendererCommon(JComponent ownerComponent, Object value,
          boolean isSelected, boolean hasFocus) {

    setFont(ownerComponent.getFont());

    String text;
    String toolTipText;
    Icon icon;
    ResourceData rData = null;
    if(value instanceof Resource) {
      text = ((Resource)value).getName();

      rData = Gate.getCreoleRegister().get(
              value.getClass().getName());
    }
    else {
      text = (value == null) ? "<null>" : value.toString();
      if(value == null) setForeground(Color.red);
    }
    if(rData != null) {
      toolTipText = "<HTML>Type: <b>" + rData.getName() + "</b></HTML>";
      /*String iconName = rData.getIcon();
      if(iconName == null) {
        if(value instanceof LanguageResource)
          iconName = "lr";
        else if(value instanceof ProcessingResource)
          iconName = "pr";
        else if(value instanceof Controller) iconName = "application";
      }
      icon = (iconName == null) ? null : MainFrame.getIcon(iconName);*/
      icon = MainFrame.getIcon(rData.getIcon());
    }
    else {
      icon = null;
      toolTipText = null;
    }

    setText(text);
    setIcon(icon);
    setToolTipText(toolTipText);
  }

  
  @Override
  public Dimension getMaximumSize() {
    //we don't mind being extended horizontally
    Dimension dim = super.getMaximumSize();
    if(dim != null){
      dim.width = Integer.MAX_VALUE;
      setMaximumSize(dim);
    }
    return dim;
  }

  @Override
  public Dimension getMinimumSize() {
    //we don't like being squashed!
    return getPreferredSize();
  }
  /*
   * The following methods are overridden as a performance measure to to
   * prune code-paths are often called in the case of renders but which
   * we know are unnecessary. Great care should be taken when writing
   * your own renderer to weigh the benefits and drawbacks of overriding
   * methods like these.
   */

  /**
   * Overridden for performance reasons.
   */
  @Override
  public boolean isOpaque() {
    Color back = getBackground();
    Component p = getParent();
    if(p != null) {
      p = p.getParent();
    }

    // p should now be the JTable.
    boolean colorMatch = (back != null) && (p != null)
            && back.equals(p.getBackground()) && p.isOpaque();
    return !colorMatch && super.isOpaque();
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void invalidate() {
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void validate() {
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void revalidate() {
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void repaint(long tm, int x, int y, int width, int height) {
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void repaint(Rectangle r) {
  }

  /**
   * Overridden for performance reasons.
   */
  @Override
  public void repaint() {
  }

}
