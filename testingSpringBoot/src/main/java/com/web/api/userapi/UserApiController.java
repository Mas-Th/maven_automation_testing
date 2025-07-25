package com.web.api.userapi;


import com.web.response.ResponseResult;
import com.web.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserApiController {


    private final IUserService userService;

    public UserApiController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<UserDtoResponse>>> getUsers() {
        List<UserDtoResponse> users = userService.getAllUsers();
      return ResponseEntity.ok(ResponseResult.success("Fetched users", users));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<UserDtoResponse>> userById(@PathVariable Long id) {
        UserDtoResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ResponseResult.success("Fetched user", user));
    }

    // crete user
    @PostMapping
    public ResponseEntity<ResponseResult<UserDtoResponse>> addUser(@Validated @RequestBody  UserDtoRequest userDtoRequest) {
        URI location = URI.create("/api/users");
        // Gọi service để lưu userDtoRequest
        UserDtoResponse user = userService.createUser(userDtoRequest);

        return ResponseEntity.created(location).body(ResponseResult.success("User created", user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseResult<UserDtoResponse>> updateUsers(
            @Validated
            @PathVariable Long id,
            @RequestBody  UserDtoRequest userDtoRequest) {

        UserDtoResponse user = userService.updateUser(id, userDtoRequest);
        return ResponseEntity.ok(ResponseResult.success("User updated", user));

    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseResult<UserDtoResponse>> patchUser (
            @Validated
            @PathVariable Long id,
            @RequestBody  UserDtoRequest userDtoRequest) {

        UserDtoResponse user = userService.updateUser(id, userDtoRequest);
        return ResponseEntity.ok(ResponseResult.success("update patched", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<UserDtoResponse>> deleteUser(@PathVariable Long id) {
        UserDtoResponse user = userService.deleteUser(id);
        return ResponseEntity.ok().body(ResponseResult.success("delete user", user));
    }
}
