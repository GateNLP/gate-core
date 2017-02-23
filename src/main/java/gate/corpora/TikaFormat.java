package gate.corpora;

import gate.Document;
import gate.DocumentFormat;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;
import gate.event.StatusListener;
import gate.util.DocumentFormatException;
import gate.xml.XmlDocumentHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Office;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;

@CreoleResource(name = "Apache Tika Document Format", isPrivate = true, autoinstances = {@AutoInstance(hidden = true)})
public class TikaFormat extends DocumentFormat {

  private static final long serialVersionUID = 1L;

  private static final Logger log = Logger.getLogger(TikaFormat.class);

  @Override
  public Resource init() throws ResourceInstantiationException {		
    super.init();
    setMimeType(new MimeType("application","tika"));
    assignMime(getMimeType());
    assignMime(new MimeType("application","pdf"), "pdf");
    assignMime(new MimeType("application","msword"), "doc");
    assignMime(new MimeType("application","vnd.ms-powerpoint"), "ppt");
    assignMime(new MimeType("application","vnd.ms-excel"), "xls");
    assignMime(new MimeType("application","vnd.openxmlformats-officedocument.wordprocessingml.document"), "docx");   
    assignMime(new MimeType("application","vnd.openxmlformats-officedocument.presentationml.presentation"), "pptx");
    assignMime(new MimeType("application","vnd.openxmlformats-officedocument.spreadsheetml.sheet"), "xlsx");       
    assignMime(new MimeType("application", "vnd.oasis.opendocument.text"), "odt");
    assignMime(new MimeType("application", "vnd.oasis.opendocument.presentation"), "odp");
    assignMime(new MimeType("application", "vnd.oasis.opendocument.spreadsheet"), "ods");
    assignMime(new MimeType("application", "rtf"), "rtf");
    
    //There are bugs in Tika related to ePub as of 0.7
    //assignMime(new MimeType("application", "epub+zip"), "epub");
    return this;
  }

  private void assignMime(MimeType mime, String... exts) {
    String mimeString = mime.getType()+ "/" + mime.getSubtype();
    mimeString2ClassHandlerMap.put(mimeString, this);
    mimeString2mimeTypeMap.put(mimeString, mime);
    for (String ext : exts)
      suffixes2mimeTypeMap.put(ext,mime);
  }

  @Override
  public Boolean supportsRepositioning() {		
    return true;
  }

  @Override
  public void unpackMarkup(Document doc) throws DocumentFormatException {
    unpackMarkup(doc, null, null);

  }

  @Override
  public void unpackMarkup(Document doc, RepositioningInfo repInfo,
          RepositioningInfo ampCodingInfo) throws DocumentFormatException {
    if(doc == null || doc.getSourceUrl() == null) {

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

    XmlDocumentHandler ch = new XmlDocumentHandler(doc, this.markupElementsMap,
            this.element2StringMap);
    Metadata metadata = extractParserTips(doc);

    ch.addStatusListener(statusListener);
    ch.setRepositioningInfo(repInfo);
    // set the object with ampersand coding positions
    ch.setAmpCodingInfo(ampCodingInfo);
    InputStream input = null;	   
    try {
      Parser tikaParser = new TikaConfig().getParser();      
      input = doc.getSourceUrl().openStream();
      tikaParser.parse(input, ch, metadata, new ParseContext());
      setDocumentFeatures(metadata, doc);
    } catch (IOException e) {
      throw new DocumentFormatException(e);
    } catch (SAXException e) {
      throw new DocumentFormatException(e);
    } catch (TikaException e) {
      throw new DocumentFormatException(e);
    }
    finally {
      IOUtils.closeQuietly(input); // null safe
      ch.removeStatusListener(statusListener);
    }

    if (doc instanceof DocumentImpl) {
      ((DocumentImpl)doc).setNextAnnotationId(ch.getCustomObjectsId());
    }
  }

  private void setDocumentFeatures(Metadata metadata, Document doc) {
    FeatureMap fmap = doc.getFeatures();
    setTikaFeature(metadata, TikaCoreProperties.TITLE, fmap);
    setTikaFeature(metadata, Office.AUTHOR, fmap);
    setTikaFeature(metadata, TikaCoreProperties.COMMENTS, fmap);
    setTikaFeature(metadata, TikaCoreProperties.CREATOR, fmap);
    if (fmap.get("AUTHORS") == null && fmap.get("AUTHOR") != null)
      fmap.put("AUTHORS", fmap.get(Office.AUTHOR));
    fmap.put("MimeType", metadata.get(Metadata.CONTENT_TYPE));
  }

  private void setTikaFeature(Metadata metadata, Property property, FeatureMap fmap) {
    String value = metadata.get(property);
    if (value == null) {
      return;
    }

    value = value.trim();
    if (value.length() == 0) {
      return;
    }
    String key = property.getName().toUpperCase();
    if (fmap.containsKey(key)) {
      fmap.put("TIKA_" + key, value);
    }
    else {
      fmap.put(key, value);
      fmap.put("TIKA_" + key, value);
    }		
  }

  /**
   * Tries to extract tips for the parser as specified here -
   * http://tika.apache.org/0.7/parser.html . The tips are not critical
   * for successful parsing.
   * 
   * @param doc
   * @return metadata, not null but may be empty
   */
  private Metadata extractParserTips(Document doc) {
    Metadata metadata = new Metadata();
    Object inputMime = doc.getFeatures().get("MimeType");
    if (inputMime instanceof String) {	
      if (!"application/tika".equals(inputMime)) {
        metadata.add(Metadata.CONTENT_TYPE, (String) doc.getFeatures().get("MimeType"));
      }
    }
    if (doc instanceof DocumentImpl) {
      if (((DocumentImpl)doc).getMimeType() != null) {
        metadata.add(Metadata.CONTENT_TYPE, ((DocumentImpl)doc).getMimeType());
      }
    }
    if (doc.getSourceUrl() != null && doc.getSourceUrl().getProtocol().startsWith("file")) {
      try {
        File fn =new File(doc.getSourceUrl().toURI());
        metadata.add(Metadata.RESOURCE_NAME_KEY, fn.getName());
      } catch (URISyntaxException e) {
        log.debug("Could not extract filename from uri: " + doc.getSourceUrl(), e);
      } catch (IllegalArgumentException e) {
        log.debug("Could not extract filename from uri: " + doc.getSourceUrl(), e);
      }
    }
    return metadata;
  }
}
