//package com.example.app;
//
//import com.example.app.user.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//public class UserFlowIT {
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("testdb")
//            .withUsername("testuser")
//            .withPassword("testpass");
//
//    @DynamicPropertySource
//    static void setProps(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    @Autowired
//    private TestRestTemplate rest;
//
//    @Test
//    void testFullFlow() {
//        User u = new User("john", "123");
//
//        ResponseEntity<User> reg = rest.postForEntity("/users/register", u, User.class);
//        assertEquals(HttpStatus.OK, reg.getStatusCode());
//
//        ResponseEntity<User> login = rest.postForEntity("/users/login", u, User.class);
//        assertEquals(HttpStatus.OK, login.getStatusCode());
//        assertEquals("john", login.getBody().getUsername());
//    }
//}