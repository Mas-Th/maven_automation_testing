// src/test/java/com/example/AdvancedCalculatorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử lớp AdvancedCalculator")
class AdvancedCalculatorTest {

    private final AdvancedCalculator calculator = new AdvancedCalculator();

    @Nested
    @DisplayName("Các phép toán cộng và trừ")
    class AdditionAndSubtraction {
        @Test
        @DisplayName("Cộng hai số dương")
        void testAdd() {
            assertEquals(5, calculator.add(2, 3));
        }

        @Test
        @DisplayName("Trừ hai số")
        void testSubtract() {
            assertEquals(-1, calculator.subtract(2, 3));
        }
    }

    @Nested
    @DisplayName("Các phép toán nhân và chia")
    class MultiplicationAndDivision {
        @Test
        @DisplayName("Nhân hai số")
        void testMultiply() {
            assertEquals(6, calculator.multiply(2, 3));
        }

        @Test
        @DisplayName("Chia hai số hợp lệ")
        void testDivide() {
            assertEquals(2.5, calculator.divide(5, 2));
        }

        @Test
        @DisplayName("Ném ngoại lệ khi chia cho 0")
        void testDivideByZero() {
            assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
        }
        @Test
        @DisplayName("Bình phương số cho trước")
        void testX2(){
            assertEquals(25, calculator.x2(5));
        }
    }
    
}