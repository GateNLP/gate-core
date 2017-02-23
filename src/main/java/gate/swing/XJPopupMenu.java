/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 20 Feb 2003
 *
 *  $Id: XJPopupMenu.java 17612 2014-03-10 08:51:17Z markagreenwood $
 */

package gate.swing;


import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 * A modified version of JPopupMenu that uses {@link MenuLayout} as its layout.
 */
@SuppressWarnings("serial")
public class XJPopupMenu extends JPopupMenu {
  public XJPopupMenu() {
    super();
    setLayout(new MenuLayout());
  }

  public XJPopupMenu(String label){
    super(label);
    setLayout(new MenuLayout());
  }

  /**
   * Force separators to be the same width as the JPopupMenu.
   * This is because the MenuLayout make separators invisible contrary
   * to the default JPopupMenu layout manager.
   * @param aFlag true if the popupmenu is visible
   */
  @Override
  public void setVisible(boolean aFlag) {
    super.setVisible(aFlag);
    if (!aFlag) { return; }
    MenuLayout layout = (MenuLayout) getLayout();
    for (int i = 0; i < getComponents().length; i++) {
      Component component = getComponents()[i];
      if (component instanceof JSeparator) {
        JSeparator separator = (JSeparator) component;
        int column = layout.getColumnForComponentIndex(i);
        int preferredWidth = layout.getPreferredWidthForColumn(column);
        // use the popupmenu width to set the separators width
        separator.setPreferredSize(new Dimension(
          preferredWidth, separator.getHeight()));
      }
    }
    revalidate();
  }
}