/*
 *  SerialCorpusImpl.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 19/Oct/2001
 *
 *  $Id: SerialCorpusImpl.java 17841 2014-04-16 12:35:41Z markagreenwood $
 */

package gate.corpora;

import gate.Corpus;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.Resource;
import gate.creole.AbstractLanguageResource;
import gate.creole.CustomDuplication;
import gate.creole.ResourceInstantiationException;
import gate.creole.ir.IREngine;
import gate.creole.ir.IndexDefinition;
import gate.creole.ir.IndexException;
import gate.creole.ir.IndexManager;
import gate.creole.ir.IndexStatistics;
import gate.creole.ir.IndexedCorpus;
import gate.creole.metadata.CreoleResource;
import gate.event.CorpusEvent;
import gate.event.CorpusListener;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.event.DatastoreEvent;
import gate.event.DatastoreListener;
import gate.persist.PersistenceException;
import gate.util.Err;
import gate.util.GateRuntimeException;
import gate.util.MethodNotImplementedException;
import gate.util.Out;

import java.io.FileFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

// The initial design was to implement this on the basis of a WeakValueHashMap.
// However this creates problems, because the user might e.g., add a transient
// document to the corpus and then if the Document variable goes out of scope
// before sync() is called, nothing will be saved of the new document. Bad!
// Instead, to cope with the unloading for memory saving use, I implemented
// a documentUnload() method, which sets the in-memory copy to null but can
// always restore the doc, because it has its persistence ID.

