
// src/main/java/com/example/OrderService.java
package com.example.testDevOk;

public class OrderService {
    private final NotificationService notificationService;
    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void placeOrder(String userId, String orderId) {
        // ... logic xử lý đơn hàng ...
        System.out.println("Processing order " + orderId);

        // Gửi thông báo
        String message = "Your order #" + orderId + " has been placed successfully.";
        notificationService.sendNotification(userId, message);
    }
}