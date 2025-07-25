package com.example.app.user.controller;

import com.example.app.user.UserController;
import com.example.app.user.UserRepository;
import com.example.app.user.integration.AbstractIntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


 // Annotation này vẫn cần thiết cho MockMvc
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)

class UserControllerIT extends AbstractIntegrationTest { // <--- Chỉ cần kế thừa

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenPostUser_withValidData_shouldReturn201AndUser() throws Exception {
        // ... Logic test y hệt như cũ ...
    }

    @Test
    void whenPostUser_withExistingEmail_shouldReturn400() throws Exception {
        // ... Logic test y hệt như cũ ...
    }

}