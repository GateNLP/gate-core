/*
 *  InitBeanDefinitionParser.java
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
 *  $Id: InitBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */

package gate.util.spring.xml;

import gate.util.spring.Init;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for <code>&lt;gate:init&gt;</code> elements,
 * producing a definition for an {@link Init} object.
 */
public class InitBeanDefinitionParser
                                     extends
                                       AbstractSimpleBeanDefinitionParser {

  @Override
  protected void doParse(Element element, ParserContext ctx,
          BeanDefinitionBuilder builder) {
    super.doParse(element, ctx, builder);
    Element preloadElt = DomUtils.getChildElementByTagName(element,
            "preload-plugins");
    if(preloadElt != null) {
      List<?> preloadPluginsList = ctx.getDelegate().parseListElement(preloadElt,
              builder.getBeanDefinition());
      builder.addPropertyValue("preloadPlugins", preloadPluginsList);
    }
    builder.setInitMethodName("init");
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return Init.class;
  }

  @Override
  protected boolean shouldGenerateIdAsFallback() {
    return true;
  }

}
