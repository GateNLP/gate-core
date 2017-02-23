/*
 * Copyright (c) 1998-2005, The University of Sheffield.
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * FeaturesSchemaEditor.java
 * 
 * Valentin Tablan, May 18, 2004
 * 
 * $Id: FeaturesSchemaEditor.java 18884 2015-08-25 17:23:21Z markagreenwood $
 */
package gate.gui;

import gate.Factory;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.AbstractResource;
import gate.creole.AnnotationSchema;
import gate.creole.FeatureSchema;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.event.FeatureMapListener;
import gate.swing.XJTable;
import gate.util.FeatureBearer;
import gate.util.GateRuntimeException;
import gate.util.ObjectComparator;
import gate.util.Strings;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
@CreoleResource(name = "Resource Features", guiType = GuiType.SMALL,
    resourceDisplayed = "gate.util.FeatureBearer")
public class FeaturesSchemaEditor extends XJTable
        implements ResizableVisualResource, FeatureMapListener{
  public FeaturesSchemaEditor(){
//    setBackground(UIManager.getDefaults().getColor("Table.background"));
    instance = this;
  }

  public void setTargetFeatures(FeatureMap features){
    if(targetFeatures != null) targetFeatures.removeFeatureMapListener(this);
    this.targetFeatures = features;
    populate();
    if(targetFeatures != null) targetFeatures.addFeatureMapListener(this);
  }
  
  
  @Override
  public void cleanup() {
    if(targetFeatures != null){
      targetFeatures.removeFeatureMapListener(this);
      targetFeatures = null;
    }
    target = null;
    schema = null;
  }

  /* (non-Javadoc)
   * @see gate.VisualResource#setTarget(java.lang.Object)
   */
  @Override
  public void setTarget(Object target){
    this.target = (FeatureBearer)target;
    setTargetFeatures(this.target.getFeatures());
  }
  
  public void setSchema(AnnotationSchema schema){
    this.schema = schema;
    featuresModel.fireTableRowsUpdated(0, featureList.size() - 1);
  }
    
  /* (non-Javadoc)
   * @see gate.event.FeatureMapListener#featureMapUpdated()
   * Called each time targetFeatures is changed.
   */
  @Override
  public void featureMapUpdated(){
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        populate();    
      }
    });
  }
  
  
  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    featureList = new ArrayList<Feature>();
    emptyFeature = new Feature("", null);
    featureList.add(emptyFeature);
    initGUI();
    return this;
  }//init()
  
  protected void initGUI(){
    featuresModel = new FeaturesTableModel();
    setModel(featuresModel);
    setTableHeader(null);
    setSortable(false);
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    setShowGrid(false);
    setBackground(getBackground());
    setIntercellSpacing(new Dimension(2,2));
    setTabSkipUneditableCell(true);
    setEditCellAsSoonAsFocus(true);
    featureEditorRenderer = new FeatureEditorRenderer();
    getColumnModel().getColumn(ICON_COL).
        setCellRenderer(featureEditorRenderer);
    getColumnModel().getColumn(NAME_COL).
        setCellRenderer(featureEditorRenderer);
    getColumnModel().getColumn(NAME_COL).
        setCellEditor(featureEditorRenderer);
    getColumnModel().getColumn(VALUE_COL).
        setCellRenderer(featureEditorRenderer);
    getColumnModel().getColumn(VALUE_COL).
        setCellEditor(featureEditorRenderer);
    getColumnModel().getColumn(DELETE_COL).
        setCellRenderer(featureEditorRenderer);
    getColumnModel().getColumn(DELETE_COL).
      setCellEditor(featureEditorRenderer);
    
//    //the background colour seems to change somewhere when using the GTK+ 
//    //look and feel on Linux, so we copy the value now and set it 
//    Color tableBG = getBackground();
//    //make a copy of the value (as the reference gets changed somewhere)
//    tableBG = new Color(tableBG.getRGB());
//    setBackground(tableBG);

    // allow Tab key to select the next cell in the table
    setSurrendersFocusOnKeystroke(true);
    setFocusCycleRoot(true);

    // remove (shift) control tab as traversal keys
    Set<AWTKeyStroke> keySet = new HashSet<AWTKeyStroke>(
      getFocusTraversalKeys(
      KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
    keySet.remove(KeyStroke.getKeyStroke("control TAB"));
    setFocusTraversalKeys(
      KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keySet);
    keySet = new HashSet<AWTKeyStroke>(
      getFocusTraversalKeys(
      KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
    keySet.remove(KeyStroke.getKeyStroke("shift control TAB"));
    setFocusTraversalKeys(
      KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keySet);

    // add (shift) control tab to go the container of this component
    keySet.clear();
    keySet.add(KeyStroke.getKeyStroke("control TAB"));
    setFocusTraversalKeys(
      KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, keySet);
    keySet.clear();
    keySet.add(KeyStroke.getKeyStroke("shift control TAB"));
    setFocusTraversalKeys(
      KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS, keySet);
  }
  
  /**
   * Called internally whenever the data represented changes.
   * Get feature names from targetFeatures and schema then sort them
   * and add them to featureList.
   * Fire a table data changed event for the feature table whith featureList
   * used as data model.
   */
  protected void populate(){
    featureList.clear();
    //get all the existing features
    Set fNames = new HashSet();
    
    if(targetFeatures != null){
      //add all the schema features
      fNames.addAll(targetFeatures.keySet());
      if(schema != null && schema.getFeatureSchemaSet() != null){
        for(FeatureSchema featureSchema : schema.getFeatureSchemaSet()) {
          //        if(featureSchema.isRequired())
          fNames.add(featureSchema.getFeatureName());
        }
      }
      List featureNames = new ArrayList(fNames);
      Collections.sort(featureNames);
      for(Object featureName : featureNames) {
        String name = (String) featureName;
        Object value = targetFeatures.get(name);
        featureList.add(new Feature(name, value));
      }
    }
    if (!featureList.contains(emptyFeature)) {
      featureList.add(emptyFeature);
    }
    featuresModel.fireTableDataChanged();
//    mainTable.setSize(mainTable.getPreferredScrollableViewportSize());
  }

  FeatureMap targetFeatures;
  FeatureBearer target;
  Feature emptyFeature;
  AnnotationSchema schema;
  FeaturesTableModel featuresModel;
  List<Feature> featureList;
  FeatureEditorRenderer featureEditorRenderer;
  FeaturesSchemaEditor instance;
  
  private static final int COLUMNS = 4;
  private static final int ICON_COL = 0;
  private static final int NAME_COL = 1;
  private static final int VALUE_COL = 2;
  private static final int DELETE_COL = 3;
  
  private static final Color REQUIRED_WRONG = Color.RED;
  private static final Color OPTIONAL_WRONG = Color.ORANGE;

  protected class Feature{
    String name;
    Object value;

    public Feature(String name, Object value){
      this.name = name;
      this.value = value;
    }
    boolean isSchemaFeature(){
      return schema != null && schema.getFeatureSchema(name) != null;
    }
    boolean isCorrect(){
      if(schema == null) return true;
      FeatureSchema fSchema = schema.getFeatureSchema(name);
      return fSchema == null || fSchema.getPermittedValues() == null||
             fSchema.getPermittedValues().contains(value);
    }
    boolean isRequired(){
      if(schema == null) return false;
      FeatureSchema fSchema = schema.getFeatureSchema(name);
      return fSchema != null && fSchema.isRequired();
    }
    Object getDefaultValue(){
      if(schema == null) return null;
      FeatureSchema fSchema = schema.getFeatureSchema(name);
      return fSchema == null ? null : fSchema.getFeatureValue();
    }
  }
  
  
  protected class FeaturesTableModel extends AbstractTableModel{
    @Override
    public int getRowCount(){
      return featureList.size();
    }
    
    @Override
    public int getColumnCount(){
      return COLUMNS;
    }
    
    @Override
    public Object getValueAt(int row, int column){
      Feature feature = featureList.get(row);
      switch(column){
        case NAME_COL:
          return feature.name;
        case VALUE_COL:
          return feature.value;
        default:
          return null;
      }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
      return columnIndex == VALUE_COL || columnIndex == NAME_COL || 
             columnIndex == DELETE_COL;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex,  int columnIndex){
      Feature feature = featureList.get(rowIndex);
      if (feature == null) { return; }
      if(targetFeatures == null){
        targetFeatures = Factory.newFeatureMap();
        target.setFeatures(targetFeatures);
        setTargetFeatures(targetFeatures);
      }
      switch(columnIndex){
        case VALUE_COL:
          if (feature.value != null
           && feature.value.equals(aValue)) { return; }
          feature.value = aValue;
          if(feature.name != null && feature.name.length() > 0){
            targetFeatures.removeFeatureMapListener(instance);
            targetFeatures.put(feature.name, aValue);
            targetFeatures.addFeatureMapListener(instance);
            SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run() {
                // edit the last row that is empty
                FeaturesSchemaEditor.this.editCellAt(FeaturesSchemaEditor.this.getRowCount() - 1, NAME_COL);
              }
            });
          }
          break;
        case NAME_COL:
          if (feature.name.equals(aValue)) {
            return;
          }
          targetFeatures.remove(feature.name);
          feature.name = (String)aValue;
          targetFeatures.put(feature.name, feature.value);
          if(feature == emptyFeature) emptyFeature = new Feature("", null);
          populate();
          int newRow;
          for (newRow = 0; newRow < FeaturesSchemaEditor.this.getRowCount(); newRow++) {
            if (FeaturesSchemaEditor.this.getValueAt(newRow, NAME_COL).equals(feature.name)) {
              break; // find the previously selected row in the new table
            }
          }
          final int newRowF = newRow;
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              // edit the cell containing the value associated with this name
              FeaturesSchemaEditor.this.editCellAt(newRowF, VALUE_COL);
            }
          });
          break;
        case DELETE_COL:
          //nothing
          break;
        default:
          throw new GateRuntimeException("Non editable cell!");
      }
      
    }
    
    @Override
    public String getColumnName(int column){
      switch(column){
        case NAME_COL:
          return "Name";
        case VALUE_COL:
          return "Value";
        case DELETE_COL:
          return "";
        default:
          return null;
      }
    }
  }


  protected class FeatureEditorRenderer extends DefaultCellEditor
                                        implements TableCellRenderer {
    
    @Override
    public boolean stopCellEditing() {
      // this is a fix for a bug in Java 8 where tabbing out of the
      // combo box doesn't store the value like it does in java 7
      editorCombo.setSelectedItem(editorCombo.getEditor().getItem());
      return super.stopCellEditing();
    }
    
    public FeatureEditorRenderer(){
      super(new JComboBox());
      defaultComparator = new ObjectComparator();
      editorCombo = (JComboBox)editorComponent;
      editorCombo.setModel(new DefaultComboBoxModel());
      editorCombo.setEditable(true);
      editorCombo.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){
          stopCellEditing();
        }
      });
      defaultBackground = editorCombo.getEditor().getEditorComponent()
          .getBackground();
      
      rendererCombo = new JComboBox(){
        @Override
        public Dimension getMaximumSize() {
          return getPreferredSize();
        }
        @Override
        public Dimension getMinimumSize() {
          return getPreferredSize();
        }
      };
      rendererCombo.setModel(new DefaultComboBoxModel());
      rendererCombo.setEditable(true);
      rendererCombo.setOpaque(false);
      
      requiredIconLabel = new JLabel(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
        @Override
        public Dimension getMaximumSize() {
          return getPreferredSize();
        }
        @Override
        public Dimension getMinimumSize() {
          return getPreferredSize();
        }        
      };
      requiredIconLabel.setIcon(MainFrame.getIcon("r"));
      requiredIconLabel.setOpaque(false);
      requiredIconLabel.setToolTipText("Required feature");
      
      optionalIconLabel = new JLabel(){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                                          Object oldValue,
                                          Object newValue){}
        @Override
        public Dimension getMaximumSize() {
          return getPreferredSize();
        }
        @Override
        public Dimension getMinimumSize() {
          return getPreferredSize();
        }
      };
      optionalIconLabel.setIcon(MainFrame.getIcon("o"));
      optionalIconLabel.setOpaque(false);
      optionalIconLabel.setToolTipText("Optional feature");

      nonSchemaIconLabel = new JLabel(MainFrame.getIcon("c")){
        @Override
        public void repaint(long tm, int x, int y, int width, int height){}
        @Override
        public void repaint(Rectangle r){}
        @Override
        public void validate(){}
        @Override
        public void revalidate(){}
        @Override
        protected void firePropertyChange(String propertyName,
                													Object oldValue,
                													Object newValue){}
        @Override
        public Dimension getMaximumSize() {
          return getPreferredSize();
        }
        @Override
        public Dimension getMinimumSize() {
          return getPreferredSize();
        }
      };
      nonSchemaIconLabel.setToolTipText("Custom feature");
      nonSchemaIconLabel.setOpaque(false);
      
      deleteButton = new JButton(MainFrame.getIcon("delete"));
      deleteButton.setMargin(new Insets(0,0,0,0));
      deleteButton.setToolTipText("Delete");
      deleteButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){
          int row = FeaturesSchemaEditor.this.getEditingRow();
          if(row < 0) return;
          Feature feature = featureList.get(row);
          if(feature == emptyFeature){
            feature.value = null;
          }else{
            featureList.remove(row);
            targetFeatures.remove(feature.name);
            featuresModel.fireTableRowsDeleted(row, row);
//            mainTable.setSize(mainTable.getPreferredScrollableViewportSize());
          }
        }
      });
    }    
		
  	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
         boolean isSelected, boolean hasFocus, int row, int column){
      Feature feature = featureList.get(row);
      switch(column){
        case ICON_COL: 
          return feature.isSchemaFeature() ? 
                 (feature.isRequired() ? 
                         requiredIconLabel : 
                         optionalIconLabel) :
                         nonSchemaIconLabel;  
        case NAME_COL:
          prepareCombo(rendererCombo, row, column);
          rendererCombo.getPreferredSize();
          return rendererCombo;
        case VALUE_COL:
          prepareCombo(rendererCombo, row, column);
          return rendererCombo;
        case DELETE_COL: return deleteButton;  
        default: return null;
      }
    }
  
    @Override
    public Component getTableCellEditorComponent(JTable table,  Object value, 
            boolean isSelected, int row, int column){
      switch(column){
        case NAME_COL:
          prepareCombo(editorCombo, row, column);
          return editorCombo;
        case VALUE_COL:
          prepareCombo(editorCombo, row, column);
          return editorCombo;
        case DELETE_COL: return deleteButton;  
        default: return null;
      }

    }
  
    protected void prepareCombo(JComboBox combo, int row, int column){
      Feature feature = featureList.get(row);
      DefaultComboBoxModel comboModel = (DefaultComboBoxModel)combo.getModel(); 
      comboModel.removeAllElements();
      switch(column){
        case NAME_COL:
          List<String> fNames = new ArrayList<String>();
          if(schema != null && schema.getFeatureSchemaSet() != null){
            Iterator<FeatureSchema> fSchemaIter = schema.getFeatureSchemaSet().iterator();
            while(fSchemaIter.hasNext())
              fNames.add(fSchemaIter.next().getFeatureName());
          }
          if(!fNames.contains(feature.name))fNames.add(feature.name);
          Collections.sort(fNames);
          for(Iterator<String> nameIter = fNames.iterator(); 
              nameIter.hasNext(); 
              comboModel.addElement(nameIter.next()));
          combo.getEditor().getEditorComponent().setBackground(defaultBackground);          
          combo.setSelectedItem(feature.name);
          break;
        case VALUE_COL:
          List<Object> fValues = new ArrayList<Object>();
          if(feature.isSchemaFeature()){
            Set<Object> permValues = schema.getFeatureSchema(feature.name).
              getPermittedValues();
            if(permValues != null) fValues.addAll(permValues);
          }
          if(!fValues.contains(feature.value)) fValues.add(feature.value);
          Collections.sort(fValues, defaultComparator);
          for(Iterator<Object> valIter = fValues.iterator(); 
              valIter.hasNext(); 
              comboModel.addElement(valIter.next()));
          combo.getEditor().getEditorComponent().setBackground(feature.isCorrect() ?
              defaultBackground :
              (feature.isRequired() ? REQUIRED_WRONG : OPTIONAL_WRONG));
          combo.setSelectedItem(feature.value);
          break;
        default: ;
      }
      
    }

    JLabel requiredIconLabel;
    JLabel optionalIconLabel;
    JLabel nonSchemaIconLabel;
    JComboBox editorCombo;
    JComboBox rendererCombo;
    JButton deleteButton;
    ObjectComparator defaultComparator;
    Color defaultBackground;
  }
  
  /* 
   * Resource implementation 
   */
  /** Accessor for features. */
  @Override
  public FeatureMap getFeatures(){
    return features;
  }//getFeatures()

  /** Mutator for features*/
  @Override
  public void setFeatures(FeatureMap features){
    this.features = features;
  }// setFeatures()


  /**
   * Used by the main GUI to tell this VR what handle created it. The VRs can
   * use this information e.g. to add items to the popup for the resource.
   */
  @Override
  public void setHandle(Handle handle){
    this.handle = handle;
  }

  //Parameters utility methods
  /**
   * Gets the value of a parameter of this resource.
   * @param paramaterName the name of the parameter
   * @return the current value of the parameter
   */
  @Override
  public Object getParameterValue(String paramaterName)
                throws ResourceInstantiationException{
    return AbstractResource.getParameterValue(this, paramaterName);
  }

  /**
   * Sets the value for a specified parameter.
   *
   * @param paramaterName the name for the parameteer
   * @param parameterValue the value the parameter will receive
   */
  @Override
  public void setParameterValue(String paramaterName, Object parameterValue)
              throws ResourceInstantiationException{
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInf = null;
    try {
      resBeanInf = Introspector.getBeanInfo(this.getClass(), Object.class);
    } catch(Exception e) {
      throw new ResourceInstantiationException(
        "Couldn't get bean info for resource " + this.getClass().getName()
        + Strings.getNl() + "Introspector exception was: " + e
      );
    }
    AbstractResource.setParameterValue(this, resBeanInf, paramaterName, parameterValue);
  }

  /**
   * Sets the values for more parameters in one step.
   *
   * @param parameters a feature map that has paramete names as keys and
   * parameter values as values.
   */
  @Override
  public void setParameterValues(FeatureMap parameters)
              throws ResourceInstantiationException{
    AbstractResource.setParameterValues(this, parameters);
  }

  // Properties for the resource
  protected FeatureMap features;
  
  /**
   * The handle for this visual resource
   */
  protected Handle handle;
  
  
}
