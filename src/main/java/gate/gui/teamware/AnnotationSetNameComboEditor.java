/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts 14/02/2009
 *
 *  $Id: AnnotationSetNameComboEditor.java 17530 2014-03-04 15:57:43Z markagreenwood $
 *
 */

package gate.gui.teamware;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

/**
 * Combo box editor for annotation set names. When the text field is
 * empty, it displays the text "&lt;Default annotation set&gt;" in
 * italics. Other than that, it delegates to the underlying default
 * editor.
 */
class AnnotationSetNameComboEditor implements ComboBoxEditor, FocusListener {

  public static final String DEFAULT_SET_TEXT = "<Default annotation set>";

  private ComboBoxEditor realEditor;

  private Font normalFont;

  private Font italicFont;

  private boolean isEmpty;

  AnnotationSetNameComboEditor(ComboBoxEditor realEditor) {
    this.realEditor = realEditor;
    normalFont = realEditor.getEditorComponent().getFont();
    italicFont = normalFont.deriveFont(Font.ITALIC);
    setItem(null, false);
    realEditor.getEditorComponent().addFocusListener(this);
  }

  @Override
  public void addActionListener(ActionListener l) {
    realEditor.addActionListener(l);
  }

  @Override
  public Component getEditorComponent() {
    return realEditor.getEditorComponent();
  }

  @Override
  public Object getItem() {
    Object realItem = realEditor.getItem();
    if(isEmpty || DEFAULT_SET_TEXT.equals(realItem)) {
      return null;
    }
    else {
      return realItem;
    }
  }

  @Override
  public void removeActionListener(ActionListener l) {
    realEditor.removeActionListener(l);
  }

  @Override
  public void selectAll() {
    if(!isEmpty) {
      realEditor.selectAll();
    }
  }

  @Override
  public void setItem(Object item) {
    setItem(item, true);
  }

  /**
   * Set the current item. If updateFocus is true, call the relevant
   * FocusListener method.
   */
  private void setItem(Object item, boolean updateFocus) {
    if(item == null || "".equals(item)) {
      isEmpty = true;
      realEditor.getEditorComponent().setFont(italicFont);
      realEditor.setItem(DEFAULT_SET_TEXT);
    }
    else {
      isEmpty = false;
      realEditor.getEditorComponent().setFont(normalFont);
      realEditor.setItem(item);
    }

    // update the field properly
    if(updateFocus) {
      if(realEditor.getEditorComponent().isFocusOwner()) {
        focusGained(null);
      }
      else {
        focusLost(null);
      }
    }
  }

  // FocusListener methods

  @Override
  public void focusGained(FocusEvent e) {
    if(isEmpty) {
      JTextField field = (JTextField)realEditor.getEditorComponent();
      field.setText("");
      field.setFont(normalFont);
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    JTextField field = (JTextField)realEditor.getEditorComponent();
    if(field.getDocument().getLength() == 0
            || DEFAULT_SET_TEXT.equals(field.getText())) {
      setItem(null, false);
    }
    else {
      isEmpty = false;
    }
  }

}
