/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  ObjectComparator.java
 *
 *  Valentin Tablan, 06-Dec-2004
 *
 *  $Id: ObjectComparator.java 19660 2016-10-10 07:57:55Z markagreenwood $
 */

package gate.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A Comparator implementation for Object values.
 * If the values provided are not comparable, then they are converted to String 
 * and the String values are compared.
 * This utility is useful for GUI components that need to sort their contents.
 */
public class ObjectComparator implements Comparator<Object>, Serializable {

  private static final long serialVersionUID = 8692612164609360477L;

  /**
   * Compares two objects.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public int compare(Object o1, Object o2){
    // If both values are null, return 0.
    if (o1 == null && o2 == null) {
      return 0;
    } else if (o1 == null) { // Define null less than everything.
      return -1;
    } else if (o2 == null) {
      return 1;
    }
    int result;
    if(o1 instanceof Comparable){
      try {
        result = ((Comparable)o1).compareTo(o2);
      } catch(ClassCastException cce) {
        String s1 = o1.toString();
        String s2 = o2.toString();
        result = s1.compareTo(s2);
      }
    } else {
      String s1 = o1.toString();
      String s2 = o2.toString();
      result = s1.compareTo(s2);
    }
    
    return result;
  }
}
