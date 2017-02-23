/*
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Valentin Tablan, 22 March 2004
 * 
 * $Id: DocumentEditor.java 19840 2016-12-01 23:02:14Z markagreenwood $
 */
package gate.gui.docview;

import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.GateConstants;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.gui.ActionsPublisher;
import gate.gui.MainFrame;
import gate.gui.annedit.AnnotationData;
import gate.gui.annedit.SearchExpressionsAction;
import gate.swing.JMenuButton;
import gate.util.GateRuntimeException;
import gate.util.OptionsMap;
import gate.util.Strings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * This is the GATE Document viewer/editor. This class is only the shell of the
 * main document VR, which gets populated with views (objects that implement the
 * {@link DocumentView} interface. Contains a search dialog and an option menu
 * button.
 */
@SuppressWarnings("serial")
@CreoleResource(name = "Document Editor", guiType = GuiType.LARGE, resourceDisplayed = "gate.Document", mainViewer = true)
public class DocumentEditor extends AbstractVisualResource implements
                                                          ActionsPublisher {
  /**
   * Save the layout of the views and selected annotations.
   */
  public void saveSettings() {
    DocumentEditor de = DocumentEditor.this;
    Gate.getUserConfig().put(
        DocumentEditor.class.getName() + ".centralViewIdx",
        Strings.toString(de.centralViewIdx));
    Gate.getUserConfig().put(DocumentEditor.class.getName() + ".rightViewIdx",
        Strings.toString(de.rightViewIdx));
    Gate.getUserConfig().put(DocumentEditor.class.getName() + ".bottomViewIdx",
        Strings.toString(de.bottomViewIdx));
    Gate.getUserConfig().put(DocumentEditor.class.getName() + ".topViewIdx",
        Strings.toString(de.topViewIdx));
    LinkedHashSet<String> setTypeSet = new LinkedHashSet<String>();
    DocumentView dv = de.getRightView();
    if(dv instanceof AnnotationSetsView) {
      AnnotationSetsView av = (AnnotationSetsView)dv;
      for(AnnotationSetsView.SetHandler sh : av.setHandlers) {
        for(AnnotationSetsView.TypeHandler th : sh.typeHandlers) {
          if(th.isSelected()) {
            setTypeSet.add((sh.set.getName() == null ? "" : sh.set.getName())
                + '.' + th.name);
          }
        }
      }
    }
    Gate.getUserConfig().put(DocumentEditor.class.getName() + ".setTypeSet",
        Strings.toString(setTypeSet));
    DocumentView bottomView = de.getBottomView();
    if(bottomView instanceof AnnotationStackView) {
      AnnotationStackView view = (AnnotationStackView)bottomView;
      Gate.getUserConfig().put(
          DocumentEditor.class.getName() + ".stackTypesFeatures",
          Strings.toString(view.typesFeatures));
      Gate.getUserConfig().put(
          DocumentEditor.class.getName() + ".stackTargetSetName",
          view.targetSetName);
    }
  }

  /**
   * Restore the layout of the views and selected annotations.
   */
  public void restoreSettings() {
    final DocumentEditor de = DocumentEditor.this;
    Integer value =
        Gate.getUserConfig().getInt(
            DocumentEditor.class.getName() + ".centralViewIdx");
    int centralViewIdx = (value == null) ? 0 : value;
    value =
        Gate.getUserConfig().getInt(
            DocumentEditor.class.getName() + ".rightViewIdx");
    int rightViewIdx = (value == null) ? -1 : value;
    value =
        Gate.getUserConfig().getInt(
            DocumentEditor.class.getName() + ".bottomViewIdx");
    int bottomViewIdx = (value == null) ? -1 : value;
    value =
        Gate.getUserConfig().getInt(
            DocumentEditor.class.getName() + ".topViewIdx");
    int topViewIdx = (value == null) ? -1 : value;
    if(de.centralViewIdx != centralViewIdx
        && centralViews.size() > centralViewIdx) {
      de.setCentralView(centralViewIdx);
    }
    if(de.rightViewIdx != rightViewIdx && verticalViews.size() > rightViewIdx) {
      de.setRightView(rightViewIdx);
    }
    if(de.bottomViewIdx != bottomViewIdx
        && horizontalViews.size() > centralViewIdx) {
      de.setBottomView(bottomViewIdx);
    }
    if(de.topViewIdx != topViewIdx && horizontalViews.size() > centralViewIdx) {
      de.setTopView(topViewIdx);
    }
    DocumentView dv = de.getRightView();
    if(dv instanceof AnnotationSetsView) {
      Set<String> setTypeSet =
          Gate.getUserConfig().getSet(
              DocumentEditor.class.getName() + ".setTypeSet");
      AnnotationSetsView av = (AnnotationSetsView)dv;
      for(AnnotationSetsView.SetHandler sh : av.setHandlers) {
        for(AnnotationSetsView.TypeHandler th : sh.typeHandlers) {
          th.setSelected(false);
          for(String setType : setTypeSet) {
            String[] values = setType.split("\\.");
            if(values.length != 2) {
              continue;
            }
            String setName = values[0].equals("") ? null : values[0];
            String typeName = values[1];
            if(av.getSetHandler(setName) != null
                && av.getSetHandler(setName).getTypeHandler(typeName) != null) {
              av.setTypeSelected(setName, typeName, true);
            }
          }
        }
      }
    }
    DocumentView bottomView = de.getBottomView();
    if(bottomView instanceof AnnotationStackView) {
      AnnotationStackView view = (AnnotationStackView)bottomView;
      view.typesFeatures =
          Gate.getUserConfig().getMap(
              DocumentEditor.class.getName() + ".stackTypesFeatures");
      view.targetSetName =
          Gate.getUserConfig().getString(
              DocumentEditor.class.getName() + ".stackTargetSetName");
      view.targetSetLabel.setText("Target set: " + view.targetSetName);
    }
  }

  /**
   * The document view is just an empty shell. This method publishes the actions
   * from the contained views.
   */
  @Override
  public List<Action> getActions() {
    List<Action> actions = new ArrayList<Action>();
    Iterator<DocumentView> viewIter;
    if(getCentralViews() != null) {
      viewIter = getCentralViews().iterator();
      while(viewIter.hasNext()) {
        actions.addAll(viewIter.next().getActions());
      }
    }
    if(getHorizontalViews() != null) {
      viewIter = getHorizontalViews().iterator();
      while(viewIter.hasNext()) {
        actions.addAll(viewIter.next().getActions());
      }
    }
    if(getVerticalViews() != null) {
      viewIter = getVerticalViews().iterator();
      while(viewIter.hasNext()) {
        actions.addAll(viewIter.next().getActions());
      }
    }
    return actions;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.Resource#init()
   */
  @Override
  public Resource init() throws ResourceInstantiationException {
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentHidden(ComponentEvent e) {
      }

      @Override
      public void componentMoved(ComponentEvent e) {
      }

      @Override
      public void componentResized(ComponentEvent e) {
        if(!viewsInited) initViews();
      }

      // lazily build the GUI only when needed
      @Override
      public void componentShown(ComponentEvent e) {
        if(!viewsInited) initViews();
      }
    });
    return this;
  }

  @Override
  public void cleanup() {
    Iterator<DocumentView> viewsIter;
    if(centralViews != null) {
      viewsIter = centralViews.iterator();
      while(viewsIter.hasNext())
        viewsIter.next().cleanup();
      centralViews.clear();
    }
    if(horizontalViews != null) {
      viewsIter = horizontalViews.iterator();
      while(viewsIter.hasNext())
        viewsIter.next().cleanup();
      horizontalViews.clear();
    }
    if(verticalViews != null) {
      viewsIter = verticalViews.iterator();
      while(viewsIter.hasNext())
        viewsIter.next().cleanup();
      verticalViews.clear();
    }
  }

  protected void initViews() {
    viewsInited = true;
    // start building the UI
    setLayout(new BorderLayout());
    JProgressBar progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, progressBar
        .getPreferredSize().height));
    add(progressBar, BorderLayout.CENTER);
    progressBar.setString("Building views");
    progressBar.setValue(10);
    // create the skeleton UI
    topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, null, null);
    topSplit.setResizeWeight(0.3);
    topSplit.setContinuousLayout(true);
    topSplit.setOneTouchExpandable(true);
    bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, null);
    bottomSplit.setResizeWeight(0.7);
    bottomSplit.setContinuousLayout(true);
    bottomSplit.setOneTouchExpandable(true);
    horizontalSplit =
        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bottomSplit, null);
    horizontalSplit.setResizeWeight(0.7);
    horizontalSplit.setContinuousLayout(true);
    horizontalSplit.setOneTouchExpandable(true);
    // create the bars
    topBar = new JToolBar(JToolBar.HORIZONTAL);
    topBar.setFloatable(false);
    add(topBar, BorderLayout.NORTH);
    progressBar.setValue(40);
    centralViews = new ArrayList<DocumentView>();
    verticalViews = new ArrayList<DocumentView>();
    horizontalViews = new ArrayList<DocumentView>();
    // parse all Creole resources and look for document views
    Set<String> vrSet = Gate.getCreoleRegister().getVrTypes();
    List<ResourceData> viewTypes = new ArrayList<ResourceData>();
    for(String vr : vrSet) {
      ResourceData rData = Gate.getCreoleRegister().get(vr);
      try {
        if(DocumentView.class.isAssignableFrom(rData.getResourceClass())) {
          viewTypes.add(rData);
        }
      } catch(ClassNotFoundException cnfe) {
        cnfe.printStackTrace();
      }
    }
    // sort view types by label
    Collections.sort(viewTypes, new Comparator<ResourceData>() {
      @Override
      public int compare(ResourceData rd1, ResourceData rd2) {
        return rd1.getName().compareTo(rd2.getName());
      }
    });
    for(ResourceData viewType : viewTypes) {
      try {
        // create the resource
        DocumentView aView =
            (DocumentView)Factory.createResource(viewType.getClassName());
        aView.setTarget(document);
        aView.setOwner(this);
        // add the view
        addView(aView, viewType.getName());
      } catch(ResourceInstantiationException rie) {
        rie.printStackTrace();
      }
    }
    // select the main central view only
    if(centralViews.size() > 0) setCentralView(0);
    // populate the main VIEW
    remove(progressBar);
    add(horizontalSplit, BorderLayout.CENTER);
    searchAction = new SearchAction();
    JButton searchButton = new JButton(searchAction);
    searchButton.setMargin(new Insets(0, 0, 0, 0));
    topBar.add(Box.createHorizontalStrut(5));
    topBar.add(searchButton);
    // add a key binding for the search function
    getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke("control F"), "Search in text");
    getActionMap().put("Search in text", searchAction);
    // create menu that contains several options for the document editor
    final OptionsMap config = Gate.getUserConfig();
    final JPopupMenu optionsMenu = new JPopupMenu("Options menu");
    final JMenuItem saveCurrentLayoutMenuItem =
        new JMenuItem(new AbstractAction("Save Current Layout") {
          @Override
          public void actionPerformed(ActionEvent evt) {
            saveSettings();
          }
        });
    optionsMenu.add(saveCurrentLayoutMenuItem);
    final JCheckBoxMenuItem restoreLayoutAutomaticallyMenuItem =
        new JCheckBoxMenuItem("Restore Layout Automatically");
    restoreLayoutAutomaticallyMenuItem.setSelected(Gate.getUserConfig()
        .getBoolean(
            DocumentEditor.class.getName() + ".restoreLayoutAutomatically"));
    restoreLayoutAutomaticallyMenuItem.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        // whenever the user checks/unchecks, update the config
        config.put(DocumentEditor.class.getName()
            + ".restoreLayoutAutomatically",
            restoreLayoutAutomaticallyMenuItem.isSelected());
      }
    });
    optionsMenu.add(restoreLayoutAutomaticallyMenuItem);
    final JCheckBoxMenuItem readOnly = new JCheckBoxMenuItem("Read-only");
    readOnly.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        config.put(GateConstants.DOCEDIT_READ_ONLY, readOnly.isSelected());
        setEditable(!readOnly.isSelected());
      }
    });
    readOnly.setSelected(config.getBoolean(GateConstants.DOCEDIT_READ_ONLY));
    optionsMenu.addSeparator();
    optionsMenu.add(readOnly);
    // right to left orientation
    final JCheckBoxMenuItem rightToLeftOrientation =
        new JCheckBoxMenuItem("Right To Left Orientation");
    rightToLeftOrientation.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        config.put(GateConstants.DOC_RTOL_ORIENTATION,
            rightToLeftOrientation.isSelected());
        setRightToLeftOrientation(rightToLeftOrientation.isSelected());
      }
    });
    optionsMenu.addSeparator();
    optionsMenu.add(rightToLeftOrientation);
    ButtonGroup buttonGroup = new ButtonGroup();
    final JRadioButtonMenuItem insertAppend =
        new JRadioButtonMenuItem("Insert Append");
    buttonGroup.add(insertAppend);
    insertAppend.setSelected(config
        .getBoolean(GateConstants.DOCEDIT_INSERT_APPEND));
    insertAppend.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        config.put(GateConstants.DOCEDIT_INSERT_APPEND,
            insertAppend.isSelected());
      }
    });
    optionsMenu.addSeparator();
    optionsMenu.add(insertAppend);
    final JRadioButtonMenuItem insertPrepend =
        new JRadioButtonMenuItem("Insert Prepend");
    buttonGroup.add(insertPrepend);
    insertPrepend.setSelected(config
        .getBoolean(GateConstants.DOCEDIT_INSERT_PREPEND));
    insertPrepend.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        config.put(GateConstants.DOCEDIT_INSERT_PREPEND,
            insertPrepend.isSelected());
      }
    });
    optionsMenu.add(insertPrepend);
    // if none set then set the default one
    if(!(insertAppend.isSelected() || insertPrepend.isSelected())) {
      insertAppend.setSelected(true);
    }
    JMenuButton menuButton = new JMenuButton(optionsMenu);
    menuButton.setIcon(MainFrame.getIcon("expanded"));
    menuButton.setToolTipText("Options for the document editor");
    menuButton.setMargin(new Insets(0, 0, 0, 1)); // icon is not centred
    topBar.add(Box.createHorizontalGlue());
    topBar.add(menuButton);
    // when the editor is shown restore views if layout saving is enable
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if(Gate.getUserConfig().getBoolean(
            DocumentEditor.class.getName() + ".restoreLayoutAutomatically")) {
          restoreSettings();
        }
      }
    });
    validate();
  }

  public List<DocumentView> getCentralViews() {
    return centralViews == null ? null : Collections
        .unmodifiableList(centralViews);
  }

  public List<DocumentView> getHorizontalViews() {
    return horizontalViews == null ? null : Collections
        .unmodifiableList(horizontalViews);
  }

  public List<DocumentView> getVerticalViews() {
    return verticalViews == null ? null : Collections
        .unmodifiableList(verticalViews);
  }

  /**
   * Registers a new view by adding it to the right list and creating the
   * activation button for it.
   * 
   * @param view
   *          view to add to the GUI as a button
   * @param name
   *          name of the view used in the GUI as a button name
   */
  protected void addView(DocumentView view, String name) {
    topBar.add(Box.createHorizontalStrut(5));
    final ViewButton viewButton = new ViewButton(view, name);
    switch(view.getType()){
      case DocumentView.CENTRAL:
        centralViews.add(view);
        // leftBar.add(new ViewButton(view, name));
        topBar.add(viewButton);
        break;
      case DocumentView.VERTICAL:
        verticalViews.add(view);
        // rightBar.add(new ViewButton(view, name));
        topBar.add(viewButton);
        break;
      case DocumentView.HORIZONTAL:
        horizontalViews.add(view);
        topBar.add(viewButton);
        // bottomBar.add(new ViewButton(view, name));
        break;
      default:
        throw new GateRuntimeException(getClass().getName()
            + ": Invalid view type");
    }
    // binds a F-key to each view toggle button
    // avoid the F-Key F1,2,6,8,10 because already used
    if((fKeyNumber == 5) || (fKeyNumber == 7) || (fKeyNumber == 9)) {
      fKeyNumber++;
    }
    getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke("F" + (fKeyNumber + 1)),
        "Shows view " + fKeyNumber + 1);
    getActionMap().put("Shows view " + fKeyNumber + 1, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        viewButton.doClick();
      }
    });
    // add a tooltip with the key shortcut
    if(view instanceof AnnotationSetsView) {
      getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
          KeyStroke.getKeyStroke("shift F" + (fKeyNumber + 1)),
          "Shows view " + fKeyNumber + 1);
      viewButton.setToolTipText("<html>Toggle the view of " + name
          + "&nbsp;&nbsp;<font color=#667799><small>F" + (fKeyNumber + 1)
          + "&nbsp;&nbsp;</small></font>"
          + "<br>Set last selected annotations "
          + "&nbsp;&nbsp;<font color=#667799><small>Shift+F" + (fKeyNumber + 1)
          + "&nbsp;&nbsp;</small></font></html>");
    } else {
      viewButton.setToolTipText("<html>Toggle the view of " + name
          + "&nbsp;&nbsp;<font color=#667799><small>F" + (fKeyNumber + 1)
          + "&nbsp;&nbsp;</small></font></html>");
    }
    fKeyNumber++;
  }

  /**
   * Gets the currently showing top view
   * 
   * @return a {@link DocumentView} object.
   */
  protected DocumentView getTopView() {
    return (topViewIdx == -1) ? null : horizontalViews.get(topViewIdx);
  }

  /**
   * Shows a new top view based on an index in the {@link #horizontalViews}
   * list.
   * 
   * @param index
   *          the index in {@link #horizontalViews} list for the new view to be
   *          shown.
   */
  public void setTopView(int index) {
    // deactivate current view
    DocumentView oldView = getTopView();
    if(oldView != null) {
      oldView.setActive(false);
    }
    topViewIdx = index;
    if(topViewIdx == -1)
      setTopView(null);
    else {
      DocumentView newView = horizontalViews.get(topViewIdx);
      // hide if shown at the bottom
      if(bottomViewIdx == topViewIdx) {
        setBottomView(null);
        bottomViewIdx = -1;
      }
      // activate if necessary
      if(!newView.isActive()) {
        newView.setActive(true);
      }
      // show the new view
      setTopView(newView);
    }
  }

  /**
   * Sets a new UI component in the top location. This method is intended to
   * only be called from {@link #setTopView(int)}.
   * 
   * @param view
   *          the new view to be shown.
   */
  protected void setTopView(DocumentView view) {
    topSplit.setTopComponent(view == null ? null : view.getGUI());
    topSplit.resetToPreferredSizes();
    updateBar(topBar);
    validate();
  }

  /**
   * Gets the currently showing central view
   * 
   * @return a {@link DocumentView} object.
   */
  protected DocumentView getCentralView() {
    return (centralViewIdx == -1) ? null : centralViews.get(centralViewIdx);
  }

  /**
   * Shows a new central view based on an index in the {@link #centralViews}
   * list.
   * 
   * @param index
   *          the index in {@link #centralViews} list for the new view to be
   *          shown.
   */
  public void setCentralView(int index) {
    // deactivate current view
    DocumentView oldView = getCentralView();
    if(oldView != null) {
      oldView.setActive(false);
    }
    centralViewIdx = index;
    if(centralViewIdx == -1)
      setCentralView(null);
    else {
      DocumentView newView = centralViews.get(centralViewIdx);
      // activate if necessary
      if(!newView.isActive()) {
        newView.setActive(true);
      }
      // show the new view
      setCentralView(newView);
    }
  }

  /**
   * Sets a new UI component in the central location. This method is intended to
   * only be called from {@link #setCentralView(int)}.
   * 
   * @param view
   *          the new view to be shown.
   */
  protected void setCentralView(DocumentView view) {
    topSplit.setBottomComponent(view == null ? null : view.getGUI());
    topSplit.resetToPreferredSizes();
    // updateBar(leftBar);
    updateBar(topBar);
    validate();
  }

  /**
   * Gets the currently showing bottom view
   * 
   * @return a {@link DocumentView} object.
   */
  protected DocumentView getBottomView() {
    return (bottomViewIdx == -1) ? null : horizontalViews.get(bottomViewIdx);
  }

  /**
   * Shows a new bottom view based on an index in the {@link #horizontalViews}
   * list.
   * 
   * @param index
   *          the index in {@link #horizontalViews} list for the new view to be
   *          shown.
   */
  public void setBottomView(int index) {
    // deactivate current view
    DocumentView oldView = getBottomView();
    if(oldView != null) {
      oldView.setActive(false);
    }
    bottomViewIdx = index;
    if(bottomViewIdx == -1) {
      setBottomView(null);
    } else {
      DocumentView newView = horizontalViews.get(bottomViewIdx);
      // hide if shown at the top
      if(topViewIdx == bottomViewIdx) {
        setTopView(null);
        topViewIdx = -1;
      }
      // activate if necessary
      if(!newView.isActive()) {
        newView.setActive(true);
      }
      // show the new view
      setBottomView(newView);
    }
  }

  /**
   * Sets a new UI component in the top location. This method is intended to
   * only be called from {@link #setBottomView(int)}.
   * 
   * @param view
   *          the new view to be shown.
   */
  protected void setBottomView(DocumentView view) {
    bottomSplit.setBottomComponent(view == null ? null : view.getGUI());
    bottomSplit.resetToPreferredSizes();
    // updateBar(bottomBar);
    updateBar(topBar);
    validate();
  }

  /**
   * Gets the currently showing right view
   * 
   * @return a {@link DocumentView} object.
   */
  protected DocumentView getRightView() {
    return (rightViewIdx == -1) ? null : verticalViews.get(rightViewIdx);
  }

  /**
   * Shows a new right view based on an index in the {@link #verticalViews}
   * list.
   * 
   * @param index
   *          the index in {@link #verticalViews} list for the new view to be
   *          shown.
   */
  public void setRightView(int index) {
    // deactivate current view
    DocumentView oldView = getRightView();
    if(oldView != null) {
      oldView.setActive(false);
    }
    rightViewIdx = index;
    if(rightViewIdx == -1)
      setRightView(null);
    else {
      DocumentView newView = verticalViews.get(rightViewIdx);
      // activate if necessary
      if(!newView.isActive()) {
        newView.setActive(true);
      }
      // show the new view
      setRightView(newView);
    }
  }

  /**
   * Sets a new UI component in the right hand side location. This method is
   * intended to only be called from {@link #setRightView(int)}.
   * 
   * @param view
   *          the new view to be shown.
   */
  protected void setRightView(DocumentView view) {
    horizontalSplit.setRightComponent(view == null ? null : view.getGUI());
    // updateBar(rightBar);
    updateBar(topBar);
    validate();
  }

  /**
   * Change the set of selected annotations. This new value will be sent to all
   * active constituent views.
   * 
   * @param selectedAnnots
   *          list of AnnotationData to select
   */
  public void setSelectedAnnotations(List<AnnotationData> selectedAnnots) {
    selectedAnnotations.clear();
    selectedAnnotations.addAll(selectedAnnots);
    // notify all active views
    for(DocumentView aView : centralViews) {
      if(aView.isActive()) aView.setSelectedAnnotations(selectedAnnotations);
    }
    for(DocumentView aView : horizontalViews) {
      if(aView.isActive()) aView.setSelectedAnnotations(selectedAnnotations);
    }
    for(DocumentView aView : verticalViews) {
      if(aView.isActive()) aView.setSelectedAnnotations(selectedAnnotations);
    }
  }

  /**
   * Gets the current set of selected annotations.
   * 
   * @return set of selected annotations
   */
  public List<AnnotationData> getSelectedAnnotations() {
    return selectedAnnotations;
  }

  /**
   * TODO: to remove? doesn't seems to be used anywhere.
   */
  protected void updateSplitLocation(JSplitPane split, int foo) {
    Component left = split.getLeftComponent();
    Component right = split.getRightComponent();
    if(left == null) {
      split.setDividerLocation(0);
      return;
    }
    if(right == null) {
      split.setDividerLocation(1);
      return;
    }
    Dimension leftPS = left.getPreferredSize();
    Dimension rightPS = right.getPreferredSize();
    double location =
        split.getOrientation() == JSplitPane.HORIZONTAL_SPLIT
            ? (double)leftPS.width / (leftPS.width + rightPS.width)
            : (double)leftPS.height / (leftPS.height + rightPS.height);
    split.setDividerLocation(location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gate.VisualResource#setTarget(java.lang.Object)
   */
  @Override
  public void setTarget(Object target) {
    this.document = (Document)target;
  }

  /**
   * Updates the selected state of the buttons on one of the toolbars.
   * 
   * @param toolbar
   *          toolbar to update
   */
  protected void updateBar(JToolBar toolbar) {
    Component btns[] = toolbar.getComponents();
    if(btns != null) {
      for(Component btn : btns) {
        if(btn instanceof ViewButton) ((ViewButton)btn).updateSelected();
      }
    }
  }

  /**
   * @return the text component associated with this document editor.
   */
  protected JTextComponent getTextComponent() {
    return (JTextComponent)(((JScrollPane)getCentralView().getGUI())
        .getViewport()).getView();
  }

  /**
   * Set the document as editable or readonly. Documents are editable by
   * default.
   * 
   * @param editable
   *          true if editable, false if readonly
   */
  public void setEditable(boolean editable) {
    getTextComponent().setEditable(editable);
  }

  /**
   * Set the text orientation in the document.
   * 
   * @param set
   *          If true, text is displayed from right to left.
   */
  public void setRightToLeftOrientation(boolean set) {
    // which orientation?
    ComponentOrientation orientation =
        set
            ? ComponentOrientation.RIGHT_TO_LEFT
            : ComponentOrientation.LEFT_TO_RIGHT;
    // interested only in the text document view
    for(DocumentView aView : centralViews) {
      if(aView instanceof TextualDocumentView) {
        ((TextualDocumentView)aView).changeOrientation(orientation);
        break;
      }
    }
    // here interested only in the annotation sets view
    for(DocumentView aView : verticalViews) {
      if(aView instanceof AnnotationSetsView) {
        ((AnnotationSetsView)aView).changeOrientation(orientation);
        break;
      }
    }
  }

  /**
   * Dialog to search an expression in the document. Select the current match in
   * the document. Features: incremental search, case insensitive, whole word,
   * highlighted annotations, regular expression.
   */
  protected class SearchAction extends AbstractAction {
    public SearchAction() {
      super();
      putValue(SHORT_DESCRIPTION, "<html>Search within the document."
          + "&nbsp;&nbsp;<font color=#667799><small>Ctrl-F"
          + "&nbsp;&nbsp;</small></font></html>");
      putValue(SMALL_ICON, MainFrame.getIcon("search"));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
      if(searchDialog == null) {
        Window parent = SwingUtilities.getWindowAncestor(DocumentEditor.this);
        searchDialog =
            (parent instanceof Dialog)
                ? new SearchDialog((Dialog)parent)
                : new SearchDialog((Frame)parent);
        searchDialog.pack();
        searchDialog.setLocationRelativeTo(DocumentEditor.this);
        searchDialog.setResizable(true);
        MainFrame.getGuiRoots().add(searchDialog);
      }
      JTextComponent textPane = getTextComponent();
      // if the user never gives the focus to the textPane then
      // there will never be any selection in it so we force it
      textPane.requestFocusInWindow();
      // put the selection of the document into the search text field
      if(textPane.getSelectedText() != null) {
        searchDialog.patternTextField.setText(textPane.getSelectedText());
      }
      if(searchDialog.isVisible()) {
        searchDialog.toFront();
      } else {
        searchDialog.setVisible(true);
      }
      searchDialog.patternTextField.selectAll();
      searchDialog.patternTextField.requestFocusInWindow();
    }
  }

  protected class SearchDialog extends JDialog {
    SearchDialog(Frame owner) {
      super(owner, false);
      setTitle("Search in \"" + document.getName() + "\"");
      initLocalData();
      initGuiComponents();
      initListeners();
    }

    SearchDialog(Dialog owner) {
      super(owner, false);
      setTitle("Search in \"" + document.getName() + "\"");
      initLocalData();
      initGuiComponents();
      initListeners();
    }

    protected void initLocalData() {
      pattern = null;
      nextMatchStartsFrom = 0;
      findFirstAction = new AbstractAction("Find first") {
        {
          putValue(SHORT_DESCRIPTION, "Finds first match");
          putValue(MNEMONIC_KEY, KeyEvent.VK_F);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
          String content = document.getContent().toString();
          refresh();
          if(!isValidRegularExpression()) return;
          boolean found = false;
          int start = -1;
          int end = -1;
          nextMatchStartsFrom = 0;
          Matcher matcher = pattern.matcher(content);
          while(matcher.find(nextMatchStartsFrom) && !found) {
            start = matcher.start();
            end = matcher.end();
            found = false;
            if(highlightsChk.isSelected()) {
              javax.swing.text.Highlighter.Highlight[] highlights =
                  textPane.getHighlighter().getHighlights();
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
            setTitle("Found: \""
                + content.substring(Math.max(0, start - 13), start).replaceAll(
                    "\\s+", " ")
                + "["
                + content.substring(start, end).replaceAll("\\s+", " ")
                + "]"
                + content.substring(end, Math.min(content.length(), end + 13))
                    .replaceAll("\\s+", " ") + "\"");
            // select the match in the document
            textPane.setCaretPosition(start);
            textPane.moveCaretPosition(end);
          } else {
            setTitle("Expression not found at all in the document.");
            findFirstAction.setEnabled(false);
            findNextAction.setEnabled(false);
          }
          patternTextField.requestFocusInWindow();
        }
      };
      findNextAction = new AbstractAction("Find next") {
        {
          putValue(SHORT_DESCRIPTION, "Finds next match");
          putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
          String content = document.getContent().toString();
          refresh();
          if(!isValidRegularExpression()) return;
          boolean found = false;
          int start = -1;
          int end = -1;
          if(evt == null) {
            // incremental search
            nextMatchStartsFrom = textPane.getSelectionStart();
          } else {
            nextMatchStartsFrom = textPane.getCaretPosition();
          }
          Matcher matcher = pattern.matcher(content);
          while(matcher.find(nextMatchStartsFrom) && !found) {
            start = matcher.start();
            end = matcher.end();
            found = false;
            if(highlightsChk.isSelected()) {
              javax.swing.text.Highlighter.Highlight[] highlights =
                  textPane.getHighlighter().getHighlights();
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
            setTitle("Found: \""
                + content.substring(Math.max(0, start - 13), start).replaceAll(
                    "\\s+", " ")
                + "["
                + content.substring(start, end).replaceAll("\\s+", " ")
                + "]"
                + content.substring(end, Math.min(content.length(), end + 13))
                    .replaceAll("\\s+", " ") + "\"");
            // select the match in the document
            textPane.setCaretPosition(start);
            textPane.moveCaretPosition(end);
          } else {
            setTitle("Expression not found after the document caret.");
            findNextAction.setEnabled(false);
          }
          patternTextField.requestFocusInWindow();
        }
      };
      cancelAction = new AbstractAction("Cancel") {
        {
          putValue(SHORT_DESCRIPTION, "Cancel");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
          searchDialog.setVisible(false);
        }
      };
    }

    protected void initGuiComponents() {
      getContentPane().setLayout(
          new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
      getContentPane().add(Box.createVerticalStrut(5));
      Box hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(new JLabel("Find:"));
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(patternTextField = new JTextField(20));
      hBox.add(Box.createHorizontalStrut(3));
      JButton helpRegExpButton = new JButton("?");
      helpRegExpButton.setMargin(new Insets(0, 2, 0, 2));
      helpRegExpButton.setToolTipText("GATE search expression builder.");
      hBox.add(helpRegExpButton);
      hBox.add(Box.createHorizontalGlue());
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);
      getContentPane().add(Box.createVerticalStrut(5));
      hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(ignoreCaseChk = new JCheckBox("Ignore case", true));
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(wholeWordsChk = new JCheckBox("Whole word", false));
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(regularExpressionChk = new JCheckBox("Regular Exp.", false));
      regularExpressionChk.setToolTipText("Regular expression search.");
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(highlightsChk = new JCheckBox("Highlights", false));
      highlightsChk
          .setToolTipText("Restrict the search on the highlighted annotations.");
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);
      getContentPane().add(Box.createVerticalStrut(5));
      hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalGlue());
      JButton findFirstButton = new JButton(findFirstAction);
      hBox.add(findFirstButton);
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(new JButton(findNextAction));
      hBox.add(Box.createHorizontalStrut(6));
      hBox.add(new JButton(cancelAction));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);
      getContentPane().add(Box.createVerticalStrut(5));
      getRootPane().setDefaultButton(findFirstButton);
      helpRegExpButton.addActionListener(new SearchExpressionsAction(
          patternTextField, this, regularExpressionChk));
    }

    protected void initListeners() {
      addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent e) {
          refresh();
        }
      });
      // incremental search
      patternTextField.getDocument().addDocumentListener(
          new javax.swing.event.DocumentListener() {
            private Timer timer = new Timer("Document Editor search timer",
                true);

            private TimerTask timerTask;

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
              update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
              update();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
              refresh();
            }

            private void update() {
              if(timerTask != null) {
                timerTask.cancel();
              }
              refresh();
              Date timeToRun = new Date(System.currentTimeMillis() + 250);
              timerTask = new TimerTask() {
                @Override
                public void run() {
                  findNextAction.actionPerformed(null);
                }
              };
              // add a delay
              timer.schedule(timerTask, timeToRun);
            }
          });
      wholeWordsChk.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          refresh();
        }
      });
      ignoreCaseChk.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          refresh();
        }
      });
      regularExpressionChk.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          refresh();
        }
      });
      highlightsChk.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          refresh();
        }
      });
      ((JComponent)getContentPane()).getInputMap(
          JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
          KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
      ((JComponent)getContentPane()).getActionMap().put("cancelAction",
          cancelAction);
    }

    /**
     * Builds and validates the regular expression before use.
     * 
     * @return true if the regular expression is valid, false otherwise.
     */
    protected boolean isValidRegularExpression() {
      String patternText = patternTextField.getText();
      boolean valid = true;
      // update patternRE
      try {
        String prefixPattern = wholeWordsChk.isSelected() ? "\\b" : "";
        prefixPattern += regularExpressionChk.isSelected() ? "" : "\\Q";
        String suffixPattern = regularExpressionChk.isSelected() ? "" : "\\E";
        suffixPattern += wholeWordsChk.isSelected() ? "\\b" : "";
        patternText = prefixPattern + patternText + suffixPattern;
        pattern =
            ignoreCaseChk.isSelected() ? Pattern.compile(patternText,
                Pattern.CASE_INSENSITIVE) : Pattern.compile(patternText);
      } catch(PatternSyntaxException e) {
        setTitle(e.getMessage().replaceFirst("(?s) near index .+$", "."));
        int index = e.getMessage().indexOf(" near index ");
        if(index != -1) {
          index += " near index ".length();
          //TODO does this work when the positon in the regex isn't a single digit
          patternTextField.setCaretPosition(Integer.parseInt(e.getMessage()
              .substring(index, index + 1)));
        }
        patternTextField.requestFocusInWindow();
        valid = false;
      }
      return valid;
    }

    protected void refresh() {
      String patternText = patternTextField.getText();
      if(patternText != null && patternText.length() > 0) {
        // update actions state
        findFirstAction.setEnabled(true);
        findNextAction.setEnabled(true);
      } else {
        findFirstAction.setEnabled(false);
        findNextAction.setEnabled(false);
      }
    }

    JTextComponent textPane = getTextComponent();

    JTextField patternTextField;

    JCheckBox ignoreCaseChk;

    JCheckBox wholeWordsChk;

    JCheckBox regularExpressionChk;

    JCheckBox highlightsChk;

    Pattern pattern;

    int nextMatchStartsFrom;

    Action findFirstAction;

    Action findNextAction;

    Action cancelAction;
  } // end of class SearchDialog

  protected class ViewButton extends JToggleButton {
    public ViewButton(DocumentView aView, String name) {
      super();
      setSelected(false);
      // setBorder(null);
      this.view = aView;
      setText(name);
      // if(aView.getType() == DocumentView.HORIZONTAL){
      // setText(name);
      // }else if(aView.getType() == DocumentView.CENTRAL){
      // setIcon(new VerticalTextIcon(this, name,
      // VerticalTextIcon.ROTATE_LEFT));
      // }else if(aView.getType() == DocumentView.VERTICAL){
      // setIcon(new VerticalTextIcon(this, name,
      // VerticalTextIcon.ROTATE_RIGHT));
      // }
      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
          if(isSelected()) {
            // show this new view
            switch(view.getType()){
              case DocumentView.CENTRAL:
                setCentralView(centralViews.indexOf(view));
                break;
              case DocumentView.VERTICAL:
                setRightView(verticalViews.indexOf(view));
                break;
              case DocumentView.HORIZONTAL:
                // if(ViewButton.this.getParent() == topBar){
                // setTopView(horizontalViews.indexOf(view));
                // }else{
                setBottomView(horizontalViews.indexOf(view));
                // }
                break;
            }
            // the view is an annotation sets view
            if(view instanceof AnnotationSetsView
                && annotationSetsViewFirstTime) {
              annotationSetsViewFirstTime = false;
              AnnotationSetsView asv = (AnnotationSetsView)view;
              // shift key was pressed
              if(evt.getModifiers() == ActionEvent.SHIFT_MASK
                  || (evt.getModifiers() == ActionEvent.SHIFT_MASK
                      + ActionEvent.MOUSE_EVENT_MASK)) {
                asv.restoreSavedSelectedTypes();
              } else {
                // expand default set
                asv.getSetHandler(null).setExpanded(true);
                if(document.getAnnotationSetNames() != null) {
                  for(Object setName : document.getAnnotationSetNames()) {
                    if(!setName.equals("Original markups")) {
                      // expand other annotation sets
                      asv.getSetHandler((String)setName).setExpanded(true);
                    }
                  }
                }
              }
              // remove the tooltip for the shift key
              setToolTipText(getToolTipText().replaceFirst("<br>.*</html>$",
                  "</html>"));
            }
          } else {
            // hide this view
            switch(view.getType()){
              case DocumentView.CENTRAL:
                setCentralView(-1);
                break;
              case DocumentView.VERTICAL:
                setRightView(-1);
                break;
              case DocumentView.HORIZONTAL:
                // if(ViewButton.this.getParent() == topBar){
                // setTopView(-1);
                // }else{
                setBottomView(-1);
                // }
                break;
            }
          }
          if(view instanceof TextualDocumentView) {
            // enable/disable according to text visibility
            searchAction.setEnabled(isSelected());
          }
        }
      });
    }

    public void updateSelected() {
      switch(view.getType()){
        case DocumentView.CENTRAL:
          setSelected(getCentralView() == view);
          break;
        case DocumentView.VERTICAL:
          setSelected(getRightView() == view);
          break;
        case DocumentView.HORIZONTAL:
          // if(ViewButton.this.getParent() == topBar){
          // setSelected(getTopView() == view);
          // }else{
          setSelected(getBottomView() == view);
          // }
          break;
      }
    }

    DocumentView view;

    boolean annotationSetsViewFirstTime = true;
  }

  protected JSplitPane horizontalSplit;

  protected JSplitPane topSplit;

  protected JSplitPane bottomSplit;

  /** The dialog used for text search */
  private SearchDialog searchDialog;

  protected Action searchAction;

  /**
   * Cached value for the selected annotations.
   */
  private List<AnnotationData> selectedAnnotations =
      new ArrayList<AnnotationData>();

  protected JToolBar topBar;

  // protected JToolBar rightBar;
  // protected JToolBar leftBar;
  // protected JToolBar bottomBar;
  protected Document document;

  /**
   * A list of {@link DocumentView} objects of type {@link DocumentView#CENTRAL}
   */
  protected List<DocumentView> centralViews;

  /**
   * A list of {@link DocumentView} objects of type
   * {@link DocumentView#VERTICAL}
   */
  protected List<DocumentView> verticalViews;

  /**
   * A list of {@link DocumentView} objects of type
   * {@link DocumentView#HORIZONTAL}
   */
  protected List<DocumentView> horizontalViews;

  /**
   * The index in {@link #centralViews} of the currently active central view.
   * <code>-1</code> if none is active.
   */
  protected int centralViewIdx = -1;

  /**
   * The index in {@link #verticalViews} of the currently active right view.
   * <code>-1</code> if none is active.
   */
  protected int rightViewIdx = -1;

  /**
   * The index in {@link #horizontalViews} of the currently active top view.
   * <code>-1</code> if none is active.
   */
  protected int topViewIdx = -1;

  /**
   * The index in {@link #horizontalViews} of the currently active bottom view.
   * <code>-1</code> if none is active.
   */
  protected int bottomViewIdx = -1;

  protected boolean viewsInited = false;

  /**
   * Used to know the last F-key used when adding a new view.
   */
  protected int fKeyNumber = 2;
}
