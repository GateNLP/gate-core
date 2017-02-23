/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  $Id: UtilBooleanQuery.java 11110 2009-08-20 19:29:40Z johann_p $
 */
package gate.creole.ontology;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class provides various Utility methods that can be used to
 * perform some generic options. For more information see javadoc of
 * each individual static method.
 * 
 * @author Niraj Aswani
 * @author Johann Petrak
 * 
 * @deprecated the use of this class and all its methods should be avoided
 * in the future. See the individual methods for replacements.
 */
@Deprecated
public class OntologyUtilities {

  /**
   * Checks the availability of an existing instance of the Ontology
   * with the given URL in the GATE's CreoleRegister. If found, returns
   * the first available one (doesn't guranttee in which order). If not
   * found, attempts to create one using OWLIM implementation with
   * RDF/XML as ontology type and if successful returns the newly
   * created instance of the ontology.
   * @throws ResourceInstantiationException
   * @deprecated - this method should be avoided
   */
  @Deprecated
  public static Ontology getOntology(URL url)
          throws ResourceInstantiationException {
    java.util.List<Resource> loadedOntologies = null;
    Ontology ontology = null;
    try {
      loadedOntologies = Gate.getCreoleRegister().getAllInstances(
              Ontology.class.getName());
    }
    catch(GateException ge) {
      throw new ResourceInstantiationException("Cannot list loaded ontologies",
              ge);
    }

    Iterator<Resource> ontIter = loadedOntologies.iterator();
    while(ontology == null && ontIter.hasNext()) {
      Ontology anOntology = (Ontology)ontIter.next();
      if(anOntology.getURL().equals(url)) {
        ontology = anOntology;
        break;
      }
    }

    try {
      // if not found, load it
      if(ontology == null) {
        // hardcoded to use OWL as the ontology type
        FeatureMap params = Factory.newFeatureMap();
        params.put("persistLocation", File.createTempFile("abc", "abc")
                .getParentFile().toURI().toURL());
        params.put("rdfXmlURL", url);
        ontology = (Ontology)Factory.createResource(
                "gate.creole.ontology.owlim.OWLIMOntologyLR", params);
      }
    }
    catch(Exception e) {
      throw new ResourceInstantiationException(
              "Cannot create a new instance of ontology", e);
    }
    return ontology;
  }

  /**
   * Given a URI, this methord returns the name part
   * @deprecated use {@link OURI#getResourceName} instead
   */
  @Deprecated
  public static String getResourceName(String uri) {
    int index = uri.lastIndexOf('#');
    if(index < 0) {
      index = uri.lastIndexOf('/');
      if(index < 0) {
        return uri;
      }
      if(index + 2 > uri.length()) {
        return uri;
      }
    }
    return uri.substring(index + 1, uri.length());
  }

