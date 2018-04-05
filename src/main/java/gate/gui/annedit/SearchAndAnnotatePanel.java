/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Thomas Heitz, Nov 21, 2007
 *
 * $Id: SchemaAnnotationEditor.java 9221 2007-11-14 17:46:37Z valyt $
 */

package gate.gui.annedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gate.Annotation;
import gate.Factory;
import gate.FeatureMap;
import gate.event.AnnotationSetListener;
import gate.resources.img.svg.ClosedIcon;
import gate.resources.img.svg.ExpandedIcon;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;

/**
 * Build a GUI for searching and annotating annotations in a text. It needs to
 * be called from a {@link gate.gui.annedit.OwnedAnnotationEditor}.
 * <p>
 * Here is an example how to add it to a JPanel panel.
 *
 * <pre>
 * SearchAndAnnotatePanel searchPanel =
 *     new SearchAndAnnotatePanel(panel.getBackground(), this, window);
 * panel.add(searchPanel);
 * </pre>
 */
public class SearchAndAnnotatePanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * The annotation editor that use this search and annotate panel.
   */
  private OwnedAnnotationEditor annotationEditor;

  /**
   * Window that contains the annotation editor.
   */
  private Window annotationEditorWindow;

  /**
   * Listener for updating the list of searched annotations.
   */
  protected AnnotationSetListener annotationSetListener;

  /**
   * The box used to host the search pane.
   */
  protected Box searchBox;

  /**
   * The pane containing the UI for search and annotate functionality.
   */
  protected JPanel searchPane;

  /**
   * Text field for searching
   */
  protected JTextField searchTextField;

  /**
   * Checkbox for enabling RegEx searching.
   */
  protected JCheckBox searchRegExpChk;

  /**
   * Help button that gives predefined regular expressions.
   */
  protected JButton helpRegExpButton;

  /**
   * Checkbox for enabling case sensitive searching.
   */
  protected JCheckBox searchCaseSensChk;

  /**
   * Checkbox for enabling whole word searching.
   */
  protected JCheckBox searchWholeWordsChk;

  /**
   * Checkbox for enabling whole word searching.
   */
  protected JCheckBox searchHighlightsChk;

  /**
   * Checkbox for showing the search UI.
   */
  protected JCheckBox searchEnabledCheck;

  /**
   * Shared instance of the matcher.
   */
  protected Matcher matcher;

  protected FindFirstAction findFirstAction;

  protected FindPreviousAction findPreviousAction;

  protected FindNextAction findNextAction;

  protected AnnotateMatchAction annotateMatchAction;

  protected AnnotateAllMatchesAction annotateAllMatchesAction;

  protected UndoAnnotateAllMatchesAction undoAnnotateAllMatchesAction;

  protected int nextMatchStartsFrom;

  protected String content;

  /**
   * Start and end index of the all the matches.
   */
  protected LinkedList<Vector<Integer>> matchedIndexes;

  /**
   * List of annotations ID that have been created by the
   * AnnotateAllMatchesAction.
   */
  protected LinkedList<Annotation> annotateAllAnnotationsID;

  protected SmallButton firstSmallButton;

  protected SmallButton annotateAllMatchesSmallButton;

  public SearchAndAnnotatePanel(Color color,
      OwnedAnnotationEditor annotationEditor, Window window) {

    this.annotationEditor = annotationEditor;
    annotationEditorWindow = window;

    initGui(color);

    // initially searchBox is collapsed
    searchBox.remove(searchPane);
    searchCaseSensChk.setVisible(false);
    searchRegExpChk.setVisible(false);
    searchWholeWordsChk.setVisible(false);
    searchHighlightsChk.setVisible(false);

    // if the user never gives the focus to the textPane then
    // there will never be any selection in it so we force it
    getOwner().getTextComponent().requestFocusInWindow();

    initListeners();

    content = getOwner().getDocument().getContent().toString();
  }

  /**
   * Build the GUI with JPanels and Boxes.
   *
   * @param color
   *          Color of the background. _ _ _ _ _ V Search & Annotate |_| Case
   *          |_| Regexp |_| Whole |_| Highlights
   *          _______________________________________________
   *          |V_Searched_Expression__________________________| |?| |First|
   *          |Prev.| |Next| |Annotate| |Ann. all next|
   */
  protected void initGui(Color color) {

    JPanel mainPane = new JPanel();
    mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
    mainPane.setBackground(color);
    mainPane.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));

    setLayout(new BorderLayout());
    add(mainPane, BorderLayout.CENTER);

    searchBox = Box.createVerticalBox();
    String aTitle = "Open Search & Annotate tool";
    JLabel label = new JLabel(aTitle);
    searchBox.setMinimumSize(new Dimension(label.getPreferredSize().width, 0));
    searchBox.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel firstLinePane = new JPanel();
    firstLinePane.setAlignmentX(Component.LEFT_ALIGNMENT);
    firstLinePane.setAlignmentY(Component.TOP_ALIGNMENT);
    firstLinePane.setLayout(new BoxLayout(firstLinePane, BoxLayout.Y_AXIS));
    firstLinePane.setBackground(color);
    Box hBox = Box.createHorizontalBox();
    searchEnabledCheck = new JCheckBox(aTitle, new ClosedIcon(12, 12), false);
    searchEnabledCheck.setSelectedIcon(new ExpandedIcon(12,12));
    searchEnabledCheck.setBackground(color);
    searchEnabledCheck.setToolTipText("<html>Allows to search for an "
        + "expression and<br>annotate one or all the matches.</html>");
    hBox.add(searchEnabledCheck);
    hBox.add(Box.createHorizontalStrut(5));
    searchCaseSensChk = new JCheckBox("Case", true);
    searchCaseSensChk.setToolTipText("Case sensitive search.");
    searchCaseSensChk.setBackground(color);
    hBox.add(searchCaseSensChk);
    hBox.add(Box.createHorizontalStrut(5));
    searchRegExpChk = new JCheckBox("Regexp", false);
    searchRegExpChk.setToolTipText("Regular expression search.");
    searchRegExpChk.setBackground(color);
    hBox.add(searchRegExpChk);
    hBox.add(Box.createHorizontalStrut(5));
    searchWholeWordsChk = new JCheckBox("Whole", false);
    searchWholeWordsChk.setBackground(color);
    searchWholeWordsChk.setToolTipText("Whole word search.");
    hBox.add(searchWholeWordsChk);
    hBox.add(Box.createHorizontalStrut(5));
    searchHighlightsChk = new JCheckBox("Highlights", false);
    searchHighlightsChk
        .setToolTipText("Restrict the search on the highlighted annotations.");
    searchHighlightsChk.setBackground(color);
    hBox.add(searchHighlightsChk);
    hBox.add(Box.createHorizontalGlue());
    firstLinePane.add(hBox);
    searchBox.add(firstLinePane);

    searchPane = new JPanel();
    searchPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    searchPane.setAlignmentY(Component.TOP_ALIGNMENT);
    searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.Y_AXIS));
    searchPane.setBackground(color);
    hBox = Box.createHorizontalBox();
    hBox.setBorder(BorderFactory.createEmptyBorder(3, 0, 5, 0));
    hBox.add(Box.createHorizontalStrut(5));
    searchTextField = new JTextField(10);
    searchTextField.setToolTipText("Enter an expression to search for.");
    // disallow vertical expansion
    searchTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        searchTextField.getPreferredSize().height));
    searchTextField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        findFirstAction.actionPerformed(null);
      }
    });
    hBox.add(searchTextField);
    hBox.add(Box.createHorizontalStrut(2));
    helpRegExpButton = new JButton("?");
    helpRegExpButton.setMargin(new Insets(0, 2, 0, 2));
    helpRegExpButton.setToolTipText("GATE search expression builder.");
    helpRegExpButton.addActionListener(new SearchExpressionsAction(
        searchTextField, annotationEditorWindow, searchRegExpChk));
    hBox.add(helpRegExpButton);
    hBox.add(Box.createHorizontalGlue());
    searchPane.add(hBox);
    hBox = Box.createHorizontalBox();
    hBox.add(Box.createHorizontalStrut(5));
    findFirstAction = new FindFirstAction();
    firstSmallButton = new SmallButton(findFirstAction);
    hBox.add(firstSmallButton);
    hBox.add(Box.createHorizontalStrut(5));
    findPreviousAction = new FindPreviousAction();
    findPreviousAction.setEnabled(false);
    hBox.add(new SmallButton(findPreviousAction));
    hBox.add(Box.createHorizontalStrut(5));
    findNextAction = new FindNextAction();
    findNextAction.setEnabled(false);
    hBox.add(new SmallButton(findNextAction));
    hBox.add(Box.createHorizontalStrut(5));
    annotateMatchAction = new AnnotateMatchAction();
    annotateMatchAction.setEnabled(false);
    hBox.add(new SmallButton(annotateMatchAction));
    hBox.add(Box.createHorizontalStrut(5));
    annotateAllMatchesAction = new AnnotateAllMatchesAction();
    undoAnnotateAllMatchesAction = new UndoAnnotateAllMatchesAction();
    annotateAllMatchesSmallButton = new SmallButton(annotateAllMatchesAction);
    annotateAllMatchesAction.setEnabled(false);
    undoAnnotateAllMatchesAction.setEnabled(false);
    hBox.add(annotateAllMatchesSmallButton);
    hBox.add(Box.createHorizontalStrut(5));
    searchPane.add(hBox);
    searchBox.add(searchPane);

    mainPane.add(searchBox);
  }

  protected void initListeners() {

    searchEnabledCheck.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(searchEnabledCheck.isSelected()) {
          // add the search box if not already there
          if(!searchBox.isAncestorOf(searchPane)) {
            // if empty, initialise the search field to the text
            // of the current annotation
            String searchText = searchTextField.getText();
            if(searchText == null || searchText.trim().length() == 0) {
              if(annotationEditor.getAnnotationCurrentlyEdited() != null
                  && getOwner() != null) {
                String annText =
                    getOwner().getDocument().getContent().toString().substring(
                        annotationEditor.getAnnotationCurrentlyEdited()
                            .getStartNode().getOffset().intValue(),
                        annotationEditor.getAnnotationCurrentlyEdited()
                            .getEndNode().getOffset().intValue());
                searchTextField.setText(annText);
              }
            }
            searchBox.add(searchPane);
          }
          searchEnabledCheck.setText("");
          searchCaseSensChk.setVisible(true);
          searchRegExpChk.setVisible(true);
          searchWholeWordsChk.setVisible(true);
          searchHighlightsChk.setVisible(true);
          searchTextField.requestFocusInWindow();
          searchTextField.selectAll();
          annotationEditorWindow.pack();
          annotationEditor.setPinnedMode(true);

        } else {
          if(searchBox.isAncestorOf(searchPane)) {
            searchEnabledCheck.setText("Open Search & Annotate tool");
            searchBox.remove(searchPane);
            searchCaseSensChk.setVisible(false);
            searchRegExpChk.setVisible(false);
            searchWholeWordsChk.setVisible(false);
            searchHighlightsChk.setVisible(false);
            if(annotationEditor.getAnnotationCurrentlyEdited() != null) {
              annotationEditor.setEditingEnabled(true);
            }
            annotationEditorWindow.pack();
          }
        }
      }
    });

    this.addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) {
        // put the selection of the document into the search text field
        if(searchTextField.getText().trim().length() == 0
            && getOwner().getTextComponent().getSelectedText() != null) {
          searchTextField
              .setText(getOwner().getTextComponent().getSelectedText());
        }
      }

      @Override
      public void ancestorRemoved(AncestorEvent event) {
        // if the editor window is closed
        enableActions(false);
      }

      @Override
      public void ancestorMoved(AncestorEvent event) {
        // do nothing
      }
    });

    searchTextField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
        enableActions(false);
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        enableActions(false);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        enableActions(false);
      }
    });

    searchCaseSensChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        enableActions(false);
      }
    });

    searchRegExpChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        enableActions(false);
      }
    });

    searchWholeWordsChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        enableActions(false);
      }
    });

    searchHighlightsChk.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        enableActions(false);
      }
    });

  }

  private void enableActions(boolean state) {
    findPreviousAction.setEnabled(state);
    findNextAction.setEnabled(state);
    annotateMatchAction.setEnabled(state);
    annotateAllMatchesAction.setEnabled(state);
    if(annotateAllMatchesSmallButton.getAction()
        .equals(undoAnnotateAllMatchesAction)) {
      annotateAllMatchesSmallButton.setAction(annotateAllMatchesAction);
    }
  }

  private boolean isAnnotationEditorReady() {
    if(!annotationEditor.editingFinished() || getOwner() == null
        || annotationEditor.getAnnotationCurrentlyEdited() == null
        || annotationEditor.getAnnotationSetCurrentlyEdited() == null) {
      annotationEditorWindow.setVisible(false);
      JOptionPane.showMessageDialog(annotationEditorWindow,
          (annotationEditor.getAnnotationCurrentlyEdited() == null
              ? "Please select an existing annotation\n"
                  + "or create a new one then select it."
              : "Please set all required features in the feature table."),
          "GATE", JOptionPane.INFORMATION_MESSAGE);
      annotationEditorWindow.setVisible(true);
      return false;
    } else {
      return true;
    }
  }

  protected class FindFirstAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public FindFirstAction() {
      super("First");
      super.putValue(SHORT_DESCRIPTION, "Finds the first occurrence.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_F);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(!isAnnotationEditorReady()) { return; }
      annotationEditor.setPinnedMode(true);
      annotationEditor.setEditingEnabled(false);
      String patternText = searchTextField.getText();
      Pattern pattern;

      try {
        String prefixPattern = searchWholeWordsChk.isSelected() ? "\\b" : "";
        prefixPattern += searchRegExpChk.isSelected() ? "" : "\\Q";
        String suffixPattern = searchRegExpChk.isSelected() ? "" : "\\E";
        suffixPattern += searchWholeWordsChk.isSelected() ? "\\b" : "";
        patternText = prefixPattern + patternText + suffixPattern;
        // TODO: Pattern.UNICODE_CASE prevent insensitive case to work
        // for Java 1.5 but works with Java 1.6
        pattern = searchCaseSensChk.isSelected()
            ? Pattern.compile(patternText)
            : Pattern.compile(patternText, Pattern.CASE_INSENSITIVE);

      } catch(PatternSyntaxException e) {
        // hides the annotator window
        // to be able to see the dialog window
        annotationEditorWindow.setVisible(false);
        JOptionPane.showMessageDialog(annotationEditorWindow,
            "Invalid regular expression.\n\n"
                + e.toString().replaceFirst("^.+PatternSyntaxException: ", ""),
            "GATE", JOptionPane.INFORMATION_MESSAGE);
        annotationEditorWindow.setVisible(true);
        return;
      }

      matcher = pattern.matcher(content);
      boolean found = false;
      int start = -1;
      int end = -1;
      nextMatchStartsFrom = 0;
      while(matcher.find(nextMatchStartsFrom) && !found) {
        start = (matcher.groupCount() > 0) ? matcher.start(1) : matcher.start();
        end = (matcher.groupCount() > 0) ? matcher.end(1) : matcher.end();
        found = false;
        if(searchHighlightsChk.isSelected()) {
          javax.swing.text.Highlighter.Highlight[] highlights =
              getOwner().getTextComponent().getHighlighter().getHighlights();
          for(javax.swing.text.Highlighter.Highlight h : highlights) {
            if(h.getStartOffset() <= start && h.getEndOffset() >= end) {
              found = true;
              break;
            }
          }
        } else {
          found = true;
        }
        nextMatchStartsFrom = end;
      }

      if(found) {
        findNextAction.setEnabled(true);
        annotateMatchAction.setEnabled(true);
        annotateAllMatchesAction.setEnabled(false);
        matchedIndexes = new LinkedList<Vector<Integer>>();
        Vector<Integer> v = new Vector<Integer>(2);
        v.add(start);
        v.add(end);
        matchedIndexes.add(v);
        getOwner().getTextComponent().select(start, end);
        annotationEditor.placeDialog(start, end);

      } else {
        // no match found
        findNextAction.setEnabled(false);
        annotateMatchAction.setEnabled(false);
      }
      findPreviousAction.setEnabled(false);
    }
  }

  protected class FindPreviousAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public FindPreviousAction() {
      super("Prev.");
      super.putValue(SHORT_DESCRIPTION, "Finds the previous occurrence.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_P);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(!isAnnotationEditorReady()) { return; }
      annotationEditor.setEditingEnabled(false);
      // the first time we invoke previous action we want to go two
      // previous matches back not just one
      matchedIndexes.removeLast();

      Vector<Integer> v;
      if(matchedIndexes.size() == 1) {
        // no more previous annotation, disable the action
        findPreviousAction.setEnabled(false);
      }
      v = matchedIndexes.getLast();
      int start = v.firstElement();
      int end = v.lastElement();
      getOwner().getTextComponent().select(start, end);
      annotationEditor.placeDialog(start, end);
      // reset the matcher for the next FindNextAction
      nextMatchStartsFrom = start;
      findNextAction.setEnabled(true);
      annotateMatchAction.setEnabled(true);
    }
  }

  protected class FindNextAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public FindNextAction() {
      super("Next");
      super.putValue(SHORT_DESCRIPTION, "Finds the next occurrence.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(!isAnnotationEditorReady()) { return; }
      annotationEditor.setEditingEnabled(false);
      boolean found = false;
      int start = -1;
      int end = -1;
      nextMatchStartsFrom = getOwner().getTextComponent().getCaretPosition();

      while(matcher.find(nextMatchStartsFrom) && !found) {
        start = (matcher.groupCount() > 0) ? matcher.start(1) : matcher.start();
        end = (matcher.groupCount() > 0) ? matcher.end(1) : matcher.end();
        found = false;
        if(searchHighlightsChk.isSelected()) {
          javax.swing.text.Highlighter.Highlight[] highlights =
              getOwner().getTextComponent().getHighlighter().getHighlights();
          for(javax.swing.text.Highlighter.Highlight h : highlights) {
            if(h.getStartOffset() <= start && h.getEndOffset() >= end) {
              found = true;
              break;
            }
          }
        } else {
          found = true;
        }
        nextMatchStartsFrom = end;
      }

      if(found) {
        Vector<Integer> v = new Vector<Integer>(2);
        v.add(start);
        v.add(end);
        matchedIndexes.add(v);
        getOwner().getTextComponent().select(start, end);
        annotationEditor.placeDialog(start, end);
        findPreviousAction.setEnabled(true);
      } else {
        // no more matches possible
        findNextAction.setEnabled(false);
        annotateMatchAction.setEnabled(false);
      }
    }
  }

  protected class AnnotateMatchAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public AnnotateMatchAction() {
      super("Annotate");
      super.putValue(SHORT_DESCRIPTION, "Annotates the current match.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(!isAnnotationEditorReady()) { return; }
      int start = getOwner().getTextComponent().getSelectionStart();
      int end = getOwner().getTextComponent().getSelectionEnd();
      FeatureMap features = Factory.newFeatureMap();
      if(annotationEditor.getAnnotationCurrentlyEdited().getFeatures() != null)
        features.putAll(
            annotationEditor.getAnnotationCurrentlyEdited().getFeatures());
      try {
        Integer id = annotationEditor.getAnnotationSetCurrentlyEdited().add(
            new Long(start), new Long(end),
            annotationEditor.getAnnotationCurrentlyEdited().getType(),
            features);
        Annotation newAnn =
            annotationEditor.getAnnotationSetCurrentlyEdited().get(id);
        getOwner().getTextComponent().select(end, end);
        // set the annotation as selected
        getOwner().selectAnnotation(new AnnotationDataImpl(
            annotationEditor.getAnnotationSetCurrentlyEdited(), newAnn));
        annotationEditor.editAnnotation(newAnn,
            annotationEditor.getAnnotationSetCurrentlyEdited());
        annotateAllMatchesAction.setEnabled(true);
        if(annotateAllMatchesSmallButton.getAction()
            .equals(undoAnnotateAllMatchesAction)) {
          annotateAllMatchesSmallButton.setAction(annotateAllMatchesAction);
        }
      } catch(InvalidOffsetException e) {
        // the offsets here should always be valid.
        throw new GateRuntimeException(e);
      }
    }
  }

  protected class AnnotateAllMatchesAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public AnnotateAllMatchesAction() {
      super("Ann. all next");
      super.putValue(SHORT_DESCRIPTION, "Annotates all the following matches.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_L);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(!isAnnotationEditorReady()) { return; }
      annotateAllAnnotationsID = new LinkedList<Annotation>();
      boolean found = false;
      int start = -1;
      int end = -1;
      nextMatchStartsFrom = getOwner().getTextComponent().getCaretPosition();

      do {
        found = false;
        while(matcher.find(nextMatchStartsFrom) && !found) {
          start =
              (matcher.groupCount() > 0) ? matcher.start(1) : matcher.start();
          end = (matcher.groupCount() > 0) ? matcher.end(1) : matcher.end();
          if(searchHighlightsChk.isSelected()) {
            javax.swing.text.Highlighter.Highlight[] highlights =
                getOwner().getTextComponent().getHighlighter().getHighlights();
            for(javax.swing.text.Highlighter.Highlight h : highlights) {
              if(h.getStartOffset() <= start && h.getEndOffset() >= end) {
                found = true;
                break;
              }
            }
          } else {
            found = true;
          }
          nextMatchStartsFrom = end;
        }
        if(found) {
          annotateCurrentMatch(start, end);
        }
      } while(found && !matcher.hitEnd());

      annotateAllMatchesSmallButton.setAction(undoAnnotateAllMatchesAction);
      undoAnnotateAllMatchesAction.setEnabled(true);
    }

    private void annotateCurrentMatch(int start, int end) {
      FeatureMap features = Factory.newFeatureMap();
      features.put("safe.regex", "true");
      if(annotationEditor.getAnnotationCurrentlyEdited().getFeatures() != null)
        features.putAll(
            annotationEditor.getAnnotationCurrentlyEdited().getFeatures());
      try {
        Integer id = annotationEditor.getAnnotationSetCurrentlyEdited().add(
            new Long(start), new Long(end),
            annotationEditor.getAnnotationCurrentlyEdited().getType(),
            features);
        Annotation newAnn =
            annotationEditor.getAnnotationSetCurrentlyEdited().get(id);
        annotateAllAnnotationsID.add(newAnn);
      } catch(InvalidOffsetException e) {
        // the offsets here should always be valid.
        throw new GateRuntimeException(e);
      }
    }
  }

  /**
   * Remove the annotations added by the last action that annotate all matches.
   */
  protected class UndoAnnotateAllMatchesAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    public UndoAnnotateAllMatchesAction() {
      super("Undo");
      super.putValue(SHORT_DESCRIPTION, "Undo previous annotate all action.");
      super.putValue(MNEMONIC_KEY, KeyEvent.VK_U);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

      for(Annotation annotation : annotateAllAnnotationsID) {
        annotationEditor.getAnnotationSetCurrentlyEdited().remove(annotation);
      }

      if(annotationEditor.getAnnotationSetCurrentlyEdited() == null) {
        annotationEditor.setEditingEnabled(false);
      }

      annotateAllMatchesSmallButton.setAction(annotateAllMatchesAction);
      annotateAllMatchesAction.setEnabled(false);
    }
  }

  /**
   * A smaller JButton with less margins.
   */
  protected class SmallButton extends JButton {
    private static final long serialVersionUID = 1L;

    public SmallButton(Action a) {
      super(a);
      setMargin(new Insets(0, 2, 0, 2));
    }
  }

  /**
   * @return the owner
   */
  public AnnotationEditorOwner getOwner() {
    return annotationEditor.getOwner();
  }

}
