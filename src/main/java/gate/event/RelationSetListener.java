package gate.event;

import java.util.EventListener;

/**
 * A listener for events fired by an {@link gate.relations.RelationSet}
 * ({@link gate.event.RelationSetEvent})
 */
public interface RelationSetListener extends EventListener {

  /**Called when a new {@link gate.relations.Relation} has been added*/
  public void relationAdded(RelationSetEvent e);

  /**Called when an {@link gate.relations.Relation} has been removed*/
  public void relationRemoved(RelationSetEvent e);

}