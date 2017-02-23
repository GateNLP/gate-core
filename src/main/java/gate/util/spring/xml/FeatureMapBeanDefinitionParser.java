/*
 *  FeatureMapBeanDefinitionParser.java
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
 *  $Id: FeatureMapBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring.xml;

import gate.util.spring.FeatureMapFactoryBean;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Bean definition parser for <code>&lt;gate:feature-map&gt;</code>
 * elements, producing a definition for a {@link FeatureMapFactoryBean}.
 */
public class FeatureMapBeanDefinitionParser
                                           extends
                                             AbstractSingleBeanDefinitionParser {

  @Override
  protected void doParse(Element element, ParserContext parserContext,
          BeanDefinitionBuilder builder) {
    Map<?,?> sourceMap = parserContext.getDelegate().parseMapElement(element,
            builder.getRawBeanDefinition());
    builder.addPropertyValue("sourceMap", sourceMap);
    if(element.hasAttribute("scope")) {
      builder.setScope(element.getAttribute("scope"));
    }
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return FeatureMapFactoryBean.class;
  }

}
