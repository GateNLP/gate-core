/*
 *  LuceneIndexer.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneIndexer.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import gate.Corpus;
import gate.creole.annic.Constants;
import gate.creole.annic.IndexException;
import gate.creole.annic.Indexer;
import gate.creole.annic.apache.lucene.document.Document;
import gate.creole.annic.apache.lucene.index.IndexReader;
import gate.creole.annic.apache.lucene.index.IndexWriter;
import gate.creole.annic.apache.lucene.index.Term;
import gate.creole.annic.apache.lucene.search.Hits;
import gate.creole.annic.apache.lucene.search.IndexSearcher;
import gate.creole.annic.apache.lucene.search.TermQuery;
import gate.util.Files;

/**
 * This class provides a Lucene based implementation for the Indexer
 * interface. It asks users to provide various required parameters and
 * creates the Lucene Index.
 * 
 * @author niraj
 * 
 */
public class LuceneIndexer implements Indexer {

  protected boolean DEBUG = false;

  /** An corpus for indexing */
  protected Corpus corpus;

  /**
   * Various parameters such as location of the Index etc.
   */
  protected Map<String,Object> parameters;

  /**
   * Constructor
   * 
   * @param indexLocationUrl
   * @throws IOException
   */
  public LuceneIndexer(URL indexLocationUrl) throws IOException {
    if(indexLocationUrl != null) {
      readParametersFromDisk(indexLocationUrl);
    }
  }

  /**
   * Checks the Index Parameters to see if they are all compatible
   */
  protected void checkIndexParameters(Map<String,Object> parameters) throws IndexException {
    this.parameters = parameters;

    if(parameters == null) {
      throw new IndexException("No parameters provided!");
    }

    URL indexLocation = (URL)parameters.get(Constants.INDEX_LOCATION_URL);
    if(indexLocation == null)
      throw new IndexException("You must provide a URL for INDEX_LOCATION");

    if(!indexLocation.getProtocol().equalsIgnoreCase("file")) {
      throw new IndexException(
              "Index Output Directory must be set to the empty directory on the file system");
    }

    File file = null;
    try {
      file = new File(indexLocation.toURI());
    } catch(URISyntaxException use) {
      file = Files.fileFromURL(indexLocation);
    }
      
      if(file.exists()) {
        if(!file.isDirectory()) {
          throw new IndexException("Path doesn't exist");
        }
      }

    String baseTokenAnnotationType = (String)parameters
            .get(Constants.BASE_TOKEN_ANNOTATION_TYPE);
    
    if(baseTokenAnnotationType == null || baseTokenAnnotationType.trim().length() == 0) {
      baseTokenAnnotationType = Constants.ANNIC_TOKEN;
      parameters.put(Constants.BASE_TOKEN_ANNOTATION_TYPE,
              Constants.ANNIC_TOKEN);
    } else if(baseTokenAnnotationType.indexOf(".") > -1 || baseTokenAnnotationType.indexOf("=") > -1
        || baseTokenAnnotationType.indexOf(";") > -1 || baseTokenAnnotationType.indexOf(",") > -1) {
      throw new IndexException(
      "Base token annotation type cannot have '.' , '=', ',' or ';; in it");
    }
    
    String indexUnitAnnotationType = (String)parameters
            .get(Constants.INDEX_UNIT_ANNOTATION_TYPE);
    
    if(DEBUG) {
      System.out.println("BTAT : " + baseTokenAnnotationType);
      System.out.println("IUAT : " + indexUnitAnnotationType);
    }   
  }

  /**
   * Returns the indexing parameters
   */
  protected Map<String,Object> getIndexParameters() {
    return this.parameters;
  }

