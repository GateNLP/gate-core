/*
 *  Pair.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 13/Sept/2001
 *
 *  $Id: Pair.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */


package gate.util;

// Imports
import java.io.Serializable;

public class Pair implements Serializable {

  // Fields
  public Object first;
  public Object second;
  static final long serialVersionUID = 3690756099267025454L;

  // Constructors
  public Pair(Object p0, Object p1) { first = p0; second = p1;}
  public Pair() { first = null; second = null;}
  public Pair(Pair p0) {first = p0.first; second = p0.second; }

  // Methods
  @Override
  public String toString() { return "<" + first.toString() +
                                    ", " + second.toString() + ">" ;}
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    Pair other = (Pair)obj;
    if(first == null) {
      if(other.first != null) return false;
    } else if(!first.equals(other.first)) return false;
    if(second == null) {
      if(other.second != null) return false;
    } else if(!second.equals(other.second)) return false;
    return true;
  }
  
  @Override
  public synchronized Object clone() { return new Pair(first, second); }
}