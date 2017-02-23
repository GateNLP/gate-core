/*
 *  QueryResultList.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 19/Apr/2002
 *
 */

package gate.creole.ir;

import java.util.Iterator;
import java.util.List;

public class QueryResultList{

  /** Executed query. */
  private String queryString;

  /** Corpus in which query was execute. */
  private IndexedCorpus corpus;

  /** List of QueryResult objects. */
  private List<QueryResult> results;

  /* Niraj */
  /* Default Constructor */
  // do not delete this as it is must to subclass this class
  public QueryResultList() {
  }
  /* End */

  /** Constructor of the class. */
  public QueryResultList(String query, IndexedCorpus corpus, List<QueryResult> results){
    this.queryString = query;
    this.corpus = corpus;
    this.results = results;
  }

  /** @return String executed query */
  public String getQueryString(){
    return queryString;
  }

  /** @return IndexedCorpus corpus where this query was execute. */
  public IndexedCorpus getQueryCorpus(){
    return corpus;
  }

  /** @return Iterator of QueryResult objects.
   *  @see gate.creole.ir.QueryResult */
  public Iterator<QueryResult> getQueryResults(){
    return results.iterator();
  }
}