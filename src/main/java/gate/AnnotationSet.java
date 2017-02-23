/*
 *  AnnotationSet.java
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
 *  $Id: AnnotationSet.java 18392 2014-10-17 13:32:18Z ian_roberts $
 */

package gate;

import gate.event.AnnotationSetListener;
import gate.event.GateListener;
import gate.util.InvalidOffsetException;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * A set of annotations on a document. In addition to the methods
 * provided by {@link SimpleAnnotationSet}, Annotation sets support
 * access to subsets of the annotations in the set by various more
 * complex criteria. Annotation sets are attached to documents - they
 * cannot be constructed directly, but are obtained via the
 * <code>getAnnotations</code> methods of {@link Document}.
 * </p>
 *
 * <p>
 * This interface provides methods to extract subsets of annotations
 * from the current set given various constraints. Note that the
 * annotation sets returned by these <code>get</code> methods are
 * immutable snapshots of the set as it was at the time the method was
 * called. Subsequent changes to the underlying set are not reflected in
 * the subset view.
 * </p>
 *
 * <p>
 * This interface extends {@link java.util.Set}&lt;Annotation&gt;, so
 * can be used anywhere a Java Collections Framework <code>Set</code>
 * or <code>Collection</code> is required.
 * </p>
 */
public interface AnnotationSet extends SimpleAnnotationSet, Serializable {
  /**
   * Create and add an annotation with a pre-existing ID. This method
   * should only be used when you have existing annotations with unique
   * IDs, for example when reading the full contents of an annotation
   * set from some saved representation. In normal use you should use
   * the method
   * {@link SimpleAnnotationSet#add(Long, Long, String, FeatureMap)},
   * which allows the set to assign a unique ID.
   *
   * @param id the ID for the new annotation
   * @param start the start offset for the new annotation
   * @param end the end offset for the new annotation
   * @param type the annotation type
   * @param features the features for the new annotation
   * @throws InvalidOffsetException if the start or end offsets are
   *           <code>null</code>, or if the start offset is less than
   *           0 or the end offset is greater than the length of the
   *           document.
   */
  public void add(Integer id, Long start, Long end, String type,
          FeatureMap features) throws InvalidOffsetException;

  /**
   * <p>
   * Select annotations by type and feature values. This will return an
   * annotation set containing just those annotations of a particular
   * type which have features with specific names and values. (It will
   * also return annotations that have features besides those specified,
   * but it will not return any annotations that do not have all the
   * specified feature-value pairs.)
   * </p>
   *
   * <p>
   * However, if constraints contains a feature whose value is equal to
   * {@link gate.creole.ANNIEConstants#LOOKUP_CLASS_FEATURE_NAME} (which
   * is normally "class"), then GATE will attempt to match that feature
   * using an ontology which it will try to retreive from a feature
   * {@link gate.creole.ANNIEConstants#LOOKUP_ONTOLOGY_FEATURE_NAME} on
   * both the annotation and in <code>constraints</code>. If these do
   * not return identical ontologies, or if either the annotation or
   * constraints does not contain an ontology, then matching will fail,
   * and the annotation will not be added. In summary, this method will
   * not work normally for features with the name "class".
   * </p>
   *
   * @param type The type of the annotations to return.
   * @param constraints A feature map containing all of the feature
   *          value pairs that the annotation must have in order for
   *          them to be returned.
   * @return An annotation set containing only those annotations with
   *         the given name and which have the specified set of
   *         feature-value pairs. If no annotations match the
   *         constraints, an empty set is returned. The returned set is
   *         immutable.
   */
  public AnnotationSet get(String type, FeatureMap constraints);

  /**
   * Select annotations by type and feature names It returns all
   * annotations of the given type that have the given set of features,
   * regardless of their concrete values If the type == null, then
   * select regardless of type
   *
   * @param type the annotation type to return. If <code>null</code>
   *          then all annotation types are searched.
   * @param featureNames the feature names which an annotation must have
   *          to be matched.
   * @return An annotation set containing only those annotations with
   *         the given type and at least the given features. If no
   *         annotations match these constraints, an empty set is
   *         returned. The returned set is immutable.
   */
  public AnnotationSet get(String type, Set<? extends Object> featureNames);

