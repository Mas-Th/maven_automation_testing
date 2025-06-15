// src/test/java/com/example/UserServiceTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// Sử dụng extension của Mockito để tự động khởi tạo các mock
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 1. Tạo một mock giả cho UserRepository
    @Mock
    private UserRepository mockUserRepository;

    // 2. Tự động inject mock ở trên vào lớp UserService
    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserGreeting_whenUserExists() {
        // 3. "Dạy" cho mock: Khi phương thức findById với id "123" được gọi...
        when(mockUserRepository.findById("123"))
            // ...thì trả về một đối tượng User cụ thể.
            .thenReturn(new User("123", "Alice"));

        // 4. Gọi phương thức cần test
        String greeting = userService.getUserGreeting("123");

        // 5. Kiểm tra kết quả
        assertEquals("Hello, Alice!", greeting);
    }

    @Test
    void testGetUserGreeting_whenUserDoesNotExist() {
        // 3. "Dạy" cho mock: Khi findById được gọi với id không tồn tại...
        when(mockUserRepository.findById("999"))
            // ...thì trả về null.
            .thenReturn(null);

        // 4. Gọi phương thức cần test
        String greeting = userService.getUserGreeting("999");

        // 5. Kiểm tra kết quả
        assertEquals("Hello, Guest!", greeting);
    }
}