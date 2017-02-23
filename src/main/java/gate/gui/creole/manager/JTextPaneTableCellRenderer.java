package gate.gui.creole.manager;

import gate.gui.MainFrame;

import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.html.HTMLDocument;

public class JTextPaneTableCellRenderer extends AbstractCellEditor implements
                                                                    TableCellEditor {

  private static final long serialVersionUID = 3745411623553392990L;

  private JTextPane textPane = new JTextPane();

  private Object value;

  public JTextPaneTableCellRenderer() {
    textPane.setContentType("text/html");
    textPane.setEditable(false);
    textPane.setOpaque(true);
    textPane.setBorder(null);

    textPane.setForeground(UIManager.getColor("Table.selectionForeground"));
    textPane.setBackground(UIManager.getColor("Table.selectionBackground"));

    Font font = UIManager.getFont("Label.font");
    String bodyRule =
        "body { font-family: " + font.getFamily() + "; " + "font-size: "
            + font.getSize() + "pt; "
            + (font.isBold() ? "font-weight: bold;" : "") + "}";
    ((HTMLDocument)textPane.getDocument()).getStyleSheet().addRule(bodyRule);

    textPane.addHyperlinkListener(new HyperlinkListener() {

      @Override
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
            MainFrame.getInstance().showHelpFrame(e.getURL().toString(), "CREOLE Plugin Manager");
      }
    });
  }

  @Override
  public Object getCellEditorValue() {
    return value;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {

    textPane.setText((String)value);

    this.value = value;

    return textPane;
  }

}
