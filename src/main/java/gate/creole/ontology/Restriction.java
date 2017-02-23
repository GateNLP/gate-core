/**
 * 
 */
package gate.creole.ontology;

/**
 * This interface defines a restriction in the ontology.
 *         The restriction is specified on a property.
 *
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
public interface Restriction extends AnonymousClass {

    /**
     *  Return the property on which the restriction is specified
     */
    public RDFProperty getOnPropertyValue();
    
    /**
     * Sets the property on which the restriction is specified
     * @param property
     */
    public void setOnPropertyValue(RDFProperty property);
    
}