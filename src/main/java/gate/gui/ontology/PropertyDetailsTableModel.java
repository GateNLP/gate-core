/*
 *  PropertyDetailsTableModel.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: PropertyDetailsTableModel.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.*;

import java.util.*;

import javax.swing.table.AbstractTableModel;

/**
 * A DataModel that is created when a node is selected in the ontology
 * property tree. It contains information such as direct/all sub/super
 * properties, equivalent properties, domain/range of each property and
 * property values and so on. The information from this model is then
 * shown in the right hand side panel of the ontology editor.
 * 
 * @author niraj
 * 
 */
@SuppressWarnings("serial")
public class PropertyDetailsTableModel extends AbstractTableModel {
  public PropertyDetailsTableModel() {
    resourceInfo = new DetailsGroup("Resource Information", true, null);
    directSuperProps = new DetailsGroup("Direct Super Properties", true, null);
    allSuperProps = new DetailsGroup("All Super Properties", true, null);
    directSubProps = new DetailsGroup("Direct Sub Properties", true, null);
    allSubProps = new DetailsGroup("All Sub Properties", true, null);
    equivalentProps = new DetailsGroup("Equivalent Properties", true, null);
    domain = new DetailsGroup("Domain", true, null);
    range = new DetailsGroup("Range", true, null);
    propertyTypes = new DetailsGroup("Properties", true, null);
    propertyValues = new DetailsGroup("PropertyValues", true, null);
    detailGroups = new DetailsGroup[0];
    itemComparator = new OntologyItemComparator();
  }

  @Override
  public int getColumnCount() {
    return COLUMN_COUNT;
  }

  @Override
  public int getRowCount() {
    int i = detailGroups.length;
    for(int j = 0; j < detailGroups.length; j++)
      if(detailGroups[j].isExpanded()) i += detailGroups[j].getSize();
    return i;
  }

  @Override
  public String getColumnName(int i) {
    switch(i) {
      case 0: // '\0'
        return "";
      case 1: // '\001'
        return "";
      case 2:
        return "";
      case 3:
        return "";
    }
    return "";
  }

  @Override
  public Class<?> getColumnClass(int i) {
    switch(i) {
      case 0:
        return Boolean.class;
      case 1:
        return Object.class;
      case 2:
        return Object.class;
      case 3:
        return Object.class;
    }
    return Object.class;
  }

  @Override
  public boolean isCellEditable(int i, int j) {
    return false;
  }

  @Override
  public void setValueAt(Object obj, int i, int j) {
    Object obj1 = getItemForRow(i);
    if(j == 0 && (obj1 instanceof DetailsGroup)) {
      DetailsGroup detailsgroup = (DetailsGroup)obj1;
      detailsgroup.setExpanded(((Boolean)obj).booleanValue());
    }
    fireTableDataChanged();
  }

  protected Object getItemForRow(int i) {
    int j = 0;
    for(int k = 0; j <= i; k++) {
      if(j == i) return detailGroups[k];
      int l = 1 + (detailGroups[k].isExpanded() ? detailGroups[k].getSize() : 0);
      if(j + l > i) return detailGroups[k].getValueAt(i - j - 1);
      j += l;
    }
    return null;
  }

