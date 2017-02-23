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
 *  $Id: DetailsTableModel.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.InvalidValueException;
import gate.creole.ontology.Literal;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OConstants;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.OResource;
import gate.creole.ontology.OValue;
import gate.creole.ontology.ObjectProperty;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.RDFProperty;
import gate.creole.ontology.Restriction;
import gate.gui.MainFrame;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * A DataModel that is created when a node is selected in the ontology
 * tree. It contains information such as direct/all sub/super classes,
 * equivalent classes, instances, properties/ property values and so on.
 * The information from this model is then shown in the right hand side
 * panel of the ontology editor.
 * 
 * @author niraj
 * 
 */
public class DetailsTableModel extends AbstractTableModel {
  private static final long serialVersionUID = 3834870286880618035L;

  public DetailsTableModel() {
    ontologyMode = false;
    resourceInfo = new DetailsGroup("Resource Information",true,null);
    directSuperClasses = new DetailsGroup("Direct Super Classes", true, null);
    allSuperClasses = new DetailsGroup("All Super Classes", true, null);
    directSubClasses = new DetailsGroup("Direct Sub Classes", true, null);
    allSubClasses = new DetailsGroup("All Sub Classes", true, null);
    equivalentClasses = new DetailsGroup("Equivalent Classes", true, null);
    sameAsInstances = new DetailsGroup("Same Instances", true, null);
    instances = new DetailsGroup("Instances", true, null);
    propertyTypes = new DetailsGroup("Property Types", true, null);
    propertyValues = new DetailsGroup("Property Values", true, null);
    directTypes = new DetailsGroup("Direct Types", true, null);
    allTypes = new DetailsGroup("All Types", true, null);
    detailGroups = new DetailsGroup[0];
    itemComparator = new OntologyItemComparator();
  }

  @Override
  public int getColumnCount() {
    return COLUMN_COUNT;
  }

  @Override
  public int getRowCount() {
    int count = detailGroups.length;
    for(int j = 0; j < detailGroups.length; j++)
      if(detailGroups[j].isExpanded()) {
        count += detailGroups[j].getSize();
      }
    return count;
  }

