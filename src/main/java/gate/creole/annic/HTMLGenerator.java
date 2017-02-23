/*
 *  HTMLGenerator.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: HTMLGenerator.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

import java.util.*;

/**
 * This class provides methods to export the annic patterns to HTML. The
 * HTML provides a way to look at various annotations that span across
 * the found annic pattern.
 * 
 * @author niraj
 */
public class HTMLGenerator {

  /**
   * This method exports the annic pattern to HTML. The HTML provides a
   * way to look at various annotations that span across the found annic
   * pattern.
   */
  public static String generateHTMLTable(Pattern pattern) {
    PatternAnnotation[] patternAnnotations = pattern.getPatternAnnotations();

    // for each table we create a separate html code
    StringBuilder html = new StringBuilder("<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"100%\" style=\"border-collapse: collapse; border: medium none; background: #E6E6E6\">");

    // at the begining we find out number of rows we need to create for
    // this
    List<String> rows = getRowData(patternAnnotations);
    Collections.sort(rows);

    // find out the number of columns
    List<String> colPositions = getColsPositions(patternAnnotations);
    Collections.sort(colPositions, new Comparator<String>() {
      @Override
      public int compare(String a, String b) {
        int aVal = Integer.parseInt(a);
        int bVal = Integer.parseInt(b);
        return aVal - bVal;
      }
    });

    patternAnnotations = sort(patternAnnotations);

    html.append("\n").append("<tr> ")
        .append(
            "<td style=\"width: 85.25pt; border-left: medium none; border-right: 1.0pt dashed blue; ")
        .append(
            "border-top: 1.0pt dashed blue; border-bottom: 1.0pt dashed blue; padding-left: 5.4pt; ")
        .append(
            "padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm\">")
        .append("<p class=\"MsoNormal\" align=\"center\">Pattern Text : </td>");
    int endPos = patternAnnotations[0].getStartOffset();
    int startPos = 0;
    for(int j = 1; j < colPositions.size(); j++) {
      startPos = endPos;
      endPos = Integer.parseInt(colPositions.get(j));
      String text = pattern.getPatternText(startPos, endPos);
      html.append("\n")
          .append("<td style=\"border: 1.0pt dashed blue;\" align=\"center\">")
          .append(text).append("</td>");
    }

    // and now for each type we create a new Row
    for(int j = 0; j < rows.size(); j++) {

      // first column is the annotation Type
      html.append("\n").append("<tr width=\"100%\" height=\"19\"> <td>")
          .append(rows.get(j)).append("</td>");
      List<PatternAnnotation> rowAnnotations = findOutAnnotationsOfType(patternAnnotations,
              rows.get(j));

      int columnsDrawn = 0;
      for(int k = 0; k < rowAnnotations.size(); k++) {
        // for each annotation we will create a column
        PatternAnnotation annot = rowAnnotations.get(k);

        // we may need to draw few columns before this annotations
        html.append("\n").append(columnsToDraw(patternAnnotations, rowAnnotations, k,
                        colPositions));
        columnsDrawn += noOfColumnsToDraw;

        // now lets find out the annotations at the same starting
        // positions
        List<PatternAnnotation> tempList = new ArrayList<PatternAnnotation>();
        tempList.add(annot);

        int maxEndOffset = annot.getEndOffset();
        int m = k + 1;
        for(; m < rowAnnotations.size(); m++) {
          PatternAnnotation annot1 = rowAnnotations.get(m);
          if(annot.getStartOffset() == annot1.getStartOffset()) {
            tempList.add(annot1);
            if(annot1.getEndOffset() > maxEndOffset) {
              maxEndOffset = annot1.getEndOffset();
            }
          }
          else {
            m--;
            break;
          }
        }

        if(k != m) {
          k = m;
        }

        int colSpan = getColSpan(annot.getStartOffset(), maxEndOffset,
                colPositions);

        if(colSpan > 0) {
          html.append("\n")
              .append(
                  "<td style=\"border: 1.0pt dashed blue;\" align=\"center\" colspan=\"")
              .append(colSpan + "\" <p align=\"center\">");
          columnsDrawn += colSpan;
        }
        else {
          html.append("\n").append(
              "<td style=\"border: 1.0pt dashed blue;\" align=\"center\"> <p align=\"center\">");
          columnsDrawn += 1;
        }

        for(m = 0; m < tempList.size(); m++) {
          html.append(addFeatures(tempList.get(m).getFeatures()))
              .append("<br>");
        }

        html.append("</td>");
      }

      // now see how many columns are yet to be drawn
      for(int k = 0; k < colPositions.size() - columnsDrawn; k++) {
        html.append("\n")
            .append("<td style=\"border: 1.0pt dashed blue;\">&nbsp;</td>");
      }
      html.append("\n").append("</tr>");
    }

    // and finally we need to add all the annotations at the end
    html.append("\n").append("</table>");
    return html.toString();
  }

