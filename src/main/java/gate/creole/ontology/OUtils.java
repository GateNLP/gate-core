/*
 * OUtils.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts 05/05/2010
 *
 * $Id: OUtils.java 17530 2014-03-04 15:57:43Z markagreenwood $
 *
 * This class includes code from the com.hp.hpl.jena.util.URIref class of jena
 * (http://jena.sourceforge.net) which is subject to the following licence:
 *
 *  (c) Copyright Hewlett-Packard Company 2001 
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gate.creole.ontology;

import java.util.regex.Pattern;

public class OUtils {

  /**
   * Private constructor - this class should not be instantiated.
   */
  private OUtils() {
  }

  /**
   * Pattern for symbol and punctuation characters that are not recommended in
   * URIs.
   */
  private static Pattern badPunctPattern =
    Pattern.compile("[\\p{P}\\p{S}&&[^;\\?@&=\\+\\$,_\\.!~\\*\\(\\)\\-]]");

  /**
   * Pattern matching runs of whitespace.
   */
  private static Pattern spacesPattern =
    Pattern.compile("\\s+");

  /**
   * Converts a string to a form suitable for use as a resource name by the
   * {@link Ontology#createOURIForName} method.  This is not a reversible
   * encoding, but is intended to produce "readable" resource URIs in an
   * ontology from English source strings.  The process is:
   * <ol>
   * <li>Replace any slashes, colons, apostrophes and other non-URI-legal
   * punctuation characters and unicode symbol characters with a space, i.e.
   * any punctuation except ; ? @ &amp; = + $ , - _ . ! ~ * ( )</li>
   * <li>Convert any runs of whitespace characters to a single underscore</li>
   * <li>{@link #uriEncode} the result.</li>
   * </ol>
   * For example, this would convert "John Smith" to "John_Smith", "Allen &amp;
   * Heath" to "Allen_&amp;_Heath", "N/A" to "N_A", "32 &#xb0;F" to "32_F", etc.
   */
  public static String toResourceName(String text) {
    return uriEncode(spacesPattern.matcher(
              badPunctPattern.matcher(text).replaceAll(" ")
          ).replaceAll("_"));
  }

  /**
   * Convert a Unicode string (which is assumed to represent a URI or URI
   * fragment) to an RFC 2396-compliant URI reference by first converting it to
   * bytes in UTF-8 and then encoding the resulting bytes as specified by the
   * RFC.  ASCII letters, numbers and the other characters that are permitted
   * in URI references are left unchanged, existing %NN escape sequences are
   * left unchanged, and any other characters are %-escaped as appropriate.  In
   * particular any % characters in the original string that are not part of a
   * %NN escape sequence will themselves be encoded as %25.
   *
   * @param uriRef The uri, in characters specified by RFC 2396 + '#'
   * @return The corresponding Unicode String
   */ 
  @SuppressWarnings("fallthrough")
  public static String uriEncode(String uriRef) {
    try {
      byte utf8[] = uriRef.getBytes("UTF-8");
      byte rsltAscii[] = new byte[utf8.length*6];
      int in = 0;
      int out = 0;
      while ( in < utf8.length ) {  
        switch ( utf8[in] ) {
              case (byte)'a': case (byte)'b': case (byte)'c': case (byte)'d': case (byte)'e': case (byte)'f': case (byte)'g': case (byte)'h': case (byte)'i': case (byte)'j': case (byte)'k': case (byte)'l': case (byte)'m': case (byte)'n': case (byte)'o': case (byte)'p': case (byte)'q': case (byte)'r': case (byte)'s': case (byte)'t': case (byte)'u': case (byte)'v': case (byte)'w': case (byte)'x': case (byte)'y': case (byte)'z':
              case (byte)'A': case (byte)'B': case (byte)'C': case (byte)'D': case (byte)'E': case (byte)'F': case (byte)'G': case (byte)'H': case (byte)'I': case (byte)'J': case (byte)'K': case (byte)'L': case (byte)'M': case (byte)'N': case (byte)'O': case (byte)'P': case (byte)'Q': case (byte)'R': case (byte)'S': case (byte)'T': case (byte)'U': case (byte)'V': case (byte)'W': case (byte)'X': case (byte)'Y': case (byte)'Z':
              case (byte)'0': case (byte)'1': case (byte)'2': case (byte)'3': case (byte)'4': case (byte)'5': case (byte)'6': case (byte)'7': case (byte)'8': case (byte)'9':
              case (byte)';': case (byte)'/': case (byte)'?': case (byte)':': case (byte)'@': case (byte)'&': case (byte)'=': case (byte)'+': case (byte)'$': case (byte)',':
              case (byte)'-': case (byte)'_': case (byte)'.': case (byte)'!': case (byte)'~': case (byte)'*': case (byte)'\'': case (byte)'(': case (byte)')':
              case (byte)'#': 
              case (byte)'[': case (byte)']':
                  rsltAscii[out] = utf8[in];
                  out++;
                  in++;
                  break;
              case (byte) '%':
                  try {
                      if ( in+2 < utf8.length ) {
                          byte first = hexEncode(hexDecode(utf8[in+1]));
                          byte second = hexEncode(hexDecode(utf8[in+2]));
                          rsltAscii[out++] = (byte)'%';
                          rsltAscii[out++] = first;
                          rsltAscii[out++] = second;
                          in += 3;
                          break;
                      }
                  }
                  catch (IllegalArgumentException e) {
                      // Illformed - should issue message ....
                      //Original JENA class prints a warning here, we want to
                      //ignore the error and simply encode bare % signs as %25
                      //
                      // Fall through.
                  }
              default:
                      rsltAscii[out++] = (byte)'%';
                      // Get rid of sign ...
                      int c = utf8[in]&255;
                      rsltAscii[out++] = hexEncode( c/16 );
                      rsltAscii[out++] = hexEncode( c%16 );
                      in++;
                      break;
          }
      }
      return new String(rsltAscii,0,out,"US-ASCII");
    }
    catch ( java.io.UnsupportedEncodingException e ) {
        throw new Error( "The JVM is required to support UTF-8 and US-ASCII encodings.");
    }
  }
  
  /**
   * Convert a URI reference (URI or URI fragment), in US-ASCII, with escaped
   * characters taken from UTF-8, to the corresponding Unicode string.
   * On ill-formed input the results are undefined, specifically if
   * the unescaped version is not a UTF-8 String, some String will be
   * returned.
   * @param uri The uri, in characters specified by RFC 2396 + '#'.
   * @return The corresponding Unicode String.
   * @exception IllegalArgumentException If a % hex sequence is ill-formed.
   */
  public static String uriDecode(String uri) {
      try {
          byte ascii[] = uri.getBytes("US-ASCII");
          byte utf8[] = new byte[ascii.length];
          int in = 0;
          int out = 0;
          while ( in < ascii.length ) {
              // Original JENA class left escaped percent signs (%25)
              // untouched, we convert them back to plain %
              if ( ascii[in] == (byte)'%' ) {
                  in++;
                  utf8[out++] = (byte)(hexDecode(ascii[in])*16 | hexDecode(ascii[in+1]));
                  in += 2;
              } else {
                  utf8[out++] = ascii[in++];
              }
          }
          return new String(utf8,0,out,"UTF-8");
      }
      catch ( java.io.UnsupportedEncodingException e ) {
          throw new Error( "The JVM is required to support UTF-8 and US-ASCII encodings.");
      }
      catch ( ArrayIndexOutOfBoundsException ee ) {
          throw new IllegalArgumentException("Incomplete Hex escape sequence in " + uri );
      }
  }
  
  private static final byte hexEncode(int i ) {
      if (i<10)
          return (byte) ('0' + i);
      else
          return (byte)('A' + i - 10);
  }
  
  private static final int hexDecode(byte b ) {
      switch (b) { 
          case (byte)'a': case (byte)'b': case (byte)'c': case (byte)'d': case (byte)'e': case (byte)'f':
           return (b&255)-'a'+10;
          case (byte)'A': case (byte)'B': case (byte)'C': case (byte)'D': case (byte)'E': case (byte)'F': 
          return b - (byte)'A' + 10;
          case (byte)'0': case (byte)'1': case (byte)'2': case (byte)'3': case (byte)'4': case (byte)'5': case (byte)'6': case (byte)'7': case (byte)'8': case (byte)'9':
              return b - (byte)'0';
              default:
                  throw new IllegalArgumentException("Bad Hex escape character: " + (b&255) );
      }
  }
}
