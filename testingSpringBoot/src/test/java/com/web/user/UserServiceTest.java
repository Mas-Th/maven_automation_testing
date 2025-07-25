//package com.web.user;
//
//
//import com.web.exception.ResourceNotFoundException;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.testng.annotations.Test;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//import static org.testng.Assert.assertThrows;
//import static org.testng.AssertJUnit.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void shouldReturnUserWhenIdExists() {
//        UserEntity user = new UserEntity(1L, "john", "john@example.com");
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        UserDtoResponse result = userService.getUserById(1L);
//
//        assertEquals("john", result.getUsername());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenIdNotFound() {
//        when(userRepository.findById(99L)).thenReturn(Optional.of());
//
//        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
//    }
//}
