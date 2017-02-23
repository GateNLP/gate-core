/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 26/10/2001
 *
 *  $Id: DSPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */
package gate.util.persistence;

import java.net.MalformedURLException;
import java.util.Iterator;

import gate.*;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import java.net.URL;

public class DSPersistence extends AbstractPersistence{


  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    //check input
    if(! (source instanceof DataStore)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                DataStore.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + DataStore.class.getName());
    }

    DataStore ds = (DataStore)source;
    className = ds.getClass().getName();
    storageUrlString = ds.getStorageUrl();
    try {
      storageUrl = PersistenceManager.getPersistentRepresentation(
        new URL(storageUrlString));
    } catch(MalformedURLException e) {
      // ignore and just stay with storageUrlString
    }
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    if(storageUrl != null) {
      storageUrlString =
        ((URL)PersistenceManager.getTransientRepresentation(storageUrl))
          .toExternalForm();
    }

    //check if the same datastore is not already open
    Iterator<DataStore> dsIter = Gate.getDataStoreRegister().iterator();
    while(dsIter.hasNext()){
      DataStore aDS = dsIter.next();
      if(aDS.getStorageUrl().equals(storageUrlString)) {
        return aDS;
      }
    }
    //if we got this far, then it's a new datastore that needs opening
    return Factory.openDataStore(className, storageUrlString);
  }
 
  protected String className;
  protected String storageUrlString;
  protected Object storageUrl;
  static final long serialVersionUID = 5952924943977701708L;
}
