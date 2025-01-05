# Use the official Amazon Corretto 21 JDK image as a base
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh


# Expose the application's port (change if necessary)
EXPOSE 8080

# Command to run the JAR file

ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]

