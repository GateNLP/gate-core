/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mike Dowman 17/9/2004
 *
 *  $Id: GateApplication.java 19801 2016-11-25 12:58:32Z markagreenwood $ */
package gate.util.persistence;

/**
 * This class is used simply to pair together the URL list and an object
 * itself so that they can be serialized as a single XML object.
 */
public class GateApplication {
  Object urlList;
	Object application;
}
