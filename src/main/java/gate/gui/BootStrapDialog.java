/*  BootStrapDialog.java
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU 05/03/2001
 *
 *  $Id: BootStrapDialog.java 17996 2014-05-15 09:58:35Z markagreenwood $
 *
 */

package gate.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import gate.creole.BootStrap;
import gate.util.Err;

/**
  * This class is used to handle BootStrap wizard with the Gate GUI interface.
  */
@SuppressWarnings("serial")
public class BootStrapDialog extends JDialog{

  MainFrame mainFrame = null;
  BootStrapDialog thisBootStrapDialog = null;
  BootStrap bootStrapWizard = null;
  // Local data
  String resourceName = null;
  String packageName = null;
  String resourceType = null;
  Map<String,String>    resourceTypes = null;
  String className = null;
  Set<String>    resourceInterfaces = null;
  String possibleInterfaces = null;
  String pathNewProject = null;

  // GUI components
  JLabel     resourceNameLabel = null;
  JTextField resourceNameTextField = null;

  JLabel     packageNameLabel = null;
  JTextField packageNameTextField = null;

  JLabel     resourceTypesLabel = null;
  JComboBox<String>  resourceTypesComboBox = null;

  JLabel     classNameLabel = null;
  JTextField classNameTextField = null;

  JLabel     interfacesLabel = null;
  JTextField interfacesTextField = null;

  JLabel     chooseFolderLabel = null;
  JTextField chooseFolderTextField = null;
  JButton    chooseFolderButton = null;

  JButton    createResourceButton = null;
  JButton    cancelButton = null;
  JButton    helpButton = null;

  JFileChooser fileChooser = null;

  public BootStrapDialog(MainFrame aMainFrame){
    super(aMainFrame);
    mainFrame = aMainFrame;
    thisBootStrapDialog = this;
    this.setTitle("BootStrap Wizard");
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    initLocalData();
    initGuiComponents();
    initListeners();
  }//BootStrapDialog

