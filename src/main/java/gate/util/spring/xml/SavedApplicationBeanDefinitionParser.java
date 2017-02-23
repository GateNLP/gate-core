/*
 *  SavedApplicationBeanDefinitionParser.java
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
 *  $Id: SavedApplicationBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring.xml;

import gate.util.spring.SavedApplicationFactoryBean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for <code>&lt;gate:saved-application&gt;</code>,
 * producing a definition for a {@link SavedApplicationFactoryBean}.
 */
public class SavedApplicationBeanDefinitionParser
                                                 extends
                                                   CustomisableBeanDefinitionParser {
  @Override
  protected void doParse(Element element, ParserContext parserContext,
          BeanDefinitionBuilder builder) {
    if(element.hasAttribute("scope")) {
      builder.setScope(element.getAttribute("scope"));
    }

    builder.addPropertyValue("location", element.getAttribute("location"));

    extractCustomisers(element, parserContext, builder);
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return SavedApplicationFactoryBean.class;
  }

}
