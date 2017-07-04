package gate.creole;

import java.util.List;

import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.Optional;

public abstract class PackagedController extends AbstractResource {
  
  private static final long serialVersionUID = -7281132236231594274L;
  
  protected ResourceReference url;

  @CreoleParameter
  public void setPipelineURL(ResourceReference url) {
    this.url = url;
  }

  public ResourceReference getPipelineURL() {
    return url;
  }
  
  private List<String> menu = null;
  
  @CreoleParameter
  @Optional
  public void setMenu(List<String> menu) {
    this.menu = menu;
  }
  
  public List<String> getMenu() {
    return menu;
  }
}