  @Override
  public String getColumnName(int column) {
    switch(column) {
      case EXPANDED_COLUMN:
        return "";
      case LABEL_COLUMN:
        return "";
      case VALUE_COLUMN:
        return "Value";
      case DELETE_COLUMN:
        return "Delete";
    }
    return "";
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch(columnIndex) {
      case EXPANDED_COLUMN:
        return String.class;
      case LABEL_COLUMN:
        return Object.class;
      case VALUE_COLUMN:
        return String.class;
      case DELETE_COLUMN:
        return Object.class;
      
    }
    return Object.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex != VALUE_COLUMN) { return false; }
    Object object = getItemForRow(rowIndex);
    if (!(object instanceof PropertyValue)) { return false; }
    RDFProperty property = ((PropertyValue) object).getProperty();
    return property instanceof AnnotationProperty
        || property instanceof DatatypeProperty;
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    Object object = getItemForRow(row);
    switch (col) {
      case EXPANDED_COLUMN:
        if (object instanceof DetailsGroup) {
          DetailsGroup detailsgroup = (DetailsGroup) object;
          detailsgroup.setExpanded((Boolean) value);
        }
        break;
      case VALUE_COLUMN:
        if (object instanceof PropertyValue) {
          PropertyValue propertyValue = (PropertyValue) object;
          RDFProperty property = propertyValue.getProperty();
          if (property instanceof AnnotationProperty) {
            // update the ontology
            oResource.removeAnnotationPropertyValue((AnnotationProperty)
              property, (Literal) propertyValue.getValue());
            oResource.addAnnotationPropertyValue((AnnotationProperty)
              property, new Literal((String) value));
            // update the data structure for this table
            setItemForRow(row, new PropertyValue(
              property, new Literal((String) value)));
          } else if (property instanceof DatatypeProperty) {
            boolean isValidValue = ((DatatypeProperty)property)
              .getDataType().isValidValue((String) value);
            if (!isValidValue) {
              JOptionPane.showMessageDialog(MainFrame.getInstance(),
                "Incompatible value: " + value +
                "\nUse a value of type " + ((DatatypeProperty)property)
                .getDataType().getXmlSchemaURIString()
                  .replaceFirst("http://www.w3.org/2001/XMLSchema#", ""));
              return;
            }
            try {
              ((OInstance)oResource).removeDatatypePropertyValue(
                (DatatypeProperty)property, (Literal)propertyValue.getValue());
              ((OInstance)oResource).addDatatypePropertyValue(
                (DatatypeProperty)property, new Literal((String) value,
                  ((DatatypeProperty)property).getDataType()));
              setItemForRow(row, new PropertyValue(
                property, new Literal((String) value)));
            } catch(InvalidValueException e) {
              JOptionPane.showMessageDialog(MainFrame.getInstance(),
                      "Incompatible value");
              e.printStackTrace();
              return;
            }
          }
        }
        break;
    }
    // redraw the table
    fireTableDataChanged();
  }

  protected Object getItemForRow(int row) {
    int groupRow = 0;
    for (int groupIndex = 0; groupRow <= row; groupIndex++) {
      if (groupRow == row) {
        return detailGroups[groupIndex];
      }
      int groupSize = 1 + (detailGroups[groupIndex].isExpanded() ?
        detailGroups[groupIndex].getSize() : 0);
      if (groupRow + groupSize > row) {
        return detailGroups[groupIndex].getValueAt(row - groupRow - 1);
      }
      groupRow += groupSize;
    }
    return null;
  }

  protected void setItemForRow(int row, Object value) {
    int groupRow = 0;
    for (int groupIndex = 0; groupRow <= row; groupIndex++) {
      if (groupRow == row) {
        detailGroups[groupIndex].setExpanded((Boolean) value);
        return;
      }
      int groupSize = 1 + (detailGroups[groupIndex].isExpanded() ?
        detailGroups[groupIndex].getSize() : 0);
      if (groupRow + groupSize > row) {
        detailGroups[groupIndex].setValueAt(row - groupRow - 1, value);
        return;
      }
      groupRow += groupSize;
    }
  }

  @Override
  public Object getValueAt(int row, int col) {
    Object object = getItemForRow(row);
    switch(col) {
      case EXPANDED_COLUMN:
        return (object instanceof DetailsGroup) ?
                 ((DetailsGroup) object).getSize() > 0 ?
                   ((DetailsGroup) object).isExpanded() ?
                     "expanded" : "closed" : "empty" : null;
      case LABEL_COLUMN:
        return object;
      case VALUE_COLUMN:
        if (object instanceof DetailsGroup) {
          return "";
        } else if (object instanceof PropertyValue) {
          PropertyValue property = (PropertyValue) object;
          if(property.getValue() instanceof Literal) {
            return ((Literal)property.getValue()).getValue();
          } else {
            return property.getValue().toString();
          }
        } else if (object instanceof KeyValuePair) {
            KeyValuePair keyValuePair = (KeyValuePair) object;
            return keyValuePair.getValue().toString();
        } else if (object instanceof RDFProperty) {
          RDFProperty property = (RDFProperty) object;
          if (property instanceof DatatypeProperty) {
            return ((DatatypeProperty)property).getDataType()
              .getXmlSchemaURIString();
          } else if (!(property instanceof AnnotationProperty)) {
            @SuppressWarnings("deprecation")
            Set<OResource> set = property.getRange();
            if (set == null || set.isEmpty()) {
              return "[ALL CLASSES]";
            } else {
              String s = "[";
              boolean firstTime = true;
              for(OResource res : set) {
                if (!firstTime) {
                  s += ",";
                } else {
                  firstTime = false;
                }
                s += res.getName();
              }
              s += "]";
              return s;
            }
          } else {
            return "[ALL RESOURCES]";
          }
        } else {
          return object.toString();
        }
      case DELETE_COLUMN:
        return object;
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public void setItem(OResource oResource) {
    this.oResource = oResource;
    if(oResource instanceof OClass) {
      detailGroups = new DetailsGroup[] {resourceInfo, directSuperClasses, allSuperClasses,
          directSubClasses, allSubClasses, equivalentClasses, propertyTypes,
          propertyValues, instances};
      OClass tclass = (OClass)oResource;
      resourceInfo.getValues().clear();
      if(tclass instanceof Restriction) {
          resourceInfo.getValues().addAll(Utils.getDetailsToAdd(tclass));
      } else {
        resourceInfo.getValues().add(tclass);
        resourceInfo.getValues().add(new KeyValuePair(tclass, "URI", tclass.getONodeID().toString(), false));
        resourceInfo.getValues().add(new KeyValuePair(tclass, "TYPE", "Ontology Class", false));
      }
      
      // direct super classes
      Set<OClass> set = tclass.getSuperClasses(OConstants.Closure.DIRECT_CLOSURE);
      directSuperClasses.getValues().clear();
      directSuperClasses.getValues().addAll(set);
      Collections.sort(directSuperClasses.getValues(), itemComparator);

      // all super classes
      Set<OClass> set1 = tclass.getSuperClasses(OConstants.Closure.TRANSITIVE_CLOSURE);
      allSuperClasses.getValues().clear();
      allSuperClasses.getValues().addAll(set1);
      Collections.sort(allSuperClasses.getValues(), itemComparator);
      

      // direct subclasses
      Set<OClass> set2 = tclass.getSubClasses(OConstants.Closure.DIRECT_CLOSURE);
      directSubClasses.getValues().clear();
      directSubClasses.getValues().addAll(set2);
      Collections.sort(directSubClasses.getValues(), itemComparator);
      
      // all sub classes
      Set<OClass> set3 = tclass.getSubClasses(OConstants.Closure.TRANSITIVE_CLOSURE);
      allSubClasses.getValues().clear();
      allSubClasses.getValues().addAll(set3);
      Collections.sort(allSubClasses.getValues(), itemComparator);

      // equivalent classes
      Set<OClass> set4 = tclass.getEquivalentClasses();
      equivalentClasses.getValues().clear();
      equivalentClasses.getValues().addAll(set4);
      Collections.sort(equivalentClasses.getValues(), itemComparator);
      
      // properties with resource as domain
      propertyTypes.getValues().clear();
      Set<RDFProperty> dprops = tclass.getPropertiesWithResourceAsDomain();
      propertyTypes.getValues().addAll(dprops);
      Collections.sort(propertyTypes.getValues(), itemComparator);
      

      // annotation property values
      propertyValues.getValues().clear();
      Set<AnnotationProperty> props = tclass.getSetAnnotationProperties();
      if(props != null) {
        Iterator<AnnotationProperty> apIter = props.iterator();
        while(apIter.hasNext()) {
          AnnotationProperty ap = apIter.next();
          List<Literal> literals = tclass.getAnnotationPropertyValues(ap);
          for(int i = 0; i < literals.size(); i++) {
            PropertyValue pv = new PropertyValue(ap, literals.get(i));
            propertyValues.getValues().add(pv);
          }
        }
      }

      // instances
      Set<OInstance> set5 = ontology.getOInstances(tclass,
              OConstants.Closure.DIRECT_CLOSURE);
      instances.getValues().clear();
      if(set5 != null) {
        instances.getValues().addAll(set5);
        Collections.sort(instances.getValues(), itemComparator);
      }
    }
    else if(oResource instanceof OInstance) {
      OInstance oinstance = (OInstance)oResource;
      detailGroups = (new DetailsGroup[] {resourceInfo, directTypes, allTypes,
          sameAsInstances, propertyTypes, propertyValues});

      resourceInfo.getValues().clear();
      resourceInfo.getValues().add(oinstance);
      resourceInfo.getValues().add(new KeyValuePair(oinstance, "URI", oinstance.getOURI().toString(), false));
      resourceInfo.getValues().add(new KeyValuePair(oinstance, "TYPE", "Ontology Instance", false));
      
      // direct classes
      Set<OClass> set1 = oinstance.getOClasses(OConstants.Closure.DIRECT_CLOSURE);
      directTypes.getValues().clear();
      if(set1 != null) {
        for(OClass aClass : set1) {
          directTypes.getValues().addAll(Utils.getDetailsToAdd(aClass));
        }
      }

      // all classes
      Set<OClass> set2 = oinstance.getOClasses(OConstants.Closure.TRANSITIVE_CLOSURE);
      allTypes.getValues().clear();
      if(set2 != null) {
        for(OClass aClass : set2) {
          allTypes.getValues().addAll(Utils.getDetailsToAdd(aClass));
        }
      }

      Set<OInstance> set3 = oinstance.getSameInstance();
      sameAsInstances.getValues().clear();
      if(set3 != null) {
        sameAsInstances.getValues().addAll(set3);
        Collections.sort(sameAsInstances.getValues(), itemComparator);
      }

      propertyTypes.getValues().clear();
      Set<RDFProperty> dprops = oinstance.getPropertiesWithResourceAsDomain();
      propertyTypes.getValues().addAll(dprops);
      
      propertyValues.getValues().clear();
      Set<AnnotationProperty> apProps = oinstance.getSetAnnotationProperties();
      Set<DatatypeProperty> dtProps = oinstance.getSetDatatypeProperties();
      Set<ObjectProperty> obProps = oinstance.getSetObjectProperties();
      Set<RDFProperty> rdfProp = oinstance.getSetRDFProperties();

      for(AnnotationProperty ap : apProps) {
        List<Literal> literals = oinstance.getAnnotationPropertyValues(ap);
        for(int i = 0; i < literals.size(); i++) {
          PropertyValue pv = new PropertyValue(ap, literals.get(i));
          propertyValues.getValues().add(pv);
        }
      }

      for(DatatypeProperty dt : dtProps) {
        List<Literal> literals = oinstance.getDatatypePropertyValues(dt);
        for(int i = 0; i < literals.size(); i++) {
          PropertyValue pv = new PropertyValue(dt, literals.get(i));
          propertyValues.getValues().add(pv);
        }
      }

      for(ObjectProperty ob : obProps) {
        List<OInstance> oinstances = oinstance.getObjectPropertyValues(ob);
        for(int i = 0; i < oinstances.size(); i++) {
          PropertyValue pv = new PropertyValue(ob, oinstances.get(i));
          propertyValues.getValues().add(pv);
        }
      }

      for(RDFProperty rd : rdfProp) {
        List<OValue> oinstances = oinstance.getRDFPropertyOValues(rd);
        for(int i = 0; i < oinstances.size(); i++) {
          PropertyValue pv = new PropertyValue(rd, oinstances.get(i));
          propertyValues.getValues().add(pv);
        }
      }
    }
    fireTableDataChanged();
  }

  public OResource getItem() {
    return oResource;
  }

  public Ontology getOntology() {
    return ontology;
  }

  public void setOntology(Ontology ontology) {
    this.ontology = ontology;
  }

  protected DetailsGroup resourceInfo;
  protected DetailsGroup directSuperClasses;
  protected DetailsGroup allSuperClasses;
  protected DetailsGroup directSubClasses;
  protected DetailsGroup allSubClasses;
  protected DetailsGroup equivalentClasses;
  protected DetailsGroup sameAsInstances;
  protected DetailsGroup instances;
  protected DetailsGroup propertyTypes;
  protected DetailsGroup propertyValues;
  protected DetailsGroup directTypes;
  protected DetailsGroup allTypes;
  protected DetailsGroup detailGroups[];
  protected Ontology ontology;
  protected OResource oResource;
  protected boolean ontologyMode;
  public static final int COLUMN_COUNT = 4;
  public static final int EXPANDED_COLUMN = 0;
  public static final int LABEL_COLUMN = 1;
  public static final int VALUE_COLUMN = 2;
  public static final int DELETE_COLUMN = 3;
  protected OntologyItemComparator itemComparator;
}
