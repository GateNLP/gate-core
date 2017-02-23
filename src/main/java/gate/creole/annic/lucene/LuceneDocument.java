/*
 *  LuceneDocument.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneDocument.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.Annotation;
import gate.AnnotationSet;
import gate.FeatureMap;
import gate.annotation.AnnotationSetImpl;
import gate.creole.annic.Constants;
import gate.creole.annic.apache.lucene.analysis.Token;
import gate.creole.annic.apache.lucene.document.Document;
import gate.creole.annic.apache.lucene.document.Field;
import gate.util.Err;
import gate.util.GateRuntimeException;
import gate.util.InvalidOffsetException;
import gate.util.OffsetComparator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Given an instance of Gate Document, this class provides a method to convert
 * it into the format that lucene can understand and can store in its indexes.
 * This class also stores the tokenStream on the disk in order to retrieve it at
 * the time of searching
 * 
 * @author niraj
 * 
 */
public class LuceneDocument {

  /**
   * Given an instance of Gate Document, it converts it into the format that
   * lucene can understand and can store in its indexes. This method also stores
   * the tokenStream on the disk in order to retrieve it at the time of
   * searching
   */
  public List<Document> createDocuments(String corpusPersistenceID,
    gate.Document gateDoc, String documentID,
    List<String> annotSetsToInclude, List<String> annotSetsToExclude,
    List<String> featuresToInclude, List<String> featuresToExclude,
    String indexLocation, String baseTokenAnnotationType,
    Boolean createTokensAutomatically, String indexUnitAnnotationType) {

    if(baseTokenAnnotationType != null)
      baseTokenAnnotationType = baseTokenAnnotationType.trim();

    List<Document> toReturnBack = new ArrayList<Document>();
    List<String> annotSetsToIndex = new ArrayList<String>();

    // by default merge set must be created
    //boolean createMergeSet = true;

    // if user has provided annotation sets to include, we don't bother
    // about annotation sets to exclude
    if(annotSetsToInclude.size() > 0) {
      annotSetsToIndex = annotSetsToInclude;

      // if there's only one annotation to index, we don't need to
      // create a MergeSet
      //if(annotSetsToIndex.size() == 1) createMergeSet = false;
    }
    else if(annotSetsToExclude.size() > 0) {
      // if there were no annotation sets to include, check if user has
      // provided any annotation sets to exclude
      // if so, we need to index all annotation sets but provided in the
      // annotationsetstoexclude list

      Set<String> namedAnnotSets = new HashSet<String>();
      if(gateDoc.getNamedAnnotationSets() != null
        && gateDoc.getNamedAnnotationSets().keySet() != null) {
        namedAnnotSets = gateDoc.getNamedAnnotationSets().keySet();
      }

      for(String setName : namedAnnotSets) {
        if(annotSetsToExclude.contains(setName)) continue;
        annotSetsToIndex.add(setName);
      }

      if(!annotSetsToExclude.contains(Constants.DEFAULT_ANNOTATION_SET_NAME)) {
        annotSetsToIndex.add(Constants.DEFAULT_ANNOTATION_SET_NAME);
      }
    }
    else {
      // if both annotation sets to include and annotation sets to
      // exclude are empty
      // we need to index all annotation sets
      Set<String> namedAnnotSets = new HashSet<String>();
      if(gateDoc.getNamedAnnotationSets() != null
        && gateDoc.getNamedAnnotationSets().keySet() != null) {
        namedAnnotSets = gateDoc.getNamedAnnotationSets().keySet();
      }

      for(String setName : namedAnnotSets) {
        annotSetsToIndex.add(setName);
      }
      annotSetsToIndex.add(Constants.DEFAULT_ANNOTATION_SET_NAME);
    }

    // lets find out the annotation set that contains tokens in it
    AnnotationSet baseTokenAnnotationSet = null;

    // search in annotation sets to find out which of them has the
    // baseTokenAnnotationType annotations
    // initially this is set to false
    boolean searchBaseTokensInAllAnnotationSets = false;
    boolean searchIndexUnitInAllAnnotationSets = false;

    // this variable tells whether we want to create manual tokens or
    // not
    boolean createManualTokens = false;

    // lets check if user's input is setName.basetokenAnnotationType
    int index = -1;
    if(baseTokenAnnotationType != null && baseTokenAnnotationType.length() > 0)
      index = baseTokenAnnotationType.lastIndexOf('.');

    // yes it is, find out the annotationset name and the
    // basetokenAnnotationType
    if(index >= 0) {

      // set name
      String setName = baseTokenAnnotationType.substring(0, index);

      // token type
      baseTokenAnnotationType =
        baseTokenAnnotationType.substring(index + 1, baseTokenAnnotationType
          .length());

      // check if user has asked to take tokens from the default
      // annotation set
      if(setName.equals(Constants.DEFAULT_ANNOTATION_SET_NAME))
        baseTokenAnnotationSet =
          gateDoc.getAnnotations().get(baseTokenAnnotationType);
      else baseTokenAnnotationSet =
        gateDoc.getAnnotations(setName).get(baseTokenAnnotationType);

      // here we check if the baseTokenAnnotationSet is null or its size
      // is 0
      // if so, we'll have to find out in all annotation sets for the
      // base token annotation type
      if(baseTokenAnnotationSet == null || baseTokenAnnotationSet.size() == 0) {
        System.err.println("Base Tokens " + baseTokenAnnotationType
          + " counldn't be found under the specified annotation set " + setName
          + "\n searching them in other annotation sets");
        searchBaseTokensInAllAnnotationSets = true;
      }
    }
    else {

      // either baseTokenAnnotation type is null or user hasn't provided
      // any annotaiton set name
      // so we search in all annotation sets
      searchBaseTokensInAllAnnotationSets = true;
    }

//    if(searchBaseTokensInAllAnnotationSets) {
//      System.out.println("Searching for the base token annotation type \""
//        + baseTokenAnnotationType + "\"in all sets");
//    }

    if(baseTokenAnnotationType != null && baseTokenAnnotationType.length() > 0
      && searchBaseTokensInAllAnnotationSets) {
      // we set this to true and if we find basetokens in any of the
      // annotationsets to index
      // we will set this to false
      createManualTokens = true;

      for(String aSet : annotSetsToIndex) {
        if(aSet.equals(Constants.DEFAULT_ANNOTATION_SET_NAME)) {
          AnnotationSet tempSet =
            gateDoc.getAnnotations().get(baseTokenAnnotationType);
          if(tempSet.size() > 0) {
            baseTokenAnnotationSet = tempSet;
//            System.out.println("found in default annotation set");
            createManualTokens = false;
            break;
          }
        }
        else {
          AnnotationSet tempSet =
            gateDoc.getAnnotations(aSet).get(baseTokenAnnotationType);
          if(tempSet.size() > 0) {
            baseTokenAnnotationSet = tempSet;
//            System.out.println("found in "+aSet);
            createManualTokens = false;
            break;
          }
        }
      }
    }

    // if baseTokenAnnotaitonType is null or an empty string
    // we'll have to create tokens ourselves
    if(baseTokenAnnotationType == null || baseTokenAnnotationType.length() == 0)
      createManualTokens = true;

    // lets check if we have to create ManualTokens
    if(createManualTokens) {
      if(!createTokensAutomatically.booleanValue()) {
        System.out
          .println("Tokens couldn't be found in the document - Ignoring the document "
            + gateDoc.getName());
        return null;
      }

      baseTokenAnnotationType = Constants.ANNIC_TOKEN;

      if(baseTokenAnnotationSet == null) {
        baseTokenAnnotationSet = new AnnotationSetImpl(gateDoc);
      }

      if(!createTokens(gateDoc, baseTokenAnnotationSet)) {
        System.out
          .println("Tokens couldn't be created manually - Ignoring the document "
            + gateDoc.getName());
        return null;
      }
    }
    // by now, baseTokenAnnotationSet will not be null for sure and we
    // know what's the baseTokenAnnotationType

    // lets find out the annotation set that contains
    // indexUnitAnnotationType in it
    AnnotationSet indexUnitAnnotationSet = null;

    // lets check if user has provided setName.indexUnitAnnotationType
    index = -1;
    if(indexUnitAnnotationType != null
      && indexUnitAnnotationType.trim().length() > 0)
      index = indexUnitAnnotationType.lastIndexOf('.');

    // yes he has, so lets go and fethc setName and
    // indexUnitAnnotationType
    if(index >= 0) {
      // setName
      String setName = indexUnitAnnotationType.substring(0, index);

      // indexUnitAnnotationType
      indexUnitAnnotationType =
        indexUnitAnnotationType.substring(index + 1, indexUnitAnnotationType
          .length());

      if(setName.equals(Constants.DEFAULT_ANNOTATION_SET_NAME))
        indexUnitAnnotationSet =
          gateDoc.getAnnotations().get(indexUnitAnnotationType);
      else indexUnitAnnotationSet =
        gateDoc.getAnnotations(setName).get(indexUnitAnnotationType);

      // here we check if the indexUnitAnnotationSet is null or its size
      // is 0
      // if so, we'll have to search other annotation sets
      if(indexUnitAnnotationSet == null || indexUnitAnnotationSet.size() == 0) {
        System.err.println("Index Unit " + indexUnitAnnotationType
          + " counldn't be found under the specified annotation set " + setName
          + "\n searching them in other annotation sets");
        searchIndexUnitInAllAnnotationSets = true;
      }
    }
    else {

      // either indexUnitAnnotationType is null or user hasn't provided
      // the setname
      searchIndexUnitInAllAnnotationSets = true;
    }

    // searching in all annotation set names
    if(indexUnitAnnotationType != null && indexUnitAnnotationType.length() > 0
      && searchIndexUnitInAllAnnotationSets) {
      for(String aSet : annotSetsToIndex) {
        if(aSet.equals(Constants.DEFAULT_ANNOTATION_SET_NAME)) {
          AnnotationSet tempSet =
            gateDoc.getAnnotations().get(indexUnitAnnotationType);
          if(tempSet.size() > 0) {
            indexUnitAnnotationSet = tempSet;
            break;
          }
        }
        else {
          AnnotationSet tempSet =
            gateDoc.getAnnotations(aSet).get(indexUnitAnnotationType);
          if(tempSet.size() > 0) {
            indexUnitAnnotationSet = tempSet;
            break;
          }
        }
      }
    }

    // if indexUnitAnnotationSet is null, we set indexUnitAnnotationType
    // to null as well
    if(indexUnitAnnotationSet == null) {
      indexUnitAnnotationType = null;
    }

    int j = 0;

    // we maintain an annotation set that contains all annotations from
    // all the annotation sets to be indexed
    // however it must not contain the baseTokens or
    // indexUnitAnnotationType annotations
    //AnnotationSet mergedSet = null;

    for(String annotSet : annotSetsToIndex) {

      // we need to generate the Token Stream here, and send it to the
      // GateLuceneReader
      AnnotationSet aSetToIndex =
        annotSet.equals(Constants.DEFAULT_ANNOTATION_SET_NAME) ? gateDoc
          .getAnnotations() : gateDoc.getAnnotations(annotSet);

      Set<String> indexedFeatures = new HashSet<String>();
      // tempBaseTokenAnnotationSet is not null
      List<Token>[] tokenStreams =
        getTokens(gateDoc, aSetToIndex, featuresToInclude, featuresToExclude,
          baseTokenAnnotationType, baseTokenAnnotationSet,
          indexUnitAnnotationType, indexUnitAnnotationSet, indexedFeatures);

      // if there was some problem inside obtaining tokens
      // tokenStream is set to null
      if(tokenStreams == null) return null;

      // this is enabled only if there are more than one annotation sets
      // available to search in
//      if(createMergeSet) {
//        if(mergedSet == null) mergedSet = new AnnotationSetImpl(gateDoc);
//
//        // we need to merge all annotations but the
//        // baseTokenAnnotationType
//        for(String aType : aSetToIndex.getAllTypes()) {
//
//          if(aType.equals(baseTokenAnnotationType)) {
//            continue;
//          }
//
//          if(indexUnitAnnotationType != null
//            && aType.equals(indexUnitAnnotationType)) {
//            continue;
//          }
//
//          for(Annotation a : aSetToIndex.get(aType)) {
//            try {
//              mergedSet.add(a.getStartNode().getOffset(), a.getEndNode()
//                .getOffset(), a.getType(), a.getFeatures());
//            }
//            catch(InvalidOffsetException ioe) {
//              throw new GateRuntimeException(ioe);
//            }
//          }
//
//        }
//      }

      StringBuffer indexedFeaturesString = new StringBuffer();
      for(String aFeat : indexedFeatures) {
        indexedFeaturesString.append(aFeat + ";");
      }

      Document[] toReturn = new Document[tokenStreams.length];

      for(int i = 0; i < tokenStreams.length; i++, j++) {
        // make a new, empty document
        Document doc = new Document();

        // and then create the document
        LuceneReader reader = new LuceneReader(gateDoc, tokenStreams[i]);
        doc.add(Field.Keyword(Constants.DOCUMENT_ID, documentID));
        doc.add(Field.Keyword(Constants.DOCUMENT_ID_FOR_SERIALIZED_FILE,
          documentID + "-" + j));
        doc.add(Field.Keyword(Constants.INDEXED_FEATURES, indexedFeaturesString
          .substring(0, indexedFeaturesString.length() - 1)));

        if(corpusPersistenceID != null)
          doc.add(Field.Keyword(Constants.CORPUS_ID, corpusPersistenceID));
        doc.add(Field.Keyword(Constants.ANNOTATION_SET_ID, annotSet));

        doc.add(Field.Text("contents", reader));
        // here we store token stream on the file system
        try {
          writeOnDisk(tokenStreams[i], documentID, documentID + "-" + j,
            indexLocation);
        }
        catch(Exception e) {
          Err.println("\nIgnoring the document : " + gateDoc.getName()
            + " since its token stream cannot be written on the disk");
          Err.println("Reason: " + e.getMessage());
          return null;
        }

        // return the document
        toReturn[i] = doc;
      }

      toReturnBack.addAll(Arrays.asList(toReturn));
    }

//    // once again do an index with everything merged all together
//    if(createMergeSet && mergedSet != null) {
//      Set<String> indexedFeatures = new HashSet<String>();
//      ArrayList<Token>[] tokenStreams =
//        getTokens(gateDoc, mergedSet, featuresToInclude, featuresToExclude,
//          baseTokenAnnotationType, baseTokenAnnotationSet,
//          indexUnitAnnotationType, indexUnitAnnotationSet, indexedFeatures);
//
//      if(tokenStreams == null) return null;
//
//      Document[] toReturn = new Document[tokenStreams.length];
//
//      for(int i = 0; i < tokenStreams.length; i++, j++) {
//        // make a new, empty document
//        Document doc = new Document();
//
//        // and then create the document
//        LuceneReader reader = new LuceneReader(gateDoc, tokenStreams[i]);
//        doc.add(Field.Keyword(Constants.DOCUMENT_ID, documentID));
//        doc.add(Field.Keyword(Constants.DOCUMENT_ID_FOR_SERIALIZED_FILE,
//          documentID + "-" + j));
//        StringBuffer indexedFeaturesString = new StringBuffer();
//        for(String aFeat : indexedFeatures) {
//          indexedFeaturesString.append(aFeat + ";");
//        }
//        doc.add(Field.Keyword(Constants.INDEXED_FEATURES, indexedFeaturesString
//          .substring(0, indexedFeaturesString.length() - 1)));
//
//        if(corpusPersistenceID != null)
//          doc.add(Field.Keyword(Constants.CORPUS_ID, corpusPersistenceID));
//        doc.add(Field.Keyword(Constants.ANNOTATION_SET_ID,
//          Constants.COMBINED_SET));
//
//        doc.add(Field.Text("contents", reader));
//        // here we store token stream on the file system
//        try {
//          writeOnDisk(tokenStreams[i], documentID, documentID + "-" + j,
//            indexLocation);
//        }
//        catch(Exception e) {
//          Err.println("\nIgnoring the document : " + gateDoc.getName()
//            + " since its token stream cannot be written on the disk");
//          Err.println("Reason: " + e.getMessage());
//          return null;
//        }
//
//        // return the document
//        toReturn[i] = doc;
//      }
//
//      toReturnBack.addAll(Arrays.asList(toReturn));
//    }

    return toReturnBack;
  }

