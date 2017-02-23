/*
 *  Controller.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 9/Nov/2000
 *
 *  $Id: Controller.java 17573 2014-03-07 10:03:27Z markagreenwood $
 */

package gate;

import java.util.Collection;

import gate.creole.ControllerAwarePR;
import gate.creole.ExecutionException;
import gate.util.FeatureBearer;
import gate.util.NameBearer;

/**
 * Models the execution of groups of ProcessingResources.
 */
public interface Controller extends Resource, Executable, NameBearer,
                           FeatureBearer {
  /**
   * Returns all the {@link gate.ProcessingResource}s contained by this
   * controller. The actual type of collection returned depends on the
   * controller type.
   */
  public Collection<ProcessingResource> getPRs();

  /**
   * Populates this controller from a collection of
   * {@link ProcessingResource}s (optional operation).
   * 
   * Controllers that are serializable must implement this method needed
   * by GATE to restore their contents.
   * 
   * @throws UnsupportedOperationException if the <tt>setPRs</tt>
   *           method is not supported by this controller.
   */
  public void setPRs(Collection<? extends ProcessingResource> PRs);

  /**
   * <p>
   * Executes this controller. Different controller implementations will
   * provide different strategies for executing the PRs they contain,
   * e.g. execute the PRs one after the other in sequence, execute them
   * once for each document in a corpus, or in parallel, based on some
   * condition, or in a branching workflow arrangement, etc.
   * </p>
   * 
   * <p>
   * If any of its child PRs implement the
   * {@link gate.creole.ControllerAwarePR} interface, then the controller
   * must ensure that all their relevant methods are called at the
   * correct times. See the documentation for {@link ControllerAwarePR}
   * for details.
   * </p>
   */
  @Override
  public void execute() throws ExecutionException;

} // interface Controller
