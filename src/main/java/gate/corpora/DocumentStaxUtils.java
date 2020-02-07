/*
 *  DocumentStaxUtils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 20/Jul/2006
 *
 *  $Id: DocumentStaxUtils.java 19658 2016-10-10 06:46:13Z markagreenwood $
 */
package gate.corpora;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.TextualDocument;
import gate.event.StatusListener;
import gate.relations.Relation;
import gate.relations.RelationSet;
import gate.relations.SimpleRelation;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.Out;

/**
 * This class provides support for reading and writing GATE XML format
 * using StAX (the Streaming API for XML).
 */
public class DocumentStaxUtils {

  private static XMLInputFactory inputFactory = null;

  /**
   * The char used to replace characters in text content that are
   * illegal in XML.
   */
  public static final char INVALID_CHARACTER_REPLACEMENT = ' ';

  public static final String GATE_XML_VERSION = "3";
  
  /**
   * The number of &lt; signs after which we encode a string using CDATA
   * rather than writeCharacters.
   */
  public static final int LT_THRESHOLD = 5;

  /**
   * Reads GATE XML format data from the given XMLStreamReader and puts
   * the content and annotation sets into the given Document, replacing
   * its current content. The reader must be positioned on the opening
   * GateDocument tag (i.e. the last event was a START_ELEMENT for which
   * getLocalName returns "GateDocument"), and when the method returns
   * the reader will be left positioned on the corresponding closing
   * tag.
   * 
   * @param xsr the source of the XML to parse
   * @param doc the document to update
   * @throws XMLStreamException
   */
  public static void readGateXmlDocument(XMLStreamReader xsr, Document doc)
          throws XMLStreamException {
    readGateXmlDocument(xsr, doc, null);
  }

  /**
   * Reads GATE XML format data from the given XMLStreamReader and puts
   * the content and annotation sets into the given Document, replacing
   * its current content. The reader must be positioned on the opening
   * GateDocument tag (i.e. the last event was a START_ELEMENT for which
   * getLocalName returns "GateDocument"), and when the method returns
   * the reader will be left positioned on the corresponding closing
   * tag.
   * 
   * @param xsr the source of the XML to parse
   * @param doc the document to update
   * @param statusListener optional status listener to receive status
   *          messages
   * @throws XMLStreamException
   */
  public static void readGateXmlDocument(XMLStreamReader xsr, Document doc,
          StatusListener statusListener) throws XMLStreamException {
    DocumentContent savedContent = null;

    // check the precondition
    xsr.require(XMLStreamConstants.START_ELEMENT, null, "GateDocument");

    // process the document features
    xsr.nextTag();
    xsr.require(XMLStreamConstants.START_ELEMENT, null, "GateDocumentFeatures");

    if(statusListener != null) {
      statusListener.statusChanged("Reading document features");
    }
    FeatureMap documentFeatures = readFeatureMap(xsr);

    // read document text, building the map of node IDs to offsets
    xsr.nextTag();
    xsr.require(XMLStreamConstants.START_ELEMENT, null, "TextWithNodes");

    Map<Integer, Long> nodeIdToOffsetMap = new HashMap<Integer, Long>();
    if(statusListener != null) {
      statusListener.statusChanged("Reading document content");
    }
    String documentText = readTextWithNodes(xsr, nodeIdToOffsetMap);

    // save the content, in case anything goes wrong later
    savedContent = doc.getContent();
    // set the document content to the text with nodes text.
    doc.setContent(new DocumentContentImpl(documentText));

    try {
      int numAnnots = 0;
      // process annotation sets, using the node map built above
      Integer maxAnnotId = null;
      // initially, we don't know whether annotation IDs are required or
      // not
      Boolean requireAnnotationIds = null;
      int eventType = xsr.nextTag();
      while(eventType == XMLStreamConstants.START_ELEMENT && xsr.getLocalName().equals("AnnotationSet")) {
        xsr.require(XMLStreamConstants.START_ELEMENT, null, "AnnotationSet");
        String annotationSetName = xsr.getAttributeValue(null, "Name");
        AnnotationSet annotationSet = null;
        if(annotationSetName == null) {
          if(statusListener != null) {
            statusListener.statusChanged("Reading default annotation set");
          }
          annotationSet = doc.getAnnotations();
        }
        else {
          if(statusListener != null) {
            statusListener.statusChanged("Reading \"" + annotationSetName
                    + "\" annotation set");
          }
          annotationSet = doc.getAnnotations(annotationSetName);
        }
        annotationSet.clear();
        SortedSet<Integer> annotIdsInSet = new TreeSet<Integer>();
        requireAnnotationIds = readAnnotationSet(xsr, annotationSet,
                nodeIdToOffsetMap, annotIdsInSet, requireAnnotationIds);
        if(annotIdsInSet.size() > 0
                && (maxAnnotId == null || annotIdsInSet.last().intValue() > maxAnnotId
                        .intValue())) {
          maxAnnotId = annotIdsInSet.last();
        }
        numAnnots += annotIdsInSet.size();
        // readAnnotationSet leaves reader positioned on the
        // </AnnotationSet> tag, so nextTag takes us to either the next
        // <AnnotationSet>, a <RelationSet>, or </GateDocument>
        eventType = xsr.nextTag();
      }

      while(eventType == XMLStreamConstants.START_ELEMENT
              && xsr.getLocalName().equals("RelationSet")) {
        xsr.require(XMLStreamConstants.START_ELEMENT, null, "RelationSet");
        String relationSetName = xsr.getAttributeValue(null, "Name");
        RelationSet relations = null;
        if(relationSetName == null) {
          if(statusListener != null) {
            statusListener
                    .statusChanged("Reading relation set for default annotation set");
          }
          relations = doc.getAnnotations().getRelations();
        } else {
          if(statusListener != null) {
            statusListener.statusChanged("Reading relation set for \""
                    + relationSetName + "\" annotation set");
          }
          relations = doc.getAnnotations(relationSetName).getRelations();
        }

        SortedSet<Integer> relIdsInSet = new TreeSet<Integer>();
        readRelationSet(xsr, relations, relIdsInSet);
        if(relIdsInSet.size() > 0
                && (maxAnnotId == null || relIdsInSet.last().intValue() > maxAnnotId
                        .intValue())) {
          maxAnnotId = relIdsInSet.last();
        }
        numAnnots += relIdsInSet.size();
        // readAnnotationSet leaves reader positioned on the
        // </RelationSet> tag, so nextTag takes us to either the next
        // <RelationSet> or to the </GateDocument>
        eventType = xsr.nextTag();
      }
      
      // check we are on the end document tag
      xsr.require(XMLStreamConstants.END_ELEMENT, null, "GateDocument");

      doc.setFeatures(documentFeatures);

      // set the ID generator, if doc is a DocumentImpl
      if(doc instanceof DocumentImpl && maxAnnotId != null) {
        ((DocumentImpl)doc).setNextAnnotationId(maxAnnotId.intValue() + 1);
      }
      if(statusListener != null) {
        statusListener.statusChanged("Finished.  " + numAnnots
                + " annotation(s) processed");
      }
    }
    // in case of exception, reset document content to the unparsed XML
    catch(XMLStreamException xse) {
      doc.setContent(savedContent);
      throw xse;
    }
    catch(RuntimeException re) {
      doc.setContent(savedContent);
      throw re;
    }
  }

