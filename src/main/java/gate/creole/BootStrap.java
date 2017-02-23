/*
 *  BootStrap.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Oana Hamza 14/Nov/2000
 *
 *  $Id: BootStrap.java 17590 2014-03-08 08:17:28Z markagreenwood $
 */
package gate.creole;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import gate.util.*;


/**
  * This class creates a resource (e.g.ProcessingResource, VisualResource or
  * Language Resource) with the information from the user and generates a
  * project in the directory provided by the user
  */

public class BootStrap {

  /** the name of the resource of the template project from the gate resources*/
  //protected String oldResource = "creole/templateproject";
  protected static final String oldResource = "creole/bootstrap/";

  /** the name of jar resource*/
  protected static final String nameProject = "Template";

  /** new line for different platforms*/
  protected static final String newLine = Strings.getNl();

  /** a map from the variants of the names of the files and the
    * directories of the empty project to the variants of the names of the
    * files and the directories the new project
    */
  protected Map<String,String> names = null;

  protected Map<String,String> oldNames = null;

  /** the methods from the class that implements the resource*/
  protected List<String> listMethodsResource = null;

  /** the list with the packages name where the main class can be find*/
  protected List<String> listPackages;

  /** the packages used by the class which creates the resources */
  protected Set<String> allPackages = null;

  /** the enumeration of the variables from main class*/
  protected Map<Integer,String> fields = null;

  /** a buffer in order to read an array of char */
  protected char cbuffer[] = null;

  /** the size of the buffer */
  protected final static int BUFF_SIZE = 65000;


  public BootStrap() {

    names = new HashMap<String,String>();

    oldNames = new HashMap<String,String>();

    listMethodsResource = new ArrayList<String>();

    listPackages = new ArrayList<String>();

    cbuffer = new char[BUFF_SIZE];

    allPackages = new HashSet<String>();

    fields = new HashMap<Integer,String>();
  }

  /** Determines all the keys from the map "names" in the text and replaces them
    * with their values
    */
  public String changeKeyValue ( String text, Map<String,String> map ){

    Set<String> keys = map.keySet();
    Iterator<String> iteratorKeys = keys.iterator();
    while (iteratorKeys.hasNext()) {
      String key = iteratorKeys.next();
      String value = map.get(key);
      text = text.replaceAll(key,value);
    } // while
    return text;
  } // changeKeyValue ( String text )

  /** determines the package of the main class
    */
  public String determineTypePackage(String text) {

    // determine the position of the last "."
    int index = text.lastIndexOf(".");
    int ind = text.lastIndexOf(";");
    String type = new String();
    String packageName = new String();

    if (index != -1){
      // determine the package and add to the list of packages
      if (ind != -1) {
        type = text.substring(index+1,text.length()-1)+"[]";
        packageName = (text.substring(2,index))+".*";
      }
      else {
        packageName = (text.substring(0,index))+".*";
        type = text.substring(index+1,text.length());
      }
      // add the name of the package
      if ((!allPackages.contains(packageName))&&
                              (packageName.compareTo("java.lang.*")!=0))
        allPackages.add(packageName);
    } else {type = text;}

    return type;
  }

