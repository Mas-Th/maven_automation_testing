// src/test/java/com/example/FilePathBuilderTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilePathBuilderTest {

    private final FilePathBuilder pathBuilder = new FilePathBuilder();

    @Test
    @EnabledOnOs(OS.WINDOWS) // Chỉ chạy test này trên Windows
    @DisplayName("Tạo đường dẫn file trên Windows")
    void testBuildPathOnWindows() {
        String expected = "C:\\Users\\Test";
        assertEquals(expected, pathBuilder.buildPath("C:\\Users", "Test"));
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC}) // Chỉ chạy test này trên Linux hoặc macOS
    @DisplayName("Tạo đường dẫn file trên Linux/macOS")
    void testBuildPathOnUnix() {
        String expected = "/home/user/app";
        assertEquals(expected, pathBuilder.buildPath("/home/user", "app"));
    }
}