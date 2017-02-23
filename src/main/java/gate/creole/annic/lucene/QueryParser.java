/*
 *  QueryParser.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: QueryParser.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.util.ArrayList;
import java.util.List;

import gate.creole.annic.Constants;
import gate.creole.annic.apache.lucene.index.Term;
import gate.creole.annic.apache.lucene.search.BooleanQuery;
import gate.creole.annic.apache.lucene.search.PhraseQuery;
import gate.creole.annic.apache.lucene.search.Query;
import gate.creole.annic.apache.lucene.search.TermQuery;
import gate.creole.ir.SearchException;

/**
 * QueryParser parses the provided ANNIC Query and converts it into the
 * format understood to Lucene.
 * 
 * @author niraj
 * 
 */
public class QueryParser {

  /**
   * Queries generated as a result of normalizing the submitted query.
   */
  private List<String> queries = new ArrayList<String>();

  /**
   * Name of the field that contains the index data.
   */
  private String field = "";

  /**
   * Base token annotation type.
   */
  private String baseTokenAnnotationType = "Token";

  /**
   * Indicates if we need to valid results returned by lucene.
   */
  private boolean needValidation = true;

  /**
   * Constructor
   */
  public QueryParser() {
    position = 0;
  }

  public static void main(String[] args) {
    System.out.println(isValidQuery(args[0]));
  }

  /**
   * Returns true if the submitted query is valid.
   */
  public static boolean isValidQuery(String query) {
    QueryParser qp = new QueryParser();
    try {
      qp.parse("contents", query, "Token", null, null);
    }
    catch(SearchException se) {
      return false;
    }
    return true;
  }

  /**
   * Given a query, this method parses it to convert it into one or more
   * lucene queries.
   * @throws gate.creole.ir.SearchException
   */
  public Query[] parse(String field, String query,
          String baseTokenAnnotationType, String corpusID, String annotationSetToSearchIn)
          throws gate.creole.ir.SearchException {
    this.field = field;
    this.baseTokenAnnotationType = baseTokenAnnotationType;
    this.position = 0;
    // at the moment this supports only | operator
    // it also support klene operators * and +
    // implicit operator is &
    // It supports simple String queries
    // it supports eight kinds of tokens
    // 1. String (without quotes)
    // 2. "String" (with quotes)
    // 3. {AnnotationType}
    // 4. {AnnotationType==String}
    // 5. {AnnotationType=="String"}
    // 7. {AnnotationType.feature==string}
    // 8. {AnnotationType.feature=="string"}

    // Steps
    // The query would we searched from left to right order

    // returned arraylist contains queries where each query is required
    // to
    // be converted into the Phrase query
    queries = SubQueryParser.parseQuery(query);
    Query[] q = new Query[queries.size()];
    for(int i = 0; i < queries.size(); i++) {
      Query phraseQuery = createPhraseQuery(queries.get(i));
      // if the corpusID is not provided we donot want to create a
      // boolean query
      if(corpusID == null && annotationSetToSearchIn == null) {
        BooleanQuery booleanQuery = new BooleanQuery();
        Term t = new Term(Constants.ANNOTATION_SET_ID, Constants.COMBINED_SET);
        TermQuery tQuery = new TermQuery(t);
        booleanQuery.add(tQuery, false, true);
        booleanQuery.add(phraseQuery, true, false);
        q[i] = booleanQuery;
      }
      else {
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(phraseQuery, true, false);
        if(corpusID != null) {
          Term t = new Term(Constants.CORPUS_ID, corpusID);
          TermQuery tQuery = new TermQuery(t);
          booleanQuery.add(tQuery, true, false);
        }
        
        if(annotationSetToSearchIn != null) {
          Term t = new Term(Constants.ANNOTATION_SET_ID, annotationSetToSearchIn);
          TermQuery tQuery = new TermQuery(t);
          booleanQuery.add(tQuery, true, false);
        } else {
          Term t = new Term(Constants.ANNOTATION_SET_ID, Constants.COMBINED_SET);
          TermQuery tQuery = new TermQuery(t);
          booleanQuery.add(tQuery, false, true);
        }
        

        q[i] = booleanQuery;
      }
    }
    return q;
  }

  /**
   * When user submits an ANNIC query, one or more instances of lucene
   * queries are created and returned. This method returns the string
   * representation of the query at the given index.
   */
  public String getQueryString(int i) {
    return queries.get(i);
  }

