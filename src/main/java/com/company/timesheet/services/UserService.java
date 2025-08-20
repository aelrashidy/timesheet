package com.company.timesheet.services;

import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.model.User;
import com.company.timesheet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
//    PasswordEncoder encoder;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(RegisterRequest registerRequest) {
       if(userRepository.existsByEmail(registerRequest.getEmail())){
           throw new IllegalArgumentException("Email already exists");
       }
        User user=new User(
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );
       userRepository.save(user);

       return user;
    }
}
