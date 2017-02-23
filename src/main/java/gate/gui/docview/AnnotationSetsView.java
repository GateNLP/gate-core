/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, Mar 23, 2004
 *
 *  $Id: AnnotationSetsView.java 20054 2017-02-02 06:44:12Z markagreenwood $
 */
package gate.gui.docview;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Factory;
import gate.Gate;
import gate.Resource;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.event.AnnotationSetEvent;
import gate.event.AnnotationSetListener;
import gate.event.DocumentEvent;
import gate.event.DocumentListener;
import gate.event.GateEvent;
import gate.gui.MainFrame;
import gate.gui.annedit.AnnotationData;
import gate.gui.annedit.AnnotationDataImpl;
import gate.gui.annedit.AnnotationEditorOwner;
import gate.gui.annedit.OwnedAnnotationEditor;
import gate.swing.ColorGenerator;
import gate.swing.XJTable;
import gate.util.Err;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.Strings;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;

/**
 * Display document annotation sets and types in a tree view like with a table.
 * Allow the selection of annotation type and modification of their color.
 */
@SuppressWarnings("serial")
public class AnnotationSetsView extends AbstractDocumentView 
		                        implements DocumentListener,
		                                   AnnotationSetListener, 
		                                   AnnotationEditorOwner{

  
  /* (non-Javadoc)
   * @see gate.gui.annedit.AnnotationEditorOwner#annotationTypeChanged(gate.Annotation, java.lang.String, java.lang.String)
   */
  @Override
  public void annotationChanged(Annotation ann, AnnotationSet set, 
          String oldType) {
    lastAnnotationType = ann.getType();
    //show new annotation type
    setTypeSelected(set.getName(), ann.getType(), true);
    //select new annotation
//    selectAnnotation(new AnnotationDataImpl(set, ann));
  }
  
  
  /**
   * changes the orientation of the annotation editor component only
   * 
   * @param orientation
   */
  public void changeOrientation(ComponentOrientation orientation) {
    currentOrientation = orientation;
    if(annotationEditor != null) {
      annotationEditor.changeOrientation(orientation);
    }
  }

  /**
   * Queues an an action for selecting the provided annotation
   */
  @Override
  public void selectAnnotation(final AnnotationData aData) {
    Runnable action = new Runnable(){
      @Override
      public void run(){
        List<AnnotationData> selAnns = Collections.singletonList(aData);
        owner.setSelectedAnnotations(selAnns);
      }
    };
    pendingEvents.offer(new PerformActionEvent(action));
    eventMinder.restart();
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
    return textPane;
  }

  
  /* (non-Javadoc)
   * @see gate.gui.annedit.AnnotationEditorOwner#getListComponent()
   * TODO: delete this obsolete method?
   */
  public AnnotationList getListComponent() {
    return listView;
  }

  public AnnotationSetsView(){
    setHandlers = new ArrayList<SetHandler>();
    tableRows = new ArrayList<Object>();
    visibleAnnotationTypes = new LinkedBlockingQueue<TypeSpec>();
    pendingEvents = new LinkedBlockingQueue<GateEvent>();
    eventMinder = new Timer(EVENTS_HANDLE_DELAY, 
            new HandleDocumentEventsAction());
    eventMinder.setRepeats(true);
    eventMinder.setCoalesce(true);    
  }

  /* (non-Javadoc)
   * @see gate.gui.docview.DocumentView#getType()
   */
  @Override
  public int getType() {
    return VERTICAL;
  }
  
  @Override
  protected void initGUI(){
    //get a pointer to the textual view used for highlights
    Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
    while(textView == null && centralViewsIter.hasNext()){
      DocumentView aView = centralViewsIter.next();
      if(aView instanceof TextualDocumentView) 
        textView = (TextualDocumentView)aView;
    }
    textPane = (JTextArea)((JScrollPane)textView.getGUI())
            .getViewport().getView();
    
    //get a pointer to the list view
    Iterator<DocumentView> horizontalViewsIter = owner.getHorizontalViews().iterator();
    while(listView == null && horizontalViewsIter.hasNext()){
      DocumentView aView = horizontalViewsIter.next();
      if(aView instanceof AnnotationListView) 
        listView = (AnnotationListView)aView;
    }
    //get a pointer to the stack view
    horizontalViewsIter = owner.getHorizontalViews().iterator();
    while(stackView == null && horizontalViewsIter.hasNext()){
      DocumentView aView = horizontalViewsIter.next();
      if(aView instanceof AnnotationStackView)
        stackView = (AnnotationStackView)aView;
    }
    mainTable = new XJTable();
    tableModel = new SetsTableModel();
    mainTable.setSortable(false);
    mainTable.setModel(tableModel);
    mainTable.setRowMargin(0);
    mainTable.getColumnModel().setColumnMargin(0);
    SetsTableCellRenderer cellRenderer = new SetsTableCellRenderer();
    mainTable.getColumnModel().getColumn(NAME_COL).setCellRenderer(cellRenderer);
    mainTable.getColumnModel().getColumn(SELECTED_COL).setCellRenderer(cellRenderer);
    SetsTableCellEditor cellEditor = new SetsTableCellEditor();
    mainTable.getColumnModel().getColumn(SELECTED_COL).setCellEditor(cellEditor);
    mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    mainTable.setColumnSelectionAllowed(false);
    mainTable.setRowSelectionAllowed(true);
    mainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //block autocreation of new columns from now on
    mainTable.setAutoCreateColumnsFromModel(false);
    mainTable.setTableHeader(null);
    mainTable.setShowGrid(false);
    mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    //the background colour seems to change somewhere when using the GTK+ 
    //look and feel on Linux, so we copy the value now and set it 
    Color tableBG = mainTable.getBackground();
    //make a copy of the value (as the reference gets changed somewhere)
    tableBG = new Color(tableBG.getRGB());
    mainTable.setBackground(tableBG);
    
    scroller = new JScrollPane(mainTable);
    scroller.getViewport().setOpaque(true);
    scroller.getViewport().setBackground(tableBG);    
    
    try {
      annotationEditor = createAnnotationEditor(textView, this);
    }
    catch(ResourceInstantiationException e) {
     //this should not really happen
      throw new GateRuntimeException(
              "Could not initialise the annotation editor!", e);
    }
    
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    
    constraints.gridy = 0;
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridwidth = 2;
    constraints.weighty = 1;
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    mainPanel.add(scroller, constraints);
    
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.weighty = 0;
    newSetNameTextField = new JTextField();
    mainPanel.add(newSetNameTextField, constraints);
    constraints.weightx = 0;
    newSetAction = new NewAnnotationSetAction();
    mainPanel.add(new JButton(newSetAction), constraints);

    populateUI();
    tableModel.fireTableDataChanged();


    eventMinder.start();    
    initListeners();
  }
  
  /**
   * Create the annotation editor (responsible for creating the window
   * used to edit individual annotations).
   * @throws ResourceInstantiationException if an error occurs
   */
  protected gate.gui.annedit.OwnedAnnotationEditor createAnnotationEditor(
          TextualDocumentView textView, AnnotationSetsView asView)
          throws ResourceInstantiationException {
    // find the last VR that implements the AnnotationEditor interface
    List<String> vrTypes = new ArrayList<String>(Gate.getCreoleRegister()
            .getPublicVrTypes());
    Collections.reverse(vrTypes);
    for(String aVrType : vrTypes) {
      ResourceData rData = Gate.getCreoleRegister().get(aVrType);
      try {
        Class<? extends Resource> resClass = rData.getResourceClass();
        if(OwnedAnnotationEditor.class.isAssignableFrom(resClass)) {
          OwnedAnnotationEditor newEditor = (OwnedAnnotationEditor)resClass
                  .newInstance();
          newEditor.setOwner(this);
          newEditor.init();
          if(currentOrientation != null) {
            newEditor.changeOrientation(currentOrientation);
          }
          return newEditor;
        }
      }
      catch(ClassNotFoundException cnfe) {
        // ignore
        Err.prln("Invalid CREOLE data:");
        cnfe.printStackTrace(Err.getPrintWriter());
      }
      catch(InstantiationException e) {
        e.printStackTrace();
      }
      catch(IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    // if we got this far, we couldn't find an editor
    Err.prln("Could not find any annotation editors. Editing annotations disabled.");
    return null;
  }
  
  protected void populateUI(){
    setHandlers.add(new SetHandler(document.getAnnotations()));
    List<String> setNames = document.getNamedAnnotationSets() == null ?
            new ArrayList<String>() :
            new ArrayList<String>(document.getNamedAnnotationSets().keySet());
    Collections.sort(setNames);
    Iterator<String> setsIter = setNames.iterator();
    while(setsIter.hasNext()){
      setHandlers.add(new SetHandler(document.
              getAnnotations(setsIter.next())));
    }
    tableRows.addAll(setHandlers);
  }
  
  @Override
  public Component getGUI(){
    return mainPanel;
  }

  /**
   * Get the saved colour for this annotation type or create a new one
   * and save it. The colours are saved in the user configuration file.
   * @param annotationType type to get a colour for
   * @return a colour
   */
  public static Color getColor(String annotationSet, String annotationType) {
    Map<String, String> colourMap = Gate.getUserConfig()
      .getMap(AnnotationSetsView.class.getName()+".colours");
    String colourValue = colourMap.get(annotationSet+"."+annotationType);
    if (colourValue == null) colourValue = colourMap.get(annotationType);

    Color colour;
    if (colourValue == null) {
      float components[] = colourGenerator.getNextColor().getComponents(null);
      colour = new Color(components[0], components[1], components[2], 0.5f);
      int rgb = colour.getRGB();
      int alpha = colour.getAlpha();
      int rgba = rgb | (alpha << 24);
      colourMap.put(annotationType, String.valueOf(rgba));
      Gate.getUserConfig().put(
        AnnotationSetsView.class.getName()+".colours", colourMap);
    } else {
      colour = new Color(Integer.parseInt(colourValue), true);
    }
    
    return colour;
  }
  
  protected void saveColor(String annotationSet, String annotationType, Color colour){
    Map<String, String> colourMap = Gate.getUserConfig()
      .getMap(AnnotationSetsView.class.getName()+".colours");
    int rgb = colour.getRGB();
    int alpha = colour.getAlpha();
    int rgba = rgb | (alpha << 24);
    
    String defaultValue = colourMap.get(annotationType);
    String newValue = String.valueOf(rgba);
    
    if (newValue.equals(defaultValue)) {
      colourMap.remove(annotationSet+"."+annotationType);
    }
    else {
      colourMap.put(annotationSet+"."+annotationType, newValue);
    }
    
    Gate.getUserConfig().put(
      AnnotationSetsView.class.getName()+".colours", colourMap);
  }

  /**
   * Save type or remove unselected type in the preferences.
   * @param setName set name to save/remove or null for the default set
   * @param typeName type name to save/remove
   * @param selected state of the selection
   */
  public void saveType(String setName, String typeName, boolean selected) {
    LinkedHashSet<String> typeList = Gate.getUserConfig().getSet(
      AnnotationSetsView.class.getName() + ".types");
    String prefix = (setName == null) ? "." : setName + ".";
    if (selected) {
      typeList.add(prefix+typeName);
    } else {
      typeList.remove(prefix+typeName);
    }
    Gate.getUserConfig().put(
      AnnotationSetsView.class.getName()+".types", typeList);
  }

  /**
   * Restore previously selected types from the preferences.
   */
  public void restoreSavedSelectedTypes() {
    LinkedHashSet<String> typeList = Gate.getUserConfig().getSet(
      AnnotationSetsView.class.getName() + ".types");
    for (SetHandler sHandler : setHandlers){
      String prefix = (sHandler.set.getName() == null) ?
        "." : sHandler.set.getName() + ".";
      for (TypeHandler tHandler : sHandler.typeHandlers) {
        if (typeList.contains(prefix + tHandler.name)) {
          tHandler.setSelected(true);
        }
      }
    }
  }

  /**
   * Enables or disables creation of the new annotation set.
   */
  public void setNewAnnSetCreationEnabled(boolean b) {
		newSetAction.setEnabled(b);
		newSetNameTextField.setEnabled(b);
	}

	/**
   * This method will be called whenever the view becomes active. Implementers 
   * should use this to add hooks (such as mouse listeners) to the other views
   * as required by their functionality. 
   */
  @Override
  protected void registerHooks(){
    textPane.addMouseListener(textMouseListener);
    textPane.addMouseMotionListener(textMouseListener);
    textPane.addPropertyChangeListener("highlighter", textChangeListener);
//    textPane.addAncestorListener(textAncestorListener);
    restoreSelectedTypes();
  }

  /**
   * This method will be called whenever this view becomes inactive. 
   * Implementers should use it to unregister whatever hooks they registered
   * in {@link #registerHooks()}.
   *
   */
  @Override
  protected void unregisterHooks(){
    textPane.removeMouseListener(textMouseListener);
    textPane.removeMouseMotionListener(textMouseListener);
    textPane.removePropertyChangeListener("highlighter", textChangeListener);
//    textPane.removeAncestorListener(textAncestorListener);
    storeSelectedTypes();
  }
  

  /**
   * Populates the {@link #visibleAnnotationTypes} structure based on the 
   * current selection
   *
   */
  protected void storeSelectedTypes(){
    visibleAnnotationTypes.clear(); // for security
    for(SetHandler sHandler:setHandlers){
      for(TypeHandler tHandler: sHandler.typeHandlers){
        if(tHandler.isSelected()){
          visibleAnnotationTypes.add(new TypeSpec(sHandler.set.getName(),
            tHandler.name));
          tHandler.setSelected(false);
        }
      }
    }
  }
  
  /**
   * Restores the selected types based on the state saved in the 
   * {@link #visibleAnnotationTypes} data structure.
   */
  protected void restoreSelectedTypes(){
    TypeSpec typeSpec;
    while((typeSpec = visibleAnnotationTypes.poll()) != null){
      TypeHandler typeHandler =
        getTypeHandler(typeSpec.setName, typeSpec.type);
      if (typeHandler != null) { // may have been deleted since
        typeHandler.setSelected(true);
      }
    }
  }

  protected void initListeners(){

    document.addDocumentListener(this);

    // popup menu to change the color, select, unselect
    // and delete annotation types
    mainTable.addMouseListener(new MouseAdapter(){
      @Override
      public void mouseClicked(MouseEvent evt){
        processMouseEvent(evt);
      }
      @Override
      public void mousePressed(MouseEvent evt){
        int row =  mainTable.rowAtPoint(evt.getPoint());
        if(evt.isPopupTrigger()
        && !mainTable.isRowSelected(row)) {
          // if right click outside the selection then reset selection
          mainTable.getSelectionModel().setSelectionInterval(row, row);
        }
        processMouseEvent(evt);
      }
      @Override
      public void mouseReleased(MouseEvent evt){
        processMouseEvent(evt);
      }
      protected void processMouseEvent(MouseEvent evt){
        int row = mainTable.rowAtPoint(evt.getPoint());
        int col = mainTable.columnAtPoint(evt.getPoint());

        if(row >= 0 && col == NAME_COL){
          Object handler = tableRows.get(row);
          if(evt.isPopupTrigger()){
            // popup menu
            JPopupMenu popup = new JPopupMenu();
            if(handler instanceof TypeHandler
            && mainTable.getSelectedRowCount() == 1){
              TypeHandler tHandler = (TypeHandler)handler;
              popup.add(tHandler.changeColourAction);
              popup.add(new DeleteSelectedAnnotationsAction("Delete"));
            } else if (mainTable.getSelectedRowCount() > 1
                    || handler instanceof SetHandler) {
              popup.add(new SetSelectedAnnotationsAction(true));
              popup.add(new SetSelectedAnnotationsAction(false));
              popup.add(new DeleteSelectedAnnotationsAction("Delete all"));
            }
            if (popup.getComponentCount() > 0) {
              popup.show(mainTable, evt.getX(), evt.getY());
            }
          } else if(evt.getClickCount() >= 2
            && evt.getID() == MouseEvent.MOUSE_CLICKED
            && handler instanceof TypeHandler){
              //double click
              TypeHandler tHandler = (TypeHandler)handler;
              tHandler.changeColourAction.actionPerformed(null);
          }
        }
      }
    });

    // Enter key to change the color of annotation type 
    // Space key to select/unselect annotation type
    // Left/Right keys to close/open an annotation set
    mainTable.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent e) {
        int row = mainTable.getSelectedRow();
        int col = mainTable.getSelectedColumn();
        if(row <= 0
        || mainTable.getSelectedRowCount() > 1) {
          return;
        }
        Object handler = tableRows.get(row);
        if (col == NAME_COL) {
          if(e.getKeyCode() == KeyEvent.VK_ENTER
            && handler instanceof TypeHandler){
              TypeHandler tHandler = (TypeHandler)handler;
              tHandler.changeColourAction.actionPerformed(null);
            e.consume();
          } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (handler instanceof TypeHandler){
              TypeHandler tHandler = (TypeHandler)handler;
              (new SetSelectedAnnotationsAction(!tHandler.selected))
                .actionPerformed(null);
            } else if (handler instanceof SetHandler) {
              SetHandler sHandler = (SetHandler)handler;
              boolean allUnselected = true;
              for (TypeHandler tHandler : sHandler.typeHandlers) {
                if (tHandler.selected) {
                  allUnselected = false;
                  break;
                }
              }
              (new SetSelectedAnnotationsAction(allUnselected))
                .actionPerformed(null);
            }
          } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (handler instanceof SetHandler) {
              ((SetHandler)handler).setExpanded(false);
              mainTable.setColumnSelectionInterval(col, col);
              mainTable.setRowSelectionInterval(row, row);
            }
            e.consume();

          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (handler instanceof SetHandler) {
              ((SetHandler)handler).setExpanded(true);
              mainTable.setColumnSelectionInterval(col, col);
              mainTable.setRowSelectionInterval(row, row);
            }
            e.consume();
          }
        }
      }
    });

    mouseStoppedMovingAction = new MouseStoppedMovingAction();
    mouseMovementTimer = new javax.swing.Timer(MOUSE_MOVEMENT_TIMER_DELAY, 
            mouseStoppedMovingAction);
    mouseMovementTimer.setRepeats(false);
    textMouseListener = new TextMouseListener();
    textChangeListener = new PropertyChangeListener(){
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getNewValue() != null){
          //we have a new highlighter
          //we need to re-highlight all selected annotations
          for(SetHandler sHandler : setHandlers){
            for(TypeHandler tHandler : sHandler.typeHandlers){
              if(tHandler.isSelected()){
                setTypeSelected(sHandler.set.getName(), tHandler.name, false);
                setTypeSelected(sHandler.set.getName(), tHandler.name, true);
              }
            }
          }
        }
      }
    };
    
    mainTable.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "deleteAll");
    mainTable.getInputMap()
      .put(KeyStroke.getKeyStroke("shift DELETE"), "deleteAll");
    mainTable.getActionMap().put("deleteAll",
      new DeleteSelectedAnnotationsAction("Delete"));
    newSetNameTextField.getInputMap().put(
      KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "newSet");
    newSetNameTextField.getActionMap().put("newSet", newSetAction);
    textPane.getInputMap()
      .put(KeyStroke.getKeyStroke("control E"), "edit annotation");
    textPane.getActionMap().put("edit annotation", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // use the same action as when the mouse stop over a selection
        // or annotation but this time for a keyboard shortcut
        mouseStoppedMovingAction.setTextLocation(textPane.getCaretPosition());
        mouseStoppedMovingAction.actionPerformed(null);
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          annotationEditor.setPinnedMode(true);
        }});
      }});

    // skip first column when tabbing
    InputMap im =
      mainTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    KeyStroke tab = KeyStroke.getKeyStroke("TAB");
    final Action oldTabAction = mainTable.getActionMap().get(im.get(tab));
    Action tabAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        oldTabAction.actionPerformed(e);
        JTable table = (JTable) e.getSource();
        if(table.getSelectedColumn() == SELECTED_COL) {
          oldTabAction.actionPerformed(e);
        }
      }
    };
    mainTable.getActionMap().put(im.get(tab), tabAction);
    KeyStroke shiftTab = KeyStroke.getKeyStroke("shift TAB");
    final Action oldShiftTabAction =
      mainTable.getActionMap().get(im.get(shiftTab));
    Action shiftTabAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        oldShiftTabAction.actionPerformed(e);
        JTable table = (JTable) e.getSource();
        if(table.getSelectedColumn() == SELECTED_COL) {
          oldShiftTabAction.actionPerformed(e);
        }
      }
    };
    mainTable.getActionMap().put(im.get(shiftTab), shiftTabAction);
  }
    
	
  /* (non-Javadoc)
   * @see gate.Resource#cleanup()
   */
  @Override
  public void cleanup() {
    document.removeDocumentListener(this);
    for(SetHandler sHandler : setHandlers){
      sHandler.set.removeAnnotationSetListener(AnnotationSetsView.this);
    }    
    eventMinder.stop();
    pendingEvents.clear();  
    super.cleanup();
    document = null;
  }
  
  @Override
  public void annotationSetAdded(final DocumentEvent e) {
    pendingEvents.offer(e);
    eventMinder.restart();
  }//public void annotationSetAdded(DocumentEvent e) 
  
  @Override
  public void annotationSetRemoved(final DocumentEvent e) {
    pendingEvents.offer(e);
    eventMinder.restart();
  }//public void annotationSetRemoved(DocumentEvent e) 
  
  /**Called when the content of the document has changed through an edit 
   * operation.
   */
  @Override
  public void contentEdited(DocumentEvent e){
    //go through all the type handlers and propagate the event
    Iterator<SetHandler> setIter = setHandlers.iterator();
    while(setIter.hasNext()){
      SetHandler sHandler = setIter.next();
      Iterator<TypeHandler> typeIter = sHandler.typeHandlers.iterator();
      while(typeIter.hasNext()){
        TypeHandler tHandler = typeIter.next();
        if(tHandler.isSelected()) 
          tHandler.repairHighlights(e.getEditStart().intValue(), 
                  e.getEditEnd().intValue());
      }
    }
  }
  
  
  @Override
  public void annotationAdded(final AnnotationSetEvent e) {
    pendingEvents.offer(e);
    eventMinder.restart();
  }
  
  @Override
  public void annotationRemoved(final AnnotationSetEvent e) {
    pendingEvents.offer(e);
    eventMinder.restart();
  }
  
  /**
   * Get an annotation set handler in this annotation set view.
   * @param name name of the annotation set or null for the default set
   * @return the annotation set handler
   */
  protected SetHandler getSetHandler(String name){
    for (SetHandler setHandler : setHandlers) {
      if (name == null) { // default annotation set
        if (setHandler.set.getName() == null) return setHandler;
      } else {
        if (name.equals(setHandler.set.getName())) return setHandler;
      }
    }
    // set handler not found
    return null;
  }
  
  /**
   * Get an annotation type handler in this annotation set view.
   * @param set name of the annotation set or null for the default set
   * @param type type of the annotation
   * @return the type handler
   */
  public TypeHandler getTypeHandler(String set, String type){
    for(TypeHandler typeHandler : getSetHandler(set).typeHandlers){
      if(typeHandler.name.equals(type)) {
        return typeHandler;
      }
    }
    // type handler not found
    return null;
  }

  /**
   * Un/select an annotation type in this annotation set view
   * and indirectly highlight it in the document view.
   * @param setName name of the annotation set or null for the default set
   * @param typeName type of the annotation
   * @param selected state of the selection
   */
  public void setTypeSelected(final String setName, 
                              final String typeName, 
                              final boolean selected){
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        TypeHandler tHandler = getTypeHandler(setName, typeName);
        if(tHandler != null){
          tHandler.setSelected(selected);
          int row = tableRows.indexOf(tHandler);
          tableModel.fireTableRowsUpdated(row, row);
          mainTable.getSelectionModel().setSelectionInterval(row, row);
        }else{
          //type handler not created yet
          visibleAnnotationTypes.add(new TypeSpec(setName, typeName));  
        }
      }
    });
  }
  
 
  
  /* (non-Javadoc)
   * @see gate.gui.docview.AbstractDocumentView#setSelectedAnnotations(java.util.List)
   */
  @Override
  public void setSelectedAnnotations(List<AnnotationData> selectedAnnots) {
    if(annotationEditor != null && annotationEditor.isActive()){
      // editor active - let's update it.
      // For annotation editing purposes, only a single selected annotation 
      // makes sense. Anything else is equivalent to no selection.
      PerformActionEvent actionEvent = null;
      if(selectedAnnots.size() == 1){
        final AnnotationData aData = selectedAnnots.get(0);
        //queue the select action to the events minder
        actionEvent = new PerformActionEvent(new Runnable(){
          @Override
          public void run(){
            //select the annotation for editing, if editing enabled
            if(annotationEditor.getAnnotationSetCurrentlyEdited() != 
                  aData.getAnnotationSet() ||
               annotationEditor.getAnnotationCurrentlyEdited() != 
                   aData.getAnnotation()){
              annotationEditor.editAnnotation(aData.getAnnotation(),
                      aData.getAnnotationSet());
            }
          }
        });
      } else {
        actionEvent = new PerformActionEvent(new Runnable(){
          @Override
          public void run(){
            // un-select the edited annotation
            annotationEditor.editAnnotation(null, 
                annotationEditor.getAnnotationSetCurrentlyEdited());
          }
        });
      }
      pendingEvents.offer(actionEvent);
      eventMinder.restart();      
    }
  }

  /**
   * Sets a particular annotation as selected. If the list view is visible
   * and active, it makes sure that the same annotation is selected there.
   * If the annotation editor exists and is active, it switches it to this 
   * current annotation.
   * @param ann the annotation
   * @param annSet the parent set
   */
  public void selectAnnotation(final Annotation ann, 
          final AnnotationSet annSet){
    selectAnnotation(new AnnotationDataImpl(annSet, ann));
  }
  
  protected class SetsTableModel extends AbstractTableModel{
    @Override
    public int getRowCount(){
      return tableRows.size();
//      //we have at least one row per set
//      int rows = setHandlers.size();
//      //expanded sets add rows
//      for(int i =0; i < setHandlers.size(); i++){
//        SetHandler sHandler = (SetHandler)setHandlers.get(i);
//        rows += sHandler.expanded ? sHandler.set.getAllTypes().size() : 0;
//      }
//      return rows;
    }
    
    @Override
    public int getColumnCount(){
      return 2;
    }
    
    @Override
    public Object getValueAt(int row, int column){
      Object value = tableRows.get(row);
      switch(column){
        case NAME_COL:
          return value;
        case SELECTED_COL:
          if(value instanceof SetHandler)
            return new Boolean(((SetHandler)value).isExpanded());
          if(value instanceof TypeHandler) 
            return new Boolean(((TypeHandler)value).isSelected());
          return null;
        default:
          return null;
      }
//      
//      int currentRow = 0;
//      Iterator handlerIter = setHandlers.iterator();
//      SetHandler sHandler = (SetHandler)handlerIter.next();
//      
//      while(currentRow < row){
//        if(sHandler.expanded){
//          if(sHandler.typeHandlers.size() + currentRow >= row){
//            //we want a row in current set
//             return sHandler.typeHandlers.get(row - currentRow);
//          }else{
//            currentRow += sHandler.typeHandlers.size();
//            sHandler = (SetHandler)handlerIter.next();
//          }
//        }else{
//          //just go to next handler
//          currentRow++;
//          sHandler = (SetHandler)handlerIter.next();
//        }
//        if(currentRow == row) return sHandler;
//      }
//      if(currentRow == row) return sHandler;
//System.out.println("BUG! row: " + row + " col: " + column);      
//      return null;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
      Object value = tableRows.get(rowIndex);
      switch(columnIndex){
        case NAME_COL: return false;
        case SELECTED_COL:
          if(value instanceof SetHandler)
            return ((SetHandler)value).typeHandlers.size() > 0;
          if(value instanceof TypeHandler) return true; 
      }
      return columnIndex == SELECTED_COL;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
      Object receiver = tableRows.get(rowIndex);
      switch(columnIndex){
        case SELECTED_COL:
          if(receiver instanceof SetHandler){
            ((SetHandler)receiver).setExpanded(((Boolean)aValue).booleanValue());
          }else if(receiver instanceof TypeHandler){
            ((TypeHandler)receiver).setSelected(((Boolean)aValue).booleanValue());
          }
          
          break;
        default:
          break;
      }
    }
  }//public Object getValueAt(int row, int column)
  
  protected class SetsTableCellRenderer implements TableCellRenderer{
    public SetsTableCellRenderer(){
      typeLabel = new JLabel(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
      };
      typeLabel.setOpaque(true);
      typeLabel.setBorder(BorderFactory.createCompoundBorder(
              BorderFactory.createMatteBorder(0, 5, 0, 0,
                      mainTable.getBackground()),
              BorderFactory.createEmptyBorder(0, 5, 0, 5)));
//      typeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

      
      setLabel = new JLabel(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
      };
      setLabel.setOpaque(true);
      setLabel.setFont(setLabel.getFont().deriveFont(Font.BOLD));
      setLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
      

      typeChk = new JCheckBox(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
      };
      typeChk.setOpaque(true);
//      typeChk.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

      setChk = new JCheckBox(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
      };
      setChk.setSelectedIcon(MainFrame.getIcon("expanded"));
      setChk.setIcon(MainFrame.getIcon("closed"));
      setChk.setMaximumSize(setChk.getMinimumSize());
      setChk.setOpaque(true);
      
      normalBorder = BorderFactory.createLineBorder(
              mainTable.getBackground(), 2);
      selectedBorder = BorderFactory.createLineBorder(
              mainTable.getSelectionBackground(), 2);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table,
																		               Object value,
																			             boolean isSelected,
																			             boolean hasFocus,
																			             int row,
																			             int column){

      value = tableRows.get(row);
      if(value instanceof SetHandler){
        SetHandler sHandler = (SetHandler)value;
        switch(column){
          case NAME_COL:
            setLabel.setText(sHandler.set.getName());
            setLabel.setBackground(isSelected ?
                                   table.getSelectionBackground() :
                                   table.getBackground());
            return setLabel;
          case SELECTED_COL:
            setChk.setSelected(sHandler.isExpanded());
            setChk.setBackground(isSelected ?
                    		         table.getSelectionBackground() :
                    		         table.getBackground());
            setChk.setEnabled(sHandler.typeHandlers.size() > 0);            
            return setChk;
        }
      }else if(value instanceof TypeHandler){
        TypeHandler tHandler = (TypeHandler)value;
        switch(column){
          case NAME_COL:
            typeLabel.setBackground(tHandler.colour);
            typeLabel.setText(tHandler.name);
            typeLabel.setBorder(isSelected ? selectedBorder : normalBorder);
            return typeLabel;
          case SELECTED_COL:
            typeChk.setBackground(isSelected ?
       		         table.getSelectionBackground() :
        		       table.getBackground());
            typeChk.setSelected(tHandler.isSelected());
            return typeChk;
        }
      }
      typeLabel.setText("?");
    	return typeLabel;
      //bugcheck!
    }
    
    protected JLabel typeLabel;
    protected JLabel setLabel;
    protected JCheckBox setChk;
    protected JCheckBox typeChk;
    protected Border selectedBorder;
    protected Border normalBorder;
  }
  
  protected class SetsTableCellEditor extends AbstractCellEditor
                                      implements TableCellEditor{
    public SetsTableCellEditor(){
      setChk = new JCheckBox();
      setChk.setSelectedIcon(MainFrame.getIcon("expanded"));
      setChk.setIcon(MainFrame.getIcon("closed"));
//      setChk.setMaximumSize(setChk.getMinimumSize());
      setChk.setOpaque(true);
      setChk.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){
          fireEditingStopped();
        }
      });
      typeChk = new JCheckBox();
      typeChk.setOpaque(false);
