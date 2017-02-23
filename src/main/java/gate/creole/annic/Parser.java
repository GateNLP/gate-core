/*
 *  Parser.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Parser.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * This class provides utility methods to export the Hits to XML and
 * read them back from XML to HIT objects.
 * 
 * @author niraj
 * 
 */
public class Parser {

  /**
   * HITS XML Element
   */
  public static final String HITS = "HITS";

  /**
   * HIT  XML Element
   */
  public static final String HIT = "HIT";

  /**
   *  DOC_ID XML Element
   */
  public static final String DOC_ID = "DOC_ID";
  
  /**
   *  ANNOTATION_SET_NAME XML Element
   */
  public static final String ANNOTATION_SET_NAME = "ANNOTATION_SET_NAME";

  /**
   * START_OFFSET  XML Element
   */
  public static final String START_OFFSET = "START_OFFSET";

  /**
   * END_OFFSET XML Element
   */
  public static final String END_OFFSET = "END_OFFSET";

  /**
   * QUERY XML Element
   */
  public static final String QUERY = "QUERY";

  /**
   * LEFT_CONTEXT_START_OFFSET XML Element
   */
  public static final String LEFT_CONTEXT_START_OFFSET = "LEFT_CONTEXT_START_OFFSET";

  /**
   * RIGHT_CONTEXT_END_OFFSET XML Element
   */
  public static final String RIGHT_CONTEXT_END_OFFSET = "RIGHT_CONTEXT_END_OFFSET";

  /**
   * PATTERN_TEXT XML Element
   */
  public static final String PATTERN_TEXT = "PATTERN_TEXT";

  /**
   * PATTERN_ANNOTATIONS XML Element
   */
  public static final String PATTERN_ANNOTATIONS = "PATTERN_ANNOTATIONS";

  /**
   * PATTERN_ANNOTATION XML Element
   */
  public static final String PATTERN_ANNOTATION = "PATTERN_ANNOTATION";

  /**
   * START XML Element
   */
  public static final String START = "START";

  /**
   * END XML Element
   */
  public static final String END = "END";

  /**
   * TEXT XML Element
   */
  public static final String TEXT = "TEXT";

  /**
   * TYPE XML Element
   */
  public static final String TYPE = "TYPE";

  /**
   * POSITION XML Element
   */
  public static final String POSITION = "POSITION";

  /**
   * FEATURES XML Element
   */
  public static final String FEATURES = "FEATURES";

  /**
   * FEATURE XML Element
   */
  public static final String FEATURE = "FEATURE";

  /**
   * KEY XML Element
   */
  public static final String KEY = "KEY";

  /**
   * VALUE XML Element
   */
  public static final String VALUE = "VALUE";

  /**
   * Given an array of instances of Hit, this method returns an xml
   * representation of the Hit
   */
  public static String toXML(Hit[] hits) {
    StringBuffer sb = new StringBuffer();

    // first sentence
    sb.append("<?xml version=\"1.0\"?>\n");

    // root element
    sb.append(wrap(HITS, true));

    // iterating through each hit
    for(int i = 0; i < hits.length; i++) {

      // adding a hit element
      sb.append(wrap(HIT, true));
      sb.append(wrap(DOC_ID, hits[i].documentID));
      sb.append(wrap(ANNOTATION_SET_NAME, hits[i].annotationSetName));
      sb.append(wrap(START_OFFSET, hits[i].startOffset));
      sb.append(wrap(END_OFFSET, hits[i].endOffset));
      sb.append(wrap(QUERY, hits[i].queryString));
      

      // it hit is an instance of Pattern, we need to add further
      // information as well
      if(hits[i] instanceof Pattern) {
        Pattern pat = (Pattern)hits[i];
        sb.append(wrap(LEFT_CONTEXT_START_OFFSET, pat
                .getLeftContextStartOffset()));
        sb
                .append(wrap(RIGHT_CONTEXT_END_OFFSET, pat
                        .getRightContextEndOffset()));
        sb.append(wrap(PATTERN_TEXT, pat.getPatternText()));

        PatternAnnotation[] annots = pat.getPatternAnnotations();

        // all annotations should be exported
        sb.append(wrap(PATTERN_ANNOTATIONS, true));

        // one annotation at a time
        for(int j = 0; j < annots.length; j++) {
          sb.append(wrap(PATTERN_ANNOTATION, true));
          sb.append(wrap(START, annots[j].getStartOffset()));
          sb.append(wrap(END, annots[j].getEndOffset()));
          sb.append(wrap(TEXT, annots[j].getText()));
          sb.append(wrap(TYPE, annots[j].getType()));
          sb.append(wrap(POSITION, annots[j].getPosition()));
          // exporting features as well
          Map<String, String> features = annots[j].getFeatures();
          sb.append(wrap(FEATURES, true));
          // one feature at a time
          //if(features != null) {
            //Set<String> keySet = features.keySet();
            //if(keySet != null) {
              //Iterator<String> iter = keySet.iterator();
              //while(iter.hasNext()) {
              for (Map.Entry<String,String> entry : features.entrySet()) {
                sb.append(wrap(FEATURE, true));
                String key = entry.getKey();
                sb.append(wrap(KEY, key));
                String value = entry.getValue();
                sb.append(wrap(VALUE, value));
                sb.append(wrap(FEATURE, false));
              }
            //}
          //}
          sb.append(wrap(FEATURES, false));
          sb.append(wrap(PATTERN_ANNOTATION, false));
        }
        sb.append(wrap(PATTERN_ANNOTATIONS, false));
      }
      sb.append(wrap(HIT, false));
    }
    sb.append(wrap(HITS, false));
    return sb.toString();
  }

