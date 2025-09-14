# Use Eclipse Temurin OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY src src

# Package the application
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "target/spring-boot-postgresql-crud-0.0.1-SNAPSHOT.jar"]
