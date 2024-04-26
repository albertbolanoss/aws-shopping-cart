#!/bin/bash
set -xe

# Start Tomcat, the application server.
#service tomcat start
java -jar /usr/local/tomcat10/webapps/aws-shopping-car-1.0-SNAPSHOT.jar
