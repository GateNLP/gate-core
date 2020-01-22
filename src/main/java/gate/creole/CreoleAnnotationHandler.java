/*
 * CreoleAnnotationHandler.java
 * 
 * Copyright (c) 1995-2013, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Ian Roberts, 27/Jul/2008
 * 
 * $Id: CreoleAnnotationHandler.java 20058 2017-02-02 10:02:32Z markagreenwood $
 */

package gate.creole;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import gate.Gate;
import gate.Gate.ResourceInfo;
import gate.Resource;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.AutoInstanceParam;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;
import gate.creole.metadata.HiddenCreoleParameter;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.util.GateClassLoader;
import gate.util.GateException;

/**
 * Class to take a creole.xml file (as a JDOM tree) and add elements
 * corresponding to the CREOLE annotations on the RESOURCE classes it declares.
 */
public class CreoleAnnotationHandler {

  private static final Logger log = LoggerFactory
		  .getLogger(CreoleAnnotationHandler.class);

  //private URL creoleFileUrl;
  private Plugin plugin;

  /**
   * Create an annotation handler for the given plugin
   * 
   * @param plugin
   *          the plugin we are to process
   */
  public CreoleAnnotationHandler(Plugin plugin) {
    //this.creoleFileUrl = creoleFileUrl;
    this.plugin = plugin;
  }

  /**
   * Extract all JAR elements from the given JDOM document and add the jars they
   * reference to the GateClassLoader.
   * 
   * @param jdomDoc
   *          JDOM document representing a parsed creole.xml file.
   */
	public void addJarsToClassLoader(GateClassLoader gcl, Document jdomDoc) throws IOException {
		try {
			XPath jarXPath = XPath.newInstance("//*[translate(local-name(), 'ivy', 'IVY') = 'IVY']");
			if (jarXPath.selectNodes(jdomDoc).size() > 0) {
				throw new IOException("Using Ivy for dependency management is no longer supported");
			}
		} catch (JDOMException e) {
			throw new IOException("Unable to load plugin", e);
		}

		addJarsToClassLoader(gcl, jdomDoc.getRootElement());
	}

  /**
   * Extracts all the the REQUIRES elements from the given JDOM document,
   * expands the URLs and then attempts to load the specified plugin
   * 
   * @param jdomDoc JDOM document representing a parsed creole.xml file.
   */
  @Deprecated
  public void loadRequiredPlugins(Document jdomDoc) throws IOException {
    try {
      XPath jarXPath =
          XPath
              .newInstance("//*[translate(local-name(), 'requires', 'REQUIRES') = 'REQUIRES']");
      @SuppressWarnings("unchecked")
      List<Element> requires = jarXPath.selectNodes(jdomDoc);

      String relativePathMarker = "$relpath$";
      String gatehomePathMarker = "$gatehome$";
      String gatepluginsPathMarker = "$gateplugins$";

      for(Element required : requires) {
        URL url = null;
        String urlString = required.getTextTrim();
        if(urlString.startsWith(relativePathMarker)) {
          url =
              new URL(plugin.getBaseURL(), urlString.substring(relativePathMarker
                  .length()));
        } else if(urlString.startsWith(gatehomePathMarker)) {
          URL gatehome = Gate.getGateHome().toURI().toURL();
          url =
              new URL(gatehome,
                  urlString.substring(gatehomePathMarker.length()));
        } else if(urlString.startsWith(gatepluginsPathMarker)) {
          URL gateplugins = Gate.getPluginsHome().toURI().toURL();
          url =
              new URL(gateplugins, urlString.substring(gatepluginsPathMarker
                  .length()));
        } else {
          url = new URL(plugin.getBaseURL(), urlString);
        }

        Gate.getCreoleRegister().registerPlugin(new Plugin.Directory(url));
      }
    } catch(GateException | JDOMException e) {
      throw new IOException("Unable to load required plugin",e);
    }
  }

