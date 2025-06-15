// src/main/java/com/example/Translator.java
package com.example.testDevOk;

import java.util.Map;

public class Translator {
    private final Map<String, String> dictionary = Map.of(
            "hello", "xin chào",
            "world", "thế giới",
            "test", "kiểm thử"
    );

    public String translate(String word) {
        return dictionary.getOrDefault(word.toLowerCase(), "N/A");
    }
}