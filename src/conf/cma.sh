#!/bin/sh
# ---------------------------------------------------------------------------
# cma.bat - Start/Stop Script for the CMA log component
#
# Environment Variable Prequisites:
#
#   CMA_HOME (Optional) May point at your cma "build" directory.
#                 If not present, the current working directory is assumed.
#
#   JAVA_HOME     Must point at your Java Development Kit installation.
#
# $Id: cma.sh,v 1.3 2006/01/31 19:49:59 beka Exp $
# ---------------------------------------------------------------------------

JAVA_OPTS=

if [ -z "$JAVA_HOME" ] ; then
    echo "JAVA_HOME must be set!"
fi

if [ -z "$CMA_HOME" ] ; then
    CMA_HOME=.
fi

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
esac

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CMA_HOME" ] && CMA_HOME=`cygpath --unix "$CMA_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# Add on extra jar files to CLASSPATH
CLASSPATH="$CLASSPATH":"$CMA_HOME"/cma-@version@.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/jdom-1.0.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/castor-0.9.5.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/jaxen-1.0-FCS-full.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/jaxp-1.2.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/oculus-1.0.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/saxpath-1.0-FCS.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/xalan-2.4.1.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/xerces-2.4.0.jar
CLASSPATH="$CLASSPATH":"$CMA_HOME"/lib/xml-apis-2.0.2.jar

APP_TYPE=default
# OBS! This is not the correct way to make a comparison in 'sh'
if "$1" == "stafett" APP_TYPE=stafett
if "$1" == "jaktstart" APP_TYPE=jaktstart

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  CMA_HOME=`cygpath --path --windows "$CMA_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

echo "Using CMA_HOME: $CMA_HOME"
echo "Using CLASSPATH:     $CLASSPATH"
echo "Using JAVA_HOME:     $JAVA_HOME"
echo "Using APP_TYPE:      $APP_TYPE"

CONF_FILE=/cma.xml
# OBS! This is not the correct way to make a comparison in 'sh'
if "$APP_TYPE" == "stafett" CONF_FILE=/cma-relay.xml
if "$APP_TYPE" == "jaktstart" CONF_FILE=/cma-hunt.xml

"$JAVA_HOME/bin/java" $JAVA_OPTS \
-Djava.endorsed.dirs="$JAVA_ENDORSED_DIRS" -classpath "$CLASSPATH" -Dapp.version="@version@" \
-Dcma.properties.file="$CONF_FILE" cma.ApplicationController
