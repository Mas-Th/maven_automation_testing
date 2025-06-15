// src/main/java/com/example/InMemoryUserRepository.java
package com.example.testDevOk;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class InMemoryUserRepository{
    private final Map<String, UserRepositoryInMemory> database = new HashMap<>();

    public void save(UserRepositoryInMemory user) {
        database.put(user.id, user);
    }

    public Optional<UserRepositoryInMemory> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    public void delete(String id) {
        database.remove(id);
    }
}