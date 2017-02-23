/*
 *  DataType.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: DataType.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import gate.util.GateRuntimeException;

import java.util.HashMap;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This class provides a list of datatypes, supported by the ontology
 * API.
 * 
 * @author Niraj Aswani
 */
public class DataType {
  /**
   * for each datatype, there exists a XML Schema URI in ontology which
   * is used to denote the specific datatype. For example to denote the
   * boolean datatype one would have to use
   * "http://www.w3.org/2001/XMLSchema#boolean".
   * 
   */
  // protected OURI xmlSchemaURI;
  protected String xmlSchemaURIString;

  static DatatypeFactory datatypeFactory = null;
  static {
    try {
      datatypeFactory = DatatypeFactory.newInstance();
    }
    catch(DatatypeConfigurationException e) {
      throw new GateRuntimeException(
              "could not initialize data type factory :\n", e);
    }
  }

  /**
   * Constructor
   * 
   * @param xmlSchemaURI for each datatype, there exists a XML Schema
   *          URI in ontology which is used to denote the specific
   *          datatype. For example to denote the boolean datatype one
   *          would have to use
   *          "http://www.w3.org/2001/XMLSchema#boolean".
   */
  public DataType(OURI xmlSchemaURI) {
    // this.xmlSchemaURI = xmlSchemaURI;
    this.xmlSchemaURIString = xmlSchemaURI.toString();
  }

  public DataType(String xmlSchemaURIString) {
    this.xmlSchemaURIString = xmlSchemaURIString;
    // TODO: make checks here, the schema URI really must be one of
    // those
    // defined in the standard!
  }

