/*
 * MinCardinalityRestriction.java
 * 
 * $Id: MinCardinalityRestriction.java 15002 2012-01-10 21:18:26Z markagreenwood $
 */
package gate.creole.ontology;

/**
 * A MinCardinalityRestriction.
 * 
 * @author Niraj Aswani
 */
public interface MinCardinalityRestriction extends Restriction {
  
  /**
   * This method returns the minimum cardinality value allowed for this value.
   */
  public String getValue();

  /**
   * This method returns the datatype associated to the restriction.
   */
  public DataType getDataType();

  /**
   * Sets the cardinality value.
   * @throws InvalidValueException
   */
  public void setValue(String value, DataType dataType)
      throws InvalidValueException;
}
