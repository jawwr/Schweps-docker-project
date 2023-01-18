FROM openjdk:17-alpine
COPY . /src
WORKDIR /src
COPY Web/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]