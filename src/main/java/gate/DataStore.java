/*
 *  DataStore.java
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
 *  $Id: DataStore.java 17662 2014-03-14 16:19:05Z markagreenwood $
 */

package gate;

import gate.event.DatastoreListener;
import gate.persist.PersistenceException;
import gate.util.FeatureBearer;
import gate.util.NameBearer;

import java.util.List;

/** Models all sorts of data storage.
  */
public interface DataStore extends FeatureBearer, NameBearer {

  public static final String DATASTORE_FEATURE_NAME = "DataStore";
  public static final String LR_ID_FEATURE_NAME = "LRPersistenceId";


  /** Set the URL as string for the underlying storage mechanism. */
  public void setStorageUrl(String storageUrl) throws PersistenceException;

  /** Get the URL as String for the underlying storage mechanism. */
  public String getStorageUrl();

  /**
   * Create a new data store. <B>NOTE:</B> for some data stores
   * creation is an system administrator task; in such cases this
   * method will throw an UnsupportedOperationException.
   */
  public void create()
  throws PersistenceException, UnsupportedOperationException;

  /** Open a connection to the data store. */
  public void open() throws PersistenceException;

  /** Close the data store. */
  public void close() throws PersistenceException;

  /**
   * Delete the data store. <B>NOTE:</B> for some data stores
   * deletion is an system administrator task; in such cases this
   * method will throw an UnsupportedOperationException.
   */
  public void delete()
  throws PersistenceException, UnsupportedOperationException;

  /**
   * Delete a resource from the data store.
   * @param lrId a data-store specific unique identifier for the resource
   * @param lrClassName class name of the type of resource
   */
  public void delete(String lrClassName, Object lrId)
  throws PersistenceException,SecurityException;

  /**
   * Save: synchonise the in-memory image of the LR with the persistent
   * image.
   */
  public void sync(LanguageResource lr)
  throws PersistenceException,SecurityException;

  /**
   * Set method for the autosaving behaviour of the data store.
   * <B>NOTE:</B> many types of datastore have no auto-save function,
   * in which case this will throw an UnsupportedOperationException.
   */
  public void setAutoSaving(boolean autoSaving)
  throws UnsupportedOperationException,PersistenceException;

  /** Get the autosaving behaviour of the LR. */
  public boolean isAutoSaving();
  
  /** Adopt a resource for persistence. */
  public LanguageResource adopt(LanguageResource lr) throws PersistenceException;

  /**
   * Get a resource from the persistent store.
   * <B>You should never call this method directly, instead use the following:</B>
   * <pre> FeatureMap params = Factory.newFeatureMap();
   * params.put(DataStore.DATASTORE_FEATURE_NAME, datastore);
   * params.put(DataStore.LR_ID_FEATURE_NAME, lrID);
   * LanguageResource lr = (LanguageResource)Factory.createResource(lrClassName, params);</pre>
   */
  LanguageResource getLr(String lrClassName, Object lrId)
  throws PersistenceException,SecurityException;

  /** Get a list of the types of LR that are present in the data store. */
  public List<String> getLrTypes() throws PersistenceException;

  /** Get a list of the IDs of LRs of a particular type that are present. */
  public List<String> getLrIds(String lrType) throws PersistenceException;

  /** Get a list of the names of LRs of a particular type that are present. */
  public List<String> getLrNames(String lrType) throws PersistenceException;

  /** Get a list of LRs that satisfy some set or restrictions */
  @SuppressWarnings("rawtypes") // we've never implemented the types are unknown
  public List findLrIds(List constraints) throws PersistenceException;

  /**
   *  Get a list of LRs that satisfy some set or restrictions and are
   *  of a particular type
   */
  @SuppressWarnings("rawtypes") // we've never implemented the types are unknown
  public List findLrIds(List constraints, String lrType) throws PersistenceException;

  /** Get the name of an LR from its ID. */
  public String getLrName(Object lrId) throws PersistenceException;

  /**
   * Registers a new {@link gate.event.DatastoreListener} with this datastore
   */
  public void addDatastoreListener(DatastoreListener l);

  /**
   * Removes a a previously registered {@link gate.event.DatastoreListener}
   * from the list listeners for this datastore
   */
  public void removeDatastoreListener(DatastoreListener l);

  /**
   * Returns the name of the icon to be used when this datastore is displayed
   * in the GUI
   */
  public String getIconName();

  /**
   * Returns the comment displayed by the GUI for this DataStore
   */
  public String getComment();


  /**
   * Checks if the user (identified by the sessionID)
   *  has read access to the LR
   */
  public boolean canReadLR(Object lrID)
    throws PersistenceException;

  /**
   * Checks if the user (identified by the sessionID)
   * has write access to the LR
   */
  public boolean canWriteLR(Object lrID)
    throws PersistenceException;

  /**
   * Try to acquire exlusive lock on a resource from the persistent store.
   * Always call unlockLR() when the lock is no longer needed
   */
  public boolean lockLr(LanguageResource lr)
  throws PersistenceException,SecurityException;

  /**
   * Releases the exlusive lock on a resource from the persistent store.
   */
  public void unlockLr(LanguageResource lr)
  throws PersistenceException,SecurityException;


} // interface DataStore
