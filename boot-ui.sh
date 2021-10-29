pid=`ps -ef | grep java | grep Tomcat9-admin | grep -v grep | awk '{print $2}'`
kill -9 $pid
pid=`ps -ef | grep java | grep Tomcat9-app | grep -v grep | awk '{print $2}'`
kill -9 $pid
sh ./Tomcat9-admin/apache-tomcat-9.0.44/bin/startup.sh
sh ./Tomcat9-app/apache-tomcat-9.0.44/bin/startup.sh
