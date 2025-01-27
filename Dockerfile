# Use an official Maven image to build the project
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project and skip tests
RUN mvn clean install -DskipTests

# Use a lightweight JDK image to run the app (non-Alpine version)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]