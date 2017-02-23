package gate.util.persistence;

import gate.creole.ConditionalController;
import gate.creole.ResourceInstantiationException;
import gate.creole.RunningStrategy;
import gate.persist.PersistenceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Persistence handler for {@link gate.creole.ConditionalController}s
 */

public class ConditionalControllerPersistence extends ControllerPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    if(! (source instanceof ConditionalController)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                ConditionalController.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + ConditionalController.class.getName());
    }
    super.extractDataFromSource(source);

    ConditionalController controller = (ConditionalController)source;

    /*strategiesList = new ArrayList<RunningStrategy>(controller.getRunningStrategies().size());

    Iterator<RunningStrategy> stratIter = controller.getRunningStrategies().iterator();
    while(stratIter.hasNext()) ((List)strategiesList).add(stratIter.next());*/
    strategiesList = new ArrayList<RunningStrategy>(controller.getRunningStrategies());

    strategiesList = PersistenceManager.
                     getPersistentRepresentation(strategiesList);
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    ConditionalController controller =
      (ConditionalController)super.createObject();
    controller.setRunningStrategies(
      (Collection<RunningStrategy>)PersistenceManager.getTransientRepresentation(
            strategiesList,resourceName,initParamOverrides));
    return controller;
  }
  protected Serializable strategiesList;

  /**
   * Serialisation ID
   */
  static final long serialVersionUID = -746291109981304574L;
}