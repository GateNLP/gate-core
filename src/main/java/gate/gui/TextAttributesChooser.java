/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 13/12/2000
 *
 *  $Id: TextAttributesChooser.java 17861 2014-04-17 17:04:10Z markagreenwood $
 *
 */

package gate.gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;

import gate.util.Out;

/**
 * A dialog used to set the attributes for text display. The attribute set
 * includes font family, size, foreground and background colours, italic,
 * bold, etc.
 */
@SuppressWarnings("serial")
public class TextAttributesChooser extends JDialog {

  JComboBox<String> fontFamilyCombo;
  JComboBox<String> fontSizeCombo;
  JCheckBox boldChk;
  JCheckBox italicChk;
  JCheckBox underlineChk;
  JCheckBox subscriptChk;
  JCheckBox superscriptChk;
  JCheckBox strikethroughChk;

  JCheckBox useForegroundChk;
  JCheckBox useBackgroundChk;

  JColorChooser fgChooser;
  JColorChooser bgChooser;
  JTextPane sampleText;
  JButton okButton;
  JButton cancelButton;

  MutableAttributeSet currentStyle;

  boolean choice;


  public TextAttributesChooser(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }// public TextAttributesChooser(Frame parent, String title, boolean modal)

  public TextAttributesChooser(Dialog parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }// public TextAttributesChooser(Dialog parent, String title, boolean modal)


  public TextAttributesChooser() {
    this((Frame)null, "", false);
  }// public TextAttributesChooser()