  /**
   * This method will create each normalized query into a Phrase or Term
   * query If the query has only one term to search, it will be returned
   * as a TermQuery otherwise, it will be returned as the PhraseQuery
   */
  private Query createPhraseQuery(String query)
          throws gate.creole.ir.SearchException {
    // Here we play the actual trick with lucene
    // For a query like {Lookup}{Token}{Person.gender=="male"}
    // internally this query is converted into the following PhraseQuery
    // (Lookup Token Person male)
    // these are the four terms which will be searched and they should
    // occur
    // in this order only
    // but what we need is
    // a pattern where
    // Lookup -> the first annotation is of type Lookup
    // Token -> the second annotation type is Token
    // Person male -> and the third annotation must have a type person
    // and a
    // feature gender with male
    // that means Person and male should be considered at the same
    // location
    // By default lucene doesn't do this and look for a position that is
    // 1
    // step more than the previous one
    // so it will search for the first position of Lookup
    // let say it is 19 (i.e. 19th annotation in the document)
    // then it would consider 20th location for Token
    // 21st for Person
    // 22nd for male
    // but we need, 19th for Lookup, 20th for Token and 21st for both
    // Person
    // and Male
    // so from here itself we send our choice for the Location of
    // annotations in this termPositions array :-).
    // isn't it a great crack?
    position = 0;

    PhraseQuery phQuery = new PhraseQuery();
    // we will tokenize this query to convert it into different tokens
    // query is like {Person}"said" "Hello" {Person.gender=="male"}
    // we need to convert this into different tokens
    // {Person}
    // "said"
    // "Hello"
    // {Person.gender=="male"}
    List<String> tokens = findTokens(query);

    // and then convert each token into separate terms
    if(tokens.size() == 1) {
      List<?>[] termsPos = createTerms(tokens.get(0));
      
      @SuppressWarnings("unchecked")
      List<Term> terms = (List<Term>)termsPos[0];
      
      if(terms.size() == 1) {
        if(areAllTermsTokens)
          needValidation = false;
        else needValidation = true;
        return new TermQuery(terms.get(0));
      }
      else {
        position = 0;
      }
    }

    int totalTerms = 0;
    boolean hadPreviousTermsAToken = true;

    needValidation = false;

    // and now for each token we need to create Term(s)
    outer: for(int i = 0; i < tokens.size(); i++) {
      List<?>[] termpositions = createTerms(tokens.get(i));
      
      @SuppressWarnings("unchecked")
      List<Term> terms = (List<Term>)termpositions[0];
      
      @SuppressWarnings("unchecked")
      List<Integer> pos = (List<Integer>)termpositions[1];
      
      @SuppressWarnings("unchecked")
      List<Boolean> consider = (List<Boolean>)termpositions[2];

      boolean allTermsTokens = true;
      // lets first find out if there's any token in this terms
      for(int k = 0; k < terms.size(); k++) {
        Term t = terms.get(k);

        if(allTermsTokens) allTermsTokens = isBaseTokenTerm(t);
      }

      if(!hadPreviousTermsAToken) {
        needValidation = true;
        break;
      }

      if(!allTermsTokens) {
        // we want to break here
        needValidation = true;
        if(i > 0)
          break outer;
      }

      for(int k = 0; k < terms.size(); k++) {
        Term t = terms.get(k);
        boolean considerValue = consider.get(k).booleanValue();
        phQuery.add(t, pos.get(k), considerValue);
        if(considerValue) totalTerms++;
      }

      hadPreviousTermsAToken = allTermsTokens;
    }
    phQuery.setTotalTerms(totalTerms);
    return phQuery;
  }

  /**
   * Returns true if the provided Term is a based token term. To be a
   * base token term it has to satisify the following terms: 1. If its
   * text is baseTokenAnnotationType and the type is "*" or 2. If its
   * type = "baseTokenAnnotationType.feature"
   */
  private boolean isBaseTokenTerm(Term t) {
    // the term refers to the base token
    // only if it satisfies the following conditions
    // 1. If its text is baseTokenAnnotationType and the type is "*"
    // or 2. If its type = "baseTokenAnnotationType.feature"

    // condition 1
    if(t.text().equals(baseTokenAnnotationType) && t.type().equals("*"))
      return true;

    // condition 2
    if(t.type().startsWith(baseTokenAnnotationType + ".")) return true;

    return false;
  }

  public int position = 0;

