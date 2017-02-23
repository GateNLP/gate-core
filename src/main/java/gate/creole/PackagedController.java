package gate.creole;

import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.Optional;

import java.net.URL;
import java.util.List;

public abstract class PackagedController extends AbstractResource {
  
  private static final long serialVersionUID = -7281132236231594274L;
  
  protected URL url;

  @CreoleParameter
  public void setPipelineURL(URL url) {
    this.url = url;
  }

  public URL getPipelineURL() {
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
