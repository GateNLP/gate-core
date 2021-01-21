package gate.util.persistence;

import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
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
		private static final Pattern JAXWS_FILE_STREAM = Pattern.compile(".*\\.ReadAllStream\\$FileStream");

		@Override
		public void configure(XStream xstream) {
			xstream.addPermission(AnyTypePermission.ANY);
			xstream.denyTypes(new String[] {
					"java.beans.EventHandler",
					"java.lang.ProcessBuilder",
					"javax.imageio.ImageIO$ContainsFilter",
					"jdk.nashorn.internal.objects.NativeString" });
			xstream.denyTypesByRegExp(new Pattern[] { LAZY_ITERATORS, JAVAX_CRYPTO, JAXWS_FILE_STREAM });
			xstream.allowTypeHierarchy(Exception.class);

		}
	}
}
