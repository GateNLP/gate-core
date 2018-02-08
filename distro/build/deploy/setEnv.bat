@echo off
REM ##############################################
REM $Id: setEnv.bat 7452 2006-06-15 14:45:17Z ian_roberts $
REM ##############################################

cd  /d %1\bin
echo set GATE_HOME=%1> tempEnv.txt
echo set JAVA_HOME=%2>> tempEnv.txt

move gate.bat gate1.bat
copy tempEnv.txt+gate1.bat gate.bat
del gate1.bat
del tempEnv.txt