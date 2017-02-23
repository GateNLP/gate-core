/**
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz - 14/12/2009
 *
 *  $Id$
 */

package gate.gui.docview;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageResource;
import gate.Resource;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OConstants;
import gate.creole.ontology.OResource;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.OntologyModificationListener;
import gate.creole.ontology.RDFProperty;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.gui.MainFrame;
import gate.gui.annedit.AnnotationData;
import gate.gui.annedit.AnnotationDataImpl;
import gate.gui.ontology.OntologyItemComparator;
import gate.util.GateRuntimeException;
import gate.util.OptionsMap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Document view that displays an ontology class tree to annotate a document.
 *
 * tick the checkbox of a class to show the instances highlighted in the text
 * selected class will be used when creating a new annotation from a text
 *    selection in the document
 * take only the first 20 characters of the selection for the instance name
 *    and add a number if already existing
 * allow multiple ontologies to be used at the same time
 * put each ontology in a JPanel with triangle icons to hide/show the panel,
 *    hidden by default
 * open only first level of classes when opening an ontology
 * load lazily the ontology trees
 * context menu for classes to hide/show them, saved in user configuration
 */
@SuppressWarnings("serial")
public class OntologyClassView extends AbstractDocumentView
    implements CreoleListener, OntologyModificationListener {

  public OntologyClassView() {

    colorByClassMap = new HashMap<OClass, Color>();
    highlightedClasses = new HashSet<OClass>();
    highlightsDataByClassMap = new HashMap<OClass, List<TextualDocumentView.HighlightData>>();
    treeByOntologyMap = new HashMap<Ontology, JTree>();
    String prefix = getClass().getName() + '.';
    hiddenClassesSet = userConfig.getSet(prefix + "hiddenclasses");
    itemComparator = new OntologyItemComparator();
  }

  @Override
  protected void initGUI() {

    // get a pointer to the text view used to display
    // the selected annotations
    Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
    while(textView == null && centralViewsIter.hasNext()){
      DocumentView aView = centralViewsIter.next();
      if(aView instanceof TextualDocumentView)
        textView = (TextualDocumentView) aView;
    }
    textArea = textView.getTextView();
    // get a pointer to the instance view
    Iterator<DocumentView> horizontalViewsIter = owner.getHorizontalViews().iterator();
    while(instanceView == null && horizontalViewsIter.hasNext()){
      DocumentView aView = horizontalViewsIter.next();
      if (aView instanceof OntologyInstanceView) {
        instanceView = (OntologyInstanceView) aView;
      }
    }
    instanceView.setOwner(owner);

    mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    treesPanel = new JPanel();
    treesPanel.setLayout(new GridBagLayout());
    // add a disclosure panel for each loaded ontology in the system
    boolean isOntologyLoaded = false;
    List<LanguageResource> resources =
      gate.Gate.getCreoleRegister().getPublicLrInstances();
    for (LanguageResource resource : resources) {
      if (resource instanceof Ontology) {
        loadOntology((Ontology) resource);
        isOntologyLoaded = true;
      }
    }
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    mainPanel.add(new JScrollPane(treesPanel), gbc);
    setComboBox = new JComboBox<String>();
    setComboBox.setEditable(true);
    setComboBox.setToolTipText(
      "Annotation set where to load/save the annotations");
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.SOUTH;
    mainPanel.add(setComboBox, gbc);

    initListeners();

    // fill the annotation sets list
    List<String> annotationSets = new ArrayList<String>();
    annotationSets.add("");
    annotationSets.addAll(document.getAnnotationSetNames());
    Collections.sort(annotationSets);
    setComboBox.setModel(new DefaultComboBoxModel<String>(
      new Vector<String>(annotationSets)));

    if (isOntologyLoaded) {
      // find the first set that contains annotations used before by this view
      selectedSet = "";
      for (int i = 0; i < setComboBox.getItemCount(); i++) {
        String setName = setComboBox.getItemAt(i);
        if (setColorTreeNodesWhenInstancesFound(setName)) {
          selectedSet = setName;
          break;
        }
      }
      setComboBox.setSelectedItem(selectedSet);
    } else {
      messageLabel = new JLabel(
        "<html><p><font color=red>Load at least one ontology.");
      messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));
      messageLabel.setBackground(
        UIManager.getColor("Tree.selectionBackground"));
      gbc = new GridBagConstraints();
      treesPanel.add(messageLabel, gbc);
    }
  }

  protected void initListeners() {

    Gate.getCreoleRegister().addCreoleListener(this);

    setComboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        selectedSet = (String) setComboBox.getSelectedItem();
        setColorTreeNodesWhenInstancesFound(selectedSet);
        // unselect annotations
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          for (OClass oClass : highlightedClasses) {
            if (highlightsDataByClassMap.containsKey(oClass)) {
              textView.removeHighlights(highlightsDataByClassMap.get(oClass));
            }
          }
          highlightsDataByClassMap.clear();
          highlightedClasses.clear();
          // update showing trees
          for (JTree tree : treeByOntologyMap.values()) {
            if (tree.isShowing()) {
              tree.revalidate();
            }
          }
        }});
      }
    });

    // a listener that stops or restarts a timer which calls an action
    mouseStoppedMovingAction = new MouseStoppedMovingAction();
    mouseMovementTimer = new javax.swing.Timer(
      MOUSE_MOVEMENT_TIMER_DELAY, mouseStoppedMovingAction);
    mouseMovementTimer.setRepeats(false);
    textMouseListener = new TextMouseListener();
  }

  @Override
  protected void registerHooks() {
    textArea.addMouseListener(textMouseListener);
    textArea.addMouseMotionListener(textMouseListener);
    // reselect annotations
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      for (OClass oClass : new HashSet<OClass>(highlightedClasses)) {
        if (highlightsDataByClassMap.containsKey(oClass)) {
          textView.addHighlights(highlightsDataByClassMap.get(oClass));
        }
      }
    }});
    // show the instance view at the bottom
    if (!instanceView.isActive()) {
      owner.setBottomView(owner.horizontalViews.indexOf(instanceView));
    }
  }

  @Override
  protected void unregisterHooks() {
    textArea.removeMouseListener(textMouseListener);
    textArea.removeMouseMotionListener(textMouseListener);
    // unselect annotations
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      for (OClass oClass : highlightedClasses) {
        if (highlightsDataByClassMap.containsKey(oClass)) {
          textView.removeHighlights(highlightsDataByClassMap.get(oClass));
        }
      }
    }});
    // hide the instance view at the bottom
    if (instanceView.isActive()) {
      owner.setBottomView(-1);
    }
  }

  @Override
  public void cleanup() {
    super.cleanup();
    Gate.getCreoleRegister().removeCreoleListener(this);
    document = null;
    // save hidden classes to be reused next time
    String prefix = getClass().getName() + '.';
    userConfig.put(prefix + "hiddenclasses", hiddenClassesSet);
  }

  @Override
  public Component getGUI() {
    return mainPanel;
  }

  @Override
  public int getType() {
    return VERTICAL;
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
    if (e.getResource() instanceof Ontology) {
      if (messageLabel != null
       && treesPanel.isAncestorOf(messageLabel)) {
        treesPanel.remove(messageLabel);
        // find the first set that contains annotations used before by this view
        selectedSet = "";
        for (int i = 0; i < setComboBox.getItemCount(); i++) {
          String setName = setComboBox.getItemAt(i);
          if (setColorTreeNodesWhenInstancesFound(setName)) {
            selectedSet = setName;
            break;
          }
        }
        setComboBox.setSelectedItem(selectedSet);
      }
      Ontology ontology = (Ontology) e.getResource();
      loadOntology(ontology);
      // listen to modification of classes in the ontology to rebuild the tree
      ontology.addOntologyModificationListener(this);
    }
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    if (e.getResource() instanceof Ontology) {
      Ontology ontology = (Ontology) e.getResource();
      JTree tree = treeByOntologyMap.remove(ontology);
      for (Component component : treesPanel.getComponents()) {
        if (component instanceof JPanel
        && ((JPanel) component).isAncestorOf(tree)) {
          treesPanel.remove(component);
        }
      }
      treesPanel.revalidate();
    }
  }

  @Override
  public void datastoreOpened(CreoleEvent e) { /* do nothing */ }

  @Override
  public void datastoreCreated(CreoleEvent e) { /* do nothing */ }

  @Override
  public void datastoreClosed(CreoleEvent e) { /* do nothing */ }

  @Override
  public void resourceRenamed(Resource resource, String oldName,
                              String newName) { /* do nothing */ }
  @Override
  public void resourceRelationChanged(Ontology ontology, OResource
    resource1, OResource resource2, int eventType) { /* do nothing */  }

  @Override
  public void resourcePropertyValueChanged(Ontology ontology, OResource
    resource, RDFProperty property, Object value, int eventType) {
    /* do nothing */  }

  @Override
  public void resourcesRemoved(Ontology ontology, String[] resources) {  }

  @Override
  public void resourceAdded(Ontology ontology, OResource resource) {
    if (resource instanceof OClass) {
      final JTree tree = treeByOntologyMap.get(ontology);
      DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) tree.getModel().getRoot();
      final Enumeration<?> enumeration = node.preorderEnumeration();
      SwingUtilities.invokeLater(new Runnable() { @Override
      public void run() {
        // traverse the expanded class tree and update all the nodes
        while (enumeration.hasMoreElements()) {
          DefaultMutableTreeNode node =
            (DefaultMutableTreeNode) enumeration.nextElement();
          Object userObject = node.getUserObject();
          if (userObject != null
          && !userObject.equals("Loading...")) {
            // node already expanded
            OClass oClass = (OClass) userObject;
            Set<OClass> classes = oClass.getSubClasses(
              OConstants.Closure.DIRECT_CLOSURE);
            // readd all the children node
            addNodes(tree, node, classes, false);
          }
        }
      }});
    }
  }

  @Override
  public void ontologyReset(Ontology ontology) { /* do nothing */ }

  /**
   * Extract annotations that have been created by this view and
   * colored the corresponding tree class node if found.
   * @param setName the annotation set name to search
   * @return true if and only if at least one annotation has been found
   */
  protected boolean setColorTreeNodesWhenInstancesFound(String setName) {
    boolean returnValue = false;
    List<LanguageResource> resources =
      gate.Gate.getCreoleRegister().getPublicLrInstances();
    Map<String, Ontology> ontologyMap = new HashMap<String, Ontology>();
    for (LanguageResource resource : resources) {
      if (resource instanceof Ontology) {
        Ontology ontology = (Ontology) resource;
        String ontologyName = ontology.getDefaultNameSpace();
        ontologyName = ontologyName.substring(0, ontologyName.length()-1);
        ontologyMap.put(ontologyName, ontology);
      }
    }
    for (Annotation annotation :
        document.getAnnotations(setName).get(ANNOTATION_TYPE)) {
      FeatureMap features = annotation.getFeatures();
      if (features.get(ONTOLOGY) != null
       && features.get(CLASS) != null
       && features.get(INSTANCE) != null) {
        // find the corresponding ontology
        Ontology ontology = ontologyMap.get(features.get(ONTOLOGY));
        if (ontology != null) {
          // choose a background color for the annotation type tree node
          OClass oClass = ontology.getOClass(ontology
            .createOURI((String) features.get(CLASS)));
          if (oClass != null) {
            colorByClassMap.put(oClass,
              AnnotationSetsView.getColor(setName, oClass.getName()));
            returnValue = true;
          }
        }
      }
    }
    return returnValue;
  }

  /**
   * Add the ontology in a disclosure panel, closed at start.
   * @param ontology ontology to display
   */
  protected void loadOntology(final Ontology ontology) {

    // create the class tree
    final JTree tree = new JTree(new Object[]{"Loading..."});
    treeByOntologyMap.put(ontology, tree);
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);
    tree.setEditable(true);
    tree.getSelectionModel().setSelectionMode(
      TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

    final JPanel treePanel = new JPanel(new BorderLayout());
    final JCheckBox disclosureCheckBox = new JCheckBox(
      ontology.getName(), MainFrame.getIcon("closed"), false);
    disclosureCheckBox.setSelectedIcon(MainFrame.getIcon("expanded"));
    treePanel.add(disclosureCheckBox, BorderLayout.NORTH);

    // show/hide the tree when clicking the disclosure checkbox
    disclosureCheckBox.addActionListener(new ActionListener() {
      boolean isTreeBuilt = false;
      @Override
      public void actionPerformed(ActionEvent e) {
        if (disclosureCheckBox.isSelected()) {
          if (!isTreeBuilt) {
            tree.expandRow(0); // expands "Loading..." node
            buildClassTree(tree, ontology);
            isTreeBuilt = true;
          }
          treePanel.add(tree, BorderLayout.CENTER);
        } else {
          treePanel.remove(tree);
        }
        treesPanel.repaint();
      }
    });

    // context menu to show root classes
    disclosureCheckBox.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) { processMouseEvent(e); }
      @Override
      public void mouseReleased(MouseEvent e) { processMouseEvent(e); }
      @Override
      public void mouseClicked(MouseEvent e) { processMouseEvent(e); }
      protected void processMouseEvent(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        if (e.isPopupTrigger()) {
          popup.add(new JMenuItem(
            new AbstractAction("Show all root classes") {
            @Override
            public void actionPerformed(ActionEvent e) {
              if (!disclosureCheckBox.isSelected()) {
                disclosureCheckBox.doClick();
              }
              hiddenClassesSet.clear();
              DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getModel().getRoot();
              final Set<OClass> classes = ontology.getOClasses(true);
              // add first level nodes to the tree
              addNodes(tree, node, classes, false);
            }
          }));
          popup.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    // when a class is selected in the tree update the instance table
    tree.getSelectionModel().addTreeSelectionListener(
      new TreeSelectionListener() {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
          if (e.getNewLeadSelectionPath() == null) {
           if (treeByOntologyMap.get(selectedClass.getOntology()).equals(tree)){
             // only nullify selectedClass if unselect from the same tree
            selectedClass = null;
           }
          } else {
            if (tree.getSelectionCount() == 1) { // a class is selected
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
              e.getNewLeadSelectionPath().getLastPathComponent();
            selectedClass = (OClass) node.getUserObject();
            } else { // several classes are selected
              selectedClass = null;
            }
            // clear selection in other trees
            for (JTree aTree : treeByOntologyMap.values()) {
              if (!aTree.equals(tree)) {
                aTree.clearSelection();
              }
            }
          }
          instanceView.updateInstanceTable(selectedClass);
        }
      }
    );

    // context menu to hide/show classes
    tree.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
        if (e.isPopupTrigger()
        && !tree.isPathSelected(path)) {
          // if right click outside the selection then reset selection
          tree.getSelectionModel().setSelectionPath(path);
        }
        processMouseEvent(e);
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        processMouseEvent(e);
      }
      @Override
      public void mouseClicked(MouseEvent e) {
        processMouseEvent(e);
      }
      protected void processMouseEvent(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        if (!e.isPopupTrigger()) { return; }
        popup.add(new JMenuItem(
          new AbstractAction("Hide selected classes") {
          @Override
          public void actionPerformed(ActionEvent e) {
            DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
            TreePath[] selectedPaths = tree.getSelectionPaths();
            for (TreePath selectedPath : selectedPaths) {
              DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                selectedPath.getLastPathComponent();
              if (node.getParent() != null) {
                treeModel.removeNodeFromParent(node);
                Object userObject = node.getUserObject();
                OClass oClass = (OClass) userObject;
                hiddenClassesSet.add(oClass.getONodeID().toString());
              }
            }
          }
        }));

        if (tree.getSelectionCount() == 1) {
          popup.add(new JMenuItem(new AbstractAction("Show all sub classes") {
            @Override
            public void actionPerformed(ActionEvent e) {
              DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getSelectionPath().getLastPathComponent();
              Object userObject = node.getUserObject();
              OClass oClass = (OClass) userObject;
              Set<OClass> classes = oClass.getSubClasses(
                OClass.Closure.DIRECT_CLOSURE);
              addNodes(tree, node, classes, false);
            }
          }));
        }
        popup.show(e.getComponent(), e.getX(), e.getY());
      }
    });

    GridBagConstraints  gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.gridx = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    treesPanel.add(treePanel, gbc);
  }

  /**
   * Build the class tree from the ontology.
   * Based on {@link gate.gui.ontology.OntologyEditor#rebuildModel()}.
   * @param tree tree to build
   * @param ontology ontology to use
   */
  protected void buildClassTree(final JTree tree, Ontology ontology) {
    if (ontology == null) { return; }

    // listener to lazily create children nodes
    tree.addTreeWillExpandListener(new TreeWillExpandListener() {
      @Override
      public void treeWillExpand(TreeExpansionEvent event)
        throws ExpandVetoException {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
          event.getPath().getLastPathComponent();
        DefaultMutableTreeNode nodeFirstChild =
          (DefaultMutableTreeNode) node.getChildAt(0);
        if (nodeFirstChild.getUserObject().equals("Loading...")) {
          // if this node has not already been expanded
          node.removeAllChildren();
          Object userObject = node.getUserObject();
          OClass oClass = (OClass) userObject;
          Set<OClass> classes =
            oClass.getSubClasses(OClass.Closure.DIRECT_CLOSURE);
          // add children nodes to the current tree node
          addNodes(tree, node, classes, true);
        }
      }
      @Override
      public void treeWillCollapse(TreeExpansionEvent event)
        throws ExpandVetoException { /* do nothing */  }
    });

    final DefaultMutableTreeNode node = new DefaultMutableTreeNode(null, true);
    final Set<OClass> classes = ontology.getOClasses(true);
    // add first level nodes to the tree
    addNodes(tree, node, classes, true);
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      tree.setModel(new DefaultTreeModel(node));
      tree.setCellRenderer(new ClassTreeCellRenderer());
      tree.setCellEditor(new ClassTreeCellEditor(tree));
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getModel().getRoot();
      Enumeration<?> enumeration = node.children();
      // expand tree until second level
      while (enumeration.hasMoreElements()) {
        node = (DefaultMutableTreeNode) enumeration.nextElement();
        tree.expandPath(new TreePath(node.getPath()));
      }
    }});
  }

  /**
   * Add children nodes to the parent node in the tree.
   * @param tree tree to update
   * @param parent parent node
   * @param newChildren children classes to add
   * @param filterClasses if children nodes contain hidden classes
   * then don't add them.
   */
  protected void addNodes(JTree tree, DefaultMutableTreeNode parent,
                          Set<OClass> newChildren,
                          boolean filterClasses) {
    // list the existing children classes of the parent node
    List<OClass> children = new ArrayList<OClass>();
    for (int i = 0; i < parent.getChildCount(); i++) {
      DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getChildAt(i);
      Object userObject = node.getUserObject();
      if (userObject instanceof OClass) {
        OClass oClass = (OClass) userObject;
        children.add(oClass);
      } else if (userObject.equals("Loading...")) {
        parent.removeAllChildren();
        children.clear();
        break;
      }
    }
    int index = -1;
    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
    List<OClass> subClasses = new ArrayList<OClass>(newChildren);
    Collections.sort(subClasses, itemComparator);
    // for each children classes to add to the parent node
    for (OClass subClass : subClasses) {
      index++;
      if (index > parent.getChildCount()) { index = parent.getChildCount(); }
      if (filterClasses) {
        if (hiddenClassesSet.contains(subClass.getONodeID().toString())) {
          // this class is filtered so skip it
          continue;
        }
      } else {
        hiddenClassesSet.remove(subClass.getONodeID().toString());
      }
      DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subClass);
      if (!filterClasses || !children.contains(subClass)) {
        if (!subClass.getSubClasses(OClass.Closure.DIRECT_CLOSURE).isEmpty()) {
          subNode.insert(new DefaultMutableTreeNode("Loading..."), 0);
        }
      }
      if (!children.contains(subClass)) {
        // add the children node if not already existing
        treeModel.insertNodeInto(subNode, parent, index);
      }
    }
    tree.expandPath(new TreePath(parent.getPath()));
  }

  /**
   * A mouse listener used for events in the text view.
   * Stop or restart the timer that will call {@link MouseStoppedMovingAction}.
   * Based on {@link AnnotationSetsView.TextMouseListener}.
   */
  protected class TextMouseListener extends MouseInputAdapter {
    @Override
    public void mouseDragged(MouseEvent e){
      //do not create annotations while dragging
      mouseMovementTimer.stop();
    }
    @Override
    public void mouseMoved(MouseEvent e){
      //this triggers select annotation leading to edit annotation or new
      //annotation actions
      //ignore movement if CTRL pressed or dragging
      int modEx = e.getModifiersEx();
      if((modEx & MouseEvent.CTRL_DOWN_MASK) != 0){
        mouseMovementTimer.stop();
        return;
      }
      if((modEx & MouseEvent.BUTTON1_DOWN_MASK) != 0){
        mouseMovementTimer.stop();
        return;
      }
      //check the text location is real
      int textLocation = textArea.viewToModel(e.getPoint());
      try {
        Rectangle viewLocation = textArea.modelToView(textLocation);
        //expand the rectangle a bit
        int error = 10;
        viewLocation = new Rectangle(viewLocation.x - error,
                                     viewLocation.y - error,
                                     viewLocation.width + 2*error,
                                     viewLocation.height + 2*error);
        if(viewLocation.contains(e.getPoint())){
          mouseStoppedMovingAction.setTextLocation(textLocation);
        }else{
          mouseStoppedMovingAction.setTextLocation(-1);
        }
      }
      catch(BadLocationException ble) {
        throw new GateRuntimeException(ble);
      }finally{
        mouseMovementTimer.restart();
      }
    }
    @Override
    public void mouseExited(MouseEvent e){
      mouseMovementTimer.stop();
    }
  }

  /**
   * Add the text selection to the filter instance table to enable creating
   * a new instance from the selection or adding it as a new label to an
   * existing instance.
   * Based on {@link AnnotationSetsView.MouseStoppedMovingAction}.
   */
  protected class MouseStoppedMovingAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent evt) {
      List<LanguageResource> resources =
        gate.Gate.getCreoleRegister().getPublicLrInstances();
      Map<String, Ontology> ontologyMap = new HashMap<String, Ontology>();
      for (LanguageResource resource : resources) {
        if (resource instanceof Ontology) {
          Ontology ontology = (Ontology) resource;
          String ontologyName = ontology.getDefaultNameSpace();
          ontologyName = ontologyName.substring(0, ontologyName.length()-1);
          ontologyMap.put(ontologyName, ontology);
        }
      }
      // check for annotations at mouse location
      String setName = (String) setComboBox.getSelectedItem();
      for (Annotation annotation : document.getAnnotations(setName)
            .get(ANNOTATION_TYPE).get(Math.max(0l, textLocation-1),
              Math.min(document.getContent().size(), textLocation+1))) {
        final FeatureMap features = annotation.getFeatures();
        if (features.get(ONTOLOGY) != null
         && features.get(CLASS) != null
         && features.get(INSTANCE) != null) {
          // find the corresponding ontology
          final Ontology ontology =
            ontologyMap.get(features.get(ONTOLOGY));
          if (ontology != null) {
            OClass oClass = ontology.getOClass(ontology
              .createOURI((String) features.get(CLASS)));
            // find if the annotation class is highlighted
            if (oClass != null
             && highlightedClasses.contains(oClass)) {
              final JTree tree = treeByOntologyMap.get(ontology);
              DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) tree.getModel().getRoot();
              Enumeration<?> nodesEnum = node.preorderEnumeration();
              boolean done = false;
              // traverse the class tree
              while(!done && nodesEnum.hasMoreElements()) {
                node = (DefaultMutableTreeNode) nodesEnum.nextElement();
                done = node.getUserObject() instanceof OClass
                    && node.getUserObject().equals(oClass);
              }
              if (done) {
                // select the class in the tree
                TreePath nodePath = new TreePath(node.getPath());
                tree.setSelectionPath(nodePath);
                tree.scrollPathToVisible(nodePath);
                SwingUtilities.invokeLater(new Runnable() { @Override
                public void run() {
                  // select the annotation in the instances table
                  instanceView.selectInstance(ontology.getOInstance(
                    ontology.createOURI((String) features.get(INSTANCE))));
                }});
                break;
              }
            }
          }
        }
      }

      int start = textArea.getSelectionStart();
      int end   = textArea.getSelectionEnd();
      String selectedText = textArea.getSelectedText();
      if (textLocation == -1
       || selectedClass == null
       || selectedText == null
       || start > textLocation
       || end < textLocation
       || start == end) {
        return;
      }
      // remove selection
      textArea.setSelectionStart(start);
      textArea.setSelectionEnd(start);
      instanceView.addSelectionToFilter(selectedSet, selectedText, start, end);
    }

    public void setTextLocation(int textLocation){
      this.textLocation = textLocation;
    }
    int textLocation;
  }

  public void setClassHighlighted(final OClass oClass, boolean isHighlighted) {
    final JTree tree = treeByOntologyMap.get(oClass.getOntology());
    if (isHighlighted) {
      // find all annotations for the class
      final List<AnnotationData> annotationsData =
        new ArrayList<AnnotationData>();
      AnnotationSet annotationSet = document.getAnnotations(selectedSet);
      String ontologyName = oClass.getOntology().getDefaultNameSpace();
      ontologyName = ontologyName.substring(0, ontologyName.length()-1);
      for (Annotation annotation : annotationSet.get(ANNOTATION_TYPE)) {
        FeatureMap features = annotation.getFeatures();
        if (features.get(ONTOLOGY) != null
        && features.get(ONTOLOGY).equals(ontologyName)
        && features.get(CLASS) != null
        && features.get(CLASS).equals(oClass.getONodeID().toString())
        && features.get(INSTANCE) != null) {
          annotationsData.add(new AnnotationDataImpl(annotationSet,annotation));
        }
      }
      highlightedClasses.add(oClass);
      if (annotationsData.isEmpty()) {
        // no instance annotation for this class
        colorByClassMap.remove(oClass);
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          if (highlightsDataByClassMap.containsKey(oClass)) {
            textView.removeHighlights(highlightsDataByClassMap.get(oClass));
          }
          highlightsDataByClassMap.remove(oClass);
          tree.repaint();
        }});
      } else {
        final Color color;
        if (colorByClassMap.containsKey(oClass)) {
          color = colorByClassMap.get(oClass);
        } else {
          color = AnnotationSetsView.getColor(selectedSet,oClass.getName());
          colorByClassMap.put(oClass, color);
        }
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          highlightsDataByClassMap.put(oClass,
            textView.addHighlights(annotationsData, color));
          tree.repaint();
        }});
      }
    } else { // if (!isHighlighted)
      highlightedClasses.remove(oClass);
        SwingUtilities.invokeLater(new Runnable() { @Override
        public void run() {
          if (highlightsDataByClassMap.containsKey(oClass)) {
            textView.removeHighlights(highlightsDataByClassMap.get(oClass));
          }
          tree.repaint();
        }});
    }
  }

  /**
   * To see if it's worth using it to optimise highlights display.
   * @param set set
   * @param annotation annotation
   * @param oClass class
   * @param tree tree
   */
  public void highlightInstance(AnnotationSet set, Annotation annotation,
                                final OClass oClass, final JTree tree) {
    final AnnotationData annotationData = new AnnotationDataImpl(set, annotation);
    final List<TextualDocumentView.HighlightData> highlightsData = highlightsDataByClassMap.containsKey(oClass) ?
      highlightsDataByClassMap.get(oClass) : new ArrayList<TextualDocumentView.HighlightData>();
    highlightedClasses.add(oClass);
    final Color color;
    if (colorByClassMap.containsKey(oClass)) {
      color = colorByClassMap.get(oClass);
    } else {
      color = AnnotationSetsView.getColor(set.getName(),oClass.getName());
      colorByClassMap.put(oClass, color);
    }
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      highlightsData.add(textView.addHighlight(annotationData, color));
      highlightsDataByClassMap.put(oClass, highlightsData);
      tree.repaint();
    }});
  }

  protected class ClassTreeCellRenderer extends JPanel
      implements TreeCellRenderer {

    protected Object userObject;
    protected JCheckBox checkBox;
    protected JLabel label;
    private Color selectionColor =
      UIManager.getColor("Tree.selectionBackground");
    private Color backgroundColor = UIManager.getColor("Tree.textBackground");
    private Border normalBorder =
      BorderFactory.createLineBorder(backgroundColor, 1);
    private Border selectionBorder =
      BorderFactory.createLineBorder(selectionColor, 1);

    protected Object getUserObject() {
      return userObject;
    }

    protected JCheckBox getCheckBox() {
      return checkBox;
    }

    public ClassTreeCellRenderer() {
      setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
      setBorder(normalBorder);
      setOpaque(true);
      setBackground(backgroundColor);
      checkBox = new JCheckBox();
      checkBox.setMargin(new Insets(0, 0, 0, 0));
      checkBox.setOpaque(true);
      checkBox.setBackground(backgroundColor);
      add(checkBox);
      label = new JLabel();
      label.setOpaque(true);
      label.setBackground(backgroundColor);
      add(label);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                               boolean isSelected, boolean isExpanded,
                               boolean isLeaf, int row, boolean hasFocus) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      userObject = node.getUserObject();
      if (node.getUserObject() == null) { return this; }
      OClass oClass = (OClass) node.getUserObject();
      checkBox.setSelected(highlightedClasses.contains(oClass));
      checkBox.setBackground(isSelected ? selectionColor : backgroundColor);
      label.setText(oClass.getName());
      label.setBackground(colorByClassMap.containsKey(oClass) ?
        colorByClassMap.get(oClass) : isSelected ?
          selectionColor : backgroundColor);
        setBackground(isSelected ? selectionColor : backgroundColor);
      setBorder(isSelected ? selectionBorder : normalBorder);

      return this;
    }
  }

  protected class ClassTreeCellEditor extends AbstractCellEditor
      implements TreeCellEditor {

    ClassTreeCellRenderer renderer = new ClassTreeCellRenderer();
    JTree tree;

    public ClassTreeCellEditor(JTree tree) {
      this.tree = tree;
    }

    @Override
    public Object getCellEditorValue() {
      boolean isSelected = renderer.getCheckBox().isSelected();
      Object userObject = renderer.getUserObject();
      OClass oClass = (OClass) userObject;
      // show/hide highlights according to the checkbox state
      setClassHighlighted(oClass, isSelected);
      return userObject;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
      boolean returnValue = false;
      if (event instanceof MouseEvent) {
        MouseEvent mouseEvent = (MouseEvent) event;
        TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                                                mouseEvent.getY());
        if (path != null) {
          Object node = path.getLastPathComponent();
          if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
            Rectangle r = tree.getPathBounds(path);
            int x = mouseEvent.getX() - r.x;
            JCheckBox checkbox = renderer.getCheckBox();
            // checks if the mouse click was on the checkbox not the label
            returnValue = x > 0 && x < checkbox.getPreferredSize().width;
          }
        }
      }
      return returnValue;
    }

    @Override
    public Component getTreeCellEditorComponent(final JTree tree, Object value,
        boolean selected, boolean expanded, boolean leaf, int row) {

      // reuse renderer as an editor
      Component editor = renderer.getTreeCellRendererComponent(tree, value,
          true, expanded, leaf, row, true);

      // stop editing when checkbox has state changed
      renderer.getCheckBox().addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
          stopCellEditing();
        }
     });

      return editor;
    }
  }

  public String getSelectedSet() {
    return selectedSet;
  }

  // external resources
  protected TextualDocumentView textView;
  protected JTextArea textArea;
  protected OntologyInstanceView instanceView;

  // UI components
  protected JPanel mainPanel;
  protected JLabel messageLabel;
  protected JPanel treesPanel;
  protected JComboBox<String> setComboBox;

  // local objects
  /** Class that has the lead selection in the focused ontology tree. */
  protected OClass selectedClass;
  /** Classes highlighted in the document with their checkboxes ticked
   *  in the class tree. */
  protected Set<OClass> highlightedClasses;
  /** Colors for class and their instances only if the latter exist. */
  protected Map<OClass, Color> colorByClassMap;
  /** HighlightData list for each class. */
  protected Map<OClass, List<TextualDocumentView.HighlightData>> highlightsDataByClassMap;
  /** Link trees with their ontologies. */
  protected Map<Ontology, JTree> treeByOntologyMap;
  /** Classes to hide in the trees. */
  protected LinkedHashSet<String> hiddenClassesSet;
  /** Annotation set name where to read/save the instance annotations. */
  protected String selectedSet;
  protected OntologyItemComparator itemComparator;
  protected MouseStoppedMovingAction mouseStoppedMovingAction;
  protected TextMouseListener textMouseListener;
  protected Timer mouseMovementTimer;
  protected static final int MOUSE_MOVEMENT_TIMER_DELAY = 500;
  protected OptionsMap userConfig = Gate.getUserConfig();

  // constants for annotation feature, annotation type
  protected static final String ONTOLOGY =
    gate.creole.ANNIEConstants.LOOKUP_ONTOLOGY_FEATURE_NAME;
  protected static final String CLASS =
    gate.creole.ANNIEConstants.LOOKUP_CLASS_FEATURE_NAME;
  protected static final String INSTANCE =
    gate.creole.ANNIEConstants.LOOKUP_INSTANCE_FEATURE_NAME;
  protected static final String ANNOTATION_TYPE = "Mention";
}
