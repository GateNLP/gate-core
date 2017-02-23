/*
 *  LuceneQueryResult.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneQueryResult.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.creole.annic.PatternAnnotation;

import java.util.List;

/**
 * This class
 * 
 * @author niraj
 */
public class LuceneQueryResult {

  /** Persistance document ID. */
  private Object docID;
  
  private String annotationSetName;

  private List<?> firstTermPositions;

  private List<Integer> patternLength;

  private int queryType;

  private List<List<PatternAnnotation>> gateAnnotations;

  private String query;

  /**
   * Constructor
   * 
   * @param docID - ID of the document
   * @param firstTermPositions - Position of the first terms
   * @param patternLength
   * @param queryType
   * @param gateAnnotations
   * @param query
   */
  public LuceneQueryResult(Object docID, String annotationSetName, List<?> firstTermPositions,
          List<Integer> patternLength, int queryType, List<List<PatternAnnotation>> gateAnnotations,
          String query) {
    this.docID = docID;
    this.annotationSetName = annotationSetName;
    this.firstTermPositions = firstTermPositions;
    this.patternLength = patternLength;
    this.queryType = queryType;
    this.gateAnnotations = gateAnnotations;
    this.query = query;
  }

  /**
   * @return the type of query used for this result 1 - Phrase Query 0 -
   *         Term Query For the termQueries firstTermPositions instead
   *         of holding positions, has an ArrayList with two values Term
   *         Text (1st value = anntotation Text) and Term Type (2nd
   *         Value = Annotation Type)
   */
  public int getQueryType() {
    return queryType;
  }

  /** @return persistance document ID. */
  public Object getDocumentID() {
    return docID;
  }

  /**
   * @return if the query type is 0, firstTermPositions, instead of
   *         holding positions, contain the string values. element at
   *         position 0: Term Text (annotation text), element at
   *         position 1: Term Type (annotation type), position 2:
   *         annotation text, position 3: annotation type and so on. If
   *         the query type is anything other than 0, it contains
   *         Integer values indicating positions of first annotations of
   *         found patterns in the token stream.
   */
  public List<?> getFirstTermPositions() {
    return firstTermPositions;
  }

  /**
   * Returns an arraylist which for each pattern contains a number of
   * annotation in it.
   */
  public List<Integer> patternLength() {
    return patternLength;
  }

  /**
   * Gets the GateAnnotations for each pattern.
   */
  public List<List<PatternAnnotation>> getGateAnnotations() {
    return this.gateAnnotations;
  }

  /**
   * Returns the main query.
   */
  public String getQuery() {
    return this.query;
  }

  /**
   * Gets the annotation set Name for this result
   */
  public String getAnnotationSetName() {
    return annotationSetName;
  }
}
