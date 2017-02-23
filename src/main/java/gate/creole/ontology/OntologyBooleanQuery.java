/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Johann Petrak 2009-08-13
 *
 *  $Id: OntologyBooleanQuery.java 16959 2013-09-28 11:57:47Z johann_p $
 */
package gate.creole.ontology;

/**
 * This represents a boolean query of the triple store for the ontology.
 * To create a boolean query object you must use the ontology's factory
 * method {@link Ontology#createBooleanQuery(String, gate.creole.ontology.OConstants.QueryLanguage)}.
 * <p>
 * NOTE: querying the ontology triple store directly should be avoided and
 * only done in exceptional cases. Using the methods to query and access ontology
 * entities should be preferred whenever possible!
 *
 * @author Johann Petrak
 */
public interface OntologyBooleanQuery  {
  /**
   * Re-assign a query variable to a new value. This will let you
   * query the triple store with the same query but a different value
   * for the variable. Depending on the implementation, this might  avoid
   * the necessity to recompile the whole query.
   *
   * @param varName - the name of the variable to be reassigned
   * @param value - a LiteralOrONodeID object representing either a literal or
   *   either an URI or a blank node identifier.
   * 
   */
  public void setBinding(String varName, LiteralOrONodeID value);
  /**
   * Re-assign a query variable to a new value. This will let you
   * query the triple store with the same query but a different value
   * for the variable. Depending on the implementation, this might  avoid
   * the necessity to recompile the whole query.
   *
   * @param varName - the name of the variable to be reassigned
   * @param value - a ONodeID object (usually a OURI object)
   * 
   */
  public void setBinding(String varName, ONodeID value);
  /**
   * Re-assign a query variable to a new value. This will let you
   * query the triple store with the same query but a different value
   * for the variable. Depending on the implementation, this might  avoid
   * the necessity to recompile the whole query.
   *
   * @param varName - the name of the variable to be reassigned
   * @param value - a Literal object 
   * 
   */
  public void setBinding(String varName, Literal value);
  /**
   * Evaluate the boolean query and return whether it evaluates to true
   * or false;
   *
   * @return - a boolean representing the result of the boolean query
   */
  public boolean evaluate();
}
