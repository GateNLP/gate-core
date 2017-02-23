/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 12/12/2000
 *
 *  $Id: AnnotationSetEvent.java 17080 2013-11-12 19:29:34Z markagreenwood $
 */

package gate.event;

import gate.relations.Relation;
import gate.relations.RelationSet;

/**
 * This class models events fired by an {@link gate.relations.RelationSet}.
 */
public class RelationSetEvent extends GateEvent{

  private static final long serialVersionUID = 6115461542702259816L;

  /**Event type used for situations when a new annotation has been added*/
  public static final int RELATION_ADDED = 901;

  /**Event type used for situations when an annotation has been removed*/
  public static final int RELATION_REMOVED = 902;

  /**
   * Constructor.
   * @param source the {@link gate.relations.RelationSet} that fired the event
   * @param type the type of the event
   * @param relation the Relation that was added or removed.
   */
  public RelationSetEvent(RelationSet source,
                            int type,
                            Relation relation) {
    super(source, type);
    this.relation = relation;
  }
  
  /**
   * Gets the document that has had an annotation added or removed.
   * @return a {@link gate.Document}
   */
  public gate.Document getSourceDocument() {
    return ((RelationSet)source).getAnnotationSet().getDocument();
  }

  /**
   * Gets the relation that has been added or removed
   * @return a {@link gate.relations.Relation}
   */
  public Relation getRelation() {
    return relation;
  }

  private Relation relation;
}