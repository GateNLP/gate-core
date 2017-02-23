/*
 *  AbstractLanguageAnalyser.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 13/Nov/2000
 *
 *  $Id: AbstractLanguageAnalyser.java 17604 2014-03-09 10:08:13Z markagreenwood $
 */

package gate.creole;

import gate.Corpus;
import gate.Document;
import gate.LanguageAnalyser;

/**
 * A parent implementation of language analysers with some default code.
 */
abstract public class AbstractLanguageAnalyser
                      extends AbstractProcessingResource
                      implements LanguageAnalyser
{

  private static final long serialVersionUID = -3921596570474645540L;

  /** Set the document property for this analyser. */
  @Override
  public void setDocument(Document document) {
    this.document = document;
  } // setDocument()

  /** Get the document property for this analyser. */
  @Override
  public Document getDocument() {
    return document;
  } // getDocument()

  /** The document property for this analyser. */
  protected Document document;

  /** Set the corpus property for this analyser. */
  @Override
  public void setCorpus(Corpus corpus) {
    this.corpus = corpus;
  } // setCorpus()

  /** Get the corpus property for this analyser. */
  @Override
  public Corpus getCorpus() {
    return corpus;
  } // getCorpus()

  /** The corpus property for this analyser. */
  protected Corpus corpus;

} // class AbstractLanguageAnalyser
