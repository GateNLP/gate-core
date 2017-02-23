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
 *  $Id: DatastoreListener.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.event;

import java.util.EventListener;

/**
 * A listener for {@link DatastoreEvent}s.
 */
public interface DatastoreListener extends EventListener {

  /**
   * Called by a datastore when a new resource has been adopted
   */
  public void resourceAdopted(DatastoreEvent evt);

  /**
   * Called by a datastore when a resource has been deleted
   */
  public void resourceDeleted(DatastoreEvent evt);

  /**
   * Called by a datastore when a resource has been wrote into the datastore
   */
  public void resourceWritten(DatastoreEvent evt);

}