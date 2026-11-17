# Runtime image for the E-commerce app (Tomcat 9 = javax.servlet, matches the pom.xml)
FROM tomcat:9.0-jdk11-temurin

# Remove Tomcat's default sample apps
RUN rm -rf /usr/local/tomcat/webapps/*

# ecommerce.war is produced by Jenkins (mvn clean package) and copied
# next to this Dockerfile on the ansible-master (/opt/deploy)
COPY ecommerce.war /usr/local/tomcat/webapps/ecommerce.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
