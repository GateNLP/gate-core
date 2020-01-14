/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz, Nov 7, 2008
 *
 *  $Id: SchemaAnnotationEditor.java 9221 2007-11-14 17:46:37Z valyt $
 */

package gate.gui.annedit;

import gate.util.GateRuntimeException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

/**
 * Dialog for building a regular expression.
 */
public class SearchExpressionsAction extends AbstractAction {
  private static final long serialVersionUID = 1L;
  private JTextField sourceTextField;
  private Window annotationEditorWindow;
  private JCheckBox searchRegExpChk;

  /**
   * Shows a dialog with a list of predefined search expressions
   * to modified the current search expression.
   *
   * @param sourceTextField text field that contains the search expression
   * @param annotationEditorWindow search window
   * @param searchRegExpChk check box for regular expression; may be null
   */
  public SearchExpressionsAction(JTextField sourceTextField,
                                 Window annotationEditorWindow,
                                 JCheckBox searchRegExpChk){
    super("?");
    super.putValue(SHORT_DESCRIPTION, "GATE search expression builder.");
    this.sourceTextField = sourceTextField;
    this.annotationEditorWindow = annotationEditorWindow;
    this.searchRegExpChk = searchRegExpChk;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    String[] values1 = {
      "Number",
      "Person"
    };
    String[] values2 = {
      "Any character",
      "The beginning of a line",
      "The end of a line",
      "All letters",
      "Letter uppercase",
      "Letter lowercase",
      "Letter titlecase",
      "Letter modifier",
      "Letter other",
      "All Numbers",
      "Number decimal digit",
      "Number letter",
      "Number other",
      "All punctuations",
      "Punctuation connector",
      "Punctuation dash",
      "Punctuation open",
      "Punctuation close",
      "Punctuation initial quote",
      "Punctuation final quote",
      "Punctuation other",
      "All symbols",
      "Symbol math",
      "Symbol currency",
      "Symbol modifier",
      "Symbol other",
      "All separators",
      "Separator space",
      "Separator line",
      "Separator paragraph",
      "All Marks",
      "Mark nonspacing",
      "Mark spacing combining",
      "Mark enclosing",
      "All others",
      "Other control",
      "Other format",
      "Other surrogate",
      "Other private use",
      "Other not assigned",
      "Any character except Category",
      "Category1 and/or Category2",
      "Category1 and Category2"
    };
    String[] values3 = {
      "Either the selection or X",
      "Once or not at all",
      "Zero or more times",
      "One or more times",
      "Capturing group",
      "Non-capturing group"
    };
    final JTextField modifiedTextField = new JTextField(25);
    modifiedTextField.setText(sourceTextField.getText());
    JPanel vspace1 = new JPanel();
    vspace1.setSize(0, 5);
    final JList<String> list1 = new JList<String>(values1);
    list1.setVisibleRowCount(Math.min(10, values1.length));
    list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp1 = new JScrollPane(list1);
    final JButton b1 = new JButton("Replace search expression");
    b1.setEnabled(false);
    JPanel vspace2 = new JPanel();
    vspace2.setSize(0, 5);
    final JList<String> list2 = new JList<String>(values2);
    list2.setVisibleRowCount(Math.min(10, values2.length));
    list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp2 = new JScrollPane(list2);
    final JButton b2 = new JButton("Insert at the caret position");
    b2.setEnabled(false);
    JPanel vspace3 = new JPanel();
    vspace3.setSize(0, 5);
    final JList<String> list3 = new JList<String>(values3);
    list3.setVisibleRowCount(Math.min(10, values3.length));
    list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp3 = new JScrollPane(list3);
    final JButton b3 = new JButton("Modify the selection");
    b3.setEnabled(false);
    modifiedTextField.addCaretListener(new CaretListener() {
      @Override
      public void caretUpdate(CaretEvent e) {
        list3.setEnabled(modifiedTextField.getSelectedText() != null);
      }
    });
    Object[] messageObjects = {
      "Current search expression:", modifiedTextField,
      vspace1, jsp1, b1, vspace2, jsp2, b2, vspace3, jsp3, b3
    };
    String options[] = {"OK", "Cancel"};
    final JOptionPane optionPane = new JOptionPane(
      messageObjects,
      JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
      null, options, "Cancel");
    b1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          modifySearchExpression(list1.getSelectedValue(),
            modifiedTextField);
      }
    });
    list1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
          modifySearchExpression(list1.getSelectedValue(),
            modifiedTextField);
        }
      }
    });
    list1.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (list1.getSelectedValue() != null) {
          b1.setEnabled(true);
        } else {
          b1.setEnabled(false);
        }
      }
    });
    b2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          modifySearchExpression(list2.getSelectedValue(),
            modifiedTextField);
      }
    });
    list2.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
          modifySearchExpression(list2.getSelectedValue(),
            modifiedTextField);
        }
      }
    });
    list2.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (list2.getSelectedValue() != null) {
          b2.setEnabled(true);
        } else {
          b2.setEnabled(false);
        }
      }
    });
    b3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          modifySearchExpression(list3.getSelectedValue(),
            modifiedTextField);
      }
    });
    list3.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2 && list3.isEnabled()) {
          modifySearchExpression(list3.getSelectedValue(),
            modifiedTextField);
        }
      }
    });
    list3.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (list3.getSelectedValue() != null) {
          b3.setEnabled(true);
        } else {
          b3.setEnabled(false);
        }
      }
    });
    annotationEditorWindow.setVisible(false);
    JDialog optionDialog = optionPane.createDialog(
      gate.gui.MainFrame.getInstance(), "GATE search expression builder");
    modifiedTextField.setCaretPosition(
      sourceTextField.getSelectionStart()
      == sourceTextField.getCaretPosition()?
        sourceTextField.getSelectionEnd()
       :sourceTextField.getSelectionStart());
    modifiedTextField.moveCaretPosition(
      sourceTextField.getCaretPosition());
    modifiedTextField.requestFocus();
    optionDialog.setVisible(true);
    Object selectedValue = optionPane.getValue();
    if (selectedValue != null
     && selectedValue.equals("OK")) {
      if (searchRegExpChk != null) {
        searchRegExpChk.setSelected(true);
      }
      sourceTextField.setText(modifiedTextField.getText());
    }
    annotationEditorWindow.setVisible(true);
  }

  private void modifySearchExpression(String modification,
                                      JTextField textField) {
    if (modification == null) {
      return;
    }

    int p = textField.getCaretPosition();
    int s1 = textField.getSelectionStart();
    int s2 = textField.getSelectionEnd();
    try {
    if (modification.equals("Number")) {
      textField.setText("\\b[\\p{N}][\\p{N},.]*\\b");
    } else if (modification.equals("Person")) {
      textField.setText("\\p{Lu}\\p{L}+, \\p{Lu}\\.(?: \\p{Lu}\\.)*");
    } else if (modification.equals("Either the selection or X")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")|(?:X)", null);
    } else if (modification.equals("Once or not at all")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")?", null);
    } else if (modification.equals("Zero or more times")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")*", null);
    } else if (modification.equals("One or more times")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")+", null);
    } else if (modification.equals("Capturing group")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")", null);
    } else if (modification.equals("Non-capturing group")) {
      textField.getDocument().insertString(s1, "(?:", null);
      textField.getDocument().insertString(s2+3, ")", null);
    } else if (modification.equals("Any character")) {
      textField.getDocument().insertString(p, ".", null);
    } else if (modification.equals("The beginning of a line")) {
      textField.getDocument().insertString(p, "^", null);
    } else if (modification.equals("The end of a line")) {
      textField.getDocument().insertString(p, "$", null);
    } else if (modification.equals("Any character except Category")) {
      textField.getDocument().insertString(p, "\\P{Category}", null);
    } else if (modification.equals("Category1 and/or Category2")) {
      textField.getDocument().insertString(p, "[\\p{Category1}\\p{Category2}]", null);
    } else if (modification.equals("Category1 and Category2")) {
      textField.getDocument().insertString(p, "[\\p{Category1}&&\\p{Category2}]", null);
    } else if (modification.equals("All letters")) {
      textField.getDocument().insertString(p, "\\p{L}", null);
    } else if (modification.equals("Letter uppercase")) {
      textField.getDocument().insertString(p, "\\p{Lu}", null);
    } else if (modification.equals("Letter lowercase")) {
      textField.getDocument().insertString(p, "\\p{Ll}", null);
    } else if (modification.equals("Letter titlecase")) {
      textField.getDocument().insertString(p, "\\p{Lt}", null);
    } else if (modification.equals("Letter modifier")) {
      textField.getDocument().insertString(p, "\\p{Lm}", null);
    } else if (modification.equals("Letter other")) {
      textField.getDocument().insertString(p, "\\p{Lo}", null);
    } else if (modification.equals("All Marks")) {
      textField.getDocument().insertString(p, "\\p{M}", null);
    } else if (modification.equals("Mark nonspacing")) {
      textField.getDocument().insertString(p, "\\p{Mn}", null);
    } else if (modification.equals("Mark spacing combining")) {
      textField.getDocument().insertString(p, "\\p{Mc}", null);
    } else if (modification.equals("Mark enclosing")) {
      textField.getDocument().insertString(p, "\\p{Me}", null);
    } else if (modification.equals("All Numbers")) {
      textField.getDocument().insertString(p, "\\p{N}", null);
    } else if (modification.equals("Number decimal digit")) {
      textField.getDocument().insertString(p, "\\p{Nd}", null);
    } else if (modification.equals("Number letter")) {
      textField.getDocument().insertString(p, "\\p{Nl}", null);
    } else if (modification.equals("Number other")) {
      textField.getDocument().insertString(p, "\\p{No}", null);
    } else if (modification.equals("All separators")) {
      textField.getDocument().insertString(p, "\\p{Z}", null);
    } else if (modification.equals("Separator space")) {
      textField.getDocument().insertString(p, "\\p{Zs}", null);
    } else if (modification.equals("Separator line")) {
      textField.getDocument().insertString(p, "\\p{Zl}", null);
    } else if (modification.equals("Separator paragraph")) {
      textField.getDocument().insertString(p, "\\p{Zp}", null);
    } else if (modification.equals("All others")) {
      textField.getDocument().insertString(p, "\\p{C}", null);
    } else if (modification.equals("Other control")) {
      textField.getDocument().insertString(p, "\\p{Cc}", null);
    } else if (modification.equals("Other format")) {
      textField.getDocument().insertString(p, "\\p{Cf}", null);
    } else if (modification.equals("Other surrogate")) {
      textField.getDocument().insertString(p, "\\p{Cs}", null);
    } else if (modification.equals("Other private use")) {
      textField.getDocument().insertString(p, "\\p{Co}", null);
    } else if (modification.equals("Other not assigned")) {
      textField.getDocument().insertString(p, "\\p{Cn}", null);
    } else if (modification.equals("All punctuations")) {
      textField.getDocument().insertString(p, "\\p{P}", null);
    } else if (modification.equals("Punctuation connector")) {
      textField.getDocument().insertString(p, "\\p{Pc}", null);
    } else if (modification.equals("Punctuation dash")) {
      textField.getDocument().insertString(p, "\\p{Pd}", null);
    } else if (modification.equals("Punctuation open")) {
      textField.getDocument().insertString(p, "\\p{Ps}", null);
    } else if (modification.equals("Punctuation close")) {
      textField.getDocument().insertString(p, "\\p{Pe}", null);
    } else if (modification.equals("Punctuation initial quote")) {
      textField.getDocument().insertString(p, "\\p{Pi}", null);
    } else if (modification.equals("Punctuation final quote")) {
      textField.getDocument().insertString(p, "\\p{Pf}", null);
    } else if (modification.equals("Punctuation other")) {
      textField.getDocument().insertString(p, "\\p{Po}", null);
    } else if (modification.equals("All symbols")) {
      textField.getDocument().insertString(p, "\\p{S}", null);
    } else if (modification.equals("Symbol math")) {
      textField.getDocument().insertString(p, "\\p{Sm}", null);
    } else if (modification.equals("Symbol currency")) {
      textField.getDocument().insertString(p, "\\p{Sc}", null);
    } else if (modification.equals("Symbol modifier")) {
      textField.getDocument().insertString(p, "\\p{Sk}", null);
    } else if (modification.equals("Symbol other")) {
      textField.getDocument().insertString(p, "\\p{So}", null);
    }
    } catch (BadLocationException e) {
      // should never happend
      throw new GateRuntimeException(e);
    }
    textField.requestFocus();
  }

}
