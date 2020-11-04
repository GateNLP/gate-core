/*
 *  Utils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution annotationSet file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Johann Petrak, 2010-02-05
 *
 */

package gate;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import gate.annotation.AnnotationSetImpl;
import gate.annotation.ImmutableAnnotationSetImpl;
import gate.creole.ConditionalSerialController;
import gate.creole.Plugin;
import gate.creole.RunningStrategy;
import gate.util.FeatureBearer;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.OffsetComparator;

/**
 * Various utility methods to make often-needed tasks more easy and
 * using up less code.  In Java code (or JAPE grammars) you may wish to
 * <code>import static gate.Utils.*</code> to access these methods without
 * having to qualify them with a class name.  In Groovy code, this class can be
 * used as a category to inject each utility method into the class of its first
 * argument, e.g.
 * <pre>
 * Document doc = // ...
 * Annotation ann = // ...
 * use(gate.Utils) {
 *   println "Annotation has ${ann.length()} characters"
 *   println "and covers the string \"${doc.stringFor(ann)}\""
 * }
 * </pre>
 *
 * @author Johann Petrak, Ian Roberts
 */
public class Utils {
  /**
   * Return the length of the document content covered by an Annotation as an
   * int -- if the content is too long for an int, the method will throw
   * a GateRuntimeException. Use getLengthLong(SimpleAnnotation ann) if
   * this situation could occur.
   * @param ann the annotation for which to determine the length
   * @return the length of the document content covered by this annotation.
   */
  public static int length(SimpleAnnotation ann) {
    long len = lengthLong(ann);
    if (len > java.lang.Integer.MAX_VALUE) {
      throw new GateRuntimeException(
              "Length of annotation too big to be returned as an int: "+len);
    } else {
      return (int)len;
    }
  }

  /**
   * Return the length of the document content covered by an Annotation as a
   * long.
   * @param ann the annotation for which to determine the length
   * @return the length of the document content covered by this annotation.
   */
  public static long lengthLong(SimpleAnnotation ann) {
    return ann.getEndNode().getOffset() -
       ann.getStartNode().getOffset();
  }

  /**
   * Return the length of the document as an
   * int -- if the content is too long for an int, the method will throw a
   * GateRuntimeException. Use getLengthLong(Document doc) if
   * this situation could occur.
   * @param doc the document for which to determine the length
   * @return the length of the document content.
   */
  public static int length(Document doc) {
    long len = doc.getContent().size();
    if (len > java.lang.Integer.MAX_VALUE) {
      throw new GateRuntimeException(
              "Length of document too big to be returned as an int: "+len);
    } else {
      return (int)len;
    }
  }

  /**
   * Return the length of the document as a long.
   * @param doc the document for which to determine the length
   * @return the length of the document content.
   */
  public static long lengthLong(Document doc) {
    return doc.getContent().size();
  }

  /**
   * Return the DocumentContent corresponding to the annotation.
   * <p>
   * Note: the DocumentContent object returned will also contain the
   * original content which can be accessed using the getOriginalContent()
   * method.
   * @param doc the document from which to extract the content
   * @param ann the annotation for which to return the content.
   * @return a DocumentContent representing the content spanned by the annotation.
   */
  public static DocumentContent contentFor(
          SimpleDocument doc, SimpleAnnotation ann) {
    try {
      return doc.getContent().getContent(
              ann.getStartNode().getOffset(),
              ann.getEndNode().getOffset());
    } catch(gate.util.InvalidOffsetException ex) {
      throw new GateRuntimeException(ex.getMessage());
    }
  }

  /**
   * Return the document text as a String corresponding to the annotation.
   * @param doc the document from which to extract the document text
   * @param ann the annotation for which to return the text.
   * @return a String representing the text content spanned by the annotation.
   */
  public static String stringFor(
          Document doc, SimpleAnnotation ann) {
    try {
      return doc.getContent().getContent(
              ann.getStartNode().getOffset(),
              ann.getEndNode().getOffset()).toString();
    } catch(gate.util.InvalidOffsetException ex) {
      throw new GateRuntimeException(ex.getMessage(),ex);
    }
  }

  
  /**
   * Return the cleaned document text as a String corresponding to the annotation.
   * (Delete leading and trailing whitespace; normalize 
   * internal whitespace to single spaces.)
   * @param doc the document from which to extract the document text
   * @param ann the annotation for which to return the text.
   * @return a String representing the text content spanned by the annotation.
   */
  public static String cleanStringFor(Document doc, SimpleAnnotation ann) {
    return cleanString(stringFor(doc, ann));
  }
  
  /**
   * Returns the document text between the provided offsets.
   * @param doc the document from which to extract the document text
   * @param start the start offset 
   * @param end the end offset
   * @return document text between the provided offsets
   */
  public static String stringFor(
          Document doc, Long start, Long end) {
    try {
      return doc.getContent().getContent(
              start,
              end).toString();
    } catch(gate.util.InvalidOffsetException ex) {
      throw new GateRuntimeException(ex.getMessage());
    }
  }

  
  /**
   * Return the cleaned document text between the provided offsets.
   * (Delete leading and trailing whitespace; normalize 
   * internal whitespace to single spaces.)
   * @param doc the document from which to extract the document text
   * @param start the start offset 
   * @param end the end offset
   * @return document text between the provided offsets
   */
  public static String cleanStringFor(Document doc, Long start, Long end) {
    return cleanString(stringFor(doc, start, end));
  }
  
  /**
   * Return the DocumentContent covered by the given annotation set.
   * <p>
   * Note: the DocumentContent object returned will also contain the
   * original content which can be accessed using the getOriginalContent()
   * method.
   * @param doc the document from which to extract the content
   * @param anns the annotation set for which to return the content.
   * @return a DocumentContent representing the content spanned by the
   * annotation set.
   */
  public static DocumentContent contentFor(
          SimpleDocument doc, AnnotationSet anns) {
    try {
      return doc.getContent().getContent(
              anns.firstNode().getOffset(),
              anns.lastNode().getOffset());
    } catch(gate.util.InvalidOffsetException ex) {
      throw new GateRuntimeException(ex.getMessage());
    }
  }

