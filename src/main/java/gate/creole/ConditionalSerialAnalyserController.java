/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 08/10/2001
 *
 *  $Id: ConditionalSerialAnalyserController.java 19158 2016-03-30 18:29:41Z johann_p $
 *
 */

package gate.creole;

import gate.Controller;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.ProcessingResource;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.event.CreoleEvent;
import gate.util.Benchmark;
import gate.util.GateRuntimeException;
import gate.util.Out;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements a SerialController that only contains
 * {@link gate.LanguageAnalyser}s.
 * It has a {@link gate.Corpus} and its execute method runs all the analysers in
 * turn over each of the documents in the corpus.
 * This is a copy of the {@link SerialAnalyserController}, the only difference
 * being that it inherits from {@link ConditionalSerialController} rather than
 * from {@link SerialController} which makes it a <b>conditional</b> serial
 * analyser controller.
 * <p>
 * NOTE: if at the time when execute() is invoked, the document is not null,
 * it is assumed that this controller is invoked from another controller and
 * only this document is processed while the corpus (which must still be
 * non-null) is ignored. Also, if the document is not null, the CorpusAwarePRs
 * are not notified at the beginning, end, or abnormal termination of the pipeline. 
 * <p>
 * If the document is null, all documents in the corpus
 * are processed in sequence and CorpusAwarePRs are notified
 * before the processing of the documents and after all documents
 * have been processed or an abnormal termination occurred.
 * 
 */
@CreoleResource(name = "Conditional Corpus Pipeline",
    comment = "A serial controller for conditionally run PR pipelines "
        + "over corpora.",
    helpURL = "http://gate.ac.uk/userguide/sec:developer:cond")
