/*
 *  TestAnnotation.java
 *
 *  Copyright (c) 1995-2015, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 */

package gate;

import junit.framework.*;

import static gate.Utils.*;
import gate.creole.ResourceInstantiationException;
import gate.util.InvalidOffsetException;

/** 
 * Tests for the gate.Utils methods 
 */
public class TestUtils extends TestCase
{
  /** Construction */
  public TestUtils(String name) { super(name); }

  /** 
   * Fixture set up.
   * 
   * @throws java.lang.Exception 
   */
  @Override
  public void setUp() throws Exception
  {
    Gate.setNetConnected(false);
    if (Gate.getGateHome() == null) Gate.init();
  } // setUp


  // test getting and combining annotations
  public void testAnnotationSetHandling() 
          throws InvalidOffsetException, ResourceInstantiationException {
    // create a new document of 100 spaces
    Document doc = Factory.newDocument(new String(new char[100]).replace("\0", " "));
    // get an annotation set for the first couple of tests
    AnnotationSet set1 = doc.getAnnotations("s1");
    // add 3 coextensive annotations on top of each other
    Annotation ann1_1 = set1.get(addAnn(set1,0,5,"t1.1",featureMap()));
    Annotation ann1_2 = set1.get(addAnn(set1,0,5,"t1.2",featureMap()));
    @SuppressWarnings("unused")
    Annotation ann1_3 = set1.get(addAnn(set1,0,5,"t1.3",featureMap()));
    // add 3 overlapping annotations
    Annotation ann2_1 = set1.get(addAnn(set1,10,15,"t2.1",featureMap()));
    @SuppressWarnings("unused")
    Annotation ann2_2 = set1.get(addAnn(set1,11,16,"t2.2",featureMap()));
    @SuppressWarnings("unused")
    Annotation ann2_3 = set1.get(addAnn(set1,12,17,"t2.3",featureMap()));
    
    AnnotationSet ret = getCoextensiveAnnotations(set1,ann1_1);
    assertEquals(3, ret.size());
    
    ret = minus(ret,ann1_1);
    assertEquals(2, ret.size());
    
    ret = minus(ret,ann2_1);
    assertEquals(2, ret.size());
    
    ret = minus(ret);
    assertEquals(2, ret.size());
    
    ret = minus(ret,ann1_2);
    assertEquals(1, ret.size());
    
    ret = getOverlappingAnnotations(set1, ann2_1);
    assertEquals(3, ret.size());
    
    ret = plus(ret,ann1_2);
    assertEquals(4, ret.size());
    
    ret = intersect(getCoextensiveAnnotations(set1,ann1_1),ret);
    assertEquals(1, ret.size());
    
    
  } // testAnnotationSetHandling
} // class TestAnnotation

