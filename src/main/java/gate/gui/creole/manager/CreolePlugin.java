/*
 * CreolePlugin.java
 * 
 * Copyright (c) 2011, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Mark A. Greenwood, 29/10/2011
 */

package gate.gui.creole.manager;

import gate.Gate;
import gate.Main;
import gate.util.VersionComparator;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreolePlugin {

  protected String id, description, gateMin, gateMax;

  protected URL downloadURL, url, helpURL;

  protected String version;

  private transient String name;

  protected transient String installed;

  protected transient File dir;

  protected transient boolean compatible;

  protected transient boolean install = false;

  public URL getHelpURL() {
    return helpURL;
  }
  
  protected void reset() {
    dir = null;
    install = false;
  }

  private Object readResolve() {
    if(url != null) {
      url = Gate.normaliseCreoleUrl(url);
      try {
        CreolePlugin p = load(new URL(url, "creole.xml"));
        if (downloadURL != null) p.downloadURL = downloadURL;
        return p;
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new RuntimeException("CREOLE Directory URL Must Be Specified!");
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    CreolePlugin other = (CreolePlugin)obj;
    if(id == null) {
      if(other.id != null) return false;
    } else if(!id.equals(other.id)) return false;
    return true;
  }

  public static CreolePlugin load(URL creoleURL) throws Exception {
    SAXBuilder builder = new SAXBuilder(false);

    URLConnection conn = creoleURL.openConnection();
    conn.setReadTimeout(5000);
    conn.setConnectTimeout(5000);

    org.jdom.Document creoleDoc = builder.build(conn.getInputStream());

    Element root = creoleDoc.getRootElement();

    String name = root.getAttributeValue("ID");
    String version = root.getAttributeValue("VERSION");
    String desc = root.getAttributeValue("DESCRIPTION");
    String gateMin = root.getAttributeValue("GATE-MIN");
    String gateMax = root.getAttributeValue("GATE-MAX");
    String help = root.getAttributeValue("HELPURL");

    URL helpURL = null;
    try {
      helpURL = new URL(creoleURL, help);
    }
    catch (Exception e) {
      //ignore all exceptions and just use null;
    }
    
    if(name == null || version == null) return null;

    return new CreolePlugin(name, version, new URL(
            creoleURL, "creole.zip"), desc, helpURL, gateMin, gateMax);
  }

  public String getName() {
    if(name == null) {
      name = id.substring(id.lastIndexOf('.') + 1);
    }

    return name;
  }

  public CreolePlugin(String id, String version, URL downloadURL, 
          String description, URL helpURL, String gateMin, String gateMax) {
    this.id = id;
    this.version = version;
    this.downloadURL = downloadURL;
    this.description = description;
    this.gateMin = gateMin;
    this.gateMax = gateMax;
    this.helpURL = helpURL;

    compatible = VersionComparator.isCompatible(this.gateMin, this.gateMax);
  }

  @Override
  public String toString() {
    return getName() + " v" + version;
  }

  public String compatabilityInfo() {
    if(compatible) return "";

    if(gateMin == null || VersionComparator.isGATENewEnough(gateMin))
      return " <i>(Discontinued after GATE " + gateMax + ")</i>";

    if(gateMax == null
            || VersionComparator.compareVersions(Main.version, gateMax) <= 0)
      return " <i>(Requires GATE " + gateMin + " or above)</i>";

    // we should never get to here but just in case...
    return " <i>(Requires GATE " + gateMin + " to " + gateMax + ")</i>";
  }
}
