/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  XJTable.java
 *
 *  Valentin Tablan, 25-Jun-2004
 *
 *  $Id: XJTable.java 17612 2014-03-10 08:51:17Z markagreenwood $
 */

package gate.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import gate.util.ObjectComparator;

/**
 * A &quot;smarter&quot; JTable. Features include:
 * <ul>
 * <li>sorting the table using the values from a column as keys</li>
 * <li>updating the widths of the columns so they accommodate the contents to
 * their preferred sizes.</li>
 * <li>filling the whole viewport by default, unless told not to auto resize 
 * (see {@link JTable#setAutoResizeMode(int)}.
 * <li>sizing the rows according to the preferred sizes of the renderers</li>
 * <li>ability to hide/show columns</li>
 * <li>ability for tab key to skip uneditable cells
 * <li>ability to get in editing mode as soon as a cell gets the focus
 * </ul>
 * It uses a custom made model that stands between the table model set by the
 * user and the GUI component. This middle model is responsible for sorting the
 * rows.
 */
@SuppressWarnings("serial")
public class XJTable extends JTable{

  public XJTable(){
    this(null);
  }
  
  public XJTable(TableModel model){
    super();
    if(model != null) setModel(model);
    // this is a partial fix for 
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4330950:
    // causes the current edit to be committed when focus moves to another
    // component. The other half of this bug (edits lost when the table 
    // header is clicked) is being addressed by overriding 
    // columnMarginChanged(ChangeEvent) and columnMoved(TableColumnModelEvent)
    putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  }
  
  @Override
  public void setModel(TableModel dataModel) {
    sortingModel = new SortingModel(dataModel);
    componentSizedProperly = false;
    super.setModel(sortingModel);
    newColumns();
  }

  /**
   * Called when the columns have changed.
   */
  protected void newColumns(){
    columnData = new ArrayList<ColumnData>(dataModel.getColumnCount());
    hiddenColumns = new ArrayList<TableColumn>();
    for(int i = 0; i < dataModel.getColumnCount(); i++) {
      columnData.add(new ColumnData(i));
    }
  }
  
  
  @Override
  public void setTableHeader(JTableHeader newTableHeader) {
    //first remove the old listener from the old header
    JTableHeader oldHeader = getTableHeader();
    if(oldHeader != null && headerMouseListener != null)
      oldHeader.removeMouseListener(headerMouseListener);
    // set the new header
    super.setTableHeader(newTableHeader);
    //add the mouse listener to the new header
    if(headerMouseListener == null) headerMouseListener = 
      new HeaderMouseListener();
    if(newTableHeader != null) 
      newTableHeader.addMouseListener(headerMouseListener);
  }
  
  @Override
  public Dimension getPreferredScrollableViewportSize(){
    return getPreferredSize();
  }
  
