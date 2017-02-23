package gate.util.ant;

import gate.Gate;
import gate.creole.CreoleAnnotationHandler;
import gate.creole.Plugin;
import gate.creole.metadata.CreoleResource;
import gate.util.CreoleXmlUpperCaseFilter;
import gate.util.GateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * Ant task to take a bunch of creole.xml files, process the
 * {@link CreoleResource} annotations on their resources, and write the
 * augmented XML to a target directory.  If the "classesOnly" attribute is
 * set to "true" (or "1", "yes" or "on" in the normal Ant way) then the task
 * performs only the first JAR scanning step, adding
 * <pre>
 * &lt;RESOURCE&gt;
 *   &lt;CLASS&gt;com.example.ClassName&lt;/CLASS&gt;
 * &lt;/RESOURCE&gt;
 * </pre>
 * for each annotated class in the JAR, but does not process the annotation
 * fully.  This limited expansion is useful in cases where you will be using
 * the plugin in a way that does not allow JAR scanning to take place at
 * runtime, for example if you will be loading the plugin directly from a
 * <code>jar:<code> URL (with the plugin's JAR files placed on your
 * application's classpath).
 */
public class ExpandCreoleXmls extends Task {

  private List<FileSet> srcFiles = new ArrayList<FileSet>();
  
  private File toDir;
  
  private File gateHome = null;
  
  private File pluginsHome = null;

  private boolean classesOnly = false;
  
  private SAXBuilder builder;
  
  private XMLOutputter outputter = new XMLOutputter();

  public ExpandCreoleXmls() {
    builder = new SAXBuilder(false);
    builder.setXMLFilter(new CreoleXmlUpperCaseFilter());
  }
  
  public void addFileset(FileSet fs) {
    srcFiles.add(fs);
  }
  
  public void setTodir(File toDir) {
    this.toDir = toDir;
  }

  public void setClassesOnly(boolean classesOnly) {
    this.classesOnly = classesOnly;
  }
  
  @Override
  public void execute() throws BuildException {
    if(toDir == null) {
      throw new BuildException("Please specify a destination directory using todir", getLocation());
    }
    if(toDir.isFile()) {
      throw new BuildException("Destination already exists and is not a directory", getLocation());
    }

    if(Gate.isInitialised()) {
      log("GATE already initialised, gatehome and pluginshome attributes ignored", Project.MSG_VERBOSE);
    } else {
      try {
        Gate.setGateHome(gateHome);
        Gate.setPluginsHome(pluginsHome);
        Gate.init();
      }
      catch(GateException e) {
        throw new BuildException("Error initialising GATE", e, getLocation());
      }
    }
    for(FileSet fs : srcFiles) {
      DirectoryScanner ds = fs.getDirectoryScanner(getProject());
      for(String f : ds.getIncludedFiles()) {
        File creoleFile = new File(ds.getBasedir(), f);
        try {
          File plugin = creoleFile.getParentFile();
          File destFile = new File(toDir, f);
          File destPlugin = destFile.getParentFile();

          log("Expanding " + creoleFile + " to " + destFile, Project.MSG_VERBOSE);
          //TODO check this works
          Plugin dirPlugin = new Plugin.Directory(plugin.toURI().toURL());
          Gate.addKnownPlugin(dirPlugin);
          CreoleAnnotationHandler annotationHandler = new CreoleAnnotationHandler(dirPlugin);
          Document creoleDoc = builder.build(creoleFile);
          annotationHandler.createResourceElementsForDirInfo(creoleDoc);
          if(!classesOnly) {
            annotationHandler.addJarsToClassLoader(Gate.getClassLoader().getDisposableClassLoader(plugin.toURI().toURL().toString()), creoleDoc);
            annotationHandler.processAnnotations(creoleDoc);
          }
          
          // strip out SCAN="true" attributes as scanning has now been done
          @SuppressWarnings({"unchecked", "serial"})
          Iterator<Element> scannedJars = creoleDoc.getDescendants(new ElementFilter("JAR").and(new Filter() {
            @Override
            public boolean matches(Object o) {
              return "true".equalsIgnoreCase(((Element)o).getAttributeValue("SCAN"));
            }
          }));
          while(scannedJars.hasNext()) {
            scannedJars.next().removeAttribute("SCAN");
          }
          
          destPlugin.mkdirs();
          FileOutputStream fos = new FileOutputStream(destFile);
          try {
            outputter.output(creoleDoc, fos);
          }
          finally {
            fos.close();
          }
        }
        catch(Throwable e) {
          log("Error processing " + creoleFile + ", skipped", Project.MSG_WARN);
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);
          e.printStackTrace(pw);
          log(sw.toString(), Project.MSG_VERBOSE);
        }
      }
    }
  }

  public void setGateHome(File gateHome) {
    this.gateHome = gateHome;
  }
  
  public void setPluginsHome(File pluginsHome) {
    this.pluginsHome = pluginsHome;
  }

}
