// src/main/java/com/example/DataProcessor.java
package com.example.testDevOk;

public class DataProcessor {
    // Phương thức public sử dụng phương thức private
    public String process(String rawData) {
        String normalized = normalizeData(rawData);
        return "Processed: " + normalized;
    }

    // Phương thức private cần được test riêng
    private String normalizeData(String data) {
        if (data == null) return "";
        return data.trim().toLowerCase().replaceAll("\\s+", " ");
    }
}