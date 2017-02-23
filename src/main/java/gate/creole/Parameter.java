/*
 *  Parameter.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 15/Oct/2000
 *
 *  $Id: Parameter.java 20035 2017-02-01 03:01:32Z markagreenwood $
 */

package gate.creole;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.util.GateRuntimeException;
import gate.util.Strings;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;


/** Models a resource parameter.
  */
public class Parameter implements Serializable
{
  private static final long serialVersionUID = 6611664706992065985L;

  /**
   * Construct a new Parameter for a resource in a given plugin.
   * @param plugin the plugin that defines the resource 
   * this parameter belongs to. This will be used as context when deriving 
   * default values for the parameters of type URL oe ResourceReference.
   */  
  public Parameter(Plugin plugin) {
    this.plugin = plugin;
  }
  
  /** The type name of the parameter */
  String typeName;

  /** Set the type name for this parameter */
  public void setTypeName(String typeName) { this.typeName = typeName; }

  /** Get the type name for this parameter */
  public String getTypeName() { return typeName; }

  /** Is the parameter optional? */
  boolean optional = false;

  /** Set optionality of this parameter */
  public void setOptional(boolean optional) { this.optional = optional; }

  /** Is the parameter optional? */
  public boolean isOptional() { return optional; }

  /** The name of the item's class. If the parameter is a collection then
    * we need  to know the class of its items in order to create them the
    * way we want.
    */
  String itemClassName = null;

  /** A set of strings representing suffixes for URL params*/
  Set<String> suffixes = null;
  
  /**
   * Map giving concrete classes that should be used for a parameter
   * whose declared type is an interface.  This substitution allows a
   * resource to specify a parameter of type, e.g. java.util.List,
   * and give it a default value in creole.xml.  The runtime class of
   * the default value will be taken from this map.
   * 
   * Note that for this to work, it must be the case that for every
   * key <i>k</i> in this map,
   * 
   * <code>k.isAssignableFrom(substituteClasses.get(k))</code>
   */
   static Map<Class<?>, Class<?>> substituteClasses = 
    new HashMap<Class<?>, Class<?>>();
  
  static {
    substituteClasses.put(Collection.class, ArrayList.class);
    substituteClasses.put(List.class, ArrayList.class);
    substituteClasses.put(Set.class, HashSet.class);
    substituteClasses.put(SortedSet.class, TreeSet.class);
    substituteClasses.put(Queue.class, LinkedList.class);
  }

  /** Calculate and return the default value for this parameter */
  public Object calculateDefaultValue() throws ParameterException {
    // if there's no default string and this is a builtin type, return null
    if(
      defaultValueString == null && typeName != null &&
      typeName.startsWith("java.")
    )
      return null;

    return calculateValueFromString(defaultValueString);
  } // calculateDefaultValue()

