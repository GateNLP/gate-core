/*
 *  LanguageAnalyserDocumentProcessor.java
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 03/Sep/2009
 *
 *  $Id: LanguageAnalyserDocumentProcessor.java 18835 2015-07-28 10:46:28Z ian_roberts $
 */

package gate.util;

import gate.Corpus;
import gate.LanguageAnalyser;
import gate.Document;
import gate.Factory;

/**
 * {@link DocumentProcessor} that processes documents using a
 * {@link LanguageAnalyser}.
 */
public class LanguageAnalyserDocumentProcessor implements DocumentProcessor {

  /**
   * The analyser used to process documents.
   */
  protected LanguageAnalyser analyser;

  /**
   * Corpus used to contain the document being processed.
   */
  private Corpus corpus;

  public LanguageAnalyserDocumentProcessor() {
  }

  /**
   * Set the controller used to process documents.
   */
  public void setAnalyser(LanguageAnalyser a) {
    this.analyser = a;
  }

  public LanguageAnalyser getAnalyser() {
    return analyser;
  }

  @Override
  public synchronized void processDocument(Document doc) throws GateException {
    if(corpus == null) {
      corpus = Factory.newCorpus("DocumentProcessor corpus");
    }
    try {
      corpus.add(doc);
      analyser.setCorpus(corpus);
      analyser.setDocument(doc);
      analyser.execute();
    }
    finally {
      analyser.setCorpus(null);
      analyser.setDocument(null);
      corpus.clear();
    }
  }

  /**
   * Clean up resources.  Should be called when this processor is no longer
   * required.
   */
  public synchronized void cleanup() {
    Factory.deleteResource(analyser);
    if(corpus != null) {
      Factory.deleteResource(corpus);
    }
  }
}
