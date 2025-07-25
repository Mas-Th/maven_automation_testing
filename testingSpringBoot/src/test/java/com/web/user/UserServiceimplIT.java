package com.web.user;

import com.web.exception.ResourceAlreadyExistsException;
import com.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
class UserServiceImplIT {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers_ShouldReturnUserList_WhenUsersExist() {
        // ✅ 1. Khởi tạo danh sách UserEntity giả lập như thể lấy từ DB
        List<UserEntity> users = List.of(
                new UserEntity(1L, "alice", "alice@example.com", "password1"),
                new UserEntity(2L, "bob", "bob@example.com", "password2"),
                new UserEntity(3L, "cvc", "cvc@example.com", "password3")
        );

        // ✅ 2. Giả lập hành vi của repository: khi gọi findAll() sẽ trả về danh sách trên
        when(userRepository.findAll()).thenReturn(users);

        // ✅ 3. Gọi service thực tế để kiểm thử logic xử lý danh sách users
        List<UserDtoResponse> result = userService.getAllUsers();

        // ✅ 4. Lấy phần tử đầu tiên trong kết quả để kiểm tra các field bên trong
        UserDtoResponse result1 = result.getFirst();

        // ✅ 5. Kiểm tra kích thước danh sách trả về đúng như mong đợi (có 3 phần tử)
        assertEquals(3, result.size());

        // ✅ 6. Kiểm tra dữ liệu cụ thể: vị trí đầu tiên và thứ hai có đúng username không
        assertEquals("alice", result.get(0).username());
        assertEquals("bob", result.get(1).username());

        // ✅ 7. (Nâng cao) Kiểm tra số lượng field trong class DTO là đúng (ví dụ: username, email, password)
        assertEquals(3, result1.getClass().getDeclaredFields().length);

        // ✅ 8. Đảm bảo repository được gọi đúng duy nhất 1 lần
        verify(userRepository, times(1)).findAll();

        // ✅ 9. Đảm bảo không có phương thức nào khác của repository bị gọi thêm ngoài findAll()
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenNoUserExists() {
        // ✅ Giả lập repository trả về danh sách rỗng
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // ✅ Gọi phương thức service thực tế
        List<UserDtoResponse> result = userService.getAllUsers();

        // ✅ Kiểm tra kết quả trả về là danh sách rỗng
        assertNotNull(result);                  // Danh sách không bị null
        assertTrue(result.isEmpty());          // Danh sách phải rỗng (size = 0)

        // ✅ Đảm bảo repository được gọi đúng 1 lần
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        // ✅ Chuẩn bị mock dữ liệu: User tồn tại trong DB (giả lập)
        UserEntity user = new UserEntity(1L, "john", "john@example.com", "john@example.com");

        // ✅ Khi gọi findById(1L), giả lập trả về user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // ✅ Gọi service thực tế cần kiểm thử
        UserDtoResponse result = userService.getUserById(1L);

        // ✅ So sánh giá trị trả về có đúng như kỳ vọng không
        assertEquals(user.getUsername(), result.username());

        // ✅ Đảm bảo repository chỉ gọi đúng 1 lần duy nhất phương thức findById(1L)
        verify(userRepository, times(1)).findById(1L);

        // ✅ Đảm bảo không có method nào khác của repository bị gọi ngoài findById
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        // ✅ Giả lập trường hợp không tìm thấy user (Optional.empty)
        when(userRepository.findById(88L)).thenReturn(Optional.empty());

        // ✅ Gọi service và kỳ vọng nó sẽ ném ra ResourceNotFoundException
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                userService.getUserById(88L)
        );

        // ✅ Kiểm tra nội dung thông báo lỗi của exception có đúng không
        assertEquals("User not found with id: 88", ex.getMessage());

        // ⚠️ (Tùy chọn): Bạn có thể thêm dòng này để đảm bảo repository chỉ gọi đúng phương thức cần thiết
        verify(userRepository).findById(88L);
        verifyNoMoreInteractions(userRepository);
    }


