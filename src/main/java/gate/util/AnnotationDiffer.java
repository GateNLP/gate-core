/*
 *  Copyright (c) 1995-2011, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 28/01/2003
 *
 *  $Id: AnnotationDiffer.java 19660 2016-10-10 07:57:55Z markagreenwood $
 *
 */
package gate.util;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

import gate.Annotation;

/**
 * This class provides the logic used by the Annotation Diff tool. It starts 
 * with two collections of annotation objects, one of key annotations
 * (representing the gold standard) and one of response annotations 
 * (representing the system's responses). It will then pair the keys and 
 * responses in a way that maximises the score. Each key - response pair gets a 
 * score of {@link #CORRECT_VALUE} (2), {@link #PARTIALLY_CORRECT_VALUE} (1) or 
 * {@link #WRONG_VALUE} (0)depending on whether the two annotations match are 
 * overlapping or completely unmatched. Each pairing also has a type of 
 * {@link #CORRECT_TYPE}, {@link #PARTIALLY_CORRECT_TYPE}, 
 * {@link #SPURIOUS_TYPE} or {@link #MISSING_TYPE} further detailing the type of
 * error for the wrong matches (<i>missing</i> being the keys that weren't 
 * matched to a response while  <i>spurious</i> are the responses that were 
 * over-generated and are not matching any key.
 *   
 * Precision, recall and f-measure are also calculated.
 */
public class AnnotationDiffer {

  /**
   * Constructor to be used when you have a collection of AnnotationDiffer
   * and want to consider it as only one AnnotationDiffer.
   * Then you can only use the methods getPrecision/Recall/FMeasure...().
   * @param differs collection to be regrouped in one AnnotationDiffer
   */
  public AnnotationDiffer(Collection<AnnotationDiffer> differs) {
    correctMatches = 0;
    partiallyCorrectMatches = 0;
    missing = 0;
    spurious = 0;
    int keyCount = 0;
    int responseCount = 0;
    for (AnnotationDiffer differ : differs) {
      // set the correct, partial, spurious and missing values to be
      // the sum of those in the collection
      correctMatches += differ.getCorrectMatches();
      partiallyCorrectMatches += differ.getPartiallyCorrectMatches();
      missing += differ.getMissing();
      spurious += differ.getSpurious();
      keyCount += differ.getKeysCount();
      responseCount += differ.getResponsesCount();
    }
    keyList = new ArrayList<Annotation>(Collections.nCopies(keyCount, (Annotation) null));
    responseList = new ArrayList<Annotation>(Collections.nCopies(responseCount, (Annotation) null));    
  }

  public AnnotationDiffer() {
    // empty constructor
  }

  /**
   * Interface representing a pairing between a key annotation and a response 
   * one.
   */
  public static interface Pairing{
    /**
     * Gets the key annotation of the pairing. Can be <tt>null</tt> (for 
     * spurious matches).
     * @return an {@link Annotation} object.
     */
    public Annotation getKey();

    public int getResponseIndex();
    public int getValue();
    public int getKeyIndex();
    public int getScore();
    public void setType(int i);
    public void remove();
    
    /**
     * Gets the response annotation of the pairing. Can be <tt>null</tt> (for 
     * missing matches).
     * @return an {@link Annotation} object.
     */
    public Annotation getResponse();
    
    /**
     * Gets the type of the pairing, one of {@link #CORRECT_TYPE},
     * {@link #PARTIALLY_CORRECT_TYPE}, {@link #SPURIOUS_TYPE} or
     * {@link #MISSING_TYPE},
     * @return an <tt>int</tt> value.
     */
    public int getType();
  }

