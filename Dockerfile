# Use the Maven image with OpenJDK 17 as a build stage
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application with Maven, skipping tests
RUN mvn clean package -DskipTests

# Use a slim JDK 17 image for the final stage
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the container
WORKDIR /app
COPY target/*.jar app.jar

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/springmaven-0.0.1-SNAPSHOT.jar demo.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "demo.jar"]