  public boolean isStringDataType() {
    return this.xmlSchemaURIString
            .equals("http://www.w3.org/2001/XMLSchema#string");
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#boolean" datatype.
   */
  public static DataType getBooleanDataType() {
    try {
      return new BooleanDT("http://www.w3.org/2001/XMLSchema#boolean");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#byte" datatype.
   */
  public static DataType getByteDataType() {
    try {
      return new ByteDT("http://www.w3.org/2001/XMLSchema#byte");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#date" datatype.
   */
  public static DataType getDateDataType() {
    try {
      return new DateDT("http://www.w3.org/2001/XMLSchema#date");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#decimal" datatype.
   */
  public static DataType getDecimalDataType() {
    try {
      return new DoubleDT("http://www.w3.org/2001/XMLSchema#decimal");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#double" datatype.
   */
  public static DataType getDoubleDataType() {
    try {
      return new DoubleDT("http://www.w3.org/2001/XMLSchema#double");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#duration" datatype.
   */
  public static DataType getDurationDataType() {
    try {
      return new LongDT("http://www.w3.org/2001/XMLSchema#duration");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#float" datatype.
   */
  public static DataType getFloatDataType() {
    try {
      return new FloatDT("http://www.w3.org/2001/XMLSchema#float");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#int" datatype.
   */
  public static DataType getIntDataType() {
    try {
      return new IntegerDT("http://www.w3.org/2001/XMLSchema#int");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#integer" datatype.
   */
  public static DataType getIntegerDataType() {
    try {
      return new IntegerDT("http://www.w3.org/2001/XMLSchema#integer");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#long" datatype.
   */
  public static DataType getLongDataType() {
    try {
      return new LongDT("http://www.w3.org/2001/XMLSchema#long");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#negativeInteger"
   * datatype.
   */
  public static DataType getNegativeIntegerDataType() {
    try {
      return new NegativeIntegerDT(
              "http://www.w3.org/2001/XMLSchema#negativeInteger");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#nonNegativeInteger"
   * datatype.
   */
  public static DataType getNonNegativeIntegerDataType() {
    try {
      return new NonNegativeIntegerDT(
              "http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#nonPositiveInteger"
   * datatype.
   */
  public static DataType getNonPositiveIntegerDataType() {
    try {
      return new NegativeIntegerDT(
              "http://www.w3.org/2001/XMLSchema#nonPositiveInteger");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#positiveInteger"
   * datatype.
   */
  public static DataType getPositiveIntegerDataType() {
    try {
      return new NonNegativeIntegerDT(
              "http://www.w3.org/2001/XMLSchema#positiveInteger");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#short" datatype.
   */
  public static DataType getShortDataType() {
    try {
      return new ShortDT("http://www.w3.org/2001/XMLSchema#short");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#string" datatype.
   */
  public static DataType getStringDataType() {
    try {
      return new DataType("http://www.w3.org/2001/XMLSchema#string");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#time" datatype.
   */
  public static DataType getTimeDataType() {
    try {
      return new TimeDT("http://www.w3.org/2001/XMLSchema#time");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#dateTime" datatype.
   */
  public static DataType getDateTimeDataType() {
    try {
      return new DateTimeDT(
              "http://www.w3.org/2001/XMLSchema#dateTime");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#unsignedByte"
   * datatype.
   */
  public static DataType getUnsignedByteDataType() {
    try {
      return new UnsignedByteDT(
              "http://www.w3.org/2001/XMLSchema#unsignedByte");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#unsignedInt"
   * datatype.
   */
  public static DataType getUnsignedIntDataType() {
    try {
      return new NonNegativeIntegerDT(
              "http://www.w3.org/2001/XMLSchema#unsignedInt");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#unsignedLong"
   * datatype.
   */
  public static DataType getUnsignedLongDataType() {
    try {
      return new UnsignedLongDT(
              "http://www.w3.org/2001/XMLSchema#unsignedLong");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  /**
   * denotes the "http://www.w3.org/2001/XMLSchema#unsignedShort"
   * datatype.
   */
  public static DataType getUnsignedShortDataType() {
    try {
      return new UnsignedShortDT(
              "http://www.w3.org/2001/XMLSchema#unsignedShort");
    }
    catch(InvalidURIException iue) {
      return null;
    }
  }

  @Deprecated
  public OURI getXmlSchemaURI() {
    return new URI(xmlSchemaURIString, false);
  }

  public String getXmlSchemaURIString() {
    return xmlSchemaURIString;
  }

  /**
   * Compares if the two objects are same, i.e. if their string
   * representations are identical.
   */
  @Override
  public boolean equals(Object o) {
    if(o instanceof DataType) {
      DataType dt = (DataType)o;
      // return
      // this.xmlSchemaURI.getNameSpace().equals(dt.xmlSchemaURI.getNameSpace())
      // &&
      // this.xmlSchemaURI.getResourceName().equals(dt.xmlSchemaURI.getResourceName());
      return this.getXmlSchemaURIString().equals(dt.getXmlSchemaURIString());
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return getXmlSchemaURIString().hashCode();
  }

  /**
   * Checks whether the provided value is a valid value for the datatype
   * (e.g. if the datatype is integer, parsing a string value into
   * integer causes the exception or not.
   * 
   * @param value
   * @return true, if the provided value can be parsed correctly into
   *         the datatype, otherwise - false.
   */
  public boolean isValidValue(String value) {
    return true;
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
   */
  public static DataType getDataType(String datatypeURI) {
    return datatypeMap.get(datatypeURI);
  }

  /**
   * Gets the respective locale for the given 2 character language code.
   * If the code doesn't match, the method returns null.
   */
  public static Locale getLocale(String languageCode) {
    if(languageCode == null) return null;
    return localsMap.get(languageCode.toLowerCase());
  }

}

/**
 * Boolean DataType
 * 
 * @author niraj
 */
class BooleanDT extends DataType {
  public BooleanDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public BooleanDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * A Method to validate the boolean value
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      if((Boolean.parseBoolean(value) + "").equalsIgnoreCase(value))
        return true;
      return false;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Byte DataType
 * 
 * @author niraj
 * 
 */
class ByteDT extends DataType {
  public ByteDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public ByteDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      if((Byte.parseByte(value) + "").equalsIgnoreCase(value)) return true;
      ;
      return false;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Double Datatype
 * 
 * @author niraj
 * 
 */
class DoubleDT extends DataType {
  public DoubleDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public DoubleDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      Double.parseDouble(value);
      return true;
    }
    catch(Exception e) {
      return false;
    }
  }
}

class DateDT extends DataType {
  public DateDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public DateDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      XMLGregorianCalendar cal = datatypeFactory.newXMLGregorianCalendar(value);
      String schema = cal.getXMLSchemaType().getNamespaceURI() + "#"
              + cal.getXMLSchemaType().getLocalPart();
      return schema.equals(getXmlSchemaURIString());
    }
    catch(Exception e) {
      return false;
    }
  }
}

class DateTimeDT extends DataType {
  public DateTimeDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public DateTimeDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      XMLGregorianCalendar cal = datatypeFactory.newXMLGregorianCalendar(value);
      String schema = cal.getXMLSchemaType().getNamespaceURI() + "#"
              + cal.getXMLSchemaType().getLocalPart();
      return schema.equals(getXmlSchemaURIString());
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}

class TimeDT extends DataType {
  public TimeDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public TimeDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      XMLGregorianCalendar cal = datatypeFactory.newXMLGregorianCalendar(value);
      String schema = cal.getXMLSchemaType().getNamespaceURI() + "#"
              + cal.getXMLSchemaType().getLocalPart();
      return schema.equals(getXmlSchemaURIString());
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Long Datatype
 * 
 * @author niraj
 * 
 */
class LongDT extends DataType {
  public LongDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public LongDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      if((Long.parseLong(value) + "").equalsIgnoreCase(value)) return true;
      ;
      return false;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Float Datatype
 * 
 * @author niraj
 * 
 */
class FloatDT extends DataType {
  public FloatDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public FloatDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      Float.parseFloat(value);
      return true;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Integer Datatype
 * 
 * @author niraj
 * 
 */
class IntegerDT extends DataType {
  public IntegerDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public IntegerDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      if((Integer.parseInt(value) + "").equalsIgnoreCase(value)) return true;
      ;
      return false;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Negative Integer Datatype
 * 
 * @author niraj
 * 
 */
class NegativeIntegerDT extends DataType {
  public NegativeIntegerDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public NegativeIntegerDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      int intVal = Integer.parseInt(value);
      if(!(intVal + "").equalsIgnoreCase(value)) return false;
      return intVal < 0;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * NonNegativeInteger Datatype
 * 
 * @author niraj
 * 
 */
class NonNegativeIntegerDT extends DataType {
  public NonNegativeIntegerDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public NonNegativeIntegerDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      int intVal = Integer.parseInt(value);
      if(!(intVal + "").equalsIgnoreCase(value)) return false;
      return intVal > -1;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * Short Datatype
 * 
 * @author niraj
 * 
 */
class ShortDT extends DataType {
  public ShortDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public ShortDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      short intVal = Short.parseShort(value);
      if(!(intVal + "").equalsIgnoreCase(value)) return false;
      return true;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * UnsignedByte Datatype
 * 
 * @author niraj
 * 
 */
class UnsignedByteDT extends DataType {
  public UnsignedByteDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public UnsignedByteDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      byte byteVal = Byte.parseByte(value);
      if(!(byteVal + "").equalsIgnoreCase(value)) return false;
      return byteVal > -1;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * UnsignedLong Datatype
 * 
 * @author niraj
 * 
 */
class UnsignedLongDT extends DataType {
  public UnsignedLongDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public UnsignedLongDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      long longVal = Long.parseLong(value);
      if(!(longVal + "").equalsIgnoreCase(value)) return false;
      return longVal > -1;
    }
    catch(Exception e) {
      return false;
    }
  }
}

/**
 * UnsignedShort Datatype
 * 
 * @author niraj
 * 
 */
class UnsignedShortDT extends DataType {
  public UnsignedShortDT(OURI xmlSchemaURI) {
    super(xmlSchemaURI);
  }
  public UnsignedShortDT(String xmlSchemaURIString) {
    super(xmlSchemaURIString);
  }

  /**
   * Methods check if the value is valid for the datatype
   */
  @Override
  public boolean isValidValue(String value) {
    try {
      short shortVal = Short.parseShort(value);
      if(!(shortVal + "").equalsIgnoreCase(value)) return false;
      return shortVal > -1;
    }
    catch(Exception e) {
      return false;
    }
  }

}
