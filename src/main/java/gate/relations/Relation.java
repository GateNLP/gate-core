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
 *  Valentin Tablan, 27 Feb 2012
 *
 *  $Id: Relation.java 17173 2013-12-15 18:54:25Z markagreenwood $
 */
package gate.relations;

import gate.util.FeatureBearer;
import gate.util.IdBearer;

import java.io.Serializable;

/**
 * Interface representing a relation between GATE annotations.
 */
public interface Relation extends Serializable, IdBearer, FeatureBearer {

  /**
   * Get the type of the relation (e.g. {@link #COREF}).
   * 
   * @return the relation type.
   */
  public String getType();

  /**
   * Gets the members of the relation.
   * 
   * @return an array containing annotation IDs.
   */
  public int[] getMembers();

  /**
   * Gets the arbitrary data associated with this relation
   * 
   * @return the arbitrary data associated with this relation
   */
  public Object getUserData();

  /**
   * Sets the arbitrary data associated with this relation
   * 
   * @param data the arbitrary data associated with this
   *          relation
   */
  public void setUserData(Object data);

  /**
   * Relation type for co-reference relations.
   */
  public static final String COREF = "coref";

}
