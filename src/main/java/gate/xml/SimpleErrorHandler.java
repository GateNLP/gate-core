/*
 *  SimpleErrorHandle.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  8/May/2000
 *
 *  $Id: SimpleErrorHandler.java 17602 2014-03-08 19:51:20Z markagreenwood $
 */

package gate.xml;

import org.xml.sax.*;

import gate.util.GateSaxException;
import gate.util.Out;

public class SimpleErrorHandler implements ErrorHandler {

  /**
    * SimpleErrorHandler constructor comment.
    */
  public SimpleErrorHandler() {
    super();
  }

  /**
    * This error method is called by the SAX parser when it encounts a
    * recoverable(can continue parsing) error.
    */
  @Override
  public void error(SAXParseException ex) throws SAXException {
    String systemId = "not available";
    String publicId = "not available";
    if (ex.getSystemId() != null) systemId = ex.getSystemId();
    if (ex.getPublicId() != null) publicId = ex.getPublicId();
    Out.prln("\nSAX parser recoverable error. Error details: \n"+
                                " Message: " + ex.getMessage() + "\n" +
                                " System ID: " + systemId +  "\n" +
                                " Public ID: " + publicId +  "\n" +
                                " Line: " + ex.getLineNumber() + "\n" +
                                " Column: "+ ex.getColumnNumber() + "\n");
  }// error
  /**
    * This fatalError method is called by the SAX parser when it encounts a
    * fatal(can't continue parsing) error.
    */
  @Override
  public void fatalError(SAXParseException ex) throws SAXException{
    String systemId = "not available";
    String publicId = "not available";
    if (ex.getSystemId() != null) systemId = ex.getSystemId();
    if (ex.getPublicId() != null) publicId = ex.getPublicId();
    throw new GateSaxException("Fatal XML parse error. Error details: \n"+
                                " Message: " + ex.getMessage() + "\n" +
                                " System ID: " + systemId +  "\n" +
                                " Public ID: " + publicId +  "\n" +
                                " Line: " + ex.getLineNumber() + "\n" +
                                " Column: "+ ex.getColumnNumber());
  }// fatalError
  /**
    * This warning is called by the SAX parser when there is the danger of a
    * confusion.
    */
  @Override
  public void warning(SAXParseException ex) throws SAXException {
    String systemId = "not available";
    String publicId = "not available";
    if (ex.getSystemId() != null) systemId = ex.getSystemId();
    if (ex.getPublicId() != null) publicId = ex.getPublicId();
    Out.prln("SAX parser warning. Warning details: \n"+
                                " Message: " + ex.getMessage() + "\n" +
                                " System ID: " + systemId +  "\n" +
                                " Public ID: " + publicId +  "\n" +
                                " Line: " + ex.getLineNumber() + "\n" +
                                " Column: "+ ex.getColumnNumber());
  }// warning
}// end class SimpleErrorHandler
