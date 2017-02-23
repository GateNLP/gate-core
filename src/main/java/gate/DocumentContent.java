/*
 *  DocumentContent.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 15/Feb/2000
 *
 *  $Id: DocumentContent.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate;

import java.io.Serializable;

import gate.util.InvalidOffsetException;

/** The content of Documents.
  */
public interface DocumentContent extends Serializable {

  /**
   * Return the contents under a particular span.
   * <p>
   * Conceptually the annotation offsets are defined as falling in between
   * characters, with "0" pointing before the fist character.
   * Because of that, the offsets where an annotation ends and the space after
   * it starts are the same.
   * <p>
   * So this is what the "abcde" string looks like with the offsets explicitly
   * included: 0a1b2c3d4e5
   * <p>
   * "ab cd" would then look like this: 0a1b2 3c4d5
   * <p>
   * with the following annotations:<br>
   * Token "ab" [0,2]<br>
   * SpaceToken " " [2,3]<br>
   * Token "cd" [3,5]
   * <p>
   * @param      start   the beginning index, inclusive.
   * @param      end     the ending index, exclusive.
   * @return     the specified substring for the document.
   * @throws gate.util.InvalidOffsetException if the
   *             <code>start</code> is negative, or
   *             <code>end</code> is larger than the length of
   *             this <code>DocumentContent</code> object, or
   *             <code>start</code> is larger than
   *             <code>end</code>.
   */
  public DocumentContent getContent(Long start, Long end)
    throws InvalidOffsetException;

  /** The size of this content (e.g. character length for textual
    * content).
    */
  public Long size();

} // interface DocumentContent
