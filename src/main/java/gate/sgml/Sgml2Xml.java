/*
 *  Sgml2Xml.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  4/July/2000
 *
 *  $Id: Sgml2Xml.java 19660 2016-10-10 07:57:55Z markagreenwood $
 */

package gate.sgml;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.*;

import gate.Document;
import gate.util.Files;


/**
  * Not so fast...
  * This class is not a realy Sgml2Xml convertor.
  * It takes an SGML document and tries to prepare it for an XML parser
  * For a true conversion we need an Java SGML parser...
  * If you know one let me know....
  *
  * What does it do:
  * <ul>
  *  <li>If it finds something like this : &lt;element attribute = value&gt;
  *      it will produce: &lt;element attribute = "value"&gt;
  *  <li>If it finds something like this : &lt;element something
  *      attribute2=value&gt;it will produce : &lt;element
  *      defaultAttribute="something" attribute2="value"&gt;
  *  <li>If it finds : &lt;element att1='value1 value2' att2="value2
  *      value3"&gt; it will produce: &lt;element att1="value1 value2"
  *      att2="value2 value3"&gt;
  *  <li>If it finds : &lt;element1&gt; &lt;elem&gt;text &lt;/element1&gt;
  *      will produce: &lt;element1&gt; &lt;elem&gt;text&lt;elem&gt;
  *      &lt;/element1&gt;
  *  <li>If it find : &lt;element1&gt; &lt;elem&gt;[white spaces]
  *      &lt;/element1&gt;,
  *      it will produce:&lt;element1&gt; &lt;elem/&gt;[white spaces]&lt;
  *      /element1&gt;
  * </ul>
  * What doesn't:
  * <ul>
  *  <li>Doesn't expand the entities. So the entities from the SGML document
  *      must be resolved by the XML parser
  *  <li>Doesn't replace internal entities with their corresponding value
  * </ul>
  */

public class Sgml2Xml{

  /**
    * The constructor initialises some member fields
    * @param SgmlDoc the content of the Sgml document that will be modified
    */
  public Sgml2Xml(String SgmlDoc){
    // create a new modifier
    m_modifier = new StringBuffer(SgmlDoc);
    // create a new dobiousElements list
    // se the explanatin at the end of the class
    dubiousElements = new ArrayList<CustomObject>();
    stack = new Stack<CustomObject>();
  }

  /**
    * The other constructor
    * @param doc The Gate document that will be transformed to XML
    */
  public Sgml2Xml(Document doc){
    // set as a member
    m_doc = doc;

    // create a new modifier
    m_modifier = new StringBuffer(m_doc.getContent().toString());

    // create a new dobiousElements list
    // se the explanatin at the end of the class
    dubiousElements = new ArrayList<CustomObject>();
    stack = new Stack<CustomObject>();

  }

/*  I keep this just in case I need some more debuging

  public static void main(String[] args){
    Sgml2Xml convertor =
      new Sgml2Xml("<w VVI='res trtetre\" relu = \"stop\">say
      <w VBZ>is\n<trunc> <w UNC>th </trunc>");
    try{
      Out.println(convertor.convert());
    } catch (Exception e){
      e.printStackTrace(Err.getPrintWriter());
    }
  }
  */

  /**
    * It analises the char that was red in state 1
    * If it finds '<' it then goes to state 2
    * Otherwise it stays in state 1 and keeps track about the text that is not
    * white spaces.
    */
  private void doState1(char currChar){
    if ('<' == currChar){
      // change to state 2
      m_currState = 2;
      if (!stack.isEmpty()){
        // peek the element from the top of the stack
        CustomObject o = stack.peek();
        // set some properties for this element
        // first test to find out if text folows this element charPos > 0
        if (charPos > 0){
          // this is not an empty element because there is text that follows
          // set the element from the top of the stack to be a non empty one
          o.setClosePos(charPos);
          o.setEmpty(false);
          // reset the charPos
          charPos = 0;
        }//if (charPos > 0)
      }//if (!stack.isEmpty())
    }//if ('<' == m_currChar)
    // if currChar is not whiteSpace then save the position of the last
    // char that was read
    if (('<' != currChar) && !isWhiteSpace(currChar))
      charPos = m_cursor;
  }//doState1

