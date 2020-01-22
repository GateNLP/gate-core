/*
 * CreoleRegisterImpl.java
 *
 * Copyright (c) 1995-2013, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Hamish Cunningham, 1/Sept/2000
 *
 * $Id: CreoleRegisterImpl.java 20134 2017-02-16 11:31:56Z markagreenwood $
 */

package gate.creole;

import gate.Controller;
import gate.CreoleRegister;
import gate.Factory;
import gate.Gate;
import gate.Gate.ResourceInfo;
import gate.LanguageResource;
import gate.ProcessingResource;
import gate.Resource;
import gate.VisualResource;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.event.PluginListener;
import gate.util.CreoleXmlUpperCaseFilter;
import gate.util.Err;
import gate.util.GateClassLoader;
import gate.util.GateException;
import gate.util.GateRuntimeException;
import gate.util.Out;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.SAXOutputter;
import org.jdom.output.XMLOutputter;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class implements the CREOLE register interface. DO NOT construct objects
 * of this class unless your name is gate.Gate (in which case please go back to
 * the source code repository and stop looking at other class's code).
 * <P>
 * The CREOLE register records the set of resources that are currently known to
 * the system. Each member of the register is a {@link gate.creole.ResourceData}
 * object, indexed by the class name of the resource.
 *
 * @see gate.CreoleRegister
 */
@SuppressWarnings("serial")
public class CreoleRegisterImpl extends HashMap<String, ResourceData>
                                implements CreoleRegister {

  /** A logger to use instead of sending messages to Out or Err **/
  protected static final Logger log =
      LoggerFactory.getLogger(CreoleRegisterImpl.class);

  /** Debug flag */
  protected static final boolean DEBUG = false;

  protected Set<Plugin> plugins;

  /** The parser for the CREOLE directory files */
  protected transient SAXBuilder jdomBuilder = null;

  /**
   * Name of the plugin-mappings file
   */
  public static final String PLUGIN_NAMES_MAPPING_FILE = "plugin-mappings.xml";

  /**
   * maps previous plugin names to new plugin names
   */
  protected Map<String, String> pluginNamesMappings = null;

  /**
   * Default constructor. Sets up directory files parser. <B>NOTE:</B> only
   * Factory should call this method.
   */
  public CreoleRegisterImpl() throws GateException {

    // initialise the various maps

    lrTypes = new HashSet<String>();
    prTypes = new HashSet<String>();
    vrTypes = new LinkedList<String>();
    toolTypes = new HashSet<String>();
    applicationTypes = new HashSet<String>();

    plugins = new LinkedHashSet<Plugin>();

    // construct a SAX parser for parsing the CREOLE directory files
    jdomBuilder = new SAXBuilder(false);
    jdomBuilder.setXMLFilter(new CreoleXmlUpperCaseFilter());

    // read plugin name mappings file
    readPluginNamesMappings();

  } // default constructor

  /**
   * reads plugins-mapping.xml file which is used for mapping old plugin names
   * to new plugin names
   */
  private void readPluginNamesMappings() {
    // should load it only once
    if(pluginNamesMappings != null) return;

    pluginNamesMappings = new HashMap<String, String>();

    // use jdom
    SAXBuilder builder = new SAXBuilder();

    // command line should offer URIs or file names
    try {
      URL creoleDirURL = Gate.getBuiltinCreoleDir();
      URL pluginMappingsFileURL =
          new URL(creoleDirURL, PLUGIN_NAMES_MAPPING_FILE);
      Document document = builder.build(pluginMappingsFileURL);
      @SuppressWarnings("unchecked")
      List<Element> plugins = document.getRootElement().getChildren("Plugin");
      if(plugins != null) {
        for(Element aPlugin : plugins) {
          String oldName = aPlugin.getChildText("OldName");
          String newName = aPlugin.getChildText("NewName");
          pluginNamesMappings.put(oldName, newName);
        }
      }
    }
    // indicates a well-formedness error
    catch(JDOMException e) {
      log.warn(PLUGIN_NAMES_MAPPING_FILE + " is not well-formed.", e);
    } catch(IOException e) {
      log.warn("Could not check " + PLUGIN_NAMES_MAPPING_FILE, e);
    }
  }

  /** Get the list of CREOLE directory URLs. */
  @Override
  @Deprecated
  public Set<URL> getDirectories() {
    // just so that any old code that calls this doesn't throw an exception will
    // be removed at some point when we remove all the deprecated methods
    return Collections.unmodifiableSet(new HashSet<URL>());
  }

  @Override
  public void registerPlugin(Plugin plugin) throws GateException {
    registerPlugin(plugin, true);
  }

  @Override
  public void registerPlugin(Plugin plugin, boolean loadDependencies)
      throws GateException {

    if(!plugins.contains(plugin)) {

      Gate.addKnownPlugin(plugin);

      try {

        if(loadDependencies) {
          for(Plugin required : plugin.getRequiredPlugins()) {
            registerPlugin(required, true);
          }
        }

        Document creoleXML = plugin.getCreoleXML();
        
        if (plugin.isValid()) {
          parseDirectory(plugin, creoleXML, plugin.getBaseURL(),
              new URL(plugin.getBaseURL(), "creole.xml"));
          log.info("CREOLE plugin loaded: " + plugin.getName() + " "
              + plugin.getVersion());
        }
        else {
          throw new GateException("plugin is invalid");
        }
      } catch(Throwable e) {
        // it failed:
        throw (new GateException("couldn't open creole.xml for plugin: "+plugin, e));
      }

      plugins.add(plugin);

      firePluginLoaded(plugin);
    }
  }

  @Override
  public void registerComponent(Class<? extends Resource> resourceClass)
      throws GateException {
    try {
      registerPlugin(new Plugin.Component(resourceClass));
    } catch(MalformedURLException mue) {
      throw new GateException("Unable to register component", mue);
    }
  }

  /**
   * Register a single CREOLE directory. The <CODE>creole.xml</CODE> file at the
   * URL is parsed, and <CODE>CreoleData</CODE> objects added to the register.
   * If the directory URL has not yet been added it is now added. If any other
   * plugins that need to be loaded for this plugin to load (specified by
   * <CODE>REQUIRES</Code> elements in <code>creole.xml</code>) will only be
   * loaded if the <code>loadDependencies</code> param is true. It is useful to
   * be able to ignore dependencies when loading an xgapp where we know they
   * have already been resolved.
   */
  @Override
  public void registerDirectories(URL directoryUrl, boolean loadDependencies)
      throws GateException {
    // TODO we need to add support for the loadDependencies option to
    // registerPlugin
    try {
      Plugin plugin = new Plugin.Directory(directoryUrl);

      registerPlugin(plugin);
    } catch(Exception e) {
      throw new GateException("Failed to load plugin", e);
    }
  }

  /**
   * Register a single CREOLE directory. The <CODE>creole.xml</CODE> file at the
   * URL is parsed, and <CODE>CreoleData</CODE> objects added to the register.
   * If the directory URL has not yet been added it is now added. If any other
   * plugins that nees top be loaded for this plugin to load (specified by
   * <CODE>REQUIRES</Code> elements in <code>creole.xml</code>) will also be
   * loaded.
   */
  @Override
  public void registerDirectories(URL directoryUrl) throws GateException {
    registerDirectories(directoryUrl, true);
  }

  /**
   * Parse a directory file (represented as an open stream), adding resource
   * data objects to the CREOLE register as they occur. If the resource is from
   * a URL then that location is passed (otherwise null).
   */
  protected void parseDirectory(Plugin plugin, Document jdomDoc,
      URL directoryUrl, URL creoleFileUrl) throws GateException {
    // create a handler for the directory file and parse it;
    // this will create ResourceData entries in the register
    try {

      CreoleAnnotationHandler annotationHandler =
          new CreoleAnnotationHandler(plugin);

      GateClassLoader gcl = Gate.getClassLoader()
          .getDisposableClassLoader(plugin.getBaseURI().toString());

      // Add any JARs from the creole.xml to the GATE ClassLoader
      annotationHandler.addJarsToClassLoader(gcl, jdomDoc);

      // Make sure there is a RESOURCE element for every resource type the
      // directory defines
      annotationHandler.createResourceElementsForDirInfo(jdomDoc);

      processFullCreoleXmlTree(plugin, jdomDoc,
          annotationHandler);
    } catch(URISyntaxException | IOException e) {
      throw (new GateException(e));
    } catch(JDOMException je) {
      if(DEBUG) je.printStackTrace(Err.getPrintWriter());
      throw (new GateException(je));
    }

  } // parseDirectory

  private void processFullCreoleXmlTree(Plugin plugin,
      Document jdomDoc, CreoleAnnotationHandler annotationHandler)
      throws GateException, IOException, JDOMException {
    // now we can process any annotations on the new classes
    // and augment the XML definition
    annotationHandler.processAnnotations(jdomDoc);

    // debugging
    if(DEBUG) {
      XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
      xmlOut.output(jdomDoc, System.out);
    }

    // finally, parse the augmented definition with the normal parser
    DefaultHandler handler =
        new CreoleXmlHandler(this, plugin);
    SAXOutputter outputter =
        new SAXOutputter(handler, handler, handler, handler);
    outputter.output(jdomDoc);
    if(DEBUG) {
      Out.prln("done parsing " + plugin);
    }
  }

  /**
   * Register resources that are built in to the GATE distribution. These
   * resources are described by the <TT>creole.xml</TT> file in
   * <TT>resources/creole</TT>.
   */
  @Override
  public void registerBuiltins() throws GateException {

    try {
      URL creoleDirURL = Gate.getBuiltinCreoleDir();
      // URL creoleFileURL = new URL(creoleDirURL, "creole.xml");
      // URL creoleFileURL = Files.getGateResource("/creole/creole.xml");
      // parseDirectory(creoleFileURL.openStream(), creoleDirURL,
      // creoleFileURL,true);*/
      Plugin plugin = new Plugin.Directory(creoleDirURL);

      parseDirectory(plugin, plugin.getCreoleXML(), plugin.getBaseURL(),
          new URL(plugin.getBaseURL(), "creole.xml"));
      log.info("CREOLE plugin loaded: " + plugin.getName() + " "
          + plugin.getVersion());

    } catch(Exception e) {
      if(DEBUG) log.debug("unable to register built in creole",e);
      throw (new GateException(e));
    }
  } // registerBuiltins()

  /**
   * Overide HashMap's put method to maintain a list of all the types of LR in
   * the register, and a list of tool types. The key is the resource type, the
   * value its data.
   */
  @Override
  public ResourceData put(String key, ResourceData rd) {

    if(super.containsKey(key)) {
      ResourceData rData = super.get(key);
      rData.increaseReferenceCount();
      log.warn(key + " is already defined, new reference will be ignored.");

      // TODO not sure what we should actually return here
      return rData;
    }

    // get the resource implementation class
    Class<? extends Resource> resClass = null;
    try {
      resClass = rd.getResourceClass();
    } catch(ClassNotFoundException e) {
      throw new GateRuntimeException(
          "Couldn't get resource class from the resource data:" + e);
    }

    // add class names to the type lists
    if(LanguageResource.class.isAssignableFrom(resClass)) {
      if(DEBUG) Out.prln("LR: " + resClass);
      if(lrTypes == null) lrTypes = new HashSet<String>(); // for
      // deserialisation
      lrTypes.add(rd.getClassName());
    }
    if(ProcessingResource.class.isAssignableFrom(resClass)) {
      if(DEBUG) {
        Out.prln("PR: " + resClass);
        // Out.prln("prTypes: " + prTypes);
        // Out.prln("rd.getClassName(): " + rd.getClassName());
      }
      if(prTypes == null) prTypes = new HashSet<String>(); // for
      // deserialisation
      prTypes.add(rd.getClassName());
    }
    if(VisualResource.class.isAssignableFrom(resClass)) {
      if(DEBUG) Out.prln("VR: " + resClass);
      if(vrTypes == null) vrTypes = new LinkedList<String>(); // for
      // deserialisation
      // we have to simulate Set behaviour as this is a list
      if(!vrTypes.contains(rd.getClassName())) vrTypes.add(rd.getClassName());
    }
    if(Controller.class.isAssignableFrom(resClass)) {
      if(DEBUG) Out.prln("Controller: " + resClass);
      if(controllerTypes == null) controllerTypes = new HashSet<String>(); // for
      // deserialisation
      controllerTypes.add(rd.getClassName());
    }
    if(PackagedController.class.isAssignableFrom(resClass)) {
      if(DEBUG) Out.prln("Application: " + resClass);
      if(applicationTypes == null) applicationTypes = new HashSet<String>();
      applicationTypes.add(rd.getClassName());
    }

    // maintain tool types list
    if(rd.isTool()) {
      if(toolTypes == null) toolTypes = new HashSet<String>(); // for
      // deserialisation
      toolTypes.add(rd.getClassName());
    }

    return super.put(key, rd);
  } // put(key, value)

  @Override
  public void unregisterPlugin(Plugin plugin) {
    if(plugins.remove(plugin)) {
      
      List<Plugin> dependantPlugins = new ArrayList<Plugin>();
      for (Plugin otherPlugin : plugins) {
        if (otherPlugin.getRequiredPlugins().contains(plugin))
          dependantPlugins.add(otherPlugin);
      }
      
      for (Plugin otherPlugin : dependantPlugins) {
        unregisterPlugin(otherPlugin);
      }
      
      int prCount = 0;

      for(ResourceInfo rInfo : plugin.getResourceInfoList()) {
        ResourceData rData = get(rInfo.getResourceClassName());
        if(rData != null && rData.getReferenceCount() == 1) {
          // we only need to remove resources if we are actually going to
          // remove the plugin
          try {
            List<Resource> loaded =
                getAllInstances(rInfo.getResourceClassName(), true);
            prCount += loaded.size();
            for(Resource r : loaded) {
              // System.out.println(r);
              Factory.deleteResource(r);
            }
          } catch(GateException e) {
            // not much we can do here other than dump the exception
            e.printStackTrace();
          }
        }

        remove(rInfo.getResourceClassName());
      }

      try {
        Gate.getClassLoader().forgetClassLoader(
            plugin.getBaseURI().toString(),
            plugin);
      } catch(Exception e) {
        e.printStackTrace();
      }

      log.info("CREOLE plugin unloaded: " + plugin.getName() + " "
          + plugin.getVersion());
      if(prCount > 0)
        log.warn(prCount + " resources were deleted as they relied on the "
            + plugin.getName() + " plugin");

      firePluginUnloaded(plugin);
    }
  }

  /**
   * Removes a CREOLE directory from the set of loaded directories.
   *
   * @param directory
   */
  @Override
  @Deprecated
  public void removeDirectory(URL directory) {

    if(directory == null) return;

    for(Plugin plugin : plugins) {
      if(directory.equals(plugin.getBaseURL())) {
        unregisterPlugin(plugin);
        break;
      }
    }
  }

  /**
   * Overide HashMap's delete method to update the lists of types in the
   * register.
   */
  @Override
  public ResourceData remove(Object key) {
    ResourceData rd = get(key);
    if(rd == null) return null;

    // TODO not sure what we should actually return here
    if(rd.reduceReferenceCount() > 0) {
      if(DEBUG)
        Out.println(
            key + " is still defined by another plugin so won't be unloaded");
      return rd;
    }

    if(DEBUG) {
      Out.prln(key);
      Out.prln(rd);
    }

    try {
      if(LanguageResource.class.isAssignableFrom(rd.getResourceClass())) {
        lrTypes.remove(rd.getClassName());
      } else if(ProcessingResource.class
          .isAssignableFrom(rd.getResourceClass())) {
        prTypes.remove(rd.getClassName());
      } else if(VisualResource.class.isAssignableFrom(rd.getResourceClass())) {
        vrTypes.remove(rd.getClassName());
      } else if(Controller.class.isAssignableFrom(rd.getResourceClass())) {
        controllerTypes.remove(rd.getClassName());
      } else if(PackagedController.class
          .isAssignableFrom(rd.getResourceClass())) {
        applicationTypes.remove(rd.getClassName());
      }
    } catch(ClassNotFoundException cnfe) {
      throw new GateRuntimeException(
          "Could not load class specified in CREOLE data.", cnfe);
    }
    // maintain tool types list
    if(rd.isTool()) toolTypes.remove(rd.getClassName());

    return super.remove(key);
  } // remove(Object)

  /**
   * Overide HashMap's clear to update the list of LR types in the register, and
   * remove all resources and forgets all directories.
   */
  @Override
  public void clear() {
    lrTypes.clear();
    prTypes.clear();
    vrTypes.clear();
    toolTypes.clear();
    applicationTypes.clear();
    plugins.clear();
    super.clear();
  } // clear()

  /** Get the list of types of LR in the register. */
  @Override
  public Set<String> getLrTypes() {
    return Collections.unmodifiableSet(lrTypes);
  }

  /** Get the list of types of PR in the register. */
  @Override
  public Set<String> getPrTypes() {
    return Collections.unmodifiableSet(prTypes);
  }

  /** Get the list of types of VR in the register. */
  @Override
  public Set<String> getVrTypes() {
    return Collections.unmodifiableSet(new HashSet<String>(vrTypes));
  }

  /** Get the list of types of Controller in the register. */
  @Override
  public Set<String> getControllerTypes() {
    return Collections.unmodifiableSet(controllerTypes);
  }

  /** Get the list of types of TOOL resources in the register. */
  @Override
  public Set<String> getToolTypes() {
    return Collections.unmodifiableSet(toolTypes);
  }

  /**
   * Get the list of types of packaged application resources in the register.
   */
  @Override
  public Set<String> getApplicationTypes() {
    return Collections.unmodifiableSet(applicationTypes);
  }

  /** Get a list of all instantiations of LR in the register. */
  @Override
  public List<LanguageResource> getLrInstances() {
    Set<String> lrTypeSet = getLrTypes();
    List<LanguageResource> instances = new ArrayList<LanguageResource>();

    Iterator<String> iter = lrTypeSet.iterator();
    while(iter.hasNext()) {
      String type = iter.next();
      instances.addAll(getLrInstances(type));
    } // End while
    return Collections.unmodifiableList(instances);
  } // getLrInstances()

  /** Get a list of all instantiations of PR in the register. */
  @Override
  public List<ProcessingResource> getPrInstances() {
    Set<String> prTypeSet = getPrTypes();
    List<ProcessingResource> instances = new ArrayList<ProcessingResource>();

    Iterator<String> iter = prTypeSet.iterator();
    while(iter.hasNext()) {
      String type = iter.next();
      instances.addAll(getPrInstances(type));
    } // End while

    return Collections.unmodifiableList(instances);
  } // getPrInstances()

  /** Get a list of all instantiations of VR in the register. */
  @Override
  public List<VisualResource> getVrInstances() {
    Set<String> vrTypeSet = getVrTypes();
    List<VisualResource> instances = new ArrayList<VisualResource>();

    Iterator<String> iter = vrTypeSet.iterator();
    while(iter.hasNext()) {
      String type = iter.next();
      instances.addAll(getVrInstances(type));
    } // End while

    return Collections.unmodifiableList(instances);
  } // getVrInstances()

  @Override
  public List<LanguageResource> getLrInstances(String resourceTypeName) {
    ResourceData resData = get(resourceTypeName);
    if(resData == null) return Collections.emptyList();

    return new TypedResourceList<LanguageResource>(resData.getInstantiations(),
        LanguageResource.class);
  } // getLrInstances

  @Override
  public List<ProcessingResource> getPrInstances(String resourceTypeName) {
    ResourceData resData = get(resourceTypeName);
    if(resData == null) return Collections.emptyList();

    return new TypedResourceList<ProcessingResource>(
        resData.getInstantiations(), ProcessingResource.class);
  } // getPrInstances

  @Override
  public List<VisualResource> getVrInstances(String resourceTypeName) {
    ResourceData resData = get(resourceTypeName);
    if(resData == null) return Collections.emptyList();

    return new TypedResourceList<VisualResource>(resData.getInstantiations(),
        VisualResource.class);
  } // getVrInstances

  /** Get a list of all non-private instantiations of LR in the register. */
  @Override
  public List<LanguageResource> getPublicLrInstances() {
    return Collections.unmodifiableList(getPublics(getLrInstances()));
  }// getPublicLrInstances()

  /** Get a list of all non-private instantiations of PR in the register. */
  @Override
  public List<ProcessingResource> getPublicPrInstances() {
    return Collections.unmodifiableList(getPublics(getPrInstances()));
  }// getPublicPrInstances()

  /** Get a list of all non-private instantiations of VR in the register. */
  @Override
  public List<VisualResource> getPublicVrInstances() {
    return Collections.unmodifiableList(getPublics(getVrInstances()));
  }// getPublicVrInstances()

  /** Get a list of all non-private types of LR in the register. */
  @Override
  public List<String> getPublicLrTypes() {
    return Collections.unmodifiableList(getPublicTypes(getLrTypes()));
  }// getPublicLrTypes()

  /** Get a list of all non-private types of PR in the register. */
  @Override
  public List<String> getPublicPrTypes() {
    return Collections.unmodifiableList(getPublicTypes(getPrTypes()));
  }// getPublicPrTypes()

  /** Get a list of all non-private types of VR in the register. */
  @Override
  public List<String> getPublicVrTypes() {
    return Collections.unmodifiableList(getPublicTypes(vrTypes));
  }// getPublicVrTypes()

  /** Get a list of all non-private types of controller in the register. */
  @Override
  public List<String> getPublicControllerTypes() {
    return Collections.unmodifiableList(getPublicTypes(getControllerTypes()));
  }// getPublicPrTypes()

  @Override
  public List<Resource> getAllInstances(String type) throws GateException {
    return getAllInstances(type, false);
  }

  public List<Resource> getAllInstances(String type, boolean includeHidden)
      throws GateException {

    List<Resource> res = new ArrayList<Resource>();
    Class<? extends Resource> targetClass;
    try {
      targetClass =
          Gate.getClassLoader().loadClass(type).asSubclass(Resource.class);
    } catch(ClassNotFoundException cnfe) {
      throw new GateException("Invalid type " + type);
    }
    for(Map.Entry<String, ResourceData> entry : entrySet()) {
      String aType = entry.getKey();
      Class<?> aClass;
      try {
        aClass = entry.getValue().getResourceClass();
        if(targetClass.isAssignableFrom(aClass)) {
          // filter out hidden instances
          Iterator<? extends Resource> newInstancesIter =
              get(aType).getInstantiations().iterator();
          while(newInstancesIter.hasNext()) {
            Resource instance = newInstancesIter.next();
            if(includeHidden
                || !Gate.getHiddenAttribute(instance.getFeatures())) {
              res.add(instance);
            }
          }
        }
      } catch(ClassNotFoundException cnfe) {
        throw new GateRuntimeException(
            "A type registered in the creole register does not exist in the VM!");
      }

    } // while(typesIter.hasNext())

    return res;
  }

  /**
   * Returns a list of strings representing class names for large VRs valid for
   * a given type of language/processing resource. The default VR will be the
   * first in the returned list.
   *
   * @param resourceClassName
   *          the name of the resource that has large viewers. If
   *          resourceClassName is <b>null</b> then an empty list will be
   *          returned.
   * @return a list with Strings representing the large VRs for the
   *         resourceClassName
   */
  @Override
  public List<String> getLargeVRsForResource(String resourceClassName) {
    return getVRsForResource(resourceClassName, ResourceData.LARGE_GUI);
  }// getLargeVRsForResource()

  /**
   * Returns a list of strings representing class names for small VRs valid for
   * a given type of language/processing resource The default VR will be the
   * first in the returned list.
   *
   * @param resourceClassName
   *          the name of the resource that has large viewers. If
   *          resourceClassName is <b>null</b> then an empty list will be
   *          returned.
   * @return a list with Strings representing the large VRs for the
   *         resourceClassName
   */
  @Override
  public List<String> getSmallVRsForResource(String resourceClassName) {
    return getVRsForResource(resourceClassName, ResourceData.SMALL_GUI);
  }// getSmallVRsForResource

  /**
   * Returns a list of strings representing class names for guiType VRs valid
   * for a given type of language/processing resource The default VR will be the
   * first in the returned list.
   *
   * @param resourceClassName
   *          the name of the resource that has large viewers. If
   *          resourceClassName is <b>null</b> then an empty list will be
   *          returned.
   * @param guiType
   *          can be ResourceData's LARGE_GUI or SMALL_GUI
   * @return a list with Strings representing the large VRs for the
   *         resourceClassName
   */
  private List<String> getVRsForResource(String resourceClassName,
      int guiType) {
    // If resurceClassName is null return a simply list
    if(resourceClassName == null)
      return Collections.unmodifiableList(new ArrayList<String>());
    // create a Class object for the resource
    Class<?> resourceClass = null;
    GateClassLoader classLoader = Gate.getClassLoader();
    try {
      resourceClass = classLoader.loadClass(resourceClassName);
    } catch(ClassNotFoundException ex) {
      throw new GateRuntimeException(
          "Couldn't get resource class from the resource name:" + ex);
    } // End try
    LinkedList<String> responseList = new LinkedList<String>();
    String defaultVR = null;
    // Take all VRs and for each large one, test if
    // resourceClassName is asignable form VR's RESOURCE_DISPLAYED
    Iterator<String> vrIterator = vrTypes.iterator();
    while(vrIterator.hasNext()) {
      String vrClassName = vrIterator.next();
      ResourceData vrResourceData = this.get(vrClassName);
      if(vrResourceData == null)
        throw new GateRuntimeException(
            "Couldn't get resource data for VR called " + vrClassName);
      if(vrResourceData.getGuiType() == guiType) {
        String resourceDisplayed = vrResourceData.getResourceDisplayed();
        if(resourceDisplayed != null) {
          Class<?> resourceDisplayedClass = null;
          try {
            resourceDisplayedClass = classLoader.loadClass(resourceDisplayed);
          } catch(ClassNotFoundException ex) {
            throw new GateRuntimeException(
                "Couldn't get resource class from the resource name :"
                    + resourceDisplayed + " " + ex);
          } // End try
          if(resourceDisplayedClass.isAssignableFrom(resourceClass)) {
            responseList.add(vrClassName);
            if(vrResourceData.isMainView()) {
              defaultVR = vrClassName;
            } // End if
          } // End if
        } // End if
      } // End if
    } // End while
    if(defaultVR != null) {
      responseList.remove(defaultVR);
      responseList.addFirst(defaultVR);
    } // End if
    return Collections.unmodifiableList(responseList);
  }// getVRsForResource()

  /**
   * Returns a list of strings representing class names for annotation VRs that
   * are able to display/edit all types of annotations. The default VR will be
   * the first in the returned list.
   *
   * @return a list with all VRs that can display all annotation types
   */
  @Override
  public List<String> getAnnotationVRs() {
    LinkedList<String> responseList = new LinkedList<String>();
    String defaultVR = null;
    Iterator<String> vrIterator = vrTypes.iterator();
    while(vrIterator.hasNext()) {
      String vrClassName = vrIterator.next();
      ResourceData vrResourceData = this.get(vrClassName);
      if(vrResourceData == null)
        throw new GateRuntimeException(
            "Couldn't get resource data for VR called " + vrClassName);
      Class<?> vrResourceClass = null;
      try {
        vrResourceClass = vrResourceData.getResourceClass();
      } catch(ClassNotFoundException ex) {
        throw new GateRuntimeException(
            "Couldn't create a class object for VR called " + vrClassName);
      } // End try
        // Test if VR can display all types of annotations
      if(vrResourceData.getGuiType() == ResourceData.NULL_GUI
          && vrResourceData.getAnnotationTypeDisplayed() == null
          && vrResourceData.getResourceDisplayed() == null
          && gate.creole.AnnotationVisualResource.class
              .isAssignableFrom(vrResourceClass)) {

        responseList.add(vrClassName);
        if(vrResourceData.isMainView()) defaultVR = vrClassName;
      } // End if
    } // End while
    if(defaultVR != null) {
      responseList.remove(defaultVR);
      responseList.addFirst(defaultVR);
    } // End if
    return Collections.unmodifiableList(responseList);
  }// getAnnotationVRs()

  /**
   * Returns a list of strings representing class names for annotation VRs that
   * are able to display/edit a given annotation type The default VR will be the
   * first in the returned list.
   */
  @Override
  public List<String> getAnnotationVRs(String annotationType) {
    if(annotationType == null)
      return Collections.unmodifiableList(new ArrayList<String>());
    LinkedList<String> responseList = new LinkedList<String>();
    String defaultVR = null;
    Iterator<String> vrIterator = vrTypes.iterator();
    while(vrIterator.hasNext()) {
      String vrClassName = vrIterator.next();
      ResourceData vrResourceData = this.get(vrClassName);
      if(vrResourceData == null)
        throw new GateRuntimeException(
            "Couldn't get resource data for VR called " + vrClassName);
      Class<?> vrResourceClass = null;
      try {
        vrResourceClass = vrResourceData.getResourceClass();
      } catch(ClassNotFoundException ex) {
        throw new GateRuntimeException(
            "Couldn't create a class object for VR called " + vrClassName);
      } // End try
        // Test if VR can display all types of annotations
      if(vrResourceData.getGuiType() == ResourceData.NULL_GUI
          && vrResourceData.getAnnotationTypeDisplayed() != null
          && gate.creole.AnnotationVisualResource.class
              .isAssignableFrom(vrResourceClass)) {

        String annotationTypeDisplayed =
            vrResourceData.getAnnotationTypeDisplayed();
        if(annotationTypeDisplayed.equals(annotationType)) {
          responseList.add(vrClassName);
          if(vrResourceData.isMainView()) defaultVR = vrClassName;
        } // End if
      } // End if
    } // End while
    if(defaultVR != null) {
      responseList.remove(defaultVR);
      responseList.addFirst(defaultVR);
    } // End if
    return Collections.unmodifiableList(responseList);
  }// getAnnotationVRs()

  /**
   * Renames an existing resource.
   */
  @Override
  public void setResourceName(Resource res, String newName) {
    String oldName = res.getName();
    res.setName(newName);
    fireResourceRenamed(res, oldName, newName);
  }

  /**
   * Returns a list of strings representing annotations types for which there
   * are custom viewers/editor registered.
   */
  @Override
  public List<String> getVREnabledAnnotationTypes() {
    LinkedList<String> responseList = new LinkedList<String>();
    Iterator<String> vrIterator = vrTypes.iterator();
    while(vrIterator.hasNext()) {
      String vrClassName = vrIterator.next();
      ResourceData vrResourceData = this.get(vrClassName);
      if(vrResourceData == null)
        throw new GateRuntimeException(
            "Couldn't get resource data for VR called " + vrClassName);
      // Test if VR can display all types of annotations
      if(vrResourceData.getGuiType() == ResourceData.NULL_GUI
          && vrResourceData.getAnnotationTypeDisplayed() != null) {

        String annotationTypeDisplayed =
            vrResourceData.getAnnotationTypeDisplayed();
        responseList.add(annotationTypeDisplayed);
      } // End if
    } // End while
    return Collections.unmodifiableList(responseList);
  }// getVREnabledAnnotationTypes()

  /** Get a list of all non-private instantiations. */
  protected <T> List<T> getPublics(List<T> instances) {
    Iterator<T> iter = instances.iterator();
    List<T> publics = new ArrayList<T>();

    // for each instance, if resource data specifies it isn't private,
    // add to the publics list
    while(iter.hasNext()) {
      T res = iter.next();
      ResourceData rd = get(res.getClass().getName());
      if(!rd.isPrivate()) publics.add(res);
    }

    return Collections.unmodifiableList(publics);
  } // getPublics

  /** Gets a list of all non private types from alist of types */
  protected List<String> getPublicTypes(Collection<String> types) {
    Iterator<String> iter = types.iterator();
    List<String> publics = new ArrayList<String>();
    while(iter.hasNext()) {
      String oneType = iter.next();
      ResourceData rData = get(oneType);
      if(rData != null && !rData.isPrivate()) publics.add(oneType);
    }
    return Collections.unmodifiableList(publics);
  }// getPublicTypes

  @Override
  public synchronized void removeCreoleListener(CreoleListener l) {
    if(creoleListeners != null && creoleListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<CreoleListener> v =
          (Vector<CreoleListener>)creoleListeners.clone();
      v.removeElement(l);
      creoleListeners = v;
    }
  }

  @Override
  public synchronized void addCreoleListener(CreoleListener l) {
    @SuppressWarnings("unchecked")
    Vector<CreoleListener> v = creoleListeners == null
        ? new Vector<CreoleListener>(2)
        : (Vector<CreoleListener>)creoleListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      creoleListeners = v;
    }
  } // getPublicTypes

  /**
   * Removes a {@link gate.event.CreoleListener} previously registered with this
   * CreoleRegister. {@see #addCreoleListener()}
   */

  /**
   * Registers a {@link gate.event.CreoleListener}with this CreoleRegister. The
   * register will fire events every time a resource is added to or removed from
   * the system.
   */
  // addCreoleListener
  /**
   * Notifies all listeners that a new {@link gate.Resource} has been loaded
   * into the system
   */
  // fireResourceLoaded
  /**
   * Notifies all listeners that a {@link gate.Resource} has been unloaded from
   * the system
   */

  // fireResourceUnloaded
  /** A list of the types of LR in the register. */
  protected Set<String> lrTypes;

  /** A list of the types of PR in the register. */
  protected Set<String> prTypes;

  /** A list of the types of VR in the register. */
  protected List<String> vrTypes;

  /** A list of the types of Controller in the register. */
  protected Set<String> controllerTypes;

  /** A list of the types of TOOL in the register. */
  protected Set<String> toolTypes;

  /** A list of the types of Packaged Applications in the register */
  protected Set<String> applicationTypes;

  private transient Vector<CreoleListener> creoleListeners;

  private transient List<PluginListener> pluginListeners =
      new CopyOnWriteArrayList<PluginListener>();

  protected void firePluginLoaded(Plugin plugin) {
    for(PluginListener listener : pluginListeners) {
      listener.pluginLoaded(plugin);
    }
  }

  protected void firePluginUnloaded(Plugin plugin) {
    for(PluginListener listener : pluginListeners) {
      listener.pluginUnloaded(plugin);
    }
  }

  public void addPluginListener(PluginListener listener) {
    pluginListeners.add(listener);
  }

  public void removePluginListener(PluginListener listener) {
    pluginListeners.remove(listener);
  }

  protected void fireResourceLoaded(CreoleEvent e) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).resourceLoaded(e);
      }
    }
  }

  protected void fireResourceUnloaded(CreoleEvent e) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).resourceUnloaded(e);
      }
    }
  }

  protected void fireResourceRenamed(Resource res, String oldName,
      String newName) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).resourceRenamed(res, oldName, newName);
      }
    }
  }

  protected void fireDatastoreOpened(CreoleEvent e) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreOpened(e);
      }
    }
  }

  protected void fireDatastoreCreated(CreoleEvent e) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreCreated(e);
      }
    }
  }

  protected void fireDatastoreClosed(CreoleEvent e) {
    if(creoleListeners != null) {
      Vector<CreoleListener> listeners = creoleListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).datastoreClosed(e);
      }
    }
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
    fireResourceLoaded(e);
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
    fireResourceUnloaded(e);
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName,
      String newName) {
    fireResourceRenamed(resource, oldName, newName);
  }

  @Override
  public void datastoreOpened(CreoleEvent e) {
    fireDatastoreOpened(e);
  }

  @Override
  public void datastoreCreated(CreoleEvent e) {
    fireDatastoreCreated(e);
  }

  @Override
  public void datastoreClosed(CreoleEvent e) {
    fireDatastoreClosed(e);
  }

  /** The lists of listeners registered with this CreoleRegister */

  /**
   * Type-safe read-only list used by getLrInstances, getPrInstances, etc.
   */
  private static class TypedResourceList<T extends Resource>
                                        extends AbstractList<T> {
    private List<Resource> backingList;

    private Class<T> realType;

    TypedResourceList(List<Resource> backingList, Class<T> realType) {
      this.backingList = backingList;
      this.realType = realType;
    }

    @Override
    public T get(int i) {
      return realType.cast(backingList.get(i));
    }

    @Override
    public int size() {
      return backingList.size();
    }
  }

  @Override
  public Set<Plugin> getPlugins() {
    return Collections.unmodifiableSet(plugins);
  }

} // class CreoleRegisterImpl
