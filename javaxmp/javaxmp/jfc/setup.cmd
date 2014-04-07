@echo off
rem -----------------------------------------------
rem Adjust the classpath to find the Swing classes.
rem
rem OS/2 Version.
rem
rem You will need to execute this command file
rem before you can run the JFC examples. Set the
rem SWINGHOME variable to point to wherever you
rem have installed the JFC class (jar) files.
rem -----------------------------------------------

set SWINGHOME=d:\java\jfc\swing-0.5.1
set classpath=%SWINGHOME%\swing.jar;%SWINGHOME%\rose.jar;%CLASSPATH%


