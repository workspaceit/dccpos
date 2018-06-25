ssh wsit@192.168.1.18

tail  -f  /opt/tomcat/logs/catalina.out

sudo scp /home/mi/IdeaProjects/dccpos/target/dccpos.war  wsit@192.168.1.67:/home/wsit/temp
sudo cp dccpos.war /opt/tomcat/webapps/


sudo /etc/init.d/tomcat7 start

sudo netstat -nlp | grep :80



mvn package -DskipTests