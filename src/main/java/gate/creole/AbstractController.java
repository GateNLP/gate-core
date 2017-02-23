/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 27 Sep 2001
 *
 *  $I$
 */
package gate.creole;

import gate.Controller;
import gate.Gate;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.metadata.CreoleResource;
import gate.event.ControllerEvent;
import gate.event.ControllerListener;
import gate.event.ProgressListener;
import gate.event.StatusListener;
import gate.util.Benchmark;
import gate.util.Benchmarkable;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

@CreoleResource(icon = "application")
public abstract class AbstractController extends AbstractResource 
       implements Controller, ProcessingResource, Benchmarkable {

  private static final long serialVersionUID = 6466829205468662382L;

  /**
   * Benchmark ID of this resource.
   */
  protected String benchmarkID;

  /**
   * Shared featureMap
   */
  protected Map<Object,Object> benchmarkFeatures = new HashMap<Object,Object>();

  // executable code
  /**
   * Execute this controller. This implementation takes care of informing any
   * {@link ControllerAwarePR}s of the start and end of execution, and
   * delegates to the {@link #executeImpl()} method to do the real work.
   * Subclasses should override {@link #executeImpl()} rather than this method.
   */
  @Override
  public void execute() throws ExecutionException {

    // inform ControllerAware PRs that execution has started, if automatic callbacks are enabled
    if(controllerCallbacksEnabled) { 
      invokeControllerExecutionStarted();
    }
    Throwable thrown = null;
    try {
      if(Benchmark.isBenchmarkingEnabled()) {
        // write a start marker to the benchmark log for this
        // controller as a whole
        Benchmark.startPoint(getBenchmarkId());
      }
      // do the real work
      this.executeImpl();
    }
    catch(Throwable t) {
      thrown = t;
    }
    finally {
      if(thrown == null) {
        // successfully completed
        if(controllerCallbacksEnabled) {
          invokeControllerExecutionFinished();
        }
      }
      else {
        // aborted
        if(controllerCallbacksEnabled) {
          invokeControllerExecutionAborted(thrown);
        }

        // rethrow the aborting exception or error
        if(thrown instanceof Error) {
          throw (Error)thrown;
        }
        else if(thrown instanceof RuntimeException) {
          throw (RuntimeException)thrown;
        }
        else if(thrown instanceof ExecutionException) {
          throw (ExecutionException)thrown;
        }
        else {
          // we have a checked exception that isn't one executeImpl can
          // throw. This shouldn't be possible, but just in case...
          throw new UndeclaredThrowableException(thrown);
        }
      }
    }

  }

  /**
   * Get the set of PRs from this controller that implement
   * {@link ControllerAwarePR}. If there are no such PRs in this controller, an
   * empty set is returned. This implementation simply filters the collection
   * returned by {@link Controller#getPRs()}, override this method if your
   * subclass admits a more efficient implementation.
   */
  protected Set<ControllerAwarePR> getControllerAwarePRs() {
    Set<ControllerAwarePR> returnSet = null;
    for(Object pr : getPRs()) {
      if(pr instanceof ControllerAwarePR) {
        if(returnSet == null) {
          returnSet = new HashSet<ControllerAwarePR>();
        }
        returnSet.add((ControllerAwarePR)pr);
      }
    }

    if(returnSet == null) {
      // optimization - don't waste time creating a new set in the most
      // common case where there are no Controller aware PRs
      return Collections.emptySet();
    }
    else {
      return returnSet;
    }
  }

  /**
   * Executes the PRs in this controller, according to the execution strategy of
   * the particular controller type (simple pipeline, parallel execution,
   * once-per-document in a corpus, etc.). Subclasses should override this
   * method, allowing the default {@link #execute()} method to handle sending
   * notifications to controller aware PRs.
   */
  protected void executeImpl() throws ExecutionException {
    throw new ExecutionException("Controller " + getClass()
      + " hasn't overriden the executeImpl() method");
  }

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    return this;
  }

  /* (non-Javadoc)
   * @see gate.ProcessingResource#reInit()
   */
  @Override
  public void reInit() throws ResourceInstantiationException {
    init();
  }

  /** Clears the internal data of the resource, when it gets released * */
  @Override
  public void cleanup() {
  }

  /**
   * Populates this controller from a collection of {@link ProcessingResource}s
   * (optional operation).
   * 
   * Controllers that are serializable must implement this method needed by GATE
   * to restore their contents.
   * 
   * @throws UnsupportedOperationException
   *           if the <tt>setPRs</tt> method is not supported by this
   *           controller.
   */
  @Override
  public void setPRs(Collection<? extends ProcessingResource> PRs) {
  }

  /**
   * Notifies all the PRs in this controller that they should stop their
   * execution as soon as possible.
   */
  @Override
  public synchronized void interrupt() {
    interrupted = true;
    Iterator<ProcessingResource> prIter = getPRs().iterator();
    while(prIter.hasNext()) {
      prIter.next().interrupt();
    }
  }

  @Override
  public synchronized boolean isInterrupted() {
    return interrupted;
  }

  // events code
  /**
   * Removes a {@link gate.event.StatusListener} from the list of listeners for
   * this processing resource
   */
  public synchronized void removeStatusListener(StatusListener l) {
    if(statusListeners != null && statusListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<StatusListener> v = (Vector<StatusListener>)statusListeners.clone();
      v.removeElement(l);
      statusListeners = v;
    }
  }

  /**
   * Adds a {@link gate.event.StatusListener} to the list of listeners for this
   * processing resource
   */
  public synchronized void addStatusListener(StatusListener l) {
    @SuppressWarnings("unchecked")
    Vector<StatusListener> v =
      statusListeners == null ? new Vector<StatusListener>(2) : (Vector<StatusListener>)statusListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      statusListeners = v;
    }
  }

  /**
   * Notifies all the {@link gate.event.StatusListener}s of a change of status.
   * 
   * @param e
   *          the message describing the status change
   */
  protected void fireStatusChanged(String e) {
    if(statusListeners != null) {
      Vector<StatusListener> listeners = statusListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).statusChanged(e);
      }
    }
  }

  /**
   * Adds a {@link gate.event.ProgressListener} to the list of listeners for
   * this processing resource.
   */
  public synchronized void addProgressListener(ProgressListener l) {
    @SuppressWarnings("unchecked")
    Vector<ProgressListener> v =
      progressListeners == null ? new Vector<ProgressListener>(2) : (Vector<ProgressListener>)progressListeners
        .clone();
    if(!v.contains(l)) {
      v.addElement(l);
      progressListeners = v;
    }
  }

  /**
   * Removes a {@link gate.event.ProgressListener} from the list of listeners
   * for this processing resource.
   */
  public synchronized void removeProgressListener(ProgressListener l) {
    if(progressListeners != null && progressListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<ProgressListener> v = (Vector<ProgressListener>)progressListeners.clone();
      v.removeElement(l);
      progressListeners = v;
    }
  }

  /**
   * Notifies all the {@link gate.event.ProgressListener}s of a progress change
   * event.
   * 
   * @param e
   *          the new value of execution completion
   */
  protected void fireProgressChanged(int e) {
    if(progressListeners != null) {
      Vector<ProgressListener> listeners = progressListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).progressChanged(e);
      }
    }
  }

  /**
   * Notifies all the {@link gate.event.ProgressListener}s of a progress
   * finished.
   */
  protected void fireProcessFinished() {
    if(progressListeners != null) {
      Vector<ProgressListener> listeners = progressListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).processFinished();
      }
    }
  }

  /**
   * A progress listener used to convert a 0..100 interval into a smaller one
   */
  protected class IntervalProgressListener implements ProgressListener {
    public IntervalProgressListener(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public void progressChanged(int i) {
      fireProgressChanged(start + (end - start) * i / 100);
    }

    @Override
    public void processFinished() {
      fireProgressChanged(end);
    }

    int start;
    int end;
  }// CustomProgressListener

  /**
   * A simple status listener used to forward the events upstream.
   */
  protected class InternalStatusListener implements StatusListener {
    @Override
    public void statusChanged(String message) {
      fireStatusChanged(message);
    }
  }

  /**
   * Checks whether all the contained PRs have all the required runtime
   * parameters set.
   * 
   * @return a {@link List} of {@link ProcessingResource}s that have required
   *         parameters with null values if they exist <tt>null</tt>
   *         otherwise.
   * @throws {@link ResourceInstantiationException}
   *           if problems occur while inspecting the parameters for one of the
   *           resources. These will normally be introspection problems and are
   *           usually caused by the lack of a parameter or of the read accessor
   *           for a parameter.
   */
  public List<ProcessingResource> getOffendingPocessingResources()
    throws ResourceInstantiationException {
    // take all the contained PRs
    List<ProcessingResource> badPRs = new ArrayList<ProcessingResource>(getPRs());
    // remove the ones that no parameters problems
    Iterator<ProcessingResource> prIter = getPRs().iterator();
    while(prIter.hasNext()) {
      ProcessingResource pr = prIter.next();
      ResourceData rData =
        Gate.getCreoleRegister().get(pr.getClass().getName());
      if(AbstractResource.checkParameterValues(pr, rData.getParameterList()
        .getRuntimeParameters())) {
        badPRs.remove(pr);
      }
    }
    return badPRs.isEmpty() ? null : badPRs;
  }

  public synchronized void removeControllerListener(ControllerListener l) {
    if(controllerListeners != null && controllerListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<ControllerListener> v = (Vector<ControllerListener>)controllerListeners.clone();
      v.removeElement(l);
      controllerListeners = v;
    }
  }

  public synchronized void addControllerListener(ControllerListener l) {
    @SuppressWarnings("unchecked")
    Vector<ControllerListener> v =
      controllerListeners == null ? new Vector<ControllerListener>(2) : (Vector<ControllerListener>)controllerListeners
        .clone();
    if(!v.contains(l)) {
      v.addElement(l);
      controllerListeners = v;
    }
  }

  /**
   * The list of {@link gate.event.StatusListener}s registered with this
   * resource
   */
  private transient Vector<StatusListener> statusListeners;

  /**
   * The list of {@link gate.event.ProgressListener}s registered with this
   * resource
   */
  private transient Vector<ProgressListener> progressListeners;

  /**
   * The list of {@link gate.event.ControllerListener}s registered with this
   * resource
   */
  private transient Vector<ControllerListener> controllerListeners;

  protected boolean interrupted = false;

  protected void fireResourceAdded(ControllerEvent e) {
    if(controllerListeners != null) {
      Vector<ControllerListener> listeners = controllerListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).resourceAdded(e);
      }
    }
  }

  protected void fireResourceRemoved(ControllerEvent e) {
    if(controllerListeners != null) {
      Vector<ControllerListener> listeners = controllerListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).resourceRemoved(e);
      }
    }
  }
  
  /**
   * Sets the benchmark ID of this controller.
   */
  @Override
  public void setBenchmarkId(String benchmarkID) {
    this.benchmarkID = benchmarkID;
  }

  /**
   * Returns the benchmark ID of this controller.
   */
  @Override
  public String getBenchmarkId() {
    if(benchmarkID == null) {
      benchmarkID = getName().replaceAll("[ ]+", "_");
    }
    return benchmarkID;
  }
  
   /**
   * Invoke the controllerExecutionStarted method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @throws ExecutionException 
   */
  public void invokeControllerExecutionStarted() throws ExecutionException {
    for (ControllerAwarePR pr : getControllerAwarePRs()) {
      pr.controllerExecutionStarted(this);
    }
  }

   /**
   * Invoke the controllerExecutionFinished method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @throws ExecutionException 
   */
  public void invokeControllerExecutionFinished() throws ExecutionException {
    for (ControllerAwarePR pr : getControllerAwarePRs()) {
      pr.controllerExecutionFinished(this);
    }
  }
  
   /**
   * Invoke the controllerExecutionAborted method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @throws ExecutionException 
   */
  public void invokeControllerExecutionAborted(Throwable thrown) throws ExecutionException {
    for (ControllerAwarePR pr : getControllerAwarePRs()) {
      pr.controllerExecutionAborted(this, thrown);
    }
  }
    
  protected boolean controllerCallbacksEnabled = true;  
  /**
   * Enable or disable the automatic invocation of the controller callbacks. 
   * By default, the controller calls the controllerExecutionStarted method of each controllerAwarePR
   * at the start of execute(), the controllerExecutionFinished method of each controllerAwarePR
   * at the end of execute() or the controllerExecutionAborted method of each controllerAwarePR if
   * there was an exception during execute(). If this method is called with the parameter false
   * before execute() is called, then those controllerAwarePR methods will not get called automatically.
   * In that case they can invoked deliberately using the invokeControllerExecutionStarted(), 
   * invokeControllerExecutionFinished() and controllerExecutionAborted() methods.
   * 
   * @param flag a boolean indicating if the callbacks should be enabled (true) or disabled (false)
   */
  public void setControllerCallbacksEnabled(boolean flag) {
    controllerCallbacksEnabled = flag;
  }
    
    
  
  
  
  
  
}
