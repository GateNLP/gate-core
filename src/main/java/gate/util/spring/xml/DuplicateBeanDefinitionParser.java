/*
 *  DuplicateBeanDefinitionParser.java
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
 *  $Id: DuplicateBeanDefinitionParser.java 17657 2014-03-14 09:08:56Z markagreenwood $
 */
package gate.util.spring.xml;

import gate.util.spring.DuplicateResourceFactoryBean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DuplicateBeanDefinitionParser
                                          extends
                                            CustomisableBeanDefinitionParser {
  @Override
  protected void doParse(Element element, ParserContext parserContext,
          BeanDefinitionBuilder builder) {
    if(element.hasAttribute("scope")) {
      if("prototype".equals(element.getAttribute("scope"))) {
        parserContext.getReaderContext().warning(
                "Prototype scope is not recommended for <gate:duplicate>", element);
      }
      builder.setScope(element.getAttribute("scope"));
    }
    
    if(element.hasAttribute("return-template")) {
      builder.addPropertyValue("returnTemplate",
              Boolean.valueOf(element.getAttribute("return-template")));
    }

    if(element.hasAttribute("template-ref")) {
      // target-ref takes precedence
      builder.addPropertyReference("template", element.getAttribute("template-ref"));
    }
    else {
      // try and find a sub-element other than <gate:customisers>
      Element targetElt = null;
      NodeList children = element.getChildNodes();
      for(int i = 0; i < children.getLength(); i++) {
        Node n = children.item(i);
        if(n instanceof Element &&
                !("http://gate.ac.uk/ns/spring".equals(n.getNamespaceURI())
                        && "customisers".equals(n.getLocalName()))) {
          if(targetElt != null) {
            parserContext.getReaderContext().error(
                    "<gate:duplicate> element can only have one sub element " +
                    "(apart from customisers, source)", element);
          }
          targetElt = (Element)n;
        }
      }
      if(targetElt != null) {
        builder.addPropertyValue("template", parserContext.getDelegate()
                .parsePropertySubElement(targetElt, builder.getRawBeanDefinition()));
      }
    }
    // handle the customisers
    extractCustomisers(element, parserContext, builder);
  }
  
  @Override
  protected Class<?> getBeanClass(Element element) {
    return DuplicateResourceFactoryBean.class;
  }

}