  /**
   * Computes a diff between two collections of annotations.
   * @param key the collection of key annotations.
   * @param response the collection of response annotations.
   * @return a list of {@link Pairing} objects representing the pairing set
   * that results in the best score.
   */
  public List<Pairing> calculateDiff(Collection<Annotation> key, Collection<Annotation> response){
    
    //initialise data structures
    if(key == null || key.size() == 0)
      keyList = new ArrayList<Annotation>();
    else
      keyList = new ArrayList<Annotation>(key);

    if(response == null || response.size() == 0)
      responseList = new ArrayList<Annotation>();
    else
      responseList = new ArrayList<Annotation>(response);

    if(correctAnnotations != null) {
      correctAnnotations.clear();
    }
    else {
      correctAnnotations = new HashSet<Annotation>();
    }
    if(partiallyCorrectAnnotations != null) {
      partiallyCorrectAnnotations.clear();
    }
    else {
      partiallyCorrectAnnotations = new HashSet<Annotation>();
    }
    if(missingAnnotations != null) {
      missingAnnotations.clear();
    }
    else {
      missingAnnotations = new HashSet<Annotation>();
    }
    if(spuriousAnnotations != null) {
      spuriousAnnotations.clear();
    }
    else {
      spuriousAnnotations = new HashSet<Annotation>();
    }
    
    keyChoices = new ArrayList<List<Pairing>>(keyList.size());
    keyChoices.addAll(Collections.nCopies(keyList.size(), (List<Pairing>) null));
    responseChoices = new ArrayList<List<Pairing>>(responseList.size());
    responseChoices.addAll(Collections.nCopies(responseList.size(), (List<Pairing>) null));

    possibleChoices = new ArrayList<Pairing>();

    //1) try all possible pairings
    for(int i = 0; i < keyList.size(); i++){
      for(int j =0; j < responseList.size(); j++){
        Annotation keyAnn = keyList.get(i);
        Annotation resAnn = responseList.get(j);
        PairingImpl choice = null;
        if(keyAnn.coextensive(resAnn)){
          //we have full overlap -> CORRECT or WRONG
          if(keyAnn.isCompatible(resAnn, significantFeaturesSet)){
            //we have a full match
            choice = new PairingImpl(i, j, CORRECT_VALUE);
          }else{
            //the two annotations are coextensive but don't match
            //we have a missmatch
            choice = new PairingImpl(i, j, MISMATCH_VALUE);
          }
        }else if(keyAnn.overlaps(resAnn)){
          //we have partial overlap -> PARTIALLY_CORRECT or WRONG
          if(keyAnn.isPartiallyCompatible(resAnn, significantFeaturesSet)){
            choice = new PairingImpl(i, j, PARTIALLY_CORRECT_VALUE);
          }else{
            choice = new PairingImpl(i, j, WRONG_VALUE);
          }
        }

        //add the new choice if any
        if (choice != null) {
          addPairing(choice, i, keyChoices);
          addPairing(choice, j, responseChoices);
          possibleChoices.add(choice);
        }
      }//for j
    }//for i

    //2) from all possible pairings, find the maximal set that also
    //maximises the total score
    Collections.sort(possibleChoices, new PairingScoreComparator());
    Collections.reverse(possibleChoices);
    finalChoices = new ArrayList<Pairing>();
    correctMatches = 0;
    partiallyCorrectMatches = 0;
    missing = 0;
    spurious = 0;

    while(!possibleChoices.isEmpty()){
      PairingImpl bestChoice = (PairingImpl)possibleChoices.remove(0);
      bestChoice.consume();
      finalChoices.add(bestChoice);
      switch(bestChoice.value){
        case CORRECT_VALUE:{
          correctAnnotations.add(bestChoice.getResponse());
          correctMatches++;
          bestChoice.setType(CORRECT_TYPE);
          break;
        }
        case PARTIALLY_CORRECT_VALUE:{
          partiallyCorrectAnnotations.add(bestChoice.getResponse());
          partiallyCorrectMatches++;
          bestChoice.setType(PARTIALLY_CORRECT_TYPE);
          break;
        }
        case MISMATCH_VALUE:{
          //this is a missing and a spurious annotations together
          missingAnnotations.add(bestChoice.getKey());
          missing ++;
          spuriousAnnotations.add(bestChoice.getResponse());
          spurious ++;
          bestChoice.setType(MISMATCH_TYPE);
          break;
        }
        case WRONG_VALUE:{
          if(bestChoice.getKey() != null){
            //we have a missed key
            if(missingAnnotations == null) missingAnnotations = new HashSet<Annotation>();
            missingAnnotations.add(bestChoice.getKey());
            missing ++;
            bestChoice.setType(MISSING_TYPE);
          }
          if(bestChoice.getResponse() != null){
            //we have a spurious response
            if(spuriousAnnotations == null) spuriousAnnotations = new HashSet<Annotation>();
            spuriousAnnotations.add(bestChoice.getResponse());
            spurious ++;
            bestChoice.setType(SPURIOUS_TYPE);
          }
          break;
        }
        default:{
          throw new GateRuntimeException("Invalid pairing type: " +
                  bestChoice.value);
        }
      }
    }
    //add choices for the incorrect matches (MISSED, SPURIOUS)
    //get the unmatched keys
    for(int i = 0; i < keyChoices.size(); i++){
      List<Pairing> aList = keyChoices.get(i);
      if(aList == null || aList.isEmpty()){
        if(missingAnnotations == null) missingAnnotations = new HashSet<Annotation>();
        missingAnnotations.add((keyList.get(i)));
        Pairing choice = new PairingImpl(i, -1, WRONG_VALUE);
        choice.setType(MISSING_TYPE);
        finalChoices.add(choice);
        missing ++;
      }
    }

    //get the unmatched responses
    for(int i = 0; i < responseChoices.size(); i++){
      List<Pairing> aList = responseChoices.get(i);
      if(aList == null || aList.isEmpty()){
        if(spuriousAnnotations == null) spuriousAnnotations = new HashSet<Annotation>();
        spuriousAnnotations.add((responseList.get(i)));
        PairingImpl choice = new PairingImpl(-1, i, WRONG_VALUE);
        choice.setType(SPURIOUS_TYPE);
        finalChoices.add(choice);
        spurious ++;
      }
    }

    return finalChoices;
  }

