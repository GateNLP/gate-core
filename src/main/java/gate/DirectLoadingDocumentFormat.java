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

/**
 * DocumentFormat which will read directly from an URL by itself.
 * 
 * If a DocumentFormat subclass implements this
 * interface, the code normally responsible for reading the document content
 * when the Document instance is created will will skip reading from the URL
 * and delegate the reading to the implementing document format. 
 * 
 * If a document is created from a String (i.e. sourceUrl is null),  
 * the document format will decide what to do and this interface is 
 * not relevant.
 * 
 */
public interface DirectLoadingDocumentFormat {

}