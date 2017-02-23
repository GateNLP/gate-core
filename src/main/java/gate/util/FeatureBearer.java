/*
 *  FeatureBearer.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 7/Feb/2000
 *
 *  $Id: FeatureBearer.java 16419 2012-12-12 17:35:53Z adamfunk $
 */

package gate.util;
import gate.FeatureMap;

/** Classes whose instances each have a FeatureMap.
  */
public interface FeatureBearer
{
  /** Get the feature set */
  public FeatureMap getFeatures();

  /** Set the feature set */
  public void setFeatures(FeatureMap features);

} // interface FeatureBearer
