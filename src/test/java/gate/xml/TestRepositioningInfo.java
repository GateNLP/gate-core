package gate.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.corpora.DocumentImpl;
import gate.corpora.TestDocument;
import gate.creole.ResourceInstantiationException;
import gate.util.BomStrippingInputStreamReader;

/**
 * <p>Title: TestRepositioningInfo.java </p>
 * <p>Description: Test to check if RepositioningInfo works. </p>
 * <p>Company: University Of Sheffield</p>
 * @author Niraj Aswani
 * @version 1.0
 */
import junit.framework.TestCase;

/**
 * This class tests if Repositinioning Information works.
 * It creates a document using an inline xml file with preserveOriginalContent
 * and collectRepositioningInfo options keeping true, which has all
 * sorts of special entities like &amp, &quot etc. + it contains both
 * kind of unix and dos types new line characters.  It then saves the
 * document to the temporary location on the disk using
 * "save preserving document format" option and then compares the contents of
 * both the original and the temporary document to see if they are equal.
 */
public class TestRepositioningInfo
    extends TestCase {
  
  /**
   * This method sets up the parameters for the files to be tested
   */
  @Override
  protected void setUp() throws Exception {

    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
    
    testFile = TestDocument.getTestServerName() + "tests/test-inline.xml";

    // creating documents
    try {
      FeatureMap params = Factory.newFeatureMap();
      params.put("sourceUrl",new URL(testFile));
      params.put("preserveOriginalContent", new Boolean("true"));
      params.put("collectRepositioningInfo", new Boolean("true"));
      doc = (Document) Factory.createResource("gate.corpora.DocumentImpl",params);
    }
    catch (MalformedURLException murle) {
      fail("Document cannot be created ");
    }
    catch (ResourceInstantiationException rie) {
      fail("Resources cannot be created for the test document");
    }
  }

  /** Fixture tear down - removes the document resource */
  @Override
  public void tearDown() throws Exception {
    Factory.deleteResource(doc);
  } // tearDown


  /**
   * This method tests if Repositinioning Information works.
   * It creates a document using an xml file with preserveOriginalContent
   * and collectRepositioningInfo options keeping true and which has all
   * sorts of special entities like &amp, &quot etc. + it contains both
   * kind of unix and dos stype new line characters.  It then saves the
   * document to the temporary location on the disk using
   * "save preserving document format" option and then compares the contents of
   * both the original and the temporary document to see if they are equal.
   * @throws java.lang.Exception
   */
  public void testRepositioningInfo() throws Exception {

    // here we need to save the document to the file
      String encoding = ((DocumentImpl)doc).getEncoding();
      File outputFile = File.createTempFile("test-inline1","xml");
      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile),encoding);
      writer.write(doc.toXml(null, true));
      writer.flush();
      writer.close();
      Reader readerForSource = new BomStrippingInputStreamReader(new URL(testFile).openStream(),encoding);
      Reader readerForDesti = new BomStrippingInputStreamReader(new FileInputStream(outputFile),encoding);
      while(true) {
        int input1 = readerForSource.read();
        int input2 = readerForDesti.read();
        if(input1 < 0 || input2 < 0) {
          assertTrue(input1 < 0 && input2 < 0);
          readerForSource.close();
          readerForDesti.close();
          outputFile.delete();
          return;
        } else {
          assertEquals(input1,input2);
        }
      }
  }


  /** A test file URL */
  private String testFile = "";

  /** Document instance */
  private Document doc = null;

}