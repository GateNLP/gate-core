/*
 *  SimpleDocument.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Kalina Bontcheva, 23/Jul/2004
 *
 *  $Id: SimpleDocument.java 17649 2014-03-13 15:04:17Z markagreenwood $
 */

package gate;

import java.net.URL;
import java.util.Set;


/** Represents the commonalities between all sorts of documents.
 */
public interface SimpleDocument extends LanguageResource, Comparable<Object> {

  /**
   * The parameter name for the document URL
   */
  public static final String
    DOCUMENT_URL_PARAMETER_NAME = "sourceUrl";

  /** Documents are identified by URLs
   */
  public URL getSourceUrl();

  /** Set method for the document's URL
   */
  public void setSourceUrl(URL sourceUrl);

  public DocumentContent getContent();

  /** Set method for the document content
   */
  public void setContent(DocumentContent newContent);

  /** Get the default set of annotations. The set is created if it
   *  doesn't exist yet.
   */
  public AnnotationSet getAnnotations();

  /** Get a named set of annotations. Creates a new set if one with this
   *  name doesn't exist yet.
   */
  public AnnotationSet getAnnotations(String name);

  /** @return a set of all named annotation sets in existence or null if none.
    */
  public Set<String> getAnnotationSetNames();

  /**
   * Removes one of the named annotation sets.
   * Note that the default annotation set cannot be removed.
   * @param name the name of the annotation set to be removed
   */
  public void removeAnnotationSet(String name);

} // interface SimpleDocument

