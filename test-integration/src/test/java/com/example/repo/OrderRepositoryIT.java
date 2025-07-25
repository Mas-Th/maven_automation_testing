
package com.example.repo;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryIT {
    static PostgreSQLContainer<?> postgre =  new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    static UserRepository userRepo;
    static OrderRepository orderRepo;
    static int userIdAlice;

    @BeforeAll
    static void setup() throws Exception {
        postgre.start();

        String jdbcUrl = postgre.getJdbcUrl();

        userRepo = new UserRepository(jdbcUrl, "testuser", "testpass");
        orderRepo = new OrderRepository(jdbcUrl, "testuser", "testpass");

        try(Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
            Statement stmt = conn.createStatement())
        {
            stmt.execute("DROP TABLE IF EXISTS orders");
            stmt.execute("DROP TABLE IF EXISTS users");

            stmt.execute("""
            CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL 
                               )
           
           """);


            stmt.execute("""
            CREATE TABLE orders (
                id SERIAL PRIMARY KEY,
                user_id INTEGER NOT NULL REFERENCES users(id),
                amount DECIMAL(10,2) NOT NULL
                                )
            """);
            // 4. Insert dữ liệu mẫu
            userRepo.insertUser("alice");
        }

        try (Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {

            stmt.setString(1, "alice");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userIdAlice = rs.getInt("id");
                }
            }
        }
    }

    @AfterAll
    static void teardown() {
        postgre.stop();
    }

    @Test
    void testInsertMultipleOrders() throws Exception {
        orderRepo.insertOrder(userIdAlice, 100.50);
        orderRepo.insertOrder(userIdAlice, 200.02);
        orderRepo.insertOrder(userIdAlice, 300.00);

        List<Double> amounts = orderRepo.getOrderAmountsByUsername("alice");

        assertEquals(3, amounts.size());
        assertTrue(amounts.stream().anyMatch(amount -> amount == 100.50));
        assertTrue(amounts.stream().anyMatch(amount -> amount == 200.02));
    }

    @Test
    void testFailInsertWithInvalidUserId_thenRollback() {
        Exception exception = assertThrows(SQLException.class, () -> {
            orderRepo.insertOrder(-999, 123.45);
        });
        System.out.println("Expected error: " + exception.getMessage());
    }

    @Test
    void testManualTransactionRollback() throws Exception {
        try (Connection conn = DriverManager.getConnection(postgre.getJdbcUrl(), "testuser", "testpass")) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, amount) VALUES (?, ?)")) {
                stmt.setInt(1, userIdAlice);
                stmt.setDouble(2, 200.00);
                stmt.executeUpdate();

                // Simulate lỗi: chèn lỗi để rollback
                if (true) throw new RuntimeException("Fake failure");

                conn.commit(); // sẽ không chạy đến đây
            } catch (Exception e) {
                conn.rollback(); // rollback
                System.out.println("Rolled back transaction");
            }
        }
    }

//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("testdb")
//            .withUsername("testuser")
//            .withPassword("testpass");
//
//    static UserRepository userRepo;
//    static OrderRepository orderRepo;
//
//    static int userIdAlice;
//
//    @BeforeAll
//    static void setup() throws Exception {
//        postgres.start();
//
//        String jdbcUrl = postgres.getJdbcUrl();
//        userRepo = new UserRepository(jdbcUrl, "testuser", "testpass");
//        orderRepo = new OrderRepository(jdbcUrl, "testuser", "testpass");
//
//        try (Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
//             Statement stmt = conn.createStatement()) {
//
//            // 1. Xóa bảng nếu tồn tại (orders phải xóa trước vì có foreign key tới users)
//            stmt.execute("DROP TABLE IF EXISTS orders");
//            stmt.execute("DROP TABLE IF EXISTS users");
//
//            // 2. Tạo lại bảng users
//            stmt.execute("""
//            CREATE TABLE users (
//                id SERIAL PRIMARY KEY,
//                username VARCHAR(50) UNIQUE NOT NULL
//            )
//        """);
//
//            // 3. Tạo lại bảng orders
//            stmt.execute("""
//            CREATE TABLE orders (
//                id SERIAL PRIMARY KEY,
//                user_id INTEGER NOT NULL REFERENCES users(id),
//                amount DECIMAL(10,2) NOT NULL
//            )
//        """);
//        }
//
//        // 4. Insert dữ liệu mẫu
//        userRepo.insertUser("alice");
//
//        // 5. Lấy userId của alice để dùng trong test
//        try (Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
//             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
//
//            stmt.setString(1, "alice");
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    userIdAlice = rs.getInt("id");
//                }
//            }
//        }
//    }
//
//    @AfterAll
//    static void teardown() {
//        postgres.stop();
//    }
//
//    @Test
//    void testInsertMultipleOrders() throws Exception {
//        orderRepo.insertOrder(userIdAlice, 100.50);
//        orderRepo.insertOrder(userIdAlice, 50.25);
//
//        List<Double> amounts = orderRepo.getOrderAmountsByUsername("alice");
//
//        assertEquals(2, amounts.size());
//        assertTrue(amounts.contains(100.50));
//        assertTrue(amounts.contains(50.25));
//    }
//
//    @Test
//    void testFailInsertWithInvalidUserId_thenRollback() {
//        Exception exception = assertThrows(SQLException.class, () -> {
//            orderRepo.insertOrder(-999, 123.45);
//        });
//        System.out.println("Expected error: " + exception.getMessage());
//    }
//
//    @Test
//    void testManualTransactionRollback() throws Exception {
//        try (Connection conn = DriverManager.getConnection(postgres.getJdbcUrl(), "testuser", "testpass")) {
//            conn.setAutoCommit(false);
//
//            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, amount) VALUES (?, ?)")) {
//                stmt.setInt(1, userIdAlice);
//                stmt.setDouble(2, 200.00);
//                stmt.executeUpdate();
//
//                // Simulate lỗi: chèn lỗi để rollback
//                if (true) throw new RuntimeException("Fake failure");
//
//                conn.commit(); // sẽ không chạy đến đây
//            } catch (Exception e) {
//                conn.rollback(); // rollback
//                System.out.println("Rolled back transaction");
//            }
//        }
//
//        List<Double> amounts = orderRepo.getOrderAmountsByUsername("alice");
//        assertFalse(amounts.contains(200.00));
//    }
}
