#!/bin/bash
set -xe


# Copy war file from S3 bucket to tomcat webapp folder
aws s3 cp codedeploystack-webappdeploymentbucket-09pf6atv8xuw/aws-shopping-car-1.0-SNAPSHOT.war /usr/local/tomcat10/webapps/aws-shopping-car-1.0-SNAPSHOT.war


# Ensure the ownership permissions are correct.
chown -R tomcat:tomcat /usr/local/tomcat10/webapps