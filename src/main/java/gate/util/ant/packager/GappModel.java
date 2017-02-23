package gate.util.ant.packager;

import gate.util.Files;
import gate.util.GateRuntimeException;
import gate.util.persistence.PersistenceManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class GappModel {
  private Document gappDocument;

  /**
   * The URL at which this GAPP file is saved.
   */
  private URL gappFileURL;

  /**
   * The URL against which to resolve $gatehome$ relative paths.
   */
  private URL gateHomeURL;

  /**
   * The URL against which to resolve $resourceshome$ relative paths.
   */
  private URL resourcesHomeURL;

  /**
   * Map whose keys are the resolved URLs of plugins referred to by relative
   * paths in the GAPP file and whose values are the JDOM Elements of the
   * &lt;urlString&gt; elements concerned.
   */
  private Map<URL, List<Element>> pluginRelpathsMap =
          new HashMap<URL, List<Element>>();

  /**
   * Map whose keys are the resolved URLs of resource files other than plugin
   * directories referred to by relative paths in the GAPP file and whose
   * values are the JDOM Elements of the &lt;urlString&gt; elements concerned.
   */
  private Map<URL, List<Element>> resourceRelpathsMap =
          new HashMap<URL, List<Element>>();

  /**
   * XPath selecting all urlStrings that contain $relpath$ or $gatehome$ in the
   * &lt;application&gt; section of the file.
   */
  private static XPath relativeResourcePathElementsXPath;

  /**
   * XPath selecting all urlStrings that contain $relpath$ or $gatehome$ in the
   * &lt;urlList&gt; section of the file.
   */
  private static XPath relativePluginPathElementsXPath;

  /**
   * @see #GappModel(URL,URL, URL)
   */
  public GappModel(URL gappFileURL) {
    this(gappFileURL, null, null);
  }

  /**
   * @see #GappModel(URL,URL, URL)
   */
  public GappModel(URL gappFileURL, URL gateHomeURL) {
    this(gappFileURL, gateHomeURL, null);
  }

  /**
   * Create a GappModel for a GAPP file.
   * 
   * @param gappFileURL the URL of the GAPP file to model.
   * @param gateHomeURL the URL against which $gatehome$ relative paths should
   * be resolved.  This may be null if you are sure that the GAPP you are
   * packaging does not contain any $gatehome$ paths.  If no gateHomeURL is
   * provided but the application does contain a $gatehome$ path, a
   * GateRuntimeException will be thrown.
   * @param resourcesHomeURL the URL against which $resourceshome$ relative paths should
   * be resolved.  This may be null if you are sure that the GAPP you are
   * packaging does not contain any $resourceshome$ paths.  If no gateHomeURL is
   * provided but the application does contain a $resourceshome$ path, a
   * GateRuntimeException will be thrown.
   */
  @SuppressWarnings("unchecked")
  public GappModel(URL gappFileURL, URL gateHomeURL, URL resourcesHomeURL) {
    if(!"file".equals(gappFileURL.getProtocol())) {
      throw new GateRuntimeException("GAPP URL must be a file: URL");
    }
    if(gateHomeURL != null && !"file".equals(gateHomeURL.getProtocol())) {
      throw new GateRuntimeException("GATE home URL must be a file: URL");
    }
    this.gappFileURL = gappFileURL;
    this.gateHomeURL = gateHomeURL;
    this.resourcesHomeURL = resourcesHomeURL;

    try {
      SAXBuilder builder = new SAXBuilder();
      this.gappDocument = builder.build(gappFileURL);
    }
    catch(Exception ex) {
      throw new GateRuntimeException("Error parsing GAPP file", ex);
    }

    // compile the XPath expression
    if(relativeResourcePathElementsXPath == null) {
      try {
        relativeResourcePathElementsXPath =
                XPath
                        .newInstance(
                                // URLHolder elements as map entry values
                                  "/gate.util.persistence.GateApplication/application"
                                + "//gate.util.persistence.PersistenceManager-URLHolder"
                                + "/urlString[starts-with(., '$relpath$') "
                                + "or starts-with(., '$resourceshome$') "
                                + "or starts-with(., '$gatehome$')]"
                                + " | "
                                // specific Persistence object fields of type URLHolder
                                // (e.g. datastore location)
                                + "/gate.util.persistence.GateApplication/application"
                                + "//*[@class='gate.util.persistence.PersistenceManager$URLHolder']"
                                + "/urlString[starts-with(., '$relpath$') "
                                + "or starts-with(., '$resourceshome$') "
                                + "or starts-with(., '$gatehome$')]");
        relativePluginPathElementsXPath =
                XPath
                        .newInstance("/gate.util.persistence.GateApplication/urlList"
                                + "//gate.util.persistence.PersistenceManager-URLHolder"
                                + "/urlString[starts-with(., '$relpath$') "
                                + "or starts-with(., '$resourceshome$') "
                                + "or starts-with(., '$gatehome$')]");
      }
      catch(JDOMException jdx) {
        throw new GateRuntimeException("Error creating XPath expression", jdx);
      }
    }

    List<Element> resourceRelpaths = null;
    List<Element> pluginRelpaths = null;
    try {
      // the compiler thinks this is unsafe, but we know that the XPath
      // expression will only select Elements so it's OK really
      resourceRelpaths =
              relativeResourcePathElementsXPath.selectNodes(gappDocument);

      pluginRelpaths =
              relativePluginPathElementsXPath.selectNodes(gappDocument);
    }
    catch(JDOMException e) {
      throw new GateRuntimeException(
              "Error extracting 'relpath' URLs from GAPP file", e);
    }

    try {
      buildRelpathsMap(resourceRelpaths, resourceRelpathsMap);
      buildRelpathsMap(pluginRelpaths, pluginRelpathsMap);
    }
    catch(MalformedURLException mue) {
      throw new GateRuntimeException(
              "Error parsing relative paths in GAPP file", mue);
    }
  }

  private void buildRelpathsMap(List<Element> relpathElements,
          Map<URL, List<Element>> relpathsMap) throws MalformedURLException {
    for(Element el : relpathElements) {
      String elementText = el.getText();
      URL targetURL = null;
      if(elementText.startsWith("$gatehome$")) {
        // complain if gateHomeURL not set
        if(gateHomeURL == null) {
          throw new GateRuntimeException("Found a $gatehome$ relative path in "
              + "GAPP file, but no GATE home URL provided to resolve against");
        }
        String relativePath = el.getText().substring("$gatehome$".length());
        targetURL = new URL(gateHomeURL, relativePath);
      }
      else if(elementText.startsWith("$resourceshome$")) {
        // complain if gateHomeURL not set
        if(gateHomeURL == null) {
          throw new GateRuntimeException("Found a $resourceshome$ relative path in "
              + "GAPP file, but no resources home URL provided to resolve against");
        }
        String relativePath = el.getText().substring("$resourceshome$".length());
        targetURL = new URL(resourcesHomeURL, relativePath);
      }
      else if(elementText.startsWith("$relpath$")) {
        String relativePath = el.getText().substring("$relpath$".length());
        targetURL = new URL(gappFileURL, relativePath);
      }
      List<Element> eltsForURL = relpathsMap.get(targetURL);
      if(eltsForURL == null) {
        eltsForURL = new ArrayList<Element>();
        relpathsMap.put(targetURL, eltsForURL);
      }
      eltsForURL.add(el);
    }
  }

  /**
   * Get the URL at which the GAPP file resides.
   * 
   * @return the gappFileURL
   */
  public URL getGappFileURL() {
    return gappFileURL;
  }

  /**
   * Set the URL at which the GAPP file resides. When this GappModel is
   * constructed this will be the URL from which the file is loaded, but
   * this should be changed if you wish to write the updated GAPP to
   * another location.
   * 
   * @param gappFileURL the gappFileURL to set
   */
  public void setGappFileURL(URL gappFileURL) {
    this.gappFileURL = gappFileURL;
  }

  /**
   * Get the JDOM Document representing this GAPP file.
   * 
   * @return the document
   */
  public Document getGappDocument() {
    return gappDocument;
  }

  /**
   * Get the plugin URLs that are referenced by relative paths in this
   * GAPP file.
   * 
   * @return the set of URLs.
   */
  public Set<URL> getPluginURLs() {
    return pluginRelpathsMap.keySet();
  }

  /**
   * Get the resource URLs that are referenced by relative paths in this
   * GAPP file.
   * 
   * @return the set of URLs.
   */
  public Set<URL> getResourceURLs() {
    return resourceRelpathsMap.keySet();
  }

  /**
   * Update the modelled content of the GAPP file to replace any
   * relative paths referring to <code>originalURL</code> with those
   * pointing to <code>newURL</code>. If makeRelative is
   * <code>true</code>, the new path will be relativized against the
   * <b>current</b> {@link #gappFileURL}, so you should call
   * {@link #setGappFileURL} with the URL at which the file will
   * ultimately be saved before calling this method. If
   * <code>makeRelative</code> is <code>false</code> the new URL
   * will be used directly as an absolute URL (so to replace a relative
   * path with the absolute URL to the same file you can call
   * <code>updatePathForURL(u, u, false)</code>).
   * 
   * @param originalURL The original URL whose references are to be
   *          replaced.
   * @param newURL the replacement URL.
   * @param makeRelative should we relativize the newURL before use?
   */
  public void updatePathForURL(URL originalURL, URL newURL, boolean makeRelative) {
    List<Element> resourceEltsToUpdate = resourceRelpathsMap.get(originalURL);
    List<Element> pluginEltsToUpdate = pluginRelpathsMap.get(originalURL);
    if(resourceEltsToUpdate == null && pluginEltsToUpdate == null) {
      return;
    }

    String newPath;
    if(makeRelative) {
      newPath =
              "$relpath$"
                      + PersistenceManager.getRelativePath(gappFileURL, newURL);
    }
    else {
      newPath = newURL.toExternalForm();
    }

    if(resourceEltsToUpdate != null) {
      for(Element e : resourceEltsToUpdate) {
        e.setText(newPath);
      }
    }
    if(pluginEltsToUpdate != null) {
      for(Element e : pluginEltsToUpdate) {
        e.setText(newPath);
      }
    }
  }

  /**
   * Finish up processing of the gapp file ready for writing.
   */
  @SuppressWarnings("unchecked")
  public void finish() {
    // remove duplicate plugin entries
    try {
      // this XPath selects all URLHolders out of the URL list that have
      // the same URL string as one of their preceding siblings, i.e. if
      // there are N URLs in the list with the same value then this
      // XPath
      // will select all but the first one of them.
      XPath duplicatePluginXPath =
              XPath
                      .newInstance("/gate.util.persistence.GateApplication/urlList"
                              + "/localList/gate.util.persistence.PersistenceManager-URLHolder"
                              + "[urlString = preceding-sibling::gate.util.persistence.PersistenceManager-URLHolder/urlString]");
      List<Element> duplicatePlugins =
              duplicatePluginXPath.selectNodes(gappDocument);
      for(Element e : duplicatePlugins) {
        e.getParentElement().removeContent(e);
      }
    }
    catch(JDOMException e) {
      throw new GateRuntimeException(
              "Error applying XPath expression to remove duplicate plugins", e);
    }
  }

  /**
   * Write out the (possibly modified) GAPP file to its new location.
   * 
   * @throws IOException if an I/O error occurs.
   */
  public void write() throws IOException {
    finish();
    File newGappFile = Files.fileFromURL(gappFileURL);
    FileOutputStream fos = new FileOutputStream(newGappFile);
    BufferedOutputStream out = new BufferedOutputStream(fos);

    XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
    outputter.output(gappDocument, out);
  }
}
