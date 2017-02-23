/*
 * VersionComparator.java
 * 
 * Copyright (c) 2011, The University of Sheffield. See the file COPYRIGHT.txt
 * in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 * 
 * This file is part of GATE (see http://gate.ac.uk/), and is free software,
 * licenced under the GNU Library General Public License, Version 2, June 1991
 * (in the distribution as file licence.html, and also available at
 * http://gate.ac.uk/gate/licence.html).
 * 
 * Mark A. Greenwood, 26/11/2011
 */

package gate.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gate.Main;

/**
 * A comparator that can be used for comparing GATE version strings. This
 * includes comparing SNAPSHOT versions as well as releases. This is needed to
 * be able to determine if a CREOLE plugin is compatible with a specific GATE
 * version.
 * 
 * @author Mark A. Greenwood
 */
public class VersionComparator implements Comparator<String>, Serializable {

  private static final long serialVersionUID = 6108998766086857918L;

  // this pattern matches any dot separated sequence of digits
  private static final Pattern VERSION_PATTERN = Pattern
          .compile("(\\d+)(?:\\.(\\d+))*");

  @Override
  public int compare(String v1, String v2) {
    return compareVersions(v1, v2);
  }

  public static int compareVersions(String v1, String v2) {

    // if the two strings are identical then they must represent the same
    // version so just return quickly
    if(v1.equalsIgnoreCase(v2)) return 0;

    // use the regex to find the digit groupings
    Matcher m1 = VERSION_PATTERN.matcher(v1);
    Matcher m2 = VERSION_PATTERN.matcher(v2);

    if(!m1.find() || !m2.find()) {
      // these don't represent versions so sort as strings
      return v1.compareTo(v2);
    }

    // get the maximum number of digit groups
    int groups = Math.max(m1.groupCount(), m2.groupCount());

    for(int i = 1; i <= groups; i++) {
      // for each digit group get the string and convert it to an int
      // if the version string doesn't have this group then set it to 0
      int g1 = (m1.group(i) != null ? Integer.parseInt(m1.group(i)) : 0);
      int g2 = (m2.group(i) != null ? Integer.parseInt(m2.group(i)) : 0);

      // now compare the numeric value of the group and return as appropriate
      if(g1 < g2) return -1;
      if(g1 > g2) return 1;
    }

    // we have checked all the numbers and the versions are equal
    // so let's check if either is a snapshot (snapshots are considered lower
    // than non-snapshot versions with the same number)
    boolean v1IsSnapshot = v1.toUpperCase().endsWith("-SNAPSHOT");
    boolean v2IsSnapshot = v2.toUpperCase().endsWith("-SNAPSHOT");

    if(v1IsSnapshot && !v2IsSnapshot) return -1;
    if(!v1IsSnapshot && v2IsSnapshot) return 1;

    // if we get all the way to here then the two strings represent the same
    // version
    return 0;
  }

  /**
   * Returns true if the specified version is the same or newer than the version
   * of GATE being used.
   * 
   * @param version
   *          the version number to check
   * @return true if the specified version is the same or newer than the version
   *         of GATE being used, false otherwise
   */
  public static boolean isGATENewEnough(String version) {
    if(version == null) return true;

    version = version.trim();

    if(version.equals("") || version.equals("*")) return true;

    return (compareVersions(Main.version, version) >= 0);
  }

  /**
   * Returns true if the specified version is the same or older than the version
   * of GATE being used.
   * 
   * @param version
   *          the version number to check
   * @return true if the specified version is the same or older than the version
   *         of GATE being used, false otherwise
   */
  public static boolean isGATEOldEnough(String version) {
    if(version == null) return true;

    version = version.trim();

    if(version.equals("") || version.equals("*")) return true;

    return (compareVersions(Main.version, version) <= 0);
  }

  /**
   * Checks to see if the version of GATE falls between the two specified
   * versions (this is an inclusive check).
   * 
   * @param min
   *          the minimum compatible GATE version
   * @param max
   *          the maximum compatible GATE version
   * @return if the version of GATE falls between the two specified versions
   *         (this is an inclusive check), false otherwise
   */
  public static boolean isCompatible(String min, String max) {
    return isGATENewEnough(min) && isGATEOldEnough(max);
  }
}