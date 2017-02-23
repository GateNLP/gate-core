/*
 * LuceneSearcher.java
 * 
 * Niraj Aswani, 19/March/07
 * 
 * $Id: LuceneSearcher.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.creole.annic.Constants;
import gate.creole.annic.Hit;
import gate.creole.annic.Pattern;
import gate.creole.annic.SearchException;
import gate.creole.annic.Searcher;
import gate.creole.annic.apache.lucene.document.Document;
import gate.creole.annic.apache.lucene.index.IndexReader;
import gate.creole.annic.apache.lucene.index.Term;
import gate.creole.annic.apache.lucene.index.TermEnum;
import gate.creole.annic.apache.lucene.search.BooleanQuery;
import gate.creole.annic.apache.lucene.search.Hits;
import gate.creole.annic.apache.lucene.search.IndexSearcher;
import gate.creole.annic.apache.lucene.search.TermQuery;
import gate.persist.LuceneDataStoreImpl;

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

/**
 * This class provides the Searching functionality for annic.
 * 
 * @author niraj
 */
public class LuceneSearcher implements Searcher {

  /**
   * A List of index locations. It allows searching at multiple locations.
   */
  private List<String> indexLocations = null;

  /**
   * The submitted query.
   */
  private String query = null;

  /**
   * The number of base token annotations to show in left and right context of
   * the pattern. By default 5.
   */
  private int contextWindow = 5;

  /**
   * Found patterns.
   */
  private List<Pattern> annicPatterns = new ArrayList<Pattern>();

  /**
   * Found annotation types in the annic patterns. The maps keeps record of
   * found annotation types and features for each of them.
   */
  public Map<String, List<String>> annotationTypesMap =
      new HashMap<String, List<String>>();

  /**
   * Search parameters.
   */
  private Map<String, Object> parameters = null;

  /**
   * Corpus to search in.
   */
  private String corpusToSearchIn = null;

  /**
   * Annotation set to search in.
   */
  private String annotationSetToSearchIn = null;

  /**
   * Hits returned by the lucene.
   */
  private Hits luceneHits = null;

  /**
   * Indicates if the query was to delete certain documents.
   */
  private boolean wasDeleteQuery = false;

  /**
   * A query can result into multiple queries. For example: (A|B)C is converted
   * into two queries: AC and AD. For each query a separate thread is started.
   */
  private List<LuceneSearchThread> luceneSearchThreads = null;

  /**
   * Indicates if the search was successful.
   */
  private boolean success = false;

  /**
   * Tells which thread to use to retrieve results from.
   */
  private int luceneSearchThreadIndex = 0;

  /**
   * Tells if we have reached at the end of of found results.
   */
  private boolean fwdIterationEnded = false;

  /**
   * Used with freq method for statistics.
   */
  private LuceneDataStoreImpl datastore;

  /**
   * Return the next numberOfHits -1 indicates all
   */
  @Override
  public Hit[] next(int numberOfHits) throws SearchException {

    annicPatterns = new ArrayList<Pattern>();

    if(!success) {
      this.annicPatterns = new ArrayList<Pattern>();
      return getHits();
    }

    if(fwdIterationEnded) {
      this.annicPatterns = new ArrayList<Pattern>();
      return getHits();
    }

    try {
      if(wasDeleteQuery) {
        List<String> docIDs = new ArrayList<String>();
        List<String> setNames = new ArrayList<String>();
        for(int i = 0; i < luceneHits.length(); i++) {
          Document luceneDoc = luceneHits.doc(i);
          String documentID = luceneDoc.get(Constants.DOCUMENT_ID);
          String annotationSetID = luceneDoc.get(Constants.ANNOTATION_SET_ID);
          int index = docIDs.indexOf(documentID);
          if(index == -1) {
            docIDs.add(documentID);
            setNames.add(annotationSetID);
          } else {
            if(!setNames.get(index).equals(annotationSetID)) {
              docIDs.add(documentID);
              setNames.add(annotationSetID);
            }
          }
        }

        Hit[] toReturn = new Hit[docIDs.size()];
        for(int i = 0; i < toReturn.length; i++) {
          toReturn[i] = new Hit(docIDs.get(i), setNames.get(i), 0, 0, "");
        }
        return toReturn;
      }

      for(; luceneSearchThreadIndex < luceneSearchThreads.size(); luceneSearchThreadIndex++) {
        LuceneSearchThread lst =
            luceneSearchThreads.get(luceneSearchThreadIndex);
        List<Pattern> results = lst.next(numberOfHits);
        if(results != null) {
          if(numberOfHits != -1) {
            numberOfHits -= results.size();
          }

          this.annicPatterns.addAll(results);
          if(numberOfHits == 0) { return getHits(); }
        }
      }

      // if we are here, there wer no sufficient patterns available
      // so what we do is make success to false so that this method
      // return null on next call
      fwdIterationEnded = true;
      return getHits();
    } catch(Exception e) {
      throw new SearchException(e);
    }
  }

