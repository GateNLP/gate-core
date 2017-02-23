/*
 *  BenchmarkReportable.java
 *
 *  Copyright (c)  2008-2009, Intelius, Inc.
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

import java.io.File;

import gate.util.reporting.exceptions.*;

/**
 * An interface to be implemented by all classes responsible for generating
 * benchmark reports.
 */
public interface BenchmarkReportable {
  /**
   * Organizes the log entries in report specific data structure.
   *
   * @param inputFile
   *          An input benchmark file handle.
   * @return An object containing the log entries organized in a report specific
   *         data structure.
   * @throws BenchmarkReportException
   *           if an error occurs while organizing the log entries.
   */
  public Object store(File inputFile) throws BenchmarkReportException;

  /**
   * Does the report specific calculations.
   *
   * @param reportContainer
   *          An object containing the log entries organized in a report
   *          specific data structure.
   * @return An object containing the log entries organized in a report specific
   *         data structure with report totals calculated.
   * @throws BenchmarkReportException
   *           if computation error occurs.
   */
  public Object calculate(Object reportContainer)
      throws BenchmarkReportException;

  /**
   * Prints a report in text or HTML format.
   *
   * @param reportContainer
   *          An object containing the log entries organized in a report
   *          specific data structure.
   * @param outputFile
   *          An output report file handle.
   * @throws BenchmarkReportException
   *           if report couldn't be printed to given media (text/HTML).
   */
  public void printReport(Object reportContainer, File outputFile)
      throws BenchmarkReportException;

  /**
   * Parses the command line arguments.
   *
   * @param args
   *          A string array containing command line parameters.
   * @throws BenchmarkReportException
   *           if an invalid argument is provided.
   */
  public void parseArguments(String[] args) throws BenchmarkReportException;

  /**
   * A single method to execute report (A command line counter part API ). Call
   * this method after setting the report parameters.
   *
   * @throws BenchmarkReportException
   *           if an error occurs while generating the report.
   * @throws BenchmarkReportExecutionException
   *           if the given input file is modified while generating the report.
   */
  public void executeReport() throws BenchmarkReportException,
      BenchmarkReportExecutionException;
}
