/*
 *  HtmlDocumentHandler.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  12/June/2000
 *
 *  $Id: HtmlDocumentHandler.java 17638 2014-03-12 09:36:47Z markagreenwood $
 */

package gate.html;

import gate.Factory;
import gate.FeatureMap;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.RepositioningInfo;
import gate.event.StatusListener;
import gate.util.Err;
import gate.util.InvalidOffsetException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;


/** Implements the behaviour of the HTML reader.
  * Methods of an object of this class are called by the HTML parser when
  * events will appear.
  * The idea is to parse the HTML document and construct Gate annotations
  * objects.
  * This class also will replace the content of the Gate document with a
  * new one containing anly text from the HTML document.
  */
public class HtmlDocumentHandler extends ParserCallback {

  /** Constructor initialises all the private memeber data.
    * This will use the default annotation set taken from the gate document.
    * @param aDocument The gate document that will be processed
    * @param aMarkupElementsMap The map containing the elements that will
    * transform into annotations
    */
  public HtmlDocumentHandler(gate.Document aDocument, Map<String,String> aMarkupElementsMap) {
    this(aDocument,aMarkupElementsMap,null);
  }

  /** Constructor initialises all the private memeber data
    * @param aDocument The gate document that will be processed
    * @param aMarkupElementsMap The map containing the elements that will
    * transform into annotations
    * @param anAnnotationSet The annotation set that will contain annotations
    * resulted from the processing of the gate document
    */
  public HtmlDocumentHandler(gate.Document       aDocument,
                             Map<String,String>  aMarkupElementsMap,
                             gate.AnnotationSet  anAnnotationSet) {
    // init stack
    stack = new Stack<CustomObject>();

    // this string contains the plain text (the text without markup)
    tmpDocContent = new StringBuffer(aDocument.getContent().size().intValue());

    // colector is used later to transform all custom objects into
    // annotation objects
    colector = new LinkedList<CustomObject>();

    // the Gate document
    doc = aDocument;

    // this map contains the elements name that we want to create
    // if it's null all the elements from the XML documents will be transformed
    // into Gate annotation objects
    markupElementsMap = aMarkupElementsMap;

    // init an annotation set for this gate document
    basicAS = anAnnotationSet;

    customObjectsId = 0;
  }//HtmlDocumentHandler

  /** Keep the refference to this structure */
  private RepositioningInfo reposInfo = null;

  /** Keep the refference to this structure */
  private RepositioningInfo ampCodingInfo = null;

  /** Set repositioning information structure refference. If you set this
   *  refference to <B>null</B> information wouldn't be collected.
   */
  public void setRepositioningInfo(RepositioningInfo info) {
    reposInfo = info;
  } // setRepositioningInfo

  /** Return current RepositioningInfo object */
  public RepositioningInfo getRepositioningInfo() {
    return reposInfo;
  } // getRepositioningInfo

  /** Set repositioning information structure refference for ampersand coding.
   *  If you set this refference to <B>null</B> information wouldn't be used.
   */
  public void setAmpCodingInfo(RepositioningInfo info) {
    ampCodingInfo = info;
  } // setRepositioningInfo

  /** Return current RepositioningInfo object for ampersand coding. */
  public RepositioningInfo getAmpCodingInfo() {
    return ampCodingInfo;
  } // getRepositioningInfo

  /** The text inside the STYLE tag is processed with <code>handleText()</code>.
   *  We should skip inserting of this text in the document. */
  private boolean isInsideStyleTag = false;

  /** This method is called when the HTML parser encounts the beginning
    * of a tag that means that the tag is paired by an end tag and it's
    * not an empty one.
    */
  @Override
  public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
    // Fire the status listener if the elements processed exceded the rate
    if (0 == (++elements % ELEMENTS_RATE))
      fireStatusChangedEvent("Processed elements : " + elements);

    // Start of STYLE tag
    if(HTML.Tag.STYLE.equals(t)) {
      isInsideStyleTag = true;
    } // if

    // Construct a feature map from the attributes list
    FeatureMap fm = Factory.newFeatureMap();

    // Take all the attributes an put them into the feature map
    if (0 != a.getAttributeCount()){
      Enumeration<?> enumeration = a.getAttributeNames();
      while (enumeration.hasMoreElements()){
        Object attribute = enumeration.nextElement();
        fm.put(attribute.toString(),(a.getAttribute(attribute)).toString());
      }// while
    }// if

