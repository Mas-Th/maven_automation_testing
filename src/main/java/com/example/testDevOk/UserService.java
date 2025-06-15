// src/main/java/com/example/UserService.java (Lớp cần test)
package com.example.testDevOk;
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserGreeting(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return "Hello, Guest!";
        }
        return "Hello, " + user.getName() + "!";
    }
}