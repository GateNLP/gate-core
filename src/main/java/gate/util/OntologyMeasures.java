/**
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 * Thomas Heitz - 09/06/2010
 *
 *  $Id$
 */

package gate.util;

import gate.Annotation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

/**
 * Modified version of Precision and Recall called BDM that takes into
 * account the distance of two concepts in an ontology.
 */
public class OntologyMeasures {

  public OntologyMeasures() {
    // empty constructor
  }

  /**
   * Constructor to be used when you have a collection of OntologyMeasures
   * and want to consider it as only one OntologyMeasures.
   * Then you can only use the methods getPrecision/Recall/FMeasure...().
   * @param measures collection to be regrouped in one OntologyMeasures
   */
  public OntologyMeasures(Collection<OntologyMeasures> measures) {
    Map<String, List<AnnotationDiffer>> differsByTypeMap =
      new HashMap<String, List<AnnotationDiffer>>();
    for (OntologyMeasures measure : measures) {
      for (Map.Entry<String, Float> entry : measure.bdmByTypeMap.entrySet()) {
        float previousBdm = 0;
        if (bdmByTypeMap.containsKey(entry.getKey())) {
          previousBdm = bdmByTypeMap.get(entry.getKey());
        }
        // set the bdmByTypeMap to be the sum of those in the collection
        bdmByTypeMap.put(entry.getKey(), previousBdm + entry.getValue());
      }
      for (Map.Entry<String, AnnotationDiffer> entry :
             measure.differByTypeMap.entrySet()) {
        List<AnnotationDiffer> differs = differsByTypeMap.get(entry.getKey());
        if (differs == null) {
          differs = new ArrayList<AnnotationDiffer>();
        }
        differs.add(entry.getValue());
        differsByTypeMap.put(entry.getKey(), differs);
      }
    }
    // combine the list of AnnotationDiffer for each type
    for (Map.Entry<String, List<AnnotationDiffer>> entry :
           differsByTypeMap.entrySet()) {
      differByTypeMap.put(entry.getKey(),
        new AnnotationDiffer(entry.getValue()));
    }
  }

