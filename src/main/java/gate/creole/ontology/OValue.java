/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Johann Petrak 2009-08-20
 *
 *  $Id: OValue.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.creole.ontology;

/**
 * A class representing something that is either a literal or a OResource.
 * <p>
 * Developer note: ideally this would be implemented as a superinteface
 * of the Literal and OResource interfaces, but Literal is implemented
 * as a class and has to remain a class for backwards compatibility.löschnaz
 * 
 * @author Johann Petrak
 */
// TODO: should we implement comparable and equals/hashcode for this?
public interface OValue  {

  /**
   * Check if the object represents a literal.
   *
   * @return true if the object represents a literal value, false if it
   * represents a blank node ID or an URI.
   */
  public boolean isLiteral();

  /**
   * Check if the object represents a OResource
   *
   * @return true if the object represents a OResource object
   */
  public boolean isOResource();

  /**
   * Get the {@link OResource} object if this object represents a resource. Throws a
   * {@link GateOntologyException} if this object represents a literal.
   *
   * @return  the OResource represented by this object
   */
  public OResource getOResource();

  /**
   * Get the {@link Literal} object if this object represents a literal.
   * Throws a {@link GateOntologyException} if this object represents a
   * node ID.
   *
   * @return the Literal represented by this object
   */
  public Literal getLiteral();
  @Override
  /**
   * Create a String representation of the represented object.
   *
   */
  public String toString();
  /**
   * Create a String representation that conforms to Turtle language syntax.
   * If this represents a OResource it is identical to OResource.toString()
   *
   * @return a string representation of the node id or literal following
   * Turtle syntax.
   */
  public String toTurtle();

  @Override
  public int hashCode();

  @Override
  public boolean equals(Object other);

}
