/*
 *  CreoleXmlUpperCaseFilter.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 1/Sept/2000
 *
 *  $Id: CreoleXmlUpperCaseFilter.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * SAX {@link XMLFilter} implementation used when reading a creole.xml
 * file to ensure that all the standard creole elements and their
 * attribute names are converted to upper case. All the creole.xml files
 * built into GATE use upper case for their elements and attributes, but
 * historically the files have been treated case-insensitively.
 * Non-standard elements (which are added as features of the resource)
 * are untouched.
 */
public class CreoleXmlUpperCaseFilter extends XMLFilterImpl {
  private Set<String> knownElements = new HashSet<String>(Arrays.asList(
          "CREOLE-DIRECTORY", "CREOLE", "RESOURCE", "AUTOINSTANCE",
          "HIDDEN-AUTOINSTANCE", "PARAM", "PARAMETER", "GUI", "OR", "NAME",
          "JAR", "CLASS", "COMMENT", "INTERFACE", "ICON", "PRIVATE", "TOOL",
          "MAIN_VIEWER", "RESOURCE_DISPLAYED", "ANNOTATION_TYPE_DISPLAYED",
          "HELPURL"));

  /**
   * Process the end of an element. If the element is a standard
   * creole.xml element then its name is converted to upper case,
   * otherwise it is passed through untouched.
   */
  @Override
  public void endElement(String uri, String localName, String name)
          throws SAXException {
    String upperCaseName = localName.toUpperCase();
    if(knownElements.contains(upperCaseName)) {
      super.endElement(uri, upperCaseName, name.substring(0,
              name.indexOf(':') + 1)
              + upperCaseName);
    }
    else {
      super.endElement(uri, localName, name);
    }
  }

  /**
   * Process the start of an element. If the element is a standard
   * creole.xml element then it and all its attributes have their names
   * converted to upper case. Other elements are passed through
   * untouched.
   */
  @Override
  public void startElement(String uri, String localName, String name,
          Attributes atts) throws SAXException {
    String upperCaseName = localName.toUpperCase();
    if(knownElements.contains(upperCaseName)) {
      AttributesImpl newAtts = new AttributesImpl();
      for(int i = 0; i < atts.getLength(); i++) {
        String upperCaseAttrName = atts.getLocalName(i).toUpperCase();
        String attrQName = atts.getQName(i);
        newAtts.addAttribute(atts.getURI(i), upperCaseAttrName, attrQName
                .substring(0, attrQName.indexOf(':') + 1)
                + upperCaseAttrName, atts.getType(i), atts.getValue(i));
      }
      super.startElement(uri, upperCaseName, name.substring(0, name
              .indexOf(':') + 1)
              + upperCaseName, newAtts);
    }
    else {
      super.startElement(uri, localName, name, atts);
    }
  }
}
