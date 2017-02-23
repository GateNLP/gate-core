/*
 *  OResource.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OResource.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This is the top level interface for all ontology resources such as
 * classes, instances and properties.
 *
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
public interface OResource  {
  /**
   * Gets the URI of the resource.
   * 
   * @return the URI.
   * @deprecated 
   */
  @Deprecated
  public URI getURI();

  public ONodeID getONodeID();

  /**
   * Sets the URI of the resource
   * 
   * @param uri
   * @deprecated
   */
  @Deprecated
  public void setURI(URI uri);

  /**
   * This method returns a set of labels specified on this resource.
   */
  public Set<Literal> getLabels();

  /**
   * This method returns a set of comments specified on this resource.
   * @deprecated
   */
  @Deprecated
  public Set<Literal> getComments();

  /**
   * Gets the comment set on the resource in the specified language.
   * Returns null if no comment found for the specified language.
   * 
   * @param language (@see OConstants for available locales)
   * @return the comment of the resource
   * @deprecated
   */
  @Deprecated
  public String getComment(Locale language);

  /**
   * Sets the comment for the resource with the specified language.
   * 
   * @param aComment the comment to be set.
   * @param Locale
   * @deprecated
   */
  @Deprecated
  public void setComment(String aComment, Locale Locale);

  /**
   * Gets the comment set on the resource in the specified language.
   * Returns null if no comment found for the specified language.
   * 
   * @param language
   * @return the label of the resource
   * @deprecated
   */
  @Deprecated
  public String getLabel(Locale language);

  /**
   * Sets the label for the resource with the specified language.
   * 
   * @param aLabel the label to be set.
   * @param language the anguage of the label. (@see OConstants for
   *          available locales)
   */
  public void setLabel(String aLabel, Locale language);

  /**
   * Gets resource name. Typically a string after the last '#' or '/'
   * 
   * @return the name of the resource.
   */
  public String getName();

  /**
   * Gets the ontology to which the resource belongs.
   * 
   * @return the {@link Ontology} to which the resource belongs
   */
  public Ontology getOntology();

  /**
   * Adds a new annotation property value and specifies the language.
   * 
   * @param theAnnotationProperty the annotation property
   * @param literal the Literal containing some value
   */
  public void addAnnotationPropertyValue(
          AnnotationProperty theAnnotationProperty, Literal literal);

  /**
   * Gets the list of values for a given property name. Values that are not
   * literals are ignored. 
   * 
   * @param theAnnotationProperty
   * @return a List of {@link Literal}.
   */
  public List<Literal> getAnnotationPropertyValues(
          AnnotationProperty theAnnotationProperty);

  /**
   * This method returns the annotation properties set on this resource.
   */
  public Set<AnnotationProperty> getSetAnnotationProperties();

  /**
   * This method returns all the set properties set on this resource.
   */
  public Set<RDFProperty> getAllSetProperties();

  /**
   * This method returns a set of all properties where the current
   * resource has been specified as one of the domain resources.
   */
  public Set<RDFProperty> getPropertiesWithResourceAsDomain();

  /**
   * This method returns a set of all properties where the current
   * resource has been specified as one of the range resources.
   */
  public Set<RDFProperty> getPropertiesWithResourceAsRange();

  /**
   * Checks if the resource has the provided annotation property set on
   * it with the specified value.
   */
  public boolean hasAnnotationPropertyWithValue(AnnotationProperty aProperty,
          Literal aValue);

  /**
   * For the current resource, the method removes the given literal for
   * the given property.
   */
  public void removeAnnotationPropertyValue(
          AnnotationProperty theAnnotationProperty, Literal literal);

  /**
   * Removes all values for a named property.
   * 
   * @param theProperty the property
   */
  public void removeAnnotationPropertyValues(AnnotationProperty theProperty);

}
