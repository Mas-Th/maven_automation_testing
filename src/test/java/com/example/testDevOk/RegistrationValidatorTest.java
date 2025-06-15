// src/test/java/com/example/RegistrationValidatorTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationValidatorTest {

    private final RegistrationValidator validator = new RegistrationValidator();

    @ParameterizedTest(name = "Kiểm tra tuổi {0}, mong đợi kết quả là {1}")
    @CsvSource({
            // Cú pháp: "đầu vào, kết quả mong đợi"
            "17, false",  // Dưới tuổi tối thiểu
            "18, true",   // Bằng tuổi tối thiểu
            "65, true", //Trong khoảng hợp lệ
            "99, true",// Trong khoảng hợp lệ
            "100, true",  // Bằng tuổi tối đa
            "101, false"  // Trên tuổi tối đa
    })
    @DisplayName("Kiểm tra các trường hợp tuổi hợp lệ và không hợp lệ")
    void testIsAgeValid(int age, boolean expectedResult) {
        assertEquals(expectedResult, validator.isAgeValid(age));
    }
}