    @ParameterizedTest
    @CsvSource({
            "false,false,true,''",                         // OK
            "true,false,false,'Username already exists!'", // Username trùng
            "false,true,false,'Email already exists!'",    // Email trùng
            "true,true,false,'Username already exists!'"   // Cả hai trùng → Ưu tiên username
    })
    void createUser_withDifferentExistenceScenarios(
            boolean usernameExists,
            boolean emailExists,
            boolean shouldSucceed,
            String expectedErrorMessage) {

        // given
        UserDtoRequest request = new UserDtoRequest("alice", "alice@example.com", "alice@example.com");
        UserEntity saved = new UserEntity(1L,"alice", "alice@example.com", "alice@example.com");

        when(userRepository.existsByUsername("alice")).thenReturn(usernameExists);

        // chỉ mock existsByEmail nếu tới đó
        if (!usernameExists) {
            when(userRepository.existsByEmail("alice@example.com")).thenReturn(emailExists);
        }

        if (shouldSucceed) {
            when(userRepository.save(any(UserEntity.class))).thenReturn(saved);
            UserDtoResponse result = userService.createUser(request);
            assertEquals("alice", result.username());

            // ✅ kiểm tra gọi đúng thứ tự
            InOrder inOrder = inOrder(userRepository);
            inOrder.verify(userRepository).existsByUsername("alice");
            inOrder.verify(userRepository).existsByEmail("alice@example.com");
            inOrder.verify(userRepository).save(any(UserEntity.class));
            verifyNoMoreInteractions(userRepository);

        } else {
            ResourceAlreadyExistsException ex =
                    assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(request));
            assertEquals(expectedErrorMessage, ex.getMessage());

            // ✅ kiểm tra gọi đúng thứ tự và dừng đúng chỗ
            InOrder inOrder = inOrder(userRepository);
            inOrder.verify(userRepository).existsByUsername("alice");
            if (!usernameExists) {
                inOrder.verify(userRepository).existsByEmail("alice@example.com");
            }

            verify(userRepository, never()).save(any());
            verifyNoMoreInteractions(userRepository);
        }
    }


    @ParameterizedTest
    @MethodSource("provideUserDtoRequests")
    void updateUser_ShouldCallRepository_WhenUserIsExists_ThenSuccess(UserDtoRequest request) {
        // ✅ Khởi tạo dữ liệu giả lập
        Long id = 1L;
        UserEntity existingUser = new UserEntity(id, "bob", "bob@example.com", "bob@example.com");

        // ✅ Giả lập: tìm thấy user trong DB
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        // ✅ Giả lập: save trả lại entity được cập nhật
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ✅ Gọi phương thức cần test
        userService.updateUser(id, request);

        // ✅ Bắt giá trị truyền vào save()
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        UserEntity updated = userCaptor.getValue();


        // ✅ Kiểm tra từng field
        assertEquals(request.getUsername(), updated.getUsername());
        assertEquals(request.getEmail(), updated.getEmail());
        assertEquals(request.getPassword(), updated.getPassword());

        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    // ✅ Cung cấp dữ liệu cho ParameterizedTest
    private static Stream<Arguments> provideUserDtoRequests() {
        return Stream.of(
                Arguments.of(new UserDtoRequest(1L, "bobb", "bob@example.com", "bob@example.com")),
                Arguments.of(new UserDtoRequest(1L, "bob", "bobb@example.com", "bob@example.com")),
                Arguments.of(new UserDtoRequest(1L, "bob", "bob@example.com", "bobb@example.com")),
                Arguments.of(new UserDtoRequest(1L, "bob", "bobb@example.com", "bobb@example.com")),
                Arguments.of(new UserDtoRequest(1L, "bobb", "bob@example.com", "bobb@example.com")),
                Arguments.of(new UserDtoRequest(1L, "bobb", "bobb@example.com", "bobb@example.com"))
        );
    }


    @Test
    void updateUser_ShouldThrowException_WhenNotFound() {
        // ✅ Khởi tạo id giả lập
        Long id = 88L;
        UserDtoRequest request = new UserDtoRequest(id, "bobbb", "bob@example.com", "bob@example.com");

        // ✅ Giả lập DB không tìm thấy user với id trên
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // ✅ Gọi method deleteUser và kiểm tra xem có ném ra ResourceNotFoundException không
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                userService.updateUser(id, request)
        );

        // ✅ Kiểm tra message exception trả về đúng
        assertEquals("User not found with id: " + id, ex.getMessage());

        // ✅ Kiểm tra findById được gọi đúng 1 lần với đúng id
        verify(userRepository, times(1)).findById(id);

        // ✅ Đảm bảo không có phương thức nào khác của repository được gọi
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser_ShouldCallRepository_WhenUserIsExists() {
        // ✅ 1. Giả lập một user tồn tại trong hệ thống (với id = 1L)
        UserEntity user = new UserEntity(1L, "bob", "bob@example.com", "bob@example.com");

        // ✅ 2. Khi gọi userRepository.findById(1L), giả lập sẽ trả về user này (bỏ qua DB thật)
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // ✅ 3. Gọi method thực tế trong service cần test
        // Mục tiêu: service sẽ gọi repository để xóa user theo ID
        userService.deleteUser(1L);

        // ✅ 5. (Bổ sung nâng cao) – kiểm tra deleteById được gọi đúng MỘT lần duy nhất
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L); // đảm bảo không gọi nhiều lần

        verifyNoMoreInteractions(userRepository); //  Không gọi thêm gì khác
    }

    @Test
    void deleteUser_ShouldThrowException_WhenNotFound() {
        // ✅ Khởi tạo id giả lập
        Long id = 88L;

        // ✅ Giả lập DB không tìm thấy user với id trên
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // ✅ Gọi method deleteUser và kiểm tra xem có ném ra ResourceNotFoundException không
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                userService.deleteUser(id)
        );

        // ✅ Kiểm tra message exception trả về đúng
        assertEquals("User not found with id: " + id, ex.getMessage());

        // ✅ Kiểm tra findById được gọi đúng 1 lần với đúng id
        verify(userRepository, times(1)).findById(id);

        // ✅ Đảm bảo không có phương thức nào khác của repository được gọi
        verifyNoMoreInteractions(userRepository);
    }


}
