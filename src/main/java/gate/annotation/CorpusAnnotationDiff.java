/*
 *  CorpusAnnotationDiff.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Angel Kirilov (mod. AnnotationDiff), 22/Aug/2002
 *
 *  $Id: CorpusAnnotationDiff.java 17530 2014-03-04 15:57:43Z markagreenwood $
 */

package gate.annotation;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.AbstractResource;
import gate.creole.AbstractVisualResource;
import gate.creole.AnnotationSchema;
import gate.creole.ResourceInstantiationException;
import gate.swing.XJTable;
import gate.util.Err;
import gate.util.Out;
import gate.util.Strings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
  * This class compare two annotation sets on annotation type given by the
  * AnnotationSchema object. It also deals with graphic representation of the
  * result.
  */
public class CorpusAnnotationDiff extends AbstractVisualResource
  implements  Scrollable{

  private static final long serialVersionUID = 334626622328431740L;

  // number of pixels to be used as increment by scroller
  protected int maxUnitIncrement = 10;

  /** Debug flag */
  private static final boolean DEBUG = false;

  /** This document contains the key annotation set which is taken as reference
   *  in comparison*/
  private Document keyDocument = null;

  /** This corpus contains the key annotation set which is taken as reference
   *  in comparison*/
  private Corpus keyCorpus = null;
  
  /** The name of the annotation set. If is null then the default annotation set
    * will be considered.
    */
  private String keyAnnotationSetName = null;

  /** This document contains the response annotation set which is being
    * compared against the key annotation set.
    */
  private Document responseDocument = null;

  /** This corpus contains the response annotation set which is being
    * compared against the key annotation set.
    */
  private Corpus responseCorpus = null;

  /** The name of the annotation set. If is null then the default annotation set
    * will be considered.
    */
  private String responseAnnotationSetName = null;

  /** The name of the annotation set considered in calculating FalsePozitive.
    * If is null then the default annotation set will be considered.
    */
  private String responseAnnotationSetNameFalsePoz = null;

  /** The annotation schema object used to get the annotation name*/
  private AnnotationSchema annotationSchema = null;

  /** A set of feature names bellonging to annotations from keyAnnotList
    * used in isCompatible() and isPartiallyCompatible() methods
    */
  private Set<?> keyFeatureNamesSet = null;

  /** The precision strict value (see NLP Information Extraction)*/
  private double precisionStrict = 0.0;
  /** The precision lenient value (see NLP Information Extraction)*/
  private double precisionLenient = 0.0;
  /** The precision average value (see NLP Information Extraction)*/
  private double precisionAverage = 0.0;

  /** The Recall strict value (see NLP Information Extraction)*/
  private double recallStrict = 0.0;
  /** The Recall lenient value (see NLP Information Extraction)*/
  private double recallLenient = 0.0;
  /** The Recall average value (see NLP Information Extraction)*/
  private double recallAverage = 0.0;

  /** The False positive strict (see NLP Information Extraction)*/
  private double falsePositiveStrict = 0.0;
  /** The False positive lenient (see NLP Information Extraction)*/
  private double falsePositiveLenient = 0.0;
  /** The False positive average (see NLP Information Extraction)*/
  private double falsePositiveAverage = 0.0;

  /** The F-measure strict (see NLP Information Extraction)*/
  private double fMeasureStrict = 0.0;
  /** The F-measure lenient (see NLP Information Extraction)*/
  private double fMeasureLenient = 0.0;
  /** The F-measure average (see NLP Information Extraction)*/
  private double fMeasureAverage = 0.0;
  /** The weight used in F-measure (see NLP Information Extraction)*/
  public  static double weight = 0.5;

  /**  This string represents the type of annotations used to play the roll of
    *  total number of words needed to calculate the False Positive.
    */
  private String annotationTypeForFalsePositive = null;

  /** A number formater for displaying precision and recall*/
  private static NumberFormat formatter = NumberFormat.getInstance();

  /** The components that will stay into diffPanel*/
  private XJTable diffTable = null;

  /** Used to represent the result of diff. See DiffSetElement class.*/
  private Set<DiffSetElement> diffSet = null;

  /** This field is used in doDiff() and detectKeyType() methods and holds all
   *  partially correct keys */
  private Set<Annotation> keyPartiallySet = null;
  /** This field is used in doDiff() and detectResponseType() methods*/
  private Set<Annotation> responsePartiallySet = null;

  /** This list is created from keyAnnotationSet at init() time*/
  private List<Annotation> keyAnnotList = null;
  /** This list is created from responseAnnotationSet at init() time*/
  private List<Annotation> responseAnnotList = null;

  /** This field indicates wheter or not the annot diff should run int the text
   *  mode*/
  private boolean textMode = false;

  /**  Field designated to represent the max nr of annot types and coolors for
    *  each type
    **/
  public static final int MAX_TYPES = 5;
  /** A default type when all annotation are the same represented by White color*/
  public static final int DEFAULT_TYPE = 0;
  /** A correct type when all annotation are corect represented by Green color*/
  public static final int CORRECT_TYPE = 1;
  /** A partially correct type when all annotation are corect represented
   *  by Blue color*/
  public static final int PARTIALLY_CORRECT_TYPE = 2;
  /** A spurious type when annotations in response were not present in key.
   *  Represented by Red color*/
  public static final int SPURIOUS_TYPE = 3;
  /** A missing type when annotations in key were not present in response
   *  Represented by Yellow color*/
  public static final int MISSING_TYPE = 4;

  /** Red used for SPURIOUS_TYPE*/
  private  final Color RED = new Color(255,173,181);
  /** Green used for CORRECT_TYPE*/
  private  final Color GREEN = new Color(173,255,214);
  /** White used for DEFAULT_TYPE*/
  private  final Color WHITE = new Color(255,255,255);
  /** Blue used for PARTIALLY_CORRECT_TYPE*/
  private  final Color BLUE = new Color(173,215,255);
  /** Yellow used for MISSING_TYPE*/
  private  final Color YELLOW = new Color(255,231,173);

  /** Used in DiffSetElement to represent an empty raw in the table*/
  private final int NULL_TYPE = -1;
  /** Used in some setForeground() methods*/
  private  final Color BLACK = new Color(0,0,0);
  /** The array holding the colours according to the annotation types*/
  private Color colors[] = new Color[MAX_TYPES];

  /** Used to store the no. of annotations from response,identified as belonging
    * to one of the previous types.
    */
  private int typeCounter[] = new int[MAX_TYPES];

  /** Constructs a CorpusAnnotationDiff*/
  public CorpusAnnotationDiff(){
  } //CorpusAnnotationDiff

  /** Sets the annotation type needed to calculate the falsePossitive measure
    * @param anAnnotType is the annotation type needed to calculate a special
    *  mesure called falsePossitive. Usualy the value is "token", but it can be
    *  any other string with the same semantic.
    */
  public void setAnnotationTypeForFalsePositive(String anAnnotType){
    annotationTypeForFalsePositive = anAnnotType;
  } // setAnnotationTypeForFalsePositive

  /** Gets the annotation type needed to calculate the falsePossitive measure
    * @return annotation type needed to calculate a special
    * mesure called falsePossitive.
    */
  public String getAnnotationTypeForFalsePositive(){
    return annotationTypeForFalsePositive;
  } // getAnnotationTypeForFalsePositive

  /** Sets the keyCorpus in AnnotDiff
    * @param aKeyCorpus The GATE corpus used as a key in annotation diff.
    */
  public void setKeyCorpus(Corpus aKeyCorpus) {
    keyCorpus = aKeyCorpus;
  } // setKeyCorpus

  /** @return the keyCorpus used in AnnotDiff process */
  public Corpus getKeyCorpus(){
    return keyCorpus;
  } // getKeyCorpus

  /** Sets the keyAnnotationSetName in AnnotDiff
    * @param aKeyAnnotationSetName The name of the annotation set from the
    * keyDocument.If aKeyAnnotationSetName is null then the default annotation
    * set will be used.
    */
  public void setKeyAnnotationSetName(String aKeyAnnotationSetName){
    keyAnnotationSetName = aKeyAnnotationSetName;
  } // setKeyAnnotationSetName();

  /** Gets the keyAnnotationSetName.
    * @return The name of the keyAnnotationSet used in AnnotationDiff. If
    * returns null then the the default annotation set will be used.
    */
  public String getKeyAnnotationSetName(){
    return keyAnnotationSetName;
  } // getKeyAnnotationSetName()

  /** Sets the keyFeatureNamesSet in AnnotDiff.
    * @param aKeyFeatureNamesSet a set containing the feature names from key
    * that will be used in isPartiallyCompatible()
    */
  public void setKeyFeatureNamesSet(Set<?> aKeyFeatureNamesSet){
    keyFeatureNamesSet = aKeyFeatureNamesSet;
  }//setKeyFeatureNamesSet();

  /** Gets the keyFeatureNamesSet in AnnotDiff.
    * @return A set containing the feature names from key
    * that will be used in isPartiallyCompatible()
    */
  public Set<?> getKeyFeatureNamesSet(){
    return keyFeatureNamesSet;
  }//getKeyFeatureNamesSet();

  /** Sets the responseAnnotationSetName in AnnotDiff
    * @param aResponseAnnotationSetName The name of the annotation set from the
    * responseDocument.If aResponseAnnotationSetName is null then the default
    * annotation set will be used.
    */
  public void setResponseAnnotationSetName(String aResponseAnnotationSetName){
    responseAnnotationSetName = aResponseAnnotationSetName;
  } // setResponseAnnotationSetName();

  /** gets the responseAnnotationSetName.
    * @return The name of the responseAnnotationSet used in AnnotationDiff. If
    * returns null then the the default annotation set will be used.
    */
  public String getResponseAnnotationSetName(){
    return responseAnnotationSetName;
  } // getResponseAnnotationSetName()

  /** Sets the responseAnnotationSetNameFalsePoz in AnnotDiff
    * @param aResponseAnnotationSetNameFalsePoz The name of the annotation set
    * from the responseDocument.If aResponseAnnotationSetName is null
    * then the default annotation set will be used.
    */
  public void setResponseAnnotationSetNameFalsePoz(
                                    String aResponseAnnotationSetNameFalsePoz){
    responseAnnotationSetNameFalsePoz = aResponseAnnotationSetNameFalsePoz;
  } // setResponseAnnotationSetNameFalsePoz();

  /** gets the responseAnnotationSetNameFalsePoz.
    * @return The name of the responseAnnotationSetFalsePoz used in
    * AnnotationDiff. If returns null then the the default annotation
    * set will be used.
    */
  public String getResponseAnnotationSetNameFalsePoz(){
    return responseAnnotationSetNameFalsePoz;
  } // getResponseAnnotationSetNamefalsePoz()

  /**  Sets the annot diff to work in the text mode.This would not initiate the
    *  GUI part of annot diff but it would calculate precision etc
    */
  public void setTextMode(Boolean aTextMode){
    //it needs to be a Boolean and not boolean, because you cannot put
    //in the parameters hashmap a boolean, it needs an object
    textMode = aTextMode.booleanValue();
  }// End setTextMode();

  /** Gets the annot diff textmode.True means that the text mode is activated.*/
  public boolean isTextMode(){
    return textMode;
  }// End setTextMode();

  /** Returns a set with all annotations of a specific type*/
  public Set<Annotation> getAnnotationsOfType(int annotType){
    Set<Annotation> results = new HashSet<Annotation>();
    if (diffSet == null) return results;
    Iterator<DiffSetElement> diffIter = diffSet.iterator();
    while(diffIter.hasNext()){
      DiffSetElement diffElem = diffIter.next();
      switch(annotType){
        case CORRECT_TYPE:{
          if (diffElem.getRightType() == CORRECT_TYPE)
            results.add(diffElem.getRightAnnotation());
        }break;
        case PARTIALLY_CORRECT_TYPE:{
          if (diffElem.getRightType() == PARTIALLY_CORRECT_TYPE)
            results.add(diffElem.getRightAnnotation());
        }break;
        case SPURIOUS_TYPE:{
          if (diffElem.getRightType() == SPURIOUS_TYPE)
            results.add(diffElem.getRightAnnotation());
        }break;
        case MISSING_TYPE:{
          if (diffElem.getLeftType() == MISSING_TYPE)
            results.add(diffElem.getLeftAnnotation());
        }break;
        case DEFAULT_TYPE:{
          if (diffElem.getLeftType() == DEFAULT_TYPE)
            results.add(diffElem.getLeftAnnotation());
        }break;
      }// End switch
    }// End while
    return results;
  }//getAnnotationsOfType

  //Prameters utility methods
  /**
   * Gets the value of a parameter of this resource.
   * @param paramaterName the name of the parameter
   * @return the current value of the parameter
   */
  @Override
  public Object getParameterValue(String paramaterName)
                throws ResourceInstantiationException{
    return AbstractResource.getParameterValue(this, paramaterName);
  }

  /**
   * Sets the value for a specified parameter.
   *
   * @param paramaterName the name for the parameteer
   * @param parameterValue the value the parameter will receive
   */
  @Override
  public void setParameterValue(String paramaterName, Object parameterValue)
              throws ResourceInstantiationException{
    // get the beaninfo for the resource bean, excluding data about Object
    BeanInfo resBeanInf = null;
    try {
      resBeanInf = Introspector.getBeanInfo(this.getClass(), Object.class);
    } catch(Exception e) {
      throw new ResourceInstantiationException(
        "Couldn't get bean info for resource " + this.getClass().getName()
        + Strings.getNl() + "Introspector exception was: " + e
      );
    }
    AbstractResource.setParameterValue(this, resBeanInf, paramaterName, parameterValue);
  }

  /**
   * Sets the values for more parameters in one step.
   *
   * @param parameters a feature map that has paramete names as keys and
   * parameter values as values.
   */
  @Override
  public void setParameterValues(FeatureMap parameters)
              throws ResourceInstantiationException{
    AbstractResource.setParameterValues(this, parameters);
  }



  ///////////////////////////////////////////////////
  // PRECISION methods
  ///////////////////////////////////////////////////

  /** @return the precisionStrict field*/
  public double getPrecisionStrict(){
    return precisionStrict;
  } // getPrecisionStrict

  /** @return the precisionLenient field*/
  public double getPrecisionLenient(){
    return precisionLenient;
  } // getPrecisionLenient

  /** @return the precisionAverage field*/
  public double getPrecisionAverage(){
    return precisionAverage;
  } // getPrecisionAverage

  /** @return the fMeasureStrict field*/
  public double getFMeasureStrict(){
    return fMeasureStrict;
  } // getFMeasureStrict

  /** @return the fMeasureLenient field*/
  public double getFMeasureLenient(){
    return fMeasureLenient;
  } // getFMeasureLenient

  /** @return the fMeasureAverage field*/
  public double getFMeasureAverage(){
    return fMeasureAverage;
  } // getFMeasureAverage

  ///////////////////////////////////////////////////
  // RECALL methods
  ///////////////////////////////////////////////////

  /** @return the recallStrict field*/
  public double getRecallStrict(){
    return recallStrict;
  } // getRecallStrict

  /** @return the recallLenient field*/
  public double getRecallLenient(){
    return recallLenient;
  } // getRecallLenient

  /** @return the recallAverage field*/
  public double getRecallAverage(){
    return recallAverage;
  } // getRecallAverage

  ///////////////////////////////////////////////////
  // FALSE POSITIVE methods
  ///////////////////////////////////////////////////

  /** @return the falsePositiveStrict field*/
  public double getFalsePositiveStrict(){
    return falsePositiveStrict;
  } // getFalsePositiveStrict

  /** @return the falsePositiveLenient field*/
  public double getFalsePositiveLenient(){
    return falsePositiveLenient;
  } // getFalsePositiveLenient

  /** @return the falsePositiveAverage field*/
  public double getFalsePositiveAverage(){
    return falsePositiveAverage;
  } // getFalsePositive

  /**
    * @param aResponseCorpus the GATE response corpus
    * containing the annotation Set being compared against the annotation from
    * the keyCorpus.
    */
  public void setResponseCorpus(Corpus aResponseCorpus) {
    responseCorpus = aResponseCorpus;
  } //setResponseCorpus

  /**
    * @param anAnnotationSchema the annotation type being compared.
    * This type is found in annotationSchema object as field
    * {@link gate.creole.AnnotationSchema#getAnnotationName()}. If is <b>null<b>
    * then AnnotDiff will throw an exception when it comes to do the diff.
    */
  public void setAnnotationSchema(AnnotationSchema anAnnotationSchema) {
    annotationSchema = anAnnotationSchema;
  } // setAnnotationType

  /** @return the annotation schema object used in annotation diff process */
  public AnnotationSchema getAnnotationSchema(){
    return annotationSchema;
  } // AnnotationSchema

  @Override
  public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
  }// public Dimension getPreferredScrollableViewportSize()

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect,
                                              int orientation, int direction) {
    return maxUnitIncrement;
  }// public int getScrollableUnitIncrement

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect,
                                              int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL)
        return visibleRect.width - maxUnitIncrement;
    else
        return visibleRect.height - maxUnitIncrement;
  }// public int getScrollableBlockIncrement

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }// public boolean getScrollableTracksViewportWidth()

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }

  /**
    * This method does the diff, Precision,Recall,FalsePositive
    * calculation and so on.
    */
  @Override
  public Resource init() throws ResourceInstantiationException {
    colors[DEFAULT_TYPE] = WHITE;
    colors[CORRECT_TYPE] = GREEN;
    colors[SPURIOUS_TYPE] = RED;
    colors[PARTIALLY_CORRECT_TYPE] = BLUE;
    colors[MISSING_TYPE] = YELLOW;

    // Initialize the partially sets...
    keyPartiallySet = new HashSet<Annotation>();
    responsePartiallySet = new HashSet<Annotation>();

    // Do the diff, P&R calculation and so on
    AnnotationSet keyAnnotSet = null;
    AnnotationSet responseAnnotSet = null;

    if(annotationSchema == null)
     throw new ResourceInstantiationException("No annotation schema defined !");

    if(keyCorpus == null || 0 == keyCorpus.size())
      throw new ResourceInstantiationException("No key corpus or empty defined !");

    if(responseCorpus == null || 0 == responseCorpus.size())
      throw new ResourceInstantiationException("No response corpus or empty defined !");

    // init counters and do difference for documents by pairs
    for (int type=0; type < MAX_TYPES; type++)
      typeCounter[type] = 0;
    diffSet = new HashSet<DiffSetElement>();

    for(int i=0; i<keyCorpus.size(); ++i) {
      keyDocument = keyCorpus.get(i);
      // find corresponding responce document if any

      Document doc;
      responseDocument = null;
      for(int j=0; j<responseCorpus.size(); ++j) {
        doc = responseCorpus.get(j);
        if(0 == doc.getName().compareTo(keyDocument.getName())
            || 0 == doc.getSourceUrl().getFile().compareTo(
                      keyDocument.getSourceUrl().getFile())) {
          responseDocument = doc;
          break; // response corpus loop
        } // if
      } // for
      
      if(null == responseDocument) {
        Out.prln("There is no mach in responce corpus for document '"
                  +keyDocument.getName()+"' from key corpus");
        continue; // key corpus loop
      } // if
      
      if (keyAnnotationSetName == null) {
        // Get the default key AnnotationSet from the keyDocument
        keyAnnotSet =  keyDocument.getAnnotations().get(
                                annotationSchema.getAnnotationName());
      }
      else {
        keyAnnotSet =  keyDocument.getAnnotations(keyAnnotationSetName).get(
                                annotationSchema.getAnnotationName());
      } // if
  
      if (keyAnnotSet == null)
        // The diff will run with an empty set.All annotations from response
        // would be spurious.
        keyAnnotList = new LinkedList<Annotation>();
      else
        // The alghoritm will modify this annotation set. It is better to make a
        // separate copy of them.
        keyAnnotList = new LinkedList<Annotation>(keyAnnotSet);
  
      if (responseAnnotationSetName == null)
        // Get the response AnnotationSet from the default set
        responseAnnotSet = responseDocument.getAnnotations().get(
                                            annotationSchema.getAnnotationName());
      else
        responseAnnotSet = responseDocument.getAnnotations(responseAnnotationSetName).
                                      get(annotationSchema.getAnnotationName());
  
      if (responseAnnotSet == null)
        // The diff will run with an empty set.All annotations from key
        // would be missing.
        responseAnnotList = new LinkedList<Annotation>();
      else
        // The alghoritm will modify this annotation set. It is better to make a
        // separate copy of them.
        responseAnnotList = new LinkedList<Annotation>(responseAnnotSet);
  
      // Sort them ascending on Start offset (the comparator does that)
      AnnotationSetComparator asComparator = new AnnotationSetComparator();
      Collections.sort(keyAnnotList, asComparator);
      Collections.sort(responseAnnotList, asComparator);
  
      // Calculate the diff Set. This set will be used later with graphic
      // visualisation.
      doDiff(keyAnnotList, responseAnnotList);
    } // for
    
    // If it runs under text mode just stop here.
    if (textMode) return this;

    //Show it
    // Configuring the formatter object. It will be used later to format
    // precision and recall
    formatter.setMaximumIntegerDigits(1);
    formatter.setMinimumFractionDigits(4);
    formatter.setMinimumFractionDigits(4);

    // Create an Annotation diff table model
    AnnotationDiffTableModel diffModel = new AnnotationDiffTableModel(diffSet);
    // Create a XJTable based on this model
    diffTable = new XJTable(diffModel);
    diffTable.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Set the cell renderer for this table.
    AnnotationDiffCellRenderer cellRenderer = new AnnotationDiffCellRenderer();
    diffTable.setDefaultRenderer(java.lang.String.class,cellRenderer);
    diffTable.setDefaultRenderer(java.lang.Long.class,cellRenderer);
    // Put the table into a JScroll

    // Arange all components on a this JPanel
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        arangeAllComponents();
      }
    });

    if (DEBUG)
      printStructure(diffSet);

    return this;
  } //init()

  /** This method creates the graphic components and aranges them on
    * <b>this</b> JPanel
    */
  protected void arangeAllComponents(){
    this.removeAll();
    // Setting the box layout for diffpanel
    BoxLayout boxLayout = new BoxLayout(this,BoxLayout.Y_AXIS);
    this.setLayout(boxLayout);

    JTableHeader tableHeader = diffTable.getTableHeader();
    tableHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.add(tableHeader);
    diffTable.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Add the tableScroll to the diffPanel
    this.add(diffTable);


    // ADD the LEGEND
    //Lay out the JLabels from left to right.
    //Box infoBox = new Box(BoxLayout.X_AXIS);
    JPanel infoBox = new  JPanel();
    infoBox.setLayout(new BoxLayout(infoBox,BoxLayout.X_AXIS));
    infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Keep the components together
    //box.add(Box.createHorizontalGlue());

    Box box = new Box(BoxLayout.Y_AXIS);
    JLabel jLabel = new JLabel("LEGEND");
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    jLabel.setOpaque(true);
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    box.add(jLabel);

    jLabel = new JLabel("Missing (present in Key but not in Response):  " +
                                                typeCounter[MISSING_TYPE]);
    jLabel.setForeground(BLACK);
    jLabel.setBackground(colors[MISSING_TYPE]);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    jLabel.setOpaque(true);
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    box.add(jLabel);

    // Add a space
    box.add(Box.createRigidArea(new Dimension(0,5)));

    jLabel = new JLabel("Correct (total match):  " + typeCounter[CORRECT_TYPE]);
    jLabel.setForeground(BLACK);
    jLabel.setBackground(colors[CORRECT_TYPE]);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    jLabel.setOpaque(true);
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    box.add(jLabel);

    // Add a space
    box.add(Box.createRigidArea(new Dimension(0,5)));

    jLabel =new JLabel("Partially correct (overlap in Key and Response):  "+
                                        typeCounter[PARTIALLY_CORRECT_TYPE]);
    jLabel.setForeground(BLACK);
    jLabel.setBackground(colors[PARTIALLY_CORRECT_TYPE]);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    jLabel.setOpaque(true);
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    box.add(jLabel);

    // Add a space
    box.add(Box.createRigidArea(new Dimension(0,5)));

    jLabel = new JLabel("Spurious (present in Response but not in Key):  " +
                                        typeCounter[SPURIOUS_TYPE]);
    jLabel.setForeground(BLACK);
    jLabel.setBackground(colors[SPURIOUS_TYPE]);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    jLabel.setOpaque(true);
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    box.add(jLabel);

    infoBox.add(box);
    // Add a space
    infoBox.add(Box.createRigidArea(new Dimension(40,0)));

    // Precision measure
    //Lay out the JLabels from left to right.
    box = new Box(BoxLayout.Y_AXIS);

    jLabel = new JLabel("Precision strict: " +
                                    formatter.format(precisionStrict));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("Precision average: " +
                                    formatter.format(precisionAverage));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("Precision lenient: " +
                                    formatter.format(precisionLenient));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    infoBox.add(box);
    // Add a space
    infoBox.add(Box.createRigidArea(new Dimension(40,0)));

    // RECALL measure
    //Lay out the JLabels from left to right.
    box = new Box(BoxLayout.Y_AXIS);

    jLabel = new JLabel("Recall strict: " + formatter.format(recallStrict));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("Recall average: " + formatter.format(recallAverage));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("Recall lenient: " + formatter.format(recallLenient));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    infoBox.add(box);
    // Add a space
    infoBox.add(Box.createRigidArea(new Dimension(40,0)));

    // F-Measure
    //Lay out the JLabels from left to right.
    box = new Box(BoxLayout.Y_AXIS);

    jLabel = new JLabel("F-Measure strict: " +
                                        formatter.format(fMeasureStrict));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("F-Measure average: " +
                                        formatter.format(fMeasureAverage));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("F-Measure lenient: " +
                                        formatter.format(fMeasureLenient));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);
    infoBox.add(box);

    // Add a space
    infoBox.add(Box.createRigidArea(new Dimension(40,0)));

    // FALSE POZITIVE measure
    //Lay out the JLabels from left to right.
    box = new Box(BoxLayout.Y_AXIS);

    jLabel = new JLabel("False positive strict: " +
                                        formatter.format(falsePositiveStrict));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("False positive average: " +
                                        formatter.format(falsePositiveAverage));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);

    jLabel = new JLabel("False positive lenient: " +
                                        formatter.format(falsePositiveLenient));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    box.add(jLabel);
    infoBox.add(box);

    // Add a space
    infoBox.add(Box.createRigidArea(new Dimension(10,0)));

    this.add(infoBox);
  } //arangeAllComponents

  /** Used internally for debugging */
  protected void printStructure(Set<DiffSetElement> aDiffSet){
    Iterator<DiffSetElement> iterator = aDiffSet.iterator();
    String leftAnnot = null;
    String rightAnnot = null;
    while(iterator.hasNext()){
      DiffSetElement diffElem = iterator.next();
      if (diffElem.getLeftAnnotation() == null)
        leftAnnot = "NULL ";
      else
        leftAnnot = diffElem.getLeftAnnotation().toString();
      if (diffElem.getRightAnnotation() == null)
        rightAnnot = " NULL";
      else
        rightAnnot = diffElem.getRightAnnotation().toString();
      Out.prln( leftAnnot + "|" + rightAnnot);
    } // end while
  } // printStructure

  /** This method is the brain of the AnnotationSet diff and creates a set with
    * diffSetElement objects.
    * @param aKeyAnnotList a list containing the annotations from key. If this
    * param is <b>null</b> then the method will simply return and will not do a
    * thing.
    * @param aResponseAnnotList a list containing the annotation from response.
    * If this param is <b>null</b> the method will return.
    */
  protected void doDiff(List<Annotation> aKeyAnnotList,
                        List<Annotation> aResponseAnnotList){

    // If one of the annotation sets is null then is no point in doing the diff.
    if (aKeyAnnotList == null || aResponseAnnotList == null)
      return;

    // Iterate throught all elements from keyList and find those in the response
    // list which satisfies isCompatible() and isPartiallyCompatible() relations
    Iterator<Annotation> keyIterator = aKeyAnnotList.iterator();
    while(keyIterator.hasNext()){
      Annotation keyAnnot = keyIterator.next();
      Iterator<Annotation> responseIterator = aResponseAnnotList.iterator();

      DiffSetElement diffElement = null;
      while(responseIterator.hasNext()){
        Annotation responseAnnot = responseIterator.next();

        if(keyAnnot.isPartiallyCompatible(responseAnnot,keyFeatureNamesSet)){
          keyPartiallySet.add(keyAnnot);
          responsePartiallySet.add(responseAnnot);
          if (keyAnnot.coextensive(responseAnnot)){
            // Found two compatible annotations
            // Create a new DiffSetElement and add it to the diffSet
            diffElement = new DiffSetElement( keyAnnot,
                                              responseAnnot,
                                              DEFAULT_TYPE,
                                              CORRECT_TYPE,
                                              keyDocument,
                                              responseDocument);

            // Add this element to the DiffSet
            addToDiffset(diffElement);
          } // End if (keyAnnot.coextensive(responseAnnot))
        }else if (keyAnnot.coextensive(responseAnnot)){
          // Found two aligned annotations. We have to find out if the response
          // is partialy compatible with another key annotation.
          // Create a new DiffSetElement and add it to the diffSet
          diffElement = new DiffSetElement( keyAnnot,
                                            responseAnnot,
                                            detectKeyType(keyAnnot),
                                            detectResponseType(responseAnnot),
                                            keyDocument,
                                            responseDocument);
          // Add this element to the DiffSet
          addToDiffset(diffElement);
        } // End if (keyAnnot.coextensive(responseAnnot)){

        if (diffElement != null){
          // Eliminate the response annotation from the list.
          responseIterator.remove();
          break;
        } // End if
      } // end while responseIterator

      // If diffElement != null it means that break was used
      if (diffElement == null){
        if (keyPartiallySet.contains(keyAnnot))
          diffElement = new DiffSetElement( keyAnnot,
                                            null,
                                            DEFAULT_TYPE,
                                            NULL_TYPE,
                                            keyDocument,
                                            responseDocument);
        else{
          // If keyAnnot is not in keyPartiallySet then it has to be checked
          // agains all annotations in DiffSet to see if there is
          // a previous annotation from response set which is partially
          // compatible with the keyAnnot
          Iterator<DiffSetElement> respParIter = diffSet.iterator();
          while (respParIter.hasNext()){
            DiffSetElement diffElem = respParIter.next();
            Annotation respAnnot = diffElem.getRightAnnotation();
            if (respAnnot != null && keyAnnot.isPartiallyCompatible(respAnnot,
                                                          keyFeatureNamesSet)){
                diffElement = new DiffSetElement( keyAnnot,
                                                  null,
                                                  DEFAULT_TYPE,
                                                  NULL_TYPE,
                                                  keyDocument,
                                                  responseDocument);
                break;
            } // End if
          } // End while
          // If is still nul then it means that the key annotation is missing
          if (diffElement == null)
            diffElement = new DiffSetElement( keyAnnot,
                                              null,
                                              MISSING_TYPE,
                                              NULL_TYPE,
                                              keyDocument,
                                              responseDocument);
        } // End if
        addToDiffset(diffElement);
      } // End if

      keyIterator.remove();
    } // end while keyIterator

    DiffSetElement diffElem = null;
    Iterator<Annotation> responseIter = aResponseAnnotList.iterator();
    while (responseIter.hasNext()){
      Annotation respAnnot = responseIter.next();
      if (responsePartiallySet.contains(respAnnot))
        diffElem = new DiffSetElement( null,
                                       respAnnot,
                                       NULL_TYPE,
                                       PARTIALLY_CORRECT_TYPE,
                                       keyDocument,
                                       responseDocument);
      else
        diffElem = new DiffSetElement( null,
                                       respAnnot,
                                       NULL_TYPE,
                                       SPURIOUS_TYPE,
                                       keyDocument,
                                       responseDocument);
      addToDiffset(diffElem);
      responseIter.remove();
    } // End while

    // CALCULATE ALL (NLP) MEASURES like:
    // Precistion, Recall, FalsePositive and F-Measure
    int possible =  typeCounter[CORRECT_TYPE] +  // this comes from Key or Resp
                    typeCounter[PARTIALLY_CORRECT_TYPE] + // this comes from Resp
                    typeCounter[MISSING_TYPE]; // this comes from Key

    int actual =  typeCounter[CORRECT_TYPE] +  // this comes from Key or Resp
                  typeCounter[PARTIALLY_CORRECT_TYPE] + // this comes from Resp
                  typeCounter[SPURIOUS_TYPE]; // this comes from Resp
/*
    if (actual != responseSize)
      Err.prln("AnnotDiff warning: The response size(" + responseSize +
      ") is not the same as the computed value of" +
    " actual(Correct[resp or key]+Partial[resp]+Spurious[resp]=" + actual +")");
*/
    if (actual != 0){
      precisionStrict =  ((double)typeCounter[CORRECT_TYPE])/((double)actual);
      precisionLenient = ((double)(typeCounter[CORRECT_TYPE] +
                         typeCounter[PARTIALLY_CORRECT_TYPE]))/((double)actual);
      precisionAverage = (precisionStrict + precisionLenient) /
                                                                  2;
    } // End if
    if (possible != 0){
      recallStrict = ((double)typeCounter[CORRECT_TYPE])/((double)possible);
      recallLenient = ((double)(typeCounter[CORRECT_TYPE] +
                       typeCounter[PARTIALLY_CORRECT_TYPE]))/((double)possible);
      recallAverage = (recallStrict + recallLenient) / 2;
    } // End if


    int no = 0;
    // If an annotation type for false poz was selected calculate the number of
    // Annotations
    if (annotationTypeForFalsePositive != null)
     // Was it the default set ?
     if (responseAnnotationSetNameFalsePoz == null){
      AnnotationSet aSet = responseDocument.getAnnotations().get(
                                      annotationTypeForFalsePositive);
          no = aSet == null ? 0 : aSet.size();
     }else{
      AnnotationSet aSet = responseDocument.getAnnotations(responseAnnotationSetNameFalsePoz).get(
                                        annotationTypeForFalsePositive);
      no = aSet == null? 0 : aSet.size();
     }
    if (no != 0){
      // No error here: the formula is the opposite to recall or precission
     falsePositiveStrict = ((double)(typeCounter[SPURIOUS_TYPE] +
                             typeCounter[PARTIALLY_CORRECT_TYPE])) /((double)no);
     falsePositiveLenient = ((double)typeCounter[SPURIOUS_TYPE]) /((double) no);
     falsePositiveAverage = (falsePositiveStrict +
                                           falsePositiveLenient)/2 ;
    } // End if

    // Calculate F-Measure Strict
    double denominator = weight * (precisionStrict + recallStrict);
    if (denominator != 0)
      fMeasureStrict = (precisionStrict * recallStrict) / denominator ;
    else fMeasureStrict = 0.0;
    // Calculate F-Measure Lenient
    denominator = weight * (precisionLenient + recallLenient);
    if (denominator != 0)
      fMeasureLenient = (precisionLenient * recallLenient) / denominator ;
    else fMeasureLenient = 0.0;
    // Calculate F-Measure Average
    fMeasureAverage = (fMeasureStrict + fMeasureLenient) / 2;

  } // doDiff

  /** Decide what type is the keyAnnotation (DEFAULT_TYPE, MISSING or NULL_TYPE)
   *  This method must be applied only on annotation from key set.
   *  @param anAnnot is an annotation from the key set.
   *  @return three possible value(DEFAULT_TYPE, MISSING or NULL_TYPE)
   */
  private int detectKeyType(Annotation anAnnot){
    if (anAnnot == null) return NULL_TYPE;

    if (keyPartiallySet.contains(anAnnot)) return DEFAULT_TYPE;
    Iterator<Annotation> iter = responsePartiallySet.iterator();
    while(iter.hasNext()){
      Annotation a = iter.next();
      if (anAnnot.isPartiallyCompatible(a,keyFeatureNamesSet))
        return DEFAULT_TYPE;
    } // End while

    iter = responseAnnotList.iterator();
    while(iter.hasNext()){
      Annotation a = iter.next();
      if (anAnnot.isPartiallyCompatible(a,keyFeatureNamesSet)){
         responsePartiallySet.add(a);
         keyPartiallySet.add(anAnnot);
         return DEFAULT_TYPE;
      } // End if
    } // End while
    return MISSING_TYPE;
  } //detectKeyType

  /**  Decide what type is the responseAnnotation
    *  (PARTIALLY_CORRECT_TYPE, SPURIOUS or NULL_TYPE)
    *  This method must be applied only on annotation from response set.
    *  @param anAnnot is an annotation from the key set.
    *  @return three possible value(PARTIALLY_CORRECT_TYPE, SPURIOUS or NULL_TYPE)
    */
  private int detectResponseType(Annotation anAnnot){
    if (anAnnot == null) return NULL_TYPE;

    if (responsePartiallySet.contains(anAnnot)) return PARTIALLY_CORRECT_TYPE;
    Iterator<Annotation> iter = keyPartiallySet.iterator();
    while(iter.hasNext()){
      Annotation a = iter.next();
      if (a.isPartiallyCompatible(anAnnot,keyFeatureNamesSet))
        return PARTIALLY_CORRECT_TYPE;
    } // End while

    iter = keyAnnotList.iterator();
    while(iter.hasNext()){
      Annotation a = iter.next();
      if (a.isPartiallyCompatible(anAnnot,keyFeatureNamesSet)){
         responsePartiallySet.add(anAnnot);
         keyPartiallySet.add(a);
         return PARTIALLY_CORRECT_TYPE;
      } // End if
    } // End while
    return SPURIOUS_TYPE;
  } //detectResponseType

  /** This method add an DiffsetElement to the DiffSet and also counts the
    * number of compatible, partialCompatible, Incorect and Missing annotations.
    */
  private void addToDiffset(DiffSetElement aDiffSetElement){
    if (aDiffSetElement == null) return;

    diffSet.add(aDiffSetElement);
    // For the Right side (response) the type can be one of the following:
    // PC, I, C
    if (NULL_TYPE != aDiffSetElement.getRightType())
      typeCounter[aDiffSetElement.getRightType()]++;
    // For the left side (key) the type can be : D or M
    if (NULL_TYPE != aDiffSetElement.getLeftType() &&
        CORRECT_TYPE != aDiffSetElement.getLeftType())
      typeCounter[aDiffSetElement.getLeftType()]++;
  } // addToDiffset

  /* ********************************************************************
   * INNER CLASS
   * ********************************************************************/

  /**
    * A custom table model used to render a table containing the two annotation
    * sets. The columns will be:
    * (KEY) Type, Start, End, Features, empty column,(Response) Type,Start, End, Features
    */
  protected class AnnotationDiffTableModel extends AbstractTableModel{

    private static final long serialVersionUID = 1410683181803904176L;

    /** Constructs an AnnotationDiffTableModel given a data Collection */
    public AnnotationDiffTableModel(Collection<DiffSetElement> data){
      modelData = new ArrayList<DiffSetElement>();
      modelData.addAll(data);
    } // AnnotationDiffTableModel

    /** Constructs an AnnotationDiffTableModel */
    public AnnotationDiffTableModel(){
      modelData = new ArrayList<DiffSetElement>();
    } // AnnotationDiffTableModel

    /** Return the size of data.*/
    @Override
    public int getRowCount(){
      return modelData.size();
    } //getRowCount

    /** Return the number of columns.*/
    @Override
    public int getColumnCount(){
      return 10;
    } //getColumnCount

    /** Returns the name of each column in the model*/
    @Override
    public String getColumnName(int column){
      switch(column){
        case 0: return "String - Key";
        case 1: return "Start - Key";
        case 2: return "End - Key";
        case 3: return "Features - Key";
        case 4: return "   ";
        case 5: return "String - Response";
        case 6: return "Start - Response";
        case 7: return "End -Response";
        case 8: return "Features - Response";
        case 9: return "Document";
        default:return "?";
      }
    } //getColumnName

    /** Return the class type for each column. */
    @Override
    public Class<?> getColumnClass(int column){
      switch(column){
        case 0: return String.class;
        case 1: return Long.class;
        case 2: return Long.class;
        case 3: return String.class;
        case 4: return String.class;
        case 5: return String.class;
        case 6: return Long.class;
        case 7: return Long.class;
        case 8: return String.class;
        case 9: return String.class;
        default:return Object.class;
      }
    } //getColumnClass

    /**Returns a value from the table model */
    @Override
    public Object getValueAt(int row, int column){
      DiffSetElement diffSetElement = modelData.get(row);
      if (diffSetElement == null) return null;
      switch(column){
        // Left Side (Key)
        //Type - Key
        case 0:{
           if (diffSetElement.getLeftAnnotation() == null) return null;
//           return diffSetElement.getLeftAnnotation().getType();
           Annotation annot = diffSetElement.getLeftAnnotation();
           String theString = "";
           try {
             theString = diffSetElement.getKeyDocument().getContent().getContent(
                    annot.getStartNode().getOffset(),
                    annot.getEndNode().getOffset()).toString();
           } catch (gate.util.InvalidOffsetException ex) {
             Err.prln(ex.getMessage());
           }
           return theString;
        }
        // Start - Key
        case 1:{
           if (diffSetElement.getLeftAnnotation() == null) return null;
           return diffSetElement.getLeftAnnotation().getStartNode().getOffset();
        }
        // End - Key
        case 2:{
           if (diffSetElement.getLeftAnnotation() == null) return null;
           return diffSetElement.getLeftAnnotation().getEndNode().getOffset();
        }
        // Features - Key
        case 3:{
           if (diffSetElement.getLeftAnnotation() == null) return null;
           if (diffSetElement.getLeftAnnotation().getFeatures() == null)
             return null;
           return diffSetElement.getLeftAnnotation().getFeatures().toString();
        }
        // Empty column
        case 4:{
          return "   ";
        }
        // Right Side (Response)
        //Type - Response
        case 5:{
           if (diffSetElement.getRightAnnotation() == null) return null;
//           return diffSetElement.getRightAnnotation().getType();
           Annotation annot = diffSetElement.getRightAnnotation();
           String theString = "";
           try {
             theString = diffSetElement.getResponseDocument().getContent().getContent(
                    annot.getStartNode().getOffset(),
                    annot.getEndNode().getOffset()).toString();
           } catch (gate.util.InvalidOffsetException ex) {
             Err.prln(ex.getMessage());
           }
           return theString;
        }
        // Start - Response
        case 6:{
           if (diffSetElement.getRightAnnotation() == null) return null;
          return diffSetElement.getRightAnnotation().getStartNode().getOffset();
        }
        // End - Response
        case 7:{
           if (diffSetElement.getRightAnnotation() == null) return null;
           return diffSetElement.getRightAnnotation().getEndNode().getOffset();
        }
        // Features - resonse
        case 8:{
           if (diffSetElement.getRightAnnotation() == null) return null;
           return diffSetElement.getRightAnnotation().getFeatures().toString();
        }
        // Document name
        case 9:{
          return diffSetElement.getKeyDocument().getName();
        }
        // The hidden column
        case 10:{
          return diffSetElement;
        }
        default:{return null;}
      } // End switch
    } //getValueAt

    public Object getRawObject(int row){
      return modelData.get(row);
    } //getRawObject

    /** Holds the data for TableDiff*/
    private List<DiffSetElement> modelData = null;

  } //Inner class AnnotationDiffTableModel

  /* ********************************************************************
   * INNER CLASS
   * ********************************************************************/
  /**
    * This class defines a Cell renderer for the AnnotationDiff table
    */
  public class AnnotationDiffCellRenderer extends DefaultTableCellRenderer{

    private static final long serialVersionUID = -7804261826021343001L;

    /** Constructs a randerer with a table model*/
    public AnnotationDiffCellRenderer() { }  //AnnotationDiffCellRenderer

    private Color background = WHITE;

    /** This method is called by JTable*/

    @Override
    public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column
    ) {
      JComponent defaultComp = null;
      defaultComp = (JComponent) super.getTableCellRendererComponent(
  table, value, isSelected, hasFocus, row, column
      );

      // The column number four will be randered using a blank component
      if (column == 4 || value == null)
        return new JPanel();

      if (!(table.getModel().getValueAt(row,10) instanceof DiffSetElement))
        return defaultComp;

      DiffSetElement diffSetElement =
                        (DiffSetElement) table.getModel().getValueAt(row,10);

      if (diffSetElement == null)
        return defaultComp;

      if (column < 4){
        if (NULL_TYPE != diffSetElement.getLeftType())
          background = colors[diffSetElement.getLeftType()];
        else return new JPanel();
      }else if (column < 10){
        if (NULL_TYPE != diffSetElement.getRightType())
          background = colors[diffSetElement.getRightType()];
        else return new JPanel();
      }

      defaultComp.setBackground(background);
      defaultComp.setForeground(BLACK);

      defaultComp.setOpaque(true);
      return defaultComp;
    } //getTableCellRendererComponent

  } // class AnnotationDiffCellRenderer

  /* ********************************************************************
   * INNER CLASS
   * ********************************************************************/
   class AnnotationSetComparator implements Comparator<Annotation> {

      public AnnotationSetComparator(){}

      @Override
      public int compare(Annotation a1, Annotation a2) {
        
        Long l1 = a1.getStartNode().getOffset();
        Long l2 = a2.getStartNode().getOffset();
        if (l1 != null)
          return l1.compareTo(l2);
        else
          return -1;
      } //compare
    } // class AnnotationSetComparator

  /* ********************************************************************
   * INNER CLASS
   * ********************************************************************/

  /**
    * This class is used for internal purposes. It represents a row from the
    * table.
    */
  protected class DiffSetElement {
    /** This field represent a key annotation*/
    private Annotation leftAnnotation = null;
    /** This field represent a response annotation*/
    private Annotation rightAnnotation = null;
    /** Default initialization of the key type*/
    private int leftType = DEFAULT_TYPE;
    /** Default initialization of the response type*/
    private int rightType = DEFAULT_TYPE;
    /** Key document */
    private Document keyDocument;
    /** Response document */
    private Document respDocument;

    /** Constructor for DiffSetlement*/
    public DiffSetElement() {}

    /** Constructor for DiffSetlement*/
    public DiffSetElement( Annotation aLeftAnnotation,
                           Annotation aRightAnnotation,
                           int aLeftType,
                           int aRightType){
      leftAnnotation = aLeftAnnotation;
      rightAnnotation = aRightAnnotation;
      leftType = aLeftType;
      rightType = aRightType;
      keyDocument = null;
      respDocument = null;
    } // DiffSetElement

    /** Constructor for DiffSetlement with document name */
    public DiffSetElement( Annotation aLeftAnnotation,
                           Annotation aRightAnnotation,
                           int aLeftType,
                           int aRightType,
                           Document kDocument,
                           Document rDocument){
      leftAnnotation = aLeftAnnotation;
      rightAnnotation = aRightAnnotation;
      leftType = aLeftType;
      rightType = aRightType;
      keyDocument = kDocument;
      respDocument = rDocument;
    } // DiffSetElement
    
    /** Sets the left annotation*/
    public void setLeftAnnotation(Annotation aLeftAnnotation){
      leftAnnotation = aLeftAnnotation;
    } // setLeftAnnot

    /** Gets the left annotation*/
    public Annotation getLeftAnnotation(){
      return leftAnnotation;
    } // getLeftAnnotation

    /** Sets the right annotation*/
    public void setRightAnnotation(Annotation aRightAnnotation){
      rightAnnotation = aRightAnnotation;
    } // setRightAnnot

    /** Gets the right annotation*/
    public Annotation getRightAnnotation(){
      return rightAnnotation;
    } // getRightAnnotation

    /** Sets the left type*/
    public void setLeftType(int aLeftType){
      leftType = aLeftType;
    } // setLeftType

    /** Get the left type */
    public int getLeftType() {
      return leftType;
    } // getLeftType

    /** Sets the right type*/
    public void setRightType(int aRightType) {
      rightType = aRightType;
    } // setRightType

    /** Get the right type*/
    public int getRightType() {
      return rightType;
    } // getRightType

    /** Get Key document */
    public Document getKeyDocument() {
      return keyDocument;
    } // getKeyDocument

    /** Set Key document */
    public void setKeyDocument(Document aDoc) {
      keyDocument = aDoc;
    } // setKeyDocument

    /** Get Response document */
    public Document getResponseDocument() {
      return respDocument;
    } // getResponseDocument

    /** Set Response document */
    public void setResponseDocument(Document aDoc) {
      respDocument = aDoc;
    } // setResponseDocument
  } // classs DiffSetElement
} // class CorpusAnnotationDiff
