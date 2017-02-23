/*
 *  Ontology.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: Ontology.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

import gate.LanguageResource;
import gate.creole.ontology.OConstants.OntologyFormat;
import gate.creole.ontology.OConstants.QueryLanguage;

import gate.util.ClosableIterator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for ontology language resources.
 * A client program may only use the methods defined in this interface
 * to manipulate ontologies and must never use any methods from the
 * implementing package.
 * <p>
 * All ontology language resources must be created using the
 * {@link gate.Factory#createResource(String, gate.FeatureMap) Factory.createResource} method.
 * <p>
 * See the documentation for the implementing plugins for details on
 * how to create ontology language resources programmatically.
 * <p>
 * Unless stated otherwise, this documentation describes 
 * the behavior of the methods as implemented in the
 * ontology API implementation plugin,
 * <a href="../../../../../plugins/Ontology/doc/javadoc/index.html" target="_parent">Ontology</a>
 * <p>
 * The backwards-compatibility plugin
 * a href="../../../../../plugins/Ontology/doc/javadoc/index.html" target="_parent">Ontology_OWLIM2</a>
 * implements all of the deprecated methods and classes but none of the
 * new methods that were added to the API in version 5.1.  Some but not all depracated
 * methods are also implemented in the new plugin <code>Ontology</code>
 * <p>
 * The use of deprecated methods should be avoided and replaced by
 * other methods as soon as possible as the backwards-compatibility
 * plugin may get removed in the future and deprecated methods may
 * get removed from the API.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
public interface Ontology extends LanguageResource {

  /**
   * This method removes the entire data from the ontology and emptys
   * it. This will also re-initialize the ontology to the state it would have
   * after creation and perform the import of system data into the ontology
   * store (OWL and RDFS assertions).
   */
  public void cleanOntology();

  /**
   * Retrieves the ontology data and returns a string with the serialization
   * of the data in the specified format.
   * 
   * @param format the format to be used for serialization <@see OConstants>
   * @return a string containing the serialization of the ontology
   * @deprecated not supported any more - throws UnsupportedOperationException
   */
  @Deprecated
  public String getOntologyData(byte format);

  /**
   * Exports the ontology data into the provided format to the provided
   * output stream.
   * 
   * @param out OutputStream the serialized ontology data is written to
   * @param format the serialization format, see {@link OConstants}
   * @deprecated not supported any more - throws UnsupportedOperationException
   */
  @Deprecated
  public void writeOntologyData(OutputStream out, byte format);

  /**
   * Write the ontology data to the provided output stream in the specified
   * serialization format. The output stream has to be closed by the caller.
   *
   * @since 5.1
   * @param out an open OutpuStream for writing the data.
   * @param format the serialization format
   * @param includeExports if false, do not write any data that was loaded
   *   as an ontology import.
   */
  public void writeOntologyData(OutputStream out, 
    OntologyFormat format, boolean includeExports);

  /**
   * Exports the ontology data into the provided format using the
   * provided writer.
   * 
   * @param out an open Writer for writing the data
   * @param format the ontology serialization format , see {@link OConstants}
   * @deprecated  not supported any more and will throw and exception
   * in the new implementation plugin
   */
  @Deprecated
  public void writeOntologyData(Writer out, byte format);

  /**
   * Write the ontology data to the provided writer in the specified 
   * serialization format. The writer object has to be closed by the caller.
   * 
   * @param out an open Writer for writing the data
   * @param format the ontology serialization format , see
   * {@link OConstants.OntologyFormat}
   * @param includeExports if false, do not write any data that was loaded as
   * and ontology import.
   */
  public void writeOntologyData(Writer out, 
      OntologyFormat format, boolean includeExports);


  /**
   * Read ontology data from the specified stream in the specified format
   * and load it into the ontology. The input stream has to be closed by
   * the caller.
   *
   * @param in an InputStream object for reading the ontology data
   * @param baseURI the URI to use for resolving URI references
   * @param format the serialization format of the ontology, see
   * {@link OConstants.OntologyFormat}
   * @param asImport if true, load the data as an ontology import which means
   *   that it will not be written as part of the user data, unless explicitly
   *   requested.
   */
  public void readOntologyData(InputStream in, String baseURI,
      OntologyFormat format, boolean asImport);



    /**
   * Read ontology data from the specified reader in the specified format
   * and load it into the ontology.
   * @param in
     * @param baseURI 
     * @param format
   * @param asImport asImport if true, load the data as an ontology import which means
   *   that it will not be written as part of the user data, unless explicitly
   *   requested.
   */
  public void readOntologyData(Reader in, String baseURI,
      OntologyFormat format, boolean asImport);


  /**
   * Get the URI of this ontology. If no ontology URI is found, null is
   * returned. If more than one ontology URI is found, an exception is
   * thrown.
   * 
   * @return the OURI of the current ontology or null
   */
  public OURI getOntologyURI();

  /**
   * Set the ontology URI of the current ontology. If the ontology
   * URI is already set to a different value, this method throws an exception.
   * <p>
   * NOTE: this method does not set the default namespace!
   * <p>
   * NOTE: at the moment, this method allows to set the ontology URI as long as no
   * URI is set yet. Once an ontology URI is set, it cannot be changed
   * since it would not be clear what to do with assertions that alreadt reference
   * that ontology URI (e.g. ontology annotations or import specifications).
   *
   * @param theURI
   */
  public void setOntologyURI(OURI theURI);


  /**
   * Loads all imported ontologies. This method finds all ontology import
   * URIs in the current ontology and loads as imports the ontologies
   * referenced by these URIs. If an URI is found in <code>importMappings<code>,
   * and maps to an empty String, the import will be ignored. If an URI
   * is found and maps to a non-empty string, the string will be interpreted
   * as an URL from which to load the imported ontology. If no entry is found
   * for the URI, the URI will be interpreted as an URL from which to load
   * the ontology. All import URIs of ontologies loaded during this process
   * will be recursively processed in the same way.
   * <p>
   * A GateOntologyException is thrown if any import that should be loaded
   * cannot be loaded and the import loading process is aborted before
   * potential additional imports are processed.
   * 
   * @param importMappings
   */
  public void resolveImports(Map<String,String> importMappings);

  /**
   * Gets the URL of this ontology. This usually is the URL the ontology was
   * loaded  from. If several files were loaded, the URL of the first file
   * is returned. Files loaded as imports are not considered for this.
   * If and how this is set automatically when an ontology LR
   * is created depends on the implementation.
   * For an ontology LR that connects to an existing ontology repository,
   * an URL derived from the ontology repository location may be returned.
   * 
   * @return the URL of this ontology if set, or null otherwise
   */
  public URL getURL();

  /**
   * Set the URL of this ontology.
   * This URL set by this method will be returned by the {@link #getURL()}
   * method. The ontology store is not modified by this.
   * 
   * @param aUrl the url to be set
   */
  public void setURL(URL aUrl);


  /**
   * Saves the ontology in the specified File
   *
   * @param newOntology  a File object describing the file where to save to
   * @throws IOException 
   * @deprecated not implemented any more, throws UnsupportedOperationException
   */
  @Deprecated
  public void store(File newOntology) throws IOException;

  /**
   * Sets the default name space/base URI for the ontology.
   * This URI must end in "#" or "/". This URI is used when a new OURI
   * is created using the {@link #createOURIForName(String)} method.
   * Setting the default name space with this method does not change the
   * ontology store and does not add a default namespace declaration to the
   * store or when the ontology is saved.
   * 
   * @param aNameSpace a String that can be used as a base URI
   */
  public void setDefaultNameSpace(String aNameSpace);

  /**
   * Gets the default name space for this ontology.
   * This is used as the base URI for the ontology.
   * This returns the last value set with the method setDefaultNameSpace.
   * If the default name space was not set with this method, it is set
   * to a default value when an ontology is loaded in the following way:
   * If a base URI is specified for the loading, that base URI is used,
   * otherwise, if there was no ontology URI already set from a previous
   * load and this load determined exactly one ontology URI, that URI
   * will be used to set the default name space.
   * 
   * @return a String value.
   */
  public String getDefaultNameSpace();

  /**
   * Sets the version information for the ontology. 
   * 
   * @param theVersion the version to be set
   * @deprecated use method setOntologyAnnotation instead
   */
  @Deprecated
  public void setVersion(String theVersion);

  /**
   * Gets the version of this ontology.
   * 
   * @return the version of this ontology
   * @deprecated use method getOntologyAnnotationValues instead
   */
  @Deprecated
  public String getVersion();

  /**
   * Set an annotation property for the ontology to the specified literal
   * value.
   *
   * @param ann the annotation property object
   * @param value a Literal object describing the value. This usually should be
   * a String literal.
   * 
   */
  public void setOntologyAnnotation(AnnotationProperty ann, Literal value);

  /**
   * Get the values of an ontology annotation property.
   *
   * @param ann the annotation property object
   * @return a a list of literals describing the values for the property
   */
  public List<Literal> getOntologyAnnotationValues(AnnotationProperty ann);


  /**
   * Creates a new OClass and adds it the ontology.
   * 
   * @param aURI URI of this class
   * @param classType one of the values from
   *          OConstants.OCLASS_TYPE_OWL_CLASS and
   *          OConstants.OCLASS_TYPE_OWL_RESTRICTION;
   * @return the newly created class or an existing class if available
   *         with the same URI.
   * @deprecated - use one of the dedicated methods to add a named class
   *   or a restriction instead
   */
  @Deprecated
  public OClass addOClass(OURI aURI, byte classType);

  /**
   * Creates a new OWL Class and adds it the ontology.
   * If a class with that URI already exists, that class is returned.
   * 
   * @param aURI URI of this class
   * @return the newly created class or an existing class if available
   *         with the same URI.
   */
  public OClass addOClass(OURI aURI);
  
  
