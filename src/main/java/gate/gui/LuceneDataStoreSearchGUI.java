/*
 *  Copyright (c) 1998-2009, The University of Sheffield and Ontotext.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz, Dec 11, 2007
 *  based on Niraj Aswani GUI
 *
 *  $Id$
 */

package gate.gui;

import gate.Corpus;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.corpora.SerialCorpusImpl;
import gate.creole.AbstractVisualResource;
import gate.creole.annic.Constants;
import gate.creole.annic.Hit;
import gate.creole.annic.Pattern;
import gate.creole.annic.PatternAnnotation;
import gate.creole.annic.SearchException;
import gate.creole.annic.Searcher;
import gate.creole.annic.lucene.QueryParser;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.event.DatastoreEvent;
import gate.event.DatastoreListener;
import gate.gui.docview.AnnotationSetsView;
import gate.gui.docview.AnnotationStack;
import gate.gui.docview.AnnotationStack.StackMouseListener;
import gate.gui.docview.TextualDocumentView;
import gate.persist.LuceneDataStoreImpl;
import gate.persist.PersistenceException;
import gate.persist.SerialDataStore;
import gate.swing.BlockingGlassPane;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.ExtensionFileFilter;
import gate.util.GateRuntimeException;
import gate.util.Strings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

/**
 * GUI allowing to write a query with a JAPE derived syntax for querying
 * a Lucene Datastore and display the results with a stacked view of the
 * annotations and their values. <br>
 * This VR is associated to {@link gate.creole.annic.SearchableDataStore}.
 * You have to set the target with setTarget(). <br>
 * Features: query auto-completion, syntactic error checker, display of
 * very big values, export of results in a file, 16 types of statistics,
 * store display settings in gate config.
 */
@SuppressWarnings("serial")
@CreoleResource(name = "Lucene Datastore Searcher", guiType = GuiType.LARGE, resourceDisplayed = "gate.creole.annic.SearchableDataStore", comment = "GUI allowing to write a query with a JAPE derived syntax for querying\n"
        + " a Lucene Datastore and display the results with a stacked view of the\n"
        + " annotations and their values.", helpURL = "http://gate.ac.uk/userguide/chap:annic")
