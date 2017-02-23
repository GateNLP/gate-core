package gate.event;

import java.net.URL;

public interface PluginListener {

  public void pluginLoaded(URL url);
    
  public void pluginUnloaded(URL url);
}
