/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 02/Nov/2001
 *
 *  $Id: CorpusFillerComponent.java 17606 2014-03-09 12:12:49Z markagreenwood $
 */
package gate.gui;

import gate.Gate;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * A simple component that allows the user to select a directory and a list of
 * permitted extensions for populating a corpus
 */

@SuppressWarnings("serial")
public class CorpusFillerComponent extends JPanel {

  /**
   * Creates a corpus filler component
   */
  public CorpusFillerComponent(){
    initLocalData();
    initGUIComponents();
    initListeners();
  }

  /**
   * Inits local variables to default values
   */
  protected void initLocalData(){
    extensions = new ArrayList<String>();
  }


  /**
   * Creates the UI
   */
  protected void initGUIComponents(){
    setLayout(new GridBagLayout());
    //first row
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.fill = GridBagConstraints.NONE;
    constraints.insets = new Insets(0, 0, 0, 5);
    add(new JLabel("Directory URL:"), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 0;
    constraints.gridwidth = 5;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(0, 0, 0, 10);
    add(urlTextField = new JTextField(40), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 0;
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    add(filerBtn = new JButton(MainFrame.getIcon("open-file")), constraints);

    //second row
    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 1;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.fill = GridBagConstraints.NONE;
    constraints.insets = new Insets(0, 0, 0, 5);
    add(new JLabel("Extensions:"), constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 1;
    constraints.gridwidth = 5;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(0, 0, 0, 10);
    add(extensionsTextField = new JTextField(40), constraints);
    extensionsTextField.setEditable(false);
    extensionsTextField.setText(extensions.toString());

    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    add(listEditBtn = new JButton(MainFrame.getIcon("edit-list")), constraints);

    //third row
    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 2;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.fill = GridBagConstraints.NONE;
    constraints.insets = new Insets(0, 0, 0, 5);
    add(new JLabel("Encoding:"), constraints);


    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 2;
    constraints.gridwidth = 4;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    add(encodingTextField = new JTextField(15), constraints);

    //fourth row
    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.fill = GridBagConstraints.NONE;
    constraints.insets = new Insets(0, 0, 0, 5);
    add(new JLabel("Mime Type:"), constraints);


    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 3;
    constraints.gridwidth = 4;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    add(mimeTypeTextField = new JTextField(15), constraints);

    
    //fifth row
    recurseCheckBox = new JCheckBox("Recurse directories");
    recurseCheckBox.setSelected(true);
    recurseCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
    constraints = new GridBagConstraints();
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.gridy = 4;
    constraints.gridwidth = 3;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    add(recurseCheckBox, constraints);

  }

  /**
   * Adds listeners for UI components
   */
  protected void initListeners(){
    filerBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser filer = MainFrame.getFileChooser();
        filer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        filer.setDialogTitle("Select a directory");

        filer.resetChoosableFileFilters();
        filer.setAcceptAllFileFilterUsed(true);
        filer.setFileFilter(filer.getAcceptAllFileFilter());
        int res = filer.showOpenDialog(CorpusFillerComponent.this);
        if(res == JFileChooser.APPROVE_OPTION){
          try {
            urlTextField.setText(filer.getSelectedFile().
                                 toURI().toURL().toExternalForm());
          } catch(IOException ioe){}
        }
      }
    });

    listEditBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ListEditorDialog listEditor = new ListEditorDialog(
          CorpusFillerComponent.this, extensions, String.class.getName());
        @SuppressWarnings("unchecked")
        List<String> answer = listEditor.showDialog();
        if(answer != null){
          extensions.clear();
          extensions.addAll(answer);
          extensionsTextField.setText(extensions.toString());
        }
      }
    });
  }

  /**
   * Sets the values for the URL string. This value is not cached so the set
   * will actually the text in the text field itself
   */
  public void setUrlString(String urlString) {
    urlTextField.setText(urlString);
  }

  /**
   * Gets the current text in the URL text field.
   */
  public String getUrlString() {
    return urlTextField.getText();
  }

  /**
   * Gets the encoding selected by the user.
   */
  public String getEncoding(){
    return encodingTextField.getText();
  }

  /**
   * Sets the initila value for the encoding field.
   */
  public void setEncoding(String enc){
    encodingTextField.setText(enc);
  }

  /**
   * Gets the mime type selected by the user.
   */
  public String getMimeType(){
    return mimeTypeTextField.getText();
  }

  /**
   * Sets the initial value for the mime type field.
   */
  public void setMimeType(String mimeType){
    mimeTypeTextField.setText(mimeType);
  }
  /**
   * Sets the current value for the list of permitted extensions.
   */
  public void setExtensions(List<String> extensions) {
    this.extensions = extensions;
    extensionsTextField.setText(extensions.toString());
  }


  /**
   * Gets the current list of permitted extensions
   */
  public List<String> getExtensions() {
    return extensions;
  }

  /**
   * Test code
   */
  static public void main(String[] args){
    try{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      Gate.init();
    }catch(Exception e){
      e.printStackTrace();
    }
    JFrame frame = new JFrame("Foo");
    CorpusFillerComponent comp = new CorpusFillerComponent();
    frame.getContentPane().add(comp);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  }

  /**
   * Should the directory parsed recursively?
   */
  public void setRecurseDirectories(boolean recurseDirectories) {
    recurseCheckBox.setSelected(recurseDirectories);
  }

  /**
   * Should the directory parsed recursively?
   */
  public boolean isRecurseDirectories() {
    return recurseCheckBox.isSelected();
  }

  /**
   * The text field for the directory URL
   */
  JTextField urlTextField;

  /**
   * The buttons that opens the file chooser
   */
  JButton filerBtn;

  /**
   * The text field for the permitted extensions
   */
  JTextField extensionsTextField;

  /**
   * The buton that opens the list editor for the extensions
   */
  JButton listEditBtn;

  /**
   * The checkbox for recurse directories
   */
  JCheckBox recurseCheckBox;

  /**
   * The textField for the encoding
   */
  JTextField encodingTextField;
  
  /**
   * The textField for the encoding
   */
  JTextField mimeTypeTextField;
  
  /**
   * The list of permitted extensions.
   */
  private List<String> extensions;
}