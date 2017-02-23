/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Atanas Kiryakov, 01/02/2002
 *
 *  $Id: ObjectPool.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */
package gate.util;


import java.util.Vector;

/**
   A generic implementation of pool of references to objects of any kind.
   It is thread-safe, so, allows multiple users to get and release objects
   "simultaneously". In fact, the standard Java synchronization is used.
   <BR><BR>
   The base idea is that, a calling routine will try to get
   an object from the pool with method Get. On success, it will use the
   object and return it in the pool with the method Put.
   <BR><BR>
   If there ares no available objects in the pool, Get will return <B>null</B>.
   Then the calling routine should create a new object. Further, scenario goes
   in the same way - when finished using the object, calling routine shoud Put
   it in the pool for future use, instead of leaving it to be garbage-collected.
   <BR><BR>
   The pool initially is empty. The only way to increase the number of objects
   managed by the pool, is some external process to Put an object, that was
   created, instead of previously Get from the pool.
   <BR><BR>
   Pool stores only references to currently "free" or available objects. When
   some external routine Gets an object from the pool, its reference is not
   locked, it is simply removed from the pool.
 */
public class ObjectPool {
   private Vector<Object>   objects;
   private int      size;

/**
   Constructs and object pool with specified size.
   @param size determines the maximum size of the pool. This is the number
         free objects that it can manage at the same time
 */
   public ObjectPool(int size) {
      this.size = size;
      objects = new Vector<Object>(this.size);
   } // ObjectPool

/**
   Pulls out an object from the pool. The reference to the object is removed
   from the pool and their is no longer any kind of relation between this
   object and the pool. It can be returned back (released) by Put method.
   @return  an object from the pool, if available.<BR>
            Otherwise, returns <B>null</B>
 */
   public synchronized Object get(){
      int n = objects.size();
      if (n > 0){
         Object o = objects.elementAt(n-1);
         objects.removeElementAt(n-1);
         return o;
      }
      else
         return null;
   } // Get

/**
   Puts an object in the pool, those stating that it's "free" or available
   for use by another processes and routines. An object can be put in the pool,
   without need to be get from there before.
   @return  <B>true</B> on success<BR>
            <B>false</B> when the object was not put in the pool,
            because the maximal number of references in the pool was riched
 */
   public synchronized boolean put(Object o){
      if (objects.size() > 30)
         return false;
      objects.addElement(o);
      return true;
   } // Put
}