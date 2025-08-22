package com.company.timesheet.services;

import com.company.timesheet.config.EncryptionUtil;
import com.company.timesheet.controller.UserController;
import com.company.timesheet.dto.LoginRequest;
import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.model.LoginTrials;
import com.company.timesheet.model.User;
import com.company.timesheet.repository.LoginTrialsRepository;
import com.company.timesheet.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    EncryptionUtil encryptionUtil;
    UserRepository userRepository;
    LoginTrialsRepository loginTrialsRepository;
    public UserService(UserRepository userRepository, EncryptionUtil encryptionUtil,
                       LoginTrialsRepository loginTrialsRepository)  {
        this.userRepository = userRepository;
        this.encryptionUtil = encryptionUtil;
        this.loginTrialsRepository = loginTrialsRepository;
    }
    public String registerUser(RegisterRequest registerRequest) {
        logger.info("Registering user method called at: {}", new Date());
        logger.debug("Registering user method called with request: {}", registerRequest);
       if(registerRequest.getName() == null || registerRequest.getName().isEmpty()) {
           throw new IllegalArgumentException("Name must not be null");
       }
       else {
           if(registerRequest.getName().length()<3 || registerRequest.getName().length()>50) {
               throw new IllegalArgumentException("Name must be between 3 and 50 characters");
           }
       }
        if(registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null");
        }
        else {
            if (!registerRequest.getEmail().contains("@") || !registerRequest.getEmail().contains(".")) {
                throw new IllegalArgumentException("Please provide a valid email address");
            }
        }
        if(registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null");
        }
        else {
            if (registerRequest.getPassword().length() < 6 || registerRequest.getName().length() > 50) {
                throw new IllegalArgumentException("Password must be between 6 and 50 characters");
            }
        }
        // Check if the email already exists in the database
       if(userRepository.existsByEmail(registerRequest.getEmail())){
           throw new IllegalArgumentException("Email already exists");
       }
       registerRequest.setPassword(encryptionUtil.encrypt(registerRequest.getPassword()));
        User user=new User(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );
       userRepository.save(user);

       return "User registered successfully: " + user.getName();
    }
    public String loginUser(LoginRequest loginRequest) {
        logger.info("Login user method called at: {}", new Date());
        logger.debug("Login user method called with request: {}", loginRequest);
        if(loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null");
        }
        if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null");
        }
        LoginTrials loginTrials=new LoginTrials();
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!encryptionUtil.decrypt(user.getPassword()).equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        // If success login, we will check if exist in the login trials table
        // If it exists, we will update the data
        // if not exist, will save a new record
        if (loginTrialsRepository.existsByUserId(user.getId())) {
            loginTrials = loginTrialsRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Login trials not found for user ID: " + user.getId()));
            loginTrials.setExpirationTime(LocalDateTime.now().plusMinutes(30));
            loginTrials.setTrialTime(LocalDateTime.now());
        } else {
            loginTrials.setUserId(user.getId());
            loginTrials.setExpirationTime(LocalDateTime.now().plusMinutes(30));
            loginTrials.setTrialTime(LocalDateTime.now());
        }
        loginTrialsRepository.save(loginTrials);
        return "Login successful for user: " + user.getId();
    }
    public String logoutUser(Long userId) {
        logger.info("Logout user method called at: {}", new Date());
        logger.debug("Logout user method called with userId: {}", userId);
        LoginTrials loginTrials = loginTrialsRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Login trials not found for user ID: " + userId));
        loginTrials.setExpirationTime(LocalDateTime.now());
        loginTrialsRepository.save(loginTrials);
        return "Logout successful for user ID: " + userId;
    }
}
