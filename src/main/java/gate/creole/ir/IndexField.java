/*
 *  IndexField.java
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

import java.io.Serializable;

public class IndexField implements Serializable{

  static final long serialVersionUID = 3632609241787241616L;

  /** Name of field for indexing - the name of the feature key of
   *  the document should be same. */
  private String fieldName;

  /** Reader object for this field. Can be NULL. */
  private PropertyReader propReader;

  /** If set to true then the value should not be modified by the analyzer. */
  private boolean isPreseved;

  /** Constructor of the class. */
  public IndexField(String name, PropertyReader rdr, boolean preseved) {
    this.fieldName = name;
    this.propReader = rdr;
    this.isPreseved = preseved;
  }

  /** @return String name of the field.*/
  public String getName(){
    return fieldName;
  }

  /** @return Reader object for this field or null */
  public PropertyReader getReader(){
    return propReader;
  }

  /** @return boolean preservation of value */
  public boolean isPreseved(){
    return isPreseved;
  }

}