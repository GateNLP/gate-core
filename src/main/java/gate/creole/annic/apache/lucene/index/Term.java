package gate.creole.annic.apache.lucene.index;

/**
  A Term represents a word from text.  This is the unit of search.  It is
  composed of two elements, the text of the word, as a string, and the name of
  the field that the text occured in, an interned string.

  Note that terms may represent more than words from text fields, but also
  things like dates, email addresses, urls, etc.  */
@SuppressWarnings({"rawtypes","unused","serial"})
public final class Term implements Comparable, java.io.Serializable {
  String field;
  String text;
  //Niraj
  String type;
  // End


  /** Constructs a Term with the given field and text. */
  public Term(String fld, String txt) {
    this(fld, txt, true);
  }

  // Niraj
  public Term(String fld, String txt, String type/*, int pos*/) {
    this(fld, txt, type, /*pos,*/ true);
  }


  Term(String fld, String txt, String type, /*int pos,*/ boolean intern) {
    field = intern ? fld.intern() : fld;
    text = txt;
    this.type = type;
  }

  // End

  Term(String fld, String txt, boolean intern) {
    field = intern ? fld.intern() : fld;	  // field names are interned
    text = txt;					  // unless already known to be
  }


  /** Returns the field of this term, an interned string.   The field indicates
    the part of a document which this term came from. */
  public final String field() { return field; }

  //Niraj
  public final String type() { return type; }
/*  public final int position() { return position; }*/
  //End

  /** Returns the text of this term.  In the case of words, this is simply the
    text of the word.  In the case of dates and other types, this is an
    encoding of the object as a string.  */
  public final String text() { return text; }

  /** Compares two terms, returning true iff they have the same
      field and text. */
  @Override
  public final boolean equals(Object o) {
    if (o == null)
      return false;
    Term other = (Term)o;
    if (type != null) {
      // Niraj
      //return field == other.field && text.equals(other.text);
      boolean ret = (field.equals(other.field)) &&
          (text.equals(other.text)/* || text.equals("*") || other.text.equals("*")*/) &&
          (type.equals(other.type)/* || type.equals("*") || other.type.equals("*")*/);/* &&
          position == other.position;*/
      return ret;
      // End
    }
    else {
      boolean ret = (field.equals(other.field)) &&
          (text.equals(other.text)/* || text.equals("*") || other.text.equals("*")*/);
      return ret;
    }
  }

  /** Combines the hashCode() of the field and the text. */
  @Override
  public final int hashCode() {
    if(type != null) {
    //Niraj
    //return field.hashCode() + text.hashCode();
    return field.hashCode() + text.hashCode() + type.hashCode();
    //End
    } else {
      return field.hashCode() + text.hashCode();
    }

  }

  @Override
  public int compareTo(Object other) {
    return compareTo((Term)other);
  }

  /** Compares two terms, returning an integer which is less than zero iff this
    term belongs after the argument, equal zero iff this term is equal to the
    argument, and greater than zero iff this term belongs after the argument.

    The ordering of terms is first by field, then by text.*/
  public final int compareTo(Term other) {
    if (field == other.field) {			  // fields are interned
      int rank = text.compareTo(other.text);
      if (rank == 0) {
        rank = type.compareTo(other.type);
        /*if(rank == 0) {
          if(position > other.position)
            return 1;
          else if(position == other.position)
            return 0;
          else
            return -1;
        }*/
        return rank;
      }
      else {
        return rank;
      }
    } else {
      return field.compareTo(other.field);
    }
  }


  public final int indexCompareTo(Term other) {
    if (field.equals(other.field)) {			  // fields are interned
      int rank = text.compareTo(other.text);
      // we need to check for the star wild card characters
      if (rank == 0 && type != null && other.type != null) {
    	  rank = type.compareTo(other.type);
        if(rank == 0 /*|| type.equals("*") || other.type.equals("*")*/) {
          return 0;
          //System.out.println("position "+position+" : "+other.position);
          //return new Integer(position).compareTo(new Integer(other.position));
        }
        return rank;
      }
      else {
        return rank;
      }
    } else {
      int rank = field.compareTo(other.field);
      return rank;
    }
  }

  private boolean isWildcharMatches(String text, String other) {
    /*if(text.endsWith("*")) {
      String text1 = text.substring(0, text.length()-1);
      if(other.startsWith(text1))
        return true;
      else
        return false;
    }
    return false;*/
     java.util.regex.Pattern p = java.util.regex.Pattern.compile(text);
     java.util.regex.Matcher m = p.matcher(other);
     return m.matches();
  }

  /** Resets the field and text of a Term. */
  final void set(String fld, String txt) {
    field = fld;
    text = txt;
  }

  // Niraj
  final void set(String fld, String text, String type/*, int pos*/) {
    field = fld;
    this.text = text;
    this.type = type;
    /*position = pos;*/
  }
  // End

  @Override
  public final String toString() {
    if(type == null)
      return field + ":" + text;
    //Niraj
    return field + ":" + text + ":" + type;/* + ":" + position;*/
    //End
  }

  private void readObject(java.io.ObjectInputStream in)
    throws java.io.IOException, ClassNotFoundException
  {
      in.defaultReadObject();
      field = field.intern();
  }
}
