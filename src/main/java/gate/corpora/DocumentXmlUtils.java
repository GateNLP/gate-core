/*
 *  DocumentXmlUtils.java
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
 *  $Id: DocumentXmlUtils.java 17580 2014-03-07 18:58:06Z markagreenwood $
 */
package gate.corpora;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.TextualDocument;
import gate.event.StatusListener;
import gate.util.Err;
import gate.util.Strings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class is contains useful static methods for working with the GATE XML
 * format.  Many of the methods in this class were originally in {@link
 * DocumentImpl} but as they are not specific to any one implementation of the
 * <code>Document</code> interface they have been moved here.
 */
public class DocumentXmlUtils {

  /**
   * This field is used when creating StringBuffers for toXml() methods. The
   * size of the StringBuffer will be docDonctent.size() multiplied by this
   * value. It is aimed to improve the performance of StringBuffer
   */
  public static final int DOC_SIZE_MULTIPLICATION_FACTOR = 40;

  /**
   * Returns a GateXml document that is a custom XML format for wich there is a
   * reader inside GATE called gate.xml.GateFormatXmlHandler. What it does is to
   * serialize a GATE document in an XML format.
   * 
   * @param doc the document to serialize.
   * @return a string representing a Gate Xml document.
   */
  public static String toXml(TextualDocument doc) {
    // Initialize the xmlContent several time the size of the current document.
    // This is because of the tags size. This measure is made to increase the
    // performance of StringBuffer.
    StringBuffer xmlContent = new StringBuffer(
            DOC_SIZE_MULTIPLICATION_FACTOR
            * (doc.getContent().size().intValue()));
    // Add xml header
    xmlContent.append("<?xml version=\"1.0\" encoding=\"");
    xmlContent.append(doc.getEncoding());
    xmlContent.append("\" ?>");
    xmlContent.append(Strings.getNl());
    // Add the root element
    xmlContent.append("<GateDocument>\n");
    xmlContent.append("<!-- The document's features-->\n\n");
    xmlContent.append("<GateDocumentFeatures>\n");
    xmlContent.append(featuresToXml(doc.getFeatures(),null));
    xmlContent.append("</GateDocumentFeatures>\n");
    xmlContent.append("<!-- The document content area with serialized"
            + " nodes -->\n\n");
    // Add plain text element
    xmlContent.append("<TextWithNodes>");
    xmlContent.append(textWithNodes(doc, doc.getContent().toString()));
    xmlContent.append("</TextWithNodes>\n");
    // Serialize as XML all document's annotation sets
    // Serialize the default AnnotationSet
    StatusListener sListener = (StatusListener)gate.Gate
            .getListeners().get("gate.event.StatusListener");
    if(sListener != null)
      sListener.statusChanged("Saving the default annotation set ");
    xmlContent.append("<!-- The default annotation set -->\n\n");
    annotationSetToXml(doc.getAnnotations(), xmlContent);
    // Serialize all others AnnotationSets
    // namedAnnotSets is a Map containing all other named Annotation Sets.
    Map<String,AnnotationSet> namedAnnotSets = doc.getNamedAnnotationSets();
    if(namedAnnotSets != null) {
      Iterator<AnnotationSet> iter = namedAnnotSets.values().iterator();
      while(iter.hasNext()) {
        AnnotationSet annotSet = iter.next();
        xmlContent.append("<!-- Named annotation set -->\n\n");
        // Serialize it as XML
        if(sListener != null)
          sListener.statusChanged("Saving " + annotSet.getName()
                  + " annotation set ");
        annotationSetToXml(annotSet, xmlContent);
      }// End while
    }// End if
    // Add the end of GateDocument
    xmlContent.append("</GateDocument>");
    if(sListener != null) sListener.statusChanged("Done !");
    // return the XmlGateDocument
    return xmlContent.toString();
  }


