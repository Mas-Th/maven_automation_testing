// src/test/java/com/example/PerformanceAnalyticsTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PerformanceAnalyticsTest {

    private PerformanceAnalytics analytics;

    @BeforeEach
    void setUp() {
        this.analytics = new PerformanceAnalytics();
    }

    @Test
    @DisplayName("Chỉ chạy test hiệu năng trên môi trường CI hoặc PERFORMANCE")
    void testPerformanceOfLargeDataSet() {
        // 1. Lấy giá trị của biến môi trường 'ENV'
        String currentEnv = System.getProperty("ENV");

        // 2. Đặt giả định: Test này chỉ tiếp tục nếu ENV là 'CI' hoặc 'PERFORMANCE'
        Assumptions.assumeTrue(
                "CI".equalsIgnoreCase(currentEnv) || "PERFORMANCE".equalsIgnoreCase(currentEnv),
                () -> "Bỏ qua test hiệu năng trên môi trường: " + currentEnv // Thông báo khi bỏ qua
        );

        // 3. Phần code test chỉ được thực thi nếu giả định ở trên là đúng
        System.out.println("Đang chạy test hiệu năng...");
        List<String> largeData = List.of("data1", "data2", "data3");
        long result = analytics.processLargeDataSet(largeData);
        assertEquals(3, result);
    }
}