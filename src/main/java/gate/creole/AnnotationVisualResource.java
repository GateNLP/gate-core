/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June1991.
 *
 *  A copy of this licence is included in the distribution in the file
 *  licence.html, and is also available at http://gate.ac.uk/gate/licence.html.
 *
 *  Valentin Tablan 09/07/2001
 *
 *  $Id: AnnotationVisualResource.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.creole;

import gate.*;
import gate.util.GateException;

/**
 * Visual Resources that display and/or edit annotations.
 * This type of resources can be used to either display or edit existing
 * annotations or to create new annotations.
 */
public interface AnnotationVisualResource extends VisualResource {

  /**
   * Called by the GUI when the user has pressed the "OK" button. This should
   * trigger the saving of the newly created annotation(s)
   */
  public void okAction() throws GateException;

  /**
   * Called by the GUI when the user has pressed the "Cancel" button. This should
   * trigger cleaning up action, if the editor has done any changes to the
   * annotation sets or document or annotation
   */
  public void cancelAction() throws GateException;

  /**
   * Checks whether this editor supports the cancel option
   * @return <tt>true</tt> iff this editor can rollback changes.
   */
  public boolean supportsCancel();

  /**
   * Checks whether this viewer/editor can handle a specific annotation type.
   * If the annotation type provided is <tt>null</tt>, then the check is whether
   * the viewer/editor can handle any arbitrary annotation. 
   */
  public boolean canDisplayAnnotationType(String annotationType);
  
  /**
   * Changes the annotation currently being edited.
   * @param ann the new annotation.
   * @param set the set to which the new annotation belongs. 
   */
  public void editAnnotation(Annotation ann, AnnotationSet set);
  
  /**
   * Checks whether the annotation currently being edited can be considered
   * complete.
   * @return <tt>true</tt> iff the editor has finished editing the current 
   * annotation. This might return <tt>false</tt> for instance when the current 
   * annotation does not yet comply with the schema and the editor 
   * implementation is designed to enforce schemas. 
   */
  public boolean editingFinished();
  
  /**
   * Checks whether the annotation editor is active (shown on screen and ready 
   * to edit annotations. 
   * @return <tt>true</tt> iff the editor is active.
   */
  public boolean isActive();

  /**
   * @return the annotation currently edited
   */
  public Annotation getAnnotationCurrentlyEdited();

  /**
   * @return the annotation set currently edited
   */
  public AnnotationSet getAnnotationSetCurrentlyEdited();



}//public interface AnnotationVisualResource extends VisualResource