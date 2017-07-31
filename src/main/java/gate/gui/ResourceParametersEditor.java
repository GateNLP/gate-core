/*
 *  Copyright (c) 1995-2013, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 03/10/2001
 *
 *  $Id: ResourceParametersEditor.java 20054 2017-02-02 06:44:12Z markagreenwood $
 *
 */

package gate.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import gate.DocumentFormat;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.corpora.DocumentImpl;
import gate.creole.Parameter;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.swing.XJFileChooser;
import gate.swing.XJTable;
import gate.util.Err;
import gate.util.ExtensionFileFilter;
import gate.util.GateException;
import gate.util.GateRuntimeException;
import gate.util.NameBearer;

/**
 * Allows the editing of a set of parameters for a resource. It needs a
 * pointer to the resource and a list of the parameter names for the
 * parameters that should be displayed. The list of the parameters is
 * actually a list of lists of strings representing parameter
 * disjunctions.
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class ResourceParametersEditor extends XJTable implements CreoleListener {

  public ResourceParametersEditor() {
    initLocalData();
    initGuiComponents();
    initListeners();
    setSortable(true);
    setSortedColumn(0);
    setComparator(0, new ParameterDisjunctionComparator());
    setTabSkipUneditableCell(true);
    setEditCellAsSoonAsFocus(true);
  }
  
  public void init(Resource resource, ResourceData resourceData, List<List<Parameter>> parameters) {
    cleanup();
    this.resource = resource;
    this.resourceData = resourceData;
    if(parameters != null) {
      parameterDisjunctions = new ArrayList<ParameterDisjunction>(parameters.size());
      for(int i = 0; i < parameters.size(); i++) {
        parameterDisjunctions.add(new ParameterDisjunction(resource,
                parameters.get(i)));
      }
    }
    else {
      parameterDisjunctions = null;
    }
    tableModel.fireTableDataChanged();
    fileChooser = MainFrame.getFileChooser();
    // must be saved now as it will be reset when the file chooser is hidden
    fileChooserResource = (resource != null) ?
      resource.getClass().getName() : fileChooser.getResource();
  }
  
  /**
   * Initialises this GUI component.
   * 
   * @param resource the resource for which the parameters need to be
   *          set.
   * @param parameters a list of lists of {@link Parameter} representing
   *          parameter disjunctions.
   */
  public void init(Resource resource, List<List<Parameter>> parameters) {
    init(resource, null, parameters);
  }

  protected void initLocalData() {
    resource = null;
    parameterDisjunctions = null;
    resourceData = null;
  }// protected void initLocalData()

  protected void initGuiComponents() {
    setModel(tableModel = new ParametersTableModel());
    getColumnModel().getColumn(0).setCellRenderer(
            new ParameterDisjunctionRenderer());
    getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer());
    getColumnModel().getColumn(2).setCellRenderer(new BooleanRenderer());
    getColumnModel().getColumn(3).setCellRenderer(new ParameterValueRenderer());
    getColumnModel().getColumn(0).setCellEditor(
            new ParameterDisjunctionEditor());
    getColumnModel().getColumn(3).setCellEditor(new ParameterValueEditor());
    setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);

    setSurrendersFocusOnKeystroke(true);
  }// protected void initGuiComponents()

  protected void initListeners() {
    Gate.getCreoleRegister().addCreoleListener(this);
  }

  /**
   * Cleans the internal data and prepares this object to be collected
   */
  public void cleanup() {
    Gate.getCreoleRegister().removeCreoleListener(this);
    if(parameterDisjunctions != null && parameterDisjunctions.size() > 0) {
      for(int i = 0; i < parameterDisjunctions.size(); i++) {
        parameterDisjunctions.get(i).cleanup();
      }
    }
    resource = null;
    resourceData = null;
  }

  /**
   * Sets the parameters for the resource to their new values as
   * resulted from the user's edits.
   */
  public void setParameters() throws ResourceInstantiationException {
    if(resource == null || parameterDisjunctions == null) return;
    // stop current edits
    if(getEditingColumn() != -1 && getEditingRow() != -1) {
      editingStopped(new ChangeEvent(getCellEditor(getEditingRow(),
              getEditingColumn())));
    }
    // set the parameters
    for(int i = 0; i < parameterDisjunctions.size(); i++) {
      ParameterDisjunction pDisj = parameterDisjunctions
              .get(i);
      resource.setParameterValue(pDisj.getName(), pDisj.getValue());
    }
  }

  /**
   * Does this GUI component allow editing?
   */

  public Resource getResource() {
    return resource;
  }

  /**
   * Gets the current values for the parameters.
   * 
   * @return a {@link FeatureMap} containing the curent values for the
   *         currently selected parameters in each disjunction.
   */
  public FeatureMap getParameterValues() {
    // stop current edits
    if(getEditingColumn() != -1 && getEditingRow() != -1) {
      editingStopped(new ChangeEvent(getCellEditor(getEditingRow(),
              getEditingColumn())));
    }
    // get the parameters
    FeatureMap values = Factory.newFeatureMap();
    if(parameterDisjunctions != null) {
      for(int i = 0; i < parameterDisjunctions.size(); i++) {
        ParameterDisjunction pDisj = parameterDisjunctions.get(i);
        values.put(pDisj.getName(), pDisj.getValue());
      }
    }
    return values;
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
    repaint();
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    repaint();
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
    repaint();
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

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean isEditable() {
    return editable;
  }

  /**
   * Called by other GUI classes that use this as a subcomponent that
   * doesn't need to update with the creole register changes.
   */
  void removeCreoleListenerLink() {
    // this component is only used as a viewer now; it doesn't need to
    // update
    // so we don't need to listen to creole events
    Gate.getCreoleRegister().removeCreoleListener(this);
    if(parameterDisjunctions != null && parameterDisjunctions.size() > 0) {
      for(int i = 0; i < parameterDisjunctions.size(); i++) {
        parameterDisjunctions.get(i).removeCreoleListenerLink();
      }
    }

  }

  ParametersTableModel tableModel;

  Resource resource;
  
  ResourceData resourceData;

  /**
   * A pointer to the filechooser from MainFrame.
   */
  static XJFileChooser fileChooser;

  String fileChooserResource;

  /**
   * A list of {@link ParameterDisjunction}
   */
  protected List<ParameterDisjunction> parameterDisjunctions;

  protected boolean editable = true;

  // inner classes
  protected class ParametersTableModel extends AbstractTableModel {

    @Override
    public int getColumnCount() {
      return 4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch(columnIndex) {
        case 0:
          return ParameterDisjunction.class;
        case 1:
          return String.class;
        case 2:
          return Boolean.class;
        case 3:
          return Object.class;
        default:
          return Object.class;
      }
    }// public Class getColumnClass(int columnIndex)

    @Override
    public String getColumnName(int columnIndex) {
      switch(columnIndex) {
        case 0:
          return "Name";
        case 1:
          return "Type";
        case 2:
          return "Required";
        case 3:
          return "Value";
        default:
          return "?";
      }
    }// public String getColumnName(int columnIndex)

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      switch(columnIndex) {
        case 0:
          return parameterDisjunctions.get(rowIndex).size() > 1;
        case 1:
          return false;
        case 2:
          return false;
        case 3:
          return editable;
        default:
          return false;
      }
    }// public boolean isCellEditable

    @Override
    public int getRowCount() {
      return (parameterDisjunctions == null) ? 0 : parameterDisjunctions.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      ParameterDisjunction pDisj = parameterDisjunctions.get(rowIndex);
      switch(columnIndex) {
        case 0:
          return pDisj;
        case 1:
          String paramType = pDisj.getType();
          return paramType.substring(paramType.lastIndexOf('.') + 1); 
        case 2:
          return pDisj.isRequired();
        case 3:
          return pDisj.getValue();
        default:
          return "?";
      }
    }// public Object getValueAt

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      ParameterDisjunction pDisj = parameterDisjunctions.get(rowIndex);
      switch(columnIndex) {
        case 0: {
          if(aValue instanceof ParameterDisjunction){
            //do nothing
          } else if (aValue instanceof Integer){
            pDisj.setSelectedIndex((Integer) aValue);
          }
          break;
        }
        case 1: {
          break;
        }
        case 2: {
          break;
        }
        case 3: {
          Object oldValue = pDisj.getValue();
          if (!Objects.equals(oldValue, aValue)) {
            pDisj.setValue(aValue);
            if (ResourceParametersEditor.this == null || ResourceParametersEditor.this.resource == null) break;
            try {
              ResourceParametersEditor.this.resource.setParameterValue(pDisj.getName(), pDisj.getValue());
            } catch(ResourceInstantiationException e) {
              e.printStackTrace();
            }            
          }
          break;
        }
        default: {
        }
      }
      tableModel.fireTableCellUpdated(rowIndex, columnIndex);
    }// public void setValueAt
  }// /class FeaturesTableModel extends DefaultTableModel

  class ParameterDisjunctionRenderer extends DefaultTableCellRenderer {
    public ParameterDisjunctionRenderer() {
      combo = new JComboBox();
      class CustomRenderer extends JLabel implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {

          setText(text);
          setIcon(icon);
          return this;
        }
      }
      combo.setRenderer(new CustomRenderer());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
      ParameterDisjunction pDisj = (ParameterDisjunction)value;
      text = pDisj.getName();
      String type = pDisj.getType();
      //iconName = "param";
      
      if(Gate.isGateType(type)) {
        ResourceData rData = Gate.getCreoleRegister().get(type);
        if(rData != null) icon = MainFrame.getIcon(rData.getIcon(),rData.getResourceClassLoader());
      }
      if(pDisj.size() > 1) {
        combo.setModel(new DefaultComboBoxModel(new Object[] {text}));
        return combo;
      }
      // prepare the renderer
      super.getTableCellRendererComponent(table, text, isSelected,
        hasFocus, row, column);
      
      if (icon == null) {
        icon = MainFrame.getIcon("param");
      }
      
      setIcon(icon);
      
      return this;
    }// public Component getTableCellRendererComponent

    // combobox used for OR parameters
    JComboBox combo;

    //String iconName;
    Icon icon = null;

    String text;
  }// class ParameterDisjunctionRenderer

  /**
   * A renderer that displays a File Open button next to a text field.
   * Used for setting URLs from files.
   */
  class ParameterValueRenderer extends DefaultTableCellRenderer {
    public ParameterValueRenderer() {
      fileButton = new JButton(MainFrame.getIcon("open-file"));
      fileButton.setToolTipText("Browse the file system");
      listButton = new JButton(MainFrame.getIcon("edit-list"));
      listButton.setToolTipText("Edit the list");
      fmButton = new JButton(MainFrame.getIcon("edit-list"));
      fmButton.setToolTipText("Edit the feature map");
      textField = new JTextField(){
        @Override
        public Dimension getMinimumSize() {
          //we don't want to be squashed!
          Dimension size = getPreferredSize();
          if(size.width < 300) {
            size.width = 300;
          }
          // we should not be larger than the screen
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          if(size.width > 0.95 * screenSize.width) {
            size.width = (int)0.95 * screenSize.width;
          }
          return size;
        }
      };
      textButtonBox = new JPanel();
      textButtonBox.setLayout(new BoxLayout(textButtonBox, BoxLayout.X_AXIS));
      textButtonBox.setOpaque(false);
      combo = new JComboBox();
      combo.setRenderer(new ResourceRenderer());
    }// CustomObjectRenderer()

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

      ParameterDisjunction pDisj = (ParameterDisjunction)table
        .getValueAt(row, convertColumnIndexToView(0));
      String type = pDisj.getType();
      // set the tooltip
      combo.setToolTipText(pDisj.getComment());
      textField.setToolTipText(pDisj.getComment());
      textButtonBox.setToolTipText(pDisj.getComment());

      if(Gate.isGateType(type)) {
        // Gate type
        if(ResourceParametersEditor.this.isEditable()) {
          combo.setModel(new DefaultComboBoxModel(new Object[] {value == null
                  ? "<none>"
                  : value}));
          return combo;
        }
        else {
          // not editable; we'll just use the text field
          // prepare the renderer
          String text = value == null ? "<none>" : value.toString();
          // super.getTableCellRendererComponent(table, text,
          // isSelected,
          // hasFocus, row, column);
          textField.setText(text);
          return textField;
        }
      }
      else {
        Class<?> typeClass = null;
        try {
          // load type class through GATE classloader
          typeClass = Class.forName(type, true, Gate.getClassLoader());
        }
        catch(ClassNotFoundException cnfe) {
          cnfe.printStackTrace();
        }
        // non Gate type -> we'll use the text field
        String text = (value == null)
                ? ""
                : value.toString();
        // prepare the renderer
        textField.setText(text);
        // super.getTableCellRendererComponent(table, text, isSelected,
        // hasFocus, row, column);

        if(type.equals("java.net.URL") || type.equals("gate.creole.ResourceReference")) {
          if(ResourceParametersEditor.this.isEditable()) {
            textButtonBox.removeAll();
            textField.setText(text);
            // textButtonBox.add(this);
            textButtonBox.add(textField);
            // this.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            // getPreferredSize().height));
            textButtonBox.add(Box.createHorizontalStrut(5));
            textButtonBox.add(fileButton);
            return textButtonBox;
          }
          else {
            // return this;
            return textField;
          }
        }
        else if(typeClass != null
                && Collection.class.isAssignableFrom(typeClass)) {
          // List value
          // setText(textForList((List)value));
          textField.setText(textForList((Collection<?>)value));
          if(ResourceParametersEditor.this.isEditable()) {
            textButtonBox.removeAll();
            // textButtonBox.add(this);
            textButtonBox.add(textField);
            // this.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            // getPreferredSize().height));
            textButtonBox.add(Box.createHorizontalStrut(5));
            textButtonBox.add(listButton);
            return textButtonBox;
          }
        }
        else if(typeClass != null
                && FeatureMap.class.isAssignableFrom(typeClass)) {
          textField.setText(textForFeatureMap((FeatureMap)value));
          if(ResourceParametersEditor.this.isEditable()) {
            textButtonBox.removeAll();
            textButtonBox.add(textField);
            textButtonBox.add(Box.createHorizontalStrut(5));
            textButtonBox.add(fmButton);
            return textButtonBox;
          }
        }
        else if(typeClass != null && typeClass.isEnum()) {
          if(ResourceParametersEditor.this.isEditable()) {
            combo.setModel(new DefaultComboBoxModel(new Object[] {value == null
                    ? "<none>"
                    : value}));
            return combo;
          }
          else {
            return textField;
          }
        }
        else {
          // return this;
          return textField;
        }

        // not actually reachable, but keeps the compiler happy
        return textField;
      }
    }// public Component getTableCellRendererComponent


    JButton fileButton;

    JButton listButton;

    JButton fmButton;

    JComboBox combo;

    JPanel textButtonBox;

    JTextField textField;
  }// class ObjectRenderer extends DefaultTableCellRenderer

  class ParameterDisjunctionComparator implements Comparator<ParameterDisjunction> {
    @Override
    public int compare(ParameterDisjunction pDisj1, ParameterDisjunction pDisj2) {
      return pDisj1.getName().compareTo(pDisj2.getName());
    }
  }

  class ParameterDisjunctionEditor extends DefaultCellEditor {
    public ParameterDisjunctionEditor() {
      super(new JComboBox());
      combo = (JComboBox)super.getComponent();
      class CustomRenderer extends JLabel implements ListCellRenderer {
        public CustomRenderer() {
          setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
          if(isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
          }
          else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
          }

          setFont(list.getFont());

          setText((String)value);

          setIcon(MainFrame.getIcon("param"));
          Parameter[] params = pDisj.getParameters();
          for(int i = 0; i < params.length; i++) {
            Parameter param = params[i];
            if(param.getName().equals(value)) {
              String type = param.getTypeName();
              if(Gate.getCreoleRegister().containsKey(type)) {
                ResourceData rData = Gate.getCreoleRegister()
                        .get(type);
                if(rData != null)
                  setIcon(MainFrame.getIcon(rData.getIcon(),
                      rData.getResourceClassLoader()));
              }
              break;
            }// if(params[i].getName().equals(value))
          }// for(int i = 0; params.length; i++)

          
          return this;
        }
      } // class CustomRenderer extends JLabel implements
        // ListCellRenderer
      combo.setRenderer(new CustomRenderer());
      combo.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          pDisj.setSelectedIndex(combo.getSelectedIndex());
          stopCellEditing();
        }
      });
    }// public ParameterDisjunctionEditor()

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
      pDisj = (ParameterDisjunction)value;
      DefaultComboBoxModel comboModel = new DefaultComboBoxModel(pDisj.getNames());
      combo.setModel(comboModel);
      combo.setSelectedIndex(pDisj.getSelectedIndex());
      return combo;
    }// public Component getTableCellEditorComponent

    @Override
    public Object getCellEditorValue() {
      pDisj.setSelectedIndex(combo.getSelectedIndex());
//      return combo.getSelectedIndex();
      return pDisj;
    }

    @Override
    public boolean stopCellEditing() {
      combo.hidePopup();
      return super.stopCellEditing();
    }

    JComboBox combo;

    ParameterDisjunction pDisj;
  }// class ParameterDisjunctionEditor extends DefaultCellEditor

  class ParameterValueEditor extends AbstractCellEditor implements
                                                       TableCellEditor {
    ParameterValueEditor() {
      combo = new JComboBox();
      combo.setRenderer(new ResourceRenderer());
      combo.setEditable(false);

      textField = new JTextField(20);

      fileButton = new JButton(MainFrame.getIcon("open-file"));
      fileButton.setToolTipText("Browse the file system");
      fileButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
          fileChooser.setDialogTitle("Select a file");
          fileChooser.setResource(fileChooserResource);
          int res = fileChooser.showOpenDialog(ResourceParametersEditor.this);
          if(res == JFileChooser.APPROVE_OPTION) {
            try {
              textField.setText(fileChooser.getSelectedFile().toURI().toURL()
                      .toExternalForm());
            }
            catch(IOException ioe) {
              ioe.printStackTrace();
            }
            fireEditingStopped();
          }
          else {
            fireEditingCanceled();
          }
        }
      });

      listButton = new JButton(MainFrame.getIcon("edit-list"));
      listButton.setToolTipText("Edit the list");
      listButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          List<?> returnedList = listEditor.showDialog();
          if(returnedList != null) {
            listValue = returnedList;
            fireEditingStopped();
          }
          else {
            fireEditingCanceled();
          }
        }
      });

      fmButton = new JButton(MainFrame.getIcon("edit-list"));
      fmButton.setToolTipText("Edit the feature map");
      fmButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          FeatureMap returnedFM = fmEditor.showDialog();
          if(returnedFM != null) {
            fmValue = returnedFM;
            fireEditingStopped();
          }
          else {
            fireEditingCanceled();
          }
        }
      });

      textButtonBox = new JPanel();
      textButtonBox.setLayout(new BoxLayout(textButtonBox, BoxLayout.X_AXIS));
      textButtonBox.setOpaque(false);
      textFieldBoolean = new JTextField();
      textFieldBoolean.setEditable(false);
      textFieldBoolean.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          Boolean value = Boolean.valueOf(textFieldBoolean.getText());
          value = !value;
          textFieldBoolean.setText(value.toString());
        }
      });
      textFieldBoolean.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
          Boolean value = Boolean.valueOf(textFieldBoolean.getText());
          value = !value;
          textFieldBoolean.setText(value.toString());
        }
      });

      textButtonBox.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
          if(!comboUsed) {
            // needed because the focus would otherwise stay
            // on the textButtonBox panel
            textField.requestFocusInWindow();
          }
        }
      });
      // select the opposite element when tab key is pressed
      textField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          JTextField textField = (JTextField) e.getSource();
          if((e.getKeyCode() == KeyEvent.VK_TAB)
          && textField.getParent().getComponentCount() == 3) {
            textField.getParent().getComponent(2).requestFocusInWindow();
            e.consume();
          }
        }
      });

    }// ParameterValueEditor()

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
      comboUsed = false;
      listUsed = false;
      fmUsed = false;
      ParameterDisjunction pDisj = (ParameterDisjunction)table
        .getValueAt(row, convertColumnIndexToView(0));
      type = pDisj.getType();
      // set the tooltip
      combo.setToolTipText(pDisj.getComment());
      textField.setToolTipText(pDisj.getComment());
      textFieldBoolean.setToolTipText(pDisj.getComment());

      if(Gate.isGateType(type)) {
        // Gate type
        comboUsed = true;
        ArrayList<Object> values = new ArrayList<Object>();
        try {
          values.addAll(Gate.getCreoleRegister().getAllInstances(type));
        }
        catch(GateException ge) {
          ge.printStackTrace(Err.getPrintWriter());
        }
        values.add(0, "<none>");
        combo.setModel(new DefaultComboBoxModel(values.toArray()));
        combo.setSelectedItem(value == null ? "<none>" : value);
        return combo;
      }
      else {
        // non Gate type
        Class<?> typeClass = null;
        try {
          // load type class through GATE classloader
          typeClass = Class.forName(type, true, Gate.getClassLoader());
        }
        catch(ClassNotFoundException cnfe) {
          cnfe.printStackTrace();
        }

        textField.setText((value == null) ? "" : value.toString());
        if(type.equals("java.net.URL") || type.equals("gate.creole.ResourceReference")) {
          // clean up all filters
          fileChooser.resetChoosableFileFilters();
          fileChooser.setAcceptAllFileFilterUsed(true);
          fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());
          Parameter param = pDisj.getParameter();
          
          Set<String> sufixes = null;          
          
          //This handles the special case of the sourceUrl param when creating documents
          //so that we get the suffix list from the set of loaded document formats rather
          //than from the data for the resource type we are loading.
          if (resourceData != null) {
            if (param.getName().equals("sourceUrl") && resourceData.getClassName().equals(DocumentImpl.class.getName())) {
              sufixes = DocumentFormat.getSupportedFileSuffixes();
            }
          }
          
          //if we haven't loaded any suffixes then fall back to the original behaviour
          if (sufixes == null || sufixes.isEmpty()) sufixes = param.getSuffixes();
          
          if(sufixes != null) {
            ExtensionFileFilter fileFilter = new ExtensionFileFilter();
            Iterator<String> sufIter = sufixes.iterator();
            while(sufIter.hasNext()) {
              fileFilter.addExtension(sufIter.next());
            }
            fileFilter.setDescription("Known file types " + sufixes.toString());
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(fileFilter);
          }

          textField.setEditable(true);
          textButtonBox.removeAll();
          textButtonBox.add(textField);
          textButtonBox.add(Box.createHorizontalStrut(5));
          textButtonBox.add(fileButton);
          return textButtonBox;
        }
        else if(type.equals("java.lang.Boolean")) {
          textFieldBoolean.setText(value == null ? "false" : value.toString());
          return textFieldBoolean;
        }
        else if(typeClass != null
                && Collection.class.isAssignableFrom(typeClass)) {
          // List value
          listUsed = true;
          Parameter param = pDisj.getParameter();

          listValue = (Collection<?>)value;
          listEditor = new ListEditorDialog(SwingUtilities.getAncestorOfClass(
                  Window.class, ResourceParametersEditor.this),
                  (Collection<?>)value, typeClass, param.getItemClassName());

          textField.setEditable(false);
          textField.setText(textForList((Collection<?>)value));
          textButtonBox.removeAll();
          textButtonBox.add(textField);
          textButtonBox.add(Box.createHorizontalStrut(5));
          textButtonBox.add(listButton);
          return textButtonBox;
        }
        else if(typeClass != null
                && FeatureMap.class.isAssignableFrom(typeClass)) {
          // List value
          fmUsed = true;

          fmValue = (FeatureMap)value;
          fmEditor = new FeatureMapEditorDialog(SwingUtilities
                  .getAncestorOfClass(Window.class,
                          ResourceParametersEditor.this), (FeatureMap)value);

          textField.setEditable(false);
          textField.setText(textForFeatureMap((FeatureMap)value));
          textButtonBox.removeAll();
          textButtonBox.add(textField);
          textButtonBox.add(Box.createHorizontalStrut(5));
          textButtonBox.add(fmButton);
          return textButtonBox;
        }
        else if(typeClass != null && typeClass.isEnum()) {
          comboUsed = true;
          try {
            // extract list of allowable values by reflection - every
            // enum type has a values method returning an array of values
            Method getValuesMethod = typeClass.getMethod("values");
            Object[] enumValues = (Object[])getValuesMethod.invoke(null);
            Object[] comboValues = null;
            Parameter param = pDisj.getParameter();
            // only allow selection of "<none>" for optional parameters
            if(param.isOptional()) {
              comboValues = new Object[enumValues.length + 1];
              comboValues[0] = "<none>";
              System.arraycopy(enumValues, 0, comboValues, 1,enumValues.length);
            }
            else {
              comboValues = enumValues;
            }
            combo.setModel(new DefaultComboBoxModel(comboValues));
            combo.setSelectedItem(value == null ? "<none>" : value);
            return combo;
          }
          catch(Exception ex) {
            throw new GateRuntimeException("Error calling \"values\" method of an "
                    + "enum type", ex);
          }
        }
        else {
          textField.setEditable(true);
          return textField;
        }
      }
    }// getTableCellEditorComponent

    @Override
    public Object getCellEditorValue() {
      if(comboUsed) {
        Object value = combo.getSelectedItem();
        return value == "<none>" ? null : value;
      }
      else if(listUsed) {
        return listValue;
      }
      else if(fmUsed) {
        return fmValue;
      }
      else {
        if(type.equals("java.lang.Boolean")) {
          // get the value from the label
          return Boolean.valueOf(textFieldBoolean.getText());
        }
        else {
          // get the value from the text field
          return textField.getText();
          // return ((textField.getText().equals("")) ? null :
          // textField.getText());
        }
      }
    }// public Object getCellEditorValue()

    /**
     * The type of the value currently being edited
     */
    String type;

    /**
     * Combobox use as editor for Gate objects (chooses between
     * instances)
     */
    JComboBox combo;

    /**
     * Generic editor for all types that are not treated special
     */
    JTextField textField;

    /**
     * Editor used for boolean values.
     */
    JTextField textFieldBoolean;

    ListEditorDialog listEditor = null;

    Collection<?> listValue;

    FeatureMapEditorDialog fmEditor = null;

    FeatureMap fmValue;

    boolean comboUsed;

    boolean listUsed;

    boolean fmUsed;

    JButton fileButton;

    JButton listButton;

    JButton fmButton;

    /** Contains a textfield and a button */
    JPanel textButtonBox;
  }// /class ParameterValueEditor

  /**
   * Gets a string representation for a list value
   */
  protected String textForList(Collection<?> list) {
    if(list == null || list.isEmpty()) return "[]";
    StringBuilder res = new StringBuilder("[");
    Iterator<?> elemIter = list.iterator();
    while(elemIter.hasNext()) {
      Object elem = elemIter.next();
      if(elem != null)
        res.append((elem instanceof NameBearer) ?
          ((NameBearer) elem).getName() : elem.toString())
          .append(", ");
      else res.append("<null>, ");
    }
    res.delete(res.length() - 2, res.length() - 1);
    res.append("]");
    return res.toString();
  }

  /**
   * Get a string representation for a FeatureMap value.
   */
  protected String textForFeatureMap(FeatureMap fm) {
    return (fm == null) ? "" : fm.toString();
  }

}// class NewResourceDialog
