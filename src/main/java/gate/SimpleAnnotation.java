/*
 *  SimpleAnnotation.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 19/Jan/00
 *
 *  $Id: SimpleAnnotation.java 17649 2014-03-13 15:04:17Z markagreenwood $
 */

package gate;

import gate.util.FeatureBearer;
import gate.util.IdBearer;

import java.io.Serializable;

/** An Annotation is an arc in an AnnotationSet. It is immutable, to avoid
  * the situation where each annotation has to point to its parent graph in
  * order to tell it to update its indices when it changes.
  * <P> Changes from TIPSTER: no ID; single span only.
  *
  * SimpleAnnotation was introduced to simplify the API of annotations
  */
public interface SimpleAnnotation
extends FeatureBearer, IdBearer, Comparable<Object>, Serializable {

  /** The type of the annotation (corresponds to TIPSTER "name"). */
  public String getType();

  /** The start node. */
  public Node getStartNode();

  /** The end node. */
  public Node getEndNode();

  /** Ordering */
  @Override
  public int compareTo(Object o) throws ClassCastException;

} // interface SimpleAnnotation,
