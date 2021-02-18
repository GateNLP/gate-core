/*
 *  DocumentImpl.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 11/Feb/2000
 *
 *  $Id: DocumentImpl.java 19660 2016-10-10 07:57:55Z markagreenwood $
 */
package gate.corpora;

import gate.Annotation;
import gate.AnnotationSet;
import gate.DataStore;
import gate.DocumentContent;
import gate.DocumentFormat;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.Node;
import gate.Resource;
import gate.TextualDocument;
import gate.annotation.AnnotationSetImpl;
import gate.creole.AbstractLanguageResource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.event.CreoleEvent;
import gate.event.CreoleListener;
import gate.event.DatastoreEvent;
import gate.event.DatastoreListener;
import gate.event.DocumentEvent;
import gate.event.DocumentListener;
import gate.event.StatusListener;
import gate.util.DocumentFormatException;
import gate.util.Err;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.OptionsMap;
import gate.util.Out;
import gate.util.SimpleFeatureMapImpl;
import gate.util.Strings;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Represents the commonalities between all sorts of documents.
 * 
 * <H2>Editing</H2>
 * 
 * <P>
 * The DocumentImpl class implements the Document interface. The
 * DocumentContentImpl class models the textual or audio-visual materials which
 * are the source and content of Documents. The AnnotationSetImpl class supplies
 * annotations on Documents.
 * 
 * <P>
 * Abbreviations:
 * 
 * <UL>
 * <LI> DC = DocumentContent
 * <LI> D = Document
 * <LI> AS = AnnotationSet
 * </UL>
 * 
 * <P>
 * We add an edit method to each of these classes; for DC and AS the methods are
 * package private; D has the public method.
 * 
 * <PRE>
 * 
 * void edit(Long start, Long end, DocumentContent replacement) throws
 * InvalidOffsetException;
 * 
 * </PRE>
 * 
 * <P>
 * D receives edit requests and forwards them to DC and AS. On DC, this method
 * makes a change to the content - e.g. replacing a String range from start to
 * end with replacement. (Deletions are catered for by having replacement =
 * null.) D then calls AS.edit on each of its annotation sets.
 * 
 * <P>
 * On AS, edit calls replacement.size() (i.e. DC.size()) to figure out how long
 * the replacement is (0 for null). It then considers annotations that terminate
 * (start or end) in the altered or deleted range as invalid; annotations that
 * terminate after the range have their offsets adjusted. I.e.:
 * <UL>
 * <LI> the nodes that pointed inside the old modified area are invalid now and
 * will be deleted along with the connected annotations;
 * <LI> the nodes that are before the start of the modified area remain
 * untouched;
 * <LI> the nodes that are after the end of the affected area will have the
 * offset changed according to the formula below.
 * </UL>
 * 
 * <P>
 * A note re. AS and annotations: annotations no longer have offsets as in the
 * old model, they now have nodes, and nodes have offsets.
 * 
 * <P>
 * To implement AS.edit, we have several indices:
 * 
 * <PRE>
 * 
 * HashMap annotsByStartNode, annotsByEndNode;
 * 
 * </PRE>
 * 
 * which map node ids to annotations;
 * 
 * <PRE>
 * 
 * RBTreeMap nodesByOffset;
 * 
 * </PRE>
 * 
 * which maps offset to Nodes.
 * 
 * <P>
 * When we get an edit request, we traverse that part of the nodesByOffset tree
 * representing the altered or deleted range of the DC. For each node found, we
 * delete any annotations that terminate on the node, and then delete the node
 * itself. We then traverse the rest of the tree, changing the offset on all
 * remaining nodes by:
 * 
 * <PRE>
 * 
 * newOffset = oldOffset - ( (end - start) - // size of mod ( (replacement ==
 * null) ? 0 : replacement.size() ) // size of repl );
 * 
 * </PRE>
 * 
 * Note that we use the same convention as e.g. java.lang.String: start offsets
 * are inclusive; end offsets are exclusive. I.e. for string "abcd" range 1-3 =
 * "bc". Examples, for a node with offset 4:
 * 
 * <PRE>
 * 
 * edit(1, 3, "BC"); newOffset = 4 - ( (3 - 1) - 2 ) = 4
 * 
 * edit(1, 3, null); newOffset = 4 - ( (3 - 1) - 0 ) = 2
 * 
 * edit(1, 3, "BBCC"); newOffset = 4 - ( (3 - 1) - 4 ) = 6
 * 
 * </PRE>
 */
@CreoleResource(name = "GATE Document", interfaceName = "gate.Document",
    comment = "GATE transient document.", icon = "document",
    helpURL = "http://gate.ac.uk/userguide/sec:developer:documents")
