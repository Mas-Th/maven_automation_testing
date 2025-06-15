// src/test/java/com/example/LoginServiceTest.java
package com.example.testDevOk;

import com.example.interfaces.AuditService;
import com.example.interfaces.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock private AuditService mockAuditService;
    @Mock private SecurityService mockSecurityService;
    @InjectMocks private LoginService loginService;

    @Test
    void testLoginSuccess_logsAndDoesNotAlert() {
        loginService.login("admin", "password123");

        // 1. Xác minh auditService.log được gọi chính xác 1 lần
        verify(mockAuditService, times(1)).log("LOGIN_SUCCESS: admin");

        // 2. Xác minh securityService.alert không bao giờ được gọi
        verify(mockSecurityService, never()).alert(anyString());
    }

    @Test
    void testLoginFailure_logsAndAlerts() {
        loginService.login("admin", "wrongpassword");

        // 1. Xác minh auditService.log được gọi chính xác 1 lần
        verify(mockAuditService, times(1)).log("LOGIN_FAILURE: admin");

        // 2. Xác minh securityService.alert được gọi chính xác 1 lần
        verify(mockSecurityService, times(1)).alert("Failed login attempt for admin");
    }
}