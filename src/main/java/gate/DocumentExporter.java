/*
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mark A. Greenwood 11/07/2014
 *
 */

package gate;

import gate.creole.AbstractResource;
import gate.util.ExtensionFileFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.IOUtils;

/**
 * Adds support for exporting documents from GATE. Subclasses of
 * DocumentExporter now how to export in a given format. These exporters
 * are loaded as Creole resources and made easily available within the
 * GUI, but can also be easily accessed from within the API.
 */
public abstract class DocumentExporter extends AbstractResource {

  private static final long serialVersionUID = -4810523902750051704L;

  protected String fileType, defaultExtension, mimeType;

  protected FileFilter filter;

  /**
   * Creates a new exporter instance for a given file type with default
   * extension.
   * 
   * @param fileType this is the human readable file type name that will
   *          appear on the menu
   * @param defaultExtension the default file extension for this type
   */
  public DocumentExporter(String fileType, String defaultExtension, String mimeType) {
    this.fileType = fileType;
    this.defaultExtension = defaultExtension;
    this.mimeType = mimeType;
  }

  /**
   * The name of the file type exported
   * 
   * @return the name of the file type exported
   */
  public String getFileType() {
    return fileType;
  }

  /**
   * The default extension added to files saved in this format
   * 
   * @return the default extension added to files saved in this format
   */
  public String getDefaultExtension() {
    return defaultExtension;
  }
  
  @Override
  public String getName() {
    return fileType;
  }
  
  public String getMimeType() {
    return mimeType;
  }

  /**
   * A filter used in the file chooser to restrict the view to files of
   * this type. The default implementation just uses the default
   * extension as the filter constraint
   */
  public FileFilter getFileFilter() {
    if(filter == null)
      filter =
              new ExtensionFileFilter(fileType + " Files (*."
                      + defaultExtension + ")", defaultExtension);
    return filter;
  }

  /**
   * Equivalent to {@link #export(Document,File,FeatureMap)} with an empty map
   * of options.
   */
  public void export(Document doc, File file) throws IOException {
    export(doc, file, Factory.newFeatureMap());
  }

  /**
   * Equivalent to {@link #export(Document,OutputStream,FeatureMap)} using a
   * FileOutputStream instance constructed from the File param.
   */
  public void export(Document doc, File file, FeatureMap options)
          throws IOException {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      export(doc, out, options);
      out.flush();
    } finally {
      IOUtils.closeQuietly(out);
    }
  }

  /**
   * Equivalent to {@link #export(Document,OutputStream)} with an empty
   * map of options.
   */
  public void export(Document doc, OutputStream out) throws IOException {
    export(doc, out, Factory.newFeatureMap());
  }

  /**
   * Exports the provided {@link Document} instance to the specified
   * {@link OutputStream} using the specified options.
   * 
   * @param doc the document to export
   * @param out the OutputStream to export the document to
   * @param options DocumentExporter specific options
   */
  public abstract void export(Document doc, OutputStream out, FeatureMap options)
          throws IOException;
  
  public static DocumentExporter getInstance(String className) {
    return (DocumentExporter)Gate.getCreoleRegister().get(className)
            .getInstantiations().iterator().next();
  }
}