  /**
    We came from state 1 and just read '<'
    If currChar == '/' -> state 11
    If is a char != white spaces -> state 3
    stay in state 2 while there are only white spaces
  */
  private void doState2(char currChar){
    if ('/' == currChar){
      // go to state 11
      m_currState = 11;
    }
    // if currChar is a char != white spaces  then go to state 3
    if (('/' != m_currChar) && !isWhiteSpace(m_currChar)){
      // save the position where starts the element's name
      // we need that in order to be able to read the current tag name
      // this name it will be read from m_modifier using the substring() method
      elemNameStart = m_cursor -1;
      // go to state 3
      m_currState = 3;
    }
  }// doState2

  /**
    * Just read the first char from the element's name and now analize the next
    * char.
    * If '>' the elem name was a single char -> state 1
    * IF is WhiteSpaces -> state 4
    * Otherwise stay in state 3 and read the elemnt's name
    */
  private void doState3(char currChar){
    if ( '>' == currChar ){

      // save the pos where the element's name ends
      elemNameEnd = m_cursor - 1;

      // this is also the pos where to insert '/' for empty elements.
      // In this case we have this situation <w> sau < w>
      closePos = m_cursor - 1;

      // get the name of the element
      elemName = m_modifier.substring(elemNameStart,elemNameEnd);

      // we put the element into stack
      // we think in this point that the element is empty...
      performFinalAction(elemName, closePos);

      // go to state 1
      m_currState = 1;
    }
    if (isWhiteSpace(currChar)){
      // go to state 4
      m_currState = 4;

      // save the pos where the element's name ends
      elemNameEnd = m_cursor - 1;

      // get the name of the element
      elemName = m_modifier.substring(elemNameStart,elemNameEnd);
    }
  }// doState3

  /**
    * We read the name of the element and we prepare for '>' or attributes
    * '>' -> state 1
    * any char !- white space -> state 5
    */
  private void doState4(char currChar){
    if ( '>' == currChar ){
      // this is also the pos where to insert '/' for empty elements in this case
      closePos = m_cursor -1 ;

      // we put the element into stack
      // we think in this point that the element is empty...
      performFinalAction(elemName, closePos);

      // go to state 1
      m_currState = 1;
    }
    if (( '>' != currChar ) && !isWhiteSpace(currChar)){
      // we just read the first char from the attrib name or attrib value..
      // go to state 5
      m_currState = 5;

      // remember the position where starts the attrib or the value of an attrib
      attrStart = m_cursor - 1;
    }
  } // doState4

  /**
    * '=' -> state 6
    * '>' -> state 4 (we didn't read an attribute but a value of the
    * defaultAtt )
    * WS (white spaces) we don't know yet if we read an attribute or the value
    * of the defaultAttr -> state 10
    * This state modifies the content onf m_modifier ... it adds text
    */
  private void doState5(char currChar){
    if ( '=' == currChar )
          m_currState = 6;
    if ( '>' == currChar ){
      // this mean that the attribute was a value and we have to create
      // a default attribute
      // the same as in state 10
      attrEnd = m_cursor - 1 ;
      m_modifier.insert(attrEnd,'"');
      m_modifier.insert(attrStart,"defaultAttr=\"");

      // go to state 4
      m_currState = 4;

      // parse again the entire sequence from state 4 before reading any char
      m_cursor = attrStart;
    }
    if (isWhiteSpace(currChar)){
      // go to state 10
      m_currState = 10;

      // record the position where ends this attribute
      attrEnd = m_cursor - 1;
    }
  } // doState5

  /**
    * IF we read ' or " then we have to get prepared to read everything until
    * the next ' or "
    * If we read a char then -> state 8;
    * Stay here while we read WS
    */
  private void doState6(char currChar){
    if ( ('\'' == currChar) || ('"' == currChar) ){
      endPair = currChar;
      if ('\'' == currChar){

        // we have to replace ' with "
        m_modifier = m_modifier.replace(m_cursor - 1, m_cursor,"\"");
      }
      m_currState = 7;
    }
    if ( ('\'' != currChar) && ('"' != currChar) && !isWhiteSpace(currChar)){

      // this means that curChar is any char
      m_currState = 8;

      // every value must be inside this pair""
      m_modifier.insert(m_cursor - 1, '"');

      // insert implies the modification of m_cursor
      // we increment m_cursor in order to say in the same position and to
      // anulate the efect of insert.
      m_cursor ++;
    }
  }// doState6

