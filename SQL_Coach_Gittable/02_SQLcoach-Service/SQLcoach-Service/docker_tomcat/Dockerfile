FROM tomcat:7-jre8-alpine
COPY target/sqlcoachservice.war /usr/local/tomcat/webapps/sqlcoachservice.war
ENV postgres_properties=postgres_docker.properties