/*
 *  TestDocument.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 21/Jan/00
 *
 *  $Id: TestDocument.java 19621 2016-10-04 05:35:22Z markagreenwood $
 */

package gate.corpora;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import junit.framework.*;

import gate.*;
import gate.util.BomStrippingInputStreamReader;
import gate.util.Err;
import gate.util.SimpleFeatureMapImpl;

/** Tests for the Document classes
  */
public class TestDocument extends TestCase
{
  
  /** Base of the test server URL */
  protected static String testServer = null;

  /** Name of test document 1 */
  protected String testDocument1;

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {

    
      if (!Gate.isInitialised()) {
        Gate.runInSandbox(true);
        Gate.init();
      }
    
    
    //try{
//      Gate.init();
      //testServer = Gate.getUrl().toExternalForm();
      testServer = getTestServerName();
    //} catch (GateException e){
    //  e.printStackTrace(Err.getPrintWriter());
    //}

    testDocument1 = "tests/html/test2.htm";
  } // setUp

  /** Get the name of the test server */
  public static String getTestServerName() {
    if(testServer != null)
      return testServer;
    else {
      try {
        URL url =
            Gate.getClassLoader().getResource("gate/resources/gate.ac.uk/");

        testServer = url.toExternalForm();
      }

      catch(Exception e) {
      }
      return testServer;
    }
  }

  /** Test ordering */
  public void testCompareTo() throws Exception{
    Document doc1 = null;
    Document doc2 = null;
    Document doc3 = null;


    doc1 = Factory.newDocument(new URL(testServer + "tests/def"));
    doc2 = Factory.newDocument(new URL(testServer + "tests/defg"));
    doc3 = Factory.newDocument(new URL(testServer + "tests/abc"));

    assertTrue(doc1.compareTo(doc2) < 0);
    assertTrue(doc1.compareTo(doc1) == 0);
    assertTrue(doc1.compareTo(doc3) > 0);

  } // testCompareTo()

  /** Test loading of the original document content */

