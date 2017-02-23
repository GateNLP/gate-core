/*
 *  TestCreoleAnnotationHandler.java
 *
 *  Copyright (c) 2008, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 27/Jul/2008
 *
 *  $Id: TestCreoleAnnotationHandler.java 19985 2017-01-25 14:20:13Z markagreenwood $
 */

package gate.creole;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import gate.Gate;
import gate.corpora.TestDocument;
import gate.util.GateException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test for the CreoleAnnotationHandler, compares the XML produced by the
 * annotation handler to an expected result.
 */
public class TestCreoleAnnotationHandler extends TestCase {

  private SAXBuilder jdomBuilder = new SAXBuilder();

  private DOMOutputter jdom2dom = new DOMOutputter();

  private DocumentBuilderFactory jaxpFactory = DocumentBuilderFactory.newInstance();

  /** Construction */
  public TestCreoleAnnotationHandler(String name) throws GateException {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    // Initialise the GATE library and creole register
    Gate.init();

    XMLUnit.setIgnoreComments(true);
    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setIgnoreAttributeOrder(true);
  }

  /**
   * Take a skeleton creole.xml file, process the annotations on the classes it
   * mentions and compare the resulting XML to the expected result.
   */
  public void testCreoleAnnotationHandler() throws Exception {
    URL originalUrl = new URL(TestDocument.getTestServerName()+"tests/creole-annotation-handler/initial-creole.xml");
    org.jdom.Document creoleXml =
      jdomBuilder.build(originalUrl.openStream());
    CreoleAnnotationHandler processor = new CreoleAnnotationHandler(new Plugin.Directory(new URL(TestDocument.getTestServerName()+"tests/creole-annotation-handler/")));
    processor.processAnnotations(creoleXml);

    URL expectedURL = new URL(TestDocument.getTestServerName()+"tests/creole-annotation-handler/expected-creole.xml");

    // XMLUnit requires the expected and actual results as W3C DOM rather than
    // JDOM
    DocumentBuilder docBuilder = jaxpFactory.newDocumentBuilder();
    org.w3c.dom.Document targetXmlDOM =
      docBuilder.parse(expectedURL.openStream());

    org.w3c.dom.Document actualXmlDOM = jdom2dom.output(creoleXml);

    Diff diff = XMLUnit.compareXML(targetXmlDOM, actualXmlDOM);

    // compare parameter elements with the same NAME, resources with the same
    // CLASS, and all other elements that have the same element name
    diff.overrideElementQualifier(new ElementNameQualifier() {
      @Override
      public boolean qualifyForComparison(Element control, Element test) {
        if("PARAMETER".equals(control.getTagName()) && "PARAMETER".equals(test.getTagName())) {
          return control.getAttribute("NAME").equals(test.getAttribute("NAME"));
        }
        else if("RESOURCE".equals(control.getTagName()) && "RESOURCE".equals(test.getTagName())) {
          String controlClass = findClass(control);
          String testClass = findClass(test);
          return (controlClass == null) ? (testClass == null)
                    : controlClass.equals(testClass);
        }
        else {
          return super.qualifyForComparison(control, test);
        }
      }

      private String findClass(Element resource) {
        Node node = resource.getFirstChild();
        while(node != null && !"CLASS".equals(node.getNodeName())) {
          node = node.getNextSibling();
        }

        if(node != null) {
          return node.getTextContent();
        }
        else {
          return null;
        }
      }
    });

    // do the comparison!
    boolean match = diff.similar();
    if(!match) {
      // if comparison failed, output the "actual" result document for
      // debugging purposes
      System.err.println("---------actual-----------");   	
      new XMLOutputter(Format.getPrettyFormat()).output(creoleXml, System.err);
    }

    assertTrue("XML produced by annotation handler does not match expected: "
        + diff.toString() , match);
  }

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestCreoleAnnotationHandler.class);
  } // suite

}
