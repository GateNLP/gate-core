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
 *  $Id: MapPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */
package gate.util.persistence;

import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapPersistence extends AbstractPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    if(! (source instanceof Map)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                Map.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + Map.class.getName());
    }
    mapType = source.getClass();

    Map<?,?> map = (Map<?,?>)source;
    
    localMap = new HashMap<Serializable,Serializable>(map.size());
    //collect the keys in the order given by the entrySet().iterator();
    Iterator<?> keyIter = map.keySet().iterator();
    while(keyIter.hasNext()){
      Object key = keyIter.next();
      Object value = map.get(key);

      localMap.put(PersistenceManager.getPersistentRepresentation(key),
              PersistenceManager.getPersistentRepresentation(value));
    }
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    //let's try to create a map of the same type as the original
    Map<Object,Object> result = null;
    try{
      result = (Map<Object,Object>)mapType.newInstance();
    }catch(Exception e){
    }
    if(result == null) result = new HashMap<Object,Object>(localMap.size());

    //now we have a map let's populate it
    Iterator<Serializable> keyIter = localMap.keySet().iterator();
    while(keyIter.hasNext()){
      Object key = keyIter.next();
      Object value = localMap.get(key);

      key = PersistenceManager.getTransientRepresentation(key);
      value = PersistenceManager.getTransientRepresentation(
              value,containingControllerName,initParamOverrides);
      result.put(key, value);
    }

    return result;
  }

  protected Class<?> mapType;
  protected Map<Serializable,Serializable> localMap;
  static final long serialVersionUID = 1835776085941379996L;
}