  @Override
  public Object getValueAt(int i, int j) {
    Object obj = getItemForRow(i);
    switch(j) {
      case 0:
        return (obj instanceof DetailsGroup) ? new Boolean(((DetailsGroup)obj)
                .isExpanded()) : null;
      case 1:
        return obj;
      case 2:
        return obj;
      case 3:
        return obj;
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public void setItem(Object obj) {
    detailGroups = (new DetailsGroup[] {resourceInfo, directSuperProps, allSuperProps,
        directSubProps, allSubProps, equivalentProps, domain, range,
        propertyTypes, propertyValues});
    
    RDFProperty property = (RDFProperty)obj;
    resourceInfo.getValues().clear();
    directSuperProps.getValues().clear();
    allSuperProps.getValues().clear();
    directSubProps.getValues().clear();
    allSubProps.getValues().clear();
    equivalentProps.getValues().clear();
    domain.getValues().clear();
    range.getValues().clear();
    propertyTypes.getValues().clear();
    propertyValues.getValues().clear();
    
    resourceInfo.getValues().clear();
    resourceInfo.getValues().add(property);
    resourceInfo.getValues().add(new KeyValuePair(property, "URI", property.getONodeID().toString(), false));
    
    Set<RDFProperty> dprops = property.getPropertiesWithResourceAsDomain();
    propertyTypes.getValues().addAll(dprops);
    Collections.sort(propertyTypes.getValues(), itemComparator);
    
//    for(RDFProperty prop : dprops) {
//      propertyTypes.getValues().addAll(Utils.getDetailsToAdd(prop));
//    }
    
    Set<AnnotationProperty> props = property.getSetAnnotationProperties();
    if(props != null) {
      Iterator<AnnotationProperty> apIter = props.iterator();
      while(apIter.hasNext()) {
        AnnotationProperty ap = apIter.next();
        List<Literal> literals = property.getAnnotationPropertyValues(ap);
        for(int i = 0; i < literals.size(); i++) {
          PropertyValue pv = new PropertyValue(ap, literals.get(i));
          propertyValues.getValues().add(pv);
        }
      }
    }

    if(property instanceof AnnotationProperty) {
      resourceInfo.getValues().add(new KeyValuePair(property, "TYPE", "Annotation Property", false));
    } else if(property instanceof DatatypeProperty) {
      resourceInfo.getValues().add(new KeyValuePair(property, "TYPE", "Datatype Property", false));
    } else if(property instanceof SymmetricProperty) {
      resourceInfo.getValues().add(new KeyValuePair(property, "TYPE", "Symmetric Property", false));
    } else if(property instanceof TransitiveProperty) {
      resourceInfo.getValues().add(new KeyValuePair(property, "TYPE", "Transitive Property", false));      
    } else {
      resourceInfo.getValues().add(new KeyValuePair(property, "TYPE", "Object Property", false));      
    }
    
    if(property instanceof AnnotationProperty) {
      fireTableDataChanged();
      return;
    }
    // else provide further details

    Set<RDFProperty> set = property
            .getSuperProperties(OConstants.Closure.DIRECT_CLOSURE);
    if(set != null) {
      directSuperProps.getValues().addAll(set);
      Collections.sort(directSuperProps.getValues(), itemComparator);
    }
    
    set = property.getSuperProperties(OConstants.Closure.TRANSITIVE_CLOSURE);
    if(set != null) {
      allSuperProps.getValues().addAll(set);
      Collections.sort(allSuperProps.getValues(), itemComparator);
    }
    set = property.getSubProperties(OConstants.Closure.DIRECT_CLOSURE);
    if(set != null) {
      directSubProps.getValues().addAll(set);
      Collections.sort(directSubProps.getValues(), itemComparator);
    }
    set = property.getSubProperties(OConstants.Closure.TRANSITIVE_CLOSURE);
    if(set != null) {
      allSubProps.getValues().addAll(set);
      Collections.sort(allSubProps.getValues(), itemComparator);
    }

    set = property.getEquivalentPropertyAs();
    if(set != null) {
      equivalentProps.getValues().addAll(set);
      Collections.sort(equivalentProps.getValues(), itemComparator);
    }

    @SuppressWarnings("deprecation")
    Set<OResource> set1 = property.getDomain();
    if(set1 != null) {
      domain.getValues().addAll(set1);
      Collections.sort(domain.getValues(), itemComparator);
      
//      Iterator iterator = set1.iterator();
//      while(iterator.hasNext()) {
//        OResource resource = (OResource)iterator.next();
//        domain.getValues().addAll(Utils.getDetailsToAdd(resource));
//      }
    }

    // TODO: this used getXmlSchemaURI originally -- test if this breaks
    // anything!
    if(property instanceof DatatypeProperty) {
      range.getValues().add(new KeyValuePair(property, "DATATYPE", 
          ((DatatypeProperty)property).getDataType().getXmlSchemaURIString(), false));
      fireTableDataChanged();
      return;
    }

    @SuppressWarnings("deprecation")
    Set<OResource> set2 = property.getRange();
    if(set2 != null) {
      range.getValues().addAll(set2);
      Collections.sort(range.getValues(), itemComparator);
      
//      Iterator iterator = set2.iterator();
//      while(iterator.hasNext()) {
//        OResource resource = (OResource)iterator.next();
//        range.getValues().addAll(Utils.getDetailsToAdd(resource));
//      }
    }

    fireTableDataChanged();
  }

  protected DetailsGroup resourceInfo;
  
  protected DetailsGroup directSuperProps;

  protected DetailsGroup allSuperProps;

  protected DetailsGroup directSubProps;

  protected DetailsGroup equivalentProps;

  protected DetailsGroup allSubProps;

  protected DetailsGroup domain;

  protected DetailsGroup range;

  protected DetailsGroup propertyTypes;

  protected DetailsGroup propertyValues;

  protected DetailsGroup detailGroups[];

  protected OntologyItemComparator itemComparator;

  public static final int COLUMN_COUNT = 4;

  public static final int EXPANDED_COLUMN = 0;

  public static final int LABEL_COLUMN = 1;
  
  public static final int VALUE_COLUMN = 2;
  
  public static final int DELETE_COLUMN = 3;
}
