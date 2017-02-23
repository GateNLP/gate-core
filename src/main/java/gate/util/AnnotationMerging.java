/**
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  $Id: IaaCalculation.java 9050 2007-09-04 10:42:12Z yaoyongli $
 */

package gate.util;

import gate.Annotation;
import gate.AnnotationSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
/**
 * Merging the annotations from different annotators. The input
 * is the array containing the annotation set for merging. The
 * output is a map, the key of which is the merged annotations
 * and the values of which represent those annotators who agree
 * on the merged annotation. Two merging methods are implemented.
 * One method selects one annotation if at least a pre-defined
 * number of annotators agree on it. If there are more than 
 * one merged annotations with the same span, the program selects only
 * one annotation from them with the maximal number of annotators 
 * on it. Another method selects only one 
 * annotation from those annotations with the same span, 
 * which majority of the annotators support. 
 */
public class AnnotationMerging {

  /**
   * Merge all annotationset from an array. If one annotation is in at least
   * numK annotation sets, then put it into the merging annotation set.
   */
  public static void mergeAnnotation(AnnotationSet[] annsArr, String nameFeat,
    HashMap<Annotation, String> mergeAnns, int numMinK, boolean isTheSameInstances) {
    int numA = annsArr.length;
    // First copy the annotatioin sets into a temp array
    @SuppressWarnings({"unchecked","rawtypes"})
    Set<Annotation>[] annsArrTemp = new Set[numA];
    for(int i = 0; i < numA; ++i) {
      if(annsArr[i] != null) {
        annsArrTemp[i] = new HashSet<Annotation>();
        for(Annotation ann : annsArr[i])
          annsArrTemp[i].add(ann);
      }
    }
    HashSet<String> featSet = new HashSet<String>();
    if(nameFeat != null) featSet.add(nameFeat);
    if(numMinK<1) numMinK=1;
    for(int iA = 0; iA < numA - numMinK + 1; ++iA) {
      if(annsArrTemp[iA] != null) {
        for(Annotation ann : annsArrTemp[iA]) {
          int numContained = 1;
          StringBuffer featAdd = new StringBuffer();
          featAdd.append(iA);
          StringBuffer featDisa = new StringBuffer();
          if(iA>0) {
            featDisa.append("0");
            for(int i=1; i<iA; ++i)
              featDisa.append("-"+i);
          }
          int numDisagreed = iA;
          for(int i = iA + 1; i < numA; ++i) {
            boolean isContained = false;
            if(annsArrTemp[i] != null) {
              Annotation annT = null;
              for(Annotation ann0 : annsArrTemp[i]) {
                if(ann0.isCompatible(ann, featSet)) {
                  ++numContained;
                  featAdd.append("-" + i);
                  annT = ann0;
                  isContained = true;
                  break;
                }
              }
              if(isContained)
                annsArrTemp[i].remove(annT);
            }
            if(!isContained){
              if(numDisagreed==0)
                featDisa.append(i);
              else featDisa.append("-"+i);
              ++numDisagreed;
            }
          }
          if(numContained >= numMinK) {
            mergeAnns.put(ann, featAdd.toString());
          } else if(isTheSameInstances && nameFeat != null) {
           ann.getFeatures().remove(nameFeat);
           mergeAnns.put(ann, featAdd.toString());
        }
        }
      }
    }
    //Remove the annotation in the same place
    removeDuplicate(mergeAnns);
    return;
  }
  /**
   * Merge all annotationset from an array. If one annotation is agreed by
   * the majority of the annotators, then put it into the merging annotation set.
   */
  public static void mergeAnnotationMajority(AnnotationSet[] annsArr, String nameFeat,
    HashMap<Annotation, String> mergeAnns, boolean isTheSameInstances) {
    int numA = annsArr.length;
    if(nameFeat == null) {
      mergeAnnogationMajorityNoFeat(annsArr, mergeAnns, isTheSameInstances);
      return;
    }
      
    // First copy the annotatioin sets into a temp array
    @SuppressWarnings({"unchecked","rawtypes"})
    Set<Annotation>[] annsArrTemp = new Set[numA];
    for(int i = 0; i < numA; ++i) {
      if(annsArr[i] != null) {
        annsArrTemp[i] = new HashSet<Annotation>();
        for(Annotation ann : annsArr[i])
          annsArrTemp[i].add(ann);
      }
    }
    for(int iA = 0; iA < numA; ++iA) {
      if(annsArrTemp[iA] != null) {
        for(Annotation ann : annsArrTemp[iA]) {
          int numDisagreed=0;
          //Already the iA annotators don't agree the annotation
          numDisagreed = iA;
          StringBuffer featDisa = new StringBuffer();
          if(iA>0) {
            featDisa.append("0");
            for(int i=1; i<iA; ++i)
              featDisa.append("-"+i);
          }
          HashMap<String,String>featOthers = new HashMap<String,String>();
          String featTh = null;
          if(ann.getFeatures().get(nameFeat)!= null)
              featTh =  ann.getFeatures().get(nameFeat).toString();
          
          featOthers.put(featTh, new Integer(iA).toString());
          HashMap<String,Annotation>annAll = new HashMap<String,Annotation>();
          annAll.put(featTh, ann);
          for(int i = iA + 1; i < numA; ++i) {
            boolean isContained = false;
            if(annsArrTemp[i] != null) {
              Annotation annT = null;
              for(Annotation ann0 : annsArrTemp[i]) {
                if(ann0.coextensive(ann)) {
                  String featValue = null;
                  if(ann0.getFeatures().get(nameFeat)!=null)
                    featValue = ann0.getFeatures().get(nameFeat).toString();
                  if(!featOthers.containsKey(featValue)) {
                    featOthers.put(featValue, new Integer(i).toString());
                    annAll.put(featValue, ann0);
                  }
                  else {
                    String str = featOthers.get(featValue);
                    featOthers.put(featValue, str+"-"+i);
                  }
                  annT = ann0;
                  isContained = true;
                  break;
                }
              }
              if(isContained) 
                annsArrTemp[i].remove(annT);
            }
            if(!isContained)  {
              if(numDisagreed==0)
                featDisa.append(i);
              else featDisa.append("-"+i);
              ++numDisagreed;
            }
          }//end of the loop for the following annotation set
          int numAgreed = -1;
          String agreeFeat = null;
          for(String str:featOthers.keySet()) {
            String str0 = featOthers.get(str);
            int num=1;
            while(str0.contains("-")) {
              ++num;
              str0 = str0.substring(str0.indexOf('-')+1);
            }
            if(numAgreed<num) {
              numAgreed = num;
              agreeFeat = str;
            }
          }
          if(numAgreed >= numDisagreed) {
            mergeAnns.put(annAll.get(agreeFeat), featOthers.get(agreeFeat));
          } else if(isTheSameInstances) {
            if(ann.getFeatures().get(nameFeat)!= null)
              ann.getFeatures().remove(nameFeat);
            mergeAnns.put(ann, featDisa.toString());
         }
        } //for each ann in the current annotation set
      }
    }
    return;
  }
  /** The majority merging method for the annotaiton not specifying any annotation
   * feature for label. 
   * */
  private static void mergeAnnogationMajorityNoFeat(AnnotationSet[] annsArr,
    HashMap<Annotation, String> mergeAnns, boolean isTheSameInstances) {
    int numA = annsArr.length;
    // First copy the annotatioin sets into a temp array
    @SuppressWarnings({"unchecked","rawtypes"})
    Set<Annotation>[] annsArrTemp = new Set[numA];
    for(int i = 0; i < numA; ++i) {
      if(annsArr[i] != null) {
        annsArrTemp[i] = new HashSet<Annotation>();
        for(Annotation ann : annsArr[i])
          annsArrTemp[i].add(ann);
      }
    }
    for(int iA = 0; iA < numA; ++iA) {
      if(annsArrTemp[iA] != null) {
        for(Annotation ann : annsArrTemp[iA]) {
          int numDisagreed=0;
          //Already the iA annotators don't agree the annotation
          numDisagreed = iA;
          StringBuffer featDisa = new StringBuffer();
          if(iA>0) {
            featDisa.append("0");
            for(int i=1; i<iA; ++i)
              featDisa.append("-"+i);
          }
          int numAgreed=1;
          StringBuffer featAdd = new StringBuffer();
          featAdd.append(iA);
          for(int i = iA + 1; i < numA; ++i) {
            boolean isContained = false;
            if(annsArrTemp[i] != null) {
              Annotation annT = null;
              for(Annotation ann0 : annsArrTemp[i]) {
                if(ann0.coextensive(ann)) {
                  ++numAgreed;
                  annT = ann0;
                  isContained = true;
                  featAdd.append("-"+i);
                  break;
                }
              }
              if(isContained) 
                annsArrTemp[i].remove(annT);
            }
            if(!isContained)  {
              if(numDisagreed==0)
                featDisa.append(i);
              else featDisa.append("-"+i);
              ++numDisagreed;
            }
          }//end of the loop for the following annotation set
          if(numAgreed >= numDisagreed) {
            mergeAnns.put(ann, featAdd.toString());
          } else if(isTheSameInstances) {
              mergeAnns.put(ann, featAdd.toString());
         }
        } //for each ann in the current annotation set
      }
    }
    return;
  }
  /** Remove the duplicate annotations from the merged annotations. */
  private static void removeDuplicate(HashMap<Annotation, String> mergeAnns) {
//  first copy the annotations into a tempory
    HashMap <Annotation, Integer> mergeAnnsNum = new HashMap<Annotation, Integer>();
    for(Annotation ann:mergeAnns.keySet()) {
      String str = mergeAnns.get(ann);
      int num=1;
      while(str.contains("-")) {
        ++num;
        str = str.substring(str.indexOf('-')+1);
      }
      mergeAnnsNum.put(ann, new Integer(num));
    }
    //remove the annotaitons having the same places
    for(Annotation ann:mergeAnnsNum.keySet()) {
      Annotation annT=null;
      int num0=-1;
      Vector<Annotation>sameAnns= new Vector<Annotation>();
      for(Annotation ann1:mergeAnnsNum.keySet()) {
        if(ann.coextensive(ann1)) {
          sameAnns.add(ann1);
          int num = mergeAnnsNum.get(ann1).intValue();
          if(num>num0) {
            annT = ann1;
            num0 = num;
          }
        }
      } //end the inner loop for merged annotations
      //Keep the one which most annotators agree on.
      sameAnns.remove(annT);
      //Remove all others 
      for(int i=0; i<sameAnns.size(); ++i)
        mergeAnns.remove(sameAnns.elementAt(i));
    }
  } 
  

  /**
   * Check if the annotation sets contain the same annotations.
   */
  public static boolean isSameInstancesForAnnotators(AnnotationSet[] annsA, int vsy) {
    int numAnnotators = annsA.length;
    if(annsA[0] == null) return false;
    for(Annotation ann : annsA[0]) {
      for(int iJud = 1; iJud < numAnnotators; ++iJud) {
        if(annsA[iJud] == null) return false;
        boolean isContained = false;
        for(Annotation ann1 : annsA[iJud]) {
          // If the ann is not the same
          if(ann.coextensive(ann1)) {
            isContained = true;
            break;
          }
        }
        if(!isContained) {
          if(vsy>0)
          System.out.println("The " + iJud + " annotator cause different");
          return false;
        }
      }// end of the loop for annotators
    }// end of loop for each annotation in one document
    // If the annotated instances are the same for every annotators.
    return true;
  }
}