public class DocumentImpl extends AbstractLanguageResource implements
                                                          TextualDocument,
                                                          CreoleListener,
                                                          DatastoreListener {
  /** Debug flag */
  private static final boolean DEBUG = false;

  /**
   * If you set this flag to true the original content of the document will be
   * kept in the document feature. <br>
   * Default value is false to avoid the unnecessary waste of memory
   */
  private Boolean preserveOriginalContent = Boolean.FALSE;

  /**
   * If you set this flag to true the repositioning information for the document
   * will be kept in the document feature. <br>
   * Default value is false to avoid the unnecessary waste of time and memory
   */
  private Boolean collectRepositioningInfo = Boolean.FALSE;

  /**
   * This is a variable which contains the latest crossed over annotation found
   * during export with preserving format, i.e., toXml(annotations) method.
   */
  private Annotation crossedOverAnnotation = null;

  
  /** Flag to determine whether to serialize namespace information held as
   *  annotation features into namespace prefix and URI in the XML
   */
  private boolean serializeNamespaceInfo = false;
  /** Feature name used for namespace uri in namespaced elements */
  private String namespaceURIFeature = null;
  /** Feature name used for namespace prefix in namespaced elements */
  private String namespacePrefixFeature = null;

  
  /** Default construction. Content left empty. */
  public DocumentImpl() {
    content = new DocumentContentImpl();
    stringContent = "";

    /** We will attempt to serialize namespace if
     *  three parameters are set in the global or local config file:
     *  ADD_NAMESPACE_FEATURES: boolean flag
     *  ELEMENT_NAMESPACE_URI: feature name used to hold namespace URI
     *  ELEMENT_NAMESPACE_PREFIX: feature name used to hold namespace prefix
     */
    OptionsMap configData = Gate.getUserConfig();

    boolean addNSFeature = Boolean.parseBoolean((String)configData.get(GateConstants.ADD_NAMESPACE_FEATURES));
    namespaceURIFeature = (String) configData.get(GateConstants.ELEMENT_NAMESPACE_URI);
    namespacePrefixFeature = (String) configData.get(GateConstants.ELEMENT_NAMESPACE_PREFIX);

    serializeNamespaceInfo = (addNSFeature && namespacePrefixFeature != null && !namespacePrefixFeature.isEmpty() && namespaceURIFeature != null && !namespaceURIFeature.isEmpty());

  } // default construction

  /** Cover unpredictable Features creation */
  @Override
  public FeatureMap getFeatures() {
    if(features == null) {
      features = new SimpleFeatureMapImpl();
    }
    return features;
  }

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    // set up the source URL and create the content
    if(sourceUrl == null) {
      if(stringContent == null) { throw new ResourceInstantiationException(
              "The sourceURL and document's content were null."); }
      content = new DocumentContentImpl(stringContent);
      getFeatures().put("gate.SourceURL", "created from String");
    } else {
      try {
        URL resolved = gate.Utils.resolveURL(sourceUrl);
        getFeatures().put("gate.OriginalURL", sourceUrl.toExternalForm());
        sourceUrl = resolved;
      }
      catch (IOException e) {
        System.err.println("Unable to resolve URL");
        e.printStackTrace();
      }

      try {
        if(!DocumentFormat.willReadFromUrl(mimeType, sourceUrl)) {
          content = new DocumentContentImpl(sourceUrl, getEncoding(),
                  sourceUrlStartOffset, sourceUrlEndOffset);
        }
        getFeatures().put("gate.SourceURL", sourceUrl.toExternalForm());
      } catch(IOException e) {
        throw new ResourceInstantiationException("DocumentImpl.init: " + e);
      }
    }
    if(preserveOriginalContent && content != null) {
      String originalContent = ((DocumentContentImpl)content)
              .getOriginalContent();
      getFeatures().put(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME,
              originalContent);
    } // if
    // set up a DocumentFormat if markup unpacking required
    if(getMarkupAware()) {
      DocumentFormat docFormat = null;
      // if a specific MIME type has been given, use it
      if(this.mimeType != null && this.mimeType.length() > 0) {
        MimeType theType = DocumentFormat.getMimeTypeForString(mimeType);
        if(theType == null) {
          throw new ResourceInstantiationException("MIME type \""
              + this.mimeType + " has no registered DocumentFormat");
        }

        docFormat = DocumentFormat.getDocumentFormat(this, theType);
      }
      else {
        docFormat = DocumentFormat.getDocumentFormat(this, sourceUrl);
      }
      try {
        if(docFormat != null) {
          StatusListener sListener = (StatusListener)gate.Gate
                  .getListeners().get("gate.event.StatusListener");
          if(sListener != null) docFormat.addStatusListener(sListener);
          // set the flag if true and if the document format support collecting
          docFormat.setShouldCollectRepositioning(collectRepositioningInfo);
          if(docFormat.getShouldCollectRepositioning()) {
            // unpack with collectiong of repositioning information
            RepositioningInfo info = new RepositioningInfo();
            String origContent = (String)getFeatures().get(
                    GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
            RepositioningInfo ampCodingInfo = new RepositioningInfo();
            if(origContent != null) {
              boolean shouldCorrectCR = docFormat instanceof XmlDocumentFormat;
              collectInformationForAmpCodding(origContent, ampCodingInfo,
                      shouldCorrectCR);
              if(docFormat.getMimeType().equals(new MimeType("text","html"))) {
                collectInformationForWS(origContent, ampCodingInfo);
              } // if
            } // if
            docFormat.unpackMarkup(this, info, ampCodingInfo);
            if(origContent != null && docFormat instanceof XmlDocumentFormat) {
              // CRLF correction of RepositioningInfo
              correctRepositioningForCRLFInXML(origContent, info);
            } // if
            getFeatures().put(
                    GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME,
                    info);
          } else {
            // normal old fashioned unpack
            docFormat.unpackMarkup(this);
          }
          docFormat.removeStatusListener(sListener);
        } // if format != null
      } catch(DocumentFormatException e) {
        throw new ResourceInstantiationException(
                "Couldn't unpack markup in document "
                        + (sourceUrl != null ? sourceUrl.toExternalForm() : "")
                        + "!", e);
      }
    } // if markup aware
    // try{
    // FileWriter fw = new FileWriter("d:/temp/doccontent.txt");
    // fw.write(getContent().toString());
    // fw.flush();
    // fw.close();
    // }catch(IOException ioe){
    // ioe.printStackTrace();
    // }
    return this;
  } // init()

  /**
   * Correct repositioning information for substitution of "\r\n" with "\n"
   */
  private void correctRepositioningForCRLFInXML(String content,
          RepositioningInfo info) {
    int index = -1;
    do {
      index = content.indexOf("\r\n", index + 1);
      if(index != -1) {
        info.correctInformationOriginalMove(index, 1);
      } // if
    } while(index != -1);
  } // correctRepositioningForCRLF

  /**
   * Collect information for substitution of "&xxx;" with "y"
   * 
   * It couldn't be collected a position information about some unicode and
   * &-coded symbols during parsing. The parser "hide" the information about the
   * position of such kind of parsed text. So, there is minimal chance to have
   * &-coded symbol inside the covered by repositioning records area. The new
   * record should be created for every coded symbol outside the existing
   * records. <BR>
   * If <code>shouldCorrectCR</code> flag is <code>true</code> the
   * correction for CRLF substitution is performed.
   */
  private void collectInformationForAmpCodding(String content,
          RepositioningInfo info, boolean shouldCorrectCR) {
    if(content == null || info == null) return;
    int ampIndex = -1;
    int semiIndex;
    do {
      ampIndex = content.indexOf('&', ampIndex + 1);
      if(ampIndex != -1) {
        semiIndex = content.indexOf(';', ampIndex + 1);
        // have semicolon and it is near enough for amp codding
        if(semiIndex != -1 && (semiIndex - ampIndex) < 8) {
          info.addPositionInfo(ampIndex, semiIndex - ampIndex + 1, 0, 1);
        } else {
          // no semicolon or it is too far
          // analyse for amp codding without semicolon
          int maxEnd = Math.min(ampIndex + 8, content.length());
          String ampCandidate = content.substring(ampIndex, maxEnd);
          int ampCodingSize = analyseAmpCodding(ampCandidate);
          if(ampCodingSize != -1) {
            info.addPositionInfo(ampIndex, ampCodingSize, 0, 1);
          } // if
        } // if - semicolon found
      } // if - ampersand found
    } while(ampIndex != -1);
    // correct the collected information to adjust it's positions
    // with reported by the parser
    int index = -1;
    if(shouldCorrectCR) {
      do {
        index = content.indexOf("\r\n", index + 1);
        if(index != -1) {
          info.correctInformationOriginalMove(index, -1);
        } // if
      } while(index != -1);
    } // if
  } // collectInformationForAmpCodding

  /**
   * This function compute size of the ampersand codded sequence when semicolin
   * is not present.
   */
  private int analyseAmpCodding(String content) {
    int result = -1;
    try {
      char ch = content.charAt(1);
      switch(ch){
        case 'l': // &lt
        case 'L': // &lt
          if(content.charAt(2) == 't' || content.charAt(2) == 'T') {
            result = 3;
          } // if
          break;
        case 'g': // &gt
        case 'G': // &gt
          if(content.charAt(2) == 't' || content.charAt(2) == 'T') {
            result = 3;
          } // if
          break;
        case 'a': // &amp
        case 'A': // &amp
          if(content.substring(2, 4).equalsIgnoreCase("mp")) {
            result = 4;
          } // if
          break;
        case 'q': // &quot
        case 'Q': // &quot
          if(content.substring(2, 5).equalsIgnoreCase("uot")) {
            result = 5;
          } // if
          break;
        case '#': // #number (example &#145, &#x4C38)
          int endIndex = 2;
          boolean hexCoded = false;
          if(content.charAt(2) == 'x' || content.charAt(2) == 'X') {
            // Hex codding
            ++endIndex;
            hexCoded = true;
          } // if
          while(endIndex < 8 && isNumber(content.charAt(endIndex), hexCoded)) {
            ++endIndex;
          } // while
          result = endIndex;
          break;
      } // switch
    } catch(StringIndexOutOfBoundsException ex) {
      // do nothing
    } // catch
    return result;
  } // analyseAmpCodding

  /** Check for numeric range. If hex is true the A..F range is included */
  private boolean isNumber(char ch, boolean hex) {
    if(ch >= '0' && ch <= '9') return true;
    if(hex) {
      if(ch >= 'A' && ch <= 'F') return true;
      if(ch >= 'a' && ch <= 'f') return true;
    } // if
    return false;
  } // isNumber

  /**
   * HTML parser perform substitution of multiple whitespaces (WS) with a single
   * WS. To create correct repositioning information structure we should keep
   * the information for such multiple WS. <BR>
   * The criteria for WS is <code>(ch <= ' ')</code>.
   */
  private void collectInformationForWS(String content, RepositioningInfo info) {
    if(content == null || info == null) return;
    // analyse the content and correct the repositioning information
    char ch;
    int startWS, endWS;
    startWS = endWS = -1;
    int contentLength = content.length();
    for(int i = 0; i < contentLength; ++i) {
      ch = content.charAt(i);
      // is whitespace
      if(ch <= ' ') {
        if(startWS == -1) {
          startWS = i;
        } // if
        endWS = i;
      } else {
        if(endWS - startWS > 0) {
          // put the repositioning information about the WS substitution
          info
                  .addPositionInfo(startWS, (endWS - startWS + 1),
                          0, 1);
        } // if
        // clear positions
        startWS = endWS = -1;
      }// if
    } // for
  } // collectInformationForWS

  /** Clear all the data members of the object. */
  @Override
  public void cleanup() {
    defaultAnnots = null;
    if((namedAnnotSets != null) && (!namedAnnotSets.isEmpty()))
      namedAnnotSets.clear();
    if(DEBUG) Out.prln("Document cleanup called");
    if(this.lrPersistentId != null)
      Gate.getCreoleRegister().removeCreoleListener(this);
    if(this.getDataStore() != null)
      this.getDataStore().removeDatastoreListener(this);
  } // cleanup()


  /** Get the specific MIME type for this document, if set */
  public String getMimeType() {
    return mimeType;
  }
  
  /** Set the specific MIME type for this document */
  @Optional
  @CreoleParameter(
      comment = "MIME type of the document.  If unspecified it will be "
            + "inferred from the file extension, etc.")
  public void setMimeType(String newMimeType) {
    this.mimeType = newMimeType;
  }
  
  /** Documents are identified by URLs */
  @Override
  public URL getSourceUrl() {
    return sourceUrl;
  }

  /** Set method for the document's URL */
  @Override
  @CreoleParameter(disjunction = "source", priority = 1, comment = "Source URL",
      suffixes = "txt;text;xml;xhtm;xhtml;html;htm;sgml;sgm;mail;email;eml;rtf;pdf;doc;ppt;pptx;docx;xls;xlsx;ods;odt;odp;iob;conll")
  public void setSourceUrl(URL sourceUrl) {
    this.sourceUrl = sourceUrl;
  } // setSourceUrl

  /**
   * Documents may be packed within files; in this case an optional pair of
   * offsets refer to the location of the document.
   */
  @Override
  public Long[] getSourceUrlOffsets() {
    Long[] sourceUrlOffsets = new Long[2];
    sourceUrlOffsets[0] = sourceUrlStartOffset;
    sourceUrlOffsets[1] = sourceUrlEndOffset;
    return sourceUrlOffsets;
  } // getSourceUrlOffsets

  /**
   * Allow/disallow preserving of the original document content. If is <B>true</B>
   * the original content will be retrieved from the DocumentContent object and
   * preserved as document feature.
   */
  @Override
  @CreoleParameter(comment = "Should the document preserve the original content?",
      defaultValue = "false")
  public void setPreserveOriginalContent(Boolean b) {
    preserveOriginalContent = b;
  } // setPreserveOriginalContent

  /**
   * Get the preserving of content status of the Document.
   * 
   * @return whether the Document should preserve it's original content.
   */
  @Override
  public Boolean getPreserveOriginalContent() {
    return preserveOriginalContent;
  } // getPreserveOriginalContent

  /**
   * Allow/disallow collecting of repositioning information. If is <B>true</B>
   * information will be retrieved and preserved as document feature.<BR>
   * Preserving of repositioning information give the possibilities for
   * converting of coordinates between the original document content and
   * extracted from the document text.
   */
  @Override
  @CreoleParameter(defaultValue = "false",
      comment = "Should the document collect repositioning information")
  public void setCollectRepositioningInfo(Boolean b) {
    collectRepositioningInfo = b;
  } // setCollectRepositioningInfo

  /**
   * Get the collectiong and preserving of repositioning information for the
   * Document. <BR>
   * Preserving of repositioning information give the possibilities for
   * converting of coordinates between the original document content and
   * extracted from the document text.
   * 
   * @return whether the Document should collect and preserve information.
   */
  @Override
  public Boolean getCollectRepositioningInfo() {
    return collectRepositioningInfo;
  } // getCollectRepositioningInfo

  /**
   * Documents may be packed within files; in this case an optional pair of
   * offsets refer to the location of the document. This method gets the start
   * offset.
   */
  @Override
  public Long getSourceUrlStartOffset() {
    return sourceUrlStartOffset;
  }

  /**
   * Documents may be packed within files; in this case an optional pair of
   * offsets refer to the location of the document. This method sets the start
   * offset.
   */
  @Override
  @Optional
  @CreoleParameter(
      comment = "Start offset for documents based on ranges")
  public void setSourceUrlStartOffset(Long sourceUrlStartOffset) {
    this.sourceUrlStartOffset = sourceUrlStartOffset;
  } // setSourceUrlStartOffset

  /**
   * Documents may be packed within files; in this case an optional pair of
   * offsets refer to the location of the document. This method gets the end
   * offset.
   */
  @Override
  public Long getSourceUrlEndOffset() {
    return sourceUrlEndOffset;
  }

  /**
   * Documents may be packed within files; in this case an optional pair of
   * offsets refer to the location of the document. This method sets the end
   * offset.
   */
  @Override
  @Optional
  @CreoleParameter(
      comment = "End offset for documents based on ranges")
  public void setSourceUrlEndOffset(Long sourceUrlEndOffset) {
    this.sourceUrlEndOffset = sourceUrlEndOffset;
  } // setSourceUrlStartOffset

  /** The content of the document: a String for text; MPEG for video; etc. */
  @Override
  public DocumentContent getContent() {
    return content;
  }

  /** Set method for the document content */
  @Override
  public void setContent(DocumentContent content) {
    this.content = content;
    // stringContent is a parameter, not a normal field, and
    // should not be overwritten here.
    //this.stringContent = content.toString();
  }

  /** Get the encoding of the document content source */
  @Override
  public String getEncoding() {
    // we need to make sure we ALWAYS have an encoding
    if(encoding == null || encoding.trim().length() == 0) {
      // no encoding definded: use the platform default
      encoding = java.nio.charset.Charset.forName(
              System.getProperty("file.encoding")).name();
    }
    return encoding;
  }

  /** Set the encoding of the document content source */
  @Optional
  @CreoleParameter(comment = "Encoding", defaultValue = "UTF-8")
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  /**
   * Get the default set of annotations. The set is created if it doesn't exist
   * yet.
   */
  @Override
  public AnnotationSet getAnnotations() {
    if(defaultAnnots == null) {
      defaultAnnots = new AnnotationSetImpl(this,"");
      fireAnnotationSetAdded(new DocumentEvent(this,
              DocumentEvent.ANNOTATION_SET_ADDED, ""));
    }// if
    return defaultAnnots;
  } // getAnnotations()

  /**
   * Get a named set of annotations. Creates a new set if one with this name
   * doesn't exist yet. If the provided name is null or the empty string then
   * it returns the default annotation set.
   */
  @Override
  public AnnotationSet getAnnotations(String name) {
    if(name == null || "".equals(name)) return getAnnotations();
    if(namedAnnotSets == null) {
      namedAnnotSets = new HashMap<String, AnnotationSet>();
    }
    AnnotationSet namedSet = namedAnnotSets.get(name);
    if(namedSet == null) {
      namedSet = new AnnotationSetImpl(this, name);
      namedAnnotSets.put(name, namedSet);
      DocumentEvent evt = new DocumentEvent(this,
              DocumentEvent.ANNOTATION_SET_ADDED, name);
      fireAnnotationSetAdded(evt);
    }
    return namedSet;
  } // getAnnotations(name)

  /**
   * Make the document markup-aware. This will trigger the creation of a
   * DocumentFormat object at Document initialisation time; the DocumentFormat
   * object will unpack the markup in the Document and add it as annotations.
   * Documents are <B>not</B> markup-aware by default.
   * 
   * @param newMarkupAware
   *          markup awareness status.
   */
  @Override
  @CreoleParameter(defaultValue = "true",
      comment = "Should the document read the original markup?")
  public void setMarkupAware(Boolean newMarkupAware) {
    this.markupAware = newMarkupAware;
  }

  /**
   * Get the markup awareness status of the Document. <B>Documents are
   * markup-aware by default.</B>
   * 
   * @return whether the Document is markup aware.
   */
  @Override
  public Boolean getMarkupAware() {
    return markupAware;
  }

  /**
   * Returns an XML document aming to preserve the original markups( the
   * original markup will be in the same place and format as it was before
   * processing the document) and include (if possible) the annotations
   * specified in the aSourceAnnotationSet. It is equivalent to
   * toXml(aSourceAnnotationSet, true).
   */
  @Override
  public String toXml(Set<Annotation> aSourceAnnotationSet) {
    return toXml(aSourceAnnotationSet, true);
  }

  /**
   * Returns an XML document aming to preserve the original markups( the
   * original markup will be in the same place and format as it was before
   * processing the document) and include (if possible) the annotations
   * specified in the aSourceAnnotationSet. <b>Warning:</b> Annotations from
   * the aSourceAnnotationSet will be lost if they will cause a crosed over
   * situation.
   * 
   * @param aSourceAnnotationSet
   *          is an annotation set containing all the annotations that will be
   *          combined with the original marup set. If the param is
   *          <code>null</code> it will only dump the original markups.
   * @param includeFeatures
   *          is a boolean that controls whether the annotation features should
   *          be included or not. If false, only the annotation type is included
   *          in the tag.
   * @return a string representing an XML document containing the original
   *         markup + dumped annotations form the aSourceAnnotationSet
   */
  @Override
  @SuppressWarnings("unused")
  public String toXml(Set<Annotation> aSourceAnnotationSet, boolean includeFeatures) {
    if(hasOriginalContentFeatures()) { return saveAnnotationSetAsXmlInOrig(
            aSourceAnnotationSet, includeFeatures); } // if
    AnnotationSet originalMarkupsAnnotSet = this
            .getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    // Create a dumping annotation set on the document. It will be used for
    // dumping annotations...
    // AnnotationSet dumpingSet = new AnnotationSetImpl((Document) this);
    List<Annotation> dumpingList = new ArrayList<Annotation>(originalMarkupsAnnotSet.size());
    // This set will be constructed inside this method. If is not empty, the
    // annotation contained will be lost.
    /*
     * if (!dumpingSet.isEmpty()){ Out.prln("WARNING: The dumping annotation set
     * was not empty."+ "All annotation it contained were lost.");
     * dumpingSet.clear(); }// End if
     */
    StatusListener sListener = (StatusListener)gate.Gate
            .getListeners().get("gate.event.StatusListener");
    // Construct the dumping set in that way that all annotations will verify
    // the condition that there are not annotations which are crossed.
    // First add all annotation from the original markups
    if(sListener != null)
      sListener.statusChanged("Constructing the dumping annotation set.");
    // dumpingSet.addAll(originalMarkupsAnnotSet);
    dumpingList.addAll(originalMarkupsAnnotSet);
    // Then take all the annotations from aSourceAnnotationSet and verify if
    // they can be inserted safely into the dumpingSet. Where not possible,
    // report.
    if(aSourceAnnotationSet != null) {
      Iterator<Annotation> iter = aSourceAnnotationSet.iterator();
      while(iter.hasNext()) {
        Annotation currentAnnot = iter.next();
        if(insertsSafety(dumpingList, currentAnnot)) {
          // dumpingSet.add(currentAnnot);
          dumpingList.add(currentAnnot);
        } else if(crossedOverAnnotation != null && DEBUG) {
          try {
            Out.prln("Warning: Annotations were found to violate the "
                    + "crossed over condition: \n"
                    + "1. ["
                    + getContent().getContent(
                            crossedOverAnnotation.getStartNode().getOffset(),
                            crossedOverAnnotation.getEndNode().getOffset())
                    + " ("
                    + crossedOverAnnotation.getType()
                    + ": "
                    + crossedOverAnnotation.getStartNode().getOffset()
                    + ";"
                    + crossedOverAnnotation.getEndNode().getOffset()
                    + ")]\n"
                    + "2. ["
                    + getContent().getContent(
                            currentAnnot.getStartNode().getOffset(),
                            currentAnnot.getEndNode().getOffset()) + " ("
                    + currentAnnot.getType() + ": "
                    + currentAnnot.getStartNode().getOffset() + ";"
                    + currentAnnot.getEndNode().getOffset()
                    + ")]\nThe second one will be discarded.\n");
          } catch(gate.util.InvalidOffsetException ex) {
            throw new GateRuntimeException(ex.getMessage());
          }
        }// End if
      }// End while
    }// End if
    // kalina: order the dumping list by start offset
    Collections.sort(dumpingList, new gate.util.OffsetComparator());
    // The dumpingSet is ready to be exported as XML
    // Here we go.
    if(sListener != null)
      sListener.statusChanged("Dumping annotations as XML");
    StringBuffer xmlDoc = new StringBuffer(
            DocumentXmlUtils.DOC_SIZE_MULTIPLICATION_FACTOR
            * (this.getContent().size().intValue()));
    // Add xml header if original format was xml
    String mimeType = (String)getFeatures().get("MimeType");
    boolean wasXML = mimeType != null && mimeType.equalsIgnoreCase("text/xml");
    if(wasXML) {
      xmlDoc.append("<?xml version=\"1.0\" encoding=\"");
      xmlDoc.append(getEncoding());
      xmlDoc.append("\" ?>");
      xmlDoc.append(Strings.getNl());
    }// ENd if
    // Identify and extract the root annotation from the dumpingSet.
    theRootAnnotation = identifyTheRootAnnotation(dumpingList);
    // If a root annotation has been identified then add it explicitly at the
    // beginning of the document
    if(theRootAnnotation != null) {
      dumpingList.remove(theRootAnnotation);
      xmlDoc.append(writeStartTag(theRootAnnotation, includeFeatures));
    }// End if
    // Construct and append the rest of the document
    xmlDoc.append(saveAnnotationSetAsXml(dumpingList, includeFeatures));
    // If a root annotation has been identified then add it eplicitley at the
    // end of the document
    if(theRootAnnotation != null) {
      xmlDoc.append(writeEndTag(theRootAnnotation));
    }// End if
    if(sListener != null) sListener.statusChanged("Done.");
    return xmlDoc.toString();
  }// End toXml()

  /**
   * This method verifies if aSourceAnnotation can ve inserted safety into the
   * aTargetAnnotSet. Safety means that it doesn't violate the crossed over
   * contition with any annotation from the aTargetAnnotSet.
   * 
   * @param aTargetAnnotSet
   *          the annotation set to include the aSourceAnnotation
   * @param aSourceAnnotation
   *          the annotation to be inserted into the aTargetAnnotSet
   * @return true if the annotation inserts safety, or false otherwise.
   */
  private boolean insertsSafety(AnnotationSet aTargetAnnotSet,
          Annotation aSourceAnnotation) {
    if(aTargetAnnotSet == null || aSourceAnnotation == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    if(aSourceAnnotation.getStartNode() == null
            || aSourceAnnotation.getStartNode().getOffset() == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    if(aSourceAnnotation.getEndNode() == null
            || aSourceAnnotation.getEndNode().getOffset() == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    // Get the start and end offsets
    Long start = aSourceAnnotation.getStartNode().getOffset();
    Long end = aSourceAnnotation.getEndNode().getOffset();
    // Read aSourceAnnotation offsets long
    long s2 = start.longValue();
    long e2 = end.longValue();
    // Obtain a set with all annotations annotations that overlap
    // totaly or partially with the interval defined by the two provided offsets
    AnnotationSet as = aTargetAnnotSet.get(start, end);
    // Investigate all the annotations from as to see if there is one that
    // comes in conflict with aSourceAnnotation
    Iterator<Annotation> it = as.iterator();
    while(it.hasNext()) {
      Annotation ann = it.next();
      // Read ann offsets
      long s1 = ann.getStartNode().getOffset().longValue();
      long e1 = ann.getEndNode().getOffset().longValue();
      if(s1 < s2 && s2 < e1 && e1 < e2) {
        this.crossedOverAnnotation = ann;
        return false;
      }
      if(s2 < s1 && s1 < e2 && e2 < e1) {
        this.crossedOverAnnotation = ann;
        return false;
      }
    }// End while
    return true;
  }// insertsSafety()

  private boolean insertsSafety(List<Annotation> aTargetAnnotList,
          Annotation aSourceAnnotation) {
    if(aTargetAnnotList == null || aSourceAnnotation == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    if(aSourceAnnotation.getStartNode() == null
            || aSourceAnnotation.getStartNode().getOffset() == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    if(aSourceAnnotation.getEndNode() == null
            || aSourceAnnotation.getEndNode().getOffset() == null) {
      this.crossedOverAnnotation = null;
      return false;
    }
    // Get the start and end offsets
    Long start = aSourceAnnotation.getStartNode().getOffset();
    Long end = aSourceAnnotation.getEndNode().getOffset();
    // Read aSourceAnnotation offsets long
    long s2 = start.longValue();
    long e2 = end.longValue();
    // Obtain a set with all annotations annotations that overlap
    // totaly or partially with the interval defined by the two provided offsets
    List<Annotation> as = new ArrayList<Annotation>();
    for(int i = 0; i < aTargetAnnotList.size(); i++) {
      Annotation annot = aTargetAnnotList.get(i);
      if(annot.getStartNode().getOffset().longValue() >= s2
              && annot.getStartNode().getOffset().longValue() <= e2)
        as.add(annot);
      else if(annot.getEndNode().getOffset().longValue() >= s2
              && annot.getEndNode().getOffset().longValue() <= e2)
        as.add(annot);
    }
    // Investigate all the annotations from as to see if there is one that
    // comes in conflict with aSourceAnnotation
    Iterator<Annotation> it = as.iterator();
    while(it.hasNext()) {
      Annotation ann = it.next();
      // Read ann offsets
      long s1 = ann.getStartNode().getOffset().longValue();
      long e1 = ann.getEndNode().getOffset().longValue();
      if(s1 < s2 && s2 < e1 && e1 < e2) {
        this.crossedOverAnnotation = ann;
        return false;
      }
      if(s2 < s1 && s1 < e2 && e2 < e1) {
        this.crossedOverAnnotation = ann;
        return false;
      }
    }// End while
    return true;
  }// insertsSafety()

  /**
   * This method saves all the annotations from aDumpAnnotSet and combines them
   * with the document content.
   * 
   * @param aDumpAnnotSet
   *          is a GATE annotation set prepared to be used on the raw text from
   *          document content. If aDumpAnnotSet is <b>null<b> then an empty
   *          string will be returned.
   * @param includeFeatures
   *          is a boolean, which controls whether the annotation features and
   *          gate ID are included or not.
   * @return The XML document obtained from raw text + the information from the
   *         dump annotation set.
   */
  @SuppressWarnings("unused")
  private String saveAnnotationSetAsXml(AnnotationSet aDumpAnnotSet,
          boolean includeFeatures) {
    String content = null;
    if(this.getContent() == null)
      content = "";
    else content = this.getContent().toString();
    StringBuffer docContStrBuff =
      DocumentXmlUtils.filterNonXmlChars(new StringBuffer(content));
    if(aDumpAnnotSet == null) return docContStrBuff.toString();
    TreeMap<Long, Character> offsets2CharsMap = new TreeMap<Long, Character>();
    if(this.getContent().size().longValue() != 0) {
      // Fill the offsets2CharsMap with all the indices where
      // special chars appear
      buildEntityMapFromString(content, offsets2CharsMap);
    }// End if
    // The saving alghorithm is as follows:
    // /////////////////////////////////////////
    // Construct a set of annot with all IDs in asc order.
    // All annotations that end at that offset swap their place in descending
    // order. For each node write all the tags from left to right.
    // Construct the node set
    TreeSet<Long> offsets = new TreeSet<Long>();
    Iterator<Annotation> iter = aDumpAnnotSet.iterator();
    while(iter.hasNext()) {
      Annotation annot = iter.next();
      offsets.add(annot.getStartNode().getOffset());
      offsets.add(annot.getEndNode().getOffset());
    }// End while
    // ofsets is sorted in ascending order.
    // Iterate this set in descending order and remove an offset at each
    // iteration
    while(!offsets.isEmpty()) {
      Long offset = offsets.last();
      // Remove the offset from the set
      offsets.remove(offset);
      // Now, use it.
      // Returns a list with annotations that needs to be serialized in that
      // offset.
      List<Annotation> annotations = getAnnotationsForOffset(aDumpAnnotSet, offset);
      // Attention: the annotation are serialized from left to right
      // StringBuffer tmpBuff = new StringBuffer("");
      StringBuffer tmpBuff = new StringBuffer(DOC_SIZE_MULTIPLICATION_FACTOR_AS
              * (this.getContent().size().intValue()));
      Stack<Annotation> stack = new Stack<Annotation>();
      // Iterate through all these annotations and serialize them
      Iterator<Annotation> it = annotations.iterator();
      while(it.hasNext()) {
        Annotation a = it.next();
        it.remove();
        // Test if a Ends at offset
        if(offset.equals(a.getEndNode().getOffset())) {
          // Test if a Starts at offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // Here, the annotation a Starts and Ends at the offset
            if(null != a.getFeatures().get("isEmptyAndSpan")
                    && "true".equals(a.getFeatures().get(
                            "isEmptyAndSpan"))) {
              // Assert: annotation a with start == end and isEmptyAndSpan
              tmpBuff.append(writeStartTag(a, includeFeatures));
              stack.push(a);
            } else {
              // Assert annotation a with start == end and an empty tag
              tmpBuff.append(writeEmptyTag(a));
              // The annotation is removed from dumped set
              aDumpAnnotSet.remove(a);
            }// End if
          } else {
            // Here the annotation a Ends at the offset.
            // In this case empty the stack and write the end tag
            if(!stack.isEmpty()) {
              while(!stack.isEmpty()) {
                Annotation a1 = stack.pop();
                tmpBuff.append(writeEndTag(a1));
              }// End while
            }// End if
            tmpBuff.append(writeEndTag(a));
          }// End if
        } else {
          // The annotation a does NOT end at the offset. Let's see if it starts
          // at the offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // The annotation a starts at the offset.
            // In this case empty the stack and write the end tag
            if(!stack.isEmpty()) {
              while(!stack.isEmpty()) {
                Annotation a1 = stack.pop();
                tmpBuff.append(writeEndTag(a1));
              }// End while
            }// End if
            tmpBuff.append(writeStartTag(a, includeFeatures));
            // The annotation is removed from dumped set
            aDumpAnnotSet.remove(a);
          }// End if ( offset.equals(a.getStartNode().getOffset()) )
        }// End if ( offset.equals(a.getEndNode().getOffset()) )
      }// End while(it.hasNext()){
      // In this case empty the stack and write the end tag
      if(!stack.isEmpty()) {
        while(!stack.isEmpty()) {
          Annotation a1 = stack.pop();
          tmpBuff.append(writeEndTag(a1));
        }// End while
      }// End if
      // Before inserting tmpBuff into docContStrBuff we need to check
      // if there are chars to be replaced and if there are, they would be
      // replaced.
      if(!offsets2CharsMap.isEmpty()) {
        Long offsChar = offsets2CharsMap.lastKey();
        while(!offsets2CharsMap.isEmpty()
                && offsChar.intValue() >= offset.intValue()) {
          // Replace the char at offsChar with its corresponding entity form
          // the entitiesMap.
          docContStrBuff.replace(offsChar.intValue(), offsChar.intValue() + 1,
                  DocumentXmlUtils.entitiesMap.get(offsets2CharsMap
                          .get(offsChar)));
          // Discard the offsChar after it was used.
          offsets2CharsMap.remove(offsChar);
          // Investigate next offsChar
          if(!offsets2CharsMap.isEmpty())
            offsChar = offsets2CharsMap.lastKey();
        }// End while
      }// End if
      // Insert tmpBuff to the location where it belongs in docContStrBuff
      docContStrBuff.insert(offset.intValue(), tmpBuff.toString());
    }// End while(!offsets.isEmpty())
    // Need to replace the entities in the remaining text, if there is any text
    // So, if there are any more items in offsets2CharsMap they need to be
    // replaced
    while(!offsets2CharsMap.isEmpty()) {
      Long offsChar = offsets2CharsMap.lastKey();
      // Replace the char with its entity
      docContStrBuff.replace(offsChar.intValue(), offsChar.intValue() + 1,
              DocumentXmlUtils.entitiesMap
                      .get(offsets2CharsMap.get(offsChar)));
      // remove the offset from the map
      offsets2CharsMap.remove(offsChar);
    }// End while
    return docContStrBuff.toString();
  }// saveAnnotationSetAsXml()

  private String saveAnnotationSetAsXml(List<Annotation> aDumpAnnotList,
          boolean includeFeatures) {
    String content;
    if(this.getContent() == null)
      content = "";
    else content = this.getContent().toString();
    StringBuffer docContStrBuff =
      DocumentXmlUtils.filterNonXmlChars(new StringBuffer(content));
    if(aDumpAnnotList == null) return docContStrBuff.toString();
    StringBuffer resultStrBuff = new StringBuffer(
            DOC_SIZE_MULTIPLICATION_FACTOR_AS
                    * (this.getContent().size().intValue()));
    // last offset position used to extract portions of text
    Long lastOffset = 0L;
    TreeMap<Long, Character> offsets2CharsMap = new TreeMap<Long, Character>();
    HashMap<Long, List<Annotation>> annotsForOffset =
      new HashMap<Long, List<Annotation>>(100);
    if(this.getContent().size() != 0) {
      // Fill the offsets2CharsMap with all the indices where
      // special chars appear
      buildEntityMapFromString(content, offsets2CharsMap);
    }// End if
    // The saving alghorithm is as follows:
    // /////////////////////////////////////////
    // Construct a set of annot with all IDs in asc order.
    // All annotations that end at that offset swap their place in descending
    // order. For each node write all the tags from left to right.
    // Construct the node set
    TreeSet<Long> offsets = new TreeSet<Long>();
    Iterator<Annotation> iter = aDumpAnnotList.iterator();
    Annotation annot;
    Long start;
    Long end;
    while(iter.hasNext()) {
      annot = iter.next();
      start = annot.getStartNode().getOffset();
      end = annot.getEndNode().getOffset();
      offsets.add(start);
      offsets.add(end);
      if(annotsForOffset.containsKey(start)) {
        annotsForOffset.get(start).add(annot);
      } else {
        List<Annotation> newList = new ArrayList<Annotation>(10);
        newList.add(annot);
        annotsForOffset.put(start, newList);
      }
      if(annotsForOffset.containsKey(end)) {
        annotsForOffset.get(end).add(annot);
      } else {
        List<Annotation> newList = new ArrayList<Annotation>(10);
        newList.add(annot);
        annotsForOffset.put(end, newList);
      }
    }// End while
    // ofsets is sorted in ascending order.
    // Iterate this set in descending order and remove an offset at each
    // iteration
    Iterator<Long> offsetIt = offsets.iterator();
    Long offset;
    List<Annotation> annotations;
    // This don't have to be a large buffer - just for tags
    StringBuffer tmpBuff = new StringBuffer(255);
    Stack<Annotation> stack = new Stack<Annotation>();
    while(offsetIt.hasNext()) {
      offset = offsetIt.next();
      // Now, use it.
      // Returns a list with annotations that needs to be serialized in that
      // offset.
      annotations = annotsForOffset.get(offset);
      // order annotations in list for offset to print tags in correct order
      annotations = getAnnotationsForOffset(annotations, offset);
      // clear structures
      tmpBuff.setLength(0);
      stack.clear();
      // Iterate through all these annotations and serialize them
      Iterator<Annotation> it = annotations.iterator();
      Annotation a;
      Annotation annStack;
      while(it.hasNext()) {
        a = it.next();
        // Test if a Ends at offset
        if(offset.equals(a.getEndNode().getOffset())) {
          // Test if a Starts at offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // Here, the annotation a Starts and Ends at the offset
            if(null != a.getFeatures().get("isEmptyAndSpan")
                    && "true".equals(a.getFeatures().get(
                            "isEmptyAndSpan"))) {
              // Assert: annotation a with start == end and isEmptyAndSpan
              tmpBuff.append(writeStartTag(a, includeFeatures));
              stack.push(a);
            } else {
              // Assert annotation a with start == end and an empty tag
              tmpBuff.append(writeEmptyTag(a));
              // The annotation is removed from dumped set
              aDumpAnnotList.remove(a);
            }// End if
          } else {
            // Here the annotation a Ends at the offset.
            // In this case empty the stack and write the end tag
            if(!stack.isEmpty()) {
              while(!stack.isEmpty()) {
                annStack = stack.pop();
                tmpBuff.append(writeEndTag(annStack));
              }// End while
            }// End if
            tmpBuff.append(writeEndTag(a));
          }// End if
        } else {
          // The annotation a does NOT end at the offset. Let's see if it starts
          // at the offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // The annotation a starts at the offset.
            // In this case empty the stack and write the end tag
            if(!stack.isEmpty()) {
              while(!stack.isEmpty()) {
                annStack = stack.pop();
                tmpBuff.append(writeEndTag(annStack));
              }// End while
            }// End if
            tmpBuff.append(writeStartTag(a, includeFeatures));
            // The annotation is removed from dumped set
          }// End if ( offset.equals(a.getStartNode().getOffset()) )
        }// End if ( offset.equals(a.getEndNode().getOffset()) )
      }// End while(it.hasNext()){
      // In this case empty the stack and write the end tag
      if(!stack.isEmpty()) {
        while(!stack.isEmpty()) {
          annStack = stack.pop();
          tmpBuff.append(writeEndTag(annStack));
        }// End while
      }// End if
      // extract text from content and replace spec chars
      StringBuffer partText = new StringBuffer();
      SortedMap<Long, Character> offsetsInRange = offsets2CharsMap.subMap(lastOffset, offset);
      Long tmpOffset;
      Long tmpLastOffset = lastOffset;
      String replacement;
      // Before inserting tmpBuff into the buffer we need to check
      // if there are chars to be replaced in range
      while(!offsetsInRange.isEmpty()) {
        tmpOffset = offsetsInRange.firstKey();
        replacement = DocumentXmlUtils.entitiesMap.get(
          offsets2CharsMap.get(tmpOffset));
        partText.append(docContStrBuff.substring(
          tmpLastOffset.intValue(), tmpOffset.intValue()));
        partText.append(replacement);
        tmpLastOffset = tmpOffset + 1;
        offsetsInRange.remove(tmpOffset);
      }
      partText.append(docContStrBuff.substring(
        tmpLastOffset.intValue(), offset.intValue()));
      resultStrBuff.append(partText);
      // Insert tmpBuff to the result string
      resultStrBuff.append(tmpBuff.toString());
      lastOffset = offset;
    }// End while(!offsets.isEmpty())
    // get text to the end of content
    // extract text from content and replace spec chars
    StringBuffer partText = new StringBuffer();
    SortedMap<Long, Character> offsetsInRange = offsets2CharsMap.subMap(
      lastOffset, (long) docContStrBuff.length());
    Long tmpOffset;
    Long tmpLastOffset = lastOffset;
    String replacement;
    // Need to replace the entities in the remaining text, if there is any text
    // So, if there are any more items in offsets2CharsMap for remaining text
    // they need to be replaced
    while(!offsetsInRange.isEmpty()) {
      tmpOffset = offsetsInRange.firstKey();
      replacement = DocumentXmlUtils.entitiesMap.get(
        offsets2CharsMap.get(tmpOffset));
      partText.append(docContStrBuff.substring(
        tmpLastOffset.intValue(), tmpOffset.intValue()));
      partText.append(replacement);
      tmpLastOffset = tmpOffset + 1;
      offsetsInRange.remove(tmpOffset);
    }
    partText.append(docContStrBuff.substring(
      tmpLastOffset.intValue(), docContStrBuff.length()));
    resultStrBuff.append(partText);
    return resultStrBuff.toString();
  }// saveAnnotationSetAsXml()

  /*
   * Old method created by Cristian. Create content backward.
   * 
   * private String saveAnnotationSetAsXml(List aDumpAnnotList, boolean
   * includeFeatures){ String content = null; if (this.getContent()== null)
   * content = new String(""); else content = this.getContent().toString();
   * StringBuffer docContStrBuff = filterNonXmlChars(new StringBuffer(content));
   * if (aDumpAnnotList == null) return docContStrBuff.toString();
   * 
   * TreeMap offsets2CharsMap = new TreeMap(); HashMap annotsForOffset = new
   * HashMap(100); if (this.getContent().size().longValue() != 0){ // Fill the
   * offsets2CharsMap with all the indices where // special chars appear
   * buildEntityMapFromString(content,offsets2CharsMap); }//End if // The saving
   * alghorithm is as follows: /////////////////////////////////////////// //
   * Construct a set of annot with all IDs in asc order. // All annotations that
   * end at that offset swap their place in descending // order. For each node
   * write all the tags from left to right. // Construct the node set TreeSet
   * offsets = new TreeSet(); Iterator iter = aDumpAnnotList.iterator(); while
   * (iter.hasNext()){ Annotation annot = (Annotation) iter.next();
   * offsets.add(annot.getStartNode().getOffset());
   * offsets.add(annot.getEndNode().getOffset()); if
   * (annotsForOffset.containsKey(annot.getStartNode().getOffset())) { ((List)
   * annotsForOffset.get(annot.getStartNode().getOffset())).add(annot); } else {
   * List newList = new ArrayList(10); newList.add(annot);
   * annotsForOffset.put(annot.getStartNode().getOffset(), newList); } if
   * (annotsForOffset.containsKey(annot.getEndNode().getOffset())) { ((List)
   * annotsForOffset.get(annot.getEndNode().getOffset())).add(annot); } else {
   * List newList = new ArrayList(10); newList.add(annot);
   * annotsForOffset.put(annot.getEndNode().getOffset(), newList); } }// End
   * while // ofsets is sorted in ascending order. // Iterate this set in
   * descending order and remove an offset at each // iteration while
   * (!offsets.isEmpty()){ Long offset = (Long)offsets.last(); // Remove the
   * offset from the set offsets.remove(offset); // Now, use it. // Returns a
   * list with annotations that needs to be serialized in that // offset. //
   * List annotations = getAnnotationsForOffset(aDumpAnnotList,offset); List
   * annotations = (List) annotsForOffset.get(offset); annotations =
   * getAnnotationsForOffset(annotations,offset); // Attention: the annotation
   * are serialized from left to right // StringBuffer tmpBuff = new
   * StringBuffer(""); StringBuffer tmpBuff = new StringBuffer(
   * DOC_SIZE_MULTIPLICATION_FACTOR*(this.getContent().size().intValue()));
   * Stack stack = new Stack(); // Iterate through all these annotations and
   * serialize them Iterator it = annotations.iterator(); while(it.hasNext()){
   * Annotation a = (Annotation) it.next(); it.remove(); // Test if a Ends at
   * offset if ( offset.equals(a.getEndNode().getOffset()) ){ // Test if a
   * Starts at offset if ( offset.equals(a.getStartNode().getOffset()) ){ //
   * Here, the annotation a Starts and Ends at the offset if ( null !=
   * a.getFeatures().get("isEmptyAndSpan") &&
   * "true".equals((String)a.getFeatures().get("isEmptyAndSpan"))){ // Assert:
   * annotation a with start == end and isEmptyAndSpan
   * tmpBuff.append(writeStartTag(a, includeFeatures)); stack.push(a); }else{ //
   * Assert annotation a with start == end and an empty tag
   * tmpBuff.append(writeEmptyTag(a)); // The annotation is removed from dumped
   * set aDumpAnnotList.remove(a); }// End if }else{ // Here the annotation a
   * Ends at the offset. // In this case empty the stack and write the end tag
   * if (!stack.isEmpty()){ while(!stack.isEmpty()){ Annotation a1 =
   * (Annotation)stack.pop(); tmpBuff.append(writeEndTag(a1)); }// End while }//
   * End if tmpBuff.append(writeEndTag(a)); }// End if }else{ // The annotation
   * a does NOT end at the offset. Let's see if it starts // at the offset if (
   * offset.equals(a.getStartNode().getOffset()) ){ // The annotation a starts
   * at the offset. // In this case empty the stack and write the end tag if
   * (!stack.isEmpty()){ while(!stack.isEmpty()){ Annotation a1 =
   * (Annotation)stack.pop(); tmpBuff.append(writeEndTag(a1)); }// End while }//
   * End if tmpBuff.append(writeStartTag(a, includeFeatures)); // The annotation
   * is removed from dumped set aDumpAnnotList.remove(a); }// End if (
   * offset.equals(a.getStartNode().getOffset()) ) }// End if (
   * offset.equals(a.getEndNode().getOffset()) ) }// End while(it.hasNext()){ //
   * In this case empty the stack and write the end tag if (!stack.isEmpty()){
   * while(!stack.isEmpty()){ Annotation a1 = (Annotation)stack.pop();
   * tmpBuff.append(writeEndTag(a1)); }// End while }// End if // Before
   * inserting tmpBuff into docContStrBuff we need to check // if there are
   * chars to be replaced and if there are, they would be // replaced. if
   * (!offsets2CharsMap.isEmpty()){ Long offsChar = (Long)
   * offsets2CharsMap.lastKey(); while( !offsets2CharsMap.isEmpty() &&
   * offsChar.intValue() >= offset.intValue()){ // Replace the char at offsChar
   * with its corresponding entity form // the entitiesMap.
   * docContStrBuff.replace(offsChar.intValue(),offsChar.intValue()+1,
   * (String)entitiesMap.get((Character)offsets2CharsMap.get(offsChar))); //
   * Discard the offsChar after it was used. offsets2CharsMap.remove(offsChar); //
   * Investigate next offsChar if (!offsets2CharsMap.isEmpty()) offsChar =
   * (Long) offsets2CharsMap.lastKey(); }// End while }// End if // Insert
   * tmpBuff to the location where it belongs in docContStrBuff
   * docContStrBuff.insert(offset.intValue(),tmpBuff.toString()); }// End
   * while(!offsets.isEmpty()) // Need to replace the entities in the remaining
   * text, if there is any text // So, if there are any more items in
   * offsets2CharsMap they need to be // replaced while
   * (!offsets2CharsMap.isEmpty()){ Long offsChar = (Long)
   * offsets2CharsMap.lastKey(); // Replace the char with its entity
   * docContStrBuff.replace(offsChar.intValue(),offsChar.intValue()+1,
   * (String)entitiesMap.get((Character)offsets2CharsMap.get(offsChar))); //
   * remove the offset from the map offsets2CharsMap.remove(offsChar); }// End
   * while return docContStrBuff.toString(); }// saveAnnotationSetAsXml()
   */
  /**
   * Return true only if the document has features for original content and
   * repositioning information.
   */
  private boolean hasOriginalContentFeatures() {
    FeatureMap features = getFeatures();
    boolean result = false;
    result = (features
            .get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME) != null)
            && (features
                    .get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME) != null);
    return result;
  } // hasOriginalContentFeatures

  /**
   * This method saves all the annotations from aDumpAnnotSet and combines them
   * with the original document content, if preserved as feature.
   * 
   * @param aSourceAnnotationSet
   *          is a GATE annotation set prepared to be used on the raw text from
   *          document content. If aDumpAnnotSet is <b>null<b> then an empty
   *          string will be returned.
   * @param includeFeatures
   *          is a boolean, which controls whether the annotation features and
   *          gate ID are included or not.
   * @return The XML document obtained from raw text + the information from the
   *         dump annotation set.
   */
  private String saveAnnotationSetAsXmlInOrig(Set<Annotation> aSourceAnnotationSet,
          boolean includeFeatures) {
    StringBuffer docContStrBuff;
    String origContent;
    origContent = (String)features
            .get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
    if(origContent == null) {
      origContent = "";
    } // if
    long originalContentSize = origContent.length();
    RepositioningInfo repositioning = (RepositioningInfo)getFeatures().get(
            GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);
    docContStrBuff = new StringBuffer(origContent);
    if(aSourceAnnotationSet == null) return docContStrBuff.toString();
    StatusListener sListener = (StatusListener)gate.Gate
            .getListeners().get("gate.event.StatusListener");
    AnnotationSet originalMarkupsAnnotSet = this
            .getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    // Create a dumping annotation set on the document. It will be used for
    // dumping annotations...
    AnnotationSet dumpingSet = new AnnotationSetImpl(this);
    if(sListener != null)
      sListener.statusChanged("Constructing the dumping annotation set.");
    // Then take all the annotations from aSourceAnnotationSet and verify if
    // they can be inserted safely into the dumpingSet. Where not possible,
    // report.
    
    Iterator<Annotation> iter = aSourceAnnotationSet.iterator();
    Annotation currentAnnot;
    while(iter.hasNext()) {
      currentAnnot = iter.next();
      if(insertsSafety(originalMarkupsAnnotSet, currentAnnot)
              && insertsSafety(dumpingSet, currentAnnot)) {
        dumpingSet.add(currentAnnot);
      } else {
        Out.prln("Warning: Annotation with ID=" + currentAnnot.getId()
                + ", startOffset=" + currentAnnot.getStartNode().getOffset()
                + ", endOffset=" + currentAnnot.getEndNode().getOffset()
                + ", type=" + currentAnnot.getType()
                + " was found to violate the"
                + " crossed over condition. It will be discarded");
      }// End if
    }// End while
    
    // The dumpingSet is ready to be exported as XML
    // Here we go.
    if(sListener != null)
      sListener.statusChanged("Dumping annotations as XML");
    // /////////////////////////////////////////
    // Construct a set of annot with all IDs in asc order.
    // All annotations that end at that offset swap their place in descending
    // order. For each node write all the tags from left to right.
    // Construct the node set
    TreeSet<Long> offsets = new TreeSet<Long>();
    iter = aSourceAnnotationSet.iterator();
    while(iter.hasNext()) {
      Annotation annot = iter.next();
      offsets.add(annot.getStartNode().getOffset());
      offsets.add(annot.getEndNode().getOffset());
    }// End while
    // ofsets is sorted in ascending order.
    // Iterate this set in descending order and remove an offset at each
    // iteration
    while(!offsets.isEmpty()) {
      Long offset = offsets.last();
      // Remove the offset from the set
      offsets.remove(offset);
      // Now, use it.
      // Returns a list with annotations that needs to be serialized in that
      // offset.
      List<Annotation> annotations = getAnnotationsForOffset(aSourceAnnotationSet, offset);
      // Attention: the annotation are serialized from left to right
      StringBuffer tmpBuff = new StringBuffer("");
      Stack<Annotation> stack = new Stack<Annotation>();
      // Iterate through all these annotations and serialize them
      Iterator<Annotation> it = annotations.iterator();
      Annotation a = null;
      while(it.hasNext()) {
        a = it.next();
        it.remove();
        // Test if a Ends at offset
        if(offset.equals(a.getEndNode().getOffset())) {
          // Test if a Starts at offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // Here, the annotation a Starts and Ends at the offset
            if(null != a.getFeatures().get("isEmptyAndSpan")
                    && "true".equals(a.getFeatures().get(
                            "isEmptyAndSpan"))) {
              // Assert: annotation a with start == end and isEmptyAndSpan
              tmpBuff.append(writeStartTag(a, includeFeatures, false));
              stack.push(a);
            } else {
              // Assert annotation a with start == end and an empty tag
              tmpBuff.append(writeEmptyTag(a, false));
              // The annotation is removed from dumped set
              aSourceAnnotationSet.remove(a);
            }// End if
          } else {
            // Here the annotation a Ends at the offset.
            // In this case empty the stack and write the end tag
            while(!stack.isEmpty()) {
              Annotation a1 = stack.pop();
              tmpBuff.append(writeEndTag(a1));
            }// End while
            tmpBuff.append(writeEndTag(a));
          }// End if
        } else {
          // The annotation a does NOT end at the offset. Let's see if it starts
          // at the offset
          if(offset.equals(a.getStartNode().getOffset())) {
            // The annotation a starts at the offset.
            // In this case empty the stack and write the end tag
            while(!stack.isEmpty()) {
              Annotation a1 = stack.pop();
              tmpBuff.append(writeEndTag(a1));
            }// End while
            tmpBuff.append(writeStartTag(a, includeFeatures, false));
            // The annotation is removed from dumped set
            aSourceAnnotationSet.remove(a);
          }// End if ( offset.equals(a.getStartNode().getOffset()) )
        }// End if ( offset.equals(a.getEndNode().getOffset()) )
      }// End while(it.hasNext()){
      // In this case empty the stack and write the end tag
      while(!stack.isEmpty()) {
        Annotation a1 = stack.pop();
        tmpBuff.append(writeEndTag(a1));
      }// End while
      long originalPosition = -1;
      boolean backPositioning = a != null
              && offset.equals(a.getEndNode().getOffset());
      if(backPositioning) {
        // end of the annotation correction
        originalPosition = repositioning
                .getOriginalPos(offset.intValue(), true);
      } // if
      if(originalPosition == -1) {
        originalPosition = repositioning.getOriginalPos(offset.intValue());
      } // if
      // Insert tmpBuff to the location where it belongs in docContStrBuff
      if(originalPosition != -1 && originalPosition <= originalContentSize) {
        docContStrBuff.insert((int)originalPosition, tmpBuff.toString());
      } else {
        Out.prln("Error in the repositioning. The offset (" + offset.intValue()
                + ") could not be positioned in the original document. \n"
                + "Calculated position is: " + originalPosition
                + " placed back: " + backPositioning);
      } // if
    }// End while(!offsets.isEmpty())
    if(theRootAnnotation != null)
      docContStrBuff.append(writeEndTag(theRootAnnotation));
    return docContStrBuff.toString();
  } // saveAnnotationSetAsXmlInOrig()

  /**
   * This method returns a list with annotations ordered that way that they can
   * be serialized from left to right, at the offset. If one of the params is
   * null then an empty list will be returned.
   * 
   * @param aDumpAnnotSet
   *          is a set containing all annotations that will be dumped.
   * @param offset
   *          represent the offset at witch the annotation must start AND/OR
   *          end.
   * @return a list with those annotations that need to be serialized.
   */
  private List<Annotation> getAnnotationsForOffset(Set<Annotation> aDumpAnnotSet, Long offset) {
    List<Annotation> annotationList = new LinkedList<Annotation>();
    if(aDumpAnnotSet == null || offset == null) return annotationList;
    Set<Annotation> annotThatStartAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_END_OFFSET, DESC));
    Set<Annotation> annotThatEndAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_START_OFFSET, DESC));
    Set<Annotation> annotThatStartAndEndAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_ANNOT_ID, ASC));
    // Fill these tree lists with annotation tat start, end or start and
    // end at the offset.
    Iterator<Annotation> iter = aDumpAnnotSet.iterator();
    while(iter.hasNext()) {
      Annotation ann = iter.next();
      if(offset.equals(ann.getStartNode().getOffset())) {
        if(offset.equals(ann.getEndNode().getOffset()))
          annotThatStartAndEndAtOffset.add(ann);
        else annotThatStartAtOffset.add(ann);
      } else {
        if(offset.equals(ann.getEndNode().getOffset()))
          annotThatEndAtOffset.add(ann);
      }// End if
    }// End while
    annotationList.addAll(annotThatEndAtOffset);
    annotThatEndAtOffset = null;
    annotationList.addAll(annotThatStartAtOffset);
    annotThatStartAtOffset = null;
    iter = annotThatStartAndEndAtOffset.iterator();
    while(iter.hasNext()) {
      Annotation ann = iter.next();
      Iterator<Annotation> it = annotationList.iterator();
      boolean breaked = false;
      while(it.hasNext()) {
        Annotation annFromList = it.next();
        if(annFromList.getId().intValue() > ann.getId().intValue()) {
          annotationList.add(annotationList.indexOf(annFromList), ann);
          breaked = true;
          break;
        }// End if
      }// End while
      if(!breaked) annotationList.add(ann);
      iter.remove();
    }// End while
    return annotationList;
  }// getAnnotationsForOffset()

  private List<Annotation> getAnnotationsForOffset(List<Annotation> aDumpAnnotList, Long offset) {
    List<Annotation> annotationList = new ArrayList<Annotation>();
    if(aDumpAnnotList == null || offset == null) return annotationList;
    Set<Annotation> annotThatStartAtOffset;
    Set<Annotation> annotThatEndAtOffset;
    Set<Annotation> annotThatStartAndEndAtOffset;
    annotThatStartAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_END_OFFSET, DESC));
    annotThatEndAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_START_OFFSET, DESC));
    annotThatStartAndEndAtOffset = new TreeSet<Annotation>(new AnnotationComparator(
            ORDER_ON_ANNOT_ID, ASC));
    // Fill these tree lists with annotation tat start, end or start and
    // end at the offset.
    Iterator<Annotation> iter = aDumpAnnotList.iterator();
    while(iter.hasNext()) {
      Annotation ann = iter.next();
      if(offset.equals(ann.getStartNode().getOffset())) {
        if(offset.equals(ann.getEndNode().getOffset()))
          annotThatStartAndEndAtOffset.add(ann);
        else annotThatStartAtOffset.add(ann);
      } else {
        if(offset.equals(ann.getEndNode().getOffset()))
          annotThatEndAtOffset.add(ann);
      }// End if
    }// End while
    annotationList.addAll(annotThatEndAtOffset);
    annotationList.addAll(annotThatStartAtOffset);
    annotThatEndAtOffset = null;
    annotThatStartAtOffset = null;
    iter = annotThatStartAndEndAtOffset.iterator();
    while(iter.hasNext()) {
      Annotation ann = iter.next();
      Iterator<Annotation> it = annotationList.iterator();
      boolean breaked = false;
      while(it.hasNext()) {
        Annotation annFromList = it.next();
        if(annFromList.getId().intValue() > ann.getId().intValue()) {
          annotationList.add(annotationList.indexOf(annFromList), ann);
          breaked = true;
          break;
        }// End if
      }// End while
      if(!breaked) annotationList.add(ann);
      iter.remove();
    }// End while
    return annotationList;
  }// getAnnotationsForOffset()

  private String writeStartTag(Annotation annot, boolean includeFeatures) {
    return writeStartTag(annot, includeFeatures, true);
  } // writeStartTag

  /** Returns a string representing a start tag based on the input annot */
  private String writeStartTag(Annotation annot, boolean includeFeatures,
          boolean includeNamespace) {

    // Get the annot feature used to store the namespace prefix, if it
    // has been defined
    String nsPrefix = null;
    
    if (serializeNamespaceInfo)
      nsPrefix = (String)annot.getFeatures().get(namespacePrefixFeature);

    AnnotationSet originalMarkupsAnnotSet = this
            .getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    StringBuffer strBuff = new StringBuffer("");
    if(annot == null) return strBuff.toString();
    // if (!addGatePreserveFormatTag && isRootTag){
    if(theRootAnnotation != null
            && annot.getId().equals(theRootAnnotation.getId())) {
      // the features are included either if desired or if that's an annotation
      // from the original markup of the document. We don't want for example to
      // spoil all links in an HTML file!
      if(includeFeatures) {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(" ");
        if(includeNamespace) {
          // but don't add the gate ns declaration if it's already there!
          if (annot.getFeatures().get("xmlns:gate") == null)
            strBuff.append("xmlns:gate=\"http://www.gate.ac.uk\"");
          strBuff.append(" gate:");
        }
        strBuff.append("gateId=\"");
        strBuff.append(annot.getId());
        strBuff.append("\"");
        strBuff.append(" ");
        if(includeNamespace) {
          strBuff.append("gate:");
        }
        strBuff.append("annotMaxId=\"");
        strBuff.append(nextAnnotationId);
        strBuff.append("\"");
        strBuff.append(writeFeatures(annot.getFeatures(), includeNamespace));
        strBuff.append(">");
      } else if(originalMarkupsAnnotSet.contains(annot)) {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(writeFeatures(annot.getFeatures(), includeNamespace));
        strBuff.append(">");
      } else {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(">");
      }
    } else {
      // the features are included either if desired or if that's an annotation
      // from the original markup of the document. We don't want for example to
      // spoil all links in an HTML file!
      if(includeFeatures) {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(" ");
        if(includeNamespace) {
          strBuff.append("gate:");
        } // if includeNamespaces
        strBuff.append("gateId=\"");
        strBuff.append(annot.getId());
        strBuff.append("\"");
        strBuff.append(writeFeatures(annot.getFeatures(), includeNamespace));
        strBuff.append(">");
      } else if(originalMarkupsAnnotSet.contains(annot)) {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(writeFeatures(annot.getFeatures(), includeNamespace));
        strBuff.append(">");
      } else {
        strBuff.append("<");
        if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
        strBuff.append(annot.getType());
        strBuff.append(">");
      }
    }// End if
    return strBuff.toString();
  }// writeStartTag()

  /**
   * Identifies the root annotations inside an annotation set. The root
   * annotation is the one that starts at offset 0, and has the greatest span.
   * If there are more than one with this function, then the annotation with the
   * smalled ID wil be selected as root. If none is identified it will return
   * null.
   * 
   * @param anAnnotationSet
   *          The annotation set possibly containing the root annotation.
   * @return The root annotation or null is it fails
   */
  @SuppressWarnings("unused")
  private Annotation identifyTheRootAnnotation(AnnotationSet anAnnotationSet) {
    if(anAnnotationSet == null) return null;
    // If the starting node of this annotation is not null, then the annotation
    // set will not have a root annotation.
    Node startNode = anAnnotationSet.firstNode();
    Node endNode = anAnnotationSet.lastNode();
    // This is placed here just to speed things up. The alghorithm bellow can
    // can identity the annotation that span over the entire set and with the
    // smallest ID. However the root annotation will have to have the start
    // offset equal to 0.
    if(startNode.getOffset().longValue() != 0) return null;
    // Go anf find the annotation.
    Annotation theRootAnnotation = null;
    // Check if there are annotations starting at offset 0. If there are, then
    // check all of them to see which one has the greatest span. Basically its
    // END offset should be the bigest offset from the input annotation set.
    long start = startNode.getOffset().longValue();
    long end = endNode.getOffset().longValue();
    for(Iterator<Annotation> it = anAnnotationSet.iterator(); it.hasNext();) {
      Annotation currentAnnot = it.next();
      // If the currentAnnot has both its Start and End equals to the Start and
      // end of the AnnotationSet then check to see if its ID is the smallest.
      if((start == currentAnnot.getStartNode().getOffset().longValue())
              && (end == currentAnnot.getEndNode().getOffset().longValue())) {
        // The currentAnnotation has is a potencial root one.
        if(theRootAnnotation == null)
          theRootAnnotation = currentAnnot;
        else {
          // If its ID is greater that the currentAnnot then update the root
          if(theRootAnnotation.getId().intValue() > currentAnnot.getId()
                  .intValue()) theRootAnnotation = currentAnnot;
        }// End if
      }// End if
    }// End for
    return theRootAnnotation;
  }// End identifyTheRootAnnotation()

  private Annotation identifyTheRootAnnotation(List<Annotation> anAnnotationList) {
    if(anAnnotationList == null || anAnnotationList.isEmpty()) return null;
    // If the first annotation in the list (which is sorted by start offset)
    // does not have an offset = 0, then there's no root tag.
    if(anAnnotationList.get(0).getStartNode().getOffset()
            .longValue() > 0) return null;
    // If there's a single annotation and it starts at the start (which we
    // already know it does), make sure it ends at the end.
    if(anAnnotationList.size() == 1) {
      Annotation onlyAnn = anAnnotationList.get(0);
      if(onlyAnn.getEndNode().getOffset().equals(content.size()))
        return onlyAnn;
      return null;
    }
    // find the limits
    long start = 0; // we know this already
    long end = 0; // end = 0 will be improved by the next loop
    for(int i = 0; i < anAnnotationList.size(); i++) {
      Annotation anAnnotation = anAnnotationList.get(i);
      long localEnd = anAnnotation.getEndNode().getOffset().longValue();
      if(localEnd > end) end = localEnd;
    }
    // Go and find the annotation.
    // look at all annotations that start at 0 and end at end
    // if there are several, choose the one with the smallest ID
    Annotation theRootAnnotation = null;
    for(int i = 0; i < anAnnotationList.size(); i++) {
      Annotation currentAnnot = anAnnotationList.get(i);
      long localStart = currentAnnot.getStartNode().getOffset().longValue();
      long localEnd = currentAnnot.getEndNode().getOffset().longValue();
      // If the currentAnnot has both its Start and End equals to the Start and
      // end of the AnnotationSet then check to see if its ID is the smallest.
      if((start == localStart) && (end == localEnd)) {
        // The currentAnnotation has is a potential root one.
        if(theRootAnnotation == null)
          theRootAnnotation = currentAnnot;
        else {
          // If root's ID is greater that the currentAnnot then update the root
          if(theRootAnnotation.getId().intValue() > currentAnnot.getId()
                  .intValue()) theRootAnnotation = currentAnnot;
        }// End if
      }// End if
    }// End for
    return theRootAnnotation;
  }// End identifyTheRootAnnotation()

  /**
   * This method takes aScanString and searches for those chars from entitiesMap
   * that appear in the string. A tree map(offset2Char) is filled using as key
   * the offsets where those Chars appear and the Char. If one of the params is
   * null the method simply returns.
   */
  private void buildEntityMapFromString(String aScanString, TreeMap<Long, Character> aMapToFill) {
    if(aScanString == null || aMapToFill == null) return;
    if(DocumentXmlUtils.entitiesMap == null || DocumentXmlUtils.entitiesMap.isEmpty()) {
      Err.prln("WARNING: Entities map was not initialised !");
      return;
    }// End if
    // Fill the Map with the offsets of the special chars
    Iterator<Character> entitiesMapIterator = DocumentXmlUtils.entitiesMap.keySet().iterator();
    Character c;
    int fromIndex;
    while(entitiesMapIterator.hasNext()) {
      c = entitiesMapIterator.next();
      fromIndex = 0;
      while(-1 != fromIndex) {
        fromIndex = aScanString.indexOf(c.charValue(), fromIndex);
        if(-1 != fromIndex) {
          aMapToFill.put(Long.valueOf(fromIndex), c);
          fromIndex++;
        }// End if
      }// End while
    }// End while
  }// buildEntityMapFromString();

  private String writeEmptyTag(Annotation annot) {
    return writeEmptyTag(annot, true);
  } // writeEmptyTag

  
  /** Returns a string representing an empty tag based on the input annot */
  private String writeEmptyTag(Annotation annot, boolean includeNamespace) {
    // Get the annot feature used to store the namespace prefix, if it
    // has been defined
    String nsPrefix = null;
    if (serializeNamespaceInfo)
      nsPrefix = (String)annot.getFeatures().get(namespacePrefixFeature);

    StringBuffer strBuff = new StringBuffer("");
    if(annot == null) return strBuff.toString();
    strBuff.append("<");
    if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
    strBuff.append(annot.getType());
    AnnotationSet originalMarkupsAnnotSet = this
            .getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
    if(!originalMarkupsAnnotSet.contains(annot)) {
      strBuff.append(" gateId=\"");
      strBuff.append(annot.getId());
      strBuff.append("\"");
    }
    strBuff.append(writeFeatures(annot.getFeatures(), includeNamespace));
    strBuff.append("/>");
    return strBuff.toString();
  }// writeEmptyTag()

  /** Returns a string representing an end tag based on the input annot */
  private String writeEndTag(Annotation annot) {
    // Get the annot feature used to store the namespace prefix, if it
    // has been defined
    String nsPrefix = null;
    if (serializeNamespaceInfo)
      nsPrefix = (String)annot.getFeatures().get(namespacePrefixFeature);

    StringBuffer strBuff = new StringBuffer("");
    if(annot == null) return strBuff.toString();
    /*
     * if (annot.getType().indexOf(" ") != -1) Out.prln("Warning: Truncating end
     * tag to first word for annot type \"" +annot.getType()+ "\". ");
     */
    strBuff.append("</");
    if (nsPrefix != null && !nsPrefix.isEmpty())
          strBuff.append(nsPrefix + ":");
    strBuff.append(annot.getType() + ">");
    return strBuff.toString();
  }// writeEndTag()

  /** Returns a string representing a FeatureMap serialized as XML attributes */
  private String writeFeatures(FeatureMap feat, boolean includeNamespace) {
    StringBuffer strBuff = new StringBuffer("");
    if(feat == null) return strBuff.toString();
    Iterator<Object> it = feat.keySet().iterator();
    while(it.hasNext()) {
      Object key = it.next();
      Object value = feat.get(key);
      if((key != null) && (value != null)) {
        /**
         * Eliminate namespace prefix feature and rename namespace uri feature
         * to xmlns:prefix=uri
         * if these have been specified in the markup and in the config
         */
        if (serializeNamespaceInfo) {
          String nsPrefix = "xmlns:" + (String)feat.get(namespacePrefixFeature);

          if (nsPrefix.equals(key.toString())) continue;
          if (namespacePrefixFeature.equals(key.toString())) continue;
          
          if (namespaceURIFeature.equals(key.toString())) {
            strBuff.append(" ");
            strBuff.append(nsPrefix + "=\"" + value.toString() + "\"");
            return strBuff.toString();
          }
        }
        // Eliminate a feature inserted at reading time and which help to
        // take some decissions at saving time
        if("isEmptyAndSpan".equals(key.toString())) continue;
        if(!String.class.isAssignableFrom(key.getClass())) {
          Out.prln("Warning:Found a feature NAME(" + key
                  + ") that isn't a String.(feature discarded)");
          continue;
        }// End if
        if(!(String.class.isAssignableFrom(value.getClass())
                || Number.class.isAssignableFrom(value.getClass()) || java.util.Collection.class
                .isAssignableFrom(value.getClass()) || Boolean.class.isAssignableFrom(value.getClass()))) {
          Out.prln("Warning:Found a feature VALUE(" + value
                  + ") that doesn't came"
                  + " from String, Number, Boolean, or Collection.(feature discarded)");
          continue;
        }// End if
        if("matches".equals(key)) {
          strBuff.append(" ");
          if(includeNamespace) {
            strBuff.append("gate:");
          }
          // strBuff.append(key);
          // replace non XML chars in attribute name
          strBuff.append(DocumentXmlUtils.combinedNormalisation(key
                  .toString()));
          strBuff.append("=\"");
        } else {
          strBuff.append(" ");
          // strBuff.append(key);
          // replace non XML chars in attribute name
          strBuff.append(DocumentXmlUtils.combinedNormalisation(key
                  .toString()));
          strBuff.append("=\"");
        }
        if(java.util.Collection.class.isAssignableFrom(value.getClass())) {
          @SuppressWarnings("unchecked")
          Iterator<Object> valueIter = ((Collection<Object>)value).iterator();
          while(valueIter.hasNext()) {
            Object item = valueIter.next();
            if(!(String.class.isAssignableFrom(item.getClass()) || Number.class
                    .isAssignableFrom(item.getClass()))) continue;
            // strBuff.append(item);
            // replace non XML chars in collection item
            strBuff.append(DocumentXmlUtils.combinedNormalisation(item
                    .toString()));
            strBuff.append(";");
          }// End while
          if(strBuff.charAt(strBuff.length() - 1) == ';')
            strBuff.deleteCharAt(strBuff.length() - 1);
        } else {
          // strBuff.append(value);
          // replace non XML chars in attribute value
          strBuff.append(DocumentXmlUtils.combinedNormalisation(value
                  .toString()));
        }// End if
        strBuff.append("\"");
      }// End if
    }// End while
    return strBuff.toString();
  }// writeFeatures()

  /**
   * Returns a GateXml document that is a custom XML format for wich there is a
   * reader inside GATE called gate.xml.GateFormatXmlHandler. What it does is to
   * serialize a GATE document in an XML format.
   * 
   * Implementation note: this method simply delegates to the static {@link
   * DocumentStaxUtils#toXml(gate.Document)} method
   * 
   * @return a string representing a Gate Xml document.
   */
  @Override
  public String toXml() {
    return DocumentStaxUtils.toXml(this);
    //return DocumentXmlUtils.toXml(this);
  }// toXml

  /**
   * Returns a map (possibly empty) with the named annotation sets. It returns <code>null</code>
   * if no named annotaton set exists.
   */
  @Override
  public Map<String, AnnotationSet> getNamedAnnotationSets() {
    if (namedAnnotSets == null) {
      namedAnnotSets = new HashMap<String, AnnotationSet>();
    }
    return namedAnnotSets;
  } // getNamedAnnotationSets

  @Override
  public Set<String> getAnnotationSetNames() {
    if (namedAnnotSets == null) {
      namedAnnotSets = new HashMap<String, AnnotationSet>();
    }
    return namedAnnotSets.keySet();
  }

  /**
   * Removes one of the named annotation sets. Note that the default annotation
   * set cannot be removed.
   * 
   * @param name
   *          the name of the annotation set to be removed
   */
  @Override
  public void removeAnnotationSet(String name) {
    if(namedAnnotSets != null) {
      AnnotationSet removed = namedAnnotSets.remove(name);
      if(removed != null) {
        fireAnnotationSetRemoved(new DocumentEvent(this,
                DocumentEvent.ANNOTATION_SET_REMOVED, name));
      }
    }
  }

  /** Propagate edit changes to the document content and annotations. */
  @Override
  public void edit(Long start, Long end, DocumentContent replacement)
          throws InvalidOffsetException {
    if(!isValidOffsetRange(start, end)) throw new InvalidOffsetException("Offsets: "+start+"/"+end);
    if(content != null)
      ((DocumentContentImpl)content).edit(start, end, replacement);
    if(defaultAnnots != null)
      ((AnnotationSetImpl)defaultAnnots).edit(start, end, replacement);
    if(namedAnnotSets != null) {
      Iterator<AnnotationSet> iter = namedAnnotSets.values().iterator();
      while(iter.hasNext())
        ((AnnotationSetImpl)iter.next()).edit(start, end, replacement);
    }
    // let the listeners know
    fireContentEdited(new DocumentEvent(this, DocumentEvent.CONTENT_EDITED,
            start, end));
  } // edit(start,end,replacement)

  /**
   * Check that an offset is valid, i.e. it is non-null, greater than or equal
   * to 0 and less than the size of the document content.
   */
  public boolean isValidOffset(Long offset) {
    if(offset == null) return false;
    long o = offset.longValue();
    if(o > getContent().size().longValue() || o < 0) return false;
    return true;
  } // isValidOffset

  /**
   * Check that both start and end are valid offsets and that they constitute a
   * valid offset range, i.e. start is greater than or equal to long.
   */
  public boolean isValidOffsetRange(Long start, Long end) {
    return isValidOffset(start) && isValidOffset(end)
            && start.longValue() <= end.longValue();
  } // isValidOffsetRange(start,end)

  /** Sets the nextAnnotationId */
  public void setNextAnnotationId(int aNextAnnotationId) {
    nextAnnotationId = aNextAnnotationId;
  }// setNextAnnotationId();

  /** Generate and return the next annotation ID */
  public Integer getNextAnnotationId() {
    return nextAnnotationId++;
  } // getNextAnnotationId
  
  /** look at the next annotation ID without incrementing it */
  public Integer peakAtNextAnnotationId() {
    return nextAnnotationId;
  }

  /** Generate and return the next node ID */
  public Integer getNextNodeId() {
    return nextNodeId++;
  }

  /** Ordering based on URL.toString() and the URL offsets (if any) */
  @Override
  public int compareTo(Object o) throws ClassCastException {
    DocumentImpl other = (DocumentImpl)o;
    return getOrderingString().compareTo(other.getOrderingString());
  } // compareTo

  /**
   * Utility method to produce a string for comparison in ordering. String is
   * based on the source URL and offsets.
   */
  protected String getOrderingString() {
    if(sourceUrl == null) return toString();
    StringBuffer orderingString = new StringBuffer(sourceUrl.toString());
    if(sourceUrlStartOffset != null && sourceUrlEndOffset != null) {
      orderingString.append(sourceUrlStartOffset.toString());
      orderingString.append(sourceUrlEndOffset.toString());
    }
    return orderingString.toString();
  } // getOrderingString()

  /** The id of the next new annotation */
  protected int nextAnnotationId = 0;

  /** The id of the next new node */
  protected int nextNodeId = 0;

  /** The source URL */
  protected URL sourceUrl;

  /** The document's MIME type.  Only relevant if the document is markup aware,
   * and if omitted, DocumentFormat will attempt to determine the format to use
   * heuristically.
   */
  protected String mimeType;

  /** The document's URL name. */
  /** The content of the document */
  protected DocumentContent content;

  /** The encoding of the source of the document content */
  protected String encoding = null;

  // Data needed in toXml(AnnotationSet) methos
  /**
   * This field indicates whether or not to add the tag called
   * GatePreserveFormat to the document. HTML, XML, SGML docs won't have this
   * tag added
   */
  // private boolean addGatePreserveFormatTag = false;
  /**
   * Used by the XML dump preserving format method
   */
  private Annotation theRootAnnotation = null;

  /**
   * This field is used when creating StringBuffers for saveAnnotationSetAsXML()
   * methods. The size of the StringBuffer will be docDonctent.size() multiplied
   * by this value. It is aimed to improve the performance of StringBuffer
   */
  private static final int DOC_SIZE_MULTIPLICATION_FACTOR_AS = 3;

  /**
   * Constant used in the inner class AnnotationComparator to order annotations
   * on their start offset
   */
  private static final int ORDER_ON_START_OFFSET = 0;

  /**
   * Constant used in the inner class AnnotationComparator to order annotations
   * on their end offset
   */
  private static final int ORDER_ON_END_OFFSET = 1;

  /**
   * Constant used in the inner class AnnotationComparator to order annotations
   * on their ID
   */
  private static final int ORDER_ON_ANNOT_ID = 2;

  /**
   * Constant used in the inner class AnnotationComparator to order annotations
   * ascending
   */
  private static final int ASC = 3;

  /**
   * Constant used in the inner class AnnotationComparator to order annotations
   * descending
   */
  private static final int DESC = -3;

  /**
   * The start of the range that the content comes from at the source URL (or
   * null if none).
   */
  protected Long sourceUrlStartOffset;

  /**
   * The end of the range that the content comes from at the source URL (or null
   * if none).
   */
  protected Long sourceUrlEndOffset;

  /** The default annotation set */
  protected AnnotationSet defaultAnnots;

  /** Named sets of annotations */
  protected Map<String, AnnotationSet> namedAnnotSets;

  /**
   * A property of the document that will be set when the user wants to create
   * the document from a string, as opposed to from a URL.
   */
  private String stringContent;

  /**
   * The stringContent of a document is a property of the document that will be
   * set when the user wants to create the document from a string, as opposed to
   * from a URL. <B>Use the <TT>getContent</TT> method instead to get the
   * actual document content.</B>
   */
  public String getStringContent() {
    return stringContent;
  }

  /**
   * The stringContent of a document is a property of the document that will be
   * set when the user wants to create the document from a string, as opposed to
   * from a URL. <B>Use the <TT>setContent</TT> method instead to update the
   * actual document content.</B>
   */
  @CreoleParameter(disjunction = "source", priority = 2,
      comment = "The content of the document")
  public void setStringContent(String stringContent) {
    this.stringContent = stringContent;
  } // set StringContent

  /** Is the document markup-aware? */
  protected Boolean markupAware = Boolean.FALSE;

  // /** Hash code */
  // public int hashCode() {
  // int code = getContent().hashCode();
  // int memberCode = (defaultAnnots == null) ? 0 : defaultAnnots.hashCode();
  // code += memberCode;
  // memberCode = (encoding == null) ? 0 : encoding.hashCode();
  // code += memberCode;
  // memberCode = (features == null) ? 0 : features.hashCode();
  // code += memberCode;
  // code += (markupAware.booleanValue()) ? 0 : 1;
  // memberCode = (namedAnnotSets == null) ? 0 : namedAnnotSets.hashCode();
  // code += memberCode;
  // code += nextAnnotationId;
  // code += nextNodeId;
  // memberCode = (sourceUrl == null) ? 0 : sourceUrl.hashCode();
  // code += memberCode;
  // memberCode =
  // (sourceUrlStartOffset == null) ? 0 : sourceUrlStartOffset.hashCode();
  // code += memberCode;
  // memberCode =
  // (sourceUrlEndOffset == null) ? 0 : sourceUrlEndOffset.hashCode();
  // code += memberCode;
  // return code;
  // } // hashcode
  /** String respresentation */
  @Override
  public String toString() {
    String n = Strings.getNl();
    StringBuffer s = new StringBuffer("DocumentImpl: " + n);
    s.append("  content:" + content + n);
    s.append("  defaultAnnots:" + defaultAnnots + n);
    s.append("  encoding:" + encoding + n);
    s.append("  features:" + features + n);
    s.append("  markupAware:" + markupAware + n);
    s.append("  namedAnnotSets:" + namedAnnotSets + n);
    s.append("  nextAnnotationId:" + nextAnnotationId + n);
    s.append("  nextNodeId:" + nextNodeId + n);
    s.append("  sourceUrl:" + sourceUrl + n);
    s.append("  sourceUrlStartOffset:" + sourceUrlStartOffset + n);
    s.append("  sourceUrlEndOffset:" + sourceUrlEndOffset + n);
    s.append(n);
    return s.toString();
  } // toString

  /** Freeze the serialization UID. */
  static final long serialVersionUID = -8456893608311510260L;

  /** Inner class needed to compare annotations */
  static class AnnotationComparator implements Comparator<Annotation>, Serializable {

    private static final long serialVersionUID = -2405379880205707461L;

    int orderOn = -1;

    int orderType = ASC;

    /**
     * Constructs a comparator according to one of three sorter types:
     * ORDER_ON_ANNOT_TYPE, ORDER_ON_END_OFFSET, ORDER_ON_START_OFFSET
     */
    public AnnotationComparator(int anOrderOn, int anOrderType) {
      orderOn = anOrderOn;
      orderType = anOrderType;
    }// AnnotationComparator()

    /** This method must be implemented according to Comparator interface */
    @Override
    public int compare(Annotation a1, Annotation a2) {

      // ORDER_ON_START_OFFSET ?
      if(orderOn == ORDER_ON_START_OFFSET) {
        int result = a1.getStartNode().getOffset().compareTo(
                a2.getStartNode().getOffset());
        if(orderType == ASC) {
          // ASC
          // If they are equal then their ID will decide.
          if(result == 0) return a1.getId().compareTo(a2.getId());
          return result;
        } else {
          // DESC
          if(result == 0) return a2.getId().compareTo(a1.getId());
          return -result;
        }// End if (orderType == ASC)
      }// End if (orderOn == ORDER_ON_START_OFFSET)
      // ORDER_ON_END_OFFSET ?
      if(orderOn == ORDER_ON_END_OFFSET) {
        int result = a1.getEndNode().getOffset().compareTo(
                a2.getEndNode().getOffset());
        if(orderType == ASC) {
          // ASC
          // If they are equal then their ID will decide.
          if(result == 0) return a2.getId().compareTo(a1.getId());
          return result;
        } else {
          // DESC
          // If they are equal then their ID will decide.
          if(result == 0) return a1.getId().compareTo(a2.getId());
          return -result;
        }// End if (orderType == ASC)
      }// End if (orderOn == ORDER_ON_END_OFFSET)
      // ORDER_ON_ANNOT_ID ?
      if(orderOn == ORDER_ON_ANNOT_ID) {
        if(orderType == ASC)
          return a1.getId().compareTo(a2.getId());
        else return a2.getId().compareTo(a1.getId());
      }// End if
      return 0;
    }// compare()
  } // End inner class AnnotationComparator

  private transient Vector<DocumentListener> documentListeners;

  @Override
  public synchronized void removeDocumentListener(DocumentListener l) {
    if(documentListeners != null && documentListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<DocumentListener> v = (Vector<DocumentListener>)documentListeners.clone();
      v.removeElement(l);
      documentListeners = v;
    }
  }

  @Override
  public synchronized void addDocumentListener(DocumentListener l) {
    @SuppressWarnings("unchecked")
    Vector<DocumentListener> v = documentListeners == null
            ? new Vector<DocumentListener>(2)
            : (Vector<DocumentListener>)documentListeners.clone();
    if(!v.contains(l)) {
      v.addElement(l);
      documentListeners = v;
    }
  }

  protected void fireAnnotationSetAdded(DocumentEvent e) {
    if(documentListeners != null) {
      Vector<DocumentListener> listeners = documentListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).annotationSetAdded(e);
      }
    }
  }

  protected void fireAnnotationSetRemoved(DocumentEvent e) {
    if(documentListeners != null) {
      Vector<DocumentListener> listeners = documentListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).annotationSetRemoved(e);
      }
    }
  }

  protected void fireContentEdited(DocumentEvent e) {
    if(documentListeners != null) {
      Vector<DocumentListener> listeners = documentListeners;
      int count = listeners.size();
      for(int i = 0; i < count; i++) {
        listeners.elementAt(i).contentEdited(e);
      }
    }
  }

  @Override
  public void resourceLoaded(CreoleEvent e) {
  }

  @Override
  public void resourceUnloaded(CreoleEvent e) {
  }

  @Override
  public void datastoreOpened(CreoleEvent e) {
  }

  @Override
  public void datastoreCreated(CreoleEvent e) {
  }

  @Override
  public void resourceRenamed(Resource resource, String oldName, String newName) {
  }

  @Override
  public void datastoreClosed(CreoleEvent e) {
    if(!e.getDatastore().equals(this.getDataStore())) return;
    // close this lr, since it cannot stay open when the DS it comes from
    // is closed
    Factory.deleteResource(this);
  }

  @Override
  public void setLRPersistenceId(Object lrID) {
    super.setLRPersistenceId(lrID);
    // make persistent documents listen to the creole register
    // for events about their DS
    Gate.getCreoleRegister().addCreoleListener(this);
  }

  @Override
  public void resourceAdopted(DatastoreEvent evt) {
  }

  @Override
  public void resourceDeleted(DatastoreEvent evt) {
    if(!evt.getSource().equals(this.getDataStore())) return;
    // if an open document is deleted from a DS, then
    // it must close itself immediately, as is no longer valid
    if(evt.getResourceID().equals(this.getLRPersistenceId()))
      Factory.deleteResource(this);
  }

  @Override
  public void resourceWritten(DatastoreEvent evt) {
  }

  @Override
  public void setDataStore(DataStore dataStore)
          throws gate.persist.PersistenceException {
    super.setDataStore(dataStore);
    if(this.dataStore != null) this.dataStore.addDatastoreListener(this);
  }

  /**
   * This method added by Shafirin Andrey, to allow access to protected member
   * {@link #defaultAnnots} Required for JAPE-Debugger.
   */
  public void setDefaultAnnotations(AnnotationSet defaultAnnotations) {
    defaultAnnots = defaultAnnotations;
  }
} // class DocumentImpl
