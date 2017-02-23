/*
 *  DocTimeReporter.java
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
import gate.util.reporting.exceptions.BenchmarkReportFileAccessException;
import gate.util.reporting.exceptions.BenchmarkReportInputFileFormatException;
import gnu.getopt.Getopt;

/**
 * A reporter class to generate a report on time taken by each document within
 * given corpus.
 */
public class DocTimeReporter implements BenchmarkReportable {

  /** A File handle to input benchmark file. */
  private File benchmarkFile = new File("benchmark.txt");
  /** Report media. */
  private String printMedia = MEDIA_HTML;
  /** No of documents to be displayed against matching PRs. */
  private int maxDocumentInReport = 10;
  /** Search string, could be a PR name. */
  private String PRMatchingRegex = MATCH_ALL_PR_REGEX;
  /** A marker indicating the start of current logical run. */
  private String logicalStart = null;
  /** Path where to save the report file. */
  private File reportFile;

  /**
   * An HashSet containing names of the documents matching the given search
   * string.
   */
  private HashSet<String> allDocs = new HashSet<String>();
  /**
   * An HashSet containing PR names matching the search string. Used to display
   * in report header.
   */
  private HashSet<String> matchingPRs = new HashSet<String>();
  /** Total time taken by the given pipeline for the current logical run. */
  private float globalTotal = 0;
  /** A LinkedHashMap containing the documents matching the given PRs. */
  private LinkedHashMap<String, String> docContainer = new LinkedHashMap<String, String>();
  /**
   * Folder where the benchmark.txt files are created for specific pipeline log
   * entries.
   */
  private File temporaryDirectory;
  /** Name of the given pipeline */
  private String pipelineName = "";
  /** Status flag for normal exit. */
  private static final int STATUS_NORMAL = 0;
  /** Status flag for error exit. */
  private static final int STATUS_ERROR = 1;
  /** Chunk size in which file will be read */
  private static final int FILE_CHUNK_SIZE = 2000;
  /** An OS independent line separator */
  private static final String NL = System.getProperty("line.separator");
  /**
   * An integer containing the count of total valid log entries present in input
   * file provided.
   */
  public int validEntries = 0;

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
   * This integer constant when set as No of Docs indicates that the report have
   * all the documents matching a given PR.
   */
  public static final int ALL_DOCS = -1;

  /**
   * The default value for search string matching PRs for given run.
   */
  public static final String MATCH_ALL_PR_REGEX = "all_prs";

  /**
   * No argument constructor.
   */
  public DocTimeReporter() {
    // some initialisations
    initTmpDir();
  }

  /**
   * A constructor to be used while executing the tool from the command line.
   *
   * @param args array containing command line arguments.
   */
  DocTimeReporter(String[] args) {
      initTmpDir();
      parseArguments(args);
  }

