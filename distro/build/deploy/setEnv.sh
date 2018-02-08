#!/bin/sh
# gate.sh - UNIX front-end for GATE binary distributions. Hamish, May 2001
# $Id: setEnv.sh 7452 2006-06-15 14:45:17Z ian_roberts $

GATE_HOME=$1
JAVA_HOME=$2
HELPER=gate.sh.tmp

cd $GATE_HOME/bin
head -4 gate.sh >$HELPER
echo "GATE_HOME=$1; export GATE_HOME" >> $HELPER
echo "JAVA_HOME=$2; export JAVA_HOME" >> $HELPER
echo '[ ! -f $JAVA_HOME/lib/tools.jar ] && export CLASSPATH=$CLASSPATH:$GATE_HOME/bin/tools14.jar' >>$HELPER
sed -n '5,$p' gate.sh >>$HELPER
mv $HELPER gate.sh