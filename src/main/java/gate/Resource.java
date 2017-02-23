/*
 *  Resource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 11/Feb/2000
 *
 *  $Id: Resource.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate;

import java.io.Serializable;

import gate.creole.ResourceInstantiationException;
import gate.util.FeatureBearer;
import gate.util.NameBearer;

/** Models all sorts of resources.
  */
public interface Resource extends FeatureBearer, NameBearer, Serializable
{
  /** Initialise this resource, and return it. */
  public Resource init() throws ResourceInstantiationException;

  /** Clears the internal data of the resource, when it gets released **/
  public void cleanup();


  //Parameters utility methods
  /**
   * Gets the value of a parameter of this resource.
   * @param paramaterName the name of the parameter
   * @return the current value of the parameter
   */
  public Object getParameterValue(String paramaterName)
                throws ResourceInstantiationException;

  /**
   * Sets the value for a specified parameter.
   *
   * @param paramaterName the name for the parameteer
   * @param parameterValue the value the parameter will receive
   */
  public void setParameterValue(String paramaterName, Object parameterValue)
              throws ResourceInstantiationException;

  /**
   * Sets the values for more parameters in one step.
   *
   * @param parameters a {@link FeatureMap} that has parameter names as keys and
   * parameter values as values.
   */
  public void setParameterValues(FeatureMap parameters)
              throws ResourceInstantiationException;

} // interface Resource
