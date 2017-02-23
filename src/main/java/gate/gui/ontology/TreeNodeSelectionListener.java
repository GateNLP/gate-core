/*
 *  TreeNodeSelectionListener.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: TreeNodeSelectionListener.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Objects wishing to listen to the events fired by the ontology editor
 * when a user changes his/her selection in the ontology tree editor,
 * should implements this interface and get registered by using the
 * repsective method in the OntologyEditor.
 * 
 * @author niraj
 * 
 */
public interface TreeNodeSelectionListener {
  /**
   * This method is invoked by the ontology editor whenever a user
   * changes his/her selection in the ontology tree editor.
   * 
   * @param arraylist
   */
  public void selectionChanged(ArrayList<DefaultMutableTreeNode> arraylist);
}