  /**
   * Return the document text as a String covered by the given annotation set.
   * @param doc the document from which to extract the document text
   * @param anns the annotation set for which to return the text.
   * @return a String representing the text content spanned by the annotation
   * set.
   */
  public static String stringFor(
          Document doc, AnnotationSet anns) {
    try {
      return doc.getContent().getContent(
              anns.firstNode().getOffset(),
              anns.lastNode().getOffset()).toString();
    } catch(gate.util.InvalidOffsetException ex) {
      throw new GateRuntimeException(ex.getMessage());
    }
  }

  /**
   * Return the cleaned document text as a String covered by the given annotation set.
   * (Delete leading and trailing whitespace; normalize 
   * internal whitespace to single spaces.)
   * @param doc the document from which to extract the document text
   * @param anns the annotation set for which to return the text.
   * @return a String representing the text content spanned by the annotation
   * set.
   */
  public static String cleanStringFor(Document doc, AnnotationSet anns) {
    return cleanString(stringFor(doc, anns));
  }
  
  /**
   * Return a cleaned version of the input String. (Delete leading and trailing
   * whitespace; normalize internal whitespace to single spaces; return an
   * empty String if the input contains nothing but whitespace, but null
   * if the input is null.)
   * @return a cleaned version of the input String.
   */
  public static String cleanString(String input) {
    if (input == null) {
      return null;
    }
    // implied else
    return input.replaceAll("\\s+", " ").trim();
  }
  
  /**
   * Get the start offset of an annotation.
   */
  public static Long start(SimpleAnnotation a) {
    return (a.getStartNode() == null) ? null : a.getStartNode().getOffset();
  }

  /**
   * Get the start offset of an annotation set.
   */
  public static Long start(AnnotationSet as) {
    return (as.firstNode() == null) ? null : as.firstNode().getOffset();
  }

  /**
   * Get the start offset of a document (i.e. 0L).
   */
  public static Long start(SimpleDocument d) {
    return Long.valueOf(0L);
  }

  /**
   * Get the end offset of an annotation.
   */
  public static Long end(SimpleAnnotation a) {
    return (a.getEndNode() == null) ? null : a.getEndNode().getOffset();
  }

  /**
   * Get the end offset of an annotation set.
   */
  public static Long end(AnnotationSet as) {
    return (as.lastNode() == null) ? null : as.lastNode().getOffset();
  }

  /**
   * Get the end offset of a document.
   */
  public static Long end(SimpleDocument d) {
    return d.getContent().size();
  }

  /**
   * Return a the subset of annotations from the given annotation set
   * that start exactly at the given offset.
   *
   * @param annotationSet the set of annotations from which to select
   * @param atOffset the offset where the annoation to be returned should start
   * @return an annotation set containing all the annotations from the original
   * set that start at the given offset
   */
  public static AnnotationSet getAnnotationsAtOffset(
          AnnotationSet annotationSet, Long atOffset) {
    // this returns all annotations that start at this atOffset OR AFTER!
    AnnotationSet tmp = annotationSet.get(atOffset);
    // so lets filter ...
    List<Annotation> ret = new ArrayList<Annotation>();
    Iterator<Annotation> it = tmp.iterator();
    while(it.hasNext()) {
      Annotation ann = it.next();
      if(ann.getStartNode().getOffset().equals(atOffset)) {
        ret.add(ann);
      }
    }
    return Factory.createImmutableAnnotationSet(annotationSet.getDocument(), ret);
  }

  public static AnnotationSet getAnnotationsEndingAtOffset(AnnotationSet annotationSet, Long endOffset) {
    List<Annotation> endsAt = new ArrayList<Annotation>();

    // start can't be negative
    Long start = endOffset > 0 ? endOffset - 1 : 0;

    // it seems we can ask for beyond the document without error
    Long end = endOffset + 1;

    // get annotations that overlap this bit
    AnnotationSet annotations = annotationSet.get(start,end);

    // filter to get just those that end at the offset
    for (Annotation a : annotations) {
      if (a.getEndNode().getOffset().equals(endOffset)) {
        endsAt.add(a);
      }
    }

    // return the annotations we've found, if any
    return Factory.createImmutableAnnotationSet(annotationSet.getDocument(), endsAt);
  }

