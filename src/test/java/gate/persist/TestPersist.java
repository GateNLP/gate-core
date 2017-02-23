/*
 * TestPersist.java
 * 
 * Copyright (c) 1995-2011, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Hamish Cunningham, 19/Jan/01
 * 
 * $Id: TestPersist.java 19621 2016-10-04 05:35:22Z markagreenwood $
 */

package gate.persist;

import gate.Corpus;
import gate.DataStore;
import gate.DataStoreRegister;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageResource;
import gate.Resource;
import gate.corpora.TestDocument;
import gate.util.Err;
import gate.util.GateException;
import gate.util.Out;
import gate.util.TestEqual;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Persistence test class
 */
public class TestPersist extends TestCase {
  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Construction */
  public TestPersist(String name) throws GateException {
    super(name);
  }

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    if (!Gate.isInitialised()) {
      Gate.runInSandbox(true);
      Gate.init();
    }
  } // setUp

  /**
   * Put things back as they should be after running tests (reinitialise the
   * CREOLE register).
   */
  @Override
  public void tearDown() throws Exception {
  } // tearDown

  /** Test resource save and restore */
  public void testSaveRestore() throws Exception {
    File storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    storageDir.delete(); // get rid of the temp file
    storageDir.mkdir(); // create an empty dir of same name

    SerialDataStore sds =
            new SerialDataStore(storageDir.toURI().toURL().toString());
    sds.create();
    sds.open();

    // create a document
    String server = TestDocument.getTestServerName();
    assertNotNull(server);
    Document doc = Factory.newDocument(new URL(server + "tests/doc0.html"));
    assertNotNull(doc);
    doc.getFeatures().put("hi there", new Integer(23232));
    doc.getAnnotations().add(new Long(0), new Long(20), "thingymajig",
            Factory.newFeatureMap());

    // check that we can't save a resource without adopting it
    boolean cannotSync = false;
    try {
      sds.sync(doc);
    } catch(PersistenceException e) {
      cannotSync = true;
    }
    if(!cannotSync) assertTrue("doc synced ok before adoption", false);

    // check that we can't adopt a resource that's stored somewhere else
    doc.setDataStore(new SerialDataStore(new File("z:\\").toURI().toURL()
            .toString()));
    try {
      sds.adopt(doc);
    } catch(PersistenceException e) {
      cannotSync = true;
    }
    if(!cannotSync)
      assertTrue("doc adopted but in other datastore already", false);
    doc.setDataStore(null);
    doc.setName("Alicia Tonbridge, a Document");

    // save the document
    Document persDoc = (Document)sds.adopt(doc);
    sds.sync(persDoc);
    Object lrPersistenceId = persDoc.getLRPersistenceId();

    // test the getLrTypes method
    List<String> lrTypes = sds.getLrTypes();
    assertTrue("wrong number of types in SDS", lrTypes.size() == 1);
    assertTrue("wrong type LR in SDS",
            lrTypes.get(0).equals("gate.corpora.DocumentImpl"));

    // test the getLrNames method
    Iterator<String> iter = sds.getLrNames("gate.corpora.DocumentImpl").iterator();
    String name = iter.next();
    assertEquals(name, "Alicia Tonbridge, a Document");

    // read the document back
    FeatureMap features = Factory.newFeatureMap();
    features.put(DataStore.LR_ID_FEATURE_NAME, lrPersistenceId);
    features.put(DataStore.DATASTORE_FEATURE_NAME, sds);
    Document doc2 =
            (Document)Factory.createResource("gate.corpora.DocumentImpl",
                    features);
    Document doc3 =
            (Document)sds.getLr("gate.corpora.DocumentImpl", lrPersistenceId);

    try {
      boolean value = TestEqual.documentsEqual(doc3, doc2);
      assertTrue(TestEqual.message, value);
      value = TestEqual.documentsEqual(persDoc, doc2);
      assertTrue(TestEqual.message, value);
    } finally {
      // delete the datastore
      sds.delete();
    }
  } // testSaveRestore()

  /** Simple test */
  public void testSimple() throws Exception {
    // create a temporary directory; because File.createTempFile actually
    // writes the bloody thing, we need to delete it from disk before calling
    // DataStore.create
    File storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    storageDir.delete();

    // create and open a serial data store
    DataStore sds =
            Factory.createDataStore("gate.persist.SerialDataStore", storageDir
                    .toURI().toURL().toString());

    // check we can get empty lists from empty data stores
    @SuppressWarnings("unused")
    List<String> lrTypes = sds.getLrTypes();

    // create a document with some annotations / features on it
    String server = TestDocument.getTestServerName();
    Document doc = Factory.newDocument(new URL(server + "tests/doc0.html"));
    doc.getFeatures().put("hi there", new Integer(23232));
    doc.getAnnotations().add(new Long(5), new Long(25), "ThingyMaJig",
            Factory.newFeatureMap());

    // save the document
    Document persDoc = (Document)sds.adopt(doc);
    sds.sync(persDoc);

    // remember the persistence ID for reading back
    // (in the normal case these ids are obtained by DataStore.getLrIds(type))
    Object lrPersistenceId = persDoc.getLRPersistenceId();

    // read the document back
    FeatureMap features = Factory.newFeatureMap();
    features.put(DataStore.LR_ID_FEATURE_NAME, lrPersistenceId);
    features.put(DataStore.DATASTORE_FEATURE_NAME, sds);
    Document doc2 =
            (Document)Factory.createResource("gate.corpora.DocumentImpl",
                    features);

    // parameters should be different
    // check that the version we read back matches the original
    assertTrue(TestEqual.documentsEqual(persDoc, doc2));

    // delete the datastore
    sds.delete();
  } // testSimple()

  /** Test multiple LRs */
  public void testMultipleLrs() throws Exception {
    // create a temporary directory; because File.createTempFile actually
    // writes the bloody thing, we need to delete it from disk before calling
    // DataStore.create
    File storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    storageDir.delete();

    // create and open a serial data store
    SerialDataStore sds =
            new SerialDataStore(storageDir.toURI().toURL().toString());
    sds.create();
    sds.open();

    // create a document with some annotations / features on it
    String server = TestDocument.getTestServerName();
    Document doc = Factory.newDocument(new URL(server + "tests/doc0.html"));
    doc.getFeatures().put("hi there", new Integer(23232));
    doc.getAnnotations().add(new Long(5), new Long(25), "ThingyMaJig",
            Factory.newFeatureMap());

    // create another document with some annotations / features on it
    Document doc2 =
            Factory.newDocument(new URL(server + "tests/html/test1.htm"));
    doc.getFeatures().put("hi there again", new Integer(23232));
    doc.getAnnotations().add(new Long(5), new Long(25), "dog poo irritates",
            Factory.newFeatureMap());

    // create a corpus with the documents
    Corpus corp = Factory.newCorpus("Hamish test corpus");
    corp.add(doc);
    corp.add(doc2);
    LanguageResource persCorpus = sds.adopt(corp);
    sds.sync(persCorpus);

    // read the documents back
    List<Resource> lrsFromDisk = new ArrayList<Resource>();
    List<String> lrIds = sds.getLrIds("gate.corpora.SerialCorpusImpl");

    Iterator<String> idsIter = lrIds.iterator();
    while(idsIter.hasNext()) {
      String lrId = idsIter.next();
      FeatureMap features = Factory.newFeatureMap();
      features.put(DataStore.DATASTORE_FEATURE_NAME, sds);
      features.put(DataStore.LR_ID_FEATURE_NAME, lrId);
      Resource lr =
              Factory.createResource("gate.corpora.SerialCorpusImpl", features);
      lrsFromDisk.add(lr);
    } // for each LR ID

    if(DEBUG) System.out.println("LRs on disk" + lrsFromDisk);

    // check that the versions we read back match the originals
    Corpus diskCorp = (Corpus)lrsFromDisk.get(0);

    Document diskDoc = diskCorp.get(0);

    if(DEBUG) Out.prln("Documents in corpus: " + corp.getDocumentNames());
    assertTrue("corp name != mem name",
            corp.getName().equals(diskCorp.getName()));
    if(DEBUG) Out.prln("Memory features " + corp.getFeatures());
    if(DEBUG) Out.prln("Disk features " + diskCorp.getFeatures());
    assertTrue("corp feat != mem feat",
            corp.getFeatures().equals(diskCorp.getFeatures()));
    if(DEBUG) Out.prln("Annotations in doc: " + diskDoc.getAnnotations());
    assertTrue(
            "doc annotations from disk not equal to memory version",
            TestEqual.annotationSetsEqual(doc.getAnnotations(),
                    diskDoc.getAnnotations()));

    assertTrue("doc from disk not equal to memory version",
            TestEqual.documentsEqual(doc, diskDoc));

    Iterator<Document> corpusIter = diskCorp.iterator();
    while(corpusIter.hasNext()) {
      if(DEBUG)
        Out.prln(corpusIter.next().getName());
      else corpusIter.next();
    }

    // assertTrue("doc2 from disk not equal to memory version",
    // doc2.equals(diskDoc2));

    // delete the datastore
    sds.delete();
  } // testMultipleLrs()

  /** Test LR deletion */
  public void testDelete() throws Exception {
    // create a temporary directory; because File.createTempFile actually
    // writes the bloody thing, we need to delete it from disk before calling
    // DataStore.create
    File storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    if(DEBUG) Out.prln("Corpus stored to: " + storageDir.getAbsolutePath());
    storageDir.delete();

    // create and open a serial data store
    SerialDataStore sds = new SerialDataStore();
    sds.setStorageUrl(storageDir.toURI().toURL().toString());
    sds.create();
    sds.open();

    // create a document with some annotations / features on it
    String server = TestDocument.getTestServerName();
    Document doc = Factory.newDocument(new URL(server + "tests/doc0.html"));
    doc.getFeatures().put("hi there", new Integer(23232));
    doc.getAnnotations().add(new Long(5), new Long(25), "ThingyMaJig",
            Factory.newFeatureMap());

    // save the document
    Document persDoc = (Document)sds.adopt(doc);
    sds.sync(persDoc);

    // remember the persistence ID for reading back
    // (in the normal case these ids are obtained by DataStore.getLrIds(type))
    Object lrPersistenceId = persDoc.getLRPersistenceId();

    // delete document back
    sds.delete("gate.corpora.DocumentImpl", lrPersistenceId);

    // check that there are no LRs left in the DS
    assertTrue(sds.getLrIds("gate.corpora.DocumentImpl").size() == 0);

    // delete the datastore
    sds.delete();
  } // testDelete()

  /** Test the DS register. */
  public void testDSR() throws Exception {
    DataStoreRegister dsr = Gate.getDataStoreRegister();
    assertTrue("DSR has wrong number elements (not 0): " + dsr.size(),
            dsr.size() == 0);

    // create a temporary directory; because File.createTempFile actually
    // writes the bloody thing, we need to delete it from disk before calling
    // DataStore.create
    File storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    storageDir.delete();

    // create and open a serial data store
    DataStore sds =
            Factory.createDataStore("gate.persist.SerialDataStore", storageDir
                    .toURI().toURL().toString());

    // create a document with some annotations / features on it
    String server = TestDocument.getTestServerName();
    Document doc = Factory.newDocument(new URL(server + "tests/doc0.html"));
    doc.getFeatures().put("hi there", new Integer(23232));
    doc.getAnnotations().add(new Long(5), new Long(25), "ThingyMaJig",
            Factory.newFeatureMap());

    // save the document
    Document persDoc = (Document)sds.adopt(doc);
    sds.sync(persDoc);

    // DSR should have one member
    assertTrue("DSR has wrong number elements (expected 1): " + dsr.size(),
            dsr.size() == 1);

    // create and open another serial data store
    storageDir = File.createTempFile("TestPersist__", "__StorageDir");
    storageDir.delete();
    DataStore sds2 =
            Factory.createDataStore("gate.persist.SerialDataStore", storageDir
                    .toURI().toURL().toString());

    // DSR should have two members
    assertTrue("DSR has wrong number elements: " + dsr.size(), dsr.size() == 2);

    // peek at the DSR members
    Iterator<DataStore> dsrIter = dsr.iterator();
    while(dsrIter.hasNext()) {
      DataStore ds = dsrIter.next();
      assertNotNull("null ds in ds reg", ds);
      if(DEBUG) Out.prln(ds);
    }

    // delete the datastores
    sds.close();
    assertTrue("DSR has wrong number elements (expected 1): " + dsr.size(),
            dsr.size() == 1);
    sds.delete();
    assertTrue("DSR has wrong number elements (expected 1): " + dsr.size(),
            dsr.size() == 1);
    sds2.delete();
    assertTrue("DSR has wrong number elements (expected 0): " + dsr.size(),
            dsr.size() == 0);

  } // testDSR()

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestPersist.class);
  } // suite

  public static void main(String[] args) {
    try {

      Gate.setLocalWebServer(false);
      Gate.setNetConnected(false);
      Gate.init();

      TestPersist test = new TestPersist("");

      /* SerialDS */

      test.setUp();
      test.testDelete();
      test.tearDown();

      test.setUp();
      test.testDSR();
      test.tearDown();

      test.setUp();
      test.testMultipleLrs();
      test.tearDown();

      test.setUp();
      // test.testSaveRestore();
      test.tearDown();

      test.setUp();
      test.testSimple();
      test.tearDown();

      // I put this last because its failure is dependent on the gc() and
      // there's nothing I can do about it. Maybe I'll remove this from the
      // test
      test.setUp();
      test.testMultipleLrs();
      test.tearDown();

      if(DEBUG) {
        Err.println("done.");
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

} // class TestPersist