  /**
   * Given a query this method returns tokens. Here token is an object
   * of string.
   * @throws gate.creole.ir.SearchException
   */
  public List<String> findTokens(String query)
          throws gate.creole.ir.SearchException {
    List<String> tokens = new ArrayList<String>();
    String token = "";
    char ch = ' ';
    char prev = ' ';
    int balance = 0;
    for(int i = 0; i < query.length(); i++) {
      prev = ch;
      ch = query.charAt(i);
      if(isOpeneningBrace(ch, prev)) {
        if(balance != 0) {
          throw new SearchException("unbalanced braces",
            "a closing brace (}) is missing before this opening brace", query, i);
        }

        if(!token.trim().equals("")) {
          tokens.add(token.trim());
        }

        balance++;
        token = "{";
        continue;
      }

      if(isClosingBrace(ch, prev)) {
        balance--;
        if(balance != 0) {
          throw new SearchException("unbalanced braces",
            "an opening brace ({) is missing before this closing brace", query, i);
        }

        token += "}";
        tokens.add(token.trim());
        token = "";
        continue;
      }

      token += ch;
    }

    if(balance != 0) {
      if (balance > 0) {
        throw new SearchException("unbalanced braces",
                "One closing brace (}) is missing in this expression", query);
      } else {
        throw new SearchException("unbalanced braces",
                "One opening brace ({) is missing in this expression", query);
      }
    }

    if(!token.trim().equals("")) tokens.add(token);

    return tokens;
  }

  private boolean isOpeneningBrace(char ch, char pre) {
    if(ch == '{' && pre != '\\')
      return true;
    else return false;
  }

  private boolean isClosingBrace(char ch, char pre) {
    if(ch == '}' && pre != '\\')
      return true;
    else return false;
  }

  boolean areAllTermsTokens = false;

  private boolean isEscapeSequence(String element, int index) {
    if(index > 0) {
      return element.charAt(index - 1) == '\\';
    }
    return false;
  }

  private ArrayList<String> splitString(String string, char with, boolean normalize) {
    // here we want to split the string
    // but also make sure the with character is not escaped
    ArrayList<String> strings = new ArrayList<String>();
    StringBuffer newString = new StringBuffer();
    for(int i = 0; i < string.length(); i++) {
      if(i == 0) {
        newString.append(string.charAt(0));
        continue;
      }

      if(string.charAt(i) == with) {
        // need to check the previous character
        if(string.charAt(i - 1) == '\\') {
          newString.append(with);
          continue;
        }
        else {
          if(normalize)
            strings.add(norm(newString.toString()));
          else strings.add(newString.toString());

          newString = new StringBuffer();
          continue;
        }
      }

      newString.append(string.charAt(i));
    }
    if(newString.length() > 0) {
      if(normalize)
        strings.add(norm(newString.toString()).trim());
      else strings.add(newString.toString().trim());
    }
    return strings;
  }

  private int findIndexOf(String element, char ch) {
    int index1 = -1;
    int start = -1;
    while(true) {
      index1 = element.indexOf(ch, start);
      if(isEscapeSequence(element, index1)) {
        start = index1 + 1;
      }
      else {
        break;
      }
    }
    return index1;
  }

