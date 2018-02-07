package gate.util.maven;

import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.RepositoryEvent;

public class LoggingRepositoryListener extends AbstractRepositoryListener {
  
  public void artifactDeployed( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactDeploying( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactDescriptorInvalid( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactDescriptorMissing( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactDownloaded( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactDownloading( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactInstalled( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactInstalling( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactResolved( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void artifactResolving( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataDeployed( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataDeploying( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataDownloaded( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataDownloading( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataInstalled( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataInstalling( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataInvalid( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataResolved( RepositoryEvent event )
  {
    logEvent(event);
  }

  public void metadataResolving( RepositoryEvent event )
  {
    logEvent(event);
  }
  
  private void logEvent(RepositoryEvent event) {
    System.out.println(event);
  }
}
