/*
 *  AnnotationSetImpl.java
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
 *  Developer notes:
 *  ---
 *
 *  the addToIndex... and indexBy... methods could be refactored as I'm
 *  sure they can be made simpler
 *
 *  every set to which annotation will be added has to have positional
 *  indexing, so that we can find or create the nodes on the new annotations
 *
 *  note that annotations added anywhere other than sets that are
 *  stored on the document will not get stored anywhere...
 *
 *  nodes aren't doing anything useful now. needs some interface that allows
 *  their creation, defaulting to no coterminous duplicates, but allowing such
 *  if required
 *
 *  $Id: AnnotationSetImpl.java 19658 2016-10-10 06:46:13Z markagreenwood $
 */
package gate.annotation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.Node;
import gate.corpora.DocumentImpl;
import gate.event.AnnotationSetEvent;
import gate.event.AnnotationSetListener;
import gate.event.GateEvent;
import gate.event.GateListener;
import gate.relations.RelationSet;
import gate.util.InvalidOffsetException;
import gate.util.RBTreeMap;

/**
 * Implementation of AnnotationSet. Has a number of indices, all bar one of
 * which are null by default and are only constructed when asked for. Has lots
 * of get methods with various selection criteria; these return views into the
 * set, which are nonetheless valid sets in their own right (but will not
 * necesarily be fully indexed). Has a name, which is null by default; clients
 * of Document can request named AnnotationSets if they so desire. Has a
 * reference to the Document it is attached to. Contrary to Collections
 * convention, there is no no-arg constructor, as this would leave the set in an
 * inconsistent state.
 * <P>
 * There are four indices: annotation by id, annotations by type, annotations by
 * start node and nodes by offset. The last two jointly provide positional
 * indexing; construction of these is triggered by indexByStart(), or by calling
 * a get method that selects on offset. The type index is triggered by
 * indexByType(), or calling a get method that selects on type. The id index is
 * always present.
 * <P>
 * NOTE: equality and hashCode of this implementation is exclusively based on the annotations
 * which appear in the set (if any). The document the set comes from, the name of the set or
 * the relations stored in that set are not taken into account for equality or hashSet!!
 *
 * 
 */
