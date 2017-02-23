/*
 *  TestAnnotation.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 7/Feb/00
 *
 *  $Id: TestAnnotation.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.annotation;

import java.net.URL;
import java.util.*;

import junit.framework.*;

import gate.*;
import gate.corpora.TestDocument;
import gate.util.*;

/** Tests for the Annotation classes
  */
public class TestAnnotation extends TestCase
{
  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Construction */
  public TestAnnotation(String name) { super(name); }

  /** A document */
  protected Document doc1;

  /** An annotation set */
  protected AnnotationSet basicAS;

  /** An empty feature map */
  protected FeatureMap emptyFeatureMap;

  /** Fixture set up */
  @Override
  public void setUp() throws Exception
  {
	Gate.setNetConnected(false);
	  if (Gate.getGateHome() == null)
	    Gate.init();
    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    doc1 = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);

    emptyFeatureMap = new SimpleFeatureMapImpl();

    basicAS = new AnnotationSetImpl(doc1);
    FeatureMap fm = new SimpleFeatureMapImpl();

    basicAS.get("T");          // to trigger type indexing
    basicAS.get(new Long(0));  // trigger offset index (though add will too)

    basicAS.add(new Long(10), new Long(20), "T1", fm);    // 0
    basicAS.add(new Long(10), new Long(20), "T2", fm);    // 1
    basicAS.add(new Long(10), new Long(20), "T3", fm);    // 2
    basicAS.add(new Long(10), new Long(20), "T1", fm);    // 3

    fm = new SimpleFeatureMapImpl();
    fm.put("pos", "NN");
    fm.put("author", "hamish");
    fm.put("version", new Integer(1));

    basicAS.add(new Long(10), new Long(20), "T1", fm);    // 4
    basicAS.add(new Long(15), new Long(40), "T1", fm);    // 5
    basicAS.add(new Long(15), new Long(40), "T3", fm);    // 6
    basicAS.add(new Long(15), new Long(40), "T1", fm);    // 7

    fm = new SimpleFeatureMapImpl();
    fm.put("pos", "JJ");
    fm.put("author", "the devil himself");
    fm.put("version", new Long(44));
    fm.put("created", "monday");

    basicAS.add(new Long(15), new Long(40), "T3", fm);    // 8
    basicAS.add(new Long(15), new Long(40), "T1", fm);    // 9
    basicAS.add(new Long(15), new Long(40), "T1", fm);    // 10

