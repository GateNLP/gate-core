/*
 *  XmlDocumentHandler.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  9 May 2000
 *
 *  $Id: XmlDocumentHandler.java 19652 2016-10-08 08:28:08Z markagreenwood $
 */
package gate.xml;

import gate.AnnotationSet;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.RepositioningInfo;
import gate.event.StatusListener;
import gate.util.Err;
import gate.util.GateSaxException;
import gate.util.OptionsMap;
import gate.util.Out;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Implements the behaviour of the XML reader
 * Methods of an object of this class are called by the SAX parser when
 * events will appear.
 * The idea is to parse the XML document and construct Gate annotations
 * objects.
 * This class also will replace the content of the Gate document with a
 * new one containing only text from the XML document.
 */
public class XmlDocumentHandler extends XmlPositionCorrectionHandler {

  /** Debug flag */
  private static final boolean DEBUG = false;
  /** Keep the refference to this structure */
  private RepositioningInfo reposInfo = null;
  /** Keep the refference to this structure */
  private RepositioningInfo ampCodingInfo = null;
  /** This is used to capture all data within two tags before calling the actual characters method */
  private StringBuffer contentBuffer = new StringBuffer("");
  /** This is a variable that shows if characters have been read */
  private boolean readCharacterStatus = false;


  /** Flag to determine whether to deserialize namespace information into
   *  annotation features within Original markups AS
   */
  private boolean deserializeNamespaceInfo = false;
  /** Feature name to use for namespace uri in namespaced elements */
  private String namespaceURIFeature = null;
  /** Feature name to use for namespace prefix in namespaced elements */
  private String namespacePrefixFeature = null;

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


  /**
   * Constructs a XmlDocumentHandler object. The annotationSet set will be the
   * default one taken from the gate document.
   * @param aDocument the Gate document that will be processed.
   * @param aMarkupElementsMap this map contains the elements name that we
   * want to create.
   * @param anElement2StringMap this map contains the strings that will be
   * added to the text contained by the key element.
   */
  public XmlDocumentHandler(gate.Document aDocument, Map<String,String> aMarkupElementsMap,
          Map<String,String> anElement2StringMap) {
    this(aDocument, aMarkupElementsMap, anElement2StringMap, null);
  } // XmlDocumentHandler

  /**
   * Constructs a XmlDocumentHandler object.
   * @param aDocument the Gate document that will be processed.
   * @param aMarkupElementsMap this map contains the elements name that we
   * want to create.
   * @param anElement2StringMap this map contains the strings that will be
   * added to the text contained by the key element.
   * @param anAnnotationSet is the annotation set that will be filled when the
   * document was processed
   */
  public XmlDocumentHandler(gate.Document aDocument,
          Map<String,String> aMarkupElementsMap,
          Map<String,String> anElement2StringMap,
          AnnotationSet anAnnotationSet) {
    // init parent
    super();
    // init stack
    stack = new Stack<CustomObject>();

    // this string contains the plain text (the text without markup)
    tmpDocContent = new StringBuffer(aDocument.getContent().size().intValue());

    // colector is used later to transform all custom objects into annotation
    // objects
    colector = new LinkedList<CustomObject>();

    // the Gate document
    doc = aDocument;

    // this map contains the elements name that we want to create
    // if it's null all the elements from the XML documents will be transformed
    // into Gate annotation objects
    markupElementsMap = aMarkupElementsMap;

    // this map contains the string that we want to insert iside the document
    // content, when a certain element is found
    // if the map is null then no string is added
    element2StringMap = anElement2StringMap;

    basicAS = anAnnotationSet;
    customObjectsId = 0;
  }// XmlDocumentHandler()/


