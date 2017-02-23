/*
 *  Term.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Term.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

/** This class represents pairs NAME-VALUE */
public class Term {

  /** Name */
  private String name;

  /** Value */
  private String value;

  /** Constructor of the class. */
  public Term(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /** @return String name */
  public String getName() {
    return name;
  }

  /** @return String value */
  public String getValue() {
    return value;
  }
}
