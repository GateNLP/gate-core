/*
 * 
 * Copyright (c) 1995-2012, The University of Sheffield. See the file
 * COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Valentin Tablan, 18/Feb/2002
 * 
 * $Id: Javac.java 17396 2014-02-22 10:16:45Z markagreenwood $
 */
package gate.util;

import gate.Gate;
import gate.GateConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * This class compiles a set of java sources using the user's preferred Java
 * compiler. The default compiler used is the Eclipse JDT compiler, but this can
 * be overridden by the user via an option in gate.xml.
 */
public abstract class Javac implements GateConstants {

  /**
   * Compiles a set of java sources and loads the compiled classes in the gate
   * class loader.
   * 
   * @param sources
   *          a map from fully qualified classname to java source
   * @param classLoader
   *          the classloader into which the sources should be compiled and
   *          loaded. Note that this classloader must also have access to all
   *          the classes required to compile the sources.
   * @throws GateException
   *           in case of a compilation error or warning. In the case of
   *           warnings the compiled classes are loaded before the error is
   *           raised.
   */
  public static void loadClasses(Map<String, String> sources,
      GateClassLoader classLoader) throws GateException {
    
    if(compiler == null) {
      setCompilerTypeFromUserConfig();
    }
    
    if (classLoader == null) {
      Err.println("A null classloader was provided, using the Top-Level GATE classloader instead!");
      classLoader = Gate.getClassLoader();
    }

    compiler.compile(sources, classLoader);
  }

  /**
   * Sets the type of compiler to be used, based on the user's configuration.
   * The default is to use the Eclipse compiler unless the user requests
   * otherwise.
   */
  private static void setCompilerTypeFromUserConfig() throws GateException {
    // see if the user has expressed a preference
    String compilerType = Gate.getUserConfig().getString(COMPILER_TYPE_KEY);
    // if not, use the default
    if(compilerType == null) {
      compilerType = DEFAULT_COMPILER;
    }

    // We try and load the compiler class first by treating the given
    // name as a fully qualified class name. If this fails, we prepend
    // "gate.util.compilers." (so the user can say just "Sun" rather
    // than "gate.util.compilers.Sun"). If that fails, we try the
    // default value DEFAULT_COMPILER. If that fails, we give up.
    try {
      // first treat the compiler type as a fully qualified class name
      compiler = createCompilerInstance(compilerType);
    } catch(GateException ge) {
      // if it's the default compiler we've just failed to load, give up
      // now
      if(DEFAULT_COMPILER.equals(compilerType)) { throw ge; }

      // we failed to find the class as a FQN, so try relative to
      // gate.util.compilers
      compilerType = "gate.util.compilers." + compilerType;
      try {
        compiler = createCompilerInstance(compilerType);
      } catch(GateException ge2) {
        // if it's the default compiler we've just failed to load, give
        // up now
        if(DEFAULT_COMPILER.equals(compilerType)) { throw ge2; }

        Err.prln("Unable to load compiler class " + compilerType
            + ", falling back to default of " + DEFAULT_COMPILER);
        compilerType = DEFAULT_COMPILER;
        // last try - fall back on the default value. If this fails we
        // just allow the failure exception to propagate up the stack
        // from here.
        compiler = createCompilerInstance(compilerType);
      }
    }
  }

  private static Javac createCompilerInstance(String compilerType)
      throws GateException {
    Class<?> compilerClass = null;
    try {
      // first treat the compiler type as a fully qualified class name
      compilerClass = Gate.getClassLoader().loadClass(compilerType, true);
    } catch(ClassNotFoundException cnfe) {
      // ignore exception but leave compilerClass == null
    }

    if(compilerClass == null || !Javac.class.isAssignableFrom(compilerClass)) { throw new GateException(
        "Unable to load Java compiler class " + compilerType); }

    // At this point we have successfully loaded a compiler class.
    // Now try and create an instance using a no-argument constructor.
    try {
      Constructor<?> noArgConstructor = compilerClass.getConstructor();
      return (Javac)noArgConstructor.newInstance();
    } catch(IllegalAccessException iae) {
      throw new GateException("Cannot access Java compiler class "
          + compilerType, iae);
    } catch(InstantiationException ie) {
      throw new GateException("Cannot instantiate Java compiler class "
          + compilerType, ie);
    } catch(NoSuchMethodException nsme) {
      throw new GateException("Java compiler class " + compilerType
          + " does not have a no-argument constructor", nsme);
    } catch(InvocationTargetException ite) {
      throw new GateException("Exception when constructing Java compiler "
          + "of type " + compilerType, ite.getCause());
    } catch(ExceptionInInitializerError eiie) {
      throw new GateException("Exception when initializing Java compiler "
          + "class " + compilerType, eiie.getCause());
    }
  }

  /**
   * Compile a set of Java sources, and load the resulting classes into the GATE
   * class loader.
   * 
   * @param sources
   *          a map from fully qualified classname to java source
   * @param classLoader
   *          the classloader into which the sources should be compiled. Note
   *          that this classloader must also have access to all the classes
   *          required to compile the sources.
   * @throws GateException
   *           in case of a compilation error or warning. In the case of
   *           warnings, the compiled classes are loaded before the exception is
   *           thrown.
   */
  public abstract void compile(Map<String, String> sources,
      GateClassLoader classLoader) throws GateException;

  /**
   * The compiler to use.
   */
  private static Javac compiler = null;

  /**
   * The default compiler to use.
   */
  public static final String DEFAULT_COMPILER = "gate.util.compilers.Eclipse";
}
