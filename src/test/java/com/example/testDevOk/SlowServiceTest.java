// src/test/java/com/example/SlowServiceTest.java
package com.example.testDevOk;

import com.example.extensions.TimingExtension; // Import extension vừa tạo
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.concurrent.TimeUnit;

// Áp dụng extension vào lớp test này
@ExtendWith(TimingExtension.class)
class SlowServiceTest {

    @Test
    void testSomethingQuick() {
        // Test này sẽ chạy rất nhanh
        System.out.println("Đây là một test nhanh.");
    }

    @Test
    void testSomethingSlow() throws InterruptedException {
        // Test này sẽ chạy chậm
        System.out.println("Đây là một test chậm.");
        TimeUnit.SECONDS.sleep(1);
    }
}