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
 *  $Id: RunningStrategy.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.creole;


import gate.ProcessingResource;

/**
 * Base interface for objects that are used to decide whether a PR member of a
 * {@link ConditionalController} needs to be run.
 */

public interface RunningStrategy{
  /**
   * Returns true if the associated PR should be run.
   * @return a boolean value.
   */
  public boolean shouldRun();

  /**
   * Returns the run mode (see {@link #RUN_ALWAYS}, {@link #RUN_NEVER},
   * {@link #RUN_CONDITIONAL}).
   * @return and int value.
   */
  public int getRunMode();

  /**
   * Gets the associated ProcessingResource.
   * @return a {@link ProcessingResource} value.
   */
  public ProcessingResource getPR();


  /**
   * Run mode constant meaning the associated PR should be run regardless of
   * what the {@link #shouldRun()} method returns.
   */
  public static final int RUN_ALWAYS = 1;

  /**
   * Run mode constant meaning the associated PR should NOT be run regardless of
   * what the {@link #shouldRun()} method returns.
   */
  public static final int RUN_NEVER = 2;

  /**
   * Run mode constant meaning the associated PR should be run only if the
   * {@link #shouldRun()} method returns true.
   */
  public static final int RUN_CONDITIONAL = 4;

  /**
   * RunningStrateguy implementation that unconditionally either runs
   * or doesn't run a given PR.
   */
  public static class UnconditionalRunningStrategy implements RunningStrategy {
    public UnconditionalRunningStrategy(ProcessingResource pr, boolean run) {
      this.pr = pr;
      this.shouldRun = run;
    }
    @Override
    public boolean shouldRun(){return shouldRun;}
    
    public void shouldRun(boolean run) { this.shouldRun = run; }

    @Override
    public int getRunMode(){return shouldRun ? RUN_ALWAYS : RUN_NEVER;}

    @Override
    public ProcessingResource getPR(){return pr;}

    ProcessingResource pr;
    boolean shouldRun;
  }
}