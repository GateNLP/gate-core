/*
 *  DatatypeProperty.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: DatatypeProperty.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

import java.util.Set;

/**
 * Interface for datatype properties. Datatype properties have as range values
 * datatype values (different from object properties which have instances as
 * values). Values are Java objects.
 *
 * @author Niraj Aswani
 * 
 */
@SuppressWarnings("deprecation")
public interface DatatypeProperty extends RDFProperty {

  /**
   * Returns the set of domain restrictions for this property.
   */
  @Override
  public Set<OResource> getDomain();

    
  /**
   * This method returns the DataType set for this property
   */
  public DataType getDataType();

  /**
   * Checks whether the provided datatype value is compatible with the DataType
   * restrictions on the property.
   * 
   * @param value
   *          the Value
   * @return true if this datatype value is compatible with the DataType restrictions on
   *         the property. False otherwise.
   */
  public boolean isValidDataTypeValue(String value);

  /**
   * Checks whether the provided instance is compatible with the domain
   * restrictions on the property.
   * 
   * @param anInstance
   *          the Instance
   * @return true if this instance is compatible with the domain restrictions on
   *         the property. False otherwise.
   */
  public boolean isValidDomain(OInstance anInstance);

}