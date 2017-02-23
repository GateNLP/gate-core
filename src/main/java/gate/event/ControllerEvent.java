/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  ControllerEvent.java
 *
 *  Valentin Tablan, 28-Jun-2004
 *
 *  $Id: ControllerEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */

package gate.event;

import gate.ProcessingResource;

/**
 * Events fired by controllers.
 */
public class ControllerEvent extends GateEvent{

  private static final long serialVersionUID = 7881276383734235706L;

  public ControllerEvent(Object source, int type, ProcessingResource pr){
    super(source, type);
    this.pr = pr;
  }
  
  
  /**
   * @return Returns the processing resource.
   */
  public ProcessingResource getPr(){
    return pr;
  }
  /**
   * @param pr The processing resource involved in this event.
   */
  public void setPr(ProcessingResource pr){
    this.pr = pr;
  }
  
  protected ProcessingResource pr;
  public static final int RESOURCE_ADDED = 0;
  public static final int RESOURCE_REMOVED = 1;
  
}