//      typeChk.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
      typeChk.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){
          fireEditingStopped();
        }
      });
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column){
      value = tableRows.get(row);
      if(value instanceof SetHandler){
        SetHandler sHandler = (SetHandler)value;
        switch(column){
          case NAME_COL: return null;
          case SELECTED_COL:
            setChk.setSelected(sHandler.isExpanded());
            setChk.setEnabled(sHandler.typeHandlers.size() > 0);
            setChk.setBackground(isSelected ?
       		         	             table.getSelectionBackground() :
       		         	             table.getBackground());
            currentChk = setChk;
            return setChk;
        }
      }else if(value instanceof TypeHandler){
        TypeHandler tHandler = (TypeHandler)value;
        switch(column){
          case NAME_COL: return null;
          case SELECTED_COL:
//            typeChk.setBackground(tHandler.colour);
            typeChk.setSelected(tHandler.isSelected());
            currentChk = typeChk;
            return typeChk;
        }
      }
      return null;
    }
    
    @Override
    public boolean stopCellEditing(){
      return true;
    }
    
    @Override
    public Object getCellEditorValue(){
      return new Boolean(currentChk.isSelected());
    }
    
    @Override
    public boolean shouldSelectCell(EventObject anEvent){
      return false;
    }
    
    @Override
    public boolean isCellEditable(EventObject anEvent){
      return true;
    }
    
    JCheckBox currentChk;
    JCheckBox setChk;
    JCheckBox typeChk;
  }
  
  
  /**
   * Stores the data related to an annotation set
   */
  public class SetHandler{
    SetHandler(AnnotationSet set){
      this.set = set;
      typeHandlers = new ArrayList<TypeHandler>();
      typeHandlersByType = new HashMap<String,TypeHandler>();
      List<String> typeNames = new ArrayList<String>(set.getAllTypes());
      Collections.sort(typeNames);
      Iterator<String> typIter = typeNames.iterator();
      while(typIter.hasNext()){
        String name = typIter.next();
        TypeHandler tHandler = new TypeHandler(this, name);
        tHandler.annotationCount = set.get(name).size();
        typeHandlers.add(tHandler);
        typeHandlersByType.put(name, tHandler);
      }
      set.addAnnotationSetListener(AnnotationSetsView.this);
    }
    
    public void cleanup(){
      set.removeAnnotationSetListener(AnnotationSetsView.this);
      typeHandlers.clear();
    }
    
    /**
     * Notifies this set handler that a new type of annotations has been created
     * @param type the new type of annotations
     * @return the new TypeHandler created as a result
     */
    public TypeHandler newType(String type){
      //create a new TypeHandler
      TypeHandler tHandler = new TypeHandler(this, type);
      //add it to the list at the right position
      int pos = 0;
      for(;
          pos < typeHandlers.size() &&
          typeHandlers.get(pos).name.compareTo(type) <= 0;
          pos++);
      typeHandlers.add(pos, tHandler);
      typeHandlersByType.put(type, tHandler);
      //preserve table selection
      int row = mainTable.getSelectedRow();
      int setRow = tableRows.indexOf(this);
      if(typeHandlers.size() == 1) 
        tableModel.fireTableRowsUpdated(setRow, setRow);
      if(expanded){
        tableRows.add(setRow + pos + 1, tHandler);
        tableModel.fireTableRowsInserted(setRow + pos + 1,
              setRow + pos + 1);
      }
      //restore selection if any
      if(row != -1) mainTable.getSelectionModel().setSelectionInterval(row, row);
      //select the newly created type if previously requested
      TypeSpec typeSpec = new TypeSpec(set.getName(), type);
      if(visibleAnnotationTypes.remove(typeSpec)){
        tHandler.setSelected(true);
      }
      return tHandler;
    }
    
    public void removeType(TypeHandler tHandler){
      int setRow = tableRows.indexOf(this);
      int pos = typeHandlers.indexOf(tHandler);
      if(setRow != -1 && pos != -1){
        typeHandlers.remove(pos);
        typeHandlersByType.remove(tHandler.name);
        //preserve table selection
        int row = mainTable.getSelectedRow();
        if(expanded){
          tableRows.remove(setRow + pos + 1);
          tableModel.fireTableRowsDeleted(setRow + pos + 1, setRow + pos + 1);
        }
        //restore selection if any
        if(row != -1){
          if(mainTable.getRowCount() <= row){
            row = mainTable.getRowCount() -1;
          }
          mainTable.getSelectionModel().setSelectionInterval(row, row);        }
      }
    }
    
    public void removeType(String type){
      removeType(typeHandlersByType.get(type));
    }

    public TypeHandler getTypeHandler(String type){
      return typeHandlersByType.get(type);
    }
    
    public void setExpanded(boolean expanded){
      if(this.expanded == expanded) return;
      this.expanded = expanded;
      int myPosition = tableRows.indexOf(this);
      if(expanded){
        //expand
        tableRows.addAll(myPosition + 1, typeHandlers);
        tableModel.fireTableRowsInserted(myPosition + 1, 
                 												 myPosition + 1 + typeHandlers.size());
      }else{
        //collapse
        for(int i = 0; i < typeHandlers.size(); i++){
          tableRows.remove(myPosition + 1);
        }
        tableModel.fireTableRowsDeleted(myPosition + 1, 
								                        myPosition + 1 + typeHandlers.size());
      }
      tableModel.fireTableRowsUpdated(myPosition, myPosition);
    }
    
    public boolean isExpanded(){
      return expanded;
    }
    
    
    AnnotationSet set;
    List<TypeHandler> typeHandlers;
    Map<String, TypeHandler> typeHandlersByType;
    private boolean expanded = false;
  }
  
  public class TypeHandler{
    TypeHandler (SetHandler setHandler, String name){
      this.setHandler = setHandler;
      this.name = name;
      colour = getColor(setHandler.set.getName(),name);
      hghltTagsForAnnId = new HashMap<Integer, TextualDocumentView.HighlightData>();
      annListTagsForAnn = new HashMap<Integer,AnnotationData>();
      changeColourAction = new ChangeColourAction();
      annotationCount = 0;
    }
    
    /**
     * @return the colour
     */
    public Color getColour() {
      return colour;
    }

    public void setColour(Color colour){
      if(this.colour.equals(colour)) return;
      this.colour = colour;
      saveColor(setHandler.set.getName(),name, colour);
      if(isSelected()){
        //redraw the highlights
        //hide highlights
        textView.removeHighlights(hghltTagsForAnnId.values());
        hghltTagsForAnnId.clear();
        //show highlights
        List<Annotation> annots = new ArrayList<Annotation>(
                setHandler.set.get(name));
        List<AnnotationData>aDataList = new ArrayList<AnnotationData>();
        for(Annotation ann : annots){
          aDataList.add(new AnnotationDataImpl(setHandler.set, ann));
        }
        List<TextualDocumentView.HighlightData> tags = textView.addHighlights(aDataList, TypeHandler.this.colour);
        for(int i = 0; i < annots.size(); i++){
          hghltTagsForAnnId.put(annots.get(i).getId(), tags.get(i));
        }
      }
      //update the table display
      int row = tableRows.indexOf(this);
      if(row >= 0) tableModel.fireTableRowsUpdated(row, row);
      
      if (stackView.isActive()) stackView.updateStackView();
    }
    
    public void setSelected(boolean selected){
      if(this.selected == selected) return;
      this.selected = selected;
      final List<Annotation> annots = new ArrayList<Annotation>(setHandler.set.get(name));
      if(selected){
        //make sure set is expanded
        setHandler.setExpanded(true);
        //add to the list view
        annListTagsForAnn.clear();
        List<AnnotationData> listTags = 
            listView.addAnnotations(annots, setHandler.set);
        for(AnnotationData aData: listTags)
          annListTagsForAnn.put(aData.getAnnotation().getId(), aData);
        //show highlights
        hghltTagsForAnnId.clear();
//        List tags = textView.addHighlights(annots, setHandler.set, colour);
        List<TextualDocumentView.HighlightData> tags = textView.addHighlights(listTags, colour);
        for(int i = 0; i < annots.size(); i++){
          hghltTagsForAnnId.put(annots.get(i).getId(), tags.get(i));
        }
      }else{
        //hide highlights
        try{
          listView.removeAnnotations(annListTagsForAnn.values());
          textView.removeHighlights(hghltTagsForAnnId.values());
        }finally{
          hghltTagsForAnnId.clear();
          annListTagsForAnn.clear();
        }
      }
      //update the table display
      int row = tableRows.indexOf(this);
      tableModel.fireTableRowsUpdated(row, row);
      saveType(setHandler.set.getName(), name, selected);
      //update the stack view
      stackView.updateStackView();
    }
    
    public boolean isSelected(){
      return selected;
    }
    
    /**
     * Notifies this type handler that a new annotation was created of the 
     * right type
     * @param ann
     */
    public void annotationAdded(final Annotation ann){
      annotationCount++;
      if(selected){
        //add new highlight
        if(!hghltTagsForAnnId.containsKey(ann.getId())) 
            hghltTagsForAnnId.put(ann.getId(), 
                    textView.addHighlight(
                            new AnnotationDataImpl(setHandler.set, ann),
                            colour));
        if(!annListTagsForAnn.containsKey(ann.getId())){
          annListTagsForAnn.put(ann.getId(), 
              listView.addAnnotation(ann, setHandler.set));
        }
        //update the stack view
        stackView.updateStackView();
      }
    }
    
    /**
     * Notifies this type handler that an annotation has been removed
     * @param ann the removed annotation
     */
    public void annotationRemoved(Annotation ann){
      annotationCount--;
      if(selected){
        //single annotation removal
        TextualDocumentView.HighlightData tag = hghltTagsForAnnId.remove(ann.getId());
        if(tag != null) textView.removeHighlight(tag);
        AnnotationData listTag = annListTagsForAnn.remove(ann.getId());
        if(tag != null) listView.removeAnnotation(listTag);
        //update the stack view
        stackView.updateStackView();
      }
      //if this was the last annotation of this type then the handler is no
      //longer required
      if(annotationCount == 0){
        setHandler.removeType(TypeHandler.this);
      }      
    }
    
    protected void repairHighlights(int start, int end){
      //map from tag to annotation
      List<Object> tags = new ArrayList<Object>(hghltTagsForAnnId.size());
      List<Annotation> annots = new ArrayList<Annotation>(hghltTagsForAnnId.size());
      Iterator<Integer> annIter = hghltTagsForAnnId.keySet().iterator();
      while(annIter.hasNext()){
        Annotation ann = setHandler.set.get(annIter.next());
        // editing the text sometimes leads to annotations being deleted 
        if(ann == null) continue;
        int annStart = ann.getStartNode().getOffset().intValue();
        int annEnd = ann.getEndNode().getOffset().intValue();
        if((annStart <= start && start <= annEnd) ||
           (start <= annStart && annStart <= end)){
          if(!hghltTagsForAnnId.containsKey(ann.getId())){
            System.out.println("Error!!!");
          }
          tags.add(hghltTagsForAnnId.get(ann.getId()));
          annots.add(ann);
        }
      }
      for(int i = 0; i < tags.size(); i++){
        Object tag = tags.get(i);
        Annotation ann = annots.get(i);
        try{
          textView.moveHighlight(tag, 
                  ann.getStartNode().getOffset().intValue(), 
                  ann.getEndNode().getOffset().intValue());
        }catch(BadLocationException ble){
          //this should never happen as the offsets come from an annotation
        }
      }
    }
    
    
    protected class ChangeColourAction extends AbstractAction{
      public ChangeColourAction(){
        super("Change colour");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ENTER"));
      }
      
      @Override
      public void actionPerformed(ActionEvent evt){
        Color col = JColorChooser.showDialog(mainTable, 
                "Select colour for \"" + name + "\"",
                colour);
        if(col != null){
          Color colAlpha = new Color(col.getRed(), col.getGreen(),
                  col.getBlue(), 128);
          setColour(colAlpha);
        }
      }
    }
    
    ChangeColourAction changeColourAction;
    boolean selected;
    /**
     * Map from annotation ID (which is immutable) to highlight tag
     */
    Map<Integer, TextualDocumentView.HighlightData> hghltTagsForAnnId;

    /**
     * Map from annotation ID (which is immutable) to AnnotationListView tag
     */
    Map<Integer, AnnotationData> annListTagsForAnn;
    
    String name;
    SetHandler setHandler;
    Color colour;
    int annotationCount;
  }
  
  /**
   * A class storing the identifying information for an annotation type (i.e.
   * the set name and the type).
   * @author Valentin Tablan (valyt)
   *
   */
  private static class TypeSpec{
    private String setName;
    
    private String type;

    public TypeSpec(String setName, String type) {
      super();
      this.setName = setName;
      this.type = type;
    }

    @Override
    public int hashCode() {
      final int PRIME = 31;
      int result = 1;
      result = PRIME * result + ((setName == null) ? 0 : setName.hashCode());
      result = PRIME * result + ((type == null) ? 0 : type.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null) return false;
      if(getClass() != obj.getClass()) return false;
      final TypeSpec other = (TypeSpec)obj;
      if(setName == null) {
        if(other.setName != null) return false;
      }
      else if(!setName.equals(other.setName)) return false;
      if(type == null) {
        if(other.type != null) return false;
      }
      else if(!type.equals(other.type)) return false;
      return true;
    }
  }
  

  /**
   * A mouse listener used for events in the text view. 
   */
  protected class TextMouseListener implements MouseInputListener{    
    @Override
    public void mouseDragged(MouseEvent e){
      //do not create annotations while dragging
      mouseMovementTimer.stop();
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
      //this triggers select annotation leading to edit annotation or new 
      //annotation actions
      //ignore movement if CTRL pressed or dragging
      int modEx = e.getModifiersEx();
      if((modEx & MouseEvent.CTRL_DOWN_MASK) != 0){
        mouseMovementTimer.stop();
        return;
      }
      if((modEx & MouseEvent.BUTTON1_DOWN_MASK) != 0){
        mouseMovementTimer.stop();
        return;
      }
      //check the text location is real
      int textLocation = textPane.viewToModel(e.getPoint());
      try {
        Rectangle viewLocation = textPane.modelToView(textLocation);
        //expand the rectangle a bit
        int error = 10;
        viewLocation = new Rectangle(viewLocation.x - error, 
                                     viewLocation.y - error,
                                     viewLocation.width + 2*error, 
                                     viewLocation.height + 2*error);
        if(viewLocation.contains(e.getPoint())){
          mouseStoppedMovingAction.setTextLocation(textLocation);
        }else{
          mouseStoppedMovingAction.setTextLocation(-1);
        }
      }
      catch(BadLocationException e1) {
        //this should not happen, as the text location comes from the text view
        //if it does. we'll just ignore it.
      }finally{
        mouseMovementTimer.restart();
      }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
    }
    
    @Override
    public void mousePressed(MouseEvent e){
      
    }
    @Override
    public void mouseReleased(MouseEvent e){
      
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
      
    }
    
    @Override
    public void mouseExited(MouseEvent e){
      mouseMovementTimer.stop();
    }
  }//protected class TextMouseListener implements MouseInputListener
  
  
    
  protected class NewAnnotationSetAction extends AbstractAction{
    public NewAnnotationSetAction(){
      super("New");
      putValue(SHORT_DESCRIPTION, "Creates a new annotation set");
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
      String name = newSetNameTextField.getText();
      newSetNameTextField.setText("");
      if(name != null && name.length() > 0){
        AnnotationSet set = document.getAnnotations(name);
        //select the newly added set
        
        int row = -1;
        for(int i = 0; i < tableRows.size() && row < 0; i++){
          if(tableRows.get(i) instanceof SetHandler &&
             ((SetHandler)tableRows.get(i)).set == set) row = i;
        }
        if(row >= 0) mainTable.getSelectionModel().setSelectionInterval(row, row);
      }
    }
  }

  protected class NewAnnotationAction extends AbstractAction{
    public NewAnnotationAction(String selection){
      super("Create new annotation");
      putValue(SHORT_DESCRIPTION, "Creates a new annotation from the" +
        " selection: [" + Strings.crop(selection, 30) + "]");
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      if(annotationEditor == null) return;
      int start = textPane.getSelectionStart();
      int end = textPane.getSelectionEnd();
      if(start != end){
        textPane.setSelectionStart(start);
        textPane.setSelectionEnd(start);
        //create a new annotation
        //find the selected set
        int row = mainTable.getSelectedRow();
        //select the default annotation set if none selected
        if(row < 0) row = 0;
        //find the set handler
        while(!(tableRows.get(row) instanceof SetHandler)) row --;
        AnnotationSet set = ((SetHandler)tableRows.get(row)).set;
        try{
	        Integer annId =  set.add(new Long(start), new Long(end), 
	                lastAnnotationType, Factory.newFeatureMap());
	        Annotation ann = set.get(annId);
          //select the annotation set in the tree view and expand it
          //to avoid the next annotation to be always in the default set
          if (tableRows.get(row) instanceof SetHandler) {
            ((SetHandler)tableRows.get(row)).setExpanded(true);
            mainTable.getSelectionModel().setSelectionInterval(row, row);
          }
	        //make sure new annotation is visible
	        setTypeSelected(set.getName(), ann.getType(), true);
	        //edit the new annotation
	        pendingEvents.offer(new PerformActionEvent(
	                new EditAnnotationAction(new AnnotationDataImpl(set, ann))));
	        eventMinder.restart();
        }catch(InvalidOffsetException ioe){
          //this should never happen
          throw new GateRuntimeException(ioe);
        }
      }
    }
  }
    
  /**
   * A fake GATE Event used to wrap a {@link Runnable} value. This is used for
   * queueing actions to the document UI update timer.  
   */
  private class PerformActionEvent extends GateEvent{
    public PerformActionEvent(Runnable runnable){
      super(AnnotationSetsView.this, 0);
      this.runnable = runnable;
      this.action = null;
    }

    public PerformActionEvent(Action action){
      super(AnnotationSetsView.this, 0);
      this.runnable = null;
      this.action = action;
    }
    
    /**
     * Runs the action (or runnable) enclosed by this event. 
     */
    public void run(){
      if(runnable != null){
        runnable.run();
      }else if(action != null){
        action.actionPerformed(null);
      }
    }
    
    private Action action;
    
    private Runnable runnable;
  }
  
  protected class HandleDocumentEventsAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent ev) {
      //see if we need to try again to rebuild from scratch
      if(uiDirty){
        //a previous call to rebuild has failed; try again
        rebuildDisplay();
        return;
      }
      //if too many individual events, then rebuild UI from scratch as it's 
      //faster.
      if(pendingEvents.size() > MAX_EVENTS){
        rebuildDisplay();
        return;
      }
      //process the individual events
      while(!pendingEvents.isEmpty()){
        GateEvent event = pendingEvents.remove();
        if(event instanceof DocumentEvent){
          DocumentEvent e = (DocumentEvent)event;
          if(event.getType() == DocumentEvent.ANNOTATION_SET_ADDED){
              String newSetName = e.getAnnotationSetName();
              SetHandler sHandler = new SetHandler(document.getAnnotations(newSetName));
              //find the right location for the new set
              //this is a named set and the first one is always the default one
              int i = 0;
              if(newSetName != null){
                for(i = 1;
                    i < setHandlers.size() && 
                    setHandlers.get(i).set.
                    getName().compareTo(newSetName) <= 0;
                    i++);
              }
              setHandlers.add(i, sHandler);
              //update the tableRows list
              int j = 0;
              if(i > 0){
                SetHandler previousHandler = setHandlers.get(i -1);
                //find the index for the previous handler - which is guaranteed to exist
                for(; tableRows.get(j) != previousHandler; j++);
                if(previousHandler.isExpanded()){
                  j += previousHandler.typeHandlers.size();
                }
                j++;
              }
              tableRows.add(j, sHandler);
              //update the table view
              tableModel.fireTableRowsInserted(j, j);
          }else if(event.getType() == DocumentEvent.ANNOTATION_SET_REMOVED){
            String setName = e.getAnnotationSetName();
            //find the handler and remove it from the list of handlers
            SetHandler sHandler = getSetHandler(setName);
            if(sHandler != null){
              sHandler.set.removeAnnotationSetListener(AnnotationSetsView.this);
              //remove highlights if any
              Iterator<TypeHandler> typeIter = sHandler.typeHandlers.iterator();
              while(typeIter.hasNext()){
                TypeHandler tHandler = typeIter.next();
                tHandler.setSelected(false);
              }
              setHandlers.remove(sHandler);
              //remove the set from the table
              int row = tableRows.indexOf(sHandler);
              tableRows.remove(row);
              int removed = 1;
              //remove the type rows as well
              if(sHandler.isExpanded())
                for(int i = 0; i < sHandler.typeHandlers.size(); i++){ 
                  tableRows.remove(row);
                  removed++;
                }
              tableModel.fireTableRowsDeleted(row, row + removed -1);
              sHandler.cleanup();
            }
          }else{
            //some other kind of event we don't care about
          }
        }else if(event instanceof AnnotationSetEvent){
          AnnotationSetEvent e = (AnnotationSetEvent)event;
          AnnotationSet set = (AnnotationSet)e.getSource();
          Annotation ann = e.getAnnotation();
          if(event.getType() == AnnotationSetEvent.ANNOTATION_ADDED){
            TypeHandler tHandler = getTypeHandler(set.getName(), ann.getType());
            if(tHandler == null){
              //new type for this set
              SetHandler sHandler = getSetHandler(set.getName());
              tHandler = sHandler.newType(ann.getType());
            }
            tHandler.annotationAdded(ann);    
          }else if(event.getType() == AnnotationSetEvent.ANNOTATION_REMOVED){
            TypeHandler tHandler = getTypeHandler(set.getName(), ann.getType());
            if(tHandler != null) tHandler.annotationRemoved(ann);
          }else{
            //some other kind of event we don't care about
          }
        }else if(event instanceof PerformActionEvent){
          ((PerformActionEvent)event).run();
        }else{
          //unknown type of event -> ignore
        }
      }
    }
    
    /**
     * This method is used to update the display by reading the associated
     * document when it is considered that doing so would be cheaper than 
     * acting on the events queued
     */
    protected void rebuildDisplay(){
      //if there is a process still running, we may get concurrent modification 
      //exceptions, in which case we should give up and try again later.
      //this method will always run from the UI thread, so no synchronisation 
      //is necessary
      uiDirty = false;
      try{
        //Ignore all pending events, as we're rebuilding from scratch.
        //Rotate once through the whole queue, filtering out events we want
        //to ignore.
        GateEvent event;
        pendingEvents.offer(END_OF_LIST);
        while((event = pendingEvents.poll()) != END_OF_LIST){
          if(event instanceof DocumentEvent || 
             event instanceof AnnotationSetEvent){
            //ignore event
          }else{
            //event of unknown type -> we re-queue it!
            pendingEvents.offer(event);
          }
        }
        //store selection state and expanded sets
        storeSelectedTypes();
        Map<String, Boolean> expandedSets = new HashMap<String, Boolean>();
        for(SetHandler sHandler : setHandlers){
          // store expanded state
          expandedSets.put(sHandler.set.getName(), sHandler.isExpanded());
          // release all resources
          sHandler.typeHandlers.clear();
          sHandler.typeHandlersByType.clear();
          sHandler.set.removeAnnotationSetListener(AnnotationSetsView.this);
        }
        setHandlers.clear();
        tableRows.clear();
        listView.removeAnnotations(listView.getAllAnnotations());
        //update the stack view
        stackView.updateStackView();
//        textView.removeAllBlinkingHighlights();
        //rebuild the UI
        populateUI();
        
        //restore the selection
        restoreSelectedTypes();
        tableModel.fireTableDataChanged();
        // restore expansion state
        for(SetHandler sHandler : setHandlers){
          sHandler.setExpanded(expandedSets.get(sHandler.set.getName()));
        }        
      }catch(Throwable t){
        //something happened, we need to give up
        uiDirty = true;
//        t.printStackTrace();        
      }
    }
    
    boolean uiDirty = false;
    /**
     * Maximum number of events to treat individually. If we have more pending
     * events than this value, the UI will be rebuilt from scratch
     */
    private static final int MAX_EVENTS = 300;
  }
  
  /**
   * Used to select an annotation for editing.
   *
   */
  protected class MouseStoppedMovingAction extends AbstractAction{
    
    @Override
    public void actionPerformed(ActionEvent evt){
      if(annotationEditor == null) return;
      //this action either creates a new annotation or starts editing an 
      //existing one. In either case we need first to make sure that the current
      //annotation is finished editing.
      if(!annotationEditor.editingFinished()) return;
      if(textLocation == -1) return;
      JPopupMenu popup = new JPopupMenu();

      //check for selection hovering
      if(textPane.getSelectedText() != null
          && textPane.getSelectionStart() <= textLocation
          && textPane.getSelectionEnd() >= textLocation){
        //add 'New annotation' to the popup menu
        popup.add(new NewAnnotationAction(textPane.getSelectedText()));
        popup.addSeparator();
      }

      //check for annotations at location
      for(SetHandler setHandler : setHandlers) {
        for(Annotation ann : setHandler.set.get(
              Math.max(0l, textLocation-1),
              Math.min(document.getContent().size(), textLocation+1))) {
          if(setHandler.getTypeHandler(ann.getType()).isSelected()) {
            AnnotationDataImpl annotAtPoint =
              new AnnotationDataImpl(setHandler.set, ann);
            //add annotations to edit to the popup menu
            popup.add(new HighlightMenuItem(
              new EditAnnotationAction(annotAtPoint),
              annotAtPoint.getAnnotation().getStartNode().getOffset().intValue(),
              annotAtPoint.getAnnotation().getEndNode().getOffset().intValue(),
              popup));
          }
        }
      }

      if (popup.getComponentCount() == 0) {
        // nothing to do
      } else if(popup.getComponentCount() == 1
        || (popup.getComponentCount() == 2
         && popup.getComponent(1) instanceof JSeparator)) {
        //only one annotation, start the editing directly
        //or only one selection, add new annotation
        ((JMenuItem)popup.getComponent(0)).getAction().actionPerformed(evt);
      } else { //mouse hover a selection AND annotation(s)
        try{
          Rectangle rect =  textPane.modelToView(textLocation);
          //display the popup
          popup.show(textPane, rect.x + 10, rect.y);
        }catch(BadLocationException ble){
          throw new GateRuntimeException(ble);
        }
      }
    }
    
    public void setTextLocation(int textLocation){
      this.textLocation = textLocation;
    }
    int textLocation;
  }//protected class SelectAnnotationAction extends AbstractAction{
  
  
  /**
   * The popup menu items used to select annotations
   * Apart from the normal {@link javax.swing.JMenuItem} behaviour, this menu
   * item also highlights the annotation which it would select if pressed.
   */
  protected class HighlightMenuItem extends JMenuItem {
    public HighlightMenuItem(Action action, int startOffset, int endOffset, 
            JPopupMenu popup) {
      super(action);
      this.start = startOffset;
      this.end = endOffset;
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          showHighlight();
        }

        @Override
        public void mouseExited(MouseEvent e) {
          removeHighlight();
        }
      });
      popup.addPopupMenuListener(new PopupMenuListener(){
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e){
          
        }
        @Override
        public void popupMenuCanceled(PopupMenuEvent e){
          removeHighlight();
        }
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
          removeHighlight();
        }
        
        
      });
    }
    
    protected void showHighlight(){
      try {
        highlight = textPane.getHighlighter().addHighlight(start, end,
                                        DefaultHighlighter.DefaultPainter);
      }catch(BadLocationException ble){
        throw new GateRuntimeException(ble.toString());
      }

    }
    
    protected void removeHighlight(){
      if(highlight != null){
        textPane.getHighlighter().removeHighlight(highlight);
        highlight = null;
      }
      
    }

    int start;
    int end;
    Action action;
    Object highlight;
  }
  
  
  
  protected class EditAnnotationAction extends AbstractAction{
    public EditAnnotationAction(AnnotationData aData){
      super(aData.getAnnotation().getType() + " [" + 
              (aData.getAnnotationSet().getName() == null ? "  " : 
                aData.getAnnotationSet().getName()) +
              "]");
      putValue(SHORT_DESCRIPTION, aData.getAnnotation().getFeatures().toString());
      this.aData = aData;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
      if(annotationEditor == null) return;
      //if the editor is done with the current annotation, we can move to the 
      //next one
      if(annotationEditor.editingFinished()){
        //queue an event to set the annotation as selected
        selectAnnotation(aData);
        //queue an event to show the annotation editor
        Runnable action = new Runnable() {
          @Override
          public void run() {
            annotationEditor.editAnnotation(aData.getAnnotation(), 
                    aData.getAnnotationSet());
          }
        };
        pendingEvents.offer(new PerformActionEvent(action));
        eventMinder.restart();
      }
    }
    
    private AnnotationData aData;
  }
  
  protected class SetSelectedAnnotationsAction extends AbstractAction{
    public SetSelectedAnnotationsAction(boolean selected){
      String title = (selected) ? "Select all" : "Unselect all";
      putValue(NAME, title);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("SPACE"));
      this.selected = selected;
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      List<Object> handlersToSelect = new ArrayList<Object>();
      int[] selectedRows = mainTable.getSelectedRows();
      Arrays.sort(selectedRows);
      for (int row : selectedRows) {
        Object handler = tableRows.get(row);
        if(handler instanceof SetHandler){
          // store the set handler
          handlersToSelect.add(0, handler);
        } else if(handler instanceof TypeHandler
        && !handlersToSelect.contains(((TypeHandler)handler).setHandler)){
          // store the type handler
          // only if not included in a previous selected set handler
          handlersToSelect.add(handlersToSelect.size(), handler);
        }
      }
      for (Object handler : handlersToSelect) {
        if(handler instanceof TypeHandler){
          TypeHandler tHandler = (TypeHandler)handler;
          tHandler.setSelected(selected);
        }else if(handler instanceof SetHandler){
          SetHandler sHandler = (SetHandler)handler;
          for (String setName : sHandler.set.getAllTypes()) {
            TypeHandler tHandler = sHandler.getTypeHandler(setName);
            tHandler.setSelected(selected);
          }
        }
      }
    }
    boolean selected;
  }

  protected class DeleteSelectedAnnotationsAction extends AbstractAction{
    public DeleteSelectedAnnotationsAction(String name){
      putValue(NAME, name);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("DELETE"));
    }
    @Override
    public void actionPerformed(ActionEvent event){
      // builds the list of type and set handlers to delete
      Vector<String> resourcesToDelete = new Vector<String>(); 
      List<Object> handlersToDelete = new ArrayList<Object>();
      int[] selectedRows = mainTable.getSelectedRows();
      Arrays.sort(selectedRows);
      for (int row : selectedRows) {
        Object handler = tableRows.get(row);
        if(handler instanceof SetHandler){
          // store the set handler
          handlersToDelete.add(0, handler);
          String setName = ((SetHandler) handler).set.getName();
          setName = (setName == null)? "Default set" : setName;
          resourcesToDelete.add("set: " + setName);
        } else if(handler instanceof TypeHandler
        && !handlersToDelete.contains(((TypeHandler)handler).setHandler)){
          // store the type handler
          // only if not included in a previous selected set handler
          handlersToDelete.add(handlersToDelete.size(), handler);
          String setName = ((TypeHandler) handler).setHandler.set.getName();
          setName = (setName == null)? "Default set" : setName;
          resourcesToDelete.add("type: " + ((TypeHandler) handler).name
            + " in set: " + setName);
        }
      }
      if ((event.getModifiers() & ActionEvent.SHIFT_MASK)
                               != ActionEvent.SHIFT_MASK
       && (event.getModifiers() & InputEvent.BUTTON1_MASK)
                               != InputEvent.BUTTON1_MASK) {
        // shows a confirm dialog to delete types and sets
        JList<String> list = new JList<String>(resourcesToDelete);
        list.setVisibleRowCount(Math.min(resourcesToDelete.size()+1, 10));
        int choice = JOptionPane.showOptionDialog(MainFrame.getInstance(), new
          Object[]{"Are you sure you want to delete the following annotations?",
          '\n', new JScrollPane(list),
          "<html><i>You can use Shift+Delete to bypass this dialog.</i>\n\n"},
          "Delete annotations",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          new String[]{"Delete annotations", "Cancel"}, "Cancel");
        if (choice == JOptionPane.CLOSED_OPTION || choice == 1)  { return; }
      }
      // deletes types and sets
      for (Object handler : handlersToDelete) {
        if(handler instanceof SetHandler){
          SetHandler sHandler = (SetHandler)handler;
          if(sHandler.set == document.getAnnotations()){
            //the default annotation set - clear
            for (Annotation annotation: new HashSet<Annotation>(sHandler.set)) {
              sHandler.set.remove(annotation);
            }
          }else{
            document.removeAnnotationSet(sHandler.set.getName());
          }
        } else if(handler instanceof TypeHandler){
          TypeHandler tHandler = (TypeHandler)handler;
          AnnotationSet set = tHandler.setHandler.set;
          AnnotationSet toDeleteAS = set.get(tHandler.name);
          if(toDeleteAS != null && toDeleteAS.size() > 0){
            List<Annotation> toDelete = new ArrayList<Annotation>(toDeleteAS);
            set.removeAll(toDelete);
          }
        }
      }
    }
  }  
  
  List<SetHandler> setHandlers;
  /** Contains the data of the main table. */
  List<Object> tableRows; 
  XJTable mainTable;
  SetsTableModel tableModel;
  JScrollPane scroller;
  JPanel mainPanel;
  JTextField newSetNameTextField;
  
  TextualDocumentView textView;
  AnnotationListView listView;
  AnnotationStackView stackView;
  JTextArea textPane;
  gate.gui.annedit.OwnedAnnotationEditor annotationEditor;
  NewAnnotationSetAction newSetAction;
  
  /**
   * The listener for mouse and mouse motion events in the text view.
   */
  protected TextMouseListener textMouseListener;
  
  /**
   * Listener for property changes on the text pane.
   */
  protected PropertyChangeListener textChangeListener;
  
  /**
   * Stores the list of visible annotation types when the view is inactivated 
   * so that the selection can be restored when the view is made active again.
   * The values are String[2] pairs of form <set name, type>.
   */
  protected BlockingQueue<TypeSpec> visibleAnnotationTypes;
  
  protected Timer mouseMovementTimer;
  /**
   * Timer used to handle events coming from the document
   */
  protected Timer eventMinder;
  
  protected BlockingQueue<GateEvent> pendingEvents;
  
  private static final int MOUSE_MOVEMENT_TIMER_DELAY = 500;
  protected MouseStoppedMovingAction mouseStoppedMovingAction;
  
  protected String lastAnnotationType = "_New_";

  protected ComponentOrientation currentOrientation;
  
  protected final static ColorGenerator colourGenerator = new ColorGenerator();
  private static final int NAME_COL = 1;
  private static final int SELECTED_COL = 0;
  
  /**
   * A special GateEvent used as a flag.
   */
  private static final GateEvent END_OF_LIST = new GateEvent(
          AnnotationSetsView.class, 
          Integer.MAX_VALUE);
  
  private static final int EVENTS_HANDLE_DELAY = 300;
  
}
