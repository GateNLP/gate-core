/*
 *  RepositioningInfo.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Angel Kirilov, 04/January/2002
 *
 *  $Id: RepositioningInfo.java 18950 2015-10-15 12:37:29Z ian_roberts $
 */

package gate.corpora;

import gate.corpora.RepositioningInfo.PositionInfo;
import gate.util.Out;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * RepositioningInfo keep information about correspondence of positions
 * between the original and extracted document content. With this information
 * this class could be used for computing of this correspondence in the strict
 * way (return -1 where is no correspondence)
 * or in "flow" way (return near computable position)
 */

public class RepositioningInfo extends ArrayList<PositionInfo> {

  /** Freeze the serialization UID. */
  static final long serialVersionUID = -2895662600168468559L;
  /** Debug flag */
  private static final boolean DEBUG = false;

  /**
   * Just information keeper inner class. No significant functionality.
   */
  public class PositionInfo implements Serializable {

    /** Freeze the serialization UID. */
    static final long serialVersionUID = -7747351720249898499L;

    /** Data members for one peace of text information */
    private long m_origPos, m_origLength, m_currPos, m_currLength;

    /** The only constructor. We haven't set methods for data members. */
    public PositionInfo(long orig, long origLen, long curr, long currLen) {
      m_origPos = orig;
      m_origLength = origLen;
      m_currPos = curr;
      m_currLength = currLen;
    } // PositionInfo

    /** Position in the extracted (and probably changed) content */
    public long getCurrentPosition() {
      return m_currPos;
    } // getCurrentPosition

    /** Position in the original content */
    public long getOriginalPosition() {
      return m_origPos;
    } // getOriginalPosition

    /** Length of peace of text in the original content */
    public long getOriginalLength() {
      return m_origLength;
    } // getOriginalLength

    /** Length of peace of text in the extracted content */
    public long getCurrentLength() {
      return m_currLength;
    } // getCurrentLength

    /** For debug purposes */
    @Override
    public String toString() {
      return "("+m_origPos+","+m_origLength+","
                +m_currPos+","+m_currLength+")";
    } // toString
  } // class PositionInfo

  /** Default constructor */
  public RepositioningInfo() {
    super();
  } // RepositioningInfo

  /** Create a new position information record. */
  public void addPositionInfo(long origPos, long origLength,
                              long currPos, long currLength) {
    // sorted add of new position
    int insertPos = 0;
    PositionInfo lastPI;

    for(int i = size(); i>0; i--) {
      lastPI = get(i-1);
      if(lastPI.getOriginalPosition() < origPos) {
        insertPos = i;
        break;
      } // if - sort key
    } // for

    add(insertPos, new PositionInfo(origPos, origLength, currPos, currLength));
  } // addPositionInfo

  /** Compute position in extracted content by position in the original content.
   *  If there is no correspondence return -1.
   */
  public long getExtractedPos(long absPos) {
    long result = absPos;
    PositionInfo currPI = null;
    int size = size();

    if(size != 0) {
      long origPos, origLen;
      boolean found = false;

      for(int i=0; i<size; ++i) {
        currPI = get(i);
        origPos = currPI.getOriginalPosition();
        origLen = currPI.getOriginalLength();

        if(absPos <= origPos+origLen) {
          if(absPos < origPos) {
            // outside the range of information
            result = -1;
          }
          else {
            if(absPos == origPos+origLen) {
              // special case for boundaries - if absPos is the end boundary of
              // this PositionInfo record (origPos+origLen) then result should
              // always be the end boundary too (extracted pos + extracted len).
              // Without this check we may get the wrong position when the "orig"
              // length is shorter than the "extracted" length.
              result = currPI.getCurrentPosition() + currPI.getCurrentLength();
            } else {
              // current position + offset in this PositionInfo record
              result = currPI.getCurrentPosition() + absPos - origPos;
            }
            // but don't go beyond the extracted length
            if(result > currPI.getCurrentPosition() + currPI.getCurrentLength()) {
              result = currPI.getCurrentPosition() + currPI.getCurrentLength();
            }
          } // if
          found = true;
          break;
        } // if
      } // for

      if(!found) {
        // after the last repositioning info
        result = -1;
      } // if - !found
    } // if

    return result;
  } // getExtractedPos