  /**
   * Gets the strict precision (the ratio of correct responses out of all the 
   * provided responses).
   * @return a <tt>double</tt> value.
   */
  public double getPrecisionStrict(){
    if(responseList.size() == 0) {
      return 1.0;
    }
    return correctMatches/(double)responseList.size();
  }

  /**
   * Gets the strict recall (the ratio of key matched to a response out of all 
   * the keys).
   * @return a <tt>double</tt> value.
   */  
  public double getRecallStrict(){
    if(keyList.size() == 0) {
      return 1.0;
    }
    return correctMatches/(double)keyList.size();
  }

  /**
   * Gets the lenient precision (where the partial matches are considered as
   * correct).
   * @return a <tt>double</tt> value.
   */
  public double getPrecisionLenient(){
    if(responseList.size() == 0) {
      return 1.0;
    }
    return ((double)correctMatches + partiallyCorrectMatches) / responseList.size();
  }

  /**
   * Gets the average of the strict and lenient precision values.
   * @return a <tt>double</tt> value.
   */
  public double getPrecisionAverage() {
    return (getPrecisionLenient() + getPrecisionStrict()) / (2.0);
  }

  /**
   * Gets the lenient recall (where the partial matches are considered as
   * correct).
   * @return a <tt>double</tt> value.
   */
  public double getRecallLenient(){
    if(keyList.size() == 0) {
      return 1.0;
    }
    return ((double)correctMatches + partiallyCorrectMatches) / keyList.size();
  }

  /**
   * Gets the average of the strict and lenient recall values.
   * @return a <tt>double</tt> value.
   */
  public double getRecallAverage() {
    return (getRecallLenient() + getRecallStrict()) / (2.0);
  }

  /**
   * Gets the strict F-Measure (the harmonic weighted mean of the strict
   * precision and the strict recall) using the provided parameter as relative 
   * weight. 
   * @param beta The relative weight of precision and recall. A value of 1 
   * gives equal weights to precision and recall. A value of 0 takes the recall 
   * value completely out of the equation.
   * @return a <tt>double</tt>value.
   */
  public double getFMeasureStrict(double beta){
    double precision = getPrecisionStrict();
    double recall = getRecallStrict();
    double betaSq = beta * beta;
    double answer = (((betaSq + 1) * precision * recall ) / (betaSq * precision + recall));
    if(Double.isNaN(answer)) answer = 0.0;
    return answer;
  }

  /**
   * Gets the lenient F-Measure (F-Measure where the lenient precision and 
   * recall values are used) using the provided parameter as relative weight. 
   * @param beta The relative weight of precision and recall. A value of 1 
   * gives equal weights to precision and recall. A value of 0 takes the recall 
   * value completely out of the equation.
   * @return a <tt>double</tt>value.
   */
  public double getFMeasureLenient(double beta){
    double precision = getPrecisionLenient();
    double recall = getRecallLenient();
    double betaSq = beta * beta;
    double answer = (((betaSq + 1) * precision * recall) / (betaSq * precision + recall));
    if(Double.isNaN(answer)) answer = 0.0;
    return answer;
  }

