// src/main/java/com/example/ProductFilter.java
package com.example.testDevOk;

import java.util.List;
import java.util.stream.Collectors;

public class ProductFilter {
    public List<Product> filterByPrice(List<Product> products, double minPrice) {
        if (products == null) {
            return List.of(); // Trả về danh sách rỗng
        }
        if(minPrice < 0){
            throw new IllegalArgumentException("min price must to greate than 100");
        }
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice)
                .collect(Collectors.toList());
    }
}