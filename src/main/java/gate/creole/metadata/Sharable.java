/*
 *  Optional.java
 *
 *  Copyright (c) 2011, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 29/Jun/2011
 *
 *  $Id: Sharable.java 14240 2011-08-12 09:46:14Z ian_roberts $
 */
package gate.creole.metadata;

import gate.Factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>Marker interface used to mark the setter methods of JavaBean
 * properties that are <i>sharable</i>.  When a resource is
 * duplicated using {@link Factory#duplicate(gate.Resource)}
 * the values of any sharable properties from the original
 * resource will be copied by reference into the duplicate.
 * In particular, and sharable properties that are themselves
 * resources will <i>not</i> be recursively duplicated, even
 * if the properties are also marked as {@link CreoleParameter}s.</p>
 * 
 * <p>The Sharable marker is typically used for cases where a
 * resource creates at initialization time a data structure which
 * it subsequently accesses in a read-only fashion. Duplicates
 * of this resource can share a reference to the original's copy
 * of this data structure rather than having to build their
 * own identical copy.</p>
 * 
 * <p>The default {@link Factory#duplicate(gate.Resource)}
 * implementation sets sharable properties at the same time as
 * init parameters (i.e. after the constructor call but before
 * init() is called).  So resources that make use of sharable
 * properties need to check in their init() methods whether
 * the sharable properties have been set, and only do the
 * necessary initialization logic if the property values are
 * <code>null</code>.</p>
 */
@Documented
@Target( {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sharable {
  /**
   * Defaults to true but can be set to false to explicitly mark a property as
   * not sharable when a superclass has marked it as sharable.
   */
  boolean value() default true;
}