  /** returns the string with the interfaces that implement the main class and
    *  the class that extends it
    */
  public String getInterfacesAndClass (String typeResource, Set<String> interfacesList)
                                    throws ClassNotFoundException {

    String abstractClass = null;
    // add the class that it extends
    String interfacesAndClass = null;
    // the class corresponding to the current interface from list interfaces.
    Class<?> currentClass = null;

    // determine the abstract class
    if (typeResource.equals("ProcessingResource")) {
      abstractClass = "AbstractProcessingResource";
    } else if (typeResource.equals("VisualResource")) {
      abstractClass = "AbstractVisualResource";
    } else if (typeResource.equals("LanguageResource")) {
      abstractClass = "AbstractLanguageResource";}

    interfacesAndClass = " extends " + abstractClass;

    // a map from all the methods from interfaces to the lists which contains
    // the features of every method
    List<FeatureMethod> methodsInterfaces = new ArrayList<FeatureMethod>();
    if (interfacesList!=null) {
      interfacesAndClass = interfacesAndClass+ newLine+ "  implements ";
      Iterator<String> iter = interfacesList.iterator();
      while (iter.hasNext()) {
        String nameInterface = iter.next();
        String nameClass = null;
        int index = nameInterface.lastIndexOf(".");
        if (index != -1) {
          currentClass = Class.forName(nameInterface);
          nameClass = nameInterface.substring(index+1,nameInterface.length());
        } else {
          nameClass = nameInterface;
          currentClass = Class.forName("gate."+nameInterface);
        }// else

        // add the package to the list
        allPackages.add(currentClass.getPackage().getName()+".*");
        
        interfacesAndClass = interfacesAndClass + nameClass + ", ";

        methodsInterfaces = featuresClass(currentClass,methodsInterfaces);
      }//while
    }// if

    // add the abstract class
    if (!interfacesList.contains("gate."+typeResource))
      interfacesAndClass = interfacesAndClass + typeResource;
    else if (interfacesAndClass.endsWith(", "))
      interfacesAndClass = interfacesAndClass.substring
                                            (0,interfacesAndClass.length()-2);

    // methods from the class that extends the resource
    List<FeatureMethod> methodsClassExtend = new ArrayList<FeatureMethod>();
    Class<?> currentClassExtend = Class.forName("gate.creole."+abstractClass);
    methodsClassExtend = featuresClass(currentClassExtend, methodsClassExtend);

    // get the methods and fields for the main class
    getMethodsAndFields(methodsClassExtend,methodsInterfaces);

    return interfacesAndClass;
  } // getInterfacesAndClass

  /**go through all methods and determines return type, parameters, exceptions*/
  public List<FeatureMethod> featuresClass (Class<?> currentClass, List<FeatureMethod> methodsList){

    // go through all the methods
    Method[] listMethodsCurrentClass = currentClass.getMethods();
    for (int i=0;i<listMethodsCurrentClass.length;i++) {
      FeatureMethod featureMethod = new FeatureMethod();
      featureMethod.setNameMethod(listMethodsCurrentClass[i].getName());
      featureMethod.setValueReturn(
                          listMethodsCurrentClass[i].getReturnType().getName());

      Class<?>[] parameters = (
                                listMethodsCurrentClass[i].getParameterTypes());
      Class<?>[] exceptions = (
                                listMethodsCurrentClass[i].getExceptionTypes());

      // determine the parameters for the current method
      List<String> aux = new ArrayList<String>();
      for (int k=0;k<parameters.length;k++)
        aux.add(parameters[k].getName());
      featureMethod.setParameterTypes(aux);

      // determine the exceptions for the current method
      aux = new ArrayList<String>();
      for (int k=0;k<exceptions.length;k++)
        aux.add(exceptions[k].getName());
      featureMethod.setExceptionTypes(aux);

      if (!methodsList.contains(featureMethod)){
        methodsList.add(featureMethod);
      }
    }// for
    return methodsList;
  }// List featureClass (Class currentClass)

