/*
 *  WordSense.java
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
 *  $Id: WordSense.java 17493 2014-03-01 09:49:01Z markagreenwood $
 */

package gate.wordnet;

import java.util.List;


/** Represents WordNet word sense.
 */
public interface WordSense {

  /** returns the Word of this WordSense */
  public Word getWord();

  /** part-of-speech for this sense (inherited from the containing synset) */
  public int getPOS();

  /** synset of this sense */
  public Synset getSynset();

  /** order of this sense relative to the word - i.e. most important senses of the same word come first */
  public int getSenseNumber();

  /** order of this sense relative to the synset- i.e. most important senses of the same synset come first */
  public int getOrderInSynset();

  /** appears in SEMCOR? */
  public boolean isSemcor();

  /** return the Lex relations this sense participates in */
  public List<LexicalRelation> getLexicalRelations() throws WordNetException ;

  /** return the Lex relations (of the specified type) this sense participates in */
  public List <LexicalRelation> getLexicalRelations(int type) throws WordNetException ;

}