  /**
   * Recursively search the given element for JAR entries and add these jars to
   * the GateClassLoader
   * 
   * @param jdomElt
   *          JDOM element representing a creole.xml file
   */
  @SuppressWarnings("unchecked")
  private void addJarsToClassLoader(GateClassLoader gcl, Element jdomElt)
      throws MalformedURLException {
    if("JAR".equals(jdomElt.getName())) {
      URL url = new URL(plugin.getBaseURL(), jdomElt.getTextTrim());
      try {
        java.io.InputStream s = url.openStream();
        s.close();
        gcl.addURL(url);
      } catch(IOException e) {
        log.debug("Unable to add JAR "
            + url
            + " specified in creole file "
            + " to class loader, hopefully the required classes are already on the classpath.");
      }
    } else {
      for(Element child : (List<Element>)jdomElt.getChildren()) {
        addJarsToClassLoader(gcl, child);
      }
    }
  }

  /**
   * Fetches the directory information for this handler's creole plugin and adds
   * additional RESOURCE elements to the given JDOM document so that it contains
   * a RESOURCE for every resource type defined in the plugin's directory info.
   * 
   * @param jdomDoc
   *          JDOM document which should be the parsed creole.xml that this
   *          handler was configured for.
   */
  public void createResourceElementsForDirInfo(Document jdomDoc)
      throws MalformedURLException {
    Element jdomElt = jdomDoc.getRootElement();
    //URL directoryUrl = new URL(creoleFileUrl, ".");
    //DirectoryInfo dirInfo = Gate.getDirectoryInfo(directoryUrl,jdomDoc);
    //if(dirInfo != null) {
      Map<String, Element> resourceElements = new HashMap<String, Element>();
      findResourceElements(resourceElements, jdomElt);
      for(ResourceInfo resInfo : plugin
          .getResourceInfoList()) {
        if(!resourceElements.containsKey(resInfo.getResourceClassName())) {
          // no existing RESOURCE element for this resource type (so it
          // was
          // auto-discovered from a <JAR SCAN="true">), so add a minimal
          // RESOURCE element which will be filled in by the annotation
          // processor.
          jdomElt.addContent(new Element("RESOURCE").addContent(new Element(
              "CLASS").setText(resInfo.getResourceClassName())));
        }
      }
    //}

  }

  @SuppressWarnings("unchecked")
  private void findResourceElements(Map<String, Element> map, Element elt) {
    if(elt.getName().equals("RESOURCE")) {
      String className = elt.getChildTextTrim("CLASS");
      if(className != null) {
        map.put(className, elt);
      }
    } else {
      for(Element child : (List<Element>)elt.getChildren()) {
        findResourceElements(map, child);
      }
    }
  }

  /**
   * Processes annotations for resource classes named in the given creole.xml
   * document, adding the relevant XML elements to the document as appropriate.
   * 
   * @param jdomDoc
   *          the parsed creole.xml file
   */
  public void processAnnotations(Document jdomDoc) throws GateException {
    processAnnotations(jdomDoc.getRootElement());
  }

  /**
   * Process annotations for the given element. If the element is a RESOURCE it
   * is processed, otherwise the method calls itself recursively for all the
   * children of the given element.
   * 
   * @param element
   *          the element to process.
   */
  @SuppressWarnings("unchecked")
  private void processAnnotations(Element element) throws GateException {
    if("RESOURCE".equals(element.getName())) {
      processAnnotationsForResource(element);
    } else {
      for(Element child : (List<Element>)element.getChildren()) {
        processAnnotations(child);
      }
    }
  }

  /**
   * Process the given RESOURCE element, adding extra elements to it based on
   * the annotations on the resource class.
   * 
   * @param element
   *          the RESOURCE element to process.
   */
  private void processAnnotationsForResource(Element element)
      throws GateException {
    String className = element.getChildTextTrim("CLASS");
    if(className == null) { throw new GateException(
        "\"CLASS\" element not found for resource in " + plugin); }
    Class<?> resourceClass = null;
    try {
      resourceClass = Gate.getClassLoader().loadClass(className);
    } catch(ClassNotFoundException e) {
      log.debug("Couldn't load class " + className + " for resource in "
          + plugin, e);
      throw new GateException("Couldn't load class " + className
          + " for resource in " + plugin);
    }

    processCreoleResourceAnnotations(element, resourceClass);
  }