  /**
   * This method saves a FeatureMap as XML elements.
   * 
   * @param aFeatureMap
   *          the feature map that has to be saved as XML.
   * @return a String like this: <Feature><Name>...</Name> <Value>...</Value></Feature><Feature>...</Feature>
   */
  public static StringBuffer featuresToXml(FeatureMap aFeatureMap, Map<String,StringBuffer> normalizedFeatureNames) {
    if(aFeatureMap == null) return new StringBuffer();
    StringBuffer buffer = new StringBuffer(1024);
    Set<Object> keySet = aFeatureMap.keySet();
    Iterator<Object> keyIterator = keySet.iterator();
    while(keyIterator.hasNext()) {
      Object key = keyIterator.next();
      Object value = aFeatureMap.get(key);
      if((key != null) && (value != null)) {
        String keyClassName = null;
        String keyItemClassName = null;
        String valueClassName = null;
        String valueItemClassName = null;
        String key2String = key.toString();
        String value2String = value.toString();
        Object item = null;
        // Test key if it is String, Number or Collection
        if(key instanceof java.lang.String || key instanceof java.lang.Number
                || key instanceof java.util.Collection)
          keyClassName = key.getClass().getName();
        // Test value if it is String, Number or Collection
        if(value instanceof java.lang.String
                || value instanceof java.lang.Number
                || value instanceof java.util.Collection)
          valueClassName = value.getClass().getName();
        // Features and values that are not Strings, Numbers or collections
        // will be discarded.
        if(keyClassName == null || valueClassName == null) continue;
        // If key is collection serialize the collection in a specific format
        if(key instanceof java.util.Collection) {
          StringBuffer keyStrBuff = new StringBuffer();
          Iterator<?> iter = ((Collection<?>)key).iterator();
          if(iter.hasNext()) {
            item = iter.next();
            if(item instanceof java.lang.Number)
              keyItemClassName = item.getClass().getName();
            else keyItemClassName = String.class.getName();
            keyStrBuff.append(item.toString());
          }// End if
          while(iter.hasNext()) {
            item = iter.next();
            keyStrBuff.append(";").append(item.toString());
          }// End while
          key2String = keyStrBuff.toString();
        }// End if
        // If key is collection serialize the colection in a specific format
        if(value instanceof java.util.Collection) {
          StringBuffer valueStrBuff = new StringBuffer();
          Iterator<?> iter = ((Collection<?>)value).iterator();
          if(iter.hasNext()) {
            item = iter.next();
            if(item instanceof java.lang.Number)
              valueItemClassName = item.getClass().getName();
            else valueItemClassName = String.class.getName();
            valueStrBuff.append(item.toString());
          }// End if
          while(iter.hasNext()) {
            item = iter.next();
            valueStrBuff.append(";").append(item.toString());
          }// End while
          value2String = valueStrBuff.toString();
        }// End if
        buffer.append("<Feature>\n  <Name");
        if(keyClassName != null)
          buffer.append(" className=\"").append(keyClassName).append("\"");
        if(keyItemClassName != null)
          buffer.append(" itemClassName=\"").append(keyItemClassName).append(
                  "\"");
        buffer.append(">");
        
        // use a map of keys already checked for XML validity
        StringBuffer normalizedKey = new StringBuffer(key2String);
        if (normalizedFeatureNames!=null){
          // has this key been already converted ?
          normalizedKey = normalizedFeatureNames.get(key2String);
          if (normalizedKey==null){
            // never seen so far!
            normalizedKey= combinedNormalisation(key2String);
            normalizedFeatureNames.put(key2String,normalizedKey);
          }
        }
        else normalizedKey = combinedNormalisation(key2String);
        
        buffer.append(normalizedKey);
        buffer.append("</Name>\n  <Value");
        if(valueClassName != null)
          buffer.append(" className=\"").append(valueClassName).append("\"");
        if(valueItemClassName != null)
          buffer.append(" itemClassName=\"").append(valueItemClassName).append(
                  "\"");
        buffer.append(">");
        buffer.append(combinedNormalisation(value2String));
        buffer.append("</Value>\n</Feature>\n");
      }// End if
    }// end While
    return buffer;
  }// featuresToXml


