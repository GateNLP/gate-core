/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 21/11/2002
 *
 *  $Id: ActionsPublisher.java 15333 2012-02-07 13:18:33Z ian_roberts $
 *
 */

package gate.gui;

import java.util.List;

import javax.swing.Action;

/**
 * This interface is used to mark resources that publish a list of actions
 * that can be performed on them.
 * Those actions will automatically be added to the appropriate menus when
 * needed.
 */

public interface ActionsPublisher {
  /**
   * Returns a list of Action objects. This method will be called everytime a
   * menu for this resource needs to be built, thus allowing for dynamic updates
   * to the list of actions.
   * A <tt>null</tt> value in this list will cause a separator to be created.
   * @return a {@link List} of {@link javax.swing.Action}s.
   */
  public List<Action> getActions();
}