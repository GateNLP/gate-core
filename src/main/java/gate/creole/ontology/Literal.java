/*
 *  Literal.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: Literal.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.creole.ontology;

import java.util.Locale;

/**
 * The class Literal represents a literal value as defined in section 6.5 of 
 * http://www.w3.org/TR/rdf-concepts . 
 * A literal value consists of a lexical form which is represented
 * as a unicode string and in addition optionally either a language 
 * tag or a datatype.
 * 
 * @author Niraj Aswani, Johann Petrak
 */

// NOTE: (Johann Petrak) This may need to get extended in the future to 
// also deal with the rdf:PlainLiteral datatype and the different
// way of how literals of that type are represented.
// See http://www.w3.org/TR/rdf-plain-literal/

public class Literal {
  /**
   * The actual value (the lexical form) of the literal, represented 
   * as a unicode string.
   */
  private String value;

  /**
   * The language tag for the literal if it is a plain literal. If there is
   * a language tag, there cannot be a data type.
   */
  private Locale language;

  /**
   * The data type of the literal if it is a typed literal. If there is 
   * a data type, there cannot be a language tag. 
   * Data types are represented as gate.creole.ontology.DataType objects
   * but the only information that is used internally is the datatype 
   * URI String (as returned by DataType.getXmlSchemaURIString())
   */
  private DataType dataType;

  /**
   * Create a plain literal (i.e an untyped literal) without a language tag
   * (the language is null).
   * A plain literal cannot have a data type (the datatype will always be null).
   */
  public Literal(String value) {
    this.value = value;
    // removed by JP: a plain literal does not have a language tag nor
    // a datatype.
    //this.language = OConstants.ENGLISH;
    //this.dataType = DataType.getStringDataType();
    this.language = null;
    this.dataType = null;
  }

  /**
   * Create a plain literal (i.e. an untyped literal) with a language
   * tag that corresponds to the string returned by language.getLanguage().
   * Any information stored in the Java Locale object other than the
   * language code is ignored and discarded when stored in the ontology.
   * A plain literal cannot have a data type (the datatype will alwats be null).
   * 
   * @param value 
   * @param language
   * @deprecated Use the constructor Literal(String value, String languagetag) 
   * instead
   */
  @Deprecated
  public Literal(String value, Locale language) {
    this.value = value;
    this.language = language;
    // removed by JP: a plain literal with a language tag must
    // not have a datatype
    // this.dataType = DataType.getStringDataType(); 
    this.dataType = null;
  }

  /**
   * Create a plain literal associated with a language tag. A language tag
   * should be an official two-character lowercase language code.
   * 
   * @param value
   * @param languagetag 
   */
  public Literal(String value, String languagetag) {
    this.value = value;
    this.language = new Locale(languagetag);
    this.dataType = null;
  }
  
  
  /**
   * Create a typed literal. A typed literal is associated with a datatype
   * and cannot have a language tag (the language will always be null).
   * 
   * @param value 
   * @param dataType
   * @throws InvalidValueException
   */
  public Literal(String value, DataType dataType) throws InvalidValueException {
    this.value = value;
    // removed by JP: a typed literal must not have a language tag
    // this.language = OConstants.ENGLISH;
    this.language = null;
    this.dataType = dataType;
    // lets check if the provided val is valid for the supplied
    // dataType
    if(!dataType.isValidValue(this.value)) {
      throw new InvalidValueException("The value :\"" + this.value
              + "\" is not compatible with the dataType \""
              + dataType.getXmlSchemaURIString() + "\"");
    }
  }

  /**
   * Gets the datatype of the literal. This will return null for all plain
   * literals (i.e. literals that are not associated with a data type).
   */
  public DataType getDataType() {
    return dataType;
  }

  /**
   * Returns the value (lexical form) associated with this instance of literal.
   */
  public String getValue() {
    return value;
  }

  /**
   * Returns the language associated with this literal represented as a 
   * Locale object. Note that the only useful information in the Locale
   * object is the language code which can be retrieved with the 
   * getLanguage() method of the Locale object.
   * @deprecated Use the method getLanguageTag() instead.
   */
  @Deprecated
  public Locale getLanguage() {
    return language;
  }

  /**
   * The language tag of the literal as a two-character lowercase language code
   * or null if no language tag
   * is associated. This always returns null for typed literals (literals
   * associated with a data type).
   * 
   * @return the language tag as String
   */
  public String getLanguageTag() {
    if(language == null) {
      return null;
    } else {
      return language.getLanguage();
    }
  }
  
  
  /**
   * This method always returns the string representation of the 
   * literal value in the same way as getValue().
   * For a representation that also includes the language tag or 
   * data type, use toTurtle().
   * 
   * @return The value of the literal as String, identical to getValue()
   */
  @Override
  public String toString() {
    return value;
  }


  /**
   * The representation of the literal in Turtle language
   * (see http://www.w3.org/TeamSubmission/turtle/)
   * Note that datatype URIs for typed literals 
   * will always be represented as full URIs
   * and not use the xsd namespace.
   * 
   * @return A String that contains the representation of the literal 
   * in Turtle language 
   */
  public String toTurtle() {
    String val = this.value;
    val = val.replace("\"", "\\\"");
    val = "\""+val+"\"";
    if(dataType == null) {
      if(language != null) {
        val = val+"@"+language;
      } 
    } else {
      val = val+"^^<" + dataType.getXmlSchemaURIString() + ">";
    }
    return val;
  }

  @Override
  public int hashCode() {
    int hash = 17 + (value == null ? 0 : value.hashCode());
    hash = 37*hash + (language == null ? 0 :  language.hashCode());
    hash = 37*hash + (dataType == null ? 0 : dataType.hashCode());
    return hash;
  }

  @Override
  /**
   * Two literals are considered equal if the strings representing their
   * values are equal and if they have identical language tags or 
   * identical data types or both do not have either a language tag or
   * a data type.
   */
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Literal other = (Literal) obj;
    // if both are Literals, they are only equal if the dataTypes are the same
    // and if the languages are the same and if the values are the same
    if ((this.value == null) && (other.value == null)) {
      return true;
    }
    if(!(this.dataType == null ?
          other.dataType == null : this.dataType.equals(other.dataType)) ||
       !(this.language == null ?
          other.language == null : this.language.equals(other.language)) ||
       !(this.value == null ?
          other.value == null : this.value.equals(other.value))) {
      return false;
    }
    return true;
  }

}
