/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Johann Petrak, 2009-10-21
 *
 *  $Id: ConditionalSerialAnalyserControllerPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */
package gate.util.persistence;

import gate.Corpus;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
/**
 * Persistence handler for {@link ConditionalSerialAnalyserController}.
 * Adds handling of the corpus memeber to the {@link ControllerPersistence}
 * class
 */

public class ConditionalSerialAnalyserControllerPersistence extends ConditionalControllerPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    if(! (source instanceof ConditionalSerialAnalyserController)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                ConditionalSerialAnalyserController.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + ConditionalSerialAnalyserController.class.getName());
    }

    super.extractDataFromSource(source);

    ConditionalSerialAnalyserController sac = (ConditionalSerialAnalyserController)source;
    corpus = PersistenceManager.getPersistentRepresentation(sac.getCorpus());
  }

  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    ConditionalSerialAnalyserController sac = (ConditionalSerialAnalyserController)
                                  super.createObject();
    sac.setCorpus((Corpus)PersistenceManager.getTransientRepresentation(
            corpus,resourceName,initParamOverrides));
    return sac;
  }
  protected Object corpus;
  static final long serialVersionUID = -411697343273269225L;
}