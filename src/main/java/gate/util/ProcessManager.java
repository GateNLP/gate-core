package gate.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class that supports running an external process and either silently
 * consuming its standard output and error streams, or copying them to Java's
 * stdout and stderr.
 *
 * This implementation reads the output and error streams in separate threads,
 * but tries to reuse these threads from one external call to the next, unlike
 * other approaches I've seen (which all spawn a pair of new threads for every
 * external call).  As a result, instances of this class are <b>not thread
 * safe</b>.  You must use a different instance of ProcessManager in each
 * thread that you use to run external processes.
 */
public class ProcessManager {
  /**
   * Debug flag.
   */
  private static final boolean DEBUG = false;

  /**
   * StreamGobbler thread for standard output.
   */
  private StreamGobbler stdout;

  /**
   * StreamGobbler thread for standard error.
   */
  private StreamGobbler stderr;
  
  private Process proc;

  /**
   * Construct a ProcessManager object and start the gobbler threads.
   */
  public ProcessManager() {
    stdout = new StreamGobbler();
    Thread t = new Thread(stdout);
    t.setDaemon(true);
    t.start();

    stderr = new StreamGobbler();
    t = new Thread(stderr);
    t.setDaemon(true);
    t.start();
  }
  
  public synchronized boolean isProcessRunning() {
    if (proc == null) return false;
    
    try {
      proc.exitValue();
      return false;
    }
    catch (IllegalThreadStateException e) {
      return true;
    }
  }
  
  /**
   * If a process is currently running then this method destroys it so
   * that the ProcessManager instance can be used to start another
   * process
   */
  public synchronized void destroyProcess() throws IOException {
    if(isProcessRunning()) proc.destroy();

    // wait for the gobblers to finish their jobs
    while(!stderr.isDone() || !stdout.isDone()) {
      try {
        if(DEBUG) {
          System.err.println("Gobblers not done, waiting...");
        }
        this.wait();
      } catch(InterruptedException ie) {
        // if interrupted, try waiting again
      }
    }

    // make it really obvious the process has finished
    proc = null;

    // reset the gobblers
    if(DEBUG) {
      System.err.println("Gobblers done - resetting");
    }
    stdout.reset();
    stderr.reset();

    // if there was an exception during running, throw that
    Exception ex = null;
    if(stdout.hasException()) {
      ex = stdout.getException();
      stderr.getException(); // to reset exception cache
    } else if(stderr.hasException()) {
      ex = stderr.getException();
    }

    if(ex != null) {
      if(DEBUG) {
        System.err.println("Rethrowing exception");
      }
      if(ex instanceof IOException) {
        throw (IOException)ex;
      } else if(ex instanceof RuntimeException) {
        throw (RuntimeException)ex;
      } else throw new GateRuntimeException(ex);
    }
  }

  /**
   * Sometimes you want to start an external process and pass data to it
   * at different intervals. A good example is a tool with large startup
   * time where you can feed it one sentence at a time and get a
   * response. If you keep the process running you can use it over
   * multiple documents.
   */
  public synchronized OutputStream startProcess(String[] argv, File dir,
          OutputStream out, OutputStream err) throws IOException {

    if(isProcessRunning())
      throw new IOException("The previous process is still running");

    // Start the process. This may throw an exception
    if(DEBUG) {
      System.err.println("Starting process");
    }

    proc = Runtime.getRuntime().exec(argv, null, dir);

    // set up the stream gobblers for stdout and stderr
    if(DEBUG) {
      System.err.println("Configuring gobblers");
    }
    stdout.setInputStream(proc.getInputStream());
    stdout.setOutputStream(out);

    stderr.setInputStream(proc.getErrorStream());
    stderr.setOutputStream(err);

    // start the gobblers
    if(DEBUG) {
      System.err.println("Waking up gobblers");
    }
    this.notifyAll();

    return proc.getOutputStream();
  }

  /**
   * Run the given external process.  If an exception results from starting the
   * process, or while reading the output from the process, it will be thrown.
   * Otherwise, the exit value from the process is returned.
   *
   * @param argv the process command line, suitable for passing to
   * <code>Runtime.exec</code>.
   * @param dumpOutput should we copy the process output and error streams to
   * the Java output and error streams or just consume them silently?
   */
  public synchronized int runProcess(String[] argv, boolean dumpOutput)
                          throws IOException {
    return runProcess(argv, null, (dumpOutput ? System.out : null), (dumpOutput ? System.err : null));
  }
  
  public synchronized int runProcess(String[] argv, OutputStream out, OutputStream err)
  throws IOException {
    return runProcess(argv, null, out, err);
  }
  
