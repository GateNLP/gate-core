/*
 *  PersistenceException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 19/Jan/2001
 *
 *  $Id: PersistenceException.java 17616 2014-03-10 16:09:07Z markagreenwood $
 */

package gate.persist;

import gate.util.GateException;

/** This exception indicates failure during persistence operations.
  */
public class PersistenceException extends GateException {

  private static final long serialVersionUID = 3021649267577030175L;

  /** Default construction */
  public PersistenceException() { super(); }

  /** Construction from string */
  public PersistenceException(String s) { super(s); }

  /** Construction from exception */
  public PersistenceException(Exception e) { 
    super(e);
  }

  /** Construction from both string and exception */
  public PersistenceException(String s, Exception e) {
    super(s,e);
  }

  /**
   * Overridden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace(){
    printStackTrace(System.err);
  }

  /**
   * Overridden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace(java.io.PrintStream s) {
    s.flush();
    super.printStackTrace(s);
    s.print("  Caused by:\n");
    if(getCause() != null) getCause().printStackTrace(s);
  }

  /**
   * Overridden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace(java.io.PrintWriter s) {
    s.flush();
    super.printStackTrace(s);
    s.print("  Caused by:\n");
    if(getCause() != null) getCause().printStackTrace(s);
  }
} // PersistenceException
