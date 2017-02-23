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
 *  $Id: GateEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */
package gate.event;

import java.util.EventObject;

/**
 * The top level event class for all the event types fired by the Gate system.
 */
public class GateEvent extends EventObject {

  private static final long serialVersionUID = 7914516539094860389L;

  public static final int FEATURES_UPDATED = 701;

  /**
   * Constructor from source and type.
   * @param source the object that initiated this event
   * @param type the type on the event.
   */
  public GateEvent(Object source, int type) {
    super(source);
    this.type = type;
  }


  /**
   * Gets the type of the event.
   */
  public int getType() {
    return type;
  }

  protected int type;
}