/*
 *  TestCreole.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Hamish Cunningham, 16/Mar/00
 *
 *  $Id: TestCreole.java 19985 2017-01-25 14:20:13Z markagreenwood $
 */

package gate.creole;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gate.CreoleRegister;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageResource;
import gate.ProcessingResource;
import gate.Resource;
import gate.corpora.TestDocument;
import gate.util.GateException;
import gate.util.Out;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** CREOLE test class
  */
public class TestCreole extends TestCase
{
  /** Debug flag */
  private static final boolean DEBUG = false;

  /** Construction */
  public TestCreole(String name) throws GateException { super(name); }

  /** Local shorthand for the CREOLE register */
  private CreoleRegister reg;

  /** Fixture set up */
  @Override
  public void setUp() throws Exception {
    // Initialise the GATE library and creole register
    Gate.init();

    // clear the register and the creole directory set
    reg = Gate.getCreoleRegister();
    reg.clear();

    // find a URL for finding test files and add to the directory set
    URL testUrl = new URL(TestDocument.getTestServerName()+"tests/");
    reg.registerPlugin(new Plugin.Directory(testUrl));
    
    if(DEBUG) {
      Iterator<ResourceData> iter = reg.values().iterator();
      while(iter.hasNext()) Out.println(iter.next());
    }
  } // setUp

  /** Put things back as they should be after running tests
    * (reinitialise the CREOLE register).
    */
  @Override
  public void tearDown() throws Exception {
    reg.clear();
    Gate.initCreoleRegister();
  } // tearDown

  /** Test the getInstances methods on CreoleRegister */
  public void testInstanceLists() throws Exception {
    // misc locals
    List<? extends Resource> l;
    CreoleRegister cr = Gate.getCreoleRegister();
    int numLrInstances = 0;

    // The only instances of any type should be autoloading ones
    l = cr.getVrInstances();
    if(! allAutoloaders(l))
      fail(" non-autoloading resources already present (1)");
    l = cr.getLrInstances();
    numLrInstances = l.size();
    if(! allAutoloaders(l))
      fail(" non-autoloading resources already present (2)");
    l = cr.getPrInstances();
    if(! allAutoloaders(l))
      fail(" non-autoloading resources already present (3)");

    // Create an LR
    FeatureMap params = Factory.newFeatureMap();
    params.put("features", Factory.newFeatureMap());
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    Factory.createResource("gate.corpora.DocumentImpl", params);

    // lr instances list should be one longer now
    if(! (cr.getLrInstances().size() == numLrInstances + 1))
      fail("wrong number of LRs");

    // Create another LR
    params = Factory.newFeatureMap();
    params.put("features", Factory.newFeatureMap());
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    Factory.createResource("gate.corpora.DocumentImpl", params);

    // lr instances list should be two longer now
    if(! (cr.getLrInstances().size() == numLrInstances + 2))
      fail("wrong number of LRs");

    // we should have two instances of type document
    l = cr.getLrInstances("gate.corpora.DocumentImpl");
    if(l.size() != 2)
      fail("wrong number of documents");
  } // testInstanceLists


  /** Test view registration */
  public void testViews() throws Exception {
    List<String> smallViews1 =
                  reg.getSmallVRsForResource("gate.persist.SerialDataStore");
    String className1 = new String("");
    if (smallViews1!= null && smallViews1.size()>0)
      className1 = smallViews1.get(0);
    assertTrue(
      "Found "+className1+
      " as small viewer for gate.persist.SerialDataStore, "+
      "instead  of gate.gui.SerialDatastoreViewer",
      smallViews1.size() == 1 &&
      "gate.gui.SerialDatastoreViewer".equals(className1)
    );

    List<String> largeViews1 =
                  reg.getLargeVRsForResource("gate.Corpus");
    assertTrue(
      "Found "+largeViews1.size()+" wich are " +largeViews1 +
      " as large viewers for gate.Corpus, "+
     "instead  of 1 which is [gate.gui.CorpusEditor]",
      largeViews1.size() == 1
    );

    List<String> largeViews2 =
                  reg.getLargeVRsForResource("gate.Document");
    assertTrue(
      "Found "+largeViews2.size()+" wich are " +largeViews2 +
      " as large viewers for gate.Document, "+
     "instead  of 0",
      largeViews2.size() == 0
    );

    List<String> annotViews1 =
                  reg.getAnnotationVRs();
    assertTrue(
      "Found "+annotViews1.size()+" wich are " +annotViews1 +
      " as annotation viewers for all types annotations, "+
     "instead  of 0",
      annotViews1.size() == 0
    );
  } // testViews()

