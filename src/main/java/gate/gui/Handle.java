/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 09/03/2001
 *
 *  $Id: Handle.java 15333 2012-02-07 13:18:33Z ian_roberts $
 *
 */
package gate.gui;


import java.awt.Window;

import javax.swing.*;

import gate.event.ProgressListener;
import gate.event.StatusListener;

/**
 * Interface for classes used to store the information about an open resource.
 * Such information will include icon to be used for tree components,
 * popup menu for right click events, etc.
 */
public interface Handle extends ProgressListener, StatusListener {

  public Icon getIcon();

  public String getTitle();

  /**
   * Returns a GUI component to be used as a small viewer/editor, e.g. below
   * the main tree in the Gate GUI for the selected resource
   */
  public JComponent getSmallView();

  /**
   * Returns the large view for this resource. This view will go into the main
   * display area.
   */
  public JComponent getLargeView();

  /**
   * Returns <tt>true</tt> if the views have already been built for this handle.
   * @return a <tt>boolean</tt> value.
   */
  public boolean viewsBuilt();
  
  /**
   * Called when this handle is not required any more.
   */
  public void cleanup();
  
  /**
   * A call to this method will cause the handle to destroy all the views built
   * for the target resource.
   *
   */
  public void removeViews();
  
  public JPopupMenu getPopup();

  public String getTooltipText();

  public Object getTarget();

  /**
   * Returns the top level GUI component that is a parent to all other GUI
   * components
   */
  public Window getWindow();
}