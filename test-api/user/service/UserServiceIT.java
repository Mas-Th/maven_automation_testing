package com.example.app.user.service;

import com.example.app.user.UserRepository;
import com.example.app.user.UserService;
import com.example.app.user.integration.AbstractIntegrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional // Vẫn giữ @Transactional để rollback sau mỗi test
class UserServiceIT extends AbstractIntegrationTest { // <--- Chỉ cần kế thừa

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // Xóa sạch DB trước mỗi test để đảm bảo tính độc lập
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenCreateUser_withValidData_shouldSucceed() {
        // ... Logic test y hệt như cũ ...
    }

    @Test
    void whenCreateUser_withExistingEmail_shouldThrowException() {
        // ... Logic test y hệt như cũ ...
    }
}