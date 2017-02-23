/*
 *  Tools.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, Jan/2000
 *
 *  $Id: Tools.java 19652 2016-10-08 08:28:08Z markagreenwood $
 */

package gate.util;

import gate.Gate;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Tools {

  /** Debug flag */
  private static final boolean DEBUG = false;

  public Tools() {
  }
  static long sym=0;

  /** Returns a Long wich is unique during the current run.
    * Maybe we should use serializaton in order to save the state on
    * System.exit...
    */
  static public synchronized Long gensym(){
    return sym++;
  }

  static public synchronized Long genTime(){

    return new Date().getTime();
  }


  /** Specifies whether Gate should or shouldn't know about Unicode */
  static public void setUnicodeEnabled(boolean value){
    unicodeEnabled = value;
  }

  /** Checks wheter Gate is Unicode enabled */
  static public boolean isUnicodeEnabled(){
    return unicodeEnabled;
  }

  /** Does Gate know about Unicode? */
  static private boolean unicodeEnabled = false;


  /**
   * Finds all subclasses of a given class or interface. It will only search
   * within the loaded packages and not the entire classpath.
   * @param parentClass the class for which subclasses are sought
   * @return a list of {@link Class} objects.
   */
  static public List<Class<?>> findSubclasses(Class<?> parentClass){
    Package[] packages = Package.getPackages();
    List<Class<?>> result = new ArrayList<Class<?>>();
    for(int i = 0; i < packages.length; i++){
      String packageDir = packages[i].getName();
      //look in the file system
      if(!packageDir.startsWith("/")) packageDir = "/" + packageDir;
      packageDir = packageDir.replace('.', Strings.getPathSep().charAt(0));
      URL packageURL = Gate.getClassLoader().getResource(packageDir);
      if(packageURL != null){
        File directory = Files.fileFromURL(packageURL);
        if(directory.exists()){
          String [] files = directory.list();
          for (int j=0; j < files.length; j++){
            // we are only interested in .class files
            if(files[j].endsWith(".class")){
              // removes the .class extension
              String classname = files[j].substring(0, files[j].length() - 6);
              try {
                // Try to create an instance of the object
                Class<?> aClass = Class.forName(packages[i] + "." + classname,
                                             true, Gate.getClassLoader());
                if(parentClass.isAssignableFrom(aClass)) result.add(aClass);
              }catch(ClassNotFoundException cnfex){}
            }
          }
        }else{
          //look in jar files
          try{
            JarURLConnection conn = (JarURLConnection)packageURL.openConnection();
            String starts = conn.getEntryName();
            JarFile jFile = conn.getJarFile();
            Enumeration<JarEntry> e = jFile.entries();
            while (e.hasMoreElements()){
              String entryname = e.nextElement().getName();
              if (entryname.startsWith(starts) &&
                  //not sub dir
                  (entryname.lastIndexOf('/')<=starts.length()) &&
                  entryname.endsWith(".class")){
                String classname = entryname.substring(0, entryname.length() - 6);
                if (classname.startsWith("/")) classname = classname.substring(1);
                classname = classname.replace('/','.');
                try {
                  // Try to create an instance of the object
                  Class<?> aClass = Class.forName(packages[i] + "." + classname,
                                               true, Gate.getClassLoader());
                  if(parentClass.isAssignableFrom(aClass)) result.add(aClass);
                }catch(ClassNotFoundException cnfex){}
              }
            }
          }catch(java.io.IOException ioe){}
        }
      }
    }
    return result;
  }
  
  /**
   * <p>Find the constructor to use to create an instance of
   * <code>targetClass</code> from one of <code>paramClass</code>.
   * We use the same rules as Java in finding the constructor
   * to call, i.e. we find the constructor that javac would call for
   * the expression
   * <code>parameterValue = new PropertyType(parameterValue)</code>:</p>
   * <ol>
   * <li>find all the single-argument constructors for propertyType
   * whose argument type <code>T1</code> is assignable from
   * <code>paramType</code></li>
   * <li>from these, choose the one whose argument type <code>T2</code>
   * is more specific than the argument type <code>S</code> of every
   * other one i.e.
   * for all S, <code>S.isAssignableFrom(T2)</code></li>
   * </ol>
   * <p>If there is no applicable single argument constructor that is
   * more specific than all the others, then the above expression
   * would be a compile error (constructor <code>ParamType(X)</code> is
   * ambiguous).</p>
   *
   * <p>(The "most specific" check is to catch situations such as
   * paramClass implements <code>SortedSet</code>,
   * targetClass = <code>TreeSet</code>.  Here both 
   * <code>TreeSet(SortedSet)</code> and <code>TreeSet(Collection)</code>
   * are applicable but the former is more specific.  However, if
   * paramType also implements <code>Comparator</code> then there are
   * three applicable constructors, taking <code>SortedSet</code>,
   * <code>Collection</code> and <code>Comparator</code> respectively
   * and none of these types is assignable to both the others.  This
   * is ambiguous according to the Java Language Specification,
   * 2nd edition, section 15.12.2)</p>
   *
   * @param targetClass the class we wish to construct
   * @param paramClass the type of the object to pass as a parameter to
   *         the constructor.
   * @return the most specific constructor of <code>targetClass</code>
   *         that is applicable to an argument of <code>paramClass</code>
   * @throws NoSuchMethodException if there are no applicable constructors,
   *         or if there is no single most-specific one.
   */
  public static Constructor<?> getMostSpecificConstructor(Class<?> targetClass,
          Class<?> paramClass) throws NoSuchMethodException {
    if(targetClass.isInterface()) {
      throw new NoSuchMethodException(targetClass.getName() +
              " is an interface, so cannot have constructors");
    }
    Constructor<?>[] targetClassConstructors =
        targetClass.getConstructors();
    List<Constructor<?>> applicableConstructors =
        new ArrayList<Constructor<?>>();
    // find all applicable constructors
    for(Constructor<?> c : targetClassConstructors) {
      Class<?>[] constructorParams = c.getParameterTypes();
      if(constructorParams.length == 1
              && constructorParams[0].isAssignableFrom(paramClass)) {
        applicableConstructors.add(c);
      }
    }
    // simple cases - none applicable
    if(applicableConstructors.size() == 0) {
      throw new NoSuchMethodException(
              "No applicable constructors for "
              + targetClass.getName() + "("
              + paramClass.getName() + ")");
    }
    Constructor<?> mostSpecificConstructor = null;
    // other simple case - only one applicable
    if(applicableConstructors.size() == 1) {
      mostSpecificConstructor = applicableConstructors.get(0);
    }
    else {
      // complicated case - need to find the most specific.
      // since the constructor parameter types are all assignable
      // from paramType there can be at most one that is assignable
      // from *all* others
      C1: for(Constructor<?> c1 : applicableConstructors) {
        Class<?> c1ParamType = c1.getParameterTypes()[0];
        for(Constructor<?> c2 : applicableConstructors) {
          Class<?> c2ParamType = c2.getParameterTypes()[0];
          if(!c2ParamType.isAssignableFrom(c1ParamType)) {
            continue C1;
          }
        }
        mostSpecificConstructor = c1;
        break C1;
      }
    }
    // we tried all the constructors and didn't find the most-specific
    // one
    if(mostSpecificConstructor == null) {
      if(DEBUG) {
        Out.println("Ambiguous constructors for "
              + targetClass.getName() + "("
              + paramClass.getName() + ")");
        Out.println("Choice was between " + applicableConstructors);
      }
      throw new NoSuchMethodException(
              "Ambiguous constructors for "
              + targetClass.getName() + "("
              + paramClass.getName() + ")");
    }
    
    if(DEBUG) {
      Out.println("Most specific constructor for " +
              targetClass.getName() + "(" + paramClass.getName() + ") is "
              + mostSpecificConstructor);
    }
    return mostSpecificConstructor;
  }
  
  /**
   * Prints the stack trace of the current thread to the specified print stream.
   * @param pStream
   */
  public static final void printStackTrace(PrintStream pStream){
    StackTraceElement stackTraceElems[] = Thread.currentThread().getStackTrace();
    for(StackTraceElement ste : stackTraceElems){
      pStream.println(ste.toString());
    }
  }
} // class Tools
