package com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PreDestroy;

@Slf4j // ✅ Tự tạo logger (Log4j2 qua Lombok)
@SpringBootApplication // ✅ Bật auto-configuration của Spring Boot
@EnableAsync // ✅ Cho phép dùng các hàm async trong hệ thống
public class Application {

    /**
     * ✅ Điểm vào của ứng dụng (Entry Point)
     * SpringApplication.run sẽ khởi động toàn bộ context của Spring Boot
     */
    public static void main(String[] args) {
        try {
            log.info("🚀 Starting application..."); // Ghi log trước khi khởi chạy (log framework đã sẵn sàng)

            SpringApplication.run(Application.class, args); // ⏩ Chạy Spring Boot

            log.info("✅ Application started successfully."); // Log sau khi hệ thống khởi động xong

        } catch (Exception ex) {
            // ❌ Nếu có lỗi khi khởi động, log lỗi đầy đủ để dễ trace
            log.error("❌ Error during application startup: {}", ex.getMessage(), ex);
        }
    }

    /**
     * ✅ Hook shutdown khi ứng dụng tắt (Ctrl+C, kill, hoặc khi deploy lại)
     * Rất hữu ích để log lại quá trình shutdown hoặc giải phóng tài nguyên (đóng connection, v.v.)
     */
    @PreDestroy
    public void onShutdown() {
        log.info("🛑 Application is shutting down gracefully...");
    }
}
