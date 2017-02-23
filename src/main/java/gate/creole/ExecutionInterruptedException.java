/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 27 Sep 2001
 *
 *  $Id: ExecutionInterruptedException.java 17586 2014-03-07 19:55:49Z markagreenwood $
 */
package gate.creole;

/**
 * Thrown by {@link gate.Executable}s after they have stopped their execution
 * as a result of a call to their interrupt() method.
 */
public class ExecutionInterruptedException extends ExecutionException {

  private static final long serialVersionUID = -6341242221676672764L;

  public ExecutionInterruptedException(String message){
    super(message);
  }
  
  public ExecutionInterruptedException(Throwable cause) {
    super(cause);
  }
  
  public ExecutionInterruptedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExecutionInterruptedException(){
  }
}