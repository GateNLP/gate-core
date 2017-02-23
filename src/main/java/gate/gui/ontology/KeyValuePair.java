/**
 * 
 */
package gate.gui.ontology;

import gate.creole.ontology.OResource;

/**
 * @author niraj
 * 
 */
public class KeyValuePair {

  protected OResource sourceResource;

  protected String key;

  protected Object value;

  protected boolean editable;

  /**
   * 
   */
  public KeyValuePair(OResource sourceResource, String key, Object value,
          boolean editable) {
    this.sourceResource = sourceResource;
    this.key = key;
    this.value = value;
    this.editable = editable;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public OResource getSourceResource() {
    return sourceResource;
  }

  public void setSourceResource(OResource sourceResource) {
    this.sourceResource = sourceResource;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

}