  /**
   * Creates index directory and indexing all documents in the corpus.
   * 
   * @param indexParameters This is a map containing various values
   *          required to create an index In case of LuceneIndexManager
   *          following are the values required
   *          <P>
   *          INDEX_LOCATION_URL - this is a URL where the Index be
   *          created
   *          <P>
   *          BASE_TOKEN_ANNOTATION_TYPE
   *          <P>
   *          INDEX_UNIT_ANNOTATION_TYPE
   *          <P>
   *          FEATURES_TO_EXCLUDE
   *          <P>
   *          FEATURES_TO_INCLUDE
   *          <P>
   * 
   */
  @Override
  public void createIndex(Map<String,Object> indexParameters) throws IndexException {
    checkIndexParameters(indexParameters);
    URL indexLocation = (URL)parameters.get(Constants.INDEX_LOCATION_URL);

    try {
      File file = null;
      try {
        file = new File(indexLocation.toURI());
      } catch(URISyntaxException use) {
        file = Files.fileFromURL(indexLocation);
      }


      // create an instance of Index Writer
      IndexWriter writer = new IndexWriter(file.getAbsolutePath(),
              new LuceneAnalyzer(), true);

      try {
        if(corpus != null) {
          // load documents and add them one by one
          for(int i = 0; i < corpus.size(); i++) {
            gate.Document gateDoc = corpus.get(i);
            String idToUse = gateDoc.getLRPersistenceId() == null ? gateDoc
                    .getName() : gateDoc.getLRPersistenceId().toString();
  
            System.out.print("Indexing : " + idToUse + " ...");
            String corpusName = corpus.getLRPersistenceId() == null ? corpus
                    .getName() : corpus.getLRPersistenceId().toString();
  
            List<gate.creole.annic.apache.lucene.document.Document> luceneDocs = getLuceneDocuments(
                    corpusName, gateDoc, indexLocation.toString());
  
            if(luceneDocs != null) {
              for(int j = 0; j < luceneDocs.size(); j++) {
                if(luceneDocs.get(j) != null) {
                  writer.addDocument(luceneDocs.get(j));
                }
              }
            }
            if(gateDoc.getLRPersistenceId() != null) {
              gate.Factory.deleteResource(gateDoc);
            }
            System.out.println("Done");
          }
        }// for (all documents)
      }
      finally {
        writer.close();
      }
      writeParametersToDisk();
    }
    catch(java.io.IOException ioe) {
      throw new IndexException(ioe);
    }
  }

  /** Optimize existing index. */
  @Override
  public void optimizeIndex() throws IndexException {
    try {
      String location = ((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .toString();
      IndexWriter writer = new IndexWriter(location,
              new gate.creole.annic.lucene.LuceneAnalyzer(), false);
      try {
        writer.optimize();
      }
      finally {
        writer.close();
      }
    }
    catch(java.io.IOException ioe) {
      throw new IndexException(ioe);
    }
  }

  /** Deletes the index. */
  @Override
  public void deleteIndex() throws IndexException {

    if(parameters == null) return;
    File dir = null;
    //TODO should we use the gate util Files mehotd for this
    try {
      dir = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .toURI());
    } catch(URISyntaxException use) {
      dir = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .getFile());
    }
   
    if(!FileUtils.deleteQuietly(dir)) {
      throw new IndexException("Can't delete directory" + dir.getAbsolutePath());
    }
  }

