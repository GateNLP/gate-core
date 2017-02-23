/*
 * Copyright (c) 1998-2009, The University of Sheffield.
 * Copyright (c) 2009-2009, Ontotext, Bulgaria.
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June 1991 (in the distribution as file licence.html,
 * and also available at http://gate.ac.uk/gate/licence.html).
 *
 * Thomas Heitz - 15/09/2009
 *
 * $Id:$
 *
 */

package gate.swing;

import gate.Gate;
import gate.gui.MainFrame;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileFilter;

/**
 * Extends {@link javax.swing.JFileChooser} to make sure the shared
 * {@link MainFrame} instance is used as a parent when no parent is specified.
 * <br><br>
 * Remember the last path used for each resource type.
 * The path is saved when the user confirm the dialog.
 * The resource name must be set with the method {@link #setResource(String)}.
 * Use {@link #setSelectedFile(java.io.File)} to preselect a different file
 * or use {@link #setFileName(String)} to use a different file name but the
 * saved directory.
 * <br><br>
 * Resource paths are saved in the user config file.
 */
@SuppressWarnings("serial")
public class XJFileChooser extends JFileChooser {
  /** key used when saving the file location to be retrieved later */
  private String resource;
  /** file name used instead of the one saved in the preferences */
  private String fileName;
  /** set to true when setSelectedFile has been used */
  private boolean isFileSelected = false;
  /** map for (resource name -> path) saved in the user config file */
  private Map<String, String> locations;

  public XJFileChooser() {
    addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) { /* do nothing */ }
      @Override
      public void ancestorRemoved(AncestorEvent event) {
        // reinitialise fields when the file chooser is hidden
        resource = null;
        fileName = null;
        isFileSelected = false;
        resetChoosableFileFilters();
      }
      @Override
      public void ancestorMoved(AncestorEvent event) { /* do nothing */ }
    });
    locations = getLocations();
  }

  /**
   * Overridden to make sure the shared MainFrame instance is used as
   * a parent when no parent is specified
   */
  @Override
  public int showDialog(Component parent, String approveButtonText)
  throws HeadlessException {
    setSelectedFileFromPreferences();
    return super.showDialog((parent != null) ? parent :
      (MainFrame.getFileChooser() != null) ? MainFrame.getInstance() :
        null, approveButtonText);
  }

  /**
   * If possible, select the last directory/file used for the resource
   * otherwise use the last file chooser selection directory or if null
   * use the user home directory.
   */
  public void setSelectedFileFromPreferences() {
    String lastUsedPath = getLocationForResource(resource);
    File file;
    String specifiedDefaultDir =
      System.getProperty("gate.user.filechooser.defaultdir");
    if (isFileSelected) {
      // a file has already been selected so do not use the saved one
      return; 
    } else if (lastUsedPath != null && fileName != null) {
      file = new File(lastUsedPath);
      if (!file.isDirectory()) {
        file = file.getParentFile();
      }
      file = new File(file, fileName);
    } else if (lastUsedPath != null) {
      file = new File(lastUsedPath);
    } else if (fileName != null) {
      // if the property for setting a default directory has been set,
      // use that for finding the file, otherwise use whatever the
      // user home directory is on this operating system.
      if(specifiedDefaultDir != null) {
        file = new File(specifiedDefaultDir, fileName);
      } else {
        file = new File(System.getProperty("user.home"), fileName);
      }
    } else {
      // if the property for setting a default directory has been set,
      // let the filechooser know, otherwise just do whatever the
      // default behavior is.
      if(specifiedDefaultDir != null) {
        this.setCurrentDirectory(new File(specifiedDefaultDir));
      }
      return;
    }
    setSelectedFile(file);
    ensureFileIsVisible(file);
  }

  public String getLocationForResource(String resource) {
    locations = getLocations();
    return (resource == null) ? null : locations.get(resource);
  }

  /**
   * Useful to modify the locations used by this file chooser.
   * @return a map of resource (name * file location)
   * @see #setLocations(java.util.Map)
   */
  public Map<String, String> getLocations() {
    return Gate.getUserConfig().getMap(XJFileChooser.class.getName());
  }

  /**
   * Useful to modify the locations used by this file chooser.
   * @param locations a map of (resource name * file location)
   * @see #getLocations()
   */
  public void setLocations(Map<String, String> locations) {
    Gate.getUserConfig().put(XJFileChooser.class.getName(), locations);
  }

  /**
   * Set the file name to be used instead of the one saved in the preferences.
   * @param fileName file name
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /** overriden to first save the location of the file chooser
   *  for the current resource. */
  @Override
  public void approveSelection() {
    if (resource != null && getSelectedFile() != null) {
      //String filePath = getSelectedFile().getCanonicalPath();
      String filePath = getSelectedFile().getAbsolutePath();
      locations.put(resource, filePath);
      setLocations(locations);
    }
    super.approveSelection();
  }

  /**
   * Set the resource to remember the path. Must be set before to call
   * {@link #showDialog}.
   * @param resource name of the resource
   */
  public void setResource(String resource) {
    this.resource = resource;
  }

  /**
   * Get the resource associated to this file chooser.
   * @return name of the resource
   */
  public String getResource() {
    return resource;
  }

  /** Overriden to test first if the file exists */
  @Override
  public void ensureFileIsVisible(File f) {
    if(f != null && f.exists()) super.ensureFileIsVisible(f);
  }

  /** Overriden to test first if the file exists */
  @Override
  public void setSelectedFile(File file) {
    if(file != null){
      if(file.exists() ||
         (file.getParentFile() != null && file.getParentFile().exists())){
        super.setSelectedFile(file);
      }
      isFileSelected = true;
    }
  }

  /** overriden to add a filter only if not already present */
  @Override
  public void addChoosableFileFilter(FileFilter filterToAdd) {
    for (FileFilter filter : getChoosableFileFilters()) {
      if (filter.getDescription().equals(filterToAdd.getDescription())) {
        setFileFilter(filter);
        return;
      }
    }
    super.addChoosableFileFilter(filterToAdd);
  }
}
