/*
 *  AutoInstance.java
 *
 *  Copyright (c) 2008, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 27/Jul/2008
 *
 *  $Id: AutoInstanceParam.java 9845 2008-08-25 22:23:24Z ian_roberts $
 */

package gate.creole.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Holder for a single name/value pair for a parameter to an
 * auto-instance. Used only in {@link AutoInstance#parameters()}.
 */
@Target( {})
@Retention(RetentionPolicy.CLASS)
public @interface AutoInstanceParam {
  /**
   * The name of the parameter.
   */
  String name();

  /**
   * The parameter value as a string, following normal GATE conversion
   * rules for non-string parameter types.
   */
  String value();
}
