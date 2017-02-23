/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts 04/07/2007
 *
 *  $Id: ControllerAwarePR.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.creole;

import gate.Controller;
import gate.ProcessingResource;

/**
 * <p>
 * This interface should be implemented by processing resources that
 * need to know when any containing controller starts and ends its
 * execution, for example to initialise internal data structures or to
 * do some aggregate processing of data gathered from a whole corpus.
 * </p>
 * <p>
 * If a controller contains several PRs that implement this interface,
 * the order in which their <code>controllerExecutionStarted</code> (<code>Finished</code>
 * or <code>Aborted</code>) methods are called is not specified. In
 * particular, the "ended" methods may be called in a different order
 * from the "started" ones. Also, if one PR throws an exception from its
 * <code>controllerExecutionFinished</code> method, the other finished
 * methods may not be called at all for this run. PRs should be robust
 * to this possibility.
 * </p>
 * <p>
 * If the processing resource implementing this interface is contained in
 * a conditional controller the methods defined by this interface are invoked
 * independently of the RunningStrategy for the processing resource: even if
 * the PR is disabled, the methods will get invoked. The method
 * {@link gate.Utils#isEnabled(Controller, ProcessingResource)} can be used
 * inside the implementation of the methods defined in this interface 
 * if necessary to find out if the processing resource has a chance to run
 * in the controller.
 * </p>
 * <p>
 * The controller should call this PRs started and finished (or aborted)
 * methods at most once per run, even if the controller allows the same
 * PR to be added multiple times.
 * </p>
 */
public interface ControllerAwarePR extends ProcessingResource {
  /**
   * Called by a controller containing this PR when the controller
   * begins executing. When this method is called, it is guaranteed that
   * none of the PRs in this controller have yet been
   * <code>execute</code>d on this run.
   * 
   * @param c the {@link Controller} that is executing.
   * @throws ExecutionException if an error occurs that requires the
   *           controller to abort its execution.
   */
  public void controllerExecutionStarted(Controller c)
          throws ExecutionException;

  /**
   * Called by a controller containing this PR when the controller's
   * execution has completed successfully. When this method is called,
   * it is guaranteed that there will be no more calls to the
   * <code>execute</code> method of any of this controller's PRs in
   * this run.
   * 
   * @param c the {@link Controller} that is executing.
   * @throws ExecutionException if an error occurs that requires the
   *           controller to abort its execution.
   */
  public void controllerExecutionFinished(Controller c)
          throws ExecutionException;

  /**
   * Called by a controller containing this PR when the controller's
   * execution has been aborted by an exception thrown by one of the
   * contained PR's <code>execute</code> methods, or by the controller
   * itself. When this method is called, it is guaranteed that there
   * will be no more calls to the <code>execute</code> method of any
   * of this controller's PRs in this run.
   * 
   * @param c the {@link Controller} that is executing.
   * @param t the <code>Throwable</code> that caused the controller to
   *          abort. This will be either an {@link ExecutionException},
   *          a {@link RuntimeException} or an {@link Error}.
   * @throws ExecutionException if an error occurs in this method that
   *           requires the controller to abort its execution. This
   *           method should not rethrow <code>t</code>, as the
   *           controller will do this after informing any other
   *           <code>ControllerAware</code> PRs it contains.
   */
  public void controllerExecutionAborted(Controller c, Throwable t)
          throws ExecutionException;
}