  /**
   * This method is called when the SAX parser encounts the beginning of the
   * XML document.
   */
  @Override
  public void startDocument() throws org.xml.sax.SAXException {
    // init of variables in the parent
    super.startDocument();

    /** We will attempt to add namespace feature info to each namespaced element
     *  only if three parameters are set in the global or local config file:
     *  ADD_NAMESPACE_FEATURES: boolean flag
     *  ELEMENT_NAMESPACE_URI: feature name to use to hold namespace uri
     *  ELEMENT_NAMESPACE_PREFIX: feature name to use to hold namespace prefix
     */
    OptionsMap configData = Gate.getUserConfig();

    boolean addNSFeature = Boolean.parseBoolean((String) configData.get(GateConstants.ADD_NAMESPACE_FEATURES));
    namespaceURIFeature = (String) configData.get(GateConstants.ELEMENT_NAMESPACE_URI);
    namespacePrefixFeature = (String) configData.get(GateConstants.ELEMENT_NAMESPACE_PREFIX);

    deserializeNamespaceInfo = (addNSFeature && namespacePrefixFeature != null && !namespacePrefixFeature.isEmpty() && namespaceURIFeature != null && !namespaceURIFeature.isEmpty());

  }

  /**
   * This method is called when the SAX parser encounts the end of the
   * XML document.
   * Here we set the content of the gate Document to be the one generated
   * inside this class (tmpDocContent).
   * After that we use the colector to generate all the annotation reffering
   * this new gate document.
   */
  @Override
  public void endDocument() throws org.xml.sax.SAXException {

    // replace the document content with the one without markups
    doc.setContent(new DocumentContentImpl(tmpDocContent.toString()));

    // fire the status listener
    fireStatusChangedEvent("Total elements: " + elements);

    // If basicAs is null then get the default AnnotationSet,
    // based on the gate document.
    if (basicAS == null) {
      basicAS = doc.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    }

    // sort colector ascending on its id
    Collections.sort(colector);
    Set<Integer> testIdsSet = new HashSet<Integer>();
    // create all the annotations (on this new document) from the collector
    while (!colector.isEmpty()) {
      CustomObject obj = colector.getFirst();
      // Test to see if there are two annotation objects with the same id.
      if (testIdsSet.contains(obj.getId())) {
        throw new GateSaxException("Found two annotations with the same Id(" +
                obj.getId() +
                ").The document is inconsistent.");
      } else {
        testIdsSet.add(obj.getId());
      }// End iff
      // create a new annotation and add it to the annotation set
      try {
        // the annotation type will be conforming with markupElementsMap
        //add the annotation to the Annotation Set
        if (markupElementsMap == null) {
          basicAS.add(obj.getId(),
                  obj.getStart(),
                  obj.getEnd(),
                  obj.getElemName(),
                  obj.getFM());
        } else {
          // get the type of the annotation from Map
          String annotationType = markupElementsMap.get(obj.getElemName());
          if (annotationType != null) {
            basicAS.add(obj.getId(),
                    obj.getStart(),
                    obj.getEnd(),
                    annotationType,
                    obj.getFM());
          }
        }// End if
      } catch (gate.util.InvalidOffsetException e) {
        Err.prln("InvalidOffsetException for annot :" + obj.getElemName() +
                " with Id =" + obj.getId() + ". Discarded...");
      }// End try
      colector.remove(obj);
    }// End while
  }// endDocument();


