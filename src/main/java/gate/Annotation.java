/*
 *  Annotation.java
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
 *  $Id: Annotation.java 17542 2014-03-05 10:36:35Z markagreenwood $
 */

package gate;

import gate.event.AnnotationListener;

import java.io.Serializable;
import java.util.Set;

/** An Annotation is an arc in an AnnotationSet. It is immutable, to avoid
  * the situation where each annotation has to point to its parent graph in
  * order to tell it to update its indices when it changes.
  * <P> Changes from TIPSTER: no ID; single span only.
  * 
  * It inherits from SimpleAnnotation in order to allow users to add events
  * and more methods for comparing annotations
  *
  * The event code is needed so a persistent annotation set can listen to
  * its annotations and update correctly the database
  */
public interface Annotation
extends SimpleAnnotation, Serializable {

  /** This verifies if <b>this</b> annotation is compatible with another one.
    * Compatible means that they hit the same possition and the FeatureMap of
    * <b>this</b> is incuded into aAnnot FeatureMap.
    * @param anAnnot a gate Annotation.
    * @return <code>true</code> if aAnnot is compatible with <b>this</b> and
    * <code>false</code> otherwise.
    */
  public boolean isCompatible(Annotation anAnnot);

  /** This verifies if <b>this</b> annotation is compatible with another one,
   *  given a set with certain keys.
    * In this case, compatible means that they hit the same possition
    * and those keys from <b>this</b>'s FeatureMap intersected with
    * aFeatureNamesSet are incuded together with their values into the aAnnot's
    * FeatureMap.
    * @param anAnnot a gate Annotation.
    * @param aFeatureNamesSet is a set containing certian key that will be
    * intersected with <b>this</b>'s FeatureMap's keys.
    * @return <code>true</code> if aAnnot is compatible with <b>this</b> and
    * <code>false</code> otherwise.
    */
  public boolean isCompatible(Annotation anAnnot, Set<? extends Object> aFeatureNamesSet);

  /** This method verifies if two annotation and are partially compatible.
    * Partially compatible means that they overlap and the FeatureMap of
    * <b>this</b> is incuded into FeatureMap of aAnnot.
    * @param anAnnot a gate Annotation.
    * @return <code>true</code> if <b>this</b> is partially compatible with
    * aAnnot and <code>false</code> otherwise.
    */
  public boolean isPartiallyCompatible(Annotation anAnnot);

  /** This method verifies if two annotation and are partially compatible,
    * given a set with certain keys.
    * In this case, partially compatible means that they overlap
    * and those keys from <b>this</b>'s FeatureMap intersected with
    * aFeatureNamesSet are incuded together with their values into the aAnnot's
    * FeatureMap.
    * @param anAnnot a gate Annotation.
    * @param aFeatureNamesSet is a set containing certian key that will be
    * intersected with <b>this</b>'s FeatureMap's keys.
    * @return <code>true</code> if <b>this</b> is partially compatible with
    * aAnnot and <code>false</code> otherwise.
    */
  public boolean isPartiallyCompatible(Annotation anAnnot,Set<? extends Object> aFeatureNamesSet);

  /**  Two Annotation are coestensive if their offsets are the same.
    *  @param anAnnot A Gate annotation.
    *  @return <code>true</code> if two annotation hit the same possition and
    *  <code>false</code> otherwise
    */
  public boolean coextensive(Annotation anAnnot);

  /** 
   * This method determines if <b>this</b> overlaps aAnnot, i.e. if either
   * the beginning or the end (or both) of anAnnot is
   * contained in the span of <b>this</b>.
   * 
   * @param aAnnot a gate Annotation.
   * @return <code>true</code> if they overlap and <code>false</code> false if
   * they don't or if aAnnot is null.
   */
  public boolean overlaps(Annotation aAnnot);
  
  /** This method tells if <b>this</b> annotation's text range is 
   * fully contained within the text annotated by <code>aAnnot</code>'s
   * annotation. 
   * @param aAnnot a gate Annotation.
   * @return <code>true</code> if this annotation is fully contained in the 
   * other one.
   */
  public boolean withinSpanOf(Annotation aAnnot);

  /**
   *
   * Removes an annotation listener
   */
  public void removeAnnotationListener(AnnotationListener l);
  /**
   *
   * Adds an annotation listener
   */
  public void addAnnotationListener(AnnotationListener l) ;

} // interface Annotation,
