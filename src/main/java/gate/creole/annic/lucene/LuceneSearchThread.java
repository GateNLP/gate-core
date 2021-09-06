/*
 *  LuceneSearchThread.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneSearchThread.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import gate.Gate;
import gate.creole.annic.Constants;
import gate.creole.annic.Pattern;
import gate.creole.annic.PatternAnnotation;
import gate.creole.annic.SearchException;
import gate.creole.annic.apache.lucene.search.Hits;
import gate.creole.annic.apache.lucene.search.Query;

/**
 * Given a boolean query, it is translated into one or more AND
 * normalized queries. For example: (A|B)C is translated into AC and BC.
 * For each such query an instance of LuceneSearchThread is created.
 * Here, each query is issued separately and results are submitted to
 * main instance of LuceneSearch.
 * 
 * @author niraj
 */
public class LuceneSearchThread {

  /**
   * Debug variable
   */
  private static boolean DEBUG = false;

  private static final XStream xstream;
  
  static {
	  xstream = new XStream(new StaxDriver());
	  Gate.configureXStreamSecurity(xstream);
  }

  /**
   * Number of base token annotations to be used in context.
   */
  private int contextWindow;

  /**
   * The location of index.
   */
  private String indexLocation;

  /**
   * Instance of a QueryParser.
   */
  private QueryParser queryParser;

  /**
   * BaseTokenAnnotationType.
   */
  private String baseTokenAnnotationType;

  /**
   * Instance of the LuceneSearcher.
   */
  private LuceneSearcher luceneSearcher;

  /**
   * Indicates if searching process is finished.
   */
  public boolean finished = false;

  /**
   * Index of the serializedFileID we are currently searching for.
   */
  private int serializedFileIDIndex = 0;

  /**
   * QueryItemIndex
   */
  private int queryItemIndex = 0;

  /**
   * List of serialized Files IDs retrieved from the lucene index
   */
  private List<String> serializedFilesIDsList = new ArrayList<String>();

  /**
   * A Map that holds information about search results.
   */
  private Map<String, List<QueryItem>> searchResultInfoMap = new HashMap<String, List<QueryItem>>();

  /**
   * First term position index.
   */
  private int ftpIndex = 0;

  /**
   * Indicates if the query was success.
   */
  private boolean success = false;

  /**
   * Indicates if we've reached the end of search results.
   */
  private boolean fwdIterationEnded = false;

  /**
   * We keep track of what was the last ID of the serialized File that we visited. This is
   * used for optimization reasons
   */
  private String serializedFileIDInUse = null;

  /**
   * This is where we store the tokenStreamInUse
   */
  private List<gate.creole.annic.apache.lucene.analysis.Token> tokenStreamInUse = null;

  /**
   * Query
   */
  private String query = null;

  /**
   * Given a file name, it replaces the all invalid characters with '_'.
   */
  private String getCompatibleName(String name) {
    return name.replaceAll("[\\/:\\*\\?\"<>|]", "_");
  }

