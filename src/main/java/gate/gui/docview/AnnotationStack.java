/*
 *  Copyright (c) 1998-2009, The University of Sheffield and Ontotext.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Thomas Heitz - 7 July 2009
 *
 *  $Id$
 */

package gate.gui.docview;

import gate.FeatureMap;
import gate.Node;
import gate.annotation.NodeImpl;
import gate.util.Strings;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Stack of annotations in a JPanel.
 * <br><br>
 * To use, respect this order:<br><code>
 * AnnotationStack stackPanel = new AnnotationStack(...);<br>
 * stackPanel.set...(...);<br>
 * stackPanel.clearAllRows();<br>
 * stackPanel.addRow(...);<br>
 * stackPanel.addAnnotation(...);<br>
 * stackPanel.drawStack();</code>
 */
@SuppressWarnings("serial")
public class AnnotationStack extends JPanel {

  public AnnotationStack() {
    super();
    init();
  }

  /**
   * @param maxTextLength maximum number of characters for the text,
   * if too long an ellipsis is added in the middle
   * @param maxFeatureValueLength maximum number of characters
   *  for a feature value
   */
  public AnnotationStack(int maxTextLength, int maxFeatureValueLength) {
    super();
    this.maxTextLength = maxTextLength;
    this.maxFeatureValueLength = maxFeatureValueLength;
    init();
  }

  void init() {
    setLayout(new GridBagLayout());
    setOpaque(true);
    setBackground(Color.WHITE);
    stackRows = new ArrayList<StackRow>();
    textMouseListener = new StackMouseListener();
    headerMouseListener = new StackMouseListener();
    annotationMouseListener = new StackMouseListener();
  }

  /**
   * Add a row to the annotation stack.
   *
   * @param set set name for the annotation, may be null
   * @param type annotation type
   * @param feature feature name, may be null
   * @param lastColumnButton button at the end of the column, may be null
   * @param shortcut replace the header of the row, may be null
   * @param crop how to crop the text for the annotation if too long, one of
   *   {@link #CROP_START}, {@link #CROP_MIDDLE} or {@link #CROP_END}
   */
  public void addRow(String set, String type, String feature,
                     JButton lastColumnButton, String shortcut, int crop) {
    stackRows.add(
      new StackRow(set, type, feature, lastColumnButton, shortcut, crop));
  }

  /**
   * Add an annotation to the current stack row.
   *
   * @param startOffset document offset where starts the annotation
   * @param endOffset document offset where ends the annotation
   * @param type annotation type
   * @param features annotation features map
   */
  public void addAnnotation(int startOffset, int endOffset,
                            String type, FeatureMap features) {
    stackRows.get(stackRows.size()-1).addAnnotation(
      StackAnnotation.createAnnotation(startOffset, endOffset, type, features));
  }

  /**
   * Add an annotation to the current stack row.
   *
   * @param annotation annotation to add to the current stack row
   */
  public void addAnnotation(gate.Annotation annotation) {
    stackRows.get(stackRows.size()-1).addAnnotation(
      StackAnnotation.createAnnotation(annotation));
  }

  /**
   * Clear all rows in the stack. To be called before adding the first row.
   */
  public void clearAllRows() {
    stackRows.clear();
  }

