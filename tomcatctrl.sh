#!/usr/bin/bash

TomcatRoot=./apache-tomcat-9.0.39
TomcatRoot=$1
ctrlOption=$2

if [ "$ctrlOption" = "" ];
then
    echo -e "\033[0;31m please input 2nd arg:option \033[0m  \033[0;34m {start|stop|restart|war2|clean|conf|log} \033[0m"
    exit 1
fi

if [ "$TomcatRoot" = "" ];
then
    echo -e "\033[0;31m please input 1st arg:tomcatRoot \033[0m"
    exit 1
fi

function stop() {
    echo '$TomcatRoot is stopping ...'
    sh $TomcatRoot/bin/shutdown.sh
    echo '$TomcatRoot stopped.'
}

function start(){
    echo '$TomcatRoot is start ...'
  sh $TomcatRoot/bin/startup.sh
  echo '$TomcatRoot is started.'
}

function restart() {
    stopTomcat
    sleep 2
    startTomcat
}

function war2() {
  mv *.war $TomcatRoot/webapps/
  echo 'war(s) was moved into $TomcatRoot/webapps/'
}

function clean() {
  rm -rf $TomcatRoot/work/*
  rm -rf $TomcatRoot/temp/*
  echo '$TomcatRoot was cleaned.'
}

function conf() {
  vi $TomcatRoot/conf/server.xml
}

function log() {
  tail -f $TomcatRoot/logs/catalina.out
}

case $ctrlOption in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    war2)
    war2;;
    clean)
    clean;;
    conf)
    conf;;
      log)
    log;;
    *)

esac
