/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 17/05/2002
 *
 *  $Id: CreateIndexGUI.java 17858 2014-04-17 14:10:55Z markagreenwood $
 *
 */
package gate.gui;

import gate.Gate;
import gate.creole.ir.IREngine;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Provides a gui for creating a IR index on a corpus.
 */
@SuppressWarnings("serial")
public class CreateIndexGUI extends JPanel {

  public CreateIndexGUI() {
    initLocalData();
    initGUIComponents();
    initListeners();
  }

  protected void initLocalData(){
    featuresList = new ArrayList<String>();
    engineByName = new TreeMap<String, IREngine>();
  }

  protected void initGUIComponents(){
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.WEST;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(2, 5, 2, 5);

    //first line
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    add(new JLabel("IR Engine type:"), constraints);
    constraints.gridwidth = 4;

    irEngineCombo = new JComboBox<String>();
    add(irEngineCombo, constraints);

    //second line
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    add(new JLabel("Index location:"), constraints);
    constraints.gridwidth = 4;
    indexLocationTextField = new JTextField(40);
    add(indexLocationTextField, constraints);
    constraints.gridwidth = 1;
    add(new JButton(new SelectDirAction()), constraints);

    //third line
    constraints.gridy =2;
    constraints.gridwidth = 2;
    add(new JLabel("Features to index:"), constraints);
    featuresListTextField = new JTextField(40);
    featuresListTextField.setEditable(false);
    constraints.gridwidth = 4;
    add(featuresListTextField, constraints);
    constraints.gridwidth = 1;
    add(new JButton(new EditFeatureListAction()), constraints);

    //fourth line
    constraints.gridy = 3;
    constraints.gridwidth = 4;
    useContentChk = new JCheckBox("Use document content", true);
    add(useContentChk, constraints);

    //populate engine names combo
    String oldIREngineName = (String)irEngineCombo.getSelectedItem();

    List<String> irEngines = new ArrayList<String>(Gate.getRegisteredIREngines());
    engineByName.clear();
    for(int i = 0; i < irEngines.size(); i++){
      String anIREngineClassName = irEngines.get(i);
      try{
        Class<?> aClass =
          Class.forName(anIREngineClassName, true, Gate.getClassLoader());
        IREngine engine = (IREngine)aClass.newInstance();
        engineByName.put(engine.getName(), engine);
      }catch(ClassNotFoundException cnfe){
      }catch(IllegalAccessException iae){
      }catch(InstantiationException ie){
      }
    }

    String[] names = new String[engineByName.size()];
    int i = 0;
    Iterator<String> namesIter = engineByName.keySet().iterator();
    while(namesIter.hasNext()){
      names[i++] = namesIter.next();
    }
    irEngineCombo.setModel(new DefaultComboBoxModel<String>(names));
    if(oldIREngineName != null && engineByName.containsKey(oldIREngineName)){
      irEngineCombo.setSelectedItem(oldIREngineName);
    }else if(engineByName.size() > 0) irEngineCombo.setSelectedIndex(0);
  }

  protected void initListeners(){
  }


  protected class SelectDirAction extends AbstractAction{
    public SelectDirAction(){
      super(null, MainFrame.getIcon("open-file"));
      putValue(SHORT_DESCRIPTION, "Click to open a file chooser!");
    }

    @Override
    public void actionPerformed(ActionEvent e){
      JFileChooser fileChooser = MainFrame.getFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.setDialogTitle("Select a directory for the index files");
      int res = fileChooser.showOpenDialog(CreateIndexGUI.this);
      if(res == JFileChooser.APPROVE_OPTION) indexLocationTextField.
                                            setText(fileChooser.
                                            getSelectedFile().toString());
    }
  }

  protected class EditFeatureListAction extends AbstractAction{
    public EditFeatureListAction(){
      super(null, MainFrame.getIcon("edit-list"));
      putValue(SHORT_DESCRIPTION, "Click to edit list!");
    }

    @Override
    public void actionPerformed(ActionEvent e){
      ListEditorDialog listEditor = new ListEditorDialog(CreateIndexGUI.this,
                                                         featuresList,
                                                         "java.lang.String");
      @SuppressWarnings("unchecked")
      List<String> result = listEditor.showDialog();
      if(result != null){
        featuresList.clear();
        featuresList.addAll(result);
        if(featuresList.size() > 0){
          String text = "[" + featuresList.get(0).toString();
          for(int j = 1; j < featuresList.size(); j++){
            text += ", " + featuresList.get(j).toString();
          }
          text += "]";
          featuresListTextField.setText(text);
        }else{
          featuresListTextField.setText("");
        }
      }
    }
  }

  public boolean getUseDocumentContent(){
    return useContentChk.isSelected();
  }

  public List<String> getFeaturesList(){
    return featuresList;
  }

  public String getIndexLocation(){
    return indexLocationTextField.getText();
  }

  public IREngine getIREngine(){
    return engineByName.get(irEngineCombo.getSelectedItem());
  }

  /**
   * Combobox for selecting IR engine.
   */
  JComboBox<String> irEngineCombo;

  /**
   * Text field for the location of the index.
   */
  JTextField indexLocationTextField;

  /**
   * Checkbox for content used.
   */
  JCheckBox useContentChk;

  /**
   * Text field for the list of features.
   */
  JTextField featuresListTextField;

  /**
   * The list of features.
   */
  List<String> featuresList;

  /**
   * A map from IREngine name to IREngine class name.
   */
  SortedMap<String,IREngine> engineByName;

}
