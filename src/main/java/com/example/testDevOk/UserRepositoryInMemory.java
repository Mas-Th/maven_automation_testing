package com.example.testDevOk;

// Giả lập một lớp User đơn giản
public class UserRepositoryInMemory {
    String id;
    public String name;

    public UserRepositoryInMemory(String id, String name) {
        this.id = id;
        this.name = name;
    }
}