  /**
   * Gets the average of strict and lenient F-Measure values.
   * @param beta The relative weight of precision and recall. A value of 1 
   * gives equal weights to precision and recall. A value of 0 takes the recall 
   * value completely out of the equation.
   * @return a <tt>double</tt>value.
   */  
  public double getFMeasureAverage(double beta) {
    double answer = (getFMeasureLenient(beta) + getFMeasureStrict(beta)) / (2.0);
    return answer;
  }

  /**
   * Gets the number of correct matches.
   * @return an <tt>int</tt> value.
   */
  public int getCorrectMatches(){
    return correctMatches;
  }

  /**
   * Gets the number of partially correct matches.
   * @return an <tt>int</tt> value.
   */
  public int getPartiallyCorrectMatches(){
    return partiallyCorrectMatches;
  }

  /**
   * Gets the number of pairings of type {@link #MISSING_TYPE}.
   * @return an <tt>int</tt> value.
   */
  public int getMissing(){
    return missing;
  }

  /**
   * Gets the number of pairings of type {@link #SPURIOUS_TYPE}.
   * @return an <tt>int</tt> value.
   */
  public int getSpurious(){
    return spurious;
  }

  /**
   * Gets the number of pairings of type {@link #SPURIOUS_TYPE}.
   * @return an <tt>int</tt> value.
   */
  public int getFalsePositivesStrict(){
    return responseList.size() - correctMatches;
  }

  /**
   * Gets the number of responses that aren't either correct or partially 
   * correct.
   * @return an <tt>int</tt> value.
   */
  public int getFalsePositivesLenient(){
    return responseList.size() - correctMatches - partiallyCorrectMatches;
  }

  /**
   * Gets the number of keys provided.
   * @return an <tt>int</tt> value.
   */
  public int getKeysCount() {
    return keyList.size();
  }

  /**
   * Gets the number of responses provided.
   * @return an <tt>int</tt> value.
   */
  public int getResponsesCount() {
    return responseList.size();
  }

  /**
   * Prints to System.out the pairings that are not correct.
   */
  public void printMissmatches(){
    //get the partial correct matches
    for (Pairing aChoice : finalChoices) {
      switch(aChoice.getValue()){
        case PARTIALLY_CORRECT_VALUE:{
          System.out.println("Missmatch (partially correct):");
          System.out.println("Key: " + keyList.get(aChoice.getKeyIndex()).toString());
          System.out.println("Response: " + responseList.get(aChoice.getResponseIndex()).toString());
          break;
        }
      }
    }

    //get the unmatched keys
    for(int i = 0; i < keyChoices.size(); i++){
      List<Pairing> aList = keyChoices.get(i);
      if(aList == null || aList.isEmpty()){
        System.out.println("Missed Key: " + keyList.get(i).toString());
      }
    }

    //get the unmatched responses
    for(int i = 0; i < responseChoices.size(); i++){
      List<Pairing> aList = responseChoices.get(i);
      if(aList == null || aList.isEmpty()){
        System.out.println("Spurious Response: " + responseList.get(i).toString());
      }
    }
  }



  /**
   * Performs some basic checks over the internal data structures from the last
   * run.
   * @throws Exception
   */
  void sanityCheck()throws Exception{
    //all keys and responses should have at most one choice left
    for (List<Pairing> choices : keyChoices)  {
      if(choices != null){
        if(choices.size() > 1){
          throw new Exception("Multiple choices found!");
        }
        else if(!choices.isEmpty()){
          //size must be 1
          Pairing aChoice = choices.get(0);
          //the SAME choice should be found for the associated response
          List<?> otherChoices = responseChoices.get(aChoice.getResponseIndex());
          if(otherChoices == null ||
             otherChoices.size() != 1 ||
             otherChoices.get(0) != aChoice){
            throw new Exception("Reciprocity error!");
          }
        }
      }
    }

    for (List<Pairing> choices : responseChoices) {
      if(choices != null){
        if(choices.size() > 1){
          throw new Exception("Multiple choices found!");
        }
        else if(!choices.isEmpty()){
          //size must be 1
          Pairing aChoice = choices.get(0);
          //the SAME choice should be found for the associated response
          List<?> otherChoices = keyChoices.get(aChoice.getKeyIndex());
          if(otherChoices == null){
            throw new Exception("Reciprocity error : null!");
          }else if(otherChoices.size() != 1){
            throw new Exception("Reciprocity error: not 1!");
          }else if(otherChoices.get(0) != aChoice){
            throw new Exception("Reciprocity error: different!");
          }
        }
      }
    }
  }
  
