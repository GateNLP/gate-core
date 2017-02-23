/*
 *  LuckyException.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 06/2000
 *
 *  $Id: LuckyException.java 17600 2014-03-08 18:47:11Z markagreenwood $
 */
package gate.util;

/**
 * This exception is intended to be used in places where there definitely
 * shouldn't be any exceptions thrown but the API requires us to catch some, eg:
 * <code>
 * try{
 *   if( a != null){
 *     a.doSomething();
 *   }
 * }catch(NullPointerException npe){
 *   throw new LuckyException("I found a null pointer!");
 * }
 *</code> Of course the system will never require you to catch
 * NullPOinterException as it derives from RuntimeException, but I couldn't come
 * with a better example.
 * 
 * @deprecated serves no purpose over and above either
 *             {@link gate.util.GateRuntimeException GateRuntimeException} or
 *             just plain {@link java.lang.RuntimeException RuntimeException}
 *             and one of those should be used instead.
 */
@Deprecated
public class LuckyException extends RuntimeException {

	private static final long serialVersionUID = -8051339390451934567L;

	/** Default constructor, creates a new execption with the default message */
	public LuckyException() {
		super(defaultMessage);
	}

	/**
	 * Creates a new exception with the provided message prepended to the
	 * default one on a separate line.
	 * 
	 * @param message
	 *            the uses message
	 */
	public LuckyException(String message) {
		super(message + "\n" + defaultMessage);
	}

	public LuckyException(String message, Throwable cause) {
		super(message + "\n" + defaultMessage, cause);
	}

	public LuckyException(Throwable cause) {
		super(defaultMessage, cause);
	}

	/** The default message carried by this type of exceptions */
	static String defaultMessage = "Congratulations, you found the ONLY bug in GATE!";

}// end class LuckyException
