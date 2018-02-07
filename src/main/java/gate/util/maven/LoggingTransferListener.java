package gate.util.maven;

import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;

public class LoggingTransferListener extends AbstractTransferListener {

  public void transferInitiated( TransferEvent event )
      throws TransferCancelledException
  {
    logEvent(event);
  }

  public void transferStarted( TransferEvent event )
      throws TransferCancelledException
  {
    logEvent(event);
  }

  public void transferProgressed( TransferEvent event )
      throws TransferCancelledException
  {
    logEvent(event);
  }

  public void transferCorrupted( TransferEvent event )
      throws TransferCancelledException
  {
    logEvent(event);
  }

  public void transferSucceeded( TransferEvent event )
  {
    logEvent(event);
  }

  public void transferFailed( TransferEvent event )
  {
    logEvent(event);
  }
  
  private void logEvent(TransferEvent event) {
    //new Throwable().printStackTrace(System.out);
    System.out.println(event.getResource().getContentLength()+"/"+event.getTransferredBytes()+"/"+event.getResource().getFile().getName());
  }
  
}
