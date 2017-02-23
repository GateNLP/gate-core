/*
 *  SearchException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Rosen Marinov, 19/Apr/2002
 *
 */

package gate.creole.ir;

public class SearchException extends Exception{

  private static final long serialVersionUID = 238378794456612672L;

  public SearchException(String msg){
    super(msg);
  }

  public SearchException(String errorType, String error,
          String erroneousLine) {

    super("\nError: "+errorType+" - "+error+".\n"
          +erroneousLine);
  }

  public SearchException(String errorType, String error,
          String erroneousLine, int errorPosition) {

    super("\nError: "+errorType+" - "+error+".\n"
            +erroneousLine+"\n"
            +caretLine(errorPosition));
  }

  final static private String caretLine(int errorPosition) {
    StringBuffer s = new StringBuffer();
    for (int c = 0; c < errorPosition; c++) {
      s.append(" ");
    }
    s.append("^");
    return s.toString();
  }

}