#!/bin/bash
set -e
# Start MySQL in the background
mysqld_safe &
# Wait for MySQL to start
sleep 10
# Run the main application
java -jar /app/app.jar
