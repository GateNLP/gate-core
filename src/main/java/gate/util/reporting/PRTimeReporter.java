/*
 *  PRTimeReporter.java
 *
 *  Copyright (c) 2008-2009, Intelius, Inc. 
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Chirag Viradiya & Andrew Borthwick, 30/Sep/2009
 *
 *  $Id$
 */

package gate.util.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import gate.util.reporting.exceptions.BenchmarkReportExecutionException;
import gate.util.reporting.exceptions.BenchmarkReportInputFileFormatException;
import gnu.getopt.Getopt;

/**
 * A reporter class to generate a report on total time taken by each processing
 * element across corpus.
 */
public class PRTimeReporter implements BenchmarkReportable {
  /**
   * This string constant when set as print media indicates that the report is
   * printed in TEXT format.
   */
  public static final String MEDIA_TEXT = "text";
  /**
   * This string constant when set as print media indicates that the report is
   * printed in HTML format.
   */
  public static final String MEDIA_HTML = "html";
  /**
   * This string constant when set as sort order indicates that the processing
   * elements are sorted in the order of their execution.
   */
  public static final String SORT_EXEC_ORDER = "exec_order";
  /**
   * This string constant when set as sort order indicates that the processing
   * elements are sorted in the descending order of processing time taken by a
   * particular element.
   */
  public static final String SORT_TIME_TAKEN = "time_taken";
  /** A Hashtable storing the time taken by each pipeline. */
  private Hashtable<String, String> globalTotal = new Hashtable<String, String>();
  /** An ArrayList containing the lines to be printed in the final text report. */
  private ArrayList<String> printLines = new ArrayList<String>();
  /** An OS independent line separator. */
  private static final String NL = System.getProperty("line.separator");
  /**
   * A String containing the HTML code to generate collapsible tree for
   * processing elements.
   */
  private String htmlElementTree =
    "<td rowspan=\"112\" width=\"550\">" + NL +
    "<div id=\"treemenu\" style=\"margin: 0;\">" + NL +
    "<ul id=\"tree\" style=\"margin-left: 0;\">" + NL;
  /**
   * A String containing the HTML code to generate collapsible tree for time
   * taken by each processing elements.
   */
  private String htmlTimeTree =
    "<td rowspan=\"112\" width=\"100\">" + NL +
    "<div style=\"margin-top: 0;\"><div>" + NL;
  /**
   * A String containing the HTML code to generate collapsible tree for time
   * taken by each processing elements (in %).
   */
  private String htmlTimeInPercentTree =
    "<td rowspan=\"112\" width=\"100\">" + NL +
    "<div style=\"margin-top: 0;\"><div>" + NL;
  /** A integer to track tree depth level. */
  private int level = 1;
  /** Place holder for storing the total time taken by a pipeline. */
  private double globalValue = 0;
  /** Status flag for normal exit. */
  private static final int STATUS_NORMAL = 0;
  /** Status flag for error exit. */
  private static final int STATUS_ERROR = 1;
  /**
   * An integer containing the count of total valid log entries present in input
   * file provided.
   */
  public int validEntries = 0;
  /** Chunk size in which file will be read. */
  private static final int FILE_CHUNK_SIZE = 2000;
  /**
   * Names of the given pipeline for which the entries are present in given
   * benchmark file.
   */
  public HashSet<String> pipelineNames = new HashSet<String>();

  /** A handle to the input benchmark file (benchmark.txt). */
  private File benchmarkFile = new File("benchmark.txt");
  /** Indicate whether or not to show 0 millisecond entries. */
  private boolean suppressZeroTimeEntries = true;
  /** Report media. */
  private String printMedia = MEDIA_HTML;
  /**
   * A String specifying the sorting order to be used while displaying the
   * report.
   */
  private String sortOrder = SORT_EXEC_ORDER;
  /** Path where to save the report file. */
  private File reportFile;
  /** A marker indicating the start of current logical run. */
  private String logicalStart = null;

  /**
   * No Argument constructor.
   */
  public PRTimeReporter() {
  }

  /**
   * A constructor to be used while executing from the command line.
   *
   * @param args array containing command line arguments.
   */
  PRTimeReporter(String[] args) {
    parseArguments(args);
  }

