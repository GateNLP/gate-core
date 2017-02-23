/*
 *  QueryResult.java
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

public class QueryResult{

  /** Persistance document ID.*/
  private Object docID;

  /** Score(relevance) of the result between 0 and 1 */
  private float relevance;

  /** List of Terms*/
  private List<Term> fieldValues;

  /** Constructor of the class. */
  public QueryResult(Object docID,float relevance, List<Term> fieldValues){
    this.docID = docID;
    this.relevance = relevance;
    this.fieldValues = fieldValues;
  }

  /** @return persistance document ID.*/
  public Object getDocumentID(){
    return docID;
  }

  /** @return relevance of this result. */
  public float getScore(){
    return relevance;
  }

  /** returns certain document fields (if specified) from the search() call */
  public List<Term> getFields(){
    return fieldValues;
  }

}