  private boolean createTokens(gate.Document gateDocument, AnnotationSet set) {
    String gateContent = gateDocument.getContent().toString();
    int start = -1;
    for(int i = 0; i < gateContent.length(); i++) {
      char c = gateContent.charAt(i);
      if(Character.isWhitespace(c)) {
        if(start != -1) {
          FeatureMap features = gate.Factory.newFeatureMap();
          String string = gateContent.substring(start, i);
          if(string.trim().length() > 0) {
            features.put("string", string);
            try {
              set.add(Long.valueOf(start), Long.valueOf(i), Constants.ANNIC_TOKEN,
                features);
            }
            catch(InvalidOffsetException ioe) {
              ioe.printStackTrace();
              return false;
            }
          }
          start = i + 1;
        }
      }
      else {
        if(start == -1) start = i;
      }
    }
    if(start == -1) return false;
    if(start < gateContent.length()) {
      FeatureMap features = gate.Factory.newFeatureMap();
      String string = gateContent.substring(start, gateContent.length());
      if(string.trim().length() > 0) {
        features.put("string", string);
        try {
          set.add(Long.valueOf(start), Long.valueOf(gateContent.length()),
            Constants.ANNIC_TOKEN, features);
        }
        catch(InvalidOffsetException ioe) {
          ioe.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Some file names are not compatible to the underlying file system. This
   * method replaces all those incompatible characters with '_'.
   */
  private String getCompatibleName(String name) {
    return name.replaceAll("[\\/:\\*\\?\"<>|]", "_");
  }

  /**
   * This method, given a tokenstream and file name, writes the tokenstream on
   * the provided location.
   */
  private void writeOnDisk(List<Token> tokenStream, String folderName,
    String fileName, String location) throws Exception {

    // before we write it on a disk, we need to change its name to
    // underlying file system name
    fileName = getCompatibleName(fileName);
    folderName = getCompatibleName(folderName);

    if(location.startsWith("file:/"))
      location = location.substring(6, location.length());

    if(location.charAt(1) != ':') location = "/" + location;

    File locationFile = new File(location);
    File folder = new File(locationFile, Constants.SERIALIZED_FOLDER_NAME);
    if(!folder.exists()) {
      if (!folder.mkdirs()) {
        throw new IOException(
            "Directory could not be created :" + folder.getAbsolutePath()); 
      }
    }

    folder = new File(folder, folderName);
    if(!folder.exists()) {
      if (!folder.mkdirs()){
        throw new IOException(
            "Directory could not be created :" + folder.getAbsolutePath());
      }
    }

    File outputFile = new File(folder, fileName + ".annic");
    try (OutputStream file = new FileOutputStream(outputFile);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);) {

      output.writeObject(tokenStream);
      output.flush();
    }
  }

  /**
   * Internal class used for storing the offsets of annotations.
   * 
   * @author niraj
   * 
   */
  private static class OffsetGroup {
    Long startOffset;

    Long endOffset;
  }

  /**
   * This method given a GATE document and other required parameters, for each
   * annotation of type indexUnitAnnotationType creates a separate list of
   * baseTokens underlying in it.
   */
  private List<Token>[] getTokens(gate.Document document,
    AnnotationSet inputAs, List<String> featuresToInclude,
    List<String> featuresToExclude, String baseTokenAnnotationType,
    AnnotationSet baseTokenSet, String indexUnitAnnotationType,
    AnnotationSet indexUnitSet, Set<String> indexedFeatures) {

    boolean excludeFeatures = false;
    boolean includeFeatures = false;

    // if include features are provided, we donot look at the exclude
    // features
    if(!featuresToInclude.isEmpty()) {
      includeFeatures = true;
    }
    else if(!featuresToExclude.isEmpty()) {
      excludeFeatures = true;
    }

    HashSet<OffsetGroup> unitOffsetsSet = new HashSet<OffsetGroup>();
    if(indexUnitAnnotationType == null
      || indexUnitAnnotationType.trim().length() == 0 || indexUnitSet == null
      || indexUnitSet.size() == 0) {
      // the index Unit Annotation Type is not specified
      // therefore we consider the entire document as a single unit
      OffsetGroup group = new OffsetGroup();
      group.startOffset = 0L;
      group.endOffset = document.getContent().size();
      unitOffsetsSet.add(group);
    }
    else {
      Iterator<Annotation> iter = indexUnitSet.iterator();
      while(iter.hasNext()) {
        Annotation annotation = iter.next();
        OffsetGroup group = new OffsetGroup();
        group.startOffset = annotation.getStartNode().getOffset();
        group.endOffset = annotation.getEndNode().getOffset();
        unitOffsetsSet.add(group);
      }
    }

    Set<String> allTypes = new HashSet<String>();

    for(String aType : inputAs.getAllTypes()) {
      if(aType.indexOf(".") > -1 || aType.indexOf("=") > -1
        || aType.indexOf(";") > -1 || aType.indexOf(",") > -1) {
        System.err
          .println("Annotations of type "
            + aType
            + " cannot be indexed as the type name contains one of the ., =, or ; character");
        continue;
      }
      allTypes.add(aType);
    }

    if(baseTokenSet != null && baseTokenSet.size() > 0) {
      allTypes.remove(baseTokenAnnotationType);
    }

    if(indexUnitSet != null && indexUnitSet.size() > 0)
      allTypes.remove(indexUnitAnnotationType);

    AnnotationSet toUseSet = new AnnotationSetImpl(document);
    for(String type : allTypes) {
      for(Annotation a : inputAs.get(type)) {
        try {
          toUseSet.add(a.getStartNode().getOffset(),
            a.getEndNode().getOffset(), a.getType(), a.getFeatures());
        }
        catch(InvalidOffsetException ioe) {
          throw new GateRuntimeException(ioe);
        }
      }
    }

    
    @SuppressWarnings({"cast","unchecked","rawtypes"})
    List<Token> toReturn[] = (List<Token>[])new List[unitOffsetsSet.size()];
    Iterator<OffsetGroup> iter = unitOffsetsSet.iterator();
    int counter = 0;
    while(iter.hasNext()) {
      OffsetGroup group = iter.next();
      List<Token> newTokens = new ArrayList<Token>();
      List<Annotation> tokens =
        new ArrayList<Annotation>(toUseSet.getContained(group.startOffset,
          group.endOffset));

      // add tokens from the baseTokenSet
      if(baseTokenSet != null && baseTokenSet.size() != 0) {
        tokens.addAll(baseTokenSet.getContained(group.startOffset,
          group.endOffset));
      }

      if(tokens.isEmpty()) return null;

      Collections.sort(tokens, new OffsetComparator());

      int position = -1;
      for(int i = 0; i < tokens.size(); i++) {
        byte inc = 1;
        Annotation annot = tokens.get(i);
        String type = annot.getType();
        // if the feature is specified in featuresToExclude -exclude it
        if(excludeFeatures && featuresToExclude.contains(type)) continue;

        // if the feature is not sepcified in the include features -
        // exclude it
        if(includeFeatures && !featuresToInclude.contains(type)) continue;

        int startOffset = annot.getStartNode().getOffset().intValue();
        int endOffset = annot.getEndNode().getOffset().intValue();
        String text =
          document.getContent().toString().substring(startOffset, endOffset);
       
        Token token1 = new Token(type, startOffset, endOffset, "*");

        // each token has four values
        // String, int, int, String
        // we add extra info of position
        if(i > 0) {
          if(annot.getStartNode().getOffset().longValue() == tokens.get(i - 1)
            .getStartNode().getOffset().longValue()) {
            token1.setPositionIncrement(0);
            inc = 0;
          }
        }

        position += inc;
        token1.setPosition(position);
        newTokens.add(token1);

        if(!type.equals(baseTokenAnnotationType)
          || (annot.getFeatures().get("string") == null)) {
          // we need to create one string feature for this
          Token tk1 = new Token(text, startOffset, endOffset, type + ".string");
          indexedFeatures.add(type + ".string");
          tk1.setPositionIncrement(0);
          tk1.setPosition(position);
          newTokens.add(tk1);
        }

        // now find out the features and add them
        FeatureMap features = annot.getFeatures();
        Iterator<Object> fIter = features.keySet().iterator();
        while(fIter.hasNext()) {
          String type1 = fIter.next().toString();
          // if the feature is specified in featuresToExclude -exclude
          // it
          if(excludeFeatures && featuresToExclude.contains(type + "." + type1)) {
            continue;
          }

          // if the feature is not sepcified in the include features -
          // exclude it
          if(includeFeatures && !featuresToInclude.contains(type + "." + type1))
            continue;

          Object tempText = features.get(type1);
          if(tempText == null) continue;

          String text1 = tempText.toString();
          // we need to qualify the type names
          // for each annotation type feature we add AT.Feature=="**" to be able
          // to search for it
          // to calculate stats

          Token tempToken =
            new Token(text1, startOffset, endOffset, type + "." + type1);
          indexedFeatures.add(type + "." + type1);
          tempToken.setPositionIncrement(0);
          tempToken.setPosition(position);
          newTokens.add(tempToken);

          Token onlyATFeature =
            new Token(type + "." + type1, startOffset, endOffset, "**");
          onlyATFeature.setPosition(position);
          onlyATFeature.setPositionIncrement(0);
          newTokens.add(onlyATFeature);

        }
      }
      toReturn[counter] = newTokens;
      counter++;
    }
    return toReturn;
  }
}