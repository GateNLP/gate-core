/*
 * ResourceReference.java
 *
 * Copyright (c) 2017, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 3, June 2007
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Mark A. Greenwood, 25th January 2017
 */

package gate.creole;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gate.Gate;

/**
 * This class provides a common way of referencing a resource regardless of
 * where it is located. Specifically it allows resources stored within plugins
 * to be referenced without needing to know where the resource is actually
 * located as that information is determined when the resource is accessed.
 * Previously most GATE components used {@link java.net.URL URL} instances to
 * refer to resources and to aid in converting existing code to use
 * {@code ResourceReference} instead the public API follows that of {@code URL}
 * where possible.
 */
public class ResourceReference implements Serializable {

  private static final long serialVersionUID = 2526144106607856721L;

  protected static final Logger log = LoggerFactory.getLogger(ResourceReference.class);

  // internal we store the location of the resource as a URI, the exact format
  // of the URI will depend on numerous factors and no specific URI scheme is
  // assumed
  private URI uri;

  /**
   * Create a new instance that references a resource accessible via a known
   * {@link java.net.URL URL}. While a useful constructor in it's own right this
   * also allows old applications that contain URLs as parameters to PRs that
   * now take a {@code ResourceReference} to continue to work as the URL will
   * get passed into this constructor automatically as the application is
   * reloaded.
   * 
   * @param url
   *          the {@link java.net.URL URL} of the resource you wish to reference
   * @throws URISyntaxException
   *           if the URL does not strictly conform to RFC 2396 or if it cannot
   *           be converted to an absolute URI
   */
  public ResourceReference(URL url) throws URISyntaxException {
    uri = url.toURI();

    // we only support absolute URIs as there would be no way to access a
    // resource at a relative URI as we wouldn't know what to resolve it against
    if(!uri.isAbsolute())
      throw new URISyntaxException(uri.toString(),
          "We only support absolute URIs");
    
    if("jar".equals(uri.getScheme())) {
      // if the uri points at a file within a jar then we check to see if it's a
      // resource inside a plugin and convert the URL (which was probably a
      // relpath in an xgapp into a creole:// URI

      try {
        String[] parts = url.toExternalForm().split("!");
        URL base = new URL(parts[0] + "!/");

        for(Plugin plugin : Gate.getCreoleRegister().getPlugins()) {
          // go through each plugin we know about until....

          if(plugin.getBaseURL().equals(base)) {
            uri = plugin.getBaseURI().resolve(parts[1]);
          }
        }

      } catch(MalformedURLException e) {
        throw new URISyntaxException(uri.toString(),"Error normalizing plugin based URL");
      }
    }
  }

  /**
   * Create a new instance that references a resource described by a
   * {@link java.net.URI URI}.
   * 
   * @param uri
   *          the {@link java.net.URI URI} of the resource you wish to reference
   * @throws URISyntaxException
   *           if the URI is not absolute
   */
  public ResourceReference(URI uri) throws URISyntaxException {
    this.uri = uri;

    // we only support absolute URIs as there would be no way to access a
    // resource at a relative URI as we wouldn't know what to resolve it against
    if(!uri.isAbsolute())
      throw new URISyntaxException(uri.toString(),
          "We only support absolute URIs");
  }

  /**
   * Creates a new instance that references a resource within a given
   * {@link gate.creole.Plugin Plugin}.
   * 
   * @param plugin
   *          a {@link gate.creole.Plugin Plugin} against which to resolve the
   *          path. Can be null, but only if the path can be parsed as an
   *          absolute URI
   * @param path
   *          the path to the resource which will be resolved against the
   *          {@link gate.creole.Plugin Plugin}
   * @throws URISyntaxException
   *           if the reference cannot be converted to an absolute URI
   */
  public ResourceReference(Plugin plugin, String path)
      throws URISyntaxException {

    // if the path is null this causes problems later but it's safe to use the
    // empty string instead so we'll do that
    if(path == null) path = "";

    if(plugin != null) {
      // if the plugin isn't null then we can simply resolve the path against
      // it's base URI
      uri = plugin.getBaseURI().resolve(path);
    } else {
      // there is no plugin so we just use the path to create the URI
      uri = new URI(path);
    }

    // we only support absolute URIs as there would be no way to access a
    // resource at a relative URI as we wouldn't know what to resolve it against
    if(!uri.isAbsolute())
      throw new URISyntaxException(path, "We only support absolute URIs");
  }

