/*
 * LogArea.java
 * 
 * Copyright (c) 1995-2013, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Cristian URSU, 26/03/2001
 * 
 * $Id: LogArea.java 17606 2014-03-09 12:12:49Z markagreenwood $
 */

package gate.gui;

import gate.Gate;
import gate.swing.XJFileChooser;
import gate.swing.XJTextPane;
import gate.util.Err;
import gate.util.ExtensionFileFilter;
import gate.util.OptionsMap;
import gate.util.Out;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Position;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * This class is used to log all messages from GATE. When an object of this
 * class is created, it redirects the output of {@link gate.util.Out} &
 * {@link gate.util.Err}. The output from Err is written with <font
 * color="red">red</font> and the one from Out is written in <b>black</b>.
 */
@SuppressWarnings("serial")
public class LogArea extends XJTextPane {

  /** Field needed in inner classes */
  protected LogArea thisLogArea = null;

  /** The popup menu with various actions */
  protected JPopupMenu popup = null;

  /** Start position from the document. */
  protected Position startPos;

  /** End position from the document. */
  protected Position endPos;

  /** The original printstream on System.out */
  protected PrintStream originalOut;

  /** The original printstream on System.err */
  protected PrintStream originalErr;

  /** This fields defines the Select all behaviour */
  protected SelectAllAction selectAllAction = null;

  /** This fields defines the copy behaviour */
  protected CopyAction copyAction = null;

  /** This fields defines the clear all behaviour */
  protected ClearAllAction clearAllAction = null;

  /**
   * The component actually used in the GUI which includes other things not just
   * the text area
   */
  private JComponent logTab = null;

  private JToggleButton btnScrollLock = null;

  private SpinnerNumberModel logSizeModel = null;

  private JCheckBox cboLogSize, cboAppend;

  private OptionsMap userConfig = Gate.getUserConfig();

  private PrintWriter logFileWriter = null;
  
  private File logFile;

  /**
   * Constructs a LogArea object and captures the output from Err and Out. The
   * output from System.out & System.err is not captured.
   */
  public LogArea() {
    thisLogArea = this;
    this.setEditable(false);

    DefaultCaret caret = new DefaultCaret();
    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    this.setCaret(caret);

    LogAreaOutputStream err = new LogAreaOutputStream(true);
    LogAreaOutputStream out = new LogAreaOutputStream(false);

    // Redirecting Err
    try {
      Err.setPrintWriter(new UTF8PrintWriter(err, true));
    } catch(UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }
    // Redirecting Out
    try {
      Out.setPrintWriter(new UTF8PrintWriter(out, true));
    } catch(UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }

    // Redirecting System.out
    originalOut = System.out;
    try {
      System.setOut(new UTF8PrintStream(out, true));
    } catch(UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }

    // Redirecting System.err
    originalErr = System.err;
    try {
      System.setErr(new UTF8PrintStream(err, true));
    } catch(UnsupportedEncodingException uee) {
      uee.printStackTrace(originalErr);
    }
    popup = new JPopupMenu();
    selectAllAction = new SelectAllAction();
    copyAction = new CopyAction();
    clearAllAction = new ClearAllAction();
    startPos = getDocument().getStartPosition();
    endPos = getDocument().getEndPosition();

    popup.add(selectAllAction);
    popup.add(copyAction);
    popup.addSeparator();
    popup.add(clearAllAction);
    initListeners();
  }// LogArea

