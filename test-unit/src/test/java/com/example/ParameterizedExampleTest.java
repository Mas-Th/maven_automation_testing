package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterizedExampleTest {

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "madam", "anna"})
    @DisplayName("Should identify palindromes correctly")
    void testIsPalindrome(String s) {
        assertTrue(StringUtils.isPalindrome(s));
    }

    // Test với @CsvSource
    @ParameterizedTest(name = "Sum of {0} and {1} should be {2}")
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "10, 20, 30"
    })
    void addNumbers(int a, int b, int expectedSum) {
        assertEquals(expectedSum, new Calculator().add(a, b));
    }

    // Test với @MethodSource
    @ParameterizedTest
    @MethodSource("provideStringsForTest")
    void testWithMethodSource(String input, int expectedLength) {
        assertEquals(expectedLength, input.length());
    }

    private static Stream<Object[]> provideStringsForTest() {
        return Stream.of(
                new Object[]{"apple", 5},
                new Object[]{"banana", 6},
                new Object[]{"kiwi", 4}
        );
    }
}
