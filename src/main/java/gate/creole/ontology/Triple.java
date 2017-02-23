/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  $Id: Triple.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.creole.ontology;

/**
 * A Triple represents an RDF Triple from the underlying triple store and
 * consists of a subject, predicate and object.
 * A specific implementation may go beyond this definition and provide 
 * additional, implementation-specific extensions, e.g. for quads, 
 * triple ids, or for arbitrary context URIs. 
 *
 * @author Johann Petrak
 */
public interface Triple {
  public ONodeID getSubject();
  public OURI getPredicate();
  public LiteralOrONodeID getObject();

  /**
   * Return a printable representation of the triple, this may be implementation
   * dependent but should ideally adhere to Turtle syntax.
   */
  @Override
  public String toString();
  
  /**
   * Return the Turtle representation of the triple
   */
  public String toTurtle();

}
