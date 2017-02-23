/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 22 March 2004
 *
 *  $Id: DocumentView.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.gui.docview;

import java.awt.Component;
import java.util.List;

import gate.VisualResource;
import gate.gui.ActionsPublisher;
import gate.gui.annedit.AnnotationData;

/**
 * A document viewer is composed out of several views (like the one showing the
 * text, the one showing the annotation sets, the on showing the annotations
 * table, etc.). This is the base interface for all the document views.
 * All document views are panes inside a {@link gate.gui.docview.DocumentEditor}
 * object.
 */

public interface DocumentView extends ActionsPublisher, VisualResource{

  /**
   * Returns the actual UI component this view represents.
   * @return a {@link Component} value.
   */
  public Component getGUI();

  /**
   * Returns the type of this view.
   * @return an int value
   * @see #CENTRAL
   * @see #HORIZONTAL
   * @see #VERTICAL
   */
  public int getType();
  
  /**
   * Notifies this view that it has become active or inactive.
   * Implementers are encouraged to lazily populate the UI elements, that is 
   * to use as little CPU time as possible before the view becomes active.
   * @param active a boolean value.
   */
  public void setActive(boolean active);
  
  /**
   * Returns the active state of this view. 
   * @return a boolean value
   */
  public boolean isActive();
  
  /**
   * Notifies this view of its owner.
   * @param editor the {@link DocumentEditor} that contains this view.
   */
  public void setOwner(DocumentEditor editor);
  
  /**
   * Some document views can use the concept of selected annotations. This 
   * method is called to change the set of selected annotations.
   * The recommended way to change the selected annotations set is by calling
   * the {@link DocumentEditor#setSelectedAnnotations(List)} method. 
   * @param selectedAnnots
   */
  public void setSelectedAnnotations(List<AnnotationData> selectedAnnots);
  
  /**
   * Constant for the CENTRAL type of the view inside the document editor. Views
   * of this type are placed in the center of the document editor.
   */
  public static final int CENTRAL = 0;

  /**
   * Constant for the VERTICAL type of the view inside the document editor.
   * Views of this type are placed as a vertical band on the right side of the
   * document editor.
   */
  public static final int VERTICAL = 1;

  /**
   * Constant for the HORIZONTAL type of the view inside the document editor.
   * Views of this type are placed as a horizontal band on the lower side of the
   * document editor.
   */
  public static final int HORIZONTAL = 2;
  

}