  /**
   * Select annotations by type, features and offset. This method is a
   * combination of {@link #get(Long)} and
   * {@link #get(String, FeatureMap)}, in that it matches annotations
   * by type and feature constraints but considers only those
   * annotations that start as close as possible to the right of the
   * given offset.
   *
   * @param type the annotation type to search for
   * @param constraints the set of features an annotation must have to
   *          be matched
   * @param offset the offset at which to anchor the search.
   * @return An annotation set containing those annotations that match
   *         the constraints, or an empty set if there are no such
   *         annotations. The returned set is immutable.
   */
  public AnnotationSet get(String type, FeatureMap constraints, Long offset);

  /**
   * Select annotations by offset. This returns the set of annotations
   * whose start node is the least such that it is greater than or equal
   * to <code>offset</code>. In other words it finds the first
   * annotation that starts at or after the given offset and returns all
   * annotations which start at the same place.
   *
   * @param offset the offset at which to start the search.
   * @return a set of annotations, all of which start at the same offset
   *         &gt;= <code>offset</code>. The returned set is
   *         immutable.
   */
  public AnnotationSet get(Long offset);

  /**
   * Select annotations by offset. This returns the set of annotations
   * that overlap totaly or partially the interval defined by the two
   * provided offsets, i.e. that start strictly before
   * <code>endOffset</code> and end strictly after
   * <code>startOffset</code>.
   *
   * @param startOffset the start of the interval
   * @param endOffset the end of the interval
   * @return the set of annotations that overlap the given interval, or
   *         an empty set if there are no such annotations. The returned
   *         set is immutable.
   */
  public AnnotationSet get(Long startOffset, Long endOffset);

  /**
   * Select annotations by offset and type. This returns the set of
   * annotations that overlap totally or partially the interval defined
   * by the two provided offsets and are of the given type. This method
   * is effectively a combination of {@link #get(Long, Long)} and
   * {@link SimpleAnnotationSet#get(String)} but may admit more
   * efficient implementation.
   *
   * @param type the annotation type to search for
   * @param startOffset the start of the interval
   * @param endOffset the end of the interval
   * @return the set of annotations of the given type that overlap the
   *         given interval, or an empty set if no such annotations
   *         exist. The returned set is immutable.
   */
  public AnnotationSet get(String type, Long startOffset, Long endOffset);

  /**
   * Select annotations of the given type that completely span the range.
   * Formally, for any annotation a, a will be included in the return
   * set if:
   * <ul>
   * <li>a.getStartNode().getOffset() <= startOffset</li>
   * <li>and</li>
   * <li>a.getEndNode().getOffset() >= endOffset</li>
   *
   * @param neededType Type of annotation to return. If empty, all
   *          annotation types will be returned.
   * @param startOffset the start of the interval
   * @param endOffset the end of the interval
   * @return the set of annotations matching the parameters, or an empty
   *         set if no such annotations exist. The returned set is
   *         immutable.
   */
  public AnnotationSet getCovering(String neededType, Long startOffset, Long endOffset);

  /**
   * Select annotations by offset. This returns the set of annotations
   * that are contained in the interval defined by the two provided
   * offsets. The difference with get(startOffset, endOffset) is that
   * the latter also provides annotations that have a span which covers
   * completely and is bigger than the given one. Here we only get the
   * annotations between the two offsets.  Formally, all annotations
   * are returned whose start position is >= <code>startOffset</code>
   * and whose end position is &lt;= <code>endOffset</code>.
   *
   * @param startOffset the start of the interval, inclusive
   * @param endOffset the end of the interval, inclusive
   * @return the set of annotations from this set contained completely
   *         inside the interval, or an empty set if no such annotations
   *         exist. The returned set is immutable.
   */
  public AnnotationSet getContained(Long startOffset, Long endOffset);

  /**
   * Return a list of annotations sorted by increasing start offset, i.e. in the order
   * they appear in the document. If more than one annotation starts at a specific offset
   * the order of these annotations is unspecified.
   * 
   * @return a list of annotations ordered by increasing start offset.
   */
  public List<Annotation> inDocumentOrder();
  
  
  /**
   * Get the node with the smallest offset
   */
  public Node firstNode();

  /**
   * Get the node with the largest offset
   */
  public Node lastNode();

  /**
   * Get the first node that is relevant for this annotation set and
   * which has the offset larger than the one of the node provided.
   */
  public Node nextNode(Node node);

  public void addAnnotationSetListener(AnnotationSetListener l);

  public void removeAnnotationSetListener(AnnotationSetListener l);

  public void addGateListener(GateListener l);

  public void removeGateListener(GateListener l);

} // interface AnnotationSet
