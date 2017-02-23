/*
 *  AbstractFeatureBearer.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 15/Oct/2000
 *
 *  $Id: AbstractFeatureBearer.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.util;
import java.io.Serializable;

import gate.FeatureMap;

/** A convenience implemetation of FeatureBearer.
  * @see FeatureBearer
  */
abstract public class AbstractFeatureBearer implements FeatureBearer, Serializable
{

  static final long serialVersionUID = -2962478253218344471L;

  /** Get the feature set */
  @Override
  public FeatureMap getFeatures() { return features; }

  /** Set the feature set */
  @Override
  public void setFeatures(FeatureMap features) { this.features = features; }

  /** The feature set */
  protected FeatureMap features;

} // class AbstractFeatureBearer
