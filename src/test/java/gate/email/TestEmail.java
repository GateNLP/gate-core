/*
 *  TestEmail.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU,  7/Aug/2000
 *
 *  $Id: TestEmail.java 19621 2016-10-04 05:35:22Z markagreenwood $
 */

package gate.email;

import gate.Gate;
import gate.corpora.TestDocument;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Assert;
//import org.w3c.www.mime.*;


/**
  * Test class for Email facilities
  */
public class TestEmail extends TestCase
{

  /** Construction */
  public TestEmail(String name) { super(name); }
  
  private EmailDocumentHandler emailDocumentHandler = new EmailDocumentHandler();

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  } // setUp

  /** A test */
  public void testUnpackMarkup() throws Exception{

    gate.Document doc = null;
//    Gate.init();
    doc = gate.Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/email/test.eml"), "ISO-8859-1");

    // get a document format that deals with e-mails
    gate.DocumentFormat docFormat = gate.DocumentFormat.getDocumentFormat(
      doc, doc.getSourceUrl()
    );
    assertTrue( "Bad document Format was produced.EmailDocumentFormat was expected",
            docFormat instanceof gate.corpora.EmailDocumentFormat
          );

    docFormat.unpackMarkup (doc,"DocumentContent");
    // Verfy if all annotations from the default annotation set are consistent
    gate.corpora.TestDocument.verifyNodeIdConsistency(doc);

  } // testUnpackMarkup()

  public static void main(String[] args) {
    try{
      Gate.init();
      TestEmail testEmail = new TestEmail("");
      testEmail.testUnpackMarkup();

    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
    * final test
    */
  //public void testEmail(){
 //   EmailDocumentHandler emailDocumentHandler = new EmailDocumentHandler();
    //emailDocumentHandler.testSelf();
 // }// testEmail
  
  /**
   * Test containsSemicolon
   */
 public void testContainsSemicolon() {
   String str1 = "X-Sender: oana@derwent";
   String str2 = "X-Sender oana@derwent";
   String str3 = ":X-Sender oana@derwent";
   String str4 = "X-Sender oana@derwent:";

   Assert.assertTrue((emailDocumentHandler.containsSemicolon(str1) == true));
   Assert.assertTrue((emailDocumentHandler.containsSemicolon(str2)== false));
   Assert.assertTrue((emailDocumentHandler.containsSemicolon(str3) == true));
   Assert.assertTrue((emailDocumentHandler.containsSemicolon(str4) == true));
 }// testContainsSemicolon

 /**
   * Test containsWhiteSpaces
   */
 public void testContainsWhiteSpaces(){
   String str1 = "Content-Type: TEXT/PLAIN; charset=US-ASCII";
   String str2 = "Content-Type:TEXT/PLAIN;charset=US-ASCII";
   String str3 = " Content-Type:TEXT/PLAIN;charset=US-ASCII";
   String str4 = "Content-Type:TEXT/PLAIN;charset=US-ASCII ";

   Assert.assertTrue((emailDocumentHandler.containsWhiteSpaces(str1) == true));
   Assert.assertTrue((emailDocumentHandler.containsWhiteSpaces(str2) == false));
   Assert.assertTrue((emailDocumentHandler.containsWhiteSpaces(str3) == true));
   Assert.assertTrue((emailDocumentHandler.containsWhiteSpaces(str4) == true));
 }// testContainsWhiteSpaces

 /**
   * Test hasAMeaning
   */
 public void testHasAMeaning() {
   String str1 = "12:05:22";
   String str2 = "Sep";
   String str3 = "Fri";
   String str4 = "2000";
   String str5 = "GMT";
   String str6 = "Date: Wed, 13 Sep 2000 13:05:22 +0100 (BST)";
   String str7 = "12:75:22";
   String str8 = "September";
   String str9 = "Friday";

   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str1) == true));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str2) == true));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str3) == true));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str4) == true));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str5) == true));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str6) == false));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str7) == false));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str8) == false));
   Assert.assertTrue((emailDocumentHandler.hasAMeaning(str9) == false));
 } // testHasAMeaning

 /**
   * Test isTime
   */
 public void testIsTime() {
   String str1 = "13:05:22";
   String str2 = "13/05/22";
   String str3 = "24:05:22";

   Assert.assertTrue((emailDocumentHandler.isTime(str1) == true));
   Assert.assertTrue((emailDocumentHandler.isTime(str2) == false));
   Assert.assertTrue((emailDocumentHandler.isTime(str3) == false));
 }// testIsTime

 /**
   * Test lineBeginsMessage
   */
 public void testLineBeginsMessage(){
   String str1 = "From oana@dcs.shef.ac.uk Wed Sep 13 13:05:23 2000";
   String str2 = "Date: Wed, 13 Sep 2000 13:05:22 +0100 (BST)";
   String str3 = "From oana@dcs.shef.ac.uk Sep 13 13:05:23 2000";

   Assert.assertTrue((emailDocumentHandler.lineBeginsMessage(str1) == true));
   Assert.assertTrue((emailDocumentHandler.lineBeginsMessage(str2) == false));
   Assert.assertTrue((emailDocumentHandler.lineBeginsMessage(str3) == false));

 }// testLineBeginsMessage

 /**
   * Test lineBeginsWithField
   */
 public void testLineBeginsWithField() {
   String str1 = "Message-ID: <Pine.SOL.3.91.1000913130311.19537A-10@derwent>";
   String str2 = "%:ContentType TEXT/PLAIN; charset=US-ASCII";

   Assert.assertTrue((emailDocumentHandler.lineBeginsWithField(str1) == true));
   Assert.assertTrue((emailDocumentHandler.lineBeginsWithField(str2) == true));
 }// testLineBeginsWithField

  /**
    * Test final
    */
  /*public void testSelf(){
    testContainsSemicolon();
    testContainsWhiteSpaces();
    testHasAMeaning();
    testIsTime();
    testLineBeginsMessage();
    testLineBeginsWithField();
  } // testSelf*/

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestEmail.class);
  } // suite

} // class TestEmail