  /**
   * This method is called when the SAX parser encounts the beginning of an
   * XML element.
   */
  /**
   *
   * @param uri - namespace uri
   * @param localName - local, unprefixed element name
   * @param qName - fully qualified, prefixed element name
   * @param atts
   * @throws SAXException
   */
  @Override
  public void startElement(String uri, String localName, String qName,
          Attributes atts) throws SAXException {

    // call characterActions
    if (readCharacterStatus) {
      readCharacterStatus = false;
      charactersAction(new String(contentBuffer).toCharArray(), 0, contentBuffer.length());
    }

    // Inform the progress listener to fire only if no of elements processed
    // so far is a multiple of ELEMENTS_RATE
    if ((++elements % ELEMENTS_RATE) == 0) {
      fireStatusChangedEvent("Processed elements : " + elements);
    }

    Integer customObjectId = null;
    // Construct a SimpleFeatureMapImpl from the list of attributes
    FeatureMap fm = Factory.newFeatureMap();

    /** Use localName rather than qName and add the namespace prefix and uri
     *  as features if global flag is set
     */
    String elemName = qName;
    boolean hasNSUri = (uri != null && !uri.isEmpty());
    if (deserializeNamespaceInfo && hasNSUri) {
      elemName = localName;
      StringTokenizer strToken = new StringTokenizer(qName, ":");
      if (strToken.countTokens() > 1) {
        String nsPrefix = strToken.nextToken();
        fm.put(namespaceURIFeature, uri);
        fm.put(namespacePrefixFeature, nsPrefix);
      }
    }


    //Get the name and the value of the attributes and add them to a FeaturesMAP
    for (int i = 0; i < atts.getLength(); i++) {
      String attName = atts.getLocalName(i);
      String attValue = atts.getValue(i);
      String attUri = atts.getURI(i);
      if (attUri != null && Gate.URI.equals(attUri)) {
        if ("gateId".equals(attName)) {
          customObjectId = Integer.parseInt(attValue);
        }// End if
        if ("annotMaxId".equals(attName)) {
          customObjectsId = Integer.parseInt(attValue);
        }// End if
        if ("matches".equals(attName)) {
          StringTokenizer strTokenizer = new StringTokenizer(attValue, ";");
          List<Integer> list = new ArrayList<Integer>();
          // Take all tokens,create Integers and add them to the list
          while (strTokenizer.hasMoreTokens()) {
            String token = strTokenizer.nextToken();
            list.add(Integer.valueOf(token));
          }// End while
          fm.put(attName, list);
        }// End if
      } else {
        fm.put(atts.getQName(i), attValue);
      }// End if
    }// End for

    // create the START index of the annotation
    Long startIndex = Long.valueOf(tmpDocContent.length());

    // initialy the Start index is equal with End index
    CustomObject obj = new CustomObject(customObjectId, elemName, fm,
            startIndex, startIndex);

    // put this object into the stack
    stack.push(obj);
  }// startElement();


  /**
   * This method is called when the SAX parser encounts the end of an
   * XML element.
   * Here we extract
   */
  /**
   *
   * @param uri - namespace uri
   * @param localName - local, unprefixed element name
   * @param qName - fully qualified, prefixed element name
   * @throws SAXException
   */
  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {

    /** Use localName rather than qName if global flag is set */
    String elemName = qName;
    boolean hasNSUri = (uri != null && !uri.isEmpty());
    if (deserializeNamespaceInfo && hasNSUri)
      elemName = localName;

    // call characterActions
    if (readCharacterStatus) {
      readCharacterStatus = false;
      charactersAction(new String(contentBuffer).toCharArray(), 0, contentBuffer.length());
    }

    // obj is for internal use
    CustomObject obj = null;

    // if the stack is not empty, we extract the custom object and
    // delete it from the stack
    if(!stack.isEmpty()) {
      obj = stack.pop();

      // Before adding it to the colector, we need to check if is an
      // emptyAndSpan one. See CustomObject's isEmptyAndSpan field.
      if(obj.getStart().equals(obj.getEnd())) {
        // The element had an end tag and its start was equal to its
        // end. Hence it is anEmptyAndSpan one.
        obj.getFM().put("isEmptyAndSpan", "true");
      }// End iff

      // Put the object into colector. Later, when the document ends
      // we will use colector to create all the annotations
      colector.add(obj);
    }// End if

    // if element is found on Element2String map, then add the string to the
    // end of the document content
    if (element2StringMap != null) {
      String stringFromMap = null;

      // test to see if element is inside the map
      // if it is then get the string value and add it to the document content
      stringFromMap = element2StringMap.get(elemName);
      if (stringFromMap != null) {
        tmpDocContent.append(stringFromMap);
      }
    }// End if
  }// endElement();

  /**
   * This method is called when the SAX parser encounts text in the XML doc.
   * Here we calculate the end indices for all the elements present inside the
   * stack and update with the new values. For entities, this method is called
   * separatley regardless of the text sourinding the entity.
   */
  @Override
  public void characters(char[] text, int start, int length) throws SAXException {
    if (!readCharacterStatus) {
      contentBuffer = new StringBuffer(new String(text, start, length));
    } else {
      contentBuffer.append(new String(text, start, length));
    }
    readCharacterStatus = true;
  }