  @SuppressWarnings("unchecked")
  public void processCreoleResourceAnnotations(Element element,
      Class<?> resourceClass) throws GateException {
    CreoleResource creoleResourceAnnot =
        resourceClass.getAnnotation(CreoleResource.class);
    if(creoleResourceAnnot != null) {
      // first process the top-level data and autoinstances
      processResourceData(resourceClass, element);

      // now deal with parameters
      // build a map holding the element corresponding to each parameter
      Map<String, Element> parameterMap = new HashMap<String, Element>();
      Map<String, Element> disjunctionMap = new HashMap<String, Element>();
      for(Element paramElt : (List<Element>)element.getChildren("PARAMETER")) {
        parameterMap.put(paramElt.getAttributeValue("NAME"), paramElt);
      }
      for(Element disjunctionElt : (List<Element>)element.getChildren("OR")) {
        String disjunctionId = disjunctionElt.getAttributeValue("ID");
        if(disjunctionId != null) {
          disjunctionMap.put(disjunctionId, disjunctionElt);
        }
        for(Element paramElt : (List<Element>)disjunctionElt
            .getChildren("PARAMETER")) {
          parameterMap.put(paramElt.getAttributeValue("NAME"), paramElt);
        }
      }

      processParameters(resourceClass, element, parameterMap, disjunctionMap);
    }
  }

  /**
   * Process the {@link CreoleResource} data for this class. This method first
   * extracts the non-inheritable data (PRIVATE, MAIN_VIEWER, NAME and TOOL),
   * then calls {@link #processInheritableResourceData} to process the
   * inheritable data, then deals with any specified {@link AutoInstance}s.
   * 
   * @param resourceClass
   *          the resource class to process, which must be annotated with
   *          {@link CreoleResource}.
   * @param element
   *          the RESOURCE element to which data should be added.
   */
  private void processResourceData(Class<?> resourceClass, Element element) {
    CreoleResource cr = resourceClass.getAnnotation(CreoleResource.class);
    if(cr.isPrivate() && element.getChild("PRIVATE") == null) {
      element.addContent(new Element("PRIVATE"));
    }
    if(cr.mainViewer() && element.getChild("MAIN_VIEWER") == null) {
      element.addContent(new Element("MAIN_VIEWER"));
    }
    if(cr.tool() && element.getChild("TOOL") == null) {
      element.addContent(new Element("TOOL"));
    }
    // NAME is the name given in the annotation, or the simple name of
    // the class if omitted
    addElement(element, ("".equals(cr.name()))
        ? resourceClass.getSimpleName()
        : cr.name(), "NAME");
    processInheritableResourceData(resourceClass, element);
    processAutoInstances(cr, element);
  }

  /**
   * Recursive method to process the {@link CreoleResource} elements that can be
   * inherited from superclasses and interfaces (everything except the PRIVATE
   * and MAIN_VIEWER flags, the NAME and the AUTOINSTANCEs). Once data has been
   * extracted from the current class the method calls itself recursively for
   * the superclass and any implemented interfaces. For any given attribute, the
   * first value specified wins (i.e. the one on the most specific class).
   * 
   * @param clazz
   *          the class to process
   * @param element
   *          the RESOURCE element to which data should be added.
   */
  private void processInheritableResourceData(Class<?> clazz, Element element) {
    CreoleResource cr = clazz.getAnnotation(CreoleResource.class);
    if(cr != null) {
      addElement(element, cr.comment(), "COMMENT");
      addElement(element, cr.helpURL(), "HELPURL");
      addElement(element, cr.interfaceName(), "INTERFACE");
      addElement(element, cr.icon(), "ICON");
      if(cr.guiType() != GuiType.NONE && element.getChild("GUI") == null) {
        Element guiElement =
            new Element("GUI").setAttribute("TYPE", cr.guiType().toString());
        element.addContent(guiElement);
        addElement(guiElement, cr.resourceDisplayed(), "RESOURCE_DISPLAYED");
      }
      addElement(element, cr.annotationTypeDisplayed(),
          "ANNOTATION_TYPE_DISPLAYED");
    }

    Class<?> superclass = clazz.getSuperclass();
    if(superclass != null) {
      processInheritableResourceData(superclass, element);
    }

    for(Class<?> intf : clazz.getInterfaces()) {
      processInheritableResourceData(intf, element);
    }
  }

