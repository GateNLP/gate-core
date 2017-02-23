/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 05/10/2001
 *
 *  $Id: LanguageAnalyser.java 16175 2012-10-29 14:01:55Z markagreenwood $
 *
 */


package gate;

import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;

/**
 * A special type of {@link ProcessingResource} that processes {@link Document}s
 */
@CreoleResource(name = "Language analyser",
        comment = "A processing resource that takes document and corpus parameters")
public interface LanguageAnalyser extends ProcessingResource {

  /** Set the document property for this analyser. */
  @RunTime
  @CreoleParameter(comment = "The document to process")
  public void setDocument(Document document);

  /** Get the document property for this analyser. */
  public Document getDocument();

  /** Set the corpus property for this analyser. */
  @Optional
  @RunTime
  @CreoleParameter(comment = "The corpus containing the document to process")
  public void setCorpus(Corpus corpus);

  /** Get the corpus property for this analyser. */
  public Corpus getCorpus();
}
