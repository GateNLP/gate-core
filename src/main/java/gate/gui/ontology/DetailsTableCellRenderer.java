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
 *  $Id: DetailsTableCellRenderer.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.ObjectProperty;
import gate.creole.ontology.RDFProperty;
import gate.creole.ontology.Restriction;
import gate.creole.ontology.SymmetricProperty;
import gate.creole.ontology.TransitiveProperty;
import gate.gui.MainFrame;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A Class that specifies how each node in the details panel should look
 * like.
 * 
 * @author niraj
 * 
 */
public class DetailsTableCellRenderer extends DefaultTableCellRenderer {
  private static final long serialVersionUID = 3257572784619337525L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(
      table, value, isSelected, hasFocus, row, column);
    setText(null);
    setToolTipText(null);
    setIcon(null);
    setEnabled(true);

    if(column == DetailsTableModel.EXPANDED_COLUMN) {
      if (value == null) {
        // render nothing
      } else if (value.equals("empty")) {
        setEnabled(false);
        setIcon(MainFrame.getIcon("closed"));
      } else {
        setEnabled(true);
        setIcon(MainFrame.getIcon((String) value));
      }
    }
    else if(column == DetailsTableModel.LABEL_COLUMN) {
      if(value instanceof DetailsGroup) {
        DetailsGroup detailsgroup = (DetailsGroup) value;
        setText(detailsgroup.getName());
        setEnabled(detailsgroup.getSize() > 0);
      }
      else if(value instanceof KeyValuePair) {
        KeyValuePair kvp = (KeyValuePair) value;
        setIcon(MainFrame.getIcon("empty"));
        setText(kvp.getKey());
      }
      else if(value instanceof Restriction) {
        OClass tclass = (OClass) value;
        setIcon(MainFrame.getIcon("ontology-restriction"));
        setText(tclass.getName());
        setToolTipText(tclass.getONodeID().toString());
      }
      else if(value instanceof OClass) {
        OClass tclass = (OClass) value;
        setIcon(MainFrame.getIcon("ontology-class"));
        setText(tclass.getName());
        setToolTipText(tclass.getONodeID().toString());
      }
      else if(value instanceof OInstance) {
        OInstance oinstance = (OInstance) value;
        setIcon(MainFrame.getIcon("ontology-instance"));
        setText(oinstance.getName());
        setToolTipText(oinstance.getONodeID().toString());
      }
      else if(value instanceof RDFProperty) {
        RDFProperty property = (RDFProperty) value;
        String propertyType = "RDF";
        if(property instanceof SymmetricProperty) {
          setIcon(MainFrame.getIcon("ontology-symmetric-property"));
          propertyType = "Symmetric";
        }
        else if(property instanceof AnnotationProperty) {
          setIcon(MainFrame.getIcon("ontology-annotation-property"));
          propertyType = "Annotation";
        }
        else if(property instanceof TransitiveProperty) {
          setIcon(MainFrame.getIcon("ontology-transitive-property"));
          propertyType = "Transitive";
        }
        else if(property instanceof ObjectProperty) {
          setIcon(MainFrame.getIcon("ontology-object-property"));
          propertyType = "Object";
        }
        else if(property instanceof DatatypeProperty) {
          setIcon(MainFrame.getIcon("ontology-datatype-property"));
          propertyType = "Datatype";
        }
        else setIcon(MainFrame.getIcon("ontology-rdf-property"));
        String s = property.getName();
        setText(s);
        setToolTipText((new StringBuilder()).append(
                "<HTML><b>" + propertyType + " Property</b><br>").append(
                property.getONodeID()).append("</html>").toString());
      }
      else if(value instanceof PropertyValue) {

        PropertyValue property = (PropertyValue) value;
        String propertyType = "RDF";
        if(property.getProperty() instanceof SymmetricProperty) {
          setIcon(MainFrame.getIcon("ontology-symmetric-property"));
          propertyType = "Symmetric";
        }
        else if(property.getProperty() instanceof AnnotationProperty) {
          setIcon(MainFrame.getIcon("ontology-annotation-property"));
          propertyType = "Annotation";
        }
        else if(property.getProperty() instanceof TransitiveProperty) {
          setIcon(MainFrame.getIcon("ontology-transitive-property"));
          propertyType = "Transitive";
        }
        else if(property.getProperty() instanceof ObjectProperty) {
          setIcon(MainFrame.getIcon("ontology-object-property"));
          propertyType = "Object";
        }
        else if(property.getProperty() instanceof DatatypeProperty) {
          setIcon(MainFrame.getIcon("ontology-datatype-property"));
          propertyType = "Datatype";
        }
        else {
          setIcon(MainFrame.getIcon("ontology-rdf-property"));
        }
        String s = property.getProperty().getName();
        setText(s);
        setToolTipText((new StringBuilder()).append(
                "<HTML><b>" + propertyType + " Property Value</b><br>")
                .append(property.getProperty().getONodeID()).append("</html>")
                .toString());
      }
    }
    else if(column == DetailsTableModel.VALUE_COLUMN) {
      if(value == null || value.equals("")) { return this; }
      setText(value.toString());
      Object property = table.getValueAt(row, DetailsTableModel.LABEL_COLUMN);
       if(property instanceof PropertyValue) {
        setToolTipText("Double-click to edit the value");
      }
      else if(property instanceof RDFProperty) {
        setToolTipText("Double-click to add a property value");
      }
    }
    else if(column == DetailsTableModel.DELETE_COLUMN) {
      if(value instanceof PropertyValue) {
        setIcon(MainFrame.getIcon("delete"));
      }
      else {
        setEnabled(false);
      }
    }
    else {
      setEnabled(false);
    }
    return this;
  }
}
