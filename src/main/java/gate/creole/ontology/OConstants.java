/*
 *  OConstants.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OConstants.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import java.util.Locale;

/**
 * This interface holds some constants used by several other intrfaces
 * and classes in the GATE ontology API.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
public interface OConstants {

  /// **********************************
  /// Ontology Event Log feature name
  /// **********************************
  /** denotes the name of the features for ontology event log */
  public static final String ONTOLOGY_EVENT_LOG_FEATURE_NAME = "eventLog";

  /**
   * All predefined URIs from the RDF name space.
   */
  public static class RDF {
    // class adapted from Sesame 2.3,
    // Copyright Aduna (http://www.aduna-software.com/) <A9> 2001-2009
    // All rights reserved.

    /** http://www.w3.org/1999/02/22-rdf-syntax-ns# */
    public static final String NAMESPACE =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#type */
    public final static String TYPE =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Property */
    public final static String PROPERTY =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#Property";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral */
    public final static String XMLLITERAL =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#subject */
    public final static String SUBJECT =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#subject";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate */
    public final static String PREDICATE =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#object */
    public final static String OBJECT =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#object";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement */
    public final static String STATEMENT =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag */
    public final static String BAG =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt */
    public final static String ALT =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq */
    public final static String SEQ =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#value */
    public final static String VALUE =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#value";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#li */
    public final static String LI =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#li";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#List */
    public final static String LIST =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#List";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#first */
    public final static String FIRST =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#first";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#rest */
    public final static String REST =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#rest";
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#nil */
    public final static String NIL =
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#nil";
  }

  /**
   * All predefined URIs from the OWL1 namespace.
   */
  public static class OWL {
    // class adapted from Sesame 2.3,
    // Copyright Aduna (http://www.aduna-software.com/) <A9> 2001-2009
    // All rights reserved.

    /** http://www.w3.org/2002/07/owl# */
    public static final String NAMESPACE =
        "http://www.w3.org/2002/07/owl#";
    // OWL Lite
    /** http://www.w3.org/2002/07/owl#Class */
    public final static String CLASS =
        "http://www.w3.org/2002/07/owl#Class";
    /** http://www.w3.org/2002/07/owl#Individual */
    public final static String INDIVIDUAL =
        "http://www.w3.org/2002/07/owl#Individual";
    /** http://www.w3.org/2002/07/owl#equivalentClass */
    public final static String EQUIVALENTCLASS =
        "http://www.w3.org/2002/07/owl#equivalentClass";
    /** http://www.w3.org/2002/07/owl#equivalentProperty */
    public final static String EQUIVALENTPROPERTY =
        "http://www.w3.org/2002/07/owl#equivalentProperty";
    /** http://www.w3.org/2002/07/owl#sameAs */
    public final static String SAMEAS =
        "http://www.w3.org/2002/07/owl#sameAs";
    /** http://www.w3.org/2002/07/owl#differentFrom */
    public final static String DIFFERENTFROM =
        "http://www.w3.org/2002/07/owl#differentFro";
    /** http://www.w3.org/2002/07/owl#AllDifferent */
    public final static String ALLDIFFERENT =
        "http://www.w3.org/2002/07/owl#AllDifferent";
    /** http://www.w3.org/2002/07/owl#distinctMembers */
    public final static String DISTINCTMEMBERS =
        "http://www.w3.org/2002/07/owl#distinctMembers";
    /** http://www.w3.org/2002/07/owl#ObjectProperty */
    public final static String OBJECTPROPERTY =
        "http://www.w3.org/2002/07/owl#ObjectProperty";
    /** http://www.w3.org/2002/07/owl#DatatypeProperty */
    public final static String DATATYPEPROPERTY =
        "http://www.w3.org/2002/07/owl#DatatypeProperty";
    /** http://www.w3.org/2002/07/owl#inverseOf */
    public final static String INVERSEOF =
        "http://www.w3.org/2002/07/owl#inverseOf";
    /** http://www.w3.org/2002/07/owl#TransitiveProperty */
    public final static String TRANSITIVEPROPERTY =
        "http://www.w3.org/2002/07/owl#TransitiveProperty";
    /** http://www.w3.org/2002/07/owl#SymmetricProperty */
    public final static String SYMMETRICPROPERTY =
        "http://www.w3.org/2002/07/owl#SymmetricProperty";
    /** http://www.w3.org/2002/07/owl#FunctionalProperty */
    public final static String FUNCTIONALPROPERTY =
        "http://www.w3.org/2002/07/owl#FunctionalProperty";
    /** http://www.w3.org/2002/07/owl#InverseFunctionalProperty */
    public final static String INVERSEFUNCTIONALPROPERTY =
        "http://www.w3.org/2002/07/owl#InverseFunctionalProperty";
    /** http://www.w3.org/2002/07/owl#Restriction */
    public final static String RESTRICTION =
        "http://www.w3.org/2002/07/owl#Restriction";
    /** http://www.w3.org/2002/07/owl#onProperty */
    public final static String ONPROPERTY =
        "http://www.w3.org/2002/07/owl#onProperty";
    /** http://www.w3.org/2002/07/owl#allValuesFrom */
    public final static String ALLVALUESFROM =
        "http://www.w3.org/2002/07/owl#allValuesFrom";
    /** http://www.w3.org/2002/07/owl#someValuesFrom */
    public final static String SOMEVALUESFROM =
        "http://www.w3.org/2002/07/owl#someValuesFrom";
    /** http://www.w3.org/2002/07/owl#minCardinality */
    public final static String MINCARDINALITY =
        "http://www.w3.org/2002/07/owl#minCardinality";
    /** http://www.w3.org/2002/07/owl#maxCardinality */
    public final static String MAXCARDINALITY =
        "http://www.w3.org/2002/07/owl#maxCardinality";
    /** http://www.w3.org/2002/07/owl#cardinality */
    public final static String CARDINALITY =
        "http://www.w3.org/2002/07/owl#cardinality";
    /** http://www.w3.org/2002/07/owl#Ontology */
    public final static String ONTOLOGY =
        "http://www.w3.org/2002/07/owl#Ontology";
    /** http://www.w3.org/2002/07/owl#imports */
    public final static String IMPORTS =
        "http://www.w3.org/2002/07/owl#imports";
    /** http://www.w3.org/2002/07/owl#intersectionOf */
    public final static String INTERSECTIONOF =
        "http://www.w3.org/2002/07/owl#intersectionOf";
    /** http://www.w3.org/2002/07/owl#versionInfo */
    public final static String VERSIONINFO =
        "http://www.w3.org/2002/07/owl#versionInfo";
    /** http://www.w3.org/2002/07/owl#priorVersion */
    public final static String PRIORVERSION =
        "http://www.w3.org/2002/07/owl#priorVersion";
    /** http://www.w3.org/2002/07/owl#backwardCompatibleWith */
    public final static String BACKWARDCOMPATIBLEWITH =
        "http://www.w3.org/2002/07/owl#backwardCompatibleWith";
    /** http://www.w3.org/2002/07/owl#incompatibleWith */
    public final static String INCOMPATIBLEWITH =
        "http://www.w3.org/2002/07/owl#incompatibleWith";
    /** http://www.w3.org/2002/07/owl#DeprecatedClass */
    public final static String DEPRECATEDCLASS =
        "http://www.w3.org/2002/07/owl#DeprecatedClass";
    /** http://www.w3.org/2002/07/owl#DeprecatedProperty */
    public final static String DEPRECATEDPROPERTY =
        "http://www.w3.org/2002/07/owl#DeprecatedProperty";
    /** http://www.w3.org/2002/07/owl#AnnotationProperty */
    public final static String ANNOTATIONPROPERTY =
        "http://www.w3.org/2002/07/owl#AnnotationProperty";
    /** http://www.w3.org/2002/07/owl#OntologyProperty */
    public final static String ONTOLOGYPROPERTY =
        "http://www.w3.org/2002/07/owl#OntologyProperty";
    // OWL DL and OWL Full
    /** http://www.w3.org/2002/07/owl#oneOf */
    public final static String ONEOF =
        "http://www.w3.org/2002/07/owl#oneOf";
    /** http://www.w3.org/2002/07/owl#hasValue */
    public final static String HASVALUE =
        "http://www.w3.org/2002/07/owl#hasValue";
    /** http://www.w3.org/2002/07/owl#disjointWith */
    public final static String DISJOINTWITH =
        "http://www.w3.org/2002/07/owl#disjointWith";
    /** http://www.w3.org/2002/07/owl#unionOf */
    public final static String UNIONOF =
        "http://www.w3.org/2002/07/owl#unionOf";
    /** http://www.w3.org/2002/07/owl#complementOf */
    public final static String COMPLEMENTOF =
        "http://www.w3.org/2002/07/owl#complementOf";
  }

  /**
   * All predefined URIs from the RDFS name space.
   */
  public static class RDFS {
    // class adapted from Sesame 2.3,
    // Copyright Aduna (http://www.aduna-software.com/) <A9> 2001-2009
    // All rights reserved.

    /** http://www.w3.org/2000/01/rdf-schema# */
    public static final String NAMESPACE =
        "http://www.w3.org/2000/01/rdf-schema#";
    /** http://www.w3.org/2000/01/rdf-schema#Resource */
    public final static String RESOURCE =
        "http://www.w3.org/2000/01/rdf-schema#Resource";
    /** http://www.w3.org/2000/01/rdf-schema#Literal */
    public final static String LITERAL =
        "http://www.w3.org/2000/01/rdf-schema#Literal";
    /** http://www.w3.org/2000/01/rdf-schema#Class */
    public final static String CLASS =
        "http://www.w3.org/2000/01/rdf-schema#Class";
    /** http://www.w3.org/2000/01/rdf-schema#subClassOf */
    public final static String SUBCLASSOF =
        "http://www.w3.org/2000/01/rdf-schema#subClassOf";
    /** http://www.w3.org/2000/01/rdf-schema#subPropertyOf */
    public final static String SUBPROPERTYOF =
        "http://www.w3.org/2000/01/rdf-schema#subPropertyOf";
    /** http://www.w3.org/2000/01/rdf-schema#domain */
    public final static String DOMAIN =
        "http://www.w3.org/2000/01/rdf-schema#domain";
    /** http://www.w3.org/2000/01/rdf-schema#range */
    public final static String RANGE =
        "http://www.w3.org/2000/01/rdf-schema#range";
    /** http://www.w3.org/2000/01/rdf-schema#comment */
    public final static String COMMENT =
        "http://www.w3.org/2000/01/rdf-schema#comment";
    /** http://www.w3.org/2000/01/rdf-schema#label */
    public final static String LABEL =
        "http://www.w3.org/2000/01/rdf-schema#label";
    /** http://www.w3.org/2000/01/rdf-schema#Datatype */
    public final static String DATATYPE =
        "http://www.w3.org/2000/01/rdf-schema#Datatype";
    /** http://www.w3.org/2000/01/rdf-schema#Container */
    public final static String CONTAINER =
        "http://www.w3.org/2000/01/rdf-schema#Container";
    /** http://www.w3.org/2000/01/rdf-schema#member */
    public final static String MEMBER =
        "http://www.w3.org/2000/01/rdf-schema#member";
    /** http://www.w3.org/2000/01/rdf-schema#isDefinedBy */
    public final static String ISDEFINEDBY =
        "http://www.w3.org/2000/01/rdf-schema#isDefinedBy";
    /** http://www.w3.org/2000/01/rdf-schema#seeAlso */
    public final static String SEEALSO =
        "http://www.w3.org/2000/01/rdf-schema#seeAlso";
    /** http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty */
    public final static String CONTAINERMEMBERSHIPPROPERTY =
        "http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty";
  }

  public class XMLSchema {
    // class adapted from Sesame 2.3,
    // Copyright Aduna (http://www.aduna-software.com/) <A9> 2001-2009
    // All rights reserved.

    /** The XML Schema namespace http://www.w3.org/2001/XMLSchema# */
    public static final String NAMESPACE = "http://www.w3.org/2001/XMLSchema#";

    /*
     * Primitive datatypes
     */
    /** http://www.w3.org/2001/XMLSchema#duration */
    public final static String DURATION =
        "http://www.w3.org/2001/XMLSchema#duration";
    /** http://www.w3.org/2001/XMLSchema#dateTime */
    public final static String DATETIME =
        "http://www.w3.org/2001/XMLSchema#dateTime";
    /** http://www.w3.org/2001/XMLSchema#time */
    public final static String TIME =
        "http://www.w3.org/2001/XMLSchema#time";
    /** http://www.w3.org/2001/XMLSchema#date */
    public final static String DATE =
        "http://www.w3.org/2001/XMLSchema#date";
    /** http://www.w3.org/2001/XMLSchema#gYearMonth */
    public final static String GYEARMONTH =
        "http://www.w3.org/2001/XMLSchema#gYearMonth";
    /** http://www.w3.org/2001/XMLSchema#gYear */
    public final static String GYEAR =
        "http://www.w3.org/2001/XMLSchema#gYear";
    /** http://www.w3.org/2001/XMLSchema#gMonthDay */
    public final static String GMONTHDAY =
        "http://www.w3.org/2001/XMLSchema#gMonthDay";
    /** http://www.w3.org/2001/XMLSchema#gDay */
    public final static String GDAY =
        "http://www.w3.org/2001/XMLSchema#gDay";
    /** http://www.w3.org/2001/XMLSchema#gMonth */
    public final static String GMONTH =
        "http://www.w3.org/2001/XMLSchema#gMonth";
    /** http://www.w3.org/2001/XMLSchema#string */
    public final static String STRING =
        "http://www.w3.org/2001/XMLSchema#string";
    /** http://www.w3.org/2001/XMLSchema#boolean */
    public final static String BOOLEAN =
        "http://www.w3.org/2001/XMLSchema#boolean";
    /** http://www.w3.org/2001/XMLSchema#base64Binary */
    public final static String BASE64BINARY =
        "http://www.w3.org/2001/XMLSchema#base64Binary";
    /** http://www.w3.org/2001/XMLSchema#hexBinary */
    public final static String HEXBINARY =
        "http://www.w3.org/2001/XMLSchema#hexBinary";
    /** http://www.w3.org/2001/XMLSchema#float */
    public final static String FLOAT =
        "http://www.w3.org/2001/XMLSchema#float";
    /** http://www.w3.org/2001/XMLSchema#decimal */
    public final static String DECIMAL =
        "http://www.w3.org/2001/XMLSchema#decimal";
    /** http://www.w3.org/2001/XMLSchema#double */
    public final static String DOUBLE =
        "http://www.w3.org/2001/XMLSchema#double";
    /** http://www.w3.org/2001/XMLSchema#anyURI */
    public final static String ANYURI =
        "http://www.w3.org/2001/XMLSchema#anyURI";
    /** http://www.w3.org/2001/XMLSchema#QName */
    public final static String QNAME =
        "http://www.w3.org/2001/XMLSchema#QName";
    /** http://www.w3.org/2001/XMLSchema#NOTATION */
    public final static String NOTATION =
        "http://www.w3.org/2001/XMLSchema#NOTATION";

    /*
     * Derived datatypes
     */
    /** http://www.w3.org/2001/XMLSchema#normalizedString */
    public final static String NORMALIZEDSTRING =
        "http://www.w3.org/2001/XMLSchema#normalizedString";
    /** http://www.w3.org/2001/XMLSchema#token */
    public final static String TOKEN =
        "http://www.w3.org/2001/XMLSchema#token";
    /** http://www.w3.org/2001/XMLSchema#language */
    public final static String LANGUAGE =
        "http://www.w3.org/2001/XMLSchema#language";
    /** http://www.w3.org/2001/XMLSchema#NMTOKEN */
    public final static String NMTOKEN =
        "http://www.w3.org/2001/XMLSchema#NMTOKEN";
    /** http://www.w3.org/2001/XMLSchema#NMTOKENS */
    public final static String NMTOKENS =
        "http://www.w3.org/2001/XMLSchema#NMTOKENS";
    /** http://www.w3.org/2001/XMLSchema#Name */
    public final static String NAME =
        "http://www.w3.org/2001/XMLSchema#Name";
    /** http://www.w3.org/2001/XMLSchema#NCName */
    public final static String NCNAME =
        "http://www.w3.org/2001/XMLSchema#NCName";
    /** http://www.w3.org/2001/XMLSchema#ID */
    public final static String ID =
        "http://www.w3.org/2001/XMLSchema#ID";
    /** http://www.w3.org/2001/XMLSchema#IDREF */
    public final static String IDREF =
        "http://www.w3.org/2001/XMLSchema#IDREF";
    /** http://www.w3.org/2001/XMLSchema#IDREFS */
    public final static String IDREFS =
        "http://www.w3.org/2001/XMLSchema#IDREFS";
    /** http://www.w3.org/2001/XMLSchema#ENTITY */
    public final static String ENTITY =
        "http://www.w3.org/2001/XMLSchema#ENTITY";
    /** http://www.w3.org/2001/XMLSchema#ENTITIES */
    public final static String ENTITIES =
        "http://www.w3.org/2001/XMLSchema#ENTITIES";
    /** http://www.w3.org/2001/XMLSchema#integer */
    public final static String INTEGER =
        "http://www.w3.org/2001/XMLSchema#integer";
    /** http://www.w3.org/2001/XMLSchema#long */
    public final static String LONG =
        "http://www.w3.org/2001/XMLSchema#long";
    /** http://www.w3.org/2001/XMLSchema#int */
    public final static String INT =
        "http://www.w3.org/2001/XMLSchema#int";
    /** http://www.w3.org/2001/XMLSchema#short */
    public final static String SHORT =
        "http://www.w3.org/2001/XMLSchema#short";
    /** http://www.w3.org/2001/XMLSchema#byte */
    public final static String BYTE =
        "http://www.w3.org/2001/XMLSchema#byte";
    /** http://www.w3.org/2001/XMLSchema#nonPositiveInteger */
    public final static String NON_POSITIVE_INTEGER =
        "http://www.w3.org/2001/XMLSchema#nonPositiveInteger";
    /** http://www.w3.org/2001/XMLSchema#negativeInteger */
    public final static String NEGATIVE_INTEGER =
        "http://www.w3.org/2001/XMLSchema#negativeInteger";
    /** http://www.w3.org/2001/XMLSchema#nonNegativeInteger */
    public final static String NON_NEGATIVE_INTEGER =
        "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    /** http://www.w3.org/2001/XMLSchema#positiveInteger */
    public final static String POSITIVE_INTEGER =
        "http://www.w3.org/2001/XMLSchema#positiveInteger";
    /** http://www.w3.org/2001/XMLSchema#unsignedLong */
    public final static String UNSIGNED_LONG =
        "http://www.w3.org/2001/XMLSchema#unsignedLong";
    /** http://www.w3.org/2001/XMLSchema#unsignedInt */
    public final static String UNSIGNED_INT =
        "http://www.w3.org/2001/XMLSchema#unsignedInt";
    /** http://www.w3.org/2001/XMLSchema#unsignedShort */
    public final static String UNSIGNED_SHORT =
        "http://www.w3.org/2001/XMLSchema#unsignedShort";
    /** http://www.w3.org/2001/XMLSchema#unsignedByte */
    public final static String UNSIGNED_BYTE =
        "http://www.w3.org/2001/XMLSchema#unsignedByte";
  }
  /// ***********************************
  /// Closure types 
  /// ***********************************

  /**
   * The set of entities directly connected.
   * 
   * @deprecated use {@link Closure} instead.
   */
  @Deprecated
  public static final byte DIRECT_CLOSURE = 0;

  /**
   * The set of entities directly or indirectly connected.
   *
   * @deprecated use {@link Closure} instead.
   */
  @Deprecated
  public static final byte TRANSITIVE_CLOSURE = 1;

  /**
   * Closure constants. 
   */
  public static enum Closure {
    DIRECT_CLOSURE,
    TRANSITIVE_CLOSURE
  }
  /// Default base URI
  public static final String ONTOLOGY_DEFAULT_BASE_URI = "http://gate.ac.uk/owlim#";
  /// ***********************************
  /// Property types 
  /// ***********************************
  /**
   * denotes the rdf property
   */
  public static final byte RDF_PROPERTY = 0;
  /**
   * denotes the object property.
   */
  public static final byte OBJECT_PROPERTY = 1;
  /**
   * denotes the datatype property.
   */
  public static final byte DATATYPE_PROPERTY = 2;
  /**
   * denotes the symmetric property.
   */
  public static final byte SYMMETRIC_PROPERTY = 3;
  /**
   * denotes the transitive property.
   */
  public static final byte TRANSITIVE_PROPERTY = 4;
  /**
   * denotes the annotation property.
   */
  public static final byte ANNOTATION_PROPERTY = 5;
  /// ***********************************
  /// Class types 
  /// ***********************************
  /**
   * specifies that the value of y in the x rdf:type y is owl:class
   */
  public static final byte OWL_CLASS = 0;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that is ia cardinality restriction
   */
  public static final byte CARDINALITY_RESTRICTION = 1;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a min cardinality restriction
   */
  public static final byte MIN_CARDINALITY_RESTRICTION = 2;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a max cardinality restriction
   */
  public static final byte MAX_CARDINALITY_RESTRICTION = 3;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a hasValue restriction
   */
  public static final byte HAS_VALUE_RESTRICTION = 4;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a allValuesFrom restriction
   */
  public static final byte ALL_VALUES_FROM_RESTRICTION = 5;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a someValuesFrom restriction
   */
  public static final byte SOME_VALUES_FROM_RESTRICTION = 6;
  /**
   * specifies that the value of y in the x rdf:type is owl:restriction and that it is a someValuesFrom restriction
   */
  public static final byte ANNONYMOUS_CLASS = 7;
  /**
   * specifies that it is an instance
   */
  public static final byte INSTANCE = 8;

  /// ***********************************
  /// Ontology Types 
  /// ***********************************
  /**
   * A list of serialization formats for ontologies. Which of these
   * formats are supported for loading, importing or saving ontologies
   * is implementation-dependent.
   */
  public static enum OntologyFormat {

    RDFXML() {

      @Override
      public String toString() {
        return "rdfxml";
      }
    },
    TURTLE() {

      @Override
      public String toString() {
        return "turtle";
      }
    },
    N3() {

      @Override
      public String toString() {
        return "n3";
      }
    },
    NTRIPLES() {

      @Override
      public String toString() {
        return "ntriples";
      }
    };
  }

  public static enum QueryLanguage {

    SPARQL, SERQL
  }
  /**
   * denotes the N3 ontology format
   */
  public static final byte ONTOLOGY_FORMAT_N3 = 0;
  /**
   * denotes the NTRIPLES ontology format
   */
  public static final byte ONTOLOGY_FORMAT_NTRIPLES = 1;
  /**
   * denotes the RDFXML ontology format
   */
  public static final byte ONTOLOGY_FORMAT_RDFXML = 2;
  /**
   * denotes the TURTLE ontology format
   */
  public static final byte ONTOLOGY_FORMAT_TURTLE = 3;
  /**
   * Name of the anonymouse class
   */
  public static final String ANONYMOUS_CLASS_NAME = "Anonymous";
  /// **********************************************
  /// when resources are modified
  /// **********************************************
  /**
   * denotes the event when a new class is added
   */
  public static final int OCLASS_ADDED_EVENT = 0;
  /**
   * denotes the event when a new anonymous class is added
   */
  public static final int ANONYMOUS_CLASS_ADDED_EVENT = 1;
  /**
   * denotes the event when a new cardinality_restriction is added
   */
  public static final int CARDINALITY_RESTRICTION_ADDED_EVENT = 2;
  /**
   * denotes the event when a new min_cardinality_restriction is added
   */
  public static final int MIN_CARDINALITY_RESTRICTION_ADDED_EVENT = 3;
  /**
   * denotes the event when a new max_cardinality_restriction is added
   */
  public static final int MAX_CARDINALITY_RESTRICTION_ADDED_EVENT = 4;
  /**
   * denotes the event when a new has_value_restriction is added
   */
  public static final int HAS_VALUE_RESTRICTION_ADDED_EVENT = 5;
  /**
   * denotes the event when a new some_values_from_restriction is added
   */
  public static final int SOME_VALUES_FROM_RESTRICTION_ADDED_EVENT = 6;
  /**
   * denotes the event when a new all_values_from_restriction is added
   */
  public static final int ALL_VALUES_FROM_RESTRICTION_ADDED_EVENT = 7;
  /**
   * denotes the addition of sub class event
   */
  public static final int SUB_CLASS_ADDED_EVENT = 8;
  /**
   * denotes the removal of sub class event
   */
  public static final int SUB_CLASS_REMOVED_EVENT = 9;
  /**
   * denotes the event of two classes set as equivalent
   */
  public static final int EQUIVALENT_CLASS_EVENT = 10;
  /**
   * denotes the event when a new annotation property is added
   */
  public static final int ANNOTATION_PROPERTY_ADDED_EVENT = 11;
  /**
   * denotes the event when a new datatype property is added
   */
  public static final int DATATYPE_PROPERTY_ADDED_EVENT = 12;
  /**
   * denotes the event when a new object property is added
   */
  public static final int OBJECT_PROPERTY_ADDED_EVENT = 13;
  /**
   * denotes the event when a new transitive
   * property is added
   */
  public static final int TRANSTIVE_PROPERTY_ADDED_EVENT = 14;
  /**
   * denotes the event when a new symmetric property is added
   */
  public static final int SYMMETRIC_PROPERTY_ADDED_EVENT = 15;
  /**
   * denotes the event when an annotation property is assigned to a
   * resource with some compatible value
   */
  public static final int ANNOTATION_PROPERTY_VALUE_ADDED_EVENT = 16;
  /**
   * denotes the event when a datatype property is assigned to a
   * resource with some compatible value
   */
  public static final int DATATYPE_PROPERTY_VALUE_ADDED_EVENT = 17;
  /**
   * denotes the event when an object property is assigned to a resource
   * with some compatible value
   */
  public static final int OBJECT_PROPERTY_VALUE_ADDED_EVENT = 18;
  /**
   * denotes the event when an rdf property is assigned to a resource
   * with some compatible value
   */
  public static final int RDF_PROPERTY_VALUE_ADDED_EVENT = 19;
  /**
   * denotes the event when an annotation property value is removed from
   * the resource
   */
  public static final int ANNOTATION_PROPERTY_VALUE_REMOVED_EVENT = 20;
  /**
   * denotes the event when a datatype property value is removed from
   * the resource
   */
  public static final int DATATYPE_PROPERTY_VALUE_REMOVED_EVENT = 21;
  /**
   * denotes the event when an object property value is removed from the
   * resource
   */
  public static final int OBJECT_PROPERTY_VALUE_REMOVED_EVENT = 22;
  /**
   * denotes the event when an rdf property value is removed from the
   * resource
   */
  public static final int RDF_PROPERTY_VALUE_REMOVED_EVENT = 23;
  /**
   * denotes the event when two properties are set to be equivalent
   */
  public static final int EQUIVALENT_PROPERTY_EVENT = 24;
  /**
   * denotes the event when a new instance is added
   */
  public static final int OINSTANCE_ADDED_EVENT = 25;
  /**
   * denotes the event when two instances are set to be different from
   * each other
   */
  public static final int DIFFERENT_INSTANCE_EVENT = 26;
  /**
   * denotes the event when two instances are set to be same instances
   */
  public static final int SAME_INSTANCE_EVENT = 27;
  /**
   * when a resource is removed
   */
  public static final int RESOURCE_REMOVED_EVENT = 28;
  /**
   * when restriction's on property value is changed
   */
  public static final int RESTRICTION_ON_PROPERTY_VALUE_CHANGED = 29;
  /**
   * denotes the addition of sub property event
   */
  public static final int SUB_PROPERTY_ADDED_EVENT = 30;
  /**
   * denotes the removal of sub property event
   */
  public static final int SUB_PROPERTY_REMOVED_EVENT = 31;
  /// **********************************************
  /// Locale constants
  /// **********************************************
  /** Language code used "aa" */
  public static final Locale AFAR = new Locale("aa");
  /** Language code used "ab" */
  public static final Locale ABKHAZIAN = new Locale("ab");
  /** Language code used "af" */
  public static final Locale AFRIKAANS = new Locale("af");
  /** Language code used "am" */
  public static final Locale AMHARIC = new Locale("am");
  /** Language code used "ar" */
  public static final Locale ARABIC = new Locale("ar");
  /** Language code used "as" */
  public static final Locale ASSAMESE = new Locale("as");
  /** Language code used "ay" */
  public static final Locale AYMARA = new Locale("ay");
  /** Language code used "az" */
  public static final Locale AZERBAIJANI = new Locale("az");
  /** Language code used "ba" */
  public static final Locale BASHKIR = new Locale("ba");
  /** Language code used "be" */
  public static final Locale BYELORUSSIAN = new Locale("be");
  /** Language code used "bg" */
  public static final Locale BULGARIAN = new Locale("bg");
  /** Language code used "bh" */
  public static final Locale BIHARI = new Locale("bh");
  /** Language code used "bi" */
  public static final Locale BISLAMA = new Locale("bi");
  /** Language code used "bn" */
  public static final Locale BENGALI = new Locale("bn");
  /** Language code used "bo" */
  public static final Locale TIBETAN = new Locale("bo");
  /** Language code used "br" */
  public static final Locale BRETON = new Locale("br");
  /** Language code used "ca" */
  public static final Locale CATALAN = new Locale("ca");
  /** Language code used "co" */
  public static final Locale CORSICAN = new Locale("co");
  /** Language code used "cs" */
  public static final Locale CZECH = new Locale("cs");
  /** Language code used "cy" */
  public static final Locale WELSH = new Locale("cy");
  /** Language code used "da" */
  public static final Locale DANISH = new Locale("da");
  /** Language code used "de" */
  public static final Locale GERMAN = new Locale("de");
  /** Language code used "dz" */
  public static final Locale BHUTANI = new Locale("dz");
  /** Language code used "el" */
  public static final Locale GREEK = new Locale("el");
  /** Language code used "en" */
  public static final Locale ENGLISH = new Locale("en");
  /** Language code used "eo" */
  public static final Locale ESPERANTO = new Locale("eo");
  /** Language code used "es" */
  public static final Locale SPANISH = new Locale("es");
  /** Language code used "et" */
  public static final Locale ESTONIAN = new Locale("et");
  /** Language code used "eu" */
  public static final Locale BASQUE = new Locale("eu");
  /** Language code used "fa" */
  public static final Locale PERSIAN = new Locale("fa");
  /** Language code used "fi" */
  public static final Locale FINNISH = new Locale("fi");
  /** Language code used "fj" */
  public static final Locale FIJI = new Locale("fj");
  /** Language code used "fo" */
  public static final Locale FAROESE = new Locale("fo");
  /** Language code used "fr" */
  public static final Locale FRENCH = new Locale("fr");
  /** Language code used "fy" */
  public static final Locale FRISIAN = new Locale("fy");
  /** Language code used "ga" */
  public static final Locale IRISH = new Locale("ga");
  /** Language code used "gd" */
  public static final Locale SCOTS = new Locale("gd");
  /** Language code used "gl" */
  public static final Locale GALICIAN = new Locale("gl");
  /** Language code used "gn" */
  public static final Locale GUARANI = new Locale("gn");
  /** Language code used "gu" */
  public static final Locale GUJARATI = new Locale("gu");
  /** Language code used "ha" */
  public static final Locale HAUSA = new Locale("ha");
  /** Language code used "he" */
  public static final Locale HEBREW = new Locale("he");
  /** Language code used "hi" */
  public static final Locale HINDI = new Locale("hi");
  /** Language code used "hr" */
  public static final Locale CROATIAN = new Locale("hr");
  /** Language code used "hu" */
  public static final Locale HUNGARIAN = new Locale("hu");
  /** Language code used "hy" */
  public static final Locale ARMENIAN = new Locale("hy");
  /** Language code used "ia" */
  public static final Locale INTERLINGUA = new Locale("ia");
  /** Language code used "id" */
  public static final Locale INDONESIAN = new Locale("id");
  /** Language code used "ie" */
  public static final Locale INTERLINGUE = new Locale("ie");
  /** Language code used "ik" */
  public static final Locale INUPIAK = new Locale("ik");
  /** Language code used "is" */
  public static final Locale ICELANDIC = new Locale("is");
  /** Language code used "it" */
  public static final Locale ITALIAN = new Locale("it");
  /** Language code used "iu" */
  public static final Locale INUKTITUT = new Locale("iu");
  /** Language code used "ja" */
  public static final Locale JAPANESE = new Locale("ja");
  /** Language code used "jw" */
  public static final Locale JAVANESE = new Locale("jw");
  /** Language code used "ka" */
  public static final Locale GEORGIAN = new Locale("ka");
  /** Language code used "kk" */
  public static final Locale KAZAKH = new Locale("kk");
  /** Language code used "kl" */
  public static final Locale GREENLANDIC = new Locale("kl");
  /** Language code used "km" */
  public static final Locale CAMBODIAN = new Locale("km");
  /** Language code used "kn" */
  public static final Locale KANNADA = new Locale("kn");
  /** Language code used "ko" */
  public static final Locale KOREAN = new Locale("ko");
  /** Language code used "ks" */
  public static final Locale KASHMIRI = new Locale("ks");
  /** Language code used "ku" */
  public static final Locale KURDISH = new Locale("ku");
  /** Language code used "ky" */
  public static final Locale KIRGHIZ = new Locale("ky");
  /** Language code used "la" */
  public static final Locale LATIN = new Locale("la");
  /** Language code used "ln" */
  public static final Locale LINGALA = new Locale("ln");
  /** Language code used "lo" */
  public static final Locale LAOTHIAN = new Locale("lo");
  /** Language code used "lt" */
  public static final Locale LITHUANIAN = new Locale("lt");
  /** Language code used "lv" */
  public static final Locale LATVIAN = new Locale("lv");
  /** Language code used "mg" */
  public static final Locale MALAGASY = new Locale("mg");
  /** Language code used "mi" */
  public static final Locale MAORI = new Locale("mi");
  /** Language code used "mk" */
  public static final Locale MACEDONIAN = new Locale("mk");
  /** Language code used "ml" */
  public static final Locale MALAYALAM = new Locale("ml");
  /** Language code used "mn" */
  public static final Locale MONGOLIAN = new Locale("mn");
  /** Language code used "mo" */
  public static final Locale MOLDAVIAN = new Locale("mo");
  /** Language code used "mr" */
  public static final Locale MARATHI = new Locale("mr");
  /** Language code used "ms" */
  public static final Locale MALAY = new Locale("ms");
  /** Language code used "mt" */
  public static final Locale MALTESE = new Locale("mt");
  /** Language code used "my" */
  public static final Locale BURMESE = new Locale("my");
  /** Language code used "na" */
  public static final Locale NAURU = new Locale("na");
  /** Language code used "ne" */
  public static final Locale NEPALI = new Locale("ne");
  /** Language code used "nl" */
  public static final Locale DUTCH = new Locale("nl");
  /** Language code used "no" */
  public static final Locale NORWEGIAN = new Locale("no");
  /** Language code used "oc" */
  public static final Locale OCCITAN = new Locale("oc");
  /** Language code used "om" */
  public static final Locale OROMO = new Locale("om");
  /** Language code used "or" */
  public static final Locale ORIYA = new Locale("or");
  /** Language code used "pa" */
  public static final Locale PUNJABI = new Locale("pa");
  /** Language code used "pl" */
  public static final Locale POLISH = new Locale("pl");
  /** Language code used "ps" */
  public static final Locale PASHTO = new Locale("ps");
  /** Language code used "pt" */
  public static final Locale PORTUGUESE = new Locale("pt");
  /** Language code used "qu" */
  public static final Locale QUECHUA = new Locale("qu");
  /** Language code used "rm" */
  public static final Locale RHAETO_ROMANCE = new Locale("rm");
  /** Language code used "rn" */
  public static final Locale KIRUNDI = new Locale("rn");
  /** Language code used "ro" */
  public static final Locale ROMANIAN = new Locale("ro");
  /** Language code used "ru" */
  public static final Locale RUSSIAN = new Locale("ru");
  /** Language code used "rw" */
  public static final Locale KINYARWANDA = new Locale("rw");
  /** Language code used "sa" */
  public static final Locale SANSKRIT = new Locale("sa");
  /** Language code used "sd" */
  public static final Locale SINDHI = new Locale("sd");
  /** Language code used "sg" */
  public static final Locale SANGHO = new Locale("sg");
  /** Language code used "sh" */
  public static final Locale SERBO_CROATIAN = new Locale("sh");
  /** Language code used "si" */
  public static final Locale SINHALESE = new Locale("si");
  /** Language code used "sk" */
  public static final Locale SLOVAK = new Locale("sk");
  /** Language code used "sl" */
  public static final Locale SLOVENIAN = new Locale("sl");
  /** Language code used "sm" */
  public static final Locale SAMOAN = new Locale("sm");
  /** Language code used "sn" */
  public static final Locale SHONA = new Locale("sn");
  /** Language code used "so" */
  public static final Locale SOMALI = new Locale("so");
  /** Language code used "sq" */
  public static final Locale ALBANIAN = new Locale("sq");
  /** Language code used "sr" */
  public static final Locale SERBIAN = new Locale("sr");
  /** Language code used "ss" */
  public static final Locale SISWATI = new Locale("ss");
  /** Language code used "st" */
  public static final Locale SESOTHO = new Locale("st");
  /** Language code used "su" */
  public static final Locale SUNDANESE = new Locale("su");
  /** Language code used "sv" */
  public static final Locale SWEDISH = new Locale("sv");
  /** Language code used "sw" */
  public static final Locale SWAHILI = new Locale("sw");
  /** Language code used "ta" */
  public static final Locale TAMIL = new Locale("ta");
  /** Language code used "te" */
  public static final Locale TELUGU = new Locale("te");
  /** Language code used "tg" */
  public static final Locale TAJIK = new Locale("tg");
  /** Language code used "th" */
  public static final Locale THAI = new Locale("th");
  /** Language code used "ti" */
  public static final Locale TIGRINYA = new Locale("ti");
  /** Language code used "tk" */
  public static final Locale TURKMEN = new Locale("tk");
  /** Language code used "tl" */
  public static final Locale TAGALOG = new Locale("tl");
  /** Language code used "tn" */
  public static final Locale SETSWANA = new Locale("tn");
  /** Language code used "to" */
  public static final Locale TONGA = new Locale("to");
  /** Language code used "tr" */
  public static final Locale TURKISH = new Locale("tr");
  /** Language code used "ts" */
  public static final Locale TSONGA = new Locale("ts");
  /** Language code used "tt" */
  public static final Locale TATAR = new Locale("tt");
  /** Language code used "tw" */
  public static final Locale TWI = new Locale("tw");
  /** Language code used "ug" */
  public static final Locale UIGHUR = new Locale("ug");
  /** Language code used "uk" */
  public static final Locale UKRAINIAN = new Locale("uk");
  /** Language code used "ur" */
  public static final Locale URDU = new Locale("ur");
  /** Language code used "uz" */
  public static final Locale UZBEK = new Locale("uz");
  /** Language code used "vi" */
  public static final Locale VIETNAMESE = new Locale("vi");
  /** Language code used "vo" */
  public static final Locale VOLAPUK = new Locale("vo");
  /** Language code used "wo" */
  public static final Locale WOLOF = new Locale("wo");
  /** Language code used "xh" */
  public static final Locale XHOSA = new Locale("xh");
  /** Language code used "yi" */
  public static final Locale YIDDISH = new Locale("yi");
  /** Language code used "yo" */
  public static final Locale YORUBA = new Locale("yo");
  /** Language code used "za" */
  public static final Locale ZHUANG = new Locale("za");
  /** Language code used "zh" */
  public static final Locale CHINESE = new Locale("zh");
  /** Language code used "zu" */
  public static final Locale ZULU = new Locale("zu");
}
