/*
 *  Copyright (c) 1995-2020, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 */
package gate;

import gate.corpora.MimeType;
import java.net.URL;

/**
 * DocumentFormat which implements can choose to read from URL itself.
 * 
 * This interface indicates that the document format is based on a binary
 * representation and therefore the initial step of reading the document 
 * content from a URL into the String representation of DocumentContent 
 * can possibly be skipped. If a DocumentFormat subclass implements this
 * interface, the code normally responsible for reading the document content
 * when the Document instance is created will call the method 
 * shouldReadFromUrl(mimetype). The method should return false if 
 * nothing should be read and the DocumentFormat implementation will instead
 * read from the sourceURL. 
 * 
 * If a document is created from a String (i.e. sourceUrl is null),  
 * the document format will decide what to do and this interface is 
 * not relevant.
 * 
 */
public interface BinaryDocumentFormat {

  /**
   * Tell if the Document constructor should read for that mime type.
   * 
   * @param mimeType the mime type used when reading the document.
   * @param sourceUrl the source URL that was specified for the document.
   */
  public boolean shouldReadFromUrl(MimeType mimeType, URL sourceUrl);

}