  /** Utility method to check that a list of resources are all
    * auto-loading.
    */
  protected boolean allAutoloaders(List<? extends Resource> l) {
    if(l != null) {
      Resource res = null;
      ResourceData resData = null;
      CreoleRegister cr = Gate.getCreoleRegister();
      Iterator<? extends Resource> iter = l.iterator();
      while(iter.hasNext()) {
        res = iter.next();
        if(DEBUG) Out.prln(res);
        resData = cr.get(res.getClass().getName());
        if(DEBUG) Out.prln(resData);
        if(! resData.isAutoLoading())
          return false;
      }
    }

    return true;
  } // allAutoloaders

  /** Test resource discovery */
  public void testDiscovery() throws Exception {

    CreoleRegister reg = Gate.getCreoleRegister();
    if(DEBUG) {
      Iterator<ResourceData> iter = reg.values().iterator();
      while(iter.hasNext()) Out.println(iter.next());
    }

    //ResourceData rd = reg.get("gate.creole.tokeniser.DefaultTokeniser");
    //assertNotNull("couldn't find unicode tok in register of resources", rd);
    //assertTrue(rd.getName().equals("ANNIE Unicode Tokeniser"));

    String docFormatName = "gate.corpora.XmlDocumentFormat";
    ResourceData xmlDocFormatRD = reg.get(docFormatName);
    assertTrue(xmlDocFormatRD.getName().equals("Sheffield XML Document Format"));
    assertTrue(xmlDocFormatRD.isAutoLoading());
    
    ResourceData rd = reg.get("testpkg.TestPR1");
    assertTrue(rd.getJarFileName().equals("TestResources.jar"));
  } // testDiscovery()

  /** Test resource metadata */
  public void testMetadata() throws Exception {

    // get some res data from the register
    ResourceData pr1rd = reg.get("testpkg.TestPR1");
    ResourceData pr2rd = reg.get("testpkg.TestPR2");
    assertTrue(pr1rd != null & pr2rd != null);
    assertTrue(pr2rd.getName().equals("Sheffield Test PR 2"));

    // checks values of parameters of param0 in test pr 1
    assertTrue(pr1rd.getClassName().equals("testpkg.TestPR1"));
    Iterator<List<Parameter>> iter = pr1rd.getParameterList().getRuntimeParameters().iterator();
    Iterator<Parameter> iter2 = null;
    Parameter param = null;
    while(iter.hasNext()) {
      iter2 = iter.next().iterator();
      while(iter2.hasNext()) {
        param = iter2.next();
        if(param.typeName.equals("param0"))
          break;
      }
      if(param.typeName.equals("param0"))
        break;
    }

    assertTrue("param0 was null", param != null);
    assertTrue(param.typeName.equals("java.lang.String"));
    assertTrue(param.optional);
    assertTrue(! param.runtime);
    assertTrue(param.comment == null);
    assertTrue(param.name.equals("thing"));

    reg.clear();
  } // testMetadata()

  /** Test TOOLS and PRIVATE attributes */
  public void testToolsAndPrivate() throws Exception {
    ResourceData pr3rd = reg.get("testpkg.PrintOutTokens");
    assertTrue("couldn't get PR3", pr3rd != null);
    assertTrue("PR3 not a tool", pr3rd.isTool());
    if(DEBUG) Out.prln(pr3rd.getFeatures());

    String docFormatName = "gate.corpora.XmlDocumentFormat";
    ResourceData xmlDocFormatRD = reg.get(docFormatName);
    assertTrue("Xml doc format not PRIVATE", xmlDocFormatRD.isPrivate());
    if(DEBUG) Out.prln(xmlDocFormatRD.getFeatures());
    
    // this used to test the number of public and private LR
    // instances known to the creole register, but this is no
    // longer reliable as extras may (will) be defined by
    // @CreoleResource annotations.

  } // testToolsAndPrivate()

