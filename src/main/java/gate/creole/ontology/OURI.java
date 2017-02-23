/*
 * OURI.java
 * 
 * $Id: OURI.java 15002 2012-01-10 21:18:26Z markagreenwood $
 *
 */

package gate.creole.ontology;

/**
 * Interface for objects representing an URI.
 * All URIs used in an ontology are represented by objects implementing this
 * interface. A client of the GATE ontology API must never directly create
 * these objects using their constructor (clients must never directly use
 * any classes from the inplementing packages below this package!).
 * In order to create OURIs the {@link Ontology} factory methods
 * {@link Ontology#createOURI(String)}, {@link Ontology#createOURIForName(String)},
 * or {@link Ontology#generateOURI(String)} must be used.
 *
 * @author Johann Petrak
 */
public interface OURI extends ONodeID {
}
