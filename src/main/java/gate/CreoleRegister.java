/*
 *  CreoleRegister.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 31/Aug/2000
 *
 *  $Id: CreoleRegister.java 19985 2017-01-25 14:20:13Z markagreenwood $
 */

package gate;

import gate.creole.Plugin;
import gate.creole.ResourceData;
import gate.creole.metadata.CreoleResource;
import gate.event.CreoleListener;
import gate.event.PluginListener;
import gate.util.GateException;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** The CREOLE register records the set of resources that are currently
  * known to the system. Each member of the register is a
  * <A HREF=creole/ResourceData.html>ResourceData</A> object, indexed by
  * the class name of the resource.
  * <P>
  * The register is accessible from the static method
  * <A HREF=Gate.html#getCreoleRegister()>gate.Gate.getCreoleRegister
  * </A>;
  * there is only one per application of the GATE framework.
  * <P>
  * Clients use the register by adding URLs (using the
  * <A HREF=#addDirectory(java.net.URL)>addDirectory</A> method)
  * pointing to CREOLE directories. A <B>CREOLE directory</B> is a URL at
  * which resides a file called <CODE>creole.xml</CODE> describing
  * the resources present, and one or more Jar files implementing
  * those resources. E.g., the CREOLE resources at
  * <A HREF=http://gate.ac.uk/>gate.ac.uk</A> are registered by Gate.init()
  * by registering the directory URL
  * <A HREF=http://gate.ac.uk/creole/>http://gate.ac.uk/creole/</A>, under
  * which lives a file called creole.xml.
  * <P>
  * To register resources clients use the <CODE>registerDirectories</CODE>
  * methods. When resources have been registered they can be accessed via
  * their <CODE>ResourceData</CODE> objects. So a typical use of the register
  * is to: add the set of URLs containing CREOLE directories; register
  * all resources found at those URLs; browse the set of registered
  * resources.
  * <P>
  * In all cases, where a resource or a directory is added which is
  * already present in the register, the new silently overwrites the old.
  *
  * The CreoleRegister notifies all registered listeners of all
  * {@link gate.event.CreoleEvent}s that occur in the system regardless of
  * whether they were initially fired by the {@link Factory}, the
  * {@link DataStoreRegister} or the {@link CreoleRegister} itself.
  *
  * @see gate.Gate
  * @see gate.creole.ResourceData
  */
public interface CreoleRegister extends Map<String, ResourceData>, Serializable, CreoleListener
{
  /** Get the list of CREOLE directory URLs. */
  @Deprecated
  public Set<URL> getDirectories();
  
  public Set<Plugin> getPlugins();

  /**
   * Given the class object for a class with {@link CreoleResource}
   * annotations, register that class as is if it was found in a scanned jar
   * file with no additional creole.xml information.
   *
   * This API is intended for use in embedded GATE applications where
   * the 'application' is created via the API. Components registered with this
   * API won't work in saved applications, but they can be added
   * to saved applications at runtime.
   *
   * @param clazz Class object for class with CreoleResource annotations.
   */
  @Deprecated
  public void registerComponent(Class<? extends Resource> clazz) throws GateException;

  /** Register a single CREOLE directory. The <CODE>creole.xml</CODE>
    * file at the URL is parsed, and <CODE>CreoleData</CODE> objects added
    * to the register. If the directory URL has not yet been added it
    * is now added. If any other plugins that needs to be loaded for this
    * plugin to load (specified by <CODE>REQUIRES</Code> elements in
    * <code>creole.xml</code>) will also be loaded.
    */
  @Deprecated
  public void registerDirectories(URL directoryUrl) throws GateException;
  
  public void registerPlugin(Plugin plugin) throws GateException;
  
  public void registerPlugin(Plugin plugin, boolean loadDependencies) throws GateException;
  
  /**
   * Register a single CREOLE directory. The <CODE>creole.xml</CODE> file at the
   * URL is parsed, and <CODE>CreoleData</CODE> objects added to the register.
   * If the directory URL has not yet been added it is now added. If any other
   * plugins that nees top be loaded for this plugin to load (specified by
   * <CODE>REQUIRES</Code> elements in <code>creole.xml</code>) will only be
   * loaded if the <code>loadDependencies</code> param is true. It is useful to
   * be able to ignore dependencies when loading an xgapp where we know they
   * have already been resolved.
   */
  @Deprecated
  public void registerDirectories(URL directoryUrl, boolean loadDependencies)
      throws GateException;

  /**
   * Removes a CREOLE directory from the set of loaded directories.
   * @param directory
   */
  @Deprecated
  public void removeDirectory(URL directory);
  
  public void unregisterPlugin(Plugin plugin);

  /** Register resources that are built in to the GATE distribution.
    * These resources are described by the <TT>creole.xml</TT> file in
    * <TT>resources/creole</TT>.
    */
  public void registerBuiltins() throws GateException;

  /** Get the list of types of LR in the register. */
  public Set<String> getLrTypes();

  /** Get the list of types of PR in the register. */
  public Set<String> getPrTypes();

  /** Get the list of types of VR in the register. */
  public Set<String> getVrTypes();

  /** Get the list of types of VR in the register. */
  public Set<String> getControllerTypes();
  
  /** Get the list of packaged application types in the register. */
  public Set<String> getApplicationTypes();

  /** Get the list of types of tool in the register. */
  public Set<String> getToolTypes();

  /** Get a list of all instantiations of LR in the register. */
  public List<LanguageResource> getLrInstances();

  /** Get a list of all instantiations of PR in the register. */
  public List<ProcessingResource> getPrInstances();

  /** Get a list of all instantiations of VR in the register. */
  public List<VisualResource> getVrInstances();

  /**
   * Get a list of all the known Language Resource instances in the register
   * whose class is <code>resourceTypeName</code>.  This is only direct
   * instances of the specified class, not its sub-classes, so if
   * <code>resourceTypeName</code> refers to an interface or abstract class an
   * empty list will be returned.
   */
  public List<LanguageResource> getLrInstances(String resourceTypeName);

  /**
   * Get a list of all the known Processing Resource instances in the register
   * whose class is <code>resourceTypeName</code>.  This is only direct
   * instances of the specified class, not its sub-classes, so if
   * <code>resourceTypeName</code> refers to an interface or abstract class an
   * empty list will be returned.
   */
  public List<ProcessingResource> getPrInstances(String resourceTypeName);

  /**
   * Get a list of all the known Visual Resource instances in the register
   * whose class is <code>resourceTypeName</code>.  This is only direct
   * instances of the specified class, not its sub-classes, so if
   * <code>resourceTypeName</code> refers to an interface or abstract class an
   * empty list will be returned.
   */
  public List<VisualResource> getVrInstances(String resourceTypeName);

  /** Get a list of all non-private instantiations of LR in the register. */
  public List<LanguageResource> getPublicLrInstances();

  /** Get a list of all non-private instantiations of PR in the register. */
  public List<ProcessingResource> getPublicPrInstances();

  /** Get a list of all non-private instantiations of VR in the register. */
  public List<VisualResource> getPublicVrInstances();

  /** Get a list of all non-private types of LR in the register. */
  public List<String> getPublicLrTypes();

  /** Get a list of all non-private types of PR in the register. */
  public List<String> getPublicPrTypes();

  /** Get a list of all non-private types of VR in the register. */
  public List<String> getPublicVrTypes();

  /** Get a list of all non-private types of Controller in the register. */
  public List<String> getPublicControllerTypes();

  /**
   * Get a list of all the known Resource instances in the register that are of
   * the specified (class or interface) type or one of its sub-types.
   * Specifically, all known Resources <code>r</code> such that
   * <pre>
   * Gate.getClassLoader().loadClass(type).isInstance(r)
   * </pre>
   *
   * Instances that are hidden (<code>Gate.getHiddenAttribute(r.getFeatures())
   * == true</code>) are not included in the returned list.
   */
  public List<Resource> getAllInstances(String type) throws GateException;

  /**
   * Returns a list of strings representing class names for large VRs valid
   * for a given type of language/processing resource.
   * The default VR will be the first in the returned list.
   */
  public List<String> getLargeVRsForResource(String resourceClassName);

  /**
   * Returns a list of strings representing class names for small VRs valid
   * for a given type of language/processing resource
   * The default VR will be the first in the returned list.
   */
  public List<String> getSmallVRsForResource(String resourceClassName);

  /**
    * Returns a list of strings representing class names for annotation VRs
    * that are able to display/edit all types of annotations.
    * The default VR will be the first in the returned list.
    */
   public List<String> getAnnotationVRs();

  /**
    * Returns a list of strings representing class names for annotation VRs
    * that are able to display/edit a given annotation type
    * The default VR will be the first in the returned list.
    */
   public List<String> getAnnotationVRs(String annotationType);


  /**
    * Returns a list of strings representing annotations types for which
    * there are custom viewers/editor registered.
    */
   public List<String> getVREnabledAnnotationTypes();

   /**
    * Renames an existing resource.
    */
   public void setResourceName(Resource res, String newName);

  /**
   * Registers a {@link gate.event.CreoleListener}with this CreoleRegister.
   * The register will fire events every time a resource is added to or removed
   * from the system and when a datastore is created, opened or closed.
   */
  public void addCreoleListener(CreoleListener l);

  /**
   * Removes a {@link gate.event.CreoleListener} previously registered with this
   * CreoleRegister.
   * @see #addCreoleListener(CreoleListener)
   */
  public void removeCreoleListener(CreoleListener l);
  
  public void addPluginListener(PluginListener l);
  
  public void removePluginListener(PluginListener l);

} // interface CreoleRegister
