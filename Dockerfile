FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} exchange-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/exchange-app.jar"]