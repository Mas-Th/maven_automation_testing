// src/test/java/com/example/TranslatorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class TranslatorTest {

    private final Translator translator = new Translator();

    @TestFactory
    @DisplayName("Kiểm thử các cặp dịch từ")
    Stream<DynamicTest> testTranslations() {
        // Dữ liệu đầu vào và kết quả mong đợi
        Map<String, String> testCases = Map.of(
                "hello", "xin chào",
                "world", "thế giới",
                "test", "kiểm thử",
                "java", "N/A" // Trường hợp không có trong từ điển
        );

        // Tạo ra một stream các DynamicTest từ bộ dữ liệu
        return testCases.entrySet().stream()
                .map(entry -> dynamicTest(
                        "Dịch từ '" + entry.getKey() + "'", // Tên hiển thị của test động
                        () -> { // Logic test
                            assertEquals(entry.getValue(), translator.translate(entry.getKey()));
                        }
                ));
    }
}