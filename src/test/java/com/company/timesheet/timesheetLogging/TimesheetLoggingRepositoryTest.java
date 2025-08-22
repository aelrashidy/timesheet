package com.company.timesheet.timesheetLogging;

import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TimesheetLoggingRepositoryTest {

    @Autowired
    TimesheetLoggingRepository timesheetLoggingRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void findLoggingByUserID(){
        //given
        TimesheetLogging timesheetLoggingSaved =testEntityManager.persistFlushFind( new TimesheetLogging
                (8L,LocalDateTime.now().minusMinutes(30), LocalDateTime.now()));
        //when
        TimesheetLogging timesheetLogging= timesheetLoggingRepository.findByUserID(8L).get().get(0);

        //then
        then(timesheetLoggingSaved.getId()).isNotNull();
        then(timesheetLogging.getUserID()).isEqualTo(timesheetLoggingSaved.getUserID());

    }
}
