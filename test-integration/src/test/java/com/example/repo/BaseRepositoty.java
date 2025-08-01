package com.example.repo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;

public class BaseRepositoty {
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

        String sqlCreateUsers = """
            CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL
                               )
           """;
        String sqlCreateOrders = """
            CREATE TABLE orders (
                id SERIAL PRIMARY KEY,
                user_id INTEGER NOT NULL REFERENCES users(id),
                amount DECIMAL(10,2) NOT NULL
                                )
            """;

        String sqlDropUsersTable = "DROP TABLE IF EXISTS users";
        String sqlDropOrdersTable = "DROP TABLE IF EXISTS orders";

        String sqlSelectUsernameUsersTable = "SELECT id FROM users WHERE username = ?";


        try(
            Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
            Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlDropUsersTable);
            stmt.execute(sqlDropOrdersTable);

            stmt.execute(sqlCreateUsers);
            stmt.execute(sqlCreateOrders);

            // 4. Insert dữ liệu mẫu
            userRepo.insertUser("alice");
        }

        try (
                Connection conn = DriverManager.getConnection(jdbcUrl, "testuser", "testpass");
                PreparedStatement stmt = conn.prepareStatement(sqlSelectUsernameUsersTable)) {

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
}
