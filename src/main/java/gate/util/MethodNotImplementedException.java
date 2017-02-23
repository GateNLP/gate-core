/*
 *  MethodNotImplementedException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Marin Dimitrov, 18/Sep/2001
 *
 */


package gate.util;

/** What to throw in a method that hasn't been implemented yet. 
 * Yes, there are good reasons never to throw RuntimeExceptions
 * and thereby sidestep Java's exception checking mechanism. But
 * we're so lazy we don't care. And anyway, none of these are
 * ever supposed to make it into released versions (who are we
 * kidding?).
 */
public class MethodNotImplementedException extends GateRuntimeException {

  private static final long serialVersionUID = 6273189553052866276L;

  /**The default message carried by this type of exceptions*/
  static String defaultMessage = 
          " It was Valentin's fault. I never touched it.";
  
  public MethodNotImplementedException() {
    super(defaultMessage);
  }

  public MethodNotImplementedException(String message) {
    super(defaultMessage+"\n"+message);
  }
}