/*
 *  Relation.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Marin Dimitrov, 16/May/2002
 *
 *  $Id: Relation.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.wordnet;



/** Represents WordNet relation.
 */
public interface Relation {

  /** !    Antonym (noun,verb,adjective,adverb) */
  public static final int REL_ANTONYM = 10001;

  /**    Hypernym (noun,verb)*/
  public static final int REL_HYPERNYM = 10002;

  /** ~    Hyponym (noun,verb)*/
  public static final int REL_HYPONYM = 10003;

  /** #m    Member holonym (noun)*/
  public static final int REL_MEMBER_HOLONYM = 10004;

  /** #s    Substance holonym (noun)*/
  public static final int REL_SUBSTANCE_HOLONYM = 10005;

  /** #p    Part holonym (noun)*/
  public static final int REL_PART_HOLONYM = 10006;

  /** %m    Member meronym (noun)*/
  public static final int REL_MEMBER_MERONYM = 10007;

  /** %s    Substance meronym (noun)*/
  public static final int REL_SUBSTANCE_MERONYM = 10008;

  /** %p    Part meronym (noun)*/
  public static final int REL_PART_MERONYM = 10009;

  /** =    Attribute (noun,adjective)*/
  public static final int REL_ATTRIBUTE = 10010;

  /** *    Entailment (verb) */
  public static final int REL_ENTAILMENT = 10011;

  /** >    Cause (verb)*/
  public static final int REL_CAUSE = 10012;

  /** ^    Also see (verb,adjective)*/
  public static final int REL_SEE_ALSO = 10013;

  /** $    Verb Group (verb)*/
  public static final int REL_VERB_GROUP = 10014;

  /** <    Participle of verb (adjective)*/
  public static final int REL_PARTICIPLE_OF_VERB = 10015;

  /** &    Similar to (adjective)*/
  public static final int REL_SIMILAR_TO = 10016;

  /** \    Pertainym - pertains to noun (adjective)*/
  public static final int REL_PERTAINYM = 10017;

  /** \    Derived from adjective (adverb)*/
  public static final int REL_DERIVED_FROM_ADJECTIVE = 10018;

  /** returns the type of the relation - one of REL_XXX*/
  public int getType();

  /** returns the inverse relation (Hyponym  <-> Hypernym, etc)*/
  public int getInverseType();

  /** returns a label for the relation, e.g. "HYPERNYM" */
  public String getLabel();

  /** returns a symbol for the relation, e.g. "@" */
  public String getSymbol();

  /** checks if the relation is applicab;le to specific POS - see REL_XXX comments */
  public boolean isApplicableTo(int pos);

}

