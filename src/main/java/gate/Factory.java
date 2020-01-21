/*
 *  Factory.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 25/May/2000
 *
 *  $Id: Factory.java 20037 2017-02-01 06:17:21Z markagreenwood $
 */

package gate;

import gate.annotation.ImmutableAnnotationSetImpl;
import gate.creole.AbstractProcessingResource;
import gate.creole.AbstractResource;
import gate.creole.AnnotationSchema;
import gate.creole.ConditionalController;
import gate.creole.CustomDuplication;
import gate.creole.ParameterException;
import gate.creole.ParameterList;
import gate.creole.Plugin;
import gate.creole.ResourceData;
import gate.creole.ResourceInstantiationException;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.persist.PersistenceException;
import gate.persist.SerialDataStore;
import gate.util.Out;
import gate.util.SimpleFeatureMapImpl;
import gate.util.Strings;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * Provides static methods for the creation of Resources.
 */
public abstract class Factory {
  /** Debug flag */
  private static final boolean DEBUG = false;

  private static final boolean DEBUG_DUPLICATION = false;

  private static final Logger log = Logger.getLogger(Factory.class);

  /** An object to source events from. */
  private static CreoleProxy creoleProxy;

  /**
   * Create an instance of a resource using default parameter values.
   * 
   * @see #createResource(String,FeatureMap)
   */
  public static Resource createResource(String resourceClassName)
          throws ResourceInstantiationException {
    // get the resource metadata
    ResourceData resData = Gate.getCreoleRegister().get(resourceClassName);
    if(resData == null) {
      Set<Plugin> plugins = Gate.getPlugins(resourceClassName);

      StringBuilder msg = new StringBuilder();
      msg.append("Couldn't get resource data for ").append(resourceClassName)
              .append(".\n\n");

      if(plugins.isEmpty()) {
        msg.append("You may need first to load the plugin that contains your resource.\n");
        msg.append("For example, to create a gate.creole.tokeniser.DefaultTokeniser\n");
        msg.append("you need first to load the ANNIE plugin.\n\n");
      } else if(plugins.size() == 1) {
        msg.append(resourceClassName).append(" can be found in the ")
                .append(plugins.iterator().next().getName())
                .append(" plugin\n\n");
      } else {
        msg.append(resourceClassName).append(
                " can be found in the following plugins\n   ");
        for(Plugin dInfo : plugins) {
          msg.append(dInfo.getName()).append(", ");
        }

        msg.setLength(msg.length() - 2);
        msg.append("\n\n");
      }

      msg.append("Go to the menu File->Manage CREOLE plugins or use the method\n");
      msg.append("\"registerPlugin\" on Gate.getCreoleRegister()");

      throw new ResourceInstantiationException(msg.toString());
    }

    // get the parameter list and default values
    ParameterList paramList = resData.getParameterList();
    FeatureMap parameterValues = null;
    try {
      parameterValues = paramList.getInitimeDefaults();
    } catch(ParameterException e) {
      throw new ResourceInstantiationException(
              "Couldn't get default parameters for " + resourceClassName + ": "
                      + e);
    }

    return createResource(resourceClassName, parameterValues);
  } // createResource(resClassName)

  /**
   * Create an instance of a resource, and return it. Callers of this
   * method are responsible for querying the resource's parameter lists,
   * putting together a set that is complete apart from runtime
   * parameters, and passing a feature map containing these parameter
   * settings.
   *
   * @param resourceClassName the name of the class implementing the
   *          resource.
   * @param parameterValues the feature map containing intialisation
   *          time parameterValues for the resource.
   * @return an instantiated resource.
   */
  public static Resource createResource(String resourceClassName,
          FeatureMap parameterValues) throws ResourceInstantiationException {
    return createResource(resourceClassName, parameterValues, null, null);
  } // createResource(resClassName, paramVals, listeners)

  /**
   * Create an instance of a resource, and return it. Callers of this
   * method are responsible for querying the resource's parameter lists,
   * putting together a set that is complete apart from runtime
   * parameters, and passing a feature map containing these parameter
   * settings.
   *
   * @param resourceClassName the name of the class implementing the
   *          resource.
   * @param parameterValues the feature map containing intialisation
   *          time parameterValues for the resource.
   * @param features the features for the new resource
   * @return an instantiated resource.
   */
  public static Resource createResource(String resourceClassName,
          FeatureMap parameterValues, FeatureMap features)
          throws ResourceInstantiationException {
    return createResource(resourceClassName, parameterValues, features, null);
  }

