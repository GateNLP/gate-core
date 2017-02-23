/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 21/04/2001
 *
 *  $Id: DatastoreEvent.java 17595 2014-03-08 13:05:32Z markagreenwood $
 */
package gate.event;

import gate.DataStore;
import gate.Resource;
/**
 * This class models events fired by datastores. Such events occur when new
 * resources are adopted by a datastore or when an existing resource from
 * the datastore is deleted.
 */
public class DatastoreEvent extends GateEvent {

  private static final long serialVersionUID = 1807421127920552348L;

  /**
   * Constructor.
   * @param source the datastore that originated the event.
   * @param type the event type.
   * @param res the resource that has been adopted/deleted/etc.
   * @param resourceID the ID corresponding to the resource in this datastore
   */
  public DatastoreEvent(DataStore source, int type, Resource res,
                        Object resourceID) {
    super(source, type);
    this.resource = res;
    this.resourceID = resourceID;
  }

  protected Resource resource;
  protected Object resourceID;

  /**
   * The type of events fired when a resource has been adopted
   */
  public static final int RESOURCE_ADOPTED = 301;

  /**
   * The type of events fired when a resource has been deleted from a datastore
   */
  public static final int RESOURCE_DELETED = 302;

  /**
   * The type of events fired when a resource has wrote into the datastore
   */
  public static final int RESOURCE_WRITTEN = 303;

  /** Gets the ID of the resource involved in this event */
  public Object getResourceID() {
    return resourceID;
  }

  /** Gets the resource involved in this event */
  public gate.Resource getResource() {
    return resource;
  }
}
