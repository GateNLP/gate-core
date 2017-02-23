/*
 * HasValueRestriction.java
 *
 * $Id: HasValueRestriction.java 11598 2009-10-13 13:44:17Z johann_p $
 * 
 */
package gate.creole.ontology;

/**
 *  A HasValueRestriction. 
 *
 * @author Niraj Aswani
 *
 */
public interface HasValueRestriction extends Restriction {

  /**
   * Returns the resource which is set as a value  * @return
   */
  public Object getHasValue();

  /**
   * Sets the resource as a restricted value.
   * @param resource
   */
  public void setHasValue(OResource resource);

  /**
   * Sets the literal as a restricted value.
   * @param resource
   */
  public void setHasValue(Literal resource);
  
}
