/*
 *  SimpleMapImpl.java
 *
 *  Copyright (c) 2001, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  D.Ognyanoff, 5/Nov/2001
 *
 *  $Id: SimpleMapImpl.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements Map interface in using less memory. Very simple but usefull
 * for small number of items on it.
 */

class SimpleMapImpl implements Map<Object, Object>,
      java.lang.Cloneable, java.io.Serializable {
  
  /**
   * Special marker class used to represent null in the keys array.
   */
  private static class NullKey implements Serializable {
    private static final long serialVersionUID = 6391916290867211345L;

    private NullKey() {
    }
  }

  /**
   * The capacity of the map
   */
  int capacity = 3;

  /**
   * The current number of elements of the map
   */
  int count = 0;

  /**
   * Array keeping the keys of the entries in the map. It is "synchrnized"
   * with the values array - the Nth position in both arrays correspond
   * to one and the same entry
   */
  Object theKeys[];

  /**
   * Array keeping the values of the entries in the map. It is "synchrnized"
   * with the keys array - the Nth position in both arrays correspond
   * to one and the same entry
   */
  Object theValues[];
  
  /** Freeze the serialization UID. */
  static final long serialVersionUID = -6747241616127229116L;

  /** the Object instance that will represent the NULL keys in the map */
  transient static Object nullKey = new NullKey();

  /** the static 'all keys' collection */
  transient public static ConcurrentHashMap<Object,Object> theKeysHere;

  /** additional static members initialization */
  static {
    theKeysHere = new ConcurrentHashMap<Object,Object>();
    theKeysHere.put(nullKey, nullKey);
  } // static code

  /**
   * Constructor
   */
  public SimpleMapImpl() {
    theKeys = new Object[capacity];
    theValues = new Object[capacity];
  } // SimpleMapImpl()

  /**
   * return the number of elements in the map
   */
  @Override
  public int size() {
    return count;
  } // size()

  /**
   * return true if there are no elements in the map
   */
  @Override
  public boolean isEmpty() {
    return (count == 0);
  } // isEmpty()

  /**
   * Not supported. This method is here only to conform the Map interface
   */
  @Override
  public Collection<Object> values() {
    throw new UnsupportedOperationException(
      "SimpleMapImpl.values() not implemented!");
  } // values()

  /**
   * return the set of the keys in the map. The changes in the set DO NOT
   * affect the map.
   */
  @Override
  public Set<Object> keySet()
  {
    Set<Object> s = new HashSet<Object>(size());
    Object k;
    for (int i = 0; i < count; i++) {
      k = theKeys[i];
      if (k == nullKey)
           s.add(null);
        else
           s.add(k);
    } //for
    return s;
  } // keySet()

  /**
   * clear the map
   */
  @Override
  public void clear()
  {
    for (int i = 0; i < count; i++) {
      theKeys[i] = null;
      theValues[i] = null;
    } // for
    count = 0;
  } // clear

  /**
   * return true if the key is in the map
   */
  @Override
  public boolean containsKey(Object key) {
    return (getPostionByKey(key) != -1);
  }// containsKey

  /**
   * return true if the map contains that value
   */
  @Override
  public boolean containsValue(Object value) {
    return (getPostionByValue(value) != -1);
  }// containsValue

  /**
   * return the value associated with the key. If the key is
   * not in the map returns null.
   */
  @Override
  public Object get(Object key) {
    int pos = getPostionByKey(key);
    return (pos == -1) ? null : theValues[pos];
  } // get

  /**
   * put a value in the map using the given key. If the key exist in the map
   * the value is replaced and the old one is returned.
   */
  @Override
  public Object put(Object key, Object value) {
    Object gKey;
    if (key == null) {
      key = nullKey;
      gKey = nullKey;
    } else
      gKey = theKeysHere.putIfAbsent(key, key);
    // if the key is already in the 'all keys' map - try to find it in that instance
    // comparing by reference
    if (gKey != null) {
      for (int i = 0; i < count; i++) {
        if (gKey == theKeys[i]) {
          // we found the reference - return the value
          Object oldVal = theValues[i];
          theValues[i] = value;
          return oldVal;
        }
      } // for
    } else {// if(gKey != null)
      // no, the key is not in the 'all keys' map - put it there
      gKey = key;
    }
    // enlarge the containers if necessary
    if (count == capacity)
      increaseCapacity();

    // put the key and value to the map
    theKeys[count] = gKey;
    theValues[count] = value;
    count++;
    return null;
  } // put

  /**
   * remove value from the map using it's key.
   */
  @Override
  public Object remove(Object key) {
    int pos = getPostionByKey(key);
    if (pos == -1)
        return null;

    // save the value to return it at the end
    Object oldVal = theValues[pos];
    count--;
    // move the last element key and value removing the element
    if (count != 0) {
        theKeys[pos] = theKeys[count];
        theValues[pos] = theValues[count];
    }
    // clear the last position
    theKeys[count] = null;
    theValues[count] = null;

    // return the value
    return oldVal;
  } // remove

  /**
   * put all the elements from a map
   */
  @SuppressWarnings("unchecked")
  @Override
  public void putAll(Map<? extends Object, ? extends Object> t)
  {
    if (t == null) {
      throw new UnsupportedOperationException(
      "SimpleMapImpl.putAll argument is null");
    } // if (t == null)

    if (t instanceof SimpleMapImpl) {
      SimpleMapImpl sfm = (SimpleMapImpl)t;
      Object key;
      for (int i = 0; i < sfm.count; i++) {
        key = sfm.theKeys[i];
        put(key, sfm.theValues[i]);
      } //for
    } else { // if (t instanceof SimpleMapImpl)
      Iterator<?> entries = t.entrySet().iterator();
      Map.Entry<Object,Object> e;
      while (entries.hasNext()) {
        e = (Map.Entry<Object,Object>)entries.next();
        put(e.getKey(), e.getValue());
      } // while
    } // if(t instanceof SimpleMapImpl)
  } // putAll

  /**
   * return positive value as index of the key in the map.
   * Negative value means that the key is not present in the map
   */
  private int getPostionByKey(Object key) {
    if (key == null)
      key = nullKey;
    // check the 'all keys' map for the very first key occurence
    key = theKeysHere.get(key);
    if (key == null)
      return -1;

    for (int i = 0; i < count; i++) {
      if (key == theKeys[i])
        return i;
    } // for
    return -1;
  } // getPostionByKey

  /**
   * return the index of the key in the map comparing them by reference only.
   * This method is used in subsume check to speed it up.
   */
  protected int getSubsumeKey(Object key) {
    for (int i = 0; i < count; i++) {
      if (key == theKeys[i])
        return i;
    } // for
    return -1;
  } // getPostionByKey

  /**
   * return positive value as index of the value in the map.
   */
  private int getPostionByValue(Object value) {
    Object av;
    for (int i = 0; i < count; i++) {
      av = theValues[i];
      if (value == null) {
        if (av == null)
          return i;
      } else {//if (value == null)
        if (value.equals(av))
          return i;
      } //if (value == null)
    } // for

    return -1;
  } // getPostionByValue

  // Modification Operations
  private void increaseCapacity() {
    int oldCapacity = capacity;
    capacity *= 2;
    Object oldKeys[] = theKeys;
    theKeys = new Object[capacity];

    Object oldValues[] = theValues;
    theValues = new Object[capacity];

    System.arraycopy(oldKeys, 0, theKeys, 0, oldCapacity);
    System.arraycopy(oldValues, 0, theValues, 0, oldCapacity);
  } // increaseCapacity

  /**
   * Auxiliary classes needed for the support of entrySet() method
   */
  private static class Entry implements Map.Entry<Object,Object> {
    int hash;
    Object key;
    Object value;

    Entry(int hash, Object key, Object value) {
      this.hash = hash;
      this.key = key;
      this.value = value;
    }

    @Override
    protected Object clone() {
      return new Entry(hash, key, value);
    }

    @Override
    public Object getKey() {
      return key;
    }

    @Override
    public Object getValue() {
      return value;
    }

    @Override
    public Object setValue(Object value) {
      Object oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Map.Entry))
        return false;
      Map.Entry<?,?> e = (Map.Entry<?,?>)o;

      return (key==null ? e.getKey()==null : key.equals(e.getKey())) &&
        (value==null ? e.getValue()==null : value.equals(e.getValue()));
    }

    @Override
    public int hashCode() {
      return hash ^ (key==null ? 0 : key.hashCode());
    }

    @Override
    public String toString() {
      return key+"="+value;
    }
  } // Entry


  @Override
  public Set<Map.Entry<Object, Object>> entrySet() {
    Set<Map.Entry<Object, Object>> s = new HashSet<Map.Entry<Object,Object>>(size());
    Object k;
    for (int i = 0; i < count; i++) {
      k = theKeys[i];
      s.add(new Entry(k.hashCode(), ((k==nullKey)?null:k), theValues[i]));
    } //for
    return s;
  } // entrySet

  // Comparison and hashing
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Map)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    Map<Object,Object> m = (Map<Object,Object>)o;
    if (m.size() != count) {
      return false;
    }

    Object v, k;
    for (int i = 0; i < count; i++) {
      k = theKeys[i];
      v = m.get(k);
      if (v==null) {
        if (theValues[i]!=null)
          return false;
      }
      else if (!v.equals(theValues[i])){
        return false;
      }
    } // for

    return true;
  } // equals

  /**
   * return the hashCode for the map
   */
  @Override
  public int hashCode() {
    int h = 0;
    Iterator<Map.Entry<Object,Object>> i = entrySet().iterator();
    while (i.hasNext())
      h += i.next().hashCode();
    return h;
  } // hashCode

  /**
   * Create a copy of the map including the data.
   */
  @Override
  public Object clone() {
    SimpleMapImpl newMap;
    try {
      newMap = (SimpleMapImpl)super.clone();
    } catch (CloneNotSupportedException e) {
      throw(new InternalError(e.toString()));
    }

    newMap.count = count;
    newMap.theKeys = new Object[capacity];
    System.arraycopy(theKeys, 0, newMap.theKeys, 0, capacity);

    newMap.theValues = new Object[capacity];
    System.arraycopy(theValues, 0, newMap.theValues, 0, capacity);

    return newMap;
  } // clone

  @Override
  public String toString() {
    int max = size() - 1;
    StringBuffer buf = new StringBuffer();
    Iterator<Map.Entry<Object, Object>> i = entrySet().iterator();

    buf.append("{");
    for (int j = 0; j <= max; j++) {
      Map.Entry<Object, Object> e = (i.next());
      buf.append(e.getKey() + "=" + e.getValue());
      if (j < max)
        buf.append(", ");
    }
    buf.append("}");
    return buf.toString();
  } // toString

  /**
   * readObject - calls the default readObject() and then initialises the
   * transient data
   *
   * @serialData Read serializable fields. No optional data read.
   */
  private void readObject(ObjectInputStream s)
      throws IOException, ClassNotFoundException {
    s.defaultReadObject();
        
    for (int i = 0; i < theKeys.length; i++) {
      if(theKeys[i] instanceof NullKey) {
        theKeys[i] = nullKey;
      }
      else if(theKeys[i] != null) {
        // check if the key is in the 'all keys' map, adding it if not
        Object o = theKeysHere.putIfAbsent(theKeys[i], theKeys[i]);
        if (o != null) // yes - so reuse the reference
          theKeys[i] = o;
      }
    }//for
  }//readObject
} //SimpleMapImpl
