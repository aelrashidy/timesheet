package com.company.timesheet.loginTrial;

import com.company.timesheet.model.LoginTrials;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.LoginTrialsRepository;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class LoginTrialsRepositoryTest {

    @Autowired
    LoginTrialsRepository loginTrialsRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void findLogginTrialByUserID(){
        //given
        LoginTrials loginTrialsSaved =testEntityManager.persistFlushFind(
                new LoginTrials(20L, LocalDateTime.now(), LocalDateTime.now().minusMinutes(30)));
        //when
        Boolean isExist= loginTrialsRepository.existsByUserId(20L);

        //then
        then(loginTrialsSaved.getId()).isNotNull();
        then(isExist).isEqualTo(true);

    }
}
