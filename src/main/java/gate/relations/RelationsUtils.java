/*
 *  RelationsUtils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 5 Mar 2012
 *
 *  $Id: RelationsUtils.java 15540 2012-03-07 14:22:16Z valyt $
 */
package gate.relations;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Utilities for working with relations.
 */
public class RelationsUtils {
  
  /**
   * Computes the transitive closure for symmetric transitive relations (such as
   * co-reference). Given a relation name and a seed annotation ID, this method 
   * returns the IDs for all annotations that are:
   * <ul>
   *   <li>the seed annotation</li>
   *   <li>in the given relation (regardless of position) with another 
   *   annotation from this set</li>
   * </ul>
   * 
   * @param relSet the {@link RelationSet} used as context.
   * @param relationName the relation to be used for computing the closure.
   * @param annotationId the ID for the seed annotation.
   * @return an array containing the IDs of all the annotations in the closure.
   */
  public static int[] transitiveClosure(RelationSet relSet,  
      String relationName, int annotationId) {
    List<Relation> relations = new ArrayList<Relation>();
    for(int pos = 0; pos < relSet.getMaximumArity(); pos++) {
      int[] constraint = new int[pos + 1];
      for(int i = 0; i < pos; i++) constraint[i] = RelationSet.ANY;
      constraint[pos] = annotationId;
      relations.addAll(relSet.getRelations(relationName, constraint));
    }
    
    SortedSet<Integer> closure = new TreeSet<Integer>();
    closure.add(annotationId);
    for(Relation rel : relations) {
      for(int annId : rel.getMembers()) closure.add(annId);
    }
    int[] res = new int[closure.size()];
    int i = 0;
    for(int annId : closure) res[i++] = annId;
    return res;
  }
  
}
