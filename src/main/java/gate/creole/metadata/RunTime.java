/*
 *  RunTime.java
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
 *  $Id: RunTime.java 16723 2013-07-05 10:37:59Z domrout $
 */

package gate.creole.metadata;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation used in conjunction with {@link CreoleParameter} to mark
 * parameters that are runtime parameters as opposed to init-time ones.  This
 * annotation is named RunTime (camel-case) so as not to conflict with the
 * {@link java.lang.Runtime} class in java.lang.
 *
 * <pre>
 * &#064;Optional
 * &#064;RunTime
 * &#064;CreoleParameter
 * public void setAnnotationTypes(List&lt;String&gt; types) { ... }
 * </pre>
 *
 * While usually used to mark parameters as runtime parameters, this annotation
 * also supports an optional boolean flag, so it can be used as
 * <code>&#064;RunTime(false)</code> to mark init-time parameters.  This is not
 * generally necessary, as parameters are init-time by default, however if a
 * given parameter has been annotated as <code>&#064;RunTime</code> in a superclass
 * this will be inherited.  If you want to change the parameter to be init-time
 * in a subclass then you must use <code>&#064;RunTime(false)</code>.
 */
@Documented
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RunTime {
  /**
   * Defaults to true but can be set to false to explicitly mark a parameter as
   * init-time rather than runtime.
   */
  boolean value() default true;
}

