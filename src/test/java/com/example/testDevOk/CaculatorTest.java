// src/test/java/com/example/CalculatorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testSquareRootOfPositiveNumber() {
        // Kiểm tra trường hợp hợp lệ
        assertEquals(5.0, calculator.squareRoot(25.0));
    }

    @Test
    void testSquareRootOfZero() {
        // Kiểm tra trường hợp biên
        assertEquals(0.0, calculator.squareRoot(0.0));
    }

    @Test
    void testSquareRootOfNegativeNumber_shouldThrowException() {
        // Kiểm tra rằng phương thức ném ra ngoại lệ như mong đợi
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> {
                // Lambda expression chứa đoạn code được kỳ vọng sẽ ném ra exception
                calculator.squareRoot(-9.0);
            }
        );

        // (Tùy chọn) Kiểm tra thông điệp của exception
        assertEquals("Cannot calculate square root of a negative number.", exception.getMessage());
    }
}