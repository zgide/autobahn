#!/bin/sh
FILE_PATH=`dirname $0`

LOG=$FILE_PATH/testReservation.log
ERR=$FILE_PATH/testReservation.err
CP=$FILE_PATH

APACHE="org.apache.cxf.common.logging.Log4jLogger"
CLASS="net.geant.autobahn.useraccesspoint.testReservationPossibility"


echo "">>$LOG
echo "">>$ERR

date >>$LOG
date >>$ERR

for i in `ls $FILE_PATH/lib/*.jar`; do CP=$CP:$i; done
java -Dorg.apache.cxf.Logger=$APACHE -classpath $CP $CLASS 1>>$LOG 2>>$ERR

echo "">>$LOG
echo "">>$ERR