  /**
   * Draw the annotation stack in a JPanel with a GridBagLayout.
   */
  public void drawStack() {

    // clear the panel
    removeAll();

    boolean textTooLong = text.length() > maxTextLength;
    int upperBound = text.length() - (maxTextLength/2);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.BOTH;

    /**********************
     * First row of text *
     *********************/

    gbc.gridwidth = 1;
    gbc.insets = new java.awt.Insets(10, 10, 10, 10);
    JLabel labelTitle = new JLabel("Context");
    labelTitle.setOpaque(true);
    labelTitle.setBackground(Color.WHITE);
    labelTitle.setBorder(new CompoundBorder(
      new EtchedBorder(EtchedBorder.LOWERED,
        new Color(250, 250, 250), new Color(250, 250, 250).darker()),
      new EmptyBorder(new Insets(0, 2, 0, 2))));
    labelTitle.setToolTipText("Expression and its context.");
    add(labelTitle, gbc);
    gbc.insets = new java.awt.Insets(10, 0, 10, 0);

    int expressionStart = contextBeforeSize;
    int expressionEnd = text.length() - contextAfterSize;

    // for each character
    for (int charNum = 0; charNum < text.length(); charNum++) {

      gbc.gridx = charNum + 1;
      if (textTooLong) {
        if (charNum == maxTextLength/2) {
          // add ellipsis dots in case of a too long text displayed
          add(new JLabel("..."), gbc);
          // skip the middle part of the text if too long
          charNum = upperBound + 1;
          continue;
        } else if (charNum > upperBound) {
          gbc.gridx -= upperBound - (maxTextLength/2) + 1;
        }
      }

      // set the text and color of the feature value
      JLabel label = new JLabel(text.substring(charNum, charNum+1));
      if (charNum >= expressionStart && charNum < expressionEnd) {
        // this part is matched by the pattern, color it
        label.setBackground(new Color(240, 201, 184));
      } else {
        // this part is the context, no color
        label.setBackground(Color.WHITE);
      }
      label.setOpaque(true);

      // get the word from which belongs the current character charNum
      int start = text.lastIndexOf(" ", charNum);
      int end = text.indexOf(" ", charNum);
      String word = text.substring(
        (start == -1) ? 0 : start,
        (end == -1) ? text.length() : end);
      // add a mouse listener that modify the query
      label.addMouseListener(textMouseListener.createListener(word));
      add(label, gbc);
    }

      /************************************
       * Subsequent rows with annotations *
       ************************************/

    // for each row to display
    for (StackRow stackRow : stackRows) {
      String type = stackRow.getType();
      String feature = stackRow.getFeature();
      if (feature == null) { feature = ""; }
      String shortcut = stackRow.getShortcut();
      if (shortcut == null) { shortcut = ""; }

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.gridwidth = 1;
      gbc.insets = new Insets(0, 0, 3, 0);

      // add the header of the row
      JLabel annotationTypeAndFeature = new JLabel();
      String typeAndFeature = type + (feature.equals("") ? "" : ".") + feature;
      annotationTypeAndFeature.setText(!shortcut.equals("") ?
        shortcut : stackRow.getSet() != null ?
          stackRow.getSet() + "#" + typeAndFeature : typeAndFeature);
      annotationTypeAndFeature.setOpaque(true);
      annotationTypeAndFeature.setBackground(Color.WHITE);
      annotationTypeAndFeature.setBorder(new CompoundBorder(
        new EtchedBorder(EtchedBorder.LOWERED,
          new Color(250, 250, 250), new Color(250, 250, 250).darker()),
        new EmptyBorder(new Insets(0, 2, 0, 2))));
      if (feature.equals("")) {
        annotationTypeAndFeature.addMouseListener(
          headerMouseListener.createListener(type));
      } else {
        annotationTypeAndFeature.addMouseListener(
          headerMouseListener.createListener(type, feature));
      }
      gbc.insets = new java.awt.Insets(0, 10, 3, 10);
      add(annotationTypeAndFeature, gbc);
      gbc.insets = new java.awt.Insets(0, 0, 3, 0);

      // add all annotations for this row
      HashMap<Integer,TreeSet<Integer>> gridSet =
        new HashMap<Integer,TreeSet<Integer>>();
      int gridyMax = gbc.gridy;
      for(StackAnnotation ann : stackRow.getAnnotations()) {
        gbc.gridx = ann.getStartNode().getOffset().intValue()
                  - expressionStartOffset + contextBeforeSize + 1;
        gbc.gridwidth = ann.getEndNode().getOffset().intValue()
                      - ann.getStartNode().getOffset().intValue();
        if (gbc.gridx == 0) {
          // column 0 is already the row header
          gbc.gridwidth -= 1;
          gbc.gridx = 1;
        } else if (gbc.gridx < 0) {
          // annotation starts before displayed text
          gbc.gridwidth += gbc.gridx - 1;
          gbc.gridx = 1;
        }
        if (gbc.gridx + gbc.gridwidth > text.length()) {
          // annotation ends after displayed text
          gbc.gridwidth = text.length() - gbc.gridx + 1;
        }
        if(textTooLong) {
          if(gbc.gridx > (upperBound + 1)) {
            // x starts after the hidden middle part
            gbc.gridx -= upperBound - (maxTextLength / 2) + 1;
          }
          else if(gbc.gridx > (maxTextLength / 2)) {
            // x starts in the hidden middle part
            if(gbc.gridx + gbc.gridwidth <= (upperBound + 3)) {
              // x ends in the hidden middle part
              continue; // skip the middle part of the text
            }
            else {
              // x ends after the hidden middle part
              gbc.gridwidth -= upperBound - gbc.gridx + 2;
              gbc.gridx = (maxTextLength / 2) + 2;
            }
          }
          else {
            // x starts before the hidden middle part
            if(gbc.gridx + gbc.gridwidth < (maxTextLength / 2)) {
              // x ends before the hidden middle part
              // do nothing
            }
            else if(gbc.gridx + gbc.gridwidth < upperBound) {
              // x ends in the hidden middle part
              gbc.gridwidth = (maxTextLength / 2) - gbc.gridx + 1;
            }
            else {
              // x ends after the hidden middle part
              gbc.gridwidth -= upperBound - (maxTextLength / 2) + 1;
            }
          }
        }
        if(gbc.gridwidth == 0) {
          gbc.gridwidth = 1;
        }

        JLabel label = new JLabel();
        Object object = ann.getFeatures().get(feature);
        String value = (object == null) ? " " : Strings.toString(object);
        if(value.length() > maxFeatureValueLength) {
          // show the full text in the tooltip
          label.setToolTipText((value.length() > 500) ?
            "<html><textarea rows=\"30\" cols=\"40\" readonly=\"readonly\">"
            + value.replaceAll("(.{50,60})\\b", "$1\n") + "</textarea></html>" :
            ((value.length() > 100) ?
              "<html><table width=\"500\" border=\"0\" cellspacing=\"0\">"
                + "<tr><td>" + value.replaceAll("\n", "<br>")
                + "</td></tr></table></html>" :
              value));
          if(stackRow.getCrop() == CROP_START) {
            value = "..." + value.substring(
              value.length() - maxFeatureValueLength - 1);
          }
          else if(stackRow.getCrop() == CROP_END) {
            value = value.substring(0, maxFeatureValueLength - 2) + "...";
          }
          else {// cut in the middle
            value = value.substring(0, maxFeatureValueLength / 2) + "..."
              + value.substring(value.length() - (maxFeatureValueLength / 2));
          }
        }
        label.setText(value);
        label.setBackground(AnnotationSetsView.getColor(stackRow.getSet(),ann.getType()));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label.setOpaque(true);

        label.addMouseListener(annotationMouseListener.createListener(
          stackRow.getSet(), type, String.valueOf(ann.getId())));

        // show the feature values in the tooltip
        if (!ann.getFeatures().isEmpty()) {
          String width = (Strings.toString(ann.getFeatures()).length() > 100) ?
            "500" : "100%";
          String toolTip = "<html><table width=\"" + width
            + "\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\">";
          Color color = (Color) UIManager.get("ToolTip.background");
          float[] hsb = Color.RGBtoHSB(
            color.getRed(), color.getGreen(), color.getBlue(), null);
          color = Color.getHSBColor(hsb[0], hsb[1],
            Math.max(0f, hsb[2] - hsb[2]*0.075f)); // darken the color
          String hexColor = Integer.toHexString(color.getRed()) +
            Integer.toHexString(color.getGreen()) +
            Integer.toHexString(color.getBlue());
          boolean odd = false; // alternate background color every other row
          
          List<Object> features = new ArrayList<Object>(ann.getFeatures().keySet());
          //sort the features into alphabetical order
          Collections.sort(features, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
              return o1.toString().compareToIgnoreCase(o2.toString());
            }
          });
          
          for (Object key : features) {
            String fv = Strings.toString(ann.getFeatures().get(key));
            toolTip +="<tr align=\"left\""
              + (odd?" bgcolor=\"#"+hexColor+"\"":"")+"><td><strong>"
              + key + "</strong></td><td>"
              + ((fv.length() > 500) ?
              "<textarea rows=\"20\" cols=\"40\" cellspacing=\"0\">"
                + StringEscapeUtils.escapeHtml(fv.replaceAll("(.{50,60})\\b", "$1\n"))
                + "</textarea>" :
              StringEscapeUtils.escapeHtml(fv).replaceAll("\n", "<br>"))
              + "</td></tr>";
            odd = !odd;
          }
          label.setToolTipText(toolTip + "</table></html>");
        } else {
          label.setToolTipText("No features.");
        }

        if(!feature.equals("")) {
          label.addMouseListener(annotationMouseListener.createListener(
            stackRow.getSet(), type, feature, Strings.toString(
              ann.getFeatures().get(feature)), String.valueOf(ann.getId())));
        }
        // find the first empty row span for this annotation
        int oldGridy = gbc.gridy;
        for(int y = oldGridy; y <= (gridyMax + 1); y++) {
          // for each cell of this row where spans the annotation
          boolean xSpanIsEmpty = true;
          for(int x = gbc.gridx;
              (x < (gbc.gridx + gbc.gridwidth)) && xSpanIsEmpty; x++) {
            xSpanIsEmpty = !(gridSet.containsKey(x)
              && gridSet.get(x).contains(y));
          }
          if(xSpanIsEmpty) {
            gbc.gridy = y;
            break;
          }
        }
        // save the column x and row y of the current value
        TreeSet<Integer> ts;
        for(int x = gbc.gridx; x < (gbc.gridx + gbc.gridwidth); x++) {
          ts = gridSet.get(x);
          if(ts == null) {
            ts = new TreeSet<Integer>();
          }
          ts.add(gbc.gridy);
          gridSet.put(x, ts);
        }
        add(label, gbc);
        gridyMax = Math.max(gridyMax, gbc.gridy);
        gbc.gridy = oldGridy;
      }

