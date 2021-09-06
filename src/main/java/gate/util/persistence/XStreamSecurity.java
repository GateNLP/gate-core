package gate.util.persistence;

import java.io.InputStream;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.security.AnyTypePermission;

public interface XStreamSecurity {

	public void configure(XStream xstream);

	/**
	 * An instance of the XStreamSecurity interface that provides minimal
	 * security by using the same blacklist as XStream 1.4.15. The only
	 * reason to use this class instead of using the default behaviour
	 * of XStream 1.4.15 is to suppress the scary warning that otherwise
	 * appears on the message pane.
	 * 
	 * This essentially lets GATE work as it always has and allows any
	 * types to be used as feature values within documents etc. 
	 * 
	 * Note that this provides minimal security via blacklisting. If you
	 * are using GATE in a embedded environment then you probably want to
	 * restrict the classes further to just those you know you need.	 *
	 */
	static final class MinimalBlacklist implements XStreamSecurity {

		private static final Pattern LAZY_ITERATORS = Pattern.compile(".*\\$LazyIterator");
		private static final Pattern JAVAX_CRYPTO = Pattern.compile("javax\\.crypto\\..*");
	    private static final Pattern LAZY_ENUMERATORS = Pattern.compile(".*\\.Lazy(?:Search)?Enumeration.*");
	    private static final Pattern GETTER_SETTER_REFLECTION = Pattern.compile(".*\\$GetterSetterReflection");
	    private static final Pattern PRIVILEGED_GETTER = Pattern.compile(".*\\$PrivilegedGetter");
	    private static final Pattern JAVA_RMI = Pattern.compile("(?:java|sun)\\.rmi\\..*");
	    private static final Pattern JAXWS_ITERATORS = Pattern.compile(".*\\$ServiceNameIterator");
	    private static final Pattern JAVAFX_OBSERVABLE_LIST__ = Pattern.compile("javafx\\.collections\\.ObservableList\\$.*");
	    private static final Pattern BCEL_CL = Pattern.compile(".*\\.bcel\\..*\\.util\\.ClassLoader");

		@Override
		public void configure(XStream xstream) {
			xstream.addPermission(AnyTypePermission.ANY);
			xstream.denyTypes(new String[]{
	            "java.beans.EventHandler", //
	            "java.lang.ProcessBuilder", //
	            "javax.imageio.ImageIO$ContainsFilter", //
	            "jdk.nashorn.internal.objects.NativeString", //
	            "com.sun.corba.se.impl.activation.ServerTableEntry", //
	            "com.sun.tools.javac.processing.JavacProcessingEnvironment$NameProcessIterator", //
	            "sun.awt.datatransfer.DataTransferer$IndexOrderComparator", //
	            "sun.swing.SwingLazyValue"});
			xstream.denyTypesByRegExp(new Pattern[]{
	            LAZY_ITERATORS, LAZY_ENUMERATORS, GETTER_SETTER_REFLECTION, PRIVILEGED_GETTER, JAVA_RMI, JAVAX_CRYPTO,
	            JAXWS_ITERATORS, JAVAFX_OBSERVABLE_LIST__, BCEL_CL});
			xstream.denyTypeHierarchy(InputStream.class);
			denyTypeHierarchyDynamically(xstream, "java.nio.channels.Channel");
			denyTypeHierarchyDynamically(xstream, "javax.activation.DataSource");
			denyTypeHierarchyDynamically(xstream, "javax.sql.rowset.BaseRowSet");
			xstream.allowTypeHierarchy(Exception.class);
		}

		private void denyTypeHierarchyDynamically(XStream xstream, String className) {
	        Class<?> type = JVM.loadClassForName(className);
	        if (type != null) {
	            xstream.denyTypeHierarchy(type);
	        }
	    }
	}
}