  /**
   * Adds an element with the given name and text value to the parent element,
   * but only if no such child element already exists and the value is not the
   * empty string.
   * 
   * @param parent
   *          the parent element
   * @param value
   *          the text value for the new child
   * @param elementName
   *          the name of the new child element
   */
  private void addElement(Element parent, String value, String elementName) {
    if(!"".equals(value) && parent.getChild(elementName) == null) {
      parent.addContent(new Element(elementName).setText(value));
    }
  }

  /**
   * Process the {@link AutoInstance} annotations contained in the given
   * {@link CreoleResource} and add the corresponding
   * AUTOINSTANCE/HIDDEN-AUTOINSTANCE elements to the given parent.
   * 
   * @param cr
   *          the {@link CreoleResource} annotation
   * @param element
   *          the parent element
   */
  private void processAutoInstances(CreoleResource cr, Element element) {
    for(AutoInstance ai : cr.autoinstances()) {
      Element aiElt = null;
      if(ai.hidden()) {
        aiElt = new Element("HIDDEN-AUTOINSTANCE");
      } else {
        aiElt = new Element("AUTOINSTANCE");
      }
      element.addContent(aiElt);
      for(AutoInstanceParam param : ai.parameters()) {
        aiElt.addContent(new Element("PARAM")
            .setAttribute("NAME", param.name()).setAttribute("VALUE",
                param.value()));
      }
    }
  }

  /**
   * Process any {@link CreoleParameter} and {@link HiddenCreoleParameter}
   * annotations on set methods of the given class and set up the corresponding
   * PARAMETER elements.
   * 
   * @param resourceClass
   *          the resource class to process
   * @param resourceElement
   *          the RESOURCE element to which the PARAMETERs are to be added
   * @param parameterMap
   *          a map from parameter names to the PARAMETER elements that define
   *          them. This is used as we combine information from the original
   *          creole.xml, the parameter annotation on the target method and the
   *          annotations on the same method of its superclasses and interfaces.
   *          Parameter names that have been hidden by a
   *          {@link HiddenCreoleParameter} annotation are explicitly mapped to
   *          <code>null</code> in this map.
   * @param disjunctionMap
   *          a map from disjunction IDs to the OR elements that define them.
   *          Disjunctive parameters are handled by specifying a disjunction ID
   *          on the {@link CreoleParameter} annotations - parameters with the
   *          same disjunction ID are grouped under the same OR element.
   */
  private void processParameters(Class<?> resourceClass,
      Element resourceElement, Map<String, Element> parameterMap,
      Map<String, Element> disjunctionMap) throws GateException {

    BeanInfo bi;
    try {
      bi = Introspector.getBeanInfo(resourceClass);
    } catch(IntrospectionException e) {
      throw new GateException("Failed to introspect " + resourceClass, e);
    }

    for(Method method : resourceClass.getDeclaredMethods()) {
      processElement(method, bi, resourceElement, parameterMap, disjunctionMap);
    }

    for(Field field : resourceClass.getDeclaredFields()) {
      processElement(field, bi, resourceElement, parameterMap, disjunctionMap);
    }

    // go up the tree
    Class<?> superclass = resourceClass.getSuperclass();
    if(superclass != null) {
      processParameters(superclass, resourceElement, parameterMap,
          disjunctionMap);
    }

    for(Class<?> intf : resourceClass.getInterfaces()) {
      processParameters(intf, resourceElement, parameterMap, disjunctionMap);
    }
  }

