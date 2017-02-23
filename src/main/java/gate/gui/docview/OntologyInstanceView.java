/**
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz - 17/12/2009
 *
 *  $Id$
 */

package gate.gui.docview;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Factory;
import gate.gui.Handle;
import gate.gui.MainFrame;
import gate.gui.ontology.OntologyEditor;
import gate.swing.XJTable;
import gate.creole.ontology.*;
import gate.util.InvalidOffsetException;
import gate.util.GateRuntimeException;
import gate.util.Out;
import gate.util.Strings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Document view that shows two tables: one instances and one for properties.
 * The instances table is linked with the OntologyClassView class selection
 * and the properties table is linked with the instances view.
 * <br>
 * Two buttons allow to add a new instance from the text selection in the
 * document or as a new label for the selected instance.
 * <br>
 * You can filter the instances table, delete instances and set properties
 * that are defined in the ontology as object properties.
 */
@SuppressWarnings("serial")
public class OntologyInstanceView extends AbstractDocumentView {

  public OntologyInstanceView() {

    instances = new HashSet<OInstance>();
    setProperties = new HashSet<ObjectProperty>();
    properties = new HashSet<ObjectProperty>();
    classesByPropertyMap = new HashMap<String, Set<OClass>>();
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
    // get a pointer to the class view
    Iterator<DocumentView> verticalViewsIter = owner.getVerticalViews().iterator();
    while(classView == null && verticalViewsIter.hasNext()){
      DocumentView aView = verticalViewsIter.next();
      if (aView instanceof OntologyClassView) {
        classView = (OntologyClassView) aView;
      }
    }
    classView.setOwner(owner);

    mainPanel = new JPanel(new BorderLayout());

    // filter and buttons at the top
    JPanel filterPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel filterLabel = new JLabel("Filter: ");
    filterPanel.add(filterLabel, gbc);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    filterPanel.add(filterTextField = new JTextField(20), gbc);
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0;
    filterTextField.setToolTipText("Filter the instance table on labels");
    clearFilterButton = new JButton();
    clearFilterButton.setBorder(BorderFactory.createEmptyBorder());
    filterPanel.add(clearFilterButton, gbc);
    hiddenInstancesLabel = new JLabel(" 0 hidden ");
    filterPanel.add(hiddenInstancesLabel, gbc);
    JPanel filterButtonsPanel = new JPanel();
    newInstanceButton = new JButton("New Inst.");
    newInstanceButton.setEnabled(false);
    newInstanceButton.setToolTipText("New instance from the selection");
    newInstanceButton.setMnemonic(KeyEvent.VK_N);
    filterButtonsPanel.add(newInstanceButton);
    addLabelButton = new JButton("Add to Selected Inst.");
    addLabelButton.setEnabled(false);
    addLabelButton.setToolTipText(
      "Add label from selection to the selected instance");
    addLabelButton.setMnemonic(KeyEvent.VK_A);
    filterButtonsPanel.add(addLabelButton);
    filterPanel.add(filterButtonsPanel, gbc);

    mainPanel.add(filterPanel, BorderLayout.NORTH);

    // tables at the bottom
    JPanel tablesPanel = new JPanel(new GridLayout(1, 2));
    instanceTable = new XJTable() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Instance");
    model.addColumn("Labels");
    instanceTable.setModel(model);
    tablesPanel.add(new JScrollPane(instanceTable));
    propertyTable = new XJTable(){
      @Override
      public boolean isCellEditable(int row, int column) {
        // property values are editable
        return convertColumnIndexToModel(column) == 1;
      }
    };
    model = new DefaultTableModel();
    model.addColumn("Property");
    model.addColumn("Value");
    propertyTable.setModel(model);
    tablesPanel.add(new JScrollPane(propertyTable));

    mainPanel.add(tablesPanel, BorderLayout.CENTER);

    initListeners();
  }