  /**
   * Adds a new pairing to the internal data structures.
   * @param pairing the pairing to be added
   * @param index the index in the list of pairings
   * @param listOfPairings the list of {@link Pairing}s where the
   * pairing should be added
   */
  protected void addPairing(Pairing pairing, int index, List<List<Pairing>> listOfPairings){
    List<Pairing> existingChoices = listOfPairings.get(index);
    if(existingChoices == null){
      existingChoices = new ArrayList<Pairing>();
      listOfPairings.set(index, existingChoices);
    }
    existingChoices.add(pairing);
  }

  /**
   * Gets the set of features considered significant for the matching algorithm.
   * @return a Set.
   */
  public java.util.Set<?> getSignificantFeaturesSet() {
    return significantFeaturesSet;
  }

  /**
   * Set the set of features considered significant for the matching algorithm.
   * A <tt>null</tt> value means that all features are significant, an empty 
   * set value means that no features are significant while a set of String 
   * values specifies that only features with names included in the set are 
   * significant. 
   * @param significantFeaturesSet a Set of String values or <tt>null</tt>.
   */
  public void setSignificantFeaturesSet(Set<String> significantFeaturesSet) {
    this.significantFeaturesSet = significantFeaturesSet;
  }

  /**
   * Represents a pairing of a key annotation with a response annotation and
   * the associated score for that pairing.
   */
   public class PairingImpl implements Pairing{
    PairingImpl(int keyIndex, int responseIndex, int value) {
      this.keyIndex = keyIndex;
      this.responseIndex = responseIndex;
      this.value = value;
      scoreCalculated = false;
	    }

    @Override
    public int getScore(){
      if(scoreCalculated) return score;
      else{
        calculateScore();
        return score;
      }
    }

    @Override
    public int getKeyIndex() {
      return this.keyIndex;
    }
    
    @Override
    public int getResponseIndex() {
      return this.responseIndex;
    }
    
    @Override
    public int getValue() {
      return this.value;
    }
    
    @Override
    public Annotation getKey(){
      return keyIndex == -1 ? null : keyList.get(keyIndex);
    }

    @Override
    public Annotation getResponse(){
      return responseIndex == -1 ? null :
        responseList.get(responseIndex);
    }

    @Override
    public int getType(){
      return type;
    }
    

    @Override
    public void setType(int type) {
      this.type = type;
    }

    /**
     * Removes all mutually exclusive OTHER choices possible from
     * the data structures.
     * <tt>this</tt> gets removed from {@link #possibleChoices} as well.
     */
    public void consume(){
      possibleChoices.remove(this);
      List<Pairing> sameKeyChoices = keyChoices.get(keyIndex);
      sameKeyChoices.remove(this);
      possibleChoices.removeAll(sameKeyChoices);

      List<Pairing> sameResponseChoices = responseChoices.get(responseIndex);
      sameResponseChoices.remove(this);
      possibleChoices.removeAll(sameResponseChoices);

      for (Pairing item : new ArrayList<Pairing>(sameKeyChoices)) {
        item.remove();
      }
      for (Pairing item : new ArrayList<Pairing>(sameResponseChoices)) {
        item.remove();
      }
      sameKeyChoices.add(this);
      sameResponseChoices.add(this);
    }

    /**
     * Removes this choice from the two lists it belongs to
     */
    @Override
    public void remove(){
      List<Pairing> fromKey = keyChoices.get(keyIndex);
      fromKey.remove(this);
      List<Pairing> fromResponse = responseChoices.get(responseIndex);
      fromResponse.remove(this);
    }

    /**
     * Calculates the score for this choice as:
     * type - sum of all the types of all OTHER mutually exclusive choices
     */
    void calculateScore(){
      //this needs to be a set so we don't count conflicts twice
      Set<Pairing> conflictSet = new HashSet<Pairing>();
      //add all the choices from the same response annotation
      conflictSet.addAll(responseChoices.get(responseIndex));
      //add all the choices from the same key annotation
      conflictSet.addAll(keyChoices.get(keyIndex));
      //remove this choice from the conflict set
      conflictSet.remove(this);
      score = value;
      for (Pairing item : conflictSet) {
        score -= item.getValue();
      }
      scoreCalculated = true;
    }

    /**
     * The index in the key collection of the key annotation for this pairing
     */
    int keyIndex;
    /**
     * The index in the response collection of the response annotation for this
     * pairing
     */
    int responseIndex;
    
