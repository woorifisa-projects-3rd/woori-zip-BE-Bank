#!/bin/bash

PID=$(pgrep -f 'backend-0.0.1-SNAPSHOT.jar')
if [ -n "$PID" ]; then
    echo "Stopping existing Spring Boot application (PID: $PID)"
    kill -15 $PID
    sleep 5

    if ps -p $PID > /dev/null; then
        echo "Force killing application (PID: $PID)"
        kill -9 $PID
    else
        echo "Application stopped successfully."
    fi
else
    echo "No running Spring Boot application found."
fi

JAR_PATH="/home/ubuntu/woori-zip-BE/build/libs/backend-0.0.1-SNAPSHOT.jar"
if [ -f "$JAR_PATH" ]; then
    echo "Deleting existing JAR file at $JAR_PATH"
    rm "$JAR_PATH"
    echo "JAR file deleted successfully."
else
    echo "No JAR file found at $JAR_PATH"
fi
