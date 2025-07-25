package com.example.app.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;

    // Constructors, getters, setters

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
