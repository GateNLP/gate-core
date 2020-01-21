/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 02/10/2001
 *
 *  $Id: OffsetComparator.java 19660 2016-10-10 07:57:55Z markagreenwood $
 *
 */
package gate.util;

import java.io.Serializable;
import java.util.Comparator;

import gate.Annotation;

/**
 * Compares annotations by start offsets first, then by end 
 * offset if the start offsets are equal.
 * Example: [5,9] &lt; [6,7] &lt; [6,9] &lt; [7,8]
 */
public class OffsetComparator implements Comparator<Annotation>, Serializable {

  private static final long serialVersionUID = -8201745469767435557L;

  @Override
  public int compare(Annotation a1, Annotation a2){
    int result;

    // compare start offsets
    result = a1.getStartNode().getOffset().compareTo(
        a2.getStartNode().getOffset());

    // if start offsets are equal compare end offsets
    if(result == 0) {
      result = a1.getEndNode().getOffset().compareTo(
          a2.getEndNode().getOffset());
    } // if

    return result;
  }
}