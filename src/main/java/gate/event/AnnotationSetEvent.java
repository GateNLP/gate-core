/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 12/12/2000
 *
 *  $Id: AnnotationSetEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */

package gate.event;

import gate.*;

/**
 * This class models events fired by an {@link gate.AnnotationSet}.
 */
public class AnnotationSetEvent extends GateEvent{

  private static final long serialVersionUID = 6618095991726447858L;

  /**Event type used for situations when a new annotation has been added*/
  public static final int ANNOTATION_ADDED = 201;

  /**Event type used for situations when an annotation has been removed*/
  public static final int ANNOTATION_REMOVED = 202;


  /**
   * Constructor.
   * @param source the {@link gate.AnnotationSet} that fired the event
   * @param type the type of the event
   * @param sourceDocument the {@link gate.Document} for wich the annotation
   * was added or removed.
   * @param annotation the annotation added or removed.
   */
  public AnnotationSetEvent(AnnotationSet source,
                            int type,
                            Document sourceDocument,
                            Annotation annotation) {
    super(source, type);
    this.sourceDocument = sourceDocument;
    this.annotation = annotation;
  }

  /**
   * Gets the document that has had an annotation added or removed.
   * @return a {@link gate.Document}
   */
  public gate.Document getSourceDocument() {
    return sourceDocument;
  }

  /**
   * Gets the annotation that has been added or removed
   * @return a {@link gate.Annotation}
   */
  public gate.Annotation getAnnotation() {
    return annotation;
  }

  private gate.Document sourceDocument;
  private gate.Annotation annotation;
}