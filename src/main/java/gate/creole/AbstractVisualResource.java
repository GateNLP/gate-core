/*
 *  AbstractVisualResource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 24/Jan/2001
 *
 *  $Id: AbstractVisualResource.java 17604 2014-03-09 10:08:13Z markagreenwood $
 */

package gate.creole;

import java.beans.BeanInfo;
import java.beans.Introspector;

import javax.swing.JPanel;

import gate.*;
import gate.gui.Handle;
import gate.util.Strings;

/** A convenience implementation of VisualResource with some default code. */
public abstract class AbstractVisualResource extends JPanel
                                             implements VisualResource{

  private static final long serialVersionUID = -3561399635284613196L;

  /**
   * Package access constructor to stop normal initialisation.
   * This kind of resources should only be created by the Factory class
   */
  public AbstractVisualResource(){
  }

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

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    return this;
  }//init()

  /** Does nothing now, but meant to clear all internal data **/
  @Override
  public void cleanup() {
    this.handle = null;
    features.clear();
  }//clear()

  /**
   * Called by the GUI when this viewer/editor has to initialise itself for a
   * specific object.
   * @param target the object (be it a {@link gate.Resource},
   * {@link gate.DataStore} or whatever) this viewer has to display
   */
  @Override
  public void setTarget(Object target){
    throw new RuntimeException(
      "Class " + getClass() + " hasn't implemented the setTarget() method!");
  }


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
  
  /**
   * Get the current values for all this resource's registered
   * init-time parameters.
   */
  public FeatureMap getInitParameterValues() throws ResourceInstantiationException {
    return AbstractResource.getInitParameterValues(this);
  }

  // Properties for the resource
  protected FeatureMap features;
  
  /**
   * The handle for this visual resource
   */
  protected Handle handle;

}//AbstractVisualResource