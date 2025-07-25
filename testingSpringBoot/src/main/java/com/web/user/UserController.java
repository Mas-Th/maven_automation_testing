package com.web.user;


import com.web.response.ResponseResult;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@Controller
@RequestMapping("/users")

public class UserController  {


    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<UserDtoResponse> users = userService.getAllUsers();
        model.addAttribute("users", users );
        return "users/index";
    }


    // ID user: GET /user/{id}
    @GetMapping("/{id}")
    public String userById(Model model, @PathVariable Long id) {
        UserDtoResponse user = userService.getUserById(id);
        model.addAttribute("user", user );
        return "users/detail";
    }



}