      // add a button at the end of the row
      gbc.gridwidth = 1;
      if (stackRow.getLastColumnButton() != null) {
        // last cell of the row
        gbc.gridx = Math.min(text.length(), maxTextLength) + 1;
        gbc.insets = new Insets(0, 10, 3, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(stackRow.getLastColumnButton(), gbc);
        gbc.insets = new Insets(0, 0, 3, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
      }

      // set the new gridy to the maximum row we put a value
      gbc.gridy = gridyMax;
    }

    if (lastRowButton != null) {
      // add a configuration button on the last row
      gbc.insets = new java.awt.Insets(0, 10, 0, 10);
      gbc.gridx = 0;
      gbc.gridy++;
      add(lastRowButton, gbc);
    }

    // add an empty cell that takes all remaining space to
    // align the visible cells at the top-left corner
    gbc.gridy++;
    gbc.gridx = Math.min(text.length(), maxTextLength) + 1;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.gridheight = GridBagConstraints.REMAINDER;
    gbc.weightx = 1;
    gbc.weighty = 1;
    add(new JLabel(""), gbc);

    validate();
    updateUI();
  }

  /**
   * Extension of a MouseInputAdapter that adds a method
   * to create new Listeners from it.<br>
   * You must overriden the createListener method.
   */
  public static class StackMouseListener extends MouseInputAdapter {
    /**
     * There is 3 cases for the parameters of createListener:
     * <ol>
     * <li>first line of text -> createListener(word)
     * <li>first column, header -> createListener(type),
     *   createListener(type, feature)
     * <li>annotation -> createListener(set, type, annotationId),
     *   createListener(set, type, feature, value, annotationId)
     * </ol>
     * @param parameters see above
     * @return a MouseInputAdapter for the text, header or annotation
     */
    public MouseInputAdapter createListener(String... parameters) {
      return null;
    }
  }

