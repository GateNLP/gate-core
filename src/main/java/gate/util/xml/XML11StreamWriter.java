/*
 *  XML11StreamWriter.java
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
 *  $Id: XML11StreamWriter.java 18753 2015-06-04 13:20:25Z ian_roberts $
 */
package gate.util.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

class XML11StreamWriter implements XMLStreamWriter {

  protected XMLStreamWriter delegate;

  public XML11StreamWriter(XMLStreamWriter delegate) {
    this.delegate = delegate;
  }

  /**
   * Overrides the default behaviour to explicitly select XML version
   * 1.1 if no version is supplied by the caller.
   */
  public void writeStartDocument() throws XMLStreamException {
    writeStartDocument("1.1");
  }

  // all remaining methods simply pass through to the delegate

  public void writeStartElement(String localName) throws XMLStreamException {
    delegate.writeStartElement(localName);
  }

  public void writeStartElement(String namespaceURI, String localName)
          throws XMLStreamException {
    delegate.writeStartElement(namespaceURI, localName);
  }

  public void writeStartElement(String prefix, String localName,
          String namespaceURI) throws XMLStreamException {
    delegate.writeStartElement(prefix, localName, namespaceURI);
  }

  public void writeEmptyElement(String namespaceURI, String localName)
          throws XMLStreamException {
    delegate.writeEmptyElement(namespaceURI, localName);
  }

  public void writeEmptyElement(String prefix, String localName,
          String namespaceURI) throws XMLStreamException {
    delegate.writeEmptyElement(prefix, localName, namespaceURI);
  }

  public void writeEmptyElement(String localName) throws XMLStreamException {
    delegate.writeEmptyElement(localName);
  }

  public void writeEndElement() throws XMLStreamException {
    delegate.writeEndElement();
  }

  public void writeEndDocument() throws XMLStreamException {
    delegate.writeEndDocument();
  }

  public void close() throws XMLStreamException {
    delegate.close();
  }

  public void flush() throws XMLStreamException {
    delegate.flush();
  }

  public void writeAttribute(String localName, String value)
          throws XMLStreamException {
    delegate.writeAttribute(localName, value);
  }

  public void writeAttribute(String prefix, String namespaceURI,
          String localName, String value) throws XMLStreamException {
    delegate.writeAttribute(prefix, namespaceURI, localName, value);
  }

  public void writeAttribute(String namespaceURI, String localName, String value)
          throws XMLStreamException {
    delegate.writeAttribute(namespaceURI, localName, value);
  }

  public void writeNamespace(String prefix, String namespaceURI)
          throws XMLStreamException {
    delegate.writeNamespace(prefix, namespaceURI);
  }

  public void writeDefaultNamespace(String namespaceURI)
          throws XMLStreamException {
    delegate.writeDefaultNamespace(namespaceURI);
  }

  public void writeComment(String data) throws XMLStreamException {
    delegate.writeComment(data);
  }

  public void writeProcessingInstruction(String target)
          throws XMLStreamException {
    delegate.writeProcessingInstruction(target);
  }

  public void writeProcessingInstruction(String target, String data)
          throws XMLStreamException {
    delegate.writeProcessingInstruction(target, data);
  }

  public void writeCData(String data) throws XMLStreamException {
    delegate.writeCData(data);
  }

  public void writeDTD(String dtd) throws XMLStreamException {
    delegate.writeDTD(dtd);
  }

  public void writeEntityRef(String name) throws XMLStreamException {
    delegate.writeEntityRef(name);
  }

  public void writeStartDocument(String version) throws XMLStreamException {
    delegate.writeStartDocument(version);
  }

  public void writeStartDocument(String encoding, String version)
          throws XMLStreamException {
    delegate.writeStartDocument(encoding, version);
  }

  public void writeCharacters(String text) throws XMLStreamException {
    delegate.writeCharacters(text);
  }

  public void writeCharacters(char[] text, int start, int len)
          throws XMLStreamException {
    delegate.writeCharacters(text, start, len);
  }

  public String getPrefix(String uri) throws XMLStreamException {
    return delegate.getPrefix(uri);
  }

  public void setPrefix(String prefix, String uri) throws XMLStreamException {
    delegate.setPrefix(prefix, uri);
  }

  public void setDefaultNamespace(String uri) throws XMLStreamException {
    delegate.setDefaultNamespace(uri);
  }

  public void setNamespaceContext(NamespaceContext context)
          throws XMLStreamException {
    delegate.setNamespaceContext(context);
  }

  public NamespaceContext getNamespaceContext() {
    return delegate.getNamespaceContext();
  }

  public Object getProperty(String name) throws IllegalArgumentException {
    return delegate.getProperty(name);
  }

}
