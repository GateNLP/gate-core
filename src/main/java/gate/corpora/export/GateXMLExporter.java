/*
 *  Copyright (c) 1995-2014, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Mark A. Greenwood 16/07/2014
 *
 */

package gate.corpora.export;

import gate.Document;
import gate.DocumentExporter;
import gate.FeatureMap;
import gate.corpora.DocumentStaxUtils;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleResource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

@CreoleResource(name = "GATE XML Exporter", tool = true, autoinstances = @AutoInstance, icon = "GATEXML")
public class GateXMLExporter extends DocumentExporter {

  private static final long serialVersionUID = -5725505758491779035L;

  public GateXMLExporter() {
    super("GATE XML", "xml", "text/xml");
  }

  @Override
  public void export(Document doc, File file, FeatureMap options)
          throws IOException {
    super.export(doc, file, options);

    // not sure why this was being done in the old code, but I'll make
    // sure we do it here as well
    doc.setSourceUrl(file.toURI().toURL());
  }

  @Override
  public void export(Document doc, OutputStream out, FeatureMap options)
          throws IOException {
    try {
      DocumentStaxUtils.writeDocument(doc, out, "");
    } catch(XMLStreamException e) {
      throw new IOException(e);
    }
  }
}
