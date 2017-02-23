/*
 *  HiddenCreoleParameter.java
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
 *  $Id: HiddenCreoleParameter.java 16723 2013-07-05 10:37:59Z domrout $
 */

package gate.creole.metadata;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark parameters that should not be inherited from
 * superclasses.  By default, a {@link CreoleResource} inherits its parameter
 * definitions from its superclasses and interfaces.  However, in some cases
 * the superclass provides a parameter but the subclass does not want to expose
 * that parameter directly (for example, it may calculate the parameter value
 * some other way).  In this case, the subclass should override the
 * <code>set</code> method for that parameter and annotate the overridden
 * method with this annotation.  GATE will then ignore the inherited parameter.
 */
@Documented
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HiddenCreoleParameter {}
