/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 17/05/2002
 *
 *  $Id: FeatureReader.java 17530 2014-03-04 15:57:43Z markagreenwood $
 *
 */
package gate.creole.ir;

import gate.Document;

/**
 * A property reader for a document feature
 */
public class FeatureReader implements PropertyReader {

  public FeatureReader(String featureName){
    this.featureName = featureName;
  }

  @Override
  public String getPropertyValue(Document doc) {
    if(doc.getFeatures() != null){
      Object value = doc.getFeatures().get(featureName);
      if(value != null) return value.toString();
    }
    return "";
  }

  String featureName;
  static final long serialVersionUID = -2831603184521440396L;
}