package gate.gui.teamware;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

@SuppressWarnings("serial")
class AnnotationSetNameCellRenderer extends DefaultListCellRenderer {

  public static final String DEFAULT_SET_TEXT = "<Default annotation set>";

  private Font defaultFont;
  
  private Font italicFont;
  
  AnnotationSetNameCellRenderer() {
    defaultFont = getFont();
    italicFont = defaultFont.deriveFont(Font.ITALIC);
  }

  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
    Font font = defaultFont;
    if(value == null) {
      value = DEFAULT_SET_TEXT;
      font = italicFont;
    }
    Component c = super.getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
    c.setFont(font);
    return c;
  }
}
