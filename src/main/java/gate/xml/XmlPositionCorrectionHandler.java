/*
 *  XmlPositionCorrectionHandler.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Angel Kirilov,  4 January 2002
 *
 *  $Id: XmlPositionCorrectionHandler.java 17605 2014-03-09 10:15:34Z markagreenwood $
 */

package gate.xml;

import org.xml.sax.helpers.DefaultHandler;


/**
 * This class correct a Xerces parser bug in reported position in file during
 * the parsing process. Xerces parser cut processed file to 16K peaces. If
 * the parser cross the 16K border reported in the characters() position is
 * zerro.
 *
 * This bug could be covered if you extend this content handler instead of
 * org.xml.sax.helpers.DefaultHandler.
 *
 * The real content handler should call methods startDocument() and characters()
 * in order to compute correct position in file. The corrected position could be
 * received throug protected data member m_realOffset or with getRealOffset().
 */
public class XmlPositionCorrectionHandler extends DefaultHandler {

  /**
   * Variables for correction of 16K parser limit for offset
   */
  protected long m_realOffset;
  private int m_lastPosition;
  private int m_lastSize;
  private int m_multiplyer;

  /** Constructor for initialization of variables */
  public XmlPositionCorrectionHandler() {
    m_realOffset = 0;
    m_lastPosition = 0;
    m_lastSize = 0;
    m_multiplyer = 0;
  } // XmlPositionCorrectionHandler

  /** Initialization of variables on start of document parsing */
  @Override
  public void startDocument() throws org.xml.sax.SAXException {
    m_realOffset = 0;
    m_lastPosition = 0;
    m_lastSize = 0;
    m_multiplyer = 0;
  } // startDocument

  /** Return corrected offset for last characters() call */
  public long getRealOffset() {
    return m_realOffset;
  } // getRealOffset

  /** Here is the correction of the Xerces parser bug. */
  @Override
  public void characters(char[] text, int offset, int len)
                  throws org.xml.sax.SAXException {
    if(offset == 0 && len == 1 && text.length <= 2) {
        // unicode char or &xxx; coding
        return;
    } // if

    // There is 16K limit for offset. Here is the correction.
    // Will catch the bug in most cases.
    if(m_lastPosition - offset > 0x2000
        || (offset == 0 && m_lastSize+m_lastPosition > 0x3000) ) {
        m_multiplyer++;
    }
    m_lastPosition = offset;
    m_lastSize = len;
    m_realOffset = m_multiplyer*0x4000+offset;
  } // characters

} // XmlPositionCorrectionHandler