/*
 *  LuceneAnalyzer.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: LuceneAnalyzer.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic.lucene;

import gate.creole.annic.apache.lucene.analysis.*;
import gate.util.GateRuntimeException;

import java.io.*;

/**
 * This class provides an implementation for Analyzer which is used
 * internally by ANNIC resources.
 * 
 * @author niraj
 * 
 */
public class LuceneAnalyzer extends Analyzer {

  /**
   * Each analyzer is required to provide implementation of this method.
   * Using the provided reader, which in this case must be an instance
   * of LuceneReader, it obtains the tokenStream and wrap it in the
   * instanceof LuceneTokenizer. It is this instance that is returned to
   * the user.
   */
  @Override
  public TokenStream tokenStream(String fieldName, Reader reader) {
    try {
      if(reader instanceof LuceneReader) {
        if(fieldName.equals("contents")) {
          LuceneReader gr = (LuceneReader)reader;
          try {
            TokenStream result = new LuceneTokenizer(gr.getTokenStream());
            return result;
          }
          catch(Exception e) {
            e.printStackTrace();
          }

        }
        else {
          new gate.creole.annic.apache.lucene.analysis.standard.StandardTokenizer(
                  reader);
        }
      }
    }
    catch(Exception e) {
      throw new GateRuntimeException(e);
    }
    return null;
  }
}
