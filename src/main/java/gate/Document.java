/*
 *  Document.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 19/Jan/2000
 *
 *  $Id: Document.java 18042 2014-06-01 15:43:56Z johann_p $
 */

package gate;

import gate.corpora.DocumentStaxUtils;
import gate.event.DocumentListener;
import gate.util.InvalidOffsetException;

import java.util.Map;
import java.util.Set;


/** Represents the commonalities between all sorts of documents.
 */
public interface Document extends SimpleDocument {

  /**
  * The parameter name that determines whether or not a document is markup aware
  */
  public static final String
    DOCUMENT_MARKUP_AWARE_PARAMETER_NAME = "markupAware";

  public static final String
    DOCUMENT_ENCODING_PARAMETER_NAME = "encoding";

  public static final String
    DOCUMENT_PRESERVE_CONTENT_PARAMETER_NAME = "preserveOriginalContent";

  public static final String
    DOCUMENT_STRING_CONTENT_PARAMETER_NAME = "stringContent";

  public static final String
    DOCUMENT_MIME_TYPE_PARAMETER_NAME = "mimeType";
  
  public static final String
    DOCUMENT_REPOSITIONING_PARAMETER_NAME = "collectRepositioningInfo";

  public static final String
    DOCUMENT_START_OFFSET_PARAMETER_NAME = "sourceUrlStartOffset";

  public static final String
    DOCUMENT_END_OFFSET_PARAMETER_NAME = "sourceUrlEndOffset";
 
  /* parameter to store additional info about the document type, 
   * e.g. publication, javadoc, etc. */
  public static final String
  DOCUMENT_TYPE_PARAMETER_NAME = "documentType";
  
  /** Documents may be packed within files; in this case an optional pair of
   *  offsets refer to the location of the document.
   */
  public Long[] getSourceUrlOffsets();

  /** Documents may be packed within files; in this case an optional pair of
   *  offsets refer to the location of the document. This method gets the
   *  start offset.
   */
  public Long getSourceUrlStartOffset();

  /** Documents may be packed within files; in this case an optional pair of
   *  offsets refer to the location of the document. This method gets the
   *  end offset.
   */
  public Long getSourceUrlEndOffset();

  /** Returns a map with the named annotation sets
    */
  public Map<String, AnnotationSet> getNamedAnnotationSets();

  /** Make the document markup-aware. This will trigger the creation
   *  of a DocumentFormat object at Document initialisation time; the
   *  DocumentFormat object will unpack the markup in the Document and
   *  add it as annotations. Documents are <B>not</B> markup-aware by default.
   *
   *  @param b markup awareness status.
   */
  public void setMarkupAware(Boolean b);

  /** Get the markup awareness status of the Document.
   *
   *  @return whether the Document is markup aware.
   */
  public Boolean getMarkupAware();

  /**
   * Allow/disallow preserving of the original document content.
   * If is <B>true</B> the original content will be retrieved from
   * the DocumentContent object and preserved as document feature.
   */
  public void setPreserveOriginalContent(Boolean b);

  /** Get the preserving of content status of the Document.
   *
   *  @return whether the Document should preserve it's original content.
   */
  public Boolean getPreserveOriginalContent();

  /**
   *  Allow/disallow collecting of repositioning information.
   *  If is <B>true</B> information will be retrieved and preserved
   *  as document feature.<BR>
   *  Preserving of repositioning information give the possibilities
   *  for converting of coordinates between the original document content and
   *  extracted from the document text.
   */
  public void setCollectRepositioningInfo(Boolean b);

  /** Get the collectiong and preserving of repositioning information
   *  for the Document. <BR>
   *  Preserving of repositioning information give the possibilities
   *  for converting of coordinates between the original document content and
   *  extracted from the document text.
   *
   *  @return whether the Document should collect and preserve information.
   */
  public Boolean getCollectRepositioningInfo();

  /** Returns a GateXml document. This document is actually a serialization of
   *  a Gate Document in XML. The <code>writeDocument</code> methods of
   *  {@link DocumentStaxUtils} provide the standard implementation of this
   *  serialization format which will work for any Document implementation.
   *  Implementations of <code>toXml</code> will typically delegate to
   *  <code>DocumentStaxUtils</code>, and in many cases it will be more
   *  efficient for callers to use that directly rather than calling
   *  <code>toXml</code>.
   *  @see DocumentStaxUtils
    * @return a string representing a Gate Xml document
    */
  public String toXml();

  /** Returns an XML document aming to preserve the original markups(
    * the original markup will be in the same place and format as it was
    * before processing the document) and include (if possible)
    * the annotations specified in the aSourceAnnotationSet.
    * <b>Warning:</b> Annotations from the aSourceAnnotationSet will be lost
    * if they will cause a crosed over situation.
    * @param aSourceAnnotationSet is an annotation set containing all the
    * annotations that will be combined with the original marup set.
    * @param includeFeatures determines whether or not features and gate IDs
    * of the annotations should be included as attributes on the tags or not.
    * If false, then only the annotation types are exported as tags, with no
    * attributes.
    * @return a string representing an XML document containing the original
    * markup + dumped annotations form the aSourceAnnotationSet
    */
  public String toXml(Set<Annotation> aSourceAnnotationSet, boolean includeFeatures);

  /**
   * Equivalent to toXml(aSourceAnnotationSet, true).
   */
  public String toXml(Set<Annotation> aSourceAnnotationSet);

  /** 
   * Make changes to the document content and adapt affected annotations.
   * 
   * This method replaces the document content ranging from the start to the
   * end offset with the new DocumentContent provided as a replacement. If
   * the original content should be removed, null can be used as a a replacement.
   * In addition, annotations in all annotation sets are removed or adapted to 
   * the changed documents see 
   * {@link gate.annotation.AnnotationSetImpl#edit(java.lang.Long, java.lang.Long, gate.DocumentContent) AnnotationSetImpl.edit(long,long,DocumentContent) }
   * for information on how annotations get adapted.
   * 
   */
  public void edit(Long start, Long end, DocumentContent replacement)
    throws InvalidOffsetException;

  /**
   * Adds a {@link gate.event.DocumentListener} to this document.
   * All the registered listeners will be notified of changes occured to the
   * document.
   */
  public void addDocumentListener(DocumentListener l);

  /**
   * Removes one of the previously registered document listeners.
   */
  public void removeDocumentListener(DocumentListener l);


  /** Documents may be packed within files; in this case an optional pair of
    * offsets refer to the location of the document. This method sets the
    * end offset.
    */
  public void setSourceUrlEndOffset(Long sourceUrlEndOffset);


  /** Documents may be packed within files; in this case an optional pair of
    * offsets refer to the location of the document. This method sets the
    * start offset.
    */
  public void setSourceUrlStartOffset(Long sourceUrlStartOffset);

} // interface Document

