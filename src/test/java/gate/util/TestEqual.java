/*
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 12 July 2002
 *
 *  $Id: TestEqual.java 19618 2016-10-03 15:13:57Z markagreenwood $
 */

package gate.util;

import gate.*;

/**
 * This class provides some static utility methods such as equality test for
 * annotation sets and documents. They are mainly used by test classes.
 */
public class TestEqual {
  /**
   * Checks two documents for equality.
   * @param doc1 a document
   * @param doc2 another document
   * @return a boolean.
   */
  public static boolean documentsEqual(Document doc1, Document doc2){
    message = "";
    if(doc1 == null ^ doc2 == null){
      message = "Documents not equal: null<>non-null!";
      return false;
    }
    if(doc1 == null) return true;
    if(! check(doc1.getContent(), doc2.getContent())){
      message = "Document contents different!";
      return false;
    }

    if(! check(doc1.getAnnotations(), doc2.getAnnotations())){
      message = "Documents default AS not equal!";
      return false;
    }

    if(doc1 instanceof TextualDocument){
      if(doc2 instanceof TextualDocument){
        if(! check(((TextualDocument)doc1).getEncoding(),
                   ((TextualDocument)doc2).getEncoding())){
          message = "Textual documents with different encodings!";
          return false;
        }
      }else{
        message = "Documents not equal: textual<>non-textual!";
        return false;
      }
    }
    if(! check(doc1.getFeatures(), doc2.getFeatures())){
      message = "Documents features not equal!";
      return false;
    }

//needs friend declaration :(
//    if(!markupAware.equals(doc.markupAware)) return false;

    if(! check(doc1.getNamedAnnotationSets(),
               doc2.getNamedAnnotationSets())){
      message = "Documents named annots not equal!";
      return false;
    }

//    if(doc1 instanceof DocumentImpl){
//      if(doc2 instanceof DocumentImpl){
//        if(! check(((DocumentImpl)doc1).getNextNodeId(),
//                   ((DocumentImpl)doc2).getNextNodeId())){
//          message = "Documents next nodeID not equal!";
//          return false;
//        }
//        if(! check(((DocumentImpl)doc1).getNextAnnotationId(),
//                   ((DocumentImpl)doc2).getNextAnnotationId())){
//          message = "Documents next annotationIDs not equal!";
//          return false;
//        }
//      }else{
//        message = "Documents not equal: DocumentImpl<>non-DocumentImpl!";
//        return false;
//      }
//    }

    if(! check(doc1.getSourceUrl(), doc2.getSourceUrl())){
      message = "Documents sourceURLs not equal!";
      return false;
    }
    if(! (check(doc1.getSourceUrlStartOffset(),
               doc2.getSourceUrlStartOffset())
         &&
         check(doc1.getSourceUrlEndOffset(),
               doc2.getSourceUrlEndOffset()))){
      message = "Documents sourceURLOffsets not equal!";
      return false;
    }
    return true;
  }

  /** Two AnnotationSet are equal if their name, the documents of which belong
    *  to the AnnotationSets and annotations from the sets are the same
    */
  public static boolean annotationSetsEqual(AnnotationSet as1,
                                            AnnotationSet as2) {
    if(as1 == null ^ as2 == null) return false;
    if(as1 == null) return true;
    //Sets equality
    if(as1.size() != as2.size()) return false;
    try{
      if(! as1.containsAll(as2)) return false;
    }catch(ClassCastException unused)   {
      return false;
    }catch(NullPointerException unused) {
      return false;
    }

//removed to prevent infinite looping in testDocumentsEqual()
//    // verify the documents which they belong to
//    if (! check (as1.getDocument(), as2.getDocument())) return false;

    // verify the name of the AnnotationSets
    if (! check(as1.getName(), as2.getName())) return false;
    return true;
  } // equals




  /** Check: test 2 objects for equality */
  static protected boolean check(Object a, Object b) {
    if(a == null || b == null) return a == b;
    else return a.equals(b);
  } // check(a,b)

  /**
   * If set to true, explanation messages will be printed when a test fails.
   */
  public static String message = "";
}