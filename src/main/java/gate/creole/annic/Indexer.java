/*
 *  Indexer.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Indexer.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import gate.Corpus;

import java.util.List;
import java.util.Map;

/**
 * Base interface that declares methods for the Indexer.
 * 
 * @author niraj
 * 
 */
public interface Indexer {

  /**
   * Create a Index
   * 
   * @param parameters - parameters needed for creating an index values
   *          depend on the implementing IndexManager
   * @throws IndexException
   */
  public void createIndex(Map<String,Object> parameters) throws IndexException;

  /** Optimize the existing index */
  public void optimizeIndex() throws IndexException;

  /** Delete all index files and directories in index location. */
  public void deleteIndex() throws IndexException;

  /**
   * Add new documents to Index
   * 
   * @param corpusPersistenceID
   * @param addedDocuments
   * @throws IndexException
   */
  public void add(String corpusPersistenceID, List<gate.Document> addedDocuments)
          throws IndexException;

  /**
   * remove documents from the Index
   * 
   * @param removedDocumentPersistenceIds
   * @throws Exception
   */
  public void remove(List<Object> removedDocumentPersistenceIds) throws IndexException;

  /**
   * Set the corpus to be indexed
   * 
   * @param corpus
   */
  public void setCorpus(Corpus corpus) throws IndexException;

  /**
   * Corpus to be indexed
   */
  public Corpus getCorpus();

  /**
   * Returns the parameters
   */
  public Map<String,Object> getParameters();
}
