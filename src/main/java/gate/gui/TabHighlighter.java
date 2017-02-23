/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 07/11/2001
 *
 *  $Id: TabHighlighter.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Highligts a tab in a JTabbedPane. Removes the highlight automatically when
 * the highlighted tab is selected.
 */
public class TabHighlighter {
  public TabHighlighter(JTabbedPane pane, Component comp,
                        Color highlightColour){
    this.tPane = pane;
    this.tab = tPane.indexOfComponent(comp);
    this.highlightColour = highlightColour;
    tPane.getModel().addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if(tPane.getSelectedIndex() == tab) removeHighlight();
      }
    });

    tPane.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(tPane.getSelectedIndex() == tab) removeHighlight();
      }
    });

  }// TabBlinker(JTabbedPane pane, Component comp, Color blinkColor)

  /**
   * Highlights the tab unless is selected
   */
  public void highlight(){
    if(tPane.getSelectedIndex() != tab){
      if(tPane.getBackgroundAt(tab).equals(highlightColour)) return;

      oldColour = tPane.getBackgroundAt(tab);
      tPane.setBackgroundAt(tab, highlightColour);
    }
  }//public void highlight()


  /**
   * Restores the tab to the normal colour
   */
  public void removeHighlight(){
    tPane.setBackgroundAt(tab, oldColour);
  }// public void removeHighlight()

  JTabbedPane tPane;
  int tab;
  Color highlightColour;
  Color oldColour;
}