/*
 *  InvalidURIException.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: InvalidURIException.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import gate.util.GateRuntimeException;

/**
 * This exception is thrown when a URI is not valid.
 * 
 * @author Niraj Aswani
 * 
 */
public class InvalidURIException extends GateRuntimeException {
  private static final long serialVersionUID = 4121418405812712500L;

  /**
   * Constructor
   */
  public InvalidURIException() {
    super();
  }

  /**
   * Constructor
   * 
   * @param s Message that should be printed along with the Exception
   *          trace
   */
  public InvalidURIException(String s) {
    super(s);
  }

  /**
   * Constructor - behaves like a wrapper to the provided exception
   * 
   * @param e
   */
  public InvalidURIException(Exception e) {
    this.exception = e;
  }

  /**
   * Overriden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace() {
    printStackTrace(System.err);
  }

  /**
   * Overriden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace(java.io.PrintStream s) {
    s.flush();
    super.printStackTrace(s);
    s.print("  Caused by:\n");
    if(exception != null) exception.printStackTrace(s);
  }

  /**
   * Overriden so we can print the enclosed exception's stacktrace too.
   */
  @Override
  public void printStackTrace(java.io.PrintWriter s) {
    s.flush();
    super.printStackTrace(s);
    s.print("  Caused by:\n");
    if(exception != null) exception.printStackTrace(s);
  }

  /**
   * Internal object of exception, for which the instance behaves like a
   * wrapper
   */
  Exception exception;
}
