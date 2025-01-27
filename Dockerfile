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

# Install MySQL server
RUN apt-get update && apt-get install -y \
    mysql-server \
    && rm -rf /var/lib/apt/lists/*

# Configure MySQL
RUN mkdir -p /var/run/mysqld \
    && chown -R mysql:mysql /var/run/mysqld \
    && echo "bind-address=0.0.0.0" >> /etc/mysql/mysql.conf.d/mysqld.cnf

# Copy initialization script for MySQL
COPY init.sql /docker-entrypoint-initdb.d/init.sql

# Copy the custom entrypoint script
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

# Copy the built JAR file from the builder
COPY --from=builder /app/target/*.jar app.jar

# Expose MySQL and application ports
EXPOSE 3306 8080

# Set entrypoint
ENTRYPOINT ["/docker-entrypoint.sh"]
