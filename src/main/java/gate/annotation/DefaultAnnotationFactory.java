/*
 * Created on Jul 25, 2005
 */
package gate.annotation;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.Node;

/**
 * The default Annotation factory that creates instances of {@link
 * gate.annotation.AnnotationImpl}.  If you wish to create an alternative
 * {@link gate.Annotation} class, you must create your own Annotation factory
 * that creates annotations of this type, and register it.
 * 
 * @author Ken Williams
 */
public class DefaultAnnotationFactory implements AnnotationFactory {
  
  /**
   * Creates a new DefaultAnnotationFactory.
   */
  public DefaultAnnotationFactory() {
  }
  
  @Override
  public Annotation createAnnotationInSet(AnnotationSet set, Integer id,
                                          Node start, Node end, String type,
                                          FeatureMap features) {
    AnnotationImpl a = new AnnotationImpl(id, start, end, type, features);
    set.add(a);
    return a;
  }
}