public class LuceneDataStoreSearchGUI extends AbstractVisualResource implements
                                                                    DatastoreListener {

  /** The GUI is associated with the AnnicSearchPR */
  private Object target;

  /** instances of results associated found in the document */
  private List<Hit> results;

  /** Annotation types as keys and list of features as values */
  private Map<String, List<String>> allAnnotTypesAndFeaturesFromDatastore;

  private Map<String, Set<String>> populatedAnnotationTypesAndFeatures;

  /** Lists the results found by the query */
  private XJTable resultTable;

  private ResultTableModel resultTableModel;

  /** Display the stack view configuration window. */
  private JButton configureStackViewButton;

  /**
   * Contains statistics for the corpus and the annotation set selected.
   */
  private XJTable globalStatisticsTable;

  /** Contains statistics of one row each. */
  private XJTable oneRowStatisticsTable;

  /** Comparator for Integer in statistics tables. */
  private Comparator<Integer> integerComparator;

  /** Collator for String with insensitive case. */
  private java.text.Collator stringCollator;

  /** Horizontal split between the results pane and statistics pane. */
  private JSplitPane bottomSplitPane;

  /** Display statistics on the datastore. */
  private JTabbedPane statisticsTabbedPane;

  /** Text Area that contains the query */
  private QueryTextArea queryTextArea;

  private JComboBox<String> corpusToSearchIn;

  private JComboBox<String> annotationSetsToSearchIn;

  /** list of IDs available in datastore */
  private List<Object> corpusIds;

  /*** AnnotationSet IDS the structure is: CorpusID;annotationSetName */
  private String[] annotationSetIDsFromDataStore;

  private JSlider numberOfResultsSlider;

  /** Number of tokens to be shown as context in the results */
  private JSlider contextSizeSlider;

  /** Gives the page number displayed in the results. */
  private JLabel titleResults;

  /** Show the next page of results. */
  private JButton nextResults;

  /** Number of the page of results. */
  private int pageOfResults;

  /** Number of row to show in the results. */
  int noOfResults;

  /** JPanel that contains the central panel of stack rows. */
  private AnnotationStack centerPanel;

  private ExecuteQueryAction executeQueryAction;

  private NextResultsAction nextResultsAction;

  private ExportResultsAction exportResultsAction;

  /** Current instance of the stack view frame. */
  private ConfigureStackViewFrame configureStackViewFrame;

  /** Names of the columns for stackRows data. */
  String[] columnNames = {"Display", "Shortcut", "Annotation type", "Feature",
      "Crop"};

  /** Column (second dimension) of stackRows double array. */
  static private final int DISPLAY = 0;

  /** Column (second dimension) of stackRows double array. */
  static private final int SHORTCUT = 1;

  /** Column (second dimension) of stackRows double array. */
  static private final int ANNOTATION_TYPE = 2;

  /** Column (second dimension) of stackRows double array. */
  static private final int FEATURE = 3;

  /** Column (second dimension) of stackRows double array. */
  static private final int CROP = 4;

  /** Maximum number of stackRow */
  static private final int maxStackRows = 100;

  /** Number of stackRows. */
  private int numStackRows = 0;

  /** Double array that contains [row, column] of the stackRows data. */
  private String[][] stackRows =
          new String[maxStackRows + 1][columnNames.length];

  private ConfigureStackViewTableModel configureStackViewTableModel;

  private DefaultTableModel oneRowStatisticsTableModel;

  private DefaultTableModel globalStatisticsTableModel;

  /** Contains the tooltips of the first column. */
  private Vector<String> oneRowStatisticsTableToolTips;

  /** Searcher object obtained from the datastore */
  private Searcher searcher;

  /** true if there was an error on the last query. */
  private boolean errorOnLastQuery;

  /**
   * Called when a View is loaded in GATE.
   */
  @Override
  public Resource init() {

    results = new ArrayList<Hit>();
    allAnnotTypesAndFeaturesFromDatastore = new HashMap<String, List<String>>();
    corpusIds = new ArrayList<Object>();
    populatedAnnotationTypesAndFeatures = new HashMap<String, Set<String>>();
    noOfResults = 0;
    for(int row = 0; row <= maxStackRows; row++) {
      stackRows[row][DISPLAY] = "true";
      stackRows[row][SHORTCUT] = "";
      stackRows[row][ANNOTATION_TYPE] = "";
      stackRows[row][FEATURE] = "";
      stackRows[row][CROP] = "Crop end";
    }

    // read the user config data for annotation stack rows
    String prefix = LuceneDataStoreSearchGUI.class.getName() + ".";
    if(Gate.getUserConfig().containsKey(prefix + "rows")) {
      Map<String, String> map = Gate.getUserConfig().getMap(prefix + "rows");
      for(int row = 0; row < maxStackRows; row++) {
        if(!map.containsKey(columnNames[0] + '_' + row)) {
          break;
        }
        for(int col = 0; col < columnNames.length; col++) {
          stackRows[row][col] = map.get(columnNames[col] + '_' + row);
        }
        numStackRows++;
      }
    }

    // initialize GUI
    initGui();
    updateViews();
    validate();
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        queryTextArea.requestFocusInWindow();
      }
    });

    return this;
  }

  /**
   * Called when the user close the datastore.
   */
  @Override
  public void cleanup() {
    // no parent so need to be disposed explicitly
    configureStackViewFrame.dispose();
  }

  /**
   * Initialize the GUI.
   */
  protected void initGui() {

    // see the global layout schema at the end
    setLayout(new BorderLayout());

    stringCollator = java.text.Collator.getInstance();
    stringCollator.setStrength(java.text.Collator.TERTIARY);

    Comparator<String> lastWordComparator = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        if(o1 == null || o2 == null) {
          return 0;
        }
        return stringCollator.compare(
                o1.substring(o1.trim().lastIndexOf(' ') + 1),
                o2.substring(o2.trim().lastIndexOf(' ') + 1));
      }
    };

    integerComparator = new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        if(o1 == null || o2 == null) {
          return 0;
        }
        return o1.compareTo(o2);
      }
    };

    /**********************************
     * Stack view configuration frame *
     **********************************/

    configureStackViewFrame =
            new ConfigureStackViewFrame("Stack view configuration");
    configureStackViewFrame.setIconImage(((ImageIcon)MainFrame
            .getIcon("crystal-clear-action-window-new")).getImage());
    configureStackViewFrame
            .setLocationRelativeTo(LuceneDataStoreSearchGUI.this);
    configureStackViewFrame.getRootPane()
            .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke("ESCAPE"), "close row manager");
    configureStackViewFrame.getRootPane().getActionMap()
            .put("close row manager", new AbstractAction() {
              @Override
              public void actionPerformed(ActionEvent e) {
                configureStackViewFrame.setVisible(false);
              }
            });
    configureStackViewFrame.validate();
    configureStackViewFrame.setSize(200, 300);
    configureStackViewFrame.pack();

    // called when Gate is exited, in case the user doesn't close the
    // datastore
    MainFrame.getInstance().addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        // no parent so need to be disposed explicitly
        configureStackViewFrame.dispose();
      }
    });

    /*************
     * Top panel *
     *************/

    JPanel topPanel = new JPanel(new GridBagLayout());
    topPanel.setOpaque(false);
    topPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 0, 3));
    GridBagConstraints gbc = new GridBagConstraints();

    // first column, three rows span
    queryTextArea = new QueryTextArea();
    queryTextArea.setToolTipText("<html>Enter a query to search the datastore."
            + "<br><small>'{' or '.' activate auto-completion."
            + "<br>Ctrl+Enter add a new line.</small></html>");
    queryTextArea.setLineWrap(true);
    gbc.gridheight = 3;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(0, 0, 0, 4);
    topPanel.add(new JScrollPane(queryTextArea), gbc);
    gbc.gridheight = 1;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.insets = new Insets(0, 0, 0, 0);

    // second column, first row
    gbc.gridx = GridBagConstraints.RELATIVE;
    topPanel.add(new JLabel("Corpus: "), gbc);
    corpusToSearchIn = new JComboBox<String>();
    corpusToSearchIn.addItem(Constants.ENTIRE_DATASTORE);
    corpusToSearchIn.setPrototypeDisplayValue(Constants.ENTIRE_DATASTORE);
    corpusToSearchIn.setToolTipText("Select the corpus to search in.");
    if(target == null || target instanceof Searcher) {
      corpusToSearchIn.setEnabled(false);
    }
    corpusToSearchIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ie) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            updateAnnotationSetsList();
          }
        });
      }
    });
    topPanel.add(corpusToSearchIn, gbc);
    topPanel.add(Box.createHorizontalStrut(4), gbc);
    topPanel.add(new JLabel("Annotation set: "), gbc);
    annotationSetsToSearchIn = new JComboBox<String>();
    annotationSetsToSearchIn.setPrototypeDisplayValue(Constants.COMBINED_SET);
    annotationSetsToSearchIn
            .setToolTipText("Select the annotation set to search in.");
    annotationSetsToSearchIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ie) {
        updateAnnotationTypesList();
      }
    });
    topPanel.add(annotationSetsToSearchIn, gbc);
    
    // refresh button
    topPanel.add(Box.createHorizontalStrut(4), gbc);
    RefreshAnnotationSetsAndFeaturesAction refreshAction = new RefreshAnnotationSetsAndFeaturesAction();
    JButton refreshTF =
            new ButtonBorder(new Color(240, 240, 240), new Insets(4, 2, 4, 3),
                    false);
    refreshTF.setAction(refreshAction);
    topPanel.add(refreshTF, gbc);
    
    // second column, second row
    gbc.gridy = 1;
    JLabel noOfResultsLabel = new JLabel("Results: ");
    topPanel.add(noOfResultsLabel, gbc);
    numberOfResultsSlider = new JSlider(1, 1100, 50);
    numberOfResultsSlider.setToolTipText("50 results per page");
    numberOfResultsSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if(numberOfResultsSlider.getValue() > (numberOfResultsSlider
                .getMaximum() - 100)) {
          numberOfResultsSlider.setToolTipText("Retrieve all results.");
          nextResults.setText("Retrieve all results.");
          nextResultsAction.setEnabled(false);
        } else {
          numberOfResultsSlider.setToolTipText("Retrieve "
                  + numberOfResultsSlider.getValue() + " results per page.");
          nextResults.setText("Next page of "
                  + numberOfResultsSlider.getValue() + " results");
          if(searcher.getHits().length == noOfResults) {
            nextResultsAction.setEnabled(true);
          }
        }
        // show the tooltip each time the value change
        ToolTipManager.sharedInstance().mouseMoved(
                new MouseEvent(numberOfResultsSlider, MouseEvent.MOUSE_MOVED,
                        0, 0, 0, 0, 0, false));
      }
    });
    // always show the tooltip for this component
    numberOfResultsSlider.addMouseListener(new MouseAdapter() {
      ToolTipManager toolTipManager = ToolTipManager.sharedInstance();

      int initialDelay, reshowDelay, dismissDelay;

      boolean enabled;

      @Override
      public void mouseEntered(MouseEvent e) {
        initialDelay = toolTipManager.getInitialDelay();
        reshowDelay = toolTipManager.getReshowDelay();
        dismissDelay = toolTipManager.getDismissDelay();
        enabled = toolTipManager.isEnabled();
        toolTipManager.setInitialDelay(0);
        toolTipManager.setReshowDelay(0);
        toolTipManager.setDismissDelay(Integer.MAX_VALUE);
        toolTipManager.setEnabled(true);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        toolTipManager.setInitialDelay(initialDelay);
        toolTipManager.setReshowDelay(reshowDelay);
        toolTipManager.setDismissDelay(dismissDelay);
        toolTipManager.setEnabled(enabled);
      }
    });
    gbc.insets = new Insets(5, 0, 0, 0);
    topPanel.add(numberOfResultsSlider, gbc);
    gbc.insets = new Insets(0, 0, 0, 0);
    topPanel.add(Box.createHorizontalStrut(4), gbc);
    JLabel contextWindowLabel = new JLabel("Context size: ");
    topPanel.add(contextWindowLabel, gbc);
    contextSizeSlider = new JSlider(1, 50, 5);
    contextSizeSlider
            .setToolTipText("Display 5 tokens of context in the results.");
    contextSizeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        contextSizeSlider.setToolTipText("Display "
                + contextSizeSlider.getValue()
                + " tokens of context in the results.");
        ToolTipManager.sharedInstance().mouseMoved(
                new MouseEvent(contextSizeSlider, MouseEvent.MOUSE_MOVED, 0, 0,
                        0, 0, 0, false));
      }
    });
    // always show the tooltip for this component
    contextSizeSlider.addMouseListener(new MouseAdapter() {
      ToolTipManager toolTipManager = ToolTipManager.sharedInstance();

      int initialDelay, reshowDelay, dismissDelay;

      boolean enabled;

      @Override
      public void mouseEntered(MouseEvent e) {
        initialDelay = toolTipManager.getInitialDelay();
        reshowDelay = toolTipManager.getReshowDelay();
        dismissDelay = toolTipManager.getDismissDelay();
        enabled = toolTipManager.isEnabled();
        toolTipManager.setInitialDelay(0);
        toolTipManager.setReshowDelay(0);
        toolTipManager.setDismissDelay(Integer.MAX_VALUE);
        toolTipManager.setEnabled(true);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        toolTipManager.setInitialDelay(initialDelay);
        toolTipManager.setReshowDelay(reshowDelay);
        toolTipManager.setDismissDelay(dismissDelay);
        toolTipManager.setEnabled(enabled);
      }
    });
    gbc.insets = new Insets(5, 0, 0, 0);
    topPanel.add(contextSizeSlider, gbc);
    gbc.insets = new Insets(0, 0, 0, 0);

    // second column, third row
    gbc.gridy = 2;
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    executeQueryAction = new ExecuteQueryAction();
    JButton executeQuery =
            new ButtonBorder(new Color(240, 240, 240), new Insets(0, 2, 0, 3),
                    false);
    executeQuery.setAction(executeQueryAction);
    panel.add(executeQuery);
    ClearQueryAction clearQueryAction = new ClearQueryAction();
    JButton clearQueryTF =
            new ButtonBorder(new Color(240, 240, 240), new Insets(4, 2, 4, 3),
                    false);
    clearQueryTF.setAction(clearQueryAction);
    panel.add(Box.createHorizontalStrut(5));
    panel.add(clearQueryTF);
    nextResultsAction = new NextResultsAction();
    nextResultsAction.setEnabled(false);
    nextResults =
            new ButtonBorder(new Color(240, 240, 240), new Insets(0, 0, 0, 3),
                    false);
    nextResults.setAction(nextResultsAction);
    panel.add(Box.createHorizontalStrut(5));
    panel.add(nextResults);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    topPanel.add(panel, gbc);

    // will be added to the GUI via a split panel

    /****************
     * Center panel *
     ****************/

    // these components will be used in updateStackView()
    centerPanel = new AnnotationStack(150, 30);

    configureStackViewButton =
            new ButtonBorder(new Color(250, 250, 250), new Insets(0, 0, 0, 3),
                    true);
    configureStackViewButton.setHorizontalAlignment(SwingConstants.LEFT);
    configureStackViewButton.setAction(new ConfigureStackViewAction());

    // will be added to the GUI via a split panel

    /*********************
     * Bottom left panel *
     *********************/

    JPanel bottomLeftPanel = new JPanel(new GridBagLayout());
    bottomLeftPanel.setOpaque(false);
    gbc = new GridBagConstraints();

    // title of the table, results options, export and next results
    // button
    gbc.gridy = 0;
    panel = new JPanel();
    panel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    titleResults = new JLabel("Results");
    titleResults.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    panel.add(titleResults);
    panel.add(Box.createHorizontalStrut(5), gbc);
    exportResultsAction = new ExportResultsAction();
    exportResultsAction.setEnabled(false);
    JButton exportToHTML =
            new ButtonBorder(new Color(240, 240, 240), new Insets(0, 0, 0, 3),
                    false);
    exportToHTML.setAction(exportResultsAction);
    panel.add(exportToHTML, gbc);
    bottomLeftPanel.add(panel, gbc);

    // table of results
    resultTableModel = new ResultTableModel();
    resultTable = new XJTable(resultTableModel);
    resultTable.setDefaultRenderer(String.class, new ResultTableCellRenderer());
    resultTable.setEnableHidingColumns(true);

    resultTable.addMouseListener(new MouseAdapter() {
      private JPopupMenu mousePopup;

      JMenuItem menuItem;

      @Override
      public void mousePressed(MouseEvent e) {
        int row = resultTable.rowAtPoint(e.getPoint());
        if(e.isPopupTrigger() && !resultTable.isRowSelected(row)) {
          // if right click outside the selection then reset selection
          resultTable.getSelectionModel().setSelectionInterval(row, row);
        }
        if(e.isPopupTrigger()) {
          createPopup();
          mousePopup.show(e.getComponent(), e.getX(), e.getY());
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()) {
          createPopup();
          mousePopup.show(e.getComponent(), e.getX(), e.getY());
        }
      }

      private void createPopup() {
        mousePopup = new JPopupMenu();
        menuItem =
                new JMenuItem("Remove the selected result"
                        + (resultTable.getSelectedRowCount() > 1 ? "s" : ""));
        mousePopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ae) {
            int[] rows = resultTable.getSelectedRows();
            for(int i = 0; i < rows.length; i++) {
              rows[i] = resultTable.rowViewToModel(rows[i]);
            }
            Arrays.sort(rows);
            for(int i = rows.length - 1; i >= 0; i--) {
              results.remove(rows[i]);
            }
            resultTable.clearSelection();
            resultTableModel.fireTableDataChanged();
            mousePopup.setVisible(false);
          }
        });

        if(target instanceof LuceneDataStoreImpl
                && SwingUtilities.getRoot(LuceneDataStoreSearchGUI.this) instanceof MainFrame) {
          menuItem =
                  new JMenuItem("Open the selected document"
                          + (resultTable.getSelectedRowCount() > 1 ? "s" : ""));
          menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              Set<Pattern> patterns = new HashSet<Pattern>();
              Set<String> documentIds = new HashSet<String>();
              for(int rowView : resultTable.getSelectedRows()) {
                // create and display the document for this result
                int rowModel = resultTable.rowViewToModel(rowView);
                Pattern pattern = (Pattern)results.get(rowModel);
                if(!documentIds.contains(pattern.getDocumentID())) {
                  patterns.add(pattern);
                  documentIds.add(pattern.getDocumentID());
                }
              }
              if(patterns.size() > 10) {
                Object[] possibleValues =
                        {"Open the " + patterns.size() + " documents",
                            "Don't open"};
                int selectedValue =
                        JOptionPane
                                .showOptionDialog(
                                        LuceneDataStoreSearchGUI.this,
                                        "Do you want to open "
                                                + patterns.size()
                                                + " documents in the central tabbed pane ?",
                                        "Warning", JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null,
                                        possibleValues, possibleValues[1]);
                if(selectedValue == 1
                        || selectedValue == JOptionPane.CLOSED_OPTION) {
                  return;
                }
              }
              for(final Pattern pattern : patterns) {
                // create and display the document for this result
                FeatureMap features = Factory.newFeatureMap();
                features.put(DataStore.DATASTORE_FEATURE_NAME, target);
                features.put(DataStore.LR_ID_FEATURE_NAME,
                        pattern.getDocumentID());
                final Document doc;
                try {
                  doc =
                          (Document)Factory.createResource(
                                  "gate.corpora.DocumentImpl", features);
                } catch(gate.util.GateException e) {
                  e.printStackTrace();
                  return;
                }
                // show the expression in the document
                SwingUtilities.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                    MainFrame.getInstance().select(doc);
                  }
                });
                if(patterns.size() == 1) {
                  // wait some time for the document to be displayed
                  Date timeToRun = new Date(System.currentTimeMillis() + 2000);
                  Timer timer = new Timer("Annic show document timer", true);
                  timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                      showResultInDocument(doc, pattern);
                    }
                  }, timeToRun);
                }
              }
            }
          });
          mousePopup.add(menuItem);
        }
      }
    }); // resultTable.addMouseListener

    // when selection change in the result table
    // update the stack view and export button
    resultTable.getSelectionModel().addListSelectionListener(
            new javax.swing.event.ListSelectionListener() {
              @Override
              public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                  updateStackView();
                }
              }
            });
    resultTable.setColumnSelectionAllowed(false);
    resultTable.setRowSelectionAllowed(true);
    resultTable.setSortable(true);
    resultTable.setComparator(ResultTableModel.LEFT_CONTEXT_COLUMN,
            lastWordComparator);
    resultTable.setComparator(ResultTableModel.RESULT_COLUMN, stringCollator);
    resultTable.setComparator(ResultTableModel.RIGHT_CONTEXT_COLUMN,
            stringCollator);

    JScrollPane tableScrollPane =
            new JScrollPane(resultTable,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.gridy = 1;
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 0, 0);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.gridheight = GridBagConstraints.REMAINDER;
    gbc.weightx = 1;
    gbc.weighty = 1;
    bottomLeftPanel.add(tableScrollPane, gbc);

    /**************************
     * Statistics tabbed pane *
     **************************/

    statisticsTabbedPane = new JTabbedPane();

    globalStatisticsTable = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return false;
      }
    };
    globalStatisticsTableModel =
            new DefaultTableModel(new Object[] {"Annotation Type", "Count"}, 0);
    globalStatisticsTable.setModel(globalStatisticsTableModel);
    globalStatisticsTable.setComparator(0, stringCollator);
    globalStatisticsTable.setComparator(1, integerComparator);
    globalStatisticsTable.setSortedColumn(1);
    globalStatisticsTable.setAscending(false);
    globalStatisticsTable.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
          updateQuery();
        }
      }

      private void updateQuery() {
        int caretPosition = queryTextArea.getCaretPosition();
        String query = queryTextArea.getText();
        String type =
                (String)globalStatisticsTable.getValueAt(
                        globalStatisticsTable.getSelectedRow(),
                        globalStatisticsTable.convertColumnIndexToView(0));
        String queryMiddle = "{" + type + "}";
        String queryLeft =
                (queryTextArea.getSelectionStart() == queryTextArea
                        .getSelectionEnd())
                        ? query.substring(0, caretPosition)
                        : query.substring(0, queryTextArea.getSelectionStart());
        String queryRight =
                (queryTextArea.getSelectionStart() == queryTextArea
                        .getSelectionEnd()) ? query.substring(caretPosition,
                        query.length()) : query.substring(
                        queryTextArea.getSelectionEnd(), query.length());
        queryTextArea.setText(queryLeft + queryMiddle + queryRight);
      }
    });

    statisticsTabbedPane.addTab("Global", null, new JScrollPane(
            globalStatisticsTable),
            "Global statistics on the Corpus and Annotation Set selected.");

    statisticsTabbedPane.addMouseListener(new MouseAdapter() {
      private JPopupMenu mousePopup;

      JMenuItem menuItem;

      @Override
      public void mousePressed(MouseEvent e) {
        int tabIndex = statisticsTabbedPane.indexAtLocation(e.getX(), e.getY());
        if(e.isPopupTrigger() && tabIndex > 0) {
          createPopup(tabIndex);
          mousePopup.show(e.getComponent(), e.getX(), e.getY());
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        int tabIndex = statisticsTabbedPane.indexAtLocation(e.getX(), e.getY());
        if(e.isPopupTrigger() && tabIndex > 0) {
          createPopup(tabIndex);
          mousePopup.show(e.getComponent(), e.getX(), e.getY());
        }
      }

      private void createPopup(final int tabIndex) {
        mousePopup = new JPopupMenu();
        if(tabIndex == 1) {
          menuItem = new JMenuItem("Clear table");
          menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ie) {
              oneRowStatisticsTableModel.setRowCount(0);
            }
          });
        } else {
          menuItem = new JMenuItem("Close tab");
          menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ie) {
              statisticsTabbedPane.remove(tabIndex);
            }
          });
        }
        mousePopup.add(menuItem);
      }
    });

    class RemoveCellEditorRenderer extends AbstractCellEditor implements
                                                             TableCellRenderer,
                                                             TableCellEditor,
                                                             ActionListener {
      private JButton button;

      public RemoveCellEditorRenderer() {
        button = new JButton();
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setIcon(MainFrame.getIcon("crystal-clear-action-button-cancel"));
        button.setToolTipText("Remove this row.");
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.addActionListener(this);
      }

      @Override
      public Component getTableCellRendererComponent(JTable table,
              Object color, boolean isSelected, boolean hasFocus, int row,
              int col) {
        button.setSelected(isSelected);
        return button;
      }

      @Override
      public boolean shouldSelectCell(EventObject anEvent) {
        return false;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        int editingRow = oneRowStatisticsTable.getEditingRow();
        fireEditingStopped();
        oneRowStatisticsTableModel.removeRow(oneRowStatisticsTable
                .rowViewToModel(editingRow));
      }

      @Override
      public Object getCellEditorValue() {
        return null;
      }

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value,
              boolean isSelected, int row, int col) {
        button.setSelected(isSelected);
        return button;
      }
    }

    oneRowStatisticsTable = new XJTable() {
      @Override
      public boolean isCellEditable(int rowIndex, int vColIndex) {
        return vColIndex == 2;
      }

      @Override
      public Component prepareRenderer(TableCellRenderer renderer, int row,
              int col) {
        Component c = super.prepareRenderer(renderer, row, col);
        if(c instanceof JComponent && col != 2) {
          // display a custom tooltip saved when adding statistics
          ((JComponent)c).setToolTipText("<html>"
                  + oneRowStatisticsTableToolTips.get(rowViewToModel(row))
                  + "</html>");
        }
        return c;
      }
    };

    oneRowStatisticsTableModel =
            new DefaultTableModel(new Object[] {"Annotation Type/Feature",
                "Count", ""}, 0);
    oneRowStatisticsTable.setModel(oneRowStatisticsTableModel);
    oneRowStatisticsTable.getColumnModel().getColumn(2)
            .setCellEditor(new RemoveCellEditorRenderer());
    oneRowStatisticsTable.getColumnModel().getColumn(2)
            .setCellRenderer(new RemoveCellEditorRenderer());
    oneRowStatisticsTable.setComparator(0, stringCollator);
    oneRowStatisticsTable.setComparator(1, integerComparator);

    statisticsTabbedPane.addTab("One item", null, new JScrollPane(
            oneRowStatisticsTable), "<html>One item statistics.<br>"
            + "Right-click on an annotation<br>" + "to add statistics here.");
    oneRowStatisticsTableToolTips = new Vector<String>();

    // will be added to the GUI via a split panel

    /**************************************************************
     * Vertical splits between top, center panel and bottom panel *
     **************************************************************/

    /** ________________________________________
     * |               topPanel                 |
     * |__________________3_____________________|
     * |                                        |
     * |             centerPanel                |
     * |________2________ __________2___________|
     * |                 |                      |
     * | bottomLeftPanel 1 statisticsTabbedPane |
     * |_________________|______________________|
     * 
     * 1 bottomSplitPane 2 centerBottomSplitPane 3 topBottomSplitPane
     */

    bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    Dimension minimumSize = new Dimension(0, 0);
    bottomLeftPanel.setMinimumSize(minimumSize);
    statisticsTabbedPane.setMinimumSize(minimumSize);
    bottomSplitPane.add(bottomLeftPanel);
    bottomSplitPane.add(statisticsTabbedPane);
    bottomSplitPane.setOneTouchExpandable(true);
    bottomSplitPane.setResizeWeight(0.75);
    bottomSplitPane.setContinuousLayout(true);

    JSplitPane centerBottomSplitPane =
            new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    centerBottomSplitPane.add(new JScrollPane(centerPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    centerBottomSplitPane.add(bottomSplitPane);
    centerBottomSplitPane.setResizeWeight(0.5);
    centerBottomSplitPane.setContinuousLayout(true);

    JSplitPane topBottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    topBottomSplitPane.add(topPanel);
    topBottomSplitPane.add(centerBottomSplitPane);
    topBottomSplitPane.setContinuousLayout(true);

    add(topBottomSplitPane, BorderLayout.CENTER);

  }

  private void showResultInDocument(final Document doc, final Pattern result) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        try {
          // find the document view associated with the document
          TextualDocumentView t = null;
          for(Resource r : Gate.getCreoleRegister().getAllInstances(
                  "gate.gui.docview.TextualDocumentView")) {
            if(((TextualDocumentView)r).getDocument().getName()
                    .equals(doc.getName())) {
              t = (TextualDocumentView)r;
              break;
            }
          }

          if(t != null && t.getOwner() != null) {
            // display the annotation sets view
            t.getOwner().setRightView(0);
            try {
              // scroll to the expression that matches the query result
              t.getTextView().scrollRectToVisible(
                      t.getTextView()
                              .modelToView(result.getRightContextEndOffset()));
            } catch(BadLocationException e) {
              e.printStackTrace();
              return;
            }
            // select the expression that matches the query result
            t.getTextView().select(result.getLeftContextStartOffset(),
                    result.getRightContextEndOffset());
            t.getTextView().requestFocus();
          }

          // find the annotation sets view associated with the document
          for(Resource r : Gate.getCreoleRegister().getAllInstances(
                  "gate.gui.docview.AnnotationSetsView")) {
            AnnotationSetsView asv = (AnnotationSetsView)r;
            if(asv == null) {
              continue;
            }
            if(asv.isActive() && asv.getDocument().getName().equals(doc.getName())) {
              // display the same annotation types as in Annic
              for(int row = 0; row < numStackRows; row++) {
                if(stackRows[row][DISPLAY].equals("false")) {
                  continue;
                }
                String type = stackRows[row][ANNOTATION_TYPE];
                if(type.equals(Constants.ANNIC_TOKEN)) {
                  // not interesting to display them
                  continue;
                }
                // look if there is the type displayed in Annic
                String asn = result.getAnnotationSetName();
                if(asn.equals(Constants.DEFAULT_ANNOTATION_SET_NAME)
                        && doc.getAnnotations().getAllTypes().contains(type)) {
                  asv.setTypeSelected(null, type, true);
                } else if(doc.getAnnotationSetNames().contains(asn)
                        && doc.getAnnotations(asn).getAllTypes().contains(type)) {
                  asv.setTypeSelected(asn, type, true);
                }
              }
              break;
            }
          }

        } catch(gate.util.GateException e) {
          e.printStackTrace();
        }
        
      }
      
    });
    
    
  } // private void showExpressionInDocument

  /**
   * Update the result table and center view according to the result of
   * the search contained in <code>searcher</code>.
   */
  protected void updateViews() {

    if(searcher != null) {
      Collections.addAll(results, searcher.getHits());
      // update the table of results
      resultTableModel.fireTableDataChanged();
    }

    if(results.size() > 0) {
      String query = queryTextArea.getText().trim();
      if(query.length() > 0 && !results.isEmpty()) {
        int row;
        do { // delete previous temporary stack rows
          row = findStackRow(DISPLAY, "one time");
          deleteStackRow(row);
        } while(row >= 0);
        // from the query display all the existing stackRows
        // that are not already displayed
        Matcher matcher = java.util.regex.Pattern.compile("\\{" // first
                                                                // condition
                + "([^\\{\\}=,.]+)" // annotation type or shortcut (1)
                + "(?:(?:\\.([^=]+)==\"([^\\}\"]+)\")" // feature (2),
                                                       // value (3)
                + "|(?:==([^\\}]+)))?" // value of a shortcut (4)
                + "(?:, ?" // second condition
                + "([^\\{\\}=,.]+)" // annotation type or shortcut (5)
                + "(?:(?:\\.([^=]+)==\"([^\\}\"]+)\")" // feature (6),
                                                       // value (7)
                + "|(?:==([^\\}]+)))?)?" // value of a shortcut (8)
                + "\\}").matcher(query);
        while(matcher.find()) {
          for(int i = 0; i <= 4; i += 4) { // first then second
                                           // condition
            String type = null, feature = null, shortcut;
            row = -1;
            if(matcher.group(1 + i) != null && matcher.group(2 + i) == null
                    && matcher.group(3 + i) == null
                    && matcher.group(4 + i) == null) {
              type = matcher.group(1 + i);
              feature = "";
              row = findStackRow(ANNOTATION_TYPE, type, FEATURE, feature);
            } else if(matcher.group(1 + i) != null
                    && matcher.group(2 + i) == null
                    && matcher.group(3 + i) == null) {
              shortcut = matcher.group(1 + i);
              row = findStackRow(SHORTCUT, shortcut);
            } else if(matcher.group(1 + i) != null
                    && matcher.group(2 + i) != null
                    && matcher.group(4 + i) == null) {
              type = matcher.group(1 + i);
              feature = matcher.group(2 + i);
              row = findStackRow(ANNOTATION_TYPE, type, FEATURE, feature);
            }
            if(row >= 0) {
              stackRows[row][DISPLAY] = "true";
            } else if(type != null && feature != null
                    && numStackRows < maxStackRows) {
              stackRows[numStackRows][DISPLAY] = "one time";
              stackRows[numStackRows][SHORTCUT] = "";
              stackRows[numStackRows][ANNOTATION_TYPE] = type;
              stackRows[numStackRows][FEATURE] = feature;
              stackRows[numStackRows][CROP] = "Crop end";
              numStackRows++;
            }
          }
        }
        configureStackViewTableModel.fireTableDataChanged();
      }
      exportResultsAction.setEnabled(true);
      if(numberOfResultsSlider.getValue() <= (numberOfResultsSlider
              .getMaximum() - 100)) {
        nextResultsAction.setEnabled(true);
      }
      if(searcher.getHits().length < noOfResults) {
        nextResultsAction.setEnabled(false);
      }
      resultTable.setRowSelectionInterval(0, 0);
      resultTable.scrollRectToVisible(resultTable.getCellRect(0, 0, true));

    } else if(queryTextArea.getText().trim().length() < 1) {
      centerPanel.removeAll();
      centerPanel
              .add(new JTextArea(
                      "Have a look at the statistics table at the bottom right\n"
                              + "for the most frequent annotations.\n\n"
                              + "Enter a query in the text area at the top and press Enter.\n\n"
                              + "For example: {Person} to retrieve Person annotations."),
                      new GridBagConstraints());
      centerPanel.updateUI();
      nextResultsAction.setEnabled(false);
      exportResultsAction.setEnabled(false);

    } else {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridy = GridBagConstraints.RELATIVE;
      if(errorOnLastQuery) {
        errorOnLastQuery = false;
      } else {
        centerPanel.removeAll();
        centerPanel.add(new JTextArea("No result found for your query."), gbc);
        if(!corpusToSearchIn.getSelectedItem().equals(
                Constants.ENTIRE_DATASTORE)
                || !annotationSetsToSearchIn.getSelectedItem().equals(
                        Constants.ALL_SETS)) {
          gbc.insets = new Insets(20, 0, 0, 0);
          centerPanel.add(
                  new JTextArea(
                          "Consider increasing the number of documents to search "
                                  + "in selecting \""
                                  + Constants.ENTIRE_DATASTORE
                                  + "\" as corpus\n" + " and \""
                                  + Constants.ALL_SETS
                                  + "\" as annotation set "
                                  + "in the drop-down lists."), gbc);
        }
      }
      gbc.insets = new Insets(20, 0, 0, 0);
      centerPanel.add(new JTextArea("Try one of these types of query:\n"
              + "- word (each word must match a Token)\n"
              + "- {AnnotationType}\n" + "- {AnnotationType==\"text\"}\n"
              + "- {AnnotationType.feature==\"value\"}\n"
              + "- {AnnotationType, AnnotationType}\n"
              + "- ({A}∣{B}) (means A or B)\n"
              + "- ({A})+n (means one and up to n occurrences)\n"
              + "- ({A})*n (means zero or up to n occurrences)\n"), gbc);
      centerPanel.updateUI();
      exportResultsAction.setEnabled(false);
      nextResultsAction.setEnabled(false);
    }
  }

  /**
   * Updates the annotation stack in the central view.
   */
  protected void updateStackView() {

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.BOTH;

    if(resultTable.getSelectedRow() == -1) {
      // no result is selected in the result table
      centerPanel.removeAll();
      if(resultTable.getRowCount() > 0) {
        centerPanel.add(new JLabel("Select a row in the results table below."),
                gbc);
      } else {
        if(numberOfResultsSlider.getValue() > (numberOfResultsSlider
                .getMaximum() - 100)) {
          centerPanel.add(new JLabel("Retrieving all results..."), gbc);
        } else {
          centerPanel.add(
                  new JLabel("Retrieving " + numberOfResultsSlider.getValue()
                          + " results..."), gbc);
        }
      }
      centerPanel.validate();
      centerPanel.repaint();
      return;
    }

    // get information for the selected row in the results table
    Pattern result =
            (Pattern)results.get(resultTable.rowViewToModel(resultTable
                    .getSelectionModel().getLeadSelectionIndex()));

    // initialize the annotation stack
    centerPanel.setText(result.getPatternText());
    centerPanel.setExpressionStartOffset(result.getStartOffset());
    centerPanel.setExpressionEndOffset(result.getEndOffset());
    centerPanel.setContextBeforeSize(result.getStartOffset()
            - result.getLeftContextStartOffset());
    centerPanel.setContextAfterSize(result.getRightContextEndOffset()
            - result.getEndOffset());
    centerPanel.setLastRowButton(configureStackViewButton);
    centerPanel.setTextMouseListener(new TextMouseListener());
    centerPanel.setHeaderMouseListener(new HeaderMouseListener());
    centerPanel.setAnnotationMouseListener(new AnnotationMouseListener());
    centerPanel.clearAllRows();

    // add each row to the annotation stack
    for(int row = 0; row < numStackRows; row++) {
      if(stackRows[row][DISPLAY].equals("false")) {
        continue;
      }

      String type = stackRows[row][ANNOTATION_TYPE];
      String feature = stackRows[row][FEATURE];
      String shortcut = stackRows[row][SHORTCUT];

      // remove button displayed at the end of each row
      JButton removeRowButton =
              new ButtonBorder(new Color(250, 250, 250),
                      new Insets(0, 3, 0, 3), true);
      removeRowButton.setIcon(MainFrame
              .getIcon("crystal-clear-action-edit-remove"));
      removeRowButton.setToolTipText("Hide this row.");
      final String typeFinal = type;
      final String featureFinal = feature;
      removeRowButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ie) {
          int row =
                  findStackRow(ANNOTATION_TYPE, typeFinal, FEATURE,
                          featureFinal);
          if(row >= 0) {
            stackRows[row][DISPLAY] = "false";
            saveStackViewConfiguration();
          }
          updateStackView();
        }
      });

      int crop;
      if(stackRows[row][CROP].equals("Crop start")) {
        crop = AnnotationStack.CROP_START;
      } else if(stackRows[row][CROP].equals("Crop end")) {
        crop = AnnotationStack.CROP_END;
      } else {
        crop = AnnotationStack.CROP_MIDDLE;
      }

      centerPanel.addRow(null, type, feature, removeRowButton, shortcut, crop);

      // annotations for this row
      PatternAnnotation[] annotations = result.getPatternAnnotations(type);
      if(annotations != null && annotations.length > 0) {
        for(PatternAnnotation annotation : annotations) {
          FeatureMap features = Factory.newFeatureMap();
          features.putAll(annotation.getFeatures());
          centerPanel.addAnnotation(annotation.getStartOffset(),
                  annotation.getEndOffset(), annotation.getType(), features);
        }
      }
    }

    // draw the annotation stack
    centerPanel.drawStack();
  }

  protected void updateAnnotationSetsList() {
    String corpusName =
            (corpusToSearchIn.getSelectedItem()
                    .equals(Constants.ENTIRE_DATASTORE))
                    ? null
                    : (String)corpusIds
                            .get(corpusToSearchIn.getSelectedIndex() - 1);
    TreeSet<String> ts = new TreeSet<String>(stringCollator);
    ts.addAll(getAnnotationSetNames(corpusName));
    DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(ts.toArray(new String[ts.size()]));
    dcbm.insertElementAt(Constants.ALL_SETS, 0);
    annotationSetsToSearchIn.setModel(dcbm);
    annotationSetsToSearchIn.setSelectedItem(Constants.ALL_SETS);

    // used in the ConfigureStackViewFrame as Annotation type column
    // cell editor
    TreeSet<String> types = new TreeSet<String>(stringCollator);
    types.addAll(getTypesAndFeatures(null, null).keySet());
    // put all annotation types from the datastore
    // combobox used as cell editor
    JComboBox<String> annotTypesBox = new JComboBox<String>();
    annotTypesBox.setMaximumRowCount(10);
    annotTypesBox.setModel(new DefaultComboBoxModel<String>(types.toArray(new String[types.size()])));
    DefaultCellEditor cellEditor = new DefaultCellEditor(annotTypesBox);
    cellEditor.setClickCountToStart(0);
    configureStackViewFrame.getTable().getColumnModel()
            .getColumn(ANNOTATION_TYPE).setCellEditor(cellEditor);
  }

  protected void updateAnnotationTypesList() {
    String corpusName =
            (corpusToSearchIn.getSelectedItem()
                    .equals(Constants.ENTIRE_DATASTORE))
                    ? null
                    : (String)corpusIds
                            .get(corpusToSearchIn.getSelectedIndex() - 1);
    String annotationSetName =
            (annotationSetsToSearchIn.getSelectedItem()
                    .equals(Constants.ALL_SETS))
                    ? null
                    : (String)annotationSetsToSearchIn.getSelectedItem();
    populatedAnnotationTypesAndFeatures =
            getTypesAndFeatures(corpusName, annotationSetName);

    int countTotal = 0;
    try {
      int count;
      TreeSet<String> ts = new TreeSet<String>(stringCollator);
      ts.addAll(populatedAnnotationTypesAndFeatures.keySet());
      globalStatisticsTableModel.setRowCount(0);
      for(String annotationType : ts) {
        // retrieves the number of occurrences for each Annotation Type
        // of the choosen Annotation Set
        count = searcher.freq(corpusName, annotationSetName, annotationType);
        globalStatisticsTableModel.addRow(new Object[] {annotationType, count});
        countTotal += count;
      }
    } catch(SearchException se) {
      se.printStackTrace();
      return;
    }
    if(countTotal == 0) {
      centerPanel.removeAll();
      centerPanel.add(new JLabel("<html>There is no annotation for the moment "
              + "for the selected corpus and annotation set.<br><br>"
              + "Select another corpus or annotation set or wait for the "
              + "end of the automatic indexation."), new GridBagConstraints());
    }
  }

  protected Set<String> getAnnotationSetNames(String corpusName) {
    Set<String> toReturn = new HashSet<String>();
    if(corpusName == null) {
      for(String aSet : annotationSetIDsFromDataStore) {
        aSet = aSet.substring(aSet.indexOf(';') + 1);
        toReturn.add(aSet);
      }
    } else {
      for(String aSet : annotationSetIDsFromDataStore) {
        if(aSet.startsWith(corpusName + ";")) {
          aSet = aSet.substring(aSet.indexOf(';') + 1);
          toReturn.add(aSet);
        }
      }
    }
    return toReturn;
  }

  protected Map<String, Set<String>> getTypesAndFeatures(String corpusName,
          String annotationSetName) {
    HashMap<String, Set<String>> toReturn = new HashMap<String, Set<String>>();
    if(corpusName == null && annotationSetName == null) {
      // we simply go through all the annotTyes
      // remove corpusID;annotationSetID; from it
      for(String type : allAnnotTypesAndFeaturesFromDatastore.keySet()) {
        String annotation = type.substring(type.lastIndexOf(';') + 1);
        Set<String> features = toReturn.get(annotation);
        if(features == null) {
          features = new HashSet<String>();
          toReturn.put(annotation, features);
        }
        features.addAll(allAnnotTypesAndFeaturesFromDatastore.get(type));
      }
    } else if(corpusName == null) {
      // we simply go through all the annotTyes
      // remove corpusID;annotationSetID; from it
      for(String type : allAnnotTypesAndFeaturesFromDatastore.keySet()) {
        String annotation = type.substring(type.indexOf(';') + 1);
        if(annotation.startsWith(annotationSetName + ";")) {
          annotation = annotation.substring(annotation.indexOf(';') + 1);
          Set<String> features = toReturn.get(annotation);
          if(features == null) {
            features = new HashSet<String>();
            toReturn.put(annotation, features);
          }
          features.addAll(allAnnotTypesAndFeaturesFromDatastore.get(type));
        }
      }
    } else if(annotationSetName == null) {
      // we simply go through all the annotTyes
      // remove corpusID;annotationSetID; from it
      for(String type : allAnnotTypesAndFeaturesFromDatastore.keySet()) {
        if(type.startsWith(corpusName + ";")) {
          String annotation = type.substring(type.lastIndexOf(';') + 1);
          Set<String> features = toReturn.get(annotation);
          if(features == null) {
            features = new HashSet<String>();
            toReturn.put(annotation, features);
          }
          features.addAll(allAnnotTypesAndFeaturesFromDatastore.get(type));
        }
      }
    } else {
      // we simply go through all the annotTyes
      // remove corpusID;annotationSetID; from it
      for(String type : allAnnotTypesAndFeaturesFromDatastore.keySet()) {
        if(type.startsWith(corpusName + ";" + annotationSetName + ";")) {
          String annotation = type.substring(type.lastIndexOf(';') + 1);
          Set<String> features = toReturn.get(annotation);
          if(features == null) {
            features = new HashSet<String>();
            toReturn.put(annotation, features);
          }
          features.addAll(allAnnotTypesAndFeaturesFromDatastore.get(type));
        }
      }
    }
    return toReturn;
  }

  /**
   * Find the first stack row satisfying all the parameters.
   * 
   * @param parameters couples of int*String that stands for
   *          column*value
   * @return -2 if there is an error in parameters, -1 if not found, row
   *         satisfying the parameters otherwise
   * @see #DISPLAY DISPLAY column parameter
   * @see #SHORTCUT SHORTCUT column parameter
   * @see #ANNOTATION_TYPE ANNOTATION_TYPE column parameter
   * @see #FEATURE FEATURE column parameter
   * @see #CROP CROP column parameter
   */
  protected int findStackRow(Object... parameters) {
    // test the number of parameters
    if((parameters.length % 2) != 0) {
      return -2;
    }
    // test the type and value of the parameters
    for(int num = 0; num < parameters.length; num += 2) {
      if(parameters[num] == null || parameters[num + 1] == null) {
        return -2;
      }
      try {
        if(Integer.parseInt(parameters[num].toString()) < 0
                || Integer.parseInt(parameters[num].toString()) > (columnNames.length - 1)) {
          return -2;
        }
      } catch(NumberFormatException nfe) {
        return -2;
      }
      if(!(parameters[num + 1] instanceof String)) {
        return -2;
      }
    }

    // look for the first row satisfying all the parameters
    for(int row = 0; row < numStackRows; row++) {
      int numParametersSatisfied = 0;
      for(int num = 0; num < parameters.length; num += 2) {
        if(stackRows[row][Integer.parseInt(parameters[num].toString())]
                .equals(parameters[num + 1])) {
          numParametersSatisfied++;
        }
      }
      if(numParametersSatisfied == (parameters.length / 2)) {
        return row;
      }
    }
    return -1;
  }

  /**
   * Delete a row in the stackRows array by shifting the following rows
   * to avoid empty row.
   * 
   * @param row row to delete in the stackRows array
   * @return true if deleted, false otherwise
   */
  protected boolean deleteStackRow(int row) {
    if(row < 0 || row > numStackRows) {
      return false;
    }
    // shift the rows in the array
    for(int row2 = row; row2 < numStackRows; row2++) {
      System.arraycopy(stackRows[row2 + 1], 0, stackRows[row2], 0,
              columnNames.length);
    }
    stackRows[numStackRows][DISPLAY] = "true";
    stackRows[numStackRows][SHORTCUT] = "";
    stackRows[numStackRows][ANNOTATION_TYPE] = "";
    stackRows[numStackRows][FEATURE] = "";
    stackRows[numStackRows][CROP] = "Crop end";
    numStackRows--;
    return true;
  }

  /**
   * Save the user config data.
   */
  protected void saveStackViewConfiguration() {
    Map<String, String> map = new HashMap<String, String>();
    for(int row = 0; row < numStackRows; row++) {
      for(int col = 0; col < columnNames.length; col++) {
        map.put(columnNames[col] + '_' + row, stackRows[row][col]);
      }
    }
    Gate.getUserConfig().put(
            LuceneDataStoreSearchGUI.class.getName() + ".rows",
            Strings.toString(map));
  }

  /**
   * Exports results and statistics to a HTML File.
   */
  protected class ExportResultsAction extends AbstractAction {

    public ExportResultsAction() {
      super("Export", MainFrame.getIcon("crystal-clear-app-download-manager"));
      super.putValue(SHORT_DESCRIPTION,
              "Export results and statistics to a HTML file.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
      XJFileChooser fileChooser =
              (MainFrame.getFileChooser() != null)
                      ? MainFrame.getFileChooser()
                      : new XJFileChooser();
      fileChooser.setAcceptAllFileFilterUsed(true);
      fileChooser.setDialogTitle("Choose a file to export the results");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      ExtensionFileFilter filter =
              new ExtensionFileFilter("HTML files", "html");
      fileChooser.addChoosableFileFilter(filter);
      String location =
              (target instanceof SerialDataStore)
                      ? '-' + ((SerialDataStore)target).getStorageDir()
                              .getName() : "";
      String fileName = "Datastore" + location + ".html";
      fileChooser.setFileName(fileName);
      fileChooser.setResource(LuceneDataStoreSearchGUI.class.getName());
      int res = fileChooser.showSaveDialog(LuceneDataStoreSearchGUI.this);
      if(res != JFileChooser.APPROVE_OPTION) {
        return;
      }

      File saveFile = fileChooser.getSelectedFile();
      BufferedWriter bw = null;
      try {
        String nl = Strings.getNl();
        bw = new BufferedWriter(new FileWriter(saveFile));
        bw.write("<!DOCTYPE html PUBLIC "
                + "\"-//W3C//DTD HTML 4.01 Transitional//EN\"" + nl);
        bw.write("\"http://www.w3.org/TR/html4/loose.dtd\">" + nl);
        bw.write("<HTML><HEAD><TITLE>Annic Results and Statistics</TITLE>" + nl);
        bw.write("<meta http-equiv=\"Content-Type\""
                + " content=\"text/html; charset=utf-8\">" + nl);
        bw.write("</HEAD><BODY>" + nl + nl);

        bw.write("<H1 align=\"center\">Annic Results and Statistics</H1>" + nl);
        bw.write("<H2>Parameters</H2>" + nl);
        bw.write("<UL><LI>Corpus: <B>" + corpusToSearchIn.getSelectedItem()
                + "</B></LI>" + nl);
        bw.write("<LI>Annotation set: <B>"
                + annotationSetsToSearchIn.getSelectedItem() + "</B></LI>" + nl);
        bw.write("<LI>Query Issued: <B>" + searcher.getQuery() + "</B></LI>");
        bw.write("<LI>Context Window: <B>"
                + searcher.getParameters().get(Constants.CONTEXT_WINDOW)
                + "</B></LI>" + nl);
        bw.write("</UL>" + nl + nl);

        bw.write("<H2>Results</H2>" + nl);
        bw.write("<TABLE border=\"1\"><TBODY>" + nl);
        bw.write("<TR>");
        for(int col = 0; col < resultTable.getColumnCount(); col++) {
          bw.write("<TH>" + resultTable.getColumnName(col) + "</TH>" + nl);
        }
        bw.write("</TR>" + nl);
        for(int row = 0; row < resultTable.getRowCount(); row++) {
          bw.write("<TR>");
          for(int col = 0; col < resultTable.getColumnCount(); col++) {
            bw.write("<TD>"
                    + ((String)resultTable.getValueAt(row, col))
                            .replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
                    + "</TD>" + nl);
          }
          bw.write("</TR>" + nl);
        }
        bw.write("</TBODY></TABLE>" + nl + nl);

        bw.write("<H2>Global Statistics</H2>");
        bw.write("<TABLE border=\"1\"><TBODY>" + nl);
        bw.write("<TR>");
        for(int col = 0; col < globalStatisticsTable.getColumnCount(); col++) {
          bw.write("<TH>" + globalStatisticsTable.getColumnName(col) + "</TH>"
                  + nl);
        }
        bw.write("</TR>" + nl);
        for(int row = 0; row < globalStatisticsTable.getRowCount(); row++) {
          bw.write("<TR>");
          for(int col = 0; col < globalStatisticsTable.getColumnCount(); col++) {
            bw.write("<TD>" + globalStatisticsTable.getValueAt(row, col)
                    + "</TD>" + nl);
          }
          bw.write("</TR>" + nl);
        }
        bw.write("</TBODY></TABLE>" + nl + nl);

        bw.write("<H2>One item Statistics</H2>" + nl);
        bw.write("<TABLE border=\"1\"><TBODY>" + nl);
        bw.write("<TR>");
        for(int col = 0; col < (oneRowStatisticsTable.getColumnCount() - 1); col++) {
          bw.write("<TH>" + oneRowStatisticsTable.getColumnName(col) + "</TH>"
                  + nl);
        }
        bw.write("</TR>" + nl);
        for(int row = 0; row < oneRowStatisticsTable.getRowCount(); row++) {
          bw.write("<TR>");
          for(int col = 0; col < (oneRowStatisticsTable.getColumnCount() - 1); col++) {
            bw.write("<TD>" + oneRowStatisticsTable.getValueAt(row, col)
                    + "</TD>" + nl);
          }
          bw.write("</TR>" + nl);
        }
        bw.write("</TBODY></TABLE>" + nl + nl);

        bw.write("<P><BR></P><HR>" + nl);
        bw.write("</BODY>" + nl);
        bw.write("</HTML>");
        bw.flush();

      } catch(IOException e) {
        e.printStackTrace();

      } finally {
        try {
          if(bw != null) {
            bw.close();
          }
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Clear the queryTextArea text box.
   */
  protected class ClearQueryAction extends AbstractAction {

    public ClearQueryAction() {
      super("Clear", MainFrame.getIcon("crystal-clear-action-button-cancel"));
      super.putValue(SHORT_DESCRIPTION, "Clear the query text box.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_BACK_SPACE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
      queryTextArea.setText("");
      queryTextArea.requestFocusInWindow();
    }
  }

  /**
   * Finds out the newly created query and execute it.
   */
  protected class ExecuteQueryAction extends AbstractAction {

    public ExecuteQueryAction() {
      super("Search", MainFrame.getIcon("crystal-clear-app-xmag"));
      super.putValue(SHORT_DESCRIPTION, "Execute the query.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

      // disable all mouse and key events and display the wait cursor
      final BlockingGlassPane blockingGlassPane = new BlockingGlassPane();
      LuceneDataStoreSearchGUI.this.getRootPane().setGlassPane(
              blockingGlassPane);
      blockingGlassPane.block(true);

      // clear the result table and center view
      if(results.size() > 0) {
        results.clear();
        resultTableModel.fireTableDataChanged();
      } else {
        updateStackView();
      }

      // set the search parameters
      Map<String, Object> parameters = searcher.getParameters();
      if(parameters == null) parameters = new HashMap<String, Object>();

      if(target instanceof LuceneDataStoreImpl) {
        String corpus2SearchIn =
                corpusToSearchIn.getSelectedItem().equals(
                        Constants.ENTIRE_DATASTORE) ? null : (String)corpusIds
                        .get(corpusToSearchIn.getSelectedIndex() - 1);
        parameters.put(Constants.CORPUS_ID, corpus2SearchIn);
      }

      noOfResults =
              (numberOfResultsSlider.getValue() > (numberOfResultsSlider
                      .getMaximum() - 100))
                      ? -1
                      : ((Number)numberOfResultsSlider.getValue()).intValue();
      int contextWindow = ((Number)contextSizeSlider.getValue()).intValue();
      String query = queryTextArea.getText().trim();
      java.util.regex.Pattern pattern =
              java.util.regex.Pattern.compile("[\\{, ]([^\\{=]+)==");
      Matcher matcher = pattern.matcher(query);
      int start = 0;
      while(matcher.find(start)) {
        start = matcher.end(1); // avoid infinite loop
        int row = findStackRow(SHORTCUT, matcher.group(1));
        if(row >= 0) {
          // rewrite the query to put the long form of the
          // shortcut found
          query =
                  query.substring(0, matcher.start(1))
                          + stackRows[row][ANNOTATION_TYPE] + "."
                          + stackRows[row][FEATURE]
                          + query.substring(matcher.end(1));
          matcher = pattern.matcher(query);
        }
      }

      parameters.put(Constants.CONTEXT_WINDOW, contextWindow);
      if(annotationSetsToSearchIn.getSelectedItem().equals(Constants.ALL_SETS)) {
        parameters.remove(Constants.ANNOTATION_SET_ID);
      } else {
        String annotationSet =
                (String)annotationSetsToSearchIn.getSelectedItem();
        parameters.put(Constants.ANNOTATION_SET_ID, annotationSet);
      }

      // execute the search
      final String queryF = query;
      final Map<String, Object> parametersF = parameters;
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            if(searcher.search(queryF, parametersF)) {
              searcher.next(noOfResults);
            }

          } catch(SearchException se) {
            errorOnLastQuery = true;
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            centerPanel.removeAll();
            String[] message = se.getMessage().split("\\n");
            if(message.length == 1) {
              // some errors to fix into QueryParser
              se.printStackTrace();
              return;
            }
            // message[0] contains the Java error
            JTextArea jta = new JTextArea(message[1]);
            jta.setForeground(Color.RED);
            centerPanel.add(jta, gbc);
            jta = new JTextArea(message[2]);
            if(message.length > 3) {
              jta.setText(message[2] + "\n" + message[3]);
            }
            jta.setFont(new Font("Monospaced", Font.PLAIN, 12));
            centerPanel.add(jta, gbc);

          } catch(Exception e) {
            e.printStackTrace();

          } finally {
            updateViews();
            pageOfResults = 1;
            titleResults.setText("Page " + pageOfResults + " ("
                    + searcher.getHits().length + " results)");
            queryTextArea.requestFocusInWindow();
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                blockingGlassPane.block(false);
              }
            });
          }
        }
      });
    }
  }

  /**
   * Refresh annotations sets and features.
   */
  protected class RefreshAnnotationSetsAndFeaturesAction extends AbstractAction {

    public RefreshAnnotationSetsAndFeaturesAction() {
      super("",MainFrame.getIcon("crystal-clear-action-reload"));
      super.putValue(SHORT_DESCRIPTION, "Reloads annotations types.");

      // assigning F5 as the short cut key for the refresh button
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_F5);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

      // disable all mouse and key events and display the wait cursor
      final BlockingGlassPane blockingGlassPane = new BlockingGlassPane();
      LuceneDataStoreSearchGUI.this.getRootPane().setGlassPane(
              blockingGlassPane);
      blockingGlassPane.block(true);

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          try {
            updateSetsTypesAndFeatures();
          } catch(Exception e) {
            e.printStackTrace();
          } finally {
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                blockingGlassPane.block(false);
              }
            });
          }
        }
      });
    }
  }

  /**
   * Finds out the next few results.
   */
  protected class NextResultsAction extends AbstractAction {

    public NextResultsAction() {
      super("Next page of " + numberOfResultsSlider.getValue() + " results",
              MainFrame.getIcon("crystal-clear-action-loopnone"));
      super.putValue(SHORT_DESCRIPTION, "Show next page of results.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_RIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

      // disable all mouse and key events and display the wait cursor
      final BlockingGlassPane blockingGlassPane = new BlockingGlassPane();
      LuceneDataStoreSearchGUI.this.getRootPane().setGlassPane(
              blockingGlassPane);
      blockingGlassPane.block(true);

      // clear the results table and center view
      if(results.size() > 0) {
        results.clear();
        resultTableModel.fireTableDataChanged();
      } else {
        updateStackView();
      }

      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          noOfResults = ((Number)numberOfResultsSlider.getValue()).intValue();
          try {
            searcher.next(noOfResults);

          } catch(Exception e) {
            e.printStackTrace();

          } finally {
            updateViews();
            pageOfResults++;
            titleResults.setText("Page " + pageOfResults + " ("
                    + searcher.getHits().length + " results)");
            if(searcher.getHits().length < noOfResults) {
              nextResultsAction.setEnabled(false);
            }
            queryTextArea.requestFocusInWindow();
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                blockingGlassPane.block(false);
              }
            });
          }
        }
      });
    }
  }

  /**
   * Show the configuration window for the annotation stack view.
   */
  protected class ConfigureStackViewAction extends AbstractAction {

    public ConfigureStackViewAction() {
      super("Configure", MainFrame.getIcon("crystal-clear-action-edit-add"));
      super.putValue(SHORT_DESCRIPTION, "Configure the view");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_LEFT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      // to avoid having the frame behind a window
      configureStackViewFrame.setVisible(false);
      configureStackViewFrame.setVisible(true);
    }
  }

  /**
   * Add at the caret position or replace the selection in the query
   * according to the text row value left clicked.
   */
  public class TextMouseListener extends StackMouseListener {

    public TextMouseListener() {
    }

    public TextMouseListener(String text) {
      this.text = text;
    }

    @Override
    public MouseInputAdapter createListener(String... parameters) {
      return new TextMouseListener(parameters[0]);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
      if(!me.isPopupTrigger() && me.getButton() == MouseEvent.BUTTON1
              && me.getClickCount() == 2) {
        int caretPosition = queryTextArea.getCaretPosition();
        String query = queryTextArea.getText();
        String queryMiddle = text;
        String queryLeft =
                (queryTextArea.getSelectionStart() == queryTextArea
                        .getSelectionEnd())
                        ? query.substring(0, caretPosition)
                        : query.substring(0, queryTextArea.getSelectionStart());
        String queryRight =
                (queryTextArea.getSelectionStart() == queryTextArea
                        .getSelectionEnd()) ? query.substring(caretPosition,
                        query.length()) : query.substring(
                        queryTextArea.getSelectionEnd(), query.length());
        queryTextArea.setText(queryLeft + queryMiddle + queryRight);
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      Component component = e.getComponent();
      if(!isTooltipSet && component instanceof JLabel) {
        isTooltipSet = true;
        JLabel label = (JLabel)component;
        Pattern result =
                (Pattern)results.get(resultTable.rowViewToModel(resultTable
                        .getSelectionModel().getLeadSelectionIndex()));
        label.setToolTipText("The query that matched this "
                + "expression was: " + result.getQueryString() + ".");
      }
    }

    String text;

    boolean isTooltipSet = false;
  }

  /**
   * Modifies the query or displays statistics according to the
   * annotation rectangle clicked.
   */
  protected class AnnotationMouseListener extends StackMouseListener {

    String type;

    String feature;

    String text;

    String description;

    String toolTip;

    String descriptionTemplate;

    String toolTipTemplate;

    JPopupMenu mousePopup;

    JMenuItem menuItem;

    final String corpusID = (corpusToSearchIn.getSelectedItem()
            .equals(Constants.ENTIRE_DATASTORE)) ? null : (String)corpusIds
            .get(corpusToSearchIn.getSelectedIndex() - 1);

    final String annotationSetID = (annotationSetsToSearchIn.getSelectedItem()
            .equals(Constants.ALL_SETS))
            ? null
            : (String)annotationSetsToSearchIn.getSelectedItem();

    final String corpusName = (String)corpusToSearchIn.getSelectedItem();

    final String annotationSetName = (String)annotationSetsToSearchIn
            .getSelectedItem();

    ToolTipManager toolTipManager = ToolTipManager.sharedInstance();

    int dismissDelay, initialDelay, reshowDelay;

    boolean enabled;

    boolean isTooltipSet = false;

    public AnnotationMouseListener() {
    }

    public AnnotationMouseListener(String type, String feature, String text) {
      this.type = type;
      this.feature = feature;
      this.text = text;
      String value;
      if(text.replace("\\s", "").length() > 20) {
        value = text.replace("\\s", "").substring(0, 20) + ("...");
      } else {
        value = text.replace("\\s", "");
      }
      this.descriptionTemplate =
              type + "." + feature + "==\"" + value + "\" (kind)";
      this.toolTipTemplate =
              "Statistics in kind" + "<br>on Corpus: " + corpusName
                      + "<br>and Annotation Set: " + annotationSetName
                      + "<br>for the query: " + results.get(0).getQueryString();
    }

    public AnnotationMouseListener(String type) {
      this.type = type;
    }

    @Override
    public MouseInputAdapter createListener(String... parameters) {
      switch(parameters.length) {
        case 3:
          return new AnnotationMouseListener(parameters[1]);
        case 5:
          return new AnnotationMouseListener(parameters[1], parameters[2],
                  parameters[3]);
        default:
          return null;
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      dismissDelay = toolTipManager.getDismissDelay();
      initialDelay = toolTipManager.getInitialDelay();
      reshowDelay = toolTipManager.getReshowDelay();
      enabled = toolTipManager.isEnabled();
      Component component = e.getComponent();
      if(feature != null && !isTooltipSet && component instanceof JLabel) {
        isTooltipSet = true;
        JLabel label = (JLabel)component;
        String toolTip = label.getToolTipText();
        toolTip =
                (toolTip == null || toolTip.equals("")) ? "" : toolTip
                        .replaceAll("</?html>", "") + "<br>";
        toolTip = "<html>" + toolTip + "Right click to get statistics.</html>";
        label.setToolTipText(toolTip);
      }
      // make the tooltip indefinitely shown when the mouse is over
      toolTipManager.setDismissDelay(Integer.MAX_VALUE);
      toolTipManager.setInitialDelay(0);
      toolTipManager.setReshowDelay(0);
      toolTipManager.setEnabled(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
      toolTipManager.setDismissDelay(dismissDelay);
      toolTipManager.setInitialDelay(initialDelay);
      toolTipManager.setReshowDelay(reshowDelay);
      toolTipManager.setEnabled(enabled);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if(e.isPopupTrigger() && type != null && feature != null) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      } else if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
        updateQuery();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if(e.isPopupTrigger() && type != null && feature != null) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    private void updateQuery() {
      int caretPosition = queryTextArea.getCaretPosition();
      String query = queryTextArea.getText();
      String queryMiddle;

      if(type != null && feature != null) {
        int row = findStackRow(ANNOTATION_TYPE, type, FEATURE, feature);
        if(row >= 0 && !stackRows[row][SHORTCUT].equals("")) {
          queryMiddle = "{" + stackRows[row][SHORTCUT] + "==\"" + text + "\"}";
        } else {
          queryMiddle = "{" + type + "." + feature + "==\"" + text + "\"}";
        }
      } else if(type != null) {
        queryMiddle = "{" + type + "}";

      } else {
        queryMiddle = text;
      }
      String queryLeft =
              (queryTextArea.getSelectionStart() == queryTextArea
                      .getSelectionEnd())
                      ? query.substring(0, caretPosition)
                      : query.substring(0, queryTextArea.getSelectionStart());
      String queryRight =
              (queryTextArea.getSelectionStart() == queryTextArea
                      .getSelectionEnd()) ? query.substring(caretPosition,
                      query.length()) : query.substring(
                      queryTextArea.getSelectionEnd(), query.length());
      queryTextArea.setText(queryLeft + queryMiddle + queryRight);
    }

    private int checkStatistics() {
      boolean found = false;
      int numRow = 0;
      // check if this statistics doesn't already exist in the table
      for(int row = 0; row < oneRowStatisticsTable.getRowCount(); row++) {
        String oldDescription =
                (String)oneRowStatisticsTable.getValueAt(row, 0);
        String oldToolTip =
                oneRowStatisticsTableToolTips.get(oneRowStatisticsTable
                        .rowViewToModel(numRow));
        if(oldDescription.equals(description) && oldToolTip.equals(toolTip)) {
          found = true;
          break;
        }
        numRow++;
      }
      return found ? numRow : -1;
    }

    private void addStatistics(String kind, int count, int numRow,
            final MouseEvent e) {
      JLabel label = (JLabel)e.getComponent();
      if(!label.getToolTipText().contains(kind)) {
        // add the statistics to the tooltip
        String toolTip = label.getToolTipText();
        toolTip = toolTip.replaceAll("</?html>", "");
        toolTip = kind + " = " + count + "<br>" + toolTip;
        toolTip = "<html>" + toolTip + "</html>";
        label.setToolTipText(toolTip);
      }
      if(bottomSplitPane.getDividerLocation()
              / bottomSplitPane.getSize().getWidth() < 0.90) {
        // select the row in the statistics table
        statisticsTabbedPane.setSelectedIndex(1);
        oneRowStatisticsTable.setRowSelectionInterval(numRow, numRow);
        oneRowStatisticsTable.scrollRectToVisible(oneRowStatisticsTable
                .getCellRect(numRow, 0, true));
      } else {
        // display a tooltip
        JToolTip tip = label.createToolTip();
        tip.setTipText(kind + " = " + count);
        PopupFactory popupFactory = PopupFactory.getSharedInstance();
        final Popup tipWindow =
                popupFactory.getPopup(label, tip, e.getX()
                        + e.getComponent().getLocationOnScreen().x, e.getY()
                        + e.getComponent().getLocationOnScreen().y);
        tipWindow.show();
        Date timeToRun = new Date(System.currentTimeMillis() + 2000);
        Timer timer = new Timer("Annic statistics hide tooltip timer", true);
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            // hide the tooltip after 2 seconds
            tipWindow.hide();
          }
        }, timeToRun);
      }
    }

    private void createPopup(final MouseEvent e) {
      mousePopup = new JPopupMenu();

      menuItem = new JMenuItem("Occurrences in datastore");
      menuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ie) {
          description = descriptionTemplate.replaceFirst("kind", "datastore");
          toolTip = toolTipTemplate.replaceFirst("kind", "datastore");
          int count;
          int numRow = checkStatistics();
          if(numRow == -1) {
            try { // retrieves the number of occurrences
              count =
                      searcher.freq(corpusID, annotationSetID, type, feature,
                              text);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            oneRowStatisticsTableModel.addRow(new Object[] {description, count,
                ""});
            oneRowStatisticsTableToolTips.add(toolTip);
            numRow =
                    oneRowStatisticsTable.rowModelToView(oneRowStatisticsTable
                            .getRowCount() - 1);
          } else {
            count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
          }
          addStatistics("datastore", count, numRow, e);
        }
      });
      mousePopup.add(menuItem);

      menuItem = new JMenuItem("Occurrences in matches");
      menuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ie) {
          description = descriptionTemplate.replaceFirst("kind", "matches");
          toolTip = toolTipTemplate.replaceFirst("kind", "matches");
          int count;
          int numRow = checkStatistics();
          if(numRow == -1) {
            try { // retrieves the number of occurrences
              count = searcher.freq(results, type, feature, text, true, false);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            oneRowStatisticsTableModel.addRow(new Object[] {description, count,
                ""});
            oneRowStatisticsTableToolTips.add(toolTip);
            numRow =
                    oneRowStatisticsTable.rowModelToView(oneRowStatisticsTable
                            .getRowCount() - 1);
          } else {
            count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
          }
          addStatistics("matches", count, numRow, e);
        }
      });
      mousePopup.add(menuItem);

      menuItem = new JMenuItem("Occurrences in contexts");
      menuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ie) {
          description = descriptionTemplate.replaceFirst("kind", "contexts");
          toolTip = toolTipTemplate.replaceFirst("kind", "contexts");
          int count;
          int numRow = checkStatistics();
          if(numRow == -1) {
            try { // retrieves the number of occurrences
              count = searcher.freq(results, type, feature, text, false, true);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            oneRowStatisticsTableModel.addRow(new Object[] {description, count,
                ""});
            oneRowStatisticsTableToolTips.add(toolTip);
            numRow =
                    oneRowStatisticsTable.rowModelToView(oneRowStatisticsTable
                            .getRowCount() - 1);
          } else {
            count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
          }
          addStatistics("contexts", count, numRow, e);
        }
      });
      mousePopup.add(menuItem);

      menuItem = new JMenuItem("Occurrences in matches+contexts");
      menuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ie) {
          description = descriptionTemplate.replaceFirst("kind", "mch+ctxt");
          toolTip = toolTipTemplate.replaceFirst("kind", "matches+contexts");
          int count;
          int numRow = checkStatistics();
          if(numRow == -1) {
            try { // retrieves the number of occurrences
              count = searcher.freq(results, type, feature, text, true, true);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            oneRowStatisticsTableModel.addRow(new Object[] {description, count,
                ""});
            oneRowStatisticsTableToolTips.add(toolTip);
            numRow =
                    oneRowStatisticsTable.rowModelToView(oneRowStatisticsTable
                            .getRowCount() - 1);
          } else {
            count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
          }
          addStatistics("matches+contexts", count, numRow, e);
        }
      });
      mousePopup.add(menuItem);
    }
  }

  /**
   * Displays statistics according to the stack row header
   * right-clicked.
   */
  protected class HeaderMouseListener extends StackMouseListener {

    String type;

    String feature;

    String description;

    String toolTip;

    String descriptionTemplate;

    String toolTipTemplate;

    JPopupMenu mousePopup;

    JMenuItem menuItem;

    XJTable table;

    JWindow popupWindow;

    int row;

    final String corpusID = (corpusToSearchIn.getSelectedItem()
            .equals(Constants.ENTIRE_DATASTORE)) ? null : (String)corpusIds
            .get(corpusToSearchIn.getSelectedIndex() - 1);

    final String annotationSetID = (annotationSetsToSearchIn.getSelectedItem()
            .equals(Constants.ALL_SETS))
            ? null
            : (String)annotationSetsToSearchIn.getSelectedItem();

    final String corpusName = (String)corpusToSearchIn.getSelectedItem();

    final String annotationSetName = (String)annotationSetsToSearchIn
            .getSelectedItem();

    boolean isTooltipSet = false;

    public HeaderMouseListener() {
    }

    public HeaderMouseListener(String type, String feature) {
      this.type = type;
      this.feature = feature;
      this.descriptionTemplate = type + "." + feature + " (kind)";
      this.toolTipTemplate =
              "Statistics in kind" + "<br>on Corpus: " + corpusName
                      + "<br>and Annotation Set: " + annotationSetName
                      + "<br>for the query: " + results.get(0).getQueryString();
      init();
    }

    public HeaderMouseListener(String type) {
      this.type = type;
      this.descriptionTemplate = type + " (kind)";
      this.toolTipTemplate =
              "Statistics in kind" + "<br>on Corpus: " + corpusName
                      + "<br>and Annotation Set: " + annotationSetName
                      + "<br>for the query: " + results.get(0).getQueryString();
      init();
    }

    void init() {
      addAncestorListener(new AncestorListener() {
        @Override
        public void ancestorMoved(AncestorEvent event) {
        }

        @Override
        public void ancestorAdded(AncestorEvent event) {
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
          // no parent so need to be disposed explicitly
          if(popupWindow != null) {
            popupWindow.dispose();
          }
        }
      });
      row =
              findStackRow(ANNOTATION_TYPE, type, FEATURE, (feature == null
                      ? ""
                      : feature));
    }

    @Override
    public MouseInputAdapter createListener(String... parameters) {
      switch(parameters.length) {
        case 1:
          return new HeaderMouseListener(parameters[0]);
        case 2:
          return new HeaderMouseListener(parameters[0], parameters[1]);
        default:
          return null;
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      Component component = e.getComponent();
      if(!isTooltipSet && component instanceof JLabel) {
        isTooltipSet = true;
        JLabel label = (JLabel)component;
        String shortcut = "";
        if(feature != null) {
          int row =
                  resultTable.rowViewToModel(resultTable.getSelectionModel()
                          .getLeadSelectionIndex());
          if(!stackRows[row][SHORTCUT].equals("")) {
            shortcut = "Shortcut for " + type + "." + feature + ".<br>";
          }
        }
        label.setToolTipText("<html>" + shortcut
                + "Double click to choose annotation feature.<br>"
                + "Right click to get statistics.</html>");
      }
    }

    // when double clicked shows a list of features for this annotation
    // type
    @Override
    public void mouseClicked(MouseEvent e) {
      if(popupWindow != null && popupWindow.isVisible()) {
        popupWindow.dispose();
        return;
      }
      if(e.getButton() != MouseEvent.BUTTON1 || e.getClickCount() != 2) {
        return;
      }
      // get a list of features for the current annotation type
      TreeSet<String> features = new TreeSet<String>();
      if(populatedAnnotationTypesAndFeatures.containsKey(type)) {
        // this annotation type still exists in the datastore
        features.addAll(populatedAnnotationTypesAndFeatures.get(type));
      }
      features.add(" ");
      // create the list component
      final JList<String> list = new JList<String>(features.toArray(new String[features.size()]));
      list.setVisibleRowCount(Math.min(8, features.size()));
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.setBackground(Color.WHITE);
      list.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if(e.getClickCount() == 1) {
            String newFeature = list.getSelectedValue();
            if(newFeature.equals(" ")) {
              newFeature = "";
            }
            stackRows[row][FEATURE] = newFeature;
            saveStackViewConfiguration();
            popupWindow.setVisible(false);
            popupWindow.dispose();
            updateStackView();
          }
        }
      });
      // create the window that will contain the list
      popupWindow = new JWindow();
      popupWindow.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            popupWindow.setVisible(false);
            popupWindow.dispose();
          }
        }
      });
      popupWindow.add(new JScrollPane(list));
      Component component = e.getComponent();
      popupWindow.setBounds(
              component.getLocationOnScreen().x,
              component.getLocationOnScreen().y + component.getHeight(),
              component.getWidth(),
              Math.min(8 * component.getHeight(),
                      features.size() * component.getHeight()));
      popupWindow.pack();
      popupWindow.setVisible(true);
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          String newFeature = stackRows[row][FEATURE];
          if(newFeature.equals("")) {
            newFeature = " ";
          }
          list.setSelectedValue(newFeature, true);
          popupWindow.requestFocusInWindow();
        }
      });
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if(e.isPopupTrigger()) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if(e.isPopupTrigger()) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    private int checkStatistics() {
      boolean found = false;
      int numRow = 0;
      // check if this statistics doesn't already exist in the table
      for(int row = 0; row < oneRowStatisticsTable.getRowCount(); row++) {
        String oldDescription =
                (String)oneRowStatisticsTable.getValueAt(row, 0);
        String oldToolTip =
                oneRowStatisticsTableToolTips.get(oneRowStatisticsTable
                        .rowViewToModel(numRow));
        if(oldDescription.equals(description) && oldToolTip.equals(toolTip)) {
          found = true;
          break;
        }
        numRow++;
      }
      return found ? numRow : -1;
    }

    private void addStatistics(String kind, int count, int numRow,
            final MouseEvent e) {
      JLabel label = (JLabel)e.getComponent();
      if(!label.getToolTipText().contains(kind)) {
        // add the statistics to the tooltip
        String toolTip = label.getToolTipText();
        toolTip = toolTip.replaceAll("</?html>", "");
        toolTip = kind + " = " + count + "<br>" + toolTip;
        toolTip = "<html>" + toolTip + "</html>";
        label.setToolTipText(toolTip);
      }
      if(bottomSplitPane.getDividerLocation()
              / bottomSplitPane.getSize().getWidth() < 0.90) {
        // select the row in the statistics table
        statisticsTabbedPane.setSelectedIndex(1);
        oneRowStatisticsTable.setRowSelectionInterval(numRow, numRow);
        oneRowStatisticsTable.scrollRectToVisible(oneRowStatisticsTable
                .getCellRect(numRow, 0, true));
      } else {
        // display a tooltip
        JToolTip tip = label.createToolTip();
        tip.setTipText(kind + " = " + count);
        PopupFactory popupFactory = PopupFactory.getSharedInstance();
        final Popup tipWindow =
                popupFactory.getPopup(label, tip, e.getX()
                        + e.getComponent().getLocationOnScreen().x, e.getY()
                        + e.getComponent().getLocationOnScreen().y);
        tipWindow.show();
        Date timeToRun = new Date(System.currentTimeMillis() + 2000);
        Timer timer = new Timer("Annic statistics hide tooltip timer", true);
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            // hide the tooltip after 2 seconds
            tipWindow.hide();
          }
        }, timeToRun);
      }
    }

    private void createPopup(final MouseEvent e) {
      mousePopup = new JPopupMenu();

      if(type != null && feature != null) {

        // count values for one Feature of an Annotation type

        menuItem = new JMenuItem("Occurrences in datastore");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "datastore");
            toolTip = toolTipTemplate.replaceFirst("kind", "datastore");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(corpusID, annotationSetID, type, feature);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("datastore", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in matches");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "matches");
            toolTip = toolTipTemplate.replaceFirst("kind", "matches");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count =
                        searcher.freq(results, type, feature, null, true, false);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("matches", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "contexts");
            toolTip = toolTipTemplate.replaceFirst("kind", "contexts");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count =
                        searcher.freq(results, type, feature, null, false, true);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("contexts", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in matches+contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "mch+ctxt");
            toolTip = toolTipTemplate.replaceFirst("kind", "matches+contexts");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(results, type, feature, null, true, true);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("matches+contexts", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        // count values for all Features of an Annotation Type

        mousePopup.addSeparator();

        menuItem = new JMenuItem("All values from matches");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            Map<String, Integer> freqs;
            try { // retrieves the number of occurrences
              freqs =
                      searcher.freqForAllValues(results, type, feature, true,
                              false);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(type + '.' + feature + " (matches)");
            model.addColumn("Count");
            for(Map.Entry<String, Integer> map : freqs.entrySet()) {
              model.addRow(new Object[] {map.getKey(), map.getValue()});
            }
            table = new XJTable() {
              @Override
              public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
              }
            };
            table.setModel(model);
            table.setComparator(0, stringCollator);
            table.setComparator(1, integerComparator);
            statisticsTabbedPane.addTab(
                    String.valueOf(statisticsTabbedPane.getTabCount() - 1),
                    null, new JScrollPane(table), "<html>Statistics in matches"
                            + "<br>on Corpus: " + corpusName
                            + "<br>and Annotation Set: " + annotationSetName
                            + "<br>for the query: "
                            + results.get(0).getQueryString() + "</html>");
            if(bottomSplitPane.getDividerLocation()
                    / bottomSplitPane.getSize().getWidth() > 0.75) {
              bottomSplitPane.setDividerLocation(0.66);
            }
            statisticsTabbedPane.setSelectedIndex(statisticsTabbedPane
                    .getTabCount() - 1);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("All values from contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            Map<String, Integer> freqs;
            try { // retrieves the number of occurrences
              freqs =
                      searcher.freqForAllValues(results, type, feature, false,
                              true);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(type + '.' + feature + " (contexts)");
            model.addColumn("Count");
            for(Map.Entry<String, Integer> map : freqs.entrySet()) {
              model.addRow(new Object[] {map.getKey(), map.getValue()});
            }
            table = new XJTable() {
              @Override
              public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
              }
            };
            table.setModel(model);
            table.setComparator(0, stringCollator);
            table.setComparator(1, integerComparator);
            statisticsTabbedPane.addTab(
                    String.valueOf(statisticsTabbedPane.getTabCount() - 1),
                    null, new JScrollPane(table),
                    "<html>Statistics in contexts" + "<br>on Corpus: "
                            + corpusName + "<br>and Annotation Set: "
                            + annotationSetName + "<br>for the query: "
                            + results.get(0).getQueryString() + "</html>");
            if(bottomSplitPane.getDividerLocation()
                    / bottomSplitPane.getSize().getWidth() > 0.75) {
              bottomSplitPane.setDividerLocation(0.66);
            }
            statisticsTabbedPane.setSelectedIndex(statisticsTabbedPane
                    .getTabCount() - 1);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("All values from matches+contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            Map<String, Integer> freqs;
            try { // retrieves the number of occurrences
              freqs =
                      searcher.freqForAllValues(results, type, feature, true,
                              true);
            } catch(SearchException se) {
              se.printStackTrace();
              return;
            }
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(type + '.' + feature + " (mch+ctxt)");
            model.addColumn("Count");
            for(Map.Entry<String, Integer> map : freqs.entrySet()) {
              model.addRow(new Object[] {map.getKey(), map.getValue()});
            }
            table = new XJTable() {
              @Override
              public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
              }
            };
            table.setModel(model);
            table.setComparator(0, stringCollator);
            table.setComparator(1, integerComparator);
            statisticsTabbedPane.addTab(
                    String.valueOf(statisticsTabbedPane.getTabCount() - 1),
                    null, new JScrollPane(table),
                    "<html>Statistics in matches+contexts" + "<br>on Corpus: "
                            + corpusName + "<br>and Annotation Set: "
                            + annotationSetName + "<br>for the query: "
                            + results.get(0).getQueryString() + "</html>");
            if(bottomSplitPane.getDividerLocation()
                    / bottomSplitPane.getSize().getWidth() > 0.75) {
              bottomSplitPane.setDividerLocation(0.66);
            }
            statisticsTabbedPane.setSelectedIndex(statisticsTabbedPane
                    .getTabCount() - 1);
          }
        });
        mousePopup.add(menuItem);

      } else {
        // count values of one Annotation type

        menuItem = new JMenuItem("Occurrences in datastore");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "datastore");
            toolTip = toolTipTemplate.replaceFirst("kind", "datastore");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(corpusID, annotationSetID, type);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("datastore", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in matches");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "matches");
            toolTip = toolTipTemplate.replaceFirst("kind", "matches");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(results, type, true, false);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("matches", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "contexts");
            toolTip = toolTipTemplate.replaceFirst("kind", "contexts");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(results, type, false, true);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("contexts", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);

        menuItem = new JMenuItem("Occurrences in matches+contexts");
        menuItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ie) {
            description = descriptionTemplate.replaceFirst("kind", "mch+ctxt");
            toolTip = toolTipTemplate.replaceFirst("kind", "matches+contexts");
            int count;
            int numRow = checkStatistics();
            if(numRow == -1) {
              try { // retrieves the number of occurrences
                count = searcher.freq(results, type, true, true);
              } catch(SearchException se) {
                se.printStackTrace();
                return;
              }
              oneRowStatisticsTableModel.addRow(new Object[] {description,
                  count, ""});
              oneRowStatisticsTableToolTips.add(toolTip);
              numRow =
                      oneRowStatisticsTable
                              .rowModelToView(oneRowStatisticsTable
                                      .getRowCount() - 1);
            } else {
              count = (Integer)oneRowStatisticsTable.getValueAt(numRow, 1);
            }
            addStatistics("matches+contexts", count, numRow, e);
          }
        });
        mousePopup.add(menuItem);
      }
    }
  }

  protected class ResultTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
      String text = (String)value;
      if(text == null) {
        text = "";
      }
      int colModel = resultTable.convertColumnIndexToModel(column);
      // cut text in the middle if too long
      switch(colModel) {
        case ResultTableModel.RESULT_COLUMN:
        case ResultTableModel.FEATURES_COLUMN:
          if(text.length() > ResultTableModel.MAX_COL_WIDTH) {
            text =
                    text.substring(0, ResultTableModel.MAX_COL_WIDTH / 2)
                            + "..."
                            + text.substring(text.length()
                                    - (ResultTableModel.MAX_COL_WIDTH / 2));
          }
          text = text.replaceAll("(?:\r?\n)|\r", " ");
          text = text.replaceAll("\t", " ");
          break;
        default:
          // do nothing
          break;
      }
      Component component =
              super.getTableCellRendererComponent(table, text, isSelected,
                      hasFocus, row, column);
      if(!(component instanceof JLabel)) {
        return component;
      }
      JLabel label = (JLabel)component;
      label.setHorizontalAlignment(SwingConstants.LEFT);
      String tip = null;
      // add tooltips
      switch(colModel) {
        case ResultTableModel.LEFT_CONTEXT_COLUMN:
          label.setHorizontalAlignment(SwingConstants.RIGHT);
          break;
        case ResultTableModel.RESULT_COLUMN:
        case ResultTableModel.FEATURES_COLUMN:
          tip = value != null ? (String)value : "";
          if(tip.length() > ResultTableModel.MAX_COL_WIDTH) {            
            if(tip.length() > 1000) {
              tip =
                      tip.substring(0, 1000 / 2) + "<br>...<br>"
                              + tip.substring(tip.length() - (1000 / 2));
            }
            tip = tip.replaceAll("\\s*\n\\s*", "<br>");
            tip = tip.replaceAll("\\s+", " ");
            tip =
                    "<html><table width=\""
                            + (tip.length() > 150 ? "500" : "100%")
                            + "\" border=\"0\" cellspacing=\"0\">" + "<tr><td>"
                            + tip + "</td></tr>" + "</table></html>";
          }
          if(colModel == ResultTableModel.RESULT_COLUMN) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
          }
          break;
        default:
          // do nothing
          break;
      }
      label.setToolTipText(tip);
      return label;
    }
  }

  /**
   * Table model for the Result Tables.
   */
  protected class ResultTableModel extends AbstractTableModel {

    public ResultTableModel() {
      featureByTypeMap = new HashMap<String, String>();
    }

    @Override
    public int getRowCount() {
      return results.size();
    }

    @Override
    public int getColumnCount() {
      return COLUMN_COUNT;
    }

    @Override
    public String getColumnName(int columnIndex) {
      switch(columnIndex) {
        case LEFT_CONTEXT_COLUMN:
          return "Left context";
        case RESULT_COLUMN:
          return "Match";
        case RIGHT_CONTEXT_COLUMN:
          return "Right context";
        case FEATURES_COLUMN:
          return "Features";
        case QUERY_COLUMN:
          return "Query";
        case DOCUMENT_COLUMN:
          return "Document";
        case SET_COLUMN:
          return "Annotation set";
        default:
          return "?";
      }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Pattern result = (Pattern)results.get(rowIndex);
      switch(columnIndex) {
        case LEFT_CONTEXT_COLUMN:
          return result.getPatternText(result.getLeftContextStartOffset(),
                  result.getStartOffset()).replaceAll("[\n ]+", " ");
        case RESULT_COLUMN:
          return result.getPatternText(result.getStartOffset(),
                  result.getEndOffset());
        case RIGHT_CONTEXT_COLUMN:
          return result.getPatternText(result.getEndOffset(),
                  result.getRightContextEndOffset()).replaceAll("[\n ]+", " ");
        case FEATURES_COLUMN:
          StringBuffer buffer = new StringBuffer();
          for(Map.Entry<String, String> featureType : featureByTypeMap
                  .entrySet()) {
            String type = featureType.getKey();
            String feature = featureType.getValue();
            List<PatternAnnotation> annotations =
                    result.getPatternAnnotations(result.getStartOffset(),
                            result.getEndOffset());
            buffer.append(type).append('.').append(feature).append('=');
            for(PatternAnnotation annotation : annotations) {
              if(annotation.getType().equals(type)
                      && annotation.getFeature(feature) != null) {
                buffer.append(annotation.getFeatures().get(feature)).append(
                        ", ");
              }
            }
            if(buffer.length() > 2) {
              if(buffer.codePointAt(buffer.length() - 2) == ',') {
                // delete the last ", "
                buffer.delete(buffer.length() - 2, buffer.length());
                // and replace it with a "; "
                buffer.append("; ");
              } else if(buffer.codePointAt(buffer.length() - 1) == '=') {
                // delete the last "Type.Feature="
                buffer.delete(
                        buffer.length() - type.length() - feature.length() - 2,
                        buffer.length());
              }
            }
          }
          if(buffer.length() > 2) {
            // delete the last "; "
            buffer.delete(buffer.length() - 2, buffer.length());
          }
          return buffer.toString();
        case QUERY_COLUMN:
          return result.getQueryString();
        case DOCUMENT_COLUMN:
          return result.getDocumentID();
        case SET_COLUMN:
          return result.getAnnotationSetName();
        default:
          return Object.class;
      }
    }

    @Override
    public void fireTableDataChanged() {
      // reinitialise types and features to display in the "Features"
      // column
      featureByTypeMap.clear();
      for(int row = 0; row < numStackRows; row++) {
        if(!stackRows[row][DISPLAY].equals("false")
                && !stackRows[row][FEATURE].equals("")) {
          String feature = stackRows[row][FEATURE];
          String type = stackRows[row][ANNOTATION_TYPE];
          featureByTypeMap.put(type, feature);
        }
      }
      super.fireTableDataChanged();
    }

    /** Maximum number of characters for the result column. */
    static public final int MAX_COL_WIDTH = 40;

    static public final int LEFT_CONTEXT_COLUMN = 0;

    static public final int RESULT_COLUMN = 1;

    static public final int RIGHT_CONTEXT_COLUMN = 2;

    static public final int FEATURES_COLUMN = 3;

    static public final int QUERY_COLUMN = 4;

    static public final int DOCUMENT_COLUMN = 5;

    static public final int SET_COLUMN = 6;

    static public final int COLUMN_COUNT = 7;

    protected Map<String, String> featureByTypeMap;
  }

  /**
   * Panel that shows a table of shortcut, annotation type and feature
   * to display in the central view of the GUI.
   */
  protected class ConfigureStackViewFrame extends JFrame {

    private final int REMOVE = columnNames.length;

    private JTable configureStackViewTable;

    public ConfigureStackViewFrame(String title) {
      super(title);

      setLayout(new BorderLayout());

      JScrollPane scrollPane =
              new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.getViewport().setOpaque(true);

      configureStackViewTableModel = new ConfigureStackViewTableModel();
      configureStackViewTable = new XJTable(configureStackViewTableModel);
      ((XJTable)configureStackViewTable).setSortable(false);
      configureStackViewTable.setCellSelectionEnabled(true);

      // combobox used as cell editor

      String[] s = {"Crop middle", "Crop start", "Crop end"};
      JComboBox<String> cropBox = new JComboBox<String>(s);

      // set the cell renderer and/or editor for each column

      configureStackViewTable.getColumnModel().getColumn(DISPLAY)
              .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object color, boolean isSelected, boolean hasFocus,
                        int row, int col) {
                  JCheckBox checkBox = new JCheckBox();
                  checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                  checkBox.setToolTipText("Tick to display this row in central section.");
                  checkBox.setSelected((!table.getValueAt(row, col).equals(
                          "false")));
                  return checkBox;
                }
              });

      final class DisplayCellEditor extends AbstractCellEditor implements
                                                              TableCellEditor,
                                                              ActionListener {
        JCheckBox checkBox;

        public DisplayCellEditor() {
          checkBox = new JCheckBox();
          checkBox.setHorizontalAlignment(SwingConstants.CENTER);
          checkBox.addActionListener(this);
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
          return false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
          return (checkBox.isSelected()) ? "true" : "false";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int col) {
          checkBox.setSelected((!table.getValueAt(row, col).equals("false")));
          return checkBox;
        }
      }
      configureStackViewTable.getColumnModel().getColumn(DISPLAY)
              .setCellEditor(new DisplayCellEditor());

      configureStackViewTable.getColumnModel().getColumn(SHORTCUT)
              .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object color, boolean isSelected, boolean hasFocus,
                        int row, int col) {
                  Component c =
                          super.getTableCellRendererComponent(table, color,
                                  isSelected, hasFocus, row, col);
                  if(c instanceof JComponent) {
                    ((JComponent)c)
                            .setToolTipText("Shortcut can be used in queries "
                                    + "instead of \"AnnotationType.Feature\".");
                  }
                  c.setBackground(UIManager.getColor("CheckBox.background"));
                  return c;
                }
              });

      DefaultCellEditor cellEditor = new DefaultCellEditor(new JTextField());
      cellEditor.setClickCountToStart(0);
      configureStackViewTable.getColumnModel().getColumn(SHORTCUT)
              .setCellEditor(cellEditor);

      configureStackViewTable.getColumnModel().getColumn(ANNOTATION_TYPE)
              .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object color, boolean isSelected, boolean hasFocus,
                        int row, int col) {
                  String[] s = {stackRows[row][ANNOTATION_TYPE]};
                  return new JComboBox<String>(s);
                }
              });

      final class FeatureCellEditor extends AbstractCellEditor implements
                                                              TableCellEditor,
                                                              ActionListener {
        private JComboBox<String> featuresBox;

        public FeatureCellEditor() {
          featuresBox = new JComboBox<String>();
          featuresBox.setMaximumRowCount(10);
          featuresBox.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
          return (featuresBox.getSelectedItem() == null) ? "" : featuresBox
                  .getSelectedItem();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int col) {
          TreeSet<String> ts = new TreeSet<String>(stringCollator);
          if(populatedAnnotationTypesAndFeatures
                  .containsKey(configureStackViewTable.getValueAt(row,
                          ANNOTATION_TYPE))) {
            // this annotation type still exists in the datastore
            ts.addAll(populatedAnnotationTypesAndFeatures
                    .get(configureStackViewTable.getValueAt(row,
                            ANNOTATION_TYPE)));
          }
          DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(ts.toArray(new String[ts.size()]));
          dcbm.insertElementAt("", 0);
          featuresBox.setModel(dcbm);
          featuresBox.setSelectedItem(ts
                  .contains(configureStackViewTable
                          .getValueAt(row, col)) ? configureStackViewTable
                  .getValueAt(row, col) : "");
          return featuresBox;
        }
      }
      configureStackViewTable.getColumnModel().getColumn(FEATURE)
              .setCellEditor(new FeatureCellEditor());

      configureStackViewTable.getColumnModel().getColumn(FEATURE)
              .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object color, boolean isSelected, boolean hasFocus,
                        int row, int col) {
                  String[] s = {stackRows[row][FEATURE]};
                  return new JComboBox<String>(s);
                }
              });

      cellEditor = new DefaultCellEditor(cropBox);
      cellEditor.setClickCountToStart(0);
      configureStackViewTable.getColumnModel().getColumn(CROP)
              .setCellEditor(cellEditor);
      configureStackViewTable.getColumnModel().getColumn(CROP)
              .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                        Object color, boolean isSelected, boolean hasFocus,
                        int row, int col) {
                  String[] s = {stackRows[row][CROP]};
                  return new JComboBox<String>(s);
                }
              });

      final class AddRemoveCellEditorRenderer extends AbstractCellEditor
                                                                        implements
                                                                        TableCellRenderer,
                                                                        TableCellEditor,
                                                                        ActionListener {
        private JButton button;

        public AddRemoveCellEditorRenderer() {
          button = new JButton();
          button.setHorizontalAlignment(SwingConstants.CENTER);
          button.addActionListener(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object color, boolean isSelected, boolean hasFocus, int row,
                int col) {
          if(row == numStackRows) {
            // add button if it's the last row of the table
            button.setIcon(MainFrame.getIcon("crystal-clear-action-edit-add"));
            button.setToolTipText("Click to add this line.");
          } else {
            // remove button otherwise
            button.setIcon(MainFrame
                    .getIcon("crystal-clear-action-button-cancel"));
            button.setToolTipText("Click to remove this line.");
          }
          button.setSelected(isSelected);
          return button;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
          return false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          int row = configureStackViewTable.getEditingRow();
          fireEditingStopped();
          if(row == numStackRows) {
            if(stackRows[row][ANNOTATION_TYPE] != null
                    && !stackRows[row][ANNOTATION_TYPE].equals("")) {
              if(numStackRows == maxStackRows) {
                JOptionPane.showMessageDialog(configureStackViewFrame,
                        "The number of rows is limited to " + maxStackRows
                                + ".", "Alert", JOptionPane.ERROR_MESSAGE);
              } else {
                // add a new row
                numStackRows++;
                configureStackViewTableModel
                        .fireTableRowsInserted(row, row + 1);
                updateStackView();
                saveStackViewConfiguration();
              }
            } else {
              JOptionPane.showMessageDialog(configureStackViewFrame,
                      "Fill at least the Annotation type column.", "Alert",
                      JOptionPane.ERROR_MESSAGE);
            }
          } else {
            // delete a row
            deleteStackRow(row);
            configureStackViewTableModel.fireTableDataChanged();
            updateStackView();
            saveStackViewConfiguration();
          }
        }

        @Override
        public Object getCellEditorValue() {
          return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int col) {
          button.setIcon(MainFrame.getIcon(row == numStackRows
                  ? "crystal-clear-action-edit-add"
                  : "crystal-clear-action-button-cancel"));
          return button;
        }
      }
      configureStackViewTable.getColumnModel().getColumn(REMOVE)
              .setCellEditor(new AddRemoveCellEditorRenderer());
      configureStackViewTable.getColumnModel().getColumn(REMOVE)
              .setCellRenderer(new AddRemoveCellEditorRenderer());

      scrollPane.setViewportView(configureStackViewTable);

      add(scrollPane, BorderLayout.CENTER);

      JButton closeButton = new JButton("Close");
      closeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          configureStackViewFrame.setVisible(false);
        }
      });
      JButton moveRowUpButton = new JButton("Move row up");
      moveRowUpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if(configureStackViewTable.getRowCount() < 2) {
            return;
          }
          final int selectedRow = configureStackViewTable.getSelectedRow();
          int lastRow = configureStackViewTable.getRowCount() - 2;
          if(selectedRow > 0 && selectedRow <= lastRow) {
            String[] stackRow = stackRows[selectedRow - 1];
            stackRows[selectedRow - 1] = stackRows[selectedRow];
            stackRows[selectedRow] = stackRow;
            configureStackViewTableModel.fireTableDataChanged();
            updateStackView();
            saveStackViewConfiguration();
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                configureStackViewTable.changeSelection(selectedRow - 1,
                        SHORTCUT, false, false);
                configureStackViewTable.requestFocusInWindow();
              }
            });
          }
        }
      });
      JButton moveRowDownButton = new JButton("Move row down");
      moveRowDownButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if(configureStackViewTable.getRowCount() < 2) {
            return;
          }
          final int selectedRow = configureStackViewTable.getSelectedRow();
          int lastRow = configureStackViewTable.getRowCount() - 2;
          if(selectedRow >= 0 && selectedRow < lastRow) {
            String[] stackRow = stackRows[selectedRow + 1];
            stackRows[selectedRow + 1] = stackRows[selectedRow];
            stackRows[selectedRow] = stackRow;
            configureStackViewTableModel.fireTableDataChanged();
            updateStackView();
            saveStackViewConfiguration();
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                configureStackViewTable.changeSelection(selectedRow + 1,
                        SHORTCUT, false, false);
                configureStackViewTable.requestFocusInWindow();
              }
            });
          }
        }
      });
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(closeButton);
      buttonPanel.add(moveRowUpButton);
      buttonPanel.add(moveRowDownButton);
      add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getTable() {
      return configureStackViewTable;
    }
  }

  /**
   * Table model for the stack view configuration.
   */
  protected class ConfigureStackViewTableModel extends AbstractTableModel {

    private final int REMOVE = columnNames.length;

    // plus one to let the user adding a new row
    @Override
    public int getRowCount() {
      return Math.min(numStackRows + 1, maxStackRows + 1);
    }

    // plus one for the add/remove column
    @Override
    public int getColumnCount() {
      return columnNames.length + 1;
    }

    @Override
    public String getColumnName(int col) {
      return (col == REMOVE) ? "Add/Remove" : columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
      return true;
    }

    @Override
    public Class<?> getColumnClass(int c) {
      return String.class;
    }

    @Override
    public Object getValueAt(int row, int col) {
      if(col == REMOVE) {
        return null;
      }
      return stackRows[row][col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
      if(col == REMOVE) {
        return;
      }

      String valueString;
      if(value instanceof String) {
        valueString = (String)value;
      } else {
        valueString = "value should be a String";
      }

      if(col == SHORTCUT && !valueString.equals("")) {
        if(getTypesAndFeatures(null, null).keySet().contains(valueString)) {
          JOptionPane
                  .showMessageDialog(
                          configureStackViewFrame,
                          "A Shortcut cannot have the same name as an Annotation type.",
                          "Alert", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          int row2 = findStackRow(SHORTCUT, valueString);
          if(row2 >= 0 && row2 != row) {
            JOptionPane.showMessageDialog(configureStackViewFrame,
                    "A Shortcut with the same name already exists.", "Alert",
                    JOptionPane.ERROR_MESSAGE);
            return;
          }
        }
      }

      String previousValue = valueString;
      stackRows[row][col] = valueString;

      if(!stackRows[row][SHORTCUT].equals("")) {
        if(stackRows[row][ANNOTATION_TYPE].equals("")
                || stackRows[row][FEATURE].equals("")) {
          // TODO table should be updated
          configureStackViewFrame.getTable().getColumnModel().getColumn(col)
                  .getCellEditor().cancelCellEditing();
          fireTableCellUpdated(row, col);
          stackRows[row][col] = previousValue;
          JOptionPane.showMessageDialog(configureStackViewFrame,
                  "A Shortcut need to have a Feature.\n"
                          + "Choose a Feature or delete the Shortcut value.",
                  "Alert", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          int row2 =
                  findStackRow(ANNOTATION_TYPE,
                          stackRows[row][ANNOTATION_TYPE], FEATURE,
                          stackRows[row][FEATURE]);
          if(row2 >= 0 && row2 != row && !stackRows[row2][SHORTCUT].equals("")) {
            configureStackViewFrame.getTable().getColumnModel().getColumn(col)
                    .getCellEditor().cancelCellEditing();
            stackRows[row][col] = previousValue;
            fireTableCellUpdated(row, col);
            JOptionPane.showMessageDialog(configureStackViewFrame,
                    "You can only have one Shortcut for a couple (Annotation "
                            + "type, Feature).", "Alert",
                    JOptionPane.ERROR_MESSAGE);
            return;
          }
        }
      }

      if(stackRows[row][DISPLAY].equals("one time")) {
        // make a temporary row permanent if the user changes it
        stackRows[row][DISPLAY] = "true";
      }

      stackRows[row][col] = valueString;
      fireTableRowsUpdated(row, row);
      updateStackView();
      saveStackViewConfiguration();
    }
  }

  /**
   * JtextArea with autocompletion for the annotation types and
   * features, context menu and undo/redo.
   */
  protected class QueryTextArea extends JTextArea implements DocumentListener,
                                                 MouseListener {

    private static final String ENTER_ACTION = "enter";

    private static final String NEW_LINE = "new line";

    private static final String CANCEL_ACTION = "cancel";

    private static final String DOWN_ACTION = "down";

    private static final String UP_ACTION = "up";

    private static final String UNDO_ACTION = "undo";

    private static final String REDO_ACTION = "redo";

    private static final String NEXT_RESULT = "next result";

    private static final String PREVIOUS_RESULT = "previous result";

    protected DefaultListModel<String> queryListModel;

    protected JList<String> queryList;

    protected JWindow queryPopupWindow;

    protected JPopupMenu mousePopup;

    protected javax.swing.undo.UndoManager undo;

    protected UndoAction undoAction;

    protected RedoAction redoAction;

    /** offset of the first completion character */
    protected int start;

    /** offset of the last completion character */
    protected int end;

    protected int mode;

    protected static final int INSERT = 0;

    protected static final int POPUP_TYPES = 1;

    protected static final int POPUP_FEATURES = 2;

    protected static final int PROGRAMMATIC = 3;

    public QueryTextArea() {
      super();

      getDocument().addDocumentListener(this);
      addMouseListener(this);
      addAncestorListener(new AncestorListener() {
        @Override
        public void ancestorMoved(AncestorEvent event) {
        }

        @Override
        public void ancestorAdded(AncestorEvent event) {
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
          // no parent so need to be disposed explicitly
          queryPopupWindow.dispose();
        }
      });

      InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
      InputMap imw = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      ActionMap am = getActionMap();
      // bind keys to actions
      im.put(KeyStroke.getKeyStroke("ENTER"), ENTER_ACTION);
      am.put(ENTER_ACTION, new EnterAction());
      im.put(KeyStroke.getKeyStroke("control ENTER"), NEW_LINE);
      am.put(NEW_LINE, new NewLineAction());
      imw.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
      am.put(CANCEL_ACTION, new CancelAction());
      im.put(KeyStroke.getKeyStroke("DOWN"), DOWN_ACTION);
      am.put(DOWN_ACTION, new DownAction());
      im.put(KeyStroke.getKeyStroke("UP"), UP_ACTION);
      am.put(UP_ACTION, new UpAction());
      undoAction = new UndoAction();
      im.put(KeyStroke.getKeyStroke("control Z"), UNDO_ACTION);
      am.put(UNDO_ACTION, undoAction);
      redoAction = new RedoAction();
      im.put(KeyStroke.getKeyStroke("control Y"), REDO_ACTION);
      am.put(REDO_ACTION, redoAction);
      im.put(KeyStroke.getKeyStroke("alt DOWN"), NEXT_RESULT);
      am.put(NEXT_RESULT, new NextResultAction());
      im.put(KeyStroke.getKeyStroke("alt UP"), PREVIOUS_RESULT);
      am.put(PREVIOUS_RESULT, new PreviousResultAction());

      // list for autocompletion
      queryListModel = new DefaultListModel<String>();
      queryList = new JList<String>(queryListModel);
      queryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      queryList.setBackground(Color.WHITE);
      queryList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if(e.getClickCount() == 2) {
            new EnterAction().actionPerformed(null);
          }
        }
      });

      queryPopupWindow = new JWindow();
      queryPopupWindow.add(new JScrollPane(queryList));

      mousePopup = new JPopupMenu();
      mousePopup.add(new JMenuItem(new MultiplierAction(0, 1)));
      mousePopup.add(new JMenuItem(new MultiplierAction(0, 2)));
      mousePopup.add(new JMenuItem(new MultiplierAction(0, 3)));
      mousePopup.add(new JMenuItem(new MultiplierAction(0, 4)));
      mousePopup.add(new JMenuItem(new MultiplierAction(0, 5)));
      mousePopup.add(new JMenuItem(new MultiplierAction(1, 2)));
      mousePopup.add(new JMenuItem(new MultiplierAction(1, 3)));
      mousePopup.add(new JMenuItem(new MultiplierAction(1, 4)));
      mousePopup.add(new JMenuItem(new MultiplierAction(1, 5)));

      undo = new javax.swing.undo.UndoManager();
      getDocument().addUndoableEditListener(
              new javax.swing.event.UndoableEditListener() {
                @Override
                public void undoableEditHappened(
                        javax.swing.event.UndoableEditEvent e) {
                  // Remember the edit and update the menus
                  undo.addEdit(e.getEdit());
                  undoAction.updateUndoState();
                  redoAction.updateRedoState();
                }
              });

      start = 0;
      end = 0;
      mode = INSERT;

      addCaretListener(new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
          if((mode == POPUP_TYPES || mode == POPUP_FEATURES)
                  && (getCaretPosition() < start || getCaretPosition() > (end + 1))) {
            // cancel any autocompletion if the user put the caret
            // outside brackets when in POPUP mode
            cleanup();
          }
        }
      });
    }

    /** Add multiplier around the selected expression. */
    private class MultiplierAction extends AbstractAction {
      int from, upto;

      public MultiplierAction(int from, int upto) {
        super(from + " to " + upto + " time" + (upto == 1 ? "" : "s"));
        this.from = from;
        this.upto = upto;
      }

      @Override
      public void actionPerformed(ActionEvent ie) {
        try {
          getDocument().insertString(getSelectionStart(), "(", null);
          getDocument().insertString(getSelectionEnd(),
                  ")" + (from == 0 ? "*" : "+") + upto, null);
        } catch(javax.swing.text.BadLocationException e) {
          e.printStackTrace();
        }
      }
    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
    }

    @Override
    public void removeUpdate(DocumentEvent ev) {
      if(mode == PROGRAMMATIC || mode == INSERT) {
        return;
      }

      int pos = ev.getOffset();

      if(ev.getLength() != 1 || (pos < start || pos > end)) {
        // cancel any autocompletion if the user cut some text
        // or delete outside brackets when in POPUP mode
        cleanup();
        return;
      }
      if(mode == POPUP_TYPES) {
        end = pos;
        String type = getText().substring(start, end);
        if(!type.matches("[a-zA-Z0-9]+")) {
          return;
        }
        for(int i = 0; i < queryList.getModel().getSize(); i++) {
          if(startsWithIgnoreCase(
                  queryList.getModel().getElementAt(i), type)) {
            queryPopupWindow.setVisible(true);
            queryList.setSelectedIndex((i));
            queryList.ensureIndexIsVisible(i);
            break;
          }
        }
      } else if(mode == POPUP_FEATURES) {
        end = pos;
        String feature = getText().substring(start, end);
        if(!feature.matches("[a-zA-Z0-9]+")) {
          return;
        }
        for(int i = 0; i < queryList.getModel().getSize(); i++) {
          if(startsWithIgnoreCase(
                  queryList.getModel().getElementAt(i), feature)) {
            queryPopupWindow.setVisible(true);
            queryList.setSelectedIndex((i));
            queryList.ensureIndexIsVisible(i);
            break;
          }
        }
      }
    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
      if(mode == PROGRAMMATIC) {
        return;
      }

      int pos = ev.getOffset();

      if(ev.getLength() != 1) {
        // cancel any autocompletion if the user paste some text
        cleanup();
        return;
      }

      String typedChar = Character.toString(getText().charAt(pos));
      String previousChar =
              (pos > 0) ? Character.toString(getText().charAt(pos - 1)) : "";
      String nextChar =
              ((pos + 1) < getText().length()) ? Character.toString(getText()
                      .charAt(pos + 1)) : "";

      // switch accordingly to the key pressed and the context
      if(((typedChar.equals("{") && !previousChar.equals("\\")) || (typedChar
              .equals(",") && nextChar.equals("}"))) && mode == INSERT) {
        mode = POPUP_TYPES;
        start = pos + 1;
        end = pos + 1;
        SwingUtilities.invokeLater(new PopupTypeTask());

      } else if(typedChar.equals(".") && mode == INSERT) {
        mode = POPUP_FEATURES;
        start = pos + 1;
        end = pos + 1;
        SwingUtilities.invokeLater(new PopupFeatureTask());

      } else if(typedChar.matches("[a-zA-Z0-9]") && mode == POPUP_TYPES) {
        end = pos;
        String type = getText().substring(start, end + 1);
        boolean found = false;
        if(type.matches("[a-zA-Z0-9]+")) {
          for(int i = 0; i < queryList.getModel().getSize(); i++) {
            if(startsWithIgnoreCase(
                    queryList.getModel().getElementAt(i), type)) {
              queryPopupWindow.setVisible(true);
              queryList.setSelectedIndex(i);
              queryList.ensureIndexIsVisible(i);
              found = true;
              break;
            }
          }
        }
        if(!found) {
          queryPopupWindow.setVisible(false);
        }

      } else if(typedChar.matches("[a-zA-Z0-9]") && mode == POPUP_FEATURES) {
        end = pos;
        String feature = getText().substring(start, end + 1);
        boolean found = false;
        if(feature.matches("[a-zA-Z0-9]+")) {
          for(int i = 0; i < queryList.getModel().getSize(); i++) {
            if(startsWithIgnoreCase(
                    queryList.getModel().getElementAt(i), feature)) {
              queryPopupWindow.setVisible(true);
              queryList.setSelectedIndex(i);
              queryList.ensureIndexIsVisible(i);
              found = true;
              break;
            }
          }
        }
        if(!found) {
          queryPopupWindow.setVisible(false);
        }
      }
    }

    private boolean startsWithIgnoreCase(String str1, String str2) {
      return str1.toUpperCase().startsWith(str2.toUpperCase());
    }

    private void cleanup() {
      mode = INSERT;
      queryPopupWindow.setVisible(false);
    }

    private class PopupTypeTask implements Runnable {
      @Override
      public void run() {
        try {
          TreeSet<String> types = new TreeSet<String>(stringCollator);
          types.addAll(populatedAnnotationTypesAndFeatures.keySet());
          if(types.isEmpty()) {
            types.add("No annotation type found !");
          }
          queryListModel.clear();
          for(String type : types) {
            queryListModel.addElement(type);
          }
          queryList.setVisibleRowCount(Math.min(12, types.size()));
          Rectangle dotRect = modelToView(getCaret().getDot());
          queryPopupWindow.setLocation(getLocationOnScreen().x // x
                                                               // location
                                                               // of
                                                               // top-left
                                                               // text
                                                               // field
                  + (int)dotRect.getMaxX(), // caret X relative position
                  getLocationOnScreen().y // y location of top-left text
                                          // field
                          + (int)dotRect.getMaxY()); // caret Y relative
                                                     // position
          queryPopupWindow.pack();
          queryPopupWindow.setVisible(true);
          if(queryListModel.getSize() == 1) {
            // preselect if only one list item
            queryList.setSelectedIndex(0);
          }

        } catch(javax.swing.text.BadLocationException e) {
          e.printStackTrace();
        }
      }
    }

    private class PopupFeatureTask implements Runnable {
      @Override
      public void run() {
        // search the annotation type before the dot just typed
        int index =
                Math.max(getText().substring(0, end - 1).lastIndexOf("{"), Math
                        .max(getText().substring(0, end - 1).lastIndexOf(","),
                                getText().substring(0, end - 1).lastIndexOf(
                                        ", ") + 1));
        String type = getText().substring(index + 1, end - 1);
        if(!populatedAnnotationTypesAndFeatures.containsKey(type)) {
          // annotation type not found, do nothing
          cleanup();
          return;
        }
        try {
          TreeSet<String> features = new TreeSet<String>(stringCollator);
          features.addAll(populatedAnnotationTypesAndFeatures.get(type));
          queryListModel.clear();
          for(String feature : features) {
            queryListModel.addElement(feature);
          }
          queryList.setVisibleRowCount(Math.min(12, features.size()));
          Rectangle dotRect = modelToView(getCaret().getDot());
          queryPopupWindow.setLocation(getLocationOnScreen().x // x
                                                               // location
                                                               // of
                                                               // top-left
                                                               // text
                                                               // field
                  + (int)dotRect.getMaxX(), // caret relative position
                  getLocationOnScreen().y // y location of top-left text
                                          // field
                          + (int)dotRect.getMaxY()); // caret Y relative
                                                     // position
          queryPopupWindow.pack();
          queryPopupWindow.setVisible(true);
          if(queryListModel.getSize() == 1) {
            // preselect if only one list item
            queryList.setSelectedIndex(0);
          }

        } catch(javax.swing.text.BadLocationException e) {
          e.printStackTrace();
        }
      }
    }

    private class EnterAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        String selection = queryList.getSelectedValue();
        if(mode == POPUP_TYPES) {
          if(selection == null) {
            return;
          }
          mode = PROGRAMMATIC;
          try {
            if(end < getDocument().getLength()) {
              // delete already typed partial string
              getDocument().remove(start, end - start + 1);
            }
            // insert selected string from list
            getDocument().insertString(start, selection, null);
            if(getText().charAt(start - 1) != ',') {
              getDocument().insertString(start + selection.length(), "}", null);
              setCaretPosition(getCaretPosition() - 1);
            }
          } catch(javax.swing.text.BadLocationException e) {
            e.printStackTrace();
          }

        } else if(mode == POPUP_FEATURES) {
          if(selection == null) {
            return;
          }
          mode = PROGRAMMATIC;
          try {
            if(end < getDocument().getLength() && getText().charAt(end) != '}') {
              getDocument().remove(start, end - start + 1);
            }
            getDocument().insertString(start, selection, null);
            getDocument().insertString(start + selection.length(), "==\"\"",
                    null);
            setCaretPosition(getCaretPosition() - 1);
          } catch(javax.swing.text.BadLocationException e) {
            e.printStackTrace();
          }

        } else {
          executeQueryAction.actionPerformed(null);
        }
        cleanup();
      }
    }

    private class CancelAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        cleanup();
      }
    }

    private class DownAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        if(mode == POPUP_TYPES) {
          int index = queryList.getSelectedIndex();
          if((index + 1) < queryList.getModel().getSize()) {
            queryList.setSelectedIndex(index + 1);
            queryList.ensureIndexIsVisible(index + 1);
          }
        } else if(mode == POPUP_FEATURES) {
          int index = queryList.getSelectedIndex();
          if((index + 1) < queryList.getModel().getSize()) {
            queryList.setSelectedIndex(index + 1);
            queryList.ensureIndexIsVisible(index + 1);
          }
        }
      }
    }

    private class UpAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        if(mode == POPUP_TYPES) {
          int index = queryList.getSelectedIndex();
          if(index > 0) {
            queryList.setSelectedIndex(index - 1);
            queryList.ensureIndexIsVisible(index - 1);
          }
        } else if(mode == POPUP_FEATURES) {
          int index = queryList.getSelectedIndex();
          if(index > 0) {
            queryList.setSelectedIndex(index - 1);
            queryList.ensureIndexIsVisible(index - 1);
          }
        }
      }
    }

    private class PreviousResultAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        if(resultTable.getSelectedRow() > 0) {
          resultTable.setRowSelectionInterval(resultTable.getSelectedRow() - 1,
                  resultTable.getSelectedRow() - 1);
          resultTable.scrollRectToVisible(resultTable.getCellRect(
                  resultTable.getSelectedRow() - 1, 0, true));
        }
      }
    }

    private class NextResultAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        if(resultTable.getSelectedRow() + 1 < resultTable.getRowCount()) {
          resultTable.setRowSelectionInterval(resultTable.getSelectedRow() + 1,
                  resultTable.getSelectedRow() + 1);
          resultTable.scrollRectToVisible(resultTable.getCellRect(
                  resultTable.getSelectedRow() + 1, 0, true));
        }
      }
    }

    private class NewLineAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent ev) {
        try {
          getDocument().insertString(getCaretPosition(), "\n", null);
        } catch(javax.swing.text.BadLocationException e) {
          e.printStackTrace();
        }
      }
    }

    private class UndoAction extends AbstractAction {
      public UndoAction() {
        super("Undo");
        setEnabled(false);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          undo.undo();
        } catch(javax.swing.undo.CannotUndoException ex) {
          System.out.println("Unable to undo: " + ex);
          ex.printStackTrace();
        }
        updateUndoState();
        redoAction.updateRedoState();
      }

      protected void updateUndoState() {
        if(undo.canUndo()) {
          setEnabled(true);
          putValue(Action.NAME, undo.getUndoPresentationName());
        } else {
          setEnabled(false);
          putValue(Action.NAME, "Undo");
        }
      }
    }

    private class RedoAction extends AbstractAction {
      public RedoAction() {
        super("Redo");
        setEnabled(false);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          undo.redo();
        } catch(javax.swing.undo.CannotRedoException ex) {
          System.out.println("Unable to redo: " + ex);
          ex.printStackTrace();
        }
        updateRedoState();
        undoAction.updateUndoState();
      }

      protected void updateRedoState() {
        if(undo.canRedo()) {
          setEnabled(true);
          putValue(Action.NAME, undo.getRedoPresentationName());
        } else {
          setEnabled(false);
          putValue(Action.NAME, "Redo");
        }
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if(e.isPopupTrigger()) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if(e.isPopupTrigger()) {
        createPopup(e);
        mousePopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }

    private void createPopup(MouseEvent e) {
      if(getSelectedText() != null
              && QueryParser.isValidQuery(getSelectedText())) {
        // if the selected text is a valid expression then shows a popup
        // menu

      } else if(getDocument().getLength() > 3) {
        int positionclicked = viewToModel(e.getPoint());
        if(positionclicked >= getDocument().getLength()) {
          positionclicked = getDocument().getLength() - 1;
        }
        int start =
                getText().substring(0, positionclicked + 1).lastIndexOf("{");
        int end =
                getText().substring(positionclicked, getDocument().getLength())
                        .indexOf("}") + positionclicked;
        if(start != -1
                && end != -1
                && QueryParser
                        .isValidQuery(getText().substring(start, end + 1))) {
          // select the shortest valid enclosing braced expression
          // and shows a popup menu
          setSelectionStart(start);
          setSelectionEnd(end + 1);
        }
      }
    }

  }

  /**
   * Called by the GUI when this viewer/editor has to initialise itself
   * for a specific object.
   * 
   * @param target the object (be it a {@link gate.Resource},
   *          {@link gate.DataStore}or whatever) this viewer has to
   *          display
   */
  @Override
  public void setTarget(Object target) {

    if(!(target instanceof LuceneDataStoreImpl)
            && !(target instanceof Searcher)) {
      throw new IllegalArgumentException(
              "The GATE LuceneDataStoreSearchGUI can only be used with a GATE LuceneDataStores!\n"
                      + target.getClass().toString()
                      + " is not a GATE LuceneDataStore or an object of Searcher!");
    }

    this.target = target;

    // standalone Java application
    if(target instanceof LuceneDataStoreImpl) {

      ((LuceneDataStoreImpl)target).addDatastoreListener(this);
      corpusToSearchIn.setEnabled(true);
      searcher = ((LuceneDataStoreImpl)target).getSearcher();

      updateSetsTypesAndFeatures();

      try {
        // get the corpus names from the datastore
        java.util.List<String> corpusPIds =
                ((LuceneDataStoreImpl)target).getLrIds(SerialCorpusImpl.class
                        .getName());
        if(corpusIds != null) {
          for(Object corpusPId : corpusPIds) {
            String name = ((LuceneDataStoreImpl)target).getLrName(corpusPId);
            this.corpusIds.add(corpusPId);
            // add the corpus name to combobox
            ((DefaultComboBoxModel<String>)corpusToSearchIn.getModel())
                    .addElement(name);
          }
        }
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            corpusToSearchIn.updateUI();
            corpusToSearchIn.setSelectedItem(Constants.ENTIRE_DATASTORE);
          }
        });
      } catch(PersistenceException e) {
        System.out.println("Couldn't find any available corpusIds.");
        throw new GateRuntimeException(e);
      }
    }
    // Java Web Start application
    else {
      searcher = (Searcher)target;
      corpusToSearchIn.setEnabled(false);

      // find out all annotation sets that are indexed
      try {
        annotationSetIDsFromDataStore = searcher.getIndexedAnnotationSetNames();
        allAnnotTypesAndFeaturesFromDatastore =
                searcher.getAnnotationTypesMap();

        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            updateAnnotationSetsList();
          }
        });
      } catch(SearchException e) {
        throw new GateRuntimeException(e);
      }
    }
  }

  /**
   * This method is called by datastore when a new resource is adopted
   */
  @Override
  public void resourceAdopted(DatastoreEvent de) {
    // don't want to do anything here
  }

  /**
   * This method is called by datastore when an existing resource is
   * deleted
   */
  @Override
  public void resourceDeleted(DatastoreEvent de) {
    Resource resource = de.getResource();
    if(resource instanceof Corpus) {
      // lets check if it is already available in our list
      Object id = de.getResourceID();
      int index = corpusIds.indexOf(id);
      if(index < 0) {
        return;
      }

      // skip the first element in combo box that is "EntireDataStore"
      index++;

      // now lets remove it from the comboBox as well
      ((DefaultComboBoxModel<String>)corpusToSearchIn.getModel())
              .removeElementAt(index);
    }
    // Added a refresh button which user should click to refresh types
    //updateSetsTypesAndFeatures();
  }

  /**
   * This method is called when a resource is written into the datastore
   */
  @Override
  public void resourceWritten(DatastoreEvent de) {
    Resource resource = de.getResource();
    if(resource instanceof Corpus) {
      // lets check if it is already available in our list
      Object id = de.getResourceID();
      if(!corpusIds.contains(id)) {
        // we need to add its name to the combobox
        corpusIds.add(id);
        ((DefaultComboBoxModel<String>)corpusToSearchIn.getModel()).addElement(resource
                .getName());
      }
    }
  }

  protected void updateSetsTypesAndFeatures() {

    try {
      annotationSetIDsFromDataStore = searcher.getIndexedAnnotationSetNames();
      allAnnotTypesAndFeaturesFromDatastore = searcher.getAnnotationTypesMap();
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          updateAnnotationSetsList();
        }
      });

    } catch(SearchException se) {
      throw new GateRuntimeException(se);
    }
  }

  /**
   * A button with a nice etched border that changes when mouse over,
   * select or press it.
   */
  protected class ButtonBorder extends JButton {
    /**
     * Create a button.
     * 
     * @param highlight color of the hightlight
     * @param insets margin between content and border
     * @param showBorderWhenInactive true if there should always be a
     *          border
     */
    public ButtonBorder(final Color highlight, final Insets insets,
            final boolean showBorderWhenInactive) {
      final CompoundBorder borderDarker =
              new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED,
                      highlight, highlight.darker()), new EmptyBorder(insets));
      final CompoundBorder borderDarkerDarker =
              new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED,
                      highlight, highlight.darker().darker()), new EmptyBorder(
                      insets));
      setBorder(borderDarker);
      setBorderPainted(showBorderWhenInactive);
      setContentAreaFilled(false);
      setFocusPainted(false);
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setBorder(borderDarkerDarker);
          button.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setBorder(borderDarker);
          button.setBorderPainted(showBorderWhenInactive);
        }

        @Override
        public void mousePressed(MouseEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setContentAreaFilled(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setContentAreaFilled(false);
        }
      });
      addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setBorder(borderDarkerDarker);
          button.setBorderPainted(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
          JButton button = ((JButton)e.getComponent());
          button.setBorder(borderDarker);
          button.setBorderPainted(showBorderWhenInactive);
        }
      });
    }
  }

}