public class AnnotationSetImpl extends AbstractSet<Annotation> implements
                                                              AnnotationSet {
  /** Freeze the serialization UID. */
  static final long serialVersionUID = 1479426765310434166L;
  /** The name of this set */
  String name = null;
  /** The document this set belongs to */
  DocumentImpl doc;
  /** Maps annotation ids (Integers) to Annotations */
  transient protected HashMap<Integer, Annotation> annotsById;
  /** Maps offsets (Longs) to nodes */
  transient RBTreeMap<Long,Node> nodesByOffset = null;
  /**
   * This field is used temporarily during serialisation to store all the
   * annotations that need to be saved. At all other times, this will be null;
   */
  private Annotation[] annotations;
  /** Maps annotation types (Strings) to AnnotationSets */
  transient Map<String, AnnotationSet> annotsByType = null;
  /**
   * Maps node ids (Integers) to Annotations or a Collection of Annotations that
   * start from that node
   */
  transient Map<Integer, Object> annotsByStartNode;
  protected transient Vector<AnnotationSetListener> annotationSetListeners;
  private transient Vector<GateListener> gateListeners;

  /**
   * A caching value that greatly improves the performance of get
   * methods that have a defined beginning and end. By tracking the
   * maximum length that an annotation can be, we know the maximum
   * amount of nodes outside of a specified range that must be checked
   * to see if an annotation starting at one of those nodes crosses into
   * the range. This mechanism is not perfect because we do not check if
   * we have to decrease it if an annotation is removed from the set.
   * However, usually annotations are removed because they are about to
   * be replaced with another one that is &gt;= to the length of the one
   * being replaced, so this isn't a big deal. At worst, it means that
   * the get methods simply checks a few more start positions than it
   * needs to.
   */
  protected transient Long longestAnnot = 0l;
  
  protected RelationSet relations = null;

  // Empty AnnotationSet to be returned instead of null
   //public final static AnnotationSet emptyAS;

   //static {
   //emptyAnnotationSet = new ImmutableAnnotationSetImpl(null,null);
   //}

  /** Construction from Document. */
  public AnnotationSetImpl(Document doc) {
    annotsById = new HashMap<Integer, Annotation>();
    this.doc = (DocumentImpl)doc;
  } // construction from document

  /** Construction from Document and name. */
  public AnnotationSetImpl(Document doc, String name) {
    this(doc);
    this.name = name;
  } // construction from document and name

  /** Construction from an existing AnnotationSet */
  @SuppressWarnings("unchecked")
  public AnnotationSetImpl(AnnotationSet c) throws ClassCastException {
    this(c.getDocument(), c.getName());
    // the original annotationset is of the same implementation
    if(c instanceof AnnotationSetImpl) {
      AnnotationSetImpl theC = (AnnotationSetImpl)c;
      annotsById.putAll(theC.annotsById);
      if(theC.annotsByStartNode != null) {
        annotsByStartNode = new HashMap<Integer, Object>(Gate.HASH_STH_SIZE);
        annotsByStartNode.putAll(theC.annotsByStartNode);
      }
      if(theC.annotsByType != null) {
        annotsByType = new HashMap<String, AnnotationSet>(Gate.HASH_STH_SIZE);
        annotsByType.putAll(theC.annotsByType);
      }
      if(theC.nodesByOffset != null) {
        nodesByOffset = (RBTreeMap<Long,Node>)theC.nodesByOffset.clone();
      }
    }
    // the implementation is not the default one
    // let's add the annotations one by one
    else {
      Iterator<Annotation> iterannots = c.iterator();
      while(iterannots.hasNext()) {
        add(iterannots.next());
      }
    }
  }
  
  @Override
  public void clear() {
    // while nullifying the indexes does clear the set it doesn't fire the
    // appropriate events so use the Iterator based clear implementation in
    // AbstractSet.clear() first and then reset the indexes
    super.clear();
    
    //reset all the indexes to be sure everything has been cleared correctly
    annotsById = new HashMap<Integer, Annotation>();
    nodesByOffset = null;
    annotsByStartNode = null;
    annotsByType = null;
    longestAnnot = 0l;
  }

  /**
   * This inner class serves as the return value from the iterator() method.
   */
  class AnnotationSetIterator implements Iterator<Annotation> {
    private Iterator<Annotation> iter;
    protected Annotation lastNext = null;

    AnnotationSetIterator() {
      iter = annotsById.values().iterator();
    }

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public Annotation next() {
      return (lastNext = iter.next());
    }

    @Override
    public void remove() {
      // this takes care of the ID index
      iter.remove();

      // what if lastNext is null
      if(lastNext == null) return;

      // remove from type index
      removeFromTypeIndex(lastNext);
      // remove from offset indices
      removeFromOffsetIndex(lastNext);
      // that's the second way of removing annotations from a set
      // apart from calling remove() on the set itself
      fireAnnotationRemoved(new AnnotationSetEvent(AnnotationSetImpl.this,
              AnnotationSetEvent.ANNOTATION_REMOVED, getDocument(),
              lastNext));
    } // remove()
  }; // AnnotationSetIterator

  /** Get an iterator for this set */
  @Override
  public Iterator<Annotation> iterator() {
    return new AnnotationSetIterator();
  }

  /** Remove an element from this set. */
  @Override
  public boolean remove(Object o) throws ClassCastException {
    Annotation a = (Annotation)o;
    boolean wasPresent = removeFromIdIndex(a);
    if(wasPresent) {
      removeFromTypeIndex(a);
      removeFromOffsetIndex(a);
    }
    // fire the event
    fireAnnotationRemoved(new AnnotationSetEvent(AnnotationSetImpl.this,
            AnnotationSetEvent.ANNOTATION_REMOVED, getDocument(), a));
    return wasPresent;
  } // remove(o)

  /** Remove from the ID index. */
  protected boolean removeFromIdIndex(Annotation a) {
    if(annotsById.remove(a.getId()) == null) return false;
    return true;
  } // removeFromIdIndex(a)

  /** Remove from the type index. */
  protected void removeFromTypeIndex(Annotation a) {
    if(annotsByType != null) {
      AnnotationSet sameType = annotsByType.get(a.getType());
      if(sameType != null) sameType.remove(a);
      if(sameType != null && sameType.isEmpty()) // none left of this type
        annotsByType.remove(a.getType());
    }
  } // removeFromTypeIndex(a)

  /** Remove from the offset indices. */
  protected void removeFromOffsetIndex(Annotation a) {

    if (nodesByOffset != null) {
      // if there is a nodesByOffset map then we need to make sure it is
      // correctly updated and redundant nodes removed, otherwise methods that
      // use it can report incorrect information
      
      // get all the annotations (ignoring the one we are removing that start at the same node
      Set<Annotation> tmp = new HashSet<Annotation>();
      tmp.addAll(gate.Utils.getAnnotationsAtOffset(this,a.getStartNode().getOffset()));
      tmp.remove(a);

      if (tmp.size() == 0) {
        // if there aren't any then we may need to remove the node, but let's
        // double check there aren't any annotations that end where this one
        // starts
        tmp.addAll(gate.Utils.getAnnotationsEndingAtOffset(this, a.getStartNode().getOffset()));
        tmp.remove(a);

        if (tmp.size() == 0) {
          // nope, there are no annotations that start or end where the one we
          // are removing starts so remove the node from the map
          nodesByOffset.remove(a.getStartNode().getOffset());
        }
      }

      // repeat the logic above but for the node at the end of the annotation we
      // want to remove from the set
      tmp = new HashSet<Annotation>();
      tmp.addAll(gate.Utils.getAnnotationsAtOffset(this,a.getEndNode().getOffset()));
      tmp.remove(a);

      if (tmp.size() == 0) {
        tmp.addAll(gate.Utils.getAnnotationsEndingAtOffset(this, a.getEndNode().getOffset()));
        tmp.remove(a);

        if (tmp.size() == 0) {
          nodesByOffset.remove(a.getEndNode().getOffset());
        }
      }
    }

    if(annotsByStartNode != null) {
      Integer id = a.getStartNode().getId();
      // might be an annotation or an annotationset
      Object objectAtNode = annotsByStartNode.get(id);
      if(objectAtNode instanceof Annotation) {
        annotsByStartNode.remove(id); // no annotations start here any
        // more
        return;
      }
      // otherwise it is a Collection
      @SuppressWarnings("unchecked")
      Collection<Annotation> starterAnnots = (Collection<Annotation>)objectAtNode;
      starterAnnots.remove(a);
      // if there is only one annotation left
      // we discard the set and put directly the annotation
      if(starterAnnots.size() == 1)
        annotsByStartNode.put(id, starterAnnots.iterator().next());
    }
  } // removeFromOffsetIndex(a)

  /** The size of this set */
  @Override
  public int size() {
    return annotsById.size();
  }

  /** Find annotations by id */
  @Override
  public Annotation get(Integer id) {
    return annotsById.get(id);
  } // get(id)

  /**
   * Get all annotations.
   *
   * @return an ImmutableAnnotationSet, empty or not
   */
  @Override
  public AnnotationSet get() {
    if (annotsById.isEmpty()) return emptyAS();
    return new ImmutableAnnotationSetImpl(doc, annotsById.values());
  } // get()

  /**
   * Select annotations by type
   *
   * @return an ImmutableAnnotationSet
   */
  @Override
  public AnnotationSet get(String type) {
    if(annotsByType == null) indexByType();
    AnnotationSet byType = annotsByType.get(type);
    if (byType==null)return emptyAS();
    // convert the mutable AS into an immutable one
    return byType.get();
  } // get(type)

  /**
   * Select annotations by a set of types. Expects a Set of String.
   *
   * @return an ImmutableAnnotationSet
   */
  @Override
  public AnnotationSet get(Set<String> types) throws ClassCastException {
    if(annotsByType == null) indexByType();
    Iterator<String> iter = types.iterator();
    List<Annotation> annotations = new ArrayList<Annotation>();
    while(iter.hasNext()) {
      String type = iter.next();
      AnnotationSet as = annotsByType.get(type);
      if(as != null) {
        Iterator<Annotation> iterAnnot = as.iterator();
        while(iterAnnot.hasNext()) {
          annotations.add(iterAnnot.next());
        }
      }
    } // while
    if(annotations.isEmpty()) return emptyAS();
    return new ImmutableAnnotationSetImpl(doc, annotations);
  } // get(types)

  /**
   * Select annotations by type and features
   *
   * This will return an annotation set containing just those annotations of a
   * particular type (i.e. with a particular name) and which have features with
   * specific names and values. (It will also return annotations that have
   * features besides those specified, but it will not return any annotations
   * that do not have all the specified feature-value pairs.)
   *
   * However, if constraints contains a feature whose value is equal to
   * gate.creole.ANNIEConstants.LOOKUP_CLASS_FEATURE_NAME (which is normally
   * "class"), then GATE will attempt to match that feature using an ontology
   * which it will try to retreive from a feature on the both the annotation and
   * in constraints. If these do not return identical ontologies, or if either
   * the annotation or constraints does not contain an ontology, then matching
   * will fail, and the annotation will not be added. In summary, this method
   * will not work normally for features with the name "class".
   *
   * @param type
   *          The name of the annotations to return.
   * @param constraints
   *          A feature map containing all of the feature value pairs that the
   *          annotation must have in order for them to be returned.
   * @return An annotation set containing only those annotations with the given
   *         name and which have the specified set of feature-value pairs.
   */
  @Override
  public AnnotationSet get(String type, FeatureMap constraints) {
    if(annotsByType == null) indexByType();
    AnnotationSet typeSet = get(type);
    if(typeSet == null) return null;
    Iterator<Annotation> iter = typeSet.iterator();
    List<Annotation> annotationsToAdd = new ArrayList<Annotation>();
    while(iter.hasNext()) {
      Annotation a = iter.next();
      // we check for matching constraints by simple equality. a
      // feature map satisfies the constraints if it contains all the
      // key/value pairs from the constraints map
      // if
      // (a.getFeatures().entrySet().containsAll(constraints.entrySet()))
      if(a.getFeatures().subsumes(constraints)) annotationsToAdd.add(a);
    } // while
    if(annotationsToAdd.isEmpty()) return emptyAS();
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // get(type, constraints)

  /** Select annotations by type and feature names */
  @Override
  public AnnotationSet get(String type, Set<? extends Object> featureNames) {
    if(annotsByType == null) indexByType();
    AnnotationSet typeSet = null;
    if(type != null) {
      // if a type is provided, try finding annotations of this type
      typeSet = get(type);
      // if none exist, then return coz nothing left to do
      if(typeSet == null) return null;
    }
    List<Annotation> annotationsToAdd = new ArrayList<Annotation>();
    Iterator<Annotation> iter = null;
    if(type != null)
      iter = typeSet.iterator();
    else iter = annotsById.values().iterator();
    while(iter.hasNext()) {
      Annotation a = iter.next();
      // we check for matching constraints by simple equality. a
      // feature map satisfies the constraints if it contains all the
      // key/value pairs from the constraints map
      if(a.getFeatures().keySet().containsAll(featureNames))
        annotationsToAdd.add(a);
    } // while
    if(annotationsToAdd.isEmpty()) return emptyAS();
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // get(type, featureNames)

  /**
   * Select annotations by offset. This returns the set of annotations whose
   * start node is the least such that it is less than or equal to offset. If a
   * positional index doesn't exist it is created. If there are no nodes at or
   * beyond the offset param then it will return an empty annotationset.
   */
  @Override
  public AnnotationSet get(Long offset) {
    if(annotsByStartNode == null) indexByStartOffset();
    // find the next node at or after offset; get the annots starting
    // there
    Node nextNode = nodesByOffset.getNextOf(offset);
    if(nextNode == null) // no nodes at or beyond this offset
      return emptyAS();
    Collection<Annotation> annotationsToAdd = getAnnotsByStartNode(nextNode
            .getId());
    // skip all the nodes that have no starting annotations
    while(annotationsToAdd == null) {
      nextNode = nodesByOffset.getNextOf(nextNode.getOffset()
              .longValue() + 1);
      if (nextNode==null) return emptyAS();
      annotationsToAdd = getAnnotsByStartNode(nextNode.getId());
    }
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  }

  
  /**
   * Select annotations by offset. This returns the set of annotations that
   * start exactly at the given offset. If a
   * positional index doesn't exist it is created. If there are no annotations
   * at the given offset then an empty annotation set is returned.
   * 
   * @param offset The starting offset for which to return annotations 
   * @return a ImmutableAnnotationSetImpl containing all annotations starting at the given
   *   offset (possibly empty).
   */
  public AnnotationSet getStartingAt(long offset) {
    if(annotsByStartNode == null) indexByStartOffset();
    Node node = nodesByOffset.get(offset);
    if(node == null) { // no nodes at or beyond this offset
      return emptyAS();
    }
    return new ImmutableAnnotationSetImpl(doc, getAnnotsByStartNode(node.getId()));
  }
  
  /**
   * Return a list of annotations sorted by increasing start offset, i.e. in the order
   * they appear in the document. If more than one annotation starts at a specific offset
   * the order of these annotations is unspecified.
   * 
   * @return a list of annotations ordered by increasing start offset. If a positional
   * index does not exist, it is created.
   */
  @Override
  public List<Annotation> inDocumentOrder() {
    if(annotsByStartNode == null) indexByStartOffset();
    Collection<Node> values = nodesByOffset.values();
    List<Annotation> result = new ArrayList<Annotation>();
    for(Node nodeObj : values) {
      Collection<Annotation> anns = getAnnotsByStartNode(nodeObj.getId());
      if(anns != null) {
        result.addAll(anns);
      }
    }
    return result;
  }
  
  /**
   * Select annotations by offset. This returns the set of annotations that
   * overlap totaly or partially with the interval defined by the two provided
   * offsets.The result will include all the annotations that either:
   * <ul>
   * <li>start before the start offset and end strictly after it</li>
   * <li>OR</li>
   * <li>start at a position between the start and the end offsets</li>
   * </ul>
   *
   * @return an ImmutableAnnotationSet
   */
  @Override
  public AnnotationSet get(Long startOffset, Long endOffset) {
    return get(null, startOffset, endOffset);
  } // get(startOfset, endOffset)

  /**
   * Select annotations by offset. This returns the set of annotations that
   * overlap strictly with the interval defined by the two provided offsets.The
   * result will include all the annotations that start at the start offset and
   * end strictly at the end offset
   */
  public AnnotationSet getStrict(Long startOffset, Long endOffset) {
    // the result will include all the annotations that
    // start at the start offset and end strictly at the end offset
    if(annotsByStartNode == null) indexByStartOffset();
    List<Annotation> annotationsToAdd = null;
    Iterator<Annotation> annotsIter;
    Node currentNode;
    Annotation currentAnnot;
    // find all the annots that start at the start offset
    currentNode = nodesByOffset.get(startOffset);
    if(currentNode != null) {
      Collection<Annotation> objFromPoint = getAnnotsByStartNode(currentNode
              .getId());
      if(objFromPoint != null) {
        annotsIter = objFromPoint.iterator();
        while(annotsIter.hasNext()) {
          currentAnnot = annotsIter.next();
          if(currentAnnot.getEndNode().getOffset().compareTo(endOffset) == 0) {
            if(annotationsToAdd == null) annotationsToAdd = new ArrayList<Annotation>();
            annotationsToAdd.add(currentAnnot);
          } // if
        } // while
      } // if
    } // if
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // getStrict(startOfset, endOffset)

  /**
   * Select annotations by offset. This returns the set of annotations of the
   * given type that overlap totaly or partially with the interval defined by
   * the two provided offsets.The result will include all the annotations that
   * either:
   * <ul>
   * <li>start before the start offset and end strictly after it</li>
   * <li>OR</li>
   * <li>start at a position between the start and the end offsets</li>
   * </ul>
   */
  @Override
  public AnnotationSet get(String neededType, Long startOffset, Long endOffset) {
    if(annotsByStartNode == null) indexByStartOffset();
    List<Annotation> annotationsToAdd = new ArrayList<Annotation>();
    Iterator<Node> nodesIter;
    Iterator<Annotation> annotsIter;
    Node currentNode;
    Annotation currentAnnot;
    boolean checkType = StringUtils.isNotBlank(neededType);
    // find all the annots that start strictly before the start offset
    // and end
    // strictly after it
    Long searchStart = (startOffset - longestAnnot);
    if (searchStart < 0) searchStart = 0l;
    //nodesIter = nodesByOffset.headMap(startOffset).values().iterator();
    nodesIter = nodesByOffset.subMap(searchStart, startOffset).values().iterator();
    while(nodesIter.hasNext()) {
      currentNode = nodesIter.next();
      Collection<Annotation> objFromPoint = getAnnotsByStartNode(currentNode
              .getId());
      if(objFromPoint == null) continue;
      annotsIter = objFromPoint.iterator();
      while(annotsIter.hasNext()) {
        currentAnnot = annotsIter.next();
        //if neededType is set, make sure this is the right type
        if (checkType && !currentAnnot.getType().equals(neededType))
          continue;
        if(currentAnnot.getEndNode().getOffset().compareTo(startOffset) > 0) {
          annotationsToAdd.add(currentAnnot);
        } // if
      } // while
    }
    // find all the annots that start at or after the start offset but
    // before the end offset
    nodesIter = nodesByOffset.subMap(startOffset, endOffset).values()
            .iterator();
    while(nodesIter.hasNext()) {
      currentNode = nodesIter.next();
      Collection<Annotation> objFromPoint = getAnnotsByStartNode(currentNode
              .getId());
      if(objFromPoint == null) continue;
      //if no specific type requested, add all of the annots
      if (!checkType)
        annotationsToAdd.addAll(objFromPoint);
      else {
        //check the type of each annot
        annotsIter = objFromPoint.iterator();
        while(annotsIter.hasNext()) {
          currentAnnot = annotsIter.next();
          if (currentAnnot.getType().equals(neededType))
            annotationsToAdd.add(currentAnnot);
        } // while
      }
    }
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // get(type, startOfset, endOffset)

  /**
   * Select annotations of the given type that completely span the range.
   * Formally, for any annotation a, a will be included in the return
   * set if:
   * <ul>
   * <li>a.getStartNode().getOffset() &lt;= startOffset</li>
   * <li>and</li>
   * <li>a.getEndNode().getOffset() &gt;= endOffset</li>
   * </ul>
   *
   * @param neededType Type of annotation to return. If empty, all
   *          annotation types will be returned.
   * @return annotations of the given type that completely span the range.
   */
  @Override
  public AnnotationSet getCovering(String neededType, Long startOffset, Long endOffset) {
    //check the range
    if(endOffset < startOffset) return emptyAS();
    //ensure index
    if(annotsByStartNode == null) indexByStartOffset();
    //if the requested range is longer than the longest annotation in this set, 
    //then there can be no annotations covering the range
    // so we return an empty set.
    if(endOffset - startOffset > longestAnnot) return emptyAS();
    
    List<Annotation> annotationsToAdd = new ArrayList<Annotation>();
    Iterator<Node> nodesIter;
    Iterator<Annotation> annotsIter;
    Node currentNode;
    Annotation currentAnnot;
    boolean checkType = StringUtils.isNotBlank(neededType);
    // find all the annots with startNode <= startOffset.  Need the + 1 because
    // headMap returns strictly less than.
    // the length of the longest annot from the endOffset since we know that nothing
    // that starts earlier will be long enough to cover the entire span.
    Long searchStart = ((endOffset - 1) - longestAnnot);
    if (searchStart < 0) searchStart = 0l;
    //nodesIter = nodesByOffset.headMap(startOffset + 1).values().iterator();
    nodesIter = nodesByOffset.subMap(searchStart, startOffset + 1).values().iterator();

    while(nodesIter.hasNext()) {
      currentNode = nodesIter.next();
      Collection<Annotation> objFromPoint = getAnnotsByStartNode(currentNode
              .getId());
      if(objFromPoint == null) continue;
      annotsIter = objFromPoint.iterator();
      while(annotsIter.hasNext()) {
        currentAnnot = annotsIter.next();
        //if neededType is set, make sure this is the right type
        if (checkType && !currentAnnot.getType().equals(neededType))
          continue;
        //check that the annot ends at or after the endOffset
        if(currentAnnot.getEndNode().getOffset().compareTo(endOffset) >= 0)
          annotationsToAdd.add(currentAnnot);
      } // while
    }
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // get(type, startOfset, endOffset)

  /** Select annotations by type, features and offset */
  @Override
  public AnnotationSet get(String type, FeatureMap constraints, Long offset) {
    // select by offset
    AnnotationSet nextAnnots = get(offset);
    
    // select by type and constraints from the next annots
    return nextAnnots.get(type, constraints);
  } // get(type, constraints, offset)

  /**
   * Select annotations contained within an interval, i.e.
   * those annotations whose start position is
   * &gt;= <code>startOffset</code> and whose end position is &lt;= 
   * <code>endOffset</code>.
   */
  @Override
  public AnnotationSet getContained(Long startOffset, Long endOffset) {
    // the result will include all the annotations that either:
    // start at a position between the start and end before the end
    // offsets
    //check the range
    if(endOffset < startOffset) return emptyAS();
    //ensure index
    if(annotsByStartNode == null) indexByStartOffset();
    List<Annotation> annotationsToAdd = null;
    Iterator<Node> nodesIter;
    Node currentNode;
    Iterator<Annotation> annotIter;
    // find all the annots that start at or after the start offset but
    // strictly
    // before the end offset
    nodesIter = nodesByOffset.subMap(startOffset, endOffset).values()
            .iterator();
    while(nodesIter.hasNext()) {
      currentNode = nodesIter.next();
      Collection<Annotation> objFromPoint = getAnnotsByStartNode(currentNode
              .getId());
      if(objFromPoint == null) continue;
      // loop through the annotations and find only those that
      // also end before endOffset
      annotIter = objFromPoint.iterator();
      while(annotIter.hasNext()) {
        Annotation annot = annotIter.next();
        if(annot.getEndNode().getOffset().compareTo(endOffset) <= 0) {
          if(annotationsToAdd == null) annotationsToAdd = new ArrayList<Annotation>();
          annotationsToAdd.add(annot);
        }
      }
    }
    return new ImmutableAnnotationSetImpl(doc, annotationsToAdd);
  } // get(startOfset, endOffset)

  /** Get the node with the smallest offset */
  @Override
  public Node firstNode() {
    indexByStartOffset();
    if(nodesByOffset.isEmpty())
      return null;
    else return nodesByOffset.get(nodesByOffset.firstKey());
  } // firstNode

  /** Get the node with the largest offset */
  @Override
  public Node lastNode() {
    indexByStartOffset();
    if(nodesByOffset.isEmpty())
      return null;
    else return nodesByOffset.get(nodesByOffset.lastKey());
  } // lastNode

  /**
   * Get the first node that is relevant for this annotation set and which has
   * the offset larger than the one of the node provided.
   */
  @Override
  public Node nextNode(Node node) {
    indexByStartOffset();
    return nodesByOffset.getNextOf(node.getOffset().longValue() + 1);
  }

  protected static AnnotationFactory annFactory;

  /**
   * Set the annotation factory used to create annotation objects. The default
   * factory is {@link DefaultAnnotationFactory}.
   */
  public static void setAnnotationFactory(AnnotationFactory newFactory) {
    annFactory = newFactory;
  }

  static {
    // set the default factory to always create AnnotationImpl objects
    setAnnotationFactory(new DefaultAnnotationFactory());
  }

  /**
   * Create and add an annotation with pre-existing nodes, and return its id.
   * <B>Note that only Nodes retrieved from the same annotation set should be used
   * to create a new annotation using this method. Using Nodes from other annotation
   * sets may lead to undefined behaviour. If in any doubt use the Long based add
   * method instead of this one.</B>
   */
  @Override
  public Integer add(Node start, Node end, String type, FeatureMap features) {
    // the id of the new annotation
    Integer id = doc.getNextAnnotationId();
    // construct an annotation
    annFactory.createAnnotationInSet(this, id, start, end, type, features);

    return id;
  } // add(Node, Node, String, FeatureMap)

  /** Add an existing annotation. Returns true when the set is modified. */
  @Override
  public boolean add(Annotation a) throws ClassCastException {
    Annotation oldValue = annotsById.put(a.getId(), a);
    
    if (oldValue != null) {
    	if (annotsByType != null) removeFromTypeIndex(oldValue);
    	if (annotsByStartNode != null) removeFromOffsetIndex(oldValue);
    }
    
    if(annotsByType != null) addToTypeIndex(a);
    if(annotsByStartNode != null) addToStartOffsetIndex(a);
    AnnotationSetEvent evt = new AnnotationSetEvent(this,
            AnnotationSetEvent.ANNOTATION_ADDED, doc, a);
    fireAnnotationAdded(evt);
    fireGateEvent(evt);
    return oldValue != a;
  } // add(o)

  /**
   * Adds multiple annotations to this set in one go. All the objects in the
   * provided collection should be of {@link gate.Annotation} type, otherwise a
   * ClassCastException will be thrown. The provided annotations will be used to
   * create new annotations using the appropriate add() methods from this set.
   * The new annotations will have different IDs from the old ones (which is
   * required in order to preserve the uniqueness of IDs inside an annotation
   * set).
   *
   * @param c
   *          a collection of annotations
   * @return <tt>true</tt> if the set has been modified as a result of this
   *         call.
   */
  @Override
  public boolean addAll(Collection<? extends Annotation> c) {
    Iterator<? extends Annotation> annIter = c.iterator();
    boolean changed = false;
    while(annIter.hasNext()) {
      Annotation a = annIter.next();
      try {
        add(a.getStartNode().getOffset(), a.getEndNode().getOffset(), a
                .getType(), a.getFeatures());
        changed = true;
      } catch(InvalidOffsetException ioe) {
        throw new IllegalArgumentException(ioe.toString());
      }
    }
    return changed;
  }

  /**
   * Adds multiple annotations to this set in one go. All the objects in the
   * provided collection should be of {@link gate.Annotation} type, otherwise a
   * ClassCastException will be thrown. This method does not create copies of
   * the annotations like addAll() does but simply adds the new annotations to
   * the set. It is intended to be used solely by annotation sets in order to
   * construct the results for various get(...) methods.
   *
   * @param c
   *          a collection of annotations
   * @return <tt>true</tt> if the set has been modified as a result of this
   *         call.
   */
  protected boolean addAllKeepIDs(Collection<? extends Annotation> c) {
    Iterator<? extends Annotation> annIter = c.iterator();
    boolean changed = false;
    while(annIter.hasNext()) {
      Annotation a = annIter.next();
      changed |= add(a);
    }
    return changed;
  }

  /** Returns the nodes corresponding to the Longs. The Nodes are created if
   * they don't exist.
   **/
  private final Node[] getNodes(Long start, Long end) throws InvalidOffsetException
  {
    // are the offsets valid?
    if(!doc.isValidOffsetRange(start, end)) {
      throw new InvalidOffsetException("Offsets [" + start + ":" + end +
              "] not valid for this document of size " + doc.getContent().size());
    }
    // the set has to be indexed by position in order to add, as we need
    // to find out if nodes need creating or if they exist already
    if(nodesByOffset == null) {
      indexByStartOffset();
    }
    // find existing nodes if appropriate nodes don't already exist,
    // create them
    Node startNode = nodesByOffset.get(start);
    if(startNode == null)
      startNode = new NodeImpl(doc.getNextNodeId(), start);

    Node endNode = null;
    if(start.equals(end)){
      endNode = startNode;
      return new Node[]{startNode,endNode};
    }

    endNode = nodesByOffset.get(end);
    if(endNode == null)
      endNode = new NodeImpl(doc.getNextNodeId(), end);

    return new Node[]{startNode,endNode};
  }


  /** Create and add an annotation and return its id */
  @Override
  public Integer add(Long start, Long end, String type, FeatureMap features)
          throws InvalidOffsetException {
    Node[] nodes = getNodes(start,end);
    // delegate to the method that adds annotations with existing nodes
    return add(nodes[0], nodes[1], type, features);
  } // add(start, end, type, features)

  /**
   * Create and add an annotation from database read data In this case the id is
   * already known being previously fetched from the database
   */
  @Override
  public void add(Integer id, Long start, Long end, String type,
          FeatureMap features) throws InvalidOffsetException {
    Node[] nodes = getNodes(start,end);
    // construct an annotation
    annFactory.createAnnotationInSet(this, id, nodes[0], nodes[1], type,
            features);
    
    //try to ensure that if someone adds an annotation directly by ID
    //the other methods don't trample all over it later
    if (id >= doc.peakAtNextAnnotationId()) {
      doc.setNextAnnotationId(id+1);
    }
  } // add(id, start, end, type, features)

  /** Construct the positional index. */
  protected void indexByType() {
    if(annotsByType != null) return;
    annotsByType = new HashMap<String, AnnotationSet>(Gate.HASH_STH_SIZE);
    Iterator<Annotation> annotIter = annotsById.values().iterator();
    while(annotIter.hasNext())
      addToTypeIndex(annotIter.next());
  } // indexByType()

  /** Construct the positional indices for annotation start */
  protected void indexByStartOffset() {
    if(annotsByStartNode != null) return;
    if(nodesByOffset == null) nodesByOffset = new RBTreeMap<Long,Node>();
    annotsByStartNode = new HashMap<Integer, Object>(annotsById.size());
    Iterator<Annotation> annotIter = annotsById.values().iterator();
    while(annotIter.hasNext())
      addToStartOffsetIndex(annotIter.next());
  } // indexByStartOffset()

  /**
   * Add an annotation to the type index. Does nothing if the index doesn't
   * exist.
   */
  void addToTypeIndex(Annotation a) {
    if(annotsByType == null) return;
    String type = a.getType();
    AnnotationSet sameType = annotsByType.get(type);
    if(sameType == null) {
      sameType = new AnnotationSetImpl(doc);
      annotsByType.put(type, sameType);
    }
    sameType.add(a);
  } // addToTypeIndex(a)

  /**
   * Add an annotation to the start offset index. Does nothing if the index
   * doesn't exist.
   */
  @SuppressWarnings("unchecked")
  void addToStartOffsetIndex(Annotation a) {
    Node startNode = a.getStartNode();
    Node endNode = a.getEndNode();
    Long start = startNode.getOffset();
    Long end = endNode.getOffset();
    // add a's nodes to the offset index
    if(nodesByOffset != null) {
      nodesByOffset.put(start, startNode);
      nodesByOffset.put(end, endNode);
    }

    //add marking for longest annot
    long annotLength = end - start;
    if (annotLength > longestAnnot)
        longestAnnot = annotLength;

    // if there's no appropriate index give up
    if(annotsByStartNode == null) return;
    // get the annotations that start at the same node, or create new
    // set
    Object thisNodeObject = annotsByStartNode.get(startNode.getId());
    if(thisNodeObject == null) {
      // put directly the annotation
      annotsByStartNode.put(startNode.getId(), a);
    } else { // already something there : a single Annotation or a
      // Collection
      Set<Annotation> newCollection = null;
      if(thisNodeObject instanceof Annotation) {
        // we need to create a set - we have more than one annotation
        // starting
        // at this Node
        if(thisNodeObject.equals(a)) return;
        newCollection = new HashSet<Annotation>(3);
        newCollection.add((Annotation)thisNodeObject);
        annotsByStartNode.put(startNode.getId(), newCollection);
      } else newCollection = (Set<Annotation>)thisNodeObject;
      // get the existing set
      // add the new node annotation
      newCollection.add(a);
    }
  } // addToStartOffsetIndex(a)

  /**
   * Propagate document content changes to this AnnotationSet. 
   * 
   * This method is called for all annotation sets of a document from 
   * DocumentImpl.edit to adapt the annotations to the text changes made through
   * the edit. The behaviour of this method is influenced by the configuration
   * setting {@link gate.GateConstants#DOCEDIT_INSERT_PREPEND GateConstants.DOCEDIT_INSERT_PREPEND }: 
   * annotations immediately 
   * ending before or starting after the point of insertion will either become
   * part of the inserted text or not. Currently it works like this:
   * <ul>
   * <li>PREPEND=true: annotation before will become part, annotation after not
   * <li>PREPEND=false: annotation before will not become part, annotation after 
   * will become part
   * </UL>
   * NOTE 1 (JP): There is another setting
   * {@link gate.GateConstants#DOCEDIT_INSERT_APPEND GateConstants.DOCEDIT_INSERT_APPEND }
   * but 
   * this setting does currently not influence the behaviour of this method. 
   * The behaviour of this method may change in the future so that 
   * DOCEDIT_INSERT_APPEND is considered separately and in addition to 
   * DOCEDIT_INSERT_PREPEND so that it can be controlled independently if 
   * the annotation before and/or after an insertion point gets expanded or not.
   * <p>
   * NOTE 2: This method has, unfortunately, to be
   * public, to allow DocumentImpls to get at it. Oh for a "friend" declaration.
   * Doesn't throw InvalidOffsetException as DocumentImpl is the only client,
   * and that checks the offsets before calling this method.
   */
  public void edit(Long start, Long end, DocumentContent replacement) {
    // make sure we have the indices computed
    indexByStartOffset();
    if(end.compareTo(start) > 0) {
      // get the nodes that need to be processed (the nodes internal to
      // the
      // removed section plus the marginal ones
      List<Node> affectedNodes = new ArrayList<Node>(nodesByOffset.subMap(start,
              end.longValue() + 1).values());
      // if we have more than 1 node we need to delete all apart from
      // the first
      // and move the annotations so that they refer to the one we keep
      // (the
      // first)
      NodeImpl firstNode = null;
      if(!affectedNodes.isEmpty()) {
        firstNode = (NodeImpl)affectedNodes.get(0);
        List<Annotation> startingAnnotations = new ArrayList<Annotation>();
        List<Annotation> endingAnnotations = new ArrayList<Annotation>();
        // now we need to find all the annotations
        // ending in the zone
        List<Node> beforeNodes = new ArrayList<Node>(nodesByOffset.subMap(0L,
                end.longValue() + 1).values());
        Iterator<Node> beforeNodesIter = beforeNodes.iterator();
        while(beforeNodesIter.hasNext()) {
          Node currentNode = beforeNodesIter.next();
          Collection<Annotation> annotations = getAnnotsByStartNode(currentNode.getId());
          if(annotations == null) continue;
          // iterates on the annotations in this set
          Iterator<Annotation> localIterator = annotations.iterator();
          while(localIterator.hasNext()) {
            Annotation annotation = localIterator.next();
            long offsetEndAnnotation = annotation.getEndNode().getOffset()
                    .longValue();
            // we are interested only in the annotations ending
            // inside the zone
            if(offsetEndAnnotation >= start.longValue()
                    && offsetEndAnnotation <= end.longValue())
              endingAnnotations.add(annotation);
          }
        }
        for(int i = 1; i < affectedNodes.size(); i++) {
          Node aNode = affectedNodes.get(i);
          Collection<Annotation> annSet = getAnnotsByStartNode(aNode.getId());
          if(annSet != null) {
            startingAnnotations.addAll(annSet);
          }
          // remove the node
          // nodesByOffset.remove(aNode.getOffset());
          // annotsByStartNode.remove(aNode);
        }
        // modify the annotations so they point to the saved node
        Iterator<Annotation> annIter = startingAnnotations.iterator();
        while(annIter.hasNext()) {
          AnnotationImpl anAnnot = (AnnotationImpl)annIter.next();
          anAnnot.start = firstNode;
          // remove the modified annotation if it has just become
          // zero-length
          if(anAnnot.start == anAnnot.end) {
            remove(anAnnot);
          } else {
            addToStartOffsetIndex(anAnnot);
          }
        }
        annIter = endingAnnotations.iterator();
        while(annIter.hasNext()) {
          AnnotationImpl anAnnot = (AnnotationImpl)annIter.next();
          anAnnot.end = firstNode;
          // remove the modified annotation if it has just become
          // zero-length
          if(anAnnot.start == anAnnot.end) {
            remove(anAnnot);
          }
        }
        // remove the unused nodes inside the area
        for(int i = 1; i < affectedNodes.size(); i++) {
          Node aNode = affectedNodes.get(i);
          nodesByOffset.remove(aNode.getOffset());
          annotsByStartNode.remove(aNode.getId());
        }
        // repair the first node
        // remove from offset index
        nodesByOffset.remove(firstNode.getOffset());
        // change the offset for the saved node
        firstNode.setOffset(start);
        // add back to the offset index
        nodesByOffset.put(firstNode.getOffset(), firstNode);
      }
    }
    // now handle the insert and/or update the rest of the nodes'
    // position
    // get the user selected behaviour (defaults to append)
    boolean shouldPrepend = Gate.getUserConfig().getBoolean(
            GateConstants.DOCEDIT_INSERT_PREPEND).booleanValue();
    long s = start.longValue(), e = end.longValue();
    long rlen = // length of the replacement value
    ((replacement == null) ? 0 : replacement.size().longValue());
    // update the offsets and the index by offset for the rest of the
    // nodes
    List<Node> nodesAfterReplacement = new ArrayList<Node>(nodesByOffset.tailMap(start)
            .values());
    // remove from the index by offset
    Iterator<Node> nodesAfterReplacementIter = nodesAfterReplacement.iterator();
    while(nodesAfterReplacementIter.hasNext()) {
      NodeImpl n = (NodeImpl)nodesAfterReplacementIter.next();
      nodesByOffset.remove(n.getOffset());
    }
    // change the offsets
    nodesAfterReplacementIter = nodesAfterReplacement.iterator();
    while(nodesAfterReplacementIter.hasNext()) {
      NodeImpl n = (NodeImpl)nodesAfterReplacementIter.next();
      long oldOffset = n.getOffset().longValue();
      // by default we move all nodes back
      long newOffset = oldOffset - (e - s) + rlen;
      // for the first node we need behave differently
      if(oldOffset == s) {
        // the first offset never moves back
        if(newOffset < s) newOffset = s;
        // if we're prepending we don't move forward
        if(shouldPrepend) newOffset = s;
      }
      n.setOffset(newOffset);
    }
    // add back to the index by offset with the new offsets
    nodesAfterReplacementIter = nodesAfterReplacement.iterator();
    while(nodesAfterReplacementIter.hasNext()) {
      NodeImpl n = (NodeImpl)nodesAfterReplacementIter.next();
      nodesByOffset.put(n.getOffset(), n);
    }
    // //rebuild the indices with the new offsets
    // nodesByOffset = null;
    // annotsByStartNode = null;
    // annotsByEndNode = null;
    // indexByStartOffset();
    // indexByEndOffset();
  } // edit(start,end,replacement)

  /** Get the name of this set. */
  @Override
  public String getName() {
    return name;
  }

  /** Get the document this set is attached to. */
  @Override
  public Document getDocument() {
    return doc;
  }

  /**
   * Get a set of java.lang.String objects representing all the annotation types
   * present in this annotation set.
   */
  @Override
  public Set<String> getAllTypes() {
    indexByType();
    return Collections.unmodifiableSet(annotsByType.keySet());
  }

  /**
   * Returns a set of annotations starting at that position This intermediate
   * method is used to simplify the code as the values of the annotsByStartNode
   * hashmap can be Annotations or a Collection of Annotations. Returns null if
   * there are no Annotations at that position
   */
  @SuppressWarnings("unchecked")
  private final Collection<Annotation> getAnnotsByStartNode(Integer id) {
    Object objFromPoint = annotsByStartNode.get(id);
    if(objFromPoint == null) return null;
    if(objFromPoint instanceof Annotation) {
      List<Annotation> al = new ArrayList<Annotation>(2);
      al.add((Annotation)objFromPoint);
      return al;
    }
    // it is already a collection
    // return it
    return (Collection<Annotation>)objFromPoint;
  }

  /**
   *
   * @return a clone of this set.
   * @throws CloneNotSupportedException
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public synchronized void removeAnnotationSetListener(AnnotationSetListener l) {
    if(annotationSetListeners != null && annotationSetListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<AnnotationSetListener> v = (Vector<AnnotationSetListener>)annotationSetListeners.clone();
      v.removeElement(l);
      annotationSetListeners = v;
    }
  }

  @Override
  public synchronized void addAnnotationSetListener(AnnotationSetListener l) {
    @SuppressWarnings("unchecked")
    Vector<AnnotationSetListener> v = annotationSetListeners == null
            ? new Vector<AnnotationSetListener>(2)
            : (Vector<AnnotationSetListener>)annotationSetListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      annotationSetListeners = v;
    }
  }

  protected void fireAnnotationAdded(AnnotationSetEvent e) {
    if(annotationSetListeners != null) {
      Vector<AnnotationSetListener> listeners = annotationSetListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).annotationAdded(e);
      }
    }
  }

  protected void fireAnnotationRemoved(AnnotationSetEvent e) {
    if(annotationSetListeners != null) {
      Vector<AnnotationSetListener> listeners = annotationSetListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).annotationRemoved(e);
      }
    }
  }

  @Override
  public synchronized void removeGateListener(GateListener l) {
    if(gateListeners != null && gateListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<GateListener> v = (Vector<GateListener>)gateListeners.clone();
      v.removeElement(l);
      gateListeners = v;
    }
  }

  @Override
  public synchronized void addGateListener(GateListener l) {
    @SuppressWarnings("unchecked")
    Vector<GateListener> v = gateListeners == null ? new Vector<GateListener>(2) : (Vector<GateListener>)gateListeners
            .clone();
    if(!v.contains(l)) {
      v.addElement(l);
      gateListeners = v;
    }
  }

  protected void fireGateEvent(GateEvent e) {
    if(gateListeners != null) {
      Vector<GateListener> listeners = gateListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).processGateEvent(e);
      }
    }
  }

  // how to serialize this object?
  // there is no need to serialize the indices
  // so it's probably as fast to just recreate them
  // if required
  private void writeObject(java.io.ObjectOutputStream out) throws IOException {
    ObjectOutputStream.PutField pf = out.putFields();
    pf.put("name", this.name);
    pf.put("doc", this.doc);
    //
    // out.writeObject(this.name);
    // out.writeObject(this.doc);
    // save only the annotations
    // in an array that will prevent the need for casting
    // when deserializing
    annotations = new Annotation[this.annotsById.size()];
    annotations = this.annotsById.values().toArray(annotations);
    // out.writeObject(annotations);
    pf.put("annotations", this.annotations);    
    pf.put("relations", this.relations);
    
    
    out.writeFields();
    annotations = null;
    boolean isIndexedByType = (this.annotsByType != null);
    boolean isIndexedByStartNode = (this.annotsByStartNode != null);
    out.writeBoolean(isIndexedByType);
    out.writeBoolean(isIndexedByStartNode);
  }

  private void readObject(java.io.ObjectInputStream in) throws IOException,
          ClassNotFoundException {
    this.longestAnnot = 0l;
    ObjectInputStream.GetField gf = in.readFields();
    this.name = (String)gf.get("name", null);
    this.doc = (DocumentImpl)gf.get("doc", null);
    boolean isIndexedByType = false;
    boolean isIndexedByStartNode = false;
    this.annotations = (Annotation[])gf.get("annotations", null);
    
    if(this.annotations == null) {
      // old style serialised version
      @SuppressWarnings("unchecked")
      Map<Integer, Annotation> annotsByIdMap = (Map<Integer, Annotation>)gf
              .get("annotsById", null);
      if(annotsByIdMap == null)
        throw new IOException(
                "Invalid serialised data: neither annotations array or map by id"
                        + " are present.");
      annotations = annotsByIdMap.values().toArray(new Annotation[]{});
    } else {
      // new style serialised version
      isIndexedByType = in.readBoolean();
      isIndexedByStartNode = in.readBoolean();
    }
    // this.name = (String)in.readObject();
    // this.doc = (DocumentImpl)in.readObject();
    // Annotation[] annotations = (Annotation[])in.readObject();
    // do we need to create the indices?
    // boolean isIndexedByType = in.readBoolean();
    // boolean isIndexedByStartNode = in.readBoolean();
    this.annotsById = new HashMap<Integer, Annotation>(annotations.length);
    // rebuilds the indices if required
    if(isIndexedByType) {
      annotsByType = new HashMap<String, AnnotationSet>(Gate.HASH_STH_SIZE);
    }
    if(isIndexedByStartNode) {
      nodesByOffset = new RBTreeMap<Long,Node>();
      annotsByStartNode = new HashMap<Integer, Object>(annotations.length);
    }
    // add all the annotations one by one
    for(int i = 0; i < annotations.length; i++) {
      add(annotations[i]);
    }
    
    this.relations = (RelationSet)gf.get("relations", null);
    
    annotations = null;
  }
  
  @Override
  public RelationSet getRelations() {
    if (relations == null) {
      relations = new RelationSet(this);
    }
    return relations;
  }
  
  // utility method that replaces the former static singleton member ImmutableAnnotationSet(null,null).
  // We should not give back annotation sets which have a null document, so instead we return
  // as an empty annotation set one that does not have annotations, but points to the same document
  // as the one it was created from. 
  protected AnnotationSet emptyAS() {
    return new ImmutableAnnotationSetImpl(doc, null);
  }
  
  
} // AnnotationSetImpl
