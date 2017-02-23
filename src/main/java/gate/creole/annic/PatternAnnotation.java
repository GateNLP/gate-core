/*
 *  PatternAnnotation.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: PatternAnnotation.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Pattern Annotation is similar to a GATE Annotation except that it
 * doesn't have any annotation ID but it contains its position in the token stream
 * that is created when indexing documents.
 * 
 * @author niraj
 * 
 */
public class PatternAnnotation implements Serializable {

  /**
   * serial version id
   */
  private static final long serialVersionUID = 3690197637685197108L;

  /**
   * Annotation Type
   */
  private String type;

  /**
   * Text of the annotation
   */
  private String text;

  /**
   * Start Offset
   */
  private int stOffset;

  /**
   * End Offset
   */
  private int enOffset;

  /**
   * FeatureMap
   */
  private Map<String, String> features;

  /**
   * Position in the token stream
   */
  private int position = 0;

  /**
   * Constructor
   */
  public PatternAnnotation() {
    features = new HashMap<String, String>();
  }

  /**
   * Sets the Type
   * @param type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Sets the TExt
   * @param text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Sets the start offset
   * @param st
   */
  public void setStOffset(int st) {
    this.stOffset = st;
  }

  /**
   * Sets the end offset
   * @param en
   */
  public void setEnOffset(int en) {
    this.enOffset = en;
  }

  /**
   * Adds a feature
   * @param key
   * @param val
   */
  public void addFeature(String key, String val) {
    features.put(key, val);
  }

  /**
   * Sets the position
   * @param pos
   */
  public void setPosition(int pos) {
    position = pos;
  }

  /**
   * Gets the Features
   */
  public Map<String, String> getFeatures() {
    return features;
  }

  /**
   * Gets the value of a feature
   */
  public String getFeature(String key) {
    return features.get(key);
  }

  /**
   * Gets the type of the annotation
   */
  public String getType() {
    return this.type;
  }

  /**
   * Gets the text of the annotation
   */
  public String getText() {
    return this.text;
  }

  /**
   * Gets the start offset
   */
  public int getStartOffset() {
    return stOffset;
  }

  /**
   * Gets the end offset
   */
  public int getEndOffset() {
    return enOffset;
  }

  /**
   * Gets the position of this annotation in the token stream.
   */
  public int getPosition() {
    return position;
  }
}
