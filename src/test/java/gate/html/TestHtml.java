/*
 *  TestHtml.java
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
 *  $Id: TestHtml.java 19622 2016-10-04 07:15:52Z markagreenwood $
 */

package gate.html;

import java.net.URL;

import gate.Gate;
import gate.corpora.MimeType;
import gate.corpora.TestDocument;
import junit.framework.TestCase;


/** Test class for HTML facilities
  */
public class TestHtml extends TestCase
{

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  } // setUp

  /** A test */
  public void testUnpackMarkup() throws Exception {

    gate.Document doc = null;
    /*
    markupElementsMap = new HashMap();
    // populate it
    markupElementsMap.put ("h1","Header 1");
    markupElementsMap.put ("H1","Header 1");
    markupElementsMap.put ("A","link");
    markupElementsMap.put ("a","link");
    */
  doc = gate.Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/html/test1.htm"));
// doc = gate.Factory.newDocument(new URL("http://www"));

   // get the docFormat that deals with it.
   gate.DocumentFormat docFormat = gate.DocumentFormat.getDocumentFormat(
                                                        doc, doc.getSourceUrl()
                                                        );
    assertTrue( "Bad document Format was produced. HtmlDocumentFormat was expected",
            docFormat.getMimeType().equals(new MimeType("text","html"))
          );


    // set's the map
    // Don't need to unpack markup explicitly, as it is already unpacked by
    // default by newDocument - unpacking it twice causes exceptions
    //docFormat.setMarkupElementsMap(markupElementsMap);
    //docFormat.unpackMarkup (doc,"DocumentContent");

    gate.corpora.TestDocument.verifyNodeIdConsistency(doc);
/*
    // Save it as XML
    File xmlFile = null;
    xmlFile = Files.writeTempFile(null);

    OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(xmlFile),"UTF-8");
    // Write (test the toXml() method)
    writer.write(doc.toXml());
    writer.flush();
    writer.close();
*/
  } // testUnpackMarkup()
//*


}//class TestHtml
