/*
 *  CasDocumentFormat.java
 *
 *  Copyright (c) 2011, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz, 27/June/2011
 *
 *  $Id:$
 */

package gate.corpora;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.util.DocumentFormatException;
import gate.util.InvalidOffsetException;
import gate.util.Out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UIMA XCAS and XMICAS document formats.
 */
@CreoleResource(name = "UIMA Document Format", isPrivate = true,
    autoinstances = {@AutoInstance(hidden = true)})
public class UimaDocumentFormat extends XmlDocumentFormat {

  private static final long serialVersionUID = -3804187336078120808L;

  @Override
  public void unpackMarkup(Document doc, RepositioningInfo repInfo,
          RepositioningInfo ampCodingInfo) throws DocumentFormatException {
    super.unpackMarkup(doc, repInfo, ampCodingInfo);
    unpackCasMarkup(doc);
  }

  /**
   * Convert UIMA CAS markups to GATE markups.
   * @param doc XML document already parsed
   * @throws DocumentFormatException error when parsing the file
   */
  private void unpackCasMarkup(Document doc)
    throws DocumentFormatException {

    AnnotationSet inputAS = doc.getAnnotations("Original markups");
    AnnotationSet outputAS = doc.getAnnotations("Original markups");

    // set format specific names
    String casPrefix;
    String idName;
    if (!inputAS.get("CAS").isEmpty()) {
      casPrefix = "uima.cas.";
      idName = "_id";
    } else if (!inputAS.get("xmi:XMI").isEmpty()) {
      casPrefix = "cas:";
      idName = "xmi:id";
    } else {
      throw new DocumentFormatException("The document \"" + doc.getName()
        + "\" is neither of XCAS nor XMICAS format.");
    }

    // get array/list contained elements annotations
    for (Annotation annotation : inputAS) {
      if (annotation.getType().matches(casPrefix + "[a-zA-Z]+(List|Array)")) {
        try {
          String elements = doc.getContent().getContent(
            annotation.getStartNode().getOffset(),
            annotation.getEndNode().getOffset()).toString();
          // add contained values as a feature to the array annotation
          if (!elements.trim().equals("")) {
            annotation.getFeatures().put("elements", elements);
          }
        } catch (InvalidOffsetException e) {
          throw new DocumentFormatException(e);
        }
      }
    }

    // get document content from SOFA annotations
    Set<Annotation> sofaSet = inputAS.get(casPrefix + "Sofa");
    if (sofaSet.size() > 1) {
      Out.prln("More than one UIMA SOFA, annotation offsets won't be correct.");
    }
    StringBuilder documentContent = new StringBuilder();
    for (Annotation annotation : sofaSet) {
      documentContent.append((String) annotation.getFeatures().get("sofaString"));
    }
    doc.setContent(new DocumentContentImpl(documentContent.toString()));

    // remove SOFA annotations
    inputAS.removeAll(sofaSet);

    // remove non document annotations
    inputAS.removeAll(inputAS.get("CAS"));
    inputAS.removeAll(inputAS.get("xmi:XMI"));
    inputAS.removeAll(inputAS.get("cas:NULL"));

    // get the views members, views will be added later as annotation sets
    List<List<String>> viewList = new ArrayList<List<String>>();
    for (Annotation view : inputAS.get(casPrefix + "View")) {
      viewList.add(Arrays.asList(((String)
        view.getFeatures().get("members")).split("\\s+")));
    }
    inputAS.removeAll(inputAS.get(casPrefix + "View"));

    // fill a map with the id as key and the entity name as value
    // this is specific to the Temis Luxid CAS format
    Map<String, String> entityMap = new HashMap<String,String>();
    for (Annotation entity : inputAS.get("com.temis.uima.Entity")) {
      FeatureMap features = entity.getFeatures();
      entityMap.put((String) features.get(idName),
        (String) features.get("value"));
    }

    try {
      // for each UIMA annotation
      for (Annotation annotation : new HashSet<Annotation>(inputAS)) {

        FeatureMap features = Factory.newFeatureMap();
        features.putAll(annotation.getFeatures());
        String start = (String) features.get("begin");
        String end = (String) features.get("end");
        String id = (String) features.get(idName);
        features.remove("begin"); // UIMA feature
        features.remove("end"); // UIMA feature
        features.remove("isEmptyAndSpan"); // GATE feature
        features.remove("_indexed"); // UIMA XCAS feature

        if (start == null || end == null) {
          // no offsets so add it as a GATE document feature
          features.remove(idName);
          for (Map.Entry<Object,Object> entry : features.entrySet()) {
            doc.getFeatures().put(annotation.getType()
              + '_' + id + '.' + entry.getKey(), entry.getValue());
          }
        } else { // offsets so add it as a GATE document annotation
          String entityReference = (String) features.get("_ref_entity");
          String type = entityMap.containsKey(entityReference)?
            entityMap.get(entityReference) : annotation.getType();
          Integer gateId = outputAS.add(
            Long.valueOf(start), Long.valueOf(end), type, features);
          int viewCount = 0;
          for (List<String> viewMembers : viewList) {
            if (viewMembers.contains(id)) {
              // add the annotation to the annotation set
              doc.getAnnotations("CasView" + viewCount)
                .add(outputAS.get(gateId));
            }
            viewCount++;
          }
        }
        // delete UIMA annotation
        inputAS.remove(annotation);
      }
    } catch (InvalidOffsetException e) {
      throw new DocumentFormatException("Couldn't create annotation.", e);
    }
  }

  @Override
  public Resource init() throws ResourceInstantiationException {
    // Register XML mime type
    MimeType mime = new MimeType("text", "xmi+xml");
    // Register the class handler for this mime type
    mimeString2ClassHandlerMap.put(mime.getType() + "/" + mime.getSubtype(),
      this);
    // Register the mime type with mine string
    mimeString2mimeTypeMap.put(mime.getType() + "/" + mime.getSubtype(), mime);
    // Register file suffixes for this mime type
    suffixes2mimeTypeMap.put("xcas", mime);
    suffixes2mimeTypeMap.put("xmicas", mime);
    suffixes2mimeTypeMap.put("xmi", mime);
    // Register magic numbers for this mime type
    magic2mimeTypeMap.put("<CAS version=\"2\">", mime);
    magic2mimeTypeMap.put("xmlns:cas=", mime);
    // Set the mimeType for this language resource
    setMimeType(mime);
    return this;
  }

}
