/*
 *  Search.java
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

import java.util.List;

public interface Search{

  /** Sets coprus in which will doing search operations. */
  public void setCorpus(IndexedCorpus ic);

  /** Search in corpus with this query. Unlimited result length.*/
  public QueryResultList search(String query)
                         throws IndexException, SearchException;

  /** Search in corpus with this query.
   *  Size of the result list is limited. */
  public QueryResultList search(String query, int limit)
                         throws IndexException, SearchException;

  /** Search in corpus with this query.
   *  In each QueryResult will be added values of these fields.
   *  Result length is unlimited. */
  public QueryResultList search(String query, List<String> fieldNames)
                         throws IndexException, SearchException;

  /** Search in corpus with this query.
   *  In each QueryResult will be added values of these fields.
   *  Result length is limited. */
  public QueryResultList search(String query, int limit, List<String> fieldNames)
                         throws IndexException, SearchException;

}