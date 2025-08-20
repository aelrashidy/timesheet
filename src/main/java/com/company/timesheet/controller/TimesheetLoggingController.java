package com.company.timesheet.controller;

import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.model.User;
import com.company.timesheet.services.TimesheetLoggingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetLoggingController {

    TimesheetLoggingService timesheetLoggingService;
    public TimesheetLoggingController(TimesheetLoggingService timesheetLoggingService) {
        this.timesheetLoggingService = timesheetLoggingService;
    }
    @PostMapping("/logging/{userId}")
    public TimesheetLogging loggingTime(@Valid @RequestBody TimesheetLoggingRequest timesheetLoggingRequest
    ,@PathVariable Long userId
    ) {
        return timesheetLoggingService.saveTimesheetLogging(timesheetLoggingRequest, userId);
    }
}
