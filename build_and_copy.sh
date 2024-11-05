#!/bin/bash

TARGET_FILE="path/to/file/generated/by/maven/file.jar"
DESTINATION_DIR="path/to/destination/folder/"

echo "Building project..."
mvn clean package

if [ -f "$TARGET_FILE" ]; then
    echo "Copying file to destination..."
    cp "$TARGET_FILE" "$DESTINATION_DIR"
    echo "File copied successfully to $DESTINATION_DIR."
else
    echo "Build failed or target file not found."
fi