  /**
   * Run the given external process.  If an exception results from starting the
   * process, or while reading the output from the process, it will be thrown.
   * Otherwise, the exit value from the process is returned.
   *
   * @param argv the process command line, suitable for passing to
   * <code>Runtime.exec</code>.
   */
  public synchronized int runProcess(String[] argv, File dir, OutputStream out, OutputStream err)
                          throws IOException {

    if (isProcessRunning()) throw new IOException("The previous process is still running");
    
    // Start the process.  This may throw an exception    
    if(DEBUG) {
      System.err.println("Starting process");
    }
    
    proc = Runtime.getRuntime().exec(argv, null, dir);
        
    // set up the stream gobblers for stdout and stderr
    if(DEBUG) {
      System.err.println("Configuring gobblers");
    }
    stdout.setInputStream(proc.getInputStream());
    stdout.setOutputStream(out);

    stderr.setInputStream(proc.getErrorStream());
    stderr.setOutputStream(err);

    // start the gobblers
    if(DEBUG) {
      System.err.println("Waking up gobblers");
    }
    this.notifyAll();

    // wait for the gobblers to finish their jobs
    while(!stderr.isDone() || !stdout.isDone()) {
      try {
        if(DEBUG) {
          System.err.println("Gobblers not done, waiting...");
        }
        this.wait();
      }
      catch(InterruptedException ie) {
        // if interrupted, try waiting again
      }
    }

    // get the return code from the process
    Integer returnCode = null;
    while(returnCode == null) {
      try {
        returnCode = new Integer(proc.waitFor());
      }
      catch(InterruptedException ie) {
        // if interrupted, just try waiting again
      }
    }
    
    // make it really obvious the process has finished
    proc = null;
    
    // reset the gobblers
    if(DEBUG) {
      System.err.println("Gobblers done - resetting");
    }
    stdout.reset();
    stderr.reset();
    
    // if there was an exception during running, throw that
    Exception ex = null;
    if(stdout.hasException()) {
      ex = stdout.getException();
      stderr.getException(); // to reset exception cache
    }
    else if(stderr.hasException()) {
      ex = stderr.getException();
    }

    if(ex != null) {
      if(DEBUG) {
        System.err.println("Rethrowing exception");
      }
      if(ex instanceof IOException) {
        throw (IOException)ex;
      }
      else if(ex instanceof RuntimeException) {
        throw (RuntimeException)ex;
      }
      else throw new GateRuntimeException(ex);
    }
    // otherwise return the exit code
    else {
      return returnCode.intValue();
    }
  }

  /**
   * Thread body that takes a stream and either consumes it silently or echoes
   * it to another stream.
   */
  private class StreamGobbler implements Runnable {
    /**
     * The input stream to gobble.  If this is null, the thread is idle.
     */
    private InputStream inputStream = null;

    /**
     * The output stream to echo to.  If this is null the gobbler silently
     * discards the input stream contents.
     */
    private OutputStream outputStream = null;;

    /**
     * Buffer used for reading and writing.
     */
    private byte[] buf = new byte[4096];

    /**
     * Are we finished?  This is set to true once the stream has been emptied.
     */
    private boolean done = false;

    /**
     * If an exception is thrown during gobbling, it is stored here.
     */
    private Exception exception = null;

    /**
     * Set the stream to gobble.  This should not be called while the thread is
     * active.
     */
    void setInputStream(InputStream is) {
      inputStream = is;
    }

    /**
     * Set the output stream to redirect output to.  A value of
     * <code>null</code> indicates that we should discard the output without
     * echoing it.
     */
    void setOutputStream(OutputStream os) {
      outputStream = os;
    }

    /**
     * Has an exception been thrown since {@link #getException()} was last
     * called?
     */
    boolean hasException() {
      return (exception != null);
    }

    /**
     * Return the last exception thrown.  This also resets the cached exception
     * to <code>null</code>.
     */
    Exception getException() {
      Exception ex = exception;
      exception = null;
      return ex;
    }

    boolean isDone() {
      return done;
    }

    /**
     * Reset state.
     */
    void reset() {
      done = false;
      exception = null;
      inputStream = null;
    }

    /**
     * Main body of the thread.  Waits until we have been given a stream to
     * gobble, then reads it until there is no more input available.
     */
    @Override
    public void run() {
      if(DEBUG) {
        System.err.println("StreamGobbler starting");
      }
      // wait until we have a stream to gobble
      synchronized(ProcessManager.this) {
        while(inputStream == null) {
          try {
            if(DEBUG) {
              System.err.println("Waiting for stream...");
            }
            ProcessManager.this.wait();
          }
          catch(InterruptedException ie) {
          }
        }
      }

      while(true) {
        // read the stream until end of file or an exception is thrown.
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        int bytesRead = -1;
        try {
          if(DEBUG) {
            System.err.println("Gobbling stream");
          }
          while((bytesRead = bis.read(buf)) != -1) {
            // echo to outputStream if necessary
            if(outputStream != null) {
              outputStream.write(buf, 0, bytesRead);
            }
          }
        }
        catch(Exception ex) {
          // any exception is stored to be retrieved by the ProcessManager
          exception = ex;
          if(DEBUG) {
            System.err.println("Exception thrown");
          }
        }
        
        try {
          bis.close();
        }
        catch(IOException ioe) {
          // oh well, it's not the end of the world
        }

        done = true;
        inputStream = null;
        outputStream = null;

        // wake up ProcessManager to say we've finished, and start waiting for
        // the next round.
        synchronized(ProcessManager.this) {
          if(DEBUG) {
            System.err.println("Waking process manager");
          }
          ProcessManager.this.notifyAll();
          while(inputStream == null) {
            try {
              if(DEBUG) {
                System.err.println("Waiting for stream (2)");
              }
              ProcessManager.this.wait();
            }
            catch(InterruptedException ie) {
            }
          }
        }
      }
    }
  }
}
