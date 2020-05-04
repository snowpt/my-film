kill -9 $(netstat -nlp | grep :8090 | awk '{print $7}' | awk -F"/" '{ print $1 }');
nohup java -jar /home/springboot/gj-movie/web-0.0.1-SNAPSHOT.jar > gj.log 2>&1 &