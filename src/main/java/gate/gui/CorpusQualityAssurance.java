/*
 *  Copyright (c) 2009-2010, Ontotext AD.
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz - 10 June 2009
 *
 *  $Id: CorpusQualityAssurance.java 19641 2016-10-06 07:24:25Z markagreenwood $
 */

package gate.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.Position;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.event.CorpusEvent;
import gate.event.CorpusListener;
import gate.resources.img.svg.AnnotationDiffIcon;
import gate.resources.img.svg.DocumentIcon;
import gate.resources.img.svg.DownloadIcon;
import gate.resources.img.svg.HelpIcon;
import gate.resources.img.svg.ProgressIcon;
import gate.resources.img.svg.RefreshIcon;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.AnnotationDiffer;
import gate.util.ClassificationMeasures;
import gate.util.ExtensionFileFilter;
import gate.util.OntologyMeasures;
import gate.util.OptionsMap;
import gate.util.Strings;

/**
 * Quality assurance corpus view.
 * Compare two sets of annotations with optionally their features
 * globally for each annotation and for each document inside a corpus
 * with different measures notably precision, recall and F1-score.
 */
@SuppressWarnings({"serial","unchecked","rawtypes","deprecation"})
@CreoleResource(name = "Corpus Quality Assurance", guiType = GuiType.LARGE,
    resourceDisplayed = "gate.Corpus", mainViewer = false,
    helpURL = "http://gate.ac.uk/userguide/sec:eval:corpusqualityassurance")
