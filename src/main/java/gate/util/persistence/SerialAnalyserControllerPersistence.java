/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 29/10/2001
 *
 *  $Id: SerialAnalyserControllerPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */
package gate.util.persistence;

import gate.Corpus;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.persist.PersistenceException;
/**
 * Persistence handler for {@link SerialAnalyserController}.
 * Adds handling of the corpus memeber to the {@link ControllerPersistence}
 * class
 */

public class SerialAnalyserControllerPersistence extends ControllerPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    if(! (source instanceof SerialAnalyserController)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                SerialAnalyserController.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + SerialAnalyserController.class.getName());
    }

    super.extractDataFromSource(source);

    SerialAnalyserController sac = (SerialAnalyserController)source;
    corpus = PersistenceManager.getPersistentRepresentation(sac.getCorpus());
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    SerialAnalyserController sac = (SerialAnalyserController)
                                  super.createObject();
    sac.setCorpus((Corpus)PersistenceManager.getTransientRepresentation(
            corpus,resourceName,initParamOverrides));
    return sac;
  }
  protected Object corpus;
  static final long serialVersionUID = -4116973147963269225L;
}