/**
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  $Id: ClosableIterator.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util;

import java.util.Iterator;

/**
 * An iterator that should be closed as soon as it is not used anymore.
 * The close() method is used to close the iterator at any time. It is
 * not an error to close() an iterator that already has been closed.
 * 
 * This iterator also auto-closes when hasNext() is called and no more
 * elements are available.
 *
 * @param <T> 
 */
public interface ClosableIterator<T> extends Iterator<T> {

  /**
   * Close the iteratori and free all resources. This method can be called
   * more than once, all calls after the first one have no effect.
   * After close() has been called, hasNext() is guaranteed to return false.
   * After close() it is an error the call next(). close will throw
   * a GateRuntimeException in that case.
   * This method is called automatically when hasNext() is called and no
   * more elements are available.
   */
  public void close();
  
}
