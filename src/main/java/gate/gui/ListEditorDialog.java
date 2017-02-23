/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 16/10/2001
 *
 *  $Id: ListEditorDialog.java 17616 2014-03-10 16:09:07Z markagreenwood $
 *
 */

package gate.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

import gate.Gate;
import gate.creole.ResourceData;
import gate.util.*;

/**
 * A simple editor for Collection values.
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class ListEditorDialog extends JDialog {

  /**
   * Contructs a new ListEditorDialog.
   * @param owner the component this dialog will be centred on.
   * @param data a Collection with the initial values. This will not be changed,
   * its values will be cached and if the user selects the OK option a new list
   * with the updated contents will be returned.
   * @param itemType the type of the elements in the collection in the form of a
   * fully qualified class name
   */
  public ListEditorDialog(Component owner, Collection<?> data, String itemType) {
    this(owner, data, null, itemType);
  }
  
  /**
   * Contructs a new ListEditorDialog.
   * @param owner the component this dialog will be centred on.
   * @param data a Collection with the initial values. This will not be changed,
   * its values will be cached and if the user selects the OK option a new list
   * with the updated contents will be returned.
   * @param collectionType the class of the <code>data</code> collection.
   * If null the class will be inferred from <code>data</code>.  If
   * <code>data</code> is also null, {@link List} will be assumed.
   * @param itemType the type of the elements in the collection in the form of a
   * fully qualified class name
   */
  public ListEditorDialog(Component owner, Collection<?> data,
          Class<?> collectionType, String itemType) {
    super(MainFrame.getInstance());
    if(collectionType == null) {
      if(data != null) {
        collectionType = data.getClass();
      }
      else {
        collectionType = List.class;
      }
    }
    this.itemType = itemType == null ? "java.lang.String" : itemType;
    setLocationRelativeTo(owner);
    initLocalData(data, collectionType);
    initGuiComponents();
    initListeners();
  }

  protected void initLocalData(Collection<?> data,
          Class<?> collectionType){
    try{
      ResourceData rData = Gate.getCreoleRegister().get(itemType);
      itemTypeClass = rData == null ?
                      Class.forName(itemType, true, Gate.getClassLoader()) :
                      rData.getResourceClass();
    }catch(ClassNotFoundException cnfe){
      throw new GateRuntimeException(cnfe.toString());
    }

    finiteType = Gate.isGateType(itemType);

    ResourceData rData = Gate.getCreoleRegister().get(itemType);

    String typeDescription = null;
    if(List.class.isAssignableFrom(collectionType)) {
      typeDescription = "List";
      allowDuplicates = true;
    }
    else {
      if(Set.class.isAssignableFrom(collectionType)) {
        typeDescription = "Set";
        allowDuplicates = false;
      }
      else {
        typeDescription = "Collection";
        allowDuplicates = true;
      }
      
      if(SortedSet.class.isAssignableFrom(collectionType)
              && data != null) {
        comparator = ((SortedSet<?>)data).comparator();
      }
      if(comparator == null) {
        comparator = new NaturalComparator();
      }
    }
    
    listModel = new DefaultListModel();
    if(data != null){
      if(comparator == null) {
        for(Object elt : data) {
          listModel.addElement(elt);
        }
      }
      else {
        Object[] dataArray = data.toArray();
        Arrays.sort(dataArray, comparator);
        for(Object elt : dataArray) {
          listModel.addElement(elt);
        }
      }
    }

    setTitle(typeDescription + " of "
            + ((rData== null) ? itemType :rData.getName()));
    addAction = new AddAction();
    removeAction = new RemoveAction();
  }

  protected void initGuiComponents(){
    getContentPane().setLayout(new BoxLayout(getContentPane(),
                                             BoxLayout.Y_AXIS));

    //the editor component
    JComponent editComp = null;
    if(finiteType){
      editComp = combo = new JComboBox(new ResourceComboModel());
      combo.setRenderer(new ResourceRenderer());
      if(combo.getModel().getSize() > 0){
        combo.getModel().setSelectedItem(combo.getModel().getElementAt(0));
      }
    }else{
      editComp = textField = new JTextField(20);
    }

    getContentPane().add(editComp);
    getContentPane().add(Box.createVerticalStrut(5));

    //the buttons box
    Box buttonsBox = Box.createHorizontalBox();
    addBtn = new JButton(addAction);
    addBtn.setMnemonic(KeyEvent.VK_A);
    removeBtn = new JButton(removeAction);
    removeBtn.setMnemonic(KeyEvent.VK_R);
    buttonsBox.add(Box.createHorizontalGlue());
    buttonsBox.add(addBtn);
    buttonsBox.add(Box.createHorizontalStrut(5));
    buttonsBox.add(removeBtn);
    buttonsBox.add(Box.createHorizontalGlue());
    getContentPane().add(buttonsBox);
    getContentPane().add(Box.createVerticalStrut(5));

    //the list component
    Box horBox = Box.createHorizontalBox();
    listComponent = new JList(listModel);
    listComponent.setSelectionMode(ListSelectionModel.
                                   MULTIPLE_INTERVAL_SELECTION);
    listComponent.setCellRenderer(new ResourceRenderer());
    horBox.add(new JScrollPane(listComponent));
    //up down buttons if the user should control the ordering
    if(comparator == null) {
      Box verBox = Box.createVerticalBox();
      verBox.add(Box.createVerticalGlue());
      moveUpBtn = new JButton(MainFrame.getIcon("up"));
      verBox.add(moveUpBtn);
      verBox.add(Box.createVerticalStrut(5));
      moveDownBtn = new JButton(MainFrame.getIcon("down"));
      verBox.add(moveDownBtn);
      verBox.add(Box.createVerticalGlue());
      horBox.add(Box.createHorizontalStrut(3));
      horBox.add(verBox);
    }
    horBox.add(Box.createHorizontalStrut(3));
    getContentPane().add(horBox);
    getContentPane().add(Box.createVerticalStrut(5));

    //the bottom buttons
    buttonsBox = Box.createHorizontalBox();
    buttonsBox.add(Box.createHorizontalGlue());
    okButton = new JButton("OK");
    buttonsBox.add(okButton);
    buttonsBox.add(Box.createHorizontalStrut(5));
    cancelButton = new JButton("Cancel");
    buttonsBox.add(cancelButton);
    buttonsBox.add(Box.createHorizontalGlue());
    getContentPane().add(buttonsBox);
  }

  protected void initListeners(){
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        userCancelled = false;
        setVisible(false);
      }
    });

    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        userCancelled = true;
        setVisible(false);
      }
    });

    // define keystrokes action bindings at the level of the main window
    InputMap inputMap = ((JComponent)this.getContentPane()).
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = ((JComponent)this.getContentPane()).getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Apply");
    actionMap.put("Apply", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        okButton.doClick();
      }
    });
    inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "Cancel");
    actionMap.put("Cancel", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelButton.doClick();
      }
    });

    if(moveUpBtn != null) {
      moveUpBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int rows[] = listComponent.getSelectedIndices();
          if(rows == null || rows.length == 0){
            JOptionPane.showMessageDialog(
                ListEditorDialog.this,
                "Please select some items to be moved ",
                "GATE", JOptionPane.ERROR_MESSAGE);
          }else{
            //we need to make sure the rows are sorted
            Arrays.sort(rows);
            //get the list of items
            for(int i = 0; i < rows.length; i++){
              int row = rows[i];
              if(row > 0){
                //move it up
                Object value = listModel.remove(row);
                listModel.add(row - 1, value);
              }
            }
            //restore selection
            for(int i = 0; i < rows.length; i++){
              int newRow = -1;
              if(rows[i] > 0) newRow = rows[i] - 1;
              else newRow = rows[i];
              listComponent.addSelectionInterval(newRow, newRow);
            }
          }
  
        }//public void actionPerformed(ActionEvent e)
      });
    }


    if(moveDownBtn != null) {
      moveDownBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int rows[] = listComponent.getSelectedIndices();
          if(rows == null || rows.length == 0){
            JOptionPane.showMessageDialog(
                ListEditorDialog.this,
                "Please select some items to be moved ",
                "GATE", JOptionPane.ERROR_MESSAGE);
          } else {
            //we need to make sure the rows are sorted
            Arrays.sort(rows);
            //get the list of items
            for(int i = rows.length - 1; i >= 0; i--){
              int row = rows[i];
              if(row < listModel.size() -1){
                //move it down
                Object value = listModel.remove(row);
                listModel.add(row + 1, value);
              }
            }
            //restore selection
            for(int i = 0; i < rows.length; i++){
              int newRow = -1;
              if(rows[i] < listModel.size() - 1) newRow = rows[i] + 1;
              else newRow = rows[i];
              listComponent.addSelectionInterval(newRow, newRow);
            }
          }
  
        }//public void actionPerformed(ActionEvent e)
      });
    }

  
  
  }

  /**
   * Make this dialog visible allowing the editing of the collection.
   * If the user selects the <b>OK</b> option a new list with the updated
   * contents will be returned; it the <b>Cancel</b> option is selected this
   * method return <tt>null</tt>.  Note that this method always returns
   * a <code>List</code>.  When used for a resource parameter this is
   * OK, as GATE automatically converts this to the right collection
   * type when the resource is created, but if you use this class
   * anywhere else to edit a non-<code>List</code> collection you will
   * have to copy the result back into a collection of the appropriate
   * type yourself.
   */
  public List showDialog(){
    pack();
    userCancelled = true;
    setModal(true);
    super.setVisible(true);
    return userCancelled ? null : Arrays.asList(listModel.toArray());
  }

  /**
   * test code
   */
  public static void main(String[] args){
    try{
      Gate.init();
    }catch(Exception e){
      e.printStackTrace();
    }
    JFrame frame = new JFrame("Foo frame");

    ListEditorDialog dialog = new ListEditorDialog(frame,
                                                   new ArrayList(),
                                                   "java.lang.Integer");

    frame.setSize(300, 300);
    frame.setVisible(true);
    System.out.println(dialog.showDialog());
  }

  /**
   * Adds an element to the list from the editing component located at the top
   * of this dialog.
   */
  protected class AddAction extends AbstractAction{
    AddAction(){
      super("Add");
      putValue(SHORT_DESCRIPTION, "Add the edited value to the list");
    }
    @Override
    public void actionPerformed(ActionEvent e){
      if(finiteType){
        listModel.addElement(combo.getSelectedItem());
      }else{
        Object value = null;
        //convert the value to the proper type
        String stringValue = textField.getText();
        if(stringValue == null || stringValue.length() == 0) stringValue = null;

        if(itemTypeClass.isAssignableFrom(String.class)){
          //no conversion necessary
          value = stringValue;
        }else{
          //try conversion
          try{
            value = itemTypeClass.getConstructor(new Class[]{String.class}).
                                  newInstance( new Object[]{stringValue} );
          }catch(Exception ex){
            JOptionPane.showMessageDialog(
                ListEditorDialog.this,
                "Invalid value!\nIs it the right type?",
                "GATE", JOptionPane.ERROR_MESSAGE);
            return;
          }
        }
        
        if(comparator == null) {
          // for a straight list, add at the end always
          listModel.addElement(value);
        }
        else {
          // otherwise, find where to insert
          int index = 0;
          while(index < listModel.size()
                  && comparator.compare(value, listModel.get(index)) > 0) {
            index++;
          }
          if(index == listModel.size()) {
            // moved past the end of the list, and the new value is
            // not contained in the list, so add at the end
            listModel.addElement(value);
          }
          else {
            if(allowDuplicates
                    || comparator.compare(value, listModel.get(index)) < 0) {
              // insert at the found index if either duplicates are allowed
              // or it's not a duplicate
              listModel.add(index, value);
            }
          }
        }
        textField.setText("");
        textField.requestFocus();
      }
    }
  }

  /**
   * Removes the selected element(s) from the list
   */
  protected class RemoveAction extends AbstractAction{
    RemoveAction(){
      super("Remove");
      putValue(SHORT_DESCRIPTION, "Remove the selected value(s) from the list");
    }

    @Override
    public void actionPerformed(ActionEvent e){
      int[] indices = listComponent.getSelectedIndices();
      Arrays.sort(indices);
      for(int i = indices.length -1; i >= 0; i--){
        listModel.remove(indices[i]);
      }
    }
  }


  /**
   * A model for a combobox containing the loaded corpora in the system
   */
  protected class ResourceComboModel extends AbstractListModel
                                  implements ComboBoxModel{

    @Override
    public int getSize(){
      //get all corpora regardless of their actual type
      java.util.List loadedResources = null;
      try{
        loadedResources = Gate.getCreoleRegister().
                               getAllInstances(itemType);
      }catch(GateException ge){
        ge.printStackTrace(Err.getPrintWriter());
      }

      return loadedResources == null ? 0 : loadedResources.size();
    }

    @Override
    public Object getElementAt(int index){
      //get all corpora regardless of their actual type
      java.util.List loadedResources = null;
      try{
        loadedResources = Gate.getCreoleRegister().
                               getAllInstances(itemType);
      }catch(GateException ge){
        ge.printStackTrace(Err.getPrintWriter());
      }
      return loadedResources == null? null : loadedResources.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem){
      if(anItem == null) selectedItem = null;
      else selectedItem = anItem;
    }

    @Override
    public Object getSelectedItem(){
      return selectedItem;
    }

    void fireDataChanged(){
      fireContentsChanged(this, 0, getSize());
    }

    Object selectedItem = null;
  }

  /**
   * The type of the elements in the list
   */
  String itemType;

  /**
   * The Class for the elements in the list
   */
  Class itemTypeClass;

  /**
   * The GUI compoenent used to display the list
   */
  JList listComponent;

  /**
   * Comobox used to select among values for GATE types
   */
  JComboBox combo;

  /**
   * Text field used to input new arbitrary values
   */
  JTextField textField;

  /**
   * Used to remove the selected element in the list;
   */
  JButton removeBtn;

  /**
   * Used to add a new value to the list
   */
  JButton addBtn;

  /**
   * Moves up one or more items in the list
   */
  JButton moveUpBtn;

  /**
   * Moves down one or more items in the list
   */
  JButton moveDownBtn;

  /**
   * The model used by the {@link #listComponent}
   */
  DefaultListModel listModel;

  /**
   * Does the item type have a finite range (i.e. should we use the combo)?
   */
  boolean finiteType;

  /**
   * An action that adds the item being edited to the list
   */
  Action addAction;

  /**
   * An action that removes the item(s) currently selected from the list
   */
  Action removeAction;

  /**
   * The OK button for this dialog
   */
  JButton okButton;

  /**
   * The cancel button for this dialog
   */
  JButton cancelButton;

  /**
   * Did the user press the cancel button?
   */
  boolean userCancelled;
  
  /**
   * Does this collection permit duplicate entries?
   */
  boolean allowDuplicates;
  
  /**
   * Comparator to use to sort the entries displayed in the list.
   * If this dialog was created to edit a List, this will be null
   * and the ordering provided by the user will be preserved.  If
   * the dialog was created from a Set or plain Collection this
   * will be either the set's own comparator (if a SortedSet) or a
   * {@link NaturalComparator}.
   */
  Comparator comparator;
  
  /**
   * A comparator that uses the objects' natural order if the item
   * class of the collection implements Comparable, and compares
   * their <code>toString</code> representations if not.
   * <code>null</code> is always treated as less than anything
   * non-<code>null</code>.
   */
  protected class NaturalComparator implements Comparator {
    @Override
    public int compare(Object a, Object b) {
      if(a == null) {
        if(b == null) {
          return 0;
        }
        else {
          return -1;
        }
      }
      else if(b == null) {
        return 1;
      }
      else if(Comparable.class.isAssignableFrom(itemTypeClass)) {
        return ((Comparable)a).compareTo(b);
      }
      else {
        return a.toString().compareTo(b.toString());
      }
    }
  }
}