  /**
   * Get all the annotations from the source annotation set that lie within
   * the range of the containing annotation.
   * 
   * @param sourceAnnotationSet the annotation set from which to select
   * @param containingAnnotation the annotation whose range must contain the
   * selected annotations
   * @return the AnnotationSet containing all annotations fully contained in
   * the offset range of the containingAnnotation
   */
  public static AnnotationSet getContainedAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation containingAnnotation) {
    return getContainedAnnotations(sourceAnnotationSet,containingAnnotation,"");
  }

  /**
   * Get all the annotations of type targetType
   * from the source annotation set that lie within
   * the range of the containing annotation.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param containingAnnotation the annotation whose range must contain the
   * @param targetType the type the selected annotations must have. If the
   * empty string, no filtering on type is done.
   * @return the AnnotationSet containing all annotations fully contained in
   * the offset range of the containingAnnotation
   */
  public static AnnotationSet getContainedAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation containingAnnotation,
    String targetType) {
    if(targetType.equals("")) {
      return sourceAnnotationSet.getContained(
        containingAnnotation.getStartNode().getOffset(),
        containingAnnotation.getEndNode().getOffset());
    } else {
      return sourceAnnotationSet.getContained(
        containingAnnotation.getStartNode().getOffset(),
        containingAnnotation.getEndNode().getOffset()).get(targetType);
    }
  }

  /**
   * Get all the annotations from the source annotation set that lie within
   * the range of the containing annotation set, i.e. within the offset range
   * between the start of the first annotation in the containing set and the
   * end of the last annotation in the annotation set. If the containing
   * annotation set is empty, an empty set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param containingAnnotationSet the annotation set whose range must contain
   * the selected annotations
   * @return the AnnotationSet containing all annotations fully contained in
   * the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getContainedAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet containingAnnotationSet) {
    return getContainedAnnotations(sourceAnnotationSet,containingAnnotationSet,"");
  }

  /**
   * Get all the annotations from the source annotation set with a type equal to
   * targetType that lie within
   * the range of the containing annotation set, i.e. within the offset range
   * between the start of the first annotation in the containing set and the
   * end of the last annotation in the annotation set. If the containing
   * annotation set is empty, an empty set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param containingAnnotationSet the annotation set whose range must contain
   * the selected annotations
   * @param targetType the type the selected annotations must have
   * @return the AnnotationSet containing all annotations fully contained in
   * the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getContainedAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet containingAnnotationSet,
    String targetType) {
    if(containingAnnotationSet.isEmpty() || sourceAnnotationSet.isEmpty()) {
      return Factory.createImmutableAnnotationSet(sourceAnnotationSet.getDocument(), null);
    }
    if(targetType.equals("")) {
      return sourceAnnotationSet.getContained(
        containingAnnotationSet.firstNode().getOffset(),
        containingAnnotationSet.lastNode().getOffset());
    } else {
      return sourceAnnotationSet.getContained(
        containingAnnotationSet.firstNode().getOffset(),
        containingAnnotationSet.lastNode().getOffset()).get(targetType);
    }
  }

  
  /**
   * Get all the annotations from the source annotation set that cover
   * the range of the specified annotation.
   * 
   * @param sourceAnnotationSet the annotation set from which to select
   * @param coveredAnnotation the annotation whose range must equal or lie within
   * the selected annotations
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the coveredAnnotation
   */
  public static AnnotationSet getCoveringAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation coveredAnnotation) {
    return getCoveringAnnotations(sourceAnnotationSet,coveredAnnotation,"");
  }

  /**
   * Get all the annotations of type targetType
   * from the source annotation set that cover
   * the range of the specified annotation.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param coveredAnnotation the annotation whose range must be covered
   * @param targetType the type the selected annotations must have. If the
   * empty string, no filtering on type is done.
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the coveredAnnotation
   */
  public static AnnotationSet getCoveringAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation coveredAnnotation,
    String targetType) {
    return sourceAnnotationSet.getCovering(targetType,
        coveredAnnotation.getStartNode().getOffset(),
        coveredAnnotation.getEndNode().getOffset());
  }

  /**
   * Get all the annotations from the source annotation set that cover
   * the range of the specified annotation set. If the covered
   * annotation set is empty, an empty set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param coveredAnnotationSet the annotation set whose range must be covered by
   * the selected annotations
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getCoveringAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet coveredAnnotationSet) {
    return getCoveringAnnotations(sourceAnnotationSet,coveredAnnotationSet,"");
  }

  /**
   * Get all the annotations from the source annotation set with a type equal to
   * targetType that cover
   * the range of the specified annotation set. If the specified
   * annotation set is empty, an empty set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param coveredAnnotationSet the annotation set whose range must
   * be covered by the selected annotations
   * @param targetType the type the selected annotations must have
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getCoveringAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet coveredAnnotationSet,
    String targetType) {
    if(coveredAnnotationSet.isEmpty() || sourceAnnotationSet.isEmpty()) {
      return Factory.createImmutableAnnotationSet(sourceAnnotationSet.getDocument(), null);
    }
    return sourceAnnotationSet.getCovering(targetType,
        coveredAnnotationSet.firstNode().getOffset(),
        coveredAnnotationSet.lastNode().getOffset());
  }

  
  
  
  /**
   * Get all the annotations from the source annotation set that
   * partly or totally overlap
   * the range of the specified annotation.
   * 
   * @param sourceAnnotationSet the annotation set from which to select
   * @param overlappedAnnotation the annotation whose range the selected
   * annotations must overlap
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the coveredAnnotation
   */
  public static AnnotationSet getOverlappingAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation overlappedAnnotation) {
    return getOverlappingAnnotations(sourceAnnotationSet,overlappedAnnotation,"");
  }

  /**
   * Get all the annotations of type targetType
   * from the source annotation set that partly or totally overlap
   * the range of the specified annotation.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param overlappedAnnotation the annotation whose range the selected
   * annotations must overlap
   * @param targetType the type the selected annotations must have. If the
   * empty string, no filtering on type is done.
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the coveredAnnotation
   */
  public static AnnotationSet getOverlappingAnnotations(
    AnnotationSet sourceAnnotationSet,
    Annotation overlappedAnnotation,
    String targetType) {
    
    
    if ( (targetType == null) || targetType.isEmpty()) {
      return sourceAnnotationSet.get(overlappedAnnotation.getStartNode().getOffset(),
          overlappedAnnotation.getEndNode().getOffset());
    }
    
    return sourceAnnotationSet.get(targetType,
        overlappedAnnotation.getStartNode().getOffset(),
        overlappedAnnotation.getEndNode().getOffset());
  }

  /**
   * Get all the annotations from the source annotation set that overlap
   * the range of the specified annotation set. If the overlapped
   * annotation set is empty, an empty set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param overlappedAnnotationSet the annotation set whose range must
   * be overlapped by the selected annotations
   * @return the AnnotationSet containing all annotations that fully cover
   * the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getOverlappingAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet overlappedAnnotationSet) {
    return getOverlappingAnnotations(sourceAnnotationSet,overlappedAnnotationSet,"");
  }

  
  /**
   * Get all the annotations from the source annotation set with a type equal to
   * targetType that partly or completely overlap the range of the specified 
   * annotation set. If the specified annotation set is empty, an empty 
   * set is returned.
   *
   * @param sourceAnnotationSet the annotation set from which to select
   * @param overlappedAnnotationSet the annotation set whose range must
   * be overlapped by the selected annotations
   * @param targetType the type the selected annotations must have
   * @return the AnnotationSet containing all annotations that partly or fully
   * overlap the offset range of the containingAnnotationSet
   */
  public static AnnotationSet getOverlappingAnnotations(
    AnnotationSet sourceAnnotationSet,
    AnnotationSet overlappedAnnotationSet,
    String targetType) {
    if(overlappedAnnotationSet.isEmpty() || sourceAnnotationSet.isEmpty()) {
      return Factory.createImmutableAnnotationSet(sourceAnnotationSet.getDocument(), null);
    }
    
    if ( (targetType == null) || targetType.isEmpty()) {
      return sourceAnnotationSet.get(overlappedAnnotationSet.firstNode().getOffset(),
          overlappedAnnotationSet.lastNode().getOffset());
    }
    
    return sourceAnnotationSet.get(targetType,
        overlappedAnnotationSet.firstNode().getOffset(),
        overlappedAnnotationSet.lastNode().getOffset());
  }


  /**
   * Return a List containing the annotations in the given annotation set, in
   * document order (i.e. increasing order of start offset).
   *
   * @param as the annotation set
   * @return a list containing the annotations from <code>as</code> in document
   * order.
   */
  public static List<Annotation> inDocumentOrder(AnnotationSet as) {
    List<Annotation> ret = new ArrayList<Annotation>();
    if(as != null) {
      ret.addAll(as);
      Collections.sort(ret, OFFSET_COMPARATOR);
    }
    return ret;
  }

  /**
   * A single instance of {@link OffsetComparator} that can be used by any code
   * that requires one.
   */
  public static final OffsetComparator OFFSET_COMPARATOR =
          new OffsetComparator();

  /**
   * Create a feature map from an array of values.  The array must have an even
   * number of items, alternating keys and values i.e. [key1, value1, key2,
   * value2, ...].
   *
   * @param values an even number of items, alternating keys and values.
   * @return a feature map containing the given items.
   */
  public static FeatureMap featureMap(Object... values) {
    FeatureMap fm = Factory.newFeatureMap();
    if(values != null) {
      for(int i = 0; i < values.length; i++) {
        fm.put(values[i], values[++i]);
      }
    }
    return fm;
  }

  /**
   * Create a feature map from an existing map (typically one that does not
   * itself implement FeatureMap).
   *
   * @param map the map to convert.
   * @return a new FeatureMap containing the same mappings as the source map.
   */
  public static FeatureMap toFeatureMap(Map<?,?> map) {
    FeatureMap fm = Factory.newFeatureMap();
    fm.putAll(map);
    return fm;
  }
  
  /** 
   * This method can be used to check if a ProcessingResource has 
   * a chance to be run in the given controller with the current settings.
   * <p>
   * That means that for a non-conditional controller, the method will return
   * true if the PR is part of the controller. For a conditional controller,
   * the method will return true if it is part of the controller and at least
   * once (if the same PR is contained multiple times) it is not disabled.
   * 
   * @param controller 
   * @param pr
   * @return true or false depending on the conditions explained above.
   */
  public static boolean isEnabled(Controller controller, ProcessingResource pr) {
    Collection<ProcessingResource> prs = controller.getPRs();
    if(!prs.contains(pr)) {
      return false;
    }
    if(controller instanceof ConditionalSerialController) {
      Collection<RunningStrategy> rss = 
        ((ConditionalSerialController)controller).getRunningStrategies();
      for(RunningStrategy rs : rss) {
        // if we find at least one occurrence of the PR that is not disabled
        // return true
        if(rs.getPR().equals(pr) && 
           rs.getRunMode() != RunningStrategy.RUN_NEVER) {
          return true;
        }
      }
      // if we get here, no occurrence of the PR has found or none that
      // is not disabled, so return false
      return false;
    }
    return true;
  }
  
  /**
   * Return the running strategy of the PR in the controller, if the controller
   * is a conditional controller. If the controller is not a conditional 
   * controller, null is returned. If the controller is a conditional controller
   * and the PR is contained multiple times, the running strategy for the 
   * first occurrence the is found is returned.
   * 
   * @param controller
   * @param pr
   * @return A RunningStrategy object or null
   */
  public static RunningStrategy getRunningStrategy(Controller controller,
          ProcessingResource pr) {
    if(controller instanceof ConditionalSerialController) {
      Collection<RunningStrategy> rss = 
        ((ConditionalSerialController)controller).getRunningStrategies();
      for(RunningStrategy rs : rss) {
        if(rs.getPR() == pr) {
          return rs;
        }
      } 
    }
    return null;
  }
  
  /**
   * Issue a message to the log but only if the same message has not
   * been logged already in the same GATE session.
   * This is intended for explanations or warnings that should not be 
   * repeated every time the same situation occurs.
   * 
   * @param logger - the logger instance to use
   * @param level  - a Log4J severity level for the message
   * @param message - the message itself
   * @deprecated Log4J support will be removed in future, please use SLF4J
   */
  @Deprecated
  public static void logOnce (Logger logger, Object level, String message) {
    if(!alreadyLoggedMessages.contains(message)) { 
    	switch (level.toString()) {
    		case "TRACE":
    			logger.trace(message);
    			break;
    		case "DEBUG":
    			logger.debug(message);
    			break;
    		case "INFO":
    			logger.info(message);
    			break;
    		case "WARN":
    			logger.warn(message);
    			break;
    		case "ERROR":
    		case "FATAL":
    			logger.error(message);
    			break;
    		default:
    			// unknown log level, should be impossible
	  }
      alreadyLoggedMessages.add(message);
    }
  }
  
  /**
   * Issue a message to the log but only if the same message has not
   * been logged already in the same GATE session.
   * This is intended for explanations or warnings that should not be 
   * repeated every time the same situation occurs.
   * 
   * @param logger - the logger instance to use
   * @param level  - an SLF4J severity level for the message
   * @param message - the message itself
   */
  public static void logOnce(Logger logger, Level level, String message) {
	  if (!alreadyLoggedMessages.contains(message)) {
		  switch (level) {
		  	case TRACE:
		  		logger.trace(message);
		  		break;
		  	case DEBUG:
	  			logger.debug(message);
		  		break;
		  	case INFO:
	  			logger.info(message);
		  		break;
		  	case WARN:
	  			logger.warn(message);
		  		break;
		  	case ERROR:
	  			logger.error(message);
		  		break;
		  	default:
		  		// unknown log level, should be impossible
		  }
		  alreadyLoggedMessages.add(message);
	  }
  }

  /**
   * Check if a message has already been logged or shown. This does not log
   * or show anything but only stores the message as one that has been shown
   * already if necessary and returns if the message has been shown or not.
   *
   * @param message - the message that should only be logged or shown once
   * @return - true if the message has already been logged or checked with
   * this method.
   *
   */
   public static boolean isLoggedOnce(String message) {
     boolean isThere = alreadyLoggedMessages.contains(message);
     if(!isThere) {
       alreadyLoggedMessages.add(message);
     }
     return isThere;
   }

  private static final Set<String> alreadyLoggedMessages = 
    Collections.synchronizedSet(new HashSet<String>());

  
  /**
   * Returns the only annotation that annset is expected to contains, throws an
   * exception if there is not exactly one annotation. This is useful when a
   * binding set is expected to contain exactly one interesting annotation.
   * 
   * @param annset the annotation set that is expected to contain exactly one annotation  
   * @return the one annotation or throws an exception if there are 0 or more than one annotations
   * 
   */
  public static Annotation getOnlyAnn(AnnotationSet annset) {
    if (annset.size() != 1) {
      throw new GateRuntimeException(
          "Annotation set does not contain exactly 1 annotation but "
              + annset.size());
    } else {
      return annset.iterator().next();
    }
  }

  /**
   * Add a new annotation to the output annotation set outSet, spanning the same
   * region as spanSet, and having the given type and feature map. The start and
   * end nodes of the new annotation will be new nodes. This method will convert
   * the checked InvalidOffsetException that can be raised by 
   * AnnotationSet.add to a GateRuntimeException.
   * 
   * @param outSet the annotation set where the new annotation will be added
   * @param spanSet an annotation set representing the span of the new annotation
   * @param type the annotation type of the new annotation
   * @param fm the feature map to use for the new annotation
   * @return Returns the Id of the added annotation
   */
  public static Integer addAnn(AnnotationSet outSet, AnnotationSet spanSet,
      String type, FeatureMap fm) {
    try {
      return outSet.add(start(spanSet), end(spanSet), type, fm);
    } catch (InvalidOffsetException ex) {
      throw new GateRuntimeException("Offset error when trying to add new annotation: ", ex);
    }
  }

  /**
   * Add a new annotation to the output annotation set outSet, spanning the 
   * given offset range, and having the given type and feature map. The start and
   * end nodes of the new annotation will be new nodes. This method will convert
   * the checked InvalidOffsetException that can be raised by 
   * AnnotationSet.add to a GateRuntimeException.
   * 
   * @param outSet outSet the annotation set where the new annotation will be added
   * @param startOffset the start offset of the new annotation
   * @param endOffset the end offset of the new annotation
   * @param type the annotation type of the new annotation
   * @param fm the feature map to use for the new annotation
   * @return Returns the Id of the added annotation
   */
  public static Integer addAnn(AnnotationSet outSet, long startOffset, long endOffset,
      String type, FeatureMap fm) {
    try {
      return outSet.add(startOffset, endOffset, type, fm);
    } catch (InvalidOffsetException ex) {
      throw new GateRuntimeException("Offset error when trying to add new annotation: ", ex);
    }
  }

  /**
   * Add a new annotation to the output annotation set outSet, covering the same
   * region as the annotation spanAnn, and having the given type and feature map. The start and
   * end nodes of the new annotation will be new nodes. This method will convert
   * the checked InvalidOffsetException that can be raised by 
   * AnnotationSet.add to a GateRuntimeException.
   * 
   * @param outSet the annotation set where the new annotation will be added
   * @param spanAnn an annotation representing the span of the new annotation
   * @param type the annotation type of the new annotation
   * @param fm the feature map to use for the new annotation
   * @return Returns the Id of the added annotation
   */
  public static Integer addAnn(AnnotationSet outSet, Annotation spanAnn,
      String type, FeatureMap fm) {
    try {
      return outSet.add(start(spanAnn), end(spanAnn), type, fm);
    } catch (InvalidOffsetException ex) {
      throw new GateRuntimeException("Offset error adding new annotation: ", ex);
    }
  }
  
  static private Pattern nsQNamePattern = Pattern.compile("^(.*:)(.+)$");
  /**
   * Expand both namespace prefixes and base-uris, if possible.
   * This will expand the String toExpand according to the following rules:
   * <ul>
   * <li>if toExpand is a qName and does start with a name prefix in the form
   * "somens:" or ":", then the name prefix is looked up in the prefixes
   * map and replaced with the URI prefix found there. If the prefix could not
   * be found a GateRuntimeException is thrown.
   * <li>if toExpand does not start with a name prefix, the entry with
   * an empty string as the key is retrieved from the prefixes map and 
   * used as a baseURI: the result is the baseURI and the toExpand String
   * concatenated. If no entry with an empty string is found in the map, a 
   * GateRuntimeException is thrown.   * 
   * </ul>
   * 
   * This method can therefore be used to expand both base uris and namespaces.
   * <p>
   * If the map only contains a basename uri (if the only entry is for the
   * empty string key) then name space prefixes are not checked: in this 
   * case, the toExpand string may contain an unescaped colon. 
   * If the map does not contain a basename URI (if there is no entry for the
   * empty string key) then all toExpand strings are expected to be qNames.
   * <p>
   * NOTE: the name prefixes in the prefixes map must include the trailing colon!
   * 
   * @param toExpand the URI portion to expand as a String
   * @param prefixes a map from name prefixes to URI prefixes
   * @return a String with name prefixes or base URI expanded 
   */
  public static String expandUriString(String toExpand, Map<String,String> prefixes ) {
    // lets see if we have a basename entry in the map
    String baseUri = prefixes.get("");
    // if there is a baseURI and it is the only entry, just prefix toExpand with
    // it, no matter what
    if(baseUri != null && prefixes.size() == 1) {
      return baseUri+toExpand;
    }
    
    // if the toExpand string starts with .*:, interpret this as the name space
    Matcher m = nsQNamePattern.matcher(toExpand);
    if (m.matches()) {
      String prefix = m.group(1);
      String lname = m.group(2);
      String uriPrefix = prefixes.get(prefix);
      if(uriPrefix == null) {
        throw new GateRuntimeException("name prefix not found in prefix map for  "+toExpand);
      } else {
        return uriPrefix+lname;
      }      
    } else {
      // this is not a qName, try to expand with the baseURI
      if(baseUri == null) {
        throw new GateRuntimeException("No base Uri in prefix map for "+toExpand);
      } else {
        return baseUri + toExpand;
      }
    }
  }
  /**
   * Compact an URI String using base URI and namespace prefixes.
   * The prefixes map, which maps name prefixes of the form "ns:" or the empty
   * string to URI prefixes is searched for the first URI prefix in the value
   * set that matches the beginning of the uriString. The corresponding name prefix
   * is then used to replace that URI prefix.
   * In order to control which URI prefix is matched first if the map contains
   * several prefixes which can all match some URIs, a LinkedHashMap can be 
   * used so that the first matching URI prefix will be deterministic.
   *  
   * @param uriString a full URI String that should get shortened using prefix names or a base URI
   * @param prefixes a map containing name prefixes mapped to URI prefixes (same as for expandUriString)
   * @return a shortened URI where the URI prefix is replaced with a prefix name or the empty string
   */
  public static String shortenUriString(String uriString, Map<String, String> prefixes) {
    // get the URI prefixes 
    String uriPrefix = "";
    String namePrefix = "";
    for(Map.Entry<String,String> entry : prefixes.entrySet()) {
      String np = entry.getKey();
      String uri = entry.getValue();
      if(uriString.startsWith(uri)) {
        uriPrefix = uri;
        namePrefix = np;
        break;
      }
    }
    if(uriPrefix.equals("")) {
      throw new GateRuntimeException("No prefix found in prefixes map for "+uriString);
    }
    return namePrefix + uriString.substring(uriPrefix.length());
  }
  
  /**
   * Get all the annotations from the source annotation set that start and end 
   * at exactly the same offsets as the given annotation set.
   * 
   * @param source the annotation set from which to select
   * @param coextSet the annotation set from which to take the start and end offsets
   * @return the AnnotationSet containing all annotations exactly coextensive with coextSet
   */
  public static AnnotationSet getCoextensiveAnnotations(AnnotationSet source, AnnotationSet coextSet) {
    return getCoextensiveAnnotationsWorker(source, null, start(coextSet), end(coextSet));
  }
  /**
   * Get all the annotations from the source annotation set that start and end 
   * at exactly the same offsets as the given annotation set and are of the 
   * specified type.
   * 
   * @param source the annotation set from which to select
   * @param coextSet the annotation set from which to take the start and end offsets
   * @param type the desired annotation type of the annotations to return
   * @return the AnnotationSet containing all annotations exactly coextensive with coextSet and of the
   * specified type
   */
  public static AnnotationSet getCoextensiveAnnotations(AnnotationSet source, AnnotationSet coextSet, String type) {
    return getCoextensiveAnnotationsWorker(source, type, start(coextSet), end(coextSet));
  }
  /**
   * Get all the annotations from the source annotation set that start and end 
   * at exactly the same offsets as the given annotation.
   * 
   * @param source the annotation set from which to select
   * @param coextAnn the annotation from which to take the start and end offsets
   * @return the AnnotationSet containing all annotations exactly coextensive with coextAnn
   */
  public static AnnotationSet getCoextensiveAnnotations(AnnotationSet source, Annotation coextAnn) {
    return getCoextensiveAnnotationsWorker(source, null, start(coextAnn), end(coextAnn));
  }
  /**
   * Get all the annotations from the source annotation set that start and end 
   * at exactly the same offsets as the given annotation and have the specified type.
   * 
   * @param source the annotation set from which to select
   * @param coextAnn the annotation from which to take the start and end offsets
   * @return the AnnotationSet containing all annotations exactly coextensive with coextAnn and 
   * having the specified type.
   */
  public static AnnotationSet getCoextensiveAnnotations(AnnotationSet source, Annotation coextAnn, String type) {
    return getCoextensiveAnnotationsWorker(source, type, start(coextAnn), end(coextAnn));
  }

  private static AnnotationSet getCoextensiveAnnotationsWorker(AnnotationSet source,
      String type, long start, long end) {
    if (source instanceof gate.annotation.AnnotationSetImpl) {
      AnnotationSet ret = ((AnnotationSetImpl) source).getStrict(start,
          end);
      if (type != null) {
        return ret.get(type);
      } else {
        return ret;
      }
    } else {
      AnnotationSet annset = source.getContained(start, end);
      List<Annotation> annotationsToAdd = new ArrayList<Annotation>();
      for (Annotation ann : annset) {
        if (start(ann) == start && end(ann) == end) {
          if (type == null || ann.getType().equals(type)) {
            annotationsToAdd.add(ann);
          }
        }
      }
      return Factory.createImmutableAnnotationSet(source.getDocument(), annotationsToAdd);
    }
  }
  
  /**
   * This will replace all occurrences of variables of the form $env{name}, 
   * $prop{name}, $doc{featname}, $pr_parm{inputAS} or $$env{name} etc in a String.
   * 
   * The source for replacing the variable can be  environment variables,
   * system properties, or arbitrary maps or resources specified when calling
   * the method.
   * <p>
   * Examples:
   * <ul>
   * <li><code>replaceVariablesInString("text $env{GATE_HOME} more text")</code>:
   * returns "text /path/to/gate more text" if the environment variable 
   * "GATE_HOME" was set to "/path/to/gate"
   * <li><code>replaceVariablesInString("text $pr{myfeature1} more text",pr1)</code>:
   * returns "myvalue1" if the feature map of the processing resource pr1 
   * contains an entry with key "myfeature" and value "myvalue"
   * <li><code>replaceVariablesInString("text ${somekey} more text",map1,map2,resource1,map3)</code>:
   *  this will
   * find the value of an entry with key "somekey" in the first Map object specified
   * in the parameter list of the method.
   * </ul>
   * <p>
   * The possible sources for finding values for a variable are:
   * <ul>
   * <li><code>System.getenv()</code>: for variables of the form $env{name}
   * <li><code>System.getProperties()</code>: for variables of the form $prop{name}
   * <li><code>Resource</code>: the feature map of any resource which is specified in the 
   * list of objects is used for variables of the form $resource{name} or 
   * for variables of the form $corpus{name} if the resource is a corpus, for
   * $pr{name} if the resource is a processing resource and so on. If the 
   * resource is a processing resource its 
   * <li><code>FeatureMap</code> or <code>Map</code>: any feature map or 
   * Map which can be used to look up String keys can be specified
   * as a source and will be used for variables of the form ${name}.
   * </ul>
   * <p>
   * The value substituted is converted to a string using the toString() 
   * method of whatever object is stored in the map. If the value returned
   * by Map.get(key) is null, no substitution is carried out and the 
   * variable is left unchanged in the string.
   * <p>
   * The following variable constructs are supported:
   * <ul>
   * <li>$env{name} will be replaced with the value from the environment variables map 
   * from System.getenv() and nothing else.
   * <li>$prop{name} will be replaced with the value of the properties map 
   * from System.getProperties() and nothing else.
   * <li>$controller{name} will be replaced with the value of a feature from the FeatureMap
   * of the first resource of type Controller found in the argument list.
   * <li>$corpus{name} will be replaced with the value of a feature from the FeatureMap
   * of the first resource of type Corpus found in the argument list.
   * <li>$pr{name} will be replaced with the value of a feature from the FeatureMap
   * of the first resource of type ProcessingResource found in the argument list.
   * <li>$pr_parm{name} will be replaced with the value of the parameter 'name'
   * of the first resource of type ProcessingResource found in the argument list.
   * This can be especially useful to replace a variable in one parameter with
   * the value of another, potentially hidden, parameter of the same PR.
   * <li>$doc{name} will be replaced with the value of a feature from the FeatureMap
   * of the first resource of type Document found in the argument list.
   * <li>$resource{name} will be replaced with the value of a feature from the FeatureMap
   * of the first resource of type Resource found in the argument list.
   * </ul>
   * <p>
   * If two dollar characters are used instead of one, the replacement string
   * will in turn be subject to replacement, e.g. $$env{abc} could get replaced
   * with the replacement string '$corpus{f1}' which would in turn get replaced
   * with the value of the feature 'f1' from the feature set of the first 
   * corpus in the parameter list that has a value for that feature.
   *  
   */
  @SuppressWarnings("unchecked")
  public static String replaceVariablesInString(
          String string, Object... sources)
  {
    // shortcut for strings where no replacement is possible (minimum content 
    // would have to be $pr{x}
    if(string == null || string.isEmpty() || string.length() < 6) { return string; }
    Matcher matcher = varnamePattern.matcher(string);
    int findFrom = 0;
    int lastEnd = 0;
    StringBuilder sb = new StringBuilder(string.length()*2);
    while(findFrom < string.length() && matcher.find(findFrom)) {
      String dollars = matcher.group(1);
      String type = matcher.group(2);
      String varname = matcher.group(3);
      int matchStart = matcher.start();
      // whenever we have found something, we can immediately move the part
      // from the last end of match to the new start of match to the 
      // return string, that is just unmodified string ...
      // But only if the length is > 0
      if((matchStart - lastEnd) > 0) {
        sb.append(string.substring(lastEnd,matchStart));
      }
      lastEnd = matcher.end();
      Object value = null;
      // for each match we find, go through all the sources in the order
      // listed and if the type of the source matches the requested type
      // then try to look the variable up. If we find something use it and
      // finish looking, otherwise continue until all sources have been 
      // exhausted. 
      // A variable where no value has been found anywhere is not replaced.
      // If a variable got replaced and it was a variable that started with
      // two dollar signs, then the replacement value is first getting 
      // recursively replaced too.
      if(type.equals("env")) {
        value = System.getenv().get(varname);
      } else if(type.equals("prop")) {
        value = System.getProperties().get(varname);
      } else {
        for(Object source : sources) {
          if(type.isEmpty()) { // an empty variable type matches only maps from the sources
            if(source instanceof Map) {
              value = ((Map<String,?>)source).get(varname);
            }
          } else if(type.equals("pr") && (source instanceof ProcessingResource)) {
            value = ((FeatureBearer)source).getFeatures().get(varname);
          } else if(type.equals("pr_parm") && (source instanceof ProcessingResource)) {
            try {
              value = ((ProcessingResource)source).getParameterValue(varname);
            } catch(Exception ex) {
              // do nothing, leave the value null
            }
          } else if(type.equals("doc") && (source instanceof Document)) {
            value = ((FeatureBearer)source).getFeatures().get(varname);
          } else if(type.equals("controller") && (source instanceof Controller)) {
            value = ((FeatureBearer)source).getFeatures().get(varname);
          } else if(type.equals("corpus") && (source instanceof Corpus)) {
            value = ((FeatureBearer)source).getFeatures().get(varname);
          } else if(type.equals("resource") && (source instanceof Resource)) {
            value = ((FeatureBearer)source).getFeatures().get(varname);
          }
          if(value != null) {
            break;
          }
        } // for source : sources
      }
      // only do anything at all if we found a value for this parameter
      if(value != null) {
        String replacement = value.toString();
        // if we had double-dollars, first do the recursive replacement ...
        if(dollars.equals("$$")) {
          replacement = replaceVariablesInString(replacement, sources);
        }
        sb.append(replacement);
      } else {
        sb.append(matcher.group());
        // the first character after the match
      }
      findFrom = matcher.end();
    } // while matcher.find ...
    // if we have some unmatched string left over, append it too
    if(lastEnd < string.length()) {
      sb.append(string.substring(lastEnd));
    }
    return sb.toString();    
  }
  private static final Pattern varnamePattern = Pattern.compile("(\\$\\$?)([a-zA-Z]*)\\{([^}]+)\\}");
  
  /**
   * Load a plugin from the default GATE plugins directory.
   * 
   * This will load the plugin with the specified directory name from the
   * default GATE plugins path, if GATE knows its own location. 
   * 
   * @param dirName The directory name of the plugin within the standard GATE plugins directory.
   */
  @Deprecated
  public static void loadPlugin(String dirName) {
    File gatehome = Gate.getGateHome();
    if(gatehome == null) {
      throw new GateRuntimeException("Cannot load Plugin, Gate home location not known");
    }
    File pluginDir = new File(new File(gatehome,"plugins"),dirName);
    loadPlugin(pluginDir);
  }
  
  /**
   * Load a plugin from the specified directory.
   * 
   * This will load the plugin from the directory path specified as a File object.
   * 
   */
  public static void loadPlugin(File pluginDir) {
    try {
      Gate.getCreoleRegister().registerPlugin(new Plugin.Directory(pluginDir.toURI().toURL()));
    } catch (Exception ex) {
      throw new GateRuntimeException("Could not register plugin directory "+pluginDir,ex);
    }
  }
  
  /**
   * Return the given set with the given annotations removed.
   * 
   * This returns a new immutable annotation set, which contains all the annotations from origSet
   * except the given annotations. The removal is not based on equality but on the id of the 
   * annotation: an annotation in origSet which has the same id as the annotation except is removed
   * in the returned set.
   * <p>
   * NOTE: Annotation ids are only unique within a document, so you should never mix annotations
   * from different documents when using this method!
   * 
   * @param origSet The annotation set from which to remove the given annotation
   * @param except The annotation to remove from the given set
   * @return A new immutable annotation set with the given annotation removed from the original set
   */
  public static AnnotationSet minus(AnnotationSet origSet, Annotation... except) {
    return minus(origSet, Arrays.asList(except));
  }
 
  /**
   * Return the given set with the given annotations removed.
   * 
   * This returns a new immutable annotation set, which contains all the annotations from origSet
   * except the annotations given in the collection of exceptions. 
   * The removal is not based on equality but on the id of the 
   * annotations: an annotation in origSet which has the same id as an annotation from the exceptions
   * is removed in the returned set.
   * <p>
   * NOTE: Annotation ids are only unique within a document, so you should never mix annotations
   * from different documents when using this method!
   * 
   * @param origSet The annotation set from which to remove the given exceptions
   * @param exceptions The annotations to remove from the given set
   * @return A new immutable annotation set with the exceptions removed from the original set
   */
  public static AnnotationSet minus(AnnotationSet origSet, Collection<Annotation> exceptions) {
    Set<Integer> ids = new HashSet<Integer>();
    for(Annotation exception : exceptions) {
      ids.add(exception.getId());
    }
    List<Annotation> tmp = new ArrayList<Annotation>();
    for(Annotation ann : origSet) {
      if(!ids.contains(ann.getId())) {
        tmp.add(ann);
      }
    }
    return new ImmutableAnnotationSetImpl(origSet.getDocument(),tmp);
  }
 
  /**
   * Return the given set with the given annotations added.
   * 
   * This returns a new immutable annotation set, which contains all the annotations from origSet
   * plus the given annotations to add. The addition is not based on equality but on the id of the 
   * annotations: any new annotation is added if its annotation id differs from all the ids
   * already in the set.
   * <p>
   * NOTE: Annotation ids are only unique within a document, so you should never mix annotations
   * from different documents when using this method!
   * 
   * @param origSet The annotation set from which to remove the given exceptions
   * @param toAdd The annotations to add to the given set
   * @return A new immutable annotation set with the given annotations added
   */
  public static AnnotationSet plus(AnnotationSet origSet, Annotation... toAdd) {
    return plus(origSet,Arrays.asList(toAdd));
  }
  
  /**
   * Return the given set with the given annotations added.
   * 
   * This returns a new immutable annotation set, which contains all the annotations from origSet
   * plus the given annotations added. The addition is not based on equality but on the id of the 
   * annotations: any new annotation is added if its annotation id differs from all the ids
   * already in the set.
   * <p>
   * NOTE: Annotation ids are only unique within a document, so you should never mix annotations
   * from different documents when using this method!
   * 
   * @param origSet The annotation set from which to remove the given exceptions
   * @param toAdd A collection of annotations to add to the original set
   * @return A new immutable annotation set with the annotations from the collection added.
   */
  public static AnnotationSet plus(AnnotationSet origSet, Collection<Annotation> toAdd) {
    Set<Integer> ids = new HashSet<Integer>();
    for(Annotation orig : origSet) {
      ids.add(orig.getId());
    }
    List<Annotation> tmp = new ArrayList<Annotation>();
    tmp.addAll(origSet);
    for(Annotation ann : toAdd) {
      if(!ids.contains(ann.getId())) {
        tmp.add(ann);
      }
    }
    return new ImmutableAnnotationSetImpl(origSet.getDocument(),tmp);    
  }
  
  
  /**
   * Return the subset from the original set that matches one of the given annotations.
   * 
   * This returns a new immutable annotation set, which contains all the annotations from origSet
   * which are also among the annotations given. The check for matching annotations is not based 
   * on equality but on the id of the 
   * annotations: an annotation from the original set is included in the returned set if its 
   * annotation id matches the annotation id of any of the annotations given.
   * <p>
   * NOTE: Annotation ids are only unique within a document, so you should never mix annotations
   * from different documents when using this method!
   * 
   * @param origSet The annotation set from which to select only the given annotations.
   * @param others the given annotations
   * @return A new immutable annotation set with the interesection between original set and given annotations
   */
  public static AnnotationSet intersect(AnnotationSet origSet, Annotation... others) {
    return intersect(origSet,Arrays.asList(others));
  }
  
  public static AnnotationSet intersect(AnnotationSet origSet, Collection<Annotation> others) {
    if(others.isEmpty()) {
      return new ImmutableAnnotationSetImpl(origSet.getDocument(),null);
    }
    Set<Integer> ids = new HashSet<Integer>();
    for(Annotation other : others) {
      ids.add(other.getId());
    }
    List<Annotation> tmp = new ArrayList<Annotation>();
    for(Annotation ann : origSet) {
      if(ids.contains(ann.getId())) {
        tmp.add(ann);
      }
    }
    return new ImmutableAnnotationSetImpl(origSet.getDocument(),tmp);    
  }
  
  public static URL resolveURL(String url) throws IOException {
    while (true) {
      // while we are still following redirects...

      // create an actual URL instance from the string representation
      URL resourceUrl = new URL(url);

      if (!resourceUrl.getProtocol().startsWith("http"))
          return resourceUrl;

      // open a connection to the URL and...
      HttpURLConnection conn = (HttpURLConnection) resourceUrl.openConnection();

      // set a bunch of connection properties
      conn.setRequestMethod("HEAD");
      conn.setConnectTimeout(30000);
      conn.setReadTimeout(30000);
      conn.setInstanceFollowRedirects(false); // Make the logic below easier to detect redirections

      switch (conn.getResponseCode()) {
      case HttpURLConnection.HTTP_MOVED_PERM:
      case HttpURLConnection.HTTP_MOVED_TEMP:
        // if we've hit a redirect then get the location from the header
        String location = conn.getHeaderField("Location");
        location = URLDecoder.decode(location, "UTF-8");
        URL base = new URL(url);
        URL next = new URL(base, location); // Deal with relative URLs
        url = next.toExternalForm();
        continue;
      }

      // we've found a URL without a redirect so at this point we can stop
      return resourceUrl;
    }
  }

}
