/*
 *  ResourceData.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 1/Sept/2000
 *
 *  $Id: ResourceData.java 19663 2016-10-10 08:44:57Z markagreenwood $
 */

package gate.creole;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import gate.CreoleRegister;
import gate.DocumentExporter;
import gate.Gate;
import gate.LanguageResource;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.metadata.Sharable;
import gate.util.AbstractFeatureBearer;
import gate.util.GateException;
import gate.util.GateRuntimeException;

/** Models an individual CREOLE resource metadata, plus configuration data,
  * plus the instantiations of the resource current within the system.
  * Some metadata elements are used by GATE to load resources, or index
  * the members of the CREOLE register; some are used during resource
  * parameterisation and initialisation.
  * Metadata elements which are used by the CREOLE registration and loading
  * mechanisms are properties of ResourceData implementations and have their
  * own get/set methods. Other metadata elements are made features of the
  * ResourceData. So, for example, if you add an element "FunkyElementThaing"
  * to the metadata of a resource, this will be made a feature of that
  * resource's ResourceData.
  * @see CreoleRegister
  */
public class ResourceData extends AbstractFeatureBearer
{
  private static final long serialVersionUID = -1275311260404979762L;

  /** Debug flag */
  protected static final boolean DEBUG = false;

  protected static final String DEFAULT_LR_ICON = "lr";
  protected static final String DEFAULT_PR_ICON = "pr";
  protected static final String DEFAULT_EXPORTER_ICON = "DocumentExporter";
  protected static final String DEFAULT_OTHER_ICON = "application";
  /** Construction */
  public ResourceData() {  }// ResourceData
  
  private int refCount = 1;
  
  public int getReferenceCount() {
    return refCount;
  }
  
  public int reduceReferenceCount() {
    return --refCount;
  }
  
  public int increaseReferenceCount() {
    return ++refCount;
  }

  /** String representation */
  @Override
  public String toString() {
    int noInst = (instantiationStack == null) ? 0: instantiationStack.size();
/*
    int noSmallViews = (smallViews == null) ? 0: smallViews.size();
    int noViews = (views == null) ? 0: views.size();
*/
    return
      "ResourceDataImpl, name=" + name + "; className=" + className +
      "; jarFileName=" + jarFileName + "; jarFileUrl=" + jarFileUrl +
      "; xmlFileName=" + xmlFileName + "; xmlFileUrl=" + xmlFileUrl +
      "; isAutoLoading=" + autoLoading + "; numberInstances=" + noInst +
      "; isPrivate=" + priv +"; isTool="+ tool +
      "; validityMessage=" + validityMessage +
      "; interfaceName=" + interfaceName +
      "; guiType=" + guiType +
      "; mainViewer=" + isMainView +
      "; resourceDisplayed=" + resourceDisplayed +
      "; annotationTypeDisplayed=" + annotationTypeDisplayed +
      "; parameterList=" + parameterList +
      "; features=" + features
    ;
    
  } // toString

  /** Equality: two resource data objects are the same if they have the
    * same name
    */
  @Override
  public boolean equals(Object other) {
    if(this == other) return true;
    if(other == null) return false;
    if(getClass() != other.getClass()) return false;
    if(name.equals(((ResourceData) other).getName()))
      return true;
    return false;
  } // equals

  /** Hashing, based on the name field of the object */
  @Override
  public int hashCode() {
    return name.hashCode();
  } // hashCode

  /** The name of the resource */
  protected String name;

  /** Set method for the resource name */
  public void setName(String name) { this.name = name; }

  /** Get method for the resource name */
  public String getName() { return name; }

  /** Location of an icon for the resource */
  protected String icon;

  /** Set method for the resource icon */
  public void setIcon(String icon) { this.icon = icon; }

  /** Get method for the resource icon */
  public String getIcon() {
    //if icon not set try and guess it
    if(icon == null){
      icon = guessIcon();
    }
    return icon;
  }

