FROM openjdk:17-alpine
COPY . /src
WORKDIR /src
COPY Consumer/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]