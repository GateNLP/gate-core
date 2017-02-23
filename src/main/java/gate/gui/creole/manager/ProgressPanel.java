/*
 * ProgressPanel.java
 * 
 * Copyright (c) 2011, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Mark A. Greenwood, 30/10/2011
 */

package gate.gui.creole.manager;

import gate.Gate;

import gate.resources.img.svg.DownloadIcon;
import gate.resources.img.svg.ProgressIcon;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ProgressPanel extends JPanel implements ComponentListener {
  private static class SafeJProgressBar extends JProgressBar {
    @Override
    public void setIndeterminate(boolean indeterminate) {
      // workaround for bug in some versions of Aqua L&F that prevents GATE
      // from exiting if indeterminate progress bars are used
      if(Gate.runningOnMac() && (UIManager.getLookAndFeel() == null
          || UIManager.getLookAndFeel().getClass().getName().equals(
            UIManager.getSystemLookAndFeelClassName()))) {
        return;
      } else {
        super.setIndeterminate(indeterminate);
      }
    }
  }

  private JProgressBar progressTotal, progressSingle;

  private JLabel message, dlMsg;

  public ProgressPanel() {
    super();

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    progressTotal = new SafeJProgressBar();
    progressTotal.setAlignmentX(CENTER_ALIGNMENT);
    progressTotal.setMaximumSize(new Dimension(250, progressTotal
            .getPreferredSize().height));
    progressTotal.setIndeterminate(true);

    progressSingle = new SafeJProgressBar();
    progressSingle.setAlignmentX(CENTER_ALIGNMENT);
    progressSingle.setMaximumSize(new Dimension(250, progressTotal
            .getPreferredSize().height));
    progressSingle.setIndeterminate(true);
    progressSingle.setVisible(false);
    progressSingle.setMinimum(0);
    progressSingle.setMaximum(100);

    message = new JLabel("");
    message.setIcon(new ProgressIcon(48, 48));
    message.setHorizontalTextPosition(SwingConstants.RIGHT);
    message.setHorizontalAlignment(SwingConstants.CENTER);
    message.setAlignmentX(CENTER_ALIGNMENT);

    dlMsg = new JLabel("Downloading CREOLE Plugin...");
    dlMsg.setIcon(new DownloadIcon(48, 48));
    dlMsg.setHorizontalTextPosition(SwingConstants.RIGHT);
    dlMsg.setHorizontalAlignment(SwingConstants.CENTER);
    dlMsg.setAlignmentX(CENTER_ALIGNMENT);
    dlMsg.setVisible(false);

    add(Box.createVerticalGlue());
    add(message);
    add(Box.createVerticalStrut(5));
    add(progressTotal);
    add(Box.createVerticalStrut(10));
    add(dlMsg);
    add(Box.createVerticalStrut(5));
    add(progressSingle);
    add(Box.createVerticalGlue());

    addComponentListener(this);
  }

  public void downloadStarting(final String name, final boolean sizeUnknown) {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        progressSingle.setValue(0);
        progressSingle.setIndeterminate(sizeUnknown);
        dlMsg.setText("Downloading " + name + " CREOLE Plugin...");
        dlMsg.setVisible(true);
        progressSingle.setVisible(true);
      }
    });
  }

  public void downloadFinished() {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        dlMsg.setVisible(false);
        progressSingle.setVisible(false);
      }
    });
  }

  public void downloadProgress(final int progress) {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        progressSingle.setValue(progress);
      }
    });
  }

  public void valueIncrement() {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        progressTotal.setValue(progressTotal.getValue() + 1);
      }
    });
  }

  public void valueChanged(final int value) {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        progressTotal.setValue(value);
      }
    });
  }

  public void rangeChanged(final int min, final int max) {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        progressTotal.setMinimum(min);
        progressTotal.setMaximum(max);
        progressTotal.setIndeterminate(min == max);
      }
    });
  }

  public void messageChanged(final String newMessage) {
    SwingUtilities.invokeLater(new Thread() {
      @Override
      public void run() {
        message.setText("<html><body>" + newMessage + "</body></html>");
      }
    });
  }

  @Override
  public void componentHidden(ComponentEvent e) {
    // we don't currently need this
  }

  @Override
  public void componentMoved(ComponentEvent e) {
    // we don't currently need this
  }

  @Override
  public void componentResized(ComponentEvent e) {
    int width = Math.min(400, (int)(getSize().width * (2f / 3)));
    progressTotal.setMaximumSize(new Dimension(width, progressTotal
            .getPreferredSize().height));
    progressSingle.setMaximumSize(new Dimension(width, progressSingle
            .getPreferredSize().height));
  }

  @Override
  public void componentShown(ComponentEvent e) {
    valueChanged(0);
    componentResized(e);
  }
}