  /**
   * Create an instance of a resource, and return it. Callers of this
   * method are responsible for querying the resource's parameter lists,
   * putting together a set that is complete apart from runtime
   * parameters, and passing a feature map containing these parameter
   * settings.
   *
   * In the case of ProcessingResources they will have their runtime
   * parameters initialised to their default values.
   *
   * @param resourceClassName the name of the class implementing the
   *          resource.
   * @param parameterValues the feature map containing intialisation
   *          time parameterValues for the resource.
   * @param features the features for the new resource or null to not
   *          assign any (new) features.
   * @param resourceName the name to be given to the resource or null to
   *          assign a default name.
   * @return an instantiated resource.
   */
  public static Resource createResource(String resourceClassName,
          FeatureMap parameterValues, FeatureMap features, String resourceName)
          throws ResourceInstantiationException {
    // get the resource metadata
    ResourceData resData = Gate.getCreoleRegister().get(resourceClassName);
    if(resData == null) {
      Set<Plugin> plugins = Gate.getPlugins(resourceClassName);

      StringBuilder msg = new StringBuilder();
      msg.append("Couldn't get resource data for ").append(resourceClassName)
              .append(".\n\n");

      if(plugins.isEmpty()) {
        msg.append("You may need first to load the plugin that contains your resource.\n");
        msg.append("For example, to create a gate.creole.tokeniser.DefaultTokeniser\n");
        msg.append("you need first to load the ANNIE plugin.\n\n");
      } else if(plugins.size() == 1) {
        msg.append(resourceClassName).append(" can be found in the ")
                .append(plugins.iterator().next().getName())
                .append(" plugin\n\n");
      } else {
        msg.append(resourceClassName).append(
                " can be found in the following plugins\n   ");
        for(Plugin dInfo : plugins) {
          msg.append(dInfo.getName()).append(", ");
        }

        msg.setLength(msg.length() - 2);
        msg.append("\n\n");
      }

      msg.append("Go to the menu File->Manage CREOLE plugins or use the method\n");
      msg.append("Gate.getCreoleRegister().registerPlugin(plugin).");

      throw new ResourceInstantiationException(msg.toString());
    }
    // get the default implementation class
    Class<? extends Resource> resClass = null;
    try {
      resClass = resData.getResourceClass();
    } catch(ClassNotFoundException e) {
      throw new ResourceInstantiationException(
              "Couldn't get resource class from the resource data:"
                      + Strings.getNl() + e);
    }

    // create a pointer for the resource
    Resource res = null;

    // if the object is an LR and it should come from a DS then create
    // that way
    DataStore dataStore;
    if(LanguageResource.class.isAssignableFrom(resClass)
            && ((dataStore =
                    (DataStore)parameterValues
                            .get(DataStore.DATASTORE_FEATURE_NAME)) != null)) {
      // ask the datastore to create our object
      if(dataStore instanceof SerialDataStore) {
        // SDS doesn't need a wrapper class; just check for
        // serialisability
        if(!Serializable.class.isAssignableFrom(resClass))
          throw new ResourceInstantiationException(
                  "Resource cannot be (de-)serialized: " + resClass.getName());
      }

      // get the datastore instance id and retrieve the resource
      Object instanceId = parameterValues.get(DataStore.LR_ID_FEATURE_NAME);
      if(instanceId == null)
        throw new ResourceInstantiationException("No instance id for "
                + resClass);
      try {
        res = dataStore.getLr(resClass.getName(), instanceId);
      } catch(PersistenceException pe) {
        throw new ResourceInstantiationException("Bad read from DB: " + pe);
      } catch(SecurityException se) {
        throw new ResourceInstantiationException("Insufficient permissions: "
                + se);
      }
      resData.addInstantiation(res);
      if(features != null) {
        if(res.getFeatures() == null) {
          res.setFeatures(newFeatureMap());
        }
        res.getFeatures().putAll(features);
      }

      // set the name
      if(res.getName() == null) {
        res.setName(resourceName == null ? resData.getName() + "_"
                + Gate.genSym() : resourceName);
      }

      // fire the event
      creoleProxy.fireResourceLoaded(new CreoleEvent(res,
              CreoleEvent.RESOURCE_LOADED));

      return res;
    }

    // The resource is not a persistent LR; use a constructor

    // create an object using the resource's default constructor
    try {
      if(DEBUG) Out.prln("Creating resource " + resClass.getName());
      res = resClass.newInstance();
    } catch(IllegalAccessException e) {
      throw new ResourceInstantiationException(
              "Couldn't create resource instance, access denied: " + e);
    } catch(InstantiationException e) {
      throw new ResourceInstantiationException(
              "Couldn't create resource instance due to newInstance() failure: "
                      + e);
    }

    if(LanguageResource.class.isAssignableFrom(resClass)) {
      // type-specific stuff for LRs
      if(DEBUG) Out.prln(resClass.getName() + " is a LR");
    } else if(ProcessingResource.class.isAssignableFrom(resClass)) {
      // type-specific stuff for PRs
      if(DEBUG) Out.prln(resClass.getName() + " is a PR");
      // set the runtime parameters to their defaults
      try {
        FeatureMap parameters = newFeatureMap();
        parameters.putAll(resData.getParameterList().getRuntimeDefaults());
        res.setParameterValues(parameters);
      } catch(ParameterException pe) {
        throw new ResourceInstantiationException(
                "Could not set the runtime parameters "
                        + "to their default values for: "
                        + res.getClass().getName() + " :\n" + pe.toString());
      }
      // type-specific stuff for VRs
    } else if(VisualResource.class.isAssignableFrom(resClass)) {
      if(DEBUG) Out.prln(resClass.getName() + " is a VR");
    } else if(Controller.class.isAssignableFrom(resClass)) {
      // type specific stuff for Controllers
      if(DEBUG) Out.prln(resClass.getName() + " is a Controller");
    }

    // set the parameterValues of the resource
    try {
      FeatureMap parameters = newFeatureMap();
      // put the defaults
      parameters.putAll(resData.getParameterList().getInitimeDefaults());
      // overwrite the defaults with the user provided values
      parameters.putAll(parameterValues);
      res.setParameterValues(parameters);
    } catch(ParameterException pe) {
      throw new ResourceInstantiationException(
              "Could not set the init parameters for: "
                      + res.getClass().getName() + " :\n" + pe.toString());
    }

    // set the name
    // if we have an explicitly provided name, use that, otherwise
    // generate a
    // suitable name if the resource doesn't already have one
    if(resourceName != null && resourceName.trim().length() > 0) {
      res.setName(resourceName);
    } else if(res.getName() == null) {
      // no name provided, and the resource doesn't have a name already
      // (e.g. calculated in init())
      // -> let's try and find a reasonable one
      try {
        // first try to get a filename from the various parameters
        URL sourceUrl = null;
        if(res instanceof SimpleDocument) {
          sourceUrl = ((SimpleDocument)res).getSourceUrl();
        } else if(res instanceof AnnotationSchema) {
          sourceUrl = ((AnnotationSchema)res).getXmlFileUrl().toURL();
        } else if(res.getClass().getName()
                .startsWith("gate.creole.ontology.owlim.")) {
          // get the name for the OWLIM2 ontology LR
          java.lang.reflect.Method m = resClass.getMethod("getRdfXmlURL");
          sourceUrl = (java.net.URL)m.invoke(res);
          if(sourceUrl == null) {
            m = resClass.getMethod("getN3URL");
            sourceUrl = (java.net.URL)m.invoke(res);
          }
          if(sourceUrl == null) {
            m = resClass.getMethod("getNtriplesURL");
            sourceUrl = (java.net.URL)m.invoke(res);
          }
          if(sourceUrl == null) {
            m = resClass.getMethod("getTurtleURL");
            sourceUrl = (java.net.URL)m.invoke(res);
          }
        } else if(res.getClass().getName()
                .startsWith("gate.creole.ontology.impl.")) {
          java.lang.reflect.Method m = resClass.getMethod("getSourceURL");
          sourceUrl = (java.net.URL)m.invoke(res);
        }
        if(sourceUrl != null) {
          URI sourceURI = sourceUrl.toURI();
          resourceName = sourceURI.getPath();
          if(resourceName == null ||
             resourceName.length() == 0 ||
             resourceName.equals("/")) {
            // this URI has no path -> use the whole string
            resourceName = sourceURI.toString();
          } else {
            // there is a significant path value -> get the last element
            resourceName = resourceName.trim();
            int lastSlash = resourceName.lastIndexOf('/');
            if(lastSlash >= 0) {
              String subStr = resourceName.substring(lastSlash + 1);
              if(subStr.trim().length() > 0) resourceName = subStr;
            }
          }
        }
      } catch(RuntimeException t) {
        // even runtime exceptions are safe to ignore at this point
      } catch(Exception t) {
        // there were problems while trying to guess a name
        // we can safely ignore them
      } finally {
        // make sure there is a name provided, whatever happened
        if(resourceName == null || resourceName.trim().length() == 0) {
          resourceName = resData.getName();
        }
      }
      resourceName += "_" + Gate.genSym();
      res.setName(resourceName);
    } // else if(res.getName() == null)
    // if res.getName() != null, leave it as it is

    Map<String, EventListener> listeners =
            new HashMap<String, EventListener>(gate.Gate.getListeners());
    // set the listeners if any
    if(!listeners.isEmpty()) {
      try {
        if(DEBUG) Out.prln("Setting the listeners for  " + res.toString());
        AbstractResource.setResourceListeners(res, listeners);
      } catch(Exception e) {
        if(DEBUG) Out.prln("Failed to set listeners for " + res.toString());
        throw new ResourceInstantiationException("Parameterisation failure" + e);
      }
    }

    try {
      // if the features of the resource have not been explicitly set,
      // set them to the features of the resource data
      if(res.getFeatures() == null || res.getFeatures().isEmpty()) {
        FeatureMap fm = newFeatureMap();
        fm.putAll(resData.getFeatures());
        res.setFeatures(fm);
      }
      // add the features specified by the user
      if(features != null) res.getFeatures().putAll(features);

      // initialise the resource
      if(DEBUG) Out.prln("Initialising resource " + res.toString());
      res = res.init();

    } finally {
      // remove the listeners if any
      if(!listeners.isEmpty()) {
        try {
          if(DEBUG) Out.prln("Removing the listeners for  " + res.toString());
          AbstractResource.removeResourceListeners(res, listeners);
        } catch(Exception e) {
          if(DEBUG)
            Out.prln("Failed to remove the listeners for " + res.toString());
          throw new ResourceInstantiationException("Parameterisation failure" + e);
        }
      }
    }
    // record the instantiation on the resource data's stack
    resData.addInstantiation(res);
    // fire the event
    creoleProxy.fireResourceLoaded(new CreoleEvent(res,
            CreoleEvent.RESOURCE_LOADED));
    return res;
  } // create(resourceClassName, parameterValues, features, listeners)

