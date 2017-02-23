/*
 *  NameBearer.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 21 Sep 2001
 *
 *  $Id: AbstractNameBearer.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util;

import java.io.Serializable;

public abstract class AbstractNameBearer implements Serializable {

  static final long serialVersionUID = -3745118855639865521L;

    /** Sets the name of this resource*/
  public void setName(String name){
    this.name = name;
  }

  /** Returns the name of this resource*/
  public String getName(){
    return name;
  }

  protected String name;
}