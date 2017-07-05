/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  AnnotationDiffGUI.java
 *
 *  Valentin Tablan, 24-Jun-2004
 *
 *  $Id: AnnotationDiffGUI.java 19641 2016-10-06 07:24:25Z markagreenwood $
 */

package gate.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.BadLocationException;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.gui.docview.AnnotationSetsView;
import gate.gui.docview.TextualDocumentView;
import gate.resources.img.svg.AnnotationDiffIcon;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.AnnotationDiffer;
import gate.util.ExtensionFileFilter;
import gate.util.GateException;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;
import gate.util.Strings;

/**
 * Compare annotations in two annotation sets in one or two documents.
 *
 * Display a table with annotations compared side by side.
 * Annotations offsets and features can be edited by modifying cells.
 * Selected annotations can be copied to another annotation set.
 */
@SuppressWarnings("serial")
public class AnnotationDiffGUI extends JFrame{

  public AnnotationDiffGUI(String title){
    super(title);
   
    setIconImage((new AnnotationDiffIcon(64,64)).getImage());
    MainFrame.getGuiRoots().add(this);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    initLocalData();
    initGUI();
    initListeners();
    populateGUI();
  }

  /**
   * Set all the parameters and compute the differences.
   *
   * @param title title of the frame
   * @param keyDocumentName name of the key document
   * @param responseDocumentName name of the response document
   * @param keyAnnotationSetName key annotation set name, may be null
   * @param responseAnnotationSetName response annotation set name, may be null
   * @param annotationType annotation type, may be null
   * @param featureNames feature name, may be null
   */

