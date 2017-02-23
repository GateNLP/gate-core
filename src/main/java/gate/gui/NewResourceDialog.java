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
 *  $Id: NewResourceDialog.java 18933 2015-10-02 13:19:21Z markagreenwood $
 *
 */

package gate.gui;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.util.Err;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class NewResourceDialog extends JDialog {

  public NewResourceDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal/*, frame.getGraphicsConfiguration()*/);
    MainFrame.getGuiRoots().add(this);
    initLocalData();
    initGuiComponents();
    initListeners();
  }


  protected void initLocalData(){
    // nothing
  }

  protected void initGuiComponents(){
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),
                                                  BoxLayout.Y_AXIS));

    //name field
    Box nameBox = Box.createHorizontalBox();
    nameBox.add(Box.createHorizontalStrut(5));
    nameBox.add(new JLabel("Name: "));
    nameBox.add(Box.createHorizontalStrut(5));
    nameField = new JTextField(30);
    nameField.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
    nameField.setRequestFocusEnabled(true);
    nameField.selectAll();
    nameField.setVerifyInputWhenFocusTarget(false);
    nameBox.add(nameField);
    nameField.setToolTipText("Enter a name for the resource");
    
    nameBox.add(Box.createHorizontalStrut(5));
    nameBox.add(Box.createHorizontalGlue());
    this.getContentPane().add(nameBox);
    this.getContentPane().add(Box.createVerticalStrut(5));

    //parameters table
    parametersEditor = new ResourceParametersEditor();
    tableScroll = new JScrollPane(parametersEditor);
    this.getContentPane().add(tableScroll);
    this.getContentPane().add(Box.createVerticalStrut(5));
    this.getContentPane().add(Box.createVerticalGlue());
    //buttons box
    JPanel buttonsBox = new JPanel();
    buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.X_AXIS));
    buttonsBox.add(Box.createHorizontalStrut(10));
    buttonsBox.add(okBtn = new JButton("OK"));
    buttonsBox.add(Box.createHorizontalStrut(10));
    buttonsBox.add(cancelBtn = new JButton("Cancel"));
    buttonsBox.add(Box.createHorizontalStrut(10));
    buttonsBox.add(helpBtn = new JButton("Help"));
    buttonsBox.add(Box.createHorizontalStrut(10));
    this.getContentPane().add(buttonsBox);
    this.getContentPane().add(Box.createVerticalStrut(5));
    setSize(400, 300);

    getRootPane().setDefaultButton(okBtn);
  }// protected void initGuiComponents()


  protected void initListeners(){
    Action applyAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        userCanceled = false;
        TableCellEditor cellEditor = parametersEditor.getCellEditor();
        if(cellEditor != null){
          cellEditor.stopCellEditing();
        }
        setVisible(false);
      }
    };
    Action helpAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().showHelpFrame(resourceData.getHelpURL(),
          resourceData.getClassName());
      }
    };
    Action cancelAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        userCanceled = true;
        setVisible(false);
      }
    };

    okBtn.addActionListener(applyAction);
    helpBtn.addActionListener(helpAction);
    cancelBtn.addActionListener(cancelAction);

    // disable Enter key in the table so this key will confirm the dialog
    InputMap im = parametersEditor.getInputMap(
      JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    im.put(enter, "none");

    // define keystrokes action bindings at the level of the main window
    InputMap inputMap = ((JComponent)this.getContentPane()).
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap =
      ((JComponent)this.getContentPane()).getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Apply");
    actionMap.put("Apply", applyAction);
    inputMap.put(KeyStroke.getKeyStroke("F1"), "Help");
    actionMap.put("Help", helpAction);
    inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "Cancel");
    actionMap.put("Cancel", cancelAction);
  }

  JButton okBtn, helpBtn, cancelBtn;
  JTextField nameField;
  ResourceParametersEditor parametersEditor;
  JScrollPane tableScroll;
  ResourceData resourceData;
  Resource resource;

  boolean userCanceled;

  /** This method is intended to be used in conjunction with
    * getSelectedParameters(). The method will not instantiate the resource
    * like {@link #show(ResourceData)} but it is intended to collect the params
    * required to instantiate a resource. Returns true if the user pressed OK
    * and false otherwise.
    */
  public synchronized boolean show(ResourceData rData, String aTitle) {
    this.resourceData = rData;
    if (aTitle != null) setTitle(aTitle);

    parametersEditor.init(null,
                          rData.getParameterList().getInitimeParameters());
    pack();
    nameField.requestFocusInWindow();
    userCanceled = true;
    setModal(true);
    setLocationRelativeTo(getOwner());
    super.setVisible(true);
    dispose();
    if(userCanceled) return false;
    else return true;
  }//show();

  /** Returns the selected params for the resource or null if none was selected
    * or the user pressed cancel
    */
  public FeatureMap getSelectedParameters(){
    if (parametersEditor != null)
      return parametersEditor.getParameterValues();
    else
      return null;
  }// getSelectedParameters()

  /**
   * Return the String entered into the resource name field of the dialog. 
   */
  public String getResourceName() {
    return nameField.getText();
  }
  
  public synchronized void show(ResourceData rData) {
    if(rData != null) {
      this.resourceData = rData;

      String name = "";
      try {
        // Only try to generate a name for a PR - other types should be named after what they contain, really!
        if (ProcessingResource.class.isAssignableFrom(rData.getResourceClass())) {
          name = rData.getName() + " " + Gate.genSym();
        }
      } catch (ClassNotFoundException e){
        Err.getPrintWriter().println("Couldn't load input resource class when showing dialogue.");
      }
      nameField.setText(name);

      parametersEditor.init(null, rData,
                            rData.getParameterList().getInitimeParameters());
      pack();
      setLocationRelativeTo(getOwner());
    } else {
      // dialog already populated
    }
    //default case when the dialog just gets closed 
    userCanceled = true;
    //show the dialog
    setVisible(true);
    if(userCanceled){
      //release resources
      dispose();
      return;
    } else {
      Runnable runnable = new Runnable(){
        @Override
        public void run(){
          //create the new resource
          FeatureMap params = parametersEditor.getParameterValues();

          gate.event.StatusListener sListener =
            (gate.event.StatusListener)Gate.getListeners().
                                       get("gate.event.StatusListener");
          if(sListener != null) sListener.statusChanged("Loading " +
                                                        nameField.getText() +
                                                        "...");

          gate.event.ProgressListener pListener =
            (gate.event.ProgressListener)Gate.getListeners().
                                         get("gate.event.ProgressListener");
          if(pListener != null){
            pListener.progressChanged(0);
          }
          boolean success = true;
          try {
            long startTime = System.currentTimeMillis();
            FeatureMap features = Factory.newFeatureMap();
            String name = nameField.getText();
            if(name == null || name.length() == 0) name = null;
            Factory.createResource(resourceData.getClassName(), params,
                                         features, name);
            long endTime = System.currentTimeMillis();
            if(sListener != null) sListener.statusChanged(
                nameField.getText() + " loaded in " +
                NumberFormat.getInstance().format(
                (double)(endTime - startTime) / 1000) + " seconds");
            if(pListener != null) pListener.processFinished();
          } catch(ResourceInstantiationException rie){
            success = false;
            JOptionPane.showMessageDialog(getOwner(),
                                          "Resource could not be created!\n" +
                                          rie.toString(),
                                          "GATE", JOptionPane.ERROR_MESSAGE);
            rie.printStackTrace(Err.getPrintWriter());
            
            if(sListener != null) sListener.statusChanged("Error loading " +
                                                          nameField.getText() +
                                                          "!");
            if(pListener != null) pListener.processFinished();
          }catch(Throwable thr){
            success = false;
            JOptionPane.showMessageDialog(getOwner(),
                    "Unhandled error!\n" +
                    thr.toString(),
                    "GATE", JOptionPane.ERROR_MESSAGE);
            thr.printStackTrace(Err.getPrintWriter());
            if(sListener != null) sListener.statusChanged("Error loading " +
                                                nameField.getText() +
                                                "!");
            if(pListener != null) pListener.processFinished();
          } finally {
            if(!success) {
              // re-show the dialog, to allow the suer to correct the entry
              SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                  show(null);    
                }
              });
            }
            else {
              dispose();
            }
          }
        }//public void run()
      };
      Thread thread = new Thread(runnable, "");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }
  }// public synchronized Resource show(ResourceData rData)
  
  @Override
  public void dispose() {
    // when we have finished with the dialog release the data about the resource
    // we were trying to create so that, at a later date, we can garbage collect
    // the classloader if the plugin is unloaded
    resourceData = null;
  }

}//class NewResourceDialog