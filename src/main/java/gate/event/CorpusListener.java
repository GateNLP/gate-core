/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 13/07/2001
 *
 *  $Id: CorpusListener.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.event;

import java.util.EventListener;

/**
 * A listener for events fired by {@link gate.Corpus}
 */
public interface CorpusListener extends EventListener {

  /**
   * Called when a document has been added
   */
  public void documentAdded(CorpusEvent e);

  /**
   * Called when a document has been removed
   */
  public void documentRemoved(CorpusEvent e);
}