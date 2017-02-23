/*
 *  ObjectWriter.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 21 Feb 2000
 *
 *  $Id: ObjectWriter.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */

package gate.util;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/** Writes an object to an PipedOutputStream wich can be connected to a
  * PipedInputStream.
  * Before writting the object it also writes it in a buffer and finds
  * out its size so it can be reported via getSize method.
  * All read/writes occur in separate threads to avoid a deadlock.
  */
public class ObjectWriter extends Thread {

  public ObjectWriter(Object obj) throws IOException {
    size = 0;
    Writer writer = new Writer(obj);
    InputStream is = writer.getInputStream();
    writer.start();
    boolean over = false;
    buffer = new LinkedList<byte[]>();

    //how much space is available in lastBuff
    int space = buffSize;

    //where to write in lastBuff
    int writeOffset = 0;
    byte lastBuff[] = new byte[buffSize];

    while (!over) {
      int read = is.read(lastBuff, writeOffset, space);
      if(read == -1) {
        lastOffset = writeOffset;
        buffer.addLast(lastBuff);
        over = true;
      } else {
        space-= read;
        size+=read;
        if(space == 0) {
          // no more space; we need a new buffer
          buffer.addLast(lastBuff);
          space = buffSize;
          writeOffset = 0;
          lastBuff = new byte[buffSize];
        } else {
          // current buffer not full yet
          writeOffset+=read;
        }
      }
    };// while(!over)

    // will be used to write the data
    outputStream = new PipedOutputStream();

    // will be returned for objects that want to read the object
    inputStream = new PipedInputStream(outputStream);
  }

  /**
    * Returns a PipedInputStream from which the object given as parameter for
    * the constructor can be read.
    *
    * @return a PipedInputStream connected to PipedOutputStream which writes
    * the object which this ObjectWriter was built for.
    */
  public InputStream getInputStream() {
    return inputStream;
  }

  /**
    * Obtains the object size.
    *
    * @return the size of the object recieved as parameter for the constructor.
    */
  public int getSize() {
    return size;
  }

  /** Writes all the buffers to the output stream
    */
  @Override
  public void run() {
    try{
      Iterator<byte[]> buffIter = buffer.iterator();
      while(buffIter.hasNext()){
        byte currentBuff[] = buffIter.next();
        if(buffIter.hasNext()) {
          // is not the last buffer
          outputStream.write(currentBuff,0,buffSize);
        } else {
          // is the last buffer
          // currentBuff[lastOffset] = '\u001a';
          outputStream.write(currentBuff,0,lastOffset);
        }
      }// while(buffIter.hasNext())

      outputStream.flush();
      outputStream.close();
    } catch(IOException ioe) {
      throw new RuntimeException(ioe.toString());
      // ioe.printStackTrace(Err.getPrintWriter());
    }
  }


  /** I need a thread to write the object so I can read it in an buffer
    * After that I know the size ana I can write it to the output stream
    * after I report the size.
    */
  private class Writer extends Thread {
    public Writer(Object _obj){
      _object = _obj;
      _outputStream = new PipedOutputStream();

      try {
        _inputStream = new PipedInputStream(_outputStream);
      } catch(IOException ioe) {
        ioe.printStackTrace(Err.getPrintWriter());
      }
    }

    public InputStream getInputStream(){
      return _inputStream;
    }

    /**
      * Describe 'run' method here.
      */
    @Override
    public void run(){
      try {
        ObjectOutputStream _oos = new ObjectOutputStream(_outputStream);
        _oos.writeObject(_object);
        _oos.close();
      } catch(IOException ioe) {
        ioe.printStackTrace(Err.getPrintWriter());
      }
    }

    private Object _object;
    private InputStream _inputStream;
    private PipedOutputStream _outputStream;

  }

  private InputStream inputStream ;

  private PipedOutputStream outputStream;

  private int size;

  private int lastOffset;

  private LinkedList<byte[]> buffer;

  private int buffSize = 1024;

} // class ObjectWriter