  /**
   * This method is used for sorting the pattern annotations.
   */
  private static PatternAnnotation[] sort(PatternAnnotation[] annots) {

    for(int i = 0; i < annots.length; i++) {
      for(int j = 0; j < annots.length - 1; j++) {
        if(annots[j].getStartOffset() > annots[j + 1].getStartOffset()) {
          PatternAnnotation temp = annots[j + 1];
          annots[j + 1] = annots[j];
          annots[j] = temp;
          break;
        }

        if(annots[j].getEndOffset() > annots[j + 1].getEndOffset()) {
          PatternAnnotation temp = annots[j + 1];
          annots[j + 1] = annots[j];
          annots[j] = temp;
          break;
        }
      }
    }
    return annots;
  }

  /**
   * Number of columns to draw in the html table.
   */
  private static int noOfColumnsToDraw = 0;

  private static String columnsToDraw(
          PatternAnnotation[] currentTableAnnotations,
          List<PatternAnnotation> rowAnnotations, int currentPos, List<String> colPositions) {

    // if currentPos == 0
    // this is the first annotation in this row
    int startPoint = 0;
    if(currentPos == 0) {
      startPoint = currentTableAnnotations[0]
              .getStartOffset();
    }
    else {
      startPoint = rowAnnotations.get(currentPos - 1)
              .getEndOffset();
    }

    noOfColumnsToDraw = noOfColumnsToDraw(startPoint,
            rowAnnotations.get(currentPos)
                    .getStartOffset(), colPositions);
    StringBuilder html = new StringBuilder();
    for(int i = 0; i < noOfColumnsToDraw; i++) {
      html.append("\n").append("<td style=\"border: 1.0pt dashed blue;\">&nbsp;</td>");
    }
    return html.toString();
  }

  private static int noOfColumnsToDraw(int start, int end,
          List<String> colPositions) {
    if(start == end) return 0;

    int counter = 0;
    int i = 0;
    for(; i < colPositions.size(); i++) {
      if(Integer.parseInt(colPositions.get(i)) == start) {
        i++;
        break;
      }
    }

    if(i == colPositions.size() || i < 0) i = 0;

    for(; i < colPositions.size(); i++) {
      if(end == Integer.parseInt(colPositions.get(i))) {
        counter++;
        break;
      }
      counter++;
    }

    return counter;
  }

  /**
   * From given an array of pattern annotations, this method finds out
   * the annotations of the given type.
   */
  private static List<PatternAnnotation> findOutAnnotationsOfType(
          PatternAnnotation[] annotations, String type) {
    List<PatternAnnotation> annots = new ArrayList<PatternAnnotation>();
    for(int i = 0; i < annotations.length; i++) {
      if(annotations[i].getType().equals(type)) {
        annots.add(annotations[i]);
      }
    }
    return annots;
  }

  private static int getColSpan(int startOffset, int endOffset,
          List<String> colPositions) {
    // given startOffset and endOffset
    // we need to find out how many columns this particular annotations
    // needs to span
    int counter = 0;

    // lets find out the starting position
    int i = 0;
    for(; i < colPositions.size(); i++) {
      if(Integer.parseInt(colPositions.get(i)) == startOffset) {
        i++;
        break;
      }
    }

    if(i == colPositions.size() || i < 0) {
      // because this is the first annotation, it cannot satisfy the
      // condition startOffset > colVal
      // and therefore it simply reached here
      // we will set it back to the 0
      i = 0;
    }

    // now we need to start the counter until we find out the end
    // position
    // in the col Positions
    for(; i < colPositions.size(); i++) {
      if(endOffset == Integer.parseInt(colPositions.get(i))) {
        counter++;
        break;
      }
      counter++;
    }
    return counter;
  }

  private static List<String> getColsPositions(PatternAnnotation[] annotations) {
    // the logic is:
    // find out the unique number of endOffsets
    List<String> offsets = new ArrayList<String>();
    for(int i = 0; i < annotations.length; i++) {
      int endOffset = annotations[i].getEndOffset();
      int startOffset = annotations[i].getStartOffset();

      if(offsets.contains("" + endOffset)) {
        // do nothing
      }
      else {
        offsets.add("" + endOffset);
      }

      if(offsets.contains("" + startOffset)) {
        // do nothing
      }
      else {
        offsets.add("" + startOffset);
      }

    }
    return offsets;
  }

  /**
   * This method return the unique rows. Each row refers to a different
   * annotation type
   * 
   * @param annotations
   * @return a list of string objects referring to the annotation types
   */
  private static List<String> getRowData(PatternAnnotation[] annotations) {
    List<String> types = new ArrayList<String>();
    for(int i = 0; i < annotations.length; i++) {
      String type = annotations[i].getType();
      if(types.contains(type))
        continue;
      else types.add(type);
    }
    return types;
  }

  // this method takes the features of a particular annotations
  // and returns the equivalent html code
  private static String addFeatures(Map<String, String> features) {
    StringBuilder html = new StringBuilder("<select size=\"1\" >");
    for (Map.Entry<String,String> feature : features.entrySet()) {
      html.append("\n").append("<option>").append(feature.getKey()).append(" = \"").append(feature.getValue()).append("\"</option>");
    }
    html.append("\n").append("</select>");
    return html.toString();
  }
}
