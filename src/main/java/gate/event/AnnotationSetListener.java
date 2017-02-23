/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 08/03/2001
 *
 *  $Id: AnnotationSetListener.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.event;

import java.util.EventListener;

/**
 * A listener for events fired by an {@link gate.AnnotationSet}
 * ({@link gate.event.AnnotationSetEvent})
 */
public interface AnnotationSetListener extends EventListener {

  /**Called when a new {@link gate.Annotation} has been added*/
  public void annotationAdded(AnnotationSetEvent e);

  /**Called when an {@link gate.Annotation} has been removed*/
  public void annotationRemoved(AnnotationSetEvent e);


}