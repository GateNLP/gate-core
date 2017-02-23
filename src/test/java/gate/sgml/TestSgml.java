/*
 *  TestSgml.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  8/May/2000
 *
 *  $Id: TestSgml.java 17656 2014-03-14 08:55:23Z markagreenwood $
 */

package gate.sgml;

import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.GateConstants;
import gate.corpora.TestDocument;

import java.net.URL;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** Test class for SGML facilities
  */
public class TestSgml extends TestCase
{
  /** Construction */
  public TestSgml(String name) { super(name); }

  /** Fixture set up */
  @Override
  public void setUp() {
  } // setUp

  public void testSgmlLoading() throws Exception {
    assertTrue(true);

    // create the markupElementsMap map
    Map<String,String> markupElementsMap = null;
    gate.Document doc = null;
    /*
    markupElementsMap = new HashMap();
    // populate it
    markupElementsMap.put ("S","Sentence");
    markupElementsMap.put ("s","Sentence");
    markupElementsMap.put ("W","Word");
    markupElementsMap.put ("w","Word");
    */

    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/sgml/Hds.sgm"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    doc = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);

    // get the docFormat that deals with it.
    // the parameter MimeType doesn't affect right now the behaviour
    //*
    gate.DocumentFormat docFormat = gate.DocumentFormat.getDocumentFormat (
        doc, doc.getSourceUrl()
    );
    assertTrue( "Bad document Format was produced. SgmlDocumentFormat was expected",
            docFormat instanceof gate.corpora.SgmlDocumentFormat
          );

    // set's the map
    docFormat.setMarkupElementsMap(markupElementsMap);
    docFormat.unpackMarkup (doc,"DocumentContent");
    AnnotationSet annotSet = doc.getAnnotations(
                        GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    assertEquals("For "+doc.getSourceUrl()+" the number of annotations"+
    " should be:1022",1022,annotSet.size());
    // Verfy if all annotations from the default annotation set are consistent
    gate.corpora.TestDocument.verifyNodeIdConsistency(doc);
  }// testSgml

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestSgml.class);
  } // suite

} // class TestSgml
