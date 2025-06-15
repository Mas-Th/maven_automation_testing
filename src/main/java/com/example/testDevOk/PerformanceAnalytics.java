// src/main/java/com/example/PerformanceAnalytics.java
package com.example.testDevOk;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PerformanceAnalytics {
    // Một phương thức giả lập việc xử lý rất tốn tài nguyên
    public long processLargeDataSet(List<String> data) {
        try {
            // Giả lập xử lý nặng
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return data.size();
    }
}