/*
 *  SetParameterBeanDefinitionParser.java
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
 *  $Id: SetParameterBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring.xml;

import gate.util.spring.SetParameterResourceCustomiser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for <code>&lt;gate:set-parameter&gt;</code>,
 * producing a definition of a {@link SetParameterResourceCustomiser}
 * object.
 */
public class SetParameterBeanDefinitionParser
                                             extends
                                               AbstractSingleBeanDefinitionParser {

  @Override
  protected void doParse(Element element, ParserContext parserContext,
          BeanDefinitionBuilder builder) {
    // never null by schema
    builder.addPropertyValue("paramName", element.getAttribute("name"));
    if(element.hasAttribute("pr-name")) {
      builder.addPropertyValue("prName", element.getAttribute("pr-name"));
    }

    builder.addPropertyValue("value", parserContext.getDelegate()
            .parsePropertyValue(element, builder.getRawBeanDefinition(),
                    "value"));
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return SetParameterResourceCustomiser.class;
  }

}
