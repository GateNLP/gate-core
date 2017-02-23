/*
 *  SimpleRelation.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 27 Feb 2012
 *
 *  $Id: SimpleRelation.java 17173 2013-12-15 18:54:25Z markagreenwood $
 */
package gate.relations;

import gate.Factory;
import gate.FeatureMap;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * A simple implementation for the {@link Relation} interface.
 */
public class SimpleRelation implements Relation {

  private static final long serialVersionUID = 6866132107461267866L;

  protected String type;

  protected int[] members;

  protected int id;

  protected FeatureMap features;

  protected Object userData;

  /**
   * You should never create instances of this class directly, you
   * should create new relations via the appropriate methods of
   * {@link RelationSet}. This method is only publicly available to
   * support persistence.
   */
  public SimpleRelation(int id, String type, int[] members) {
    super();
    this.id = id;
    this.type = type;
    this.members = members;
    features = Factory.newFeatureMap();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.relations.Relation#getType()
   */
  @Override
  public String getType() {
    return type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.relations.Relation#getMembers()
   */
  @Override
  public int[] getMembers() {
    return members;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(id).append(": ");
    String typeOut =
            type.replaceAll("\\(", Matcher.quoteReplacement("\\(")).replaceAll(
                    "\\)", Matcher.quoteReplacement("\\)"));
    str.append(typeOut).append("(");
    for(int i = 0; i < members.length; i++) {
      if(i > 0) str.append(", ");
      str.append(members[i]);
    }
    str.append(")");
    if(features != null) {
      str.append("#").append(features.toString());
    }
    if(userData != null) {
      str.append("#").append(userData.toString());
    }
    return str.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((features == null) ? 0 : features.hashCode());
    result = prime * result + Arrays.hashCode(members);
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((userData == null) ? 0 : userData.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    SimpleRelation other = (SimpleRelation)obj;
    if(features == null) {
      if(other.features != null) return false;
    } else if(!features.equals(other.features)) return false;
    if(!Arrays.equals(members, other.members)) return false;
    if(type == null) {
      if(other.type != null) return false;
    } else if(!type.equals(other.type)) return false;
    if(userData == null) {
      if(other.userData != null) return false;
    } else if(!userData.equals(other.userData)) return false;
    return true;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public FeatureMap getFeatures() {
    return features;
  }

  @Override
  public void setFeatures(FeatureMap features) {
    this.features = features;
  }

  @Override
  public Object getUserData() {
    return userData;
  }

  @Override
  public void setUserData(Object data) {
    userData = data;
  }
}