  void jbInit() throws Exception {
    sampleText = new JTextPane();
    sampleText.setText("Type your own sample here...");
    if(currentStyle == null){
      StyleContext context = new StyleContext();
      currentStyle = context.addStyle(null, null);
      currentStyle.addAttributes(sampleText.getInputAttributes());
    }
    //The overall organisation is:
    //First level
      //Font Tab
      //Foreground colour
      //Background colour
    //Second level
      //Sample text
    //Third level
      //Ok Button
      //Cancel Button

    Box contents = Box.createVerticalBox();
    //FIRST LEVEL
    JTabbedPane firstLevel = new JTabbedPane();
    //Font stuff
    Box fontBox = Box.createVerticalBox();

    fontFamilyCombo = new JComboBox<String>(
                        GraphicsEnvironment.getLocalGraphicsEnvironment().
                        getAvailableFontFamilyNames()
                      );
    fontFamilyCombo.setSelectedItem(StyleConstants.getFontFamily(currentStyle));
    fontSizeCombo = new JComboBox<String>(new String[]{"6", "8", "10", "12", "14", "16",
                                              "18", "20", "22", "24", "26"});
    fontSizeCombo.setSelectedItem(new Integer(
                        StyleConstants.getFontSize(currentStyle)).toString());
    fontSizeCombo.setEditable(true);
    JPanel box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    box.add(fontFamilyCombo);
    box.add(Box.createHorizontalStrut(5));
    box.add(fontSizeCombo);
    box.add(Box.createHorizontalGlue());
    box.setBorder(BorderFactory.createTitledBorder("Font"));
    fontBox.add(box);

    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    //First column
    Box box1 = Box.createVerticalBox();
    boldChk = new JCheckBox("<html><b>Bold</b></html>");
    boldChk.setSelected(StyleConstants.isBold(currentStyle));
    box1.add(boldChk);

//    italicChk = new JCheckBox("<html><i>Italic</i></html>");
//    italicChk.setSelected(StyleConstants.isItalic(currentStyle));
//    box1.add(italicChk);
    underlineChk = new JCheckBox("<html><u>Underline</u></html>");
    underlineChk.setSelected(StyleConstants.isUnderline(currentStyle));
//    box1.add(underlineChk);
    box.add(box1);

    //Second column
    box1 = Box.createVerticalBox();
    italicChk = new JCheckBox("<html><i>Italic</i></html>");
    italicChk.setSelected(StyleConstants.isItalic(currentStyle));
    box1.add(italicChk);


    subscriptChk = new JCheckBox("<html>T<sub>Subscript</sub></html>");
    subscriptChk.setSelected(StyleConstants.isSubscript(currentStyle));
//    box1.add(subscriptChk);
    superscriptChk = new JCheckBox("<html>T<sup>Superscript</sup></html>");
    superscriptChk.setSelected(StyleConstants.isSuperscript(currentStyle));
//    box1.add(superscriptChk);
    strikethroughChk = new JCheckBox(
                                "<html><strike>Strikethrough</strike></html>");
    strikethroughChk.setSelected(StyleConstants.isStrikeThrough(currentStyle));
//    box1.add(strikethroughChk);
    box.add(box1);
    box.add(Box.createHorizontalGlue());
    box.setBorder(BorderFactory.createTitledBorder("Effects"));

    fontBox.add(box);

    //Use colors checkboxes
    box = new JPanel();
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
    useForegroundChk = new JCheckBox("Use foreground colour");
    useForegroundChk.setSelected(false);
    box.add(useForegroundChk);

    useBackgroundChk = new JCheckBox("Use background colour");
    useBackgroundChk.setSelected(false);
    box.add(useBackgroundChk);

    box.add(Box.createHorizontalGlue());
    box.setBorder(BorderFactory.createTitledBorder("Use Colours"));

    fontBox.add(box);


    fontBox.add(Box.createVerticalGlue());
    firstLevel.add("Font", fontBox);
    //Colors stuff
    fgChooser = new JColorChooser(StyleConstants.getForeground(currentStyle));
    JTabbedPane tp = new JTabbedPane();
    AbstractColorChooserPanel[] panels = fgChooser.getChooserPanels();
    for(int i=0; i < panels.length; i++){
      tp.add(panels[i].getDisplayName(), panels[i]);
    }
    firstLevel.add("Foreground", tp);
    bgChooser = new JColorChooser(StyleConstants.getBackground(currentStyle));
    tp = new JTabbedPane();
    panels = bgChooser.getChooserPanels();
    for(int i=0; i < panels.length; i++){
      tp.add(panels[i].getDisplayName(), panels[i]);
    }
    firstLevel.add("Background", tp);

    contents.add(firstLevel);

    //SECOND LEVEL
    JPanel secondLevel = new JPanel();
    secondLevel.setBorder(BorderFactory.createTitledBorder("Sample"));
    //Sample text
    JScrollPane scroller = new JScrollPane(sampleText);
    scroller.setPreferredSize(new Dimension(400, 50));
    secondLevel.add(scroller);
    secondLevel.add(Box.createHorizontalGlue());
    contents.add(secondLevel);

    //THIRD LEVEL
    //Buttons
    Box thirdLevel = Box.createHorizontalBox();
    okButton = new JButton("OK");
    thirdLevel.add(okButton);
    cancelButton = new JButton("Cancel");
    thirdLevel.add(cancelButton);

    contents.add(thirdLevel);

    getContentPane().add(contents, BorderLayout.CENTER);

    fontFamilyCombo.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        StyleConstants.setFontFamily(currentStyle,
                                     (String)fontFamilyCombo.getSelectedItem());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    fontSizeCombo.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        try {
          Integer.parseInt((String)fontSizeCombo.getSelectedItem());
        } catch(NumberFormatException nfe) {
          fontSizeCombo.setSelectedIndex(3);
        }
        StyleConstants.setFontSize(currentStyle,
                                   Integer.parseInt((String)
                                   fontSizeCombo.getSelectedItem()));
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    boldChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        StyleConstants.setBold(currentStyle, boldChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    italicChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        StyleConstants.setItalic(currentStyle, italicChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    underlineChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(underlineChk.isSelected()) strikethroughChk.setSelected(false);
        StyleConstants.setUnderline(currentStyle, underlineChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    strikethroughChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(strikethroughChk.isSelected()) underlineChk.setSelected(false);
        StyleConstants.setStrikeThrough(currentStyle,
                                                strikethroughChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    superscriptChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(superscriptChk.isSelected()) subscriptChk.setSelected(false);
        StyleConstants.setSuperscript(currentStyle,
                                                  superscriptChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    subscriptChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(subscriptChk.isSelected()) superscriptChk.setSelected(false);
        StyleConstants.setSubscript(currentStyle, subscriptChk.isSelected());
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    fgChooser.getSelectionModel().addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        StyleConstants.setForeground(currentStyle, fgChooser.getColor());
        useForegroundChk.setSelected(true);
        updateSample();
      }// public void stateChanged(ChangeEvent e)
    });

    useForegroundChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(useForegroundChk.isSelected()) {
          StyleConstants.setForeground(currentStyle, fgChooser.getColor());
        } else {
          currentStyle.removeAttribute(StyleConstants.Foreground);
        }
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    bgChooser.getSelectionModel().addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        StyleConstants.setBackground(currentStyle, bgChooser.getColor());
        useBackgroundChk.setSelected(true);
        updateSample();
      }// public void stateChanged(ChangeEvent e)
    });

    useBackgroundChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(useBackgroundChk.isSelected()) {
          StyleConstants.setBackground(currentStyle, bgChooser.getColor());
        } else {
          currentStyle.removeAttribute(StyleConstants.Background);
        }
        updateSample();
      }// public void actionPerformed(ActionEvent e)
    });

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        updateSample();
      }// public void componentShown(ComponentEvent e)
    });

    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        choice = true;
        setVisible(false);
      }// public void actionPerformed(ActionEvent e)
    });

    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        choice = false;
        setVisible(false);
      }// public void actionPerformed(ActionEvent e)
    });

  }// void jbInit()

  /**
   * Initialises all the values for the attributes from the provided attribute
   * set and displays the dialog allowing the user to make changes.
   * If the user presses the <b>OK</b> button the method will return the values
   * as modified by the user otherwise it will return the attribute set received
   * as input.
   * @param style the attribute set to be used as a starting point for the user's
   * selections
   * @return an {@link javax.swing.text.AttributeSet} containing the values
   * selected by the user.
   */
  public AttributeSet show(AttributeSet style) {
    currentStyle = new SimpleAttributeSet(style);
    //currentStyle.addAttributes(style);
    updateData();
    updateSample();
    setModal(true);
    super.setVisible(true);
    if(choice) return currentStyle;
    else return style;
  }// public AttributeSet show(AttributeSet style)

  /**
   * Updates all the GUI components to show the values in the current attribute
   * set.
   */
  protected void updateData() {
    fontFamilyCombo.setSelectedItem(StyleConstants.getFontFamily(currentStyle));
    fontSizeCombo.setSelectedItem(new Integer(
                        StyleConstants.getFontSize(currentStyle)).toString());
    boldChk.setSelected(StyleConstants.isBold(currentStyle));
    italicChk.setSelected(StyleConstants.isItalic(currentStyle));
    italicChk.setSelected(StyleConstants.isItalic(currentStyle));
    underlineChk.setSelected(StyleConstants.isUnderline(currentStyle));
    subscriptChk.setSelected(StyleConstants.isSubscript(currentStyle));
    superscriptChk.setSelected(StyleConstants.isSuperscript(currentStyle));
    strikethroughChk.setSelected(StyleConstants.isStrikeThrough(currentStyle));
    if(currentStyle.isDefined(StyleConstants.Foreground)){
      fgChooser.setColor(StyleConstants.getForeground(currentStyle));
      useForegroundChk.setSelected(true);
    } else useForegroundChk.setSelected(false);
    if(currentStyle.isDefined(StyleConstants.Background)){
      bgChooser.setColor(StyleConstants.getBackground(currentStyle));
      useBackgroundChk.setSelected(true);
    } else useBackgroundChk.setSelected(false);
  }// protected void updateData()

  /**
   * Updates the sample text with the current attributes.
   */
  protected void updateSample() {
    if(sampleText.getSelectedText() != null &&
       sampleText.getSelectedText().length() > 0){
      sampleText.setCharacterAttributes(currentStyle, true);
    } else {
      sampleText.selectAll();
      sampleText.setCharacterAttributes(currentStyle, true);
      sampleText.setSelectionStart(0);
      sampleText.setSelectionEnd(0);
    }
  }//protected void updateSample()

  /**
   * Test code
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      JFrame frame = new JFrame("Frame");
      frame.addWindowListener(new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent e){
          System.exit(0);
        }
      });
      final TextAttributesChooser dialog = new TextAttributesChooser(frame,
                                                              "Dialog", false);
      //frame.getContentPane().add(dialog.getContentPane().getComponent(0));
      JButton btn = new JButton("Display Dialog");
      btn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
          Style style = new StyleContext().addStyle(null,null);
          StyleConstants.setBackground(style, Color.white);
          Out.println(dialog.show(style));
        }// public void actionPerformed(ActionEvent e)
      });
      frame.getContentPane().add(btn);
      frame.pack();
      frame.setVisible(true);

    } catch(Exception e){
      e.printStackTrace();
    }
  }// main
}// class TextAttributesChooser extends JDialog