  /**
   * This method collects the necessary information from lucene and uses
   * it when the next method is called
   * 
   * @param query query supplied by the user
   * @param patternWindow number of tokens to refer on left and right
   *          context
   * @param indexLocation location of the index the searcher should
   *          search in
   * @param luceneSearcher an instance of lucene search from where the
   *          instance of SearchThread is invoked
   * @return true iff search was successful false otherwise
   */
  @SuppressWarnings("unchecked")
  public boolean search(String query, int patternWindow, String indexLocation,
          String corpusToSearchIn, String annotationSetToSearchIn,
          LuceneSearcher luceneSearcher) throws SearchException {

    this.query = query;
    this.contextWindow = patternWindow;
    this.indexLocation = indexLocation;
    this.queryParser = new QueryParser();
    this.luceneSearcher = luceneSearcher;

    /*
     * reset all parameters that keep track of where we are in our
     * searching. These parameters are used mostly to keep track of
     * where to start fetching the next results from
     */
    searchResultInfoMap = new HashMap<String, List<QueryItem>>();
    serializedFileIDIndex = 0;
    queryItemIndex = 0;
    serializedFilesIDsList = new ArrayList<String>();
    ftpIndex = -1;
    success = false;
    fwdIterationEnded = false;

    try {
      // first find out the location of Index
      //TODO does this just replace \ with / if so we should do this better
      StringBuilder temp = new StringBuilder();
      for(int i = 0; i < indexLocation.length(); i++) {
        if(indexLocation.charAt(i) == '\\') {
          temp.append("/");
        }
        else {
          temp.append(indexLocation.charAt(i));
        }
      }
      indexLocation = temp.toString();

      /*
       * for each different location there can be different
       * baseTokenAnnotationType each index will have their index
       * Definition file stored under the index directory so first see
       * if given location is a valid directory
       */
      File locationFile = new File(indexLocation);
      if(!locationFile.isDirectory()) {
        System.out.println("Skipping the invalid Index Location :"
                + indexLocation);
        return false;
      }

      if(!indexLocation.endsWith("/")) {
        indexLocation += "/";
      }

      // otherwise let us read the index definition file
      locationFile = new File(indexLocation + "LuceneIndexDefinition.xml");

      // check if this file is available
      if(!locationFile.exists()) {
        System.out
                .println("Index Definition file not found - Skipping the invalid Index Location :"
                        + indexLocation + "LuceneIndexDefinition.xml");
        return false;
      }

      Map<String,Object> indexInformation = null;
      
      // other wise read this file
      try (FileReader fileReader =
          new FileReader(indexLocation + "LuceneIndexDefinition.xml");) {

        // Saving was accomplished by using XML serialization of the map.
        indexInformation = (Map<String, Object>)xstream.fromXML(fileReader);
      }

      // find out if the current index was indexed by annicIndexPR
      String indexedWithANNICIndexPR = (String)indexInformation
              .get(Constants.CORPUS_INDEX_FEATURE);

      if(indexedWithANNICIndexPR == null
              || !indexedWithANNICIndexPR
                      .equals(Constants.CORPUS_INDEX_FEATURE_VALUE)) {
        System.out
                .println("This corpus was not indexed by Annic Index PR - Skipping the invalid Index");
        return false;
      }

      // find out the baseTokenAnnotationType name
      baseTokenAnnotationType = ((String)indexInformation
              .get(Constants.BASE_TOKEN_ANNOTATION_TYPE)).trim();

      int separatorIndex = baseTokenAnnotationType.lastIndexOf('.');
      if(separatorIndex >= 0) {
        baseTokenAnnotationType = baseTokenAnnotationType
                .substring(separatorIndex + 1);
      }

      // create various Queries from the user's query
      Query[] luceneQueries = queryParser.parse("contents", query,
              baseTokenAnnotationType, corpusToSearchIn,
              annotationSetToSearchIn);
      if(queryParser.needValidation()) {
        if(DEBUG) System.out.println("Validation enabled!");
      }
      else {
        if(DEBUG) System.out.println("Validation disabled!");
      }

      // create an instance of Index Searcher
      LuceneIndexSearcher searcher = new LuceneIndexSearcher(indexLocation);
      
      try {
        // we need to iterate through one query at a time
        for(int luceneQueryIndex = 0; luceneQueryIndex < luceneQueries.length; luceneQueryIndex++) {
            
          /*
           * this call reinitializes the first Term positions arraylists
           * which are being used to store the results
           */
          searcher.initializeTermPositions();
  
          /*
           * and now execute the query result of which will be stored in
           * hits
           */
          Hits hits = searcher.search(luceneQueries[luceneQueryIndex]);
  
          /*
           * and so now find out the positions of the first terms in the
           * returned results. first term position is the position of the
           * first term in the found pattern
           */
          List<?>[] firstTermPositions = searcher.getFirstTermPositions();
          // if no result available, set null to our scores
          if(firstTermPositions[0].size() == 0) {
            // do nothing
            continue;
          }
  
  
         
          // iterate through each result and collect necessary
          // information
          for(int hitIndex = 0; hitIndex < hits.length(); hitIndex++) {
            int index = firstTermPositions[0].indexOf(Integer.valueOf(hits
                    .id(hitIndex)));
  
            // we fetch all the first term positions for the query
            // issued
            List<?> ftp = (List<?>)firstTermPositions[1].get(index);

            /*
             * pattern length (in terms of total number of annotations
             * following one other)
             */
            int patLen = ((Integer)firstTermPositions[2].get(index)).intValue();
  
            /*
             * and the type of query (if it has only one annotation in it,
             * or multiple terms following them)
             */
            int qType = ((Integer)firstTermPositions[3].get(index)).intValue();
  
            // find out the documentID
            String serializedFileID = hits.doc(hitIndex).get(Constants.DOCUMENT_ID_FOR_SERIALIZED_FILE);
            QueryItem queryItem = new QueryItem();
            queryItem.annotationSetName = hits.doc(hitIndex).get(
                    Constants.ANNOTATION_SET_ID).intern();
            queryItem.id = hits.id(hitIndex);
            queryItem.documentID = hits.doc(hitIndex).get(Constants.DOCUMENT_ID).intern();
            queryItem.ftp = ftp;
            queryItem.patLen = patLen;
            queryItem.qType = qType;
            queryItem.query = luceneQueries[luceneQueryIndex];
            queryItem.queryString = queryParser.getQueryString(luceneQueryIndex).intern();
  
            /*
             * all these information go in the top level arrayList. we
             * create separate arrayList for each individual document
             * where each element in the arrayList provides information
             * about different query issued over it
             */
            List<QueryItem> queryItemsList = searchResultInfoMap.get(serializedFileID);
            if(queryItemsList == null) {
              queryItemsList = new ArrayList<QueryItem>();
              queryItemsList.add(queryItem);
              searchResultInfoMap.put(serializedFileID, queryItemsList);
              serializedFilesIDsList.add(serializedFileID);
            }
            else {
//              // before inserting we check if it is already added
//              if(!doesAlreadyExist(queryItem, queryItemsList)) {
                queryItemsList.add(queryItem);
//              }
            }
          }
        }
      }
      finally {
        searcher.close();
      }
      // if any result possible, return true
      if(searchResultInfoMap.size() > 0)
        success = true;
      else success = false;
    } catch(IOException | gate.creole.ir.SearchException e) {
      throw new SearchException(e);
    }

    return success;
  }

