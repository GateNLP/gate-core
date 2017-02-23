/*
 *  Benchmark.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 */

package gate.util;

import gate.Executable;
import gate.creole.ExecutionException;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This class provides methods for making entries in the shared log
 * maintained by the GATE system. User should use various methods
 * provided by this class and as described in the following example.
 * 
 * <p>
 * 
 * TODO: Provide example here.
 * 
 * </p>
 * 
 * @author niraj
 */
public class Benchmark {

  /**
   * variable that keeps track of if logging is ON or OFF.
   */
  protected static boolean benchmarkingEnabled = false;

  /**
   * corpus name feature
   */
  public final static String CORPUS_NAME_FEATURE = "corpusName";

  /**
   * application name feature
   */
  public final static String APPLICATION_NAME_FEATURE = "applicationName";

  /**
   * document name feature
   */
  public final static String DOCUMENT_NAME_FEATURE = "documentName";

  /**
   * processing resource name feature
   */
  public final static String PR_NAME_FEATURE = "prName";

  /**
   * message feature
   */
  public final static String MESSAGE_FEATURE = "message";

  // various check point ids

  public final static String PR_PREFIX = "pr_";

  public final static String DOCUMENT_LOADED = "documentLoaded";

  public final static String DOCUMENT_SAVED = "documentSaved";

  public final static String WRITING_FVS_TO_DISK = "writingFVsToDisk";

  public final static String ANNOTS_TO_NLP_FEATURES = "annotsToNlpFeatures";

  public final static String NLP_FEATURES_TO_FVS = "nlpFeaturesToFVs";

  public final static String READING_LEARNING_INFO = "readingLearningInfo";

  public final static String MODEL_APPLICATION = "modelApplication";

  public final static String WRITING_NGRAM_MODEL = "writingNgramModel";

  public final static String TERM_DOC_STATS = "termDocStats";

  public final static String FILTERING = "filtering";

  public final static String MODEL_TRAINING = "modelTraining";

  public final static String EVALUATION = "evaluation";

  public final static String NLP_LABELS_TO_DATA_LABELS = "nlpLabelsToDataLabels";

  public final static String READING_NLP_FEATURES = "readingNlpFeatures";

  public final static String READING_FVS = "readingFVs";

  public final static String WEKA_MODEL_TRAINING = "wekaModelTraining";

  public final static String PAUM_MODEL_TRAINING = "paumModelTraining";

  public final static String READING_CHUNK_LEARNING_DATA = "readingChunkLearningData";

  public final static String WEKA_MODEL_APPLICATION = "wekaModelApplication";

  public final static String PAUM_MODEL_APPLICATION = "paumModelApplication";

  public final static String POST_PROCESSING = "postProcessing";

  /**
   * Static shared logger used for logging.
   */
  public static final Logger logger = Logger.getLogger(Benchmark.class);

  /**
   * This returns the current system time.
   */
  public static long startPoint() {
    return System.currentTimeMillis();
  }

  /**
   * Like {@link #startPoint()} but also logs a message with the
   * starting time if benchmarking is enabled. This is intended to be
   * used in conjuntion with the three-argument version of checkPoint.
   * 
   * @param benchmarkID the identifier of the process that is just
   *          starting.
   * @return the current time, as logged.
   */
  public static long startPoint(String benchmarkID) {
    long time = startPoint();
    if(benchmarkingEnabled) {
      logger.info(time + " START " + benchmarkID);
    }
    return time;
  }

  /**
   * This method is responsible for making entries into the log.
   * 
   * @param startTime - when did the actual process started. This value
   *          should be the value obtained by Benchmark.startPoint()
   *          method invoked at the begining of the process.
   * @param benchmarkID - a unique ID of the resource that should be
   *          logged with this message.
   * @param objectInvokingThisCheckPoint - The benchmarkable object that
   *          invokes this method.
   * @param benchmarkingFeatures - any features (key-value pairs) that should be
   *          reported in the log message. toString() method will be
   *          invoked on the objects.
   */
  public static void checkPoint(long startTime, String benchmarkID,
          Object objectInvokingThisCheckPoint, Map<Object,Object> benchmarkingFeatures) {

    // check if logging is disabled
    if(!benchmarkingEnabled) return;

    // we calculate processEndTime here as we don't want to consider
    // the time to convert featureMapToString
    long processingTime = System.currentTimeMillis() - startTime;

    logCheckPoint(String.valueOf(processingTime), benchmarkID,
            objectInvokingThisCheckPoint, benchmarkingFeatures);
  }
  
  /**
   * This method is responsible for making entries into the log.
   * 
   * @param totalTime - Total time consumed by the process
   * @param benchmarkID - a unique ID of the resource that should be
   *          logged with this message.
   * @param objectInvokingThisCheckPoint - The benchmarkable object that
   *          invokes this method.
   * @param benchmarkingFeatures - any features (key-value pairs) that should be
   *          reported in the log message. toString() method will be
   *          invoked on the objects.
   */
  public static void checkPointWithDuration(long totalTime, String benchmarkID,
          Object objectInvokingThisCheckPoint, Map<Object,Object> benchmarkingFeatures) {

    // check if logging is disabled
    if(!benchmarkingEnabled) return;

    logCheckPoint(String.valueOf(totalTime), benchmarkID,
            objectInvokingThisCheckPoint, benchmarkingFeatures);
  }

