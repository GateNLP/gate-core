/*
 *  SimpleFeatureMap.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, Jul/23/2004
 *
 *  $Id: SimpleFeatureMap.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate;
import java.util.Map;

/** An attribute-value matrix. Represents the content of an annotation, the
  * meta-data on a resource, and anything else we feel like.
  *
  * The event code is needed so a persistent annotation can fire updated events
  * when its features are updated
  */
public interface SimpleFeatureMap extends Map<Object, Object>
{
} // interface SimpleFeatureMap
