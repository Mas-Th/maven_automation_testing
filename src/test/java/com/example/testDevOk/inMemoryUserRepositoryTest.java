// src/test/java/com/example/InMemoryUserRepositoryTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 1. Chỉ định rằng các test sẽ được sắp xếp bằng @Order
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử CRUD cho UserRepository")
class InMemoryUserRepositoryTest {

    private static InMemoryUserRepository repository;
    private static final String USER_ID = "user-123";

    @BeforeAll
    static void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    @Order(1) // Chạy đầu tiên
    @DisplayName("1. Create: Lưu người dùng mới")
    void testCreateUser() {
        repository.save(new UserRepositoryInMemory(USER_ID, "Alice"));
        assertTrue(repository.findById(USER_ID).isPresent());
    }

    @Test
    @Order(2) // Chạy thứ hai
    @DisplayName("2. Read: Tìm và xác thực người dùng đã tạo")
    void testReadUser() {
        Optional<UserRepositoryInMemory> userOpt = repository.findById(USER_ID);
        assertTrue(userOpt.isPresent());
        assertEquals("Alice", userOpt.get().name);
    }

    @Test
    @Order(3) // Chạy thứ ba
    @DisplayName("3. Update: Cập nhật tên người dùng")
    void testUpdateUser() {
        repository.save(new UserRepositoryInMemory(USER_ID, "Bob"));
        Optional<UserRepositoryInMemory> userOpt = repository.findById(USER_ID);
        assertTrue(userOpt.isPresent());
        assertEquals("Bob", userOpt.get().name);
    }

    @Test
    @Order(4) // Chạy cuối cùng
    @DisplayName("4. Delete: Xóa người dùng")
    void testDeleteUser() {
        repository.delete(USER_ID);
        assertFalse(repository.findById(USER_ID).isPresent());
    }
}