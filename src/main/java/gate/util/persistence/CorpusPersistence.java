/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 26/10/2001
 *
 *  $Id: CorpusPersistence.java 18176 2014-07-11 15:45:13Z johann_p $
 *
 */

package gate.util.persistence;


import gate.Corpus;
import gate.Document;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CorpusPersistence extends LRPersistence {
  /**
   * Populates this Persistence with the data that needs to be stored from the
   * original source object.
   */
  @Override
  public void extractDataFromSource(Object source)throws PersistenceException{
    //check input
    if(! (source instanceof Corpus)){
      throw new UnsupportedOperationException(
                getClass().getName() + " can only be used for " +
                Corpus.class.getName() +
                " objects!\n" + source.getClass().getName() +
                " is not a " + Corpus.class.getName());
    }

    Corpus corpus = (Corpus)source;
    super.extractDataFromSource(source);
    if(dsData == null){
      //transient corpus; we still need to save the docs
      docList = new ArrayList<Serializable>();
      Iterator<Document> docIter = corpus.iterator();
      while(docIter.hasNext()){
        docList.add(PersistenceManager.
                    getPersistentRepresentation(docIter.next()));
      }
    }else{
      //persistent corpus; it takes care of documents by itself
      //nothing to do :)
      docList = null;
    }
  }


  /**
   * Creates a new object from the data contained. This new object is supposed
   * to be a copy for the original object used as source for data extraction.
   */
  @Override
  public Object createObject()throws PersistenceException,
                                     ResourceInstantiationException{
    Corpus corpus = (Corpus)super.createObject();
    if(docList != null){
      //transient corpus; we need to recreate the docs
      if(!docList.isEmpty() && corpus.isEmpty()){
        Iterator<Serializable> docIter = docList.iterator();
        while(docIter.hasNext()){
          corpus.add((Document) PersistenceManager.getTransientRepresentation(
                  docIter.next(),containingControllerName,initParamOverrides));
        }

      }
    }
    return corpus;
  }


  protected List<Serializable> docList;
  static final long serialVersionUID = 6181534551802883626L;
}