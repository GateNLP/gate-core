package gate.util.spring.xml;

import gate.util.spring.ExtraGatePlugin;
import gate.util.spring.Init;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Bean definition parser for
 * <code>&lt;gate:extra-plugin&gt;path&lt;/gate:extra-plugin&gt;</code>
 * producing the equivalent of
 * 
 * <pre>
 * &lt;bean class="gate.util.spring.ExtraGatePlugin"&gt;
 *   &lt;property name="location" value="path" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * While the element can take an <code>id</code> it is not normally
 * necessary to provide one as the {@link Init} bean enumerates all
 * {@link ExtraGatePlugin} beans by type, ignoring their IDs.
 */
public class ExtraGatePluginBeanDefinitionParser
                                                extends
                                                  AbstractSingleBeanDefinitionParser {

  @Override
  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.addPropertyValue("location", DomUtils.getTextValue(element));
  }

  @Override
  protected Class<?> getBeanClass(Element element) {
    return ExtraGatePlugin.class;
  }

  @Override
  protected boolean shouldGenerateIdAsFallback() {
    return true;
  }
}
