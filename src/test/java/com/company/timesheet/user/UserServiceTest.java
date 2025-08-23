package com.company.timesheet.user;

import com.company.timesheet.config.EncryptionUtil;
import com.company.timesheet.dto.LoginRequest;
import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.dto.UserDto;
import com.company.timesheet.model.User;
import com.company.timesheet.repository.UserRepository;
import com.company.timesheet.services.UserService;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Returns registered user when successful")
    void testRegisterUser(){
        UserDto userRegistered=userService.registerUser(new RegisterRequest
                ("Ismail", "omismail89@gmail.com", "12345645"));

        User user= userRepository.findByEmail("omismail89@gmail.com").get();

        then(userRegistered.getId()).isNotNull();
        then(user.getId()).isEqualTo(userRegistered.getId());
    }
    @Test
    @DisplayName("Returns Login user when successful")
    void testLoginUser(){
        UserDto userLogin=userService.loginUser(new LoginRequest
                ("omismail@gmail.com", "12345645"));

        then(userLogin.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Login user when not successful")
    void testNonSuccessfulLoginUser(){
        Throwable throwable= catchThrowable(
                ()->   userService.loginUser(new LoginRequest("omismail99@gmail.com", "12345645"))
        );
        BDDAssertions.then(throwable).isInstanceOf(IllegalArgumentException.class);
    }
}
