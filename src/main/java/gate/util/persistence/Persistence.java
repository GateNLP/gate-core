/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 25/10/2001
 *
 *  $Id: Persistence.java 15333 2012-02-07 13:18:33Z ian_roberts $
 *
 */
package gate.util.persistence;

import java.io.Serializable;

import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
/**
 * Defines an object that holds persistent data about another object.
 * Storing an arbitrary object will consist of creating an appropiate
 * Persistence object for it and storing that one (via serialisation).
 *
 * Restoring a previously saved object will consist of restoring the persistence
 * object and using the data it stores to create a new object that is as similar
 * as possible to the original object.
 */
public interface Persistence extends Serializable{

  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  public void extractDataFromSource(Object source)throws PersistenceException;

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException;
}