// src/main/java/com/example/FileManager.java
package com.example.testDevOk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public void writeToFile(Path filePath, String content) throws IOException {
        Files.writeString(filePath, content);
    }

    public String readFromFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }
}