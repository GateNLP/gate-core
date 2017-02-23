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
 *  $Id: DocumentEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */
package gate.event;

import gate.Document;

/**
 * This class models events fired by an {@link gate.Document}.
 */
public class DocumentEvent extends GateEvent {

  private static final long serialVersionUID = 2315324967342557414L;

  /**Event type used to mark the addition of an {@link gate.AnnotationSet}*/
  public static final int ANNOTATION_SET_ADDED = 101;

  /**Event type used to mark the removal of an {@link gate.AnnotationSet}*/
  public static final int ANNOTATION_SET_REMOVED = 102;

  /**Event type used to mark the editing of the document content
   */
  public static final int CONTENT_EDITED = 103;
  
  /**
   * Constructor.
   * @param source the document that has been changed
   * @param type the type of the event
   * @param setName the name of the {@link gate.AnnotationSet} that has been
   * added or removed.
   */
  public DocumentEvent(Document source, int type, String setName) {
    super(source, type);
    this.annotationSetName = setName;
  }

  /**
   * Constructor.
   * @param source the document that has been changed
   * @param type the type of the event
   * @param editStart the offset where the edit operation started
   * @param editEnd the offset where the edit operation ended
   */
  public DocumentEvent(Document source, int type, Long editStart, Long editEnd) {
    super(source, type);
    this.editStart = editStart;
    this.editEnd = editEnd;
  }
  
  /**
   * Gets the name of the {@link gate.AnnotationSet} that has been added or
   * removed.
   */
  public String getAnnotationSetName() {
    return annotationSetName;
  }

  /**
   * @return Returns the editEnd.
   */
  public Long getEditEnd(){
    return editEnd;
  }
  
  /**
   * @return Returns the editStart.
   */
  public Long getEditStart(){
    return editStart;
  }
  private String annotationSetName;
  private Long editStart;
  private Long editEnd;
}