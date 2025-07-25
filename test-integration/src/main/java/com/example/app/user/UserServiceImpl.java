package com.example.app.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userrepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userrepo = userRepo;
    }

    @Override
    public User register(String username, String password) {
        return userrepo.save(new User(username, password));
    }

    @Override
    public User login(String username, String password) {
        return userrepo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Login failed"));
    }
}