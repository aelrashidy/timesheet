package com.company.timesheet.services;

import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.LoginTrials;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.LoginTrialsRepository;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import com.company.timesheet.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TimesheetLoggingService {
   TimesheetLoggingRepository timesheetLoggingRepository;
   LoginTrialsRepository loginTrialsRepository;
    public TimesheetLoggingService(TimesheetLoggingRepository timesheetLoggingRepository,
                                   LoginTrialsRepository loginTrialsRepository) {
        this.timesheetLoggingRepository = timesheetLoggingRepository;
        this.loginTrialsRepository = loginTrialsRepository;
    }

    public TimesheetLogging saveTimesheetLogging(TimesheetLoggingRequest timesheetLoggingRequest,Long id) {
        //before saving the timesheet logging, we need to check if the user exists and if the login trials are valid
        LoginTrials loginTrials=loginTrialsRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Login trials not found for user ID: " + id));
        if (loginTrials.getExpirationTime().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Login trials expired for user ID: " + id);
        }

        if (timesheetLoggingRequest.getLoginTime() == null || timesheetLoggingRequest.getLogoutTime() == null) {
            throw new IllegalArgumentException("Login and logout times must not be null");
        }
        TimesheetLogging timesheetLogging = new TimesheetLogging(
                timesheetLoggingRequest.getLoginTime(),
                timesheetLoggingRequest.getLogoutTime()
        );
        timesheetLogging.setUserID(id);
        timesheetLoggingRepository.save(timesheetLogging);
        return timesheetLogging;
    }
}
