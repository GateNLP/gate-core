/*
 *  ObjectPropertyAction.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: RestrictionAction.java 17865 2014-04-18 08:45:27Z markagreenwood $
 */
package gate.gui.ontology;

import gate.creole.ontology.*;
import gate.gui.MainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Action to create a new ObjectProperty in the ontology
 * 
 * @author niraj
 * 
 */
@SuppressWarnings("serial")
public class RestrictionAction extends AbstractAction {

  public RestrictionAction(String s, Icon icon) {
    super(s, icon);
    mainPanel = new JPanel(new BorderLayout());
    restrictionTypes = new JPanel(new GridLayout(3, 2));

    EnableDisableClass edc = new EnableDisableClass();
    minCard = new JRadioButton("Minimum Cardinality");
    minCard.addActionListener(edc);

    maxCard = new JRadioButton("Maximum Cardinality");
    maxCard.addActionListener(edc);

    card = new JRadioButton("Cardinality");
    card.addActionListener(edc);

    hasVal = new JRadioButton("Has Value");
    hasVal.addActionListener(edc);

    allVals = new JRadioButton("All Values From");
    allVals.addActionListener(edc);

    someVals = new JRadioButton("Some Values From");
    someVals.addActionListener(edc);

    ButtonGroup bg = new ButtonGroup();
    bg.add(minCard);
    bg.add(maxCard);
    bg.add(card);
    bg.add(hasVal);
    bg.add(allVals);
    bg.add(someVals);
    restrictionTypes.add(card);
    restrictionTypes.add(hasVal);
    restrictionTypes.add(minCard);
    restrictionTypes.add(allVals);
    restrictionTypes.add(maxCard);
    restrictionTypes.add(someVals);
    restrictionTypes.setBorder(new TitledBorder("Restriction Types"));

    middlePanel = new JPanel(new FlowLayout());
    middlePanel.setBorder(new TitledBorder("On Property"));
    onPropertyChoice = new JComboBox<RDFProperty>(new DefaultComboBoxModel<RDFProperty>());
    onPropertyChoice.setPrototypeDisplayValue(new RDFPropertyPrototype("http://www.dcs.shef.ac.uk/owlim#SomeObjectProperty"));
    middlePanel.add(onPropertyChoice);

    bottomPanel = new JPanel(new GridLayout(2, 1));
    valuePanel = new JPanel(new FlowLayout());
    valuePanel.setBorder(new TitledBorder("Cardinality Value"));
    value = new JTextField("1", 20);
    valuePanel.add(value);

    hasValuePanel = new JPanel(new FlowLayout());
    hasValuePanel.setBorder(new TitledBorder("Has Value"));
    hasValChoice = new JComboBox<OResource>(new DefaultComboBoxModel<OResource>());
    hasValChoice.setPrototypeDisplayValue(new RDFPropertyPrototype("http://www.dcs.shef.ac.uk/owlim#SomeObjectProperty"));
    hasValuePanel.add(hasValChoice);
    bottomPanel.add(valuePanel);
    bottomPanel.add(hasValuePanel);

    mainPanel.add(restrictionTypes, BorderLayout.NORTH);
    mainPanel.add(middlePanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    card.setSelected(true);
    hasValChoice.setEnabled(false);
  }

  @Override
  @SuppressWarnings("deprecation")
  public void actionPerformed(ActionEvent actionevent) {

    ArrayList<RDFProperty> props = new ArrayList<RDFProperty>();
    props.addAll(ontology.getObjectProperties());
    props.addAll(ontology.getDatatypeProperties());
    Collections.sort(props, new OntologyItemComparator());
    DefaultComboBoxModel<RDFProperty> dcbm = new DefaultComboBoxModel<RDFProperty>(props.toArray(new RDFProperty[props.size()]));
    onPropertyChoice.setModel(dcbm);

    ArrayList<OResource> classes = new ArrayList<OResource>();
    classes.addAll(ontology.getOClasses(false));
    Collections.sort(classes, new OntologyItemComparator());
    DefaultComboBoxModel<OResource> dcbm1 = new DefaultComboBoxModel<OResource>(classes.toArray(new OResource[classes.size()]));
    hasValChoice.setModel(dcbm1);

    int i = JOptionPane.showOptionDialog(MainFrame.getInstance(), mainPanel,
            "New Restriction", 2, 3, MainFrame.getIcon("empty"), new String[] {"OK", "Cancel"}, "OK");
    if(i == 0) {
      RDFProperty onProp = (RDFProperty)onPropertyChoice.getSelectedItem();
      if(!value.isEnabled()) {
        OResource hasValResource = (OResource)hasValChoice.getSelectedItem();
        if(allVals.isSelected()) {
          ontology.addAllValuesFromRestriction(onProp, hasValResource);
        }
        else if(someVals.isSelected()) {
          ontology.addSomeValuesFromRestriction(onProp, hasValResource);
        }
        else {
          ontology.addHasValueRestriction(onProp, hasValResource);
        }
      }
      else {
        // first check if the provided string is a valid datatype
        String number = value.getText();
        if(!DataType.getDataType("http://www.w3.org/2001/XMLSchema#nonNegativeInteger")
                .isValidValue(number)) {
          JOptionPane.showMessageDialog(MainFrame.getInstance(),
                  "Invalid value " + number);
          return;
        }
        try {
          if(card.isSelected()) {
            ontology.addCardinalityRestriction(onProp, number);
          }
          else if(minCard.isSelected()) {
            ontology.addMinCardinalityRestriction(onProp, number);
          }
          else {
            ontology.addMaxCardinalityRestriction(onProp, number);
          }
        }
        catch(InvalidValueException ive) {
          JOptionPane.showMessageDialog(MainFrame.getInstance(), ive
                  .getMessage());
          ive.printStackTrace();
          return;
        }
      }
    }
  }

  public Ontology getOntology() {
    return ontology;
  }

  public void setOntology(Ontology ontology1) {
    ontology = ontology1;
  }

  protected JPanel mainPanel;

  protected JPanel restrictionTypes;

  protected JRadioButton minCard, maxCard, card, hasVal, allVals, someVals;

  protected JPanel middlePanel;

  protected JComboBox<RDFProperty> onPropertyChoice;

  protected JPanel bottomPanel;

  protected JPanel valuePanel, hasValuePanel;

  protected JTextField value;

  protected JComboBox<OResource> hasValChoice;

  protected Ontology ontology;

  class EnableDisableClass implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
      if(hasVal.isSelected() || allVals.isSelected() || someVals.isSelected()) {
        value.setEnabled(false);
        hasValChoice.setEnabled(true);
      }
      else {
        value.setEnabled(true);
        hasValChoice.setEnabled(false);
      }
    }
  }
}
