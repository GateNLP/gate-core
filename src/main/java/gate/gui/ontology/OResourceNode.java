/**
 * 
 */
package gate.gui.ontology;

import gate.creole.ontology.OResource;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author niraj
 * 
 */
public class OResourceNode implements Transferable {

  /** flavor used for drag and drop */
  final public static DataFlavor ORESOURCE_NODE_FLAVOR = new DataFlavor(
          OResourceNode.class, "OResource Node");

  static DataFlavor flavors[] = {ORESOURCE_NODE_FLAVOR};

  private OResource resource;

  public OResourceNode(OResource resource) {
    super();
    this.resource = resource;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
   */
  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
   */
  @Override
  public boolean isDataFlavorSupported(DataFlavor df) {
    return df.equals(ORESOURCE_NODE_FLAVOR);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
   */
  @Override
  public Object getTransferData(DataFlavor df)
          throws UnsupportedFlavorException, IOException {
    if(df.equals(ORESOURCE_NODE_FLAVOR)) {
      return this;
    }
    else throw new UnsupportedFlavorException(df);
  }

  public OResource getResource() {
    return resource;
  }

}
