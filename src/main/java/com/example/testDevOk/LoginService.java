package com.example.testDevOk;

import com.example.interfaces.AuditService;
import com.example.interfaces.SecurityService;

import java.util.Map;

// src/main/java/com/example/LoginService.java

public class LoginService {
    private final AuditService auditService;
    private final SecurityService securityService;
    private final Map<String, String> userCredentials = Map.of("admin", "password123");

    public LoginService(AuditService auditService, SecurityService securityService) {
        this.auditService = auditService;
        this.securityService = securityService;
    }

    public boolean login(String username, String password) {
        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            auditService.log("LOGIN_SUCCESS: " + username);
            return true;
        } else {
            auditService.log("LOGIN_FAILURE: " + username);
            securityService.alert("Failed login attempt for " + username);
            return false;
        }
    }

}