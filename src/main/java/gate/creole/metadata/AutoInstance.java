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
 *  $Id: AutoInstance.java 9845 2008-08-25 22:23:24Z ian_roberts $
 */

package gate.creole.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define an instance of a resource that is created
 * automatically when the plugin is loaded. This annotation is used as a
 * parameter in {@link CreoleResource#autoinstances()}.
 */
@Target( {})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInstance {
  /**
   * Parameter values for this auto-instance. If not specified, the
   * default values are used.
   */
  AutoInstanceParam[] parameters() default {};

  /**
   * Should this auto-instance be hidden in the GUI (true) or should it
   * display just like a manually-created instance (false)?
   */
  boolean hidden() default false;
}
