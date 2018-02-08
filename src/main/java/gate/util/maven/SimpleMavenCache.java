package gate.util.maven;

import static gate.util.maven.Utils.getRepositoryList;
import static gate.util.maven.Utils.getRepositorySession;
import static gate.util.maven.Utils.getRepositorySystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.building.DefaultModelBuilderFactory;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.repository.WorkspaceRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.util.artifact.SubArtifact;

public class SimpleMavenCache implements WorkspaceReader, Serializable {

	private static final long serialVersionUID = 8612094868614282978L;

	private File head;

	private SimpleMavenCache tail;

	private transient WorkspaceRepository repo;

	public SimpleMavenCache(File... dir) {
		
		if (dir == null || dir.length == 0) {
			throw new NullPointerException("At least one workspace directory must be specified");
		}
		
		head = dir[0];

		if (dir.length > 1) {
			tail = new SimpleMavenCache(Arrays.copyOfRange(dir, 1, dir.length));
		}
	}

	private File getArtifactFile(Artifact artifact) {
		File file = head;

		for (String part : artifact.getGroupId().split("\\.")) {
			file = new File(file, part);
		}

		file = new File(file, artifact.getArtifactId());

		file = new File(file, artifact.getVersion());

		file = new File(file, artifact.getArtifactId() + "-" + artifact.getVersion() + "." + artifact.getExtension());

		return file;
	}

	@Override
	public File findArtifact(Artifact artifact) {

		File file = getArtifactFile(artifact);

		if (file.exists())
			return file;

		if (tail == null)
			return null;

		return tail.findArtifact(artifact);
	}

	@Override
	public List<String> findVersions(Artifact artifact) {
		List<String> versions = new ArrayList<String>();

		if (tail != null) {
			versions.addAll(tail.findVersions(artifact));
		}

		File file = getArtifactFile(artifact).getParentFile().getParentFile();

		if (!file.exists() || !file.isDirectory())
			return versions;

		for (File version : file.listFiles()) {
			if (version.isDirectory())
				versions.add(version.getName());
		}

		return versions;
	}

	public void cacheArtifact(Artifact artifact) throws IOException, SettingsBuildingException,
			DependencyCollectionException, DependencyResolutionException, ArtifactResolutionException, ModelBuildingException {

	  List<RemoteRepository> repos = getRepositoryList();
	  
		Dependency dependency = new Dependency(artifact, "runtime");

		RepositorySystem repoSystem = getRepositorySystem();
		RepositorySystemSession repoSession = getRepositorySession(repoSystem, null);

		CollectRequest collectRequest = new CollectRequest(dependency, repos);

		DependencyNode node = repoSystem.collectDependencies(repoSession, collectRequest).getRoot();

		DependencyRequest dependencyRequest = new DependencyRequest();
		dependencyRequest.setRoot(node);

		DependencyResult result = repoSystem.resolveDependencies(repoSession, dependencyRequest);
		
		for (ArtifactResult ar : result.getArtifactResults()) {
			// generate output file name from the *requested* artifact rather
			// than the resolved one (e.g. if we requested a -SNAPSHOT version
			// but got a timestamped one)
			File file = getArtifactFile(ar.getRequest().getArtifact());

			FileUtils.copyFile(ar.getArtifact().getFile(), file);
			
			// get the POM corresponding to the specific resolved JAR
			Artifact pomArtifact = new SubArtifact(ar.getArtifact(),"", "pom");
			
			ArtifactRequest artifactRequest =
          new ArtifactRequest(pomArtifact, repos, null);
			
      ArtifactResult artifactResult =
          repoSystem.resolveArtifact(repoSession,
                  artifactRequest);
      
      // but copy it to a file named for the original requested version number
      file = getArtifactFile(new SubArtifact(ar.getRequest().getArtifact(), "", "pom"));
      FileUtils.copyFile(artifactResult.getArtifact().getFile(), file);        
      
      cacheParents(artifactResult.getArtifact().getFile(), repoSystem, repoSession, repos);
		}
	}
	
  private void cacheParents(File pom, RepositorySystem repoSystem,
      RepositorySystemSession repoSession, List<RemoteRepository> repos)
      throws ModelBuildingException, IOException, ArtifactResolutionException {
    
    ModelBuildingRequest req = new DefaultModelBuildingRequest();
    req.setProcessPlugins(false);
    req.setPomFile(pom);
    req.setModelResolver(
        new SimpleModelResolver(repoSystem, repoSession, repos));
    req.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

    ModelBuilder modelBuilder = new DefaultModelBuilderFactory().newInstance();
    Model model = modelBuilder.build(req).getEffectiveModel();

    Parent parent = model.getParent();

    if(parent == null) return;

    Artifact pomArtifact = new DefaultArtifact(parent.getGroupId(),
        parent.getArtifactId(), "pom", parent.getVersion());

    ArtifactRequest artifactRequest =
        new ArtifactRequest(pomArtifact, repos, null);

    ArtifactResult artifactResult =
        repoSystem.resolveArtifact(repoSession, artifactRequest);

    // but copy it to a file named for the original requested version number
    File file = getArtifactFile(artifactRequest.getArtifact());
    FileUtils.copyFile(artifactResult.getArtifact().getFile(), file);

    cacheParents(artifactResult.getArtifact().getFile(), repoSystem,
        repoSession, repos);
  }


	@Override
	public WorkspaceRepository getRepository() {
		if (repo == null) {
			repo = new WorkspaceRepository();
		}
		return repo;
	}

	public static void main(String args[]) throws Exception {

		for (RemoteRepository repo : Utils.getRepositoryList()) {
			System.out.println(repo);
		}

		Artifact artifactObj = new DefaultArtifact("uk.ac.gate.plugins", "annie", "jar", "8.5-SNAPSHOT");
		//artifactObj = artifactObj.setFile(
		//		new File("/home/mark/.m2/repository/uk/ac/gate/plugins/annie/8.5-SNAPSHOT/annie-8.5-SNAPSHOT.jar"));

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
