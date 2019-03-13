package gate.util.maven;

import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

import java.util.List;
import java.util.ArrayList;

/**
 * Simple Aether ProxySelector implementation that tries a series of selectors
 * in turn and returns the first non-null response, or null if all selectors in
 * the chain return null.
 */
public class ChainedProxySelector implements ProxySelector {

  private List<ProxySelector> selectors = new ArrayList<>();

  public void addSelector(ProxySelector sel) {
    selectors.add(sel);
  }

  public Proxy getProxy(RemoteRepository repo) {
    Proxy proxy = null;
    
    for (ProxySelector selector : selectors) {
      if (selector != null) {
        proxy = selector.getProxy(repo);
      }
      
      if (proxy != null) return proxy;
    }
    
    return proxy;
  }
}
