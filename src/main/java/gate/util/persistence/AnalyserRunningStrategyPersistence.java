package gate.util.persistence;

import java.io.Serializable;

import gate.LanguageAnalyser;
import gate.creole.AnalyserRunningStrategy;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;

/**
 * Persistent holder for {@link gate.creole.AnalyserRunningStrategy}.
 */

public class AnalyserRunningStrategyPersistence extends AbstractPersistence {

  @Override
  public void extractDataFromSource(Object source) throws PersistenceException {
    if(! (source instanceof AnalyserRunningStrategy))
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                AnalyserRunningStrategy.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + AnalyserRunningStrategy.class.getName());
    AnalyserRunningStrategy strategy = (AnalyserRunningStrategy)source;
    this.pr = PersistenceManager.getPersistentRepresentation(strategy.getPR());
    this.runMode = strategy.getRunMode();
    this.featureName = strategy.getFeatureName();
    this.featureValue = strategy.getFeatureValue();
  }


  @Override
  public Object createObject() throws PersistenceException,
                                      ResourceInstantiationException {
    return new AnalyserRunningStrategy(
            (LanguageAnalyser)PersistenceManager.getTransientRepresentation(
              pr,containingControllerName,initParamOverrides),
                                       runMode, featureName, featureValue);
  }

  protected int runMode;

  protected String featureName;

  protected String featureValue;

  protected Serializable pr;
  /**
   * Serialisation ID
   */
  static final long serialVersionUID = -8288186597177634360L;
}