public class ConditionalSerialAnalyserController
       extends ConditionalSerialController
       implements CorpusController, LanguageAnalyser, ControllerAwarePR {

  private static final long serialVersionUID = -2328353583769147103L;


  /** Debug flag */
  private static final boolean DEBUG = false;

  
  /**
   * @return the document
   */
  @Override
  public Document getDocument() {
    return document;
  }

  /**
   * @param document the document to set
   */
  @Override
  @Optional
  @RunTime
  @CreoleParameter
  public void setDocument(Document document) {
    this.document = document;
  }
  
  @Override
  public gate.Corpus getCorpus() {
    return corpus;
  }

  @Override
  public void setCorpus(gate.Corpus corpus) {
    this.corpus = corpus;
  }

  protected boolean runningAsSubPipeline = false;
  
  @Override
  public void execute() throws ExecutionException {

    // Our assumption of if we run as a subpipeline of another corpus pipeline or
    // not is based on whether or not the document is null or not:
    if(document != null) {
      runningAsSubPipeline = true;
    } else {
      runningAsSubPipeline = false;
    }
    // inform ControllerAware PRs that execution has started, but only if we are not
    // running as a subpipeline of another corpus pipeline.
    if(!runningAsSubPipeline) {
      if(controllerCallbacksEnabled) {
        invokeControllerExecutionStarted();
      }
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
        if(!runningAsSubPipeline) {
          if(controllerCallbacksEnabled) {
            invokeControllerExecutionFinished();
          }
        }
      }
      else {
        // aborted
        if(!runningAsSubPipeline) {
          if(controllerCallbacksEnabled) {
            invokeControllerExecutionAborted(thrown);
          }
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
  
  

  /** Run the Processing Resources in sequence. */
  @Override
  protected void executeImpl() throws ExecutionException{
    interrupted = false;
    if(corpus == null) throw new ExecutionException(
      "(ConditionalSerialAnalyserController) \"" + getName() + "\":\n" +
      "The corpus supplied for execution was null!");
    
    benchmarkFeatures.put(Benchmark.CORPUS_NAME_FEATURE, corpus.getName());
    
    
    if(document == null){
      //running as a top-level controller -> execute over all documents in 
      //sequence
      // iterate through the documents in the corpus
      for(int i = 0; i < corpus.size(); i++) {
        String savedBenchmarkId = getBenchmarkId();
        try {
          if(isInterrupted()) {
            throw new ExecutionInterruptedException("The execution of the "
              + getName() + " application has been abruptly interrupted!");
          }
  
          boolean docWasLoaded = corpus.isDocumentLoaded(i);
  
          // record the time before loading the document
          long documentLoadingStartTime = Benchmark.startPoint();
  
          Document doc = corpus.get(i);
  
          // include the document name in the benchmark ID for sub-events
          setBenchmarkId(Benchmark.createBenchmarkId("doc_" + doc.getName(),
                  getBenchmarkId()));
          // report the document loading
          benchmarkFeatures.put(Benchmark.DOCUMENT_NAME_FEATURE, doc.getName());
          Benchmark.checkPoint(documentLoadingStartTime,
                  Benchmark.createBenchmarkId(Benchmark.DOCUMENT_LOADED,
                          getBenchmarkId()), this, benchmarkFeatures);
  
          // run the system over this document
          // set the doc and corpus
          for(int j = 0; j < prList.size(); j++) {
            ((LanguageAnalyser)prList.get(j)).setDocument(doc);
            ((LanguageAnalyser)prList.get(j)).setCorpus(corpus);
          }
  
          try {
            if(DEBUG)
              Out.pr("SerialAnalyserController processing doc=" + doc.getName()
                + "...");
  
            super.executeImpl();
            if(DEBUG) Out.prln("done.");
          }
          finally {
            // make sure we unset the doc and corpus even if we got an exception
            for(int j = 0; j < prList.size(); j++) {
              ((LanguageAnalyser)prList.get(j)).setDocument(null);
              ((LanguageAnalyser)prList.get(j)).setCorpus(null);
            }
          }
  
          if(!docWasLoaded) {
            long documentSavingStartTime = Benchmark.startPoint();
            // trigger saving
            corpus.unloadDocument(doc);
            Benchmark.checkPoint(documentSavingStartTime,
                    Benchmark.createBenchmarkId(Benchmark.DOCUMENT_SAVED,
                            getBenchmarkId()), this, benchmarkFeatures);
            
            // close the previously unloaded Doc
            Factory.deleteResource(doc);
          }
        }
        finally {
          setBenchmarkId(savedBenchmarkId);
        }
      }      
    }else{
      //document is set, so we run as a contained controller (i.e. as a compound
      //Language Analyser
      // run the system over this document
      // set the doc and corpus
      for(int j = 0; j < prList.size(); j++) {
        ((LanguageAnalyser)prList.get(j)).setDocument(document);
        ((LanguageAnalyser)prList.get(j)).setCorpus(corpus);
      }

      try {
        if(DEBUG)
          Out.pr("SerialAnalyserController processing doc=" + document.getName()
            + "...");

        super.executeImpl();
        if(DEBUG) Out.prln("done.");
      }
      finally {
        // make sure we unset the doc and corpus even if we got an exception
        for(int j = 0; j < prList.size(); j++) {
          ((LanguageAnalyser)prList.get(j)).setDocument(null);
          ((LanguageAnalyser)prList.get(j)).setCorpus(null);
        }
      }
    }//document was not null

    
    
//    //iterate through the documents in the corpus
//    for(int i = 0; i < corpus.size(); i++){
//      if(isInterrupted()) throw new ExecutionInterruptedException(
//        "The execution of the " + getName() +
//        " application has been abruptly interrupted!");
//      
//      boolean docWasLoaded = corpus.isDocumentLoaded(i);
//      
//      // record the time before loading the document
//      long documentLoadingStartTime = Benchmark.startPoint();
//
//      Document doc = (Document)corpus.get(i);
//
//      // report the document loading
//      benchmarkFeatures.put(Benchmark.DOCUMENT_NAME_FEATURE, doc.getName());
//      Benchmark.checkPoint(documentLoadingStartTime,
//              Benchmark.createBenchmarkId(Benchmark.DOCUMENT_LOADED,
//                      getBenchmarkId()), this, benchmarkFeatures);
//      //run the system over this document
//      //set the doc and corpus
//      for(int j = 0; j < prList.size(); j++){
//        ((LanguageAnalyser)prList.get(j)).setDocument(doc);
//        ((LanguageAnalyser)prList.get(j)).setCorpus(corpus);
//      }
//
//      try{
//        if (DEBUG) 
//          Out.pr("ConditionalSerialAnalyserController processing doc=" + doc.getName()+ "...");      
//        super.executeImpl();
//        if (DEBUG) 
//          Out.prln("done.");      
//      }
//      finally {
//        // make sure we unset the doc and corpus even if we got an exception
//        for(int j = 0; j < prList.size(); j++){
//          ((LanguageAnalyser)prList.get(j)).setDocument(null);
//          ((LanguageAnalyser)prList.get(j)).setCorpus(null);
//        }
//      }
//
//      if(!docWasLoaded){
//        long documentSavingStartTime = Benchmark.startPoint();
//        // trigger saving
//        corpus.unloadDocument(doc);
//        Benchmark.checkPoint(documentSavingStartTime,
//                Benchmark.createBenchmarkId(Benchmark.DOCUMENT_SAVED,
//                        getBenchmarkId()), this, benchmarkFeatures);
//        //close the previoulsy unloaded Doc
//        Factory.deleteResource(doc);
//      }
//    }
  }

  /**
   * Overidden from {@link SerialController} to only allow
   * {@link LanguageAnalyser}s as components.
   */
  @Override
  public void add(ProcessingResource pr){
    checkLanguageAnalyser(pr);
    super.add(pr);
  }
  
  /**
   * Overidden from {@link SerialController} to only allow
   * {@link LanguageAnalyser}s as components.
   */
  @Override
  public void add(int index, ProcessingResource pr) {
    checkLanguageAnalyser(pr);
    super.add(index, pr);
  }

  /**
   * Throw an exception if the given processing resource is not
   * a LanguageAnalyser.
   */
  protected void checkLanguageAnalyser(ProcessingResource pr) {
    if(!(pr instanceof LanguageAnalyser)) {
      throw new GateRuntimeException(getClass().getName() +
                                     " only accepts " +
                                     LanguageAnalyser.class.getName() +
                                     "s as components\n" +
                                     pr.getClass().getName() +
                                     " is not!");
    }
  }
  
  /**
   * Sets the current document to the memeber PRs
   */
  protected void setDocToPrs(Document doc){
    Iterator<ProcessingResource> prIter = getPRs().iterator();
    while(prIter.hasNext()){
      ProcessingResource pr = prIter.next();
      
      // This is a bug fix, found by playing with the generics, as the
      // old version cast everything to be a LanguageAnalyser even
      // though not every ProcessingResource is one
      if (pr instanceof LanguageAnalyser)
        ((LanguageAnalyser)pr).setDocument(doc);
    }
  }


  /**
   * Checks whether all the contained PRs have all the required runtime
   * parameters set. Ignores the corpus and document parameters as these will
   * be set at run time.
   *
   * @return a {@link List} of {@link ProcessingResource}s that have required
   * parameters with null values if they exist <tt>null</tt> otherwise.
   * @throws {@link ResourceInstantiationException} if problems occur while
   * inspecting the parameters for one of the resources. These will normally be
   * introspection problems and are usually caused by the lack of a parameter
   * or of the read accessor for a parameter.
   */
  @Override
  public List<ProcessingResource> getOffendingPocessingResources()
         throws ResourceInstantiationException{
    //take all the contained PRs
    List<ProcessingResource> badPRs = new ArrayList<ProcessingResource>(getPRs());
    //remove the ones that no parameters problems
    Iterator<ProcessingResource> prIter = getPRs().iterator();
    while(prIter.hasNext()){
      ProcessingResource pr = prIter.next();
      ResourceData rData = Gate.getCreoleRegister().
                                              get(pr.getClass().getName());
      //this is a list of lists
      List<List<Parameter>> parameters = rData.getParameterList().getRuntimeParameters();
      //remove corpus and document
      List<List<Parameter>> newParameters = new ArrayList<List<Parameter>>();
      Iterator<List<Parameter>> pDisjIter = parameters.iterator();
      while(pDisjIter.hasNext()){
        List<Parameter> aDisjunction = pDisjIter.next();
        List<Parameter> newDisjunction = new ArrayList<Parameter>(aDisjunction);
        Iterator<Parameter> internalParIter = newDisjunction.iterator();
        while(internalParIter.hasNext()){
          Parameter parameter = internalParIter.next();
          if(parameter.getName().equals("corpus") ||
             parameter.getName().equals("document")) internalParIter.remove();
        }
        if(!newDisjunction.isEmpty()) newParameters.add(newDisjunction);
      }

      if(AbstractResource.checkParameterValues(pr, newParameters)){
        badPRs.remove(pr);
      }
    }
    return badPRs.isEmpty() ? null : badPRs;
  }


  protected gate.Corpus corpus;

  
  /**
   * The document being processed. This is part of the {@link LanguageAnalyser} 
   * interface, so this value is only used when the controller is used as a 
   * member of another controller.
   */
  protected Document document;
  
  
  /**
   * Overridden to also clean up the corpus value.
   */
  @Override
  public void resourceUnloaded(CreoleEvent e) {
    super.resourceUnloaded(e);    
    if(e.getResource() == corpus){
      setCorpus(null);
    }
  }

  @Override
  public void controllerExecutionStarted(Controller c)
      throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionStarted(c);
        }
      }
    }
    
  }

  @Override
  public void controllerExecutionFinished(Controller c)
      throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionFinished(c);
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(null);
          }
        }
      }
    }       
  }

  @Override
  public void controllerExecutionAborted(Controller c, Throwable t)
      throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionAborted(c,t);
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(null);
          }
        }
      }
    }       
  }
  
   /**
   * Invoke the controllerExecutionStarted method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @throws ExecutionException 
   */
  @Override
  public void invokeControllerExecutionStarted() throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionStarted(this);
        }
      }
    }
  }

   /**
   * Invoke the controllerExecutionFinished method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @throws ExecutionException 
   */
  @Override
  public void invokeControllerExecutionFinished() throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionFinished(this);
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(null);
          }
        }
      }
    }       
    
  }
  
   /**
   * Invoke the controllerExecutionAborted method on this controller and all nested PRs and controllers. 
   * This method is intended to be used after if the automatic invocation of the controller
   * callback methods has been disabled with a call setControllerCallbackEnabled(false).  Normally
   * the callback methods are automatically invoked at the start and end of execute().  
   * @param thrown The Throwable to pass on to the controller callback method.
   * @throws ExecutionException 
   */
  @Override
  public void invokeControllerExecutionAborted(Throwable thrown) throws ExecutionException {
    
    for(int i=0; i<getPRs().size(); i++) {
      ProcessingResource pr = getPRs().get(i);
      if(pr instanceof ControllerAwarePR) {
        if(getRunningStrategies().get(i).getRunMode() != RunningStrategy.RUN_NEVER) {
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(corpus);
          }
          ((ControllerAwarePR)pr).controllerExecutionAborted(this,thrown);
          if(pr instanceof LanguageAnalyser) {
            ((LanguageAnalyser)pr).setCorpus(null);
          }
        }
      }
    }       
  }
  
  
  
}
