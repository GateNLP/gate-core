/*
 *  Copyright 1994-2009 Sun Microsystems, Inc. All Rights Reserved.
 *  Under BSD licence, LGPL compatible.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  From Sun forums:
 *  <a href="http://forums.sun.com/thread.jspa?threadID=294121&forumID=57">
 *  Swing [Archive] - Block JFrame and JDialog with JGlassPane</a>
 *
 *  Retrieved by Thomas Heitz - 03 july 2009.
 *
 *  $Id$
 */

package gate.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A Panel that can be blocked.
 * <br>
 * Just set an instance of this class as the glassPane
 * of your JFrame and call <code>block()</code> as needed.
 */
@SuppressWarnings("serial")
public class BlockingGlassPane extends JPanel {

  private int        blockCount = 0;
  private BlockMouse blockMouse = new BlockMouse();
  private BlockKeys  blockKeys  = new BlockKeys();

  /**
   * Constructor.
   */
  public BlockingGlassPane() {
    setVisible(false);
    setOpaque (false);

    addMouseListener(blockMouse);
  }

  /**
   * Start or end blocking.
   *
   * @param block   should blocking be started or ended
   */
  public void block(boolean block) {
    if (block) {
      if (blockCount == 0) {
        setVisible(true);

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        FocusManager.getCurrentManager().addKeyEventDispatcher(blockKeys);
      }
      blockCount++;
    } else {
      blockCount--;
      if (blockCount == 0) {
        FocusManager.getCurrentManager().removeKeyEventDispatcher(blockKeys);

        setCursor(Cursor.getDefaultCursor());

        setVisible(false);
      }
    }
  }

  /**
   * Test if this glasspane is blocked.
   *
   * @return    <code>true</code> if currently blocked
   */
  public boolean isBlocked() {
    return blockCount > 0;
  }

  /**
   * The key dispatcher to block the keys.
   */
  private class BlockKeys implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent ev) {
      Component source = ev.getComponent();
      if (source != null &&
          SwingUtilities.isDescendingFrom(source, getParent())) {
        Toolkit.getDefaultToolkit().beep();
        ev.consume();
        return true;
      }
      return false;
    }
  }

  /**
   * The mouse listener used to block the mouse.
   */
  private class BlockMouse extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent ev) {
      Toolkit.getDefaultToolkit().beep();
    }
  }
}
