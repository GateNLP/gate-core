/*
 *  Word.java
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
 *  $Id: Word.java 17493 2014-03-01 09:49:01Z markagreenwood $
 */

package gate.wordnet;

import java.util.List;


/** Represents WordNet word.
 */
public interface Word {

  /** returns the senses of this word */
  public List<WordSense> getWordSenses() throws WordNetException;

  /** returns the lemma of this word */
  public String getLemma();

  /** returns the number of senses of this word (not necessarily loading them from storage) */
  public int getSenseCount();

}

