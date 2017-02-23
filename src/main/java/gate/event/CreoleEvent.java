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
 *  $Id: CreoleEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */

package gate.event;

import gate.*;

/**
 * Events related to the gate.creole package. This kind of events will
 * be fired when resources are loaded or unloaded in the Gate system.
 */
public class CreoleEvent extends GateEvent {

  private static final long serialVersionUID = -6834347398037784548L;

  /**
   * Constructor
   * @param res the {@link gate.Resource} that has been (un)loaded
   * @param type the type of the event
   */
  public CreoleEvent(Resource res, int type){
    //the source will always be the Creole register
    super(Gate.getCreoleRegister(), type);
    this.resource = res;
    datastore = null;
  }

  /**
   * Constructor
   * @param datastore the {@link gate.DataStore} that has been
   * created/loaded/closed.
   * @param type the type of the event
   */
  public CreoleEvent(DataStore datastore, int type){
    //the source will always be the Creole register
    super(Gate.getCreoleRegister(), type);
    this.resource = null;
    this.datastore = datastore;
  }

  /**
   * Gets the resource that has been (un)loaded.
   */
  public gate.Resource getResource() {
    return resource;
  }

  /**
   * Gets the {@link gate.DataStore} that has been created/loaded/closed.
   */
  public DataStore getDatastore(){
    return datastore;
  }

  /**Event type that marks the loading of a new resource into the Gate system*/
  public static final int RESOURCE_LOADED = 1;

  /**Event type that marks the unloading of a resource from the Gate system*/
  public static final int RESOURCE_UNLOADED = 2;

  /**Event type that marks the creation of a new datastore*/
  public static final int DATASTORE_CREATED = 3;

  /**Event type that mark the opening of a datastore*/
  public static final int DATASTORE_OPENED = 4;

  /**Event type that mark the closing of a datastore*/
  public static final int DATASTORE_CLOSED = 5;

  private gate.Resource resource;
  private DataStore datastore;

}