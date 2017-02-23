package gate.util.spring.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Bean definition parser for
 * <code>&lt;gate:url&gt;path&lt;/gate:url&gt;</code>, producing the
 * equivalent of
 * 
 * <pre>
 * &lt;bean class="gate.util.spring.SpringFactory"
 *          factory-method="resourceToUrl"&gt;
 *   &lt;constructor-arg value="path" /&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * The <code>&lt;gate:url&gt;</code> element can take an <code>id</code>
 * attribute, but in most cases it will be used inline (e.g. as the value
 * of a property or as an entry in a feature map).
 */
public class UrlBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

  @Override
  protected void doParse(Element element, BeanDefinitionBuilder builder) {
    builder.setFactoryMethod("resourceToUrl");
    builder.addConstructorArgValue(DomUtils.getTextValue(element));
  }

  @Override
  protected String getBeanClassName(Element element) {
    return "gate.util.spring.SpringFactory";
  }

  @Override
  protected boolean shouldGenerateIdAsFallback() {
    return true;
  }

}
