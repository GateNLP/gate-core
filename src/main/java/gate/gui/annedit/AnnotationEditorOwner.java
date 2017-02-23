/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 17 Sep 2007
 *
 *  $Id: AnnotationEditorOwner.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.gui.annedit;

import javax.swing.text.JTextComponent;

import gate.*;

/**
 * Objects of this type control the interaction with an 
 * {@link OwnedAnnotationEditor}.
 */
public interface AnnotationEditorOwner {

  /**
   * Gets the document currently being edited.
   * @return a {@link Document} object.
   */
  public Document getDocument();
  
  /**
   * Gets the UI component used to display the document text. This is used by 
   * the annotation editor for obtaining positioning information. 
   * @return a {@link JTextComponent} object.
   */
  public JTextComponent getTextComponent();
  

  /**
   * Called by the annotation editor when an annotation has been 
   * changed.
   * @param ann the annotation modified (after the modification occurred).
   * @param set the parent annotation set for the annotation
   * @param oldType the old type of the annotation. This value is only set if 
   * the annotation modification included a change of type.  
   */
  public void annotationChanged(Annotation ann, AnnotationSet set, 
          String oldType);
  
  /**
   * Called by the editor when a new annotation needs to be selected.
   */
  public void selectAnnotation(AnnotationData aData);
  
  /**
   * Called by the editor for obtaining the next annotation to be edited.
   * @return an {@link Annotation} value.
   */
  public Annotation getNextAnnotation();
  
  /**
   * Called by the editor for obtaining the previous annotation to be edited.
   * @return an {@link Annotation} value.
   */
  public Annotation getPreviousAnnotation();  
}
