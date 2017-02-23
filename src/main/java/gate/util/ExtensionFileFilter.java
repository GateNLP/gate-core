/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 22/May/2000
 *
 *  $Id: ExtensionFileFilter.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */
package gate.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Implementation of a file name filter.
 * This class is used by {@link javax.swing.JFileChooser} to filter the
 * displayed files by their extension.
 */
public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter
                                 implements FileFilter {

  /**
   * Builds a new ExtensionFileFilter.
   */
  public ExtensionFileFilter() {
  }

  /**
   * Creates a FileNameExtensionFilter with the specified description and
   * file name extensions. The returned FileNameExtensionFilter will accept
   * all directories and any file with a file name extension contained
   * in extensions.
   * @param description textual description for the filter, may be null
   * @param extensions the accepted file name extensions
   */
  public ExtensionFileFilter(String description, String... extensions) {
    setDescription(description);
    for (String extension : extensions) {
      addExtension(extension);
    }
  }

  /**
   * Checks a file for compliance with the requested extensions.
   *
   * @param f file to test with this filter
   */
  @Override
  public boolean accept(File f){
    String name = f.getName();
    if(f.isDirectory()) return true;

    for (String acceptedExtension : acceptedExtensions) {
      if (name.endsWith(acceptedExtension)) return true;
    }
    return false;
  }

  /**
   * Returns the user-frielndly description for the files, e.g. "Text files"
   *
   */
  @Override
  public String getDescription() {
    return (description == null) ? toString() : description;
  }

  /**
   * Adds a new extension to the list of accepted extensions.
   *
   * @param extension file extension used to filter files
   */
  public void addExtension(String extension) {
    acceptedExtensions.add(extension);
  }

  /**
   * Sets the user friendly description for the accepted files.
   *
   * @param description description for this file filter
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the set of file name extensions files are tested against
   */
  public String[] getExtensions() {
    return acceptedExtensions.toArray(new String[acceptedExtensions.size()]);
  }

  /**
   * @return a string representation of this file filter.
   */
  @Override
  public String toString() {
    return "Filter for " + Arrays.toString(acceptedExtensions.toArray());
  }

  /** The list of accepted file name extensions. */
  private List<String> acceptedExtensions = new ArrayList<String>();

  /** The description of this file filter. */
  private String description;

} // ExtensionFileFilter