  public AnnotationDiffGUI(String title,
    final String keyDocumentName, final String responseDocumentName,
    final String keyAnnotationSetName, final String responseAnnotationSetName,
    final String annotationType, final Set<String> featureNames){
    super(title);
    setIconImage(((ImageIcon)MainFrame.getIcon("annotation-diff")).getImage());
    MainFrame.getGuiRoots().add(this);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    initLocalData();
    initGUI();
    initListeners();
    populateGUI();

    // set programmatically the different settings
    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run() {
      keyDocCombo.setSelectedItem(keyDocumentName);
      resDocCombo.setSelectedItem(responseDocumentName);
      if (keyAnnotationSetName != null) {
        keySetCombo.setSelectedItem(keyAnnotationSetName);
      }
      if (responseAnnotationSetName != null) {
        resSetCombo.setSelectedItem(responseAnnotationSetName);
      }
      if (annotationType != null) {
        annTypeCombo.setSelectedItem(annotationType);
      }
      significantFeatures.clear();
      if (featureNames == null || featureNames.isEmpty()) {
        noFeaturesBtn.setSelected(true);
      } else {
        significantFeatures.addAll(featureNames);
        someFeaturesBtn.setSelected(true);
      }
      // compute differences automatically
      if (keyAnnotationSetName != null
       && responseAnnotationSetName != null
       && annotationType != null) {
        SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          // wait some time
          Date timeToRun = new Date(System.currentTimeMillis() + 1000);
          Timer timer = new Timer("Annotation diff init timer", true);
          timer.schedule(new TimerTask() {
            @Override
            public void run() {
              diffAction.actionPerformed(
                new ActionEvent(this, -1, "corpus quality"));
            }
          }, timeToRun);
        }});
      }
    }});
  }

  protected void initLocalData(){
    differ = new AnnotationDiffer();
    pairings = new ArrayList<AnnotationDiffer.Pairing>();
    keyCopyValueRows = new ArrayList<Boolean>();
    resCopyValueRows = new ArrayList<Boolean>();
    significantFeatures = new HashSet<String>();
    keyDoc = null;
    resDoc = null;
    Component root = SwingUtilities.getRoot(AnnotationDiffGUI.this);
    isStandalone = (root instanceof MainFrame);
  }

  protected void initGUI(){
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;

    /*******************************************
     * First row - Settings and 'Compare' button *
     *******************************************/

    constraints.gridy = 0;
    JLabel keyDocLabel = new JLabel("Key doc:");
    keyDocLabel.setToolTipText("Key document");
    getContentPane().add(keyDocLabel, constraints);
    keyDocCombo = new JComboBox<String>();
    keyDocCombo.setPrototypeDisplayValue("long_document_name");
    // add the value of the combobox in a tooltip for long value
    keyDocCombo.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(
          list, value, index, isSelected, cellHasFocus);
        if (value != null) {
          Rectangle textRectangle = new Rectangle(keyDocCombo.getSize().width,
            getPreferredSize().height);
          String shortText = SwingUtilities.layoutCompoundLabel(this,
            getFontMetrics(getFont()), value.toString(), null,
            getVerticalAlignment(), getHorizontalAlignment(),
            getHorizontalTextPosition(), getVerticalTextPosition(),
            textRectangle, new Rectangle(), textRectangle, getIconTextGap());
          if (shortText.equals(value)) { // no tooltip
            if (index == -1) {
              keyDocCombo.setToolTipText(null);
            } else {
              setToolTipText(null);
            }
          } else { // add tooltip because text is shortened
            if (index == -1) {
              keyDocCombo.setToolTipText(value.toString());
            } else {
              setToolTipText(value.toString());
            }
          }
        }
        return this;
      }
    });
    constraints.weightx = 3;
    getContentPane().add(keyDocCombo, constraints);
    constraints.weightx = 1;
    JLabel keySetLabel = new JLabel("Key set:");
    keySetLabel.setToolTipText("Key annotation set");
    getContentPane().add(keySetLabel, constraints);
    keySetCombo = new JComboBox<String>();
    keySetCombo.setPrototypeDisplayValue("long_set_name");
    getContentPane().add(keySetCombo, constraints);
    JLabel typeLabel = new JLabel("Type:");
    typeLabel.setToolTipText("Annotation type");
    getContentPane().add(typeLabel, constraints);
    annTypeCombo = new JComboBox<String>();
    annTypeCombo.setPrototypeDisplayValue("very_long_type");
    constraints.gridwidth = 3;
    getContentPane().add(annTypeCombo, constraints);
    constraints.gridwidth = 1;
    JLabel weightLabel = new JLabel("Weight");
    weightLabel.setToolTipText("F-measure weight");
    getContentPane().add(weightLabel, constraints);
    diffAction = new DiffAction();
    diffAction.setEnabled(false);
    doDiffBtn = new JButton(diffAction);
    doDiffBtn.setForeground((Color)
      UIManager.getDefaults().get("Button.disabledText"));
    doDiffBtn.setToolTipText("Choose two annotation sets "
            +"that have at least one annotation type in common.");
    constraints.gridheight = 2;
    getContentPane().add(doDiffBtn, constraints);
    constraints.gridheight = 1;

    /*************************
     * Second row - Settings *
     *************************/

    constraints.gridy++;
    constraints.gridx = 0;
    JLabel responseDocLabel = new JLabel("Resp. doc:");
    responseDocLabel.setToolTipText("Response document");
    getContentPane().add(responseDocLabel, constraints);
    constraints.gridx = GridBagConstraints.RELATIVE;
    resDocCombo = new JComboBox<String>();
    resDocCombo.setPrototypeDisplayValue("long_document_name");
    resDocCombo.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(
          list, value, index, isSelected, cellHasFocus);
        if (value != null) {
          Rectangle textRectangle = new Rectangle(resDocCombo.getSize().width,
            getPreferredSize().height);
          String shortText = SwingUtilities.layoutCompoundLabel(this,
            getFontMetrics(getFont()), value.toString(), null,
            getVerticalAlignment(), getHorizontalAlignment(),
            getHorizontalTextPosition(), getVerticalTextPosition(),
            textRectangle, new Rectangle(), textRectangle, getIconTextGap());
          if (shortText.equals(value)) { // no tooltip
            if (index == -1) {
              resDocCombo.setToolTipText(null);
            } else {
              setToolTipText(null);
            }
          } else { // add tooltip because text is shortened
            if (index == -1) {
              resDocCombo.setToolTipText(value.toString());
            } else {
              setToolTipText(value.toString());
            }
          }
        }
        return this;
      }
    });
    constraints.weightx = 3;
    getContentPane().add(resDocCombo, constraints);
    constraints.weightx = 1;
    JLabel responseSetLabel = new JLabel("Resp. set:");
    responseSetLabel.setToolTipText("Response annotation set");
    getContentPane().add(responseSetLabel, constraints);
    resSetCombo = new JComboBox<String>();
    resSetCombo.setPrototypeDisplayValue("long_set_name");
    getContentPane().add(resSetCombo, constraints);
    getContentPane().add(new JLabel("Features:"), constraints);
    ButtonGroup btnGrp = new ButtonGroup();
    allFeaturesBtn = new JRadioButton("all");
    allFeaturesBtn.setOpaque(false);
    allFeaturesBtn.setMargin(new Insets(0, 0, 0, 1));
    allFeaturesBtn.setIconTextGap(1);
    btnGrp.add(allFeaturesBtn);
    constraints.insets = new Insets(0, 0, 0, 0);
    getContentPane().add(allFeaturesBtn, constraints);
    someFeaturesBtn = new JRadioButton("some");
    someFeaturesBtn.setOpaque(false);
    someFeaturesBtn.setMargin(new Insets(0, 0, 0, 1));
    someFeaturesBtn.setIconTextGap(1);
    btnGrp.add(someFeaturesBtn);
    getContentPane().add(someFeaturesBtn, constraints);
    noFeaturesBtn = new JRadioButton("none");
    noFeaturesBtn.setOpaque(false);
    noFeaturesBtn.setMargin(new Insets(0, 0, 0, 1));
    noFeaturesBtn.setIconTextGap(1);
    btnGrp.add(noFeaturesBtn);
    getContentPane().add(noFeaturesBtn, constraints);
    constraints.insets = new Insets(2, 2, 2, 2);
    noFeaturesBtn.setSelected(true);
    weightTxt = new JTextField("1.0");
    weightTxt.setToolTipText(
      "<html>Relative weight of precision and recall aka beta." +
      "<ul><li>1 weights equally precision and recall" +
      "<li>0.5 weights precision twice as much as recall" +
      "<li>2 weights recall twice as much as precision</ul></html>");
    constraints.fill = GridBagConstraints.HORIZONTAL;
    getContentPane().add(weightTxt, constraints);
    constraints.fill = GridBagConstraints.NONE;

    /********************************
     * Third row - Comparison table *
     ********************************/

    constraints.gridy++;
    constraints.gridx = 0;
    diffTableModel = new DiffTableModel();
    diffTable = new XJTable(diffTableModel);
    diffTable.setDefaultRenderer(String.class, new DiffTableCellRenderer());
    diffTable.setDefaultRenderer(Boolean.class, new DiffTableCellRenderer());
    diffTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    diffTable.setComparator(DiffTableModel.COL_MATCH, new Comparator<String>(){
      @Override
      public int compare(String label1, String label2){
        int match1 = 0;
        while(!label1.equals(matchLabel[match1])) match1++;
        int match2 = 0;
        while(!label2.equals(matchLabel[match2])) match2++;

        return match1 - match2;
      }
    });
    diffTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    diffTable.setRowSelectionAllowed(true);
    diffTable.setColumnSelectionAllowed(true);
    diffTable.setEnableHidingColumns(true);
    diffTable.hideColumn(DiffTableModel.COL_KEY_COPY);
    diffTable.hideColumn(DiffTableModel.COL_RES_COPY);

    Comparator<String> startEndComparator = new Comparator<String>() {
      @Override
      public int compare(String no1, String no2) {
        if (no1.trim().equals("") && no2.trim().equals("")) {
          return 0;
        }
        else if (no1.trim().equals("")) {
          return -1;
        }
        else if (no2.trim().equals("")) {
          return 1;
        }
        int n1 = Integer.parseInt(no1);
        int n2 = Integer.parseInt(no2);
        if(n1 == n2)
          return 0;
        else if(n1 > n2)
          return 1;
        else
          return -1;
      }
    };

    diffTable.setComparator(DiffTableModel.COL_KEY_START, startEndComparator);
    diffTable.setComparator(DiffTableModel.COL_KEY_END, startEndComparator);
    diffTable.setComparator(DiffTableModel.COL_RES_START, startEndComparator);
    diffTable.setComparator(DiffTableModel.COL_RES_END, startEndComparator);

    diffTable.setSortable(true);
    diffTable.setSortedColumn(DiffTableModel.COL_MATCH);
    diffTable.setAscending(true);
    scroller = new JScrollPane(diffTable);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    getContentPane().add(scroller, constraints);
    constraints.gridwidth = 1;
    constraints.weightx = 0;
    constraints.weighty = 0;
    constraints.fill = GridBagConstraints.NONE;

    /*************************************************
     * Fourth row - Tabbed panes, status and buttons *
     *************************************************/

    bottomTabbedPane = new JTabbedPane(
      JTabbedPane.BOTTOM, JTabbedPane.WRAP_TAB_LAYOUT);

    // statistics pane will be added to the bottom tabbed pane
    statisticsPane = new JPanel(new GridBagLayout());
    GridBagConstraints constraints2 = new GridBagConstraints();
    constraints2.insets = new Insets(2, 2, 2, 2);

    // first column
    constraints2.gridx = 0;
    constraints2.anchor = GridBagConstraints.WEST;
    JLabel lbl = new JLabel("Correct:");
    lbl.setToolTipText("Correct:");
    lbl.setBackground(diffTable.getBackground());
    statisticsPane.add(lbl, constraints2);
    lbl = new JLabel("Partially correct:");
    lbl.setToolTipText("Partially correct:");
    lbl.setBackground(PARTIALLY_CORRECT_BG);
    lbl.setOpaque(true);
    statisticsPane.add(lbl, constraints2);
    lbl = new JLabel("Missing:");
    lbl.setToolTipText("Missing:");
    lbl.setBackground(MISSING_BG);
    lbl.setOpaque(true);
    statisticsPane.add(lbl, constraints2);
    lbl = new JLabel("False positives:");
    lbl.setToolTipText("False positives:");
    lbl.setBackground(FALSE_POSITIVE_BG);
    lbl.setOpaque(true);
    statisticsPane.add(lbl, constraints2);

    // second column
    constraints2.gridx++;
    correctLbl = new JLabel("0");
    statisticsPane.add(correctLbl, constraints2);
    partiallyCorrectLbl = new JLabel("0");
    statisticsPane.add(partiallyCorrectLbl, constraints2);
    missingLbl = new JLabel("0");
    statisticsPane.add(missingLbl, constraints2);
    falsePozLbl = new JLabel("0");
    statisticsPane.add(falsePozLbl, constraints2);

    // third column
    constraints2.gridx++;
    constraints2.insets = new Insets(4, 30, 4, 4);
    statisticsPane.add(Box.createGlue());
    lbl = new JLabel("Strict:");
    statisticsPane.add(lbl, constraints2);
    lbl = new JLabel("Lenient:");
    statisticsPane.add(lbl, constraints2);
    lbl = new JLabel("Average:");
    statisticsPane.add(lbl, constraints2);

    // fourth column
    constraints2.gridx++;
    constraints2.insets = new Insets(4, 4, 4, 4);
    lbl = new JLabel("Recall");
    statisticsPane.add(lbl, constraints2);
    recallStrictLbl = new JLabel("0.00");
    statisticsPane.add(recallStrictLbl, constraints2);
    recallLenientLbl = new JLabel("0.00");
    statisticsPane.add(recallLenientLbl, constraints2);
    recallAveLbl = new JLabel("0.00");
    statisticsPane.add(recallAveLbl, constraints2);

    // fifth column
    constraints2.gridx++;
    lbl = new JLabel("Precision");
    statisticsPane.add(lbl, constraints2);
    precisionStrictLbl = new JLabel("0.00");
    statisticsPane.add(precisionStrictLbl, constraints2);
    precisionLenientLbl = new JLabel("0.00");
    statisticsPane.add(precisionLenientLbl, constraints2);
    precisionAveLbl = new JLabel("0.00");
    statisticsPane.add(precisionAveLbl, constraints2);

    // sixth column
    constraints2.gridx++;
    lbl = new JLabel("F-measure");
    lbl.setToolTipText("<html>F-measure =<br>" +
      "   ((weight² + 1) * precision * recall)<br>" +
      " / (weight² * precision + recall))</html>");
    statisticsPane.add(lbl, constraints2);
    fmeasureStrictLbl = new JLabel("0.00");
    statisticsPane.add(fmeasureStrictLbl, constraints2);
    fmeasureLenientLbl = new JLabel("0.00");
    statisticsPane.add(fmeasureLenientLbl, constraints2);
    fmeasureAveLbl = new JLabel("0.00");
    statisticsPane.add(fmeasureAveLbl, constraints2);

    bottomTabbedPane.add("Statistics", statisticsPane);

    // adjudication pane will be added to the bottom tabbed pane
    JPanel adjudicationPane = new JPanel(new GridBagLayout());
    constraints2 = new GridBagConstraints();
    constraints2.insets = new Insets(2, 2, 2, 2);

    // first row
    constraints2.gridy = 0;
    adjudicationPane.add(new JLabel("Target set:"), constraints2);
    consensusASTextField = new JTextField("consensus", 15);
    consensusASTextField.setToolTipText(
      "Annotation set name where to copy the selected annotations");
    adjudicationPane.add(consensusASTextField, constraints2);

    // second row
    constraints2.gridy++;
    constraints2.gridx = 0;
    copyToTargetSetAction = new CopyToTargetSetAction();
    copyToTargetSetAction.setEnabled(false);
    copyToConsensusBtn = new JButton(copyToTargetSetAction);
    constraints2.gridwidth = 2;
    adjudicationPane.add(copyToConsensusBtn, constraints2);

    bottomTabbedPane.add("Adjudication", adjudicationPane);

    JPanel bottomPanel = new JPanel(new GridBagLayout());
    constraints2 = new GridBagConstraints();
    constraints2.insets = new Insets(2, 2, 2, 2);

    // add the bottom tabbed pane to the bottom panel
    constraints2.gridx = 0;
    constraints2.gridy = 0;
    constraints2.gridheight = 3;
    constraints2.anchor = GridBagConstraints.WEST;
    bottomPanel.add(bottomTabbedPane, constraints2);
    constraints2.gridheight = 1;

    // status bar
    constraints2.gridx++;
    statusLabel = new JLabel();
    statusLabel.setBackground(diffTable.getBackground());
    constraints2.insets = new Insets(2, 6, 6, 2);
    bottomPanel.add(statusLabel, constraints2);

    // toolbar
    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);
    if (!isStandalone) {
      showDocumentAction = new ShowDocumentAction();
      showDocumentAction.setEnabled(false);
      showDocumentBtn = new JButton(showDocumentAction);
      showDocumentBtn.setToolTipText("Use first the \"Compare\"" +
        " button then select an annotation in the table.");
      toolbar.add(showDocumentBtn);
    }
    htmlExportAction = new HTMLExportAction();
    htmlExportAction.setEnabled(false);
    htmlExportBtn = new JButton(htmlExportAction);
    htmlExportBtn.setToolTipText("Use first the \"Compare\" button.");
    toolbar.add(htmlExportBtn);
    if (!isStandalone) {
      toolbar.add(new HelpAction());
    }
    constraints2.gridy++;
    constraints2.fill = GridBagConstraints.NONE;
    constraints2.anchor = GridBagConstraints.NORTHWEST;
    bottomPanel.add(toolbar, constraints2);

    // progress bar
    progressBar = new JProgressBar();
    progressBar.setMinimumSize(new Dimension(5, 5));
    progressBar.setBackground(diffTable.getBackground());
    progressBar.setOpaque(true);
    constraints2.gridy++;
    constraints2.anchor = GridBagConstraints.SOUTHWEST;
    bottomPanel.add(progressBar, constraints2);

    // add the bottom panel to the fourth row
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.gridheight = GridBagConstraints.REMAINDER;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(0, 0, 0, 0);
    getContentPane().add(bottomPanel, constraints);
    constraints.insets = new Insets(2, 2, 2, 2);

    // set the background colour
    Color background = diffTable.getBackground();
    getContentPane().setBackground(background);
    scroller.setBackground(background);
    scroller.getViewport().setBackground(background);
    statisticsPane.setBackground(background);
    adjudicationPane.setBackground(background);
    bottomPanel.setBackground(background);
    bottomTabbedPane.setBackground(background);

    featureslistModel = new DefaultListModel<String>();
    featuresList = new JList<String>(featureslistModel);
    featuresList.setSelectionMode(
      ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    keySetCombo.requestFocusInWindow();
  }

  protected void initListeners(){

    addWindowListener(new WindowAdapter(){
      @Override
      public void windowClosing(WindowEvent e) {
        new CloseAction().actionPerformed(null);
      }
    });

    addWindowFocusListener(new WindowAdapter(){
      @Override
      public void windowGainedFocus(WindowEvent e) {
        populateGUI();
      }
    });

    keyDocCombo.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        int keyDocSelectedIndex = keyDocCombo.getSelectedIndex();
        if (keyDocSelectedIndex == -1) { return; }
        Document newDoc = (Document) documents.get(keyDocSelectedIndex);
        if (keyDoc == newDoc) { return; }
        pairings.clear();
        diffTableModel.fireTableDataChanged();
        copyToTargetSetAction.setEnabled(false);
        keyDoc = newDoc;
        keySets = new ArrayList<AnnotationSet>();
        List<String> keySetNames = new ArrayList<String>();
        keySets.add(keyDoc.getAnnotations());
        keySetNames.add("[Default set]");
        if(keyDoc.getNamedAnnotationSets() != null) {
          for (Object o : keyDoc.getNamedAnnotationSets().keySet()) {
            String name = (String) o;
            keySetNames.add(name);
            keySets.add(keyDoc.getAnnotations(name));
          }
        }
        keySetCombo.setModel(new DefaultComboBoxModel<String>(keySetNames.toArray(new String[keySetNames.size()])));
        if(!keySetNames.isEmpty()) {
          keySetCombo.setSelectedIndex(0);
          if(resSetCombo.getItemCount() > 0) {
            // find first annotation set with annotation type in common
            for(int res = 0; res < resSetCombo.getItemCount(); res++) {
              resSetCombo.setSelectedIndex(res);
              for(int key = 0; key < keySetCombo.getItemCount(); key++) {
                if (keyDoc.equals(resDoc)
                 && resSetCombo.getItemAt(res).equals(
                    keySetCombo.getItemAt(key))) {
                  continue; // same document, skip it
                }
                keySetCombo.setSelectedIndex(key);
                if (annTypeCombo.getSelectedItem() != null) { break; }
              }
              if (annTypeCombo.getSelectedItem() != null) { break; }
            }
            if (annTypeCombo.getSelectedItem() == null) {
              statusLabel.setText("There is no annotation type in common.");
              statusLabel.setForeground(Color.RED);
            } else if (statusLabel.getText().equals(
                "There is no annotation type in common.")) {
              statusLabel.setText("The first annotation sets with" +
                " annotation type in common have been automatically selected.");
              statusLabel.setForeground(Color.BLACK);
            }
          }
        }
      }
    });

    resDocCombo.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        int resDocSelectedIndex = resDocCombo.getSelectedIndex();
        if (resDocSelectedIndex == -1) { return; }
        Document newDoc = (Document) documents.get(resDocSelectedIndex);
        if (resDoc == newDoc) { return; }
        resDoc = newDoc;
        pairings.clear();
        diffTableModel.fireTableDataChanged();
        copyToTargetSetAction.setEnabled(false);
        resSets = new ArrayList<AnnotationSet>();
        List<String> resSetNames = new ArrayList<String>();
        resSets.add(resDoc.getAnnotations());
        resSetNames.add("[Default set]");
        if(resDoc.getNamedAnnotationSets() != null) {
          for (Object o : resDoc.getNamedAnnotationSets().keySet()) {
            String name = (String) o;
            resSetNames.add(name);
            resSets.add(resDoc.getAnnotations(name));
          }
        }
        resSetCombo.setModel(new DefaultComboBoxModel<String>(resSetNames.toArray(new String[resSetNames.size()])));
        if(!resSetNames.isEmpty()) {
          resSetCombo.setSelectedIndex(0);
          if(keySetCombo.getItemCount() > 0) {
            // find annotation sets with annotations in common
            for(int res = 0; res < resSetCombo.getItemCount(); res++) {
              resSetCombo.setSelectedIndex(res);
              for(int key = 0; key < keySetCombo.getItemCount(); key++) {
                if (keyDoc.equals(resDoc)
                 && resSetCombo.getItemAt(res).equals(
                    keySetCombo.getItemAt(key))) {
                  continue;
                }
                keySetCombo.setSelectedIndex(key);
                if (annTypeCombo.getSelectedItem() != null) { break; }
              }
              if (annTypeCombo.getSelectedItem() != null) { break; }
            }
            if (annTypeCombo.getSelectedItem() == null) {
              statusLabel.setText("There is no annotations in common.");
              statusLabel.setForeground(Color.RED);
            } else if (statusLabel.getText().equals(
              "There is no annotations in common.")) {
              statusLabel.setText("The first annotation sets with" +
                " annotations in common have been selected.");
              statusLabel.setForeground(Color.BLACK);
            }
          }
        }
      }
    });

    /**
     * This populates the types combo when set selection changes
     */
    ActionListener setComboActionListener = new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        keySet = keySets == null || keySets.isEmpty()?
                 null : keySets.get(keySetCombo.getSelectedIndex());
        resSet = resSets == null || resSets.isEmpty()?
                null : resSets.get(resSetCombo.getSelectedIndex());
        Set<String> keyTypes = (keySet == null || keySet.isEmpty()) ?
                new HashSet<String>() : keySet.getAllTypes();
        Set<String> resTypes = (resSet == null || resSet.isEmpty()) ?
                new HashSet<String>() : resSet.getAllTypes();
        Set<String> types = new HashSet<String>(keyTypes);
        types.retainAll(resTypes);
        List<String> typesList = new ArrayList<String>(types);
        Collections.sort(typesList);
        annTypeCombo.setModel(new DefaultComboBoxModel<String>(typesList.toArray(new String[typesList.size()])));
        if(typesList.size() > 0) {
          annTypeCombo.setSelectedIndex(0);
          diffAction.setEnabled(true);
          doDiffBtn.setForeground((Color)
            UIManager.getDefaults().get("Button.foreground"));
          doDiffBtn.setToolTipText(
                  (String)diffAction.getValue(Action.SHORT_DESCRIPTION));
        } else {
          diffAction.setEnabled(false);
          doDiffBtn.setForeground((Color)
            UIManager.getDefaults().get("Button.disabledText"));
          doDiffBtn.setToolTipText("Choose two annotation sets "
                  +"that have at least one annotation type in common.");
        }
      }
    };
    keySetCombo.addActionListener(setComboActionListener);

    resSetCombo.addActionListener(setComboActionListener);

    someFeaturesBtn.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent evt){
        if(someFeaturesBtn.isSelected()){
          if(keySet == null || keySet.isEmpty() ||
                  annTypeCombo.getSelectedItem() == null) return;
          Iterator<Annotation> annIter = keySet.
              get((String)annTypeCombo.getSelectedItem()).iterator();
          Set<String> featureSet = new HashSet<String>();
          while(annIter.hasNext()){
            Annotation ann = annIter.next();
            Map<Object, Object> someFeatures = ann.getFeatures();
            if (someFeatures == null) { continue; }
            for (Object feature : someFeatures.keySet()) {
              featureSet.add((String) feature);
            }
          }
          List<String> featureList = new ArrayList<String>(featureSet);
          Collections.sort(featureList);
          featureslistModel.clear();
          Iterator<String> featIter = featureList.iterator();
          int index = 0;
          while(featIter.hasNext()){
            String aFeature = featIter.next();
            featureslistModel.addElement(aFeature);
            if(significantFeatures.contains(aFeature))
              featuresList.addSelectionInterval(index, index);
            index ++;
          }
           int ret = JOptionPane.showConfirmDialog(AnnotationDiffGUI.this,
                  new JScrollPane(featuresList),
                  "Select features",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
           if(ret == JOptionPane.OK_OPTION){
             significantFeatures.clear();
             int[] selIdxs = featuresList.getSelectedIndices();
             for (int selIdx : selIdxs) {
               significantFeatures.add(featureslistModel.get(selIdx));
             }
           }
        }
      }
    });

    // enable/disable buttons according to the table state
    diffTableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(javax.swing.event.TableModelEvent e) {
        if (diffTableModel.getRowCount() > 0) {
          htmlExportAction.setEnabled(true);
          htmlExportBtn.setToolTipText((String)
            htmlExportAction.getValue(Action.SHORT_DESCRIPTION));
          showDocumentAction.setEnabled(true);
          showDocumentBtn.setToolTipText((String)
            showDocumentAction.getValue(Action.SHORT_DESCRIPTION));
        } else {
          htmlExportAction.setEnabled(false);
          htmlExportBtn.setToolTipText("Use first the \"Compare\" button.");
          showDocumentAction.setEnabled(false);
          showDocumentBtn.setToolTipText("Use first the \"Compare\""
            + " button then select an annotation in the table.");
        }
      }
    });

    // enable/disable buttons according to the table selection
    diffTable.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          int row = diffTable.rowViewToModel(diffTable.getSelectedRow());
          if (row == -1) { showDocumentAction.setEnabled(false); return; }
          AnnotationDiffer.Pairing pairing = pairings.get(row);
          Annotation key = pairing.getKey();
          Annotation response = pairing.getResponse();
          int column = diffTable.convertColumnIndexToModel(
            diffTable.getSelectedColumn());
          boolean enabled;
          switch(column){
            case DiffTableModel.COL_KEY_START:
            case DiffTableModel.COL_KEY_END:
            case DiffTableModel.COL_KEY_STRING:
            case DiffTableModel.COL_KEY_FEATURES:
              enabled = (key != null); break;
            case DiffTableModel.COL_RES_START:
            case DiffTableModel.COL_RES_END:
            case DiffTableModel.COL_RES_STRING:
            case DiffTableModel.COL_RES_FEATURES:
              enabled = (response != null); break;
            default: enabled = false;
          }
          showDocumentAction.setEnabled(enabled);
        }
      });

    // enable/disable buttons according to the table selection
    diffTable.getColumnModel().addColumnModelListener(
      new TableColumnModelListener() {
        @Override
        public void columnAdded(TableColumnModelEvent e) { /* do nothing */ }
        @Override
        public void columnRemoved(TableColumnModelEvent e) { /* do nothing */ }
        @Override
        public void columnMoved(TableColumnModelEvent e) { /* do nothing */ }
        @Override
        public void columnMarginChanged(ChangeEvent e) { /* do nothing */ }
        @Override
        public void columnSelectionChanged(ListSelectionEvent e) {
          int row = diffTable.rowViewToModel(diffTable.getSelectedRow());
          if (row == -1) { showDocumentAction.setEnabled(false); return; }
          AnnotationDiffer.Pairing pairing = pairings.get(row);
          Annotation key = pairing.getKey();
          Annotation response = pairing.getResponse();
          int column = diffTable.convertColumnIndexToModel(
            diffTable.getSelectedColumn());
          boolean enabled;
          switch(column){
            case DiffTableModel.COL_KEY_START:
            case DiffTableModel.COL_KEY_END:
            case DiffTableModel.COL_KEY_STRING:
            case DiffTableModel.COL_KEY_FEATURES:
              enabled = (key != null); break;
            case DiffTableModel.COL_RES_START:
            case DiffTableModel.COL_RES_END:
            case DiffTableModel.COL_RES_STRING:
            case DiffTableModel.COL_RES_FEATURES:
              enabled = (response != null); break;
            default: enabled = false;
          }
          showDocumentAction.setEnabled(enabled);
        }
      });

    // inverse state of selected checkboxes when Space key is pressed
    diffTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_SPACE
          || !(diffTable.isColumnSelected(DiffTableModel.COL_KEY_COPY)
            || diffTable.isColumnSelected(DiffTableModel.COL_RES_COPY))) {
          return;
        }
        e.consume(); // disable normal behavior of Space key in a table
        int[] cols = {DiffTableModel.COL_KEY_COPY, DiffTableModel.COL_RES_COPY};
        for (int col : cols) {
          for (int row : diffTable.getSelectedRows()) {
            if (diffTable.isCellSelected(row, col)
             && diffTable.isCellEditable(row, col)) {
              diffTable.setValueAt(
                !(Boolean)diffTable.getValueAt(row, col), row, col);
              diffTableModel.fireTableCellUpdated(row, col);
            }
          }
        }
      }
    });

    // context menu for the check boxes to easily change their state
    diffTable.addMouseListener(new MouseAdapter() {
      private JPopupMenu mousePopup;
      JMenuItem menuItem;
      @Override
      public void mousePressed(MouseEvent e) {
        showContextMenu(e);
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        showContextMenu(e);
      }
      private void showContextMenu(MouseEvent e) {
        int col = diffTable.convertColumnIndexToModel(
          diffTable.columnAtPoint(e.getPoint()));
        if (!e.isPopupTrigger()
        || ( col != DiffTableModel.COL_KEY_COPY
          && col != DiffTableModel.COL_RES_COPY) ) {
          return;
        }
        mousePopup = new JPopupMenu();
        for (final String tick : new String[] {"Tick", "Untick"}) {
          menuItem = new JMenuItem(tick + " selected check boxes");
          menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              int keyCol = diffTable.convertColumnIndexToView(
                  DiffTableModel.COL_KEY_COPY);
              int responseCol = diffTable.convertColumnIndexToView(
                  DiffTableModel.COL_RES_COPY);
              for (int row = 0; row < diffTable.getRowCount(); row++) {
                int rowModel = diffTable.rowViewToModel(row);
                AnnotationDiffer.Pairing pairing = pairings.get(rowModel);
                if (diffTable.isCellSelected(row, keyCol)
                 && pairing.getKey() !=  null) {
                  diffTable.setValueAt(tick.equals("Tick"), row, keyCol);
                }
                if (diffTable.isCellSelected(row, responseCol)
                 && pairing.getResponse() !=  null) {
                  diffTable.setValueAt(tick.equals("Tick"), row, responseCol);
                }
              }
              diffTableModel.fireTableDataChanged();
            }
          });
          mousePopup.add(menuItem);
        }
        mousePopup.addSeparator();
        String[] types = new String[] {"correct", "partially correct", "missing",
          "false positives", "mismatch"};
        String[] symbols = new String[] {"=", "~", "-?", "?-", "<>"};
        for (int i = 0; i < types.length; i++) {
          menuItem = new JMenuItem("Tick "+ types[i] + " annotations");
          final String symbol = symbols[i];
          menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              int matchCol = diffTable.convertColumnIndexToView(
                  DiffTableModel.COL_MATCH);
              for (int row = 0; row < diffTable.getRowCount(); row++) {
                int rowModel = diffTable.rowViewToModel(row);
                AnnotationDiffer.Pairing pairing = pairings.get(rowModel);
                if (diffTable.getValueAt(row, matchCol).equals(symbol)
                 && pairing.getKey() !=  null) {
                  keyCopyValueRows.set(diffTable.rowViewToModel(row), true);
                } else if (diffTable.getValueAt(row, matchCol).equals(symbol)
                 && pairing.getResponse() !=  null) {
                  resCopyValueRows.set(diffTable.rowViewToModel(row), true);
                }
              }
              diffTableModel.fireTableDataChanged();
            }
          });
          mousePopup.add(menuItem);
        }
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    });

    // revert to default name if the field is empty and lost focus
    consensusASTextField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        String target = consensusASTextField.getText().trim();
        if (target.length() == 0) {
          consensusASTextField.setText("consensus");
        }
        if (keyDoc != null
         && keyDoc.getAnnotationSetNames().contains(target)) {
          statusLabel.setText("Be careful, the annotation set " + target
            + " already exists.");
          statusLabel.setForeground(Color.RED);
        }
      }
    });

    bottomTabbedPane.addChangeListener(new ChangeListener(){
      @Override
      public void stateChanged(ChangeEvent e) {
        if (bottomTabbedPane.getSelectedIndex() == 0) {
          diffTable.hideColumn(DiffTableModel.COL_KEY_COPY);
          diffTable.hideColumn(DiffTableModel.COL_RES_COPY);
        } else {
          int middleIndex = Math.round(diffTable.getColumnCount() / 2);
          diffTable.showColumn(DiffTableModel.COL_KEY_COPY, middleIndex);
          diffTable.showColumn(DiffTableModel.COL_RES_COPY, middleIndex + 2);
          diffTable.getColumnModel().getColumn(DiffTableModel.COL_KEY_COPY)
            .setPreferredWidth(10);
          diffTable.getColumnModel().getColumn(DiffTableModel.COL_RES_COPY)
            .setPreferredWidth(10);
          diffTable.doLayout();
        }
      }
    });

    // define keystrokes action bindings at the level of the main window
    InputMap inputMap = ((JComponent)this.getContentPane()).
      getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = ((JComponent)this.getContentPane()).getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("F1"), "Help");
    actionMap.put("Help", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new HelpAction().actionPerformed(null);
      }
    });
  }

  @Override
  public void pack(){
    super.pack();
    // add some vertical space for the table
    setSize(getWidth(), getHeight() + 300);
  }

  protected void populateGUI(){
    try{
      documents = Gate.getCreoleRegister().getAllInstances("gate.Document");
    }catch(GateException ge){
      throw new GateRuntimeException(ge);
    }
    List<String> documentNames = new ArrayList<String>(documents.size());
    for(Resource document : documents) {
      documentNames.add(document.getName());
    }
    Object keyDocSelectedItem = keyDocCombo.getSelectedItem();
    Object resDocSelectedItem = resDocCombo.getSelectedItem();
    keyDocCombo.setModel(new DefaultComboBoxModel<String>(documentNames.toArray(new String[documentNames.size()])));
    resDocCombo.setModel(new DefaultComboBoxModel<String>(documentNames.toArray(new String[documentNames.size()])));
    if(!documents.isEmpty()){
      keyDocCombo.setSelectedItem(keyDocSelectedItem);
      if (keyDocCombo.getSelectedIndex() == -1) {
        keyDocCombo.setSelectedIndex(0);
      }
      resDocCombo.setSelectedItem(resDocSelectedItem);
      if (resDocCombo.getSelectedIndex() == -1) {
        resDocCombo.setSelectedIndex(0);
      }
      statusLabel.setText(documents.size() + " documents loaded");
      if (annTypeCombo.getSelectedItem() == null) {
        statusLabel.setText(statusLabel.getText() +
          ". Choose two annotation sets to compare.");
      }
      statusLabel.setForeground(Color.BLACK);
    } else {
      statusLabel.setText("You must load at least one document.");
      statusLabel.setForeground(Color.RED);
    }
  }

  protected class DiffAction extends AbstractAction{
    public DiffAction(){
      super("Compare");
      putValue(SHORT_DESCRIPTION,
        "Compare annotations between key and response sets");
      putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
      putValue(SMALL_ICON, MainFrame.getIcon("Run"));
    }

    @Override
    public void actionPerformed(ActionEvent evt){
      final int rowView = diffTable.getSelectedRow();
      final int colView = diffTable.getSelectedColumn();
      final int id = evt.getID();
      final String command = evt.getActionCommand();

      // waiting animations
      progressBar.setIndeterminate(true);
      getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      // compute the differences
      Runnable runnable = new Runnable() { @Override
      public void run() {
        Set<Annotation> keys = new HashSet<Annotation>(
          keySet.get((String)annTypeCombo.getSelectedItem()));
        Set<Annotation> responses = new HashSet<Annotation>(
          resSet.get((String)annTypeCombo.getSelectedItem()));
        int countHidden = 0;
        if (bottomTabbedPane.getSelectedIndex() == 1) { // adjudication mode
          for (Annotation annotation : new ArrayList<Annotation>(keys)) {
            if (annotation.getFeatures().containsKey("anndiffstep")) {
              keys.remove(annotation); // previously copied
              countHidden++;
            }
          }
          for (Annotation annotation : new ArrayList<Annotation>(responses)) {
            if (annotation.getFeatures().containsKey("anndiffstep")) {
              responses.remove(annotation); // previously copied
              countHidden++;
            }
          }
        }
        if(someFeaturesBtn.isSelected())
          differ.setSignificantFeaturesSet(significantFeatures);
        else if(allFeaturesBtn.isSelected())
          differ.setSignificantFeaturesSet(null);
        else differ.setSignificantFeaturesSet(new HashSet<String>());
        pairings.clear();
        pairings.addAll(differ.calculateDiff(keys, responses));
        keyCopyValueRows.clear();
        keyCopyValueRows.addAll(Collections.nCopies(pairings.size(), false));
        resCopyValueRows.clear();
        resCopyValueRows.addAll(Collections.nCopies(pairings.size(), false));
        if (command.equals("setvalue")) {
          // tick check boxes for annotations previously modified
          int row = 0;
          for (AnnotationDiffer.Pairing pairing : pairings) {
            if (pairing.getKey() != null
            && pairing.getKey().getFeatures().containsKey("anndiffmodified")) {
              keyCopyValueRows.set(row, true);
            }
            if (pairing.getResponse() != null
            && pairing.getResponse().getFeatures().containsKey("anndiffmodified")) {
              resCopyValueRows.set(row, true);
            }
            row++;
          }
        }

        final int countHiddenF = countHidden;
        SwingUtilities.invokeLater(new Runnable(){ @Override
        public void run(){
        // update the GUI
        diffTableModel.fireTableDataChanged();
        correctLbl.setText(Integer.toString(differ.getCorrectMatches()));
        partiallyCorrectLbl.setText(
                Integer.toString(differ.getPartiallyCorrectMatches()));
        missingLbl.setText(Integer.toString(differ.getMissing()));
        falsePozLbl.setText(Integer.toString(differ.getSpurious()));
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(2);
        f.setMinimumFractionDigits(2);
        recallStrictLbl.setText(f.format(differ.getRecallStrict()));
        recallLenientLbl.setText(f.format(differ.getRecallLenient()));
        recallAveLbl.setText(f.format(differ.getRecallAverage()));
        precisionStrictLbl.setText(f.format(differ.getPrecisionStrict()));
        precisionLenientLbl.setText(f.format(differ.getPrecisionLenient()));
        precisionAveLbl.setText(f.format(differ.getPrecisionAverage()));
        double weight = Double.parseDouble(weightTxt.getText());
        fmeasureStrictLbl.setText(f.format(differ.getFMeasureStrict(weight)));
        fmeasureLenientLbl.setText(f.format(differ.getFMeasureLenient(weight)));
        fmeasureAveLbl.setText(f.format(differ.getFMeasureAverage(weight)));
        copyToTargetSetAction.setEnabled(keyDoc.equals(resDoc));
        if (keyDoc.equals(resDoc)) {
          copyToConsensusBtn.setToolTipText((String)
            copyToTargetSetAction.getValue(Action.SHORT_DESCRIPTION));
        } else {
          copyToConsensusBtn.setToolTipText(
            "Key and response document must be the same");
        }
        if (!command.equals("setvalue") && !command.equals("copy")) {
          statusLabel.setText(pairings.size() + " pairings have been found " +
            "(" + countHiddenF + " annotations are hidden)");
          statusLabel.setForeground(Color.BLACK);
        }
        diffTable.requestFocusInWindow();
        // stop waiting animations
        progressBar.setIndeterminate(false);
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        showDocumentAction.setEnabled(false);

        if (!command.equals("setvalue") && !command.equals("copy")) { return; }

        SwingUtilities.invokeLater(new Runnable(){ @Override
        public void run(){
          if (command.equals("setvalue")) {
            // select the cell containing the previously selected annotation
            for (int row = 0; row < diffTable.getRowCount(); row++) {
              AnnotationDiffer.Pairing pairing =
                pairings.get(diffTable.rowViewToModel(row));
              if ((pairing.getKey() != null
                 && pairing.getKey().getId() == id)
               || (pairing.getResponse() != null
                 && pairing.getResponse().getId() == id)) {
                diffTable.changeSelection(row, colView, false, false);
                break;
              }
            }
          } else if (command.equals("copy")) {
            // select the previously selected cell
             diffTable.changeSelection(rowView, colView, false, false);
          }
          SwingUtilities.invokeLater(new Runnable(){ @Override
          public void run(){
            diffTable.scrollRectToVisible(diffTable.getCellRect(
              diffTable.getSelectedRow(), diffTable.getSelectedColumn(), true));
          }});
        }});
        }});
      }};
      Thread thread = new Thread(runnable, "DiffAction");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }
  }

  /**
   * Copy selected annotations to the target annotation set.
   */
  protected class CopyToTargetSetAction extends AbstractAction {
    public CopyToTargetSetAction(){
      super("Copy selection to target set");
      putValue(SHORT_DESCRIPTION,
        "<html>Move selected annotations to the target annotation set" +
          "<br>and hide their paired annotations if not moved." +
          "&nbsp;&nbsp;<font color=\"#667799\"><small>Alt-Right" +
          "</small></font></html>");
      putValue(MNEMONIC_KEY, KeyEvent.VK_RIGHT);
      putValue(SMALL_ICON, MainFrame.getIcon("RightArrow"));
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      String step = (String) keyDoc.getFeatures().get("anndiffsteps");
      if (step == null) { step = "0"; }
      AnnotationSet target =
        keyDoc.getAnnotations(consensusASTextField.getText().trim());
      AnnotationSet keyAS =
        keyDoc.getAnnotations((String)keySetCombo.getSelectedItem());
      AnnotationSet responseAS =
        resDoc.getAnnotations((String)resSetCombo.getSelectedItem());
      int countCopied = 0, countMarked = 0;
      for (int row = 0; row < pairings.size(); row++) {
        if (keyCopyValueRows.get(row)) {
          Annotation key = pairings.get(row).getKey();
          key.getFeatures().put("anndiffstep", step);
          FeatureMap features = (FeatureMap)
            ((SimpleFeatureMapImpl)key.getFeatures()).clone();
          features.put("anndiffsource", keySetCombo.getSelectedItem());
          try { // copy the key annotation
            target.add(key.getStartNode().getOffset(),
              key.getEndNode().getOffset(), key.getType(), features);
          } catch (InvalidOffsetException e) { e.printStackTrace(); }
          if (key.getFeatures().containsKey("anndiffmodified")) {
            keyAS.remove(key); // remove the modified copy
          }
          countCopied++;
          if (pairings.get(row).getResponse() != null
           && !resCopyValueRows.get(row)) { // mark the response annotation
            pairings.get(row).getResponse().getFeatures().put("anndiffstep", step);
            countMarked++;
          }
        }
        if (resCopyValueRows.get(row)) {
          Annotation response = pairings.get(row).getResponse();
          response.getFeatures().put("anndiffstep", step);
          FeatureMap features = (FeatureMap)
            ((SimpleFeatureMapImpl)response.getFeatures()).clone();
          features.put("anndiffsource", resSetCombo.getSelectedItem());
          try { // copy the response annotation
            target.add(response.getStartNode().getOffset(),
              response.getEndNode().getOffset(), response.getType(), features);
          } catch (InvalidOffsetException e) { e.printStackTrace(); }
          if (response.getFeatures().containsKey("anndiffmodified")) {
            responseAS.remove(response); // remove the modified copy
          }
          countCopied++;
          if (pairings.get(row).getKey() != null
         && !keyCopyValueRows.get(row)) { // mark the key annotation
            pairings.get(row).getKey().getFeatures().put("anndiffstep", step);
            countMarked++;
          }
        }
      }
      if (countCopied > 0) {
        step = String.valueOf(Integer.parseInt(step) + 1);
        keyDoc.getFeatures().put("anndiffsteps", step);
        diffAction.actionPerformed(new ActionEvent(this, -1, "copy"));
        statusLabel.setText(countCopied +
          " annotations copied to " + consensusASTextField.getText().trim() +
          " and " + countMarked + " hidden");
        statusLabel.setForeground(Color.BLACK);
      } else {
        diffTable.requestFocusInWindow();
        statusLabel.setText(
          "Tick checkboxes in the columns K(ey) and R(esponse)");
        statusLabel.setForeground(Color.RED);
      }
    }
  }

  protected class HTMLExportAction extends AbstractAction{
    public HTMLExportAction(){
      putValue(SHORT_DESCRIPTION, "Export the results to HTML");
      putValue(SMALL_ICON,
        MainFrame.getIcon("Download"));
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      XJFileChooser fileChooser =  (MainFrame.getFileChooser() != null) ?
        MainFrame.getFileChooser() : new XJFileChooser();
      fileChooser.setAcceptAllFileFilterUsed(true);
      fileChooser.setDialogTitle("Choose a file to export the results");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      ExtensionFileFilter filter = new ExtensionFileFilter("HTML files","html");
      fileChooser.addChoosableFileFilter(filter);
      String fileName = (resDoc.getSourceUrl() != null) ?
        new File (resDoc.getSourceUrl().getFile()).getName() : resDoc.getName();
      fileName += "_" + annTypeCombo.getSelectedItem().toString() + ".html";
      fileChooser.setFileName(fileName);
      fileChooser.setResource(AnnotationDiffGUI.class.getName());
      int res = fileChooser.showSaveDialog(AnnotationDiffGUI.this);
      if (res != JFileChooser.APPROVE_OPTION) { return; }

      File saveFile = fileChooser.getSelectedFile();
      try{
      String nl = Strings.getNl();
      Writer fw = new BufferedWriter(new FileWriter(saveFile));
      //write the header
      fw.write(HEADER_1);
      fw.write(resDoc.getName() + " " +
              annTypeCombo.getSelectedItem().toString() +
              " annotations");
      fw.write(HEADER_2 + nl);
      fw.write("<H2>Annotation Diff - comparing " +
              annTypeCombo.getSelectedItem().toString() +
              " annotations" + "</H2>");
      fw.write("<TABLE cellpadding=\"5\" border=\"0\"");
      fw.write(nl);
      fw.write("<TR>" + nl);
      fw.write("\t<TH align=\"left\">&nbsp;</TH>" + nl);
      fw.write("\t<TH align=\"left\">Document</TH>" + nl);
      fw.write("\t<TH align=\"left\">Annotation Set</TH>" + nl);
      fw.write("</TR>" + nl);

      fw.write("<TR>" + nl);
      fw.write("\t<TH align=\"left\">Key</TH>" + nl);
      fw.write("\t<TD align=\"left\">" + keyDoc.getName() + "</TD>" + nl);
      fw.write("\t<TD align=\"left\">" + keySet.getName() + "</TD>" + nl);
      fw.write("</TR>" + nl);
      fw.write("<TR>" + nl);
      fw.write("\t<TH align=\"left\">Response</TH>" + nl);
      fw.write("\t<TD align=\"left\">" + resDoc.getName() + "</TD>" + nl);
      fw.write("\t<TD align=\"left\">" + resSet.getName() + "</TD>" + nl);
      fw.write("</TR>" + nl);
      fw.write("</TABLE>" + nl);
      fw.write("<BR><BR><BR>" + nl);
      //write the results
      java.text.NumberFormat format = java.text.NumberFormat.getInstance();
      format.setMaximumFractionDigits(4);
      fw.write("Recall: " + format.format(differ.getRecallStrict()) + "<br>" + nl);
      fw.write("Precision: " + format.format(differ.getPrecisionStrict()) + "<br>" + nl);
      fw.write("F-measure: " + format.format(differ.getFMeasureStrict(1)) + "<br>" + nl);
      fw.write("<br>");
      fw.write("Correct: " + differ.getCorrectMatches() + "<br>" + nl);
      fw.write("Partially correct: " +
          differ.getPartiallyCorrectMatches() + "<br>" + nl);
      fw.write("Missing: " + differ.getMissing() + "<br>" + nl);
      fw.write("False positives: " + differ.getSpurious() + "<br>" + nl);
      fw.write(HEADER_3 + nl + "<TR>" + nl);
      int maxColIdx = diffTable.getColumnCount() - 1;
      for(int col = 0; col <= maxColIdx; col++){
        fw.write("\t<TH align=\"left\">" + diffTable.getColumnName(col) +
                "</TH>" + nl);
      }
      fw.write("</TR>");
      int rowCnt = diffTableModel.getRowCount();
      for(int row = 0; row < rowCnt; row ++){
        fw.write("<TR>");
        for(int col = 0; col <= maxColIdx; col++){
          Color bgCol = diffTableModel.getBackgroundAt(
                  diffTable.rowViewToModel(row),
                  diffTable.convertColumnIndexToModel(col));
          fw.write("\t<TD bgcolor=\"#" +
                  Integer.toHexString(bgCol.getRGB()).substring(2) +
                  "\">" +
                  diffTable.getValueAt(row, col) +
                  "</TD>" + nl);
        }
        fw.write("</TR>");
      }
      fw.write(FOOTER);
      fw.flush();
      fw.close();

      } catch(IOException ioe){
        JOptionPane.showMessageDialog(AnnotationDiffGUI.this, ioe.toString(),
                "GATE", JOptionPane.ERROR_MESSAGE);
        ioe.printStackTrace();
      }
    }

    static final String HEADER_1 = "<html><head><title>";
    static final String HEADER_2 = "</title></head><body>";
    static final String HEADER_3 = "<table cellpadding=\"0\" border=\"1\">";
    static final String FOOTER = "</table></body></html>";
  }

  protected class ShowDocumentAction extends AbstractAction{
    public ShowDocumentAction(){
      putValue(SHORT_DESCRIPTION,
        "Show the selected annotation in the document editor.");
      putValue(SMALL_ICON, MainFrame.getIcon("document"));
      putValue(MNEMONIC_KEY, KeyEvent.VK_UP);
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      int rowModel = diffTable.rowViewToModel(diffTable.getSelectedRow());
      boolean isKeySelected = (diffTable.convertColumnIndexToModel(
        diffTable.getSelectedColumn()) < DiffTableModel.COL_MATCH);
      final Document doc = isKeySelected ? keyDoc : resDoc;
      final Annotation annotation = isKeySelected ?
        pairings.get(rowModel).getKey() : pairings.get(rowModel).getResponse();
      final String asname = isKeySelected ? keySet.getName() : resSet.getName();
      // show the expression in the document
      SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        MainFrame.getInstance().select(doc);
        // wait some time for the document to be displayed
        Date timeToRun = new Date(System.currentTimeMillis() + 1000);
        Timer timer = new Timer("Annotation diff show document timer", true);
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            showExpressionInDocument(doc, annotation, asname);
          }
        }, timeToRun);
      }});
    }

    private void showExpressionInDocument(Document doc, Annotation annotation,
                                          String asname) {
      try {
      // find the document view associated with the document
      TextualDocumentView t = null;
      for (Resource r : Gate.getCreoleRegister().getAllInstances(
          "gate.gui.docview.TextualDocumentView")) {
        if (((TextualDocumentView)r).getDocument().getName()
          .equals(doc.getName())) {
          t = (TextualDocumentView)r;
          break;
        }
      }

      if (t != null && t.getOwner() != null) {
        // display the annotation sets view
        t.getOwner().setRightView(0);
        try {
          // scroll to the expression that matches the query result
          t.getTextView().scrollRectToVisible(
            t.getTextView().modelToView(
            annotation.getStartNode().getOffset().intValue()));
        } catch (BadLocationException e) {
          e.printStackTrace();
          return;
        }
        // select the expression that matches the query result
        t.getTextView().select(
          annotation.getStartNode().getOffset().intValue(),
          annotation.getEndNode().getOffset().intValue());
      }

      // find the annotation sets view associated with the document
      for (Resource r : Gate.getCreoleRegister().getAllInstances(
          "gate.gui.docview.AnnotationSetsView")) {
        AnnotationSetsView asv = (AnnotationSetsView)r;
        if (asv.getDocument() != null
        && asv.isActive()
        && asv.getDocument().getName().equals(doc.getName())) {
          // look if there is the type displayed
          String type = annotation.getType();
          if (asname == null
          && doc.getAnnotations().getAllTypes().contains(type)) {
            asv.setTypeSelected(null, type, true);
          } else if (doc.getAnnotationSetNames().contains(asname)
          && doc.getAnnotations(asname).getAllTypes().contains(type)) {
            asv.setTypeSelected(asname, type, true);
          }
        }
      }

      diffTable.requestFocusInWindow();

      } catch (gate.util.GateException e) {
        e.printStackTrace();
      }
    }
  }

  protected class HelpAction extends AbstractAction {
    public HelpAction() {
      super();
      putValue(SHORT_DESCRIPTION, "User guide for this tool");
      putValue(SMALL_ICON, MainFrame.getIcon("Help"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      MainFrame.getInstance().showHelpFrame(
        "sec:eval:annotationdiff", AnnotationDiffGUI.class.getName());
    }
  }

  protected class CloseAction extends AbstractAction {
    public CloseAction(){
      super("Close");
    }
    @Override
    public void actionPerformed(ActionEvent evt){
      MainFrame.getGuiRoots().remove(AnnotationDiffGUI.this);
      dispose();
    }
  }

  protected class DiffTableCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
      int rowModel = diffTable.rowViewToModel(row);
      int colModel = diffTable.convertColumnIndexToModel(column);
      Component component;
      if (value instanceof Boolean) {
        component = new JCheckBox();
      } else {
        component = super.getTableCellRendererComponent(table, value,
          false, hasFocus, row, column);
      }
      if (pairings.size() == 0 || rowModel >= pairings.size()) {
        return component;
      }
      AnnotationDiffer.Pairing pairing = pairings.get(rowModel);
      // set fore and background colours
      component.setBackground(isSelected ? table.getSelectionBackground() :
        diffTableModel.getBackgroundAt(rowModel, column));
      component.setForeground(isSelected ? table.getSelectionForeground() :
        table.getForeground());
      if (!(component instanceof JComponent)) { return component; }
      // add tooltips for each cell, disable some checkboxes
      // shorten features column values
      String tip;
      try {
      switch (colModel){
        case DiffTableModel.COL_KEY_STRING:
          Annotation key = pairing.getKey();
          if (key == null) {
            tip = null;
          } else { // reformat the text
            tip = keyDoc.getContent().getContent(
                key.getStartNode().getOffset(),
                key.getEndNode().getOffset()).toString();
            if (tip.length() > 1000) {
              tip = tip.substring(0, 1000 / 2) + "<br>...<br>"
                + tip.substring(tip.length() - (1000 / 2));
            }
            tip = keyDoc.getContent().getContent(
              Math.max(0, key.getStartNode().getOffset()-40),
              Math.max(0, key.getStartNode().getOffset())).toString() +
              "<strong>" + tip + "</strong>" +
              keyDoc.getContent().getContent(
              Math.min(keyDoc.getContent().size(),
                key.getEndNode().getOffset()),
              Math.min(keyDoc.getContent().size(),
                key.getEndNode().getOffset()+40)).toString();
            tip = tip.replaceAll("\\s*\n\\s*", "<br>");
            tip = tip.replaceAll("\\s+", " ");
            tip = "<html><table width=\""+(tip.length() > 150? "500": "100%")
              + "\" border=\"0\" cellspacing=\"0\">"
              + "<tr><td>" + tip + "</td></tr><tr><td>";
            tip += "<small><i>↓ = new line, → = tab, · = space</i></small>";
            tip += "</td></tr></table></html>";
          }
          break;
        case DiffTableModel.COL_KEY_FEATURES:
          if (pairing.getKey() == null) {
            tip = null;
          } else {
            String features = pairing.getKey().getFeatures().toString();
            tip = features +
              "<br><small><i>To edit, double-click or press F2.</i></small>";
            tip = "<html><table width=\""+(tip.length() > 150? "500": "100%")
              + "\" border=\"0\" cellspacing=\"0\">"
              + "<tr><td>" + tip + "</td></tr></table></html>";
            if (features.length() > maxCellLength) {
              features = features.substring(0, maxCellLength / 2) + "..."
                + features.substring(features.length() - (maxCellLength / 2));
            }
            ((JLabel)component).setText(features);
          }
          break;
        case DiffTableModel.COL_KEY_COPY:
          tip = (pairing.getKey() == null) ?
            "<html>There is no key annotation."
          : "<html>Select this key annotation to copy.";
          tip += "<br><small><i>"
            + "Space key invert the selected check boxes state."
            + "<br>Right-click for context menu.</i></small></html>";
          component.setEnabled(pairing.getKey() != null);
          ((JCheckBox)component).setSelected(keyCopyValueRows.get(rowModel));
          break;
        case DiffTableModel.COL_MATCH: tip =
          value.equals("=")  ? "correct" :
          value.equals("~")  ? "partially correct" :
          value.equals("-?") ? "missing" :
          value.equals("?-") ? "false positives" :
          value.equals("<>") ? "mismatch" : "";
          break;
        case DiffTableModel.COL_RES_COPY:
          tip = (pairing.getResponse() == null) ?
            "<html>There is no response annotation."
          : "<html>Select this response annotation to copy.";
          tip += "<br><small><i>"
            + "Space key invert the selected check boxes state."
            + "<br>Right-click for context menu.</i></small></html>";
          component.setEnabled(pairing.getResponse() != null);
          ((JCheckBox)component).setSelected(resCopyValueRows.get(rowModel));
           break;
        case DiffTableModel.COL_RES_STRING:
          Annotation response = pairing.getResponse();
          if (response == null) {
            tip = null;
          } else { // reformat the text
            tip = resDoc.getContent().getContent(
                response.getStartNode().getOffset(),
                response.getEndNode().getOffset()).toString();
            if (tip.length() > 1000) {
              tip = tip.substring(0, 1000 / 2) + "<br>...<br>"
                + tip.substring(tip.length() - (1000 / 2));
            }
            tip = resDoc.getContent().getContent(
              Math.max(0, response.getStartNode().getOffset()-40),
              Math.max(0, response.getStartNode().getOffset())).toString() +
              "<strong>" + tip + "</strong>" +
              resDoc.getContent().getContent(
              Math.min(resDoc.getContent().size(),
                response.getEndNode().getOffset()),
              Math.min(resDoc.getContent().size(),
                response.getEndNode().getOffset()+40)).toString();
            tip = tip.replaceAll("\\s*\n\\s*", "<br>");
            tip = tip.replaceAll("\\s+", " ");
            tip = "<html><table width=\""+(tip.length() > 150? "500": "100%")
              + "\" border=\"0\" cellspacing=\"0\">"
              + "<tr><td>" + tip + "</td></tr><tr><td>";
            tip += "<small><i>↓ = new line, → = tab, · = space</i></small>";
            tip += "</td></tr></table></html>";
          }
          break;
        case DiffTableModel.COL_RES_FEATURES:
          if (pairing.getResponse() == null) {
            tip = null;
          } else {
            String features = pairing.getResponse().getFeatures().toString();
            tip = features +
              "<br><small><i>To edit, double-click or press F2.</i></small>";
            tip = "<html><table width=\""+(tip.length() > 150? "500": "100%")
              + "\" border=\"0\" cellspacing=\"0\">"
              + "<tr><td>" + tip + "</td></tr></table></html>";
            if (features.length() > maxCellLength) {
              features = features.substring(0, maxCellLength / 2) + "..."
                + features.substring(features.length() - (maxCellLength / 2));
            }
            ((JLabel)component).setText(features);
          }
          break;
        default:
          Annotation ann = (colModel < DiffTableModel.COL_MATCH) ?
            pairing.getKey() : pairing.getResponse();
          if (ann == null) {
            tip = null;
          } else {
            tip = "<html><small><i>To edit, double-click or press F2." +
              "</i></small></html>";
          }
      }
      } catch(InvalidOffsetException ioe){
        //this should never happen
        throw new GateRuntimeException(ioe);
      }
      ((JComponent)component).setToolTipText(tip);
      return component;
    }
  }

  protected class DiffTableModel extends AbstractTableModel{

    @Override
    public int getRowCount(){
      return pairings.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
      switch (columnIndex){
        case COL_KEY_COPY: return Boolean.class;
        case COL_RES_COPY: return Boolean.class;
        default: return String.class;
      }
    }

    @Override
    public int getColumnCount(){
      return COL_COUNT;
    }

    @Override
    public String getColumnName(int column){
      switch(column){
        case COL_KEY_START: return "Start";
        case COL_KEY_END: return "End";
        case COL_KEY_STRING: return "Key";
        case COL_KEY_FEATURES: return "Features";
        case COL_KEY_COPY: return "K";
        case COL_MATCH: return "=?";
        case COL_RES_COPY: return "R";
        case COL_RES_START: return "Start";
        case COL_RES_END: return "End";
        case COL_RES_STRING: return "Response";
        case COL_RES_FEATURES: return "Features";
        default: return "?";
      }
    }

    public Color getBackgroundAt(int row, int column){
      AnnotationDiffer.Pairing pairing = pairings.get(row);
      switch(pairing.getType()){
        case(AnnotationDiffer.CORRECT_TYPE): return diffTable.getBackground();
        case(AnnotationDiffer.PARTIALLY_CORRECT_TYPE): return PARTIALLY_CORRECT_BG;
        case(AnnotationDiffer.MISMATCH_TYPE):
          if(column < COL_MATCH) return MISSING_BG;
          else if(column > COL_MATCH) return FALSE_POSITIVE_BG;
          else return diffTable.getBackground();
        case(AnnotationDiffer.MISSING_TYPE): return MISSING_BG;
        case(AnnotationDiffer.SPURIOUS_TYPE): return FALSE_POSITIVE_BG;
        default: return diffTable.getBackground();
      }
//      
//      Color colKey = pairing.getType() == 
//          AnnotationDiffer.CORRECT_TYPE ?
//          diffTable.getBackground() :
//          (pairing.getType() == AnnotationDiffer.PARTIALLY_CORRECT_TYPE ?
//           PARTIALLY_CORRECT_BG : MISSING_BG);
//      if(pairing.getKey() == null) colKey = diffTable.getBackground();
//      
//      Color colRes = pairing.getType() == AnnotationDiffer.CORRECT_TYPE ?
//                     diffTable.getBackground() :
//                       (pairing.getType() == AnnotationDiffer.PARTIALLY_CORRECT_TYPE ?
//                       PARTIALLY_CORRECT_BG :
//                         FALSE_POSITIVE_BG);
//      if(pairing.getResponse() == null) colRes = diffTable.getBackground();
//      switch(column){
//        case COL_KEY_START: return colKey;
//        case COL_KEY_END: return colKey;
//        case COL_KEY_STRING: return colKey;
//        case COL_KEY_FEATURES: return colKey;
//        case COL_MATCH: return diffTable.getBackground();
//        case COL_RES_START: return colRes;
//        case COL_RES_END: return colRes;
//        case COL_RES_STRING: return colRes;
//        case COL_RES_FEATURES: return colRes;
//        default: return diffTable.getBackground();
//      }
    }

    @Override
    public Object getValueAt(int row, int column){
      AnnotationDiffer.Pairing pairing = pairings.get(row);
      Annotation key = pairing.getKey();
      Annotation res = pairing.getResponse();

      switch(column){
        case COL_KEY_START: return key == null ? "" :
          key.getStartNode().getOffset().toString();
        case COL_KEY_END: return key == null ? "" :
          key.getEndNode().getOffset().toString();
        case COL_KEY_STRING:
          String keyStr = "";
          try{
            if(key != null && keyDoc != null){
              keyStr = keyDoc.getContent().getContent(
                key.getStartNode().getOffset(),
                key.getEndNode().getOffset()).toString();
            }
          }catch(InvalidOffsetException ioe){
            //this should never happen
            throw new GateRuntimeException(ioe);
          }
          // cut annotated text in the middle if too long
          if (keyStr.length() > maxCellLength) {
            keyStr = keyStr.substring(0, maxCellLength / 2) + "..."
              + keyStr.substring(keyStr.length() - (maxCellLength / 2));
          }
          // use special characters for newline, tab and space
          keyStr = keyStr.replaceAll("(?:\r?\n)|\r", "↓");
          keyStr = keyStr.replaceAll("\t", "→");
          keyStr = keyStr.replaceAll(" ", "·");
          return keyStr;
        case COL_KEY_FEATURES: return key == null ? "" :
          key.getFeatures().toString();
        case COL_KEY_COPY: return keyCopyValueRows.get(row);
        case COL_MATCH: return matchLabel[pairing.getType()];
        case COL_RES_COPY: return resCopyValueRows.get(row);
        case COL_RES_START: return res == null ? "" :
          res.getStartNode().getOffset().toString();
        case COL_RES_END: return res == null ? "" :
          res.getEndNode().getOffset().toString();
        case COL_RES_STRING:
          String resStr = "";
          try{
            if(res != null && resDoc != null){
              resStr = resDoc.getContent().getContent(
                res.getStartNode().getOffset(),
                res.getEndNode().getOffset()).toString();
            }
          }catch(InvalidOffsetException ioe){
            //this should never happen
            throw new GateRuntimeException(ioe);
          }
          if (resStr.length() > maxCellLength) {
            resStr = resStr.substring(0, maxCellLength / 2) + "..."
              + resStr.substring(resStr.length() - (maxCellLength / 2));
          }
          // use special characters for newline, tab and space
          resStr = resStr.replaceAll("(?:\r?\n)|\r", "↓");
          resStr = resStr.replaceAll("\t", "→");
          resStr = resStr.replaceAll(" ", "·");
          return resStr;
        case COL_RES_FEATURES: return res == null ? "" :
          res.getFeatures().toString();
        default: return "?";
      }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
      if (pairings.size() == 0) { return false; }
      AnnotationDiffer.Pairing pairing = pairings.get(rowIndex);
      switch(columnIndex) {
        case COL_KEY_COPY:
        case COL_KEY_START:
        case COL_KEY_END:
        case COL_KEY_FEATURES: return pairing.getKey() != null;
        case COL_RES_COPY:
        case COL_RES_START:
        case COL_RES_END:
        case COL_RES_FEATURES: return pairing.getResponse() != null;
        default: return false;
      }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
      AnnotationDiffer.Pairing pairing = pairings.get(rowIndex);
      AnnotationSet keyAS =
        keyDoc.getAnnotations((String)keySetCombo.getSelectedItem());
      AnnotationSet responseAS =
        resDoc.getAnnotations((String)resSetCombo.getSelectedItem());
      Annotation key = pairing.getKey();
      Annotation res = pairing.getResponse();
      String step = (String) keyDoc.getFeatures().get("anndiffsteps");
      if (step == null) { step = "0"; }
      int id = -1;
      String keysValues;
      FeatureMap features;
      try {
      switch(columnIndex){
        case COL_KEY_START:
          if (Long.valueOf((String) aValue)
            .equals(key.getStartNode().getOffset())) { break; }
          id = keyAS.add(Long.valueOf((String)aValue),
            key.getEndNode().getOffset(), key.getType(),
            (FeatureMap)((SimpleFeatureMapImpl)key.getFeatures()).clone());
          keyAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (key.getFeatures().containsKey("anndiffmodified")) {
            keyAS.remove(key);
          } else {
            key.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("Start offset changed: " +
            key.getStartNode().getOffset() + " -> " + aValue + ".");
          statusLabel.setForeground(Color.BLACK);
          break;
        case COL_KEY_END:
          if (Long.valueOf((String) aValue)
            .equals(key.getEndNode().getOffset())) { break; }
          id = keyAS.add(key.getStartNode().getOffset(),
            Long.valueOf((String)aValue), key.getType(),
            (FeatureMap)((SimpleFeatureMapImpl)key.getFeatures()).clone());
          keyAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (key.getFeatures().containsKey("anndiffmodified")) {
            keyAS.remove(key);
          } else {
            key.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("End offset changed: " +
            key.getEndNode().getOffset() + " -> " + aValue + ".");
          statusLabel.setForeground(Color.BLACK);
          break;
        case COL_KEY_FEATURES:
          keysValues = (String) aValue;
          keysValues = keysValues.replaceAll("\\s+", " ").replaceAll("[}{]", "");
          features = Factory.newFeatureMap();
          if (keysValues.length() != 0) {
            for (String keyValue : keysValues.split(",")) {
              String[] keyOrValue = keyValue.split("=");
              if (keyOrValue.length != 2) { throw new NumberFormatException(); }
                features.put(keyOrValue[0].trim(), keyOrValue[1].trim());
            }
          }
          if (features.equals(key.getFeatures())) { break; }
          id = keyAS.add(key.getStartNode().getOffset(),
            key.getEndNode().getOffset(), key.getType(), features);
          keyAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (key.getFeatures().containsKey("anndiffmodified")) {
            keyAS.remove(key);
          } else {
            key.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("Features changed.");
          statusLabel.setForeground(Color.BLACK);
          break;
        case COL_KEY_COPY:
          keyCopyValueRows.set(rowIndex, (Boolean)aValue);
          break;
        case COL_RES_COPY:
          resCopyValueRows.set(rowIndex, (Boolean)aValue);
          break;
        case COL_RES_START:
          if (Long.valueOf((String) aValue)
            .equals(res.getStartNode().getOffset())) { break; }
          id = responseAS.add(Long.valueOf((String)aValue),
            res.getEndNode().getOffset(), res.getType(),
            (FeatureMap)((SimpleFeatureMapImpl)res.getFeatures()).clone());
          responseAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (res.getFeatures().containsKey("anndiffmodified")) {
            responseAS.remove(res);
          } else {
            res.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("Start offset changed: " +
            res.getStartNode().getOffset() + " -> " + aValue + ".");
          statusLabel.setForeground(Color.BLACK);
          break;
        case COL_RES_END:
          if (Long.valueOf((String) aValue)
            .equals(res.getEndNode().getOffset())) { break; }
          id = responseAS.add(res.getStartNode().getOffset(),
            Long.valueOf((String)aValue), res.getType(),
            (FeatureMap)((SimpleFeatureMapImpl)res.getFeatures()).clone());
          responseAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (res.getFeatures().containsKey("anndiffmodified")) {
            responseAS.remove(res);
          } else {
            res.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("End offset changed: " +
            res.getEndNode().getOffset() + " -> " + aValue + ".");
          statusLabel.setForeground(Color.BLACK);
          break;
        case COL_RES_FEATURES:
          keysValues = (String) aValue;
          keysValues = keysValues.replaceAll("\\s+", " ").replaceAll("[}{]", "");
          features = Factory.newFeatureMap();
          if (keysValues.length() != 0) {
            for (String keyValue : keysValues.split(",")) {
              String[] keyOrValue = keyValue.split("=");
              if (keyOrValue.length != 2) { throw new NumberFormatException(); }
              features.put(keyOrValue[0].trim(), keyOrValue[1].trim());
            }
          }
          if (features.equals(res.getFeatures())) { break; }
          id = responseAS.add(res.getStartNode().getOffset(),
            res.getEndNode().getOffset(), res.getType(), features);
          responseAS.get(id).getFeatures().put("anndiffmodified", "true");
          if (res.getFeatures().containsKey("anndiffmodified")) {
            responseAS.remove(res);
          } else {
            res.getFeatures().put("anndiffstep", step);
          }
          statusLabel.setText("Features changed.");
          statusLabel.setForeground(Color.BLACK);
          break;
      }
      }catch(InvalidOffsetException e){
        statusLabel.setText(
          "This offset is incorrect. No changes have been made.");
        statusLabel.setForeground(Color.RED);
        return;
      }catch(NumberFormatException e) {
        statusLabel.setText(
          "The format is incorrect. No changes have been made.");
        statusLabel.setForeground(Color.RED);
        return;
      }
      if (id != -1) {
        // compute again the differences
        diffAction.actionPerformed(new ActionEvent(this, id, "setvalue"));
      }
    }

    protected static final int COL_COUNT = 11;
    protected static final int COL_KEY_START = 0;
    protected static final int COL_KEY_END = 1;
    protected static final int COL_KEY_STRING = 2;
    protected static final int COL_KEY_FEATURES = 3;
    protected static final int COL_KEY_COPY = 4;
    protected static final int COL_MATCH = 5;
    protected static final int COL_RES_COPY = 6;
    protected static final int COL_RES_START = 7;
    protected static final int COL_RES_END = 8;
    protected static final int COL_RES_STRING = 9;
    protected static final int COL_RES_FEATURES = 10;
  } // protected class DiffTableModel extends AbstractTableModel

  // Local objects
  protected AnnotationDiffer differ;
  protected List<AnnotationDiffer.Pairing> pairings;
  protected List<Boolean> keyCopyValueRows;
  protected List<Boolean> resCopyValueRows;
  protected List<Resource> documents;
  protected Document keyDoc;
  protected Document resDoc;
  protected List<AnnotationSet> keySets;
  protected List<AnnotationSet> resSets;
  protected AnnotationSet keySet;
  protected AnnotationSet resSet;
  protected Set<String> significantFeatures;

  // Actions
  protected DiffAction diffAction;
  protected CopyToTargetSetAction copyToTargetSetAction;
  protected HTMLExportAction htmlExportAction;
  protected ShowDocumentAction showDocumentAction;

  // Top part of the UI from left to right
  protected JComboBox<String> keyDocCombo;
  protected JComboBox<String> resDocCombo;
  protected JComboBox<String> keySetCombo;
  protected JComboBox<String> resSetCombo;
  protected JComboBox<String> annTypeCombo;
  protected DefaultListModel<String> featureslistModel;
  protected JList<String> featuresList;
  protected JRadioButton allFeaturesBtn;
  protected JRadioButton someFeaturesBtn;
  protected JRadioButton noFeaturesBtn;
  protected JTextField weightTxt;
  protected JButton doDiffBtn;

  // Center part of the UI
  protected JScrollPane scroller;
  protected DiffTableModel diffTableModel;
  protected XJTable diffTable;

  // Bottom part of the UI
  protected JTabbedPane bottomTabbedPane;
  protected JPanel statisticsPane;
  protected JLabel correctLbl;
  protected JLabel partiallyCorrectLbl;
  protected JLabel missingLbl;
  protected JLabel falsePozLbl;
  protected JLabel recallStrictLbl;
  protected JLabel precisionStrictLbl;
  protected JLabel fmeasureStrictLbl;
  protected JLabel recallLenientLbl;
  protected JLabel precisionLenientLbl;
  protected JLabel fmeasureLenientLbl;
  protected JLabel recallAveLbl;
  protected JLabel precisionAveLbl;
  protected JLabel fmeasureAveLbl;
  protected JTextField consensusASTextField;
  protected JButton copyToConsensusBtn;
  protected JLabel statusLabel;
  protected JButton htmlExportBtn;
  protected JButton showDocumentBtn;
  protected JProgressBar progressBar;

  // Constants
  protected static final Color PARTIALLY_CORRECT_BG = new Color(173,215,255);
  protected static final Color MISSING_BG = new Color(255,173,181);
  protected static final Color FALSE_POSITIVE_BG = new Color(255,231,173);
  protected static final String[] matchLabel;
  static{
    matchLabel = new String[5];
    matchLabel[AnnotationDiffer.CORRECT_TYPE] = "=";
    matchLabel[AnnotationDiffer.PARTIALLY_CORRECT_TYPE] = "~";
    matchLabel[AnnotationDiffer.MISSING_TYPE] = "-?";
    matchLabel[AnnotationDiffer.SPURIOUS_TYPE] = "?-";
    matchLabel[AnnotationDiffer.MISMATCH_TYPE] = "<>";
  }
  /** Maximum number of characters for Key, Response and Features columns. */
  protected final int maxCellLength = 40;
  /** Is this GUI standalone or embedded in GATE? */
  protected boolean isStandalone;
}
