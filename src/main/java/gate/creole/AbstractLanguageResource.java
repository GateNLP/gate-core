/*
 *  AbstractLanguageResource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 24/Oct/2000
 *
 *  $Id: AbstractLanguageResource.java 17662 2014-03-14 16:19:05Z markagreenwood $
 */

package gate.creole;

import gate.DataStore;
import gate.LanguageResource;
import gate.persist.PersistenceException;

/** A convenience implementation of LanguageResource with some default code.
  */
abstract public class AbstractLanguageResource
extends AbstractResource implements LanguageResource
{
  static final long serialVersionUID = 3320133313194786685L;

  /** Get the data store that this LR lives in. Null for transient LRs. */
  @Override
  public DataStore getDataStore() { return dataStore; }

  /** Set the data store that this LR lives in. */
  @Override
  public void setDataStore(DataStore dataStore) throws PersistenceException {
    this.dataStore = dataStore;
  } // setDataStore(DS)

  /** Returns the persistence id of this LR, if it has been stored in
   *  a datastore. Null otherwise.
   */
  @Override
  public Object getLRPersistenceId(){
    return lrPersistentId;
  }

  /** Sets the persistence id of this LR. To be used only in the
   *  Factory and DataStore code.
   */
  @Override
  public void setLRPersistenceId(Object lrID){
    this.lrPersistentId = lrID;
  }


  /** The data store this LR lives in. */
  transient protected DataStore dataStore;

  /** The persistence ID of this LR. Only set, when dataStore is.*/
  transient protected Object lrPersistentId = null;


  /** Save: synchonise the in-memory image of the LR with the persistent
    * image.
    */
  @Override
  public void sync()
    throws PersistenceException,SecurityException {
    if(dataStore == null)
      throw new PersistenceException("LR has no DataStore");

    dataStore.sync(this);
  } // sync()

  /** Clear the internal state of the resource
    */
  @Override
  public void cleanup() {
  } //clear()

  /**
   * Returns true of an LR has been modified since the last sync.
   * Always returns false for transient LRs.
   */
  @Override
  public boolean isModified() {return false;}

  /**
   * Returns the parent LR of this LR.
   * Only relevant for LRs that support shadowing. Most do not by default.
   */
  @Override
  public LanguageResource getParent()
    throws PersistenceException,SecurityException {
    if(dataStore == null)
      throw new PersistenceException("LR has no DataStore");
    throw new UnsupportedOperationException("getParent method not " +
                                            "supported by this LR");
  }//getParent

  /**
   * Sets the parent LR of this LR.
   * Only relevant for LRs that support shadowing. Most do not by default.
   */
  @Override
  public void setParent(LanguageResource parentLR)
    throws PersistenceException,SecurityException {
    if(dataStore == null)
      throw new PersistenceException("LR has no DataStore");
    throw new UnsupportedOperationException("setParent method not " +
                                            "supported by this LR");
  }//setParent



} // class AbstractLanguageResource
