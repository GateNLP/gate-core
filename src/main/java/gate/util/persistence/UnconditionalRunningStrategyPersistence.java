package gate.util.persistence;

import gate.ProcessingResource;
import gate.creole.ResourceInstantiationException;
import gate.creole.RunningStrategy.UnconditionalRunningStrategy;
import gate.persist.PersistenceException;

import java.io.Serializable;

/**
 * Persistent holder for {@link gate.creole.RunningStrategy.UnconditionalRunningStrategy}.
 */

public class UnconditionalRunningStrategyPersistence extends AbstractPersistence {

  @Override
  public void extractDataFromSource(Object source) throws PersistenceException {
    if(! (source instanceof UnconditionalRunningStrategy))
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                UnconditionalRunningStrategy.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + UnconditionalRunningStrategy.class.getName());
    UnconditionalRunningStrategy strategy = (UnconditionalRunningStrategy)source;
    this.pr = PersistenceManager.getPersistentRepresentation(strategy.getPR());
    this.shouldRun = strategy.shouldRun();
  }


  @Override
  public Object createObject() throws PersistenceException,
                                      ResourceInstantiationException {
    return new UnconditionalRunningStrategy(
            (ProcessingResource)PersistenceManager.
               getTransientRepresentation(pr,containingControllerName,initParamOverrides),
            shouldRun);
  }

  protected boolean shouldRun;

  protected Serializable pr;
  
  /**
   * Serialisation ID
   */
  private static final long serialVersionUID = 5049829826299681555L;
}