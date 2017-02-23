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
 *  $Id: OntologyTupleQuery.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

import gate.util.ClosableIterator;
import java.util.Vector;

/**
 * This represents a tuple query of the triple store for the ontology.
 * To create a tuple query object you must use the ontology's factory
 * method {@link Ontology#createTupleQuery(String, gate.creole.ontology.OConstants.QueryLanguage)}.
 * <p>
 * NOTE: querying the ontology triple store directly should be avoided and
 * only done in exceptional cases. Using the methods to query and access ontology
 * entities should be preferred whenever possible!
 * <p>
 * The query object implements a closable iterator that auto-closes when all
 * its elements are exhausted. However, you must close it if you stop retrieving
 * elements before all elements have been exhausted (i.e. before the
 * hasNext() method has returned false). Closing the query object is necessary
 * to free the considerable resources allocated by it.
 * <p>
 * To use a query object properly, be sure to follow the following steps:
 * <ul>
 * <li>Create the query object using the ontology's factory method
 * <li>Optionally set variable bindings using the setBinding method
 * <li>Evaluate the query. This is optional if you are not re-using a query
 * object with new binding settings
 * <li>Check if there is something available using the hasNext() method
 * <li>Retrieve the next tuple using the next() or nextAsString() method, or
 * using the nextFirst() or nextFirstAsString() method if you only need the
 * first or only variable in a tuple.
 * <li>Close the query if hasNext() has not returned false yet and you do not
 * need any more results from the query.
 * <li>If you want to re-use the query with different variable bindings
 * use method setBinding() and reevaluate using the method evaluate().
 * </uk>
 *
 * @author Johann Petrak
 */
public interface OntologyTupleQuery
    extends ClosableIterator<Vector<LiteralOrONodeID>>
{
  /**
   * Test if more results are available.
   * @return a boolean indicating if more results can be retrieved with one
   * of the next methods.
   */
  @Override
  public boolean hasNext();
  /**
   * Retrieve the next tuple from the query object.
   * @return a vector of LiteralOrONodeID objects that represent the next
   * tuple of the query.
   */
  @Override
  public Vector<LiteralOrONodeID> next();
  /**
   * Retrieve the next tuple from the query object as a vector of strings.
   *
   * @return a vector of strings representing the next result tuple of the query.
   * Each element of the vector is the result of using the original value's
   * toString() method to convert the value into a string.
   */
  public Vector<String> nextAsString();
  /**
   * Set the binding of a query variable to a new value. This can be used
   * to re-use a query with different variable bindings without recompiling
   * it.
   *
   * @param varName the name of the variable
   * @param value the value to assign to the variable
   */
  public void setBinding(String varName, Literal value);
  /**
   * Set the binding of a query variable to a new value. This can be used
   * to re-use a query with different variable bindings without recompiling
   * it.
   *
   * @param varName the name of the variable
   * @param value the value to assign to the variable
   */
  public void setBinding(String varName, ONodeID value);
  /**
   * Evaluate the tuple query (again). This method is optional but can
   * be used to explicitly re-evaluate the query after variable bindings
   * have been set. The method can be used explicitly to separate the
   * query evaluation from the first retrieval of a tuple (where it is
   * done implicitly).
   */
  public void evaluate();
  /**
   * Return just the first variable of a tuple. This is useful if the
   * tuple only consists of one variable and can be used to avoid the
   * creation of a vector only containing one element in that case.
   *
   * @return a LiteralOrONodeID object representing the first or only variable
   * in a returned tuple.
   */
  public LiteralOrONodeID nextFirst();
  /**
   * Return just the first variable of a tuple as a String. This is useful if the
   * tuple only consists of one variable and can be used to avoid the
   * creation of a vector only containing one element in that case.
   * The original value's toString() method is used to convert the value
   * to its String representation.
   *
   * @return a LiteralOrONodeID object representing the first or only variable
   * in a returned tuple.
   */
  public String nextFirstAsString();
  /**
   * Explicitly close the query and free its resources. This method must be
   * used if a query is not used any longer but the hasNext() method has
   * not returned false yet. 
   */
  @Override
  public void close();
}
