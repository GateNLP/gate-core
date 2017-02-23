/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, June 12th 2002
 *
 *  $Id: TextualDocument.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate;

/**
 * Top interface for all types of textual documents (transient or persistent).
 * Extends the {@link Document} interface with the encoding property.
 */
public interface TextualDocument extends Document {
  /**
   * Gets the encoding used for this document. This encoding has been used to
   * read the content for the document and will be used for dumping this
   * document to other textual formats such as XML.
   * @return a String value.
   */
  public String getEncoding();
}