/*
 *  AbstractProcessingResource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 10/Nov/2000
 *
 *  $Id: AbstractProcessingResource.java 19739 2016-11-15 16:02:46Z markagreenwood $
 */

package gate.creole;

import java.util.Vector;

import gate.FeatureMap;
import gate.Gate;
import gate.ProcessingResource;
import gate.Resource;
import gate.event.ProgressListener;
import gate.event.StatusListener;

/** A convenience implementation of ProcessingResource with some default
  * code.
  */
abstract public class AbstractProcessingResource
extends AbstractResource implements ProcessingResource, ANNIEConstants
{
  private static final long serialVersionUID = -5919543745701986862L;

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    return this;
  } // init()

  /** Run the resource. It doesn't make sense not to override
   *  this in subclasses so the default implementation signals an
   *  exception.
   */
  @Override
  public void execute() throws ExecutionException{
    throw new ExecutionException(
      "Resource " + getClass() + " hasn't overriden the execute() method"
    );
  } // execute()

  /**
   * Reinitialises the processing resource. After calling this method the
   * resource should be in the state it is after calling init.
   * If the resource depends on external resources (such as rules files) then
   * the resource will re-read those resources. If the data used to create
   * the resource has changed since the resource has been created then the
   * resource will change too after calling reInit().
   * The implementation in this class simply calls {@link #init()}. This
   * functionality must be overriden by derived classes as necessary.
   */
  @Override
  public void reInit() throws ResourceInstantiationException{
    init();
  } // reInit()

  /** should clear all internal data of the resource. Does nothing now */
  @Override
  public void cleanup() {
  }

  /**
   * Checks whether this PR has been interrupted since the last time its
   * {@link #execute()} method was called.
   */
  @Override
  public boolean isInterrupted(){
    return interrupted;
  }

  /**
   * Notifies this PR that it should stop its execution as soon as possible.
   */
  @Override
  public void interrupt(){
    interrupted = true;
  }


  /**
   * Removes a {@link gate.event.StatusListener} from the list of listeners for
   * this processing resource
   */
  public synchronized void removeStatusListener(StatusListener l) {
    if (statusListeners != null && statusListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<StatusListener> v = (Vector<StatusListener>)statusListeners.clone();
      v.removeElement(l);
      statusListeners = v;
    }
  }

  /**
   * Adds a {@link gate.event.StatusListener} to the list of listeners for
   * this processing resource
   */
  public synchronized void addStatusListener(StatusListener l) {
    @SuppressWarnings("unchecked")
    Vector<StatusListener> v = statusListeners == null ? new Vector<StatusListener>(2) : (Vector<StatusListener>)statusListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      statusListeners = v;
    }
  }

  /**
   * Notifies all the {@link gate.event.StatusListener}s of a change of status.
   * @param e the message describing the status change
   */
  protected void fireStatusChanged(String e) {
    if (statusListeners != null) {
      Vector<StatusListener> listeners = statusListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
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
    Vector<ProgressListener> v = progressListeners == null ? new Vector<ProgressListener>(2) : (Vector<ProgressListener>)progressListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      progressListeners = v;
    }
  }

  /**
   * Removes a {@link gate.event.ProgressListener} from the list of listeners
   * for this processing resource.
   */
  public synchronized void removeProgressListener(ProgressListener l) {
    if (progressListeners != null && progressListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<ProgressListener> v = (Vector<ProgressListener>)progressListeners.clone();
      v.removeElement(l);
      progressListeners = v;
    }
  }

  /**
   * Notifies all the {@link gate.event.ProgressListener}s of a progress change
   * event.
   * @param e the new value of execution completion
   */
  protected void fireProgressChanged(int e) {
    if (progressListeners != null) {
      Vector<ProgressListener> listeners = progressListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).progressChanged(e);
      }
    }
  }

  /**
   * Notifies all the {@link gate.event.ProgressListener}s of a progress
   * finished.
   */
  protected void fireProcessFinished() {
    if (progressListeners != null) {
      Vector<ProgressListener> listeners = progressListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).processFinished();
      }
    }
  }
  
  /**
   * Get the current values for all of a specified resource's
   * registered runtime parameters.
   */
  public static FeatureMap getRuntimeParameterValues(Resource res)
              throws ResourceInstantiationException {
    ResourceData rData = Gate.getCreoleRegister().get(
            res.getClass().getName());
    if(rData == null)
      throw new ResourceInstantiationException(
              "Could not find CREOLE data for " + res.getClass().getName());

    ParameterList params = rData.getParameterList();

    return AbstractResource.getParameterValues(res,
            params.getRuntimeParameters());
  }
  
  /**
   * Get the current values for all this resource's registered
   * init-time parameters.
   */
  public FeatureMap getRuntimeParameterValues()
              throws ResourceInstantiationException {
    return getRuntimeParameterValues(this);
  }

  /**
   * A progress listener used to convert a 0..100 interval into a smaller one
   */
  protected class IntervalProgressListener implements ProgressListener{
    public IntervalProgressListener(int start, int end){
      this.start = start;
      this.end = end;
    }
    @Override
    public void progressChanged(int i){
      fireProgressChanged(start + (end - start) * i / 100);
    }

    @Override
    public void processFinished(){
      fireProgressChanged(end);
    }

    int start;
    int end;
  }//IntervalProgressListener

  /**
   * A simple status listener used to forward the events upstream.
   */
  protected class InternalStatusListener implements StatusListener{
    
    public InternalStatusListener() {

    }
    
    @Override
    public void statusChanged(String message){
      fireStatusChanged(message);
    }
  }//InternalStatusListener

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

  protected volatile boolean interrupted = false;
} // class AbstractProcessingResource