  /** Calculate and return the value for this parameter starting from a String
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object calculateValueFromString(String stringValue)
  throws ParameterException {
    //if we have no string we can't construct a value
    Object value = null;

    // get the Class for the parameter via Class.forName or CREOLE register
    Class<?> paramClass = getParameterClass();
    if(substituteClasses.containsKey(paramClass)) {
      paramClass = substituteClasses.get(paramClass);
    }


    // Test if the paramClass is a collection and if it is, try to
    // construct the param as a collection of items specified in the
    // default string value.  If paramClass is an interface type we
    // look up its substitute concrete type in the map
    // collectionSubstituteClasses and create a value of that type.
    if (Collection.class.isAssignableFrom(paramClass) &&
            !paramClass.isInterface()){
      // Create an collection object belonging to paramClass
      Collection<?> colection = null;
      try{
        colection = paramClass.asSubclass(Collection.class).getConstructor(new Class<?>[]{}).
                                  newInstance(new Object[]{});
      } catch(Exception ex){
          throw new ParameterException("Could not construct an object of type "
            + typeName + " for param " + name +
            "\nProblem was: " + ex.toString());
      }// End try
      // If an itemClassName was specified then try to create objects belonging
      // to this class and add them to the collection. Otherwise add the
      // string tokens to the collection.
      if(itemClassName == null){
        // Read the tokens from the default value and try to create items
        // belonging to the itemClassName
        StringTokenizer strTokenizer = new StringTokenizer(
                                                      stringValue,";");
        while(strTokenizer.hasMoreTokens()){
          String itemStringValue = strTokenizer.nextToken();
          ((Collection<String>)colection).add(itemStringValue);
        }// End while
      }else{
        Class<?> itemClass = null;
        try{
          itemClass = Gate.getClassLoader().loadClass(itemClassName);
        }catch(ClassNotFoundException e){
          throw new ParameterException("Could not construct a class object for "
            + itemClassName + " for param "+ name +
            ", with type name="+ typeName);
        }// End try
        // Read the tokens from the default value and try to create items
        // belonging to the itemClassName
        StringTokenizer strTokenizer = new StringTokenizer(
                                                      stringValue,";");
        while(strTokenizer.hasMoreTokens()){
          // Read a string item and construct an object belonging to
          // itemClassName
          String itemStringValue = strTokenizer.nextToken();
          Object itemValue = null;
          try{
            itemValue = itemClass.getConstructor(new Class<?>[]{String.class}).
                                  newInstance(new Object[]{itemStringValue});
          }catch(Exception e){
            throw new ParameterException("Could not create an object of " +
            itemClassName + " for param name "+ name + ", with type name ="+
            typeName);
          }// End try
          // Add the item value object to the collection
          ((Collection<Object>)colection).add(itemValue);
        }// End while
      }// End if(itemClassName == null)
      return colection;
    }// End if (Collection.class.isAssignableFrom(paramClass))
    
    if(FeatureMap.class.isAssignableFrom(paramClass)) {
      // a null string value means a null FeatureMap
      if(stringValue == null) return null;
      FeatureMap fm = null;
      // if the type is an interface type (not a concrete implementation)
      // then just create a normal feature map using the factory
      if(paramClass.isInterface()) {
        fm = Factory.newFeatureMap();
      }
      else {
        try{
          fm = paramClass.asSubclass(FeatureMap.class).getConstructor(new Class<?>[]{}).
                                    newInstance(new Object[]{});
        } catch(InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
          
          throw new ParameterException("Could not construct an object of type "
              + typeName + " for param " + name +
              "\nProblem was: " + e.toString());
        }
      }
      
      // Read the tokens from the default value and try to create items
      // belonging to the itemClassName
      StringTokenizer strTokenizer = new StringTokenizer(
                                                    stringValue,";");
      while(strTokenizer.hasMoreTokens()) {
        String keyAndValue = strTokenizer.nextToken();
        int indexOfEquals = keyAndValue.indexOf('=');
        if(indexOfEquals == -1) {
          throw new ParameterException("Error parsing string \""
                  + stringValue + "\" for parameter " + name + " of type "
                  + typeName + ". Value string must be of the form "
                  + "name1=value1;name2=value2;...");
        }
        String featName = keyAndValue.substring(0, indexOfEquals);
        String featValue = keyAndValue.substring(indexOfEquals + 1);
        fm.put(featName, featValue);
      }
      
      return fm;
    }
    
    // Java 5.0 enum types
    if(paramClass.isEnum()) {
      if(stringValue == null) {
        value = null;
      }
      else {
        try {
          value = Enum.valueOf(paramClass.<Enum>asSubclass(Enum.class), stringValue);
        }
        catch(IllegalArgumentException e) {
          throw new ParameterException("Invalid enum constant name "
              + stringValue + " for type " + typeName);
        }
      }
    }
    else if (typeName.equals("gate.creole.ResourceReference")) {
      if (stringValue != null && !stringValue.equals("")) {
        try {
          value = new ResourceReference(plugin, stringValue);
        } catch(URISyntaxException e) {
          throw new ParameterException("Malformed ResourceReference parameter value: "+stringValue,e);
        }
      }
    }
    // java builtin types - for numeric types, we don't attempt to parse an
    // empty string value, but just leave value as null
    else if(typeName.startsWith("java.")) {
      if(typeName.equals("java.lang.Boolean"))
        value = Boolean.valueOf(stringValue);
      else if(typeName.equals("java.lang.Long")) {
        if(stringValue != null && !stringValue.equals("")) {
          value = Long.valueOf(stringValue);
        }
      }
      else if(typeName.equals("java.lang.Integer")) {
        if(stringValue != null && !stringValue.equals("")) {
          value = Integer.valueOf(stringValue);
        }
      }
      else if(typeName.equals("java.lang.String"))
        value = stringValue;
      else if(typeName.equals("java.lang.Double")) {
        if(stringValue != null && !stringValue.equals("")) {
          value = Double.valueOf(stringValue);
        }
      }
      else if(typeName.equals("java.lang.Float")) {
        if(stringValue != null && !stringValue.equals("")) {
          value = Float.valueOf(stringValue);
        }
      }
      else if(typeName.equals("java.net.URL")) {
        try{
          if(stringValue != null && !stringValue.equals("")) {
            value = new URL(plugin.getBaseURL(), stringValue);
          }
        }catch(MalformedURLException mue){
          //value = null;
          throw new ParameterException("Malformed URL parameter value: "+stringValue,mue);
        }
      }
      else{
        //try to construct a new value from the string using a constructor
        // e.g. for URLs
        try{
          if(!paramClass.isAssignableFrom(String.class)){
            value = paramClass.getConstructor(new Class<?>[]{String.class}).
                         newInstance(new Object[]{stringValue});
          }
        }catch(Exception e){
          throw new ParameterException("Unsupported parameter type " + typeName);
        }
      }
    } else {
      // non java types
      // null string value means null target value
      if(stringValue != null) {
        // otherwise, if it's a GATE resource type pick the first registered instance 
        if(resData == null)
          resData = Gate.getCreoleRegister().get(typeName);
        if(resData == null){
          //unknown type
          return null;
        }
  
        List<Resource> instantiations = resData.getInstantiations();
        if(! instantiations.isEmpty()) value = instantiations.get(0);
      }
    }

    return value;
  } // calculateValueFromString()


  /** The resource data that this parameter is part of. */
  protected ResourceData resData;