  /**
   * Processes an AnnotationSet element from the given reader and fills
   * the given annotation set with the corresponding annotations. The
   * reader must initially be positioned on the starting AnnotationSet
   * tag and will be left positioned on the correspnding closing tag.
   * 
   * @param xsr the reader
   * @param annotationSet the annotation set to fill.
   * @param nodeIdToOffsetMap a map mapping node IDs (Integer) to their
   *          offsets in the text (Long). If null, we assume that the
   *          node ids and offsets are the same (useful if parsing an
   *          annotation set in isolation).
   * @param allAnnotIds a set to contain all annotation IDs specified in
   *          the annotation set. It should initially be empty and will
   *          be updated if any of the annotations in this set specify
   *          an ID.
   * @param requireAnnotationIds whether annotations are required to
   *          specify their IDs. If true, it is an error for an
   *          annotation to omit the Id attribute. If false, it is an
   *          error for the Id to be present. If null, we have not yet
   *          determined what style of XML this is.
   * @return <code>requireAnnotationIds</code>. If the passed in
   *         value was null, and we have since determined what it should
   *         be, the updated value is returned.
   * @throws XMLStreamException
   */
  public static Boolean readAnnotationSet(XMLStreamReader xsr,
          AnnotationSet annotationSet, Map<Integer, Long> nodeIdToOffsetMap,
          Set<Integer> allAnnotIds, Boolean requireAnnotationIds)
          throws XMLStreamException {
    List<AnnotationObject> collectedAnnots = new ArrayList<AnnotationObject>();
    while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
      xsr.require(XMLStreamConstants.START_ELEMENT, null, "Annotation");
      AnnotationObject annObj = new AnnotationObject();
      annObj.setElemName(xsr.getAttributeValue(null, "Type"));
      try {
        int startNodeId = Integer.parseInt(xsr.getAttributeValue(null,
                "StartNode"));
        if(nodeIdToOffsetMap != null) {
          Long startOffset = nodeIdToOffsetMap.get(startNodeId);
          if(startOffset != null) {
            annObj.setStart(startOffset);
          }
          else {
            throw new XMLStreamException("Invalid start node ID", xsr
                    .getLocation());
          }
        }
        else {
          // no offset map, so just use the ID as an offset
          annObj.setStart(Long.valueOf(startNodeId));
        }
      }
      catch(NumberFormatException nfe) {
        throw new XMLStreamException("Non-integer value found for StartNode",
                xsr.getLocation());
      }

      try {
        int endNodeId = Integer
                .parseInt(xsr.getAttributeValue(null, "EndNode"));
        if(nodeIdToOffsetMap != null) {
          Long endOffset = nodeIdToOffsetMap.get(endNodeId);
          if(endOffset != null) {
            annObj.setEnd(endOffset);
          }
          else {
            throw new XMLStreamException("Invalid end node ID", xsr
                    .getLocation());
          }
        }
        else {
          // no offset map, so just use the ID as an offset
          annObj.setEnd(Long.valueOf(endNodeId));
        }
      }
      catch(NumberFormatException nfe) {
        throw new XMLStreamException("Non-integer value found for EndNode", xsr
                .getLocation());
      }

      String annotIdString = xsr.getAttributeValue(null, "Id");
      if(annotIdString == null) {
        if(requireAnnotationIds == null) {
          // if one annotation doesn't specify Id than all must
          requireAnnotationIds = Boolean.FALSE;
        }
        else {
          if(requireAnnotationIds.booleanValue()) {
            // if we were expecting an Id but didn't get one...
            throw new XMLStreamException(
                    "New style GATE XML format requires that every annotation "
                            + "specify its Id, but an annotation with no Id was found",
                    xsr.getLocation());
          }
        }
      }
      else {
        // we have an ID attribute
        if(requireAnnotationIds == null) {
          // if one annotation specifies an Id then all must
          requireAnnotationIds = Boolean.TRUE;
        }
        else {
          if(!requireAnnotationIds.booleanValue()) {
            // if we were expecting not to have an Id but got one...
            throw new XMLStreamException(
                    "Old style GATE XML format requires that no annotation "
                            + "specifies its Id, but an annotation with an Id was found",
                    xsr.getLocation());
          }
        }
        try {
          Integer annotationId = Integer.valueOf(annotIdString);
          if(allAnnotIds.contains(annotationId)) {
            throw new XMLStreamException("Annotation IDs must be unique "
                    + "within an annotation set. Found duplicate ID", xsr
                    .getLocation());
          }
          allAnnotIds.add(annotationId);
          annObj.setId(annotationId);
        }
        catch(NumberFormatException nfe) {
          throw new XMLStreamException("Non-integer annotation ID found", xsr
                  .getLocation());
        }
      }

      // get the features of this annotation
      annObj.setFM(readFeatureMap(xsr));
      // readFeatureMap leaves xsr on the </Annotation> tag
      collectedAnnots.add(annObj);
    }

