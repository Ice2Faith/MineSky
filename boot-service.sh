#!/bin/bash

JVM_OPTS_MAIN=" -Duser.timezone=Asia/Shanghai -Xms768M -Xmx768M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
JVM_OPTS_OTHER=" -Duser.timezone=Asia/Shanghai -Xms256M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"

pid=
appName=
appLog=
JVM_OPTS=

function stopJar()
{
  pid=`ps -ef | grep java | grep $appName | grep -v grep | awk '{print $2}'`
  kill -9 $pid
}

function startJar()
{
  nohup java -jar $JVM_OPTS $appName > ./logs/$appLog &
}

function restartJar()
{
  stopJar
  sleep 3
  startJar
}

echo begin run MineSky project...
echo run eureka registry center ...
JVM_OPTS=$JVM_OPTS_OTHER
appName=i2f-eureka-server.jar
appLog=eureka-server.log
restartJar;

echo run config management center ...
JVM_OPTS=$JVM_OPTS_OTHER
appName=i2f-config-center.jar
appLog=config-center.log
restartJar;

echo run zuul gatway ...
JVM_OPTS=$JVM_OPTS_OTHER
appName=i2f-zuul.jar
appLog=zuul.log
restartJar;

echo run admin service ...
JVM_OPTS=$JVM_OPTS_MAIN
appName=i2f-admin.jar
appLog=admin.log
restartJar;

echo run app service ...
JVM_OPTS=$JVM_OPTS_MAIN
appName=i2f-app.jar
appLog=app.log
restartJar;

echo MineSky project is boot done.
