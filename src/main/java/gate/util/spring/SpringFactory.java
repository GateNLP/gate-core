/*
 *  SpringFactory.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 07/Oct/2006
 *
 *  $Id: SpringFactory.java 17656 2014-03-14 08:55:23Z markagreenwood $
 */

package gate.util.spring;

import gate.FeatureMap;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.springframework.core.io.Resource;

/**
 * This class contains spring-aware factory methods for useful GATE
 * components. Most of these methods have now been superseded by (and
 * delegate their processing to) the specific factory beans in this
 * package, but are retained for compatibility with existing
 * configurations.
 */
public class SpringFactory {

  /**
   * Creates a feature map from a source map. Any values in the source
   * map that are of type
   * <code>org.springframework.core.io.Resource</code> are converted to
   * their corresponding {@link java.net.URL}. For example:
   * 
   * <pre>
   * &lt;bean id=&quot;feature-map&quot; class=&quot;gate.util.spring.SpringFactory&quot;
   *       factory-method=&quot;createFeatureMap&quot;&gt;
   *   &lt;constructor-arg&gt;
   *     &lt;map&gt;
   *       &lt;entry key=&quot;inputASName&quot; value=&quot;Extra&quot; /&gt;
   *       &lt;entry key=&quot;config&quot;&gt;
   *         &lt;value type=&quot;org.springframework.core.io.Resource&quot;&gt;path/to/config.xml&lt;/value&gt;
   *       &lt;/entry&gt;
   *     &lt;/map&gt;
   *   &lt;/constructor-arg&gt;
   * &lt;/bean&gt;
   * </pre>
   * 
   * For an easier way to achieve this see {@link FeatureMapFactoryBean},
   * to which this method delegates.
   */
  public static FeatureMap createFeatureMap(Map<Object,Object> sourceMap) throws IOException {
    FeatureMapFactoryBean factory = new FeatureMapFactoryBean();
    factory.setSourceMap(sourceMap);

    return (FeatureMap)factory.getObject();
  }

  /**
   * Loads a saved application state (gapp file) from the given Spring
   * resource. The resource is first looked up as a {@link File}, and if
   * found the application is loaded using
   * {@link PersistenceManager#loadObjectFromFile(File)
   * loadObjectFromFile}. If the resource cannot be accessed as a file
   * it is accessed as a {@link URL} and the application loaded with
   * {@link PersistenceManager#loadObjectFromUrl(URL) loadObjectFromUrl}
   * . This is useful as many PRs either require or function better with
   * file: URLs than with other kinds of URL. For an easier way to
   * achieve this, see {@link SavedApplicationFactoryBean}, to which
   * this method delegates.
   */
  public static Object loadObjectFromResource(Resource res)
          throws GateException, IOException {
    SavedApplicationFactoryBean factory = new SavedApplicationFactoryBean();
    factory.setLocation(res);

    return factory.getObject();
  }

  /**
   * Convert a Spring resource to a URL. The resource is first looked up
   * as a {@link File}, and if this succeeds then the file is converted
   * to a URL. If the resource cannot be accessed as a file then it is
   * converted directly to a URL. This is useful as many PRs either
   * require or function better with file: URLs than with other kinds of
   * URL. In a Spring configuration this method may be accessed using
   * the shorthand
   * <code>&lt;gate:url&gt;WEB-INF/file.txt&lt;/gate:url&gt;</code>
   */
  public static URL resourceToUrl(Resource res) throws IOException {
    File resourceFile = null;
    try {
      resourceFile = res.getFile();
    }
    catch(IOException e) {
      // ignore, leaving resourceFile == null
    }

    if(resourceFile == null) {
      // couldn't get a file, so resolve the resource as a URL
      return res.getURL();
    }
    else {
      // get the URL to the file
      return resourceFile.toURI().toURL();
    }
  }
}