  public JComponent getComponentToDisplay() {
    // don't build the display more than once
    if(logTab != null) return logTab;

    // create the main panel we will return so we can add things to it
    logTab = new JPanel(new BorderLayout());

    JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
    toolbar.setFloatable(false);

    JButton btnClear = new JButton(MainFrame.getIcon("ClearLog"));
    btnClear.setToolTipText("Clear Log");
    btnClear.addActionListener(clearAllAction);

    btnScrollLock =
        new JToggleButton(MainFrame.getIcon("ScrollLock"),
            userConfig.getBoolean("ScrollLock", Boolean.FALSE));
    btnScrollLock.setToolTipText("Scroll Lock");
    btnScrollLock.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        userConfig.put("ScrollLock", btnScrollLock.isSelected());
      }
    });

    logSizeModel =
        new SpinnerNumberModel(userConfig.getInt("LogSize", 80000).intValue(),
            0, Integer.MAX_VALUE, 1);
    logSizeModel.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent arg0) {
        userConfig.put("LogSize", logSizeModel.getValue());
      }
    });

    final JSpinner spinLogSize = new JSpinner(logSizeModel);
    if(spinLogSize.getEditor() instanceof JSpinner.DefaultEditor) {
      JTextField textField =
          ((JSpinner.DefaultEditor)spinLogSize.getEditor()).getTextField();
      textField.setColumns(5);
    }

    cboLogSize =
        new JCheckBox("Max Log Size (chars)", userConfig.getBoolean(
            "LimitLogSize", Boolean.TRUE));
    cboLogSize.setOpaque(false);
    spinLogSize.setEnabled(cboLogSize.isSelected());
    cboLogSize.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        spinLogSize.setEnabled(cboLogSize.isSelected());
        userConfig.put("LimitLogSize", cboLogSize.isSelected());
      }
    });

    toolbar.add(cboLogSize);
    toolbar.add(spinLogSize);
    toolbar.addSeparator();

    Icon fileIcon = MainFrame.getIcon("OpenFile");
    final JButton btnLogFile = new JButton(fileIcon);
    
    BufferedImage disabledIcon = new BufferedImage(fileIcon.getIconWidth(), fileIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = disabledIcon.createGraphics();
    g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f));
    fileIcon.paintIcon(null, g2d, 0, 0);
    btnLogFile.setDisabledIcon(new ImageIcon(disabledIcon));
    
    btnLogFile.setToolTipText("Select Log File");
    btnLogFile.setEnabled(userConfig.getBoolean("LogToFile", Boolean.FALSE));
    
    final JTextField txtLogFile = new JTextField(20);

    logFile = userConfig.getFile("LogFile");
    if(logFile != null) {
      try {
        if (btnLogFile.isEnabled()) logFileWriter = new PrintWriter(new FileWriter(logFile, true));
        txtLogFile.setText(logFile.getAbsolutePath());
      } catch(IOException ioe) {
        logFile = null;
      }
    }    
    
    btnLogFile.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        XJFileChooser fileChooser = MainFrame.getFileChooser();
        ExtensionFileFilter filter =
            new ExtensionFileFilter("Log Files (*.txt)", "txt");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Log File");

        if(fileChooser.showSaveDialog(MainFrame.getInstance()) != JFileChooser.APPROVE_OPTION)
          return;

        File logFile = fileChooser.getSelectedFile();
        if(logFile == null) return;

        try {
          logFileWriter = new PrintWriter(new FileWriter(logFile, true));
          userConfig.put("LogFile", logFile);
          txtLogFile.setText(logFile.getAbsolutePath());
        } catch(IOException ioe) {
          logFile = null;
          logFileWriter = null;
          txtLogFile.setText("");
          userConfig.remove("LogFile");
          ioe.printStackTrace();
        }
      }
    });

    txtLogFile.setEditable(false);
    txtLogFile.setEnabled(btnLogFile.isEnabled());

    cboAppend = new JCheckBox("Append To", btnLogFile.isEnabled());
    cboAppend.setOpaque(false);
    cboAppend.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        try {
          logFileWriter = cboAppend.isSelected() && logFile != null ? new PrintWriter(new FileWriter(logFile, true)) : null;
          btnLogFile.setEnabled(cboAppend.isSelected());
          txtLogFile.setEnabled(cboAppend.isSelected());
          userConfig.put("LogToFile", cboAppend.isSelected());
        }
        catch (IOException e) {
          logFile = null;
          logFileWriter = null;
          txtLogFile.setText("");
          userConfig.remove("LogFile");
          e.printStackTrace();
        }
      }
    });

    toolbar.add(cboAppend);
    toolbar.add(txtLogFile);
    toolbar.add(btnLogFile);
    toolbar.addSeparator();

    toolbar.add(Box.createHorizontalGlue());
    toolbar.add(btnClear);
    toolbar.add(btnScrollLock);

    // add the text area as the main component, inside a scroller
    logTab.add(new JScrollPane(this), BorderLayout.CENTER);
    logTab.add(toolbar, BorderLayout.SOUTH);

    // return the display
    return logTab;
  }

  /**
   * Overridden to fetch new start and end Positions when the document is
   * changed.
   */
  @Override
  public void setDocument(Document d) {
    super.setDocument(d);
    startPos = d.getStartPosition();
    endPos = d.getEndPosition();
  }

  @Override
  public void setStyledDocument(StyledDocument d) {
    this.setDocument(d);
  }

  /** Init all listeners for this object */
  @Override
  public void initListeners() {
    super.initListeners();
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)) {
          popup.show(thisLogArea, e.getPoint().x, e.getPoint().y);
        }// End if
      }// end mouseClicked()
    });// End addMouseListener();
  }

  /** Returns the original printstream on System.err */
  public PrintStream getOriginalErr() {
    return originalErr;
  }

  /** Returns the original printstream on System.out */
  public PrintStream getOriginalOut() {
    return originalOut;
  }// initListeners();

  /** Inner class that defines the behaviour of SelectAll action. */
  protected class SelectAllAction extends AbstractAction {
    public SelectAllAction() {
      super("Select all");
    }// SelectAll

    @Override
    public void actionPerformed(ActionEvent e) {
      thisLogArea.selectAll();
    }// actionPerformed();
  }// End class SelectAllAction

  /** Inner class that defines the behaviour of copy action. */
  protected class CopyAction extends AbstractAction {
    public CopyAction() {
      super("Copy");
    }// CopyAction

    @Override
    public void actionPerformed(ActionEvent e) {
      thisLogArea.copy();
    }// actionPerformed();
  }// End class CopyAction

  /**
   * A runnable that adds a bit of text to the area; needed so we can write from
   * the Swing thread.
   */
  protected class SwingWriter implements Runnable {
    SwingWriter(String text, Style style) {
      this.text = text;
      this.style = style;
    }

    @Override
    public void run() {

      if(cboAppend.isSelected() && logFileWriter != null) {
        // if logging to a file is enabled then do the logging
        logFileWriter.print(text);
        logFileWriter.flush();
      }

      try {
        // endPos is always one past the real end position because of the
        // implicit newline character at the end of any Document
        getDocument().insertString(endPos.getOffset() - 1, text, style);

        if(cboLogSize.isSelected()
            && getDocument().getLength() > logSizeModel.getNumber().intValue()) {
          // if the document is now over the buffer size then trim it roughly to
          // length by finding the first new line within the valid buffer size
          // and cutting there or if there is no new line then just trim to
          // length
          int index =
              getText().indexOf(
                  "\n",
                  getDocument().getLength()
                      - logSizeModel.getNumber().intValue()) + 1;
          getDocument().remove(
              0,
              index != 0 ? index : getDocument().getLength()
                  - logSizeModel.getNumber().intValue());
        }

        if(!btnScrollLock.isSelected()) {
          setCaretPosition(getDocument().getLength());
        }
      } catch(BadLocationException e) {
        // a BLE here is a real problem
        handleBadLocationException(e, text, style);
      }// End try
    }

    String text;

    Style style;
  }

  /**
   * Try and recover from a BadLocationException thrown when inserting a string
   * into the log area. This method must only be called on the AWT event
   * handling thread.
   */
  private void handleBadLocationException(BadLocationException e,
      String textToInsert, Style style) {
    originalErr.println("BadLocationException encountered when writing to "
        + "the log area: " + e);
    originalErr.println("trying to recover...");

    Document newDocument = new DefaultStyledDocument();
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("An error occurred when trying to write a message to the log area.  The log\n");
      sb.append("has been cleared to try and recover from this problem.\n\n");
      sb.append(textToInsert);

      newDocument.insertString(0, sb.toString(), style);
    } catch(BadLocationException e2) {
      // oh dear, all bets are off now...
      e2.printStackTrace(originalErr);
      return;
    }
    // replace the log area's document with the new one
    setDocument(newDocument);
  }

  /**
   * A print writer that uses UTF-8 to convert from char[] to byte[]
   */
  public static class UTF8PrintWriter extends PrintWriter {
    public UTF8PrintWriter(OutputStream out)
        throws UnsupportedEncodingException {
      this(out, true);
    }

    public UTF8PrintWriter(OutputStream out, boolean autoFlush)
        throws UnsupportedEncodingException {
      super(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")), autoFlush);
    }
  }

  /**
   * A print writer that uses UTF-8 to convert from char[] to byte[]
   */
  public static class UTF8PrintStream extends PrintStream {
    public UTF8PrintStream(OutputStream out)
        throws UnsupportedEncodingException {
      this(out, true);
    }

    public UTF8PrintStream(OutputStream out, boolean autoFlush)
        throws UnsupportedEncodingException {
      super(out, autoFlush);
    }

    /**
     * Overriden so it uses UTF-8 when converting a string to byte[]
     * 
     * @param s
     *          the string to be printed
     */
    @Override
    public void print(String s) {
      try {
        write((s == null ? "null" : s).getBytes("UTF-8"));
      } catch(UnsupportedEncodingException uee) {
        // support for UTF-8 is guaranteed by the JVM specification
      } catch(IOException ioe) {
        // print streams don't throw exceptions
        setError();
      }
    }

    /**
     * Overriden so it uses UTF-8 when converting a char[] to byte[]
     * 
     * @param s
     *          the string to be printed
     */
    @Override
    public void print(char s[]) {
      print(String.valueOf(s));
    }
  }

  /** Inner class that defines the behaviour of clear all action. */
  protected class ClearAllAction extends AbstractAction {
    public ClearAllAction() {
      super("Clear all");
    }// ClearAllAction

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        thisLogArea.getDocument().remove(startPos.getOffset(),
            endPos.getOffset() - startPos.getOffset() - 1);
      } catch(BadLocationException e1) {
        // it's OK to print this exception to the current log area
        e1.printStackTrace(Err.getPrintWriter());
      }// End try
    }// actionPerformed();
  }// End class ClearAllAction

  /**
   * Inner class that defines the behaviour of an OutputStream that writes to
   * the LogArea.
   */
  class LogAreaOutputStream extends OutputStream {
    /** This field dictates the style on how to write */
    private boolean isErr = false;

    /** Char style */
    private Style style = null;

    /** Constructs an Out or Err LogAreaOutputStream */
    public LogAreaOutputStream(boolean anIsErr) {
      isErr = anIsErr;
      if(isErr) {
        style = addStyle("error", getStyle("default"));
        StyleConstants.setForeground(style, Color.red);
      } else {
        style = addStyle("out", getStyle("default"));
        StyleConstants.setForeground(style, Color.black);
      }// End if
    }// LogAreaOutputStream

    /**
     * Writes an int which must be a the code of a char, into the LogArea, using
     * the style specified in constructor. The int is downcast to a byte.
     */
    @Override
    public void write(int charCode) {
      // charCode int must be a char. Let us be sure of that
      charCode &= 0x000000FF;
      // Convert the byte to a char before put it into the log area
      char c = (char)charCode;
      // Insert it in the log Area
      SwingUtilities.invokeLater(new SwingWriter(String.valueOf(c), style));
    }// write(int charCode)

    /**
     * Writes an array of bytes into the LogArea, using the style specified in
     * constructor.
     */
    @Override
    public void write(byte[] data, int offset, int length) {
      // Insert the string to the log area
      try {
        SwingUtilities.invokeLater(new SwingWriter(new String(data, offset,
            length, "UTF-8"), style));
      } catch(UnsupportedEncodingException uee) {
        // should never happen - all JREs are required to support UTF-8
        uee.printStackTrace(originalErr);
      }
    }// write(byte[] data, int offset, int length)
  }// //End class LogAreaOutputStream
}// End class LogArea
