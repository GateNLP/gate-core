package gate.gui.ontology;

import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.Literal;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.OResource;
import gate.creole.ontology.ObjectProperty;
import gate.creole.ontology.RDFProperty;
import gate.creole.ontology.Restriction;
import gate.creole.ontology.SymmetricProperty;
import gate.creole.ontology.TransitiveProperty;
import gate.gui.MainFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

/**
 * A Class that provides a GUI to search for a resource in the ontology
 * editor. It allows looking up for a resource with certain name or to
 * locate a resource that has a certain value set on the specified
 * property.
 * 
 * @author niraj
 * 
 */
@SuppressWarnings("serial")
public class SearchAction extends AbstractAction {

  /**
   * Constructor
   * 
   * @param s - caption of the search button
   * @param icon - icon to be used for the search button
   * @param editor - instance of the ontology editor
   */
  public SearchAction(String s, Icon icon, OntologyEditor editor) {
    super(s, icon);
    this.ontologyEditor = editor;
    guiPanel = new JPanel();
    guiPanel.setLayout(new BoxLayout(guiPanel, BoxLayout.Y_AXIS));
    JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panel1.add(new JLabel("Find what: "));

    resourcesBox = new JComboBox<OResource>();
    resourcesBox.setRenderer(new ComboRenderer());
    resourcesBox.setPrototypeDisplayValue(new RDFPropertyPrototype("this is just an example, not a value. OK?"));
    
    resourcesBox.setEditable(true);
    resourcesBox.setEditable(true);
    resourcesBox.getEditor().getEditorComponent().addKeyListener(
            new KeyAdapter() {
              @Override
              public void keyReleased(KeyEvent keyevent) {
                String s = ((JTextComponent)resourcesBox.getEditor()
                        .getEditorComponent()).getText();
                if(s != null) {
                  if(keyevent.getKeyCode() != KeyEvent.VK_ENTER) {
                    HashSet<OResource> set = new HashSet<OResource>();
                    for(int i = 0; i < resourcesArray.length; i++) {
                      String s1 = resourcesArray[i].getName();
                      if(s1.toLowerCase().startsWith(s.toLowerCase())) {
                        set.add(resourcesArray[i]);
                      }
                    }

                    if(searchInPropertyValues.isSelected()) {
                      RDFProperty aProp = (RDFProperty)properties
                              .getSelectedItem();
                      List<OResource> toAdd = new ArrayList<OResource>();
                      if(aProp instanceof ObjectProperty) {
                        @SuppressWarnings("deprecation")
                        OResource res = ontologyEditor.ontology
                                .getOResourceByName(s);
                        if(res != null) {
                          toAdd = ontologyEditor.ontology.getOResourcesWith(
                                  aProp, res);
                        }
                      }
                      else {
                        toAdd = ontologyEditor.ontology.getOResourcesWith(
                                aProp, new Literal(s));
                      }
                      set.addAll(toAdd);
                    }
                    List<OResource> setList = new ArrayList<OResource>(set);
                    Collections.sort(setList, new OntologyItemComparator());

                    DefaultComboBoxModel<OResource> defaultcomboboxmodel = new DefaultComboBoxModel<OResource>(
                            setList.toArray(new OResource[setList.size()]));

                    resourcesBox.setModel(defaultcomboboxmodel);

                    try {
                      if(!setList.isEmpty()) resourcesBox.showPopup();
                    }
                    catch(Exception exception) {
                    }
                  }
                  ((JTextComponent)resourcesBox.getEditor()
                          .getEditorComponent()).setText(s);
                }
              }
            });
    panel1.add(resourcesBox);
    properties = new JComboBox<RDFProperty>();
    properties.setRenderer(new ComboRenderer());
    properties.setEditable(true);
    properties.setPrototypeDisplayValue(new RDFPropertyPrototype("this is just an example, not a value. OK?"));
    properties.getEditor().getEditorComponent().addKeyListener(
            new KeyAdapter() {
              @Override
              public void keyReleased(KeyEvent keyevent) {
                String s = ((JTextComponent)properties.getEditor()
                        .getEditorComponent()).getText();
                if(s != null) {
                  if(keyevent.getKeyCode() != KeyEvent.VK_ENTER) {
                    ArrayList<RDFProperty> arraylist = new ArrayList<RDFProperty>();
                    for(int i = 0; i < propertiesArray.length; i++) {
                      String s1 = propertiesArray[i].getName();
                      if(s1.toLowerCase().startsWith(s.toLowerCase())) {
                        arraylist.add(propertiesArray[i]);
                      }
                    }
                    Collections.sort(arraylist, new OntologyItemComparator());
                    DefaultComboBoxModel<RDFProperty> defaultcomboboxmodel = new DefaultComboBoxModel<RDFProperty>(
                            arraylist.toArray(new RDFProperty[arraylist.size()]));
                    properties.setModel(defaultcomboboxmodel);

                    try {
                      if(!arraylist.isEmpty()) properties.showPopup();
                    }
                    catch(Exception exception) {
                    }
                  }
                  ((JTextComponent)properties.getEditor().getEditorComponent())
                          .setText(s);
                }
              }
            });

    searchInPropertyValues = new JCheckBox("In the values of: ");
    searchInPropertyValues.setSelected(false);
    panel2.add(searchInPropertyValues);
    panel2.add(properties);

    guiPanel.add(panel1);
    guiPanel.add(panel2);
    resourcesBox.setPreferredSize(new Dimension(300, resourcesBox.getPreferredSize().height));
    properties.setPreferredSize(new Dimension(300, resourcesBox.getPreferredSize().height));
  }

