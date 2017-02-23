/*
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 03/11/2014
 *
 */
package gate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A {@link DocumentExporter} that is also capable of exporting
 * a whole corpus to a single file.
 */
public abstract class CorpusExporter extends DocumentExporter {

  private static final long serialVersionUID = 3172689319810927933L;

  public CorpusExporter(String fileType, String defaultExtension,
          String mimeType) {
    super(fileType, defaultExtension, mimeType);
  }

  /**
   * Equivalent to {@link #export(Corpus,File,FeatureMap)} with an empty map
   * of options.
   */
  public void export(Corpus corpus, File file) throws IOException {
    export(corpus, file, Factory.newFeatureMap());
  }

  /**
   * Equivalent to {@link #export(Corpus,OutputStream,FeatureMap)} using a
   * FileOutputStream instance constructed from the File param.
   */
  public void export(Corpus corpus, File file, FeatureMap options)
          throws IOException {
    try (FileOutputStream out = new FileOutputStream(file)){
      export(corpus, out, options);
      out.flush();
    }
  }

  /**
   * Equivalent to {@link #export(Corpus,OutputStream)} with an empty
   * map of options.
   */
  public void export(Corpus corpus, OutputStream out) throws IOException {
    export(corpus, out, Factory.newFeatureMap());
  }

  /**
   * Exports the provided {@link Corpus} instance to the specified
   * {@link OutputStream} using the specified options.
   * 
   * @param corpus the corpus to export
   * @param out the OutputStream to export the document to
   * @param options DocumentExporter specific options
   */
  public abstract void export(Corpus corpus, OutputStream out, FeatureMap options)
          throws IOException;
}
