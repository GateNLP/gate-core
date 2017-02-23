/*
 *  HashMapLong.java
 *
 *  Copyright (c) 2001, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  D.Ognyanoff, 15/Nov/2001
 *
 */

 package gate.util;

/**
 * simple cut-off version of the hashmap with native long's as keys
 * only get,put and isEmpty methods are implemented().
 * This map is used in very private case in the SimpleSortedSet class.
 * The main purpose is to optimize the speed of the SinglePhaseTransducer.transduce()
 */

public class HashMapLong {

    /**
     * The hash table data.
     */
    private transient Entry table[];

    private transient int count;

    private int threshold;

    private float loadFactor;

    /**
     * the main constructor. see the HashMap constructor for description
     */
    public HashMapLong(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Initial Capacity: "+
                                               initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal Load factor: "+
                                               loadFactor);
        if (initialCapacity==0)
            initialCapacity = 1;
        this.loadFactor = loadFactor;
        table = new Entry[initialCapacity];
        threshold = (int)(initialCapacity * loadFactor);
    }

    public HashMapLong(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public HashMapLong() {
        this(11, 0.75f);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Object get(long key) {
        Entry tab[] = table;
    int hash = (int)(key ^ (key >> 32));
    int index = (hash & 0x7FFFFFFF) % tab.length;
    for (Entry e = tab[index]; e != null; e = e.next)
        if ((e.hash == hash) && key == e.key)
            return e.value;

        return null;
    }

    /**
     * Rehashes the contents of this map into a new <tt>HashMapLong</tt> instance
     * with a larger capacity. This method is called automatically when the
     * number of keys in this map exceeds its capacity and load factor.
     */
    private void rehash() {
      int oldCapacity = table.length;
      Entry oldMap[] = table;

      int newCapacity = oldCapacity * 2 + 1;
      Entry newMap[] = new Entry[newCapacity];

      threshold = (int) (newCapacity * loadFactor);
      table = newMap;

      for (int i = oldCapacity; i-- > 0; ) {
        for (Entry old = oldMap[i]; old != null; ) {
          Entry e = old;
          old = old.next;

          int index = (e.hash & 0x7FFFFFFF) % newCapacity;
          e.next = newMap[index];
          newMap[index] = e;
        }
      }
    }

    public Object put(long key, Object value) {
        // Makes sure the key is not already in the HashMapLong.
        Entry tab[] = table;
        int hash = (int)(key ^ (key >> 32));
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (Entry e = tab[index] ; e != null ; e = e.next) {
            if ((e.hash == hash) && key == e.key) {
                Object old = e.value;
                e.value = value;
                return old;
            }
        }

        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded
            rehash();

                tab = table;
                index = (hash & 0x7FFFFFFF) % tab.length;
        }

        // Creates the new entry.
        Entry e = new Entry(hash, key, value, tab[index]);
        tab[index] = e;
        count++;
        return null;
    }

    /**
     * HashMapLong collision list entry.
     */
    private static class Entry {
        int hash;
        long key;
        Object value;
        Entry next;

        Entry(int hash, long key, Object value, Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    } //Entry
} // hasnMapLong
