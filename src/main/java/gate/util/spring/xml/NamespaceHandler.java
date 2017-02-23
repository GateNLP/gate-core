/*
 *  NamespaceHandler.java
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
 *  $Id: NamespaceHandler.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.util.spring.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Spring namespace handler for the http://gate.ac.uk/ns/spring
 * namespace.
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

  @Override
  public void init() {
    registerBeanDefinitionParser("init", new InitBeanDefinitionParser());
    registerBeanDefinitionParser("url", new UrlBeanDefinitionParser());
    registerBeanDefinitionParser("extra-plugin",
            new ExtraGatePluginBeanDefinitionParser());
    registerBeanDefinitionParser("feature-map",
            new FeatureMapBeanDefinitionParser());
    registerBeanDefinitionParser("resource", new ResourceBeanDefinitionParser());
    registerBeanDefinitionParser("saved-application",
            new SavedApplicationBeanDefinitionParser());
    registerBeanDefinitionParser("duplicate", new DuplicateBeanDefinitionParser());
    registerBeanDefinitionParser("set-parameter",
            new SetParameterBeanDefinitionParser());
    registerBeanDefinitionParser("add-pr", new AddPRBeanDefinitionParser());
    registerBeanDefinitionDecorator("pooled-proxy",
            new PooledProxyBeanDefinitionDecorator());
  }

}
