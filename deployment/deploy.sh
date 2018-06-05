#mvn clean package -DskipTests
ssh -tt wsit@192.168.1.18 < command1.dat
#sudo scp /home/mi/IdeaProjects/dccpos/target/dccpos.war  wsit@192.168.1.18:/var/lib/tomcat7/webapps
#ssh wsit@192.168.1.18 < command2.dat