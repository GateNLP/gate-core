/*
 *  EmailDocumentFormat.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 3/Aug/2000
 *
 *  $Id: EmailDocumentFormat.java 17638 2014-03-12 09:36:47Z markagreenwood $
 */

package gate.corpora;

import gate.Annotation;
import gate.AnnotationSet;
import gate.GateConstants;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.email.EmailDocumentHandler;
import gate.event.StatusListener;
import gate.util.DocumentFormatException;
import gate.util.InvalidOffsetException;

import java.io.IOException;
import java.util.Iterator;

/** The format of Documents. Subclasses of DocumentFormat know about
  * particular MIME types and how to unpack the information in any
  * markup or formatting they contain into GATE annotations. Each MIME
  * type has its own subclass of DocumentFormat, e.g. XmlDocumentFormat,
  * RtfDocumentFormat, MpegDocumentFormat. These classes register themselves
  * with a static index residing here when they are constructed. Static
  * getDocumentFormat methods can then be used to get the appropriate
  * format class for a particular document.
  */
@CreoleResource(name = "GATE EMAIL Document Format", isPrivate = true,
    autoinstances = {@AutoInstance(hidden = true)})
public class EmailDocumentFormat extends TextualDocumentFormat
{
  private static final long serialVersionUID = 5738598679165395119L;

  /** Default construction */
  public EmailDocumentFormat() { super();}

  /** Unpack the markup in the document. This converts markup from the
    * native format (e.g. EMAIL) into annotations in GATE format.
    * Uses the markupElementsMap to determine which elements to convert, and
    * what annotation type names to use.
    * It always tryes to parse te doc's content. It doesn't matter if the
    * sourceUrl is null or not.
    *
    * @param doc The gate document you want to parse.
    *
    */

  @Override
  public void unpackMarkup(gate.Document doc) throws DocumentFormatException{
    if ( (doc == null) ||
         (doc.getSourceUrl() == null && doc.getContent() == null)){

      throw new DocumentFormatException(
               "GATE document is null or no content found. Nothing to parse!");
    }// End if

    setNewLineProperty(doc);

    // create an EmailDocumentHandler
    EmailDocumentHandler emailDocHandler = null;
    emailDocHandler = new  gate.email.EmailDocumentHandler(
                                                       doc,
                                                       this.markupElementsMap,
                                                       this.element2StringMap);
    StatusListener statusListener = new StatusListener(){
        @Override
        public void statusChanged(String text) {
          // this is implemented in DocumentFormat.java and inherited here
          fireStatusChanged(text);
        }//statusChanged(String text)
    };
    // Register a status listener with it
    emailDocHandler.addStatusListener(statusListener);
    try{
      // Call the method that creates annotations on the gate document
      emailDocHandler.annotateMessages();
      // Process the body annotations and search for paragraphs
      AnnotationSet bodyAnnotations = doc.getAnnotations(
                    GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME).get("body");
      if (bodyAnnotations != null && !bodyAnnotations.isEmpty()){
        Iterator<Annotation> iter = bodyAnnotations.iterator();
        while(iter.hasNext()){
          Annotation a = iter.next();
          annotateParagraphs(doc,a.getStartNode().getOffset().intValue(),
                                 a.getEndNode().getOffset().intValue(),
                                 GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
        }// End while
      }// End if
    } catch (IOException e){
      throw new DocumentFormatException("Couldn't create a buffered reader ",e);
    } catch (InvalidOffsetException e){
      throw new DocumentFormatException(e);
    }finally{
      emailDocHandler.removeStatusListener(statusListener);
    }// End try
  }//unpackMarkup(doc)

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException{
    // Register EMAIL mime type
    MimeType mime = new MimeType("text","email");
    // Register the class handler for this mime type
    mimeString2ClassHandlerMap.put(mime.getType()+ "/" + mime.getSubtype(),
                                                                          this);
    // Register the mime type with mine string
    mimeString2mimeTypeMap.put(mime.getType() + "/" + mime.getSubtype(), mime);
    // Register file sufixes for this mime type
    suffixes2mimeTypeMap.put("eml",mime);
    suffixes2mimeTypeMap.put("email",mime);
    suffixes2mimeTypeMap.put("mail",mime);
    // Register magic numbers for this mime type
    magic2mimeTypeMap.put("Subject:",mime);
    // Set the mimeType for this language resource
    setMimeType(mime);
    return this;
  }// init()
}// class EmailDocumentFormat

