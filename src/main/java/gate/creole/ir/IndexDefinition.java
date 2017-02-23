/*
 *  IndexDefinition.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 19/Apr/2002
 *
 */

package gate.creole.ir;

import java.io.Serializable;
import java.util.Iterator;

public interface IndexDefinition extends Serializable{

  static final long serialVersionUID = 3632609241787241616L;

  /** @return String  path of index store directory*/
  public String getIndexLocation();

  /**  @return Iterator of IndexFields, fileds for indexing. */
  public Iterator<IndexField> getIndexFields();

//  /**  @return int index type*/
//  public int getIndexType();

  /**
   * Gets the type of IR engine to be used for indexing
   * @return a String.
   */
  public String getIrEngineClassName();
}