  /**
   * For a document get the annotation differs that contain the type to compare
   * and the annotation differs that may have miscategorized annotations
   * for this type. Then we try to find miscategorized types that are close
   * enough from the main type and use their BDM value to get an augmented
   * precision, recall and fscore.
   *
   * @param differs annotation differ for the type and for possible
   * miscategorized types.
   */
  public void calculateBdm(Collection<AnnotationDiffer> differs) {

    if (bdmByConceptsMap == null) {
      // load BDM file with scores for each concept/annotation type pair
      bdmByConceptsMap = read(bdmFileUrl); // read the bdm scores
    }

    // calculate BDM from the spurious and missing annotations
    Set<Annotation> unpairedResponseAnnotations = new HashSet<Annotation>();
    Set<Annotation> unpairedKeyAnnotations;

    // will use the whole spurious annotations as the second set to compare
    for (AnnotationDiffer differ : differs) {
      unpairedResponseAnnotations.addAll(
        differ.getAnnotationsOfType(AnnotationDiffer.SPURIOUS_TYPE));
    }

    bdmByTypeMap.clear();

    for (AnnotationDiffer differ : differs) {
      unpairedKeyAnnotations = differ.getAnnotationsOfType(
        AnnotationDiffer.MISSING_TYPE);
      if (!bdmByTypeMap.containsKey(differ.getAnnotationType())) {
        bdmByTypeMap.put(differ.getAnnotationType(), 0f);
      }

      // use the missing annotations as the first set to compare
      for (Annotation unpairedKeyAnnotation : unpairedKeyAnnotations) {
        String type = unpairedKeyAnnotation.getType();
//        Out.prln("unpairedKeyAnnotation: " + unpairedKeyAnnotation.toString());
        Iterator<Annotation> iterator = unpairedResponseAnnotations.iterator();

        // use the spurious annotations as the second set to compare
        while (iterator.hasNext()) {
          Annotation unpairedResponseAnnotation = iterator.next();
//          Out.prln("unpairedResponsAnnotation: "
//            + unpairedResponseAnnotation.toString());
          float bdm = 0;

          // annotations have the same start and end offsets
          if (unpairedKeyAnnotation.coextensive(unpairedResponseAnnotation)) {

            // compare both features values with BDM pairs
            if (differ.getSignificantFeaturesSet() != null) {
              if (!type.equals(unpairedResponseAnnotation.getType())) {
                continue; // types must be the same
              }
              for (Object feature : differ.getSignificantFeaturesSet()) {
                if (unpairedKeyAnnotation.getFeatures() == null
                 || unpairedResponseAnnotation.getFeatures() == null) {
                  continue;
                }
//                Out.prln("Feature: " + feature);
                String keyLabel = (String)
                  unpairedKeyAnnotation.getFeatures().get(feature);
//                Out.prln("KeyLabel: " + keyLabel);
                String responseLabel = (String)
                  unpairedResponseAnnotation.getFeatures().get(feature);
//                Out.prln("ResponseLabel: " + responseLabel);
                if (keyLabel == null || responseLabel == null) {
                  // do nothing
                } else if (bdmByConceptsMap.containsKey(
                                              keyLabel + ", " + responseLabel)) {
                  bdm += bdmByConceptsMap.get(keyLabel + ", " + responseLabel);
                } else if (bdmByConceptsMap.containsKey(
                                              responseLabel + ", " + keyLabel)) {
                  bdm += bdmByConceptsMap.get(responseLabel + ", " + keyLabel);
                }
              }
              bdm = bdm / differ.getSignificantFeaturesSet().size();

            } else { // compare both types with BDM pairs
              if (bdmByConceptsMap.containsKey(
                    type + ',' + unpairedResponseAnnotation.getType())) {
                bdm = bdmByConceptsMap.get(
                    type + ',' + unpairedResponseAnnotation.getType());
              } else if (bdmByConceptsMap.containsKey(
                           unpairedResponseAnnotation.getType() + ", " + type)) {
                bdm = bdmByConceptsMap.get(
                           unpairedResponseAnnotation.getType() + ", " + type);
              }
            }
            if (bdm > 0) {
              bdmByTypeMap.put(type, bdmByTypeMap.get(type) + bdm);
              iterator.remove();
//              Out.prln("BDM: " + bdmByTypeMap.get(type));
            }
          }
        }
      }
    }

    differByTypeMap.clear();
    Map<String, List<AnnotationDiffer>> differsByTypeMap =
      new HashMap<String, List<AnnotationDiffer>>();

    for (AnnotationDiffer differ : differs) {
      // we consider that all annotations in AnnotationDiffer are the same type
      String type = differ.getAnnotationType();
      List<AnnotationDiffer> differsType = differsByTypeMap.get(type);
      if (differsType == null) {
        differsType = new ArrayList<AnnotationDiffer>();
      }
      differsType.add(differ);
      differsByTypeMap.put(type, differsType);
    }

    // combine the list of AnnotationDiffer for each type
    for (Map.Entry<String, List<AnnotationDiffer>> entry :
          differsByTypeMap.entrySet()) {
      differByTypeMap.put(entry.getKey(),
        new AnnotationDiffer(entry.getValue()));
    }
  }

  /**
   * AP = (sum of BDMs for BDM-matching pair spurious/missing + Correct)
   *    / (Correct + Spurious)
   * @param type annotation type
   * @return strict precision with BDM correction
   */
  public double getPrecisionStrictBdm(String type) {
    AnnotationDiffer differ = differByTypeMap.get(type);
    if (differ.getCorrectMatches() + differ.getSpurious() == 0) {
      return 1.0;
    }
    return (bdmByTypeMap.get(type) + differ.getCorrectMatches())
         / (differ.getCorrectMatches() + differ.getSpurious());
  }

