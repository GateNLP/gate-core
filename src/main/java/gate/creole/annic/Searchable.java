/*
 *  Searchable.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Searchable.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import java.util.Map;

/**
 * The interface declares methods which should be implemented by an
 * object wishing to get indexed and being searchable.
 * 
 * @author niraj
 * 
 */
public interface Searchable {

  /**
   * This method is used to specify the indexer which is used to index
   * documents
   * 
   * @param indexer
   * @param parameters - parameters required by the specific
   *          implementation of provided indexer
   * @throws IndexException
   */
  public void setIndexer(Indexer indexer, Map<String,Object> parameters) throws IndexException;

  /**
   * Returns the Indexer
   */
  public Indexer getIndexer();

  /**
   * This method is used to specify the searcher which is used for
   * searchering the index
   * 
   * @param searcher
   * @throws SearchException
   */
  public void setSearcher(Searcher searcher) throws SearchException;

  /**
   * Returns the Searcher
   */
  public Searcher getSearcher();

  /**
   * @param query
   * @param searchParameters - parameters required for searching an
   *          index (e.g. location of the index)
   * @return true if the search was successful
   * @throws SearchException
   */
  public boolean search(String query, Map<String,Object> searchParameters)
          throws SearchException;

  /**
   * @param numberOfPatterns
   * @return an Array of Hit that has atleast a document ID, start and
   *         end offset and the original query string
   * @throws SearchException
   */
  public Hit[] next(int numberOfPatterns) throws SearchException;

}
