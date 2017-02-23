/*
 *  SetParameterResourceCustomiser.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 22/Jan/2008
 *
 *  $Id: SetParameterResourceCustomiser.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.util.spring;

import gate.Controller;
import gate.Resource;

/**
 * ResourceCustomiser that sets a parameter on the resource being
 * customised. When used to customise Controllers, it can optionally
 * take a "prName" property. In this case it will set the parameter on
 * the first PR with that name in the controller, rather than the
 * controller itself
 */
public class SetParameterResourceCustomiser implements ResourceCustomiser {

  private String paramName;

  private Object value;

  private String prName = null;

  @Override
  public void customiseResource(Resource res) throws Exception {
    if(prName == null) {
      res.setParameterValue(paramName, value);
    }
    else {
      if(res instanceof Controller) {
        for(Object pr : ((Controller)res).getPRs()) {
          if(prName.equals(((Resource)pr).getName())) {
            ((Resource)pr).setParameterValue(paramName, value);
            break;
          }
        }
      }
      else {
        throw new IllegalArgumentException("prName was specified, so we can "
                + "only customise Controllers.  Supplied resource was a "
                + res.getClass().getName());
      }
    }
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void setPrName(String prName) {
    this.prName = prName;
  }

}
