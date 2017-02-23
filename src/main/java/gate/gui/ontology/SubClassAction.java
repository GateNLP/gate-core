/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: SubClassAction.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.*;
import gate.gui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Action to create a new subclass.
 */
public class SubClassAction extends AbstractAction implements
                                                  TreeNodeSelectionListener {
  private static final long serialVersionUID = 3258409543049359926L;

  public SubClassAction(String s, Icon icon) {
    super(s, icon);
    mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(3, 3, 3, 3);
    gbc.anchor = GridBagConstraints.WEST;

    mainPanel.add(new JLabel("Name Space:"), gbc);
    mainPanel.add(nameSpace = new JTextField(30), gbc);

    gbc.gridy = 1;
    mainPanel.add(new JLabel("Class Name:"), gbc);
    mainPanel.add(className = new JTextField(30), gbc);
  }

  @Override
  @SuppressWarnings("deprecation")
  public void actionPerformed(ActionEvent actionevent) {
    OResource selectedNode = ((OResourceNode)selectedNodes.get(0)
      .getUserObject()).getResource();
    String ns = selectedNode.getONodeID().getNameSpace();
    if(gate.creole.ontology.Utils.hasSystemNameSpace(
      selectedNode.getONodeID().toString())) {
      ns = ontology.getDefaultNameSpace();
    }
    nameSpace.setText(ns);
    ArrayList<OClass> arraylist = new ArrayList<OClass>();
    for(int i = 0; i < selectedNodes.size(); i++) {
      Object obj = ((OResourceNode)selectedNodes.get(i).getUserObject()).getResource();
      if(obj instanceof OClass) arraylist.add((OClass)obj);
    }

    nameSpace.setText(ontology.getDefaultNameSpace() == null ?
      "http://gate.ac.uk/example#" : ontology.getDefaultNameSpace());
    @SuppressWarnings("serial")
    JOptionPane pane = new JOptionPane(mainPanel, JOptionPane.QUESTION_MESSAGE,
      JOptionPane.OK_CANCEL_OPTION, MainFrame.getIcon("ontology-subclass")) {
      @Override
      public void selectInitialValue() {
        className.requestFocusInWindow();
        className.selectAll();
      }
    };
    pane.createDialog(MainFrame.getInstance(),"New Sub Class").setVisible(true);
    Object selectedValue = pane.getValue();
    if (selectedValue != null
    && selectedValue instanceof Integer
    && (Integer) selectedValue == JOptionPane.OK_OPTION) {
      String s = nameSpace.getText();
      if (!Utils.isValidNameSpace(s)) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid Name Space: " + s + "\nExample: http://gate.ac.uk/example#");
        return;
      }
      if(!Utils.isValidOntologyResourceName(className.getText())) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid Class Name: " + className.getText());
        return;
      }
      if(Utils.getOResourceFromMap(ontology,s + className.getText()) != null) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),"<html>" +
          "Resource <b>" + s+className.getText() + "</b> already exists.");
        return;
      }

      OClass oclassimpl =
          ontology.addOClass(ontology.createOURI(s + className.getText()));
      for(int k = 0; k < arraylist.size(); k++) {
        (arraylist.get(k)).addSubClass(oclassimpl);
      }
    }
  }

  public Ontology getOntology() {
    return ontology;
  }

  public void setOntology(Ontology ontology) {
    this.ontology = ontology;
  }

  @Override
  public void selectionChanged(ArrayList<DefaultMutableTreeNode> arraylist) {
    selectedNodes = arraylist;
  }

  protected JTextField nameSpace;
  protected JTextField className;
  protected JPanel mainPanel;
  protected ArrayList<DefaultMutableTreeNode> selectedNodes;
  protected Ontology ontology;
}
