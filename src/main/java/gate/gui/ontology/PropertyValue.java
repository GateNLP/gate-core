/*
 *  PropertyValue.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: PropertyValue.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.Literal;
import gate.creole.ontology.RDFProperty;

/**
 * There are various types of properties (e.g.
 * RDF/Object/Datatype/Annotation etc). Resources with different
 * properties have different types of property values (e.g. Instances
 * for Object properties, string values for annotation/datatype and so
 * on). This class represents a property value and its type.
 * 
 * @author niraj
 * 
 */
public class PropertyValue {

  /**
   * The instance of property for which the value is of type.
   */
  protected RDFProperty property;

  /**
   * The actual value (it can be string or an instance of OResource)
   */
  protected Object value;

  /**
   * Constructor
   * 
   * @param property
   * @param value
   */
  public PropertyValue(RDFProperty property, Object value) {
    this.property = property;
    this.value = value;
  }

  /**
   * Gets the associated property
   */
  public RDFProperty getProperty() {
    return property;
  }

  /**
   * Gets the set value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Returns the string representation (i.e. propertyURI("value")) which
   * is used to show in the right hand side panel of the Ontology
   * Editor.
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer(property.toString());
    sb.append("(");
    if(value instanceof Literal) {
      sb.append(((Literal)value).getValue());
    }
    else {
      sb.append(value.toString());
    }
    sb.append(")");
    return sb.toString();
  }
}
