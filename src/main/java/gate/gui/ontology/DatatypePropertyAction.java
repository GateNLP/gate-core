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
 *  $Id: DatatypePropertyAction.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.DataType;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OResource;
import gate.creole.ontology.Ontology;
import gate.gui.MainFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Action to create a new datatype property.
 */
public class DatatypePropertyAction extends AbstractAction implements
                                                          TreeNodeSelectionListener {
  private static final long serialVersionUID = 3257852073457235252L;

  public DatatypePropertyAction(String s, Icon icon) {
    super(s, icon);

    mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(3, 3, 3, 3);
    gbc.anchor = GridBagConstraints.WEST;

    mainPanel.add(new JLabel("Name Space:"), gbc);
    mainPanel.add(nameSpace = new JTextField(30), gbc);

    gbc.gridy = 1;
    mainPanel.add(new JLabel("Data Type:"), gbc);
    mainPanel.add(datatypesComboBox = new JComboBox<String>(), gbc);
    mainPanel.add(datatypesComboBox, gbc);

    gbc.gridy = 2;
    mainPanel.add(new JLabel("Property Name:"), gbc);
    mainPanel.add(propertyName = new JTextField(30), gbc);
    mainPanel.add(domainButton = new JButton("Domain"), gbc);

    datatypesComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
        "http://www.w3.org/2001/XMLSchema#boolean",
        "http://www.w3.org/2001/XMLSchema#byte",
        "http://www.w3.org/2001/XMLSchema#date",
        "http://www.w3.org/2001/XMLSchema#decimal",
        "http://www.w3.org/2001/XMLSchema#double",
        "http://www.w3.org/2001/XMLSchema#duration",
        "http://www.w3.org/2001/XMLSchema#float",
        "http://www.w3.org/2001/XMLSchema#int",
        "http://www.w3.org/2001/XMLSchema#integer",
        "http://www.w3.org/2001/XMLSchema#long",
        "http://www.w3.org/2001/XMLSchema#negativeInteger",
        "http://www.w3.org/2001/XMLSchema#nonNegativeInteger",
        "http://www.w3.org/2001/XMLSchema#nonPositiveInteger",
        "http://www.w3.org/2001/XMLSchema#positiveInteger",
        "http://www.w3.org/2001/XMLSchema#short",
        "http://www.w3.org/2001/XMLSchema#string",
        "http://www.w3.org/2001/XMLSchema#time",
        "http://www.w3.org/2001/XMLSchema#unsignedByte",
        "http://www.w3.org/2001/XMLSchema#unsignedInt",
        "http://www.w3.org/2001/XMLSchema#unsignedLong",
        "http://www.w3.org/2001/XMLSchema#unsignedShort"}));
    domainAction = new ValuesSelectionAction();
    domainButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionevent) {
        String as[] = new String[ontologyClassesURIs.size()];
        for(int i = 0; i < as.length; i++)
          as[i] = ontologyClassesURIs.get(i);
        ArrayList<String> arraylist = new ArrayList<String>();
        for(int j = 0; j < selectedNodes.size(); j++) {
          DefaultMutableTreeNode node = selectedNodes.get(j);
          OResource res = ((OResourceNode)node.getUserObject()).getResource();
          if(res instanceof OClass) {
            arraylist.add(res.getONodeID().toString());
          }
        }
        String as1[] = new String[arraylist.size()];
        for(int k = 0; k < as1.length; k++) {
          as1[k] = arraylist.get(k);
        }
        domainAction.showGUI("Domain", as, as1, false,
          MainFrame.getIcon("ontology-datatype-property"));
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
      MainFrame.getIcon("ontology-datatype-property")) {
      @Override
      public void selectInitialValue() {
        propertyName.requestFocusInWindow();
        propertyName.selectAll();
      }
    };
    pane.createDialog(MainFrame.getInstance(),
      "New Datatype Property").setVisible(true);
    Object selectedValue = pane.getValue();
    if (selectedValue != null
    && selectedValue instanceof Integer
    && (Integer) selectedValue == JOptionPane.OK_OPTION) {
      String s = nameSpace.getText();
      if(!Utils.isValidNameSpace(s)) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid Name Space: " + s + "\nExample: http://gate.ac.uk/example#");
        return;
      }
      if(!Utils.isValidOntologyResourceName(propertyName.getText())) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
          "Invalid Property Name: " + propertyName.getText());
        return;
      }
      if(Utils.getOResourceFromMap(ontology,s + propertyName.getText()) != null) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),"<html>" +
          "Resource <b>" + s+propertyName.getText() + "</b> already exists.");
        return;
      }
      String as[] = domainAction.getSelectedValues();
      HashSet<OClass> hashset = new HashSet<OClass>();
      for (String a : as) {
        OClass oclass = (OClass) Utils.getOResourceFromMap(ontology,a);
        hashset.add(oclass);
      }
      DataType dt = DataType.getDataType((String)
        datatypesComboBox.getSelectedItem());
      ontology.addDatatypeProperty(ontology.createOURI(nameSpace.getText()
        + propertyName.getText()), hashset, dt);
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

  public List<String> getOntologyClassesURIs() {
    return ontologyClassesURIs;
  }

  public void setOntologyClassesURIs(ArrayList<String> arraylist) {
    ontologyClassesURIs = arraylist;
  }

  protected JPanel mainPanel;
  protected JTextField nameSpace;
  protected JComboBox<String> datatypesComboBox;
  protected JButton domainButton;
  protected JTextField propertyName;
  protected ValuesSelectionAction domainAction;
  protected List<String> ontologyClassesURIs;
  protected List<DefaultMutableTreeNode> selectedNodes;
  protected Ontology ontology;
}
