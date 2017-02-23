/*
 *  Copyright (c) 1998-2009, The University of Sheffield and Ontotext.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz - 7 July 2009
 *
 *  $Id$
 */

package gate.gui.docview;

import gate.event.AnnotationListener;
import gate.event.AnnotationEvent;
import gate.gui.MainFrame;
import gate.gui.annedit.AnnotationData;
import gate.gui.annedit.AnnotationDataImpl;
import gate.*;
import gate.util.InvalidOffsetException;
import gate.util.OffsetComparator;
import gate.gui.docview.AnnotationSetsView.*;
import gate.gui.docview.AnnotationStack.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.util.List;
import java.text.Collator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Show a stack view of highlighted annotations in the document
 * centred on the document caret.
 *
 * When double clicked, an annotation is copied to another set in order
 * to create a gold standard set from several annotator sets.
 *
 * You can choose to display a feature value by double clicking
 * the first column rectangles.
 */
@SuppressWarnings("serial")
public class AnnotationStackView  extends AbstractDocumentView
  implements AnnotationListener {

  public AnnotationStackView() {
    typesFeatures = new HashMap<String,String>();
    annotationMouseListener = new AnnotationMouseListener();
    headerMouseListener = new HeaderMouseListener();
  }

  @Override
  public void cleanup() {
    super.cleanup();
    textView = null;
  }

  @Override
  protected void initGUI() {

    //get a pointer to the text view used to display
    //the selected annotations
    Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
    while(textView == null && centralViewsIter.hasNext()){
      DocumentView aView = centralViewsIter.next();
      if(aView instanceof TextualDocumentView)
        textView = (TextualDocumentView) aView;
    }
    // find the annotation set view associated with the document
    Iterator<DocumentView> verticalViewsIter = owner.getVerticalViews().iterator();
    while(annotationSetsView == null && verticalViewsIter.hasNext()){
      DocumentView aView = verticalViewsIter.next();
      if(aView instanceof AnnotationSetsView)
        annotationSetsView = (AnnotationSetsView) aView;
    }
    //get a pointer to the list view
    Iterator<DocumentView> horizontalViewsIter = owner.getHorizontalViews().iterator();
    while(annotationListView == null && horizontalViewsIter.hasNext()){
      DocumentView aView = horizontalViewsIter.next();
      if(aView instanceof AnnotationListView)
        annotationListView = (AnnotationListView)aView;
    }
    annotationListView.setOwner(owner);
    document = textView.getDocument();

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // toolbar with previous and next annotation buttons
    JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.addSeparator();
    toolBar.add(previousAnnotationAction = new PreviousAnnotationAction());
    toolBar.add(nextAnnotationAction = new NextAnnotationAction());
    overlappingCheckBox = new JCheckBox("Overlapping");
    overlappingCheckBox.setToolTipText("Skip non overlapping annotations.");
    toolBar.add(overlappingCheckBox);
    toolBar.addSeparator();
    toolBar.add(targetSetLabel = new JLabel("Target set: Undefined"));
    targetSetLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        askTargetSet();
      }
    });
    targetSetLabel.setToolTipText(
      "<html>Target set to copy annotation when double clicked.<br>" +
      "Click to change it.</html>");
    mainPanel.add(toolBar, BorderLayout.NORTH);

    stackPanel = new AnnotationStack(100, 30);
    scroller = new JScrollPane(stackPanel);
    scroller.getViewport().setOpaque(true);
    mainPanel.add(scroller, BorderLayout.CENTER);

    initListeners();
  }

  @Override
  public Component getGUI(){
    return mainPanel;
  }

  protected void initListeners(){

    stackPanel.addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) {
        // when the view becomes visible
          updateStackView();
      }
      @Override
      public void ancestorMoved(AncestorEvent event) {
      }
      @Override
      public void ancestorRemoved(AncestorEvent event) {
      }
    });

    textView.getTextView().addCaretListener(new CaretListener() {
      @Override
      public void caretUpdate(CaretEvent e) {
        updateStackView();
      }
    });
  }

  class PreviousAnnotationAction extends AbstractAction {
    public PreviousAnnotationAction() {
      super("Previous boundary");
      putValue(SHORT_DESCRIPTION,
        "<html>Move to the previous annotation boundary" +
        "&nbsp;&nbsp;<font color=#667799><small>Alt-Left" +
        "&nbsp;&nbsp;</small></font></html>");
      putValue(MNEMONIC_KEY, KeyEvent.VK_LEFT);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      nextAnnotationAction.setEnabled(true);
      List<Annotation> list = new ArrayList<Annotation>();
      for(SetHandler setHandler : annotationSetsView.setHandlers) {
        for(TypeHandler typeHandler: setHandler.typeHandlers) {
          if (typeHandler.isSelected()) {
            // adds all the annotations from the selected type contained
            // between the beginning of the document and the caret position - 1
            list.addAll(setHandler.set.get(typeHandler.name).getContained(
              0l, (long)textView.getTextView().getCaretPosition()-1));
          }
        }
      }
      boolean enabled = false;
      if (list.size() > 0) {
        if (overlappingCheckBox.isSelected()) {
          Collections.sort(list, new OffsetComparator());
          boolean found = false;
          for (int i = list.size()-1; i > 0; i--) {
            if (list.get(i).overlaps(list.get(i-1))) {
              if (found) { enabled = true; break; }
              // set the caret on the previous overlapping annotation found
              textView.getTextView().setCaretPosition(
                list.get(i).getEndNode().getOffset().intValue());
              found = true;
            }
          }
        } else {
          SortedSet<Annotation> set =
            new TreeSet<Annotation>(new OffsetComparator());
          set.addAll(list); // remove same offsets annotations
          list = new ArrayList<Annotation>(set);
          // set the caret on the previous annotation found
          textView.getTextView().setCaretPosition(
            list.get(list.size()-1).getEndNode().getOffset().intValue());
          enabled = (list.size() > 1);
        }
        try {
          textView.getTextView().scrollRectToVisible(
          textView.getTextView().modelToView(
            textView.getTextView().getCaretPosition()));
        } catch (BadLocationException exception) {
          exception.printStackTrace();
        }
      }
      setEnabled(enabled);
      textView.getTextView().requestFocusInWindow();
    }
  }

  class NextAnnotationAction extends AbstractAction {
    public NextAnnotationAction() {
      super("Next boundary");
      putValue(SHORT_DESCRIPTION,
        "<html>Move to the next annotation boundary" +
        "&nbsp;&nbsp;<font color=#667799><small>Alt-Right" +
        "&nbsp;&nbsp;</small></font></html>");
      putValue(MNEMONIC_KEY, KeyEvent.VK_RIGHT);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      previousAnnotationAction.setEnabled(true);
      List<Annotation> list = new ArrayList<Annotation>();
      for(SetHandler setHandler : annotationSetsView.setHandlers) {
        for(TypeHandler typeHandler: setHandler.typeHandlers) {
          if (typeHandler.isSelected()) {
            // adds all the annotations from the selected type contained
            // between the caret position and the end of the document
            list.addAll(setHandler.set.get(typeHandler.name).getContained(
              (long)textView.getTextView().getCaretPosition(),
              document.getContent().size()-1));
          }
        }
      }
      boolean enabled = false;
      if (list.size() > 0) {
        if (overlappingCheckBox.isSelected()) {
          Collections.sort(list, new OffsetComparator());
          boolean found = false;
          for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i).overlaps(list.get(i+1))) {
              if (found) { enabled = true; break; }
              // set the caret on the next overlapping annotation found
              textView.getTextView().setCaretPosition(
                list.get(i).getEndNode().getOffset().intValue());
              found = true;
            }
          }
        } else {
          SortedSet<Annotation> set =
            new TreeSet<Annotation>(new OffsetComparator());
          set.addAll(list); // remove same offsets annotations
          list = new ArrayList<Annotation>(set);
          // set the caret on the next annotation found
          textView.getTextView().setCaretPosition(
            list.get(0).getEndNode().getOffset().intValue());
          enabled = (list.size() > 1);
        }
        try {
          textView.getTextView().scrollRectToVisible(
          textView.getTextView().modelToView(
            textView.getTextView().getCaretPosition()));
        } catch (BadLocationException exception) {
          exception.printStackTrace();
        }
      }
      setEnabled(enabled);
      textView.getTextView().requestFocusInWindow();
    }
  }

  @Override
  protected void registerHooks() { /* do nothing */ }

  @Override
  protected void unregisterHooks() { /* do nothing */ }

  @Override
  public int getType() {
    return HORIZONTAL;
  }

  @Override
  public void annotationUpdated(AnnotationEvent e) {
    updateStackView();
  }

  public void updateStackView() {
    if (textView == null) { return; }

    int caretPosition = textView.getTextView().getCaretPosition();

    // get the context around the annotation
    int context = 40;
    String text = "";
    try {
      text = document.getContent().getContent(
        Math.max(0l, caretPosition - context),
        Math.min(document.getContent().size(),
                 caretPosition + 1 + context)).toString();
    } catch (InvalidOffsetException e) {
      e.printStackTrace();
    }

    // initialise the annotation stack
    stackPanel.setText(text);
    stackPanel.setExpressionStartOffset(caretPosition);
    stackPanel.setExpressionEndOffset(caretPosition + 1);
    stackPanel.setContextBeforeSize(Math.min(caretPosition, context));
    stackPanel.setContextAfterSize(Math.min(
      document.getContent().size().intValue()-1-caretPosition, context));
    stackPanel.setAnnotationMouseListener(annotationMouseListener);
    stackPanel.setHeaderMouseListener(headerMouseListener);
    stackPanel.clearAllRows();

    // add stack rows and annotations for each selected annotation set
    // in the annotation sets view
    for(SetHandler setHandler : annotationSetsView.setHandlers) {
      for(TypeHandler typeHandler: setHandler.typeHandlers) {
        if (typeHandler.isSelected()) {
          stackPanel.addRow(setHandler.set.getName(), typeHandler.name,
            typesFeatures.get(typeHandler.name),
            null, null, AnnotationStack.CROP_MIDDLE);
          Set<Annotation> annotations = setHandler.set.get(typeHandler.name)
            .get(Math.max(0l, caretPosition - context), Math.min(
              document.getContent().size(), caretPosition + 1 + context));
          for (Annotation annotation : annotations) {
            stackPanel.addAnnotation(annotation);
          }
        }
      }
    }

    stackPanel.drawStack();
  }

  /** @return true if the user input a valid annotation set */
  boolean askTargetSet() {
    Object[] messageObjects;
    final JTextField setsTextField = new JTextField("consensus", 15);
    if (document.getAnnotationSetNames() != null
     && !document.getAnnotationSetNames().isEmpty()) {
      Collator collator = Collator.getInstance(Locale.ENGLISH);
      collator.setStrength(Collator.TERTIARY);
      SortedSet<String> setNames = new TreeSet<String>(collator);
      setNames.addAll(document.getAnnotationSetNames());
      JList<String> list = new JList<String>(setNames.toArray(new String[setNames.size()]));
      list.setVisibleRowCount(Math.min(10, setNames.size()));
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.setSelectedValue(targetSetName, true);
      JScrollPane scroll = new JScrollPane(list);
      JPanel vspace = new JPanel();
      vspace.setSize(0, 5);
      list.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          @SuppressWarnings("unchecked")
          JList<String> list = (JList<String>) e.getSource();
          if (list.getSelectedValue() != null) {
            setsTextField.setText(list.getSelectedValue());
          }
        }
      });
      messageObjects = new Object[] { "Existing annotation sets:",
        scroll, vspace, "Target set:", setsTextField };
    } else {
      messageObjects = new Object[] { "Target set:", setsTextField };
    }
    String options[] = { "Use this target set", "Cancel" };
    JOptionPane optionPane = new JOptionPane(
      messageObjects, JOptionPane.QUESTION_MESSAGE,
      JOptionPane.YES_NO_OPTION, null, options, "Cancel");
    JDialog optionDialog = optionPane.createDialog(
      owner, "Copy annotation to another set");
    setsTextField.requestFocus();
    optionDialog.setVisible(true);
    Object selectedValue = optionPane.getValue();
    if (selectedValue == null
     || selectedValue.equals("Cancel")
     || setsTextField.getText().trim().length() == 0) {
      textView.getTextView().requestFocusInWindow();
      return false;
    }
    targetSetName = setsTextField.getText();
    targetSetLabel.setText("Target set: " + targetSetName);
    textView.getTextView().requestFocusInWindow();
    return true;
  }

  class AnnotationMouseListener extends StackMouseListener {

    public AnnotationMouseListener() {
    }

    public AnnotationMouseListener(String setName, String annotationId) {
      AnnotationSet set = document.getAnnotations(setName);
      annotation = set.get(Integer.valueOf(annotationId));
      if (annotation != null) {
        // the annotation has been found by its id
        annotationData = new AnnotationDataImpl(set, annotation);
      }
    }

    @Override
    public MouseInputAdapter createListener(String... parameters) {
      switch(parameters.length) {
        case 3:
          return new AnnotationMouseListener(parameters[0], parameters[2]);
        case 5:
          return new AnnotationMouseListener(parameters[0], parameters[4]);
        default:
          return null;
      }
    }

    @Override
    public void mousePressed(MouseEvent me) {
      processMouseEvent(me);
    }
    @Override
    public void mouseReleased(MouseEvent me) {
      processMouseEvent(me);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
      processMouseEvent(me);
    }
    public void processMouseEvent(MouseEvent me) {

      if(me.isPopupTrigger()) { // context menu
        // add annotation editors to the context menu
        JPopupMenu popup = new JPopupMenu();
        List<Action> specificEditorActions =
          annotationListView.getSpecificEditorActions(
          annotationData.getAnnotationSet(), annotationData.getAnnotation());
        for (Action action : specificEditorActions) {
          popup.add(action);
        }
        List<Action> genericEditorActions =
          annotationListView.getGenericEditorActions(
            annotationData.getAnnotationSet(), annotationData.getAnnotation());
        for (Action action : genericEditorActions) {
          if (specificEditorActions.contains(action)) { continue; }
          popup.add(action);
        }
        if (specificEditorActions.size() + genericEditorActions.size() > 1) {
          popup.show(me.getComponent(), me.getX(), me.getY());
        } else { // only one choice so use it directly
          if (specificEditorActions.size() == 1) {
            specificEditorActions.get(0).actionPerformed(null);
          } else {
            genericEditorActions.get(0).actionPerformed(null);
          }
        }

      } else if (me.getID() == MouseEvent.MOUSE_CLICKED
              && me.getButton() == MouseEvent.BUTTON1
              && me.getClickCount() == 1
              && (me.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0
              && (me.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0) {
              // control + shift + click -> delete the annotation
        annotationData.getAnnotationSet().remove(annotation);

      } else if (me.getID() == MouseEvent.MOUSE_CLICKED
              && me.getButton() == MouseEvent.BUTTON1
              && me.getClickCount() == 1
              && (me.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0) {
               // control + click
        String featureDisplayed = typesFeatures.get(annotation.getType());
        Object[] features;
        if (featureDisplayed != null) {
          features = new Object[]{featureDisplayed};
        } else {
          features = annotation.getFeatures().keySet().toArray();
        }
        for (Object feature : features) {
          String value = (String) annotation.getFeatures().get(feature);
          if (value == null) { continue; }
          Pattern pattern = Pattern.compile("(https?://[^\\s,;]+)");
          Matcher matcher = pattern.matcher(value);
          if (matcher.find()) {
            // if the feature value contains an url display it in a browser
            MainFrame.getInstance().showHelpFrame(
              matcher.group(), "Annotation Stack View");
          }
        }

      } else if (me.getID() == MouseEvent.MOUSE_CLICKED
              && me.getButton() == MouseEvent.BUTTON1
              && me.getClickCount() == 2) { // double click
        if (targetSetName == null) {
          if (!askTargetSet()) { return; }
        }
        // copy the annotation to the target annotation set
        try {
          FeatureMap params = Factory.newFeatureMap();
          params.putAll(annotation.getFeatures());
          document.getAnnotations(targetSetName).add(
            annotation.getStartNode().getOffset(),
            annotation.getEndNode().getOffset(),
            annotation.getType(),
            params);
        } catch (InvalidOffsetException e) {
          e.printStackTrace();
        }
        // wait some time
        Date timeToRun = new Date(System.currentTimeMillis() + 500);
        Timer timer = new Timer("Annotation stack view select type", true);
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            SwingUtilities.invokeLater(new Runnable() { @Override
            public void run() {
              // select the new annotation and update the stack view
              annotationSetsView.setTypeSelected(targetSetName,
                annotation.getType(), true);
            }});
          }
        }, timeToRun);
      }
      textView.getTextView().requestFocusInWindow();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      dismissDelay = toolTipManager.getDismissDelay();
      initialDelay = toolTipManager.getInitialDelay();
      reshowDelay = toolTipManager.getReshowDelay();
      enabled = toolTipManager.isEnabled();
      Component component = e.getComponent();
      if (component instanceof JLabel
      && !((JLabel)component).getToolTipText().contains("Ctr-Sh-click")) {
        JLabel label = (JLabel) component;
        String toolTip = (label.getToolTipText() == null) ?
          "" : label.getToolTipText();
        toolTip = toolTip.replaceAll("</?html>", "");
        toolTip = "<html>"
          + (toolTip.length() == 0 ? "" : ("<b>" + toolTip + "</b><br>"))
          + "Double-click to copy. Right-click to edit.<br>"
          + "Ctr-click to show URL. Ctr-Sh-click to delete.</html>";
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

    ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
    int dismissDelay, initialDelay, reshowDelay;
    boolean enabled;
    Annotation annotation;
    AnnotationData annotationData;
  }

  protected class HeaderMouseListener extends StackMouseListener {

    public HeaderMouseListener() {
    }

    public HeaderMouseListener(String type, String feature) {
      this.type = type;
      this.feature = feature;
      init();
    }

    public HeaderMouseListener(String type) {
      this.type = type;
      init();
    }

    void init() {
      mainPanel.addAncestorListener(new AncestorListener() {
        @Override
        public void ancestorMoved(AncestorEvent event) {}
        @Override
        public void ancestorAdded(AncestorEvent event) {}
        @Override
        public void ancestorRemoved(AncestorEvent event) {
          // no parent so need to be disposed explicitly
          if (popupWindow != null) { popupWindow.dispose(); }
        }
      });
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

    // when double clicked shows a list of features for this annotation type
    @Override
    public void mouseClicked(MouseEvent e) {
      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.dispose();
        return;
      }
      if (e.getButton() != MouseEvent.BUTTON1
       || e.getClickCount() != 2) {
        return;
      }
      // get a list of features for the current annotation type
      TreeSet<String> features = new TreeSet<String>();
      Set<String> setNames = new HashSet<String>();
      if (document.getAnnotationSetNames() != null) {
        setNames.addAll(document.getAnnotationSetNames());
      }
      setNames.add(null); // default set name
      for (String setName : setNames) {
        int count = 0;
        for (Annotation annotation :
          document.getAnnotations(setName).get(type)) {
          for (Object feature : annotation.getFeatures().keySet()) {
            features.add((String) feature);
          }
          count++; // checks only the 50 first annotations per set
          if (count == 50) { break; } // to avoid slowing down
        }
      }
      features.add("          ");
      // create the list component
      final JList<String> list = new JList<String>(features.toArray(new String[features.size()]));
      list.setVisibleRowCount(Math.min(8, features.size()));
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.setBackground(Color.WHITE);
      list.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 1) {
            String feature = list.getSelectedValue();
            if (feature.equals("          ")) {
              typesFeatures.remove(type);
            } else {
              typesFeatures.put(type, feature);
            }
            popupWindow.setVisible(false);
            popupWindow.dispose();
            updateStackView();
            textView.getTextView().requestFocusInWindow();
          }
        }
      });
      // create the window that will contain the list
      popupWindow = new JWindow();
      popupWindow.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
        Math.min(8*component.getHeight(),
          features.size()*component.getHeight()));
      popupWindow.pack();
      popupWindow.setVisible(true);
      SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        String newFeature = typesFeatures.get(type);
        if (newFeature == null) { newFeature = "          "; }
        list.setSelectedValue(newFeature, true);
        popupWindow.requestFocusInWindow();
      }});
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      Component component = e.getComponent();
      if (component instanceof JLabel
      && ((JLabel)component).getToolTipText() == null) {
        ((JLabel)component).setToolTipText("Double click to choose a feature.");
      }
    }

    String type;
    String feature;
    JWindow popupWindow;
  }

  // external objects
  TextualDocumentView textView;
  AnnotationSetsView annotationSetsView;
  AnnotationListView annotationListView;

  // user interface elements
  JLabel targetSetLabel;
  String targetSetName;
  JCheckBox overlappingCheckBox;
  AnnotationStack stackPanel;
  JScrollPane scroller;
  JPanel mainPanel;

  // actions
  PreviousAnnotationAction previousAnnotationAction;
  NextAnnotationAction nextAnnotationAction;

  // local objects
  /** optionally map a type to a feature when the feature value
   *  must be displayed in the rectangle annotation */
  Map<String,String> typesFeatures;
  AnnotationMouseListener annotationMouseListener;
  HeaderMouseListener headerMouseListener;
}
