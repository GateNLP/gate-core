/*
 *  Init.java
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
 *  $Id: Init.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.util.spring;

import gate.Gate;
import gate.util.GateException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Helper class to support GATE initialisation via <a
 * href="http://www.springframework.org">Spring</a>. The following is a
 * typical XML fragment to initialise GATE.
 * </p>
 * 
 * <pre>
 * &lt;beans xmlns="http://www.springframework.org/schema/beans"
 *        xmlns:gate="http://gate.ac.uk/ns/spring"
 *        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *        xsi:schemaLocation="
 *          http://www.springframework.org/schema/beans
 *          http://www.springframework.org/schema/beans/spring-beans.xsd
 *          http://gate.ac.uk/ns/spring
 *          http://gate.ac.uk/ns/spring.xsd"&gt;
 * 
 *   &lt;gate:init gate-home="path/to/GATE"
 *              site-config-file="site/gate.xml"
 *              user-config-file="user/gate.xml"&gt;
 *     &lt;gate:preload-plugins&gt;
 *       &lt;value&gt;plugins/ANNIE&lt;/value&gt;
 *       &lt;value&gt;http://plugins.org/another/plugin&lt;/value&gt;
 *     &lt;/gate:preload-plugins&gt;
 *   &lt;/gate:init&gt;
 * </pre>
 * 
 * <p>
 * Valid attributes are <code>gate-home</code>,
 * <code>plugins-home</code>, <code>site-config-file</code>,
 * <code>user-config-file</code> and <code>builtin-creole-dir</code> -
 * Spring <code>Resource</code>s corresponding to the equivalent static
 * set methods of {@link gate.Gate}. Also, <code>preload-plugins</code>
 * is a list of <code>Resource</code>s that will be loaded as GATE
 * plugins after GATE is initialised.
 * </p>
 *
 * <p>
 * Alternatively, instead of specifying <code>gate-home</code>,
 * <code>plugins-home</code> and the configuration files, specifying
 * <code>run-in-sandbox="true"</code> will tell GATE to initialize without
 * reading any configuration files.  See {@link gate.Gate#runInSandbox} for
 * details.
 * </p>
 * 
 * <p>
 * As well as any plugins specified using <code>preload-plugins</code>,
 * we also scan the defining bean factory for any beans of type
 * {@link ExtraGatePlugin}, and load the plugins they refer to. This is
 * useful if bean definitions are provided in several separate files, or
 * if you are providing additional bean definitions to a context that
 * already defines an Init bean definition that you cannot edit.
 * </p>
 * 
 * <p>
 * The equivalent definition in "normal" Spring form (without the
 * <code>gate:</code> namespace) would be:
 * </p>
 * 
 * <pre>
 * &lt;bean class="gate.util.spring.Init"
 *      init-method="init"&gt;
 *   &lt;property name="gateHome" value="path/to/GATE" /&gt;
 *   &lt;property name="siteConfigFile" value="site/gate.xml" /&gt;
 *   &lt;property name="userConfigFile" value="user/gate.xml" /&gt;
 *   &lt;property name="preloadPlugins"&gt;
 *     &lt;list&gt;
 *       &lt;value&gt;plugins/ANNIE&lt;/value&gt;
 *       &lt;value&gt;http://plugins.org/another/plugin&lt;/value&gt;
 *     &lt;/list&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <b>Note that when using this form the init-method="init" in the above
 * definition is vital. GATE will not work if it is omitted.</b>
 */
public class Init implements BeanFactoryAware {
  
  private static final Logger log = Logger.getLogger(Init.class);

  /**
   * An optional list of plugins to load after GATE initialisation.
   */
  private List<Resource> plugins;

  private BeanFactory beanFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  private File gateHome = null;

  public void setGateHome(Resource gateHome) throws IOException {
    this.gateHome = gateHome.getFile();
  }

  private File pluginsHome = null;

  public void setPluginsHome(Resource pluginsHome) throws IOException {
    this.pluginsHome = pluginsHome.getFile();
  }

  private File siteConfigFile = null;

  public void setSiteConfigFile(Resource siteConfigFile) throws IOException {
    this.siteConfigFile = siteConfigFile.getFile();
  }

  private File userConfigFile = null;

  public void setUserConfigFile(Resource userConfigFile) throws IOException {
    this.userConfigFile = userConfigFile.getFile();
  }

  private URL builtinCreoleDir = null;

  public void setBuiltinCreoleDir(Resource builtinCreoleDir) throws IOException {
    this.builtinCreoleDir = builtinCreoleDir.getURL();
  }

  // use Boolean rather than boolean so we can distinguish "set to false" from
  // "not set"
  private Boolean runInSandbox = null;

  public void setRunInSandbox(boolean runInSandbox) {
    this.runInSandbox = Boolean.valueOf(runInSandbox);
  }

  public void setPreloadPlugins(List<Resource> plugins) {
    this.plugins = plugins;
  }

  /**
   * Initialises GATE and loads any preloadPlugins that have been
   * specified, as well as any defined by {@link ExtraGatePlugin} beans
   * in the containing factory.
   */
  public void init() throws Exception {
    if(!Gate.isInitialised()) {
      log.info("Initialising GATE");

      if(gateHome != null) Gate.setGateHome(gateHome);
      if(pluginsHome != null) Gate.setPluginsHome(pluginsHome);
      if(siteConfigFile != null) Gate.setSiteConfigFile(siteConfigFile);
      if(userConfigFile != null) Gate.setUserConfigFile(userConfigFile);
      if(builtinCreoleDir != null) Gate.setBuiltinCreoleDir(builtinCreoleDir);
      if(runInSandbox != null) Gate.runInSandbox(runInSandbox.booleanValue());

      Gate.init();
    }
    else {
      log.info("GATE already initialised");
    }
    if(plugins != null && !plugins.isEmpty()) {
      for(Resource plugin : plugins) {
        log.debug("Loading preload-plugin " + plugin);
        loadPlugin(plugin);
      }
    }
    // look for any ExtraGatePlugin beans
    if(beanFactory instanceof ListableBeanFactory) {
      String[] extraPluginBeanNames = BeanFactoryUtils
              .beanNamesForTypeIncludingAncestors(
                      (ListableBeanFactory)beanFactory, ExtraGatePlugin.class);
      for(String name : extraPluginBeanNames) {
        Resource plugin = ((ExtraGatePlugin)beanFactory.getBean(name,
                ExtraGatePlugin.class)).getLocation();
        if(plugin != null) {
          log.debug("Loading extra-plugin " + plugin);
          loadPlugin(plugin);
        }
      }
    }
  } // init()

  private void loadPlugin(Resource plugin) throws GateException, IOException,
          MalformedURLException {
    File pluginFile = null;
    try {
      pluginFile = plugin.getFile();
    }
    catch(IOException e) {
      // no problem, try just as URL
    }

    if(pluginFile == null) {
      Gate.getCreoleRegister().registerDirectories(plugin.getURL());
    }
    else {
      Gate.getCreoleRegister().registerDirectories(pluginFile.toURI().toURL());
    }
  }
}
