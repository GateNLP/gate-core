/*
 *  CorpusImpl.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 11/Feb/2000
 *
 *  $Id: CorpusImpl.java 17604 2014-03-09 10:08:13Z markagreenwood $
 */

package gate.corpora;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.creole.AbstractLanguageResource;
import gate.creole.CustomDuplication;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.event.CorpusEvent;
import gate.event.CorpusListener;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.event.StatusListener;
import gate.util.BomStrippingInputStreamReader;
import gate.util.Err;
import gate.util.Files;
import gate.util.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Corpora are sets of Document. They are ordered by lexicographic
 * collation on Url.
 */
@CreoleResource(name = "GATE Corpus", comment = "GATE transient corpus.", interfaceName = "gate.Corpus", icon = "corpus-trans", helpURL = "http://gate.ac.uk/userguide/sec:developer:loadlr")
public class CorpusImpl extends AbstractLanguageResource implements Corpus,
                                                        CreoleListener,
                                                        CustomDuplication {

  public CorpusImpl() {
    supportList = Collections.synchronizedList(new VerboseList());
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
    ArrayList<String> res = new ArrayList<String>(supportList.size());
    for(Object document : supportList) {
      res.add(((Document)document).getName());
    }
    return res;
  }

  /**
   * Gets the name of a document in this corpus.
   * 
   * @param index the index of the document
   * @return a String value representing the name of the document at
   *         <tt>index</tt> in this corpus.
   */
  @Override
  public String getDocumentName(int index) {
    return supportList.get(index).getName();
  }

  /**
   * This method does not make sense for transient corpora, so it does
   * nothing.
   */
  @Override
  public void unloadDocument(Document doc) {
    return;
  }

  /**
   * The underlying list that holds the documents in this corpus.
   */
  protected List<Document> supportList = null;

  /**
   * A proxy list that stores the actual data in an internal list and
   * forwards all operations to that one but it also fires the
   * appropriate corpus events when necessary. It also does some type
   * checking so only Documents are accepted as corpus members.
   */
  protected class VerboseList extends AbstractList<Document> implements Serializable {

    private static final long serialVersionUID = 3483062654980468826L;

    VerboseList() {
      data = new ArrayList<Document>();
    }

    @Override
    public Document get(int index) {
      return data.get(index);
    }

    @Override
    public int size() {
      return data.size();
    }

    @Override
    public Document set(int index, Document element) {
        Document oldDoc = data.set(index, element);

        // fire the 2 events
        fireDocumentRemoved(new CorpusEvent(CorpusImpl.this, oldDoc, index,
                CorpusEvent.DOCUMENT_REMOVED));
        fireDocumentAdded(new CorpusEvent(CorpusImpl.this, element, index,
                CorpusEvent.DOCUMENT_ADDED));
        return oldDoc;
    }

    @Override
    public void add(int index, Document element) {
        data.add(index, element);

        // fire the event
        fireDocumentAdded(new CorpusEvent(CorpusImpl.this, element,
                index, CorpusEvent.DOCUMENT_ADDED));
    }

    @Override
    public Document remove(int index) {
      Document oldDoc = data.remove(index);

      fireDocumentRemoved(new CorpusEvent(CorpusImpl.this, oldDoc, index,
              CorpusEvent.DOCUMENT_REMOVED));
      return oldDoc;
    }

    /**
     * The List containing the actual data.
     */
    List<Document> data;
  }

  /**
   * This method returns true when the document is already loaded in
   * memory
   */
  @Override
  public boolean isDocumentLoaded(int index) {
    return true;
  }

  protected void clearDocList() {
    if(supportList == null) return;
    supportList.clear();
  }

  // List methods
  // java docs will be automatically copied from the List interface.

  @Override
  public int size() {
    return supportList.size();
  }

  @Override
  public boolean isEmpty() {
    return supportList.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return supportList.contains(o);
  }

  @Override
  public Iterator<Document> iterator() {
    return supportList.iterator();
  }

  @Override
  public Object[] toArray() {
    return supportList.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return supportList.toArray(a);
  }

  @Override
  public boolean add(Document o) {
    return supportList.add(o);
  }

  @Override
  public boolean remove(Object o) {
    return supportList.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return supportList.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends Document> c) {
    return supportList.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends Document> c) {
    return supportList.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return supportList.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return supportList.retainAll(c);
  }

  @Override
  public void clear() {
    supportList.clear();
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof CorpusImpl)) return false;

    return supportList.equals(o);
  }

  @Override
  public int hashCode() {
    return supportList.hashCode();
  }

  @Override
  public Document get(int index) {
    return supportList.get(index);
  }

  @Override
  public Document set(int index, Document element) {
    return supportList.set(index, element);
  }

  @Override
  public void add(int index, Document element) {
    supportList.add(index, element);
  }

  @Override
  public Document remove(int index) {
    return supportList.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return supportList.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return supportList.lastIndexOf(o);
  }

  @Override
  public ListIterator<Document> listIterator() {
    return supportList.listIterator();
  }

  @Override
  public ListIterator<Document> listIterator(int index) {
    return supportList.listIterator(index);
  }

  @Override
  public List<Document> subList(int fromIndex, int toIndex) {
    return supportList.subList(fromIndex, toIndex);
  }

  /** Construction */

  @Override
  public void cleanup() {
    Gate.getCreoleRegister().removeCreoleListener(this);
  }

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() {
    if(documentsList != null && !documentsList.isEmpty()) {
      addAll(documentsList);
    }
    return this;
  } // init()

  /**
   * Fills the provided corpus with documents created on the fly from
   * selected files in a directory. Uses a {@link FileFilter} to select
   * which files will be used and which will be ignored. A simple file
   * filter based on extensions is provided in the Gate distribution (
   * {@link gate.util.ExtensionFileFilter}).
   * 
   * @param corpus the corpus to be populated
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown.
   * @param filter the file filter used to select files from the target
   *          directory. If the filter is <tt>null</tt> all the files
   *          will be accepted.
   * @param encoding the encoding to be used for reading the documents
   * @param recurseDirectories should the directory be parsed
   *          recursively?. If <tt>true</tt> all the files from the
   *          provided directory and all its children directories (on as
   *          many levels as necessary) will be picked if accepted by
   *          the filter otherwise the children directories will be
   *          ignored.
   * @throws java.io.IOException if a file doesn't exist
   */
  public static void populate(Corpus corpus, URL directory, FileFilter filter,
          String encoding, boolean recurseDirectories) throws IOException {
    populate(corpus, directory, filter, encoding, null, recurseDirectories);
  }

  /**
   * Fills the provided corpus with documents created on the fly from
   * selected files in a directory. Uses a {@link FileFilter} to select
   * which files will be used and which will be ignored. A simple file
   * filter based on extensions is provided in the Gate distribution (
   * {@link gate.util.ExtensionFileFilter}).
   * 
   * @param corpus the corpus to be populated
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown.
   * @param filter the file filter used to select files from the target
   *          directory. If the filter is <tt>null</tt> all the files
   *          will be accepted.
   * @param encoding the encoding to be used for reading the documents
   * @param recurseDirectories should the directory be parsed
   *          recursively?. If <tt>true</tt> all the files from the
   *          provided directory and all its children directories (on as
   *          many levels as necessary) will be picked if accepted by
   *          the filter otherwise the children directories will be
   *          ignored.
   * @throws java.io.IOException if a file doesn't exist
   */
  public static void populate(Corpus corpus, URL directory, FileFilter filter,
          String encoding, String mimeType, boolean recurseDirectories)
          throws IOException {

    // check input
    if(!directory.getProtocol().equalsIgnoreCase("file"))
      throw new IllegalArgumentException(
              "The URL provided is not of type \"file:\"!");

    File dir = Files.fileFromURL(directory);
    if(!dir.exists()) throw new FileNotFoundException(dir.toString());

    if(!dir.isDirectory())
      throw new IllegalArgumentException(dir.getAbsolutePath()
              + " is not a directory!");

    File[] files;
    // populate the corpus
    if(recurseDirectories) {
      files = Files.listFilesRecursively(dir, filter);
    }
    else {
      files = dir.listFiles(filter);
    }

    if(files == null) {
      return;
    }

    // sort the files alphabetically regardless of their paths
    Arrays.sort(files, new Comparator<File>() {
      @Override
      public int compare(File f1, File f2) {
        return f1.getName().compareTo(f2.getName());
      }
    });

    // create the GATE documents
    for(File file : files) {
      if(file.isDirectory()) {
        continue;
      }
      StatusListener sListener = (StatusListener)Gate.getListeners().get(
              "gate.event.StatusListener");
      if(sListener != null)
        sListener.statusChanged("Reading: " + file.getName());
      String docName = file.getName() + "_" + Gate.genSym();
      FeatureMap params = Factory.newFeatureMap();
      params.put(Document.DOCUMENT_URL_PARAMETER_NAME, file.toURI().toURL());
      if(encoding != null)
        params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, encoding);
      if(mimeType != null)
        params.put(Document.DOCUMENT_MIME_TYPE_PARAMETER_NAME, mimeType);

      try {
        Document doc = (Document)Factory.createResource(DocumentImpl.class
                .getName(), params, null, docName);
        corpus.add(doc);
        if(corpus.getLRPersistenceId() != null) {
          // persistent corpus -> unload the document
          corpus.unloadDocument(doc);
          Factory.deleteResource(doc);
        }
      }
      catch(Throwable t) {
        String nl = Strings.getNl();
        Err.prln("WARNING: Corpus.populate could not instantiate document" + nl
                + "  Document name was: " + docName + nl + "  Exception was: "
                + t + nl + nl);
        t.printStackTrace();
      }
      if(sListener != null) sListener.statusChanged(file.getName() + " read");
    }

  }// public static void populate

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
    populate(this, directory, filter, encoding, null, recurseDirectories);
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
   *@param mimeType the mime type to be used when loading documents. If
   *          null, then the mime type will be detected automatically.
   * 
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
    populate(this, directory, filter, encoding, mimeType, recurseDirectories);
  }

  /**
   * Fills the provided corpus with documents extracted from the
   * provided trec file.
   * 
   * @param corpus the corpus to be populated.
   * @param singleConcatenatedFile the trec file.
   * @param documentRootElement text between this element (start and
   *          end) is considered for creating a new document.
   * @param encoding the encoding of the trec file.
   * @param numberOfDocumentsToExtract extracts the specified number of
   *          documents from the trecweb file; -1 to indicate all files.
   * @param mimeType the mime type which determines how the document is handled
   * @return total length of populated documents in the corpus in number
   *         of bytes
   * @throws java.io.IOException
   */  
  public static long populate(Corpus corpus, URL singleConcatenatedFile,
      String documentRootElement, String encoding,
      int numberOfDocumentsToExtract, String documentNamePrefix,
      String mimeType, boolean includeRootElement) throws IOException { 
    
    StatusListener sListener = (StatusListener)gate.Gate.getListeners().get("gate.event.StatusListener");
    
    // obtain the root element that user has provided
    // content between the start and end of root element is considered
    // for creating documents
    documentRootElement = documentRootElement.toLowerCase();

    // document name prefix could be an empty string
    documentNamePrefix = documentNamePrefix == null ? "" : documentNamePrefix
            .trim()
            + "_";

    // we start a new document when we find <documentRootElement> and
    // close it when we find </documentRootElement>
    BufferedReader br = null;
    try {
      
      if(encoding != null && encoding.trim().length() != 0) {
        br = new BomStrippingInputStreamReader(singleConcatenatedFile.openStream(),
                encoding, 10485760);
      }
      else {
        br = new BomStrippingInputStreamReader(singleConcatenatedFile.openStream(),
                10485760);
      }

      // reading line by line
      String line = br.readLine();

      // this is where we store document content
      StringBuilder documentString = new StringBuilder();

      // toggle switch to indicate search for start element
      boolean searchingForStartElement = true;

      // keeping count of number of documents extracted
      int count = 1;

      // length in bytes read so far (to return)
      long lengthInBytes = 0;

      // continue until reached the end of file
      while(line != null) {

        // lowercase the line in order to match documentRootElement in any case
        String lowerCasedLine = line.toLowerCase();

        // if searching for startElement?
        if(searchingForStartElement) {

          // may be its with attributes
          int index = lowerCasedLine.indexOf("<" + documentRootElement + " ");

          // may be no attributes?
          if(index == -1) {
            index = lowerCasedLine.indexOf("<" + documentRootElement + ">");
          }

          // if index <0, we are out of the content boundaries, so simply
          // skip the current line and start reading from the next line
          if(index != -1) {
            // if found, that's the first line
            line = line.substring(index);
            searchingForStartElement = false;
          }
          else {
            line = br.readLine();
          }
        }
        else {

          // now searching for last element
          int index = lowerCasedLine.indexOf("</" + documentRootElement + ">");

          // if not found.. this is the content of a new document
          if(index == -1) {
            documentString.append(line + "\n");
            line = br.readLine();
          }
          else {

            // found.. then end the document
            documentString.append(line.substring(0, index + documentRootElement.length() + 3));

            // getting ready for the next document
            searchingForStartElement = true;

            // here lets create a new document create the doc
            if(sListener != null) sListener.statusChanged("Creating Document Number :" + count);
            
            String docName = documentNamePrefix + count + "_" + Gate.genSym();
            
            String docContent = documentString.toString();
            
            if (!includeRootElement)
              docContent = docContent.substring(docContent.indexOf(">")+1, docContent.lastIndexOf("<"));
            
            FeatureMap params = Factory.newFeatureMap();
            if (mimeType != null) params.put(Document.DOCUMENT_MIME_TYPE_PARAMETER_NAME, mimeType);            
            params.put(Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME, docContent);
            if(encoding != null && encoding.trim().length() > 0)
              params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, encoding); 
            
            // calculate the length
            lengthInBytes += docContent.getBytes().length;            

            try {
              Document doc = (Document)Factory.createResource(DocumentImpl.class.getName(), params, null, docName);
              count++;
              corpus.add(doc);
              
              if(corpus.getLRPersistenceId() != null) {
                // persistent corpus -> unload the document
                corpus.unloadDocument(doc);
                Factory.deleteResource(doc);
              }
              
              // already extracted requested num of documents?
              if((count - 1) == numberOfDocumentsToExtract) break;
            }
            catch(Throwable t) {
              String nl = Strings.getNl();
              Err.prln("WARNING: Corpus.populate could not instantiate document" + nl
                  + "  Document name was: " + docName + nl
                  + "  Exception was: " + t + nl + nl);
              t.printStackTrace();
            }
            
            documentString = new StringBuilder();
            if(sListener != null) sListener.statusChanged(docName + " created!");
           
            line = line.substring(index + documentRootElement.length() + 3);
            if (line.trim().equals("")) line = br.readLine();
          }
        }
      }
      return lengthInBytes;
    }
    finally {
      if(br != null) br.close();
    }
  }// public static void populate

  /**
   * Fills the provided corpus with documents extracted from the
   * provided single concatenated file.
   * 
   * @param singleConcatenatedFile the single concatenated file to load.
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

  /**
   * Custom duplication for a corpus - duplicate this corpus in the
   * usual way, then duplicate the documents in this corpus and add them
   * to the duplicate.
   */
  @Override
  public Resource duplicate(Factory.DuplicationContext ctx)
          throws ResourceInstantiationException {
    Corpus newCorpus = (Corpus)Factory.defaultDuplicate(this, ctx);
    for(Document d : this) {
      newCorpus.add((Document)Factory.duplicate(d, ctx));
    }
    return newCorpus;
  }

  /** Freeze the serialization UID. */
  static final long serialVersionUID = -1113142759053898456L;

  private transient Vector<CorpusListener> corpusListeners;

  protected transient List<Document> documentsList;

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

  @Optional
  @CreoleParameter(collectionElementType = Document.class, comment = "A list of GATE documents")
  public void setDocumentsList(java.util.List<Document> documentsList) {
    this.documentsList = documentsList;
  }

  public java.util.List<Document> getDocumentsList() {
    return documentsList;
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    Resource res = e.getResource();
    // remove all occurences
    if(res instanceof Document) while(contains(res))
      remove(res);
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
  }

  @Override
  public void datastoreOpened(CreoleEvent e) {
  }

  @Override
  public void datastoreCreated(CreoleEvent e) {
  }

  @Override
  public void datastoreClosed(CreoleEvent e) {
  }
} // class CorpusImpl
