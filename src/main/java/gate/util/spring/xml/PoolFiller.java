/*
 *  PoolFiller.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 10/Apr/2010
 *
 *  $Id: PoolFiller.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.util.spring.xml;

import org.springframework.aop.target.AbstractPoolingTargetSource;
import org.springframework.beans.factory.InitializingBean;

/**
 * Simple bean that takes a pooled target source and performs a sequence
 * of n getTarget calls followed by n releaseTarget calls. This has the
 * effect of pre-populating the pool with n instances up-front (normally
 * a pooled target source only creates objects the first time they are
 * required). Note that if the specified number of instances is greater
 * than the maximum size of the pool the number of calls will be reduced
 * accordingly.
 */
public class PoolFiller implements InitializingBean {

  private AbstractPoolingTargetSource targetSource;

  private int numInstances = 1;

  /**
   * Set the target source to be populated.
   */
  public void setTargetSource(AbstractPoolingTargetSource targetSource) {
    this.targetSource = targetSource;
  }

  /**
   * Set the number of nested get/release calls to make. The actual
   * number of calls made is the minimum of this value and the maximum
   * size of the pool.
   */
  public void setNumInstances(int numInstances) {
    this.numInstances = numInstances;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    int instancesToCreate = numInstances;
    if(instancesToCreate > targetSource.getMaxSize()) {
      instancesToCreate = targetSource.getMaxSize();
    }
    checkoutTargets(instancesToCreate);
  }

  /**
   * Recursive helper method to check out <code>num</code> targets
   * from the target source and then release them, with proper
   * try/finally handling in case of exceptions. If the target source is
   * backed by a pool this will have the effect of forcing upfront
   * creation of at least <code>num</code> instances in the pool.
   */
  private void checkoutTargets(int num) throws Exception {
    if(num < 1) return;
    Object target = targetSource.getTarget();
    try {
      checkoutTargets(num - 1);
    }
    finally {
      if(target != null) targetSource.releaseTarget(target);
    }
  }
}