  public void testOriginalContentPreserving() throws Exception {
    Document doc = null;
    FeatureMap params;
    String encoding = "UTF-8";
    String origContent;

    // test the default value of preserve content flag
    params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(testServer + testDocument1));
    params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, encoding);
    doc =
      (Document) Factory.createResource("gate.corpora.DocumentImpl", params);

    origContent = (String) doc.getFeatures().get(
      GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);

    assertNull(
      "The original content should not be preserved without demand.",
      origContent);

    params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME,
      new URL(testServer + testDocument1));
    params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, encoding);
    params.put(Document.DOCUMENT_PRESERVE_CONTENT_PARAMETER_NAME, new Boolean(true));
    doc =
      (Document) Factory.createResource("gate.corpora.DocumentImpl", params);

    origContent = (String) doc.getFeatures().get(
      GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);

    assertNotNull("The original content is not preserved on demand.",
              origContent);

    assertTrue("The original content size is zerro.", origContent.length()>0);
  } // testOriginalContentPreserving()

  /** A comprehensive test */
  public void testLotsOfThings() {

    // check that the test URL is available
    URL u = null;
    try{
      u = new URL(testServer + testDocument1);
    } catch (Exception e){
      e.printStackTrace(Err.getPrintWriter());
    }

    // get some text out of the test URL
    BufferedReader uReader = null;
    try {
      uReader = new BomStrippingInputStreamReader(u.openStream());
      assertEquals(uReader.readLine(), "<HTML>");
    } catch(UnknownHostException e) { // no network connection
      return;
    } catch(IOException e) {
      fail(e.toString());
    }
    /*
    Document doc = new TextualDocument(testServer + testDocument1);
    AnnotationGraph ag = new AnnotationGraphImpl();

    Tokeniser t = ...   doc.getContent()
    tokenise doc using java stream tokeniser

    add several thousand token annotation
    select a subset
    */
  } // testLotsOfThings


  public void testDocRender() throws Exception
  {
      Document doc = Factory.newDocument("Hi Mom");
      doc.getAnnotations().add(new Long(0), new Long(2),
          "Foo", new SimpleFeatureMapImpl());
      String content = doc.toXml(doc.getAnnotations(), false);

      // Will fail, content is "<Foo>Hi Mom</Foo>"
      assertEquals("<Foo>Hi</Foo> Mom", content);
  }


  /** The reason this is method begins with verify and not with test is that it
   *  gets called by various other test methods. It is somehow a utility test
   *  method. It should be called on all gate documents having annotation sets.
   */
  public static void verifyNodeIdConsistency(gate.Document doc)throws Exception{
      if (doc == null) return;
      Map<Long,Integer> offests2NodeId = new HashMap<Long,Integer>();
      // Test the default annotation set
      AnnotationSet annotSet = doc.getAnnotations();
      verifyNodeIdConsistency(annotSet,offests2NodeId, doc);
      // Test all named annotation sets
      if (doc.getNamedAnnotationSets() != null){
        Iterator<AnnotationSet> namedAnnotSetsIter =
                              doc.getNamedAnnotationSets().values().iterator();
        while(namedAnnotSetsIter.hasNext()){
         verifyNodeIdConsistency(namedAnnotSetsIter.next(),offests2NodeId,doc);
        }// End while
      }// End if
      // Test suceeded. The map is not needed anymore.
      offests2NodeId = null;
  }// verifyNodeIdConsistency();

  /** This metod runs the test over an annotation Set. It is called from her
   *  older sister. Se above.
   *  @param annotSet is the annotation set being tested.
   *  @param offests2NodeId is the Map used to test the consistency.
   *  @param doc is used in composing the assert error messsage.
   */
  public static void verifyNodeIdConsistency(gate.AnnotationSet annotSet,
                                             Map<Long,Integer>  offests2NodeId,
                                             gate.Document doc)
                                                              throws Exception{

      if (annotSet == null || offests2NodeId == null) return;

      Iterator<Annotation> iter = annotSet.iterator();
      while(iter.hasNext()){
        Annotation annot = iter.next();
        String annotSetName = (annotSet.getName() == null)? "Default":
                                                          annotSet.getName();
        // check the Start node
        if (offests2NodeId.containsKey(annot.getStartNode().getOffset())){
             assertEquals("Found two different node IDs for the same offset( "+
             annot.getStartNode().getOffset()+ " ).\n" +
             "START NODE is buggy for annotation(" + annot +
             ") from annotation set " + annotSetName + " of GATE document :" +
             doc.getSourceUrl(),
             annot.getStartNode().getId(),
             offests2NodeId.get(annot.getStartNode().getOffset()));
        }// End if
        // Check the End node
        if (offests2NodeId.containsKey(annot.getEndNode().getOffset())){
             assertEquals("Found two different node IDs for the same offset("+
             annot.getEndNode().getOffset()+ ").\n" +
             "END NODE is buggy for annotation(" + annot+ ") from annotation"+
             " set " + annotSetName +" of GATE document :" + doc.getSourceUrl(),
             annot.getEndNode().getId(),
             offests2NodeId.get(annot.getEndNode().getOffset()));
        }// End if
        offests2NodeId.put(annot.getStartNode().getOffset(),
                                                  annot.getStartNode().getId());
        offests2NodeId.put(annot.getEndNode().getOffset(),
                                                    annot.getEndNode().getId());
    }// End while
  }//verifyNodeIdConsistency();

  /**
   * Test to verify behaviour of the mimeType init parameter.
   */
  public void testExplicitMimeType() throws Exception {
    // override the user config to make sure we DON'T add extra space on
    // unpackMarkup when parsing XML, whatever is set in the user config file.
    Object savedAddSpaceValue = Gate.getUserConfig().get(
        GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME);
    Gate.getUserConfig().put(
        GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME, "false");

    try {
      String testXmlString = "<p>This is a <strong>TEST</strong>.</p>";
      String xmlParsedContent = "This is a TEST.";
      String htmlParsedContent = "This is a TEST.\n";

      // if we create a Document from this string WITHOUT setting a mime type,
      // it should be treated as plain text and not parsed.
      FeatureMap docParams = Factory.newFeatureMap();
      docParams.put(Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME,
          testXmlString);
      docParams.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME,
          Boolean.TRUE);

      Document noMimeTypeDoc = (Document)Factory.createResource(
          DocumentImpl.class.getName(), docParams);

      assertEquals("Document created with no explicit mime type should have "
          + "unparsed XML as content.", testXmlString,
          noMimeTypeDoc.getContent().toString());

      assertEquals("Document created with no explicit mime type should not "
          + "have any Original markups annotations.", 0,
          noMimeTypeDoc.getAnnotations(
            GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME).size());

      Factory.deleteResource(noMimeTypeDoc);
      noMimeTypeDoc = null;

      // if we create the same document with an explicit mime type of text/xml,
      // it should be parsed properly, and have two original markups
      // annotations.
      docParams.put(Document.DOCUMENT_MIME_TYPE_PARAMETER_NAME, "text/xml");

      Document xmlDoc = (Document)Factory.createResource(
          DocumentImpl.class.getName(), docParams);

      assertEquals("Document created with explicit mime type should have been "
          + "parsed as XML.", xmlParsedContent,
          xmlDoc.getContent().toString());

      assertEquals("Document created with explicit mime type has wrong number "
          + "of Original markups annotations.", 2,
          xmlDoc.getAnnotations(
            GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME).size());

      Factory.deleteResource(xmlDoc);
      xmlDoc = null;

      // if we create the same document with an explicit mime type of text/html,
      // it should be parsed properly and have *4* original markups
      // annotations, as the HTML parser creates enclosing <html> and <body>
      // elements and a zero-length <head> annotation.
      docParams.put(Document.DOCUMENT_MIME_TYPE_PARAMETER_NAME, "text/html");

      Document htmlDoc = (Document)Factory.createResource(
          DocumentImpl.class.getName(), docParams);

      assertEquals("Document created with explicit mime type should have been "
          + "parsed as HTML.", htmlParsedContent,
          htmlDoc.getContent().toString());

      assertEquals("Document created with explicit mime type has wrong number "
          + "of Original markups annotations.", 5,
          htmlDoc.getAnnotations(
            GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME).size());

      Factory.deleteResource(htmlDoc);
      htmlDoc = null;
    }
    finally {
      // restore the saved value for ADD_SPACE_ON_MARKUP_UNPACK
      if(savedAddSpaceValue == null) {
        Gate.getUserConfig().remove(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME);
      }
      else {
        Gate.getUserConfig().put(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME,
            savedAddSpaceValue);
      }
    }
  }

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestDocument.class);
  } // suite

} // class TestDocument
