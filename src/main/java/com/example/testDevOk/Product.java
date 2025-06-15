// src/main/java/com/example/Product.java
package com.example.testDevOk;

import java.util.Objects;


public class Product {
    private String name;
    private double price;
    // Constructor, getters...
    public Product(String name, double price) 
    { 
        this.name = name; this.price = price; 
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    // Thêm equals & hashCode để việc so sánh trong test dễ dàng hơn
    @Override public boolean equals(Object o) 
    { 
        if (this == o) return true;                    // Cùng tham chiếu => bằng nhau
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);

    }

    @Override public int hashCode() {  return Objects.hash(name, price); }
}
