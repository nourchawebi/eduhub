FROM openjdk:17-oracle

VOLUME /tmp
COPY target/*.jar app.jar
COPY uploads/images /uploads/images

ENV MYSQL_HOST=mysql-container1
ENV MYSQL_USER=root
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=cloudcraft
ENV MYSQL_PORT=3306

# Add other environment variables related to your application

ENTRYPOINT ["java", "-jar", "app.jar"]