  /**
   * Delete an instance of a resource. This involves removing it from
   * the stack of instantiations maintained by this resource type's
   * resource data. Deletion does not guarantee that the resource will
   * become a candidate for garbage collection, just that the GATE
   * framework is no longer holding references to the resource.
   *
   * @param resource the resource to be deleted.
   */
  public static void deleteResource(Resource resource) {
    ResourceData rd =
            Gate.getCreoleRegister().get(resource.getClass().getName());
    if(rd != null && rd.removeInstantiation(resource)) {
      creoleProxy.fireResourceUnloaded(new CreoleEvent(resource,
              CreoleEvent.RESOURCE_UNLOADED));
      resource.cleanup();
    }
  } // deleteResource

  /** Create a new transient Corpus. */
  public static Corpus newCorpus(String name)
          throws ResourceInstantiationException {
    return (Corpus)createResource("gate.corpora.CorpusImpl", newFeatureMap(),
            newFeatureMap(), name);
  } // newCorpus

  /** Create a new transient Document from a URL. */
  public static Document newDocument(URL sourceUrl)
          throws ResourceInstantiationException {
    FeatureMap parameterValues = newFeatureMap();
    parameterValues.put(Document.DOCUMENT_URL_PARAMETER_NAME, sourceUrl);
    return (Document)createResource("gate.corpora.DocumentImpl",
            parameterValues);
  } // newDocument(URL)

