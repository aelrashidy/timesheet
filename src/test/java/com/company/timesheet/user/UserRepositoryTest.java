package com.company.timesheet.user;

import com.company.timesheet.model.User;
import com.company.timesheet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.BDDAssertions.then;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void testFindUserByEmail(){
       //given
        User user =testEntityManager.persistFlushFind( new  User
                ("Mohamed Ali", "moali99@gmail.com", "12345645"));
         //when
        User foundUser = userRepository.findByEmail("moali99@gmail.com").get();

        //then
        then(user.getId()).isNotNull();
        then(foundUser.getEmail()).isEqualTo(user.getEmail());

    }


}