  /** Get the default value for this parameter. If the value is
    * currently null it will try and calculate a value.
    * @see #calculateDefaultValue()
    */
  public Object getDefaultValue() throws ParameterException {
    return calculateDefaultValue();
  } // getDefaultValue

  /** Default value string (unprocessed, from the metadata)
    * for the parameter
    */
  String defaultValueString;

  /** Set the default value string (from the metadata)
    * for the parameter
    */
  public void setDefaultValueString(String defaultValueString) {
    this.defaultValueString = defaultValueString;
  } // setDefaultValueString

  /** Get the default value string (unprocessed, from the metadata)
    * for the parameter
    */
  public String getDefaultValueString() { return defaultValueString; }

  /** Comment for the parameter */
  String comment;

  /** Set the comment for this parameter */
  public void setComment(String comment) { this.comment = comment; }

  /** Get the comment for this parameter */
  public String getComment() { return comment; }

  /** helpURL for the parameter */
  String helpURL;
  
  /** Set the helpURL for this parameter */
  public void setHelpURL(String helpURL) { this.helpURL = helpURL; }
  
  /** Get the helpURL for this parameter */
  public String getHelpURL() { return helpURL; }
  
  /** Name for the parameter */
  String name;

  /** Set the name for this parameter */
  public void setName(String name) { this.name = name; }

  /** Get the name for this parameter */
  public String getName() { return name; }

  /** Get the suffixes atached with this param. If it's null then there are
   *  no suffices attached with it
   */
  public Set<String> getSuffixes(){ return suffixes;}

  /** Is this a run-time parameter? */
  boolean runtime = false;

  /**
   * The URL to the creole.xml file that defines the resource this parameter 
   * belongs to. It is used for deriving default values for parameters of type
   * {@link URL}.
   */
  //protected URL baseURL;
  
  protected Plugin plugin;
  
  /** Set runtime status of this parameter */
  public void setRuntime(boolean runtime) { this.runtime = runtime; }

  /** Is the parameter runtime? */
  public boolean isRuntime() { return runtime; }

  /** The Class for the parameter type */
  protected Class<?> paramClass;

  /** Find the class for this parameter type. */
  protected Class<?> getParameterClass() throws ParameterException
  {
    // get java builtin classes via class; else look in the register
    try {
      ResourceData resData = Gate.getCreoleRegister().get(typeName);
      if(resData == null){
        paramClass = Gate.getClassLoader().loadClass(typeName);
      }else{
        paramClass = resData.getResourceClass();
      }

//      if(typeName.startsWith("java."))
//          paramClass = Class.forName(typeName);
//      else {
//        ResourceData resData =
//          (ResourceData) Gate.getCreoleRegister().get(typeName);
//        if(resData == null)
//          throw new ParameterException(
//            "No resource data for " + typeName + " in Parameter/getParamClz"
//          );
//        paramClass = resData.getResourceClass();
//      }
    } catch(ClassNotFoundException e) {
      throw new ParameterException(
        "Couldn't find class " + typeName + ": " + Strings.getNl() + e
      );
    }

    if(paramClass == null)
      throw new ParameterException("Couldn't find class " + typeName);

    return paramClass;
  } // getParameterClass

  /** String representation */
  @Override
  public String toString() {
    try{
      return "Parameter: name="+ name+ "; valueString=" + typeName +
             "; optional=" + optional +
             "; defaultValueString=" + defaultValueString +
             "; defaultValue=" + getDefaultValue() + "; comment=" +
             comment + "; helpURL=" +
             helpURL + "; runtime=" + runtime +
             "; itemClassName=" + itemClassName +
             "; suffixes=" + suffixes;
    }catch(ParameterException pe){
      throw new GateRuntimeException(pe.toString());
    }
  }

  /**
   * If this parameter is a List type this will return the type of the items
   * in the list. If the type is <tt>null</tt> String will be assumed.
   */
  public String getItemClassName() {
    return itemClassName;
  } // toString()
} // class Parameter