  /** Test resource loading */
  public void testLoading() throws Exception {

    // get some res data from the register
    assertEquals("wrong number of resources in the register: " + reg.size(), 9, reg.size());
    ResourceData pr1rd = reg.get("testpkg.TestPR1");
    ResourceData pr2rd = reg.get("testpkg.TestPR2");
    assertTrue("couldn't find PR1/PR2 res data", pr1rd != null && pr2rd != null);
    assertTrue("wrong name on PR1", pr1rd.getName().equals("Sheffield Test PR 1"));

    // instantiation
    ProcessingResource pr1 = (ProcessingResource)
      Factory.createResource("testpkg.TestPR1", Factory.newFeatureMap());
    ProcessingResource pr2 = (ProcessingResource)
      Factory.createResource("testpkg.TestPR2", Factory.newFeatureMap());

    // run the beasts
    FeatureMap pr1features = pr1.getFeatures();
    FeatureMap pr2features = pr2.getFeatures();
    assertNotNull("PR1 features are null", pr1features);
    assertTrue(
      "PR2 got wrong features: " + pr2features,
      pr2features != null && pr2features.size() != 1
    );
    pr1.execute();
    pr2.execute();
    assertTrue(
      "PR1 feature not present",
      pr1.getFeatures().get("I").equals("have been run, thankyou")
    );
    assertTrue(
      "PR2 feature not present",
      pr2.getFeatures().get("I").equals("am in a bad mood")
    );

    reg.clear();
  } // testLoading()

  /** Test resource indexing by class */
  public void testClassIndex() throws Exception {

    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("couldn't find document res data", docRd);
    assertTrue(
      "doc res data has wrong class name",
      docRd.getClassName().equals("gate.corpora.DocumentImpl")
    );
    assertTrue(
      "doc res data has wrong interface name",
      docRd.getInterfaceName().equals("gate.Document")
    );

    Class<?> docClass = docRd.getResourceClass();
    assertNotNull("couldn't get doc class", docClass);
    LanguageResource docRes = (LanguageResource) docClass.newInstance();
    assertTrue(
      "instance of doc is wrong type",
      docRes instanceof gate.Document
    );

    reg.clear();
  } // testClassIndex()

  /** Test type lists */
  public void testTypeLists() throws Exception {
    Set<String> vrs = reg.getVrTypes();
    Set<String> prs = reg.getPrTypes();
    Set<String> lrs = reg.getLrTypes();

    assertTrue("wrong number vrs in reg: " + vrs.size(), vrs.size() == 2);
    assertTrue("wrong number prs in reg: " + prs.size(), prs.size() == 4);
    assertTrue("wrong number lrs in reg: " + lrs.size(), lrs.size() == 3);
  } // testTypeLists()

  /** Test comments on resources */
  public void testComments() throws Exception {

    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("testComments: couldn't find document res data", docRd);
    String comment = docRd.getComment();
    assertTrue(
      "testComments: incorrect or missing COMMENT on document",
      comment != null && comment.equals("GATE document")
    );
  } // testComments()

  /** Test parameter defaults */
  public void testParameterDefaults1() throws Exception {

    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("Couldn: couldn't find document res data", docRd);
    if(DEBUG) Out.prln(docRd.getParameterList().getInitimeParameters());
    ParameterList paramList = docRd.getParameterList();
    if(DEBUG) Out.prln(docRd);

    // runtime params - none for a document
    Iterator<List<Parameter>> iter = paramList.getRuntimeParameters().iterator();
    assertTrue("Document has runtime params: " + paramList, ! iter.hasNext());

    // init time params
    Parameter param = null;
    iter = paramList.getInitimeParameters().iterator();
    int paramDisjNumber = -1;
    while(iter.hasNext()) {
      List<Parameter> paramDisj = iter.next();
      Iterator<Parameter> iter2 = paramDisj.iterator();
      paramDisjNumber++;

      while(iter2.hasNext()) {
        param = iter2.next();

        switch(paramDisjNumber) {
          case 0:
            assertTrue(
              "Doc param 0 wrong type: " + param.getTypeName(),
              param.getTypeName().equals("java.lang.String")
            );
            assertTrue(
              "Doc param 0 wrong name: " + param.getName(),
              param.getName().equals("sourceUrlName")
            );
            Object defaultValue = param.calculateDefaultValue();
            assertTrue(
              "Doc param 0 default should be null but was: " + defaultValue,
              defaultValue == null
            );
            break;
          case 1:
            assertTrue(
              "Doc param 1 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_ENCODING_PARAMETER_NAME)
            );
            break;
          case 2:
            assertTrue(
              "Doc param 2 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_START_OFFSET_PARAMETER_NAME)
            );
            break;
          case 3:
            assertTrue(
              "Doc param 3 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_END_OFFSET_PARAMETER_NAME)
            );
            break;
          default:
            //fail("Doc has more than 4 params; 5th is: " + param);
            // don't fail if document has more than 4 params - it now pulls in
            // extra ones from the @CreoleParameter annotations
        } // switch
      }
    }

  } // testParameterDefaults1()