  /**
   * Combines replaceCharsWithEntities and filterNonXmlChars in a single method
   **/
  public static StringBuffer combinedNormalisation(String inputString){
    if(inputString == null) return new StringBuffer("");
    StringBuffer buffer = new StringBuffer(inputString);
    for (int i=buffer.length()-1; i>=0; i--){
      char currentchar = buffer.charAt(i);
      // is the current character an xml char which needs replacing?
      if(!isXmlChar(currentchar)) buffer.replace(i,i+1," ");
      // is the current character an xml char which needs replacing?
      else if(currentchar == '<' || currentchar == '>' || currentchar == '&'|| currentchar == '\''|| currentchar == '\"' || currentchar == 0xA0 || currentchar == 0xA9)
        buffer.replace(i,i+1,entitiesMap.get(new Character(currentchar)));
      }
    return buffer;
  }

  /**
   * This method filters any non XML char see:
   * http://www.w3c.org/TR/2000/REC-xml-20001006#charsets All non XML chars will
   * be replaced with 0x20 (space char) This assures that the next time the
   * document is loaded there won't be any problems.
   * 
   * @param aStrBuffer
   *          represents the input String that is filtred. If the aStrBuffer is
   *          null then an empty string will be returend
   * @return the "purified" StringBuffer version of the aStrBuffer
   */
  public static StringBuffer filterNonXmlChars(StringBuffer aStrBuffer) {
    if(aStrBuffer == null) return new StringBuffer("");
    // String space = new String(" ");
    char space = ' ';
    for(int i = aStrBuffer.length() - 1; i >= 0; i--) {
      if(!isXmlChar(aStrBuffer.charAt(i))) aStrBuffer.setCharAt(i, space);
    }// End for
    return aStrBuffer;
  }// filterNonXmlChars()

  /**
   * This method decide if a char is a valid XML one or not
   * 
   * @param ch
   *          the char to be tested
   * @return true if is a valid XML char and fals if is not.
   */
  public static boolean isXmlChar(char ch) {
    if(ch == 0x9 || ch == 0xA || ch == 0xD) return true;
    if((0x20 <= ch) && (ch <= 0xD7FF)) return true;
    if((0xE000 <= ch) && (ch <= 0xFFFD)) return true;
    if((0x10000 <= ch) && (ch <= 0x10FFFF)) return true;
    return false;
  }// End isXmlChar()


  /** This method replace all chars that appears in the anInputString and also
    * that are in the entitiesMap with their corresponding entity
    * @param anInputString the string analyzed. If it is null then returns the
    *  empty string
    * @return a string representing the input string with chars replaced with
    *  entities
    */
  public static StringBuffer replaceCharsWithEntities(String anInputString){
    if (anInputString == null) return new StringBuffer("");
    StringBuffer strBuff = new StringBuffer(anInputString);
    for (int i=strBuff.length()-1; i>=0; i--){
      Character ch = new Character(strBuff.charAt(i));
      if (entitiesMap.keySet().contains(ch)){
        strBuff.replace(i,i+1,entitiesMap.get(ch));
      }// End if
    }// End for
    return strBuff;
  }// replaceCharsWithEntities()