  /**
   * Logs the end of a process. There must previously have been a call
   * to {@link #startPoint(String)} with the same benchmark ID.
   * 
   * @see #checkPoint(long, String, Object, Map)
   */
  public static void checkPoint(String benchmarkID,
          Object objectInvokingThisCheckPoint, Map<Object,Object> benchmarkingFeatures) {
    if(!benchmarkingEnabled) return;
    logCheckPoint("END", benchmarkID, objectInvokingThisCheckPoint,
            benchmarkingFeatures);
  }

  /**
   * Private method to create a line in the benchmark log.
   * 
   * @param processingTimeOrFlag either the duration of the task in ms
   *          or the string "END" if no start time was provided.
   */
  private static void logCheckPoint(String processingTimeOrFlag,
          String benchmarkID, Object objectInvokingThisCheckPoint,
          Map<Object,Object> benchmarkingFeatures) {
    // finally build the string to be logged
    StringBuilder messageToLog = new StringBuilder();
    messageToLog.append("" + System.currentTimeMillis() + " ");
    messageToLog.append(processingTimeOrFlag + " " + benchmarkID + " "
            + objectInvokingThisCheckPoint.getClass().getName() + " ");

    if(benchmarkingFeatures == null) {
      messageToLog.append("{}");
    }
    else {
      messageToLog.append(benchmarkingFeatures.toString().replaceAll("\n", ""))
            .append("\n");
    }
    logger.info(messageToLog.toString());
  }

  /**
   * Helper method to generate the benchmark ID.
   */
  public static String createBenchmarkId(String resourceName,
          String parentBenchmarkID) {
    if(parentBenchmarkID != null) {
      if(resourceName != null) {
        return (parentBenchmarkID + "." + resourceName.replaceAll("\\.","_")).replaceAll("[ ]+", "_");
      }
      else {
        return (parentBenchmarkID + ".null").replaceAll("[ ]+", "_");
      }
    }
    else {
      if(resourceName != null) {
        return resourceName.replaceAll("[ .]+", "_");
      }
      else {
        return "null";
      }
    }

  }

  /**
   * Returns if the logging is enabled.
   */
  public static boolean isBenchmarkingEnabled() {
    return benchmarkingEnabled;
  }

  /**
   * Enables or disables the logging.
   * 
   * @param benchmarkingEnabled
   */
  public static void setBenchmarkingEnabled(boolean benchmarkingEnabled) {
    Benchmark.benchmarkingEnabled = benchmarkingEnabled;
  }

  /**
   * Executes the given {@link Executable}, logging its runtime under
   * the given benchmark ID (which is propagated to the Executable if it
   * is itself {@link Benchmarkable}).
   * 
   * @param executable the object to execute
   * @param benchmarkID the benchmark ID, which must not contain spaces
   *  as it is already used as a separator in the log, you can use
   * {@link #createBenchmarkId(String, String)} for it. 
   * @param objectInvokingThisCheckPoint the object invoking this method
   *          (typically the caller would pass <code>this</code> here)
   * @param benchmarkingFeatures features to include in the check point
   *          log
   * @throws ExecutionException any exceptions thrown by the underlying
   *           Executable are propagated.
   */
  public static void executeWithBenchmarking(Executable executable,
          String benchmarkID, Object objectInvokingThisCheckPoint,
          Map<Object,Object> benchmarkingFeatures) throws ExecutionException {
    if(!benchmarkingEnabled) {
      executable.execute();
    }
    else {
      long startTime = startPoint();
      String savedBenchmarkID = null;
      try {
        if(executable instanceof Benchmarkable) {
          savedBenchmarkID = ((Benchmarkable)executable).getBenchmarkId();
          ((Benchmarkable)executable).setBenchmarkId(benchmarkID);
        }

        executable.execute();
      }
      catch(Exception e) {
        Map<Object,Object> tempFeatures = new HashMap<Object,Object>();
        if(benchmarkingFeatures != null) {
          tempFeatures.putAll(benchmarkingFeatures);
        }
        tempFeatures.put("exceptionThrown", e);
        checkPoint(startTime, benchmarkID, objectInvokingThisCheckPoint,
                tempFeatures);
        if(e instanceof ExecutionException) {
          throw (ExecutionException)e;
        }
        else {
          throw (RuntimeException)e;
        }
      }
      finally {
        if(savedBenchmarkID != null) {
          ((Benchmarkable)executable).setBenchmarkId(savedBenchmarkID);
        }
      }

      // succeeded, so log checkpoint with the original features
      checkPoint(startTime, benchmarkID, objectInvokingThisCheckPoint,
              benchmarkingFeatures);
    }
  }
}