  /** Create a new transient Document from a URL and an encoding. */
  public static Document newDocument(URL sourceUrl, String encoding)
          throws ResourceInstantiationException {
    FeatureMap parameterValues = newFeatureMap();
    parameterValues.put(Document.DOCUMENT_URL_PARAMETER_NAME, sourceUrl);
    parameterValues.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, encoding);
    return (Document)createResource("gate.corpora.DocumentImpl",
            parameterValues);
  } // newDocument(URL)

  /** Create a new transient textual Document from a string. */
  public static Document newDocument(String content)
          throws ResourceInstantiationException {
    FeatureMap params = newFeatureMap();
    params.put(Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME, content);
    Document doc =
            (Document)createResource("gate.corpora.DocumentImpl", params);
    /*
     * // laziness: should fit this into createResource by adding a new
     * // document parameter, but haven't time right now...
     * doc.setContent(new DocumentContentImpl(content));
     */
    // various classes are in the habit of assuming that a document
    // inevitably has a source URL... so give it a dummy one
    /*
     * try { doc.setSourceUrl(new URL("http://localhost/")); }
     * catch(MalformedURLException e) { throw new
     * ResourceInstantiationException(
     * "Couldn't create dummy URL in newDocument(String): " + e ); }
     */
    doc.setSourceUrl(null);
    return doc;
  } // newDocument(String)

  /**
   * Utility method to create an immutable annotation set. If the
   * provided collection of annotations is
   * <code>null</code>, the newly created set will
   * be empty.
   * 
   * @param document the document this set belongs to.
   * @param annotations the set of annotations that should be contained
   *          in the returned {@link AnnotationSet}.
   * @return an {@link AnnotationSet} that throws exceptions on all
   *         attempts to modify it.
   */
  public static AnnotationSet createImmutableAnnotationSet(Document document,
          Collection<Annotation> annotations) {
    return new ImmutableAnnotationSetImpl(document, annotations);
  }

  /**
   * <p>
   * Create a <i>duplicate</i> of the given resource. A duplicate is a
   * an independent copy of the resource that has the same name and the
   * same behaviour. It does <i>not necessarily</i> have the same
   * concrete class as the original, but if the original resource
   * implements any of the following interfaces then the duplicate can
   * be assumed to implement the same ones:
   * </p>
   * <ul>
   * <li>{@link ProcessingResource}</li>
   * <li>{@link LanguageAnalyser}</li>
   * <li>{@link Controller}</li>
   * <li>{@link CorpusController}</li>
   * <li>{@link ConditionalController}</li>
   * <li>{@code Gazetteer}</li>
   * <li>{@link LanguageResource}</li>
   * <li>{@link gate.creole.ontology.Ontology}</li>
   * <li>{@link Document}</li>
   * <li>{@link Corpus}</li>
   * </ul>
   * <p>
   * The default duplication algorithm simply calls
   * {@link #createResource(String, FeatureMap, FeatureMap, String)
   * createResource} with the type and name of the original resource,
   * and with parameters and features which are copies of those from the
   * original resource, but any Resource values in the maps will
   * themselves be duplicated. A context is passed around all the
   * duplicate calls that stem from the same call to this method so that
   * if the same resource is referred to in different places, the same
   * duplicate can be used in the corresponding places in the duplicated
   * object graph.
   * </p>
   * <p>
   * This default behaviour is sufficient for most resource types (and
   * is roughly the equivalent of saving the resource's state using the
   * persistence manager and then reloading it), but individual resource
   * classes can override it by implementing the
   * {@link CustomDuplication} interface. This may be necessary for
   * semantic reasons (e.g. controllers need to recursively duplicate
   * the PRs they contain), or desirable for performance or memory
   * consumption reasons (e.g. the behaviour of a DefaultGazetteer can
   * be duplicated by a SharedDefaultGazetteer that shares the internal
   * data structures).
   * </p>
   *
   * @param res the resource to duplicate
   * @return an independent duplicate copy of the resource
   * @throws ResourceInstantiationException if an exception occurs while
   *           constructing the duplicate.
   */
  public static Resource duplicate(Resource res)
          throws ResourceInstantiationException {
    DuplicationContext ctx = new DuplicationContext();
    try {
      return duplicate(res, ctx);
    } finally {
      // de-activate the context
      ctx.active = false;
    }
  }

  private static long dupIndex = 0;

  /**
   * Create a duplicate of the given resource, using the provided
   * context. This method is intended for use by resources that
   * implement the {@link CustomDuplication} interface when they need to
   * duplicate their child resources. Calls made to this method outside
   * the scope of such a {@link CustomDuplication#duplicate
   * CustomDuplication.duplicate} call will fail with a runtime
   * exception.
   *
   * @see #duplicate(Resource)
   * @param res the resource to duplicate
   * @param ctx the current context as passed to the
   *          {@link CustomDuplication#duplicate} method.
   * @return the duplicated resource
   * @throws ResourceInstantiationException if an error occurs while
   *           constructing the duplicate.
   */
  public static Resource duplicate(Resource res, DuplicationContext ctx)
          throws ResourceInstantiationException {
    long myDupIndex = -1, startTime = -1;
    if(DEBUG_DUPLICATION) {
      myDupIndex = dupIndex++;
      log.debug(myDupIndex + ": Duplicating \""
              + ((res == null) ? "null" : res.getName()) + "\" (a "
              + ((res == null) ? "null" : res.getClass().getName()) + ")");
      startTime = System.currentTimeMillis();
    }
    try {
      checkDuplicationContext(ctx);
      // check for null
      if(res == null) {
        return null;
      }
      // check if we've seen this resource before
      else if(ctx.knownResources.containsKey(res)) {
        if(DEBUG_DUPLICATION) {
          log.debug(myDupIndex + ": Resource already duplicated in context");
        }
        return ctx.knownResources.get(res);
      } else {
        // create the duplicate
        Resource newRes = null;
        if(res instanceof CustomDuplication) {
          // use custom duplicate if available
          newRes = ((CustomDuplication)res).duplicate(ctx);
        } else {
          newRes = defaultDuplicate(res, ctx);
        }
        // remember this duplicate in the context
        ctx.knownResources.put(res, newRes);
        return newRes;
      }
    } finally {
      if(DEBUG_DUPLICATION) {
        log.debug(myDupIndex + ": Duplication took "
                + (System.currentTimeMillis() - startTime) + " ms");
      }
    }
  }

  /**
   * Implementation of the default duplication algorithm described in
   * the comment for {@link #duplicate(Resource)}. This method is public
   * for the benefit of resources that implement
   * {@link CustomDuplication} but only need to do some post-processing
   * after the default duplication algorithm; they can call this method
   * to obtain an initial duplicate and then post-process it before
   * returning. If they need to duplicate child resources they should
   * call {@link #duplicate(Resource, DuplicationContext)} in the normal
   * way. Calls to this method made outside the context of a
   * {@link CustomDuplication#duplicate CustomDuplication.duplicate}
   * call will fail with a runtime exception.
   *
   * @param res the resource to duplicate
   * @param ctx the current context
   * @return a duplicate of the given resource, constructed using the
   *         default algorithm. In particular, if <code>res</code>
   *         implements {@link CustomDuplication} its own duplicate
   *         method will <i>not</i> be called.
   * @throws ResourceInstantiationException if an error occurs while
   *           duplicating the given resource.
   */
  public static Resource defaultDuplicate(Resource res, DuplicationContext ctx)
          throws ResourceInstantiationException {
    checkDuplicationContext(ctx);
    String className = res.getClass().getName();
    ResourceData resData = Gate.getCreoleRegister().get(className);
    if(resData == null) {
      throw new ResourceInstantiationException(
              "Could not find CREOLE data for " + className);
    }
    String resName = res.getName();

    FeatureMap newResFeatures = duplicate(res.getFeatures(), ctx);

    // init parameters
    FeatureMap initParams = AbstractResource.getInitParameterValues(res);
    // remove parameters that are also sharable properties
    for(String propName : resData.getSharableProperties()) {
      initParams.remove(propName);
    }
    // duplicate any Resources in the params map (excluding sharable
    // ones)
    initParams = duplicate(initParams, ctx);
    // add sharable properties to the params map (unduplicated). Some of
    // these
    // may be registered parameters but others may not be.
    for(String propName : resData.getSharableProperties()) {
      initParams.put(propName, res.getParameterValue(propName));
    }

    // create the new resource
    Resource newResource =
            createResource(className, initParams, newResFeatures, resName);
    if(newResource instanceof ProcessingResource) {
      // runtime params
      FeatureMap runtimeParams =
              AbstractProcessingResource.getRuntimeParameterValues(res);
      // remove parameters that are also sharable properties
      for(String propName : resData.getSharableProperties()) {
        runtimeParams.remove(propName);
      }
      // duplicate any Resources in the params map (excluding sharable
      // ones)
      runtimeParams = duplicate(runtimeParams, ctx);
      // do not need to add sharable properties here, they have already
      // been injected by createResource

      newResource.setParameterValues(runtimeParams);
    }

    return newResource;
  }

  /**
   * Construct a feature map that is a copy of the one provided except
   * that any {@link Resource} values in the map are replaced by their
   * duplicates. This method is public for the benefit of resources that
   * implement {@link CustomDuplication} and will fail if called outside
   * of a {@link CustomDuplication#duplicate
   * CustomDuplication.duplicate} implementation.
   *
   * @param fm the feature map to duplicate
   * @param ctx the current context
   * @return a duplicate feature map
   * @throws ResourceInstantiationException if an error occurs while
   *           duplicating any Resource in the feature map.
   */
  public static FeatureMap duplicate(FeatureMap fm, DuplicationContext ctx)
          throws ResourceInstantiationException {
    checkDuplicationContext(ctx);
    FeatureMap newFM = Factory.newFeatureMap();
    for(Map.Entry<Object, Object> entry : fm.entrySet()) {
      Object value = entry.getValue();
      if(value instanceof Resource) {
        value = duplicate((Resource)value, ctx);
      }
      newFM.put(entry.getKey(), value);
    }
    return newFM;
  }

  /**
   * Opaque memo object passed to {@link CustomDuplication#duplicate
   * CustomDuplication.duplicate} methods to encapsulate the state of
   * the current duplication run. If the duplicate method itself needs
   * to duplicate any objects it should pass this context back to
   * {@link #duplicate(Resource,DuplicationContext)}.
   */
  public static class DuplicationContext {
    IdentityHashMap<Resource, Resource> knownResources =
            new IdentityHashMap<Resource, Resource>();

    /**
     * Whether this duplication context is part of an active duplicate
     * call.
     */
    boolean active = true;

    /**
     * Overridden to ensure no public constructor.
     */
    DuplicationContext() {
    }
  }

  /**
   * Throws an exception if the specified duplication context is null or
   * not active. This is to ensure that the Factory helper methods that
   * take a DuplicationContext parameter can only be called in the
   * context of a {@link #duplicate(Resource)} call.
   * 
   * @param ctx the context to check.
   * @throws NullPointerException if the provided context is null.
   * @throws IllegalStateException if the provided context is not
   *           active.
   */
  protected static void checkDuplicationContext(DuplicationContext ctx) {
    if(ctx == null) {
      throw new NullPointerException("No DuplicationContext provided");
    }
    if(!ctx.active) {
      throw new IllegalStateException(
              new Throwable().getStackTrace()[1].getMethodName()
                      + " helper method called outside an active duplicate call");
    }
  }

  /** Create a new FeatureMap. */
  public static FeatureMap newFeatureMap() {
    return new SimpleFeatureMapImpl();
  } // newFeatureMap

  /** Open an existing DataStore. */
  public static DataStore openDataStore(String dataStoreClassName,
          String storageUrl) throws PersistenceException {
    DataStore ds = instantiateDataStore(dataStoreClassName, storageUrl);
    ds.open();
    if(Gate.getDataStoreRegister().add(ds))
      creoleProxy.fireDatastoreOpened(new CreoleEvent(ds,
              CreoleEvent.DATASTORE_OPENED));

    return ds;
  } // openDataStore()

  /**
   * Create a new DataStore and open it. <B>NOTE:</B> for some data
   * stores creation is an system administrator task; in such cases this
   * method will throw an UnsupportedOperationException.
   */
  public static DataStore createDataStore(String dataStoreClassName,
          String storageUrl) throws PersistenceException,
          UnsupportedOperationException {
    DataStore ds = instantiateDataStore(dataStoreClassName, storageUrl);
    ds.create();
    ds.open();
    if(Gate.getDataStoreRegister().add(ds))
      creoleProxy.fireDatastoreCreated(new CreoleEvent(ds,
              CreoleEvent.DATASTORE_CREATED));

    return ds;
  } // createDataStore()

  /** Instantiate a DataStore (not open or created). */
  protected static DataStore instantiateDataStore(String dataStoreClassName,
          String storageUrl) throws PersistenceException {
    DataStore godfreyTheDataStore = null;
    try {
      godfreyTheDataStore =
              (DataStore)Gate.getClassLoader().loadClass(dataStoreClassName)
                      .newInstance();
    } catch(Exception e) {
      throw new PersistenceException("Couldn't create DS class: " + e);
    }

    godfreyTheDataStore.setStorageUrl(storageUrl);

    return godfreyTheDataStore;
  } // instantiateDS(dataStoreClassName, storageURL)

  /** Add a listener */
  public static synchronized void addCreoleListener(CreoleListener l) {
    creoleProxy.addCreoleListener(l);
  } // addCreoleListener(CreoleListener)

  /** Static initialiser to set up the CreoleProxy event source object */
  static {
    creoleProxy = new CreoleProxy();
  } // static initialiser

} // abstract Factory

