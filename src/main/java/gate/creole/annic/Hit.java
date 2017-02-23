/*
 *  Hit.java
 *
 *  Niraj Aswani, 19/March/07
 *
 *  $Id: Hit.html,v 1.0 2007/03/19 16:22:01 niraj Exp $
 */
package gate.creole.annic;

/**
 * Hit is a unit of result that is returned when user searches the annic
 * index. It stores the four things: document ID (String), start and end
 * offsets (int) and the query that matched this hit.
 * 
 * @author niraj
 * 
 */
public class Hit implements java.io.Serializable {

  /**
   * serial version id
   */
  private static final long serialVersionUID = 3257562927719658297L;

  /**
   * Start OFfset of the found pattern
   */
  protected int startOffset;

  /**
   * End Offset of the found pattern
   */
  protected int endOffset;

  /**
   * Document ID - the document this Hit belongs to
   */
  protected String documentID;

  /**
   * Annotation Set Name - the annotation set this Hit belongs to
   */
  protected String annotationSetName;
  
  /**
   * Query that matches with this instance of Hit.
   */
  protected String queryString;

  /**
   * Constructor
   * @param docID
   * @param annotationSetName
   * @param startOffset
   * @param endOffset
   * @param queryString
   */
  public Hit(String docID, String annotationSetName, int startOffset, int endOffset, String queryString) {
    this.documentID = docID;
    this.annotationSetName = annotationSetName;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.queryString = queryString;
  }

  /**
   * Returns the start offset of the matching part (query matched part)
   */
  public int getStartOffset() {
    return startOffset;
  }

  /**
   * Returns the end offset of the matching part (query matched part)
   */
  public int getEndOffset() {
    return endOffset;
  }

  /**
   * Returns the document ID
   */
  public String getDocumentID() {
    return this.documentID;
  }

  /**
   * Returns the query for which the current pattern was matched
   */
  public String getQueryString() {
    return this.queryString;
  }

  /**
   * Returns the annotation set this pattern belongs to.
   */
  public String getAnnotationSetName() {
    return annotationSetName;
  }
}