    // now process all found annotations.to add to the set
    Iterator<AnnotationObject> collectedAnnotsIt = collectedAnnots.iterator();
    while(collectedAnnotsIt.hasNext()) {
      AnnotationObject annObj = collectedAnnotsIt.next();
      try {
        if(annObj.getId() != null) {
          annotationSet.add(annObj.getId(), annObj.getStart(), annObj.getEnd(),
                  annObj.getElemName(), annObj.getFM());
        }
        else {
          annotationSet.add(annObj.getStart(), annObj.getEnd(), annObj
                  .getElemName(), annObj.getFM());
        }
      }
      catch(InvalidOffsetException ioe) {
        // really shouldn't happen, but could if we're not using an id
        // to offset map
        throw new XMLStreamException("Invalid offset when creating annotation "
                + annObj, ioe);
      }
    }
    return requireAnnotationIds;
  }
  
  public static void readRelationSet(XMLStreamReader xsr,
          RelationSet relations, Set<Integer> allAnnotIds)
          throws XMLStreamException {
    while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
      xsr.require(XMLStreamConstants.START_ELEMENT, null, "Relation");
      String type = xsr.getAttributeValue(null, "Type");
      String idString = xsr.getAttributeValue(null, "Id");
      String memberString = xsr.getAttributeValue(null, "Members");
      
      if(memberString == null)
        throw new XMLStreamException("A relation must have members");
      if (type == null)
        throw new XMLStreamException("A relation must have a type");
      if (idString == null)
        throw new XMLStreamException("A relation must have an id");
      
      String[] memberStrings = memberString.split(";");
      int[] members = new int[memberStrings.length];
      for(int i = 0; i < members.length; ++i) {
        members[i] = Integer.parseInt(memberStrings[i]);
      }

      xsr.nextTag();
      xsr.require(XMLStreamConstants.START_ELEMENT, null, "UserData");

      // get the string representation of the user data
      StringBuilder stringRep = new StringBuilder(1024);
      int eventType;
      while((eventType = xsr.next()) != XMLStreamConstants.END_ELEMENT) {
        switch(eventType) {
          case XMLStreamConstants.CHARACTERS:
          case XMLStreamConstants.CDATA:
            stringRep.append(xsr.getTextCharacters(), xsr.getTextStart(),
                    xsr.getTextLength());
            break;

          case XMLStreamConstants.START_ELEMENT:
            throw new XMLStreamException("Elements not allowed within "
                    + "user data.", xsr.getLocation());

          default:
            // do nothing - ignore comments, PIs, etc.
        }
      }

      xsr.require(XMLStreamConstants.END_ELEMENT, null, "UserData");

      FeatureMap features = readFeatureMap(xsr);

      Relation r = new SimpleRelation(Integer.parseInt(idString), type, members);
      r.setFeatures(features);

      if(stringRep.length() > 0) {
        ObjectWrapper wrapper = new ObjectWrapper(stringRep.toString());
        r.setUserData(wrapper.getValue());
      }

      relations.add(r);
    }
  }

  /**
   * Processes the TextWithNodes element from this XMLStreamReader,
   * returning the text content of the document. The supplied map is
   * updated with the offset of each Node element encountered. The
   * reader must be positioned on the starting TextWithNodes tag and
   * will be returned positioned on the corresponding closing tag.
   * 
   * @param xsr
   * @param nodeIdToOffsetMap
   * @return the text content of the document
   */
  public static String readTextWithNodes(XMLStreamReader xsr,
          Map<Integer, Long> nodeIdToOffsetMap) throws XMLStreamException {
    StringBuffer textBuf = new StringBuffer(20480);
    int eventType;
    while((eventType = xsr.next()) != XMLStreamConstants.END_ELEMENT) {
      switch(eventType) {
        case XMLStreamConstants.CHARACTERS:
        case XMLStreamConstants.CDATA:
          textBuf.append(xsr.getTextCharacters(), xsr.getTextStart(), xsr
                  .getTextLength());
          break;

        case XMLStreamConstants.START_ELEMENT:
          // only Node elements allowed
          xsr.require(XMLStreamConstants.START_ELEMENT, null, "Node");
          String idString = xsr.getAttributeValue(null, "id");
          if(idString == null) {
            throw new XMLStreamException("Node element has no id", xsr
                    .getLocation());
          }
          try {
            Integer id = Integer.valueOf(idString);
            Long offset = Long.valueOf(textBuf.length());
            nodeIdToOffsetMap.put(id, offset);
          }
          catch(NumberFormatException nfe) {
            throw new XMLStreamException("Node element must have "
                    + "integer id", xsr.getLocation());
          }

          // Node element must be empty
          if(xsr.next() != XMLStreamConstants.END_ELEMENT) {
            throw new XMLStreamException("Node element within TextWithNodes "
                    + "must be empty.", xsr.getLocation());
          }
          break;

        default:
          // do nothing - ignore comments, PIs...
      }
    }
    return textBuf.toString();
  }

  /**
   * Processes a GateDocumentFeatures or Annotation element to build a
   * feature map. The element is expected to contain Feature children,
   * each with a Name and Value. The reader will be returned positioned
   * on the closing GateDocumentFeatures or Annotation tag.
   * 
   * @throws XMLStreamException
   */
  public static FeatureMap readFeatureMap(XMLStreamReader xsr)
          throws XMLStreamException {
    FeatureMap fm = Factory.newFeatureMap();
    while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
      xsr.require(XMLStreamConstants.START_ELEMENT, null, "Feature");
      Object featureName = null;
      Object featureValue = null;
      while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
        if("Name".equals(xsr.getLocalName())) {
          featureName = readFeatureNameOrValue(xsr);
        }
        else if("Value".equals(xsr.getLocalName())) {
          featureValue = readFeatureNameOrValue(xsr);
        }
        else {
          throw new XMLStreamException("Feature element should contain "
                  + "only Name and Value children", xsr.getLocation());
        }
      }
      fm.put(featureName, featureValue);
    }
    return fm;
  }

  /**
   * Read the name or value of a feature. The reader must be initially
   * positioned on an element with className and optional itemClassName
   * attributes, and text content convertable to this class. It will be
   * returned on the corresponding end tag.
   * 
   * @param xsr the reader
   * @return the name or value represented by this element.
   * @throws XMLStreamException
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  static Object readFeatureNameOrValue(XMLStreamReader xsr)
          throws XMLStreamException {
    String className = xsr.getAttributeValue(null, "className");
    if(className == null) {
      className = "java.lang.String";
    }
    String itemClassName = xsr.getAttributeValue(null, "itemClassName");
    if(itemClassName == null) {
      itemClassName = "java.lang.String";
    }
    // get the string representation of the name/value
    StringBuffer stringRep = new StringBuffer(1024);
    int eventType;
    while((eventType = xsr.next()) != XMLStreamConstants.END_ELEMENT) {
      switch(eventType) {
        case XMLStreamConstants.CHARACTERS:
        case XMLStreamConstants.CDATA:
          stringRep.append(xsr.getTextCharacters(), xsr.getTextStart(), xsr
                  .getTextLength());
          break;
		  
        case XMLStreamConstants.START_ELEMENT:
          throw new XMLStreamException("Elements not allowed within "
                  + "feature name or value element.", xsr.getLocation());

        default:
          // do nothing - ignore comments, PIs, etc.
      }
    }

    // shortcut - if class name is java.lang.String, just return the
    // string representation directly
    if("java.lang.String".equals(className)) {
      return stringRep.toString();
    }

    // otherwise, do some fancy reflection
    Class<?> theClass = null;
    try {
      theClass = Class.forName(className, true, Gate.getClassLoader());
    }
    catch(ClassNotFoundException cnfe) {
      // give up and just return the String
      return stringRep.toString();
    }

    if(java.util.Collection.class.isAssignableFrom(theClass)) {
      Class<?> itemClass = null;
      Constructor<?> itemConstructor = null;
      Collection featObject = null;

      boolean addItemAsString = false;

      // construct the collection object to use as the feature value
      try {
        featObject = (Collection)theClass.newInstance();
      }
      // if we can't instantiate the collection class at all, give up
      // and return the value as a string
      catch(IllegalAccessException iae) {
        return stringRep.toString();
      }
      catch(InstantiationException ie) {
        return stringRep.toString();
      }

      // common case - itemClass *is* java.lang.String, so we can
      // avoid all the reflection
      if("java.lang.String".equals(itemClassName)) {
        addItemAsString = true;
      }
      else {
        try {
          itemClass = Class.forName(itemClassName, true, Gate.getClassLoader());
          // Let's detect if itemClass takes a constructor with a String
          // as param
          Class<?>[] paramsArray = new Class[1];
          paramsArray[0] = java.lang.String.class;
          itemConstructor = itemClass.getConstructor(paramsArray);
        }
        catch(ClassNotFoundException cnfex) {
          Out.prln("Warning: Item class " + itemClassName + " not found."
                  + "Adding items as Strings");
          addItemAsString = true;
        }
        catch(NoSuchMethodException nsme) {
          addItemAsString = true;
        }
        catch(SecurityException se) {
          addItemAsString = true;
        }// End try
      }

      StringTokenizer strTok = new StringTokenizer(stringRep.toString(), ";");
      Object[] params = new Object[1];
      Object itemObj = null;
      while(strTok.hasMoreTokens()) {
        String itemStrRep = strTok.nextToken();
        if(addItemAsString)
          featObject.add(itemStrRep);
        else {
          params[0] = itemStrRep;
          try {
            itemObj = itemConstructor.newInstance(params);
          }
          catch(Exception e) {
            throw new XMLStreamException("An item(" + itemStrRep
                    + ")  does not comply with its class" + " definition("
                    + itemClassName + ")", xsr.getLocation());
          }// End try
          featObject.add(itemObj);
        }// End if
      }// End while

      return featObject;
    }// End if

    // If currentfeatClass is not a Collection and not String, test to
    // see if it has a constructor that takes a String as param
    Class<?>[] params = new Class[1];
    params[0] = java.lang.String.class;
    try {
      Constructor<?> featConstr = theClass.getConstructor(params);
      Object[] featConstrParams = new Object[1];
      featConstrParams[0] = stringRep.toString();
      Object featObject = featConstr.newInstance(featConstrParams);
      if(featObject instanceof ObjectWrapper) {
        featObject = ((ObjectWrapper)featObject).getValue();
      }
      return featObject;
    }
    catch(Exception e) {
      return stringRep.toString();
    }// End try
  }

  // ///// Reading XCES /////

  // constants
  /**
   * Version of XCES that this class can handle.
   */
  public static final String XCES_VERSION = "1.0";

  /**
   * XCES namespace URI.
   */
  public static final String XCES_NAMESPACE = "http://www.xces.org/schema/2003";

  /**
   * Read XML data in <a href="http://www.xces.org/">XCES</a> format
   * from the given stream and add the corresponding annotations to the
   * given annotation set. This method does not close the stream, this
   * is the responsibility of the caller.
   * 
   * @param is the input stream to read from, which will <b>not</b> be
   *          closed before returning.
   * @param as the annotation set to read into.
   */
  public static void readXces(InputStream is, AnnotationSet as)
          throws XMLStreamException {
    if(inputFactory == null) {
      inputFactory = XMLInputFactory.newInstance();
    }
    XMLStreamReader xsr = inputFactory.createXMLStreamReader(is);
    try {
      nextTagSkipDTD(xsr);
      readXces(xsr, as);
    }
    finally {
      xsr.close();
    }
  }

  /**
   * A copy of the nextTag algorithm from the XMLStreamReader javadocs,
   * but which also skips over DTD events as well as whitespace,
   * comments and PIs.
   * 
   * @param xsr the reader to advance
   * @return {@link XMLStreamConstants#START_ELEMENT} or
   *         {@link XMLStreamConstants#END_ELEMENT} for the next tag.
   * @throws XMLStreamException
   */
  private static int nextTagSkipDTD(XMLStreamReader xsr)
          throws XMLStreamException {
    int eventType = xsr.next();
    while((eventType == XMLStreamConstants.CHARACTERS && xsr.isWhiteSpace())
            || (eventType == XMLStreamConstants.CDATA && xsr.isWhiteSpace())
            || eventType == XMLStreamConstants.SPACE
            || eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
            || eventType == XMLStreamConstants.COMMENT
            || eventType == XMLStreamConstants.DTD) {
      eventType = xsr.next();
    }
    if(eventType != XMLStreamConstants.START_ELEMENT
            && eventType != XMLStreamConstants.END_ELEMENT) {
      throw new XMLStreamException("expected start or end tag", xsr
              .getLocation());
    }
    return eventType;
  }

  /**
   * Read XML data in <a href="http://www.xces.org/">XCES</a> format
   * from the given reader and add the corresponding annotations to the
   * given annotation set. The reader must be positioned on the starting
   * <code>cesAna</code> tag and will be left pointing to the
   * corresponding end tag.
   * 
   * @param xsr the XMLStreamReader to read from.
   * @param as the annotation set to read into.
   * @throws XMLStreamException
   */
  public static void readXces(XMLStreamReader xsr, AnnotationSet as)
          throws XMLStreamException {
    xsr.require(XMLStreamConstants.START_ELEMENT, XCES_NAMESPACE, "cesAna");

    // Set of all annotation IDs in this set.
    Set<Integer> allAnnotIds = new TreeSet<Integer>();
    // pre-populate with the IDs of any existing annotations in the set
    for(Annotation a : as) {
      allAnnotIds.add(a.getId());
    }

    // lists to collect the annotations in before adding them to the
    // set. We collect the annotations that specify and ID (via
    // struct/@n) in one list and those that don't in another, so we can
    // add the identified ones first, then the others will take the next
    // available ID
    List<AnnotationObject> collectedIdentifiedAnnots = new ArrayList<AnnotationObject>();
    List<AnnotationObject> collectedNonIdentifiedAnnots = new ArrayList<AnnotationObject>();
    while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
      xsr.require(XMLStreamConstants.START_ELEMENT, XCES_NAMESPACE, "struct");
      AnnotationObject annObj = new AnnotationObject();
      annObj.setElemName(xsr.getAttributeValue(null, "type"));
      try {
        annObj.setStart(Long.valueOf(xsr.getAttributeValue(null, "from")));
      }
      catch(NumberFormatException nfe) {
        throw new XMLStreamException(
                "Non-integer value found for struct/@from", xsr.getLocation());
      }

      try{
        annObj.setEnd(Long.valueOf(xsr.getAttributeValue(null, "to")));
      }
      catch(NumberFormatException nfe) {
        throw new XMLStreamException("Non-integer value found for struct/@to",
                xsr.getLocation());
      }

      String annotIdString = xsr.getAttributeValue(null, "n");
      if(annotIdString != null) {
        try {
          Integer annotationId = Integer.valueOf(annotIdString);
          if(allAnnotIds.contains(annotationId)) {
            throw new XMLStreamException("Annotation IDs must be unique "
                    + "within an annotation set. Found duplicate ID", xsr
                    .getLocation());
          }
          allAnnotIds.add(annotationId);
          annObj.setId(annotationId);
        }
        catch(NumberFormatException nfe) {
          throw new XMLStreamException("Non-integer annotation ID found", xsr
                  .getLocation());
        }
      }

      // get the features of this annotation
      annObj.setFM(readXcesFeatureMap(xsr));
      // readFeatureMap leaves xsr on the </Annotation> tag
      if(annObj.getId() != null) {
        collectedIdentifiedAnnots.add(annObj);
      }
      else {
        collectedNonIdentifiedAnnots.add(annObj);
      }
    }

    // finished reading, add the annotations to the set
    AnnotationObject a = null;
    try {
      // first the ones that specify an ID
      Iterator<AnnotationObject> it = collectedIdentifiedAnnots.iterator();
      while(it.hasNext()) {
        a = it.next();
        as.add(a.getId(), a.getStart(), a.getEnd(), a.getElemName(), a.getFM());
      }
      // next the ones that don't
      it = collectedNonIdentifiedAnnots.iterator();
      while(it.hasNext()) {
        a = it.next();
        as.add(a.getStart(), a.getEnd(), a.getElemName(), a.getFM());
      }
    }
    catch(InvalidOffsetException ioe) {
      throw new XMLStreamException("Invalid offset when creating annotation "
              + a, ioe);
    }
  }

  /**
   * Processes a struct element to build a feature map. The element is
   * expected to contain feat children, each with name and value
   * attributes. The reader will be returned positioned on the closing
   * struct tag.
   * 
   * @throws XMLStreamException
   */
  public static FeatureMap readXcesFeatureMap(XMLStreamReader xsr)
          throws XMLStreamException {
    FeatureMap fm = Factory.newFeatureMap();
    while(xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
      xsr.require(XMLStreamConstants.START_ELEMENT, XCES_NAMESPACE, "feat");
      String featureName = xsr.getAttributeValue(null, "name");
      Object featureValue = xsr.getAttributeValue(null, "value");

      fm.put(featureName, featureValue);
      // read the (possibly virtual) closing tag of the feat element
      xsr.nextTag();
      xsr.require(XMLStreamConstants.END_ELEMENT, XCES_NAMESPACE, "feat");
    }
    return fm;
  }

  // ////////// Writing methods ////////////

  private static XMLOutputFactory outputFactory = null;

  /**
   * Returns a string containing the specified document in GATE XML
   * format.
   * 
   * @param doc the document
   */
  public static String toXml(Document doc) {
    try {
      if(outputFactory == null) {
        outputFactory = XMLOutputFactory.newInstance();
      }
      StringWriter sw = new StringWriter(doc.getContent().size().intValue()
              * DocumentXmlUtils.DOC_SIZE_MULTIPLICATION_FACTOR);
      XMLStreamWriter xsw = outputFactory.createXMLStreamWriter(sw);

      // start the document
      if(doc instanceof TextualDocument) {
        xsw.writeStartDocument(((TextualDocument)doc).getEncoding(), "1.0");
      }
      else {
        xsw.writeStartDocument("1.0");
      }
      newLine(xsw);
      writeDocument(doc, xsw, "");
      xsw.close();

      return sw.toString();
    }
    catch(XMLStreamException xse) {
      throw new GateRuntimeException("Error converting document to XML", xse);
    }
  }

  /**
   * Write the specified GATE document to a File.
   * 
   * @param doc the document to write
   * @param file the file to write it to
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void writeDocument(Document doc, File file)
          throws XMLStreamException, IOException {
    writeDocument(doc, file, "");
  }

  /**
   * Write the specified GATE document to a File, optionally putting the
   * XML in a namespace.
   * 
   * @param doc the document to write
   * @param file the file to write it to
   * @param namespaceURI the namespace URI to use for the XML elements.
   *          Must not be null, but can be the empty string if no
   *          namespace is desired.
   * @throws XMLStreamException
   * @throws IOException
   */
  public static void writeDocument(Document doc, File file, String namespaceURI)
          throws XMLStreamException, IOException {

    OutputStream outputStream = new FileOutputStream(file);
    try {
      writeDocument(doc,outputStream,namespaceURI);
    }
    finally {
      outputStream.close();
    }
  }
  
  public static void writeDocument(Document doc, OutputStream outputStream, String namespaceURI) throws XMLStreamException, IOException {
    if(outputFactory == null) {
      outputFactory = XMLOutputFactory.newInstance();
    }

    XMLStreamWriter xsw = null;
    try {
      if(doc instanceof TextualDocument) {
        xsw = outputFactory.createXMLStreamWriter(outputStream,
                ((TextualDocument)doc).getEncoding());
        xsw.writeStartDocument(((TextualDocument)doc).getEncoding(), "1.0");
      }
      else {
        xsw = outputFactory.createXMLStreamWriter(outputStream);
        xsw.writeStartDocument("1.0");
      }
      newLine(xsw);

      writeDocument(doc, xsw, namespaceURI);
    }
    finally {
      if(xsw != null) {
        xsw.close();
      }
    }
  }

  /**
   * Write the specified GATE Document to an XMLStreamWriter. This
   * method writes just the GateDocument element - the XML declaration
   * must be filled in by the caller if required.
   * 
   * @param doc the Document to write
   * @param annotationSets the annotations to include. If the map
   *          contains an entry for the key <code>null</code>, this
   *          will be treated as the default set. All other entries are
   *          treated as named annotation sets.
   * @param xsw the StAX XMLStreamWriter to use for output
   * @throws XMLStreamException if an error occurs during writing
   */
  public static void writeDocument(Document doc,
          Map<String, Collection<Annotation>> annotationSets,
          XMLStreamWriter xsw, String namespaceURI) throws XMLStreamException {
    xsw.setDefaultNamespace(namespaceURI);
    xsw.writeStartElement(namespaceURI, "GateDocument");
    xsw.writeAttribute("version", GATE_XML_VERSION);
    if(namespaceURI.length() > 0) {
      xsw.writeDefaultNamespace(namespaceURI);
    }
    newLine(xsw);
    // features
    xsw.writeComment(" The document's features");
    newLine(xsw);
    newLine(xsw);
    xsw.writeStartElement(namespaceURI, "GateDocumentFeatures");
    newLine(xsw);
    writeFeatures(doc.getFeatures(), xsw, namespaceURI);
    xsw.writeEndElement(); // GateDocumentFeatures
    newLine(xsw);
    // text with nodes
    xsw.writeComment(" The document content area with serialized nodes ");
    newLine(xsw);
    newLine(xsw);
    writeTextWithNodes(doc, annotationSets.values(), xsw, namespaceURI);
    newLine(xsw);
    // Serialize as XML all document's annotation sets
    // Serialize the default AnnotationSet
    StatusListener sListener = (StatusListener)gate.Gate
            .getListeners().get("gate.event.StatusListener");
    if(annotationSets.containsKey(null)) {
      if(sListener != null)
        sListener.statusChanged("Saving the default annotation set ");
      xsw.writeComment(" The default annotation set ");
      newLine(xsw);
      newLine(xsw);
      writeAnnotationSet(annotationSets.get(null), null, xsw, namespaceURI);
      newLine(xsw);
    }

    // Serialize all others AnnotationSets
    // namedAnnotSets is a Map containing all other named Annotation
    // Sets.
    //Iterator<String> iter = annotationSets.keySet().iterator();
    //while(iter.hasNext()) {
    for (Map.Entry<String,Collection<Annotation>> entry : annotationSets.entrySet()) {
      String annotationSetName = entry.getKey();//iter.next();
      // ignore the null entry, if present - we've already handled that
      // above
      if(annotationSetName != null) {
        Collection<Annotation> annots = entry.getValue();//annotationSets.get(annotationSetName);
        xsw.writeComment(" Named annotation set ");
        newLine(xsw);
        newLine(xsw);
        // Serialize it as XML
        if(sListener != null)
          sListener.statusChanged("Saving " + annotationSetName
                  + " annotation set ");
        writeAnnotationSet(annots, annotationSetName, xsw, namespaceURI);
        newLine(xsw);
      }// End if
    }// End while
    
    Iterator<String> iter = annotationSets.keySet().iterator();
    while(iter.hasNext()) {
      
      writeRelationSet(doc.getAnnotations(iter.next()).getRelations(), xsw,
              namespaceURI);
    }

    // close the GateDocument element
    xsw.writeEndElement();
    newLine(xsw);
  }

  /**
   * Write the specified GATE Document to an XMLStreamWriter. This
   * method writes just the GateDocument element - the XML declaration
   * must be filled in by the caller if required. This method writes all
   * the annotations in all the annotation sets on the document. To
   * write just specific annotations, use
   * {@link #writeDocument(Document, Map, XMLStreamWriter, String)}.
   */
  public static void writeDocument(Document doc, XMLStreamWriter xsw,
          String namespaceURI) throws XMLStreamException {
    Map<String, Collection<Annotation>> asMap = new HashMap<String, Collection<Annotation>>();
    asMap.put(null, doc.getAnnotations());
    if(doc.getNamedAnnotationSets() != null) {
      asMap.putAll(doc.getNamedAnnotationSets());
    }
    writeDocument(doc, asMap, xsw, namespaceURI);
  }

  /**
   * Writes the given annotation set to an XMLStreamWriter as GATE XML
   * format. The Name attribute of the generated AnnotationSet element
   * is set to the default value, i.e. <code>annotations.getName</code>.
   * 
   * @param annotations the annotation set to write
   * @param xsw the writer to use for output
   * @param namespaceURI
   * @throws XMLStreamException
   */
  public static void writeAnnotationSet(AnnotationSet annotations,
          XMLStreamWriter xsw, String namespaceURI) throws XMLStreamException {
    writeAnnotationSet((Collection<Annotation>)annotations, annotations.getName(), xsw,
            namespaceURI);
  }

  /**
   * Writes the given annotation set to an XMLStreamWriter as GATE XML
   * format. The value for the Name attribute of the generated
   * AnnotationSet element is given by <code>asName</code>.
   * 
   * @param annotations the annotation set to write
   * @param asName the name under which to write the annotation set.
   *          <code>null</code> means that no name will be used.
   * @param xsw the writer to use for output
   * @param namespaceURI
   * @throws XMLStreamException
   */
  public static void writeAnnotationSet(Collection<Annotation> annotations,
          String asName, XMLStreamWriter xsw, String namespaceURI)
          throws XMLStreamException {
    xsw.writeStartElement(namespaceURI, "AnnotationSet");
    if(asName != null) {
      xsw.writeAttribute("Name", asName);
    }
    newLine(xsw);

    if(annotations != null) {
      Iterator<Annotation> iterator = annotations.iterator();
      while(iterator.hasNext()) {
        Annotation annot = iterator.next();
        xsw.writeStartElement(namespaceURI, "Annotation");
        xsw.writeAttribute("Id", String.valueOf(annot.getId()));
        xsw.writeAttribute("Type", annot.getType());
        xsw.writeAttribute("StartNode", String.valueOf(annot.getStartNode()
                .getOffset()));
        xsw.writeAttribute("EndNode", String.valueOf(annot.getEndNode()
                .getOffset()));
        newLine(xsw);
        writeFeatures(annot.getFeatures(), xsw, namespaceURI);
        xsw.writeEndElement();
        newLine(xsw);
      }
    }
    // end AnnotationSet element
    xsw.writeEndElement();
    newLine(xsw);
  }
  
  public static void writeRelationSet(RelationSet relations,
          XMLStreamWriter xsw, String namespaceURI) throws XMLStreamException {

    // if there are no relations then don't write the set, this means
    // that docs without relations will remain compatible with earlier
    // versions of GATE
    if(relations == null || relations.size() == 0) return;

    xsw.writeComment(" Relation Set for "
            + relations.getAnnotationSet().getName() + " ");
    newLine(xsw);
    newLine(xsw);

    xsw.writeStartElement(namespaceURI, "RelationSet");

    if(relations.getAnnotationSet().getName() != null) {
      xsw.writeAttribute("Name", relations.getAnnotationSet().getName());
    }
    newLine(xsw);

    for(Relation relation : relations.get()) {

      StringBuilder str = new StringBuilder();
      int[] members = relation.getMembers();
      for(int i = 0; i < members.length; i++) {
        if(i > 0) str.append(";");
        str.append(members[i]);
      }
      xsw.writeStartElement(namespaceURI, "Relation");
      xsw.writeAttribute("Id", String.valueOf(relation.getId()));
      xsw.writeAttribute("Type", relation.getType());
      xsw.writeAttribute("Members", str.toString());
      newLine(xsw);

      xsw.writeStartElement(namespaceURI, "UserData");
      if(relation.getUserData() != null) {
        ObjectWrapper userData = new ObjectWrapper(relation.getUserData());
        writeCharactersOrCDATA(xsw,
                replaceXMLIllegalCharactersInString(userData.toString()));
      }
      xsw.writeEndElement();
      newLine(xsw);

      writeFeatures(relation.getFeatures(), xsw, namespaceURI);
      xsw.writeEndElement();
      newLine(xsw);
    }

    // end RelationSet element
    xsw.writeEndElement();
    newLine(xsw);
  }

  /**
   * Retained for binary compatibility, new code should call the
   * <code>Collection&lt;Annotation&gt;</code> version instead.
   */
  public static void writeAnnotationSet(AnnotationSet annotations,
          String asName, XMLStreamWriter xsw, String namespaceURI)
          throws XMLStreamException {
    writeAnnotationSet((Collection<Annotation>)annotations, asName, xsw, namespaceURI);
  }

  /**
   * Writes the content of the given document to an XMLStreamWriter as a
   * mixed content element called "TextWithNodes". At each point where
   * there is the start or end of an annotation in any annotation set on
   * the document, a "Node" element is written with an "id" feature
   * whose value is the offset of that node.
   * 
   * @param doc the document whose content is to be written
   * @param annotationSets the annotations for which nodes are required.
   *          This is a collection of collections.
   * @param xsw the {@link XMLStreamWriter} to write to.
   * @param namespaceURI the namespace URI. May be empty but may not be
   *          null.
   * @throws XMLStreamException
   */
  public static void writeTextWithNodes(Document doc,
          Collection<Collection<Annotation>> annotationSets,
          XMLStreamWriter xsw, String namespaceURI) throws XMLStreamException {
    String aText = doc.getContent().toString();
    // no text, so return an empty element
    if(aText == null) {
      xsw.writeEmptyElement(namespaceURI, "TextWithNodes");
      return;
    }

    // build a set of all the offsets where Nodes are required
    TreeSet<Long> offsetsSet = new TreeSet<Long>();
    if(annotationSets != null) {
      for(Collection<Annotation> set : annotationSets) {
        if(set != null) {
          for(Annotation annot : set) {
            offsetsSet.add(annot.getStartNode().getOffset());
            offsetsSet.add(annot.getEndNode().getOffset());
          }
        }
      }
    }

    // write the TextWithNodes element
    char[] textArray = aText.toCharArray();
    xsw.writeStartElement(namespaceURI, "TextWithNodes");
    int lastNodeOffset = 0;
    // offsetsSet iterator is in ascending order of offset, as it is a
    // SortedSet
    Iterator<Long> offsetsIterator = offsetsSet.iterator();
    while(offsetsIterator.hasNext()) {
      int offset = offsetsIterator.next().intValue();
      // write characters since the last node output
      // replace XML-illegal characters in this slice of text - we
      // have to do this here rather than on the text as a whole in
      // case the node falls between the two halves of a surrogate
      // pair (in which case both halves are illegal and must be
      // replaced).
      replaceXMLIllegalCharacters(textArray, lastNodeOffset, offset - lastNodeOffset);
      writeCharactersOrCDATA(xsw, new String(textArray, lastNodeOffset, offset
              - lastNodeOffset));
      xsw.writeEmptyElement(namespaceURI, "Node");
      xsw.writeAttribute("id", String.valueOf(offset));
      lastNodeOffset = offset;
    }
    // write any remaining text after the last node
    replaceXMLIllegalCharacters(textArray, lastNodeOffset, textArray.length - lastNodeOffset);
    writeCharactersOrCDATA(xsw, new String(textArray, lastNodeOffset,
            textArray.length - lastNodeOffset));
    // and the closing TextWithNodes
    xsw.writeEndElement();
  }

  /**
   * Write a TextWithNodes section containing nodes for all annotations
   * in the given document.
   * 
   * @see #writeTextWithNodes(Document, Collection, XMLStreamWriter,
   *      String)
   */
  public static void writeTextWithNodes(Document doc, XMLStreamWriter xsw,
          String namespaceURI) throws XMLStreamException {
    Collection<Collection<Annotation>> annotationSets = new ArrayList<Collection<Annotation>>();
    annotationSets.add(doc.getAnnotations());
    if(doc.getNamedAnnotationSets() != null) {
      annotationSets.addAll(doc.getNamedAnnotationSets().values());
    }
    writeTextWithNodes(doc, annotationSets, xsw, namespaceURI);
  }

  /**
   * Replace any characters in the given buffer that are illegal in XML
   * with spaces. Characters that are illegal in XML are:
   * <ul>
   * <li>Control characters U+0000 to U+001F, <i>except</i> U+0009,
   * U+000A and U+000D, which are permitted.</li>
   * <li><i>Unpaired</i> surrogates U+D800 to U+D8FF (valid surrogate
   * pairs are OK).</li>
   * <li>U+FFFE and U+FFFF (only allowed as part of the Unicode byte
   * order mark).</li>
   * </ul>
   * 
   * @param buf the buffer to process
   */
  static void replaceXMLIllegalCharacters(char[] buf) {
    replaceXMLIllegalCharacters(buf, 0, buf.length);
  }

  /**
   * Replace any characters in the given buffer that are illegal in XML
   * with spaces. Characters that are illegal in XML are:
   * <ul>
   * <li>Control characters U+0000 to U+001F, <i>except</i> U+0009,
   * U+000A and U+000D, which are permitted.</li>
   * <li><i>Unpaired</i> surrogates U+D800 to U+D8FF (valid surrogate
   * pairs are OK).</li>
   * <li>U+FFFE and U+FFFF (only allowed as part of the Unicode byte
   * order mark).</li>
   * </ul>
   * 
   * @param buf the buffer to process
   */
  static void replaceXMLIllegalCharacters(char[] buf, int start, int len) {
    ArrayCharSequence bufSequence = new ArrayCharSequence(buf, start, len);
    for(int i = 0; i < len; i++) {
      if(isInvalidXmlChar(bufSequence, i)) {
        buf[start + i] = INVALID_CHARACTER_REPLACEMENT;
      }
    }
  }

  /**
   * Return a string containing the same characters as the supplied
   * string, except that any characters that are illegal in XML will be
   * replaced with spaces. Characters that are illegal in XML are:
   * <ul>
   * <li>Control characters U+0000 to U+001F, <i>except</i> U+0009,
   * U+000A and U+000D, which are permitted.</li>
   * <li><i>Unpaired</i> surrogates U+D800 to U+D8FF (valid surrogate
   * pairs are OK).</li>
   * <li>U+FFFE and U+FFFF (only allowed as part of the Unicode byte
   * order mark).</li>
   * </ul>
   * 
   * A new string is only created if required - if the supplied string
   * contains no illegal characters then the same object is returned.
   * 
   * @param str the string to process
   * @return <code>str</code>, unless it contains illegal characters
   *         in which case a new string the same as str but with the
   *         illegal characters replaced by spaces.
   */
  static String replaceXMLIllegalCharactersInString(String str) {
    StringBuilder builder = null;
    for(int i = 0; i < str.length(); i++) {
      if(isInvalidXmlChar(str, i)) {
        // lazily create the StringBuilder
        if(builder == null) {
          builder = new StringBuilder(str.substring(0, i));
        }
        builder.append(INVALID_CHARACTER_REPLACEMENT);
      }
      else if(builder != null) {
        builder.append(str.charAt(i));
      }
    }

    if(builder == null) {
      // no illegal characters were found
      return str;
    }
    else {
      return builder.toString();
    }
  }

  /**
   * Check whether a character is illegal in XML.
   * 
   * @param buf the character sequence in which to look (must not be
   *          null)
   * @param i the index of the character to check (must be within the
   *          valid range of characters in <code>buf</code>)
   */
  static final boolean isInvalidXmlChar(CharSequence buf, int i) {
    // illegal control character
    if(buf.charAt(i) <= 0x0008 || buf.charAt(i) == 0x000B
            || buf.charAt(i) == 0x000C
            || (buf.charAt(i) >= 0x000E && buf.charAt(i) <= 0x001F)) {
      return true;
    }

    // buf.charAt(i) is a high surrogate...
    if(buf.charAt(i) >= 0xD800 && buf.charAt(i) <= 0xDBFF) {
      // if we're not at the end of the buffer we can look ahead
      if(i < buf.length() - 1) {
        // followed by a low surrogate is OK
        if(buf.charAt(i + 1) >= 0xDC00 && buf.charAt(i + 1) <= 0xDFFF) {
          return false;
        }
      }

      // at the end of the buffer, or not followed by a low surrogate is
      // not OK.
      return true;
    }

    // buf.charAt(i) is a low surrogate...
    if(buf.charAt(i) >= 0xDC00 && buf.charAt(i) <= 0xDFFF) {
      // if we're not at the start of the buffer we can look behind
      if(i > 0) {
        // preceded by a high surrogate is OK
        if(buf.charAt(i - 1) >= 0xD800 && buf.charAt(i - 1) <= 0xDBFF) {
          return false;
        }
      }

      // at the start of the buffer, or not preceded by a high surrogate
      // is not OK
      return true;
    }

    // buf.charAt(i) is a BOM character
    if(buf.charAt(i) == 0xFFFE || buf.charAt(i) == 0xFFFF) {
      return true;
    }

    // anything else is OK
    return false;
  }

  /**
   * Write a feature map to the given XMLStreamWriter. The map is output
   * as a sequence of "Feature" elements, each having "Name" and "Value"
   * children. Note that there is no enclosing element - the caller must
   * write the enclosing "GateDocumentFeatures" or "Annotation" element.
   * Characters in feature values that are illegal in XML are replaced
   * by {@link #INVALID_CHARACTER_REPLACEMENT} (a space). Feature
   * <i>names</i> are not modified - an illegal character in a feature
   * name will cause the serialization to fail.
   * 
   * @param features
   * @param xsw
   * @param namespaceURI
   * @throws XMLStreamException
   */
  public static void writeFeatures(FeatureMap features, XMLStreamWriter xsw,
          String namespaceURI) throws XMLStreamException {
    if(features == null) {
      return;
    }

    Set<Object> keySet = features.keySet();
    Iterator<Object> keySetIterator = keySet.iterator();
    //FEATURES:
    while(keySetIterator.hasNext()) {
      Object key = keySetIterator.next();
      Object value = features.get(key);
      if(key != null && value != null) {
        String keyClassName = null;
        //String keyItemClassName = null;
        String valueClassName = null;
        //String valueItemClassName = null;
        String key2String = key.toString();
        String value2String = value.toString();
        //Object item = null;
        // Test key if it is String, Number
        if(key instanceof java.lang.String || 
           key instanceof java.lang.Number) {
          keyClassName = key.getClass().getName();
        } else {
          keyClassName = ObjectWrapper.class.getName();
          key2String = new ObjectWrapper(key).toString();
        }
          
        // Test value if it is String, Number
        if(value instanceof java.lang.String
                || value instanceof java.lang.Number
                || value instanceof java.lang.Boolean){
          valueClassName = value.getClass().getName();
        } else {
          valueClassName = ObjectWrapper.class.getName();
          value2String = new ObjectWrapper(value).toString();
        }
          
        // Features and values that are not Strings, Numbers, Booleans or
        // collections
        // will be discarded.
        //if(keyClassName == null || valueClassName == null) continue;

        xsw.writeStartElement(namespaceURI, "Feature");
        xsw.writeCharacters("\n  ");

        // write the Name
        xsw.writeStartElement(namespaceURI, "Name");
        if(keyClassName != null) {
          xsw.writeAttribute("className", keyClassName);
        }
        //if(keyItemClassName != null) {
        //  xsw.writeAttribute("itemClassName", keyItemClassName);
        //}
        xsw.writeCharacters(key2String);
        xsw.writeEndElement();
        xsw.writeCharacters("\n  ");

        // write the Value
        xsw.writeStartElement(namespaceURI, "Value");
        if(valueClassName != null) {
          xsw.writeAttribute("className", valueClassName);
        }
        //if(valueItemClassName != null) {
        //  xsw.writeAttribute("itemClassName", valueItemClassName);
        //}
        writeCharactersOrCDATA(xsw,
                replaceXMLIllegalCharactersInString(value2String));
        xsw.writeEndElement();
        newLine(xsw);

        // close the Feature element
        xsw.writeEndElement();
        newLine(xsw);
      }
    }
  }

  /**
   * Convenience method to write a single new line to the given writer.
   * 
   * @param xsw the XMLStreamWriter to write to.
   * @throws XMLStreamException
   */
  static void newLine(XMLStreamWriter xsw) throws XMLStreamException {
    xsw.writeCharacters("\n");
  }

  /**
   * The regular expression pattern that will match the end of a CDATA
   * section.
   */
  private static Pattern CDATA_END_PATTERN = Pattern.compile("\\]\\]>");

  /**
   * Write the given string to the given writer, using either
   * writeCharacters or, if there are more than a few less than signs in
   * the string (e.g. if it is an XML fragment itself), write it with
   * writeCData. This method properly handles the case where the string
   * contains other CDATA sections - as a CDATA section cannot contain
   * the CDATA end marker <code>]]></code>, we split the output CDATA
   * at any occurrences of this marker and write the marker using a
   * normal writeCharacters call in between.
   * 
   * @param xsw the writer to write to
   * @param string the string to write
   * @throws XMLStreamException
   */
  static void writeCharactersOrCDATA(XMLStreamWriter xsw, String string)
          throws XMLStreamException {
    if(containsEnoughLTs(string)) {
      Matcher m = CDATA_END_PATTERN.matcher(string);
      int startFrom = 0;
      while(m.find()) {
        // we found a CDATA end marker, so write everything up to the
        // marker as CDATA...
        xsw.writeCData(string.substring(startFrom, m.start()));
        // then write the marker as characters
        xsw.writeCharacters("]]>");
        startFrom = m.end();
      }

      if(startFrom == 0) {
        // no "]]>" in the string, the normal case
        xsw.writeCData(string);
      }
      else if(startFrom < string.length()) {
        // there is some trailing text after the last ]]>
        xsw.writeCData(string.substring(startFrom));
      }
      // else the last ]]> was the end of the string, so nothing more to
      // do.
    }
    else {
      // if fewer '<' characters, just writeCharacters as normal
      xsw.writeCharacters(string);
    }
  }

  /**
   * Checks whether the given string contains at least
   * <code>LT_THRESHOLD</code> &lt; characters.
   */
  private static boolean containsEnoughLTs(String string) {
    int numLTs = 0;
    int index = -1;
    while((index = string.indexOf('<', index + 1)) >= 0) {
      numLTs++;
      if(numLTs >= LT_THRESHOLD) {
        return true;
      }
    }

    return false;
  }

  // ///// Writing XCES /////

  /**
   * Comparator that compares annotations based on their offsets; when
   * two annotations start at the same location, the longer one is
   * considered to come first in the ordering.
   */
  public static final Comparator<Annotation> LONGEST_FIRST_OFFSET_COMPARATOR = new Comparator<Annotation>() {
    @Override
    public int compare(Annotation left, Annotation right) {
      long loffset = left.getStartNode().getOffset().longValue();
      long roffset = right.getStartNode().getOffset().longValue();
      if(loffset == roffset) {
        // if the start offsets are the same compare end
        // offsets.
        // the largest offset should come first
        loffset = left.getEndNode().getOffset().longValue();
        roffset = right.getEndNode().getOffset().longValue();
        if(loffset == roffset) {
          return left.getId() - right.getId();
        }
        else {
          return (int)(roffset - loffset);
        }
      }
      return (int)(loffset - roffset);
    }
  };

  /**
   * Save the content of a document to the given output stream. Since
   * XCES content files are plain text (not XML), XML-illegal characters
   * are not replaced when writing. The stream is <i>not</i> closed by
   * this method, that is left to the caller.
   * 
   * @param doc the document to save
   * @param out the stream to write to
   * @param encoding the character encoding to use. If null, defaults to
   *          UTF-8
   */
  public static void writeXcesContent(Document doc, OutputStream out,
          String encoding) throws IOException {
    if(encoding == null) {
      encoding = "UTF-8";
    }

    String documentContent = doc.getContent().toString();

    OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
    BufferedWriter writer = new BufferedWriter(osw);
    writer.write(documentContent);
    writer.flush();
    // do not close the writer, this would close the underlying stream,
    // which is something we want to leave to the caller
  }

  /**
   * Save annotations to the given output stream in XCES format, with
   * their IDs included as the "n" attribute of each <code>struct</code>.
   * The stream is <i>not</i> closed by this method, that is left to
   * the caller.
   * 
   * @param annotations the annotations to save, typically an
   *          AnnotationSet
   * @param os the output stream to write to
   * @param encoding the character encoding to use.
   */
  public static void writeXcesAnnotations(Collection<Annotation> annotations,
          OutputStream os, String encoding) throws XMLStreamException {
    XMLStreamWriter xsw = null;
    try {
      if(outputFactory == null) {
        outputFactory = XMLOutputFactory.newInstance();
      }      
      if(encoding == null) {
        xsw = outputFactory.createXMLStreamWriter(os);
        xsw.writeStartDocument();
      }
      else {
        xsw = outputFactory.createXMLStreamWriter(os, encoding);
        xsw.writeStartDocument(encoding, "1.0");
      }
      newLine(xsw);
      writeXcesAnnotations(annotations, xsw);
    }
    finally {
      if(xsw != null) {
        xsw.close();
      }
    }
  }

  /**
   * Save annotations to the given XMLStreamWriter in XCES format, with
   * their IDs included as the "n" attribute of each <code>struct</code>.
   * The writer is <i>not</i> closed by this method, that is left to
   * the caller. This method writes just the cesAna element - the XML
   * declaration must be filled in by the caller if required.
   * 
   * @param annotations the annotations to save, typically an
   *          AnnotationSet
   * @param xsw the XMLStreamWriter to write to
   */
  public static void writeXcesAnnotations(Collection<Annotation> annotations,
          XMLStreamWriter xsw) throws XMLStreamException {
    writeXcesAnnotations(annotations, xsw, true);
  }

  /**
   * Save annotations to the given XMLStreamWriter in XCES format. The
   * writer is <i>not</i> closed by this method, that is left to the
   * caller. This method writes just the cesAna element - the XML
   * declaration must be filled in by the caller if required. Characters
   * in feature values that are illegal in XML are replaced by
   * {@link #INVALID_CHARACTER_REPLACEMENT} (a space). Feature <i>names</i>
   * are not modified, nor are annotation types - an illegal character
   * in one of these will cause the serialization to fail.
   * 
   * @param annotations the annotations to save, typically an
   *          AnnotationSet
   * @param xsw the XMLStreamWriter to write to
   * @param includeId should we include the annotation IDs (as the "n"
   *          attribute on each <code>struct</code>)?
   * @throws XMLStreamException
   */
  public static void writeXcesAnnotations(Collection<Annotation> annotations,
          XMLStreamWriter xsw, boolean includeId) throws XMLStreamException {
    List<Annotation> annotsToDump = new ArrayList<Annotation>(annotations);
    Collections.sort(annotsToDump, LONGEST_FIRST_OFFSET_COMPARATOR);

    xsw.setDefaultNamespace(XCES_NAMESPACE);
    xsw.writeStartElement(XCES_NAMESPACE, "cesAna");
    xsw.writeDefaultNamespace(XCES_NAMESPACE);
    xsw.writeAttribute("version", XCES_VERSION);
    newLine(xsw);

    String indent = "   ";
    String indentMore = indent + indent;

    for(Annotation a : annotsToDump) {
      long start = a.getStartNode().getOffset().longValue();
      long end = a.getEndNode().getOffset().longValue();
      FeatureMap fm = a.getFeatures();
      xsw.writeCharacters(indent);
      if(fm == null || fm.size() == 0) {
        xsw.writeEmptyElement(XCES_NAMESPACE, "struct");
      }
      else {
        xsw.writeStartElement(XCES_NAMESPACE, "struct");
      }
      xsw.writeAttribute("type", a.getType());
      xsw.writeAttribute("from", String.valueOf(start));
      xsw.writeAttribute("to", String.valueOf(end));
      // include the annotation ID as the "n" attribute if requested
      if(includeId) {
        xsw.writeAttribute("n", String.valueOf(a.getId()));
      }
      newLine(xsw);

      if(fm != null && fm.size() != 0) {
        for(Map.Entry<Object,Object> att : fm.entrySet()) {
          if(!"isEmptyAndSpan".equals(att.getKey())) {
            xsw.writeCharacters(indentMore);
            xsw.writeEmptyElement(XCES_NAMESPACE, "feat");
            xsw.writeAttribute("name", String.valueOf(att.getKey()));
            xsw.writeAttribute("value",
                    replaceXMLIllegalCharactersInString(String.valueOf(att
                            .getValue())));
            newLine(xsw);
          }
        }
        xsw.writeCharacters(indent);
        xsw.writeEndElement();
        newLine(xsw);
      }
    }

    xsw.writeEndElement();
    newLine(xsw);
  }

  /** An inner class modeling the information contained by an annotation. */
  static class AnnotationObject {
    /** Constructor */
    public AnnotationObject() {
    }// AnnotationObject

    /** Accesor for the annotation type modeled here as ElemName */
    public String getElemName() {
      return elemName;
    }// getElemName

    /** Accesor for the feature map */
    public FeatureMap getFM() {
      return fm;
    }// getFM()

    /** Accesor for the start ofset */
    public Long getStart() {
      return start;
    }// getStart()

    /** Accesor for the end offset */
    public Long getEnd() {
      return end;
    }// getEnd()

    /** Mutator for the annotation type */
    public void setElemName(String anElemName) {
      elemName = anElemName;
    }// setElemName();

    /** Mutator for the feature map */
    public void setFM(FeatureMap aFm) {
      fm = aFm;
    }// setFM();

    /** Mutator for the start offset */
    public void setStart(Long aStart) {
      start = aStart;
    }// setStart();

    /** Mutator for the end offset */
    public void setEnd(Long anEnd) {
      end = anEnd;
    }// setEnd();

    /** Accesor for the id */
    public Integer getId() {
      return id;
    }// End of getId()

    /** Mutator for the id */
    public void setId(Integer anId) {
      id = anId;
    }// End of setId()

    @Override
    public String toString() {
      return " [id =" + id + " type=" + elemName + " startNode=" + start
              + " endNode=" + end + " features=" + fm + "] ";
    }

    // Data fields
    private String elemName = null;

    private FeatureMap fm = null;

    private Long start = null;

    private Long end = null;

    private Integer id = null;
  } // AnnotationObject

  /**
   * Thin wrapper class to use a char[] as a CharSequence. The array is
   * not copied - changes to the array are reflected by the CharSequence
   * methods.
   */
  static class ArrayCharSequence implements CharSequence {
    char[] array;
    int offset;
    int len;

    ArrayCharSequence(char[] array) {
      this(array, 0, array.length);
    }

    ArrayCharSequence(char[] array, int offset, int len) {
      this.array = array;
      this.offset = offset;
      this.len = len;
    }

    @Override
    public final char charAt(int i) {
      return array[offset + i];
    }

    @Override
    public final int length() {
      return len;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      return new ArrayCharSequence(array, offset + start, offset + end);
    }

    @Override
    public String toString() {
      return String.valueOf(array, offset, len);
    }
  } // ArrayCharSequence
}
