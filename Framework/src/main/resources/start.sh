#!/bin/sh
java -Dcxf.config.file=etc/cxf/cxf.xml -Dorg.apache.cxf.Logger=org.apache.cxf.common.logging.Log4jLogger -classpath .:lib/* net.geant.autobahn.framework.Framework start