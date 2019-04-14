package gate.util.maven;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;

public class SimpleMavenCache implements WorkspaceReader, Serializable {

  private static final long serialVersionUID = 8612094868614282978L;

  private File head;

  private SimpleMavenCache tail;

  private transient WorkspaceRepository repo;

  public SimpleMavenCache(File... dir) {

    if(dir == null || dir.length == 0) { throw new NullPointerException(
        "At least one workspace directory must be specified"); }

    head = dir[0];

    if(dir.length > 1) {
      tail = new SimpleMavenCache(Arrays.copyOfRange(dir, 1, dir.length));
    }
  }

  private File getArtifactFile(Artifact artifact) {
    File file = head;

    for(String part : artifact.getGroupId().split("\\.")) {
      file = new File(file, part);
    }

    file = new File(file, artifact.getArtifactId());

    file = new File(file, artifact.getVersion());

    if("".equals(artifact.getClassifier())) {
      file = new File(file, artifact.getArtifactId() + "-"
          + artifact.getVersion() + "." + artifact.getExtension());
    } else {
      file =
          new File(file, artifact.getArtifactId() + "-" + artifact.getVersion()
              + "-" + artifact.getClassifier() + "." + artifact.getExtension());
    }

    return file;
  }

  @Override
  public File findArtifact(Artifact artifact) {
    File file = getArtifactFile(artifact);

    if(file.exists()) return file;

    if(tail == null) return null;

    return tail.findArtifact(artifact);
  }

  @Override
  public List<String> findVersions(Artifact artifact) {
    List<String> versions = new ArrayList<String>();

    if(tail != null) {
      versions.addAll(tail.findVersions(artifact));
    }

    File file = getArtifactFile(artifact).getParentFile().getParentFile();

    if(!file.exists() || !file.isDirectory()) return versions;

    for(File version : file.listFiles()) {
      if(version.isDirectory()) versions.add(version.getName());
    }

    return versions;
  }

  public void cacheArtifact(Artifact artifact)
      throws IOException, SettingsBuildingException,
      DependencyCollectionException, DependencyResolutionException,
      ArtifactResolutionException, ModelBuildingException {

    // setup a maven resolution hierarchy that uses the main local repo as
    // a remote repo and then cache into a new local repo
    List<RemoteRepository> repos = Utils.getRepositoryList();
    RepositorySystem repoSystem = Utils.getRepositorySystem();
    DefaultRepositorySystemSession repoSession =
        Utils.getRepositorySession(repoSystem, null);

    // treat the usual local repository as if it were a remote, ignoring checksum
    // failures as the local repo doesn't have checksums as a rule
    RemoteRepository localAsRemote =
        new RemoteRepository.Builder("localAsRemote", "default",
            repoSession.getLocalRepository().getBasedir().toURI().toString())
                .setPolicy(new RepositoryPolicy(true,
                        RepositoryPolicy.UPDATE_POLICY_NEVER,
                        RepositoryPolicy.CHECKSUM_POLICY_IGNORE))
                .build();

    repos.add(0, localAsRemote);

    repoSession.setLocalRepositoryManager(repoSystem.newLocalRepositoryManager(
        repoSession, new LocalRepository(head.getAbsolutePath())));

    Dependency dependency = new Dependency(artifact, "runtime");

    CollectRequest collectRequest = new CollectRequest(dependency, repos);

    DependencyNode node =
        repoSystem.collectDependencies(repoSession, collectRequest).getRoot();

    DependencyRequest dependencyRequest = new DependencyRequest();
    dependencyRequest.setRoot(node);

    repoSystem.resolveDependencies(repoSession, dependencyRequest);

  }

  public void compact() throws IOException {
    // making the cache using a local repository generates a lot of files
    // we don't need, including duplicated jars for -SNAPSHOT versions so
    // we remove any file from the cache where the name doesn't include
    // the parent folder name within it, which leaves us the main jar, the
    // pom.xml and the -creole.jar plus some .sha1 files
    Files.walkFileTree(head.toPath(), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
          throws IOException {

        String filename = file.getFileName().toString();

        if((!filename.endsWith(".jar") && !filename.endsWith(".pom")) ||
            !filename.contains(file.getParent().getFileName().toString())) {
          java.nio.file.Files.delete(file);
        }

        return FileVisitResult.CONTINUE;
      }
    });
  }

  @Override
  public WorkspaceRepository getRepository() {
    if(repo == null) {
      repo = new WorkspaceRepository();
    }
    return repo;
  }

  public static void main(String args[]) throws Exception {

    for(RemoteRepository repo : Utils.getRepositoryList()) {
      System.out.println(repo);
    }

    Artifact artifactObj = new DefaultArtifact("uk.ac.gate.plugins", "annie",
        "jar", "8.5-SNAPSHOT");
    // artifactObj = artifactObj.setFile(
    // new
    // File("/home/mark/.m2/repository/uk/ac/gate/plugins/annie/8.5-SNAPSHOT/annie-8.5-SNAPSHOT.jar"));

    SimpleMavenCache reader = new SimpleMavenCache(new File("repo"));
    System.out.println(reader.findArtifact(artifactObj));
    System.out.println(reader.findVersions(artifactObj));
    reader.cacheArtifact(artifactObj);
    System.out.println(reader.findArtifact(artifactObj));
    System.out.println(reader.findVersions(artifactObj));

    reader = new SimpleMavenCache(new File("repo2"), new File("repo"));
    System.out.println(reader.findArtifact(artifactObj));
    System.out.println(reader.findVersions(artifactObj));
  }

}
