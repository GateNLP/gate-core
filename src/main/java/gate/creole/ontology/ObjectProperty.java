/*
 *  ObjectProperty.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: ObjectProperty.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

import java.util.Set;

/**
 * ObjectProperty is a sub type of the RDFProperty. This property takes
 * a set of OClasses as its domain and range. The property can be then
 * assigned to an instance, where the subject instance must belongs to
 * all the OClasses (Transitive closure) specified in the domain and the
 * object instance must belongs to all the OClass (Transitive Closure)
 * specified in the range.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
@SuppressWarnings("deprecation")
public interface ObjectProperty extends RDFProperty {
  /**
   * Returns the set of inverse properties for this property. 
   * 
   * @return a {@link Set} of {@link ObjectProperty} value.
   */
  public Set<ObjectProperty> getInverseProperties();

  /**
   * Set theInverse as inverse property to this property.
   * 
   * @param theInverse
   */
  public void setInverseOf(ObjectProperty theInverse);

  /**
   * Returns the set of domain restrictions for this property.
   */
  @Override
  public Set<OResource> getDomain();

  /**
   * Gets the set of range restrictions for this property.
   * 
   * @return a set of {@link OClass} or {@link Class} objects.
   */
  @Override
  public Set<OResource> getRange();

  /**
   * Checks whether the provided instance is compatible with the range
   * restrictions on the property.
   * 
   * @param anInstance the Instance
   * @return true if this instance is compatible with the range
   *         restrictions on the property. False otherwise.
   */
  public boolean isValidRange(OInstance anInstance);

  /**
   * Checks whether the provided instance is compatible with the domain
   * restrictions on the property.
   * 
   * @param anInstance the Instance
   * @return true if this instance is compatible with the domain
   *         restrictions on the property. False otherwise.
   */
  public boolean isValidDomain(OInstance anInstance);

}
