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
 *  $Id: ConditionalSerialController.java 19648 2016-10-07 15:42:09Z markagreenwood $
 */

package gate.creole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gate.Factory;
import gate.LanguageAnalyser;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.RunningStrategy.UnconditionalRunningStrategy;
import gate.creole.metadata.CreoleResource;
import gate.event.ControllerEvent;
import gate.util.Benchmark;
import gate.util.Err;

/**
 * Execute a list of PRs serially. For each PR a running strategy is stored
 * which decides whether the PR will be run always, never or upon a condition
 * being satisfied.
 * This controller uses {@link AnalyserRunningStrategy} objects as running
 * strategies and they only work with {@link LanguageAnalyser}s so the PRs that
 * are not analysers will get a default &quot;run always&quot; strategy.
 */
@CreoleResource(name = "Conditional Pipeline",
    comment = "A simple serial controller for conditionally run PRs.",
    helpURL = "http://gate.ac.uk/userguide/sec:developer:cond")
public class ConditionalSerialController extends SerialController
                                         implements ConditionalController{

  private static final long serialVersionUID = -3791943170768459208L;

  public ConditionalSerialController(){
    strategiesList = new ArrayList<RunningStrategy>();
  }

  @Override
  public List<RunningStrategy> getRunningStrategies(){
    return Collections.unmodifiableList(strategiesList);
  }

  /**
   * Set a PR at a specified location.
   * The running strategy defaults to run always.
   * @param index the position for the PR
   * @param pr the PR to be set.
   */
  @Override
  public void add(int index, ProcessingResource pr){
    if(pr instanceof LanguageAnalyser){
      strategiesList.add(index,
                         new AnalyserRunningStrategy((LanguageAnalyser)pr,
                                                      RunningStrategy.RUN_ALWAYS,
                                                      null, null));
    }else{
      strategiesList.add(index, new RunningStrategy.UnconditionalRunningStrategy(pr, true));
    }
    super.add(index, pr);
  }

  /**
   * Add a PR to the end of the execution list.
   * @param pr the PR to be added.
   */
  @Override
  public void add(ProcessingResource pr){
    if(pr instanceof LanguageAnalyser){
      strategiesList.add(new AnalyserRunningStrategy((LanguageAnalyser)pr,
                                                      RunningStrategy.RUN_ALWAYS,
                                                      null, null));
    }else{
      strategiesList.add(new RunningStrategy.UnconditionalRunningStrategy(pr, true));
    }
    super.add(pr);
  }

  @Override
  public ProcessingResource remove(int index){
    ProcessingResource aPr = super.remove (index);
    strategiesList.remove(index);
    fireResourceRemoved(new ControllerEvent(this, 
            ControllerEvent.RESOURCE_REMOVED, aPr));
    return aPr;
  }

  @Override
  public boolean remove(ProcessingResource pr){
    int index = prList.indexOf(pr);
    if(index != -1){
      prList.remove(index);
      strategiesList.remove(index);
      fireResourceRemoved(new ControllerEvent(this, 
              ControllerEvent.RESOURCE_REMOVED, pr));
      return true;
    }
    return false;
  }

  public void setRunningStrategy(int index, AnalyserRunningStrategy strategy){
    strategiesList.set(index, strategy);
  }

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
  @Override
  public void setRunningStrategies(Collection<RunningStrategy> strategies){
    strategiesList.clear();
    Iterator<RunningStrategy> stratIter = strategies.iterator();
    while(stratIter.hasNext()) strategiesList.add(stratIter.next());
  }

  /**
   * Executes a {@link ProcessingResource}.
   */
  @Override
  protected void runComponent(int componentIndex) throws ExecutionException{
    ProcessingResource currentPR = prList.get(componentIndex);

    //create the listeners
    //FeatureMap listeners = Factory.newFeatureMap();
    Map<String, Object> listeners = new HashMap<String, Object>();
    listeners.put("gate.event.StatusListener", sListener);
    int componentProgress = 100 / prList.size();
    listeners.put("gate.event.ProgressListener",
                  new IntervalProgressListener(
                          componentIndex * componentProgress,
                          (componentIndex +1) * componentProgress)
                  );

    //add the listeners
    try{
      AbstractResource.setResourceListeners(currentPR, listeners);
    }catch(Exception e){
      // the listeners setting failed; nothing important
      Err.prln("Could not set listeners for " + currentPR.getClass().getName() +
               "\n" + e.toString() + "\n...nothing to lose any sleep over.");
    }


    //run the thing
    if(strategiesList.get(componentIndex).shouldRun()){
      benchmarkFeatures.put(Benchmark.PR_NAME_FEATURE, currentPR.getName());

      long startTime = System.currentTimeMillis();
      // run the thing
      Benchmark.executeWithBenchmarking(currentPR,
              Benchmark.createBenchmarkId(Benchmark.PR_PREFIX + currentPR.getName(),
                      getBenchmarkId()), this, benchmarkFeatures);

      benchmarkFeatures.remove(Benchmark.PR_NAME_FEATURE);
      
      // calculate the time taken by the PR
      long timeTakenByThePR = System.currentTimeMillis() - startTime;
      Long time = prTimeMap.get(currentPR.getName());
      if(time == null) {
        time = 0L;
      }
      time += timeTakenByThePR;
      prTimeMap.put(currentPR.getName(), time);
    }


    //remove the listeners
    try{
      AbstractResource.removeResourceListeners(currentPR, listeners);
    }catch(Exception e){
      // the listeners removing failed; nothing important
      Err.prln("Could not clear listeners for " +
               currentPR.getClass().getName() +
               "\n" + e.toString() + "\n...nothing to lose any sleep over.");
    }
  }//protected void runComponent(int componentIndex)

  /**
   * Cleans the internal data and prepares this object to be collected
   */
  @Override
  public void cleanup(){
    super.cleanup();
    strategiesList.clear();
  }

  /**
   * Custom duplication method for conditional controllers to handle
   * duplicating the running strategies.
   */
  @Override
  public Resource duplicate(Factory.DuplicationContext ctx)
          throws ResourceInstantiationException {
    ConditionalController c = (ConditionalController)super.duplicate(ctx);
    Collection<ProcessingResource> newPRs = c.getPRs();
    List<RunningStrategy> newStrategies = new ArrayList<RunningStrategy>(
            strategiesList.size());
    Iterator<RunningStrategy> oldRSIt = getRunningStrategies().iterator();
    Iterator<ProcessingResource> prIt = newPRs.iterator();
    while(oldRSIt.hasNext()) {
      RunningStrategy oldStrat = oldRSIt.next();
      ProcessingResource currentPR = prIt.next();
      if(oldStrat instanceof AnalyserRunningStrategy) {
        newStrategies.add(new AnalyserRunningStrategy(
                (LanguageAnalyser)currentPR,
                ((AnalyserRunningStrategy)oldStrat).getRunMode(),
                ((AnalyserRunningStrategy)oldStrat).getFeatureName(),
                ((AnalyserRunningStrategy)oldStrat).getFeatureValue()));
      }
      else {
        boolean run = true;
        if(oldStrat instanceof UnconditionalRunningStrategy) {
          run = oldStrat.shouldRun();
        }
        // assume an unconditional strategy.  Subclasses that know about other types
        // of strategies can fix this up later
        newStrategies.add(new RunningStrategy.UnconditionalRunningStrategy(currentPR, run));
      }
    }
    c.setRunningStrategies(newStrategies);
    
    return c;
  }

  /**
   * The list of running strategies for the member PRs.
   */
  protected List<RunningStrategy> strategiesList;
} // class SerialController
