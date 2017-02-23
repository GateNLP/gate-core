/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts 23/03/2010
 *
 *  $Id: CustomDuplication.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package gate.creole;

import gate.Resource;
import gate.Factory;
import gate.Factory.DuplicationContext;

/**
 * Interface which should be implemented by any Resource type which cannot be
 * duplicated in the standard way (see {@link Factory#duplicate(Resource)
 * Factory.duplicate}). If a Resource class requires custom duplication logic it
 * should implement this interface and provide a method to create a new resource
 * instance that has the same behaviour as <code>this</code>.
 * 
 * @author ian
 */
public interface CustomDuplication {

  /**
   * <p>
   * Create a <i>duplicate</i> of this resource. The object returned
   * need not be of the same concrete class as <code>this</code>, but
   * should behave the same and implement the same set of GATE core
   * interfaces, i.e. if <code>this</code> implements
   * {@link gate.ProcessingResource} then the duplicate should also
   * implement {@link gate.ProcessingResource}, if <code>this</code>
   * implements {@link gate.LanguageAnalyser} then the duplicate should
   * also implement {@link gate.LanguageAnalyser}, etc.
   * </p>
   * <p>
   * Typical uses for resource duplication are multi-threaded
   * applications that require a number of identical resources for
   * concurrent use in different threads. Therefore it is important that
   * duplicates created by this method should be safe for concurrent use
   * in multiple threads - in some cases it may be appropriate for the
   * duplicate to share some state with the original object, but this
   * must be handled in a thread-safe manner.
   * </p>
   * <p>
   * Implementors of this interface should <i>not</i> use covariant
   * return types, as to do so may limit the flexibility of subclasses
   * to implement duplication in the most efficient manner.
   * </p>
   * <p>
   * <b>NOTE</b> this method cannot be called directly, use
   * {@link Factory#duplicate(Resource)} instead.
   * </p>
   * 
   * @param ctx the current {@link DuplicationContext duplication context}.
   *          If an implementation of this method needs to duplicate any
   *          other resources as part of the custom duplication process
   *          it should pass this context back to the two-argument form of
   *          {@link Factory#duplicate(Resource, DuplicationContext) Factory.duplicate}
   *          rather than using the single-argument form.
   * @return an independent copy of this resource.
   * @throws ResourceInstantiationException
   */
  public Resource duplicate(DuplicationContext ctx)
          throws ResourceInstantiationException;
}
