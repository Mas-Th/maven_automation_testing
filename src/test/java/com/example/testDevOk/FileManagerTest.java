// src/test/java/com/example/FileManagerTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private final FileManager fileManager = new FileManager();

    // JUnit sẽ inject một thư mục tạm thời vào biến này
    @TempDir
    Path tempDirectory;

    @Test
    @DisplayName("Ghi và đọc file thành công trong thư mục tạm")
    void testWriteAndReadFile() throws IOException {
        // 1. Tạo đường dẫn đến file trong thư mục tạm
        Path testFile = tempDirectory.resolve("my-test-file.txt");
        String content = "Hello, JUnit 5 TempDir!";

        // 2. Ghi nội dung vào file
        fileManager.writeToFile(testFile, content);

        // 3. Xác minh file đã tồn tại
        assertTrue(Files.exists(testFile), "File phải được tạo.");

        // 4. Đọc lại nội dung và kiểm tra
        String readContent = fileManager.readFromFile(testFile);
        assertEquals(content, readContent, "Nội dung đọc ra phải giống nội dung đã ghi.");
    }
}