/*
 *  InvalidValueException.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: InvalidValueException.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import gate.util.GateException;

/**
 * This exception should be thrown when a property value is not
 * compatible with the property. e.g. boolean value for the integer
 * datatype, invalid instance for an object property.
 * 
 * @author Niraj Aswani
 * 
 */
public class InvalidValueException extends GateException {
  private static final long serialVersionUID = 3833465093706036789L;

  /**
   * Constructor
   */
  public InvalidValueException() {
    super();
  }

  /**
   * Constructor
   * 
   * @param s Message that should be printed along with the Exception
   *          trace
   */
  public InvalidValueException(String s) {
    super(s);
  }

  /**
   * Constructor - behaves like a wrapper to the provided exception
   * 
   * @param e
   */
  public InvalidValueException(Exception e) {
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
