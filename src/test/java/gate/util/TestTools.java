package gate.util;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;

/**
 * Test cases for {@link Tools#getMostSpecificConstructor}.
 */
public class TestTools extends TestCase {

  // A small hierarchy of interfaces and classes to test
  // getMostSpecificConstructor
  private static interface InterfaceA {
  }

  private static interface InterfaceB {
  }

  private static interface SubInterfaceB extends InterfaceB {
  }

  @SuppressWarnings("unused")
  private static class ClassA implements InterfaceA {
  }

  private static class ClassB implements InterfaceB {
  }

  private static class ClassSubB implements SubInterfaceB {
  }

  private static class ClassAB implements InterfaceA, InterfaceB {
  }

  private static class SubClassOfAB extends ClassAB {
  }

  private static class DifferentClassAB implements InterfaceA, InterfaceB {
  }

  @SuppressWarnings("unused")
  private static class ConstructorTest {
    
    public ConstructorTest(InterfaceA a) {
      // applicable to InterfaceA, ClassA, ClassAB, SubClassOfAB
      // and DifferentClassAB
    }

    public ConstructorTest(InterfaceB b) {
      // applicable to InterfaceB, ClassB, SubInterfaceB, ClassSubB,
      // ClassAB, SubClassOfAB and DifferentClassAB
    }

    public ConstructorTest(SubInterfaceB sb) {
      // applicable to SubInterfaceB and ClassSubB only
    }

    public ConstructorTest(ClassAB ab) {
      // applicable to ClassAB and SubClassOfAB only
    }
  }

  public void testGetMostSpecificConstructor1() throws Exception {
    // simple case - there is only one constructor of
    // ConstructorTest that is applicable to an argument of type ClassB
    // - the one taking an InterfaceB
    Constructor<?> expected =
            ConstructorTest.class.getConstructor(InterfaceB.class);

    Constructor<?> result =
            Tools.getMostSpecificConstructor(ConstructorTest.class,
                    ClassB.class);
    assertEquals("Most specific constructor for ConstructorTest taking a "
            + "ClassB should be ConstructorTest(InterfaceB)", expected, result);
  }

  public void testGetMostSpecificConstructor2() throws Exception {
    // more complex case - there are two applicable constructors taking
    // a ClassSubB - InterfaceB and SubInterfaceB - but the latter is
    // more specific than the former
    Constructor<?> expected =
            ConstructorTest.class.getConstructor(SubInterfaceB.class);

    Constructor<?> result =
            Tools.getMostSpecificConstructor(ConstructorTest.class,
                    ClassSubB.class);
    assertEquals("Most specific constructor for ConstructorTest taking a "
            + "ClassSubB should be ConstructorTest(SubInterfaceB)", expected,
            result);
  }

  public void testGetMostSpecificConstructor3() throws Exception {
    // more complex again - there are three applicable constructors
    // taking a SubClassOfAB - InterfaceA, InterfaceB and ClassAB - but
    // the last is more specific than both the others, so should
    // be chosen
    Constructor<?> expected = ConstructorTest.class.getConstructor(ClassAB.class);

    Constructor<?> result =
            Tools.getMostSpecificConstructor(ConstructorTest.class,
                    SubClassOfAB.class);
    assertEquals("Most specific constructor for ConstructorTest taking a "
            + "SubClassOfAB should be ConstructorTest(ClassAB)", expected,
            result);
  }

  public void testGetMostSpecificConstructor4() throws Exception {
    // ambiguous case - there are two applicable constructors for
    // a DifferentClassAB - InterfaceA and InterfaceB - and neither
    // is more specific than the other. We expect an "ambiguous"
    // exception

    try {
      Tools.getMostSpecificConstructor(ConstructorTest.class,
                      DifferentClassAB.class);
    }
    catch(NoSuchMethodException e) {
      assertTrue("Expected \"ambiguous\" exception", e.getMessage().startsWith(
              "Ambiguous"));
      return;
    }

    fail("getMostSpecificConstructor(ConstructorTest, "
            + "DifferentClassAB) "
            + "should have thrown an exception");
  }
}
