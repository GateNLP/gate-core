/*
 *  TestFiles.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 10/June/00
 *
 *  $Id: TestFiles.java 19909 2017-01-03 13:45:54Z markagreenwood $
 */

package gate.util;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;

import junit.framework.*;

/** Files test class.
  */
public class TestFiles extends TestCase
{
  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Fixture set up */
  @Override
  public void setUp() {
  } // setUp

  /** Test the getResourceAs... methods. */
  public void testGetResources() throws Exception {
    assertTrue(true);
    String japeResName = "gate.ac.uk/tests/ft-bt-03-aug-2001.html";
    String firstLine = "<!-- Vignette V/5 Mon Aug 06 07:50:01 2001 -->";

    BufferedReader bufResReader =
      new BomStrippingInputStreamReader(Files.getGateResourceAsStream(japeResName));
    assertTrue(bufResReader.readLine().equals(firstLine));
    bufResReader.close();

    String resString = Files.getGateResourceAsString(japeResName);
    assertTrue(resString.startsWith(firstLine));

    byte[] resBytes = Files.getGateResourceAsByteArray(japeResName);

    /*
    Out.println(new String(resBytes));
    Out.println(resBytes.length);
    Out.println(resString);
    Out.println(resString.length());
    */

    char resChars[] = new char[firstLine.length()];
    for(int i=0; i<resChars.length; i++) resChars[i] = (char)resBytes[i];
    resString = new String(resChars);
    assertTrue(resString, resString.equals(firstLine));

  } // testGetResources()

  /** Test the writeTempFile... method. */
  public void testWriteTempFile() throws Exception {
    assertTrue(true);
    String japeResName = "gate.ac.uk/tests/ft-bt-03-aug-2001.html";
    String firstLine = "<!-- Vignette V/5 Mon Aug 06 07:50:01 2001 -->";

    File f = Files.writeTempFile(Files.getGateResourceAsStream(japeResName));
    BufferedReader bfr = null;
    
    try {
      bfr = new BufferedReader(new FileReader(f));
  
      String firstLn = bfr.readLine();
      assertTrue("first line from jape/combined/testloc.jape doesn't match",
        firstLine.equals(firstLn));
  
      f.delete ();
    }
    finally {
      IOUtils.closeQuietly(bfr);
    }
  } // testWriteTempFile()

  /** Test the find method. */
  public void testFind(){
    String regex = "z:/gate2/doc/.*.html";
    String filePath = "z:/gate2/doc";
    Iterator<String> iter;
    
    Set<String> regfind = Files.Find(regex,filePath);
    iter = regfind.iterator();
    if (iter.hasNext()){
      while (iter.hasNext()){
        @SuppressWarnings("unused")
        String verif = iter.next();
        //Out.println(verif);
      }
    }
  } // testFind

  /** Test the updateXmlElement method. */
  public void testUpdateXmlElement() throws IOException {
    String nl = Strings.getNl();
    String configElementName = "GATECONFIG";
    String configElement = "<" + configElementName + " FULLSIZE=\"yes\"/>";
    String exampleXmlStart =
      "<?xml version=\"1.0\"?>" + nl +
      "<!-- a comment -->" + nl +
      "<GATE>" + nl +
      "" + nl +
      "<CREOLE-DIRECTORY>http://on.the.net/</CREOLE-DIRECTORY>" + nl +
      "<!--- The next element may be overwritten by the GUI --->" + nl;
    String exampleXmlEnd =
      "</GATE>" + nl;
    String exampleXml = exampleXmlStart + configElement + nl + exampleXmlEnd;

    // check that the getEmptyElement method works
    assertTrue(
      "the GATECONFIG element doesn't match",
      getEmptyElement(exampleXml, configElementName).equals(configElement)
    );

    // a map of values to place in the new element
    Map<String,String> newAttrs = new HashMap<String,String>();
    newAttrs.put("a", "1");
    newAttrs.put("b", "2");
    newAttrs.put("c", "3");
    newAttrs.put("d", "needs&escaping");

    // test the files method
    String newXml = Files.updateXmlElement(
      new BufferedReader(new StringReader(exampleXml)),
      configElementName,
      newAttrs
    );
    assertTrue(
      "newXml doesn't match (1): " + newXml.toString(),
      newXml.toString().startsWith(exampleXmlStart) &&
        newXml.toString().endsWith(exampleXmlEnd) &&
        newXml.toString().contains("a=\"1\"") &&
        newXml.toString().contains("d=\"needs&amp;escaping\"")
    );
    if(DEBUG) Out.prln(newXml);

    // write the example data into a temp file and try on that
    File tempFile = Files.writeTempFile(exampleXml);
    newXml = Files.updateXmlElement(tempFile, configElementName, newAttrs);
    assertTrue(
      "newXml doesn't match (2): " + newXml.toString(),
      newXml.toString().startsWith(exampleXmlStart) &&
        newXml.toString().endsWith(exampleXmlEnd) &&
        newXml.toString().contains("a=\"1\"") &&
        newXml.toString().contains("d=\"needs&amp;escaping\"")
    );
    if(DEBUG) Out.prln(newXml);

    // check that the file was overwritten successfully
    newXml = Files.getString(tempFile);
    assertTrue(
      "newXml doesn't match (3): " + newXml.toString(),
      newXml.toString().startsWith(exampleXmlStart) &&
        newXml.toString().endsWith(exampleXmlEnd) &&
        newXml.toString().contains("a=\"1\"") &&
        newXml.toString().contains("d=\"needs&amp;escaping\"")
    );
    if(DEBUG) Out.prln(newXml);

  } // updateXmlElement

  /**
   * Helper method to extract an empty element from a string
   * containing XML
   */
  String getEmptyElement(String xml, String elementName) {
    // find the index of "<elementName"; find the next ">"
    int start = xml.indexOf("<" + elementName);
    int end = xml.indexOf(">", start);

    // get the range between the two indices
    StringBuffer xmlBuf = new StringBuffer(xml);
    String substr = xmlBuf.substring(start, end + 1);
    if(DEBUG) {
      Out.prln("start=" + start + "; end=" + end + "; substr=" + substr);
    }

    return substr;
  } // getEmptyElement

} // class TestFiles
