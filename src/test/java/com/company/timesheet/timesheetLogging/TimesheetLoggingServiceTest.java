package com.company.timesheet.timesheetLogging;

import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.dto.TimesheetLoggingDto;
import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.dto.UserDto;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.model.User;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import com.company.timesheet.services.TimesheetLoggingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TimesheetLoggingServiceTest {
    @Autowired
    TimesheetLoggingService timesheetLoggingService;
    @Autowired
    TimesheetLoggingRepository timesheetLoggingRepository;


    @Test
    @DisplayName("Returns LoggingTimesheet when Logging successful")
    void testRegisterUser(){
        TimesheetLoggingRequest timesheetLoggingRequest= new TimesheetLoggingRequest(LocalDateTime.now().minusMinutes(30),LocalDateTime.now());
        TimesheetLoggingDto timesheetLoggingDtoSaved =timesheetLoggingService.saveTimesheetLogging(timesheetLoggingRequest,20L);

        TimesheetLoggingDto retrieved= timesheetLoggingService.getTodayLoggingTime(20L).get(0);

        then(timesheetLoggingDtoSaved.getUserID()).isNotNull();
        then(retrieved.getUserID()).isEqualTo(timesheetLoggingDtoSaved.getUserID());
    }
}