  /** create the form for the methods from the class that create the resource
    * @param methodsExtendList is the list with all methods from the class that extends
    *  the resource
    * @param methodsInterfacesList is the list with all methods from the interfaces
    * that implement the resource
    */
  public void getMethodsAndFields(List<FeatureMethod> methodsExtendList,
                                                  List<FeatureMethod> methodsInterfacesList){
    // determine all the methods from the interfaces which are not among the
    // methods of the class that extends the resource

    int j = 0;
    for (int i=0;i<methodsInterfacesList.size();i++) {
      FeatureMethod featureMethod = methodsInterfacesList.get(i);
      if (methodsExtendList.contains(featureMethod) == false) {
        // the name of the method
        String nameMethod = (featureMethod.getNameMethod());

        // the types of the parameters of the method
        List<String> valTypes = (featureMethod.getParameterTypes());

        // the value which the method returns
        String typeReturn = determineTypePackage(
                                      (featureMethod.getValueReturn()));

        // get the list of exceptions for the current method
        List<String> valException = (featureMethod.getExceptionTypes());

        String declaration = "public "+ typeReturn +" "+
                             nameMethod +"(";
        // parameters
        if (valTypes.size() == 0)
          declaration = declaration+")";
        else
          for (int k=0;k<valTypes.size();k++) {
            String type = valTypes.get(k);
            if (type.endsWith("[]"))
              declaration = declaration +
                  determineTypePackage(type.substring(0,type.length()-2)) +
                  " parameter"+ k;
            else
              declaration = declaration +
                            determineTypePackage(valTypes.get(k)) +
                            " parameter"+ k;

            if (k==valTypes.size()-1)
              declaration = declaration + ")";
            else
              declaration = declaration + ", ";

          } // for

        // exceptions
        if (valException.size() == 0) {
          if (!typeReturn.equals("void")){
            if (!typeReturn.endsWith("[]"))
              declaration = declaration + "{ " + "return "+
                            typeReturn.toLowerCase()+ j + "; }";
            else
              declaration = declaration + "{ " + "return "+
                            typeReturn.toLowerCase().substring(
                            0,typeReturn.length()-2)+ j + "; }";

            fields.put(new Integer(j),typeReturn);
            j =j+1;
          }
          else {declaration = declaration+" {}" ;}
        } // if
        else {
          declaration = declaration + newLine+ "                throws ";
          for (int k=0;k<valException.size();k++) {
            declaration = declaration + determineTypePackage(valException.get(k));

            if (k == valException.size()-1) {
              if (!typeReturn.equals("void")){
                if (!typeReturn.endsWith("[]"))
                  declaration = declaration + "{ " + "return "+
                          typeReturn.toLowerCase()+ j+"; }";
                else
                  declaration = declaration + "{ " + "return "+
                            typeReturn.toLowerCase().substring(
                            0,typeReturn.length()-2)+ j + "; }";

                fields.put(new Integer(j),typeReturn);
                j=j+1;
              }
              else
                declaration = declaration+" {}" ;
            } else
              declaration = declaration + ", ";

          } // for
        } // else

        // add the form of the method
        listMethodsResource.add(declaration);

      } // if
    } // while

  } // getMethodsAndFields

  /**
    * write the methods and the fields in the right form
    */
  public String displayMethodsAndFields(List<String> methods, Map<Integer,String> fields) {

    String methodsFields = "";

    // go through all methods
    Iterator<String> iterator = listMethodsResource.iterator();
    while (iterator.hasNext()) {
      methodsFields = methodsFields + newLine + iterator.next()+newLine;
    }

    // go through all fields
    Iterator<Integer> iter = fields.keySet().iterator();
    while (iter.hasNext()) {
      Integer index = iter.next();
      String type = fields.get(index);
      if (type.endsWith("[]"))
        methodsFields = methodsFields + newLine + "protected " + type +" " +
                       type.substring(0,type.length()-2).toLowerCase() +
                        index.toString() +";";

      else
        methodsFields = methodsFields + newLine + "protected " + type +" " +
                        type.toLowerCase() + index.toString() +";";
    }
    return methodsFields;
  }// displayMethodsAndFields(List methods, List fields)


  /** create the map with variants of the names... */
  public Map<String,String> createNames ( String packageName,
                           String resourceName,
                           String className,
                           String stringPackages,
                           String interfacesAndClass) {

     // all the packages from the class, which creates the resource
    String packages = namesPackages(allPackages);

    // determine the name of the current user and the current day
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH)+1;
    int year = calendar.get(Calendar.YEAR);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    String date = day+"/"+month+"/"+year;
    String user = System.getProperty("user.name");

