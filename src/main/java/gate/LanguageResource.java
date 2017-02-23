/*
 *  LanguageResource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 11/Feb/2000
 *
 *  $Id: LanguageResource.java 17662 2014-03-14 16:19:05Z markagreenwood $
 */

package gate;

import gate.persist.PersistenceException;

/** Models all sorts of language resources.
  */
public interface LanguageResource extends Resource
{
  /** Get the data store that this LR lives in. Null for transient LRs. */
  public DataStore getDataStore();

  /** Set the data store that this LR lives in. */
  public void setDataStore(DataStore dataStore) throws PersistenceException;

  /** Returns the persistence id of this LR, if it has been stored in
   *  a datastore. Null otherwise.
   */
  public Object getLRPersistenceId();

  /** Sets the persistence id of this LR. To be used only in the
   *  Factory and DataStore code.
   */
  public void setLRPersistenceId(Object lrID);

  /** Save: synchonise the in-memory image of the LR with the persistent
    * image.
    */
  public void sync() throws PersistenceException,SecurityException;

  /**
   * Returns true of an LR has been modified since the last sync.
   * Always returns false for transient LRs.
   */
  public boolean isModified();

  /**
   * Returns the parent LR of this LR.
   * Only relevant for LRs that support shadowing. Most do not by default.
   */
  public LanguageResource getParent()
    throws PersistenceException,SecurityException;

  /**
   * Sets the parent LR of this LR.
   * Only relevant for LRs that support shadowing. Most do not by default.
   */
  public void setParent(LanguageResource parentLR)
    throws PersistenceException,SecurityException;

} // interface LanguageResource
