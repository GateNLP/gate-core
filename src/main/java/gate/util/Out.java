/*
 *  Out.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Cristian URSU, 29 September 2000
 *
 *  $Id: Out.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

import java.io.PrintWriter;

/** Shorthand for the <CODE> System.out.print and println</CODE>
  * methods.
  */
public class Out {

  /** A printwriter to delegate to */
  private static PrintWriter out = new PrintWriter(System.out,true);

  /** Don't construct any of these */
  private Out() {}

  /** Flush the output stream. */
  public static void flush() { out.flush(); }

  /** This sets a new printWriter*/
  public static void setPrintWriter(PrintWriter aPrintWriter){
    out = aPrintWriter;
  }//setPrintWriter

  /** This sets a new printWriter*/
  public static PrintWriter getPrintWriter(){
    return out;
  }

  // print() and println() methods definition
  ////////////////////////////////////////////////

  // print

  /** @see java.io.PrintWriter#print(boolean) */
  public static void print(boolean b) {
    out.print(b);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(char) */
  public static void print(char c) {
    out.print(c);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(int) */
  public static void print(int i) {
    out.print(i);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(long) */
  public static void print(long l) {
    out.print(l);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(float) */
  public static void print(float f) {
    out.print(f);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(double) */
  public static void print(double d) {
    out.print(d);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(char[]) */
  public static void print(char s[]) {
    out.print(s);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(java.lang.String) */
  public static void print(String s) {
    out.print(s);
    out.flush();
  }

  /** @see java.io.PrintWriter#print(java.lang.Object) */
  public static void print(Object obj) {
    out.print(obj);
    out.flush();
  }

  // println

  /** @see java.io.PrintWriter#println() */
  public static void println() {
    out.println();
  }

  /** @see java.io.PrintWriter#println(boolean) */
  public static void println(boolean x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(char) */
  public static void println(char x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(int) */
  public static void println(int x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(long) */
  public static void println(long x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(float) */
  public static void println(float x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(double) */
  public static void println(double x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(char[]) */
  public static void println(char x[]) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(java.lang.String) */
  public static void println(String x) {
    out.println(x);
  }

  /** @see java.io.PrintWriter#println(java.lang.Object) */
  public static void println(Object x) {
    out.println(x);
  }

  // pr and prln uses print and println so further modifications
  // must be done to print and println only
  ////////////////////////////////////////////////////////////////

  /** @see java.io.PrintWriter#print(boolean) */
  public static void pr(boolean b) {
    print(b);
  }

  /** @see java.io.PrintWriter#print(char) */
  public static void pr(char c) {
    print(c);
  }

  /** @see java.io.PrintWriter#print(int) */
  public static void pr(int i) {
    print(i);
  }

  /** @see java.io.PrintWriter#print(long) */
  public static void pr(long l) {
    print(l);
  }

  /** @see java.io.PrintWriter#print(float) */
  public static void pr(float f) {
    print(f);
  }

  /** @see java.io.PrintWriter#print(double) */
  public static void pr(double d) {
    print(d);
  }

  /** @see java.io.PrintWriter#print(char[]) */
  public static void pr(char s[]) {
    print(s);
  }

  /** @see java.io.PrintWriter#print(java.lang.String) */
  public static void pr(String s) {
    print(s);
  }

  /** @see java.io.PrintWriter#print(java.lang.Object) */
  public static void pr(Object obj) {
    print(obj);
  }

  /** @see java.io.PrintWriter#println() */
  public static void prln() {
    println();
  }

  /** @see java.io.PrintWriter#println(boolean) */
  public static void prln(boolean x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(char) */
  public static void prln(char x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(int) */
  public static void prln(int x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(long) */
  public static void prln(long x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(float) */
  public static void prln(float x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(double) */
  public static void prln(double x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(char[]) */
  public static void prln(char x[]) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(java.lang.String) */
  public static void prln(String x) {
    println(x);
  }

  /** @see java.io.PrintWriter#println(java.lang.Object) */
  public static void prln(Object x) {
    println(x);
  }

  /** Char to pad with. */
  private static char padChar = ' ';

  /** A mutator method for padChar*/
  public static void setPadChar(char aChar){
    padChar = aChar;
  }//setPadChar

  /** An accesor method for padChar*/
  public static char getPadChar(){
    return padChar;
  }//getPadChar

  /** Print padding followed by String s. */
  public static void padPr(String s, int padding) {
      for(int i=0; i<padding; i++)
                       out.print(padChar);
      out.print(s);
      out.flush();
  } // padPr(String,int)

} // Out

