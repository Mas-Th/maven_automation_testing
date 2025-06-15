// src/main/java/com/example/Validator.java
package com.example.testDevOk;

import java.util.regex.Pattern;

public class Validator {

    // Sử dụng Regex để kiểm tra định dạng email cơ bản
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Kiểm tra xem chuỗi có phải là email hợp lệ không.
     * @param email Chuỗi cần kiểm tra
     * @return true nếu hợp lệ, false nếu không hợp lệ
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
}