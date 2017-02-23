/*
 *  WordNetException.java
 *
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 */
package gate.wordnet;

import gate.util.GateException;

public class WordNetException extends GateException {

  private static final long serialVersionUID = -9114971170009789898L;

  public WordNetException() {
    super();
  }

  public WordNetException(String s) {
    super(s);
  }

  public WordNetException(Throwable e) {
    super(e);
  }
  
  public WordNetException(String s, Throwable e) {
    super(s,e);
  }

}
