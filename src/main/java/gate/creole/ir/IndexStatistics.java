/*
 *  IndexStatistics.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 19/Apr/2002
 *
 */

package gate.creole.ir;

import java.util.Map;

public interface IndexStatistics{

  public Long getTermCount();

  public Long getUniqueTermCount();

  public Long getExhaustivity(Long docID, String fieldName);

  public Long getSpecificity(String term);

  @SuppressWarnings("rawtypes")
  public Map getTermFrequency(Long docID, String fieldName);

}