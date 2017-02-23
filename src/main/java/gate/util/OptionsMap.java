/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 09/11/2001
 *
 *  $Id: OptionsMap.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */
package gate.util;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * A map that stores values as strings and provides support for converting some
 * frequently used types to and from string.<br>
 * Not very efficient as there is a lot of conversions from/to String.
 * The conversion could happen only when loading/saving from/to a file.
 */
public class OptionsMap extends TreeMap<Object, Object> {

  private static final long serialVersionUID = 1005431810071035036L;

  /**
   * Converts the value to string using {@link Strings#toString(Object)}
   * method and then stores it.
   * There is get methods for values that are a String, an Integer, a Boolean,
   * a Font, a List of String and a Map of String*String.
   */
  @Override
  public Object put(Object key, Object value) {
    if(value instanceof Font){
      Font font = (Font)value;
      String family = font.getFamily();
      int size = font.getSize();
      boolean italic = font.isItalic();
      boolean bold = font.isBold();
      value = family + "#" + size + "#" + italic + "#" + bold;
    }
    return super.put(key.toString(), Strings.toString(value));
  }

  public Object put(Object key, LinkedHashSet<String> value) {
    return super.put(key.toString(), Strings.toString(value));
  }

  public Object put(Object key, Map<String, String> value) {
    return super.put(key.toString(), Strings.toString(value));
  }

  /**
   * If the object stored under key is an Integer then returns its value
   * otherwise returns null.
   * @param key key associated to the value to retrieve
   * @return the associated integer
   */
  public Integer getInt(Object key) {
    try {
      return Integer.decode((String) get(key));
    } catch (Exception e) {
      return null;
    }
  }
  
  public Integer getInt(Object key, Integer defaultValue) {
    Integer value = getInt(key);
    
    return (value != null ? value : defaultValue);
  }
  
  public File getFile(Object key) {
    try {
      return new File((String)get(key));
    }
    catch (Exception e) {
      return null;
    }
  }

  /**
   * If the object stored under key is an Double then returns its value
   * otherwise returns null.
   * @param key key associated to the value to retrieve
   * @return the associated Double
   */
  public Double getDouble(Object key) {
    try {
      return Double.valueOf((String) get(key));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * If the object stored under key is a Boolean then returns its value
   * otherwise returns false.
   * @param key key associated to the value to retrieve
   * @return the associated boolean
   */
  public Boolean getBoolean(Object key) {
    try {
      return Boolean.valueOf((String) get(key));
    } catch (Exception e) {
      return false;
    }
  }
  
  public Boolean getBoolean(Object key, Boolean defaultValue) {
    try {
      if(containsKey(key)) return Boolean.valueOf((String)get(key));
    } catch(Exception e) {

    }

    return defaultValue;
  }

  /**
   * If the object stored under key is a String then returns its value
   * otherwise returns null.
   * @param key key associated to the value to retrieve
   * @return the associated string
   */
  public String getString(Object key) {
    try {
      return (String) get(key);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * If the object stored under key is a Font then returns its value
   * otherwise returns null.
   * @param key key associated to the value to retrieve
   * @return the associated font
   */
  public Font getFont(Object key) {
    try {
      String stringValue = (String) get(key);
      if (stringValue == null) { return null; }
      StringTokenizer strTok = new StringTokenizer(stringValue, "#", false);
      String family = strTok.nextToken();
      int size = Integer.parseInt(strTok.nextToken());
      boolean italic = Boolean.valueOf(strTok.nextToken());
      boolean bold = Boolean.valueOf(strTok.nextToken());
      HashMap<TextAttribute, Serializable> fontAttrs =
        new HashMap<TextAttribute, Serializable>();
      fontAttrs.put(TextAttribute.FAMILY, family);
      fontAttrs.put(TextAttribute.SIZE, (float) size);
      if(bold) fontAttrs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
      else fontAttrs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
      if(italic) fontAttrs.put(
        TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
      else fontAttrs.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
      return new Font(fontAttrs);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * If the object stored under key is a set then returns its value
   * otherwise returns an empty set.
   *
   * @param key key associated to the value to retrieve
   * @return the associated linked hash set
   */
  public LinkedHashSet<String> getSet(Object key) {
    return Strings.toSet((String) get(key), ", ");
  }


  /**
   * If the object stored under key is a map then returns its value
   * otherwise returns an empty map.
   *
   * @param key key associated to the value to retrieve
   * @return the associated map
   */
  public Map<String, String> getMap(Object key) {
      return Strings.toMap((String) get(key));
  }
  
  /**
   * Returns a String based view of the data. All the keys and values
   * are stored as strings in the underlying map, but because we want to
   * allow the addition of Object, which is then converted to a String,
   * we aren't ourselves typed as String to String.
   */
  public Map<String, String> getStringMap() {
    Map<String, String> data = new HashMap<String, String>();
    for(Map.Entry<Object, Object> entry : this.entrySet()) {
      data.put((String)entry.getKey(), (String)entry.getValue());
    }
    return data;
  }
}