/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gate.util.persistence;

import java.util.Map;

/**
 *
 * @author johann
 */
public abstract class AbstractPersistence implements Persistence {

  private static final long serialVersionUID = -682494755574835875L;

  /**
   * The name of the controller, if any, that contains this persistence.
   * For example the PR persistence will contain the name of the controller
   * which (indirectly via the collection persistence) contains the PR.
   */
  protected String containingControllerName;
  /**
   * A map that contains init parameter overrides for controllerName+resourecName
   * keys. The key of this map is of the form 
   * containingControllerName+"\t"+resourceName and the value is a map that
   * associates the parameter name with a parameter value. By default this field
   * is null and no init parameters will ever get overriden. However a resource
   * (for example a controller) can set this field and fill the map and 
   * subsequent resources will have their init parameters overriden based
   * on the content of this map before they get created.
   */
  protected Map<String,Map<String,Object>> initParamOverrides;
}
