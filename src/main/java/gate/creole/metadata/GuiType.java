/*
 *  GuiType.java
 *
 *  Copyright (c) 2008, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 27/Jul/2008
 *
 *  $Id: GuiType.java 9845 2008-08-25 22:23:24Z ian_roberts $
 */

package gate.creole.metadata;

/**
 * Enumeration defining the allowable GUI types for
 * {@link CreoleResource#guiType()}.
 */
public enum GuiType {
  /**
   * This resource is not a GUI resource.
   */
  NONE,

  /**
   * This resource is the small GUI for some other resource type.
   */
  SMALL,

  /**
   * This resource is the large GUI for some other resource type.
   */
  LARGE
}