//  /**
//   * Retrieves a named class by its URI.
//   *
//   * @param theClassURI the URI of the class
//   * @return the class with that URI or null if no such class exists
//   */
//  public OClass getOClass(OURI theClassURI);

  /**
   * Retrieves a both named classes and anonymous classes and retrictions that
   * match either the URI or the blank node identifier represented by ONodeID
   * @param theClassID
   * @return the class matching the URI or blank node ID or null if no matches.
   */
  public OClass getOClass(ONodeID theClassID);

  /**
   * Removes a class from this ontology.
   * 
   * @param theClass the class to be removed
   */
  public void removeOClass(OClass theClass);

  /**
   * Checks whether a class with the specified URI or blank node ID
   * exists in the ontology.
   * 
   * @param theURI a ONodeID, usually an OURI specifying the ID of the
   * ontology class
   * @return true, if the class exists 
   */
  public boolean containsOClass(ONodeID theURI);

  /**
   * Checks whether the ontology contains this class.
   * 
   * @param theClass a ontology class object
   * @return true, if the class exists, otherwise - false.
   */
  public boolean containsOClass(OClass theClass);

  /**
   * Retrieves all ontology classes in a set.
   * This method returns a set of either all classes in the ontology or
   * just the "top" classes. A "top" class is a class that is not a subclass
   * of any other class that is not a predefined system class like owl:Thing
   * or rdfs:Resource or a trivial subclass (of itself or of a class that
   * is defined to be equivalent to itself).
   * <p>
   * NOTE: for large ontologies with a large number of classes it may be
   * preferable to use method {@link #getOClassesIterator(boolean)} instead.
   * 
   * @param top If set to true, only returns those classes with no super
   *          classes, otherwise - a set of all classes.
   * @return set of all the classes in this ontology
   */
  public Set<OClass> getOClasses(boolean top);


  /**
   * Return an iterator to retrieve all ontology classes in the ontology.
   * The iterator should be closed() as soon as it is not needed anymore
   * but will auto-close when it is exhausted and the hasNext() method
   * returned false.
   * 
   * @param top If set to true, only returns those classes with no
   *     super classes, otherwise all classes
   * @return a ClosableIterator for accessing the returned ontology classes
   */
  public ClosableIterator<OClass> getOClassesIterator(boolean top);
   

  /**
   * Gets the taxonomic distance between 2 classes.
   * 
   * @param class1 the first class
   * @param class2 the second class
   * @return the taxonomic distance between the 2 classes
   */
  public int getDistance(OClass class1, OClass class2);

  // *****************************
  // OInstance methods
  // *****************************
  /**
   * Creates a new OInstance and returns it.
   * 
   * @param theInstanceURI
   * @param theClass the class to which the instance belongs.
   * @return the OInstance that has been added to the ontology.
   */
  public OInstance addOInstance(OURI theInstanceURI, OClass theClass);

  /**
   * Removes the instance from the ontology.
   * 
   * @param theInstance to be removed
   */
  public void removeOInstance(OInstance theInstance);

  /**
   * Gets all instances in the ontology.
   * 
   * @return a {@link Set} of OInstance objects
   */
  public Set<OInstance> getOInstances();

  /*
   * Return an iterator for accessing all instances in the ontology.
   * @return
   */
  public ClosableIterator<OInstance> getOInstancesIterator();

  /**
   * Gets instances in the ontology, which belong to this class. 
   * 
   * @param theClass the class of the instances
   * @param closure either DIRECT_CLOSURE or TRANSITIVE_CLOSURE of
   *          {@link OConstants}
   * 
   * @return {@link Set} of OInstance objects
   */
  @Deprecated
  public Set<OInstance> getOInstances(OClass theClass, byte closure);

  /**
   * Gets instances in the ontology, which belong to this class.
   * The second parameter specifies if the the given class needs to be
   * a direct class of the instance (direct closure)
   * or a class to which the instance belongs
   * indirectly (transitive closure)
   *
   * @param theClass the class of the instances
   * @param closure either {@link OConstants.Closure#DIRECT_CLOSURE} or
   * {@link OConstants.Closure#TRANSITIVE_CLOSURE}
   *
   * @return {@link Set} of OInstance objects
   */
  public Set<OInstance> getOInstances(OClass theClass, OConstants.Closure closure);

  public ClosableIterator<OInstance>
      getOInstancesIterator(OClass theClass, OConstants.Closure closure);

  /**
   * Gets the instance with the given URI.
   * 
   * @param theInstanceURI the instance URI
   * @return the OInstance object with this URI. If there is no such
   *         instance then null.
   */
  public OInstance getOInstance(OURI theInstanceURI);

  /**
   * Checks whether the provided Instance exists in the ontology.
   * 
   * @param theInstance
   * @return true, if the Instance exists in ontology, otherwise -
   *         false.
   */
  public boolean containsOInstance(OInstance theInstance);

  /**
   * Checks whether the provided URI refers to an Instance that exists
   * in the ontology.
   * 
   * @param theInstanceURI
   * @return true, if the URI exists in ontology and refers to an
   *         Instance, otherwise - false.
   */
  public boolean containsOInstance(OURI theInstanceURI);

  // *****************************
  // Property definitions methods
  // *****************************
  /**
   * Creates a new RDFProperty.
   * 
   * @param aPropertyURI URI of the property to be added into the
   *          ontology.
   * @param domain a set of {@link OResource} (e.g. a Class, a Property
   *          etc.).
   * @param range a set of {@link OResource} (e.g. a Class, a Property
   *          etc.).
   * @deprecated
   */
  @Deprecated
  public RDFProperty addRDFProperty(OURI aPropertyURI, Set<OResource> domain,
          Set<OResource> range);

  /**
   * Gets the set of RDF Properties in the ontology where for a property
   * there exists a statement <theProperty, RDF:Type, RDF:Property>.
   * 
   * @return a {@link Set} of {@link RDFProperty}.
   */
  public Set<RDFProperty> getRDFProperties();

  /**
   * Checkes whether there exists a statement <thePropertyURI, RDF:Type,
   * RDF:Property> in the ontology or not.
   * 
   * @param thePropertyURI
   * @return true, only if there exists the above statement, otherwise -
   *         false.
   */
  public boolean isRDFProperty(OURI thePropertyURI);

  /**
   * Creates a new AnnotationProperty.
   * 
   * @param aPropertyURI URI of the property to be added into the
   *          ontology.
   */
  public AnnotationProperty addAnnotationProperty(OURI aPropertyURI);

  /**
   * Gets the set of Annotation Properties in the ontology where for a
   * property there exists a statement <theProperty, RDF:Type,
   * OWL:AnnotationProperty>.
   * 
   * @return a {@link Set} of {@link AnnotationProperty}.
   */
  public Set<AnnotationProperty> getAnnotationProperties();

  /**
   * Checkes whether there exists a statement <thePropertyURI, RDF:Type,
   * OWL:AnnotationProperty> in the ontology or not.
   * 
   * @param thePropertyURI
   * @return true, only if there exists the above statement, otherwise -
   *         false.
   */
  public boolean isAnnotationProperty(OURI thePropertyURI);

  /**
   * Create a DatatypeProperty with the given domain and range.
   *
   * The domain must be specified as a set of OClass objects, the resulting
   * domain is the intersection of all specified classes.
   * <p>
   * If this method is called with an OURI of a datatype property that
   * already exists, any specified domain class is added to the intersection
   * of classes already defined for the domain.
   * 
   * @param aPropertyURI the URI for the new property.
   * @param domain the set of ontology classes
   * that constitutes the domain for the new property.
   * @param aDatatype a DataType object describing the datatype of the range.
   * @return the newly created property.
   */
  public DatatypeProperty addDatatypeProperty(OURI aPropertyURI,
          Set<OClass> domain, DataType aDatatype);

  /**
   * Gets the set of Datatype Properties in the ontology.
   * 
   * @return a {@link Set} of {@link DatatypeProperty}.
   */
  public Set<DatatypeProperty> getDatatypeProperties();

  /**
   * Checkes whether the ontology contains a datatype property with the
   * given URI.
   * 
   * @param thePropertyURI
   * @return true if there is an instance of owl:DatatypeProperty with the
   * given URI in the ontology.
   */
  public boolean isDatatypeProperty(OURI thePropertyURI);

  /**
   * Creates a new object property (a property that takes instances as
   * values).
   * <p>
   * If this method is called with an OURI of an object property that
   * already exists, any specified domain or range class is added to
   * the intersection of classes already defined for the domain or range.
   * 
   * @param aPropertyURI the URI for the new property.
   * @param domain the set of ontology classes (i.e. {@link OClass}
   * objects} that constitutes the domain of the property.
   * An instance must belong to the intersection of all classes
   * in the set to be a valid member of the domain of the property.
   * If an empty set or null is passed, the instance can belong to any
   * class.
   * @param range the set of ontology classes (i.e. {@link OClass}
   * objects} that constitutes the range for the new property. The instance
   * that is the value of a property must belong to the intersection of
   * all classes in this set to be valid. If an empty set or null is passed
   * on, the instance can belong to any class.
   * @return the newly created property.
   */
  public ObjectProperty addObjectProperty(OURI aPropertyURI, Set<OClass> domain,
          Set<OClass> range);

  /**
   * Gets the set of Object Properties in the ontology.
   * 
   * @return a {@link Set} of {@link ObjectProperty}.
   */
  public Set<ObjectProperty> getObjectProperties();

  /**
   * Checks if there exists an object property with the given URI
   * 
   * @param thePropertyURI
   * @return true, only if there exists an object property with the
   * given URI.
   */
  public boolean isObjectProperty(OURI thePropertyURI);

  /**
   * Creates a new symmetric property (an object property that is
   * symmetric).
   * <p>
   * If this method is called with an OURI of a datatype property that
   * already exists, any specified domainAndRange class is added to the
   * intersection of classes already defined for the domain and range.
   * 
   * @param aPropertyURI the URI for the new property.
   * @param domainAndRange the set of ontology classes (i.e.
   *          {@link OClass} objects} that constitutes the domain and
   *          the range for the new property. The property only applies
   *          to instances that belong to <b>the intersection of all</b>
   * classes included
   *          in its domain. An empty set means that the property
   *          applies to instances of any class.
   * @return the newly created property.
   */
  public SymmetricProperty addSymmetricProperty(OURI aPropertyURI,
          Set<OClass> domainAndRange);

  /**
   * Gets the set of Symmetric Properties in the ontology.
   * 
   * @return a {@link Set} of {@link SymmetricProperty}.
   */
  public Set<SymmetricProperty> getSymmetricProperties();

  /**
   * Checkes whether there exists a statement <thePropertyURI, RDF:Type,
   * OWL:SymmetricProperty> in the ontology or not.
   * 
   * @param thePropertyURI
   * @return true, only if there exists the above statement, otherwise -
   *         false.
   */
  public boolean isSymmetricProperty(OURI thePropertyURI);

  /**
   * Creates a new transitive property (an object property that is
   * transitive).
   * <p>
   * If this method is called with an OURI of a transitive property that
   * already exists, any specified domain or range class is added to the intersection
   * of classes already defined for the domain or range.
   *
   * @param aPropertyURI the URI for the new property.
   * @param domain the set of ontology classes (i.e. {@link OClass}
   *          objects} that constitutes the range for the new property.
   *          The property only applies to instances that belong to
   *          <b>the intersection of all</b> classes included in its domain.
   * An empty set
   *          means that the property applies to instances of any class.
   * @param range the set of ontology classes (i.e. {@link OClass}
   *          objects} that constitutes the range for the new property.
   * @return the newly created property.
   */
  public TransitiveProperty addTransitiveProperty(OURI aPropertyURI,
          Set<OClass> domain, Set<OClass> range);

  /**
   * Gets the set of Transitive Properties in the ontology.
   * 
   * @return a {@link Set} of {@link TransitiveProperty}.
   */
  public Set<TransitiveProperty> getTransitiveProperties();

  /**
   * Checkes whether there exists a statement <thePropertyURI, RDF:Type,
   * OWL:TransitiveProperty> in the ontology or not.
   * 
   * @param thePropertyURI
   * @return true, only if there exists the above statement, otherwise -
   *         false.
   */
  public boolean isTransitiveProperty(OURI thePropertyURI);

  /**
   * Gets the set of RDF, Object, Datatype, Symmetric and Transitive
   * property definitions in this ontology.
   * 
   * @return a {@link Set} of {@link RDFProperty},
   *         {@link DatatypeProperty}, {@link ObjectProperty},
   *         {@link TransitiveProperty} and , {@link SymmetricProperty}
   *         objects. <B>Please note that the method does not include
   *         annotation properties</B>.
   */
  public Set<RDFProperty> getPropertyDefinitions();

  /**
   * Returns the property for a given URI or null if there is no property
   * with that URI.
   * 
   * @param thePropertyURI the URI of the property
   * @return the RDFProperty object or null if no property with that URI is found
   */
  public RDFProperty getProperty(OURI thePropertyURI);

  /**
   * Returns the annotation property for the given URI or null if there is
   * no annotation property with that URI.
   *
   * @param theURI the URI of the property
   * @return the AnnotationProperty obejct
   */
  public AnnotationProperty getAnnotationProperty(OURI theURI);

  /**
   * Returns the datatype property for the given URI or null if there is
   * no datatype property with that URI.
   *
   * @param theURI the URI of the property
   * @return the DatatypeProperty obejct
   */
  public DatatypeProperty getDatatypeProperty(OURI theURI);

  /**
   * Returns the object property for the given URI or null if there is
   * no object property with that URI.
   *
   * @param theURI the URI of the property
   * @return the AnnotationProperty obejct
   */
  public ObjectProperty getObjectProperty(OURI theURI);


  /**
   * A method to remove the existing propertyDefinition (exclusive of
   * Annotation Property).
   * 
   * @param theProperty
   */
  public void removeProperty(RDFProperty theProperty);

  // *****************************
  // Restrictions
  // *****************************

  /**
   * Adds a new MinCardinality Restriction to the ontology. It
   * automatically creates a randon anonymous class, which it uses to
   * denote the restriction. The default datatype is set to NonNegativeIntegerNumber
   * 
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param minCardinalityValue - generally a numeric number.
   * @throws InvalidValueException - if a value is not compatible with the nonNegativeIntegerNumber datatype.
   */
  public MinCardinalityRestriction addMinCardinalityRestriction(
          RDFProperty onProperty, String minCardinalityValue)
          throws InvalidValueException;
  
  /**
   * Adds a new MaxCardinality Restriction to the ontology. It
   * automatically creates a randon anonymous class, which it uses to
   * denote the restriction. The default datatype is set to NonNegativeIntegerNumber
   * 
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param maxCardinalityValue - generally a numeric number.
   * @throws InvalidValueException - if a value is not compatible with the nonNegativeIntegerNumber datatype.
   */
  public MaxCardinalityRestriction addMaxCardinalityRestriction(
          RDFProperty onProperty, String maxCardinalityValue)
          throws InvalidValueException;

  /**
   * Adds a new Cardinality Restriction to the ontology. It
   * automatically creates a randon anonymous class, which it uses to
   * denote the restriction. The default datatype is set to NonNegativeIntegerNumber
   * 
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param cardinalityValue - generally a numeric number.
   * @throws InvalidValueException - if a value is not compatible with the nonNegativeIntegerNumber datatype.
   */
  public CardinalityRestriction addCardinalityRestriction(
          RDFProperty onProperty, String cardinalityValue)
          throws InvalidValueException;

  /**
   * Adds a new HasValue Restriction to the ontology. It
   * automatically creates a randon anonymous class, which it uses to
   * denote the restriction.
   * 
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param hasValue - a resource used as a value for hasValue element of the restriction.
   */
  public HasValueRestriction addHasValueRestriction(
          RDFProperty onProperty, OResource hasValue);

  /**
   * Adds a new AllValuesFrom Restriction to the ontology.
   *
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param hasValue - a resource used as a value for hasValue element of the restriction.
   * @deprecated - this method is deprecated and kept for backwards compatibility
   * as long as the OntologyOWLIM2 plugin is kept. Use the method
   * {@link #addAllValuesFromRestriction(ObjectProperty, OClass)} instead.
   */
  @Deprecated
  public AllValuesFromRestriction addAllValuesFromRestriction(
          RDFProperty onProperty, OResource hasValue);

  public AllValuesFromRestriction addAllValuesFromRestriction(
      ObjectProperty onProperty, OClass theClass);


  /**
   * Adds a new AllValuesFrom Restriction to the ontology. It
   * automatically creates a randon anonymous class, which it uses to
   * denote the restriction.
   * 
   * @param onProperty - Specifies the property for which the restriction is being set.
   * @param hasValue - a resource used as a value for hasValue element of the restriction.
   */
  public SomeValuesFromRestriction addSomeValuesFromRestriction(
          RDFProperty onProperty, OResource hasValue);


  public AnonymousClass addAnonymousClass();
  
  // *****************************
  // Ontology Modification Events
  // *****************************
  /**
   * Sets the modified flag.
   * 
   * @param isModified sets this param as a value of the modified
   *          property of the ontology
   */
  public void setModified(boolean isModified);

  /**
   * Checks the modified flag.
   * 
   * @return whether the ontology has been modified after the loading
   */
  @Override
  public boolean isModified();

  /**
   * Register the Ontology Modification Listeners
   */
  public void addOntologyModificationListener(OntologyModificationListener oml);

  /**
   * Removed the registered ontology modification listeners
   */
  public void removeOntologyModificationListener(
          OntologyModificationListener oml);

  /**
   * A method to invoke when a resource's property value is changed
   */
  public void fireResourcePropertyValueChanged(OResource resource, RDFProperty property, Object value, int eventType);

  /**
   * A method to invoke when a resource's property value is changed
   * 
   * @param resource1
   * @param resource2
   * @param eventType
   */
  public void fireResourceRelationChanged(OResource resource1, OResource resource2,int eventType);

  /**
   * A Method to invoke an event for newly added ontology resource
   * 
   * @param resource
   */
  public void fireOntologyResourceAdded(OResource resource);

  /**
   * A Method to invoke an event for a removed ontology resource
   * 
   * @param resources 
   */
  public void fireOntologyResourcesRemoved(String[] resources);

  /**
   * A method to invoke when the ontology is reset.
   * 
   */
  public void fireOntologyReset();

  // *************************
  // Sesame Repository methods
  // *************************
  /**
   * Start the transaction before additing statements.
   */

  /**
   * Commit all changes to the ontology.
   * This will commit all changesall the transaction (so far included after the call to
   * start transaction) into the repository.
   *
   * @deprecated
   */
  @Deprecated
  public void commitTransaction();

  /**
   * Checks whether the transation is already started.
   * @deprecated 
   */
  @Deprecated
  public boolean transationStarted();

  /**
   * Returns the repository created for this particular instance of the
   * ontology.
   * @deprecated
   */
  @Deprecated
  public Object getSesameRepository();

  /**
   * Returns the ID of a Sesame Repository created for this particular
   * instance of the ontology.
   * @deprecated
   */
  @Deprecated
  public String getSesameRepositoryID();



  /**
   * Given a URI object, this method returns its equivalent object
   * @deprecated
   */
  @Deprecated
  public OResource getOResourceFromMap(String uri);

  /**
   * Adds the resource to central map
   * @deprecated
   */
  @Deprecated
  public void addOResourceToMap(String uri, OResource resource);

  /**
   * Removes the resource from the central map
   * 
   * @param uri
   * @deprecated 
   */
  @Deprecated
  public void removeOResourceFromMap(String uri);

  /**
   * This method checks in its cache to find out the OResource for the
   * given resource name. However, It is also possible for two resources
   * to have a same name but different name spaces. This method returns
   * the first found OResource (without guaranteeing the order) from its
   * list. If user wants to retrieve a list of resources, he/she must
   * use the getOResourcesByName(String resourceName).
   * @deprecated
   */
  @Deprecated
  public OResource getOResourceByName(String resourceName);

  /**
   * This method checks in its cache to find out the OResources for the
   * given resource name. It is possible for two resources to have a
   * same name but different name spaces. This method returns a list of
   * resources with the common name. Please note that deleting an
   * instance from this list (e.g. list.remove(int/Object)) does not
   * delete the resource from an ontology. One must use appropriate
   * method from the Ontology interface to delete such resources.
   */
  public List<OResource> getOResourcesByName(String resourceName);

  /**
   * This method returns a list of OResources from the ontology. Please
   * note that deleting an instance from this list (e.g.
   * list.remove(int/Object)) does not delete the resource from an
   * ontology. One must use appropriate method from the Ontology
   * interface to delete such resources.
   * 
   * @deprecated
   */
  @Deprecated
  public List<OResource> getAllResources();

  /**
   * This method given a property (either an annotation or datatype),
   * retrieves a list of resources which have the provided literal set
   * as a value.
   */
  public List<OResource> getOResourcesWith(RDFProperty aProperty, Literal aValue);

  /**
   * This method given a property (either object, transitive, symmetric
   * or rdf), retrieves a list of resources which have the provided
   * resource set as a value.
   */
  public List<OResource> getOResourcesWith(RDFProperty aProperty,
          OResource aValue);

  /**
   * The method executes the query on repository and returns the toString()
   * result of the QueryResultTable.
   *
   * @deprecated
   */
  @Deprecated
  public String executeQuery(String serqlQuery);

  /**
   * This method creates a OntologyTupleQuery object and passes on
   * the specified query string in the specified query language.
   *
   * @param theQuery a String representing the tuple query.
   * @param queryLanguage the query language, either SERQL or SPARQL
   * @return the OntologyTurpleQuery object that can be used to retrieve
   * the matching tuples in several formats. The query object can be re-used
   * with different variable bindings.
   */
  public OntologyTupleQuery createTupleQuery(String theQuery, QueryLanguage queryLanguage);

  /**
   * This method creates a OntologyBooleanQuery object and passes on the
   * specified query string and the specified query language.
   *
   * @param theQuery a String representing the tuple query.
   * @param queryLanguage the query language, either SERQL or SPARQL
   * @return the OntologyBooleanQuery object that can be used to retrieve
   * the boolean answer for the query. The query object can be re-used
   * with different variable bindings.
   */
  public OntologyBooleanQuery createBooleanQuery(String theQuery, QueryLanguage queryLanguage);

  /**
   * Return an object representing the triple store which backs the ontology.
   * 
   * @return The OntologyTripleStore object for this ontology or null if
   * modifying the ontology via triples is not supported by the implementation.
   */
  public abstract OntologyTripleStore getOntologyTripleStore();



  /**
   * Create an ORUI object from the given URI string.
   *
   * @param theURI an URI or IRI string
   * @return the OURI object representing the URI/IRI
   */
  public OURI createOURI(String theURI);

  /**
   * Create an OURI from the given resource name, using the ontology base URI
   * (default name space). This method will throw an exception if no
   * default name space is set (i.e. if the method getDefaultNameSpace would
   * return null).
   *
   * @param resourceName the resource name i.e. the part of the final URI/IRI
   * that is attached to the base URI (aftaer a trailing "#" or "/").
   * @return the OURI 
   */
  public OURI createOURIForName(String resourceName);


  /** Generate a new unique OURI for this ontology.
   * This generates a new OURI that is guaranteed to be unique in the
   * ontology, using the specified resource name string as a prefix for the
   * URI's fragement identifier and the current defaultNameSpace of the
   * ontology as a base URI.
   * The resource name can be null or the empty string if no prefix is
   * wanted.
   * <p>
   * The URI fragment part that is appended to the given resourceName (if any)
   * consists of 7 ASCII characters representing the system time  in radix 36
   * followed by 3 ASCII characters representing a random number.
   * <p>
   * Note that this method will return an OURI that is guaranteed not
   * to be contained in the ontology, but if called repeatedly without
   * actually adding a resource with the newly generated OURI to the ontology,
   * the method might, although extremely unlikely,
   * still return the same OURI more than once. Generated OURIs should hence
   * been used for adding resources before more OURIs are generated.
   * 
   * @param resourceNamePrefix the prefix to use for the generated resource
   * name
   * @return a OURI object for an URI that is new and unique in the ontology
   */
  public OURI generateOURI(String resourceNamePrefix);

  /** Generate a new unique OURI for this ontology.
   * This works in the same way as {@link #generateOURI(java.lang.String) }
   * but also allows the specification of the base URI part of the final
   * URI.
   *
   * @param resourceNamePrefix the prefix to use for the generated resource name
   * @param baseURI the base URI to use for the final URI
   * @return a OURI object for an URI that is new and unique in the ontology
   */
  public OURI generateOURI(String resourceNamePrefix, String baseURI);

}
