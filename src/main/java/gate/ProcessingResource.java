/*
 *  ProcessingResource.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 11/Feb/2000
 *
 *  $Id: ProcessingResource.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate;

import gate.creole.ResourceInstantiationException;

/** Models all sorts of processing resources.
  * Because <CODE>run()</CODE> doesn't throw exceptions, we
  * have a <CODE>check()</CODE> that will re-throw any exception
  * that was caught when <CODE>run()</CODE> was invoked.
  */
public interface ProcessingResource extends Resource, Executable
{

  /**
   * Reinitialises the processing resource. After calling this method the
   * resource should be in the state it is after calling init.
   * If the resource depends on external resources (such as rules files) then
   * the resource will re-read those resources. If the data used to create
   * the resource has changed since the resource has been created then the
   * resource will change too after calling reInit().
   */
  public void reInit() throws ResourceInstantiationException;
} // interface ProcessingResource
