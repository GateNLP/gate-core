/*
 *  CreoleResource.java
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
 *  $Id: CreoleResource.java 12430 2010-04-04 00:00:40Z ian_roberts $
 */

package gate.creole.metadata;

import gate.Factory;
import gate.Resource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a CREOLE resource type. This annotation should only be used
 * on classes or interfaces that implement the {@link Resource} interface
 * (directly or indirectly).
 */
@Documented
@Target( {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreoleResource {
  /**
   * The name of the resource, as it will appear in the GATE GUI. If
   * unspecified, defaults to the bare name of the resource class (without its
   * package).
   */
  String name() default "";

  /**
   * Is the resource private? If true, this resource type will not
   * appear in the menus of the GATE GUI, though it is still possible to
   * create it in code using {@link Factory#createResource}.
   */
  boolean isPrivate() default false;

  /**
   * A descriptive comment about this resource, which appears in the
   * tooltip in the GUI.
   */
  String comment() default "";

  /**
   * A  help URL about this resource, which is used to
   * display the help page in the GATE help browser.
   */
  String helpURL() default "";
  
  /**
   * Defines any instances of this resource that should be created
   * automatically when the plugin is loaded.
   */
  AutoInstance[] autoinstances() default {};

  /**
   * The interface implemented by this resource. For example, a document
   * implementation should specify "gate.Document" here.
   */
  String interfaceName() default "";

  /**
   * The path (in the {@link Class#getResource} sense) to the icon used
   * to represent this resource in the GUI. A path starting with a
   * forward slash is treated as an absolute path, a relative path
   * (without the leading slash) is interpreted relative to
   * gate/resources/img.
   */
  String icon() default "";

  /**
   * The name of the resource class that this resource is responsible
   * for displaying in the GUI. Only relevant for visual resources.
   */
  String resourceDisplayed() default "";

  /**
   * The annotation type that this resource displays. Only relevant for
   * annotation editor resources.
   */
  String annotationTypeDisplayed() default "";

  /**
   * The GUI type of this resource. Only relevant for visual resources.
   */
  GuiType guiType() default GuiType.NONE;

  /**
   * Is this resource the "main" viewer for its target resource type?
   * Only relevant for visual resources.
   */
  boolean mainViewer() default false;
  
  /**
   * Is this resource a 'tool' (i.e. should its published actions be
   * added to the Tools menu)?
   */
  boolean tool() default false;
}
