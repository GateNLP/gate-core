/*
 *  AnnotationSchema.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 27/Sept/2000
 *
 *  $Id: AnnotationSchema.java 19273 2016-05-05 08:57:14Z markagreenwood $
 */
package gate.creole;

import gate.Factory;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/** This class handles annotation schemas.An annotation schema is a
  * representation of an annotation, together with its types and their
  * attributes, values and types.
  */
@CreoleResource(name="Annotation Schema", comment="An annotation type and its features.", helpURL="http://gate.ac.uk/userguide/sec:corpora:schemas")
public class AnnotationSchema extends AbstractLanguageResource{
  
  private static final long serialVersionUID = 3499202938128514949L;

  public static final String FILE_URL_PARAM_NAME = "xmlFileUrl";

  /** A map between XSchema types and Java Types */
  private static Map<String, Class<?>> xSchema2JavaMap;

  /** A map between Java types and XSchema */
  private static Map<Class<?>, String> java2xSchemaMap;

  /** This sets up two Maps between XSchema types and their corresponding
    * Java types + a DOM xml parser
    */
  private static void setUpStaticData()
  throws ResourceInstantiationException
  {
    xSchema2JavaMap = new HashMap<String, Class<?>>();
    java2xSchemaMap = new HashMap<Class<?>, String>();

    xSchema2JavaMap.put("string",   String.class);
    xSchema2JavaMap.put("integer",  Integer.class);
    xSchema2JavaMap.put("int",      Integer.class);
    xSchema2JavaMap.put("boolean",  Boolean.class);
    xSchema2JavaMap.put("float",    Float.class);
    xSchema2JavaMap.put("double",   Double.class);
    xSchema2JavaMap.put("short",    Short.class);
    xSchema2JavaMap.put("byte",     Byte.class);

    java2xSchemaMap.put(String.class,   "string");
    java2xSchemaMap.put(Integer.class,  "integer");
    java2xSchemaMap.put(Boolean.class,  "boolean");
    java2xSchemaMap.put(Float.class,    "float");
    java2xSchemaMap.put(Double.class,   "double");
    java2xSchemaMap.put(Short.class,    "short");
    java2xSchemaMap.put(Byte.class,     "byte");
  } //setUpStaticData

  /** The name of the annotation */
  protected String annotationName = null;

  /** Returns the value of annotation name */
  public String getAnnotationName(){
    return annotationName;
  } // getAnnotationName

  /** Sets the annotation name */
  public void setAnnotationName(String annotationName) {
    this.annotationName = annotationName;
  } // setAnnotationName

  /** Schemas for the attributes */
  protected Set<FeatureSchema> featureSchemaSet = null;

  /** Constructs an annotation schema. */
  public AnnotationSchema(){
  } // AnnotationSchema

  /** Returns the feature schema set */
  public Set<FeatureSchema> getFeatureSchemaSet(){
    return featureSchemaSet;
  } // getAttributeSchemas

  /** Sets the feature schema set */
  public void setFeatureSchemaSet(Set<FeatureSchema> featureSchemaSet) {
    this.featureSchemaSet = featureSchemaSet;
  } // setFeatureSchemaSet

  /** @return a FeatureSchema object from featureSchemaSet, given a
    * feature name.It will return null if the feature name is not found.
    */
  public FeatureSchema getFeatureSchema(String featureName) {
    if(featureSchemaSet == null) return null;
    for(FeatureSchema fs : featureSchemaSet){
      if (fs.getFeatureName().equals(featureName)) return fs;      
    }
    return null;
  } // getFeatureSchema

  /** Initialise this resource, and return it. If the schema XML source file
    * URL has been set, it will construct itself from that file.
    */
  @Override
  public Resource init() throws ResourceInstantiationException {
    // set up the static data if it's not there already
    if(xSchema2JavaMap == null || java2xSchemaMap == null)
      setUpStaticData();

    // parse the XML file if we have its URL
    if(xmlFileUrl != null) {
      fromXSchema(xmlFileUrl);

      if(annotationName == null) {
        Factory.deleteResource(this);

        if(lastIncluded != null) return lastIncluded;

        throw new ResourceInstantiationException(
                "The specified XML Schema doesn't define any annotation types");
      }
    }

    return this;
  } // init()

  /** The xml file URL of the resource */
  protected URL xmlFileUrl;
  
  private transient AnnotationSchema lastIncluded = null;

  /** Set method for the resource xml file URL */
  @CreoleParameter(comment="The url to the definition file", suffixes="xml;xsd")
  public void setXmlFileUrl(URL xmlFileUrl) { this.xmlFileUrl = xmlFileUrl; }

