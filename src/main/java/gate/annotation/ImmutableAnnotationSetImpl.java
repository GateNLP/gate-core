package gate.annotation;

import gate.Annotation;
import gate.Document;
import gate.FeatureMap;
import gate.Node;
import gate.util.InvalidOffsetException;
import java.util.Collection;
import java.util.Iterator;

public class ImmutableAnnotationSetImpl extends AnnotationSetImpl {
  
  private static final long serialVersionUID = 2658641359323106241L;

  /**
   * Constructs an ImmutableAnnotationSet. ImmutableAnnotationSet are returned
   * by the get* methods of AnnotationSet
   * 
   * @param annotations
   */
  public ImmutableAnnotationSetImpl(Document doc,
          Collection<Annotation> annotations) throws ClassCastException {
    super(doc);
    if(annotations != null) {
      Iterator<Annotation> iter = annotations.iterator();
      // adds the annotations one by one
      while(iter.hasNext()) {
        Annotation a = iter.next();
        annotsById.put(a.getId(), a);
      }
    }
  }

  /*****************************************************************************
   * The following methods throw an exception as they try to modify the state of
   * the object
   ****************************************************************************/
  @Override
  public Integer add(Node start, Node end, String type, FeatureMap features) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Integer add(Long start, Long end, String type, FeatureMap features) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean add(Annotation a) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(Integer id, Long start, Long end, String type,
          FeatureMap features) throws InvalidOffsetException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends Annotation> arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> arg0) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }
}