  /**
   * Processes parameter annotations on a single element. Can support both Field
   * and Method elements.
   * 
   * @param element
   *          The Method of Field from which to discern parameters
   * @param bi
   *          the BeanInfo for the corresponding class.
   * @param resourceElement
   *          the RESOURCE element to which the PARAMETERs are to be added
   * @param parameterMap
   *          a map from parameter names to the PARAMETER elements that define
   *          them. This is used as we combine information from the original
   *          creole.xml, the parameter annotation on the target method and the
   *          annotations on the same method of its superclasses and interfaces.
   *          Parameter names that have been hidden by a
   *          {@link HiddenCreoleParameter} annotation are explicitly mapped to
   *          <code>null</code> in this map.
   * @param disjunctionMap
   *          a map from disjunction IDs to the OR elements that define them.
   *          Disjunctive parameters are handled by specifying a disjunction ID
   *          on the {@link CreoleParameter} annotations - parameters with the
   *          same disjunction ID are grouped under the same OR element.
   */
  private void processElement(AnnotatedElement element, BeanInfo bi,
      Element resourceElement, Map<String, Element> parameterMap,
      Map<String, Element> disjunctionMap) throws GateException {
    CreoleParameter paramAnnot = element.getAnnotation(CreoleParameter.class);
    HiddenCreoleParameter hiddenParamAnnot =
        element.getAnnotation(HiddenCreoleParameter.class);
    String paramName; // Extracted name of this parameter
    Class<?> paramType;
    Type genericParamType; // The type of this parameter.
    if(paramAnnot != null || hiddenParamAnnot != null) {
      // Enforce constraints relevant for this type of element
      if(element.getClass().equals(java.lang.reflect.Field.class)) {
        java.lang.reflect.Field field = (java.lang.reflect.Field)element;
        paramName = field.getName();
        genericParamType = field.getGenericType();
        paramType = field.getType();
        PropertyDescriptor paramDescriptor = null;
        for(PropertyDescriptor pd : bi.getPropertyDescriptors()) {
          if(paramName.equals(pd.getName())) {
            paramDescriptor = pd;
            break;
          }
        }

        if(paramDescriptor == null) {
          throw new GateException("CREOLE parameter annotation found on field "
              + field
              + " but no corresponding JavaBean accessor methods exist.");
        } else if(paramDescriptor.getReadMethod() == null
            || paramDescriptor.getWriteMethod() == null) { throw new GateException(
            "CREOLE parameter annotation found on field "
                + field
                + " but getter or setter is missing.  CREOLE parameters require both."); }
      } else if(element.getClass().equals(Method.class)) {
        Method method = (Method)element;
        // Extract the parameter name from the BeanInfo
        PropertyDescriptor paramDescriptor = null;
        for(PropertyDescriptor pd : bi.getPropertyDescriptors()) {
          if(method.equals(pd.getWriteMethod())) {
            paramDescriptor = pd;
            break;
          }
        }

        if(paramDescriptor == null) { throw new GateException(
            "CREOLE parameter annotation found on " + method
                + " but this method is not a Java Bean property setter."); }
        paramName = paramDescriptor.getName();
        // And the type is that of the first argument
        genericParamType = method.getGenericParameterTypes()[0];
        paramType = method.getParameterTypes()[0];
      } else {
        throw new GateException("CREOLE parameter annotation found on "
            + element + " but can only be placed on Method or Field");
      }

      // Hidden parameters can be added straight to the map.
      if(hiddenParamAnnot != null && !parameterMap.containsKey(paramName)) {
        parameterMap.put(paramName, null);
      }

      // Visible parameters need converting to JDOM Elements
      if(paramAnnot != null) {
        Element paramElt = null;
        if(parameterMap.containsKey(paramName)) {
          // Use existing annotation if there is such a thing.
          paramElt = parameterMap.get(paramName);
        } else {
          // Otherwise create on - type depends on whether it is disjunctive or
          // not.
          paramElt = new Element("PARAMETER").setAttribute("NAME", paramName);

          if(!"".equals(paramAnnot.disjunction())) {
            // Disjunctive parameters (cannot both be set) need special markup.
            Element disjunctionElt =
                disjunctionMap.get(paramAnnot.disjunction());
            if(disjunctionElt == null) {
              disjunctionElt = new Element("OR");
              resourceElement.addContent(disjunctionElt);
              disjunctionMap.put(paramAnnot.disjunction(), disjunctionElt);
            }
            disjunctionElt.addContent(paramElt);
          } else {
            resourceElement.addContent(paramElt);
          }
          parameterMap.put(paramName, paramElt);
        }

        if(paramElt != null) {
          // paramElt is a valid element for the current parameter,
          // which has not been masked by a @HiddenCreoleParameter
          if(paramElt.getTextTrim().length() == 0) {
            // The text of the element should be the the type
            paramElt.setText(paramType.getName());
            // for collections (but not GATE Resources) we also try to determine
            // the item type.
            if((!Resource.class.isAssignableFrom(paramType))
                && Collection.class.isAssignableFrom(paramType)) {
              determineCollectionElementType(element, genericParamType,
                  paramElt);
            }
          }

          // other attributes
          addAttribute(paramElt, paramAnnot.comment(), "", "COMMENT");
          addAttribute(paramElt, paramAnnot.suffixes(), "", "SUFFIXES");
          addAttribute(paramElt, paramAnnot.defaultValue(),
              CreoleParameter.NO_DEFAULT_VALUE, "DEFAULT");
          addAttribute(paramElt, String.valueOf(paramAnnot.priority()),
              String.valueOf(CreoleParameter.DEFAULT_PRIORITY), "PRIORITY");

          // runtime and optional are based on marker annotations
          String runtimeParam = "";
          if(element.isAnnotationPresent(RunTime.class)) {
            runtimeParam =
                String.valueOf(element.getAnnotation(RunTime.class).value());
          }
          addAttribute(paramElt, runtimeParam, "", "RUNTIME");

          String optionalParam = "";
          if(element.isAnnotationPresent(Optional.class)) {
            optionalParam =
                String.valueOf(element.getAnnotation(Optional.class).value());
          }
          addAttribute(paramElt, optionalParam, "", "OPTIONAL");
        }
      }
    }
  }

