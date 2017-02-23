/*
 *  LuceneReader.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneReader.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.creole.annic.apache.lucene.analysis.Token;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

/**
 * A Reader that stores the document to read and the token stream
 * associated with it.
 * 
 * @author niraj
 * 
 */
public class LuceneReader extends BufferedReader {

  /**
   * Gate document
   */
  gate.Document gateDoc;

  /**
   * Token Stream.
   */
  List<Token> tokenStream;

  /**
   * Constructor
   * 
   * @param gateDoc
   * @param tokenStream
   */
  public LuceneReader(gate.Document gateDoc, List<Token> tokenStream) {
    super(new StringReader(""));
    this.gateDoc = gateDoc;
    this.tokenStream = tokenStream;
  }

  /**
   * Gets the document object
   */
  public gate.Document getDocument() {
    return this.gateDoc;
  }

  /**
   * Gets the token stream associated with this reader
   */
  public List<Token> getTokenStream() {
    return this.tokenStream;
  }
}
