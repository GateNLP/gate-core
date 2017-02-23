/*  CollectionSelectionDialog.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  05/Oct/2001
 *
 *  $Id: CollectionSelectionDialog.java 17879 2014-04-18 16:59:35Z markagreenwood $
 *
 */

package gate.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


/** This class visually selects some items from a collection and returns
  * a collection with the items selected by the user.
  */
@SuppressWarnings({"serial","unchecked","rawtypes","deprecation"})
public class CollectionSelectionDialog extends JDialog {

  // Local data
  ////////////////////////////
  /** This is the model for the list that the user will choose from*/
  DefaultListModel sourceListModel = null;
  /** This is the model for the list that the user chosed*/
  DefaultListModel targetListModel = null;
  /** A value indicating which button has been pressed (Ok or Cancel)*/
  int buttonPressed = JFileChooser.CANCEL_OPTION;
  // Gui Components
  /////////////////////////
  /** The button that removes items from the target list*/
  JButton removeButton = null;
  /** The button that adds items to the target list*/
  JButton addButton = null;
  /** The source list which contains the items that the user will select from*/
  JList   sourceList = null;
  /** The source list which contains the items that the user choosed*/
  JList   targetList = null;
  /** The Ok button*/
  JButton okButton = null;
  /** The Cancel button*/
  JButton cancelButton = null;
  /** A label for the source list*/
  JLabel sourceLabel = null;
  /** A label for the target list*/
  JLabel targetLabel = null;
  /** The parent frame for this dialog*/
  Frame mainFrame = null;

  /** Constructs an ColectionSelectionDialog
    * @param aFrame the parent frame of this dialog
    * @param aModal (wheter or not this dialog is modal)
    */
  public CollectionSelectionDialog(Frame aFrame, boolean aModal){
    super(aFrame,aModal);
    this.setLocationRelativeTo(aFrame);
    mainFrame = aFrame;
  }//CollectionSelectionDialog

  /** Constructs an ColectionSelectionDialog using <b>null<b> as a frame
    *   and <b>true
    *  </b> as modal value for dialog
    */
  public CollectionSelectionDialog(){
    this(null, true);
  }// CollectionSelectionDialog

  /** Init local data from a source collection
    * @param aSourceData is the collection from what the user will choose
    */
  protected void initLocalData(Collection aSourceData){
    targetListModel = new DefaultListModel();
    sourceListModel = new DefaultListModel();
    if (aSourceData == null) return;
    List source = new ArrayList(aSourceData);
    Collections.sort(source);
    Iterator iter = source.iterator();
    while(iter.hasNext()){
      sourceListModel.addElement(iter.next());
    }// End while
  }// initLocalData();

