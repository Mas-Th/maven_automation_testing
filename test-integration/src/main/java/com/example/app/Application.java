package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.app")
@EntityScan(basePackages = "com.example.user.models")
public class Application {
    public static void main(String[] args) {
        System.out.println(">>> Starting Spring Boot...");
        SpringApplication.run(Application.class, args);
        System.out.println(">>> Spring Boot started successfully.");
    }
}

