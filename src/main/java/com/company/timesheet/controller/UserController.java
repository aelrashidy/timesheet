package com.company.timesheet.controller;

import com.company.timesheet.dto.LoginRequest;
import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.dto.UserDto;
import com.company.timesheet.model.User;
import com.company.timesheet.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(userService.registerUser(registerRequest));
        } catch (IllegalArgumentException e) {
            UserDto userDto= new UserDto();
            userDto.setDescription("Register error: "+e.getMessage());
            return ResponseEntity.badRequest().body(userDto);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUSer(@RequestBody LoginRequest loginRequest) {
        try{
            return ResponseEntity.ok(userService.loginUser(loginRequest));
        } catch (IllegalArgumentException e) {
            logger.error("Login error: {}", e.getMessage());
            UserDto userDto= new UserDto();
            userDto.setDescription("Login error: "+e.getMessage());
            return ResponseEntity.badRequest().body(userDto);
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
