/*
 *  OntologyModificationListener.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OntologyModificationListener.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import java.util.EventListener;

/**
 * Objects wishing to listen to various ontology events, must implement
 * this interface (using implements java keyword) and the methods of
 * this interface. They must get registered themselves with the
 * respective ontology by using the
 * ontology.addOntologyModificationListener(OntologyModificationListener)
 * method.
 * 
 * @author Niraj Aswani
 * 
 */
public interface OntologyModificationListener extends EventListener {
  /**
   * This method is invoked whenever a relation between two objects of the same class (e.g. OClass and OClass, RDFPRoeprty and RDFProeprty etc) is changed.
   * 
   * @param ontology the source of the event
   * @param resource1 the affected OResource
   * @param resource2 the affected OResource
   * @param eventType the type of an event (@see OConstants) for more
   *          details
   */
  public void resourceRelationChanged(Ontology ontology, OResource resource1, OResource resource2,int eventType);
  
  
  /**
   * This method should be invoked when a property value is added or removed (specified by the event type). 
   * @param ontology the source of the event
   * @param resource the affected resource whose property value is added or removed
   * @param property the property whose value has been added or removed
   * @param value the actual value that is added or removed. If the value is null - it indicates all the values related to the property are deleted.
   * @param eventType type of the event (@see OConstants) for more details
   */
  public void resourcePropertyValueChanged(Ontology ontology, OResource resource, RDFProperty property, Object value, int eventType);
  
  /**
   * This method is invoked whenever a resource
   * (class/property/instance) is removed from the ontology.
   * 
   * @param ontology the source of the event
   * @param resources an array of URIs of resources which were deleted
   *          (including the resource which was asked to be deleted).
   */
  public void resourcesRemoved(Ontology ontology, String[] resources);

  /**
   * This method is invoked whenever a resource
   * (class/property/instance) is added to the ontology.
   * 
   * @param ontology the source of the event
   * @param resource an instance of OResource, which was created as a
   *          result of addition of a resource.
   */
  public void resourceAdded(Ontology ontology, OResource resource);
  
  /**
   * This method is called whenever ontology is reset.  In other words
   * when all resources of the ontology are deleted using the ontology.cleanup method.
   * @param ontology
   */
  public void ontologyReset(Ontology ontology);
}
