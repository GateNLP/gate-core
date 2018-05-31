# GATE Developer (including GATE Embedded)

[![Javadocs](https://javadoc.io/badge/uk.ac.gate/gate-core.svg?color=brightgreen&label=JavaDoc)](https://javadoc.io/doc/uk.ac.gate/gate-core)

Note that this is our current development code, if you are looking for stable releases please visit https://gate.ac.uk/download/

See the [user guide](http://gate.ac.uk/userguide) for more details

## Building and Running from the Source Code

Requirements:
* Java 8 or above
* Maven 3.5 or above

To build the development version of gate-core:
* clone this repository: `git clone https://github.com/GateNLP/gate-core.git` 
* change into the directory that has been created
* compile the library and install into your local Maven cache: `mvn install`
* prepare the files needed to run the GATE Developer GUI: change into the `./distro` directory (`cd distro`) then run `mvn compile`

After this you can run the GUI from the `distro` directory as appropriate to your platform (`bin/gate.sh` on Linux, open `GATE.app` on Mac, or run `gate.exe` on Windows). When you update (`git pull`), it's the same procedure.

## Using Plugins

Plugins are no longer part of this `gate-core` repository - by default GATE will download its plugins from a Maven repository at runtime (the Central Repository for release versions of GATE, the [GATE Maven repository](https://repo.gate.ac.uk) for snapshots) so it is not necessary to build the plugins locally in order to use them. 

Each plugin by the GATE team is in its own repository on GitHub (search https://github.com/GateNLP for "gateplugin"), to make changes to a plugin simply clone its repository, make your changes, then `mvn install` - GATE will prefer your locally built version over one from a remote repository with the same (SNAPSHOT) version number.

## Setting up a classpath for a script or program

As GATE is distributed via Maven in most cases depending on the relevant release of GATE within your own Maven project should suffice.
If, however, you need to depend, at compile time, on a SNAPSHOT version of GATE, or a plugin from the GATE team which has not yet
been released to Maven central (either a SNAPSHOT or a release that can't be pushed to Central) then you will need to
add the following to the `pom.xml` of your project.

```xml
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
```

If you are not using Maven or need to provide the full classpath to a command line script then
the `./distro` directory inside the cloned source code repository contains a file 
`gate.classpath` after running `mvn compile` which contains the paths to all the JARs in the Maven cache 
that should be put on the classpath when using gate-core from a Java application that embeds GATE. 

If GATE was installed using a pre-built distribution, then the distribution directory contains a `./lib`
directory with all the JAR files that should be included in the class path.
