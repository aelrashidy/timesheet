package com.company.timesheet.controller;

import com.company.timesheet.dto.RegisterRequest;
import com.company.timesheet.dto.TimesheetLoggingRequest;
import com.company.timesheet.model.TimesheetLogging;
import com.company.timesheet.model.User;
import com.company.timesheet.services.TimesheetLoggingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetLoggingController {
    private static final Logger logger = LoggerFactory.getLogger(TimesheetLoggingController.class);
    TimesheetLoggingService timesheetLoggingService;
    public TimesheetLoggingController(TimesheetLoggingService timesheetLoggingService) {
        this.timesheetLoggingService = timesheetLoggingService;
    }
    @PostMapping("/logging/{userId}")
    public ResponseEntity<String> loggingTime(@RequestBody TimesheetLoggingRequest timesheetLoggingRequest
            , @PathVariable Long userId)
    {
        try {
            return ResponseEntity.ok(timesheetLoggingService.saveTimesheetLogging(timesheetLoggingRequest, userId));
        } catch (IllegalArgumentException e) {
            logger.error("Timesheet logging error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/getLoggingTime/{userId}")
    public ResponseEntity<List<TimesheetLogging>> getLoggingTime(@PathVariable Long userId)
    {
        try {
            return ResponseEntity.ok(timesheetLoggingService.getTodayLoggingTime( userId));
        } catch (IllegalArgumentException e) {
            logger.error("Can't Retrieve Data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

    }
}
