/*
 *  SgmlDocumentFormat.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 4/July/2000
 *
 *  $Id: SgmlDocumentFormat.java 19663 2016-10-10 08:44:57Z markagreenwood $
 */

package gate.corpora;

import java.io.IOException;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

import gate.Document;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.event.StatusListener;
import gate.sgml.Sgml2Xml;
import gate.util.DocumentFormatException;
import gate.xml.XmlDocumentHandler;

/** The format of Documents. Subclasses of DocumentFormat know about
  * particular MIME types and how to unpack the information in any
  * markup or formatting they contain into GATE annotations. Each MIME
  * type has its own subclass of DocumentFormat, e.g. XmlDocumentFormat,
  * RtfDocumentFormat, MpegDocumentFormat. These classes register themselves
  * with a static index residing here when they are constructed. Static
  * getDocumentFormat methods can then be used to get the appropriate
  * format class for a particular document.
  */
@CreoleResource(name = "GATE SGML Document Format", isPrivate = true,
    autoinstances = {@AutoInstance(hidden = true)})
public class SgmlDocumentFormat extends TextualDocumentFormat
{
  private static final long serialVersionUID = -3596255263987343560L;

  /** Default construction */
  public SgmlDocumentFormat() { super(); }

  /** Unpack the markup in the document. This converts markup from the
    * native format (e.g. SGML) into annotations in GATE format.
    * Uses the markupElementsMap to determine which elements to convert, and
    * what annotation type names to use.
    * The doc's content is first converted to a wel formed XML.
    * If this succeddes then the document is saved into a temp file and parsed
    * as an XML document.
    *
    * @param doc The gate document you want to parse.
    *
    */
  @Override
  public void unpackMarkup(Document doc) throws DocumentFormatException{
    if ( (doc == null) ||
         (doc.getSourceUrl() == null && doc.getContent() == null)){

      throw new DocumentFormatException(
               "GATE document is null or no content found. Nothing to parse!");
    }// End if
    // Create a status listener
    StatusListener statusListener = new StatusListener(){
            @Override
            public void statusChanged(String text){
              fireStatusChanged(text);
            }
    };
    XmlDocumentHandler xmlDocHandler = null;
    try {
      Sgml2Xml sgml2Xml = new Sgml2Xml(doc);

      fireStatusChanged("Performing SGML to XML...");

      // convert the SGML document
      String xmlUri = sgml2Xml.convert();

      fireStatusChanged("DONE !");

      //Out.println("Conversion done..." + xmlUri);
      //Out.println(sgml2Xml.convert());
      // Get a parser factory.
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      // Set up the factory to create the appropriate type of parser

      // Set up the factory to create the appropriate type of parser
      // non validating one
      saxParserFactory.setValidating(false);
      // non namesapace aware one
      saxParserFactory.setNamespaceAware(true);

      // Create a SAX parser
      SAXParser parser = saxParserFactory.newSAXParser();

      // use it

        // create a new Xml document handler
        xmlDocHandler = new XmlDocumentHandler(doc,
                                               this.markupElementsMap,
                                               this.element2StringMap);

        // register a status listener with it
        xmlDocHandler.addStatusListener(statusListener);

        parser.parse(xmlUri, xmlDocHandler);
        ((DocumentImpl) doc).setNextAnnotationId(
                                          xmlDocHandler.getCustomObjectsId());

    } catch (ParserConfigurationException e){
        throw
        new DocumentFormatException("XML parser configuration exception ", e);
    } catch (SAXException e){
        throw new DocumentFormatException(e);
    } catch (IOException e){
        throw new DocumentFormatException("I/O exception for " +
                                      doc.getSourceUrl().toString());
    }finally{
      if (xmlDocHandler != null)
        xmlDocHandler.removeStatusListener(statusListener);
    }// End try

  }// unpackMarkup

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException{
    // Register SGML mime type
    MimeType mime = new MimeType("text","sgml");
    // Register the class handler for this mime type
    mimeString2ClassHandlerMap.put(mime.getType()+ "/" + mime.getSubtype(),
                                                                          this);
    // Register the mime type with mine string
    mimeString2mimeTypeMap.put(mime.getType() + "/" + mime.getSubtype(), mime);
    // Register file sufixes for this mime type
    suffixes2mimeTypeMap.put("sgm",mime);
    suffixes2mimeTypeMap.put("sgml",mime);
    setMimeType(mime);
    return this;
  }// init

}//class SgmlDocumentFormat
