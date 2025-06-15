// src/main/java/com/example/DataFetcher.java
package com.example.testDevOk;

import java.util.concurrent.TimeUnit;

public class DataFetcher {
    /**
     * Giả lập việc gọi API mất khoảng 100ms.
     * @return Dữ liệu giả lập
     */
    public String fetchData() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Sample Data";
    }
}