  /**
   * This method replaces all the special characters (invalid xml characters) with their respective legal sequences.
   * These includes &, <, >, \ and '.
   */
  public static String replaceAmpChars(String s) {
    s = s.replaceAll("&", "&amp;");
    s = s.replaceAll("<", "&lt;");
    s = s.replaceAll(">", "&gt;");
    s = s.replaceAll("\"", "&quot;");
    s = s.replaceAll("'", "&apos;");
    return s;
  }

  /**
   * Given xml representation of HIT converts them into an array of hits
   * @throws IOException
   */
  public static Hit[] fromXML(String xml) throws IOException, JDOMException {
    SAXBuilder saxBuilder = new SAXBuilder(false);
    org.jdom.Document jdomDoc = saxBuilder.build(new StringReader(xml));
    Element rootElement = jdomDoc.getRootElement();
    if(!rootElement.getName().equalsIgnoreCase(HITS)) {
      throw new IOException("Root element must be " + HITS);
    }

    // rootElement is HITS
    // this will internally contains instances of HIT
    List<?> hitsChildren = rootElement.getChildren(HIT);
    Hit[] hits = new Hit[hitsChildren.size()];

    for(int i = 0; i < hitsChildren.size(); i++) {
      Element hitElem = (Element)hitsChildren.get(i);
      int startOffset = Integer.parseInt(hitElem.getChildText(START_OFFSET));
      int endOffset = Integer.parseInt(hitElem.getChildText(END_OFFSET));
      String docID = hitElem.getChildText(DOC_ID);
      String annotationSetName = hitElem.getChildText(ANNOTATION_SET_NAME);
      String queryString = hitElem.getChildText(QUERY);

      Element patternAnnotations = hitElem.getChild(PATTERN_ANNOTATIONS);
      if(patternAnnotations == null) {
        hits[i] = new Hit(docID, annotationSetName, startOffset, endOffset, queryString);
        continue;
      }

      List<?> patAnnots = patternAnnotations.getChildren(PATTERN_ANNOTATION);
      List<PatternAnnotation> patAnnotsList = new ArrayList<PatternAnnotation>();
      for(int j = 0; j < patAnnots.size(); j++) {
        Element patAnnot = (Element)patAnnots.get(j);
        PatternAnnotation pa = new PatternAnnotation();
        pa.setStOffset(Integer.parseInt(patAnnot.getChildText(START)));
        pa.setEnOffset(Integer.parseInt(patAnnot.getChildText(END)));
        pa.setPosition(Integer.parseInt(patAnnot.getChildText(POSITION)));
        pa.setText(patAnnot.getChildText(TEXT));
        pa.setType(patAnnot.getChildText(TYPE));

        // we need to find out its features
        Element featuresElem = patAnnot.getChild(FEATURES);
        // more than one features possible
        List<?> featuresElemsList = featuresElem.getChildren(FEATURE);
        for(int k = 0; k < featuresElemsList.size(); k++) {
          Element featureElem = (Element)featuresElemsList.get(k);
          String key = featureElem.getChildText(KEY);
          String value = featureElem.getChildText(VALUE);
          pa.addFeature(key, value);
        }
        patAnnotsList.add(pa);
      }

      String patternText = hitElem.getChildText(PATTERN_TEXT);
      int leftCSO = Integer.parseInt(hitElem
              .getChildText(LEFT_CONTEXT_START_OFFSET));
      int rightCEO = Integer.parseInt(hitElem
              .getChildText(RIGHT_CONTEXT_END_OFFSET));

      hits[i] = new Pattern(docID, annotationSetName, patternText, startOffset, endOffset,
              leftCSO, rightCEO, patAnnotsList, queryString);
    }
    return hits;
  }

  /**
   * wraps the element into the following format
   * @return &lt;elementText&gt;elementValue&lt;/elementText&gt;
   */
  public static String wrap(String elementText, String elementValue) {
    if(elementValue == null) {
      return "<" + elementText + "> </" + elementText + ">\n";
    }
    return "<" + elementText + ">" + replaceAmpChars(elementValue) + "</"
            + elementText + ">\n";
  }

  /**
   * wraps the element into the following format
   * 
   * @return &lt;elementText&gt;elementValue&lt;/elementText&gt;
   */
  public static String wrap(String elementText, int elementValue) {
    return wrap(elementText, "" + elementValue);
  }

  /**
   * wraps the element into the following format
   * @return "&lt;" + (startElement ? "" : "/") + elementText + "&gt;\n";
   */
  public static String wrap(String elementText, boolean startElement) {
    return "<" + (startElement ? "" : "/") + elementText + ">\n";
  }
}
