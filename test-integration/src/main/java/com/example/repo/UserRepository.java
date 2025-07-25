package com.example.repo;

import java.sql.*;
import java.util.*;

public class UserRepository {
    private final String jdbcUrl;
    private final String username;
    private final String password;
    // Ctor
    public UserRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }
    // Method
    public List<String> findAllUsernames() throws SQLException {
        List<String> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username FROM users")) {

            while (rs.next()) {
                result.add(rs.getString("username"));
            }
        }
        return result;
    }

    public void insertUser(String username) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, this.username, this.password);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username) VALUES (?)")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
