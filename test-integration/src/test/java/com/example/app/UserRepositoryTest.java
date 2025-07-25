//package com.example.app;
//
//import com.example.app.user.User;
//import com.example.app.user.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Testcontainers
//@DataJpaTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class UserRepositoryTest {
//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
////    @Autowired
////    private TestEntityManager entityManager;
//
//    @Autowired
//    private UserRepository userRepo;
//
////    @DynamicPropertySource
////    static void setupProps(DynamicPropertyRegistry registry) {
////        registry.add("spring.datasource.url", postgres::getJdbcUrl);
////        registry.add("spring.datasource.username", postgres::getUsername);
////        registry.add("spring.datasource.password", postgres::getPassword);
////    }
//
//    @Test
//    void shouldFindByUsername() {
//        User user = new User("alice", "pass");
////        entityManager.persist(user);
//
//        Optional<User> result = userRepo.findByUsername("alice");
//        Assertions.assertTrue(result.isPresent());
//        assertEquals("alice", result.get().getUsername());
//    }
//}