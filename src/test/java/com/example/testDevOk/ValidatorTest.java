// src/test/java/com/example/ValidatorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    void testValidEmail() {
        // Kiểm tra email hợp lệ
        assertTrue(validator.isValidEmail("user@example.com"));
    }

    @Test
    void testInvalidEmail_NoAtSign() {
        // Kiểm tra email thiếu ký tự @
        assertFalse(validator.isValidEmail("userexample.com"));
    }

    @Test
    void testInvalidEmail_NoDomainDot() {
        // Kiểm tra email thiếu dấu chấm ở tên miền
        assertFalse(validator.isValidEmail("user@examplecom"));
    }

    @Test
    void testInvalidEmail_NullInput() {
        // Kiểm tra với đầu vào là null
        assertFalse(validator.isValidEmail(null));
    }

    @Test
    void testValidEmail_WithSubdomain() {
        // Kiểm tra với tên miền phụ
        assertTrue(validator.isValidEmail("user@sub.example.co.uk"));
    }
}