  /** Get method for the resource xml file URL */
  public URL getXmlFileUrl() { return xmlFileUrl; }

  /** Creates an AnnotationSchema object from an XSchema file
    * @param anXSchemaURL the URL where to find the XSchema file
    */
  public void fromXSchema(URL anXSchemaURL)
              throws ResourceInstantiationException {
    org.jdom.Document jDom = null;
    SAXBuilder saxBuilder = new SAXBuilder(false);
    try {
    try{
      jDom = saxBuilder.build(anXSchemaURL);
    }catch(JDOMException je){
      throw new ResourceInstantiationException(je);
    }
    } catch (java.io.IOException ex) {
      throw new ResourceInstantiationException(ex);
    }
    workWithJDom(jDom);
  } // fromXSchema

  /** Creates an AnnotationSchema object from an XSchema file
    * @param anXSchemaInputStream the Input Stream containing the XSchema file
    */
  public void fromXSchema(InputStream anXSchemaInputStream)
              throws ResourceInstantiationException {
    org.jdom.Document jDom = null;
    SAXBuilder saxBuilder = new SAXBuilder(false);
    try {
    try{
      jDom = saxBuilder.build(anXSchemaInputStream);
    }catch(JDOMException je){
      throw new ResourceInstantiationException(je);
    }
    } catch (java.io.IOException ex) {
      throw new ResourceInstantiationException(ex);
    }
    workWithJDom(jDom);
  } // end fromXSchema

  /** This method uses the JDom structure for our XSchema needs. What it does is
    * to add semantics to the XML elements defined in XSchema. In the end we need
    * to construct an AnnotationSchema object form an XSchema file.
    *
    * @param jDom the JDOM structure containing the XSchema document. It must not
    * be <b>null<b>
    */
  private void workWithJDom(org.jdom.Document jDom) throws ResourceInstantiationException {
    // Use the jDom structure the way we want
    org.jdom.Element rootElement = jDom.getRootElement();
    Namespace namespace = rootElement.getNamespace();
    
    // get all children elements from the rootElement
    List<?> rootElementChildrenList =
            rootElement.getChildren("element", namespace);
    if(rootElementChildrenList.size() > 1)
      throw new ResourceInstantiationException(
              "Each Annotation must be defined in a separate XML Schema file");
    Iterator<?> rootElementChildrenIterator = rootElementChildrenList.iterator();
    while(rootElementChildrenIterator.hasNext()) {
      org.jdom.Element childElement =
              (org.jdom.Element)rootElementChildrenIterator.next();
      createAnnotationSchemaObject(childElement, namespace);
    }// end while
    
    rootElementChildrenList = rootElement.getChildren("include", namespace);
    rootElementChildrenIterator = rootElementChildrenList.iterator();
    while(rootElementChildrenIterator.hasNext()) {
      org.jdom.Element childElement =
              (org.jdom.Element)rootElementChildrenIterator.next();

      try {
        String url = childElement.getAttributeValue("schemaLocation");
        FeatureMap params = Factory.newFeatureMap();
        params.put("xmlFileUrl", new URL(xmlFileUrl, url));

        lastIncluded =
                (AnnotationSchema)Factory.createResource(
                        "gate.creole.AnnotationSchema", params);
      } catch(Exception e) {
        throw new ResourceInstantiationException(e);
      }
    }
  } // workWithJdom

  /** This method creates an AnnotationSchema object fom an org.jdom.Element
    * @param anElement is an XSchema element element
    */
  private void createAnnotationSchemaObject(org.jdom.Element anElement, Namespace namespace){
    // Get the value of the name attribute. If this attribute doesn't exists
    // then it will receive a default one.
    annotationName = anElement.getAttributeValue("name");
    if (annotationName == null)
        annotationName = "UnknownElement";
    // See if this element has a complexType element inside it
    org.jdom.Element complexTypeElement = anElement.getChild("complexType",
                                                             namespace);
    if (complexTypeElement != null){
      List<?> complexTypeCildrenList = complexTypeElement.getChildren("attribute",
                                                                   namespace);
      Iterator<?> complexTypeCildrenIterator = complexTypeCildrenList.iterator();
      if (complexTypeCildrenIterator.hasNext())
        featureSchemaSet = new LinkedHashSet<FeatureSchema>();
      while (complexTypeCildrenIterator.hasNext()) {
        org.jdom.Element childElement =
                    (org.jdom.Element) complexTypeCildrenIterator.next();
        createAndAddFeatureSchemaObject(childElement, namespace);
      }// end while
    }// end if
  } // createAnnoatationSchemaObject

