/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  AbstractDocumentView.java
 *
 *  Valentin Tablan, Mar 25, 2004
 *
 *  $Id: AbstractDocumentView.java 17606 2014-03-09 12:12:49Z markagreenwood $
 */

package gate.gui.docview;

import gate.Document;
import gate.creole.AbstractResource;
import gate.gui.Handle;
import gate.gui.annedit.AnnotationData;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

/**
 * A convenience implementation of {@link gate.gui.docview.DocumentView} that
 * can be extended by implementers of document views.
 * An implementation of a document view that extends this class will need to 
 * provide implementations for the three abstract methods:
 * {@link #initGUI()}, {@link #registerHooks()} and {@link #unregisterHooks()}.
 */
@SuppressWarnings("serial")
public abstract class AbstractDocumentView extends AbstractResource
                                           implements DocumentView {

  /**
   * Notifies this view that it has become active or inactive.
   * This method will initialise the GUI the first time the view becomes active.
   * @param active a boolean value.
   */
  @Override
  public void setActive(boolean active) {
    this.active = active;
    if(active){
	    if(!guiInitialised){
	      initGUI();
	      guiInitialised = true;
	    }
	    registerHooks();
    }else{
      unregisterHooks();
    }
  }

  /**
   * Returns the active state of this view. 
   * @return a boolean value
   */
  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setSelectedAnnotations(List<AnnotationData> selectedAnnots){
    //do nothing
  }
  
  /* 
   * By default return an empty list of actions.
   * @return an empty list.
   */
  @Override
  public List<Action> getActions() {
    return new ArrayList<Action>();
  }
  
  /* 
   * By default store the handle in the {@link #handle} field. 
   */
  @Override
  public void setHandle(Handle handle) {
    this.handle = handle;
  }
  
  /**
   * Stores the target (which should always be a {@link Document}) into the 
   * {@link #document} field.
   */
  @Override
  public void setTarget(Object target) {
    this.document = (Document)target;
  }
  
  /**
   * Gets the document this view displays.
   * @return a {@link Document}
   */
  public Document getDocument(){
    return document;
  }
  
  /**
   * Stores the owner of this view into the {@link #owner} field. The owner is 
   * the {@link DocumentEditor} this view is part of.
   */
  @Override
  public void setOwner(DocumentEditor editor) {
    this.owner = editor;
  }
  
  /**
   * @return the handle
   */
  public Handle getHandle() {
    return handle;
  }

  /**
   * @return the owner
   */
  public DocumentEditor getOwner() {
    return owner;
  }

  /**
   * Implementers should override this method and use it for populating the GUI. 
   *
   */
  protected abstract void initGUI();
  
  /**
   * This method will be called whenever the view becomes active. Implementers 
   * should use this to add hooks (such as mouse listeners) to the other views
   * as required by their functionality. 
   */
  protected abstract void registerHooks();

  /**
   * This method will be called whenever this view becomes inactive. 
   * Implementers should use it to unregister whatever hooks they registered
   * in {@link #registerHooks()}.
   *
   */
  protected abstract void unregisterHooks();
  
  /**
   * Stores the active state of this view.
   */
  protected boolean active = false;
  
  /**
   * Stores the handle of this view.
   */
  protected Handle handle;
  
  /**
   * Has the UI been initialised yet?
   */
  protected boolean guiInitialised = false;
  
  /**
   * The document this view displays.
   */
  protected Document document;
  
  /**
   * The {@link DocumentEditor} this view is part of.
   */
  protected DocumentEditor owner;
}

