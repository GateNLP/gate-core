/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 11 Apr 2002
 *
 *  $Id: ConditionalController.java 17606 2014-03-09 12:12:49Z markagreenwood $
 */

package gate.creole;

import gate.Controller;

import java.util.Collection;
import java.util.List;

/**
 * A Conditional controller is a controller that keeps a running strategy for
 * each PR contained. The running strategy decides whether a particular PR will
 * be run or not.
 */

public interface ConditionalController extends Controller{

  /**
   * Gets the collection of running strategies for the contained PRs.
   * The iterator of this collection should return the running strategies in
   * sync with the iterator for the getPRs() method of {@link Controller}.
   * @return a Collection object.
   */
  public List<RunningStrategy> getRunningStrategies();

  /**
   * Populates this controller with the appropiate running strategies from a
   * collection of running strategies
   * (optional operation).
   *
   * Controllers that are serializable must implement this method needed by GATE
   * to restore their contents.
   * @throws UnsupportedOperationException if the <tt>setPRs</tt> method
   * 	       is not supported by this controller.
   */
  public void setRunningStrategies(Collection<RunningStrategy> strategies);
}