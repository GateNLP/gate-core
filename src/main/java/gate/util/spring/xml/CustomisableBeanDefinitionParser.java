/*
 *  CustomisableBeanDefinitionParser.java
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
 *  $Id: CustomisableBeanDefinitionParser.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util.spring.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Common superclass for BeanDefinitionParsers for elements that support
 * a nested <code>&lt;gate:customisers&gt;</code> element mapping to a
 * "customisers" property in the parsed definition.
 */
public abstract class CustomisableBeanDefinitionParser
                                                      extends
                                                        AbstractSingleBeanDefinitionParser {

  public CustomisableBeanDefinitionParser() {
    super();
  }

  /**
   * Processes the customisers sub-element of the given element. If no
   * such sub-element exists, does nothing.
   */
  protected void extractCustomisers(Element element,
          ParserContext parserContext, BeanDefinitionBuilder builder) {
    Element customisersElement = DomUtils.getChildElementByTagName(element,
            "customisers");
    if(customisersElement != null) {
      if(customisersElement.hasAttribute("ref")) {
        NodeList childNodes = customisersElement.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++) {
          if(childNodes.item(i) instanceof Element) {
            parserContext.getReaderContext().error(
                    "customisers element in saved-application cannot "
                            + "have both children and a 'ref' attribute",
                    customisersElement);
            break;
          }
        }

        builder.addPropertyReference("customisers", customisersElement
                .getAttribute("ref"));
      }
      else {
        builder.addPropertyValue("customisers", parserContext.getDelegate()
                .parseListElement(customisersElement,
                        builder.getRawBeanDefinition()));
      }
    }
  }

}
