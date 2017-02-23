/*
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Created on Jul 25, 2005
 *  
 *  $Id: AnnotationFactory.java 7452 2006-06-15 14:45:17Z ian_roberts $
 *
 */
package gate.annotation;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Node;
import gate.FeatureMap;

/**
 * Factory used to create annotations in an annotation set.
 * 
 * @author Ken Williams
 */
public interface AnnotationFactory
{
  /**
   * Adds a new AnnotationImpl to the given set.
   * @param set the set to which the new annotation will be added
   * @param id the ID to use for the new annotation
   * @param start the starting node for the new annotation
   * @param end the ending node for the new annotation
   * @param type the type of the new annotation
   * @param features the features for the new annotation
   */
  public Annotation createAnnotationInSet(AnnotationSet set, Integer id,
                                          Node start, Node end, String type,
                                          FeatureMap features);
}
