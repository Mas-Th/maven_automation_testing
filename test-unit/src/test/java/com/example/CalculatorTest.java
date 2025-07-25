package com.example;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for MyCalculator class")
class CalculatorTest {
    private Calculator calculator;

    @BeforeAll
    static void setupAll() {
        System.out.println("Running @BeforeAll: Initializing resources for all tests.");
    }

    @BeforeEach
    void setup() {
        calculator = new Calculator();
        System.out.println("Running @BeforeEach: Initializing calculator for each test.");
    }

    @Test
    @DisplayName("Should add two numbers correctly")
    void testAdd() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 should be 5");
        System.out.println("Running testAdd.");
    }

    @Test
    @DisplayName("Should subtract two numbers correctly")
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2), "3 - 2 should be 1");
        System.out.println("Running testSubtract.");
    }

    @AfterEach
    void teardown() {
        calculator = null;
        System.out.println("Running @AfterEach: Cleaning up calculator after each test.");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("Running @AfterAll: Cleaning up resources after all tests.");
    }
}