  /**
   * Given a single-argument method whose parameter is a {@link Collection}, use
   * the method's generic type information to determine the collection element
   * type and store it as the ITEM_CLASS_NAME attribute of the given Element.
   * 
   * @param method
   *          the setter method
   * @param paramElt
   *          the PARAMETER element
   */
  private void determineCollectionElementType(AnnotatedElement method,
      Type paramType, Element paramElt) {
    if(paramElt.getAttributeValue("ITEM_CLASS_NAME") == null) {
      Class<?> elementType;
      CreoleParameter paramAnnot = method.getAnnotation(CreoleParameter.class);
      if(paramAnnot != null
          && paramAnnot.collectionElementType() != CreoleParameter.NoElementType.class) {
        elementType = paramAnnot.collectionElementType();
      } else {
        elementType = findCollectionElementType(paramType);
      }
      if(elementType != null) {
        paramElt.setAttribute("ITEM_CLASS_NAME", elementType.getName());
      }
    }
  }

  /**
   * Find the collection element type for the given type.
   * 
   * @param type
   *          the type to use. To be able to find the element type, this must be
   *          a Class that is assignable from Collection or a ParameterizedType
   *          whose raw type is assignable from Collection.
   * @return the Class representing the collection element type, or
   *         <code>null</code> if this cannot be determined
   */
  private Class<?> findCollectionElementType(Type type) {
    return findCollectionElementType(type,
        new HashMap<TypeVariable<?>, Class<?>>());
  }

