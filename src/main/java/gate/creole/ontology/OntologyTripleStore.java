/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Johann Petrak
 *
 *  $Id: $
 */
package gate.creole.ontology;

/**
 * An OntologyTripleStore object can be used to directly manipulate the
 * triples that represent an ontology, if the ontology implementation 
 * supports this. The OntologyTripleStore object can be accessed using
 * the method {@link Ontology#getOntologyTripleStore}.
 * 
 * @author Johann Petrak
 */
public interface OntologyTripleStore {
  /** 
   * Add a triple with a non-literal triple object to the triple store. 
   *
   * @param subject 
   * @param predicate
   * @param object 
   */
  public void addTriple(ONodeID subject, OURI predicate, ONodeID object);
  
  /** 
   * Add a triple with a literal triple object to the triple store. 
   *
   * @param subject
   * @param predicate
   * @param object 
   */
  public void addTriple(ONodeID subject, OURI predicate, Literal object);
  
  /** 
   * Remove a statement(triple) with a non-literal triple object 
   * from the ontology triple
   * store. The null value can be used for any of the subject, predicate,
   * or object parameters as a wildcard indicator: in that case, triples
   * with any value for that parameter will be removed.
   * 
   * NOTE: if a null value is passed for the object parameter, triples with
   * any value for the object, no matter if it is a literal or non-literal
   * (including blank nodes) will be removed!!
   * 
   * @param subject
   * @param predicate
   * @param object 
   */
  public void removeTriple(ONodeID subject, OURI predicate, ONodeID object);
  /** 
   * Remove a statement(triple) with a literal triple object from the ontology triple
   * store. The null value can be used for any of the subject, predicate,
   * or object parameters as a wildcard indicator: in that case, triples
   * with any value for that parameter will be removed.
   * 
   * NOTE: if a null value is passed for the object parameter, triples with
   * any value for the object, no matter if it is a literal or non-literal
   * (including blank nodes) will be removed!!
   * 
   * @param subject
   * @param predicate
   * @param object 
   */
  public void removeTriple(ONodeID subject, OURI predicate, Literal object);

  /** 
   * Add a listener for ontology triple store additions and removals. 
   * The listener will get the parameters of the original addition or 
   * deletion request, not any indication of which concrete triples were 
   * actually added or removed!
   * <p>
   * NOTE: the listener will not get all events for modifications made 
   * through methods other than the direct triple addition and removal
   * methods of the OntologyTripleStore object.
   * 
   * @param listener  OntologyTripleStoreListener object
   */
  public void addOntologyTripleStoreListener(
    OntologyTripleStoreListener listener);
  
  /**
   * Check if a given triple with an URI object is part of the ontology.
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return true if the triple with the given subject, predicate and object
   * is either asserted or inferred in the ontology.
   */
  public boolean hasTriple(ONodeID subject, OURI predicate, ONodeID object);
  
  /**
   * Check if a given triple with a literal object is part of the ontology.
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return true if the triple with the given subject, predicate and object
   * is either asserted or inferred in the ontology.
   */
  public boolean hasTriple(ONodeID subject, OURI predicate, 
    gate.creole.ontology.Literal object);
  
  
  /** 
   * Remove an existing listener for ontology triple store additions and removals. 
   * 
   * @param listener  OntologyTripleStoreListener object
   */
  public void removeOntologyTripleStoreListener(
    OntologyTripleStoreListener listener);
}