  /**
   * Add new documents to Index
   * @throws IndexException
   */
  @Override
  public void add(String corpusPersistenceID, List<gate.Document> added)
          throws IndexException {

    String location = null;
    //TODO should we use the gate util Files mehotd for this
    try {
      location = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .toURI()).getAbsolutePath();
    } catch(URISyntaxException use) {
      location = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .getFile()).getAbsolutePath();
    }

    try {
      IndexWriter writer = new IndexWriter(location, new LuceneAnalyzer(), false);

      try {
        if(added != null) {
          for(int i = 0; i < added.size(); i++) {

            gate.Document gateDoc = added.get(i);

            String idToUse = gateDoc.getLRPersistenceId() == null ? gateDoc
                    .getName() : gateDoc.getLRPersistenceId().toString();
            System.out.print("Indexing : " + idToUse + " ...");
            List<gate.creole.annic.apache.lucene.document.Document> docs = getLuceneDocuments(
                    corpusPersistenceID, gateDoc, location);
            if(docs == null) {
              System.out.println("Done");
              continue;
            }
            for(int j = 0; j < docs.size(); j++) {
              writer.addDocument(docs.get(j));
            }
            System.out.println("Done");
          }// for (add all added documents)
        }
      }
      finally {
        // make sure we close the writer, whatever happens
        writer.close();
      }
    }
    catch(java.io.IOException ioe) {
      throw new IndexException(ioe);
    }
  }

  private String getCompatibleName(String name) {
    return name.replaceAll("[\\/:\\*\\?\"<>|]", "_");
  }
  

  /**
   * remove documents from the Index
   * 
   * @param removedIDs - when documents are not
   *          peristed, Persistence IDs will not be available In that
   *          case provide the document Names instead of their IDs
   * @throws IndexException if an error occurs while removing documents
   */
  @Override
  public void remove(List<Object> removedIDs) throws IndexException {

    String location = null;
    try {
      location = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .toURI()).getAbsolutePath();
    } catch(URISyntaxException use) {
      location = new File(((URL)parameters.get(Constants.INDEX_LOCATION_URL))
              .getFile()).getAbsolutePath();
    
    }
    
    try {

      IndexReader reader = IndexReader.open(location);

      try {
        // let us first remove the documents which need to be removed
        if(removedIDs != null) {
          for(int i = 0; i < removedIDs.size(); i++) {
            String id = removedIDs.get(i).toString();
            
            Set<String> serializedFilesIDs = getNamesOfSerializedFiles(id);
            
            if(serializedFilesIDs.size() > 0) {
              System.out.print("Removing => " + id + "...");
            
            id = getCompatibleName(id);
            File file = new File(location, Constants.SERIALIZED_FOLDER_NAME);
            file = new File(file, id);
            
            for(String serializedFileID : serializedFilesIDs) {
              gate.creole.annic.apache.lucene.index.Term term = new gate.creole.annic.apache.lucene.index.Term(
                      Constants.DOCUMENT_ID_FOR_SERIALIZED_FILE, serializedFileID);
              reader.delete(term);
              serializedFileID = getCompatibleName(serializedFileID);
              // deleting them from the disk as well
              // we have a subfolder for each document
              
              File toDelete = new File(file, serializedFileID
                      + ".annic");
              if(toDelete.exists()) toDelete.delete();
            }
            
            if(file.exists() && file.isDirectory()) {
              file.delete();
            }
            
            System.out.println("Done ");
            }
          }// for (remove all removed documents)
        }
      }
      finally {
        reader.close();
      }
    }
    catch(java.io.IOException ioe) {
      throw new IndexException(ioe);
    }

  }

  /**
   * We create a separate Lucene document for each index unit available
   * in the gate document. An array of Lucene document is returned as a
   * call to this method. It uses various indexing parameters set
   * earlier.
   * @throws IndexException
   */
  private List<gate.creole.annic.apache.lucene.document.Document> getLuceneDocuments(
          String corpusPersistenceID, gate.Document gateDoc, String location)
          throws IndexException {

    String baseTokenAnnotationType = (String)parameters
            .get(Constants.BASE_TOKEN_ANNOTATION_TYPE);

    String indexUnitAnnotationType = (String)parameters
            .get(Constants.INDEX_UNIT_ANNOTATION_TYPE);

    @SuppressWarnings("unchecked")
    List<String> featuresToExclude = new ArrayList<String>((List<String>)parameters
            .get(Constants.FEATURES_TO_EXCLUDE));

    @SuppressWarnings("unchecked")
    List<String> featuresToInclude = new ArrayList<String>((List<String>)parameters
            .get(Constants.FEATURES_TO_INCLUDE));

    @SuppressWarnings("unchecked")
    List<String> annotationSetsToExclude = new ArrayList<String>((List<String>)parameters
            .get(Constants.ANNOTATION_SETS_NAMES_TO_EXCLUDE));

    @SuppressWarnings("unchecked")
    List<String> annotationSetsToInclude = new ArrayList<String>((List<String>)parameters
            .get(Constants.ANNOTATION_SETS_NAMES_TO_INCLUDE));

    Boolean createTokensAutomatically = (Boolean) parameters.get(Constants.CREATE_TOKENS_AUTOMATICALLY);
    if(createTokensAutomatically == null) createTokensAutomatically = Boolean.TRUE;
    
    String idToUse = gateDoc.getLRPersistenceId() == null
            ? gateDoc.getName()
            : gateDoc.getLRPersistenceId().toString();

    return new gate.creole.annic.lucene.LuceneDocument().createDocuments(
            corpusPersistenceID, gateDoc, idToUse, annotationSetsToInclude,
            annotationSetsToExclude, featuresToInclude, featuresToExclude,
            location, baseTokenAnnotationType, createTokensAutomatically, indexUnitAnnotationType);
  }

  /**
   * Returns the corpus.
   */
  @Override
  public Corpus getCorpus() {
    return corpus;
  }

  /**
   * Sets the corpus.
   */
  @Override
  public void setCorpus(Corpus corpus) throws IndexException {
    this.corpus = corpus;
    if(corpus == null) {
      throw new IndexException("Corpus is not initialized");
    }

    // we would add a feature to the corpus
    // which will tell us if this corpus was index by the ANNIC
    corpus.getFeatures().put(Constants.CORPUS_INDEX_FEATURE,
            Constants.CORPUS_INDEX_FEATURE_VALUE);
  }

  /**
   * This method, searchers for the LuceneIndexDefinition.xml file at
   * the provided location. The file is supposed to contain all the
   * required parameters which are used to create an index.
   * 
   * @param indexLocationUrl
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  private void readParametersFromDisk(URL indexLocationUrl) throws IOException {
    // we create a hashmap to store index definition in the index
    // directory

    File file = null;
    try {
      file = new File(new File(indexLocationUrl.toURI()), "LuceneIndexDefinition.xml");
    } catch(URISyntaxException use) {
      file = new File(indexLocationUrl.getFile(), "LuceneIndexDefinition.xml");
    }

    if(!file.exists()) return;

    java.io.FileReader fileReader = new java.io.FileReader(file);

    try {
      // other wise read this and
      com.thoughtworks.xstream.XStream xstream = new com.thoughtworks.xstream.XStream(
              new com.thoughtworks.xstream.io.xml.StaxDriver());
  
      // Saving is accomplished just using XML serialization of the map.
      this.parameters = (Map<String,Object>)xstream.fromXML(fileReader);
      // setting the index location URL
      this.parameters.put(Constants.INDEX_LOCATION_URL, indexLocationUrl);
    }
    finally {
      fileReader.close();
    }
  }

  /**
   * All Index parameters are stored on a disc at the
   * index_location_url/LuceneIndexDefinition.xml file.
   * 
   * @throws IOException
   */
  private void writeParametersToDisk() throws IOException {
    // we create a hashmap to store index definition in the index
    // directory
    URL location = (URL)parameters.get(Constants.INDEX_LOCATION_URL);
    File file = null;
    try {
      file = new File(new File(location.toURI()), "LuceneIndexDefinition.xml");
    } catch(URISyntaxException use) {
      file = new File(location.getFile(), "LuceneIndexDefinition.xml");
    }

    java.io.FileWriter fileWriter = new java.io.FileWriter(file);
    Map<String,Object> indexInformation = new HashMap<String,Object>();
    //Iterator<String> iter = parameters.keySet().iterator();
    //while(iter.hasNext()) {
    for (Map.Entry<String, Object> entry : parameters.entrySet()){
      String key = entry.getKey();
      if(key.equals(Constants.INDEX_LOCATION_URL)) continue;
      indexInformation.put(key, entry.getValue());
    }

    indexInformation.put(Constants.CORPUS_INDEX_FEATURE,
            Constants.CORPUS_INDEX_FEATURE_VALUE);
    if(corpus != null)
      indexInformation.put(Constants.CORPUS_SIZE, corpus
              .getDocumentNames().size());

    // we would use XStream library to store annic patterns
    com.thoughtworks.xstream.XStream xstream = new com.thoughtworks.xstream.XStream();

    // Saving is accomplished just using XML serialization of
    // the map.
    try {
      xstream.toXML(indexInformation, fileWriter);
    }
    finally {
      fileWriter.close();
    }
  }

  /**
   * Returns the set parameters
   */
  @Override
  public Map<String,Object> getParameters() {
    return this.parameters;
  }

  /**
   * This method returns a set of annotation set names that are indexed.
   */
  public Set<String> getNamesOfSerializedFiles(String documentID)
          throws IndexException {
    String location = null;
    try {
      location = new File(((URL)parameters
            .get(Constants.INDEX_LOCATION_URL)).toURI()).getAbsolutePath();
    } catch(URISyntaxException use) {
      location = new File(((URL)parameters
              .get(Constants.INDEX_LOCATION_URL)).getFile()).getAbsolutePath();
    }
    
    Set<String> toReturn = new HashSet<String>();
    try {
      Term term = new Term(Constants.DOCUMENT_ID, documentID);
      TermQuery tq = new TermQuery(term);
      gate.creole.annic.apache.lucene.search.Searcher searcher = new IndexSearcher(location);
      try {
        // and now execute the query
        // result of which will be stored in hits
        Hits luceneHits = searcher.search(tq);
        for(int i = 0; i < luceneHits.length(); i++) {
          Document luceneDoc = luceneHits.doc(i);
          String documentIdOfSerializedFile = luceneDoc
                  .get(Constants.DOCUMENT_ID_FOR_SERIALIZED_FILE);
          toReturn.add(documentIdOfSerializedFile);
        }
        return toReturn;
      }
      finally {
        searcher.close();
      }
    }
    catch(IOException ioe) {
      throw new IndexException(ioe);
    }
  }
}
