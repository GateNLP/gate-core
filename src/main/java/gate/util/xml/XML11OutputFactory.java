/*
 *  XML11OutputFactory.java
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
 *  $Id: XML11OutputFactory.java 18753 2015-06-04 13:20:25Z ian_roberts $
 */
package gate.util.xml;

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

/**
 * Simple delegating XMLOutputFactory that produces stream writers whose
 * <code>startDocument()</code> method delegates to
 * <code>startDocument("1.1")</code>, thus producing XML 1.1 instead of
 * 1.0 by default.
 */
public class XML11OutputFactory extends XMLOutputFactory {

  private XMLOutputFactory delegate;
  
  /**
   * Create an output factory that generates stream writers that
   * prefer XML 1.1 instead of 1.0 by default.  The underlying
   * writers are created from the given delegate output factory.
   */
  public XML11OutputFactory(XMLOutputFactory delegate) {
    this.delegate = delegate;
  }

  /**
   * Wrap the supplied writer in a wrapper that prefers XML 1.1 over 1.0
   */
  protected XMLStreamWriter wrap(XMLStreamWriter writer)
          throws XMLStreamException {
    return new XML11StreamWriter(writer);
  }

  public XMLStreamWriter createXMLStreamWriter(Writer stream)
          throws XMLStreamException {
    return wrap(delegate.createXMLStreamWriter(stream));
  }

  public XMLStreamWriter createXMLStreamWriter(OutputStream stream)
          throws XMLStreamException {
    return wrap(delegate.createXMLStreamWriter(stream));
  }

  public XMLStreamWriter createXMLStreamWriter(OutputStream stream,
          String encoding) throws XMLStreamException {
    return wrap(delegate.createXMLStreamWriter(stream, encoding));
  }

  public XMLStreamWriter createXMLStreamWriter(Result result)
          throws XMLStreamException {
    return wrap(delegate.createXMLStreamWriter(result));
  }

  public XMLEventWriter createXMLEventWriter(Result result)
          throws XMLStreamException {
    return delegate.createXMLEventWriter(result);
  }

  public XMLEventWriter createXMLEventWriter(OutputStream stream)
          throws XMLStreamException {
    return delegate.createXMLEventWriter(stream);
  }

  public XMLEventWriter createXMLEventWriter(OutputStream stream,
          String encoding) throws XMLStreamException {
    return delegate.createXMLEventWriter(stream, encoding);
  }

  public XMLEventWriter createXMLEventWriter(Writer stream)
          throws XMLStreamException {
    return delegate.createXMLEventWriter(stream);
  }

  public void setProperty(String name, Object value)
          throws IllegalArgumentException {
    delegate.setProperty(name, value);
  }

  public Object getProperty(String name) throws IllegalArgumentException {
    return delegate.getProperty(name);
  }

  public boolean isPropertySupported(String name) {
    return delegate.isPropertySupported(name);
  }
}
