/*
 *  LuceneTokeniser.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneTokeniser.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.creole.annic.apache.lucene.analysis.*;
import java.io.*;
import gate.*;
import java.util.*;

/**
 * Implementation of token stream.
 * @author niraj
 *
 */
public class LuceneTokenizer extends TokenStream {
	Document document;
	List<Token> tokens;
	//List featuresToExclude;
	int pointer = 0;

  /**
   * Constructor
   * @param tokenStream
   */
	public LuceneTokenizer(List<Token> tokenStream) {
		this.tokens = tokenStream;
		pointer = 0;
	}

  /**
   * Returns the next token in the token stream.
   */
	@Override
  public Token next() throws IOException {
		while (pointer < tokens.size()) {
			Token token = tokens.get(pointer);
			pointer++;
			if (token == null)
				continue;
			return token;
		}
		return null;
	}
}