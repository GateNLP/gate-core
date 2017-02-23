/*
 *  RBTreeMap.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, Jan/2000, modified from Sun sources
 *
 *  $Id: RBTreeMap.java 17639 2014-03-12 10:48:13Z markagreenwood $
 */

package gate.util;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/** Slightly modified implementation of java.util.TreeMap in order to return the
  * closest neighbours in the case of a failed search.
  */
public class RBTreeMap<K,V> extends AbstractMap<K,V>
               implements SortedMap<K,V>, Cloneable, java.io.Serializable
{

  /** Freeze the serialization UID. */
  static final long serialVersionUID = -1454324265766936618L;

  /**
    * The Comparator used to maintain order in this RBTreeMap, or
    * null if this RBTreeMap uses its elements natural ordering.
    *
    * @serial
    */
  private Comparator<? super K> comparator = null;

   private transient Entry<K,V> root = null;

  /**
    * The number of entries in the tree
    */
  private transient int size = 0;

  /**
    * The number of structural modifications to the tree.
    */
  private transient int modCount = 0;

  private void incrementSize()   { modCount++; size++; }
  private void decrementSize()   { modCount++; size--; }

  /**
    * Constructs a new, empty map, sorted according to the keys' natural
    * order.  All keys inserted into the map must implement the
    * <tt>Comparable</tt> interface.  Furthermore, all such keys must be
    * <i>mutually comparable</i>: <tt>k1.compareTo(k2)</tt> must not throw a
    * ClassCastException for any elements <tt>k1</tt> and <tt>k2</tt> in the
    * map.  If the user attempts to put a key into the map that violates this
    * constraint (for example, the user attempts to put a string key into a
    * map whose keys are integers), the <tt>put(Object key, Object
    * value)</tt> call will throw a <tt>ClassCastException</tt>.
    *
    * @see Comparable
    */
  public RBTreeMap() {
  }

  /**
    * Constructs a new, empty map, sorted according to the given comparator.
    * All keys inserted into the map must be <i>mutually comparable</i> by
    * the given comparator: <tt>comparator.compare(k1, k2)</tt> must not
    * throw a <tt>ClassCastException</tt> for any keys <tt>k1</tt> and
    * <tt>k2</tt> in the map.  If the user attempts to put a key into the
    * map that violates this constraint, the <tt>put(Object key, Object
    * value)</tt> call will throw a <tt>ClassCastException</tt>.
    */
  public RBTreeMap(Comparator<? super K> c) {
    this.comparator = c;
  }

  /**
    * Constructs a new map containing the same mappings as the given map,
    * sorted according to the keys' <i>natural order</i>.  All keys inserted
    * into the new map must implement the <tt>Comparable</tt> interface.
    * Furthermore, all such keys must be <i>mutually comparable</i>:
    * <tt>k1.compareTo(k2)</tt> must not throw a <tt>ClassCastException</tt>
    * for any elements <tt>k1</tt> and <tt>k2</tt> in the map.  This method
    * runs in n*log(n) time.
    *
    * @throws    ClassCastException the keys in t are not Comparable, or
    * are not mutually comparable.
    */
  public RBTreeMap(Map<? extends K, ? extends V> m) {
    putAll(m);
  }

  /**
    * Constructs a new map containing the same mappings as the given
    * <tt>SortedMap</tt>, sorted according to the same ordering.  This method
    * runs in linear time.
    */
  public RBTreeMap(SortedMap<K, ? extends V> m) {
      comparator = m.comparator();
      try {
          buildFromSorted(m.size(), m.entrySet().iterator(), null, null);
      } catch (java.io.IOException cannotHappen) {
      } catch (ClassNotFoundException cannotHappen) {
      }
  }


  // Query Operations

  /**
    * Returns the number of key-value mappings in this map.
    *
    * @return the number of key-value mappings in this map.
    */
  @Override
  public int size() {
    return size;
  }

  /**
    * Returns <tt>true</tt> if this map contains a mapping for the specified
    * key.
    *
    * @param key key whose presence in this map is to be tested.
    *
    * @return <tt>true</tt> if this map contains a mapping for the
    *            specified key.
    * @throws ClassCastException if the key cannot be compared with the keys
    *		  currently in the map.
    * @throws NullPointerException key is <tt>null</tt> and this map uses
    *		  natural ordering, or its comparator does not tolerate
    *            <tt>null</tt> keys.
    */
  @Override
  public boolean containsKey(Object key) {
    return getEntry(key) != null;
  }

  /**
    * Returns <tt>true</tt> if this map maps one or more keys to the
    * specified value.  More formally, returns <tt>true</tt> if and only if
    * this map contains at least one mapping to a value <tt>v</tt> such
    * that <tt>(value==null ? v==null : value.equals(v))</tt>.  This
    * operation will probably require time linear in the Map size for most
    * implementations of Map.
    *
    * @param value value whose presence in this Map is to be tested.
    * @since JDK1.2
    */
  @Override
  public boolean containsValue(Object value) {
      return (value==null ? valueSearchNull(root)
              : valueSearchNonNull(root, value));
  }

  private boolean valueSearchNull(Entry<K,V> n) {
      if (n.value == null)
          return true;

      // Check left and right subtrees for value
      return (n.left  != null && valueSearchNull(n.left)) ||
             (n.right != null && valueSearchNull(n.right));
  }

  private boolean valueSearchNonNull(Entry<K,V> n, Object value) {
      // Check this node for the value
      if (value.equals(n.value))
          return true;

      // Check left and right subtrees for value
      return (n.left  != null && valueSearchNonNull(n.left, value)) ||
             (n.right != null && valueSearchNonNull(n.right, value));
  }

  /**
    * Returns a pair of values: (glb,lub).
    * If the given key is found in the map then glb=lub=the value associated
    * with the given key.
    * If the key is not in the map:
    * glb=the value associated with the greatest key in the map that is lower
    * than the given key; or null if the given key is smaller than any key in
    * the map.
    * lub=the value associated with the smaller key in the map that is greater
    * than the given key; or null the given key is greater than any key in the
    * map.
    * If the map is empty it returns (null,null).
    */
  @SuppressWarnings("unchecked")
  public V[] getClosestMatch(K key){
    if (root==null)return (V[])(new Object[]{null,null});

    Entry<K,V> lub=getCeilEntry(key);

    if(lub==null){//greatest key in set is still smaller then parameter "key"
      return (V[])new Object[]{lastEntry().value,null};
    };

    int cmp=compare(key,lub.key);

    if (cmp==0){return (V[])(new Object[]{lub.value,lub.value});}
    else {
      Entry<K,V> prec=getPrecedingEntry(lub.key);
      if (prec == null) return (V[])(new Object[]{null,lub.value});
      else return (V[])(new Object[]{prec.value,lub.value});
    }
  }

  /** Returns the value associated to the next key in the map if an exact match
    * doesn't exist. If there is an exact match, the method will return the
    * value associated to the given key.
    * @param key the key for wich the look-up will be done.
    * @return the value associated to the given key or the next available
    * value
    */
  public V getNextOf(K key){
    if (root==null) return null;
    Entry<K,V> lub=getCeilEntry(key);
    if (lub == null) return null;
    return lub.value;
  }

  /**
    * Returns the value to which this map maps the specified key.  Returns
    * <tt>null</tt> if the map contains no mapping for this key.  A return
    * value of <tt>null</tt> does not <i>necessarily</i> indicate that the
    * map contains no mapping for the key; it's also possible that the map
    * explicitly maps the key to <tt>null</tt>.  The <tt>containsKey</tt>
    * operation may be used to distinguish these two cases.
    *
    * @param key key whose associated value is to be returned.
    * @return the value to which this map maps the specified key, or
    *	       <tt>null</tt> if the map contains no mapping for the key.
    * @throws    ClassCastException key cannot be compared with the keys
    *		  currently in the map.
    * @throws NullPointerException key is <tt>null</tt> and this map uses
    *		  natural ordering, or its comparator does not tolerate
    *		  <tt>null</tt> keys.
    *
    * @see #containsKey(Object)
    */
  @Override
  public V get(Object key) {
    Entry<K,V> p = getEntry(key);
    return p==null ? null : p.value;
  }

  /**
    * Returns the comparator used to order this map, or <tt>null</tt> if this
    * map uses its keys' natural order.
    *
    * @return the comparator associated with this sorted map, or
    * 	       <tt>null</tt> if it uses its keys' natural sort method.
    */
  @Override
  public Comparator<? super K> comparator() {
      return comparator;
  }

  /**
    * Returns the first (lowest) key currently in this sorted map.
    *
    * @return the first (lowest) key currently in this sorted map.
    * @throws    NoSuchElementException Map is empty.
    */
  @Override
  public K firstKey() {
      return key(firstEntry());
  }

  /**
    * Returns the last (highest) key currently in this sorted map.
    *
    * @return the last (highest) key currently in this sorted map.
    * @throws    NoSuchElementException Map is empty.
    */
  @Override
  public K lastKey() {
      return key(lastEntry());
  }

  /**
    * Copies all of the mappings from the specified map to this map.  These
    * mappings replace any mappings that this map had for any of the keys
    * currently in the specified map.
    *
    * @param map Mappings to be stored in this map.
    * @throws    ClassCastException class of a key or value in the specified
    * 	          map prevents it from being stored in this map.
    *
    * @throws NullPointerException this map does not permit <tt>null</tt>
    *            keys and a specified key is <tt>null</tt>.
    */
  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
      int mapSize = map.size();
      if (size==0 && mapSize!=0 && map instanceof SortedMap) {
          @SuppressWarnings("unchecked")
          Comparator<? super K> c = ((SortedMap<K,V>)map).comparator();
          if (c == comparator || (c != null && c.equals(comparator))) {
            ++modCount;
            try {
                buildFromSorted(mapSize, map.entrySet().iterator(),
                                null, null);
            } catch (java.io.IOException cannotHappen) {
            } catch (ClassNotFoundException cannotHappen) {
            }
            return;
          }
      }
      super.putAll(map);
  }

  /**
    * Returns this map's entry for the given key, or <tt>null</tt> if the map
    * does not contain an entry for the key.
    *
    * @return this map's entry for the given key, or <tt>null</tt> if the map
    * 	       does not contain an entry for the key.
    * @throws ClassCastException if the key cannot be compared with the keys
    *		  currently in the map.
    * @throws NullPointerException key is <tt>null</tt> and this map uses
    *		  natural order, or its comparator does not tolerate *
    *		  <tt>null</tt> keys.
    */
  private Entry<K,V> getEntry(Object key) {
    Entry<K,V> p = root;
    while (p != null) {
        int cmp = compare(key,p.key);
        if (cmp == 0)
      return p;
        else if (cmp < 0)
      p = p.left;
        else
      p = p.right;
    }
    return null;
  }


  /**
    * Gets the entry corresponding to the specified key; if no such entry
    * exists, returns the entry for the least key greater than the specified
    * key; if no such entry exists (i.e., the greatest key in the Tree is less
    * than the specified key), returns <tt>null</tt>.
    */
  private Entry<K,V> getCeilEntry(Object key) {
    Entry<K,V> p = root;
    if (p==null)
        return null;

    while (true) {
        int cmp = compare(key, p.key);
        if (cmp == 0) {
      return p;
        } else if (cmp < 0) {
      if (p.left != null)
          p = p.left;
      else
          return p;
        } else {
      if (p.right != null) {
          p = p.right;
      } else {
          Entry<K,V> parent = p.parent;
          Entry<K,V> ch = p;
          while (parent != null && ch == parent.right) {
        ch = parent;
        parent = parent.parent;
          }
          return parent;
      }
        }
    }
  }

  /**
    * Returns the entry for the greatest key less than the specified key; if
    * no such entry exists (i.e., the least key in the Tree is greater than
    * the specified key), returns <tt>null</tt>.
    */
  private Entry<K,V> getPrecedingEntry(Object key) {
    Entry<K,V> p = root;
    if (p==null)return null;

    while (true) {
      int cmp = compare(key, p.key);
      if (cmp > 0) {
        if (p.right != null) p = p.right;
        else return p;
        }else{
          if (p.left != null) {
            p = p.left;
          } else {
            Entry<K,V> parent = p.parent;
            Entry<K,V> ch = p;
            while (parent != null && ch == parent.left) {
              ch = parent;
              parent = parent.parent;
            }
            return parent;
          }
        }
    }//while true
  }

  /**
    * Returns the key corresonding to the specified Entry.  Throw
    * NoSuchElementException if the Entry is <tt>null</tt>.
    */
  private static <X,Y> X key(Entry<X,Y> e) {
      if (e==null)
          throw new NoSuchElementException();
      return e.key;
  }

  /**
    * Associates the specified value with the specified key in this map.
    * If the map previously contained a mapping for this key, the old
    * value is replaced.
    *
    * @param key key with which the specified value is to be associated.
    * @param value value to be associated with the specified key.
    *
    * @return previous value associated with specified key, or <tt>null</tt>
    *	       if there was no mapping for key.  A <tt>null</tt> return can
    *	       also indicate that the map previously associated <tt>null</tt>
    *	       with the specified key.
    * @throws    ClassCastException key cannot be compared with the keys
    *		  currently in the map.
    * @throws NullPointerException key is <tt>null</tt> and this map uses
    *		  natural order, or its comparator does not tolerate
    *		  <tt>null</tt> keys.
    */
  @Override
  public V put(K key, V value) {
    Entry<K,V> t = root;

    if (t == null) {
        incrementSize();
        root = new Entry<K,V>(key, value, null);
        return null;
    }

    while (true) {
        int cmp = compare(key, t.key);
        if (cmp == 0) {
      return t.setValue(value);
        } else if (cmp < 0) {
      if (t.left != null) {
          t = t.left;
      } else {
          incrementSize();
          t.left = new Entry<K,V>(key, value, t);
          fixAfterInsertion(t.left);
          return null;
      }
        } else { // cmp > 0
      if (t.right != null) {
          t = t.right;
      } else {
          incrementSize();
          t.right = new Entry<K,V>(key, value, t);
          fixAfterInsertion(t.right);
          return null;
      }
        }
    }
  }

  /**
    * Removes the mapping for this key from this RBTreeMap if present.
    *
    * @return previous value associated with specified key, or <tt>null</tt>
    *	       if there was no mapping for key.  A <tt>null</tt> return can
    *	       also indicate that the map previously associated
    *	       <tt>null</tt> with the specified key.
    *
    * @throws    ClassCastException key cannot be compared with the keys
    *		  currently in the map.
    * @throws NullPointerException key is <tt>null</tt> and this map uses
    *		  natural order, or its comparator does not tolerate
    *		  <tt>null</tt> keys.
    */
  @Override
  public V remove(Object key) {
    Entry<K,V> p = getEntry(key);
    if (p == null)
        return null;

    V oldValue = p.value;
    deleteEntry(p);
    return oldValue;
  }

  /**
    * Removes all mappings from this RBTreeMap.
    */
  @Override
  public void clear() {
    modCount++;
    size = 0;
    root = null;
  }

  /**
    * Returns a shallow copy of this <tt>RBTreeMap</tt> instance. (The keys and
    * values themselves are not cloned.)
    *
    * @return a shallow copy of this Map.
    */
  @Override
  public Object clone() {
    return new RBTreeMap<K,V>(this);
  }


  // Views

  /**
    * These fields are initialized to contain an instance of the appropriate
    * view the first time this view is requested.  The views are stateless,
    * so there's no reason to create more than one of each.
    */
  private transient Set<K>		keySet = null;
  private transient Set<Map.Entry<K,V>>		entrySet = null;
  private transient Collection<V>	values = null;

  /**
    * Returns a Set view of the keys contained in this map.  The set's
    * iterator will return the keys in ascending order.  The map is backed by
    * this <tt>RBTreeMap</tt> instance, so changes to this map are reflected in
    * the Set, and vice-versa.  The Set supports element removal, which
    * removes the corresponding mapping from the map, via the
    * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>, <tt>removeAll</tt>,
    * <tt>retainAll</tt>, and <tt>clear</tt> operations.  It does not support
    * the <tt>add</tt> or <tt>addAll</tt> operations.
    *
    * @return a set view of the keys contained in this RBTreeMap.
    */
  @Override
  public Set<K> keySet() {
    if (keySet == null) {
      keySet = new AbstractSet<K>() {

        @Override
        public java.util.Iterator<K> iterator() {
            return new Iterator<K>(KEYS);
        }

        @Override
        public int size() {
          return RBTreeMap.this.size();
        }

        @Override
        public boolean contains(Object o) {
          return containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
          return RBTreeMap.this.remove(o) != null;
        }

        @Override
        public void clear() {
          RBTreeMap.this.clear();
        }
      };
    }
    return keySet;
  }

  /**
    * Returns a collection view of the values contained in this map.  The
    * collection's iterator will return the values in the order that their
    * corresponding keys appear in the tree.  The collection is backed by
    * this <tt>RBTreeMap</tt> instance, so changes to this map are reflected in
    * the collection, and vice-versa.  The collection supports element
    * removal, which removes the corresponding mapping from the map through
    * the <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>,
    * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt> operations.
    * It does not support the <tt>add</tt> or <tt>addAll</tt> operations.
    *
    * @return a collection view of the values contained in this map.
    */
  @Override
  public Collection<V> values() {
    if (values == null) {
      values = new AbstractCollection<V>() {
        @Override
        public java.util.Iterator<V> iterator() {
            return new Iterator<V>(VALUES);
        }

        @Override
        public int size() {
            return RBTreeMap.this.size();
        }

        @Override
        public boolean contains(Object o) {
            for (Entry<K,V> e = firstEntry(); e != null; e = successor(e))
                if (valEquals(e.getValue(), o))
                    return true;
            return false;
        }

        @Override
        public boolean remove(Object o) {
          for (Entry<K,V> e = firstEntry(); e != null; e = successor(e)) {
            if (valEquals(e.getValue(), o)) {
                deleteEntry(e);
                return true;
            }
          }
          return false;
        }

        @Override
        public void clear() {
            RBTreeMap.this.clear();
        }
      };
    }
    return values;
  }

  /**
    * Returns a set view of the mappings contained in this map.  The set's
    * iterator returns the mappings in ascending key order.  Each element in
    * the returned set is a <tt>Map.Entry</tt>.  The set is backed by this
    * map, so changes to this map are reflected in the set, and vice-versa.
    * The set supports element removal, which removes the corresponding
    * mapping from the RBTreeMap, through the <tt>Iterator.remove</tt>,
    * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
    * <tt>clear</tt> operations.  It does not support the <tt>add</tt> or
    * <tt>addAll</tt> operations.
    *
    * @return a set view of the mappings contained in this map.
    */
  @Override
  public Set<Map.Entry<K,V>> entrySet() {
    if (entrySet == null) {
      entrySet = new AbstractSet<Map.Entry<K,V>>() {
        @Override
        public java.util.Iterator<Map.Entry<K,V>> iterator() {
          return new Iterator<Map.Entry<K,V>>(ENTRIES);
        }

        @Override
        public boolean contains(Object o) {
          if (!(o instanceof Map.Entry))
              return false;
          @SuppressWarnings("unchecked")
          Map.Entry<K,V> entry = (Map.Entry<K,V>)o;
          Object value = entry.getValue();
          Entry<K,V> p = getEntry(entry.getKey());
          return p != null && valEquals(p.getValue(), value);
        }

        @Override
        public boolean remove(Object o) {
          if (!(o instanceof Map.Entry))
              return false;
          @SuppressWarnings("unchecked")
          Map.Entry<K,V> entry = (Map.Entry<K,V>)o;
          Object value = entry.getValue();
          Entry<K,V> p = getEntry(entry.getKey());
          if (p != null && valEquals(p.getValue(), value)) {
              deleteEntry(p);
              return true;
          }
          return false;
        }

        @Override
        public int size() {
            return RBTreeMap.this.size();
        }

        @Override
        public void clear() {
            RBTreeMap.this.clear();
        }
      };
    }
    return entrySet;
  }

  /**
    * Returns a view of the portion of this map whose keys range from
    * <tt>fromKey</tt>, inclusive, to <tt>toKey</tt>, exclusive.  (If
    * <tt>fromKey</tt> and <tt>toKey</tt> are equal, the returned sorted map
    * is empty.)  The returned sorted map is backed by this map, so changes
    * in the returned sorted map are reflected in this map, and vice-versa.
    * The returned sorted map supports all optional map operations.<p>
    *
    * The sorted map returned by this method will throw an
    * <tt>IllegalArgumentException</tt> if the user attempts to insert a key
    * less than <tt>fromKey</tt> or greater than or equal to
    * <tt>toKey</tt>.<p>
    *
    * Note: this method always returns a <i>half-open range</i> (which
    * includes its low endpoint but not its high endpoint).  If you need a
    * <i>closed range</i> (which includes both endpoints), and the key type
    * allows for calculation of the successor a given key, merely request the
    * subrange from <tt>lowEndpoint</tt> to <tt>successor(highEndpoint)</tt>.
    * For example, suppose that <tt>m</tt> is a sorted map whose keys are
    * strings.  The following idiom obtains a view containing all of the
    * key-value mappings in <tt>m</tt> whose keys are between <tt>low</tt>
    * and <tt>high</tt>, inclusive:
    * 	    <pre>    SortedMap sub = m.submap(low, high+"\0");</pre>
    * A similar technique can be used to generate an <i>open range</i> (which
    * contains neither endpoint).  The following idiom obtains a view
    * containing all of the key-value mappings in <tt>m</tt> whose keys are
    * between <tt>low</tt> and <tt>high</tt>, exclusive:
    * 	    <pre>    SortedMap sub = m.subMap(low+"\0", high);</pre>
    *
    * @param fromKey low endpoint (inclusive) of the subMap.
    * @param toKey high endpoint (exclusive) of the subMap.
    *
    * @return a view of the portion of this map whose keys range from
    * 	       <tt>fromKey</tt>, inclusive, to <tt>toKey</tt>, exclusive.
    *
    * @throws NullPointerException if <tt>fromKey</tt> or <tt>toKey</tt> is
    *		  <tt>null</tt> and this map uses natural order, or its
    *		  comparator does not tolerate <tt>null</tt> keys.
    *
    * @throws IllegalArgumentException if <tt>fromKey</tt> is greater than
    *            <tt>toKey</tt>.
    */
  @Override
  public SortedMap<K,V> subMap(Object fromKey, Object toKey) {
    return new SubMap(fromKey, toKey);
  }

  /**
    * Returns a view of the portion of this map whose keys are strictly less
    * than <tt>toKey</tt>.  The returned sorted map is backed by this map, so
    * changes in the returned sorted map are reflected in this map, and
    * vice-versa.  The returned sorted map supports all optional map
    * operations.<p>
    *
    * The sorted map returned by this method will throw an
    * <tt>IllegalArgumentException</tt> if the user attempts to insert a key
    * greater than or equal to <tt>toKey</tt>.<p>
    *
    * Note: this method always returns a view that does not contain its
    * (high) endpoint.  If you need a view that does contain this endpoint,
    * and the key type allows for calculation of the successor a given key,
    * merely request a headMap bounded by <tt>successor(highEndpoint)</tt>.
    * For example, suppose that suppose that <tt>m</tt> is a sorted map whose
    * keys are strings.  The following idiom obtains a view containing all of
    * the key-value mappings in <tt>m</tt> whose keys are less than or equal
    * to <tt>high</tt>:
    * <pre>
    *     SortedMap head = m.headMap(high+"\0");
    * </pre>
    *
    * @param toKey high endpoint (exclusive) of the headMap.
    * @return a view of the portion of this map whose keys are strictly
    * 	       less than <tt>toKey</tt>.
    * @throws NullPointerException if <tt>toKey</tt> is <tt>null</tt> and
    *		  this map uses natural order, or its comparator does * not
    *		  tolerate <tt>null</tt> keys.
    */
  @Override
  public SortedMap<K,V> headMap(K toKey) {
    return new SubMap(toKey, true);
  }

  /**
    * Returns a view of the portion of this map whose keys are greater than
    * or equal to <tt>fromKey</tt>.  The returned sorted map is backed by
    * this map, so changes in the returned sorted map are reflected in this
    * map, and vice-versa.  The returned sorted map supports all optional map
    * operations.<p>
    *
    * The sorted map returned by this method will throw an
    * <tt>IllegalArgumentException</tt> if the user attempts to insert a key
    * less than <tt>fromKey</tt>.<p>
    *
    * Note: this method always returns a view that contains its (low)
    * endpoint.  If you need a view that does not contain this endpoint, and
    * the element type allows for calculation of the successor a given value,
    * merely request a tailMap bounded by <tt>successor(lowEndpoint)</tt>.
    * For For example, suppose that suppose that <tt>m</tt> is a sorted map
    * whose keys are strings.  The following idiom obtains a view containing
    * all of the key-value mappings in <tt>m</tt> whose keys are strictly
    * greater than <tt>low</tt>: <pre>
    *     SortedMap tail = m.tailMap(low+"\0");
    * </pre>
    *
    * @param fromKey low endpoint (inclusive) of the tailMap.
    * @return a view of the portion of this map whose keys are greater
    * 	       than or equal to <tt>fromKey</tt>.
    * @throws    NullPointerException fromKey is <tt>null</tt> and this
    *		  map uses natural ordering, or its comparator does
    *            not tolerate <tt>null</tt> keys.
    */
  @Override
  public SortedMap<K,V> tailMap(K fromKey) {
    return new SubMap(fromKey, false);
  }

  private class SubMap extends AbstractMap<K,V>
         implements SortedMap<K,V>, java.io.Serializable {

    // fromKey is significant only if fromStart is false.  Similarly,
    // toKey is significant only if toStart is false.
    private boolean fromStart = false, toEnd = false;
    private Object  fromKey,           toKey;

    SubMap(Object fromKey, Object toKey) {
      if (compare(fromKey, toKey) > 0)
        throw new IllegalArgumentException("fromKey > toKey");
      this.fromKey = fromKey;
      this.toKey = toKey;
    }

    SubMap(Object key, boolean headMap) {
      if (headMap) {
          fromStart = true;
          toKey = key;
      } else {
          toEnd = true;
          fromKey = key;
      }
    }

    SubMap(boolean fromStart, Object fromKey, boolean toEnd, Object toKey){
      this.fromStart = fromStart;
      this.fromKey= fromKey;
      this.toEnd = toEnd;
      this.toKey = toKey;
    }

    @Override
    public boolean isEmpty() {
      return entrySet.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
      return inRange(key) && RBTreeMap.this.containsKey(key);
    }

    @Override
    public V get(Object key) {
      if (!inRange(key))
        return null;
      return RBTreeMap.this.get(key);
    }

    @Override
    public V put(K key, V value) {
      if (!inRange(key))
        throw new IllegalArgumentException("key out of range");
      return RBTreeMap.this.put(key, value);
    }

    @Override
    public Comparator<? super K> comparator() {
        return comparator;
    }

    @Override
    public K firstKey() {
        return key(fromStart ? firstEntry() : getCeilEntry(fromKey));
    }

    @Override
    public K lastKey() {
        return key(toEnd ? lastEntry() : getPrecedingEntry(toKey));
    }

    private transient Set<Map.Entry<K, V>> entrySet = new EntrySetView();

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entrySet;
    }

    private class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
      private transient int size = -1, sizeModCount;

      @Override
      public int size() {
        if (size == -1 || sizeModCount != RBTreeMap.this.modCount) {
          size = 0;  sizeModCount = RBTreeMap.this.modCount;
          java.util.Iterator<Map.Entry<K,V>> i = iterator();
          while (i.hasNext()) {
            size++;
            i.next();
          }
        }
        return size;
      } // size

      @Override
      public boolean isEmpty() {
        return !iterator().hasNext();
      }// isEmpty

      @Override
      public boolean contains(Object o) {
        if (!(o instanceof Map.Entry))
          return false;

        @SuppressWarnings("unchecked")
        Map.Entry<K,V> entry = (Map.Entry<K,V>)o;
        Object key = entry.getKey();
        if (!inRange(key))
          return false;
        RBTreeMap.Entry<K,V> node = getEntry(key);
        return node != null &&
                 valEquals(node.getValue(), entry.getValue());
      } // contains

      @Override
      public boolean remove(Object o) {
        if (!(o instanceof Map.Entry))
          return false;
        
        @SuppressWarnings("unchecked")
        Map.Entry<K,V> entry = (Map.Entry<K,V>)o;
        Object key = entry.getKey();
        if (!inRange(key))
          return false;
        RBTreeMap.Entry<K,V> node = getEntry(key);
        if (node!=null && valEquals(node.getValue(),entry.getValue())){
          deleteEntry(node);
          return true;
        }
        return false;
      }

      @Override
      public java.util.Iterator<Map.Entry<K,V>> iterator() {
        return new Iterator<Map.Entry<K,V>>(
                  (fromStart ? firstEntry() : getCeilEntry(fromKey)),
                  (toEnd     ? null	      : getCeilEntry(toKey)));
      }
    } // EntrySetView

    @Override
    public SortedMap<K,V> subMap(Object fromKey, Object toKey) {
      if (!inRange(fromKey))
        throw new IllegalArgumentException("fromKey out of range");
      if (!inRange2(toKey))
        throw new IllegalArgumentException("toKey out of range");
      return new SubMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K,V> headMap(Object toKey) {
      if (!inRange2(toKey))
        throw new IllegalArgumentException("toKey out of range");
      return new SubMap(fromStart, fromKey, false, toKey);
    }

    @Override
    public SortedMap<K,V> tailMap(Object fromKey) {
      if (!inRange(fromKey))
        throw new IllegalArgumentException("fromKey out of range");
      return new SubMap(false, fromKey, toEnd, toKey);
    }

    private boolean inRange(Object key) {
      return (fromStart || compare(key, fromKey) >= 0) &&
                     (toEnd     || compare(key, toKey)   <  0);
    }

    // This form allows the high endpoint (as well as all legit keys)
    private boolean inRange2(Object key) {
      return (fromStart || compare(key, fromKey) >= 0) &&
                 (toEnd     || compare(key, toKey)   <= 0);
    }
    static final long serialVersionUID = 4333473260468321526L;
  } // SubMap

  // Types of Iterators
  private static final int KEYS = 0;

  private static final int VALUES = 1;

  private static final int ENTRIES = 2;

  /**
    * RBTreeMap Iterator.
    */
  private class Iterator<E> implements java.util.Iterator<E> {
    private int type;

    private int expectedModCount = RBTreeMap.this.modCount;

    private Entry<K,V> lastReturned = null;

    private Entry<K,V> next;

    private Entry<K,V> firstExcluded = null;

    Iterator(int type) {
      this.type = type;
      next = firstEntry();
    }

    Iterator(Entry<K,V> first, Entry<K,V> firstExcluded) {
      type = ENTRIES;
      next = first;
      this.firstExcluded = firstExcluded;
    }

    @Override
    public boolean hasNext() {
      return next != firstExcluded;
    } //hasNext

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
      if (next == firstExcluded)
        throw new NoSuchElementException();

      if (modCount != expectedModCount)
        throw new ConcurrentModificationException();

      lastReturned = next;
      next = successor(next);
      return (E)(type == KEYS ? lastReturned.key :
        (type == VALUES ? lastReturned.value : lastReturned));
    } // next

    @Override
    public void remove() {
      if (lastReturned == null)
        throw new IllegalStateException();

      if (modCount != expectedModCount)
        throw new ConcurrentModificationException();

      deleteEntry(lastReturned);
      expectedModCount++;
      lastReturned = null;
    } // remove
  } //Iterator

  /**
    * Compares two keys using the correct comparison method for this RBTreeMap.
    */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private int compare(Object k1, Object k2) {
    return (comparator==null ? ((Comparable)k1).compareTo(k2)
       : comparator.compare((K)k1, (K)k2));
  }

  /**
    * Test two values  for equality.  Differs from o1.equals(o2) only in
    * that it copes with with <tt>null</tt> o1 properly.
    */
  private static boolean valEquals(Object o1, Object o2) {
    return (o1==null ? o2==null : o1.equals(o2));
  }

  private static final boolean RED   = false;

  private static final boolean BLACK = true;

  /**
    * Node in the Tree.  Doubles as a means to pass key-value pairs back to
    * user (see Map.Entry).
    */

  private static class Entry<M,N> implements Map.Entry<M,N> {
    M key;
    N value;
    Entry<M,N> left = null;
    Entry<M,N> right = null;
    Entry<M,N> parent;
    boolean color = BLACK;

    /** Make a new cell with given key, value, and parent, and with
      * <tt>null</tt> child links, and BLACK color.
      */
    Entry(M key, N value, Entry<M,N> parent) {
      this.key = key;
      this.value = value;
      this.parent = parent;
    }

    /**
      * Returns the key.
      *
      * @return the key.
      */
    @Override
    public M getKey() {
        return key;
    }

    /**
      * Returns the value associated with the key.
      *
      * @return the value associated with the key.
      */
    @Override
    public N getValue() {
        return value;
    }

    /**
      * Replaces the value currently associated with the key with the given
      * value.
      *
      * @return the value associated with the key before this method was
      * called.
      */
    @Override
    public N setValue(N value) {
        N oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
      return false;
        @SuppressWarnings("unchecked")
        Map.Entry<M,N> e = (Map.Entry<M,N>)o;

        return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
    } // equals

    @Override
    public int hashCode() {
        int keyHash = (key==null ? 0 : key.hashCode());
        int valueHash = (value==null ? 0 : value.hashCode());
        return keyHash ^ valueHash;
    } // hashCode

    @Override
    public String toString() {
        return key + "=" + value;
    }
  } // Entry

  /**
    * Returns the first Entry in the RBTreeMap (according to the RBTreeMap's
    * key-sort function).  Returns null if the RBTreeMap is empty.
    */
  private Entry<K,V> firstEntry() {
    Entry<K,V> p = root;
    if (p != null)
      while (p.left != null)
        p = p.left;
    return p;
  }

  /**
    * Returns the last Entry in the RBTreeMap (according to the RBTreeMap's
    * key-sort function).  Returns null if the RBTreeMap is empty.
    */
  private Entry<K,V> lastEntry() {
    Entry<K,V> p = root;
    if (p != null)
      while (p.right != null)
        p = p.right;
    return p;
  }

  /**
    * Returns the successor of the specified Entry, or null if no such.
    */
  private Entry<K,V> successor(Entry<K,V> t) {
    if (t == null)
      return null;
    else if (t.right != null) {
      Entry<K,V> p = t.right;
      while (p.left != null)
        p = p.left;
      return p;
    } else {
      Entry<K,V> p = t.parent;
      Entry<K,V> ch = t;
      while (p != null && ch == p.right) {
        ch = p;
        p = p.parent;
      }
      return p;
    }
  } // successor

  /**
    * Balancing operations.
    *
    * Implementations of rebalancings during insertion and deletion are
    * slightly different than the CLR version.  Rather than using dummy
    * nilnodes, we use a set of accessors that deal properly with null.  They
    * are used to avoid messiness surrounding nullness checks in the main
    * algorithms.
    */

  private static boolean colorOf(Entry<?,?> p) {
    return (p == null ? BLACK : p.color);
  }

  private static <X,Y> Entry<X,Y> parentOf(Entry<X,Y> p) {
    return (p == null ? null: p.parent);
  }

  private static void setColor(Entry<?,?> p, boolean c) {
    if (p != null)  p.color = c;
  }

  private static <X,Y> Entry<X,Y> leftOf(Entry<X,Y> p) {
    return (p == null)? null: p.left;
  }

  private static <X,Y> Entry<X,Y> rightOf(Entry<X,Y> p) {
    return (p == null)? null: p.right;
  }

  /** From CLR **/
  private void rotateLeft(Entry<K,V> p) {
    Entry<K,V> r = p.right;
    p.right = r.left;

    if (r.left != null)
      r.left.parent = p;
    r.parent = p.parent;

    if (p.parent == null)
      root = r;
    else if (p.parent.left == p)
      p.parent.left = r;
    else
      p.parent.right = r;
    r.left = p;
    p.parent = r;
  }

  /** From CLR **/
  private void rotateRight(Entry<K,V> p) {
    Entry<K,V> l = p.left;
    p.left = l.right;

    if (l.right != null) l.right.parent = p;
    l.parent = p.parent;

    if (p.parent == null)
        root = l;
    else if (p.parent.right == p)
        p.parent.right = l;
    else p.parent.left = l;

    l.right = p;
    p.parent = l;
  }


  /** From CLR **/
  private void fixAfterInsertion(Entry<K,V> x) {
    x.color = RED;

    while (x != null && x != root && x.parent.color == RED) {
      if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
        Entry<K,V> y = rightOf(parentOf(parentOf(x)));
        if (colorOf(y) == RED) {
          setColor(parentOf(x), BLACK);
          setColor(y, BLACK);
          setColor(parentOf(parentOf(x)), RED);
          x = parentOf(parentOf(x));
        } else {
          if (x == rightOf(parentOf(x))) {
            x = parentOf(x);
            rotateLeft(x);
          }
          setColor(parentOf(x), BLACK);
          setColor(parentOf(parentOf(x)), RED);
          if (parentOf(parentOf(x)) != null)
            rotateRight(parentOf(parentOf(x)));
        }
      } else {
        Entry<K,V> y = leftOf(parentOf(parentOf(x)));
        if (colorOf(y) == RED) {
            setColor(parentOf(x), BLACK);
            setColor(y, BLACK);
            setColor(parentOf(parentOf(x)), RED);
            x = parentOf(parentOf(x));
        } else {
          if (x == leftOf(parentOf(x))) {
            x = parentOf(x);
            rotateRight(x);
          }
          setColor(parentOf(x),  BLACK);
          setColor(parentOf(parentOf(x)), RED);
          if (parentOf(parentOf(x)) != null)
            rotateLeft(parentOf(parentOf(x)));
        }
      }
    }
    root.color = BLACK;
  } // fixAfterInsertion

  /**
    * Delete node p, and then rebalance the tree.
    */
  private void deleteEntry(Entry<K,V> p) {
    decrementSize();

    // If strictly internal, first swap position with successor.
    if (p.left != null && p.right != null) {
        Entry<K,V> s = successor(p);
        swapPosition(s, p);
    }

    // Start fixup at replacement node, if it exists.
    Entry<K,V> replacement = (p.left != null ? p.left : p.right);

    if (replacement != null) {
      // Link replacement to parent
      replacement.parent = p.parent;
      if (p.parent == null)
        root = replacement;
      else if (p == p.parent.left)
        p.parent.left  = replacement;
      else
        p.parent.right = replacement;

    // Null out links so they are OK to use by fixAfterDeletion.
    p.left = p.right = p.parent = null;

    // Fix replacement
    if (p.color == BLACK)
      fixAfterDeletion(replacement);
    } else if (p.parent == null) { // return if we are the only node.
      root = null;
    } else { //  No children. Use self as phantom replacement and unlink.
      if (p.color == BLACK)
        fixAfterDeletion(p);

      if (p.parent != null) {
        if (p == p.parent.left)
          p.parent.left = null;
        else if (p == p.parent.right)
          p.parent.right = null;
        p.parent = null;
      }
    }
  } // deleteEntry

  /** From CLR **/
  private void fixAfterDeletion(Entry<K,V> x) {
    while (x != root && colorOf(x) == BLACK) {
      if (x == leftOf(parentOf(x))) {
        Entry<K,V> sib = rightOf(parentOf(x));

        if (colorOf(sib) == RED) {
          setColor(sib, BLACK);
          setColor(parentOf(x), RED);
          rotateLeft(parentOf(x));
          sib = rightOf(parentOf(x));
        }

        if (colorOf(leftOf(sib))  == BLACK &&
            colorOf(rightOf(sib)) == BLACK) {
          setColor(sib,  RED);
          x = parentOf(x);
        } else {
          if (colorOf(rightOf(sib)) == BLACK) {
            setColor(leftOf(sib), BLACK);
            setColor(sib, RED);
            rotateRight(sib);
            sib = rightOf(parentOf(x));
          }
          setColor(sib, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(rightOf(sib), BLACK);
          rotateLeft(parentOf(x));
          x = root;
        }
      } else { // symmetric
        Entry<K,V> sib = leftOf(parentOf(x));

        if (colorOf(sib) == RED) {
          setColor(sib, BLACK);
          setColor(parentOf(x), RED);
          rotateRight(parentOf(x));
          sib = leftOf(parentOf(x));
        }

        if (colorOf(rightOf(sib)) == BLACK &&
          colorOf(leftOf(sib)) == BLACK) {
          setColor(sib,  RED);
          x = parentOf(x);
        } else {
          if (colorOf(leftOf(sib)) == BLACK) {
            setColor(rightOf(sib), BLACK);
            setColor(sib, RED);
            rotateLeft(sib);
            sib = leftOf(parentOf(x));
          }
          setColor(sib, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(leftOf(sib), BLACK);
          rotateRight(parentOf(x));
          x = root;
        }
      }
    }
    setColor(x, BLACK);
  } // fixAfterDeletion

  /**
    * Swap the linkages of two nodes in a tree.
    */
  private void swapPosition(Entry<K,V> x, Entry<K,V> y) {
    // Save initial values.
    Entry<K,V> px = x.parent, lx = x.left, rx = x.right;
    Entry<K,V> py = y.parent, ly = y.left, ry = y.right;
    boolean xWasLeftChild = px != null && x == px.left;
    boolean yWasLeftChild = py != null && y == py.left;

    // Swap, handling special cases of one being the other's parent.
    if (x == py) {  // x was y's parent
      x.parent = y;

      if (yWasLeftChild) {
        y.left = x;
        y.right = rx;
      } else {
        y.right = x;
        y.left = lx;
      }
    } else {
      x.parent = py;

      if (py != null) {
        if (yWasLeftChild)
          py.left = x;
        else
          py.right = x;
      }
      y.left = lx;
      y.right = rx;
    }

    if (y == px) { // y was x's parent
      y.parent = x;
      if (xWasLeftChild) {
        x.left = y;
        x.right = ry;
      } else {
        x.right = y;
        x.left = ly;
      }
    } else {
      y.parent = px;
      if (px != null) {
        if (xWasLeftChild)
          px.left = y;
        else
          px.right = y;
      }
      x.left = ly;
      x.right = ry;
    }

    // Fix children's parent pointers
    if (x.left != null)
      x.left.parent = x;

    if (x.right != null)
      x.right.parent = x;

    if (y.left != null)
      y.left.parent = y;

    if (y.right != null)
      y.right.parent = y;

    // Swap colors
    boolean c = x.color;
    x.color = y.color;
    y.color = c;

    // Check if root changed
    if (root == x)
        root = y;
    else if (root == y)
        root = x;
  } // swapPosition




  /**
    * Save the state of the <tt>RBTreeMap</tt> instance to a stream (i.e.,
    * serialize it).
    *
    * @serialData The <i>size</i> of the RBTreeMap (the number of key-value
    *		   mappings) is emitted (int), followed by the key (Object)
    *		   and value (Object) for each key-value mapping represented
    *		   by the RBTreeMap. The key-value mappings are emitted in
    *		   key-order (as determined by the RBTreeMap's Comparator,
    *		   or by the keys' natural ordering if the RBTreeMap has no
    *             Comparator).
    */
  private void writeObject(java.io.ObjectOutputStream s)
      throws java.io.IOException {

    // Write out the Comparator and any hidden stuff
    s.defaultWriteObject();

    // Write out size (number of Mappings)
    s.writeInt(size);

          // Write out keys and values (alternating)
    for (java.util.Iterator<Map.Entry<K,V>> i = entrySet().iterator(); i.hasNext(); ) {
              Map.Entry<K,V> e = i.next();
              s.writeObject(e.getKey());
              s.writeObject(e.getValue());
    }
  } // writeObject



  /**
    * Reconstitute the <tt>RBTreeMap</tt> instance from a stream (i.e.,
    * deserialize it).
    */
  private void readObject(final java.io.ObjectInputStream s)
      throws java.io.IOException, ClassNotFoundException {

    // Read in the Comparator and any hidden stuff
    s.defaultReadObject();

    // Read in size
    int size = s.readInt();

    buildFromSorted(size, null, s, null);
  } // readObject

  /** Intended to be called only from TreeSet.readObject */
  void readTreeSet(int size, java.io.ObjectInputStream s, V defaultVal)
      throws java.io.IOException, ClassNotFoundException {
    buildFromSorted(size, null, s, defaultVal);
  }

  /** Intended to be called only from TreeSet.addAll */
  void addAllForTreeSet(SortedSet<K> set, V defaultVal) {
    try {
      buildFromSorted(set.size(), set.iterator(), null, defaultVal);
    } catch (java.io.IOException cannotHappen) {
    } catch (ClassNotFoundException cannotHappen) {
    }
  }


  /**
    * Linear time tree building algorithm from sorted data.  Can accept keys
    * and/or values from iterator or stream. This leads to too many
    * parameters, but seems better than alternatives.  The four formats
    * that this method accepts are:
    *
    *	  1) An iterator of Map.Entries.  (it != null, defaultVal == null).
    *    2) An iterator of keys.         (it != null, defaultVal != null).
    *	  3) A stream of alternating serialized keys and values.
    *					  (it == null, defaultVal == null).
    *	  4) A stream of serialized keys. (it == null, defaultVal != null).
    *
    * It is assumed that the comparator of the RBTreeMap is already set prior
    * to calling this method.
    *
    * @param size the number of keys (or key-value pairs) to be read from
    *	      the iterator or stream.
    * @param it If non-null, new entries are created from entries
    *        or keys read from this iterator.
    * @param str If non-null, new entries are created from keys and
    *	      possibly values read from this stream in serialized form.
    *        Exactly one of it and str should be non-null.
    * @param defaultVal if non-null, this default value is used for
    *        each value in the map.  If null, each value is read from
    *        iterator or stream, as described above.
    * @throws IOException propagated from stream reads. This cannot
    *         occur if str is null.
    * @throws ClassNotFoundException propagated from readObject.
    *         This cannot occur if str is null.
    */
  private void buildFromSorted(int size, java.util.Iterator<?> it,
                                java.io.ObjectInputStream str,
                                V defaultVal)
    throws  java.io.IOException, ClassNotFoundException {

    this.size = size;
    root = buildFromSorted(0, 0, size-1, computeRedLevel(size),
                             it, str, defaultVal);
  } // buildFromSorted

  /**
    * Recursive "helper method" that does the real work of the
    * of the previous method.  Identically named parameters have
    * identical definitions.  Additional parameters are documented below.
    * It is assumed that the comparator and size fields of the RBTreeMap are
    * already set prior to calling this method.  (It ignores both fields.)
    *
    * @param level the current level of tree. Initial call should be 0.
    * @param lo the first element index of this subtree. Initial should be 0.
    * @param hi the last element index of this subtree.  Initial should be
    *	      size-1.
    * @param redLevel the level at which nodes should be red.
    *        Must be equal to computeRedLevel for tree of this size.
    */
  @SuppressWarnings("unchecked")
  private static <X,Y> Entry<X,Y> buildFromSorted(int level, int lo, int hi,
                                       int redLevel,
                                       java.util.Iterator<?> it,
                                       java.io.ObjectInputStream str,
                                       Y defaultVal)
    throws  java.io.IOException, ClassNotFoundException {
    /*
     * Strategy: The root is the middlemost element. To get to it, we
     * have to first recursively construct the entire left subtree,
     * so as to grab all of its elements. We can then proceed with right
     * subtree.
     *
     * The lo and hi arguments are the minimum and maximum
     * indices to pull out of the iterator or stream for current subtree.
     * They are not actually indexed, we just proceed sequentially,
     * ensuring that items are extracted in corresponding order.
     */

    if (hi < lo) return null;

    int mid = (lo + hi) / 2;

    Entry<X,Y> left  = null;
    if (lo < mid)
      left = buildFromSorted(level+1, lo, mid - 1, redLevel,
                               it, str, defaultVal);

    // extract key and/or value from iterator or stream
    X key;
    Y value;

    if (it != null) { // use iterator

      if (defaultVal==null) {
        Map.Entry<?,?> entry = (Map.Entry<?,?>)it.next();
        key = (X)entry.getKey();
        value = (Y)entry.getValue();
      } else {
        key = (X)it.next();
        value = defaultVal;
      }
    } else { // use stream
      key = (X)str.readObject();
      value = (defaultVal != null ? defaultVal : (Y)str.readObject());
    }

    Entry<X,Y> middle =  new Entry<X,Y>(key, value, null);

    // color nodes in non-full bottommost level red
    if (level == redLevel)
      middle.color = RED;

    if (left != null) {
      middle.left = left;
      left.parent = middle;
    }

    if (mid < hi) {
      Entry<X,Y> right = buildFromSorted(level+1, mid+1, hi, redLevel,
                                    it, str, defaultVal);
      middle.right = right;
      right.parent = middle;
    }

    return middle;

  } // Entry buildFromSorted

  /**
    * Find the level down to which to assign all nodes BLACK.  This is the
    * last `full' level of the complete binary tree produced by
    * buildTree. The remaining nodes are colored RED. (This makes a `nice'
    * set of color assignments wrt future insertions.) This level number is
    * computed by finding the number of splits needed to reach the zeroeth
    * node.  (The answer is ~lg(N), but in any case must be computed by same
    * quick O(lg(N)) loop.)
    */
  private static int computeRedLevel(int sz) {
    int level = 0;
    for (int m = sz - 1; m >= 0; m = m / 2 - 1)
        level++;
    return level;
  }

} // class RBTreeMap