    // the a map with the variants of names and the current date
    // and the current user
    names.put(nameProject,resourceName);
    names.put(nameProject.toUpperCase(),resourceName.toUpperCase());
    names.put(nameProject.toLowerCase(),resourceName.toLowerCase());
    names.put("___CLASSNAME___",className);
    names.put("___INTERFACES___",interfacesAndClass);
    names.put("___CONTENT___",
                          displayMethodsAndFields(listMethodsResource,fields));
    names.put("___DATE___",date);
    names.put("___AUTHOR___",user);
    // "___ALLPACKAGE___" is the packages separated by "/"
    // e.g. "sheffied/creole/tokeniser"
    names.put("___ALLPACKAGES___",stringPackages);
    // "___PACKAGE___" is the packages separated by "."
    // e.g. "sheffied.creole.tokeniser"
    names.put("___PACKAGE___",packageName);
    names.put("___PACKAGETOP___",listPackages.get(0));
    names.put("___RESOURCE___",resourceName);;
    names.put(
      "___GATECLASSPATH___",
      System.getProperty("path.separator") +
        System.getProperty("java.class.path")
    );
    // ___GATEHOME___ is the GATE home dir, in the form suitable for an Ant
    // property
    File gateHome = gate.Gate.getGateHome();
    if(gateHome == null) {
      // put a fake gate home location in here if we can't get the real one
      names.put("___GATEHOME___",
          gate.Gate.runningOnUnix() ? "/path/to/gate"
                                    : "C:/Program Files/GATE");
    }
    else {
      names.put("___GATEHOME___",
          gateHome.getPath().replace('\\', '/'));
    }

    if (packages.length() == 0){
      names.put("import ___packages___.*;", "");
    } else {
      names.put("import ___packages___.*;", packages);
    }

    oldNames.put("___PACKAGE___","template");
    oldNames.put("___ALLPACKAGES___","template");
    oldNames.put("___PACKAGETOP___","template");

