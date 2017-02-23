/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 15/11/2001
 *
 *  $Id: OptionsDialog.java 20053 2017-02-02 06:09:08Z markagreenwood $
 *
 */
package gate.gui;

import gate.Gate;
import gate.GateConstants;
import gate.swing.JFontChooser;
import gate.util.GateRuntimeException;
import gate.util.OptionsMap;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * The options dialog for Gate.
 */
@SuppressWarnings("serial")
public class OptionsDialog extends JDialog {
  public OptionsDialog(Frame owner){
    super(owner, "GATE Options", true);
    MainFrame.getGuiRoots().add(this);
  }

  protected void initLocalData(){
    lookAndFeelClassName = userConfig.getString(GateConstants.LOOK_AND_FEEL);
    textComponentsFont = userConfig.getFont(GateConstants.TEXT_COMPONENTS_FONT);
    menusFont = userConfig.getFont(GateConstants.MENUS_FONT);
    componentsFont = userConfig.getFont(GateConstants.OTHER_COMPONENTS_FONT);
    dirtyGUI = false;
  }


  protected void initGuiComponents(){
    getContentPane().removeAll();
    mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    getContentPane().setLayout(new BoxLayout(getContentPane(),
                                             BoxLayout.Y_AXIS));
    getContentPane().add(mainTabbedPane);
    Box vBox;
    Box hBox;
    Box hBox2;

    /*******************
     * Appearance pane *
     *******************/

    //the LNF combo
    List<LNFData> supportedLNFs = new ArrayList<LNFData>();
    LNFData currentLNF = null;
    UIManager.LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
    for (UIManager.LookAndFeelInfo lnf : lnfs) {
      try {
        Class<?> lnfClass = Class.forName(lnf.getClassName());
        if (((LookAndFeel) (lnfClass.newInstance())).isSupportedLookAndFeel()) {
          if (lnf.getName().equals(UIManager.getLookAndFeel().getName())) {
            supportedLNFs.add(currentLNF =
              new LNFData(lnf.getClassName(), lnf.getName()));
          } else {
            supportedLNFs.add(new LNFData(lnf.getClassName(), lnf.getName()));
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    lnfCombo = new JComboBox<LNFData>(supportedLNFs.toArray(new LNFData[supportedLNFs.size()]));
    lnfCombo.setSelectedItem(currentLNF);
    lnfCombo.setToolTipText("Be aware that only 'Metal' is fully tested.");

    fontBG = new ButtonGroup();
    textBtn = new JRadioButton("Text components font");
    textBtn.setActionCommand("text");
    fontBG.add(textBtn);
    menuBtn = new JRadioButton("Menu components font");
    menuBtn.setActionCommand("menu");
    fontBG.add(menuBtn);
    otherCompsBtn = new JRadioButton("Other components font");
    otherCompsBtn.setActionCommand("other");
    fontBG.add(otherCompsBtn);

    JPanel appearanceBox = new JPanel();
    appearanceBox.setLayout(new BoxLayout(appearanceBox, BoxLayout.Y_AXIS));
    appearanceBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    appearanceBox.add(Box.createVerticalStrut(5));

    vBox = Box.createVerticalBox();
    vBox.setBackground(getContentPane().getBackground());
    vBox.setBorder(BorderFactory.createTitledBorder(" Look and Feel "));
    vBox.add(Box.createVerticalStrut(5));
      hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(lnfCombo);
      hBox.add(Box.createHorizontalStrut(5));
    vBox.add(hBox);
    vBox.add(Box.createVerticalStrut(5));
    appearanceBox.add(vBox);

    appearanceBox.add(Box.createVerticalStrut(5));

    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createTitledBorder(" Font options "));
    hBox.add(Box.createHorizontalStrut(5));
      vBox = Box.createVerticalBox();
      vBox.add(textBtn);
      vBox.add(Box.createVerticalStrut(5));
      vBox.add(menuBtn);
      vBox.add(Box.createVerticalStrut(5));
      vBox.add(otherCompsBtn);
      vBox.add(Box.createVerticalStrut(5));
      vBox.add(Box.createVerticalGlue());
    hBox.add(Box.createHorizontalStrut(5));
    hBox.add(vBox);
    fontChooser = new JFontChooser();
    hBox.add(fontChooser);
    hBox.add(Box.createHorizontalStrut(5));

    appearanceBox.add(hBox);

    mainTabbedPane.add("Appearance", appearanceBox);

    /*****************
     * Advanced pane *
     *****************/

    saveOptionsChk = new JCheckBox("Save options on exit",
      userConfig.getBoolean(GateConstants.SAVE_OPTIONS_ON_EXIT));
    saveOptionsChk.setToolTipText(
      "Remembers the options set in this dialogue.");

    saveSessionChk = new JCheckBox("Save session on exit",
      userConfig.getBoolean(GateConstants.SAVE_SESSION_ON_EXIT));
    saveSessionChk.setToolTipText(
      "Reloads the same resources in the tree on next start.");

    includeFeaturesOnPreserveFormatChk = new JCheckBox(
      "Include annotation features for \"Save preserving format\"",
      userConfig.getBoolean(
        GateConstants.SAVE_FEATURES_WHEN_PRESERVING_FORMAT));

    addSpaceOnMarkupUnpackChk = new JCheckBox(
      "Add space on markup unpack if needed", true);
    addSpaceOnMarkupUnpackChk.setToolTipText(
      "Adds a space instead of concatenate words separated by a XML tag");
    if ( (userConfig.get(GateConstants
      .DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME) != null)
      && !userConfig.getBoolean(GateConstants
      .DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME) )
      addSpaceOnMarkupUnpackChk.setSelected(false);

    browserComboBox = new JComboBox<String>(new String[] {
      "Default browser", "Custom"});
    browserComboBox.setPrototypeDisplayValue("Default browser");
    browserComboBox.setToolTipText(
      "Use Custom only if Default doesn't work.");
    browserCommandLineTextField = new JTextField(15);
    String commandLine =
      userConfig.getString(MainFrame.class.getName()+".browsercommandline");
    if(commandLine == null || commandLine.trim().length() == 0
    || commandLine.equals("Set dynamically when you display help.")) {
      // option not configured or empty or default browser
      browserComboBox.setSelectedItem("Default browser");
      browserCommandLineTextField.setEnabled(false);
    }
    else {
      browserComboBox.setSelectedItem("Custom");
    }
    browserCommandLineTextField.setText((commandLine == null)?"":commandLine);

    treeSelectViewChk = new JCheckBox("Tree select view",
      userConfig.getBoolean(MainFrame.class.getName()+".treeselectview"));
    treeSelectViewChk.setToolTipText(
      "Selection in left resources tree select the main view");

    viewSelectTreeChk = new JCheckBox("View select in tree",
      userConfig.getBoolean(MainFrame.class.getName()+".viewselecttree"));
    viewSelectTreeChk.setToolTipText(
      "Selection of the main view select item in left resources tree");

    JPanel advancedBox =  new JPanel();
    advancedBox.setLayout(new BoxLayout(advancedBox, BoxLayout.Y_AXIS));
    advancedBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    advancedBox.add(Box.createVerticalStrut(5));

    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createTitledBorder(" Advanced features "));
    hBox.add(Box.createHorizontalStrut(5));
      vBox = Box.createVerticalBox();
      vBox.add(includeFeaturesOnPreserveFormatChk);
      vBox.add(Box.createVerticalStrut(5));
      vBox.add(addSpaceOnMarkupUnpackChk);
      vBox.add(Box.createVerticalStrut(5));
    hBox.add(vBox);
    hBox.add(Box.createHorizontalStrut(5));
    hBox.add(Box.createHorizontalGlue());
    advancedBox.add(hBox);

    advancedBox.add(Box.createVerticalStrut(5));

    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createTitledBorder(" Session persistence "));
    hBox.add(Box.createHorizontalStrut(5));
      hBox2 = Box.createHorizontalBox();
      hBox2.add(saveOptionsChk);
      hBox2.add(Box.createVerticalStrut(5));
      hBox2.add(saveSessionChk);
      hBox2.add(Box.createVerticalStrut(5));
    hBox.add(hBox2);
    hBox.add(Box.createHorizontalStrut(5));
    hBox.add(Box.createHorizontalGlue());
    advancedBox.add(hBox);

    advancedBox.add(Box.createVerticalStrut(5));

    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createTitledBorder(" Help browser "));
    hBox.add(Box.createHorizontalStrut(5));
      vBox = Box.createVerticalBox();
      vBox.add(browserComboBox);
      vBox.add(Box.createVerticalStrut(5));
      vBox.add(browserCommandLineTextField);
      vBox.add(Box.createVerticalStrut(5));
    hBox.add(vBox);
    hBox.add(Box.createHorizontalStrut(5));
    advancedBox.add(hBox);

    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createTitledBorder(
      " Link resources tree selection and the main view "));
    hBox.add(Box.createHorizontalStrut(5));
      hBox2 = Box.createHorizontalBox();
      hBox2.add(treeSelectViewChk);
      hBox2.add(Box.createVerticalStrut(5));
      hBox2.add(viewSelectTreeChk);
      hBox2.add(Box.createVerticalStrut(5));
    hBox.add(hBox2);
    hBox.add(Box.createHorizontalStrut(5));
    hBox.add(Box.createHorizontalGlue());
    advancedBox.add(hBox);

