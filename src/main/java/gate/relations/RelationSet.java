/*
 *  RelationData.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 27 Feb 2012
 *
 *  $Id: RelationSet.java 18589 2015-02-26 14:15:49Z markagreenwood $
 */
package gate.relations;

import gate.Annotation;
import gate.AnnotationSet;
import gate.corpora.DocumentImpl;
import gate.event.AnnotationSetEvent;
import gate.event.AnnotationSetListener;
import gate.event.RelationSetEvent;
import gate.event.RelationSetListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;

/**
 * Utility class for managing a set of GATE relations (usually each
 * annotation set of a document will have one set of associated
 * relations).
 */
public class RelationSet implements Serializable, AnnotationSetListener,
                        Set<Relation> {

  private static final long serialVersionUID = 8552798130184595465L;

  /**
   * Annotation ID used when calling {@link #getRelations(int...)} for
   * positions with no restrictions.
   */
  public static final int ANY = -1;

  /**
   * Index for relations by type.
   */
  protected Map<String, BitSet> indexByType;

  /**
   * Index for relations by id.
   */
  protected Map<Integer, Relation> indexById;

  /**
   * Indexes for relations by member. Each element in the list refers to
   * a given position in the members array: the element at position zero
   * refers to the first member of all relations.
   * 
   * The element at position <code>pos</code> is a map from annotation
   * ID (representing a relation member) to a {@link BitSet} indicating
   * which of the relation indexes correspond to
   * relations that contain the given annotation (i.e. member) on the
   * position <code>pos</code>.
   */
  protected List<Map<Integer, BitSet>> indexesByMember;

  /**
   * The {@link AnnotationSet} this set of relations relates to. The
   * assumption (which is actively enforced) is that all members of this
   * RelationSet will be either {@link Annotation} instances from this
   * {@link AnnotationSet} or other {@link Relation} instances within
   * this set.
   */
  protected AnnotationSet annSet;

  private Vector<RelationSetListener> listeners = null;

  /**
   * The max ID of any relation within this set which is needed for some
   * of the index access operations
   */
  private int maxID = 0;
  
  /**
   * Use only for serialization
   */
  private Relation[] relations;

  /**
   * The {@link AnnotationSet} which this instance belongs to.
   * 
   * @return the AnnotationSet which this instance belongs to.
   */
  public AnnotationSet getAnnotationSet() {
    return annSet;
  }

  /**
   * An unmodifiable view of the contents of this RelationSet.
   * 
   * @return an unmodifiable view of the contents of this RelationSet.
   */
  public Collection<Relation> get() {
    return Collections.unmodifiableCollection(indexById.values());
  }

  /**
   * You should never create a RelationSet directly, instead get if via
   * the AnnotationSet
   */
  public RelationSet(AnnotationSet annSet) {
    this.annSet = annSet;
    indexByType = new HashMap<String, BitSet>();
    indexesByMember = new ArrayList<Map<Integer, BitSet>>();
    indexById = new HashMap<Integer, Relation>();

    annSet.addAnnotationSetListener(this);
  }

  /**
   * Empties the relation set
   */
  @Override
  public void clear() {

    // clearing the indexes won't fire the events so we fire the events
    // first and then clear the indexes, hopefully we won't end up with
    // any weird side effects from this but I can't think of a
    // safer/better way of doing it other than calling delete on each
    // relation which would be horribly inefficient
    for(Relation r : indexById.values()) {
      fireRelationRemoved(new RelationSetEvent(this,
              RelationSetEvent.RELATION_REMOVED, r));
    }

    indexByType.clear();
    indexesByMember.clear();
    indexById.clear();
  }

  /**
   * The number of relations in this set.
   * 
   * @return the number of relations in this set.
   */
  @Override
  public int size() {
    return indexById.size();
  }

  /**
   * Creates a new {@link Relation} and adds it to this set. Uses the
   * default relation implementation at {@link SimpleRelation}.
   * 
   * @param type the type for the new relation.
   * @param members the annotation IDs for the annotations that are
   *          members in this relation.
   * @return the newly created {@link Relation} instance.
   */
  public Relation addRelation(String type, int... members)
          throws IllegalArgumentException {

    if(members.length < 2)
      throw new IllegalArgumentException(
              "A relation needs to have atleast two members");

    for(int member : members) {
      if(!indexById.containsKey(member) && annSet.get(member) == null)
        throw new IllegalArgumentException(
                "Member must be from within this annotation set");
    }

    if(type == null || type.trim().equals(""))
      throw new IllegalArgumentException("A relation must have a type");

    // now we have sanity checked the params create the relation
    Relation rel =
            new SimpleRelation(
                    ((DocumentImpl)annSet.getDocument()).getNextAnnotationId(),
                    type, members);

    // add the relation to the set
    add(rel);

    // return the relation to the calling method
    return rel;
  }

  /**
   * Adds an externally-created {@link Relation} instance.
   * 
   * @param rel the {@link Relation} to be added.
   * @return true if the {@link Relation} was added to the set, false otherwise
   */
  @Override
  public boolean add(Relation rel) {

    // keep a track of the max ID we now of to support the index access
    maxID = Math.max(maxID, rel.getId());

    /** index by ID **/
    indexById.put(rel.getId(), rel);

    /** index by type **/
    BitSet sameType = indexByType.get(rel.getType());
    if(sameType == null) {
      sameType = new BitSet(rel.getId());
      indexByType.put(rel.getType(), sameType);
    }
    sameType.set(rel.getId());

    /** index by member **/
    // widen the index by member list, if needed
    for(int i = indexesByMember.size(); i < rel.getMembers().length; i++) {
      indexesByMember.add(new HashMap<Integer, BitSet>());
    }

    for(int memeberPos = 0; memeberPos < rel.getMembers().length; memeberPos++) {
      int member = rel.getMembers()[memeberPos];
      Map<Integer, BitSet> indexByMember = indexesByMember.get(memeberPos);
      BitSet sameMember = indexByMember.get(member);
      if(sameMember == null) {
        sameMember = new BitSet(maxID);
        indexByMember.put(member, sameMember);
      }
      sameMember.set(rel.getId());
    }

    // notify anyone who cares that a new relation has been added
    fireRelationAdded(new RelationSetEvent(this,
            RelationSetEvent.RELATION_ADDED, rel));

    return true;
  }

  /**
   * Returns the maximum arity for any relation in this
   * {@link RelationSet}.
   * 
   * @return an int value.
   */
  public int getMaximumArity() {
    return indexesByMember.size();
  }

  /**
   * Finds relations based on their type.
   * 
   * @param type the type of relation being sought.
   * @return the list of all relations in this {@link RelationSet} that
   *         have the required type.
   */
  public Collection<Relation> getRelations(String type) {
    List<Relation> res = new ArrayList<Relation>();
    BitSet rels = indexByType.get(type);
    if(rels != null) {
      for(int relPos = 0; relPos <= maxID; relPos++) {
        if(rels.get(relPos)) res.add(indexById.get(relPos));
      }
    }
    return res;
  }

  public Relation get(Integer id) {
    return indexById.get(id);
  }

  /**
   * Finds relations based on their members.
   * 
   * @param members an array containing annotation IDs. If a constraint
   *          is not required for a given member position, then the
   *          {@link #ANY}. value should be used.
   * @return all the relations that have the given annotation IDs
   *         (members) on the specified positions.
   */
  public Collection<Relation> getRelations(int... members) {
    // get the lists of relations for each member
    return getRelations(null, members);
  }

  public Collection<Relation> getRelations(String type, int... members) {
    // get the lists of relations for each member
    BitSet[] postingLists =
            new BitSet[getMaximumArity() + (type != null ? 1 : 0)];
    for(int i = 0; i < postingLists.length; i++) {
      if(i < members.length && members[i] >= 0) {
        postingLists[i] = indexesByMember.get(i).get(members[i]);
      } else {
        postingLists[i] = null;
      }

    }

    if(type != null) {
      postingLists[postingLists.length - 1] = indexByType.get(type);
    }

    return intersection(postingLists);
  }

  /**
   * Deletes the specified relation.
   * 
   * @param relation the relation to be deleted.
   * @return <code>true</code> if the given relation was deleted, or
   *         <code>false</code> if it was not found.
   */
  public boolean deleteRelation(Relation relation) {

    // don't do anything if this relation isn't part of this set
    if(!indexById.containsValue(relation)) return false;

    // find all relations which include the annotation and remove them
    for(Relation r : getReferencing(relation.getId())) {
      deleteRelation(r);
    }

    // delete this relation from the type index
    indexByType.get(relation.getType()).clear(relation.getId());

    // delete this relation from the ID index
    indexById.remove(relation.getId());

    // delete from the member index
    for(int memeberPos = 0; memeberPos < relation.getMembers().length; memeberPos++) {
      int member = relation.getMembers()[memeberPos];

      Map<Integer, BitSet> indexByMember = indexesByMember.get(memeberPos);
      BitSet sameMember = indexByMember.get(member);
      sameMember.clear(relation.getId());
    }

    // notify anyone who cares enough to listen that we have deleted a
    // relation
    fireRelationRemoved(new RelationSetEvent(this,
            RelationSetEvent.RELATION_REMOVED, relation));
    return true;

  }

  /**
   * Returns a collection of all {@link Relation} instances within this
   * set which include the specified {@link Annotation} or
   * {@link Relation}
   * 
   * @param id the ID of the {@link Annotation} or {@link Relation} to
   *          look for
   * @return a set of all {@link Relation} instances within this set
   *         which include the specified id
   */
  public Collection<Relation> getReferencing(Integer id) {

    Set<Relation> relations = new HashSet<Relation>();
    for(int pos = 0; pos < getMaximumArity(); pos++) {
      int[] constraint = new int[pos + 1];
      for(int i = 0; i < pos; i++)
        constraint[i] = RelationSet.ANY;
      constraint[pos] = id;
      relations.addAll(getRelations(null, constraint));
    }

    return relations;
  }

  /**
   * Calculates the intersection of a set of lists containing relation
   * IDs
   * 
   * @param indexLists the list to be intersected.
   * @return the list of relations contained in all the supplied index
   *         lists.
   */
  protected Collection<Relation> intersection(BitSet... indexLists) {

    Set<Relation> res = new HashSet<Relation>();

    BitSet relIds = new BitSet(maxID + 1);
    relIds.set(0, maxID + 1);

    boolean found = false;

    for(BitSet aList : indexLists) {
      if(aList != null) {
        found = true;
        relIds.and(aList);

        // if there is no intersection then return the empty list
        if(relIds.isEmpty()) return res;
      }
    }

    if(!found) return res;

    for(int relIdx = 0; relIdx < (maxID + 1); relIdx++) {
      if(relIds.get(relIdx)) {
        res.add(indexById.get(relIdx));
      }
    }
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder str = new StringBuilder();
    str.append("[");
    boolean first = true;
    for(Relation r : indexById.values()) {
      if(first) {
        first = false;
      } else {
        str.append("; ");
      }
      String relStr = r.toString();
      relStr = relStr.replaceAll(";", Matcher.quoteReplacement("\\;"));
      if(!r.getClass().equals(SimpleRelation.class)) {
        relStr = "(" + r.getClass().getName() + ")" + relStr;
      }
      str.append(relStr);

    }
    str.append("]");
    return str.toString();
  }

  @Override
  public void annotationAdded(AnnotationSetEvent e) {
    // we don't care about annotations being added so we do nothing
  }

  @Override
  public void annotationRemoved(AnnotationSetEvent e) {

    Annotation a = e.getAnnotation();

    // find all relations which include the annotation and remove them
    for(Relation r : getReferencing(a.getId())) {
      deleteRelation(r);
    }
  }

  public synchronized void removeRelationSetListener(RelationSetListener l) {
    if(listeners != null && listeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<RelationSetListener> v =
              (Vector<RelationSetListener>)listeners.clone();
      v.removeElement(l);
      listeners = v;
    }
  }

  public synchronized void addRelationSetListener(RelationSetListener l) {
    @SuppressWarnings("unchecked")
    Vector<RelationSetListener> v =
            listeners == null
                    ? new Vector<RelationSetListener>(2)
                    : (Vector<RelationSetListener>)listeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      listeners = v;
    }
  }

  protected void fireRelationAdded(RelationSetEvent e) {
    if(listeners != null) {
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).relationAdded(e);
      }
    }
  }

  protected void fireRelationRemoved(RelationSetEvent e) {
    if(listeners != null) {
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).relationRemoved(e);
      }
    }
  }

  @Override
  public boolean addAll(Collection<? extends Relation> relations) {
    boolean modified = false;

    for(Relation r : relations) {
      modified |= add(r);
    }

    return modified;
  }

  @Override
  public boolean contains(Object obj) {
    return indexById.containsValue(obj);
  }

  @Override
  public boolean containsAll(Collection<?> relations) {
    boolean all = true;

    for(Object r : relations) {
      all &= contains(r);
    }

    return all;
  }

  @Override
  public boolean isEmpty() {
    return indexById.isEmpty();
  }

  @Override
  public Iterator<Relation> iterator() {

    final Set<Relation> copy = new HashSet<Relation>(indexById.values());

    return new Iterator<Relation>() {
      private Relation nextElement, currentElement;

      private boolean hasNext;

      private Iterator<Relation> iterator = copy.iterator();

      {
        nextMatch();
      }

      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public Relation next() {
        if(!hasNext) {
          throw new NoSuchElementException();
        }

        return (currentElement = nextMatch());
      }

      private Relation nextMatch() {
        Relation oldMatch = nextElement;

        while(iterator.hasNext()) {
          Relation o = iterator.next();

          if(indexById.containsValue(o)) {
            hasNext = true;
            nextElement = o;

            return oldMatch;
          }
        }

        hasNext = false;

        return oldMatch;
      }

      @Override
      public void remove() {
        if(currentElement != null) deleteRelation(currentElement);
      }

    };
  }

  @Override
  public boolean remove(Object obj) {
    if(!(obj instanceof Relation)) return false;
    return deleteRelation((Relation)obj);
  }

  @Override
  public boolean removeAll(Collection<?> relations) {
    boolean modified = false;

    for(Object r : relations) {
      if(r instanceof Relation) {
        modified |= deleteRelation((Relation)r);
      }
    }

    return modified;
  }

  @Override
  public boolean retainAll(Collection<?> relations) {
    boolean modified = false;

    Iterator<Relation> it = iterator();
    while(it.hasNext()) {
      Relation r = it.next();

      if(!relations.contains(r)) {
        //deleteRelation(r);
        it.remove();
        modified = true;
      }
    }

    return modified;
  }

  @Override
  public Object[] toArray() {
    return indexById.values().toArray();
  }

  @Override
  public <T> T[] toArray(T[] store) {
    return indexById.values().toArray(store);
  }
  
  private void writeObject(java.io.ObjectOutputStream out) throws IOException {
    ObjectOutputStream.PutField pf = out.putFields();
    
    pf.put("annSet", annSet);
    pf.put("relations", toArray(new Relation[size()]));
    out.writeFields();
  }
  
  private void readObject(java.io.ObjectInputStream in) throws IOException,
          ClassNotFoundException {
    ObjectInputStream.GetField gf = in.readFields();
    
    annSet = (AnnotationSet)gf.get("annSet", null);
    relations = (Relation[])gf.get("relations", null);
    
    indexByType = new HashMap<String, BitSet>();
    indexesByMember = new ArrayList<Map<Integer, BitSet>>();
    indexById = new HashMap<Integer, Relation>();

    annSet.addAnnotationSetListener(this);
    
    if (relations != null) {
      for (Relation r : relations) {
        add(r);
      }      
    }
    
    relations = null;
  }
}
