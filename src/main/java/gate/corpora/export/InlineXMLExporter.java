/*
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mark A. Greenwood 14/08/2014
 *
 */
package gate.corpora.export;

import gate.AnnotationSet;
import gate.Document;
import gate.DocumentExporter;
import gate.Factory;
import gate.FeatureMap;
import gate.GateConstants;
import gate.annotation.AnnotationSetImpl;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.util.InvalidOffsetException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CreoleResource(name = "Inline XML Exporter", tool = true, autoinstances = @AutoInstance, icon = "InlineXML")
public class InlineXMLExporter extends DocumentExporter {

  private static final long serialVersionUID = -9072204691197080958L;

  private String annotationSetName, rootElement, encoding;

  private List<String> annotationTypes;

  private Boolean includeFeatures, includeOriginalMarkups;

  public InlineXMLExporter() {
    super("Inline XML", "xml", "text/xml");
  }

  public String getAnnotationSetName() {
    return annotationSetName;
  }

  public String getEncoding() {
    return encoding;
  }

  @RunTime
  @CreoleParameter(defaultValue = "UTF-8")
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getRootElement() {
    return rootElement;
  }

  @RunTime
  @Optional
  @CreoleParameter()
  public void setRootElement(String rootElement) {
    this.rootElement = rootElement;
  }

  @RunTime
  @Optional
  @CreoleParameter
  public void setAnnotationSetName(String annotationSetName) {
    this.annotationSetName = annotationSetName;
  }

  public List<String> getAnnotationTypes() {
    return annotationTypes;
  }

  @RunTime
  @CreoleParameter(defaultValue = "Person;Location;Organization")
  public void setAnnotationTypes(List<String> annotationTypes) {
    this.annotationTypes = annotationTypes;
  }

  public Boolean getIncludeOriginalMarkups() {
    return includeOriginalMarkups;
  }

  @RunTime
  @CreoleParameter(defaultValue = "false")
  public void setIncludeOriginalMarkups(Boolean includeOriginalMarkups) {
    this.includeOriginalMarkups = includeOriginalMarkups;
  }

  public Boolean getIncludeFeatures() {
    return includeFeatures;
  }

  @RunTime
  @CreoleParameter(defaultValue = "true")
  public void setIncludeFeatures(Boolean includeFeatures) {
    this.includeFeatures = includeFeatures;
  }

  @Override
  public void export(Document doc, OutputStream out, FeatureMap options)
          throws IOException {

    Integer rootID = null;
    AnnotationSet withRoot = null;

    AnnotationSet originalMarkups = null;
    AnnotationSet backupOriginalMarkups = null;

    try {
      AnnotationSet allAnnots =
              doc.getAnnotations((String)options.get("annotationSetName"));

      if(!(Boolean)options.get("includeOriginalMarkups")) {
        originalMarkups =
                doc.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
        backupOriginalMarkups = new AnnotationSetImpl(originalMarkups);
        originalMarkups.clear();
      }

      // first transfer the annotation types from a list to a set
      @SuppressWarnings("unchecked")
      Set<String> types2Export =
              new HashSet<String>((List<String>)options.get("annotationTypes"));

      // then get the annotations for export
      AnnotationSet annots2Export = allAnnots.get(types2Export);
      withRoot = new AnnotationSetImpl(doc);
      withRoot.addAll(annots2Export);

      String rootType = (String)options.get("rootElement");
      if(rootType != null && !"".equals(rootType)) {

        // add the root element to the set
        rootID =
                withRoot.add(0L, doc.getContent().size(),
                        (String)options.get("rootElement"),
                        Factory.newFeatureMap());
      }

      // create a writer using the specified encoding
      OutputStreamWriter writer =
              new OutputStreamWriter(out, (String)options.get("encoding"));

      // write the document
      writer.write(doc.toXml(withRoot, (Boolean)options.get("includeFeatures")));

      // make sure it gets written
      writer.flush();
    } catch(InvalidOffsetException e) {
      throw new IOException(e);
    } finally {
      // delete the fake root element
      if(rootID != null) withRoot.remove(withRoot.get(rootID));

      // restore the original markups
      if(backupOriginalMarkups != null)
        originalMarkups.addAll(backupOriginalMarkups);
    }
  }
}
