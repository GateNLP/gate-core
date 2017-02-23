/*
 *  ResourceBeanDefinitionParser.java
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
 *  $Id: ResourceBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring.xml;

import gate.util.spring.GateResourceFactoryBean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for <code>&lt;gate:resource&gt;</code>,
 * producing a definition for a {@link GateResourceFactoryBean}.
 */
public class ResourceBeanDefinitionParser
                                         extends
                                           CustomisableBeanDefinitionParser {
  private FeatureMapBeanDefinitionParser fmParser = new FeatureMapBeanDefinitionParser();

  @Override
  protected void doParse(Element element, ParserContext parserContext,
          BeanDefinitionBuilder builder) {

    if(element.hasAttribute("scope")) {
      builder.setScope(element.getAttribute("scope"));
    }

    // this can't be null - use="required" in schema
    String resourceClass = element.getAttribute("resource-class");
    builder.addPropertyValue("resourceClass", resourceClass);

    BeanDefinition paramsDefinition = null;
    Element parametersElement = DomUtils.getChildElementByTagName(element,
            "parameters");
    if(parametersElement != null) {
      paramsDefinition = fmParser.parse(parametersElement, nestedContext(
              parserContext, builder));
      builder.addPropertyValue("parameters", paramsDefinition);
    }

    BeanDefinition featuresDefinition = null;
    Element featuresElement = DomUtils.getChildElementByTagName(element,
            "features");
    if(featuresElement != null) {
      featuresDefinition = fmParser.parse(featuresElement, nestedContext(
              parserContext, builder));
      builder.addPropertyValue("features", featuresDefinition);
    }

    if(element.hasAttribute("resource-name")) {
      builder.addPropertyValue("resourceName", element
              .getAttribute("resource-name"));
    }

    extractCustomisers(element, parserContext, builder);
  }

  private ParserContext nestedContext(ParserContext parserContext,
          BeanDefinitionBuilder builder) {
    return new ParserContext(parserContext.getReaderContext(), parserContext
            .getDelegate(), builder.getRawBeanDefinition());
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return GateResourceFactoryBean.class;
  }

}
