FROM maven:3.8.5-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18.0-slim
COPY --from=build /target/springmaven-SNAPSHOT.jar springmaven.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","springmaven.jar"]


