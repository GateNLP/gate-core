package gate.swing;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import gate.resources.img.svg.InvalidIcon;
import gate.resources.img.svg.MavenIcon;
import gate.resources.img.svg.OpenFileIcon;

@SuppressWarnings("serial")
public class IconTableCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
    if(value instanceof Icon) {
      super.getTableCellRendererComponent(table, "", isSelected, hasFocus,
              row, column);
      setIcon((Icon)value);

      if (value instanceof MavenIcon)
    	  setToolTipText("Maven Plugin");
      else if (value instanceof OpenFileIcon)
    	  setToolTipText("Directory Plugin");
      else if (value instanceof InvalidIcon)
    	  setToolTipText("Invalid Plugin");

      return this;
    } else {
      return super.getTableCellRendererComponent(table, value, isSelected,
              hasFocus, row, column);
    }
  }
}