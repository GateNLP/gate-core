/*
 *  PooledProxyBeanDefinitionDecorator.java
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
 *  $Id: PooledProxyBeanDefinitionDecorator.java 18817 2015-07-08 10:18:34Z ian_roberts $
 */
package gate.util.spring.xml;

import javax.xml.XMLConstants;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Conventions;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Bean decorator to easily create a pool of target beans.
 * 
 * <pre>
 * &lt;bean id=&quot;myBean&quot; class=&quot;some.pkg.MyBean&quot;&gt;
 *   &lt;property name=&quot;gateApplication&quot; ref=&quot;app&quot; /&gt;
 *   &lt;gate:pooled-proxy max-size=&quot;3&quot; /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <p>
 * This replaces the <code>myBean</code> bean with a proxy that
 * delegates to a pool of target objects. The targets are obtained by
 * converting the original <code>myBean</code> definition to a
 * prototype-scoped bean definition and setting that as the
 * targetBeanName of a CommonsPoolTargetSource. The
 * CommonsPoolTargetSource is then used as the target source for a
 * standard ProxyFactoryBean, which is created with the scope specified
 * in the original <code>myBean</code> definition (usually singleton).
 * </p>
 * 
 * <p>
 * If the pooled-proxy element has an attribute proxy-target-class, this
 * value is passed through to the generated ProxyFactoryBean. All other
 * attributes are passed through to the CommonsPoolTargetSource. The
 * element also supports an initial-size attribute. If specified it will
 * pre-populate the pool with this number of instances, which is useful
 * if you want to load several copies of a saved application up-front
 * (the normal behaviour of a Spring pool is to only instantiate the
 * pooled objects as and when they are required).
 * </p>
 * 
 * <p>
 * <b>NOTE</b> In addition to the <code>spring-aop</code> JAR, you also
 * need the Apache <code>commons-pool</code> JAR and its dependencies
 * available to your application in order to use this class.
 * </p>
 */