  /**
   * This method is called when all characters between specific tags have been read completely
   */
  public void charactersAction(char[] text, int start, int length) throws SAXException {
    // correction of real offset. Didn't affect on other data.
    super.characters(text, start, length);
    // create a string object based on the reported text
    String content = new String(text, start, length);
    StringBuffer contentBuffer = new StringBuffer("");
    int tmpDocContentSize = tmpDocContent.length();
    boolean incrementStartIndex = false;
    boolean addExtraSpace = true;
    if (Gate.getUserConfig().get(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME) != null) {
      addExtraSpace =
              Gate.getUserConfig().getBoolean(
              GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME).booleanValue();
    }
    // If the first char of the text just read "text[0]" is NOT whitespace AND
    // the last char of the tmpDocContent[SIZE-1] is NOT whitespace then
    // concatenation "tmpDocContent + content" will result into a new different
    // word... and we want to avoid that, because the tokenizer, gazetter and
    // Jape work on the raw text and concatenating tokens might be not good.
    if (tmpDocContentSize != 0 &&
            content.length() != 0 &&
            !Character.isWhitespace(content.charAt(0)) &&
            !Character.isWhitespace(tmpDocContent.charAt(tmpDocContentSize - 1))) {

      // If we are here it means that a concatenation between the last
      // token in the tmpDocContent and the content(which doesn't start
      // with a white space) will be performed. In order to prevent this,
      // we will add a " " space char in order to assure that the 2 tokens
      // stay apart. Howerver we will except from this rule the most known
      // internal entities like &, <, >, etc
      if (( // Testing the length against 1 makes it more likely that
              // an internal entity was called. characters() gets called for
              // each entity separately.
              (content.length() == 1) &&
              (content.charAt(0) == '&' ||
              content.charAt(0) == '<' ||
              content.charAt(0) == '>' ||
              content.charAt(0) == '"' ||
              content.charAt(0) == '\'')) ||
              (tmpDocContent.charAt(tmpDocContentSize - 1) == '&' ||
              tmpDocContent.charAt(tmpDocContentSize - 1) == '<' ||
              tmpDocContent.charAt(tmpDocContentSize - 1) == '>' ||
              tmpDocContent.charAt(tmpDocContentSize - 1) == '"' ||
              tmpDocContent.charAt(tmpDocContentSize - 1) == '\'')) {// do nothing. The content will be appended
      } else if (!addExtraSpace) {
      } else {
        // In all other cases append " "
        contentBuffer.append(" ");
        incrementStartIndex = true;
      }// End if
    }// End if

    // put the repositioning information
    if (reposInfo != null) {
      if (!(start == 0 && length == 1 && text.length <= 2)) {
        // normal piece of text
        reposInfo.addPositionInfo(getRealOffset(), content.length(),
                tmpDocContent.length() + contentBuffer.length(),
                content.length());
        if (DEBUG) {
          Out.println("Info: " + getRealOffset() + ", " + content.length());
          Out.println("Start: " + start + " len" + length);
        } // DEBUG
      } else {
        // unicode char or &xxx; coding
        // Reported from the parser offset is 0
        // The real offset should be found in the ampCodingInfo structure.

        long lastPosition = 0;
        RepositioningInfo.PositionInfo pi;

        if (reposInfo.size() > 0) {
          pi = reposInfo.get(reposInfo.size() - 1);
          lastPosition = pi.getOriginalPosition();
        } // if

        for (int i = 0; i < ampCodingInfo.size(); ++i) {
          pi = ampCodingInfo.get(i);
          if (pi.getOriginalPosition() > lastPosition) {
            // found
            reposInfo.addPositionInfo(pi.getOriginalPosition(),
                    pi.getOriginalLength(),
                    tmpDocContent.length() + contentBuffer.length(),
                    content.length());
            break;
          } // if
        } // for
      } // if
    } // if

    // update the document content
    contentBuffer.append(content);
    // calculate the End index for all the elements of the stack
    // the expression is : End index = Current doc length + text length
    Long end = Long.valueOf(tmpDocContent.length() + contentBuffer.length());

    CustomObject obj = null;
    // Iterate through stack to modify the End index of the existing elements

    Iterator<CustomObject> anIterator = stack.iterator();
    while (anIterator.hasNext()) {
      // get the object and move to the next one
      obj = anIterator.next();
      if (incrementStartIndex && obj.getStart().equals(obj.getEnd())) {
        obj.setStart(obj.getStart() + 1);
      }// End if
      // sets its End index
      obj.setEnd(end);
    }// End while

    tmpDocContent.append(contentBuffer.toString());
  }// characters();

