# Use Maven as the builder for Java application
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# Set working directory for the builder
WORKDIR /app

# Copy project files to the container
COPY . .

# Build the project and skip tests
RUN mvn clean install -DskipTests

# Use Eclipse Temurin as the base image for the runtime
FROM eclipse-temurin:17-jdk

# Set working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the builder
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]