  /**
   * First term positions.
   */
  private List<?> ftp;

  /**
   * This method returns a list containing instances of Pattern
   * 
   * @param numberOfResults the number of results to fetch
   * @return a list of QueryResult
   * @throws Exception
   */
  public List<Pattern> next(int numberOfResults) throws Exception {

    /*
     * We check here, if there were no results found, we return null
     */
    if(!success) {
      return null;
    }

    if(fwdIterationEnded) {
      return null;
    }

    int noOfResultsToFetch = numberOfResults;
    List<Pattern> toReturn = new ArrayList<Pattern>();

    // iterator over one document ID
    for(; serializedFileIDIndex < serializedFilesIDsList.size(); serializedFileIDIndex++, queryItemIndex = 0, this.ftp = null) {

      // deal with one document at a time
      String serializedFileID = serializedFilesIDsList.get(serializedFileIDIndex);

      // obtain the information about all queries
      List<QueryItem> queryItemsList = searchResultInfoMap.get(serializedFileID);
      if(queryItemsList.isEmpty()) continue;
      String folder = queryItemsList.get(0).documentID.intern();
      
      if(serializedFileIDInUse == null || !serializedFileIDInUse.equals(serializedFileID)
              || tokenStreamInUse == null) {
        serializedFileIDInUse = serializedFileID;
        try {
          // this is the first and last time we want this tokenStream
          // to hold information about the current document
          tokenStreamInUse = getTokenStreamFromDisk(indexLocation,getCompatibleName(folder),
                  getCompatibleName(serializedFileID));
        }
        catch(Exception e) {
          continue;
        }
      }

      // deal with one query at a time
      for(; queryItemIndex < queryItemsList.size(); queryItemIndex++, ftpIndex = -1, this.ftp = null) {
        QueryItem queryItem = queryItemsList.get(queryItemIndex);

        /*
         * we've found the tokenStream and now we need to convert it
         * into the format we had at the time of creating index.. the
         * method getTokenStream(...) returns an array of arraylists
         * where the first object is GateAnnotations of that pattern
         * only second object is the position of the first token of the
         * actual pattern third object is the lenght of the actual
         * pattern
         */
        int qType = queryItem.qType;
        int patLen = queryItem.patLen;
        if(this.ftp == null) {
          this.ftp = queryItem.ftp;
        }
        else {
          qType = 1;
          patLen = 1;
        }
        PatternResult patternResult = getPatternResult(tokenStreamInUse,
                queryItem.annotationSetName, patLen, qType, contextWindow,
                queryItem.queryString, baseTokenAnnotationType,
                noOfResultsToFetch);

        /*
         * if none of the found patterns is valid continue with the next
         * query
         */
        if(patternResult.numberOfPatterns == 0)
          continue;

        /*
         * We've found some patterns so give its effect to
         * noOfResultsToFetch
         */
        if(noOfResultsToFetch != -1)
          noOfResultsToFetch -= patternResult.numberOfPatterns;

        List<Pattern> annicPatterns = createAnnicPatterns(new LuceneQueryResult(
                removeUnitNumber(serializedFileID), patternResult.annotationSetName,
                patternResult.firstTermPositions, patternResult.patternLegths,
                queryItem.qType, patternResult.gateAnnotations,
                queryItem.queryString));
        toReturn.addAll(annicPatterns);

        /*
         * If noOfResultsToFetch is 0, it means the search should
         * terminate unless and otherwise user has asked to return all
         * (-1)
         */
        if(numberOfResults != -1 && noOfResultsToFetch == 0) {
          return toReturn;
        }
      }
    }

    /*
     * if we are out of the loop set success to false such that this
     * thread is closed
     */
    fwdIterationEnded = true;
    return toReturn;
  }

