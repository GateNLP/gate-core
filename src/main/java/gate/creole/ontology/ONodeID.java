/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  $Id: ONodeID.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

/**
 * An ONodeID represents the id of either a blank node or a resource.
 * If the node represented by this is a blank node, name space will be
 * blank and the resource name will be whatever internal name for the
 * blank node the implementation uses. If the node respresented by this
 * is a named resource, the ID will be an URI consisting of a name space
 * and a resource name. <br>
 * TODO: plan for URI/IRI encodings and comparion of OURIs that have non-ASCII
 * resource names. Most likely each ORUI will remember the string it was
 * created from and return that string for getResourceName or toString.
 * The toDisplayString method will always generate a valid IRI, and the
 * toASCIIString will always generate a valid URI representation.
 * Whether these representations are stored or generated on the fly and which
 * of these representations are used with the backend is implementation
 * dependent.
 *
 * @author Johann Petrak
 */
public interface ONodeID extends Comparable<ONodeID> {
  public String getNameSpace();
  public String getResourceName();

  /**
   * Return the node ID as the string from which the ID was originally created.
   */
  @Override
  public String toString();
  
  /**
   * Return the node ID as a pure ASCII string.
   * If the node is an OURI, this will return a string that has non ASCII
   * characters escaped according to URI escaping rules.<br>
   * if the node is a BNodeID, this will return the same string as toString().
   * <p> 
   * NOTE: URI encoding and translation from/to IRIs is not implemented yet!
   */
  public String toASCIIString();
  
  /**
   * Return the node ID as a unicode string.
   * If the node is an OURI, this will return a string that is a valid IRI.
   * If the node is a BNodeID, this will return the same sting as toString().
   * <p>
   * NOTE: not implemented yet!
   */
  public String toDisplayString();
  
  /**
   * Return if this represents a blank node or a named resource.
   * Returns true if this represents a blank node (is a BNodeID) or a named
   * resource (is a ORUI).
   * <p>
   * NOTE: not implemented yet!
   */
  public boolean isAnonymousResource();

  @Override
  public int compareTo(ONodeID other);
  
  @Override
  public boolean equals(Object other);
  
  @Override
  public int hashCode();
  
  /**
   * Return a representation of the node that conforms to Turtle syntax.
   * This will return a string that conforms to TURTLE (Terse RDF Triple
   * Language) - see http://www.w3.org/TeamSubmission/turtle/
   * <p>
   * TODO: at the moment, this only returns either the blank node ID unchanged
   * or the URI as returned by toString() between "<" and ">". This will have
   * to use a proper ASCII representation of the URI or an IRI representation
   * instead.
   */
  public String toTurtle();
  
  /**
   * Validate if the string that was passed on as a bnode id or as an URI/IRI
   * to the constructor of the implementing class can be converted to
   * a blank node identifier or and URI/IRI that conforms to the implementaion.
   * <p>
   * TODO: this is not yet implemented.
   * 
   * @throws IllegalArgumentException
   */
  public void validate() throws IllegalArgumentException;
}
