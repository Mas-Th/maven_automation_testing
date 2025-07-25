//package com.web.user;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testng.annotations.Test;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private IUserService userService;
//
//    @Test
//    void shouldReturnUserById() throws Exception {
//        UserDtoResponse mockUser = new UserDtoResponse(1L, "john", "john@example.com");
//
//        when(userService.getUserById(1L)).thenReturn(mockUser);
//
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.username").value("john"));
//    }
//}
