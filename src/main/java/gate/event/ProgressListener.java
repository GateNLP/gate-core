/*
 *  ProgressListener.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 03/07/2000
 *
 *  $Id: ProgressListener.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.event;

import java.util.EventListener;

/** This interface describes objects that can register themselves as listeners
 * to ProcessProgressReporters.
 * They need to be able to handle progress change and process finished events.
 *
 */
public interface ProgressListener extends EventListener{

  /**
   * Called when the progress has changed
   *
   * @param i
   */
  public void progressChanged(int i);

  /**
   * Called when the process is finished.
   *
   */
  public void processFinished();

} // ProgressListener