  public double getPrecisionStrictBdm() {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getPrecisionStrictBdm(type);
    }
    return result / differByTypeMap.size();
  }

  public double getRecallStrictBdm(String type) {
    AnnotationDiffer differ = differByTypeMap.get(type);
    if (differ.getCorrectMatches() + differ.getMissing() == 0) {
      return 1.0;
    }
    return (bdmByTypeMap.get(type) + differ.getCorrectMatches())
         / (differ.getCorrectMatches() + differ.getMissing());
  }

  public double getRecallStrictBdm() {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getRecallStrictBdm(type);
    }
    return result / differByTypeMap.size();
  }

  public double getFMeasureStrictBdm(String type, double beta) {
    double precision = getPrecisionStrictBdm(type);
    double recall = getRecallStrictBdm(type);
    double betaSq = beta * beta;
    double answer = ((betaSq + 1) * precision * recall)
                  / (betaSq * precision + recall);
    if(Double.isNaN(answer)) answer = 0.0;
    return answer;
  }

  public double getFMeasureStrictBdm(double beta) {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getFMeasureStrictBdm(type, beta);
    }
    return result / differByTypeMap.size();
  }

  public double getPrecisionLenientBdm(String type) {
    AnnotationDiffer differ = differByTypeMap.get(type);
    if (differ.getCorrectMatches() + differ.getSpurious() == 0) {
      return 1.0;
    }
    return (bdmByTypeMap.get(type) + differ.getCorrectMatches()
          + differ.getPartiallyCorrectMatches())
         / (differ.getCorrectMatches() + differ.getSpurious());
  }

  public double getPrecisionLenientBdm() {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getPrecisionLenientBdm(type);
    }
    return result / differByTypeMap.size();
  }

  public double getRecallLenientBdm(String type) {
    AnnotationDiffer differ = differByTypeMap.get(type);
    if (differ.getCorrectMatches() + differ.getMissing() == 0) {
      return 1.0;
    }
    return (bdmByTypeMap.get(type) + differ.getCorrectMatches()
          + differ.getPartiallyCorrectMatches())
         / (differ.getCorrectMatches() + differ.getMissing());
  }

  public double getRecallLenientBdm() {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getRecallLenientBdm(type);
    }
    return result / differByTypeMap.size();
  }

  public double getFMeasureLenientBdm(String type, double beta) {
    double precision = getPrecisionLenientBdm(type);
    double recall = getRecallLenientBdm(type);
    double betaSq = beta * beta;
    double answer = ((betaSq + 1) * precision * recall)
                  / (betaSq * precision + recall);
    if(Double.isNaN(answer)) answer = 0.0;
    return answer;
  }

  public double getFMeasureLenientBdm(double beta) {
    double result = 0;
    for (String type : differByTypeMap.keySet()) {
      result += getFMeasureLenientBdm(type, beta);
    }
    return result / differByTypeMap.size();
  }

  public double getPrecisionAverageBdm(String type) {
    return (getPrecisionLenientBdm(type) + getPrecisionStrictBdm(type)) / 2.0;
  }

  /**
   * Gets the average of the strict and lenient precision values.
   * @return a <tt>double</tt> value.
   */
  public double getPrecisionAverageBdm() {
    return (getPrecisionLenientBdm() + getPrecisionStrictBdm()) / 2.0;
  }

  public double getRecallAverageBdm(String type) {
    return (getRecallLenientBdm(type) + getRecallStrictBdm(type)) / 2.0;
  }

  /**
   * Gets the average of the strict and lenient recall values.
   * @return a <tt>double</tt> value.
   */
  public double getRecallAverageBdm() {
    return (getRecallLenientBdm() + getRecallStrictBdm()) / 2.0;
  }

  public double getFMeasureAverageBdm(String type, double beta) {
    return (getFMeasureLenientBdm(type, beta)
          + getFMeasureStrictBdm(type, beta))
          / 2.0;
  }

  /**
   * Gets the average of strict and lenient F-Measure values.
   * @param beta The relative weight of precision and recall. A value of 1
   * gives equal weights to precision and recall. A value of 0 takes the recall
   * value completely out of the equation.
   * @return a <tt>double</tt>value.
   */
  public double getFMeasureAverageBdm(double beta) {
    return (getFMeasureLenientBdm(beta) + getFMeasureStrictBdm(beta)) / 2.0;
  }

  public void setBdmFile(URL url) {
    bdmFileUrl = url;
    bdmByConceptsMap = null;
  }

  /**
   * Read the BDM scores from a file.
   * @param bdmFile URL of the BDM file
   * @return map from a pair of concepts to their BDM score
   */
  public Map<String, Float> read(URL bdmFile) {
    Map<String, Float> bdmByConceptsMap = new HashMap<String, Float>();
    if (bdmFile == null) {
      Out.prln("There is no BDM file specified.");
      return bdmByConceptsMap;
    }
    BufferedReader bdmResultsReader = null;
    try {
      bdmResultsReader = new BomStrippingInputStreamReader(
        new FileInputStream(Files.fileFromURL(bdmFile)), "UTF-8");
      bdmResultsReader.readLine(); // skip the first line as the header
      String line = bdmResultsReader.readLine();
      while (line != null) {
        String[] terms = line.split(", ");
        if (terms.length > 3) {
          String oneCon = terms[0].substring(4);
          String anoCon = terms[1].substring(9);
          String bdmS = terms[2].substring(4);
          bdmByConceptsMap.put(oneCon + ", " + anoCon, new Float(bdmS));
        } else {
          Out.prln("File " + bdmFile.toString() + " has incorrect format" +
            "for the line [" + line + "].");
        }
        line = bdmResultsReader.readLine();
      }

    } catch(Exception e) {
      Out.prln("There is something wrong with the BDM file.");
      e.printStackTrace();

    } finally {
      if (bdmResultsReader != null) {
        try {
          bdmResultsReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return bdmByConceptsMap;
  }

  public List<String> getMeasuresRow(Object[] measures, String title) {
    List<AnnotationDiffer> differs = new ArrayList<AnnotationDiffer>(
      getDifferByTypeMap().values());
    AnnotationDiffer differ = new AnnotationDiffer(differs);
    NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
    f.setMaximumFractionDigits(2);
    f.setMinimumFractionDigits(2);
    List<String> row = new ArrayList<String>();
    row.add(title);
    row.add(Integer.toString(differ.getCorrectMatches()));
    row.add(Integer.toString(differ.getMissing()));
    row.add(Integer.toString(differ.getSpurious()));
    row.add(Integer.toString(differ.getPartiallyCorrectMatches()));
    for (Object object : measures) {
      String measure = (String) object;
      double beta = Double.valueOf(
        measure.substring(1,measure.indexOf('-')));
      if (measure.endsWith("strict")) {
        row.add(f.format(differ.getPrecisionStrict()));
        row.add(f.format(differ.getRecallStrict()));
        row.add(f.format(differ.getFMeasureStrict(beta)));
      } else if (measure.endsWith("strict BDM")) {
        row.add(f.format(getPrecisionStrictBdm()));
        row.add(f.format(getRecallStrictBdm()));
        row.add(f.format(getFMeasureStrictBdm(beta)));
      } else if (measure.endsWith("lenient")) {
        row.add(f.format(differ.getPrecisionLenient()));
        row.add(f.format(differ.getRecallLenient()));
        row.add(f.format(differ.getFMeasureLenient(beta)));
      } else if (measure.endsWith("lenient BDM")) {
        row.add(f.format(getPrecisionLenientBdm()));
        row.add(f.format(getRecallLenientBdm()));
        row.add(f.format(getFMeasureLenientBdm(beta)));
      } else if (measure.endsWith("average")) {
        row.add(f.format(differ.getPrecisionAverage()));
        row.add(f.format(differ.getRecallAverage()));
        row.add(f.format(differ.getFMeasureAverage(beta)));
      } else if (measure.endsWith("average BDM")) {
        row.add(f.format(getPrecisionAverageBdm()));
        row.add(f.format(getRecallAverageBdm()));
        row.add(f.format(getFMeasureAverageBdm(beta)));
      }
    }
    return row;
  }

  /**
   * Be careful, don't modify it.
   * That's not a copy because it would take too much memory.
   * @return differ by type map
   */
  public Map<String, AnnotationDiffer> getDifferByTypeMap() {
    return differByTypeMap;
  }

  protected Map<String, Float> bdmByTypeMap = new HashMap<String, Float>();
  protected URL bdmFileUrl;
  protected Map<String, AnnotationDiffer> differByTypeMap =
    new HashMap<String, AnnotationDiffer>();
  protected Map<String, Float> bdmByConceptsMap;
}
