package com.company.timesheet.controller;

import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.model.User;
import com.company.timesheet.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public User register( @Valid @RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }
}
