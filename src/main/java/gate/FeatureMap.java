/*
 *  FeatureMap.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, Jan/19/2000
 *
 *  $Id: FeatureMap.java 17546 2014-03-05 20:54:08Z markagreenwood $
 */

package gate;
import java.util.Set;

import gate.creole.ontology.Ontology;
import gate.event.FeatureMapListener;

/** An attribute-value matrix. Represents the content of an annotation, the
  * meta-data on a resource, and anything else we feel like.
  *
  * The event code is needed so a persistent annotation can fire updated events
  * when its features are updated
  */
public interface FeatureMap extends SimpleFeatureMap
{
  /** Tests if <b>this</b> featureMap object includes  aFeatureMap features.
    * @param aFeatureMap object which will be included  or not in  <b>this</b>
    * FeatureMap obj.
    * @return <code>true</code> if <b>this</b> includes aFeatureMap
    * and <code>false</code> if not.
    */
  public boolean subsumes(FeatureMap aFeatureMap);

  /** Tests if <b>this</b> featureMap object includes aFeatureMap features. <br>
    * If the feature map contains <code>class</code> and (optionally) <code>ontology</code> features:<br>
    * then the ontologyLR is used to provide ontology based subsume with respect to the subClassOf relations.
    * @param ontologyLR an ontology to be used for the subsume
    * @param aFeatureMap object which will be included  or not in  <b>this</b>
    * FeatureMap obj.
    * @return <code>true</code> if <b>this</b> includes aFeatureMap
    * and <code>false</code> if not.
    */
  public boolean subsumes(Ontology ontologyLR, FeatureMap aFeatureMap);

  /** Tests if <b>this</b> featureMap object includes aFeatureMap but only
    * for the features present in the aFeatureNamesSet.
    * @param aFeatureMap which will be included or not in <b>this</b>
    * FeatureMap obj.
    * @param aFeatureNamesSet is a set of strings representing the names of the
    * features that would be considered for subsumes.
    * @return <code>true</code> if all features present in the aFeaturesNameSet
    * from aFeatureMap are included in <b>this</b> obj, or <code>false</code>
    * otherwise.
    */
  public boolean subsumes(FeatureMap aFeatureMap, Set<? extends Object> aFeatureNamesSet);

  /**
   *
   * Removes a gate listener
   */
  public void removeFeatureMapListener(FeatureMapListener l);
  /**
   *
   * Adds a gate listener
   */
  public void addFeatureMapListener(FeatureMapListener l);

} // interface FeatureMap
