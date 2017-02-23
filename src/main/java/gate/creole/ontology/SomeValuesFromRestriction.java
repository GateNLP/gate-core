/**
 * 
 */
package gate.creole.ontology;

/**
 * A SomeValuesFromRestriction.
 *
 * @author Niraj Aswani
 *
 */
public interface SomeValuesFromRestriction extends Restriction {

  /**
   * Returns the resource which is set as a value
   */
  public OResource getHasValue();

  /**
   * Sets the resource as a restricted value.
   */
  public void setHasValue(OResource resource);

}
