/*
 *  NekoHtmlDocumentFormat.java
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
 *  $Id: NekoHtmlDocumentFormat.java 17864 2014-04-18 07:12:27Z markagreenwood $
 */

package gate.corpora;

import gate.Document;
import gate.GateConstants;
import gate.Resource;
import gate.TextualDocument;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.event.StatusListener;
import gate.html.NekoHtmlDocumentHandler;
import gate.util.DocumentFormatException;
import gate.util.Out;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLConnection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.xerces.xni.parser.XMLInputSource;
import org.cyberneko.html.HTMLConfiguration;

/**
 * <p>
 * DocumentFormat that uses Andy Clark's <a
 * href="http://people.apache.org/~andyc/neko/doc/html/">NekoHTML</a>
 * parser to parse HTML documents. It tries to render HTML in a similar
 * way to a web browser, i.e. whitespace is normalized, paragraphs are
 * separated by a blank line, etc. By default the text content of style
 * and script tags is ignored completely, though the set of tags treated
 * in this way is configurable via a CREOLE parameter.
 * </p>
 */
@CreoleResource(name = "GATE HTML Document Format", isPrivate = true,
    autoinstances = {@AutoInstance(hidden = true)})
public class NekoHtmlDocumentFormat extends TextualDocumentFormat {
 
  private static final long serialVersionUID = -3163147687966075651L;
 
  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Default construction */
  public NekoHtmlDocumentFormat() {
    super();
  }

  /**
   * The set of tags whose text content is to be ignored when parsing.
   */
  private Set<String> ignorableTags = null;

  @CreoleParameter(comment = "HTML tags whose text content should be ignored",
      defaultValue = "script;style")
  public void setIgnorableTags(Set<String> newTags) {
    this.ignorableTags = newTags;
  }

  public Set<String> getIgnorableTags() {
    return ignorableTags;
  }

  /**
   * We support repositioning info for HTML files.
   */
  @Override
  public Boolean supportsRepositioning() {
    return Boolean.TRUE;
  }

  /**
   * Old-style unpackMarkup, without repositioning info.
   */
  @Override
  public void unpackMarkup(Document doc) throws DocumentFormatException {
    unpackMarkup(doc, null, null);
  }

