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
import java.util.*;

import gate.creole.annic.apache.lucene.store.Directory;
import gate.creole.annic.apache.lucene.document.Document;
import gate.creole.annic.apache.lucene.index.IndexReader;
import gate.creole.annic.apache.lucene.index.Term;

/**
 * Implements search over a single IndexReader.
 * 
 * <p>
 * Applications usually need only call the inherited {@link #search(Query)} or
 * {@link #search(Query,Filter)} methods.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class IndexSearcher extends Searcher {
	IndexReader reader;

	private boolean closeReader;
	
	/** Creates a searcher searching the index in the named directory. */
	public IndexSearcher(String path) throws IOException {
		this(IndexReader.open(path), true);
	}

	/** Creates a searcher searching the index in the provided directory. */
	public IndexSearcher(Directory directory) throws IOException {
		this(IndexReader.open(directory), true);
	}

	/** Creates a searcher searching the provided index. */
	public IndexSearcher(IndexReader r) {
		this(r, false);
	}

	private IndexSearcher(IndexReader r, boolean closeReader) {
		reader = r;
		this.closeReader = closeReader;
	}

	/**
	 * Note that the underlying IndexReader is not closed, if IndexSearcher was
	 * constructed with IndexSearcher(IndexReader r). If the IndexReader was
	 * supplied implicitly by specifying a directory, then the IndexReader gets
	 * closed.
	 */
	@Override
  public void close() throws IOException {
		if (closeReader)
			reader.close();
	}

	// inherit javadoc
	@Override
  public int docFreq(Term term) throws IOException {
		return reader.docFreq(term);
	}

	// inherit javadoc
	@Override
  public Document doc(int i) throws IOException {
		return reader.document(i);
	}

	// inherit javadoc
	@Override
  public int maxDoc() throws IOException {
		return reader.maxDoc();
	}

	// inherit javadoc
	@Override
  public TopDocs search(Query query, Filter filter, final int nDocs)
			throws IOException {
	  initializeTermPositions();
		Scorer scorer = query.weight(this)
				.scorer(reader,/* Niraj */this /* End */);
		if (scorer == null)
			return new TopDocs(0, new ScoreDoc[0]);
		final BitSet bits = filter != null ? filter.bits(reader) : null;
		final HitQueue hq = new HitQueue(nDocs);
		final int[] totalHits = new int[1];
		scorer.score(new HitCollector() {
			@Override
      public final void collect(int doc, float score) {
				if (score > 0.0f && // ignore zeroed buckets
						(bits == null || bits.get(doc))) { // skip docs not in
															// bits
					totalHits[0]++;
					hq.insert(new ScoreDoc(doc, score));
				}
			}
		},/* Niraj */this /* End */);

		ScoreDoc[] scoreDocs = new ScoreDoc[hq.size()];
		for (int i = hq.size() - 1; i >= 0; i--)
			// put docs in array
			scoreDocs[i] = (ScoreDoc) hq.pop();

		return new TopDocs(totalHits[0], scoreDocs);
	}

	// inherit javadoc
	@Override
  public TopFieldDocs search(Query query, Filter filter, final int nDocs,
			Sort sort) throws IOException {
	  initializeTermPositions();
		Scorer scorer = query.weight(this).scorer(reader, this);
		if (scorer == null)
			return new TopFieldDocs(0, new ScoreDoc[0], sort.fields);

		final BitSet bits = filter != null ? filter.bits(reader) : null;
		final FieldSortedHitQueue hq = new FieldSortedHitQueue(reader,
				sort.fields, nDocs);
		final int[] totalHits = new int[1];
		scorer.score(new HitCollector() {
			@Override
      public final void collect(int doc, float score) {
				if (score > 0.0f && // ignore zeroed buckets
						(bits == null || bits.get(doc))) { // skip docs not in
															// bits
					totalHits[0]++;
					hq.insert(new FieldDoc(doc, score));
				}
			}
		}, this);

		ScoreDoc[] scoreDocs = new ScoreDoc[hq.size()];
		for (int i = hq.size() - 1; i >= 0; i--)
			// put docs in array
			scoreDocs[i] = hq.fillFields((FieldDoc) hq.pop());

		return new TopFieldDocs(totalHits[0], scoreDocs, hq.getFields());
	}

	// inherit javadoc
	@Override
  public void search(Query query, Filter filter, final HitCollector results)
			throws IOException {
	  initializeTermPositions();
		HitCollector collector = results;
		if (filter != null) {
			final BitSet bits = filter.bits(reader);
			collector = new HitCollector() {
				@Override
        public final void collect(int doc, float score) {
					if (bits.get(doc)) { // skip docs not in bits
						results.collect(doc, score);
					}
				}
			};
		}

		Scorer scorer = query.weight(this).scorer(reader, this);
		if (scorer == null)
			return;
		scorer.score(collector, this);
	}

	@Override
  public Query rewrite(Query original) throws IOException {
		Query query = original;
		for (Query rewrittenQuery = query.rewrite(reader); rewrittenQuery != query; rewrittenQuery = query
				.rewrite(reader)) {
			query = rewrittenQuery;
		}
		return query;
	}

	@Override
  public Explanation explain(Query query, int doc) throws IOException {
		return query.weight(this).explain(reader, doc);
	}
	
  /**
   * Each pattern is a result of either simple or a boolean query. The
   * type number indicates if the query used to retrieve that pattern
   * was simple or boolean.
   */
  private ArrayList<Integer> queryType = new ArrayList<Integer>();

  /**
   * Each terms has a frequency.
   */
  private ArrayList<Integer> frequencies = new ArrayList<Integer>();
  
  /**
   * Each Integer value in this list is an index of first annotation of
   * the pattern that matches with the user query.
   */
  private ArrayList firstTermPositions = new ArrayList();

  /**
   * document numbers
   */
  private ArrayList<Integer> documentNumbers = new ArrayList<Integer>();

  /**
   * Stores how long each pattern is (in terms of number of
   * annotations).
   */
  private ArrayList<Integer> patternLengths = new ArrayList<Integer>();

  /**
   * Sets the firstTermPositions.
   * 
   * @param qType
   * @param doc
   * @param positions
   * @param patternLength
   */
  public void setFirstTermPositions(int qType, int doc, ArrayList positions,
          int patternLength, int frequency) {
    queryType.add(new Integer(qType));
    firstTermPositions.add(positions);
    documentNumbers.add(new Integer(doc));
    patternLengths.add(new Integer(patternLength));
    frequencies.add(new Integer(frequency));
  }

  /**
   * Initializes all local variables
   * 
   */
  public void initializeTermPositions() {
    queryType = new ArrayList<Integer>();
    firstTermPositions = new ArrayList();
    documentNumbers = new ArrayList<Integer>();
    patternLengths = new ArrayList<Integer>();
    frequencies = new ArrayList<Integer>();
  }

  /**
   * Returns an array of arrayLists where the first list contains
   * document numbers, second list contains first term positions, third
   * list contains the pattern lengths and the fourth one contains the
   * query type for each pattern.
   */
  public ArrayList[] getFirstTermPositions() {
    return new ArrayList[] {documentNumbers, firstTermPositions,
        patternLengths, queryType, frequencies};
  }
}