    /**
     * The type of this pairing.
     */
    int type;
    
    /**
     * The value for this pairing. This value depends only on this pairing, not
     * on the conflict set.
     */
    int value;
    
    /**
     * The score of this pairing (calculated based on value and conflict set).
     */
    int score;
    boolean scoreCalculated;
  }

   /**
    * Compares two pairings:
    * the better score is preferred;
    * for the same score the better type is preferred (exact matches are
    * preffered to partial ones).
    */   
	protected static class PairingScoreComparator implements Comparator<Pairing>, Serializable {

    private static final long serialVersionUID = 7386841422038756873L;

    /**
     * Compares two choices:
     * the better score is preferred;
     * for the same score the better type is preferred (exact matches are
     * preffered to partial ones).
     * @return a positive value if the first pairing is better than the second,
     * zero if they score the same or negative otherwise.
     */

	  @Override
    public int compare(Pairing first, Pairing second){
      //compare by score
      int res = first.getScore() - second.getScore();
      //compare by type
      if(res == 0) res = first.getType() - second.getType();
      //compare by completeness (a wrong match with both key and response
      //is "better" than one with only key or response
      if(res == 0){
        res = (first.getKey() == null ? 0 : 1) +
              (first.getResponse() == null ? 0 : 1) +
              (second.getKey() == null ? 0 : -1) +
              (second.getResponse() == null ? 0 : -1);
      }
      return res;
	  }
	}

    /**
     * Compares two choices based on start offset of key (or response
     * if key not present) and type if offsets are equal.
     */
	public static class PairingOffsetComparator implements Comparator<Pairing>, Serializable {
 
      private static final long serialVersionUID = 1L;

    /**
     * Compares two choices based on start offset of key (or response
     * if key not present) and type if offsets are equal.
     */
	  @Override
    public int compare(Pairing first, Pairing second){
	    Annotation key1 = first.getKey();
	    Annotation key2 = second.getKey();
	    Annotation res1 = first.getResponse();
	    Annotation res2 = second.getResponse();
	    Long start1 = key1 == null ? null : key1.getStartNode().getOffset();
	    if(start1 == null) start1 = res1.getStartNode().getOffset();
	    Long start2 = key2 == null ? null : key2.getStartNode().getOffset();
	    if(start2 == null) start2 = res2.getStartNode().getOffset();
	    int res = start1.compareTo(start2);
	    if(res == 0){
	      //compare by type
	      res = second.getType() - first.getType();
	    }

//
//
//
//	    //choices with keys are smaller than ones without
//	    if(key1 == null && key2 != null) return 1;
//	    if(key1 != null && key2 == null) return -1;
//	    if(key1 == null){
//	      //both keys are null
//	      res = res1.getStartNode().getOffset().
//	      		compareTo(res2.getStartNode().getOffset());
//	      if(res == 0) res = res1.getEndNode().getOffset().
//      				compareTo(res2.getEndNode().getOffset());
//	      if(res == 0) res = second.getType() - first.getType();
//	    }else{
//	      //both keys are present
//	      res = key1.getStartNode().getOffset().compareTo(
//	          key2.getStartNode().getOffset());
//
//	      if(res == 0){
//		      //choices with responses are smaller than ones without
//		      if(res1 == null && res2 != null) return 1;
//		      if(res1 != null && res2 == null) return -1;
//		      if(res1 != null){
//			      res = res1.getStartNode().getOffset().
//    						compareTo(res2.getStartNode().getOffset());
//		      }
//		      if(res == 0)res = key1.getEndNode().getOffset().compareTo(
//		              key2.getEndNode().getOffset());
//		      if(res == 0 && res1 != null){
//				      res = res1.getEndNode().getOffset().
//	    						compareTo(res2.getEndNode().getOffset());
//		      }
//		      if(res == 0) res = second.getType() - first.getType();
//	      }
//	    }
      return res;
	  }

	}

  /**
   * A method that returns specific type of annotations
   * @param type
   * @return a {@link Set} of {@link Annotation}s.
   */
  public Set<Annotation> getAnnotationsOfType(int type) {
    switch(type) {
      case CORRECT_TYPE:
        return (correctAnnotations == null)? new HashSet<Annotation>() : correctAnnotations;
      case PARTIALLY_CORRECT_TYPE:
        return (partiallyCorrectAnnotations == null) ? new HashSet<Annotation>() : partiallyCorrectAnnotations;
      case SPURIOUS_TYPE:
        return (spuriousAnnotations == null) ? new HashSet<Annotation>() : spuriousAnnotations;
      case MISSING_TYPE:
        return (missingAnnotations == null) ? new HashSet<Annotation>() : missingAnnotations;
      default:
        return new HashSet<Annotation>();
    }
  }

