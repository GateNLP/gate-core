/*
 *  SimpleCorpus.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 23/Jul/2004
 *
 *  $Id: SimpleCorpus.java 17434 2014-02-26 14:45:12Z markagreenwood $
 */

package gate;

import gate.creole.ResourceInstantiationException;
import gate.util.NameBearer;

import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Corpora are lists of Document. TIPSTER equivalent: Collection.
 */
public interface SimpleCorpus extends LanguageResource, List<Document>, NameBearer {

  public static final String CORPUS_NAME_PARAMETER_NAME = "name";

  public static final String CORPUS_DOCLIST_PARAMETER_NAME = "documentsList";

  /**
   * Gets the names of the documents in this corpus.
   * 
   * @return a {@link List} of Strings representing the names of the
   *         documents in this corpus.
   */
  public List<String> getDocumentNames();

  /**
   * Gets the name of a document in this corpus.
   * 
   * @param index the index of the document
   * @return a String value representing the name of the document at
   *         <tt>index</tt> in this corpus.
   */
  public String getDocumentName(int index);

  /**
   * Fills this corpus with documents created on the fly from selected
   * files in a directory. Uses a {@link FileFilter} to select which
   * files will be used and which will be ignored. A simple file filter
   * based on extensions is provided in the Gate distribution (
   * {@link gate.util.ExtensionFileFilter}).
   * 
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown. An implementation for this method is
   *          provided as a static method at
   *          {@link gate.corpora.CorpusImpl#populate(Corpus, URL, FileFilter, String, boolean)}
   *          .
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
   */
  public void populate(URL directory, FileFilter filter, String encoding,
          boolean recurseDirectories) throws IOException,
          ResourceInstantiationException;

  /**
   * Fills this corpus with documents created on the fly from selected
   * files in a directory. Uses a {@link FileFilter} to select which
   * files will be used and which will be ignored. A simple file filter
   * based on extensions is provided in the Gate distribution (
   * {@link gate.util.ExtensionFileFilter}).
   * 
   * @param directory the directory from which the files will be picked.
   *          This parameter is an URL for uniformity. It needs to be a
   *          URL of type file otherwise an InvalidArgumentException
   *          will be thrown. An implementation for this method is
   *          provided as a static method at
   *          {@link gate.corpora.CorpusImpl#populate(Corpus, URL, FileFilter, String, boolean)}
   *          .
   * @param filter the file filter used to select files from the target
   *          directory. If the filter is <tt>null</tt> all the files
   *          will be accepted.
   * @param encoding the encoding to be used for reading the documents
   *@param mimeType the mime type to be used when loading documents. If
   *          null, then the mime type will be automatically determined.
   * @param recurseDirectories should the directory be parsed
   *          recursively?. If <tt>true</tt> all the files from the
   *          provided directory and all its children directories (on as
   *          many levels as necessary) will be picked if accepted by
   *          the filter otherwise the children directories will be
   *          ignored.
   */
  public void populate(URL directory, FileFilter filter, String encoding,
          String mimeType, boolean recurseDirectories) throws IOException,
          ResourceInstantiationException;

  /**
   * Fills the provided corpus with documents extracted from the
   * provided trec file.
   * 
   * @param singleConcatenatedFile the file with multiple documents in it.
   * @param documentRootElement content between the start and end of
   *          this element is considered for documents.
   * @param encoding the encoding of the trec file.
   * @param numberOfDocumentsToExtract indicates the number of documents to
   *          extract from the concatenated file. -1 to indicate all
   *          files.
   * @param documentNamePrefix the prefix to use for document names when
   *          creating from
   * @param mimeType the mime type which determines how the document is handled 
   * @return total length of populated documents in the corpus in number
   *         of bytes
   */
 
  public long populate(URL singleConcatenatedFile, String documentRootElement,
      String encoding, int numberOfDocumentsToExtract,
      String documentNamePrefix, String mimeType, boolean includeRootElement) throws IOException,
      ResourceInstantiationException;

} // interface SimpleCorpus
