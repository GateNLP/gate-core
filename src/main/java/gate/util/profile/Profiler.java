/*
 *  Profiler.java - A simple profiling utility
 *
 *  Copyright (c) 2001, OntoText Lab. (http://www.ontotext.com)
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Atanas Kiryakov, 05/11/01
 *
 *  $Id: Profiler.java 17601 2014-03-08 18:56:22Z markagreenwood $
 */


package gate.util.profile;

/**
 *  This is a simple "code-internal" profiler which can be used
 *  by introducing calls to its methods (mostly, checkPoint()) in  the code.
 *  It allows detection and reporting of time and memory usage
 *  between execution points as well as for the whole run. In addition allows
 *  for collection/reporting of the resources usage by categories (types of
 *  activity performed between the check point and the previous one).
 *  The results are currently just dumped to the output.
 *  <p>
 *  Important feature is the possibility of the memory usage measurment code
 *  to call the Garbage Collector following a specific strategy that allows
 *  the real memory usage to be deteceted. This feature can be switched OFF
 *  if appropriate, for example, if the *real* run-time memory usage have to
 *  be measured rather than the precise but ideallistic one provided otherwise.
 *  <p>
 *  The profiler can be switched OFF using the enable(boolean) method. In such
 *  case it ignores any calls to its checkPoint(...) method, so, basically does
 *  nothing. This feature is usefull in cases when the profiler have to be
 *  "stripped" from the code in order to allow non-disturbed execution.
 *  <p>
 *  The profiler takes care to calculate the time exlusive its footprint. It
 *  takes account for both "net" time and the absolute duration. Also in
 *  relation to the execution time measurment - it depends on the speed of the
 *  machine as well as on the other applications running in parallel.
 */