  /** Test parameter defaults (2) */
  public void testParameterDefaults2() throws Exception {

    ResourceData rd = reg.get("testpkg.PrintOutTokens");
    assertNotNull("Couldn't find testpkg.POT res data", rd);

    // create a document, so that the parameter default will pick it up
    Factory.newDocument(new URL(TestDocument.getTestServerName()+"tests/doc0.html"));

    ParameterList paramList = rd.getParameterList();
    if(DEBUG) Out.prln(rd);

    // init time params - none for this one
    Iterator<List<Parameter>> iter = paramList.getInitimeParameters().iterator();
    assertTrue("POT has initime params: " + paramList, ! iter.hasNext());

    // runtime params
    Parameter param = null;
    iter = paramList.getRuntimeParameters().iterator();
    int paramDisjNumber = -1;
    while(iter.hasNext()) {
      List<Parameter> paramDisj = iter.next();
      Iterator<Parameter> iter2 = paramDisj.iterator();
      paramDisjNumber++;

      while(iter2.hasNext()) {
        param = iter2.next();

        switch(paramDisjNumber) {
          case 0:
            assertTrue(
              "POT param 0 wrong type: " + param.getTypeName(),
              param.getTypeName().equals("gate.corpora.DocumentImpl")
            );
            assertTrue(
              "POT param 0 wrong name: " + param.getName(),
              param.getName().equals("document")
            );
            Object defaultValue = param.calculateDefaultValue();
            assertNull(
              "POT param 0 default should be null",
              defaultValue
            );
            break;
          default:
            fail("POT has more than 1 param; 2nd is: " + param);
        } // switch
      }
    }

  } // testParameterDefaults2()

  /** Test param as lists*/
  public void testParamAsLists() throws Exception{
    ResourceData rd = reg.get("testpkg.TestPR3");
    assertNotNull("Couldn: couldn't find testPR3 res data", rd);

    ParameterList paramList = rd.getParameterList();
    // runtime params - none for a document
    List<List<Parameter>> runTime = paramList.getRuntimeParameters();
    assertTrue("PR3 should have 4 runtime params: " + paramList, runTime.size()==4);
  }// End testParamAsLists();

  /** Test parameters */
  public void testParameters() throws Exception {

    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("Couldn: couldn't find document res data", docRd);

    ParameterList paramList = docRd.getParameterList();
    if(DEBUG) Out.prln(docRd);

    // runtime params - none for a document
    Iterator<List<Parameter>> iter = paramList.getRuntimeParameters().iterator();
    assertTrue("Document has runtime params: " + paramList, ! iter.hasNext());

    // init time params
    Parameter param = null;
    List<List<Parameter>> initimeParams = paramList.getInitimeParameters();
    // only check the four parameters we can control in tests/creole.xml
    // there are more parameters after the fourth, but these come from
    // @CreoleParameter annotations so we can't reliably control for them
    // in this test
    for(int paramDisjNumber = 0; paramDisjNumber < 4; paramDisjNumber++) {
      List<Parameter> paramDisj = initimeParams.get(paramDisjNumber);
      Iterator<Parameter> iter2 = paramDisj.iterator();

      int paramDisjLen = paramDisj.size();
      assertTrue(
        "param disj wrong length: " + paramDisjLen,
        paramDisjLen == 1
      );

      while(iter2.hasNext()) {
        param = iter2.next();

        switch(paramDisjNumber) {
          case 0:
            assertTrue(
              "Doc param 0 wrong type: " + param.getTypeName(),
              param.getTypeName().equals("java.lang.String")
            );
            assertTrue(
              "Doc param 0 wrong name: " + param.getName(),
              param.getName().equals("sourceUrlName")
            );
            Object defaultValue = param.calculateDefaultValue();
            assertTrue(
              "Doc param 0 default should be null but was: " + defaultValue,
              defaultValue == null
            );
            break;
          case 1:
            assertTrue(
              "Doc param 1 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_ENCODING_PARAMETER_NAME)
            );
            break;
          case 2:
            assertTrue(
              "Doc param 2 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_START_OFFSET_PARAMETER_NAME)
            );
            defaultValue = param.getDefaultValue();
            break;
          case 3:
            assertTrue(
              "Doc param 3 wrong name: " + param.getName(),
              param.getName().equals(Document.DOCUMENT_END_OFFSET_PARAMETER_NAME)
            );
            break;
          default:
            // can't be reached
        } // switch
      }
    }

  } // testParameters()

  /** Test default run() on processing resources */
  public void testDefaultRun() throws Exception {
    @SuppressWarnings("serial")
    ProcessingResource defaultPr = new AbstractProcessingResource() {
    };
    boolean gotExceptionAsExpected = false;
    try {
      defaultPr.execute();
    } catch(ExecutionException e) {
      gotExceptionAsExpected = true;
    }

    assertTrue("check should have thrown exception", gotExceptionAsExpected);
  } // testDefaultRun()