  /**
    * If we find the pair ' or " go to state 9
    * Otherwhise read everything and stay in state 7
    * If in state 7 we read '>' then we add automaticaly a " at the end and go
    * to state 1
    */
  private void doState7(char currChar){
    //if ( ('\'' == currChar) || ('"' == currChar) ){
    if ( endPair == currChar ){
      if ('\'' == currChar){

        // we have to replace ' with "
        m_modifier = m_modifier.replace(m_cursor - 1, m_cursor,"\"");
      }
      // reset the endPair
      endPair = ' ';
      m_currState = 9;
    }

    if ('>' == currChar){
      // go to state 1
      m_currState = 1;

      // insert the final " ata the end
      m_modifier.insert(m_cursor - 1, '"');

      // go to te current possition (because of insert)
      m_cursor ++;

      performFinalAction(elemName, m_cursor - 1);
    }

  }// doState7

  /**
    * If '>' go to state 1
    * If WS go to state 9
    * Stays in state 8 and read the attribute's value
    */
  private void doState8(char currChar){

    if ('>' == currChar){
      // go to state 1
      m_currState = 1;

      // complete the end " ( <elem attr="value> )
      m_modifier.insert(m_cursor - 1, '"');

      // go to te current possition (because of insert)
      m_cursor ++;

      // we finished to read a beggining tag
      // see the method definition for more details
      performFinalAction(elemName, m_cursor - 1);
    }
    if (isWhiteSpace(currChar)){
      // go to state 9
      m_currState = 9;

      // add the ending " char
      m_modifier.insert(m_cursor - 1, '"');

      // increment the cursor in order to anulate the effect of insert
      m_cursor ++;
    }
  } // doState8
  /**
    * Here we prepare to read another attrib, value pair (any char -> state 5)
    * If '>' we just read a beggining tag -> state 1
    * Stay here while read WS
    */
  private void doState9(char currChar){
    if ('>' == currChar){
      // go to state 1
      m_currState = 1;

      // add the object to the stack
      performFinalAction(elemName, m_cursor - 1);
    }
    if (('>' != currChar) && !isWhiteSpace(m_currChar)){
      // this is the same as state 4->5
      m_currState = 5;
      attrStart = m_cursor - 1;
    }
  }//doState9

  /**
    * If any C -> state 4
    * If '=' state 6
    * Stays here while reads WS
    */
  private void doState10(char currChar){
    if ('=' == currChar)
             m_currState = 6;
    if ( ('=' != currChar) && !isWhiteSpace(currChar)){
      // this mean that the attribute was a value and we have to create
      // a default attribute
      m_modifier.insert(attrEnd,'"');
      m_modifier.insert(attrStart,"defaultAttr=\"");

      // go to state 4
      m_currState = 4;

      m_cursor = attrStart;
    }
  }// doState10

  /**
    * We are preparing to read the and definition of an element
    * Stays in this state while reading WS
    */
  private void doState11(char currChar){
    if (!isWhiteSpace(currChar)){
      m_currState = 12;
      elemNameStart = m_cursor - 1;
    }
  } // doState11

  /**
    * Here we read the element's name ...this is an end tag
    * Stays here while reads a char
    */
  private void doState12(char currChar) {
    if ('>' == currChar){
      elemNameEnd = m_cursor - 1;
      elemName = m_modifier.substring(elemNameStart,elemNameEnd);
      performActionWithEndElem(elemName);
      m_currState = 1;
    }
    if (isWhiteSpace(currChar)){
      m_currState = 13;
      elemNameEnd = m_cursor - 1;
    }
  }//doState12

  /**
    * If '>' -> state 1
    * Stays here while reads WS
    */
  private void doState13(char currChar) {
    if ('>' == currChar){
      elemName = m_modifier.substring(elemNameStart,elemNameEnd);
      performActionWithEndElem(elemName);
      m_currState = 1;
    }
  } // doState13

