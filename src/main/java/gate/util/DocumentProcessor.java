/*
 *  DocumentProcessor.java
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 03/Sep/2009
 *
 *  $Id: DocumentProcessor.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util;

import gate.Document;
import gate.util.GateException;

/**
 * Very simple interface for a component that processes GATE documents.
 * Typical implementations of this interface would contain a Controller but the
 * interface is deliberately generic.
 */
public interface DocumentProcessor {
  /**
   * Process the given GATE document.
   */
  public void processDocument(Document doc) throws GateException;
}