  protected void calculatePreferredSize(){
    try{
      if(sizingInProgress){
        return;
      }else{
        sizingInProgress = true;
      }
      
      int colCount = getColumnModel().getColumnCount();
      if (colCount == 0) { return; }
      //recalculate the preferred sizes if anything changed 
      if(!componentSizedProperly){
        Dimension spacing = getIntercellSpacing();
        //start with all columns at header size
        for(int col = 0; col < colCount; col++){
          TableColumn tColumn = getColumnModel().getColumn(col);
          TableCellRenderer headerRenderer = tColumn.getHeaderRenderer();
          boolean needToResetRenderer = false;
          if(headerRenderer == null){
            needToResetRenderer = true;
            //no header renderer provided -> use default implementation
            JTableHeader tHeader = getTableHeader();
            if(tHeader == null){
              tHeader = new JTableHeader();
            }
            headerRenderer = tHeader.getDefaultRenderer();
            tColumn.setHeaderRenderer(headerRenderer);
          }
          //initialise sizes for columns:
          // - min size = header size
          // - pref size = header size
          if(headerRenderer != null){
            Component c = headerRenderer.getTableCellRendererComponent(
                    XJTable.this, tColumn.getHeaderValue(), false, false, 0, 0);
            int width = c.getMinimumSize().width  + spacing.width;
            if(tColumn.getMinWidth() != width) tColumn.setMinWidth(width);
            tColumn.setPreferredWidth(width);
          }else{
            tColumn.setMinWidth(1);
            tColumn.setPreferredWidth(1);
          }
          
          if (needToResetRenderer) tColumn.setHeaderRenderer(null);
        }
        
        //now fix the row height and column min/max widths
        for(int row = 0; row < getRowCount(); row++){
          //start with all rows of size 1      
          int newRowHeight = 1;
          // update the preferred size of the column ( to make it larger if any 
          // components are larger than then header.
          for(int column = 0; column < getColumnCount(); column ++){
            Component cellComponent = prepareRenderer(getCellRenderer(row, column), 
                    row, column);
            TableColumn tColumn = getColumnModel().getColumn(column);
            int minWidth = ( cellComponent == null ? 0 : 
                cellComponent.getMinimumSize().width ) + spacing.width;
            //minimum width can only grow
            //if needed, increase the max width
//            if(tColumn.getMaxWidth() < minWidth) tColumn.setMaxWidth(minWidth);
            //we prefer not to have any extra space.
            if(tColumn.getPreferredWidth() < minWidth) tColumn.setPreferredWidth(minWidth);

            //now fix the row height
            int height = (cellComponent == null ? 0 : cellComponent.getPreferredSize().height);
            if(newRowHeight < (height + spacing.height)) 
              newRowHeight = height + spacing.height;
          }
          setRowHeight(row, newRowHeight);
        }        
      }//if(!componentSizedProperly){

      //now adjust the column widths, if we need to fill all the space
      if(getAutoResizeMode() != AUTO_RESIZE_OFF){
        //the column being resized (if any)
        TableColumn resizingCol = null;
        if(getTableHeader() != null) resizingCol = 
            getTableHeader().getResizingColumn();
        
        int prefWidth = 0;
        for(int i = 0; i < colCount; i++){
          int colWidth = getColumnModel().getColumn(i).getPreferredWidth();
          if(colWidth > 0) prefWidth += colWidth;
        }
        
        int requestedWidth = getWidth();
        Container parent = this.getParent();
        if(parent != null && parent instanceof JViewport) {
          // only track the viewport width if it is big enough.
          int viewportWidth = ((JViewport)parent).getExtentSize().width;
          if(viewportWidth > requestedWidth){
            requestedWidth = viewportWidth;
          }
        }
        int extra = 0;
        
        if(requestedWidth > prefWidth){
          //we have extra space to fill
          extra = requestedWidth - prefWidth;
          if(getAutoResizeMode() == AUTO_RESIZE_ALL_COLUMNS){
            int extraCol = extra / colCount;
            //distribute the extra space to all columns
            for(int col = 0; col < colCount - 1; col++){
              TableColumn tColumn = getColumnModel().getColumn(col);
              //we leave the resizing column alone
              if(tColumn != resizingCol){
                tColumn.setPreferredWidth(tColumn.getPreferredWidth() + extraCol);
                extra -= extraCol;
              }
            }
          }
          //all the remainder goes to the last col
          TableColumn tColumn = getColumnModel().getColumn(colCount - 1);
          tColumn.setPreferredWidth(tColumn.getPreferredWidth() + extra);        
        }//if(requestedWidth > prefWidth){        
      }//if(getAutoResizeMode() != AUTO_RESIZE_OFF){
    }finally{
      componentSizedProperly = true;
      sizingInProgress = false;
    }
  }
  
  
  
  @Override
  public void doLayout() {
    calculatePreferredSize();
    super.doLayout();
  }

  @Override
  /**
   * Overridden so that the preferred size can be calculated properly
   */
  public Dimension getPreferredSize() {
    //if hard-coded, we return the given value.
    if(isPreferredSizeSet()) return super.getPreferredSize();
    
    //the first time the component is sized, calculate the actual preferred size
    if(!componentSizedProperly){
      calculatePreferredSize();
    }
    return super.getPreferredSize();
  }

