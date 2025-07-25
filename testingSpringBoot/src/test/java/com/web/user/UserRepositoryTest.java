package com.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // ✅ Tạo môi trường test JPA, dùng DB H2 in-memory, rollback tự động sau mỗi test

//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:postgresql://localhost:5432/testdb",
//        "spring.datasource.username=postgres",
//        "spring.datasource.password=123456",
//        "spring.datasource.driver-class-name=org.postgresql.Driver"
//})    // Postgres DB



// @DataMongoTest // Môi trường Db MongoDB

public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // ✅ Inject repository thật, không cần mock

    @Test
    void testSaveAndFindById() {
        // ✅ Tạo user mới
        UserEntity user = new UserEntity();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("john@example.com");

        // ✅ Lưu user vào DB, trả về bản ghi đã lưu (có ID)
        UserEntity savedUser = userRepository.save(user);

        // ✅ Tìm user theo ID
        Optional<UserEntity> result = userRepository.findById(savedUser.getId());

        // ✅ Kiểm tra có tồn tại user trong DB
        assertTrue(result.isPresent());

        // ✅ Kiểm tra ID được sinh ra và hợp lệ
        assertNotNull(savedUser.getId());
        assertTrue(savedUser.getId() > 0);

        // ✅ So sánh dữ liệu trước và sau khi lưu
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getEmail(), result.get().getEmail());
        assertEquals(user.getPassword(), result.get().getPassword());
    }

    @Test
    void testFindAll() {
        // ✅ Lưu 2 user khác nhau
        userRepository.save(new UserEntity("john", "john@example.com", "john@example.com"));
        userRepository.save(new UserEntity("mary", "mary@example.com", "john@example.com"));

        // ✅ Lấy toàn bộ danh sách user
        List<UserEntity> allUsers = userRepository.findAll();

        // ✅ Kiểm tra danh sách không rỗng
        assertFalse(allUsers.isEmpty());

        // ✅ Kiểm tra username của user đầu tiên
        assertEquals("john", allUsers.getFirst().getUsername());

        // ✅ Kiểm tra tổng số lượng user
        assertEquals(2, allUsers.size());
    }

    @Test
    void testDeleteById() {
        // ✅ Lưu user
        UserEntity user = new UserEntity("john", "john@example.com", "john@example.com");
        userRepository.save(user);

        // ✅ Xóa user theo ID
        userRepository.deleteById(user.getId());

        // ✅ Kiểm tra không còn tồn tại user sau khi xóa
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void testExistsById() {
        // ✅ Lưu user
        UserEntity user = new UserEntity("john", "john@example.com", "john@example.com");
        UserEntity saved = userRepository.save(user);

        // ✅ Kiểm tra user có tồn tại theo ID
        assertTrue(userRepository.existsById(saved.getId()));
    }

    @Test
    void testUpdateById() {
        // ✅ Tạo user ban đầu
        UserEntity savedUser = userRepository.save(
                new UserEntity("john", "john@example.com", "secret")
        );
        Long id = savedUser.getId();

        // ✅ Lấy user từ DB, cập nhật email
        UserEntity user = userRepository.findById(id).orElseThrow();
        user.setEmail("john_updated@example.com");
        userRepository.save(user); // Cập nhật = save lại

        // ✅ Truy vấn lại user để kiểm tra
        UserEntity updatedUser = userRepository.findById(id).orElseThrow();

        // ✅ Kiểm tra email đã được cập nhật
        assertEquals("john_updated@example.com", updatedUser.getEmail());

        // ✅ Kiểm tra các field còn lại không bị thay đổi
        assertEquals("john", updatedUser.getUsername());
        assertEquals("secret", updatedUser.getPassword());
    }

}
