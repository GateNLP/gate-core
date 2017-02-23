/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 24 Apr 2008
 *
 *  $Id: AnnotationDataImpl.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.gui.annedit;

import gate.Annotation;
import gate.AnnotationSet;

/**
 * A simple reusable implementation of {@link AnnotationData}.
 */
public class AnnotationDataImpl implements AnnotationData {
  public AnnotationDataImpl(AnnotationSet set, Annotation ann){
    this.ann = ann;
    this.set = set;
  }
  
  
  Annotation ann;
  AnnotationSet set;
  /**
   * @return the ann
   */
  @Override
  public Annotation getAnnotation() {
    return ann;
  }
  /**
   * @return the set
   */
  @Override
  public AnnotationSet getAnnotationSet() {
    return set;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ann == null) ? 0 : ann.hashCode());
    String setName = set.getName();
    result = prime * result + ((setName == null) ? 0 : setName.hashCode());
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(!(obj instanceof AnnotationDataImpl)) return false;
    final AnnotationDataImpl other = (AnnotationDataImpl)obj;
    if(ann == null) {
      if(other.ann != null) return false;
    }
    else if(!ann.equals(other.ann)) return false;
    if(set == null) {
      if(other.set != null) return false;
    }
    else if(set != other.set) return false;
    return true;
  }
}