//import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class Profiler {
  protected static final Logger log = Logger.getLogger(Profiler.class);

  //private PrintStream m_out;
  private boolean m_enabled = true;
  private boolean m_garbageCollection = true;
  /** Indicates whether just to return the string dumps (false) or also print them
   to the std out (true)*/
  private boolean m_doPrintToStdOut = true;

  // keeps the sum of the time spend on a category of tasks
  // the categories are identified via strings which are used as
  // keys in the table. The values in the table are Long objects, millisec.
  private Map<String,Long> m_categorySums;

  // keeps the time spend on the last task of a category
  // the categories are identified via strings which are used as
  // keys in the table. The values in the table are Long objects, millisec.
  private Map<String,Long> m_categoryLasts;

  private Runtime m_rt;

  // all the time constants below in 1/1000s
  // the time when the profiler was intiated for the current run
  private long m_startTime;
  private long m_lastCheckTime, m_profilerTime, m_lastDuration;

  private long m_maxMemory, m_currMemory, m_diffMemory;

  public Profiler() {
    m_rt = Runtime.getRuntime();
    //m_out = System.out;
  }

  /**
   * Switches the profiler ON and OFF. When OFF all the methods do nothing
   */
  public void enable(boolean isEnabled) {
    m_enabled = isEnabled;
  } // enable

  /**
   * Answers is the profiler switched ON or OFF. When OFF all the methods do
   * nothing
   */
  public boolean isEnabled() {
    return m_enabled;
  }

  /**
   * Tell's the profiler whether to call the garbage collector when detecting
   * memory usage or not. If switched ON the GC is called following a
   * specific strategy as many time as needed so to collect all the memory
   * that can be collected. This makes the profiling slower and also does not
   * correctly account for the really memory usage of the applicaton when
   * run without the profiler. On the other hand, with garbage collection, it
   * is easier to detecet the amount of memory really necessary for the
   * application.
   */
  public void enableGCCalling(boolean collect) {
    m_garbageCollection = collect;
  }

  /**
   * @see #enableGCCalling(boolean)
   */
  public boolean isGCCallingEnabled() {
    return m_garbageCollection;
  }


  /**
   * Returns the time spend by the profiler during the last run. It is the
   * case that
   *      net_run_time = run_duration - profiler_time
   */
  public long getProfilerTime() {
    return m_profilerTime;
  }

  /**
   * Returns the time spend in the last run without the time
   * spend by the profiler. It is the case that
   *      net_run_time = run_duration - profiler_time
   */
  public long getNetRunTime() {
    return m_lastCheckTime - m_profilerTime;
  };

  /**
   * Returns the time spend in the current run until the last check-point
   * inclusive the time spend by the profiler. It is the case that
   *      net_run_time = run_duration - profiler_time
   */
  public long getRunDuration() {
    return m_lastCheckTime;
//        long auxTime1 = (System.currentTimeMillis() - m_startTime1000)/10;
//        return ((double)auxTime1)/100;
  } ;

  public long getLastDuration() {
    return m_lastDuration;
  }


  /**
   * Inialises the profiler for a new run
   */
  public String initRun(String runDescription) {
    StringBuffer buf = new StringBuffer();
    buf.append("-----------------------------------------------\n");
    buf.append("New profiler run: " + runDescription);
    buf.append("\n-----------------------------------------------\n");

    m_maxMemory=0;
    m_currMemory=0;
    m_diffMemory=0;
    m_profilerTime=0;
    m_startTime = System.currentTimeMillis();
    m_lastCheckTime=0;
    m_lastDuration=0;

    m_categorySums = new HashMap<String,Long>();
    m_categoryLasts = new HashMap<String,Long>();
    if ( m_doPrintToStdOut ) {
      log.debug(buf.toString());
    }
    return buf.toString();
  } // initRun

  /**
   * To be called at all execution points of interest. Detects the time
   * and memory usage in absolute terms as well as compared to the previous
   * execution point
   */
  public String checkPoint(String execPointDescr) {
    return checkPoint(execPointDescr, new String[0], true, true, true);
  }

  /**
   * In addition to the variant of the method with two parameters allows:
   * a set of categories (identified by strings) to which the preceeding
   * fragment of code belongs; flag determining whether the description of
   * the execution point to be displayed; flag determining whether the
   * statistics to be shown
   */
  public String checkPoint(String execPointDescr, String categories[],
      boolean showDescr, boolean showStats, boolean memoryCheck)
  {
    if (!m_enabled)
      return "";

    long currTime = System.currentTimeMillis() - m_startTime;
    m_lastDuration = currTime - m_lastCheckTime;

    if (memoryCheck) {
      long oldMemory = m_currMemory;

      if (m_garbageCollection) {
        do {
          m_currMemory = m_rt.totalMemory() - m_rt.freeMemory();
          m_rt.gc();
          try {
            wait(300);
          } catch(Exception e) {
            //ignore this as it should never really happen
          }
          m_rt.gc();
        } while (m_currMemory > m_rt.totalMemory() - m_rt.freeMemory());
      }
      else {
        m_currMemory = m_rt.totalMemory() - m_rt.freeMemory();
      }

      m_currMemory /= 1000;
      m_maxMemory = Math.max(m_maxMemory, m_currMemory);
      m_diffMemory = m_currMemory - oldMemory;
    } // if (memoryCheck)

    m_lastCheckTime = System.currentTimeMillis() - m_startTime;
    m_profilerTime += (m_lastCheckTime - currTime);

    checkCategories(categories);
    return showResults(execPointDescr, showDescr, showStats);
  } // checkPoint

  private void checkCategories(String categs[]) {
    int size = categs.length;
    String categ;
    long sum;
    Long l;
    for (int i=0; i<size; i++) {
      categ = categs[i].toUpperCase();
      l = m_categorySums.get(categ);
      sum = (l==null) ? 0 : l.longValue();
      sum += m_lastDuration;
      m_categorySums.put(categ, new Long(sum));
      m_categoryLasts.put(categ, new Long(m_lastDuration));
    } // for
  } // checkCategories

  private String showResults(String execPointDescr, boolean showDescr,
      boolean showStats)
  {
    StringBuffer buff = new StringBuffer(500);
    if (showDescr) {
      buff.append("---------LOG: ");
      buff.append(execPointDescr);
      buff.append("---------");
    }

    if (showStats) {
      buff.append("\nMemory: ");
      buff.append(m_currMemory);
      buff.append("k; change: ");
      buff.append(m_diffMemory);
      buff.append("k; max: ");
      buff.append(m_maxMemory);
      buff.append("k; Net time:   ");
      buff.append(printTime(getNetRunTime()));
      buff.append("; since prev. millisecs: ");
      buff.append(m_lastDuration);
//            buff.append("; profiler time: ");
//            buff.append(printTime(m_profilerTime));
//            buff.append("; duration: ");
//            buff.append(printTime(m_lastCheckTime));
    }

    if (buff.length() > 0 && m_doPrintToStdOut) {
        log.debug(buff.toString());
    }
    return buff.toString();
  } // showResults

  /**
   * Returns 0 if the category was not found
   */
  public long getCategoryTimeSum(String category) {
    Long sum = m_categorySums.get(category.toUpperCase());
    return (sum == null) ? 0 : sum.longValue();
  } // getCategoryTimeSum

  /**
   * Returns 0 if the category was not found
   */
  public long getCategoryTimeLast(String category) {
    Long sum = m_categoryLasts.get(category.toUpperCase());
    return (sum == null) ? 0 : sum.longValue();
  } // getCategoryTimeSum

  /**
   * Prints the time for all the categories of activities
   */
  public void showCategoryTimes() {
    log.debug("Time spent by categories:");
    Iterator<String> categNames = m_categorySums.keySet().iterator();
    String categ;
    while (categNames.hasNext()) {
      categ = categNames.next();
      showCategoryTime(categ);
    } // while
  } // showCategoryTimes

  /**
   * Prints the time for certain category of activities
   */
  public void showCategoryTime(String categ) {
    log.debug(categ + ", sum=" +
            printTime(getCategoryTimeSum(categ)) +
            ", last=" + printTime(getCategoryTimeLast(categ)));
  } // showCategoryTimes

  /**
   * An auxiliary routine printing time in "nnn.nns" format
   */
  public String printTime(long timeMillis) {
    long round = timeMillis/1000;
    long remaind = (timeMillis % 1000)/10;
    StringBuffer buff = new StringBuffer(10);
    buff.append(round);
    buff.append(".");
    buff.append(remaind);
    buff.append("s");
    return buff.toString();
  } // printTime

  /**
   * An auxiliary routine printing in a string speed
   */
  public String printSpeed(long timeMillis,
      double whatever, String whateverMeasure)
  {
    double speed1000 = whatever/ timeMillis;
    long round = (long)(speed1000*1000);
    long remaind =  (long)((speed1000*100000) - 100 * round);
    StringBuffer buff = new StringBuffer(10);
    buff.append(round);
    buff.append(".");
    buff.append(remaind);
    buff.append(whateverMeasure);
    buff.append("/s");
    return buff.toString();
  } // printTime

  /**
   * An auxiliary routine printing time, avg. time, and avg. speed for
   * a category
   */
  public void printCategAvg(String categ, long items,
      double volume, String whateverMeasure)
  {
    long time = getCategoryTimeSum(categ);
    if (time==0) {
      log.debug("Category \"" + categ + "\" not found");
    }

    log.debug("Category \"" + categ + "\",  Time= " +
        printTime(time) + "; avg. time= " +
        printTime(time/items) +  "; speed= " +
        printSpeed(time, volume, whateverMeasure));
  } // printCategAvg

  /**
   * Sets the profiler to print (or not) to the standard output.
   * The default behaviour is - print to std out.
   * @param doPrint whether or not to print to std out.
   */
  public void printToSystemOut(boolean doPrint){
    m_doPrintToStdOut = doPrint;
  } // printToSystemOut(doPrint);
} // Profiler
