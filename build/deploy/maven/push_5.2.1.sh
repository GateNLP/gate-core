#!/bin/sh

/opt/apache-maven-2.2.1/bin/mvn -f sign-and-deploy.pom.xml -Pgate_release,publish-generic -Dfile=gate-5.2.1 -Dpom=gate-core.5.2.1.pom.xml
