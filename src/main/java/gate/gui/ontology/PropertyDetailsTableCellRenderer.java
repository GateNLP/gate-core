/*
 *  PropertyDetailsTableCellRenderer.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: PropertyDetailsTableCellRenderer.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.*;
import gate.gui.MainFrame;
import java.awt.Component;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A Class that specifies how each node in the details panel should look
 * like.
 * 
 * @author niraj
 * 
 */
public class PropertyDetailsTableCellRenderer extends DefaultTableCellRenderer {
  private static final long serialVersionUID = 3257572784619337525L;

  public PropertyDetailsTableCellRenderer(
          PropertyDetailsTableModel detailstablemodel) {
    propertyDetailsTableModel = detailstablemodel;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj,
          boolean flag, boolean flag1, int i, int j) {
    Component component = super.getTableCellRendererComponent(table, "", flag,
            flag1, i, j);
    try {
      if(j == 0) {
        setText(null);
        if(obj == null) {
          setIcon(null);
        }
        else {
          Object obj1 = propertyDetailsTableModel.getValueAt(i, 1);
          setIcon(MainFrame.getIcon(((Boolean)obj).booleanValue()
                  ? "expanded"
                  : "closed"));
          setEnabled(((DetailsGroup)obj1).getSize() > 0);
        }
      }
      else if(j == 1) {
        if(obj instanceof DetailsGroup) {
          DetailsGroup detailsgroup = (DetailsGroup)obj;
          setIcon(null);
          setFont(getFont().deriveFont(1));
          setText(detailsgroup.getName());
          setEnabled(detailsgroup.getSize() > 0);
        }
        else if(obj instanceof KeyValuePair) {
          KeyValuePair kvp = (KeyValuePair)obj;
          setIcon(MainFrame.getIcon("empty"));
          setFont(getFont().deriveFont(0));
          setText(kvp.getKey());
          setEnabled(true);
        }
        else if(obj instanceof Restriction) {
          OClass tclass = (OClass)obj;
          setIcon(MainFrame.getIcon("ontology-restriction"));
          setFont(getFont().deriveFont(0));
          setText(tclass.getName());
          setToolTipText(tclass.getONodeID().toString());
          setEnabled(true);
        }
        else if(obj instanceof OClass) {
          OClass tclass = (OClass)obj;
          setIcon(MainFrame.getIcon("ontology-class"));
          setFont(getFont().deriveFont(0));
          setText(tclass.getName());
          setToolTipText(tclass.getONodeID().toString());
          setEnabled(true);
        }
        else if(obj instanceof OInstance) {
          OInstance oinstance = (OInstance)obj;
          setIcon(MainFrame.getIcon("ontology-instance"));
          setFont(getFont().deriveFont(0));
          setText(oinstance.getName());
          setToolTipText(oinstance.getONodeID().toString());
          setEnabled(true);
        }
        else if(obj instanceof RDFProperty) {
          RDFProperty property = (RDFProperty)obj;
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
          else {
            setIcon(MainFrame.getIcon("ontology-rdf-property"));
          }
          setFont(getFont().deriveFont(0));
          String s = property.getName();
          setText(s);
          setToolTipText((new StringBuilder()).append(
                  "<HTML><b>" + propertyType + " Property</b><br>").append(
                  property.getONodeID()).append("</html>").toString());
          setEnabled(true);
        }
        else if(obj instanceof PropertyValue) {

          PropertyValue property = (PropertyValue)obj;
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

          setFont(getFont().deriveFont(0));
          String s = property.getProperty().getName();
          setText(s);
          setToolTipText((new StringBuilder()).append(
                  "<HTML><b>" + propertyType + " Property Value</b><br>")
                  .append(property.getProperty().getONodeID()).append("</html>")
                  .toString());
          setEnabled(true);
        }
      }
      else if(j == 2) {
        setIcon(null);
        if(obj instanceof PropertyValue) {
          PropertyValue property = (PropertyValue)obj;
          setFont(getFont().deriveFont(0));
          String s = "";
          if(property.getValue() instanceof Literal) {
            s = ((Literal)property.getValue()).getValue();
          }
          else {
            s = property.getValue().toString();
          }
          setText(s);
          setEnabled(true);
        }
        else if(obj instanceof KeyValuePair) {
          KeyValuePair kvp = (KeyValuePair)obj;
          setIcon(null);
          setFont(getFont().deriveFont(0));
          setText(kvp.getValue().toString());
          setEnabled(true);
        }
        else if(obj instanceof RDFProperty) {
          RDFProperty prop = (RDFProperty)obj;
          String s = "";
          if(prop instanceof DatatypeProperty) {
            s = ((DatatypeProperty)prop).getDataType().getXmlSchemaURIString();
          }
          else if(prop instanceof ObjectProperty) {
            @SuppressWarnings("deprecation")
            Set<OResource> set = prop.getRange();
            if(set == null || set.isEmpty()) {
              s = "[ALL CLASSES]";
            }
            else {
              s = "[";
              boolean firstTime = true;
              for(OResource res : set) {
                if(!firstTime) {
                  s += ",";
                }
                else {
                  firstTime = false;
                }
                s += res.getName();
              }
              s += "]";
            }
          }
          else {
            s = "[ALL RESOURCES]";
          }
          setIcon(null);
          setFont(getFont().deriveFont(0));
          setText(s);
          setEnabled(true);
        }
        else {
          setIcon(null);
          setFont(getFont().deriveFont(0));
          setText("");
          setEnabled(false);
        }
      } else if(j==3) {
        if(obj instanceof PropertyValue) {
          setIcon(MainFrame.getIcon("delete"));
          setText("");
          setEnabled(true);
          setFont(getFont().deriveFont(0));
        } else {
          setIcon(null);
          setText("");
          setEnabled(false);
          setFont(getFont().deriveFont(0));
        }
      }
      else {
        setIcon(null);
        setFont(getFont().deriveFont(0));
        setText("");
        setEnabled(false);
      }
    }
    catch(Exception e) {
      // refreshing errors hiding them
    }
    return component;
  }

  protected PropertyDetailsTableModel propertyDetailsTableModel;
}
