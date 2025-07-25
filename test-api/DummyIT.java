package com.example.app;

// src/test/java/com/example/DummyIT.java

// Test chạy integration test  mvn verify
public class DummyIT {

    @org.junit.jupiter.api.Test
    void shouldNotRunThisIT() {
        System.out.println(">>> THIS SHOULD NOT RUN!");
        System.out.println(">>> THIS SHOULD NOT RUN!");
        System.out.println(">>> THIS SHOULD NOT RUN!");
        System.out.println(">>> THIS SHOULD NOT RUN!");
        System.out.println(">>> [TEST FAIL] This integration test ran.");
        System.out.println(">>> [TEST FAIL] This integration test ran.");
        System.out.println(">>> [TEST FAIL] This integration test ran.");
    }
}
