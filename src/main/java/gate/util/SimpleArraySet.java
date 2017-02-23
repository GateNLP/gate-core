/*
 *  SimpleArraySet.java
 *
 *  Copyright (c) 2001, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *   D.Ognyanoff, 5/Nov/2001
 *
 *  $Id: SimpleArraySet.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */


package gate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * A specific *partial* implementation of the Set interface used for
 * high performance and memory reduction on small sets. Used in
 * gate.fsm.State, for example
 */
public class SimpleArraySet<T> implements Serializable, Iterable<T>
{

  private static final long serialVersionUID = 7356655846408155644L;

  /**
   * The array storing the elements
   */
  T[] theArray = null;

  public int size()
  {
      return theArray == null ? 0 : theArray.length;
  }

  public Collection<T> asCollection()
  {
      if (theArray == null) return new ArrayList<T>();
      return Arrays.asList(theArray);
  }

  @SuppressWarnings("unchecked")
  public boolean add(T tr)
  {
    if (theArray == null)
    {
      theArray = (T[])new Object[1];
      theArray[0] = tr;
    } else {
      int newsz = theArray.length+1;
      int index = java.util.Arrays.binarySearch(theArray, tr);
      if (index < 0)
      {
        index = ~index;
        T[] temp = (T[])new Object[newsz];
        int i;
        for (i = 0; i < index; i++)
        {
          temp[i] = theArray[i]; theArray[i] = null;
        }
        for (i = index+1; i<newsz; i++)
        {
          temp[i] = theArray[i-1]; theArray[i-1] = null;
        }
        temp[index] = tr;
        theArray = temp;
      } else {
        theArray[index] = tr;
      }
    } // if initially empty
    return true;
  } // add

  /**
   * iterator
   */
  @Override
  public java.util.Iterator<T> iterator()
  {
    if (theArray == null)
      return new java.util.Iterator<T>()
        {
          @Override
          public boolean hasNext() {return false;}
          @Override
          public T next() { return null; }
          @Override
          public void remove() {}
        };
    else
      return new java.util.Iterator<T>()
        {
          int count = 0;
          @Override
          public boolean hasNext()
          {
            if (theArray == null)
              throw new RuntimeException("");
            return count < theArray.length;
          }
          @Override
          public T next() {
            if (theArray == null)
              throw new RuntimeException("");
            return theArray[count++];
          }
          @Override
          public void remove() {}
        }; // anonymous iterator
  } // iterator

} // SimpleArraySet
