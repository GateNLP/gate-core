/*
 *  NekoHtmlDocumentHandler.java
 *
 *  Copyright (c) 2006, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 17/Dec/2006
 *
 *  $Id: NekoHtmlDocumentHandler.java 17597 2014-03-08 15:19:43Z markagreenwood $
 */

package gate.html;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.RepositioningInfo;
import gate.event.StatusListener;
import gate.util.Err;
import gate.util.InvalidOffsetException;
import gate.util.Out;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.cyberneko.html.HTMLEventInfo;

/**
 * The XNI document handler used with NekoHTML to parse HTML documents.
 * We use XNI rather than SAX as XNI can distinguish between empty
 * elements (&lt;element/&gt;) and elements with an empty span
 * (&lt;element&gt;&lt;/element&gt;), whereas SAX just treats both cases
 * the same.
 */
public class NekoHtmlDocumentHandler
                                    implements
                                    org.apache.xerces.xni.XMLDocumentHandler,
                                    org.apache.xerces.xni.parser.XMLErrorHandler {
  private static final boolean DEBUG = false;

  private static final boolean DEBUG_GENERAL = DEBUG;

  private static final boolean DEBUG_ELEMENTS = DEBUG;

  private static final boolean DEBUG_CHARACTERS = DEBUG;

  private static final boolean DEBUG_UNUSED = DEBUG;

  public static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";

  /**
   * Constructor initialises all the private memeber data
   * 
   * @param aDocument The gate document that will be processed
   * @param anAnnotationSet The annotation set that will contain
   *          annotations resulted from the processing of the gate
   *          document
   * @param ignorableTags HTML tag names (lower case) whose text content
   *          should be ignored by this handler.
   */
  public NekoHtmlDocumentHandler(gate.Document aDocument,
          gate.AnnotationSet anAnnotationSet, Set<String> ignorableTags) {
    if(ignorableTags == null) {
      ignorableTags = new HashSet<String>();
    }
    if(DEBUG_GENERAL) {
      Out.println("Created NekoHtmlDocumentHandler.  ignorableTags = "
              + ignorableTags);
    }
    // init stack
    stack = new java.util.Stack<CustomObject>();

    // this string contains the plain text (the text without markup)
    tmpDocContent = new StringBuilder(aDocument.getContent().size().intValue());

    // colector is used later to transform all custom objects into
    // annotation objects
    colector = new LinkedList<CustomObject>();

    // the Gate document
    doc = aDocument;

    // init an annotation set for this gate document
    basicAS = anAnnotationSet;

    // first annotation ID to use
    customObjectsId = 0;

    this.ignorableTags = ignorableTags;
    
    if ( Gate.getUserConfig().get(
            GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME)!= null) {
      addSpaceOnUnpack =
        Gate.getUserConfig().getBoolean(
          GateConstants.DOCUMENT_ADD_SPACE_ON_UNPACK_FEATURE_NAME
        ).booleanValue();
    }
  }// HtmlDocumentHandler

  /**
   * Set the array of line offsets. This array holds the starting
   * character offset in the document of the beginning of each line of
   * text, to allow us to convert the NekoHTML location information
   * (line and column number) into offsets from the beginning of the
   * document for repositioning info.
   */
  public void setLineOffsets(int[] lineOffsets) {
    this.lineOffsets = lineOffsets;
  }

  /**
   * Called when the parser encounters the start of an HTML element.
   * Empty elements also trigger this method, followed immediately by an
   * {@link #endElement}.
   */
  @Override
  public void startElement(QName element, XMLAttributes attributes,
          Augmentations augs) throws XNIException {
    // deal with any outstanding character content
    charactersAction();

    if(DEBUG_ELEMENTS) {
      Out.println("startElement: " + element.localpart);
    }
    // Fire the status listener if the elements processed exceded the
    // rate
    if(0 == (++elements % ELEMENTS_RATE))
      fireStatusChangedEvent("Processed elements : " + elements);

    // Start of ignorable tag
    if(ignorableTags.contains(element.localpart)) {
      ignorableTagLevels++;
      if(DEBUG_ELEMENTS) {
        Out.println("  ignorable tag: levels = " + ignorableTagLevels);
      }
    } // if

    // Construct a feature map from the attributes list
    FeatureMap fm = Factory.newFeatureMap();

    // Take all the attributes an put them into the feature map
    for(int i = 0; i < attributes.getLength(); i++) {
      if(DEBUG_ELEMENTS) {
        Out.println("  attribute: " + attributes.getLocalName(i) + " = "
                + attributes.getValue(i));
      }
      fm.put(attributes.getLocalName(i), attributes.getValue(i));
    }

    // Just analize the tag and add some\n chars and spaces to the
    // tmpDocContent.The reason behind is that we need to have a
    // readable form
    // for the final document.
    customizeAppearanceOfDocumentWithStartTag(element.localpart);

    // create the start index of the annotation
    Long startIndex = new Long(tmpDocContent.length());

    // initialy the start index is equal with the End index
    CustomObject obj = new CustomObject(element.localpart, fm, startIndex,
            startIndex);

    // put it into the stack
    stack.push(obj);

  }

  /**
   * Called when the parser encounters character or CDATA content.
   * Characters may be reported in more than one chunk, so we gather all
   * contiguous chunks together and process them in one block.
   */
  @Override
  public void characters(XMLString text, Augmentations augs)
          throws XNIException {
    if(!readCharacterStatus) {
      if(reposInfo != null) {
        HTMLEventInfo evInfo = (augs == null) ? null : (HTMLEventInfo)augs
                .getItem(AUGMENTATIONS);
        if(evInfo == null) {
          Err.println("Warning: could not determine proper repositioning "
                  + "info for character chunk \""
                  + new String(text.ch, text.offset, text.length)
                  + "\" near offset " + charactersStartOffset
                  + ".  Save preserving format may give incorret results.");
        }
        else {
          // NekoHTML numbers lines and columns from 1, not 0
          int line = evInfo.getBeginLineNumber() - 1;
          int col = evInfo.getBeginColumnNumber() - 1;
          charactersStartOffset = lineOffsets[line] + col;
          if(DEBUG_CHARACTERS) {
            Out.println("characters: line = " + line + " (offset " +
                lineOffsets[line] + "), col = " + col + " : file offset = " +
                charactersStartOffset);
          }
        }
      }

      contentBuffer = new StringBuilder();
    }
    readCharacterStatus = true;

    boolean canAppendWS = (contentBuffer.length() == 0 || !Character
            .isWhitespace(contentBuffer.charAt(contentBuffer.length() - 1)));
    // we must collapse
    // whitespace down to a single space, to mirror the normal
    // HtmlDocumentFormat.
    for(int i = text.offset; i < text.offset + text.length; ++i) {
      if(!Character.isWhitespace(text.ch[i])) {
        contentBuffer.append(text.ch[i]);
        canAppendWS = true;
      }
      else {
        if(canAppendWS) {
          contentBuffer.append(' ');
          canAppendWS = false;
        }
      }
    }
  }

  /**
   * Called when all text between two tags has been processed.
   */
  public void charactersAction() throws XNIException {
    // check whether there are actually any characters to process
    if(!readCharacterStatus) {
      return;
    }
    readCharacterStatus = false;

    if(DEBUG_CHARACTERS) {
      Out.println("charactersAction: offset = " + charactersStartOffset);
    }

    if(contentBuffer.length() == 0) return;

    // Skip ignorable tag content
    if(ignorableTagLevels > 0) {
      if(DEBUG_CHARACTERS) {
        Out.println("  inside ignorable tag, skipping");
      }
      return;
    }

    // the number of whitespace characters trimmed off the front of this
    // chunk of characters
    boolean thisChunkStartsWithWS = Character.isWhitespace(contentBuffer.charAt(0));

    // trim leading whitespace
    if(thisChunkStartsWithWS) {
      contentBuffer.deleteCharAt(0);
    }

    if(contentBuffer.length() == 0) {
      if(DEBUG_CHARACTERS) {
        Out.println("  whitespace only: ignoring");
      }
      // if this chunk starts with whitespace and is whitespace only, then
      // it ended with whitespace too
      previousChunkEndedWithWS = thisChunkStartsWithWS;
      return;
    } // if

    // trim trailing whitespace
    boolean trailingWhitespace = Character.isWhitespace(contentBuffer.charAt(contentBuffer.length() - 1));
    if(trailingWhitespace) {
      contentBuffer.setLength(contentBuffer.length() - 1);
    }

    if(DEBUG_CHARACTERS) {
      Out.println("  content = \"" + contentBuffer + "\"");
    }

    int tmpDocContentSize = tmpDocContent.length();
    boolean incrementStartIndex = false;
    // correct for whitespace.  Since charactersAction never leaves
    // tmpDocContent with a trailing whitespace character, we may
    // need to add space before we append the current chunk to prevent
    // two chunks either side of a tag from running into one.  We need
    // to do this if there is whitespace in the original content on
    // one side or other of the tag (i.e. the previous chunk ended
    // with space or the current chunk starts with space).  Also, if
    // the user's "add space on markup unpack" option is true, we add
    // space anyway so as not to run things like
    // "...foo</td><td>bar..." together into "foobar".
    if(tmpDocContentSize != 0
            && !Character.isWhitespace(tmpDocContent
                    .charAt(tmpDocContentSize - 1))
            && (previousChunkEndedWithWS || thisChunkStartsWithWS || addSpaceOnUnpack)) {
      if(DEBUG_CHARACTERS) {
        Out
                .println(String
                        .format(
                                "  non-whitespace character %1$x (%1$c) found at end of content, adding space",
                                (int)tmpDocContent
                                        .charAt(tmpDocContentSize - 1)));
      }
      tmpDocContent.append(' ');
      incrementStartIndex = true;
    }// End if
    // update the document content

    tmpDocContent.append(contentBuffer);

    // put the repositioning information
    if(reposInfo != null) {
      long actualStartOffset = charactersStartOffset;
      if(thisChunkStartsWithWS) {
        actualStartOffset = fixStartOffsetForWhitespace(actualStartOffset);
      }
      int extractedPos = tmpDocContentSize;
      if(incrementStartIndex) extractedPos++;
      addRepositioningInfo(contentBuffer.length(), (int)actualStartOffset,
              extractedPos);
    } // if

    // calculate the End index for all the elements of the stack
    // the expression is : End index = Current doc length + text length
    Long end = new Long(tmpDocContent.length());

    CustomObject obj = null;
    // Iterate through stack to modify the End index of the existing
    // elements

    java.util.Iterator<CustomObject> anIterator = stack.iterator();
    while(anIterator.hasNext()) {
      // get the object and move to the next one
      obj = anIterator.next();
      if(incrementStartIndex && obj.getStart().equals(obj.getEnd())) {
        obj.setStart(new Long(obj.getStart().longValue() + 1));
      }// End if
      // sets its End index
      obj.setEnd(end);
    }// End while
    
    // remember whether this chunk ended with whitespace for next time
    previousChunkEndedWithWS = trailingWhitespace;
  }

  /**
   * Called when the parser encounters the end of an element.
   */
  @Override
  public void endElement(QName element, Augmentations augs) throws XNIException {
    endElement(element, augs, false);
  }

  /**
   * Called to signal an empty element. This simply synthesizes a
   * startElement followed by an endElement event.
   */
  @Override
  public void emptyElement(QName element, XMLAttributes attributes,
          Augmentations augs) throws XNIException {
    this.startElement(element, attributes, augs);
    this.endElement(element, augs, true);
  }

  /**
   * Called when the parser encounters the end of an HTML element.
   */
  public void endElement(QName element, Augmentations augs,
          boolean wasEmptyElement) throws XNIException {
    charactersAction();

    // localName = localName.toLowerCase();
    if(DEBUG_ELEMENTS) {
      Out.println("endElement: " + element.localpart + " (was "
              + (wasEmptyElement ? "" : "not ") + "empty)");
    }

    // obj is for internal use
    CustomObject obj = null;

    // end of ignorable tag
    if(ignorableTags.contains(element.localpart)) {
      ignorableTagLevels--;
      if(DEBUG_ELEMENTS) {
        Out.println("  end of ignorable tag.  levels = " + ignorableTagLevels);
      }
    } // if

    // If the stack is not empty then we get the object from the stack
    if(!stack.isEmpty()) {
      obj = stack.pop();
      // Before adding it to the colector, we need to check if is an
      // emptyAndSpan one. See CustomObject's isEmptyAndSpan field.
      // We only set isEmptyAndSpan if this endElement was NOT generated
      // from an empty element in the HTML.
      if(obj.getStart().equals(obj.getEnd()) && !wasEmptyElement) {
        // The element had an end tag and its start was equal to its
        // end. Hence it is anEmptyAndSpan one.
        obj.getFM().put("isEmptyAndSpan", "true");
      }// End iff
      // we add it to the colector
      colector.add(obj);
    }// End if

    // If element has text between, then customize its apearance
    if(obj != null && obj.getStart().longValue() != obj.getEnd().longValue())
    // Customize the appearance of the document
      customizeAppearanceOfDocumentWithEndTag(element.localpart);
  }

  /**
   * Called when the parser reaches the end of the document. Here we
   * store the new content and construct the Original markups
   * annotations.
   */
  @Override
  public void endDocument(Augmentations augs) throws XNIException {
    if(DEBUG_GENERAL) {
      Out.println("endDocument");
    }
    CustomObject obj = null;
    // replace the old content with the new one
    doc.setContent(new DocumentContentImpl(tmpDocContent.toString()));

    // If basicAs is null then get the default annotation
    // set from this gate document
    if(basicAS == null)
      basicAS = doc
              .getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

    // sort colector ascending on its id
    Collections.sort(colector);
    // iterate through colector and construct annotations
    while(!colector.isEmpty()) {
      obj = colector.getFirst();
      colector.remove(obj);
      // Construct an annotation from this obj
      try {
        basicAS.add(obj.getStart(), obj.getEnd(), obj.getElemName(), obj
                .getFM());
      }
      catch(InvalidOffsetException e) {
        Err.prln("Error creating an annot :" + obj + " Discarded...");
      }// end try
      // }// end if
    }// while

    // notify the listener about the total amount of elements that
    // has been processed
    fireStatusChangedEvent("Total elements : " + elements);
  }

  /**
   * Non-fatal error, print the stack trace but continue processing.
   */
  @Override
  public void error(String domain, String key, XMLParseException e) {
    e.printStackTrace(Err.getPrintWriter());
  }

  @Override
  public void fatalError(String domain, String key, XMLParseException e)
          throws XNIException {
    throw e;
  }

  // we don't do anything with processing instructions, comments or CDATA
  // markers, but if we encounter them they interrupt the flow of text.  Thus
  // we must call charactersAction so the repositioning info is correctly
  // generated.

  @Override
  public void processingInstruction(String target, XMLString data,
          Augmentations augs) throws XNIException {
    charactersAction();
  }

  @Override
  public void comment(XMLString content,
          Augmentations augs) throws XNIException {
    charactersAction();
  }

  @Override
  public void startCDATA(Augmentations augs) throws XNIException {
    charactersAction();
  }

  @Override
  public void endCDATA(Augmentations augs) throws XNIException {
    charactersAction();
  }


  /**
   * A comparator that compares two RepositioningInfo.PositionInfo
   * records by their originalPosition values. It also supports either
   * or both argument being a Long, in which case the Long value is used
   * directly. This allows you to binarySearch for an offset rather than
   * having to construct a PositionInfo record with the target value.
   */
  private static final Comparator<Object> POSITION_INFO_COMPARATOR = new Comparator<Object>() {
    @Override
    public int compare(Object a, Object b) {
      Long offA = null;
      if(a instanceof Long) {
        offA = (Long)a;
      }
      else if(a instanceof RepositioningInfo.PositionInfo) {
        offA = ((RepositioningInfo.PositionInfo)a).getOriginalPosition();
      }

      Long offB = null;
      if(b instanceof Long) {
        offB = (Long)b;
      }
      else if(b instanceof RepositioningInfo.PositionInfo) {
        offB = ((RepositioningInfo.PositionInfo)a).getOriginalPosition();
      }

      return offA.compareTo(offB);
    }
  };

  /**
   * Correct for whitespace. Given the offset of the start of a block of
   * whitespace in the original content, this method calculates the
   * offset of the first following non-whitespace character. If wsOffset
   * points to the start of a run of whitespace then there will be a
   * PositionInfo record in the ampCodingInfo that represents this run
   * of whitespace, from which we can find the end of the run. If there
   * is no PositionInfo record for this offset then it must point to a
   * single whitespace character, so we simply return wsOffset+1.
   */
  private long fixStartOffsetForWhitespace(long wsOffset) {
    // see whether we have a repositioning record in ampCodingInfo for
    // the whitespace starting at wsOffset
    int wsPosInfoIndex = Collections.binarySearch(ampCodingInfo, wsOffset,
            POSITION_INFO_COMPARATOR);

    // if we don't find a repos record it means that the whitespace
    // really is a single space in the original content
    if(wsPosInfoIndex < 0) {
      return wsOffset + 1;
    }
    // if there is a repos record we move by the record's originalLength
    else {
      return wsOffset
              + ampCodingInfo.get(wsPosInfoIndex).getOriginalLength();
    }
  }

  /**
   * For given content the list with shrink position information is
   * searched and on the corresponding positions the correct
   * repositioning information is calculated and generated.
   */
  public void addRepositioningInfo(int contentLength, int pos, int extractedPos) {
    // wrong way (without correction and analysing)
    // reposInfo.addPositionInfo(pos, contentLength, extractedPos,
    // contentLength);

    RepositioningInfo.PositionInfo pi = null;
    long startPos = pos;
    long correction = 0;
    long substituteStart;
    long remainingLen;
    long offsetInExtracted;

    for(int i = 0; i < ampCodingInfo.size(); ++i) {
      pi = ampCodingInfo.get(i);
      substituteStart = pi.getOriginalPosition();

      if(substituteStart >= startPos) {
        if(substituteStart > pos + contentLength + correction) {
          break; // outside the current text
        } // if

        // should create two repositioning information records
        remainingLen = substituteStart - (startPos + correction);
        offsetInExtracted = startPos - pos;
        if(remainingLen > 0) {
          reposInfo.addPositionInfo(startPos + correction, remainingLen,
                  extractedPos + offsetInExtracted, remainingLen);
        } // if
        // record for shrank text
        reposInfo.addPositionInfo(substituteStart, pi.getOriginalLength(),
                extractedPos + offsetInExtracted + remainingLen, pi
                        .getCurrentLength());
        startPos = startPos + remainingLen + pi.getCurrentLength();
        correction += pi.getOriginalLength() - pi.getCurrentLength();
      } // if
    } // for

    // there is some text remaining for repositioning
    offsetInExtracted = startPos - pos;
    remainingLen = contentLength - offsetInExtracted;
    if(remainingLen > 0) {
      reposInfo.addPositionInfo(startPos + correction, remainingLen,
              extractedPos + offsetInExtracted, remainingLen);
    } // if
  } // addRepositioningInfo

  /**
   * This method analizes the tag t and adds some \n chars and spaces to
   * the tmpDocContent.The reason behind is that we need to have a
   * readable form for the final document. This method modifies the
   * content of tmpDocContent.
   * 
   * @param tagName the Html tag encounted by the HTML parser
   */
  protected void customizeAppearanceOfDocumentWithStartTag(String tagName) {
    boolean modification = false;
    int tmpDocContentSize = tmpDocContent.length();
    if("p".equals(tagName)) {
      if(tmpDocContentSize >= 2
              && '\n' != tmpDocContent.charAt(tmpDocContentSize - 2)) {
        tmpDocContent.append("\n");
        modification = true;
      }
    }// End if
    // if the HTML tag is BR then we add a new line character to the
    // document
    if("br".equals(tagName)) {
      tmpDocContent.append("\n");
      modification = true;
    }// End if

    // only add a newline at the start of a div if there isn't already a
    // newline induced by something else
    if("div".equals(tagName) && tmpDocContentSize > 0
            && tmpDocContent.charAt(tmpDocContentSize - 1) != '\n') {
      tmpDocContent.append("\n");
      modification = true;
    }

    if(modification == true) {
      Long end = new Long(tmpDocContent.length());
      java.util.Iterator<CustomObject> anIterator = stack.iterator();
      while(anIterator.hasNext()) {
        // get the object and move to the next one, and set its end
        // index
        anIterator.next().setEnd(end);
      }// End while
    }// End if
  }// customizeAppearanceOfDocumentWithStartTag

  /**
   * This method analizes the tag t and adds some \n chars and spaces to
   * the tmpDocContent.The reason behind is that we need to have a
   * readable form for the final document. This method modifies the
   * content of tmpDocContent.
   * 
   * @param tagName the Html tag encounted by the HTML parser
   */
  protected void customizeAppearanceOfDocumentWithEndTag(String tagName) {
    boolean modification = false;
    // if the HTML tag is BR then we add a new line character to the
    // document
    if(("p".equals(tagName)) || ("h1".equals(tagName))
            || ("h2".equals(tagName)) || ("h3".equals(tagName))
            || ("h4".equals(tagName)) || ("h5".equals(tagName))
            || ("h6".equals(tagName)) || ("tr".equals(tagName))
            || ("center".equals(tagName)) || ("li".equals(tagName))) {
      tmpDocContent.append("\n");
      modification = true;
    }
    // only add a newline at the end of a div if there isn't already a
    // newline induced by something else
    if("div".equals(tagName) && tmpDocContent.length() > 0
            && tmpDocContent.charAt(tmpDocContent.length() - 1) != '\n') {
      tmpDocContent.append("\n");
      modification = true;
    }

    if("title".equals(tagName)) {
      tmpDocContent.append("\n\n");
      modification = true;
    }// End if

    if(modification == true) {
      Long end = new Long(tmpDocContent.length());
      Iterator<CustomObject> anIterator = stack.iterator();
      while(anIterator.hasNext()) {
        // get the object and move to the next one
        CustomObject obj = anIterator.next();
        // sets its End index
        obj.setEnd(end);
      }// End while
    }// End if
  }// customizeAppearanceOfDocumentWithEndTag

  /** Keep the refference to this structure */
  private RepositioningInfo reposInfo = null;

  /** Keep the refference to this structure */
  private RepositioningInfo ampCodingInfo = null;

  /**
   * Set repositioning information structure refference. If you set this
   * refference to <B>null</B> information wouldn't be collected.
   */
  public void setRepositioningInfo(RepositioningInfo info) {
    reposInfo = info;
  } // setRepositioningInfo

  /** Return current RepositioningInfo object */
  public RepositioningInfo getRepositioningInfo() {
    return reposInfo;
  } // getRepositioningInfo

  /**
   * Set repositioning information structure refference for ampersand
   * coding. If you set this refference to <B>null</B> information
   * wouldn't be used.
   */
  public void setAmpCodingInfo(RepositioningInfo info) {
    ampCodingInfo = info;
  } // setRepositioningInfo

  /** Return current RepositioningInfo object for ampersand coding. */
  public RepositioningInfo getAmpCodingInfo() {
    return ampCodingInfo;
  } // getRepositioningInfo

  /**
   * The HTML tag names (lower case) whose text content should be
   * ignored completely by this handler. Typically this is just script
   * and style tags.
   */
  private Set<String> ignorableTags = null;

  /**
   * Set the set of tag names whose text content will be ignored.
   * 
   * @param newTags a set of lower-case tag names
   */
  public void setIgnorableTags(Set<String> newTags) {
    ignorableTags = newTags;
  }

  /**
   * Get the set of tag names whose content is ignored by this handler.
   */
  public Set<String> getIgnorableTags() {
    return ignorableTags;
  }

  // HtmlDocumentHandler member data

  // counter for the number of levels of ignorable tag we are inside.
  // For example, if we configured "ul" as an ignorable tag name then
  // this variable would have the following values:
  //
  // 0: <p>
  // 0: This is some text
  // 1: <ul>
  // 1: <li>
  // 1: some more text
  // 2: <ul> ...
  // 1: </ul>
  // 1: </li>
  // 0: </ul>
  //
  // this allows us to support nested ignorables
  int ignorableTagLevels = 0;

  // this constant indicates when to fire the status listener
  // this listener will add an overhead and we don't want a big overhead
  // this listener will be callled from ELEMENTS_RATE to ELEMENTS_RATE
  final static int ELEMENTS_RATE = 128;

  /**
   * Array holding the character offset of the start of each line in the
   * document.
   */
  private int[] lineOffsets;

  // the content of the HTML document, without any tag
  // for internal use
  private StringBuilder tmpDocContent = null;

  /**
   * This is used to capture all data within two tags before calling the
   * actual characters method
   */
  private StringBuilder contentBuffer = new StringBuilder("");

  /** This is a variable that shows if characters have been read */
  private boolean readCharacterStatus = false;

  /**
   * The start offset of the current block of character content.
   */
  private int charactersStartOffset;

  // a stack used to remember elements and to keep the order
  private java.util.Stack<CustomObject> stack = null;

  // a gate document
  private gate.Document doc = null;

  // an annotation set used for creating annotation reffering the doc
  private gate.AnnotationSet basicAS;

  // listeners for status report
  protected List<StatusListener> myStatusListeners = new LinkedList<StatusListener>();

  // this reports the the number of elements that have beed processed so
  // far
  private int elements = 0;

  protected int customObjectsId = 0;

  public int getCustomObjectsId() {
    return customObjectsId;
  }

  // we need a colection to retain all the CustomObjects that will be
  // transformed into annotation over the gate document...
  // the transformation will take place inside onDocumentEnd() method
  private LinkedList<CustomObject> colector = null;
  
  /**
   * Initialised from the user config, stores whether to add extra space
   * characters to separate words that would otherwise be run together,
   * e.g. "...foo&lt;/td&gt;&lt;td&gt;bar...".  If true, this becomes
   * "foo bar", if false it is "foobar".
   */
  protected boolean addSpaceOnUnpack = true;
  
  /**
   * During parsing, keeps track of whether the previous chunk of
   * character data ended with a whitespace character.
   */
  protected boolean previousChunkEndedWithWS = false;

  // Inner class
  /**
   * The objects belonging to this class are used inside the stack. This
   * class is for internal needs
   */
  class CustomObject implements Comparable<CustomObject> {

    // constructor
    public CustomObject(String anElemName, FeatureMap aFm, Long aStart,
            Long anEnd) {
      elemName = anElemName;
      fm = aFm;
      start = aStart;
      end = anEnd;
      id = new Long(customObjectsId++);
    }// End CustomObject()

    // Methos implemented as required by Comparable interface
    @Override
    public int compareTo(CustomObject obj) {
      return this.id.compareTo(obj.getId());
    }// compareTo();

    // accesor
    public String getElemName() {
      return elemName;
    }// getElemName()

    public FeatureMap getFM() {
      return fm;
    }// getFM()

    public Long getStart() {
      return start;
    }// getStart()

    public Long getEnd() {
      return end;
    }// getEnd()

    public Long getId() {
      return id;
    }

    // mutator
    public void setElemName(String anElemName) {
      elemName = anElemName;
    }// getElemName()

    public void setFM(FeatureMap aFm) {
      fm = aFm;
    }// setFM();

    public void setStart(Long aStart) {
      start = aStart;
    }// setStart();

    public void setEnd(Long anEnd) {
      end = anEnd;
    }// setEnd();

    // data fields
    private String elemName = null;

    private FeatureMap fm = null;

    private Long start = null;

    private Long end = null;

    private Long id = null;

  } // End inner class CustomObject

  // StatusReporter Implementation

  public void addStatusListener(StatusListener listener) {
    myStatusListeners.add(listener);
  }

  public void removeStatusListener(StatusListener listener) {
    myStatusListeners.remove(listener);
  }

  protected void fireStatusChangedEvent(String text) {
    Iterator<StatusListener> listenersIter = myStatusListeners.iterator();
    while(listenersIter.hasNext())
      listenersIter.next().statusChanged(text);
  }

  // //// Unused methods from XNI interfaces //////

  @Override
  public void doctypeDecl(String arg0, String arg1, String arg2,
          Augmentations arg3) throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("doctypeDecl");
    }
  }

  @Override
  public void endGeneralEntity(String arg0, Augmentations arg1)
          throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("endGeneralEntity");
    }
  }

  @Override
  public XMLDocumentSource getDocumentSource() {
    if(DEBUG_UNUSED) {
      Out.println("getDocumentSource");
    }
    return null;
  }

  @Override
  public void ignorableWhitespace(XMLString arg0, Augmentations arg1)
          throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("ignorableWhitespace: " + arg0);
    }
  }

  @Override
  public void setDocumentSource(XMLDocumentSource arg0) {
    if(DEBUG_UNUSED) {
      Out.println("setDocumentSource");
    }
  }

  @Override
  public void startDocument(XMLLocator arg0, String arg1,
          NamespaceContext arg2, Augmentations arg3) throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("startDocument");
    }
  }

  @Override
  public void startGeneralEntity(String arg0, XMLResourceIdentifier arg1,
          String arg2, Augmentations arg3) throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("startGeneralEntity");
    }
  }

  @Override
  public void textDecl(String arg0, String arg1, Augmentations arg2)
          throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("textDecl");
    }
  }

  @Override
  public void xmlDecl(String arg0, String arg1, String arg2, Augmentations arg3)
          throws XNIException {
    if(DEBUG_UNUSED) {
      Out.println("xmlDecl");
    }
  }

  @Override
  public void warning(String arg0, String arg1, XMLParseException arg2)
          throws XNIException {
    if(DEBUG_GENERAL) {
      Out.println("warning:");
      arg2.printStackTrace(Err.getPrintWriter());
    }
  }

}
