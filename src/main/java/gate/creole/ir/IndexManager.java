/*
 *  Indexmanager.java
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

import gate.Corpus;
import gate.Document;

import java.util.List;

public interface IndexManager{

  /**
   * Gets the corpus this index manages will index.
   * @return a {@link gate.Corpus} value;
   */
  public Corpus getCorpus();

  /**
   * Sets the corpus this index manages will index.
   * @param corpus a {@link gate.Corpus} value;
   */
  public void setCorpus(Corpus corpus);

  /**
   * Gets the index definition for this index manager.
   * @return a {@link IndexDefinition} value.
   */
  public IndexDefinition getIndexDefinition();

  /**
   * Sets the index definition for this index manager.
   * @param indexDefinition a {@link IndexDefinition} value.
   */
  public void setIndexDefinition(IndexDefinition indexDefinition);


  /** Creates index directory and indexing all
   *  documents in the corpus. */
  public void createIndex() throws IndexException;

  /** Optimize the existing index*/
  public void optimizeIndex() throws IndexException;

  /** Delete all index files and directories in index location. */
  public void deleteIndex() throws IndexException;

  /** Reindexing changed documents, removing removed documents and
   *  add to the index new corpus documents. */
  public void sync(List<Document> added, List<String> removed, List<Document> changed) throws IndexException;


}