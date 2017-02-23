/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva 21/10/2001
 *
 *  $Id: AnnotationEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */

package gate.event;

import gate.Annotation;

/**
 * This class models events fired by an {@link gate.Annotation}.
 */
public class AnnotationEvent extends GateEvent{

  private static final long serialVersionUID = -5794804058531941540L;

  /**Event type used for situations when an annotation has been updated*/
  public static final int ANNOTATION_UPDATED = 601;


  /**
   * Constructor.
   * @param source the {@link gate.Annotation} that fired the event
   * @param type the type of the event
   */
  public AnnotationEvent(Annotation source,
                            int type) {
    super(source, type);
  }

}