    return names;
  }// End createNames()

  /** determine all the packages */
  public String namesPackages (Set<String> listPackages) {
    Iterator<String> iterator = listPackages.iterator();
    String packages = new String();
    while (iterator.hasNext()) {
      String currentPackage = iterator.next();
      if ((!currentPackage.equals("gate.*"))&&
         (!currentPackage.equals("gate.creole.*"))&&
         (!currentPackage.equals("gate.util.*"))&&
         (!currentPackage.equals("java.util.*")))
          packages = packages + newLine + "import "+ currentPackage+";";
    }// while
    return packages;
  }

  /** determines the name of the packages and adds them to a list
    */
  public List<String> determinePath (String packageName)throws IOException {
    List<String> list = new ArrayList<String>();
    StringTokenizer token = new StringTokenizer(packageName,".");
    //if (token.countTokens()>1) {
      while (token.hasMoreTokens()) {
        list.add(token.nextToken());
      }
    //} else list.add(packageName);
    return list;
  }

  /** verify if the class name contains only letters and digits
   *  the path of the new project is a directory
   */
  public void verifyInput(String className, String pathNewProject)
                          throws GateException {
    // verify the input
    // class name contains only letters and digits
    char[] classNameChars = className.toCharArray();
    for (int i=0;i<classNameChars.length;i++){
      if (!Character.isLetterOrDigit(classNameChars[i]))
        throw new GateException("Only letters and digits in the class name");
    }

    // verify if it exits a directory of given path
    File dir = new File(pathNewProject);
    if (!dir.isDirectory())
      throw new GateException("The folder is not a directory");
  }

  /***/
  public void executableFile(String nameFile)
                                    throws IOException,InterruptedException{
    String osName = System.getProperty("os.name" );
    if( !osName.startsWith("Windows") ){
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec("chmod 711 "+nameFile);

      // any error???
      int exitVal = proc.waitFor();
      if (exitVal!=0)
        Out.prln("Warning: it is necessary to make executable the "+
          "following file: " + nameFile);
    }//if
  }// executableFile

  /**  Creates the resource and dumps out a project structure using the
    *  structure from gate/resource/creole/bootstrap/Template and the
    *  information provided by the user
    * @param resourceName is the name of the resource
    * @param packageName is the name of the new resource
    * @param typeResource is the type of the resource (e.g.ProcessingResource,
    *  LanguageResource or VisualResource)
    * @param className is the name of the class which implements the resource
    * @param interfacesList is the set of the interfaces that implements the resource
    * @param pathNewProject is the path where it will be the new resource
    */
  public void createResource( String resourceName,String packageName,
                              String typeResource,String className,
                              Set<String> interfacesList,String pathNewProject)
                              throws
                              IOException,ClassNotFoundException,
                              GateException,InterruptedException {
    // the current file created by the system
    File newFile = null;

    // define for reading from a file.properties
    Properties properties = new Properties();

    // the new path of the current file
    String newPathFile = null;

    // the path of file from template project
    String oldPathFile = null;

    // verify the input
    verifyInput(className,pathNewProject);

    // determine the interfaces that the resource implements and the class
    // that it extends
    String interfacesAndClass = getInterfacesAndClass (typeResource,
                                                  interfacesList);

    //determine the list with packages
    listPackages = determinePath(packageName);

    //add "/" at the end of the path of project
    if (!pathNewProject.endsWith("/")) pathNewProject = pathNewProject + "/";

    // determine the path of the main class
    String stringPackages = listPackages.get(0);
    for (int i=1;i<listPackages.size();i++) {
      stringPackages = stringPackages + "/"+listPackages.get(i);
    }

    // create the map with the names
    createNames(packageName,resourceName,className,
                                            stringPackages,interfacesAndClass);

    // take the content of the file with the structure of the template project
    InputStream inputStream = Files.getGateResourceAsStream(oldResource +
                              "file-list.properties");

    // put all the files and directories
    properties.load(inputStream);

    // close the input stream
    inputStream.close();

    // firstly create the directories
    String oldDirectories = properties.getProperty("directories");
    StringTokenizer token = new StringTokenizer(oldDirectories,",");
    while (token.hasMoreTokens()) {
      String propPathDirectory = token.nextToken();
      if (propPathDirectory.endsWith("___ALLPACKAGES___")) {
        //create every directory from the path of package
        newPathFile =
          propPathDirectory.substring(0,propPathDirectory.length()-18);
        // change the path according to input
        newPathFile = changeKeyValue(newPathFile,names);
        for (int i=0;i<listPackages.size();i++) {
          newPathFile = newPathFile + "/"+listPackages.get(i);
          newFile = new File(pathNewProject + newPathFile);
          newFile.mkdir();
        }//for
      } else {
        newPathFile = changeKeyValue(propPathDirectory,names);
        // change the path according to input
        newFile = new File(pathNewProject + newPathFile);
        newFile.mkdir();
      }//else
    }// while

    // secondly, create the files
    Enumeration<?> keyProperties = properties.propertyNames();
    // goes through all the files from the template project
    while (keyProperties.hasMoreElements()) {
      String key = (String)keyProperties.nextElement();
      if (!key.equals("directories")) {
        String oldFiles = properties.getProperty(key);
        token = new StringTokenizer(oldFiles,",");
        //go through all the files
        while (token.hasMoreTokens()) {
          String propPathFiles = token.nextToken();
          oldPathFile = changeKeyValue(propPathFiles,oldNames);

          // change the path according to input
          newPathFile = changeKeyValue(propPathFiles,names);

          // change the extension of the current file from "jav" to "java"
          if (newPathFile.endsWith("jav")) newPathFile = newPathFile +"a";

          // the content of the current file is copied on the disk

          // the current file for writing characters
          newFile = new File(pathNewProject+newPathFile);

          //create a filewriter for writing
          FileWriter fileWriter = new FileWriter(newFile);

          // get the input stream from
          InputStream currentInputStream =
              Files.getGateResourceAsStream(oldResource+oldPathFile);

          Reader inputStreamReader = new BomStrippingInputStreamReader(currentInputStream);
          int  charRead = 0;
          String text = null;
          while(
          (charRead = inputStreamReader.read(cbuffer,0,BUFF_SIZE)) != -1){
            text = new String (cbuffer,0,charRead);
            text = changeKeyValue(text,names);
            fileWriter.write(text ,0,text.length());
           }//while
           inputStreamReader.close();
           // close the input stream
           currentInputStream.close();
           // close the file for writing
           fileWriter.close();

          // change sh files in executable
          if (newPathFile.endsWith("configure")||newPathFile.endsWith(".sh"))
            executableFile(pathNewProject+newPathFile);

        }//while
      }//if
    }//while

  } // modify

  public static void main(String[] args) {
    System.out.println(System.getProperty("path.separator"));
    System.out.println("intre");
    System.out.println(System.getProperty("java.class.path"));
    BootStrap bootStrap = new BootStrap();
    Set<String> interfaces = new HashSet<String>();
    interfaces.add("gate.Document");
    interfaces.add("gate.ProcessingResource");
    try{

    bootStrap.createResource("morph","creole.sheffield.ac.lisa","LanguageResource",
      "Documente", interfaces, "z:/test");
    } catch (GateException ge) {
      ge.printStackTrace(Err.getPrintWriter());
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace(Err.getPrintWriter());
    } catch (IOException ioe) {
      ioe.printStackTrace(Err.getPrintWriter());
    } catch (InterruptedException ie){
      ie.printStackTrace(Err.getPrintWriter());
    }
  }// main

} // class BootStrap

