/*  TabBlinker.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 30/03/2001
 *
 *  $Id: TabBlinker.java 17530 2014-03-04 15:57:43Z markagreenwood $
 *
 */
package gate.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class TabBlinker implements Runnable{
    public TabBlinker(JTabbedPane pane, Component comp, Color blinkColor){
      this.tPane = pane;
      this.tab = tPane.indexOfComponent(comp);
      this.blinkColor = blinkColor;
      thread = new Thread(Thread.currentThread().getThreadGroup(),
                          this,
                          "TabBlinker1");
      thread.setPriority(Thread.MIN_PRIORITY);
    }// TabBlinker(JTabbedPane pane, Component comp, Color blinkColor)

    @Override
    public void run(){
      oldColor = tPane.getBackgroundAt(tab);
      synchronized(this){
        stopIt = false;
      }
      while(true){
        synchronized(this){
          if(tPane.getSelectedIndex() == tab) stopIt = true;
          if(stopIt){
            tPane.setBackgroundAt(tab, oldColor);
            return;
          }
        }
        SwingUtilities.invokeLater(new Runnable(){
          @Override
          public void run(){
            if(tPane.getBackgroundAt(tab).equals(oldColor)){
              tPane.setBackgroundAt(tab, blinkColor);
            }else{
              tPane.setBackgroundAt(tab, oldColor);
            }
          }// run()
        });
        try {
          Thread.sleep(400);
        } catch(InterruptedException ie){}
      }// while
    }//run()

    public void stopBlinking(int foo){
      synchronized(this){
        if(thread.isAlive()){
          stopIt = true;
        }
      }
    }// void stopBlinking()

    public void startBlinking(){
      synchronized(this){
        if(!thread.isAlive()){
          thread = new Thread(Thread.currentThread().getThreadGroup(),
                              this,
                              "TabBlinker2");
          thread.setPriority(Thread.MIN_PRIORITY);
          thread.start();
        }
      }
    }// void startBlinking()

    boolean stopIt;
    JTabbedPane tPane;
    int tab;
    Color blinkColor;
    Color oldColor;
    Thread thread;
  }//class TabBlinker implements Runnable