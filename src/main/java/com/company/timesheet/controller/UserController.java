package com.company.timesheet.controller;

import com.company.timesheet.dto.LoginRequest;
import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.model.User;
import com.company.timesheet.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(userService.registerUser(registerRequest));
        } catch (IllegalArgumentException e) {
            logger.error("Registration error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUSer(@RequestBody LoginRequest loginRequest) {
        try{
            return ResponseEntity.ok(userService.loginUser(loginRequest));
        } catch (IllegalArgumentException e) {
            logger.error("Login error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logoutUSer(@PathVariable Long userId){
        try{
            return ResponseEntity.ok(userService.logoutUser(userId));
        } catch (IllegalArgumentException e) {
            logger.error("Logout error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
