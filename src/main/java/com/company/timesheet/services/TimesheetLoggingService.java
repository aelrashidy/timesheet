package com.company.timesheet.services;

import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.LoginTrials;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.LoginTrialsRepository;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import com.company.timesheet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class TimesheetLoggingService {
   TimesheetLoggingRepository timesheetLoggingRepository;
   LoginTrialsRepository loginTrialsRepository;
    public TimesheetLoggingService(TimesheetLoggingRepository timesheetLoggingRepository,
                                   LoginTrialsRepository loginTrialsRepository) {
        this.timesheetLoggingRepository = timesheetLoggingRepository;
        this.loginTrialsRepository = loginTrialsRepository;
    }

    public String saveTimesheetLogging(TimesheetLoggingRequest timesheetLoggingRequest,Long id) {
        //before saving the timesheet logging, we need to check if the user exists and the login trials are valid
        LoginTrials loginTrials=loginTrialsRepository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("Login trials not found for user ID: " + id));
        if (loginTrials.getExpirationTime().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Login trials expired for user ID: " + id);
        }

        if (timesheetLoggingRequest.getLoginTime() == null || timesheetLoggingRequest.getLogoutTime() == null) {
            throw new IllegalArgumentException("Login and logout times must not be null");
        }
        if (timesheetLoggingRequest.getLoginTime().after(timesheetLoggingRequest.getLogoutTime()))
        {
            throw new IllegalArgumentException("Login time must be before logout time");
        }
        TimesheetLogging timesheetLogging = new TimesheetLogging(
                timesheetLoggingRequest.getLoginTime(),
                timesheetLoggingRequest.getLogoutTime()
        );
        timesheetLogging.setUserID(id);
        timesheetLoggingRepository.save(timesheetLogging);
        return "Timesheet logging saved successfully";
    }
    public List<TimesheetLogging> getTodayLoggingTime(Long userId) {
        //before saving the timesheet logging, we need to check if the user exists and the login trials are valid
        LoginTrials loginTrials=loginTrialsRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Login trials not found for user ID: " + userId));
        if (loginTrials.getExpirationTime().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Login trials expired for user ID: " + userId);
        }
        List<TimesheetLogging> timesheetLoggingList= timesheetLoggingRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("Timesheet logging not found for user ID: " + userId));
        LocalDate today = LocalDate.now();
        timesheetLoggingList.removeIf(logging ->
                !logging.getLoginTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today)
        );
        return timesheetLoggingList;
    }
}