/** FeatureMethod is a class encapsulating
  * information about the feature of a method such as the name, the return
  * type, the parameters types or exceptions types
  */
class FeatureMethod {
  /** the name of the method*/
  protected String nameMethod;

  /** the return value*/
  protected String valueReturn;

  /** the list with the types of the parameters */
  protected List<String> parameterTypes;

  /** the list with the types of the exceptions */
  protected List<String> exceptionTypes;

  FeatureMethod() {
    nameMethod = new String();
    valueReturn = new String();
    parameterTypes = new ArrayList<String>();
    exceptionTypes = new ArrayList<String>();
  }

  public String getNameMethod() {
    return nameMethod;
  }//getNameMethod

  public String getValueReturn() {
    return valueReturn;
  }//getValueReturn

  public List<String> getParameterTypes() {
    return parameterTypes;
  }//getParameterTypes

  public List<String> getExceptionTypes() {
    return exceptionTypes;
  }//getExceptionTypes

  public void setNameMethod(String newNameMethod) {
    nameMethod = newNameMethod;
  }//setDocument

  public void setValueReturn(String newValueReturn) {
    valueReturn = newValueReturn;
  }//setValueReturn

  public void setParameterTypes(List<String> newParameterTypes) {
    parameterTypes = newParameterTypes;
  }//setParameterTypes

  public void setExceptionTypes(List<String> newExceptionTypes) {
    exceptionTypes = newExceptionTypes;
  }//setExceptionTypes

  @Override
  public boolean equals(Object obj){
    if(obj == null)
      return false;
    FeatureMethod other;
    if(obj instanceof FeatureMethod){
      other = (FeatureMethod) obj;
    }else return false;

    // If their names are not equals then return false
    if((nameMethod == null) ^ (other.getNameMethod() == null))
      return false;
    if(nameMethod != null && (!nameMethod.equals(other.getNameMethod())))
      return false;

    // If their return values are not equals then return false
    if((valueReturn == null) ^ (other.getValueReturn() == null))
      return false;
    if(valueReturn != null && (!valueReturn.equals(other.getValueReturn())))
      return false;

    // If their parameters types are not equals then return false
    if((parameterTypes == null) ^ (other.getParameterTypes() == null))
      return false;
    if(parameterTypes != null &&
                            (!parameterTypes.equals(other.getParameterTypes())))
      return false;

    // If their exceptions types are not equals then return false
    if((exceptionTypes == null) ^ (other.getExceptionTypes() == null))
      return false;
    if(exceptionTypes != null &&
                            (!exceptionTypes.equals(other.getExceptionTypes())))
      return false;
    return true;
  }// equals

   @Override
  public int hashCode(){
    int hashCodeRes = 0;
    if (nameMethod != null )
       hashCodeRes ^= nameMethod.hashCode();
    if (valueReturn != null)
      hashCodeRes ^= valueReturn.hashCode();
    if(exceptionTypes != null)
      hashCodeRes ^= exceptionTypes.hashCode();
    if(parameterTypes != null)
      hashCodeRes ^= parameterTypes.hashCode();

    return  hashCodeRes;
  }// hashCode
}// class FeatureMethod

