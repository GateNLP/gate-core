/*
 *  DocumentFormat.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 25/May/2000
 *
 *  $Id: DocumentFormat.java 19756 2016-11-19 01:55:44Z markagreenwood $
 */

package gate;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

import gate.corpora.MimeType;
import gate.corpora.RepositioningInfo;
import gate.creole.AbstractLanguageResource;
import gate.event.StatusListener;
import gate.util.BomStrippingInputStreamReader;
import gate.util.DocumentFormatException;

/** The format of Documents. Subclasses of DocumentFormat know about
  * particular MIME types and how to unpack the information in any
  * markup or formatting they contain into GATE annotations. Each MIME
  * type has its own subclass of DocumentFormat, e.g. XmlDocumentFormat,
  * RtfDocumentFormat, MpegDocumentFormat. These classes register themselves
  * with a static index residing here when they are constructed. Static
  * getDocumentFormat methods can then be used to get the appropriate
  * format class for a particular document.
  */
public abstract class DocumentFormat
extends AbstractLanguageResource {
  
  private static final long serialVersionUID = 4147880563349143923L;

  /** The MIME type of this format. */
  private MimeType mimeType = null;

  /** Map of MimeTypeString to ClassHandler class. This is used to find the
    * language resource that deals with the specific Document format
    */
  protected static final Map<String, DocumentFormat>
          mimeString2ClassHandlerMap = new HashMap<String, DocumentFormat>();
  /** Map of MimeType to DocumentFormat Class. This is used to find the
    * DocumentFormat subclass that deals with a particular MIME type.
    */
  protected static final Map<String, MimeType>
          mimeString2mimeTypeMap = new HashMap<String, MimeType>();

  /** Map of Set of file suffixes to MimeType. This is used to figure
    * out what MIME type a document is from its file name.
    */
  protected static final Map<String, MimeType>
          suffixes2mimeTypeMap = new HashMap<String, MimeType>();

  /** Map of Set of magic numbers to MimeType. This is used to guess the
    * MIME type of a document, when we don't have any other clues.
    */
  protected static final Map<String, MimeType>
          magic2mimeTypeMap = new HashMap<String, MimeType>();

  /** Map of markup elements to annotation types. If it is null, the
    * unpackMarkup() method will convert all markup, using the element names
    * for annotation types. If it is non-null, only those elements specified
    * here will be converted.
    */
  protected Map<String,String> markupElementsMap = null;

  /** This map is used inside uppackMarkup() method...
    * When an element from the map is encounted, The corresponding string
    * element is added to the document content
    */
  protected Map<String,String> element2StringMap = null;

  /** The features of this resource */
  private FeatureMap features = null;

  /** Default construction */
  public DocumentFormat() {}

  /** listeners for status report */
  private transient Vector<StatusListener> statusListeners;

  /** Flag for enable/disable collecting of repositioning information */
  private Boolean shouldCollectRepositioning = Boolean.FALSE;

  /** If the document format could collect repositioning information
   *  during the unpack phase this method will return <B>true</B>.
   *  <BR>
   *  You should override this method in the child class of the defined
   *  document format if it could collect the repositioning information.
   */
  public Boolean supportsRepositioning() {
    return Boolean.FALSE;
  } // supportsRepositioning

  public void setShouldCollectRepositioning(Boolean b) {
    if(supportsRepositioning().booleanValue() && b.booleanValue()) {
      shouldCollectRepositioning = b;
    }
    else {
      shouldCollectRepositioning = Boolean.FALSE;
    } // if
  } // setShouldCollectRepositioning

  public Boolean getShouldCollectRepositioning() {
    return shouldCollectRepositioning;
  } //

  /** Unpack the markup in the document. This converts markup from the
    * native format (e.g. XML, RTF) into annotations in GATE format.
    * Uses the markupElementsMap to determine which elements to convert, and
    * what annotation type names to use.
    */
  abstract public void unpackMarkup(Document doc)
                                      throws DocumentFormatException;

  abstract public void unpackMarkup(Document doc, RepositioningInfo repInfo,
                                        RepositioningInfo ampCodingInfo)
                                      throws DocumentFormatException;
  /** Unpack the markup in the document. This method calls unpackMarkup on the
    * GATE document, but after it saves its content as a feature attached to
    * the document. This method is useful if one wants to save the content
    * of the document being unpacked. After the markups have been unpacked,
    * the content of the document will be replaced with a new one containing
    * the text between markups.
    *
    * @param doc the document that will be unpacked
    * @param originalContentFeatureType the name of the feature that will hold
    * the document's content.
    */
  public void unpackMarkup( Document doc,
                            String  originalContentFeatureType )
                                              throws DocumentFormatException{
     FeatureMap fm = doc.getFeatures();
     if (fm == null) fm = Factory.newFeatureMap();
     fm.put(originalContentFeatureType, doc.getContent().toString());
     doc.setFeatures(fm);
     unpackMarkup(doc);
  }// unpackMarkup();

  /**
    * Returns a MimeType having as input a fileSufix.
    * If the file sufix is <b>null</b> or not recognised then,
    * <b>null</b> will be returned.
    * @param fileSufix The file sufix associated with a recognisabe mime type.
    * @return The MimeType associated with this file suffix.
    */
  static private MimeType  getMimeType(String fileSufix){
    // Get a mimeType string associated with this fileSuffix
    // Eg: for html returns  MimeType("text/html"), for xml returns
    // MimeType("text/xml")
    if(fileSufix == null) return null;
    return  suffixes2mimeTypeMap.get(fileSufix.toLowerCase());
  }//getMimeType
  
  public static Set<String> getSupportedMimeTypes() {
    return Collections.unmodifiableSet(mimeString2mimeTypeMap.keySet());
  }
  
  /**
    * Returns a MymeType having as input a URL object. If the MimeType wasn't
    * recognized it returns <b>null</b>.
    * @param url The URL object from which the MimeType will be extracted
    * @return A MimeType object for that URL, or <b>null</b> if the Mime Type is
    * unknown.
    */
  static private MimeType  getMimeType(URL url) {
    String mimeTypeString = null;
    String charsetFromWebServer = null;
    String contentType = null;
    InputStream is = null;
    MimeType mimeTypeFromWebServer = null;
    MimeType mimeTypeFromFileSuffix = null;
    MimeType mimeTypeFromMagicNumbers = null;

    if (url == null)
      return null;
    // Ask the web server for the content type
    // We expect to get contentType something like this:
    // "text/html; charset=iso-8859-1"
    // Charset is optional

    try {
    try{
      URLConnection urlconn = url.openConnection();
      is = urlconn.getInputStream();
      contentType = urlconn.getContentType();
    } catch (IOException e){
      // Failed to get the content type with te Web server.
      // Let's try some other methods like FileSuffix or magic numbers.
    }
    // If a content Type was returned by the server, try to get the mime Type
    // string
    // If contentType is something like this:"text/html; charset=iso-8859-1"
    // try to get content Type string (text/html)
    if (contentType != null){
      StringTokenizer st = new StringTokenizer(contentType, ";");
      // We assume that the first token is the mime type string...
      // If this doesn't happen then BAD LUCK :(( ...
      if (st.hasMoreTokens())
        mimeTypeString     = st.nextToken().toLowerCase();
      // The next token it should be the CharSet
      if (st.hasMoreTokens())
        charsetFromWebServer = st.nextToken().toLowerCase();
      if (charsetFromWebServer != null){
        //We have something like : "charset=iso-8859-1" and let's extract the
        // encoding.
        st = new StringTokenizer(charsetFromWebServer, "=");
        // Don't need this anymore
        charsetFromWebServer = null;
        // Discarding the first token which is : "charset"
        if (st.hasMoreTokens())
          st.nextToken();
        // Get the encoding : "ISO-8859-1"
        if (st.hasMoreTokens())
          charsetFromWebServer = st.nextToken().toUpperCase();
      } // End if
    }// end if
    // Return the corresponding MimeType with WebServer from the associated MAP
    mimeTypeFromWebServer = mimeString2mimeTypeMap.get(mimeTypeString);
    // Let's try a file suffix detection
    // mimeTypeFromFileSuffix = getMimeType(getFileSuffix(url));    
    for(String suffix : getFileSuffixes(url)) {
      mimeTypeFromFileSuffix = getMimeType(suffix);
      if(mimeTypeFromFileSuffix != null) break;
    }

    // Let's perform a magic numbers guess..
    mimeTypeFromMagicNumbers = guessTypeUsingMagicNumbers(is,
                                                    charsetFromWebServer);
    }
    finally {
      IOUtils.closeQuietly(is); //null safe
    }
    //All those types enter into a deciding system
    return decideBetweenThreeMimeTypes( mimeTypeFromWebServer,
                                        mimeTypeFromFileSuffix,
                                        mimeTypeFromMagicNumbers);
  }//getMimeType

  /**
    * This method decides what mimeType is in majority
    * @param aMimeTypeFromWebServer a MimeType
    * @param aMimeTypeFromFileSuffix a MimeType
    * @param aMimeTypeFromMagicNumbers a MimeType
    * @return the MimeType which occurs most. If all are null, then returns
    * <b>null</b>
    */
  protected static MimeType decideBetweenThreeMimeTypes(
                                    MimeType aMimeTypeFromWebServer,
                                    MimeType aMimeTypeFromFileSuffix,
                                    MimeType aMimeTypeFromMagicNumbers){
    
    // First a voting system
    if (areEqual(aMimeTypeFromWebServer,aMimeTypeFromFileSuffix))
      return aMimeTypeFromFileSuffix;
    if (areEqual(aMimeTypeFromFileSuffix,aMimeTypeFromMagicNumbers))
      return aMimeTypeFromFileSuffix;
    if (areEqual(aMimeTypeFromWebServer,aMimeTypeFromMagicNumbers))
      return aMimeTypeFromWebServer;

    // 1 is the highest priority
    if (aMimeTypeFromFileSuffix != null)
      aMimeTypeFromFileSuffix.addParameter("Priority","1");
    // 2 is the second priority
    if (aMimeTypeFromWebServer != null)
      aMimeTypeFromWebServer.addParameter("Priority","2");
    // 3 is the third priority
    if (aMimeTypeFromMagicNumbers != null)
      aMimeTypeFromMagicNumbers.addParameter("Priority","3");

    return decideBetweenTwoMimeTypes(
                             decideBetweenTwoMimeTypes(aMimeTypeFromWebServer,
                                                       aMimeTypeFromFileSuffix),
                             aMimeTypeFromMagicNumbers);

  }// decideBetweenThreeMimeTypes

  /** Decide between two mimeTypes. The decistion is made on "Priority"
    * parameter set into decideBetweenThreeMimeTypes method. If both mimeTypes
    * doesn't have "Priority" paramether set, it will return one on them.
    * @param aMimeType a MimeType object with "Prority" parameter set
    * @param anotherMimeType a MimeType object with "Prority" parameter set
    * @return One of the two mime types.
    */
  protected static MimeType decideBetweenTwoMimeTypes( MimeType aMimeType,
                                                MimeType anotherMimeType){
    if (aMimeType == null) return anotherMimeType;
    if (anotherMimeType == null) return aMimeType;

    int priority1 = 0;
    int priority2 = 0;
    // Both of them are not null
    if (aMimeType.hasParameter("Priority"))
      try{
        priority1 =
              Integer.parseInt(aMimeType.getParameterValue("Priority"));
      }catch (NumberFormatException e){
        return anotherMimeType;
      }
    if (anotherMimeType.hasParameter("Priority"))
      try{
        priority2 =
          Integer.parseInt(anotherMimeType.getParameterValue("Priority"));
      }catch (NumberFormatException e){
        return aMimeType;
      }

    // The lower the number, the highest the priority
    if (priority1 <= priority2)
      return aMimeType;
    else
      return anotherMimeType;
  }// decideBetweenTwoMimeTypes

  /**
    * Tests if two MimeType objects are equal.
    * @return true only if boths MimeType objects are different than <b>null</b>
    * and their Types and Subtypes are equals. The method is case sensitive.
    */
  protected static boolean areEqual( MimeType aMimeType,
                                     MimeType anotherMimeType){
    if (aMimeType == null || anotherMimeType == null)
      return false;

    if ( aMimeType.getType().equals(anotherMimeType.getType()) &&
         aMimeType.getSubtype().equals(anotherMimeType.getSubtype())
       ) return true;
    else
      return false;
  }// are Equal

  /**
    * This method tries to guess the mime Type using some magic numbers.
    * @param aInputStream a InputStream which has to be transformed into a
    *        InputStreamReader
    * @param anEncoding the encoding. If is null or unknown then a
    * InputStreamReader with default encodings will be created.
    * @return the mime type associated with magic numbers
    */
  protected static MimeType guessTypeUsingMagicNumbers(InputStream aInputStream,
                                                            String anEncoding){

    if (aInputStream == null) return null;
    Reader reader = null;
    if (anEncoding != null)
      try{
        reader = new BomStrippingInputStreamReader(aInputStream, anEncoding);
      } catch (UnsupportedEncodingException e){
        reader = null;
      }
    if (reader == null)
      // Create a reader with the default encoding system
      reader = new BomStrippingInputStreamReader(aInputStream);

    // We have a input stream reader
    return runMagicNumbers(reader);
  }//guessTypeUsingMagicNumbers

  /** Performs magic over Gate Document */
  protected static MimeType runMagicNumbers(Reader aReader) {
    // No reader, nothing to detect
    if( aReader == null) return null;

    // Prepare to run the magic stuff
    String strBuffer = null;
    int bufferSize = 2048;
    int charReads = 0;
    char[] cbuf = new char[bufferSize];

    try {
      charReads = aReader.read(cbuf,0,bufferSize);
    } catch (IOException e){
      return null;
    }// End try

    if (charReads == -1)
      // the document is empty
      return null;

    // Create a string form the buffer and perform some search on it.
    strBuffer = new String(cbuf,0,charReads);

    // If this fails then surrender
    return getTypeFromContent(strBuffer);
  }// runMagicNumbers

  private static MimeType getTypeFromContent(String aContent){

    // change case to cover more variants
    aContent = aContent.toLowerCase();

    // the mime type we have detected (null to start with)
    MimeType detectedMimeType = null;

    // the offset of the first match now we use a "first wins" priority
    int firstOffset = Integer.MAX_VALUE;
    
    MimeType xmlMime = getMimeType("xml");    

    // Run the magic numbers test
    for(Map.Entry<String, MimeType> kv : magic2mimeTypeMap.entrySet()) {
      // the magic code we are looking for
      String magic = kv.getKey().toLowerCase();

      // the offset of this code in the content
      int offset = aContent.indexOf(magic.toLowerCase());
      if(offset != -1 && (offset < firstOffset || (!kv.getValue().equals(xmlMime) && detectedMimeType.equals(xmlMime)))) {
        // if the magic code exists in the doc and appears before any others
        // than use that mime type
        detectedMimeType = kv.getValue();
        firstOffset = offset;
      }
    }

    // return the mime type (null if we failed)
    return detectedMimeType;
  }

  /**
    * Return the fileSuffix or null if the url doesn't have a file suffix
    * If the url is null then the file suffix will be null also
    */
  @SuppressWarnings("unused")
  private static String getFileSuffix(URL url){
    String fileName = null;
    String fileSuffix = null;

    // GIGO test  (garbage in garbage out)
    if (url != null){
      // get the file name from the URL
      fileName = url.getFile();

      // tokenize this file name with "." as separator...
      // the last token will be the file suffix
      StringTokenizer st = new StringTokenizer(fileName,".");

      // fileSuffix is the last token
      while (st.hasMoreTokens())
        fileSuffix = st.nextToken();
      // here fileSuffix is the last token
    } // End if
    return fileSuffix;
  }//getFileSufix

  /**
   * Given a URL, this method returns all the 'file extensions' for the file
   * part of the URL. For this purposes, a 'file extension' is any sequence of
   * .-separated tokens (such as .gate.xml.gz). The order the extensions are 
   * returned in is from the most specific (longest) to the most generic 
   * (shortest) one, e.g. [.gate.xml.gz, .xml.gz, .gz]. 
   */
  private static List<String> getFileSuffixes(URL url){
    List<String> res = new LinkedList<String>();
    if (url != null){
      // get the file name from the URL
      String fileName = url.getPath();
      int pos = fileName.lastIndexOf('/');
      if(pos  > 0) fileName = fileName.substring(pos);
      pos = fileName.indexOf('.', 1);
      while(pos > 0 && pos < fileName.length() - 1) {
        res.add(fileName.substring(pos + 1));
        pos = fileName.indexOf('.', pos + 1);
      }
    }
    return res;
  }
  
  
  /**
    * Find a DocumentFormat implementation that deals with a particular
    * MIME type, given that type.
    * @param  aGateDocument this document will receive as a feature
    *                      the associated Mime Type. The name of the feature is
    *                      MimeType and its value is in the format type/subtype
    * @param  mimeType the mime type that is given as input
    */
  static public DocumentFormat getDocumentFormat(gate.Document aGateDocument,
                                                            MimeType mimeType){
    FeatureMap      aFeatureMap    = null;
    if(mimeType == null) {
      String content = aGateDocument.getContent().toString();
      // reduce size for better performance
      if(content.length() > 2048) content = content.substring(0, 2048);
      mimeType = getTypeFromContent( content );
    }

    if (mimeType != null){
      // If the Gate Document doesn't have a feature map atached then
      // We will create and set one.
      if(aGateDocument.getFeatures() == null){
            aFeatureMap = Factory.newFeatureMap();
            aGateDocument.setFeatures(aFeatureMap);
      }// end if
      aGateDocument.getFeatures().put("MimeType",mimeType.getType() + "/" +
                                          mimeType.getSubtype());

      return mimeString2ClassHandlerMap.get(mimeType.getType()
                                               + "/" + mimeType.getSubtype());
    }// end If
    return null;
  } // getDocumentFormat(aGateDocument, MimeType)

  /**
    * Find a DocumentFormat implementation that deals with a particular
    * MIME type, given the file suffix (e.g. ".txt") that the document came
    * from.
    * @param  aGateDocument this document will receive as a feature
    *                     the associated Mime Type. The name of the feature is
    *                     MimeType and its value is in the format type/subtype
    * @param  fileSuffix the file suffix that is given as input
    */
  static public DocumentFormat getDocumentFormat(gate.Document aGateDocument,
                                                            String fileSuffix) {
    return getDocumentFormat(aGateDocument, getMimeType(fileSuffix));
  } // getDocumentFormat(String)
  
  /**
   * Find the DocumentFormat implementation that deals with the given
   * MIME type.
   * 
   * @param mimeType the MIME type you want the DocumentFormat for
   * @return the DocumentFormat associated with the MIME type or null if
   *         the MIME type does not have a registered DocumentFormat
   */
  public static DocumentFormat getDocumentFormat(MimeType mimeType) {
    if(mimeType != null) {
      return mimeString2ClassHandlerMap.get(mimeType.getType() + "/"
            + mimeType.getSubtype());
    } else {
      return null;
    }
  }

  /**
    * Find a DocumentFormat implementation that deals with a particular
    * MIME type, given the URL of the Document. If it is an HTTP URL, we
    * can ask the web server. If it has a recognised file extension, we
    * can use that. Otherwise we need to use a map of magic numbers
    * to MIME types to guess the type, and then look up the format using the
    * type.
    * @param  aGateDocument this document will receive as a feature
    *                      the associated Mime Type. The name of the feature is
    *                      MimeType and its value is in the format type/subtype
    * @param  url  the URL that is given as input
    */
  static public DocumentFormat getDocumentFormat(gate.Document aGateDocument,
                                                                      URL url) {
    return getDocumentFormat(aGateDocument, getMimeType(url));
  } // getDocumentFormat(URL)
  
  /** Get the feature set */
  @Override
  public FeatureMap getFeatures() { return features; }

   /** Get the markup elements map */
  public Map<String,String> getMarkupElementsMap() { return markupElementsMap; }

   /** Get the element 2 string map */
  public Map<String,String> getElement2StringMap() { return element2StringMap; }

  /** Set the markup elements map */
  public void setMarkupElementsMap(Map<String,String> markupElementsMap) {
   this.markupElementsMap = markupElementsMap;
  }

  /** Set the element 2 string map */
  public void setElement2StringMap(Map<String,String> anElement2StringMap) {
   element2StringMap = anElement2StringMap;
  }

  /** Set the features map*/
  @Override
  public void setFeatures(FeatureMap features){this.features = features;}

  /** Set the mime type*/

  public void setMimeType(MimeType aMimeType){mimeType = aMimeType;}
  /** Gets the mime Type*/
  public MimeType getMimeType(){return mimeType;}


  /**
   * Utility method to get a {@link MimeType} given the type string.
   */
  public static MimeType getMimeTypeForString(String typeString) {
    return mimeString2mimeTypeMap.get(typeString.split(";",2)[0].trim());
  }

  /**
   * Utility method to get the set of all file suffixes that are registered
   * with this class.
   */
  public static Set<String> getSupportedFileSuffixes() {
    return Collections.unmodifiableSet(suffixes2mimeTypeMap.keySet());
  }

  /**
   * Utility function to determine if reading from the URL will be done by
   * a registered DocumentFormat.
   *  
   * This tries to find out if there is a registered DocumentFormat for 
   * the mime type string or document URL. If yes, it is checked if that 
   * document format implements DirectLoadingDocumentFormat. If yes, returns
   * true, otherwise returns false. 
   * 
   * If both the mime type and docUrl are null or empty, the default behavior
   * of false is returned. 
   * 
   * @param mimeTypeStr the mime type string parameter for the Document
   * @param docUrl the sourceUrl parameter for the Document
   * @return true if the URL will be read directly by a document format.
   */
  public static boolean willReadFromUrl(String mimeTypeStr, URL docUrl) {
    // figure out if we have a document format registered for the 
    // mime type. If yes, check if the document format implemts 
    // BinaryDocumentFormat. If yes, ask the document format if we 
    // should create the content from the URL here or leave that for
    // the DocumentFormat to do later. 
    DocumentFormat docFormat = null;
    // If a mimeTypeStr is specified, try to get the document format 
    // registered for it
    MimeType theType = null;
    if (mimeTypeStr != null && mimeTypeStr.length() > 0) {
      theType = DocumentFormat.getMimeTypeForString(mimeTypeStr);
      if (theType != null) {
        docFormat = DocumentFormat.getDocumentFormat(theType);
      }
    }
    // If we could not get the docFormat from the mimeTypeStr, lets see
    // if we can get it from the url
    if (docFormat == null && docUrl != null) {
      // if we do not have a mime type already, try to find one from the
      // URL suffixes. Only if this fails, use the methods that actually
      // open the URL to to get a content type or magic number
      for (String suffix : getFileSuffixes(docUrl)) {
        theType = getMimeType(suffix);
        if (theType != null) {
          break;
        }
      }
      if(theType != null) {
        docFormat = DocumentFormat.getDocumentFormat(theType);
      }
      if (docFormat != null) {
        theType = DocumentFormat.getMimeType(docUrl);
        docFormat = DocumentFormat.getDocumentFormat(theType);
      }
    }
    
    return (docFormat != null && docFormat instanceof DirectLoadingDocumentFormat);
  }

  
  //StatusReporter Implementation


  public synchronized void removeStatusListener(StatusListener l) {
    if (statusListeners != null && statusListeners.contains(l)) {
      @SuppressWarnings("unchecked")
      Vector<StatusListener> v = (Vector<StatusListener>) statusListeners.clone();
      v.removeElement(l);
      statusListeners = v;
    }
  }
  public synchronized void addStatusListener(StatusListener l) {
    @SuppressWarnings("unchecked")
    Vector<StatusListener> v = statusListeners == null ? new Vector<StatusListener>(2) : (Vector<StatusListener>) statusListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      statusListeners = v;
    }
  }
  protected void fireStatusChanged(String e) {
    if (statusListeners != null) {
      
      int count = statusListeners.size();
      for (int i = 0; i < count; i++) {
        statusListeners.elementAt(i).statusChanged(e);
      }
    }
  }

} // class DocumentFormat
