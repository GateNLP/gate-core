/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OntologyEditor.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.ResourceInstantiationException;
import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.InvalidValueException;
import gate.creole.ontology.Literal;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OConstants;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.ONodeID;
import gate.creole.ontology.OResource;
import gate.creole.ontology.ObjectProperty;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.OntologyModificationListener;
import gate.creole.ontology.RDFProperty;
import gate.event.GateEvent;
import gate.gui.MainFrame;
import gate.gui.ResizableVisualResource;
import gate.swing.XJTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


/**
 * The GUI for the Ontology Editor
 * 
 * @author niraj
 * 
 */
public class OntologyEditor extends AbstractVisualResource
                                                          implements
                                                          ResizableVisualResource,
                                                          OntologyModificationListener {

  private static final long serialVersionUID = 3257847701214345265L;

  /*
   * (non-Javadoc)
   * 
   * @see gate.creole.AbstractVisualResource#setTarget(java.lang.Object)
   */
  @Override
  public void setTarget(Object target) {
    this.ontology = (Ontology)target;
    selectedNodes = new ArrayList<DefaultMutableTreeNode>();
    detailsTableModel.setOntology(ontology);
    topClassAction.setOntology(ontology);
    subClassAction.setOntology(ontology);
    instanceAction.setOntology(ontology);
    annotationPropertyAction.setOntology(ontology);
    datatypePropertyAction.setOntology(ontology);
    objectPropertyAction.setOntology(ontology);
    symmetricPropertyAction.setOntology(ontology);
    transitivePropertyAction.setOntology(ontology);
    deleteOntoResourceAction.setOntology(ontology);
    restrictionAction.setOntology(ontology);
    ontology.removeOntologyModificationListener(this);
    rebuildModel();
    ontology.addOntologyModificationListener(this);
  }

  /**
   * Init method, that creates this object and returns this object as a
   * resource
   */
  @Override
  public Resource init() throws ResourceInstantiationException {
    super.init();
    initLocalData();
    initGUIComponents();
    initListeners();
    return this;
  }

  /**
   * Initialize the local data
   */
  protected void initLocalData() {
    itemComparator = new OntologyItemComparator();
    listeners = new ArrayList<TreeNodeSelectionListener>();
  }

  /**
   * Initialize the GUI Components
   */
  protected void initGUIComponents() {
    this.setLayout(new BorderLayout());
    mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    this.add(mainSplit, BorderLayout.CENTER);
    tabbedPane = new JTabbedPane();
    mainSplit.setLeftComponent(tabbedPane);

    rootNode = new DefaultMutableTreeNode(null, true);
    treeModel = new DefaultTreeModel(rootNode);
    tree = new JTree(treeModel);
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);
    tree.setCellRenderer(new OntoTreeCellRenderer());
    tree.getSelectionModel().setSelectionMode(
            TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    scroller = new JScrollPane(tree);

    // enable tooltips for the tree
    ToolTipManager.sharedInstance().registerComponent(tree);
    tabbedPane.addTab("Classes & Instances", scroller);
    scroller.setBorder(new TitledBorder("Classes and Instances"));
    // ----------------------------------------------

    propertyRootNode = new DefaultMutableTreeNode(null, true);
    propertyTreeModel = new DefaultTreeModel(propertyRootNode);
    propertyTree = new JTree(propertyTreeModel);
    propertyTree.setRootVisible(false);
    propertyTree.setShowsRootHandles(true);
    propertyTree.setCellRenderer(new OntoTreeCellRenderer());
    propertyTree.getSelectionModel().setSelectionMode(
            TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    propertyScroller = new JScrollPane(propertyTree);
    // enable tooltips for the tree
    ToolTipManager.sharedInstance().registerComponent(propertyTree);
    tabbedPane.addTab("Properties", propertyScroller);
    propertyScroller.setBorder(new TitledBorder("Properties"));
    // -----------------------------------------------
    detailsTableModel = new DetailsTableModel();
    // ----------------
    propertyDetailsTableModel = new PropertyDetailsTableModel();
    // ----------------
    detailsTable = new XJTable(detailsTableModel);
    ((XJTable)detailsTable).setSortable(false);
    DetailsTableCellRenderer renderer = new DetailsTableCellRenderer();
    detailsTable.getColumnModel().getColumn(DetailsTableModel.EXPANDED_COLUMN)
            .setCellRenderer(renderer);
    detailsTable.getColumnModel().getColumn(DetailsTableModel.LABEL_COLUMN)
            .setCellRenderer(renderer);
    detailsTable.getColumnModel().getColumn(DetailsTableModel.VALUE_COLUMN)
            .setCellRenderer(renderer);
    detailsTable.getColumnModel().getColumn(DetailsTableModel.DELETE_COLUMN)
            .setCellRenderer(renderer);

    detailsTable.setShowGrid(false);
    detailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    detailsTable.setColumnSelectionAllowed(true);
    detailsTable.setRowSelectionAllowed(true);
    detailsTable.setTableHeader(null);
    detailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    detailsTableScroller = new JScrollPane(detailsTable);
    detailsTableScroller.getViewport().setOpaque(true);
    detailsTableScroller.getViewport().setBackground(
            detailsTable.getBackground());
    mainSplit.setRightComponent(detailsTableScroller);

    // -------------------
    propertyDetailsTable = new XJTable(propertyDetailsTableModel);
    ((XJTable)propertyDetailsTable).setSortable(false);
    PropertyDetailsTableCellRenderer propertyRenderer = new PropertyDetailsTableCellRenderer(
            propertyDetailsTableModel);
    propertyDetailsTable.getColumnModel().getColumn(
            PropertyDetailsTableModel.EXPANDED_COLUMN).setCellRenderer(
            propertyRenderer);
    propertyDetailsTable.getColumnModel().getColumn(
            PropertyDetailsTableModel.LABEL_COLUMN).setCellRenderer(
            propertyRenderer);
    propertyDetailsTable.getColumnModel().getColumn(
            PropertyDetailsTableModel.VALUE_COLUMN).setCellRenderer(
            propertyRenderer);
    propertyDetailsTable.getColumnModel().getColumn(
            PropertyDetailsTableModel.DELETE_COLUMN).setCellRenderer(
            propertyRenderer);

    propertyDetailsTable.setShowGrid(false);
    propertyDetailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    propertyDetailsTable.setColumnSelectionAllowed(true);
    propertyDetailsTable.setRowSelectionAllowed(true);
    propertyDetailsTable.setTableHeader(null);
    propertyDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    propertyDetailsTableScroller = new JScrollPane(propertyDetailsTable);
    propertyDetailsTableScroller.getViewport().setOpaque(true);
    propertyDetailsTableScroller.getViewport().setBackground(
            propertyDetailsTable.getBackground());

    // --------------------

    toolBar = new JToolBar(JToolBar.HORIZONTAL);
    searchAction = new SearchAction("", MainFrame.getIcon("ontology-search"),
            this);
    search = new JButton(searchAction);
    search.setToolTipText("Advanced search in the ontology");


    topClassAction = new TopClassAction("", MainFrame
            .getIcon("ontology-topclass"));
    topClass = new JButton(topClassAction);
    topClass.setToolTipText("Add New Top Class");

    refreshOntologyBtn = 
      new JButton(MainFrame.getIcon("crystal-clear-action-reload-small"));
    refreshOntologyBtn.setToolTipText("Rebuilds the ontology tree");
    refreshOntologyBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        rebuildModel();
      }
    });
    
    subClassAction = new SubClassAction("", MainFrame
            .getIcon("ontology-subclass"));
    addTreeNodeSelectionListener(subClassAction);
    subClass = new JButton(subClassAction);
    subClass.setToolTipText("Add New Sub Class");
    subClass.setEnabled(false);

    instanceAction = new InstanceAction("", MainFrame
            .getIcon("ontology-instance"));
    addTreeNodeSelectionListener(instanceAction);
    instance = new JButton(instanceAction);
    instance.setToolTipText("Add New Instance");
    instance.setEnabled(false);

    annotationPropertyAction = new AnnotationPropertyAction("", MainFrame
            .getIcon("ontology-annotation-property"));
    annotationProperty = new JButton(annotationPropertyAction);
    annotationProperty.setToolTipText("Add New Annotation Property");
    annotationProperty.setEnabled(false);

    datatypePropertyAction = new DatatypePropertyAction("", MainFrame
            .getIcon("ontology-datatype-property"));
    addTreeNodeSelectionListener(datatypePropertyAction);
    datatypeProperty = new JButton(datatypePropertyAction);
    datatypeProperty.setToolTipText("Add New Datatype Property");
    datatypeProperty.setEnabled(false);

    objectPropertyAction = new ObjectPropertyAction("", MainFrame
            .getIcon("ontology-object-property"));
    addTreeNodeSelectionListener(objectPropertyAction);
    objectProperty = new JButton(objectPropertyAction);
    objectProperty.setToolTipText("Add New Object Property");
    objectProperty.setEnabled(false);

    symmetricPropertyAction = new SymmetricPropertyAction("", MainFrame
            .getIcon("ontology-symmetric-property"));
    addTreeNodeSelectionListener(symmetricPropertyAction);
    symmetricProperty = new JButton(symmetricPropertyAction);
    symmetricProperty.setToolTipText("Add New Symmetric Property");
    symmetricProperty.setEnabled(false);

    transitivePropertyAction = new TransitivePropertyAction("", MainFrame
            .getIcon("ontology-transitive-property"));
    addTreeNodeSelectionListener(transitivePropertyAction);
    transitiveProperty = new JButton(transitivePropertyAction);
    transitiveProperty.setToolTipText("Add New Transitive Property");
    transitiveProperty.setEnabled(false);

    deleteOntoResourceAction = new DeleteOntologyResourceAction("", MainFrame
            .getIcon("ontology-delete"));
    restrictionAction = new RestrictionAction("", MainFrame
            .getIcon("ontology-restriction"));

    addTreeNodeSelectionListener(deleteOntoResourceAction);
    delete = new JButton(deleteOntoResourceAction);
    delete.setToolTipText("Delete the selected nodes");
    delete.setEnabled(false);

    restriction = new JButton(restrictionAction);
    restriction.setToolTipText("Add New Restriction");
    restriction.setEnabled(false);
    
    toolBar.setFloatable(false);
    toolBar.add(topClass);
    toolBar.add(subClass);
    toolBar.add(restriction);
    toolBar.add(instance);
    toolBar.add(annotationProperty);
    toolBar.add(datatypeProperty);
    toolBar.add(objectProperty);
    toolBar.add(symmetricProperty);
    toolBar.add(transitiveProperty);
    toolBar.add(delete);
    toolBar.add(search);
    toolBar.add(refreshOntologyBtn);
    this.add(toolBar, BorderLayout.NORTH);
  }

  private void updateSelection(JTree treeToUse, Component componentToUpdate) {
    int[] selectedRows = treeToUse.getSelectionRows();
    if(selectedRows != null && selectedRows.length > 0) {
      selectedNodes.clear();
      for(int i = 0; i < selectedRows.length; i++) {
        DefaultMutableTreeNode node1 = (DefaultMutableTreeNode)treeToUse
                .getPathForRow(selectedRows[i]).getLastPathComponent();
        selectedNodes.add(node1);
      }
      if(treeToUse == tree) {
        detailsTableModel
                .setItem(((OResourceNode)(selectedNodes
                        .get(0)).getUserObject()).getResource());
      }
      else {
        propertyDetailsTableModel
                .setItem(((OResourceNode)(selectedNodes
                        .get(0)).getUserObject()).getResource());

      }
      enableDisableToolBarComponents();
      fireTreeNodeSelectionChanged(selectedNodes);
      if(treeToUse == tree)
        propertyTree.clearSelection();
      else tree.clearSelection();
    }
    mainSplit.setRightComponent(componentToUpdate);
    mainSplit.updateUI();
  }

  /**
   * Initializes various listeners
   */
  protected void initListeners() {
    tree.getSelectionModel().addTreeSelectionListener(
            new TreeSelectionListener() {
              @Override
              public void valueChanged(TreeSelectionEvent e) {
                updateSelection(tree, detailsTableScroller);
              }
            });
    propertyTree.getSelectionModel().addTreeSelectionListener(
            new TreeSelectionListener() {
              @Override
              public void valueChanged(TreeSelectionEvent e) {
                updateSelection(propertyTree, propertyDetailsTableScroller);
              }
            });

    mainSplit.addComponentListener(new ComponentListener() {
      @Override
      public void componentHidden(ComponentEvent e) {
      }

      @Override
      public void componentMoved(ComponentEvent e) {
      }

      @Override
      public void componentResized(ComponentEvent e) {
        mainSplit.setDividerLocation(0.4);
      }

      @Override
      public void componentShown(ComponentEvent e) {
      }
    });

    tree.addMouseListener(new MouseAdapter() {
      @Override
      @SuppressWarnings("deprecation")
      public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isRightMouseButton(me)) {
          if(selectedNodes == null || selectedNodes.size() != 1) return;
          final JPopupMenu menu = new JPopupMenu();
          final JMenu addProperty = new JMenu("Properties");
          final OResource candidate = ((OResourceNode)(selectedNodes
                  .get(0)).getUserObject()).getResource();
          menu.add(addProperty);
          final JMenuItem sameAs = new JMenuItem(candidate instanceof OClass
                  ? "Equivalent Class"
                  : "Same As Instance");
          menu.add(sameAs);
          final JMenuItem subClass = new JMenuItem("Add SubClass", MainFrame
                  .getIcon("ontology-subclass"));
          final JMenuItem instance = new JMenuItem("Add Instance", MainFrame
                  .getIcon("ontology-instance"));
          final JMenuItem delete = new JMenuItem("Delete", MainFrame
                  .getIcon("delete"));
          if(candidate instanceof OClass) {
            menu.add(subClass);
            menu.add(instance);

            // invoke new sub class action
            subClass.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                subClassAction.actionPerformed(ae);
              }
            });

            // invoke new instance action
            instance.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                instanceAction.actionPerformed(ae);
              }
            });

            // invoke same as action
            sameAs.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                Set<OClass> oclasses = ontology.getOClasses(false);
                ArrayList<OClass> classList = new ArrayList<OClass>();
                Iterator<OClass> classIter = oclasses.iterator();
                while(classIter.hasNext()) {
                  OClass aClass = classIter.next();
                  classList.add(aClass);
                }

                // the selected class shouldn't appear in the list
                classList.remove(candidate);
                ValuesSelectionAction vsa = new ValuesSelectionAction();
                String[] classArray = new String[classList.size()];
                for(int i = 0; i < classArray.length; i++) {
                  classArray[i] = classList.get(i).getONodeID().toString();
                }
                vsa.showGUI(candidate.getName() + " is equivalent to :",
                        classArray, new String[0], false, null);
                String[] selectedValues = vsa.getSelectedValues();
                for(int i = 0; i < selectedValues.length; i++) {
                  OClass byName = (OClass)
                    Utils.getOResourceFromMap(ontology,selectedValues[i]);
                  if(byName == null) continue;
                  ((OClass)candidate).setEquivalentClassAs(byName);
                }
                TreePath path = tree.getSelectionPath();
                tree.setSelectionRow(0);
                tree.setSelectionPath(path);
                return;
              }
            });
          }

          // same as action for OInstance
          if(candidate instanceof OInstance) {
            sameAs.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                Set<OInstance> instances = ontology.getOInstances();
                ArrayList<OInstance> instancesList = new ArrayList<OInstance>();
                Iterator<OInstance> instancesIter = instances.iterator();
                while(instancesIter.hasNext()) {
                  OInstance instance = instancesIter.next();
                  instancesList.add(instance);
                }
                instancesList.remove(candidate);
                ValuesSelectionAction vsa = new ValuesSelectionAction();
                String[] instancesArray = new String[instancesList.size()];
                for(int i = 0; i < instancesArray.length; i++) {
                  instancesArray[i] = instancesList.get(i).getONodeID().toString();
                }
                vsa.showGUI(candidate.getName() + " is same As :",
                        instancesArray, new String[0], false, null);
                String[] selectedValues = vsa.getSelectedValues();
                for(int i = 0; i < selectedValues.length; i++) {
                  OInstance byName = (OInstance)
                    Utils.getOResourceFromMap(ontology,selectedValues[i]);
                  if(byName == null) continue;
                  ((OInstance)candidate).setSameInstanceAs(byName);
                }
                TreePath path = tree.getSelectionPath();
                tree.setSelectionRow(0);
                tree.setSelectionPath(path);
                return;
              }
            });
          }

          // add the delete button here
          menu.add(delete);
          delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              deleteOntoResourceAction.actionPerformed(ae);
            }
          });

          int propertyCounter = 0;
          JMenu whereToAdd = addProperty;

          // finally add properties
          Set<RDFProperty> props = ontology.getPropertyDefinitions();
          Iterator<RDFProperty> iter = props.iterator();
          while(iter.hasNext()) {
            final RDFProperty p = iter.next();

            if(propertyCounter > 10) {
              JMenu newMenu = new JMenu("More >");
              whereToAdd.add(newMenu);
              whereToAdd = newMenu;
              propertyCounter = 0;
              whereToAdd.setEnabled(false);
            }

            // if property is an annotation property
            if(p instanceof AnnotationProperty) {
              JMenuItem item = new JMenuItem(new AnnotationPropertyValueAction(
                p.getName(), candidate, (AnnotationProperty) p));
              whereToAdd.setEnabled(true);
              whereToAdd.add(item);
              propertyCounter++;
              continue;
            }

            // if it is a datatype property
            if(candidate instanceof OInstance
            && p instanceof DatatypeProperty
            && p.isValidDomain(candidate)) {
              JMenuItem item = new JMenuItem(new DatatypePropertyValueAction(
                p.getName(), candidate, (DatatypeProperty) p));
              whereToAdd.add(item);
              whereToAdd.setEnabled(true);
              propertyCounter++;
              continue;
            }

            // if it is an object property
            if(candidate instanceof OInstance
            && p instanceof ObjectProperty
            && p.isValidDomain(candidate)) {
              JMenuItem item = new JMenuItem(new ObjectPropertyValueAction(
                p.getName(), candidate, (ObjectProperty) p, null));
              whereToAdd.add(item);
              whereToAdd.setEnabled(true);
              propertyCounter++;
              continue;
            }

          }
          menu.show(tree, me.getX(), me.getY());
          menu.setVisible(true);
        }
      }
    });

    propertyTree.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isRightMouseButton(me)) {
          if(selectedNodes == null || selectedNodes.size() != 1) return;
          final JPopupMenu menu = new JPopupMenu();
          final OResource candidate = ((OResourceNode)selectedNodes
                  .get(0).getUserObject()).getResource();
          final JMenuItem sameAs = new JMenuItem("Equivalent Property");
          final JMenuItem delete = new JMenuItem("Delete", MainFrame
                  .getIcon("delete"));
          final JCheckBoxMenuItem functional = new JCheckBoxMenuItem(
                  "Functional");
          final JCheckBoxMenuItem inverseFunctional = new JCheckBoxMenuItem(
                  "InverseFunctional");
          menu.add(sameAs);
          if(candidate instanceof AnnotationProperty) {
            return;
          }
          final Set<RDFProperty> props = new HashSet<RDFProperty>();
          if(candidate instanceof ObjectProperty) {
            props.addAll(ontology.getObjectProperties());
            functional.setSelected(((ObjectProperty)candidate).isFunctional());
            inverseFunctional.setSelected(((ObjectProperty)candidate)
                    .isInverseFunctional());
            functional.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                ((ObjectProperty)candidate).setFunctional(functional
                        .isSelected());
              }
            });
            inverseFunctional.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
                ((ObjectProperty)candidate)
                        .setInverseFunctional(inverseFunctional.isSelected());
              }
            });
            menu.add(functional);
            menu.add(inverseFunctional);
          }
          else if(candidate instanceof DatatypeProperty) {
            props.addAll(ontology.getDatatypeProperties());
          }
          else {
            props.addAll(ontology.getRDFProperties());
          }
          sameAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              props.remove(candidate);
              Iterator<RDFProperty> iter = props.iterator();
              ValuesSelectionAction vsa = new ValuesSelectionAction();
              String[] propArray = new String[props.size()];
              for(int i = 0; i < propArray.length; i++) {
                propArray[i] = iter.next().getONodeID().toString();
              }
              vsa.showGUI(candidate.getName() + " is equivalent to :",
                      propArray, new String[0], false, null);
              String[] selectedValues = vsa.getSelectedValues();
              for(int i = 0; i < selectedValues.length; i++) {
                RDFProperty byName = (RDFProperty)
                  Utils.getOResourceFromMap(ontology,selectedValues[i]);
                if(byName == null) continue;
                ((RDFProperty)candidate).setEquivalentPropertyAs(byName);
              }
              TreePath path = propertyTree.getSelectionPath();
              propertyTree.setSelectionRow(0);
              propertyTree.setSelectionPath(path);
              return;
            }
          });

          menu.add(delete);
          delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              deleteOntoResourceAction.actionPerformed(ae);
            }
          });

          JMenu addProperty = new JMenu("Properties");
          Set<RDFProperty> rdfprops = ontology.getPropertyDefinitions();
          Iterator<RDFProperty> iter = rdfprops.iterator();
          menu.add(addProperty);
          
          JMenu whereToAdd = addProperty;
          int propertyCounter = 0;

          while(iter.hasNext()) {
            final RDFProperty p = iter.next();

            if(propertyCounter > 10) {
              JMenu newMenu = new JMenu("More >");
              whereToAdd.add(newMenu);
              whereToAdd = newMenu;
              propertyCounter = 0;
              whereToAdd.setEnabled(false);
            }

            if(p instanceof AnnotationProperty) {
              JMenuItem item = new JMenuItem(p.getName(), MainFrame
                      .getIcon("ontology-annotation-property"));
              whereToAdd.add(item);
              whereToAdd.setEnabled(true);
              propertyCounter++;

              item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                  String value = JOptionPane.showInputDialog(
                    MainFrame.getInstance(),
                    "Enter Value for property :" + p.getName());
                  if(value != null) {
                    candidate.addAnnotationPropertyValue((AnnotationProperty)p,
                            new Literal(value));
                  }
                  TreePath path = propertyTree.getSelectionPath();
                  propertyTree.setSelectionRow(0);
                  propertyTree.setSelectionPath(path);
                  return;
                }
              });
            }
          }
          menu.show(propertyTree, me.getX(), me.getY());
          menu.setVisible(true);
        }
      }
    });

    detailsTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isLeftMouseButton(me)) {
          int row = detailsTable.getSelectedRow();
          int column = detailsTable.getSelectedColumn();
          if(row == -1 || column == -1) { return; }
          Object value = detailsTableModel.getValueAt(
            row, DetailsTableModel.LABEL_COLUMN);
          OResource resource = detailsTableModel.getItem();

          if(value instanceof DetailsGroup) {
            if(column == DetailsTableModel.EXPANDED_COLUMN) {
              boolean expanded = ((DetailsGroup)value).isExpanded();
              ((DetailsGroup)value).setExpanded(!expanded);
              detailsTable.updateUI();
            }
          }

          else if(column == DetailsTableModel.LABEL_COLUMN
            && me.getClickCount() == 2) {

            OResource toSelect = null;
            JTree treeToSelectIn = null;

            if(value instanceof OClass) {
              toSelect = (OResource) value;
              treeToSelectIn = tree;
            }
            else if(value instanceof RDFProperty) {
              toSelect = (RDFProperty) value;
              treeToSelectIn = propertyTree;
            }
            else if(value instanceof PropertyValue) {
              toSelect = ((PropertyValue) value).getProperty();
              treeToSelectIn = propertyTree;
            }

            if(toSelect != null) {

              treeToSelectIn.setSelectionPath(new TreePath(uri2TreeNodesListMap
                      .get(toSelect.getONodeID().toString()).get(0).getPath()));
              treeToSelectIn.scrollPathToVisible(treeToSelectIn
                      .getSelectionPath());

              if(treeToSelectIn == tree) {
                tabbedPane.setSelectedComponent(scroller);
              }
              else {
                tabbedPane.setSelectedComponent(propertyScroller);
              }
            }
          }

          else if(value instanceof PropertyValue
            && column == DetailsTableModel.VALUE_COLUMN
            && me.getClickCount() == 2) {

            PropertyValue pv = (PropertyValue) value;
            RDFProperty p = pv.getProperty();

            // if it is an object property
            if(p instanceof ObjectProperty) {
              new ObjectPropertyValueAction("", resource,
                (ObjectProperty) p, (OInstance)pv.getValue())
                .actionPerformed(null);
            }
            // for the other types of data edit directly in cell
          }

          else if(value instanceof RDFProperty
            && column == DetailsTableModel.VALUE_COLUMN
            && me.getClickCount() == 2) {

            final RDFProperty property = (RDFProperty) value;
            if (property instanceof AnnotationProperty) {
              resource.addAnnotationPropertyValue(
                (AnnotationProperty) property, new Literal(""));
              detailsTableModel.setItem(resource);
              SwingUtilities.invokeLater(new Runnable() { @Override
              public void run() {
              for (int rowI = detailsTable.getRowCount()-1; rowI >= 0; rowI--) {
                if (detailsTable.getValueAt(rowI,
                    DetailsTableModel.VALUE_COLUMN).equals("")
                 && detailsTable.getValueAt(rowI,
                    DetailsTableModel.LABEL_COLUMN) instanceof PropertyValue
                 && ((PropertyValue)detailsTable.getValueAt(rowI,
                  DetailsTableModel.LABEL_COLUMN)).getProperty()
                  .equals(property)) {
                  detailsTable.editCellAt(rowI, DetailsTableModel.VALUE_COLUMN);
                  detailsTable.getEditorComponent().requestFocusInWindow();
                  break;
                }
              }
              }});
            }
            else if (property instanceof DatatypeProperty
                  && resource instanceof OInstance
            ) {
              String type = ((DatatypeProperty)property)
                .getDataType().getXmlSchemaURIString();
              final String literalValue;
              if (type.endsWith("boolean")) {
                literalValue = "true";
              } else if (type.endsWith("date")) {
                literalValue = "01/01/2000";
              } else if (type.endsWith("time")) {
                literalValue = "12:00";
              } else if (type.endsWith("decimal")
                      || type.endsWith("double")
                      || type.endsWith("float")) {
                literalValue = "1.0";
              } else if (type.endsWith("byte")
                      || type.endsWith("unsignedByte")) {
                literalValue = "F";
              } else if (type.endsWith("int")
                      || type.endsWith("integer")
                      || type.endsWith("long")
                      || type.endsWith("unsignedInt")
                      || type.endsWith("unsignedShort")
                      || type.endsWith("unsignedLong")
                      || type.endsWith("nonNegativeInteger")
                      || type.endsWith("positiveInteger")
                      || type.endsWith("short")
                      || type.endsWith("duration")) {
                literalValue = "1";
              } else if (type.endsWith("negativeInteger")
                      || type.endsWith("nonPositiveInteger")) {
                literalValue = "-1";
              } else {
                literalValue = "";
              }
              try {
              ((OInstance)resource).addDatatypePropertyValue(
                (DatatypeProperty) property, new Literal(literalValue,
                  ((DatatypeProperty)property).getDataType()));
              } catch(InvalidValueException e) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(),
                        "Incompatible value");
                e.printStackTrace();
                return;
              }
              detailsTableModel.setItem(resource);
              SwingUtilities.invokeLater(new Runnable() { @Override
              public void run() {
              for (int rowI = detailsTable.getRowCount()-1; rowI >= 0; rowI--) {
                if (detailsTable.getValueAt(rowI,
                    DetailsTableModel.VALUE_COLUMN).equals(literalValue)
                  && detailsTable.getValueAt(rowI,
                     DetailsTableModel.LABEL_COLUMN) instanceof PropertyValue
                  && ((PropertyValue)detailsTable.getValueAt(rowI,
                   DetailsTableModel.LABEL_COLUMN)).getProperty()
                   .equals(property)) {
                  detailsTable.editCellAt(rowI, DetailsTableModel.VALUE_COLUMN);
                  detailsTable.getEditorComponent().requestFocusInWindow();
                  break;
                }
              }
              }});
            }
            else if (property instanceof ObjectProperty
                 && resource instanceof OInstance) {
              new ObjectPropertyValueAction("", resource,
                (ObjectProperty) property, null).actionPerformed(null);
            }
          }

          else if(value instanceof PropertyValue
            && column == DetailsTableModel.DELETE_COLUMN
            && me.getClickCount() == 1) {

            PropertyValue pv = (PropertyValue) value;

            try {
              if(resource instanceof OClass) {
                if(pv.getProperty() instanceof AnnotationProperty) {
                  resource.removeAnnotationPropertyValue(
                          (AnnotationProperty)pv.getProperty(), (Literal)pv
                                  .getValue());
                }
              }
              else {
                OInstance instance = (OInstance)resource;
                if(pv.getProperty() instanceof AnnotationProperty) {
                  instance.removeAnnotationPropertyValue((AnnotationProperty)pv
                          .getProperty(), (Literal)pv.getValue());
                }
                else if(pv.getProperty() instanceof DatatypeProperty) {
                  instance.removeDatatypePropertyValue((DatatypeProperty)pv
                          .getProperty(), (Literal)pv.getValue());
                }
                else if(pv.getProperty() instanceof ObjectProperty) {
                  instance.removeObjectPropertyValue((ObjectProperty)pv
                          .getProperty(), (OInstance)pv.getValue());
                }
              }

              SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                  TreePath path = tree.getSelectionPath();
                  tree.setSelectionRow(0);
                  tree.setSelectionPath(path);
                }
              });
            }
            catch(Exception e) {
              JOptionPane.showMessageDialog(MainFrame.getInstance(),
                      "Cannot delete the property value because \n"
                              + e.getMessage());
              e.printStackTrace();
            }
          }
        }
      }
    });

    propertyDetailsTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isLeftMouseButton(me)) {
          int[] rows = propertyDetailsTable.getSelectedRows();
          int column = propertyDetailsTable.getSelectedColumn();
          if(rows == null || rows.length == 0) return;

          Object value = propertyDetailsTable.getModel().getValueAt(rows[0], 1);
          if(value instanceof DetailsGroup) {
            if(column == 0) {
              boolean expanded = ((DetailsGroup)value).isExpanded();
              ((DetailsGroup)value).setExpanded(!expanded);
              propertyDetailsTable.updateUI();
            }
            else {
              return;
            }
          }

          if(value instanceof DetailsGroup) return;
          final Object finalObj = value;

          // find out the selected component in the tree
          Object sc = propertyTree.getSelectionPath().getLastPathComponent();
          if(sc == null) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(),
                    "No resource selected in the main ontology tree");
            return;
          }

          // find out the treenode for the current selection
          final DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)sc;
          final OResource resource = ((OResourceNode)dmtn.getUserObject())
                  .getResource();

          if((!(finalObj instanceof PropertyValue) || column == 1)
                  && me.getClickCount() == 2) {

            OResource toSelect = null;
            JTree treeToSelectIn = null;

            if(finalObj instanceof OClass) {
              toSelect = (OResource)finalObj;
              treeToSelectIn = tree;
            }
            else if(finalObj instanceof RDFProperty) {
              toSelect = (RDFProperty)finalObj;
              treeToSelectIn = propertyTree;
            }
            else if(finalObj instanceof PropertyValue) {
              toSelect = ((PropertyValue)finalObj).getProperty();
              treeToSelectIn = propertyTree;
            }

            if(toSelect != null) {

              treeToSelectIn.setSelectionPath(new TreePath(uri2TreeNodesListMap
                      .get(toSelect.getONodeID().toString()).get(0).getPath()));
              treeToSelectIn.scrollPathToVisible(treeToSelectIn
                      .getSelectionPath());

              if(treeToSelectIn == tree) {
                tabbedPane.setSelectedComponent(scroller);
              }
              else {
                tabbedPane.setSelectedComponent(propertyScroller);
              }
              return;
            }
          }

          if(finalObj instanceof PropertyValue && column == 2
                  && me.getClickCount() == 2) {
            PropertyValue pv = (PropertyValue)finalObj;
            RDFProperty p = pv.getProperty();
            // if property is an annotation property
            if(p instanceof AnnotationProperty) {
              String reply = JOptionPane.showInputDialog(MainFrame
                      .getInstance(), ((Literal)pv.getValue()).getValue(),
                      "Value for " + p.getName() + " property",
                      JOptionPane.QUESTION_MESSAGE);
              if(reply != null) {
                resource.removeAnnotationPropertyValue((AnnotationProperty)p,
                        (Literal)pv.getValue());
                resource.addAnnotationPropertyValue((AnnotationProperty)p,
                        new Literal(reply));
              }
              TreePath path = propertyTree.getSelectionPath();
              propertyTree.setSelectionRow(0);
              propertyTree.setSelectionPath(path);
              return;
            }
          }

          if(finalObj instanceof PropertyValue && column == 3
                  && me.getClickCount() == 1) {

            PropertyValue pv = (PropertyValue)finalObj;
            try {
              if(resource instanceof RDFProperty) {
                if(pv.getProperty() instanceof AnnotationProperty) {
                  ((RDFProperty)resource).removeAnnotationPropertyValue(
                          (AnnotationProperty)pv.getProperty(), (Literal)pv
                                  .getValue());
                }
              }

              SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                  TreePath path = propertyTree.getSelectionPath();
                  propertyTree.setSelectionRow(0);
                  propertyTree.setSelectionPath(path);
                }
              });
            }
            catch(Exception e) {
              JOptionPane.showMessageDialog(MainFrame.getInstance(),
                      "Cannot delete the property value because \n"
                              + e.getMessage());
              e.printStackTrace();
            }
          }
        }
      }
    });
  }

  @SuppressWarnings("serial")
  protected class AnnotationPropertyValueAction extends AbstractAction {
    public AnnotationPropertyValueAction(String name, OResource oResource,
                                         AnnotationProperty property) {
      super(name, MainFrame.getIcon("ontology-annotation-property"));
      this.oResource = oResource;
      this.property = property;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      Object inputValue = JOptionPane.showInputDialog(MainFrame.getInstance(),
        "<html>Enter a value for the property <b>" +
        property.getName() + "</b>",
        "New property value", JOptionPane.QUESTION_MESSAGE,
        MainFrame.getIcon("ontology-annotation-property"), null, null);
      if(inputValue != null) {
        // add a new property value
        oResource.addAnnotationPropertyValue(
          property, new Literal((String) inputValue));
      }
      // reselect instance in the tree to update the table
      TreePath path = tree.getSelectionPath();
      tree.setSelectionRow(0);
      tree.setSelectionPath(path);
    }
    private OResource oResource;
    private AnnotationProperty property;
  }

  @SuppressWarnings("serial")
  protected class DatatypePropertyValueAction extends AbstractAction {
    public DatatypePropertyValueAction(String name, OResource oResource,
                                       DatatypeProperty property) {
      super(name, MainFrame.getIcon("ontology-datatype-property"));
      this.oResource = oResource;
      this.property = property;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
      Object inputValue = JOptionPane.showInputDialog(MainFrame.getInstance(),
        "<html>Enter a value for the property <b>" +
        property.getName() + "</b>\n" +
        "of type " + property.getDataType().getXmlSchemaURIString()
          .replaceFirst("http://www.w3.org/2001/XMLSchema#", ""),
        "New property value", JOptionPane.QUESTION_MESSAGE,
        MainFrame.getIcon("ontology-datatype-property"), null, null);
      if(inputValue != null) {
        boolean validValue = property.getDataType()
          .isValidValue((String) inputValue);
        if(!validValue) {
          JOptionPane.showMessageDialog(MainFrame.getInstance(),
            "Incompatible value: " + inputValue);
          return;
        }
        try {
          ((OInstance)oResource).addDatatypePropertyValue(property,
            new Literal((String) inputValue, property.getDataType()));
        }
        catch(InvalidValueException e) {
          JOptionPane.showMessageDialog(MainFrame.getInstance(),
            "Incompatible value.\n" + e.getMessage());
          return;
        }
      }
      TreePath path = tree.getSelectionPath();
      tree.setSelectionRow(0);
      tree.setSelectionPath(path);
    }
    private OResource oResource;
    private DatatypeProperty property;
  }

  @SuppressWarnings("serial")
  protected class ObjectPropertyValueAction extends AbstractAction {
    public ObjectPropertyValueAction(String name, OResource oResource,
                                     ObjectProperty property,
                                     OInstance oldValue) {
      super(name, MainFrame.getIcon("ontology-object-property"));
      this.oResource = oResource;
      this.property = property;
      this.oldValue = oldValue;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
      Set<OInstance> instances = ontology.getOInstances();
      ArrayList<String> validInstances = new ArrayList<String>();
      for (OInstance instance : instances) {
        if (property.isValidRange(instance)) {
          validInstances.add(instance.getONodeID().toString());
        }
      }
      ValuesSelectionAction vsa = new ValuesSelectionAction();
      int choice = vsa.showGUI("New property value",
        validInstances.toArray(new String[validInstances.size()]),
        oldValue != null ?
          new String[]{oldValue.getONodeID().toString()} : new String[]{},
        false, MainFrame.getIcon("ontology-object-property"));
      if (choice != JOptionPane.OK_OPTION) { return; }
      if (oldValue != null) {
        ((OInstance)oResource).removeObjectPropertyValue(property, oldValue);
      }
      String[] selectedValues = vsa.getSelectedValues();
      for(int i = 0; i < selectedValues.length; i++) {
        OInstance byName = (OInstance)
          Utils.getOResourceFromMap(ontology,selectedValues[i]);
        if(byName == null) { continue; }
        try {
          ((OInstance)oResource).addObjectPropertyValue(property, byName);
        }
        catch(InvalidValueException e) {
          JOptionPane.showMessageDialog(MainFrame.getInstance(),
            "Incompatible value.\n" + e.getMessage());
          return;
        }
      }
      TreePath path = tree.getSelectionPath();
      tree.setSelectionRow(0);
      tree.setSelectionPath(path);
    }
    private OResource oResource;
    private ObjectProperty property;
    private OInstance oldValue;
  }

  protected void expandNode(JTree tree) {
    for(int i = 0; i < tree.getRowCount(); i++) {
      tree.expandRow(i);
    }
  }

  /**
   * Enable-disable toolBar components
   */
  private void enableDisableToolBarComponents() {
    boolean allClasses = true;
    boolean allProperties = true;
    boolean allInstances = true;
    for (DefaultMutableTreeNode node : selectedNodes) {
      OResource res = ((OResourceNode) node.getUserObject()).getResource();
      if (res instanceof OClass) {
        allProperties = false;
        allInstances = false;
      } else if (res instanceof OInstance) {
        allClasses = false;
        allProperties = false;
      } else {
        allInstances = false;
        allClasses = false;
      }
    }
    if (selectedNodes.isEmpty()) {
      topClass.setEnabled(false);
      subClass.setEnabled(false);
      instance.setEnabled(false);
      annotationProperty.setEnabled(false);
      datatypeProperty.setEnabled(false);
      objectProperty.setEnabled(false);
      symmetricProperty.setEnabled(false);
      transitiveProperty.setEnabled(false);
      delete.setEnabled(false);
      restriction.setEnabled(false);
    }
    else if(allClasses) {
      topClass.setEnabled(true);
      subClass.setEnabled(true);
      instance.setEnabled(true);
      annotationProperty.setEnabled(true);
      datatypeProperty.setEnabled(true);
      objectProperty.setEnabled(true);
      symmetricProperty.setEnabled(true);
      transitiveProperty.setEnabled(true);
      delete.setEnabled(true);
      restriction.setEnabled(true);
    }
    else if(allInstances) {
      topClass.setEnabled(true);
      subClass.setEnabled(false);
      instance.setEnabled(false);
      annotationProperty.setEnabled(true);
      datatypeProperty.setEnabled(false);
      objectProperty.setEnabled(false);
      symmetricProperty.setEnabled(false);
      transitiveProperty.setEnabled(false);
      delete.setEnabled(true);
      restriction.setEnabled(false);
    }
    else if(allProperties) {
      topClass.setEnabled(false);
      subClass.setEnabled(false);
      instance.setEnabled(false);
      annotationProperty.setEnabled(true);
      datatypeProperty.setEnabled(true);
      objectProperty.setEnabled(true);
      symmetricProperty.setEnabled(true);
      transitiveProperty.setEnabled(true);
      delete.setEnabled(true);
      restriction.setEnabled(true);
    }
    else {
      topClass.setEnabled(false);
      subClass.setEnabled(false);
      instance.setEnabled(false);
      annotationProperty.setEnabled(true);
      datatypeProperty.setEnabled(false);
      objectProperty.setEnabled(false);
      symmetricProperty.setEnabled(false);
      transitiveProperty.setEnabled(false);
      delete.setEnabled(true);
      restriction.setEnabled(false);
    }
  }

  /**
   * Called when the target of this editor has changed
   */
  protected void rebuildModel() {
    rootNode.removeAllChildren();
    propertyRootNode.removeAllChildren();
    if(ontologyClassesURIs == null)
      ontologyClassesURIs = new ArrayList<String>();
    else ontologyClassesURIs.clear();
    uri2TreeNodesListMap = new HashMap<String, ArrayList<DefaultMutableTreeNode>>();
    reverseMap = new HashMap<DefaultMutableTreeNode, ONodeID>();
    List<OResource> rootClasses = new ArrayList<OResource>(ontology
            .getOClasses(true));
    Collections.sort(rootClasses, itemComparator);
    addChidrenRec(rootNode, rootClasses, itemComparator);
    List<RDFProperty> props = new ArrayList<RDFProperty>(ontology
            .getPropertyDefinitions());
    List<RDFProperty> subList = new ArrayList<RDFProperty>();
    for(int i = 0; i < props.size(); i++) {
      RDFProperty prop = props.get(i);
      if(prop instanceof AnnotationProperty) {
        subList.add(prop);
        continue;
      }
      Set<RDFProperty> set = prop.getSuperProperties(OConstants.Closure.DIRECT_CLOSURE);
      if(set != null && !set.isEmpty()) {
        continue;
      }
      else {
        subList.add(prop);
      }
    }
    Collections.sort(subList, itemComparator);
    addPropertyChidrenRec(propertyRootNode, subList, itemComparator);
    datatypePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    objectPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    symmetricPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    transitivePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        treeModel.nodeStructureChanged(rootNode);
        tree.setSelectionInterval(0, 0);
        // expand the root
        tree.expandPath(new TreePath(rootNode));
        // expand the entire tree
        for(int i = 0; i < tree.getRowCount(); i++)
          tree.expandRow(i);
        propertyTreeModel.nodeStructureChanged(propertyRootNode);
        // expand the root
        propertyTree.expandPath(new TreePath(propertyRootNode));
        // expand the entire tree
        for(int i = 0; i < propertyTree.getRowCount(); i++)
          propertyTree.expandRow(i);
        // detailsTableModel.fireTableDataChanged();
        // propertyDetailsTableModel.fireTableDataChanged();
      }
    });
  }

  /**
   * Adds the children nodes to a node using values from a list of
   * classes and instances.
   * 
   * @param parent the parent node.
   * @param children the List<OResource> of children objects.
   * @param comparator the Comparator used to sort the children.
   */
  protected void addChidrenRec(DefaultMutableTreeNode parent,
          List<OResource> children, Comparator<OResource> comparator) {
    for(OResource aChild : children) {
      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
              new OResourceNode(aChild));
      parent.add(childNode);
      
      // we maintain a map of ontology resources and their representing
      // tree
      // nodes
      ArrayList<DefaultMutableTreeNode> list = uri2TreeNodesListMap.get(aChild
              .getONodeID().toString());
      if(list == null) {
        list = new ArrayList<DefaultMutableTreeNode>();
        uri2TreeNodesListMap.put(aChild.getONodeID().toString(), list);
      }
      list.add(childNode);
      reverseMap.put(childNode, aChild.getONodeID());
      if(aChild instanceof OClass) {
        if(!ontologyClassesURIs.contains(aChild.getONodeID().toString()))
          ontologyClassesURIs.add(aChild.getONodeID().toString());
        childNode.setAllowsChildren(true);
        // add all the subclasses
        OClass aClass = (OClass)aChild;
        List<OResource> childList = new ArrayList<OResource>(aClass
                .getSubClasses(OConstants.Closure.DIRECT_CLOSURE));
        Collections.sort(childList, comparator);
        addChidrenRec(childNode, childList, comparator);
        childList = new ArrayList<OResource>(ontology.getOInstances(aClass,
                OConstants.Closure.DIRECT_CLOSURE));
        Collections.sort(childList, comparator);
        addChidrenRec(childNode, childList, comparator);
      }
      else if(aChild instanceof OInstance) {
        childNode.setAllowsChildren(false);
      }
      tree.expandPath(new TreePath(childNode.getPath()));
    }
    
  }

  /**
   * Adds the children nodes to a node using values from a list of
   * classes and instances.
   * 
   * @param parent the parent node.
   * @param children the lsit of children objects.
   * @param comparator the Comparator used to sort the children.
   */
  protected void addPropertyChidrenRec(DefaultMutableTreeNode parent,
          List<RDFProperty> children, Comparator<OResource> comparator) {
    for(RDFProperty aChild : children) {
      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
              new OResourceNode(aChild));
      parent.add(childNode);
      // we maintain a map of ontology resources and their representing
      // tree
      // nodes
      ArrayList<DefaultMutableTreeNode> list = uri2TreeNodesListMap.get(aChild
              .getONodeID().toString());
      if(list == null) {
        list = new ArrayList<DefaultMutableTreeNode>();
        uri2TreeNodesListMap.put(aChild.getONodeID().toString(), list);
      }
      list.add(childNode);
      reverseMap.put(childNode, aChild.getONodeID());
      if(aChild instanceof AnnotationProperty) {
        childNode.setAllowsChildren(false);
      }
      else {
        childNode.setAllowsChildren(true);
        // add all the sub properties
        List<RDFProperty> childList = new ArrayList<RDFProperty>(aChild
                .getSubProperties(OConstants.Closure.DIRECT_CLOSURE));
        Collections.sort(childList, comparator);
        addPropertyChidrenRec(childNode, childList, comparator);
      }
      propertyTree.expandPath(new TreePath(childNode.getPath()));
    }
  }

  public void processGateEvent(GateEvent e) {
    // ignore
  }

  /**
   * Update the class tree model. To call when a class is added.
   * 
   * @param aClass class to add
   */
  protected void classIsAdded(OClass aClass) {
    // we first obtain its superClasses
    Set<OClass> superClasses = aClass.getSuperClasses(OConstants.Closure.DIRECT_CLOSURE);
    List<OResource> list = new ArrayList<OResource>();
    list.add(aClass);
    if(superClasses == null || superClasses.isEmpty()) {
      // this is a root node
      addChidrenRec(rootNode, list, itemComparator);
      treeModel.nodeStructureChanged(rootNode);
    }
    else {
      List<OClass> cs = new ArrayList<OClass>(superClasses);
      Collections.sort(cs, itemComparator);
      for(OClass c : cs) {
        ArrayList<DefaultMutableTreeNode> superNodeList = uri2TreeNodesListMap
                .get(c.getONodeID().toString());
        if(superNodeList == null) {
          // this is a result of two classes being created as a result
          // of only one addition
          // e.g. create a cardinality restriction and it will create a
          // fictivenode as well
          // so lets first add it
          classIsAdded(c);
          // no need to go any further, as refreshing the super node
          // will automatically refresh the children nodes
          continue;
        }

        for(int i = 0; i < superNodeList.size(); i++) {
          DefaultMutableTreeNode node = superNodeList.get(i);
          addChidrenRec(node, list, itemComparator);
          treeModel.nodeStructureChanged(node);
        }
      }
    }
  }

  /**
   * Update the property tree model. To call when a property is added.
   *
   * @param p property to add
   */
  protected void propertyIsAdded(RDFProperty p) {
    // we first obtain its superProperty
    if(p instanceof AnnotationProperty) {
      List<RDFProperty> list = new ArrayList<RDFProperty>();
      list.add(p);
      addPropertyChidrenRec(propertyRootNode, list, itemComparator);
      propertyTreeModel.nodeStructureChanged(propertyRootNode);
      return;
    }
    Set<RDFProperty> superProperties = p
            .getSuperProperties(OConstants.Closure.DIRECT_CLOSURE);
    List<RDFProperty> list = new ArrayList<RDFProperty>();
    list.add(p);
    if(superProperties == null || superProperties.isEmpty()) {
      // this is a root node
      addPropertyChidrenRec(propertyRootNode, list, itemComparator);
      propertyTreeModel.nodeStructureChanged(propertyRootNode);
    }
    else {
      List<RDFProperty> sps = new ArrayList<RDFProperty>(superProperties);
      Collections.sort(sps, itemComparator);
      for(RDFProperty r : sps) {
        ArrayList<DefaultMutableTreeNode> superNodeList = uri2TreeNodesListMap
                .get(r.getONodeID().toString());
        for(int i = 0; i < superNodeList.size(); i++) {
          DefaultMutableTreeNode node = superNodeList.get(i);
          addPropertyChidrenRec(node, list, itemComparator);
          propertyTreeModel.nodeStructureChanged(node);
        }
      }
    }
  }

  /**
   * Update the class tree model. To call when an instance is added.
   *
   * @param anInstance instance to add
   */
  protected void instanceIsAdded(OInstance anInstance) {
    ArrayList<DefaultMutableTreeNode> newList = uri2TreeNodesListMap
            .get(anInstance.getONodeID().toString());
    if(newList != null) {
      for(int i = 0; i < newList.size(); i++) {
        DefaultMutableTreeNode node = newList.get(i);
        removeFromMap(treeModel, node);
      }
    }
    Set<OClass> superClasses = anInstance
            .getOClasses(OConstants.Closure.DIRECT_CLOSURE);
    List<OResource> list = new ArrayList<OResource>();
    list.add(anInstance);
    Iterator<OClass> iter = superClasses.iterator();
    while(iter.hasNext()) {
      OClass aClass = iter.next();
      ArrayList<DefaultMutableTreeNode> superNodeList = uri2TreeNodesListMap
              .get(aClass.getONodeID().toString());
      for(int i = 0; i < superNodeList.size(); i++) {
        DefaultMutableTreeNode node = superNodeList.get(i);
        addChidrenRec(node, list, itemComparator);
        treeModel.nodeStructureChanged(node);
      }
    }
  }

  private void removeFromMap(DefaultTreeModel model, DefaultMutableTreeNode node) {
    if(!node.isLeaf()) {
      Enumeration<?> enumeration = node.children();
      List<Object> children = new ArrayList<Object>();
      while(enumeration.hasMoreElements()) {
        children.add(enumeration.nextElement());
      }
      for(int i = 0; i < children.size(); i++) {
        removeFromMap(model, (DefaultMutableTreeNode)children.get(i));
      }
    }
    ONodeID rURI = reverseMap.get(node);
    reverseMap.remove(node);
    ArrayList<DefaultMutableTreeNode> list = uri2TreeNodesListMap.get(rURI
            .toString());
    list.remove(node);
    if(list.isEmpty()) uri2TreeNodesListMap.remove(rURI.toString());

    model.removeNodeFromParent(node);
  }

  /**
   * Update the property tree model. To call when a subproperty is added.
   *
   * @param p subproperty to add
   */
  protected void subPropertyIsAdded(RDFProperty p) {
    ArrayList<DefaultMutableTreeNode> nodesList = uri2TreeNodesListMap.get(p
            .getONodeID().toString());
    // p is a property where the subProperty is added
    // the property which is added as a subProperty might not have any
    // super RDFProperty before
    // so we first remove it from the propertyTree
    Set<RDFProperty> props = p.getSubProperties(OConstants.Closure.DIRECT_CLOSURE);
    List<RDFProperty> ps = new ArrayList<RDFProperty>(props);
    Collections.sort(ps, itemComparator);
    for(RDFProperty subP : ps) {
      ArrayList<DefaultMutableTreeNode> subNodesList = uri2TreeNodesListMap
              .get(subP.getONodeID().toString());
      if(subNodesList != null) {
        for(int i = 0; i < subNodesList.size(); i++) {
          DefaultMutableTreeNode node = subNodesList.get(i);
          removeFromMap(propertyTreeModel, node);
          propertyTreeModel.nodeStructureChanged(node.getParent());
        }
      }
      if(subNodesList != null && nodesList != null) {
        // and each of this node needs to be added again
        for(int i = 0; i < nodesList.size(); i++) {
          DefaultMutableTreeNode superNode = nodesList.get(i);
          List<RDFProperty> list = new ArrayList<RDFProperty>();
          list.add(subP);
          addPropertyChidrenRec(superNode, list, itemComparator);
          propertyTreeModel.nodeStructureChanged(superNode);
        }
      }
    }
  }

  /**
   * Update the property tree model. To call when a subproperty is deleted.
   *
   * @param p subproperty to delete
   */
  protected void subPropertyIsDeleted(RDFProperty p) {
    ArrayList<DefaultMutableTreeNode> nodeList = uri2TreeNodesListMap.get(p
            .getONodeID().toString());
    if(nodeList == null || nodeList.isEmpty()) {
      // this is already deleted
      return;
    }
    // p is a property where the subProperty is deleted
    // we don't know which property is deleted
    // so we remove the property p from the tree and add it again
    for(int i = 0; i < nodeList.size(); i++) {
      DefaultMutableTreeNode node = nodeList.get(i);
      removeFromMap(propertyTreeModel, node);
      propertyTreeModel.nodeStructureChanged(node.getParent());
    }
    // now we need to add it again
    Set<RDFProperty> superProperties = p
            .getSuperProperties(OConstants.Closure.DIRECT_CLOSURE);
    List<RDFProperty> list = new ArrayList<RDFProperty>();
    list.add(p);
    if(superProperties != null) {
      List<RDFProperty> rps = new ArrayList<RDFProperty>(superProperties);
      Collections.sort(rps, itemComparator);
      for(RDFProperty superP : rps) {
        nodeList = uri2TreeNodesListMap.get(superP.getONodeID().toString());
        for(int i = 0; i < nodeList.size(); i++) {
          DefaultMutableTreeNode superNode = nodeList.get(i);
          addPropertyChidrenRec(superNode, list, itemComparator);
          propertyTreeModel.nodeStructureChanged(superNode);
        }
      }
    }
    else {
      addPropertyChidrenRec(propertyRootNode, list, itemComparator);
      propertyTreeModel.nodeStructureChanged(propertyRootNode);
    }
  }

  /**
   * Update the class tree model. To call when a subclass is added.
   *
   * @param c subclass to add
   */
  protected void subClassIsAdded(OClass c) {
    ArrayList<DefaultMutableTreeNode> nodesList = uri2TreeNodesListMap.get(c
            .getONodeID().toString());
    // c is a class where the subClass is added
    // the class which is added as a subClass might not have any
    // super Class before
    // so we first remove it from the tree
    Set<OClass> classes = c.getSubClasses(OClass.Closure.DIRECT_CLOSURE);
    List<OClass> cs = new ArrayList<OClass>(classes);
    Collections.sort(cs, itemComparator);
    for(OClass subC : cs) {
      ArrayList<DefaultMutableTreeNode> subNodesList = uri2TreeNodesListMap
              .get(subC.getONodeID().toString());
      if(subNodesList != null) {
        for(int i = 0; i < subNodesList.size(); i++) {
          DefaultMutableTreeNode node = subNodesList.get(i);
          removeFromMap(treeModel, node);
          treeModel.nodeStructureChanged(node.getParent());
        }
      }
      if(subNodesList != null && nodesList != null) {
        // and each of this node needs to be added again
        List<OResource> list = new ArrayList<OResource>();
        list.add(subC);
        for(int i = 0; i < nodesList.size(); i++) {
          DefaultMutableTreeNode superNode = nodesList.get(i);
          addChidrenRec(superNode, list, itemComparator);
          treeModel.nodeStructureChanged(superNode);
        }
      }
    }
  }

  /**
   * Update the class tree model. To call when a subclass is deleted.
   *
   * @param c subclass to delete
   */
  protected void subClassIsDeleted(OClass c) {
    ArrayList<DefaultMutableTreeNode> nodeList = uri2TreeNodesListMap.get(c
            .getONodeID().toString());
    if(nodeList == null || nodeList.isEmpty()) {
      // this is already deleted
      return;
    }

    List<OResource> toAdd = new ArrayList<OResource>();

    // c is a class whose subClass is deleted
    // we don't know which class is deleted
    // so we remove the class c from the tree and add it again
    boolean firstTime = true;
    for(int i = 0; i < nodeList.size(); i++) {
      DefaultMutableTreeNode node = nodeList.get(i);
      if(firstTime) {
        OClass parentClass = (OClass)
          Utils.getOResourceFromMap(this.ontology,reverseMap.get(node).toString());
        firstTime = false;
        // find out which class is deleted
        Enumeration<?> e = node.children();
        if(e != null) {
          while(e.hasMoreElements()) {
            DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)e
                    .nextElement();
            ONodeID rURI = reverseMap.get(aNode);
            // lets check with the ontology if this instance is still
            // there
            OResource res = Utils.getOResourceFromMap(this.ontology,rURI.toString());
            if(res != null) {
              // lets check if its parents is the current node
              if(res instanceof OClass) {
                if(((OClass)res).isSubClassOf(parentClass,
                        OConstants.Closure.DIRECT_CLOSURE)) {
                  // continue;
                }
                else {
                  // that's it this is the class which should be added
                  // at the top of tree
                  toAdd.add(res);
                  break;
                }
              }
            }
          }
        }
      }
      removeFromMap(treeModel, node);
      treeModel.nodeStructureChanged(node.getParent());
    }

    // now we need to add it again
    Set<OClass> superClasses = c.getSuperClasses(OConstants.Closure.DIRECT_CLOSURE);
    List<OResource> list = new ArrayList<OResource>();
    list.add(c);

    if(superClasses != null && !superClasses.isEmpty()) {
      List<OClass> cs = new ArrayList<OClass>(superClasses);
      Collections.sort(cs, itemComparator);
      for(OClass superC : cs) {
        nodeList = uri2TreeNodesListMap.get(superC.getONodeID().toString());
        for(int i = 0; i < nodeList.size(); i++) {
          DefaultMutableTreeNode superNode = nodeList.get(i);
          addChidrenRec(superNode, list, itemComparator);
          treeModel.nodeStructureChanged(superNode);
        }
      }
    }
    else {
      addChidrenRec(rootNode, list, itemComparator);
      treeModel.nodeStructureChanged(rootNode);
    }

    if(!toAdd.isEmpty()) {
      addChidrenRec(rootNode, toAdd, itemComparator);
      treeModel.nodeStructureChanged(rootNode);
    }
  }

  @Override
  public void resourcesRemoved(final Ontology ontology, final String[] resources) {
    if(this.ontology != ontology) {
      return;
    }

    // ok before we refresh our gui, lets find out the resource which
    // was deleted originally from the
    // gui. Deleting a resource results in deleting other resources as
    // well. The last resource in the resources is the one which was
    // asked from a user to delete.
    // MAG 12/3/2014: this code does nothing as there are no side
    // effects to the calls and the variables filled by the calls are
    // never used
    /*String deletedResourceURI = resources[resources.length - 1];
    DefaultMutableTreeNode aNode = uri2TreeNodesListMap.get(deletedResourceURI)
            .get(0);

    
    DefaultMutableTreeNode probableParentNode = null;
    if(aNode.getParent() == null) {
      OResource res = ((OResourceNode)aNode.getUserObject()).getResource();
      if(res instanceof RDFProperty) {
        probableParentNode = propertyRootNode;
      }
      else {
        probableParentNode = rootNode;
      }
    }
    else {
      probableParentNode = (DefaultMutableTreeNode)aNode.getParent();
    }*/
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        // first hide the tree
        scroller.getViewport().setView(new JLabel("PLease wait, updating..."));
        propertyScroller.getViewport().setView(
                new JLabel("Please wait, updating..."));
        // now update the tree
        // we can use a normal thread here
        Runnable treeUpdater = new Runnable() {
          @Override
          public void run() {
            // this is not in the swing thread
            // update the tree...
            ontologyClassesURIs.removeAll(Arrays.asList(resources));
            final HashSet<DefaultMutableTreeNode> nodesToRefresh = new HashSet<DefaultMutableTreeNode>();
            for(int i = 0; i < resources.length; i++) {
              ArrayList<DefaultMutableTreeNode> nodeList = uri2TreeNodesListMap
                      .get(resources[i]);
              if(nodeList != null) {
                for(int j = 0; j < nodeList.size(); j++) {
                  DefaultMutableTreeNode node = nodeList.get(j);
                  DefaultTreeModel modelToUse = ((OResourceNode)node
                          .getUserObject()).getResource() instanceof RDFProperty
                          ? propertyTreeModel
                          : treeModel;
                  if(node.getParent() != null) {
                    nodesToRefresh
                            .add((DefaultMutableTreeNode)node.getParent());
                  } else if(modelToUse == treeModel) {
                    nodesToRefresh.add(rootNode);
                  } else {
                    nodesToRefresh.add(propertyRootNode);
                  }
                  removeFromMap(modelToUse, node);
                  modelToUse.nodeStructureChanged(node.getParent());
                }
              }
            }

            // now we need to show back the tree
            // go back to the swing thread
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                // show the tree again
                scroller.getViewport().setView(tree);
                propertyScroller.getViewport().setView(propertyTree);
                // ask the tree to refresh
                tree.invalidate();
                propertyTree.invalidate();

                for(DefaultMutableTreeNode parentNode : nodesToRefresh) {
                  if(!reverseMap.containsKey(parentNode) && parentNode != rootNode && parentNode != propertyRootNode) continue;
                  
                  if(parentNode != rootNode && parentNode != propertyRootNode) {
                    OResource parentResource = ((OResourceNode)parentNode
                            .getUserObject()).getResource();
                    if(parentResource instanceof RDFProperty) {
                      List<RDFProperty> children = new ArrayList<RDFProperty>(
                              ((RDFProperty)parentResource)
                                      .getSubProperties(OConstants.Closure.DIRECT_CLOSURE));
                      Enumeration<?> en = parentNode.children();
                      while(en.hasMoreElements()) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)en
                                .nextElement();
                        String toCompare = ((OResourceNode)dmtn.getUserObject())
                                .getResource().getONodeID().toString();
                        List<OResource> delete = new ArrayList<OResource>();
                        for(OResource aRes : children) {
                          if(toCompare.equals(aRes.getONodeID().toString())) {
                            delete.add(aRes);
                          }
                        }
                        children.removeAll(delete);
                      }

                      addPropertyChidrenRec(parentNode, children,
                              itemComparator);
                      propertyTreeModel.nodeStructureChanged(parentNode);
                      propertyTree.setSelectionPath(new TreePath(parentNode
                              .getPath()));
                    }
                    else {
                      List<OResource> children = new ArrayList<OResource>(
                              ((OClass)parentResource)
                                      .getSubClasses(OConstants.Closure.DIRECT_CLOSURE));
                      children.addAll(ontology
                              .getOInstances((OClass)parentResource,
                                      OConstants.Closure.DIRECT_CLOSURE));
                      Enumeration<?> en = parentNode.children();
                      while(en.hasMoreElements()) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)en
                                .nextElement();
                        String toCompare = ((OResourceNode)dmtn.getUserObject())
                                .getResource().getONodeID().toString();
                        List<OResource> delete = new ArrayList<OResource>();
                        for(OResource aRes : children) {
                          if(toCompare.equals(aRes.getONodeID().toString())) {
                            delete.add(aRes);
                          }
                        }
                        children.removeAll(delete);
                      }
                      addChidrenRec(parentNode, children, itemComparator);
                      treeModel.nodeStructureChanged(parentNode);
                      tree.setSelectionPath(new TreePath(parentNode.getPath()));
                    }
                  }
                  else if(parentNode == rootNode) {
                    if(tree.getRowCount() > 0) {
                      List<OResource> children = new ArrayList<OResource>(
                              ontology.getOClasses(true));

                      Enumeration<?> en = parentNode.children();
                      while(en.hasMoreElements()) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)en
                                .nextElement();
                        if(dmtn.getLevel() != 1) continue;
                        String toCompare = ((OResourceNode)dmtn.getUserObject())
                                .getResource().getONodeID().toString();
                        List<OResource> delete = new ArrayList<OResource>();
                        for(OResource aRes : children) {
                          if(toCompare.equals(aRes.getONodeID().toString())) {
                            delete.add(aRes);
                          }
                        }
                        children.removeAll(delete);
                      }
                      addChidrenRec(rootNode, children, itemComparator);
                      treeModel.nodeStructureChanged(rootNode);
                      tree.setSelectionRow(0);
                    }
                  }
                  else {
                    if(propertyTree.getRowCount() > 0) {
                      List<RDFProperty> props = new ArrayList<RDFProperty>(
                              ontology.getPropertyDefinitions());
                      List<RDFProperty> subList = new ArrayList<RDFProperty>();
                      for(int i = 0; i < props.size(); i++) {
                        RDFProperty prop = props.get(i);
                        if(prop instanceof AnnotationProperty) {
                          subList.add(prop);
                          continue;
                        }
                        Set<RDFProperty> set = prop
                                .getSuperProperties(OConstants.Closure.DIRECT_CLOSURE);
                        if(set != null && !set.isEmpty()) {
                          continue;
                        }
                        else {
                          subList.add(prop);
                        }
                      }
                      Collections.sort(subList, itemComparator);
                      Enumeration<?> en = parentNode.children();
                      while(en.hasMoreElements()) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)en
                                .nextElement();
                        if(dmtn.getLevel() != 1) continue;
                        String toCompare = ((OResourceNode)dmtn.getUserObject())
                                .getResource().getONodeID().toString();
                        List<OResource> delete = new ArrayList<OResource>();
                        for(OResource aRes : subList) {
                          if(toCompare.equals(aRes.getONodeID().toString())) {
                            delete.add(aRes);
                          }
                        }
                        subList.removeAll(delete);
                      }

                      addPropertyChidrenRec(propertyRootNode, subList,
                              itemComparator);
                      propertyTreeModel.nodeStructureChanged(propertyRootNode);
                      propertyTree.setSelectionRow(0);
                    }
                  }
                }
              }
            });
          }
        };
        treeUpdater.run();
      }
    });
  }

  @Override
  public void resourceAdded(Ontology ontology, OResource resource) {
    if(this.ontology != ontology) {
      return;
    }
    boolean isItTree = true;
    TreePath path = tree.getSelectionPath();
    if(path == null) {
      isItTree = false;
      path = propertyTree.getSelectionPath();
    }

    if(resource instanceof OClass) {
      classIsAdded((OClass)resource);
      expandNode(tree);
    }
    else if(resource instanceof RDFProperty) {
      propertyIsAdded((RDFProperty)resource);
      expandNode(propertyTree);
    }
    else if(resource instanceof OInstance) {
      instanceIsAdded((OInstance)resource);
      expandNode(tree);
    }
    datatypePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    objectPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    symmetricPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    transitivePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);

    if(isItTree) {
      DefaultMutableTreeNode aNode = uri2TreeNodesListMap.get(
              resource.getONodeID().toString()).get(0);
      tree.setSelectionPath(new TreePath(aNode.getPath()));
    }
    else {
      DefaultMutableTreeNode aNode = uri2TreeNodesListMap.get(
              resource.getONodeID().toString()).get(0);
      propertyTree.setSelectionPath(new TreePath(aNode.getPath()));
    }
    return;
  }

  @Override
  public void resourceRelationChanged(Ontology ontology, OResource resource1,
          OResource resouce2, int eventType) {
    this.ontologyModified(ontology, resource1, eventType);
  }

  @Override
  public void resourcePropertyValueChanged(Ontology ontology,
          OResource resource, RDFProperty property, Object value, int eventType) {
    this.ontologyModified(ontology, resource, eventType);
  }

  /**
   * This method is invoked from ontology whenever it is modified
   */
  public void ontologyModified(Ontology ontology, OResource resource,
          int eventType) {
    if(this.ontology != ontology) {
      return;
    }
    boolean isItTree = true;
    TreePath path = tree.getSelectionPath();
    if(path == null) {
      isItTree = false;
      path = propertyTree.getSelectionPath();
    }

    switch(eventType) {
      case OConstants.SUB_PROPERTY_ADDED_EVENT:
        subPropertyIsAdded((RDFProperty)resource);
        break;
      case OConstants.SUB_PROPERTY_REMOVED_EVENT:
        subPropertyIsDeleted((RDFProperty)resource);
        break;
      case OConstants.SUB_CLASS_ADDED_EVENT:
        subClassIsAdded((OClass)resource);
        break;
      case OConstants.SUB_CLASS_REMOVED_EVENT:
        subClassIsDeleted((OClass)resource);
        break;
    }

    switch(eventType) {
      case OConstants.SUB_PROPERTY_ADDED_EVENT:
      case OConstants.SUB_PROPERTY_REMOVED_EVENT:
        expandNode(propertyTree);
        break;
      default:
        expandNode(tree);
        break;
    }
    datatypePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    objectPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    symmetricPropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    transitivePropertyAction.setOntologyClassesURIs(ontologyClassesURIs);
    if(isItTree) {
      tree.setSelectionPath(path);
      DefaultMutableTreeNode aNode = uri2TreeNodesListMap.get(
              resource.getONodeID().toString()).get(0);
      tree.setSelectionPath(new TreePath(aNode.getPath()));
    }
    else {
      propertyTree.setSelectionPath(path);
      DefaultMutableTreeNode aNode = uri2TreeNodesListMap.get(
              resource.getONodeID().toString()).get(0);
      propertyTree.setSelectionPath(new TreePath(aNode.getPath()));
    }
  }

  /**
   * This method is called whenever ontology is reset. 
   * 
   * @param ontology
   */
  @Override
  public void ontologyReset(Ontology ontology) {
    if(this.ontology != ontology) {
      return;
    }
    rebuildModel();
  }

  public void addTreeNodeSelectionListener(TreeNodeSelectionListener listener) {
    this.listeners.add(listener);
  }

  public void removeTreeNodeSelectionListener(TreeNodeSelectionListener listener) {
    this.listeners.remove(listener);
  }

  private void fireTreeNodeSelectionChanged(
          ArrayList<DefaultMutableTreeNode> nodes) {
    for(int i = 0; i < listeners.size(); i++) {
      listeners.get(i).selectionChanged(nodes);
    }
  }

  public void selectResourceInClassTree(final OResource resource) {
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      tabbedPane.setSelectedComponent(scroller);
      tree.setSelectionPath(new TreePath(uri2TreeNodesListMap.get(
        resource.getONodeID().toString()).get(0).getPath()));
      tree.scrollPathToVisible(tree.getSelectionPath());
    }});
  }

  /**
   * the ontology instance
   */
  protected Ontology ontology;

  /**
   * Ontology Item Comparator
   */
  protected OntologyItemComparator itemComparator;

  /**
   * The tree view.
   */
  protected JTree tree;

  /**
   * The property treeView
   */
  protected JTree propertyTree;

  /**
   * The mode, for the tree.
   */
  protected DefaultTreeModel treeModel;

  /**
   * The property model, for the tree
   */
  protected DefaultTreeModel propertyTreeModel;

  /**
   * The list view used to display item details
   */
  protected JTable detailsTable;

  protected JTable propertyDetailsTable;

  protected DetailsTableModel detailsTableModel;

  protected PropertyDetailsTableModel propertyDetailsTableModel;

  /**
   * The main split
   */
  protected JSplitPane mainSplit;

  /**
   * The root node of the tree.
   */
  protected DefaultMutableTreeNode rootNode;

  /**
   * The property root node of the tree
   */
  protected DefaultMutableTreeNode propertyRootNode;

  protected JScrollPane detailsTableScroller, propertyDetailsTableScroller;

  /**
   * ToolBar
   */
  protected JToolBar toolBar;

  protected JButton queryBtn;

  protected JButton refreshOntologyBtn;
  
  protected JButton topClass;

  protected JButton subClass;

  protected JButton restriction;

  protected JButton instance;

  protected JButton annotationProperty;

  protected JButton datatypeProperty;

  protected JButton objectProperty;

  protected JButton symmetricProperty;

  protected JButton transitiveProperty;

  protected JButton delete;

  protected JButton search;

  protected ArrayList<DefaultMutableTreeNode> selectedNodes;

  protected ArrayList<String> ontologyClassesURIs;

  protected SearchAction searchAction;

  protected TopClassAction topClassAction;

  protected SubClassAction subClassAction;

  protected InstanceAction instanceAction;

  protected AnnotationPropertyAction annotationPropertyAction;

  protected DatatypePropertyAction datatypePropertyAction;

  protected ObjectPropertyAction objectPropertyAction;

  protected SymmetricPropertyAction symmetricPropertyAction;

  protected TransitivePropertyAction transitivePropertyAction;

  protected DeleteOntologyResourceAction deleteOntoResourceAction;

  protected RestrictionAction restrictionAction;

  protected ArrayList<TreeNodeSelectionListener> listeners;

  protected HashMap<String, ArrayList<DefaultMutableTreeNode>> uri2TreeNodesListMap;

  protected HashMap<DefaultMutableTreeNode, ONodeID> reverseMap;

  protected JScrollPane propertyScroller, scroller;

  protected JTabbedPane tabbedPane;
}
