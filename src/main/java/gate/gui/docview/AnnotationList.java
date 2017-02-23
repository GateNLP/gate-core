/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  AnnotationList.java
 *
 *  Valentin Tablan, 23 Apr 2008
 *
 *  $Id: AnnotationList.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.gui.docview;

import gate.gui.annedit.AnnotationData;

import javax.swing.ListSelectionModel;

/**
 * Interface for document views showing a list of annotations.
 */
public interface AnnotationList extends DocumentView {
  
  /**
   * Obtains the selection model used by this list view.
   * @return a {@link ListSelectionModel} object.
   */
  public ListSelectionModel getSelectionModel();
  
  /**
   * Provides the annotation 
   */
  public AnnotationData getAnnotationAtRow(int row);

  /**
   * Returns the display row for a given annotation. 
   * @param aData the annotation for which the row is required
   * @return a positive int value if the annotation is found or -1 otherwise 
   */
  public int getRowForAnnotation(AnnotationData aData);
}
