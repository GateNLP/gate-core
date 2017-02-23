/*
 *  XML11StaxDriver.java
 *
 *  Copyright (c) 1995-2015, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 04/Jun/2015
 *
 *  $Id: XML11StaxDriver.java 18753 2015-06-04 13:20:25Z ian_roberts $
 */
package gate.util.xml;

import gate.corpora.ObjectWrapper;

import javax.xml.stream.XMLOutputFactory;

import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Simple extension of the XStream StaxDriver to generate XML 1.1 rather
 * than 1.0. This is used by {@link ObjectWrapper} in case the objects
 * being wrapped include characters that are not legal in XML 1.0.
 */
public class XML11StaxDriver extends StaxDriver {

  @Override
  protected XMLOutputFactory createOutputFactory() {
    return new XML11OutputFactory(super.createOutputFactory());
  }

}
