//package com.web.user;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//// ✅ Test lớp controller riêng biệt mà không khởi chạy toàn bộ Spring context
//@WebMvcTest(UserController.class)
//class UserControllerValidationTest {
//
//    // ✅ Cung cấp khả năng mô phỏng request HTTP đến controller
//    @Autowired
//    private MockMvc mockMvc;
//
//    // ✅ Dùng để convert object Java thành chuỗi JSON
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    // 🔎 Test khi username không hợp lệ (bị để trống)
//    @Test
//    void whenInvalidUsername_thenReturns400() throws Exception {
//        // ❌ Tạo DTO có username rỗng
//        UserDtoRequest dto = new UserDtoRequest("", "ValidPass123", "valid@email.com");
//
//        // 🔥 Gửi POST /users với nội dung JSON không hợp lệ → mong đợi lỗi 400 Bad Request
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto))) // Chuyển DTO thành JSON
//                .andExpect(status().isBadRequest()); // ✅ Mong đợi lỗi validation
//    }
//
//    // 🔎 Test khi email sai định dạng
//    @Test
//    void whenInvalidEmail_thenReturns400() throws Exception {
//        // ❌ Email sai định dạng
//        UserDtoRequest dto = new UserDtoRequest("validuser", "ValidPass123", "invalid-email");
//
//        // 🔥 Gửi request với email sai → mong đợi lỗi 400
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest()); // ✅ Mong đợi validation lỗi
//    }
//
//    // 🔎 Test khi toàn bộ input đều hợp lệ
//    @Test
//    void whenValidInput_thenReturns200() throws Exception {
//        // ✅ Dữ liệu đúng
//        UserDtoRequest dto = new UserDtoRequest("validuser", "ValidPass123", "user@example.com");
//
//        // 🔥 Gửi request với dữ liệu hợp lệ → mong đợi phản hồi 200 OK và nội dung cụ thể
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk()) // ✅ Mong đợi thành công
//                .andExpect(content().string("User created")); // ✅ Kiểm tra nội dung phản hồi
//    }
//}
