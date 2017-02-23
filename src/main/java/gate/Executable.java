/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 21 Sep 2001
 *
 *  $Id: Executable.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate;

import gate.creole.ExecutionException;

/**
 * Describes entities that can be executed such as {@link ProcessingResource}s
 * or {@link Controller}s.
 */
public interface Executable {

  /**
   * Starts the execution of this executable
   */
  public void execute() throws ExecutionException;

  /**
   * Notifies this executable that it should stop its execution as soon as
   * possible.
   */
  public void interrupt();

  /**
   * Returns true if this executable has been interrupted via the
   * {@link #interrupt()} method since the last time its {@link #execute()} method
   * was called
   */
  public boolean isInterrupted();
}