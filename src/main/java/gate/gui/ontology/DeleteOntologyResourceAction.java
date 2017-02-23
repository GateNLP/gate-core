/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: DeleteOntologyResourceAction.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.OClass;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.OResource;
import gate.creole.ontology.OURI;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.RDFProperty;
import gate.gui.MainFrame;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Action to delete a resource from ontology.
 */
public class DeleteOntologyResourceAction extends AbstractAction implements
                                                                TreeNodeSelectionListener {
  private static final long serialVersionUID = 3257289136439439920L;

  public DeleteOntologyResourceAction(String caption, Icon icon) {
    super(caption, icon);
  }

  @Override
  public void actionPerformed(ActionEvent actionevent) {
    String[] resourcesToDelete = new String[selectedNodes.size()];
    int i = 0;
    for (DefaultMutableTreeNode node : selectedNodes) {
      OResource object = ((OResourceNode) node.getUserObject()).getResource();
      resourcesToDelete[i++] = object.getONodeID().toString();
    }
    JList<String> list = new JList<String>(resourcesToDelete);
    int choice = JOptionPane.showOptionDialog(MainFrame.getInstance(),
      new Object[]{"Are you sure you want to delete the following resources?",
      "\n\n", new JScrollPane(list), '\n'}, "Delete resources",
      JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
      new String[]{"Delete resources", "Cancel"}, "Cancel");
    if (choice == JOptionPane.CLOSED_OPTION || choice == 1)  { return; }
    for (DefaultMutableTreeNode node : selectedNodes) {
      Object object = ((OResourceNode) node.getUserObject()).getResource();
      try {
        if (object instanceof OClass) {
          if (ontology.containsOClass(((OClass) object).getONodeID()))
            ontology.removeOClass((OClass) object);
          continue;
        }
        if (object instanceof OInstance) {
          if (ontology.getOInstance((OURI)((OInstance) object).getONodeID()) != null)
            ontology.removeOInstance((OInstance) object);
          continue;
        }
        if ((object instanceof RDFProperty)
          && Utils.getOResourceFromMap(ontology,
            ((RDFProperty) object).getONodeID().toString()) != null)
          ontology.removeProperty((RDFProperty) object);
      }
      catch (Exception re) {
        re.printStackTrace();
        JOptionPane.showMessageDialog(MainFrame.getInstance(), re.getMessage() +
          "\nPlease see tab messages for more information!");
      }
    }
  }

  public Ontology getOntology() {
    return ontology;
  }

  public void setOntology(Ontology ontology) {
    this.ontology = ontology;
  }

  @Override
  public void selectionChanged(ArrayList<DefaultMutableTreeNode> arraylist) {
    selectedNodes = arraylist;
  }

  protected Ontology ontology;
  protected ArrayList<DefaultMutableTreeNode> selectedNodes;
}
