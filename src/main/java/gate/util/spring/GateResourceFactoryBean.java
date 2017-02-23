/*
 *  GateResourceFactoryBean.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 22/Jan/2008
 *
 *  $Id: GateResourceFactoryBean.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring;

import java.util.List;

import gate.Factory;
import gate.FeatureMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring factory bean to create a GATE resource (LR, PR, controller).
 * Generally used via the <code>gate:</code> extension namespace,
 * e.g.:
 * 
 * <pre>
 * &lt;gate:resource id=&quot;doc&quot; scope=&quot;prototype&quot;
 *                resource-class=&quot;gate.corpora.DocumentImpl&quot;
 *                resource-name=&quot;News document&quot;&gt;
 *   &lt;gate:parameters&gt;
 *     &lt;entry key=&quot;sourceUrl&quot;&gt;
 *       &lt;value type=&quot;org.springframework.core.io.Resource&quot;&gt;resources/doc.xm&lt;/value&gt;
 *     &lt;/entry&gt;
 *     &lt;entry key=&quot;preserveOriginalContent&quot; value=&quot;true&quot; /&gt;
 *   &lt;/gate:parameters&gt;
 *   &lt;gate:features&gt;
 *     &lt;entry key=&quot;genre&quot; value=&quot;news&quot; /&gt;
 *   &lt;/gate:features&gt;
 *   &lt;gate:customisers&gt;
 *     &lt;!-- optional list of {@link ResourceCustomiser}s applied to the resource after creation --&gt;
 *   &lt;/gate:customisers&gt;
 * &lt;/gate:resource&gt;
 * </pre>
 * 
 * The <code>gate:parameters</code> and <code>gate:features</code>
 * elements are FeatureMaps giving the init-time parameters and features
 * for the resource respectively. Any Spring Resource values in these
 * maps are converted to URLs, so the rest of the GATE code does not
 * need to know about Spring. For details of how to declare the
 * <code>gate</code> namespace, see {@link Init}.
 */
public class GateResourceFactoryBean extends GateAwareObject implements
                                                            FactoryBean,
                                                            DisposableBean {

  private String resourceClass;

  private String resourceName;

  private FeatureMap parameters;

  private FeatureMap features;

  private List<ResourceCustomiser> customisers;

  private gate.Resource object;

  /**
   * Create the resource specified by this bean.
   */
  @Override
  public Object getObject() throws Exception {
    if(object == null) {
      ensureGateInit();

      if(parameters == null) {
        parameters = Factory.newFeatureMap();
      }

      if(features == null) {
        features = Factory.newFeatureMap();
      }

      if(resourceName == null) {
        object = Factory.createResource(resourceClass, parameters, features);
      }
      else {
        object = Factory.createResource(resourceClass, parameters, features,
                resourceName);
      }

      if(customisers != null) {
        for(ResourceCustomiser c : customisers) {
          c.customiseResource(object);
        }
      }
    }

    return object;
  }

  @Override
  public Class<?> getObjectType() {
    if(object != null) {
      return object.getClass();
    }
    return null;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * Destroy the resource created by this bean, by passing it to
   * {@link Factory#deleteResource}.
   */
  @Override
  public void destroy() throws Exception {
    if(object != null) {
      Factory.deleteResource(object);
    }
  }

  public void setResourceClass(String resourceClass) {
    this.resourceClass = resourceClass;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public void setParameters(FeatureMap parameters) {
    this.parameters = parameters;
  }

  public void setFeatures(FeatureMap features) {
    this.features = features;
  }

  public void setCustomisers(List<ResourceCustomiser> customisers) {
    this.customisers = customisers;
  }
}
