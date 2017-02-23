/*  XJMenuItem.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 02/04/2001
 *
 *  $Id: XJMenuItem.java 17612 2014-03-10 08:51:17Z markagreenwood $
 *
 */

package gate.swing;

import gate.event.StatusListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Extension of a JMenuItem that adds a description and a StatusListener
 * as parameters. The description is used in the statusListener.
 */
@SuppressWarnings("serial")
public class XJMenuItem extends JMenuItem {

  public XJMenuItem(Icon icon, String description, StatusListener listener){
    super(icon);
    this.description = description;
    this.listener = listener;
    initListeners();
  }

  public XJMenuItem(String text, String description, StatusListener listener){
    super(text);
    this.description = description;
    this.listener = listener;
    initListeners();
  }

  public XJMenuItem(Action a, StatusListener listener){
    super(a);
    this.description = (String) a.getValue(Action.SHORT_DESCRIPTION);
    this.listener = listener;
    // stop showing tooltip in the menu, status bar is enough
    setToolTipText(null);
    initListeners();
  }

  public XJMenuItem(String text, Icon icon,
                    String description, StatusListener listener){
    super(text, icon);
    this.description = description;
    this.listener = listener;
    initListeners();
  }

  public XJMenuItem(String text, int mnemonic,
                    String description, StatusListener listener){
    super(text, mnemonic);
    this.description = description;
    this.listener = listener;
    initListeners();
  }

  protected void initListeners(){
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        // clear the status
        listener.statusChanged("");
      }
    });
    this.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        // display the menu item description in the status
        listener.statusChanged(description);
      }
    });
  }

  private StatusListener listener;
  String description;
}