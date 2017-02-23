/*
 *  AnnotationImpl.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, Jan/00
 *
 *  $Id: AnnotationImpl.java 19642 2016-10-06 09:52:06Z markagreenwood $
 */

package gate.annotation;

import java.io.Serializable;
import java.util.Set;
import java.util.Vector;

import gate.Annotation;
import gate.FeatureMap;
import gate.Node;
import gate.event.AnnotationEvent;
import gate.event.AnnotationListener;
import gate.util.AbstractFeatureBearer;

/** Provides an implementation for the interface gate.Annotation
 *
 */
public class AnnotationImpl extends AbstractFeatureBearer
                            implements Annotation {

  /** Freeze the serialization UID. */
  static final long serialVersionUID = -5658993256574857725L;

  /** Constructor. Package access - annotations have to be constructed via
   * AnnotationSets.
   *
   * @param id The id of the new annotation;
   * @param start The node from where the annotation will depart;
   * @param end The node where trhe annotation ends;
   * @param type The type of the new annotation;
   * @param features The features of the annotation.
   */
  protected AnnotationImpl(
    Integer id, Node start, Node end, String type, FeatureMap features
  ) {
    this.id       = id;
    this.start    = start;
    this.end      = end;
    this.type     = type;
    this.features = features;

  } // AnnotationImpl

  /** The ID of the annotation.
   */
  @Override
  public Integer getId() {
    return id;
  } // getId()

  /** The type of the annotation (corresponds to TIPSTER "name").
   */
  @Override
  public String getType() {
    return type;
  } // getType()

  /** The start node.
   */
  @Override
  public Node getStartNode() {
    return start;
  } // getStartNode()

  /** The end node.
   */
  @Override
  public Node getEndNode() {
    return end;
  } // getEndNode()

  /** String representation of hte annotation
   */
  @Override
  public String toString() {
    return "AnnotationImpl: id=" + id + "; type=" + type +
           "; features=" + features + "; start=" + start +
           "; end=" + end + System.getProperty("line.separator");
  } // toString()

  /** Ordering
   */
  @Override
  public int compareTo(Object o) throws ClassCastException {
    Annotation other = (Annotation) o;
    return id.compareTo(other.getId());
  } // compareTo

  /** When equals called on two annotations returns true, is REQUIRED that the
    * value hashCode for each annotation to be the same. It is not required
    * that when equals return false, the values to be different. For speed, it
    * would be beneficial to happen that way.
    */

  @Override
  public int hashCode(){
    // hash code based on type, id, start and end offsets (which should never
    // change once the annotation has been created).
    int hashCodeRes = 17;
    hashCodeRes = 31*hashCodeRes
        + ((type == null) ? 0 : type.hashCode());
    hashCodeRes = 31*hashCodeRes
        + ((id == null) ? 0 : id.hashCode());
    return  hashCodeRes;
  }// hashCode

  /** Returns true if two annotation are Equals.
   *  Two Annotation are equals if their offsets, types, id and features are the
   *  same.
   */
  @Override
  public boolean equals(Object obj){
    if(obj == null)
      return false;
    Annotation other;
    if(obj instanceof AnnotationImpl){
      other = (Annotation) obj;
    }else return false;

    // If their types are not equals then return false
    if((type == null) ^ (other.getType() == null))
      return false;
    if(type != null && (!type.equals(other.getType())))
      return false;

    // If their types are not equals then return false
    if((id == null) ^ (other.getId() == null))
      return false;
    if((id != null )&& (!id.equals(other.getId())))
      return false;

    // If their start offset is not the same then return false
    if((start == null) ^ (other.getStartNode() == null))
      return false;
    if(start != null){
      if((start.getOffset() == null) ^
         (other.getStartNode().getOffset() == null))
        return false;
      if(start.getOffset() != null &&
        (!start.getOffset().equals(other.getStartNode().getOffset())))
        return false;
    }

    // If their end offset is not the same then return false
    if((end == null) ^ (other.getEndNode() == null))
      return false;
    if(end != null){
      if((end.getOffset() == null) ^
         (other.getEndNode().getOffset() == null))
        return false;
      if(end.getOffset() != null &&
        (!end.getOffset().equals(other.getEndNode().getOffset())))
        return false;
    }

    // If their featureMaps are not equals then return false
    if((features == null) ^ (other.getFeatures() == null))
      return false;
    if(features != null && (!features.equals(other.getFeatures())))
      return false;
    return true;
  }// equals

  /** Set the feature set. Overriden from the implementation in
   *  AbstractFeatureBearer because it needs to fire events
   */
  @Override
  public void setFeatures(FeatureMap features) {
    //I need to remove first the old features listener if any
    if (eventHandler != null)
      this.features.removeFeatureMapListener(eventHandler);

    this.features = features;

    //if someone cares about the annotation changes, then we need to
    //track the events from the new feature
    if (annotationListeners != null && ! annotationListeners.isEmpty())
      this.features.addFeatureMapListener(eventHandler);

    //finally say that the annotation features have been updated
    fireAnnotationUpdated(new AnnotationEvent(
                            this,
                            AnnotationEvent.FEATURES_UPDATED));


  }


  /** This verifies if <b>this</b> annotation is compatible with another one.
    * Compatible means that they hit the same possition and the FeatureMap of
    * <b>this</b> is incuded into aAnnot FeatureMap.
    * @param anAnnot a gate Annotation. If anAnnotation is null then false is
    * returned.
    * @return <code>true</code> if aAnnot is compatible with <b>this</b> and
    * <code>false</code> otherwise.
    */
  @Override
  public boolean isCompatible(Annotation anAnnot){
    if (anAnnot == null) return false;
    if (coextensive(anAnnot)){
      if (anAnnot.getFeatures() == null) return true;
      if (anAnnot.getFeatures().subsumes(this.getFeatures()))
        return true;
    }// End if
    return false;
  }//isCompatible

  /** This verifies if <b>this</b> annotation is compatible with another one,
    * given a set with certain keys.
    * In this case, compatible means that they hit the same possition
    * and those keys from <b>this</b>'s FeatureMap intersected with
    * aFeatureNamesSet are incuded together with their values into the aAnnot's
    * FeatureMap.
    * @param anAnnot a gate Annotation. If param is null, it will return false.
    * @param aFeatureNamesSet is a set containing certian key that will be
    * intersected with <b>this</b>'s FeatureMap's keys.If param is null then
    * isCompatible(Annotation) will be called.
    * @return <code>true</code> if aAnnot is compatible with <b>this</b> and
    * <code>false</code> otherwise.
    */
  @Override
  public boolean isCompatible(Annotation anAnnot, Set<? extends Object> aFeatureNamesSet){
    // If the set is null then isCompatible(Annotation) will decide.
    if (aFeatureNamesSet == null) return isCompatible(anAnnot);
    if (anAnnot == null) return false;
    if (coextensive(anAnnot)){
      if (anAnnot.getFeatures() == null) return true;
      if (anAnnot.getFeatures().subsumes(this.getFeatures(),aFeatureNamesSet))
        return true;
    }// End if
    return false;
  }//isCompatible()

  /** This method verifies if two annotation and are partially compatible.
    * Partially compatible means that they overlap and the FeatureMap of
    * <b>this</b> is incuded into FeatureMap of aAnnot.
    * @param anAnnot a gate Annotation.
    * @return <code>true</code> if <b>this</b> is partially compatible with
    * anAnnot and <code>false</code> otherwise.
    */
  @Override
  public boolean isPartiallyCompatible(Annotation anAnnot){
    if (anAnnot == null) return false;
    if (overlaps(anAnnot)){
      if (anAnnot.getFeatures() == null) return true;
      if (anAnnot.getFeatures().subsumes(this.getFeatures()))
        return true;
    }// End if
    return false;
  }//isPartiallyCompatible

  /** This method verifies if two annotation and are partially compatible,
    * given a set with certain keys.
    * In this case, partially compatible means that they overlap
    * and those keys from <b>this</b>'s FeatureMap intersected with
    * aFeatureNamesSet are incuded together with their values into the aAnnot's
    * FeatureMap.
    * @param anAnnot a gate Annotation. If param is null, the method will return
    * false.
    * @param aFeatureNamesSet is a set containing certian key that will be
    * intersected with <b>this</b>'s FeatureMap's keys.If param is null then
    * isPartiallyCompatible(Annotation) will be called.
    * @return <code>true</code> if <b>this</b> is partially compatible with
    * aAnnot and <code>false</code> otherwise.
    */
  @Override
  public boolean isPartiallyCompatible(Annotation anAnnot,Set<? extends Object> aFeatureNamesSet){
    if (aFeatureNamesSet == null) return isPartiallyCompatible(anAnnot);
    if (anAnnot == null) return false;
    if (overlaps(anAnnot)){
      if (anAnnot.getFeatures() == null) return true;
      if (anAnnot.getFeatures().subsumes(this.getFeatures(),aFeatureNamesSet))
        return true;
    }// End if
    return false;
  }//isPartiallyCompatible()

  /**
    *  Two Annotation are coextensive if their offsets are the
    *  same.
    *  @param anAnnot A Gate annotation.
    *  @return <code>true</code> if two annotation hit the same possition and
    *  <code>false</code> otherwise
    */
  @Override
  public boolean coextensive(Annotation anAnnot){
    // If their start offset is not the same then return false
    if((anAnnot.getStartNode() == null) ^ (this.getStartNode() == null))
      return false;

    if(anAnnot.getStartNode() != null){
      if((anAnnot.getStartNode().getOffset() == null) ^
         (this.getStartNode().getOffset() == null))
        return false;
      if(anAnnot.getStartNode().getOffset() != null &&
        (!anAnnot.getStartNode().getOffset().equals(
                            this.getStartNode().getOffset())))
        return false;
    }// End if

    // If their end offset is not the same then return false
    if((anAnnot.getEndNode() == null) ^ (this.getEndNode() == null))
      return false;

    if(anAnnot.getEndNode() != null){
      if((anAnnot.getEndNode().getOffset() == null) ^
         (this.getEndNode().getOffset() == null))
        return false;
      if(anAnnot.getEndNode().getOffset() != null &&
        (!anAnnot.getEndNode().getOffset().equals(
              this.getEndNode().getOffset())))
        return false;
    }// End if

    // If we are here, then the annotations hit the same position.
    return true;
  }//coextensive

  @Override
  public boolean overlaps(Annotation aAnnot){
    if (aAnnot == null) return false;
    if (aAnnot.getStartNode() == null ||
        aAnnot.getEndNode() == null ||
        aAnnot.getStartNode().getOffset() == null ||
        aAnnot.getEndNode().getOffset() == null) return false;

//    if ( (aAnnot.getEndNode().getOffset().longValue() ==
//          aAnnot.getStartNode().getOffset().longValue()) &&
//          this.getStartNode().getOffset().longValue() <=
//          aAnnot.getStartNode().getOffset().longValue() &&
//          aAnnot.getEndNode().getOffset().longValue() <=
//          this.getEndNode().getOffset().longValue()
//       ) return true;


    if ( aAnnot.getEndNode().getOffset().longValue() <=
         this.getStartNode().getOffset().longValue())
      return false;

    if ( aAnnot.getStartNode().getOffset().longValue() >=
         this.getEndNode().getOffset().longValue())
      return false;

    return true;
  }//overlaps

  
  /** This method tells if <b>this</b> annotation's text range is 
   * fully contained within the text annotated by <code>aAnnot</code>'s
   * annotation. 
   * @param aAnnot a gate Annotation.
   * @return <code>true</code> if this annotation is fully contained in the 
   * other one.
   */
 @Override
public boolean withinSpanOf(Annotation aAnnot){
   if (aAnnot == null) return false;
   if (aAnnot.getStartNode() == null ||
       aAnnot.getEndNode() == null ||
       aAnnot.getStartNode().getOffset() == null ||
       aAnnot.getEndNode().getOffset() == null) return false;

   if ( ( aAnnot.getEndNode().getOffset().longValue() >=
          this.getEndNode().getOffset().longValue() ) &&
        ( aAnnot.getStartNode().getOffset().longValue() <= 
          this.getStartNode().getOffset().longValue() ) )
     return true;
   else 
     return false;
 }//withinSpanOf

  
//////////////////THE EVENT HANDLING CODE/////////////////////
//Needed so an annotation set can listen to its annotations//
//and update correctly the database/////////////////////////

  /**
   * The set of listeners of the annotation update events. At present there
   * are two event types supported:
   * <UL>
   *   <LI> ANNOTATION_UPDATED event
   *   <LI> FEATURES_UPDATED event
   * </UL>
   */
  private transient Vector<AnnotationListener> annotationListeners;
  /**
   * The listener for the events coming from the features.
   */
  protected EventsHandler eventHandler;


  /**
   *
   * Removes an annotation listener
   */
  @Override
  public synchronized void removeAnnotationListener(AnnotationListener l) {
    if (annotationListeners != null && annotationListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<AnnotationListener> v = (Vector<AnnotationListener>) annotationListeners.clone();
      v.removeElement(l);
      annotationListeners = v;
    }
  }
  /**
   *
   * Adds an annotation listener
   */
  @Override
  public synchronized void addAnnotationListener(AnnotationListener l) {
    @SuppressWarnings("unchecked")
    Vector<AnnotationListener> v = annotationListeners == null ? new Vector<AnnotationListener>(2) : (Vector<AnnotationListener>) annotationListeners.clone();

    //now check and if this is the first listener added,
    //start listening to all features, so their changes can
    //also be propagated
    if (v.isEmpty()) {
      FeatureMap features = getFeatures();
      if (eventHandler == null)
        eventHandler = new EventsHandler();
      features.addFeatureMapListener(eventHandler);
    }

    if (!v.contains(l)) {
      v.addElement(l);
      annotationListeners = v;
    }
  }
  /**
   *
   * @param e
   */
  protected void fireAnnotationUpdated(AnnotationEvent e) {
    if (annotationListeners != null) {
      Vector<AnnotationListener> listeners = annotationListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        listeners.elementAt(i).annotationUpdated(e);
      }
    }
  }//fireAnnotationUpdated


  /**
   * The id of this annotation (for persitency resons)
   *
   */
  Integer id;
  /**
   * The type of the annotation
   *
   */
  String type;
  /**
   * The features of the annotation are inherited from Abstract feature bearer
   * so no need to define here
   */

  /**
   * The start node
   */
  protected Node start;

  /**
   *  The end node
   */
  protected Node end;
  
  /** @link dependency */
  /*#AnnotationImpl lnkAnnotationImpl;*/

  /**
   * All the events from the features are handled by
   * this inner class.
   */
  class EventsHandler implements gate.event.FeatureMapListener, Serializable {
    @Override
    public void featureMapUpdated(){
      //tell the annotation listeners that my features have been updated
      fireAnnotationUpdated(new AnnotationEvent(
                                  AnnotationImpl.this,
                                  AnnotationEvent.FEATURES_UPDATED));
    }
    static final long serialVersionUID = 2608156420244752907L;
    
  }//inner class EventsHandler


} // class AnnotationImpl
