/*
 *  InvalidOffsetException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *  
 *  Valentin Tablan, Jan/2000
 *
 *  $Id: InvalidOffsetException.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

/** Used to signal an attempt to create a node with an invalid offset.
  * An invalid offset is a negative value, or
  * an offset bigger than the document size, or a start greater than an end. 
  */
public class InvalidOffsetException extends GateException {

  private static final long serialVersionUID = -2556783648232304356L;

  public InvalidOffsetException() {
  }

  public InvalidOffsetException(String s) {
    super(s);
  }
} // InvalidOffsetException