  /**
   * Overridden to ignore requests for this table to track the width of its
   * containing viewport in cases where the viewport is narrower than the
   * minimum size of the table.  Where the viewport is at least as wide as
   * the minimum size of the table, we will allow the table to resize with
   * the viewport, but when it gets too small we stop tracking, which allows
   * the horizontal scrollbar to appear.
   */
  @Override
  public boolean getScrollableTracksViewportWidth() {
    if(super.getScrollableTracksViewportWidth()) {
      Container parent = this.getParent();
      if(parent != null && parent instanceof JViewport) {
        // only track the viewport width if it is big enough.
        return ((JViewport)parent).getExtentSize().width
                    > this.getPreferredSize().width;
      } else {
        return true;
      }
    }
    else { // super.getScrollableTracksViewportWidth() == false
      return false;
    }
  }

  /**
   * Track parent viewport's size if it's larger than the current preferred 
   * height of the table (causes empty tables to fill in the whole area of
   * a JScrollPane). 
   * @return true if the preferred height of the table is smaller than the
   *   viewport.
   */
  @Override
  public boolean getScrollableTracksViewportHeight() {
    Container parent = getParent();
    Dimension dim = getPreferredSize();
    return  (parent != null && dim!= null &&
             parent instanceof JViewport && 
             parent.getHeight() > getPreferredSize().height);
  }

  private volatile boolean componentSizedProperly = false;

  private volatile boolean sizingInProgress = false;
  
  
  /**
   * Converts a row number from the model co-ordinates system to the view's. 
   * @param modelRow the row number in the model
   * @return the corresponding row number in the view. 
   * @see #rowViewToModel(int) 
   */
  public int rowModelToView(int modelRow){
    return sortingModel.sourceToTarget(modelRow);
  }

  /**
   * @return Returns the ascending.
   */
  public boolean isAscending() {
    return ascending;
  }
  
  /**
   * Gets the hidden state for a column
   * @param columnIndex index of the column in the table model
   * @return hidden state for a column
   */
  public boolean isColumnHidden(int columnIndex){
    for (TableColumn hiddenColumn : hiddenColumns) {
      if (hiddenColumn.getModelIndex() == columnIndex) {
        return true;
      }
    }
    return false;
  }

  /**
   * Hide a column. Do nothing if already hidden.
   * @param columnIndex index of the column in the table model
   */
  public void hideColumn(int columnIndex) {
    int viewColumn = convertColumnIndexToView(columnIndex);
    TableColumn columnToHide = columnModel.getColumn(viewColumn);
    columnModel.removeColumn(columnToHide);
    hiddenColumns.add(columnToHide);
  }

  /**
   * Show a column. Do nothing if already shown.
   * @param columnIndex index of the column in the table model
   * @param insertionIndex index of the view where the colum will be inserted
   */
  public void showColumn(int columnIndex, int insertionIndex) {
    for (TableColumn hiddenColumn : hiddenColumns) {
      if (hiddenColumn.getModelIndex() == columnIndex) {
        columnModel.addColumn(hiddenColumn);
        columnModel.moveColumn(columnModel.getColumnCount()-1, insertionIndex);
        hiddenColumns.remove(hiddenColumn);
        break;
      }
    }
  }