  private String norm(String string) {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < string.length(); i++) {
      if(string.charAt(i) == '\\') {
        if(i + 1 <= string.length() - 1) {
          char ch = string.charAt(i + 1);
          if(ch == ',' || ch == '.' || ch == '(' || ch == ')' || ch == '{'
                  || ch == '}' || ch == '"' || ch == '\\') continue;
        }
      }
      sb.append(string.charAt(i));
    }
    return sb.toString();
  }

  public List<?>[] createTerms(String elem)
          throws gate.creole.ir.SearchException {
    areAllTermsTokens = true;
    List<Term> terms = new ArrayList<Term>();
    List<Integer> pos = new ArrayList<Integer>();
    List<Boolean> consider = new ArrayList<Boolean>();

    elem = elem.trim();
    if(elem.charAt(0) == '{' && elem.charAt(elem.length() - 1) == '}') {
      // possible
      elem = elem.substring(1, elem.length() - 1);
      int index = elem.indexOf("==");
      int index1 = findIndexOf(elem, '.');

      if(index == -1 && index1 == -1) {
        // 3. {AnnotationType}
        // this can be {AnnotationType, AnnotationType...}
        ArrayList<String> fields = splitString(elem, ',', true);

        for(int p = 0; p < fields.size(); p++) {
          if(areAllTermsTokens
                  && !fields.get(p).equals(baseTokenAnnotationType))
            areAllTermsTokens = false;

          terms.add(new Term(field, norm(fields.get(p)), "*"));
          pos.add(position);
          consider.add(p == 0);

        }
        position++;
      }
      else if(index != -1 && index1 == -1) {
        // 4. {AnnotationType==String}
        // 5. {AnnotationType=="String"}

        ArrayList<String> fields = splitString(elem, ',', false);
        for(int p = 0; p < fields.size(); p++) {
          index = fields.get(p).indexOf("==");
          // here this is also posible
          // {AnnotationType, AnnotationType=="String"}
          if(index != -1) {
            String annotType = norm(fields.get(p).substring(0, index)
                    .trim());
            String annotText = norm(fields.get(p).substring(
                    index + 2, fields.get(p).length()).trim());
            if(annotText.length() > 2 && annotText.charAt(0) == '\"'
                    && annotText.charAt(annotText.length() - 1) == '\"') {
              annotText = annotText.substring(1, annotText.length() - 1);
            }
            if(!annotType.trim().equals(baseTokenAnnotationType))
              areAllTermsTokens = false;
            
            terms.add(new Term(field, annotText, annotType + ".string"));
            pos.add(position);
            consider.add(p == 0);
          }
          else {
            if(!(norm(fields.get(p))).equals(baseTokenAnnotationType))
              areAllTermsTokens = false;
            
            terms.add(new Term(field, norm(fields.get(p)), "*"));
            pos.add(position);
            consider.add(p == 0);
          }
        }

        position++;

      }
      else if(index == -1 && index1 != -1) {
        throw new SearchException("missing operator",
                "an equal operator (==) is missing",
                elem, (elem.indexOf("=", index1)!=-1)?
                       elem.indexOf("=", index1):elem.length());
      }
      else if(index != -1 && index1 != -1) {

        // it can be {AT, AT.f==S, AT=="S"}
        int index2 = findIndexOf(elem, ',');
        String[] subElems = null;
        if(index2 == -1) {
          subElems = new String[] {elem};
        }
        else {
          ArrayList<String> list = splitString(elem, ',', false);
          subElems = new String[list.size()];
          for(int k = 0; k < list.size(); k++) {
            subElems[k] = list.get(k);
          }
        }

        int lengthTravelledSoFar = 0;
        for(int j = 0; j < subElems.length; j++) {
          // 7. {AnnotationType.feature==string}
          // 8. {AnnotationType.feature=="string"}
          index = subElems[j].indexOf("==");
          index1 = findIndexOf(subElems[j], '.');
          if(index == -1 && index1 == -1) {
            // this is {AT}
            if(!norm(subElems[j].trim()).equals(baseTokenAnnotationType))
              areAllTermsTokens = false;
            terms.add(new Term(field, norm(subElems[j].trim()), "*"));
            pos.add(position);
            consider.add(j == 0);
          }
          else if(index != -1 && index1 == -1) {
            // this is {AT=="String"}
            String annotType = norm(subElems[j].substring(0, index).trim());
            String annotText = norm(subElems[j].substring(index + 2,
                    subElems[j].length()).trim());
            if(annotText.charAt(0) == '\"'
                    && annotText.charAt(annotText.length() - 1) == '\"') {
              annotText = annotText.substring(1, annotText.length() - 1);
            }
            if(!annotType.trim().equals(baseTokenAnnotationType))
              areAllTermsTokens = false;
            terms.add(new Term(field, annotText, annotType + ".string"));
            pos.add(position);
            consider.add(j == 0);
          }
          else if(index == -1 && index1 != -1) {
            throw new SearchException("missing operator",
                    "an equal operator (==) is missing",
                    elem, (elem.indexOf("=", lengthTravelledSoFar)!=-1)?
                           elem.indexOf("=", lengthTravelledSoFar):elem.length());
          }
          else {
            // this is {AT.f == "s"}
            String annotType = norm(subElems[j].substring(0, index1).trim());
            String featureType = norm(subElems[j].substring(index1 + 1, index)
                    .trim());
            String featureText = norm(subElems[j].substring(index + 2,
                    subElems[j].length()).trim());
            if(featureText.length() > 2 && featureText.charAt(0) == '\"'
                    && featureText.charAt(featureText.length() - 1) == '\"')
              featureText = featureText.substring(1, featureText.length() - 1);

            if(!annotType.trim().equals(baseTokenAnnotationType))
              areAllTermsTokens = false;
            terms.add(new Term(field, featureText, annotType + "."
                    + featureType));
            pos.add(position);
            consider.add(j == 0);
          }
          lengthTravelledSoFar += subElems[j].length() + 1;
        }
        position++;
      }
    }
    else {
      // possible
      // remove all the inverted commas
      StringBuilder newString = new StringBuilder();
      char prev = ' ', ch = ' ';
      for(int i = 0; i < elem.length(); i++) {
        prev = ch;
        ch = elem.charAt(i);
        if(ch == '\"' && prev != '\\') {
          continue;
        }
        else {
          newString.append(ch);
        }
      }
      // there can be many tokens
      String[] subTokens = norm(newString.toString()).split("( )+");
      for(int k = 0; k < subTokens.length; k++) {
        if(subTokens[k].trim().length() > 0) {
          terms.add(new Term(field, norm(subTokens[k]), baseTokenAnnotationType
                  + ".string"));
          pos.add(position);
          consider.add(Boolean.TRUE);
          position++;
        }
      }
    }
    return new List<?>[] {terms, pos, consider};
  }

  public boolean needValidation() {
    return needValidation;
  }
}
