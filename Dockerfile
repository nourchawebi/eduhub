FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar app.jar
ENV MYSQL_PORT=3038
ENV MYSQL_HOST=mysql
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENTRYPOINT ["java","-jar","app.jar"]