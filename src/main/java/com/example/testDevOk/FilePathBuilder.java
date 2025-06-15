// src/main/java/com/example/FilePathBuilder.java
package com.example.testDevOk;

import java.io.File;

public class FilePathBuilder {
    public String buildPath(String parent, String child) {
        // File.separator sẽ tự động là '\' trên Windows và '/' trên OS khác
        return parent + File.separator + child;
    }
}