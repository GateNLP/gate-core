/*
 *  SynchronisationException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Marin Dimitrov, 17/Oct/2001
 *
 *  $Id: SynchronisationException.java 17604 2014-03-09 10:08:13Z markagreenwood $
 */

package gate.corpora;

import gate.util.GateRuntimeException;


public class SynchronisationException extends GateRuntimeException {

  private static final long serialVersionUID = -2741385209392213036L;

  /** Default construction */
  public SynchronisationException() { super(); }

  /** Construction from string */
  public SynchronisationException(String s) { super(s); }

  /** Construction from exception */
  public SynchronisationException(Exception e) { super(e.toString()); }

}