    // Just analize the tag t and add some\n chars and spaces to the
    // tmpDocContent.The reason behind is that we need to have a readable form
    // for the final document.
    customizeAppearanceOfDocumentWithStartTag(t);

    // If until here the "tmpDocContent" ends with a NON whitespace char,
    // then we add a space char before calculating the START index of this
    // tag.
    // This is done in order not to concatenate the content of two separate tags
    // and obtain a different NEW word.
    int tmpDocContentSize = tmpDocContent.length();
    if ( tmpDocContentSize != 0 &&
         !Character.isWhitespace(tmpDocContent.charAt(tmpDocContentSize - 1))
       ) tmpDocContent.append(" ");

    // create the start index of the annotation
    Long startIndex = new Long(tmpDocContent.length());

    // initialy the start index is equal with the End index
    CustomObject obj = new CustomObject(t.toString(),fm,startIndex,startIndex);

    // put it into the stack
    stack.push (obj);

  }//handleStartTag

   /** This method is called when the HTML parser encounts the end of a tag
     * that means that the tag is paired by a beginning tag
     */
  @Override
  public void handleEndTag(HTML.Tag t, int pos){
    // obj is for internal use
    CustomObject obj = null;

    // end of STYLE tag
    if(HTML.Tag.STYLE.equals(t)) {
      isInsideStyleTag = false;
    } // if

    // If the stack is not empty then we get the object from the stack
    if (!stack.isEmpty()){
      obj = stack.pop();
      // Before adding it to the colector, we need to check if is an
      // emptyAndSpan one. See CustomObject's isEmptyAndSpan field.
      if (obj.getStart().equals(obj.getEnd())){
        // The element had an end tag and its start was equal to its end. Hence
        // it is anEmptyAndSpan one.
        obj.getFM().put("isEmptyAndSpan","true");
      }// End iff
      // we add it to the colector
      colector.add(obj);
    }// End if

    // If element has text between, then customize its apearance
    if ( obj != null &&
         obj.getStart().longValue() != obj.getEnd().longValue()
       )
      // Customize the appearance of the document
      customizeAppearanceOfDocumentWithEndTag(t);

    // if t is the </HTML> tag then we reached the end of theHTMLdocument
    if (t == HTML.Tag.HTML){
      // replace the old content with the new one
      doc.setContent (new DocumentContentImpl(tmpDocContent.toString()));

      // If basicAs is null then get the default annotation
      // set from this gate document
      if (basicAS == null)
        basicAS = doc.getAnnotations(
                                GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

      // sort colector ascending on its id
      Collections.sort(colector);
      // iterate through colector and construct annotations
      while (!colector.isEmpty()){
        obj = colector.getFirst();
        colector.remove(obj);
          // Construct an annotation from this obj
          try{
            if (markupElementsMap == null){
               basicAS.add( obj.getStart(),
                            obj.getEnd(),
                            obj.getElemName(),
                            obj.getFM()
                           );
            }else{
              String annotationType =
                     markupElementsMap.get(obj.getElemName());
              if (annotationType != null)
                 basicAS.add( obj.getStart(),
                              obj.getEnd(),
                              annotationType,
                              obj.getFM()
                             );
            }
          }catch (InvalidOffsetException e){
              Err.prln("Error creating an annot :" + obj + " Discarded...");
          }// end try
//        }// end if
      }//while

      // notify the listener about the total amount of elements that
      // has been processed
      fireStatusChangedEvent("Total elements : " + elements);

    }//else

  }//handleEndTag

  /** This method is called when the HTML parser encounts an empty tag
    */
  @Override
  public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos){
    // fire the status listener if the elements processed exceded the rate
    if ((++elements % ELEMENTS_RATE) == 0)
      fireStatusChangedEvent("Processed elements : " + elements);

    // construct a feature map from the attributes list
    // these are empty elements
    FeatureMap fm = Factory.newFeatureMap();

    // take all the attributes an put them into the feature map
    if (0 != a.getAttributeCount ()){

       // Out.println("HAS  attributes = " + a.getAttributeCount ());
        Enumeration<?> enumeration = a.getAttributeNames ();
        while (enumeration.hasMoreElements ()){
          Object attribute = enumeration.nextElement ();
          fm.put ( attribute.toString(),(a.getAttribute(attribute)).toString());

        }//while

    }//if

    // create the start index of the annotation
    Long startIndex = new Long(tmpDocContent.length());

    // initialy the start index is equal with the End index
    CustomObject obj = new CustomObject(t.toString(),fm,startIndex,startIndex);

    // we add the object directly into the colector
    // we don't add it to the stack because this is an empty tag
    colector.add(obj);

    // Just analize the tag t and add some\n chars and spaces to the
    // tmpDocContent.The reason behind is that we need to have a readable form
    // for the final document.
    customizeAppearanceOfDocumentWithSimpleTag(t);

  } // handleSimpleTag

  /** This method is called when the HTML parser encounts text (PCDATA)
    */
  @Override
  public void handleText(char[] text, int pos){

    // Skip the STYLE tag content
    if(isInsideStyleTag) return;

    // create a string object based on the reported text
    String content = new String(text);

    // remove the difference between JDK 1.3 and JDK 1.4
    String trimContent = content.trim();
    if(trimContent.length() == 0) {
      return;
    } // if

    int trimCorrection = content.indexOf(trimContent.charAt(0));
    content = trimContent;

    StringBuffer contentBuffer = new StringBuffer("");
    int tmpDocContentSize = tmpDocContent.length();
    boolean incrementStartIndex = false;
    // If the first char of the text just read "text[0]" is NOT whitespace AND
    // the last char of the tmpDocContent[SIZE-1] is NOT whitespace then
    // concatenation "tmpDocContent + content" will result into a new different
    // word... and we want to avoid that...
    if ( tmpDocContentSize != 0 &&
         content.length() != 0 &&
         !Character.isWhitespace(content.charAt(0)) &&
         !Character.isWhitespace(tmpDocContent.charAt(tmpDocContentSize - 1))){

            contentBuffer.append(" ");
            incrementStartIndex = true;
    }// End if
    // update the document content

    // put the repositioning information
    if(reposInfo != null) {
      int extractedPos = tmpDocContent.length() + contentBuffer.length();
      addRepositioningInfo(content, pos + trimCorrection, extractedPos);
    } // if

    contentBuffer.append(content);
    // calculate the End index for all the elements of the stack
    // the expression is : End index = Current doc length + text length
    Long end = new Long(tmpDocContent.length() + contentBuffer.length());

    CustomObject obj = null;
    // Iterate through stack to modify the End index of the existing elements

    Iterator<CustomObject> anIterator = stack.iterator();
    while (anIterator.hasNext ()){
      // get the object and move to the next one
      obj = anIterator.next ();
      if (incrementStartIndex && obj.getStart().equals(obj.getEnd())){
        obj.setStart(new Long(obj.getStart().longValue() + 1));
      }// End if
      // sets its End index
      obj.setEnd(end);
    }// End while

    tmpDocContent.append(contentBuffer.toString());
  }// end handleText();

  /** For given content the list with shrink position information is searched
   *  and on the corresponding positions the correct repositioning information
   *  is calculated and generated.
   */
  public void addRepositioningInfo(String content, int pos, int extractedPos) {
    int contentLength = content.length();

    // wrong way (without correction and analysing)
   //reposInfo.addPositionInfo(pos, contentLength, extractedPos, contentLength);

    RepositioningInfo.PositionInfo pi = null;
    long startPos = pos;
    long correction = 0;
    long substituteStart;
    long remainingLen;
    long offsetInExtracted;

    for(int i = 0; i < ampCodingInfo.size(); ++i) {
      pi = ampCodingInfo.get(i);
      substituteStart = pi.getOriginalPosition();

      if(substituteStart >= startPos) {
        if(substituteStart > pos + contentLength + correction) {
          break; // outside the current text
        } // if

        // should create two repositioning information records
        remainingLen = substituteStart - (startPos + correction);
        offsetInExtracted = startPos - pos;
        if(remainingLen > 0) {
          reposInfo.addPositionInfo(startPos + correction, remainingLen,
                            extractedPos + offsetInExtracted, remainingLen);
        } // if
        // record for shrank text
        reposInfo.addPositionInfo(substituteStart, pi.getOriginalLength(),
                          extractedPos + offsetInExtracted + remainingLen,
                          pi.getCurrentLength());
        startPos = startPos + remainingLen + pi.getCurrentLength();
        correction += pi.getOriginalLength() - pi.getCurrentLength();
      } // if
    } // for

    // there is some text remaining for repositioning
    offsetInExtracted = startPos - pos;
    remainingLen = contentLength - offsetInExtracted;
    if(remainingLen > 0) {
      reposInfo.addPositionInfo(startPos + correction, remainingLen,
                        extractedPos + offsetInExtracted, remainingLen);
    } // if
  } // addRepositioningInfo

  /** This method analizes the tag t and adds some \n chars and spaces to the
    * tmpDocContent.The reason behind is that we need to have a readable form
    * for the final document. This method modifies the content of tmpDocContent.
    * @param t the Html tag encounted by the HTML parser
    */
  protected void customizeAppearanceOfDocumentWithSimpleTag(HTML.Tag t){
    boolean modification = false;
    // if the HTML tag is BR then we add a new line character to the document
    if (HTML.Tag.BR == t){
      tmpDocContent.append("\n");
      modification = true;
    }// End if
    if (modification == true){
      Long end = new Long (tmpDocContent.length());
      Iterator<CustomObject> anIterator = stack.iterator();
      while (anIterator.hasNext ()){
        // get the object and move to the next one
        CustomObject obj = anIterator.next();
        // sets its End index
        obj.setEnd(end);
      }// End while
    }//End if
  }// customizeAppearanceOfDocumentWithSimpleTag

  /** This method analizes the tag t and adds some \n chars and spaces to the
    * tmpDocContent.The reason behind is that we need to have a readable form
    * for the final document. This method modifies the content of tmpDocContent.
    * @param t the Html tag encounted by the HTML parser
    */
  protected void customizeAppearanceOfDocumentWithStartTag(HTML.Tag t){
    boolean modification = false;
    if (HTML.Tag.P == t){
      int tmpDocContentSize = tmpDocContent.length();
      if ( tmpDocContentSize >= 2 &&
           '\n' != tmpDocContent.charAt(tmpDocContentSize - 2)
         ) { tmpDocContent.append("\n"); modification = true;}
    }// End if
    if (modification == true){
      Long end = new Long (tmpDocContent.length());
      Iterator<CustomObject> anIterator = stack.iterator();
      while (anIterator.hasNext ()){
        // get the object and move to the next one
        CustomObject obj = anIterator.next();
        // sets its End index
        obj.setEnd(end);
      }// End while
    }//End if
  }// customizeAppearanceOfDocumentWithStartTag

  /** This method analizes the tag t and adds some \n chars and spaces to the
    * tmpDocContent.The reason behind is that we need to have a readable form
    * for the final document. This method modifies the content of tmpDocContent.
    * @param t the Html tag encounted by the HTML parser
    */
  protected void customizeAppearanceOfDocumentWithEndTag(HTML.Tag t){
    boolean modification = false;
    // if the HTML tag is BR then we add a new line character to the document
    if ( (HTML.Tag.P == t) ||

         (HTML.Tag.H1 == t) ||
         (HTML.Tag.H2 == t) ||
         (HTML.Tag.H3 == t) ||
         (HTML.Tag.H4 == t) ||
         (HTML.Tag.H5 == t) ||
         (HTML.Tag.H6 == t) ||
         (HTML.Tag.TR == t) ||
         (HTML.Tag.CENTER == t) ||
         (HTML.Tag.LI == t)
       ){ tmpDocContent.append("\n"); modification = true;}

    if (HTML.Tag.TITLE == t){
      tmpDocContent.append("\n\n");
      modification = true;
    }// End if

    if (modification == true){
      Long end = new Long (tmpDocContent.length());
      Iterator<CustomObject> anIterator = stack.iterator();
      while (anIterator.hasNext ()){
        // get the object and move to the next one
        CustomObject obj = anIterator.next();
        // sets its End index
        obj.setEnd(end);
      }// End while
    }//End if
  }// customizeAppearanceOfDocumentWithEndTag

  /**
    * This method is called when the HTML parser encounts an error
    * it depends on the programmer if he wants to deal with that error
    */
  @Override
  public void handleError(String errorMsg, int pos) {
    //Out.println ("ERROR CALLED : " + errorMsg);
  }

  /** This method is called once, when the HTML parser reaches the end
    * of its input streamin order to notify the parserCallback that there
    * is nothing more to parse.
    */
  @Override
  public void flush() throws BadLocationException{
  }// flush

  /** This method is called when the HTML parser encounts a comment
    */
  @Override
  public void handleComment(char[] text, int pos) {
  }

  //StatusReporter Implementation

  public void addStatusListener(StatusListener listener) {
    myStatusListeners.add(listener);
  }

  public void removeStatusListener(StatusListener listener) {
    myStatusListeners.remove(listener);
  }

  protected void fireStatusChangedEvent(String text) {
    Iterator<StatusListener> listenersIter = myStatusListeners.iterator();
    while(listenersIter.hasNext())
      listenersIter.next().statusChanged(text);
  }

  /**
    * This method verifies if data contained by the CustomObject can be used
    * to create a GATE annotation.
    */
