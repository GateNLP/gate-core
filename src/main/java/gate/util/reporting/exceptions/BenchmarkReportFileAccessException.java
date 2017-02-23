/*
 *  BenchmarkReportFileAccessException.java
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
 * Thrown in following situations -
 * <ul>
 * <li> Input benchmark file does not exist. </li>
 * <li> Could not read input benchmark file. </li>
 * <li> Could not create output report files or its parent directories. </li>
 * <li> Could not read input benchmark file </li>
 * <li> Could not write to output report files. </li>
 * <li> Couldn't delete the given file. </li>
 * <li> Couldn't create the temporary benchmark.txt for a particular pipeline in
 * case when the given input file contain interleaved entries from multiple
 * pipeline. </li>
 * </ul>
 */
public class BenchmarkReportFileAccessException extends
    BenchmarkReportException {

  private static final long serialVersionUID = -1095399127391838299L;

  public BenchmarkReportFileAccessException(String message) {
    super(message);
  }
}
