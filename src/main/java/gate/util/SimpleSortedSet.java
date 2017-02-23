/*
 *  SimpleSortedSet.java
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
import java.util.*;

/**
 * The purpose of this Map is to combine the functionality found in
 * TreeSet, especially first() and tailSet() with the hashcode driven map
 * using native long as key to hold the annotations ordered by their offset.
 * It is used in the SinglePhaseTransducer.transduce()
 */
public class SimpleSortedSet {

/**
 * the Map contianing Lists with the annotations by offset as key
 */
    HashMapLong m = new HashMapLong();

/**
 * the initial dimension of the offsets array
 */
    static final int INCREMENT = 4;
/**
 * the array containing the distinct offsets in the map
 * It should be sorted before usinf the first and tailSet methods
 */
    long[] theArray = new long[INCREMENT];
/**
 * tailSet generated index - this is the index found to be les or equl to the
 * argument provided when tailSet() methos was invoked
 */
    int tsindex = 0;
/**
 * size of the offset's array
 */
    int size = 0;

/**
 * the Contructor. fills the allocated offset's array with the large possible
 * valuse so any valis value will be placed on front of them.
 */
    public SimpleSortedSet()
    {
        java.util.Arrays.fill(theArray, Long.MAX_VALUE);
    }
/**
 * the get method retrive the List element by offset key given as argument
 * @param elValue the offset to which the list should be retrived.
 * @return the list with annotations by this offset or null if there is no
 * such offset placed in the map
 */
    public Object get(long elValue)
    {
        return m.get(elValue);
    }
/**
 * add the new annotation to the annotation list for the given offset
 * Note: if the offset is not in the map new empty list is created and the
 * annotation is added to it
 * @param elValue the offset of the annotation
 * @param o the annotation instance to be placed in the list
 * @return true if the offset is already in the map false otherwise
 */
    @SuppressWarnings("unchecked")
    public boolean add(long elValue, Object o)
    {
// get the list by offset
        Object f = m.get(elValue);
        if (f == null)
        {
// there is no such offset in the map
// create one empty list
            f = new ArrayList<Object>();
// put it in the map
            m.put(elValue, f);
// add the annotation to it
            ((List<Object>)f).add(o);
// update the size of the offsets array if necessery
            if (theArray.length == size)
            {
            // allocate
                long[] temp = new long[theArray.length*2]; // + INCREMENT
            // copy the old
                System.arraycopy(theArray, 0, temp, 0, theArray.length);
            // fill the rest wit the large possible value
                java.util.Arrays.fill(temp, theArray.length, temp.length , Integer.MAX_VALUE);
            // get it
                theArray = temp;
            }
            // put the offset into the array
            theArray[size++] = elValue;
            // indicate the 'new element' condition
            return false;
        }
        // yes we already have an annotation liss for this offset
        // add the annotation to it
        ((List<Object>)f).add(o);

        return true;
    } // add

    /**
     * sort the offset's array in ascending way
     */
    public void sort()
    {
      java.util.Arrays.sort(theArray,0,size);
    }
    /**
     * retrive the smallest offset of the array. If there was a tailset before
     * then retrive the smallest or equal element given the index calculated ad tailSet() method
     * @return the offset value conforming the above conditions
     */
    public long first()
    {
        if (tsindex >= size) return -1;
        return theArray[tsindex];
    } // first()

    /**
     * calculate the index of the first element in the offset's array that is
     * equal or not greater then the given one
     * @param elValue the value to search for
     * @return actually <B>this</B>. Used to mimic the TreeSet.tailSet()
     */
    public SimpleSortedSet tailSet(long elValue)
    {
        // small speedup opt. because most of the queries are about to the same
        // or the next element in the array
//        if (tsindex < theArray.length && elValue != theArray[tsindex])
//        {
            if (tsindex<(size-1) && elValue > theArray[tsindex] &&
                elValue <= theArray[tsindex+1])
                {
                    tsindex++;
                   return this;
                }
            int index = java.util.Arrays.binarySearch(theArray, elValue);
            if (index < 0)
                index = ~index;
            tsindex = index;
//        }
        return this;
    } // tailSet()

    /**
     * is the map is empty
     * @return true if teher is no element in the map
     */
    public boolean isEmpty()
    {
        return m.isEmpty();
    } // isEmpty

    // return the number of distinct offsets in the map
    public int size()
    {
        return size;
    }
} //SimpleSortedSet