  /**
   * Obtains a list of resources from the ontology being displayed in
   * the ontology editor and invokes the search dialog.
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    @SuppressWarnings("deprecation")
    List<OResource> resources = ontologyEditor.ontology.getAllResources();
    Collections.sort(resources, new OntologyItemComparator());

    resourcesArray = new OResource[resources.size()];
    resourcesArray = resources.toArray(resourcesArray);
    DefaultComboBoxModel<OResource> defaultcomboboxmodel = new DefaultComboBoxModel<OResource>(
            resources.toArray(new OResource[resources.size()]));
    resourcesBox.setModel(defaultcomboboxmodel);

    Set<RDFProperty> props = ontologyEditor.ontology.getRDFProperties();
    props.addAll(ontologyEditor.ontology.getAnnotationProperties());
    props.addAll(ontologyEditor.ontology.getObjectProperties());
    List<RDFProperty> propsList = new ArrayList<RDFProperty>(props);
    Collections.sort(propsList, new OntologyItemComparator());

    propertiesArray = new RDFProperty[props.size()];
    propertiesArray = props.toArray(propertiesArray);
    DefaultComboBoxModel<RDFProperty> defaultcomboboxmodel1 = new DefaultComboBoxModel<RDFProperty>(
            propsList.toArray(new RDFProperty[propsList.size()]));
    properties.setModel(defaultcomboboxmodel1);

    resources = null;
    props = null;
    propsList = null;

    int returnValue = JOptionPane.showOptionDialog(MainFrame.getInstance(),
            guiPanel, "Find Ontology Resource", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, MainFrame.getIcon("search"),
            new String[] {"Find", "Cancel"}, "Find");
    if(returnValue == JOptionPane.OK_OPTION) {
      Object selectedItem = resourcesBox.getSelectedItem();
      if(!(selectedItem instanceof OResource))
        return;
      
      OResource selectedR = (OResource) selectedItem;
      if(selectedR instanceof RDFProperty) {
        ontologyEditor.propertyTree.setSelectionPath(new TreePath(
                ontologyEditor.uri2TreeNodesListMap.get(
                        selectedR.getONodeID().toString()).get(0).getPath()));
        ontologyEditor.propertyTree
                .scrollPathToVisible(ontologyEditor.propertyTree
                        .getSelectionPath());
        ontologyEditor.tabbedPane
                .setSelectedComponent(ontologyEditor.propertyScroller);
      }
      else {
        ontologyEditor.tree.setSelectionPath(new TreePath(
                ontologyEditor.uri2TreeNodesListMap.get(
                        selectedR.getONodeID().toString()).get(0).getPath()));
        ontologyEditor.tree.scrollPathToVisible(ontologyEditor.tree
                .getSelectionPath());
        ontologyEditor.tabbedPane.setSelectedComponent(ontologyEditor.scroller);
      }
    }
  }

  /**
   * Box to show the filtered resources based on the user's input in the
   * find what box.
   */
  protected JComboBox<OResource> resourcesBox;

