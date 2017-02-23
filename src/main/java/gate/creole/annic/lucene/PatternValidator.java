/*
 *  PatternValidator.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: PatternValidator.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.util.*;
import gate.creole.annic.apache.lucene.index.*;
import gate.creole.annic.apache.lucene.analysis.*;

/**
 * Pattern Validator that given a position of first term, retrieves the
 * entire pattern from the token stream. If it is not able to retrieve
 * the entire pattern, the class reports it as an invalid pattern.
 * 
 * @author niraj
 * 
 */
public class PatternValidator {

  private int patLen = 0;

  /**
   * Gets the length of the pattern.
   */
  public int getPatternLength() {
    return patLen;
  }

  /**
   * This method takes two parameters the actual query issued and
   * annotations in which it checks if the annotations exist that are
   * validating for the given query
   * 
   * @return int positive number indicates the offset of the last
   *         annotation of the pattern. -1 indicates invalid pattern.
   */
  public int validate(List<String> queryTokens, List<Token> annotations, int from,
          QueryParser queryParser) throws gate.creole.ir.SearchException {
    patLen = 0;

    // and now for each token we need to create Term(s)
    int enOffset = -1;
    int stOffset = -1;
    int position = -1;

    for(int i = 0; i < queryTokens.size(); i++) {
      queryParser.position = 0;
      List<?>[] termpositions = queryParser.createTerms(queryTokens.get(i));
      
      @SuppressWarnings("unchecked")
      List<Term> terms = (List<Term>)termpositions[0];
      
      @SuppressWarnings("unchecked")
      List<Boolean> consider = (List<Boolean>)termpositions[2];
      
      // process each term individually
      for(int k = 0; k < terms.size(); k++) {
        // when consider is true, that means we should change the start
        // offset conditions
        Term term = terms.get(k);
        if(consider.get(k).booleanValue()) {
          patLen++;
          // find relavant annotations where type and text should
          // match with terms type and text
          boolean found = false;
          // among this if we are able to find the token that has
          // start offset > previous enOffset
          innerLoop: for(int j = from; j < annotations.size(); j++) {
            Token tk = annotations.get(j);
            // if the term is equal to one of the tokens
            if(!isEqual(tk, term)) continue;
            // the next token with consider must be starting with
            // the end of last token
            // or after 1 space
            if(enOffset == -1 || tk.startOffset() == enOffset
                    || tk.startOffset() == enOffset + 1) {
              // the position of the new token must be +1
              if(tk.getPosition() > position) {
                found = true;
                // set the current position to the position of
                // the found token
                position = tk.getPosition();
                enOffset = tk.endOffset();
                stOffset = tk.startOffset();
                // as the annotation is found
                // break the innerLoop
                // and search for the next term
                break innerLoop;
              }
            }
          }

          if(!found) {
            // we could not find any annotation that means this
            // pattern is not valid
            return -1;
          }

        }
        else {
          // if consider is false
          boolean found = false;
          for(int j = 0; j < annotations.size(); j++) {
            Token tk = annotations.get(j);
            if(tk.getPosition() != position) continue;
            if(tk.endOffset() != enOffset || tk.startOffset() != stOffset)
              continue;
            if(!isEqual(tk, term))
              continue;
            else {
              found = true;
              break;
            }
          }
          if(!found) {
            return -1;
          }
        }
      }
    }
    return enOffset;
  }

  /**
   * Checks whether two terms are equal.
   */
  private boolean isEqual(Token tk, Term term) {
    return (term.text().equals(tk.termText()) && term.type().equals(tk.type()));
  }
}
