/*
 *  Benchmarkable.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 */

package gate.util;


/**
 * Resources that want to log their progress or results into a shared
 * log centrally maintained by GATE, should implement this interface and
 * use the java.util.Benchmark class to log their entries.
 * 
 * @author niraj
 */
public interface Benchmarkable {

  /**
   * Returns the benchmark ID of this resource.
   */
  public String getBenchmarkId();

  /**
   * This method sets the benchmarkID for this resource. The resource
   * must use this as the prefix for any sub-events it logs.
   * 
   * @param benchmarkId the benchmark ID, which must not contain spaces
   *  as it is already used as a separator in the log, you can use
   * {@link Benchmark#createBenchmarkId(String, String)} for it. 
   */
  public void setBenchmarkId(String benchmarkId);

}
