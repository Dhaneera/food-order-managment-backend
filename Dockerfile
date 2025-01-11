# Use the official Amazon Corretto 21 JDK image as a base
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar



# Expose the application's port (change if necessary)
EXPOSE 8080

# Command to run the JAR file

ENTRYPOINT [    "java", "-jar", "app.jar"]

