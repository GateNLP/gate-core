package gate.creole.metadata.test;

import gate.creole.AbstractLanguageAnalyser;
import gate.creole.metadata.*;
import java.util.List;
import java.net.URL;

@CreoleResource(comment = "Superclass comment", isPrivate = true,
        name = "This should be overridden by the XML")
public class TestSuperclassProcessingResource extends AbstractLanguageAnalyser {

  private static final long serialVersionUID = -7138568686172001909L;

  @RunTime
  @CreoleParameter(comment = "First parameter comment")
  public void setFirstParameter(URL value) {
  }

  @Optional
  @CreoleParameter(comment = "Second parameter", defaultValue = "default")
  public void setSecondParameter(List<String> value) {
  }

  @CreoleParameter(comment = "Should be hidden in subclass")
  public void setThirdParameter(Integer value) {
  }
}
