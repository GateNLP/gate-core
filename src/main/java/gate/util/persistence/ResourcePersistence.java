/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 25/10/2001
 *
 *  $Id: ResourcePersistence.java 20046 2017-02-01 10:10:20Z markagreenwood $
 *
 */
package gate.util.persistence;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.creole.Parameter;
import gate.creole.ParameterException;
import gate.creole.ParameterList;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.NameBearer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Holds the data needed to serialise and recreate a {@link Resource}.
 * This data is considered to be: the resource class name, the resource name,
 * the resource features and the resource initialistion parameters.
 */
class ResourcePersistence extends AbstractPersistence{

  protected static final Logger log = Logger.getLogger(ResourcePersistence.class);
  
  @Override
  public void extractDataFromSource(Object source) throws PersistenceException{
    if(! (source instanceof Resource)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                Resource.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + Resource.class.getName());
    }
    Resource res = (Resource)source;
    resourceType = res.getClass().getName();
    resourceName = ((NameBearer)res).getName();

    ResourceData rData = Gate.getCreoleRegister().get(resourceType);
    if(rData == null) throw new PersistenceException(
                                "Could not find CREOLE data for " +
                                resourceType);
    ParameterList params = rData.getParameterList();
    try{
      //get the values for the init time parameters
      initParams = Factory.newFeatureMap();
      //this is a list of lists
      Iterator<List<Parameter>> parDisjIter = params.getInitimeParameters().iterator();
      while(parDisjIter.hasNext()){
        Iterator<Parameter> parIter = parDisjIter.next().iterator();
        while(parIter.hasNext()){
          Parameter parameter = parIter.next();
          String parName = parameter.getName();
          Object parValue = res.getParameterValue(parName);
          if (storeParameterValue(parValue, parameter.getDefaultValue())) {
        	  ((FeatureMap)initParams).put(parName, parValue);
          }
        }
      }
      initParams = PersistenceManager.getPersistentRepresentation(initParams);

      //get the features
      if(res.getFeatures() != null){
        features = Factory.newFeatureMap();
        ((FeatureMap)features).putAll(res.getFeatures());
        features = PersistenceManager.getPersistentRepresentation(features);
      }
    }catch(ResourceInstantiationException | ParameterException rie){
      throw new PersistenceException(rie);
    }
  }
  
	/**
	 * Returns true if we should store the parameter value as it is different
	 * from the default value.
	 * 
	 * @param value
	 *            the value we might store
	 * @param defaultValue
	 *            the default value
	 * @return true if we should store the parameter value as it is different
	 *         from the default value, false otherwise
	 */
	protected static boolean storeParameterValue(Object value, Object defaultValue) {
		// if both are null then we don't need to store the value
		if (value == null && defaultValue == null)
			return false;

		// if only one of them is null then we do need to store
		if (value == null || defaultValue == null)
			return true;

		// if neither are null and they are identical objects then we don't need
		// to store
		if (value == defaultValue)
			return false;

		// only store if the two objects are truly differnet
		return !value.equals(defaultValue);
	}

  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException {
    if(initParams != null)
      initParams = PersistenceManager.getTransientRepresentation(
              initParams,containingControllerName,initParamOverrides);
    if(features != null)
      features = PersistenceManager.getTransientRepresentation(
              features,containingControllerName,initParamOverrides);
    if(initParamOverrides != null) {
      // check if there is a map for this resource Id in the overrides
      String containingControllerNameToUse = 
              containingControllerName == null ? "" : containingControllerName;
      String resourceId = containingControllerNameToUse+"\t"+resourceName;
      if(initParamOverrides.containsKey(resourceId)) {
        Map<String,Object> parmOverrides = initParamOverrides.get(resourceId);
        if(initParams instanceof FeatureMap) {
          FeatureMap fm = (FeatureMap)initParams;
          // override the values
          // do this in a loop instead of using putAll so we can log the changes
          for(String name : parmOverrides.keySet()) {
            Object obj = parmOverrides.get(name);
            log.info(
                    "Overriding init parameter "+name+
                    " for "+containingControllerNameToUse+
                    "//"+resourceName+" with "+obj);
            fm.put(name, obj);
          }
        }
      }
    } 
    Resource res = Factory.createResource(resourceType, (FeatureMap)initParams,
                                          (FeatureMap)features,resourceName);
    return res;
  }

  protected String resourceType;
  protected String resourceName;
  protected Object initParams;
  protected Object features;
  static final long serialVersionUID = -3196664486112887875L;
}