/*
 *  OrderByRestriction.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 10/Dec/2001
 */
package gate.util;

import java.io.Serializable;


public class OrderByRestriction implements Serializable{

  private static final long serialVersionUID = -8295746692769442286L;

  /* Type of operator for cmarision in query*/
  public static final int OPERATOR_ASCENDING = 100;
  public static final int OPERATOR_DESCENDING = 101;

  private String key;
  private int operator_;

  /** Constructor.
   *
   * @param key string value of feature key
   * @param operator_ type of operator for ordering: ascending or descending
   */
  public OrderByRestriction(String key,  int operator_){
    this.key = key;
    this.operator_ = operator_;
  }

  /**
   * @return String key of the feature
   */
  public String getKey(){
    return key;
  }

  /**
   * @return int type of operator for ordering: ascending or descending
   */
  public int getOperator(){
    return operator_;
  }
}