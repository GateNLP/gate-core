/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 13/07/2001
 *
 *  $Id: CorpusEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */

package gate.event;

import gate.Corpus;
import gate.Document;

/**
 * Models events fired by corpora when documents are added or removed.
 */
public class CorpusEvent extends GateEvent {

  private static final long serialVersionUID = -1499954680735513011L;

  /**
   * Event type that is fired when a new document is added to a corpus
   */
  public final static int DOCUMENT_ADDED = 401;

  /**
   * Event type that is fired when a document is removed from a corpus
   */
  public final static int DOCUMENT_REMOVED = 402;

  /**
   * Creates a new CorpusEvent.
   * @param source the corpus that fires the event
   * @param doc the document this event refers to
   * @param type the type of event ({@link #DOCUMENT_ADDED} or
   * {@link #DOCUMENT_REMOVED}).
   */
  public CorpusEvent(Corpus source, Document doc, int index, int type){
    this(source, doc, index, null, type);
  }

  /**
   * Creates a new CorpusEvent.
   * @param source the corpus that fires the event
   * @param doc the document this event refers to
   * @param documentLRID the persistence ID of the document that has been added
   * or removed.
   * @param type the type of event ({@link #DOCUMENT_ADDED} or
   * {@link #DOCUMENT_REMOVED}).
   */
  public CorpusEvent(Corpus source, Document doc, int index, 
          Object documentLRID, int type){
    super(source, type);
    this.document = doc;
    this.documentIndex = index;
    this.documentLRID = documentLRID;
  }
  
  /**
   * Gets the dcument this event refers to
   */
  public gate.Document getDocument() {
    return document;
  }

  /**
   * Gets the index of the document this event refers to
   */
  public int getDocumentIndex() {
    return this.documentIndex;
  }

  
  /**
   * Gets the persistence ID of the document to which this event refers.
   * This value could be <code>null</code>, if the document does not have a 
   * persistence ID.
   * @return the documentLRID
   */
  public Object getDocumentLRID() {
    return documentLRID;
  }


  /**
   * The document that has been added/removed.
   */
  private gate.Document document;
  
  /**
   * The index of the document which has been removed. Needed because
   * the document itself might not have been loaded in memory, so the
   * index could be used instead.
   */
  private int documentIndex;
  
  /**
   * The persistence ID of the document to which this event refers.
   */
  private Object documentLRID;
  
}

