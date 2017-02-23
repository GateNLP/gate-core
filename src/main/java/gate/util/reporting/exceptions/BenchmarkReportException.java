/*
 *  BenchmarkReportException.java
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
 * The class BenchmarkReportException and its subclasses indicate conditions
 * that the calling application might want to catch.
 */
public class BenchmarkReportException extends Exception {

  private static final long serialVersionUID = 7985671419407663629L;

  public BenchmarkReportException(String message) {
    super(message);
  }
}
