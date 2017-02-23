package gate.util.spring;

import org.springframework.core.io.Resource;

/**
 * Holder class for a single {@link Resource} that points to a
 * GATE plugin.  {@link Init} scans for all beans in its defining
 * BeanFactory that are instances of this class, and loads the
 * plugins they point to after GATE has been initialized.
 */
public class ExtraGatePlugin {
  private Resource location;
  
  public void setLocation(Resource location) {
    this.location = location;
  }
  
  public Resource getLocation() {
    return location;
  }
}
