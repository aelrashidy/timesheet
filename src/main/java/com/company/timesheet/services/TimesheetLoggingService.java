package com.company.timesheet.services;

import com.company.timesheet.dto.TimesheetLoggingDto;
import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.LoginTrials;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.LoginTrialsRepository;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TimesheetLoggingService {
    private static final Logger logger = LogManager.getLogger(TimesheetLoggingService.class);
   TimesheetLoggingRepository timesheetLoggingRepository;
   LoginTrialsRepository loginTrialsRepository;
    public TimesheetLoggingService(TimesheetLoggingRepository timesheetLoggingRepository,
                                   LoginTrialsRepository loginTrialsRepository) {
        this.timesheetLoggingRepository = timesheetLoggingRepository;
        this.loginTrialsRepository = loginTrialsRepository;
    }
    public void checkLoginValidation(Long userId) {
        logger.info("Check login validation method called at: {}", new Date());
        logger.debug("Check login validation method called for user ID: {}", userId);
        //before saving the timesheet logging, we need to check if the user exists and the login trials are valid
        LoginTrials loginTrials=loginTrialsRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Login trials not found for user ID: " + userId));
        if (loginTrials.getExpirationTime().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Login trials expired for user ID: " + userId);
        }
    }
    public TimesheetLoggingDto saveTimesheetLogging(TimesheetLoggingRequest timesheetLoggingRequest, Long id) {
        logger.info("Save timesheet logging method called at: {}", new Date());
        logger.debug("Save timesheet logging method called with request: {}", timesheetLoggingRequest);
           checkLoginValidation(id);
        if (timesheetLoggingRequest.getLoginTime() == null || timesheetLoggingRequest.getLogoutTime() == null) {
            throw new IllegalArgumentException("Login and logout times must not be null");
        }
        if (timesheetLoggingRequest.getLoginTime().isAfter(timesheetLoggingRequest.getLogoutTime()))
        {
            throw new IllegalArgumentException("Login time must be before logout time");
        }
        TimesheetLogging timesheetLogging = new TimesheetLogging(
                timesheetLoggingRequest.getLoginTime(),
                timesheetLoggingRequest.getLogoutTime()
        );
        timesheetLogging.setUserID(id);
        timesheetLoggingRepository.save(timesheetLogging);
        TimesheetLoggingDto timesheetLoggingDto= new TimesheetLoggingDto(
                timesheetLogging.getUserID(),
                timesheetLogging.getLoginTime(),
                timesheetLogging.getLogoutTime()
        );
        timesheetLoggingDto.setDescription("Timesheet logging saved successfully");
        return timesheetLoggingDto;
    }
    public List<TimesheetLoggingDto> getTodayLoggingTime(Long userId) {
        logger.info("Get today logging time method called at: {}", new Date());
        logger.debug("Get today logging time method called for user ID: {}", userId);
        checkLoginValidation(userId);
        List<TimesheetLogging> timesheetLoggingList= timesheetLoggingRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("Timesheet logging not found for user ID: " + userId));
        LocalDate today = LocalDate.now();
        timesheetLoggingList.removeIf(logging ->
                !logging.getLoginTime().toLocalDate().isEqual(LocalDate.now())
        );
        List<TimesheetLoggingDto> timesheetLoggingDtoList = timesheetLoggingList.stream().map(logging -> new TimesheetLoggingDto(
                logging.getUserID(),
                logging.getLoginTime(),
                logging.getLogoutTime()
        )).toList();
        return timesheetLoggingDtoList;
    }
}