  /**
   * This method is called when the SAX parser encounts white spaces
   */
  @Override
  public void ignorableWhitespace(char ch[], int start, int length) throws
          SAXException {

    // internal String object
    String text = new String(ch, start, length);
    // if the last character in tmpDocContent is \n and the read whitespace is
    // \n then don't add it to tmpDocContent...

    if (tmpDocContent.length() != 0) {
      if (tmpDocContent.charAt(tmpDocContent.length() - 1) != '\n' ||
              !text.equalsIgnoreCase("\n")) {
        tmpDocContent.append(text);
      }
    }
  }

  /**
   * Error method.We deal with this exception inside SimpleErrorHandler class
   */
  @Override
  public void error(SAXParseException ex) throws SAXException {
    // deal with a SAXParseException
    // see SimpleErrorhandler class
    _seh.error(ex);
  }

  /**
   * FatalError method.
   */
  @Override
  public void fatalError(SAXParseException ex) throws SAXException {
    // deal with a SAXParseException
    // see SimpleErrorhandler class
    _seh.fatalError(ex);
  }

  /**
   * Warning method comment.
   */
  @Override
  public void warning(SAXParseException ex) throws SAXException {
    // deal with a SAXParseException
    // see SimpleErrorhandler class
    _seh.warning(ex);
  }

  /**
   * This method is called when the SAX parser encounts a comment
   * It works only if the XmlDocumentHandler implements a
   * com.sun.parser.LexicalEventListener
   */
  public void comment(String text) throws SAXException {
    // create a FeatureMap and then add the comment to the annotation set.
    /*
    gate.util.SimpleFeatureMapImpl fm = new gate.util.SimpleFeatureMapImpl();
    fm.put ("text_comment",text);
    Long node = new Long (tmpDocContent.length());
    CustomObject anObject = new CustomObject("Comment",fm,node,node);
    colector.add(anObject);
     */
  }

  /**
   * This method is called when the SAX parser encounts a start of a CDATA
   * section
   * It works only if the XmlDocumentHandler implements a
   * com.sun.parser.LexicalEventListener
   */
  public void startCDATA() throws SAXException {
  }

  /**
   * This method is called when the SAX parser encounts the end of a CDATA
   * section.
   * It works only if the XmlDocumentHandler implements a
   * com.sun.parser.LexicalEventListener
   */
  public void endCDATA() throws SAXException {
  }

  /**
   * This method is called when the SAX parser encounts a parsed Entity
   * It works only if the XmlDocumentHandler implements a
   * com.sun.parser.LexicalEventListener
   */
  public void startParsedEntity(String name) throws SAXException {
  }

  /**
   * This method is called when the SAX parser encounts a parsed entity and
   * informs the application if that entity was parsed or not
   * It's working only if the CustomDocumentHandler implements a
   *  com.sun.parser.LexicalEventListener
   */
  public void endParsedEntity(String name, boolean included) throws SAXException {
  }

  //StatusReporter Implementation
  /**
   * This methos is called when a listener is registered with this class
   */
  public void addStatusListener(StatusListener listener) {
    myStatusListeners.add(listener);
  }

  /**
   * This methos is called when a listener is removed
   */
  public void removeStatusListener(StatusListener listener) {
    myStatusListeners.remove(listener);
  }

