/*
 *  CreoleParameter.java
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
 *  $Id: CreoleParameter.java 16723 2013-07-05 10:37:59Z domrout $
 */

package gate.creole.metadata;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * <p>
 * Annotation used to define a parameter to a CREOLE resource. This annotation
 * should be used in one of two ways:
 * 
 * <ol>
 * <li>To annotate the <code>set</code> method corresponding to the parameter.
 * The parameter's name is inferred from the method name, and its type is
 * inferred from the type of the method's argument.</li>
 * <li>To annotate the field for the parameter itself. The corresponding get and
 * set methods must exist for the field</li>
 * </ol>
 * 
 * When annotating a method whose argument type is a subtype of
 * {@link Collection}, GATE also attempts to infer the collection element type
 * from any generic type arguments given. If the collection type is a raw type
 * then you will need to supply the collectionElementType explicitly in the
 * annotation.
 * </p>
 * 
 * <p>
 * Parameters can be marked as optional or runtime parameters using the
 * appropriate additional annotations.
 * </p>
 * 
 * @see Optional
 * @see RunTime
 */
@Documented
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreoleParameter {
  /**
   * Dummy type used to signify that no value has been supplied for {@link #collectionElementType()}.
   */
  public static interface NoElementType {}
  
  /**
   * Special value used to signify the absence of a default value for a
   * parameter.
   */
  public static final String NO_DEFAULT_VALUE = "____NO_DEFAULT____";
  
  /**
   * The default priority value assumed if no explicit priority
   * is set.
   */
  public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
  
  /**
   * The item class name for parameters whose type is a Collection, Set
   * or List. When annotating a field or get method with a parameterised type
   * (e.g. <code>List&lt;String&gt;</code>), the correct value can often be
   * inferred automatically, but if a value is specified here it takes
   * precedence.
   */
  Class<?> collectionElementType() default NoElementType.class;

  /**
   * The default value for the parameter, expressed as a string. For
   * non-string parameters, follow the rules given in the <a
   * href="http://gate.ac.uk/userguide/sec:creole-model:config:xml">user
   * guide</a>. If not specified, the default is <code>null</code>.
   */
  String defaultValue() default NO_DEFAULT_VALUE;

  /**
   * A descriptive comment about this parameter.
   */
  String comment() default "";

  /**
   * Semicolon-separated list of file suffixes accepted by default for
   * this parameter. For parameters of type {@link java.net.URL}, the
   * GUI provides a button to locate the desired file in a Java file
   * chooser. The suffixes list defines the default file name filter
   * used by the file chooser.
   */
  String suffixes() default "";

  /**
   * If this parameter is part of a disjunction, set an ID here. All
   * parameters sharing the same disjunction ID will be grouped together
   * in a single disjunction. If not specified, the parameter will not
   * be considered as part of a disjunction.
   */
  String disjunction() default "";
  
  /**
   * If this parameter is part of a disjunction, the order in which
   * the disjunctive parameters are listed is determined by their
   * priority values.  Parameters with smaller priority values are
   * considered "more important" than those with larger values (i.e.
   * 1 is higher priority than 10).  When creating a new resource
   * instance in GATE Developer, the parameters dialog offers the
   * parameters in each disjunction in priority order, so you should
   * assign priorities to your parameters such that the most important
   * parameter in each disjunction is the most frequent use case.  For
   * example, in a JAPE transducer the grammarURL parameter (for
   * loading a .jape file) is considered more important than the
   * binaryGrammarURL (for loading a serialized transducer).
   */
  int priority() default Integer.MAX_VALUE;
}
