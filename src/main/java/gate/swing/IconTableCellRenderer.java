package gate.swing;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class IconTableCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
    if(value instanceof Icon) {
      super.getTableCellRendererComponent(table, "", isSelected, hasFocus,
              row, column);
      setIcon((Icon)value);
      return this;
    } else {
      return super.getTableCellRendererComponent(table, value, isSelected,
              hasFocus, row, column);
    }
  }
}