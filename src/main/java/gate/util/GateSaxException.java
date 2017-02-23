/*
 *  GateSaxException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 23/OCT/2000
 *
 *  $Id: GateSaxException.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

/** An inherited class from  SAX exception in the GATE packages. Can be used
  * to catch any internal exception thrown by the GATE SAX libraries.
  */
public class GateSaxException extends org.xml.sax.SAXException {

  private static final long serialVersionUID = 1355352302306968618L;

  public GateSaxException(String aMessage, Exception anException) {
    super(aMessage,anException);
  }

  public GateSaxException(String aMessage) {
    super(aMessage);
  }

  public GateSaxException(Exception anException) {
    super(anException.toString());
  }
} // GateSaxException
