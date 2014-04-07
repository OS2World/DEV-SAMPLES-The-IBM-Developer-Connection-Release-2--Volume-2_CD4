@echo off
rem -----------------------------------------------
rem Adjust the classpath to find the Swing classes.
rem
rem OS/2 version.
rem
rem You will need to execute this command file
rem before you can run the JFC examples. Set the
rem SWINGHOME variable to point to wherever you
rem have installed the JFC class (jar) files.
rem
rem -----------------------------------------------

set SWING_HOME=d:\java\jfc\swing-0.6.1
set classpath=%classpath%;%SWING_HOME%\swing.jar;%SWING_HOME%\motif.jar;%SWING_HOME%\jlf.jar