    // Out.println(basicAS);
  } // setUp


  /** Test indexing by offset */
  public void testOffsetIndex() throws InvalidOffsetException {
    AnnotationSet as = new AnnotationSetImpl(doc1);
    AnnotationSet asBuf;
    Integer newId;
    FeatureMap fm = new SimpleFeatureMapImpl();
    Annotation a;
    Node startNode;
    Node endNode;

    newId = as.add(new Long(10), new Long(20), "T", fm);
    assertEquals(newId.intValue(), 11);
    a = as.get(newId);

    startNode = a.getStartNode();
    endNode = a.getEndNode();
    assertEquals(startNode.getId().intValue(), 4);
    assertEquals(endNode.getId().intValue(), 5);
    assertEquals(startNode.getOffset().longValue(), 10);
    assertEquals(endNode.getOffset().longValue(), 20);

    newId = as.add(new Long(10), new Long(30), "T", fm);
    assertEquals(newId.intValue(), 12);
    a = as.get(newId);

    startNode = a.getStartNode();
    endNode = a.getEndNode();
    assertEquals(startNode.getId().intValue(), 4);
    assertEquals(endNode.getId().intValue(), 6);
    assertEquals(startNode.getOffset().longValue(), 10);
    assertEquals(endNode.getOffset().longValue(), 30);

    asBuf = as.get(new Long(10));
    assertEquals(asBuf.size(), 2);

  } // testOffsetIndex()

  public void testImmutability() {
    Long l0= new Long(0);
    Long l1= new Long(20);
    FeatureMap fm = new SimpleFeatureMapImpl();

    // simple get
    AnnotationSet immutable = basicAS.get();
    assertTrue(_subtestImmutability(immutable));

    // get Long
    immutable = basicAS.get(l0);
    assertTrue(_subtestImmutability(immutable));

    // get two Longs
    immutable = basicAS.get(l0, l1);
    assertTrue(_subtestImmutability(immutable));

    // get Type
    immutable = basicAS.get("T1");
    assertTrue(_subtestImmutability(immutable));

    // get Types
    Set<String> types = new HashSet<String>();
    types.add ("T1");
    types.add ("T2");
    immutable = basicAS.get(types);
    assertTrue(_subtestImmutability(immutable));

    // get type + constraint
    immutable = basicAS.get("T1", fm);
    assertTrue(_subtestImmutability(immutable));

    // get type + constraint + Long
    immutable = basicAS.get("T1", fm, l0);
    assertTrue(_subtestImmutability(immutable));

    // type + Long + Long
    immutable = basicAS.get("T1", l0, l1);
    assertTrue(_subtestImmutability(immutable));

    // type + Set of feature names
    Set<String> annotset = new HashSet<String>();
    annotset.add("pos");
    immutable = basicAS.get("T3",annotset);
    assertTrue(_subtestImmutability(immutable));
  }

  // try all possible sorts of changes to an immutable AS
  private final boolean _subtestImmutability(AnnotationSet immutable){
    boolean threwException  = false;
    Node startNode = null;

    try {
      immutable.add(null, null, null, null, null);
    }
    catch(InvalidOffsetException e) {}
    catch(UnsupportedOperationException e) {threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.add(startNode, null, null, null);
    }
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
      immutable.add(new Long(0), null, null, null);
    }
    catch(InvalidOffsetException e) {}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.add(null);}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.removeAll(null);}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.addAll(null);}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.remove(null);}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.retainAll(null);}
    catch(UnsupportedOperationException e){threwException=true;}

    if (threwException==false)return false;

    try {
    immutable.clear();}
    catch(UnsupportedOperationException e){threwException=true;}

    return threwException;
  }


  /** Test exception throwing */
  public void testExceptions() {
    AnnotationSet as = new AnnotationSetImpl(doc1);
    boolean threwUp = false;

    try {
      as.add(new Long(-1), new Long(1), "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    threwUp = false;

    try {
      as.add(new Long(1), new Long(-1), "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    threwUp = false;

    try {
      as.add(new Long(1), new Long(0), "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    threwUp = false;

    try {
      as.add(null, new Long(1), "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    threwUp = false;

    try {
      as.add(new Long(1), null, "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    threwUp = false;

    try {
      as.add(new Long(999999), new Long(100000000), "T", emptyFeatureMap);
    } catch (InvalidOffsetException e) {
      threwUp = true;
    }

    /*
    // won't work until the doc size check is implemented
    if(! threwUp) fail("Should have thrown InvalidOffsetException");
    */
    threwUp = false;

  } // testExceptions()

  /** Test type index */
  public void testTypeIndex() throws Exception {
    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);
    AnnotationSet as = new AnnotationSetImpl(doc);
    AnnotationSet asBuf;
    FeatureMap fm = new SimpleFeatureMapImpl();
    Annotation a;
    Node startNode;
    Node endNode;

    // to trigger type indexing
    as.get("T");
    as.add(new Long(10), new Long(20), "T1", fm);    // 0
    as.add(new Long(10), new Long(20), "T2", fm);    // 1
    as.add(new Long(10), new Long(20), "T3", fm);    // 2
    as.add(new Long(10), new Long(20), "T1", fm);    // 3
    as.add(new Long(10), new Long(20), "T1", fm);    // 4
    as.add(new Long(10), new Long(20), "T1", fm);    // 5
    as.add(new Long(10), new Long(20), "T3", fm);    // 6
    as.add(new Long(10), new Long(20), "T1", fm);    // 7
    as.add(new Long(10), new Long(20), "T3", fm);    // 8
    as.add(new Long(10), new Long(20), "T1", fm);    // 9
    as.add(new Long(10), new Long(20), "T1", fm);    // 10

    asBuf = as.get("T");
    assertEquals(0, asBuf.size());

    asBuf = as.get("T1");
    assertEquals(7, asBuf.size());
    asBuf = as.get("T2");
    assertEquals(1, asBuf.size());
    asBuf = as.get("T3");
    assertEquals(3, asBuf.size());

    // let's check that we've only got two nodes, what the ids are and so on;
    // first construct a sorted set of annotations
    SortedSet<Annotation> sortedAnnots = new TreeSet<Annotation>(as);

    // for checking the annotation id
    int idCounter = 0;
    Iterator<Annotation> iter = sortedAnnots.iterator();
    while(iter.hasNext()) {
      a = iter.next();

      // check annot ids
      assertEquals(idCounter++, a.getId().intValue());

      startNode = a.getStartNode();
      endNode = a.getEndNode();

      // start node id
      assertEquals(0,  startNode.getId().intValue());

      // start offset
      assertEquals(10, startNode.getOffset().longValue());

      // end id
      assertEquals(1,  endNode.getId().intValue());

      // end offset
      assertEquals(20, endNode.getOffset().longValue());
    }

  } // testTypeIndex()

  /** Test the annotations set add method that uses existing nodes */
  public void testAddWithNodes() throws Exception {
    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);
    AnnotationSet as = new AnnotationSetImpl(doc);
    AnnotationSet asBuf;
    Integer newId;
    FeatureMap fm = new SimpleFeatureMapImpl();
    Annotation a;
    Node startNode;
    Node endNode;

    // to trigger type indexing
    as.get("T");
    newId = as.add(new Long(10), new Long(20), "T1", fm);    // 0
    a = as.get(newId);
    startNode = a.getStartNode();
    endNode = a.getEndNode();

    as.add(startNode, endNode, "T2", fm);    // 1
    as.add(startNode, endNode, "T3", fm);    // 2
    as.add(startNode, endNode, "T1", fm);    // 3
    as.add(startNode, endNode, "T1", fm);    // 4
    as.add(startNode, endNode, "T1", fm);    // 5
    as.add(startNode, endNode, "T3", fm);    // 6
    as.add(startNode, endNode, "T1", fm);    // 7
    as.add(startNode, endNode, "T3", fm);    // 8
    as.add(startNode, endNode, "T1", fm);    // 9
    as.add(startNode, endNode, "T1", fm);    // 10

    asBuf = as.get("T");
    assertEquals(0, asBuf.size());

    asBuf = as.get("T1");
    assertEquals(7, asBuf.size());
    asBuf = as.get("T2");
    assertEquals(1, asBuf.size());
    asBuf = as.get("T3");
    assertEquals(3, asBuf.size());

    // let's check that we've only got two nodes, what the ids are and so on;
    // first construct a sorted set of annotations
    Set<Annotation> sortedAnnots = new TreeSet<Annotation>(as);

    // for checking the annotation id
    int idCounter = 0;
    Iterator<Annotation> iter = sortedAnnots.iterator();
    while(iter.hasNext()) {
      a = iter.next();
      // check annot ids
      assertEquals(idCounter++, a.getId().intValue());

      startNode = a.getStartNode();
      endNode = a.getEndNode();

      // start node id
      assertEquals(0,  startNode.getId().intValue());

      // start offset
      assertEquals(10, startNode.getOffset().longValue());

      // end id
      assertEquals(1,  endNode.getId().intValue());

      // end offset
      assertEquals(20, endNode.getOffset().longValue());
    }

  } // testAddWithNodes()

  /** Test complex get (with type, offset and feature contraints) */
  public void testGetFeatureMapOffset() throws InvalidOffsetException {
    AnnotationSet asBuf;

    FeatureMap constraints = new SimpleFeatureMapImpl();
    constraints.put("pos", "NN");

    //Out.println(basicAS);
    //Out.println(constraints);

    asBuf = basicAS.get("T1", constraints);
    assertEquals(3, asBuf.size());
    asBuf = basicAS.get("T3", constraints);
    assertEquals(1, asBuf.size());
    asBuf = basicAS.get("T1", constraints, new Long(12));
    assertEquals(2, asBuf.size());
    asBuf = basicAS.get("T1", constraints, new Long(10));
    assertEquals(1, asBuf.size());
    asBuf = basicAS.get("T1", constraints, new Long(11));
    assertEquals(2, asBuf.size());
    asBuf = basicAS.get("T1", constraints, new Long(9));
    assertEquals(1, asBuf.size());

    constraints.put("pos", "JJ");
    //Out.println(constraints);
    asBuf = basicAS.get("T1", constraints, new Long(0));
    assertEquals(0, asBuf.size());
    asBuf = basicAS.get("T1", constraints, new Long(14));
    assertEquals(2, asBuf.size());

    constraints.put("author", "valentin");
    asBuf = basicAS.get("T1", constraints, new Long(14));
    assertEquals(0, asBuf.size());

    constraints.put("author", "the devil himself");
    asBuf = basicAS.get("T1", constraints, new Long(14));
    assertEquals(2, asBuf.size());

    asBuf = basicAS.get("T1", constraints, new Long(5));
    assertEquals(0, asBuf.size());

    constraints.put("this feature isn't", "there at all");
    asBuf = basicAS.get("T1", constraints, new Long(14));
    assertEquals(0, asBuf.size());

  } // testGetFeatureMapOffset()

  public void testGetStringLongLong() throws InvalidOffsetException {
    FeatureMap fm = new SimpleFeatureMapImpl();
    basicAS.add(21l, 25l, "T1", fm);
    basicAS.add(22l, 25l, "T1", fm);
    basicAS.add(45l, 50l, "T3", fm);

    assertEquals(0, basicAS.get(0l, 5l).size());
    assertEquals(0, basicAS.get("T1", 0l, 5l).size());

    assertEquals(5, basicAS.get(10l, 12l).size());
    assertEquals(3, basicAS.get("T1", 10l, 12l).size());

    assertEquals(11, basicAS.get(10l, 16l).size());
    assertEquals(7, basicAS.get("T1", 10l, 16l).size());

    assertEquals(11, basicAS.get(10l, 20l).size());
    assertEquals(7, basicAS.get("T1", 10l, 20l).size());

    assertEquals(6, basicAS.get(20l, 21l).size());
    assertEquals(4, basicAS.get("T1", 20l, 21l).size());

    assertEquals(0, basicAS.get(41l, 42l).size());
    assertEquals(0, basicAS.get("T1", 41l, 42l).size());
  } // testGetStringLongLong()

  public void testGetCovering() throws InvalidOffsetException {
    assertEquals(0, basicAS.getCovering(null, 0l, 5l).size());
    assertEquals(0, basicAS.getCovering("T1", 0l, 5l).size());

    //null and blank strings should be treated the same.  Just test
    //with both a couple of times.  Mostly can just test with null.
    assertEquals(0, basicAS.getCovering(null, 9l, 12l).size());
    assertEquals(0, basicAS.getCovering("  ", 9l, 12l).size());
    assertEquals(0, basicAS.getCovering("T1", 9l, 12l).size());

    assertEquals(5, basicAS.getCovering(null, 10l, 20l).size());
    assertEquals(5, basicAS.getCovering("  ", 10l, 20l).size());
    assertEquals(3, basicAS.getCovering("T1", 10l, 20l).size());

    assertEquals(11, basicAS.getCovering(null, 16l, 20l).size());
    assertEquals(7, basicAS.getCovering("T1", 16l, 20l).size());

    assertEquals(6, basicAS.getCovering(null, 16l, 21l).size());
    assertEquals(4, basicAS.getCovering("T1", 16l, 21l).size());

  } // testGetCovering()

  /** Test remove */
  public void testRemove() {
    AnnotationSet asBuf = basicAS.get("T1");
    assertEquals(7, asBuf.size());
    asBuf = basicAS.get(new Long(9));
    assertEquals(5, asBuf.size());

    basicAS.remove(basicAS.get(new Integer(0)));

    assertEquals(10, basicAS.size());
    assertEquals(10, ((AnnotationSetImpl) basicAS).annotsById.size());

    asBuf = basicAS.get("T1");
    assertEquals(6, asBuf.size());

    asBuf = basicAS.get(new Long(9));
    assertEquals(4, asBuf.size());

    assertEquals(null, basicAS.get(new Integer(0)));
    basicAS.remove(basicAS.get(new Integer(8)));
    assertEquals(9, basicAS.size());
    basicAS.removeAll(basicAS);
    assertEquals(0, basicAS.get().size());
    assertEquals(0, basicAS.get("T1").size());
    assertEquals(null, basicAS.get(new Integer(0)));
  } // testRemove()

  public void testRemoveInexistant() throws Exception{
    basicAS.add(new Long(0), new Long(10), "Foo", emptyFeatureMap);
    Annotation ann = basicAS.get("Foo").iterator().next();
    basicAS.remove(ann);
    //the second remove should do nothing...
    basicAS.remove(ann);
  }

  /** Test iterator remove */
/* This seems to be a bad idea - just testing order of hashset iterator, which
 * isn't stable....
 *
  public void testIteratorRemove() {
    AnnotationSet asBuf = basicAS.get("T1");
    assertEquals(7, asBuf.size());
    asBuf = basicAS.get(new Long(9));
    assertEquals(5, asBuf.size());

    // remove annotation with id 0; this is returned last by the
    // iterator
    Iterator<Annotation> iter = basicAS.iterator();
    Annotation ann = null;
    while(iter.hasNext()){
      ann = iter.next();
    }
    iter.remove();
    assertEquals("Last annotation from iterator not ID 0!", 0,
             ann.getId().intValue());

    assertEquals(10, basicAS.size());
    assertEquals(10, ((AnnotationSetImpl) basicAS).annotsById.size());
    asBuf = basicAS.get("T1");
    assertEquals(6, asBuf.size());
    asBuf = basicAS.get(new Long(9));
    assertEquals(4, asBuf.size());
    assertEquals(null, basicAS.get(new Integer(0)));
    basicAS.remove(basicAS.get(new Integer(8)));

  } // testIteratorRemove()
  */

  /** Test iterator */
  public void testIterator() {
    Iterator<Annotation> iter = basicAS.iterator();
    Annotation[] annots = new Annotation[basicAS.size()];
    int i = 0;

    while(iter.hasNext()) {
      Annotation a = iter.next();
      annots[i++] = a;

      assertTrue(basicAS.contains(a));
      iter.remove();
      assertTrue(!basicAS.contains(a));
    } // while

    i = 0;
    while(i < annots.length) {
      basicAS.add(annots[i++]);
      assertEquals(i, basicAS.size());
    } // while

    AnnotationSet asBuf = basicAS.get("T1");
    assertEquals(7, asBuf.size());
    asBuf = basicAS.get(new Long(9));
    assertEquals(5, asBuf.size());
  } // testIterator

  /** Test Set methods */
  public void testSetMethods() {
    Annotation a = basicAS.get(new Integer(6));
    assertTrue(basicAS.contains(a));

    Annotation[] annotArray = basicAS.toArray(new Annotation[0]);
    Object[] annotObjectArray = basicAS.toArray();
    assertEquals(11, annotArray.length);
    assertEquals(11, annotObjectArray.length);

    SortedSet<Annotation> sortedAnnots = new TreeSet<Annotation>(basicAS);
    annotArray = sortedAnnots.toArray(new Annotation[0]);
    for(int i = 0; i<11; i++)
      assertTrue( annotArray[i].getId().equals(new Integer(i)) );

    Annotation a1 = basicAS.get(new Integer(3));
    Annotation a2 = basicAS.get(new Integer(4));
    Set<Annotation> a1a2 = new HashSet<Annotation>();
    a1a2.add(a1);
    a1a2.add(a2);
    assertTrue(basicAS.contains(a1));
    assertTrue(basicAS.containsAll(a1a2));
    basicAS.removeAll(a1a2);

    assertEquals(9, basicAS.size());
    assertTrue(! basicAS.contains(a1));
    assertTrue(! basicAS.containsAll(a1a2));

    //this will not work anymore as the semantics of addAll has changed (new
    //annotations are created in order to avoid ID clashes
/*
    basicAS.addAll(a1a2);
    assertTrue(basicAS.contains(a2));
    assertTrue(basicAS.containsAll(a1a2));

    assertTrue(basicAS.retainAll(a1a2));
    assertTrue(basicAS.equals(a1a2));
*/
    basicAS.clear();
    assertTrue(basicAS.isEmpty());

  } // testSetMethods()

  /** Test AnnotationSetImpl */
  public void testAnnotationSet() throws Exception {
    // constuct an empty AS
    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "false");
    Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);

    AnnotationSet as = new AnnotationSetImpl(doc);
    assertEquals(as.size(), 0);

    // add some annotations
    FeatureMap fm1 = Factory.newFeatureMap();
    fm1.put("test", "my-value");
    FeatureMap fm2 = Factory.newFeatureMap();
    fm2.put("test", "my-value-different");
    FeatureMap fm3 = Factory.newFeatureMap();
    fm3.put("another test", "different my-value");

    Integer newId;
    newId =
      as.add(new Long(0), new Long(10), "Token", fm1);
    assertEquals(newId.intValue(), 0);
    newId =
      as.add(new Long(11), new Long(12), "Token", fm2);
    assertEquals(newId.intValue(), 1);
    assertEquals(as.size(), 2);
    assertTrue(! as.isEmpty());
    newId =
      as.add(new Long(15), new Long(22), "Syntax", fm1);

    // get by ID; remove; add(object)
    Annotation a = as.get(new Integer(1));
    as.remove(a);
    assertEquals(as.size(), 2);
    as.add(a);
    assertEquals(as.size(), 3);

    // iterate over the annotations
    Iterator<Annotation> iter = as.iterator();
    while(iter.hasNext()) {
      a = iter.next();
      if(a.getId().intValue() != 2)
        assertEquals(a.getType(), "Token");
      assertEquals(a.getFeatures().size(), 1);
    }

    // add some more
    newId =
      as.add(new Long(0), new Long(12), "Syntax", fm3);
    assertEquals(newId.intValue(), 3);
    newId =
      as.add(new Long(14), new Long(22), "Syntax", fm1);
    assertEquals(newId.intValue(), 4);
    assertEquals(as.size(), 5);
    newId =
      as.add(new Long(15), new Long(22), "Syntax", new SimpleFeatureMapImpl());

    //get by feature names
    Set<String> hs = new HashSet<String>();
    hs.add("test");
    AnnotationSet fnSet = as.get("Token", hs);
    assertEquals(fnSet.size(), 2);
    //now try without a concrete type, just features
    //we'll get some Syntax ones now too
    fnSet = as.get(null, hs);
    assertEquals(fnSet.size(), 4);


    // indexing by type
    ((AnnotationSetImpl) as).indexByType();
    AnnotationSet tokenAnnots = as.get("Token");
    assertEquals(tokenAnnots.size(), 2);

    // indexing by position
    AnnotationSet annotsAfter10 = as.get(new Long(15));
    if(annotsAfter10 == null)
      fail("no annots found after offset 10");
    assertEquals(annotsAfter10.size(), 2);

  } // testAnnotationSet
  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestAnnotation.class);
  } // suite

  /** Test get with offset and no annotation starting at given offset */
  public void _testGap() throws InvalidOffsetException {
    AnnotationSet as = basicAS;
    as.clear();
    FeatureMap fm = Factory.newFeatureMap();
    fm.put("A", "B");
    as.add(new Long(0), new Long(10), "foo", fm);
    as.add(new Long(11), new Long(20), "foo", fm);
    as.add(new Long(10), new Long(11), "space", fm);

    //do the input selection (ignore spaces)
    Set<String> input = new HashSet<String>();
    input.add("foo");
    input.add("foofoo");
    AnnotationSet annotations = null;

    if(input.isEmpty()) annotations = as;
    else{
      Iterator<String> typesIter = input.iterator();
      AnnotationSet ofOneType = null;

      while(typesIter.hasNext()){
        ofOneType = as.get(typesIter.next());

        if(ofOneType != null){
          //System.out.println("Adding " + ofOneType.getAllTypes());
          if(annotations == null) annotations = ofOneType;
          else annotations.addAll(ofOneType);
        }
      }
    }
    /* if(annotations == null) annotations = new AnnotationSetImpl(doc); */
    if (DEBUG)
      Out.println(
        "Actual input:" + annotations.getAllTypes() + "\n" + annotations
      );

    AnnotationSet res =
      annotations.get("foo", Factory.newFeatureMap(), new Long(10));

    if (DEBUG)
      Out.println(res);
    assertTrue(!res.isEmpty());
  }

  /** Test Overlaps */
  public void testOverlapsAndCoextensive() throws InvalidOffsetException {
    Node node1 = new NodeImpl(new Integer(1),new Long(10));
    Node node2 = new NodeImpl(new Integer(2),new Long(20));
    Node node4 = new NodeImpl(new Integer(4),new Long(15));
    Node node5 = new NodeImpl(new Integer(5),new Long(20));
    Node node6 = new NodeImpl(new Integer(6),new Long(30));

    FeatureMap fm1 = new SimpleFeatureMapImpl();
    fm1.put("color","red");
    fm1.put("Age",new Long(25));
    fm1.put(new Long(23), "Cristian");

    FeatureMap fm2 = new SimpleFeatureMapImpl();
    fm2.put("color","red");
    fm2.put("Age",new Long(25));
    fm2.put(new Long(23), "Cristian");

    FeatureMap fm4 = new SimpleFeatureMapImpl();
    fm4.put("color","red");
    fm4.put("Age",new Long(26));
    fm4.put(new Long(23), "Cristian");

    FeatureMap fm3 = new SimpleFeatureMapImpl();
    fm3.put("color","red");
    fm3.put("Age",new Long(25));
    fm3.put(new Long(23), "Cristian");
    fm3.put("best",new Boolean(true));

    // Start=10, End = 20
    Annotation annot1 = createAnnotation(new Integer(1),
                                           node1,
                                           node2,
                                           "pos",
                                           null);
    // Start=20, End = 30
    Annotation annot2 = createAnnotation (new Integer(2),
                                            node2,
                                            node6,
                                            "pos",
                                            null);
    // Start=20, End = 30
    Annotation annot3 = createAnnotation (new Integer(3),
                                            node5,
                                            node6,
                                            "pos",
                                            null);
    // Start=20, End = 20
    Annotation annot4 = createAnnotation (new Integer(4),
                                            node2,
                                            node5,
                                            "pos",
                                            null);
    // Start=10, End = 30
    Annotation annot5 = createAnnotation (new Integer(5),
                                            node1,
                                            node6,
                                            "pos",
                                            null);
    // Start=10, End = 15
    Annotation annot6 = createAnnotation (new Integer(6),
                                            node1,
                                            node4,
                                            "pos",
                                            null);
    // Start=null, End = null
    Annotation annot7 = createAnnotation (new Integer(7),
                                            null,
                                            null,
                                            "pos",
                                            null);

    // MAP
    // annot1 -> Start=10, End = 20
    // annot2 -> Start=20, End = 30
    // annot3 -> Start=20, End = 30
    // annot4 -> Start=20, End = 20
    // annot5 -> Start=10, End = 30
    // annot6 -> Start=10, End = 15

    // Not overlaping situations
   assertTrue("Those annotations does not overlap!",!annot1.overlaps(annot3));
   assertTrue("Those annotations does not overlap!",!annot1.overlaps(annot2));
   assertTrue("Those annotations does not overlap!",!annot2.overlaps(annot1));
   assertTrue("Those annotations does not overlap!",!annot3.overlaps(annot1));
   assertTrue("Those annotations does not overlap!",!annot4.overlaps(annot6));
   assertTrue("Those annotations does not overlap!",!annot6.overlaps(annot4));

   assertTrue("Those annotations does not overlap!",!annot6.overlaps(null));
   assertTrue("Those annotations does not overlap!",!annot1.overlaps(annot7));

   // Overlaping situations
   assertTrue("Those annotations does overlap!",annot4.overlaps(annot5));
   assertTrue("Those annotations does overlap!",annot5.overlaps(annot4));
   assertTrue("Those annotations does overlap!",annot1.overlaps(annot6));
   assertTrue("Those annotations does overlap!",annot6.overlaps(annot1));
   assertTrue("Those annotations does overlap!",annot2.overlaps(annot5));
   assertTrue("Those annotations does overlap!",annot5.overlaps(annot2));

   // Not coextensive situations
   assertTrue("Those annotations are not coextensive!",!annot1.coextensive(annot2));
   assertTrue("Those annotations are not coextensive!",!annot2.coextensive(annot1));
   assertTrue("Those annotations are not coextensive!",!annot4.coextensive(annot3));
   assertTrue("Those annotations are not coextensive!",!annot3.coextensive(annot4));
   assertTrue("Those annotations are not coextensive!",!annot4.coextensive(annot7));
   assertTrue("Those annotations are not coextensive!",!annot5.coextensive(annot6));
   assertTrue("Those annotations are not coextensive!",!annot6.coextensive(annot5));
   //Coextensive situations
   assertTrue("Those annotations are coextensive!",annot2.coextensive(annot2));
   assertTrue("Those annotations are coextensive!",annot2.coextensive(annot3));
   assertTrue("Those annotations are coextensive!",annot3.coextensive(annot2));

  }//testOverlapsAndCoextensive

  /** Test Coextensive */
  public void testIsPartiallyCompatibleAndCompatible()
                                                throws InvalidOffsetException {
    Node node1 = new NodeImpl(new Integer(1),new Long(10));
    Node node2 = new NodeImpl(new Integer(2),new Long(20));
    Node node4 = new NodeImpl(new Integer(4),new Long(15));
    Node node5 = new NodeImpl(new Integer(5),new Long(20));
    Node node6 = new NodeImpl(new Integer(6),new Long(30));

    FeatureMap fm1 = new SimpleFeatureMapImpl();
    fm1.put("color","red");
    fm1.put("Age",new Long(25));
    fm1.put(new Long(23), "Cristian");

    FeatureMap fm2 = new SimpleFeatureMapImpl();
    fm2.put("color","red");
    fm2.put("Age",new Long(25));
    fm2.put(new Long(23), "Cristian");

    FeatureMap fm4 = new SimpleFeatureMapImpl();
    fm4.put("color","red");
    fm4.put("Age",new Long(26));
    fm4.put(new Long(23), "Cristian");

    FeatureMap fm3 = new SimpleFeatureMapImpl();
    fm3.put("color","red");
    fm3.put("Age",new Long(25));
    fm3.put(new Long(23), "Cristian");
    fm3.put("best",new Boolean(true));

    // Start=10, End = 20
    Annotation annot1 = createAnnotation(new Integer(1),
                                           node1,
                                           node2,
                                           "pos",
                                           fm1);
    // Start=20, End = 30
    Annotation annot2 = createAnnotation (new Integer(2),
                                            node2,
                                            node6,
                                            "pos",
                                            fm2);
    // Start=20, End = 30
    Annotation annot3 = createAnnotation (new Integer(3),
                                            node5,
                                            node6,
                                            "pos",
                                            fm3);
    // Start=20, End = 20
    Annotation annot4 = createAnnotation (new Integer(4),
                                            node2,
                                            node5,
                                            "pos",
                                            fm4);
    // Start=10, End = 30
    Annotation annot5 = createAnnotation (new Integer(5),
                                            node1,
                                            node6,
                                            "pos",
                                            fm3);
    // Start=10, End = 15
    Annotation annot6 = createAnnotation (new Integer(6),
                                            node1,
                                            node4,
                                            "pos",
                                            fm1);

// MAP
  /*
   annot1 -> Start=10, End = 20,{color="red",Age="25",23="Cristian"}
   annot2 -> Start=20, End = 30,{color="red",Age="25",23="Cristian"}
   annot3 -> Start=20, End = 30,{color="red",Age="25",23="Cristian",best="true"}
   annot4 -> Start=20, End = 20,{color="red",Age="26",23="Cristian"}
   annot5 -> Start=10, End = 30,{color="red",Age="25",23="Cristian",best="true"}
   annot6 -> Start=10, End = 15,{color="red",Age="25",23="Cristian"}
  */
  // Not compatible situations
  assertTrue("Those annotations are not compatible!",!annot3.isCompatible(annot2));

  // Not partially compatible situations
  // They don't overlap
  assertTrue("Those annotations("+ annot1 +" & " +
                               annot2+ ") are not partially compatible!",
                                       !annot1.isPartiallyCompatible(annot2));

  // Again they don't overlap
  assertTrue("Those annotations("+ annot1 +" & " +
                               annot3+ ") are not partially compatible!",
                                       !annot1.isPartiallyCompatible(annot3));
  // Fails because of the age value
  assertTrue("Those annotations("+ annot1 +" & " +
                               annot4+ ") are not partially compatible!",
                                       !annot1.isPartiallyCompatible(annot4));
  // Fails because of the value of Age
  assertTrue("Those annotations("+ annot4 +" & " +
                               annot5+ ") are not partially compatible!",
                                       !annot4.isPartiallyCompatible(annot5));
  // Features from annot6 does not subsumes features annot3
  assertTrue("Those annotations("+ annot3 +" & " +
                               annot6+ ") are not partially compatible!",
                               !annot3.isPartiallyCompatible(annot6,null));
  // Features from annot2 does not subsumes features annot5
  assertTrue("Those annotations("+ annot5 +" & " +
                               annot2+ ") are not partially compatible!",
                               !annot5.isPartiallyCompatible(annot2,null));
  Set<Object> keySet = new HashSet<Object>();
  // They don't overlap
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot4+ ") are not partially compatible!",
                               !annot2.isPartiallyCompatible(annot4,keySet));
  keySet.add("color");
  keySet.add("Age");
  keySet.add("best");
  // Fails because of best feture
  assertTrue("Those annotations("+ annot5 +" & " +
                               annot2+ ") are not partially compatible!",
                               !annot5.isPartiallyCompatible(annot2,keySet));
  // Fails because start=end in both cases and they don't overlap
  assertTrue("Those annotations("+ annot4 +" & " +
                               annot4+ ") are not partially compatible!",
                                        !annot4.isPartiallyCompatible(annot4));

  /*
   annot1 -> Start=10, End = 20,{color="red",Age="25",23="Cristian"}
   annot2 -> Start=20, End = 30,{color="red",Age="25",23="Cristian"}
   annot3 -> Start=20, End = 30,{color="red",Age="25",23="Cristian",best="true"}
   annot4 -> Start=20, End = 20,{color="red",Age="26",23="Cristian"}
   annot5 -> Start=10, End = 30,{color="red",Age="25",23="Cristian",best="true"}
   annot6 -> Start=10, End = 15,{color="red",Age="25",23="Cristian"}
  */

  // Compatible situations
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot3+ ") should be compatible!",
                                      annot2.isCompatible(annot3));
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot3+ ") should be compatible!",
                                      annot2.isCompatible(annot3,null));
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot3+ ") should be compatible!",
                                     annot2.isCompatible(annot3,new HashSet<String>()));
  assertTrue("Those annotations("+ annot4 +" & " +
                               annot4+ ") should be compatible!",
                                        annot4.isCompatible(annot4));
  keySet = new HashSet<Object>();
  keySet.add("color");
  keySet.add(new Long(23));
  assertTrue("Those annotations("+ annot3 +" & " +
                               annot2+ ") should be compatible!",
                                      annot3.isCompatible(annot2,keySet));

  // Partially compatible situations
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot3+ ") should be partially compatible!",
                                        annot2.isPartiallyCompatible(annot3));
  assertTrue("Those annotations("+ annot2 +" & " +
                               annot2+ ") should be partially compatible!",
                                        annot2.isPartiallyCompatible(annot2));
  assertTrue("Those annotations are partially compatible!",
                                        annot1.isPartiallyCompatible(annot5));
  assertTrue("Those annotations are partially compatible!",
                                        annot1.isPartiallyCompatible(annot6));
  assertTrue("Those annotations are partially compatible!",
                                        annot3.isPartiallyCompatible(annot5));
  assertTrue("Those annotations are partially compatible!",
                                        annot5.isPartiallyCompatible(annot3));
  assertTrue("Those annotations are partially compatible!",
                                        annot6.isPartiallyCompatible(annot5));

  }// testIsPartiallyCompatibleAndCompatible


  public void testFeatureSubsumeMethods(){

    FeatureMap fm1 = Factory.newFeatureMap();
    fm1.put("k1","v1");
    fm1.put("k2","v2");

    FeatureMap fm2 = Factory.newFeatureMap();
    fm2.put("k1","v1");

    Set<String> featKeysSet1 = new HashSet<String>();
    featKeysSet1.add("k1");
    featKeysSet1.add("k2");
    featKeysSet1.add("k3");
    featKeysSet1.add("k4");

    assertTrue(fm1 + " should subsume " + fm2 + " using the key set" +
                               featKeysSet1,fm1.subsumes(fm2, featKeysSet1));
    assertTrue(fm1 + " should subsume " + fm2 +
                            " taking all feat into consideration",
                            fm1.subsumes(fm2, null));

    FeatureMap fm3 = Factory.newFeatureMap();
    fm3.put("k1","v1");
    fm3.put("k2","v2");
    fm3.put("k3",new Integer(3));

    Set<String> featKeysSet2 = new HashSet<String>();
    featKeysSet2.add("k1");

    assertTrue(fm1 + " should subsume " + fm3 + " using the key set" +
                          featKeysSet2,fm1.subsumes(fm3, featKeysSet2));
    assertTrue(fm1 + " should NOT subsume " + fm3 +
                                " taking all feats into consideration",
                                !fm1.subsumes(fm3,null));

    FeatureMap fm4 = Factory.newFeatureMap();
    fm4.put("k1",new Integer(2));
    fm4.put("k2","v2");
    fm4.put("k3","v3");

    Set<String> featKeysSet3 = new HashSet<String>();
    featKeysSet3.add("k2");

    assertTrue(fm3 + " should subsume " + fm4 + " using the key set" +
                              featKeysSet3, fm4.subsumes(fm3,featKeysSet3));
    assertTrue(fm4 + " should NOT subsume " + fm3 +
                                " taking all feats into consideration",
                                !fm4.subsumes(fm3,null));


  }// testFeatureSubsumeMethods();

  protected Annotation createAnnotation
    (Integer id, Node start, Node end, String type, FeatureMap features) {
      return new AnnotationImpl(id, start, end, type, features);
  }

  /** Test inDocumentOrder() and getStartingAt(long) */
  public void testDocumentOrder() throws Exception {
    FeatureMap params = Factory.newFeatureMap();
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, "true");
    Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl",
                                                    params);

    AnnotationSet originals = doc.getAnnotations("Original markups");
    if(originals instanceof AnnotationSetImpl) {
      AnnotationSetImpl origimpl = (AnnotationSetImpl)originals;
      List<Annotation> ordered = origimpl.inDocumentOrder();
      assertNotNull(ordered);
      assertEquals(20, ordered.size());
      assertEquals(33, ordered.get(4).getStartNode().getOffset().intValue());
      for(int i=1;i<ordered.size();i++) {
        assertTrue("Elements "+(i-1)+"/"+i,
            ordered.get(i-1).getStartNode().getOffset() <= ordered.get(i).getStartNode().getOffset());
      }
      AnnotationSet anns;
      anns = origimpl.getStartingAt(0);
      assertEquals(4,anns.size());
      anns = origimpl.getStartingAt(1);
      assertEquals(0,anns.size());
      anns = origimpl.getStartingAt(33);
      assertEquals(4,anns.size());
      anns = origimpl.getStartingAt(48);
      assertEquals(1,anns.size());
      anns = origimpl.getStartingAt(251);
      assertEquals(1,anns.size());
    }
  } // testDocumentOrder()
  
  
  
  public static void main(String[] args){

    try{
      TestAnnotation testAnnot = new TestAnnotation("");
      testAnnot.setUp();
      testAnnot.testIterator();
      testAnnot._testGap();
      testAnnot.tearDown();
      testAnnot.testOverlapsAndCoextensive();
      testAnnot.testIsPartiallyCompatibleAndCompatible();
    }catch(Throwable t){
      t.printStackTrace();
    }
  }
} // class TestAnnotation

