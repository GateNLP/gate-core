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
 *  $Id: SymmetricPropertyAction.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.*;
import gate.gui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Action to create a new symmetric property in the ontology.
 */
public class SymmetricPropertyAction extends AbstractAction implements
                                                           TreeNodeSelectionListener {
  private static final long serialVersionUID = 3257286915891017008L;

  public SymmetricPropertyAction(String s, Icon icon) {
    super(s, icon);

    mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(3, 3, 3, 3);
    gbc.anchor = GridBagConstraints.WEST;

    mainPanel.add(new JLabel("Name Space:"), gbc);
    mainPanel.add(nameSpace = new JTextField(30), gbc);

    gbc.gridy = 1;
    mainPanel.add(new JLabel("Property Name:"), gbc);
    mainPanel.add(propertyName = new JTextField(30), gbc);
    mainPanel.add(domainRangeButton = new JButton("Domain and Range"), gbc);

    domainRangeAction = new ValuesSelectionAction();
    domainRangeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionevent) {
        String as[] = new String[ontologyClassesURIs.size()];
        for(int i = 0; i < as.length; i++)
          as[i] = ontologyClassesURIs.get(i);
        ArrayList<String> arraylist = new ArrayList<String>();
        for(int j = 0; j < selectedNodes.size(); j++) {
          DefaultMutableTreeNode defaultmutabletreenode = selectedNodes.get(j);
          if(((OResourceNode)defaultmutabletreenode.getUserObject())
                  .getResource() instanceof OClass)
            arraylist.add((((OResourceNode)defaultmutabletreenode
                    .getUserObject()).getResource()).getONodeID().toString());
        }
        String as1[] = new String[arraylist.size()];
        for(int k = 0; k < as1.length; k++)
          as1[k] = arraylist.get(k);
        domainRangeAction.showGUI("Domain and Range", as, as1, false,
          MainFrame.getIcon("ontology-symmetric-property"));
      }
    });
  }

  @Override
  public void actionPerformed(ActionEvent actionevent) {
    nameSpace.setText(ontology.getDefaultNameSpace() == null ?
      "http://gate.ac.uk/example#" : ontology.getDefaultNameSpace());
    @SuppressWarnings("serial")
    JOptionPane pane = new JOptionPane(mainPanel, JOptionPane.QUESTION_MESSAGE,
      JOptionPane.OK_CANCEL_OPTION,
      MainFrame.getIcon("ontology-symmetric-property")) {
      @Override
      public void selectInitialValue() {
        propertyName.requestFocusInWindow();
        propertyName.selectAll();
      }
    };
    pane.createDialog(MainFrame.getInstance(),
      "New Symmetric Property").setVisible(true);
    Object selectedValue = pane.getValue();
    if (selectedValue != null
    && selectedValue instanceof Integer
    && (Integer) selectedValue == JOptionPane.OK_OPTION) {
      String s = nameSpace.getText();
      if(!Utils.isValidNameSpace(s)) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid NameSpace: " + s + "\n Example: http://gate.ac.uk/example#");
        return;
      }
      if(!Utils.isValidOntologyResourceName(propertyName.getText())) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid Symmetric Property Name: " + propertyName.getText());
        return;
      }
      if(Utils.getOResourceFromMap(ontology,nameSpace.getText()
              + propertyName.getText()) != null) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),"<html>" +
          "Resource <b>" + s+propertyName.getText() + "</b> already exists.");
        return;
      }
      String domainSelectedValues[] = domainRangeAction.getSelectedValues();
      HashSet<OClass> domainSet = new HashSet<OClass>();
      for(int j = 0; j < domainSelectedValues.length; j++) {
        OClass oclass = (OClass)
          Utils.getOResourceFromMap(ontology,domainSelectedValues[j]);
        domainSet.add(oclass);
      }
      ontology.addSymmetricProperty(ontology.createOURI(nameSpace.getText()
        + propertyName.getText()), domainSet);
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

  public ArrayList<String> getOntologyClassesURIs() {
    return ontologyClassesURIs;
  }

  public void setOntologyClassesURIs(ArrayList<String> arraylist) {
    ontologyClassesURIs = arraylist;
  }

  protected JPanel mainPanel;
  protected JTextField nameSpace;
  protected JTextField propertyName;
  protected JButton domainRangeButton;
  protected ValuesSelectionAction domainRangeAction;
  protected ArrayList<String> ontologyClassesURIs;
  protected ArrayList<DefaultMutableTreeNode> selectedNodes;
  protected Ontology ontology;
}
