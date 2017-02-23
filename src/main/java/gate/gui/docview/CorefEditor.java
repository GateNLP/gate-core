/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  CorefEditor.java
 *
 *  Niraj Aswani, 24-Jun-2004
 *
 *  $Id: CorefEditor.java 19152 2016-03-17 18:39:49Z markagreenwood $
 */

package gate.gui.docview;

import gate.Annotation;
import gate.AnnotationSet;
import gate.creole.ANNIEConstants;
import gate.event.AnnotationSetEvent;
import gate.event.AnnotationSetListener;
import gate.gui.MainFrame;
import gate.swing.ColorGenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * Display a tree that contains the co-references type of the document,
 * highlight co-references in the document, allow creating
 * co-references from existing annotations, editing and deleting co-references.
 */
@SuppressWarnings("serial")
public class CorefEditor
    extends AbstractDocumentView
    implements ActionListener, gate.event.FeatureMapListener,
    gate.event.DocumentListener, AnnotationSetListener {

  // default AnnotationSet Name
  private final static String DEFAULT_ANNOTSET_NAME = "Default";

  private JPanel mainPanel, topPanel, subPanel;
  private JToggleButton showAnnotations;
  private JComboBox<String> annotSets, annotTypes;
  private DefaultComboBoxModel<String> annotSetsModel, annotTypesModel;

  // Co-reference Tree
  private JTree corefTree;

  // Root node
  private CorefTreeNode rootNode;

  // top level hashMap (corefChains)
  // AnnotationSet(CorefTreeNode) --> (CorefTreeNode type ChainNode --> ArrayList AnnotationIds)
  private Map<CorefTreeNode, Map<CorefTreeNode, List<Integer>>> corefChains;

  // This is used to store the annotationSet name and its respective corefTreeNode
  // annotationSetName --> CorefTreeNode of type (AnnotationSet)
  private Map<String,CorefTreeNode> corefAnnotationSetNodesMap;

  // annotationSetName --> (chainNodeString --> Boolean)
  private Map<String,Map<String,Boolean>> selectionChainsMap;

  // chainString --> Boolean
  private Map<String,Boolean> currentSelections;

  // annotationSetName --> (chainNodeString --> Color)
  private Map<String,Map<String,Color>> colorChainsMap;

  // chainNodeString --> Color
  private Map<String,Color> currentColors;

  private ColorGenerator colorGenerator;
  private TextualDocumentView textView;
  private JTextArea textPane;

  /* ChainNode --> (HighlightedTags) */
  private Map<CorefTreeNode, List<Object>> highlightedTags;

  /* This arraylist stores the highlighted tags for the specific selected annotation type */
  private List<Object> typeSpecificHighlightedTags;
  private TextPaneMouseListener textPaneMouseListener;

  /* This stores Ids of the highlighted Chain Annotations*/
  private List<Annotation> highlightedChainAnnots = new ArrayList<Annotation>();
  /* This stores start and end offsets of the highlightedChainAnnotations */
  private int[] highlightedChainAnnotsOffsets;

  /* This stores Ids of the highlighted Annotations of particular type */
  private List<Annotation> highlightedTypeAnnots = new ArrayList<Annotation>();
  /* This stores start and end offsets of highlightedTypeAnnots */
  private int[] highlightedTypeAnnotsOffsets;

  /* Timer for the Chain Tool tip action */
  private ChainToolTipAction chainToolTipAction;
  private javax.swing.Timer chainToolTipTimer;

  private NewCorefAction newCorefAction;
  private javax.swing.Timer newCorefActionTimer;

  private Annotation annotToConsiderForChain = null;
  private JWindow popupWindow;
  private boolean explicitCall = false;
  private Highlighter highlighter;

  /**
   * This method intiates the GUI for co-reference editor
   */
  @Override
  protected void initGUI() {

    //get a pointer to the textual view used for highlights
    Iterator<DocumentView> centralViewsIter = owner.getCentralViews().iterator();
    while (textView == null && centralViewsIter.hasNext()) {
      DocumentView aView = centralViewsIter.next();
      if (aView instanceof TextualDocumentView)
        textView = (TextualDocumentView) aView;
    }
    textPane = (JTextArea) ( (JScrollPane) textView.getGUI()).getViewport().
               getView();
    highlighter = textPane.getHighlighter();
    chainToolTipAction = new ChainToolTipAction();
    chainToolTipTimer = new javax.swing.Timer(500, chainToolTipAction);
    chainToolTipTimer.setRepeats(false);
    newCorefAction = new NewCorefAction();
    newCorefActionTimer = new javax.swing.Timer(500, newCorefAction);
    newCorefActionTimer.setRepeats(false);

    colorGenerator = new ColorGenerator();

    // main Panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // topPanel
    topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());

    // subPanel
    subPanel = new JPanel();
    subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    // showAnnotations Button
    showAnnotations = new JToggleButton("Show");
    showAnnotations.addActionListener(this);

    // annotSets
    annotSets = new JComboBox<String>();
    annotSets.addActionListener(this);

    // get all the annotationSets
    Map<String,AnnotationSet> annotSetsMap = document.getNamedAnnotationSets();
    annotSetsModel = new DefaultComboBoxModel<String>();
    if (annotSetsMap != null) {
      String [] array = annotSetsMap.keySet().toArray(new String[annotSetsMap.keySet().size()]);
      for(int i=0;i<array.length;i++) {
        annotSetsMap.get(array[i]).addAnnotationSetListener(this);
      }
      annotSetsModel = new DefaultComboBoxModel<String>(array);
    }
    document.getAnnotations().addAnnotationSetListener(this);
    annotSetsModel.insertElementAt(DEFAULT_ANNOTSET_NAME, 0);
    annotSets.setModel(annotSetsModel);

    // annotTypes
    annotTypesModel = new DefaultComboBoxModel<String>();
    annotTypes = new JComboBox<String>(annotTypesModel);
    annotTypes.addActionListener(this);
    subPanel.add(new JLabel("Sets : "));
    subPanel.add(annotSets);
    JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    tempPanel.add(new JLabel("Types : "));
    tempPanel.add(annotTypes);
    tempPanel.add(showAnnotations);
    // intialises the Data
    initData();

    // and creating the tree
    corefTree = new JTree(rootNode);
    corefTree.putClientProperty("JTree.lineStyle", "None");
    corefTree.setRowHeight(corefTree.getRowHeight() * 2);
    corefTree.setLargeModel(true);
    corefTree.setAutoscrolls(true);

    //corefTree.setRootVisible(false);
    //corefTree.setShowsRootHandles(false);
    corefTree.addMouseListener(new CorefTreeMouseListener());
    corefTree.setCellRenderer(new CorefTreeCellRenderer());

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(corefTree), BorderLayout.CENTER);
    topPanel.add(subPanel, BorderLayout.CENTER);
    topPanel.add(tempPanel, BorderLayout.SOUTH);

    // get the highlighter
    textPaneMouseListener = new TextPaneMouseListener();
    annotSets.setSelectedIndex(0);

    // finally show the tree
    //annotSetSelectionChanged();

    document.addDocumentListener(this);
    document.getFeatures().addFeatureMapListener(this);
  }

  public void reinitAllVariables() {

    if (highlightedChainAnnots != null)
      highlightedChainAnnots.clear();
    if (highlightedTypeAnnots != null)
      highlightedTypeAnnots.clear();
    if (typeSpecificHighlightedTags != null)
      typeSpecificHighlightedTags.clear();
    highlightedChainAnnotsOffsets = null;
    highlightedTypeAnnotsOffsets = null;
    if (highlightedTags != null && highlightedTags.values() != null) {
      Iterator<List<Object>> highlightsIter = highlightedTags.values().iterator();
      while (highlightsIter.hasNext()) {
        List<Object> tags = highlightsIter.next();
        for (int i = 0; i < tags.size(); i++) {
          highlighter.removeHighlight(tags.get(i));
        }
      }
      highlightedTags.clear();
    }

    // we need to find out all the annotSetNodes and remove all the chainNodes
    // under it
    Iterator<String> annotSetsIter = corefAnnotationSetNodesMap.keySet().iterator();
    while (annotSetsIter.hasNext()) {
      CorefTreeNode annotSetNode = corefAnnotationSetNodesMap.get(annotSetsIter.next());
      annotSetNode.removeAllChildren();
      colorChainsMap.put(annotSetNode.toString(), new HashMap<String,Color>());
      selectionChainsMap.put(annotSetNode.toString(), new HashMap<String,Boolean>());
      corefChains.put(annotSetNode, new HashMap<CorefTreeNode, List<Integer>>());
    }
  }

      /** This methods cleans up the memory by removing all listener registrations */
  @Override
  public void cleanup() {
    document.removeDocumentListener(this);
    document.getFeatures().removeFeatureMapListener(this);
  }

  /** Given arrayList containing Ids of the annotations, and an annotationSet, this method
   * returns the annotations that has longest string among the matches
   */
  public Annotation findOutTheLongestAnnotation(List<Integer> matches,
                                                AnnotationSet set) {
    if (matches == null || matches.size() == 0) {
      return null;
    }
    int length = 0;
    int index = 0;
    for (int i = 0; i < matches.size(); i++) {
      Annotation currAnn = set.get(matches.get(i));
      int start = currAnn.getStartNode().getOffset().intValue();
      int end = currAnn.getEndNode().getOffset().intValue();
      if ( (end - start) > length) {
        length = end - start;
        index = i;
      }
    }
    // so now we now have the longest String annotations at index
    return set.get(matches.get(index));
  }

  /**
   * This method is called when any annotationSet is removed outside the
   * co-reference editor..
   * @param de
   */
  @Override
  public void annotationSetRemoved(gate.event.DocumentEvent de) {
    // this method removes the annotationSet from the annotSets
    // and all chainNodes under it

    String annotSet = de.getAnnotationSetName();
    annotSet = (annotSet == null) ? DEFAULT_ANNOTSET_NAME : annotSet;
    // find out the currently Selected annotationSetName
    String annotSetName = (String) annotSets.getSelectedItem();
    // remove it from the main data store
    corefChains.remove(corefAnnotationSetNodesMap.get(annotSet));
    // remove it from the main data store
    corefAnnotationSetNodesMap.remove(annotSet);
    // remove it from the annotationSetModel (combobox)
    annotSetsModel.removeElement(annotSet);
    annotSets.setModel(annotSetsModel);
    // remove it from the colorChainMap
    colorChainsMap.remove(annotSet);
    // remove it from the selectionChainMap
    selectionChainsMap.remove(annotSet);
    if (annotSetsModel.getSize() == 0) {
      // no annotationSet to display
      // so set visible false
      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.setVisible(false);
      }
      corefTree.setVisible(false);
    }
    else {
      if (annotSetName.equals(annotSet)) {
        if (popupWindow != null && popupWindow.isVisible()) {
          popupWindow.setVisible(false);
        }
        if (!corefTree.isVisible())
          corefTree.setVisible(true);

        annotSets.setSelectedIndex(0);
        //annotSetSelectionChanged();
      }
    }
  }

  /**
   * This method is called when any new annotationSet is added
   * @param de
   */
  @Override
  public void annotationSetAdded(gate.event.DocumentEvent de) {

    String annotSet = de.getAnnotationSetName();
    if(annotSet == null)
      document.getAnnotations().addAnnotationSetListener(this);
    else
      document.getAnnotations(annotSet).addAnnotationSetListener(this);

    annotSet = (annotSet == null) ? DEFAULT_ANNOTSET_NAME : annotSet;
    // find out the currently Selected annotationSetName
    String annotSetName = (String) annotSets.getSelectedItem();

    // check if newly added annotationSet is the default AnnotationSet
    CorefTreeNode annotSetNode = null;

    if (annotSet.equals(DEFAULT_ANNOTSET_NAME))
      annotSetNode = createAnnotSetNode(document.getAnnotations(), true);
    else
      annotSetNode = createAnnotSetNode(document.getAnnotations(annotSet), false);

    corefAnnotationSetNodesMap.put(annotSet, annotSetNode);
    /*annotSetsModel.addElement(annotSet);
    annotSets.setModel(annotSetsModel);*/

    if (annotSetName != null)
      annotSets.setSelectedItem(annotSetName);
    else
      annotSets.setSelectedIndex(0);

      //annotSetSelectionChanged();
  }

  /**Called when the content of the document has changed through an edit
   * operation.
   */
  @Override
  public void contentEdited(gate.event.DocumentEvent e) {
    //ignore
  }

  @Override
  public void annotationAdded(AnnotationSetEvent ase) {
    // ignore
  }

  @Override
  public void annotationRemoved(AnnotationSetEvent ase) {
    Annotation delAnnot = ase.getAnnotation();
    Integer id = delAnnot.getId();
    Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
    
    if(matchesMapObject == null)
      return;
    
    if(!(matchesMapObject instanceof Map)) {
      // no need to do anything
      // and return
      return;
    }

    @SuppressWarnings("unchecked")
    Map<String, List<List<Integer>>> matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;

    Set<String> keySet = matchesMap.keySet();
    if(keySet == null)
      return;

    Iterator<String> iter = keySet.iterator();
    boolean found = false;
    while(iter.hasNext()) {
      String currSet = iter.next();
      List<List<Integer>> matches = matchesMap.get(currSet);
      if(matches == null || matches.size() == 0)
        continue;
      else {
        for(int i=0;i<matches.size();i++) {
          List<Integer> ids = matches.get(i);
          if(ids.contains(id)) {
            // found
            // so remove this
            found = true;
            ids.remove(id);
            matches.set(i, ids);
            break;
          }
        }
        if(found) {
          matchesMap.put(currSet, matches);
          explicitCall = true;
          document.getFeatures().put(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME,
                                     matchesMap);
          explicitCall = false;
          break;
        }
      }
    }
    if(found)
      featureMapUpdated();
  }

  /**
   * Called when features are changed outside the co-refEditor
   */
  @Override
  public void featureMapUpdated() {

    if (explicitCall)
      return;

    // we would first save the current settings
    // 1. Current AnnotSet
    // 2. Current AnnotType
    // 3. ShowAnnotation Status
    String currentAnnotSet = (String) annotSets.getSelectedItem();
    String currentAnnotType = (String) annotTypes.getSelectedItem();
    boolean currentShowAnnotationStatus = showAnnotations.isSelected();

    // there is some change in the featureMap
    Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
    if(matchesMapObject == null || !(matchesMapObject instanceof Map)) {
      // no need to do anything
      // and return
      reinitAllVariables();
      explicitCall = false;
      
      SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run() {
          // not sure why this is necessary but if we are going to do
          // it then at least do it on the EDT to avoid a violation
          annotSets.setSelectedIndex(0);          
        }
      });
      
      return;
    }

    @SuppressWarnings("unchecked")
    Map<String, List<List<Integer>>> matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;

    //AnnotationSetName --> List of ArrayLists
    //each ArrayList contains Ids of related annotations
    Iterator<String> setIter = matchesMap.keySet().iterator();
    HashMap<Object, Boolean> annotSetsNamesMap = new HashMap<Object, Boolean>();
    for (int i = 0; i < annotSets.getItemCount(); i++) {
      annotSetsNamesMap.put( annotSets.getItemAt(i), new Boolean(false));
    }
    outer:while (setIter.hasNext()) {
      String currentSet = setIter.next();
      List<List<Integer>> matches = matchesMap.get(currentSet);
      currentSet = (currentSet == null) ? DEFAULT_ANNOTSET_NAME : currentSet;

      if (matches == null)
        continue;

      AnnotationSet currAnnotSet = getAnnotationSet(currentSet);
      annotSetsNamesMap.put(currentSet, new Boolean(true));

      Iterator<List<Integer>> entitiesIter = matches.iterator();
      //each entity is a list of annotation IDs

      if (corefAnnotationSetNodesMap.get(currentSet) == null) {
        // we need to create the node for this
        if (currentSet.equals(DEFAULT_ANNOTSET_NAME)) {
          corefAnnotationSetNodesMap.put(DEFAULT_ANNOTSET_NAME,
                                         createChain(document.getAnnotations(), true));
        }
        else {
          corefAnnotationSetNodesMap.put(currentSet,
                                         createChain(document.getAnnotations(
              currentSet), false));
        }
        continue outer;
      }

      Map<CorefTreeNode, List<Integer>> chains = corefChains.get(corefAnnotationSetNodesMap.get(currentSet));
      Map<CorefTreeNode, Boolean> visitedList = new HashMap<CorefTreeNode, Boolean>();

      if (chains != null) {
        Iterator<CorefTreeNode> chainsList = chains.keySet().iterator();

        // intially no chainHead is visited
        while (chainsList.hasNext()) {
          visitedList.put( chainsList.next(), new Boolean(false));
        }

        // now we need to search for the chainHead of each group
        List<List<Integer>> idsToRemove = new ArrayList<List<Integer>>();
        while (entitiesIter.hasNext()) {
          List<Integer> ids = entitiesIter.next();
          if (ids == null || ids.size() == 0) {
            idsToRemove.add(ids);
            continue;
          }

          CorefTreeNode chainHead = null;
          for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            // now lets find out the headnode for this, if it is available
            chainHead = findOutTheChainHead(currAnnotSet.get(id), currentSet);
            if (chainHead != null) {
              visitedList.put(chainHead, new Boolean(true));
              break;
            }
          }

          if (chainHead != null) {
            // we found the chainHead for this
            // so we would replace the ids
            // but before that we would check if chainHead should be replaced
            Annotation longestAnn = findOutTheLongestAnnotation(ids, getAnnotationSet(currentSet));
            if (getString(longestAnn).equals(chainHead.toString())) {
              chains.put(chainHead, ids);
              corefChains.put(corefAnnotationSetNodesMap.get(currentSet),
                              chains);
            }
            else {
              // we first check if new longestAnnotation String is already available as some other chain Node head
              if (currentColors.containsKey(getString(longestAnn))) {
                // yes one chainHead with this string already exists
                // so we need to merge them together
                String longestString = getString(longestAnn);
                CorefTreeNode tempChainHead = findOutChainNode(longestString, currentSet);
                // now all the ids under current chainHead should be placed under the tempChainHead
                List<Integer> tempIds = chains.get(tempChainHead);
                List<Integer> currentChainHeadIds = chains.get(chainHead);
                // so lets merge them
                tempIds.addAll(currentChainHeadIds);

                // and update the chains
                chains.remove(chainHead);
                chains.put(tempChainHead, tempIds);
                corefChains.put(corefAnnotationSetNodesMap.get(currentSet),
                                chains);
                visitedList.put(chainHead, new Boolean(false));
                visitedList.put(tempChainHead, new Boolean(true));

              }
              else {
                String previousString = chainHead.toString();
                String newString = getString(longestAnn);
                chainHead.setUserObject(newString);

                // we need to change the colors
                Color color = currentColors.get(previousString);
                currentColors.remove(previousString);
                currentColors.put(newString, color);
                colorChainsMap.put(newString, currentColors);

                // we need to change the selections
                Boolean val = currentSelections.get(previousString);
                currentSelections.remove(previousString);
                currentSelections.put(newString, val);
                selectionChainsMap.put(newString, currentSelections);

                chains.put(chainHead, ids);
                corefChains.put(corefAnnotationSetNodesMap.get(currentSet),
                                chains);
              }
            }
          }
          else {
            // this is something new addition
            // so we need to create a new chainNode
            // this is the new chain
            // get the current annotSetNode
            CorefTreeNode annotSetNode = corefAnnotationSetNodesMap.get(currentSet);

            // we need to find out the longest string annotation
            @SuppressWarnings("unused")
            AnnotationSet actSet = getAnnotationSet(currentSet);

            Annotation ann = findOutTheLongestAnnotation(ids, getAnnotationSet(currentSet));
            // so before creating a new chainNode we need to find out if
            // any of the chainNodes has the same string that of this chainNode
            HashMap<String, Boolean> tempSelection = (HashMap<String, Boolean>) selectionChainsMap.get(
                currentSet);
            CorefTreeNode chainNode = null;
            if (tempSelection.containsKey(getString(ann))) {
              chainNode = findOutChainNode(getString(ann), currentSet);

              // ArrayList matches
              Map<CorefTreeNode,List<Integer>> newHashMap = corefChains.get(annotSetNode);
              newHashMap.put(chainNode, ids);
              corefChains.put(annotSetNode, newHashMap);

              visitedList.put(chainNode, new Boolean(true));
            }
            else {
              // create the new chainNode
              chainNode = new CorefTreeNode(getString(ann), false,
                                            CorefTreeNode.CHAIN_NODE);

              // add this to tree
              annotSetNode.add(chainNode);
              corefAnnotationSetNodesMap.put(currentSet, annotSetNode);

              // ArrayList matches
              Map<CorefTreeNode, List<Integer>> newHashMap = corefChains.get(annotSetNode);
              newHashMap.put(chainNode, ids);
              corefChains.put(annotSetNode, newHashMap);

              boolean selectionValue = false;
              if(currentAnnotSet.equals(currentSet))
                selectionValue = true;

              // entry into the selection
              tempSelection.put(chainNode.toString(), new Boolean(selectionValue));
              selectionChainsMap.put(currentSet, tempSelection);

              // entry into the colors
              float components[] = colorGenerator.getNextColor().getComponents(null);
              Color color = new Color(components[0],
                                      components[1],
                                      components[2],
                                      0.5f);
              Map<String,Color> tempColors = colorChainsMap.get(currentSet);
              tempColors.put(chainNode.toString(), color);
              colorChainsMap.put(annotSets.getSelectedItem().toString(), tempColors);
            }
          }
        }

        // ok we need to remove Idsnow
        Iterator<List<Integer>> removeIter = idsToRemove.iterator();
        while (removeIter.hasNext()) {
          explicitCall = true;
          List<Integer> ids = removeIter.next();
          matches.remove(ids);
          String set = currentSet.equals(DEFAULT_ANNOTSET_NAME) ? null :
                       currentSet;
          matchesMap.put(set, matches);
          explicitCall = false;
        }
        explicitCall = true;
        document.getFeatures().put(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME,
                                   matchesMap);
        explicitCall = false;

        // here we need to find out the chainNodes those are no longer needed
        Iterator<CorefTreeNode> visitedListIter = visitedList.keySet().iterator();
        while (visitedListIter.hasNext()) {
          CorefTreeNode chainNode = visitedListIter.next();
          if (! visitedList.get(chainNode).booleanValue()) {
            // yes this should be deleted
            CorefTreeNode annotSetNode = corefAnnotationSetNodesMap.get(currentSet);

            // remove from the tree
            annotSetNode.remove(chainNode);
            corefAnnotationSetNodesMap.put(currentSet, annotSetNode);

            // ArrayList matches
            Map<CorefTreeNode, List<Integer>> newHashMap = corefChains.get(annotSetNode);
            newHashMap.remove(chainNode);
            corefChains.put(annotSetNode, newHashMap);

            // remove from the selections
            Map<String,Boolean> tempSelection = selectionChainsMap.get(
                currentSet);
            tempSelection.remove(chainNode.toString());
            selectionChainsMap.put(currentSet, tempSelection);

            // remove from the colors
            Map<String,Color> tempColors = colorChainsMap.get(currentSet);
            tempColors.remove(chainNode.toString());
            colorChainsMap.put(currentSet, currentColors);
          }
        }
      }
    }

    Iterator<Object> tempIter = annotSetsNamesMap.keySet().iterator();
    while (tempIter.hasNext()) {
      String currentSet = (String) tempIter.next();
      if (! annotSetsNamesMap.get(currentSet).booleanValue()) {
        String annotSet = currentSet;

        // find out the currently Selected annotationSetName
        @SuppressWarnings("unused")
        String annotSetName = (String) annotSets.getSelectedItem();
        
        // remove it from the main data store
        corefChains.remove(corefAnnotationSetNodesMap.get(annotSet));
        // remove it from the main data store
        corefAnnotationSetNodesMap.remove(annotSet);
        // remove it from the annotationSetModel (combobox)
        annotSetsModel.removeElement(annotSet);
        annotSets.setModel(annotSetsModel);
        annotSets.updateUI();
        // remove it from the colorChainMap
        colorChainsMap.remove(annotSet);
        // remove it from the selectionChainMap
        selectionChainsMap.remove(annotSet);
      }
    }

    if (annotSetsModel.getSize() == 0) {
      // no annotationSet to display
      // so set visible false
      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.setVisible(false);
      }
      corefTree.setVisible(false);

      // remove all highlights
      List<Object> allHighlights = new ArrayList<Object>();
      if(typeSpecificHighlightedTags != null)
        allHighlights.addAll(typeSpecificHighlightedTags);
      if(highlightedTags != null) {
        Iterator<List<Object>> iter = highlightedTags.values().iterator();
        while(iter.hasNext()) {
          List<Object> highlights = iter.next();
          allHighlights.addAll(highlights);
        }
      }
      for(int i=0;i<allHighlights.size();i++) {
        highlighter.removeHighlight(allHighlights.get(i));
      }

      //highlighter.removeAllHighlights();
      highlightedTags = null;
      typeSpecificHighlightedTags = null;
      return;
    }
    else {

      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.setVisible(false);
      }

      // remove all highlights
      List<Object> allHighlights = new ArrayList<Object>();
      if(typeSpecificHighlightedTags != null)
        allHighlights.addAll(typeSpecificHighlightedTags);
      if(highlightedTags != null) {
        Iterator<List<Object>> iter = highlightedTags.values().iterator();
        while(iter.hasNext()) {
          List<Object> highlights = iter.next();
          allHighlights.addAll(highlights);
        }
      }
      for (int i = 0; i < allHighlights.size(); i++) {
        highlighter.removeHighlight(allHighlights.get(i));
      }

      //highlighter.removeAllHighlights();
      highlightedTags = null;
      typeSpecificHighlightedTags = null;
      if (currentAnnotSet != null) {
        annotSets.setSelectedItem(currentAnnotSet);
        currentSelections = selectionChainsMap.get(currentAnnotSet);
        currentColors = colorChainsMap.get(currentAnnotSet);
        highlightAnnotations();

        showAnnotations.setSelected(currentShowAnnotationStatus);
        if (currentAnnotType != null)
          annotTypes.setSelectedItem(currentAnnotType);
        else
        if (annotTypes.getModel().getSize() > 0) {
          annotTypes.setSelectedIndex(0);
        }
      }
      else {
        explicitCall = false;
        annotSets.setSelectedIndex(0);
      }
    }
  }

  /**
   * ActionPerformed Activity
   * @param ae
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    // when annotationSet value changes
    if (ae.getSource() == annotSets) {
      if (!explicitCall) {
        annotSetSelectionChanged();
      }
    }
    else if (ae.getSource() == showAnnotations) {
      if (!explicitCall) {
        showTypeWiseAnnotations();
      }
    }
    else if (ae.getSource() == annotTypes) {
      if (!explicitCall) {
        if (typeSpecificHighlightedTags != null) {
          for (int i = 0; i < typeSpecificHighlightedTags.size(); i++) {
            highlighter.removeHighlight(typeSpecificHighlightedTags.get(i));
          }
        }
        typeSpecificHighlightedTags = null;
        showTypeWiseAnnotations();
      }
    }
  }

  /**
   * When user preses the show Toggle button, this will show up annotations
   * of selected Type from selected AnnotationSet
   */
  private void showTypeWiseAnnotations() {
    if (typeSpecificHighlightedTags == null) {
      highlightedTypeAnnots = new ArrayList<Annotation>();
      typeSpecificHighlightedTags = new ArrayList<Object>();
    }

    if (showAnnotations.isSelected()) {
      // get the annotationsSet and its type
      AnnotationSet set = getAnnotationSet( (String) annotSets.getSelectedItem());
      String type = (String) annotTypes.getSelectedItem();
      if (type == null) {
        try {
          JOptionPane.showMessageDialog(MainFrame.getInstance(),
                                        "No annotation type found to display");
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        showAnnotations.setSelected(false);
        return;
      }

      Color color = AnnotationSetsView.getColor(getAnnotationSet((String)annotSets.getSelectedItem()).getName(),type);
      if (type != null) {
        AnnotationSet typeSet = set.get(type);
        Iterator<Annotation> iter = typeSet.iterator();
        while (iter.hasNext()) {
          Annotation ann = iter.next();
          highlightedTypeAnnots.add(ann);
          try {
            typeSpecificHighlightedTags.add(highlighter.addHighlight(ann.
                getStartNode().
                getOffset().intValue(),
                ann.getEndNode().getOffset().intValue(),
                new DefaultHighlighter.
                DefaultHighlightPainter(color)));
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          //typeSpecificHighlightedTags.add(textView.addHighlight(ann, getAnnotationSet((String)annotSets.getSelectedItem()),color));
        }
      }
    }
    else {
      for (int i = 0; i < typeSpecificHighlightedTags.size(); i++) {
        //textView.removeHighlight(typeSpecificHighlightedTags.get(i));
        highlighter.removeHighlight(typeSpecificHighlightedTags.get(i));
      }
      typeSpecificHighlightedTags = new ArrayList<Object>();
      highlightedTypeAnnots = new ArrayList<Annotation>();
      highlightedTypeAnnotsOffsets = null;
    }

    // This is to make process faster.. instead of accessing each annotation and
    // its offset, we create an array with its annotation offsets to search faster
    Collections.sort(highlightedTypeAnnots, new gate.util.OffsetComparator());
    highlightedTypeAnnotsOffsets = new int[highlightedTypeAnnots.size() * 2];
    for (int i = 0, j = 0; j < highlightedTypeAnnots.size(); i += 2, j++) {
      Annotation ann1 = highlightedTypeAnnots.get(j);
      highlightedTypeAnnotsOffsets[i] = ann1.getStartNode().getOffset().
                                        intValue();
      highlightedTypeAnnotsOffsets[i +
          1] = ann1.getEndNode().getOffset().intValue();
    }

  }

  /**
   * Returns annotation Set
   */
  private AnnotationSet getAnnotationSet(String annotSet) {
    return (annotSet.equals(DEFAULT_ANNOTSET_NAME)) ? document.getAnnotations() :
        document.getAnnotations(annotSet);
  }

  /**
   * When annotationSet selection changes
   */
  private void annotSetSelectionChanged() {
    if (annotSets.getModel().getSize() == 0) {
      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.setVisible(false);
      }
      corefTree.setVisible(false);
      return;
    }

    System.out.println("calling from here");
    
    final String currentAnnotSet =
            annotSets.getSelectedItem() != null ? (String)annotSets
                    .getSelectedItem() : annotSets.getItemAt(0);
                    
    // get all the types of the currently Selected AnnotationSet
    AnnotationSet temp = getAnnotationSet(currentAnnotSet);
    Set<String> types = temp.getAllTypes();
    annotTypesModel = new DefaultComboBoxModel<String>();
    if (types != null) {
      annotTypesModel = new DefaultComboBoxModel<String>(types.toArray(new String[types.size()]));
    }
    
    SwingUtilities.invokeLater(new Runnable() {
      
      @Override
      public void run() {
        annotTypes.setModel(annotTypesModel);
        annotTypes.updateUI();

        // and redraw the CorefTree
        if (rootNode.getChildCount() > 0)
          rootNode.removeAllChildren();

        CorefTreeNode annotSetNode = corefAnnotationSetNodesMap.get(currentAnnotSet);

        if (annotSetNode != null) {
          rootNode.add(annotSetNode);
          currentSelections = selectionChainsMap.get(currentAnnotSet);
          currentColors = colorChainsMap.get(currentAnnotSet);
          if (!corefTree.isVisible()) {
            if (popupWindow != null && popupWindow.isVisible()) {
              popupWindow.setVisible(false);
            }
            corefTree.setVisible(true);
          }
          corefTree.repaint();
          corefTree.updateUI();

        }
        else {
          corefTree.setVisible(false);
        }
    
      }
    });
  }

  /**
   * This will initialise the data
   */
  private void initData() {

    rootNode = new CorefTreeNode("Co-reference Data", true,
                                 CorefTreeNode.ROOT_NODE);
    corefChains = new HashMap<CorefTreeNode, Map<CorefTreeNode, List<Integer>>>();
    selectionChainsMap = new HashMap<String, Map<String,Boolean>>();
    currentSelections = new HashMap<String, Boolean>();
    colorChainsMap = new HashMap<String, Map<String,Color>>();
    currentColors = new HashMap<String, Color>();
    corefAnnotationSetNodesMap = new HashMap<String, CorefTreeNode>();

    // now we need to findout the chains
    // for the defaultAnnotationSet
    CorefTreeNode annotSetNode = createChain(document.getAnnotations(), true);
    if (annotSetNode != null) {
      corefAnnotationSetNodesMap.put(DEFAULT_ANNOTSET_NAME, annotSetNode);
    }

    // and for the rest AnnotationSets
    Map<String,AnnotationSet> annotSets = document.getNamedAnnotationSets();
    if (annotSets != null) {
      Iterator<String> annotSetsIter = annotSets.keySet().iterator();
      while (annotSetsIter.hasNext()) {
        String annotSetName = annotSetsIter.next();
        annotSetNode = createChain(document.getAnnotations(annotSetName), false);
        if (annotSetNode != null) {
          corefAnnotationSetNodesMap.put(annotSetName, annotSetNode);
        }
      }
    }
  }

  private CorefTreeNode createAnnotSetNode(AnnotationSet set, boolean isDefaultSet) {
    // create the node for setName
    String setName = isDefaultSet ? DEFAULT_ANNOTSET_NAME : set.getName();
    CorefTreeNode annotSetNode = new CorefTreeNode(setName, true,
        CorefTreeNode.ANNOTSET_NODE);

    // see if this setName available in the annotSets
    boolean found = false;
    for (int i = 0; i < annotSets.getModel().getSize(); i++) {
      if ( annotSets.getModel().getElementAt(i).equals(setName)) {
        found = true;
        break;
      }
    }
    if (!found) {
      explicitCall = true;
      annotSets.addItem(setName);
      explicitCall = false;
    }

    // the internal datastructure
    Map<CorefTreeNode,List<Integer>> chainLinks = new HashMap<CorefTreeNode, List<Integer>>();
    Map<String,Boolean> selectionMap = new HashMap<String,Boolean>();
    Map<String,Color> colorMap = new HashMap<String,Color>();

    corefChains.put(annotSetNode, chainLinks);
    selectionChainsMap.put(setName, selectionMap);
    colorChainsMap.put(setName, colorMap);
    return annotSetNode;

  }

  /**
   * Creates the internal data structure
   * @param set
   */
  @SuppressWarnings("unchecked")
  private CorefTreeNode createChain(AnnotationSet set, boolean isDefaultSet) {

    // create the node for setName
    String setName = isDefaultSet ? DEFAULT_ANNOTSET_NAME : set.getName();
    CorefTreeNode annotSetNode = new CorefTreeNode(setName, true,
        CorefTreeNode.ANNOTSET_NODE);

    // see if this setName available in the annotSets
    boolean found = false;
    for (int i = 0; i < annotSets.getModel().getSize(); i++) {
      if ( annotSets.getModel().getElementAt(i).equals(setName)) {
        found = true;
        break;
      }
    }
    if (!found) {
      explicitCall = true;
      annotSets.addItem(setName);
      explicitCall = false;
    }

    // the internal datastructure
    Map<CorefTreeNode, List<Integer>> chainLinks = new HashMap<CorefTreeNode, List<Integer>>();
    Map<String, Boolean> selectionMap = new HashMap<String, Boolean>();
    Map<String, Color> colorMap = new HashMap<String, Color>();

    // map for all the annotations with matches feature in it
    Map<String,List<List<Integer>>> matchesMap = null;
    Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
    if(matchesMapObject instanceof Map) {
      matchesMap = (Map<String,List<List<Integer>>>) matchesMapObject;
    }


    // what if this map is null
    if (matchesMap == null) {
      corefChains.put(annotSetNode, chainLinks);
      selectionChainsMap.put(setName, selectionMap);
      colorChainsMap.put(setName, colorMap);
      return annotSetNode;
    }

    //AnnotationSetName --> List of ArrayLists
    //each ArrayList contains Ids of related annotations
    List<List<Integer>> matches1 = matchesMap.get(isDefaultSet ? null :
        setName);
    if (matches1 == null) {
      corefChains.put(annotSetNode, chainLinks);
      selectionChainsMap.put(setName, selectionMap);
      colorChainsMap.put(setName, colorMap);
      return annotSetNode;
    }

    Iterator<List<Integer>> tempIter = matches1.iterator();

    while (tempIter.hasNext()) {
      List<Integer> matches = tempIter.next();
      if (matches == null)
        matches = new ArrayList<Integer>();

      if (matches.size() > 0 && set.size() > 0) {

        String longestString = getString(findOutTheLongestAnnotation(matches,
            set));
        // so this should become one of the tree node
        CorefTreeNode chainNode = new CorefTreeNode(longestString, false,
            CorefTreeNode.CHAIN_NODE);
        // and add it under the topNode
        annotSetNode.add(chainNode);

        // chainNode --> All related annotIds
        chainLinks.put(chainNode, matches);
        selectionMap.put(chainNode.toString(), new Boolean(false));
        // and generate the color for this chainNode
        float components[] = colorGenerator.getNextColor().getComponents(null);
        Color color = new Color(components[0],
                                components[1],
                                components[2],
                                0.5f);
        colorMap.put(chainNode.toString(), color);
      }
    }

    corefChains.put(annotSetNode, chainLinks);
    selectionChainsMap.put(setName, selectionMap);
    colorChainsMap.put(setName, colorMap);
    return annotSetNode;
  }

  /**
   * Given an annotation, this method returns the string of that annotation
   */
  public String getString(Annotation ann) {
  	return document.getContent().toString().substring(ann.
          getStartNode().getOffset().intValue(),
          ann.getEndNode().getOffset().intValue()
          ).replaceAll("\\r\\n|\\r|\\n", " ");
  }

  /**
   * Removes the reference of this annotation from the current chain.
   * @param annot annotation to remove
   * @param chainHead co-reference chain to modify
   */
  @SuppressWarnings("unchecked")
  public void removeChainReference(Annotation annot, CorefTreeNode chainHead) {

    // so we would find out the matches
    List<Integer> ids = corefChains.get(corefAnnotationSetNodesMap.get(
    annotSets.getSelectedItem())).get(chainHead);

   String currentSet = (String) annotSets.getSelectedItem();
   currentSet = (currentSet.equals(DEFAULT_ANNOTSET_NAME)) ? null : currentSet;

    // we need to update the Co-reference document feature
    Map<String, List<List<Integer>>> matchesMap = null;
    List<List<Integer>> matches = null;
    Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
    if(matchesMapObject instanceof Map) {
      matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;
      matches = matchesMap.get(currentSet);
    } else {
      matchesMap = new HashMap<String, List<List<Integer>>>();
    }

    if (matches == null)
      matches = new ArrayList<List<Integer>>();

    int index = matches.indexOf(ids);
    if (index != -1) {
      // yes found
      ids.remove(annot.getId());
      
      //Annotation ann = findOutTheLongestAnnotation(ids,getAnnotationSet( (String) annotSets.getSelectedItem()));

      matches.set(index, ids);
      matchesMap.put(currentSet, matches);
      document.getFeatures().put(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME,
                                 matchesMap);
    }
  }

  /**
   * Given an annotation, this will find out the chainHead
   */
  private CorefTreeNode findOutTheChainHead(Annotation ann, String set) {
    Map<CorefTreeNode, List<Integer>> chains = corefChains.get(corefAnnotationSetNodesMap.get(
        set));
    if (chains == null)
      return null;
    Iterator<CorefTreeNode> iter = chains.keySet().iterator();
    while (iter.hasNext()) {
      CorefTreeNode head = iter.next();
      if (chains.get(head).contains(ann.getId())) {
        return head;
      }
    }
    return null;
  }

  /**
   * This methods highlights the annotations
   */
  public void highlightAnnotations() {

    if (highlightedTags == null) {
      highlightedTags = new HashMap<CorefTreeNode, List<Object>>();
      highlightedChainAnnots = new ArrayList<Annotation>();
    }

    AnnotationSet annotSet = getAnnotationSet( (String) annotSets.
                                              getSelectedItem());
    CorefTreeNode annotSetNode = corefAnnotationSetNodesMap.get(annotSets.getSelectedItem());
    if (annotSetNode == null) {
      return;
    }
    Map<CorefTreeNode,List<Integer>> chainMap = corefChains.get(annotSetNode);
    Iterator<CorefTreeNode> iter = chainMap.keySet().iterator();

    while (iter.hasNext()) {
      CorefTreeNode currentNode = iter.next();
      if (currentSelections.get(currentNode.toString()).
          booleanValue()) {
        if (!highlightedTags.containsKey(currentNode)) {
          // find out the arrayList
          List<Integer> ids = chainMap.get(currentNode);
          ArrayList<Object> highlighTag = new ArrayList<Object>();
          if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
              Annotation ann = annotSet.get(ids.get(i));
              highlightedChainAnnots.add(ann);
              Color color = currentColors.get(currentNode.toString());
              try {
                highlighTag.add(highlighter.addHighlight(ann.getStartNode().
                    getOffset().intValue(),
                    ann.getEndNode().getOffset().intValue(),
                    new DefaultHighlighter.
                    DefaultHighlightPainter(color)));
              }
              catch (Exception e) {
                e.printStackTrace();
              }
              //highlighTag.add(textView.addHighlight(ann, getAnnotationSet((String) annotSets.getSelectedItem()), color));
            }
            highlightedTags.put(currentNode, highlighTag);
          }
        }
      }
      else {
        if (highlightedTags.containsKey(currentNode)) {
          List<Object> highlights = highlightedTags.get(currentNode);
          for (int i = 0; i < highlights.size(); i++) {
            //textView.removeHighlight(highlights.get(i));
            highlighter.removeHighlight(highlights.get(i));
          }
          highlightedTags.remove(currentNode);
          List<Integer> ids = chainMap.get(currentNode);
          if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
              Annotation ann = annotSet.get(ids.get(i));
              highlightedChainAnnots.remove(ann);
            }
          }
        }
      }
    }

    // This is to make process faster.. instead of accessing each annotation and
    // its offset, we create an array with its annotation offsets to search faster
    Collections.sort(highlightedChainAnnots, new gate.util.OffsetComparator());
    highlightedChainAnnotsOffsets = new int[highlightedChainAnnots.size() * 2];
    for (int i = 0, j = 0; j < highlightedChainAnnots.size(); i += 2, j++) {
      Annotation ann1 = highlightedChainAnnots.get(j);
      highlightedChainAnnotsOffsets[i] = ann1.getStartNode().getOffset().
                                         intValue();
      highlightedChainAnnotsOffsets[i +
          1] = ann1.getEndNode().getOffset().intValue();
    }
  }

  @Override
  protected void registerHooks() {
    textPane.addMouseListener(textPaneMouseListener);
    textPane.addMouseMotionListener(textPaneMouseListener);

  }

  @Override
  protected void unregisterHooks() {
    textPane.removeMouseListener(textPaneMouseListener);
    textPane.removeMouseMotionListener(textPaneMouseListener);
  }

  @Override
  public Component getGUI() {
    return mainPanel;
  }

  @Override
  public int getType() {
    return VERTICAL;
  }

  //**********************************************
   // MouseListener and MouseMotionListener Methods
   //***********************************************

    protected class TextPaneMouseListener
        extends MouseInputAdapter {

      public TextPaneMouseListener() {
        chainToolTipTimer.setRepeats(false);
        newCorefActionTimer.setRepeats(false);
      }

      @Override
      public void mouseMoved(MouseEvent me) {
        int textLocation = textPane.viewToModel(me.getPoint());
        chainToolTipAction.setTextLocation(textLocation);
        chainToolTipAction.setMousePointer(me.getPoint());
        chainToolTipTimer.restart();

        newCorefAction.setTextLocation(textLocation);
        newCorefAction.setMousePointer(me.getPoint());
        newCorefActionTimer.restart();
      }
    }

  public void mouseClicked(MouseEvent me) {
    if (popupWindow != null && popupWindow.isVisible()) {
      popupWindow.setVisible(false);
    }
  }

  public CorefTreeNode findOutChainNode(String chainNodeString, String set) {
    if (corefChains == null || corefAnnotationSetNodesMap == null) {
      return null;
    }
    Map<CorefTreeNode, List<Integer>> chains = corefChains.get(corefAnnotationSetNodesMap.get(set));
    if (chains == null) {
      return null;
    }
    Iterator<CorefTreeNode> iter = chains.keySet().iterator();
    while (iter.hasNext()) {
      CorefTreeNode currentNode = iter.next();
      if (currentNode.toString().equals(chainNodeString))
        return currentNode;
    }
    return null;
  }

  /**
   * When user hovers over the annotations which have been highlighted by
   * show button
   */
  protected class NewCorefAction
      implements ActionListener {

    int textLocation;
    Point mousePoint;
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel subPanel = new JPanel();
    String field = "";
    JButton add = new JButton("OK");
    JButton cancel = new JButton("Cancel");
    JComboBox<String> list = new JComboBox<String>();
    JPanel mainPanel = new JPanel();
    JPopupMenu popup1 = new JPopupMenu();
    ListEditor listEditor = null;
    ComboBoxModel<String> model = new DefaultComboBoxModel<String>();
    boolean firstTime = true;

    public NewCorefAction() {
      popupWindow = new JWindow(SwingUtilities.getWindowAncestor(textView.
          getGUI()));
      popupWindow.setBackground(UIManager.getLookAndFeelDefaults().
                                getColor("ToolTip.background"));
      mainPanel.setLayout(new BorderLayout());
      mainPanel.setOpaque(true);
      mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      mainPanel.setBackground(UIManager.getLookAndFeelDefaults().
                              getColor("ToolTip.background"));
      popupWindow.setContentPane(mainPanel);

      panel.setLayout(new BorderLayout());
      panel.setOpaque(false);
      panel.add(new JScrollPane(list), BorderLayout.CENTER);

      subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      subPanel.add(add);
      subPanel.add(cancel);
      subPanel.setOpaque(false);
      panel.add(subPanel, BorderLayout.SOUTH);
      mainPanel.add(label, BorderLayout.NORTH);
      mainPanel.add(panel, BorderLayout.CENTER);

      // and finally load the data for the list
      AddAction action = new AddAction();
      add.addActionListener(action);
      cancel.addActionListener(action);
      listEditor = new ListEditor(action);
      list.setMaximumRowCount(5);
      list.setEditable(true);
      list.setEditor(listEditor);
      list.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
      int index = -1;
      if (highlightedChainAnnotsOffsets != null) {
        for (int i = 0; i < highlightedChainAnnotsOffsets.length; i += 2) {
          if (textLocation >= highlightedChainAnnotsOffsets[i] &&
              textLocation <= highlightedChainAnnotsOffsets[i + 1]) {
            index = (i == 0) ? i : i / 2;
            break;
          }
        }
      }

      // yes it is put on highlighted so show the annotationType
      if (highlightedChainAnnotsOffsets != null &&
          index < highlightedChainAnnotsOffsets.length && index >= 0) {
        return;
      }

      if (highlightedTypeAnnotsOffsets != null) {
        for (int i = 0; i < highlightedTypeAnnotsOffsets.length; i += 2) {
          if (textLocation >= highlightedTypeAnnotsOffsets[i] &&
              textLocation <= highlightedTypeAnnotsOffsets[i + 1]) {
            index = (i == 0) ? i : i / 2;
            break;
          }
        }
      }

      // yes it is put on highlighted so show the annotationType
      if (highlightedTypeAnnotsOffsets != null &&
          index < highlightedTypeAnnotsOffsets.length && index >= 0) {
        textPane.removeAll();
        annotToConsiderForChain = highlightedTypeAnnots.get(index);
        // now check if this annotation is already linked with something
        CorefTreeNode headNode = findOutTheChainHead(annotToConsiderForChain, (String) annotSets.getSelectedItem());
        if (headNode != null) {
          popup1 = new JPopupMenu();
          popup1.setBackground(UIManager.getLookAndFeelDefaults().
                               getColor("ToolTip.background"));
          JLabel label1 = new JLabel("Annotation co-referenced to : \"" +
                                     headNode.toString() + "\"");
          popup1.setLayout(new FlowLayout());
          popup1.add(label1);
          if (popupWindow != null && popupWindow.isVisible()) {
            popupWindow.setVisible(false);
          }
          popup1.setVisible(true);
          popup1.show(textPane, (int) mousePoint.getX(), (int) mousePoint.getY());
        }
        else {
          popupWindow.setVisible(false);
          List<String> set = new ArrayList<String>(currentSelections.keySet());
          Collections.sort(set);
          set.add(0, "[New Chain]");
          model = new DefaultComboBoxModel<String>(set.toArray(new String[set.size()]));
          list.setModel(model);
          listEditor.setItem("");
          label.setText("Add \"" + getString(annotToConsiderForChain) +
                        "\" to ");
          Point topLeft = textPane.getLocationOnScreen();
          int x = topLeft.x + (int) mousePoint.getX();
          int y = topLeft.y + (int) mousePoint.getY();
          popupWindow.setLocation(x, y);
          if (popup1.isVisible()) {
            popup1.setVisible(false);
          }
          popupWindow.pack();
          popupWindow.setVisible(true);
          listEditor.requestFocus();

          if (firstTime) {
            firstTime = false;
            popupWindow.pack();
            popupWindow.repaint();
            listEditor.requestFocus();
          }
        }
      }
    }

    public void setTextLocation(int textLocation) {
      this.textLocation = textLocation;
    }

    public void setMousePointer(Point point) {
      this.mousePoint = point;
    }

    /** Custom Editor for the ComboBox to enable key events */
    private class ListEditor
        extends KeyAdapter
        implements ComboBoxEditor {
      JTextField myField = new JTextField(20);
      AddAction action = null;
      Vector<String> myList = new Vector<String>();

      public ListEditor(AddAction action) {
        this.action = action;
        myField.addKeyListener(this);
      }

      @Override
      public void addActionListener(ActionListener al) {
        myField.addActionListener(al);
      }

      @Override
      public void removeActionListener(ActionListener al) {
        myField.removeActionListener(al);
      }

      @Override
      public Component getEditorComponent() {
        return myField;
      }

      @Override
      public Object getItem() {
        return myField.getText();
      }

      @Override
      public void selectAll() {
        if (myField.getText() != null && myField.getText().length() > 0) {
          myField.setSelectionStart(0);
          myField.setSelectionEnd(myField.getText().length());
        }
      }

      @Override
      public void setItem(Object item) {
        myField.setText( (String) item);
        field = myField.getText();
      }

      public void requestFocus() {
        myField.requestFocus();
      }

      @Override
      public void keyReleased(KeyEvent ke) {
        if (myField.getText() == null) {
          myField.setText("");
          field = myField.getText();
        }

        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
          if (myList.size() == 1) {
            myField.setText( myList.get(0));
          }
          else if (list.getSelectedIndex() < list.getModel().getSize() - 1) {
            list.setSelectedIndex(list.getSelectedIndex());
            myField.setText( (String) list.getSelectedItem());
          }
          field = myField.getText();
          myField.requestFocus();
          return;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_UP) {
          if (list.getSelectedIndex() > 0) {
            list.setSelectedIndex(list.getSelectedIndex());
          }
          myField.setText( (String) list.getSelectedItem());
          field = myField.getText();
          return;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
          field = myField.getText();
          action.actionPerformed(new ActionEvent(add,
                                                 ActionEvent.ACTION_PERFORMED,
                                                 "add"));
          return;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        }
        else if (Character.isJavaIdentifierPart(ke.getKeyChar()) ||
                 Character.isSpaceChar(ke.getKeyChar()) ||
                 Character.isDefined(ke.getKeyChar())) {
        }
        else {
          return;
        }

        String startWith = myField.getText();
        myList = new Vector<String>();
        ArrayList<String> set = new ArrayList<String>(currentSelections.keySet());
        Collections.sort(set);
        set.add(0, "[New Chain]");
        boolean first = true;
        for (int i = 0; i < set.size(); i++) {
          String currString = set.get(i);
          if (currString.toLowerCase().startsWith(startWith.toLowerCase())) {
            if (first) {
              myField.setText(currString.substring(0, startWith.length()));
              first = false;
            }
            myList.add(currString);
          }
        }
        ComboBoxModel<String> model = new DefaultComboBoxModel<String>(myList);
        list.setModel(model);
        myField.setText(startWith);
        field = myField.getText();
        list.showPopup();
      }
    }

    private class AddAction
        extends AbstractAction {
      @SuppressWarnings("unchecked")
      @Override
      public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cancel) {
          popupWindow.setVisible(false);
          return;
        }
        else if (ae.getSource() == add) {
          if (field.length() == 0) {
            try {
              JOptionPane.showMessageDialog(MainFrame.getInstance(),
                                            "No Chain Selected",
                                            "New Chain - Error",
                                            JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
            return;
          }
          else {
            // we want to add this
            // now first find out the annotation
            Annotation ann = annotToConsiderForChain;
            if (ann == null)
              return;
            // yes it is available
            // find out the CorefTreeNode for the chain under which it is to be inserted
            if (field.equals("[New Chain]")) {
              // we want to add this
              // now first find out the annotation
              
              CorefTreeNode chainNode = findOutChainNode(getString(ann), (String) annotSets.getSelectedItem());
              if (chainNode != null) {
                try {
                  JOptionPane.showMessageDialog(MainFrame.getInstance(),
                                                "Chain with " + getString(ann) +
                                                " title already exists",
                                                "New Chain - Error",
                                                JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
                return;
              }

              popupWindow.setVisible(false);

              String currentSet = (String) annotSets.getSelectedItem();
              currentSet = (currentSet.equals(DEFAULT_ANNOTSET_NAME)) ? null :
                           currentSet;

              Map<String, List<List<Integer>>> matchesMap = null;
              Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
              if(matchesMapObject instanceof Map) {
                matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;
              }

              if (matchesMap == null) {
                matchesMap = new HashMap<String, List<List<Integer>>>();
              }

              List<List<Integer>> matches = matchesMap.get(currentSet);
              ArrayList<Integer> tempList = new ArrayList<Integer>();
              tempList.add(ann.getId());
              if (matches == null)
                matches = new ArrayList<List<Integer>>();
              matches.add(tempList);
              matchesMap.put(currentSet, matches);
              document.getFeatures().put(ANNIEConstants.
                                         DOCUMENT_COREF_FEATURE_NAME,
                                         matchesMap);
              return;
            }

            CorefTreeNode chainNode = findOutChainNode(field, (String) annotSets.getSelectedItem());
            Map<CorefTreeNode, List<Integer>> chains = corefChains.get(corefAnnotationSetNodesMap.get(
        annotSets.getSelectedItem()));
            if (chainNode == null) {
              try {
                JOptionPane.showMessageDialog(MainFrame.getInstance(),
                                              "Incorrect Chain Title",
                                              "New Chain - Error",
                                              JOptionPane.ERROR_MESSAGE);
              }
              catch (Exception e) {
                e.printStackTrace();
              }
              return;
            }
            popupWindow.setVisible(false);
            List<Integer> ids = chains.get(chainNode);

            Map<String, List<List<Integer>>> matchesMap = null;
            Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
            if(matchesMapObject instanceof Map) {
              matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;
            }

            if (matchesMap == null) {
              matchesMap = new HashMap<String, List<List<Integer>>>();
            }
            String currentSet = (String) annotSets.getSelectedItem();
            currentSet = (currentSet.equals(DEFAULT_ANNOTSET_NAME)) ? null :
                         currentSet;
            List<List<Integer>> matches = matchesMap.get(currentSet);
            if (matches == null)
              matches = new ArrayList<List<Integer>>();
            int index = matches.indexOf(ids);
            if (index != -1) {
              List<Integer> tempIds = matches.get(index);
              tempIds.add(ann.getId());
              matches.set(index, tempIds);
              matchesMap.put(currentSet, matches);
              document.getFeatures().put(ANNIEConstants.
                                         DOCUMENT_COREF_FEATURE_NAME,
                                         matchesMap);
            }
            return;
          }
        }
      }
    }
  }

  /** When user hovers over the chainnodes */
  protected class ChainToolTipAction
      extends AbstractAction {

    int textLocation;
    Point mousePoint;
    JPopupMenu popup = new JPopupMenu();

    public ChainToolTipAction() {
      popup.setBackground(UIManager.getLookAndFeelDefaults().
                          getColor("ToolTip.background"));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

      int index = -1;
      if (highlightedChainAnnotsOffsets != null) {
        for (int i = 0; i < highlightedChainAnnotsOffsets.length; i += 2) {
          if (textLocation >= highlightedChainAnnotsOffsets[i] &&
              textLocation <= highlightedChainAnnotsOffsets[i + 1]) {
            index = (i == 0) ? i : i / 2;
            break;
          }
        }
      }

      // yes it is put on highlighted so show the annotationType
      if (highlightedChainAnnotsOffsets != null &&
          index < highlightedChainAnnotsOffsets.length && index >= 0) {

        if (popupWindow != null && popupWindow.isVisible()) {
          popupWindow.setVisible(false);
        }

        popup.setVisible(false);
        popup.removeAll();
        final int tempIndex = index;
        CorefTreeNode chainHead = findOutTheChainHead( highlightedChainAnnots.get(index), (String) annotSets.getSelectedItem());
        final HashMap<String, CorefTreeNode> tempMap = new HashMap<String, CorefTreeNode>();
        popup.setLayout(new FlowLayout(FlowLayout.LEFT));
        if (chainHead != null) {
          JPanel tempPanel = new JPanel();
          tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
          tempPanel.add(new JLabel(chainHead.toString()));
          tempPanel.setBackground(UIManager.getLookAndFeelDefaults().
                                  getColor("ToolTip.background"));
          final JButton deleteButton = new JButton("Delete");
          tempPanel.add(deleteButton);
          popup.add(tempPanel);
          deleteButton.setActionCommand(chainHead.toString());
          tempMap.put(chainHead.toString(), chainHead);
          deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              try {
                int confirm = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
                    "Are you sure?", "Removing reference...",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                  popup.setVisible(false);
                  // remove it
                  removeChainReference( highlightedChainAnnots.get(
                      tempIndex),
                      tempMap.get(deleteButton.getActionCommand()));
                }
              }
              catch (Exception e1) {
                e1.printStackTrace();
              }
            }
          });
        }
        //label.setText("Remove \""+getString((Annotation) highlightedChainAnnots.get(index)) + "\" from \""+ findOutTheChainHead((Annotation) highlightedChainAnnots.get(index)).toString()+"\"");
        popup.revalidate();
        if (popupWindow != null && popupWindow.isVisible()) {
          popupWindow.setVisible(false);
        }
        popup.setVisible(true);
        popup.show(textPane, (int) mousePoint.getX(), (int) mousePoint.getY());
      }
    }

    public void setTextLocation(int textLocation) {
      this.textLocation = textLocation;
    }

    public void setMousePointer(Point point) {
      this.mousePoint = point;
    }

  }

  // Class that represents each individual tree node in the corefTree
  protected class CorefTreeNode
      extends DefaultMutableTreeNode {
    public final static int ROOT_NODE = 0;
    public final static int ANNOTSET_NODE = 1;
    public final static int CHAIN_NODE = 2;

    private int type;

    public CorefTreeNode(Object value, boolean allowsChildren, int type) {
      super(value, allowsChildren);
      this.type = type;
    }

    public int getType() {
      return this.type;
    }

  }

  /**
   * Action for mouseClick on the Tree
   */
  protected class CorefTreeMouseListener
      extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent me) { }
    @Override
    public void mouseReleased(MouseEvent me) { }

    @Override
    public void mousePressed(MouseEvent me) {
      if (popupWindow != null && popupWindow.isVisible()) {
        popupWindow.setVisible(false);
      }
      textPane.removeAll();
      // ok now find out the currently selected node
      int x = me.getX();
      int y = me.getY();
      int row = corefTree.getRowForLocation(x, y);
      TreePath path = corefTree.getPathForRow(row);

      if (path != null) {
        final CorefTreeNode node = (CorefTreeNode) path.
                                   getLastPathComponent();

        // if it only chainNode
        if (node.getType() != CorefTreeNode.CHAIN_NODE) {
          return;
        }

        // see if user clicked the right click
        if (SwingUtilities.isRightMouseButton(me)) {
          // it is right click
          // we need to show the popup window
          final JPopupMenu popup = new JPopupMenu();
          JButton delete = new JButton("Delete");
          delete.setToolTipText("Delete Chain");
          ToolTipManager.sharedInstance().registerComponent(delete);
          JButton cancel = new JButton("Close");
          cancel.setToolTipText("Closes this popup");
          JButton changeColor = new JButton("Change Color");
          changeColor.setToolTipText("Changes Color");
          ToolTipManager.sharedInstance().registerComponent(cancel);
          ToolTipManager.sharedInstance().registerComponent(changeColor);
          JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
          panel.setOpaque(false);
          panel.add(changeColor);
          panel.add(delete);
          panel.add(cancel);
          popup.setLayout(new BorderLayout());
          popup.setOpaque(true);
          popup.setBackground(UIManager.getLookAndFeelDefaults().
                              getColor("ToolTip.background"));
          popup.add(new JLabel("Chain \"" + node.toString() + "\""),
                    BorderLayout.NORTH);
          popup.add(panel, BorderLayout.SOUTH);

          changeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              String currentAnnotSet = (String) annotSets.getSelectedItem();
              currentColors = colorChainsMap.get(currentAnnotSet);
              Color colour = currentColors.get(node.toString());
              Color col = JColorChooser.showDialog(getGUI(),
                  "Select colour for \"" + node.toString() + "\"",
                  colour);
              if (col != null) {
                Color colAlpha = new Color(col.getRed(), col.getGreen(),
                                           col.getBlue(), 128);

                // make change in the datastructures
                currentColors.put(node.toString(),colAlpha);
                colorChainsMap.put(currentAnnotSet, currentColors);
                // and redraw the tree
                corefTree.repaint();

                // remove all highlights
                List<Object> allHighlights = new ArrayList<Object>();
                if(typeSpecificHighlightedTags != null)
                  allHighlights.addAll(typeSpecificHighlightedTags);
                if(highlightedTags != null) {
                  Iterator<List<Object>> iter = highlightedTags.values().iterator();
                  while(iter.hasNext()) {
                    List<Object> highlights = iter.next();
                    allHighlights.addAll(highlights);
                  }
                }
                for (int i = 0; i < allHighlights.size(); i++) {
                  highlighter.removeHighlight(allHighlights.get(i));
                }

                //highlighter.removeAllHighlights();
                highlightedTags = null;
                highlightAnnotations();
                typeSpecificHighlightedTags = null;
                showTypeWiseAnnotations();
              }
              popup.setVisible(false);
            }
          });

          delete.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent ae) {
              // get the ids of current chainNode
              Map<CorefTreeNode, List<Integer>> chains = corefChains.get(corefAnnotationSetNodesMap.get(
         annotSets.getSelectedItem()));
              List<Integer> ids = chains.get(node);

              String currentSet = (String) annotSets.getSelectedItem();
              currentSet = (currentSet.equals(DEFAULT_ANNOTSET_NAME)) ? null : currentSet;

              // now search this in the document feature map
              Map<String, List<List<Integer>>> matchesMap = null;
              List<List<Integer>> matches = null;

              Object matchesMapObject = document.getFeatures().get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME);
              if(matchesMapObject instanceof Map) {
                matchesMap = (Map<String, List<List<Integer>>>) matchesMapObject;
                matches = matchesMap.get(currentSet);
              }

              if(matchesMap == null) {
                matchesMap = new HashMap<String, List<List<Integer>>>();
              }

              if (matches == null)
                matches = new ArrayList<List<Integer>>();

              int index = matches.indexOf(ids);
              if (index != -1) {
                // yes found
                matches.remove(index);
                matchesMap.put(currentSet, matches);
                document.getFeatures().put(ANNIEConstants.
                                           DOCUMENT_COREF_FEATURE_NAME,
                                           matchesMap);
              }
              popup.setVisible(false);
            }
          });

          cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              popup.setVisible(false);
            }
          });
          popup.setVisible(true);
          popup.show(corefTree, x, y);
          return;
        }

        boolean isSelected = ! currentSelections.get(node.toString()).
                             booleanValue();
        currentSelections.put(node.toString(), new Boolean(isSelected));

        // so now we need to highlight all the stuff
        highlightAnnotations();
        corefTree.repaint();
        corefTree.updateUI();
        corefTree.repaint();
        corefTree.updateUI();

      }
    }
  }

  /**
   * Cell renderer to add the checkbox in the tree
   */
  protected class CorefTreeCellRenderer
      extends JPanel
      implements TreeCellRenderer {

    private JCheckBox check;
    private JLabel label;

    /**
     * Constructor.
     */
    public CorefTreeCellRenderer() {
      setOpaque(true);
      check = new JCheckBox();
      check.setBackground(Color.white);
      label = new JLabel();
      setLayout(new BorderLayout(5, 10));
      add(check, BorderLayout.WEST);
      add(label, BorderLayout.CENTER);
    }

    /**
     * Renderer class
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean isSelected,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {

      CorefTreeNode userObject = (CorefTreeNode) value;
      label.setText(userObject.toString());
      this.setSize(label.getWidth(),
                   label.getFontMetrics(label.getFont()).getHeight() * 2);
      tree.expandRow(row);
      if (userObject.getType() == CorefTreeNode.ROOT_NODE || userObject.getType() ==
          CorefTreeNode.ANNOTSET_NODE) {
        this.setBackground(Color.white);
        this.check.setVisible(false);
        return this;
      }
      else {
        this.setBackground(currentColors.get(userObject.toString()));
        check.setVisible(true);
        check.setBackground(Color.white);
      }

      // if node should be selected
      boolean selected = currentSelections.get(userObject.toString()).
                         booleanValue();
      check.setSelected(selected);
      return this;
    }
  }
}
