// src/test/java/com/example/ShoppingCartTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;

    // Phương thức này sẽ chạy trước mỗi test case
    @BeforeEach
    void setUp() {
        // Khởi tạo một giỏ hàng mới cho mỗi lần test
        cart = new ShoppingCart();
    }

    @Test
    void testCartIsInitiallyEmpty() {
        // 1. Kiểm tra giỏ hàng lúc đầu rỗng
        assertEquals(0, cart.getItemCount());
        assertEquals(0.0, cart.getTotalPrice());
    }

    @Test
    void testAddItem() {
        // 2. Thêm một sản phẩm và kiểm tra
        cart.addItem(new Item("Laptop", 1200.50));
        assertEquals(1, cart.getItemCount());
        assertEquals(1200.50, cart.getTotalPrice());
    }

    @Test
    void testAddMultipleItems() {
        // 3. Thêm nhiều sản phẩm và kiểm tra tổng tiền
        cart.addItem(new Item("Mouse", 25.00));
        cart.addItem(new Item("Keyboard", 75.00));
        assertEquals(2, cart.getItemCount());
        assertEquals(100.00, cart.getTotalPrice());
    }
}