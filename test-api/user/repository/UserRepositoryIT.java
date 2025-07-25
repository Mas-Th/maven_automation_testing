package com.example.app.user.repository;

import com.example.app.user.User;
import com.example.app.user.UserRepository;
import com.example.app.user.integration.AbstractIntegrationTest; // Kế thừa từ lớp base

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest thường dùng với embedded DB. Với Testcontainers, chúng ta cần cấu hình khác đi một chút
// Hoặc đơn giản là dùng @SpringBootTest từ lớp cha là đủ.
// Ta sẽ bỏ @DataJpaTest và kế thừa từ lớp base.

// Cách 1: Sử dụng lại đầy đủ môi trường SpringBootTest từ lớp cha
// (Đơn giản và test được toàn bộ stack)
class UserRepositoryIT extends AbstractIntegrationTest { // <--- Kế thừa ở đây

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSaveUser_thenFindById_shouldReturnUser() {
        // Given
        User newUser = new User();
        newUser.setUsername("testuser_pg"); // Đổi tên để phân biệt
        newUser.setEmail("test_pg@example.com");

        // When
        User savedUser = userRepository.save(newUser);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser_pg");
        assertThat(foundUser.getId()).isNotNull();
    }
}