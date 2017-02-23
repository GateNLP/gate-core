/*
 *  Adjective.java
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
 *  $Id: Adjective.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.wordnet;



/** Represents WordNet adj.
 */
public interface Adjective extends WordSense {

  /** adjective - prenominal (attributive) position  */
  public static final int ADJ_POS_ATTRIBUTIVE = 10001;

  /** adjective -  immediately postnominal position */
  public static final int ADJ_POS_IMMEDIATE_POSTNOMINAL  = 10002;

  /** adjective - predicate position */
  public static final int ADJ_POS_PREDICATIVE  = 10003;

  /** adjective - position unknown */
  public static final int ADJ_POS_NONE = 10004;

  /** returns the syntactic position of the adjective in relation to noun that it modifies */
  public int getAdjectivePosition();

}