/*  private boolean canCreateAnnotation(CustomObject aCustomObject){
    long start            = aCustomObject.getStart().longValue();
    long end              = aCustomObject.getEnd().longValue();
    long gateDocumentSize = doc.getContent().size().longValue();

    if (start < 0 || end < 0 ) return false;
    if (start > end ) return false;
    if ((start > gateDocumentSize) || (end > gateDocumentSize)) return false;
    return true;
  }// canCreateAnnotation
*/

  // HtmlDocumentHandler member data

  // this constant indicates when to fire the status listener
  // this listener will add an overhead and we don't want a big overhead
  // this listener will be callled from ELEMENTS_RATE to ELEMENTS_RATE
  final static  int ELEMENTS_RATE = 128;

  // this map contains the elements name that we want to create
  // if it's null all the elements from the HTML documents will be transformed
  // into Gate annotation objects otherwise only the elements it contains will
  // be transformed
  private Map<String,String> markupElementsMap = null;

  // the content of the HTML document, without any tag
  // for internal use
  private StringBuffer tmpDocContent = null;

  // a stack used to remember elements and to keep the order
  private Stack<CustomObject> stack = null;

  // a gate document
  private gate.Document doc = null;

  // an annotation set used for creating annotation reffering the doc
  private gate.AnnotationSet basicAS;

  // listeners for status report
  protected List<StatusListener> myStatusListeners = new LinkedList<StatusListener>();

  // this reports the the number of elements that have beed processed so far
  private int elements = 0;

  protected  long customObjectsId = 0;
  // we need a colection to retain all the CustomObjects that will be
  // transformed into annotation over the gate document...
  // the transformation will take place inside onDocumentEnd() method
  private LinkedList<CustomObject> colector = null;

  // Inner class
  /**
    * The objects belonging to this class are used inside the stack.
    * This class is for internal needs
    */
  class  CustomObject implements Comparable<CustomObject> {

    // constructor
    public CustomObject(String anElemName, FeatureMap aFm,
                           Long aStart, Long anEnd) {
      elemName = anElemName;
      fm = aFm;
      start = aStart;
      end = anEnd;
      id = new Long(customObjectsId ++);
    }// End CustomObject()

    // Methos implemented as required by Comparable interface
    @Override
    public int compareTo(CustomObject obj){
      return this.id.compareTo(obj.getId());
    }// compareTo();

    // accesor
    public String getElemName() {
      return elemName;
    }// getElemName()

    public FeatureMap getFM() {
      return fm;
    }// getFM()

    public Long getStart() {
      return start;
    }// getStart()

    public Long getEnd() {
      return end;
    }// getEnd()

    public Long getId(){ return id;}

    // mutator
    public void setElemName(String anElemName) {
      elemName = anElemName;
    }// getElemName()

    public void setFM(FeatureMap aFm) {
      fm = aFm;
    }// setFM();

    public void setStart(Long aStart) {
      start = aStart;
    }// setStart();

    public void setEnd(Long anEnd) {
      end = anEnd;
    }// setEnd();

    // data fields
    private String elemName = null;
    private FeatureMap fm = null;
    private Long start = null;
    private Long end  = null;
    private Long id = null;

  } // End inner class CustomObject

}//End class HtmlDocumentHandler



