/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 11 Apr 2002
 *
 *  $Id: AnalyserRunningStrategy.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole;

import gate.*;
import gate.util.GateRuntimeException;

/**
 * A type running strategy that decides whether the associated PR needs to be
 * run based on the value of a specified feature on the document that the PR
 * needs to be run on.
 * It can only be used for {@link LanguageAnalyser}s because it needs the
 * document the PR will run on.
 */

public class AnalyserRunningStrategy implements RunningStrategy{

  public AnalyserRunningStrategy(LanguageAnalyser pr, int runMode,
                                 String featureName, String featureValue){
    this.pr = pr;
    this.runMode = runMode;
    this.featureName = featureName;
    this.featureValue = featureValue;
  }

  /**
   * If the runMode is {@link #RUN_ALWAYS} returns true.
   * If the runMode is {@link #RUN_NEVER} returns false.
   * If the runMode is {@link #RUN_CONDITIONAL}:
   * <ul>
   * <li>if the document is null returns false</li>
   * <li>if the document features are null
   *  <ul>
   *  <li>if {@link #featureName} is null returns true</li>
   *  <li>if {@link #featureName} is not null returns false</li>
   *  </ul></li>
   * <li>if the document features are not null
   * <ul>
   *  <li>if {@link #featureName} is null returns true</li>
   *  <li>if {@link #featureName} is not null and the document features contain
   *  such a feature returns true if the value of the feature is
   *  {@link #featureValue} and false otherwise.</li>
   * </ul></li>
   * </ul>
   * @return a <tt>boolean</tt> value.
   */
  @Override
  public boolean shouldRun() {
    if(runMode == RUN_ALWAYS) return true;
    if(runMode == RUN_NEVER) return false;
    if(runMode == RUN_CONDITIONAL){
      if(featureName == null || featureName.length() == 0) return true;
      Document doc = pr.getDocument();
      if(doc != null){
        FeatureMap fm = doc.getFeatures();
        if(fm != null){
          Object actualValue = fm.get(featureName);
          return (actualValue == null && featureValue == null)
                  ||
                 (actualValue != null && actualValue.equals(featureValue));
        }else return featureName == null;
      }else return false;
    }
    throw new GateRuntimeException("Unknown run mode!");
  }

  @Override
  public int getRunMode() {
    return runMode;
  }

  public void setRunMode(int mode){
    this.runMode = mode;
  }

  public void setFeatureName(String name){
    this.featureName = name;
  }

  public void setFeatureValue(String value){
    this.featureValue = value;
  }

  public String getFeatureName() {
    return featureName;
  }

  public String getFeatureValue() {
    return featureValue;
  }

  @Override
  public ProcessingResource getPR() {
    return pr;
  }

  public void setProcessingResource(ProcessingResource pr){
    if(pr instanceof LanguageAnalyser){
      this.pr = (LanguageAnalyser)pr;
    }else throw new GateRuntimeException(
      getClass().getName() + " can only be used for " +
      LanguageAnalyser.class.getName() + "!\n" +
      pr.getClass().getName() + " is not a " +
      LanguageAnalyser.class.getName() + "!");
  }

  protected LanguageAnalyser pr;
  protected int runMode = RUN_ALWAYS;
  protected String featureName;
  protected String featureValue;
}