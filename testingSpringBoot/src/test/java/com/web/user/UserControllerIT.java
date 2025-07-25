package com.web.user;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Optional;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IUserService userService;




    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        // ✅ Giả sử bạn đã có dữ liệu chuẩn trong DB, hoặc chuẩn bị trước bằng TestContainer/DataSeeder

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3)) // ✅ VD: bạn đã biết có 2 user
                .andExpect(jsonPath("$.data[1].username").value("jane"))
                .andExpect(jsonPath("$.data[1].email").value("jane@example.com"));
    }

    @BeforeEach
    void setUp() {
//        userService.deleteAllUsers(); // Dọn sạch DB test
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserDtoRequest user = new UserDtoRequest(1L,"john", "john@example.com", "123456");
        userService.createUser(user);

        // When - thực hiện GET request
        mockMvc.perform(get("/api/users/" + user.getId())
                        .accept(MediaType.APPLICATION_JSON))
                // Then - kiểm tra phản hồi
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.username").value("john"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    void getUserById_ShouldReturn404_WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }


    @Test
    void createUser_ShouldReturn201_WhenValid() throws Exception {
        UserDtoRequest request = new UserDtoRequest("john", "john@example.com", "123456");

        System.out.println(objectMapper.writeValueAsString(request));
        System.out.println(objectMapper.writeValueAsString(request));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("john"));
    }

    @ParameterizedTest
    @CsvSource({
            "'', john@example.com, 123456, username, must not be blank",
            "john, not-an-email, 123456, email, must be a well-formed email address",
            "john, john@example.com, 123, password, size must be between 6 and 2147483647"
    })
    void createUser_ShouldReturn400AndFieldError_WhenInvalid(
            String username, String email, String password ) throws Exception {

        UserDtoRequest request = new UserDtoRequest(username, email, password);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }


    @Sql(statements = {
            "DELETE FROM users;",
            "INSERT INTO users (id, username, email, password) VALUES (1L, 'john', 'john@example.com', '123456');",
            "INSERT INTO users (id, username, email, password) VALUES (2L, 'jane', 'jane@example.com', 'abcdef');",
            "INSERT INTO users (id, username, email, password) VALUES (3L, 'mike', 'mike@example.com', '654321');"
    })
    @ParameterizedTest
    @MethodSource("updateUserProvider")
    void updateUser_ShouldFields_WhenValid_ThenSuccess(Long id, String username, String email, String password) throws Exception {
        UserDtoRequest request = new UserDtoRequest(username, email, password);


        mockMvc.perform(put("/api/users/1", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.email").value(email));

    }

    static Stream<Arguments> updateUserProvider() {
        return Stream.of(
                Arguments.of( 1L,"john", "john@example.com", "pass"),                          // chỉ username
                Arguments.of( 1L,"john", "john@example.com", "123456"),             // chỉ email
                Arguments.of( 1L,"johnny", "johnny@example.com", "newpass"),                         // chỉ password
                Arguments.of( 1L,"john", "john@example.com", "pass"),         // username + email
                Arguments.of( 1L,"johnny", "johnny@example.com", "newpass"),
                Arguments.of( 1L,"johnny", "john@example.com", "newpass"), // full update                 // chuỗi rỗng -> test validation fail
                Arguments.of( 1L,"john", "john@example.com", "newpass")                           // email invalid
        );
    }



    @Test
    void deleteUser_ShouldReturn204_WhenSuccess() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }


}
