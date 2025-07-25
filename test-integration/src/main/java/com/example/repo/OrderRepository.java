package com.example.repo;

import java.sql.*;
import java.util.*;

public class OrderRepository {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public OrderRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void insertOrder(int userId, double amount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO orders (user_id, amount) VALUES (?, ?)")) {

            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        }
    }

    public List<Double> getOrderAmountsByUsername(String usernameQuery) throws SQLException {
        List<Double> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement("""
                 SELECT o.amount FROM orders o
                 JOIN users u ON o.user_id = u.id
                 WHERE u.username = ?
             """)) {

            stmt.setString(1, usernameQuery);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getDouble("amount"));
                }
            }
        }
        return result;
    }
}