  /**
   * Unpack the markup in the document. This converts markup from the
   * native format into annotations in GATE format. If the document was
   * created from a String, then is recomandable to set the doc's
   * sourceUrl to <b>null</b>. So, if the document has a valid URL,
   * then the parser will try to parse the XML document pointed by the
   * URL.If the URL is not valid, or is null, then the doc's content
   * will be parsed. If the doc's content is not a valid XML then the
   * parser might crash.
   *
   * @param doc The gate document you want to parse. If
   *          <code>doc.getSourceUrl()</code> returns <b>null</b>
   *          then the content of doc will be parsed. Using a URL is
   *          recomended because the parser will report errors corectlly
   *          if the document is not well formed.
   */
  @Override
  public void unpackMarkup(Document doc, RepositioningInfo repInfo,
          RepositioningInfo ampCodingInfo) throws DocumentFormatException {
    if((doc == null)
            || (doc.getSourceUrl() == null && doc.getContent() == null)) {

      throw new DocumentFormatException(
              "GATE document is null or no content found. Nothing to parse!");
    }// End if

    // Create a status listener
    StatusListener statusListener = new StatusListener() {
      @Override
      public void statusChanged(String text) {
        // This is implemented in DocumentFormat.java and inherited here
        fireStatusChanged(text);
      }
    };

    boolean docHasContentButNoValidURL = hasContentButNoValidUrl(doc);

    NekoHtmlDocumentHandler handler = null;
    try {
      org.cyberneko.html.HTMLConfiguration parser = new HTMLConfiguration();

      // convert element and attribute names to lower case
      parser.setProperty("http://cyberneko.org/html/properties/names/elems",
              "lower");
      parser.setProperty("http://cyberneko.org/html/properties/names/attrs",
              "lower");
      // make parser augment infoset with location information
      parser.setFeature(NekoHtmlDocumentHandler.AUGMENTATIONS, true);

      // Create a new Xml document handler
      handler = new NekoHtmlDocumentHandler(doc, null, ignorableTags);
      // Register a status listener with it
      handler.addStatusListener(statusListener);
      // set repositioning object
      handler.setRepositioningInfo(repInfo);
      // set the object with ampersand coding positions
      handler.setAmpCodingInfo(ampCodingInfo);
      // construct the list of offsets for each line of the document
      int[] lineOffsets = buildLineOffsets(doc.getContent().toString());
      handler.setLineOffsets(lineOffsets);

      // set the handlers
      parser.setDocumentHandler(handler);
      parser.setErrorHandler(handler);

      // Parse the XML Document with the appropriate encoding
      XMLInputSource is;

      if(docHasContentButNoValidURL) {
        // no URL, so parse from string
        is =
                new XMLInputSource(null, null, null, new StringReader(doc
                        .getContent().toString()), null);
      }
      else if(doc instanceof TextualDocument) {
        // textual document - load with user specified encoding
        String docEncoding = ((TextualDocument)doc).getEncoding();
        // XML, so no BOM stripping.
        
        URLConnection conn = doc.getSourceUrl().openConnection();
        InputStream uStream = conn.getInputStream();
                
        if ("gzip".equals(conn.getContentEncoding())) {
          uStream = new GZIPInputStream(uStream);
        }
        
        Reader docReader =
                new InputStreamReader(uStream,
                        docEncoding);
        is =
                new XMLInputSource(null, doc.getSourceUrl().toString(), doc
                        .getSourceUrl().toString(), docReader, docEncoding);

        // since we control the encoding, tell the parser to ignore any
        // meta http-equiv hints
        parser
                .setFeature(
                        "http://cyberneko.org/html/features/scanner/ignore-specified-charset",
                        true);
      }
      else {
        // let the parser decide the encoding
        is =
                new XMLInputSource(null, doc.getSourceUrl().toString(), doc
                        .getSourceUrl().toString());
      }

      /* The following line can forward an
       * ArrayIndexOutOfBoundsException from
       * org.cyberneko.html.HTMLConfiguration.parse and crash GATE.    */
      parser.parse(is);
      // Angel - end
      ((DocumentImpl)doc).setNextAnnotationId(handler.getCustomObjectsId());
    }

    /* Handle IOException specially.      */
    catch(IOException e) {
      throw new DocumentFormatException("I/O exception for "
              + doc.getSourceUrl().toString(), e);
    }

    /* Handle XNIException and ArrayIndexOutOfBoundsException:
     * flag the parsing error and keep going.     */
    catch(Exception e) {
      doc.getFeatures().put("parsingError", Boolean.TRUE);

      Boolean bThrow =
              (Boolean)doc.getFeatures().get(
                      GateConstants.THROWEX_FORMAT_PROPERTY_NAME);

      if(bThrow != null && bThrow.booleanValue()) {
        // the next line is commented to avoid Document creation fail on
        // error
        throw new DocumentFormatException(e);
      }
      else {
        Out.println("Warning: Document remains unparsed. \n"
                + "\n  Stack Dump: ");
        e.printStackTrace(Out.getPrintWriter());
      } // if

    }
    finally {
      if(handler != null) handler.removeStatusListener(statusListener);
    }// End if else try

  }

  /**
   * Pattern that matches the beginning of every line in a multi-line
   * string. The regular expression engine handles the different types
   * of newline characters (\n, \r\n or \r) automatically.
   */
  private static Pattern afterNewlinePattern =
          Pattern.compile("^", Pattern.MULTILINE);

  /**
   * Build an array giving the starting character offset of each line in
   * the document. The HTML parser only reports event positions as line
   * and column numbers, so we need this information to be able to
   * correctly infer the repositioning information.
   */
  private int[] buildLineOffsets(String docContent) {
    Matcher m = afterNewlinePattern.matcher(docContent);
    // we have to scan the text twice, first to determine how many lines
    // there are (i.e. how long the array needs to be)...
    int numMatches = 0;
    while(m.find()) {
      if(DEBUG) {
        System.out.println("found line starting at offset " + m.start());
      }
      numMatches++;
    }

    int[] lineOffsets = new int[numMatches];

    // ... and then again to populate the array with values.
    m.reset();
    for(int i = 0; i < lineOffsets.length; i++) {
      m.find();
      lineOffsets[i] = m.start();
    }

    return lineOffsets;
  }

  /** Initialise this resource, and return it. */
  @Override
  public Resource init() throws ResourceInstantiationException {
    // Register HTML mime type
    MimeType mime = new MimeType("text", "html");
    // Register the class handler for this mime type
    mimeString2ClassHandlerMap.put(mime.getType() + "/" + mime.getSubtype(),
            this);
    // Register the mime type with mine string
    mimeString2mimeTypeMap.put(mime.getType() + "/" + mime.getSubtype(), mime);
    // sometimes XHTML file appear as application/xhtml+xml
    mimeString2mimeTypeMap.put("application/xhtml+xml", mime);
    // Register file sufixes for this mime type
    suffixes2mimeTypeMap.put("html", mime);
    suffixes2mimeTypeMap.put("htm", mime);
    // Register magic numbers for this mime type
    magic2mimeTypeMap.put("<html", mime);
    // Set the mimeType for this language resource
    setMimeType(mime);
    return this;
  }// init()

}// class XmlDocumentFormat
