// src/test/java/com/example/OrderServiceTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private NotificationService mockNotificationService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testPlaceOrder_sendsCorrectNotification() {
        // 1. Định nghĩa ArgumentCaptor cho tham số kiểu String (message)
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        // 2. Gọi phương thức cần test
        orderService.placeOrder("user456", "ORD9988");

        // 3. Xác minh rằng phương thức sendNotification ĐÃ ĐƯỢC GỌI
        //    Đồng thời, "bắt giữ" tham số thứ hai (message) bằng captor
        verify(mockNotificationService).sendNotification(
            // Chúng ta có thể kiểm tra tham số userId trực tiếp
            eq("user456"),
            // Hoặc bắt giữ tham số message để kiểm tra sau
            messageCaptor.capture()
        );

        // 4. Lấy giá trị đã bị bắt giữ
        String capturedMessage = messageCaptor.getValue();

        // 5. Kiểm tra nội dung của message
        assertEquals("Your order #ORD9988 has been placed successfully.", capturedMessage);
    }
}