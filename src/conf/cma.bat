@echo off
rem ---------------------------------------------------------------------------
rem cma.bat - Start/Stop Script for the CMA log component
rem
rem Environment Variable Prequisites:
rem
rem   CMA_HOME (Optional) May point at your cma "build" directory.
rem                 If not present, the current working directory is assumed.
rem
rem   JAVA_HOME     Must point at your Java Development Kit installation.
rem
rem $Id: cma.bat,v 1.3 2006/01/31 19:49:59 beka Exp $
rem ---------------------------------------------------------------------------


rem ----- Save Environment Variables That May Change --------------------------

set _CMA_HOME=%CMA_HOME%
set _CLASSPATH=%CLASSPATH%
set _CP=%CP%

rem ----- Verify and Set Required Environment Variables -----------------------

if not "%JAVA_HOME%" == "" goto gotJava
echo You must set JAVA_HOME to point at your Java Development Kit installation
pause
goto cleanup
:gotJava

if not "%CMA_HOME%" == "" goto gotHome

set CMA_HOME=.
if exist "%CMA_HOME%\cma-@version@.jar" goto okHome

set CMA_HOME=..
:gotHome

if exist "%CMA_HOME%\cma-@version@.jar" goto okHome
echo Cannot find cma-@version@.jar in %CMA_HOME%
echo Please check your CMA_HOME setting
goto cleanup
:okHome

rem ----- Prepare Appropriate Java Execution Commands -------------------------
set _RUNJAVA="%JAVA_HOME%\bin\java"

rem ----- Set Up The Runtime Classpath ----------------------------------------

set CP=%CMA_HOME%\cma-@version@.jar
set CP=%CP%;%CMA_HOME%\lib\jdom-1.0.jar
set CP=%CP%;%CMA_HOME%\lib\castor-0.9.5.jar
set CP=%CP%;%CMA_HOME%\lib\jaxen-1.0-FCS-full.jar
set CP=%CP%;%CMA_HOME%\lib\jaxp-1.2.jar
set CP=%CP%;%CMA_HOME%\lib\oculus-1.0.jar
set CP=%CP%;%CMA_HOME%\lib\saxpath-1.0-FCS.jar
set CP=%CP%;%CMA_HOME%\lib\xalan-2.4.1.jar
set CP=%CP%;%CMA_HOME%\lib\xerces-2.4.0.jar
set CP=%CP%;%CMA_HOME%\lib\xml-apis-2.0.2.jar

set APP_TYPE=default
if "%1" == "stafett" set APP_TYPE=stafett
if "%1" == "jaktstart" set APP_TYPE=jaktstart


echo Using CMA_HOME: 	%CMA_HOME%
echo Using JAVA_HOME:	%JAVA_HOME%
echo Using CLASSPATH:   %CP%
echo Using APP_TYPE:    %APP_TYPE%

set CONF_FILE=/cma.xml
if "%APP_TYPE%" == "stafett" set CONF_FILE=/cma-relay.xml
if "%APP_TYPE%" == "jaktstart" set CONF_FILE=/cma-hunt.xml

rem ----- Execute The Requested Command ---------------------------------------

%_RUNJAVA% -cp %CP% -Dapp.version=@version@ -Dcma.properties.file=%CONF_FILE% cma.ApplicationController
goto cleanup

rem ----- Restore Environment Variables ---------------------------------------

:cleanup
set CMA_HOME=%_CMA_HOME%
set _CMA_HOME=
set CLASSPATH=%_CLASSPATH%
set _CLASSPATH=
set CP=%_CP%
set _LIBJARS=
set _RUNJAVA=
set _STARTJAVA=
:finish
