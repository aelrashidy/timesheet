package com.company.timesheet.services;

import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.repository.TimesheetLoggingRepository;
import com.company.timesheet.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TimesheetLoggingService {
   TimesheetLoggingRepository timesheetLoggingRepository;
    public TimesheetLoggingService(TimesheetLoggingRepository timesheetLoggingRepository) {
        this.timesheetLoggingRepository = timesheetLoggingRepository;
    }

    public TimesheetLogging saveTimesheetLogging(TimesheetLoggingRequest timesheetLoggingRequest,Long id) {
        TimesheetLogging timesheetLogging = new TimesheetLogging(
                timesheetLoggingRequest.getLoginTime(),
                timesheetLoggingRequest.getLogoutTime()
        );
        timesheetLogging.setUserID(id);
        timesheetLoggingRepository.save(timesheetLogging);
        return timesheetLogging;
    }
}
