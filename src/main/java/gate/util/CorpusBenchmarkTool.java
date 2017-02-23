/*
 *  CorpusBenchmarkTool.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 24/Oct/2001
 *
 *  $Id: CorpusBenchmarkTool.java 17889 2014-04-21 10:39:34Z markagreenwood $
 */

package gate.util;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Controller;
import gate.Corpus;
import gate.CorpusController;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageResource;
import gate.ProcessingResource;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.persist.SerialDataStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CorpusBenchmarkTool {
  private static final String MARKED_DIR_NAME = "marked";
  private static final String CLEAN_DIR_NAME = "clean";
  private static final String CVS_DIR_NAME = "Cvs";
  private static final String PROCESSED_DIR_NAME = "processed";
  private static final String ERROR_DIR_NAME = "err";

  public CorpusBenchmarkTool() {}

  public void initPRs() {
    if (applicationFile == null)
      throw new GateRuntimeException("Application not set!");
    
    try {
      Out.prln("App file is: " + applicationFile.getAbsolutePath());
      application = (Controller) gate.util.persistence.PersistenceManager
                    .loadObjectFromFile(applicationFile);
    }
    catch (Exception ex) {
      throw new GateRuntimeException("Corpus Benchmark Tool:" + ex.getMessage(), ex);
    }
  } //initPRs

  public void unloadPRs() {
    //we have nothing to unload if no PRs are loaded
    if (isMarkedStored)
      return;

  }

  public void execute() {
    execute(startDir);
    if (application != null) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {

          Iterator<ProcessingResource> iter = new ArrayList<ProcessingResource>(application.getPRs()).iterator();
          while (iter.hasNext())
            Factory.deleteResource(iter.next());

          Factory.deleteResource(application);
        }
      });
    }
  }

  public void init() {
    //first read the corpus_tool.properties file from the reference dir
    File propFile = new File(getStartDirectory(),"corpus_tool.properties");
    if (!propFile.exists())
      propFile = new File("corpus_tool.properties");    
    Out.prln("Loading properties from " + propFile.getAbsolutePath());
    if (propFile.exists()) {
      try {
        InputStream inputStream = new FileInputStream(propFile);
        this.configs.load(inputStream);
        String thresholdString = this.configs.getProperty("threshold");
        if (thresholdString != null && !thresholdString.equals("")) {
          thresholdString=thresholdString.trim();
          this.threshold = (new Double(thresholdString)).doubleValue();
          Out.prln("New threshold is: " + this.threshold + "<P>\n");
        }
        String setName = this.configs.getProperty("annotSetName");
        if (setName != null && !setName.equals("")) {
          setName=setName.trim();
          Out.prln("Annotation set in marked docs is: " + setName + " <P>\n");
          this.annotSetName = setName;
        }
        setName = this.configs.getProperty("outputSetName");
        if (setName != null && !setName.equals("")) {
          setName=setName.trim();
          Out.prln("Annotation set in processed docs is: " + setName + " <P>\n");
          this.outputSetName = setName;
        }
        String encodingString = this.configs.getProperty("encoding");
        if (encodingString != null && !encodingString.equals("")) {
          encodingString=encodingString.trim();
          this.documentEncoding = encodingString;
          Out.prln("New encoding is: " + this.documentEncoding + "<P>\n");
        }
        String types = this.configs.getProperty("annotTypes");
        if (types != null && !types.equals("")) {
          types=types.trim();
          Out.prln("Using annotation types from the properties file. <P>\n");
          StringTokenizer strTok = new StringTokenizer(types, ";");
          annotTypes = new ArrayList<String>();
          while (strTok.hasMoreTokens())
            annotTypes.add(strTok.nextToken());
        }
        else {
          annotTypes = new ArrayList<String>();
          annotTypes.add("Organization");
          annotTypes.add("Person");
          annotTypes.add("Date");
          annotTypes.add("Location");
          annotTypes.add("Address");
          annotTypes.add("Money");
          annotTypes.add("Percent");
          annotTypes.add("GPE");
          annotTypes.add("Facility");
        }
        String features = this.configs.getProperty("annotFeatures");
        Set<String> result = new HashSet<String>();
        if (features != null && !features.equals("")) {
          features=features.trim();
          Out.pr("Using annotation features from the properties file. \n");
          java.util.StringTokenizer tok =
              new java.util.StringTokenizer(features, ";");
          String current;
          while (tok.hasMoreTokens()) {
            current = tok.nextToken();
            result.add(current);
          } // while
        }
        diffFeaturesSet = result;
        Out.prln("Features: " + diffFeaturesSet + " <P>\n");

      }
      catch (IOException ex) {
        throw new GateRuntimeException("Error loading " + propFile.getAbsolutePath(), ex);
      }
    }
    else {
      Err.prln(propFile.getAbsolutePath() + " does not exist, using default settings");
      this.configs = new Properties();
    }

    //we only initialise the PRs if they are going to be used
    //for processing unprocessed documents
    if (!this.isMarkedStored)
      initPRs();

  }

  public void execute(File dir) {
    if (dir == null)
      return;
    //first set the current directory to be the given one
    currDir = dir;

    File processedDir = null;
    File cleanDir = null;
    File markedDir = null;
    File errorDir = null;

    List<File> subDirs = new ArrayList<File>();
    File[] dirArray = currDir.listFiles();
    if (dirArray == null)return;
    for (int i = 0; i < dirArray.length; i++) {
      if (dirArray[i].isFile() || dirArray[i].getName().equals(CVS_DIR_NAME))
        continue;
      if (dirArray[i].getName().equals(CLEAN_DIR_NAME))
        cleanDir = dirArray[i];
      else if (dirArray[i].getName().equals(MARKED_DIR_NAME))
        markedDir = dirArray[i];
      else if (dirArray[i].getName().equals(PROCESSED_DIR_NAME))
        processedDir = dirArray[i];
      else if (dirArray[i].getName().equals(ERROR_DIR_NAME))
        errorDir = dirArray[i];
      else
        subDirs.add(dirArray[i]);
    }

    if (cleanDir == null)return;
    Out.prln("Processing directory: " + currDir + "<P>");

    if (this.isGenerateMode)
      generateCorpus(cleanDir, processedDir);
    else
      evaluateCorpus(cleanDir, processedDir, markedDir, errorDir);

      //if no more subdirs left, return
    if (subDirs.isEmpty())
      return;

    //there are more subdirectories to traverse, so iterate through
    for (int j = 0; j < subDirs.size(); j++)
      execute(subDirs.get(j));

  } //execute(dir)

  public static void main(String[] args) throws GateException {
    Out.prln("<HTML>");
    Out.prln("<HEAD>");
    Out.prln("<TITLE> Corpus benchmark tool: ran with args ");
    for (int argC = 0; argC < args.length; ++argC)
      Out.pr(args[argC] + " ");
    Out.pr(" on " + new Date() + "</TITLE> </HEAD>");
    Out.prln("<BODY>");
    Out.prln("Please wait while GATE tools are initialised. <P>");
    // initialise GATE
    Gate.init();

    CorpusBenchmarkTool corpusTool = new CorpusBenchmarkTool();

    if (args.length < 1)throw new GateException(usage);
    int i = 0;
    while (i < args.length && args[i].startsWith("-")) {
      if (args[i].equals("-generate")) {
        Out.prln("Generating the corpus... <P>");
        corpusTool.setGenerateMode(true);
      }
      else if (args[i].equals("-marked_clean")) {
        Out.prln("Evaluating current grammars against human-annotated...<P>");
        corpusTool.setMarkedClean(true);
      }
      else if (args[i].equals("-marked_stored")) {
        Out.prln("Evaluating stored documents against human-annotated...<P>");
        corpusTool.setMarkedStored(true);
      }
      else if (args[i].equals("-marked_ds")) {
        Out.prln("Looking for marked docs in a datastore...<P>");
        corpusTool.setMarkedDS(true);
      }
      else if (args[i].equals("-verbose")) {
        Out.prln("Running in verbose mode. Will generate annotation " +
                 "information when precision/recall are lower than " +
                 corpusTool.getThreshold() + "<P>");
        corpusTool.setVerboseMode(true);
      }
      else if (args[i].equals("-moreinfo")) {
        Out.prln("Show more details in document table...<P>");
        corpusTool.setMoreInfo(true);
      }
      i++; //just ignore the option, which we do not recognise
    } //while

    String dirName = args[i];
    File dir = new File(dirName);
    if (!dir.isDirectory())
      throw new GateException(usage);

    //get the last argument which is the application
    i++;
    String appName = args[i];
    File appFile = new File(appName);
    if (!appFile.isFile())
      throw new GateException(usage);
    else
      corpusTool.setApplicationFile(appFile);

    corpusTool.init();
    corpusWordCount = 0;

    Out.prln("Measuring annotaitions of types: " +
             CorpusBenchmarkTool.annotTypes + "<P>");

    corpusTool.setStartDirectory(dir);
    corpusTool.execute();
    //if we're not generating the corpus, then print the precision and recall
    //statistics for the processed corpus
    if (!corpusTool.getGenerateMode())
      corpusTool.printStatistics();

    Out.prln("<BR>Overall average precision: " + corpusTool.getPrecisionAverage());
    Out.prln("<BR>Overall average recall: " + corpusTool.getRecallAverage());
    Out.prln("<BR>Overall average fMeasure: " + corpusTool.getFMeasureAverage());
    if (corpusWordCount == 0)
      Out.prln("<BR>No Token annotations to count words in the corpus.");
    else
      Out.prln("<BR>Overall word count: " + corpusWordCount);

    if (hasProcessed) {
      Out.prln("<P>Old Processed: ");
      Out.prln("<BR>Overall average precision: "
               + corpusTool.getPrecisionAverageProc());
      Out.prln("<BR>Overall average recall: "
               + corpusTool.getRecallAverageProc());
      Out.prln("<BR>Overall average fMeasure: "
               + corpusTool.getFMeasureAverageProc());
    }
    Out.prln("<BR>Finished! <P>");
    Out.prln("</BODY>");
    Out.prln("</HTML>");

    System.exit(0);

  } //main

  public void setGenerateMode(boolean mode) {
    isGenerateMode = mode;
  } //setGenerateMode

  public boolean getGenerateMode() {
    return isGenerateMode;
  } //getGenerateMode

  public boolean getVerboseMode() {
    return isVerboseMode;
  } //getVerboseMode

  public void setVerboseMode(boolean mode) {
    isVerboseMode = mode;
  } //setVerboseMode

  public void setMoreInfo(boolean mode) {
    isMoreInfoMode = mode;
  } // setMoreInfo

  public boolean getMoreInfo() {
    return isMoreInfoMode;
  } // getMoreInfo

  public void setDiffFeaturesList(Set<String> features) {
    diffFeaturesSet = features;
  } // setDiffFeaturesList

  public Set<String> getDiffFeaturesList() {
    return diffFeaturesSet;
  } // getDiffFeaturesList

  public void setMarkedStored(boolean mode) {
    isMarkedStored = mode;
  } // setMarkedStored

  public boolean getMarkedStored() {
    return isMarkedStored;
  } // getMarkedStored

  public void setMarkedClean(boolean mode) {
    isMarkedClean = mode;
  } //

  public boolean getMarkedClean() {
    return isMarkedClean;
  } //

  public void setMarkedDS(boolean mode) {
    isMarkedDS = mode;
  } //

  public boolean getMarkedDS() {
    return isMarkedDS;
  } //

  public void setApplicationFile(File newAppFile) {
    applicationFile = newAppFile;
  }

  /**
   * Returns the average precision over the entire set of processed documents.
   * <P>
   * If the tool has been evaluating the original documents against the
   * previously-stored automatically annotated ones, then the precision
   * will be the average precision on those two sets. <P>
   * If the tool was run in -marked mode, i.e., was evaluating the stored
   * automatically processed ones against the human-annotated ones, then
   * the precision will be the average precision on those two sets of documents.
   */
  public double getPrecisionAverage() {
    return precisionSum / docNumber;
  }

  /**
   * Returns the average recall over the entire set of processed documents.
   * <P>
   * If the tool has been evaluating the original documents against the
   * previously-stored automatically annotated ones, then the recall
   * will be the average recall on those two sets. <P>
   * If the tool was run in -marked mode, i.e., was evaluating the stored
   * automatically processed ones against the human-annotated ones, then
   * the recall will be the average recall on those two sets of documents.
   */
  public double getRecallAverage() {
    return recallSum / docNumber;
  }

  public double getFMeasureAverage() {
    return fMeasureSum / docNumber;
  }

  /** For processed documents */
  public double getPrecisionAverageProc() {
    return proc_precisionSum / docNumber;
  }

  public double getRecallAverageProc() {
    return proc_recallSum / docNumber;
  }

  public double getFMeasureAverageProc() {
    return proc_fMeasureSum / docNumber;
  }

  public boolean isGenerateMode() {
    return isGenerateMode == true;
  } //isGenerateMode

  public double getThreshold() {
    return threshold;
  }

  public void setThreshold(double newValue) {
    threshold = newValue;
  }

  public File getStartDirectory() {
    return startDir;
  } //getStartDirectory

  public void setStartDirectory(File dir) {
    startDir = dir;
  } //setStartDirectory

  protected void generateCorpus(File fileDir, File outputDir) {
    //1. check if we have input files
    if (fileDir == null)
      return;
    //2. create the output directory or clean it up if needed
    File outDir = outputDir;
    if (outputDir == null) {
      outDir = new File(currDir, PROCESSED_DIR_NAME);
    }
    else {
      // get rid of the directory, coz datastore wants it clean
      if (!Files.rmdir(outDir))
        Out.prln("cannot delete old output directory: " + outDir);
    }
    outDir.mkdir();

    //create the datastore and process each document
    try {
      SerialDataStore sds = new SerialDataStore(outDir.toURI().toURL().toString());
      sds.create();
      sds.open();

      File[] files = fileDir.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (!files[i].isFile())
          continue;
        // create a document
        Out.prln("Processing and storing document: " + files[i].toURI().toURL() + "<P>");

        FeatureMap params = Factory.newFeatureMap();
        params.put(Document.DOCUMENT_URL_PARAMETER_NAME, files[i].toURI().toURL());
        params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, documentEncoding);

        FeatureMap features = Factory.newFeatureMap();
//        Gate.setHiddenAttribute(features, true);

        // create the document
        final Document doc = (Document) Factory.createResource(
            "gate.corpora.DocumentImpl", params, features
            );

        doc.setName(files[i].getName());
        
        processDocument(doc);
        final LanguageResource lr = sds.adopt(doc);
        sds.sync(lr);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            Factory.deleteResource(doc);
            Factory.deleteResource(lr);
          }
        });
      } //for
      sds.close();
    }
    catch (java.net.MalformedURLException ex) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex.getMessage())
        .initCause(ex);
    }
    catch (PersistenceException ex1) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex1.getMessage())
        .initCause(ex1);
    }
    catch (ResourceInstantiationException ex2) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex2.getMessage())
        .initCause(ex2);
    }
  } //generateCorpus

  protected void evaluateCorpus(File fileDir,
                                File processedDir, File markedDir,
                                File errorDir) {
    //1. check if we have input files and the processed Dir
    if (fileDir == null || !fileDir.exists())
      return;
    if (processedDir == null || !processedDir.exists())

      //if the user wants evaluation of marked and stored that's not possible
      if (isMarkedStored) {
        Out.prln("Cannot evaluate because no processed documents exist.");
        return;
      }
      else
        isMarkedClean = true;

        // create the error directory or clean it up if needed
    File errDir = null;
    if (isMoreInfoMode) {
      errDir = errorDir;
      if (errDir == null) {
        errDir = new File(currDir, ERROR_DIR_NAME);
      }
      else {
        // get rid of the directory, coz we wants it clean
        if (!Files.rmdir(errDir))
          Out.prln("cannot delete old error directory: " + errDir);
      }
      Out.prln("Create error directory: " + errDir + "<BR><BR>");
      errDir.mkdir();
    }

    //looked for marked texts only if the directory exists
    boolean processMarked = markedDir != null && markedDir.exists();
    if (!processMarked && (isMarkedStored || isMarkedClean)) {
      Out.prln("Cannot evaluate because no human-annotated documents exist.");
      return;
    }

    if (isMarkedStored) {
      evaluateMarkedStored(markedDir, processedDir, errDir);
      return;
    }
    else if (isMarkedClean) {
      evaluateMarkedClean(markedDir, fileDir, errDir);
      return;
    }

    Document persDoc = null;
    Document cleanDoc = null;
    Document markedDoc = null;

    //open the datastore and process each document
    try {
      //open the data store
      DataStore sds = Factory.openDataStore
                      ("gate.persist.SerialDataStore",
                       processedDir.toURI().toURL().toExternalForm());

      List<String> lrIDs = sds.getLrIds("gate.corpora.DocumentImpl");
      for (int i = 0; i < lrIDs.size(); i++) {
        String docID = lrIDs.get(i);

        //read the stored document
        FeatureMap features = Factory.newFeatureMap();
        features.put(DataStore.DATASTORE_FEATURE_NAME, sds);
        features.put(DataStore.LR_ID_FEATURE_NAME, docID);
        FeatureMap hparams = Factory.newFeatureMap();
//        Gate.setHiddenAttribute(hparams, true);

        persDoc = (Document) Factory.createResource(
            "gate.corpora.DocumentImpl",
            features, hparams);

        if (isMoreInfoMode) {
          StringBuffer errName = new StringBuffer(persDoc.getName());
          errName.replace(
              persDoc.getName().lastIndexOf("."),
              persDoc.getName().length(),
              ".err");
          Out.prln("<H2>" +
                   "<a href=\"err/" + errName.toString() + "\">"
                   + persDoc.getName() + "</a>" + "</H2>");
        }
        else
          Out.prln("<H2>" + persDoc.getName() + "</H2>");

        File cleanDocFile = new File(fileDir, persDoc.getName());
        //try reading the original document from clean
        if (!cleanDocFile.exists()) {
          Out.prln("Warning: Cannot find original document " +
                   persDoc.getName() + " in " + fileDir);
        }
        else {
          FeatureMap params = Factory.newFeatureMap();
          params.put(Document.DOCUMENT_URL_PARAMETER_NAME, cleanDocFile.toURI().toURL());
          params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME,
                     documentEncoding);

          // create the document
          cleanDoc = (Document) Factory.createResource(
              "gate.corpora.DocumentImpl", params, hparams);
          cleanDoc.setName(persDoc.getName());
        }

        //try finding the marked document
        StringBuffer docName = new StringBuffer(persDoc.getName());
        if (!isMarkedDS) {
          docName.replace(
              persDoc.getName().lastIndexOf("."),
              docName.length(),
              ".xml");
          File markedDocFile = new File(markedDir, docName.toString());
          if (!processMarked || !markedDocFile.exists()) {
            Out.prln("Warning: Cannot find human-annotated document " +
                     markedDocFile + " in " + markedDir);
          }
          else {
            FeatureMap params = Factory.newFeatureMap();
            params.put(Document.DOCUMENT_URL_PARAMETER_NAME,
                       markedDocFile.toURI().toURL());
            params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME,
                       documentEncoding);

            // create the document
            markedDoc = (Document) Factory.createResource(
                "gate.corpora.DocumentImpl", params, hparams);
            markedDoc.setName(persDoc.getName());
          }
        }
        else {
          //open marked from a DS
          //open the data store
          DataStore sds1 = Factory.openDataStore
                           ("gate.persist.SerialDataStore",
                            markedDir.toURI().toURL().toExternalForm());

          List<String> lrIDs1 = sds1.getLrIds("gate.corpora.DocumentImpl");
          boolean found = false;
          int k = 0;
          //search for the marked doc with the same name
          while (k < lrIDs1.size() && !found) {
            String docID1 = lrIDs1.get(k);

            //read the stored document
            FeatureMap features1 = Factory.newFeatureMap();
            features1.put(DataStore.DATASTORE_FEATURE_NAME, sds1);
            features1.put(DataStore.LR_ID_FEATURE_NAME, docID1);
            Document tempDoc = (Document) Factory.createResource(
                "gate.corpora.DocumentImpl",
                features1, hparams);
            //check whether this is our doc
            if ( ( (String) tempDoc.getFeatures().get("gate.SourceURL")).
                endsWith(persDoc.getName())) {
              found = true;
              markedDoc = tempDoc;
            }
            else k++;
          }
        }

        evaluateDocuments(persDoc, cleanDoc, markedDoc, errDir);

        if (persDoc != null) {
          final gate.Document pd = persDoc;
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              Factory.deleteResource(pd);
            }
          });
        }
        if (cleanDoc != null) {
          final gate.Document cd = cleanDoc;
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              Factory.deleteResource(cd);
            }
          });
        }
        if (markedDoc != null) {
          final gate.Document md = markedDoc;
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              Factory.deleteResource(md);
            }
          });
        }

      } //for loop through saved docs
      sds.close();
    }
    catch (java.net.MalformedURLException ex) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex.getMessage())
        .initCause(ex);
    }
    catch (PersistenceException ex1) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex1.getMessage())
        .initCause(ex1);
    }
    catch (ResourceInstantiationException ex2) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex2.getMessage())
        .initCause(ex2);
    }

  } //evaluateCorpus

  protected void evaluateMarkedStored(File markedDir, File storedDir,
                                      File errDir) {
    Document persDoc = null;
    Document cleanDoc = null;
    Document markedDoc = null;

    //open the datastore and process each document
    try {
      //open the data store
      DataStore sds = Factory.openDataStore
                      ("gate.persist.SerialDataStore",
                       storedDir.toURI().toURL().toExternalForm());

      List<String> lrIDs = sds.getLrIds("gate.corpora.DocumentImpl");
      for (int i = 0; i < lrIDs.size(); i++) {
        String docID = lrIDs.get(i);

        //read the stored document
        FeatureMap features = Factory.newFeatureMap();
        features.put(DataStore.DATASTORE_FEATURE_NAME, sds);
        features.put(DataStore.LR_ID_FEATURE_NAME, docID);

        FeatureMap hparams = Factory.newFeatureMap();
//        Gate.setHiddenAttribute(hparams, true);

        persDoc = (Document) Factory.createResource(
            "gate.corpora.DocumentImpl",
            features, hparams);

        if (isMoreInfoMode) {
          StringBuffer errName = new StringBuffer(persDoc.getName());
          errName.replace(
              persDoc.getName().lastIndexOf("."),
              persDoc.getName().length(),
              ".err");
          Out.prln("<H2>" +
                   "<a href=\"err/" + errName.toString() + "\">"
                   + persDoc.getName() + "</a>" + "</H2>");
        }
        else
          Out.prln("<H2>" + persDoc.getName() + "</H2>");

        if (!this.isMarkedDS) { //try finding the marked document as file
          StringBuffer docName = new StringBuffer(persDoc.getName());
          docName.replace(
              persDoc.getName().lastIndexOf("."),
              docName.length(),
              ".xml");
          File markedDocFile = new File(markedDir, docName.toString());
          if (!markedDocFile.exists()) {
            Out.prln("Warning: Cannot find human-annotated document " +
                     markedDocFile + " in " + markedDir);
          }
          else {
            FeatureMap params = Factory.newFeatureMap();
            params.put(Document.DOCUMENT_URL_PARAMETER_NAME,
                       markedDocFile.toURI().toURL());
            params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME,
                       documentEncoding);

            // create the document
            markedDoc = (Document) Factory.createResource(
                "gate.corpora.DocumentImpl", params, hparams);
            markedDoc.setName(persDoc.getName());
          } //find marked as file
        }
        else {
          try {
            //open marked from a DS
            //open the data store
            DataStore sds1 = Factory.openDataStore
                             ("gate.persist.SerialDataStore",
                              markedDir.toURI().toURL().toExternalForm());

            List<String> lrIDs1 = sds1.getLrIds("gate.corpora.DocumentImpl");
            boolean found = false;
            int k = 0;
            //search for the marked doc with the same name
            while (k < lrIDs1.size() && !found) {
              String docID1 = lrIDs1.get(k);

              //read the stored document
              FeatureMap features1 = Factory.newFeatureMap();
              features1.put(DataStore.DATASTORE_FEATURE_NAME, sds1);
              features1.put(DataStore.LR_ID_FEATURE_NAME, docID1);
              Document tempDoc = (Document) Factory.createResource(
                  "gate.corpora.DocumentImpl",
                  features1, hparams);
              //check whether this is our doc
              if ( ( (String) tempDoc.getFeatures().get("gate.SourceURL")).
                  endsWith(persDoc.getName())) {
                found = true;
                markedDoc = tempDoc;
              }
              else k++;
            }
          }
          catch (java.net.MalformedURLException ex) {
            Out.prln("Error finding marked directory " +
                     markedDir.getAbsolutePath());
          }
          catch (gate.persist.PersistenceException ex1) {
            Out.prln(
                "Error opening marked as a datastore (-marked_ds specified)");
          }
          catch (gate.creole.ResourceInstantiationException ex2) {
            Out.prln(
                "Error opening marked as a datastore (-marked_ds specified)");
          }
        }

        evaluateDocuments(persDoc, cleanDoc, markedDoc, errDir);
        if (persDoc != null) {
          final gate.Document pd = persDoc;
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              Factory.deleteResource(pd);
            }
          });
        }
        if (markedDoc != null) {
          final gate.Document md = markedDoc;
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              Factory.deleteResource(md);
            }
          });
        }

      } //for loop through saved docs
      sds.close();

    }
    catch (java.net.MalformedURLException ex) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex.getMessage())
        .initCause(ex);
    }
    catch (PersistenceException ex1) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex1.getMessage())
        .initCause(ex1);
    }
    catch (ResourceInstantiationException ex2) {
      throw (GateRuntimeException)
        new GateRuntimeException("CorpusBenchmark: " + ex2.getMessage())
        .initCause(ex2);
    }

  } //evaluateMarkedStored

  protected void evaluateMarkedClean(File markedDir, File cleanDir, File errDir) {
    Document persDoc = null;
    Document cleanDoc = null;
    Document markedDoc = null;

    File[] cleanDocs = cleanDir.listFiles();
    for (int i = 0; i < cleanDocs.length; i++) {
      if (!cleanDocs[i].isFile())
        continue;

      //try reading the original document from clean
      FeatureMap params = Factory.newFeatureMap();
      try {
        params.put(Document.DOCUMENT_URL_PARAMETER_NAME, cleanDocs[i].toURI().toURL());
      }
      catch (java.net.MalformedURLException ex) {
        Out.prln("Cannot create document from file: " +
                 cleanDocs[i].getAbsolutePath());
        continue;
      }
      //params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "");
      params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, documentEncoding);

      FeatureMap hparams = Factory.newFeatureMap();
//      Gate.setHiddenAttribute(hparams, true);

      // create the document
      try {
        cleanDoc = (Document) Factory.createResource(
            "gate.corpora.DocumentImpl", params, hparams, cleanDocs[i].getName());
      }
      catch (gate.creole.ResourceInstantiationException ex) {
        Out.prln("Cannot create document from file: " +
                 cleanDocs[i].getAbsolutePath());
        continue;
      }

      if (isMoreInfoMode) {
        StringBuffer errName = new StringBuffer(cleanDocs[i].getName());
        errName.replace(
            cleanDocs[i].getName().lastIndexOf("."),
            cleanDocs[i].getName().length(),
            ".err");
        Out.prln("<H2>" +
                 "<a href=\"err/" + errName.toString() + "\">"
                 + cleanDocs[i].getName() + "</a>" + "</H2>");
      }
      else
        Out.prln("<H2>" + cleanDocs[i].getName() + "</H2>");

        //try finding the marked document
      if (!isMarkedDS) {
        StringBuffer docName = new StringBuffer(cleanDoc.getName());
        docName.replace(
            cleanDoc.getName().lastIndexOf("."),
            docName.length(),
            ".xml");
        File markedDocFile = new File(markedDir, docName.toString());
        if (!markedDocFile.exists()) {
          Out.prln("Warning: Cannot find human-annotated document " +
                   markedDocFile + " in " + markedDir);
          continue;
        }
        else {
          params = Factory.newFeatureMap();
          try {
            params.put(Document.DOCUMENT_URL_PARAMETER_NAME,
                       markedDocFile.toURI().toURL());
          }
          catch (java.net.MalformedURLException ex) {
            Out.prln("Cannot create document from file: " +
                     markedDocFile.getAbsolutePath());
            continue;
          }
          //params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "");
          params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, documentEncoding);

          // create the document
          try {
            markedDoc = (Document) Factory.createResource(
                "gate.corpora.DocumentImpl", params,
                hparams, cleanDoc.getName());
          }
          catch (gate.creole.ResourceInstantiationException ex) {
            Out.prln("Cannot create document from file: " +
                     markedDocFile.getAbsolutePath());
            continue;
          }

        } //if markedDoc exists
      }
      else {
        try {
          //open marked from a DS
          //open the data store
          DataStore sds1 = Factory.openDataStore
                           ("gate.persist.SerialDataStore",
                            markedDir.toURI().toURL().toExternalForm());

          List<String> lrIDs1 = sds1.getLrIds("gate.corpora.DocumentImpl");
          boolean found = false;
          int k = 0;
          //search for the marked doc with the same name
          while (k < lrIDs1.size() && !found) {
            String docID1 = lrIDs1.get(k);

            //read the stored document
            FeatureMap features1 = Factory.newFeatureMap();
            features1.put(DataStore.DATASTORE_FEATURE_NAME, sds1);
            features1.put(DataStore.LR_ID_FEATURE_NAME, docID1);
            Document tempDoc = (Document) Factory.createResource(
                "gate.corpora.DocumentImpl",
                features1, hparams);
            //check whether this is our doc
            if ( ( (String) tempDoc.getFeatures().get("gate.SourceURL")).
                endsWith(cleanDoc.getName())) {
              found = true;
              markedDoc = tempDoc;
            }
            else k++;
          }
        }
        catch (java.net.MalformedURLException ex) {
          Out.prln("Error finding marked directory " +
                   markedDir.getAbsolutePath());
        }
        catch (gate.persist.PersistenceException ex1) {
          Out.prln("Error opening marked as a datastore (-marked_ds specified)");
        }
        catch (gate.creole.ResourceInstantiationException ex2) {
          Out.prln("Error opening marked as a datastore (-marked_ds specified)");
        }
      } //if using a DS for marked

      try {
        evaluateDocuments(persDoc, cleanDoc, markedDoc, errDir);
      }
      catch (gate.creole.ResourceInstantiationException ex) {
        ex.printStackTrace();
        Out.prln("Evaluate failed on document: " + cleanDoc.getName());
      }
      if (persDoc != null) {
        final gate.Document pd = persDoc;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            Factory.deleteResource(pd);
          }
        });
      }
      if (cleanDoc != null) {
        final gate.Document cd = cleanDoc;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            Factory.deleteResource(cd);
          }
        });
      }
      if (markedDoc != null) {
        final gate.Document md = markedDoc;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            Factory.deleteResource(md);
          }
        });
      }

    } //for loop through clean docs

  } //evaluateMarkedClean

  protected void processDocument(Document doc) {
    try {
      if (application instanceof CorpusController) {
        Corpus tempCorpus = Factory.newCorpus("temp");
        tempCorpus.add(doc);
        ( (CorpusController) application).setCorpus(tempCorpus);
        application.execute();
        Factory.deleteResource(tempCorpus);
        tempCorpus = null;
      }
      else {
        Iterator<ProcessingResource> iter = application.getPRs().iterator();
        while (iter.hasNext())
          iter.next().setParameterValue("document", doc);
        application.execute();
      }
    }
    catch (ResourceInstantiationException ex) {
      throw (RuntimeException)
        new RuntimeException("Error executing application: "
                                 + ex.getMessage())
        .initCause(ex);
    }
    catch (ExecutionException ex) {
      throw (RuntimeException)
        new RuntimeException("Error executing application: "
                                 + ex.getMessage())
        .initCause(ex);
    }
  }

  protected void evaluateDocuments(Document persDoc,
                                   Document cleanDoc, Document markedDoc,
                                   File errDir) throws
      ResourceInstantiationException {
    if (cleanDoc == null && markedDoc == null)
      return;

    //we've got no types to compare
    if (annotTypes == null || annotTypes.isEmpty())
      return;

    if (cleanDoc != null && !isMarkedStored) {

      processDocument(cleanDoc);

      int wordCount = countWords(cleanDoc);
      if (wordCount == 0)
        Out.prln("<BR>No Token annotations to count words in the document.");
      else
        Out.prln("<BR>Word count: " + wordCount);
      corpusWordCount += wordCount;

      if (!isMarkedClean)
        evaluateAllThree(persDoc, cleanDoc, markedDoc, errDir);
      else
        evaluateTwoDocs(markedDoc, cleanDoc, errDir);

    }
    else
      evaluateTwoDocs(markedDoc, persDoc, errDir);

  }

  /**
   * Count all Token.kind=word annotations in the document
   */
  protected int countWords(Document annotDoc) {
    int count = 0;

    if (annotDoc == null)return 0;
    // check for Token in outputSetName
    AnnotationSet tokens = annotDoc.getAnnotations(outputSetName).get("Token");
    if (tokens == null)return 0;

    Iterator<Annotation> it = tokens.iterator();
    Annotation currAnnotation;
    while (it.hasNext()) {
      currAnnotation = it.next();
      Object feature = currAnnotation.getFeatures().get("kind");
      if (feature != null && "word".equalsIgnoreCase( (String) feature))++count;
    } // while

    return count;
  }

  protected void evaluateAllThree(Document persDoc,
                                  Document cleanDoc, Document markedDoc,
                                  File errDir) throws
      ResourceInstantiationException {
    //first start the table and its header
    printTableHeader();

    // store annotation diff in .err file
    Writer errWriter = null;
    if (isMoreInfoMode && errDir != null) {
      StringBuffer docName = new StringBuffer(cleanDoc.getName());
      docName.replace(
          cleanDoc.getName().lastIndexOf("."),
          docName.length(),
          ".err");
      File errFile = new File(errDir, docName.toString());
      //String encoding = ( (gate.corpora.DocumentImpl) cleanDoc).getEncoding();
      try {
        errWriter = new FileWriter(errFile, false);
        /*
                 if(encoding == null) {
          errWriter = new OutputStreamWriter(
              new FileOutputStream(errFile, false));
                 } else {
          errWriter = new OutputStreamWriter(
              new FileOutputStream(errFile, false), encoding);
                 }*/
      }
      catch (Exception ex) {
        Out.prln("Exception when creating the error file " + errFile + ": "
                 + ex.getMessage());
        errWriter = null;
      }
    }

    for (int jj = 0; jj < annotTypes.size(); jj++) {
      String annotType = annotTypes.get(jj);

      AnnotationDiffer annotDiffer = measureDocs(markedDoc, cleanDoc, annotType);
      //we don't have this annotation type in this document
      if (annotDiffer == null)
        continue;

      //increase the number of processed documents
      docNumber++;
      //add precison and recall to the sums
      updateStatistics(annotDiffer, annotType);

      AnnotationDiffer annotDiffer1 =
          measureDocs(markedDoc, persDoc, annotType);

      Out.prln("<TR>");

      if (isMoreInfoMode && annotDiffer1 != null
          &&
          (annotDiffer1.getPrecisionAverage() != annotDiffer.getPrecisionAverage()
           || annotDiffer1.getRecallAverage() != annotDiffer.getRecallAverage())
          )
        Out.prln("<TD> " + annotType + "_new" + "</TD>");
      else
        Out.prln("<TD> " + annotType + "</TD>");

      if (isMoreInfoMode) {
        if (annotDiffer1 != null) updateStatisticsProc(annotDiffer1, annotType);

        Out.prln("<TD>" + annotDiffer.getCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiffer.getPartiallyCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiffer.getMissing() + "</TD>");
        Out.prln("<TD>" + annotDiffer.getSpurious() + "</TD>");
      }

      Out.prln("<TD>");

      //check the precision first
      if (annotDiffer1 != null) {

        if (annotDiffer1.getPrecisionAverage()
            < annotDiffer.getPrecisionAverage()) {
          Out.prln("<P><Font color=blue> ");
          Out.prln(annotDiffer.getPrecisionAverage());

          if (!isMoreInfoMode) {
            Out.pr("<BR>Precision increase on human-marked from ");
            Out.pr(annotDiffer1.getPrecisionAverage() + " to ");
            Out.prln(annotDiffer.getPrecisionAverage());
          }
          Out.prln(" </Font></P>");
        }
        else if (annotDiffer1.getPrecisionAverage()
                 > annotDiffer.getPrecisionAverage()) {
          Out.prln("<P><Font color=red> ");
          Out.prln(annotDiffer.getPrecisionAverage());

          if (!isMoreInfoMode) {
            Out.pr("<BR>Precision decrease on human-marked from ");
            Out.pr(annotDiffer1.getPrecisionAverage() + " to ");
            Out.prln(annotDiffer.getPrecisionAverage());
          }
          Out.prln(" </Font></P>");
        }
        else
          Out.prln("<P> " + annotDiffer.getPrecisionAverage() +
                   " </P>");
      }
      else
        Out.prln("<P> " + annotDiffer.getPrecisionAverage() + " </P>");

      Out.prln("</TD>");

      Out.prln("<TD>");

      //check the recall now
      if (annotDiffer1 != null) {

        if (annotDiffer1.getRecallAverage() < annotDiffer.getRecallAverage()) {
          Out.prln("<P><Font color=blue> ");
          Out.prln(annotDiffer.getRecallAverage());

          if (!isMoreInfoMode) {
            Out.pr("<BR>Recall increase on human-marked from ");
            Out.pr(annotDiffer1.getRecallAverage() + " to ");
            Out.prln(annotDiffer.getRecallAverage());
          }
          Out.prln(" </Font></P>");
        }
        else if (annotDiffer1.getRecallAverage() > annotDiffer.getRecallAverage()) {
          Out.prln("<P><Font color=red> ");
          Out.prln(annotDiffer.getRecallAverage());

          if (!isMoreInfoMode) {
            Out.pr("<BR>Recall decrease on human-marked from ");
            Out.pr(annotDiffer1.getRecallAverage() + " to ");
            Out.prln(annotDiffer.getRecallAverage());
          }
          Out.prln(" </Font></P>");
        }
        else
          Out.prln("<P> " + annotDiffer.getRecallAverage() + " </P>");
      }
      else
        Out.prln("<P> " + annotDiffer.getRecallAverage() + " </P>");

      Out.prln("</TD>");

      //check the recall now
      if (isVerboseMode) {
        Out.prln("<TD>");
        if (annotDiffer.getRecallAverage() < threshold
            || annotDiffer.getPrecisionAverage() < threshold) {
          printAnnotations(annotDiffer, markedDoc, cleanDoc);
        }
        else {
          Out.prln("&nbsp;");
        }
        Out.prln("</TD>");
      }

      Out.prln("</TR>");

      // show one more table line for processed document
      if (isMoreInfoMode && annotDiffer1 != null
          &&
          (annotDiffer1.getPrecisionAverage() != annotDiffer.getPrecisionAverage()
           || annotDiffer1.getRecallAverage() != annotDiffer.getRecallAverage())
          ) {

        Out.prln("<TR>");
        Out.prln("<TD> " + annotType + "_old" + "</TD>");

        Out.prln("<TD>" + annotDiffer1.getCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiffer1.getPartiallyCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiffer1.getMissing() + "</TD>");
        Out.prln("<TD>" + annotDiffer1.getSpurious() + "</TD>");

        Out.prln("<TD>");
        if (annotDiffer1.getPrecisionAverage() <
            annotDiffer.getPrecisionAverage())

          Out.prln("<P><Font color=blue> " + annotDiffer1.getPrecisionAverage()
                   + "</Font></P>");
        else if (annotDiffer1.getPrecisionAverage() >
                 annotDiffer.getPrecisionAverage())
          Out.prln(
              "<P><Font color=red> " + annotDiffer1.getPrecisionAverage()
              + " </Font></P>");
        else
          Out.prln(annotDiffer1.getPrecisionAverage());

        Out.prln("</TD>");

        Out.prln("<TD>");
        if (annotDiffer1.getRecallAverage() < annotDiffer.getRecallAverage())
          Out.prln("<P><Font color=blue> " + annotDiffer1.getRecallAverage()
                   + " </Font></P>");
        else if (annotDiffer1.getRecallAverage() > annotDiffer.getRecallAverage())
          Out.prln("<P><Font color=red> " + annotDiffer1.getRecallAverage()
                   + " </Font></P>");
        else
          Out.prln(annotDiffer1.getRecallAverage());

        Out.prln("</TD>");

        //check the recall now
        if (isVerboseMode) {
          // create error file and start writing

          Out.prln("<TD>");
        if (annotDiffer.getRecallAverage() < threshold
            || annotDiffer.getPrecisionAverage() < threshold) {
            printAnnotations(annotDiffer, markedDoc, cleanDoc);
          }
          else {
            Out.prln("&nbsp;");
          }
          Out.prln("</TD>");
        }
        Out.prln("</TR>");
      } // if(isMoreInfoMode && annotDiff1 != null)

      if (isMoreInfoMode && errDir != null)
        storeAnnotations(annotType, annotDiffer, markedDoc, cleanDoc, errWriter);
    } //for loop through annotation types
    Out.prln("</TABLE>");

    try {
      if (errWriter != null)
        errWriter.close();
    }
    catch (Exception ex) {
      Out.prln("Exception on close of error file " + errWriter + ": "
               + ex.getMessage());
    }
  } //evaluateAllThree

  protected void evaluateTwoDocs(Document keyDoc, Document respDoc,
                                 File errDir) throws
      ResourceInstantiationException {

    //first start the table and its header
    printTableHeader();

    // store annotation diff in .err file
    Writer errWriter = null;
    if (isMoreInfoMode && errDir != null) {
      StringBuffer docName = new StringBuffer(keyDoc.getName());
      docName.replace(
          keyDoc.getName().lastIndexOf("."),
          docName.length(),
          ".err");
      File errFile = new File(errDir, docName.toString());
      //String encoding = ( (gate.corpora.DocumentImpl) keyDoc).getEncoding();
      try {
        errWriter = new FileWriter(errFile, false);
        /*
                 if(encoding == null) {
          errWriter = new OutputStreamWriter(
              new FileOutputStream(errFile, false));
                 } else {
          errWriter = new OutputStreamWriter(
              new FileOutputStream(errFile, false), encoding);
                 }*/
      }
      catch (Exception ex) {
        Out.prln("Exception when creating the error file " + errFile + ": "
                 + ex.getMessage());
        errWriter = null;
      }
    }

    for (int jj = 0; jj < annotTypes.size(); jj++) {
      String annotType = annotTypes.get(jj);

      AnnotationDiffer annotDiff = measureDocs(keyDoc, respDoc, annotType);
      //we don't have this annotation type in this document
      if (annotDiff == null)
         continue;

      //increase the number of processed documents
      docNumber++;
      //add precison and recall to the sums
      updateStatistics(annotDiff, annotType);

      Out.prln("<TR>");
      Out.prln("<TD>" + annotType + "</TD>");

      if (isMoreInfoMode) {
        Out.prln("<TD>" + annotDiff.getCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiff.getPartiallyCorrectMatches() + "</TD>");
        Out.prln("<TD>" + annotDiff.getMissing() + "</TD>");
        Out.prln("<TD>" + annotDiff.getSpurious() + "</TD>");
      }

      Out.prln("<TD>" + annotDiff.getPrecisionAverage() + "</TD>");
      Out.prln("<TD>" + annotDiff.getRecallAverage() + "</TD>");
      //check the recall now
      if (isVerboseMode) {
        Out.prln("<TD>");
        if (annotDiff.getRecallAverage() < threshold
            || annotDiff.getPrecisionAverage() < threshold) {
          printAnnotations(annotDiff, keyDoc, respDoc);
        }
        else {
          Out.prln("&nbsp;");
        }
        Out.prln("</TD>");
      }
      Out.prln("</TR>");

      if (isMoreInfoMode && errDir != null)
        storeAnnotations(annotType, annotDiff, keyDoc, respDoc, errWriter);
    } //for loop through annotation types
    Out.prln("</TABLE>");

    try {
      if (errWriter != null)
        errWriter.close();
    }
    catch (Exception ex) {
      Out.prln("Exception on close of error file " + errWriter + ": "
               + ex.getMessage());
    }
  } //evaluateTwoDocs

  protected void printTableHeader() {
    Out.prln("<TABLE BORDER=1");
    Out.pr("<TR> <TD><B>Annotation Type</B></TD> ");

    if (isMoreInfoMode)
      Out.pr("<TD><B>Correct</B></TD> <TD><B>Partially Correct</B></TD> "
             + "<TD><B>Missing</B></TD> <TD><B>Spurious<B></TD>");

    Out.pr("<TD><B>Precision</B></TD> <TD><B>Recall</B></TD>");

    if (isVerboseMode)
      Out.pr("<TD><B>Annotations</B></TD>");

    Out.prln("</TR>");
  }

  protected void updateStatistics(AnnotationDiffer annotDiffer,
                                  String annotType) {
    double precisionAverage = ( ( annotDiffer.
                                          getPrecisionLenient() +
                                          annotDiffer.getPrecisionStrict()) /
                               (2.0));
    if (Double.isNaN(precisionAverage)) precisionAverage = 0.0;
    precisionSum += precisionAverage;

    double recallAverage = ( (annotDiffer.getRecallLenient() +
                                       annotDiffer.getRecallStrict()) /
                            (2.0));
    if (Double.isNaN(recallAverage)) recallAverage = 0.0;
    recallSum += recallAverage;

    double fMeasureAverage = ( (annotDiffer.getFMeasureLenient(1.0) +
                                         annotDiffer.getFMeasureStrict(1.0)) /
                              (2.0));
    if (Double.isNaN(fMeasureAverage)) fMeasureAverage = 0.0;
    fMeasureSum += fMeasureAverage;

    Double oldPrecision = precisionByType.get(annotType);
    if (oldPrecision == null)
      precisionByType.put(annotType, new Double(precisionAverage));
    else
      precisionByType.put(annotType,
                          new Double(oldPrecision.doubleValue() + precisionAverage));

    Integer precCount = prCountByType.get(annotType);
    if (precCount == null)
      prCountByType.put(annotType, new Integer(1));
    else
      prCountByType.put(annotType, new Integer(precCount.intValue() + 1));

    Double oldFMeasure = fMeasureByType.get(annotType);
    if (oldFMeasure == null)
      fMeasureByType.put(annotType, new Double(fMeasureAverage));
    else
      fMeasureByType.put(annotType,
                         new Double(oldFMeasure.doubleValue() + fMeasureAverage));

    Integer fCount = fMeasureCountByType.get(annotType);
    if (fCount == null)
      fMeasureCountByType.put(annotType, new Integer(1));
    else
      fMeasureCountByType.put(annotType, new Integer(fCount.intValue() + 1));

    Double oldRecall = recallByType.get(annotType);
    if (oldRecall == null)
      recallByType.put(annotType, new Double(recallAverage));
    else
      recallByType.put(annotType,
                       new Double(oldRecall.doubleValue() + recallAverage));

    Integer recCount = recCountByType.get(annotType);
    if (recCount == null)
      recCountByType.put(annotType, new Integer(1));
    else
      recCountByType.put(annotType, new Integer(recCount.intValue() + 1));

      //Update the missing, spurious, correct, and partial counts
    Long oldMissingNo = missingByType.get(annotType);
    if (oldMissingNo == null)
      missingByType.put(annotType, new Long(annotDiffer.getMissing()));
    else
      missingByType.put(annotType,
                        new Long(oldMissingNo.longValue() +
                                 annotDiffer.getMissing()));

    Long oldCorrectNo = correctByType.get(annotType);
    if (oldCorrectNo == null)
      correctByType.put(annotType, new Long(annotDiffer.getCorrectMatches()));
    else
      correctByType.put(annotType,
                        new Long(oldCorrectNo.longValue() +
                                 annotDiffer.getCorrectMatches()));

    Long oldPartialNo = partialByType.get(annotType);
    if (oldPartialNo == null)
      partialByType.put(annotType,
                        new Long(annotDiffer.getPartiallyCorrectMatches()));
    else
      partialByType.put(annotType,
                        new Long(oldPartialNo.longValue() +
                                 annotDiffer.getPartiallyCorrectMatches()));

    Long oldSpuriousNo = spurByType.get(annotType);
    if (oldSpuriousNo == null)
      spurByType.put(annotType, new Long(annotDiffer.getSpurious()));
    else
      spurByType.put(annotType,
                     new Long(oldSpuriousNo.longValue() +
                              annotDiffer.getSpurious()));
  }

  /**
   * Update statistics for processed documents
   * The same procedure as updateStatistics with different hashTables
   */
  protected void updateStatisticsProc(AnnotationDiffer annotDiffer,
                                      String annotType) {
    hasProcessed = true;
    double precisionAverage = ( (annotDiffer.getPrecisionLenient() +
                                          annotDiffer.getPrecisionStrict()) /
                               (2.0));
    if (Double.isNaN(precisionAverage)) precisionAverage = 0.0;
    proc_precisionSum += precisionAverage;

    double recallAverage = ( (annotDiffer.getRecallLenient() +
                                       annotDiffer.getRecallStrict()) /
                            (2.0));
    if (Double.isNaN(recallAverage)) recallAverage = 0.0;
    proc_recallSum += recallAverage;

    double fMeasureAverage = ( (annotDiffer.getFMeasureLenient(1.0) +
                                         annotDiffer.getFMeasureStrict(1.0)) /
                              (2.0));
    if (Double.isNaN(fMeasureAverage)) fMeasureAverage = 0.0;
    proc_fMeasureSum += fMeasureAverage;

    Double oldPrecision = proc_precisionByType.get(annotType);
    if (oldPrecision == null)
      proc_precisionByType.put(annotType, new Double(precisionAverage));
    else
      proc_precisionByType.put(annotType,
                               new Double(oldPrecision.doubleValue() +
                                          precisionAverage));
    Integer precCount = proc_prCountByType.get(annotType);
    if (precCount == null)
      proc_prCountByType.put(annotType, new Integer(1));
    else
      proc_prCountByType.put(annotType, new Integer(precCount.intValue() + 1));

    Double oldFMeasure = proc_fMeasureByType.get(annotType);
    if (oldFMeasure == null)
      proc_fMeasureByType.put(annotType,
                              new Double(fMeasureAverage));
    else
      proc_fMeasureByType.put(annotType,
                              new Double(oldFMeasure.doubleValue() +
                                         fMeasureAverage));
    Integer fCount = proc_fMeasureCountByType.get(annotType);
    if (fCount == null)
      proc_fMeasureCountByType.put(annotType, new Integer(1));
    else
      proc_fMeasureCountByType.put(annotType, new Integer(fCount.intValue() + 1));

    Double oldRecall = proc_recallByType.get(annotType);
    if (oldRecall == null)
      proc_recallByType.put(annotType,
                            new Double(recallAverage));
    else
      proc_recallByType.put(annotType,
                            new Double(oldRecall.doubleValue() +
                                       recallAverage));
    Integer recCount = proc_recCountByType.get(annotType);
    if (recCount == null)
      proc_recCountByType.put(annotType, new Integer(1));
    else
      proc_recCountByType.put(annotType, new Integer(recCount.intValue() + 1));

      //Update the missing, spurious, correct, and partial counts
    Long oldMissingNo = proc_missingByType.get(annotType);
    if (oldMissingNo == null)
      proc_missingByType.put(annotType, new Long(annotDiffer.getMissing()));
    else
      proc_missingByType.put(annotType,
                             new Long(oldMissingNo.longValue() +
                                      annotDiffer.getMissing()));

    Long oldCorrectNo = proc_correctByType.get(annotType);
    if (oldCorrectNo == null)
      proc_correctByType.put(annotType, new Long(annotDiffer.getCorrectMatches()));
    else
      proc_correctByType.put(annotType,
                             new Long(oldCorrectNo.longValue() +
                                      annotDiffer.getCorrectMatches()));

    Long oldPartialNo = proc_partialByType.get(annotType);
    if (oldPartialNo == null)
      proc_partialByType.put(annotType,
                             new Long(annotDiffer.getPartiallyCorrectMatches()));
    else
      proc_partialByType.put(annotType,
                             new Long(oldPartialNo.longValue() +
                                      annotDiffer.getPartiallyCorrectMatches()));

    Long oldSpuriousNo = proc_spurByType.get(annotType);
    if (oldSpuriousNo == null)
      proc_spurByType.put(annotType, new Long(annotDiffer.getSpurious()));
    else
      proc_spurByType.put(annotType,
                          new Long(oldSpuriousNo.longValue() +
                                   annotDiffer.getSpurious()));
  }

  public void printStatistics() {

    Out.prln("<H2> Statistics </H2>");

    /*
        Out.prln("<H3> Precision </H3>");
        if (precisionByType != null && !precisionByType.isEmpty()) {
          Iterator iter = precisionByType.keySet().iterator();
          while (iter.hasNext()) {
            String annotType = (String) iter.next();
            Out.prln(annotType + ": "
              + ((Double)precisionByType.get(annotType)).doubleValue()
                  /
                  ((Integer)prCountByType.get(annotType)).intValue()
              + "<P>");
          }//while
        }
        Out.prln("Overall precision: " + getPrecisionAverage() + "<P>");

        Out.prln("<H3> Recall </H3>");
        if (recallByType != null && !recallByType.isEmpty()) {
          Iterator iter = recallByType.keySet().iterator();
          while (iter.hasNext()) {
            String annotType = (String) iter.next();
            Out.prln(annotType + ": "
              + ((Double)recallByType.get(annotType)).doubleValue()
                  /
                  ((Integer)recCountByType.get(annotType)).intValue()
              + "<P>");
          }//while
        }

        Out.prln("Overall recall: " + getRecallAverage()
                 + "<P>");
     */
    if (annotTypes == null) {
      Out.prln("No types given for evaluation, cannot obtain precision/recall");
      return;
    }
    Out.prln("<table border=1>");
    Out.prln("<TR> <TD><B>Annotation Type</B></TD> <TD><B>Correct</B></TD>" +
             "<TD><B>Partially Correct</B></TD> <TD><B>Missing</B></TD>" +
             "<TD><B>Spurious</B></TD> <TD><B>Precision</B></TD>" +
             "<TD><B>Recall</B></TD> <TD><B>F-Measure</B></TD> </TR>");
    String annotType;
    for (int i = 0; i < annotTypes.size(); i++) {
      annotType = annotTypes.get(i);
      printStatsForType(annotType);
    } //for
    Out.prln("</table>");
  } // updateStatisticsProc

  protected void printStatsForType(String annotType) {
    long correct =
            (correctByType.get(annotType) == null) ? 0 : correctByType.get(
                    annotType).longValue();
    long partial =
            (partialByType.get(annotType) == null) ? 0 : partialByType.get(
                    annotType).longValue();
    long spurious =
            (spurByType.get(annotType) == null) ? 0 : spurByType.get(annotType)
                    .longValue();
    long missing =
            (missingByType.get(annotType) == null) ? 0 : missingByType.get(
                    annotType).longValue();
    long actual = correct + partial + spurious;
    long possible = correct + partial + missing;
    //precision strict is correct/actual
    //precision is (correct + 0.5 * partially correct)/actual
    double precision = 0d;
    if (actual!=0)
      precision = (correct + 0.5 * partial) / actual;
    
    //recall strict is correct/possible
    double recall = 0d;
    if (possible!=0)
      recall = (correct + 0.5 * partial) / possible;
    
    //F-measure = ( (beta*beta + 1)*P*R ) / ((beta*beta*P) + R)
    double fmeasure = 0d;
    if ((beta * beta * precision) + recall !=0){
      fmeasure =
        ( (beta * beta + 1) * precision * recall)
        /
        ( (beta * beta * precision) + recall);
    }

    long proc_correct = 0;
    long proc_partial = 0;
    long proc_spurious = 0;
    long proc_missing = 0;
    long proc_actual = 0;
    long proc_possible = 0;
    double proc_precision = 0;
    double proc_recall = 0;
    double proc_fmeasure = 0;

    if (hasProcessed) {
      // calculate values for processed
      proc_correct = (proc_correctByType.get(annotType) == null) ? 0 :
                     proc_correctByType.get(annotType).longValue();
      proc_partial = (proc_partialByType.get(annotType) == null) ? 0 :
                     proc_partialByType.get(annotType).longValue();
      proc_spurious = (proc_spurByType.get(annotType) == null) ? 0 :
                      proc_spurByType.get(annotType).longValue();
      proc_missing = (proc_missingByType.get(annotType) == null) ? 0 :
                     proc_missingByType.get(annotType).longValue();
      proc_actual = proc_correct + proc_partial + proc_spurious;
      proc_possible = proc_correct + proc_partial + proc_missing;
      //precision strict is correct/actual
      //precision is (correct + 0.5 * partially correct)/actual
      proc_precision = (proc_correct + 0.5 * proc_partial) / proc_actual;
      //recall strict is correct/possible
      proc_recall = (proc_correct + 0.5 * proc_partial) / proc_possible;
      //F-measure = ( (beta*beta + 1)*P*R ) / ((beta*beta*P) + R)
      proc_fmeasure =
          ( (beta * beta + 1) * proc_precision * proc_recall)
          /
          ( (beta * beta * proc_precision) + proc_recall);

    }

    // output data
    Out.prln("<TR>");
    if (hasProcessed)
      Out.prln("<TD>" + annotType + "_new" + "</TD>");
    else
      Out.prln("<TD>" + annotType + "</TD>");

    Out.prln("<TD>" + correct + "</TD>");
    Out.prln("<TD>" + partial + "</TD>");
    Out.prln("<TD>" + missing + "</TD>");
    Out.prln("<TD>" + spurious + "</TD>");

    String strPrec = (isMoreInfoMode) ?
                     avgPrint(precision, 4)
                     : Double.toString(precision);
    String strRec = (isMoreInfoMode) ?
                    avgPrint(recall, 4)
                    : Double.toString(recall);
    String strFmes = (isMoreInfoMode) ?
                     avgPrint(fmeasure, 4)
                     : Double.toString(fmeasure);

    if (hasProcessed && (precision < proc_precision))
      Out.prln("<TD><Font color=red>" + strPrec + "</TD>");
    else if (hasProcessed && (precision > proc_precision))
      Out.prln("<TD><Font color=blue>" + strPrec + "</TD>");
    else
      Out.prln("<TD>" + strPrec + "</TD>");
    if (hasProcessed && (recall < proc_recall))
      Out.prln("<TD><Font color=red>" + strRec + "</TD>");
    else if (hasProcessed && (recall > proc_recall))
      Out.prln("<TD><Font color=blue>" + strRec + "</TD>");
    else
      Out.prln("<TD>" + strRec + "</TD>");
    Out.prln("<TD>" + strFmes + "</TD>");
    Out.prln("</TR>");

    if (hasProcessed) {
      // output data
      Out.prln("<TR>");
      Out.prln("<TD>" + annotType + "_old" + "</TD>");

      Out.prln("<TD>" + proc_correct + "</TD>");
      Out.prln("<TD>" + proc_partial + "</TD>");
      Out.prln("<TD>" + proc_missing + "</TD>");
      Out.prln("<TD>" + proc_spurious + "</TD>");

      String strProcPrec = (isMoreInfoMode) ?
                           avgPrint(proc_precision, 4)
                           : Double.toString(proc_precision);
      String strProcRec = (isMoreInfoMode) ?
                          avgPrint(proc_recall, 4)
                          : Double.toString(proc_recall);
      String strProcFmes = (isMoreInfoMode) ?
                           avgPrint(proc_fmeasure, 4)
                           : Double.toString(proc_fmeasure);

      if (precision < proc_precision)
        Out.prln("<TD><Font color=red>" + strProcPrec + "</TD>");
      else if (precision > proc_precision)
        Out.prln("<TD><Font color=blue>" + strProcPrec + "</TD>");
      else
        Out.prln("<TD>" + strProcPrec + "</TD>");
      if (recall < proc_recall)
        Out.prln("<TD><Font color=red>" + strProcRec + "</TD>");
      else if (recall > proc_recall)
        Out.prln("<TD><Font color=blue>" + strProcRec + "</TD>");
      else
        Out.prln("<TD>" + strProcRec + "</TD>");
      Out.prln("<TD>" + strProcFmes + "</TD>");
      Out.prln("</TR>");
    }
  } //printStatsForType

  //** Print @param value with @param count digits after decimal point */
  protected String avgPrint(double value, int count) {
    double newvalue;
    double power = Math.pow(10, count);
    newvalue = Math.round(value * power) / power;
    return Double.toString(newvalue);
  }

  private double precisionSumCalc = 0;
  private double recallSumCalc = 0;
  private double fMeasureSumCalc = 0;

  public double getPrecisionAverageCalc() {
    return precisionSumCalc;
  }

  public double getRecallAverageCalc() {
    return recallSumCalc;
  }

  public double getFmeasureAverageCalc() {
    return fMeasureSumCalc;
  }

  protected void calculateAvgTotal() {
    long correct, partial, spurious, missing;
    long correctSum, partialSum, spuriousSum, missingSum;

    if (annotTypes == null) {
      return;
    }
    correctSum = partialSum = spuriousSum = missingSum = 0;

    String annotType;
    for(int i = 0; i < annotTypes.size(); i++) {
      annotType = annotTypes.get(i);
      correct =
              (correctByType.get(annotType) == null) ? 0 : correctByType.get(
                      annotType).longValue();
      partial =
              (partialByType.get(annotType) == null) ? 0 : partialByType.get(
                      annotType).longValue();
      spurious =
              (spurByType.get(annotType) == null) ? 0 : spurByType.get(
                      annotType).longValue();
      missing =
              (missingByType.get(annotType) == null) ? 0 : missingByType.get(
                      annotType).longValue();
      correctSum += correct;
      partialSum += partial;
      spuriousSum += spurious;
      missingSum += missing;
    } // for

    long actual = correctSum + partialSum + spuriousSum;
    long possible = correctSum + partialSum + missingSum;

    if (actual == 0) {
      precisionSumCalc = 0;
    }
    else {
      precisionSumCalc = (correctSum + 0.5 * partialSum) / actual;
    }

    if (possible == 0) {
      recallSumCalc = 0;
    }
    else {
      recallSumCalc = (correctSum + 0.5 * partialSum) / actual;
    }

    if (precisionSumCalc == 0 && recallSumCalc == 0) {
      fMeasureSumCalc = 0;
    }
    else {
      fMeasureSumCalc =
          ( (beta * beta + 1) * precisionSumCalc * recallSumCalc)
          /
          ( (beta * beta * precisionSumCalc) + recallSumCalc);

    }
  } // calculateAvgTotal

  protected AnnotationDiffer measureDocs(
      Document keyDoc, Document respDoc, String annotType) throws
      ResourceInstantiationException {

    if (keyDoc == null || respDoc == null)
      return null;

    if (annotSetName != null
        && keyDoc.getAnnotations(annotSetName).get(annotType) == null)
      return null;
    else if ( (annotSetName == null || annotSetName.equals(""))
             && keyDoc.getAnnotations().get(annotType) == null)
      return null;

    // create an annotation diff
    AnnotationDiffer annotDiffer = new AnnotationDiffer();
    // set the feature names set for annotation differ
    annotDiffer.setSignificantFeaturesSet(diffFeaturesSet);
    // we need to find the sets
    AnnotationSet keys, responses;
    if (annotSetName == null || annotSetName.equals("")) {
      keys = keyDoc.getAnnotations().get(annotType);
      responses = respDoc.getAnnotations().get(annotType);
    }
    else {
      keys = keyDoc.getAnnotations(annotSetName).get(annotType);
      responses = respDoc.getAnnotations(outputSetName).get(annotType);
    }

    // we have annotation sets so call the annotationDiffer
    annotDiffer.calculateDiff(keys, responses);
    
    return annotDiffer;
  } // measureDocs

  protected void storeAnnotations(String type, AnnotationDiffer annotDiffer,
                                  Document keyDoc, Document respDoc,
                                  Writer errFileWriter) {
    if (errFileWriter == null)return; // exit on "no file"

    try {
      // extract and store annotations
      Comparator<Annotation> comp = new OffsetComparator();
      Set<Annotation> sortedSet = new TreeSet<Annotation>(comp);
      Set<Annotation> missingSet =
          annotDiffer.getAnnotationsOfType(AnnotationDiffer.MISSING_TYPE);
      sortedSet.clear();
      sortedSet.addAll(missingSet);
      storeAnnotations(type + ".miss", sortedSet, keyDoc, errFileWriter);
      Set<Annotation> spuriousSet =
          annotDiffer.getAnnotationsOfType(AnnotationDiffer.SPURIOUS_TYPE);
      sortedSet.clear();
      sortedSet.addAll(spuriousSet);
      storeAnnotations(type + ".spur", sortedSet, respDoc, errFileWriter);
      Set<Annotation> partialSet =
          annotDiffer.getAnnotationsOfType(AnnotationDiffer.
                                           PARTIALLY_CORRECT_TYPE);
      sortedSet.clear();
      sortedSet.addAll(partialSet);
      storeAnnotations(type + ".part", sortedSet, respDoc, errFileWriter);
    }
    catch (Exception ex) {
      Out.prln("Exception on close of error file " + errFileWriter + ": "
               + ex.getMessage());
    }
  } // storeAnnotations

  protected void storeAnnotations(String type, Set<Annotation> set, Document doc,
                                  Writer file) throws IOException {

    if (set == null || set.isEmpty())
      return;

    Iterator<Annotation> iter = set.iterator();
    Annotation ann;
    while (iter.hasNext()) {
      ann = iter.next();
      file.write(type);
      file.write(".");
      file.write(doc.getContent().toString().substring(
          ann.getStartNode().getOffset().intValue(),
          ann.getEndNode().getOffset().intValue()));
      file.write(".");
      file.write(ann.getStartNode().getOffset().toString());
      file.write(".");
      file.write(ann.getEndNode().getOffset().toString());
      file.write("\n");
    } //while
  } // storeAnnotations

  protected void printAnnotations(AnnotationDiffer annotDiff,
                                  Document keyDoc, Document respDoc) {
    Out.pr("MISSING ANNOTATIONS in the automatic texts: ");
    Set<Annotation> missingSet =
        annotDiff.getAnnotationsOfType(AnnotationDiffer.MISSING_TYPE);
    printAnnotations(missingSet, keyDoc);
    Out.prln("<BR>");

    Out.pr("SPURIOUS ANNOTATIONS in the automatic texts: ");
    Set<Annotation> spuriousSet =
        annotDiff.getAnnotationsOfType(AnnotationDiffer.SPURIOUS_TYPE);
    printAnnotations(spuriousSet, respDoc);
    Out.prln("</BR>");

    Out.pr("PARTIALLY CORRECT ANNOTATIONS in the automatic texts: ");
    Set<Annotation> partialSet =
        annotDiff.getAnnotationsOfType(AnnotationDiffer.PARTIALLY_CORRECT_TYPE);
    printAnnotations(partialSet, respDoc);
  }

  protected void printAnnotations(Set<Annotation> set, Document doc) {
    if (set == null || set.isEmpty())
      return;

    Iterator<Annotation> iter = set.iterator();
    while (iter.hasNext()) {
      Annotation ann = iter.next();
      Out.prln(
          "<B>" +
          doc.getContent().toString().substring(
          ann.getStartNode().getOffset().intValue(),
          ann.getEndNode().getOffset().intValue()) +
          "</B>: <I>[" + ann.getStartNode().getOffset() +
          "," + ann.getEndNode().getOffset() + "]</I>"
//        + "; features" + ann.getFeatures()
          );
    } //while
  } //printAnnotations

  /**
   * The directory from which we should generate/evaluate the corpus
   */
  private File startDir;
  private File currDir;
  private static List<String> annotTypes;

  private Controller application = null;
  private File applicationFile = null;

  //collect the sum of all precisions and recalls of all docs
  //and the number of docs, so I can calculate the average for
  //the corpus at the end
  private double precisionSum = 0.0;
  private double recallSum = 0.0;
  private double fMeasureSum = 0.0;
  private Map<String,Double> precisionByType = new HashMap<String,Double>();
  private Map<String,Integer> prCountByType = new HashMap<String,Integer>();
  private Map<String,Double> recallByType = new HashMap<String,Double>();
  private Map<String,Integer> recCountByType = new HashMap<String,Integer>();
  private Map<String,Double> fMeasureByType = new HashMap<String,Double>();
  private Map<String,Integer> fMeasureCountByType = new HashMap<String,Integer>();

  private Map<String,Long> missingByType = new HashMap<String,Long>();
  private Map<String,Long> spurByType = new HashMap<String,Long>();
  private Map<String,Long> correctByType = new HashMap<String,Long>();
  private Map<String,Long> partialByType = new HashMap<String,Long>();

  // statistic for processed
  static boolean hasProcessed = false;
  private double proc_precisionSum = 0;
  private double proc_recallSum = 0;
  private double proc_fMeasureSum = 0;
  private Map<String,Double> proc_precisionByType = new HashMap<String,Double>();
  private Map<String,Integer> proc_prCountByType = new HashMap<String,Integer>();
  private Map<String,Double> proc_recallByType = new HashMap<String,Double>();
  private Map<String,Integer> proc_recCountByType = new HashMap<String,Integer>();
  private Map<String,Double> proc_fMeasureByType = new HashMap<String,Double>();
  private Map<String,Integer> proc_fMeasureCountByType = new HashMap<String,Integer>();

  private Map<String,Long> proc_missingByType = new HashMap<String,Long>();
  private Map<String,Long> proc_spurByType = new HashMap<String,Long>();
  private Map<String,Long> proc_correctByType = new HashMap<String,Long>();
  private Map<String,Long> proc_partialByType = new HashMap<String,Long>();

  double beta = 1;

  private int docNumber = 0;

  /**
   * If true, the corpus tool will generate the corpus, otherwise it'll
   * run in evaluate mode
   */
  private boolean isGenerateMode = false;

  /**
   * If true - show annotations for docs below threshold
   */
  private boolean isVerboseMode = false;

  /**
   * If true - show more info in document table
   */
  private boolean isMoreInfoMode = false;

  /**
   * The list of features used in the AnnotationDiff separated by comma
   * Example: "class;inst"
   */
  private Set<String> diffFeaturesSet;

  /**
   * If true, the corpus tool will evaluate stored against the human-marked
   * documents
   */
  private boolean isMarkedStored = false;
  private boolean isMarkedClean = false;

  //whether marked are in a DS, not xml
  private boolean isMarkedDS = false;

  private String annotSetName = "Key";
  private String outputSetName = null;

  private double threshold = 0.5;
  private Properties configs = new Properties();
  private static int corpusWordCount = 0;

  private String documentEncoding = "";

  /** String to print when wrong command-line args */
  private static String usage =
      "usage: CorpusBenchmarkTool [-generate|-marked_stored|-marked_clean] "
      + "[-verbose] [-moreinfo] directory-name application";

}