  /**
   * Map containing uri and respective instance of datatypes
   */
  private static HashMap<String, DataType> datatypeMap = new HashMap<String, DataType>();
  static {
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#boolean", DataType
            .getBooleanDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#byte", DataType
            .getByteDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#date", DataType
            .getDateDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#decimal", DataType
            .getDecimalDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#double", DataType
            .getDoubleDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#duration", DataType
            .getDurationDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#float", DataType
            .getFloatDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#int", DataType
            .getIntDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#integer", DataType
            .getIntegerDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#long", DataType
            .getLongDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#negativeInteger",
            DataType.getNegativeIntegerDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#nonNegativeInteger",
            DataType.getNonNegativeIntegerDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#nonPositiveInteger",
            DataType.getNonPositiveIntegerDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#positiveInteger",
            DataType.getPositiveIntegerDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#short", DataType
            .getShortDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#string", DataType
            .getStringDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#time", DataType
            .getTimeDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#unsignedByte", DataType
            .getUnsignedByteDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#unsignedInt", DataType
            .getUnsignedIntDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#unsignedLong", DataType
            .getUnsignedLongDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#unsignedShort", DataType
            .getUnsignedShortDataType());
    datatypeMap.put("http://www.w3.org/2001/XMLSchema#dateTime", DataType
            .getDateTimeDataType());
    
  }

  /**
   * Map containing language codes and their respective locales
   */
  private static HashMap<String, Locale> localsMap = new HashMap<String, Locale>();
  static {
    localsMap.put("aa", OConstants.AFAR);
    localsMap.put("ab", OConstants.ABKHAZIAN);
    localsMap.put("af", OConstants.AFRIKAANS);
    localsMap.put("am", OConstants.AMHARIC);
    localsMap.put("ar", OConstants.ARABIC);
    localsMap.put("as", OConstants.ASSAMESE);
    localsMap.put("ay", OConstants.AYMARA);
    localsMap.put("az", OConstants.AZERBAIJANI);
    localsMap.put("ba", OConstants.BASHKIR);
    localsMap.put("be", OConstants.BYELORUSSIAN);
    localsMap.put("bg", OConstants.BULGARIAN);
    localsMap.put("bh", OConstants.BIHARI);
    localsMap.put("bi", OConstants.BISLAMA);
    localsMap.put("bn", OConstants.BENGALI);
    localsMap.put("bo", OConstants.TIBETAN);
    localsMap.put("br", OConstants.BRETON);
    localsMap.put("ca", OConstants.CATALAN);
    localsMap.put("co", OConstants.CORSICAN);
    localsMap.put("cs", OConstants.CZECH);
    localsMap.put("cy", OConstants.WELSH);
    localsMap.put("da", OConstants.DANISH);
    localsMap.put("de", OConstants.GERMAN);
    localsMap.put("dz", OConstants.BHUTANI);
    localsMap.put("el", OConstants.GREEK);
    localsMap.put("en", OConstants.ENGLISH);
    localsMap.put("eo", OConstants.ESPERANTO);
    localsMap.put("es", OConstants.SPANISH);
    localsMap.put("et", OConstants.ESTONIAN);
    localsMap.put("eu", OConstants.BASQUE);
    localsMap.put("fa", OConstants.PERSIAN);
    localsMap.put("fi", OConstants.FINNISH);
    localsMap.put("fj", OConstants.FIJI);
    localsMap.put("fo", OConstants.FAROESE);
    localsMap.put("fr", OConstants.FRENCH);
    localsMap.put("fy", OConstants.FRISIAN);
    localsMap.put("ga", OConstants.IRISH);
    localsMap.put("gd", OConstants.SCOTS);
    localsMap.put("gl", OConstants.GALICIAN);
    localsMap.put("gn", OConstants.GUARANI);
    localsMap.put("gu", OConstants.GUJARATI);
    localsMap.put("ha", OConstants.HAUSA);
    localsMap.put("he", OConstants.HEBREW);
    localsMap.put("hi", OConstants.HINDI);
    localsMap.put("hr", OConstants.CROATIAN);
    localsMap.put("hu", OConstants.HUNGARIAN);
    localsMap.put("hy", OConstants.ARMENIAN);
    localsMap.put("ia", OConstants.INTERLINGUA);
    localsMap.put("id", OConstants.INDONESIAN);
    localsMap.put("ie", OConstants.INTERLINGUE);
    localsMap.put("ik", OConstants.INUPIAK);
    localsMap.put("is", OConstants.ICELANDIC);
    localsMap.put("it", OConstants.ITALIAN);
    localsMap.put("iu", OConstants.INUKTITUT);
    localsMap.put("ja", OConstants.JAPANESE);
    localsMap.put("jw", OConstants.JAVANESE);
    localsMap.put("ka", OConstants.GEORGIAN);
    localsMap.put("kk", OConstants.KAZAKH);
    localsMap.put("kl", OConstants.GREENLANDIC);
    localsMap.put("km", OConstants.CAMBODIAN);
    localsMap.put("kn", OConstants.KANNADA);
    localsMap.put("ko", OConstants.KOREAN);
    localsMap.put("ks", OConstants.KASHMIRI);
    localsMap.put("ku", OConstants.KURDISH);
    localsMap.put("ky", OConstants.KIRGHIZ);
    localsMap.put("la", OConstants.LATIN);
    localsMap.put("ln", OConstants.LINGALA);
    localsMap.put("lo", OConstants.LAOTHIAN);
    localsMap.put("lt", OConstants.LITHUANIAN);
    localsMap.put("lv", OConstants.LATVIAN);
    localsMap.put("mg", OConstants.MALAGASY);
    localsMap.put("mi", OConstants.MAORI);
    localsMap.put("mk", OConstants.MACEDONIAN);
    localsMap.put("ml", OConstants.MALAYALAM);
    localsMap.put("mn", OConstants.MONGOLIAN);
    localsMap.put("mo", OConstants.MOLDAVIAN);
    localsMap.put("mr", OConstants.MARATHI);
    localsMap.put("ms", OConstants.MALAY);
    localsMap.put("mt", OConstants.MALTESE);
    localsMap.put("my", OConstants.BURMESE);
    localsMap.put("na", OConstants.NAURU);
    localsMap.put("ne", OConstants.NEPALI);
    localsMap.put("nl", OConstants.DUTCH);
    localsMap.put("no", OConstants.NORWEGIAN);
    localsMap.put("oc", OConstants.OCCITAN);
    localsMap.put("om", OConstants.OROMO);
    localsMap.put("or", OConstants.ORIYA);
    localsMap.put("pa", OConstants.PUNJABI);
    localsMap.put("pl", OConstants.POLISH);
    localsMap.put("ps", OConstants.PASHTO);
    localsMap.put("pt", OConstants.PORTUGUESE);
    localsMap.put("qu", OConstants.QUECHUA);
    localsMap.put("rm", OConstants.RHAETO_ROMANCE);
    localsMap.put("rn", OConstants.KIRUNDI);
    localsMap.put("ro", OConstants.ROMANIAN);
    localsMap.put("ru", OConstants.RUSSIAN);
    localsMap.put("rw", OConstants.KINYARWANDA);
    localsMap.put("sa", OConstants.SANSKRIT);
    localsMap.put("sd", OConstants.SINDHI);
    localsMap.put("sg", OConstants.SANGHO);
    localsMap.put("sh", OConstants.SERBO_CROATIAN);
    localsMap.put("si", OConstants.SINHALESE);
    localsMap.put("sk", OConstants.SLOVAK);
    localsMap.put("sl", OConstants.SLOVENIAN);
    localsMap.put("sm", OConstants.SAMOAN);
    localsMap.put("sn", OConstants.SHONA);
    localsMap.put("so", OConstants.SOMALI);
    localsMap.put("sq", OConstants.ALBANIAN);
    localsMap.put("sr", OConstants.SERBIAN);
    localsMap.put("ss", OConstants.SISWATI);
    localsMap.put("st", OConstants.SESOTHO);
    localsMap.put("su", OConstants.SUNDANESE);
    localsMap.put("sv", OConstants.SWEDISH);
    localsMap.put("sw", OConstants.SWAHILI);
    localsMap.put("ta", OConstants.TAMIL);
    localsMap.put("te", OConstants.TELUGU);
    localsMap.put("tg", OConstants.TAJIK);
    localsMap.put("th", OConstants.THAI);
    localsMap.put("ti", OConstants.TIGRINYA);
    localsMap.put("tk", OConstants.TURKMEN);
    localsMap.put("tl", OConstants.TAGALOG);
    localsMap.put("tn", OConstants.SETSWANA);
    localsMap.put("to", OConstants.TONGA);
    localsMap.put("tr", OConstants.TURKISH);
    localsMap.put("ts", OConstants.TSONGA);
    localsMap.put("tt", OConstants.TATAR);
    localsMap.put("tw", OConstants.TWI);
    localsMap.put("ug", OConstants.UIGHUR);
    localsMap.put("uk", OConstants.UKRAINIAN);
    localsMap.put("ur", OConstants.URDU);
    localsMap.put("uz", OConstants.UZBEK);
    localsMap.put("vi", OConstants.VIETNAMESE);
    localsMap.put("vo", OConstants.VOLAPUK);
    localsMap.put("wo", OConstants.WOLOF);
    localsMap.put("xh", OConstants.XHOSA);
    localsMap.put("yi", OConstants.YIDDISH);
    localsMap.put("yo", OConstants.YORUBA);
    localsMap.put("za", OConstants.ZHUANG);
    localsMap.put("zh", OConstants.CHINESE);
    localsMap.put("zu", OConstants.ZULU);
  }

  /**
   * Gets the respective datatype for the given datatype URI. If the URI
   * is invalid, the method returns null.
   * @deprecated use {@link DataType#getDataType} instead
   */
  @Deprecated
  public static DataType getDataType(String datatypeURI) {
    return datatypeMap.get(datatypeURI);
  }

  /**
   * Gets the respective locale for the given 2 character language code.
   * If the code doesn't match, the method returns null.
   * @deprecated use {@link DataType#getLocale} instead
   */
  @Deprecated
  public static Locale getLocale(String languageCode) {
    if(languageCode == null) return null;
    return localsMap.get(languageCode.toLowerCase());
  }

  /**
   * This method by using the default name space and the provided
   * ontology resource name, creates a new instance of URI. If
   * isAnonymousResource is set to true, an anonymous URI only using the
   * resource name is created.
   * 
   * @param ontology
   * @param aResourceName
   * @param isAnonymousResource
   * @return an instance of URI
   * @deprecated - use {@link Ontology#createOURI(String)} and related methods
   * instead.
   */
  @Deprecated
  public static URI createURI(Ontology ontology, String aResourceName,
          boolean isAnonymousResource) {
    if(isAnonymousResource) {
      return new URI(aResourceName, true);
    }

    String uri = ontology.getDefaultNameSpace() + aResourceName;
    return new URI(uri, false);
  }
}
