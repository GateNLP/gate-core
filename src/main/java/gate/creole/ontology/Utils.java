/*
 *  Utils.java
 *
 *  $Id: Utils.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole.ontology;

/**
 *
 * @author Johann Petrak
 */
public class Utils {

  // TODO: only used in the gui .. move?
  @Deprecated
  public static boolean hasSystemNameSpace(String uri) {
    if(uri.startsWith("http://www.w3.org/2002/07/owl#")) {
      return true;
    } else if(uri.startsWith("http://www.w3.org/2001/XMLSchema#")) {
      return true;
    } else if(uri.startsWith("http://www.w3.org/2000/01/rdf-schema#")) {
      return true;
    } else if(uri.startsWith("http://www.w3.org/1999/02/22-rdf-syntax-ns#")) {
      return true;
    } else {
      return false;
    }
  }

  // TODO: it seems this is only used for the GUI ... move?
  @Deprecated
  public static String getRestrictionName(Restriction res) {
    String className = "Unknown";
    if(res instanceof HasValueRestriction) {
      className = "http://www.w3.org/2002/07/owl#hasValue";
    } else if(res instanceof AllValuesFromRestriction) {
        className = "http://www.w3.org/2002/07/owl#allValuesFrom";
    } else if(res instanceof SomeValuesFromRestriction) {
        className = "http://www.w3.org/2002/07/owl#someValuesFrom";
    } else if(res instanceof CardinalityRestriction) {
        className = "http://www.w3.org/2002/07/owl#cardinality";
    } else if(res instanceof MinCardinalityRestriction) {
        className = "http://www.w3.org/2002/07/owl#minCardinality";
    } else if(res instanceof MaxCardinalityRestriction) {
        className = "http://www.w3.org/2002/07/owl#maxCardinality";
    } else {
        className = "Annonymous";
    }
    return className;
  }

}


