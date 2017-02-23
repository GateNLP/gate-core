/*
 *  Constants.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Constants.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

/**
 * Constants used by annic classes.
 * @author niraj
 *
 */
public class Constants {
  
    /**
     * Name of the document_id_field that is stored in index.
     */
  public final static String DOCUMENT_ID = "DOCUMENT_ID";

  /**
   * Name of the document_id_field that is stored in index.
   */
  public final static String DOCUMENT_ID_FOR_SERIALIZED_FILE = "DOCUMENT_ID_FOR_SERIALIZED_FILE";
  
  /**
   * Indexed features
   */
  public final static String INDEXED_FEATURES = "INDEXED_FEATURES";
  
  /**
   * Default annotation set name
   */
  public final static String DEFAULT_ANNOTATION_SET_NAME = "<null>";
  
  /**
   * create tokens automatically parameter name
   */
  public final static String CREATE_TOKENS_AUTOMATICALLY = "CREATE_TOKENS_AUTOMATICALLY";

  /**
   * 
   * Annic token which is created when no tokens are provided
   */
  public final static String ANNIC_TOKEN = "Token";
  
  /**
   * name of the index_location_url parameter.
   */
  public final static String INDEX_LOCATION_URL = "INDEX_LOCATION_URL";

  /**
   * Name of the annotation_sets_names_to_include parameter.
   */
  public final static String ANNOTATION_SETS_NAMES_TO_INCLUDE = "ANNOTATION_SETS_NAMES_TO_INCLUDE";

  /**
   * Name of the annotation_sets_names_to_exclude parameter.
   */
  public final static String ANNOTATION_SETS_NAMES_TO_EXCLUDE = "ANNOTATION_SETS_NAMES_TO_EXCLUDE";
  
  /**
   * Name of the features_to_exclude parameter.
   */
  public final static String FEATURES_TO_EXCLUDE = "FEATURES_TO_EXCLUDE";

  /**
   * Name of the features_to_include parameter.
   */
  public final static String FEATURES_TO_INCLUDE = "FEATURES_TO_INCLUDE";
  
  
  /**
   * Name of the base_token_annotation_type parameter.
   */
  public final static String BASE_TOKEN_ANNOTATION_TYPE = "BASE_TOKEN_ANNOTATION_TYPE";

  /**
   * Name of the index_unit_annotation_type parameter.
   */
  public final static String INDEX_UNIT_ANNOTATION_TYPE = "INDEX_UNIT_ANNOTATION_TYPE";

  /**
   * Name of the corpus_index_feature parameter.
   */
  public final static String CORPUS_INDEX_FEATURE = "CorpusIndexFeature";

  /**
   * default value for the corpus_index_feature
   */
  public final static String CORPUS_INDEX_FEATURE_VALUE = "AnnicIR";

  /**
   * Name of the corpus_size parameter.
   */
  public final static String CORPUS_SIZE = "CORPUS_SIZE";

  /**
   * Name of the context_window parameter.
   */
  public final static String CONTEXT_WINDOW = "CONTEXT_WINDOW";

  /**
   * Name of the index_locations parameter.
   */
  public final static String INDEX_LOCATIONS = "INDEX_LOCATIONS";

  /**
   * folder name used for creating a folder which is then used for serializing the files
   */
  public final static String SERIALIZED_FOLDER_NAME = "serialized-files";

  /**
   * Name of the corpus_id parameter.
   */
  public final static String CORPUS_ID = "CORPUS_ID";
  
  /**
   * Name of the annotation set id parameter
   */
  public final static String ANNOTATION_SET_ID = "ANNOTATION_SET_ID";
  
  /**
   * Contains the merged annotation set in combined sets.
   */
  public final static String COMBINED_SET = "Combined sets";
  
  /**
   * Contains the merged annotation set.
   */
  public final static String ALL_SETS = "All sets";

  /**
   * Contains the entire datastore.
   */
  public final static String ENTIRE_DATASTORE = "Entire datastore";
}