  /**
   * Given an object of luceneQueryResult this method for each found
   * pattern, converts it into the annic pattern. In other words, for
   * each pattern it collects the information such as annotations in
   * context and so on.
   */
  private List<Pattern> createAnnicPatterns(LuceneQueryResult aResult) {
    // get the result from search engine
    List<Pattern> annicPatterns = new ArrayList<Pattern>();
    List<?> firstTermPositions = aResult.getFirstTermPositions();
    if(firstTermPositions != null && firstTermPositions.size() > 0) {
      List<Integer> patternLength = aResult.patternLength();
      // locate Pattern
      List<Pattern> pats = locatePatterns((String)aResult.getDocumentID(),
              aResult.getAnnotationSetName(), aResult.getGateAnnotations(),
              firstTermPositions, patternLength, aResult.getQuery());
      
      annicPatterns.addAll(pats);
      
    }
    return annicPatterns;
  }

  /**
   * Locates the valid patterns in token stream and discards the invalid
   * first term positions returned by the lucene searcher.
   */
  private List<Pattern> locatePatterns(String docID, String annotationSetName,
          List<List<PatternAnnotation>> gateAnnotations,
          List<?> firstTermPositions, List<Integer> patternLength,
          String queryString) {

    // patterns
    List<Pattern> pats = new ArrayList<Pattern>();
    for(int i = 0; i < gateAnnotations.size(); i++) {

      // each element in the tokens stream is a pattern
      List<PatternAnnotation> annotations = gateAnnotations.get(i);
      if(annotations.size() == 0) {
        continue;
      }
      // from this annotations we need to create a text string
      // so lets find out the smallest and the highest offsets
      int smallest = Integer.MAX_VALUE;
      int highest = -1;
      for(int j = 0; j < annotations.size(); j++) {
        // each annotation is an instance of GateAnnotation
        PatternAnnotation ga = annotations.get(j);
        if(ga.getStartOffset() < smallest) {
          smallest = ga.getStartOffset();
        }

        if(ga.getEndOffset() > highest) {
          highest = ga.getEndOffset();
        }
      }

      // we have smallest and highest offsets
      char[] patternText = new char[highest - smallest];

      for(int j = 0; j < patternText.length; j++) {
        patternText[j] = ' ';
      }

      // and now place the text
      for(int j = 0; j < annotations.size(); j++) {
        // each annotation is an instance of GateAnnotation
        PatternAnnotation ga = annotations.get(j);
        if(ga.getText() == null) {
          // this is to avoid annotations such as split
          continue;
        }

        for(int k = ga.getStartOffset() - smallest, m = 0; m < ga.getText()
                .length()
                && k < patternText.length; m++, k++) {
          patternText[k] = ga.getText().charAt(m);
        }

        // we will initiate the annotTypes as well
        if(luceneSearcher.annotationTypesMap.keySet().contains(ga.getType())) {
          List<String> aFeatures = luceneSearcher.annotationTypesMap.get(ga
                  .getType());
          Map<String, String> features = ga.getFeatures();
          if(features != null) {
            Iterator<String> fSet = features.keySet().iterator();
            while(fSet.hasNext()) {
              String feature = fSet.next();
              if(!aFeatures.contains(feature)) {
                aFeatures.add(feature);
              }
            }
          }
          luceneSearcher.annotationTypesMap.put(ga.getType(), aFeatures);
        }
        else {
          Map<String, String> features = ga.getFeatures();
          List<String> aFeatures = new ArrayList<String>();
          aFeatures.add("All");
          if(features != null) {
            aFeatures.addAll(features.keySet());
          }
          luceneSearcher.annotationTypesMap.put(ga.getType(), aFeatures);
        }
        // end of initializing annotationTypes for the comboBox
      }

      // we have the text
      // smallest is the textStOffset
      // highest is the textEndOffset
      // how to find the patternStartOffset
      int stPos = ((Integer)firstTermPositions.get(i)).intValue();
      int endOffset = patternLength.get(i).intValue();
      int patStart = Integer.MAX_VALUE;

      for(int j = 0; j < annotations.size(); j++) {
        // each annotation is an instance of GateAnnotation
        PatternAnnotation ga = annotations.get(j);
        if(ga.getPosition() == stPos) {
          if(ga.getStartOffset() < patStart) {
            patStart = ga.getStartOffset();
          }
        }
      }

      if(patStart == Integer.MAX_VALUE) {
        continue;
      }

      if(patStart < smallest || endOffset > highest) {
        continue;
      }

      // now create the pattern for this
      Pattern ap = new Pattern(docID, annotationSetName,
              new String(patternText), patStart, endOffset, smallest, highest,
              annotations, queryString);
      pats.add(ap);
    }
    return pats;
  }

