/*
 * ControllerMetadataViewer.java
 *
 * Copyright (c) 2012, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 *
 * Mark A. Greenwood, 08/11/2012
 */

package gate.gui;

import gate.Controller;
import gate.Gate;
import gate.GateConstants;
import gate.Resource;
import gate.creole.AbstractVisualResource;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.GuiType;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.xerces.parsers.DOMParser;
import org.cyberneko.html.HTMLConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.swing.BasicPanel;
import org.xhtmlrenderer.swing.FSMouseListener;
import org.xhtmlrenderer.swing.LinkListener;
import org.xhtmlrenderer.util.Configuration;
import org.xml.sax.InputSource;

/**
 * This viewer displays metadata associated with a GATE Controller. The location
 * of the <code>metadata.xml</code> file is specified using the
 * <code>gate.app.MetadataURL</code> feature on the controller. Note that this
 * feature must be a URL object and not a String. Currently the only way to
 * specifiy a URL as a controler feature is to manually edit the saved xgapp
 * file.
 * 
 * @author Mark A. Greenwood
 */
@CreoleResource(name = "About...", guiType = GuiType.LARGE, resourceDisplayed = "gate.Controller")
public class ControllerMetadataViewer extends AbstractVisualResource {

  private static final long serialVersionUID = -1161421403987238291L;

  private XHTMLPanel display = new XHTMLPanel();

  private DocumentBuilderFactory builderFactory = DocumentBuilderFactory
      .newInstance();

  private DocumentBuilder builder = null;

  @SuppressWarnings("rawtypes")
  @Override
  public Resource init() throws ResourceInstantiationException {
    setLayout(new BorderLayout());
    add(new JScrollPane(display), BorderLayout.CENTER);

    try {
      builder = builderFactory.newDocumentBuilder();
    } catch(ParserConfigurationException e) {
      throw new ResourceInstantiationException(
          "Unable to construct an XML parser", e);
    }

    if(Configuration.isTrue("xr.use.listeners", true)) {
      List l = display.getMouseTrackingListeners();
      for(Iterator i = l.iterator(); i.hasNext();) {
        FSMouseListener listener = (FSMouseListener)i.next();
        if(listener instanceof LinkListener) {
          display.removeMouseTrackingListener(listener);
        }
      }
      display.addMouseTrackingListener(new LinkListener() {

        @Override
        public void linkClicked(BasicPanel panel, String uri) {
          //open any links in an actual web browser
          MainFrame.getInstance().showHelpFrame(uri, null);
        }
      });
    }

    return this;
  }

  @Override
  public void setTarget(Object target) {

    if(target == null)
      throw new NullPointerException("received a null target");

    if(!(target instanceof Controller))
      throw new IllegalArgumentException("not a controller");

    Controller controller = (Controller)target;

    if(!controller.getFeatures().containsKey("gate.app.MetadataURL"))
      throw new IllegalArgumentException("no gate.app.MetadataURL feature");

    try {
      URL metadata = (URL)controller.getFeatures().get("gate.app.MetadataURL");
      URL longDesc = new URL(metadata, "long-desc.html");
      URL iconDesc = new URL(metadata, "icon.png");

      Document document = builder.parse(metadata.openStream());

      Node text =
          document.getDocumentElement().getElementsByTagName("pipeline-name")
              .item(0).getFirstChild();

      Font font =
          Gate.getUserConfig().getFont(GateConstants.TEXT_COMPONENTS_FONT);

      StringBuilder page = new StringBuilder();
      page.append("<!DOCTYPE html>");
      page.append("<html>");
      page.append("<head>");
      page.append("<style type='text/css'>body { font-family: ")
          .append(font.getFamily()).append("; font-size: ")
          .append(font.getSize()).append("pt }</style>");
      page.append("</head>");
      page.append("<body>");
      page.append("<h1><img style='vertical-align: middle;' src='")
          .append(StringEscapeUtils.escapeHtml(iconDesc.toString())).append("'/> ")
          .append(StringEscapeUtils.escapeHtml(text.getTextContent())).append("</h1>");
      page.append(IOUtils.toString(longDesc, "UTF-8"));
      page.append("</body></html>");

      // parse using NekoHTML
      HTMLConfiguration config = new HTMLConfiguration();
      // Force element names to lower case to match XHTML requirements
      // as that is what Flying Saucer expects
      config.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
      DOMParser htmlParser = new DOMParser(config);
      htmlParser.parse(new InputSource(new StringReader(page.toString())));
      display.setDocument(htmlParser.getDocument(),
          longDesc.toString());

    } catch(Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
