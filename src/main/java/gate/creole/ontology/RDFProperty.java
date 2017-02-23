/*
 *  RDFProperty.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: RDFProperty.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import gate.creole.ontology.OConstants.Closure;
import java.util.Set;

/**
 * RDFProperty is the top level property. Any property is an
 * RDFProperty. Each sub property has their constraints on the type of
 * values they can have for their domain and range. Typically
 * RDFProperties (and not any of their sub properties) can have any
 * OResource as its domain and range.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 */
public interface RDFProperty extends OResource {

  /**
   * Add an equivalentPropertyAs relation between the two properties. Each
   * property has a set of these, so it is possible to have
   * equivalentPropertyAs relation between more than two properties.
   * 
   * @param theProperty
   */
  public void setEquivalentPropertyAs(RDFProperty theProperty);

  /**
   * Returns a set of all RDFProperty instances that are in
   * EquivalentPropertyAs relation with this property. Or null if there
   * are no such properties.
   * 
   * @return a {@link Set} value.
   */
  public Set<RDFProperty> getEquivalentPropertyAs();

  /**
   * Checks whether the property is Equivalent as the one provide.
   * 
   * @param theProperty
   * @return true, if the provided property is same as the one provided,
   *         otherwise - false.
   */
  public boolean isEquivalentPropertyAs(RDFProperty theProperty);

  /**
   * Gets the set of super-properties for this property.
   * 
   * @param closure {@link OConstants#DIRECT_CLOSURE} for direct
   *          super-properties only or
   *          {@link OConstants#TRANSITIVE_CLOSURE} for all the
   *          super-properties.
   * @return a set of {@link RDFProperty} values.
   * @deprecated
   */
  @Deprecated
  public Set<RDFProperty> getSuperProperties(byte closure);

  /**
   * Gets the set of super-properties for this property.
   * 
   * @param closure {@link OConstants.Closure#DIRECT_CLOSURE} for direct
   *          super-properties only or
   *          {@link OConstants.Closure#TRANSITIVE_CLOSURE} for all the
   *          super-properties.
   * @return a set of {@link RDFProperty} values.
   */
  public Set<RDFProperty> getSuperProperties(Closure closure);

  /**
   * Checks whether the property is a super property of the given
   * property.
   * 
   * @param theProperty
   * @param closure either OntologyConstants.DIRECT_CLOSURE or
   *          OntologyConstants.TRANSTIVE_CLOSURE
   * @return true, if the property is a super property of the given
   *         property, otherwise - false.
   */
  @Deprecated
  public boolean isSuperPropertyOf(RDFProperty theProperty, byte closure);

  public boolean isSuperPropertyOf(RDFProperty theProperty, Closure closure);

  /**
   * Add a SuperPropertyOf relation between the given property and this.
   * 
   * @param property
   */
  public void addSubProperty(RDFProperty property);

  /**
   * Removes a SuperPropertyOf relation between the given property and
   * this.
   * 
   * @param property
   */
  public void removeSubProperty(RDFProperty property);

  /**
   * Gets the set of sub-properties for this property.
   * 
   * @param closure {@link OConstants#DIRECT_CLOSURE} for direct sub-properties
   *          only or {@link OConstants#TRANSITIVE_CLOSURE} for all the
   *          sub-properties.
   * @return a set of {@link RDFProperty} values.
   * @deprecated
   */
  @Deprecated
  public Set<RDFProperty> getSubProperties(byte closure);

  /**
   * Gets the set of sub-properties for this property.
   * 
   * @param closure {@link OConstants.Closure#DIRECT_CLOSURE} for direct sub-properties
   *          only or {@link OConstants.Closure#TRANSITIVE_CLOSURE} for all the
   *          sub-properties.
   * @return a set of {@link RDFProperty} values.
   */
  public Set<RDFProperty> getSubProperties(Closure closure);

  /**
   * Checks whether the property is a sub property of the given
   * property.
   * 
   * @param theProperty
   * @param closure either OntologyConstants.DIRECT_CLOSURE or
   *          OntologyConstants.TRANSTIVE_CLOSURE
   * @return true, if the property is a sub property of the given
   *         property, otherwise - false.
   */
  @Deprecated
  public boolean isSubPropertyOf(RDFProperty theProperty, byte closure);

  public boolean isSubPropertyOf(RDFProperty theProperty, Closure closure);

  /**
   * Answers whether this property is a functional property. Functional
   * properties are the ones that can have at most one value for any
   * given value from the domain. Both object properties and datatype
   * properties can be functional.
   * 
   * @return <tt>true</tt> if this property is functional.
   */
  public boolean isFunctional();

  /**
   * Sets the functional property flag on this property.
   * 
   * @param functional <tt>true</tt> iff the property should be marked
   *          as functional.
   */
  public void setFunctional(boolean functional);

  /**
   * Answers whether this property is an inverse functional property.
   * Inverse functional properties are the ones that for any given
   * domain value there can be at most one range value that is valid for
   * this property. Both object properties and datatype properties can
   * be inverse functional.
   * 
   * @return <tt>true</tt> if this property is inverse functional.
   */
  public boolean isInverseFunctional();

  /**
   * Sets the inverse functional property flag on this property.
   * 
   * @param inverseFunctional <tt>true</tt> iff the property should be
   *          marked as inverse functional.
   */
  public void setInverseFunctional(boolean inverseFunctional);

  /**
   * Checks whether the provided resource is compatible with the range
   * restrictions on the property.
   * 
   * @param aResource the Resource
   * @return true if this resource is compatible with the range
   *         restrictions on the property. False otherwise.
   * @deprecated 
   */
  @Deprecated
  public boolean isValidRange(OResource aResource);

  /**
   * Checks whether the provided resource is compatible with the domain
   * restrictions on the property.
   * 
   * @param aResource the Resource
   * @return true if this resource is compatible with the domain
   *         restrictions on the property. False otherwise.
   * @deprecated 
   */
  @Deprecated
  public boolean isValidDomain(OResource aResource);

  /**
   * Returns the set of domain restrictions for this property.
   * @deprecated 
   */
  @Deprecated
  public Set<OResource> getDomain();

  /**
   * Gets the set of range restrictions for this property. If no range
   * has been set it returns an empty set.
   * 
   * @return a set of {@link OClass} or {@link Class} objects.
   * @deprecated
   */
  @Deprecated
  public Set<OResource> getRange();

  /**
   * Get the URI of the property.
   * @return - an OURI object representing the URI of the property.
   */
  public OURI getOURI();
}
