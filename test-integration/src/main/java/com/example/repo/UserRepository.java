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
        String sql = "SELECT username FROM users";

        try (
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ){
            while (rs.next()) {
                result.add(rs.getString("username"));
            }
        }
        return result;
    }

    public void insertUser(String username) throws SQLException {
        String sqlInsertUsername="INSERT INTO users (username) VALUES (?)";

        try (
            Connection conn = DriverManager.getConnection(jdbcUrl, this.username, this.password);
            PreparedStatement stmt = conn.prepareStatement(sqlInsertUsername)
        ){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
