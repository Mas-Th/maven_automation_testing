package com.example.app.user.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers // Báo cho JUnit 5 biết lớp này sử dụng Testcontainers
public abstract class AbstractIntegrationTest {

    @Container // Đánh dấu đây là một container được quản lý bởi Testcontainers
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15-alpine") // Sử dụng ảnh Docker postgres
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    // Phương thức này sẽ được gọi SAU KHI container đã khởi động
    // để cung cấp thông tin kết nối cho Spring Boot một cách linh động.
    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }
}