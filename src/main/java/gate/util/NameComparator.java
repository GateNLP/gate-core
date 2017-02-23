/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 02/10/2001
 *
 *  $Id: NameComparator.java 19660 2016-10-10 07:57:55Z markagreenwood $
 *
 */
package gate.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares {@link NameBearer}s by name (string comparation)
 */
public class NameComparator implements Comparator<NameBearer>, Serializable {

  private static final long serialVersionUID = -8248579892793888133L;

  @Override
  public int compare(NameBearer nb1, NameBearer nb2){
    return nb1.getName().compareTo(nb2.getName());
  }
}