  /**
   * Stores GATE processing elements and the time taken by them in an in-memory
   * data structure for report generation.
   *
   * @param inputFile
   *          A File handle of the input log file.
   *
   * @return An Object of type LinkedHashMap&lt;String, Object&gt; containing the
   *         processing elements (with time in milliseconds) in hierarchical
   *         structure. Null if there was an error.
   */
  @Override
  public Object store(File inputFile)
      throws BenchmarkReportInputFileFormatException {
    LinkedHashMap<String, Object> globalStore =
      new LinkedHashMap<String, Object>();
    long fromPos = 0;
    RandomAccessFile in = null;
    try {
      if (getLogicalStart() != null) {
        fromPos = tail(inputFile, FILE_CHUNK_SIZE);
      }
      in = new RandomAccessFile(inputFile, "r");
      if (getLogicalStart() != null) {
        in.seek(fromPos);
      }
      ArrayList<String> startTokens = new ArrayList<String>();
      String logEntry;
      String docName = null;
      Pattern pattern = Pattern.compile("(\\d+) (\\d+) (.*) (.*) \\{(.*)\\}");
      while ((logEntry = in.readLine()) != null) {
        Matcher matcher = pattern.matcher(logEntry);
        // Skip the statistics for the event documentLoaded
        if (logEntry.matches(".*documentLoaded.*"))
          continue;
        if (logEntry.matches(".*START.*")) {
          String[] splittedStartEntry = logEntry.split("\\s");
          String startToken = (splittedStartEntry.length > 2) ? splittedStartEntry[2]
              : null;
          if (startToken == null) {
            throw new BenchmarkReportInputFileFormatException(
                getBenchmarkFile().getAbsolutePath() + " is invalid.");
          }
          startTokens.add(startToken);
          if (startToken.endsWith("Start"))
            continue;
          organizeEntries(globalStore, startToken.split("\\."), "0");
        }

        if (matcher != null) {
          if (matcher.matches()) {
            if (validateLogEntry(matcher.group(3), startTokens)) {
              String[] splittedBIDs = matcher.group(3).split("\\.");
              if (splittedBIDs.length > 1) {
                docName = splittedBIDs[1];
                pipelineNames.add(splittedBIDs[0]);
              }
              organizeEntries(globalStore, (matcher.group(3).replaceFirst(
                  Pattern.quote(docName) + ".", "")).split("\\."), matcher.group(2));
            }
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
      globalStore = null;

    } finally {
      try {
        if (in != null) { in.close(); }
      } catch (IOException e) {
        e.printStackTrace();
        globalStore = null;
      }
    }

    if (validEntries == 0) {
      if (logicalStart != null) {
        throw new BenchmarkReportInputFileFormatException(
          "No valid log entries present in " +
          getBenchmarkFile().getAbsolutePath() +
          " does not contain a marker named " + logicalStart + ".");
      } else {
        throw new BenchmarkReportInputFileFormatException(
          "No valid log entries present in " +
          getBenchmarkFile().getAbsolutePath());
      }
    }
    return globalStore;
  }

  /**
   * Generates a tree like structure made up of LinkedHashMap containing the
   * processing elements and time taken by each element totaled at leaf level
   * over corpus.
   *
   * @param store
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param tokens
   *          An array consisting of remaining benchmarkID tokens except the one
   *          being processed.
   * @param bTime
   *          time(in milliseconds) of the benchmarkID token being processed.
   */
  @SuppressWarnings("unchecked")
  private void organizeEntries(LinkedHashMap<String, Object> store,
                               String[] tokens, String bTime) {
      if (tokens.length > 0 && store.containsKey(tokens[0])) {
        if (tokens.length > 1) {
          String[] tempArr = new String[tokens.length - 1];
          System.arraycopy(tokens, 1, tempArr, 0, tokens.length - 1);
          if (store.get(tokens[0]) instanceof LinkedHashMap) {
            organizeEntries((LinkedHashMap<String, Object>) (store
                .get(tokens[0])), tempArr, bTime);
          } else {
            if (store.get(tokens[0]) != null) {
              store.put(tokens[0], new LinkedHashMap<String, Object>());
            } else {
              store.put(tokens[0], bTime);
            }
          }
        } else {
          if (store.get(tokens[0]) != null) {
            if (!(store.get(tokens[0]) instanceof LinkedHashMap)) {
              int total = Integer.parseInt((String) (store.get(tokens[0])))
                  + Integer.parseInt(bTime);
              store.put(tokens[0], Integer.toString(total));
            } else {
              int total = Integer.parseInt(bTime);
              if (((java.util.LinkedHashMap<String, Object>) (store
                  .get(tokens[0]))).get("systotal") != null) {
                total = total
                    + Integer
                        .parseInt((String) ((java.util.LinkedHashMap<String, Object>) (store
                            .get(tokens[0]))).get("systotal"));
              }
              ((java.util.LinkedHashMap<String, Object>) (store.get(tokens[0])))
                  .put("systotal", Integer.toString(total));
            }
          }
        }
      } else {
        if (tokens.length - 1 == 0) {
          store.put(tokens[0], bTime);
        } else {
          store.put(tokens[0], new LinkedHashMap<String, Object>());
          String[] tempArr = new String[tokens.length - 1];
          System.arraycopy(tokens, 1, tempArr, 0, tokens.length - 1);
          organizeEntries(
              (LinkedHashMap<String, Object>) (store.get(tokens[0])), tempArr,
              bTime);
        }
      }
  }

  /**
   * Sorts the processing element entries inside tree like structure made up of
   * LinkedHashMap. Entries will be sorted in descending order of time taken.
   *
   * @param gStore
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   *
   * @return An Object of type LinkedHashMap<String, Object> containing the
   *         processing elements sorted in descending order of processing time
   *         taken.
   */
  @SuppressWarnings("unchecked")
  private LinkedHashMap<String, Object> sortReport(
    LinkedHashMap<String, Object> gStore) {
    Iterator<String> i = gStore.keySet().iterator();
    LinkedHashMap<String, Object> sortedReport = new LinkedHashMap<String, Object>();
    LinkedHashMap<String, Object> mapperReport = new LinkedHashMap<String, Object>();
    LinkedHashMap<String, String> unsortedReport = new LinkedHashMap<String, String>();
    while (i.hasNext()) {
      Object key = i.next();
      if (gStore.get(key) instanceof LinkedHashMap) {
        int systotal = 0;
        if (((LinkedHashMap<String, Object>) (gStore.get(key)))
            .get("systotal") != null) {
          systotal = Integer
              .parseInt((String) ((LinkedHashMap<String, Object>) (gStore
                  .get(key))).get("systotal"));
        }
        if (systotal >= 0) {
          unsortedReport.put((String) key, Integer.toString(systotal));
        }
        mapperReport.put((String) key,
            sortReport((LinkedHashMap<String, Object>) (gStore.get(key))));

      } else {
        if (!(key.equals("total") || key.equals("systotal"))) {
          if (Integer.parseInt((String) (gStore.get(key))) >= 0) {
            unsortedReport.put((String) key, new Integer((String) gStore
                .get(key)).toString());
          }
        }
      }
    }
    LinkedHashMap<String, String> tempOutLHM =
      sortHashMapByValues(unsortedReport);

    Iterator<String> itr = tempOutLHM.keySet().iterator();
    while (itr.hasNext()) {
      Object tempKey = itr.next();
      sortedReport.put((String) tempKey, tempOutLHM.get(tempKey));
      if (mapperReport.containsKey(tempKey)) {
        sortedReport
            .put((String) tempKey, mapperReport.get(tempKey));
      }
    }
    sortedReport.put("total", gStore.get("total"));
    if (gStore.get("systotal") != null) {
      sortedReport.put("systotal", gStore.get("systotal"));
    }
    return sortedReport;
  }

  /**
   * Sorts LinkedHashMap by its values(natural descending order). keeps the
   * duplicates as it is.
   *
   * @param passedMap
   *          An Object of type LinkedHashMap to be sorted by its values.
   *
   * @return An Object containing the sorted LinkedHashMap.
   */
  private LinkedHashMap<String,String> sortHashMapByValues(LinkedHashMap<String,String> passedMap) {
      List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
      List<String> mapValues = new ArrayList<String>(passedMap.values());

      Collections.sort(mapValues, new ValueComparator());
      Collections.sort(mapKeys);
      Collections.reverse(mapValues);
      LinkedHashMap<String,String> sortedMap = new LinkedHashMap<String,String>();

      Iterator<String> valueIt = mapValues.iterator();
      while (valueIt.hasNext()) {
        String val = valueIt.next();
        Iterator<String> keyIt = mapKeys.iterator();
        while (keyIt.hasNext()) {
          String key = keyIt.next();
          String comp1 = passedMap.get(key);
          String comp2 = val;

          if (comp1.equals(comp2)) {
            passedMap.remove(key);
            mapKeys.remove(key);
            sortedMap.put(key, val);
            break;
          }
        }
      }
      return sortedMap;
  }

  /**
   * Calculates the sub totals at each level.
   *
   * @param reportContainer
   *          An Object of type LinkedHashMap&lt;String, Object&gt; containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   *
   * @return An Object containing modified hierarchical structure of processing
   *         elements with totals and All others embedded in it.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object calculate(Object reportContainer) {
    LinkedHashMap<String, Object> globalStore =
      (LinkedHashMap<String, Object>) reportContainer;
    Iterator<String> iter = globalStore.keySet().iterator();
    int total = 0;
    while (iter.hasNext()) {
      String key = iter.next();
      total = getTotal((LinkedHashMap<String, Object>) (globalStore.get(key)));
      globalTotal.put(key, Integer.toString(total));
    }
    return globalStore;
  }

  /**
   * Calculates the total of the time taken by processing element at each leaf
   * level. Also calculates the difference between the actual time taken by the
   * resources and system noted time.
   *
   * @param reportContainer
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   *
   * @return An integer containing the sub total.
   */

  @SuppressWarnings("unchecked")
  private int getTotal(LinkedHashMap<String, Object> reportContainer) {
    int total = 0;
    int diff = 0;
    int systotal = 0;
    int subLevelTotal = 0;
    Iterator<String> i = reportContainer.keySet().iterator();
    while (i.hasNext()) {
      Object key = i.next();

      if (reportContainer.get(key) instanceof LinkedHashMap) {
        subLevelTotal = getTotal((LinkedHashMap<String, Object>) (reportContainer
            .get(key)));
        total = total + subLevelTotal;
      } else {
        if (!key.equals("systotal")) {
          total = total
              + Integer.parseInt((String) (reportContainer.get(key)));
        }
      }
    }
    if (reportContainer.get("systotal") != null) {
      systotal = Integer.parseInt((String) (reportContainer.get("systotal")));
    }
    diff = systotal - total;
    reportContainer.put("total", Integer.toString(total));
    reportContainer.put("All others", Integer.toString(diff));
    total += diff;
    return total;
  }

  /**
   * Prints a report as per the value provided for print media option.
   *
   * @param reportSource
   *          An Object of type LinkedHashMap&lt;String, Object&gt; containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param outputFile
   *          Path where to save the report.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void printReport(Object reportSource, File outputFile) {
    if (printMedia.equalsIgnoreCase(MEDIA_TEXT)) {
      printToText(reportSource, outputFile, suppressZeroTimeEntries);
    } else if (printMedia.equalsIgnoreCase(MEDIA_HTML)) {
      printToHTML((LinkedHashMap<String, Object>) reportSource,
        outputFile, suppressZeroTimeEntries);
    }
  }

  /**
   * Prints benchmark report in text format.
   *
   * @param reportContainer
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param outputFile
   *          An object of type File representing the output report file.
   * @param suppressZeroTimeEntries
   *          Indicate whether or not to show 0 millisecond entries.
   */
  @SuppressWarnings("unchecked")
  private void printToText(Object reportContainer, File outputFile,
                           boolean suppressZeroTimeEntries) {
    LinkedHashMap<String, Object> globalStore =
      (LinkedHashMap<String, Object>) reportContainer;
    prettyPrint(globalStore, "\t", suppressZeroTimeEntries);
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new FileWriter(outputFile));
      for (String line : printLines) {
        out.write(line);
        out.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) { out.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Prints a processing elements structure in a tree like format with time
   * taken by each element in milliseconds and in %.
   *
   * @param gStore
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param separator
   *          A String separator to indent the processing elements in tree like
   *          structure.
   * @param suppressZeroTimeEntries
   *          Indicate whether or not to show 0 millisecond entries.
   */
  @SuppressWarnings("unchecked")
  private void prettyPrint(LinkedHashMap<String, Object> gStore,
                           String separator, boolean suppressZeroTimeEntries) {

    Iterator<String> i = gStore.keySet().iterator();
    while (i.hasNext()) {
      Object key = i.next();
      if (globalTotal.containsKey(key))
        globalValue = Integer.parseInt(globalTotal.get(key));
      if (gStore.get(key) instanceof LinkedHashMap) {
        int systotal = 0;
        if (((LinkedHashMap<String, Object>) gStore.get(key))
            .containsKey("systotal")) {
          systotal = Integer
              .parseInt((String) ((LinkedHashMap<String, Object>) (gStore
                  .get(key))).get("systotal"));
        }
        if (suppressZeroTimeEntries) {
          if (systotal > 0)
            printLines.add(separator + key + " (" + systotal / 1000.0 + ") ["
                + Math.round(((systotal / globalValue) * 100) * 10) / 10.0
                + "%]");
        } else {
          printLines
              .add(separator + key + " (" + systotal / 1000.0 + ") ["
                  + Math.round(((systotal / globalValue) * 100) * 10) / 10.0
                  + "%]");
        }

        prettyPrint((LinkedHashMap<String, Object>) (gStore.get(key)),
            separator + "\t", suppressZeroTimeEntries);
      } else {
        if (!(key.equals("total") || key.equals("systotal"))) {
          if (suppressZeroTimeEntries) {
            if (Integer.parseInt((String) (gStore.get(key))) != 0) {
              printLines
                  .add(separator
                      + key
                      + " ("
                      + Integer.parseInt((String) (gStore.get(key)))
                      / 1000.0
                      + ") ["
                      + Math
                          .round(((Integer.parseInt((String) (gStore.get(key))) / globalValue) * 100) * 10)
                      / 10.0 + "%]");
            }
          } else {
            printLines
                .add(separator
                    + key
                    + " ("
                    + Integer.parseInt((String) (gStore.get(key)))
                    / 1000.0
                    + ") ["
                    + Math
                        .round(((Integer.parseInt((String) (gStore.get(key))) / globalValue) * 100) * 10)
                    / 10.0 + "%]");
          }
        }
      }
    }
  }

  /**
   * Prints a report in HTML format. The output report will be represented as
   * collapsible ul/li structure.
   *
   * @param gStore
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param outputFile
   *          An object of type File representing the output report file to
   *          which the HTML report is to be written.
   * @param suppressZeroTimeEntries
   *          Indicate whether or not to show 0 millisecond entries.
   */
  private void printToHTML(LinkedHashMap<String, Object> gStore,
                           File outputFile, boolean suppressZeroTimeEntries) {
    String htmlPipelineNames = "<ul>";
    for (String pipeline : pipelineNames) {
      htmlPipelineNames += "<li><b>" + pipeline + "</b></li>" + NL;
    }
    htmlPipelineNames += "</ul>" + NL;
    String htmlReport =
      "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"" + NL +
      "\"http://www.w3.org/TR/html4/loose.dtd\">" + NL +
      "<html><head><title>Benchmarking Report</title>" + NL +
      "<meta http-equiv=\"Content-Type\"" +
      " content=\"text/html; charset=utf-8\">" + NL +
      "<style type=\"text/css\">" + NL +
      "#treemenu li { list-style: none; }" + NL +
      "#treemenu li A { font-family: monospace; text-decoration: none; }" + NL +
      "</style>" + NL +
      "<script type=\"text/javascript\" language=\"javascript\">" + NL +
      "function expandCollapseTree(obj){" + NL +
      "  var li = obj.parentNode;" + NL +
      "  var uls = li.getElementsByTagName('ul');" + NL +
      "  var id = li.id;" + NL +
      "  var ul = uls[0];" + NL +
      "  var objTimeBranch = document.getElementById(id + '.1');" + NL +
      "  var divs = objTimeBranch.getElementsByTagName('div');" + NL +
      "  var div = divs[0];" + NL +
      "  var objperBranch = document.getElementById(id + '.2');" + NL +
      "  var perdivs = objperBranch.getElementsByTagName('div');" + NL +
      "  var perdiv = perdivs[0];" + NL + NL +
      "  if (ul.style.display == 'none' || ul.style.display == '') {" + NL +
      "    ul.style.display = 'block';" + NL +
      "    obj.innerHTML = '[&mdash;]';" + NL +
      "  } else {" + NL +
      "    ul.style.display = 'none';" + NL +
      "    obj.innerHTML = '[+]';" + NL +
      "  }" + NL +
      "  if (div.style.display == 'block') {" + NL +
      "    div.style.display = 'none';" + NL +
      "  } else {" + NL +
      "    div.style.display = 'block';" + NL +
      "  }" + NL +
      "  if (perdiv.style.display == 'block') {" + NL +
      "    perdiv.style.display = 'none';" + NL +
      "  } else {" + NL +
      "    perdiv.style.display = 'block';" + NL +
      "  }" + NL +
      "}" + NL + NL +
      "</script>" + NL +
      "</head>" + NL +
      "<body style=\"font-family:Verdana; color:navy;\">" + NL +
      "<table><tr bgcolor=\"#eeeeff\">" + NL +
      "<td><b>Processing elements of following pipelines</b>" + NL +
      htmlPipelineNames + NL + "</td>" + NL +
      "<td><b>Time in seconds</b></td>" + NL +
      "<td><b>% time taken</b></td></tr><tr>";
    generateCollapsibleHTMLTree(gStore, suppressZeroTimeEntries);
    htmlElementTree += "</ul></div></td>" + NL;
    htmlTimeTree += "</div></div></td>" + NL;
    htmlTimeInPercentTree += "</div></div></td>" + NL;
    htmlReport += htmlElementTree + htmlTimeTree + htmlTimeInPercentTree +
      "</tr></table>" + NL +
      "</body></html>";
    // write the html string in the specified output html file
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new FileWriter(outputFile));
      out.write(htmlReport);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) { out.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Creates three tree like ul/li structures 1. A tree to represent processing
   * elements 2. A tree to represent time taken by processing elements 3. A tree
   * to represent time taken by processing elements in %.
   *
   * @param gStore
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param suppressZeroTimeEntries
   *          Indicate whether or not to show 0 millisecond entries.
   */
  @SuppressWarnings("unchecked")
  private void generateCollapsibleHTMLTree(LinkedHashMap<String, Object> gStore,
                                           boolean suppressZeroTimeEntries) {
    Iterator<String> i = gStore.keySet().iterator();
    while (i.hasNext()) {
      Object key = i.next();
      if (globalTotal.containsKey(key))
        globalValue = Integer.parseInt(globalTotal.get(key));

      if (gStore.get(key) instanceof LinkedHashMap) {
        int systotal = 0;
        if (((LinkedHashMap<String, Object>) gStore.get(key))
            .containsKey("systotal")) {
          systotal = Integer
              .parseInt((String) ((LinkedHashMap<String, Object>) (gStore
                  .get(key))).get("systotal"));
        }

        if (suppressZeroTimeEntries) {
          if (systotal > 0) {
            htmlElementTree += "<li id=\"level" + level + "\">" +
              "<a href=\"#\"  onclick=\"expandCollapseTree(this)\">[+]</a>" +
              "&nbsp;" + key + "<ul style=\"display:none\">" + NL;
            htmlTimeTree += "<div id=level" + level + ".1>" + NL +
              systotal / 1000.0 + NL + "<div style=\"display:none\">" + NL;
            htmlTimeInPercentTree += "<div id=level" + level + ".2>" + NL
                + Math.round(((systotal / globalValue) * 100) * 10) / 10.0
                + "<div style=\"display:none\">" + NL;
            level++;
            generateCollapsibleHTMLTree((LinkedHashMap<String, Object>) (gStore
                .get(key)), suppressZeroTimeEntries);
            htmlElementTree += "</ul></li>" + NL;
            htmlTimeTree += "</div></div>" + NL;
            htmlTimeInPercentTree += "</div></div>" + NL;
          }
        } else {
          htmlElementTree += "<li id=level" + level + ">" +
            "<a href=\"#\" onclick=\"expandCollapseTree(this)\">[+]</a>" +
            "&nbsp;" + key + "<ul style=\"display:none\">" + NL;
          htmlTimeTree += "<div id=level" + level + ".1>" + NL +
            systotal / 1000.0 + "<div style=\"display:none\">" + NL;
          htmlTimeInPercentTree += "<div id=level" + level + ".2>" + NL
              + Math.round(((systotal / globalValue) * 100) * 10) / 10.0
              + "<div style=\"display:none\">" + NL;
          level++;
          generateCollapsibleHTMLTree((LinkedHashMap<String, Object>) (gStore
              .get(key)), suppressZeroTimeEntries);
          htmlElementTree += "</ul></li>" + NL;
          htmlTimeTree += "</div></div>" + NL;
          htmlTimeInPercentTree += "</div></div>" + NL;
        }
      } else {
        if (!(key.equals("total") || key.equals("systotal"))) {
          if (suppressZeroTimeEntries) {
            if (Integer.parseInt((String) (gStore.get(key))) != 0) {
              htmlElementTree += "<li>&nbsp;&nbsp;&nbsp;" + key + "</li>" + NL;
              htmlTimeTree += "<div>" + NL
                  + Integer.parseInt((String) (gStore.get(key))) / 1000.0
                  + "</div>" + NL;
              htmlTimeInPercentTree += "<div>" + NL
                  + Math.round(((Integer.parseInt((String) (gStore.get(key))) / globalValue) * 100) * 10)
                  / 10.0 + "</div>" + NL;
            }
          } else {
            htmlElementTree += "<li>&nbsp;&nbsp;&nbsp;" + key + "</li>" + NL;
            htmlTimeTree += "<div>" + NL
                + Integer.parseInt((String) (gStore.get(key))) / 1000.0
                + "</div>" + NL;
            htmlTimeInPercentTree += "<div>" + NL
                + Math.round(((Integer.parseInt((String) (gStore.get(key))) / globalValue) * 100) * 10)
                / 10.0 + "</div>" + NL;
          }
        }
      }
    }
  }

  /**
   * Ensures that the required line is read from the given file part.
   *
   * @param bytearray
   *          A part of a file being read upside down.
   * @param lastNlines
   *          A vector containing the lines extracted from file part.
   * @return true if marker indicating the logical start of run is found; false
   *         otherwise.
   */
  private boolean parseLinesFromLast(byte[] bytearray,
                                     Vector<String> lastNlines) {
      String lastNChars = new String(bytearray);
      StringBuffer sb = new StringBuffer(lastNChars);
      lastNChars = sb.reverse().toString();
      StringTokenizer tokens = new StringTokenizer(lastNChars, NL);
      while (tokens.hasMoreTokens()) {
        StringBuffer sbLine = new StringBuffer(tokens.nextToken());
        lastNlines.add(sbLine.reverse().toString());
        if ((lastNlines.get(lastNlines.size() - 1)).trim().endsWith(
            getLogicalStart())) {
          return true;
        }
      }
      return false; // indicates didn't read 'lineCount' lines
  }

  /**
   * Reads the given file upside down.
   *
   * @param fileToBeRead
   *          An object of type File representing the file to be read.
   * @param chunkSize
   *          An integer specifying the size of the chunks in which file will be
   *          read.
   * @return A long value pointing to the start position of the given file
   *         chunk.
   * @throws BenchmarkReportInputFileFormatException
   */
  private long tail(File fileToBeRead, int chunkSize)
      throws BenchmarkReportInputFileFormatException {
    RandomAccessFile raf = null;
    try {
      raf = new RandomAccessFile(fileToBeRead, "r");
      Vector<String> lastNlines = new Vector<String>();
      int delta = 0;
      long curPos = raf.length() - 1;
      long fromPos;
      byte[] bytearray;
      while (true) {
        fromPos = curPos - chunkSize;
        if (fromPos <= 0) {
          raf.seek(0);
          bytearray = new byte[(int) curPos];
          raf.readFully(bytearray);
          if (parseLinesFromLast(bytearray, lastNlines)) {
            if (fromPos < 0)
              fromPos = 0;
          }
          break;
        } else {
          raf.seek(fromPos);
          bytearray = new byte[chunkSize];
          raf.readFully(bytearray);
          if (parseLinesFromLast(bytearray, lastNlines)) {
            break;
          }
          delta = (lastNlines.get(lastNlines.size() - 1)).length();
          lastNlines.remove(lastNlines.size() - 1);
          curPos = fromPos + delta;
        }
      }
      if (fromPos < 0)
        throw new BenchmarkReportInputFileFormatException(getBenchmarkFile()
            .getAbsolutePath()
            + " does not contain a marker named "
            + getLogicalStart()
            + " indicating logical start of a run.");
      return fromPos;

    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
    finally {
      IOUtils.closeQuietly(raf);
    }
  }

  /**
   * Ignores the inconsistent log entries from the benchmark file. Entries from
   * modules like pronominal coreferencer which have not been converted to new
   * benchmarking conventions are ignored.
   *
   * @param benchmarkIDChain
   *          The chain of benchmark ids. This is the third token in the
   *          benchmark file.
   * @param startTokens
   *          An array of first tokens in the benchmark id chain.
   *
   * @return true if valid log entry; false otherwise.
   */
  private boolean validateLogEntry(String benchmarkIDChain,
                                   ArrayList<String> startTokens) {
    String startTokenRegExp = "(";
    for (int i = 0; i < startTokens.size(); i++) {
      if ((benchmarkIDChain.split("\\.")).length == 1
          && benchmarkIDChain.equals(startTokens.get(i))) {
        startTokens.remove(i);
        validEntries += 1;
        return true;
      }
      startTokenRegExp += startTokens.get(i) + "|";
    }
    if (startTokenRegExp.length() > 1) {
      startTokenRegExp = startTokenRegExp.substring(0, startTokenRegExp
          .length() - 1);
    }
    startTokenRegExp += ")";
    if (benchmarkIDChain.matches(startTokenRegExp + "\\.doc_.*?\\.pr_.*")) {
      validEntries += 1;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Parses the report arguments.
   *
   * @param args
   *          A string array containing the command line arguments.
   */
  @Override
  public void parseArguments(String[] args) {
    Getopt g = new Getopt("gate.util.reporting.PRTimeReporter", args,
        "i:m:z:s:o:l:h");
    int choice;
    String argSuppressZeroTimeEntries = null;
    while ((choice = g.getopt()) != -1) {
      switch (choice) {
      // -i inputFile
      case 'i':
        String argInPath = g.getOptarg();
        if (argInPath != null) {
          setBenchmarkFile(new File(argInPath));
        }
        break;
      // -m printMedia
      case 'm':
        String argPrintMedia = g.getOptarg();
        if (argPrintMedia != null) {
          setPrintMedia(argPrintMedia);
        } else {
          setPrintMedia(printMedia);
        }
        break;
      // -z suppressZeroTimeEntries
      case 'z':
        argSuppressZeroTimeEntries = g.getOptarg();
        if (argSuppressZeroTimeEntries == null) {
          setSuppressZeroTimeEntries(suppressZeroTimeEntries);
        }
        break;
      // -s sortOrder
      case 's':
        String argSortOrder = g.getOptarg();
        if (argSortOrder != null) {
          setSortOrder(argSortOrder);
        } else {
          setSortOrder(sortOrder);
        }
        break;

      // -o ReportFile
      case 'o':
        String argOutPath = g.getOptarg();
        if (argOutPath != null) {
          setReportFile(new File(argOutPath));
        }
        break;
      // -l logical start
      case 'l':
        String argLogicalStart = g.getOptarg();
        if (argLogicalStart != null) {
          setLogicalStart(argLogicalStart);
        }
        break;
      // -h
      case 'h':
      case '?':
        usage();
        System.exit(STATUS_NORMAL);
        break;

      default:
        usage();
        System.exit(STATUS_ERROR);
        break;
      } // getopt switch
    }
    if (argSuppressZeroTimeEntries != null) {
      if (argSuppressZeroTimeEntries.trim().equalsIgnoreCase("true")) {
        setSuppressZeroTimeEntries(true);
      } else if (argSuppressZeroTimeEntries.trim().equalsIgnoreCase("false")) {
        setSuppressZeroTimeEntries(false);
      } else {
        System.err.println("Suppress Zero Time Entries: parameter value" + NL +
          " passed is invalid. Please provide true or false as value.");
        usage();
        System.exit(STATUS_ERROR);
      }
    }
  }

  /**
   * Display a usage message
   */
  public static void usage() {
    System.out.println(
    "Usage: java gate.util.reporting.PRTimeReporter [Options]" + NL
  + "\t Options:" + NL
  + "\t -i input file path (default: benchmark.txt in the execution directory)" + NL
  + "\t -m print media - html/text (default: html)" + NL
  + "\t -z suppressZeroTimeEntries - true/false (default: true)" + NL
  + "\t -s sorting order - exec_order/time_taken (default: exec_order)" + NL
  + "\t -o output file path (default: report.html/txt in the system temporary directory)" + NL
  + "\t -l logical start (not set by default)" + NL
  + "\t -h show help" + NL);
  } // usage()

  /**
   * A main method which acts as a entry point while executing a report via
   * command line.
   *
   * @param args
   *          A string array containing the command line arguments.
   */
  public static void main(String[] args)
      throws BenchmarkReportInputFileFormatException {
    // process command-line options
    PRTimeReporter reportOne = new PRTimeReporter(args);
    reportOne.generateReport();
  }

  /**
   * Calls store, calculate and printReport for generating the actual report.
   */
  @SuppressWarnings("unchecked")
  private void generateReport()
      throws BenchmarkReportInputFileFormatException {
    Timer timer = null;
    try {
      TimerTask task = new FileWatcher(getBenchmarkFile()) {
        @Override
        protected void onChange(File file) throws BenchmarkReportExecutionException {
          throw new BenchmarkReportExecutionException(getBenchmarkFile()
              + " file has been modified while generating the report.");
        }
      };
      timer = new Timer();
      // repeat the check every second
      timer.schedule(task, new Date(), 1000);

      Object report1Container1 = store(getBenchmarkFile());
      Object report1Container2 = calculate(report1Container1);
      if (getSortOrder().equalsIgnoreCase("time_taken")) {
        report1Container2 = sortReport(
          (LinkedHashMap<String, Object>) report1Container2);
      }
      if (reportFile == null) {
        reportFile = new File(System.getProperty("java.io.tmpdir"),
          "report." + ((printMedia.equals(MEDIA_HTML)) ? "html" : "txt"));
      }
      printReport(report1Container2, reportFile);
    } finally {
      if (timer != null) { timer.cancel(); }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see gate.util.reporting.BenchmarkReportable#executeReport()
   */
  @Override
  public void executeReport() throws BenchmarkReportInputFileFormatException {
    generateReport();
  }

  /**
   * Returns the flag indicating whether or not to suppress the processing
   * elements from the report which took 0 milliseconds.
   *
   * @return suppressZeroTimeEntries A boolean indicating whether or not to
   *         suppress zero time entries.
   */
  public boolean isSuppressZeroTimeEntries() {
    return suppressZeroTimeEntries;
  }

  /**
   * Allow to suppress the processing elements from the report which
   * took 0 milliseconds.
   *
   * @param suppressZeroTimeEntries if true suppress zero time entries.
   * This Parameter is ignored if SortOrder specified is
   * <code>SORT_TIME_TAKEN</code>. True by default.
   */
  public void setSuppressZeroTimeEntries(boolean suppressZeroTimeEntries) {
    this.suppressZeroTimeEntries = suppressZeroTimeEntries;
  }

  /**
   * Returns the name of the media on which report will be generated. e.g. text,
   * HTML.
   *
   * @return printMedia A String containing the name of the media on which
   *         report will be generated.
   */
  public String getPrintMedia() {
    return printMedia;
  }

  /**
   * Sets the media on which report will be generated.
   *
   * @param printMedia Type of media on which the report will be generated.
   * Must be MEDIA_TEXT or  MEDIA_HTML.
   * The default is MEDIA_HTML.
   */
  public void setPrintMedia(String printMedia) {
    if (!printMedia.equals(MEDIA_HTML)
     && !printMedia.equals(MEDIA_TEXT)) {
      throw new IllegalArgumentException("Illegal argument: " + printMedia);
    }
    this.printMedia = printMedia.trim();
  }

  /**
   * Returns the sorting order specified for the report (EXEC_ORDER or
   * TIME_TAKEN).
   *
   * @return sortOrder A String containing the sorting order.
   */
  public String getSortOrder() {
    return sortOrder;
  }

  /**
   * Sets the sorting order of the report.
   *
   * @param sortOrder Sorting order of the report.
   * Must be SORT_EXEC_ORDER or SORT_TIME_TAKEN.
   * Default is <code>SORT_EXEC_ORDER</code>.
   *
   */
  public void setSortOrder(String sortOrder) {
    if (!sortOrder.equals(SORT_EXEC_ORDER)
     && !sortOrder.equals(SORT_TIME_TAKEN)) {
      throw new IllegalArgumentException("Illegal argument: " + printMedia);
    }
    this.sortOrder = sortOrder.trim();
  }

  /**
   * Returns the marker indicating logical start of a run.
   *
   * @return logicalStart A String containing the marker indicating logical
   *         start of a run.
   */
  public String getLogicalStart() {
    return logicalStart;
  }

  /**
   * Sets optionally a string indicating the logical start of a run.
   *
   * @param logicalStart A String indicating the logical start of a run.
   * Useful when you you have marked different runs in
   * your benchmark file with this string at their start.
   * By default the value is null.
   */
  public void setLogicalStart(String logicalStart) {
    this.logicalStart = logicalStart.trim();
  }

  /**
   * @return benchmarkFile path to input benchmark file.
   * @see #setBenchmarkFile(java.io.File)
   */
  public File getBenchmarkFile() {
    return benchmarkFile;
  }

  /**
   * Sets the input benchmark file from which the report is generated.
   * By default use the file named "benchmark.txt" from the application
   * execution directory.
   *
   * @param benchmarkFile Input benchmark file.
   */
  public void setBenchmarkFile(File benchmarkFile) {
    this.benchmarkFile = benchmarkFile;
  }

  /**
   * @return reportFile file path where the report file is written.
   * @see #setReportFile(java.io.File)
   */
  public File getReportFile() {
    return reportFile;
  }

  /**
   * If not set, the default is the file name "report.txt/html"
   * in the system temporary directory.
   *
   * @param reportFile file path to the report file to write.
   */
  public void setReportFile(File reportFile) {
    this.reportFile = reportFile;
  }

}

/**
 * A Comparator class to compare the values of the LinkedHashMaps containing
 * processing elements and time taken by them.
 */
class ValueComparator implements Comparator<String>, Serializable {

  private static final long serialVersionUID = -1767153293265172453L;

  /**
   * Provides the comparison logic between the processing time taken by
   * processing elements
   *
   * @param obj1
   *          An integer value in form of string to be compared
   * @param obj2
   *          An integer value in form of string to be compared
   *
   * @return An integer representing difference (0 if both are equal, positive
   *         if obj1 is greater then obj2, negative if obj2 is greater then
   *         obj1)
   */
  @Override
  public int compare(String obj1, String obj2) {
    int i1 = Integer.parseInt(obj1);
    int i2 = Integer.parseInt(obj2);
    return i1 - i2;
  }
}