public class PooledProxyBeanDefinitionDecorator implements
                                               BeanDefinitionDecorator {

  public static final String TARGET_PREFIX = "gate.util.spring.pool-target.";

  public static final String TARGET_SOURCE_PREFIX =
          "gate.util.spring.pool-target-source.";

  public static final String POOL_FILLER_PREFIX =
          "gate.util.spring.pool-filler.";

  private static final String PROXY_TARGET_CLASS = "proxy-target-class";

  private static final String INITIAL_SIZE = "initial-size";

  private static final String TARGET_SOURCE_CLASS = "target-source-class";

  @Override
  public BeanDefinitionHolder decorate(Node node,
          BeanDefinitionHolder definition, ParserContext parserContext) {
    String originalBeanName = definition.getBeanName();
    String[] originalAliases = definition.getAliases();
    String targetBeanName = TARGET_PREFIX + originalBeanName;

    BeanDefinition targetDefinition = definition.getBeanDefinition();
    BeanDefinitionRegistry reg = parserContext.getRegistry();

    // remember the scope of the original definition,
    // change the target bean to be prototype.
    String originalScope = targetDefinition.getScope();
    targetDefinition.setScope("prototype");

    // create a bean definition for the target source, pointing at the
    // target bean name
    RootBeanDefinition targetSourceDefinition = new RootBeanDefinition();
    targetSourceDefinition.setScope(originalScope);
    String targetSourceClassName = null;
    if(node instanceof Element
            && ((Element)node).hasAttribute(TARGET_SOURCE_CLASS)) {
      targetSourceClassName = ((Element)node).getAttribute(TARGET_SOURCE_CLASS);
    } else {
      targetSourceClassName =
              "org.springframework.aop.target.CommonsPoolTargetSource";
    }
    targetSourceDefinition.setBeanClassName(targetSourceClassName);
    targetSourceDefinition.getPropertyValues().addPropertyValue(
            "targetBeanName", targetBeanName);
    String targetSourceBeanName = TARGET_SOURCE_PREFIX + originalBeanName;

    // apply any attributes of the pooled-proxy element except
    // proxy-target-class and initial-size as properties of the
    // target source
    if(node instanceof Element) {
      Element ele = (Element)node;
      NamedNodeMap attrs = ele.getAttributes();
      for(int i = 0; i < attrs.getLength(); i++) {
        Attr att = (Attr)attrs.item(i);
        if(!XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(att.getNamespaceURI())
                && !XMLConstants.XML_NS_URI.equals(att.getNamespaceURI())
                && !XMLConstants.XMLNS_ATTRIBUTE.equals(att.getLocalName())
                && !PROXY_TARGET_CLASS.equals(att.getLocalName())
                && !INITIAL_SIZE.equals(att.getLocalName())
                && !TARGET_SOURCE_CLASS.equals(att.getLocalName())) {
          String propName =
                  Conventions.attributeNameToPropertyName(att.getLocalName());
          targetSourceDefinition.getPropertyValues().addPropertyValue(propName,
                  att.getValue());
        }
      }
    }

    // create a bean definition for the proxy, pointing to the target
    // source
    RootBeanDefinition proxyDefinition = new RootBeanDefinition();
    proxyDefinition.setScope(originalScope);
    proxyDefinition
            .setBeanClassName("org.springframework.aop.framework.ProxyFactoryBean");
    proxyDefinition.getPropertyValues().addPropertyValue("targetSource",
            new RuntimeBeanReference(targetSourceBeanName));

    Boolean proxyTargetClass = Boolean.TRUE;
    if(node instanceof Element) {
      Element ele = (Element)node;
      if(ele.hasAttribute(PROXY_TARGET_CLASS)) {
        proxyTargetClass =
                Boolean.valueOf(ele.getAttribute(PROXY_TARGET_CLASS));
      }
    }
    proxyDefinition.getPropertyValues().addPropertyValue("proxyTargetClass",
            proxyTargetClass);

    if(targetDefinition instanceof AbstractBeanDefinition) {
      AbstractBeanDefinition abd = (AbstractBeanDefinition)targetDefinition;
      proxyDefinition.setDependsOn(abd.getDependsOn());
      proxyDefinition.setAutowireCandidate(abd.isAutowireCandidate());
      // The target bean should be ignored in favor of the scoped proxy.
      abd.setAutowireCandidate(false);
    }

    // if we have an initial-size attribute, create a pool filler bean
    // to pre-fill the pool to the stated initial size
    if(node instanceof Element && ((Element)node).hasAttribute(INITIAL_SIZE)) {
      RootBeanDefinition poolFillerDefinition =
              new RootBeanDefinition(PoolFiller.class);
      String poolFillerBeanName = POOL_FILLER_PREFIX + originalBeanName;
      poolFillerDefinition.getPropertyValues().addPropertyValue("targetSource",
              new RuntimeBeanReference(targetSourceBeanName));
      poolFillerDefinition.getPropertyValues().addPropertyValue("numInstances",
              ((Element)node).getAttribute(INITIAL_SIZE));
      poolFillerDefinition.setScope(targetSourceDefinition.getScope());
      // make the proxy depend on the pool filler
      String[] proxyDepends = proxyDefinition.getDependsOn();
      if(proxyDepends == null) {
        proxyDepends = new String[1];
      } else {
        String[] newDepends = new String[proxyDepends.length + 1];
        System.arraycopy(proxyDepends, 0, newDepends, 0, proxyDepends.length);
        proxyDepends = newDepends;
      }
      proxyDepends[proxyDepends.length - 1] = poolFillerBeanName;
      proxyDefinition.setDependsOn(proxyDepends);

      reg.registerBeanDefinition(poolFillerBeanName, poolFillerDefinition);
    }

    // register the (prototype) target bean under its new name
    reg.registerBeanDefinition(targetBeanName, targetDefinition);
    // register the target source
    reg.registerBeanDefinition(targetSourceBeanName, targetSourceDefinition);

    // return the bean definition for the proxy
    return new BeanDefinitionHolder(proxyDefinition, originalBeanName,
            originalAliases);
  }

}