  /** This method creates and adds a FeatureSchema object to the current
    * AnnotationSchema one.
    * @param anAttributeElement is an XSchema attribute element
    */
  public void createAndAddFeatureSchemaObject(org.jdom.Element
                                                          anAttributeElement, Namespace namespace) {
    String featureName = null;
    Class<?> featureType = null;
    String featureUse  = null;
    String featureValue = null;
    Set<String> featurePermittedValuesSet = null;

    // Get the value of the name attribute. If this attribute doesn't exists
    // then it will receive a default one.
    featureName = anAttributeElement.getAttributeValue("name");
    if (featureName == null)
      featureName = "UnknownFeature";

    // See if it has a type attribute associated
    String featureTypeName = anAttributeElement.getAttributeValue("type");
    if (featureTypeName != null)
      // Set it to the corresponding Java type
      featureType = xSchema2JavaMap.get(featureTypeName);
    
    // Get the value of use attribute
    featureUse = anAttributeElement.getAttributeValue("use");
    if (featureUse == null)
      // Set it to the default value
      featureUse = "optional";

    // Get the value of value attribute
    featureValue = anAttributeElement.getAttributeValue("value");
    if (featureValue == null)
      featureValue = "";

    // Let's check if it has a simpleType element inside
    org.jdom.Element simpleTypeElement  =
                                  anAttributeElement.getChild("simpleType",
                                                              namespace);

    // If it has (!= null) then check to see if it has a restrictionElement
    if (simpleTypeElement != null) {
      org.jdom.Element restrictionElement =
                              simpleTypeElement.getChild("restriction",
                                                         namespace);
      if (restrictionElement != null) {
        // Get the type attribute for restriction element
        featureTypeName = restrictionElement.getAttributeValue("base");
        if (featureTypeName == null) featureTypeName = "string";
        // Set it to the corresponding Java type
        featureType =  xSchema2JavaMap.get(featureTypeName);

        // Check to see if there are any enumeration elements inside
        List<?> enumerationElementChildrenList =
                                 restrictionElement.getChildren("enumeration",
                                                                namespace);
        Iterator<?> enumerationChildrenIterator =
                                enumerationElementChildrenList.iterator();

        // Check if there is any enumeration element in the list
        if (enumerationChildrenIterator.hasNext())
            featurePermittedValuesSet = new HashSet<String>();
        while (enumerationChildrenIterator.hasNext()) {
          org.jdom.Element enumerationElement =
                        (org.jdom.Element) enumerationChildrenIterator.next();
          String permissibleValue =
                            enumerationElement.getAttributeValue("value");
          // Add that value to the featureSchema possible values set.
          featurePermittedValuesSet.add(permissibleValue);
        }// end while
      }// end if( restrictionElement != null)
    }// end if (simpleTypeElement != null)

    // If it doesn't have a simpleTypeElement inside and featureType is null or
    // it wasn't recognised, then we set the default type to string.
    if (simpleTypeElement == null && featureType == null )
      featureType = xSchema2JavaMap.get("string");

    // Create an add a featureSchema object
    FeatureSchema featureSchema = new FeatureSchema(
                                                   featureName,
                                                   //if for some reason we've ended up with a null type default to String
                                                   featureType != null ? featureType : String.class, 
                                                   featureValue,
                                                   featureUse,
                                                   featurePermittedValuesSet);
    featureSchemaSet.add(featureSchema);
  } // createAndAddFeatureSchemaObject

  /** @return a String containing the XSchema document representing
    *  an AnnotationSchema object.
    */
  public String toXSchema(){
    StringBuffer schemaString = new StringBuffer();
    schemaString.append("<?xml version=\"1.0\"?>\n" +
                   "<schema xmlns=\"http://www.w3.org/2000/10/XMLSchema\">\n"+
                   " <element name=\"" + annotationName + "\"");

    if (featureSchemaSet == null)
      schemaString.append("/>\n");
    else {
      schemaString.append(">\n  <complexType>\n");
      for(FeatureSchema fs : featureSchemaSet){
        schemaString.append(fs.toXSchema(java2xSchemaMap));
      }
      schemaString.append("  </complexType>\n");
      schemaString.append(" </element>\n");
    }// end if else
    schemaString.append("</schema>\n");
    return schemaString.toString();
  }// toXSchema
} // AnnotationSchema


