/*
 *  DocumentFormatException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 24/OCT/2000
 *
 *  $Id: DocumentFormatException.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

/**
  * This exception can be used to catch any internal exception thrown by the
  * DocumentFormat class and its subbclasses.
  */
public class DocumentFormatException extends GateException {

  private static final long serialVersionUID = 3148554558608939860L;

  public DocumentFormatException(){
    super();
  }
  public DocumentFormatException(String aMessage, Exception anException) {
    super(aMessage, anException);
  }
  public DocumentFormatException(String aMessage) {
    super(aMessage);
  }
  public DocumentFormatException(Exception anException) {
    super(anException);
  }  
} // DocumentFormatException
