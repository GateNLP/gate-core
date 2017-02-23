/*
 *  AllValuesFromRestriction.java
 *
 *  $Id: AllValuesFromRestriction.java 15002 2012-01-10 21:18:26Z markagreenwood $
 */
package gate.creole.ontology;

/**
 * An AllValuesFromRestriction.
 *
 * @author Niraj Aswani
 * @author Johann Petrak
 *
 */
public interface AllValuesFromRestriction extends Restriction {

    /**
     * Returns the resource which is set as a restricted value.
     * For ontologies that conform to OWL-Lite this is always an OClass
     * object.
     */
    public OResource getHasValue();
    
    
    /**
     * Sets the value of the restriction. For ontologies that conform to
     * OWL-Lite this should always be an OClass.
     * 
     * @param resource - the OResource, usually and OClass that should be value
     * of the restriction.
     * @deprecated - use {@link #setHasValue(OClass) } instead
     */
    @Deprecated
    public void setHasValue(OResource resource);

    /**
     * Sets the value of the the restiction to the specified OClass.
     * 
     * @param resource
     */
    public void setHasValue(OClass resource);

    /**
     * Specify the object property for which to set the allValuesFromRestriction.
     *
     * @param property
     */
    public void setOnPropertyValue(ObjectProperty property);


}
