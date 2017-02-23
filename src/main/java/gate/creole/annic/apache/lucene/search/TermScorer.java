package gate.creole.annic.apache.lucene.search;

/**
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import gate.creole.annic.apache.lucene.index.TermDocs;
import gate.creole.annic.apache.lucene.index.Term;

@SuppressWarnings({"rawtypes","unchecked"})
final class TermScorer extends Scorer {
  private Weight weight;

  private TermDocs termDocs;

  private byte[] norms;

  private float weightValue;

  private int doc;

  private Term term;

  private final int[] docs = new int[32]; // buffered doc numbers

  private final int[] freqs = new int[32]; // buffered term freqs

  private int pointer;

  private int pointerMax;

  private static final int SCORE_CACHE_SIZE = 32;

  private float[] scoreCache = new float[SCORE_CACHE_SIZE];

  TermScorer(Weight weight, TermDocs td, Similarity similarity, byte[] norms,
          Term term) throws IOException {
    super(similarity);
    this.weight = weight;
    this.termDocs = td;
    this.norms = norms;
    this.weightValue = weight.getValue();
    this.term = term;

    for(int i = 0; i < SCORE_CACHE_SIZE; i++)
      scoreCache[i] = getSimilarity().tf(i) * weightValue;
  }

  @Override
  public int doc() {
    return doc;
  }

  @Override
  public boolean next(Searcher searcher) throws IOException {
    this.searcher = searcher;
    pointer++;
    if(pointer >= pointerMax) {
      pointerMax = termDocs.read(docs, freqs); // refill buffer
      if(pointerMax != 0) {
        pointer = 0;
      }
      else {
        termDocs.close(); // close stream
        doc = Integer.MAX_VALUE; // set to sentinel value
        return false;
      }
    }
    doc = docs[pointer];
    return true;
  }

  @Override
  public float score(Searcher searcher) throws IOException {
    this.searcher = searcher;
    int f = freqs[pointer];
    float raw = // compute tf(f)*weight
    f < SCORE_CACHE_SIZE // check cache
            ? scoreCache[f] // cache hit
            : getSimilarity().tf(f) * weightValue; // cache miss

    /* Niraj */
    // here before returning this score we will check if it is
    // greater than 0.0f
    float score = raw * Similarity.decodeNorm(norms[doc]); // normalize
                                                            // for field
    if(score > 0.0f) {
      // here 0 means TermQuery
      // annic query parser in no case is going to generate any
      // null value for term type and term text (it is not possible)
      // if it generates it, it is result of some other type of query
      // (e.g. specifying
      // a corpus to search in and therefore should not be set as first
      // term position
      if(term.type() != null && this.searcher instanceof IndexSearcher) {
        // we need to add this into the IndexSercher
        java.util.ArrayList termInfo = new java.util.ArrayList();
        termInfo.add(term.text());
        termInfo.add(term.type());
        ((IndexSearcher)this.searcher).setFirstTermPositions(0, doc(), termInfo, 1, f);
      }
    }
    return score;
  }

  @Override
  public boolean skipTo(int target) throws IOException {
    // first scan in cache
    for(pointer++; pointer < pointerMax; pointer++) {
      if(!(target > docs[pointer])) {
        doc = docs[pointer];
        return true;
      }
    }

    // not found in cache, seek underlying stream
    boolean result = termDocs.skipTo(target);
    if(result) {
      pointerMax = 1;
      pointer = 0;
      docs[pointer] = doc = termDocs.doc();
      freqs[pointer] = termDocs.freq();
    }
    else {
      doc = Integer.MAX_VALUE;
    }
    return result;
  }

  @Override
  public Explanation explain(int doc) throws IOException {
    TermQuery query = (TermQuery)weight.getQuery();
    Explanation tfExplanation = new Explanation();
    int tf = 0;
    while(pointer < pointerMax) {
      if(docs[pointer] == doc) tf = freqs[pointer];
      pointer++;
    }
    if(tf == 0) {
      while(termDocs.next()) {
        if(termDocs.doc() == doc) {
          tf = termDocs.freq();
        }
      }
    }
    termDocs.close();
    tfExplanation.setValue(getSimilarity().tf(tf));
    tfExplanation.setDescription("tf(termFreq(" + query.getTerm() + ")=" + tf
            + ")");

    return tfExplanation;
  }

  @Override
  public String toString() {
    return "scorer(" + weight + ")";
  }

}
