package gate.util;

import java.net.URL;
import java.util.ArrayList;

import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.corpora.TestDocument;
import junit.framework.TestCase;

public class TestClassificationMeasures extends TestCase{
 
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  }

  public void test(){
    String type = "sent";
    String feature = "Op";
        
    Document doc1 = null;
    Document doc2 = null;
    Document doc3 = null;
    Document doc4 = null;
        
    try {
      Gate.init();
      
      doc1 = Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/iaa/beijing-opera.xml"));
      doc2 = Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/iaa/beijing-opera.xml"));
      doc3 = Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/iaa/in-outlook-09-aug-2001.xml"));
      doc4 = Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/iaa/in-outlook-09-aug-2001.xml"));
        
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    if(doc1!=null && doc2!=null && doc3!=null && doc4!=null){
      AnnotationSet as1 = doc1.getAnnotations("ann1");
      AnnotationSet as2 = doc2.getAnnotations("ann2");

      ClassificationMeasures myClassificationMeasures1 =
        new ClassificationMeasures();
      myClassificationMeasures1.calculateConfusionMatrix(
        as1, as2, type, feature, true);
      assertEquals(myClassificationMeasures1.getObservedAgreement(), 0.7777778f);
      assertEquals(myClassificationMeasures1.getKappaCohen(), 0.6086957f);
      assertEquals(myClassificationMeasures1.getKappaPi(), 0.59550565f);
      
      AnnotationSet as3 = doc3.getAnnotations("ann1");
      AnnotationSet as4 = doc4.getAnnotations("ann2");
       
      ClassificationMeasures myClassificationMeasures2 =
        new ClassificationMeasures();
      myClassificationMeasures2.calculateConfusionMatrix(
        as3, as4, type, feature, true);
      assertEquals(myClassificationMeasures2.getObservedAgreement(), 0.96875f);
      assertEquals(myClassificationMeasures2.getKappaCohen(), 0.3263158f);
      assertEquals(myClassificationMeasures2.getKappaPi(), 0.3227513f);
       
      ArrayList<ClassificationMeasures> tablesList = new ArrayList<ClassificationMeasures>();
      tablesList.add(myClassificationMeasures1);
      tablesList.add(myClassificationMeasures2);
      ClassificationMeasures myNewClassificationMeasures =
        new ClassificationMeasures(tablesList);
      assertEquals(myNewClassificationMeasures.getObservedAgreement(), 0.94520545f);
      assertEquals(myNewClassificationMeasures.getKappaCohen(), 0.7784521f);
      assertEquals(myNewClassificationMeasures.getKappaPi(), 0.7778622f);
       
    } else {
      System.out.println("Failed to create docs from URLs.");
    }
  }
}
