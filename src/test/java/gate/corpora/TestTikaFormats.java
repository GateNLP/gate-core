/*
 * TestTikaFormats.java
 *
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Mark A. Greenwood, 16/01/2012
 *
 * $Id: TestTikaFormats.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.corpora;

import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.util.GateException;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests to make sure the Tika format parsers work as expected
 */
public class TestTikaFormats extends TestCase {

  @Override
  public void setUp() throws GateException {
    Gate.init();
  }

  private void doTest(String ext) throws Exception {
    String base = TestDocument.getTestServerName();

    URL url = new URL(base + "tests/tika/tika-test." + ext);

    Document doc = Factory.newDocument(url);

    assertNotNull(doc);

    assertTrue(doc.getContent().toString().indexOf("Testing Tika Format Parsers") != -1);
  }

  public void testDOC() throws Exception {
    doTest("doc");
  }

  public void testDOCX() throws Exception {
    doTest("docx");
  }

  public void testODT() throws Exception {
    doTest("odt");
  }

  public void testRTF() throws Exception {
    doTest("rtf");
  }

  public void testPDF() throws Exception {
    doTest("pdf");
  }

  public void testODP() throws Exception {
    doTest("odp");
  }

  public void testPPT() throws Exception {
    doTest("ppt");
  }

  public void testPPTX() throws Exception {
    doTest("pptx");
  }

  public void testXLS() throws Exception {
    doTest("xls");
  }

  public void testXLSX() throws Exception {
    doTest("xlsx");
  }

  public void testODS() throws Exception {
      doTest("ods");
  }

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestTikaFormats.class);
  }
}
