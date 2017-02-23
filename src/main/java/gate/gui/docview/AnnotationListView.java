/*
 *  Copyright (c) 2009-2010, Ontotext AD.
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  AnnotatioListView.java
 *
 *  Valentin Tablan, May 25, 2004
 *
 *  $Id: AnnotationListView.java 17606 2014-03-09 12:12:49Z markagreenwood $
 */

package gate.gui.docview;

import gate.*;
import gate.creole.*;
import gate.event.AnnotationEvent;
import gate.event.AnnotationListener;
import gate.gui.MainFrame;
import gate.gui.annedit.*;
import gate.swing.XJTable;
import gate.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.JTextComponent;

/**
 * A tabular view for a list of annotations.
 * Used as part of the document viewer to display all the annotation currently
 * highlighted.
 */
@SuppressWarnings("serial")
public class AnnotationListView extends AbstractDocumentView
  implements AnnotationListener, AnnotationList, AnnotationEditorOwner{

  public AnnotationListView(){
    annDataList = new ArrayList<AnnotationData>();
    editorsCache = new HashMap<String, AnnotationVisualResource>();
  }


   /**
    *  (non-Javadoc)
    * @see gate.gui.docview.AnnotationList#getAnnotationAtRow(int)
    */
   @Override
  public AnnotationData getAnnotationAtRow(int row) {
     return annDataList == null ? null : annDataList.get(
             table.rowViewToModel(row));
   }


   /* (non-Javadoc)
    * @see gate.gui.docview.AnnotationList#getSelectionModel()
    */
   @Override
  public ListSelectionModel getSelectionModel() {
     return table == null ? null : table.getSelectionModel();
   }


   @Override
   public void cleanup() {
     super.cleanup();
     for(AnnotationData aData : annDataList){
       aData.getAnnotation().removeAnnotationListener(this);
     }
     annDataList.clear();
     textView = null;
   }

   /* (non-Javadoc)
    * @see gate.gui.docview.AbstractDocumentView#initGUI()
    */
   @Override
  protected void initGUI() {
     tableModel = new AnnotationTableModel();
     table = new XJTable(tableModel);
     table.setAutoResizeMode(XJTable.AUTO_RESIZE_LAST_COLUMN);
     table.setSortable(true);
     table.setSortedColumn(START_COL);
     table.setIntercellSpacing(new Dimension(2, 0));
     table.setEnableHidingColumns(true);
     scroller = new JScrollPane(table);

     mainPanel = new JPanel();
     mainPanel.setLayout(new GridBagLayout());
     GridBagConstraints constraints = new GridBagConstraints();

     constraints.gridx = 0;
     constraints.gridwidth = 4;
     constraints.gridy = 0;
     constraints.weightx = 1;
     constraints.weighty = 1;
     constraints.fill= GridBagConstraints.BOTH;
     mainPanel.add(scroller, constraints);

     constraints.gridx = GridBagConstraints.RELATIVE;
     constraints.gridwidth = 1;
     constraints.gridy = 1;
     constraints.weightx = 0;
     constraints.weighty = 0;
     constraints.fill= GridBagConstraints.NONE;
     constraints.anchor = GridBagConstraints.WEST;
     statusLabel = new JLabel();
     mainPanel.add(statusLabel, constraints);
     constraints.fill= GridBagConstraints.HORIZONTAL;
     constraints.anchor = GridBagConstraints.EAST;
     mainPanel.add(Box.createHorizontalStrut(10), constraints);
     mainPanel.add(new JLabel("Select: "), constraints);
     filterTextField = new JTextField(20);
     filterTextField.setToolTipText("Select the rows containing this text.");
     mainPanel.add(filterTextField, constraints);

     //get a pointer to the text view used to display
     //the selected annotations
     Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
     while(textView == null && centralViewsIter.hasNext()){
       DocumentView aView = centralViewsIter.next();
       if(aView instanceof TextualDocumentView)
         textView = (TextualDocumentView)aView;
     }

     initListeners();
   }

   @Override
  public Component getGUI(){
     return mainPanel;
   }

   protected void initListeners(){

        tableModel.addTableModelListener(new TableModelListener(){
          @Override
          public void tableChanged(TableModelEvent e){
            statusLabel.setText(
                    Integer.toString(tableModel.getRowCount()) +
                    " Annotations (" +
                    Integer.toString(table.getSelectedRowCount()) +
                    " selected)");
          }
        });

        table.getSelectionModel().
          addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
              if(!isActive())return;
              if(e.getValueIsAdjusting()) return;
              statusLabel.setText(
                      Integer.toString(tableModel.getRowCount()) +
                      " Annotations (" +
                      Integer.toString(table.getSelectedRowCount()) +
                      " selected)");
              //if the new selection is already known about, no more work to do 
              if(localSelectionUpdating) return;
              //update the list of selected annotations globally
              int[] viewRows = table.getSelectedRows();
              List<AnnotationData> selAnns = new ArrayList<AnnotationData>();
              for(int i = 0; i < viewRows.length; i++){
                int modelRow = table.rowViewToModel(viewRows[i]);
                if(modelRow >= 0){
                  selAnns.add(annDataList.get(modelRow));
                }
              }
              owner.setSelectedAnnotations(selAnns);
              
              if(table.getSelectedRowCount() >= 1){
                int viewRow = table.getSelectionModel().getLeadSelectionIndex();
                if(table.getSelectionModel().isSelectedIndex(viewRow)){
                  int modelRow = table.rowViewToModel(viewRow);
                  AnnotationData aHandler = annDataList.get(modelRow);
                  //scroll to show the last highlight
                  if(aHandler != null && aHandler.getAnnotation() != null)
                    textView.scrollAnnotationToVisible(aHandler.getAnnotation());
                }else{
                  //last operation was a remove selection
                  //do nothing
                }
              }
            }
        });

        table.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent me) {
            processMouseEvent(me);
          }
          @Override
          public void mouseReleased(MouseEvent me) {
            processMouseEvent(me);
          }
          @Override
          public void mousePressed(MouseEvent me) {
            int row = table.rowAtPoint(me.getPoint());
            if(me.isPopupTrigger()
            && !table.isRowSelected(row)) {
              // if right click outside the selection then reset selection
              table.getSelectionModel().setSelectionInterval(row, row);
            }
            processMouseEvent(me);
          }
          protected void processMouseEvent(MouseEvent me){
            int viewRow = table.rowAtPoint(me.getPoint());
            final int modelRow = viewRow == -1 ?
                                 viewRow :
                                 table.rowViewToModel(viewRow);

            // popup menu
            if(me.isPopupTrigger()) {
              JPopupMenu popup = new JPopupMenu();
              popup.add(new DeleteAction());

              //add the custom edit actions
              if(modelRow != -1){
                AnnotationData aHandler = annDataList.get(modelRow);
                popup.addSeparator();
                List<Action> specificEditorActions = getSpecificEditorActions(
                  aHandler.getAnnotationSet(), aHandler.getAnnotation());
                for (Action action : specificEditorActions) {
                  popup.add(action);
                }
                if (!(popup.getComponent(popup.getComponentCount()-1)
                    instanceof JSeparator)) {
                  popup.addSeparator();
                }
                for (Action action : getGenericEditorActions(
                  aHandler.getAnnotationSet(), aHandler.getAnnotation())) {
                  if (specificEditorActions.contains(action)) { continue; }
                  popup.add(action);
                }
                if ((popup.getComponent(popup.getComponentCount()-1)
                    instanceof JSeparator)) {
                  popup.remove(popup.getComponentCount()-1);
                }
              }
              popup.show(table, me.getX(), me.getY());
            }
          }
        });

        table.addAncestorListener(new AncestorListener() {
          @Override
          public void ancestorAdded(AncestorEvent event) {
            // force the table to be sorted when the view is shown
            tableModel.fireTableDataChanged();
          }
          @Override
          public void ancestorMoved(AncestorEvent event) {
          }
          @Override
          public void ancestorRemoved(AncestorEvent event) {
          }
        });

    // select all the rows containing the text from filterTextField
    filterTextField.getDocument().addDocumentListener( new DocumentListener() {
      private Timer timer = new Timer("Annotation list selection timer", true);
      private TimerTask timerTask;
      @Override
      public void changedUpdate(DocumentEvent e) { /* do nothing */ }
      @Override
      public void insertUpdate(DocumentEvent e) { update(); }
      @Override
      public void removeUpdate(DocumentEvent e) { update(); }
      private void update() {
        if (timerTask != null) { timerTask.cancel(); }
        Date timeToRun = new Date(System.currentTimeMillis() + 300);
        timerTask = new TimerTask() { @Override
        public void run() {
          selectRows();
        }};
        // add a delay
        timer.schedule(timerTask, timeToRun);
      }
      private void selectRows() {
        table.clearSelection();
        if (filterTextField.getText().trim().length() < 2
         || table.getRowCount() == 0) {
          return;
        }
        // block upward events
        localSelectionUpdating = true;
        for (int row = 0; row < table.getRowCount(); row++) {
          for (int col = 0; col < table.getColumnCount(); col++) {
            if (table.getValueAt(row, col) != null
             && table.getValueAt(row, col).toString()
                .contains(filterTextField.getText().trim())) {
              table.addRowSelectionInterval(row, row);
              break;
            }
          }
        }
        localSelectionUpdating = false;
        // update the highlights in the document
        if (table.isCellSelected(0,0)) {
          table.addRowSelectionInterval(0, 0);
        } else {
          table.removeRowSelectionInterval(0, 0);
        }
      }
    });

    // Delete key for deleting selected annotations
    table.addKeyListener(new KeyAdapter() {
     @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_DELETE) {
         new DeleteAction().actionPerformed(new ActionEvent(e.getSource(),
           e.getID(), "", e.getWhen(), e.getModifiers()));
       }
     }
    });
  }

  public List<Action> getSpecificEditorActions(AnnotationSet set,
                                               Annotation annotation) {
    List<Action> actions = new ArrayList<Action>();
    //add the specific editors
    List<String> specificEditorClasses =
      Gate.getCreoleRegister().getAnnotationVRs(annotation.getType());
    if (specificEditorClasses != null &&
      specificEditorClasses.size() > 0) {
      for (String editorClass : specificEditorClasses) {
        AnnotationVisualResource editor =
          editorsCache.get(editorClass);
        if (editor == null) {
          //create the new type of editor
          try {
            editor = (AnnotationVisualResource)
              Factory.createResource(editorClass);
            editorsCache.put(editorClass, editor);
          } catch (ResourceInstantiationException rie) {
            rie.printStackTrace(Err.getPrintWriter());
          }
        }
        actions.add(new EditAnnotationAction(set, annotation, editor));
      }
    }
    return actions;
  }

  public List<Action> getGenericEditorActions(AnnotationSet set,
                                              Annotation annotation) {
    List<Action> actions = new ArrayList<Action>();
    //add generic editors
    List<String> genericEditorClasses = Gate.getCreoleRegister().
      getAnnotationVRs();
    if (genericEditorClasses != null &&
      genericEditorClasses.size() > 0) {
      for (String editorClass : genericEditorClasses) {
        AnnotationVisualResource editor = editorsCache.get(editorClass);
        if (editor == null) {
          //create the new type of editor
          try {
            ResourceData resData = Gate.getCreoleRegister().get(editorClass);
            Class<?> resClass = resData.getResourceClass();
            if (OwnedAnnotationEditor.class.isAssignableFrom(resClass)) {
              OwnedAnnotationEditor newEditor =
                (OwnedAnnotationEditor) resClass.newInstance();
              newEditor.setOwner(AnnotationListView.this);
              newEditor.init();
              editor = newEditor;
            } else {
              editor = (AnnotationVisualResource)
                Factory.createResource(editorClass);
            }
            editorsCache.put(editorClass, editor);
          } catch (Exception rie) {
            rie.printStackTrace(Err.getPrintWriter());
          }
        }
        actions.add(new EditAnnotationAction(set, annotation, editor));
      }
    }
    return actions;
  }

  protected class DeleteAction extends AbstractAction {
    public DeleteAction() {
      super("Delete Annotations");
      putValue(SHORT_DESCRIPTION, "Delete selected annotations");
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("DELETE"));
    }
    @Override
    public void actionPerformed(ActionEvent event){
      if ((event.getModifiers() & ActionEvent.SHIFT_MASK)
                               != ActionEvent.SHIFT_MASK
       && (event.getModifiers() & InputEvent.BUTTON1_MASK)
                               != InputEvent.BUTTON1_MASK) {
        // shows a confirm dialog before to delete annotations
        int choice = JOptionPane.showOptionDialog(MainFrame.getInstance(), new
          Object[]{"Are you sure you want to delete the "
          + table.getSelectedRowCount() + " selected annotations?",
          "<html><i>You can use Shift+Delete to bypass this dialog.</i>\n\n"},
          "Delete annotations",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          new String[]{"Delete annotations", "Cancel"}, "Cancel");
        if (choice == JOptionPane.CLOSED_OPTION || choice == 1)  { return; }
      }
      List<AnnotationData> annotationsData = new ArrayList<AnnotationData>();
      for (int selectedRow : table.getSelectedRows()) {
        annotationsData.add(annDataList.get(table.rowViewToModel(selectedRow)));
      }
      for (AnnotationData annotationData : annotationsData) {
        annotationData.getAnnotationSet().remove(annotationData.getAnnotation());
      }
    }
  }

   /* (non-Javadoc)
       * @see gate.gui.docview.AbstractDocumentView#registerHooks()
       */
      @Override
      protected void registerHooks() {
        //this is called on activation
    //    showHighlights();
      }

   /* (non-Javadoc)
    * @see gate.gui.docview.AbstractDocumentView#unregisterHooks()
    */
   @Override
  protected void unregisterHooks() {
     //this is called on de-activation
     //remove highlights
 //    textView.removeAllBlinkingHighlights();
   }

   /* (non-Javadoc)
    * @see gate.gui.docview.DocumentView#getType()
    */
   @Override
  public int getType() {
     return HORIZONTAL;
   }

 //  protected void showHighlights(){
 //    int[] viewRows = table.getSelectedRows();
 //    AnnotationData aHandler = null;
 //    for(int i = 0; i < viewRows.length; i++){
 //      int modelRow = table.rowViewToModel(viewRows[i]);
 //      if(modelRow >= 0){
 //        aHandler = annDataList.get(modelRow);
 //        textView.addBlinkingHighlight(aHandler.getAnnotation());
 //      }
 //    }
 //  }


   /**
    * Adds an annotation to be displayed in the list.
    * @param ann the annotation
    * @param set the set containing the annotation
    * @return a tag that can be used to refer to this annotation for future
    * operations, e.g. when removing the annotation from display.
    */
   public AnnotationDataImpl addAnnotation(Annotation ann, AnnotationSet set){
     AnnotationDataImpl aData = new AnnotationDataImpl(set, ann);
     annDataList.add(aData);
     int row = annDataList.size() -1;
     try{
       localSelectionUpdating = true;
       if(tableModel != null) tableModel.fireTableRowsInserted(row, row);
     }finally{
       localSelectionUpdating = false;
     }

     //listen for the new annotation's events
     aData.getAnnotation().addAnnotationListener(AnnotationListView.this);
     return aData;
   }

   public void removeAnnotation(AnnotationData tag){
     int row = annDataList.indexOf(tag);
     if(row >= 0){
       AnnotationData aHandler = annDataList.get(row);
       //remove from selection, if the table is built
       List<AnnotationData> selAnns = owner.getSelectedAnnotations();
       if(selAnns.remove(tag)){
         owner.setSelectedAnnotations(selAnns);
       }
       aHandler.getAnnotation().removeAnnotationListener(
               AnnotationListView.this);
       annDataList.remove(row);
       //owner was already notified
       try{
         localSelectionUpdating = true;
         if(tableModel != null) tableModel.fireTableRowsDeleted(row, row);
       }finally{
         localSelectionUpdating = false;
     }
   }
   }

   public void removeAnnotations(Collection<AnnotationData> tags){
     //to speed-up things, first remove all blinking highlights
     if(table != null){
       table.getSelectionModel().clearSelection();
     }
     //cache the selected annotations
     final List<AnnotationData> selAnns = owner.getSelectedAnnotations();
     boolean selectionChanged = false;
     //now do the actual removal, in batch mode
     for(AnnotationData aData : tags){
       annDataList.remove(aData);
       if(selAnns.remove(aData)){
         selectionChanged = true;
       }
       aData.getAnnotation().removeAnnotationListener(AnnotationListView.this);
     }
     //update the table display
     if(tableModel != null) tableModel.fireTableDataChanged();
     //restore selection
     if(selectionChanged){
       //this needs to happen after the table has caught up with all the changes
       //hence we need to queue it to the GUI thread
       SwingUtilities.invokeLater(new Runnable(){
       @Override
      public void run(){
         owner.setSelectedAnnotations(selAnns);
       }});
     }
   }

   /**
    * Adds a batch of annotations in one go.
    * For each annotation, a tag object will be created. The return value is
    * list that returns the tags in the same order as the collection used
    * for the input annotations.
    * This method does not assume it was called from the UI Thread.
    * @return a collection of tags corresponding to the annotations.
    * @param annotations a collection of annotations
    * @param set the annotation set to which all the annotations belong.
    */
   public List<AnnotationData> addAnnotations(List<Annotation> annotations,
           AnnotationSet set){
     List<AnnotationData> tags = new ArrayList<AnnotationData>();
     for(Annotation ann : annotations){
       AnnotationData aTag = new AnnotationDataImpl(set, ann);
       tags.add(aTag);
       annDataList.add(aTag);
       ann.addAnnotationListener(AnnotationListView.this);
     }
     try{
       //this will cause the selection to change (the actual selection contents
       //stay the same, but the row numbers may change)
       //we want to avoid circular notifications.
       localSelectionUpdating  = true;
       if(tableModel != null) tableModel.fireTableDataChanged();
     }finally{
       localSelectionUpdating = false;
     }
     return tags;
   }

   /**
    * Returns the tags for all the annotations currently displayed
    * @return a list of {@link AnnotationDataImpl}.
    */
   public List<AnnotationData> getAllAnnotations(){
     return annDataList;
   }

   @Override
  public void annotationUpdated(AnnotationEvent e){
     //update all occurrences of this annotation
    // if annotations tab has not been set to visible state
     // table will be null.
     if(table == null)	return;
     //save selection
     int[] selection = table.getSelectedRows();
     if(selection != null){
       localSelectionUpdating = true;
     }
     Annotation ann = (Annotation)e.getSource();
     if(tableModel != null){
       for(int i = 0; i < annDataList.size(); i++){
         AnnotationData aHandler = annDataList.get(i);
         if(aHandler.getAnnotation() == ann)tableModel.fireTableRowsUpdated(i, i);
       }
     }
     //restore selection
     table.clearSelection();
     if(selection != null){
       localSelectionUpdating = true;
       for(int i = 0; i < selection.length; i++){
         table.addRowSelectionInterval(selection[i], selection[i]);
       }
       localSelectionUpdating = false;
     }
   }


   /* (non-Javadoc)
    * @see gate.gui.docview.AbstractDocumentView#setSelectedAnnotations(java.util.List)
    */
   @Override
   public void setSelectedAnnotations(final List<AnnotationData> selectedAnnots) {
     //if the list of selected annotations differs from the current selection,
     //update the selection.
     //otherwise do nothing (to break infinite looping)

     //first get the local list of selected annotations
     int[] viewRows = table.getSelectedRows();
     List<AnnotationData> localSelAnns = new ArrayList<AnnotationData>();
     for (int viewRow : viewRows) {
       int modelRow = table.rowViewToModel(viewRow);
       if (modelRow >= 0) {
         localSelAnns.add(annDataList.get(modelRow));
       }
     }
     //now compare with the new value
     if(localSelAnns.size() == selectedAnnots.size()){
       //same size, we need to actually compare contents
       localSelAnns.removeAll(selectedAnnots);
       if(localSelAnns.isEmpty()){
         //lists are the same -> exit!
         return;
       }
     }
     //if we got this far, the selection lists were different
     //we need to change the selection from the UI thread.
     SwingUtilities.invokeLater(new Runnable(){
       @Override
      public void run(){
         try{
           //block upward events
           localSelectionUpdating = true;
           //update the local selection
           table.getSelectionModel().clearSelection();
           int rowToScrollTo = -1;
           for(AnnotationData aData : selectedAnnots){
             int modelRow = annDataList.indexOf(aData);
             if(modelRow != -1){
               int viewRow = table.rowModelToView(modelRow);
               table.getSelectionModel().addSelectionInterval(viewRow, viewRow);
               rowToScrollTo = viewRow;
             }
           }
           if(rowToScrollTo >= 0){
             table.scrollRectToVisible(table.getCellRect(rowToScrollTo, 0, true));
           }
         }finally{
           //re-enable upward events
           localSelectionUpdating = false;
         }
       }
     });
   }


   /**
    * Selects the annotation for the given tag.
    * @param tag the tag of the annotation to be selected.
    */
   public void selectAnnotationForTag(Object tag){
     int modelPosition = annDataList.indexOf(tag);
     table.getSelectionModel().clearSelection();
     if(modelPosition != -1){
       int tablePosition = table.rowModelToView(modelPosition);
       table.getSelectionModel().setSelectionInterval(tablePosition,
               tablePosition);
       table.scrollRectToVisible(table.getCellRect(tablePosition, 0, false));
     }
   }



   /* (non-Javadoc)
    * @see gate.gui.annedit.AnnotationEditorOwner#annotationChanged(gate.Annotation, gate.AnnotationSet, java.lang.String)
    */
   @Override
  public void annotationChanged(Annotation ann, AnnotationSet set,
           String oldType) {
     //do nothing
   }

   /* (non-Javadoc)
    * @see gate.gui.annedit.AnnotationEditorOwner#getNextAnnotation()
    */
   @Override
  public Annotation getNextAnnotation() {
     return null;
   }


   /* (non-Javadoc)
    * @see gate.gui.annedit.AnnotationEditorOwner#getPreviousAnnotation()
    */
   @Override
  public Annotation getPreviousAnnotation() {
     return null;
   }


   /* (non-Javadoc)
    * @see gate.gui.annedit.AnnotationEditorOwner#getTextComponent()
    */
   @Override
  public JTextComponent getTextComponent() {
     //get a pointer to the text view used to display
     //the selected annotations
     Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
     while(textView == null && centralViewsIter.hasNext()){
       DocumentView aView = centralViewsIter.next();
       if(aView instanceof TextualDocumentView)
         textView = (TextualDocumentView)aView;
     }
     return (JTextArea)((JScrollPane)textView.getGUI()).getViewport().getView();
   }

   /* (non-Javadoc)
    * @see gate.gui.annedit.AnnotationEditorOwner#selectAnnotation(gate.gui.annedit.AnnotationData)
    */
   @Override
  public void selectAnnotation(AnnotationData data) {
   }

   /* (non-Javadoc)
    * @see gate.gui.docview.AnnotationList#getRowForAnnotation(gate.gui.annedit.AnnotationData)
    */
   @Override
  public int getRowForAnnotation(AnnotationData data) {
     return annDataList.indexOf(data);
   }

  class AnnotationTableModel extends AbstractTableModel{
       @Override
      public int getRowCount(){
         return annDataList.size();
       }

       @Override
      public int getColumnCount(){
         return 6;
       }

       @Override
      public String getColumnName(int column){
         switch(column){
           case TYPE_COL: return "Type";
           case SET_COL: return "Set";
           case START_COL: return "Start";
           case END_COL: return "End";
           case ID_COL: return "Id";
           case FEATURES_COL: return "Features";
           default: return "?";
         }
       }

       @Override
      public Class<?> getColumnClass(int column){
         switch(column){
           case TYPE_COL: return String.class;
           case SET_COL: return String.class;
           case START_COL: return Long.class;
           case END_COL: return Long.class;
           case ID_COL: return Integer.class;
           case FEATURES_COL: return String.class;
           default: return String.class;
         }
       }

       @Override
      public boolean isCellEditable(int rowIndex, int columnIndex){
         return false;
       }

       @SuppressWarnings("unchecked")
      @Override
      public Object getValueAt(int row, int column){
         if(row >= annDataList.size()) return null;
         AnnotationData aData = annDataList.get(row);
         switch(column){
           case TYPE_COL: return aData.getAnnotation().getType();
           case SET_COL: return aData.getAnnotationSet().getName();
           case START_COL: return aData.getAnnotation().getStartNode().getOffset();
           case END_COL: return aData.getAnnotation().getEndNode().getOffset();
           case ID_COL: return aData.getAnnotation().getId();
           case FEATURES_COL:
             //sort the features by name
             FeatureMap features = aData.getAnnotation().getFeatures();
             @SuppressWarnings("rawtypes")
            List keyList = new ArrayList(features.keySet());
             Collections.sort(keyList);
             StringBuffer strBuf = new StringBuffer("{");
             Iterator<Object> keyIter = keyList.iterator();
             boolean first = true;
             while(keyIter.hasNext()){
               Object key = keyIter.next();
               Object value = features.get(key);
               if(first){
                 first = false;
               }else{
                 strBuf.append(", ");
               }
               strBuf.append(key.toString());
               strBuf.append("=");
               strBuf.append(value == null ? "[null]" : value.toString());
             }
             strBuf.append("}");
             return strBuf.toString();
           default: return "?";
         }
       }

     }


  protected class EditAnnotationAction extends AbstractAction{
       public EditAnnotationAction(AnnotationSet set, Annotation ann,
               AnnotationVisualResource editor){
         this.set = set;
         this.ann = ann;
         this.editor = editor;
         ResourceData rData =
           Gate.getCreoleRegister().get(editor.getClass().getName());
         if(rData != null){
           title = rData.getName();
           putValue(NAME, "Edit with " + title);
           putValue(SHORT_DESCRIPTION, rData.getComment());
         }
       }

       @Override
      public void actionPerformed(ActionEvent evt){
   //      editor.setTarget(set);
   //      editor.setAnnotation(ann);
         if(editor instanceof OwnedAnnotationEditor){
           //we need to unpin the editor so that it actually calculates the
           //position
           ((OwnedAnnotationEditor)editor).setPinnedMode(false);
           ((OwnedAnnotationEditor)editor).placeDialog(
                   ann.getStartNode().getOffset().intValue(),
                   ann.getEndNode().getOffset().intValue());
           //now we need to pin it so that it does not disappear automatically
           ((OwnedAnnotationEditor)editor).setPinnedMode(true);
           editor.editAnnotation(ann, set);
         }else{
           editor.editAnnotation(ann, set);
           JScrollPane scroller = new JScrollPane((Component)editor);
           JOptionPane optionPane = new JOptionPane(scroller,
                   JOptionPane.QUESTION_MESSAGE,
                   JOptionPane.OK_CANCEL_OPTION,
                   null, new String[]{"OK", "Cancel"});
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           scroller.setMaximumSize(new Dimension((int)(screenSize.width * .75),
                   (int)(screenSize.height * .75)));
           JDialog dialog = optionPane.createDialog(AnnotationListView.this.getGUI(),
                   title);
           dialog.setModal(true);
           dialog.setResizable(true);
           dialog.setVisible(true);
           try{
             if(optionPane.getValue().equals("OK")) editor.okAction();
             else editor.cancelAction();
           }catch(GateException ge){
             throw new GateRuntimeException(ge);
           }

         }

       }

       String title;
       Annotation ann;
       AnnotationSet set;
       AnnotationVisualResource editor;
     }

     protected XJTable table;
     protected AnnotationTableModel tableModel;
     protected JScrollPane scroller;

     /**
      * Stores the {@link AnnotationData} objects representing the annotations
      * displayed by this view.
      */
     protected List<AnnotationData> annDataList;

     /**
      * Flag used to mark the fact that the table selection is currently being
      * updated, to synchronise it with the global selection.
      * This is used to block update events being sent to the owner, while the
      * current selection is adjusted.
      */
     protected volatile boolean localSelectionUpdating = false;

     protected JPanel mainPanel;
     protected JLabel statusLabel;
     protected JTextField filterTextField;
     protected TextualDocumentView textView;
     /**
      * A map that stores instantiated annotations editors in order to avoid the
      * delay of building them at each request;
      */
     protected Map<String, AnnotationVisualResource> editorsCache;

     private static final int TYPE_COL = 0;
     private static final int SET_COL = 1;
     private static final int START_COL = 2;
     private static final int END_COL = 3;
     private static final int ID_COL = 4;
     private static final int FEATURES_COL = 5;

   }
