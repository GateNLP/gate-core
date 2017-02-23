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
 *  $Id: FeatureMapEditorDialog.java 20054 2017-02-02 06:44:12Z markagreenwood $
 *
 */

package gate.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.util.*;

/**
 * A simple editor for List values.
 */
@SuppressWarnings("serial")
public class FeatureMapEditorDialog extends JDialog {

  /**
   * Contructs a new FeatureMapEditorDialog.
   * 
   * @param owner the component this dialog will be centred on.
   * @param data a feature map with the initial values. This map will
   *          not be changed, its values will be cached and if the user
   *          selects the OK option a new map with the updated contents
   *          will be returned.
   */
  public FeatureMapEditorDialog(Component owner, FeatureMap data) {
    super(MainFrame.getInstance());
    setLocationRelativeTo(owner);
    initLocalData(data);
    initGuiComponents();
    initListeners();
  }

  protected void initLocalData(FeatureMap data) {
    if(data != null) {
      FeatureMap fm = Factory.newFeatureMap();
      fm.putAll(data);
      tempFMHolder.setFeatures(fm);
    }
  }

  protected void initGuiComponents() {
    this.setMinimumSize(new Dimension(150, 300));
    getContentPane().setLayout(new BorderLayout());

    // create the FeaturesSchemaEditor for the main body of the dialog
    fmView = new FeaturesSchemaEditor();
    try {
      fmView.init();
    }
    catch(ResourceInstantiationException rie) {
      // can't happen, but needs to be caught to satisfy the compiler
      throw new GateRuntimeException("FeaturesSchemaEditor.init() threw "
              + "ResourceInstantiationException!", rie);
    }
    fmView.setTarget(tempFMHolder);
    // make sure the window is a sensible size
    Dimension preferredSize = fmView.getPreferredSize();
    if(preferredSize.height < 150) {
      preferredSize.height = 150;
    }
    else if(preferredSize.height > 300) {
      preferredSize.height = 300;
    }
    fmView.setPreferredSize(preferredSize);
    JPanel fmViewPanel = new JPanel(new BorderLayout());
    fmViewPanel.add(fmView, BorderLayout.CENTER);
    fmViewPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    getContentPane().add(fmViewPanel, BorderLayout.CENTER);

    // the bottom buttons
    Box buttonsBox = Box.createHorizontalBox();
    buttonsBox.add(Box.createHorizontalGlue());
    okButton = new JButton("OK");
    buttonsBox.add(okButton);
    buttonsBox.add(Box.createHorizontalStrut(5));
    cancelButton = new JButton("Cancel");
    buttonsBox.add(cancelButton);
    buttonsBox.add(Box.createHorizontalGlue());
    getContentPane().add(buttonsBox, BorderLayout.SOUTH);
  }

  protected void initListeners() {
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
  }

  /**
   * Make this dialog visible allowing the editing of the list. If the
   * user selects the <b>OK</b> option a new list with the updated
   * contents will be returned; it the <b>Cancel</b> option is selected
   * this method return <tt>null</tt>.
   */
  public FeatureMap showDialog() {
    pack();
    userCancelled = true;
    setModal(true);
    super.setVisible(true);
    return userCancelled ? null : tempFMHolder.getFeatures();
  }

  /**
   * test code
   */
  public static void main(String[] args) {
    try {
      Gate.init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    JFrame frame = new JFrame("Foo frame");

    FeatureMapEditorDialog dialog = new FeatureMapEditorDialog(frame, null);

    frame.setSize(300, 300);
    frame.setVisible(true);
    System.out.println(dialog.showDialog());
  }

  /**
   * A dummy FeatureBearer used to hold the temporary feature map used
   * for the editor.
   */
  FeatureBearer tempFMHolder = new FeatureBearer() {
    private FeatureMap fm;

    @Override
    public void setFeatures(FeatureMap map) {
      fm = map;
    }

    @Override
    public FeatureMap getFeatures() {
      return fm;
    }
  };

  /**
   * The GUI compoenent used to display the feature map.
   */
  FeaturesSchemaEditor fmView;

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
}