  protected void initListeners() {

    clearFilterButton.setAction(
      new AbstractAction("", MainFrame.getIcon("exit.gif")) {
      { this.putValue(MNEMONIC_KEY, KeyEvent.VK_BACK_SPACE);
        this.putValue(SHORT_DESCRIPTION, "Clear text field"); }
      @Override
      public void actionPerformed(ActionEvent e) {
        filterTextField.setText("");
        filterTextField.requestFocusInWindow();
      }
    });

    // when an instance is selected, update the property table
    instanceTable.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
          if (e.getValueIsAdjusting()) { return; }
          updatePropertyTable();
          addLabelButton.setEnabled(newInstanceButton.isEnabled()
                                 && selectedInstance != null);
        }
      }
    );

    // when typing a character in the instance table, use it for filtering
    instanceTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() != KeyEvent.VK_TAB
         && e.getKeyChar() != KeyEvent.VK_SPACE
         && e.getKeyChar() != KeyEvent.VK_BACK_SPACE
         && e.getKeyChar() != KeyEvent.VK_DELETE) {
          filterTextField.requestFocusInWindow();
          filterTextField.setText(String.valueOf(e.getKeyChar()));
        }
      }
    });

    // context menu to delete instances
    instanceTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        processMouseEvent(evt);
      }
      @Override
      public void mousePressed(MouseEvent evt) {
        JTable table = (JTable) evt.getSource();
        int row =  table.rowAtPoint(evt.getPoint());
        if (evt.isPopupTrigger()
        && !table.isRowSelected(row)) {
          // if right click outside the selection then reset selection
          table.getSelectionModel().setSelectionInterval(row, row);
        }
        processMouseEvent(evt);
      }
      @Override
      public void mouseReleased(MouseEvent evt) {
        processMouseEvent(evt);
      }
      protected void processMouseEvent(MouseEvent evt) {
        final JTable table = (JTable) evt.getSource();
        int row = table.rowAtPoint(evt.getPoint());
        if (row >= 0) {
          if (evt.isPopupTrigger()) {
            // context menu
            JPopupMenu popup = new JPopupMenu();
            if (table.getSelectedRowCount() == 1) {
              popup.add(new ShowInstanceInOntologyEditorAction());
              popup.addSeparator();
            }
            if (table.getSelectedRowCount() > 0) {
              popup.add(new DeleteSelectedInstanceAction());
            }
            if (popup.getComponentCount() > 0) {
              popup.show(table, evt.getX(), evt.getY());
            }
          }
        }
      }
    });

    // context menu to delete properties
    propertyTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        processMouseEvent(evt);
      }
      @Override
      public void mousePressed(MouseEvent evt) {
        JTable table = (JTable) evt.getSource();
        int row =  table.rowAtPoint(evt.getPoint());
        if (evt.isPopupTrigger()
        && !table.isRowSelected(row)) {
          // if right click outside the selection then reset selection
          table.getSelectionModel().setSelectionInterval(row, row);
        }
        processMouseEvent(evt);
      }
      @Override
      public void mouseReleased(MouseEvent evt) {
        processMouseEvent(evt);
      }
      protected void processMouseEvent(MouseEvent evt) {
        final JTable table = (JTable) evt.getSource();
        int row = table.rowAtPoint(evt.getPoint());
        if (row >= 0) {
          if (evt.isPopupTrigger()) {
            // context menu
            JPopupMenu popup = new JPopupMenu();
            if (table.getSelectedRowCount() > 0) {
              popup.add(new DeleteSelectedPropertyAction());
            }
            if (popup.getComponentCount() > 0) {
              popup.show(table, evt.getX(), evt.getY());
            }
          }
        }
      }
    });

    // show only the rows containing the text from filterTextField
    filterTextField.getDocument().addDocumentListener(new DocumentListener() {
      private Timer timer = new Timer("Instance view table rows filter", true);
      private TimerTask timerTask;
      @Override
      public void changedUpdate(DocumentEvent e) { /* do nothing */ }
      @Override
      public void insertUpdate(DocumentEvent e) { update(); }
      @Override
      public void removeUpdate(DocumentEvent e) { update(); }
      private void update() {
        if (timerTask != null) { timerTask.cancel(); }
        Date timeToRun = new Date(System.currentTimeMillis() + 300);
        timerTask = new TimerTask() { @Override
        public void run() {
          updateInstanceTable(selectedClass);
        }};
        // add a delay
        timer.schedule(timerTask, timeToRun);
      }
    });

    // Up/Down key events in filterTextField are transferred to the table
    filterTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP
         || e.getKeyCode() == KeyEvent.VK_DOWN
         || e.getKeyCode() == KeyEvent.VK_PAGE_UP
         || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
          instanceTable.dispatchEvent(e);
        }
      }
    });
  }

  @Override
  protected void registerHooks() {
    // show the class view at the right
    if (!classView.isActive()) {
      owner.setRightView(owner.verticalViews.indexOf(classView));
    }
  }

  @Override
  protected void unregisterHooks() {
    // hide the class view at the right
    if (classView.isActive()) {
      owner.setRightView(-1);
    }
  }

  @Override
  public Component getGUI() {
    return mainPanel;
  }

  @Override
  public int getType() {
    return HORIZONTAL;
  }

  /**
   * Update the instance table for the class and ontology selected.
   * @param selectedClass class selected
   */
  public void updateInstanceTable(OClass selectedClass) {
    this.selectedClass = selectedClass;
    instances.clear();
    Set<OInstance> allInstances = new HashSet<OInstance>();
    final DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.addColumn("Instance");
    tableModel.addColumn("Labels");
    if (selectedClass != null) {
      selectedOntology = selectedClass.getOntology();
      allInstances.addAll(selectedOntology.getOInstances(
        selectedClass, OConstants.Closure.TRANSITIVE_CLOSURE));
      String filter = filterTextField.getText()
        .trim().toLowerCase(Locale.ENGLISH);
      for (OInstance instance : allInstances) {
        Set<AnnotationProperty> properties =
          instance.getSetAnnotationProperties();
        boolean hasLabelProperty = false;
        instances.add(instance);
        for (AnnotationProperty property : properties) {
          if (property.getName().equals("label")) {
            hasLabelProperty = true;
            List<Literal> values =
              instance.getAnnotationPropertyValues(property);
            Set<String> labels = new HashSet<String>();
            boolean matchFilter = false;
            for (Literal value : values) {
              labels.add(value.getValue());
              if (value.getValue().toLowerCase().indexOf(filter) != -1) {
                matchFilter = true;
              }
            }
            if (matchFilter) {
              tableModel.addRow(new Object[]{instance.getName(),
                Strings.toString(labels)});
            } else {
              instances.remove(instance);
            }
          }
        }
        if (!hasLabelProperty) {
          // add instance row without label property
          tableModel.addRow(new Object[]{instance.getName(), ""});
        }
      }
    }
    final int hiddenInstances = allInstances.size() - instances.size();
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      hiddenInstancesLabel.setText(" " + hiddenInstances + " hidden ");
      instanceTable.setModel(tableModel);
      if (instanceTable.getRowCount() > 0) {
        instanceTable.setRowSelectionInterval(0, 0);
      }
    }});
  }

  protected void updatePropertyTable() {
    selectedInstance = null;
    final DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.addColumn("Property");
    tableModel.addColumn("Value");
    if (instanceTable.getSelectedRow() != -1) {
      String selectedValue = (String) instanceTable.getValueAt(
        instanceTable.getSelectedRow(),
        instanceTable.convertColumnIndexToView(0));
      for (OInstance instance : instances) {
        if (instance.getName().equals(selectedValue)) {
          // found the instance matching the name in the table
          selectedInstance = instance;
          setProperties.clear();
          properties.clear();
          // get all object properties that can be set for this instance
          Set<OClass> classes =
            instance.getOClasses(OConstants.Closure.DIRECT_CLOSURE);
          for (OClass oClass : classes) {
            for (RDFProperty property :
                 oClass.getPropertiesWithResourceAsDomain()) {
              if (property instanceof ObjectProperty) {
                properties.add((ObjectProperty) property);
                Set<String> ranges = new HashSet<String>();
                Set<OClass> rangeClasses = new HashSet<OClass>();
                for (OResource range :
                    ((ObjectProperty) property).getRange()) {
                  ranges.add(range.getName());
                  rangeClasses.add((OClass) range);
                }
                if (ranges.isEmpty()) {
                  ranges.add("All classes");
                }
                classesByPropertyMap.put(property.getName(), rangeClasses);
                tableModel.addRow(new Object[]{property.getName(),
                  Strings.toString(ranges)});
              }
            }
          }
          // get all set object properties and values for this instance
          for (ObjectProperty objectProperty :
               instance.getSetObjectProperties()) {
            setProperties.add(objectProperty);
            for (OInstance oInstance :
                 instance.getObjectPropertyValues(objectProperty)) {
                  tableModel.addRow(new Object[]{objectProperty.getName(),
                    oInstance.getONodeID().getResourceName()});
            }
          }
          break;
        }
      }
    }
    SwingUtilities.invokeLater(new Runnable() { @Override
    public void run() {
      propertyTable.setModel(tableModel);
      propertyTable.getColumnModel().getColumn(1)
        .setCellEditor(new PropertyValueCellEditor());
      propertyTable.getColumnModel().getColumn(0)
        .setCellRenderer(new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object
            value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(
              table, value, isSelected, hasFocus, row, column);
            setBackground(table.getBackground());
            Object nextValue = table.getModel().getValueAt(row, 1);
            if (nextValue != null && ((String)nextValue).startsWith("[")) {
              // change color for rows that have no values set
              setBackground(new Color(252, 252, 176));
            }
            return this;
          }
        });
      propertyTable.getColumnModel().getColumn(1)
        .setCellRenderer(new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(JTable table, Object
            value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(
              table, value, isSelected, hasFocus, row, column);
            setBackground(table.getBackground());
            if (value != null && ((String)value).startsWith("[")) {
              // change color for rows that have no values set
              setBackground(new Color(252, 252, 176));
            }
            return this;
          }
        });
    }});
  }

  /**
   * Create a new annotation and instance from a text selection.
   * Use the text selected as the instance property label.
   *
   * @param selectedSet name of the selected annotation set
   * @param selectedText selection
   * @param start selection start offset
   * @param end selection end offset
   */
  protected void addSelectionToFilter(final String selectedSet,
      final String selectedText, final int start, final int end) {
    newInstanceButton.setAction(
      new AbstractAction(newInstanceButton.getText()) {
      { this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(SHORT_DESCRIPTION, newInstanceButton.getToolTipText()); }
      @Override
      public void actionPerformed(ActionEvent e) {
        createFromSelection(selectedSet, selectedText, start, end, true);
        filterTextField.setText("");
//        filterTextField.setBackground(
//          UIManager.getColor("TextField.background"));
      }
    });
    newInstanceButton.setEnabled(true);
    addLabelButton.setAction(
      new AbstractAction(addLabelButton.getText()) {
      { this.putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        this.putValue(SHORT_DESCRIPTION, addLabelButton.getToolTipText()); }
      @Override
      public void actionPerformed(ActionEvent e) {
        createFromSelection(selectedSet, selectedText, start, end, false);
        filterTextField.setText("");
//        filterTextField.setBackground(
//          UIManager.getColor("TextField.background"));
      }
    });
    filterTextField.setText(selectedText);
    filterTextField.selectAll();
    filterTextField.requestFocusInWindow();
    addLabelButton.setEnabled(selectedInstance != null);
//    filterTextField.setBackground(new Color(252, 255, 194));
//    filterTextField.setBackground(
//      UIManager.getColor("TextField.background"));
  }

  /**
   * Create a new annotation and instance or label from a text selection.
   * Use the text selected as the instance property label.
   *
   * @param selectedSet name of the selected annotation set
   * @param selectedText selection
   * @param start selection start offset
   * @param end selection end offset
   * @param newInstance true if it will create a new instance otherwise
   * it will add a new label to the selected instance
   */
  protected void createFromSelection(String selectedSet, String selectedText,
                                     int start, int end, boolean newInstance) {
    newInstanceButton.setEnabled(false);
    addLabelButton.setEnabled(false);
    AnnotationProperty annotationProperty;
    RDFProperty property = selectedOntology.getProperty(
      selectedOntology.createOURIForName("label"));
    if (property == null) {
      // create a property 'label' if it doesn't exist
      annotationProperty = selectedOntology.addAnnotationProperty(
        selectedOntology.createOURIForName("label"));
    } else if (property instanceof AnnotationProperty) {
      // get the existing property 'label'
      annotationProperty = (AnnotationProperty) property;
    } else {
      Out.prln("There is already a property 'label' " +
        "that is not an annotation property!");
      return;
    }
    OInstance instance = selectedInstance;
    if (newInstance) {
      // squeeze spaces, replace spaces and HTML characters with underscores
      String instanceName = selectedText.replaceAll("\\s+", "_");
      instanceName = instanceName.replaceAll("<>\"&", "_");
      // take only the first 20 characters of the selection
      if (instanceName.length() > 100) {
        instanceName = instanceName.substring(0, 100);
      }
      OURI instanceOURI = selectedOntology.createOURIForName(instanceName);
      for (int i = 0; selectedOntology.containsOInstance(instanceOURI)
          && i < Integer.MAX_VALUE; i++) {
        // instance name already existing so suffix with a number
        instanceOURI = selectedOntology.createOURIForName(instanceName+'_'+i);
      }
      // create a new instance from the text selected
      instance = selectedOntology.addOInstance(instanceOURI, selectedClass);
    }
    // add a property 'label' with the selected text as value
    instance.addAnnotationPropertyValue(annotationProperty,
      new Literal(selectedText));
    AnnotationSet set = document.getAnnotations(selectedSet);

    try {
      String ontology = selectedOntology.getDefaultNameSpace();
      // to be compatible with KIM and OAT which have
      // ontology feature without ending #
      ontology = ontology.substring(0, ontology.length()-1);
      features = Factory.newFeatureMap();
      features.put(ONTOLOGY, ontology);
      features.put(CLASS, selectedClass.getONodeID().toString());
      features.put(INSTANCE, instance.getONodeID().toString());
      // create a new annotation from the text selected
      set.add((long) start, (long) end, ANNOTATION_TYPE, features);
    } catch(InvalidOffsetException e) {
      throw new GateRuntimeException(e);
    }
    classView.setClassHighlighted(selectedClass, false);
    classView.setClassHighlighted(selectedClass, true);
//    classView.selectInstance(set, set.get(id), selectedClass);
    updateInstanceTable(selectedClass);
  }

  /**
   * Select an instance in the instance table if it exists..
   * @param oInstance instance to be selected
   */
  public void selectInstance(OInstance oInstance) {
    for (int row = 0; row < instanceTable.getRowCount(); row++) {
      if (oInstance.getONodeID().toString().endsWith(
        (String) instanceTable.getValueAt(row, 0))) {
        int rowModel = instanceTable.rowViewToModel(row);
        instanceTable.getSelectionModel()
          .setSelectionInterval(rowModel, rowModel);
        break;
      }
    }
  }

  protected class PropertyValueCellEditor extends AbstractCellEditor
      implements TableCellEditor, ActionListener {
    private JComboBox<String> valueComboBox;
    private Collator comparator;
    private String oldValue;
    private Map<String, OInstance> nameInstanceMap;
    private Pattern instanceLabelsPattern;

    private PropertyValueCellEditor() {
      valueComboBox = new JComboBox<String>();
      valueComboBox.setMaximumRowCount(10);
      valueComboBox.addActionListener(this);
      comparator = Collator.getInstance();
      comparator.setStrength(java.text.Collator.TERTIARY);
      nameInstanceMap = new HashMap<String, OInstance>();
      instanceLabelsPattern = Pattern.compile("^(.+) \\[.*\\]$");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      oldValue = (String) value;
      TreeSet<String> ts = new TreeSet<String>(comparator);
      Set<OClass> classes = classesByPropertyMap.get(propertyTable.getModel().getValueAt(row, 0));
      if (classes.isEmpty()) { classes = selectedOntology.getOClasses(false); }
      for (OClass oClass : classes) {
        // get all the classes that belong to the property domain
        Set<OInstance> instances = selectedOntology.getOInstances(
          oClass, OConstants.Closure.TRANSITIVE_CLOSURE);
        for (OInstance instance : instances) {
          Set<String> labelSet = new HashSet<String>();
          Set<AnnotationProperty> properties =
            instance.getSetAnnotationProperties();
          for (AnnotationProperty property : properties) {
            if (property.getName().equals("label")) {
              List<Literal> labels =
                instance.getAnnotationPropertyValues(property);
              for (Literal label : labels) {
                labelSet.add(label.getValue());
              }
            }
          }
          // for each class add their instance names and labels list
          ts.add(instance.getName() + " " + Strings.toString(labelSet));
          nameInstanceMap.put(instance.getName(), instance);
        }
      }
      DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(ts.toArray(new String[ts.size()]));
      valueComboBox.setModel(dcbm);
      valueComboBox.setSelectedItem(propertyTable.getValueAt(row, column));
      return valueComboBox;
    }

    @Override
    public Object getCellEditorValue() {
      return valueComboBox.getSelectedItem();
    }

    @Override
    protected void fireEditingStopped() {
      String newValue = (String) getCellEditorValue();
      if (newValue == null) {
        fireEditingCanceled();
        return;
      }
      Matcher matcher = instanceLabelsPattern.matcher(newValue);
      // remove the list of labels from the selected instance value
      if (matcher.matches()) { newValue = matcher.group(1); }
      super.fireEditingStopped();
      String selectedProperty = (String) propertyTable.getModel().getValueAt(
        propertyTable.getSelectedRow(), 0);
      // search the object property to set
      for (ObjectProperty objectProperty : setProperties) {
        // verify that the property value correspond
        if (objectProperty.getName().equals(selectedProperty)) {
          for (OInstance oInstance :
               selectedInstance.getObjectPropertyValues(objectProperty)) {
            String value = oInstance.getONodeID().getResourceName();
            if (value.equals(oldValue)) {
              // property already existing, remove it first
              selectedInstance.removeObjectPropertyValue(
                objectProperty, oInstance);
                try {
                  // set the new value for the selected object property
                  selectedInstance.addObjectPropertyValue(
                    objectProperty, nameInstanceMap.get(newValue));
                } catch (InvalidValueException e) {
                  e.printStackTrace();
                }
              updatePropertyTable();
              return;
            }
          }
        }
      }
      for (ObjectProperty objectProperty : properties) {
        if (objectProperty.getName().equals(selectedProperty)) {
          try {
            // set the new value for the selected object property
              selectedInstance.addObjectPropertyValue(
                objectProperty, nameInstanceMap.get(newValue));
          } catch (InvalidValueException e) {
            e.printStackTrace();
          }
          updatePropertyTable();
          return;
        }
      }
    }

    // TODO: itemlistener may be better
    @Override
    public void actionPerformed(ActionEvent e) {
      if (getCellEditorValue() == null) {
        fireEditingCanceled();
      } else {
        fireEditingStopped();
      }
    }
  }

  protected class ShowInstanceInOntologyEditorAction extends AbstractAction {
    public ShowInstanceInOntologyEditorAction() {
      super("Show In Ontology Editor");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
      // show the ontology editor if not already displayed
      SwingUtilities.invokeLater(new Runnable() { @Override
      public void run() {
        final Handle handle = MainFrame.getInstance().select(selectedOntology);
        if (handle == null) { return; }
        // wait some time for the ontology editor to be displayed
        Date timeToRun = new Date(System.currentTimeMillis() + 1000);
        Timer timer = new Timer("Ontology Instance View Timer", true);
        timer.schedule(new TimerTask() { @Override
        public void run() {
          String instanceName = (String) instanceTable.getModel()
            .getValueAt(instanceTable.getSelectedRow(), 0);
          for (OInstance oInstance : instances) {
            if (oInstance.getName().equals(instanceName)) {
              // found the corresponding instance in the ontology
              JComponent largeView = handle.getLargeView();
              if (largeView != null
              && largeView instanceof JTabbedPane
              && ((JTabbedPane)largeView).getSelectedComponent() != null) {
                  ((OntologyEditor) ((JTabbedPane) largeView)
                    .getSelectedComponent())
                      .selectResourceInClassTree(oInstance);
              }
              break;
            }
          }
        }}, timeToRun);
      }});
    }
  }

  protected class DeleteSelectedInstanceAction extends AbstractAction {
    public DeleteSelectedInstanceAction() {
      super(instanceTable.getSelectedRowCount() > 1 ?
        "Delete instances" : "Delete instance");
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift DELETE"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String ontology = selectedOntology.getDefaultNameSpace();
      ontology = ontology.substring(0, ontology.length()-1);
      for (OInstance oInstance : instances) {
        for (int selectedRow : instanceTable.getSelectedRows()) {
          if (oInstance.getName().equals(
              instanceTable.getModel().getValueAt(selectedRow, 0))) {
            selectedOntology.removeOInstance(oInstance);
            // find annotations related to this instance
            AnnotationSet annotationSet =
              document.getAnnotations(classView.getSelectedSet());
            for (Annotation annotation :
              annotationSet.get(ANNOTATION_TYPE)) {
              if (annotation.getFeatures().containsKey(ONTOLOGY)
              && annotation.getFeatures().get(ONTOLOGY)
                .equals(ontology)
              && annotation.getFeatures().containsKey(CLASS)
              && annotation.getFeatures().get(CLASS)
                .equals(selectedClass.getONodeID().toString())
              && annotation.getFeatures().containsKey(INSTANCE)
              && annotation.getFeatures().get(INSTANCE)
                .equals(oInstance.getONodeID().toString())) {
                // delete the annotation
                annotationSet.remove(annotation);
              }
            }
          }
        }
      }
      classView.setClassHighlighted(selectedClass, false);
      classView.setClassHighlighted(selectedClass, true);
      updateInstanceTable(selectedClass);
    }
  }

  protected class DeleteSelectedPropertyAction extends AbstractAction {
    public DeleteSelectedPropertyAction() {
      super(propertyTable.getSelectedRowCount() > 1 ?
        "Delete properties" : "Delete property");
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift DELETE"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      for (ObjectProperty objectProperty : setProperties) {
        for (int selectedRow : propertyTable.getSelectedRows()) {
          // find the property that matches the first column value
          if (objectProperty.getName().equals(
              propertyTable.getModel().getValueAt(selectedRow, 0))) {
            for (OInstance oInstance : selectedInstance
                .getObjectPropertyValues(objectProperty)) {
              String value = oInstance.getONodeID()
                .getResourceName();
              // find the value that matches the second column value
              if (value.equals(propertyTable.getModel()
                  .getValueAt(selectedRow, 1))) {
                // delete the property
                selectedInstance.removeObjectPropertyValue(
                  objectProperty, oInstance);
                break;
              }
            }
          }
        }
      }
      updatePropertyTable();
    }
  }

  // external resources
  protected Ontology selectedOntology;
  protected TextualDocumentView textView;
  protected OntologyClassView classView;

  // UI components
  protected JPanel mainPanel;
  protected JTextField filterTextField;
  protected JButton clearFilterButton;
  protected JLabel hiddenInstancesLabel;
  protected JButton newInstanceButton;
  protected JButton addLabelButton;
  protected XJTable instanceTable;
  protected XJTable propertyTable;

  // local objects
  /** Class that has the lead selection in the focused tree. */
  protected OClass selectedClass;
  /** Instance selected in the instance table. */
  protected OInstance selectedInstance;
  /** Instances in the instance table for the selected class and filter. */
  protected Set<OInstance> instances;
  /** Properties set in the property table for the selected class and filter. */
  protected Set<ObjectProperty> setProperties;
  /** Properties in the instance table for the selected class and filter. */
  protected Set<ObjectProperty> properties;
  protected Map<String, Set<OClass>> classesByPropertyMap;

  // constants for annotation feature, annotation type
  protected static final String ONTOLOGY =
    gate.creole.ANNIEConstants.LOOKUP_ONTOLOGY_FEATURE_NAME;
  protected static final String CLASS =
    gate.creole.ANNIEConstants.LOOKUP_CLASS_FEATURE_NAME;
  protected static final String INSTANCE =
    gate.creole.ANNIEConstants.LOOKUP_INSTANCE_FEATURE_NAME;
  protected static final String ANNOTATION_TYPE = "Mention";
}
