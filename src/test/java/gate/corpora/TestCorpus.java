/*
 *  TestCorpus.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 18/Feb/00
 *
 *  $Id: TestCorpus.java 17656 2014-03-14 08:55:23Z markagreenwood $
 */

package gate.corpora;

import java.net.URL;

import junit.framework.*;

import gate.*;
import gate.util.SimpleFeatureMapImpl;

/** Tests for the Corpus classes
  */
public class TestCorpus extends TestCase
{
  /** Construction */
  public TestCorpus(String name) { super(name); }

  /** Fixture set up */
  @Override
  public void setUp() {
  } // setUp

  /** Corpus creation */
  public void testCreation() throws Exception {
    Corpus c = Factory.newCorpus("test corpus");

    assertTrue(c.isEmpty());
    assertTrue(c.getName().equals("test corpus"));

    c.setFeatures(new SimpleFeatureMapImpl());
    c.getFeatures().put("author", "hamish");
    c.getFeatures().put("date", new Integer(180200));
    assertTrue(c.getFeatures().size() == 2);

    Corpus c2 = Factory.newCorpus("test corpus2");
    c2.getFeatures().put("author", "hamish");
    c2.getFeatures().put("author", "valy");
    assertTrue(
      "corpus feature set wrong, size = " + c2.getFeatures().size(),
      c2.getFeatures().size() == 1
    );
    assertTrue(c2.getFeatures().get("author").equals("valy"));

  } // testCreation()

  /** Add some documents */
  public void testDocumentAddition() throws Exception {
    Corpus c = Factory.newCorpus("test corpus");
    Document d1 = Factory.newDocument("a document");
    Document d2 = Factory.newDocument("another document");
    d2.setSourceUrl(new URL("http://localhost/1"));
    d2.setSourceUrl(new URL("http://localhost/2"));
    assertTrue(c.add(d1));
    assertTrue(c.add(d2));
    assertEquals(2, c.size());
  } // testDocumentAddition()

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestCorpus.class);
  } // suite

} // class TestCorpus
