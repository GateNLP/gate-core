/*
 *  ObjectWrapper.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 8 Mar 2012
 *
 *  $Id: ObjectWrapper.java 19658 2016-10-10 06:46:13Z markagreenwood $
 */
package gate.corpora;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

import gate.Gate;
import gate.util.xml.XML11StaxDriver;

/**
 * Class used to wrap arbitrary values prior to saving as GATE XML. The 
 * {@link #toString()} method uses {@link XStream} to serialise the 
 * ObjectWrapper as XML; while the {@link #ObjectWrapper(String)} constructor
 * performs the reverse, parsing the previously saved XML and re-creating the 
 * wrapped value. 
 */
public class ObjectWrapper {
  
  /**
   * The value being wrapped. 
   */
  protected Object value;
  
  private static final XStream xstream;

  static {
	  /**
	   * Use XML 1.1 in case the XML representation includes characters
	   * that 1.0 forbids (typically control characters).
	   */
	  xstream = new XStream(new XML11StaxDriver());
	  Gate.configureXStreamSecurity(xstream);
  }

  protected static final Logger log = LoggerFactory.getLogger(ObjectWrapper.class);

  /**
   * Wraps an arbitrary value, which must be serialisable by XStream.
   * @param value any of any class that can be XML-serialised by XStream.
   */
  public ObjectWrapper(Object value) {
    this.value = value;
  }

  /**
   * De-serialises an {@link ObjectWrapper} instance from a string previously 
   * obtained by calling {@link #toString()}.
   * @param xmlSerialisation the XML string representing the saved 
   * {@link ObjectWrapper} instance.
   */
  public ObjectWrapper(String xmlSerialisation) {
    Object other = xstream.fromXML(xmlSerialisation);
    if(other instanceof ObjectWrapper) {
      this.value = ((ObjectWrapper)other).value;
    } else {
      log.error("Value de-serialised from XML is of type \"" + 
          other.getClass().getName() + "\", instead of expected \"" + 
          ObjectWrapper.class.getName() + ". Value was lost.");
      value = null;
    }
  }

  /**
   * Produces an XML serialisation of this {@link ObjectWrapper} instance, that
   * can be deserialised by using the {@link #ObjectWrapper(String)} constructor.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringWriter out  = new StringWriter();
    xstream.toXML(this, out);
    return out.toString();
  }

  /**
   * Gets the wrapped value.
   * 
   * @return the value
   */
  public Object getValue() {
    return value;
  }
}
