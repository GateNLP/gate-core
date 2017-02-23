/*
 *  Corpus.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 19/Jan/2000
 *
 *  $Id: Corpus.java 17467 2014-02-27 12:54:45Z markagreenwood $
 */

package gate;

import gate.event.CorpusListener;

/** Corpora are lists of Document. TIPSTER equivalent: Collection.
  */
public interface Corpus extends SimpleCorpus {

  /**
   * Unloads the document from memory. Only needed if memory
   * preservation is an issue. Only supported for Corpus which is
   * stored in a Datastore. To get this document back in memory,
   * use get() on Corpus or if you have its persistent ID, request it
   * from the Factory.
   * <P>
   * Transient Corpus objects do nothing,
   * because there would be no way to get the document back
   * again afterwards.
   * @param doc Document to be unloaded from memory.
   */
  public void unloadDocument(Document doc);

  /**
   * This method returns true when the document is already loaded in memory.
   * The transient corpora will always return true as they can only contain
   * documents that are present in the memory.
   */
  public boolean isDocumentLoaded(int index);


  /**
   * Removes one of the listeners registered with this corpus.
   * @param l the listener to be removed.
   */
  public void removeCorpusListener(CorpusListener l);

  /**
   * Registers a new {@link CorpusListener} with this corpus.
   * @param l the listener to be added.
   */
  public void addCorpusListener(CorpusListener l);

} // interface Corpus