  /**
   * This methos is called whenever we need to inform the listener about an
   * event.
   */
  protected void fireStatusChangedEvent(String text) {
    Iterator<StatusListener> listenersIter = myStatusListeners.iterator();
    while (listenersIter.hasNext()) {
      listenersIter.next().statusChanged(text);
    }
  }

  /** This method is a workaround of the java 4 non namespace supporting parser
   * It receives a qualified name and returns its local name.
   * For eg. if it receives gate:gateId it will return gateId
   */
  @SuppressWarnings("unused")
  private String getMyLocalName(String aQName) {
    if (aQName == null) {
      return "";
    }
    StringTokenizer strToken = new StringTokenizer(aQName, ":");
    if (strToken.countTokens() <= 1) {
      return aQName;
    }
    // The nr of tokens is >= than 2
    // Skip the first token which is the QName
    strToken.nextToken();
    return strToken.nextToken();
  }//getMyLocalName()

  /** Also a workaround for URI identifier. If the QName is gate it will return
   *  GATE's. Otherwhise it will return the empty string
   */
  @SuppressWarnings("unused")
  private String getMyURI(String aQName) {
    if (aQName == null) {
      return "";
    }
    StringTokenizer strToken = new StringTokenizer(aQName, ":");
    if (strToken.countTokens() <= 1) {
      return "";
    }
    // If first token is "gate" then return GATE's URI
    if ("gate".equalsIgnoreCase(strToken.nextToken())) {
      return Gate.URI;
    }
    return "";
  }// getMyURI()
  // XmlDocumentHandler member data
  // this constant indicates when to fire the status listener
  // this listener will add an overhead and we don't want a big overhead
  // this listener will be callled from ELEMENTS_RATE to ELEMENTS_RATE
  final static int ELEMENTS_RATE = 128;
  // this map contains the elements name that we want to create
  // if it's null all the elements from the XML documents will be transformed
  // into Gate annotation objects otherwise only the elements it contains will
  // be transformed
  private Map<String,String> markupElementsMap = null;
  // this map contains the string that we want to insert iside the document
  // content, when a certain element is found
  // if the map is null then no string is added
  private Map<String,String> element2StringMap = null;
  /**This object inducates what to do when the parser encounts an error*/
  private SimpleErrorHandler _seh = new SimpleErrorHandler();
  /**The content of the XML document, without any tag for internal use*/
  private StringBuffer tmpDocContent = null;
  /**A stack used to remember elements and to keep the order */
  private Stack<CustomObject> stack = null;
  /**A gate document */
  private gate.Document doc = null;
  /**An annotation set used for creating annotation reffering the doc */
  private gate.AnnotationSet basicAS = null;
  /**Listeners for status report */
  protected List<StatusListener> myStatusListeners = new LinkedList<StatusListener>();
  /**This reports the the number of elements that have beed processed so far*/
  private int elements = 0;
  /** We need a colection to retain all the CustomObjects that will be
   * transformed into annotation over the gate document...
   * the transformation will take place inside onDocumentEnd() method
   */
  private LinkedList<CustomObject> colector = null;
  /** This is used to generate unique Ids for the CustomObjects read*/
  protected int customObjectsId = 0;

  /** Accesor method for the customObjectsId field*/
  public int getCustomObjectsId() {
    return customObjectsId;
  }

  //////// INNER CLASS
  /**
   * The objects belonging to this class are used inside the stack.
   * This class is for internal needs
   */
  class CustomObject implements Comparable<CustomObject> {

    // constructor
    public CustomObject(Integer anId, String anElemName, FeatureMap aFm,
            Long aStart, Long anEnd) {
      elemName = anElemName;
      fm = aFm;
      start = aStart;
      end = anEnd;
      if (anId == null) {
        id = customObjectsId++;
      } else {
        id = anId;
        if (customObjectsId <= anId.intValue()) {
          customObjectsId = anId.intValue() + 1;
        }
      }// End if
    }// End CustomObject()

    // Methos implemented as required by Comparable interface
    @Override
    public int compareTo(CustomObject obj) {
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

    public Integer getId() {
      return id;
    }

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
    private Long end = null;
    private Integer id = null;
  } // End inner class CustomObject
} //XmlDocumentHandler



