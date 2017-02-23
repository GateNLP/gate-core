/*
 *  GateAwareObjectInputStream.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Ian Roberts, 04/Apr/2007
 *
 *  $Id: GateAwareObjectInputStream.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */

package gate.persist;

import java.io.*;
import java.lang.reflect.Proxy;

import gate.Gate;

/**
 * An ObjectInputStream that attempts to resolve the classes of objects
 * loaded from the stream via the GateClassLoader if they cannot be
 * found by the usual means. This allows LRs to be loaded successfully
 * from a serial datastore when they have features (or annotations with
 * features) whose class is defined in a plugin.
 */
public class GateAwareObjectInputStream extends ObjectInputStream {
  /**
   * Creates a GATE aware object input stream to read from the given
   * source.
   */
  public GateAwareObjectInputStream(InputStream source) throws IOException {
    super(source);
  }

  /**
   * Resolve the class of an object read from the stream. First attempts
   * to use the default resolution method provided by ObjectInputStream.
   * If that fails with a ClassNotFoundException then tries to resolve
   * the class via the GATE classloader instead.
   */
  @Override
  protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
          ClassNotFoundException {
    try {
      return super.resolveClass(desc);
    }
    catch(ClassNotFoundException cnfe) {
      // try the GATE classloader instead
      return Class.forName(desc.getName(), false, Gate.getClassLoader());
    }
  }

  /**
   * Resolve a proxy class that implements the given interfaces. First
   * attempts to use the default resolution method provided by
   * ObjectInputStream. If that fails with a ClassNotFoundException then
   * tries to resolve the interfaces and create the proxy class using
   * the GATE classloader instead.
   */
  @Override
  protected Class<?> resolveProxyClass(String[] interfaces) throws IOException,
          ClassNotFoundException {
    try {
      return super.resolveProxyClass(interfaces);
    }
    catch(ClassNotFoundException cnfe) {
      // failed to load with the normal method, try the GATE ClassLoader
      // instead
      Class<?>[] interfaceClasses = new Class<?>[interfaces.length];
      for(int i = 0; i < interfaces.length; i++) {
        interfaceClasses[i] = Class.forName(interfaces[i], false, Gate
                .getClassLoader());
      }
      return Proxy.getProxyClass(Gate.getClassLoader(), interfaceClasses);
    }
  }
}
