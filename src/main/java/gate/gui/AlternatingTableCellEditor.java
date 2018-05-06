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
 *  $Id: AlternatingTableCellEditor.java 1 2018-05-04 14:36:21Z ian_roberts $
 *
 */
package gate.gui;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * <p>Table cell editor which delegates to a number of other editors, switching between
 * them in round-robin fashion on each call to {@link #getTableCellEditorComponent}.
 * This works around rendering issues that occur particularly with JComboBox-based
 * cell editors when you try to switch between cells on different rows that would
 * be edited using the same JComboBox object, and the "pop-down" of the combo box
 * from the previously-edited row is not properly sequenced with the change of model
 * and pop-up on the subsequently-edited row.</p>
 * <p>This class is not thread-safe, but all the method calls happen on the EDT
 * anyway so it doesn't have to be.</p>
 * <p>The behaviour of this class is as follows:</p>
 * <ul>
 *   <li>The add/remove listener methods are passed through to <em>all</em> of
 *   the underlying editors, so listeners will receive events from any of them</li>
 *   <li>The "current editor" is stepped along the provided array at each call to
 *   <code>getTableCellEditorComponent</code> - consecutive calls to this method will
 *   be forwarded to different underlying editor instances</li>
 *   <li>All other methods forward to whichever editor handled the most recent
 *   <code>getTableCellEditorComponent</code> call.</li>
 * </ul>
 */
public class AlternatingTableCellEditor implements TableCellEditor {

  /**
   * The real editors to which we delegate round-robin.  They should
   * generally all be separate but identical instances of the same class.
   */
  private TableCellEditor[] editors;

  /**
   * The index into {@link #editors} of the last one returned from
   * {@link #getTableCellEditorComponent}
   */
  private int currentEditor = 0;

  /**
   * Create an <code>AlternatingTableCellEditor</code> that delegates to
   * the specified other editors in round robin order.  For a sensible
   * user experience all the delegate editors should have identical
   * behaviour (they would typically be several instances of the same
   * class constructed with the same arguments).
   * @param editors the underlying editors to which we will delegate.
   */
  public AlternatingTableCellEditor(TableCellEditor... editors) {
    this.editors = editors;
  }

  /**
   * Switches focus to the <em>next</em> cell editor in the provided list,
   * and then passes on the call to that editor.
   * @param table the table being edited
   * @param value the current value of the cell being edited
   * @param isSelected true if the cell is to be rendered with highlighting
   * @param row the row index of the cell being edited
   * @param column the column index of the cell being edited
   * @return a component capable of editing the specified cell
   */
  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return editors[currentEditor = (currentEditor + 1) % editors.length].getTableCellEditorComponent(table, value, isSelected, row, column);
  }

  @Override
  public Object getCellEditorValue() {
    return editors[currentEditor].getCellEditorValue();
  }

  @Override
  public boolean isCellEditable(EventObject anEvent) {
    return editors[currentEditor].isCellEditable(anEvent);
  }

  @Override
  public boolean shouldSelectCell(EventObject anEvent) {
    return editors[currentEditor].shouldSelectCell(anEvent);
  }

  @Override
  public boolean stopCellEditing() {
    return editors[currentEditor].stopCellEditing();
  }

  @Override
  public void cancelCellEditing() {
    editors[currentEditor].cancelCellEditing();
  }

  /**
   * Add the specified listener to <em>all</em> the delegate editors.
   * @param l the listener to add
   */
  @Override
  public void addCellEditorListener(CellEditorListener l) {
    for(TableCellEditor e : editors) {
      e.addCellEditorListener(l);
    }
  }

  /**
   * Remove the specified listener from <em>all</em> the delegate editors.
   * @param l the listener to remove.
   */
  @Override
  public void removeCellEditorListener(CellEditorListener l) {
    for(TableCellEditor e : editors) {
      e.removeCellEditorListener(l);
    }
  }
}
