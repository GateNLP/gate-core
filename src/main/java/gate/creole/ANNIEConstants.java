/*
 *  AnnieConstants.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 16/Oct/2001
 *
 *  $Id: ANNIEConstants.java 17392 2014-02-21 20:31:54Z markagreenwood $
 */

package gate.creole;

/** This interface defines constants used by the ANNIE processing resources. */
public interface ANNIEConstants {

  public static final String PLUGIN_DIR = "ANNIE";
  public static final String DEFAULT_FILE = "ANNIE_with_defaults.gapp";

  /** The name of the feature on Documents that holds coreference matches. */
  public static final String DOCUMENT_COREF_FEATURE_NAME = "MatchesAnnots";

  /** The name of the feature on Annotations that holds coreference matches. */
  public static final String ANNOTATION_COREF_FEATURE_NAME = "matches";

  public static final String TOKEN_ANNOTATION_TYPE = "Token";
  public static final String TOKEN_STRING_FEATURE_NAME = "string";
  public static final String TOKEN_CATEGORY_FEATURE_NAME = "category";
  public static final String TOKEN_KIND_FEATURE_NAME = "kind";
  public static final String TOKEN_LENGTH_FEATURE_NAME = "length";
  public static final String TOKEN_ORTH_FEATURE_NAME = "orth";

  public static final String SPACE_TOKEN_ANNOTATION_TYPE = "SpaceToken";

  public static final String LOOKUP_ANNOTATION_TYPE = "Lookup";
  public static final String LOOKUP_MAJOR_TYPE_FEATURE_NAME = "majorType";
  public static final String LOOKUP_MINOR_TYPE_FEATURE_NAME = "minorType";
  public static final String LOOKUP_LANGUAGE_FEATURE_NAME = "language";  
  public static final String LOOKUP_ONTOLOGY_FEATURE_NAME = "ontology";
  public static final String LOOKUP_CLASS_FEATURE_NAME = "class";
  public static final String LOOKUP_INSTANCE_FEATURE_NAME = "inst";

  public static final String SENTENCE_ANNOTATION_TYPE = "Sentence";

  public static final String PERSON_ANNOTATION_TYPE = "Person";
  public static final String PERSON_GENDER_FEATURE_NAME = "gender";

  public static final String ORGANIZATION_ANNOTATION_TYPE = "Organization";
  public static final String LOCATION_ANNOTATION_TYPE = "Location";
  public static final String MONEY_ANNOTATION_TYPE = "Money";
  public static final String DATE_ANNOTATION_TYPE = "Date";

  public static final String DATE_POSTED_ANNOTATION_TYPE = "Date_Posted";
  public static final String JOB_ID_ANNOTATION_TYPE = "JobId";


  } // AnnieConstants