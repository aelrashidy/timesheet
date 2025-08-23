package com.company.timesheet.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class TimesheetLoggingDto {

    private Long userID;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private String Description;
    public TimesheetLoggingDto(Long userID, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.userID = userID;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public TimesheetLoggingDto() {
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