  /**
   * Annotation that doesn't belong to an annotation set
   * and with id always equal to -1.<br>
   * Allows to create an annotation without document, nodes, annotation set,
   * and keep compatibility with gate.Annotation.
   * <br>
   * This class is only for AnnotationStack internal use
   * as it won't work with most of the methods that use gate.Annotation.
   */
  private static class StackAnnotation extends gate.annotation.AnnotationImpl {
    StackAnnotation(Integer id, Node start, Node end, String type,
                         FeatureMap features) {
      super(id, start, end, type, features);
    }
    static StackAnnotation createAnnotation(int startOffset,
                  int endOffset, String type, FeatureMap features) {
      Node startNode = new NodeImpl(-1, (long) startOffset);
      Node endNode = new NodeImpl(-1, (long) endOffset);
      return new StackAnnotation(-1, startNode, endNode, type, features);
    }
    static StackAnnotation createAnnotation(gate.Annotation annotation) {
      return new StackAnnotation(annotation.getId(), annotation.getStartNode(),
        annotation.getEndNode(), annotation.getType(), annotation.getFeatures());
    }
  }

  /**
   * A row of annotations in the stack.
   */
  class StackRow {
    StackRow(String set, String type, String feature,
             JButton lastColumnButton, String shortcut, int crop) {
      this.set = set;
      this.type = type;
      this.feature = feature;
      this.annotations = new HashSet<StackAnnotation>();
      this.lastColumnButton = lastColumnButton;
      this.shortcut = shortcut;
      this.crop = crop;
    }