    mainTabbedPane.add("Advanced", advancedBox);

    /******************
     * Dialog buttons *
     ******************/

    Box buttonsBox = Box.createHorizontalBox();
    okButton = new JButton(new OKAction());
    buttonsBox.add(okButton);
    buttonsBox.add(Box.createHorizontalStrut(10));
    buttonsBox.add(cancelButton = new JButton(new CancelAction()));

    getContentPane().add(Box.createVerticalStrut(10));
    getContentPane().add(buttonsBox);
    getContentPane().add(Box.createVerticalStrut(10));

    getRootPane().setDefaultButton(okButton);
  }

  protected void initListeners(){
    lnfCombo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!lookAndFeelClassName.equals(
           ((LNFData)lnfCombo.getSelectedItem()).className)
          ){
          dirtyGUI = true;
          lookAndFeelClassName = ((LNFData)lnfCombo.getSelectedItem()).
                                 className;
        }
      }
    });

    fontChooser.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equals("fontValue")){
          String selectedFont = fontBG.getSelection().getActionCommand();
          if(selectedFont.equals("text")){
            textComponentsFont = (Font)e.getNewValue();
            dirtyGUI = true;
          }else if(selectedFont.equals("menu")){
            menusFont = (Font)e.getNewValue();
            dirtyGUI = true;
          }else if(selectedFont.equals("other")){
            componentsFont = (Font)e.getNewValue();
            dirtyGUI = true;
          }
        }
      }
    });

    textBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(textBtn.isSelected()) selectedFontChanged();
        selectedFontBtn = "text";
        fontChooser.setFontValue(textComponentsFont);
      }
    });

    menuBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(menuBtn.isSelected()) selectedFontChanged();
        selectedFontBtn = "menu";
        fontChooser.setFontValue(menusFont);
      }
    });

    otherCompsBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(otherCompsBtn.isSelected()) selectedFontChanged();
        selectedFontBtn = "other";
        fontChooser.setFontValue(componentsFont);
      }
    });

    textBtn.setSelected(true);

    browserComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(browserComboBox.getSelectedItem() == null) {
          return;
        }
        String item = (String)browserComboBox.getSelectedItem();
        browserCommandLineTextField.setEnabled(item.equals("Custom"));
        if(item.equals("Default browser")) {
          browserCommandLineTextField.setText(
            "Set dynamically when you display help.");
        } else if(item.equals("Custom")) {
          browserCommandLineTextField.setText("firefox %file");
        }
      }
    });

    // define keystrokes action bindings at the level of the main window
    InputMap inputMap = ((JComponent)this.getContentPane())
      .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = ((JComponent)this.getContentPane()).getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "Apply");
    actionMap.put("Apply", new OKAction());
    inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "Cancel");
    actionMap.put("Cancel", new CancelAction());
  }

  protected void selectedFontChanged(){
    if(selectedFontBtn != null){
      //save the old font
      if(selectedFontBtn.equals("text")){
        textComponentsFont = fontChooser.getFontValue();
      }else if(selectedFontBtn.equals("menu")){
        menusFont = fontChooser.getFontValue();
      }else if(selectedFontBtn.equals("other")){
        componentsFont = fontChooser.getFontValue();
      }
    }
  }

  public void showDialog(){
    initLocalData();
    initGuiComponents();
    initListeners();
    textBtn.doClick();
    
    pack();
    setLocationRelativeTo(getOwner());
    setVisible(true);
    selectedFontBtn = null;
  }

  public static void main(String args[]){
    try{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }catch(Exception e){
      e.printStackTrace();
    }
    final JFrame frame = new JFrame("Foo frame");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JButton btn = new JButton("Show dialog");
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OptionsDialog dialog = new OptionsDialog(frame);
        dialog.pack();
        dialog.showDialog();
      }
    });
    frame.getContentPane().add(btn);
    frame.pack();
    frame.setVisible(true);
    System.out.println("Font: " + UIManager.getFont("Button.font"));
  }// main


  protected static void setUIDefaults(Object[] keys, Object value) {
    for(int i = 0; i < keys.length; i++){
      UIManager.put(keys[i], value);
    }
  }// setUIDefaults(Object[] keys, Object value)

  /**
   * Updates the Swing defaults table with the provided font to be used for the
   * text components
   */
  public static void setTextComponentsFont(Font font){
    setUIDefaults(textComponentsKeys, new FontUIResource(font));
    userConfig.put(GateConstants.TEXT_COMPONENTS_FONT, font);
  }

  /**
   * Updates the Swing defaults table with the provided font to be used for the
   * menu components
   */
  public static void setMenuComponentsFont(Font font){
    setUIDefaults(menuKeys, new FontUIResource(font));
    userConfig.put(GateConstants.MENUS_FONT, font);
  }

  /**
   * Updates the Swing defaults table with the provided font to be used for
   * various compoents that neither text or menu components
   */
  public static void setComponentsFont(Font font){
    setUIDefaults(componentsKeys, new FontUIResource(font));
    userConfig.put(GateConstants.OTHER_COMPONENTS_FONT, font);
  }

  class OKAction extends AbstractAction{
    OKAction(){
      super("OK");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(dirtyGUI){
        setMenuComponentsFont(menusFont);
        setComponentsFont(componentsFont);
        setTextComponentsFont(textComponentsFont);
        userConfig.put(GateConstants.LOOK_AND_FEEL, lookAndFeelClassName);
        try{
          UIManager.setLookAndFeel(lookAndFeelClassName);
        }catch(Exception e){
          throw new GateRuntimeException(
                  "Error while setting the look and feel", e);
        }
        Iterator<Component> rootsIter = MainFrame.getGuiRoots().iterator();
        while(rootsIter.hasNext()){
          try{
            SwingUtilities.updateComponentTreeUI(rootsIter.next());
          }catch(Exception e){
            throw new GateRuntimeException(
                    "Error while updating the graphical interface", e);
          }            
        }
      }

      userConfig.put(GateConstants.SAVE_OPTIONS_ON_EXIT,
        saveOptionsChk.isSelected());
      userConfig.put(GateConstants.SAVE_SESSION_ON_EXIT,
        saveSessionChk.isSelected());
      userConfig.put(GateConstants.SAVE_FEATURES_WHEN_PRESERVING_FORMAT,
        includeFeaturesOnPreserveFormatChk.isSelected());
      userConfig.put(GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME,
        addSpaceOnMarkupUnpackChk.isSelected());
      userConfig.put(MainFrame.class.getName()+".browsercommandline",
        browserCommandLineTextField.getText());
      userConfig.put(MainFrame.class.getName()+".treeselectview",
        treeSelectViewChk.isSelected());
      userConfig.put(MainFrame.class.getName()+".viewselecttree",
        viewSelectTreeChk.isSelected());
      setVisible(false);
    }// void actionPerformed(ActionEvent evt)
  }

  protected class CancelAction extends AbstractAction {
    public CancelAction(){
      super("Cancel");
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      setVisible(false);
    }
  }

  protected static class LNFData{
    public LNFData(String className, String name){
      this.className = className;
      this.name = name;
    }

    @Override
    public String toString(){
      return name;
    }

    String className;
    String name;
  }


  public static String[] menuKeys = new String[]{"CheckBoxMenuItem.acceleratorFont",
                                          "CheckBoxMenuItem.font",
                                          "Menu.acceleratorFont",
                                          "Menu.font",
                                          "MenuBar.font",
                                          "MenuItem.acceleratorFont",
                                          "MenuItem.font",
                                          "RadioButtonMenuItem.acceleratorFont",
                                          "RadioButtonMenuItem.font"};

  public static String[] componentsKeys =
                             new String[]{"Button.font",
                                          "CheckBox.font",
                                          "ColorChooser.font",
                                          "ComboBox.font",
                                          "InternalFrame.titleFont",
                                          "Label.font",
                                          "List.font",
                                          "OptionPane.font",
                                          "Panel.font",
                                          "PasswordField.font",
                                          "PopupMenu.font",
                                          "ProgressBar.font",
                                          "RadioButton.font",
                                          "ScrollPane.font",
                                          "TabbedPane.font",
                                          "Table.font",
                                          "TableHeader.font",
                                          "TextField.font",
                                          "TitledBorder.font",
                                          "ToggleButton.font",
                                          "ToolBar.font",
                                          "ToolTip.font",
                                          "Tree.font",
                                          "Viewport.font"};

  public static String[] textComponentsKeys =
                             new String[]{"EditorPane.font",
                                          "TextArea.font",
                                          "TextPane.font"};

  /**
   * The main tabbed pane
   */
  protected JTabbedPane mainTabbedPane;

  /**
   * Radio button used to set the font for text components
   */
  protected JRadioButton textBtn;
  
  /**
   * The OK button for the dialog. It's set as protected so it can be accessed
   * by subclasses in other projects.
   */
  protected JButton okButton;
  
  /**
   * The Cancel button for the dialog. It's set as protected so it can be 
   * accessed by subclasses in other projects.
   */
  protected JButton cancelButton;

  /**
   * which text is currently being edited; values are: "text", "menu", "other"
   */
  protected String selectedFontBtn = null;

  /**
   * Radio button used to set the font for menu components
   */
  protected JRadioButton menuBtn;

  /**
   * Radio button used to set the font for other components
   */
  protected JRadioButton otherCompsBtn;

  /**
   * Button group for the font setting radio buttons
   */
  protected ButtonGroup fontBG;

  /**
   * The font chooser used for selecting fonts
   */
  protected JFontChooser fontChooser;

  /**
   * The "Save Options on close" checkbox
   */
  protected JCheckBox saveOptionsChk;

  /**
   * The "Save Session on close" checkbox
   */
  protected JCheckBox saveSessionChk;

  /**
   * The "Include Annotation Features in Save Preserving Format" checkbox
   */
  protected JCheckBox includeFeaturesOnPreserveFormatChk;

  /**
   * The "Add extra space markup unpack if needed" checkbox
   */
  protected JCheckBox addSpaceOnMarkupUnpackChk;

  /**
   * The name of the look and feel class
   */
  protected String lookAndFeelClassName;

  /**
   * The font to be used for the menus; cached value for the one in the user
   * config map.
   */
  protected Font menusFont;

  /**
   * The font to be used for text components; cached value for the one in the
   * user config map.
   */
  protected Font textComponentsFont;

  /**
   * The font to be used for GUI components; cached value for the one in the
   * user config map.
   */
  protected Font componentsFont;

  /**
   * This flag becomes true when an GUI related option has been changed
   */
  protected boolean dirtyGUI;

  /**
   * The combobox for the look and feel selection
   */
  protected JComboBox<LNFData> lnfCombo;

  /**
   * List of browsers. Update the browserCommandLineTextField.
   */
  protected JComboBox<String> browserComboBox;

  /**
   * Browser command line.
   */
  protected JTextField browserCommandLineTextField;

  protected JCheckBox treeSelectViewChk;

  protected JCheckBox viewSelectTreeChk;

  private static OptionsMap userConfig = Gate.getUserConfig();
}
