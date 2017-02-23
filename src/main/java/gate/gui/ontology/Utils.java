/*
 *  Utils.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: Utils.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.AllValuesFromRestriction;
import gate.creole.ontology.AnnotationProperty;
import gate.creole.ontology.CardinalityRestriction;
import gate.creole.ontology.DatatypeProperty;
import gate.creole.ontology.HasValueRestriction;
import gate.creole.ontology.InvalidURIException;
import gate.creole.ontology.Literal;
import gate.creole.ontology.MaxCardinalityRestriction;
import gate.creole.ontology.MinCardinalityRestriction;
import gate.creole.ontology.OClass;
import gate.creole.ontology.OInstance;
import gate.creole.ontology.OResource;
import gate.creole.ontology.OURI;
import gate.creole.ontology.Ontology;
import gate.creole.ontology.RDFProperty;
import gate.creole.ontology.Restriction;
import gate.creole.ontology.SomeValuesFromRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class provides various static utility methods which are used by
 * the ontology editor.
 * 
 * @author niraj
 * 
 */
public class Utils {

  /**
   * Checks whether the provided name space is valid name space. In this
   * version, the namespace must match the following java regular
   * expression. <BR>
   * 
   * "[a-zA-Z]+(:)(/)+[a-zA-Z0-9\\-]+((\\.)[a-zA-Z0-9\\-]+)+((/)[a-zA-Z0-9\\.\\-_]+)*(#|/)"
   */
  public static boolean isValidNameSpace(String s) {
    String s1 = new String(
            "[a-zA-Z]+(:)(/)+[a-zA-Z0-9\\-]+((\\.)[a-zA-Z0-9\\-]+)+((/)[a-zA-Z0-9\\.\\-_]+)*(#|/|/#)");
    Pattern pattern = Pattern.compile(s1);
    return pattern.matcher(s).matches();
  }

  /**
   * Checks whether the provided resource name is a valid resource name
   * In this version, the resource name must match the following java
   * regular expression <BR>. "[a-zA-Z0-9_-]+"
   */
  public static boolean isValidOntologyResourceName(String s) {
    String s1 = new String("[a-zA-Z0-9_-]+");
    Pattern pattern = Pattern.compile(s1);
    return pattern.matcher(s).matches();
  }

  /**
   * This method returns the details to be added.
   */
  @SuppressWarnings("deprecation")
  public static List<Object> getDetailsToAdd(Object object) {
    List<Object> toAdd = new ArrayList<Object>();
    toAdd.add(object);
    if(object instanceof Restriction) {
      Restriction res = (Restriction)object;
      toAdd.add(new KeyValuePair(res, "RESTRICTION TYPE",
              gate.creole.ontology.Utils.getRestrictionName(res), false));
      toAdd.add(new KeyValuePair(res.getOnPropertyValue(), "ON PROPERTY", res
              .getOnPropertyValue().getName(), false));
      String valueString = null;
      String datatypeString = null;

      if(res instanceof CardinalityRestriction) {
        valueString = ((CardinalityRestriction)res).getValue();
        datatypeString = ((CardinalityRestriction)res).getDataType()
                .getXmlSchemaURIString();
        toAdd.add(new KeyValuePair(res, "DATATYPE", datatypeString, false));
        toAdd.add(new KeyValuePair(res, "VALUE", valueString, true));
      }
      else if(res instanceof MinCardinalityRestriction) {
        valueString = ((MinCardinalityRestriction)res).getValue();
        datatypeString = ((MinCardinalityRestriction)res).getDataType()
                .getXmlSchemaURIString();
        toAdd.add(new KeyValuePair(res, "DATATYPE", datatypeString, false));
        toAdd.add(new KeyValuePair(res, "VALUE", valueString, true));
      }
      else if(res instanceof MaxCardinalityRestriction) {
        valueString = ((MaxCardinalityRestriction)res).getValue();
        datatypeString = ((MaxCardinalityRestriction)res).getDataType()
                .getXmlSchemaURIString();
        toAdd.add(new KeyValuePair(res, "DATATYPE", datatypeString, false));
        toAdd.add(new KeyValuePair(res, "VALUE", valueString, false));
      }
      else if(res instanceof HasValueRestriction) {
        Object value = ((HasValueRestriction)res).getHasValue(); 
        if(value instanceof Literal) {
          valueString = ((Literal)value).getValue();
          datatypeString = ((DatatypeProperty)((HasValueRestriction)res)
              .getOnPropertyValue()).getDataType().getXmlSchemaURIString();
          toAdd.add(new KeyValuePair(res, "DATATYPE", datatypeString, false));
          toAdd.add(new KeyValuePair(res, "VALUE", valueString, true));
        } else {
          valueString = ((OResource)value).getONodeID().toString();
          toAdd.add(new KeyValuePair((OResource)value, "VALUE", valueString, false));
        }
      }
      else if(res instanceof AllValuesFromRestriction) {
        valueString = ((AllValuesFromRestriction)res).getHasValue().getONodeID()
                .toString();
        toAdd.add(new KeyValuePair(((AllValuesFromRestriction)res)
                .getHasValue(), "VALUE", valueString, false));
      }
      else if(res instanceof SomeValuesFromRestriction) {
        valueString = ((SomeValuesFromRestriction)res).getHasValue().getONodeID()
                .toString();
        toAdd.add(new KeyValuePair(((SomeValuesFromRestriction)res)
                .getHasValue(), "VALUE", valueString, false));
      }
    }
    else if(object instanceof RDFProperty) {
      RDFProperty prop = (RDFProperty)object;
      if(prop instanceof DatatypeProperty) {
        toAdd.add(new KeyValuePair(prop, "DATATYPE", ((DatatypeProperty)prop)
                .getDataType().getXmlSchemaURIString(), false));
      }
      else if(!(prop instanceof AnnotationProperty)) {
        Set<OResource> set = prop.getRange();
        if(set == null || set.isEmpty()) {
          toAdd.add(new KeyValuePair(prop, "RANGE", "[ALL CLASSES]", false));
        }
        else {
          String key = "RANGE";
          for(OResource res : set) {
            toAdd.add(new KeyValuePair(res, key, res.getName(), false));
            key = "";
          }
        }
      }
    }
    return toAdd;
  }
  
  /** 
   * Replacement for the deprecated method from gate.creole.ontology.Ontology.
   * 
   * @param o Ontology to use
   * @param uri URI as a string of the resource to find
   * @return the resource as an OResource object
   */
  static OResource getOResourceFromMap(Ontology o, String uri) {
    try {
      OURI ouri = o.createOURI(uri);

      OClass c = o.getOClass(ouri);
      if (c != null) {
        return c;
      }
      OInstance i = o.getOInstance(ouri);
      if (i != null) {
        return i;
      }
      RDFProperty r = o.getProperty(ouri);
      if (r != null) {
        return r;
      }
    } catch (InvalidURIException iue) {
      // do nothing, we will just return null
    }
    return null;
  }
  
  
}
