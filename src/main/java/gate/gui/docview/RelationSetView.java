/*
 *  RelationSetView.java
 *
 *  Copyright (c) 1995-2013, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mark A. Greenwood, 15th December 2013
 */
package gate.gui.docview;

import gate.AnnotationSet;
import gate.Document;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.event.AnnotationSetEvent;
import gate.event.AnnotationSetListener;
import gate.event.DocumentEvent;
import gate.event.DocumentListener;
import gate.event.RelationSetEvent;
import gate.event.RelationSetListener;
import gate.gui.MainFrame;
import gate.relations.RelationSet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

@CreoleResource(name = "Relation Viewer", guiType = GuiType.LARGE, resourceDisplayed = "gate.Document")
public class RelationSetView extends AbstractVisualResource
                                                           implements
                                                           DocumentListener,
                                                           AnnotationSetListener,
                                                           RelationSetListener {

  private static final long serialVersionUID = 2976754146115707386L;

  private JTextPane text = new JTextPane();

  private Document doc = null;

  @Override
  public Resource init() {
    setLayout(new BorderLayout());

    text.setEditable(false);

    add(text, BorderLayout.CENTER);

    JButton btnRefresh = new JButton("Refresh", MainFrame.getIcon("Refresh"));
    btnRefresh.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        refresh();
      }
    });

    JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
    toolbar.setFloatable(false);

    toolbar.add(btnRefresh);
    toolbar.addSeparator();
    toolbar.add(new JLabel("Currently this view is not automatically updated"));

    // not currently need as we now support the listners properly
    // add(toolbar, BorderLayout.NORTH);

    return this;
  }

  private void refresh() {
    if(doc == null) return;

    StringBuilder builder = new StringBuilder();

    RelationSet relations = doc.getAnnotations().getRelations();
    if(relations.size() > 0) {
      builder.append(relations).append("\n\n");
    }

    for(String name : doc.getAnnotationSetNames()) {
      relations = doc.getAnnotations(name).getRelations();
      if(relations.size() > 0) {
        builder.append(name).append(":\n").append(relations).append("\n\n");
      }
    }

    text.setText(builder.toString());
  }

  @Override
  public void setTarget(Object target) {
    if(doc != null) {
      doc.removeDocumentListener(this);
      doc.getAnnotations().removeAnnotationSetListener(this);
      doc.getAnnotations().getRelations().removeRelationSetListener(this);
      for(String name : doc.getAnnotationSetNames()) {
        AnnotationSet as = doc.getAnnotations(name);
        as.removeAnnotationSetListener(this);
        as.getRelations().removeRelationSetListener(this);
      }
    }

    doc = (Document)target;
    doc.addDocumentListener(this);
    doc.getAnnotations().addAnnotationSetListener(this);
    doc.getAnnotations().getRelations().addRelationSetListener(this);

    for(String name : doc.getAnnotationSetNames()) {
      AnnotationSet as = doc.getAnnotations(name);
      as.addAnnotationSetListener(this);
      as.getRelations().addRelationSetListener(this);
    }

    refresh();
  }

  @Override
  public void annotationSetAdded(DocumentEvent e) {
    doc.getAnnotations(e.getAnnotationSetName()).addAnnotationSetListener(this);
    refresh();
  }

  @Override
  public void annotationSetRemoved(DocumentEvent e) {
    doc.getAnnotations(e.getAnnotationSetName()).removeAnnotationSetListener(
            this);
    refresh();
  }

  @Override
  public void contentEdited(DocumentEvent e) {
    // we don't care about changes in content, as hopefully any changes
    // that cause annotations to be deleted will be handled through the
    // appropriate handlers
  }

  @Override
  public void annotationAdded(AnnotationSetEvent e) {
    refresh();
  }

  @Override
  public void annotationRemoved(AnnotationSetEvent e) {
    refresh();
  }

  @Override
  public void relationAdded(RelationSetEvent e) {
    refresh();
  }

  @Override
  public void relationRemoved(RelationSetEvent e) {
    refresh();
  }
}
