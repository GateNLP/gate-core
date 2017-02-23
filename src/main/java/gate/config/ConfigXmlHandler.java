/*
 *  ConfigXmlHandler.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 9/Nov/2000
 *
 *  $Id: ConfigXmlHandler.java 17653 2014-03-13 19:40:20Z markagreenwood $
 */

package gate.config;

import gate.CreoleRegister;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.util.GateException;
import gate.util.GateSaxException;
import gate.util.Out;
import gate.util.Strings;
import gate.xml.SimpleErrorHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


/** This is a SAX handler for processing <CODE>gate.xml</CODE> files.
  */
public class ConfigXmlHandler extends DefaultHandler {

  /** A stack to stuff PCDATA onto for reading back at element ends.
   *  (Probably redundant to have a stack as we only push one item
   *  onto it. Probably. Ok, so I should check, but a) it works, b)
   *  I'm bald already and c) life is short.)
   */
  private Stack<String> contentStack = new Stack<String>();

  /** The current element's attribute list */
  private Attributes currentAttributes;

  /** A feature map representation of the current element's attribute list */
  private FeatureMap currentAttributeMap;

  /** Debug flag */
  private static final boolean DEBUG = false;

  /** The source URL of the config file being parsed. */
  private URL sourceUrl;

  /** This object indicates what to do when the parser encounts an error*/
  private SimpleErrorHandler _seh = new SimpleErrorHandler();


  /** This is used to capture all data within two tags before calling the actual characters method */
  private StringBuffer contentBuffer = new StringBuffer("");

  /** This is a variable that shows if characters have been read */
  private boolean readCharacterStatus = false;


  /** Construction */
  public ConfigXmlHandler(URL configUrl) {
    this.register = Gate.getCreoleRegister();
    this.sourceUrl = configUrl;
  } // construction

  /** The register object that we add CREOLE directories to during parsing.
    */
  private CreoleRegister register;

  /** Called when the SAX parser encounts the beginning of the XML document */
  @Override
  public void startDocument() throws GateSaxException {
    if(DEBUG) Out.prln("start document");
  } // startDocument

  /** Called when the SAX parser encounts the end of the XML document */
  @Override
  public void endDocument() throws GateSaxException {
    if(DEBUG) Out.prln("end document");
    if(! contentStack.isEmpty()) {
      StringBuffer errorMessage =
        new StringBuffer("document ended but element stack not empty:");
      while(! contentStack.isEmpty())
        errorMessage.append(Strings.getNl()+"  "+contentStack.pop());
      throw new GateSaxException(errorMessage.toString());
    }
  } // endDocument

  /** A verbose method for Attributes*/
  private String attributes2String(Attributes atts){
    StringBuffer strBuf = new StringBuffer("");
    if (atts == null) return strBuf.toString();
    for (int i = 0; i < atts.getLength(); i++) {
     String attName  = atts.getQName(i);
     String attValue = atts.getValue(i);
     strBuf.append(" ");
     strBuf.append(attName);
     strBuf.append("=");
     strBuf.append(attValue);
    }// End for
    return strBuf.toString();
  }// attributes2String()

  /** Called when the SAX parser encounts the beginning of an XML element */
  @Override
  public void startElement (
    String uri, String qName, String elementName, Attributes atts
  ) throws SAXException {

    // call characterActions
    if(readCharacterStatus) {
      readCharacterStatus = false;
      charactersAction(new String(contentBuffer).toCharArray(),0,contentBuffer.length());
    }

    if(DEBUG) {
      Out.pr("startElement: ");
      Out.println(
        elementName + " " +
        attributes2String(atts)
      );
    }

    // record the attributes of this element for endElement()
    currentAttributes = atts;
    currentAttributeMap = attributeListToParameterList();

    if(elementName.toUpperCase().equals(Gate.getUserConfigElement())) {
      Gate.getUserConfig().putAll(currentAttributeMap);
    }

  } // startElement

  /** Called when the SAX parser encounts the end of an XML element.
    * This is actions happen.
    */
  @Override
  public void endElement (String uri, String qName, String elementName)
                                                      throws GateSaxException, SAXException {

     // call characterActions
     if(readCharacterStatus) {
       readCharacterStatus = false;
       charactersAction(new String(contentBuffer).toCharArray(),0,contentBuffer.length());
     }

    if(DEBUG) Out.prln("endElement: " + elementName);

    //////////////////////////////////////////////////////////////////
    if(elementName.toUpperCase().equals("GATE")) {

    //////////////////////////////////////////////////////////////////
    } else if(elementName.toUpperCase().equals("CREOLE-DIRECTORY")) {
      String dirUrlName = contentStack.pop();
      try {
        register.registerDirectories(new URL(dirUrlName));
      } catch(MalformedURLException e) {
        throw new GateSaxException("bad URL " + dirUrlName + e);
      } catch(GateException e) {
        throw new GateSaxException("unable to register directory",e);
      }

    //////////////////////////////////////////////////////////////////
    } else if(elementName.toUpperCase().equals("GATECONFIG")) {
      // these are empty elements with attributes; nothing to do here

    //////////////////////////////////////////////////////////////////
    } else {
      throw new GateSaxException(
        "Unknown config data element: " + elementName +
        "; encountered while parsing " + sourceUrl
      );
    }
    //////////////////////////////////////////////////////////////////

  } // endElement

  /** Called when the SAX parser encounts text (PCDATA) in the XML doc */
  @Override
  public void characters(char [] text,int start,int length) throws SAXException {
    if(!readCharacterStatus) {
      contentBuffer = new StringBuffer(new String(text,start,length));
    } else {
      contentBuffer.append(new String(text,start,length));
    }
    readCharacterStatus = true;
  }

  /**
    * This method is called when all characters between specific tags have been read completely
    */

  public void charactersAction(char[] text, int start, int length)
  throws SAXException {
    // Get the trimmed text between elements
    String content = new String(text, start, length).trim();
    // If the entire text is empty or is made from whitespaces then we simply
    // return
    if (content.length() == 0) return;
    contentStack.push(content);
    if(DEBUG) Out.println(content);
  } // characters

  /** Utility method to convert the current SAX attribute list to a
   *  FeatureMap
   */
  protected FeatureMap attributeListToParameterList() {
    FeatureMap params = Factory.newFeatureMap();

    // for each attribute of this element, add it to the param list
    for(int i=0, len=currentAttributes.getLength(); i<len; i++) {
      params.put(
        currentAttributes.getQName(i), currentAttributes.getValue(i)
      );
    }

    return params;
  } // attributeListToParameterList

  /** Called when the SAX parser encounts white space */
  @Override
  public void ignorableWhitespace(char ch[], int start, int length)
  throws SAXException {
  } // ignorableWhitespace

  /** Called for parse errors. */
  @Override
  public void error(SAXParseException ex) throws SAXException {
    _seh.error(ex);
  } // error

  /** Called for fatal errors. */
  @Override
  public void fatalError(SAXParseException ex) throws SAXException {
    _seh.fatalError(ex);
  } // fatalError

  /** Called for warnings. */
  @Override
  public void warning(SAXParseException ex) throws SAXException {
    _seh.warning(ex);
  } // warning

} // ConfigXmlHandler
