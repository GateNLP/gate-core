/*
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * AnnotationEditor.java
 * 
 * Valentin Tablan, Apr 5, 2004
 * 
 * $Id: AnnotationEditor.java 17901 2014-04-24 12:59:58Z markagreenwood $
 */
package gate.gui.docview;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Gate;
import gate.LanguageResource;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.AnnotationSchema;
import gate.creole.ResourceInstantiationException;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.gui.FeaturesSchemaEditor;
import gate.gui.MainFrame;
import gate.gui.annedit.AnnotationDataImpl;
import gate.gui.annedit.AnnotationEditorOwner;
import gate.gui.annedit.OwnedAnnotationEditor;
import gate.gui.annedit.SearchAndAnnotatePanel;
import gate.util.GateException;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

/**
 * A generic annotation editor, which uses the known annotation schemas to help
 * speed up the annotation process (e.g. by pre-populating sets of choices) but
 * does not enforce the schemas, allowing the user full control.
 */
@SuppressWarnings("serial")
public class AnnotationEditor extends AbstractVisualResource implements
                                                            OwnedAnnotationEditor {
  /*
   * (non-Javadoc)
   * 
   * @see gate.creole.AbstractVisualResource#init()
   */
  @Override
  public Resource init() throws ResourceInstantiationException {
    super.init();
    initData();
    initGUI();
    initListeners();
    annotationEditorInstance = this;
    return this;
  }

  protected void initData() {
    schemasByType = new HashMap<String, AnnotationSchema>();
    java.util.List<LanguageResource> schemas =
        Gate.getCreoleRegister().getLrInstances("gate.creole.AnnotationSchema");
    for(Iterator<LanguageResource> schIter = schemas.iterator(); schIter.hasNext();) {
      AnnotationSchema aSchema = (AnnotationSchema)schIter.next();
      schemasByType.put(aSchema.getAnnotationName(), aSchema);
    }
    CreoleListener creoleListener = new CreoleListener() {
      @Override
      public void resourceLoaded(CreoleEvent e) {
        Resource newResource = e.getResource();
        if(newResource instanceof AnnotationSchema) {
          AnnotationSchema aSchema = (AnnotationSchema)newResource;
          schemasByType.put(aSchema.getAnnotationName(), aSchema);
        }
      }

      @Override
      public void resourceUnloaded(CreoleEvent e) {
        Resource newResource = e.getResource();
        if(newResource instanceof AnnotationSchema) {
          AnnotationSchema aSchema = (AnnotationSchema)newResource;
          if(schemasByType.containsValue(aSchema)) {
            schemasByType.remove(aSchema.getAnnotationName());
          }
        }
      }

      @Override
      public void datastoreOpened(CreoleEvent e) {
      }

      @Override
      public void datastoreCreated(CreoleEvent e) {
      }

      @Override
      public void datastoreClosed(CreoleEvent e) {
      }

      @Override
      public void resourceRenamed(Resource resource, String oldName,
          String newName) {
      }
    };
    Gate.getCreoleRegister().addCreoleListener(creoleListener);
  }

  protected void initGUI() {
    popupWindow =
        new JWindow(SwingUtilities.getWindowAncestor(owner.getTextComponent())) {
          @Override
          public void pack() {
            // increase the feature table size only if not bigger
            // than the main frame
            if(isVisible()) {
              int maxHeight = MainFrame.getInstance().getHeight();
              int otherHeight = getHeight() - featuresScroller.getHeight();
              maxHeight -= otherHeight;
              if(featuresScroller.getPreferredSize().height > maxHeight) {
                featuresScroller.setMaximumSize(new Dimension(featuresScroller
                    .getMaximumSize().width, maxHeight));
                featuresScroller.setPreferredSize(new Dimension(
                    featuresScroller.getPreferredSize().width, maxHeight));
              }
            }
            super.pack();
          }

          @Override
          public void setVisible(boolean b) {
            super.setVisible(b);
            // when the editor is shown put the focus in the type combo box
            if(b) {
              typeCombo.requestFocus();
            }
          }
        };
    JPanel pane = new JPanel();
    pane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    pane.setLayout(new GridBagLayout());
    pane.setBackground(UIManager.getLookAndFeelDefaults().getColor(
        "ToolTip.background"));
    popupWindow.setContentPane(pane);
    Insets insets0 = new Insets(0, 0, 0, 0);
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.NONE;
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.gridwidth = 1;
    constraints.gridy = 0;
    constraints.gridx = GridBagConstraints.RELATIVE;
    constraints.weightx = 0;
    constraints.weighty = 0;
    constraints.insets = insets0;
    solButton = new JButton();
    solButton.setContentAreaFilled(false);
    solButton.setBorderPainted(false);
    solButton.setMargin(insets0);
    pane.add(solButton, constraints);
    sorButton = new JButton();
    sorButton.setContentAreaFilled(false);
    sorButton.setBorderPainted(false);
    sorButton.setMargin(insets0);
    pane.add(sorButton, constraints);
    delButton = new JButton();
    delButton.setContentAreaFilled(false);
    delButton.setBorderPainted(false);
    delButton.setMargin(insets0);
    constraints.insets = new Insets(0, 20, 0, 20);
    pane.add(delButton, constraints);
    constraints.insets = insets0;
    eolButton = new JButton();
    eolButton.setContentAreaFilled(false);
    eolButton.setBorderPainted(false);
    eolButton.setMargin(insets0);
    pane.add(eolButton, constraints);
    eorButton = new JButton();
    eorButton.setContentAreaFilled(false);
    eorButton.setBorderPainted(false);
    eorButton.setMargin(insets0);
    pane.add(eorButton, constraints);
    pinnedButton = new JToggleButton(MainFrame.getIcon("pin"));
    pinnedButton.setSelectedIcon(MainFrame.getIcon("pin-in"));
    pinnedButton.setSelected(false);
    pinnedButton.setBorderPainted(false);
    pinnedButton.setContentAreaFilled(false);
    constraints.weightx = 1;
    constraints.insets = new Insets(0, 0, 0, 0);
    constraints.anchor = GridBagConstraints.EAST;
    pane.add(pinnedButton, constraints);
    dismissButton = new JButton();
    dismissButton.setBorder(null);
    constraints.anchor = GridBagConstraints.NORTHEAST;
    pane.add(dismissButton, constraints);
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.insets = insets0;
    typeCombo = new JComboBox<String>();
    typeCombo.setEditable(true);
    typeCombo.setBackground(UIManager.getLookAndFeelDefaults().getColor(
        "ToolTip.background"));
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridy = 1;
    constraints.gridwidth = 7;
    constraints.weightx = 1;
    constraints.insets = new Insets(3, 2, 2, 2);
    pane.add(typeCombo, constraints);
    featuresEditor = new FeaturesSchemaEditor();
    featuresEditor.setBackground(UIManager.getLookAndFeelDefaults().getColor(
        "ToolTip.background"));
    try {
      featuresEditor.init();
    } catch(ResourceInstantiationException rie) {
      throw new GateRuntimeException(rie);
    }
    constraints.gridy = 2;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    featuresScroller = new JScrollPane(featuresEditor);
    pane.add(featuresScroller, constraints);
    // add the search and annotate GUI at the bottom of the annotator editor
    SearchAndAnnotatePanel searchPanel =
        new SearchAndAnnotatePanel(pane.getBackground(), this, popupWindow);
    constraints.insets = new Insets(0, 0, 0, 0);
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridx = 0;
    constraints.gridy = GridBagConstraints.RELATIVE;
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.gridheight = GridBagConstraints.REMAINDER;
    constraints.weightx = 0.0;
    constraints.weighty = 0.0;
    pane.add(searchPanel, constraints);
    popupWindow.pack();
  }

  protected void initListeners() {
    // resize the window when the table changes.
    featuresEditor.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // the table has changed size -> resize the window too!
        popupWindow.pack();
      }
    });
    KeyAdapter keyAdapter = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        hideTimer.stop();
      }
    };
    typeCombo.getEditor().getEditorComponent().addKeyListener(keyAdapter);
    MouseListener windowMouseListener = new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        hideTimer.stop();
      }

      // allow a JWindow to be dragged with a mouse
      @Override
      public void mousePressed(MouseEvent me) {
        pressed = me;
      }
    };
    MouseMotionListener windowMouseMotionListener = new MouseMotionAdapter() {
      Point location;

      // allow a JWindow to be dragged with a mouse
      @Override
      public void mouseDragged(MouseEvent me) {
        location = popupWindow.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();
        popupWindow.setLocation(x, y);
        pinnedButton.setSelected(true);
      }
    };
    popupWindow.getRootPane().addMouseListener(windowMouseListener);
    popupWindow.getRootPane().addMouseMotionListener(windowMouseMotionListener);
    InputMap inputMap =
        ((JComponent)popupWindow.getContentPane())
            .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    actionMap = ((JComponent)popupWindow.getContentPane()).getActionMap();
    // add the key-action bindings of this Component to the parent window
    solAction =
        new StartOffsetLeftAction("", MainFrame.getIcon("extend-left"),
            SOL_DESC, KeyEvent.VK_LEFT);
    solButton.setAction(solAction);
    setShortCuts(inputMap, SOL_KEY_STROKES, "solAction");
    actionMap.put("solAction", solAction);
    sorAction =
        new StartOffsetRightAction("", MainFrame.getIcon("extend-right"),
            SOR_DESC, KeyEvent.VK_RIGHT);
    sorButton.setAction(sorAction);
    setShortCuts(inputMap, SOR_KEY_STROKES, "sorAction");
    actionMap.put("sorAction", sorAction);
    delAction =
        new DeleteAnnotationAction("", MainFrame.getIcon("remove-annotation"),
            "Delete the annotation", KeyEvent.VK_DELETE);
    delButton.setAction(delAction);
    inputMap.put(KeyStroke.getKeyStroke("alt DELETE"), "delAction");
    actionMap.put("delAction", delAction);
    eolAction =
        new EndOffsetLeftAction("", MainFrame.getIcon("extend-left"), EOL_DESC,
            KeyEvent.VK_LEFT);
    eolButton.setAction(eolAction);
    setShortCuts(inputMap, EOL_KEY_STROKES, "eolAction");
    actionMap.put("eolAction", eolAction);
    eorAction =
        new EndOffsetRightAction("", MainFrame.getIcon("extend-right"),
            EOR_DESC, KeyEvent.VK_RIGHT);
    eorButton.setAction(eorAction);
    setShortCuts(inputMap, EOR_KEY_STROKES, "eorAction");
    actionMap.put("eorAction", eorAction);
    pinnedButton.setToolTipText("<html>Press to pin window in place"
        + "&nbsp;&nbsp;<font color=#667799><small>Ctrl-P"
        + "&nbsp;&nbsp;</small></font></html>");
    inputMap.put(KeyStroke.getKeyStroke("control P"), "toggle pin");
    actionMap.put("toggle pin", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pinnedButton.doClick();
      }
    });
    DismissAction dismissAction =
        new DismissAction("", null, "Close the window", KeyEvent.VK_ESCAPE);
    dismissButton.setAction(dismissAction);
    inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "dismissAction");
    inputMap.put(KeyStroke.getKeyStroke("alt ESCAPE"), "dismissAction");
    actionMap.put("dismissAction", dismissAction);
    ApplyAction applyAction =
        new ApplyAction("Apply", null, "", KeyEvent.VK_ENTER);
    inputMap.put(KeyStroke.getKeyStroke("alt ENTER"), "applyAction");
    actionMap.put("applyAction", applyAction);
    typeCombo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        String newType = typeCombo.getSelectedItem().toString();
        if(ann == null || ann.getType().equals(newType)) return;
        // annotation editing
        Integer oldId = ann.getId();
        Annotation oldAnn = ann;
        set.remove(ann);
        try {
          set.add(oldId, oldAnn.getStartNode().getOffset(), oldAnn.getEndNode()
              .getOffset(), newType, oldAnn.getFeatures());
          Annotation newAnn = set.get(oldId);
          // update the selection to the new annotation
          getOwner().selectAnnotation(new AnnotationDataImpl(set, newAnn));
          editAnnotation(newAnn, set);
          owner.annotationChanged(newAnn, set, oldAnn.getType());
        } catch(InvalidOffsetException ioe) {
          throw new GateRuntimeException(ioe);
        }
      }
    });
    hideTimer = new Timer(HIDE_DELAY, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        annotationEditorInstance.setVisible(false);
      }
    });
    hideTimer.setRepeats(false);
    AncestorListener textAncestorListener = new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) {
        if(wasShowing) {
          annotationEditorInstance.setVisible(true);
        }
        wasShowing = false;
      }

      @Override
      public void ancestorRemoved(AncestorEvent event) {
        if(isShowing()) {
          wasShowing = true;
          popupWindow.dispose();
        }
      }

      @Override
      public void ancestorMoved(AncestorEvent event) {
      }

      private boolean wasShowing = false;
    };
    owner.getTextComponent().addAncestorListener(textAncestorListener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.gui.annedit.AnnotationEditor#isActive()
   */
  @Override
  public boolean isActive() {
    return popupWindow.isVisible();
  }

  @Override
  public void editAnnotation(Annotation ann, AnnotationSet set) {
    this.ann = ann;
    this.set = set;
    if(ann == null) {
      typeCombo.setModel(new DefaultComboBoxModel<String>());
      featuresEditor.setSchema(new AnnotationSchema());
      // popupWindow.doLayout();
      popupWindow.validate();
      return;
    }
    // repopulate the types combo
    String annType = ann.getType();
    Set<String> types = new HashSet<String>(schemasByType.keySet());
    types.add(annType);
    types.addAll(set.getAllTypes());
    java.util.List<String> typeList = new ArrayList<String>(types);
    Collections.sort(typeList);
    typeCombo.setModel(new DefaultComboBoxModel<String>(typeList.toArray(new String[typeList.size()])));
    typeCombo.setSelectedItem(annType);
    featuresEditor.setSchema(schemasByType.get(annType));
    featuresEditor.setTargetFeatures(ann.getFeatures());
    setEditingEnabled(true);
    popupWindow.pack();
    setVisible(true);
    if(!pinnedButton.isSelected()) {
      hideTimer.restart();
    }
  }

  @Override
  public Annotation getAnnotationCurrentlyEdited() {
    return ann;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.gui.annedit.AnnotationEditor#editingFinished()
   */
  @Override
  public boolean editingFinished() {
    // this editor implementation has no special requirements (such as schema
    // compliance), so it always returns true.
    return true;
  }

  @Override
  public boolean isShowing() {
    return popupWindow.isShowing();
  }

  /**
   * Shows/Hides the UI(s) involved in annotation editing.
   */
  @Override
  public void setVisible(boolean setVisible) {
    super.setVisible(setVisible);
    if(setVisible) {
      placeDialog(ann.getStartNode().getOffset().intValue(), ann.getEndNode()
          .getOffset().intValue());
    } else {
      popupWindow.setVisible(false);
      pinnedButton.setSelected(false);
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          // when hiding the editor put back the focus in the document
          owner.getTextComponent().requestFocus();
        }
      });
    }
  }

  /**
   * Finds the best location for the editor dialog for a given span of text.
   */
  @Override
  public void placeDialog(int start, int end) {
    if(popupWindow.isVisible() && pinnedButton.isSelected()) {
      // just resize
      Point where = popupWindow.getLocation();
      popupWindow.pack();
      if(where != null) {
        popupWindow.setLocation(where);
      }
    } else {
      // calculate position
      try {
        Rectangle startRect = owner.getTextComponent().modelToView(start);
        Rectangle endRect = owner.getTextComponent().modelToView(end);
        Point topLeft = owner.getTextComponent().getLocationOnScreen();
        int x = topLeft.x + startRect.x;
        int y = topLeft.y + endRect.y + endRect.height;
        // make sure the window doesn't start lower
        // than the end of the visible rectangle
        Rectangle visRect = owner.getTextComponent().getVisibleRect();
        int maxY = topLeft.y + visRect.y + visRect.height;
        // make sure window doesn't get off-screen
        popupWindow.pack();
        // responding to changed orientation
        if(currentOrientation == ComponentOrientation.RIGHT_TO_LEFT) {
          x = x - popupWindow.getSize().width;
          if(x < 0) x = 0;
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boolean revalidate = false;
        if(popupWindow.getSize().width > screenSize.width) {
          popupWindow.setSize(screenSize.width, popupWindow.getSize().height);
          revalidate = true;
        }
        if(popupWindow.getSize().height > screenSize.height) {
          popupWindow.setSize(popupWindow.getSize().width, screenSize.height);
          revalidate = true;
        }
        if(revalidate) popupWindow.validate();
        // calculate max X
        int maxX = screenSize.width - popupWindow.getSize().width;
        // calculate max Y
        if(maxY + popupWindow.getSize().height > screenSize.height) {
          maxY = screenSize.height - popupWindow.getSize().height;
        }
        // correct position
        if(y > maxY) y = maxY;
        if(x > maxX) x = maxX;
        popupWindow.setLocation(x, y);
      } catch(BadLocationException ble) {
        // this should never occur
        throw new GateRuntimeException(ble);
      }
    }
    if(!popupWindow.isVisible()) popupWindow.setVisible(true);
  }

  /**
   * Changes the span of an existing annotation by creating a new annotation
   * with the same ID, type and features but with the new start and end offsets.
   * 
   * @param set
   *          the annotation set
   * @param oldAnnotation
   *          the annotation to be moved
   * @param newStartOffset
   *          the new start offset
   * @param newEndOffset
   *          the new end offset
   */
  protected void moveAnnotation(AnnotationSet set, Annotation oldAnnotation,
      Long newStartOffset, Long newEndOffset) throws InvalidOffsetException {
    // Moving is done by deleting the old annotation and creating a new one.
    // If this was the last one of one type it would mess up the gui which
    // "forgets" about this type and then it recreates it (with a different
    // colour and not visible.
    // In order to avoid this problem, we'll create a new temporary annotation.
    Annotation tempAnn = null;
    if(set.get(oldAnnotation.getType()).size() == 1) {
      // create a clone of the annotation that will be deleted, to act as a
      // placeholder
      Integer tempAnnId =
          set.add(oldAnnotation.getStartNode(), oldAnnotation.getStartNode(),
              oldAnnotation.getType(), oldAnnotation.getFeatures());
      tempAnn = set.get(tempAnnId);
    }
    Integer oldID = oldAnnotation.getId();
    set.remove(oldAnnotation);
    set.add(oldID, newStartOffset, newEndOffset, oldAnnotation.getType(),
        oldAnnotation.getFeatures());
    Annotation newAnn = set.get(oldID);
    // update the selection to the new annotation
    getOwner().selectAnnotation(new AnnotationDataImpl(set, newAnn));
    editAnnotation(newAnn, set);
    // remove the temporary annotation
    if(tempAnn != null) set.remove(tempAnn);
    owner.annotationChanged(newAnn, set, null);
  }

  /**
   * Base class for actions on annotations.
   */
  protected abstract class AnnotationAction extends AbstractAction {
    public AnnotationAction(String text, Icon icon, String desc, int mnemonic) {
      super(text, icon);
      putValue(SHORT_DESCRIPTION, desc);
      putValue(MNEMONIC_KEY, mnemonic);
    }
  }

  protected class StartOffsetLeftAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public StartOffsetLeftAction(String text, Icon icon, String desc,
        int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      int increment = 1;
      if((evt.getModifiers() & ActionEvent.SHIFT_MASK) > 0) {
        // CTRL pressed -> use tokens for advancing
        increment = SHIFT_INCREMENT;
        if((evt.getModifiers() & ActionEvent.CTRL_MASK) > 0) {
          increment = CTRL_SHIFT_INCREMENT;
        }
      }
      long newValue = ann.getStartNode().getOffset().longValue() - increment;
      if(newValue < 0) newValue = 0;
      try {
        moveAnnotation(set, ann, new Long(newValue), ann.getEndNode()
            .getOffset());
      } catch(InvalidOffsetException ioe) {
        throw new GateRuntimeException(ioe);
      }
    }
  }

  protected class StartOffsetRightAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public StartOffsetRightAction(String text, Icon icon, String desc,
        int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      long endOffset = ann.getEndNode().getOffset().longValue();
      int increment = 1;
      if((evt.getModifiers() & ActionEvent.SHIFT_MASK) > 0) {
        // CTRL pressed -> use tokens for advancing
        increment = SHIFT_INCREMENT;
        if((evt.getModifiers() & ActionEvent.CTRL_MASK) > 0) {
          increment = CTRL_SHIFT_INCREMENT;
        }
      }
      long newValue = ann.getStartNode().getOffset().longValue() + increment;
      if(newValue > endOffset) newValue = endOffset;
      try {
        moveAnnotation(set, ann, new Long(newValue), ann.getEndNode()
            .getOffset());
      } catch(InvalidOffsetException ioe) {
        throw new GateRuntimeException(ioe);
      }
    }
  }

  protected class EndOffsetLeftAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public EndOffsetLeftAction(String text, Icon icon, String desc, int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      long startOffset = ann.getStartNode().getOffset().longValue();
      int increment = 1;
      if((evt.getModifiers() & ActionEvent.SHIFT_MASK) > 0) {
        // CTRL pressed -> use tokens for advancing
        increment = SHIFT_INCREMENT;
        if((evt.getModifiers() & ActionEvent.CTRL_MASK) > 0) {
          increment = CTRL_SHIFT_INCREMENT;
        }
      }
      long newValue = ann.getEndNode().getOffset().longValue() - increment;
      if(newValue < startOffset) newValue = startOffset;
      try {
        moveAnnotation(set, ann, ann.getStartNode().getOffset(), new Long(
            newValue));
      } catch(InvalidOffsetException ioe) {
        throw new GateRuntimeException(ioe);
      }
    }
  }

  protected class EndOffsetRightAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public EndOffsetRightAction(String text, Icon icon, String desc,
        int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      long maxOffset = owner.getDocument().getContent().size().longValue();
      int increment = 1;
      if((evt.getModifiers() & ActionEvent.SHIFT_MASK) > 0) {
        // CTRL pressed -> use tokens for advancing
        increment = SHIFT_INCREMENT;
        if((evt.getModifiers() & ActionEvent.CTRL_MASK) > 0) {
          increment = CTRL_SHIFT_INCREMENT;
        }
      }
      long newValue = ann.getEndNode().getOffset().longValue() + increment;
      if(newValue > maxOffset) newValue = maxOffset;
      try {
        moveAnnotation(set, ann, ann.getStartNode().getOffset(), new Long(
            newValue));
      } catch(InvalidOffsetException ioe) {
        throw new GateRuntimeException(ioe);
      }
    }
  }

  protected class DeleteAnnotationAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public DeleteAnnotationAction(String text, Icon icon, String desc,
        int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      set.remove(ann);
      // clear the dialog
      editAnnotation(null, set);
      if(!pinnedButton.isSelected()) {
        // if not pinned, hide the dialog.
        annotationEditorInstance.setVisible(false);
      } else {
        setEditingEnabled(false);
      }
    }
  }

  protected class DismissAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public DismissAction(String text, Icon icon, String desc, int mnemonic) {
      super(text, icon, desc, mnemonic);
      Icon exitIcon = UIManager.getIcon("InternalFrame.closeIcon");
      if(exitIcon == null) exitIcon = MainFrame.getIcon("exit");
      putValue(SMALL_ICON, exitIcon);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      annotationEditorInstance.setVisible(false);
    }
  }

  protected class ApplyAction extends AnnotationAction {
    private static final long serialVersionUID = 1L;

    public ApplyAction(String text, Icon icon, String desc, int mnemonic) {
      super(text, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      annotationEditorInstance.setVisible(false);
    }
  }

  /**
   * The popup window used by the editor.
   */
  protected JWindow popupWindow;

  /**
   * Toggle button used to pin down the dialog.
   */
  protected JToggleButton pinnedButton;

  /**
   * Combobox for annotation type.
   */
  protected JComboBox<String> typeCombo;

  /**
   * Component for features editing.
   */
  protected FeaturesSchemaEditor featuresEditor;

  protected JScrollPane featuresScroller;

  protected JButton solButton;

  protected JButton sorButton;

  protected JButton delButton;

  protected JButton eolButton;

  protected JButton eorButton;

  protected JButton dismissButton;

  protected Timer hideTimer;

  protected MouseEvent pressed;

  /**
   * Constant for delay before hiding the popup window (in milliseconds).
   */
  protected static final int HIDE_DELAY = 3000;

  /**
   * Constant for the number of characters when changing annotation boundary
   * with Shift key pressed.
   */
  protected static final int SHIFT_INCREMENT = 5;

  /**
   * Constant for the number of characters when changing annotation boundary
   * with Ctrl+Shift keys pressed.
   */
  protected static final int CTRL_SHIFT_INCREMENT = 10;

  /**
   * Stores the Annotation schema objects available in the system. The
   * annotation types are used as keys for the map.
   */
  protected Map<String, AnnotationSchema> schemasByType;

  /**
   * The controlling object for this editor.
   */
  private AnnotationEditorOwner owner;

  /**
   * The annotation being edited.
   */
  protected Annotation ann;

  /**
   * The parent set of the current annotation.
   */
  protected AnnotationSet set;

  /**
   * Current instance of this class.
   */
  protected AnnotationEditor annotationEditorInstance;

  /**
   * Action bindings for the popup window.
   */
  private ActionMap actionMap;

  private StartOffsetLeftAction solAction;

  private StartOffsetRightAction sorAction;

  private DeleteAnnotationAction delAction;

  private EndOffsetLeftAction eolAction;

  private EndOffsetRightAction eorAction;

  /*
   * (non-Javadoc)
   * 
   * @see gate.gui.annedit.AnnotationEditor#getAnnotationSetCurrentlyEdited()
   */
  @Override
  public AnnotationSet getAnnotationSetCurrentlyEdited() {
    return set;
  }

  /**
   * @return the owner
   */
  @Override
  public AnnotationEditorOwner getOwner() {
    return owner;
  }

  /**
   * @param owner
   *          the owner to set
   */
  @Override
  public void setOwner(AnnotationEditorOwner owner) {
    this.owner = owner;
  }

  @Override
  public void setPinnedMode(boolean pinned) {
    pinnedButton.setSelected(pinned);
  }

  @Override
  public void setEditingEnabled(boolean isEditingEnabled) {
    solButton.setEnabled(isEditingEnabled);
    sorButton.setEnabled(isEditingEnabled);
    delButton.setEnabled(isEditingEnabled);
    eolButton.setEnabled(isEditingEnabled);
    eorButton.setEnabled(isEditingEnabled);
    typeCombo.setEnabled(isEditingEnabled);
    // cancel editing, if any
    if(featuresEditor.isEditing()) {
      featuresEditor.getColumnModel()
          .getColumn(featuresEditor.getEditingColumn()).getCellEditor()
          .cancelCellEditing();
    }
    // en/disable the featuresEditor table, no easy way unfortunately : |
    featuresEditor.setEnabled(isEditingEnabled);
    if(isEditingEnabled) {
      // avoid the background to be incorrectly reset to the default color
      Color tableBG = featuresEditor.getBackground();
      tableBG = new Color(tableBG.getRGB());
      featuresEditor.setBackground(tableBG);
    }
    final boolean isEditingEnabledF = isEditingEnabled;
    for(int col = 0; col < featuresEditor.getColumnCount(); col++) {
      final TableCellRenderer previousTcr =
          featuresEditor.getColumnModel().getColumn(col).getCellRenderer();
      TableCellRenderer tcr = new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
          Component c =
              previousTcr.getTableCellRendererComponent(table, value,
                  isSelected, hasFocus, row, column);
          c.setEnabled(isEditingEnabledF);
          return c;
        }
      };
      featuresEditor.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }
    // enable/disable the key binding actions
    if(isEditingEnabled) {
      actionMap.put("solAction", solAction);
      actionMap.put("sorAction", sorAction);
      actionMap.put("delAction", delAction);
      actionMap.put("eolAction", eolAction);
      actionMap.put("eorAction", eorAction);
    } else {
      actionMap.put("solAction", null);
      actionMap.put("sorAction", null);
      actionMap.put("delAction", null);
      actionMap.put("eolAction", null);
      actionMap.put("eorAction", null);
    }
    // change the orientation
    changeOrientation(currentOrientation);
  }

  /**
   * Does nothing, as this editor does not support cancelling and rollbacks.
   */
  @Override
  public void cancelAction() throws GateException {
  }

  /**
   * Returns <tt>true</tt> always as this editor is generic and can edit any
   * annotation type.
   */
  @Override
  public boolean canDisplayAnnotationType(String annotationType) {
    return true;
  }

  /**
   * Does nothing as this editor works in auto-commit mode (changes are
   * implemented immediately).
   */
  @Override
  public void okAction() throws GateException {
  }

  /**
   * Returns <tt>false</tt>, as this editor does not support cancel operations.
   */
  @Override
  public boolean supportsCancel() {
    return false;
  }

  @Override
  public void changeOrientation(ComponentOrientation orientation) {
    if(orientation == null) return;
    // remember the current orientation
    this.currentOrientation = orientation;
    // input map
    InputMap inputMap =
        ((JComponent)popupWindow.getContentPane())
            .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    Action solAction = actionMap.get("solAction");
    Action sorAction = actionMap.get("sorAction");
    Action eolAction = actionMap.get("eolAction");
    Action eorAction = actionMap.get("eorAction");
    if(orientation == ComponentOrientation.RIGHT_TO_LEFT) {
      // in right to left orientation
      // extending start offset is equal to extending end offset
      solButton.setAction(eorAction);
      solButton.setToolTipText(EOR_DESC);
      setShortCuts(inputMap, SOL_KEY_STROKES, "eorAction");
      solButton.setIcon(MainFrame.getIcon("extend-left"));
      // shrinking start offset is equal to shrinking end offset
      sorButton.setAction(eolAction);
      sorButton.setToolTipText(EOL_DESC);
      setShortCuts(inputMap, SOR_KEY_STROKES, "eolAction");
      sorButton.setIcon(MainFrame.getIcon("extend-right"));
      // shrinking end offset is equal to shrinking start offset
      eolButton.setAction(sorAction);
      eolButton.setToolTipText(SOR_DESC);
      setShortCuts(inputMap, EOL_KEY_STROKES, "sorAction");
      eolButton.setIcon(MainFrame.getIcon("extend-left"));
      // extending end offset is extending start offset
      eorButton.setAction(solAction);
      eorButton.setToolTipText(SOL_DESC);
      setShortCuts(inputMap, EOR_KEY_STROKES, "solAction");
      eorButton.setIcon(MainFrame.getIcon("extend-right"));
    } else {
      solButton.setAction(solAction);
      solButton.setToolTipText(SOL_DESC);
      setShortCuts(inputMap, SOL_KEY_STROKES, "solAction");
      solButton.setIcon(MainFrame.getIcon("extend-left"));
      sorButton.setAction(sorAction);
      sorButton.setToolTipText(SOR_DESC);
      setShortCuts(inputMap, SOR_KEY_STROKES, "sorAction");
      sorButton.setIcon(MainFrame.getIcon("extend-right"));
      eolButton.setAction(eolAction);
      eolButton.setToolTipText(EOL_DESC);
      setShortCuts(inputMap, EOL_KEY_STROKES, "eolAction");
      eolButton.setIcon(MainFrame.getIcon("extend-left"));
      eorButton.setAction(eorAction);
      eorButton.setToolTipText(EOR_DESC);
      setShortCuts(inputMap, EOR_KEY_STROKES, "eorAction");
      eorButton.setIcon(MainFrame.getIcon("extend-right"));
    }
  }

  private void setShortCuts(InputMap inputMap, String[] keyStrokes,
      String action) {
    for(String aKeyStroke : keyStrokes) {
      inputMap.put(KeyStroke.getKeyStroke(aKeyStroke), action);
    }
  }

  /**
   * current orientation set by the user
   */
  private ComponentOrientation currentOrientation = null;

  /* various tool tips for the changing offsets buttons */
  private final String SOL_DESC = "<html><b>Extend start</b><small>"
      + "<br>LEFT = 1 character" + "<br> + SHIFT = 5 characters, "
      + "<br> + CTRL + SHIFT = 10 characters</small></html>";

  private final String SOR_DESC = "<html><b>Shrink start</b><small>"
      + "<br>RIGHT = 1 character" + "<br> + SHIFT = 5 characters, "
      + "<br> + CTRL + SHIFT = 10 characters</small></html>";

  private final String EOL_DESC = "<html><b>Shrink end</b><small>"
      + "<br>ALT + LEFT = 1 character" + "<br> + SHIFT = 5 characters, "
      + "<br> + CTRL + SHIFT = 10 characters</small></html>";

  private final String EOR_DESC = "<html><b>Extend end</b><small>"
      + "<br>ALT + RIGHT = 1 character" + "<br> + SHIFT = 5 characters, "
      + "<br> + CTRL + SHIFT = 10 characters</small></html>";

  /* various short cuts we define */
  private final String[] SOL_KEY_STROKES = new String[]{"LEFT", "shift LEFT",
      "control shift released LEFT"};

  private final String[] SOR_KEY_STROKES = new String[]{"RIGHT", "shift RIGHT",
      "control shift released RIGHT"};

  private final String[] EOL_KEY_STROKES = new String[]{"LEFT", "alt LEFT",
      "control alt released LEFT"};

  private final String[] EOR_KEY_STROKES = new String[]{"RIGHT", "alt RIGHT",
      "control alt released RIGHT"};
}
