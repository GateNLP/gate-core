/*
 *  ColorGenerator.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 11/07/2000
 *
 *  $Id: ColorGenerator.java 17873 2014-04-18 11:14:43Z markagreenwood $
 */
package gate.swing;

import java.awt.Color;
import java.util.LinkedList;

/**
 * This class is used to generate random colours that are evenly distributed in
 * the colours space.
 *
 */
public class ColorGenerator {

  /**
   * Creates a new ColorGenerator
   *
   */
  public ColorGenerator() {
    for(int i = 0; i < 8; i++)availableSpacesList[i] = new LinkedList<ColorSpace>();
    ColorSpace usedCS = new ColorSpace(0,0,0,255);
    availableSpacesList[0].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG,
                                               usedCS.baseB,
                                               usedCS.radius/2));
    availableSpacesList[1].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB,
                                               usedCS.radius/2));
    availableSpacesList[2].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB,
                                               usedCS.radius/2));

    availableSpacesList[3].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
    availableSpacesList[4].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
    availableSpacesList[5].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
  /*
    availableSpacesList[6].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));

  */
  //    Color foo = getNextColor();
  }

  /**
   * Gets the next random colour
   *
   */
  public Color getNextColor(){
    ColorSpace usedCS;
    listToRead = listToRead % 8;

    if(availableSpacesList[listToRead].isEmpty()){
      usedCS = usedSpacesList.removeFirst();
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));

      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));

    }
    usedCS = availableSpacesList[listToRead].removeFirst();
    Color res = new Color(usedCS.baseR + usedCS.radius/2,
                          usedCS.baseG + usedCS.radius/2,
                          usedCS.baseB + usedCS.radius/2);
    usedSpacesList.addLast(usedCS);
    listToRead++;
    res = res.brighter();
    return res;
  }

  /**
   * Represents a colur space. A colour space is a cube in a tridimiensional
   * space (where the axes represent red/green/blue values) defined by a point
   * and a radius(the length of the edge).
   */
  class ColorSpace{
    /**
     * Creates a new ColorSpace
     *
     * @param r
     * @param g
     * @param b
     * @param radius
     */
    ColorSpace(int r, int g, int b, int radius){
      baseR = r;
      baseG = g;
      baseB = b;
      this.radius = radius;
    }

    /**      *
     */
    int baseR, baseG, baseB;
    /**      */
    int radius;
  }

  /**    */
  @SuppressWarnings({"unchecked","rawtypes"})
  LinkedList<ColorSpace>[] availableSpacesList = new LinkedList[8];

  /**    */
  LinkedList<ColorSpace> usedSpacesList = new LinkedList<ColorSpace>();

  /**    */
  int listToRead = 0;

} // ColorGenerator
