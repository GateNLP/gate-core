/*
 * GateConstants.java
 * 
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Cristian URSU, 8/Nov/2001
 * 
 * $Id: GateConstants.java 18850 2015-08-04 14:44:01Z domrout $
 */
package gate;

/** Interface used to hold different GATE constants */
public interface GateConstants {
  /** The name of config data files (<TT>gate.xml</TT>). */
  public static final String GATE_DOT_XML = "gate.xml";

  /** The name of the installed plug-ins directory */
  public static final String PLUGINS = "plugins";

  /** The name of session state data files (<TT>gate.session</TT>). */
  public static final String GATE_DOT_SER = "gate.session";

  /** The name of the site config property (<TT>gate.config</TT>). */
  public static final String GATE_CONFIG_PROPERTY = "gate.config";

  /** The name of the annotation set storing original markups in a document */
  public static final String ORIGINAL_MARKUPS_ANNOT_SET_NAME =
      "Original markups";

  /** The look and feel option name */
  public static final String LOOK_AND_FEEL = "Look_and_Feel";

  /** The key for the font used for text components */
  public static final String TEXT_COMPONENTS_FONT = "Text_components_font";

  /** The key for the font used for menus */
  public static final String MENUS_FONT = "Menus_font";

  /** The key for the font used for other GUI components */
  public static final String OTHER_COMPONENTS_FONT = "Other_components_font";

  /** The key for the main window width */
  public static final String MAIN_FRAME_WIDTH = "Main_frame_width";

  /** The key for the main window height */
  public static final String MAIN_FRAME_HEIGHT = "Main_frame_height";

  /** The toolbar text option name */
  public static final String TOOLBAR_TEXT = "Toolbar_Text";

  /** The key for determining if the main window should be maximized **/
  public static final String MAIN_FRAME_MAXIMIZED = "Main_frame_maximized";

  /** The key for the save options on exit value */
  public static final String SAVE_OPTIONS_ON_EXIT = "Save_options_on_exit";

  /** The key for the save session on exit value */
  public static final String SAVE_SESSION_ON_EXIT = "Save_session_on_exit";

  /** The key for saving the features when preserving format */
  public static final String SAVE_FEATURES_WHEN_PRESERVING_FORMAT =
      "Save_features_when_preserving_format";

  /**
   * The key for the known plugins path option in the GATE config map
   */
  public static final String KNOWN_PLUGIN_PATH_KEY = "Known_plugin_path";

  /**
   * The key for the autoload plugins path option in the GATE config map
   */
  public static final String AUTOLOAD_PLUGIN_PATH_KEY = "Load_plugin_path";

  /**
   * The name for the autoload plugins path system property
   */
  public static final String AUTOLOAD_PLUGIN_PATH_PROPERTY_NAME =
      "load.plugin.path";

  /**
   * The name of the GATE home system property
   */
  public static final String GATE_HOME_PROPERTY_NAME = "gate.home";

  /**
   * The name of the GATE plugins home system property
   */
  public static final String PLUGINS_HOME_PROPERTY_NAME = "gate.plugins.home";

  /**
   * The name of the GATE site config system property
   */
  public static final String SITE_CONFIG_PROPERTY_NAME = "gate.site.config";

  /**
   * The name of the GATE user config system property
   */
  public static final String USER_CONFIG_PROPERTY_NAME = "gate.user.config";

  /**
   * The name of the GATE site config system property
   */
  public static final String GATE_SITE_CONFIG_PROPERTY_NAME =
      "gate.site.config";

  /**
   * The name of the GATE user config system property
   */
  public static final String GATE_USER_CONFIG_PROPERTY_NAME =
      "gate.user.config";

  /**
   * The name of the property for setting the user session file name
   */
  public static final String GATE_USER_SESSION_PROPERTY_NAME =
      "gate.user.session";

  /**
   * The name of the built-in creole directory URL property
   */
  public static final String BUILTIN_CREOLE_DIR_PROPERTY_NAME =
      "gate.builtin.creole.dir";

  /** The key for the feature keeping the original content of the document */
  public static final String ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME =
      "Original_document_content_on_load";

  /**
   * The key for the feature keeping the repositioning information between
   * original and displayed content of the document
   */
  public static final String DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME =
      "Document_repositioning_info";

  /** */
  public static final String DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME =
      "Document_add_space_on_unpack";

  /** Property to set title of application from command line */
  public static final String TITLE_JAVA_PROPERTY_NAME = "gate.slug.title";

  /** Property to set icon of application from command line */
  public static final String APP_ICON_JAVA_PROPERTY_NAME = "gate.slug.icon";

  /** Property to set splash of application from command line */
  public static final String APP_SPLASH_JAVA_PROPERTY_NAME = "gate.slug.splash";

  /** Property to set help about box from command line */
  public static final String ABOUT_URL_JAVA_PROPERTY_NAME =
      "gate.slug.abouturl";

  /** Property to set slug application from command line */
  public static final String APPLICATION_JAVA_PROPERTY_NAME = "gate.slug.app";

  /** Property to set slug annotation types for export Inline */
  public static final String ANNOT_TYPE_TO_EXPORT = "annotTypesToExport";

  /** The key for the feature keeping the IndexDefinition */
  public static final String CORPUS_INDEX_DEFINITION_FEATURE_KEY =
      "Index_definition_feature_key";

  /** The key for the feature keeping the IndexStatistics */
  public static final String CORPUS_INDEX_STATISTICS_FEATURE_KEY =
      "Index_statistics_feature_key";

  /** The key used for document editor inser behaviour */
  public static final String DOCEDIT_INSERT_APPEND = "docedit_insert_append";

  /** The key used for document editor inser behaviour */
  public static final String DOCEDIT_INSERT_PREPEND = "docedit_insert_prepend";

  public static final String DOCEDIT_READ_ONLY = "docedit_read-only";

  /**
   * Orientation of the document from right to left
   */
  public static final String DOC_RTOL_ORIENTATION = "doc_r2l_orientation";

  /** Document property to set throw of exception on parsing format error */
  public static final String THROWEX_FORMAT_PROPERTY_NAME =
      "throwExceptionOnFormatError";

  /** The key for the WordNet config file */
  public static final String WORDNET_CONFIG_FILE = "Wordnet_config_file";

  // /** The index type of corpus*/
  // public static final int IR_LUCENE_INVFILE = 1001;
  /** Property for document new line type. Values {"CR", "LF", "CRLF", "LFCR"} */
  public static final String DOCUMENT_NEW_LINE_TYPE = "docNewLineType";

  /**
   * The key for the gate.xml option to specify a compiler type to use to build
   * the Java files compiled from JAPE grammars.
   */
  public static final String COMPILER_TYPE_KEY = "Compiler_type";

  /**
   * Key used in resource features for hiding the resource fromthe GUI.
   */
  public static final String HIDDEN_FEATURE_KEY = "gate.HIDDEN";

  /**
   * Key used in TOOL actions to store the 'menu path' under which the action
   * should be placed in the Tools menu.
   */
  public static final String MENU_PATH_KEY = "gate.MenuPath";

  /**
   * Keys used in gate.xml or user config to specify the feature name to use to
   * add to annotations in Original markups AS that contain namespace URI and
   * prefix information. E.g. title
   * namespaceURI="http://purl.org/dc/elements/1.1/" namespacePrefix="dc"
   */
  public static final String ADD_NAMESPACE_FEATURES = "addNamespaceFeatures";

  public static final String ELEMENT_NAMESPACE_URI = "namespaceURI";

  public static final String ELEMENT_NAMESPACE_PREFIX = "namespacePrefix";
} // GateConstants
