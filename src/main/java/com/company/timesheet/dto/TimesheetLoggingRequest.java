package com.company.timesheet.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.Date;


public class TimesheetLoggingRequest {

    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public TimesheetLoggingRequest(LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
}