  /**
   * Each index unit is first converted into a separate lucene document.
   * And a new ID with documentName and a unit number is assined to it.
   * But when we return results, we take the unit number out.
   */
  private String removeUnitNumber(String documentID) {
    int index = documentID.lastIndexOf("-");
    if(index == -1) return documentID;
    return documentID.substring(0, index);
  }

  /**
   * This method looks on the disk to find the tokenStream
   */
  private List<gate.creole.annic.apache.lucene.analysis.Token> getTokenStreamFromDisk(
          String indexDirectory, String documentFolder, String documentID) throws Exception {
    if(indexDirectory.startsWith("file:/"))
      indexDirectory = indexDirectory.substring(6, indexDirectory.length());

    // use buffering
    File folder = new File(indexDirectory, Constants.SERIALIZED_FOLDER_NAME);
    folder = new File(folder, documentFolder);
    File fileToLoad = new File(folder, documentID + ".annic");
    
    try (InputStream file = new FileInputStream(fileToLoad);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);) {

      // deserialize the List
      @SuppressWarnings("unchecked")
      List<gate.creole.annic.apache.lucene.analysis.Token> recoveredTokenStream =
          (List<gate.creole.annic.apache.lucene.analysis.Token>)input
              .readObject();

      return recoveredTokenStream;
    }    
  }

  /**
   * this method takes the tokenStream as a text, the first term
   * positions, pattern length, queryType and patternWindow and returns
   * the GateAnnotations as an array for each pattern with left and
   * right context
   */
  private PatternResult getPatternResult(
          List<gate.creole.annic.apache.lucene.analysis.Token> subTokens,
          String annotationSetName, int patLen, int qType, int patWindow,
          String query, String baseTokenAnnotationType,
          int numberOfResultsToFetch) {

    /*
     * ok so we first see what kind of query is that two possibilities
     * (Phrase query or Term query) Term query is what contains only one
     * word to seach and Phrase query contains more than one word 1
     * indicates the PhraseQuery
     */
    if(qType == 1) {
      return getPatternResult(subTokens, annotationSetName, patLen, patWindow,
              query, baseTokenAnnotationType, numberOfResultsToFetch);
    }
    else {
      /*
       * where the query is Term. In term query it is possible that user
       * is searching for the particular annotation type (say: "Token"
       * or may be for text (say: "Hello") query parser converts the
       * annotation type query into Token == "*" and the latter to
       * Token.string == "Hello"
       */

      /*
       * the first element is text. the second element is type
       */
      String annotText = (String)ftp.get(0);
      String annotType = (String)ftp.get(1);

      // so here we search through subTokens and find out the positions
      List<Integer> positions = new ArrayList<Integer>();
      for(int j = 0; j < subTokens.size(); j++) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(j);
        String type = token.termText();
        String text = token.type();

        // if annotType == "*", the query was {AnnotType}
        if(annotType.equals("*")) {
          if(type.equals(annotText) && annotType.equals(text)) {
            positions.add(token.getPosition());
          }
        }
        // the query is Token == "string"
        else {
          if(annotText.equals(type) && annotType.equals(text)) {
            positions.add(token.getPosition());
          }
        }
      }

      this.ftp = positions;
      // we have positions here
      return getPatternResult(subTokens, annotationSetName, 1, patWindow,
              query, baseTokenAnnotationType, numberOfResultsToFetch);
    }
  }

  /**
   * This method returns the valid patterns back and the respective
   * GateAnnotations
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private PatternResult getPatternResult(
          List<gate.creole.annic.apache.lucene.analysis.Token> subTokens,
          String annotationSetName, int patLen, int patWindow, String query,
          String baseTokenAnnotationType, int noOfResultsToFetch) {

    List<List<PatternAnnotation>> tokens = new ArrayList<List<PatternAnnotation>>();
    List<Integer> patLens = new ArrayList<Integer>();
    ftpIndex++;

    // Phrase Query
    // consider only one pattern at a time

    // first term position index at the begining
    int ftpIndexATB = ftpIndex;
    mainForLoop: for(; ftpIndex < ftp.size()
            && (noOfResultsToFetch == -1 || noOfResultsToFetch > 0); ftpIndex++) {

      // find out the position of the first term
      int pos = ((Integer)ftp.get(ftpIndex)).intValue();

      // find out the token with pos
      int j = 0;
      for(; j < subTokens.size(); j++) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(j);
        if(token.getPosition() == pos) {
          break;
        }
      }

      int counter = 0;
      int leftstart = -1;
      /*
       * ok so we need to go back to find out the first token of the
       * left context
       */
      int k = j - 1;
      for(; k >= 0; k--) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(k);
        if(token.getPosition() < pos
                && token.termText().equals(baseTokenAnnotationType)
                && token.type().equals("*")) {
          counter++;
          leftstart = token.startOffset();
          j = k;
        }
        if(counter == patWindow) {
          break;
        }
      }

      // j holds the start of the left context

      // now we want to search for the end of left context
      pos--;
      k = j;

      if(leftstart > -1) {

        boolean breakNow = false;
        for(; k < subTokens.size(); k++) {
          gate.creole.annic.apache.lucene.analysis.Token token = subTokens
                  .get(k);
          if(token.getPosition() == pos) {
            breakNow = true;
          }
          else {
            if(breakNow) {
              break;
            }
          }
        }
      }
      // now k holds the begining of the pattern

      // leftEnd holds the position of the last token in left context
      int leftEnd = leftstart == -1 ? -1 : k - 1;

      /*
       * we need to validate this pattern. As a result of query, we get
       * the positions of the first term. We need to locate the full
       * pattern along with all its other annotations. This is done by
       * using the ValidatePattern class. This class provides a method,
       * which takes as arguments the query Tokens, the position in the
       * tokenStream from where to start searching and returns the end
       * offset of the last annotation in the found pattern. We then
       * search for this endoffset in our current tokenStream to
       * retrieve the wanted annotations.
       */
      int upto = -1;
      int tempPos = 0;
      if(this.queryParser.needValidation()) {

        try {

          List<String> queryTokens = luceneSearcher.getQueryTokens(query);
          if(queryTokens == null) {
            queryTokens = new QueryParser().findTokens(query);
            luceneSearcher.addQueryTokens(query, queryTokens);
          }

          /*
           * validate method returns the endoffset of the last token of
           * the middle pattern returns -1 if pattern could not be
           * located at that location
           */
          PatternValidator vp = new PatternValidator();

          // here k is the position where the first token should occur

          upto = vp.validate(queryTokens, subTokens, k, new QueryParser());
          if(upto == -1) {
            /*
             * if the validatePAttern class could not find the valid
             * pattern it returns -1 and therefore we should remove the
             * position of the invalid pattern
             */
            ftp.remove(ftpIndex);
            ftpIndex--;
            continue mainForLoop;
          }
          else {
            /*
             * now we need to locate the token whose endPosition is upto
             */
            int jj = leftEnd + 1;
            boolean breaknow = false;
            tempPos = subTokens.get(jj).getPosition();
            for(; jj < subTokens.size(); jj++) {
              gate.creole.annic.apache.lucene.analysis.Token token = subTokens
                      .get(jj);
              if(token.endOffset() == upto) {
                tempPos = token.getPosition();
                breaknow = true;
              }
              else if(breaknow) {
                break;
              }
            }
            // we send the endoffset to our GUI class
            patLens.add(upto);

            /*
             * k holds the position of the first token in right context
             */
            k = jj;
          }
        }
        catch(Exception e) {
          e.printStackTrace();
        }
      }
      else {
        /*
         * the query contains all tokens, which is already validated at
         * the time of creating query the pointer k points to the
         * begining of our patern we need to travel patLen into the
         * right direction to obtain the pattern
         */
        for(counter = 0; counter < patLen && k < subTokens.size(); k++) {
          gate.creole.annic.apache.lucene.analysis.Token token = subTokens
                  .get(k);
          if(token.termText().equals(baseTokenAnnotationType)
                  && token.type().equals("*")) {
            counter++;
            upto = token.endOffset();
            tempPos = token.getPosition();
          }
        }
        patLens.add(upto);
        k++;
      }
      int maxEndOffset = upto;

      /*
       * so now search for the token with the position == tempPos + 1 in
       * other words search for the first term of the right context
       */
      for(; k < subTokens.size(); k++) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(k);
        if(token.getPosition() == tempPos + 1) {
          break;
        }
      }

      // and now we need to locate the right context pattern
      counter = 0;
      for(; k < subTokens.size(); k++) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(k);
        if(token.startOffset() >= upto
                && token.termText().equals(baseTokenAnnotationType)
                && token.type().equals("*")) {
          counter++;
          maxEndOffset = token.endOffset();
        }
        if(counter == patWindow) {
          break;
        }
      }

      // if there are any sub-tokens left
      if(k < subTokens.size()) {
        /*
         * now we would search for the position untill we see it having
         * the same position
         */
        tempPos = subTokens.get(k).getPosition();

        for(; k < subTokens.size(); k++) {
          gate.creole.annic.apache.lucene.analysis.Token token = subTokens
                  .get(k);
          if(token.getPosition() != tempPos) {
            break;
          }
        }
      }

      if(k >= subTokens.size()) {
        // we used all sub-tokens - set k to maximum size
        k = subTokens.size() - 1;
      }

      /*
       * so k is the position til where we need to search for each
       * annotation and every feature in it at the time of creating
       * index were converted into separate tokens we need to convert
       * them back into annotations
       */
      List<PatternAnnotation> patternGateAnnotations = new ArrayList<PatternAnnotation>();
      PatternAnnotation ga = null;
      for(int m = j; m <= k; m++) {
        gate.creole.annic.apache.lucene.analysis.Token token = subTokens.get(m);
        String text = token.termText();
        int st = token.startOffset();
        int end = token.endOffset();
        String type = token.type();
        int position = token.getPosition();

        // if this is a new annotation Type
        if(type.equals("*")) {
          ga = new PatternAnnotation();
          ga.setType(text);
          ga.setStOffset(st);
          ga.setEnOffset(end);
          ga.setPosition(position);
          if(ga.getEndOffset() <= maxEndOffset) {
            patternGateAnnotations.add(ga);
          }
          continue;
        } else if(type.equals("**")) {
          continue;
        }

        // and from here all are the features
        int index = type.indexOf(".");
        String feature = type.substring(index + 1, type.length());
        /*
         * we need to compare the type1 each annotation has string
         * feature in index so text will be definitely going to be
         * initialized
         */
        if(feature.equals("string")) {
          ga.setText(text);
        }
        ga.addFeature(feature, text);
      }
      tokens.add(patternGateAnnotations);
      if(noOfResultsToFetch != -1) noOfResultsToFetch--;
    }

    if(noOfResultsToFetch == 0 && ftpIndex < ftp.size()) ftpIndex--;

    // finally create an instance of PatternResult
    PatternResult pr = new PatternResult();
    pr.annotationSetName = annotationSetName;
    pr.gateAnnotations = tokens;
    pr.firstTermPositions = new ArrayList();
    for(int i = 0; i < pr.gateAnnotations.size(); i++) {
      pr.firstTermPositions.add(ftp.get(i + ftpIndexATB));
    }
    pr.patternLegths = patLens;
    pr.numberOfPatterns = pr.gateAnnotations.size();
    return pr;
  }

  /**
   * Inner class to store pattern results.
   * 
   * @author niraj
   */
  private static class PatternResult {
    int numberOfPatterns;

    List<List<PatternAnnotation>> gateAnnotations;
    
    String annotationSetName;

    @SuppressWarnings("rawtypes")
    List firstTermPositions;

    List<Integer> patternLegths;
  }

  /**
   * Inner class to store query Item.
   * 
   * @author niraj
   * 
   */
  private static class QueryItem {
    @SuppressWarnings("unused")
    float score;

    @SuppressWarnings("unused")
    int id;

    String documentID;
    
    @SuppressWarnings("rawtypes")
    List ftp;

    int patLen;

    int qType;

    @SuppressWarnings("unused")
    Query query;

    String queryString;

    String annotationSetName;

//    public boolean equals(Object m) {
//      if(m instanceof QueryItem) {
//        QueryItem n = (QueryItem)m;
//        // no need to compare documentID as we don't compare documents with different docIDs anyway
//        return n.score == score && n.id == id && n.patLen == patLen
//                && n.qType == qType && n.ftp.size() == ftp.size()
//                && n.queryString.equals(queryString)
//                && n.annotationSetName.equals(annotationSetName)
//                && areTheyEqual(n.ftp, ftp, qType);
//      }
//      return false;
//    }
  }

  /**
   * Checks if the QueryItem already exists.
   */
//  private boolean doesAlreadyExist(QueryItem n, List<QueryItem> top) {
//
//    for(int i = 0; i < top.size(); i++) {
//      QueryItem m = top.get(i);
//      if(m.equals(n)) return true;
//    }
//    return false;
//  }

  /**
   * Checks if two first term positions are identical. 
   */
  @SuppressWarnings({"unused", "rawtypes"})
  private boolean areTheyEqual(List ftp, List ftp1, int qType) {
    if(qType == 1) {
      if(ftp.size() == ftp1.size()) {
        for(int i = 0; i < ftp.size(); i++) {
          int pos = ((Integer)ftp.get(i)).intValue();
          int pos1 = ((Integer)ftp1.get(i)).intValue();
          if(pos != pos1) return false;
        }
        return true;
      }
      else {
        return false;
      }
    }
    else {
      String annotText = (String)ftp.get(0);
      String annotType = (String)ftp.get(1);
      String annotText1 = (String)ftp1.get(0);
      String annotType1 = (String)ftp1.get(1);
      return annotText1.equals(annotText) && annotType1.equals(annotType);
    }
  }

  /**
   * Gets the query.
   */
  public String getQuery() {
    return query;
  }

}
