/*
 * GateClassLoader.java
 * 
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Kalina Bontcheva, 1998
 * 
 * Revised by Hamish for 1.2 style and URL/Jar loading, June 2000
 * 
 * a new architecture, by Mark Greenwood, to allow proper plugin isolation and
 * full unloading of class definitions when a plugin is unloaded, March 2012
 * 
 * $Id: GateClassLoader.java 20032 2017-01-31 17:39:47Z markagreenwood $
 */

package gate.util;

import java.beans.Introspector;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import gate.Gate;
import gate.Gate.ResourceInfo;
import gate.Resource;
import gate.creole.AbstractResource;
import gate.creole.Plugin;

/**
 * GATE's class loader, which allows loading of classes over the net. A
 * list of URLs is searched, which should point at .jar files or to
 * directories containing class file hierarchies. The loader is also
 * used for creating JAPE RHS action classes.
 */
public class GateClassLoader extends URLClassLoader {

  protected static final Logger log = Logger.getLogger(GateClassLoader.class);

  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Default construction - use an empty URL list. */
  public GateClassLoader(String name) {
    super(new URL[0]);
    this.id = name;
  }

  /** Chaining constructor. */
  public GateClassLoader(String name, ClassLoader parent) {
    super(new URL[0], parent);
    this.id = name;
  }

  /** Default construction with URLs list. */
  public GateClassLoader(String name, URL[] urls) {
    super(urls);
    this.id = name;
  }

  /** Chaining constructor with URLs list. */
  public GateClassLoader(String name, URL[] urls, ClassLoader parent) {
    super(urls, parent);
    this.id = name;
  }

  public GateClassLoader(String name, URL[] urls, ClassLoader parent,
          boolean isolated) {
    super(urls, parent);
    this.id = name;
    this.isolated = isolated;
  }

  private String id = null;

  public String getID() {
    return id;
  }

  private boolean isolated = false;

  public boolean isIsolated() {
    return isolated;
  }

  @Override
  public String toString() {
    return "Classloader ID: " + id;
  }

  /**
   * Appends the specified URL to the list of URLs to search for classes
   * and resources.
   */
  @Override
  public void addURL(URL url) {
    super.addURL(url);
  }

