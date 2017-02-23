/*
 *  TestDocumentStaxUtils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 1/Sep/2008
 *
 *  $Id: TestDocumentStaxUtils.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.corpora;

import junit.framework.*;

public class TestDocumentStaxUtils extends TestCase {

  public TestDocumentStaxUtils(String name) {
    super(name);
  }

  @Override
  public void setUp() {
    
  }

  public void testIllegalXMLCharacters() throws Exception {
    char[] chars = new char[] {
        '\u0000', // null
        '\n', // LF (this is OK)
        '\uD801', '\uDC01', // surrogate pair, this is OK
        ' ', // space (this is OK)
        '\uDC03' // unpaired low surrogate
    };

    DocumentStaxUtils.replaceXMLIllegalCharacters(chars);
    assertEquals("Null character should have been replaced by space",
        ' ', chars[0]);
    assertEquals("Line feed character should not have been replaced",
        '\n', chars[1]);
    assertEquals("High surrogate of a valid pair should not have been replaced",
        '\uD801', chars[2]);
    assertEquals("Low surrogate of a valid pair should not have been replaced",
        '\uDC01', chars[3]);
    assertEquals("Space character should not have been replaced",
        ' ', chars[4]);
    assertEquals("Unpaired low surrogate should have been replaced",
        ' ', chars[5]);
  }

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestDocumentStaxUtils.class);
  } // suite

}