  /**
    This method is responsable with document conversion
  */
  public String convert()throws IOException,MalformedURLException {
    while (thereAreCharsToBeProcessed()) {
      // read() gets the next char and increment the m_cursor
      m_currChar = read();
      switch(m_currState){
        case 1:   doState1(m_currChar);break;
        case 2:   doState2(m_currChar);break;
        case 3:   doState3(m_currChar);break;
        case 4:   doState4(m_currChar);break;
        case 5:   doState5(m_currChar);break;
        case 6:   doState6(m_currChar);break;
        case 7:   doState7(m_currChar);break;
        case 8:   doState8(m_currChar);break;
        case 9:   doState9(m_currChar);break;
        case 10:  doState10(m_currChar);break;
        case 11:  doState11(m_currChar);break;
        case 12:  doState12(m_currChar);break;
        case 13:  doState13(m_currChar);break;
      }// switch(m_currState)
    }// while (thereAreCharsToBeProcessed())

    // put all the elements from the stack into the dubiousElements list
    // we do that in order to colect all the dubious elements
    while (!stack.isEmpty()) {
      CustomObject obj = stack.pop();
      dubiousElements.add(obj);
    }

    // sort the dubiousElements list descending on closePos...
    // This is vital for the alghorithm because we have to make
    // all the modifications from the bottom to the top...
    // If we fail to do that, insert will change indices and
    // CustomObject.getClosePos() will not be acurate anymore.
    Collections.sort(dubiousElements, new MyComparator());

    //here we resolve all the dubious Elements...
    // see the description of makeFinalModifications() method
    ListIterator<CustomObject> listIterator = dubiousElements.listIterator();
    while (listIterator.hasNext()){
      CustomObject obj = listIterator.next();
      makeFinalModifications(obj);
    }

    //finally add the XML prolog
    m_modifier.insert(0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    //Out.println(m_modifier.toString());
/*
    // get a InputStream from m_modifier and write it into a temp file
    // finally return the URI of the new XML document
    ByteArrayInputStream is = new ByteArrayInputStream(
                                              m_modifier.toString().getBytes()
                                                       );
*/
    // this method is in gate.util package
    File file = Files.writeTempFile(m_modifier.toString(),"UTF-8");

    //return m_doc.getSourceURL().toString();
    return file.toURI().toURL().toString();
  }// convert()

  /**
    * This method tests to see if there are more char to be read
    * It will return false when there are no more chars to be read
    */
  private boolean thereAreCharsToBeProcessed() {
    if (m_cursor < m_modifier.length()) return true;
    else return false;
  }//thereAreCharsToBeProcessed

  /**
    * This method reads a char and increments the m_cursor
    */
  private char read(){
    return m_modifier.charAt(m_cursor ++);
  }//read

  /**
    * This is the action when we finished to read the entire tag
    * The action means that we put the tag into stack and consider that is empty
    * as default
    */
  private void performFinalAction(String elemName, int pos) {
    // create anew CustomObject
    CustomObject obj = new CustomObject();

    // set its properties
    obj.setElemName(elemName);
    obj.setClosePos(pos);

    // default we consider every element to be empty
    // in state 1 we modify that if the element is followed by text
    obj.setEmpty(true);
    stack.push(obj);
  } // performFinalAction

  /**
    * This is the action performed when an end tag is read.
    * The action consists in colecting all the dubiosElements(elements without
    * an end tag). They are considered dubious because we don't know if they
    * are empty or may be closed... Only the DTD can provide this information.
    * We don't have a DTD so we will consider that all dubious elements
    * followed by text will close at the end of the text...
    * If a dubious element is followed by another element then is
    * automaticaly considered an empty element.
    *
    * @param elemName is the the name of the end tag that was read
    */
  private void performActionWithEndElem(String elemName) {
    CustomObject obj    = null;
    boolean      stop = false;

    // get all the elements that are dubious from the stack
    // the iteration will stop when an element is equal with elemName
    while (!stack.isEmpty() && !stop){

      // eliminate the object from the stack
      obj = stack.pop();

      //if its elemName is equal with the param elemName we stop the itteration
      if (obj.getElemName().equalsIgnoreCase(elemName)) stop = true;

      // otherwhise add the element to the doubiousElements list
      else dubiousElements.add(obj);
    }
  }//performActionWithEndElem

  /**
    * This method is called after we read the entire SGML document
    * It resolves the dobious Elements this way:
    * <ul>
    * <li>
    * 1. We don't have a DTD so we will consider that all dubious elements
    *    followed by text will close at the end of the text...
    * <li>
    * 2. If a dubious element is followed by another element then is
        automaticaly considered an empty element.
    *
    * An element is considered dubious when we don't know if it is  empty
    * or may be closed...
    *
    * @param aCustomObject an object from the dubiousElements list
    */
  private void makeFinalModifications(CustomObject aCustomObject) {
    String endElement = null;
    // if the element is empty then we add / before > like this:
    // <w> -> <w/>
    if (aCustomObject.isEmpty())
        m_modifier.insert(aCustomObject.getClosePos(),"/");
    // otherwhise we create an end element
    // <w> -> </w>
    else{
      // create the end element
      endElement = "</" + aCustomObject.getElemName() + ">";
      // insert it where the closePos indicates
      m_modifier.insert(aCustomObject.getClosePos(), endElement);
    }
  } // makeFinalModifications

  /**
    * Tests if c is a white space char
    */
  private boolean isWhiteSpace(char c) {
    return Character.isWhitespace(c);
  }

  // this is a gate Document... It's content will be transferred to
  // m_modifier
  private Document m_doc = null;

  // this is the modifier that will transform an SGML document into an
  // XML document
  private StringBuffer m_modifier = null;

  // we need the stack to be able to remember the order of the tags
  private Stack<CustomObject> stack = null;

  // this is a list with all the tags that are not colsed...
  // some of them are empty tags and some of them are not...
  private List<CustomObject> dubiousElements = null;

  // this is tre current position inside the modifier
  private int m_cursor = 0;

  // the current state of the SGML2XML automata
  private int m_currState = 1;

  // the char that was read from the m_modifier @ position m_cursor
  private char m_currChar = ' ';

  // the fields above are used by the convert method and its auxiliary functions
  // like doState1...13()

  // indicates the last position of a text character (one which is not a white
  // space)
  // it is used in doState1() when we have to decide if an element is empty or
  // not
  // We decide that based on this field
  // If the charPos > 0 then it means that the object from the top of stack
  // is followed by text and we consider that is not empty
  private int charPos = 0;

  // is the current tag name
  private String elemName = null;

  // indicates where in the m_modifier begins the current tag elemName
  private int elemNameStart = 0;

  // indicates where in the m_modifier ends the current tag elemName
  // we need that in order to be able to read the current tag name
  // this name it will be read from m_modifier using the substring() method
  // it will be something like this :
  // elemName = m_modifier.substring(elemNameStart,elemNameEnd)
  // Eg: <w attr1=val1> -> <[elemNameStart]w[elemNameEnd] [attr1=val1>
  private int elemNameEnd = 0;

  // this is the position there a start tag ends like this:
  // Eg: <w attr1=val1>  -> <w attr1=val1 [closePos]>
  private int closePos = 0;

  //this is the position where an attribute starts...
  // we need it when we have to add the defaultAttr (see state 5)
  private int attrStart = 0;

    //this is the position where an attribute ends...
  // we need it when we have to add the defaultAttr (see state 5) or to add "
  // Eg: <w attr1=val1> -> <w [attrStart]attr1[attrEnd]=val1>
  private int attrEnd = 0;

  // endPair field is used in states 6 and 7....
  // When we read something like this :
  // attr=' val1 val2 val3' endPair remembers what is the pair for the beginning
  // string
  // Note that a combination like: attr = ' val1 val2 " will have an unexpected
  // behaviour...
  // We need this field when we have the following situation
  // attr1 = " val1 val2 ' val3" . We need to know what is the end pair for ".
  // In this case we can't allow ' to be the endPair
  private char endPair = ' ';

} // class Sgml2Xml

/**
  * The objects belonging to this class are used inside the stack
  */
class  CustomObject {

  // constructor
  public CustomObject() {
    elemName = null;
    closePos = 0;
    empty = false;
  }

  // accessor
  public String getElemName() {
    return elemName;
  }

  public int getClosePos() {
    return closePos;
  }

  public boolean isEmpty() {
    return empty;
  }

  // modifiers
  void setElemName(String anElemName) {
    elemName = anElemName;
  }

  void setClosePos(int aPos){
    closePos = aPos;
  }

  void setEmpty(boolean anEmptyValue) {
    empty = anEmptyValue;
  }

  // data fields
  private String elemName = null;

  private int closePos = 0;

  private boolean empty = false;

} // CustomObject

class MyComparator implements Comparator<CustomObject>, Serializable {

  private static final long serialVersionUID = -3559488985426858804L;

  public MyComparator() {
  }

  @Override
  public int compare(CustomObject co1, CustomObject co2) {

    int result = 0;
    if (co1.getClosePos() <   co2.getClosePos())  result = -1;
    if (co1.getClosePos() ==  co2.getClosePos())  result =  0;
    if (co1.getClosePos() >   co2.getClosePos())  result =  1;

    return -result;
  } // compare

}// class MyComparator