  /**
   * Method retunrs true/false indicating whether results were found or not.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean search(String query, Map<String, Object> parameters)
      throws SearchException {
    luceneHits = null;
    annicPatterns = new ArrayList<Pattern>();
    annotationTypesMap = new HashMap<String, List<String>>();
    luceneSearchThreads = new ArrayList<LuceneSearchThread>();
    luceneSearchThreadIndex = 0;
    success = false;
    fwdIterationEnded = false;
    wasDeleteQuery = false;

    if(parameters == null)
      throw new SearchException("Parameters cannot be null");

    this.parameters = parameters;

    /*
     * lets first check if the query is to search the document names This is
     * used when we only wants to search for documents stored under the specific
     * corpus
     */
    if(parameters.size() == 2
        && parameters.get(Constants.INDEX_LOCATION_URL) != null) {
      String corpusID = (String)parameters.get(Constants.CORPUS_ID);
      String indexLocation = null;
      try {
        indexLocation =
            new File(
                ((URL)parameters.get(Constants.INDEX_LOCATION_URL)).toURI())
                .getAbsolutePath();
      } catch(URISyntaxException use) {
        indexLocation =
            new File(
                ((URL)parameters.get(Constants.INDEX_LOCATION_URL)).getFile())
                .getAbsolutePath();
      }

      if(corpusID != null && indexLocation != null) {
        wasDeleteQuery = true;
        Term term = new Term(Constants.CORPUS_ID, corpusID);
        TermQuery tq = new TermQuery(term);
        try {
          gate.creole.annic.apache.lucene.search.Searcher searcher =
              new IndexSearcher(indexLocation);
          // and now execute the query
          // result of which will be stored in hits
          luceneHits = searcher.search(tq);
          success = luceneHits.length() > 0 ? true : false;
          return success;
        } catch(IOException ioe) {
          ioe.printStackTrace();
          throw new SearchException(ioe);
        }
      }
    }

    // check for index locations
    if(parameters.get(Constants.INDEX_LOCATIONS) == null) {
      String indexLocation;
      try {
        indexLocation =
            new File(((URL)datastore.getIndexer().getParameters()
                .get(Constants.INDEX_LOCATION_URL)).toURI()).getAbsolutePath();

      } catch(URISyntaxException use) {
        indexLocation =
            new File(((URL)datastore.getIndexer().getParameters()
                .get(Constants.INDEX_LOCATION_URL)).getFile())
                .getAbsolutePath();
      }
      ArrayList<String> indexLocations = new ArrayList<String>();
      indexLocations.add(indexLocation);
      parameters.put(Constants.INDEX_LOCATIONS, indexLocations);
    }

    indexLocations =
        new ArrayList<String>(
            (List<? extends String>)parameters.get(Constants.INDEX_LOCATIONS));

    if(indexLocations.size() == 0)
      throw new SearchException("Corpus is not initialized");

    // check for valid context window
    if(parameters.get(Constants.CONTEXT_WINDOW) == null)
      throw new SearchException("Parameter " + Constants.CONTEXT_WINDOW
          + " is not provided!");

    contextWindow =
        ((Integer)parameters.get(Constants.CONTEXT_WINDOW)).intValue();

    if(getContextWindow().intValue() <= 0)
      throw new SearchException("Context Window must be atleast 1 or > 1");

    if(query == null) throw new SearchException("Query is not initialized");

    this.query = query;
    this.corpusToSearchIn = (String)parameters.get(Constants.CORPUS_ID);
    this.annotationSetToSearchIn =
        (String)parameters.get(Constants.ANNOTATION_SET_ID);

    annicPatterns = new ArrayList<Pattern>();
    annotationTypesMap = new HashMap<String, List<String>>();

    luceneSearchThreads = new ArrayList<LuceneSearchThread>();

    // for different indexes, we create a different instance of
    // indexSearcher
    // TODO: is this really useful or used to have several indexLocations ?
    for(int indexCounter = 0; indexCounter < indexLocations.size(); indexCounter++) {
      String location = indexLocations.get(indexCounter);
      // we create a separate Thread for each index
      LuceneSearchThread lst = new LuceneSearchThread();
      if(lst.search(query, contextWindow, location, corpusToSearchIn,
          annotationSetToSearchIn, this)) {
        luceneSearchThreads.add(lst);
      }
    }

