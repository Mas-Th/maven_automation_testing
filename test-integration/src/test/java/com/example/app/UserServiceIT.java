package com.example.app;

import com.example.app.user.User;
import com.example.app.user.UserRepository;
import com.example.app.user.UserService;
import com.example.app.user.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceIT {

    private UserRepository repo = mock(UserRepository.class);
    private UserService service = new UserServiceImpl(repo);

    @Test
    void testRegister() {
        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        User user = service.register("bob", "123");
        assertEquals("bob", user.getUsername());
    }

    @Test
    void testLoginSuccess() {
        when(repo.findByUsername("bob")).thenReturn(Optional.of(new User("bob", "123")));
        User user = service.login("bob", "123");
        assertEquals("bob", user.getUsername());
    }

    @Test
    void testLoginFail() {
        when(repo.findByUsername("bob")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.login("bob", "123"));
    }
}