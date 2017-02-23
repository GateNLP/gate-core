/*
 *  DetailsGroup.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: DetailsGroup.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */
package gate.gui.ontology;

import gate.creole.ontology.OResource;
import java.util.*;

/**
 * Represents each group (e.g. direct sub classes, all subclasses,
 * property values etc.) shown in the right hand side of panel when a
 * resource in the ontology tree is selected.
 * 
 * @author niraj
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DetailsGroup {
  public DetailsGroup(String groupName, boolean flag,
          Collection<OResource> collection) {
    name = groupName;
    expanded = flag;
    values = collection != null ? new ArrayList(collection) : new ArrayList();
  }

  public String getName() {
    return name;
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean flag) {
    expanded = flag;
  }

  public void setName(String s) {
    name = s;
  }

  public int getSize() {
    return values.size();
  }

  public Object getValueAt(int index) {
    return values.get(index);
  }

  public List getValues() {
    return values;
  }

  public void setValues(List list) {
    values = list;
  }

  /** Set one of the value in the list.
   *  Same behaviour as {@link List#set(int, Object)}. */
  public Object setValueAt(int index, Object value) {
    return values.set(index, value);
  }

  boolean expanded;

  String name;

  List values;
}
