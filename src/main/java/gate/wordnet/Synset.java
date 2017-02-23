/*
 *  Synset.java
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
 *  $Id: Synset.java 17496 2014-03-01 14:20:35Z markagreenwood $
 */

package gate.wordnet;

import java.util.List;

/** Represents WordNet synset.
 */
public interface Synset {

  /** returns the part-of-speech for this synset, see WordNet::POS_XXX constants */
  public int getPOS();

  /** is this synset a UB - i.e. has no hypernym */
  public boolean isUniqueBeginner() throws WordNetException;

  /** textual description of the synset */
  public String getGloss();

  /** offset in index files */
  public long getOffset();

  /** WordSenses contained in this synset */
  public List<WordSense> getWordSenses();

  /** get specific WordSense according to its order in the synset - most important senses come first  */
  public WordSense getWordSense(int offset);

  /** get the SemanticRelation-s of this synset */
  public List<SemanticRelation> getSemanticRelations() throws WordNetException;

  /** get the SemanticRelation-s of specific type (HYPERNYm) for this synset */
  public List<SemanticRelation> getSemanticRelations(int type) throws WordNetException;

}

