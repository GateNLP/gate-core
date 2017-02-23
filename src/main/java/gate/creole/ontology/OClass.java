/*
 *  OClass.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OClass.java 11598 2009-10-13 13:44:17Z johann_p $
 */
package gate.creole.ontology;

import gate.util.ClosableIterator;
import java.util.ArrayList;
import java.util.Set;

/**
 * Each OClass (Ontology Class) represents a concept/class in ontology.
 * It provides various methods (including and not limited) to iterate
 * through its super and sub classes in the taxonomy hierarchy.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 */
public interface OClass extends OResource, OConstants {
  /**
   * Adds a sub class to this class.
   * 
   * @param subClass the subClass to be added.
   */
  public void addSubClass(OClass subClass);

  /**
   * Removes a sub class.
   * 
   * @param subClass the sub class to be removed
   */
  public void removeSubClass(OClass subClass);

  /**
   * Gets the subclasses according to the desired closure.
   * 
   * @param closure either DIRECT_CLOSURE or TRASITIVE_CLOSURE
   * @return the set of subclasses
   */
  @Deprecated
  public Set<OClass> getSubClasses(byte closure);

  public Set<OClass> getSubClasses(Closure closure);

  public ClosableIterator<OClass> getSubClassesIterator(Closure closure);

  /**
   * Gets the super classes according to the desired closure.
   * 
   * @param closure either DIRECT_CLOSURE or TRASITIVE_CLOSURE
   * @return the set of super classes
   */
  @Deprecated
  public Set<OClass> getSuperClasses(byte closure);

  public Set<OClass> getSuperClasses(Closure closure);

  /**
   * Checks whether the class is a super class of the given class.
   * 
   * @param aClass
   * @param closure either OntologyConstants.DIRECT_CLOSURE or
   *          OntologyConstants.TRANSITIVE_CLOSURE
   * @return true, if the class is a super class of the given class,
   *         otherwise - false.
   */
  public boolean isSuperClassOf(OClass aClass, byte closure);

  public boolean isSuperClassOf(OClass aClass, OConstants.Closure closure);

  /**
   * Checks whether the class is a sub class of the given class.
   * 
   * @param aClass
   * @param closure either OntologyConstants.DIRECT_CLOSURE or
   *          OntologyConstants.TRANSITIVE_CLOSURE
   * @return true, if the class is a sub class of the given class,
   *         otherwise - false.
   */
  @Deprecated
  public boolean isSubClassOf(OClass aClass, byte closure);

  public boolean isSubClassOf(OClass aClass, OConstants.Closure closure);

  /**
   * Checks whether this class is a top.
   * 
   * @return true if this is a top class, otherwise - false.
   */
  public boolean isTopClass();

  /** Indicates that these classes are the equivalent */
  public void setEquivalentClassAs(OClass theClass);

  /**
   * Returns a set of all classes that are equivalent as this one. Null
   * if no such classes.
   */
  public Set<OClass> getEquivalentClasses();

  /**
   * Checks whether the class is equivalent as the given class.
   * 
   * @param aClass
   * @return true, if the class is equivalent as the aClass, otherwise -
   *         false.
   */
  public boolean isEquivalentClassAs(OClass aClass);

  /**
   * Gets the super classes, and returns them in an array list where on
   * each index there is a collection of the super classes at distance -
   * the index.
   */
  public ArrayList<Set<OClass>> getSuperClassesVSDistance();

  /**
   * Gets the sub classes, and returns them in an array list where on
   * each index there is a collection of the sub classes at distance -
   * the index.
   */
  public ArrayList<Set<OClass>> getSubClassesVsDistance();

}
