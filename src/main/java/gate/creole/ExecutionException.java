/*
 *  ExecutionException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 23/Oct/2000
 *
 *  $Id: ExecutionException.java 17586 2014-03-07 19:55:49Z markagreenwood $
 */

package gate.creole;

import gate.util.GateException;

/** Exception used to signal problems during the execution of GATE controllers
  * and Processing Resources.
  */
public class ExecutionException extends GateException {

  private static final long serialVersionUID = -4184224637622988276L;

  public ExecutionException() {
    super();
  }

  public ExecutionException(String s) {
    super(s);
  }

  public ExecutionException(Throwable t) {
    super(t);
  }

  public ExecutionException(String s, Throwable t) {
    super(s, t);
  }
} // ExecutionException