  /**
   * @return annotation type for all the annotations
   */
  public String getAnnotationType() {
    if (!keyList.isEmpty()) {
      return keyList.iterator().next().getType();
    } else if (!responseList.isEmpty()) {
      return responseList.iterator().next().getType();
    } else {
      return "";
    }
  }

  public List<String> getMeasuresRow(Object[] measures, String title) {
    NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
    f.setMaximumFractionDigits(4);
    f.setMinimumFractionDigits(4);
    f.setRoundingMode(RoundingMode.HALF_UP);
    
    List<String> row = new ArrayList<String>();
    row.add(title);
    row.add(Integer.toString(getCorrectMatches()));
    row.add(Integer.toString(getMissing()));
    row.add(Integer.toString(getSpurious()));
    row.add(Integer.toString(getPartiallyCorrectMatches()));
    for (Object object : measures) {
      String measure = (String) object;
      double beta = Double.valueOf(
        measure.substring(1,measure.indexOf('-')));
      if (measure.endsWith("strict")) {
        row.add(f.format(getPrecisionStrict()));
        row.add(f.format(getRecallStrict()));
        row.add(f.format(getFMeasureStrict(beta)));
      } else if (measure.endsWith("lenient")) {
        row.add(f.format(getPrecisionLenient()));
        row.add(f.format(getRecallLenient()));
        row.add(f.format(getFMeasureLenient(beta)));
      } else if (measure.endsWith("average")) {
        row.add(f.format(getPrecisionAverage()));
        row.add(f.format(getRecallAverage()));
        row.add(f.format(getFMeasureAverage(beta)));
      }
    }
    return row;
  }

  public Set<Annotation> correctAnnotations, partiallyCorrectAnnotations,
                 missingAnnotations, spuriousAnnotations;


  /** Type for correct pairings (when the key and response match completely)*/
  public static final int CORRECT_TYPE = 0;
  
  /** 
   * Type for partially correct pairings (when the key and response match 
   * in type and significant features but the spans are just overlapping and 
   * not identical.
   */
  public static final int PARTIALLY_CORRECT_TYPE = 1;
  
  /**
   * Type for missing pairings (where the key was not matched to a response).
   */
  public static final int MISSING_TYPE = 2;

  /**
   * Type for spurious pairings (where the response is not matching any key).
   */
  public static final int SPURIOUS_TYPE = 3;
  
  /**
   * Type for mismatched pairings (where the key and response are co-extensive
   * but they don't match).
   */
  public static final int MISMATCH_TYPE = 4;

  /**
   * Score for a correct pairing.
   */
  private static final int CORRECT_VALUE = 3;

  /**
   * Score for a partially correct pairing.
   */
  private static final int PARTIALLY_CORRECT_VALUE = 2;
  
  
  /**
   * Score for a mismatched pairing (higher then for WRONG as at least the 
   * offsets were right).
   */
  private static final int MISMATCH_VALUE = 1;
  
  /**
   * Score for a wrong (missing or spurious) pairing.
   */  
  private static final int WRONG_VALUE = 0;

  /**
   * The set of significant features used for matching.
   */
  private java.util.Set<?> significantFeaturesSet;

  /**
   * The number of correct matches.
   */
  protected int correctMatches;

  /**
   * The number of partially correct matches.
   */
  protected int partiallyCorrectMatches;
  
  /**
   * The number of missing matches.
   */
  protected int missing;
  
  /**
   * The number of spurious matches.
   */  
  protected int spurious;

  /**
   * A list with all the key annotations
   */
  protected List<Annotation> keyList;

  /**
   * A list with all the response annotations
   */
  protected List<Annotation> responseList;

  /**
   * A list of lists representing all possible choices for each key
   */
  protected List<List<Pairing>> keyChoices;

  /**
   * A list of lists representing all possible choices for each response
   */
  protected List<List<Pairing>> responseChoices;

  /**
   * All the posible choices are added to this list for easy iteration.
   */
  protected List<Pairing> possibleChoices;

  /**
   * A list with the choices selected for the best result.
   */
  protected List<Pairing> finalChoices;

}
