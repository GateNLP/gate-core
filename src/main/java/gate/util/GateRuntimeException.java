/*
 * GateRuntimeException.java
 *
 * Copyright (c) 1998-2005, The University of Sheffield.
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://gate.ac.uk/gate/licence.html.
 *
 * Valentin Tablan, 03/11/2000
 *
 * $Id: GateRuntimeException.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */
package gate.util;

/**
 * Exception used to signal a runtime exception within Gate.
 */
public class GateRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 2605697923542827933L;

  public GateRuntimeException() {
  }

  public GateRuntimeException(String message) {
    super(message);
  }
  
  public GateRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public GateRuntimeException(Throwable e) {
    super(e);
  }
}
