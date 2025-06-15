// src/main/java/com/example/RegistrationValidator.java
package com.example.testDevOk;

public class RegistrationValidator {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;

    /**
     * Kiểm tra tuổi hợp lệ để đăng ký.
     * @param age Tuổi của người dùng
     * @return true nếu tuổi trong khoảng [18, 100], ngược lại false.
     */
    public boolean isAgeValid(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }
}