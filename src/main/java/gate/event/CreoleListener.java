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
 *  $Id: CreoleListener.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.event;

import gate.Resource;

/**
 * A listener for events fired by the {@link gate.CreoleRegister}
 * ({@link gate.event.CreoleEvent}).
 * In a Gate system there are many classes that can fire {@link CreoleEvent}s
 * but all this events are collected and fired back by the
 * {@link gate.CreoleRegister} that can be obtained with a call to
 * {@link gate.Gate#getCreoleRegister()}
 */
public interface CreoleListener extends java.util.EventListener{

  /**Called when a new {@link gate.Resource} has been loaded into the system*/
  public void resourceLoaded(CreoleEvent e);

  /**Called when a {@link gate.Resource} has been removed from the system*/
  public void resourceUnloaded(CreoleEvent e);

  /**Called when a {@link gate.DataStore} has been opened*/
  public void datastoreOpened(CreoleEvent e);

  /**Called when a {@link gate.DataStore} has been created*/
  public void datastoreCreated(CreoleEvent e);

  /**Called when a {@link gate.DataStore} has been closed*/
  public void datastoreClosed(CreoleEvent e);

  /**
   * Called when the creole register has renamed a resource.1
   */
  public void resourceRenamed(Resource resource, String oldName,
                              String newName);

}