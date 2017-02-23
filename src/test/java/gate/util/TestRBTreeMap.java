/*
 *  TestRBTreeMap.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *  
 *  Valentin Tablan, 09/02/2000
 *
 *  $Id: TestRBTreeMap.java 19622 2016-10-04 07:15:52Z markagreenwood $
 */

package gate.util;

import junit.framework.TestCase;

/** Tests for the RBTreeMap class
  */
public class TestRBTreeMap extends TestCase
{

  /** Create a map  with sparse values as keys */
  @Override
  public void setUp() {
    
    //we don't need to init GATE for this test
    
    myTree=new RBTreeMap<Object,Object>();
    myTree.put(new Long(10),"Ten");
    myTree.put(new Long(20),"Twenty");
    myTree.put(new Long(30),"Thirty");
    myTree.put(new Long(40),"Forty");
    myTree.put(new Long(50),"Fifty");
  } // setUp

  /** A test test */
  public void testExact() {
    Object result;
    Long key;
    String expected;

    //try the first entry
    key=new Long(10);
    expected="Ten";
    result=myTree.get(key);
    assertEquals(expected,result);

    //try some entry
    key=new Long(30);
    expected="Thirty";
    result=myTree.get(key);
    assertEquals(expected,result);

    //try the last entry
    key=new Long(50);
    expected="Fifty";
    result=myTree.get(key);
    assertEquals(expected,result);

    //try the last entry
    key=new Long(15);
    result=myTree.get(key);
    assertNull(result);

  } // testExact

  public void testClosestMatch(){
    Object[] result;
    Long key;
    Object[] expected;

    //try a match
    key=new Long(10);
    expected=new Object[]{"Ten","Ten"};
    result=myTree.getClosestMatch(key);
    assertEquals("TestCM 1",expected[0],result[0]);
    assertEquals("TestCM 2",expected[1],result[1]);

    //try glb=null
    key=new Long(5);
    expected=new Object[]{null,"Ten"};
    result=myTree.getClosestMatch(key);
    assertNull("TestCM 3",result[0]);
    assertEquals("TestCM 4",expected[1],result[1]);

    //the normal case
    key=new Long(15);
    expected=new Object[]{"Ten","Twenty"};
    result=myTree.getClosestMatch(key);
    assertEquals("TestCM 5",expected[0],result[0]);
    assertEquals("TestCM 6",expected[1],result[1]);

    //try lub=null
    key=new Long(55);
    expected=new Object[]{"Fifty",null};
    result=myTree.getClosestMatch(key);
    assertEquals("TestCM 7",expected[0],result[0]);
    assertNull("TestCM 8",result[1]);

    //empty the tree
    myTree=new RBTreeMap<Object,Object>();

    //try glb=lub=null
    key=new Long(15);
    expected=new Object[]{null,null};
    result=myTree.getClosestMatch(key);
    assertNull("TestCM 9",result[0]);
    assertNull("TestCM 10",result[1]);
  }

  public void testGetNextOf(){
    Object result;
    Long key;
    String expected;

    //try the first entry
    key=new Long(5);
    expected="Ten";
    result=myTree.getNextOf(key);
    assertEquals(expected,result);

    //try some entry
    key=new Long(20);
    expected="Twenty";
    result=myTree.getNextOf(key);
    assertEquals(expected,result);

    //try the "next" case
    key=new Long(15);
    expected="Twenty";
    result=myTree.getNextOf(key);
    assertEquals(expected,result);

    //try the last case
    key=new Long(55);
    result=myTree.getNextOf(key);
    assertNull(result);

    //empty the tree
    myTree=new RBTreeMap<Object,Object>();
    key=new Long(15);
    result=myTree.getNextOf(key);
    assertNull(result);
  }

  private RBTreeMap<Object,Object> myTree;
  
} // class TestRBTreeMap
