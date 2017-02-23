/*
 *  SubQueryParser.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: SubQueryParser.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import java.io.*;
import java.util.*;

import gate.creole.ir.SearchException;

/**
 * This class behaves as a helper class to the QueryParser and provides
 * various methods which are called from various methods of QueryParser.
 * 
 * @author niraj
 */
public class SubQueryParser {

  public static void main(String[] args) {
    try {

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while(true) {
        System.out.print("Query: ");
        String line = in.readLine();

        if(line.length() == -1) break;

        List<String> queries = parseQuery(line);
        for(int i = 0; i < queries.size(); i++) {
          System.out.println("=>" + queries.get(i));
        }
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Method retrieves wild card characters after the closing bracket.
   */
  private static String findWildCardString(int brClPos, String query) {
    String wcs = "";
    if(brClPos + 1 < query.length()) {
      if(query.charAt(brClPos + 1) == '*' || query.charAt(brClPos + 1) == '+' || query.charAt(brClPos + 1) == '?') {
        wcs = query.charAt(brClPos + 1) + "";
        // ok so lets fetch the number
        for(int i = brClPos + 2; i < query.length(); i++) {
          if(Character.isDigit(query.charAt(i))) {
            wcs += query.charAt(i);
          }
          else {
            break;
          }
        }
      }
    }
    return wcs;
  }

  /**
   * This method, interprets the wild cards and convert query
   * accordingly. For example: (A)+3 is converted into ((A) | ((A)(A)) |
   * ((A)(A)(A)))
   */
  private static String extractWildcards(String query) throws SearchException {
    outer: while(true) {
      char ch = ' ', pre = ' ';
      for(int i = 0; i < query.length(); i++) {
        pre = ch;
        ch = query.charAt(i);

        // check if it is an open bracket
        // it is if it doesn't follow the '\' escape sequence
        if(isOpenBracket(ch, pre)) {

          // so find out where it gets closed
          int brClPos = findBracketClosingPosition(i + 1, query);
          if(brClPos == -1) {
            throw new SearchException("unbalanced brackets",
              "a closing bracket ()) is missing for this opening bracket", query, i);
          }

          String wildCardString = findWildCardString(brClPos, query);
          int wcsLen = 0;
          boolean atLeastOne = false;

          // at least once
          int repeatClause = 1;

          if(wildCardString.length() != 0) {
            if(wildCardString.length() == 1) {
              // if there is only wildcard char sign
              // we consider it as 1
              wcsLen = 1;
            }
            else {
              atLeastOne = (wildCardString.charAt(0) == '*' || wildCardString.charAt(0) == '?') ? false : true;
              // now find out the number of Times we need to
              // duplicate the bracketClause
              repeatClause = Integer.parseInt(wildCardString.substring(1,
                      wildCardString.length()));
              wcsLen = wildCardString.length();
            }

            String previous = query.substring(0, i);
            String after = query
                    .substring(brClPos + wcsLen + 1, query.length());
            String sToRepeat = query.substring(i, brClPos + 1);

            String newString = "(";
            for(int k = 1; k <= repeatClause; k++) {
              newString += "(";
              for(int subK = 0; subK < k; subK++) {
                newString += sToRepeat;
              }
              newString += ")";
              if(k + 1 <= repeatClause) {
                newString += " | ";
              }
            }

            if(!atLeastOne) {
              newString += "| {__o__}";
            }

            newString += ")";
            query = previous + newString + after;
            continue outer;
          }
        }
      }

      // if we are here
      // that means no whildcard left
      return query;
    }
  }

  /**
   * this method parses the query and returns the different queries
   * converted into the OR normalized form 
   * for e.g. ({A}|{B}){C} 
   * this will be converted into ({A}{C}) | ({B}{C}) 
   * and the arrayList consists of 
   * 1. {A}{C} 
   * 2. {B}{C}
   */
  public static List<String> parseQuery(String q1) throws SearchException {

    // arraylist to return - will contain all the OR normalized queries
    List<String> queries = new ArrayList<String>();

    // remove all extra spaces from the query
    q1 = q1.trim();

    // we add opening and closing brackets explicitly
    q1 = "( " + q1 + " )";

    q1 = extractWildcards(q1);
    // add the main Query in the arraylist
    queries.add(q1);

    for(int index = 0; index < queries.size(); index++) {
      // get the query to be parsed
      String query = queries.get(index);

      // current character and the previous character
      char ch = ' ', pre = ' ';

      // if query is ORed
      // we need duplication
      // for example: {A}({B}|{C})
      // the normalized form will be
      // {A}{B}
      // {A}{C}
      // here we need {A} to be duplicated two times
      boolean duplicated = false;
      int dupliSize = 0;
      String data = "";

      // we need to look into one query at a time and parse it
      for(int i = 0; i < query.length(); i++) {
        pre = ch;
        ch = query.charAt(i);

        // check if it is an open bracket
        // it is if it doesn't follow the '\' escape sequence
        if(isOpenBracket(ch, pre)) {

          // so find out where it gets closed
          int brClPos = findBracketClosingPosition(i + 1, query);
          if(brClPos == -1) {
            throw new SearchException("unbalanced brackets",
              "a closing bracket ()) is missing for this opening bracket", query, i);
          }

          // see if there are any OR operators in it
          ArrayList<String> orTokens = findOrTokens(query.substring(i + 1, brClPos));

          // orTokens will have
          // for eg. {A} | ({B}{C})
          // then {A}
          // and ({B}{C})
          // so basically findOrTokens find out all the tokens around
          // | operator
          if(orTokens.size() > 1) {
            String text = "";

            // data contains all the buffered character before the
            // current positions
            // for example "ABC" ({B} | {C})
            // here "ABC" will be in data
            // and {B} and {C} in orTokens
            if(!duplicated && data.length() > 0) {
              text = data;
              data = "";
            }
            else {
              if(index == queries.size() - 1) {
                // this is the case where we would select the
                // text as ""
                text = "";
              }
              else {
                text = queries.get(queries.size() - 1);
              }
            }

            // so we need to duplicate the text orTokens.size()
            // times
            // for example "ABC" ({B} | {C})
            // text = "ABC"
            // orTokens {B} {C}
            // so two queries will be added
            // 1. "ABC"
            // 2. "ABC"

            queries = duplicate(queries, text, dupliSize, orTokens.size());
            // and tokens will be added
            // 1. "ABC" {B}
            // 2. "ABC" {C}
            queries = writeTokens(orTokens, queries, dupliSize);

            // text is duplicated so make it true
            duplicated = true;

            // and how many times it was duplicated
            if(dupliSize == 0) dupliSize = 1;
            dupliSize *= orTokens.size();
          }
          else {
            // what if the there is only one element between ( and )
            // it is not an 'OR' query

            // check how many times we have duplicated the text
            if(dupliSize == 0) {
              // if zero and the text buffered is ""
              // we simply add "" as a separate Query
              // otherwise add the buffered data as a separate
              // Query
              if(data.length() == 0)
                queries.add("");
              else queries.add(data);

              // because we simply needs to add it only once
              // but still we have copied it as a separate query
              // so say duplicated = true
              duplicated = true;
              data = "";
              // and ofcourse the size of the duplication will be
              // only 1
              dupliSize = 1;
            }
            // and we need to add all the contents between two
            // brackets in the last duplicated
            // queries
            queries = writeStringInAll(query.substring(i + 1, brClPos),
                    dupliSize, queries);
          }
          i = brClPos;
        }
        else if(isClosingBracket(ch, pre)) {
          throw new SearchException("unbalanced brackets",
            "a opening bracket (() is missing for this closing bracket", query, i);
        }
        else {
          if(duplicated) {
            queries = writeCharInAll(ch, dupliSize, queries);
          }
          else {
            data += "" + ch;
          }
        }
      }

      boolean scan = scanQueryForOrOrBracket(query);
      if(scan) {
        queries.remove(index);
        index--;
      }
    }

    ArrayList<String> queriesToReturn = new ArrayList<String>();
    for(int i = 0; i < queries.size(); i++) {
      String q = queries.get(i);
      if(q.trim().length() == 0) {
        continue;
      }
      else if(queriesToReturn.contains(q.trim())) {
        continue;
      }
      else {
        queriesToReturn.add(q.trim());
      }
    }
    return queriesToReturn;
  }

  /**
   * This method checks if query has either | or ( in it.
   */
  public static boolean scanQueryForOrOrBracket(String query) {
    int index = 0;
    int index1 = 0;
    do {
      index = query.indexOf('|', index);
      if(index == 0) {
        return true;
      }
      else if(index > 0) {
        // we have found it but we need to check if it is an escape
        // sequence
        if(query.charAt(index - 1) == '\\') {
          // yes it is an escape sequence
          // lets search for the next one
        }
        else {
          return true;
        }
      }

      // if we are here that means it was not found
      index1 = query.indexOf('(', index1);
      if(index1 == 0) {
        return true;
      }
      else if(index1 > 0) {
        // we have found it
        if(query.charAt(index1 - 1) == '\\') {
          // yes it is an escape sequence
          continue;
        }
        else {
          return true;
        }
      }

    } while(index >= 0 && index1 >= 0);

    return false;
  }

  /**
   * This is a helper method that helps in duplicating the provided tokens.
   */
  private static List<String> writeTokens(List<String> tokens, List<String> queries,
          int dupliSize) {
    if(dupliSize == 0) dupliSize = 1;

    ArrayList<String> qToRemove = new ArrayList<String>();
    for(int j = 0; j < dupliSize; j++) {
      for(int i = 1; i <= tokens.size(); i++) {
        String token = tokens.get(i - 1);
        if(token.trim().equals("{__o__}")) {
          token = " ";
        }
        String s = queries
                .get(queries.size() - (j * tokens.size() + i));
        qToRemove.add(s);
        s += token;
        queries.set(queries.size() - (j * tokens.size() + i), s);
      }
    }

    // and now remove
    for(int i = 0; i < qToRemove.size(); i++) {
      queries.remove(qToRemove.get(i));
    }

    return queries;
  }

  /**
   * This is a helper method that helps in duplicating the provided tokens.
   */
  private static List<String> duplicate(List<String> queries, String s, int dupliSize,
          int no) {
    if(s == null) s = "";

    List<String> strings = new ArrayList<String>();
    if(dupliSize == 0) {
      strings.add(s);
    }
    else {
      for(int i = 0; i < dupliSize; i++) {
        strings.add(queries.get(queries.size() - (i + 1)));
      }
    }

    for(int i = 0; i < strings.size(); i++) {
      for(int j = 0; j < no; j++) {
        queries.add(strings.get(i));
      }
    }

    return queries;
  }

  /**
   * This method given a query identifies the OR Tokens
   * for eg. {A} | ({B}{C})
   * then {A}
   * and ({B}{C})
   * so basically findOrTokens find out all the tokens around
   * | operator
   */
  public static ArrayList<String> findOrTokens(String query) {
    int balance = 0;
    char pre = ' ';
    char ch = ' ';
    ArrayList<String> ors = new ArrayList<String>();

    String s = "";
    for(int i = 0; i < query.length(); i++) {
      pre = ch;
      ch = query.charAt(i);
      if(isOpenBracket(ch, pre)) {
        balance++;
        s += "" + ch;
        continue;
      }

      if(isClosingBracket(ch, pre) && balance > 0) {
        balance--;
        s += "" + ch;
        continue;
      }

      if(isOrSym(ch, pre)) {
        if(balance > 0) {
          s += "" + ch;
          continue;
        }
        else {
          ors.add(s);
          s = "";
          continue;
        }
      }

      s += "" + ch;
    }

    if(s.length() > 0) ors.add(s);

    return ors;
  }

  /**
   * Returns the position of a closing bracket.
   */
  private static int findBracketClosingPosition(int startFrom, String query) {
    int balance = 0;
    char pre = ' ';
    char ch = ' ';
    for(int i = startFrom; i < query.length(); i++) {
      pre = ch;
      ch = query.charAt(i);
      if(isOpenBracket(ch, pre)) {
        balance++;
        continue;
      }

      if(isClosingBracket(ch, pre)) {
        if(balance > 0) {
          balance--;
        }
        else {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Helps in duplicating a character in the provided queries
   */
  private static List<String> writeCharInAll(char c, int no, List<String> queries) {
    for(int i = 0; i < no; i++) {
      String s = queries.get(queries.size() - (i + 1));
      s += "" + c;
      queries.set(queries.size() - (i + 1), s);
    }
    return queries;
  }

  /**
   * Helps in duplicating a string in the provided queries
   */
  private static List<String> writeStringInAll(String c, int no, List<String> queries) {
    for(int i = 0; i < no; i++) {
      String s = queries.get(queries.size() - (i + 1));
      s += "" + c;
      queries.set(queries.size() - (i + 1), s);
    }
    return queries;
  }

  /**
   * Returns if the character is bracket used to mark boundary of a token or an escape character.
   */
  private static boolean isOpenBracket(char ch, char pre) {
    if(ch == '(' && pre != '\\')
      return true;
    else return false;
  }

  /**
   * Returns if the character is bracket used to mark boundary of a token or an escape character.
   */
  private static boolean isClosingBracket(char ch, char pre) {
    if(ch == ')' && pre != '\\')
      return true;
    else return false;
  }

  /**
   * Returns if the character is an OR symbol used as a logical operator or an escape character.
   */
  private static boolean isOrSym(char ch, char pre) {
    if(ch == '|' && pre != '\\')
      return true;
    else return false;
  }

}
