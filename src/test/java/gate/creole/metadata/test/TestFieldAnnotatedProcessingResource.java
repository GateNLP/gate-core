package gate.creole.metadata.test;

import gate.creole.AbstractLanguageAnalyser;
import gate.creole.metadata.*;
import java.util.List;
import java.net.URL;

@CreoleResource(comment = "Field annotation comment",
        name = "Name from Annotation")
public class TestFieldAnnotatedProcessingResource extends AbstractLanguageAnalyser {

  private static final long serialVersionUID = 3714695512128601493L;

  @RunTime
  @CreoleParameter(comment = "First parameter comment")
  private URL firstParameter;
  
  public void setFirstParameter(URL value) {
  }
  
  public URL getFirstParameter() {
	  return null;
  }

  @Optional
  @CreoleParameter(comment = "Second parameter", defaultValue = "default")
  private List<String> secondParameter;

  public void setSecondParameter(List<String> value) {
  }
  
  public List<String> getSecondParameter() {
	  return null;
  }
}
