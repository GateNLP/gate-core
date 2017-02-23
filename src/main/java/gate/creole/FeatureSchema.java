/*
 *  FeatureSchema.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 27/Sept/2000
 *
 *  $Id: FeatureSchema.java 17604 2014-03-09 10:08:13Z markagreenwood $
 */

package gate.creole;

import java.io.Serializable;
import java.util.*;

/**
 * This class describes a schema for a feature. It is used as part of
 * {@link gate.creole.AnnotationSchema} class.
 */
public class FeatureSchema implements Serializable {

  private static final long serialVersionUID = 2705192644986091866L;

  /** The name of this feature. */
  String featureName = null;

  /** The class name of the feature value */
  Class<?> featureValueClass = null;

  /**
   * The value of the feature. This must be read only when "use" is
   * default or fixed.
   */
  String featureValue = null;

  /**
   * The use of that feature can be one of: prohibited | optional |
   * required | default | fixed : optional
   */
  String featureUse = null;

  /** The default or fixed value for that feature */

  /** Permisible value set, if appropriate. */
  Set<Object> featurePermissibleValuesSet = null;

  /**
   * Construction given a name of an feature and a feature value class
   * name
   */
  @SuppressWarnings("unchecked")
  public FeatureSchema(String aFeatureName, Class<?> aFeatureValueClass,
          String aFeatureValue, String aFeatureUse,
          Set<? extends Object> aFeaturePermissibleValuesSet) {

    featureName = aFeatureName;
    featureValueClass = aFeatureValueClass;
    featureValue = aFeatureValue;
    featureUse = aFeatureUse;
    featurePermissibleValuesSet = (Set<Object>)aFeaturePermissibleValuesSet;
  }

  /** Tests whether the values are an enumeration or not. */
  public boolean isEnumeration() {
    return featurePermissibleValuesSet != null;
  }// isEnumeration()

  /** Get the feature name */
  public String getFeatureName() {
    return featureName;
  }// getFeatureName()

  /**
   * @return the featureValueClass
   */
  public Class<?> getFeatureValueClass() {
    return featureValueClass;
  }

  /** Returns the permissible values as a Set */
  public Set<Object> getPermittedValues() {
    return featurePermissibleValuesSet;
  }// getPermissibleValues()

  /**
   * Adds all values from the given set as permissible values for the
   * given feature. No check is performed to see if the class name of
   * the feature value is the same as the the elements of the given set.
   * Returns true if the set has been assigned.
   */
  public boolean setPermissibleValues(Set<? extends Object> aPermisibleValuesSet) {
    featurePermissibleValuesSet.clear();
    return featurePermissibleValuesSet.addAll(aPermisibleValuesSet);
  }// setPermissibleValues()

  /**
   * Adds a value to the enumeration of permissible value for a feature
   * of this type. Returns false, i.e. fails, if the class name of the
   * feature value does not match the class name of the given object
   * 
   * @param obj the object representing a permissible value. If null
   *          then simply returns with false.
   */
  public boolean addPermissibleValue(Object obj) {
    if(obj == null) return false;
    if(!obj.getClass().getName().equals(featureValueClass.getName())) return false;
    if(featurePermissibleValuesSet == null)
      featurePermissibleValuesSet = new HashSet<Object>();
    return featurePermissibleValuesSet.add(obj);
  }// addPermissibleValue()

  /**
   * This method transforms a feature to its XSchema representation. It
   * is used in toXSchema().
   * 
   * @param aJava2XSchemaMap a Java map object that will be serialized
   *          in XSchema
   * @return a String containing the XSchema representation
   */
  public String toXSchema(Map<Class<?>,String> aJava2XSchemaMap) {

    StringBuffer schemaString = new StringBuffer();
    schemaString.append("   <attribute name=\"" + featureName + "\" ");
    schemaString.append("use=\"" + featureUse + "\"");

    // If there are no permissible values that means that the type must
    // be specified as an attribute for the attribute element
    if(!isEnumeration())
      schemaString.append(" type=\""
              + aJava2XSchemaMap.get(featureValueClass) + "\"/>\n");
    else {
      schemaString.append(">\n    <simpleType>\n");
      schemaString.append("     <restriction base=\""
              + aJava2XSchemaMap.get(featureValueClass) + "\">\n");
      Iterator<Object> featurePermissibleValuesSetIterator = featurePermissibleValuesSet
              .iterator();

      while(featurePermissibleValuesSetIterator.hasNext()) {
        String featurePermissibleValue = featurePermissibleValuesSetIterator
                .next().toString();
        schemaString.append("      <enumeration value=\""
                + featurePermissibleValue + "\"/>\n");
      }// end while

      schemaString.append("     </restriction>\n");
      schemaString.append("    </simpleType>\n");
      schemaString.append("   </attribute>\n");

    }// end if else

    return schemaString.toString();
  } // end toXSchema

  /**
   * This method returns the value of the feature. If featureUse is
   * something else than "default" or "fixed" it will return the empty
   * string "".
   */
  public String getFeatureValue() {
    if(isDefault() || isFixed())
      return featureValue;
    else return "";
  } // getFeatureValue

  /**
   * This method returns the value of the feature regardless of the
   * current value of featureUse.
   */
  public String getRawFeatureValue() {
    return featureValue;
  } // getRawFeatureValue

  /**
   * This method sets the value of the feature.
   * 
   * @param aFeatureValue a String representing the value of a feature.
   */
  public void setFeatureValue(String aFeatureValue) {
    featureValue = aFeatureValue;
  } // setFeatureValue

  /**
   * This method is used to check if the feature is required.
   * 
   * @return true if the feature is required. Otherwhise returns false
   */
  public boolean isRequired() {
    return "required".equals(featureUse);
  } // isRequired

  /**
   * This method is used to check if the feature is default. Default is
   * used if the feature was omitted.
   * 
   * @return true if the feature is default. Otherwhise returns false
   */
  public boolean isDefault() {
    return "default".equals(featureUse);
  } // isDefault

  /**
   * This method is used to check if the feature, is fixed.
   * 
   * @return true if the feature is fixed. Otherwhise returns false
   */
  public boolean isFixed() {
    return "fixed".equals(featureUse);
  } // isFixed

  /**
   * This method is used to check if the feature is optional.
   * 
   * @return true if the optional is fixed. Otherwhise returns false
   */
  public boolean isOptional() {
    return "optional".equals(featureUse);
  } // isOptional

  /**
   * This method is used to check if the feature is prohibited.
   * 
   * @return true if the prohibited is fixed. Otherwhise returns false
   */
  public boolean isProhibited() {
    return "prohibited".equals(featureUse);
  } // isProhibited

} // FeatureSchema