  /** This method creates the GUI components and paces them into the layout*/
  protected void initGuiComponents(){
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),
                                                  BoxLayout.Y_AXIS));
    // Create source label
    sourceLabel = new JLabel("Source");
    sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Create source list
    sourceList = new JList(sourceListModel);
    sourceList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    sourceList.setVisibleRowCount(10);
    sourceList.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Create target label
    targetLabel = new JLabel("Target");
    targetLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Create the target list
    targetList = new JList(targetListModel);
    targetList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    targetList.setVisibleRowCount(10);
    targetList.setAlignmentX(Component.LEFT_ALIGNMENT);
    targetList.setPreferredSize(sourceList.getPreferredSize());
    // Create Add >>  button
    addButton = new JButton(">>>");
    // Create Remove <<  button
    removeButton = new JButton("<<<");
    // Create ok button
    okButton = new JButton("Ok");
    // Create cancel button
    cancelButton = new JButton("Cancel");
    ///////////////////////////////////////
    // Arange components
    //////////////////////////////////////

    // Create the main box
    Box componentsBox = Box.createVerticalBox();
    componentsBox.add(Box.createRigidArea(new Dimension(0,5)));

    Box firstLevelBox = Box.createHorizontalBox();
    firstLevelBox.add(Box.createRigidArea(new Dimension(10,0)));
    // Add the Source list
    Box currentBox = Box.createVerticalBox();
    currentBox.add(sourceLabel);
    currentBox.add(new JScrollPane(sourceList));

    // Add the current box to the firstLevelBox
    firstLevelBox.add(currentBox);
    firstLevelBox.add(Box.createRigidArea(new Dimension(10,0)));

    // Add the add and remove buttons
    currentBox = Box.createVerticalBox();
    currentBox.add(addButton);
    currentBox.add(Box.createRigidArea(new Dimension(0,10)));
    currentBox.add(removeButton);

    // Add the remove buttons to the firstLevelBox
    firstLevelBox.add(currentBox);
    firstLevelBox.add(Box.createRigidArea(new Dimension(10,0)));

    // Add the target list
    currentBox = Box.createVerticalBox();
    currentBox.add(targetLabel);
    currentBox.add(new JScrollPane(targetList));

    // Add target list to the firstLevelBox
    firstLevelBox.add(currentBox);
    firstLevelBox.add(Box.createRigidArea(new Dimension(20,0)));

    // Add ok and cancel buttons to the currentBox
    currentBox = Box.createHorizontalBox();
    currentBox.add(Box.createHorizontalGlue());
    currentBox.add(okButton);
    currentBox.add(Box.createRigidArea(new Dimension(25,0)));
    currentBox.add(cancelButton);
    currentBox.add(Box.createHorizontalGlue());

    // Add all components to the components box
    componentsBox.add(firstLevelBox);
    componentsBox.add(Box.createRigidArea(new Dimension(0,10)));
    componentsBox.add(currentBox);
    componentsBox.add(Box.createRigidArea(new Dimension(0,5)));
    // Add the components box to the dialog
    this.getContentPane().add(componentsBox);
    this.pack();
}//initGuiComponents();

  /** Init all the listeners*/
  protected void initListeners(){
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doOk();
      }// actionPerformed();
    });// addActionListener();
    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doCancel();
      }// actionPerformed();
    });// addActionListener();
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doAdd();
      }// actionPerformed();
    });// addActionListener();
    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doRemove();
      }// actionPerformed();
    });// addActionListener();
  }//initListeners()

  /** This method is called when the user press the OK button*/
  private void doOk(){
    buttonPressed = JFileChooser.APPROVE_OPTION;
    this.setVisible(false);
  }//doOk();

  /** This method is called when the user press the CANCEL button*/
  private void doCancel(){
    buttonPressed = JFileChooser.CANCEL_OPTION;
    this.setVisible(false);
  }//doCancel();
  /** Called when user press remove button*/
  private void doRemove(){
    Object[] selectedItems = targetList.getSelectedValues();
    for (int i = 0 ; i < selectedItems.length; i ++){
      sourceListModel.addElement(selectedItems[i]);
      targetListModel.removeElement(selectedItems[i]);
    }// end for
  }// doRemove();
  /** Called when user press add button*/
  private void doAdd(){
    Object[] selectedItems = sourceList.getSelectedValues();
    for (int i = 0 ; i < selectedItems.length; i ++){
      targetListModel.addElement(selectedItems[i]);
      sourceListModel.removeElement(selectedItems[i]);
    }// end for
  }// doAdd();
  /** Returns the target collection*/
  public Collection getSelectedCollection(){
    List resultsList = new ArrayList();
    for (int i=0; i<targetListModel.getSize(); i++){
      resultsList.add(targetListModel.getElementAt(i));
    }// End for
    return resultsList;
  }// getSelectedCollection()

  /** This method displays the CollectionSelectionDialog*/
  public int show(String aTitle,Collection aSourceData){
    if (aTitle == null){
      JOptionPane.showMessageDialog(mainFrame,
      "Feature selection dialog coud not been created because title was null!",
      "GATE", JOptionPane.ERROR_MESSAGE);
      return buttonPressed;
    }// End if
    if (aSourceData == null){
     JOptionPane.showMessageDialog(mainFrame,
     "Feature selection dialog coud not been created because data source null!",
     "GATE", JOptionPane.ERROR_MESSAGE);
     return buttonPressed;
    }// End if
    this.setTitle(aTitle);
    initLocalData(aSourceData);
    initGuiComponents();
    initListeners();
    super.setVisible(true);
    return buttonPressed;
  }// show()
}//CollectionSelectionDialog class