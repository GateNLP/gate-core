/*
 *  MimeType.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 27 Aug 2003
 *
 *  $Id: MimeType.java 20036 2017-02-01 04:51:37Z markagreenwood $
 */

package gate.corpora;

import java.util.HashMap;
import java.util.Map;

/**
 * A very basic implementation for a MIME Type.
 */
public class MimeType {
  /**
   * Constructor from type and subtype.
   * @param type
   * @param subType
   */
  public MimeType(String type, String subType){
    this.type = type;
    this.subtype = subType;
    parameters = new HashMap<String,String>();
  }

  /**
   * Two MIME Types are equal if their types and subtypes coincide.
   * @param obj the othe MIME Type to be compared with this one.
   * @return true if the two MIME Types are the same.
   */
  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    MimeType other = (MimeType)obj;
    if(subtype == null) {
      if(other.subtype != null) return false;
    } else if(!subtype.equals(other.subtype)) return false;
    if(type == null) {
      if(other.type != null) return false;
    } else if(!type.equals(other.type)) return false;
    return true;
  }
  
  /**
   * The hashcode is composed (by addition) from the hashcodes for the type and
   * subtype.
   * @return an integer hashcode
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((subtype == null) ? 0 : subtype.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  /**
   * Returns the type component of this MIME Type.
   * @return a String value.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type component of this MIME type.
   * @param type a String value.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the subtype component of this MIME Type.
   * @return a String value.
   */
  public String getSubtype() {
    return subtype;
  }

  /**
   * Sets the subtype component of this MIME type.
   * @param subtype a String value.
   */
  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

  /**
   * Adds (and replaces if necessary) a parameter to this MIME type.
   * @param param the name of the parameter.
   * @param value the value of the parameter.
   */
  public void addParameter(String param, String value){
    parameters.put(param, value);
  }

  /**
   * Gets the value for a particular parameter.
   * @param name the name of the parameter.
   * @return a {@link java.lang.String} value.
   */
  public String getParameterValue(String name){
    return parameters.get(name);
  }

  /**
   * Checks to see if this MIME type has a particular parameter.
   * @param name the name of the parameter.
   * @return a boolean value.
   */
  public boolean hasParameter(String name){
    return parameters.containsKey(name);
  }

  /**
   * The type component
   */
  protected String type;

  /**
   * The subtype component
   */
  protected String subtype;

  /**
   * The parameters map.
   */
  protected Map<String,String> parameters;
  
  public String toString() {
     return type+"/"+subtype;
  }
}