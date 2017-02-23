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
 * An OntologyTripleStoreListener is noticed whenever a triple is added to
 * or removed from the ontology store. Note: at the moment, additions or 
 * removals that are not made through direct calls to OntologyTripleStore
 * methods are not fully implemented yet!
 * 
 * @author Johann Petrak
 */
public interface OntologyTripleStoreListener {
  public void tripleAdded(ONodeID subject, OURI predicate, ONodeID object);
  public void tripleAdded(ONodeID subject, OURI predicate, Literal object);
  public void tripleRemoved(ONodeID subject, OURI predicate, ONodeID object);
  public void tripleRemoved(ONodeID subject, OURI predicate, Literal object);
}
