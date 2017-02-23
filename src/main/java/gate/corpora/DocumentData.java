/*
 *  DocumentData.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Marin Dimitrov, 05/Mar/2002
 *
 *  $Id: DocumentData.java 19642 2016-10-06 09:52:06Z markagreenwood $
 */

package gate.corpora;

import java.io.Serializable;

public class DocumentData implements Serializable {

  //fix the ID for serialisation
  static final long serialVersionUID = 4192762901421847525L;

  public DocumentData(String name, Object ID, String classType){
    docName = name;
    persistentID = ID;
    this.classType = classType; 
  }

  public DocumentData(String name, Object ID){
    docName = name;
    persistentID = ID;
    this.classType = "gate.corpora.DocumentImpl"; 
  }
  
  public String getDocumentName() {
    return docName;
  }

  public Object getPersistentID() {
    return persistentID;
  }

  public void setPersistentID(Object newID) {
    persistentID = newID;
  }

  @Override
  public String toString() {
    return "DocumentData: " + docName + ", " + persistentID + ", " + classType;
  }

  String docName;
  Object persistentID;
  String classType;

  public String getClassType() {
    if(classType == null) {
      classType = DocumentImpl.class.getName();
    }
    return classType;
  }

  public void setClassType(String classType) {
     this.classType = classType;
    
  }
}