public class CorpusQualityAssurance extends AbstractVisualResource
  implements CorpusListener {

  @Override
  public Resource init(){
    initLocalData();
    initGuiComponents();
    initListeners();
    return this;
  }

  protected void initLocalData(){
    collator = Collator.getInstance(Locale.ENGLISH);
    collator.setStrength(Collator.TERTIARY);
    documentTableModel = new DefaultTableModel();
    documentTableModel.addColumn("Document");
    documentTableModel.addColumn("Match");
    documentTableModel.addColumn("Only A");
    documentTableModel.addColumn("Only B");
    documentTableModel.addColumn("Overlap");
    annotationTableModel = new DefaultTableModel();
    annotationTableModel.addColumn("Annotation");
    annotationTableModel.addColumn("Match");
    annotationTableModel.addColumn("Only A");
    annotationTableModel.addColumn("Only B");
    annotationTableModel.addColumn("Overlap");
    document2TableModel = new DefaultTableModel();
    document2TableModel.addColumn("Document");
    document2TableModel.addColumn("Agreed");
    document2TableModel.addColumn("Total");
    confusionTableModel = new DefaultTableModel();
    types = new TreeSet<String>(collator);
    corpusChanged = false;
    measuresType = FSCORE_MEASURES;
    doubleComparator = new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        if (s1 == null || s2 == null) {
          return 0;
        } else if (s1.equals("")) {
          return 1;
        } else if (s2.equals("")) {
          return -1;
        } else {
          return Double.valueOf(s1).compareTo(Double.valueOf(s2));
        }
      }
    };
    totalComparator = new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        if (s1 == null || s2 == null) {
          return 0;
        } else if (s1.equals("Micro summary")) {
          return s2.equals("Macro summary") ? -1 : 1;
        } else if (s1.equals("Macro summary")) {
          return s2.equals("Micro summary") ? -1 : 1;
        } else if (s2.equals("Micro summary")) {
          return s1.equals("Macro summary") ? 1 : -1;
        } else if (s2.equals("Macro summary")) {
          return s1.equals("Micro summary") ? 1 : -1;
        } else {
          return s1.compareTo(s2);
        }
      }
    };
  }

  protected void initGuiComponents() {
    setLayout(new BorderLayout());

    JPanel sidePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // toolbar
    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);
    toolbar.add(openDocumentAction = new OpenDocumentAction());
    openDocumentAction.setEnabled(false);
    toolbar.add(openAnnotationDiffAction = new OpenAnnotationDiffAction());
    openAnnotationDiffAction.setEnabled(false);
    toolbar.add(exportToHtmlAction = new ExportToHtmlAction());
    toolbar.add(reloadCacheAction = new ReloadCacheAction());
    toolbar.add(new HelpAction());
    gbc.anchor = GridBagConstraints.NORTHWEST;
    sidePanel.add(toolbar, gbc);
    gbc.anchor = GridBagConstraints.NORTH;
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // annotation sets list
    JLabel label = new JLabel("Annotation Sets A/Key & B/Response");
    label.setToolTipText("aka 'Key & Response sets'");
    gbc.fill = GridBagConstraints.BOTH;
    sidePanel.add(label, gbc);
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    setList = new JList();
    setList.setSelectionModel(new ToggleSelectionABModel(setList));
    setList.setPrototypeCellValue("present in every document");
    setList.setVisibleRowCount(4);
    gbc.weighty = 1;
    sidePanel.add(new JScrollPane(setList), gbc);
    gbc.weighty = 0;
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    setCheck = new JCheckBox("present in every document", false);
    setCheck.addActionListener(new AbstractAction(){
      @Override
      public void actionPerformed(ActionEvent e) {
        updateSetList();
      }
    });
    sidePanel.add(setCheck, gbc);
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // annotation types list
    label = new JLabel("Annotation Types");
    label.setToolTipText("Annotation types to compare");
    sidePanel.add(label, gbc);
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    typeList = new JList();
    typeList.setSelectionModel(new ToggleSelectionModel());
    typeList.setPrototypeCellValue("present in every document");
    typeList.setVisibleRowCount(4);
    gbc.weighty = 1;
    sidePanel.add(new JScrollPane(typeList), gbc);
    gbc.weighty = 0;
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    typeCheck = new JCheckBox("present in every selected set", false);
    typeCheck.addActionListener(new AbstractAction(){
      @Override
      public void actionPerformed(ActionEvent e) {
        setList.getListSelectionListeners()[0].valueChanged(null);
      }
    });
    sidePanel.add(typeCheck, gbc);
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // annotation features list
    label = new JLabel("Annotation Features");
    label.setToolTipText("Annotation features to compare");
    sidePanel.add(label, gbc);
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    featureList = new JList();
    featureList.setSelectionModel(new ToggleSelectionModel());
    featureList.setPrototypeCellValue("present in every document");
    featureList.setVisibleRowCount(4);
    gbc.weighty = 1;
    sidePanel.add(new JScrollPane(featureList), gbc);
    gbc.weighty = 0;
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    featureCheck = new JCheckBox("present in every selected type", false);
    featureCheck.addActionListener(new AbstractAction(){
      @Override
      public void actionPerformed(ActionEvent e) {
        typeList.getListSelectionListeners()[0].valueChanged(null);
      }
    });
    sidePanel.add(featureCheck, gbc);
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // measures tabbed panes
    label = new JLabel("Measures");
    label.setToolTipText("Measures used to compare annotations");
    optionsButton = new JToggleButton("Options");
    optionsButton.setMargin(new Insets(1, 1, 1, 1));
    JPanel labelButtonPanel = new JPanel(new BorderLayout());
    labelButtonPanel.add(label, BorderLayout.WEST);
    labelButtonPanel.add(optionsButton, BorderLayout.EAST);
    sidePanel.add(labelButtonPanel, gbc);
    sidePanel.add(Box.createVerticalStrut(2), gbc);
    final JScrollPane measureScrollPane = new JScrollPane();
    measureList = new JList();
    measureList.setSelectionModel(new ToggleSelectionModel());
    String prefix = getClass().getName() + '.';
    double beta = (userConfig.getDouble(prefix+"fscorebeta") == null) ?
      1.0 : userConfig.getDouble(prefix+"fscorebeta");
    double beta2 = (userConfig.getDouble(prefix+"fscorebeta2") == null) ?
      0.5 : userConfig.getDouble(prefix+"fscorebeta2");
    String fscore = "F" + beta + "-score ";
    String fscore2 = "F" + beta2 + "-score ";
    measureList.setModel(new ExtendedListModel(new String[]{
      fscore+"strict", fscore+"lenient", fscore+"average",
      fscore+"strict BDM", fscore+"lenient BDM", fscore+"average BDM",
      fscore2+"strict", fscore2+"lenient", fscore2+"average",
      fscore2+"strict BDM", fscore2+"lenient BDM", fscore2+"average BDM"}));
    measureList.setPrototypeCellValue("present in every document");
    measureList.setVisibleRowCount(4);
    measureScrollPane.setViewportView(measureList);
    final JScrollPane measure2ScrollPane = new JScrollPane();
    measure2List = new JList();
    measure2List.setSelectionModel(new ToggleSelectionModel());
    measure2List.setModel(new ExtendedListModel(new String[]{
      "Observed agreement", "Cohen's Kappa" , "Pi's Kappa"}));
    measure2List.setPrototypeCellValue("present in every document");
    measure2List.setVisibleRowCount(4);
    measure2ScrollPane.setViewportView(measure2List);
    measureTabbedPane = new JTabbedPane();
    measureTabbedPane.addTab("F-Score", null,
      measureScrollPane, "Inter-annotator agreement");
    measureTabbedPane.addTab("Classification", null,
      measure2ScrollPane, "Classification agreement");
    gbc.weighty = 1;
    sidePanel.add(measureTabbedPane, gbc);
    gbc.weighty = 0;
    sidePanel.add(Box.createVerticalStrut(5), gbc);
    sidePanel.add(Box.createVerticalGlue(), gbc);

    // options panel for fscore measures
    final JPanel measureOptionsPanel = new JPanel();
    measureOptionsPanel.setLayout(
      new BoxLayout(measureOptionsPanel, BoxLayout.Y_AXIS));
    JPanel betaPanel = new JPanel();
    betaPanel.setLayout(new BoxLayout(betaPanel, BoxLayout.X_AXIS));
    JLabel betaLabel = new JLabel("Fscore Beta 1:");
    final JSpinner betaSpinner =
      new JSpinner(new SpinnerNumberModel(beta, 0, 1, 0.1));
    betaSpinner.setToolTipText(
      "<html>Relative weight of precision and recall." +
      "<ul><li>1 weights equally precision and recall" +
      "<li>0.5 weights precision twice as much as recall" +
      "<li>2 weights recall twice as much as precision</ul></html>");
    betaPanel.add(betaLabel);
    betaPanel.add(Box.createHorizontalStrut(5));
    betaPanel.add(betaSpinner);
    betaPanel.add(Box.createHorizontalGlue());
    measureOptionsPanel.add(betaPanel);
    betaSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE,
      Math.round(betaLabel.getPreferredSize().height*1.5f)));
    JPanel beta2Panel = new JPanel();
    beta2Panel.setLayout(new BoxLayout(beta2Panel, BoxLayout.X_AXIS));
    JLabel beta2Label = new JLabel("Fscore Beta 2:");
    final JSpinner beta2Spinner =
      new JSpinner(new SpinnerNumberModel(beta2, 0, 1, 0.1));
    beta2Spinner.setToolTipText(betaSpinner.getToolTipText());
    beta2Panel.add(beta2Label);
    beta2Panel.add(Box.createHorizontalStrut(5));
    beta2Panel.add(beta2Spinner);
    beta2Panel.add(Box.createHorizontalGlue());
    measureOptionsPanel.add(beta2Panel);
    beta2Spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE,
      Math.round(beta2Label.getPreferredSize().height*1.5f)));
    JPanel bdmFilePanel = new JPanel();
    bdmFilePanel.setLayout(new BoxLayout(bdmFilePanel, BoxLayout.X_AXIS));
    JLabel bdmFileLabel = new JLabel("BDM file:");
    JButton bdmFileButton = new JButton(new SetBdmFileAction());
    bdmFilePanel.add(bdmFileLabel);
    bdmFilePanel.add(Box.createHorizontalStrut(5));
    bdmFilePanel.add(bdmFileButton);
    bdmFilePanel.add(Box.createHorizontalGlue());
    measureOptionsPanel.add(bdmFilePanel);

    // options panel for classification measures
    final JPanel measure2OptionsPanel = new JPanel();
    measure2OptionsPanel.setLayout(
      new BoxLayout(measure2OptionsPanel, BoxLayout.Y_AXIS));
    JPanel verbosePanel = new JPanel();
    verbosePanel.setLayout(new BoxLayout(verbosePanel, BoxLayout.X_AXIS));
    boolean verbose = (userConfig.getBoolean(prefix+"verbose") == null) ?
      false : userConfig.getBoolean(prefix+"verbose");
    verboseOptionCheckBox = new JCheckBox("Output ignored annotations",verbose);
    verbosePanel.add(verboseOptionCheckBox);
    verbosePanel.add(Box.createHorizontalGlue());
    measure2OptionsPanel.add(verbosePanel);

    // options button action
    optionsButton.setAction(new AbstractAction("Options") {
      int[] selectedIndices;
      @Override
      public void actionPerformed(ActionEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        // switch measure options panel and measure list
        if (button.isSelected()) {
          if (measuresType == FSCORE_MEASURES) {
            selectedIndices = measureList.getSelectedIndices();
            measureScrollPane.setViewportView(measureOptionsPanel);
          } else if (measuresType == CLASSIFICATION_MEASURES) {
            selectedIndices = measure2List.getSelectedIndices();
            measure2ScrollPane.setViewportView(measure2OptionsPanel);
          }
        } else {
          String prefix = getClass().getEnclosingClass().getName() + '.';
          if (measuresType == FSCORE_MEASURES) {
            // update beta with new values
            String fscore = "F" + betaSpinner.getValue() + "-score ";
            String fscore2 = "F" + beta2Spinner.getValue() + "-score ";
            measureList.setModel(new ExtendedListModel(new String[]{
              fscore+"strict", fscore+"lenient", fscore+"average",
              fscore+"strict BDM", fscore+"lenient BDM", fscore+"average BDM",
              fscore2+"strict", fscore2+"lenient", fscore2+"average",
              fscore2+"strict BDM", fscore2+"lenient BDM", fscore2+"average BDM"}));
            // save in GATE preferences
            userConfig.put(prefix+"fscorebeta", betaSpinner.getValue());
            userConfig.put(prefix+"fscorebeta2", beta2Spinner.getValue());
            // put back the list and its selection
            measureScrollPane.setViewportView(measureList);
            measureList.setSelectedIndices(selectedIndices);
          } else if (measuresType == CLASSIFICATION_MEASURES) {
            userConfig.put(prefix+"verbose",verboseOptionCheckBox.isSelected());
            measure2ScrollPane.setViewportView(measure2List);
            measure2List.setSelectedIndices(selectedIndices);
          }
        }
      }
    });

    // compare button and progress bar
    JButton compareButton = new JButton(compareAction = new CompareAction());
    compareAction.setEnabled(false);
    sidePanel.add(compareButton, gbc);
    sidePanel.add(Box.createVerticalStrut(5), gbc);
    progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressBar.setString("");
    sidePanel.add(progressBar, gbc);
    sidePanel.add(Box.createVerticalStrut(5), gbc);

    // tables
    annotationTable = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return false;
      }
      @Override
      protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
          @Override
          public String getToolTipText(MouseEvent event) {
            int index = columnModel.getColumnIndexAtX(event.getPoint().x);
            if (index == -1) { return null; }
            int modelIndex = columnModel.getColumn(index).getModelIndex();
            String columnName = this.table.getModel().getColumnName(modelIndex);
            return createToolTipFromColumnName(columnName);
          }
        };
      }
    };
    annotationTable.setModel(annotationTableModel);
    annotationTable.setSortable(false);
    annotationTable.setEnableHidingColumns(true);
    annotationTable.setAutoResizeMode(XJTable.AUTO_RESIZE_ALL_COLUMNS);
    documentTable = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return false;
      }
      @Override
      protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
          @Override
          public String getToolTipText(MouseEvent event) {
            int index = columnModel.getColumnIndexAtX(event.getPoint().x);
            if (index == -1) { return null; }
            int modelIndex = columnModel.getColumn(index).getModelIndex();
            String columnName = this.table.getModel().getColumnName(modelIndex);
            return createToolTipFromColumnName(columnName);
          }
        };
      }
    };
    documentTable.setModel(documentTableModel);
    documentTable.setSortable(false);
    documentTable.setEnableHidingColumns(true);
    documentTable.setAutoResizeMode(XJTable.AUTO_RESIZE_ALL_COLUMNS);
    document2Table = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return false;
      }
    };
    document2Table.setModel(document2TableModel);
    confusionTable = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return false;
      }
    };
    confusionTable.setModel(confusionTableModel);
    confusionTable.setSortable(false);

    tableTabbedPane = new JTabbedPane();
    tableTabbedPane.addTab("Corpus statistics", null,
      new JScrollPane(annotationTable),
      "Compare each annotation type for the whole corpus");
    tableTabbedPane.addTab("Document statistics", null,
      new JScrollPane(documentTable),
      "Compare each documents in the corpus with theirs annotations");

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setContinuousLayout(true);
    splitPane.setOneTouchExpandable(true);
    splitPane.setResizeWeight(0.80);
    splitPane.setLeftComponent(tableTabbedPane);
    splitPane.setRightComponent(new JScrollPane(sidePanel));

    add(splitPane);
  }

  protected void initListeners() {

    // when the view is shown update the tables if the corpus has changed
    addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) {
        if (!isShowing() || !corpusChanged) { return; }
        if (timerTask != null) { timerTask.cancel(); }
        Date timeToRun = new Date(System.currentTimeMillis() + 1000);
        timerTask = new TimerTask() { @Override
        public void run() {
          readSetsTypesFeatures(0);
        }};
        timer.schedule(timerTask, timeToRun); // add a delay before updating
      }
      @Override
      public void ancestorRemoved(AncestorEvent event) { /* do nothing */ }
      @Override
      public void ancestorMoved(AncestorEvent event) { /* do nothing */ }
    });

    // when set list selection change
    setList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (typesSelected == null) {
          typesSelected = typeList.getSelectedValues();
        }
        typeList.setModel(new ExtendedListModel());
        keySetName = ((ToggleSelectionABModel)
          setList.getSelectionModel()).getSelectedValueA();
        responseSetName = ((ToggleSelectionABModel)
          setList.getSelectionModel()).getSelectedValueB();
        if (keySetName == null
         || responseSetName == null
         || setList.getSelectionModel().getValueIsAdjusting()) {
          compareAction.setEnabled(false);
          return;
        }
        setList.setEnabled(false);
        setCheck.setEnabled(false);
        // update type UI list
        TreeSet<String> someTypes = new TreeSet<String>();
        TreeMap<String, TreeSet<String>> typesFeatures;
        boolean firstLoop = true; // needed for retainAll to work
        synchronized(docsSetsTypesFeatures) {
          for (TreeMap<String, TreeMap<String, TreeSet<String>>>
              setsTypesFeatures : docsSetsTypesFeatures.values()) {
            typesFeatures = setsTypesFeatures.get(
              keySetName.equals("[Default set]") ? "" : keySetName);
            if (typesFeatures != null) {
              if (typeCheck.isSelected() && !firstLoop) {
                someTypes.retainAll(typesFeatures.keySet());
              } else {
                someTypes.addAll(typesFeatures.keySet());
              }
            } else if (typeCheck.isSelected()) {
              // empty set no types to display
              break;
            }
            typesFeatures = setsTypesFeatures.get(
              responseSetName.equals("[Default set]") ? "" : responseSetName);
            if (typesFeatures != null) {
              if (typeCheck.isSelected()) {
                someTypes.retainAll(typesFeatures.keySet());
              } else {
                someTypes.addAll(typesFeatures.keySet());
              }
            } else if (typeCheck.isSelected()) {
              break;
            }
            firstLoop = false;
          }
        }
        typeList.setModel(new ExtendedListModel(someTypes.toArray()));
        if (someTypes.size() > 0) {
          for (Object value : typesSelected) {
            // put back the selection if possible
            int index = typeList.getNextMatch(
              (String) value, 0, Position.Bias.Forward);
            if (index != -1) {
              typeList.setSelectedIndex(index);
            }
          }
        }
        typesSelected = null;
        setList.setEnabled(true);
        setCheck.setEnabled(true);
        if (measuresType == FSCORE_MEASURES) {
          compareAction.setEnabled(true);
        }
      }
    });

    // when type list selection change
    typeList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        // update feature UI list
        if (featuresSelected == null) {
          featuresSelected = featureList.getSelectedValues();
        }
        featureList.setModel(new ExtendedListModel());
        if (typeList.getSelectedValues().length == 0
         || typeList.getSelectionModel().getValueIsAdjusting()) {
          return;
        }
        final Set<String> typeNames = new HashSet<String>();
        for (Object type : typeList.getSelectedValues()) {
          typeNames.add((String) type);
        }
        typeList.setEnabled(false);
        typeCheck.setEnabled(false);
        TreeSet<String> features = new TreeSet<String>(collator);
        TreeMap<String, TreeSet<String>> typesFeatures;
        boolean firstLoop = true; // needed for retainAll to work
        synchronized(docsSetsTypesFeatures) {
          for (TreeMap<String, TreeMap<String, TreeSet<String>>> sets :
               docsSetsTypesFeatures.values()) {
            typesFeatures = sets.get(keySetName.equals("[Default set]") ?
              "" : keySetName);
            if (typesFeatures != null) {
              for (String typeName : typesFeatures.keySet()) {
                if (typeNames.contains(typeName)) {
                  if (featureCheck.isSelected() && !firstLoop) {
                    features.retainAll(typesFeatures.get(typeName));
                  } else {
                    features.addAll(typesFeatures.get(typeName));
                  }
                }
              }
            } else if (featureCheck.isSelected()) {
              // empty type no features to display
              break;
            }
            typesFeatures = sets.get(responseSetName.equals("[Default set]") ?
              "" : responseSetName);
            if (typesFeatures != null) {
              for (String typeName : typesFeatures.keySet()) {
                if (typeNames.contains(typeName)) {
                  if (featureCheck.isSelected()) {
                    features.retainAll(typesFeatures.get(typeName));
                  } else {
                    features.addAll(typesFeatures.get(typeName));
                  }
                }
              }
            } else if (featureCheck.isSelected()) {
              break;
            }
            firstLoop = false;
          }
        }
        featureList.setModel(new ExtendedListModel(features.toArray()));
        if (features.size() > 0) {
          for (Object value : featuresSelected) {
            // put back the selection if possible
            int index = featureList.getNextMatch(
              (String) value, 0, Position.Bias.Forward);
            if (index != -1) {
              featureList.setSelectedIndex(index);
            }
          }
        }
        featuresSelected = null;
        typeList.setEnabled(true);
        typeCheck.setEnabled(true);
      }
    });

    // when type list selection change
    featureList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (measuresType == CLASSIFICATION_MEASURES) {
          if (typeList.getSelectedIndices().length == 1
           && featureList.getSelectedIndices().length == 1) {
            compareAction.setEnabled(true);
            compareAction.putValue(Action.SHORT_DESCRIPTION,
              "Compare annotations between sets A and B");
          } else {
            compareAction.setEnabled(false);
            compareAction.putValue(Action.SHORT_DESCRIPTION,
              "You must select exactly one type and one feature");
          }
        }
      }
    });

    // when the measure tab selection change
    measureTabbedPane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        int selectedTab = tabbedPane.getSelectedIndex();
        tableTabbedPane.removeAll();
        openDocumentAction.setEnabled(false);
        openAnnotationDiffAction.setEnabled(false);
        if (optionsButton.isSelected()) {
          optionsButton.doClick(); // hide the options panel if shown
        }
        if (tabbedPane.getTitleAt(selectedTab).equals("F-Score")) {
          measuresType = FSCORE_MEASURES;
          compareAction.setEnabled(keySetName != null
                           && responseSetName != null);
          compareAction.putValue(Action.SHORT_DESCRIPTION,
            "Compare annotations between sets A and B");
          tableTabbedPane.addTab("Corpus statistics", null,
            new JScrollPane(annotationTable),
            "Compare each annotation type for the whole corpus");
          tableTabbedPane.addTab("Document statistics", null,
            new JScrollPane(documentTable),
            "Compare each documents in the corpus with theirs annotations");
        } else {
          measuresType = CLASSIFICATION_MEASURES;
          featureList.getListSelectionListeners()[0].valueChanged(null);
          tableTabbedPane.addTab("Document statistics", null,
            new JScrollPane(document2Table),
            "Compare each documents in the corpus with theirs annotations");
          tableTabbedPane.addTab("Confusion Matrices", null,
            new JScrollPane(confusionTable), "Describe how annotations in" +
              " one set are classified in the other and vice versa.");
        }
      }
    });

    // enable/disable toolbar icons according to the document table selection
    documentTable.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          if (e.getValueIsAdjusting()) { return; }
          boolean enabled = documentTable.getSelectedRow() != -1
            && !((String)documentTableModel.getValueAt(
            documentTable.getSelectedRow(), 0)).endsWith("summary");
          openDocumentAction.setEnabled(enabled);
          openAnnotationDiffAction.setEnabled(enabled);
        }
      }
    );

    // enable/disable toolbar icons according to the document 2 table selection
    document2Table.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          if (e.getValueIsAdjusting()) { return; }
          boolean enabled = document2Table.getSelectedRow() != -1
            && !((String)document2TableModel.getValueAt(
              document2Table.getSelectedRow(),
              0)).endsWith("summary");
          openDocumentAction.setEnabled(enabled);
          openAnnotationDiffAction.setEnabled(enabled);
        }
      }
    );

    // double click on a document loads it in the document editor
    documentTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!e.isPopupTrigger()
          && e.getClickCount() == 2
          && openDocumentAction.isEnabled()) {
          openDocumentAction.actionPerformed(null);
        }
      }
    });

    // double click on a document loads it in the document editor
    document2Table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!e.isPopupTrigger()
          && e.getClickCount() == 2
          && openDocumentAction.isEnabled()) {
          openDocumentAction.actionPerformed(null);
        }
      }
    });

    InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = getActionMap();
    inputMap.put(KeyStroke.getKeyStroke("F1"), "help");
    actionMap.put("help", new HelpAction());
  }

  /**
   * Create a table header tool tips from the column name.
   * @param columnName name used for creating the tooltip
   * @return tooltip value
   */
  protected String createToolTipFromColumnName(String columnName) {
    String tooltip;
    if (columnName.equals("Document")
     || columnName.equals("Annotation")) {
      tooltip = null;
    } else if (columnName.equals("Match")) {
      tooltip = "aka Correct";
    } else if (columnName.equals("Only A")) {
      tooltip = "aka Missing";
    } else if (columnName.equals("Only B")) {
      tooltip = "aka Spurious";
    } else if (columnName.equals("Overlap")) {
      tooltip = "aka Partial";
    } else if (columnName.equals("Rec.B/A")) {
      tooltip = "Recall for B relative to A";
    } else if (columnName.equals("Prec.B/A")) {
      tooltip = "Precision for B relative to A";
    } else {
      tooltip = columnName
        .replaceFirst("s.", "score strict")
        .replaceFirst("l.", "score lenient")
        .replaceFirst("a.", "score average")
        .replaceFirst("B.", " BDM");
    }
    return tooltip;
  }

  protected static class ExtendedListModel extends DefaultListModel {
    public ExtendedListModel() {
      super();
    }
    public ExtendedListModel(Object[] elements) {
      super();
      for (Object element : elements) {
        super.addElement(element);
      }
    }
  }

  protected static class ToggleSelectionModel extends DefaultListSelectionModel {
    boolean gestureStarted = false;
    @Override
    public void setSelectionInterval(int index0, int index1) {
      if (isSelectedIndex(index0) && !gestureStarted) {
        super.removeSelectionInterval(index0, index1);
      } else {
        super.addSelectionInterval(index0, index1);
      }
      gestureStarted = true;
    }
    @Override
    public void setValueIsAdjusting(boolean isAdjusting) {
      if (!isAdjusting) {
        gestureStarted = false;
      }
    }
  }

  /**
   * Add a suffix A and B for the first and second selected item.
   * Allows only 2 items to be selected.
   */
  protected static class ToggleSelectionABModel extends DefaultListSelectionModel {
    public ToggleSelectionABModel(JList list) {
      this.list = list;
    }
    @Override
    public void setSelectionInterval(int index0, int index1) {
      ExtendedListModel model = (ExtendedListModel) list.getModel();
      String value = (String) model.getElementAt(index0);
      if (value.endsWith(" (A)") || value.endsWith(" (B)")) {
        // if ends with ' (A)' or ' (B)' then remove the suffix
        model.removeElementAt(index0);
        model.insertElementAt(value.substring(0,
          value.length() - " (A)".length()), index0);
        if (value.endsWith(" (A)")) {
          selectedValueA = null;
        } else {
          selectedValueB = null;
        }
        removeSelectionInterval(index0, index1);
      } else {
        // suffix with ' (A)' or ' (B)' if not already existing
        if (selectedValueA == null) {
          model.removeElementAt(index0);
          model.insertElementAt(value + " (A)", index0);
          selectedValueA = value;
          addSelectionInterval(index0, index1);
        } else if (selectedValueB == null) {
          model.removeElementAt(index0);
          model.insertElementAt(value + " (B)", index0);
          selectedValueB = value;
          addSelectionInterval(index0, index1);
        }
      }
    }
    @Override
    public void clearSelection() {
      selectedValueA = null;
      selectedValueB = null;
      super.clearSelection();
    }
    public String getSelectedValueA() {
      return selectedValueA;
    }
    public String getSelectedValueB() {
      return selectedValueB;
    }
    JList list;
    String selectedValueA, selectedValueB;
  }

  @Override
  public void cleanup(){
    super.cleanup();
    corpus = null;
  }

  @Override
  public void setTarget(Object target){
    if(corpus != null && corpus != target){
      //we already had a different corpus
      corpus.removeCorpusListener(this);
    }
    if(!(target instanceof Corpus)){
      throw new IllegalArgumentException(
        "This view can only be used with a GATE corpus!\n" +
        target.getClass().toString() + " is not a GATE corpus!");
    }
    this.corpus = (Corpus) target;
    corpus.addCorpusListener(this);

    corpusChanged = true;
    if (!isShowing()) { return; }
    if (timerTask != null) { timerTask.cancel(); }
    Date timeToRun = new Date(System.currentTimeMillis() + 2000);
    timerTask = new TimerTask() { @Override
    public void run() {
      readSetsTypesFeatures(0);
    }};
    timer.schedule(timerTask, timeToRun); // add a delay before updating
  }

  @Override
  public void documentAdded(final CorpusEvent e) {
    corpusChanged = true;
    if (!isShowing()) { return; }
    if (timerTask != null) { timerTask.cancel(); }
    Date timeToRun = new Date(System.currentTimeMillis() + 2000);
    timerTask = new TimerTask() { @Override
    public void run() {
      readSetsTypesFeatures(0);
    }};
    timer.schedule(timerTask, timeToRun); // add a delay before updating
  }

  @Override
  public void documentRemoved(final CorpusEvent e) {
    corpusChanged = true;
    if (!isShowing()) { return; }
    if (timerTask != null) { timerTask.cancel(); }
    Date timeToRun = new Date(System.currentTimeMillis() + 2000);
    timerTask = new TimerTask() { @Override
    public void run() {
      readSetsTypesFeatures(0);
    }};
    timer.schedule(timerTask, timeToRun); // add a delay before updating
  }

  /**
   * Update set lists.
   * @param documentStart first document to read in the corpus,
   * the first document of the corpus is 0.
   */
  protected void readSetsTypesFeatures(final int documentStart) {
    if (!isShowing()) { return; }
    corpusChanged = false;
    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run() {
      progressBar.setMaximum(corpus.size() - 1);
      progressBar.setString("Read sets, types, features");
      reloadCacheAction.setEnabled(false);
    }});
    CorpusQualityAssurance.this.setCursor(
      Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    Runnable runnable = new Runnable() { @Override
    public void run() {
    if (docsSetsTypesFeatures.size() != corpus.getDocumentNames().size()
    || !docsSetsTypesFeatures.keySet().containsAll(corpus.getDocumentNames())) {
      if (documentStart == 0) { docsSetsTypesFeatures.clear(); }
      TreeMap<String, TreeMap<String, TreeSet<String>>> setsTypesFeatures;
      TreeMap<String, TreeSet<String>> typesFeatures;
      TreeSet<String> features;
      for (int i = documentStart; i < corpus.size(); i++) {
        // fill in the lists of document, set, type and feature names
        boolean documentWasLoaded = corpus.isDocumentLoaded(i);
        Document document = corpus.get(i);
        if (document != null && document.getAnnotationSetNames() != null) {
          setsTypesFeatures =
            new TreeMap<String, TreeMap<String, TreeSet<String>>>(collator);
          HashSet<String> setNames =
            new HashSet<String>(document.getAnnotationSetNames());
          setNames.add("");
          for (String set : setNames) {
            typesFeatures = new TreeMap<String, TreeSet<String>>(collator);
            AnnotationSet annotations = document.getAnnotations(set);
            for (String type : annotations.getAllTypes()) {
              features = new TreeSet<String>(collator);
              for (Annotation annotation : annotations.get(type)) {
                for (Object featureKey : annotation.getFeatures().keySet()) {
                  features.add((String) featureKey);
                }
              }
              typesFeatures.put(type, features);
            }
            setsTypesFeatures.put(set, typesFeatures);
          }
          docsSetsTypesFeatures.put(document.getName(), setsTypesFeatures);
        }
        if (!documentWasLoaded) {
          corpus.unloadDocument(document);
          Factory.deleteResource(document);
        }
        final int progressValue = i + 1;
        SwingUtilities.invokeLater(new Runnable(){ @Override
        public void run() {
          progressBar.setValue(progressValue);
          if ((progressValue+1) % 5 == 0) {
            // update the set list every 5 documents read
            updateSetList();
          }
        }});
        if (Thread.interrupted()) { return; }
      }
    }
    updateSetList();
    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run(){
      progressBar.setValue(progressBar.getMinimum());
      progressBar.setString("");
      CorpusQualityAssurance.this.setCursor(
        Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      reloadCacheAction.setEnabled(true);
    }});
    }};
    readSetsTypesFeaturesThread = new Thread(runnable, "readSetsTypesFeatures");
    readSetsTypesFeaturesThread.setPriority(Thread.MIN_PRIORITY);
    readSetsTypesFeaturesThread.start();
  }

  protected void updateSetList() {
    final TreeSet<String> setsNames = new TreeSet<String>(collator);
    Set<String> sets;
    boolean firstLoop = true; // needed for retainAll to work
    synchronized(docsSetsTypesFeatures) {
      for (String document : docsSetsTypesFeatures.keySet()) {
        // get the list of set names
        sets = docsSetsTypesFeatures.get(document).keySet();
        if (!sets.isEmpty()) {
          if (setCheck.isSelected() && !firstLoop) {
            setsNames.retainAll(sets);
          } else {
            setsNames.addAll(sets);
          }
        } else if (setCheck.isSelected()) {
          break;
        }
        firstLoop = false;
      }
    }
    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run() {
      // update the UI lists of sets
      setsNames.remove("");
      setsNames.add("[Default set]");
      String keySetNamePrevious = keySetName;
      String responseSetNamePrevious = responseSetName;
      setList.setModel(new ExtendedListModel(setsNames.toArray()));
      if (setsNames.size() > 0) {
        if (keySetNamePrevious != null) {
          // put back the selection if possible
          int index = setList.getNextMatch(
            keySetNamePrevious, 0, Position.Bias.Forward);
          if (index != -1) {
            setList.setSelectedIndex(index);
          }
        }
        if (responseSetNamePrevious != null) {
          // put back the selection if possible
          int index = setList.getNextMatch(
            responseSetNamePrevious, 0, Position.Bias.Forward);
          if (index != -1) {
            setList.setSelectedIndex(index);
          }
        }
      }
    }});
  }

  protected void compareAnnotation() {
    int progressValuePrevious = -1;
    if (readSetsTypesFeaturesThread != null
     && readSetsTypesFeaturesThread.isAlive()) {
      // stop the thread that reads the sets, types and features
      progressValuePrevious = progressBar.getValue();
      readSetsTypesFeaturesThread.interrupt();
    }
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      progressBar.setMaximum(corpus.size() - 1);
      progressBar.setString("Compare annotations");
      setList.setEnabled(false);
      setCheck.setEnabled(false);
      typeList.setEnabled(false);
      typeCheck.setEnabled(false);
      featureList.setEnabled(false);
      featureCheck.setEnabled(false);
      optionsButton.setEnabled(false);
      measureTabbedPane.setEnabled(false);
      measureList.setEnabled(false);
      exportToHtmlAction.setEnabled(false);
      reloadCacheAction.setEnabled(false);
    }});

    boolean useBdm = false;
    if (measuresType == FSCORE_MEASURES) {
      differsByDocThenType.clear();
      documentNames.clear();
      for (Object measure : measureList.getSelectedValues()) {
        if (((String) measure).contains("BDM")) { useBdm = true; break; }
      }
    }
    List<ClassificationMeasures> classificationMeasuresList =
      new ArrayList<ClassificationMeasures>();
    List<OntologyMeasures> documentOntologyMeasuresList =
      new ArrayList<OntologyMeasures>();
    List<OntologyMeasures> annotationOntologyMeasuresList =
      new ArrayList<OntologyMeasures>();

    // for each document
    for (int row = 0; row < corpus.size(); row++) {
      boolean documentWasLoaded = corpus.isDocumentLoaded(row);
      Document document = corpus.get(row);
      documentNames.add(document.getName());
      Set<Annotation> keys = new HashSet<Annotation>();
      Set<Annotation> responses = new HashSet<Annotation>();
      // get annotations from selected annotation sets
      if (keySetName.equals("[Default set]")) {
        keys = document.getAnnotations();
      } else if (document.getAnnotationSetNames() != null
      && document.getAnnotationSetNames().contains(keySetName)) {
        keys = document.getAnnotations(keySetName);
      }
      if (responseSetName.equals("[Default set]")) {
        responses = document.getAnnotations();
      } else if (document.getAnnotationSetNames() != null
      && document.getAnnotationSetNames()
        .contains(responseSetName)) {
        responses = document.getAnnotations(responseSetName);
      }
      if (!documentWasLoaded) { // in case of datastore
        corpus.unloadDocument(document);
        Factory.deleteResource(document);
      }

      // add data to the fscore document table
      if (measuresType == FSCORE_MEASURES) {
        types.clear();
        for (Object type : typeList.getSelectedValues()) {
          types.add((String) type);
        }
        if (typeList.isSelectionEmpty()) {
          for (int i = 0; i < typeList.getModel().getSize(); i++) {
            types.add((String) typeList.getModel().getElementAt(i));
          }
        }
        Set<String> featureSet = new HashSet<String>();
        for (Object feature : featureList.getSelectedValues()) {
          featureSet.add((String) feature);
        }
        HashMap<String, AnnotationDiffer> differsByType =
          new HashMap<String, AnnotationDiffer>();
        AnnotationDiffer differ;
        Set<Annotation> keysIter = new HashSet<Annotation>();
        Set<Annotation> responsesIter = new HashSet<Annotation>();
        for (String type : types) {
          if (!keys.isEmpty() && !types.isEmpty()) {
            keysIter = ((AnnotationSet)keys).get(type);
          }
          if (!responses.isEmpty() && !types.isEmpty()) {
            responsesIter = ((AnnotationSet)responses).get(type);
          }
          differ = new AnnotationDiffer();
          differ.setSignificantFeaturesSet(featureSet);
          differ.calculateDiff(keysIter, responsesIter); // compare
          differsByType.put(type, differ);
        }
        differsByDocThenType.add(differsByType);
        differ = new AnnotationDiffer(differsByType.values());
        List<String> measuresRow;
        if (useBdm) {
          OntologyMeasures ontologyMeasures = new OntologyMeasures();
          ontologyMeasures.setBdmFile(bdmFileUrl);
          ontologyMeasures.calculateBdm(differsByType.values());
          documentOntologyMeasuresList.add(ontologyMeasures);
          measuresRow = ontologyMeasures.getMeasuresRow(
            measureList.getSelectedValues(),
            documentNames.get(documentNames.size()-1));
        } else {
          measuresRow = differ.getMeasuresRow(measureList.getSelectedValues(),
            documentNames.get(documentNames.size()-1));
        }
        documentTableModel.addRow(measuresRow.toArray());

        // add data to the classification document table
      } else if (measuresType == CLASSIFICATION_MEASURES
             && !keys.isEmpty() && !responses.isEmpty()) {
        ClassificationMeasures classificationMeasures =
          new ClassificationMeasures();
        classificationMeasures.calculateConfusionMatrix(
          (AnnotationSet) keys, (AnnotationSet) responses,
          (String) typeList.getSelectedValue(),
          (String) featureList.getSelectedValue(),
          verboseOptionCheckBox.isSelected());
        classificationMeasuresList.add(classificationMeasures);
        List<String> measuresRow = classificationMeasures.getMeasuresRow(
          measure2List.getSelectedValues(),
          documentNames.get(documentNames.size()-1));
        document2TableModel.addRow(measuresRow.toArray());
        List<List<String>> matrix = classificationMeasures
          .getConfusionMatrix(documentNames.get(documentNames.size()-1));
        for (List<String> matrixRow : matrix) {
          while (confusionTableModel.getColumnCount() < matrix.size()) {
            confusionTableModel.addColumn(" ");
          }
          confusionTableModel.addRow(matrixRow.toArray());
        }
      }
      final int progressValue = row + 1;
      SwingUtilities.invokeLater(new Runnable(){ @Override
      public void run() {
        progressBar.setValue(progressValue);
      }});
    } // for (int row = 0; row < corpus.size(); row++)

    // add data to the fscore annotation table
    if (measuresType == FSCORE_MEASURES) {
      for (String type : types) {
        ArrayList<AnnotationDiffer> differs = new ArrayList<AnnotationDiffer>();
        for (HashMap<String, AnnotationDiffer> differsByType :
              differsByDocThenType) {
          differs.add(differsByType.get(type));
        }
        List<String> measuresRow;
        if (useBdm) {
          OntologyMeasures ontologyMeasures = new OntologyMeasures();
          ontologyMeasures.setBdmFile(bdmFileUrl);
          ontologyMeasures.calculateBdm(differs);
          annotationOntologyMeasuresList.add(ontologyMeasures);
          measuresRow = ontologyMeasures.getMeasuresRow(
            measureList.getSelectedValues(), type);
        } else {
          AnnotationDiffer differ = new AnnotationDiffer(differs);
          measuresRow = differ.getMeasuresRow(
            measureList.getSelectedValues(), type);
        }
        annotationTableModel.addRow(measuresRow.toArray());
      }
    }

    // add summary rows to the fscore tables
    if (measuresType == FSCORE_MEASURES) {
      if (useBdm) {
        OntologyMeasures ontologyMeasures =
          new OntologyMeasures(documentOntologyMeasuresList);
        printSummary(ontologyMeasures, documentTableModel, 5,
          documentTableModel.getRowCount(), measureList.getSelectedValues());
        ontologyMeasures = new OntologyMeasures(annotationOntologyMeasuresList);
        printSummary(ontologyMeasures, annotationTableModel, 5,
          annotationTableModel.getRowCount(), measureList.getSelectedValues());
      } else {
        List<AnnotationDiffer> differs = new ArrayList<AnnotationDiffer>();
        for (Map<String, AnnotationDiffer> differsByType :
              differsByDocThenType) {
          differs.addAll(differsByType.values());
        }
        AnnotationDiffer differ = new AnnotationDiffer(differs);
        printSummary(differ, documentTableModel, 5,
          documentTableModel.getRowCount(), measureList.getSelectedValues());
        printSummary(differ, annotationTableModel, 5,
          annotationTableModel.getRowCount(), measureList.getSelectedValues());
      }

      // add summary rows to the classification tables
    } else if (measuresType == CLASSIFICATION_MEASURES) {
      ClassificationMeasures classificationMeasures =
        new ClassificationMeasures(classificationMeasuresList);
      printSummary(classificationMeasures, document2TableModel, 3,
        document2TableModel.getRowCount(), measure2List.getSelectedValues());
      List<List<String>> matrix = classificationMeasures
        .getConfusionMatrix("Whole corpus");
      int insertionRow = 0;
      for (List<String> row : matrix) {
        while (confusionTableModel.getColumnCount() < matrix.size()) {
          confusionTableModel.addColumn(" ");
        }
        confusionTableModel.insertRow(insertionRow++, row.toArray());
      }
    }

    SwingUtilities.invokeLater(new Runnable(){ @Override
    public void run(){
      progressBar.setValue(progressBar.getMinimum());
      progressBar.setString("");
      setList.setEnabled(true);
      setCheck.setEnabled(true);
      typeList.setEnabled(true);
      typeCheck.setEnabled(true);
      featureList.setEnabled(true);
      featureCheck.setEnabled(true);
      optionsButton.setEnabled(true);
      measureTabbedPane.setEnabled(true);
      measureList.setEnabled(true);
      exportToHtmlAction.setEnabled(true);
      reloadCacheAction.setEnabled(true);
    }});
    if (progressValuePrevious > -1) {
      // restart the thread where it was interrupted
      readSetsTypesFeatures(progressValuePrevious);
    }
  }

  protected void printSummary(Object measureObject,
                              DefaultTableModel tableModel, int columnGroupSize,
                              int insertionRow, Object[] measures) {
    AnnotationDiffer differ = null;
    ClassificationMeasures classificationMeasures = null;
    OntologyMeasures ontologyMeasures = null;
    if (measureObject instanceof AnnotationDiffer) {
      differ = (AnnotationDiffer) measureObject;
    } else if (measureObject instanceof ClassificationMeasures) {
      classificationMeasures = (ClassificationMeasures) measureObject;
    } else if (measureObject instanceof OntologyMeasures) {
      ontologyMeasures = (OntologyMeasures) measureObject;
    }
    NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
    f.setMaximumFractionDigits(4);
    f.setMinimumFractionDigits(4);
    f.setRoundingMode(RoundingMode.HALF_UP);
    List<Object> values = new ArrayList<Object>();

    // average measures by document
    values.add("Macro summary");
    for (int col = 1; col < tableModel.getColumnCount(); col++) {
      if (col < columnGroupSize) {
        values.add("");
      } else {
        float sumF = 0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
          try {
            sumF += Float.parseFloat((String) tableModel.getValueAt(row, col));
          } catch(NumberFormatException e) {
            // do nothing
          }
        }
        values.add(f.format(sumF / tableModel.getRowCount()));
      }
    }
    tableModel.insertRow(insertionRow, values.toArray());

    // sum counts and recalculate measures like the corpus is one document
    values.clear();
    values.add("Micro summary");
    for (int col = 1; col < columnGroupSize; col++) {
      int sum = 0;
      for (int row = 0; row < tableModel.getRowCount()-1; row++) {
        try {
          sum += Integer.parseInt((String) tableModel.getValueAt(row, col));
        } catch(NumberFormatException e) {
          // do nothing
        }
      }
      values.add(Integer.toString(sum));
    }
    if (measureObject instanceof OntologyMeasures) {
      List<AnnotationDiffer> differs = new ArrayList<AnnotationDiffer>(
        ontologyMeasures.getDifferByTypeMap().values());
      differ = new AnnotationDiffer(differs);
    }
    for (Object object : measures) {
      String measure = (String) object;
      int index = measure.indexOf('-');
      double beta = (index == -1) ?
        1 : Double.valueOf(measure.substring(1, index));
      if (measure.endsWith("strict")) {
        values.add(f.format(differ.getPrecisionStrict()));
        values.add(f.format(differ.getRecallStrict()));
        values.add(f.format(differ.getFMeasureStrict(beta)));
      } else if (measure.endsWith("strict BDM")) {
        values.add(f.format(ontologyMeasures.getPrecisionStrictBdm()));
        values.add(f.format(ontologyMeasures.getRecallStrictBdm()));
        values.add(f.format(ontologyMeasures.getFMeasureStrictBdm(beta)));
      } else if (measure.endsWith("lenient")) {
        values.add(f.format(differ.getPrecisionLenient()));
        values.add(f.format(differ.getRecallLenient()));
        values.add(f.format(differ.getFMeasureLenient(beta)));
      } else if (measure.endsWith("lenient BDM")) {
        values.add(f.format(ontologyMeasures.getPrecisionLenientBdm()));
        values.add(f.format(ontologyMeasures.getRecallLenientBdm()));
        values.add(f.format(ontologyMeasures.getFMeasureLenientBdm(beta)));
      } else if (measure.endsWith("average")) {
        values.add(f.format(differ.getPrecisionAverage()));
        values.add(f.format(differ.getRecallAverage()));
        values.add(f.format(differ.getFMeasureAverage(beta)));
      } else if (measure.endsWith("average BDM")) {
        values.add(f.format(ontologyMeasures.getPrecisionAverageBdm()));
        values.add(f.format(ontologyMeasures.getRecallAverageBdm()));
        values.add(f.format(ontologyMeasures.getFMeasureAverageBdm(beta)));
      } else if (measure.equals("Observed agreement")) {
        values.add(f.format(classificationMeasures.getObservedAgreement()));
      } else if (measure.equals("Cohen's Kappa")) {
        float result = classificationMeasures.getKappaCohen();
        values.add(Float.isNaN(result) ? "" : f.format(result));
      } else if (measure.equals("Pi's Kappa")) {
        float result = classificationMeasures.getKappaPi();
        values.add(Float.isNaN(result) ? "" : f.format(result));
      }
    }
    tableModel.insertRow(insertionRow + 1, values.toArray());
  }

  protected class SetBdmFileAction extends AbstractAction {
    public SetBdmFileAction() {
      super("Browse");
      putValue(SHORT_DESCRIPTION, "Choose a BDM file to compute BDM measures");
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
      XJFileChooser fileChooser = MainFrame.getFileChooser();
      fileChooser.setAcceptAllFileFilterUsed(true);
      fileChooser.setDialogTitle("Choose a BDM file");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setResource(
        CorpusQualityAssurance.class.getName() + ".BDMfile");
      int res = fileChooser.showOpenDialog(CorpusQualityAssurance.this);
      if (res != JFileChooser.APPROVE_OPTION) { return; }
      try {
        bdmFileUrl = fileChooser.getSelectedFile().toURI().toURL();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Update document table.
   */
  protected class CompareAction extends AbstractAction {
    public CompareAction() {
      super("Compare");
      putValue(SHORT_DESCRIPTION, "Compare annotations between sets A and B");
      putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
      putValue(SMALL_ICON, new ProgressIcon(MainFrame.ICON_DIMENSION));
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new ProgressIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
      boolean useBdm = false;
      for (Object measure : measureList.getSelectedValues()) {
        if (((String) measure).contains("BDM")) { useBdm = true; break; }
      }
      if (useBdm && measuresType == FSCORE_MEASURES && bdmFileUrl == null) {
        new SetBdmFileAction().actionPerformed(null);
        if (bdmFileUrl == null) { return; }
      }

      CorpusQualityAssurance.this.setCursor(
        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      setEnabled(false);

      Runnable runnable = new Runnable() { @Override
      public void run() {
      if (measuresType == FSCORE_MEASURES) {
        documentTableModel = new DefaultTableModel();
        annotationTableModel = new DefaultTableModel();
        documentTableModel.addColumn("Document");
        annotationTableModel.addColumn("Annotation");
        documentTableModel.addColumn("Match");
        annotationTableModel.addColumn("Match");
        documentTableModel.addColumn("Only A");
        annotationTableModel.addColumn("Only A");
        documentTableModel.addColumn("Only B");
        annotationTableModel.addColumn("Only B");
        documentTableModel.addColumn("Overlap");
        annotationTableModel.addColumn("Overlap");
        for (Object measure : measureList.getSelectedValues()) {
          String measureString = ((String) measure)
            .replaceFirst("score strict", "s.")
            .replaceFirst("score lenient", "l.")
            .replaceFirst("score average", "a.")
            .replaceFirst(" BDM", "B.");
          documentTableModel.addColumn("Prec.B/A");
          annotationTableModel.addColumn("Prec.B/A");
          documentTableModel.addColumn("Rec.B/A");
          annotationTableModel.addColumn("Rec.B/A");
          documentTableModel.addColumn(measureString);
          annotationTableModel.addColumn(measureString);
        }
        compareAnnotation(); // do all the computation
        // update data

        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          // redraw document table
          documentTable.setModel(documentTableModel);
          for (int col = 0; col < documentTable.getColumnCount(); col++) {
            documentTable.setComparator(col, doubleComparator);
          }
          documentTable.setComparator(0, totalComparator);
          documentTable.setSortedColumn(0);
          // redraw annotation table
          annotationTable.setModel(annotationTableModel);
          for (int col = 0; col < annotationTable.getColumnCount(); col++) {
            annotationTable.setComparator(col, doubleComparator);
          }
          annotationTable.setComparator(0, totalComparator);
          annotationTable.setSortedColumn(0);
          CorpusQualityAssurance.this.setCursor(
            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          setEnabled(true);
        }});

      } else if (measuresType == CLASSIFICATION_MEASURES) {
        document2TableModel = new DefaultTableModel();
        document2TableModel.addColumn("Document");
        document2TableModel.addColumn("Agreed");
        document2TableModel.addColumn("Total");
        for (Object measure : measure2List.getSelectedValues()) {
          document2TableModel.addColumn(measure);
        }
        confusionTableModel = new DefaultTableModel();
        compareAnnotation(); // do all the computation
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          document2Table.setSortable(false);
          document2Table.setModel(document2TableModel);
          document2Table.setComparator(0, totalComparator);
          document2Table.setComparator(1, doubleComparator);
          document2Table.setSortedColumn(0);
          document2Table.setSortable(true);
          confusionTable.setModel(confusionTableModel);
          CorpusQualityAssurance.this.setCursor(
            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          setEnabled(true);
        }});
      }
      }};
      Thread thread = new Thread(runnable,  "CompareAction");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }
  }

  class OpenDocumentAction extends AbstractAction{
    public OpenDocumentAction(){
      super("Open documents", new DocumentIcon(MainFrame.ICON_DIMENSION));
      putValue(SHORT_DESCRIPTION,
        "Opens document for the selected row in a document editor");
      putValue(MNEMONIC_KEY, KeyEvent.VK_UP);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new DocumentIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
      final Document document = corpus.get(measuresType == FSCORE_MEASURES ?
        documentTable.rowViewToModel(documentTable.getSelectedRow())
      : document2Table.rowViewToModel(document2Table.getSelectedRow()));
      SwingUtilities.invokeLater( new Runnable() { @Override
      public void run() {
        MainFrame.getInstance().select(document);
      }});
    }
  }

  class OpenAnnotationDiffAction extends AbstractAction{
    public OpenAnnotationDiffAction(){
      super("Open annotation diff", new AnnotationDiffIcon(MainFrame.ICON_DIMENSION));
      
      putValue(SHORT_DESCRIPTION,
        "Opens annotation diff for the selected row in the document table");
      putValue(MNEMONIC_KEY, KeyEvent.VK_RIGHT);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new AnnotationDiffIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    @Override
    public void actionPerformed(ActionEvent e){
      Document document = corpus.get(measuresType == FSCORE_MEASURES ?
        documentTable.rowViewToModel(documentTable.getSelectedRow())
      : document2Table.rowViewToModel(document2Table.getSelectedRow()));
      String documentName = document.getName();
      String annotationType = (String) typeList.getSelectedValue();
      Set<String> featureSet = new HashSet<String>();
      for (Object feature : featureList.getSelectedValues()) {
        featureSet.add((String) feature);
      }
      AnnotationDiffGUI frame = new AnnotationDiffGUI("Annotation Difference",
        documentName, documentName, keySetName,
        responseSetName, annotationType, featureSet);
      frame.pack();
      frame.setLocationRelativeTo(MainFrame.getInstance());
      frame.setVisible(true);
    }
  }

  protected class ExportToHtmlAction extends AbstractAction{
    public ExportToHtmlAction(){
      super("Export to HTML");
      putValue(SHORT_DESCRIPTION, "Export the tables to HTML");
      putValue(SMALL_ICON, new DownloadIcon(MainFrame.ICON_DIMENSION));
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new DownloadIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt){
      XJFileChooser fileChooser = MainFrame.getFileChooser();
      fileChooser.setAcceptAllFileFilterUsed(true);
      fileChooser.setDialogTitle("Choose a file where to export the tables");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      ExtensionFileFilter filter = new ExtensionFileFilter("HTML files","html");
      fileChooser.addChoosableFileFilter(filter);
      String title = corpus.getName();
        title += "_" + keySetName;
        title += "-" + responseSetName;
      fileChooser.setFileName(title + ".html");
      fileChooser.setResource(CorpusQualityAssurance.class.getName());
      int res = fileChooser.showSaveDialog(CorpusQualityAssurance.this);
      if (res != JFileChooser.APPROVE_OPTION) { return; }

      File saveFile = fileChooser.getSelectedFile();
      Writer fw = null;
      try{
        fw = new BufferedWriter(new FileWriter(saveFile));

        // Header, Title
        fw.write(BEGINHTML + nl);
        fw.write(BEGINHEAD);
        fw.write(title);
        fw.write(ENDHEAD + nl);
        fw.write("<H1>Corpus Quality Assurance</H1>" + nl);
        fw.write("<P>Corpus: " + corpus.getName() + "<BR>" + nl);
        fw.write("Key set: " + keySetName + "<BR>" + nl);
        fw.write("Response set: " + responseSetName + "<BR>" + nl);
        fw.write("Types: "
          + Strings.toString(typeList.getSelectedValues()) + "<BR>" + nl);
        fw.write("Features: "
          + Strings.toString(featureList.getSelectedValues()) + "</P>" + nl);
        fw.write("<P>&nbsp;</P>" + nl);

        ArrayList<JTable> tablesToExport = new ArrayList<JTable>();
        tablesToExport.add(annotationTable);
        tablesToExport.add(documentTable);
        tablesToExport.add(document2Table);
        tablesToExport.add(confusionTable);
        for (JTable table : tablesToExport) {
          fw.write(BEGINTABLE + nl + "<TR>" + nl);
          for(int col = 0; col < table.getColumnCount(); col++){
            fw.write("<TH align=\"left\">"
              + table.getColumnName(col) + "</TH>" + nl);
          }
          fw.write("</TR>" + nl);
          for(int row = 0; row < table.getRowCount(); row ++){
            fw.write("<TR>" + nl);
            for(int col = 0; col < table.getColumnCount(); col++){
              String value = (String) table.getValueAt(row, col);
              if (value == null) { value = ""; }
              fw.write("<TD>" + value  + "</TD>" + nl);
            }
            fw.write("</TR>" + nl);
          }
          fw.write(ENDTABLE + nl);
          fw.write("<P>&nbsp;</P>" + nl);
        }

        fw.write(ENDHTML + nl);
        fw.flush();

      } catch(IOException ioe){
        JOptionPane.showMessageDialog(CorpusQualityAssurance.this,
          ioe.toString(), "GATE", JOptionPane.ERROR_MESSAGE);
        ioe.printStackTrace();

      } finally {
        if (fw != null) {
          try {
            fw.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    final String nl = Strings.getNl();
    static final String BEGINHTML =
      "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" +
      "<html>";
    static final String ENDHTML = "</body></html>";
    static final String BEGINHEAD = "<head>" +
      "<meta content=\"text/html; charset=utf-8\" http-equiv=\"content-type\">"
      + "<title>";
    static final String ENDHEAD = "</title></head><body>";
    static final String BEGINTABLE = "<table cellpadding=\"0\" border=\"1\">";
    static final String ENDTABLE = "</table>";
  }

  class ReloadCacheAction extends AbstractAction{
    public ReloadCacheAction(){
      super("Reload cache", new RefreshIcon(MainFrame.ICON_DIMENSION));
      putValue(SHORT_DESCRIPTION,
        "Reload cache for set, type and feature names list");
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new RefreshIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
      docsSetsTypesFeatures.clear();
      readSetsTypesFeatures(0);
    }
  }

  protected class HelpAction extends AbstractAction {
    public HelpAction() {
      super();
      putValue(SHORT_DESCRIPTION, "User guide for this tool");
      putValue(SMALL_ICON, new HelpIcon(MainFrame.ICON_DIMENSION));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
    }
    
    @Override
    public void setEnabled(boolean enabled) {
      putValue(SMALL_ICON, new HelpIcon(MainFrame.ICON_DIMENSION, !enabled));
      super.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      MainFrame.getInstance().showHelpFrame(
        "sec:eval:corpusqualityassurance",
        CorpusQualityAssurance.class.getName());
    }
  }

  // local variables
  protected Corpus corpus;
  protected boolean corpusChanged;
  protected TreeSet<String> types;
  /** cache for document*set*type*feature names */
  final protected Map<String, TreeMap<String, TreeMap<String, TreeSet<String>>>>
    docsSetsTypesFeatures = Collections.synchronizedMap(new LinkedHashMap
      <String, TreeMap<String, TreeMap<String, TreeSet<String>>>>());
  /** ordered by document as in the <code>corpus</code>
   *  then contains (annotation type * AnnotationDiffer) */
  protected ArrayList<HashMap<String, AnnotationDiffer>> differsByDocThenType =
    new ArrayList<HashMap<String, AnnotationDiffer>>();
  protected ArrayList<String> documentNames = new ArrayList<String>();
  protected String keySetName;
  protected String responseSetName;
  protected Object[] typesSelected;
  protected Object[] featuresSelected;
  protected Timer timer = new Timer("CorpusQualityAssurance", true);
  protected TimerTask timerTask;
  protected Thread readSetsTypesFeaturesThread;
  /** FSCORE_MEASURES or CLASSIFICATION_MEASURES */
  protected int measuresType;
  protected static final int FSCORE_MEASURES = 0;
  protected static final int CLASSIFICATION_MEASURES = 1;
  protected Collator collator;
  protected Comparator<String> doubleComparator;
  protected Comparator<String> totalComparator;
  protected OptionsMap userConfig = Gate.getUserConfig();
  protected URL bdmFileUrl;

  // user interface components
  protected XJTable documentTable;
  protected DefaultTableModel documentTableModel;
  protected XJTable annotationTable;
  protected DefaultTableModel annotationTableModel;
  protected XJTable document2Table;
  protected DefaultTableModel document2TableModel;
  protected XJTable confusionTable;
  protected DefaultTableModel confusionTableModel;
  protected JTabbedPane tableTabbedPane;
  protected JList setList;
  protected JList typeList;
  protected JList featureList;
  protected JToggleButton optionsButton;
  protected JTabbedPane measureTabbedPane;
  protected JList measureList;
  protected JList measure2List;
  protected JCheckBox setCheck;
  protected JCheckBox typeCheck;
  protected JCheckBox featureCheck;
  protected JProgressBar progressBar;
  protected JCheckBox verboseOptionCheckBox;

  // actions
  protected OpenDocumentAction openDocumentAction;
  protected OpenAnnotationDiffAction openAnnotationDiffAction;
  protected ExportToHtmlAction exportToHtmlAction;
  protected ReloadCacheAction reloadCacheAction;
  protected CompareAction compareAction;
}