  /**
   * Returns the document's text interspersed with &lt;Node&gt; elements at all
   * points where the document has an annotation beginning or ending.
   */
  public static String textWithNodes(TextualDocument doc, String aText) {
    // filterNonXmlChars
    // getoffsets for Nodes
    // getoffsets for XML entities
    if(aText == null) return new String("");
    StringBuffer textWithNodes = filterNonXmlChars(new StringBuffer(aText));
    // Construct a map from offsets to Chars ()
    SortedMap<Long, Character> offsets2CharsMap = new TreeMap<Long, Character>();
    if(aText.length() != 0) {
      // Fill the offsets2CharsMap with all the indices where special chars
      // appear
      buildEntityMapFromString(aText, offsets2CharsMap);
    }// End if
    // Construct the offsetsSet for all nodes belonging to this document
    SortedSet<Long> offsetsSet = new TreeSet<Long>();
    Iterator<Annotation> annotSetIter = doc.getAnnotations().iterator();
    while(annotSetIter.hasNext()) {
      Annotation annot = annotSetIter.next();
      offsetsSet.add(annot.getStartNode().getOffset());
      offsetsSet.add(annot.getEndNode().getOffset());
    }// end While
    // Get the nodes from all other named annotation sets.
    Map<String,AnnotationSet> namedAnnotSets = doc.getNamedAnnotationSets();
    if(namedAnnotSets != null) {
      Iterator<AnnotationSet> iter = namedAnnotSets.values().iterator();
      while(iter.hasNext()) {
        AnnotationSet annotSet = iter.next();
        Iterator<Annotation> iter2 = annotSet.iterator();
        while(iter2.hasNext()) {
          Annotation annotTmp = iter2.next();
          offsetsSet.add(annotTmp.getStartNode().getOffset());
          offsetsSet.add(annotTmp.getEndNode().getOffset());
        }// End while
      }// End while
    }// End if
    // offsetsSet is ordered in ascending order because the structure
    // is a TreeSet
    if(offsetsSet.isEmpty()) { return replaceCharsWithEntities(aText)
            .toString(); }// End if
    
    // create a large StringBuffer
    StringBuffer modifiedBuffer = new StringBuffer(textWithNodes.length() * 2);
    
    // last character copied from the original String
    int lastCharactercopied = 0;
    
    // append to buffer all text up to next offset
    // for node or entity
    // we need to iterate on offsetSet and offsets2CharsMap
    Set<Long> allOffsets = new TreeSet<Long>();
    allOffsets.addAll(offsetsSet);
    allOffsets.addAll(offsets2CharsMap.keySet());
    Iterator<Long> allOffsetsIterator = allOffsets.iterator();
    while (allOffsetsIterator.hasNext()){
      Long nextOffset = allOffsetsIterator.next();
      int nextOffsetint = nextOffset.intValue();
      // is there some text to add since last time?
      if (nextOffsetint>lastCharactercopied){
        modifiedBuffer.append(textWithNodes.substring(lastCharactercopied,nextOffsetint));
        lastCharactercopied=nextOffsetint;
      }
      // do we need to add a node information here?
      if (offsetsSet.contains(nextOffset))
        modifiedBuffer.append("<Node id=\"").append(nextOffsetint).append("\"/>");
      
      // do we need to convert an XML entity?
      if (offsets2CharsMap.containsKey(nextOffset)){
       String entityString = entitiesMap.get(offsets2CharsMap.get(nextOffset));
       // skip the character in the original String
       lastCharactercopied++;
       // append the corresponding entity
       modifiedBuffer.append(entityString);
      }
    }
    // copies the remaining text
    modifiedBuffer.append(textWithNodes.substring(lastCharactercopied,textWithNodes.length()));
    
    return modifiedBuffer.toString();
  }

  /**
   * This method takes aScanString and searches for those chars from entitiesMap
   * that appear in the string. A tree map(offset2Char) is filled using as key
   * the offsets where those Chars appear and the Char. If one of the params is
   * null the method simply returns.
   */
  public static void buildEntityMapFromString(String aScanString, SortedMap<Long, Character> aMapToFill) {
    if(aScanString == null || aMapToFill == null) return;
    if(entitiesMap == null || entitiesMap.isEmpty()) {
      Err.prln("WARNING: Entities map was not initialised !");
      return;
    }// End if
    // Fill the Map with the offsets of the special chars
    Iterator<Character> entitiesMapIterator = entitiesMap.keySet().iterator();
    Character c;
    int fromIndex;
    while(entitiesMapIterator.hasNext()) {
      c = entitiesMapIterator.next();
      fromIndex = 0;
      while(-1 != fromIndex) {
        fromIndex = aScanString.indexOf(c.charValue(), fromIndex);
        if(-1 != fromIndex) {
          aMapToFill.put(new Long(fromIndex), c);
          fromIndex++;
        }// End if
      }// End while
    }// End while
  }// buildEntityMapFromString();