@CreoleResource(name = "GATE Serial Corpus", isPrivate = true, comment = "GATE persistent corpus (serialisation)", icon = "corpus", helpURL = "http://gate.ac.uk/userguide/sec:developer:datastores")
public class SerialCorpusImpl extends AbstractLanguageResource 
    implements Corpus, CreoleListener, DatastoreListener, IndexedCorpus,
    CustomDuplication {

  /** Debug flag */
  private static final boolean DEBUG = false;

  static final long serialVersionUID = 3632609241787241616L;

  protected transient Vector<CorpusListener> corpusListeners;

  protected List<DocumentData> docDataList = null;

  // here I keep document index as key (same as the index in docDataList
  // which defines the document order) and Documents as value
  protected transient List<Document> documents = null;

  protected transient IndexManager indexManager = null;

  protected transient List<Document> addedDocs = null;

  protected transient List<String> removedDocIDs = null;

  protected transient List<Document> changedDocs = null;

  public SerialCorpusImpl() {
  }

  /**
   * Constructor to create a SerialCorpus from a transient one. This is
   * called by adopt() to store the transient corpus and re-route the
   * methods calls to it, until the corpus is sync-ed on disk. After
   * that, the transientCorpus will always be null, so the new
   * functionality will be used instead.
   */
  protected SerialCorpusImpl(Corpus tCorpus) {
    // copy the corpus name and features from the one in memory
    this.setName(tCorpus.getName());
    this.setFeatures(tCorpus.getFeatures());

    docDataList = new ArrayList<DocumentData>();
    // now cache the names of all docs for future use
    List<String> docNames = tCorpus.getDocumentNames();
    for(int i = 0; i < docNames.size(); i++) {
      Document doc = tCorpus.get(i);
      docDataList.add(new DocumentData(docNames.get(i), null, doc
              .getClass().getName()));
    }

    // copy all the documents from the transient corpus
    documents = new ArrayList<Document>();
    documents.addAll(tCorpus);

    // make sure we fire events when docs are added/removed/etc
    Gate.getCreoleRegister().addCreoleListener(this);
  }

  /**
   * Gets the names of the documents in this corpus.
   * 
   * @return a {@link List} of Strings representing the names of the
   *         documents in this corpus.
   */
  @Override
  public List<String> getDocumentNames() {
    List<String> docsNames = new ArrayList<String>();
    if(docDataList == null) return docsNames;
    for(Object aDocDataList : docDataList) {
      DocumentData data = (DocumentData)aDocDataList;
      docsNames.add(data.getDocumentName());
    }
    return docsNames;
  }

  /**
   * Gets the persistent IDs of the documents in this corpus.
   * 
   * @return a {@link List} of Objects representing the persistent IDs
   *         of the documents in this corpus.
   */
  public List<Object> getDocumentPersistentIDs() {
    List<Object> docsIDs = new ArrayList<Object>();
    if(docDataList == null) return docsIDs;
    Iterator<DocumentData> iter = docDataList.iterator();
    while(iter.hasNext()) {
      DocumentData data = iter.next();
      docsIDs.add(data.getPersistentID());
    }
    return docsIDs;
  }

  /**
   * Gets the persistent IDs of the documents in this corpus.
   * 
   * @return a {@link List} of Objects representing the persistent IDs
   *         of the documents in this corpus.
   */
  public List<String> getDocumentClassTypes() {
    List<String> docsIDs = new ArrayList<String>();
    if(docDataList == null) return docsIDs;
    Iterator<DocumentData> iter = docDataList.iterator();
    while(iter.hasNext()) {
      DocumentData data = iter.next();
      docsIDs.add(data.getClassType());
    }
    return docsIDs;
  }

  /**
   * This method should only be used by the Serial Datastore to set
   */
  public void setDocumentPersistentID(int index, Object persID) {
    if(index >= docDataList.size()) return;
    docDataList.get(index).setPersistentID(persID);
    if(DEBUG) Out.prln("IDs are now: " + docDataList);
  }

  /**
   * Gets the name of a document in this corpus.
   * 
   * @param index the index of the document
   * @return a String value representing the name of the document at
   *         <tt>index</tt> in this corpus.
   *         <P>
   */
  @Override
  public String getDocumentName(int index) {
    if(index >= docDataList.size()) return "No such document";

    return docDataList.get(index).getDocumentName();
  }

  /**
   * Gets the persistent ID of a document in this corpus.
   * 
   * @param index the index of the document
   * @return a value representing the persistent ID of the document at
   *         <tt>index</tt> in this corpus.
   *         <P>
   */
  public Object getDocumentPersistentID(int index) {
    if(index >= docDataList.size()) return null;
    return docDataList.get(index).getPersistentID();
  }

  public String getDocumentClassType(int index) {
    if(index >= docDataList.size()) return null;
    return docDataList.get(index).getClassType();
  }

  /**
   * Unloads a document from memory.
   * 
   * @param index the index of the document to be unloaded.
   * @param sync should the document be sync'ed (i.e. saved) before
   *          unloading.
   */
  public void unloadDocument(int index, boolean sync) {
    // 1. check whether its been loaded and is a persistent one
    // if a persistent doc is not loaded, there's nothing we need to do
    if((!isDocumentLoaded(index)) && isPersistentDocument(index)) return;
    // 2. If requested, sync the document before releasing it from
    // memory,
    // because the creole register garbage collects all LRs which are
    // not used
    // any more
    if(sync) {
      Document doc = documents.get(index);
      try {
        // if the document is not already adopted, we need to do that
        // first
        if(doc.getLRPersistenceId() == null) {
          doc = (Document)this.getDataStore().adopt(doc);
          this.getDataStore().sync(doc);
          this.setDocumentPersistentID(index, doc.getLRPersistenceId());
        }
        else // if it is adopted, just sync it
        this.getDataStore().sync(doc);
      }
      catch(PersistenceException ex) {
        throw new GateRuntimeException("Error unloading document from corpus"
                + "because document sync failed: " + ex.getMessage(), ex);
      }
    }
    // 3. remove the document from the memory
    // do this, only if the saving has succeeded
    documents.set(index, null);
  }

  /**
   * Unloads a document from memory
   * 
   * @param doc the document to be unloaded
   * @param sync should the document be sync'ed (i.e. saved) before
   *          unloading.
   */
  public void unloadDocument(Document doc, boolean sync) {
    if(DEBUG) Out.prln("Document to be unloaded :" + doc.getName());
    // 1. determine the index of the document; if not there, do nothing
    int index = findDocument(doc);
    if(index == -1) return;
    if(DEBUG) Out.prln("Index of doc: " + index);
    if(DEBUG) Out.prln("Size of corpus: " + documents.size());
    unloadDocument(index, sync);
    // documents.remove(new Integer(index));
  }

  /**
   * Unloads a document from memory, calling sync() first, to store the
   * changes.
   * 
   * @param doc the document to be unloaded.
   */
  @Override
  public void unloadDocument(Document doc) {
    unloadDocument(doc, true);
  }

  /**
   * Unloads the document from memory, calling sync() first, to store
   * the changes.
   * 
   * @param index the index of the document to be unloaded.
   */
  public void unloadDocument(int index) {
    unloadDocument(index, true);
  }

  /**
   * This method returns true when the document is already loaded in
   * memory
   */
  @Override
  public boolean isDocumentLoaded(int index) {
    if(documents == null || documents.isEmpty()) return false;
    return documents.get(index) != null;
  }

  /**
   * This method returns true when the document is already stored on
   * disk i.e., is not transient
   */
  public boolean isPersistentDocument(int index) {
    if(documents == null || documents.isEmpty()) return false;
    return (docDataList.get(index).getPersistentID() != null);
  }

  /**
   * Every LR that is a CreoleListener (and other Listeners too) must
   * override this method and make sure it removes itself from the
   * objects which it has been listening to. Otherwise, the object will
   * not be released from memory (memory leak!).
   */
  @Override
  public void cleanup() {
    if(DEBUG) Out.prln("serial corpus cleanup called");
    if(corpusListeners != null) corpusListeners = null;
    if(documents != null) documents.clear();
    docDataList.clear();
    Gate.getCreoleRegister().removeCreoleListener(this);
    if(this.dataStore != null) {
      this.dataStore.removeDatastoreListener(this);
    }
  }

  /**
   * Fills this corpus with documents created from files in a directory.
   * 
   * @param filter the file filter used to select files from the target
   *          directory. If the filter is <tt>null</tt> all the files
   *          will be accepted.
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown. An implementation for this method is
   *          provided as a static method at
   *          {@link gate.corpora.CorpusImpl#populate(Corpus, URL, FileFilter, String, boolean)}
   *          .
   * @param encoding the encoding to be used for reading the documents
   * @param recurseDirectories should the directory be parsed
   *          recursively?. If <tt>true</tt> all the files from the
   *          provided directory and all its children directories (on as
   *          many levels as necessary) will be picked if accepted by
   *          the filter otherwise the children directories will be
   *          ignored.
   */
  @Override
  public void populate(URL directory, FileFilter filter, String encoding,
          boolean recurseDirectories) throws IOException,
          ResourceInstantiationException {
    CorpusImpl.populate(this, directory, filter, encoding, recurseDirectories);
  }

  /**
   * Fills this corpus with documents created from files in a directory.
   * 
   * @param filter the file filter used to select files from the target
   *          directory. If the filter is <tt>null</tt> all the files
   *          will be accepted.
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown. An implementation for this method is
   *          provided as a static method at
   *          {@link gate.corpora.CorpusImpl#populate(Corpus, URL, FileFilter, String, boolean)}
   *          .
   * @param encoding the encoding to be used for reading the documents
   * @param recurseDirectories should the directory be parsed
   *          recursively?. If <tt>true</tt> all the files from the
   *          provided directory and all its children directories (on as
   *          many levels as necessary) will be picked if accepted by
   *          the filter otherwise the children directories will be
   *          ignored.
   */
  @Override
  public void populate(URL directory, FileFilter filter, String encoding,
          String mimeType, boolean recurseDirectories) throws IOException,
          ResourceInstantiationException {
    CorpusImpl.populate(this, directory, filter, encoding, mimeType,
            recurseDirectories);
  }

  /**
   * Fills the provided corpus with documents extracted from the
   * provided single concatenated file.
   * 
   * @param singleConcatenatedFile the single concatenated file.
   * @param documentRootElement content between the start and end of
   *          this element is considered for documents.
   * @param encoding the encoding of the trec file.
   * @param numberOfFilesToExtract indicates the number of files to
   *          extract from the trecweb file.
   * @param documentNamePrefix the prefix to use for document names when
   *          creating from
   * @param mimeType the mime type which determines how the document is handled
   * @return total length of populated documents in the corpus in number
   *         of bytes
   */  
  @Override
  public long populate(URL singleConcatenatedFile, String documentRootElement,
          String encoding, int numberOfFilesToExtract,
          String documentNamePrefix, String mimeType, boolean includeRootElement) throws IOException,
          ResourceInstantiationException {
    return CorpusImpl.populate(this, singleConcatenatedFile,
            documentRootElement, encoding, numberOfFilesToExtract,
            documentNamePrefix, mimeType, includeRootElement);
  }

  @Override
  public synchronized void removeCorpusListener(CorpusListener l) {
    if(corpusListeners != null && corpusListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<CorpusListener> v = (Vector<CorpusListener>)corpusListeners.clone();
      v.removeElement(l);
      corpusListeners = v;
    }
  }

  @Override
  public synchronized void addCorpusListener(CorpusListener l) {
    @SuppressWarnings("unchecked")
    Vector<CorpusListener> v = corpusListeners == null
            ? new Vector<CorpusListener>(2)
            : (Vector<CorpusListener>)corpusListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      corpusListeners = v;
    }
  }

  protected void fireDocumentAdded(CorpusEvent e) {
    if(corpusListeners != null) {
      Vector<CorpusListener> listeners = corpusListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).documentAdded(e);
      }
    }
  }

  protected void fireDocumentRemoved(CorpusEvent e) {
    if(corpusListeners != null) {
      Vector<CorpusListener> listeners = corpusListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).documentRemoved(e);
      }
    }
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    Resource res = e.getResource();
    if(res instanceof Document) {
      Document doc = (Document)res;
      if(DEBUG) Out.prln("resource Unloaded called ");
      // remove from the corpus too, if a transient one
      if(doc.getDataStore() != this.getDataStore()) {
        this.remove(doc);
      }
      else {
        // unload all occurences
        int index = indexOf(res);
        if(index < 0) return;
        documents.set(index, null);
        if(DEBUG)
          Out.prln("corpus: document " + index + " unloaded and set to null");
      } // if
    }
  }

  @Override
  public void datastoreOpened(CreoleEvent e) {
  }

  @Override
  public void datastoreCreated(CreoleEvent e) {
  }

  @Override
  public void datastoreClosed(CreoleEvent e) {
    if(!e.getDatastore().equals(this.getDataStore())) return;
    if(this.getDataStore() != null)
      this.getDataStore().removeDatastoreListener(this);
    // close this corpus, since it cannot stay open when the DS it comes
    // from
    // is closed
    Factory.deleteResource(this);
  }

  /**
   * Called by a datastore when a new resource has been adopted
   */
  @Override
  public void resourceAdopted(DatastoreEvent evt) {
  }

  /**
   * Called by a datastore when a resource has been deleted
   */
  @Override
  public void resourceDeleted(DatastoreEvent evt) {
    DataStore ds = (DataStore)evt.getSource();
    // 1. check whether this datastore fired the event. If not, return.
    if(!ds.equals(this.dataStore)) return;

    Object docID = evt.getResourceID();
    if(docID == null) return;

    if(DEBUG) Out.prln("Resource deleted called for: " + docID);
    // first check if it is this corpus that's been deleted, it must be
    // unloaded immediately
    if(docID.equals(this.getLRPersistenceId())) {
      Factory.deleteResource(this);
      return;
    }// if

    boolean isDirty = false;
    // the problem here is that I only have the doc persistent ID
    // and nothing else, so I need to determine the index of the doc
    // first
    for(int i = 0; i < docDataList.size(); i++) {
      DocumentData docData = docDataList.get(i);
      // we've found the correct document
      // don't break the loop, because it might appear more than once
      if(docID.equals(docData.getPersistentID())) {
        if(evt.getResource() == null) {
          // instead of calling remove() which tries to load the
          // document
          // remove it from the documents and docDataList
          documentRemoved(docDataList.get(i).persistentID
                  .toString());
          docDataList.remove(i);
          documents.remove(i);
          isDirty = true;
          i--;
          continue;
        }

        remove(i);
        isDirty = true;
      }// if
    }// for loop through the doc data

    if(isDirty) try {
      this.dataStore.sync(this);
    }
    catch(PersistenceException ex) {
      throw new GateRuntimeException("SerialCorpusImpl: " + ex.getMessage());
    }
    catch(SecurityException sex) {
      throw new GateRuntimeException("SerialCorpusImpl: " + sex.getMessage());
    }
  }// resourceDeleted

  /**
   * Called by a datastore when a resource has been wrote into the
   * datastore
   */
  @Override
  public void resourceWritten(DatastoreEvent evt) {
    if(evt.getResourceID().equals(this.getLRPersistenceId())) {
      thisResourceWritten();
    }
  }

  // List methods
  // java docs will be automatically copied from the List interface.

  @Override
  public int size() {
    return docDataList.size();
  }

  @Override
  public boolean isEmpty() {
    return docDataList.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    // return true if:
    // - the document data list contains a document with such a name
    // and persistent id

    if(!(o instanceof Document)) return false;

    int index = findDocument((Document)o);
    if(index < 0)
      return false;
    else return true;
  }

  @Override
  public Iterator<Document> iterator() {
    return new Iterator<Document>() {
      Iterator<DocumentData> docDataIter = docDataList.iterator();

      @Override
      public boolean hasNext() {
        return docDataIter.hasNext();
      }

      @Override
      public Document next() {

        // try finding a document with the same name and persistent ID
        DocumentData docData = docDataIter.next();
        int index = docDataList.indexOf(docData);
        return SerialCorpusImpl.this.get(index);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("SerialCorpusImpl does not "
                + "support remove in the iterators");
      }
    }; // return

  }// iterator

  @Override
  public String toString() {
    return "document data " + docDataList.toString() + " documents "
            + documents;
  }

  @Override
  public Object[] toArray() {
    // there is a problem here, because some docs might not be
    // instantiated
    throw new MethodNotImplementedException(
            "toArray() is not implemented for SerialCorpusImpl");
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // there is a problem here, because some docs might not be
    // instantiated
    throw new MethodNotImplementedException(
            "toArray(Object[] a) is not implemented for SerialCorpusImpl");
  }

  @Override
  public boolean add(Document o) {
    if(o == null) return false;
    Document doc = o;

    // make it accept only docs from its own datastore
    if(doc.getDataStore() != null && !this.dataStore.equals(doc.getDataStore())) {
      Err.prln("Error: Persistent corpus can only accept documents "
              + "from its own datastore!");
      return false;
    }// if

    // add the document with its index in the docDataList
    // in this case, since it's going to be added to the end
    // the index will be the size of the docDataList before
    // the addition
    DocumentData docData = new DocumentData(doc.getName(), doc
            .getLRPersistenceId(), doc.getClass().getName());
    boolean result = docDataList.add(docData);
    documents.add(doc);
    documentAdded(doc);
    fireDocumentAdded(new CorpusEvent(SerialCorpusImpl.this, doc, docDataList
            .size() - 1, doc.getLRPersistenceId(), CorpusEvent.DOCUMENT_ADDED));

    return result;
  }

  @Override
  public boolean remove(Object o) {
    if(DEBUG) Out.prln("SerialCorpus:Remove object called");
    if(!(o instanceof Document)) return false;
    Document doc = (Document)o;

    // see if we can find it first. If not, then judt return
    int index = findDocument(doc);
    if(index == -1) return false;

    if(index < docDataList.size()) { // we found it, so remove it
      // by Andrey Shafirin: this part of code can produce an exception
      // if
      // document wasn't loaded
      String docName = docDataList.get(index).getDocumentName();
      Object docPersistentID = getDocumentPersistentID(index);
      docDataList.remove(index);
      // Document oldDoc = (Document) documents.remove(index);
      documents.remove(index);
      // if (DEBUG) Out.prln("documents after remove of " +
      // oldDoc.getName()
      // + " are " + documents);
      if(DEBUG)
        Out.prln("documents after remove of " + docName + " are " + documents);
      // documentRemoved(oldDoc.getLRPersistenceId().toString());
      if(docPersistentID != null) documentRemoved(docPersistentID.toString());
      // fireDocumentRemoved(new CorpusEvent(SerialCorpusImpl.this,
      // oldDoc,
      // index,
      // CorpusEvent.DOCUMENT_REMOVED));
      fireDocumentRemoved(new CorpusEvent(SerialCorpusImpl.this, (Document)o,
              index, docPersistentID, CorpusEvent.DOCUMENT_REMOVED));
    }

    return true;
  }

  public int findDocument(Document doc) {
    boolean found = false;
    DocumentData docData = null;

    // first try finding the document in memory
    int index = documents.indexOf(doc);
    if(index > -1 && index < docDataList.size()) return index;

    // else try finding a document with the same name and persistent ID
    Iterator<DocumentData> iter = docDataList.iterator();
    for(index = 0; iter.hasNext(); index++) {
      docData = iter.next();
      if(docData.getDocumentName().equals(doc.getName())
              && docData.getPersistentID().equals(doc.getLRPersistenceId())
              && docData.getClassType().equals(doc.getClass().getName())) {
        found = true;
        break;
      }
    }
    if(found && index < docDataList.size())
      return index;
    else return -1;
  }// findDocument

  @Override
  public boolean containsAll(Collection<?> c) {
    Iterator<?> iter = c.iterator();
    while(iter.hasNext()) {
      if(!contains(iter.next())) return false;
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends Document> c) {
    boolean allAdded = true;
    Iterator<? extends Document> iter = c.iterator();
    while(iter.hasNext()) {
      if(!add(iter.next())) allAdded = false;
    }
    return allAdded;
  }

  @Override
  public boolean addAll(int index, Collection<? extends Document> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean allRemoved = true;
    Iterator<?> iter = c.iterator();
    while(iter.hasNext()) {
      if(!remove(iter.next())) allRemoved = false;
    }
    return allRemoved;

  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    documents.clear();
    docDataList.clear();
  }

  @Override
  public boolean equals(Object o) {
    if(o == null) return false;
    if(!(o instanceof SerialCorpusImpl)) return false;
    SerialCorpusImpl oCorpus = (SerialCorpusImpl)o;    
    if(oCorpus == this) return true;
    if((oCorpus.lrPersistentId == this.lrPersistentId || (this.lrPersistentId != null && this.lrPersistentId
            .equals(oCorpus.lrPersistentId)))
            && oCorpus.name.equals(this.name)
            && (oCorpus.dataStore == this.dataStore || oCorpus.dataStore
                    .equals(this.dataStore))
            && oCorpus.docDataList.equals(docDataList)) return true;
    return false;
  }

  @Override
  public int hashCode() {
    return docDataList.hashCode();
  }

  @Override
  public Document get(int index) {
    if(index >= docDataList.size()) return null;

    Document res = documents.get(index);

    if(DEBUG)
      Out.prln("SerialCorpusImpl: get(): index " + index + "result: " + res);

    // if the document is null, then I must get it from the DS
    if(res == null) {
      FeatureMap parameters = Factory.newFeatureMap();
      parameters.put(DataStore.DATASTORE_FEATURE_NAME, this.dataStore);
      try {
        parameters.put(DataStore.LR_ID_FEATURE_NAME, docDataList
                .get(index).getPersistentID());
        Document lr = (Document) Factory.createResource(docDataList
                .get(index).getClassType(), parameters);
        if(DEBUG) Out.prln("Loaded document :" + lr.getName());
        // change the result to the newly loaded doc
        res = lr;

        // finally replace the doc with the instantiated version
        documents.set(index, lr);
      }
      catch(ResourceInstantiationException ex) {
        Err.prln("Error reading document inside a serialised corpus.");
        throw new GateRuntimeException(ex);
      }
    }

    return res;
  }

  @Override
  public Document set(int index, Document element) {
    throw new gate.util.MethodNotImplementedException();
    // fire the 2 events
    /*
     * fireDocumentRemoved(new CorpusEvent(SerialCorpusImpl.this,
     * oldDoc, ((Integer) key).intValue(),
     * CorpusEvent.DOCUMENT_REMOVED)); fireDocumentAdded(new
     * CorpusEvent(SerialCorpusImpl.this, newDoc, ((Integer)
     * key).intValue(), CorpusEvent.DOCUMENT_ADDED));
     */
  }

  @Override
  public void add(int index, Document o) {
    if(o == null) return;
    Document doc = o;

    DocumentData docData = new DocumentData(doc.getName(), doc
            .getLRPersistenceId(), doc.getClass().getName());
    docDataList.add(index, docData);

    documents.add(index, doc);
    documentAdded(doc);
    fireDocumentAdded(new CorpusEvent(SerialCorpusImpl.this, doc, index, doc
            .getLRPersistenceId(), CorpusEvent.DOCUMENT_ADDED));

  }

  @Override
  public Document remove(int index) {
    if(DEBUG) Out.prln("Remove index called");
    // try to get the actual document if it was loaded
    Document res = isDocumentLoaded(index) ? get(index) : null;
    Object docLRID = docDataList.get(index).persistentID;
    if(docLRID != null) documentRemoved(docLRID.toString());
    docDataList.remove(index);
    documents.remove(index);
    fireDocumentRemoved(new CorpusEvent(SerialCorpusImpl.this, res, index,
            docLRID, CorpusEvent.DOCUMENT_REMOVED));
    return res;
  }

  @Override
  public int indexOf(Object o) {
    if(o instanceof Document) return findDocument((Document)o);

    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new gate.util.MethodNotImplementedException();
  }

  @Override
  public ListIterator<Document> listIterator() {
    throw new gate.util.MethodNotImplementedException();
  }

  @Override
  public ListIterator<Document> listIterator(int index) {
    throw new gate.util.MethodNotImplementedException();
  }

  /**
   * persistent Corpus does not support this method as all the documents
   * might no be in memory
   */
  @Override
  public List<Document> subList(int fromIndex, int toIndex) {
    throw new gate.util.MethodNotImplementedException();
  }

  @Override
  public void setDataStore(DataStore dataStore)
          throws gate.persist.PersistenceException {
    super.setDataStore(dataStore);
    if(this.dataStore != null) this.dataStore.addDatastoreListener(this);
  }

  public void setTransientSource(Object source) {
    if(!(source instanceof Corpus)) return;

    // the following initialisation is only valid when we're
    // constructing
    // this object from a transient one. If it has already been stored
    // in
    // a datastore, then the initialisation is done in readObject()
    // since
    // this method is the one called by serialisation, when objects
    // are restored.
    if(this.dataStore != null && this.lrPersistentId != null) return;

    Corpus tCorpus = (Corpus)source;

    // copy the corpus name and features from the one in memory
    this.setName(tCorpus.getName());
    this.setFeatures(tCorpus.getFeatures());

    docDataList = new ArrayList<DocumentData>();
    // now cache the names of all docs for future use
    List<String> docNames = tCorpus.getDocumentNames();
    for(int i = 0; i < docNames.size(); i++) {
      Document aDoc = tCorpus.get(i);
      docDataList.add(new DocumentData(docNames.get(i), null, aDoc
              .getClass().getName()));
    }

    // copy all the documents from the transient corpus
    documents = new ArrayList<Document>();
    documents.addAll(tCorpus);

    this.addedDocs = new Vector<Document>();
    this.removedDocIDs = new Vector<String>();
    this.changedDocs = new Vector<Document>();

    // make sure we fire events when docs are added/removed/etc
    Gate.getCreoleRegister().addCreoleListener(this);

  }

  // we don't keep the transient source, so always return null
  // Sill this must be implemented, coz of the GUI and Factory
  public Object getTransientSource() {
    return null;
  }

  @Override
  public Resource init() throws gate.creole.ResourceInstantiationException {
    super.init();

    return this;

  }

  /**
   * readObject - calls the default readObject() and then initialises
   * the transient data
   * 
   * @serialData Read serializable fields. No optional data read.
   */
  private void readObject(ObjectInputStream s) throws IOException,
          ClassNotFoundException {
    s.defaultReadObject();
    documents = new ArrayList<Document>(docDataList.size());
    for(int i = 0; i < docDataList.size(); i++)
      documents.add(null);
    corpusListeners = new Vector<CorpusListener>();
    // finally set the creole listeners if the LR is like that
    Gate.getCreoleRegister().addCreoleListener(this);
    if(this.dataStore != null) this.dataStore.addDatastoreListener(this);

    // if indexed construct the manager.
    /*IndexDefinition definition = (IndexDefinition)this.getFeatures().get(
            GateConstants.CORPUS_INDEX_DEFINITION_FEATURE_KEY);
    if(definition != null) {
      String className = definition.getIrEngineClassName();
      try {
        // Class aClass = Class.forName(className);
        Class<?> aClass = Class.forName(className, true, Gate.getClassLoader());
        IREngine engine = (IREngine)aClass.newInstance();
        this.indexManager = engine.getIndexmanager();
        this.indexManager.setIndexDefinition(definition);
        this.indexManager.setCorpus(this);
      }
      catch(Exception e) {
        e.printStackTrace(Err.getPrintWriter());
      }
      // switch (definition.getIndexType()) {
      // case GateConstants.IR_LUCENE_INVFILE:
      // this.indexManager = new LuceneIndexManager();
      // this.indexManager.setIndexDefinition(definition);
      // this.indexManager.setCorpus(this);
      // break;
      // }
      this.addedDocs = new Vector<Document>();
      this.removedDocIDs = new Vector<String>();
      this.changedDocs = new Vector<Document>();
    }*/
  }// readObject

  @Override
  public void setIndexDefinition(IndexDefinition definition) {
    if(definition != null) {
      this.getFeatures().put(GateConstants.CORPUS_INDEX_DEFINITION_FEATURE_KEY,
              definition);

      String className = definition.getIrEngineClassName();
      try {
        // Class aClass = Class.forName(className);
        Class<?> aClass = Class.forName(className, true, Gate.getClassLoader());
        IREngine engine = (IREngine)aClass.newInstance();
        this.indexManager = engine.getIndexmanager();
        this.indexManager.setIndexDefinition(definition);
        this.indexManager.setCorpus(this);
      }
      catch(Exception e) {
        e.printStackTrace(Err.getPrintWriter());
      }
      // switch (definition.getIndexType()) {
      // case GateConstants.IR_LUCENE_INVFILE:
      // this.indexManager = new LuceneIndexManager();
      // this.indexManager.setIndexDefinition(definition);
      // this.indexManager.setCorpus(this);
      // break;
      // }
      this.addedDocs = new Vector<Document>();
      this.removedDocIDs = new Vector<String>();
      this.changedDocs = new Vector<Document>();
    }
  }

  @Override
  public IndexDefinition getIndexDefinition() {
    return (IndexDefinition)this.getFeatures().get(
            GateConstants.CORPUS_INDEX_DEFINITION_FEATURE_KEY);
  }

  @Override
  public IndexManager getIndexManager() {
    return this.indexManager;
  }

  @Override
  public IndexStatistics getIndexStatistics() {
    return (IndexStatistics)this.getFeatures().get(
            GateConstants.CORPUS_INDEX_STATISTICS_FEATURE_KEY);
  }

  private void documentAdded(Document doc) {
    if(indexManager != null) {
      addedDocs.add(doc);
    }
  }

  private void documentRemoved(String lrID) {
    if(indexManager != null) {
      removedDocIDs.add(lrID);
    }
  }

  private void thisResourceWritten() {
    if(indexManager != null) {
      try {
        for(int i = 0; i < documents.size(); i++) {
          if(documents.get(i) != null) {
            Document doc = documents.get(i);
            if(!addedDocs.contains(doc) && doc.isModified()) {
              changedDocs.add(doc);
            }
          }
        }
        indexManager.sync(addedDocs, removedDocIDs, changedDocs);
      }
      catch(IndexException ie) {
        ie.printStackTrace();
      }
    }
  }

  /**
   * SerialCorpusImpl does not support duplication.
   */
  @Override
  public Resource duplicate(Factory.DuplicationContext ctx)
          throws ResourceInstantiationException {
    throw new ResourceInstantiationException("Duplication of "
            + this.getClass().getName() + " not permitted");
  }

}