  /**
   * Makes the best attempt of guessing an appropriate icon for this resource
   * type based on whether it is a Language Resource, a Processing Resource, or
   * something else.
   * @return a String representing the file name for most appropriate icon for
   * this resource type.
   */
  protected String guessIcon(){
    //if no class set we can't do any guessing
    if(className == null) return DEFAULT_OTHER_ICON;
    if(resourceClass == null) return DEFAULT_OTHER_ICON;
    if(LanguageResource.class.isAssignableFrom(resourceClass))
      return DEFAULT_LR_ICON;
    if(ProcessingResource.class.isAssignableFrom(resourceClass))
      return DEFAULT_PR_ICON;
    if (DocumentExporter.class.isAssignableFrom(resourceClass))
      return DEFAULT_EXPORTER_ICON;
    
    return DEFAULT_OTHER_ICON;
  }

  /** The stack of instantiations */
  protected List<Resource> instantiationStack = new CopyOnWriteArrayList<Resource>();

  /**
   * Unmodifiable view of the instantiation stack, returned by
   * getInstantiations to ensure that the only way to modify the list is
   * through the add/removeInstantiation methods of this class.
   */
  protected List<Resource> unmodifiableInstantiationStack =
          Collections.unmodifiableList(instantiationStack);

  /** Get the list of instantiations of resources */
  public List<Resource> getInstantiations() {
    return unmodifiableInstantiationStack;
  } // getInstantiations

  /** Add an instantiation of the resource to the register of these */
  public void addInstantiation(Resource resource) {
    instantiationStack.add(0, resource);
  } // addInstantiation

  /**
   * Remove an instantiation of the resource from the register of these.
   * @return true if the given instance was contained in the register,
   *         false otherwise (i.e. the instance had already been removed).
   */
  public boolean removeInstantiation(Resource resource) {
    return instantiationStack.remove(resource);
    //persistantInstantiationList.remove(resource);
  } // removeInstantiation

  /** The class name of the resource */
  protected String className;

  /** Set method for the resource class name */
  public void setClassName(String className) { this.className = className; }

  /** Get method for the resource class name */
  public String getClassName() { return className; }

  /** The interface name of the resource */
  protected String interfaceName;