  private void initTmpDir() {
     try {
      temporaryDirectory = File.createTempFile("benchmark-reports", "", null);
      if (!temporaryDirectory.delete()
       || !temporaryDirectory.mkdir()) {
        throw new IOException("Unable to create temporary directory.\n"
          + temporaryDirectory.getCanonicalPath());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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
   * @return An Object containing modified hierarchical structure of processing
   *         elements with totals and All others embedded in it.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object calculate(Object reportContainer) {
    return sortHashMapByValues(
      doTotal((LinkedHashMap<String, Object>) reportContainer));
  }

  /**
   * Sorts LinkedHashMap by its values(natural descending order). keeps the
   * duplicates as it is.
   *
   * @param passedMap
   *          An Object of type LinkedHashMap to be sorted by its values.
   * @return An Object containing the sorted LinkedHashMap.
   */
  private LinkedHashMap<?,?> sortHashMapByValues(LinkedHashMap<String,String> passedMap) {
    List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
    List<String> mapValues = new ArrayList<String>(passedMap.values());

    Collections.sort(mapValues, new ValueComparator());
    Collections.sort(mapKeys);
    // Reversing the collection to sort the values in descending order
    Collections.reverse(mapValues);
    LinkedHashMap<String,String> sortedMap = new LinkedHashMap<String,String>();

    Iterator<String> valueIt = mapValues.iterator();
    while (valueIt.hasNext()) {
      String val = valueIt.next();
      Iterator<String> keyIt = mapKeys.iterator();
      while (keyIt.hasNext()) {
        String key = keyIt.next();
        String comp1 = passedMap.get(key).toString();
        String comp2 = val.toString();

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
   * Computes the sub totals at each processing level.
   *
   * @param reportContainer
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @return An Object containing the LinkedHashMap with the element values
   *         totaled.
   */
  @SuppressWarnings("unchecked")
  private LinkedHashMap<String, String> doTotal(
    LinkedHashMap<String, Object> reportContainer) {
    LinkedHashMap<String, Object> myHash =
      reportContainer;
    Iterator<String> i = myHash.keySet().iterator();
    while (i.hasNext()) {
      String key = i.next();
      if (myHash.get(key) instanceof LinkedHashMap) {
        docContainer = doTotal((LinkedHashMap<String, Object>) (myHash
            .get(key)));
      } else {
        if (docContainer.get(key) == null) {
          docContainer.put(key, (String)myHash.get(key));
        } else {
          // Do total if value already exists
          int val = Integer.parseInt(docContainer.get(key))
              + Integer.parseInt((String) myHash.get(key));
          docContainer.put(key, Integer.toString(val));
        }
      }
    }
    return docContainer;
  }

  /**
   * Prints a report as per the value provided for print media option.
   *
   * @param reportSource
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          processing elements (with time in milliseconds) in hierarchical
   *          structure.
   * @param outputFile
   *          Path where to save the report.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void printReport(Object reportSource, File outputFile) {
    if (printMedia.equalsIgnoreCase(MEDIA_TEXT)) {
      printToText(reportSource, outputFile);
    } else if (printMedia.equalsIgnoreCase(MEDIA_HTML)) {
      printToHTML((LinkedHashMap<String, Object>) reportSource, outputFile);
    }
  }

  /**
   * Prints benchmark report in text format.
   *
   * @param reportContainer
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          document names (with time in milliseconds) in hierarchical
   *          structure.
   * @param outputFile
   *          An object of type File representing the output report file.
   */
  private void printToText(Object reportContainer, File outputFile) {
    ArrayList<String> printLines = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    LinkedHashMap<String, Object> rcHash =
      (LinkedHashMap<String, Object>) reportContainer;
    String docs = "";
    if (maxDocumentInReport != ALL_DOCS) {
      if (allDocs.size() < maxDocumentInReport) {
        docs = Integer.toString(allDocs.size());
      } else {
        docs = Integer.toString(maxDocumentInReport);
      }

    } else {
      docs = "All";
    }
    printLines
        .add("============================================================="
            + NL);
    if (PRMatchingRegex.equals(MATCH_ALL_PR_REGEX)) {
      printLines.add("Top " + docs
          + " expensive documents matching All PRs in " + pipelineName
          + NL);
    } else {
      if (matchingPRs.size() > 0) {
        printLines.add("Top " + docs
            + " expensive documents matching following PRs in " + pipelineName
            + NL);
        for (String pr : matchingPRs) {
          printLines.add("\t" + pr + NL);
        }
      } else {
        printLines.add("No PRs matched to search string \""
            + getPRMatchingRegex() + "\"" + " in " + pipelineName);
        printLines.add(NL);
        printLines
            .add("============================================================="
                + NL);
      }

    }
    if (allDocs.size() > 0) {
      printLines
          .add("============================================================="
              + NL);
      printLines.add("Document Name" + "\t" + "Time (in seconds)" + "\t" + "%"
          + NL);
      printLines
          .add("-------------------------------------------------------------"
              + NL);
    }
    Iterator<String> i = rcHash.keySet().iterator();
    int count = 0;
    // Iterating over the report container
    while (i.hasNext()) {
      Object key = i.next();
      if (!((String) key).equals("total")) {
        int value = Integer.parseInt((String) rcHash.get(key));
        if (maxDocumentInReport == ALL_DOCS)
          printLines.add(key + "\t" + value / 1000.0 + "\t"
              + Math.round(((value / globalTotal) * 100) * 10) / 10.0
              + NL);
        else if (count < maxDocumentInReport)
          printLines.add(key + "\t" + value / 1000.0 + "\t"
              + Math.round(((value / globalTotal) * 100) * 10) / 10.0
              + NL);
      }
      count++;
    }
    if (allDocs.size() > 0) {
      printLines
          .add("-------------------------------------------------------------"
              + NL);
      printLines.add("Pipeline Total" + "\t" + globalTotal / 1000.0 + "\t"
          + 100 + NL + NL + NL);
    }
    BufferedWriter out = null;
    try {
      // Writing to report file
      out = new BufferedWriter(new FileWriter(outputFile, true));
      for (String line : printLines) {
        out.write(line);
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
   * Stores GATE processing elements and the time taken by them in an in-memory
   * data structure for report generation.
   *
   * @param inputFile
   *          A handle to the input benchmark file.
   *
   * @return An Object of type LinkedHashMap<String, Object> containing the
   *         processing elements (with time in milliseconds) in hierarchical
   *         structure. Null if there was an error.
   *
   * @throws BenchmarkReportInputFileFormatException
   *           if the input file provided is not a valid benchmark file.
   */
  @Override
  public Object store(File inputFile)
      throws BenchmarkReportInputFileFormatException {
    String[] temp = inputFile.getAbsolutePath().split("\\" + File.separator);
    pipelineName = temp[temp.length - 1].replace("_benchmark.txt", "");
    LinkedHashMap<String, Object> globalStore =
      new LinkedHashMap<String, Object>();
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(inputFile));
      String str;
      String docName = null;
      String matchedPR = null;
      String startToken = null;
      // Reading the benchmark.txt one line at a time
      Pattern pattern = Pattern.compile("(\\d+) (\\d+) (.*) (.*) \\{(.*)\\}");
      // Pattern matching for extracting document name
      Pattern patternDocName = Pattern.compile(".*documentName=(.*?)[,|}].*");
      while ((str = in.readLine()) != null) {
        if (str.matches(".*START.*")) {
          String[] splittedStartEntry = str.split("\\s");
          if (splittedStartEntry.length > 2) {
            startToken = splittedStartEntry[2];
          } else {
            throw new BenchmarkReportInputFileFormatException(
                getBenchmarkFile() + " is invalid.");
          }
        }
        Matcher matcher = pattern.matcher(str);
        Matcher matcherDocName = patternDocName.matcher(str);
        Pattern patternDocEnd = Pattern.compile("(\\d+) (\\d+) " + Pattern.quote(startToken)
            + " (.*) \\{(.*)\\}.*");
        Matcher matcherDocEnd = patternDocEnd.matcher(str);
        if (matcherDocName != null) {
          if (matcherDocName.matches()) {
            docName = matcherDocName.group(1);

          }
        }
        if (matcherDocEnd != null) {
          if (matcherDocEnd.matches()) {

            globalTotal = globalTotal
                + Integer.parseInt(matcherDocEnd.group(2));
          }
        }
        if (matcher != null && matcher.matches()) {
          String benchmarkIDs = matcher.group(3).replaceFirst(Pattern.quote(startToken) + ".",
              "").replaceFirst("doc_" + Pattern.quote(docName) + ".", "");
          String[] splittedBenchmarkIDs = benchmarkIDs.split("\\.");
          // Getting the exact PR name and storing only entries matching PR name
          if (PRMatchingRegex.equals(MATCH_ALL_PR_REGEX)) {
            if (splittedBenchmarkIDs.length > 0) {
              matchedPR = splittedBenchmarkIDs[0];
            }
            if (!matchedPR.equalsIgnoreCase(startToken)) {
              organizeEntries(globalStore, matchedPR, matcher.group(2), docName);
            }
          } else if (isPRMatched(benchmarkIDs, PRMatchingRegex)) {
            if (splittedBenchmarkIDs.length > 0) {
              matchedPR = splittedBenchmarkIDs[0];
            }
            if (matchedPR != null)
              matchingPRs.add(matchedPR);
            organizeEntries(globalStore, matchedPR, matcher.group(2), docName);
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
    return globalStore;
  }

  /**
   * Organizes the valid data extracted from the log entries into LinkedHashMap.
   *
   * @param store
   *          A global LinkedHashMap containing the processing elements (with
   *          time in milliseconds) in hierarchical structure.
   * @param matchedPR
   *          A PR matching the given search string.
   * @param bTime
   *          Time taken by the specific processing element.
   * @param docName
   *          Name of the document being processed.
   */
  @SuppressWarnings("unchecked")
  private void organizeEntries(LinkedHashMap<String, Object> store,
                               String matchedPR, String bTime, String docName) {
    allDocs.add(docName);
    if (store.containsKey(matchedPR)) {
      ((LinkedHashMap<String, Object>) store.get(matchedPR))
          .put(docName, bTime);
    } else {
      LinkedHashMap<String, Object> tempLHM = new LinkedHashMap<String, Object>();
      tempLHM.put(docName, bTime);
      store.put(matchedPR, tempLHM);
    }
  }

  /**
   * Prints the document level statistics report in HTML format.
   *
   * @param reportSource
   *          An Object of type LinkedHashMap<String, Object> containing the
   *          document names (with time in milliseconds).
   * @param outputFile
   *          An object of type File representing the output report file to
   *          which the HTML report is to be written.
   */
  private void printToHTML(LinkedHashMap<String, Object> reportSource,
                           File outputFile) {
    String htmlReport =
      "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"" + NL +
      "\"http://www.w3.org/TR/html4/loose.dtd\">" + NL +
      "<html><head><title>Benchmarking Report</title>" + NL +
      "<meta http-equiv=\"Content-Type\"" +
      " content=\"text/html; charset=utf-8\">" + NL +
      "<style type=\"text/css\">" + NL +
      "div { font-size:12px; margin-top: 4; }" + NL +
      "</style>" + NL +
      "</head>" + NL +
      "<body style=\"font-family:Verdana; color:navy;\">" + NL;
    String hTrace =
      "<div style=\"right: 0pt; border-top:1px solid #C9D7F1;" +
        " font-size:1px;\" ></div>" + NL;
    String reportTitle = hTrace;
    String docs = "";
    if (maxDocumentInReport != ALL_DOCS) {
      if (allDocs.size() < maxDocumentInReport) {
        docs = Integer.toString(allDocs.size());
      } else {
        docs = Integer.toString(maxDocumentInReport);
      }
    } else {
      docs = "All";
    }
    if (PRMatchingRegex.equals(MATCH_ALL_PR_REGEX)) {
      reportTitle = reportTitle
        + "<div style=\"font-size:15px;font-family:Verdana; color:navy;\">Top "
        + docs + " expensive documents matching All PRs in <b>"
        + pipelineName + "</b></div>" + NL;
    } else {
      if (matchingPRs.size() > 0) {
        reportTitle = reportTitle
          + "<div style=\"font-size:15px;font-family:Verdana; color:navy;\">Top "
          + docs + " expensive documents matching following PRs in <b>"
          + pipelineName + "</b> <ul>" + NL;
        for (String pr : matchingPRs) {
          reportTitle = reportTitle + "<li>" + pr + "</li>";
        }
        reportTitle = reportTitle + "</ul></div>";
      } else {
        reportTitle +=
          "<div style=\"font-size:15px;font-family:Verdana; color:navy;\">" +
          "No PRs matched to search string \"" +
          getPRMatchingRegex() + " \" in " + pipelineName + "</div>";
      }
    }
    reportTitle = reportTitle + hTrace;

    if (allDocs.size() > 0) {
      String htmlReportTitle = reportTitle +
        "<table><tr bgcolor=\"#eeeeff\">" +
        "<td><b>Document Name</b></td>" +
        "<td><b>Time in seconds</b></td>" +
        "<td><b>% Time taken</b></td>" +
        "</tr><tr>" + NL;
      String documentNameHTMLString = "<td rowspan = '112' width = '550'>";
      String timeTakenHTMLString = "<td width = '100'>";
      String timeInPercentHTMLString = "<td width = '100'>";
      LinkedHashMap<String, Object> rcHash =
        reportSource;
      rcHash.remove("total");
      Iterator<String> i = rcHash.keySet().iterator();
      int count = 0;
      while (i.hasNext()) {
        Object key = i.next();
        if (!((String) key).equals("total")) {
          int value = Integer.parseInt((String) rcHash.get(key));
          if (maxDocumentInReport == ALL_DOCS) {
            documentNameHTMLString += "<div>" + key + "</div>";
            timeTakenHTMLString += "<div>" + value / 1000.0 + "</div>";
            timeInPercentHTMLString += "<div>"
                + Math.round(((value / globalTotal) * 100) * 10) / 10.0
                + "</div>" + NL;
          } else if (count < maxDocumentInReport) {
            documentNameHTMLString += "<div>" + key + "</div>";
            timeTakenHTMLString += "<div>" + value / 1000.0 + "</div>";
            timeInPercentHTMLString += "<div>"
                + Math.round(((value / globalTotal) * 100) * 10) / 10.0
                + "</div>" + NL;
          }
        }
        count++;
      }
      documentNameHTMLString +=
        "<div bgcolor=\"#eeeeff\" style = \"font-size:15px;margin-left:400px;\">" +
        "<b>Total</b></div></td>" + NL;
      timeTakenHTMLString +=
        "<div bgcolor=\"#eeeeff\" style = \"font-size:15px;\"><b>" +
        globalTotal / 1000.0 + "</b></div></td>" + NL;
      timeInPercentHTMLString +=
        "<div bgcolor=\"#eeeeff\" style = \"font-size:15px;\">" +
         "<b>100</b></div></td>" + NL;

      if (!outputFile.exists()) {
        htmlReport += htmlReportTitle + documentNameHTMLString
            + timeTakenHTMLString + timeInPercentHTMLString + "</tr></table>";
      } else {
        htmlReport = "<br/><br/>" + htmlReportTitle + documentNameHTMLString
            + timeTakenHTMLString + timeInPercentHTMLString
            + "</tr></table></body></html>";
      }
    } else {
      htmlReport += reportTitle + "</body></html>";
    }

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
   * Ignores the inconsistent log entries from the benchmark file. Entries from
   * modules like pronominal coreferencer which have not been converted to new
   * benchmarking conventions are ignored.
   *
   * @param benchmarkIDChain
   *          the chain of benchmark ids. This is the third token in the
   *          benchmark file.
   * @param startTokens
   *          an array of first tokens in the benchmark id chain.
   *
   * @return true if valid log entry; false otherwise.
   */
  private boolean validateLogEntry(String benchmarkIDChain,
                                   ArrayList<String> startTokens) {
    String startTokenRegExp = "(";
    for (int i = 0; i < startTokens.size(); i++) {
      if ((benchmarkIDChain.split("\\.")).length == 1
          && benchmarkIDChain.equals(startTokens.get(i))) {
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
    } else
      return false;
  }

  /**
   * Parses the report command lime arguments.
   *
   * @param args array containing the command line arguments.
   */
  @Override
  public void parseArguments(String[] args) {
    Getopt g = new Getopt("gate.util.reporting.DocTimeReporter", args,
        "i:m:d:p:o:l:h");
    int c;
    String argNoOfDocs = null;
    while ((c = g.getopt()) != -1) {
      switch (c) {
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
        }
        break;
      // -d noOfDocs
      case 'd':
        argNoOfDocs = g.getOptarg();
        if (argNoOfDocs == null) {
          setMaxDocumentInReport(maxDocumentInReport);
        }
        break;
      // -p prName
      case 'p':
        String argPrName = g.getOptarg();
        if (argPrName != null) {
          setPRMatchingRegex(argPrName);
        } else {
          setPRMatchingRegex(PRMatchingRegex);
        }
        break;
      // -o Report File
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
      // -h usage information
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
    if (argNoOfDocs != null) {
      try {
        setMaxDocumentInReport(Integer.parseInt(argNoOfDocs));
      } catch (NumberFormatException e) {
        e.printStackTrace();
        usage();
        System.exit(STATUS_ERROR);
      }
    }
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
   * Provides the functionality to match a user input string with the PR in the
   * given benchmark ids.
   *
   * @param benchmarkIDs
   *          A string of benchmarkIDs containing the PR name at the start of
   *          string.
   * @param searchString
   *          The string to be matched for PR name.
   *
   * @return boolean true if search string matches PR name; false otherwise.
   */
  private boolean isPRMatched(String benchmarkIDs, String searchString) {
    String prName = benchmarkIDs.split("\\.")[0];
    // Remove leading and trailing whitespaces of search string
    searchString = searchString.trim();
    // Remove "pr" or "pr_" appearing in start of the prName string
    searchString = searchString.replaceAll("^(pr|pr_)", "");
    // Replace underscores with a space in the search string
    searchString = searchString.replaceAll("_", " ");
    // Replace multiple spaces with a single space
    searchString = searchString.replaceAll("\\s+", " ");
    searchString = searchString.trim();
    // Remove "pr_" appearing in start of the prName string
    String processedPRName = prName.replaceAll("^pr_", "");
    // Replace underscores with a space in the prName
    processedPRName = processedPRName.replaceAll("_", " ");
    if (prName.startsWith("pr_")) {
      return processedPRName.matches("(?i).*" + searchString + ".*");
    } else {
      return false;
    }
  }

  /**
   * A method for deleting a given file.
   *
   * @param fileToBeDeleted
   *          A handle of the file to be deleted.
   * @throws BenchmarkReportFileAccessException
   *           if a given file could not be deleted.
   */
  private void deleteFile(File fileToBeDeleted)
      throws BenchmarkReportFileAccessException {
    if (fileToBeDeleted.isFile()) {
      if (!fileToBeDeleted.delete()) {
        throw new BenchmarkReportFileAccessException(
          "Could not delete " + fileToBeDeleted.getAbsolutePath());
      }
    }
  }

  /**
   * Provides the functionality to separate out pipeline specific benchmark
   * entries in separate temporary benchmark files in a temporary folder in the
   * current working directory.
   *
   * @param benchmarkFile
   *          An object of type File representing the input benchmark file.
   * @param report
   *          A file handle to the report file to be written.
   * @throws BenchmarkReportFileAccessException
   *           if any error occurs while accessing the input benchmark file or
   *           while splitting it.
   * @throws BenchmarkReportExecutionException
   *           if the given input benchmark file is modified while generating
   *           the report.
   */
  private void splitBenchmarkFile(File benchmarkFile, File report)
      throws BenchmarkReportFileAccessException,
             BenchmarkReportInputFileFormatException {
    File dir = temporaryDirectory;
    // Folder already exists; then delete all files in the temporary folder
    if (dir.isDirectory()) {
      File files[] = dir.listFiles();
      for (int count = 0; count < files.length; count++) {
        if (!files[count].delete()) {
          throw new BenchmarkReportFileAccessException(
            "Could not delete files in the folder \"" +
            temporaryDirectory + "\"");
        }
      }
    } else if (!dir.mkdir()) {
      throw new BenchmarkReportFileAccessException(
        "Could not create  temporary folder \"" + temporaryDirectory + "\"");
    }

    // delete report2 from the filesystem
    if (getPrintMedia().equalsIgnoreCase(MEDIA_TEXT)) {
      deleteFile(new File(report.getAbsolutePath() + ".txt"));
    } else if (getPrintMedia().equalsIgnoreCase(MEDIA_HTML)) {
      deleteFile(new File(report.getAbsolutePath() + ".html"));
    }

    RandomAccessFile in = null;
    BufferedWriter out = null;
    try {
      String logEntry = "";
      long fromPos = 0;

      // File benchmarkFileName;
      if (getLogicalStart() != null) {
        fromPos = tail(benchmarkFile, FILE_CHUNK_SIZE);
      }
      in = new RandomAccessFile(benchmarkFile, "r");

      if (getLogicalStart() != null) {
        in.seek(fromPos);
      }
      ArrayList<String> startTokens = new ArrayList<String>();
      String lastStart = "";
      Pattern pattern = Pattern.compile("(\\d+) (\\d+) (.*) (.*) \\{(.*)\\}");
      Matcher matcher = null;
      File benchmarkFileName = null;
      while ((logEntry = in.readLine()) != null) {
        matcher = pattern.matcher(logEntry);
        String startToken = "";
        if (logEntry.matches(".*START.*")) {
          String[] splittedStartEntry = logEntry.split("\\s");
          if (splittedStartEntry.length > 2) {
            startToken = splittedStartEntry[2];
          } else {
            throw new BenchmarkReportInputFileFormatException(
                getBenchmarkFile() + " is invalid.");
          }

          if (startToken.endsWith("Start")) {
            continue;
          }
          if (!startTokens.contains(startToken)) {
            // create a new file for the new pipeline
            startTokens.add(startToken);
            benchmarkFileName = new File(
              temporaryDirectory, startToken + "_benchmark.txt");
            if (!benchmarkFileName.createNewFile()) {
              throw new BenchmarkReportFileAccessException(
                  "Could not create \"" + startToken + "_benchmark.txt"
                      + "\" in directory named \"" + temporaryDirectory + "\"");
            }
            out = new BufferedWriter(new FileWriter(benchmarkFileName));
            out.write(logEntry);
            out.newLine();
          }
        }
        // if a valid benchmark entry then write it to the pipeline specific
        // file
        if (matcher != null
            && matcher.matches()
            && (validateLogEntry(matcher.group(3), startTokens) || logEntry
                .matches(".*documentLoaded.*"))) {
          startToken = matcher.group(3).split("\\.")[0];
          if (!(lastStart.equals(startToken))) {
            if (out != null) { out.close(); }
            benchmarkFileName = new File(
              temporaryDirectory, startToken + "_benchmark.txt");
            out = new BufferedWriter(new FileWriter(benchmarkFileName, true));
          }
          if (out != null) {
            out.write(logEntry);
            out.newLine();
          }
          lastStart = startToken;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();

    } finally {
      try {
        if (in != null) { in.close(); }
        if (out != null) { out.close(); }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * A method for reading the file upside down.
   *
   * @param fileToBeRead
   *          An object of the file to be read.
   * @param chunkSize
   *          An integer specifying the size of the chunks in which file will be
   *          read.
   * @return A long value pointing to the start position of the given file
   *         chunk.
   */
  private long tail(File fileToBeRead, int chunkSize)
      throws BenchmarkReportInputFileFormatException {
    RandomAccessFile raf = null;
    try {
      raf = new RandomAccessFile(fileToBeRead, "r");
      Vector<String> lastNlines = new Vector<String>();
      int delta = 0;
      long curPos = 0;
      curPos = raf.length() - 1;
      long fromPos;
      byte[] bytearray;
      while (true) {
        fromPos = curPos - chunkSize;
        if (fromPos <= 0) {
          raf.seek(0);
          bytearray = new byte[(int) curPos];
          raf.readFully(bytearray);
          if (parseLinesFromLast(bytearray, lastNlines, fromPos)) {
            if (fromPos < 0)
              fromPos = 0;
          }
          break;
        } else {
          raf.seek(fromPos);
          bytearray = new byte[chunkSize];
          raf.readFully(bytearray);
          if (parseLinesFromLast(bytearray, lastNlines, fromPos)) {
            break;
          }
          delta = lastNlines.get(lastNlines.size() - 1).length();
          lastNlines.remove(lastNlines.size() - 1);
          curPos = fromPos + delta;
        }
      }
      if (fromPos < 0)
        throw new BenchmarkReportInputFileFormatException(getBenchmarkFile()
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
   * A method to ensure that the required line is read from the given file part.
   *
   * @param bytearray
   *          A part of a file being read upside down.
   * @param lastNlines
   *          A vector containing the lines extracted from file part.
   * @param fromPos
   *          A long value indicating the start of a file part.
   *
   * @return true if marker indicating the logical start of run is found; false
   *         otherwise.
   */
  private boolean parseLinesFromLast(byte[] bytearray,
                                     Vector<String> lastNlines, long fromPos) {
      String lastNChars = new String(bytearray);
      StringBuffer sb = new StringBuffer(lastNChars);
      lastNChars = sb.reverse().toString();
      StringTokenizer tokens = new StringTokenizer(lastNChars, NL);
      while (tokens.hasMoreTokens()) {
        StringBuffer sbLine = new StringBuffer(tokens.nextToken());
        lastNlines.add(sbLine.reverse().toString());
        if ((lastNlines.get(lastNlines.size() - 1))
            .trim().endsWith(getLogicalStart())) {
          return true;
        }
      }
      return false;
  }

  /**
   * Display a usage message
   */
  public static void usage() {
    System.out.println(
    "Usage: java gate.util.reporting.DocTimeReporter [Options]" + NL
  + "\t Options:" + NL
  + "\t -i input file path (default: benchmark.txt in the execution directory)" + NL
  + "\t -m print media - html/text (default: html)" + NL
  + "\t -d number of docs, use -1 for all docs (default: 10 docs)" + NL
  + "\t -p processing resource name to be matched (default: all_prs)" + NL
  + "\t -o output file path (default: report.html/txt in the system temporary directory)" + NL
  + "\t -l logical start (not set by default)" + NL
  + "\t -h show help" + NL);
  } // usage()

  /**
   * A main method which acts as a entry point while executing a report via
   * command line
   *
   * @param args
   *          A string array containing the command line arguments.
   * @throws BenchmarkReportExecutionException
   *           if a given input file is modified while generating the report.
   */
  public static void main(String[] args)
      throws BenchmarkReportInputFileFormatException,
             BenchmarkReportFileAccessException {
    // process command-line options
    DocTimeReporter reportTwo = new DocTimeReporter(args);
    reportTwo.generateReport();
  }

  /**
   * Calls store, calculate and printReport for generating the actual report.
   */
  private void generateReport() throws BenchmarkReportInputFileFormatException,
                                       BenchmarkReportFileAccessException {
    Timer timer = null;
    try {
      TimerTask task = new FileWatcher(getBenchmarkFile()) {
        @Override
        protected void onChange(File file) {
          throw new BenchmarkReportExecutionException(getBenchmarkFile()
              + " file has been modified while generating the report.");
        }
      };
      timer = new Timer();
      // repeat the check every second
      timer.schedule(task, new Date(), 1000);

      if (reportFile == null) {
        reportFile = new File(System.getProperty("java.io.tmpdir"),
          "report." + ((printMedia.equals(MEDIA_HTML)) ? "html" : "txt"));
      }
      splitBenchmarkFile(getBenchmarkFile(), reportFile);
      if (validEntries == 0) {
        if (logicalStart != null) {
          throw new BenchmarkReportInputFileFormatException(
            "No valid log entries present in " + getBenchmarkFile() +
            " does not contain a marker named " + logicalStart + ".");
        } else {
          throw new BenchmarkReportInputFileFormatException(
            "No valid log entries present in "
            + getBenchmarkFile().getAbsolutePath());
        }
      }
      File dir = temporaryDirectory;
      // Folder already exists; then delete all files in the temporary folder
      if (dir.isDirectory()) {
        File files[] = dir.listFiles();
        for (int count = 0; count < files.length; count++) {
          File inFile = files[count];
          Object report2Container1 = store(inFile);
          Object report2Container2 = calculate(report2Container1);
          printReport(report2Container2, reportFile);
        }
        if (files.length > 0 && files[0].exists()) {
          if (!files[0].delete()) {
            System.err.println(files[0] + " was not possible to delete.");
          }
        }
      }
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
  public void executeReport() throws BenchmarkReportInputFileFormatException,
                                     BenchmarkReportFileAccessException {
    generateReport();
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
    this.logicalStart = logicalStart;
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

  /**
   * Returns the maximum no of documents to be shown in the report.
   *
   * @return maxDocumentInReport An integer specifying the maximum no of
   *         documents to be shown in the report.
   */
  public int getMaxDocumentInReport() {
    return maxDocumentInReport;
  }

  /**
   * Maximum number of documents contained in the report.
   * @param maxDocumentInReport Maximum number of documents contained in
   * the report. Use the constant ALL_DOCS for reporting all documents.
   * The default is 10.
   */
  public void setMaxDocumentInReport(int maxDocumentInReport) {
    if (!(maxDocumentInReport > 0 || maxDocumentInReport == ALL_DOCS)) {
      throw new IllegalArgumentException(
        "Illegal argument: " + maxDocumentInReport);
    }
    this.maxDocumentInReport = maxDocumentInReport;
  }

  /**
   * Returns the search string to be matched to PR names present in the log
   * entries.
   *
   * @return PRMatchingRegex A String to be matched to PR names present in the
   *         log entries.
   */
  public String getPRMatchingRegex() {
    return PRMatchingRegex;
  }

  /**
   * Search string to match PR names present in the benchmark file.
   *
   * @param matchingRegex regular expression to match PR names
   * present in the benchmark file. The default is MATCH_ALL_PR_REGEX.
   */
  public void setPRMatchingRegex(String matchingRegex) {
    PRMatchingRegex = matchingRegex;
  }
}

/**
 * A FileWather class to check whether the file is modified or not at specified
 * interval.
 */
abstract class FileWatcher extends TimerTask {
  private long timeStamp;
  private File file;

  /**
   * Creates a FileWatcher on a given file.
   *
   * @param file
   *          A handle of the file to be watched.
   */
  public FileWatcher(File file) {
    this.file = file;
    timeStamp = file.lastModified();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.TimerTask#run()
   */
  @Override
  public final void run() {
    long oldTimeStamp = file.lastModified();
    if (timeStamp != oldTimeStamp) {
      cancel();
      onChange(file);
    }
  }

  /**
   * Specifies the actions to be taken when a file is modified.
   *
   * @param file
   *          A handle of the file to be watched.
   */
  protected abstract void onChange(File file)
    throws BenchmarkReportExecutionException;
}
