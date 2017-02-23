/*
 *  DocumentJsonUtils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 20/Dec/2013
 *
 *  $Id: DocumentJsonUtils.java 19041 2015-12-19 23:49:25Z domrout $
 */
package gate.corpora;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import gate.Annotation;
import gate.Document;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;

/**
 * <p>
 * This class contains utility methods to output GATE documents in a
 * JSON format which is (deliberately) close to the format used by
 * Twitter to represent entities such as user mentions and hashtags in
 * Tweets.
 * </p>
 * 
 * <pre>
 * {
 *   "text":"Text of the document",
 *   "entities":{
 *     "Person":[
 *       {
 *         "indices":[startOffset, endOffset],
 *         // other features here
 *       },
 *       { ... }
 *     ],
 *     "Location":[
 *       {
 *         "indices":[startOffset, endOffset],
 *         // other features here
 *       },
 *       { ... }
 *     ]
 *   }
 * }
 * </pre>
 * 
 * <p>
 * The document is represented as a JSON object with two properties,
 * "text" holding the text of the document and "entities" representing
 * the annotations. The "entities" property is an object mapping each
 * "annotation type" to an array of objects, one per annotation, that
 * holds the annotation's start and end offsets as a property "indices"
 * and the other features of the annotation as its remaining properties.
 * Features are serialized using Jackson's ObjectMapper, so
 * string-valued features become JSON strings, numeric features become
 * JSON numbers, Boolean features become JSON booleans, and other types
 * are serialized according to Jackson's normal rules (e.g. Map values
 * become nested JSON objects).
 * </p>
 * 
 * <p>
 * The grouping of annotations into blocks is the responsibility of the
 * caller - annotations are supplied as a Map&lt;String,
 * Collection&lt;Annotation&gt;&gt;, the map keys become the property
 * names within the "entities" object and the corresponding values
 * become the annotation arrays. In particular the actual annotation
 * type of an annotation within one of the collections is ignored - it
 * is allowed to mix annotations of different types within one
 * collection, the name of the group of annotations in the "entities"
 * object comes from the map key. However some overloadings of
 * <code>writeDocument</code> provide the option to write the annotation
 * type as if it were a feature, i.e. as one of the JSON properties of
 * the annotation object.
 * </p>
 * 
 * @author ian
 * 
 */
