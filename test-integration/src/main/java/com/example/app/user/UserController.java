//package com.example.app.user;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//    private final UserService service;
//
//    public UserController(UserService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody User u) {
//        return ResponseEntity.ok(service.register(u.getUsername(), u.getPassword()));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody User u) {
//        return ResponseEntity.ok(service.login(u.getUsername(), u.getPassword()));
//    }
//}