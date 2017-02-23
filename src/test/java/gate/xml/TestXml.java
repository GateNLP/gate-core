/*
 *  TestXml.java
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
 *  $Id: TestXml.java 19635 2016-10-05 09:52:33Z markagreenwood $
 */

package gate.xml;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentFormat;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.DocumentImpl;
import gate.corpora.TestDocument;
import gate.util.Files;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//import org.w3c.www.mime.*;


/** Test class for XML facilities
  *
  */
public class TestXml extends TestCase
{
  /** The encoding used in our tests*/
  private static String workingEncoding="UTF-8";

  /** Construction */
  public TestXml(String name) { super(name); }

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  } // setUp

  public void testGateDocumentToAndFromXmlWithDifferentKindOfFormats()
                                                               throws Exception{
    List<URL> urlList = new LinkedList<URL>();
    List<String> urlDescription = new LinkedList<String>();
    URL url = null;

    url = new URL(TestDocument.getTestServerName()+"tests/xml/xces.xml");
    assertTrue("Coudn't create a URL object for tests/xml/xces.xml ", url != null);
    urlList.add(url);
    urlDescription.add(" an XML document ");

    url = new URL(TestDocument.getTestServerName()+"tests/xml/Sentence.xml");
    assertTrue("Coudn't create a URL object for tests/xml/Sentence.xml",
                                                         url != null);
    urlList.add(url);
    urlDescription.add(" an XML document ");

    url = new URL(TestDocument.getTestServerName()+"tests/html/test1.htm");
    assertTrue("Coudn't create a URL object for tests/html/test.htm",url != null);
    urlList.add(url);
    urlDescription.add(" an HTML document ");

    url = new URL(TestDocument.getTestServerName()+"tests/email/test2.eml");
    assertTrue("Coudn't create a URL object for defg ",url != null);
    urlList.add(url);
    urlDescription.add(" an EMAIL document ");

    Iterator<URL> iter = urlList.iterator();
    Iterator<String> descrIter = urlDescription.iterator();
    while(iter.hasNext()){
      runCompleteTestWithAFormat(iter.next(), descrIter.next());
    }// End While


  }// testGateDocumentToAndFromXmlWithDifferentKindOfFormats

  private void runCompleteTestWithAFormat(URL url, String urlDescription)
                                                             throws Exception{
    // Load the xml Key Document and unpack it
    gate.Document keyDocument = null;

    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, url);
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    keyDocument = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);

    assertTrue("Coudn't create a GATE document instance for " +
            url.toString() +
            " Can't continue." , keyDocument != null);

    gate.DocumentFormat keyDocFormat = null;
    keyDocFormat = gate.DocumentFormat.getDocumentFormat(
      keyDocument, keyDocument.getSourceUrl()
    );

    assertTrue("Fail to recognize " +
            url.toString() +
            " as being " + urlDescription + " !", keyDocFormat != null);

    // Unpack the markup
    keyDocFormat.unpackMarkup(keyDocument);
    // Verfy if all annotations from the default annotation set are consistent
    gate.corpora.TestDocument.verifyNodeIdConsistency(keyDocument);

    // Verifies if the maximum annotation ID on the GATE doc is less than the
    // Annotation ID generator of the document.
    verifyAnnotationIDGenerator(keyDocument);

    // Save the size of the document and the number of annotations
    long keyDocumentSize = keyDocument.getContent().size().longValue();
    int keyDocumentAnnotationSetSize = keyDocument.getAnnotations().size();


    // Export the Gate document called keyDocument as  XML, into a temp file,
    // using the working encoding
    File xmlFile = null;
    xmlFile = Files.writeTempFile(keyDocument.toXml(), workingEncoding );
    assertTrue("The temp GATE XML file is null. Can't continue.",xmlFile != null);

    // Load the XML Gate document form the tmp file into memory
    gate.Document gateDoc = null;
    gateDoc = gate.Factory.newDocument(xmlFile.toURI().toURL(), workingEncoding);

    assertTrue("Coudn't create a GATE document instance for " +
                xmlFile.toURI().toURL().toString() +
                " Can't continue." , gateDoc != null);

    gate.DocumentFormat gateDocFormat = null;
    gateDocFormat =
            DocumentFormat.getDocumentFormat(gateDoc,gateDoc.getSourceUrl());

    assertTrue("Fail to recognize " +
      xmlFile.toURI().toURL().toString() +
      " as being a GATE XML document !", gateDocFormat != null);

    gateDocFormat.unpackMarkup(gateDoc);
    // Verfy if all annotations from the default annotation set are consistent
    gate.corpora.TestDocument.verifyNodeIdConsistency(gateDoc);

    // Save the size of the document snd the number of annotations
    long gateDocSize = keyDocument.getContent().size().longValue();
    int gateDocAnnotationSetSize = keyDocument.getAnnotations().size();

    assertTrue("Exporting as GATE XML resulted in document content size lost." +
      " Something went wrong.", keyDocumentSize == gateDocSize);

    assertTrue("Exporting as GATE XML resulted in annotation lost." +
      " No. of annotations missing =  " +
      Math.abs(keyDocumentAnnotationSetSize - gateDocAnnotationSetSize),
      keyDocumentAnnotationSetSize == gateDocAnnotationSetSize);

    // Verifies if the maximum annotation ID on the GATE doc is less than the
    // Annotation ID generator of the document.
    verifyAnnotationIDGenerator(gateDoc);

    //Don't need tmp Gate XML file.
    xmlFile.delete();
  }//runCompleteTestWithAFormat

  /** A test */
  public void testUnpackMarkup() throws Exception{
    // create the markupElementsMap map
    //Map markupElementsMap = null;
    gate.Document doc = null;
    /*
    markupElementsMap = new HashMap();
    // populate it
    markupElementsMap.put ("S","Sentence");
    markupElementsMap.put ("s","Sentence");
    */
    // Create the element2String map
    Map<String,String> anElement2StringMap = new HashMap<String,String>();

    // Populate it
    anElement2StringMap.put("S","\n");
    anElement2StringMap.put("s","\n");

    doc = gate.Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/xml/xces.xml"), workingEncoding);

    AnnotationSet annotSet = doc.getAnnotations(
                        GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    assertEquals("For "+doc.getSourceUrl()+" the number of annotations"+
    " should be:758",758,annotSet.size());

    gate.corpora.TestDocument.verifyNodeIdConsistency(doc);

    // Verifies if the maximum annotation ID on the GATE doc is less than the
    // Annotation ID generator of the document.
    verifyAnnotationIDGenerator(doc);

  } // testUnpackMarkup()

  /*
   * This method runs ANNIE with defaults on a document, then saves
   * it as a GATE XML document and loads it back. All the annotations on the
   * loaded document should be the same as the original ones.
   *
   * It also verifies if the matches feature still holds after an export/import to XML
   */
  public void testAnnotationConsistencyForSaveAsXml()throws Exception{
    // Load a document from the test repository
    //Document origDoc = gate.Factory.newDocument(Gate.getUrl("tests/xml/gateTestSaveAsXML.xml"));
    String testDoc = gate.util.Files.getGateResourceAsString("gate.ac.uk/tests/xml/gateTestSaveAsXML.xml");
    Document origDoc = gate.Factory.newDocument(testDoc);

    // Verifies if the maximum annotation ID on the origDoc is less than the
    // Annotation ID generator of the document.
    verifyAnnotationIDGenerator(origDoc);

    //create a couple of annotations with features we can look at after a round trip to disc    
    Integer ann1ID = origDoc.getAnnotations().add(0L,10L,"Test",Factory.newFeatureMap());
    Integer ann2ID = origDoc.getAnnotations().add(15L,20L,"Test",Factory.newFeatureMap());    
    origDoc.getAnnotations().get(ann1ID).getFeatures().put("matches", Arrays.asList(new Integer[]{ann2ID}));
    origDoc.getAnnotations().get(ann2ID).getFeatures().put("matches", Arrays.asList(new Integer[]{ann1ID}));    

    // SaveAS XML and reload the document into another GATE doc
    // Export the Gate document called origDoc as XML, into a temp file,
    // using the working encoding
    File xmlFile = Files.writeTempFile(origDoc.toXml(),workingEncoding);
    System.out.println("Saved to temp file :" + xmlFile.toURI().toURL());

    Document reloadedDoc = gate.Factory.newDocument(xmlFile.toURI().toURL(), workingEncoding);
    // Verifies if the maximum annotation ID on the origDoc is less than the
    // Annotation ID generator of the document.
    verifyAnnotationIDGenerator(reloadedDoc);

    // Verify if the annotations are identical in the two docs.
    Map<Integer,Annotation> origAnnotMap = buildID2AnnotMap(origDoc);
    Map<Integer,Annotation> reloadedAnnMap = buildID2AnnotMap(reloadedDoc);

    //Verifies if the reloaded annotations are the same as the original ones
    verifyIDConsistency(origAnnotMap, reloadedAnnMap);

    // Build the original Matches map
    // ID  -> List of IDs
    Map<Integer,List<Integer>> origMatchesMap = buildMatchesMap(origDoc);
    // Verify the consistency of matches
    // Compare every orig annotation pointed by the MatchesMap with the reloadedAnnot
    // extracted from the reloadedMAp
    for(Iterator<Integer> it = origMatchesMap.keySet().iterator(); it.hasNext();){
      Integer id = it.next();
      Annotation origAnnot = origAnnotMap.get(id);
      assertTrue("Couldn't find an original annot with ID=" + id, origAnnot != null);
      Annotation reloadedAnnot = reloadedAnnMap.get(id);
      assertTrue("Couldn't find a reloaded annot with ID=" + id, reloadedAnnot != null);
      compareAnnot(origAnnot,reloadedAnnot);
      // Iterate through the matches list and repeat the comparison
      List<Integer> matchesList = origMatchesMap.get(id);
      for (Iterator<Integer> itList = matchesList.iterator(); itList.hasNext();){
        Integer matchId = itList.next();
        Annotation origA = origAnnotMap.get(matchId);
        assertTrue("Couldn't find an original annot with ID=" + matchId, origA != null);
        Annotation reloadedA = reloadedAnnMap.get(matchId);
        assertTrue("Couldn't find a reloaded annot with ID=" + matchId, reloadedA != null);
        compareAnnot(origA, reloadedA);
      }// End for
    }// End for
    // Clean up the XMl file
    xmlFile.delete();
  }// End testAnnotationIDConsistencyForSaveAsXml

  /**
   * Builds a Map based on the matches feature of some annotations. The goal is to
   * use this map to validate the annotations from the reloaded document.
   * In case no Annot has the matches feat, will return an Empty MAP
   * @param doc The document of which annotations will be used to construct the map
   * @return A Map from Annot ID -> Lists of Annot IDs
   */
  private Map<Integer,List<Integer>> buildMatchesMap(Document doc){
    Map<Integer,List<Integer>> matchesMap = new HashMap<Integer,List<Integer>>();
    // Scan the default annotation set
    AnnotationSet annotSet = doc.getAnnotations();

    helperBuildMatchesMap(annotSet, matchesMap);
    // Scan all named annotation sets
    if (doc.getNamedAnnotationSets() != null){
      for ( Iterator<AnnotationSet> namedAnnotSetsIter = doc.getNamedAnnotationSets().values().iterator();
                                                                namedAnnotSetsIter.hasNext(); ){
        helperBuildMatchesMap(namedAnnotSetsIter.next(), matchesMap);
      }// End while
    }// End if
    return matchesMap;
  }// End of buildMatchesMap()

  /**
   * This is a helper metod. It scans an annotation set and adds the ID of the annotations
   * which have the matches feature to the map.
   * @param sourceAnnotSet  The annotation set investigated
   * @param aMap
   */
  private void helperBuildMatchesMap(AnnotationSet sourceAnnotSet, Map<Integer,List<Integer>> aMap ){

    for (Iterator<Annotation> it = sourceAnnotSet.iterator(); it.hasNext();){
      Annotation a = it.next();
      FeatureMap aFeatMap = a.getFeatures();
      // Skip those annotations who don't have features
      if (aFeatMap == null) continue;
      // Extract the matches feat
      @SuppressWarnings("unchecked")
      List<Integer> matchesVal = (List<Integer>) aFeatMap.get("matches");
      if (matchesVal == null) continue;
      Integer id = a.getId();
      aMap.put(id,matchesVal);
    }//End for

  }// End of helperBuildMatchesMap()

  /**
   * This method tests if the generator for new Annotation IDs is greather than the
   * maximum Annotation ID present in the GATE document. In oter words, it ensures that
   * new Annotations will receive an UNIQUE ID.
   *
   * @param aDoc The GATE document being tested
   */
  protected void verifyAnnotationIDGenerator(gate.Document aDoc){
    // Creates a MAP containing all the annotations of the document.
    // In doing so, it also tests if there are annotations with the same ID.
    Map<Integer,Annotation> id2AnnotationMap = buildID2AnnotMap(aDoc);

    if (id2AnnotationMap == null || id2AnnotationMap.isEmpty()){
      //System.out.println("No annotations found on the document! Nothing to test.");
      return;
    }

    // Get the key set of the Map and sort them
    Set<Integer> keysSet = id2AnnotationMap.keySet();
    TreeSet<Integer> sortedSet = new TreeSet<Integer>(keysSet);
    // Get the highest Annotation ID
    Integer maxAnnotId =  sortedSet.last();
    // Compare its value to the one hold by the document's ID generator
    Integer generatorId = ((DocumentImpl)aDoc).getNextAnnotationId();

//    System.out.println("maxAnnotid = " + maxAnnotId + " generatorID = " + generatorId);

    assertTrue("Annotation ID generator["+generatorId+"] on document [" + aDoc.getSourceUrl() +
            "] was equal or less than the MAX Annotation ID["+maxAnnotId+"] on the document."+
            " This may lead to Annotation ID conflicts.", generatorId.intValue() > maxAnnotId.intValue());


  }// End of verifyAnnotationIDGenerator()

  /**
   * Verifies if the two maps hold annotations with the same ID. The only thing not checked
   * are the features, as some of them could be lost in the serialization/deserialization process
   * @param origAnnotMap A map by ID, containing the original annotations
   * @param reloadedAnnMap A map by ID, containing the recreated annotations
   */
  private void verifyIDConsistency(Map<Integer,Annotation> origAnnotMap, Map<Integer,Annotation> reloadedAnnMap) {
    assertEquals("Found a different number of annot in both documents.",
            origAnnotMap.keySet().size(), reloadedAnnMap.keySet().size());

//    List orig = new ArrayList(origAnnotMap.keySet());
//    Collections.sort(orig);
//    System.out.println("ORIG SET =" + orig);
//
//    List rel = new ArrayList(reloadedAnnMap.keySet());
//    Collections.sort(rel);
//    System.out.println("REL  SET =" + rel);
//

    for (Iterator<Integer> it = origAnnotMap.keySet().iterator(); it.hasNext();){
      Integer id = it.next();
      Annotation origAnn = origAnnotMap.get(id);
      Annotation reloadedAnnot = reloadedAnnMap.get(id);

      assertTrue("Annotation with ID="+ id +" was not found in the reloaded document.", reloadedAnnot != null);
      compareAnnot(origAnn, reloadedAnnot);

    }// End for
  }// End of verifyIDConsistency()

  /**
   * Thes if two annotatiosn are the same, except their features.
   * @param origAnn
   * @param reloadedAnnot
   */
  private void compareAnnot(Annotation origAnn, Annotation reloadedAnnot) {
    assertTrue("Found original and reloaded annot without the same ID!",
            origAnn.getId().equals(reloadedAnnot.getId()));
    assertTrue("Found original and reloaded annot without the same TYPE!\n"+
               "Original was ["+origAnn.getType()+"] and reloaded was ["+reloadedAnnot.getType()+"].",
            origAnn.getType().equals(reloadedAnnot.getType()));
    assertTrue("Found original and reloaded annot without the same START offset!",
            origAnn.getStartNode().getOffset().equals(reloadedAnnot.getStartNode().getOffset()));
    assertTrue("Found original and reloaded annot without the same END offset!",
            origAnn.getEndNode().getOffset().equals(reloadedAnnot.getEndNode().getOffset()));
  }// End of compareAnnot()


  private Map<Integer,Annotation> addAnnotSet2Map(AnnotationSet annotSet, Map<Integer,Annotation> id2AnnMap){
    for (Iterator<Annotation> it = annotSet.iterator(); it.hasNext();){
      Annotation a = it.next();
      Integer id = a.getId();

      assertTrue("Found two annotations(one with type = " + a.getType() +
              ")with the same ID=" + id, !id2AnnMap.keySet().contains(id));

      id2AnnMap.put(id, a);
    }// End for
    return id2AnnMap;
  }

  /**
   * Scans a target Doc for all Annotations and builds a map (from anot ID to annot) in the process
   * I also checks to see if there are two annotations with the same ID.
   * @param aDoc The GATE doc to be scaned
   * @return a Map ID2Annot
   */
  private Map<Integer,Annotation> buildID2AnnotMap(Document aDoc){
    Map<Integer,Annotation> id2AnnMap = new HashMap<Integer,Annotation>();
    // Scan the default annotation set
    AnnotationSet annotSet = aDoc.getAnnotations();
    addAnnotSet2Map(annotSet, id2AnnMap);
    // Scan all named annotation sets
    if (aDoc.getNamedAnnotationSets() != null){
      for ( Iterator<AnnotationSet> namedAnnotSetsIter = aDoc.getNamedAnnotationSets().values().iterator();
                                                                namedAnnotSetsIter.hasNext(); ){

        addAnnotSet2Map(namedAnnotSetsIter.next(), id2AnnMap);
      }// End while
    }// End if
    return id2AnnMap;
  }// End of buildID2AnnotMap()

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestXml.class);
  } // suite

} // class TestXml
