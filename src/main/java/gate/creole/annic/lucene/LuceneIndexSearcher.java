/*
 *  LuceneIndexSearcher.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneIndexSearcher.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.io.IOException;
import gate.creole.annic.apache.lucene.index.IndexReader;
import gate.creole.annic.apache.lucene.search.Filter;
import gate.creole.annic.apache.lucene.search.IndexSearcher;
import gate.creole.annic.apache.lucene.search.Query;
import gate.creole.annic.apache.lucene.search.TopDocs;
import gate.creole.annic.apache.lucene.store.Directory;

/**
 * This class provides an implementation that searches within the lucene
 * index to retrieve the results of a query submitted by user.
 * 
 * @author niraj
 * 
 */
public class LuceneIndexSearcher extends IndexSearcher {

  /** Creates a searcher searching the index in the named directory. */
  public LuceneIndexSearcher(String path) throws IOException {
    super(path);
  }

  /** Creates a searcher searching the index in the provided directory. */
  public LuceneIndexSearcher(Directory directory) throws IOException {
    super(directory);
  }

  /** Creates a searcher searching the provided index. */
  public LuceneIndexSearcher(IndexReader r) {
    super(r);
  }


  /**
   * Searches through the lucene index and returns an instance of TopDocs.
   */
  @Override
  public TopDocs search(Query query, Filter filter, final int nDocs)
          throws IOException {
    initializeTermPositions(); 
    return super.search(query, filter, nDocs);
  }
}
