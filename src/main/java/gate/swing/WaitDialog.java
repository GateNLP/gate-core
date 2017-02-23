/*
 *  WaitDialog.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 12/07/2000
 *
 *  $Id: WaitDialog.java 17612 2014-03-10 08:51:17Z markagreenwood $
 */

package gate.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * A small window used to show messages to the user during processing.
 * This component is intended as a nicer alternative
 * to a status bar/progress bar.
 * The window has its own thread for updating the animated pictures displayed.
 *
 */
@SuppressWarnings("serial")
public class WaitDialog extends JWindow implements Runnable {

  /**    *
   */
  Box centerBox;

  /**    */
  public WaitDialog(Frame frame, String title) {
    super(frame);
    //this.icon = new ImageIcon(ClassLoader.getSystemResource(
    //            "gate/resources/img/working"));
    // use the cached version from MainFrame
    this.icon = gate.gui.MainFrame.getIcon("working");
    this.frame = frame;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Shows the window containing labels for the texts provided as attributes.
   *
   * @param texts
   */
  public synchronized void showDialog(String[] texts) {
    centerBox.removeAll();

    for(int i =0; i < texts.length; i++){
      centerBox.add(new JLabel(texts[i]));
    }

    centerBox.validate();
    pack();
/*
    Point loc = frame.getLocation();
    loc.move(frame.getSize().width - getSize().width / 2 ,
             frame.getSize().height - getSize().height /2 );
    setLocation(loc);
*/
    stop = false;
    Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                               this,
                               "WaitDialog1");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.start();
    setVisible(true);
  }

  /**
   * Shows the window containing the components provided as attributes.
   *
   * @param components
   */
  public synchronized void showDialog(Component[] components) {
    centerBox.removeAll();
    for(int i =0; i < components.length; i++){
      centerBox.add(components[i]);
    }
    centerBox.validate();
    pack();
/*
    Point loc = frame.getLocation();
    setLocation(loc.x + (frame.getSize().width - getSize().width) / 2 ,
                loc.y + (frame.getSize().height - getSize().height) /2);
*/
    stop = false;
    Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                               this,
                               "WaitDialog2");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.start();
    setVisible(true);
  }

  /**    */
  void jbInit() throws Exception {
    JPanel centerPanel = new JPanel();
    centerBox = Box.createVerticalBox();
    centerPanel.setLayout(borderLayout1);
    //centerPanel.setBorder(new LineBorder(Color.darkGray, 2));
//    centerPanel.setBackground(Color.white);
//    centerBox.setBackground(Color.white);
    picture = new JLabel(icon);
    picture.setOpaque(false);
    centerPanel.add(centerBox, BorderLayout.CENTER);
    centerPanel.add(picture, BorderLayout.WEST);
    centerPanel.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
    centerPanel.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);
    centerPanel.add(Box.createHorizontalStrut(8), BorderLayout.EAST);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
    centerPanel.setOpaque(false);
  }

  /**
   * Hides the window.
   *
   */
  public void goAway() {
    stop = true;
  }

  /**    *
   */
  @Override
  public void run() {
    while(!stop){
      try{
        Thread.sleep(300);
        centerBox.validate();
        pack();
        /*
        Point loc = frame.getLocation();
        setLocation(loc.x + (frame.getSize().width - getSize().width) / 2 ,
                    loc.y + (frame.getSize().height - getSize().height) /2);
        */
        picture.paintImmediately(picture.getVisibleRect());
      }catch(InterruptedException ie){}
    }
    this.setVisible(false);
  }


  boolean stop = false;

  BorderLayout borderLayout1 = new BorderLayout();

  Frame frame;

  JLabel picture;

  Icon icon;

} // class WaitDialog
