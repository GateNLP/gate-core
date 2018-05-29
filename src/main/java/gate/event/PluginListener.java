package gate.event;

import gate.creole.Plugin;

public interface PluginListener {

  public void pluginLoaded(Plugin plugin);
    
  public void pluginUnloaded(Plugin plugin);
}
