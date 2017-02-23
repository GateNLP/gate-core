/*
 *  BenchmarkReportInputFileFormatException.java
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

package gate.util.reporting.exceptions;

/**
 * Thrown in following situations
 * <ul>
 * <li> No valid log entries present in input benchmark file. </li>
 * <li> Input benchmark file does not contain a marker for logical start of a
 * run. </li>
 * </ul>
 */
public class BenchmarkReportInputFileFormatException extends
    BenchmarkReportException {

  private static final long serialVersionUID = 7410148749197145786L;

  public BenchmarkReportInputFileFormatException(String message) {
    super(message);
  }
}
