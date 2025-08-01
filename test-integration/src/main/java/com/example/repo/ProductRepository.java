package com.example.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private String jdbc;
    private String username;
    private String password;

    public ProductRepository(String jdbc, String username, String password){
        this.jdbc = jdbc;
        this.username = username;
        this.password= password;
    }

    public List<String> findAllProductsName() throws SQLException {
        List<String> result = new ArrayList<>();

        try(
            Connection conn = DriverManager.getConnection(jdbc, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM products");
        ){
            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void insertProductName(String name){

        try(
            Connection conn = DriverManager.getConnection(jdbc, username, password);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO  products (name) VALUES (?)");
        ){
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