  public long getOriginalPos(long relPos) {
    return getOriginalPos(relPos, false);
  } // getOriginalPos

  /** Compute position in original content by position in the extracted content.
   *  If there is no correspondence return -1.
   */
  public long getOriginalPos(long relPos, boolean afterChar) {
    long result = relPos;
    PositionInfo currPI = null;
    int size = size();

    if(size != 0) {
      long currPos, currLen;
      boolean found = false;

      for(int i=0; i<size; ++i) {
        currPI = get(i);
        currPos = currPI.getCurrentPosition();
        currLen = currPI.getCurrentLength();

        if(afterChar && relPos == currPos+currLen) {
          result = currPI.getOriginalPosition() + currPI.getOriginalLength();
          found = true;
          break;
        } // if

        if(relPos < currPos+currLen) {
          if(relPos < currPos) {
            // outside the range of information
            result = -1;
          }
          else {
            // current position + offset in this PositionInfo record
            result = currPI.getOriginalPosition() + relPos - currPos;
          } // if
          found = true;
          break;
        } // if
      } // for

      if(!found) {
        // after the last repositioning info
        result = -1;
      } // if - !found
    } // if

    return result;
  } // getOriginalPos

  /** Not finished yet */
  public long getExtractedPosFlow(long absPos) {
    long result = -1;
    return result;
  } // getExtractedPosFlow

  /** Not finished yet */
  public long getOriginalPosFlow(long relPos) {
    long result = -1;
    return result;
  } // getOriginalPosFlow

  /**
   * Return the position info index containing <B>@param absPos</B>
   * If there is no such position info return -1.
   */
  public int getIndexByOriginalPosition(long absPos) {
    PositionInfo currPI = null;
    int result = -1;

    int size = size();
    long origPos, origLen;

    // Find with the liniear algorithm. Could be extended to binary search.
    for(int i=0; i<size; ++i) {
      currPI = get(i);
      origPos = currPI.getOriginalPosition();
      origLen = currPI.getOriginalLength();

      if(absPos <= origPos+origLen) {
        if(absPos >= origPos) {
          result = i;
        } // if
        break;
      } // if
    } // for

    return result;
  } // getItemByOriginalPosition

  /**
   * Return the position info index containing <B>@param absPos</B>
   * or the index of record before this position.
   * Result is -1 if the position is before the first record.
   * Rezult is size() if the position is after the last record.
   */
  public int getIndexByOriginalPositionFlow(long absPos) {
    PositionInfo currPI = null;

    int size = size();
    int result = size;
    long origPos, origLen;

    // Find with the liniear algorithm. Could be extended to binary search.
    for(int i=0; i<size; ++i) {
      currPI = get(i);
      origPos = currPI.getOriginalPosition();
      origLen = currPI.getOriginalLength();

      if(absPos <= origPos+origLen) {
        // is inside of current record
        if(absPos >= origPos) {
          result = i;
        }
        else {
          // not inside the current recort - return previous
          result = i-1;
        } // if
        break;
      } // if
    } // for

    return result;
  } // getItemByOriginalPositionFlow

