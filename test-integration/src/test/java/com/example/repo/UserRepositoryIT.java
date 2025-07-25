package com.example.repo;

import lombok.With;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserRepositoryIT {
    static PostgreSQLContainer<?> postgre = new PostgreSQLContainer<>("postgres:16")
    .withDatabaseName("testDb")
    .withUsername("testuser")
    .withPassword("testpassword");

    static UserRepository userRepository;

    @BeforeAll
    static void startContainer() throws Exception {
        postgre.start();

        String jdbcUrl = postgre.getJdbcUrl();
        userRepository = new UserRepository(jdbcUrl, "testuser", "testpassword");

        try (   Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpassword");
                Statement stmt = conn.createStatement())
        {
                stmt.execute("CREATE TABLE users (id SERIAL PRIMARY KEY, username VARCHAR(50))");
        };
    }

    @AfterAll
    static void stopContainer(){
        postgre.stop();
    }
    @Test
    void testInsertAndFindAllUser() throws SQLException {
        userRepository.insertUser("a");
        userRepository.insertUser("b");
        userRepository.insertUser("c");

        List<String> usernames = userRepository.findAllUsernames();

        assertEquals(3, usernames.size(), "co 3");
        assertTrue(usernames.contains("a"));
        assertTrue(usernames.contains("b"));
    }
}



//class UserRepositoryIT {
//
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("testdb")
//            .withUsername("testuser")
//            .withPassword("testpass");
//
//    static UserRepository userRepository;
//
//    @BeforeAll
//    static void startContainer() throws Exception {
//        postgres.start();
//
//        String jdbcUrl = postgres.getJdbcUrl();
//        userRepository = new UserRepository(jdbcUrl, "testuser", "testpass");
//
//        try (Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
//             Statement stmt = conn.createStatement()) {
//            stmt.execute("CREATE TABLE users (id SERIAL PRIMARY KEY, username VARCHAR(50))");
//        }
//    }
//
//    @AfterAll
//    static void stopContainer() {
//        postgres.stop();
//    }
//
//    @Test
//void testInsertAndFindAllUser() throws SQLException {
//        userRepository.insertUser("alice");
//        userRepository.insertUser("bod");
//        List<String> usernames = userRepository.findAllUsernames();
//
//        assertEquals(2, usernames.size());
//        assertTrue(usernames.contains("alice"));
//        assertTrue(usernames.contains("bob"));
//    }


//    void testInsertAndFindAll() throws Exception {
//        userRepository.insertUser("alice");
//        userRepository.insertUser("bob");
//
//        List<String> usernames = userRepository.findAllUsernames();
//
//        assertEquals(2, usernames.size());
//        assertTrue(usernames.contains("alice"));
//        assertTrue(usernames.contains("bob"));
//    }
//}