  /** Test arbitrary metadata elements on resources */
  public void testArbitraryMetadata() throws Exception {

    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("testArbitraryMetadata: couldn't find doc res data", docRd);
    FeatureMap features = docRd.getFeatures();
    String comment = (String) features.get("FUNKY-METADATA-THAING");
    assertTrue(
      "testArbitraryMetadata: incorrect FUNKY-METADATA-THAING on document",
      comment != null && comment.equals("hubba hubba")
    );
  } // testArbitraryMetadata()

  /** Test resource introspection */
  @SuppressWarnings("unused")
  public void testIntrospection() throws Exception {
    // get the gate.Document resource and its class
    ResourceData docRd = reg.get("gate.corpora.DocumentImpl");
    assertNotNull("couldn't find document res data (2)", docRd);
    Class<?> resClass = docRd.getResourceClass();

    // get the beaninfo and property descriptors for the resource
    BeanInfo docBeanInfo = Introspector.getBeanInfo(resClass, Object.class);
    PropertyDescriptor[] propDescrs = docBeanInfo.getPropertyDescriptors();

    // print all the properties in the reource's bean info;
    // remember the setFeatures method
    Method setFeaturesMethod = null;
    for(int i = 0; i<propDescrs.length; i++) {
      Method getMethodDescr = null;
      Method setMethodDescr = null;
      Class<?> propClass = null;

      PropertyDescriptor propDescr = propDescrs[i];
      propClass = propDescr.getPropertyType();
      getMethodDescr = propDescr.getReadMethod();
      setMethodDescr = propDescr.getWriteMethod();

      if(
        setMethodDescr != null &&
        setMethodDescr.getName().equals("setFeatures")
      )
        setFeaturesMethod = setMethodDescr;

      if(DEBUG) printProperty(propDescrs[i]);
    }

    // try setting the features property
    // invoke(Object obj, Object[] args)
    LanguageResource res = (LanguageResource) resClass.newInstance();
    FeatureMap feats = Factory.newFeatureMap();
    feats.put("things are sunny in sunny countries", "aren't they?");
    Object[] args = new Object[1];
    args[0] = feats;
    setFeaturesMethod.invoke(res, args);
    assertTrue(
      "features not added to resource properly",
      res.getFeatures().get("things are sunny in sunny countries")
        .equals("aren't they?")
    );
  } // testIntrospection

  /** Test the Factory resource creation provisions */
  public void testFactory() throws Exception {
    FeatureMap params = Factory.newFeatureMap();
    params.put("features", Factory.newFeatureMap());
    params.put(Document.DOCUMENT_URL_PARAMETER_NAME, new URL(TestDocument.getTestServerName()+"tests/doc0.html"));
    Resource res =
      Factory.createResource("gate.corpora.DocumentImpl", params);
    
    assertNotNull("unable to create document", res);
  } // testFactory

  /** Utility method to print out the values of a property descriptor
    * @see java.beans.PropertyDescriptor
    */
  public static void printProperty(PropertyDescriptor prop) {
    Class<?> propClass = prop.getPropertyType();
    Method getMethodDescr = prop.getReadMethod();
    Method setMethodDescr = prop.getWriteMethod();
    Out.pr("prop dispname= " + prop.getDisplayName() + "; ");
    Out.pr("prop type name= " + propClass.getName() + "; ");
    if(getMethodDescr != null)
      Out.pr("get meth name= " + getMethodDescr.getName() + "; ");
    if(setMethodDescr != null)
      Out.pr("set meth name= " + setMethodDescr.getName() + "; ");
    Out.prln();
  } // printProperty

  /** Example of what bean info classes do.
    * If this was a public class in gate.corpora it would be used
    * by the beans Introspector to generation bean info for the
    * gate.corpora.DocumentImpl class. It inherits from SimpleBeanInfo
    * whose default behaviour is to return null for the various methods;
    * this tells the Introspector to do its own investigations.
    */
  class DocumentImplBeanInfo extends SimpleBeanInfo {

    /** Override the SimpleBeanInfo behaviour and return a 0-length
      * array of properties; this will be passed on by the Introspector,
      * the effect being to block info on the properties of the bean.
      */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
      return new PropertyDescriptor[0];
    } // getPropertyDescriptors

  } // DocumentImplBeanInfo

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestCreole.class);
  } // suite

} // class TestCreole
