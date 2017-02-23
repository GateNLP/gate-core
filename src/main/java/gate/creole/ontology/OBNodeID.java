/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gate.creole.ontology;

/**
 * An object representing an ontology node ID. This is either a blank
 * node ID or an URI. The details of what can be a blank node ID or an
 * URI is implementation dependent.
 * <p>
 * ONodeID objects must not directly created by a client using the GATE
 * ontology API and there is no factory method that allows the direct
 * creation of ONodeID objects. However, ONodeID objects can be retrieved
 * with the getONodeID method for some ontology objects (OClasses).
 * <p>
 * Ontology API methods that accept ONodeID objects also accept
 * {@link OURI objects} and clients of the GATE ontology API should normally
 * pass {@link OURI} objects to these methods.
 *
 * @author Johann Petrak
 */
public interface OBNodeID extends ONodeID {
}
