package gate.creole;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.corpora.TestDocument;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.util.persistence.PersistenceManager;
import java.nio.charset.Charset;
import junit.framework.TestCase;

@SuppressWarnings("serial")
public class TestResourceReference extends TestCase {

  private Plugin creolePlugin;

  @CreoleResource
  public static class TestResource extends AbstractProcessingResource {

    ResourceReference rr = null;

    public ResourceReference getParam() {
      return rr;
    }

    @CreoleParameter(defaultValue = "resources/file.txt")
    public void setParam(ResourceReference rr) {
      this.rr = rr;
    }    
  }
  
  public static class TestPlugin extends Plugin.Maven {
    
    public TestPlugin() {
      super("group", "artifact", "version");
    }

    @Override
    public URL getBaseURL() {
      try {
        return new URL(TestDocument.getTestServerName() + "tests/");
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
    
    @Override
    public Document getMetadataXML() throws Exception {
      return getCreoleXML();
    }

    @Override
    public Document getCreoleXML() throws Exception, JDOMException {
      Document doc = new Document();
      Element element = null;
      doc.addContent(element = new Element("CREOLE-DIRECTORY"));

      element.addContent(element = new Element("CREOLE"));
      element.addContent(element = new Element("RESOURCE"));
      Element classElement = new Element("CLASS");
      classElement.setText(TestResource.class.getName());
      element.addContent(classElement);
      return doc;
    }
  };

  @Override
  public void setUp() throws Exception {
    // Initialise the GATE library and creole register
    Gate.init();
    
    creolePlugin = new TestPlugin();

    Gate.getCreoleRegister().registerPlugin(creolePlugin);
  }

  public void testReadFromURL() throws Exception {

    URL url = getClass().getClassLoader()
        .getResource("gate/resources/gate.ac.uk/creole/creole.xml");
    ResourceReference rr = new ResourceReference(url);

    try (InputStream in = rr.openStream()) {
      String contents = IOUtils.toString(in);

      assertEquals("Length of data read not as expected", 98,
          contents.length());
    }
  }

  public void testReadFromURLPlugin() throws Exception {
    // find a URL for finding test files and add to the directory set
    URL testURL = new URL(TestDocument.getTestServerName() + "tests/");
    Plugin p = new Plugin.Directory(testURL);
    ResourceReference rr = new ResourceReference(p, "gate.xml");

    try (InputStream in = rr.openStream()) {
      String contents = IOUtils.toString(in, Charset.forName("UTF-8"));

      assertEquals("Length of data read not as expected", 658,
          contents.length());
    }
  }

  public void testCreateFromURL() throws Exception {
    Resource resource = new TestResource();

    URL url = new URL("http://gate.ac.uk");
    resource.setParameterValue("param", url);

    assertEquals("References do not match", url,
        ((ResourceReference)resource.getParameterValue("param")).toURL());
  }

  public void testRelativeReferences() throws Exception {
    URL testURL = new URL(TestDocument.getTestServerName() + "tests/");
    URL creoleURL =
        new URL(TestDocument.getTestServerName() + "tests/creole.xml");

    ResourceReference context = new ResourceReference(testURL);
    ResourceReference rr = new ResourceReference(context, "./creole.xml");
    assertEquals("References do not match (1)", creoleURL, rr.toURL());

    Plugin plugin = new Plugin.Directory(testURL);
    context = new ResourceReference(plugin, "abc");
    rr = new ResourceReference(context, "./creole.xml");
    assertEquals("References do not match (2)", creoleURL, rr.toURL());

    context = new ResourceReference(plugin, "html/");
    rr = new ResourceReference(context, "../creole.xml");
    assertEquals("References do not match (3)", creoleURL, rr.toURL());

    URI creoleURI = new URI("creole://group;artifact;version/creole.xml");

    context = new ResourceReference(creolePlugin, "folder/");
    rr = new ResourceReference(context, "../creole.xml");
    assertEquals("References do not match (4)", creoleURI, rr.toURI());
    assertEquals("URLs do not match (4)", creoleURL, rr.toURL());
  }

  public void testCreateFromAbsolutePath() throws Exception {
    String path = "creole://group;artifact;version/test-file.xml";

    ResourceReference rr = new ResourceReference((ResourceReference)null, path);
    assertEquals("String representations don't match (1)", path, rr.toString());

    rr = new ResourceReference((Plugin)null, path);
    assertEquals("String representations don't match (2)", path, rr.toString());

    rr = new ResourceReference(
        new ResourceReference(new URL("http://gate.ac.uk")), path);
    assertEquals("String representations don't match (3)", path, rr.toString());

    rr = new ResourceReference(creolePlugin, path);
    assertEquals("String representations don't match (4)", path, rr.toString());
  }

  public void testDefaultValue() throws Exception {

    Resource resource = null;

    try {
      resource = Factory.createResource(TestResource.class.getName());

      ResourceReference rr =
          (ResourceReference)resource.getParameterValue("param");

      assertNotNull("ResourceReference param should not be null", rr);

      assertEquals("References do not match",
          new URI("creole://group;artifact;version/resources/file.txt"),
          rr.toURI());
    } finally {
      if(resource != null) {
        Factory.deleteResource(resource);
      }
    }
  }

  public void testPersistence() throws Exception {
      File xgappFile = File.createTempFile("rr-test", ".xgapp");
      xgappFile.deleteOnExit();
      
      File txtFile = new File(xgappFile.getParentFile(),"test-file.txt");
      txtFile.deleteOnExit();
      ResourceReference rr = new ResourceReference(txtFile.toURI());      
      checkPersistence(xgappFile, rr, "$relpath$test-file.txt");
      
      rr = new ResourceReference(creolePlugin,"resources/test.txt");
      checkPersistence(xgappFile, rr, "creole://group;artifact;version/resources/test.txt"); 
      
      rr = new ResourceReference(new URL("http://gate.ac.uk/resource/file.txt"));
      checkPersistence(xgappFile, rr, "http://gate.ac.uk/resource/file.txt");
  }
  
  public void checkPersistence(File xgappFile, ResourceReference rr1, String expected) throws Exception {
    Resource resource = null, restored = null;
    
    try {
            
      FeatureMap params = Factory.newFeatureMap();      
      params.put("param", rr1);
      resource = Factory.createResource(TestResource.class.getName(),params);      
            
      PersistenceManager.saveObjectToFile(resource, xgappFile);
            
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(xgappFile);
      
      Element entry = doc.getRootElement().getChild("application").getChild("initParams").getChild("localMap").getChild("entry");
      
      assertEquals("couldn't find the paramameter entry", "param",entry.getChildText("string"));
      
      Element value = entry.getChild("gate.util.persistence.PersistenceManager-RRPersistence");
      
      assertNotNull("We couldn't find the RRPersistence wrapper",value);
      
      assertEquals("The URI was not as expected",expected, value.getChildText("uriString"));
      
      restored = (Resource)PersistenceManager.loadObjectFromFile(xgappFile);
      
      ResourceReference rr2 = (ResourceReference)restored.getParameterValue("param");
      
      assertEquals(rr1, rr2);
      
    } finally {
      if (xgappFile != null) xgappFile.deleteOnExit();
      
      if(resource != null) {
        Factory.deleteResource(resource);
      }
      
      if(restored != null) {
        Factory.deleteResource(restored);
      } 
    }
  }
}