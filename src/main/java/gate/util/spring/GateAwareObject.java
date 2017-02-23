/*
 *  GateAwareObject.java
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
 *  $Id: GateAwareObject.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.util.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * Convenience superclass for objects that may be created by Spring and
 * need to ensure that GATE is initialised before they do their work.
 * Subclasses can call {@link #ensureGateInit} to force the
 * initialisation of any {@link Init} beans in the creating
 * {@link BeanFactory}, thus ensuring that GATE will be properly
 * initialised. The method does nothing if the bean factory has not been
 * set (e.g. if the object is being used outside Spring). In this case,
 * we assume that GATE initialisation has already been handled
 * elsewhere.
 */
public class GateAwareObject implements BeanFactoryAware {

  private BeanFactory factory;

  public GateAwareObject() {
    super();
  }

  /**
   * To be called by subclasses to ensure that any {@link Init} beans in
   * the containing bean factory (and its ancestor factories, if any)
   * have been initialised.
   */
  protected void ensureGateInit() {
    if(factory != null && factory instanceof ListableBeanFactory) {
      String[] initNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
              (ListableBeanFactory)factory, Init.class);
      for(String name : initNames) {
        factory.getBean(name);
      }
    }
  }

  @Override
  public void setBeanFactory(BeanFactory factory) {
    this.factory = factory;
  }

}
