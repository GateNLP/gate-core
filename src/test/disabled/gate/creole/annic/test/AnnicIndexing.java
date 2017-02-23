/*
 *  AnnicIndexing.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: AnnicIndexing.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.test;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import gate.creole.*;
import gate.creole.annic.Constants;
import gate.creole.annic.IndexException;
import gate.creole.annic.lucene.LuceneIndexer;

import java.io.IOException;
import java.net.URL;

/**
 * The class is an example of how to index a corpus using the ANNIC
 * functionalities. The class is used by the TestAnnic.java.
 * 
 * @author niraj
 * 
 */
public class AnnicIndexing {

  /**
   * Corpus to index.
   */
  private gate.Corpus corpus;

  /**
   * Features to exclude from index.
   */
  private ArrayList<String> featuresToExclude = new ArrayList<String>();

  /**
   * Where to store the index.
   */
  private java.net.URL indexOutputDirectoryLocation;

  /**
   * AnnotationSet to index.
   */
  private String annotationSetName = "";

  /**
   * Base Token Annotation Type e.g. Token
   */
  private String baseTokenAnnotationType = "";

  /**
   * Index Unit Annotation type e.g. Sentence
   */
  private String indexUnitAnnotationType = "";

  /**
   * Instance of a Lucene Indexer
   */
  private LuceneIndexer indexer = null;

  /**
   * Constructor
   * @throws IOException
   */
  public AnnicIndexing() throws IOException {
    corpus = null;
    annotationSetName = "";
    indexer = new LuceneIndexer((URL)null);

  }

  /**
   * This method creates a lucene index.
   */
  public void execute() throws ExecutionException {
    try {
      Map<String,Object> parameters = new HashMap<String,Object>();
      ArrayList<String> toinclude = new ArrayList<String>();
      toinclude.add(getAnnotationSetName());
      parameters.put(Constants.ANNOTATION_SETS_NAMES_TO_INCLUDE, toinclude);
      parameters.put(Constants.ANNOTATION_SETS_NAMES_TO_EXCLUDE, new ArrayList<String>());
      parameters.put(Constants.BASE_TOKEN_ANNOTATION_TYPE, getBaseTokenAnnotationType());
      parameters.put(Constants.FEATURES_TO_EXCLUDE, getFeaturesToExclude());
      parameters.put(Constants.FEATURES_TO_INCLUDE, new ArrayList<String>());
      parameters.put(Constants.INDEX_UNIT_ANNOTATION_TYPE, getIndexUnitAnnotationType());
      parameters.put(Constants.INDEX_LOCATION_URL, getIndexOutputDirectoryLocation());
      indexer.setCorpus(getCorpus());
      indexer.createIndex(parameters);
    }
    catch(IndexException ie) {
      throw new ExecutionException(ie);
    }
  }

  /**
   * Gets the location of index output directory
   */
  public java.net.URL getIndexOutputDirectoryLocation() {
    return indexOutputDirectoryLocation;
  }

  /**
   * Sets the location of index output directory
   * @param dir
   */
  public void setIndexOutputDirectoryLocation(java.net.URL dir) {
    indexOutputDirectoryLocation = dir;
  }

  /**
   * Gets the annotation set name to be indexed
   */
  public String getAnnotationSetName() {
    return annotationSetName;
  }

  /**
   * Sets the annotation set name
   * @param annotationSetName
   */
  public void setAnnotationSetName(String annotationSetName) {
    if(annotationSetName != null && annotationSetName.trim().equals("")) {
      annotationSetName = null;
    }

    this.annotationSetName = annotationSetName;
  }

  /**
   * Gets the base token annotation type
   */
  public String getBaseTokenAnnotationType() {
    return this.baseTokenAnnotationType;
  }

  /**
   * Sets the base token annotation type
   * @param baseTokenAnnotationType
   */
  public void setBaseTokenAnnotationType(String baseTokenAnnotationType) {
    this.baseTokenAnnotationType = baseTokenAnnotationType;
  }

  /**
   * Sets the corpus to index
   * @param corpus
   */
  public void setCorpus(gate.Corpus corpus) {
    this.corpus = corpus;
  }

  /**
   * Gets the corpus to index
   */
  public gate.Corpus getCorpus() {
    return this.corpus;
  }

  /**
   * Gets the features of annotation to be excluded from being indexed
   */
  public List<?> getFeaturesToExclude() {
    return featuresToExclude;
  }

  /**
   * Sets the features of annotations to be excluded from being indexed
   * @param featuresToExclude
   */
  public void setFeaturesToExclude(ArrayList<String> featuresToExclude) {
    this.featuresToExclude = featuresToExclude;
  }

  /**
   * Gets the Index Unit Annotation type.
   */
  public String getIndexUnitAnnotationType() {
    return indexUnitAnnotationType;
  }

  /**
   * Sets the Index Unit annotation type.
   * @param indexUnitAnnotationType
   */
  public void setIndexUnitAnnotationType(String indexUnitAnnotationType) {
    this.indexUnitAnnotationType = indexUnitAnnotationType;
  }
}
