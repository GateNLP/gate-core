/*
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Valentin Tablan, Sep 11, 2007
 * 
 * $Id: OwnedAnnotationEditor.java 17077 2013-11-12 16:13:30Z markagreenwood $
 */
package gate.gui.annedit;

import gate.creole.AnnotationVisualResource;

import java.awt.ComponentOrientation;

/**
 * Interface for all annotation editor components
 */
public interface OwnedAnnotationEditor extends AnnotationVisualResource {
  /**
   * Finds the best location for the editor dialog for a given span of text
   */
  public void placeDialog(int start, int end);

  /**
   * Sets the owner (i.e. controller) for this editor.
   * 
   * @param owner
   */
  public void setOwner(AnnotationEditorOwner owner);

  /**
   * @return owner The owner (i.e. controller) for this editor.
   */
  public AnnotationEditorOwner getOwner();

  /**
   * @param pinned
   *          true if the window should not move when an annotation is selected.
   */
  public void setPinnedMode(boolean pinned);

  /**
   * Enable or disable the editing GUI components.
   * 
   * @param isEditingEnabled
   *          true to enable the editing, false to disable it
   */
  public void setEditingEnabled(boolean isEditingEnabled);

  /**
   * Changes the orientation of components
   * 
   * @param orientation
   */
  public void changeOrientation(ComponentOrientation orientation);
}
