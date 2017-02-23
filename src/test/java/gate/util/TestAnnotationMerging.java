package gate.util;

import java.net.URL;
import java.util.HashMap;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.TestDocument;
import junit.framework.TestCase;

public class TestAnnotationMerging extends TestCase {
  /** The id of test case. */
  int caseN;

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  } // setUp

  /**
   * Put things back as they should be after running tests.
   */
  @Override
  public void tearDown() throws Exception {
  } // tearDown

  private Document loadDocument(String path, String name) throws Exception {
    Document doc = Factory.newDocument(new URL(TestDocument.getTestServerName()+path), "UTF-8");
    doc.setName(name);
    return doc;
  }

  /** The test for AnnotationMerging. */
  public void testAnnotationMerging() throws Exception {

    Boolean savedSpaceSetting = Gate.getUserConfig().getBoolean(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME);
    Gate.getUserConfig().put(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME,
            Boolean.FALSE);
    try {

      //Gate.setGateHome(new File("C:\\svn\\gate"));
      //Gate.setUserConfigFile(new File("C:\\svn\\gate.xml"));
      //Gate.init();
      // Load the documents into a corpus
      Corpus data = Factory.newCorpus("data");
      // Put the annotated document into a matrix for IAA
      String nameAnnSet;
      String nameAnnType = "";
      String nameAnnFeat = "";
      // Use the dataset of one document and three annotators
      data.add(loadDocument("tests/iaa/beijing-opera.xml", "beijing-opera.xml"));
      //ExtensionFileFilter fileFilter = new ExtensionFileFilter();
      //fileFilter.addExtension("xml");
      //data.populate(new File("C:\\yaoyong_h\\work\\iaa\\data\\smallData").toURI().toURL(), fileFilter, "UTF-8", false);
      
      nameAnnSet = "ann1;ann2;ann3";
      boolean isUsingMajority=false;
      nameAnnType = "sent";
      nameAnnFeat = "Op";
      caseN = 1;
      isUsingMajority=true;
      testWithfeat(nameAnnSet, nameAnnType, nameAnnFeat,
              data, isUsingMajority);
      
      caseN = 2;
      isUsingMajority=false;
      testWithfeat(nameAnnSet, nameAnnType, nameAnnFeat,
              data, isUsingMajority);

      
      nameAnnType = "Os";
      nameAnnFeat = null;
      caseN = 3;
      isUsingMajority=true;
      testWithfeat(nameAnnSet, nameAnnType, nameAnnFeat, data, isUsingMajority);
      
      caseN = 4;
      isUsingMajority=false;
      testWithfeat(nameAnnSet, nameAnnType, nameAnnFeat, data, isUsingMajority);
    }
    finally {
      Gate.getUserConfig().put(
              GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME,
              savedSpaceSetting);
    }

  }

 
  /** The actual method for testing. */
  public void testWithfeat(String nameAnnSets, String nameAnnType, String nameAnnFeat, Corpus data, boolean isUsingMajority) {
    //  get the annotation sets
    String [] annSetsN = nameAnnSets.split(";");
    int numJudges = annSetsN.length;
    int numDocs = data.size();
    AnnotationSet[][] annArr2 = new AnnotationSet[numDocs][numJudges];
    for(int i = 0; i < numDocs; ++i) {
      Document doc = data.get(i);
      for(int j=0; j<numJudges; ++j) {
        // Get the annotation
        annArr2[i][j] = doc.getAnnotations(annSetsN[j]).get(nameAnnType);
      }
    }
    //Annotation merging
    boolean isTheSameInstances = true;
    for(int i=0; i<annArr2.length; ++i)
      if(!AnnotationMerging.isSameInstancesForAnnotators(annArr2[i], 1)) {
        isTheSameInstances = false;
        break;
      }
    HashMap<Annotation,String>mergeInfor = new HashMap<Annotation,String>();
    if(isUsingMajority)
      AnnotationMerging.mergeAnnotationMajority(annArr2[0], nameAnnFeat, mergeInfor, isTheSameInstances);
    else AnnotationMerging.mergeAnnotation(annArr2[0], nameAnnFeat, mergeInfor, 2, isTheSameInstances);
    int numAnns=0;
    if(isTheSameInstances) {
      for(Annotation ann:mergeInfor.keySet()) {
        if(ann.getFeatures().get(nameAnnFeat) != null)
          ++numAnns;
         
      }
    } else {
      numAnns = mergeInfor.size();
    }
    checkNumbers(numAnns);
  }

  /** Check the numbers. */
  private void checkNumbers(int numAnns) {
    switch(caseN) {
      case 1:
        assertEquals(numAnns, 9);
        break;
      case 2:
        assertEquals(numAnns, 9);
        break;
      case 3:
        assertEquals(numAnns, 2);
        break;
      case 4:
        assertEquals(numAnns, 2);
        break;
      default:
        System.out.println("The test case " + caseN + " is not defined yet.");
    }
  }

}
