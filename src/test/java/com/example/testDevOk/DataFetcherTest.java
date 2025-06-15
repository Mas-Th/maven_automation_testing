// src/test/java/com/example/DataFetcherTest.java
package com.example.testDevOk;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class DataFetcherTest {

    private final DataFetcher dataFetcher = new DataFetcher();

    @Test
    void testFetchData_completesWithinTimeout() {
        // Định nghĩa một khoảng timeout (ví dụ: 200 milliseconds)
        Duration timeout = Duration.ofMillis(200);

        // Chạy test và kiểm tra nó hoàn thành trong khoảng timeout
        String result = assertTimeoutPreemptively(timeout, () -> {
            return dataFetcher.fetchData();
        });
        
        // Vẫn có thể kiểm tra kết quả trả về như bình thường
        assertEquals("Sample Data", result);
    }

    @Test
    void testFetchData_failsWhenTooSlow() {
        // Định nghĩa một khoảng timeout ngắn hơn thời gian thực thi (ví dụ: 50ms)
        Duration timeout = Duration.ofMillis(50);
        
        // Test này được kỳ vọng sẽ thất bại với lỗi TimeoutExpiredException
        // vì fetchData() mất 100ms để chạy.
        assertThrows(org.opentest4j.AssertionFailedError.class, () -> {
            assertTimeoutPreemptively(timeout, () -> {
                dataFetcher.fetchData();
            });
        });
    }
}