# GATE Developer (including GATE Embedded)

[![Javadocs](https://javadoc.io/badge/uk.ac.gate/gate-core.svg?color=brightgreen&label=JavaDoc)](https://javadoc.io/doc/uk.ac.gate/gate-core)

Note that this is out current development code and there are significant differences to our current stable release, although this code will form the basis of our next release. If you are looking for the source code for the 8.4.x stable release please see https://sourceforge.net/p/gate/code/HEAD/tree/gate/trunk/

See the [user guide](http://gate.ac.uk/userguide) for more details

# Building and Running

To build the development version of gate-core, clone this repository, then run `mvn install` in the top-level directory.  To run the GATE Developer GUI, you also need to run `mvn compile` in the `distro` directory, after which you can run the GUI from the `distro` directory as appropriate to your platform (`bin/gate.sh` on Linux, open `GATE.app` on Mac, or run `gate.exe` on Windows).

Plugins are no longer part of this `gate-core` repository - by default GATE will download its plugins from a Maven repository at runtime (the Central Repository for release versions of GATE, the [GATE Maven repository](https://repo.gate.ac.uk) for snapshots) so it is not necessary to build the plugins locally in order to use them. Each plugin is in its own repository on GitHub (search https://github.com/GateNLP for "gateplugin"), to make changes to a plugin simply clone its repository, make your changes, then `mvn install` - GATE will prefer your locally built version over one from a remote repository with the same (SNAPSHOT) version number.  However, you may need to configure the GATE repository in your `~/.m2/settings.xml` first:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <profiles>
    <profile>
      <id>gate-plugin-repo</id>
      <pluginRepositories>
        <pluginRepository>
            <id>gate-repo-plugins</id>
            <name>GATE Repo</name>
            <url>http://repo.gate.ac.uk/content/groups/public/</url>
            <layout>default</layout>
            <releases><enabled>true</enabled></releases>
            <snapshots><enabled>true</enabled></snapshots>
          </pluginRepository>
      </pluginRepositories>
      <repositories>
        <repository>
            <id>gate-repo</id>
            <name>GATE Repo</name>
            <url>http://repo.gate.ac.uk/content/groups/public/</url>
            <layout>default</layout>
            <releases><enabled>true</enabled></releases>
            <snapshots><enabled>true</enabled></snapshots>
          </repository>
      </repositories>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>gate-plugin-repo</activeProfile>
  </activeProfiles>
</settings>
```