  /**
   * Creates a new instance that references a resource identified by resolving
   * the path against an existing {@code ResourceReference}.
   * 
   * @param context
   *          a {@code ResourceReference} against which to resolve the path. Can
   *          be null, but only if the path can be parsed as an absolute URI
   * @param path
   *          the path to the resource which will be resolved against the
   *          context.
   * @throws URISyntaxException
   *           if the reference cannot be converted to an absolute URI
   */
  public ResourceReference(ResourceReference context, String path)
      throws URISyntaxException {

    if(context != null) {
      // if a context is provided then try and resolve the path against the
      // encapsulated URI
      uri = context.uri.resolve(path);
    } else {
      // there is no context so we just use the path to create the URI
      uri = new URI(path);
    }

    // we only support absolute URIs as there would be no way to access a
    // resource at a relative URI as we wouldn't know what to resolve it against
    if(!uri.isAbsolute())
      throw new URISyntaxException(path, "We only support absolute URIs");
  }

  /**
   * Opens a connection to this {@code ResourceReference} and returns an
   * {@code InputStream} for reading from that connection. This method is a
   * shorthand for: <blockquote>
   * 
   * <pre>
   * toURL().openConnection().getInputStream()
   * </pre>
   * 
   * </blockquote>
   *
   * @return an input stream for reading from the URL connection.
   * @exception IOException
   *              if an I/O exception occurs.
   */
  public InputStream openStream() throws IOException {
    return toURL().openStream();
  }

  /**
   * Returns a {@link java.net.URLConnection URLConnection} instance that
   * represents a connection to the resource referred to by this
   * {@code ResourceReference}.
   * <P>
   * It should be noted that a URLConnection instance does not establish the
   * actual network connection on creation. This will happen only when calling
   * {@linkplain java.net.URLConnection#connect() URLConnection.connect()}.
   * </P>
   * This method is a shorthand for: <blockquote>
   * 
   * <pre>
   * toURL().openConnection()
   * </pre>
   * 
   * </blockquote>
   *
   * @return a {@link java.net.URLConnection URLConnection} linking to the
   *         underlying resource.
   * @exception IOException
   *              if an I/O exception occurs.
   */
  public URLConnection openConnection() throws IOException {
    return toURL().openConnection();
  }

  /**
   * Creates a {@link java.net.URL URL} instance that can be used to access the
   * underlying resource. It should be noted that the result is not guaranteed
   * to be valid long term and should never be persisted. If you want persistent
   * access then store the {@code ResourceReference} instance instead.
   * 
   * @return a {@link java.net.URL URL} that currently gives access to the
   *         referenced resource.
   * @throws IOException
   *           if an I/O exception occurs.
   */
  public URL toURL() throws IOException {

    // if the URI scheme is anything but creole then let java handle the
    // conversion to a URL as it already knows how to do that
    if(!uri.getScheme().equals("creole")) return uri.toURL();

    try {
      // create a URI that should point to the base of the plugin in which this
      // resource resides
      URI base = new URI("creole", uri.getAuthority(), "/", null, null);

      for(Plugin plugin : Gate.getCreoleRegister().getPlugins()) {
        // go through each plugin we know about until....

        if(plugin.getBaseURI().equals(base)) {
          // ... we find one with the base URI we are expecting and then

          // create a new URL using the base URL of the plugin and the path from
          // the URI we know points to the resource
          String path = uri.getPath();
          if(path.startsWith("/")) {
            // strip off leading slash
            path = path.substring(1);
          }
          return new URL(plugin.getBaseURL(), path);
        }
      }

      // TODO if we can't find the plugin should we try loading it?

    } catch(URISyntaxException e) {
      // this is impossible so ignore it
      log.debug("An impossible exception case happened, how?", e);
    }

    throw new IOException("Unable to locate URI: " + uri);
  }

  public URI toURI() {
    return uri;
  }

  @Override
  public String toString() {
    return uri.toString();
  }

  public String toExternalForm() {
    return toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((uri == null) ? 0 : uri.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    ResourceReference other = (ResourceReference)obj;
    if(uri == null) {
      if(other.uri != null) return false;
    } else if(!uri.equals(other.uri)) return false;
    return true;
  }
}
