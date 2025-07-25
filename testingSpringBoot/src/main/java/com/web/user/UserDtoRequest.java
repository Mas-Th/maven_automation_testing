package com.web.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter

public class UserDtoRequest {

    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;


    public UserDtoRequest(){}

    public UserDtoRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDtoRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDtoRequest(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
