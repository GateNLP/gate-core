package gate.creole.annic.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gate.creole.annic.Constants;
import gate.creole.annic.Hit;
import gate.creole.annic.Pattern;
import gate.creole.annic.PatternAnnotation;
import gate.creole.annic.SearchException;
import gate.creole.annic.apache.lucene.index.Term;
import gate.creole.annic.apache.lucene.search.BooleanQuery;
import gate.creole.annic.apache.lucene.search.Hits;
import gate.creole.annic.apache.lucene.search.IndexSearcher;
import gate.creole.annic.apache.lucene.search.PhraseQuery;
import gate.creole.annic.apache.lucene.search.TermQuery;

public class StatsCalculator {

  /**
   * Allows retriving frequencies for the given parameters. Please make
   * sure that you close the searcher on your own. Failing to do so may
   * result into many files being opened at the same time and that can
   * cause the problem with your OS.
   * @throws SearchException
   */
  public static int freq(IndexSearcher searcher, String corpusToSearchIn,
          String annotationSetToSearchIn, String annotationType,
          String featureName, String value) throws SearchException {

    try {
      corpusToSearchIn = corpusToSearchIn == null
              || corpusToSearchIn.trim().length() == 0
              ? null
              : corpusToSearchIn.trim();
      annotationSetToSearchIn = annotationSetToSearchIn == null
              || annotationSetToSearchIn.trim().length() == 0
              ? null
              : annotationSetToSearchIn.trim();
      if(annotationType == null)
        throw new SearchException("Annotation Type cannot be null");

      // term that contains a value to be searched in the index
      Term term = null;
      if(featureName == null && value == null) {
        term = new Term("contents", annotationType, "*");
      }
      else if(featureName != null && value == null) {
        term = new Term("contents", annotationType + "." + featureName, "**");
      }
      else if(featureName == null) {
        throw new SearchException("FeatureName cannot be null");
      }
      else {
        term = new Term("contents", value, annotationType + "." + featureName);
      }

      // term query
      TermQuery tq = new TermQuery(term);

      // indicates whether we want to use booleanQuery
      boolean useBooleanQuery = false;
      BooleanQuery bq = new BooleanQuery();

      if(corpusToSearchIn != null) {
        PhraseQuery cq = new PhraseQuery();
        cq.add(new Term(Constants.CORPUS_ID, corpusToSearchIn), 0,
                true);
        bq.add(cq, true, false);
        useBooleanQuery = true;
      }

      if(annotationSetToSearchIn != null) {
        PhraseQuery aq = new PhraseQuery();
        aq.add(new Term(Constants.ANNOTATION_SET_ID, annotationSetToSearchIn),
                0, true);
        bq.add(aq, true, false);
        useBooleanQuery = true;
      }

      Hits corpusHits = null;
      if(useBooleanQuery) {
        bq.add(tq, true, false);
        corpusHits = searcher.search(bq);
      }
      else {
        corpusHits = searcher.search(tq);
      }

      List<?>[] firstTermPositions = searcher.getFirstTermPositions();

      // if no result available, set null to our scores
      if(firstTermPositions[0].size() == 0) {
        return 0;
      }

      int size = 0;
      // iterate through each result and collect necessary
      // information
      for(int hitIndex = 0; hitIndex < corpusHits.length(); hitIndex++) {
        int index = firstTermPositions[0].indexOf(new Integer(corpusHits
                .id(hitIndex)));

        // we fetch all the first term positions for the query
        // issued
        Integer freq = (Integer)firstTermPositions[4].get(index);
        size += freq.intValue();
      }
      return size;
    }
    catch(IOException ioe) {
      throw new SearchException(ioe);
    }
    finally {
      searcher.initializeTermPositions();
    }
  }

  /**
   * @see #freq(IndexSearcher, String, String, String, String, String)
   */
  public static int freq(IndexSearcher searcher, String corpusToSearchIn,
          String annotationSetToSearchIn, String annotationType)
          throws SearchException {

    return freq(searcher, corpusToSearchIn, annotationSetToSearchIn,
            annotationType, null, null);
  }

  /**
   * @see #freq(IndexSearcher, String, String, String, String, String)
   */
  public static int freq(IndexSearcher searcher, String corpusToSearchIn,
          String annotationSetToSearchIn, String annotationType,
          String featureName) throws SearchException {

    return freq(searcher, corpusToSearchIn, annotationSetToSearchIn,
            annotationType, featureName, null);
  }

