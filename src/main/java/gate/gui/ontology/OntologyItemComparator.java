/*
 *  OntologyItemComparator.java
 *
 *  Niraj Aswani, 09/March/07
 *
 *  $Id: OntologyItemComparator.html,v 1.0 2007/03/09 16:13:01 niraj Exp $
 */

package gate.gui.ontology;

import java.io.Serializable;
import java.util.Comparator;

import gate.creole.ontology.OResource;

/**
 * A Comparator that sorts the resources in ontology based on their URIs
 * 
 * @author niraj
 * 
 */
public class OntologyItemComparator implements Comparator<OResource>, Serializable {

  private static final long serialVersionUID = 4652904428995140296L;

  @Override
  public int compare(OResource resource1, OResource resource2) {
    if (resource1 == null) return (resource2 != null) ? -1 : 0;
    if (resource2 == null) return 1;
    String name1 = resource1.getONodeID().getResourceName();
    String name2 = resource2.getONodeID().getResourceName();
    if (name1 == null) return (name2 != null) ? -1 : 0;
    if (name2 == null) return 1;
    else return name1.compareTo(name2);
  }
}