  private void doCreateResource(){
    // Collect the  resourceName and signal ERROR if something goes wrong
    resourceName = resourceNameTextField.getText();
    if (resourceName == null || "".equals(resourceName)){
      thisBootStrapDialog.setModal(false);
      JOptionPane.showMessageDialog(mainFrame,
                      "A name for the resource must be provided",
                      "ERROR !",
                      JOptionPane.ERROR_MESSAGE);
      thisBootStrapDialog.setModal(true);
      return;
    }// End if

    // Collect the  packageName and signal ERROR if something goes wrong
    packageName = packageNameTextField.getText();
    if (packageName == null || "".equals(packageName)){
      thisBootStrapDialog.setModal(false);
      JOptionPane.showMessageDialog(mainFrame,
                      "A package name must be provided",
                      "ERROR !",
                      JOptionPane.ERROR_MESSAGE);
      thisBootStrapDialog.setModal(true);
      return;
    }// End if

    // Collect the  className and signal ERROR if something goes wrong
    className = classNameTextField.getText();
    if (className == null || "".equals(className)){
      thisBootStrapDialog.setModal(false);
      JOptionPane.showMessageDialog(mainFrame,
                      "A name for the implementing class must be provided",
                      "ERROR !",
                      JOptionPane.ERROR_MESSAGE);
      thisBootStrapDialog.setModal(true);
      return;
    }// End if

    // Collect the pathNewproject and signal ERROR if something goes wrong
    pathNewProject = chooseFolderTextField.getText();
    if (pathNewProject == null || "".equals(pathNewProject)){
      thisBootStrapDialog.setModal(false);
      JOptionPane.showMessageDialog(mainFrame,
                      "A path to the creation folder must be provided",
                      "ERROR !",
                      JOptionPane.ERROR_MESSAGE);
      thisBootStrapDialog.setModal(true);
      return;
    }// End if

    // Collect the  resourceType and signal ERROR if something goes wrong
    resourceType = (String)resourceTypesComboBox.getSelectedItem();
    resourceInterfaces = this.getSelectedInterfaces();

    Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                               new CreateResourceRunner(),
                               "BootstrapDialog1");
    thread.setPriority(Thread.MIN_PRIORITY);
    thread.start();
  }//doCreateResource();

  /**Initialises the data (the loaded resources)*/
  public void initLocalData(){
    pathNewProject = new String(".");
    resourceTypes = new HashMap<String,String>();
    resourceTypes.put("LanguageResource","gate.LanguageResource");
    resourceTypes.put("VisualResource","gate.VisualResource");
    resourceTypes.put("ProcessingResource","gate.ProcessingResource");

    possibleInterfaces = resourceTypes.get("LanguageResource");
    if (possibleInterfaces == null)
      possibleInterfaces = new String();
  }// initLocalData

  /**
    * This method initializes the GUI components
    */
  public void initGuiComponents(){

    //Initialise GUI components
    this.getContentPane().setLayout(
        new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
    this.setModal(true);
    // Init resource name
    resourceNameLabel = new JLabel("Resource name, e.g. myMorph");
    resourceNameLabel.setToolTipText("The name of the resource" +
                                     " you want to create");
    resourceNameLabel.setOpaque(true);
    resourceNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    resourceNameTextField = new JTextField();
    resourceNameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    resourceNameTextField.setColumns(40);
    Dimension dim = new Dimension(
                              resourceNameTextField.getPreferredSize().width,
                              resourceNameTextField.getPreferredSize().height);
    resourceNameTextField.setPreferredSize(dim);
    resourceNameTextField.setMinimumSize(dim);

    // Init package name
    packageNameLabel =
      new JLabel("Resource package, e.g. sheffield.creole.morph");
    packageNameLabel.setToolTipText("The Java package of the resource" +
                                     " you want to create");
    packageNameLabel.setOpaque(true);
    packageNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    packageNameTextField = new JTextField();
    packageNameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    packageNameTextField.setColumns(40);
    dim = new Dimension( packageNameTextField.getPreferredSize().width,
                         packageNameTextField.getPreferredSize().height);
    packageNameTextField.setPreferredSize(dim);
    packageNameTextField.setMinimumSize(dim);

    // init resourceTypesComboBox
    resourceTypesLabel = new JLabel("Resource type");
    resourceTypesLabel.setToolTipText("Resources must be LRs, PRs or VRs");
    resourceTypesLabel.setOpaque(true);
    resourceTypesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    Vector<String> comboCont = new Vector<String>(resourceTypes.keySet());
    Collections.sort(comboCont);
    resourceTypesComboBox = new JComboBox<String>(comboCont);
    resourceTypesComboBox.setEditable(false);
    resourceTypesComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

    // init class name
    classNameLabel = new JLabel("Implementing class name, e.g. Morpher");
    classNameLabel.setToolTipText("The name of the class that " +
                                  "impements this resource");
    classNameLabel.setOpaque(true);
    classNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    classNameTextField = new JTextField();
    classNameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    classNameTextField.setColumns(40);
    dim = new Dimension(classNameTextField.getPreferredSize().width,
                        classNameTextField.getPreferredSize().height);

    classNameTextField.setPreferredSize(dim);
    classNameTextField.setMinimumSize(dim);
//    classNameTextField.setMaximumSize(dim);

    // init interfaces
    interfacesLabel = new JLabel("Interfaces implemented");
    interfacesLabel.setToolTipText(
      "Any additional interfaces implemented, separated by comma"
    );
    interfacesLabel.setOpaque(true);
    interfacesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    interfacesTextField = new JTextField(possibleInterfaces);
    interfacesTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    interfacesTextField.setColumns(40);
    dim = new Dimension(interfacesTextField.getPreferredSize().width,
                        interfacesTextField.getPreferredSize().height);

    interfacesTextField.setPreferredSize(dim);
    interfacesTextField.setMinimumSize(dim);
//    interfacesTextField.setMaximumSize(dim);

    // init choose Folder
    chooseFolderLabel = new JLabel("Create in folder ...");
    chooseFolderLabel.setOpaque(true);
    chooseFolderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    chooseFolderLabel.setToolTipText("Select the name of the folder where" +
                                  " you want the resource to be created.");
    chooseFolderButton = new JButton("Browse");
    chooseFolderButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    chooseFolderTextField = new JTextField();
    chooseFolderTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    chooseFolderTextField.setColumns(35);
    dim = new Dimension(chooseFolderTextField.getPreferredSize().width,
                        chooseFolderTextField.getPreferredSize().height);

    chooseFolderTextField.setPreferredSize(dim);
    chooseFolderTextField.setMinimumSize(dim);
//    chooseFolderTextField.setMaximumSize(dim);

    // init createresource
    createResourceButton = new JButton("Finish");
    getRootPane().setDefaultButton(createResourceButton);
    // init cancel
    cancelButton = new JButton("Cancel");
    helpButton = new JButton("Help");
    fileChooser = new JFileChooser();

    // Arrange the components
    // Put all those components at their place
    Box mainBox = new Box(BoxLayout.Y_AXIS);

    // resourceName
    Box currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(resourceNameLabel);
    currentBox.add(resourceNameTextField);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,10)));

    // packageName
    currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(packageNameLabel);
    currentBox.add(packageNameTextField);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,10)));

    // resourceTypes
    currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(resourceTypesLabel);
    currentBox.add(resourceTypesComboBox);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,10)));

    // className
    currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(classNameLabel);
    currentBox.add(classNameTextField);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,10)));

    // interfaces
    currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(interfacesLabel);
    currentBox.add(interfacesTextField);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,10)));

    // folderName
    currentBox = new Box(BoxLayout.Y_AXIS);
    currentBox.add(chooseFolderLabel);
    JPanel tmpBox = new JPanel();
    tmpBox.setLayout(new BoxLayout(tmpBox,BoxLayout.X_AXIS));
    tmpBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    tmpBox.add(chooseFolderTextField);
    tmpBox.add(chooseFolderButton);
    currentBox.add(tmpBox);
    mainBox.add(currentBox);

    mainBox.add(Box.createRigidArea(new Dimension(0,20)));

    tmpBox = new JPanel();
    tmpBox.setLayout(new BoxLayout(tmpBox,BoxLayout.X_AXIS));
    tmpBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    tmpBox.add(Box.createHorizontalGlue());
    tmpBox.add(createResourceButton);
    tmpBox.add(Box.createRigidArea(new Dimension(25,0)));
    tmpBox.add(cancelButton);
    tmpBox.add(Box.createRigidArea(new Dimension(25,0)));
    tmpBox.add(helpButton);
    tmpBox.add(Box.createHorizontalGlue());
    mainBox.add(tmpBox);

    // Add a space
    this.getContentPane().add(Box.createVerticalGlue());
    this.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
    this.getContentPane().add(mainBox);
    this.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
    this.getContentPane().add(Box.createVerticalGlue());

    this.pack();
    ////////////////////////////////
    // Center it on screen
    ///////////////////////////////
    Dimension ownerSize;
    Point ownerLocation;
    if(getOwner() == null){
      ownerSize = Toolkit.getDefaultToolkit().getScreenSize();
      ownerLocation = new Point(0, 0);
    }else{
      ownerSize = getOwner().getSize();
      ownerLocation = getOwner().getLocation();
      if(ownerSize.height == 0 ||
         ownerSize.width == 0 ||
         !getOwner().isVisible()){
        ownerSize = Toolkit.getDefaultToolkit().getScreenSize();
        ownerLocation = new Point(0, 0);
      }
    }
    //Center the window
    Dimension frameSize = getSize();
    if (frameSize.height > ownerSize.height)
      frameSize.height = ownerSize.height;
    if (frameSize.width > ownerSize.width)
      frameSize.width = ownerSize.width;
    setLocation(ownerLocation.x + (ownerSize.width - frameSize.width) / 2,
                ownerLocation.y + (ownerSize.height - frameSize.height) / 2);
  }//initGuiComponents

  /**
    * This one initializes the listeners fot the GUI components
    */
  public void initListeners(){

   createResourceButton.addActionListener(new java.awt.event.ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        doCreateResource();
      }
   });

   cancelButton.addActionListener(new java.awt.event.ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        thisBootStrapDialog.setVisible(false);
        BootStrapDialog.this.dispose();
      }
   });

   helpButton.addActionListener(new java.awt.event.ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        MainFrame.getInstance().showHelpFrame(
          "http://gate.ac.uk/userguide/sec:api:bootstrap",
          "gate.gui.BootStrapDialog");
      }
   });

   resourceTypesComboBox.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        String selectedItem =(String) resourceTypesComboBox.getSelectedItem();
        possibleInterfaces = resourceTypes.get(selectedItem);
        interfacesTextField.setText(possibleInterfaces);
      }
   });

   chooseFolderButton.addActionListener(new java.awt.event.ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        // choose folder code
        fileChooser.setDialogTitle("Select the path for this resource");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION){
          pathNewProject = fileChooser.getSelectedFile().toString();
          fileChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());
        }// End if
        chooseFolderTextField.setText(pathNewProject);

      }//actionPerformed
   });

    // define keystrokes action bindings at the level of the main window
    InputMap inputMap = ((JComponent)this.getContentPane()).
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = ((JComponent)this.getContentPane()).getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Apply");
    actionMap.put("Apply", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        createResourceButton.doClick();
      }
    });
    inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "Cancel");
    actionMap.put("Cancel", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cancelButton.doClick();
      }
    });
    inputMap.put(KeyStroke.getKeyStroke("F1"), "Help");
    actionMap.put("Help", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        helpButton.doClick();
      }
    });
  }//initListeners


  /** It returns the interfaces the resource implements*/
  public Set<String> getSelectedInterfaces(){
    String interfaces = interfacesTextField.getText();
    resourceInterfaces = new HashSet<String>();
    if (interfaces == null || "".equals(interfaces))
        return resourceInterfaces;
    StringTokenizer tokenizer = new StringTokenizer(interfaces,",");
    while (tokenizer.hasMoreElements()){
      String token = tokenizer.nextToken();
      resourceInterfaces.add(token);
    }// end While
    return resourceInterfaces;
  }//getSelectedInterfaces

  /**Class used to run an annot. diff in a new thread*/
  class CreateResourceRunner implements Runnable{

    public CreateResourceRunner(){
    }// CreateResourceRunner()

    @Override
    public void run(){


      try{
        bootStrapWizard = new BootStrap();
        bootStrapWizard.createResource(resourceName,
                                       packageName,
                                       resourceType,
                                       className,
                                       resourceInterfaces,
                                       pathNewProject);
        thisBootStrapDialog.setVisible(false);
        thisBootStrapDialog.dispose();
        JOptionPane.showMessageDialog(mainFrame,
                                      resourceName + " creation succeeded !\n" +
                                      "Look for it in " + pathNewProject,
                                      "DONE !",
                                      JOptionPane.DEFAULT_OPTION);
      }catch (Exception e){
        thisBootStrapDialog.setModal(false);
        e.printStackTrace(Err.getPrintWriter());
        JOptionPane.showMessageDialog(mainFrame,
                     e.getMessage() + "\n Resource creation stopped !",
                     "BootStrap error !",
                     JOptionPane.ERROR_MESSAGE);
        thisBootStrapDialog.setModal(true);
      } //End try
    }// run();
  }//CreateResourceRunner

}//BootStrapDialog