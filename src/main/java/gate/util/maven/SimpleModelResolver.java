package gate.util.maven;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Parent;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.UnresolvableModelException;
import org.apache.maven.repository.internal.ArtifactDescriptorUtils;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;

@SuppressWarnings("deprecation")
public class SimpleModelResolver implements ModelResolver {

  private final RepositorySystem system;
  private final RepositorySystemSession session;
  private final Set<String> repositoryIds;
  private List<RemoteRepository> repositories;

  /**
   * Creates a model resolver to assist building of dependency POMs.
   * 
   * @param system a {@link RepositorySystem}
   * @param session a {@link RepositorySystemSession}
   * @param remoteRepositories remote repositories to use for resolution.
   */
  public SimpleModelResolver(RepositorySystem system, RepositorySystemSession session, 
          List<RemoteRepository> remoteRepositories) {
      this.system = system;
      this.session = session;
      this.repositories = new ArrayList<RemoteRepository>(remoteRepositories);
      this.repositoryIds = new HashSet<String>(
              remoteRepositories.size() < 3 ? 3 : remoteRepositories.size());
      
      for(RemoteRepository repository : remoteRepositories) {
          repositoryIds.add(repository.getId());
      }
  }

  /**
   * Clone Constructor.
   * 
   * @param original a SimpleModelResolver.
   */
  private SimpleModelResolver(SimpleModelResolver original) {
      this.session = original.session;
      this.system = original.system;
      this.repositoryIds = new HashSet<String>(original.repositoryIds);
  }

  @Override
  public void addRepository(org.apache.maven.model.Repository repository) throws InvalidRepositoryException {
      if (!repositoryIds.add(repository.getId())) {
          return;
      }

      this.repositories.add(ArtifactDescriptorUtils.toRemoteRepository(repository));
  }

  @Override
  public ModelResolver newCopy() {
      return new SimpleModelResolver(this);
  }

  @Override
  public ModelSource resolveModel(String groupId, String artifactId, String version)
          throws UnresolvableModelException {
      Artifact pomArtifact = new DefaultArtifact(groupId, artifactId, "", "pom", version);

      try {
          ArtifactRequest request = new ArtifactRequest(pomArtifact, repositories, null);
          pomArtifact = system.resolveArtifact(session, request).getArtifact();
      } catch (org.eclipse.aether.resolution.ArtifactResolutionException ex) {
          throw new UnresolvableModelException(ex.getMessage(), groupId, artifactId, version, ex);
      } 

      File pomFile = pomArtifact.getFile();

      return new FileModelSource(pomFile);
  }

  @Override
  public ModelSource resolveModel(Parent parent)
      throws UnresolvableModelException {
    return resolveModel(parent.getGroupId(), parent.getArtifactId(), parent.getVersion());
    
  }

  @Override
  public void addRepository(org.apache.maven.model.Repository repository,
      boolean replace) throws InvalidRepositoryException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ModelSource resolveModel(Dependency dependency)
      throws UnresolvableModelException {
    return resolveModel(dependency.getGroupId(), dependency.getArtifactId(),
        dependency.getVersion());
  }
}
