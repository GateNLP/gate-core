/*
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Niraj Aswani, 09/March/07
 * 
 * $Id: AnnotationPropertyAction.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.Ontology;
import gate.gui.MainFrame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Action to create a new annotation property.
 */
public class AnnotationPropertyAction extends AbstractAction {
  private static final long serialVersionUID = 3546358452780544048L;

  /**
   * Constructor
   * 
   * @param s
   *          - Label assigned to the Button
   * @param icon
   *          - Icon assigned to the Button
   */
  public AnnotationPropertyAction(String s, Icon icon) {
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
  }

  @SuppressWarnings("serial")
  @Override
  public void actionPerformed(ActionEvent actionevent) {
    nameSpace.setText(ontology.getDefaultNameSpace() == null
        ? "http://gate.ac.uk/example#"
        : ontology.getDefaultNameSpace());
    JOptionPane pane =
        new JOptionPane(mainPanel, JOptionPane.QUESTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION,
            MainFrame.getIcon("ontology-annotation-property")) {
          @Override
          public void selectInitialValue() {
            propertyName.requestFocusInWindow();
            propertyName.selectAll();
          }
        };
    pane.createDialog(MainFrame.getInstance(), "New Annotation Property")
        .setVisible(true);
    Object selectedValue = pane.getValue();
    if(selectedValue != null && selectedValue instanceof Integer
        && (Integer)selectedValue == JOptionPane.OK_OPTION) {
      String s = nameSpace.getText();
      if(!Utils.isValidNameSpace(s)) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
            "Invalid Name Space: " + s
                + "\nExample: http://gate.ac.uk/example#");
        return;
      }
      if(!Utils.isValidOntologyResourceName(propertyName.getText())) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
            "Invalid Property Name: " + propertyName.getText());
        return;
      }
      if(Utils.getOResourceFromMap(ontology, s + propertyName.getText()) != null) {
        JOptionPane.showMessageDialog(MainFrame.getInstance(), "<html>"
            + "Resource <b>" + s + propertyName.getText()
            + "</b> already exists.");
        return;
      }

      ontology.addAnnotationProperty(ontology.createOURI(nameSpace.getText()
          + propertyName.getText()));
    }
  }

  /**
   * @return the associated ontology
   */
  public Ontology getOntology() {
    return ontology;
  }

  /**
   * Specifies the ontology that should be used to add/remove resource to/from.
   */
  public void setOntology(Ontology ontology) {
    this.ontology = ontology;
  }

  protected JPanel mainPanel;

  protected JTextField nameSpace;

  protected JTextField propertyName;

  protected Ontology ontology;
}