  /**
   * Converts the Annotation set to XML which is appended to the supplied
   * StringBuffer instance.
   * 
   * @param anAnnotationSet
   *          The annotation set that has to be saved as XML.
   * @param buffer
   *          the StringBuffer that the XML representation should be appended to
   */
  public static void annotationSetToXml(AnnotationSet anAnnotationSet,
          StringBuffer buffer) {
    if(anAnnotationSet == null) {
      buffer.append("<AnnotationSet>\n");
      buffer.append("</AnnotationSet>\n");
      return;
    }// End if
    if(anAnnotationSet.getName() == null)
      buffer.append("<AnnotationSet>\n");
    else {
      buffer.append("<AnnotationSet Name=\"");
      buffer.append(anAnnotationSet.getName());
      buffer.append("\" >\n");
    }
    Map<String, StringBuffer> convertedKeys = new HashMap<String, StringBuffer>();
    // Iterate through AnnotationSet and save each Annotation as XML
    Iterator<Annotation> iterator = anAnnotationSet.iterator();
    while(iterator.hasNext()) {
      Annotation annot = iterator.next();
      buffer.append("<Annotation Id=\"");
      buffer.append(annot.getId());
      buffer.append("\" Type=\"");
      buffer.append(annot.getType());
      buffer.append("\" StartNode=\"");
      buffer.append(annot.getStartNode().getOffset());
      buffer.append("\" EndNode=\"");
      buffer.append(annot.getEndNode().getOffset());
      buffer.append("\">\n");
      buffer.append(featuresToXml(annot.getFeatures(),convertedKeys));
      buffer.append("</Annotation>\n");
    }// End while
    buffer.append("</AnnotationSet>\n");
  }// annotationSetToXml

  /**
   * Converts the Annotation set to XML which is appended to the supplied
   * StringBuffer instance. The standard
   * {@link #annotationSetToXml(AnnotationSet, StringBuffer) method} uses the
   * name that belongs to the provided annotation set, however, this method
   * allows one to store the provided annotation set under a different
   * annotation set name.
   * 
   * @param anAnnotationSet
   *          the annotation set that has to be saved as XML.
   * @param annotationSetNameToUse
   *          the new name for the annotation set being converted to XML
   * @param buffer
   *          the StringBuffer that the XML representation should be appended to
   */
  public static void annotationSetToXml(AnnotationSet anAnnotationSet, String annotationSetNameToUse,
          StringBuffer buffer) {
    if(anAnnotationSet == null) {
      buffer.append("<AnnotationSet>\n");
      buffer.append("</AnnotationSet>\n");
      return;
    }// End if
    if(annotationSetNameToUse == null || annotationSetNameToUse.trim().length() == 0)
      buffer.append("<AnnotationSet>\n");
    else {
      buffer.append("<AnnotationSet Name=\"");
      buffer.append(annotationSetNameToUse);
      buffer.append("\" >\n");
    }
    Map<String, StringBuffer> convertedKeys = new HashMap<String, StringBuffer>();
    // Iterate through AnnotationSet and save each Annotation as XML
    Iterator<Annotation> iterator = anAnnotationSet.iterator();
    while(iterator.hasNext()) {
      Annotation annot = iterator.next();
      buffer.append("<Annotation Id=\"");
      buffer.append(annot.getId());
      buffer.append("\" Type=\"");
      buffer.append(annot.getType());
      buffer.append("\" StartNode=\"");
      buffer.append(annot.getStartNode().getOffset());
      buffer.append("\" EndNode=\"");
      buffer.append(annot.getEndNode().getOffset());
      buffer.append("\">\n");
      buffer.append(featuresToXml(annot.getFeatures(),convertedKeys));
      buffer.append("</Annotation>\n");
    }// End while
    buffer.append("</AnnotationSet>\n");
  }// annotationSetToXml

  /**
   * A map initialized in init() containing entities that needs to be replaced
   * in strings
   */
  public static final Map<Character,String> entitiesMap = new HashMap<Character,String>();
  // Initialize the entities map use when saving as xml
  static {
    entitiesMap.put(new Character('<'), "&lt;");
    entitiesMap.put(new Character('>'), "&gt;");
    entitiesMap.put(new Character('&'), "&amp;");
    entitiesMap.put(new Character('\''), "&apos;");
    entitiesMap.put(new Character('"'), "&quot;");
    entitiesMap.put(new Character((char)160), "&#160;");
    entitiesMap.put(new Character((char)169), "&#169;");
  }// static
}
