/*
 *  DataStoreRegister.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 23/Jan/2001
 *
 *  $Id: DataStoreRegister.java 17662 2014-03-14 16:19:05Z markagreenwood $
 */

package gate;

import gate.event.CreoleEvent;
import gate.event.CreoleListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Records all the open DataStores.
 */
public class DataStoreRegister extends HashSet<DataStore> {
  private static final long serialVersionUID = 1L;

  /**
   * All the DataStore classes available. This is a map of class name to
   * descriptive text.
   */
  public static Map<String,String> getDataStoreClassNames() {
    Map<String,String> names = new HashMap<String,String>();

    // TODO: no plugability here at present.... at some future point there should
    // be a capability to add new data store classes via creole.xml metadata
    // and resource jars

    // filesystem
    names.put("gate.persist.SerialDataStore", "SerialDataStore: file-based storage using Java serialisation");

    names.put("gate.persist.LuceneDataStoreImpl", "Lucene Based Searchable DataStore");
    
    // docservice
    try {
      if (Class.forName("gleam.docservice.gate.DocServiceDataStore", true, Gate.getClassLoader()) != null) {
        names.put("gleam.docservice.gate.DocServiceDataStore",
            "SAFE DocService DataStore");
      }
    } catch (ClassNotFoundException e) {
    }
    
    return names;
  } // getDataStoreClassNames()

  /**
   * Adds the specified element to this set if it is not already present.
   * Overriden here for event registration code.
   */
  @Override
  public boolean add(DataStore o) {
    return super.add(o);
  } // add

  /**
   * Removes the given element from this set if it is present. Overriden here
   * for event registration code.
   */
  @Override
  public boolean remove(Object o) {
    boolean res = super.remove(o);
    if (res) {
      fireDatastoreClosed(new CreoleEvent((DataStore) o, CreoleEvent.DATASTORE_CLOSED));
    }
    return res;
  } // remove

  /**
   * Removes all of the elements from this set. Overriden here for event
   * registration code.
   */
  @Override
  public void clear() {
    Set<DataStore> datastores = new HashSet<DataStore>(this);
    super.clear();

    Iterator<DataStore> iter = datastores.iterator();
    while (iter.hasNext()) {
      fireDatastoreClosed(new CreoleEvent(iter.next(), CreoleEvent.DATASTORE_CLOSED));
    } // while
  } // clear()
  
  /**
   * Removes a previously registered {@link gate.event.CreoleListener} from the
   * list of listeners for this DataStoreRegister. Normally the only listener
   * that is registered with the DataStoreRegister is the {@link CreoleRegister}
   * which can be obtained through {@link Gate#getCreoleRegister()}
   */
  public synchronized void removeCreoleListener(CreoleListener l) {
    if (creoleListeners != null && creoleListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<CreoleListener> v = (Vector<CreoleListener>) creoleListeners.clone();
      v.removeElement(l);
      creoleListeners = v;
    }
  } // removeCreoleListener(CreoleListener l)

  /**
   * Registers a new {@link gate.event.CreoleListener} with this
   * DataStoreRegister. Normally the only listener that is registered with the
   * DataStoreRegister is the {@link CreoleRegister} which can be obtained
   * through {@link Gate#getCreoleRegister()}
   */
  public synchronized void addCreoleListener(CreoleListener l) {
    @SuppressWarnings("unchecked")
    Vector<CreoleListener> v = creoleListeners == null ? new Vector<CreoleListener>(2) : (Vector<CreoleListener>)creoleListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      creoleListeners = v;
    }// if
  }// addCreoleListener(CreoleListener l)

  /**
   * Notifies all registered {@link gate.event.CreoleListener}s that a
   * {@link DataStore} has been opened. Normally the only listener that is
   * registered with the DataStoreRegister is the {@link CreoleRegister} which
   * can be obtained through {@link Gate#getCreoleRegister()}
   */
  protected void fireDatastoreOpened(CreoleEvent e) {
    if (creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreOpened(e);
      } // for
    } // if
  } // fireDatastoreOpened(CreoleEvent e)

  /**
   * Notifies all registered {@link gate.event.CreoleListener}s that a new
   * {@link DataStore} has been created. Normally the only listener that is
   * registered with the DataStoreRegister is the {@link CreoleRegister} which
   * can be obtained through {@link Gate#getCreoleRegister()}
   */
  protected void fireDatastoreCreated(CreoleEvent e) {
    if (creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreCreated(e);
      } // for
    } // if
  } // fireDatastoreCreated(CreoleEvent e)

  /**
   * Notifies all registered {@link gate.event.CreoleListener}s that a
   * {@link DataStore} has been closed. Normally the only listener that is
   * registered with the DataStoreRegister is the {@link CreoleRegister} which
   * can be obtained through {@link Gate#getCreoleRegister()}
   */
  protected void fireDatastoreClosed(CreoleEvent e) {
    if (creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreClosed(e);
      } // for
    } // if
  } // fireDatastoreClosed(CreoleEvent e)

  /** */
  private transient Vector<CreoleListener> creoleListeners;

} // class DataStoreRegister