public class DocumentJsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final JsonFactory JSON_FACTORY = new JsonFactory();

  /**
   * Write a GATE document to the specified OutputStream. The document
   * text will be written as a property named "text" and the specified
   * annotations will be written as "entities".
   * 
   * @param doc the document to write
   * @param annotationsMap annotations to write.
   * @param out the {@link OutputStream} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc,
          Map<String, Collection<Annotation>> annotationsMap, OutputStream out)
          throws JsonGenerationException, IOException {
    try(JsonGenerator jsonG = JSON_FACTORY.createGenerator(out)) {
      writeDocument(doc, annotationsMap, jsonG);
    }
  }

  /**
   * Write a GATE document to the specified Writer. The document text
   * will be written as a property named "text" and the specified
   * annotations will be written as "entities".
   * 
   * @param doc the document to write
   * @param annotationsMap annotations to write.
   * @param out the {@link Writer} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc,
          Map<String, Collection<Annotation>> annotationsMap, Writer out)
          throws JsonGenerationException, IOException {
    try(JsonGenerator jsonG = JSON_FACTORY.createGenerator(out)) {
      writeDocument(doc, annotationsMap, jsonG);
    }
  }

  /**
   * Write a GATE document to the specified File. The document text will
   * be written as a property named "text" and the specified annotations
   * will be written as "entities".
   * 
   * @param doc the document to write
   * @param annotationsMap annotations to write.
   * @param out the {@link File} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc,
          Map<String, Collection<Annotation>> annotationsMap, File out)
          throws JsonGenerationException, IOException {
    try(JsonGenerator jsonG = JSON_FACTORY.createGenerator(out, JsonEncoding.UTF8)) {
      writeDocument(doc, annotationsMap,jsonG);
    }
  }

  /**
   * Convert a GATE document to JSON representation and return it as a
   * string. The document text will be written as a property named
   * "text" and the specified annotations will be written as "entities".
   * 
   * @param doc the document to write
   * @param annotationsMap annotations to write.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   * @return the JSON as a String
   */
  public static String toJson(Document doc,
          Map<String, Collection<Annotation>> annotationsMap)
          throws JsonGenerationException, IOException {
    StringWriter sw = new StringWriter();
    JsonGenerator gen = JSON_FACTORY.createGenerator(sw);
    writeDocument(doc, annotationsMap, gen);
    gen.close();
    return sw.toString();
  }

  /**
   * Write a GATE document to the specified JsonGenerator. The document
   * text will be written as a property named "text" and the specified
   * annotations will be written as "entities".
   * 
   * @param doc the document to write
   * @param annotationsMap annotations to write.
   * @param json the {@link JsonGenerator} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc,
          Map<String, Collection<Annotation>> annotationsMap, JsonGenerator json)
          throws JsonGenerationException, IOException {
    try {
      writeDocument(doc, 0L, doc.getContent().size(), annotationsMap, json);
    } catch(InvalidOffsetException e) {
      // shouldn't happen
      throw new GateRuntimeException(
              "Got invalid offset exception when passing "
                      + "offsets that are known to be valid");
    }
  }

  /**
   * Write a substring of a GATE document to the specified
   * JsonGenerator. The specified window of document text will be
   * written as a property named "text" and the specified annotations
   * will be written as "entities", with their offsets adjusted to be
   * relative to the specified window.
   * 
   * @param doc the document to write
   * @param start the start offset of the segment to write
   * @param end the end offset of the segment to write
   * @param annotationsMap annotations to write.
   * @param json the {@link JsonGenerator} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc, Long start, Long end,
          Map<String, Collection<Annotation>> annotationsMap, JsonGenerator json)
          throws JsonGenerationException, IOException, InvalidOffsetException {
    writeDocument(doc, start, end, annotationsMap, null, null, json);
  }

  /**
   * Write a substring of a GATE document to the specified
   * JsonGenerator. The specified window of document text will be
   * written as a property named "text" and the specified annotations
   * will be written as "entities", with their offsets adjusted to be
   * relative to the specified window.
   * 
   * @param doc the document to write
   * @param start the start offset of the segment to write
   * @param end the end offset of the segment to write
   * @param annotationsMap annotations to write.
   * @param extraFeatures additional properties to add to the generated
   *          JSON. If the map includes a "text" key this will be
   *          ignored, and if it contains a key "entities" whose value
   *          is a map then these entities will be merged with the
   *          generated ones derived from the annotationsMap. This would
   *          typically be used for documents that were originally
   *          derived from Twitter data, to re-create the original JSON.
   * @param json the {@link JsonGenerator} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc, Long start, Long end,
          Map<String, Collection<Annotation>> annotationsMap,
          Map<?, ?> extraFeatures, JsonGenerator json)
          throws JsonGenerationException, IOException, InvalidOffsetException {
    writeDocument(doc, start, end, annotationsMap, extraFeatures, null, json);
  }

  /**
   * Write a substring of a GATE document to the specified
   * JsonGenerator. The specified window of document text will be
   * written as a property named "text" and the specified annotations
   * will be written as "entities", with their offsets adjusted to be
   * relative to the specified window.
   * 
   * @param doc the document to write
   * @param start the start offset of the segment to write
   * @param end the end offset of the segment to write
   * @param extraFeatures additional properties to add to the generated
   *          JSON. If the map includes a "text" key this will be
   *          ignored, and if it contains a key "entities" whose value
   *          is a map then these entities will be merged with the
   *          generated ones derived from the annotationsMap. This would
   *          typically be used for documents that were originally
   *          derived from Twitter data, to re-create the original JSON.
   * @param annotationTypeProperty if non-null, the annotation type will
   *          be written as a property under this name, as if it were an
   *          additional feature of each annotation.
   * @param json the {@link JsonGenerator} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc, Long start, Long end,
          Map<String, Collection<Annotation>> annotationsMap,
          Map<?, ?> extraFeatures, String annotationTypeProperty, JsonGenerator json) 
          throws JsonGenerationException, IOException, InvalidOffsetException {
    writeDocument(doc, start, end, annotationsMap, extraFeatures, annotationTypeProperty, null, json);

  } 
  /**
   * Write a substring of a GATE document to the specified
   * JsonGenerator. The specified window of document text will be
   * written as a property named "text" and the specified annotations
   * will be written as "entities", with their offsets adjusted to be
   * relative to the specified window.
   * 
   * @param doc the document to write
   * @param start the start offset of the segment to write
   * @param end the end offset of the segment to write
   * @param extraFeatures additional properties to add to the generated
   *          JSON. If the map includes a "text" key this will be
   *          ignored, and if it contains a key "entities" whose value
   *          is a map then these entities will be merged with the
   *          generated ones derived from the annotationsMap. This would
   *          typically be used for documents that were originally
   *          derived from Twitter data, to re-create the original JSON.
   * @param annotationTypeProperty if non-null, the annotation type will
   *          be written as a property under this name, as if it were an
   *          additional feature of each annotation.
   * @param annotationIDProperty if non-null, the annotation ID will
   *          be written as a property under this name, as if it were an
   *          additional feature of each annotation.
   * @param json the {@link JsonGenerator} to write to.
   * @throws JsonGenerationException if a problem occurs while
   *           generating the JSON
   * @throws IOException if an I/O error occurs.
   */
  public static void writeDocument(Document doc, Long start, Long end,
          Map<String, Collection<Annotation>> annotationsMap,
          Map<?, ?> extraFeatures, String annotationTypeProperty, 
          String annotationIDProperty, JsonGenerator json) throws JsonGenerationException, IOException,
          InvalidOffsetException {
    ObjectWriter writer = MAPPER.writer();

    json.writeStartObject();
    RepositioningInfo repos = new RepositioningInfo();
    String text = escape(doc.getContent().getContent(start, end)
            .toString(), repos);
    json.writeStringField("text", text);
    json.writeFieldName("entities");
    json.writeStartObject();
    // if the extraFeatures already includes entities, merge them with
    // the new ones we create
    Object entitiesExtraFeature =
            (extraFeatures == null) ? null : extraFeatures.get("entities");
    Map<?, ?> entitiesMap = null;
    if(entitiesExtraFeature instanceof Map) {
      entitiesMap = (Map<?, ?>)entitiesExtraFeature;
    }
    for(Map.Entry<String, Collection<Annotation>> annsByType : annotationsMap
            .entrySet()) {
      String annotationType = annsByType.getKey();
      Collection<Annotation> annotations = annsByType.getValue();
      json.writeFieldName(annotationType);
      json.writeStartArray();
      for(Annotation a : annotations) {
        json.writeStartObject();
        // indices:[start, end], corrected to match the sub-range of
        // text we're writing
        json.writeArrayFieldStart("indices");
        json.writeNumber(repos.getOriginalPos(a.getStartNode().getOffset() - start, true));
        json.writeNumber(repos.getOriginalPos(a.getEndNode().getOffset() - start, false));
        json.writeEndArray(); // end of indices
        if(annotationTypeProperty != null) {
          json.writeStringField(annotationTypeProperty, a.getType());
        } 
        if (annotationIDProperty != null) {
          json.writeNumberField(annotationIDProperty, a.getId());
        }
        // other features
        for(Map.Entry<?, ?> feature : a.getFeatures().entrySet()) {
          if(annotationTypeProperty != null
                  && annotationTypeProperty.equals(feature.getKey())) {
            // ignore a feature that has the same name as the
            // annotationTypeProperty
            continue;
          }
          json.writeFieldName(String.valueOf(feature.getKey()));
          writer.writeValue(json, feature.getValue());
        }
        json.writeEndObject(); // end of annotation
      }
      // add any entities from the extraFeatures map
      if(entitiesMap != null
              && entitiesMap.get(annotationType) instanceof Collection) {
        for(Object ent : (Collection<?>)entitiesMap.get(annotationType)) {
          writer.writeValue(json, ent);
        }
      }
      json.writeEndArray();
    }
    if(entitiesMap != null) {
      for(Map.Entry<?, ?> entitiesEntry : entitiesMap.entrySet()) {
        if(!annotationsMap.containsKey(entitiesEntry.getKey())) {
          // not an entity type we've already seen
          json.writeFieldName(String.valueOf(entitiesEntry.getKey()));
          writer.writeValue(json, entitiesEntry.getValue());
        }
      }
    }

    json.writeEndObject(); // end of entities

    if(extraFeatures != null) {
      for(Map.Entry<?, ?> feature : extraFeatures.entrySet()) {
        if("text".equals(feature.getKey())
                || "entities".equals(feature.getKey())) {
          // already dealt with text and entities
          continue;
        }
        json.writeFieldName(String.valueOf(feature.getKey()));
        writer.writeValue(json, feature.getValue());
      }
    }
    json.writeEndObject(); // end of document

    // Make sure that everything we have generated is flushed to the
    // underlying OutputStream. It seems that not doing this can easily
    // lead to corrupt files that just end in the middle of a JSON
    // object. This occurs even if you flush the OutputStream instance
    // as the data never leaves the JsonGenerator
    json.flush();
  }

  /**
   * Characters to account for when escaping - ampersand, angle brackets, and supplementaries
   */
  private static final Pattern CHARS_TO_ESCAPE = Pattern.compile("[<>&\\x{" +
          Integer.toHexString(Character.MIN_SUPPLEMENTARY_CODE_POINT)+ "}-\\x{" +
          Integer.toHexString(Character.MAX_CODE_POINT) + "}]");
  
  /**
   * Escape all angle brackets and ampersands in the given string,
   * recording the adjustments to character offsets within the
   * given {@link RepositioningInfo}.  Also record supplementary
   * characters (above U+FFFF), which count as two in terms of
   * GATE annotation offsets (which count in Java chars) but one
   * in terms of JSON (counting in Unicode characters).
   */
  private static String escape(String str, RepositioningInfo repos) {
    StringBuffer buf = new StringBuffer();
    int origOffset = 0;
    int extractedOffset = 0;
    Matcher mat = CHARS_TO_ESCAPE.matcher(str);
    while(mat.find()) {
      if(mat.start() != extractedOffset) {
        // repositioning record for the span from end of previous match to start of this one
        int nonMatchLen = mat.start() - extractedOffset;
        repos.addPositionInfo(origOffset, nonMatchLen, extractedOffset, nonMatchLen);
        origOffset += nonMatchLen;
        extractedOffset += nonMatchLen;
      }

      // the extracted length is the number of code units matched by the pattern
      int extractedLen = mat.end() - mat.start();
      int origLen = 0;
      String replace = "?";
      switch(mat.group()) {
        case "&":
          replace = "&amp;";
          origLen = 5;
          break;
        case ">":
          replace = "&gt;";
          origLen = 4;
          break;
        case "<":
          replace = "&lt;";
          origLen = 4;
          break;
        default:
          // supplementary character, so no escaping but need to account for
          // it in repositioning info
          replace = mat.group();
          origLen = 1;
      }
      // repositioning record covering this match
      repos.addPositionInfo(origOffset, origLen, extractedOffset, extractedLen);
      mat.appendReplacement(buf, replace);
      origOffset += origLen;
      extractedOffset += extractedLen;

    }
    int tailLen = str.length() - extractedOffset;
    if(tailLen > 0) {
      // repositioning record covering everything after the last match
      repos.addPositionInfo(origOffset, tailLen + 1, extractedOffset, tailLen + 1);
    }
    mat.appendTail(buf);
    return buf.toString();
  }
}
