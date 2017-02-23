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
 *  $Id: InputOutputAnnotationSetsDialog.java 19660 2016-10-10 07:57:55Z markagreenwood $
 *
 */

package gate.gui.teamware;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gate.Controller;

/**
 * Dialog box to edit the lists of input and output annotation set names
 * for the "export to teamware" option.
 */
public class InputOutputAnnotationSetsDialog {

  /**
   * The controller being exported.
   */
  private Controller controller;

  private JPanel panel;

  /**
   * Editor component for the input annotation set names.
   */
  private AnnotationSetsList inputList;

  private Set<String> inputSetNames;

  /**
   * Editor component for the output annotation set names.
   */
  private AnnotationSetsList outputList;

  private Set<String> outputSetNames;

  public InputOutputAnnotationSetsDialog(Controller controller) {
    this.controller = controller;
    initGuiComponents();
  }

  /**
   * Set up the GUI.
   */
  protected void initGuiComponents() {
    panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2, 5, 5));

    // get the list of annotation set names from last time the app was
    // saved, or make an educated guess if it hasn't been saved before.
    inputSetNames = TeamwareUtils.getInputAnnotationSets(controller);
    outputSetNames = TeamwareUtils.getOutputAnnotationSets(controller);

    Set<String> likelyInputSetNames = TeamwareUtils
            .getLikelyInputAnnotationSets(controller);
    Set<String> likelyOutputSetNames = TeamwareUtils
            .getLikelyOutputAnnotationSets(controller);

    inputList = new AnnotationSetsList(likelyInputSetNames, inputSetNames);
    outputList = new AnnotationSetsList(likelyOutputSetNames, outputSetNames);

    inputList.setBorder(BorderFactory.createTitledBorder(BorderFactory
            .createEtchedBorder(), "Input annotation sets"));
    outputList.setBorder(BorderFactory.createTitledBorder(BorderFactory
            .createEtchedBorder(), "Output annotation sets"));

    panel.add(inputList);
    panel.add(outputList);
  }

  /**
   * Show the dialog. If the dialog is closed with the OK button, the
   * contents of the two lists (input and output annotation set names)
   * are persisted back to the relevant Controller features.
   * 
   * @return true if the dialog was closed with the OK button, false
   *         otherwise.
   */
  public boolean showDialog(Component parent) {
    int selectedOption = JOptionPane.showConfirmDialog(parent, panel,
            "Select input and output annotation sets",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if(selectedOption == JOptionPane.OK_OPTION) {
      inputSetNames.clear();
      for(int i = 0; i < inputList.listModel.size(); i++) {
        inputSetNames.add(inputList.listModel.get(i));
      }

      outputSetNames.clear();
      for(int i = 0; i < outputList.listModel.size(); i++) {
        outputSetNames.add(outputList.listModel.get(i));
      }

      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Panel representing the list of annotation set names for either
   * input or output.
   */
  @SuppressWarnings("serial")
  static class AnnotationSetsList extends JPanel {
    private JList<String> annotationSetsList;

    private DefaultListModel<String> listModel;

    private JComboBox<String> combo;

    private JButton addButton;

    private JButton removeButton;

    /**
     * Adds the currently selected annotation set name to the list, if
     * it is not already present.
     */
    protected class AddAction extends AbstractAction {
      AddAction() {
        super("Add");
        putValue(SHORT_DESCRIPTION, "Add the edited value to the list");
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        String selected = (String)combo.getSelectedItem();
        // find where to insert
        int index = 0;
        while(index < listModel.size()
                && NATURAL_COMPARATOR.compare(listModel.get(index),
                        selected) < 0) {
          index++;
        }
        if(index == listModel.size()) {
          // moved past the end of the list, so add there
          listModel.addElement(selected);
        }
        else {
          // add if the value is not already present
          if(NATURAL_COMPARATOR.compare(listModel.get(index), selected) != 0) {
            listModel.add(index, selected);
          }
        }
      }
    }

    /**
     * Removes the selected element(s) from the list
     */
    protected class RemoveAction extends AbstractAction {
      RemoveAction() {
        super("Remove");
        putValue(SHORT_DESCRIPTION,
                "Remove the selected value(s) from the list");
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        int[] indices = annotationSetsList.getSelectedIndices();
        Arrays.sort(indices);
        for(int i = indices.length - 1; i >= 0; i--) {
          listModel.remove(indices[i]);
        }
      }
    }

    AnnotationSetsList(Collection<String> hintSetNames,
            Collection<String> initialSetNames) {
      GridBagLayout layout = new GridBagLayout();
      setLayout(layout);
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.ipady = 2;
      c.weightx = 1.0;

      String[] hintSetNamesArray = hintSetNames.toArray(new String[hintSetNames
              .size()]);
      Arrays.sort(hintSetNamesArray, NATURAL_COMPARATOR);
      combo = new JComboBox<String>(hintSetNamesArray);
      combo.setEditable(true);
      // custom editor to handle the default annotation set.
      combo.setEditor(new AnnotationSetNameComboEditor(combo.getEditor()));

      // set up renderer
      combo.setRenderer(new AnnotationSetNameCellRenderer());

      c.fill = GridBagConstraints.HORIZONTAL;
      add(combo, c);

      // add and remove buttons
      Action addAction = new AddAction();
      Action removeAction = new RemoveAction();

      Box buttonsBox = Box.createHorizontalBox();
      buttonsBox.add(Box.createHorizontalGlue());
      addButton = new JButton(addAction);
      buttonsBox.add(addButton);
      buttonsBox.add(Box.createHorizontalStrut(5));
      removeButton = new JButton(removeAction);
      buttonsBox.add(removeButton);
      buttonsBox.add(Box.createHorizontalGlue());

      add(buttonsBox, c);

      listModel = new DefaultListModel<String>();
      String[] initialSetNamesArray = initialSetNames
              .toArray(new String[initialSetNames.size()]);
      Arrays.sort(initialSetNamesArray, NATURAL_COMPARATOR);
      for(String name : initialSetNamesArray) {
        listModel.addElement(name);
      }

      annotationSetsList = new JList<String>(listModel);
      // set up list cell renderer
      annotationSetsList.setCellRenderer(new AnnotationSetNameCellRenderer());

      c.fill = GridBagConstraints.BOTH;
      c.weighty = 1.0;
      add(new JScrollPane(annotationSetsList), c);
    }
  }

  /**
   * A comparator for strings that uses their natural order, treating
   * <code>null</code> as less than anything non-<code>null</code>.
   */
  protected static class NaturalComparator implements Comparator<String>, Serializable {

    private static final long serialVersionUID = -7376222886417999334L;

    @Override
    public int compare(String a, String b) {
      if(a == null) {
        if(b == null) {
          return 0;
        }
        else {
          return -1;
        }
      }
      else if(b == null) {
        return 1;
      }
      else {
        return a.compareTo(b);
      }
    }
  }

  private static final Comparator<String> NATURAL_COMPARATOR = new NaturalComparator();

}