    public String getSet() {
      return set;
    }
    public String getType() {
      return type;
    }
    public String getFeature() {
      return feature;
    }
    public Set<StackAnnotation> getAnnotations() {
      return annotations;
    }
    public JButton getLastColumnButton() {
      return lastColumnButton;
    }
    public String getShortcut() {
      return shortcut;
    }
    public int getCrop() {
      return crop;
    }
    public void addAnnotation(StackAnnotation annotation) {
      annotations.add(annotation);
    }

    String set;
    String type;
    String feature;
    Set<StackAnnotation> annotations;
    JButton lastColumnButton;
    String shortcut;
    int crop;
  }

  public void setLastRowButton(JButton lastRowButton) {
    this.lastRowButton = lastRowButton;
  }

  /** @param text first line of text that contains the expression
   *  and its context */
  public void setText(String text) {
    this.text = text;
  }

  /** @param expressionStartOffset document offset where starts the expression */
  public void setExpressionStartOffset(int expressionStartOffset) {
    this.expressionStartOffset = expressionStartOffset;
  }

  /** @param expressionEndOffset document offset where ends the expression */
  public void setExpressionEndOffset(int expressionEndOffset) {
    this.expressionEndOffset = expressionEndOffset;
  }

  /** @param contextBeforeSize number of characters before the expression */
  public void setContextBeforeSize(int contextBeforeSize) {
    this.contextBeforeSize = contextBeforeSize;
  }

  /** @param contextAfterSize number of characters after the expression */
  public void setContextAfterSize(int contextAfterSize) {
    this.contextAfterSize = contextAfterSize;
  }

  /** @param expressionTooltip optional tooltip for the expression */
  public void setExpressionTooltip(String expressionTooltip) {
    this.expressionTooltip = expressionTooltip;
  }

  /** @param textMouseListener optional listener for the first line of text */
  public void setTextMouseListener(StackMouseListener textMouseListener) {
    this.textMouseListener = textMouseListener;
  }

  /** @param headerMouseListener optional listener for the first column */
  public void setHeaderMouseListener(StackMouseListener headerMouseListener) {
    this.headerMouseListener = headerMouseListener;
  }

  /** @param annotationMouseListener optional listener for the annotations */
  public void setAnnotationMouseListener(StackMouseListener annotationMouseListener) {
    this.annotationMouseListener = annotationMouseListener;
  }

  /** rows of annotations that are displayed in the stack*/
  ArrayList<StackRow> stackRows;
  /** maximum number of characters for the text,
   * if too long an ellipsis is added in the middle */
  int maxTextLength = 150;
  /** maximum number of characters for a feature value */
  int maxFeatureValueLength = 30;
  JButton lastRowButton;
  String text = "";
  int expressionStartOffset = 0;
  int expressionEndOffset = 0;
  /** number of characters before the expression */
  int contextBeforeSize = 10;
  /** number of characters after the expression */
  int contextAfterSize = 10;
  String expressionTooltip = "";
  StackMouseListener textMouseListener;
  StackMouseListener headerMouseListener;
  StackMouseListener annotationMouseListener;
  public final static int CROP_START = 0;
  public final static int CROP_MIDDLE = 1;
  public final static int CROP_END = 2;
}
