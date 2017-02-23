/*
 *  SimpleAnnotationSet.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 23/Jul/2004
 *
 *  $Id: SimpleAnnotationSet.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate;

import gate.relations.RelationSet;
import gate.util.InvalidOffsetException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * A set of annotations on a document. Simple annotation sets support
 * creation of new annotations and access to subsets of the annotations
 * in the set by annotation type. Annotation sets are attached to
 * documents - they cannot be constructed directly, but are obtained via
 * the <code>getAnnotations</code> methods of {@link Document}.
 * </p>
 * 
 * <p>
 * This interface provides methods to get all annotations of a
 * particular type or set of types from the current set. Note that the
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
public interface SimpleAnnotationSet extends Set<Annotation>, Cloneable,
                                    Serializable {
  /**
   * Create and add an annotation with pre-existing nodes, and return
   * its id. The nodes provided must already exist in this set, i.e.
   * they must have been obtained from an existing annotation which is
   * in this set.
   * 
   * @param start the start node for the new annotation
   * @param end the end node for the new annotation
   * @param type the annotation type
   * @param features the features for the new annotation
   * @return the newly generated annotation ID, which will be distinct
   *         from all other annotations in this set.
   */
  public Integer add(Node start, Node end, String type, FeatureMap features);

  /**
   * Create and add an annotation and return its id.
   * 
   * @param start the start offset for the new annotation
   * @param end the end offset for the new annotation
   * @param type the annotation type
   * @param features the features for the new annotation
   * @return the newly generated annotation ID, which will be distinct
   *         from all other annotations in this set.
   * @throws InvalidOffsetException if the start or end offsets are
   *           <code>null</code>, or if the start offset is less than
   *           0 or the end offset is greater than the length of the
   *           document.
   */
  public Integer add(Long start, Long end, String type, FeatureMap features)
          throws InvalidOffsetException;

  /**
   * Add an existing annotation, which should be an annotation on this
   * set's document.
   * 
   * @param a the annotation to add
   * @return <code>true</code> if the set was modified by this
   *         operation, <code>false</code> otherwise.
   */
  @Override
  public boolean add(Annotation a);

  /**
   * Get an iterator for this set
   */
  @Override
  public Iterator<Annotation> iterator();

  /**
   * Get the size of (i.e. number of annotations in) this set.
   */
  @Override
  public int size();

  /**
   * Remove an element from this set.
   * 
   * @param o the element to remove
   * @return <code>true</code> if the set was modified by this
   *         operation, <code>false</code> otherwise.
   */
  @Override
  public boolean remove(Object o);

  /**
   * Find annotations by id
   * 
   * @param id the ID to search for
   * @return the annotation from this set with this ID, or
   *         <code>null</code> if there is no annotation with this ID
   *         in this set.
   */
  public Annotation get(Integer id);

  /**
   * Get a copy of this annotation set.
   * 
   * @return a snapshot copy of all annotations in this set. The
   *         returned annotation set is immutable.
   */
  public AnnotationSet get();

  /**
   * Select annotations by type.
   * 
   * @param type the annotation type to search for.
   * @return a snapshot copy of all annotations in this set that have
   *         the requested type. If there are no annotations of the
   *         requested type in this set, an empty set is returned. The
   *         returned set is immutable.
   */
  public AnnotationSet get(String type);

  /**
   * Select annotations by a set of types.
   * 
   * @param types the set of annotation types to search for.
   * @return a snapshot copy of all annotations in this set that have
   *         one of the requested types. If there are no annotations of
   *         the requested types in this set, an empty set is returned.
   *         The returned set is immutable.
   */
  public AnnotationSet get(Set<String> types);

  /**
   * Get the name of this set.
   * 
   * @return the name of this annotation set, or <code>null</code> if
   *         this set does not have a name (i.e. it is the default
   *         annotation set for a document, or it is a subset view of
   *         another annotation set).
   */
  public String getName();

  /**
   * Get a set of java.lang.String objects representing all the
   * annotation types present in this annotation set.
   */
  public Set<String> getAllTypes();

  /**
   * Get the document this set is attached to. Every annotation set must
   * be attached to a document.
   */
  public Document getDocument();
  
  public RelationSet getRelations();

} // interface SimpleAnnotationSet