  /**
   * @param ascending The ascending to set. True by default.
   */
  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }
  /**
   * Converts a row number from the view co-ordinates system to the model's. 
   * @param viewRow the row number in the view.
   * @return the corresponding row number in the model.
   * @see #rowModelToView(int)
   */
  public int rowViewToModel(int viewRow){
    return sortingModel.targetToSource(viewRow);
  }

  /**
   * Set the possibility for the user to hide/show a column by right-clicking
   * on a column header. False by default.
   * @param enableHidingColumns true if and only if the columns can be hidden.
   */
  public void setEnableHidingColumns(boolean enableHidingColumns) {
    this.enableHidingColumns = enableHidingColumns;
  }

  /**
   * Returns the state for hiding a column.
   * @return true if and only if the columns can be hidden.
   */
  public boolean isEnableHidingColumns() {
    return this.enableHidingColumns;
  }

  /**
   * Returns the state for editing a cell as soon as it gets the focus.
   * @return true if and only if a cell get in editing mode when
   * it receives the focus.
   */
  public boolean isEditCellAsSoonAsFocus() {
    return editCellAsSoonAsFocus;
  }

  /**
   * Set the possibility for a cell to be in editing mode as soon as
   * it gets the focus. False by default.
   * @param editCellAsSoonAsFocus true if and only if a cell get in editing
   * mode when it receives the focus.
   */
  public void setEditCellAsSoonAsFocus(boolean editCellAsSoonAsFocus) {
    this.editCellAsSoonAsFocus = editCellAsSoonAsFocus;
  }

  /**
   * Returns the state for enabling tab key to skip uneditable cells.
   * @return true if and only if the tab key skip uneditable cells.
   */
  public boolean isTabSkipUneditableCell() {
    return tabSkipUneditableCell;
  }

  /**
   * Set the possibility for the tab key to skip uneditable cells.
   * False by default.
   * @param tabSkipUneditableCell true if and only if the tab key skip
   * uneditable cells.
   */
  public void setTabSkipUneditableCell(boolean tabSkipUneditableCell) {

    this.tabSkipUneditableCell = tabSkipUneditableCell;

    InputMap im = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    KeyStroke tab = KeyStroke.getKeyStroke("TAB");
    KeyStroke shiftTab = KeyStroke.getKeyStroke("shift TAB");
    if (oldTabAction == null) {
      oldTabAction = getActionMap().get(im.get(tab));
      oldShiftTabAction = getActionMap().get(im.get(shiftTab));
    }

    if (tabSkipUneditableCell) {

      // skip non editable cells when tabbing
      Action tabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          oldTabAction.actionPerformed(e);
          JTable table = (JTable) e.getSource();
          int row = table.getSelectedRow();
          int originalRow = row;
          int column = table.getSelectedColumn();
          int originalColumn = column;
          while(!table.isCellEditable(row, column)) {
            oldTabAction.actionPerformed(e);
            row = table.getSelectedRow();
            column = table.getSelectedColumn();
            //  back to where we started, get out
            if(row == originalRow
              && column == originalColumn) {
              break;
            }
          }
        }
      };
      getActionMap().put(im.get(tab), tabAction);

      // skip non editable cells when shift tabbing
      Action shiftTabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          oldShiftTabAction.actionPerformed(e);
          JTable table = (JTable) e.getSource();
          int row = table.getSelectedRow();
          int originalRow = row;
          int column = table.getSelectedColumn();
          int originalColumn = column;
          while(!table.isCellEditable(row, column)) {
            oldShiftTabAction.actionPerformed(e);
            row = table.getSelectedRow();
            column = table.getSelectedColumn();
            //  back to where we started, get out
            if(row == originalRow
              && column == originalColumn) {
              break;
            }
          }
        }
      };
      getActionMap().put(im.get(shiftTab), shiftTabAction);

    } else {
      getActionMap().put(im.get(tab), oldTabAction);
      getActionMap().put(im.get(shiftTab), oldShiftTabAction);
    }
  }


  /**
   * Sets the custom comparator to be used for a particular column. Columns that
   * don't have a custom comparator will be sorted using the natural order.
   * @param column the column index.
   * @param comparator the comparator to be used.
   */
  public void setComparator(int column, Comparator<?> comparator){
    columnData.get(column).comparator = comparator;
  }
    
  /**
   * @return Returns the sortable.
   */
  public boolean isSortable(){
    return sortable;
  }
  /**
   * @param sortable The sortable to set. True by default.
   */
  public void setSortable(boolean sortable){
    this.sortable = sortable;
  }
  /**
   * @return Returns the sortColumn.
   */
  public int getSortedColumn(){
    return sortedColumn;
  }
  /**
   * @param sortColumn The sortColumn to set. None (-1) by default.
   */
  public void setSortedColumn(int sortColumn){
    this.sortedColumn = sortColumn;
  }
  
  /**
   * Get the row in the table for a row in the model.
   * @param modelRow row in the model
   * @return row in the table view
   */
  public int getTableRow(int modelRow){
    return sortingModel.sourceToTarget(modelRow);
  }

  /**
   * Handles translations between an indexed data source and a permutation of 
   * itself (like the translations between the rows in sorted table and the
   * rows in the actual unsorted model).  
   */
  protected class SortingModel extends AbstractTableModel 
      implements TableModelListener{
    
    public SortingModel(TableModel sourceModel){
      compWrapper = new ValueHolderComparator();
      init(sourceModel);
    }
    
    protected class ValueHolder{
      private Object value;
      private int index;
      public ValueHolder(Object value, int index) {
        super();
        this.value = value;
        this.index = index;
      }
    }
    
    @SuppressWarnings({"rawtypes","unchecked"})
    protected class ValueHolderComparator implements Comparator<ValueHolder>{
      private Comparator comparator;
            
      protected Comparator getComparator() {
        return comparator;
      }

      protected void setComparator(Comparator comparator) {
        this.comparator = comparator;
      }

      @Override
      public int compare(ValueHolder o1, ValueHolder o2) {
        return ascending ? comparator.compare(o1.value, o2.value) :
          comparator.compare(o1.value, o2.value) * -1;
      }
    }
    
    protected void init(TableModel sourceModel){
      if(this.sourceModel != null) 
        this.sourceModel.removeTableModelListener(this);
      this.sourceModel = sourceModel;
      //start with the identity order
      int size = sourceModel.getRowCount();
      sourceToTarget = new int[size];
      targetToSource = new int[size];
      for(int i = 0; i < size; i++) {
        sourceToTarget[i] = i;
        targetToSource[i] = i;
      }
      sourceModel.addTableModelListener(this);
      if(isSortable() && sortedColumn == -1) setSortedColumn(0);
      componentSizedProperly = false;
    }
    
    /**
     * This gets events from the source model and forwards them to the UI
     */
    @Override
    public void tableChanged(TableModelEvent e){
      int type = e.getType();
      int firstRow = e.getFirstRow();
      int lastRow = e.getLastRow();
      int column = e.getColumn();
      
      //deal with the changes in the data
      //we have no way to "repair" the sorting on data updates so we will need
      //to rebuild the order every time
      
      switch(type){
        case TableModelEvent.UPDATE:
          if(firstRow == TableModelEvent.HEADER_ROW){
            //complete structure change -> reallocate the data
            init(sourceModel);
            newColumns();
            fireTableStructureChanged();
            if(isSortable()) sort();
          }else if(lastRow == Integer.MAX_VALUE){
            //all data changed (including the number of rows)
            init(sourceModel);
            if(isSortable()){
              sort();
            } else {
              //this will re-create all rows (which deletes the rowModel in JTable)
              componentSizedProperly = false;
              fireTableDataChanged();
            }
          }else{
            //the rows should have normal values
            //if the sortedColumn is not affected we don't care
            if(isSortable() &&
               (column == sortedColumn || 
                column == TableModelEvent.ALL_COLUMNS)){
                //re-sorting will also fire the event upwards
                sort();
            }else{
              componentSizedProperly = false;
              fireTableChanged(new TableModelEvent(this,  
                      sourceToTarget(firstRow), 
                      sourceToTarget(lastRow), column, type));
              
            }
          }
          break;
        case TableModelEvent.INSERT:
          //rows were inserted -> we need to rebuild
          init(sourceModel);
          if(firstRow != TableModelEvent.HEADER_ROW && firstRow == lastRow){
            //single row insertion
            if(isSortable()) sort();
            else{
              componentSizedProperly = false;
              fireTableChanged(new TableModelEvent(this,  
                    sourceToTarget(firstRow), 
                    sourceToTarget(lastRow), column, type));
            }
          }else{
            //the real rows are not in sequence
            if(isSortable()) sort();
            else {
              //this will re-create all rows (which deletes the rowModel in JTable)
              componentSizedProperly = false;
              fireTableDataChanged();
            }
          }
          break;
        case TableModelEvent.DELETE:
          //rows were deleted -> we need to rebuild
          init(sourceModel);
          if(isSortable()) sort();
          else {
            //this will re-create all rows (which deletes the rowModel in JTable)
            componentSizedProperly = false;
            fireTableDataChanged();
          }
      }
    }
    
    @Override
    public int getRowCount(){
      return sourceToTarget.length;
//      return sourceModel.getRowCount();
    }
    
    @Override
    public int getColumnCount(){
      return sourceModel.getColumnCount();
    }
    
    @Override
    public String getColumnName(int columnIndex){
      return sourceModel.getColumnName(columnIndex);
    }
    @Override
    public Class<?> getColumnClass(int columnIndex){
      return sourceModel.getColumnClass(columnIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
      int sourceRow = targetToSource(rowIndex);
      return sourceRow >= 0 ? 
             sourceModel.isCellEditable(sourceRow, columnIndex) : 
             false;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
      int sourceRow = targetToSource(rowIndex);
      if(sourceRow >= 0) sourceModel.setValueAt(aValue, sourceRow, columnIndex);
    }
    
    @Override
    public Object getValueAt(int row, int column){
      try{
        return sourceModel.getValueAt(targetToSource(row), column);
      }catch(IndexOutOfBoundsException iobe){
        //this can occur because of multithreaded access -> some threads empties 
        //the data while some other thread tries to update the display.
        //this error is safe to ignore as the GUI will get updated by another 
        //event once the change to the data has been effected
        return null;
      }
    }
    
    /**
     * Sorts the table using the values in the specified column and sorting order.
     * sortedColumn is the column used for sorting the data.
     * ascending is the sorting order.
     */
    public void sort(){
      try {
        if (sortedColumn >= columnData.size()) { return; }
        //save the selection
        int[] rows = getSelectedRows();
        //convert to model co-ordinates
        for(int i = 0; i < rows.length; i++)  rows[i] = rowViewToModel(rows[i]);
        //create a list of ValueHolder objects for the source data that needs
        //sorting
        List<ValueHolder> sourceData = 
            new ArrayList<ValueHolder>(sourceModel.getRowCount());
        //get the data in the source order
        for(int i = 0; i < sourceModel.getRowCount(); i++){
          Object value = sourceModel.getValueAt(i, sortedColumn);
          sourceData.add(new ValueHolder(value, i));
        }
        
        //get an appropriate comparator
        Comparator<?> comparator = columnData.get(sortedColumn).comparator;
        if(comparator == null){
          //use the default comparator
          if(defaultComparator == null) defaultComparator = new ObjectComparator();
          comparator = defaultComparator;
        }
        compWrapper.setComparator(comparator);
        //perform the actual sorting
        Collections.sort(sourceData, compWrapper);
        //extract the new order
        for(int i = 0; i < sourceData.size(); i++){
          int targetIndex = i;
          int sourceIndex = sourceData.get(i).index;
          sourceToTarget[sourceIndex] = targetIndex;
          targetToSource[targetIndex] = sourceIndex;
        }
        sourceData.clear();
        //the rows may have moved about so we need to recalculate the heights
        componentSizedProperly = false;
        //this deletes the JTable row model 
        fireTableDataChanged();
        //we need to recreate the row model, and only then restore selection
        resizeAndRepaint();
        //restore the selection
        clearSelection();
        for(int i = 0; i < rows.length; i++){
            rows[i] = rowModelToView(rows[i]);
            if(rows[i] > 0 && rows[i] < getRowCount())
              addRowSelectionInterval(rows[i], rows[i]);
        }
      }catch(ArrayIndexOutOfBoundsException aioob) {
        //this can happen when update events get behind
        //just ignore - we'll get another event later to cause the sorting
      }
    }
    /**
     * Converts an index from the source coordinates to the target ones.
     * Used to propagate events from the data source (table model) to the view. 
     * @param index the index in the source coordinates.
     * @return the corresponding index in the target coordinates or -1 if the 
     * provided row is outside the permitted values.
     */
    public int sourceToTarget(int index){
      return (index >= 0 && index < sourceToTarget.length) ?
              sourceToTarget[index] :
              -1;
    }

    /**
     * Converts an index from the target coordinates to the source ones. 
     * @param index the index in the target coordinates.
     * Used to propagate events from the view (e.g. editing) to the source
     * data source (table model).
     * @return the corresponding index in the source coordinates or -1 if the 
     * row provided is outside the allowed range.
     */
    public int targetToSource(int index){
      return (index >= 0 && index < targetToSource.length) ?
             targetToSource[index] :
             -1;
    }
    
    /**
     * Builds the reverse index based on the new sorting order.
     */
    protected void buildTargetToSourceIndex(){
      targetToSource = new int[sourceToTarget.length];
      for(int i = 0; i < sourceToTarget.length; i++)
        targetToSource[sourceToTarget[i]] = i;
    }
    
    /**
     * The direct index
     */
    protected int[] sourceToTarget;
    
    /**
     * The reverse index.
     */
    protected int[] targetToSource;
    
    protected TableModel sourceModel;
    
    protected ValueHolderComparator compWrapper;
  }
  
  protected class HeaderMouseListener extends MouseAdapter{
    @Override
    public void mouseClicked(MouseEvent e){
      process(e);
    }

    @Override
    public void mousePressed(MouseEvent e){
      process(e);
    }

    @Override
    public void mouseReleased(MouseEvent e){
      process(e);
    }

    protected void process(MouseEvent e){
      final int viewColumn = columnModel.getColumnIndexAtX(e.getX());
      if(viewColumn != -1){
        final int column = convertColumnIndexToModel(viewColumn);
        if(e.isPopupTrigger()
        && enableHidingColumns){
          //show pop-up
          JPopupMenu popup = new JPopupMenu();
          if (columnModel.getColumnCount() > 1) {
            popup.add(new AbstractAction("Hide column "
              + dataModel.getColumnName(column)){
              @Override
              public void actionPerformed(ActionEvent e) {
                hideColumn(column);
              }
            });
          }
          if (hiddenColumns.size() > 0) {
            popup.addSeparator();
          }
          for (TableColumn hiddenColumn : hiddenColumns) {
            final TableColumn hiddenColumnF = hiddenColumn;
            popup.add(new AbstractAction("Show column "
              + dataModel.getColumnName(hiddenColumn.getModelIndex())){
              @Override
              public void actionPerformed(ActionEvent e) {
                showColumn(hiddenColumnF.getModelIndex(), viewColumn);
              }
            });
          }
          popup.show(e.getComponent(), e.getX(), e.getY());
        }
        else if(!e.isPopupTrigger()
              && e.getID() == MouseEvent.MOUSE_CLICKED
              && e.getClickCount() == 1){
          //normal click -> re-sort
          if(sortable && column != -1) {
            ascending = (column == sortedColumn) ? !ascending : true;
            sortedColumn = column;
            sortingModel.sort();
          }
        }
      }
    }
  }
  
  protected class ColumnData{
    public ColumnData(int column){
      this.column = column;
    }
    
    int column;
    int columnWidth;
    Comparator<?> comparator;
  }

  @Override
  public void changeSelection(int rowIndex, int columnIndex,
                              boolean toggle, boolean extend) {
    super.changeSelection(rowIndex, columnIndex, toggle, extend);
    if (!toggle && !extend && editCellAsSoonAsFocus) {
      // start cell editing as soon as a cell is selected
      if(!isEditing() && isCellEditable(rowIndex, columnIndex)) this.editCellAt(rowIndex, columnIndex);
    }
  }

  @Override
  public int rowAtPoint(Point point) {
    //The Swing implementation is very keen to drop the rowModel. We KNOW we
    //should always have a row model, so if null, create it!
    if(!componentSizedProperly){
      calculatePreferredSize();
    }
    return super.rowAtPoint(point);
  }

  /**
   * Overridden for efficiency reasons (provides a better calculation of the 
   * dirty region). See 
   * <a href="http://www.objectdefinitions.com/odblog/2009/jtable-setrowheight-causes-slow-repainting/">this page</a>
   * for a more complete discussion. 
   */
  @Override
  public void tableChanged(TableModelEvent e) {
    //if just an update, and not a data or structure changed event or an insert or delete, use the fixed row update handling
    //otherwise call super.tableChanged to let the standard JTable update handling manage it
    if ( e != null &&
        e.getType() == TableModelEvent.UPDATE &&
        e.getFirstRow() != TableModelEvent.HEADER_ROW &&
        e.getLastRow() != Integer.MAX_VALUE) {
        handleRowUpdate(e);
    } else {
        super.tableChanged(e);
    }
  }

  /**
   * This borrows most of the logic from the superclass handling of update 
   * events, but changes the calculation of the height for the dirty region to 
   * provide proper handling for repainting custom height rows.
   * Copied from <a href="http://www.objectdefinitions.com/odblog/2009/jtable-setrowheight-causes-slow-repainting/">this page</a>.
   */
  private void handleRowUpdate(TableModelEvent e) {
    int modelColumn = e.getColumn();
    int start = e.getFirstRow();
    int end = e.getLastRow();

    Rectangle dirtyRegion;
    if (modelColumn == TableModelEvent.ALL_COLUMNS) {
        // 1 or more rows changed
      int rowStart = 0;
      for ( int row=0; row < start; row++ ) rowStart += getRowHeight(row);
      dirtyRegion = new Rectangle(0, rowStart, 
              getColumnModel().getTotalColumnWidth(), 0);
      
    } else {
      // A cell or column of cells has changed.
      // Unlike the rest of the methods in the JTable, the TableModelEvent
      // uses the coordinate system of the model instead of the view.
      // This is the only place in the JTable where this "reverse mapping"
      // is used.
      int column = convertColumnIndexToView(modelColumn);
      dirtyRegion = getCellRect(start, column, true);
    }

    // Now adjust the height of the dirty region
    dirtyRegion.height = 0;
    for (int row = start; row <= end; row ++ ) {
      //THIS IS CHANGED TO CALCULATE THE DIRTY REGION HEIGHT CORRECTLY
      dirtyRegion.height += getRowHeight(row);
    }
    repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
  }
  
  
  /**
   * Overridden to fix 
   * //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4330950:
   */ 
  @Override
  public void columnMoved(TableColumnModelEvent e) {
    if (isEditing()) {
        cellEditor.stopCellEditing();
    }
    super.columnMoved(e);
  }

  /**
   * Overridden to fix 
   * //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4330950:
   */   
  @Override
  public void columnMarginChanged(ChangeEvent e) {
    //save the current edit (otherwise it gets lost)
    if (isEditing()) cellEditor.stopCellEditing();
    //allow resizing of columns even if JTable believes we should block it.
    TableColumn resizingCol = null;
    if(getTableHeader() != null) resizingCol = 
        getTableHeader().getResizingColumn();
    if (resizingCol != null) {
      resizingCol.setPreferredWidth(resizingCol.getWidth());
    }
    
    super.columnMarginChanged(e);
  }

  protected SortingModel sortingModel;
  protected ObjectComparator defaultComparator;
  
  /**
   * The column currently being sorted.
   */
  protected int sortedColumn = -1;
  
  /**
   * is the current sort order ascending (or descending)?
   */
  protected boolean ascending = true;
  /**
   * Should this table be sortable.
   */
  protected boolean sortable = true;
  
  /**
   * A list of {@link ColumnData} objects.
   */
  protected List<ColumnData> columnData;
  
  protected HeaderMouseListener headerMouseListener;

  /**
   * Contains the hidden columns in no particular order.
   */
  protected List<TableColumn> hiddenColumns;

  private boolean enableHidingColumns = false;

  private boolean tabSkipUneditableCell = false;

  private boolean editCellAsSoonAsFocus = false;

  private Action oldTabAction;

  private Action oldShiftTabAction;
}
