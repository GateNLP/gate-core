/*
 *  Term.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 19/Apr/2002
 *
 */

package gate.creole.ir;

/** This class represents pairs NAME-VALUE*/
public class Term{

  /** Name */
  private String name;
  /**  Value*/
  private String value;

  /** Constructor of the class. */
  public Term(String name, String value){
    this.name = name;
    this.value = value;
  }

  /** @return String name*/
  public String getName(){
    return name;
  }

  /** @return String value*/
  public String getValue(){
    return value;
  }
}