  /**
   * Allows retrieving frequencies for the given parameters.
   * @param value - set to null if only wants to retrieve frequencies for AT.feature
   * @param inMatchedSpan - true if only interested in frequencies from the matched spans.
   * @param inContext - true if only interested in frequencies from the contexts. Please note that both isMatchedSpan 
   * and inContext can be set to true if interested in frequencies from the entire patterns, but cannot be set false
   * at the same time.
   * @throws SearchException
   */
  public static int freq(List<Hit> patternsToSearchIn,
          String annotationType, String feature, String value,
          boolean inMatchedSpan, boolean inContext) throws SearchException {
    if(patternsToSearchIn == null || patternsToSearchIn.isEmpty()) return 0;

    if(!inMatchedSpan && !inContext)
      throw new SearchException(
              "Both inMatchedSpan and inContext cannot be set to false");

    int count = 0;
    for(Hit aResult1 : patternsToSearchIn) {
      Pattern aResult = (Pattern) aResult1;
      
      List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
      if(inMatchedSpan && !inContext) {
        annots = aResult.getPatternAnnotations(aResult.getStartOffset(),
                aResult.getEndOffset());
      }
      else if(!inMatchedSpan && inContext) {
        annots = aResult.getPatternAnnotations(aResult
                .getLeftContextStartOffset(), aResult.getStartOffset());
        annots.addAll(aResult.getPatternAnnotations(aResult.getEndOffset(),
                aResult.getRightContextEndOffset()));
      }
      else {
        // both matchedSpan and context are set to true
        annots = Arrays.asList(aResult.getPatternAnnotations());
      }

      if(annots.isEmpty()) continue;
      List<PatternAnnotation> subAnnots = null;
      if(value == null) {
        subAnnots = getPatternAnnotations(annots, annotationType, feature);
      }
      else {
        subAnnots = getPatternAnnotations(annots, annotationType, feature,
                value);
      }

      count += subAnnots.size();
    }
    return count;
  }

  
  /**
   * @see #freq(List, String, String, String, boolean, boolean)
   */
  public static int freq(List<Hit> patternsToSearchIn,
          String annotationType, boolean inMatchedSpan, boolean inContext) throws SearchException {
    if(patternsToSearchIn == null || patternsToSearchIn.isEmpty()) return 0;

    if(!inMatchedSpan && !inContext)
      throw new SearchException(
              "Both inMatchedSpan and inContext cannot be set to false");

    int count = 0;
    for(Hit aResult1 : patternsToSearchIn) {
      Pattern aResult = (Pattern) aResult1;


      List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
      if(inMatchedSpan && !inContext) {
        annots = aResult.getPatternAnnotations(aResult.getStartOffset(),
                aResult.getEndOffset());
      }
      else if(!inMatchedSpan && inContext) {
        annots = aResult.getPatternAnnotations(aResult
                .getLeftContextStartOffset(), aResult.getStartOffset());
        annots.addAll(aResult.getPatternAnnotations(aResult.getEndOffset(),
                aResult.getRightContextEndOffset()));
      }
      else {
        // both matchedSpan and context are set to true
        annots = Arrays.asList(aResult.getPatternAnnotations());
      }

      if(annots.isEmpty()) continue;
      List<PatternAnnotation> subAnnots = getPatternAnnotations(annots, annotationType);

      count += subAnnots.size();
    }
    return count;
  }
  
  
  /**
   * Calculates frequencies for all possible values of the provided AT.feature
   * @param patternsToSearchIn
   * @param annotationType
   * @param feature
   * @param inMatchedSpan
   * @param inContext
   * @return returns a map where key is the unique value of AT.feature and value is the Integer object giving count for the value.
   * @throws SearchException
   */
  public static Map<String, Integer> freqForAllValues(
          List<Hit> patternsToSearchIn, String annotationType,
          String feature, boolean inMatchedSpan, boolean inContext)
          throws SearchException {
    Map<String, Integer> toReturn = new HashMap<String, Integer>();
    if(patternsToSearchIn == null || patternsToSearchIn.isEmpty())
      return toReturn;

    
    if(!inMatchedSpan && !inContext)
      throw new SearchException(
              "Both inMatchedSpan and inContext cannot be set to false");

    for(Hit aResult1 : patternsToSearchIn) {
      Pattern aResult = (Pattern) aResult1;


      List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
      if(inMatchedSpan && !inContext) {
        annots = aResult.getPatternAnnotations(aResult.getStartOffset(),
                aResult.getEndOffset());
      }
      else if(!inMatchedSpan && inContext) {
        annots = aResult.getPatternAnnotations(aResult
                .getLeftContextStartOffset(), aResult.getStartOffset());
        annots.addAll(aResult.getPatternAnnotations(aResult.getEndOffset(),
                aResult.getRightContextEndOffset()));
      }
      else {
        // both matchedSpan and context are set to true
        annots = Arrays.asList(aResult.getPatternAnnotations());
      }

      if(annots.isEmpty()) continue;
      List<PatternAnnotation> subAnnots = getPatternAnnotations(annots,
              annotationType, feature);

      for(PatternAnnotation pa : subAnnots) {
        String uniqueKey = pa.getFeatures().get(feature);
        Integer counter = toReturn.get(uniqueKey);
        if(counter == null) {
          counter = 1;
          toReturn.put(uniqueKey, counter);
        }
        else {
          counter = counter.intValue() + 1;
          toReturn.put(uniqueKey, counter);
        }
      }
    }
    return toReturn;
  }

  private static List<PatternAnnotation> getPatternAnnotations(
          List<PatternAnnotation> annotations, String type, String feature,
          String value) {
    List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
    for(int i = 0; i < annotations.size(); i++) {
      PatternAnnotation ga1 = annotations.get(i);
      if(ga1.getType().equals(type)) {
        Map<String, String> features = ga1.getFeatures();
        if(features != null && features.keySet().contains(feature)) {
          if(features.get(feature).equals(value)) annots.add(ga1);
        }
      }
    }
    return annots;
  }

  private static List<PatternAnnotation> getPatternAnnotations(
          List<PatternAnnotation> annotations, String type, String feature) {
    List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
    for(int i = 0; i < annotations.size(); i++) {
      PatternAnnotation ga1 = annotations.get(i);
      if(ga1.getType().equals(type)) {
        Map<String, String> features = ga1.getFeatures();
        if(features != null && features.keySet().contains(feature)) {
          annots.add(ga1);
        }
      }
    }
    return annots;
  }

  private static List<PatternAnnotation> getPatternAnnotations(
          List<PatternAnnotation> annotations, String type) {
    List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
    for(int i = 0; i < annotations.size(); i++) {
      PatternAnnotation ga1 = annotations.get(i);
      if(ga1.getType().equals(type)) {
         annots.add(ga1);
      }
    }
    return annots;
  }
  
  
}