  /**
   * main guiPanel that holds the search gui components.
   */
  protected JPanel guiPanel;

  /**
   * The editor whose ontology is used for searching in.
   */
  protected OntologyEditor ontologyEditor;

  /**
   * An array that contains a list of resources in which the search
   * function searches in. This list is updated on each invocation of
   * the search function.
   */
  protected OResource[] resourcesArray = new OResource[0];

  /**
   * An array that contains a list of properties in which the search
   * function searches in. This list is updated on each invocation of
   * the search function.
   */
  protected RDFProperty[] propertiesArray = new RDFProperty[0];

  /**
   * combobox that holds the filtered properties based on user's input.
   */
  protected JComboBox<RDFProperty> properties;

  /**
   * Indicates if the search function should search for the find what
   * string in the values of the specified property.
   */
  protected JCheckBox searchInPropertyValues;


  /**
   * Description: This class provides the renderer for the Search comboBox Nodes.
   * @author Niraj Aswani
   * @version 1.0
   */
  public class ComboRenderer extends JPanel implements ListCellRenderer<OResource> {

    /**
     * Class label is shown using this label
     */
    private JLabel label;

    /**
     * ICon label
     */
    private JLabel iconLabel;

    /**
     * Label Panel
     */
    private JPanel labelPanel;
    
    public ComboRenderer() {
      label = new JLabel();
      iconLabel = new JLabel();
      labelPanel = new JPanel(new BorderLayout(5,10));
      ((BorderLayout) labelPanel.getLayout()).setHgap(0);
      labelPanel.add(label);
      
      setLayout(new BorderLayout(5,10));
      ((BorderLayout)getLayout()).setHgap(1);
      add(iconLabel, BorderLayout.WEST);
      add(labelPanel, BorderLayout.CENTER);
      this.setOpaque(true);
    }

    /**
     * Renderer method
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends OResource> list, OResource value,
        int row, boolean isSelected, boolean hasFocus) {

     
      /*if (!(value instanceof OResource)) {
        label.setBackground(Color.white);
        return this;
      }*/
      
      javax.swing.Icon icon = null;
      String conceptName = value.getName();
      iconLabel.setVisible(true);
      if(value instanceof Restriction) {
        icon = MainFrame.getIcon("ontology-restriction");
      }
      else if(value instanceof OClass) {
        icon = MainFrame.getIcon("ontology-class");
      }
      else if(value instanceof OInstance) {
        icon = MainFrame.getIcon("ontology-instance");
      }
      else if(value instanceof AnnotationProperty) {
        icon = MainFrame.getIcon("ontology-annotation-property");
      }
      else if(value instanceof DatatypeProperty) {
        icon = MainFrame.getIcon("ontology-datatype-property");
      }
      else if(value instanceof SymmetricProperty) {
        icon = MainFrame.getIcon("ontology-symmetric-property");
      }
      else if(value instanceof TransitiveProperty) {
        icon = MainFrame.getIcon("ontology-transitive-property");
      }
      else if(value instanceof ObjectProperty) {
        icon = MainFrame.getIcon("ontology-object-property");
      }
      else if(value instanceof RDFProperty) {
        icon = MainFrame.getIcon("ontology-rdf-property");
      }      
      
      iconLabel.setIcon(icon);
      label.setText(conceptName);
      label.setFont(list.getFont());
      return this;
    }
  }  
  

}