  /**
   * Recursive method to find the collection element type for the given type.
   * 
   * @param type
   *          the type to use
   * @param tvMap
   *          map from type variables to the classes they are ultimately bound
   *          to. The reflection APIs can tell us that List&lt;String&gt; is an
   *          instantiation of List&lt;X&gt; and List&lt;X&gt; implements
   *          Collection&lt;X&gt;, but we have to keep track of the fact that X
   *          maps to String ourselves.
   * @return the collection element type, or <code>null</code> if it cannot be
   *         determined.
   */
  private Class<?> findCollectionElementType(Type type,
      Map<TypeVariable<?>, Class<?>> tvMap) {
    Class<?> rawClass = null;
    if(type instanceof Class) {
      // we have a non-parameterized type, but it might be a raw class
      // that extends a parameterized one (e.g. CustomCollection extends
      // Set<Integer>) so we still need to look up
      // the class tree
      rawClass = (Class<?>)type;
    } else if(type instanceof ParameterizedType) {
      Type rawType = ((ParameterizedType)type).getRawType();
      // if we've reached Collection<T>, look at the tvMap to find what
      // T maps to and return that
      if(rawType == Collection.class) {
        Type collectionTypeArgument =
            ((ParameterizedType)type).getActualTypeArguments()[0];
        if(collectionTypeArgument instanceof Class<?>) {
          // e.g. Collection<String>
          return (Class<?>)collectionTypeArgument;
        } else if(collectionTypeArgument instanceof TypeVariable<?>) {
          // e.g. Collection<X>
          return tvMap.get(collectionTypeArgument);
        } else {
          // e.g. Collection<? extends Widget> or Collection<T[]>- we
          // can't handle this in creole.xml so give up
          return null;
        }
      }

      // we haven't reached Collection here, so add the type variable
      // mappings to the tvMap before we look up the tree
      if(rawType instanceof Class) {
        rawClass = (Class<?>)rawType;
        Type[] actualTypeParams =
            ((ParameterizedType)type).getActualTypeArguments();
        TypeVariable<?>[] formalTypeParams =
            ((Class<?>)rawType).getTypeParameters();
        for(int i = 0; i < actualTypeParams.length; i++) {
          if(actualTypeParams[i] instanceof Class) {
            tvMap.put(formalTypeParams[i], (Class<?>)actualTypeParams[i]);
          } else if(actualTypeParams[i] instanceof TypeVariable) {
            tvMap.put(formalTypeParams[i], tvMap.get(actualTypeParams[i]));
          }
        }
      }
    }

    // process the superclass, if there is one, and any implemented
    // interfaces
    if(rawClass != null) {
      Type superclass = rawClass.getGenericSuperclass();
      if(type != null) {
        Class<?> componentType = findCollectionElementType(superclass, tvMap);
        if(componentType != null) { return componentType; }
      }

      for(Type intf : rawClass.getGenericInterfaces()) {
        Class<?> componentType = findCollectionElementType(intf, tvMap);
        if(componentType != null) { return componentType; }
      }
    }

    return null;
  }

  /**
   * Add an attribute with the given value to the given element, but only if the
   * element does not already have the attribute, and the value is not equal to
   * the given default value.
   * 
   * @param paramElt
   *          the element
   * @param value
   *          the attribute value (which will be converted to a string)
   * @param defaultValue
   *          if value.equals(defaultValue) we do not add the attribute.
   * @param attrName
   *          the name of the attribute to add.
   */
  private void addAttribute(Element paramElt, Object value,
      Object defaultValue, String attrName) {
    if(!defaultValue.equals(value) && paramElt.getAttribute(attrName) == null) {
      paramElt.setAttribute(attrName, value.toString());
    }
  }
}
