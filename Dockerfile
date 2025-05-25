# Use Tomcat with OpenJDK 11
FROM tomcat:9.0-jdk11-openjdk

# Maintainer info (optional)
LABEL maintainer="anudeep0496m@gmail.com"

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR into Tomcat webapps folder
COPY LoginWebApp.war /usr/local/tomcat/webapps/ROOT.war

# Add MySQL connector
COPY mysql-connector-j-8.0.31.jar /usr/local/tomcat/lib/

# Change default port to 7071
RUN sed -i 's/port="8080"/port="7071"/' /usr/local/tomcat/conf/server.xml

# Expose the new port
EXPOSE 7071

# Start Tomcat
CMD ["catalina.sh", "run"]