  @Override
  public URL getResource(String name) {
    URL result = null;

    result = super.getResource(name);
    if(result != null) return result;

    if(getParent() == null) {
      result = Gate.getClassLoader().findResource(name);
      if(result != null) return result;
    }

    Set<GateClassLoader> children;
    synchronized(childClassLoaders) {
      children = new LinkedHashSet<GateClassLoader>(childClassLoaders.values());
    }

    for(GateClassLoader cl : children) {
      if(!cl.isIsolated()) {
        result = cl.getResource(name);
        if(result != null) return result;
      }
    }

    return null;
  }
  
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    return loadClass(name, false, false, new HashSet<GateClassLoader>());
  }

  /**
   * Delegate loading to the super class (loadClass has protected access
   * there).
   */
  @Override
  public Class<?> loadClass(String name, boolean resolve)
          throws ClassNotFoundException {
    return loadClass(name, resolve, false, new HashSet<GateClassLoader>());
  }

  /**
   * Delegate loading to the super class (loadClass has protected access
   * there).
   */
  private Class<?> loadClass(String name, boolean resolve, boolean localOnly,
          Set<GateClassLoader> visited) throws ClassNotFoundException {

    Class<?> previous = findLoadedClass(name);

    if(previous != null) {
      if(DEBUG) System.out.println("CACHE HIT: " + name + " -- " + id);

      return previous;
    }

    if(DEBUG)
      System.out.println(name + " -- " + id + ": " + localOnly + "/" + isolated
              + "/" + getParent());

    // to ensure we don't end up looping through the same classloader
    // twice we
    // keep a track of which ones we have already visited
    visited.add(this);

    if(!this.equals(Gate.getClassLoader())) {
      try {
        // first we see if we can find the class via the system class
        // path
        Class<?> found = Gate.getClassLoader().getParent().loadClass(name);

        URL url = findResource(name.replace('.', '/') + ".class");
        if(url != null)
          log.warn(name
                  + " is available via both the system classpath and a plugin; the plugin ("+getID()+") classes will be ignored");

        // if we got to here then the class has been found via the
        // system
        // classpath so return it and stop looking
        return found;

      } catch(ClassNotFoundException e) {
        // this can safely be ignored
      }
    }

    try {
      // try loading and returning by looking within this classloader
      return super.loadClass(name, resolve);
    } catch(ClassNotFoundException e) {
      // this can safely be ignored
    }

    if(this.getParent() != null && this.getParent() instanceof GateClassLoader)
      visited.add((GateClassLoader)this.getParent());

    if(!localOnly) {
      // if we aren't just looking locally then...

      if(getParent() == null) {
        try {
          // if this classloader doesn't have a parent then it must be
          // disposable, but as we haven't found the class we need yet
          // we should
          // now look into the main GATE classloader
          return Gate.getClassLoader().loadClass(name, resolve, false, visited);
        } catch(ClassNotFoundException e) {
          // this can safely be ignored
        }
      }

      Set<GateClassLoader> children;
      synchronized(childClassLoaders) {
        children =
                new LinkedHashSet<GateClassLoader>(childClassLoaders.values());
      }

      // make sure we don't visit a classloader we've already been
      // through
      children.removeAll(visited);

      for(GateClassLoader cl : children) {
        // the class isn't to be found in either this classloader or the
        // main
        // GATE classloader so let's check all the other disposable
        // classloaders
        try {
          if(!cl.isIsolated())
            return cl.loadClass(name, resolve, true, visited);
        } catch(ClassNotFoundException e) {
          // this can safely be ignored
        }
      }
    }

    // if we got to here then no matter where we have looked we have
    // been unable
    // to find the class requested so throw an exception
    throw new ClassNotFoundException(name);
  }

  /**
   * Forward a call to super.defineClass, which is protected and final
   * in super. This is used by JAPE and the Jdk compiler class.
   */
  public Class<?> defineGateClass(String name, byte[] bytes, int offset, int len) {
    return super.defineClass(name, bytes, offset, len);
  }

  /**
   * Forward a call to super.resolveClass, which is protected and final
   * in super. This is used by JAPE and the Jdk compiler class
   */
  public void resolveGateClass(Class<?> c) {
    super.resolveClass(c);
  }

  /**
   * Given a fully qualified class name, this method returns the
   * instance of Class if it is already loaded using the ClassLoader or
   * it returns null.
   */
  public Class<?> findExistingClass(String name) {
    return findLoadedClass(name);
  }

  Map<String, GateClassLoader> childClassLoaders =
          new LinkedHashMap<String, GateClassLoader>();

  /**
   * Returns a classloader that can, at some point in the future, be
   * forgotton which allows the class definitions to be garbage
   * collected.
   * 
   * @param id the id of the classloader to return
   * @return either an existing classloader with the given id or a new
   *         classloader
   */
  public GateClassLoader getDisposableClassLoader(String id) {
    return getDisposableClassLoader(id, null, false);
  }

  public GateClassLoader getDisposableClassLoader(String id, boolean isolated) {
    return getDisposableClassLoader(id, null, isolated);
  }

  public GateClassLoader getDisposableClassLoader(String id,
          ClassLoader parent, boolean isolated) {
    GateClassLoader gcl = null;

    synchronized(childClassLoaders) {
      gcl = childClassLoaders.get(id);

      if(gcl == null) {
        gcl = new GateClassLoader(id, new URL[0], parent, isolated);
        childClassLoaders.put(id, gcl);
      }
    }

    return gcl;
  }

  /**
   * Causes the specified classloader to be forgotten, making it and all
   * the class definitions loaded by it available for garbage collection
   * 
   * @param id the id of the classloader to forget
   */
  public void forgetClassLoader(String id) {
    
    GateClassLoader gcl;
    
    synchronized(childClassLoaders) {
       gcl = childClassLoaders.remove(id);
    }
  
    if(gcl != null && !gcl.isIsolated()) {
      // in theory this shouldn't be needed as the Introspector uses
      // soft references if we move to requiring Java 8 it should be
      // safe to drop this call
      Introspector.flushCaches();

      AbstractResource.flushBeanInfoCache();
    }
  }

  /**
   * Causes the specified classloader to be forgotten, making it and all
   * the class definitions loaded by it available for garbage collection
   * 
   * @param classloader the classloader to forget
   */
  public void forgetClassLoader(GateClassLoader classloader) {
    if(classloader != null) forgetClassLoader(classloader.getID());
  }
  
  
  public void forgetClassLoader(String id, Plugin dInfo) {

    if(dInfo == null) {
      forgetClassLoader(id);
      return;
    }

    GateClassLoader classloader = null;

    synchronized(childClassLoaders) {
      classloader = childClassLoaders.remove(id);
    }

    if(classloader != null && !classloader.isIsolated()) {
      // now only remove those classes from the caches that the
      // classloader was responsible for
      for(ResourceInfo rInfo : dInfo.getResourceInfoList()) {
        try {
          @SuppressWarnings("unchecked")
          Class<? extends Resource> c =
                  (Class<? extends Resource>)classloader.loadClass(
                          rInfo.getResourceClassName());
          
          if(c != null) {
            // in theory this shouldn't be needed as the Introspector
            // uses soft references if we move to requiring Java 8 it
            // should be safe to drop this call
            Introspector.flushFromCaches(c);

            AbstractResource.forgetBeanInfo(c);
          }
        } catch(ClassNotFoundException e) {
          // hmm not sure what to do now
           e.printStackTrace();
        }
      }
    }
  }
  
  @Override
  public Enumeration<URL> getResources(String resource) throws IOException {
    
    if (childClassLoaders.isEmpty()) return super.getResources(resource);
    
    Set<URL> urls = new LinkedHashSet<URL>();
    
    urls.addAll(Collections.list(super.getResources(resource)));
    
    
    Set<GateClassLoader> children;
    synchronized(childClassLoaders) {
      children = new LinkedHashSet<GateClassLoader>(childClassLoaders.values());
    }

    for(GateClassLoader cl : children) {
      if(!cl.isIsolated()) {
        urls.addAll(Collections.list(cl.getResources(resource)));
      }
    }
    
    return Collections.enumeration(urls);
  }

  /**
   * Get the child classloaders in creation order. Note that you
   * shouldn't have any need to access the child classloaders. Holding
   * references is very likely to lead to a memory leak as the plugins
   * will no longer be disposable. YOU HAVE BEEN WARNED!
   * 
   * @return the child classloaders in creation order
   */
  public List<GateClassLoader> getChildren() {
    synchronized(childClassLoaders) {
      return new ArrayList<GateClassLoader>(childClassLoaders.values());
    }
  }
}
