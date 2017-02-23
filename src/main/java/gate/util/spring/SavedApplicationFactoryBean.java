/*
 *  SavedApplicationFactoryBean.java
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
 *  $Id: SavedApplicationFactoryBean.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring;

import gate.Factory;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

/**
 * Spring factory bean to load a saved GATE application from a Spring
 * resource location. Typically used via the <code>gate:</code>
 * extension namespace:
 * 
 * <pre>
 * &lt;gate:saved-application location=&quot;WEB-INF/application.gapp&quot; /&gt;
 * </pre>
 * 
 * See {@link Init} for details of how to declare this namespace in your
 * bean definition files. We first attempt to resolve the location as a
 * File, if this fails we fall back to resolving it as a URL. This is
 * useful with servlet context resources (with an expanded web
 * application) as it will give a <code>file:</code> URL rather than
 * an opaque scheme like <code>jndi:</code> (Tomcat). The element also
 * supports a nested <code>&lt;gate:customisers&gt;</code> element,
 * giving a list of {@link ResourceCustomiser}s that will be applied to
 * the application after it is loaded.
 */
public class SavedApplicationFactoryBean extends GateAwareObject implements
                                                                FactoryBean,
                                                                DisposableBean {

  private Resource location;

  private List<ResourceCustomiser> customisers;

  private Object object = null;

  public void setLocation(Resource location) {
    this.location = location;
  }

  public void setCustomisers(List<ResourceCustomiser> customisers) {
    this.customisers = customisers;
  }

  /**
   * Loads the saved application file and applies any registered
   * customisers.
   * 
   * @return the (possibly customised) application
   */
  @Override
  public Object getObject() throws GateException, IOException {
    if(object == null) {
      ensureGateInit();

      object = PersistenceManager.loadObjectFromUrl(
              SpringFactory.resourceToUrl(location));

      if(customisers != null) {
        for(ResourceCustomiser c : customisers) {
          try {
            c.customiseResource((gate.Resource)object);
          }
          catch(GateException gx) {
            throw gx;
          }
          catch(IOException ix) {
            throw ix;
          }
          catch(RuntimeException rx) {
            throw rx;
          }
          catch(Exception e) {
            throw new ResourceInstantiationException(
                    "Exception in resource customiser", e);
          }
        }
      }
    }

    return object;
  }

  @Override
  public Class<?> getObjectType() {
    return null;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * Destroy the resource created by this bean, by passing it to
   * {@link Factory#deleteResource}.  This will in turn delete
   * any PRs that the application contains.
   */
  @Override
  public void destroy() throws Exception {
    if(object != null && object instanceof gate.Resource) {
      Factory.deleteResource((gate.Resource)object);
    }
  }

}
