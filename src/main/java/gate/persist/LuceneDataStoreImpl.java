package gate.persist;

import gate.Corpus;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageResource;
import gate.Resource;
import gate.corpora.SerialCorpusImpl;
import gate.creole.ResourceInstantiationException;
import gate.creole.annic.Constants;
import gate.creole.annic.Hit;
import gate.creole.annic.IndexException;
import gate.creole.annic.Indexer;
import gate.creole.annic.SearchException;
import gate.creole.annic.SearchableDataStore;
import gate.creole.annic.Searcher;
import gate.creole.annic.lucene.LuceneIndexer;
import gate.creole.annic.lucene.LuceneSearcher;
import gate.event.CorpusEvent;
import gate.event.CorpusListener;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.util.Files;
import gate.util.GateRuntimeException;
import gate.util.Strings;
import gate.util.persistence.PersistenceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;

public class LuceneDataStoreImpl extends SerialDataStore implements
                                                        SearchableDataStore,
                                                        CorpusListener,
                                                        CreoleListener {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 3618696392336421680L;

  /**
   * To store canonical lock objects for each LR ID.
   */
  protected Map<Object, LabelledSoftReference> lockObjects =
          new HashMap<Object, LabelledSoftReference>();

  /**
   * Reference queue with which the soft references in the lockObjects
   * map will be registered.
   */
  protected ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();

  /**
   * Indicates if the datastore is being closed.
   */
  protected boolean dataStoreClosing = false;

  /**
   * Executor to run the indexing tasks
   */
  protected ScheduledThreadPoolExecutor executor;

  /**
   * Map keeping track of the most recent indexing task for each LR ID.
   */
  protected ConcurrentMap<Object, IndexingTask> currentTasks =
          new ConcurrentHashMap<Object, IndexingTask>();

  /**
   * Number of milliseconds we should wait after a sync before
   * attempting to re-index a document. If sync is called again for the
   * same document within this time then the timer for the re-indexing
   * task is reset. Thus if several changes to the same document are
   * made in quick succession it will only be re-indexed once. On the
   * other hand, if the delay is set too long the document may never be
   * indexed until the data store is closed. The default delay is 1000
   * (one second).
   */
  protected long indexDelay = 1000L;

  /**
   * Indexer to be used for indexing documents
   */
  protected Indexer indexer;

  /**
   * Index Parameters
   */
  protected Map<String,Object> indexParameters;

  /**
   * URL of the index
   */
  protected URL indexURL;

  /**
   * Searcher to be used for searching the indexed documents
   */
  protected Searcher searcher;

  /**
   * This is where we store the search parameters
   */
  protected Map<String,Object> searchParameters;

  /** Close the data store. */
  @Override
  public void close() throws PersistenceException {
    // stop listening to Creole events
    Gate.getCreoleRegister().removeCreoleListener(this);
    // shut down the executor. We submit the shutdown request
    // as a zero-delay task rather than calling shutdown directly,
    // in order to interrupt any timed wait currently in progress.
    executor.execute(new Runnable() {
      @Override
      public void run() {
        executor.shutdown();
      }
    });
    try {
      // allow up to two minutes for indexing to finish
      executor.awaitTermination(120, TimeUnit.SECONDS);
    } catch(InterruptedException e) {
      // propagate the interruption
      Thread.currentThread().interrupt();
    }

    // At this point, any in-progress indexing tasks have
    // finished. We now process any tasks that were queued
    // but not run, running them in the current thread.
    Collection<IndexingTask> queuedTasks = currentTasks.values();
    // copy the tasks into an array to avoid concurrent
    // modification issues, as IndexingTask.run modifies
    // the currentTasks map
    IndexingTask[] queuedTasksArray =
            queuedTasks.toArray(new IndexingTask[queuedTasks.size()]);
    for(IndexingTask task : queuedTasksArray) {
      task.run();
    }

    super.close();
  } // close()

  /** Open a connection to the data store. */
  @Override
  public void open() throws PersistenceException {
    super.open();
    /*
     * check if the storage directory is a valid serial datastore if we
     * want to support old style: String versionInVersionFile = "1.0";
     * (but this means it will open *any* directory)
     */
    BufferedReader isr = null;
    try {
      isr = new BufferedReader(new FileReader(getVersionFile()));
      currentProtocolVersion = isr.readLine();
      String indexDirRelativePath = isr.readLine();

      if(indexDirRelativePath != null
              && indexDirRelativePath.trim().length() > 1) {
        URL storageDirURL = storageDir.toURI().toURL();
        URL theIndexURL = new URL(storageDirURL, indexDirRelativePath);
        // check if index directory exists
        File indexDir = Files.fileFromURL(theIndexURL);
        if(!indexDir.exists()) {
          throw new PersistenceException("Index directory "
                  + indexDirRelativePath
                  + " could not be found for datastore at " + storageDirURL);
        }

        indexURL = theIndexURL;
        this.indexer = new LuceneIndexer(indexURL);
        this.searcher = new LuceneSearcher();
        ((LuceneSearcher)this.searcher).setLuceneDatastore(this);
      }
    } catch(IOException e) {
      throw new PersistenceException("Invalid storage directory: " + e);
    } finally {
      IOUtils.closeQuietly(isr);
    }
    
    if(!isValidProtocolVersion(currentProtocolVersion))
      throw new PersistenceException("Invalid protocol version number: "
              + currentProtocolVersion);

    // Lets create a separate indexer thread which keeps running in the
    // background
    executor =
            new ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());
    // set up the executor so it does not execute delayed indexing tasks
    // that are still waiting when it is shut down. We run these tasks
    // immediately at shutdown time rather than waiting.
    executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
    executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    
    // start listening to Creole events
    Gate.getCreoleRegister().addCreoleListener(this);
  }

  /**
   * Obtain the lock object on which we must synchronize when loading or
   * saving the LR with the given ID.
   */
  private Object lockObjectForID(Object id) {
    synchronized(lockObjects) {
      processRefQueue();
      Object lock = null;
      if(lockObjects.containsKey(id)) {
        lock = lockObjects.get(id).get();
      }
      if(lock == null) {
        lockObjects.remove(id);
        lock = new Object();
        LabelledSoftReference ref = new LabelledSoftReference(lock);
        ref.label = id;
        lockObjects.put(id, ref);
      }

      return lock;
    }
  }

  /**
   * Cleans up the lockObjects map by removing any entries whose
   * SoftReference values have been cleared by the garbage collector.
   */
  private void processRefQueue() {
    LabelledSoftReference ref = null;
    while((ref = LabelledSoftReference.class.cast(refQueue.poll())) != null) {
      // check that the queued ref hasn't already been replaced in the
      // map
      if(lockObjects.get(ref.label) == ref) {
        lockObjects.remove(ref.label);
      }
    }
  }

  /**
   * Submits the given LR ID for indexing. The task is delayed by 5
   * seconds, so multiple updates to the same LR in close succession do
   * not un-necessarily trigger multiple re-indexing passes.
   */
  protected void queueForIndexing(Object lrID) {
    IndexingTask existingTask = currentTasks.get(lrID);
    if(existingTask != null) {
      existingTask.disable();
    }

    IndexingTask newTask = new IndexingTask(lrID);
    currentTasks.put(lrID, newTask);
    // set the LR to be indexed after the configured delay
    executor.schedule(newTask, indexDelay, TimeUnit.MILLISECONDS);
  }

  /**
   * Delete a resource from the data store.
   */
  @Override
  public void delete(String lrClassName, Object lrPersistenceId)
          throws PersistenceException {

    IndexingTask task = currentTasks.get(lrPersistenceId);
    if(task != null) {
      task.disable();
    }

    // and we delete it from the datastore
    // we obtained the lock on this - in order to avoid clashing between
    // the object being loaded by the indexer thread and the thread that
    // deletes it
    Object lock = lockObjectForID(lrPersistenceId);
    synchronized(lock) {
      super.delete(lrClassName, lrPersistenceId);
    }
    lock = null;

    /*
     * lets first find out if the deleted resource is a corpus. Deleting
     * a corpus does not require deleting all its member documents but
     * we need to remove the reference of corpus from all its underlying
     * documents in index
     */
    try {
      if(Corpus.class.isAssignableFrom(Class.forName(lrClassName, true,
              Gate.getClassLoader()))) {
        /*
         * we would issue a search query to obtain all documents which
         * belong to his corpus and set them as referring to null
         * instead of refering to the given corpus
         */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(Constants.INDEX_LOCATION_URL, indexURL);
        parameters.put(Constants.CORPUS_ID, lrPersistenceId.toString());
        try {
          boolean success = getSearcher().search("nothing", parameters);
          if(!success) return;

          Hit[] hits = getSearcher().next(-1);
          if(hits == null || hits.length == 0) {
            // do nothing
            return;
          }

          for(int i = 0; i < hits.length; i++) {
            String docID = hits[i].getDocumentID();
            queueForIndexing(docID);
          }
        } catch(SearchException se) {
          throw new PersistenceException(se);
        }
        return;
      }
    } catch(ClassNotFoundException cnfe) {
      // don't do anything
    }

    // we want to delete this document from the Index as well
    ArrayList<Object> removed = new ArrayList<Object>();
    removed.add(lrPersistenceId);
    try {
      synchronized(indexer) {
        this.indexer.remove(removed);
      }
    } catch(IndexException ie) {
      throw new PersistenceException(ie);
    }
  }

  @Override
  public LanguageResource getLr(String lrClassName, Object lrPersistenceId)
          throws PersistenceException, SecurityException {
    LanguageResource lr = super.getLr(lrClassName, lrPersistenceId);
    if(lr instanceof Corpus) {
      ((Corpus)lr).addCorpusListener(this);
    }
    return lr;
  }

  /**
   * Save: synchonise the in-memory image of the LR with the persistent
   * image.
   */
  @Override
  public void sync(LanguageResource lr) throws PersistenceException {
    if(lr.getLRPersistenceId() != null) {
      // lock the LR ID so we don't write to the file while an
      // indexer task is reading it
      Object lock = lockObjectForID(lr.getLRPersistenceId());
      synchronized(lock) {
        
        // we load the copy of this LR and check if any modification were done
        // if so, it should be reindexed or else it should not be synced again.
        LanguageResource copy = null;
        try {
          copy =
                  getLr(lr.getClass().getName(), lr.getLRPersistenceId());

          // we check it only if it is an instance of Document
          if(copy instanceof Document && lr instanceof Document) {
            Document cDoc = (Document)copy;
            Document lrDoc = (Document)lr;
            boolean sameDocs = false;
            
            // we only check content and annotation sets
            // as that's what matters from the annic perspective
            if(cDoc.getContent().equals(lrDoc.getContent())) {
              if(cDoc.getAnnotations().equals(lrDoc.getAnnotations())) {
                if(cDoc.getNamedAnnotationSets().equals(
                        lrDoc.getNamedAnnotationSets())) {
                  boolean allSetsSame = true;
                  for(String key : cDoc.getNamedAnnotationSets().keySet()) {
                    if(!cDoc.getAnnotations(key).equals(lrDoc.getAnnotations(key))) {
                      allSetsSame = false;
                      break;
                    }
                  }
                  if(allSetsSame) {
                    sameDocs = true;
                  }
                }
              }
            }

            
            if(sameDocs) {
              lock = null;
              return;
            }
          }
        } catch(SecurityException e) {
          e.printStackTrace();
        } finally {
          
          // delete the copy of this LR
          if(copy != null) {
            Factory.deleteResource(copy);
          }
        }
        
        super.sync(lr);
      }
      lock = null;
    } else {
      super.sync(lr);
    }



    if(lr instanceof Document) {
      queueForIndexing(lr.getLRPersistenceId());
    }
  }

  /**
   * Sets the Indexer to be used for indexing Datastore
   */
  @Override
  public void setIndexer(Indexer indexer, Map<String,Object> indexParameters)
          throws IndexException {

    this.indexer = indexer;
    this.indexParameters = indexParameters;
    this.indexURL = (URL)this.indexParameters.get(Constants.INDEX_LOCATION_URL);
    this.indexer.createIndex(this.indexParameters);

    // dump the version file
    try {
      File versionFile = getVersionFile();
      OutputStreamWriter osw =
              new OutputStreamWriter(new FileOutputStream(versionFile));
      osw.write(versionNumber + Strings.getNl());
      String indexDirRelativePath =
              PersistenceManager.getRelativePath(storageDir.toURI().toURL(),
                      indexURL);
      osw.write(indexDirRelativePath);
      osw.close();
    } catch(IOException e) {
      throw new IndexException("couldn't write version file: " + e);
    }
  }

  @Override
  public Indexer getIndexer() {
    return this.indexer;
  }

  @Override
  public void setSearcher(Searcher searcher) throws SearchException {
    this.searcher = searcher;
    if(this.searcher instanceof LuceneSearcher) {
      ((LuceneSearcher)this.searcher).setLuceneDatastore(this);
    }
  }

  @Override
  public Searcher getSearcher() {
    return this.searcher;
  }

  /**
   * Sets the delay in milliseconds that we should wait after a sync
   * before attempting to re-index a document. If sync is called again
   * for the same document within this time then the timer for the
   * re-indexing task is reset. Thus if several changes to the same
   * document are made in quick succession it will only be re-indexed
   * once. On the other hand, if the delay is set too long the document
   * may never be indexed until the data store is closed. The default
   * delay is 1000ms (one second), which should be appropriate for usage
   * in the GATE GUI.
   */
  public void setIndexDelay(long indexDelay) {
    this.indexDelay = indexDelay;
  }

  public long getIndexDelay() {
    return indexDelay;
  }

  /**
   * Search the datastore
   */
  @Override
  public boolean search(String query, Map<String,Object> searchParameters)
          throws SearchException {
    return this.searcher.search(query, searchParameters);
  }

  /**
   * Returns the next numberOfPatterns
   * 
   * @param numberOfPatterns
   * @return null if no patterns found
   */
  @Override
  public Hit[] next(int numberOfPatterns) throws SearchException {
    return this.searcher.next(numberOfPatterns);
  }

  // Corpus Events
  /**
   * This method is invoked whenever a document is removed from a corpus
   */
  @Override
  public void documentRemoved(CorpusEvent ce) {
    Object docLRID = ce.getDocumentLRID();

    /*
     * we need to remove this document from the index
     */
    if(docLRID != null) {
      ArrayList<Object> removed = new ArrayList<Object>();
      removed.add(docLRID);
      try {
        synchronized(indexer) {
          indexer.remove(removed);
        }
      } catch(IndexException ie) {
        throw new GateRuntimeException(ie);
      }
      // queueForIndexing(docLRID);
    }
  }

  /**
   * This method is invoked whenever a document is added to a particular
   * corpus
   */
  @Override
  public void documentAdded(CorpusEvent ce) {
    /*
     * we don't want to do anything here, because the sync is
     * automatically called when a document is added to a corpus which
     * is part of the the datastore
     */
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * gate.event.CreoleListener#datastoreClosed(gate.event.CreoleEvent)
   */
  @Override
  public void datastoreClosed(CreoleEvent e) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * gate.event.CreoleListener#datastoreCreated(gate.event.CreoleEvent)
   */
  @Override
  public void datastoreCreated(CreoleEvent e) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * gate.event.CreoleListener#datastoreOpened(gate.event.CreoleEvent)
   */
  @Override
  public void datastoreOpened(CreoleEvent e) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * gate.event.CreoleListener#resourceLoaded(gate.event.CreoleEvent)
   */
  @Override
  public void resourceLoaded(CreoleEvent e) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.event.CreoleListener#resourceRenamed(gate.Resource,
   * java.lang.String, java.lang.String)
   */
  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * gate.event.CreoleListener#resourceUnloaded(gate.event.CreoleEvent)
   */
  @Override
  public void resourceUnloaded(CreoleEvent e) {
    // if the resource being close is one of our corpora. we need to
    // remove
    // the corpus listener associated with it
    Resource res = e.getResource();
    if(res instanceof Corpus) {
      ((Corpus)res).removeCorpusListener(this);
    }
  }

  protected class IndexingTask implements Runnable {
    private AtomicBoolean disabled = new AtomicBoolean(false);

    private Object lrID;

    public IndexingTask(Object lrID) {
      this.lrID = lrID;
    }

    public void disable() {
      disabled.set(true);
    }

    @Override
    public void run() {
      // remove this task from the currentTasks map if it has not been
      // superseded by a later task
      currentTasks.remove(lrID, this);
      // only run the rest of the process if this task has not been
      // disabled (because a newer task for the same LR was scheduled).
      // We set the disabled flag at this point so the same task cannot
      // be run twice.
      if(disabled.compareAndSet(false, true)) {
        Document doc = null;
        // read the document from datastore
        FeatureMap features = Factory.newFeatureMap();
        features.put(DataStore.LR_ID_FEATURE_NAME, lrID);
        features.put(DataStore.DATASTORE_FEATURE_NAME, LuceneDataStoreImpl.this);
        FeatureMap hidefeatures = Factory.newFeatureMap();
        Gate.setHiddenAttribute(hidefeatures, true);
        try {
          // lock the LR ID so we don't try and read a file
          // which is in the process of being written
          Object lock = lockObjectForID(lrID);
          synchronized(lock) {
            doc =
                    (Document)Factory
                            .createResource("gate.corpora.DocumentImpl",
                                    features, hidefeatures);
          }
          lock = null;
        } catch(ResourceInstantiationException rie) {
          // this means the LR ID was null
          doc = null;
        }

        // if the document is not null,
        // proceed to indexing it
        if(doc != null) {

          /*
           * we need to reindex this document in order to synchronize it
           * lets first remove it from the index
           */
          ArrayList<Object> removed = new ArrayList<Object>();
          removed.add(lrID);
          try {
            synchronized(indexer) {
              indexer.remove(removed);
            }
          } catch(IndexException ie) {
            throw new GateRuntimeException(ie);
          }

          // and add it back
          ArrayList<Document> added = new ArrayList<Document>();
          added.add(doc);

          try {
            String corpusPID = null;

            /*
             * we need to find out the corpus which this document
             * belongs to one easy way is to check all instances of
             * serial corpus loaded in memory
             */
            List<LanguageResource> scs =
                    Gate.getCreoleRegister().getLrInstances(
                            SerialCorpusImpl.class.getName());
            if(scs != null) {
              /*
               * we need to check which corpus the deleted class
               * belonged to
               */
              Iterator<LanguageResource> iter = scs.iterator();
              while(iter.hasNext()) {
                SerialCorpusImpl sci = (SerialCorpusImpl)iter.next();
                if(sci != null) {
                  if(sci.contains(doc)) {
                    corpusPID = sci.getLRPersistenceId().toString();
                    break;
                  }
                }
              }
            }

            /*
             * it is also possible that the document is loaded from
             * datastore without being loaded from the corpus (e.g.
             * using getLR(...) method of datastore) in this case the
             * relevant corpus won't exist in memory
             */
            if(corpusPID == null) {
              List<String> corpusPIDs = getLrIds(SerialCorpusImpl.class.getName());
              if(corpusPIDs != null) {
                for(int i = 0; i < corpusPIDs.size(); i++) {
                  Object corpusID = corpusPIDs.get(i);

                  SerialCorpusImpl corpusLR = null;
                  // we will have to load this corpus
                  FeatureMap params = Factory.newFeatureMap();
                  params.put(DataStore.DATASTORE_FEATURE_NAME,
                          LuceneDataStoreImpl.this);
                  params.put(DataStore.LR_ID_FEATURE_NAME, corpusID);
                  hidefeatures = Factory.newFeatureMap();
                  Gate.setHiddenAttribute(hidefeatures, true);
                  Object lock = lockObjectForID(corpusID);
                  synchronized(lock) {
                    corpusLR =
                            (SerialCorpusImpl)Factory.createResource(
                                    SerialCorpusImpl.class.getCanonicalName(),
                                    params, hidefeatures);
                  }
                  lock = null;

                  if(corpusLR != null) {
                    if(corpusLR.contains(doc)) {
                      corpusPID = corpusLR.getLRPersistenceId().toString();
                    }
                    Factory.deleteResource(corpusLR);
                    if(corpusPID != null) break;
                  }
                }
              }
            }

            synchronized(indexer) {
              indexer.add(corpusPID, added);
            }

            Factory.deleteResource(doc);
          } catch(Exception ie) {
            ie.printStackTrace();
          }
        }
      }
    }

  }

  /**
   * Soft reference with an associated label.
   */
  private class LabelledSoftReference extends SoftReference<Object> {
    Object label;

    public LabelledSoftReference(Object referent) {
      super(referent);
    }
  }
}