  /**
   *  Correct the RepositioningInfo structure for shrink/expand changes.
   *  <br>
   *
   *  Normaly the text peaces have same sizes in both original text and
   *  extracted text. But in some cases there are nonlinear substitutions.
   *  For example the sequence "&lt;" is converted to "<".
   *  <br>
   *
   *  The correction will split the corresponding PositionInfo structure to
   *  3 new records - before correction, correction record and after correction.
   *  Front and end records are the same maner like the original record -
   *  m_origLength == m_currLength, since the middle record has different
   *  values because of shrink/expand changes. All records after this middle
   *  record should be corrected with the difference between these values.
   *  <br>
   *
   *  All m_currPos above the current information record should be corrected
   *  with (origLen - newLen) i.e.
   *  <code> m_currPos -= origLen - newLen; </code>
   *  <br>
   *
   *  @param originalPos Position of changed text in the original content.
   *  @param origLen Length of changed peace of text in the original content.
   *  @param newLen Length of new peace of text substiting the original peace.
   */
  public void correctInformation(long originalPos, long origLen, long newLen) {
    PositionInfo currPI;
    PositionInfo frontPI, correctPI, endPI;

    int index = getIndexByOriginalPositionFlow(originalPos);

    // correct the index when the originalPos precede all records
    if(index == -1) {
      index = 0;
    } // if

   // correction of all other information records
    // All m_currPos above the current record should be corrected with
    // (origLen - newLen) i.e. <code> m_currPos -= origLen - newLen; </code>

    for(int i=index; i<size(); ++i) {
      currPI = get(i);
      currPI.m_currPos -= origLen - newLen;
    } // for

    currPI = get(index);
    if(originalPos >= currPI.m_origPos
        && currPI.m_origPos + currPI.m_origLength >= originalPos + origLen) {
      long frontLen = originalPos - currPI.m_origPos;

      frontPI = new PositionInfo(currPI.m_origPos,
                              frontLen,
                              currPI.m_currPos,
                              frontLen);
      correctPI = new PositionInfo(originalPos,
                              origLen,
                              currPI.m_currPos + frontLen,
                              newLen);
      long endLen = currPI.m_origLength - frontLen - origLen;
      endPI = new PositionInfo(originalPos + origLen,
                              endLen,
                              currPI.m_currPos + frontLen + newLen,
                              endLen);

      set(index, frontPI); // substitute old element
      if(endPI.m_origLength > 0) {
        add(index+1, endPI); // insert new end element
      } // if
      if(correctPI.m_origLength > 0) {
        add(index+1, correctPI); // insert middle new element
      } // if
    } // if - substitution range check
  } // correctInformation

  /**
   *  Correct the original position information in the records. When some text
   *  is shrinked/expanded by the parser. With this method is corrected the
   *  substitution of "\r\n" with "\n".
   */
  public void correctInformationOriginalMove(long originalPos, long moveLen) {
    PositionInfo currPI;

    if(DEBUG) {
      if(originalPos < 380) // debug information restriction
        Out.println("Before correction: "+this);
    } // DEBUG

    int index = getIndexByOriginalPositionFlow(originalPos);

    // correct the index when the originalPos precede all records
    if(index == -1) {
      index = 0;
    } // if

    // position is after all records in list
    if(index == size()) {
      return;
    } // if

    for(int i = index+1; i<size(); ++i) {
      currPI = get(i);
      currPI.m_origPos += moveLen;
    } // for

    currPI = get(index);

    // should we split this record to two new records (inside the record)
    if(originalPos > currPI.m_origPos) {
      if(originalPos < currPI.m_origPos + currPI.m_origLength) {
        PositionInfo frontPI, endPI;
        long frontLen = originalPos - currPI.m_origPos;
        frontPI = new PositionInfo(currPI.m_origPos,
                                frontLen,
                                currPI.m_currPos,
                                frontLen);

        long endLen = currPI.m_origLength - frontLen;
        endPI = new PositionInfo(originalPos + moveLen,
                                endLen,
                                currPI.m_currPos + frontLen,
                                endLen);
        set(index, frontPI); // substitute old element
        if(endPI.m_origLength != 0) {
          add(index+1, endPI); // insert new end element
        } // if - should add this record

        if(DEBUG) {
          if(originalPos < 380) { // debug information restriction
            Out.println("Point 2. Current: "+currPI);
            Out.println("Point 2. frontPI: "+frontPI);
            Out.println("Point 2. endPI: "+endPI);
          }
        } // DEBUG
      } // if - inside the record
    } // if
    else {
      // correction if the position is before the current record
      currPI.m_origPos += moveLen;
    }

    if(DEBUG) {
      if(originalPos < 380) {
        Out.println("Correction move: "+originalPos+", "+moveLen);
        Out.println("Corrected: "+this);
        Out.println("index: "+index);
        /*
        Exception ex = new Exception();
        Out.println("Call point: ");
        ex.printStackTrace();
        */
      }
    } // DEBUG
  } // correctInformationOriginalMove

} // class RepositioningInfo