  /** Set method for the resource interface name */
  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  } // setInterfaceName

  /** Get method for the resource interface name */
  public String getInterfaceName() { return interfaceName; }

  /** The class of the resource */
  protected Class<? extends Resource> resourceClass;

  /** Set method for the resource class */
  public void setResourceClass(Class<? extends Resource> resourceClass) {
    this.resourceClass = resourceClass;
  } // setResourceClass

  /** Get method for the resource class. Asks the GATE class loader
    * to load it, if it is not already present.
    */
  public Class<? extends Resource> getResourceClass() throws ClassNotFoundException {
    if(resourceClass == null) {
      resourceClass = getResourceClassLoader().loadClass(className).asSubclass(Resource.class);
    }

    return resourceClass;
  } // getResourceClass
  
  public ClassLoader getResourceClassLoader() {
    try {
      return Gate.getClassLoader().getDisposableClassLoader(plugin.getBaseURI().toString());
    } catch(URISyntaxException e) {
      // should never be able to happen
      throw new GateRuntimeException("Can't get plugin base URI", e);
    }
  }

  /** The jar file name of the resource */
  protected String jarFileName;

  /** Set method for the resource jar file name */
  public void setJarFileName(String jarFileName) {
    this.jarFileName = jarFileName;
  } // setJarFileName

  /** Get method for the resource jar file name */
  public String getJarFileName() { return jarFileName; }

  /** The jar file URL of the resource */
  protected URL jarFileUrl;

  /** Set method for the resource jar file URL */
  public void setJarFileUrl(URL jarFileUrl) { this.jarFileUrl = jarFileUrl; }

  /** Get method for the resource jar file URL */
  public URL getJarFileUrl() { return jarFileUrl; }

  /** The xml file name of the resource */
  protected String xmlFileName;

  /** The xml file URL of the resource */
  protected URL xmlFileUrl;

  /** Set the URL to the creole.xml file that defines this resource */
  public void setXmlFileUrl(URL xmlFileUrl) { this.xmlFileUrl = xmlFileUrl; }

  /** Get the URL to the creole.xml file that defines this resource */
  public URL getXmlFileUrl() { return xmlFileUrl; }

  /** The plugin defining this resource type */
  protected Plugin plugin;

  public Plugin getPlugin() {
    return plugin;
  }

  public void setPlugin(Plugin plugin) {
    this.plugin = plugin;
  }

  /** The comment string */
  protected String comment;

  /** Get method for the resource comment */
  public String getComment() { return comment; }

  /** Set method for the resource comment */
  public void setComment(String comment) { this.comment = comment; }

  /** The helpURL string */
  protected String helpURL;

  /** Get method for the resource helpURL */
  public String getHelpURL() { return helpURL; }

  /** Set method for the resource helpURL */
  public void setHelpURL(String helpURL) { this.helpURL = helpURL; }

  /** The set of parameter lists */
  protected ParameterList parameterList = new ParameterList();

  /** Set the parameter list */
  public void setParameterList(ParameterList parameterList) {
    this.parameterList = parameterList;
  } // addParameterList

  /** Get the parameter list */
  public ParameterList getParameterList() { return parameterList; }

  /** Autoloading flag */
  protected boolean autoLoading;

  /** Set method for resource autoloading flag */
  public void setAutoLoading(boolean autoLoading) {
    this.autoLoading = autoLoading;
  } // setAutoLoading

  /** Is the resource autoloading? */
  public boolean isAutoLoading() { return autoLoading; }

  /** Private flag */
  protected boolean priv = false;

  /** Set method for resource private flag */
  public void setPrivate(boolean priv) {
    this.priv = priv;
  } // setPrivate

  /** Is the resource private? */
  public boolean isPrivate() { return priv; }

  /** Tool flag */
  protected boolean tool = false;

  /** Set method for resource tool flag */
  public void setTool(boolean tool) {
    this.tool = tool;
  } // setTool

  /** Is the resource a tool? */
  public boolean isTool() { return tool; }
  /** Is this a valid resource data configuration? If not, leave an
    * error message that can be returned by <TT>getValidityMessage()</TT>.
    */
  public boolean isValid() {
    boolean valid = true;
    validityMessage = "";
//******************************
// here should check that the resource has all mandatory elements,
// e.g. class name, and non-presence of runtime params on LRs and VRs etc.
//******************************
    if(getClassName() == null || getClassName().length() == 0){
      validityMessage += "No class name provided for the resource!";
      valid = false;
    }
    if(getName() == null || getName().length() == 0){
      //no name provided.
      setName(className.substring(className.lastIndexOf('.') + 1));
    }
    return valid;
  } // isValid()

  /** Status message set by isValid() */
  protected String validityMessage = "";

  /** Get validity statues message. */
  public String getValidityMessage() { return validityMessage; }

  /////////////////////////////////////////////////////
  // Fields added for GUI element
  /////////////////////////////////////////////////////
  /** This type indicates that the resource is not a GUI */
  public static final int NULL_GUI = 0;
  /**This type indicates that the resource goes into the large area of GATE GUI*/
  public static final int LARGE_GUI = 1;
  /**This type indicates that the resource goes into the small area of GATE GUI*/
  public static final int SMALL_GUI = 2;
  /** A filed which can have one of the 3 predefined values. See above.*/
  protected int guiType = NULL_GUI;
  /** Whether or not this viewer will be the default one*/
  protected boolean isMainView = false;
  /** The full class name of the resource displayed by this viewer.*/
  protected String resourceDisplayed = null;
  /** The full type name of the annotation displayed by this viewer.*/
  protected String annotationTypeDisplayed = null;
  /** A simple mutator for guiType field*/
  public void setGuiType(int aGuiType){guiType = aGuiType;}
  /** A simple accessor for guiType field*/
  public int getGuiType(){return guiType;}
  /** A simple mutator for isMainView field*/
  public void setIsMainView(boolean mainView){isMainView = mainView;}
  /** A simple accessor for isMainView field*/
  public boolean isMainView(){return isMainView;}
  /** A simple mutator for resourceDisplayed field*/
  public void setResourceDisplayed(String aResourceDisplayed){
    resourceDisplayed = aResourceDisplayed;
  }// setResourceDisplayed
  /** A simple accessor for resourceDisplayed field*/
  public String getResourceDisplayed(){return resourceDisplayed;}
  /** A simple mutator for annotationTypeDisplayed field*/
  public void setAnnotationTypeDisplayed(String anAnnotationTypeDisplayed){
    annotationTypeDisplayed = anAnnotationTypeDisplayed;
  }// setAnnotationTypeDisplayed
  /** A simple accessor for annotationTypeDisplayed field*/
  public String getAnnotationTypeDisplayed(){return annotationTypeDisplayed;}

  // Sharable properties for duplication
  protected Collection<String> sharableProperties = new HashSet<String>();

  /**
   * Get the collection of property names that should be treated as
   * sharable state when duplicating resources of this type.  The
   * specified properties may also be declared as parameters of the
   * resource but this is not required.
   */
  public Collection<String> getSharableProperties() {
    return sharableProperties;
  }

  /**
   * Initialize this ResourceData.  Called by CreoleXmlHandler once all
   * the properties of this ResourceData specified in the XML have been
   * filled in, but before autoinstances are created.
   */
  public void init() throws Exception {
    determineSharableProperties(getResourceClass(), new HashSet<String>());
  }

  private void determineSharableProperties(Class<?> cls, Collection<String> hiddenPropertyNames) throws GateException {
    BeanInfo bi;
    try {
      bi = Introspector.getBeanInfo(cls);
    } catch(IntrospectionException e) {
      throw new GateException("Failed to introspect " + cls, e);
    }

    // setter methods
    for(Method m : cls.getDeclaredMethods()) {
      Sharable sharableAnnot = m.getAnnotation(Sharable.class);
      if(sharableAnnot != null) {
        // determine the property name from the method name
        PropertyDescriptor propDescriptor = null;
        for(PropertyDescriptor pd : bi.getPropertyDescriptors()) {
          if(m.equals(pd.getWriteMethod())) {
            propDescriptor = pd;
            break;
          }
        }

        if(propDescriptor == null) {
          throw new GateException("@Sharable annotation found on "
                  + m
                  + ", but only Java Bean property setters may have "
                  + "this annotation.");
        }
        if(propDescriptor.getReadMethod() == null) {
          throw new GateException("@Sharable annotation found on "
                  + m
                  + ", but no matching getter was found.");
        }
        String propName = propDescriptor.getName();
        if(sharableAnnot.value() == false) {
          // hide this property name from the search in superclasses
          hiddenPropertyNames.add(propName);
        }
        else {
          // this property is sharable if it has not been hidden in a subclass
          if(!hiddenPropertyNames.contains(propName)) {
            sharableProperties.add(propName);
          }
        }
      }
    }
    
    // fields
    for(Field f : cls.getDeclaredFields()) {
      Sharable sharableAnnot = f.getAnnotation(Sharable.class);
      if(sharableAnnot != null) {
        String propName = f.getName();
        // check it's a valid Java Bean property
        PropertyDescriptor propDescriptor = null;
        for(PropertyDescriptor pd : bi.getPropertyDescriptors()) {
          if(propName.equals(pd.getName())) {
            propDescriptor = pd;
            break;
          }
        }
        if(propDescriptor == null || propDescriptor.getReadMethod() == null ||
                propDescriptor.getWriteMethod() == null) {
          throw new GateException("@Sharable annotation found on "
                  + f
                  + " without matching Java Bean getter and setter.");
        }
        if(sharableAnnot.value() == false) {
          // hide this property name from the search in superclasses
          hiddenPropertyNames.add(propName);
        }
        else {
          // this property is sharable if it has not been hidden in a subclass
          if(!hiddenPropertyNames.contains(propName)) {
            sharableProperties.add(propName);
          }
        }
      }
    }

    // go up the class tree
    if(cls.getSuperclass() != null) {
      determineSharableProperties(cls.getSuperclass(), hiddenPropertyNames);
    }
    for(Class<?> intf : cls.getInterfaces()) {
      determineSharableProperties(intf, hiddenPropertyNames);
    }
  }
} // ResourceData
