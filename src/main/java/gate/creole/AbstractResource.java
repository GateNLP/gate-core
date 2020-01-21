/*
 *  AbstractResource.java
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
 *  $Id: AbstractResource.java 19662 2016-10-10 08:03:37Z markagreenwood $
 */

package gate.creole;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.util.AbstractFeatureBearer;
import gate.util.Err;
import gate.util.GateException;
import gate.util.Strings;
import gate.util.Tools;


/** A convenience implementation of Resource with some default code.
  */
abstract public class AbstractResource
extends AbstractFeatureBearer implements Resource
{
  static final long serialVersionUID = -9196293927841163321L;

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    return this;
  } // init()

    /** Sets the name of this resource*/
  @Override
  public void setName(String name){
    this.name = name;
  }

  /** Returns the name of this resource*/
  @Override
  public String getName(){
    return name;
  }

  protected String name;
  /**
   * releases the memory allocated to this resource
   */
  @Override
  public void cleanup(){
  }

  //Parameters utility methods
  /**
   * Gets the value of a parameter for a resource.
   * @param resource the resource from which the parameter value will be
   * obtained
   * @param paramaterName the name of the parameter
   * @return the current value of the parameter
   */
  public static Object getParameterValue(Resource resource,
                                         String paramaterName)
                throws ResourceInstantiationException{
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInf = null;
    try {
      resBeanInf = getBeanInfo(resource.getClass());
    } catch(Exception e) {
      throw new ResourceInstantiationException(
        "Couldn't get bean info for resource " + resource.getClass().getName()
        + Strings.getNl() + "Introspector exception was: " + e
      );
    }
    PropertyDescriptor[] properties = resBeanInf.getPropertyDescriptors();

    //find the property we're interested on
    if(properties == null){
      throw new ResourceInstantiationException(
        "Couldn't get properties info for resource " +
        resource.getClass().getName());
    }
    boolean done = false;
    int i = 0;
    Object value = null;
    while(!done && i < properties.length){
      PropertyDescriptor prop = properties[i];
      if(prop.getName().equals(paramaterName)){
        Method getMethod = prop.getReadMethod();
        if(getMethod == null){
          throw new ResourceInstantiationException(
            "Couldn't get read accessor method for parameter " + paramaterName +
            " in " + resource.getClass().getName());
        }
        // call the get method with the parameter value
        Object[] args = new Object[0];
        try {
          value = getMethod.invoke(resource, args);
        } catch(Exception e) {
          throw new ResourceInstantiationException(
            "couldn't invoke get method: " + e
          );
        }
        done = true;
      }//if(prop.getName().equals(paramaterName))
      i++;
    }//while(!done && i < properties.length)
    if(done) return value;
    else throw new ResourceInstantiationException(
            "Couldn't find parameter named " + paramaterName +
            " in " + resource.getClass().getName());
  }

  /**
   * Sets the value for a specified parameter for a resource.
   *
   * @param resource the resource for which the parameter value will be set
   * @param paramaterName the name for the parameter
   * @param parameterValue the value the parameter will receive
   */
  public static void setParameterValue(Resource resource, BeanInfo resBeanInf,
                                       String paramaterName,
                                       Object parameterValue)
              throws ResourceInstantiationException{
    PropertyDescriptor[] properties = resBeanInf.getPropertyDescriptors();
    //find the property we're interested on
    if(properties == null){
      throw new ResourceInstantiationException(
        "Couldn't get properties info for resource " +
        resource.getClass().getName());
    }
    boolean done = false;
    int i = 0;
    while(!done && i < properties.length){
      PropertyDescriptor prop = properties[i];
      if(prop.getName().equals(paramaterName)){
        Method setMethod = prop.getWriteMethod();
        if(setMethod == null){
          throw new ResourceInstantiationException(
            "Couldn't get write accessor method for parameter " +
            paramaterName + " in " + resource.getClass().getName());
        }

        // convert the parameter to the right type eg String -> URL
        if(parameterValue != null){
          Class<?> propertyType = prop.getPropertyType();
          Class<?> typeToCreate = propertyType;
          if(Parameter.substituteClasses.containsKey(propertyType)) {
            typeToCreate = Parameter.substituteClasses.get(propertyType);
          }
          Class<?> paramType = parameterValue.getClass();
          if(!propertyType.isAssignableFrom(paramType)) {
            try {
              Constructor<?> mostSpecificConstructor =
                Tools.getMostSpecificConstructor(typeToCreate, paramType);
              parameterValue = mostSpecificConstructor
                 .newInstance( new Object[]{parameterValue} );
            } catch(Exception e) {
              //this didn't work; if the parameter value is String
              //try to use the Parameter implementation for finding the
              //value
              if(String.class.isAssignableFrom(paramType)){
                ResourceData rData = Gate.getCreoleRegister().
                  get(resource.getClass().getName());
                ParameterList pList = rData.getParameterList();
                Parameter param = null;
                Iterator<List<Parameter>> disjIter = pList.getInitimeParameters().iterator();
                while(param == null && disjIter.hasNext()){
                  Iterator<Parameter> paramIter = disjIter.next().iterator();
                  while(param == null && paramIter.hasNext()){
                    Parameter aParam = paramIter.next();
                    if(aParam.getName().equals(paramaterName)) param = aParam;
                  }
                }
                disjIter = pList.getRuntimeParameters().iterator();
                while(param == null && disjIter.hasNext()){
                  Iterator<Parameter> paramIter = disjIter.next().iterator();
                  while(param == null && paramIter.hasNext()){
                    Parameter aParam = paramIter.next();
                    if(aParam.getName().equals(paramaterName)) param = aParam;
                  }
                }
                if(param != null){
                  try{
                    parameterValue = param.calculateValueFromString(
                            (String)parameterValue);
                  }catch(ParameterException pe){
                    throw new ResourceInstantiationException(pe);
                  }
                }else{
                  // if this happens it means that the class has the specified parameter
                  // name does indeed correspond to a getter/setter pair on the class
                  // but there is no associated CREOLE metadata for that parameter name
                  throw new ResourceInstantiationException("Property " + paramaterName +
                      " of resource class " + resource.getClass().getName() +
                      " is not declared as a CREOLE parameter.  Automatic" +
                      " conversion of string values to other types is only" +
                      " supported for declared parameters."); 
                }
              }else{
                throw new ResourceInstantiationException(
                  "Error converting " + parameterValue.getClass() +
                  " to " + propertyType + ": " + e.toString()
                );
              }
            }
          }
        }//if(parameterValue != null)

        // call the set method with the parameter value
        Object[] args = new Object[1];
        args[0] = parameterValue;
        try {
          setMethod.invoke(resource, args);
        } catch(Exception e) {
          e.printStackTrace(Err.getPrintWriter());
          throw new ResourceInstantiationException(
            "couldn't invoke set method for " + paramaterName +
            " on " + resource.getClass().getName() + ": " + e);
        }
        done = true;
      }//if(prop.getName().equals(paramaterName))
      i++;
    }//while(!done && i < properties.length)
    if(!done) throw new ResourceInstantiationException(
                          "Couldn't find parameter named " + paramaterName +
                          " in " + resource.getClass().getName());
  }//public void setParameterValue(String paramaterName, Object parameterValue)


  /**
   * Sets the values for more parameters for a resource in one step.
   *
   * @param parameters a feature map that has parameter names as keys and
   * parameter values as values.
   */
  public static void setParameterValues(Resource resource,
                                        FeatureMap parameters)
              throws ResourceInstantiationException{
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInf = null;
    try {
      resBeanInf = getBeanInfo(resource.getClass());
    } catch(Exception e) {
      throw new ResourceInstantiationException(
        "Couldn't get bean info for resource " + resource.getClass().getName()
        + Strings.getNl() + "Introspector exception was: " + e
      );
    }

    Iterator<Object> parnameIter = parameters.keySet().iterator();
    while(parnameIter.hasNext()){
      String parName = (String)parnameIter.next();
      setParameterValue(resource, resBeanInf, parName, parameters.get(parName));
    }
  }


  /**
   * Adds listeners to a resource.
   * @param listeners The listeners to be registered with the resource. A
   * {@link java.util.Map} that maps from fully qualified class name (as a
   * string) to listener (of the type declared by the key).
   * @param resource the resource that listeners will be registered to.
   */
  public static void setResourceListeners(Resource resource, Map<String, ? extends Object> listeners)
  throws
    IntrospectionException, InvocationTargetException,
    IllegalAccessException, GateException
  {
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInfo = getBeanInfo(resource.getClass());

    // get all the events the bean can fire
    EventSetDescriptor[] events = resBeanInfo.getEventSetDescriptors();

    // add the listeners
    if (events != null) {
      EventSetDescriptor event;
      for(int i = 0; i < events.length; i++) {
        event = events[i];

        // did we get such a listener?
        Object listener =
          listeners.get(event.getListenerType().getName());
        if(listener != null) {
          Method addListener = event.getAddListenerMethod();

          // call the set method with the parameter value
          Object[] args = new Object[1];
          args[0] = listener;
          addListener.invoke(resource, args);
        }
      } // for each event
    }   // if events != null
  } // setResourceListeners()

  /**
   * Removes listeners from a resource.
   * @param listeners The listeners to be removed from the resource. A
   * {@link java.util.Map} that maps from fully qualified class name
   * (as a string) to listener (of the type declared by the key).
   * @param resource the resource that listeners will be removed from.
   */
  public static void removeResourceListeners(Resource resource, Map<String, ? extends Object> listeners)
                     throws IntrospectionException, InvocationTargetException,
                            IllegalAccessException, GateException{

    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInfo = getBeanInfo(resource.getClass());

    // get all the events the bean can fire
    EventSetDescriptor[] events = resBeanInfo.getEventSetDescriptors();

    //remove the listeners
    if(events != null) {
      EventSetDescriptor event;
      for(int i = 0; i < events.length; i++) {
        event = events[i];

        // did we get such a listener?
        Object listener =
          listeners.get(event.getListenerType().getName());
        if(listener != null) {
          Method removeListener = event.getRemoveListenerMethod();

          // call the set method with the parameter value
          Object[] args = new Object[1];
          args[0] = listener;
          removeListener.invoke(resource, args);
        }
      } // for each event
    }   // if events != null
  } // removeResourceListeners()

  /**
   * Checks whether the provided {@link Resource} has values for all the
   * required parameters from the provided list of parameters.
   *
   * @param resource the resource being checked
   * @param parameters is a {@link List} of {@link List} of {@link Parameter}
   * representing a list of parameter disjunctions (e.g. the one returned by
   * {@link ParameterList#getRuntimeParameters()}).
   * @return <tt>true</tt> if all the required parameters have non null values,
   * <tt>false</tt> otherwise.
   * @throws ResourceInstantiationException if problems occur while
   * inspecting the parameters for the resource. These will normally be
   * introspection problems and are usually caused by the lack of a parameter
   * or of the read accessor for a parameter.
   */
  public static boolean checkParameterValues(Resource resource,
                                             List<List<Parameter>> parameters)
                throws ResourceInstantiationException{
    Iterator<List<Parameter>> disIter = parameters.iterator();
    while(disIter.hasNext()){
      List<Parameter> disjunction = disIter.next();
      boolean required = !disjunction.get(0).isOptional();
      if(required){
        //at least one parameter in the disjunction must have a value
        boolean valueSet = false;
        Iterator<Parameter> parIter = disjunction.iterator();
        while(!valueSet && parIter.hasNext()){
          Parameter par = parIter.next();
          valueSet = (resource.getParameterValue(par.getName()) != null);
        }
        if(!valueSet) return false;
      }
    }
    return true;
  }



  /**
   * Gets the value of a parameter of this resource.
   * @param paramaterName the name of the parameter
   * @return the current value of the parameter
   */
  @Override
  public Object getParameterValue(String paramaterName)
                throws ResourceInstantiationException{
    return getParameterValue(this, paramaterName);
  }

  /**
   * Sets the value for a specified parameter for this resource.
   *
   * @param paramaterName the name for the parameter
   * @param parameterValue the value the parameter will receive
   */
  @Override
  public void setParameterValue(String paramaterName, Object parameterValue)
              throws ResourceInstantiationException{
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInf = null;
    try {
      resBeanInf = getBeanInfo(this.getClass());
    } catch(Exception e) {
      throw new ResourceInstantiationException(
        "Couldn't get bean info for resource " + this.getClass().getName()
        + Strings.getNl() + "Introspector exception was: " + e
      );
    }
    setParameterValue(this, resBeanInf, paramaterName, parameterValue);
  }

  /**
   * Sets the values for more parameters for this resource in one step.
   *
   * @param parameters a feature map that has paramete names as keys and
   * parameter values as values.
   */
  @Override
  public void setParameterValues(FeatureMap parameters)
              throws ResourceInstantiationException{
    setParameterValues(this, parameters);
  }

  /**
   * Get the current values of the given parameters from the given
   * resource.
   *
   * @param res the resource
   * @param params a list of parameter disjunctions such as would
   *         be returned by {@link ParameterList#getInitimeParameters()}
   *         or similar.
   */
  public static FeatureMap getParameterValues(Resource res,
          List<List<Parameter>> params) throws ResourceInstantiationException {
    FeatureMap fm = Factory.newFeatureMap();
    for(List<Parameter> parDisjunction : params) {
      for(Parameter p : parDisjunction) {
        fm.put(p.getName(), res.getParameterValue(p.getName()));
      }
    }
    return fm;
  }

  /**
   * Get the current values for all of a specified resource's
   * registered init-time parameters.
   */
  public static FeatureMap getInitParameterValues(Resource res)
              throws ResourceInstantiationException {
    ResourceData rData = Gate.getCreoleRegister().get(
            res.getClass().getName());
    if(rData == null)
      throw new ResourceInstantiationException(
              "Could not find CREOLE data for " + res.getClass().getName());

    ParameterList params = rData.getParameterList();

    return getParameterValues(res, params.getInitimeParameters());
  }

  /**
   * Get the current values for all this resource's registered
   * init-time parameters.
   */
  public FeatureMap getInitParameterValues() throws ResourceInstantiationException {
    return getInitParameterValues(this);
  }

  private static Map<Class<? extends Resource>, BeanInfo> beanInfoCache = new HashMap<Class<? extends Resource>,BeanInfo>();
  
  public static BeanInfo getBeanInfo(Class<? extends Resource> c) throws IntrospectionException {
    
    BeanInfo r = beanInfoCache.get(c);
    if(r == null) {
      r = Introspector.getBeanInfo(c, Object.class);
      beanInfoCache.put(c, r);
    }
    return r;
  }
  
  public static boolean forgetBeanInfo(Class<? extends Resource> c) {
    return (beanInfoCache.remove(c) != null);
  }
  
  public static void flushBeanInfoCache() {
    beanInfoCache.clear();
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
