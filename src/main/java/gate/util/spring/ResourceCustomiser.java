/*
 *  ResourceCustomiser.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 22/Jan/2008
 *
 *  $Id: ResourceCustomiser.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.util.spring;

import gate.Resource;

/**
 * Simple interface for objects that are used to customise GATE
 * resources.
 */
public interface ResourceCustomiser {
  public void customiseResource(Resource res) throws Exception;
}