    success = luceneSearchThreads.size() > 0 ? true : false;
    return success;
  }

  /**
   * Gets the submitted query.
   */
  @Override
  public String getQuery() {
    return this.query;
  }

  /**
   * Gets the number of base token annotations to show in the context.
   */
  public Integer getContextWindow() {
    return this.contextWindow;
  }

  /**
   * Gets the found hits (annic patterns).
   */
  @Override
  public Hit[] getHits() {
    if(annicPatterns == null) annicPatterns = new ArrayList<Pattern>();
    Hit[] hits = new Hit[annicPatterns.size()];
    for(int i = 0; i < annicPatterns.size(); i++) {
      hits[i] = annicPatterns.get(i);
    }
    return hits;
  }

  /**
   * Gets the map of found annotation types and annotation features. This call
   * must be invoked only after a call to the
   * getIndexedAnnotationSetNames(String indexLocation) method. Otherwise this
   * method doesn't guranttee the correct results. The results obtained has the
   * following format. Key: CorpusName;AnnotationSetName;AnnotationType Value:
   * respective features
   */
  @Override
  public Map<String, List<String>> getAnnotationTypesMap() {
    return annotationTypesMap;
  }

  /**
   * This method returns a set of annotation set names that are indexed. Each
   * entry has the following format:
   * <p>
   * corpusName;annotationSetName
   * </p>
   * where, the corpusName is the name of the corpus the annotationSetName
   * belongs to.
   */
  @Override
  public String[] getIndexedAnnotationSetNames() throws SearchException {
    String indexLocation;
    try {
      indexLocation =
          new File(((URL)datastore.getIndexer().getParameters()
              .get(Constants.INDEX_LOCATION_URL)).toURI()).getAbsolutePath();

    } catch(URISyntaxException use) {
      indexLocation =
          new File(((URL)datastore.getIndexer().getParameters()
              .get(Constants.INDEX_LOCATION_URL)).getFile()).getAbsolutePath();
    }

    annotationTypesMap = new HashMap<String, List<String>>();
    Set<String> toReturn = new HashSet<String>();
    try {
      IndexReader reader = IndexReader.open(indexLocation);
      try {

        // lets first obtain stored corpora
        TermEnum terms =
            reader.terms(new Term(Constants.ANNOTATION_SET_ID, ""));
        if(terms == null) { return new String[0]; }

        // iterating over terms and finding out names of annotation sets indexed
        Set<String> annotSets = new HashSet<String>();
        boolean foundAnnotSet = false;
        do {
          Term t = terms.term();
          if(t == null) continue;

          if(t.field().equals(Constants.ANNOTATION_SET_ID)) {
            annotSets.add(t.text());
            foundAnnotSet = true;
          } else {
            if(foundAnnotSet) break;
          }
        } while(terms.next());

        
        // we query for each annotation set
        // and go through docs with that annotation set in them
        // to see which corpus they belong to.
        // we could have done the other way round as well (i.e. 
        // first asking for corpus ids and then obtainin annotation set ids
        // but not all documents belong to corpora
        for(String annotSet : annotSets) {
          Term term = new Term(Constants.ANNOTATION_SET_ID, annotSet);
          TermQuery tq = new TermQuery(term);
          try {
            gate.creole.annic.apache.lucene.search.Searcher searcher =
                new IndexSearcher(indexLocation);
            try {
              Hits annotSetHits = searcher.search(tq);
              for(int i = 0; i < annotSetHits.length(); i++) {
                Document luceneDoc = annotSetHits.doc(i);
                String corpusID = luceneDoc.get(Constants.CORPUS_ID);
                if(corpusID == null) corpusID = "";
                toReturn.add(corpusID + ";" + annotSet);

                // lets create a boolean query
                Term annotSetTerm =
                    new Term(Constants.ANNOTATION_SET_ID, annotSet);
                TermQuery atq = new TermQuery(annotSetTerm);

                BooleanQuery bq = new BooleanQuery();
                bq.add(tq, true, false);
                bq.add(atq, true, false);
                gate.creole.annic.apache.lucene.search.Searcher indexFeatureSearcher =
                    new IndexSearcher(indexLocation);
                try {
                  Hits indexFeaturesHits = searcher.search(bq);
                  for(int j = 0; j < indexFeaturesHits.length(); j++) {
                    Document aDoc = indexFeaturesHits.doc(j);
                    String indexedFeatures =
                        aDoc.get(Constants.INDEXED_FEATURES);
                    if(indexedFeatures != null) {
                      String[] features = indexedFeatures.split(";");
                      for(String aFeature : features) {
                        // AnnotationType.FeatureName
                        int index = aFeature.indexOf(".");
                        if(index == -1) {
                          continue;
                        }
                        String type = aFeature.substring(0, index);
                        String featureName = aFeature.substring(index + 1);
                        String key = corpusID + ";" + annotSet + ";" + type;
                        List<String> listOfFeatures =
                            annotationTypesMap.get(key);
                        if(listOfFeatures == null) {
                          listOfFeatures = new ArrayList<String>();
                          annotationTypesMap.put(key, listOfFeatures);
                        }
                        if(!listOfFeatures.contains(featureName)) {
                          listOfFeatures.add(featureName);
                        }
                      }
                    }
                  }
                } finally {
                  indexFeatureSearcher.close();
                }
              }
            } finally {
              searcher.close();
            }
          } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new SearchException(ioe);
          }
        }
      } finally {
        reader.close();
      }
    } catch(IOException ioe) {
      throw new SearchException(ioe);
    }
    return toReturn.toArray(new String[0]);
  }

  /**
   * Gets the search parameters set by user.
   */
  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

  /**
   * A Map used for caching query tokens created for a query.
   */
  private Map<String, List<String>> queryTokens =
      new HashMap<String, List<String>>();

  /**
   * Gets the query tokens for the given query.
   */
  public synchronized List<String> getQueryTokens(String query) {
    return queryTokens.get(query);
  }

  /**
   * Adds the query tokens for the given query.
   */
  public synchronized void addQueryTokens(String query, List<String> queryTokens) {
    this.queryTokens.put(query, queryTokens);
  }

  /**
   * This method allow exporting results in to the provided file. This method
   * has not been implemented yet.
   */
  @Override
  public void exportResults(File outputFile) {
    throw new RuntimeException("ExportResults method is not implemented yet!");
  }

  @Override
  public int freq(String corpusToSearchIn, String annotationSetToSearchIn,
      String annotationType, String featureName, String value)
      throws SearchException {

    String indexLocation;
    try {
      indexLocation =
          new File(((URL)datastore.getIndexer().getParameters()
              .get(Constants.INDEX_LOCATION_URL)).toURI()).getAbsolutePath();

    } catch(URISyntaxException use) {
      indexLocation =
          new File(((URL)datastore.getIndexer().getParameters()
              .get(Constants.INDEX_LOCATION_URL)).getFile()).getAbsolutePath();
    }
    IndexSearcher indexSearcher;
    try { // open the IndexSearcher
      indexSearcher = new IndexSearcher(indexLocation);
    } catch(IOException e) {
      e.printStackTrace();
      return -1;
    }
    int result =
        StatsCalculator.freq(indexSearcher, corpusToSearchIn,
            annotationSetToSearchIn, annotationType, featureName, value);
    try { // close the IndexSearcher
      indexSearcher.close();
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return -1;
    }
    return result;
  }

  @Override
  public int freq(String corpusToSearchIn, String annotationSetToSearchIn,
      String annotationType) throws SearchException {
    return this.freq(corpusToSearchIn, annotationSetToSearchIn, annotationType,
        null, null);
  }

  @Override
  public int freq(String corpusToSearchIn, String annotationSetToSearchIn,
      String annotationType, String featureName) throws SearchException {
    return this.freq(corpusToSearchIn, annotationSetToSearchIn, annotationType,
        featureName, null);
  }

  @Override
  public int freq(List<Hit> patternsToSearchIn, String annotationType,
      String feature, String value, boolean inMatchedSpan, boolean inContext)
      throws SearchException {
    return StatsCalculator.freq(patternsToSearchIn, annotationType, feature,
        value, inMatchedSpan, inContext);
  }

  @Override
  public int freq(List<Hit> patternsToSearchIn, String annotationType,
      boolean inMatchedSpan, boolean inContext) throws SearchException {
    return StatsCalculator.freq(patternsToSearchIn, annotationType,
        inMatchedSpan, inContext);
  }

  @Override
  public Map<String, Integer> freqForAllValues(List<Hit> patternsToSearchIn,
      String annotationType, String feature, boolean inMatchedSpan,
      boolean inContext) throws SearchException {
    return StatsCalculator.freqForAllValues(patternsToSearchIn, annotationType,
        feature, inMatchedSpan, inContext);
  }

  public void setLuceneDatastore(gate.persist.LuceneDataStoreImpl datastore) {
    this.datastore = datastore;
  }

}
