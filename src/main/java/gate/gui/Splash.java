/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 28/01/2001
 *
 *  $Id: Splash.java 17606 2014-03-09 12:12:49Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * A splash screen.
 * A splash screen is an image that appears on the screen while an application
 * initialises. The implementation uses a {@link java.awt.Window} (a Frame with
 * no decorations such as bar or buttons) and can either display a JComponent
 * as content or an image.
 * When clicked it is hidden.
 */
@SuppressWarnings("serial")
public class Splash extends JWindow {

  /**
   * Constructor from owner, GraphicsConfiguration and content.
   */
  public Splash(Window owner, GraphicsConfiguration gc, final JComponent content) {
    super(owner, gc);
    getContentPane().setLayout(new BorderLayout());
    content.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    getContentPane().add(content, BorderLayout.CENTER);
    content.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // when the content pane is clicked, hide it
        setVisible(false);
      }
    });
    validate();
    pack();
  }

  public Splash(Window owner,  JComponent content) {
    this(owner, null, content);
  }
  
  /**
   * Constructor from image.
   */
  public Splash(String imageResourcePath) {
    this(null, imageResourcePath);
  }

  /**
   * Constructor from content.
   */
  public Splash(JComponent content) {
    this(null, content);
  }

  /**
   * Constructor from owner and image.
   */
  public Splash(Window owner, String imageResourcePath) {
    this(owner,
        new JLabel(new ImageIcon(Splash.class.getResource(imageResourcePath))));
  }

  /**
   * Displays the splash screen centered in the owner's space or centered on
   * the screen if no owner or owner not shown.
   */
  public void showSplash(){
    Dimension ownerSize;
    Point ownerLocation;
    Rectangle screenBounds = getGraphicsConfiguration().getBounds();
    if(getOwner() == null){
      ownerSize = screenBounds.getSize();
      ownerLocation = screenBounds.getLocation();
    }else{
      ownerSize = getOwner().getSize();
      ownerLocation = getOwner().getLocation();
      if(ownerSize.height == 0 ||
         ownerSize.width == 0 ||
         !getOwner().isVisible()){
        ownerSize = screenBounds.getSize();
        ownerLocation = screenBounds.getLocation();        
      }
    }
    //Center the window
    Dimension frameSize = getSize();
    if (frameSize.height > ownerSize.height)
      frameSize.height = ownerSize.height;
    if (frameSize.width > ownerSize.width)
      frameSize.width = ownerSize.width;
    setLocation(ownerLocation.x + (ownerSize.width - frameSize.width) / 2,
                ownerLocation.y + (ownerSize.height - frameSize.height) / 2);
    super.setVisible(true);
  }
}