/**
 * Factory is basically a collection of static methods but events need
 * to have as source an object and not a class. The CreolProxy class
 * addresses this issue acting as source for all events fired by the
 * Factory class.
 */
class CreoleProxy {

  public synchronized void removeCreoleListener(CreoleListener l) {
    if(creoleListeners != null && creoleListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<CreoleListener> v =
              (Vector<CreoleListener>)creoleListeners.clone();
      v.removeElement(l);
      creoleListeners = v;
    }// if
  }// removeCreoleListener(CreoleListener l)

  public synchronized void addCreoleListener(CreoleListener l) {
    @SuppressWarnings("unchecked")
    Vector<CreoleListener> v =
            creoleListeners == null
                    ? new Vector<CreoleListener>(2)
                    : (Vector<CreoleListener>)creoleListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      creoleListeners = v;
    }// if
  }// addCreoleListener(CreoleListener l)

  protected void fireResourceLoaded(CreoleEvent e) {
    if(creoleListeners != null) {
      int count = creoleListeners.size();
      for(int i = 0; i < count; i++) {
        creoleListeners.elementAt(i).resourceLoaded(e);
      }// for
    }// if
  }// fireResourceLoaded(CreoleEvent e)

  protected void fireResourceUnloaded(CreoleEvent e) {
    if(creoleListeners != null) {
      int count = creoleListeners.size();
      for(int i = 0; i < count; i++) {
        creoleListeners.elementAt(i).resourceUnloaded(e);
      }// for
    }// if
  }// fireResourceUnloaded(CreoleEvent e)

  protected void fireDatastoreOpened(CreoleEvent e) {
    if(creoleListeners != null) {
      int count = creoleListeners.size();
      for(int i = 0; i < count; i++) {
        creoleListeners.elementAt(i).datastoreOpened(e);
      }// for
    }// if
  }// fireDatastoreOpened(CreoleEvent e)

  protected void fireDatastoreCreated(CreoleEvent e) {
    if(creoleListeners != null) {
      int count = creoleListeners.size();
      for(int i = 0; i < count; i++) {
        creoleListeners.elementAt(i).datastoreCreated(e);
      }// for
    }// if
  }// fireDatastoreCreated(CreoleEvent e)

  protected void fireDatastoreClosed(CreoleEvent e) {
    if(creoleListeners != null) {
      int count = creoleListeners.size();
      for(int i = 0; i < count; i++) {
        creoleListeners.elementAt(i).datastoreClosed(e);
      }// for
    }// if
  }// fireDatastoreClosed(CreoleEvent e)

  private transient Vector<CreoleListener> creoleListeners;
}// class CreoleProxy
