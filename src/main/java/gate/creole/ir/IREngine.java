/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 17/05/2002
 *
 *  $Id: IREngine.java 15333 2012-02-07 13:18:33Z ian_roberts $
 *
 */
package gate.creole.ir;

/**
 * Defines an information retrieval engine which needs to supply a
 * {@link IndexManager} and a {@link Search}.
 */

public interface IREngine {

  /**
   * Gets the search component of this IR engine.
   * @return a {@link Search} value.
   */
  public Search getSearch();

  /**
   * Gets the index manager component of this IR engine.
   * @return a {@link IndexManager} value.
   */
  public IndexManager getIndexmanager();

  /**
   * Gets the name for this IR engine.
   * @return a {@link String} value.
   */
  public String getName();

//  /**
//   * Returns the index type.
//   * @return and int.
//   */
//  public int getIndexType();

}