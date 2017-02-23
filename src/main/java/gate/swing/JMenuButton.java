/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  JMenuButton.java
 *
 *  Valentin Tablan, 21 Aug 2008
 *
 *  $Id: JMenuButton.java 17608 2014-03-09 12:38:50Z markagreenwood $
 */

package gate.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * A toggle button that shows a pop-up menu.
 */
@SuppressWarnings("serial")
public class JMenuButton extends JToggleButton {
  public JMenuButton(JMenu menu) {
    this(menu.getPopupMenu());
    this.menu = menu;
  }
  
  public JMenuButton(JPopupMenu popup) {
    this.popup = popup;
    popup.setInvoker(this);
    initListeners();
  }

  protected void initListeners() {
    
    addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if(menu != null) menu.setSelected(isSelected());
        if(isSelected()) {
          // show the popup
          Point p = getPopupMenuOrigin();
          popup.show(JMenuButton.this, p.x, p.y);
        }
        else {
          // hide the popup
          popup.setVisible(false);
        }
      }
    });
    
    popup.addPopupMenuListener(new PopupMenuListener(){

      @Override
      public void popupMenuCanceled(PopupMenuEvent e) {
        setSelected(false);
        if(menu != null) menu.setSelected(false);
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        setSelected(false);
        if(menu != null) menu.setSelected(false);
      }

      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      }
    });
  }

  /**
   * Method largely borrowed from Swing's JMenu.
   * 
   * Computes the origin for the <code>JMenu</code>'s popup menu. This
   * method uses Look and Feel properties named
   * <code>Menu.menuPopupOffsetX</code>,
   * <code>Menu.menuPopupOffsetY</code>,
   * <code>Menu.submenuPopupOffsetX</code>, and
   * <code>Menu.submenuPopupOffsetY</code> to adjust the exact location
   * of popup.
   * 
   * @return a <code>Point</code> in the coordinate space of the menu
   *         which should be used as the origin of the
   *         <code>JMenu</code>'s popup menu
   */
  protected Point getPopupMenuOrigin() {
    int x = 0;
    int y = 0;
    // Figure out the sizes needed to caclulate the menu position
    Dimension s = getSize();
    Dimension pmSize = popup.getSize();
    // For the first time the menu is popped up,
    // the size has not yet been initiated
    if(pmSize.width == 0) {
      pmSize = popup.getPreferredSize();
    }
    Point position = getLocationOnScreen();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    GraphicsConfiguration gc = getGraphicsConfiguration();
    Rectangle screenBounds = new Rectangle(toolkit.getScreenSize());
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] gd = ge.getScreenDevices();
    for(int i = 0; i < gd.length; i++) {
      if(gd[i].getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
        GraphicsConfiguration dgc = gd[i].getDefaultConfiguration();
        if(dgc.getBounds().contains(position)) {
          gc = dgc;
          break;
        }
      }
    }

    if(gc != null) {
      screenBounds = gc.getBounds();
      // take screen insets (e.g. taskbar) into account
      Insets screenInsets = toolkit.getScreenInsets(gc);

      screenBounds.width -= Math.abs(screenInsets.left + screenInsets.right);
      screenBounds.height -= Math.abs(screenInsets.top + screenInsets.bottom);
      position.x -= Math.abs(screenInsets.left);
      position.y -= Math.abs(screenInsets.top);
    }

    // We are a toplevel menu (pull-down)
    int xOffset = UIManager.getInt("Menu.menuPopupOffsetX");
    int yOffset = UIManager.getInt("Menu.menuPopupOffsetY");

    if(getComponentOrientation().isLeftToRight()) {
      // First determine the x:
      x = xOffset; // Extend to the right
      if(position.x + x + pmSize.width >= screenBounds.width + screenBounds.x &&
      // popup doesn't fit - place it wherever there's more
              // room
              screenBounds.width - s.width < 2 * (position.x - screenBounds.x)) {

        x = s.width - xOffset - pmSize.width;
      }
    }
    else {
      // First determine the x:
      x = s.width - xOffset - pmSize.width; // Extend to the left
      if(position.x + x < screenBounds.x &&
      // popup doesn't fit - place it wherever there's more room
              screenBounds.width - s.width > 2 * (position.x - screenBounds.x)) {

        x = xOffset;
      }
    }
    // Then the y:
    y = s.height + yOffset; // Prefer dropping down
    if(position.y + y + pmSize.height >= screenBounds.height &&
    // popup doesn't fit - place it wherever there's more room
            screenBounds.height - s.height < 2 * (position.y - screenBounds.y)) {

      y = 0 - yOffset - pmSize.height; // Otherwise drop 'up'
    }
    return new Point(x, y);
  }

  protected JPopupMenu popup;
  protected JMenu menu;
}
